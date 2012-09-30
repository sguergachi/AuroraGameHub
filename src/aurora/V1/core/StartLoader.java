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

import aurora.engine.V1.UI.aImage;
import aurora.engine.V1.UI.aImagePane;
import aurora.engine.V1.UI.aProgressWheel;
import aurora.engine.V1.UI.aScrollingImage;
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
    private aScrollingImage HexPanes;
    private int Scale = 0;
    private double smallScale = 0;
    private Graphics g;
    private Graphics2D g2d;
    private aImagePane HeaderPanel;
    private JLabel logo;
    private aImage ImgSlim;
    private JPanel FramePane;
    private JPanel CenterPane;
    private int TopHeight;
    private int CenterHeight;
    private JFrame Frame;
    private Aurora_StartUp StartUp_Obj;
    private AuroraCoreUI ui;
    private aProgressWheel progress;
    private Aurora_Dashboard mainWin;
    private final int SIZE_TopHeight;
    private final int SIZE_ImageWidth;
    private final int SIZE_ImageHeight;

    public StartLoader(AuroraCoreUI AUI, Aurora_StartUp Obj) {
        this.StartUp_Obj = Obj;
        this.ui = AUI;
        HexPanes = StartUp_Obj.getHexAnimation();
        HeaderPanel = ui.getPnlTop();
        this.CenterPane = ui.getPnlCenter();
        this.logo = ui.getImgLogo();
        Frame = ui.getFrame();
        FramePane = ui.getPnlSouthFromTop();

        SIZE_TopHeight = ui.getPnlCenter().getHeight() / 8;
        SIZE_ImageHeight = SIZE_TopHeight / 2 + 20;
        SIZE_ImageWidth = ui.getFrame().getWidth() / 2 + 20;

        ImgSlim = new aImage("Aurora_Header2.png");
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
        CenterPane.add(BorderLayout.PAGE_START, FramePane);

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
            HeaderPanel.setPreferredSize(new Dimension(HeaderPanel.getWidth(), TopHeight - 50));
            HeaderPanel.setImageHeight(TopHeight);
            CenterPane.setPreferredSize(new Dimension(CenterPane.getWidth(), CenterHeight));

            HexPanes.revalidate();

            //5. Stop When Hex Panels Large
            if (Scale == 35) {
                //Scale = Scale - 1;
                pause();
                try {
                    try {
                        try {
                            transition();
                        } catch (FontFormatException ex) {
                            Logger.getLogger(StartLoader.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (InvocationTargetException ex) {
                        Logger.getLogger(StartLoader.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(StartLoader.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(StartLoader.class.getName()).log(Level.SEVERE, null, ex);
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(StartLoader.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(StartLoader.class.getName()).log(Level.SEVERE, null, ex);
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

    private void transition() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException, InvocationTargetException, FontFormatException {
        System.out.println(CenterHeight);
        System.out.println(CenterPane.getWidth());
        progress = new aProgressWheel("Aurora_wheel.png");
        ui.getPnlTop().add(BorderLayout.PAGE_END, ui.getPnlSouthFromTop());
        ui.getPnlCenter().removeAll();

        ui.getFrame().add(progress);
        ui.getFrame().repaint();
        mainWin = new Aurora_Dashboard(this, ui, StartUp_Obj);
        loadDashboard load = new loadDashboard();

    }

    class loadDashboard implements Runnable {

        private Thread loadDashThread;

        public loadDashboard() {

            mainWin.loadGUI();

            //Loading Thread
            loadDashThread = null;

            if (loadDashThread == null) {
                loadDashThread = new Thread(this);
            }
            loadDashThread.setName("load Dashboard Thread");
            //Start Loader
            loadDashThread.start();
        }

        @Override
        public void run() {

            if (Thread.currentThread() == loadDashThread) {


                try {
                    mainWin.buildGUI();
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(StartLoader.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(StartLoader.class.getName()).log(Level.SEVERE, null, ex);
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(StartLoader.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(StartLoader.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FontFormatException ex) {
                    Logger.getLogger(StartLoader.class.getName()).log(Level.SEVERE, null, ex);
                }
                mainWin.setObject(mainWin);

                //Remove from memory
                System.gc();

                FramePane.setVisible(true);
            }
            ui.getFrame().remove(progress);
        }
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

    public aImagePane getHeaderPanel() {
        return HeaderPanel;
    }

    public aScrollingImage getHexPanes() {
        return HexPanes;
    }

    public aImage getImgSlim() {
        return ImgSlim;
    }

    public int getScale() {
        return Scale;
    }

    public Aurora_StartUp getStartUp_Obj() {
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

    public aProgressWheel getProgress() {
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
            Logger.getLogger(StartLoader.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}