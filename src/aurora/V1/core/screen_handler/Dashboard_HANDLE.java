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

import aurora.V1.core.screen_ui.Dashboard_UI;
import aurora.V1.core.screen_ui.GameLibrary_UI;
import aurora.V1.core.screen_ui.GamerProfile_UI;
import aurora.V1.core.screen_ui.Settings_UI;
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
public class Dashboard_HANDLE {

    private final Dashboard_UI ui;

    public Dashboard_HANDLE(Dashboard_UI ui) {
        this.ui = ui;
    }

    public class RightListener implements ActionListener {


        public void actionPerformed(ActionEvent e) {

            ui.getCarousel().MoveLeft();


        }
    }

    public class LeftListener implements ActionListener {



        @Override
        public void actionPerformed(ActionEvent e) {

            ui.getCarousel().MoveRight();

        }
    }

    public class CarouselKeyListener implements KeyListener {


        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {


            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                ui.getCarousel().MoveRight();
            }

            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                ui.getCarousel().MoveLeft();
            }

            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                ui.getCoreUI().showExitDilog();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                aCarouselPane pane = ui.getCarousel().getCenterPane();

                if (pane == ui.getLibraryPane()) {
                    //action on click right Panel
                    GameLibrary_UI library = new GameLibrary_UI(ui.getStartUp_Obj().getAuroraStorage(), ui, ui.getCoreUI());
                    library.createGUI();
                } else if (pane == ui.getProfilePane()) {
                    GamerProfile_UI aObj_profile = new GamerProfile_UI(ui, ui.getCoreUI());
                    aObj_profile.createGUI();
                } else if (pane == ui.getSettingsPane()) {
                    Settings_UI aObj_settings = new Settings_UI(ui, ui.getCoreUI());
                    aObj_settings.createGUI();
                } else if (pane == ui.getAuroraNetPane()) {
                    // do nothing for now
                }
            }


        }
    }

    public class CarouselLibraryMouseListener implements MouseListener {


        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("CLICKED");
            if (ui != null) {
                GameLibrary_UI library = new GameLibrary_UI(ui.getStartUp_Obj().getAuroraStorage(), ui, ui.getCoreUI());
                library.createGUI();
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

            if (pane.getPointX() == ui.getCarousel().getRightX()) {
                ui.getCarousel().MoveLeft();
            } else if (pane.getPointX() == ui.getCarousel().getLeftX()) {
                ui.getCarousel().MoveRight();
            } else if (pane.getPointX() == ui.getCarousel().getCentX()) {
                if (pane == ui.getLibraryPane()) {
                    //action on click right Panel
                    if (ui != null) {
                        GameLibrary_UI library = new GameLibrary_UI(ui.getStartUp_Obj().getAuroraStorage(), ui, ui.getCoreUI());
                        library.createGUI();
                    }
                } else if (pane == ui.getProfilePane()) {
                    GamerProfile_UI aObj_profile = new GamerProfile_UI(ui, ui.getCoreUI());
                    aObj_profile.createGUI();
                } else if (pane == ui.getSettingsPane()) {
                    Settings_UI aObj_settings = new Settings_UI(ui, ui.getCoreUI());
                    aObj_settings.createGUI();
                } else if (pane == ui.getAuroraNetPane()) {
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
                ui.getCarousel().MoveLeft();
            } else if (numberClicks > 0) {
                ui.getCarousel().MoveRight();
            }

        }
    }
}
