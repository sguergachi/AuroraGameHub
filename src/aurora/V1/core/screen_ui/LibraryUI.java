/*
 *  Made By Sardonix Creative.
 *
 * This work is licensed under the
 * Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * To view a copy of this license, visit
 *
 *      http://creativecommons.org/licenses/by-nc-nd/3.0/
 *
 * or send a letter to Creative Commons, 444 Castro Street, ScoreUIte 900,
 * Mountain View, California, 94041, USA.
 * Unless reqcoreUIred by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package aurora.V1.core.screen_ui;

import aurora.V1.core.AuroraApp;
import aurora.V1.core.AuroraCoreUI;
import aurora.V1.core.AuroraStorage;
import aurora.V1.core.GridAnimation;
import aurora.V1.core.GridManager;
import aurora.V1.core.screen_handler.LibraryHandler;
import aurora.V1.core.screen_handler.LibraryHandler.GameLibraryKeyListener;
import aurora.V1.core.screen_handler.LibraryHandler.HoverButtonLeft;
import aurora.V1.core.screen_handler.LibraryHandler.HoverButtonRight;
import aurora.V1.core.screen_handler.LibraryHandler.SearchBoxHandler;
import aurora.V1.core.screen_handler.LibraryHandler.SearchFocusHandler;
import aurora.V1.core.screen_handler.LibraryHandler.ShowAddGameUIHandler;
import aurora.V1.core.screen_logic.LibraryLogic;
import aurora.V1.core.screen_logic.SettingsLogic;
import aurora.engine.V1.Logic.AThreadWorker;
import aurora.engine.V1.UI.AButton;
import aurora.engine.V1.UI.AFadeLabel;
import aurora.engine.V1.UI.AHoverButton;
import aurora.engine.V1.UI.AImage;
import aurora.engine.V1.UI.AImagePane;
import aurora.engine.V1.UI.AProgressWheel;
import aurora.engine.V1.UI.ASlickLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.logging.Level;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import org.apache.log4j.Logger;

/**
 * .---------------------------------------------------------------------------.
 * | LibraryUI :: Aurora App Class
 * .---------------------------------------------------------------------------.
 * |
 * | This class contains the UI for the GameLibrary Screen which has a Handler
 * | and a Logic class associated to it.
 * |
 * | This is an AuroraApp meaning it also implements the AuroraScreenUI
 * | interface.
 * |
 * .............................................................................
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 * @author Carlos Machado <camachado@gmail.com>
 * <p/>
 */
public class LibraryUI extends AuroraApp {

    /**
     * Search Button to activate focus on Library Search Bar.
     */
    private AButton btnSearch;

    /**
     * Button to show add game UI.
     */
    private AButton btnShowAddGameUI;

    /**
     * Button to Exit out of Library Search.
     */
    private AButton btnRemoveSearch;

    /**
     * Hover Button to navigate Right in Library.
     */
    private AHoverButton btnMoveRight;

    /**
     * Hover Button to navigate Left in Library.
     */
    private AHoverButton btnMoveLeft;

    /**
     * Panel Containing Hover Buttons and the Library Grids.
     */
    private JPanel pnlLibraryContainer;

    /**
     * Panel Containing imgSelectedGamePane.
     */
    private JPanel pnlBottomCenterContainer;

    /**
     * Panel Containing SearchBarBG.
     */
    private JPanel pnlSearchBar;

    /**
     * Panel Containing Text box for Search Bar.
     */
    private JPanel pnlSearchText;

    /**
     * Panel Containing Button Panel and Search Text box Pane for Search Box.
     */
    private JPanel pnlSearchContainer;

    /**
     * Panel Containing Search Button for Search Box.
     */
    private JPanel pnlSearchButton;

    /**
     * AddGameUI Panel Containing Search box.
     */
    private JPanel pnlAddGameSearchContainer;

    /**
     * Panel Containing background image of Currently Selected game label.
     */
    private AImagePane imgLibraryStatusPane;

    /**
     * Background image for Search Bar.
     */
    private AImagePane pnlSearchBarBG;

    /**
     * Image Panel Containing Search Text Box for Search Bar.
     */
    private AImagePane pnlSearchBG;

    /**
     * AddGameUI Background Image of Button Panel.
     */
    private AImagePane pnlSearchButtonBG;

    /**
     * Image for keyboard icon.
     */
    private AImage imgKeyIco;

    /**
     * Image for Favorite logo on side of library.
     */
    private AImage imgOrganizeTypeSideBar;

    /**
     * AddGameUI Label explaining Keyboard action.
     */
    private JLabel lblKeyAction;

    /**
     * AddGameUI Label showing status of Library
     */
    public static AFadeLabel lblLibraryStatus;

    private ArrayList<AImagePane> gameCover;

    private GridManager libraryGridManager;

    private int currentIndex;

    private ArrayList<Boolean> loadedPanels;

    private GridAnimation GridAnimate;

    private JTextField txtGridSearchField;

    private final LibraryHandler libraryHandler;

    private SearchBoxHandler searchBoxHandler;

    private SearchFocusHandler searchFocusHandler;

    private boolean addGameUI_Visible = false;

    private JScrollPane gameScrollPane;

    private final AuroraStorage storage;

    private HoverButtonLeft moveLibraryLeftHandler;

    private HoverButtonRight moveLibraryRightHandler;

    private boolean isAddGameUILoaded = false;

    private boolean isGameLibraryKeyListenerAdded = false;

    private int SearchBarWidth;

    public static int gameCoverWidth;

    public static int zoomButtonHeight;

    public static int selectedGameBarHeight;

    public static int selectedGameBarWidth;

    public static int addGameWidth;

    public static int addGameHeight;

    public static int gameNameFontSize;

    public static int gameCoverHeight;

    public static int listFontSize;

    private boolean isScreenLoaded = false;

    private final DashboardUI dashboardUI;

    private final AuroraCoreUI coreUI;

    private final LibraryLogic libraryLogic;

    private AButton btnOrganizeGames;

    private int gridSearchFontSize;

    private int bottomTopPadding;

    static final Logger logger = Logger.getLogger(LibraryUI.class);

    private boolean editGameUIVisible = false;

    private boolean editUILoaded = false;

    private boolean editGameCoverUIvisible;

    private AProgressWheel prgLibraryStatus;

    private final long IDLE_TIME_TO_WAIT = 4000;

    private JPanel pnlLibraryStatusContainer;

    public static final String DEAFULT_LIBRARY_STATUS = "Select a Game";

    public static final Color DEFAULT_LIBRARY_COLOR = Color.lightGray;

    private int updatedCurrentIndex = -1;

    private AddGameUI addGameUI;

    private int addGameFontSize;

    private EditGameUI editGameUI;

    private EditCoverUI editCoverUI;

    private OrganizeUI organizeUI;

    private JPanel pnlSearchResultsContainer;

    private ASlickLabel lblSearchResults;

    private ASlickLabel lblSearchResultsInfo;

    private JPanel pnlMoveRightContainer;

    private JPanel pnlMoveLeftContainer;

    private GridBagConstraints gridBagConstant;

    /**
     * .-----------------------------------------------------------------------.
     * | LibraryUI(AuroraStorage, DashboardUI, AuroraCoreUI)
     * .-----------------------------------------------------------------------.
     * |
     * | This is the Constructor of the LibraryUI UI class.
     * |
     * | The Constructor of Screen Classes must initialize/create both a
     * | Handler and a Logic object which should contain the UI as a parameter
     * |
     * .........................................................................
     *
     * @param auroraStorage AuroraStorage
     * @param dashboardUi   DashboardUI
     * @param auroraCoreUI  AuroraCoreUI
     *
     */
    public LibraryUI(final AuroraStorage auroraStorage,
                     final DashboardUI dashboardUi,
                     final AuroraCoreUI auroraCoreUI) {
        this.appName = "Game Library";
        this.coreUI = auroraCoreUI;
        this.storage = auroraStorage;
        this.dashboardUI = dashboardUi;

        this.libraryLogic = new LibraryLogic(this);
        this.libraryHandler = new LibraryHandler(this);

        libraryHandler.setLogic(libraryLogic);
        libraryLogic.setHandler(libraryHandler);
        libraryLogic.setUpCoverDB();

    }

    @Override
    public final void loadUI() {

        // Load All UI Components
        // --------------------------------------------------------------------.

        setSize();

        //
        // Create Components for Library
        //
        pnlLibraryContainer = new JPanel(true);
        pnlLibraryContainer.setBorder(BorderFactory.createEmptyBorder(4,
                                                                      0, 0, 0));
        imgOrganizeTypeSideBar = new AImage("library_favourites.png");



        gridBagConstant = new GridBagConstraints();

        pnlMoveRightContainer = new JPanel(new GridBagLayout());
        pnlMoveRightContainer.setOpaque(false);
        btnMoveRight = new AHoverButton(4,
                                        "library_navRight_norm.png",
                                        "library_navRight_over.png");


        pnlMoveLeftContainer = new JPanel(new GridBagLayout());
        pnlMoveLeftContainer.setOpaque(false);
        btnMoveLeft = new AHoverButton(4,
                                       "library_navLeft_norm.png",
                                       "library_navLeft_over.png");
        //
        // Key Board Naviagtion Icon
        //
        imgKeyIco = new AImage("KeyboardKeys/arrows.png", coreUI
                               .getKeyIconWidth(), coreUI.getKeyIconHeight());
        lblKeyAction = new JLabel(" Move ");
        //
        // Library Status Pane
        //
        pnlBottomCenterContainer = new JPanel(new FlowLayout(FlowLayout.CENTER,
                                                             0, bottomTopPadding));
        imgLibraryStatusPane = new AImagePane("library_selectedGameBar_bg.png",
                                              selectedGameBarWidth,
                                              selectedGameBarHeight);
        imgLibraryStatusPane.setLayout(new BorderLayout(0, 10));
        prgLibraryStatus = new AProgressWheel("app_progressWheel.png");
        lblLibraryStatus = new AFadeLabel(DEAFULT_LIBRARY_STATUS);
        lblLibraryStatus.setForeground(DEFAULT_LIBRARY_COLOR);
        lblLibraryStatus.setFont(coreUI
                .getDefaultFont().deriveFont(Font.PLAIN,
                                             gameNameFontSize));
        //
        // Add Game Button
        //
        btnShowAddGameUI = new AButton("library_btn_addGame_norm.png",
                                       "library_btn_addGame_down.png",
                                       "library_btn_addGame_over.png");
        btnOrganizeGames = new AButton("library_btn_organizeGame_norm.png",
                                       "library_btn_organizeGame_down.png",
                                       "library_btn_organizeGame_over.png");
        //
        // Search Bar
        //
        pnlSearchBarBG = new AImagePane("library_searchBar_inactive.png",
                                        new BorderLayout());
        btnRemoveSearch = new AButton("library_btnCancelSearch_norm.png",
                                      "library_btnCancelSearch_down.png",
                                      "library_btnCancelSearch_over.png");
        txtGridSearchField = new JTextField("Just Start Typing...");
        btnSearch = new AButton("library_btnSearch_norm.png",
                                "library_btnSearch_down.png",
                                "library_btnSearch_over.png");
        pnlSearchButtonBG = new AImagePane("library_searchButton_bg.png",
                                           new BorderLayout());
        pnlSearchText = new JPanel(new BorderLayout());
        pnlSearchButton = new JPanel(new BorderLayout());
        pnlSearchContainer = new JPanel(new BorderLayout());
        pnlSearchBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        pnlSearchResultsContainer = new JPanel(new FlowLayout(FlowLayout.LEFT,
                                                              0, 0));
        pnlSearchResultsContainer.setOpaque(false);
        lblSearchResults = new ASlickLabel();
        lblSearchResultsInfo = new ASlickLabel("Results");
        //
        // Set up grid
        //
        libraryGridManager = new GridManager(2, 4, coreUI);
        this.GridAnimate = new GridAnimation(libraryGridManager,
                                             pnlLibraryContainer);


        //
        // initialize and load Library modules
        //
        addGameUI = new AddGameUI(coreUI, this);
        addGameUI.loadAddGameUI();

        editGameUI = new EditGameUI(coreUI, this);
        editGameUI.loadEditGameUI();

        organizeUI = new OrganizeUI(coreUI, this);
        organizeUI.loadOrganizeUI();

        editCoverUI = new EditCoverUI(coreUI, this);
        editCoverUI.loadEditGameCoverUI();

    }

    @Override
    public final void buildUI() {
        if (!isScreenLoaded) {
            setSize();

            pnlLibraryContainer.setOpaque(false);
            pnlLibraryContainer.setBackground(Color.red);
            pnlLibraryContainer.setLayout(new BorderLayout(0, 140));

            pnlMoveLeftContainer.add(btnMoveLeft, gridBagConstant);

            pnlMoveRightContainer.add(btnMoveRight, gridBagConstant);


            lblKeyAction.setFont(coreUI.getDefaultFont().deriveFont(Font.PLAIN,
                                                                    coreUI
                                                                    .getKeysFontSize()));
            lblKeyAction.setForeground(new Color(0, 178, 178));

            // Bottom Center Bar
            pnlBottomCenterContainer.setOpaque(false);

            imgLibraryStatusPane.setPreferredSize(new Dimension(
                    selectedGameBarWidth, selectedGameBarHeight));

            prgLibraryStatus.stop();

            pnlLibraryStatusContainer = new JPanel();
            pnlLibraryStatusContainer.setOpaque(false);

            pnlLibraryStatusContainer.setPreferredSize(new Dimension(
                    imgLibraryStatusPane.getPreferredSize().width - 100,
                    imgLibraryStatusPane.getPreferredSize().height));

            imgLibraryStatusPane.add(lblLibraryStatus,
                                     BorderLayout.CENTER);

            lblLibraryStatus.setSize(new Dimension(imgLibraryStatusPane
                    .getRealImageWidth(), imgLibraryStatusPane
                                                   .getRealImageHeight()
                                                  / 2 + gameNameFontSize / 2));

            lblLibraryStatus.validate();
            pnlLibraryStatusContainer.validate();

            JPanel progressContainer = new JPanel(
                    new FlowLayout(FlowLayout.LEFT, 0, 20));
            progressContainer.setOpaque(false);
            progressContainer.add(prgLibraryStatus);
            progressContainer.setPreferredSize(new Dimension(50, 25));

            imgLibraryStatusPane.add(progressContainer, BorderLayout.EAST);
            imgLibraryStatusPane.add(Box.createHorizontalStrut(50),
                                     BorderLayout.WEST);

            // Organize Games Button
            pnlBottomCenterContainer.add(btnOrganizeGames);

            // Selected Game Bar
            pnlBottomCenterContainer.add(imgLibraryStatusPane);

            // Add Game Button
            pnlBottomCenterContainer.add(btnShowAddGameUI);

            // Search Bar
            // ----------------------------------------------------------------.

            btnRemoveSearch.setPreferredSize(new Dimension(70, 51));

            // Search results
            lblSearchResults.setForeground(Color.darkGray);
            lblSearchResults.setFont(coreUI.getDefaultFont()
                    .deriveFont(Font.BOLD,
                                gridSearchFontSize - 5));
            lblSearchResults.setPreferredSize(new Dimension(30,
                                                            coreUI
                                                            .getFrameControlImagePane()
                                                            .getRealImageHeight()));


            lblSearchResultsInfo.setForeground(Color.darkGray);
            lblSearchResultsInfo.setFont(coreUI.getDefaultFont()
                    .deriveFont(Font.BOLD,
                                gridSearchFontSize - 15));
            lblSearchResultsInfo.setPreferredSize(new Dimension(60,
                                                                coreUI
                                                                .getFrameControlImagePane()
                                                                .getRealImageHeight()));
            pnlSearchResultsContainer.add(Box.createHorizontalStrut(10));
            pnlSearchResultsContainer.add(lblSearchResults);
            pnlSearchResultsContainer.add(lblSearchResultsInfo);

            pnlSearchResultsContainer.setPreferredSize(new Dimension(150,
                                                                     coreUI
                                                                     .getFrameControlImagePane()
                                                                     .getRealImageHeight()));

            // Search bar above grid
            txtGridSearchField.setOpaque(false);
            txtGridSearchField.setBorder(null);
            txtGridSearchField.setBorder(BorderFactory.createEmptyBorder());
            txtGridSearchField.setColumns(18);
            txtGridSearchField.setForeground(Color.darkGray);
            txtGridSearchField.setFont(coreUI.getDefaultFont()
                    .deriveFont(Font.BOLD,
                                gridSearchFontSize));
            txtGridSearchField.setPreferredSize(new Dimension(700, 50));

            // Search button
            btnSearch.setPreferredSize(new Dimension(70, 51));
            btnSearch
                    .addActionListener(libraryHandler.new SearchButtonHandler());

            // Background img of search button
            pnlSearchButtonBG.setPreferredSize(new Dimension(70, 51));
            pnlSearchButtonBG.add(btnSearch, BorderLayout.NORTH);

            // Text in search bar
            pnlSearchText.setOpaque(false);
            pnlSearchText.add(txtGridSearchField, BorderLayout.CENTER);
            pnlSearchText.add(pnlSearchResultsContainer,
                              BorderLayout.EAST);
            pnlSearchText.setPreferredSize(new Dimension(txtGridSearchField
                    .getPreferredSize().width + pnlSearchResultsContainer
                    .getPreferredSize().width, coreUI.getFrameControlImagePane()
                                                         .getRealImageHeight()));

            pnlSearchButton.setOpaque(false);
            pnlSearchButton.add(pnlSearchButtonBG, BorderLayout.NORTH);

            pnlSearchContainer.setOpaque(false);
            pnlSearchContainer.add(pnlSearchButton, BorderLayout.WEST);
            pnlSearchContainer.add(pnlSearchText, BorderLayout.CENTER);

            pnlSearchBarBG.add(pnlSearchContainer, BorderLayout.WEST);
            pnlSearchBarBG.validate();
            pnlSearchResultsContainer.setVisible(false);

            pnlSearchBar.setBackground(Color.GREEN);
            pnlSearchBar.setOpaque(false);
            pnlSearchBar.add(Box.createHorizontalStrut(coreUI
                    .getFrameControlImagePane().getRealImageWidth()));
            pnlSearchBar.add(pnlSearchBarBG);
            pnlSearchBar.setPreferredSize(new Dimension(pnlSearchBar
                    .getPreferredSize().width,
                                                        coreUI
                                                        .getFrameControlImagePane()
                                                        .getRealImageHeight()));
            pnlSearchBar.validate();

            // Initiate Library Grid
            libraryGridManager.initiateGrid(0);

            // Add Components to Central Container
            pnlLibraryContainer.add(BorderLayout.WEST, imgOrganizeTypeSideBar);
            pnlLibraryContainer.add(BorderLayout.CENTER, libraryGridManager
                                    .getGrid(0));
            pnlLibraryContainer.add(BorderLayout.EAST, pnlMoveRightContainer);

            // Add game to library
            libraryLogic.addGamesToLibrary();

            isScreenLoaded = true;
            addToCanvas();

        } else {

            addToCanvas();

        }

        backgroundWork();

    }

    /**
     * Does some tasks in the background after the UI is loaded
     */
    private void backgroundWork() {

        AThreadWorker backgroundWorker = new AThreadWorker(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        AuroraStorage aStorage = dashboardUI.getStorage();
                        String backgroundGameSearch = aStorage
                        .getStoredSettings().getSettingValue(
                                "background_game_search");
                        if (backgroundGameSearch == null) {
                            backgroundGameSearch
                                    = SettingsLogic.DEFAULT_BACKGROUND_SEARCH_SETTING;
                        }

                        if (backgroundGameSearch.equals("enabled")) {

                            // Idle wait before starting
                            try {
                                Thread.sleep(IDLE_TIME_TO_WAIT);
                            } catch (InterruptedException ex) {
                                java.util.logging.Logger.getLogger(
                                        LibraryUI.class
                                        .getName())
                                .log(Level.SEVERE, null, ex);
                            }

                            // Pre build Add game UI
                            getAddGameUI().buildAddGameUI();

                            if (!libraryLogic.isAutoLoadedOnce()) {
                                libraryLogic.setNeedAutoAddRefresh(true);
                                libraryLogic.autoFindGames();
                            }
                        }
                    }
                });

        backgroundWorker.startOnce();

    }

    @Override
    public final void addToCanvas() {
        coreUI.getTitleLabel().setText("   Loading...   ");

        coreUI.getLblKeyActionEnter().setText(" Play ");
        dashboardUI.getLblKeyActionArrow().setText(" Nav ");

        // Add Components with listeners to volatile listener bank
        // ----------------------------------------------------------------.
        addToVolatileListenerBank(txtGridSearchField);
        addToVolatileListenerBank(coreUI.getBackgroundImagePane());
        addToVolatileListenerBank(coreUI.getBottomPane());
        addToVolatileListenerBank(coreUI.getCenterPanel());
        addToVolatileListenerBank(coreUI.getSouthFromTopPanel());
        addToVolatileListenerBank(coreUI.getFrameControlImagePane());
        addToVolatileListenerBank(coreUI.getTopPane());
        addToVolatileListenerBank(this.pnlLibraryContainer);
        addToVolatileListenerBank(this.imgLibraryStatusPane);
        addToVolatileListenerBank(this.btnMoveLeft);
        addToVolatileListenerBank(this.btnMoveRight);

        // Remove Show Add Game UI Listener if it exists
        if (this.btnShowAddGameUI.getActionListeners().length > 0) {
            this.btnShowAddGameUI.removeActionListener(btnShowAddGameUI
                    .getActionListeners()[0]);
        }
        attactchHandlers();

        // Add Search Bar to Top Bar

        coreUI.getSouthFromTopPanel().add(BorderLayout.CENTER, pnlSearchBar);
        coreUI.getSouthFromTopPanel().setPreferredSize(
                new Dimension(coreUI.getSouthFromTopPanel().getWidth(), coreUI
                              .getFrameControlImagePane().getHeight()));
        coreUI.getSouthFromTopPanel().revalidate();

        // Add AddGameButton to Bottom Bar
        coreUI.getBottomContentPane().setLayout(new BorderLayout());
        coreUI.getBottomContentPane().setVisible(true);

        // Add InfoFeed to bottom //
        getDashboardUI().getInfoFeed().setImageSize(getCoreUI()
                .getScreenWidth() - 20, getDashboardUI().getInfoFeed()
                                                    .getImageHeight() - 5);
        getDashboardUI().getInfoFeed()
                .setPreferredSize(new Dimension(getDashboardUI().getInfoFeed()
                                .getPreferredSize().width,
                                                getDashboardUI().getInfoFeed()
                                                .getImageHeight()));

        coreUI.getBottomContentPane().add(Box.createVerticalStrut(4),
                                          BorderLayout.NORTH);
        coreUI.getBottomContentPane().add(Box.createHorizontalStrut(10),
                                          BorderLayout.EAST);
        coreUI.getBottomContentPane().add(dashboardUI.getInfoFeedContainer(),
                                          BorderLayout.CENTER);
        coreUI.getBottomContentPane().add(Box.createHorizontalStrut(10),
                                          BorderLayout.WEST);
        coreUI.getBottomContentPane().setPreferredSize(new Dimension(dashboardUI
                .getInfoFeed().getImageWidth(), dashboardUI.getInfoFeed()
                                                                     .getImageHeight()));
        coreUI.getBottomContentPane().revalidate();

        // Set up Bottom Bar
        coreUI.getCenterFromBottomPanel().setLayout(new BorderLayout());
        coreUI.getCenterFromBottomPanel().add(BorderLayout.NORTH,
                                              pnlBottomCenterContainer);
        coreUI.getCenterFromBottomPanel().add(BorderLayout.CENTER, coreUI
                                              .getBottomContentPane());

        // Add To Key Action Panel
        coreUI.getKeyToPressPanel().add(coreUI.getKeyIconImage());
        coreUI.getKeyToPressPanel().add(coreUI.getKeyActionLabel());
        coreUI.getKeyToPressPanel().add(imgKeyIco);
        coreUI.getKeyToPressPanel().add(lblKeyAction);
        coreUI.getHeaderOfCenterFromBottomPanel()
                .setPreferredSize(new Dimension(coreUI.getFrame().getWidth(), 5));
        coreUI.getHeaderOfCenterFromBottomPanel().revalidate();

        // Add Library Container to Center Panel
        coreUI.getCenterPanel().add(BorderLayout.CENTER, pnlLibraryContainer);
        coreUI.getCenterPanel().repaint();

        // Finalize
        coreUI.getTitleLabel().setText("     Game Library   ");
        btnMoveRight.requestFocusInWindow();
        coreUI.getFrame().requestFocus();

    }

    /**
     * .-----------------------------------------------------------------------.
     * | attactchHandlers()
     * .-----------------------------------------------------------------------.
     * |
     * | Attaches Handlers/Listeners to UI components that have previously
     * | never had them attached or been scrubbed of them through being an
     * | AuroraApp
     * |
     * .........................................................................
     *
     */
    public final void attactchHandlers() {


        // Create Handlers
        searchBoxHandler = libraryHandler.new SearchBoxHandler();
        searchFocusHandler = libraryHandler.new SearchFocusHandler();
        txtGridSearchField.addKeyListener(searchBoxHandler);
        txtGridSearchField.addFocusListener(searchFocusHandler);
        txtGridSearchField.addMouseListener(
                libraryHandler.new SearchSelectHandler());

        moveLibraryLeftHandler = libraryHandler.new HoverButtonLeft();
        moveLibraryRightHandler = libraryHandler.new HoverButtonRight();



        // Attach Handlers
        btnShowAddGameUI.addActionListener(
                libraryHandler.new ShowAddGameUIHandler());
        btnOrganizeGames
                .addActionListener(libraryHandler.new ShowOrganizeGameHandler());

        btnMoveRight.setMouseListener(moveLibraryRightHandler);
        btnMoveLeft.setMouseListener(moveLibraryLeftHandler);

        coreUI.getFrame()
                .addKeyListener(libraryHandler.new SearchRefocusListener());
        coreUI.getFrame()
                .addKeyListener(libraryHandler.new GameLibraryKeyListener());
        coreUI.getFrame()
                .addMouseWheelListener(
                        libraryHandler.new GridMouseWheelListener());

        coreUI.getBackgroundImagePane()
                .addKeyListener(libraryHandler.new SearchRefocusListener());
        coreUI.getBackgroundImagePane()
                .addKeyListener(libraryHandler.new GameLibraryKeyListener());
        coreUI.getBackgroundImagePane()
                .addMouseWheelListener(
                        libraryHandler.new GridMouseWheelListener());

        coreUI.getBottomPane()
                .addKeyListener(libraryHandler.new SearchRefocusListener());
        coreUI.getBottomPane()
                .addKeyListener(libraryHandler.new GameLibraryKeyListener());
        coreUI.getBottomPane()
                .addMouseWheelListener(
                        libraryHandler.new GridMouseWheelListener());

        coreUI.getCenterPanel()
                .addKeyListener(libraryHandler.new SearchRefocusListener());
        coreUI.getCenterPanel()
                .addKeyListener(libraryHandler.new GameLibraryKeyListener());
        coreUI.getCenterPanel()
                .addMouseWheelListener(
                        libraryHandler.new GridMouseWheelListener());

        coreUI.getSouthFromTopPanel()
                .addKeyListener(libraryHandler.new SearchRefocusListener());
        coreUI.getSouthFromTopPanel()
                .addKeyListener(libraryHandler.new GameLibraryKeyListener());
        coreUI.getSouthFromTopPanel()
                .addMouseWheelListener(
                        libraryHandler.new GridMouseWheelListener());

        coreUI.getFrameControlImagePane()
                .addKeyListener(libraryHandler.new SearchRefocusListener());
        coreUI.getFrameControlImagePane()
                .addKeyListener(libraryHandler.new GameLibraryKeyListener());
        coreUI.getFrameControlImagePane()
                .addMouseWheelListener(
                        libraryHandler.new GridMouseWheelListener());

        coreUI.getTopPane()
                .addKeyListener(libraryHandler.new SearchRefocusListener());
        coreUI.getTopPane()
                .addKeyListener(libraryHandler.new GameLibraryKeyListener());
        coreUI.getTopPane()
                .addMouseWheelListener(
                        libraryHandler.new GridMouseWheelListener());


        this.btnShowAddGameUI
                .addKeyListener(libraryHandler.new SearchRefocusListener());
        this.btnShowAddGameUI.addKeyListener(
                libraryHandler.new GameLibraryKeyListener());

        this.pnlLibraryContainer.addKeyListener(
                libraryHandler.new SearchRefocusListener());
        this.pnlLibraryContainer
                .addKeyListener(libraryHandler.new GameLibraryKeyListener());

        this.imgLibraryStatusPane
                .addKeyListener(libraryHandler.new SearchRefocusListener());
        this.imgLibraryStatusPane
                .addKeyListener(libraryHandler.new GameLibraryKeyListener());

        this.btnMoveLeft.addKeyListener(
                libraryHandler.new SearchRefocusListener());
        this.btnMoveLeft.addKeyListener(
                libraryHandler.new GameLibraryKeyListener());

        this.btnMoveRight
                .addKeyListener(libraryHandler.new SearchRefocusListener());
        this.btnMoveRight
                .addKeyListener(libraryHandler.new GameLibraryKeyListener());

        this.btnRemoveSearch.addActionListener(
                libraryHandler.new ResetSearchHandler());

    }

    /**
     * .-----------------------------------------------------------------------.
     * | moveGridLeft()
     * .-----------------------------------------------------------------------.
     * |
     * | Moves to the next grid by animating to the left
     * |
     * .........................................................................
     *
     */
    public void moveGridLeft() {

        if (logger.isDebugEnabled()) {
            logger.debug("Left key pressed");
        }

        if (!GridAnimate.getAnimator1().isAnimating() && !GridAnimate
                .getAnimator2().isAnimating()) {

            //
            // Get The Index of The Current Panel Being Displayed
            // Refer too GridManager array of All panels to find it
            //
            currentIndex = libraryGridManager.getArray()
                    .indexOf(pnlLibraryContainer.getComponent(1));

            // Stop from going to far left
            if (currentIndex - 1 == -1) {
                currentIndex = 1;
                btnMoveLeft.mouseExit();
            }

            if (currentIndex < libraryGridManager.getArray().size()) {

                libraryGridManager.decrementVisibleGridIndex();
                // Clear Panel
                if (currentIndex - 1 <= 0) {
                    // Far Left Image
                    pnlLibraryContainer.remove(0);
                    pnlLibraryContainer.add(imgOrganizeTypeSideBar,
                                            BorderLayout.WEST, 0);

                } else {
                    // Left Button
                    pnlLibraryContainer.remove(0);
                    pnlLibraryContainer.add(pnlMoveLeftContainer,
                                            BorderLayout.WEST, 0);
                }
                // Add GameCover Covers

                GridAnimate.moveLeft(currentIndex);

                pnlLibraryContainer
                        .add(BorderLayout.EAST, pnlMoveRightContainer);

                try {
                    libraryLogic.loadGames(currentIndex - 1);
                } catch (MalformedURLException ex) {
                    logger.error(ex);
                }
            }

            coreUI.getCenterPanel().removeAll();
            coreUI.getCenterPanel().add(BorderLayout.CENTER,
                                        pnlLibraryContainer);

            pnlLibraryContainer.repaint();
            pnlLibraryContainer.revalidate();

            coreUI.getFrame().requestFocus();

        }
        btnMoveLeft.mouseExit();
    }

    /**
     * .-----------------------------------------------------------------------.
     * | moveGridRight()
     * .-----------------------------------------------------------------------.
     * |
     * | Moves to the previous grid by animating to the right
     * |
     * .........................................................................
     *
     */
    public void moveGridRight() {

        if (logger.isDebugEnabled()) {
            logger.debug("Right key pressed");
        }

        if (!GridAnimate.getAnimator1().isAnimating() && !GridAnimate
                .getAnimator2().isAnimating()) {

            currentIndex = libraryGridManager.getArray()
                    .indexOf(pnlLibraryContainer.getComponent(1));

            if (currentIndex < libraryGridManager.getArray().size() - 1) {

                libraryGridManager.incrementVisibleGridIndex();

                pnlLibraryContainer.remove(0);
                pnlLibraryContainer.add(pnlMoveLeftContainer, BorderLayout.WEST,
                                        0);

                pnlLibraryContainer
                        .add(pnlMoveRightContainer, BorderLayout.EAST, 2);

                GridAnimate.moveRight(currentIndex);

                try {
                    libraryLogic.loadGames(currentIndex + 1);
                } catch (MalformedURLException ex) {
                    logger.error(ex);
                }

                //of on last Grid then dont show right arrow button
                if (!(currentIndex + 1 < libraryGridManager.getArray().size()
                                                 - 1)) {

                    pnlLibraryContainer.remove(pnlMoveRightContainer);
                    pnlLibraryContainer.add(Box.createHorizontalStrut(140),
                                            BorderLayout.EAST, 2);
                    btnMoveRight.mouseExit();
                }
            }

            coreUI.getCenterPanel().removeAll();
            coreUI.getCenterPanel().add(BorderLayout.CENTER,
                                        pnlLibraryContainer);

            pnlLibraryContainer.repaint();
            pnlLibraryContainer.revalidate();

            coreUI.getFrame().requestFocus();

            currentIndex = libraryGridManager.getArray()
                    .indexOf(pnlLibraryContainer.getComponent(1));

        }
        btnMoveRight.mouseExit();
    }

    @Override
    public final void closeApp() {
        if (isAddGameUIVisible()) {
            addGameUI.hideAddGameUI();
        }
        if (isEditGameUIVisible()) {
            editGameUI.hideEditGameUI();
        }

        libraryGridManager.unFlipAll();
        libraryGridManager.unselectPrevious();
    }

    public void setSize() {

        double Ratio = ((double) coreUI.getFrame().getWidth() / (double) coreUI
                .getFrame().getHeight());

        int Ratio2 = (coreUI.getFrame().getWidth() - coreUI.getFrame()
                .getHeight()) / 2;

        if (logger.isDebugEnabled()) {
            logger.debug("Ratio " + Ratio);
            logger.debug("Height " + coreUI.getFrame().getHeight());
            logger.debug("Width " + coreUI.getFrame().getWidth());

        }

        if (coreUI.isLargeScreen()) {
            gameCoverHeight = coreUI.getFrame().getHeight() / 3 - (Ratio2 / 10)
                                      + 5;
            gameCoverWidth = coreUI.getFrame().getWidth() / 5 - (Ratio2 / 10)
                                     - 5;
            selectedGameBarHeight = coreUI.getBottomPane().getHeight() / 3;
            selectedGameBarWidth = coreUI.getFrame().getWidth() / 3;
            addGameWidth = coreUI.getFrame().getWidth() / 3;
            addGameHeight = coreUI.getBottomPane().getHeight() / 3;
            gameNameFontSize = 32;
            SearchBarWidth = coreUI.getFrame().getWidth() - coreUI
                    .getFrameControlImagePane().getHeight();
            listFontSize = 19;
            gridSearchFontSize = 35;
            addGameFontSize = 28;
            bottomTopPadding = 10;

        } else {
            gameCoverHeight = (int) ((coreUI.getFrame().getHeight() + (coreUI
                    .getTopPanelHeight() * 2)) / (Ratio * 2.5));
            gameCoverWidth = (int) ((coreUI.getFrame().getWidth() + coreUI
                    .getTopPanelHeight()) / (Ratio * 3.5));
            addGameWidth = coreUI.getFrame().getWidth() / 3 - 20;
            addGameWidth = gameCoverWidth;
            addGameHeight = 40;
            selectedGameBarHeight = coreUI.getBottomPane().getHeight() / 3;
            selectedGameBarWidth = coreUI.getFrame().getWidth() / 3 - 20;
            gameNameFontSize = 30;
            SearchBarWidth = coreUI.getFrame().getWidth() / 2 + coreUI
                    .getControlWidth() / 2;
            listFontSize = 19;
            gridSearchFontSize = 35;
            addGameFontSize = 28;
            bottomTopPadding = -5;
        }

    }

    // Getters and Setters
    // ----------------------------------------------------------------.
    public EditGameUI getEditGameUI() {
        return editGameUI;
    }

    public int getAddGameFontSize() {
        return addGameFontSize;
    }

    public boolean isEditGameUIVisible() {
        return editGameUIVisible;
    }

    public void setEditGameUI_Visible(boolean editGameUI_Visible) {
        this.editGameUIVisible = editGameUI_Visible;
    }

    public AddGameUI getAddGameUI() {
        return addGameUI;
    }

    public boolean isAddGameUIVisible() {
        return addGameUI_Visible;
    }

    public void setIsAddGameUI_Visible(boolean isAddGameUI_Visible) {
        this.addGameUI_Visible = isAddGameUI_Visible;
    }

    public boolean isEditUILoaded() {
        return editUILoaded;
    }

    public void setIsEditUILoaded(boolean isEditUILoaded) {
        this.editUILoaded = isEditUILoaded;
    }

    public AFadeLabel getLblLibraryStatus() {
        return lblLibraryStatus;
    }

    public boolean isGameLibraryKeyListenerAdded() {
        return isGameLibraryKeyListenerAdded;
    }

    public final void setIsGameLibraryKeyListenerAdded(
            boolean isGameLibraryKeyListenerAdded) {
        this.isGameLibraryKeyListenerAdded = isGameLibraryKeyListenerAdded;
    }

    public HoverButtonLeft getMoveLibraryLeftHandler() {
        return moveLibraryLeftHandler;
    }

    public AProgressWheel getPrgLibraryStatus() {
        return prgLibraryStatus;
    }

    public HoverButtonRight getMoveLibraryRightHandler() {
        return moveLibraryRightHandler;
    }

    public JPanel getAddSearchContainer() {
        return pnlAddGameSearchContainer;
    }

    public LibraryHandler getHandler() {
        return libraryHandler;
    }

    public AButton getRemoveSearchButton() {
        return btnRemoveSearch;
    }

    public JScrollPane getListScrollPane() {
        return gameScrollPane;
    }

    public AImagePane getPnlSearchBG() {
        return pnlSearchBG;
    }

    public JPanel getSearchButtonPane() {
        return pnlSearchButton;
    }

    public JPanel getGamesContainer() {
        return pnlLibraryContainer;
    }

    public GridAnimation getGridAnimate() {
        return GridAnimate;
    }

    public GridManager getGridSplit() {
        return libraryGridManager;
    }

    public boolean isEditGameCoverUI_visible() {
        return editGameCoverUIvisible;
    }

    public void setIsEditGameCoverUI_visible(boolean isEditGameCoverUI_visible) {
        this.editGameCoverUIvisible = isEditGameCoverUI_visible;
    }

    /**
     * Size Value.
     * <p/>
     * @return int
     */
    public final int getAddGameHeight() {
        return addGameHeight;
    }

    /**
     * Size Value.
     * <p/>
     * @return int
     */
    public final int getAddGameWidth() {
        return addGameWidth;
    }

    /**
     * Size Value.
     * <p/>
     * @return int
     */
    public final int getGameCoverHeight() {
        return gameCoverHeight;
    }

    /**
     * Size Value.
     * <p/>
     * @return int
     */
    public final int getGameNameFont() {
        return gameNameFontSize;
    }

    /**
     * Size Value.
     * <p/>
     * @return int
     */
    public final int getSelectedGameBarHeight() {
        return selectedGameBarHeight;
    }

    /**
     * Size Value.
     * <p/>
     * @return int
     */
    public final int getSelectedGameBarWidth() {
        return selectedGameBarWidth;
    }

    /**
     * Size Value.
     * <p/>
     * @return int
     */
    public final int getGameNameFontSize() {
        return gameNameFontSize;
    }

    /**
     * Size Value.
     * <p/>
     * @return int
     */
    public final int getZoomButtonHeight() {
        return zoomButtonHeight;
    }

    /**
     * Size Value.
     * <p/>
     * @return int
     */
    public final int getGameCoverWidth() {
        return gameCoverWidth;
    }

    public JTextField getSearchBar() {
        return txtGridSearchField;
    }

    public AImagePane getSearchBarBG() {
        return pnlSearchBarBG;
    }

    public AButton getSearchButton() {
        return btnSearch;
    }

    public AImagePane getSearchButtonBG() {
        return pnlSearchButtonBG;
    }

    public JPanel getSearchContainer() {
        return pnlSearchContainer;
    }

    public JPanel getSearchPane() {
        return pnlSearchBar;
    }

    public JPanel getSelectedGameContainer() {
        return pnlBottomCenterContainer;
    }

    public AuroraStorage getStorage() {
        return storage;
    }

    public JPanel getTextPane() {
        return pnlSearchText;
    }

    public int getSearchBarWidth() {
        return SearchBarWidth;
    }

    public AButton getBtnShowAddGameUI() {
        return btnShowAddGameUI;
    }

    public int getCurrentGridIndex() {

        if (updatedCurrentIndex == -1) {
            currentIndex = libraryGridManager.getArray()
                    .indexOf(pnlLibraryContainer.getComponent(1));
            return currentIndex;
        } else {

            return updatedCurrentIndex;
        }

    }

    public void setCurrentIndex(int index) {
        updatedCurrentIndex = index;
    }

    public ArrayList<AImagePane> getGameCover() {
        return gameCover;
    }

    public AImage getImgOrganizeType() {
        return imgOrganizeTypeSideBar;
    }

    public AImage getImgKeyIco() {
        return imgKeyIco;
    }

    public AImagePane getImgSelectedGamePane() {
        return imgLibraryStatusPane;
    }

    public JLabel getLblKeyAction() {
        return lblKeyAction;
    }

    public ArrayList<Boolean> getLoadedPanels() {
        return loadedPanels;
    }

    public AuroraCoreUI getCoreUI() {
        return coreUI;
    }

    public DashboardUI getDashboardUI() {
        return dashboardUI;
    }

    public LibraryLogic getLogic() {
        return libraryLogic;
    }

    public boolean isAddGameUILoaded() {
        return isAddGameUILoaded;
    }

    public void setIsAddGameUILoaded(boolean isAddGameUILoaded) {
        this.isAddGameUILoaded = isAddGameUILoaded;
    }

    public EditCoverUI getEditCoverUI() {
        return editCoverUI;
    }

    public OrganizeUI getOrganizeUI() {
        return organizeUI;
    }

    public AButton getBtnOrganizeGames() {
        return btnOrganizeGames;
    }

    public ASlickLabel getLblSearchResults() {
        return lblSearchResults;
    }

    public JPanel getPnlSearchResultsContainer() {
        return pnlSearchResultsContainer;
    }

    public JTextField getTxtGridSearchField() {
        return txtGridSearchField;
    }

    public ASlickLabel getLblSearchResultsInfo() {
        return lblSearchResultsInfo;
    }

    public AHoverButton getBtnMoveLeft() {
        return btnMoveLeft;
    }

    public AHoverButton getBtnMoveRight() {
        return btnMoveRight;
    }

    public JPanel getPnlMoveRightContainer() {
        return pnlMoveRightContainer;
    }

    public JPanel getPnlMoveLeftContainer() {
        return pnlMoveLeftContainer;
    }

    public static int getListFontSize() {
        return listFontSize;
    }
}
