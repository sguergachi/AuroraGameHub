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
package aurora.V1.core.screen_logic;

import aurora.V1.core.AuroraCoreUI;
import aurora.V1.core.screen_handler.StartScreenHandler;
import aurora.V1.core.screen_ui.DashboardUI;
import aurora.V1.core.screen_ui.StartScreenUI;
import aurora.engine.V1.Logic.AThreadWorker;
import aurora.engine.V1.Logic.AuroraScreenHandler;
import aurora.engine.V1.Logic.AuroraScreenLogic;
import aurora.engine.V1.UI.AImage;
import aurora.engine.V1.UI.AImagePane;
import aurora.engine.V1.UI.AProgressWheel;
import aurora.engine.V1.UI.AScrollingImage;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class StartScreenLogic implements AuroraScreenLogic {

    private final StartScreenUI startScreenUI;

    private StartScreenHandler startHandler;

    private final AuroraCoreUI coreUI;

    private JPanel bottomOfTopPane;

    private AScrollingImage imgHexPane;

    private AImagePane headerPane;

    private JPanel centerPane;

    private AImage imgTopLogo;

    private int topHeight;

    private AImage imgTopLogoSmall;

    private int topSmallImageHeight;

    private int topSmallImageWidth;

    private AThreadWorker animateTransision;

    private int centerHeight;

    private DashboardUI dashboardUI;

    private AProgressWheel progressWheel;

    public StartScreenLogic(StartScreenUI aStartScreenUI) {

        this.startScreenUI = aStartScreenUI;
        this.coreUI = startScreenUI.getCoreUI();

    }

    @Override
    public void setHandler(AuroraScreenHandler handler) {
        startHandler = (StartScreenHandler) handler;
    }

    private void loadUI() {



        bottomOfTopPane = coreUI.getSouthFromTopPanel();
        headerPane = coreUI.getTopPane();
        centerPane = coreUI.getCenterPanel();

        imgHexPane = startScreenUI.getImgHexPane();
        imgTopLogo = coreUI.getLogoImage();
        imgTopLogoSmall = new AImage("Aurora_Header2.png");


        setSize();

        imgTopLogoSmall.setImageSize(topSmallImageWidth, topSmallImageHeight);



    }

    public void transisionToDashboard() {

        loadUI();

        animateTransision = new AThreadWorker(new ActionListener() {
            //* Times cycling through threadWorker loop *//
            private int c = 0;
            //* Scale of Hex Image growning *//

            private int scale = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                c++;
                if (c == 1) {
                    //* change header logo to smaller logo *//
                    imgTopLogo.setIcon(imgTopLogoSmall.getImgIcon());
                    imgTopLogo.repaint();

                    //* stop scrolling Animation *//
                    imgHexPane.stop();

                    //* Remove Panel Containing Frame Controls*//
                    headerPane.remove(bottomOfTopPane);
                    headerPane.revalidate();

                    imgHexPane.setCenterToFrame(coreUI.getFrame());
                    imgHexPane.repaint();

                } else {
                    //* Change Size Values *//
                    scale++;
                    topHeight--;
                    centerHeight += 5;

                    //* Change Component Sizes *//
                    imgHexPane.grow(scale);
                    headerPane.setImageHeight(topHeight);
                    imgHexPane.repaint();
                    imgHexPane.revalidate();

                    headerPane.setPreferredSize(new Dimension(headerPane
                            .getWidth(),
                            topHeight - 50));
                    centerPane.setPreferredSize(new Dimension(centerPane
                            .getWidth(),
                            centerHeight));




                    if (topHeight >= imgTopLogoSmall.getImgIcon()
                            .getIconHeight() + 300) {
                        centerHeight = centerHeight - 2;
                        topHeight = imgTopLogoSmall.getImgIcon().getIconHeight()
                                    + 50;
                        headerPane.setImageHeight(topHeight);

                    }

                    //* Check if Reached Proper Size to stop *//
                    if (scale == 34) {

                        showDashdoard();
                        animateTransision.stop();
                    }

                }


            }
        }, 18);

        animateTransision.start();

    }

    private void showDashdoard() {
        //* Show loading of Dashboard *//
        progressWheel = new AProgressWheel("Aurora_wheel.png");
        coreUI.getFrame().add(progressWheel);
        coreUI.getFrame().repaint();


        //* Re-add Frame Controls *//
        bottomOfTopPane.setVisible(true);
        coreUI.getTopPane().add(BorderLayout.PAGE_END, coreUI
                .getSouthFromTopPanel());
        coreUI.getTopPane().revalidate();

        //* Remove all components in Center Panel *//
        coreUI.getCenterPanel().removeAll();

        //* Get or Generate new DashboardUI *//
        dashboardUI = startScreenUI.getLoadedDashboardUI();

        if (dashboardUI == null) {
            System.out.println("Creating New Dashboard");
            dashboardUI = new DashboardUI(coreUI, startScreenUI);
            dashboardUI.loadUI();
        } else {
            System.out.println("Using LOADED Dashboard");
        }


        //* Remove garbage from Memory *//
        System.gc();

        //* Build DashboardUI *//
        dashboardUI.buildUI();

        //* remove progressWheel to finilze *//
        coreUI.getFrame().remove(progressWheel);
    }

    private void setSize() {

        topHeight = headerPane.getHeight();
        centerHeight = centerPane.getHeight() + 60;
        topSmallImageHeight = coreUI.getCenterPanel().getHeight() / 16 + 20;
        topSmallImageWidth = coreUI.getFrame().getWidth() / 2 + 20;

    }
}
