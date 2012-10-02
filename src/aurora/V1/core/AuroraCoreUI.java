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

    private final String Revision = "255";
    private final String Version =
            "  //BUILD: " + getRbTok("BUILD")
            + "  //REVISION: " + Revision
            + "  //AURORA.ENGINE.VERSION = 0.1." + (Integer.parseInt(Revision) - 1)
            + "  //  -- ALPHA BUILT ON: 9/25/2012 --   //";
    private JFrame frame;
    private aImagePane pnlBackground;
    private aImagePane pnlTop;
    private aImagePane pnlBottom;
    private JPanel pnlCenter;
    private JPanel pnlSouthFromTop;
    private JPanel pnlScreenLable;
    private JPanel pnlVersion;
    private aImagePane pnlFrameControl;
    private aImage imgLogo;
    private JLabel lblVersion;
    private JLabel lblInfo;
    public static aTimeLabel lblTime;
    private JButton btnExit;
    private JButton btnMin;
    final static ResourceBundle rb = ResourceBundle.getBundle("version");
    private JPanel pnlHeaderOfCenterFromBottom;
    private JPanel pnlCenterFromBottom;
    private aDialog dilgWarning;
    private aDialog err;
//    public aSound sfxTheme;
//    public aSound sfxClunk;
//    private aSound sfxExit;
//    private aSound sfxMinimize;
//    private aSound sfxWarning;
    private JPanel pnlUserSpace;
    private aXAVI vi;
    private Font FontRegular;
    private JPanel pnlKeyToPress;
    private JLabel lblKeyAction;
    private aImage imgKeyIco;
    private JPanel pnlLogo;
    private AuroraMini miniMode;
    private boolean LargeScreen;
    private int SIZE_pnlTop;
    private int SIZE_pnlCenter;
    private int SIZE_pnlBottom;
    private int SIZE_controlHeight;
    private int SIZE_controlWidth;
    private int SIZE_welcomeFont;
    private int SIZE_keysFont;
    private int SIZE_KeyIconWidth;
    private int SIZE_KeyIconHeight;
    private int SIZE_VersionFont;
    private int SIZE_TimeFont;
    private int SIZE_logoHeight;
    private int SIZE_logoWidth;
    private int SIZE_btnExitWidth;
    private int SIZE_btnExitHeight;
    private int SIZE_btnMinWidth;
    private int SIZE_btnMinHeight;
    private Font FontBold;
    private aSurface resource;
    private int SIZE_ScreenWidth;
    private int SIZE_ScreenHeight;
    private MinimizeListener MinimizeHandler;
    private JPanel pnlTime;
    private JPanel pnlInfo;
    private JPanel pnlFrameControlContainer;

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

        SIZE_ScreenWidth = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].getDisplayMode().getWidth();
        SIZE_ScreenHeight = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].getDisplayMode().getHeight();

        System.out.println("Current Screen Ressolution: "
                + SIZE_ScreenWidth + "x" + SIZE_ScreenHeight);

        if (SIZE_ScreenWidth >= 1680 && SIZE_ScreenHeight >= 1050) {
            LargeScreen = true;
        } else {
            LargeScreen = false;
        }

        // LargeScreen = false;
        System.out.println("High Resolution Boolean = " + LargeScreen);

        ////Set Size For UI

        setSizes();

        /////////////////
        //Start Preparation///////////////////////////////////////
        ////////////////


        //Get Font

        try {
            FontRegular = Font.createFont(Font.TRUETYPE_FONT, new URL(resource.getSurfacePath() + "/aurora/V1/resources/AGENCYR.TTF").openStream());
            FontBold = Font.createFont(Font.TRUETYPE_FONT, new URL(resource.getSurfacePath() + "/aurora/V1/resources/AGENCYB.TTF").openStream());
        } catch (MalformedURLException ex) {
            try {
                FontRegular = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/aurora/V1/resources/AGENCYR.TTF"));
                FontBold = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/aurora/V1/resources/AGENCYB.TTF"));

            } catch (Exception exx) {
                System.out.println("ERROR In Getting Font Resourcess");
            }
        }
        ///////////////////////
        // The Background Panel Contains The Background Image for the Window as
        // Well as all components found in the window
        ///////////////////////


        pnlBackground = new aImagePane("Aurora_Background.png", frame.getSize().width, frame.getSize().height, true);

        pnlBackground.setPreferredSize(frame.getSize());
        pnlBackground.setLayout(new BoxLayout(pnlBackground, BoxLayout.Y_AXIS));

        ///////////////////////
        // The Top Panel Contains Header Image as well as the Frame Buttons:
        //  Exit and Minimize
        ///////////////////////

        pnlTop = new aImagePane("Aurora_Header1.png", frame.getSize().width, (frame.getSize().height / 6), true);
        pnlTop.setPreferredSize(new Dimension(frame.getSize().width, (frame.getSize().height / 6)));

        pnlTop.setIgnoreRepaint(true);
        pnlTop.setLayout(new BorderLayout());


        ///////////////////////
        // The Center Panel
        // and maintains a space between top and bottom panel
        ///////////////////////

        pnlCenter = new JPanel(true);
        pnlCenter.setPreferredSize(new Dimension(frame.getSize().width, frame.getSize().height - (frame.getSize().height / 6 + frame.getSize().height / 6)));
        pnlCenter.setOpaque(false);
        pnlCenter.setLayout(new BorderLayout());
        pnlCenter.setIgnoreRepaint(true);

        ///////////////////////
        // The Bottom Panel Contains the Footer Image as well as
        // the Time and the Login Controls
        ///////////////////////
        pnlBottom = new aImagePane("Aurora_Footer1.png", frame.getSize().width, frame.getSize().height / 6, true);
        pnlBottom.setPreferredSize(new Dimension(frame.getSize().width, frame.getSize().height / 6));
        pnlBottom.setOpaque(false);
        pnlBottom.setLayout(new BorderLayout());




        //////////////////////////////////////////////////////////////////////////
        //Configure Panels:                                            //////////////////////////////////////////
        // Add specific UI components to each Panel///////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////


        //Create V.I
        vi = new aXAVI();


        /////////////////
        //Setup Buttons///////////////////////////////////////
        ////////////////

        btnExit = new aButton("Aurora_Close_normal.png", "Aurora_Close_down.png", "Aurora_Close_over.png", SIZE_btnExitWidth, SIZE_btnExitHeight);
        btnExit.addActionListener(new CloseListener());
        btnExit.setToolTipText("Exit");
        btnMin = new aButton("Aurora_Desktop_normal.png", "Aurora_Desktop_down.png", "Aurora_Desktop_over.png", SIZE_btnMinWidth, SIZE_btnMinHeight);
        MinimizeHandler = new MinimizeListener(this, AuroraMini.MINIMIZE_MODE);
        btnMin.addActionListener(MinimizeHandler);
        btnMin.setToolTipText("Minimize");




        /////////
        // Top Panel
        /////////


        //// Frame Buttons

        pnlFrameControl = new aImagePane("Aurora_FrameButton1.png", SIZE_controlWidth, SIZE_controlHeight);
        pnlFrameControl.setImageHeight(SIZE_controlHeight);
        pnlFrameControl.setOpaque(false);
        pnlFrameControl.add(btnMin);
        pnlFrameControl.add(btnExit);
        
        pnlFrameControlContainer = new JPanel(new BorderLayout());
        pnlFrameControlContainer.setOpaque(false);
        pnlFrameControlContainer.add(pnlFrameControl, BorderLayout.NORTH);

        pnlSouthFromTop = new JPanel();
        pnlSouthFromTop.setLayout(new BorderLayout());
        pnlSouthFromTop.setOpaque(false);
        pnlSouthFromTop.add(BorderLayout.LINE_END, pnlFrameControlContainer);
        pnlSouthFromTop.setPreferredSize(new Dimension(frame.getWidth(), SIZE_controlHeight + 5));

        pnlTop.add(BorderLayout.SOUTH, pnlSouthFromTop);


        //// Logo Image

        imgLogo = new aImage("Logo_Aurora.png", SIZE_logoWidth, SIZE_logoHeight);
        pnlLogo = new JPanel();
        pnlLogo.setOpaque(false);
        pnlLogo.add(imgLogo);
        pnlLogo.setPreferredSize(imgLogo.getSize());

        pnlTop.add(BorderLayout.CENTER, pnlLogo);

        ////////////////
        // Bottom Panel/////////////////////////////
        ////////////////

        pnlCenterFromBottom = new JPanel(new BorderLayout());
        pnlCenterFromBottom.setOpaque(false);
        pnlBottom.add(BorderLayout.CENTER, pnlCenterFromBottom);

        /// Welcome Label
        lblInfo = new JLabel(vi.VI(aXAVI.inx_Welcome));
        lblInfo.setOpaque(false);
        lblInfo.setForeground(Color.LIGHT_GRAY);
        lblInfo.setFont(FontRegular.deriveFont(Font.PLAIN, SIZE_welcomeFont));

        pnlInfo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlInfo.setOpaque(false);
        pnlInfo.add(lblInfo);

        pnlScreenLable = new JPanel(new BorderLayout());
        pnlScreenLable.setOpaque(false);
        pnlScreenLable.add(BorderLayout.NORTH, pnlInfo);
        pnlBottom.add(BorderLayout.PAGE_START, pnlScreenLable);




        /// Time Label

        pnlHeaderOfCenterFromBottom = new JPanel(new BorderLayout());
        lblTime = new aTimeLabel();
        lblTime.setFont(FontBold.deriveFont(Font.PLAIN, SIZE_TimeFont));
        lblTime.setForeground(new Color(80, 126, 222));
        pnlTime = new JPanel(new BorderLayout());
        pnlTime.setOpaque(false);
        pnlTime.add(lblTime, BorderLayout.NORTH);
        pnlHeaderOfCenterFromBottom.add(BorderLayout.EAST, pnlTime);
        pnlHeaderOfCenterFromBottom.setOpaque(false);



        ///Key Press Panel
        pnlKeyToPress = new JPanel();
        pnlKeyToPress.setOpaque(false);


        imgKeyIco = new aImage("KeyboardKeys/enter.png");
        imgKeyIco.setImageSize(SIZE_KeyIconWidth, SIZE_KeyIconHeight);
        lblKeyAction = new JLabel(" Select ");

        lblKeyAction.setFont(FontRegular.deriveFont(Font.PLAIN, SIZE_keysFont));
        lblKeyAction.setForeground(Color.YELLOW);




        pnlHeaderOfCenterFromBottom.add(BorderLayout.WEST, pnlKeyToPress);
        pnlCenterFromBottom.add(BorderLayout.NORTH, pnlHeaderOfCenterFromBottom);

        ///User Space
        pnlUserSpace = new JPanel();
        pnlUserSpace.setOpaque(false);
        pnlUserSpace.setLayout(new BoxLayout(pnlUserSpace, BoxLayout.Y_AXIS));

        pnlCenterFromBottom.add(BorderLayout.CENTER, pnlUserSpace);


        /// Version Label

        lblVersion = new JLabel(Version);
        lblVersion.setOpaque(false);
        lblVersion.setForeground(Color.LIGHT_GRAY);
        lblVersion.setFont(FontRegular.deriveFont(Font.PLAIN, SIZE_VersionFont));


        pnlVersion = new JPanel();
        pnlVersion.setOpaque(false);
        pnlVersion.setLayout(new BorderLayout());
        pnlVersion.add(BorderLayout.WEST, lblVersion);
        pnlBottom.add(BorderLayout.PAGE_END, pnlVersion);




        ///Finalize

        //////////////////////////////////
        // Add All 3 Main Panels To     //
        // Background Panel             //
        //////////////////////////////////

        pnlBackground.add(pnlTop);

        pnlBackground.add(pnlCenter);

        pnlBackground.add(pnlBottom);


        frame.addKeyListener(new FrameKeyListener());
        frame.requestFocus();

    }

    public aDialog getDilgWarning() {
        return dilgWarning;
    }

    public void setDilgWarning(aDialog dilgWarning) {
        this.dilgWarning = dilgWarning;
    }

    public aDialog getErr() {
        return err;
    }

    public void setErr(aDialog err) {
        this.err = err;
    }

    public JPanel getPnlLogo() {
        return pnlLogo;
    }

    public void setPnlLogo(JPanel pnlLogo) {
        this.pnlLogo = pnlLogo;
    }

    public JPanel getPnlTime() {
        return pnlTime;
    }

    public void setPnlTime(JPanel pnlTime) {
        this.pnlTime = pnlTime;
    }

    public JPanel getPnlFrameControlContainer() {
        return pnlFrameControlContainer;
    }

    public void setPnlFrameControlContainer(JPanel pnlFrameControlContainer) {
        this.pnlFrameControlContainer = pnlFrameControlContainer;
    }

    public void auroraMinimize(String arg) {
        if (miniMode == null) {
            miniMode = new AuroraMini(this, arg); //retain state
        }
        miniMode.createUI();

        frame.setLocation(0, 3000);
    }

    public MinimizeListener getMinimizeHandler() {
        return MinimizeHandler;
    }

    public AuroraMini getMiniMode() {
        if (miniMode == null) {
            miniMode = new AuroraMini(this, "null"); //retain state
        }
        return miniMode;
    }

    public int getSIZE_ScreenHeight() {
        return SIZE_ScreenHeight;
    }

    public int getSIZE_ScreenWidth() {
        return SIZE_ScreenWidth;
    }

    public Font getFontBold() {
        return FontBold;
    }

    public Font getFontRegular() {
        return FontRegular;
    }

    public Font getDefaultFont() {
        return FontRegular;
    }

    private void setSizes() {
        double Ratio = (frame.getWidth() - frame.getHeight()) / 2;
        if (LargeScreen) {
            SIZE_pnlTop = frame.getHeight() / 4;
            SIZE_pnlCenter = frame.getHeight() / 2 + frame.getHeight() / 40;
            SIZE_pnlBottom = frame.getHeight() / 4 + frame.getHeight() / 40;
            SIZE_controlHeight = 55;
            SIZE_controlWidth = 160;
            SIZE_keysFont = frame.getHeight() / 40;
            SIZE_welcomeFont = 22;
            SIZE_KeyIconWidth = 0;
            SIZE_KeyIconHeight = 0;
            SIZE_VersionFont = 15;
            SIZE_TimeFont = SIZE_pnlBottom / 12;
            SIZE_logoHeight = SIZE_pnlTop / 3 + (int) (Ratio / 14);
            SIZE_logoWidth = frame.getWidth() / 2 + (int) (Ratio / 5);
            SIZE_btnExitWidth = 0;
            SIZE_btnExitHeight = 0;
            SIZE_btnMinWidth = 0;
            SIZE_btnMinHeight = 0;
        } else {
            SIZE_pnlTop = frame.getHeight() / 4;
            SIZE_pnlCenter = frame.getHeight() / 2 + frame.getHeight() / 40;
            SIZE_pnlBottom = frame.getHeight() / 4 + frame.getHeight() / 40;
            SIZE_keysFont = frame.getHeight() / 40;
            SIZE_welcomeFont = 21;
            SIZE_KeyIconWidth = SIZE_pnlBottom / 4;
            SIZE_KeyIconHeight = SIZE_pnlBottom / 8;
            SIZE_VersionFont = 12;
            SIZE_TimeFont = SIZE_pnlBottom / 12;
            SIZE_logoHeight = SIZE_pnlTop / 3 + (int) (Ratio / 20);
            SIZE_logoWidth = frame.getWidth() / 2;
            SIZE_controlHeight = 45;
            SIZE_controlWidth = 150;
            SIZE_btnExitWidth = 35;
            SIZE_btnExitHeight = 30;
            SIZE_btnMinWidth = 35;
            SIZE_btnMinHeight = 30;
        }
    }

////////////
//Setters //
////////////
    public int getSIZE_KeyIconHeight() {
        return SIZE_KeyIconHeight;
    }

    public void setSIZE_KeyIconHeight(int SIZE_KeyIconHeight) {
        this.SIZE_KeyIconHeight = SIZE_KeyIconHeight;
    }

    public boolean getLargeScreen() {
        return LargeScreen;
    }

    public int getSIZE_KeyIconWidth() {
        return SIZE_KeyIconWidth;
    }

    public void setSIZE_KeyIconWidth(int SIZE_KeyIconWidth) {
        this.SIZE_KeyIconWidth = SIZE_KeyIconWidth;
    }

    public int getSIZE_TimeFont() {
        return SIZE_TimeFont;
    }

    public void setSIZE_TimeFont(int SIZE_TimeFont) {
        this.SIZE_TimeFont = SIZE_TimeFont;
    }

    public int getSIZE_VersionFont() {
        return SIZE_VersionFont;
    }

    public void setSIZE_VersionFont(int SIZE_VersionFont) {
        this.SIZE_VersionFont = SIZE_VersionFont;
    }

    public int getSIZE_btnExitHeight() {
        return SIZE_btnExitHeight;
    }

    public void setSIZE_btnExitHeight(int SIZE_btnExitHeight) {
        this.SIZE_btnExitHeight = SIZE_btnExitHeight;
    }

    public int getSIZE_btnExitWidth() {
        return SIZE_btnExitWidth;
    }

    public void setSIZE_btnExitWidth(int SIZE_btnExitWidth) {
        this.SIZE_btnExitWidth = SIZE_btnExitWidth;
    }

    public int getSIZE_btnMinHeight() {
        return SIZE_btnMinHeight;
    }

    public void setSIZE_btnMinHeight(int SIZE_btnMinHeight) {
        this.SIZE_btnMinHeight = SIZE_btnMinHeight;
    }

    public int getSIZE_btnMinWidth() {
        return SIZE_btnMinWidth;
    }

    public void setSIZE_btnMinWidth(int SIZE_btnMinWidth) {
        this.SIZE_btnMinWidth = SIZE_btnMinWidth;
    }

    public int getSIZE_controlHeight() {
        return SIZE_controlHeight;
    }

    public void setSIZE_controlHeight(int SIZE_controlHeight) {
        this.SIZE_controlHeight = SIZE_controlHeight;
    }

    public int getSIZE_controlWidth() {
        return SIZE_controlWidth;
    }

    public void setSIZE_controlWidth(int SIZE_controlWidth) {
        this.SIZE_controlWidth = SIZE_controlWidth;
    }

    public int getSIZE_keysFont() {
        return SIZE_keysFont;
    }

    public void setSIZE_keysFont(int SIZE_keysFont) {
        this.SIZE_keysFont = SIZE_keysFont;
    }

    public int getSIZE_logoHeight() {
        return SIZE_logoHeight;
    }

    public void setSIZE_logoHeight(int SIZE_logoHeight) {
        this.SIZE_logoHeight = SIZE_logoHeight;
    }

    public int getSIZE_logoWidth() {
        return SIZE_logoWidth;
    }

    public void setSIZE_logoWidth(int SIZE_logoWidth) {
        this.SIZE_logoWidth = SIZE_logoWidth;
    }

    public int getSIZE_pnlBottom() {
        return SIZE_pnlBottom;
    }

    public void setSIZE_pnlBottom(int SIZE_pnlBottom) {
        this.SIZE_pnlBottom = SIZE_pnlBottom;
    }

    public int getSIZE_pnlCenter() {
        return SIZE_pnlCenter;
    }

    public void setSIZE_pnlCenter(int SIZE_pnlCenter) {
        this.SIZE_pnlCenter = SIZE_pnlCenter;
    }

    public int getSIZE_pnlTop() {
        return SIZE_pnlTop;
    }

    public void setSIZE_pnlTop(int SIZE_pnlTop) {
        this.SIZE_pnlTop = SIZE_pnlTop;
    }

    public int getSIZE_welcomeFont() {
        return SIZE_welcomeFont;
    }

    public void setSIZE_welcomeFont(int SIZE_welcomeFont) {
        this.SIZE_welcomeFont = SIZE_welcomeFont;
    }

    public void setPnlBackground(aImagePane pnlBackground) {
        this.pnlBackground = pnlBackground;
    }

    public void setPnlBottom(aImagePane pnlBottom) {
        this.pnlBottom = pnlBottom;
    }

    public void setPnlCenter(JPanel pnlCenter) {
        this.pnlCenter = pnlCenter;
    }

    public void setPnlCenterFromBottom(JPanel pnlCenterFromBottom) {
        this.pnlCenterFromBottom = pnlCenterFromBottom;
    }

    public void setPnlSouthFromTop(JPanel pnlSouthFromTop) {
        this.pnlSouthFromTop = pnlSouthFromTop;
    }

    public void setPnlFrameControl(aImagePane pnlFrameControl) {
        this.pnlFrameControl = pnlFrameControl;
    }

    public void setPnlTop(aImagePane pnlTop) {
        this.pnlTop = pnlTop;
    }

    public void setImgLogo(aImage imgLogo) {
        this.imgLogo = imgLogo;
    }

    public void setLblInfo(JLabel lblInfo) {
        this.lblInfo = lblInfo;
    }

    public void setVi(aXAVI vi) {
        this.vi = vi;
    }

    public void setPnlUserbar(JPanel pnlUserbar) {
        this.pnlUserSpace = pnlUserbar;
    }

    public String getRevision() {
        return Revision;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public void setImgKeyIco(aImage imgKeyIco) {
        this.imgKeyIco = imgKeyIco;
    }

    public void setLblKeyAction(JLabel lblKeyAction) {
        this.lblKeyAction = lblKeyAction;
    }

    public static void setLblTime(aTimeLabel lblTime) {
        AuroraCoreUI.lblTime = lblTime;
    }

    public void setLblVersion(JLabel lblVersion) {
        this.lblVersion = lblVersion;
    }

    public void setHeaderOfCenterFromBottom(JPanel pnlHeaderOfCenterFromBottom) {
        this.pnlHeaderOfCenterFromBottom = pnlHeaderOfCenterFromBottom;
    }

    public void setPnlKeyToPress(JPanel pnlKeyToPress) {
        this.pnlKeyToPress = pnlKeyToPress;
    }

    public void setPnlScreenLable(JPanel pnlScreenLable) {
        this.pnlScreenLable = pnlScreenLable;
    }

    public void setPnlVersion(JPanel pnlVersion) {
        this.pnlVersion = pnlVersion;
    }

    ///
    //Getters
    ///
    public aImage getImgKeyIco() {
        return imgKeyIco;
    }

    public JLabel getLblKeyAction() {
        return lblKeyAction;
    }

    public JLabel getLblVersion() {
        return lblVersion;
    }

    public JPanel getPnlHeaderOfCenterFromBottom() {
        return pnlHeaderOfCenterFromBottom;
    }

    public JPanel getPnlKeyToPress() {
        return pnlKeyToPress;
    }

    public String getVersion() {
        return Version;
    }

    public JPanel getPnlScreenLable() {
        return pnlScreenLable;
    }

    public JPanel getPnlVersion() {
        return pnlVersion;
    }

    public boolean isLargeScreen() {
        return LargeScreen;
    }

    public JButton getBtnExit() {
        return btnExit;
    }

    public JButton getBtnMin() {
        return btnMin;
    }

    public JLabel getLblInfo() {
        return lblInfo;
    }

    public aXAVI getVi() {
        return vi;
    }

    public JFrame getFrame() {
        return frame;
    }

    public static aTimeLabel getLblTime() {
        return lblTime;
    }

    public JPanel getPnlUserSpace() {
        return pnlUserSpace;
    }

    public aImage getImgLogo() {
        return imgLogo;
    }

    public aImagePane getPnlBackground() {
        return pnlBackground;
    }

    public JPanel getPnlInfo() {

        return pnlInfo;
    }

    public aImagePane getPnlBottom() {
        return pnlBottom;
    }

    public JPanel getPnlCenter() {
        return pnlCenter;
    }

    public JPanel getPnlCenterFromBottom() {
        return pnlCenterFromBottom;
    }

    public JPanel getPnlSouthFromTop() {
        return pnlSouthFromTop;
    }

    public aImagePane getPnlFrameControl() {
        return pnlFrameControl;
    }

    public aImagePane getPnlTop() {
        return pnlTop;
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

    public void showExitDilog() {
        if (dilgWarning == null) {
            dilgWarning = new aDialog(aDialog.aDIALOG_WARNING, "Are You Sure you want to " + vi.VI(vi.inx_Exit) + "?", FontBold);


            dilgWarning.setButtonListener(new ActionListener() {
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
            dilgWarning.showDialog();


        }
        dilgWarning.setVisible(true);
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
    private static String getRbTok(String propToken) {
        String msg = "";
        try {
            msg = rb.getString(propToken);
        } catch (MissingResourceException e) {
            System.err.println("Token ".concat(propToken).concat(" not in Property file!"));
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

            showExitDilog();

        }
    }

    class MinimizeListener implements ActionListener {

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

            auroraMinimize(arg);

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
                showExitDilog();
            }

        }
    }
}
