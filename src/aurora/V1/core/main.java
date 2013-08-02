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

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
import aurora.engine.V1.Logic.aSurface;
import aurora.engine.V1.UI.aDialog;
=======
import aurora.V1.core.screen_ui.StartScreenUI;
=======
import aurora.V1.core.screen_ui.WelcomeUI;
>>>>>>> origin/dev
=======
import aurora.V1.core.screen_ui.WelcomeUI;
>>>>>>> origin/dev
import aurora.engine.V1.Logic.ASurface;
import aurora.engine.V1.UI.ADialog;
>>>>>>> origin/dev
=======
import aurora.V1.core.screen_ui.StartScreenUI;
import aurora.engine.V1.Logic.ASurface;
import aurora.engine.V1.UI.ADialog;
>>>>>>> origin/dev
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.apache.log4j.Logger;

import org.apache.log4j.Logger;

/**
 *
 * @author Sammy
 */
public class main {

    private static ADialog err;
    private static Font FontRegular;
<<<<<<< HEAD
<<<<<<< HEAD
    private static aSurface ressource = null;
=======
    private static ASurface ressource = null;
>>>>>>> origin/dev
=======
    private static ASurface ressource = null;
>>>>>>> origin/dev
    private static boolean startMini = false;
<<<<<<< HEAD
    public static String VERSION = "Alpha 7";
    static final Logger logger = Logger.getLogger(main.class);

    public static void main(String[] args) throws InterruptedException, UnsupportedAudioFileException, IOException, LineUnavailableException, FontFormatException {

    	if (args.length > 0 && args[0].equalsIgnoreCase("Startup")) {
        	logger.info("Start Up Mode");
            startMini = true;
        } else {
        	logger.info("No start up");
          //  System.out.println("No start up");
=======
    public static String VERSION = "Alpha 8";
    static final Logger logger = Logger.getLogger(main.class);
    public static int LAUNCHES;

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
>>>>>>> origin/dev
            startMini = false;
        }



        if (Double.parseDouble(System.getProperty("java.version")
                .substring(2, 3)) >= 7
            && Integer.parseInt(System.getProperty("java.version").substring(6,
                8)) >= 9) {

            //Initiate The LoginWindow
<<<<<<< HEAD
        	logger.info("Running Java Version: " + System.getProperty("java.version"));
           // System.out.println("Running Java Version: " + System.getProperty("java.version"));
            logger.info("OS: " + System.getProperty("os.name"));
        	//System.out.println("OS: " + System.getProperty("os.name"));
            setLAF();
            WelcomeUI aurora_StartUp = new WelcomeUI(startMini);
            aurora_StartUp.loadUI();

        } else if (Double.parseDouble(System.getProperty("java.version").substring(0, 3)) == 1.7) {
            //Initiate The LoginWindow
        	logger.info("Running Java Version: " + System.getProperty("java.version"));
        	logger.info("OS: " + System.getProperty("os.name"));
            //System.out.println("Running Java Version: " + System.getProperty("java.version"));
            //System.out.println("OS: " + System.getProperty("os.name"));

            setLAF();

            WelcomeUI aurora_StartUp = new WelcomeUI(startMini);
            aurora_StartUp.loadUI();



=======
            logger.info("Running Java Version: " + System.getProperty(
                    "java.version"));
            logger.info("OS: " + System.getProperty("os.name"));
            WelcomeUI aurora_StartUp = new WelcomeUI(startMini);
            aurora_StartUp.loadUI();

>>>>>>> origin/dev
        } else {
<<<<<<< HEAD
<<<<<<< HEAD
            ressource = new aSurface("");
=======
            ressource = new ASurface("");
>>>>>>> origin/dev
=======
            ressource = new ASurface("");
>>>>>>> origin/dev
            try {
                FontRegular = Font.createFont(Font.TRUETYPE_FONT, new URL(
                        ressource.getSurfacePath()
                        + "/aurora/V1/resources/AGENCYR.TTF").openStream());
            } catch (Exception ex) {
                FontRegular = Font.createFont(Font.TRUETYPE_FONT, main.class
                        .getResourceAsStream("/aurora/V1/resources/AGENCYR.TTF"));
            }
            err = new ADialog(ADialog.aDIALOG_ERROR,
                    "Latest Version of Java 7 is Required   ", FontRegular
                    .deriveFont(Font.BOLD, 25),new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);

                }
            });
            err.setVisible(true);

<<<<<<< HEAD
            logger.info("Running Java Version: " + System.getProperty("java.version"));
            logger.error("Cannot Run Aurora, Java Version is below minimum (J6u17)");
           // System.out.println("Running Java Version: " + System.getProperty("java.version"));
           // System.out.println("Cannot Run Aurora, Java Version is Bellow minimum (J6u17)");
=======
            logger.info("Running Java Version: " + System.getProperty(
                    "java.version"));
            logger.error(
                    "Cannot Run Aurora, Java Version is below minimum");
>>>>>>> origin/dev

        }



    }

    private static void setLAF() {


        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
    }
}
