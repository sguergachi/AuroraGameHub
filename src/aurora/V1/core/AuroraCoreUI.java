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

import aurora.engine.V1.Logic.ANuance;
import aurora.engine.V1.Logic.ASurface;
import aurora.engine.V1.UI.AButton;
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
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

    private ADialog warningDialog;

    private ADialog errorDialog;

    private AImage imgLogo;

    private AImage imgKeyIcon;

    private AImagePane paneBackground;

    private AImagePane paneBottom;

    private AImagePane paneFrameControl;

    private AImagePane paneTopImage;

    private ASurface ressources;

    private ANuance vi;

    private Font regularFont;

    private Font boldFont;

    private JButton btnExit;

    private JButton btnMinimize;

    private JFrame frame;

    private JPanel paneCenter;

    private JPanel paneKeyToPress;

    private JPanel southFromTopPanel;

    private JPanel logoPanel;

    private JPanel screenLabelPanel;

    private JPanel versionPanel;

    private JPanel paneHeaderOfCenterFromBottom;

    private JPanel paneCenterFromBottom;

    private JPanel paneTime;

    private JPanel paneInfo;

    private JPanel paneFrameControlContainer;

    private JPanel paneUserSpace;

    private JLabel lblVersion;

    private JLabel lblInfo;

    private JLabel lblKeyAction;

    private AuroraMini miniMode;

    private MinimizeListener minimizeHandler;

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
        aFrame.setUndecorated(true);
        aFrame.setBackground(Color.BLACK);
        aFrame.setResizable(false);
        aFrame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        aFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ressources = new ASurface("");

    }

    public void setUI() throws UnsupportedAudioFileException, IOException,
                               LineUnavailableException, InterruptedException,
                               FontFormatException {


        //////////////////////////////////////
        //Determine Global Size based on Screen Size
        //////////////////////////////////////

        //TODO work on Screen Gui Change

        screenWidth = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getScreenDevices()[0].getDisplayMode().getWidth();
        screenHeight = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getScreenDevices()[0].getDisplayMode().getHeight();

        System.out.println("Current Screen Ressolution: "
                           + screenWidth + "x" + screenHeight);

        if (screenWidth >= 1680 && screenHeight >= 1050) {
            isLargeScreen = true;
        } else {
            isLargeScreen = false;
        }

        // LargeScreen = false;
        System.out.println("High Resolution Boolean = " + isLargeScreen);

        ////Set Size For UI

        setSizes();

        /////////////////
        //Start Preparation///////////////////////////////////////
        ////////////////


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
        ///////////////////////
        // The Background Panel Contains The Background Image for the Window as
        // Well as all components found in the window
        ///////////////////////


        paneBackground = new AImagePane("Aurora_Background.png", frame
                .getSize().width, frame.getSize().height, true);

        paneBackground.setPreferredSize(frame.getSize());
        paneBackground.setLayout(new BoxLayout(paneBackground,
                BoxLayout.Y_AXIS));

        ///////////////////////
        // The Top Panel Contains Header Image as well as the Frame Buttons:
        //  Exit and Minimize
        ///////////////////////

        paneTopImage = new AImagePane("Aurora_Header1.png",
                frame.getSize().width, (frame.getSize().height / 6), true);
        paneTopImage.setPreferredSize(new Dimension(frame.getSize().width,
                (frame.getSize().height / 6)));

        paneTopImage.setIgnoreRepaint(true);
        paneTopImage.setLayout(new BorderLayout());


        ///////////////////////
        // The Center Panel
        // and maintains a space between top and bottom panel
        ///////////////////////

        paneCenter = new JPanel(true);
        paneCenter.setPreferredSize(new Dimension(frame.getSize().width, frame
                .getSize().height - (frame.getSize().height / 6 + frame
                .getSize().height / 6)));
        paneCenter.setOpaque(false);
        paneCenter.setLayout(new BorderLayout());
        paneCenter.setIgnoreRepaint(true);

        ///////////////////////
        // The Bottom Panel Contains the Footer Image as well as
        // the Time and the Login Controls
        ///////////////////////
        paneBottom = new AImagePane("Aurora_Footer1.png",
                frame.getSize().width, frame.getSize().height / 6, true);
        paneBottom.setPreferredSize(new Dimension(frame.getSize().width,
                frame.getSize().height / 6));
        paneBottom.setOpaque(false);
        paneBottom.setLayout(new BorderLayout());




        //////////////////////////////////////////////////////////////////////////
        //Configure Panels:                                            //////////////////////////////////////////
        // Add specific UI components to each Panel///////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////


        //Create V.I
        vi = new ANuance();


        /////////////////
        //Setup Buttons///////////////////////////////////////
        ////////////////

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




        /////////
        // Top Panel
        /////////


        //// Frame Buttons

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


        //// Logo Image

        imgLogo = new AImage("Logo_Aurora.png", logoWidth, logoHeight);
        logoPanel = new JPanel();
        logoPanel.setOpaque(false);
        logoPanel.add(imgLogo);
        logoPanel.setPreferredSize(imgLogo.getSize());

        paneTopImage.add(BorderLayout.CENTER, logoPanel);

        ////////////////
        // Bottom Panel/////////////////////////////
        ////////////////

        paneCenterFromBottom = new JPanel(new BorderLayout());
        paneCenterFromBottom.setOpaque(false);
        paneBottom.add(BorderLayout.CENTER, paneCenterFromBottom);

        /// Welcome Label
        lblInfo = new JLabel(vi.VI(ANuance.inx_Welcome));
        lblInfo.setOpaque(false);
        lblInfo.setForeground(Color.LIGHT_GRAY);
        lblInfo.setFont(regularFont.deriveFont(Font.PLAIN, welcomeFontSize));

        paneInfo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        paneInfo.setOpaque(false);
        paneInfo.add(lblInfo);

        screenLabelPanel = new JPanel(new BorderLayout());
        screenLabelPanel.setOpaque(false);
        screenLabelPanel.add(BorderLayout.NORTH, paneInfo);
        paneBottom.add(BorderLayout.PAGE_START, screenLabelPanel);




        /// Time Label

        paneHeaderOfCenterFromBottom = new JPanel(new BorderLayout());
        lblTime = new ATimeLabel();
        lblTime.setFont(boldFont.deriveFont(Font.PLAIN, timeFontSize));
        lblTime.setForeground(new Color(80, 126, 222));
        paneTime = new JPanel(new BorderLayout());
        paneTime.setOpaque(false);
        paneTime.add(lblTime, BorderLayout.NORTH);
        paneHeaderOfCenterFromBottom.add(BorderLayout.EAST, paneTime);
        paneHeaderOfCenterFromBottom.setOpaque(false);



        ///Key Press Panel
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

        ///User Space
        paneUserSpace = new JPanel();
        paneUserSpace.setOpaque(false);
        paneUserSpace
                .setLayout(new BoxLayout(paneUserSpace, BoxLayout.Y_AXIS));

        paneCenterFromBottom.add(BorderLayout.CENTER, paneUserSpace);


        /// Version Label

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

    public void setInfoLabel(JLabel infoLabel) {
        this.lblInfo = infoLabel;
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

    public JLabel getInfoLabel() {
        return lblInfo;
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

    public JPanel getInfoPanel() {
        return paneInfo;
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
    }//end SFX

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
            //SOUND
//            try {
//                sfxMinimize.Play();
//            } catch (UnsupportedAudioFileException ex) {
//                Logger.getLogger(AuroraUI.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (IOException ex) {
//                Logger.getLogger(AuroraUI.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (LineUnavailableException ex) {
//                Logger.getLogger(AuroraUI.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(AuroraUI.class.getName()).log(Level.SEVERE, null, ex);
//            }

            //ENABLE MINI MODE// indev

            minimizeAurora(arg);

            //ENABLE BASIC MINIMIZE
            //ui.getFrame().setState(Frame.ICONIFIED);



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
}
