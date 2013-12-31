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
package aurora.V1.core.screen_handler;

import aurora.V1.core.screen_logic.LibraryLogic;
import aurora.V1.core.screen_ui.SettingsUI;
import aurora.engine.V1.Logic.AThreadWorker;
import aurora.engine.V1.Logic.AuroraScreenHandler;
import aurora.engine.V1.Logic.AuroraScreenLogic;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class SettingsHandler implements AuroraScreenHandler {

    private AuroraScreenLogic settingsLogic;

    private SettingsUI settingsUI;

    public SettingsHandler(SettingsUI aThis) {
        this.settingsUI = aThis;
    }

    @Override
    public void setLogic(AuroraScreenLogic logic) {
        this.settingsLogic = logic;
    }

    public class RefreshAuroraDBHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            AThreadWorker worker = new AThreadWorker(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    SettingsUI.lblSettingsStatus.setForeground(Color.cyan);
                    SettingsUI.lblSettingsStatus.setText(
                            "Updating Aurora Cover DB...");



                    if (settingsUI.getDashboardUI().getStartUI().getLogic()
                            .downloadAuroraDB()) {
                        // display completion message in green
                        SettingsUI.lblSettingsStatus.setForeground(Color.GREEN);
                        SettingsUI.lblSettingsStatus.setText("Update Complete");
                    } else {
                        // display error in red
                        SettingsUI.lblSettingsStatus.setForeground(Color.red);
                        SettingsUI.lblSettingsStatus.setText(
                                "Unable to Download");
                    }



                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException ex) {
                        java.util.logging.Logger.getLogger(LibraryLogic.class
                                .getName()).
                                log(Level.SEVERE, null, ex);
                    }

                    if (SettingsUI.lblSettingsStatus.getCurrentText().equals(
                            "Update Complete") || SettingsUI.lblSettingsStatus
                            .getCurrentText().equals(
                                    "Unable to Download")) {
                        // Show default message after 1.5 seconds
                        SettingsUI.lblSettingsStatus.setForeground(
                                SettingsUI.DEFAULT_SETTINGS_COLOR);
                        SettingsUI.lblSettingsStatus.setText(
                                SettingsUI.DEAFULT_SETTINGS_STATUS);
                    }

                }
            });


            worker.startOnce();


        }

    }

    public class EnableBackgroundGameSearchHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {



            AThreadWorker worker = new AThreadWorker(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {


                    // Do work here



                    SettingsUI.lblSettingsStatus.setForeground(Color.GREEN);
                    SettingsUI.lblSettingsStatus.setText(
                            "Background Game Search Enabled");

                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException ex) {
                        java.util.logging.Logger.getLogger(LibraryLogic.class
                                .getName()).
                                log(Level.SEVERE, null, ex);
                    }

                    if (SettingsUI.lblSettingsStatus.getCurrentText().equals(
                            "Background Game Search Enabled")) {
                        // Show default message after 1.5 seconds
                        SettingsUI.lblSettingsStatus.setForeground(
                                SettingsUI.DEFAULT_SETTINGS_COLOR);
                        SettingsUI.lblSettingsStatus.setText(
                                SettingsUI.DEAFULT_SETTINGS_STATUS);
                    }

                }
            });


            worker.startOnce();



        }

    }

    public class DisableBackgroundGameSearchHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            AThreadWorker worker = new AThreadWorker(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {


                    // Do work here



                    SettingsUI.lblSettingsStatus.setForeground(Color.red);
                    SettingsUI.lblSettingsStatus.setText(
                            "Background Game Search Disabled");

                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException ex) {
                        java.util.logging.Logger.getLogger(LibraryLogic.class
                                .getName()).
                                log(Level.SEVERE, null, ex);
                    }

                    if (SettingsUI.lblSettingsStatus.getCurrentText().equals(
                            "Background Game Search Disabled")) {
                        // Show default message after 1.5 seconds
                        SettingsUI.lblSettingsStatus.setForeground(
                                SettingsUI.DEFAULT_SETTINGS_COLOR);
                        SettingsUI.lblSettingsStatus.setText(
                                SettingsUI.DEAFULT_SETTINGS_STATUS);
                    }

                }
            });


            worker.startOnce();

        }

    }

    public class EnableWASDNavigationHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            AThreadWorker worker = new AThreadWorker(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {


                    // Do work here



                    SettingsUI.lblSettingsStatus.setForeground(Color.GREEN);
                    SettingsUI.lblSettingsStatus.setText(
                            "WASD Navigation Enabled");

                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException ex) {
                        java.util.logging.Logger.getLogger(LibraryLogic.class
                                .getName()).
                                log(Level.SEVERE, null, ex);
                    }

                    if (SettingsUI.lblSettingsStatus.getCurrentText().equals(
                            "WASD Navigation Enabled")) {
                        // Show default message after 1.5 seconds
                        SettingsUI.lblSettingsStatus.setForeground(
                                SettingsUI.DEFAULT_SETTINGS_COLOR);
                        SettingsUI.lblSettingsStatus.setText(
                                SettingsUI.DEAFULT_SETTINGS_STATUS);
                    }

                }
            });


            worker.startOnce();
        }

    }

    public class DisableWASDNavigationHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            AThreadWorker worker = new AThreadWorker(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {


                    // Do work here



                    SettingsUI.lblSettingsStatus.setForeground(Color.red);
                    SettingsUI.lblSettingsStatus.setText(
                            "WASD Navigation Disabled");

                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException ex) {
                        java.util.logging.Logger.getLogger(LibraryLogic.class
                                .getName()).
                                log(Level.SEVERE, null, ex);
                    }

                    if (SettingsUI.lblSettingsStatus.getCurrentText().equals(
                            "WASD Navigation Disabled")) {
                        // Show default message after 1.5 seconds
                        SettingsUI.lblSettingsStatus.setForeground(
                                SettingsUI.DEFAULT_SETTINGS_COLOR);
                        SettingsUI.lblSettingsStatus.setText(
                                SettingsUI.DEAFULT_SETTINGS_STATUS);
                    }

                }
            });


            worker.startOnce();

        }

    }

    public class EnableSoundEffectsHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            AThreadWorker worker = new AThreadWorker(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {


                    // Do work here



                    SettingsUI.lblSettingsStatus.setForeground(Color.green);
                    SettingsUI.lblSettingsStatus.setText(
                            "Sound Effects Enabled");

                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException ex) {
                        java.util.logging.Logger.getLogger(LibraryLogic.class
                                .getName()).
                                log(Level.SEVERE, null, ex);
                    }

                    if (SettingsUI.lblSettingsStatus.getCurrentText().equals(
                            "Sound Effects Enabled")) {
                        // Show default message after 1.5 seconds
                        SettingsUI.lblSettingsStatus.setForeground(
                                SettingsUI.DEFAULT_SETTINGS_COLOR);
                        SettingsUI.lblSettingsStatus.setText(
                                SettingsUI.DEAFULT_SETTINGS_STATUS);
                    }

                }
            });


            worker.startOnce();
        }

    }

    public class DisableSoundEffectsHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            AThreadWorker worker = new AThreadWorker(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {


                    // Do work here



                    SettingsUI.lblSettingsStatus.setForeground(Color.red);
                    SettingsUI.lblSettingsStatus.setText(
                            "Sound Effects Disabled");

                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException ex) {
                        java.util.logging.Logger.getLogger(LibraryLogic.class
                                .getName()).
                                log(Level.SEVERE, null, ex);
                    }


                    if (SettingsUI.lblSettingsStatus.getCurrentText().equals(
                            "Sound Effects Disabled")) {
                        // Show default message after 1.5 seconds
                        SettingsUI.lblSettingsStatus.setForeground(
                                SettingsUI.DEFAULT_SETTINGS_COLOR);
                        SettingsUI.lblSettingsStatus.setText(
                                SettingsUI.DEAFULT_SETTINGS_STATUS);
                    }

                }
            });


            worker.startOnce();
        }

    }

}
