/*
 * Made By Sardonix Creative.
 *
 * This work is licensed under the
 * Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * To view a copy of this license, visit
 *
 *      http://creativecommons.org/licenses/by-nc-nd/3.0/
 *
 * or send a letter to Creative Commons, 444 Castro Street, Suite 900,
 * Mountain View, California, 94041, USA.
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package aurora.V1.core;

import aurora.V1.core.screen_ui.WelcomeUI;
import aurora.engine.V1.Logic.ASurface;
import aurora.engine.V1.UI.ADialog;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.logging.Level;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.apache.log4j.Logger;

/**
 *
 * @author Sammy
 */
public class main {

    private static ADialog errorDialog;

    private static Font FontRegular;

    private static ASurface ressource = null;

    private static boolean startMini = false;

    public static String VERSION = "Alpha 10";

    public static int LAUNCHES;

    public static String DATA_PATH = "//AuroraData//";

    static final Logger logger = Logger.getLogger(main.class);

    private static boolean okToRun = true;

    public static void main(String[] args) throws InterruptedException,
                                                  UnsupportedAudioFileException,
                                                  IOException,
                                                  LineUnavailableException,
                                                  FontFormatException {

        if (args.length > 0 && args[0].equalsIgnoreCase("Startup")) {
            logger.info("Start Up Mode");
            startMini = true;
        } else {
            logger.info("No start up");
            startMini = false;
        }

        // Determine version and update values of JRE
        String versionString = System.getProperty("java.version");
        int versionNum = Integer.parseInt(versionString.substring(0, 5).replace(
                ".", ""));
        int updateNum;
        if (versionString.contains("_")) {
            updateNum = Integer.parseInt(versionString.substring(versionString
                    .indexOf("_") + 1, versionString.length()));
        } else {
            updateNum = 9;
        }

        // Determine whether system can run Aurora
        if (versionNum >= 170 && updateNum >= 9) {


            launch();


        } else {
            ressource = new ASurface("");
            try {
                FontRegular = Font.createFont(Font.TRUETYPE_FONT, new URL(
                                              ressource.getSurfacePath()
                                                      + "/aurora/V1/resources/AGENCYR.TTF")
                                              .openStream());
            } catch (Exception ex) {
                FontRegular = Font.createFont(Font.TRUETYPE_FONT, main.class
                                              .getResourceAsStream(
                                                      "/aurora/V1/resources/AGENCYR.TTF"));
            }
            errorDialog = new ADialog(ADialog.aDIALOG_ERROR,
                              "Latest Version of Java 7 is Required   ",
                              FontRegular
                              .deriveFont(Font.PLAIN, 25),
                              new ActionListener() {
                                  @Override
                                  public void actionPerformed(ActionEvent e) {
                                      System.exit(0);

                                  }
                              });
            errorDialog.setVisible(true);

            logger.info("Running Java Version: " + System.getProperty(
                    "java.version"));
            logger.error(
                    "Cannot Run Aurora, Java Version is below minimum");

        }

    }

    private static void launch() {

        String line;

        // Prevent multiple instances of Aurora form being launched
        if (System.getProperty("os.name").contains("Windows")) {
            try {
                Process proc = Runtime.getRuntime().exec("wmic.exe");
                BufferedReader input = new BufferedReader(new InputStreamReader(
                        proc
                        .getInputStream()));
                OutputStreamWriter oStream = new OutputStreamWriter(proc
                        .getOutputStream());
                oStream.write("process where name='AuroraGameHub.exe'");
                oStream.flush();
                oStream.close();

                int count = 0;
                while ((line = input.readLine()) != null) {
                    if (count > 1 && line.trim().contains("AuroraGameHub.exe")) {
                        okToRun = false;
                        break;
                    }

                    count++;
                }
                input.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

        if (okToRun) {
            // Start Aurora
            logger.info("Running Java Version: " + System.getProperty(
                    "java.version"));
            logger.info("OS: " + System.getProperty("os.name"));
            WelcomeUI aurora_StartUp = new WelcomeUI(startMini);
            aurora_StartUp.loadUI();
        } else {
            logger.error(
                    "Cannot Run Aurora, Already an instance running");

            ressource = new ASurface("");
            try {
                FontRegular = Font.createFont(Font.TRUETYPE_FONT,
                                              new URL(ressource
                                                      .getSurfacePath()
                                                              + "/aurora/V1/resources/AGENCYR.TTF")
                                              .openStream());
            } catch (Exception ex) {
                try {
                    try {
                        FontRegular = Font.createFont(Font.TRUETYPE_FONT,
                                                      main.class
                                                      .getResourceAsStream(
                                                              "/aurora/V1/resources/AGENCYR.TTF"));
                    } catch (IOException ex1) {
                        java.util.logging.Logger.getLogger(main.class.getName())
                                .log(Level.SEVERE, null, ex1);
                    }
                } catch (FontFormatException ex1) {
                    java.util.logging.Logger.getLogger(main.class
                            .getName())
                            .log(Level.SEVERE, null, ex1);
                }
            }
            errorDialog = new ADialog(ADialog.aDIALOG_ERROR,
                              "An Instance of Aurora Is Already Running!  ",
                              FontRegular
                              .deriveFont(Font.PLAIN, 25),
                              new ActionListener() {
                                  @Override
                                  public void actionPerformed(
                                          ActionEvent e) {
                                              System.exit(0);

                                          }
                              });
            errorDialog.setVisible(true);
        }

    }

}
