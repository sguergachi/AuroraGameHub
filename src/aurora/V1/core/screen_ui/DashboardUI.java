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
package aurora.V1.core.screen_ui;

import aurora.V1.core.AuroraApp;
import aurora.V1.core.AuroraCoreUI;
import aurora.V1.core.AuroraStorage;
import aurora.V1.core.Game;
import aurora.V1.core.StartLoader;
import aurora.V1.core.screen_handler.DashboardHandler;
import aurora.engine.V1.Logic.aXAVI;
import aurora.engine.V1.UI.aButton;
import aurora.engine.V1.UI.aCarousel;
import aurora.engine.V1.UI.aCarouselPane;
import aurora.engine.V1.UI.aCarouselTitle;
import aurora.engine.V1.UI.aCarouselTitle.Title;
import aurora.engine.V1.UI.aImage;
import aurora.engine.V1.UI.aImagePane;
import aurora.engine.V1.UI.aInfoFeed;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Sammy
 * @version 0.3
 */
public class DashboardUI extends AuroraApp {

    private aCarousel Carousel;
    private aButton btnLogout;
    private aButton btnCarouselLeft;
    private aButton btnCarouselRight;
    private aImage titleGamerGlow;
    private aImage titleimgGamerNorm;
    private aImage titleSettingGlow;
    private aImage titleSettingNorm;
    private aImage titleLibraryGlow;
    private aImage titleLibraryNorm;
    private aImage titleAuroraNetGlow;
    private aImage imgProfile;
    private aImage imgSetting;
    private aImagePane imgGame;
    private aImage imgNet;
    private AuroraCoreUI coreUI;
    private aImage imgKeyIco;
    private JLabel lblKeyAction;
    private aInfoFeed info;
    private ArrayList<String> infoArray;
    private int SIZE_btnLogoutWidth;
    private int SIZE_btnLogoutHeight;
    private double SIZE_CarouselWidth;
    private int SIZE_CarouselHeight;
    private Game Game;
    private int SIZE_GameCoverHeight;
    private int SIZE_GameCoverWidth;
    private int SIZE_CarouselImageWidth;
    private int SIZE_CarouselImageHeight;
    private int SIZE_ImageHeight;
    private int SIZE_InfobarWidth;
    private int SIZE_InfobarHeight;
    private int SIZE_FramePadding;
    private int SIZE_BottomPaneHeightAdjust;
    private int SIZE_TopPaneHeighAdjust;
    private final StartScreenUI startUI;
    private aCarouselTitle titleGamer;
    private aCarouselTitle titleSetting;
    private aCarouselTitle titleLibrary;
    private aCarouselTitle titleAuroraNet;
    private aCarouselPane libraryPane;
    aCarouselPane settingsPane;
    aCarouselPane profilePane;
    aCarouselPane auroraNetPane;
    private AuroraStorage storage;
    private StartLoader loader;
    private aImage titleAuroraNetNorm;
    private int SIZE_TopHeight;
    private int SIZE_ImageWidth;
    private int SIZE_CarouselButtonWidth;
    private int SIZE_CarouselButtonHeight;
    private JPanel pnlInfo;
    private  DashboardHandler handler;

/**
* .------------------------------------------------------------------------.
* |    DashboardUI()
* .------------------------------------------------------------------------.
* |
* | This is the main constructor for the DasboardUI
* | It requires the startLoader that transitions from the StartScreenUI
* | to the DashboardUI. It also takes the CoreUI
* |
* | 
* | 
* | 
* | 
* |
* |
* |
* .........................................................................
 * @param startLoader
 * @param auroraCoreUi
 * @param startScreenUi 
 */
    public DashboardUI(StartLoader startLoader, AuroraCoreUI auroraCoreUi, StartScreenUI startScreenUi) {
        this.loader = startLoader;
        this.startUI = startScreenUi;

        this.handler = new DashboardHandler(this);
        this.coreUI = auroraCoreUi;
        this.storage = startUI.getAuroraStorage();


    }

    public StartScreenUI getStartUp_Obj() {
        return startUI;
    }


    @Override
    public  void loadUI() {

        //Initialize Sizes
        setSizes();

        coreUI.getImgLogo().setImgURl("Aurora_Header2.png");
        coreUI.getImgLogo().setImageSize(SIZE_ImageWidth, SIZE_ImageHeight);

        coreUI.getPnlBottom().setPreferredSize(new Dimension(coreUI.getPnlBottom().getWidth(), SIZE_BottomPaneHeightAdjust));
        coreUI.getPnlBottom().setImageHeight(SIZE_BottomPaneHeightAdjust);

        coreUI.getPnlTop().setImageHeight(SIZE_TopHeight);
        coreUI.getPnlTop().setPreferredSize(new Dimension(coreUI.getPnlTop().getWidth(), coreUI.getPnlTop().getImageHeight() + coreUI.getPnlFrameControl().getHeight()));


        btnLogout = new aButton("Aurora_Logout_normal.png", "Aurora_Logout_down.png", "Aurora_Logout_over.png", SIZE_btnLogoutWidth, SIZE_btnLogoutHeight);
        btnLogout.setToolTipText("Back");

        coreUI.getPnlFrameControl().removeAll();

        coreUI.getPnlFrameControl().add(btnLogout);
        coreUI.getPnlFrameControl().add(coreUI.getBtnMin());
        coreUI.getPnlFrameControl().add(coreUI.getBtnExit());
        coreUI.getPnlFrameControl().setImage("Aurora_FrameButton2.png");

        coreUI.getPnlSouthFromTop().setPreferredSize(new Dimension(coreUI.getPnlSouthFromTop().getWidth(), coreUI.getPnlFrameControl().getHeight()));
        coreUI.getPnlTop().setPreferredSize(new Dimension(coreUI.getPnlTop().getWidth(), coreUI.getPnlTop().getImageHeight() + coreUI.getPnlFrameControl().getHeight()));
        coreUI.getPnlSouthFromTop().revalidate();


        ///.......Titles To The carousel Panels
        titleSettingGlow = new aImage("settings_glow.png");
        titleSettingNorm = new aImage("settings_normal.png");

        titleLibraryGlow = new aImage("gamelibrary_glow.png");
        titleLibraryNorm = new aImage("gamelibrary_normal.png");

        titleGamerGlow = new aImage("gamerprofile_glow.png");
        titleimgGamerNorm = new aImage("gamerprofile_normal.png");

        titleAuroraNetGlow = new aImage("auroranet_glow.png");
        titleAuroraNetNorm = new aImage("auroranet_normal.png");

        imgProfile = new aImage("Aurora_Profile.png");
        imgSetting = new aImage("Aurora_Settings.png");
        imgNet = new aImage("ComingSoon.png");

        titleGamer = new aCarouselTitle(titleimgGamerNorm, titleGamerGlow);

        titleSetting = new aCarouselTitle(titleSettingNorm, titleSettingGlow);
        titleLibrary = new aCarouselTitle(titleLibraryNorm, titleLibraryGlow);
        titleAuroraNet = new aCarouselTitle(titleAuroraNetNorm, titleAuroraNetGlow);


        coreUI.getFrame().removeKeyListener(startUI.getStartKeyHandler());
        coreUI.getFrame().add(coreUI.getPnlBackground());
    }

    public void buildUI()  {



        coreUI.getLblInfo().setText(".: Loading :.");

        ////........ Create new Components

        //Key Actions Panel (lower left)        

        imgKeyIco = new aImage("KeyboardKeys/arrows.png", coreUI.getSIZE_KeyIconWidth(), coreUI.getSIZE_KeyIconHeight());
        lblKeyAction = new JLabel(" Move ");
        lblKeyAction.setFont(coreUI.getDefaultFont().deriveFont(Font.PLAIN, coreUI.getSIZE_keysFont()));
        lblKeyAction.setForeground(Color.YELLOW);


        //Press Enter Icons
        coreUI.getPnlKeyToPress().add(coreUI.getImgKeyIco());
        coreUI.getPnlKeyToPress().add(coreUI.getLblKeyAction());

        //Use Arrow Keys Icons
        coreUI.getPnlKeyToPress().add(imgKeyIco);
        coreUI.getPnlKeyToPress().add(lblKeyAction);

        //Images inside carousels

        imgSetting.setImageSize(SIZE_CarouselImageWidth, SIZE_CarouselImageHeight);
        imgProfile.setImageSize(SIZE_CarouselImageWidth, SIZE_CarouselImageHeight);
        imgNet.setImageSize(SIZE_CarouselImageWidth, SIZE_CarouselImageHeight);

        if (storage.getStoredLibrary().getBoxArtPath() == null || storage.getStoredLibrary().getBoxArtPath().isEmpty()) {

            aImagePane blank = new aImagePane("Blank-Case.png", SIZE_GameCoverWidth, SIZE_GameCoverHeight);
            imgGame = blank; //Name change for carousel

        } else {
            Random rand = new Random();

            int randomNum = rand.nextInt(storage.getStoredLibrary().getGameNames().size());

            System.out.println("Random Num " + randomNum);
            System.out.println("Storage size " + storage.getStoredLibrary().getBoxArtPath());

            Game = new Game(storage.getStoredLibrary().getBoxArtPath().get(randomNum), this);
            Game.setCoverSize(SIZE_GameCoverWidth, SIZE_GameCoverHeight);

            imgGame = Game; //Name change for carousel
        }


        imgGame.setPreferredSize(new Dimension(SIZE_GameCoverWidth, SIZE_GameCoverHeight));


//////////////CAROUSEL/////

        String URL = "HexPane.png";
        Carousel = new aCarousel(URL, SIZE_CarouselWidth, SIZE_CarouselHeight, Toolkit.getDefaultToolkit().getScreenSize().width);

        settingsPane = new aCarouselPane(URL, (int) SIZE_CarouselWidth + 25, SIZE_CarouselHeight - 25, true, titleSetting, "Setting Pane");
        settingsPane.setName("settingsPane");
        settingsPane.addKeyListener(handler.new CarouselKeyListener());


        profilePane = new aCarouselPane(URL, (int) SIZE_CarouselWidth + 25, SIZE_CarouselHeight - 25, true, titleGamer, "gamer pane");
        profilePane.setName("profilePane");
        profilePane.addKeyListener(handler.new CarouselKeyListener());

        libraryPane = new aCarouselPane(URL, (int) SIZE_CarouselWidth + 25, SIZE_CarouselHeight - 25, true, titleLibrary, "library pane");
        libraryPane.setName("libraryPane");
        libraryPane.addKeyListener(handler.new CarouselKeyListener());

        auroraNetPane = new aCarouselPane(URL, (int) SIZE_CarouselWidth + 25, SIZE_CarouselHeight - 25, true, titleAuroraNet, "auroranet");
        auroraNetPane.setName("auroraNetPane");

        settingsPane.addContent(imgSetting, Title.NORMAL);
        profilePane.addContent(imgProfile, Title.NORMAL);
        libraryPane.addContent(imgGame, Title.GLOW);
        auroraNetPane.addContent(imgNet, Title.GLOW);

        Carousel.addPane(settingsPane);
        Carousel.addPane(libraryPane);
        Carousel.addPane(profilePane);
        Carousel.addPane(auroraNetPane);
        Carousel.addKeyListener(handler.new CarouselKeyListener());


        ///.....Check for the Enter Button Press OR Mouse Click

        profilePane.addMouseListener(handler.new CarouselPaneMouseListener());
        settingsPane.addMouseListener(handler.new CarouselPaneMouseListener());
        libraryPane.addMouseListener(handler.new CarouselPaneMouseListener());

        auroraNetPane.addMouseListener(handler.new CarouselPaneMouseListener());
        Carousel.addMouseWheelListener(handler.new carouselPaneMouseWheelListener());



///////////////////////////



        //Carousel Buttons

        btnCarouselLeft = new aButton("Aurora_left_normal.png", "Aurora_left_down.png", "Aurora_left_over.png", SIZE_CarouselButtonWidth, SIZE_CarouselButtonHeight);
        btnCarouselLeft.addActionListener(handler.new LeftListener());
        btnCarouselLeft.addKeyListener(handler.new CarouselKeyListener());

        btnCarouselRight = new aButton("Aurora_right_normal.png", "Aurora_right_down.png", "Aurora_right_over.png", SIZE_CarouselButtonWidth, SIZE_CarouselButtonHeight);
        btnCarouselRight.addActionListener(handler.new RightListener());
        btnCarouselRight.addKeyListener(handler.new CarouselKeyListener());



        //Info Bar

        infoArray = new ArrayList();
        infoArray.add(coreUI.getVi().VI(aXAVI.inx_Welcome) + ", ");
        infoArray.add("How are you doing Today " + coreUI.getVi().VI(aXAVI.inx_User) + " We hope you enjoy this Alpha release of the Aurora Game Manager");
        infoArray.add("Make Sure You Check out the Improved Game Library!");
        infoArray.add("It can totally do stuff now!");
        infoArray.add("It only took a year or so...");
        infoArray.add("Checkout our website at auroragm.sourceforge.net ");
        infoArray.add("Please feel free to contact me personally via e-mail");
        infoArray.add("> sguergachi@gmail.com < ");
        infoArray.add("Let me know if you find any bugs ");
        infoArray.add("I will personally attend to it that it is exterminated");
        infoArray.add("You can also contact me regarding feedback ");
        infoArray.add("I will feed on your feedback, if you know what i'm saying ");
        infoArray.add("Just so you know, I didn't put this bar here to annoy you ");
        infoArray.add("I plan on having it show you a live feed of breaking gaming news ");
        infoArray.add("As well as tracking information from your profile ");
        infoArray.add("When ever the heck that gets done... ");
        infoArray.add("Its gonna be awesome and super useful, I promise ");
        infoArray.add("Just to demonstrate how useful it's going to be, why don't I teach you the alphabet? ");
        infoArray.add("A B C D E F G H I J K L M N O P Q R S T U V W X Y Z ");
        infoArray.add("There you go, I just thought you the ABCs! ");
        infoArray.add("Anyways, you don't have to hang around here, just click on the library to start! ");
        infoArray.add("Just press Enter, or, Move your mouse and click on the big thing that says 'Library' ");
        infoArray.add("I can do this all day ");
        infoArray.add("And all night ");
        infoArray.add("Ohh, Breaking News! ");
        infoArray.add("There is a new Call of Duty game coming out Next Year!!");
        infoArray.add("Totally did not see that comming...");
        infoArray.add("I wonder what's going to be in it");
        infoArray.add("I'm going to guess it has something to do with shooting guys with guns");
        infoArray.add("Well, i'm tired, keep checking the Sourceforge page for new updates");
        infoArray.add("Have fun!");

        info = new aInfoFeed("InfoBar.png", SIZE_InfobarWidth, SIZE_InfobarHeight, infoArray);
//        info.setPreferredSize(new Dimension(SIZE_InfobarWidth, SIZE_InfobarHeight));
        info.go();

        pnlInfo = new JPanel(new BorderLayout());
        pnlInfo.add(info, BorderLayout.SOUTH);
        pnlInfo.setOpaque(false);

        coreUI.getPnlCenter().add(BorderLayout.CENTER, Carousel);

        coreUI.getPnlCenterFromBottom().add(BorderLayout.EAST, btnCarouselRight);
        coreUI.getPnlCenterFromBottom().add(info, BorderLayout.CENTER);
        coreUI.getPnlCenterFromBottom().add(BorderLayout.WEST, btnCarouselLeft);

        //Load GameCover Cover
        if (Game != null) {
            try {
                Game.update();
            } catch (MalformedURLException ex) {
                Logger.getLogger(DashboardUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            Game.removeInteraction();
            Game.getInteractivePane().addMouseListener(handler.new CarouselLibraryMouseListener());
        }



        //Finished loading so change text
        coreUI.getLblInfo().setText(" Dashboard ");

        //Finalize
        coreUI.getFrame().getContentPane().addKeyListener(handler.new CarouselKeyListener());
        coreUI.getFrame().addKeyListener(handler.new CarouselKeyListener());
        coreUI.getPnlBackground().addKeyListener(handler.new CarouselKeyListener());

        coreUI.getFrame().repaint();
        coreUI.getFrame().requestFocus();

    }

    private void setSizes() {

        int Ratio = (coreUI.getFrame().getHeight() / coreUI.getFrame().getWidth());

        if (coreUI.isLargeScreen()) {
            SIZE_TopHeight = coreUI.getPnlCenter().getHeight() / 8;
            SIZE_btnLogoutWidth = 0;
            SIZE_btnLogoutHeight = 0;
            SIZE_CarouselWidth = (int) (coreUI.getFrame().getWidth() / 42) * 16;
            SIZE_CarouselHeight = coreUI.getFrame().getHeight() - (coreUI.getFrame().getWidth() / 6);
            SIZE_GameCoverHeight = SIZE_CarouselHeight - (2 * SIZE_CarouselHeight / 6);
            SIZE_GameCoverWidth = (int) SIZE_CarouselWidth - (int) (SIZE_CarouselWidth / 4);
            SIZE_CarouselImageWidth = SIZE_CarouselHeight - (2 * SIZE_CarouselHeight / 6) - (Ratio / 8);
            SIZE_CarouselImageHeight = (int) SIZE_CarouselWidth - (int) (SIZE_CarouselWidth / 4) - 20;
            SIZE_ImageHeight = SIZE_TopHeight / 2 + 20;
            SIZE_ImageWidth = coreUI.getFrame().getWidth() / 2 + 20;

            SIZE_BottomPaneHeightAdjust = coreUI.getSIZE_pnlBottom() / 2 + coreUI.getFrame().getHeight() / 50 + 25;
            SIZE_TopPaneHeighAdjust = coreUI.getPnlCenter().getHeight() / 5 - Ratio / 10;
            SIZE_CarouselButtonWidth = coreUI.getFrame().getWidth() / 12 + 10;
            SIZE_CarouselButtonHeight = coreUI.getFrame().getHeight() / 15 + 10;
            SIZE_InfobarWidth = coreUI.getFrame().getSize().width - (SIZE_CarouselButtonWidth * 2 + 65);
            SIZE_InfobarHeight = 75;


        } else {
            SIZE_TopHeight = coreUI.getPnlCenter().getHeight() / 8;
            SIZE_btnLogoutWidth = 30;
            SIZE_btnLogoutHeight = 35;
            SIZE_CarouselWidth = (int) (coreUI.getFrame().getWidth() / 40) * 16;
            SIZE_CarouselHeight = coreUI.getFrame().getHeight() - (coreUI.getFrame().getWidth() / 6);
            SIZE_GameCoverHeight = SIZE_CarouselHeight - (2 * SIZE_CarouselHeight / 6);
            SIZE_GameCoverWidth = (int) SIZE_CarouselWidth - (int) (SIZE_CarouselWidth / 4);
            SIZE_CarouselImageWidth = (int) SIZE_CarouselWidth - (int) (400 / 2) - (Ratio * 2);
            SIZE_CarouselImageHeight = (int) SIZE_CarouselHeight - (450 / 2) - (Ratio * 2) - 55;
            SIZE_ImageHeight = SIZE_TopHeight / 2 + 20;
            SIZE_ImageWidth = coreUI.getFrame().getWidth() / 2 + 20;

            SIZE_BottomPaneHeightAdjust = coreUI.getSIZE_pnlBottom() / 2 + coreUI.getFrame().getHeight() / 90 + 25;
            SIZE_TopPaneHeighAdjust = coreUI.getPnlCenter().getHeight() / 5 - Ratio / 10;
            SIZE_CarouselButtonWidth = coreUI.getFrame().getWidth() / 12;
            SIZE_CarouselButtonHeight = coreUI.getFrame().getHeight() / 15;
            SIZE_InfobarWidth = coreUI.getFrame().getSize().width - (SIZE_CarouselButtonWidth * 2 + 60);
            SIZE_InfobarHeight = SIZE_CarouselButtonHeight - SIZE_BottomPaneHeightAdjust / 18;


            System.out.println("WIDTH " + SIZE_CarouselWidth);
        }

    }

    public aImage getTitleGamerGlow() {
        return titleGamerGlow;
    }

    public void setTitleGamerGlow(aImage titleGamerGlow) {
        this.titleGamerGlow = titleGamerGlow;
    }

    public aImage getTitleimgGamerNorm() {
        return titleimgGamerNorm;
    }

    public void setTitleimgGamerNorm(aImage titleimgGamerNorm) {
        this.titleimgGamerNorm = titleimgGamerNorm;
    }

    public aImage getTitleSettingGlow() {
        return titleSettingGlow;
    }

    public void setTitleSettingGlow(aImage titleSettingGlow) {
        this.titleSettingGlow = titleSettingGlow;
    }

    public aImage getTitleSettingNorm() {
        return titleSettingNorm;
    }

    public void setTitleSettingNorm(aImage titleSettingNorm) {
        this.titleSettingNorm = titleSettingNorm;
    }

    public aImage getTitleLibraryGlow() {
        return titleLibraryGlow;
    }

    public void setTitleLibraryGlow(aImage titleLibraryGlow) {
        this.titleLibraryGlow = titleLibraryGlow;
    }

    public aImage getTitleLibraryNorm() {
        return titleLibraryNorm;
    }

    public void setTitleLibraryNorm(aImage titleLibraryNorm) {
        this.titleLibraryNorm = titleLibraryNorm;
    }

    public aImage getTitleAuroraNetGlow() {
        return titleAuroraNetGlow;
    }

    public void setTitleAuroraNetGlow(aImage titleAuroraNetGlow) {
        this.titleAuroraNetGlow = titleAuroraNetGlow;
    }

    public aImage getImgProfile() {
        return imgProfile;
    }

    public void setImgProfile(aImage imgProfile) {
        this.imgProfile = imgProfile;
    }

    public aImage getImgSetting() {
        return imgSetting;
    }

    public void setImgSetting(aImage imgSetting) {
        this.imgSetting = imgSetting;
    }

    public aImagePane getImgGame() {
        return imgGame;
    }

    public void setImgGame(aImagePane imgGame) {
        this.imgGame = imgGame;
    }

    public aImage getImgNet() {
        return imgNet;
    }

    public void setImgNet(aImage imgNet) {
        this.imgNet = imgNet;
    }

    public aImage getImgKeyIco() {
        return imgKeyIco;
    }

    public void setImgKeyIco(aImage imgKeyIco) {
        this.imgKeyIco = imgKeyIco;
    }

    public JLabel getLblKeyAction() {
        return lblKeyAction;
    }

    public void setLblKeyAction(JLabel lblKeyAction) {
        this.lblKeyAction = lblKeyAction;
    }

    public aInfoFeed getInfo() {
        return info;
    }

    public void setInfo(aInfoFeed info) {
        this.info = info;
    }

    public ArrayList<String> getInfoArray() {
        return infoArray;
    }

    public void setInfoArray(ArrayList<String> infoArray) {
        this.infoArray = infoArray;
    }


    public int getSIZE_btnLogoutWidth() {
        return SIZE_btnLogoutWidth;
    }

    public void setSIZE_btnLogoutWidth(int SIZE_btnLogoutWidth) {
        this.SIZE_btnLogoutWidth = SIZE_btnLogoutWidth;
    }

    public int getSIZE_btnLogoutHeight() {
        return SIZE_btnLogoutHeight;
    }

    public void setSIZE_btnLogoutHeight(int SIZE_btnLogoutHeight) {
        this.SIZE_btnLogoutHeight = SIZE_btnLogoutHeight;
    }

    public double getSIZE_CarouselWidth() {
        return SIZE_CarouselWidth;
    }

    public void setSIZE_CarouselWidth(double SIZE_CarouselWidth) {
        this.SIZE_CarouselWidth = SIZE_CarouselWidth;
    }

    public int getSIZE_CarouselHeight() {
        return SIZE_CarouselHeight;
    }

    public void setSIZE_CarouselHeight(int SIZE_CarouselHeight) {
        this.SIZE_CarouselHeight = SIZE_CarouselHeight;
    }

    public int getSIZE_GameCoverHeight() {
        return SIZE_GameCoverHeight;
    }

    public void setSIZE_GameCoverHeight(int SIZE_GameCoverHeight) {
        this.SIZE_GameCoverHeight = SIZE_GameCoverHeight;
    }

    public int getSIZE_GameCoverWidth() {
        return SIZE_GameCoverWidth;
    }

    public void setSIZE_GameCoverWidth(int SIZE_GameCoverWidth) {
        this.SIZE_GameCoverWidth = SIZE_GameCoverWidth;
    }

    public int getSIZE_CarouselImageWidth() {
        return SIZE_CarouselImageWidth;
    }

    public void setSIZE_CarouselImageWidth(int SIZE_CarouselImageWidth) {
        this.SIZE_CarouselImageWidth = SIZE_CarouselImageWidth;
    }

    public int getSIZE_CarouselImageHeight() {
        return SIZE_CarouselImageHeight;
    }

    public void setSIZE_CarouselImageHeight(int SIZE_CarouselImageHeight) {
        this.SIZE_CarouselImageHeight = SIZE_CarouselImageHeight;
    }

    public int getSIZE_ImageHeight() {
        return SIZE_ImageHeight;
    }

    public void setSIZE_ImageHeight(int SIZE_ImageHeight) {
        this.SIZE_ImageHeight = SIZE_ImageHeight;
    }

    public int getSIZE_InfobarWidth() {
        return SIZE_InfobarWidth;
    }

    public void setSIZE_InfobarWidth(int SIZE_InfobarWidth) {
        this.SIZE_InfobarWidth = SIZE_InfobarWidth;
    }

    public int getSIZE_InfobarHeight() {
        return SIZE_InfobarHeight;
    }

    public void setSIZE_InfobarHeight(int SIZE_InfobarHeight) {
        this.SIZE_InfobarHeight = SIZE_InfobarHeight;
    }

    public int getSIZE_FramePadding() {
        return SIZE_FramePadding;
    }

    public void setSIZE_FramePadding(int SIZE_FramePadding) {
        this.SIZE_FramePadding = SIZE_FramePadding;
    }

    public int getSIZE_BottomPaneHeightAdjust() {
        return SIZE_BottomPaneHeightAdjust;
    }

    public void setSIZE_BottomPaneHeightAdjust(int SIZE_BottomPaneHeightAdjust) {
        this.SIZE_BottomPaneHeightAdjust = SIZE_BottomPaneHeightAdjust;
    }

    public int getSIZE_TopPaneHeighAdjust() {
        return SIZE_TopPaneHeighAdjust;
    }

    public void setSIZE_TopPaneHeighAdjust(int SIZE_TopPaneHeighAdjust) {
        this.SIZE_TopPaneHeighAdjust = SIZE_TopPaneHeighAdjust;
    }

    public aCarouselTitle getTitleGamer() {
        return titleGamer;
    }

    public void setTitleGamer(aCarouselTitle titleGamer) {
        this.titleGamer = titleGamer;
    }

    public aCarouselTitle getTitleSetting() {
        return titleSetting;
    }

    public void setTitleSetting(aCarouselTitle titleSetting) {
        this.titleSetting = titleSetting;
    }

    public aCarouselTitle getTitleLibrary() {
        return titleLibrary;
    }

    public void setTitleLibrary(aCarouselTitle titleLibrary) {
        this.titleLibrary = titleLibrary;
    }

    public aCarouselTitle getTitleAuroraNet() {
        return titleAuroraNet;
    }

    public void setTitleAuroraNet(aCarouselTitle titleAuroraNet) {
        this.titleAuroraNet = titleAuroraNet;
    }

    public aCarouselPane getLibraryPane() {
        return libraryPane;
    }

    public void setLibraryPane(aCarouselPane libraryPane) {
        this.libraryPane = libraryPane;
    }

    public aCarouselPane getSettingsPane() {
        return settingsPane;
    }

    public void setSettingsPane(aCarouselPane settingsPane) {
        this.settingsPane = settingsPane;
    }

    public aCarouselPane getProfilePane() {
        return profilePane;
    }

    public void setProfilePane(aCarouselPane profilePane) {
        this.profilePane = profilePane;
    }

    public aCarouselPane getAuroraNetPane() {
        return auroraNetPane;
    }

    public void setAuroraNetPane(aCarouselPane auroraNetPane) {
        this.auroraNetPane = auroraNetPane;
    }

    public StartLoader getLoader() {
        return loader;
    }

    public void setLoader(StartLoader loader) {
        this.loader = loader;
    }

    public aImage getTitleAuroraNetNorm() {
        return titleAuroraNetNorm;
    }

    public void setTitleAuroraNetNorm(aImage titleAuroraNetNorm) {
        this.titleAuroraNetNorm = titleAuroraNetNorm;
    }

    public int getSIZE_TopHeight() {
        return SIZE_TopHeight;
    }

    public void setSIZE_TopHeight(int SIZE_TopHeight) {
        this.SIZE_TopHeight = SIZE_TopHeight;
    }

    public int getSIZE_ImageWidth() {
        return SIZE_ImageWidth;
    }

    public void setSIZE_ImageWidth(int SIZE_ImageWidth) {
        this.SIZE_ImageWidth = SIZE_ImageWidth;
    }

    public int getSIZE_CarouselButtonWidth() {
        return SIZE_CarouselButtonWidth;
    }

    public void setSIZE_CarouselButtonWidth(int SIZE_CarouselButtonWidth) {
        this.SIZE_CarouselButtonWidth = SIZE_CarouselButtonWidth;
    }

    public int getSIZE_CarouselButtonHeight() {
        return SIZE_CarouselButtonHeight;
    }

    public void setSIZE_CarouselButtonHeight(int SIZE_CarouselButtonHeight) {
        this.SIZE_CarouselButtonHeight = SIZE_CarouselButtonHeight;
    }

    public JPanel getPnlInfo() {
        return pnlInfo;
    }

    public void setPnlInfo(JPanel pnlInfo) {
        this.pnlInfo = pnlInfo;
    }

    public aCarousel getCarousel() {
        return Carousel;
    }

    public aurora.V1.core.Game getGame() {
        return Game;
    }

    public aButton getBtnLogout() {
        return btnLogout;
    }

    public AuroraStorage getStorage() {
        return storage;
    }

    public AuroraCoreUI getUi() {
        return coreUI;
    }


    public aButton getBtnCarouselLeft() {
        return btnCarouselLeft;
    }

    public aButton getBtnCarouselRight() {
        return btnCarouselRight;
    }

    public AuroraCoreUI getCoreUI() {
        return this.coreUI;
    }



}
