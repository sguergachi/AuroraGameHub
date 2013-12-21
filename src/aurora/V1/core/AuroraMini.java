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

import aurora.engine.V1.UI.AButton;
import aurora.engine.V1.UI.AImage;
import aurora.engine.V1.UI.AImagePane;
import aurora.engine.V1.UI.AProgressWheel;
import aurora.engine.V1.UI.ASlickLabel;
import com.sun.awt.AWTUtilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.RoundRectangle2D;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import org.apache.log4j.Logger;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class AuroraMini {

    private JFrame mini;

    private AuroraCoreUI coreUI;

    private AImagePane pnlBackground;

    private AImage icon;

    private JPanel pnlIconPane;

    private Point location;

    private MouseEvent pressed;

    private Timer timer;

    public static Boolean isIconHover = true;

    private String mode;

    private int yPos;

    private ASlickLabel lblStatus;

    private JPanel pnlStatus;

    private boolean isDrag = false;

    public static Boolean isMinimode = false;

    public static final String LOADING_MODE = "startup";

    public static final String MINIMIZE_MODE = "minimize";

    private AButton close;

    private boolean firstClick;

    private PaneAnimateHandler animationHander;

    static final Logger logger = Logger.getLogger(AuroraMini.class);

    public AuroraMini(AuroraCoreUI ui, String mode) {
        this.coreUI = ui;
        this.mode = mode;
    }

    public void setStatus(String status) {

        lblStatus.setText(status);
    }

    public void setMode(String mode) {
        this.mode = mode;
        executeMode();
    }

    public void createUI() {
        isMinimode = true;
        if (mini == null) { // retain state

            //SET UP FRAME
            mini = new JFrame("Aurora Minimized");

            mini.setUndecorated(true);
            mini.setBackground(Color.BLACK);
            mini.setResizable(false);
            mini.setSize(300, 65);
            mini.setLocation(coreUI.getScreenWidth() - 65, coreUI
                    .getScreenHeight()
                                                           - 160);

            mini.setAlwaysOnTop(true);

            //SET FRAME ICON
            try {
                mini.setIconImage(new ImageIcon(new URL(coreUI.getResource()
                        .getSurfacePath() + "/aurora/V1/resources/icon.png"))
                        .getImage());
            } catch (MalformedURLException ex) {
                try {

                    mini.setIconImage(new ImageIcon(getClass().getResource(
                            "/aurora/V1/resources/icon.png")).getImage());

                } catch (Exception exx) {
                    logger.fatal(exx);
                }
            }


            //SET UP BACKGROUND
            pnlBackground = new AImagePane("app_miniMode_bg.png", mini
                    .getWidth(), mini.getHeight(),
                    new FlowLayout(FlowLayout.LEFT, 0, 0));
            pnlBackground.setPreferredSize(mini.getSize());

            //CREATE
            icon = new AImage("icon.png");
            icon.addMouseListener(new IconHoverHandler());

            pnlIconPane = new JPanel(new BorderLayout(0, 0));
            pnlIconPane.setOpaque(false);
            pnlIconPane.setPreferredSize(new Dimension(icon.getImgWidth() + 5,
                    mini.getHeight()));


            lblStatus = new ASlickLabel();
            lblStatus.setFont(coreUI.getDefaultFont().deriveFont(Font.BOLD, 30));
            lblStatus.setForeground(Color.LIGHT_GRAY);

            pnlStatus = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
            pnlStatus.setOpaque(false);

            close = new AButton("app_btn_close_norm.png",
                    "app_btn_close_down.png", "app_btn_close_over.png");

            //ADD HANDLERS//

            //BACKGROUND IMAGE CHANGE ON HOVER
            pnlBackground.addMouseListener(new PanelHoverHandler());
            icon.addMouseListener(new PanelHoverHandler());
            close.addMouseListener(new PanelHoverHandler());



            //DRAG VERTICAL FUNCTIONALITY
            pnlBackground.addMouseMotionListener(new IconPaneMotionLister());
            pnlBackground.addMouseListener(new IconPaneMouseListener());

            icon.addMouseListener(new IconPaneMouseListener());
            icon.addMouseMotionListener(new IconPaneMotionLister());


            //ANIMATING POP OUT
            animationHander = new PaneAnimateHandler();
            pnlBackground.addMouseListener(animationHander);
            close.addMouseListener(animationHander);

            //CLOSE BUTTON HANDER
            close.addActionListener(new CloseButtonHander());

            //ADD COMPONENTS TO OTHER COMPONENTS
            pnlIconPane.add(icon, BorderLayout.EAST);
            pnlStatus.add(lblStatus);

            pnlBackground.add(pnlIconPane);
            pnlBackground.add(pnlStatus);
            mini.getContentPane().add(pnlBackground);
        }
        mini.setVisible(true);
        try {
            AWTUtilities.setWindowShape(mini, new RoundRectangle2D.Double(0, 0,
                    mini
                    .getWidth(), mini.getHeight(), 30,
                    27));
        } catch (UnsupportedOperationException ex) {
            logger.fatal("System does not support Shapes");
        }

        executeMode();
        mini.requestFocusInWindow();

    }

    private void executeMode() {
        icon.setImgURl("icon.png");

        if (mode.equals("minimize")) {
            mini.setLocation((coreUI.getScreenWidth() - 70) - 170, mini.getY()); //pop out
            animateIN();                                                          //Animate in
            lblStatus.setText(" READY");

            //Remove progress wheel if it exists
            if (pnlBackground.getComponentCount() == 4) {
                pnlBackground.remove(3);
                pnlBackground.remove(2);
            } else if (pnlBackground.getComponentCount() == 3) {
                pnlBackground.remove(2);
            }
            mini.repaint();
            pnlBackground.add(close);

        } else if (mode.equals("startup")) {
            mini.setLocation((coreUI.getScreenWidth() - 70) - 150, mini.getY());
            animateIN();
            lblStatus.setText("LOADING");
            pnlBackground.add(new AProgressWheel("Aurora_wheel.png"));
        }

    }

    //Make Mini Slide in to screen
    private void animateIN() {
        if (timer != null) {
            timer.stop();
        }
        timer = null;
        timer = new Timer(20, new AnimateINFrameListener());
        timer.setInitialDelay(900);
        timer.setRepeats(true);

        lblStatus.setText(" READY");
        firstClick = true;

        timer.start();
    }

    //Make Mini pop out of screen
    private void animateOUT() {
        if (timer != null) {
            timer.stop();
        }
        timer = null;
        timer = new Timer(20, new AnimateOUTFrameListener());
        timer.setInitialDelay(200);
        timer.setRepeats(true);
        timer.start();
    }

    private class CloseButtonHander implements ActionListener {

        public CloseButtonHander() {
            firstClick = true;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (firstClick) {
                firstClick = false;
                lblStatus.setText(" EXIT? ");
            } else {
                coreUI.getFrame().getWindowListeners()[0].windowClosing(null);
                coreUI.getFrame().getWindowListeners()[0].windowClosed(null);
            }
        }
    }

    /*
     * ANIMAION Aurora Mini Slides Out Of The Screen
     */
    private class AnimateOUTFrameListener implements ActionListener {

        private int count = 0;

        public AnimateOUTFrameListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            count++; //Accelerator

            if (mini.getX() > (coreUI.getScreenWidth() - 75) - 170) {
                mini.setLocation(mini.getX() - 4 - count, mini.getY());
            } else {
                timer.stop();
            }
        }
    }

    /*
     * ANIMATION Aurora Mini Slides Into The Screen
     */
    private class AnimateINFrameListener implements ActionListener {

        private int count = 0;

        @Override
        public void actionPerformed(ActionEvent e) {

            count++; //Accelerator

            if (mini.getX() < (coreUI.getScreenWidth() - 75)) {                //Stop Here
                mini.setLocation(mini.getX() + 4 + count, mini.getY());         //Animate
            } else {
                timer.stop();
                if (coreUI.getOS().contains("Mac")) {
                    mini.setBackground(new Color(0f, 0f, 0f, 0.5f));
                } else {
                    AWTUtilities.setWindowOpacity(mini, 0.5f);
                }
            }

        }
    }

    private class PaneAnimateHandler implements MouseListener {

        public PaneAnimateHandler() {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {

            animateOUT();
            if (coreUI.getOS().contains("Mac")) {
                mini.setBackground(new Color(0f, 0f, 0f, 1.0f));
            } else {
                AWTUtilities.setWindowOpacity(mini, 1.0f);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {


            animateIN();

        }
    }

    private class IconPaneMotionLister implements MouseMotionListener {

        public IconPaneMotionLister() {
        }

        @Override
        public void mouseDragged(MouseEvent me) {
            location = mini.getLocation(location);
            // int x = location.x - pressed.getX() + me.getX();
            icon.setImgURl("app_miniMode_drag.png");

            yPos = location.y - pressed.getY() + me.getY();
            mini.setLocation(location.x, yPos);
            isDrag = true;
        }

        @Override
        public void mouseMoved(MouseEvent e) {
        }
    }

    private class IconPaneMouseListener implements MouseListener {

        private boolean isMouseExited;

        public IconPaneMouseListener() {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent me) {
            pressed = me;
        }

        @Override
        public void mouseReleased(MouseEvent e) {

            isDrag = false;
            if (logger.isDebugEnabled()) {
                logger.debug("Mouse Release");
            }
            if (isMouseExited) {
                icon.setImgURl("icon.png");
            } else {
                icon.setImgURl("app_miniMode_back.png");
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            isMouseExited = false;

            if (coreUI.getOS().contains("Mac")) {
                mini.setBackground(new Color(0f, 0f, 0f, 1.0f));
            } else {
                AWTUtilities.setWindowOpacity(mini, 1.0f);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            isMouseExited = true;
            icon.setImgURl("icon.png");
            if (coreUI.getOS().contains("Mac")) {
                mini.setBackground(new Color(0f, 0f, 0f, 0.5f));
            } else {
                AWTUtilities.setWindowOpacity(mini, 0.5f);
            }
        }
    }

    private class PanelHoverHandler implements MouseListener {

        public PanelHoverHandler() {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if (coreUI.getOS().contains("Mac")) {
                mini.setBackground(new Color(0f, 0f, 0f, 1.0f));
            } else {
                AWTUtilities.setWindowOpacity(mini, 1.0f);
            }

        }

        @Override
        public void mouseExited(MouseEvent e) {
            AWTUtilities.setWindowOpacity(mini, 0.5f);
            mini.setBackground(new Color(0f, 0f, 0f, 0.5f));
        }
    }

    //When Click On Back Button/Icon
    private class IconHoverHandler implements MouseListener {

        public IconHoverHandler() {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (!isDrag) {

                mini.setVisible(false);
                coreUI.getFrame().setLocation(coreUI.getFrame().getWidth(), 0);
                coreUI.getFrame().requestFocus();
                isMinimode = false;
                coreUI.getFrame().setVisible(true);
                coreUI.getFrame().setState(JFrame.NORMAL);
                coreUI.getFrame().repaint();

                try {
                    Thread.sleep(16);
                } catch (InterruptedException ex) {
                    logger.error(ex);
                }

                moveFrameIn();
            }

        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            icon.setImgURl("icon.png");
        }

        @Override
        public void mouseEntered(MouseEvent e) {

            if (timer != null) {
                timer.stop();
            }

            icon.setImgURl("app_miniMode_back.png");
        }

        @Override
        public void mouseExited(MouseEvent e) {

            if (timer != null && !timer.isRunning()) {
                timer.start();
            }

            icon.setImgURl("icon.png");
        }

        /**
         * .-------------------------------------------------------------------.
         * | moveFrameIn()
         * .-------------------------------------------------------------------.
         * |
         * | method to slide in Frame containing Aurora from the right
         * | frame needs to be visible and positioned to the right correctly
         * | this only does the moving. Uses Swing InvokeLater method
         * |
         * .....................................................................
         */
        private void moveFrameIn() {

            //* Don't Interrupt UI Thread *//
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    int acc = 0;
                    while (!(coreUI.getFrame().getLocation().x <= 0)) {

                        coreUI.getFrame().repaint();

                        //* accelerate ease in up to Half way*//
                        if (coreUI.getFrame().getLocation().x > coreUI
                                .getFrame()
                                .getWidth() / 2) {

                            acc++;
                            acc++;

                        } else if (coreUI.getFrame().getLocation().x <= coreUI
                                .getFrame()
                                .getWidth() / 4) {

                            //* Quarter way in eas in out. *//
                            if (!(acc <= 4)) {
                                acc--;
                                acc--;
                                acc--;
                            }
                        } else {
                            if (!(acc <= 4)) {
                                acc--;
                            }
                        }
                        System.out.println(acc);
                        //* move Frame from left smoothly *//
                        coreUI.getFrame()
                                .setBounds(coreUI.getFrame().getLocation().x
                                           - (3 + acc), 0, coreUI.getFrame()
                                .getWidth(), coreUI
                                .getFrame().getHeight());


                        //* 60fps *//
                        try {
                            Thread.sleep(16);
                        } catch (InterruptedException ex) {
                            logger.error(ex);
                        }

                    }

                    //* Reset to correct location *//
                    coreUI.getFrame()
                            .setBounds(0, 0, coreUI.getFrame()
                            .getWidth(), coreUI
                            .getFrame().getHeight());
                    coreUI.getFrame().requestFocusInWindow();
                }
            });

        }
    }
}
