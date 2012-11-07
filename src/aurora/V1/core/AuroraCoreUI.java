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

import Test.Sandbox;
import aurora.engine.V1.Logic.ANuance;
import aurora.engine.V1.Logic.ASurface;
import aurora.engine.V1.UI.AButton;
import aurora.engine.V1.UI.ADialog;
import aurora.engine.V1.UI.AImage;
import aurora.engine.V1.UI.AImagePane;
import aurora.engine.V1.UI.ATimeLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

/**
 * .------------------------------------------------------------------------.
 * | AuroraCoreUI
 * .------------------------------------------------------------------------.
 * |
 * |
 * |
 * |
 * |
 * |
 * |
 * |
 * |
 * .........................................................................
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
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
                         "  //BUILD: " + getResourceBundleToken("BUILD")
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

    /*
     * Warning dialog window.
     */
    private ADialog warningDialog;

    /*
     * Error dialog window.
     */
    private ADialog errorDialog;

    /*
     * Logo image.
     */
    private AImage imgLogo;

    /*
     * Key icon image.
     */
    private AImage imgKeyIcon;

    /*
     * Background image pane.
     */
    private AImagePane paneBackground;

    /*
     * Bottom image pane.
     */
    private AImagePane paneBottom;

    private AImagePane paneFrameControl;

    /*
     * Top image.
     */
    private AImagePane paneTopImage;

    /*
     * 
     */
    private ASurface ressources;

    private ANuance vi;

    /*
     * Regular font
     */
    private Font regularFont;

    /*
     * Bold font.
     */
    private Font boldFont;

    /*
     * Exit button.
     */
    private JButton btnExit;

    /*
     * Minimize button.
     */
    private JButton btnMinimize;

    private JFrame frame;

    /*
     * Center panel.
     */
    private JPanel paneCenter;

    /*
     * Panel that holds the keys to press images and labels
     */
    private JPanel paneKeyToPress;

    private JPanel southFromTopPanel;

    /*
     * Panel that holds the Aurora logo.
     */
    private JPanel logoPanel;

    /*
     * Panel that 
     */
    private JPanel screenLabelPanel;

    /*
     * Panel that holds the current version of Aurora
     */
    private JPanel versionPanel;

    private JPanel paneHeaderOfCenterFromBottom;

    private JPanel paneCenterFromBottom;

    /*
     * Panel that holds the current time.
     */
    private JPanel paneTime;

    /*
     * Panel that holds the app title
     */
    private JPanel paneTitle;

    private JPanel paneFrameControlContainer;

    /*
     * 
     */
    private JPanel paneUserSpace;

    /*
     * Label for the version panel
     */
    private JLabel lblVersion;

    /*
     * Label for the title panel
     */
    private JLabel lblTitle;

    /*
     * Label for the key image icon
     */
    private JLabel lblKeyAction;

    /*
     * 
     */
    private AuroraMini miniMode;

    /*
     * Listener to listen for when Aurora is minimized
     */
    private MinimizeListener minimizeHandler;

    /*
     * Label indicating the current time
     */
    public static ATimeLabel lblTime;

    final static ResourceBundle resourceBundle = ResourceBundle.getBundle(
            "version");

    /**
     * .-----------------------------------------------------------------------.
     * | AuroraCoreUI(JFrame)
     * .-----------------------------------------------------------------------.
     * |
     * |
     * |
     * |
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

        ressources = new ASurface("");

    }


    /**
     * .-----------------------------------------------------------------------.
     * | setUI()
     * .-----------------------------------------------------------------------.
     * |
     * | This method sets up the core UI for Aurora
     * |
     * | 
     * | 
     * | 
     * | 
     * | 
     * | 
     * |
     * |
     * .........................................................................
     *
     * 
     *
     */
    public void setUI() throws UnsupportedAudioFileException, IOException,
                               LineUnavailableException, InterruptedException,
                               FontFormatException {

        // Determine Global Size based on Screen Size 	

        // TODO work on Screen Gui Change

        screenWidth = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getScreenDevices()[0].getDisplayMode().getWidth();
        screenHeight = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getScreenDevices()[0].getDisplayMode().getHeight();

        System.out.println("Current Screen Ressolution: "
                           + screenWidth + "x" + screenHeight);
       
        //*
        // Check the resolution (in pixels) of the screen to determine if the screen
        // is large or not
        //*
        if (screenWidth >= 1680 && screenHeight >= 1050) {
            isLargeScreen = true;
        } else {
            isLargeScreen = false;
        }

        // LargeScreen = false;
        System.out.println("High Resolution Boolean = " + isLargeScreen);

        // Set Size For UI

        setSizes();

        // Start Preparation


        //Get Font

        try {
            regularFont = Font.createFont(Font.TRUETYPE_FONT, new URL(ressources
                    .getSurfacePath() + "/aurora/V1/resources/AGENCYR.TTF")
                    .openStream());
            boldFont = Font.createFont(Font.TRUETYPE_FONT, new URL(ressources
                    .getSurfacePath() + "/aurora/V1/resources/AGENCYB.TTF")
                    .openStream());
        } catch (MalformedURLException ex) {
            try {
                regularFont = Font
                        .createFont(Font.TRUETYPE_FONT, getClass()
                        .getResourceAsStream("/aurora/V1/resources/AGENCYR.TTF"));
                boldFont = Font
                        .createFont(Font.TRUETYPE_FONT, getClass()
                        .getResourceAsStream("/aurora/V1/resources/AGENCYB.TTF"));

            } catch (Exception exx) {
                System.out.println("ERROR In Getting Font Resourcess");
            }
        }
        
        //*
        // The Background Panel Contains The Background Image for the Window as
        // Well as all components found in the window
        //*

        paneBackground = new AImagePane("Aurora_Background.png", frame
                .getSize().width, frame.getSize().height, true);

        paneBackground.setPreferredSize(frame.getSize());
        paneBackground.setLayout(new BoxLayout(paneBackground,
                BoxLayout.Y_AXIS));

        //*
        // The Top Panel Contains Header Image as well as the Frame Buttons:
        // Exit and Minimize
        //*

        paneTopImage = new AImagePane("Aurora_Header1.png",
                frame.getSize().width, (frame.getSize().height / 6), true);
        paneTopImage.setPreferredSize(new Dimension(frame.getSize().width,
                (frame.getSize().height / 6)));

        paneTopImage.setIgnoreRepaint(true);
        paneTopImage.setLayout(new BorderLayout());


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
        paneBottom = new AImagePane("Aurora_Footer1.png",
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
        btnExit = new AButton("Aurora_Close_normal.png",
                "Aurora_Close_down.png", "Aurora_Close_over.png",
                exitButtonWidth, exitButtonHeight);
        btnExit.addActionListener(new CloseListener());
        btnExit.setToolTipText("Exit");
        btnMinimize = new AButton("Aurora_Desktop_normal.png",
                "Aurora_Desktop_down.png", "Aurora_Desktop_over.png",
                minimizeButtonWidth, minimizeButtonHeight);
        minimizeHandler = new MinimizeListener(this, AuroraMini.MINIMIZE_MODE);
        btnMinimize.addActionListener(minimizeHandler);
        btnMinimize.setToolTipText("Minimize");

        // Top Panel

        // Frame Buttons
        
        paneFrameControl = new AImagePane("Aurora_FrameButton1.png",
                controlWidth, controlHeight);
        paneFrameControl.setImageHeight(controlHeight);
        paneFrameControl.setOpaque(false);
        paneFrameControl.add(btnMinimize);
        paneFrameControl.add(btnExit);

        paneFrameControlContainer = new JPanel(new BorderLayout());
        paneFrameControlContainer.setOpaque(false);
        paneFrameControlContainer
                .add(paneFrameControl, BorderLayout.NORTH);

        southFromTopPanel = new JPanel();
        southFromTopPanel.setLayout(new BorderLayout());
        southFromTopPanel.setOpaque(false);
        southFromTopPanel.add(BorderLayout.LINE_END, paneFrameControlContainer);
        southFromTopPanel.setPreferredSize(new Dimension(frame.getWidth(),
                controlHeight + 5));

        paneTopImage.add(BorderLayout.SOUTH, southFromTopPanel);


        // Logo Image

        imgLogo = new AImage("Logo_Aurora.png", logoWidth, logoHeight);
        logoPanel = new JPanel();
        logoPanel.setOpaque(false);
        logoPanel.add(imgLogo);
        logoPanel.setPreferredSize(imgLogo.getSize());

        paneTopImage.add(BorderLayout.CENTER, logoPanel);

        // BOTTOM PANEL
        // -----------------------------------------------------------------------

        paneCenterFromBottom = new JPanel(new BorderLayout());
        paneCenterFromBottom.setOpaque(false);
        paneBottom.add(BorderLayout.CENTER, paneCenterFromBottom);
        
        // WELCOME LABEL
        // -----------------------------------------------------------------------

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
        // -----------------------------------------------------------------------
        
        paneHeaderOfCenterFromBottom = new JPanel(new BorderLayout());
        lblTime = new ATimeLabel();
        lblTime.setFont(boldFont.deriveFont(Font.PLAIN, timeFontSize));
        lblTime.setForeground(new Color(80, 126, 222));
        paneTime = new JPanel(new BorderLayout());
        paneTime.setOpaque(false);
        paneTime.add(lblTime, BorderLayout.NORTH);
        paneHeaderOfCenterFromBottom.add(BorderLayout.EAST, paneTime);
        paneHeaderOfCenterFromBottom.setOpaque(false);

        // KEY PRESS PANEL
        // -----------------------------------------------------------------------
  
        paneKeyToPress = new JPanel();
        paneKeyToPress.setOpaque(false);


        imgKeyIcon = new AImage("KeyboardKeys/enter.png");
        imgKeyIcon.setImageSize(keyIconWidth, keyIconHeight);
        lblKeyAction = new JLabel(" Select ");

        lblKeyAction.setFont(regularFont.deriveFont(Font.PLAIN, keysFontSize));
        lblKeyAction.setForeground(Color.YELLOW);


        paneHeaderOfCenterFromBottom.add(BorderLayout.WEST, paneKeyToPress);
        paneCenterFromBottom.add(BorderLayout.NORTH,
                paneHeaderOfCenterFromBottom);

        // USER SPACE
        // -----------------------------------------------------------------------
        
        paneUserSpace = new JPanel();
        paneUserSpace.setOpaque(false);
        paneUserSpace
                .setLayout(new BoxLayout(paneUserSpace, BoxLayout.Y_AXIS));

        paneCenterFromBottom.add(BorderLayout.CENTER, paneUserSpace);

        // VERSION LABEL
        // -----------------------------------------------------------------------        
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




        ///Finalize

        //////////////////////////////////
        // Add All 3 Main Panels To     //
        // Background Panel             //
        //////////////////////////////////

        paneBackground.add(paneTopImage);

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
    }

    public MinimizeListener getMinimizeHandler() {
        return minimizeHandler;
    }

    public AuroraMini getMiniMode() {
        if (miniMode == null) {
            miniMode = new AuroraMini(this, "null"); //retain state
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

    private void setSizes() {
        double Ratio = (frame.getWidth() - frame.getHeight()) / 2;
        if (isLargeScreen) {
            topPanelSize = frame.getHeight() / 4;
            centerPanelSize = frame.getHeight() / 2 + frame.getHeight() / 40;
            bottomPanelSize = frame.getHeight() / 4 + frame.getHeight() / 40;
            controlHeight = 55;
            controlWidth = 160;
            keysFontSize = frame.getHeight() / 40;
            welcomeFontSize = 22;
            keyIconWidth = 0;
            keyIconHeight = 0;
            versionFontSize = 15;
            timeFontSize = bottomPanelSize / 12;
            logoHeight = topPanelSize / 3 + (int) (Ratio / 14);
            logoWidth = frame.getWidth() / 2 + (int) (Ratio / 5);
            exitButtonWidth = 0;
            exitButtonHeight = 0;
            minimizeButtonWidth = 0;
            minimizeButtonHeight = 0;
        } else {
            topPanelSize = frame.getHeight() / 4;
            centerPanelSize = frame.getHeight() / 2 + frame.getHeight() / 40;
            bottomPanelSize = frame.getHeight() / 4 + frame.getHeight() / 40;
            keysFontSize = frame.getHeight() / 40;
            welcomeFontSize = 21;
            keyIconWidth = bottomPanelSize / 4;
            keyIconHeight = bottomPanelSize / 8;
            versionFontSize = 12;
            timeFontSize = bottomPanelSize / 12;
            logoHeight = topPanelSize / 3 + (int) (Ratio / 20);
            logoWidth = frame.getWidth() / 2;
            controlHeight = 45;
            controlWidth = 150;
            exitButtonWidth = 35;
            exitButtonHeight = 30;
            minimizeButtonWidth = 35;
            minimizeButtonHeight = 30;
        }
    }

////////////
//Setters //
////////////
    public int getKeyIconHeight() {
        return keyIconHeight;
    }

    public void setKeyIconHeight(int keyIconHeight) {
        this.keyIconHeight = keyIconHeight;
    }

    public boolean getLargeScreen() {
        return isLargeScreen;
    }

    public int getKeyIconWidth() {
        return keyIconWidth;
    }

    public void setKeyIconWidth(int keyIconWidth) {
        this.keyIconWidth = keyIconWidth;
    }

    public int getTimeFontSize() {
        return timeFontSize;
    }

    public void setTimeFontSize(int timeFontSize) {
        this.timeFontSize = timeFontSize;
    }

    public int getVersionFontSize() {
        return versionFontSize;
    }

    public void setVersionFontSize(int versionFontSize) {
        this.versionFontSize = versionFontSize;
    }

    public int getExitButtonHeight() {
        return exitButtonHeight;
    }

    public void setExitButtonHeight(int exitButtonHeight) {
        this.exitButtonHeight = exitButtonHeight;
    }

    public int getExitButtonWidth() {
        return exitButtonWidth;
    }

    public void setExitButtonWidth(int exitButtonWidth) {
        this.exitButtonWidth = exitButtonWidth;
    }

    public int getMinimizeButtonHeight() {
        return minimizeButtonHeight;
    }

    public void setMinimizeButtonHeight(int minimizeButtonHeight) {
        this.minimizeButtonHeight = minimizeButtonHeight;
    }

    public int getMinimizeButtonWidth() {
        return minimizeButtonWidth;
    }

    public void setMinimizeButtonWidth(int minimizeButtonWidth) {
        this.minimizeButtonWidth = minimizeButtonWidth;
    }

    public int getControlHeight() {
        return controlHeight;
    }

    public void setControlHeight(int controlHeight) {
        this.controlHeight = controlHeight;
    }

    public int getControlWidth() {
        return controlWidth;
    }

    public void setControlWidth(int controlWidth) {
        this.controlWidth = controlWidth;
    }

    public int getKeysFontSize() {
        return keysFontSize;
    }

    public void setKeysFontSize(int keysFontSize) {
        this.keysFontSize = keysFontSize;
    }

    public int getLogoHeight() {
        return logoHeight;
    }

    public void setLogoHeight(int logoHeight) {
        this.logoHeight = logoHeight;
    }

    public int getLogoWidth() {
        return logoWidth;
    }

    public void setLogoWidth(int logoWidth) {
        this.logoWidth = logoWidth;
    }

    public int getBottomPanelSize() {
        return bottomPanelSize;
    }

    public void setBottomPanelSize(int bottomPanelSize) {
        this.bottomPanelSize = bottomPanelSize;
    }

    public int getCenterPanelSize() {
        return centerPanelSize;
    }

    public void setCenterPanelSize(int centerPanelSize) {
        this.centerPanelSize = centerPanelSize;
    }

    public int getTopPanelSize() {
        return topPanelSize;
    }

    public void setTopPanelSize(int topPanelSize) {
        this.topPanelSize = topPanelSize;
    }

    public int getWelcomeFontSize() {
        return welcomeFontSize;
    }

    public void setWelcomeFontSize(int welcomeFontSize) {
        this.welcomeFontSize = welcomeFontSize;
    }

    public void setBackgroundImagePane(AImagePane backgroundImagePane) {
        this.paneBackground = backgroundImagePane;
    }

    public void setBottomImagePane(AImagePane bottomImagePane) {
        this.paneBottom = bottomImagePane;
    }

    public void setCenterPanel(JPanel centerPanel) {
        this.paneCenter = centerPanel;
    }

    public void setCenterFromBottomPanel(JPanel centerFromBottomPanel) {
        this.paneCenterFromBottom = centerFromBottomPanel;
    }

    public void setSouthFromTopPanel(JPanel southFromTopPanel) {
        this.southFromTopPanel = southFromTopPanel;
    }

    public void setFrameControlImagePane(AImagePane frameControlImagePane) {
        this.paneFrameControl = frameControlImagePane;
    }

    public void setTopImagePane(AImagePane topImagePane) {
        this.paneTopImage = topImagePane;
    }

    public void setLogoImage(AImage logoImage) {
        this.imgLogo = logoImage;
    }

    public void setTitleLabel(JLabel aTitleLabel) {
        this.lblTitle = aTitleLabel;
    }

    public void setVi(ANuance vi) {
        this.vi = vi;
    }

    public void setUserbarPanel(JPanel userbarPanel) {
        this.paneUserSpace = userbarPanel;
    }

    public String getRevision() {
        return revision;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public void setKeyIconImage(AImage keyIconImage) {
        this.imgKeyIcon = keyIconImage;
    }

    public void setKeyActionLabel(JLabel keyActionLabel) {
        this.lblKeyAction = keyActionLabel;
    }

    public static void setTimeLabel(ATimeLabel timeLabel) {
        AuroraCoreUI.lblTime = timeLabel;
    }

    public void setVersionLabel(JLabel versionLabel) {
        this.lblVersion = versionLabel;
    }

    public void setHeaderOfCenterFromBottomPanel(
            JPanel headerOfCenterFromBottomPanel) {
        this.paneHeaderOfCenterFromBottom = headerOfCenterFromBottomPanel;
    }

    public void setKeyToPressPanel(JPanel keyToPressPanel) {
        this.paneKeyToPress = keyToPressPanel;
    }

    public void setScreenLabelPanel(JPanel screenLabelPanel) {
        this.screenLabelPanel = screenLabelPanel;
    }

    public void setVersionPanel(JPanel versionPanel) {
        this.versionPanel = versionPanel;
    }

    ///
    //Getters
    ///
    public AImage getKeyIconImage() {
        return imgKeyIcon;
    }

    public JLabel getKeyActionLabel() {
        return lblKeyAction;
    }

    public JLabel getVersionLabel() {
        return lblVersion;
    }

    public JPanel getHeaderOfCenterFromBottomPanel() {
        return paneHeaderOfCenterFromBottom;
    }

    public JPanel getKeyToPressPanel() {
        return paneKeyToPress;
    }

    public String getVersion() {
        return version;
    }

    public JPanel getScreenLabelPanel() {
        return screenLabelPanel;
    }

    public JPanel getVersionPanel() {
        return versionPanel;
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

    public JLabel getTitleLabel() {
        return lblTitle;
    }

    public ANuance getVi() {
        return vi;
    }

    public JFrame getFrame() {
        return frame;
    }

    public static ATimeLabel getTimeLabel() {
        return lblTime;
    }

    public JPanel getUserSpacePanel() {
        return paneUserSpace;
    }

    public AImage getLogoImage() {
        return imgLogo;
    }

    public AImagePane getBackgroundImagePane() {
        return paneBackground;
    }

    public JPanel getTitlePanel() {
        return paneTitle;
    }

    public AImagePane getBottomImagePane() {
        return paneBottom;
    }

    public JPanel getCenterPanel() {
        return paneCenter;
    }

    public JPanel getCenterFromBottomPanel() {
        return paneCenterFromBottom;
    }

    public JPanel getSouthFromTopPanel() {
        return southFromTopPanel;
    }

    public AImagePane getFrameControlImagePane() {
        return paneFrameControl;
    }

    public AImagePane getTopImagePane() {
        return paneTopImage;
    }

    public void setSFX() throws UnsupportedAudioFileException, IOException,
                                LineUnavailableException, InterruptedException {
        //////////
        /////Background Sound
        ///////////
    }

    public void showExitDialog() {
        if (warningDialog == null) {
            warningDialog = new ADialog(ADialog.aDIALOG_WARNING,
                    "Are You Sure you want to " + vi.VI(vi.inx_Exit) + "?",
                    boldFont);


            warningDialog.setButtonListener(new ActionListener() {
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

    //Get Build Version Of Application
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
        this.ressources = resource;
    }

    public ASurface getResource() {
        return ressources;
    }

    private void setCursor() {


        frame.setCursor(frame.getToolkit().createCustomCursor(
                new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(
                0, 0),
                "null"));

        AImage cursorImage = new AImage("cursor.png");
        AImage cursorSelect = new AImage("cursorSelect.png");

        cursorImage.setVisible(false);
        cursorSelect.setVisible(false);

        JPanel glass = new JPanel();


        glass.add(cursorImage);
        glass.add(cursorSelect);

        Cursor cursor = new Cursor(
                cursorImage, cursorSelect, frame.getContentPane(), glass);

        frame.getContentPane()
                .addMouseWheelListener(new MouseEventRedispatcher(glass, frame
                .getContentPane()));


        glass.addMouseMotionListener(cursor);

        glass.addMouseListener(new CursorClick(cursor));


        frame.setGlassPane(glass);

        glass.setVisible(true);
        glass.setLayout(null);
        glass.setOpaque(false);



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

    private static class CursorClick extends MouseAdapter {

        private final AImage cursorImage;

        private final AImage cursorSelect;

        private final Cursor click;

//        private final AImage cursorHover;
        public CursorClick(Cursor click) {
            this.cursorImage = click.getCursorImage();
            this.cursorSelect = click.getCursorSelect();
            this.click = click;
        }

        public AImage getCursorImage() {
            return cursorImage;
        }

        public AImage getCursorSelect() {
            return cursorSelect;
        }

        @Override
        public void mousePressed(MouseEvent e) {

            cursorImage.setVisible(false);

            cursorSelect.setLocation(e.getPoint());

            cursorSelect.setBounds(e.getX() - 11, e.getY() - 8, cursorImage
                    .getImgIcon()
                    .getIconWidth(), cursorImage.getImgIcon().getIconHeight());

            cursorSelect.setVisible(true);

            click.redispatchMouseEvent(e, true);
        }

        @Override
        public void mouseReleased(MouseEvent e) {

            cursorSelect.setVisible(false);

            cursorImage.setLocation(e.getPoint());

            cursorImage.setBounds(e.getX() - 11, e.getY() - 8, cursorImage
                    .getImgIcon()
                    .getIconWidth(), cursorImage.getImgIcon().getIconHeight());

            cursorImage.setVisible(true);

            click.redispatchMouseEvent(e, true);

        }

        public void mouseClicked(MouseEvent e) {
            click.redispatchMouseEvent(e, true);
        }

        public void mouseEntered(MouseEvent e) {
            click.redispatchMouseEvent(e, true);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            cursorSelect.setVisible(false);
            cursorImage.setVisible(false);
            click.redispatchMouseEvent(e, true);
        }
    }

    class Cursor implements MouseMotionListener {

        private final AImage cursorImage;

        private final AImage cursorSelect;

        private final JPanel glassPane;

        private final Container contentPane;

        private Cursor(AImage cursorImage, AImage cursorSelect,
                       Container contentPane, JPanel glassPane) {
            this.cursorImage = cursorImage;
            this.cursorSelect = cursorSelect;
            this.contentPane = contentPane;
            this.glassPane = glassPane;
        }

        public AImage getCursorImage() {
            return cursorImage;
        }

        public AImage getCursorSelect() {
            return cursorSelect;
        }

        @Override
        public void mouseDragged(MouseEvent e) {

            cursorImage.setVisible(false);

            cursorSelect.setLocation(e.getPoint());

            cursorSelect.setBounds(e.getX() - 11, e.getY() - 8, cursorImage
                    .getImgIcon()
                    .getIconWidth(), cursorImage.getImgIcon().getIconHeight());

            cursorSelect.setVisible(true);

            redispatchMouseEvent(e, true);
        }

        @Override
        public void mouseMoved(MouseEvent e) {

            cursorSelect.setVisible(false);
//            if (panel.getBounds().contains(e.getPoint())) {


            cursorImage.setLocation(e.getPoint());

            cursorImage.setBounds(e.getX() - 11, e.getY() - 8, cursorImage
                    .getImgIcon()
                    .getIconWidth(), cursorImage.getImgIcon()
                    .getIconHeight());


            cursorImage.setVisible(true);

            redispatchMouseEvent(e, true);

        }

        public void redispatchMouseEvent(MouseEvent e,
                                         boolean repaint) {
            Point glassPanePoint = e.getPoint();
            Container container = contentPane;
            Point containerPoint = SwingUtilities.convertPoint(glassPane,
                    glassPanePoint,
                    contentPane);
            //The mouse event is probably over the content pane.
            //Find out exactly which component it's over.
            Component component =
                      SwingUtilities.getDeepestComponentAt(container,
                    containerPoint.x,
                    containerPoint.y);

            if (component != null) {
                //Forward events
                Point componentPoint = SwingUtilities.convertPoint(glassPane,
                        glassPanePoint,
                        component);
                component.dispatchEvent(new MouseEvent(component,
                        e.getID(),
                        e.getWhen(),
                        e.getModifiers(),
                        componentPoint.x,
                        componentPoint.y,
                        e.getClickCount(),
                        e.isPopupTrigger()));
            } else {
                glassPanePoint = null;
            }

            //Update the glass pane if requested.
            if (repaint) {
                glassPane.repaint();
            }
        }
    }

    class MouseEventRedispatcher extends MouseAdapter {

        private final JPanel glassPane;

        private final Container contentPane;

        public MouseEventRedispatcher(JPanel glassPane, Container contentPane) {
            this.glassPane = glassPane;
            this.contentPane = contentPane;
        }

        public void mouseMoved(MouseEvent e) {
            redispatchMouseEvent(e, true);
        }

        public void mouseDragged(MouseEvent e) {
            redispatchMouseEvent(e, true);
        }

        public void mouseClicked(MouseEvent e) {
            redispatchMouseEvent(e, true);
        }

        public void mouseEntered(MouseEvent e) {
            redispatchMouseEvent(e, true);
        }

        public void mouseExited(MouseEvent e) {
            redispatchMouseEvent(e, true);
        }

        public void mousePressed(MouseEvent e) {
            redispatchMouseEvent(e, true);
        }

        public void mouseReleased(MouseEvent e) {
            redispatchMouseEvent(e, true);
        }


        //A more finished version of this method would
        //handle mouse-dragged events specially.
        private void redispatchMouseEvent(MouseEvent e,
                                          boolean repaint) {
            Point glassPanePoint = e.getPoint();
            Container container = contentPane;
            Point containerPoint = SwingUtilities.convertPoint(glassPane,
                    glassPanePoint,
                    contentPane);
            //The mouse event is probably over the content pane.
            //Find out exactly which component it's over.
            Component component =
                      SwingUtilities.getDeepestComponentAt(container,
                    containerPoint.x,
                    containerPoint.y);

            if (component != null) {
                //Forward events
                Point componentPoint = SwingUtilities.convertPoint(glassPane,
                        glassPanePoint,
                        component);
                component.dispatchEvent(new MouseEvent(component,
                        e.getID(),
                        e.getWhen(),
                        e.getModifiers(),
                        componentPoint.x,
                        componentPoint.y,
                        e.getClickCount(),
                        e.isPopupTrigger()));
            } else {
                glassPanePoint = null;
            }

            //Update the glass pane if requested.
            if (repaint) {
                glassPane.repaint();
            }
        }
    }
}
