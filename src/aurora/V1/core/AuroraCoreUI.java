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

import aurora.V1.core.screen_logic.StartScreenLogic;
import aurora.engine.V1.Logic.ANuance;
import aurora.engine.V1.Logic.ASound;
import aurora.engine.V1.Logic.ASurface;
import aurora.engine.V1.UI.AButton;
import aurora.engine.V1.UI.ACursor;
import aurora.engine.V1.UI.ADialog;
import aurora.engine.V1.UI.AImage;
import aurora.engine.V1.UI.AImagePane;
import aurora.engine.V1.UI.ATimeLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

/**
 * .------------------------------------------------------------------------.
 * | AuroraCoreUI
 * .------------------------------------------------------------------------.
 * |
 * | This is the Core UI class, it contains the persistent UI components
 * | that encompasses the main center UI
 * |
 * | It contains the surrounding frame which is to be manipulated and added
 * | to by Aurora Screens.
 * | This class is loaded when the first Aurora Screen is loaded.
 * |
 * |
 * .........................................................................
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 * @author Carlos Machado <camachado at gmail.com>
 * <p/>
 */
public class AuroraCoreUI {

    /**
     * Get revision based on Month Number and Day Number and Year Number.
     */
    private final String revision = ATimeLabel.current(ATimeLabel.DATE_NUM);

    /**
     * Generate full Version string to be used at the bottom of UI.
     */
    private final String version =
                         "             //BUILD: " + getResourceBundleToken(
            "BUILD")
                         + "  //REVISION: " + revision
                         + "  //AURORA.ENGINE.VERSION = 0.1." + (Integer
            .parseInt(revision));

    /**
     * Size Constant.
     */
    private int topPanelSize;

    /**
     * Size Constant.
     */
    private int centerPanelSize;

    /**
     * Size Constant.
     */
    private int bottomPanelSize;

    /**
     * Size Constant.
     */
    private int controlHeight;

    /**
     * Size Constant.
     */
    private int controlWidth;

    /**
     * Size Constant.
     */
    private int welcomeFontSize;

    /**
     * Size Constant.
     */
    private int keysFontSize;

    /**
     * Size Constant.
     */
    private int keyIconWidth;

    /**
     * Size Constant.
     */
    private int keyIconHeight;

    /**
     * Size Constant.
     */
    private int versionFontSize;

    /**
     * Size Constant.
     */
    private int timeFontSize;

    /**
     * Size Constant.
     */
    private int logoHeight;

    /**
     * Size Constant.
     */
    private int logoWidth;

    /**
     * Size Constant.
     */
    private int exitButtonWidth;

    /**
     * Size Constant.
     */
    private int exitButtonHeight;

    /**
     * Size Constant.
     */
    private int minimizeButtonWidth;

    /**
     * Size Constant.
     */
    private int minimizeButtonHeight;

    /**
     * Size Constant.
     */
    private int screenWidth;

    /**
     * Size Constant.
     */
    private int screenHeight;

    /**
     * Boolean for whether screen is of larger type or smaller type.
     */
    private boolean isLargeScreen;

    /**
     * Warning dialog window.
     */
    private ADialog warningDialog;

    /**
     * Error dialog window.
     */
    private ADialog errorDialog;

    /**
     * Logo image.
     */
    private AImage imgLogo;

    /**
     * Key icon image.
     */
    private AImage imgKeyIcon;

    /**
     * Background image pane.
     */
    private AImagePane paneBackground;

    /**
     * Bottom image pane.
     */
    private AImagePane paneBottom;

    /**
     * Panel that holds the back, minimize, exit
     * buttons.
     */
    private AImagePane paneFrameControl;

    /**
     * Top image.
     */
    private AImagePane paneTop;

    /**
     * Reference to the available Surface resources.
     */
    private ASurface resources;

    /**
     * Reference to the Nuance virtual intelligence.
     */
    private ANuance vi;

    /**
     * Regular font.
     */
    private Font regularFont;

    /**
     * Bold font.
     */
    private Font boldFont;

    /**
     * Exit button.
     */
    private JButton btnExit;

    /**
     * Minimize button.
     */
    private JButton btnMinimize;

    /**
     * The JFrame where the Core UI is placed on.
     */
    private JFrame frame;

    /**
     * Center panel that displays the main part of Aurora
     * ie. Dashboard, Library, etc.
     */
    private JPanel paneCenter;

    /**
     * Panel that holds the keys to press images and labels.
     */
    private JPanel paneKeyToPress;

    /**
     * Panel located south of the top panel.
     */
    private JPanel southFromTopPanel;

    /**
     * Panel that holds the Aurora logo.
     */
    private JPanel logoPanel;

    /**
     * Panel that holds the screen label.
     */
    private JPanel screenLabelPanel;

    /**
     * Panel that holds the current version of Aurora.
     */
    private JPanel versionPanel;

    /**
     * Header panel that is located in pnlCenterFromBottom.
     */
    private JPanel paneHeaderOfCenterFromBottom;

    /**
     * Panel that is located in the center of the
     * bottom panel.
     */
    private JPanel paneCenterFromBottom;

    /**
     * Panel that holds the current time.
     */
    private JPanel paneTime;

    /**
     * Panel that holds the app title.
     */
    private JPanel paneTitle;

    /**
     * The Container to the panel paneFrameControl.
     */
    private JPanel paneFrameControlContainer;

    /**
     *
     */
    private JPanel paneBottomCenterContent;

    /**
     * Label for the version panel.
     */
    private JLabel lblVersion;

    /**
     * Label for the title panel.
     */
    private JLabel lblTitle;

    /**
     * Label for the key image icon.
     */
    private JLabel lblKeyAction;

    /**
     * The instance of AuroraMini which activates when you minimize Aurora.
     */
    private AuroraMini miniMode;

    /**
     * Listener to listen for when Aurora is minimized.
     */
    private MinimizeListener minimizeHandler;

    /**
     * Label indicating the current time.
     */
    public static ATimeLabel lblTime;

    /**
     * Detects what is the current build number using the
     * version.properties file.
     */
    final static ResourceBundle resourceBundle = ResourceBundle.getBundle(
            "version");

    private ATimeLabel lblDate;

    private JPanel paneTimeContainer;

    private ASound backgrounSFX;

    /**
     * .-----------------------------------------------------------------------.
     * | AuroraCoreUI(JFrame)
     * .-----------------------------------------------------------------------.
     * |
     * | This is the Constructor of the AuroraCoreUI class.
     * |
     * | The constructor sets up the Aurora app main frame
     * .........................................................................
     *
     * @param aFrame JFrame
     *
     */
    public AuroraCoreUI(final JFrame aFrame) {

        this.frame = aFrame;
        setCursor();
        aFrame.setUndecorated(true);
        aFrame.setBackground(Color.BLACK);
        aFrame.setResizable(false);
        aFrame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        aFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        aFrame.addWindowFocusListener(new FrameFocusListener());
        resources = new ASurface("");

    }

    /**
     * .-----------------------------------------------------------------------
     * | setUI()
     * .-----------------------------------------------------------------------
     * |
     * | Sets up the Aurora UI
     * |
     * | This method sets up all the key UI components that make up the core UI
     * | for Aurora
     * |
     * .........................................................................
     * <p/>
     * @throws UnsupportedAudioFileException Exception
     * @throws IOException                   Exception
     * @throws LineUnavailableException      Exception
     * @throws InterruptedException          Exception
     * @throws FontFormatException           Exception
     */
    public final void setUI() throws UnsupportedAudioFileException, IOException,
                                     LineUnavailableException,
                                     InterruptedException,
                                     FontFormatException {

        //* Determine Global Size based on Screen Size *//

        // TODO work on Screen Gui Change

        screenWidth = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getScreenDevices()[0].getDisplayMode().getWidth();
        screenHeight = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getScreenDevices()[0].getDisplayMode().getHeight();

        System.out.println("Current Screen Ressolution: "
                           + screenWidth + "x" + screenHeight);

        //*
        // Check the resolution (in pixels) of the screen to
        // determine if the screen is large or not
        //*
        if (screenWidth >= 1680 && screenHeight >= 1050) {
            isLargeScreen = true;
        } else {
            isLargeScreen = false;
        }

        // LargeScreen = false;
        System.out.println("High Resolution Boolean = " + isLargeScreen);

        //* Set Size For UI *//

        setSizes();

        //* Get Font *//

        try {
            regularFont = Font.createFont(Font.TRUETYPE_FONT, new URL(
                    resources
                    .getSurfacePath()
                    + "/aurora/V1/resources/Gudea-Regular.ttf")
                    .openStream());
            boldFont = Font.createFont(Font.TRUETYPE_FONT, new URL(
                    resources
                    .getSurfacePath()
                    + "/aurora/V1/resources/Gudea-Bold.ttf")
                    .openStream());
        } catch (MalformedURLException ex) {
            try {
                regularFont = Font
                        .createFont(Font.TRUETYPE_FONT, getClass()
                        .getResourceAsStream(
                        "/aurora/V1/resources/Gudea-Regular.ttf"));
                boldFont = Font
                        .createFont(Font.TRUETYPE_FONT, getClass()
                        .getResourceAsStream(
                        "/aurora/V1/resources/Gudea-Bold.ttf"));

            } catch (Exception exx) {
                System.out.println("ERROR In Getting Font Resourcess");
            }
        }

        //*
        // The Background Panel Contains The Background Image for the Window as
        // Well as all components found in the window
        //*

        paneBackground = new AImagePane("app_background.png", frame
                .getSize().width, frame.getSize().height, true);

        paneBackground.setPreferredSize(frame.getSize());
        paneBackground.setLayout(new BoxLayout(paneBackground,
                BoxLayout.Y_AXIS));

        //*
        // The Top Panel Contains Header Image as well as the Frame Buttons:
        // Exit and Minimize
        //*

        paneTop = new AImagePane("app_header.png",
                frame.getSize().width, (frame.getSize().height / 6), true);
        paneTop.setPreferredSize(new Dimension(frame.getSize().width,
                (frame.getSize().height / 6)));

        paneTop.setIgnoreRepaint(true);
        paneTop.setLayout(new BorderLayout());


        //*
        // The Center Panel
        // and maintains a space between top and bottom panel
        //*

        paneCenter = new JPanel(true);
        paneCenter.setPreferredSize(new Dimension(frame.getSize().width, frame
                .getSize().height - (frame.getSize().height / 6 + frame
                .getSize().height / 6)));
        paneCenter.setOpaque(false);
        paneCenter.setLayout(new BorderLayout(0, 0));
        paneCenter.setIgnoreRepaint(true);

        //*
        // The Bottom Panel Contains the Footer Image as well as
        // the Time and the Login Controls
        //*
        paneBottom = new AImagePane("app_footer.png",
                frame.getSize().width, frame.getSize().height / 6, true);
        paneBottom.setPreferredSize(new Dimension(frame.getSize().width,
                frame.getSize().height / 6));
        paneBottom.setOpaque(false);
        paneBottom.setLayout(new BorderLayout());

        //*
        // Configure Panels:
        // Add specific UI components to each panel
        //*

        // Create V.I
        vi = new ANuance();

        //* Setup Buttons *//
        btnExit = new AButton("app_btn_close_norm.png",
                "app_btn_close_down.png", "app_btn_close_over.png",
                exitButtonWidth, exitButtonHeight);
        btnExit.addActionListener(new CloseListener());
        btnExit.setToolTipText("Exit");
        btnMinimize = new AButton("app_btn_minimize_norm.png",
                "app_btn_minimize_down.png", "app_btn_minimize_over.png",
                minimizeButtonWidth, minimizeButtonHeight);
        minimizeHandler = new MinimizeListener(this, AuroraMini.MINIMIZE_MODE);
        btnMinimize.addActionListener(minimizeHandler);
        btnMinimize.setToolTipText("Minimize");

        // TOP PANEL
        // --------------------------------------------------------------------

        //* Frame Buttons *//

        paneFrameControl = new AImagePane("start_frameControl_bg.png",
                controlWidth, controlHeight);
        paneFrameControl.setImageHeight(controlHeight);
        paneFrameControl.setOpaque(false);
        paneFrameControl.add(btnMinimize);
        paneFrameControl.add(btnExit);

        paneFrameControlContainer = new JPanel(new BorderLayout(0, 0));
        paneFrameControlContainer.setOpaque(false);
        paneFrameControlContainer.setBackground(Color.BLUE);
        paneFrameControlContainer
                .add(paneFrameControl, BorderLayout.NORTH);

        southFromTopPanel = new JPanel();
        southFromTopPanel.setLayout(new BorderLayout());
        southFromTopPanel.setOpaque(false);
        southFromTopPanel.setBackground(Color.red);
        southFromTopPanel.add(BorderLayout.EAST, paneFrameControlContainer);
        southFromTopPanel.setPreferredSize(new Dimension(frame.getWidth(),
                controlHeight));

        paneTop.add(BorderLayout.SOUTH, southFromTopPanel);

        // LOGO PANEL
        // ---------------------------------------------------------------------

        imgLogo = new AImage("start_header_logo.png", logoWidth, logoHeight);
        logoPanel = new JPanel();
        logoPanel.setOpaque(false);
        logoPanel.add(imgLogo);
        logoPanel.setPreferredSize(imgLogo.getSize());

        paneTop.add(BorderLayout.CENTER, logoPanel);

        // BOTTOM PANEL
        // --------------------------------------------------------------------

        paneCenterFromBottom = new JPanel(new BorderLayout());
        paneCenterFromBottom.setOpaque(false);
        paneBottom.add(BorderLayout.CENTER, paneCenterFromBottom);

        // WELCOME LABEL
        // --------------------------------------------------------------------

        lblTitle = new JLabel(vi.VI(ANuance.inx_Welcome));
        lblTitle.setOpaque(false);
        lblTitle.setForeground(Color.LIGHT_GRAY);
        lblTitle.setFont(regularFont.deriveFont(Font.PLAIN, welcomeFontSize));

        paneTitle = new JPanel(new FlowLayout(FlowLayout.CENTER));
        paneTitle.setOpaque(false);
        paneTitle.add(lblTitle);

        screenLabelPanel = new JPanel(new BorderLayout());
        screenLabelPanel.setOpaque(false);
        screenLabelPanel.add(BorderLayout.NORTH, paneTitle);
        paneBottom.add(BorderLayout.PAGE_START, screenLabelPanel);

        // TIME LABEL
        // ---------------------------------------------------------------------

        paneHeaderOfCenterFromBottom = new JPanel(new BorderLayout());
        lblTime = new ATimeLabel(ATimeLabel.TIME);
        lblTime.setFont(boldFont.deriveFont(Font.PLAIN, timeFontSize));
        lblTime.setForeground(new Color(0, 178, 178));

        lblDate = new ATimeLabel(ATimeLabel.DATE_LETTERS);
        lblDate.setForeground(Color.gray);
        lblDate.setFont(boldFont.deriveFont(Font.PLAIN, timeFontSize));

        paneTimeContainer = new JPanel();
        paneTimeContainer.setOpaque(false);

        paneTimeContainer.add(lblDate);
        paneTimeContainer.add(lblTime);


        paneTime = new JPanel(new BorderLayout());
        paneTime.setOpaque(false);
        paneTime.add(paneTimeContainer, BorderLayout.NORTH);
        paneHeaderOfCenterFromBottom.add(BorderLayout.EAST, paneTime);
        paneHeaderOfCenterFromBottom.setOpaque(false);

        // KEY PRESS PANEL
        // ---------------------------------------------------------------------

        paneKeyToPress = new JPanel();
        paneKeyToPress.setOpaque(false);

        imgKeyIcon = new AImage("KeyboardKeys/enter.png");
        imgKeyIcon.setImageSize(keyIconWidth, keyIconHeight);
        lblKeyAction = new JLabel(" Select ");

        lblKeyAction.setFont(regularFont.deriveFont(Font.PLAIN, keysFontSize));
        lblKeyAction.setForeground(new Color(0, 178, 178));

        paneHeaderOfCenterFromBottom.add(BorderLayout.WEST, paneKeyToPress);
        paneCenterFromBottom.add(BorderLayout.NORTH,
                paneHeaderOfCenterFromBottom);

        // USER SPACE
        // ---------------------------------------------------------------------

        paneBottomCenterContent = new JPanel();
        paneBottomCenterContent.setOpaque(false);
        paneBottomCenterContent
                .setLayout(new BoxLayout(paneBottomCenterContent,
                BoxLayout.Y_AXIS));

        paneCenterFromBottom.add(BorderLayout.CENTER, paneBottomCenterContent);

        // VERSION LABEL
        // ---------------------------------------------------------------------

        lblVersion = new JLabel(version);
        lblVersion.setOpaque(false);
        lblVersion.setForeground(Color.LIGHT_GRAY);
        lblVersion
                .setFont(regularFont.deriveFont(Font.PLAIN, versionFontSize));

        versionPanel = new JPanel();
        versionPanel.setOpaque(false);
        versionPanel.setLayout(new BorderLayout());
        versionPanel.add(BorderLayout.WEST, lblVersion);
        paneBottom.add(BorderLayout.PAGE_END, versionPanel);


        try {
            backgrounSFX = new ASound(ASound.sfxTheme, true);
        } catch (MalformedURLException ex) {
            Logger.getLogger(StartScreenLogic.class.getName()).
                    log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(StartScreenLogic.class.getName()).
                    log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StartScreenLogic.class.getName()).
                    log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(StartScreenLogic.class.getName()).
                    log(Level.SEVERE, null, ex);
        }

        //*
        // Add All 3 Main Panels To
        // Background Panel
        //*

        paneBackground.add(paneTop);
        paneBackground.add(paneCenter);
        paneBackground.add(paneBottom);

        frame.addKeyListener(new FrameKeyListener());
        frame.requestFocus();

    }

    public ADialog getWarningDialog() {
        return warningDialog;
    }

    public void setWarningDialog(ADialog warningDialog) {
        this.warningDialog = warningDialog;
    }

    public ADialog getErrorDialog() {
        return errorDialog;
    }

    public void setErrorDialog(ADialog errorDialog) {
        this.errorDialog = errorDialog;
    }

    public JPanel getLogoPanel() {
        return logoPanel;
    }

    public void setLogoPanel(JPanel logoPanel) {
        this.logoPanel = logoPanel;
    }

    public JPanel getTimePanel() {
        return paneTime;
    }

    public void setTimePanel(JPanel timePanel) {
        this.paneTime = timePanel;
    }

    public JPanel getFrameControlContainerPanel() {
        return paneFrameControlContainer;
    }

    public void setFrameControlContainerPanel(JPanel frameControlContainerPanel) {
        this.paneFrameControlContainer = frameControlContainerPanel;
    }

    public void minimizeAurora(String arg) {
        if (miniMode == null) {
            miniMode = new AuroraMini(this, arg); //retain state
        }
        miniMode.createUI();

        frame.setLocation(0, 3000);
        frame.setVisible(false);
    }

    public MinimizeListener getMinimizeHandler() {
        return minimizeHandler;
    }

    public AuroraMini getMiniMode() {
        if (miniMode == null) {
            miniMode = new AuroraMini(this, "null"); //* retain state *//
        }
        return miniMode;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public Font getBoldFont() {
        return boldFont;
    }

    public Font getRegularFont() {
        return regularFont;
    }

    public Font getDefaultFont() {
        return regularFont;
    }

    /**
     * Sets the sizes of the various components based on the screen resolution
     */
    private void setSizes() {
        double Ratio = (frame.getWidth() - frame.getHeight()) / 2;
        if (isLargeScreen) {
            topPanelSize = frame.getHeight() / 4;
            centerPanelSize = frame.getHeight() / 2 + frame.getHeight() / 40;
            bottomPanelSize = frame.getHeight() / 4 + frame.getHeight() / 40;
            controlHeight = 55;
            controlWidth = 160;
            keysFontSize = frame.getHeight() / 45;
            welcomeFontSize = 18;
            versionFontSize = 12;
            timeFontSize = bottomPanelSize / 15;
            logoHeight = topPanelSize / 3 + (int) (Ratio / 14);
            logoWidth = frame.getWidth() / 2 + (int) (Ratio / 5);
            exitButtonWidth = 0;
            exitButtonHeight = 0;
            minimizeButtonWidth = 0;
            minimizeButtonHeight = 0;
            keyIconWidth = 0;
            keyIconHeight = 0;
        } else {
            topPanelSize = frame.getHeight() / 4;
            centerPanelSize = frame.getHeight() / 2 + frame.getHeight() / 40;
            bottomPanelSize = frame.getHeight() / 4 + frame.getHeight() / 40;
            keysFontSize = frame.getHeight() / 45;
            welcomeFontSize = 18;
            versionFontSize = 12;
            timeFontSize = bottomPanelSize / 15;
            logoHeight = topPanelSize / 3 + (int) (Ratio / 20);
            logoWidth = frame.getWidth() / 2;
            controlHeight = 45;
            controlWidth = 150;
            exitButtonWidth = 35;
            exitButtonHeight = 30;
            minimizeButtonWidth = 35;
            minimizeButtonHeight = 30;
            keyIconWidth = bottomPanelSize / 4;
            keyIconHeight = bottomPanelSize / 8;
        }
    }

    /**
     * .-----------------------------------------------------------------------
     * | setSFX()
     * .-----------------------------------------------------------------------
     * |
     * | Background sound
     * |
     * | This is a method that will set the background sound effects for Aurora
     * |
     * .........................................................................
     *
     */
    public void setSFX() throws UnsupportedAudioFileException, IOException,
                                LineUnavailableException, InterruptedException {
        //*
        // Background Sound
        //*
    }

    /**
     * .-----------------------------------------------------------------------
     * | showExitDialog()
     * .-----------------------------------------------------------------------
     * |
     * | This method displays the exit dialog
     * |
     * | After the user clicks on the Exit button, the user will be presented
     * | with a warning dialog asking them if they are sure they want to exit
     * | the application.
     * .........................................................................
     *
     */
    public void showExitDialog() {
        if (warningDialog == null) {
            warningDialog = new ADialog(ADialog.aDIALOG_WARNING,
                    "Are You " + vi.VI(vi.inx_Sure) + " You Want To " + vi
                    .VI(vi.inx_Exit) + "?",
                    regularFont.deriveFont(Font.BOLD, 25));



            warningDialog.setOKButtonListener(new ActionListener() {
                private ADialog err;

                public void actionPerformed(ActionEvent e) {

                    System.exit(0);
                }
            });
            warningDialog.showDialog();


        }
        warningDialog.setVisible(true);

        try {
            try {
                setSFX();
            } catch (InterruptedException ex) {
                Logger.getLogger(AuroraCoreUI.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(AuroraCoreUI.class.getName()).log(Level.SEVERE,
                    null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AuroraCoreUI.class.getName()).log(Level.SEVERE,
                    null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(AuroraCoreUI.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

    }

    //* Get Build Version Of Application *//
    private static String getResourceBundleToken(String propertyToken) {
        String msg = "";
        try {
            msg = resourceBundle.getString(propertyToken);
        } catch (MissingResourceException e) {
            System.err.println("Token ".concat(propertyToken).concat(
                    " not in Property file!"));
        }
        return msg;
    }

    public String getOS() {
        return System.getProperty("os.name");
    }

    public void setSurface(ASurface resource) {
        this.resources = resource;
    }

    public ASurface getResource() {
        return resources;
    }
    /**
     * .-----------------------------------------------------------------------.
     * | setCursor()
     * .-----------------------------------------------------------------------.
     * |
     * | This method sets the mouse pointer cursor and states
     * |
     * | Customize the look of the mouse pointer cursor in various states
     * |
     * .........................................................................
     *
     */
    private BufferedImage cursorImage;

    private void setCursor() {

        ACursor cursor = new ACursor(new AImage("cursor.png"));

        frame.setCursor(cursor.getCursor());

    }

    private class FrameFocusListener implements WindowFocusListener {

        /**
         * Glass Pane from Frame.
         */
        private JPanel glass;

        /**
         * ImagePane containing Unfocused Background Image.
         */
        private AImagePane paneUnfocused;

        private boolean wasVisible;

        /**
         * FrameFocusListener()
         * Detect when Frame is out of focus.
         */
        public FrameFocusListener() {
            paneUnfocused = new AImagePane("Aurora_Unfocused.png", frame
                    .getWidth(), frame.getHeight());
            this.glass = (JPanel) frame.getGlassPane();
        }

        @Override
        public void windowGainedFocus(WindowEvent e) {
            System.out.println("FOCUS GAINED");
            if (wasVisible) {
                glass.remove(paneUnfocused);
                glass.repaint();
            } else {

                glass.remove(paneUnfocused);
                glass.setVisible(false);
                glass.repaint();
            }
        }

        @Override
        public void windowLostFocus(WindowEvent e) {
            System.out.println("FOCUS LOST");
            if (frame.getGlassPane().isVisible()) {
                glass.setLayout(null);
                wasVisible = true;
                glass.add(paneUnfocused, 0);
                paneUnfocused.setSize(frame.getSize());
                paneUnfocused.setLocation(0, 0);
                paneUnfocused.setBounds(0, 0, frame
                        .getWidth() + 1, frame.getHeight());
                glass.setVisible(true);
                glass.repaint();
            } else {
                wasVisible = false;
                glass.setLayout(null);
                glass.add(paneUnfocused, 0);
                paneUnfocused.setSize(frame.getSize());
                paneUnfocused.setLocation(0, 0);
                paneUnfocused.setBounds(0, 0, frame
                        .getWidth() + 1, frame.getHeight());
                glass.setVisible(true);
                glass.repaint();
            }
        }
    }

    /*
     * Sets The Close Button Actions
     */
    class CloseListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            showExitDialog();

        }
    }

    /*
     * Sets the minimize button actions
     */
    public class MinimizeListener implements ActionListener {

        private AuroraCoreUI ui;

        private String arg;

        public MinimizeListener(AuroraCoreUI ui, String arg) {
            this.ui = ui;
            this.arg = arg;
        }

        public void setArg(String arg) {
            this.arg = arg;
        }

        public void actionPerformed(ActionEvent e) {
            //ENABLE MINI MODE
            backgrounSFX.Pause();
            minimizeAurora(arg);

        }
    }

    class FrameKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {

            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                showExitDialog();
            }

        }
    }

    // SETTERS & GETTERS
    // -----------------------------------------------------------------------
    public void setKeyIconHeight(int keyIconHeight) {
        this.keyIconHeight = keyIconHeight;
    }

    public int getKeyIconHeight() {
        return keyIconHeight;
    }

    public boolean getLargeScreen() {
        return isLargeScreen;
    }

    public void setKeyIconWidth(int keyIconWidth) {
        this.keyIconWidth = keyIconWidth;
    }

    public int getKeyIconWidth() {
        return keyIconWidth;
    }

    public void setTimeFontSize(int timeFontSize) {
        this.timeFontSize = timeFontSize;
    }

    public int getTimeFontSize() {
        return timeFontSize;
    }

    public void setVersionFontSize(int versionFontSize) {
        this.versionFontSize = versionFontSize;
    }

    public int getVersionFontSize() {
        return versionFontSize;
    }

    public void setExitButtonHeight(int exitButtonHeight) {
        this.exitButtonHeight = exitButtonHeight;
    }

    public int getExitButtonHeight() {
        return exitButtonHeight;
    }

    public void setExitButtonWidth(int exitButtonWidth) {
        this.exitButtonWidth = exitButtonWidth;
    }

    public int getExitButtonWidth() {
        return exitButtonWidth;
    }

    public void setMinimizeButtonHeight(int minimizeButtonHeight) {
        this.minimizeButtonHeight = minimizeButtonHeight;
    }

    public int getMinimizeButtonHeight() {
        return minimizeButtonHeight;
    }

    public void setMinimizeButtonWidth(int minimizeButtonWidth) {
        this.minimizeButtonWidth = minimizeButtonWidth;
    }

    public int getMinimizeButtonWidth() {
        return minimizeButtonWidth;
    }

    public void setControlHeight(int controlHeight) {
        this.controlHeight = controlHeight;
    }

    public int getControlHeight() {
        return controlHeight;
    }

    public void setControlWidth(int controlWidth) {
        this.controlWidth = controlWidth;
    }

    public int getControlWidth() {
        return controlWidth;
    }

    public void setKeysFontSize(int keysFontSize) {
        this.keysFontSize = keysFontSize;
    }

    public int getKeysFontSize() {
        return keysFontSize;
    }

    public void setLogoHeight(int logoHeight) {
        this.logoHeight = logoHeight;
    }

    public int getLogoHeight() {
        return logoHeight;
    }

    public void setLogoWidth(int logoWidth) {
        this.logoWidth = logoWidth;
    }

    public int getLogoWidth() {
        return logoWidth;
    }

    public void setBottomPanelSize(int bottomPanelSize) {
        this.bottomPanelSize = bottomPanelSize;
    }

    public int getBottomPanelSize() {
        return bottomPanelSize;
    }

    public void setCenterPanelSize(int centerPanelSize) {
        this.centerPanelSize = centerPanelSize;
    }

    public int getCenterPanelSize() {
        return centerPanelSize;
    }

    public void setTopPanelSize(int topPanelSize) {
        this.topPanelSize = topPanelSize;
    }

    public int getTopPanelSize() {
        return topPanelSize;
    }

    public void setWelcomeFontSize(int welcomeFontSize) {
        this.welcomeFontSize = welcomeFontSize;
    }

    public int getWelcomeFontSize() {
        return welcomeFontSize;
    }

    public void setBackgroundImagePane(AImagePane backgroundImagePane) {
        this.paneBackground = backgroundImagePane;
    }

    public AImagePane getBackgroundImagePane() {
        return paneBackground;
    }

    public void setBottomPane(AImagePane bottomImagePane) {
        this.paneBottom = bottomImagePane;
    }

    public AImagePane getBottomPane() {
        return paneBottom;
    }

    public void setCenterPanel(JPanel centerPanel) {
        this.paneCenter = centerPanel;
    }

    public JPanel getCenterPanel() {
        return paneCenter;
    }

    public void setCenterFromBottomPanel(JPanel centerFromBottomPanel) {
        this.paneCenterFromBottom = centerFromBottomPanel;
    }

    public JPanel getCenterFromBottomPanel() {
        return paneCenterFromBottom;
    }

    public void setSouthFromTopPanel(JPanel southFromTopPanel) {
        this.southFromTopPanel = southFromTopPanel;
    }

    public JPanel getSouthFromTopPanel() {
        return southFromTopPanel;
    }

    public void setFrameControlImagePane(AImagePane frameControlImagePane) {
        this.paneFrameControl = frameControlImagePane;
    }

    public AImagePane getFrameControlImagePane() {
        return paneFrameControl;
    }

    public void setTopImagePane(AImagePane topImagePane) {
        this.paneTop = topImagePane;
    }

    public AImagePane getTopPane() {
        return paneTop;
    }

    public void setLogoImage(AImage logoImage) {
        this.imgLogo = logoImage;
    }

    public AImage getLogoImage() {
        return imgLogo;
    }

    public void setTitleLabel(JLabel aTitleLabel) {
        this.lblTitle = aTitleLabel;
    }

    public JLabel getTitleLabel() {
        return lblTitle;
    }

    public void setVi(ANuance vi) {
        this.vi = vi;
    }

    public ANuance getVi() {
        return vi;
    }

    public void setUserbarPanel(JPanel bottomCenter) {
        this.paneBottomCenterContent = bottomCenter;
    }

    public JPanel getBottomContentPane() {
        return paneBottomCenterContent;
    }

    public String getRevision() {
        return revision;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setKeyIconImage(AImage keyIconImage) {
        this.imgKeyIcon = keyIconImage;
    }

    public AImage getKeyIconImage() {
        return imgKeyIcon;
    }

    public void setKeyActionLabel(JLabel keyActionLabel) {
        this.lblKeyAction = keyActionLabel;
    }

    public JLabel getKeyActionLabel() {
        return lblKeyAction;
    }

    public static void setTimeLabel(ATimeLabel timeLabel) {
        AuroraCoreUI.lblTime = timeLabel;
    }

    public void setVersionLabel(JLabel versionLabel) {
        this.lblVersion = versionLabel;
    }

    public JLabel getVersionLabel() {
        return lblVersion;
    }

    public void setHeaderOfCenterFromBottomPanel(
            JPanel headerOfCenterFromBottomPanel) {
        this.paneHeaderOfCenterFromBottom = headerOfCenterFromBottomPanel;
    }

    public JPanel getHeaderOfCenterFromBottomPanel() {
        return paneHeaderOfCenterFromBottom;
    }

    public void setKeyToPressPanel(JPanel keyToPressPanel) {
        this.paneKeyToPress = keyToPressPanel;
    }

    public JPanel getKeyToPressPanel() {
        return paneKeyToPress;
    }

    public void setScreenLabelPanel(JPanel screenLabelPanel) {
        this.screenLabelPanel = screenLabelPanel;
    }

    public JPanel getScreenLabelPanel() {
        return screenLabelPanel;
    }

    public void setVersionPanel(JPanel versionPanel) {
        this.versionPanel = versionPanel;
    }

    public JPanel getVersionPanel() {
        return versionPanel;
    }

    public String getVersion() {
        return version;
    }

    public boolean isLargeScreen() {
        return isLargeScreen;
    }

    public JButton getExitButton() {
        return btnExit;
    }

    public JButton getMinimizeButton() {
        return btnMinimize;
    }

    public static ATimeLabel getTimeLabel() {
        return lblTime;
    }

    public JPanel getTitlePanel() {
        return paneTitle;
    }

    public ASound getBackgroundSound() {
        return backgrounSFX;
    }
}
