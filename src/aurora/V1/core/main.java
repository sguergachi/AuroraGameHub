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
import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.apache.log4j.Logger;

/**
 *
 * @author Sammy
 */
public class main {

    private static ADialog err;

    private static Font FontRegular;

    private static ASurface ressource = null;

    private static boolean startMini = false;

    public static String VERSION = "Alpha 10";

    public static int LAUNCHES;

    static final Logger logger = Logger.getLogger(main.class);

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

            //Initiate The LoginWindow
            logger.info("Running Java Version: " + System.getProperty(
                    "java.version"));
            logger.info("OS: " + System.getProperty("os.name"));
            WelcomeUI aurora_StartUp = new WelcomeUI(startMini);
            aurora_StartUp.loadUI();

        } else {
            ressource = new ASurface("");
            try {
                FontRegular = Font.createFont(Font.TRUETYPE_FONT, new URL(
                        ressource.getSurfacePath()
                                + "/aurora/V1/resources/AGENCYR.TTF")
                        .openStream());
            } catch (Exception ex) {
                FontRegular = Font.createFont(Font.TRUETYPE_FONT, main.class
                        .getResourceAsStream("/aurora/V1/resources/AGENCYR.TTF"));
            }
            err = new ADialog(ADialog.aDIALOG_ERROR,
                    "Latest Version of Java 7 is Required   ", FontRegular
                    .deriveFont(Font.BOLD, 25), new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.exit(0);

                        }
                    });
            err.setVisible(true);

            logger.info("Running Java Version: " + System.getProperty(
                    "java.version"));
            logger.error(
                    "Cannot Run Aurora, Java Version is below minimum");

        }

    }

}
