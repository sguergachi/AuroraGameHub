/*
 * Copyright 2012 Sardonix Creative.
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

import aurora.V1.core.screen_ui.DashboardUI;
import aurora.V1.core.screen_ui.GameLibraryUI;
import aurora.V1.core.screen_ui.GamerProfileUI;
import aurora.V1.core.screen_ui.SettingsUI;
import aurora.engine.V1.UI.aCarouselPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class DashboardHandler {

    private final DashboardUI dashboardUI;

    public DashboardHandler(DashboardUI dashboardUI) {
        this.dashboardUI = dashboardUI;
    }

    public class RightListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            dashboardUI.getCarousel().MoveLeft();


        }
    }

    public class LeftListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            dashboardUI.getCarousel().MoveRight();

        }
    }

    public class CarouselKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {


            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                dashboardUI.getCarousel().MoveRight();
            }

            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                dashboardUI.getCarousel().MoveLeft();
            }

            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                dashboardUI.getCoreUI().showExitDialog();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                aCarouselPane pane = dashboardUI.getCarousel().getCenterPane();

                if (pane == dashboardUI.getLibraryPane()) {
                    //action on click right Panel
                    GameLibraryUI libraryUI = new GameLibraryUI(dashboardUI
                            .getStartUI().getAuroraStorage(), dashboardUI,
                            dashboardUI.getCoreUI());
                    libraryUI.loadUI();
                } else if (pane == dashboardUI.getProfilePane()) {
                    GamerProfileUI profileUI = new GamerProfileUI(dashboardUI,
                            dashboardUI.getCoreUI());
                    profileUI.loadUI();
                } else if (pane == dashboardUI.getSettingsPane()) {
                    SettingsUI settingsUI = new SettingsUI(dashboardUI,
                            dashboardUI.getCoreUI());
                    settingsUI.loadUI();
                } else if (pane == dashboardUI.getAuroraNetPane()) {
                    // do nothing for now
                }
            }


        }
    }

    public class CarouselLibraryMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("CLICKED");
            if (dashboardUI != null) {
                GameLibraryUI libraryUI = new GameLibraryUI(dashboardUI
                        .getStartUI().getAuroraStorage(), dashboardUI,
                        dashboardUI.getCoreUI());
                libraryUI.loadUI();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    ///....Mouse Click Handlers...////////
    public class CarouselPaneMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("CLICKED");
            aCarouselPane pane = (aCarouselPane) e.getComponent();

            if (pane.getPointX() == dashboardUI.getCarousel().getRightX()) {
                dashboardUI.getCarousel().MoveLeft();
            } else if (pane.getPointX() == dashboardUI.getCarousel().getLeftX()) {
                dashboardUI.getCarousel().MoveRight();
            } else if (pane.getPointX() == dashboardUI.getCarousel().getCentX()) {
                if (pane == dashboardUI.getLibraryPane()) {
                    //action on click right Panel
                    if (dashboardUI != null) {
                        GameLibraryUI libraryUI = new GameLibraryUI(dashboardUI
                                .getStartUI().getAuroraStorage(), dashboardUI,
                                dashboardUI.getCoreUI());
                        libraryUI.loadUI();
                    }
                } else if (pane == dashboardUI.getProfilePane()) {
                    GamerProfileUI profileUI = new GamerProfileUI(dashboardUI,
                            dashboardUI.getCoreUI());
                    profileUI.loadUI();
                } else if (pane == dashboardUI.getSettingsPane()) {
                    SettingsUI settingsUI = new SettingsUI(dashboardUI,
                            dashboardUI.getCoreUI());
                    settingsUI.loadUI();
                } else if (pane == dashboardUI.getAuroraNetPane()) {
                    // do nothing for now
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    public class carouselPaneMouseWheelListener implements MouseWheelListener {

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {


            int numberClicks = e.getWheelRotation();
            System.out.println("Mouse wheel moved " + numberClicks);

            if (numberClicks < 0) {
                dashboardUI.getCarousel().MoveLeft();
            } else if (numberClicks > 0) {
                dashboardUI.getCarousel().MoveRight();
            }

        }
    }
}
