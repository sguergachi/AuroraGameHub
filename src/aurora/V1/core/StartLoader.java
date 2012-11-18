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
package aurora.V1.core;

import aurora.V1.core.screen_ui.DashboardUI;
import aurora.V1.core.screen_ui.StartScreenUI;
import aurora.engine.V1.UI.AImage;
import aurora.engine.V1.UI.AImagePane;
import aurora.engine.V1.UI.AProgressWheel;
import aurora.engine.V1.UI.AScrollingImage;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 *
 * @author Sammy
 */
public class StartLoader implements Runnable {

    private Thread runner;

    private AScrollingImage HexPanes;

    private int Scale = 0;

    private double smallScale = 0;

    private Graphics g;

    private Graphics2D g2d;

    private AImagePane HeaderPanel;

    private JLabel logo;

    private AImage ImgSlim;

    private JPanel FramePane;

    private JPanel CenterPane;

    private int TopHeight;

    private int CenterHeight;

    private JFrame Frame;

    private StartScreenUI StartUp_Obj;

    private AuroraCoreUI ui;

    private AProgressWheel progress;

    private DashboardUI mainWin;

    private final int SIZE_TopHeight;

    private final int SIZE_ImageWidth;

    private final int SIZE_ImageHeight;

    public StartLoader(AuroraCoreUI AUI, StartScreenUI Obj) {
        this.StartUp_Obj = Obj;
        this.ui = AUI;
        HexPanes = StartUp_Obj.getHexAnimation();
        HeaderPanel = ui.getTopImagePane();
        this.CenterPane = ui.getCenterPanel();
        this.logo = ui.getLogoImage();
        Frame = ui.getFrame();
        FramePane = ui.getSouthFromTopPanel();

        SIZE_TopHeight = ui.getCenterPanel().getHeight() / 8;
        SIZE_ImageHeight = SIZE_TopHeight / 2 + 20;
        SIZE_ImageWidth = ui.getFrame().getWidth() / 2 + 20;

        ImgSlim = new AImage("Aurora_Header2.png");
        ImgSlim.setImageSize(SIZE_ImageWidth, SIZE_ImageHeight);
    }

    public void transitionToMain() {
        if (runner == null) {
            runner = new Thread(this);
            runner.setName("Animator Thread");
            //pre. Change Header Image
            logo.setIcon(ImgSlim.getImgIcon());
            logo.repaint();

            runner.start();
        }

    }

    @Override
    public void run() {

        // 1. Stop Image from moving
        HexPanes.stop();

        //pre set sizes
        TopHeight = HeaderPanel.getHeight();
        CenterHeight = CenterPane.getHeight() + 60;

        //pre reset Control Buttons
        FramePane.setVisible(false);
        HeaderPanel.remove(FramePane);
        CenterPane.add(BorderLayout.EAST, FramePane);

        HexPanes.Center(ui.getFrame());
        HexPanes.pause();
        while (runner == Thread.currentThread()) {


            //2. Increase Size\\
            smallScale = smallScale + 0.5;
            Scale++;

            HexPanes.grow(Scale);
            //3. reposition Hex Image\\


            HexPanes.repaint();

            //4 . Change Top Panel Height\\

            TopHeight--;
            CenterHeight += 5;
            HeaderPanel.setPreferredSize(new Dimension(HeaderPanel.getWidth(),
                    TopHeight - 50));
            HeaderPanel.setImageHeight(TopHeight);
            CenterPane.setPreferredSize(new Dimension(CenterPane.getWidth(),
                    CenterHeight));

            HexPanes.revalidate();

            //5. Stop When Hex Panels Large
            if (Scale == 35) {
                pause();
                try {
                    try {
                        try {
                            transition();
                        } catch (FontFormatException ex) {
                            Logger.getLogger(StartLoader.class.getName()).
                                    log(Level.SEVERE, null, ex);
                        }
                    } catch (InvocationTargetException ex) {
                        Logger.getLogger(StartLoader.class.getName()).log(
                                Level.SEVERE, null, ex);
                    }
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(StartLoader.class.getName()).log(
                            Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(StartLoader.class.getName()).log(
                            Level.SEVERE, null, ex);
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(StartLoader.class.getName()).log(
                            Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(StartLoader.class.getName()).log(
                            Level.SEVERE, null, ex);
                }

                break;
            } //6. Stop Image Height shrink
            if (TopHeight >= ImgSlim.getImgIcon().getIconHeight() + 300) {
                CenterHeight = CenterHeight - 2;
                TopHeight = ImgSlim.getImgIcon().getIconHeight() + 50;
                HeaderPanel.setImageHeight(TopHeight);
                pause();

            } else {
                pause();
            }
        }
        runner = null;
    }

    private void transition() throws UnsupportedAudioFileException, IOException,
                                     LineUnavailableException,
                                     InterruptedException,
                                     InvocationTargetException,
                                     FontFormatException {
        System.out.println(CenterHeight);
        System.out.println(CenterPane.getWidth());
        progress = new AProgressWheel("Aurora_wheel.png");
        ui.getTopImagePane().add(BorderLayout.PAGE_END, ui.getSouthFromTopPanel());
        ui.getCenterPanel().removeAll();

        ui.getFrame().add(progress);
        ui.getFrame().repaint();
        mainWin = new DashboardUI(ui, StartUp_Obj);
        loadDashboard();
    }

    public void loadDashboard() {

        mainWin.loadUI();
        mainWin.buildUI();


        //Remove from memory
        System.gc();

        FramePane.setVisible(true);
        ui.getFrame().remove(progress);


    }

    public int getCenterHeight() {
        return CenterHeight;
    }

    public JPanel getCenterPane() {
        return CenterPane;
    }

    public JFrame getFrame() {
        return Frame;
    }

    public JPanel getFramePane() {
        return FramePane;
    }

    public AImagePane getHeaderPanel() {
        return HeaderPanel;
    }

    public AScrollingImage getHexPanes() {
        return HexPanes;
    }

    public AImage getImgSlim() {
        return ImgSlim;
    }

    public int getScale() {
        return Scale;
    }

    public StartScreenUI getStartUp_Obj() {
        return StartUp_Obj;
    }

    public int getTopHeight() {
        return TopHeight;
    }

    public Graphics getG() {
        return g;
    }

    public Graphics2D getG2d() {
        return g2d;
    }

    public JLabel getLogo() {
        return logo;
    }

    public AProgressWheel getProgress() {
        return progress;
    }

    public Thread getRunner() {
        return runner;
    }

    public double getSmallScale() {
        return smallScale;
    }

    public AuroraCoreUI getUi() {
        return ui;
    }

    private void pause() {
        try {
            Thread.sleep(16);
        } catch (InterruptedException ex) {
            Logger.getLogger(StartLoader.class.getName()).
                    log(Level.SEVERE, null, ex);
        }

    }
}
