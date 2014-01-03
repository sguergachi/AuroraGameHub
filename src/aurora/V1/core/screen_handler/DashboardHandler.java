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

import aurora.V1.core.AuroraStorage;
import aurora.V1.core.screen_logic.DashboardLogic;
import aurora.V1.core.screen_logic.SettingsLogic;
import aurora.V1.core.screen_ui.DashboardUI;
import aurora.engine.V1.Logic.APostHandler;
import aurora.engine.V1.Logic.ASound;
import aurora.engine.V1.Logic.AuroraScreenHandler;
import aurora.engine.V1.Logic.AuroraScreenLogic;
import aurora.engine.V1.UI.ACarouselPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import org.apache.log4j.Logger;

/**
 * .------------------------------------------------------------------------.
 * | DashboardHandler
 * .------------------------------------------------------------------------.
 * |
 * | This class contains all Listeners/Handlers attached to UI elements
 * | found in DashboardUI. The handlers may access the logic or simply
 * | make simple processing within each Handler/Listeners.
 * |
 * | Each Handler is attached to UI components to listen for different actions
 * | The actions can be processed or handled internally or within th Logic
 * | of the Screen.
 * |
 * |
 * .........................................................................
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 * @author Carlos Machado <camachado@gmail.com>
 *
 */
public class DashboardHandler implements AuroraScreenHandler {

    /**
     * The DashboardUI instance passed to the handler.
     */
    private final DashboardUI dashboardUI;

    /**
     * The DashboardLogic instance obtained from the DashboardUI instance.
     */
    private DashboardLogic dashboardLogic;

    static final Logger logger = Logger.getLogger(DashboardHandler.class);

    /**
     * .-----------------------------------------------------------------------.
     * | DashboardHandler(DashboardUI)
     * .-----------------------------------------------------------------------.
     * |
     * | This is the Constructor of the Dashboard Handler class.
     * |
     * | The Constructor of the Handler class needs to UI class to be able to
     * | first get the logic from it, and second to be able to manipulate the UI
     * | within the actual Handlers.
     * |
     * .........................................................................
     * <p/>
     * @param aDashboardUI DashboardUI
     */
    public DashboardHandler(final DashboardUI aDashboardUI) {

        /*
         * Make local UI and Logic variables
         */

        this.dashboardUI = aDashboardUI;

    }

    @Override
    public final void setLogic(final AuroraScreenLogic logic) {
        this.dashboardLogic = (DashboardLogic) logic;
    }

    /**
     * .-----------------------------------------------------------------------.
     * | RightButtonListener
     * .-----------------------------------------------------------------------.
     * |
     * | Right Listener is the Action Listener attached to the Right Carousel
     * | Button.
     * | It makes the Carousel move Right 1 step.
     * |
     * |
     */
    public class RightButtonListener implements ActionListener {

        @Override
        public final void actionPerformed(final ActionEvent e) {

            AuroraStorage storage = dashboardUI.getStorage();
            final String soundEffectsSetting = storage.getStoredSettings()
                    .getSettingValue("sound_effects");

            dashboardUI.getCarousel().setPostLeftAnimation(new APostHandler() {
                @Override
                public void postAction() {
                    if (soundEffectsSetting.equals("enabled")) {
                        ASound showSound = new ASound("click_3.wav", false);
                        showSound.Play();
                    }

                }
            });
            dashboardUI.getCarousel().MoveLeft();

        }
    }

    /**
     * .-----------------------------------------------------------------------.
     * | LeftButtonListener
     * .-----------------------------------------------------------------------.
     * |
     * | Left Listener is the Action Listener attached to the Left Carousel
     * | Button.
     * | It makes the Carousel move Left 1 step.
     * |
     * |
     */
    public class LeftButtonListener implements ActionListener {

        @Override
        public final void actionPerformed(final ActionEvent e) {

            AuroraStorage storage = dashboardUI.getStorage();
            final String soundEffectsSetting = storage.getStoredSettings()
                    .getSettingValue("sound_effects");

            dashboardUI.getCarousel().setPostRightAnimation(new APostHandler() {
                @Override
                public void postAction() {
                    if (soundEffectsSetting.equals("enabled")) {
                        ASound showSound = new ASound("click_1.wav", false);
                        showSound.Play();
                    }

                }
            });
            dashboardUI.getCarousel().MoveRight();

        }
    }

    /**
     * .-----------------------------------------------------------------------.
     * | DashboardlKeyListener
     * .-----------------------------------------------------------------------.
     * |
     * | This Keyboard Listener is supposed to listen to a keyboard press
     * | to find out if the following is pressed to do the following:
     * |
     * | - Right Key >>> Move Carousel to the Right 1
     * | - Left Key >>> Move Carousel to the Left 1
     * | - Escape Key >>> Launch Exit Dialog
     * | - Enter Key >>> Launch the current Carousel Pane in the center
     * |
     */
    public class DashboardlKeyListener extends KeyAdapter {

        @Override
        public final void keyPressed(final KeyEvent e) {

            AuroraStorage storage = dashboardUI.getStorage();
            final String soundEffectsSetting = storage.getStoredSettings()
                    .getSettingValue("sound_effects");

            /*
             * More responsive put here
             */

            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                dashboardUI.getCarousel()
                        .setPostRightAnimation(new APostHandler() {
                            @Override
                            public void postAction() {
                                if (soundEffectsSetting.equals("enabled")) {
                                    ASound showSound = new ASound("click_1.wav",
                                            false);
                                    showSound.Play();
                                }
                            }
                        });
                dashboardUI.getCarousel().MoveRight();
            }

            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

                dashboardUI.getCarousel()
                        .setPostLeftAnimation(new APostHandler() {
                            @Override
                            public void postAction() {
                                if (soundEffectsSetting.equals("enabled")) {
                                    ASound showSound = new ASound("click_3.wav",
                                            false);
                                    showSound.Play();
                                }
                            }
                        });
                dashboardUI.getCarousel().MoveLeft();

            }

            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                dashboardUI.getCoreUI().showExitDialog();
            }
        }

        @Override
        public final void keyReleased(final KeyEvent e) {

            if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                //*
                // Check which Pane is current Center pane. Then Launch
                // the appropriate AuroraApp assosiated to that Center Pane
                //*

                ACarouselPane pane = dashboardUI.getCarousel().getCenterPane();

                dashboardLogic.launchAuroraApp(pane);

            }

        }
    }

    /**
     * .-----------------------------------------------------------------------.
     * | CarouselLibraryMouseListener
     * .-----------------------------------------------------------------------.
     * |
     * | This Mouse Listener is attached to the Game Icon specifically for the
     * | Library Pane to allow clicking on the Game Icon to launch the Library:
     * |
     * |
     */
    public class CarouselLibraryMouseListener extends MouseAdapter {

        @Override
        public final void mouseClicked(final MouseEvent e) {

            if (logger.isDebugEnabled()) {
                logger.debug("CLICKED LIBRARY MOUSE LISTENER");
            }

            if (dashboardUI != null) {
                dashboardLogic.navigateCarousel(dashboardUI.getLibraryPane());
            }
        }
    }

    /**
     * .-----------------------------------------------------------------------.
     * | CarouselPaneMouseListener
     * .-----------------------------------------------------------------------.
     * |
     * | This Mouse Listener handles the Mouse Navigation of the Carousel
     * | It is Attached to Each Carousel Pane
     * |
     * |
     */
    public class CarouselPaneMouseListener extends MouseAdapter {

        /**
         * Temporary Instance of a CarouselPane.
         */
        private ACarouselPane pane;

        /**
         * .-------------------------------------------------------------------.
         * | CarouselPaneMouseListener(ACarouselPane)
         * .-------------------------------------------------------------------.
         * |
         * | This Constructor allows for a Custom ACarouselPane to be passed to
         * | be used as reference for navigating the Carousel.
         * | if a null value is passed then CarouselPaneMouseListener will use
         * | the MouseEvent source to determine what Carousel Pane was selected.
         * |
         * .....................................................................
         * <p/>
         * @param aCarouselPane ACarouselPane
         */
        public CarouselPaneMouseListener(final ACarouselPane aCarouselPane) {

            if (aCarouselPane != null) {
                pane = aCarouselPane;
                System.out.println(pane);
            }
        }

        @Override
        public final void mouseClicked(final MouseEvent e) {

            if (logger.isDebugEnabled()) {
                logger.debug("CLICKED CAROUSEL PANE");
            }

            if (e.getSource() != null && e.getSource() instanceof ACarouselPane) {
                pane = (ACarouselPane) e.getSource();
            }

            if (logger.isDebugEnabled()) {
                logger.debug(pane);
                logger.debug(e.getComponent());
            }

            dashboardLogic.navigateCarousel(pane);

        }
    }

    /**
     * .-----------------------------------------------------------------------.
     * | CarouselPaneMouseWheelListener
     * .-----------------------------------------------------------------------.
     * |
     * | This Mouse Wheel Listener is attached to The Major CoreUI components
     * | As well as The Carousel in DashboardUI to allow for scrolling
     * | navigation of the carousel by allowing the carousel to move Right or
     * | Left depending on the Direction of the scroll.
     * |
     */
    public class CarouselPaneMouseWheelListener implements MouseWheelListener {

        @Override
        public final void mouseWheelMoved(final MouseWheelEvent e) {

            AuroraStorage storage = dashboardUI.getStorage();
            String soundEffectsSetting = storage.getStoredSettings()
                    .getSettingValue("sound_effects");
            if (soundEffectsSetting == null) {
                soundEffectsSetting = SettingsLogic.DEFAULT_SFX_SETTING;
            }

            int numberClicks = e.getWheelRotation();

            if (logger.isDebugEnabled()) {
                logger.debug("Mouse wheel moved " + numberClicks);
            }

            if (numberClicks < 0) {
                if (soundEffectsSetting.equals("enabled")) {
                    dashboardUI.getCarousel()
                            .setPostLeftAnimation(new APostHandler() {
                                @Override
                                public void postAction() {

                                    ASound showSound = new ASound("click_3.wav",
                                            false);
                                    showSound.Play();

                                }
                            });
                }
                dashboardUI.getCarousel().MoveLeft();
            } else if (numberClicks > 0) {
                if (soundEffectsSetting.equals("enabled")) {
                    dashboardUI.getCarousel()
                            .setPostRightAnimation(new APostHandler() {
                                @Override
                                public void postAction() {

                                    ASound showSound = new ASound("click_1.wav",
                                            false);
                                    showSound.Play();
                                }

                            });
                }
                dashboardUI.getCarousel().MoveRight();
            }

        }
    }
}
