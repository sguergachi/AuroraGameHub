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

import aurora.engine.V1.Logic.aSurface;
import aurora.engine.V1.Logic.aXAVI;
import aurora.engine.V1.UI.*;
import java.awt.*;
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
import javax.swing.*;

/**
 *
 * @author Sammy
 * @version 0.3
 */
public class AuroraCoreUI {

    private final String revision = "255";
    private final String version =
            "  //BUILD: " + getResourceBundleToken("BUILD")
            + "  //REVISION: " + revision
            + "  //AURORA.ENGINE.VERSION = 0.1." + (Integer.parseInt(revision) - 1)
            + "  //  -- ALPHA BUILT ON: 9/25/2012 --   //";
    private int topPanelSize;
    private int centerPanelSize;
    private int bottomPanelSize;
    private int controlHeight;
    private int controlWidth;
    private int welcomeFontSize;
    private int keysFontSize;
    private int keyIconWidth;
    private int keyIconHeight;
    private int versionFontSize;
    private int timeFontSize;
    private int logoHeight;
    private int logoWidth;
    private int exitButtonWidth;
    private int exitButtonHeight;
    private int minimizeButtonWidth;
    private int minimizeButtonHeight;
    private int screenWidth;
    private int screenHeight;
    private boolean isLargeScreen;
    private aDialog warningDialog;
    private aDialog errorDialog;
    private aImage logoImage;
    private aImage keyIconImage;
    private aImagePane backgroundImagePane;
    private aImagePane bottomImagePane;
    private aImagePane frameControlImagePane;
    private aImagePane topImagePane;
    private aSurface resource;
    private aXAVI vi;
    private Font regularFont;
    private Font boldFont;
    private JButton exitButton;
    private JButton minimizeButton;    
    private JFrame frame;
    private JPanel centerPanel;
    private JPanel keyToPressPanel;
    private JPanel southFromTopPanel;
    private JPanel logoPanel;
    private JPanel screenLabelPanel;
    private JPanel versionPanel;
    private JPanel headerOfCenterFromBottomPanel;
    private JPanel centerFromBottomPanel;
    private JPanel timePanel;
    private JPanel infoPanel;
    private JPanel frameControlContainerPanel;
    private JPanel userSpacePanel;
    private JLabel versionLabel;
    private JLabel infoLabel;
    private JLabel keyActionLabel;
    private AuroraMini miniMode;
    private MinimizeListener minimizeHandler;
    public static aTimeLabel timeLabel;
    final static ResourceBundle resourceBundle = ResourceBundle.getBundle("version");
//    public aSound sfxTheme;
//    public aSound sfxClunk;
//    private aSound sfxExit;
//    private aSound sfxMinimize;
//    private aSound sfxWarning;
    
 
    /**
    .------------------------------------------------------------------------.
    |     AuroraCoreUI() Method
    .------------------------------------------------------------------------.
    |
    |
    |
    |
   */
    public AuroraCoreUI(JFrame frame) {
        this.frame = frame;
        frame.setUndecorated(true);
        frame.setBackground(Color.BLACK);
        frame.setResizable(false);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        resource = new aSurface("");

    }

    public void setUI() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException, FontFormatException {


        //////////////////////////////////////
        //Determine Global Size based on Screen Size
        //////////////////////////////////////

        //TODO work on Screen Gui Change

        screenWidth = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].getDisplayMode().getWidth();
        screenHeight = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].getDisplayMode().getHeight();

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
            regularFont = Font.createFont(Font.TRUETYPE_FONT, new URL(resource.getSurfacePath() + "/aurora/V1/resources/AGENCYR.TTF").openStream());
            boldFont = Font.createFont(Font.TRUETYPE_FONT, new URL(resource.getSurfacePath() + "/aurora/V1/resources/AGENCYB.TTF").openStream());
        } catch (MalformedURLException ex) {
            try {
                regularFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/aurora/V1/resources/AGENCYR.TTF"));
                boldFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/aurora/V1/resources/AGENCYB.TTF"));

            } catch (Exception exx) {
                System.out.println("ERROR In Getting Font Resourcess");
            }
        }
        ///////////////////////
        // The Background Panel Contains The Background Image for the Window as
        // Well as all components found in the window
        ///////////////////////


        backgroundImagePane = new aImagePane("Aurora_Background.png", frame.getSize().width, frame.getSize().height, true);

        backgroundImagePane.setPreferredSize(frame.getSize());
        backgroundImagePane.setLayout(new BoxLayout(backgroundImagePane, BoxLayout.Y_AXIS));

        ///////////////////////
        // The Top Panel Contains Header Image as well as the Frame Buttons:
        //  Exit and Minimize
        ///////////////////////

        topImagePane = new aImagePane("Aurora_Header1.png", frame.getSize().width, (frame.getSize().height / 6), true);
        topImagePane.setPreferredSize(new Dimension(frame.getSize().width, (frame.getSize().height / 6)));

        topImagePane.setIgnoreRepaint(true);
        topImagePane.setLayout(new BorderLayout());


        ///////////////////////
        // The Center Panel
        // and maintains a space between top and bottom panel
        ///////////////////////

        centerPanel = new JPanel(true);
        centerPanel.setPreferredSize(new Dimension(frame.getSize().width, frame.getSize().height - (frame.getSize().height / 6 + frame.getSize().height / 6)));
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setIgnoreRepaint(true);

        ///////////////////////
        // The Bottom Panel Contains the Footer Image as well as
        // the Time and the Login Controls
        ///////////////////////
        bottomImagePane = new aImagePane("Aurora_Footer1.png", frame.getSize().width, frame.getSize().height / 6, true);
        bottomImagePane.setPreferredSize(new Dimension(frame.getSize().width, frame.getSize().height / 6));
        bottomImagePane.setOpaque(false);
        bottomImagePane.setLayout(new BorderLayout());




        //////////////////////////////////////////////////////////////////////////
        //Configure Panels:                                            //////////////////////////////////////////
        // Add specific UI components to each Panel///////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////


        //Create V.I
        vi = new aXAVI();


        /////////////////
        //Setup Buttons///////////////////////////////////////
        ////////////////

        exitButton = new aButton("Aurora_Close_normal.png", "Aurora_Close_down.png", "Aurora_Close_over.png", exitButtonWidth, exitButtonHeight);
        exitButton.addActionListener(new CloseListener());
        exitButton.setToolTipText("Exit");
        minimizeButton = new aButton("Aurora_Desktop_normal.png", "Aurora_Desktop_down.png", "Aurora_Desktop_over.png", minimizeButtonWidth, minimizeButtonHeight);
        minimizeHandler = new MinimizeListener(this, AuroraMini.MINIMIZE_MODE);
        minimizeButton.addActionListener(minimizeHandler);
        minimizeButton.setToolTipText("Minimize");




        /////////
        // Top Panel
        /////////


        //// Frame Buttons

        frameControlImagePane = new aImagePane("Aurora_FrameButton1.png", controlWidth, controlHeight);
        frameControlImagePane.setImageHeight(controlHeight);
        frameControlImagePane.setOpaque(false);
        frameControlImagePane.add(minimizeButton);
        frameControlImagePane.add(exitButton);
        
        frameControlContainerPanel = new JPanel(new BorderLayout());
        frameControlContainerPanel.setOpaque(false);
        frameControlContainerPanel.add(frameControlImagePane, BorderLayout.NORTH);

        southFromTopPanel = new JPanel();
        southFromTopPanel.setLayout(new BorderLayout());
        southFromTopPanel.setOpaque(false);
        southFromTopPanel.add(BorderLayout.LINE_END, frameControlContainerPanel);
        southFromTopPanel.setPreferredSize(new Dimension(frame.getWidth(), controlHeight + 5));

        topImagePane.add(BorderLayout.SOUTH, southFromTopPanel);


        //// Logo Image

        logoImage = new aImage("Logo_Aurora.png", logoWidth, logoHeight);
        logoPanel = new JPanel();
        logoPanel.setOpaque(false);
        logoPanel.add(logoImage);
        logoPanel.setPreferredSize(logoImage.getSize());

        topImagePane.add(BorderLayout.CENTER, logoPanel);

        ////////////////
        // Bottom Panel/////////////////////////////
        ////////////////

        centerFromBottomPanel = new JPanel(new BorderLayout());
        centerFromBottomPanel.setOpaque(false);
        bottomImagePane.add(BorderLayout.CENTER, centerFromBottomPanel);

        /// Welcome Label
        infoLabel = new JLabel(vi.VI(aXAVI.inx_Welcome));
        infoLabel.setOpaque(false);
        infoLabel.setForeground(Color.LIGHT_GRAY);
        infoLabel.setFont(regularFont.deriveFont(Font.PLAIN, welcomeFontSize));

        infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        infoPanel.setOpaque(false);
        infoPanel.add(infoLabel);

        screenLabelPanel = new JPanel(new BorderLayout());
        screenLabelPanel.setOpaque(false);
        screenLabelPanel.add(BorderLayout.NORTH, infoPanel);
        bottomImagePane.add(BorderLayout.PAGE_START, screenLabelPanel);




        /// Time Label

        headerOfCenterFromBottomPanel = new JPanel(new BorderLayout());
        timeLabel = new aTimeLabel();
        timeLabel.setFont(boldFont.deriveFont(Font.PLAIN, timeFontSize));
        timeLabel.setForeground(new Color(80, 126, 222));
        timePanel = new JPanel(new BorderLayout());
        timePanel.setOpaque(false);
        timePanel.add(timeLabel, BorderLayout.NORTH);
        headerOfCenterFromBottomPanel.add(BorderLayout.EAST, timePanel);
        headerOfCenterFromBottomPanel.setOpaque(false);



        ///Key Press Panel
        keyToPressPanel = new JPanel();
        keyToPressPanel.setOpaque(false);


        keyIconImage = new aImage("KeyboardKeys/enter.png");
        keyIconImage.setImageSize(keyIconWidth, keyIconHeight);
        keyActionLabel = new JLabel(" Select ");

        keyActionLabel.setFont(regularFont.deriveFont(Font.PLAIN, keysFontSize));
        keyActionLabel.setForeground(Color.YELLOW);




        headerOfCenterFromBottomPanel.add(BorderLayout.WEST, keyToPressPanel);
        centerFromBottomPanel.add(BorderLayout.NORTH, headerOfCenterFromBottomPanel);

        ///User Space
        userSpacePanel = new JPanel();
        userSpacePanel.setOpaque(false);
        userSpacePanel.setLayout(new BoxLayout(userSpacePanel, BoxLayout.Y_AXIS));

        centerFromBottomPanel.add(BorderLayout.CENTER, userSpacePanel);


        /// Version Label

        versionLabel = new JLabel(version);
        versionLabel.setOpaque(false);
        versionLabel.setForeground(Color.LIGHT_GRAY);
        versionLabel.setFont(regularFont.deriveFont(Font.PLAIN, versionFontSize));


        versionPanel = new JPanel();
        versionPanel.setOpaque(false);
        versionPanel.setLayout(new BorderLayout());
        versionPanel.add(BorderLayout.WEST, versionLabel);
        bottomImagePane.add(BorderLayout.PAGE_END, versionPanel);




        ///Finalize

        //////////////////////////////////
        // Add All 3 Main Panels To     //
        // Background Panel             //
        //////////////////////////////////

        backgroundImagePane.add(topImagePane);

        backgroundImagePane.add(centerPanel);

        backgroundImagePane.add(bottomImagePane);


        frame.addKeyListener(new FrameKeyListener());
        frame.requestFocus();

    }

    public aDialog getWarningDialog() {
        return warningDialog;
    }

    public void setWarningDialog(aDialog warningDialog) {
        this.warningDialog = warningDialog;
    }

    public aDialog getErrorDialog() {
        return errorDialog;
    }

    public void setErrorDialog(aDialog errorDialog) {
        this.errorDialog = errorDialog;
    }

    public JPanel getLogoPanel() {
        return logoPanel;
    }

    public void setLogoPanel(JPanel logoPanel) {
        this.logoPanel = logoPanel;
    }

    public JPanel getTimePanel() {
        return timePanel;
    }

    public void setTimePanel(JPanel timePanel) {
        this.timePanel = timePanel;
    }

    public JPanel getFrameControlContainerPanel() {
        return frameControlContainerPanel;
    }

    public void setFrameControlContainerPanel(JPanel frameControlContainerPanel) {
        this.frameControlContainerPanel = frameControlContainerPanel;
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

    public void setBackgroundImagePane(aImagePane backgroundImagePane) {
        this.backgroundImagePane = backgroundImagePane;
    }

    public void setBottomImagePane(aImagePane bottomImagePane) {
        this.bottomImagePane = bottomImagePane;
    }

    public void setCenterPanel(JPanel centerPanel) {
        this.centerPanel = centerPanel;
    }

    public void setCenterFromBottomPanel(JPanel centerFromBottomPanel) {
        this.centerFromBottomPanel = centerFromBottomPanel;
    }

    public void setSouthFromTopPanel(JPanel southFromTopPanel) {
        this.southFromTopPanel = southFromTopPanel;
    }

    public void setFrameControlImagePane(aImagePane frameControlImagePane) {
        this.frameControlImagePane = frameControlImagePane;
    }

    public void setTopImagePane(aImagePane topImagePane) {
        this.topImagePane = topImagePane;
    }

    public void setLogoImage(aImage logoImage) {
        this.logoImage = logoImage;
    }

    public void setInfoLabel(JLabel infoLabel) {
        this.infoLabel = infoLabel;
    }

    public void setVi(aXAVI vi) {
        this.vi = vi;
    }

    public void setUserbarPanel(JPanel userbarPanel) {
        this.userSpacePanel = userbarPanel;
    }

    public String getRevision() {
        return revision;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public void setKeyIconImage(aImage keyIconImage) {
        this.keyIconImage = keyIconImage;
    }

    public void setKeyActionLabel(JLabel keyActionLabel) {
        this.keyActionLabel = keyActionLabel;
    }

    public static void setTimeLabel(aTimeLabel timeLabel) {
        AuroraCoreUI.timeLabel = timeLabel;
    }

    public void setVersionLabel(JLabel versionLabel) {
        this.versionLabel = versionLabel;
    }

    public void setHeaderOfCenterFromBottomPanel(JPanel headerOfCenterFromBottomPanel) {
        this.headerOfCenterFromBottomPanel = headerOfCenterFromBottomPanel;
    }

    public void setKeyToPressPanel(JPanel keyToPressPanel) {
        this.keyToPressPanel = keyToPressPanel;
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
    public aImage getKeyIconImage() {
        return keyIconImage;
    }

    public JLabel getKeyActionLabel() {
        return keyActionLabel;
    }

    public JLabel getVersionLabel() {
        return versionLabel;
    }

    public JPanel getHeaderOfCenterFromBottomPanel() {
        return headerOfCenterFromBottomPanel;
    }

    public JPanel getKeyToPressPanel() {
        return keyToPressPanel;
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
        return exitButton;
    }

    public JButton getMinimizeButton() {
        return minimizeButton;
    }

    public JLabel getInfoLabel() {
        return infoLabel;
    }

    public aXAVI getVi() {
        return vi;
    }

    public JFrame getFrame() {
        return frame;
    }

    public static aTimeLabel getTimeLabel() {
        return timeLabel;
    }

    public JPanel getUserSpacePanel() {
        return userSpacePanel;
    }

    public aImage getLogoImage() {
        return logoImage;
    }

    public aImagePane getBackgroundImagePane() {
        return backgroundImagePane;
    }

    public JPanel getInfoPanel() {
        return infoPanel;
    }

    public aImagePane getBottomImagePane() {
        return bottomImagePane;
    }

    public JPanel getCenterPanel() {
        return centerPanel;
    }

    public JPanel getCenterFromBottomPanel() {
        return centerFromBottomPanel;
    }

    public JPanel getSouthFromTopPanel() {
        return southFromTopPanel;
    }

    public aImagePane getFrameControlImagePane() {
        return frameControlImagePane;
    }

    public aImagePane getTopImagePane() {
        return topImagePane;
    }

    public void setSFX() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        //////////
        /////Background Sound
        ///////////
        ///TODO FIX SOUND BUG
//
//        try {
//            sfxTheme = new aSound(aSound.sfxTheme, true);
//
//            ///////////
//            //Play Sound//////////////////////////
//            ///////////
//
//
//            sfxExit = new aSound(aSound.sfxAlert, false);
//            sfxMinimize = new aSound(aSound.sfxButton, false);
//            sfxWarning = new aSound(aSound.sfxButton, false);
//            sfxClunk = new aSound(aSound.sfxClunk, false);
//
//        } catch (MalformedURLException ex) {
//            err = new aDialog(aDialog.aDIALOG_ERROR, "A Sound " + vi.VI(vi.inx_Error) + " Occured!");
//            err.setVisible(true);
//        }
    }//end SFX

    public void showExitDialog() {
        if (warningDialog == null) {
            warningDialog = new aDialog(aDialog.aDIALOG_WARNING, "Are You Sure you want to " + vi.VI(vi.inx_Exit) + "?", boldFont);


            warningDialog.setButtonListener(new ActionListener() {
                private aDialog err;

                public void actionPerformed(ActionEvent e) {
                    ///SOUND
//                    try {
//                        sfxWarning.Play();
//                    } catch (UnsupportedAudioFileException ex) {
//                        Logger.getLogger(AuroraUI.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (IOException ex) {
//                        Logger.getLogger(AuroraUI.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (LineUnavailableException ex) {
//                        Logger.getLogger(AuroraUI.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (InterruptedException ex) {
//                        Logger.getLogger(AuroraUI.class.getName()).log(Level.SEVERE, null, ex);
//                    }

                    System.exit(0);
                }
            });
            warningDialog.showDialog();


        }
        warningDialog.setVisible(true);
        //SOUND
//        try {
//            sfxExit.Play();
//        } catch (UnsupportedAudioFileException ex) {
//            Logger.getLogger(AuroraUI.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(AuroraUI.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (LineUnavailableException ex) {
//            Logger.getLogger(AuroraUI.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(AuroraUI.class.getName()).log(Level.SEVERE, null, ex);
//        }
        try {
            try {
                setSFX();
            } catch (InterruptedException ex) {
                Logger.getLogger(AuroraCoreUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(AuroraCoreUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AuroraCoreUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(AuroraCoreUI.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //Get Build Version Of Application
    private static String getResourceBundleToken(String propertyToken) {
        String msg = "";
        try {
            msg = resourceBundle.getString(propertyToken);
        } catch (MissingResourceException e) {
            System.err.println("Token ".concat(propertyToken).concat(" not in Property file!"));
        }
        return msg;
    }

    public String getOS() {
        return System.getProperty("os.name");
    }

    public void setSurface(aSurface resource) {
        this.resource = resource;
    }

    public aSurface getResource() {
        return resource;
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
