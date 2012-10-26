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
import aurora.V1.core.screen_logic.DashboardLogic;
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
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * .------------------------------------------------------------------------.
 * | DashboardUI :: Aurora App Class
 * .------------------------------------------------------------------------.
 * |
 * | This class contains the UI attached to an appropriate *Handler*
 * | and * *Logic* class which handle the actions caused by the UI
 * | components found here
 * |
 * | This class must follow the rules stated in the AuroraScreenUI
 * | Interface. The *Handler* and *Logic* classes
 * | The Handler class is called: DashboardHandler
 * | The Logic class is called: DashboardLogic
 * |
 * |
 * |
 * .........................................................................
 *
 * @author sammy <sguergachi@gmail.com> carlos <camachado@gmail.com>
 *
 */
public class DashboardUI extends AuroraApp {

    /**
     * The main carousel used to navigate AuroraApps.
     */
    private aCarousel carousel;

    /**
     * Button to make carousel move left.
     */
    private aButton btnCarouselLeft;

    /**
     * Button to make carousel move right.
     */
    private aButton btnCarouselRight;

    /**
     * Image with Glow state of the Profile Title.
     */
    private aImage titleProfileGlow;

    /**
     * Image with Normal state of the Profile Title.
     */
    private aImage titleProfileNorm;

    /**
     * Image with Glow state of the Settings Title.
     */
    private aImage titleSettingGlow;

    /**
     * Image with Normal state of the Settings Title.
     */
    private aImage titleSettingNorm;

    /**
     * Image with Glow state of the Library Title.
     */
    private aImage titleLibraryGlow;

    /**
     * Image with Normal state of the Library Title.
     */
    private aImage titleLibraryNorm;

    /**
     * Image with Glow state of the AuroraNet Title.
     */
    private aImage titleAuroraNetGlow;

    /**
     * Image with Normal state of the AuroraNet Title.
     */
    private aImage titleAuroraNetNorm;

    /**
     * Title component containing both Glow and Normal state of Profile item.
     */
    private aCarouselTitle titleProfile;

    /**
     * Title component containing both Glow and Normal state of Settings item.
     */
    private aCarouselTitle titleSetting;

    /**
     * Title component containing both Glow and Normal state of Library item.
     */
    private aCarouselTitle titleLibrary;

    /**
     * Title component containing both Glow and Normal state of AuroraNet item.
     */
    private aCarouselTitle titleAuroraNet;

    /**
     * Panel Containing Title and Icon representing the Library in Carousel.
     */
    private aCarouselPane paneLibrary;

    /**
     * Panel Containing Title and Icon representing the Library in Settings.
     */
    private aCarouselPane paneSettings;

    /**
     * Panel Containing Title and Icon representing the Library in Profile.
     */
    private aCarouselPane paneProfile;

    /**
     * Panel Containing Title and Icon representing the Library in AuroraNet.
     */
    private aCarouselPane paneNet;

    /**
     * Image of Icon representing AuroraNet Carousel pane.
     */
    private aImage icoNet;

    /**
     * Image of Icon representing Profile Carousel pane.
     */
    private aImage icoProfile;

    /**
     * Image of Icon representing Settings Carousel pane.
     */
    private aImage icoSetting;

    /**
     * Image of Icon representing Library Carousel pane.
     */
    private aImagePane icoLibrary;

    /**
     * Game component which generates the icon for the Library carousel pane.
     */
    private Game randomGame;

    /**
     * Image of Keyboard Arrows indicating ability to use keyboard to navigate.
     */
    private aImage keyArrows;

    /**
     * Label describing what the Keyboard Icon will do.
     */
    private JLabel lblKeyAction;

    /**
     * A Scrolling Information ticker bar.
     */
    private aInfoFeed infoFeed;

    /**
     * An array list containing all of the info being fed to the infoFeed.
     */
    private ArrayList<String> infoArray;

    /**
     * Button in frame controls to go back to Dashboard.
     */
    private aButton btnBack;

    /**
     * Size Constant.
     */
    private int btnBackWidth;

    /**
     * Size Constant.
     */
    private int btnBackHeight;

    /**
     * Size Constant.
     */
    private double carouselWidth;

    /**
     * Size Constant.
     */
    private int carouselHeight;

    /**
     * Size Constant.
     */
    private int gameCoverHeight;

    /**
     * Size Constant.
     */
    private int gameCoverWidth;

    /**
     * Size Constant.
     */
    private int carouselImageWidth;

    /**
     * Size Constant.
     */
    private int carouselImageHeight;

    /**
     * Size Constant.
     */
    private int logoHeight;

    /**
     * Size Constant.
     */
    private int infoFeedWidth;

    /**
     * Size Constant.
     */
    private int infoFeedHeight;

    /**
     * Size Constant.
     */
    private int bottomPaneHeightAdjust;

    /**
     * Size Constant.
     */
    private int topPaneHeighAdjust;

    /**
     * Size Constant.
     */
    private int topHeight;

    /**
     * Size Constant.
     */
    private int logoWidth;

    /**
     * Size Constant.
     */
    private int carouselButtonWidth;

    /**
     * Size Constant.
     */
    private int carouselButtonHeight;

    /**
     * The loader that does the transition animation and loads the DashboardUI.
     */
    private StartLoader loader;

    /**
     * This is the Local Storage Instance.
     */
    private AuroraStorage storage;

    /**
     * This is the Previous Screen, which contains the Local Storage Instance.
     */
    private final StartScreenUI startUI;

    /**
     * This is the CoreUI Canvas.
     */
    private AuroraCoreUI coreUI;

    /**
     * This is the Handler for the DashboardUIs Actions.
     */
    private DashboardHandler handler;

    /**
     * This is the Logic for the DashboardUIs Processing.
     */
    private final DashboardLogic logic;

    /**
     * .-----------------------------------------------------------------------.
     * | DashboardUI(AuroraCoreUI, StartScreenUI)
     * .-----------------------------------------------------------------------.
     * |
     * | This is the Constructor of the Dashboard UI class.
     * |
     * | The Constructor of Screen Classes must initialize/create both a
     * | Handler and a Logic object which should contain the UI as a parameter
     * |
     * .........................................................................
     *
     * @param auroraCoreUi  AuroraCoreUI
     * @param startScreenUi StartScreenUI
     *
     */
    public DashboardUI(final AuroraCoreUI auroraCoreUi,
                       final StartScreenUI startScreenUi) {

        //* Other core objects *//
        this.startUI = startScreenUi;
        this.storage = startUI.getAuroraStorage();

        //* Core UI Canvas *//
        this.coreUI = auroraCoreUi;


        //* The Dashboard Handler + Logic *//
        this.handler = new DashboardHandler(this);
        this.logic = new DashboardLogic(this);




    }

    @Override
    public final void loadUI() {

        //Initialize Sizes
        setSizes();

        //----------------------------CAROUSEL--------------------------------//

        carousel = new aCarousel(carouselWidth, carouselHeight,
                Toolkit.getDefaultToolkit().getScreenSize().width);

        titleSettingGlow = new aImage("settings_glow.png");
        titleSettingNorm = new aImage("settings_normal.png");

        titleLibraryGlow = new aImage("gamelibrary_glow.png");
        titleLibraryNorm = new aImage("gamelibrary_normal.png");

        titleProfileGlow = new aImage("Profileprofile_glow.png");
        titleProfileNorm = new aImage("Profileprofile_normal.png");

        titleAuroraNetGlow = new aImage("auroranet_glow.png");
        titleAuroraNetNorm = new aImage("auroranet_normal.png");

        icoProfile = new aImage("Aurora_Profile.png");
        icoSetting = new aImage("Aurora_Settings.png");
        icoNet = new aImage("ComingSoon.png");
        icoLibrary = logic.getLibraryIcon();

        titleProfile = new aCarouselTitle(titleProfileNorm, titleProfileGlow);
        titleSetting = new aCarouselTitle(titleSettingNorm, titleSettingGlow);
        titleLibrary = new aCarouselTitle(titleLibraryNorm, titleLibraryGlow);
        titleAuroraNet = new aCarouselTitle(titleAuroraNetNorm,
                titleAuroraNetGlow);

        paneSettings = new aCarouselPane("HexPane.png", (int) carouselWidth + 25,
                carouselHeight - 25, true, titleSetting, "Setting Pane");
        paneProfile = new aCarouselPane("HexPane.png", (int) carouselWidth + 25,
                carouselHeight - 25, true, titleProfile, "Profile pane");
        paneLibrary = new aCarouselPane("HexPane.png", (int) carouselWidth + 25,
                carouselHeight - 25, true, titleLibrary, "library pane");
        paneNet = new aCarouselPane("HexPane.png", (int) carouselWidth + 25,
                carouselHeight - 25, true, titleAuroraNet, "auroranet");

        btnCarouselLeft = new aButton("Aurora_left_normal.png",
                "Aurora_left_down.png", "Aurora_left_over.png",
                carouselButtonWidth, carouselButtonHeight);

        btnCarouselRight = new aButton("Aurora_right_normal.png",
                "Aurora_right_down.png", "Aurora_right_over.png",
                carouselButtonWidth, carouselButtonHeight);

        //------------------------------|||-----------------------------------//



        //----------------------------INFOFEED--------------------------------//

        infoFeed = new aInfoFeed("InfoBar.png", infoFeedWidth,
                infoFeedHeight, logic.createFeed(null));
        //------------------------------|||-----------------------------------//



        //----------------------------CORE UI---------------------------------//

        //* Indicate to User DashboardUI is loading. *//
        coreUI.getInfoLabel().setText(".: Loading :.");

        btnBack = new aButton("Aurora_Logout_normal.png",
                "Aurora_Logout_down.png", "Aurora_Logout_over.png",
                btnBackWidth, btnBackHeight);

        keyArrows = new aImage("KeyboardKeys/arrows.png", coreUI.
                getKeyIconWidth(), coreUI.getKeyIconHeight());

        lblKeyAction = new JLabel(" Move ");
        //------------------------------|||-----------------------------------//

    }

    @Override
    public final void buildUI() {

        //----------------------------CAROUSEL--------------------------------//

        //* Set size of Icons inside each Carousel Pane *//

        icoSetting.setImageSize(carouselImageWidth,
                carouselImageHeight);
        icoProfile.setImageSize(carouselImageWidth,
                carouselImageHeight);
        icoNet.setImageSize(carouselImageWidth, carouselImageHeight);
        icoLibrary.setPreferredSize(new Dimension(gameCoverWidth,
                gameCoverHeight));


        //* Set ID For each Panel and add ENTER Key Listener *//

        paneSettings.setName("settingsPane");
        paneSettings.addKeyListener(handler.new CarouselKeyListener());

        paneProfile.setName("profilePane");
        paneProfile.addKeyListener(handler.new CarouselKeyListener());

        paneLibrary.setName("libraryPane");
        paneLibrary.addKeyListener(handler.new CarouselKeyListener());

        paneNet.setName("auroraNetPane");
        paneNet.addKeyListener(handler.new CarouselKeyListener());

        //* Set ID For each Panel and add ENTER Key Listener *//

        paneSettings.addContent(icoSetting, Title.NORMAL);
        paneProfile.addContent(icoProfile, Title.NORMAL);
        paneNet.addContent(icoNet, Title.NORMAL);
        //* Initially set to Glow state *//
        paneLibrary.addContent(icoLibrary, Title.GLOW);


        //* Add each Pane to the Carousel *//

        carousel.addPane(paneSettings);
        carousel.addPane(paneLibrary);
        carousel.addPane(paneProfile);
        carousel.addPane(paneNet);
        carousel.addKeyListener(handler.new CarouselKeyListener());


        //* Check for the Enter Button Press OR Mouse Click *//

        paneProfile.addMouseListener(handler.new CarouselPaneMouseListener());
        paneSettings.addMouseListener(handler.new CarouselPaneMouseListener());
        paneLibrary.addMouseListener(handler.new CarouselPaneMouseListener());
        paneNet.addMouseListener(handler.new CarouselPaneMouseListener());

        //* Check for Mouse Wheel Rotation *//

        carousel.
                addMouseWheelListener(
                handler.new carouselPaneMouseWheelListener());


        //* Add Listeners to the Left and Right Carousel Buttons *//

        btnCarouselLeft.addActionListener(handler.new LeftListener());
        btnCarouselLeft.addKeyListener(handler.new CarouselKeyListener());

        btnCarouselRight.addActionListener(handler.new RightListener());
        btnCarouselRight.addKeyListener(handler.new CarouselKeyListener());

        //------------------------------|||-----------------------------------//



        //----------------------------INFOFEED--------------------------------//
        infoFeed.go();


        coreUI.getCenterPanel().add(BorderLayout.CENTER, carousel);

        //------------------------------|||-----------------------------------//



        //----------------------------CORE UI---------------------------------//



        //* Set bigger Logo to Header *//
        coreUI.getLogoImage().setImgURl("Aurora_Header2.png");
        coreUI.getLogoImage().setImageSize(logoWidth, logoHeight);

        //* Set bigger background image for Frame Control panel *//
        coreUI.getFrameControlImagePane().setImage("Aurora_FrameButton2.png");

        //* Set size of Top panel in CoreUI *//
        coreUI.getTopImagePane().setImageHeight(topHeight);
        coreUI.getTopImagePane().setPreferredSize(new Dimension(coreUI
                .getTopImagePane().
                getWidth(), coreUI.getTopImagePane().getImageHeight() + coreUI.
                getFrameControlImagePane().getHeight()));

        //* Set size of Bottom panel in CoreUI *//
        coreUI.getBottomImagePane().setPreferredSize(new Dimension(coreUI.
                getBottomImagePane().getWidth(), bottomPaneHeightAdjust));
        coreUI.getBottomImagePane().setImageHeight(bottomPaneHeightAdjust);

        //* Set size of Top Panels *//
        coreUI.getSouthFromTopPanel().setPreferredSize(new Dimension(coreUI.
                getSouthFromTopPanel().getWidth(), coreUI
                .getFrameControlImagePane().
                getHeight()));
        coreUI.getTopImagePane().setPreferredSize(new Dimension(coreUI
                .getTopImagePane().
                getWidth(), coreUI.getTopImagePane().getImageHeight() + coreUI.
                getFrameControlImagePane().getHeight()));
        coreUI.getSouthFromTopPanel().revalidate();

        //* Set Font of Keyboard Action Label *//
        lblKeyAction.setFont(coreUI.getDefaultFont().deriveFont(Font.PLAIN,
                coreUI.getKeysFontSize()));
        lblKeyAction.setForeground(Color.YELLOW);



        //* Add  Components to CoreUI *//


        //* Add Back Button to Frame Controls *//
        coreUI.getFrameControlImagePane().add(btnBack, 0);
        btnBack.setToolTipText("Back");


        //* Add Arrow Keys Icons *//
        coreUI.getKeyToPressPanel().add(keyArrows);
        coreUI.getKeyToPressPanel().add(lblKeyAction);

        //* Add Enter Key Icons *//
        coreUI.getKeyToPressPanel().add(coreUI.getKeyIconImage());
        coreUI.getKeyToPressPanel().add(coreUI.getKeyActionLabel());

        //* Add Carousel to Center Panel *//
        coreUI.getCenterPanel().add(BorderLayout.CENTER, carousel);

        //* Add To Bottom Panel  InfoFeed and both Carousel Buttons*//
        coreUI.getCenterFromBottomPanel().add(BorderLayout.EAST,
                btnCarouselRight);
        coreUI.getCenterFromBottomPanel().add(BorderLayout.CENTER, infoFeed);
        coreUI.getCenterFromBottomPanel()
                .add(BorderLayout.WEST, btnCarouselLeft);


        //* CoreUI Listeners *//

        //* Remove ENTER KeyListener attached to frame. *//
        coreUI.getFrame().removeKeyListener(startUI.getStartKeyHandler());

        //* Add Carousel KeyListener to Background *//
        coreUI.getFrame().getContentPane().
                addKeyListener(handler.new CarouselKeyListener());
        coreUI.getFrame().addKeyListener(handler.new CarouselKeyListener());
        coreUI.getBackgroundImagePane().
                addKeyListener(handler.new CarouselKeyListener());

        //* Finished loading so change text *//
        coreUI.getInfoLabel().setText(" Dashboard ");

        //* Final Refresh and Refocus *//
        coreUI.getFrame().repaint();
        coreUI.getFrame().requestFocus();

        //------------------------------|||-----------------------------------//

    }

    /**
     * .-----------------------------------------------------------------------.
     * | setSizes()
     * .-----------------------------------------------------------------------.
     * |
     * | This method generates the initial size of each component in this UI
     * |
     * | The set size uses some trial and error based calculations to determine
     * | sizes for large screens and for small screens. This method is not
     * | Perfect but its an improvement on previous implementations
     * |
     */
    private void setSizes() {

        int Ratio = (coreUI.getFrame().getHeight() / coreUI.getFrame().
                getWidth());

        if (coreUI.isLargeScreen()) {
            topHeight = coreUI.getCenterPanel().getHeight() / 8;
            btnBackWidth = 0;
            btnBackHeight = 0;
            carouselWidth = (int) (coreUI.getFrame().getWidth() / 42) * 16;
            carouselHeight = coreUI.getFrame().getHeight() - (coreUI.
                    getFrame().getWidth() / 6);
            gameCoverHeight = carouselHeight - (2 * carouselHeight / 6);
            gameCoverWidth = (int) carouselWidth - (int) (carouselWidth / 4);
            carouselImageWidth = carouselHeight - (2 * carouselHeight / 6)
                                 - (Ratio / 8);
            carouselImageHeight = (int) carouselWidth
                                  - (int) (carouselWidth / 4) - 20;
            logoHeight = topHeight / 2 + 20;
            logoWidth = coreUI.getFrame().getWidth() / 2 + 20;

            bottomPaneHeightAdjust = coreUI.getBottomPanelSize() / 2 + coreUI.
                    getFrame().getHeight() / 50 + 25;
            topPaneHeighAdjust = coreUI.getCenterPanel().getHeight() / 5 - Ratio
                                                                           / 10;

            carouselButtonWidth = coreUI.getFrame().getWidth() / 12 + 10;
            carouselButtonHeight = coreUI.getFrame().getHeight() / 15 + 10;
            infoFeedWidth = coreUI.getFrame().getSize().width
                            - (carouselButtonWidth * 2 + 65);
            infoFeedHeight = 75;


        } else {
            topHeight = coreUI.getCenterPanel().getHeight() / 8;
            btnBackWidth = 30;
            btnBackHeight = 35;
            carouselWidth = (int) (coreUI.getFrame().getWidth() / 40) * 16;
            carouselHeight = coreUI.getFrame().getHeight() - (coreUI.
                    getFrame().getWidth() / 6);
            gameCoverHeight = carouselHeight - (2 * carouselHeight / 6);
            gameCoverWidth = (int) carouselWidth - (int) (carouselWidth / 4);
            carouselImageWidth = (int) carouselWidth - (int) (400 / 2) - (Ratio
                                                                          * 2);
            carouselImageHeight = (int) carouselHeight - (450 / 2) - (Ratio * 2)
                                  - 55;
            logoHeight = topHeight / 2 + 20;
            logoWidth = coreUI.getFrame().getWidth() / 2 + 20;

            bottomPaneHeightAdjust = coreUI.getBottomPanelSize() / 2 + coreUI.
                    getFrame().getHeight() / 90 + 25;
            topPaneHeighAdjust = coreUI.getCenterPanel().getHeight() / 5 - Ratio
                                                                           / 10;

            carouselButtonWidth = coreUI.getFrame().getWidth() / 12;
            carouselButtonHeight = coreUI.getFrame().getHeight() / 15;
            infoFeedWidth = coreUI.getFrame().getSize().width
                            - (carouselButtonWidth * 2 + 60);
            infoFeedHeight = carouselButtonHeight - bottomPaneHeightAdjust / 18;


            System.out.println("WIDTH " + carouselWidth);
        }

    }

    //----------------------------GETTER & SETTER-----------------------------//
    /**
     * Get the DashboardLogic instance generated in DashboardUI.
     * <p/>
     * @return DashboardLogic
     */
    public final DashboardLogic getDashboardLogic() {
        return logic;
    }

    /**
     * Get the DashboardHandler instance generated in DashboardUI.
     * <p/>
     * @return DashboardHandler
     */
    public final DashboardHandler getDashboardHandler() {
        return handler;
    }

    /**
     * Get the AuroraCoreUI generated by StartScreenUI.
     * <p/>
     * @return AuroraCoreUI
     */
    public final AuroraCoreUI getCoreUI() {
        return this.coreUI;
    }

    /**
     * Get the DashboardLogic instance generated in DashboardUI.
     * <p/>
     * @return StartScreenUI
     */
    public final StartScreenUI getStartUI() {
        return startUI;
    }

    /**
     * The Glow State of the Profile Pane title.
     * <p/>
     * @return aImage
     */
    public final aImage getTitleProfileGlow() {
        return titleProfileGlow;
    }

    /**
     * Set the Normal state of the Profile Pane title.
     * <p/>
     * @param aTitleProfileGlow aImage
     */
    public final void setTitleProfileGlow(final aImage aTitleProfileGlow) {
        this.titleProfileGlow = aTitleProfileGlow;
    }

    /**
     * The Normal State of the Profile Pane title.
     * <p/>
     * @return aImage
     */
    public final aImage getTitleimgProfileNorm() {
        return titleProfileNorm;
    }

    /**
     * Set the Normal state of the Profile Pane title.
     * <p/>
     * @param aTitleimgProfileNorm aImage
     */
    public final void setTitleimgProfileNorm(final aImage aTitleimgProfileNorm) {
        this.titleProfileNorm = aTitleimgProfileNorm;
    }

    /**
     * The Glow State of the Settings Pane title.
     * <p/>
     * @return aImage
     */
    public final aImage getTitleSettingGlow() {
        return titleSettingGlow;
    }

    /**
     * Set the Glow state of the Settings Pane title.
     * <p/>
     * @param aTitleSettingGlow aImage
     */
    public final void setTitleSettingGlow(final aImage aTitleSettingGlow) {
        this.titleSettingGlow = aTitleSettingGlow;
    }

    /**
     * The Normal State of the Settings Pane title.
     * <p/>
     * @return aImage
     */
    public final aImage getTitleSettingNorm() {
        return titleSettingNorm;
    }

    /**
     * Set the Normal state of the Settings Pane title.
     * <p/>
     * @param aTitleSettingNorm aImage
     */
    public final void setTitleSettingNorm(final aImage aTitleSettingNorm) {
        this.titleSettingNorm = aTitleSettingNorm;
    }

    /**
     * The Glow State of the Library Pane title.
     * <p/>
     * @return aImage
     */
    public final aImage getTitleLibraryGlow() {
        return titleLibraryGlow;
    }

    /**
     * Set the Glow state of the Library Pane title.
     * <p/>
     * @param aTitleLibraryGlow aImage
     */
    public final void setTitleLibraryGlow(final aImage aTitleLibraryGlow) {
        this.titleLibraryGlow = aTitleLibraryGlow;
    }

    /**
     * The Normal State of the Library Pane title.
     * <p/>
     * @return aImage
     */
    public final aImage getTitleLibraryNorm() {
        return titleLibraryNorm;
    }

    /**
     * Set the Normal state of the Library Pane title.
     * <p/>
     * @param aTitleLibraryNorm aImage
     */
    public final void setTitleLibraryNorm(final aImage aTitleLibraryNorm) {
        this.titleLibraryNorm = aTitleLibraryNorm;
    }

    /**
     * The Glow State of the AuroraNet Pane title.
     * <p/>
     * @return aImage
     */
    public final aImage getTitleAuroraNetGlow() {
        return titleAuroraNetGlow;
    }

    /**
     * Set the Glow state of the AuroraNet Pane title.
     * <p/>
     * @param aTitleAuroraNetGlow aImage
     */
    public final void setTitleAuroraNetGlow(final aImage aTitleAuroraNetGlow) {
        this.titleAuroraNetGlow = aTitleAuroraNetGlow;
    }

    /**
     * The Normal State of the AuroraNet Pane title.
     * <p/>
     * @return aImage
     */
    public final aImage getTitleAuroraNetNorm() {
        return titleAuroraNetNorm;
    }

    /**
     * Set the Normal state of the AuroraNet Pane title.
     * <p/>
     * @param aTitleAuroraNetNorm aImage
     */
    public final void setTitleAuroraNetNorm(final aImage aTitleAuroraNetNorm) {
        this.titleAuroraNetNorm = aTitleAuroraNetNorm;
    }

    /**
     * Get the Icon for the Profile Pane.
     * <p/>
     * @return aImage
     */
    public final aImage getImgProfile() {
        return icoProfile;
    }

    /**
     * Set the Icon for the Profile Pane.
     * <p/>
     * @param aImgProfile aImage
     */
    public final void setImgProfile(final aImage aImgProfile) {
        this.icoProfile = aImgProfile;
    }

    /**
     * Get the Icon for the Settings Pane.
     * <p/>
     * @return aImage
     */
    public final aImage getImgSetting() {
        return icoSetting;
    }

    /**
     * Set the Icon for the Settings Pane.
     * <p/>
     * @param aImgSetting aImage
     */
    public final void setImgSetting(final aImage aImgSetting) {
        this.icoSetting = aImgSetting;
    }

    /**
     * Get the Icon for the Library Pane.
     * <p/>
     * @return aImage
     */
    public final aImagePane getImgLibrary() {
        return icoLibrary;
    }

    /**
     * Set the Icon for the Library Pane.
     * <p/>
     * @param aImgLibrary aImage
     */
    public final void setImgLibrary(final aImagePane aImgLibrary) {
        this.icoLibrary = aImgLibrary;
    }

    /**
     * Get the Icon for the AuroraNet Pane.
     * <p/>
     * @return aImage
     */
    public final aImage getImgNet() {
        return icoNet;
    }

    /**
     * Set the Icon for the AuroraNet Pane.
     * <p/>
     * @param aImgNet aImage
     */
    public final void setImgNet(final aImage aImgNet) {
        this.icoNet = aImgNet;
    }

    /**
     * Get the Image representing the Key to press for an action.
     * <p/>
     * @return aImage
     */
    public final aImage getImgKeyIco() {
        return keyArrows;
    }

    /**
     * Set the Image representing the Key to press for an action.
     * <p/>
     * @param aImgKeyIco aImage
     */
    public final void setImgKeyIco(final aImage aImgKeyIco) {
        this.keyArrows = aImgKeyIco;
    }

    /**
     * Get the Label describing the action occurring when pressing a key.
     * <p/>
     * @return JLabel
     */
    public final JLabel getLblKeyAction() {
        return lblKeyAction;
    }

    /**
     * Set the Label describing the action occurring when pressing a key.
     * <p/>
     * @param aLblKeyAction JLabel
     */
    public final void setLblKeyAction(final JLabel aLblKeyAction) {
        this.lblKeyAction = aLblKeyAction;
    }

    /**
     * Get the InfoFeed component instanced in DashboardUI.
     * <p/>
     * @return aInfoFeed
     */
    public final aInfoFeed getInfoFeed() {
        return infoFeed;
    }

    /**
     * Set the InfoFeed currently used in DashboardUI.
     * <p/>
     * @param anInfoFeed aInfoFeed
     */
    public final void setInfoFeed(final aInfoFeed anInfoFeed) {
        this.infoFeed = anInfoFeed;
    }

    /**
     * Get UI component Size Value.
     * <p/>
     * @return int
     */
    public final int getBtnBackWidth() {
        return btnBackWidth;
    }

    /**
     * Set UI component Size Value.
     * <p/>
     * @param theBtnBackWidth int
     */
    public final void setBtnBackWidth(final int theBtnBackWidth) {
        this.btnBackWidth = theBtnBackWidth;
    }

    /**
     * Get UI component Size Value.
     * <p/>
     * @return int
     */
    public final int getBtnBackHeight() {
        return btnBackHeight;
    }

    /**
     * Set UI component Size Value.
     * <p/>
     * @param theBtnBackHeight int
     */
    public final void setBtnLogoutHeight(final int theBtnBackHeight) {
        this.btnBackHeight = theBtnBackHeight;
    }

    /**
     * Get UI component Size Value.
     * <p/>
     * @return double
     */
    public final double getCarouselWidth() {
        return carouselWidth;
    }

    /**
     * Set UI component Size Value.
     * <p/>
     * @param theCarouselWidth double
     */
    public final void setCarouselWidth(final double theCarouselWidth) {
        this.carouselWidth = theCarouselWidth;
    }

    /**
     * Get UI component Size Value.
     * <p/>
     * @return double
     */
    public final int getCarouselHeight() {
        return carouselHeight;
    }

    /**
     * Set UI component Size Value.
     * <p/>
     * @param theCarouselHeight int
     */
    public final void setCarouselHeight(final int theCarouselHeight) {
        this.carouselHeight = theCarouselHeight;
    }

    /**
     * Get UI component Size Value.
     * <p/>
     * @return int
     */
    public final int getGameCoverHeight() {
        return gameCoverHeight;
    }

    /**
     * Set UI component Size Value.
     * <p/>
     * @param theGameCoverHeight int
     */
    public final void setGameCoverHeight(final int theGameCoverHeight) {
        this.gameCoverHeight = theGameCoverHeight;
    }

    /**
     * Get UI component Size Value.
     * <p/>
     * @return int
     */
    public final int getGameCoverWidth() {
        return gameCoverWidth;
    }

    /**
     * Set UI component Size Value.
     * <p/>
     * @param theGameCoverWidth int
     */
    public final void setGameCoverWidth(final int theGameCoverWidth) {
        this.gameCoverWidth = theGameCoverWidth;
    }

    /**
     * Get UI component Size Value.
     * <p/>
     * @return int
     */
    public final int getCarouselImageWidth() {
        return carouselImageWidth;
    }

    /**
     * Set UI component Size Value.
     * <p/>
     * @param theCarouselImageWidth int
     */
    public final void setCarouselImageWidth(final int theCarouselImageWidth) {
        this.carouselImageWidth = theCarouselImageWidth;
    }

    /**
     * Get UI component Size Value.
     * <p/>
     * @return int
     */
    public final int getCarouselImageHeight() {
        return carouselImageHeight;
    }

    /**
     * Set UI component Size Value.
     * <p/>
     * @param theCarouselImageHeight int
     */
    public final void setCarouselImageHeight(final int theCarouselImageHeight) {
        this.carouselImageHeight = theCarouselImageHeight;
    }

    /**
     * Get UI component Size Value.
     * <p/>
     * @return int
     */
    public final int getImageHeight() {
        return logoHeight;
    }

    /**
     * Set UI component Size Value.
     * <p/>
     * @param theImageHeight int
     */
    public final void setImageHeight(final int theImageHeight) {
        this.logoHeight = theImageHeight;
    }

    /**
     * Get UI component Size Value.
     * <p/>
     * @return int
     */
    public final int getInfoFeedWidth() {
        return infoFeedWidth;
    }

    /**
     * Set UI component Size Value.
     * <p/>
     * @param theInfoFeedWidth int
     */
    public final void setInfoFeedWidth(final int theInfoFeedWidth) {
        this.infoFeedWidth = theInfoFeedWidth;
    }

    /**
     * Get UI component Size Value.
     * <p/>
     * @return int
     */
    public final int getInfobarHeight() {
        return infoFeedHeight;
    }

    /**
     * Set UI component Size Value.
     * <p/>
     * @param theInfoFeedHeight int
     */
    public final void setInfobarHeight(final int theInfoFeedHeight) {
        this.infoFeedHeight = theInfoFeedHeight;
    }

    /**
     * Get UI component Size Value.
     * <p/>
     * @return int
     */
    public final int getBottomPaneHeightAdjust() {
        return bottomPaneHeightAdjust;
    }

    /**
     * Set UI component Size Value.
     * <p/>
     * @param theBottomPaneHeightAdjust int
     */
    public final void setBottomPaneHeightAdjust(
            final int theBottomPaneHeightAdjust) {
        this.bottomPaneHeightAdjust = theBottomPaneHeightAdjust;
    }

    /**
     * Get UI component Size Value.
     * <p/>
     * @return int
     */
    public final int getTopPaneHeighAdjust() {
        return topPaneHeighAdjust;
    }

    /**
     * Set UI component Size Value.
     * <p/>
     * @param theTopPaneHeighAdjust int
     */
    public final void setTopPaneHeighAdjust(final int theTopPaneHeighAdjust) {
        this.topPaneHeighAdjust = theTopPaneHeighAdjust;
    }

    public final aCarouselTitle getTitleProfile() {
        return titleProfile;
    }

    public final void setTitleProfile(final aCarouselTitle titleProfile) {
        this.titleProfile = titleProfile;
    }

    public final aCarouselTitle getTitleSetting() {
        return titleSetting;
    }

    public final void setTitleSetting(final aCarouselTitle titleSetting) {
        this.titleSetting = titleSetting;
    }

    public final aCarouselTitle getTitleLibrary() {
        return titleLibrary;
    }

    public final void setTitleLibrary(final aCarouselTitle titleLibrary) {
        this.titleLibrary = titleLibrary;
    }

    public final aCarouselTitle getTitleAuroraNet() {
        return titleAuroraNet;
    }

    public final void setTitleAuroraNet(final aCarouselTitle titleAuroraNet) {
        this.titleAuroraNet = titleAuroraNet;
    }

    public final aCarouselPane getLibraryPane() {
        return paneLibrary;
    }

    public final void setLibraryPane(final aCarouselPane libraryPane) {
        this.paneLibrary = libraryPane;
    }

    public final aCarouselPane getSettingsPane() {
        return paneSettings;
    }

    public final void setSettingsPane(final aCarouselPane settingsPane) {
        this.paneSettings = settingsPane;
    }

    public final aCarouselPane getProfilePane() {
        return paneProfile;
    }

    public final void setProfilePane(final aCarouselPane profilePane) {
        this.paneProfile = profilePane;
    }

    public final aCarouselPane getAuroraNetPane() {
        return paneNet;
    }

    public final void setAuroraNetPane(final aCarouselPane auroraNetPane) {
        this.paneNet = auroraNetPane;
    }

    public final StartLoader getLoader() {
        return loader;
    }

    public final void setLoader(final StartLoader loader) {
        this.loader = loader;
    }

    public final int getTopHeight() {
        return topHeight;
    }

    public final void setTopHeight(final int topHeight) {
        this.topHeight = topHeight;
    }

    public final int getImageWidth() {
        return logoWidth;
    }

    public final void setImageWidth(final int imageWidth) {
        this.logoWidth = imageWidth;
    }

    public final int getCarouselButtonWidth() {
        return carouselButtonWidth;
    }

    public final void setCarouselButtonWidth(final int carouselButtonWidth) {
        this.carouselButtonWidth = carouselButtonWidth;
    }

    public final int getCarouselButtonHeight() {
        return carouselButtonHeight;
    }

    public final void setCarouselButtonHeight(final int carouselButtonHeight) {
        this.carouselButtonHeight = carouselButtonHeight;
    }

    public final aCarousel getCarousel() {
        return carousel;
    }

    public final aurora.V1.core.Game getGame() {
        return randomGame;
    }

    public final aButton getBtnLogout() {
        return btnBack;
    }

    public final AuroraStorage getStorage() {
        return storage;
    }

    public final AuroraCoreUI getUi() {
        return coreUI;
    }

    public final aButton getBtnCarouselLeft() {
        return btnCarouselLeft;
    }

    public final aButton getBtnCarouselRight() {
        return btnCarouselRight;
    }
    //------------------------------|||-----------------------------------//
}
