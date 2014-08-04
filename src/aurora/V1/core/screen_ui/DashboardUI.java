/*
 *  Made By Sardonix Creative.
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

import aurora.V1.core.AboutBox;
import aurora.V1.core.AuroraCoreUI;
import aurora.V1.core.AuroraStorage;
import aurora.V1.core.screen_handler.DashboardHandler;
import aurora.V1.core.screen_logic.DashboardLogic;
import aurora.V1.core.screen_logic.SettingsLogic;
import aurora.engine.V1.Logic.AThreadWorker;
import aurora.engine.V1.Logic.AuroraScreenUI;
import aurora.engine.V1.UI.AButton;
import aurora.engine.V1.UI.ACarousel;
import aurora.engine.V1.UI.ACarouselPane;
import aurora.engine.V1.UI.ACarouselTitle;
import aurora.engine.V1.UI.ACarouselTitle.TitleType;
import aurora.engine.V1.UI.AImage;
import aurora.engine.V1.UI.AImagePane;
import aurora.engine.V1.UI.AMarqueePanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import org.apache.log4j.Logger;

/**
 * .------------------------------------------------------------------------. |
 * DashboardUI :: Aurora App Class
 * .------------------------------------------------------------------------.
 * |
 * | This class contains the UI for the Dashboard Screen associated with an
 * | appropriate *Handler* and *Logic* class which handle the actions caused
 * | by the UI components found here
 * |
 * | This class must follow the rules stated in the AuroraScreenUI
 * | Interface found in the Aurora Engine. The *Handler* and *Logic* classes
 * | The Handler class is called: DashboardHandler
 * | The Logic class is called: DashboardLogic
 * |
 * .........................................................................
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 * @author Carlos Machado <camachado@gmail.com>
 *
 */
public class DashboardUI implements AuroraScreenUI {

    /**
     * The main carousel used to navigate AuroraApps.
     */
    private ACarousel carousel;

    /**
     * Button to make carousel move left.
     */
    private AButton btnCarouselLeft;

    /**
     * Button to make carousel move right.
     */
    private AButton btnCarouselRight;

    /**
     * Image with Glow state of the Profile TitleType.
     */
    private AImage titleProfileGlow;

    /**
     * Image with Normal state of the Profile TitleType.
     */
    private AImage titleProfileNorm;

    /**
     * Image with Glow state of the Settings TitleType.
     */
    private AImage titleSettingGlow;

    /**
     * Image with Normal state of the Settings TitleType.
     */
    private AImage titleSettingNorm;

    /**
     * Image with Glow state of the Library TitleType.
     */
    private AImage titleLibraryGlow;

    /**
     * Image with Normal state of the Library TitleType.
     */
    private AImage titleLibraryNorm;

    /**
     * Image with Glow state of the AuroraNet TitleType.
     */
    private AImage titleAuroraNetGlow;

    /**
     * Image with Normal state of the AuroraNet TitleType.
     */
    private AImage titleAuroraNetNorm;

    /**
     * TitleType component containing both Glow and Normal state of Profile
     * item.
     */
    private ACarouselTitle titleProfile;

    /**
     * TitleType component containing both Glow and Normal state of Settings
     * item.
     */
    private ACarouselTitle titleSetting;

    /**
     * TitleType component containing both Glow and Normal state of Library
     * item.
     */
    private ACarouselTitle titleLibrary;

    /**
     * TitleType component containing both Glow and Normal state of AuroraNet
     * item.
     */
    private ACarouselTitle titleAuroraNet;

    /**
     * Panel Containing TitleType and Icon representing the Library in Carousel.
     */
    private ACarouselPane paneLibrary;

    /**
     * Panel Containing TitleType and Icon representing the Library in Settings.
     */
    private ACarouselPane paneSettings;

    /**
     * Panel Containing TitleType and Icon representing the Library in Profile.
     */
    private ACarouselPane paneProfile;

    /**
     * Panel Containing TitleType and Icon representing the Library in
     * AuroraNet.
     */
    private ACarouselPane paneNet;

    /**
     * Image of Icon representing AuroraNet Carousel pane.
     */
    private AImage icoNet;

    /**
     * Image of Icon representing Profile Carousel pane.
     */
    private AImage icoProfile;

    /**
     * Image of Icon representing Settings Carousel pane.
     */
    private AImage icoSetting;

    /**
     * Image of Icon representing Library Carousel pane.
     */
    private AImagePane icoLibrary;

    /**
     * Image of Keyboard Arrows indicating ability to use keyboard to navigate.
     */
    private AImage keyArrows;

    /**
     * Label describing what the Keyboard Icon will do.
     */
    private JLabel lblKeyActionArrow;

    /**
     * the InfoFeed which displays news etc. at the bottom of the Dashboard
     */
    private AMarqueePanel infoFeed;

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
     * This is the Local Storage Instance.
     */
    private AuroraStorage storage;

    /**
     * This is the Previous Screen, which contains the Local Storage Instance.
     */
    private final WelcomeUI startUI;

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
    private final DashboardLogic dashboardLogic;

    /**
     * Boolean to see when the loadedUI() method is completed.
     */
    private boolean dashboardUiLoaded;

    private JPanel infoFeedFill;

    private AboutBox aboutBox;

    private ArrayList<JLabel> infoFeedLabelList;

    static final Logger logger = Logger.getLogger(DashboardUI.class);
    private JPanel infoFeedContainer;

    /**
     * .-----------------------------------------------------------------------.
     * | DashboardUI(AuroraCoreUI, WelcomeUI)
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
     * @param startScreenUi WelcomeUI
     *
     */
    public DashboardUI(final AuroraCoreUI auroraCoreUi,
                       final WelcomeUI startScreenUi) {
        // Other core objects
        this.startUI = startScreenUi;
        this.storage = startUI.getAuroraStorage();

        // Core UI Canvas
        this.coreUI = auroraCoreUi;


        // The Dashboard Handler + Logic
        this.handler = new DashboardHandler(this);
        this.dashboardLogic = new DashboardLogic(this);
        handler.setLogic(dashboardLogic);
        dashboardLogic.setHandler(handler);
    }

    @Override
    public final void loadUI() {

        //Initialize Sizes
        setSizes();

        // Initialize Carousel
        // --------------------------------------------------------------------.

        titleSettingGlow = new AImage("dash_carousel_settings_glow.png");
        titleSettingNorm = new AImage("dash_carousel_settings_norm.png");

        titleLibraryGlow = new AImage("dash_carousel_library_glow.png");
        titleLibraryNorm = new AImage("dash_carousel_library_norm.png");

        titleProfileGlow = new AImage("dash_carousel_profile_glow.png");
        titleProfileNorm = new AImage("dash_carousel_profile_norm.png");

        titleAuroraNetGlow = new AImage("auroranet_glow.png");
        titleAuroraNetNorm = new AImage("auroranet_normal.png");

        icoProfile = new AImage("dash_carousel_profileIcon.png");
        icoSetting = new AImage("dash_carousel_settingsIcon.png");
        icoNet = new AImage("ComingSoon.png");
        icoLibrary = dashboardLogic.getLibraryIcon();
        icoLibrary.setPreferredSize(new Dimension(gameCoverWidth,
                                                  gameCoverHeight));

        titleProfile = new ACarouselTitle(titleProfileNorm, titleProfileGlow);
        titleSetting = new ACarouselTitle(titleSettingNorm, titleSettingGlow);
        titleLibrary = new ACarouselTitle(titleLibraryNorm, titleLibraryGlow);
        titleAuroraNet = new ACarouselTitle(titleAuroraNetNorm,
                                            titleAuroraNetGlow);

        carousel = new ACarousel(carouselWidth, carouselHeight,
                                 Toolkit.getDefaultToolkit().getScreenSize().width);
        carousel.setVisible(false);

        paneSettings = new ACarouselPane("dash_carousel_bg.png",
                                         (int) carouselWidth + 25,
                                         carouselHeight - 25, true, titleSetting,
                                         "Setting Pane");
        paneSettings.setVisible(false);

        paneProfile = new ACarouselPane("dash_carousel_bg.png",
                                        (int) carouselWidth + 25,
                                        carouselHeight - 25, true, titleProfile,
                                        "Profile pane");
        paneProfile.setVisible(false);

        paneLibrary = new ACarouselPane("dash_carousel_bg.png",
                                        (int) carouselWidth + 25,
                                        carouselHeight - 25, true, titleLibrary,
                                        "library pane");
        paneLibrary.setVisible(false);

        paneNet = new ACarouselPane("dash_carousel_bg.png", (int) carouselWidth
                                                            + 25,
                                    carouselHeight - 25, true, titleAuroraNet,
                                    "auroranet");
        paneNet.setVisible(false);

        btnCarouselLeft = new AButton("dash_btn_carouselLeft_norm.png",
                                      "dash_btn_carouselLeft_down.png",
                                      "dash_btn_carouselLeft_over.png",
                                      carouselButtonWidth, carouselButtonHeight);
        btnCarouselLeft.setVisible(false);

        btnCarouselRight = new AButton("dash_btn_carouselRight_norm.png",
                                       "dash_btn_carouselRight_down.png",
                                       "dash_btn_carouselRight_over.png",
                                       carouselButtonWidth, carouselButtonHeight);
        btnCarouselRight.setVisible(false);



        // Setup Carousel
        // --------------------------------------------------------------------.


        // Set ID For each Panel and add ENTER Key Listener
        paneSettings.setName("settingsPane");
        paneProfile.setName("profilePane");
        paneLibrary.setName("libraryPane");
        paneNet.setName("auroraNetPane");


        // Set ID For each Panel and add ENTER Key Listener
        paneSettings.addContent(icoSetting, TitleType.NORMAL);
        paneProfile.addContent(icoProfile, TitleType.NORMAL);
        paneNet.addContent(icoNet, TitleType.NORMAL);
        // Initially set to Glow state
        paneLibrary.addContent(icoLibrary, TitleType.GLOW);


        // Add each Pane to the Carousel

        carousel.addPane(paneSettings);
        carousel.addPane(paneLibrary);
        carousel.addPane(paneProfile);
        carousel.addPane(paneNet);

        if (storage.getStoredSettings().getSettingValue(SettingsLogic.WASD_NAV_SETTING).equalsIgnoreCase("enabled")) {
            carousel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                    .put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "Carousel_D");
            carousel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                    .put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "Carousel_A");
        }

        carousel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "Carousel_LEFT");
        carousel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "Carousel_RIGHT");
        carousel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Carousel_ENTER");
        carousel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Carousel_ESCAPE");


        carousel.getActionMap()
                .put("Carousel_LEFT", handler.new DashboardlKeyListener(KeyEvent.VK_LEFT));
        carousel.getActionMap()
                .put("Carousel_RIGHT", handler.new DashboardlKeyListener(KeyEvent.VK_RIGHT));
        carousel.getActionMap()
                .put("Carousel_D", handler.new DashboardlKeyListener(KeyEvent.VK_D));
        carousel.getActionMap()
                .put("Carousel_A", handler.new DashboardlKeyListener(KeyEvent.VK_A));
        carousel.getActionMap()
                .put("Carousel_ENTER", handler.new DashboardlKeyListener(KeyEvent.VK_ENTER));
        carousel.getActionMap()
                .put("Carousel_ESCAPE", handler.new DashboardlKeyListener(KeyEvent.VK_ESCAPE));





        // Handel mouse click

        icoSetting.addMouseListener(handler.new CarouselPaneMouseListener(
                paneSettings));
        icoProfile.addMouseListener(handler.new CarouselPaneMouseListener(
                paneProfile));
        icoNet.addMouseListener(handler.new CarouselPaneMouseListener(paneNet));

        icoLibrary.addMouseListener(handler.new CarouselPaneMouseListener(
                paneLibrary));


        paneProfile
                .addMouseListener(handler.new CarouselPaneMouseListener(null));
        paneSettings.addMouseListener(
                handler.new CarouselPaneMouseListener(null));
        paneLibrary
                .addMouseListener(handler.new CarouselPaneMouseListener(null));
        paneNet.addMouseListener(handler.new CarouselPaneMouseListener(null));




        // Info Feed
        // --------------------------------------------------------------------.

        infoFeedFill = new JPanel(new BorderLayout());
        infoFeedFill.setOpaque(false);



        // Marquee Panel Text
        // --------------------------------------------------------------------.
        infoFeed = new AMarqueePanel(infoFeedWidth, infoFeedHeight,
                                     new Color(38, 46, 60), new Color(16, 18, 29));
        infoFeed.setPreferredSize(new Dimension(
                infoFeedWidth,
                infoFeedHeight));
        infoFeed.setVisible(false);
        infoFeedFill.setPreferredSize(new Dimension(infoFeedWidth,
                                                    infoFeedHeight));

        infoFeedFill.add(Box.createVerticalStrut(btnCarouselLeft.getPreferredSize().height / 2
                                                 - infoFeedHeight / 2), BorderLayout.NORTH);

        infoFeedContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        infoFeedContainer.setOpaque(false);
        infoFeedContainer.setPreferredSize(new Dimension(infoFeedWidth,
                                                         infoFeedHeight));
        infoFeedContainer.add(infoFeed);



        AThreadWorker work = new AThreadWorker(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                infoFeedLabelList = dashboardLogic.createRssFeed();
                dashboardLogic.loadInfoFeed(infoFeed, infoFeedLabelList);


                infoFeed.setPostCycleListener(new ActionListener() {
                    @Override
                    public void actionPerformed(final ActionEvent e) {

                        if (logger.isDebugEnabled()) {
                            logger.debug("Refreshing feed");
                        }

                        infoFeed.removeAll();
                        infoFeedLabelList = dashboardLogic.refreshRssFeed(
                                infoFeedLabelList);
                        dashboardLogic.loadInfoFeed(infoFeed, infoFeedLabelList);
                        infoFeed.startScrolling();


                    }
                });

                infoFeed.startScrolling();

                infoFeed.setPreferredSize(new Dimension(
                        infoFeed.getPreferredSize().width,
                        infoFeedHeight));


                infoFeedFill.revalidate();
                infoFeed.repaint();

            }
        });

        work.startOnce();


        // About Box
        aboutBox = new AboutBox(coreUI);
        coreUI.getLogoImage().addMouseListener(new HeaderMouseListener());
        aboutBox.buildAboutUI();

        // Finalize
        // --------------------------------------------------------------------.

        keyArrows = new AImage("KeyboardKeys/arrows.png", coreUI.
                               getKeyIconWidth(), coreUI.getKeyIconHeight());

        lblKeyActionArrow = new JLabel();

        if (logger.isDebugEnabled()) {
            logger.debug("DashboardUI loaded");
        }
        setAllToVisible();
        dashboardUiLoaded = true;

    }

    private void setAllToVisible() {
        infoFeed.setVisible(true);
        infoFeed.repaint();

        try {
            Thread.sleep(5);
        } catch (InterruptedException ex) {
            logger.error(ex);
        }
        btnCarouselRight.setVisible(true);
        btnCarouselLeft.setVisible(true);
        try {
            Thread.sleep(5);
        } catch (InterruptedException ex) {
            logger.error(ex);
        }
        carousel.setVisible(true);
        try {
            Thread.sleep(5);
        } catch (InterruptedException ex) {
            logger.error(ex);
        }
        paneLibrary.setVisible(true);
        try {
            Thread.sleep(5);
        } catch (InterruptedException ex) {
            logger.error(ex);
        }
        paneNet.setVisible(true);
        try {
            Thread.sleep(5);
        } catch (InterruptedException ex) {
            logger.error(ex);
        }
        paneProfile.setVisible(true);
        try {
            Thread.sleep(5);
        } catch (InterruptedException ex) {
            logger.error(ex);
        }
        paneSettings.setVisible(true);

    }

    @Override
    public final void buildUI() {

        // Indicate to User DashboardUI is loading.
        coreUI.getTitleLabel().setText("  " + ".: Loading :.");


        // Set bigger Logo to Header
        coreUI.getLogoImage().setImgURl("dash_header_logo.png");
        coreUI.getLogoImage().setImageSize(logoWidth, logoHeight);

        // Add UI to Canvas
        addToCanvas();



    }

    @Override
    public final void addToCanvas() {


        // CoreUI
        // --------------------------------------------------------------------.


        // Set size of Bottom panel in CoreUI
        coreUI.getBottomPane().setPreferredSize(new Dimension(coreUI
                .getBottomPane().getWidth(), bottomPaneHeightAdjust));
        coreUI.getBottomPane().setImageHeight(bottomPaneHeightAdjust);

        // Set size of Top panel in CoreUI
        coreUI.getTopPane().setImageHeight(topHeight);
        coreUI.getTopPane().setPreferredSize(new Dimension(coreUI
                .getTopPane().
                getWidth(), topHeight + coreUI.getFrameControlContainerPanel()
                                                           .getHeight()));

        // Set size of Top Panels
        coreUI.getSouthFromTopPanel().revalidate();
        coreUI.getSouthFromTopPanel().setPreferredSize(new Dimension(coreUI.
                getSouthFromTopPanel().getWidth(), coreUI
                                                                     .getFrameControlContainerPanel()
                                                                     .getHeight()));
        coreUI.getTopPane().setPreferredSize(new Dimension(coreUI
                .getTopPane().
                getWidth(), coreUI.getTopPane().getImageHeight()
                            + coreUI.getFrameControlContainerPanel()
                                                           .getHeight()));
        coreUI.getSouthFromTopPanel().revalidate();

        // Set Font of Keyboard Action Label
        lblKeyActionArrow.setFont(coreUI.getDefaultFont().deriveFont(Font.PLAIN,
                                                                     coreUI
                                                                     .getKeysFontSize()));
        lblKeyActionArrow.setForeground(new Color(0, 178, 178));
        lblKeyActionArrow.setText(" Move ");

        coreUI.getLblKeyActionEnter().setText(" Launch ");




        // Add  Components to CoreUI

        // Add Arrow Keys Icons
        coreUI.getKeyToPressPanel().add(keyArrows);
        coreUI.getKeyToPressPanel().add(lblKeyActionArrow);

        // Add Enter Key Icons
        coreUI.getKeyToPressPanel().add(coreUI.getKeyIconImage());
        coreUI.getKeyToPressPanel().add(coreUI.getKeyActionLabel());

        // Add Carousel to Center Panel
        coreUI.getCenterPanel().add(BorderLayout.CENTER, carousel);

        infoFeed.setPreferredSize(new Dimension(
                infoFeedWidth,
                infoFeedHeight));
        infoFeedContainer.setPreferredSize(new Dimension(infoFeedWidth,
                                                         infoFeedHeight));
        infoFeedFill.add(infoFeedContainer, BorderLayout.CENTER);


        // Add To Bottom Panel  InfoFeed and both Carousel Buttons*//
        coreUI.getCenterFromBottomPanel().add(BorderLayout.EAST,
                                              btnCarouselRight);
        coreUI.getCenterFromBottomPanel().add(BorderLayout.CENTER,
                                              infoFeedFill);
        coreUI.getCenterFromBottomPanel()
                .add(BorderLayout.WEST, btnCarouselLeft);

        // Handlers

        coreUI.getInputController().clearAllListeners();

        coreUI.getInputController().setListener_A_Button(handler.new DashboardlKeyListener(KeyEvent.VK_ENTER));
        coreUI.getInputController().setListener_B_Button(handler.new DashboardlKeyListener(KeyEvent.VK_ESCAPE));

        coreUI.getInputController().setListener_RB_Button(handler.new DashboardlKeyListener(KeyEvent.VK_RIGHT));
        coreUI.getInputController().setListener_LB_Button(handler.new DashboardlKeyListener(KeyEvent.VK_LEFT));

        coreUI.getInputController().setListener_HAT_Left_Button(handler.new DashboardlKeyListener(KeyEvent.VK_LEFT));
        coreUI.getInputController().setListener_HAT_Right_Button(handler.new DashboardlKeyListener(KeyEvent.VK_RIGHT));

        coreUI.getInputController().setListener_ANALOG_Left_Button(handler.new DashboardlKeyListener(KeyEvent.VK_LEFT));
        coreUI.getInputController().setListener_ANALOG_Right_Button(handler.new DashboardlKeyListener(KeyEvent.VK_RIGHT));

        // Check for Mouse Wheel Rotation
        carousel.
                addMouseWheelListener(
                        handler.new CarouselPaneMouseWheelListener());


        // Add Listeners to the Left and Right Carousel Buttons
        btnCarouselLeft.addActionListener(handler.new LeftButtonListener());

        btnCarouselRight.addActionListener(handler.new RightButtonListener());


        // CoreUI Listeners

        // Remove ENTER KeyListener attached to frame.
        coreUI.getFrame().removeKeyListener(startUI.getStartKeyHandler());


        // Finished loading so change text
        coreUI.getTitleLabel().setText("  " + " Dashboard ");



        // Final Refresh and Refocus
        coreUI.getFrame().repaint();
        coreUI.getFrame().requestFocus();


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


        if (coreUI.isLargeScreen()) {
            topHeight = coreUI.getCenterPanel().getHeight() / 8;

            carouselWidth = coreUI.getFrame().getWidth() / 42 * 16;
            carouselHeight = coreUI.getFrame().getHeight() - (coreUI.
                    getFrame().getWidth() / 6);
            gameCoverHeight = carouselHeight - (2 * carouselHeight / 5);
            gameCoverWidth = (int) carouselWidth - (int) (carouselWidth / 4);
            carouselImageWidth = carouselHeight - (2 * carouselHeight / 6);
            carouselImageHeight = (int) carouselWidth
                                  - (int) (carouselWidth / 4) - 20;
            logoHeight = topHeight / 2 + 20;
            logoWidth = coreUI.getFrame().getWidth() / 2 + 20;

            bottomPaneHeightAdjust = coreUI.getScreenHeight() / 9 + coreUI.
                    getFrame().getHeight() / 50 + 60;
            topPaneHeighAdjust = coreUI.getCenterPanel().getHeight() / 5;

            carouselButtonWidth = coreUI.getFrame().getWidth() / 12;
            carouselButtonHeight = coreUI.getFrame().getHeight() / 15;
            infoFeedWidth = coreUI.getFrame().getSize().width
                            - (carouselButtonWidth * 2) - 70;
            infoFeedHeight = 55;


        } else {
            topHeight = coreUI.getCenterPanel().getHeight() / 8;
            btnBackWidth = 30;
            btnBackHeight = 35;

            carouselWidth = (coreUI.getFrame().getWidth() / 40 + topHeight / 55)
                            * 16;
            carouselHeight = coreUI.getFrame().getHeight() - (coreUI.
                    getFrame().getWidth() / 7);

            gameCoverHeight = carouselHeight - (2 * carouselHeight / 5);
            gameCoverWidth = (int) carouselWidth - (int) (carouselWidth / 3);

            carouselImageWidth = (int) carouselWidth - 400 / 2;
            carouselImageHeight = carouselHeight - (450 / 2)
                                  - 55;
            logoHeight = topHeight / 2 + 20;
            logoWidth = coreUI.getFrame().getWidth() / 2 + 20;

            bottomPaneHeightAdjust = coreUI.getBottomPanelSize() / 2 + coreUI.
                    getFrame().getHeight() / 90 + 35;
            topPaneHeighAdjust = coreUI.getCenterPanel().getHeight() / 5;

            carouselButtonWidth = coreUI.getFrame().getWidth() / 12;
            carouselButtonHeight = coreUI.getFrame().getHeight() / 15;
            infoFeedWidth = coreUI.getFrame().getSize().width
                            - (carouselButtonWidth * 2) - 70;
            infoFeedHeight = carouselButtonHeight
                             - (bottomPaneHeightAdjust / 18);


            if (logger.isDebugEnabled()) {
                logger.debug("INFO FEED WIDTH = " + infoFeedWidth);
                logger.debug("INFO FEED HEIGHT = " + infoFeedHeight);
                logger.debug("WIDTH " + carouselWidth);
            }

        }

    }


    /**
     * .-----------------------------------------------------------------------.
     * | HeaderMouseListener()
     * .-----------------------------------------------------------------------.
     * |
     * | This is a MouseListener for when you click on the Header icon
     * | this is used to open the About box
     * |
     */
    private class HeaderMouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(final MouseEvent e) {
            aboutBox.showAboutBox();
        }

        @Override
        public void mousePressed(final MouseEvent e) {
            aboutBox.showAboutBox();
        }

        @Override
        public void mouseEntered(final MouseEvent e) {
            coreUI.getLogoImage().setImgURl("dash_header_logo_hover.png");
            coreUI.getLogoImage().setImageSize(logoWidth, logoHeight);
        }

        @Override
        public void mouseExited(final MouseEvent e) {
            coreUI.getLogoImage().setImgURl("dash_header_logo.png");
            coreUI.getLogoImage().setImageSize(logoWidth, logoHeight);
        }
    }

    // Getters & Setters
    // -----------------------------------------------------------------------.
    /**
     * Get the DashboardLogic instance generated in DashboardUI.
     * <p/>
     * @return DashboardLogic
     */
    public final DashboardLogic getDashboardLogic() {
        return dashboardLogic;
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
     * Get the AuroraCoreUI generated by WelcomeUI.
     * <p/>
     * @return AuroraCoreUI
     */
    public final AuroraCoreUI getCoreUI() {
        return this.coreUI;
    }

    /**
     * Get the AuroraStorage instance generated by WelcomeUI.
     * <p/>
     * @return AuroraStorage
     */
    public final AuroraStorage getStorage() {
        return storage;
    }

    /**
     * Get the DashboardLogic instance generated in DashboardUI.
     * <p/>
     * @return WelcomeUI
     */
    public final WelcomeUI getStartUI() {
        return startUI;
    }

    /**
     * The Glow State of the Profile Pane title.
     * <p/>
     * @return AImage
     */
    public final AImage getTitleProfileGlow() {
        return titleProfileGlow;
    }

    /**
     * Set the Normal state of the Profile Pane title.
     * <p/>
     * @param aTitleProfileGlow AImage
     */
    public final void setTitleProfileGlow(final AImage aTitleProfileGlow) {
        this.titleProfileGlow = aTitleProfileGlow;
    }

    /**
     * The Normal State of the Profile Pane title.
     * <p/>
     * @return AImage
     */
    public final AImage getTitleimgProfileNorm() {
        return titleProfileNorm;
    }

    /**
     * Set the Normal state of the Profile Pane title.
     * <p/>
     * @param aTitleimgProfileNorm AImage
     */
    public final void setTitleimgProfileNorm(final AImage aTitleimgProfileNorm) {
        this.titleProfileNorm = aTitleimgProfileNorm;
    }

    /**
     * The Glow State of the Settings Pane title.
     * <p/>
     * @return AImage
     */
    public final AImage getTitleSettingGlow() {
        return titleSettingGlow;
    }

    /**
     * Set the Glow state of the Settings Pane title.
     * <p/>
     * @param aTitleSettingGlow AImage
     */
    public final void setTitleSettingGlow(final AImage aTitleSettingGlow) {
        this.titleSettingGlow = aTitleSettingGlow;
    }

    /**
     * The Normal State of the Settings Pane title.
     * <p/>
     * @return AImage
     */
    public final AImage getTitleSettingNorm() {
        return titleSettingNorm;
    }

    /**
     * Set the Normal state of the Settings Pane title.
     * <p/>
     * @param aTitleSettingNorm AImage
     */
    public final void setTitleSettingNorm(final AImage aTitleSettingNorm) {
        this.titleSettingNorm = aTitleSettingNorm;
    }

    /**
     * The Glow State of the Library Pane title.
     * <p/>
     * @return AImage
     */
    public final AImage getTitleLibraryGlow() {
        return titleLibraryGlow;
    }

    /**
     * Set the Glow state of the Library Pane title.
     * <p/>
     * @param aTitleLibraryGlow AImage
     */
    public final void setTitleLibraryGlow(final AImage aTitleLibraryGlow) {
        this.titleLibraryGlow = aTitleLibraryGlow;
    }

    /**
     * The Normal State of the Library Pane title.
     * <p/>
     * @return AImage
     */
    public final AImage getTitleLibraryNorm() {
        return titleLibraryNorm;
    }

    /**
     * Set the Normal state of the Library Pane title.
     * <p/>
     * @param aTitleLibraryNorm AImage
     */
    public final void setTitleLibraryNorm(final AImage aTitleLibraryNorm) {
        this.titleLibraryNorm = aTitleLibraryNorm;
    }

    /**
     * The Glow State of the AuroraNet Pane title.
     * <p/>
     * @return AImage
     */
    public final AImage getTitleAuroraNetGlow() {
        return titleAuroraNetGlow;
    }

    /**
     * Set the Glow state of the AuroraNet Pane title.
     * <p/>
     * @param aTitleAuroraNetGlow AImage
     */
    public final void setTitleAuroraNetGlow(final AImage aTitleAuroraNetGlow) {
        this.titleAuroraNetGlow = aTitleAuroraNetGlow;
    }

    /**
     * The Normal State of the AuroraNet Pane title.
     * <p/>
     * @return AImage
     */
    public final AImage getTitleAuroraNetNorm() {
        return titleAuroraNetNorm;
    }

    /**
     * Set the Normal state of the AuroraNet Pane title.
     * <p/>
     * @param aTitleAuroraNetNorm AImage
     */
    public final void setTitleAuroraNetNorm(final AImage aTitleAuroraNetNorm) {
        this.titleAuroraNetNorm = aTitleAuroraNetNorm;
    }

    /**
     * Get the Icon for the Profile Pane.
     * <p/>
     * @return AImage
     */
    public final AImage getImgProfile() {
        return icoProfile;
    }

    /**
     * Set the Icon for the Profile Pane.
     * <p/>
     * @param aImgProfile AImage
     */
    public final void setImgProfile(final AImage aImgProfile) {
        this.icoProfile = aImgProfile;
    }

    /**
     * Get the Icon for the Settings Pane.
     * <p/>
     * @return AImage
     */
    public final AImage getImgSetting() {
        return icoSetting;
    }

    /**
     * Set the Icon for the Settings Pane.
     * <p/>
     * @param aImgSetting AImage
     */
    public final void setImgSetting(final AImage aImgSetting) {
        this.icoSetting = aImgSetting;
    }

    /**
     * Get the Icon for the Library Pane.
     * <p/>
     * @return AImage
     */
    public final AImagePane getImgLibrary() {
        return icoLibrary;
    }

    /**
     * Set the Icon for the Library Pane.
     * <p/>
     * @param aImgLibrary AImage
     */
    public final void setImgLibrary(final AImagePane aImgLibrary) {
        this.icoLibrary = aImgLibrary;
    }

    /**
     * Get the Icon for the AuroraNet Pane.
     * <p/>
     * @return AImage
     */
    public final AImage getImgNet() {
        return icoNet;
    }

    /**
     * Set the Icon for the AuroraNet Pane.
     * <p/>
     * @param aImgNet AImage
     */
    public final void setImgNet(final AImage aImgNet) {
        this.icoNet = aImgNet;
    }

    /**
     * Get the Image representing the Key to press for an action.
     * <p/>
     * @return AImage
     */
    public final AImage getImgKeyIco() {
        return keyArrows;
    }

    /**
     * Set the Image representing the Key to press for an action.
     * <p/>
     * @param aImgKeyIco AImage
     */
    public final void setImgKeyIco(final AImage aImgKeyIco) {
        this.keyArrows = aImgKeyIco;
    }

    /**
     * Get the Label describing the action occurring when pressing a key.
     * <p/>
     * @return JLabel
     */
    public final JLabel getLblKeyActionArrow() {
        return lblKeyActionArrow;
    }

    /**
     * Set the Label describing the action occurring when pressing a key.
     * <p/>
     * @param aLblKeyAction JLabel
     */
    public final void setLblKeyAction(final JLabel aLblKeyAction) {
        this.lblKeyActionArrow = aLblKeyAction;
    }

    /**
     * Get the InfoFeed component instanced in DashboardUI.
     * <p/>
     * @return AInfoFeed
     */
    public final AMarqueePanel getInfoFeed() {
        return infoFeed;
    }

    /**
     * Get the InfoFeed container instanced in DashboardUI.
     * <p/>
     * @return JPanel
     */
    public final JPanel getInfoFeedContainer() {
        return infoFeedContainer;
    }

    /**
     * Get CarouselTitle instance of the Profile Pane containing both the Glow
     * and Normal state of the TitleType.
     * <p/>
     * @return ACarouselTitle
     */
    public final ACarouselTitle getTitleProfile() {
        return titleProfile;
    }

    /**
     * Set the CarouselTitle instance of the Profile Pane containing both the
     * Glow and Normal state of the TitleType.
     * <p/>
     * @param aTitleProfile ACarouselTitle
     */
    public final void setTitleProfile(final ACarouselTitle aTitleProfile) {
        this.titleProfile = aTitleProfile;
    }

    /**
     * Get CarouselTitle instance of the Setting Pane containing both the Glow
     * and Normal state of the TitleType.
     * <p/>
     * @return ACarouselTitle
     */
    public final ACarouselTitle getTitleSetting() {
        return titleSetting;
    }

    /**
     * Set the CarouselTitle instance of the Setting Pane containing both the
     * Glow and Normal state of the TitleType.
     * <p/>
     * @param aTitleSetting ACarouselTitle
     */
    public final void setTitleSetting(final ACarouselTitle aTitleSetting) {
        this.titleSetting = aTitleSetting;
    }

    /**
     * Get CarouselTitle instance of the Library Pane containing both the Glow
     * and Normal state of the TitleType.
     * <p/>
     * @return ACarouselTitle
     */
    public final ACarouselTitle getTitleLibrary() {
        return titleLibrary;
    }

    /**
     * Set the CarouselTitle instance of the Library Pane containing both the
     * Glow and Normal state of the TitleType.
     * <p/>
     * @param aTitleLibrary ACarouselTitle
     */
    public final void setTitleLibrary(final ACarouselTitle aTitleLibrary) {
        this.titleLibrary = aTitleLibrary;
    }

    /**
     * Get CarouselTitle instance of the AuroraNet Pane containing both the Glow
     * and Normal state of the TitleType.
     * <p/>
     * @return ACarouselTitle
     */
    public final ACarouselTitle getTitleAuroraNet() {
        return titleAuroraNet;
    }

    /**
     * Set the CarouselTitle instance of the AuroraNet Pane containing both the
     * Glow and Normal state of the TitleType.
     * <p/>
     * @param aTitleAuroraNet ACarouselTitle
     */
    public final void setTitleAuroraNet(final ACarouselTitle aTitleAuroraNet) {
        this.titleAuroraNet = aTitleAuroraNet;
    }

    /**
     * Get CarouselPane instance of the Library Carousel Pane containing both
     * the TitleType and the Icon of the actual Pane.
     * <p/>
     * @return ACarouselPane
     */
    public final ACarouselPane getLibraryPane() {
        return paneLibrary;
    }

    /**
     * Set CarouselPane instance of the Library Carousel Pane containing both
     * the TitleType and the Icon of the actual Pane.
     * <p/>
     * @param alibraryPane ACarouselPane
     */
    public final void setLibraryPane(final ACarouselPane alibraryPane) {
        this.paneLibrary = alibraryPane;
    }

    /**
     * Get CarouselPane instance of the Settings Carousel Pane containing both
     * the TitleType and the Icon of the actual Pane.
     * <p/>
     * @return ACarouselPane
     */
    public final ACarouselPane getSettingsPane() {
        return paneSettings;
    }

    /**
     * Set CarouselPane instance of the Settings Carousel Pane containing both
     * the TitleType and the Icon of the actual Pane.
     * <p/>
     * @param aSettingsPane ACarouselPane
     */
    public final void setSettingsPane(final ACarouselPane aSettingsPane) {
        this.paneSettings = aSettingsPane;
    }

    /**
     * Get CarouselPane instance of the Profile Carousel Pane containing both
     * the TitleType and the Icon of the actual Pane.
     * <p/>
     * @return ACarouselPane
     */
    public final ACarouselPane getProfilePane() {
        return paneProfile;
    }

    /**
     * Set CarouselPane instance of the Profile Carousel Pane containing both
     * the TitleType and the Icon of the actual Pane.
     * <p/>
     * @param aProfilePane ACarouselPane
     */
    public final void setProfilePane(final ACarouselPane aProfilePane) {
        this.paneProfile = aProfilePane;
    }

    /**
     * Get CarouselPane instance of the AuroraNet Carousel Pane containing both
     * the TitleType and the Icon of the actual Pane.
     * <p/>
     * @return ACarouselPane
     */
    public final ACarouselPane getAuroraNetPane() {
        return paneNet;
    }

    /**
     * Set CarouselPane instance of the AuroraNet Carousel Pane containing both
     * the TitleType and the Icon of the actual Pane.
     * <p/>
     * @param aAuroraNetPane ACarouselPane
     */
    public final void setAuroraNetPane(final ACarouselPane aAuroraNetPane) {
        this.paneNet = aAuroraNetPane;
    }

    /**
     * Get ACarousel instance from the DashboardUI.
     * <p/>
     * @return ACarousel
     */
    public final ACarousel getCarousel() {
        return carousel;
    }

    /**
     * Get The Right Carousel Button.
     * <p/>
     * @return AButton
     */
    public final AButton getBtnCarouselRight() {
        return btnCarouselRight;
    }

    /**
     * Get The Left Carousel Button.
     * <p/>
     * @return AButton
     */
    public final AButton getBtnCarouselLeft() {
        return btnCarouselLeft;
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

    /**
     * Get UI component Size Value.
     * <p/>
     * @return int
     */
    public final int getTopHeight() {
        return topHeight;
    }

    /**
     * Set UI component Size Value.
     * <p/>
     * @param theTopHeight int
     */
    public final void setTopHeight(final int theTopHeight) {
        this.topHeight = theTopHeight;
    }

    /**
     * Get UI component Size Value.
     * <p/>
     * @return int
     */
    public final int getImageWidth() {
        return logoWidth;
    }

    /**
     * Set UI component Size Value.
     * <p/>
     * @param theImageWidth int
     */
    public final void setImageWidth(final int theImageWidth) {
        this.logoWidth = theImageWidth;
    }

    /**
     * Get UI component Size Value.
     * <p/>
     * @return int
     */
    public final int getCarouselButtonWidth() {
        return carouselButtonWidth;
    }

    /**
     * Set UI component Size Value.
     * <p/>
     * @param theCarouselButtonWidth int
     */
    public final void setCarouselButtonWidth(final int theCarouselButtonWidth) {
        this.carouselButtonWidth = theCarouselButtonWidth;
    }

    /**
     * Get UI component Size Value.
     * <p/>
     * @return int
     */
    public final int getCarouselButtonHeight() {
        return carouselButtonHeight;
    }

    /**
     * Set UI component Size Value.
     * <p/>
     * @param theCarouselButtonHeight int
     */
    public final void setCarouselButtonHeight(final int theCarouselButtonHeight) {
        this.carouselButtonHeight = theCarouselButtonHeight;
    }

    /**
     * Is Dashboard LoadedUI() method completed
     * <p/>
     * @return
     */
    public boolean isDashboardUiLoaded() {
        return dashboardUiLoaded;
    }
}
