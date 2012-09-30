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

import aurora.engine.V1.Logic.aResourceManager;
import aurora.engine.V1.Logic.aXAVI;
import aurora.engine.V1.UI.*;
import aurora.engine.V1.UI.aCarouselTitle.Title;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Sammy
 * @version 0.3
 */
public class Aurora_Dashboard extends AuroraApp{

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
    private AuroraCoreUI ui;
    private aImage imgKeyIco;
    private JLabel lblKeyAction;
    private aInfoFeed info;
    private ArrayList<String> infoArray;
    private Aurora_Dashboard dash_Obj;
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
    private final Aurora_StartUp StartUp_Obj;
    //carlos
    private aCarouselTitle titleGamer;
    private aCarouselTitle titleSetting;
    private aCarouselTitle titleLibrary;
    private aCarouselTitle titleAuroraNet;
    private aCarouselPane libraryPane;
    aCarouselPane settingsPane;
    aCarouselPane profilePane;
    aCarouselPane auroraNetPane;
    private final aResourceManager ressource;
    private AuroraStorage storage;
    private StartLoader loader;
    private aImage titleAuroraNetNorm;
    private int SIZE_TopHeight;
    private int SIZE_ImageWidth;
    private int SIZE_CarouselButtonWidth;
    private int SIZE_CarouselButtonHeight;
    private JPanel pnlInfo;

    public Aurora_Dashboard(StartLoader loader, AuroraCoreUI AUI, Aurora_StartUp Obj) throws UnsupportedAudioFileException {
        this.loader = loader;
        this.StartUp_Obj = Obj;

        //.......Load New Main Pane Config
        ui = AUI;
        ressource = new aResourceManager("");
        this.storage = StartUp_Obj.getAuroraStorage();

//        try {
//            ui.setSFX();
//        } catch (IOException ex) {
//            Logger.getLogger(Aurora_MainWindow.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (LineUnavailableException ex) {
//            Logger.getLogger(Aurora_MainWindow.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(Aurora_MainWindow.class.getName()).log(Level.SEVERE, null, ex);
//        }


    }

    public Aurora_StartUp getStartUp_Obj() {
        return StartUp_Obj;
    }

    public void setObject(Aurora_Dashboard aObj_dash) {

        this.dash_Obj = aObj_dash;
    }

    public void loadGUI() {
        ////.....Override Aurora UI for Main Window
        setSizes();

        ui.getImgLogo().setImgURl("Aurora_Header2.png"); 
        ui.getImgLogo().setImageSize(SIZE_ImageWidth, SIZE_ImageHeight);

        ui.getPnlBottom().setPreferredSize(new Dimension(ui.getPnlBottom().getWidth(), SIZE_BottomPaneHeightAdjust));
        ui.getPnlBottom().setImageHeight(SIZE_BottomPaneHeightAdjust);

        ui.getPnlTop().setImageHeight(SIZE_TopHeight);
        ui.getPnlTop().setPreferredSize(new Dimension(ui.getPnlTop().getWidth(), ui.getPnlTop().getImageHeight() + ui.getPnlFrameControl().getHeight()));


        btnLogout = new aButton("Aurora_Logout_normal.png", "Aurora_Logout_down.png", "Aurora_Logout_over.png", SIZE_btnLogoutWidth, SIZE_btnLogoutHeight);
        btnLogout.setToolTipText("Back");

        ui.getPnlFrameControl().removeAll();

        ui.getPnlFrameControl().add(btnLogout);
        ui.getPnlFrameControl().add(ui.getBtnMin());
        ui.getPnlFrameControl().add(ui.getBtnExit());
        ui.getPnlFrameControl().setImage("Aurora_FrameButton2.png");

        ui.getPnlSouthFromTop().setPreferredSize(new Dimension(ui.getPnlSouthFromTop().getWidth(), ui.getPnlFrameControl().getHeight()));
        ui.getPnlTop().setPreferredSize(new Dimension(ui.getPnlTop().getWidth(), ui.getPnlTop().getImageHeight() + ui.getPnlFrameControl().getHeight()));
        ui.getPnlSouthFromTop().revalidate();


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


        ui.getFrame().removeKeyListener(StartUp_Obj.getStartKeyHandler());
        ui.getFrame().add(ui.getPnlBackground());
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

    public Aurora_Dashboard getDash_Obj() {
        return dash_Obj;
    }

    public void setDash_Obj(Aurora_Dashboard dash_Obj) {
        this.dash_Obj = dash_Obj;
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

    public void buildGUI() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException, FontFormatException {
        ui.getLblInfo().setText(".: Loading :.");

        ////........ Create new Components

        //Key Actions Panel (lower left)        

        imgKeyIco = new aImage("KeyboardKeys/arrows.png", ui.getSIZE_KeyIconWidth(), ui.getSIZE_KeyIconHeight());
        lblKeyAction = new JLabel(" Move ");
        lblKeyAction.setFont(ui.getDefaultFont().deriveFont(Font.PLAIN, ui.getSIZE_keysFont()));
        lblKeyAction.setForeground(Color.YELLOW);


        //Press Enter Icons
        ui.getPnlKeyToPress().add(ui.getImgKeyIco());
        ui.getPnlKeyToPress().add(ui.getLblKeyAction());

        //Use Arrow Keys Icons
        ui.getPnlKeyToPress().add(imgKeyIco);
        ui.getPnlKeyToPress().add(lblKeyAction);

        //Images inside carousels

        imgSetting.setImageSize(SIZE_CarouselImageWidth, SIZE_CarouselImageHeight);
        imgProfile.setImageSize(SIZE_CarouselImageWidth, SIZE_CarouselImageHeight);
        imgNet.setImageSize(SIZE_CarouselImageWidth, SIZE_CarouselImageHeight);

        if (storage.getStoredLibrary().getGameNames() == null || storage.getStoredLibrary().getGameNames().isEmpty()) {

            aImagePane blank = new aImagePane("Blank-Case.png", SIZE_GameCoverWidth, SIZE_GameCoverHeight);
            imgGame = blank; //Name change for carousel

        } else {
            Random rand = new Random();
            System.out.println("Storage Size " + storage.getStoredLibrary().getGameNames().size());

            int randomNum = rand.nextInt(storage.getStoredLibrary().getGameNames().size());

            System.out.println("Random Num " + randomNum);

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
        settingsPane.addKeyListener(new Aurora_Dashboard.CarouselKeyListener());
        

        profilePane = new aCarouselPane(URL, (int) SIZE_CarouselWidth + 25, SIZE_CarouselHeight - 25, true, titleGamer, "gamer pane");
        profilePane.setName("profilePane");
        profilePane.addKeyListener(new Aurora_Dashboard.CarouselKeyListener());

        libraryPane = new aCarouselPane(URL, (int) SIZE_CarouselWidth + 25, SIZE_CarouselHeight - 25, true, titleLibrary, "library pane");
        libraryPane.setName("libraryPane");
        libraryPane.addKeyListener(new Aurora_Dashboard.CarouselKeyListener());

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
        Carousel.addKeyListener(new Aurora_Dashboard.CarouselKeyListener());


        ///.....Check for the Enter Button Press OR Mouse Click

        profilePane.addMouseListener(new Aurora_Dashboard.CarouselPaneMouseListener());
        settingsPane.addMouseListener(new Aurora_Dashboard.CarouselPaneMouseListener());
        libraryPane.addMouseListener(new Aurora_Dashboard.CarouselPaneMouseListener());

        auroraNetPane.addMouseListener(new Aurora_Dashboard.CarouselPaneMouseListener());
        Carousel.addMouseWheelListener(new Aurora_Dashboard.carouselPaneMouseWheelListener());



///////////////////////////



        //Carousel Buttons

        btnCarouselLeft = new aButton("Aurora_left_normal.png", "Aurora_left_down.png", "Aurora_left_over.png", SIZE_CarouselButtonWidth, SIZE_CarouselButtonHeight);
        btnCarouselLeft.addActionListener(new Aurora_Dashboard.LeftListener());
        btnCarouselLeft.addKeyListener(new Aurora_Dashboard.CarouselKeyListener());

        btnCarouselRight = new aButton("Aurora_right_normal.png", "Aurora_right_down.png", "Aurora_right_over.png", SIZE_CarouselButtonWidth, SIZE_CarouselButtonHeight);
        btnCarouselRight.addActionListener(new Aurora_Dashboard.RightListener());
        btnCarouselRight.addKeyListener(new Aurora_Dashboard.CarouselKeyListener());

        

        //Info Bar

        infoArray = new ArrayList();
        infoArray.add(ui.getVi().VI(aXAVI.inx_Welcome) + ", ");
        infoArray.add("How are you doing Today " + ui.getVi().VI(aXAVI.inx_User) + " We hope you enjoy this Alpha release of the Aurora Game Manager");
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

        ui.getPnlCenter().add(BorderLayout.CENTER, Carousel);

        ui.getPnlCenterFromBottom().add(BorderLayout.EAST, btnCarouselRight);
        ui.getPnlCenterFromBottom().add(info, BorderLayout.CENTER);
        ui.getPnlCenterFromBottom().add(BorderLayout.WEST, btnCarouselLeft);

        //Load GameCover Cover
        if (Game != null) {
            Game.update();
            Game.removeInteraction();
            Game.getInteractivePane().addMouseListener(new Aurora_Dashboard.CarouselLibraryMouseListener());
        }



        //Finished loading so change text
        ui.getLblInfo().setText(" Dashboard ");
        
        //Finalize
        ui.getFrame().getContentPane().addKeyListener(new Aurora_Dashboard.CarouselKeyListener());
        ui.getFrame().addKeyListener(new Aurora_Dashboard.CarouselKeyListener());
        ui.getPnlBackground().addKeyListener(new Aurora_Dashboard.CarouselKeyListener());

        ui.getFrame().repaint();
        ui.getFrame().requestFocus();

    }

    private void setSizes() {

        int Ratio = (ui.getFrame().getHeight() / ui.getFrame().getWidth());

        if (ui.isLargeScreen()) {
            SIZE_TopHeight = ui.getPnlCenter().getHeight() / 8;
            SIZE_btnLogoutWidth = 0;
            SIZE_btnLogoutHeight = 0;
            SIZE_CarouselWidth = (int) (ui.getFrame().getWidth() / 42) * 16;
            SIZE_CarouselHeight = ui.getFrame().getHeight() - (ui.getFrame().getWidth() / 6);
            SIZE_GameCoverHeight = SIZE_CarouselHeight - (2 * SIZE_CarouselHeight / 6);
            SIZE_GameCoverWidth = (int) SIZE_CarouselWidth - (int) (SIZE_CarouselWidth / 4);
            SIZE_CarouselImageWidth = SIZE_CarouselHeight - (2 * SIZE_CarouselHeight / 6) - (Ratio / 8);
            SIZE_CarouselImageHeight = (int) SIZE_CarouselWidth - (int) (SIZE_CarouselWidth / 4) - 20;
            SIZE_ImageHeight = SIZE_TopHeight / 2 + 20;
            SIZE_ImageWidth = ui.getFrame().getWidth() / 2 + 20;

            SIZE_BottomPaneHeightAdjust = ui.getSIZE_pnlBottom() / 2 + ui.getFrame().getHeight() / 50 + 25;
            SIZE_TopPaneHeighAdjust = ui.getPnlCenter().getHeight() / 5 - Ratio / 10;
            SIZE_CarouselButtonWidth = ui.getFrame().getWidth() / 12 + 10;
            SIZE_CarouselButtonHeight = ui.getFrame().getHeight() / 15 + 10;
            SIZE_InfobarWidth = ui.getFrame().getSize().width - (SIZE_CarouselButtonWidth * 2 + 65);
            SIZE_InfobarHeight = 75;


        } else {
            SIZE_TopHeight = ui.getPnlCenter().getHeight() / 8;
            SIZE_btnLogoutWidth = 30;
            SIZE_btnLogoutHeight = 35;
            SIZE_CarouselWidth = (int) (ui.getFrame().getWidth() / 40) * 16;
            SIZE_CarouselHeight = ui.getFrame().getHeight() - (ui.getFrame().getWidth() / 6);
            SIZE_GameCoverHeight = SIZE_CarouselHeight - (2 * SIZE_CarouselHeight / 6);
            SIZE_GameCoverWidth = (int) SIZE_CarouselWidth - (int) (SIZE_CarouselWidth / 4);
            SIZE_CarouselImageWidth = (int) SIZE_CarouselWidth - (int) (400 / 2) - (Ratio * 2);
            SIZE_CarouselImageHeight = (int) SIZE_CarouselHeight - (450 / 2) - (Ratio * 2) - 55;
            SIZE_ImageHeight = SIZE_TopHeight / 2 + 20;
            SIZE_ImageWidth = ui.getFrame().getWidth() / 2 + 20;

            SIZE_BottomPaneHeightAdjust = ui.getSIZE_pnlBottom() / 2 + ui.getFrame().getHeight() / 90 + 25;
            SIZE_TopPaneHeighAdjust = ui.getPnlCenter().getHeight() / 5 - Ratio / 10;
            SIZE_CarouselButtonWidth = ui.getFrame().getWidth() / 12;
            SIZE_CarouselButtonHeight = ui.getFrame().getHeight() / 15;
            SIZE_InfobarWidth = ui.getFrame().getSize().width - (SIZE_CarouselButtonWidth * 2 + 60);
            SIZE_InfobarHeight = SIZE_CarouselButtonHeight - SIZE_BottomPaneHeightAdjust / 18;


            System.out.println("WIDTH " + SIZE_CarouselWidth);
        }

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
        return ui;
    }

    public Aurora_Dashboard getMainWin() {
        return dash_Obj;
    }

    public aButton getBtnCarouselLeft() {
        return btnCarouselLeft;
    }

    public aButton getBtnCarouselRight() {
        return btnCarouselRight;
    }

    AuroraCoreUI getUI() {
        return this.ui;
    }

    @Override
    public void createGUI() {
        
    }

    class RightListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            //SOUND
//            try {
//                ui.sfxClunk.Play();
//            } catch (UnsupportedAudioFileException ex) {
//                Logger.getLogger(Aurora_MainWindow.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (IOException ex) {
//                Logger.getLogger(Aurora_MainWindow.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (LineUnavailableException ex) {
//                Logger.getLogger(Aurora_MainWindow.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(Aurora_MainWindow.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
            Carousel.MoveLeft();


        }
    }

    class LeftListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //SOUND
//            try {
//                ui.sfxClunk.Play();
//            } catch (UnsupportedAudioFileException ex) {
//                Logger.getLogger(Aurora_MainWindow.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (IOException ex) {
//                Logger.getLogger(Aurora_MainWindow.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (LineUnavailableException ex) {
//                Logger.getLogger(Aurora_MainWindow.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(Aurora_MainWindow.class.getName()).log(Level.SEVERE, null, ex);
//            }
            Carousel.MoveRight();

        }
    }

    class CarouselKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {


            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                Carousel.MoveRight();
            }

            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                Carousel.MoveLeft();
            }

            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                ui.showExitDilog();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                aCarouselPane pane = Carousel.getCenterPane();

                if (pane == libraryPane) {
                    //action on click right Panel
                    Aurora_GameLibrary library = new Aurora_GameLibrary(StartUp_Obj.getAuroraStorage(), dash_Obj, ui);
                    library.createGUI();
                } else if (pane == profilePane) {
                    Aurora_GamerProfile aObj_profile = new Aurora_GamerProfile(dash_Obj, ui);
                    aObj_profile.createGUI();
                } else if (pane == settingsPane) {
                    Aurora_Settings aObj_settings = new Aurora_Settings(dash_Obj, ui);
                    aObj_settings.createGUI();
                } else if (pane == auroraNetPane) {
                    // do nothing for now
                }
            }


        }
    }

    public class CarouselLibraryMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("CLICKED");
            if (dash_Obj != null) {
                Aurora_GameLibrary library = new Aurora_GameLibrary(StartUp_Obj.getAuroraStorage(), dash_Obj, ui);
                library.createGUI();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    ///....Mouse Click Handlers...////////
    public class CarouselPaneMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("CLICKED");
            aCarouselPane pane = (aCarouselPane) e.getComponent();

            if (pane.getPointX() == Carousel.getRightX()) {
                Carousel.MoveLeft();
            } else if (pane.getPointX() == Carousel.getLeftX()) {
                Carousel.MoveRight();
            } else if (pane.getPointX() == Carousel.getCentX()) {
                if (pane == libraryPane) {
                    //action on click right Panel
                    if (dash_Obj != null) {
                        Aurora_GameLibrary library = new Aurora_GameLibrary(StartUp_Obj.getAuroraStorage(), dash_Obj, ui);
                        library.createGUI();
                    }
                } else if (pane == profilePane) {
                    Aurora_GamerProfile aObj_profile = new Aurora_GamerProfile(dash_Obj, ui);
                    aObj_profile.createGUI();
                } else if (pane == settingsPane) {
                    Aurora_Settings aObj_settings = new Aurora_Settings(dash_Obj, ui);
                    aObj_settings.createGUI();
                } else if (pane == auroraNetPane) {
                    // do nothing for now
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    class carouselPaneMouseWheelListener implements MouseWheelListener {

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {


            int numberClicks = e.getWheelRotation();
            System.out.println("Mouse wheel moved " + numberClicks);

            if (numberClicks < 0) {
                Carousel.MoveLeft();
            } else if (numberClicks > 0) {
                Carousel.MoveRight();
            }

        }
    }
}
