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

import aurora.V1.core.*;
import aurora.V1.core.screen_handler.LibraryHandler;
import aurora.V1.core.screen_handler.LibraryHandler.GameLibraryKeyListener;
import aurora.V1.core.screen_handler.LibraryHandler.HoverButtonLeft;
import aurora.V1.core.screen_handler.LibraryHandler.HoverButtonRight;
import aurora.V1.core.screen_handler.LibraryHandler.ShowAddGameUiHandler;
import aurora.V1.core.screen_handler.LibraryHandler.searchBoxHandler;
import aurora.V1.core.screen_handler.LibraryHandler.searchFocusHandler;
import aurora.V1.core.screen_logic.LibraryLogic;
import aurora.engine.V1.Logic.*;
import aurora.engine.V1.UI.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.PopupFactory;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

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
     * Zoom In Button.
     */
    private AButton btnZoomPlus;

    /**
     * Zoom Out Button.
     */
    private AButton btnZoomLess;

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
    private AButton removeSearchButton;

    /**
     * Button to Add Searched Game to Library.
     */
    private AButton addGameToLibButton;

    /**
     * Button to Close Add Game UI.
     */
    private AButton btnClose_addUI;

    /**
     * Hover Button to navigate Right in Library.
     */
    private AHoverButton btnGameRight;

    /**
     * Hover Button to navigate Left in Library.
     */
    private AHoverButton btnGameLeft;

    /**
     * Panel Containing Hover Buttons and the Library Grids.
     */
    private JPanel paneLibraryContainer;

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
     * AddGameUI Panel Containing Center Content for picking game to add.
     */
    private JPanel pnlCenter_addUI;

    /**
     * AddGameUI Panel Containing Top part of Bottom Panel.
     */
    private JPanel pnTopOfBottom;

    /**
     * AddGameUI Panel Containing Top part of Center Panel.
     */
    private JPanel pnlTopOfCenter;

    /**
     * AddGameUI ImagePane Containing Left part of Center Panel.
     */
    private AImagePane pnlLeftOfTopCenter;

    /**
     * AddGameUI ImagePane Containing Right part of Top Part of Center Panel.
     */
    private AImagePane pnlRightOfTop;

    /**
     * AddGameUI Panel Containing Top part of Panel.
     */
    private JPanel pnlTopPane_addUI;

    /**
     * AddGameUI Panel Containing Left part of Bottom Part of Center Panel.
     */
    private JPanel pnlLeftOfBottom;

    /**
     * AddGameUI Panel Containing Right part of Bottom Part of Center Panel.
     */
    private JPanel pnlRightOfBottom;

    /**
     * AddGameUI Panel Containing Bottom part of Center Panel.
     */
    private JPanel pnlBottomOfCenter;

    /**
     * AddGameUI Panel Containing Right part of Bottom Panel.
     */
    private JPanel pnlRightOfBottomContainer;

    /**
     * AddGameUI Main Panel Containing All Other Panels.
     */
    private JPanel pnlAddGameContainer;

    /**
     * AddGameUI Glass Panel from current JFrame.
     */
    private JPanel pnlGlass;

    /**
     * AddGameUI Bottom panel.
     */
    private JPanel pnlBottomPane;

    /**
     * Panel Containing background image of Currently Selected game label.
     */
    private AImagePane imgLibraryStatusPane;

    /**
     * Background image for Search Bar.
     */
    private AImagePane pnlSearchBarBG;

    /**
     * AddGameUI Image Panel representing AddGameUI.
     */
    private AImagePane pnlAddGamePane;

    /**
     * Image Panel Containing Search Text Box for Search Bar.
     */
    private AImagePane pnlSearchBG;

    /**
     * AddGameUI Panel that contains the Game Cover.
     */
    private AImagePane pnlCoverPane;

    /**
     * AddGameUI Image that is a Blank Case as a place holder Game Cover.
     */
    private AImagePane pnlBlankCoverGame;

    /**
     * AddGameUI Background Image of Button Panel.
     */
    private AImagePane pnlSearchButtonBG;

    /**
     * AddGameUI Shortcut button to Steam games dir.
     */
    private AButton btnGoToSteam;

    /**
     * AddGameUI Shortcut button to Programs dir.
     */
    private AButton btnGoToProgram;

    /**
     * AddGameUI Pane containing Shortcut buttons.
     */
    private JPanel pnlRightOfTopEast;

    /**
     * Image for keyboard icon.
     */
    private AImage imgKeyIco;

    /**
     * Image for Favorite logo on side of library.
     */
    private AImage imgOrganizeTypeSideBar;

    /**
     * Image Step One badge.
     */
    private AImage statusBadge1;

    /**
     * Image Step Two badge.
     */
    private AImage statusBadge2;

    /**
     * AddGameUI Label explaining left side of panel.
     */
    private JLabel lblLeftTitle;

    /**
     * AddGameUI Label explaining right side of panel.
     */
    private JLabel lblRightTitle;

    /**
     * AddGameUI Label explaining Keyboard action.
     */
    private JLabel lblKeyAction;

    /**
     * AddGameUI Label with Title of Selected Game.
     */
    public static AFadeLabel lblLibraryStatus;

    private ArrayList<AImagePane> gameCover;

    private int zoom;

    private GridManager GridSplit;

    private int currentIndex;

    private ArrayList<Boolean> loadedPanels;

    private GridAnimation GridAnimate;

    private JTextField searchTextField;

    private LibraryHandler handler;

    private ASimpleDB CoverDB;

    private AAnimate addGameAnimator;

    private JTextField addGameSearchField;

    private searchBoxHandler searchBoxHandler;

    private searchFocusHandler searchFocusHandler;

    private boolean addGameUI_Visible = false;

    private JList gamesList;

    private JScrollPane gameScrollPane;

    private JFileChooser gameFileChooser;

    private DefaultListModel listModel;

    private String currentPath;

    private AAnimate addGameToLibButtonAnimator;

    private AuroraStorage storage;

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

    private boolean isScreenLoaded = false;

    private final DashboardUI dashboardUI;

    private final AuroraCoreUI coreUI;

    private final LibraryLogic logic;

    private AButton btnOrganizeGames;

    private int listFontSize;

    private int gridSearchFontSize;

    private int addGameFontSize;

    private int bottomTopPadding;

    static final Logger logger = Logger.getLogger(LibraryUI.class);

    private JPanel pnlRightOfTopEastContainer;

    private APopupMenu organizeMenu;

    private ARadioButtonManager organizeBtnManager;

    private ARadioButton btnBottom;

    private ARadioButton btnMiddle;

    private ARadioButton btnTop;

    private ASlickLabel lblFavorite;

    private ASlickLabel lblAlphabetic;

    private ASlickLabel lblMostPlayed;

    private AImage icoFavorite;

    private AImage icoAlphabetic;

    private AImage icoMostPlayed;

    private JPanel favoritePane;

    private JPanel alphabeticPane;

    private JPanel mostplayedPane;

    private AImagePane pnlEditGamePane;

    private AButton btnClose_editUI;

    private boolean isEditUILoaded = false;

    private JPanel pnlTopPane_editUI;

    private boolean editGameUI_Visible = false;

    private AAnimate editGameAnimator;

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

        this.logic = new LibraryLogic(this);
        this.handler = new LibraryHandler(this);

        handler.setLogic(logic);
        logic.setHandler(handler);
    }

    @Override
    public final void loadUI() {

        setSize();

        // Load All UI Components
        // --------------------------------------------------------------------.

        //* Create Components for Library *//

        paneLibraryContainer = new JPanel(true);
        paneLibraryContainer.setBorder(BorderFactory.createEmptyBorder(4,
                0, 0, 0));

        imgOrganizeTypeSideBar = new AImage("library_favourites.png");

        btnGameRight = new AHoverButton(3,
                "library_navRight_norm.png", "library_navRight_over.png");
        btnGameLeft = new AHoverButton(3,
                "library_navLeft_norm.png", "library_navLeft_over.png");

        //* Key Board Naviagtion Icon *//
        imgKeyIco = new AImage("KeyboardKeys/arrows.png", coreUI
                .getKeyIconWidth(), coreUI.getKeyIconHeight());
        lblKeyAction = new JLabel(" Move ");


        //* Selected Game Name Bar *//
        pnlBottomCenterContainer = new JPanel(new FlowLayout(FlowLayout.CENTER,
                0, bottomTopPadding));

        imgLibraryStatusPane = new AImagePane("library_selectedGameBar_bg.png",
                selectedGameBarWidth, selectedGameBarHeight,
                new FlowLayout(FlowLayout.CENTER, 0, 5));
        imgLibraryStatusPane.setLayout(new BorderLayout());

        lblLibraryStatus = new AFadeLabel("Select a Game");
        lblLibraryStatus.setForeground(Color.lightGray);
        lblLibraryStatus.setFont(coreUI
                .getDefaultFont().deriveFont(Font.PLAIN,
                gameNameFontSize));




        //* Add Game Button *//

        btnShowAddGameUI = new AButton("library_btn_addGame_norm.png",
                "library_btn_addGame_down.png",
                "library_btn_addGame_over.png");

        btnOrganizeGames = new AButton("library_btn_organizeGame_norm.png",
                "library_btn_organizeGame_down.png",
                "library_btn_organizeGame_over.png");


        //* Search Bar *//
        pnlSearchBarBG = new AImagePane("library_searchBar_inactive.png",
                new BorderLayout());
        removeSearchButton = new AButton("library_btnCancelSearch_norm.png",
                "library_btnCancelSearch_down.png",
                "library_btnCancelSearch_over.png");
        searchTextField = new JTextField("Start Typing To Search...");
        btnSearch = new AButton("library_btnSearch_norm.png",
                "library_btnSearch_down.png", "library_btnSearch_over.png");
        pnlSearchButtonBG = new AImagePane("library_searchButton_bg.png",
                new BorderLayout());
        pnlSearchText = new JPanel(new BorderLayout());
        pnlSearchButton = new JPanel(new BorderLayout());
        pnlSearchContainer = new JPanel(new BorderLayout());
        pnlSearchBar = new JPanel(new BorderLayout());

        //* Create Grid Manager *//
        GridSplit = new GridManager(2, 4, coreUI);

        //* Grid Animator *//
        this.GridAnimate = new GridAnimation(GridSplit, paneLibraryContainer);


        //* Load Organize UI *//
        loadOrganizeUI();

        //* Add Game UI *//
        loadAddGameUI();

        //* Edit Game UI *//
        loadEditGameUI();

    }

    @Override
    public final void buildUI() {
        if (!isScreenLoaded) {
            setSize();

            paneLibraryContainer.setOpaque(false);
            paneLibraryContainer.setBackground(Color.red);
            paneLibraryContainer.setLayout(new BorderLayout(0, 140));

            lblKeyAction.setFont(coreUI.getDefaultFont().deriveFont(Font.PLAIN,
                    coreUI.getKeysFontSize()));
            lblKeyAction.setForeground(new Color(0, 178, 178));


            //* Bottom Center Bar *//
            pnlBottomCenterContainer.setOpaque(false);



            imgLibraryStatusPane.setPreferredSize(new Dimension(
                    selectedGameBarWidth, selectedGameBarHeight));
            imgLibraryStatusPane.add(lblLibraryStatus);

            //* Organize Games Button *//
            pnlBottomCenterContainer.add(btnOrganizeGames);

            //* Selected Game Bar *//
            pnlBottomCenterContainer.add(imgLibraryStatusPane);

            //* Add Game Button *//
            pnlBottomCenterContainer.add(btnShowAddGameUI);




            // Search Bar
            // ----------------------------------------------------------------.

            pnlSearchBarBG.setPreferredSize(new Dimension(SearchBarWidth,
                    50));
            removeSearchButton.setPreferredSize(new Dimension(70, 51));

            searchTextField.setOpaque(false);
            searchTextField.setBorder(null);
            searchTextField.setColumns(20);
            searchTextField.setForeground(Color.darkGray);
            searchTextField.setFont(coreUI.getDefaultFont()
                    .deriveFont(Font.BOLD,
                    gridSearchFontSize));
            searchTextField.setPreferredSize(new Dimension(880, 50));

            btnSearch.setPreferredSize(new Dimension(70, 51));
            btnSearch
                    .addActionListener(handler.new searchButtonHandler());

            pnlSearchButtonBG.setPreferredSize(new Dimension(70, 51));
            pnlSearchButtonBG.add(btnSearch, BorderLayout.NORTH);


            pnlSearchText.setOpaque(false);
            pnlSearchText.add(searchTextField, BorderLayout.CENTER);

            pnlSearchButton.setOpaque(false);
            pnlSearchButton.add(pnlSearchButtonBG, BorderLayout.NORTH);


            pnlSearchContainer.setOpaque(false);
            pnlSearchContainer.add(pnlSearchButton, BorderLayout.WEST);
            pnlSearchContainer.add(pnlSearchText, BorderLayout.CENTER);


            pnlSearchBarBG.add(pnlSearchContainer, BorderLayout.WEST);
            pnlSearchBarBG.validate();

            pnlSearchBar.setOpaque(false);
            pnlSearchBar.add(pnlSearchBarBG, BorderLayout.EAST);
            pnlSearchBar.setPreferredSize(new Dimension(
                    pnlSearchBar.getBounds().width,
                    75));
            pnlSearchBar.validate();

            //* Initiate Library Grid *//
            GridSplit.initiateGrid(0);


            //* Add Components to Central Container *//
            paneLibraryContainer.add(BorderLayout.WEST, imgOrganizeTypeSideBar);
            paneLibraryContainer.add(BorderLayout.CENTER, GridSplit.getGrid(0));
            paneLibraryContainer.add(BorderLayout.EAST, btnGameRight);


            //* add game to library *//
            logic.addGamesToLibrary();

            isScreenLoaded = true;
            addToCanvas();


        } else {
            addToCanvas();
        }


    }

    @Override
    public final void addToCanvas() {
        coreUI.getTitleLabel().setText("   Loading...   ");

        coreUI.getLblKeyActionEnter().setText(" Play ");
        dashboardUI.getLblKeyActionArrow().setText(" Nav ");



        // Add Components with listeners to volatile listener bank
        // ----------------------------------------------------------------.

        addToVolatileListenerBank(searchTextField);
        addToVolatileListenerBank(coreUI.getBackgroundImagePane());
        addToVolatileListenerBank(coreUI.getBottomPane());
        addToVolatileListenerBank(coreUI.getCenterPanel());
        addToVolatileListenerBank(coreUI.getSouthFromTopPanel());
        addToVolatileListenerBank(coreUI.getFrameControlImagePane());
        addToVolatileListenerBank(coreUI.getTopPane());
        addToVolatileListenerBank(this.paneLibraryContainer);
        addToVolatileListenerBank(this.imgLibraryStatusPane);
        addToVolatileListenerBank(this.btnGameLeft);
        addToVolatileListenerBank(this.btnGameRight);

        if (this.btnShowAddGameUI.getActionListeners().length > 0) {
            this.btnShowAddGameUI.removeActionListener(btnShowAddGameUI
                    .getActionListeners()[0]);
        }
        attactchHandlers();

        //* Add Search Bar to Top Bar *//
        coreUI.getSouthFromTopPanel().add(BorderLayout.CENTER, pnlSearchBar);
        coreUI.getSouthFromTopPanel().setPreferredSize(
                new Dimension(coreUI.getSouthFromTopPanel().getWidth(), coreUI
                .getFrameControlImagePane().getHeight()));
        coreUI.getSouthFromTopPanel().revalidate();

        //* Add AddGameButton to Bottom Bar *//
        coreUI.getBottomContentPane().setLayout(new BorderLayout());
        coreUI.getBottomContentPane().setVisible(true);

        // Add InfoFeed to bottom //
        getDashboardUI().getInfoFeed().setImageSize(getCoreUI()
                .getScreenWidth() - 20, getDashboardUI().getInfoFeed()
                .getImageHeight() - 5);
        getDashboardUI().getInfoFeed()
                .setPreferredSize(new Dimension(getDashboardUI().getInfoFeed()
                .getPreferredSize().width,
                getDashboardUI().getInfoFeed().getImageHeight()));

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

        //* Set up Bottom Bar *//
        coreUI.getCenterFromBottomPanel().setLayout(new BorderLayout());
        coreUI.getCenterFromBottomPanel().add(BorderLayout.NORTH,
                pnlBottomCenterContainer);
        coreUI.getCenterFromBottomPanel().add(BorderLayout.CENTER, coreUI
                .getBottomContentPane());



        //* Add To Key Action Panel *//
        coreUI.getKeyToPressPanel().add(coreUI.getKeyIconImage());
        coreUI.getKeyToPressPanel().add(coreUI.getKeyActionLabel());
        coreUI.getKeyToPressPanel().add(imgKeyIco);
        coreUI.getKeyToPressPanel().add(lblKeyAction);
        coreUI.getHeaderOfCenterFromBottomPanel()
                .setPreferredSize(new Dimension(coreUI.getFrame().getWidth(), 5));
        coreUI.getHeaderOfCenterFromBottomPanel().revalidate();


        //* Add Library Container to Center Panel *//
        coreUI.getCenterPanel().add(BorderLayout.CENTER, paneLibraryContainer);
        coreUI.getCenterPanel().repaint();



        //* Finalize *//
        coreUI.getTitleLabel().setText("     Game Library   ");
        btnGameRight.requestFocusInWindow();
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

        searchBoxHandler = handler.new searchBoxHandler();
        searchFocusHandler = handler.new searchFocusHandler();
        searchTextField.addKeyListener(searchBoxHandler);
        searchTextField.addFocusListener(searchFocusHandler);
        searchTextField.addMouseListener(handler.new searchSelectHandler());

        moveLibraryLeftHandler = handler.new HoverButtonLeft();
        moveLibraryRightHandler = handler.new HoverButtonRight();

        btnShowAddGameUI.addActionListener(handler.new ShowAddGameUiHandler());
        btnOrganizeGames
                .addActionListener(handler.new ShowOrganizeGameHandler());

        btnGameRight.setMouseListener(moveLibraryRightHandler);
        btnGameLeft.setMouseListener(moveLibraryLeftHandler);

        coreUI.getFrame()
                .addKeyListener(handler.new searchRefocusListener());
        coreUI.getFrame()
                .addKeyListener(handler.new GameLibraryKeyListener());
        coreUI.getFrame()
                .addMouseWheelListener(handler.new GridMouseWheelListener());

        coreUI.getBackgroundImagePane()
                .addKeyListener(handler.new searchRefocusListener());
        coreUI.getBackgroundImagePane()
                .addKeyListener(handler.new GameLibraryKeyListener());
        coreUI.getBackgroundImagePane()
                .addMouseWheelListener(handler.new GridMouseWheelListener());

        coreUI.getBottomPane()
                .addKeyListener(handler.new searchRefocusListener());
        coreUI.getBottomPane()
                .addKeyListener(handler.new GameLibraryKeyListener());
        coreUI.getBottomPane()
                .addMouseWheelListener(handler.new GridMouseWheelListener());

        coreUI.getCenterPanel()
                .addKeyListener(handler.new searchRefocusListener());
        coreUI.getCenterPanel()
                .addKeyListener(handler.new GameLibraryKeyListener());
        coreUI.getCenterPanel()
                .addMouseWheelListener(handler.new GridMouseWheelListener());

        coreUI.getSouthFromTopPanel()
                .addKeyListener(handler.new searchRefocusListener());
        coreUI.getSouthFromTopPanel()
                .addKeyListener(handler.new GameLibraryKeyListener());
        coreUI.getSouthFromTopPanel()
                .addMouseWheelListener(handler.new GridMouseWheelListener());

        coreUI.getFrameControlImagePane()
                .addKeyListener(handler.new searchRefocusListener());
        coreUI.getFrameControlImagePane()
                .addKeyListener(handler.new GameLibraryKeyListener());
        coreUI.getFrameControlImagePane()
                .addMouseWheelListener(handler.new GridMouseWheelListener());

        coreUI.getTopPane()
                .addKeyListener(handler.new searchRefocusListener());
        coreUI.getTopPane()
                .addKeyListener(handler.new GameLibraryKeyListener());
        coreUI.getTopPane()
                .addMouseWheelListener(handler.new GridMouseWheelListener());

        this.btnShowAddGameUI
                .addKeyListener(handler.new searchRefocusListener());
        this.btnShowAddGameUI.addKeyListener(
                handler.new GameLibraryKeyListener());

        this.paneLibraryContainer.addKeyListener(
                handler.new searchRefocusListener());
        this.paneLibraryContainer
                .addKeyListener(handler.new GameLibraryKeyListener());

        this.imgLibraryStatusPane
                .addKeyListener(handler.new searchRefocusListener());
        this.imgLibraryStatusPane
                .addKeyListener(handler.new GameLibraryKeyListener());

        this.btnGameLeft.addKeyListener(handler.new searchRefocusListener());
        this.btnGameLeft.addKeyListener(handler.new GameLibraryKeyListener());

        this.btnGameRight
                .addKeyListener(handler.new searchRefocusListener());
        this.btnGameRight
                .addKeyListener(handler.new GameLibraryKeyListener());

    }

    /**
     * .-----------------------------------------------------------------------.
     * | loadAddGameUI()
     * .-----------------------------------------------------------------------.
     * |
     * | load the components to the Add Game UI
     * | this method is called in the loadUI method of the LibraryUI class
     * |
     * .........................................................................
     *
     */
    public final void loadAddGameUI() {

        // Create Components
        // ----------------------------------------------------------------.

        //* Get Glass Pane to Put UI On *//
        pnlGlass = (JPanel) coreUI.getFrame().getGlassPane();
        pnlAddGamePane = new AImagePane("addUI_bg.png",
                new BorderLayout());

        //* TOP PANEL COMPONENTS *//
        pnlTopPane_addUI = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 4));
        pnlTopPane_addUI.setOpaque(false);
        btnClose_addUI = new AButton("addUI_btnClose_norm.png",
                "addUI_btnClose_down.png", "addUI_btnClose_over.png");


        btnGoToSteam = new AButton("addUI_btnGoToSteam_norm.png",
                "addUI_btnGoToSteam_down.png", "addUI_btnGoToSteam_over.png");
        btnGoToSteam.setBorder(null);
        btnGoToSteam.setMargin(new Insets(0, 0, 0, 0));
        btnGoToSteam.addActionListener(new GoToSteamListener());


        if (coreUI.getOS().contains("Mac")) {
            btnGoToProgram = new AButton("addUI_btnGoToApps_norm.png",
                    "addUI_btnGoToApps_down.png",
                    "addUI_btnGoToApps_over.png");
        } else {
            btnGoToProgram = new AButton("addUI_btnGoToPrograms_norm.png",
                    "addUI_btnGoToPrograms_down.png",
                    "addUI_btnGoToPrograms_over.png");
        }

        btnGoToProgram.setBorder(null);
        btnGoToProgram.setMargin(new Insets(0, 0, 0, 0));

        btnGoToProgram.addActionListener(new GoToProgramsListener());




        //* CENTRAL PANEL COMPONENTS *//
        pnlCenter_addUI = new JPanel(new BorderLayout());
        pnlCenter_addUI.setOpaque(false);

        pnlTopOfCenter = new JPanel(new BorderLayout());
        pnlTopOfCenter.setOpaque(false);

        pnlLeftOfTopCenter = new AImagePane("addUI_status_container.png",
                new FlowLayout(FlowLayout.LEFT));
        pnlRightOfTop = new AImagePane("addUI_status_container.png",
                new FlowLayout(FlowLayout.LEFT, 0, 5));

        pnlRightOfTopEast = new JPanel(new BorderLayout(0, 0));
        pnlRightOfTopEast.setOpaque(false);


        pnlRightOfTopEastContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT,
                0, 0));
        pnlRightOfTopEastContainer.setOpaque(false);



        pnlLeftOfBottom = new JPanel(new FlowLayout(FlowLayout.LEFT,
                0, 0));
        pnlLeftOfBottom.setOpaque(false);
        pnlRightOfBottom = new JPanel(new FlowLayout(FlowLayout.CENTER,
                0, 0));
        pnlRightOfBottom.setOpaque(true);
        pnlRightOfBottomContainer = new JPanel(new FlowLayout(FlowLayout.LEFT,
                0, 0));
        pnlRightOfBottomContainer.setOpaque(false);
        pnlAddGameContainer = new JPanel(new BorderLayout(0, 0));
        pnlAddGameContainer.setOpaque(false);


        lblLeftTitle = new JLabel("Name");
        lblRightTitle = new JLabel("Location");

        statusBadge1 = new AImage("addUI_badge_idle.png");
        statusBadge2 = new AImage("addUI_badge_idle.png");

        pnlCoverPane = new AImagePane("addUI_game_bg.png",
                new FlowLayout(FlowLayout.RIGHT, -10, 10));
        pnlBlankCoverGame = new AImagePane("Blank-Case.png", 240, 260);
        gamesList = new JList();
        gameFileChooser = new JFileChooser(System.getProperty("user.home"));

        // Set up File Chooser UI //

        try {
            UIManager.setLookAndFeel(UIManager
                    .getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger
                    .getLogger(LibraryUI.class.getName()).
                    log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger
                    .getLogger(LibraryUI.class.getName()).
                    log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger
                    .getLogger(LibraryUI.class.getName()).
                    log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger
                    .getLogger(LibraryUI.class.getName()).
                    log(Level.SEVERE, null, ex);
        }

        //* Set up File Chooser *//
        SwingUtilities.updateComponentTreeUI(gameFileChooser);

        gameFileChooser.setApproveButtonText("Select");
        gameFileChooser.setDragEnabled(false);
        gameFileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        gameFileChooser.setMultiSelectionEnabled(false);
        gameFileChooser.setAcceptAllFileFilterUsed(true);
        gameFileChooser.setEnabled(true);
        gameFileChooser.revalidate();


        //* BOTTOM PANEL COMPONENTS *//
        pnlBottomPane = new JPanel(new BorderLayout(0, 20));
        pnlBottomPane.setOpaque(false);
        pnTopOfBottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnTopOfBottom.setOpaque(false);
        pnlBottomOfCenter = new JPanel(new BorderLayout());
        pnlBottomOfCenter.setOpaque(false);
        pnlSearchBG = new AImagePane("addUI_text_inactive.png",
                new FlowLayout(FlowLayout.RIGHT, 5, -1));

        addGameSearchField = new JTextField("Search For Game To Add...");
        pnlAddGameSearchContainer = new JPanel(new FlowLayout(
                FlowLayout.CENTER));
        pnlAddGameSearchContainer.setOpaque(false);

        addGameToLibButton = new AButton("addUI_btnAdd_norm.png",
                "addUI_btnAdd_down.png", "addUI_btnAdd_over.png");
        addGameToLibButton.setVisible(false);

    }

    /**
     * .-----------------------------------------------------------------------.
     * | buildAddGameUI()
     * .-----------------------------------------------------------------------.
     * |
     * | This method builds the entire AddGameUI overlay that pops up when
     * | a new game is to be added.
     * |
     * | This UI is loaded the first time a new game is to be added, but never
     * | reloaded again.
     * |
     * .........................................................................
     *
     */
    public final void buildAddGameUI() {

        if (!isAddGameUILoaded) {

            // Set Up Components
            // ----------------------------------------------------------------.


            //* CENTRAL PANEL COMPONENTS *//

            //*
            // Set Up Title labels for both Left
            // and Right side of the Central Panel
            //*
            lblLeftTitle.setFont(coreUI.getDefaultFont().deriveFont(Font.BOLD,
                    33));
            lblLeftTitle.setForeground(Color.lightGray);
            lblRightTitle.setFont(coreUI.getDefaultFont().deriveFont(Font.BOLD,
                    33));
            lblRightTitle.setForeground(Color.lightGray);


            //* Set Up Panels containing the Game Cover Art *//
            pnlCoverPane.setPreferredSize(new Dimension(pnlCoverPane
                    .getImgIcon()
                    .getIconWidth(), pnlCoverPane.getImgIcon().getIconHeight()));

            //*
            // Set Up 2 Panels containing the Left and
            // Right titles at the top of the Content panel
            //*
            pnlLeftOfTopCenter.setPreferredSize(new Dimension(pnlAddGamePane
                    .getImgIcon().getIconWidth() / 2, 75));
            pnlRightOfTop.setPreferredSize(new Dimension(pnlAddGamePane
                    .getImgIcon().getIconWidth() / 2, 75));


            pnlLeftOfBottom
                    .setPreferredSize(new Dimension(pnlAddGamePane
                    .getImgIcon().getIconWidth() / 2 - 10,
                    pnlCoverPane
                    .getImgIcon().getIconHeight()));
            pnlRightOfBottom
                    .setPreferredSize(new Dimension(pnlAddGamePane
                    .getImgIcon().getIconWidth() / 2 - 10,
                    pnlCoverPane
                    .getImgIcon().getIconHeight()));
            pnlRightOfBottom.setBackground(new Color(38, 46, 60));

            pnlRightOfBottomContainer
                    .setPreferredSize(new Dimension(pnlAddGamePane
                    .getImgIcon().getIconWidth() / 2,
                    pnlCoverPane
                    .getImgIcon().getIconHeight()));

            pnlBlankCoverGame.setPreferredSize(new Dimension(240, 260));



            // Set up Go To Shortcuts //

            int rightOfTopEastWidth;
            // Check that steam exists before showing the
            if (logic.fetchSteamDirOnWindows() != null) {
                pnlRightOfTopEastContainer.add(btnGoToSteam);
                rightOfTopEastWidth = 4;
            } else {
                rightOfTopEastWidth = 3;
            }

            // Add Go To Program

            pnlRightOfTopEastContainer.add(btnGoToProgram);

            pnlRightOfTopEast.add(Box.createHorizontalStrut(pnlRightOfTop
                    .getPreferredSize().width
                                                            / rightOfTopEastWidth));
            pnlRightOfTopEast.add(pnlRightOfTopEastContainer, BorderLayout.EAST);


            // Set up Game List //

            gamesList.setPreferredSize(
                    new Dimension(pnlCoverPane.getImgIcon().getIconWidth() + 80,
                    pnlCoverPane.getImgIcon().getIconHeight()));
            gamesList.setBackground(new Color(38, 46, 60));
            gamesList.setForeground(Color.lightGray);
            gamesList.setFont(coreUI.getDefaultFont().deriveFont(Font.BOLD,
                    listFontSize));
            gamesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            gamesList.setLayoutOrientation(JList.VERTICAL);
            gamesList.setVisibleRowCount(10);

            //* List model for JList Containing Game Names *//
            listModel = new DefaultListModel();
            gamesList.setModel(listModel);



            try {
                Field field = PopupFactory.class.getDeclaredField(
                        "forceHeavyWeightPopupKey");
                field.setAccessible(true);
                gameFileChooser.putClientProperty(field.get(null), true);
            } catch (Exception e) {
                logger.error(e);
            }



            if (logger.isDebugEnabled()) {
                logger.debug("Cover Pane Height: " + pnlCoverPane.getHeight());
            }

            gameFileChooser.setPreferredSize(new Dimension(pnlAddGamePane
                    .getImgIcon().getIconWidth() / 2 - 10, pnlCoverPane
                    .getImgIcon().getIconHeight()));


            //* BOTTOM PANEL COMPONENTS *//


            //* Set Up Textfield where user will search for game to add *//
            addGameSearchField.setFont(coreUI.getRopaFont().deriveFont(
                    Font.PLAIN,
                    addGameFontSize - 3));
            addGameSearchField.setForeground(Color.gray);
            addGameSearchField.setOpaque(false);
            addGameSearchField.setBorder(BorderFactory.createEmptyBorder());
            addGameSearchField.setBorder(null);

            addGameSearchField.setPreferredSize(new Dimension(500, 50));

            //* Set up image sizes for the Search box *//
            pnTopOfBottom.setPreferredSize(new Dimension(pnlAddGamePane
                    .getImgIcon()
                    .getIconWidth(), 20));
            pnlSearchBG.setPreferredSize(new Dimension(pnlSearchBG.getImgIcon()
                    .getIconWidth(), pnlSearchBG.getImgIcon().getIconHeight()));


            //* Set up glass panel *//
            pnlGlass.setVisible(true);
            pnlGlass.setLayout(null);
            //* Set Location for Add Game UI panels *//
            pnlAddGamePane.setLocation((coreUI.getFrame().getWidth() / 2)
                                       - (pnlAddGamePane.getImgIcon()
                    .getIconWidth()
                                          / 2), -380);
            pnlAddGamePane
                    .setSize(
                    new Dimension(pnlAddGamePane.getImgIcon()
                    .getIconWidth(), pnlAddGamePane.getImgIcon().getIconHeight()));
            pnlAddGamePane.revalidate();

            addGameToLibButton.setLocation((coreUI.getFrame().getWidth() / 2)
                                           - addGameToLibButton.getWidth() / 2,
                    pnlAddGamePane
                    .getImgIcon()
                    .getIconHeight() - 90);
            addGameToLibButton.setSize(new Dimension(340, 140));


            // Add to Components
            // ----------------------------------------------------------------.

            //* TOP PANEL COMPONENTS *//

            //* Add the Close button to the Top most Panel *//
            pnlTopPane_addUI.add(btnClose_addUI);


            //* BOTTOM PANEL COMPONENTS *//

            //* Add components to form the Search Box *//
            pnlSearchBG.add(addGameSearchField, BorderLayout.CENTER);
            pnlAddGameSearchContainer.add(pnlSearchBG);

            //* Add UI elements to the Bottom Panel in the Add Game UI *//
            pnlBottomPane
                    .add(pnlAddGameSearchContainer, BorderLayout.PAGE_START);

            //* CENTRAL PANEL COMPONENTS *//

            //* Add the Titles and Badges at the Top of the CENTRAL panel *//
            pnlLeftOfTopCenter.add(statusBadge1);
            pnlLeftOfTopCenter.add(lblLeftTitle);
            pnlRightOfTop.add(statusBadge2);
            pnlRightOfTop.add(lblRightTitle);
            pnlRightOfTop.add(pnlRightOfTopEast);

            pnlCoverPane.add(pnlBlankCoverGame, BorderLayout.SOUTH);
            pnlLeftOfBottom.add(pnlCoverPane);
            pnlLeftOfBottom.add(gamesList);

            //* Add the main Content to the Central Panel *//

            pnlRightOfBottom.add(gameFileChooser);
            pnlRightOfBottomContainer
                    .add(pnlRightOfBottom);

            pnlAddGameContainer.add(pnlLeftOfBottom, BorderLayout.WEST);
            pnlAddGameContainer
                    .add(pnlRightOfBottomContainer, BorderLayout.EAST);

            pnlTopOfCenter.add(pnlLeftOfTopCenter, BorderLayout.WEST);
            pnlTopOfCenter.add(pnlRightOfTop, BorderLayout.EAST);

            pnlCenter_addUI.add(pnlTopOfCenter, BorderLayout.NORTH);
            pnlCenter_addUI.add(pnlAddGameContainer, BorderLayout.CENTER);

            //*
            // Add the TOP the CENTER and the BOTTOM
            // panels to the Add Game UI
            //*
            pnlAddGamePane.add(pnlTopPane_addUI, BorderLayout.PAGE_START);
            pnlAddGamePane.add(pnlCenter_addUI, BorderLayout.CENTER);
            pnlAddGamePane.add(pnlBottomPane, BorderLayout.SOUTH);

            pnlGlass.add(pnlAddGamePane);
            pnlGlass.add(addGameToLibButton);



            // Handlers
            // ----------------------------------------------------------------.

            btnClose_addUI.addActionListener(handler.new HideGameAddUIHandler(
                    this));

            pnlAddGamePane.addMouseListener(handler.new EmptyMouseHandler());
            addGameSearchField
                    .addFocusListener(handler.new addGameFocusHandler());
            addGameSearchField
                    .addMouseListener(handler.new addGameMouseHandler());
            addGameSearchField
                    .addKeyListener(handler.new addGameSearchBoxHandler());
            gamesList.addListSelectionListener(handler.new SelectListHandler());
            gameFileChooser.setFileFilter(
                    handler.new ExecutableFilterHandler());
            gameFileChooser
                    .addActionListener(handler.new ExecutableChooserHandler(
                    gameFileChooser));
            addGameToLibButton
                    .addActionListener(handler.new AddToLibraryHandler());


            // Finalize
            // ----------------------------------------------------------------.

            pnlAddGamePane.setVisible(false);

            addGameToLibButton.revalidate();
            pnlAddGamePane.revalidate();

            isAddGameUILoaded = true;
        }
    }

    public void loadEditGameUI() {


        // Create Components
        // ----------------------------------------------------------------.

        //* Get Glass Pane to Put UI On *//
        pnlGlass = (JPanel) coreUI.getFrame().getGlassPane();
        pnlEditGamePane = new AImagePane("editUI_bg.png",
                new BorderLayout());

        //* TOP PANEL COMPONENTS *//
        pnlTopPane_editUI = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 4));
        pnlTopPane_editUI.setOpaque(false);
        btnClose_editUI = new AButton("addUI_btnClose_norm.png",
                "addUI_btnClose_down.png", "addUI_btnClose_over.png");

    }

    public void buildEditGameUI() {
        if (!isEditUILoaded) {


            // Set Up Components
            // ----------------------------------------------------------------.

            //* Set up glass panel *//
            pnlGlass.setVisible(true);
            pnlGlass.setLayout(null);

            //* Set Location for Edit Game UI panels *//
            pnlEditGamePane.setLocation((coreUI.getFrame().getWidth() / 2)
                                        - (pnlAddGamePane.getImgIcon()
                    .getIconWidth() / 2), -380);
            pnlEditGamePane
                    .setSize(
                    new Dimension(pnlAddGamePane.getImgIcon()
                    .getIconWidth(), pnlAddGamePane.getImgIcon().getIconHeight()));
            pnlEditGamePane.revalidate();


            // Add to Components
            // ----------------------------------------------------------------.

            //* TOP PANEL COMPONENTS *//

            //* Add the Close button to the Top most Panel *//
            pnlTopPane_editUI.add(btnClose_editUI);

            //-
            // Add major panels to component
            //-
            pnlEditGamePane.add(pnlTopPane_editUI, BorderLayout.PAGE_START);

            pnlGlass.add(pnlEditGamePane);



            // Handlers
            // ----------------------------------------------------------------.

            btnClose_editUI.addActionListener(handler.new HideEditAddUIHandler(
                    this));




            isEditUILoaded = true;
        }


    }

    private void loadOrganizeUI() {

        organizeMenu = new APopupMenu();
        organizeMenu.setOpaque(false);

        // Background Panes //

        btnTop = new ARadioButton("library_organize_top.png",
                "library_organize_top_selected.png");
        btnTop.setLayout(
                new FlowLayout(FlowLayout.CENTER));
        btnTop.setPreferredSize(new Dimension(btnTop.getRealImageWidth(), btnTop
                .getRealImageHeight()));




        btnMiddle = new ARadioButton("library_organize_middle.png",
                "library_organize_middle_selected.png");
        btnMiddle.setLayout(
                new FlowLayout(FlowLayout.CENTER));
        btnMiddle.setPreferredSize(new Dimension(btnMiddle.getRealImageWidth(),
                btnMiddle.getRealImageHeight()));




        btnBottom = new ARadioButton("library_organize_bottom.png",
                "library_organize_bottom_selected.png");
        btnBottom.setLayout(
                new FlowLayout(FlowLayout.CENTER));
        btnBottom.setPreferredSize(new Dimension(btnBottom.getRealImageWidth(),
                btnBottom.getRealImageHeight()));


        organizeBtnManager = new ARadioButtonManager();
        organizeBtnManager.addButton(btnBottom);
        organizeBtnManager.addButton(btnMiddle);
        organizeBtnManager.addButton(btnTop);
        organizeBtnManager.setRadioButton();


        // Labels //

        lblFavorite = new ASlickLabel("Favorite");
        lblFavorite.setFont(getCoreUI().getRopaFont().deriveFont(
                Font.PLAIN, 19));
        lblFavorite.setForeground(new Color(173, 173, 173));

        lblAlphabetic = new ASlickLabel("Alphabetic");
        lblAlphabetic.setFont(getCoreUI().getRopaFont()
                .deriveFont(
                Font.PLAIN, 19));
        lblAlphabetic.setForeground(new Color(173, 173, 173));

        lblMostPlayed = new ASlickLabel("Most Played");
        lblMostPlayed.setFont(getCoreUI().getRopaFont()
                .deriveFont(
                Font.PLAIN, 19));
        lblMostPlayed.setForeground(new Color(173, 173, 173));

        // Icons //
        icoFavorite = new AImage("library_organize_favorite.png");

        icoAlphabetic = new AImage("library_organize_alphabetic.png");

        icoMostPlayed = new AImage("library_organize_mostPlayed.png");


        // Containers //
        favoritePane = new JPanel(new FlowLayout(FlowLayout.LEFT, 10,
                2));
        favoritePane.setPreferredSize(new Dimension(btnBottom
                .getRealImageWidth(),
                btnBottom.getRealImageHeight()));
        favoritePane.setOpaque(false);

        alphabeticPane = new JPanel(new FlowLayout(FlowLayout.LEFT,
                10, 2));
        alphabeticPane.setPreferredSize(new Dimension(btnBottom
                .getRealImageWidth(),
                btnBottom.getRealImageHeight()));
        alphabeticPane.setOpaque(false);

        mostplayedPane = new JPanel(new FlowLayout(FlowLayout.LEFT,
                10, 2));
        mostplayedPane.setPreferredSize(new Dimension(btnBottom
                .getRealImageWidth(),
                btnBottom.getRealImageHeight()));
        mostplayedPane.setOpaque(false);


        // Handlers //
        btnTop.addMouseListener(handler.new OrganizeMouseListener(
                lblFavorite));
        btnTop.setSelectedHandler(handler.new SelectedOrganizeListener(
                lblFavorite,
                storage.getStoredSettings(),
                "Favorite"));
        btnTop.setUnSelectedHandler(handler.new UnSelectedOrganizeListener(
                lblFavorite, organizeMenu));


        btnMiddle.addMouseListener(
                handler.new OrganizeMouseListener(lblAlphabetic));
        btnMiddle.setSelectedHandler(
                handler.new SelectedOrganizeListener(lblAlphabetic, storage
                .getStoredSettings(), "Alphabetic"));
        btnMiddle.setUnSelectedHandler(handler.new UnSelectedOrganizeListener(
                lblAlphabetic, organizeMenu));

        btnBottom.addMouseListener(handler.new OrganizeMouseListener(
                lblMostPlayed));
        btnBottom.setSelectedHandler(
                handler.new SelectedOrganizeListener(lblMostPlayed, storage
                .getStoredSettings(), "Most Played"));
        btnBottom.setUnSelectedHandler(handler.new UnSelectedOrganizeListener(
                lblMostPlayed, organizeMenu));

        // Add to panels //

        favoritePane.add(icoFavorite);
        favoritePane.add(lblFavorite);

        btnTop.add(favoritePane);


        alphabeticPane.add(icoAlphabetic);
        alphabeticPane.add(lblAlphabetic);

        btnMiddle.add(alphabeticPane);


        mostplayedPane.add(icoMostPlayed);
        mostplayedPane.add(lblMostPlayed);

        btnBottom.add(mostplayedPane);


        organizeMenu.add(btnTop);
        organizeMenu.add(btnMiddle);
        organizeMenu.add(btnBottom);
    }

    public void showOrganizeUI() {
        ASound organizeSFX = new ASound("tick_2.wav", false);
        organizeSFX.Play();

        // States //

        if (!btnTop.isSelected && !btnMiddle.isSelected && !btnBottom.isSelected) {
            String value = storage.getStoredSettings().getSettingValue(
                    "organize");
            if (value != null) {
                if (value.equals("Favorite")) {
                    btnTop.setSelected();

                } else if (value.equals("Alphabetic")) {
                    btnMiddle.setSelected();

                } else if (value.equals("Most Played")) {
                    btnBottom.setSelected();
                }
            } else {

                storage.getStoredSettings().saveSetting("organize", "favorite");
                value = "Favorite";
                btnTop.setSelected();
            }
        }



        organizeMenu
                .show(getCoreUI().getFrame(), btnOrganizeGames
                .getLocationOnScreen().x + ((btnOrganizeGames.getBounds().width)
                                            / 3
                                            - (btnOrganizeGames.getBounds().width)
                                              / 5)
                                              - 3,
                btnOrganizeGames.getLocationOnScreen().y - btnOrganizeGames
                .getBounds().height
                - btnMiddle
                .getRealImageHeight());


    }

    public void showAddGameUI() {

        if (isEditGameUI_Visible()) {
            hideEditGameUI();
            showAddGameUI();
        } else if (isAddGameUI_Visible()) {

            hideAddGameUI();

        } else {
            pnlGlass.setVisible(true);


            addGameAnimator = new AAnimate(pnlAddGamePane);

            int num = 1 + (int) (Math.random() * ((3 - 1) + 1));
            ASound showSound = new ASound("swoop_" + num + ".wav", false);
            showSound.Play();

            AThreadWorker addGameWorker = new AThreadWorker(
                    new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    buildAddGameUI();
                    addGameAnimator.setInitialLocation(
                            (coreUI.getFrame().getWidth() / 2)
                            - (pnlAddGamePane.getImgIcon()
                            .getIconWidth() / 2), -390);
                }
            }, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //* Animate Down Add Game UI *//

                    addGameAnimator.moveVertical(0, 33);
                    pnlAddGamePane.revalidate();

                    addGameSearchField.setFocusable(true);

                    addGameAnimator.addPostAnimationListener(
                            new APostHandler() {
                        @Override
                        public void postAction() {
                            addGameUI_Visible = true;
                            addGameSearchField.requestFocus();
                        }
                    });
                }
            });

            addGameWorker.startOnce();

        }
    }

    public void hideAddGameUI() {
        if (addGameUI_Visible == true) {
            addGameUI_Visible = false;
            try {
                addGameToLibButton.setVisible(false);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            int num = 1 + (int) (Math.random() * ((3 - 1) + 1));
            ASound showSound = new ASound("reverse_swoop_" + num + ".wav", false);
            showSound.Play();

            //* Animate Up Add Game UI *//
            addGameAnimator.moveVertical(-492, 35);
            addGameAnimator.addPostAnimationListener(new APostHandler() {
                @Override
                public void postAction() {
                    pnlGlass.setVisible(false);
                }
            });

            statusBadge2.setImgURl("addUI_badge_invalid.png");
            addGameAnimator.removeAllListeners();
            addGameToLibButton.setVisible(false);
            addGameToLibButton.repaint();

            //TODO Fix Search Bar Focus
            searchTextField.requestFocus();
            coreUI.getFrame().requestFocus();
            searchTextField.requestFocus();
            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                logger.error(ex);
            }

            coreUI.getFrame().requestFocus();

            addGameAnimator = null;
        }
    }

    public void showEditGameUI() {

        if (isAddGameUI_Visible()) {
            hideAddGameUI();
            showEditGameUI();
        } else if (isEditGameUI_Visible()) {
            hideEditGameUI();
        } else {

            pnlGlass.setVisible(true);
            editGameUI_Visible = true;

            editGameAnimator = new AAnimate(pnlEditGamePane);

            int num = 1 + (int) (Math.random() * ((3 - 1) + 1));
            ASound showSound = new ASound("swoop_" + num + ".wav", false);
            showSound.Play();

            AThreadWorker editGameWorker = new AThreadWorker(
                    new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    buildEditGameUI();

                }
            }, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //* Animate Down Add Game UI *//
                    editGameAnimator.setInitialLocation(
                            (coreUI.getFrame().getWidth() / 2)
                            - (pnlEditGamePane.getImgIcon()
                            .getIconWidth() / 2), -390);
                    editGameAnimator.moveVertical(0, 33);
                    pnlEditGamePane.revalidate();

                }
            });

            editGameWorker.startOnce();
        }

    }

    public boolean isEditGameUI_Visible() {
        return editGameUI_Visible;
    }

    public void hideEditGameUI() {

        if (editGameUI_Visible == true) {

            editGameUI_Visible = false;

            int num = 1 + (int) (Math.random() * ((3 - 1) + 1));
            ASound showSound = new ASound("reverse_swoop_" + num + ".wav", false);
            showSound.Play();

            //* Animate Up Add Game UI *//
            editGameAnimator.moveVertical(-492, 35);
            editGameAnimator.addPostAnimationListener(new APostHandler() {
                @Override
                public void postAction() {
                    pnlGlass.setVisible(false);
                }
            });

            editGameAnimator.removeAllListeners();

            searchTextField.requestFocus();
            coreUI.getFrame().requestFocus();
            searchTextField.requestFocus();
            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                logger.error(ex);
            }

            coreUI.getFrame().requestFocus();
        }

    }

    public void moveGridLeft() {

        if (logger.isDebugEnabled()) {
            logger.debug("Left key pressed");
        }

        if (!GridAnimate.getAnimator1().isAnimating() && !GridAnimate
                .getAnimator2().isAnimating()) {

            //*
            // Get The Index of The Current Panel Being Displayed
            // Refer too GridManager array of All panels to find it
            //*
            currentIndex = GridSplit.getArray()
                    .indexOf(paneLibraryContainer.getComponent(1));

            //* Stop from going to far left *//
            if (currentIndex - 1 == -1) {
                currentIndex = 1;
                btnGameLeft.mouseExit();
            }


            if (currentIndex < GridSplit.getArray().size()) {

                GridSplit.decrementVisibleGridIndex();
                //Clear Panel
                if (currentIndex - 1 <= 0) {
                    //Far Left Image
                    paneLibraryContainer.remove(0);
                    paneLibraryContainer.add(imgOrganizeTypeSideBar,
                            BorderLayout.WEST, 0);

                } else {
                    //Left Button
                    paneLibraryContainer.remove(0);
                    paneLibraryContainer.add(btnGameLeft, BorderLayout.WEST, 0);
                }
                //Add GameCover Covers

                GridAnimate.moveLeft(currentIndex);

                paneLibraryContainer.add(BorderLayout.EAST, btnGameRight);

                try {
                    logic.loadGames(currentIndex - 1);
                } catch (MalformedURLException ex) {
                    logger.error(ex);
                }
            }

            coreUI.getCenterPanel().removeAll();
            coreUI.getCenterPanel().add(BorderLayout.CENTER,
                    paneLibraryContainer);

            paneLibraryContainer.repaint();
            paneLibraryContainer.revalidate();

            coreUI.getFrame().requestFocus();

        }
        btnGameLeft.mouseExit();
    }

    public void moveGridRight() {

        if (logger.isDebugEnabled()) {
            logger.debug("Right key pressed");
        }

        if (!GridAnimate.getAnimator1().isAnimating() && !GridAnimate
                .getAnimator2().isAnimating()) {

            currentIndex = GridSplit.getArray()
                    .indexOf(paneLibraryContainer.getComponent(1));


            if (currentIndex < GridSplit.getArray().size() - 1) {

                GridSplit.incrementVisibleGridIndex();

                paneLibraryContainer.remove(0);
                paneLibraryContainer.add(btnGameLeft, BorderLayout.WEST, 0);


                paneLibraryContainer.add(btnGameRight, BorderLayout.EAST, 2);

                GridAnimate.moveRight(currentIndex);


                try {
                    logic.loadGames(currentIndex + 1);
                } catch (MalformedURLException ex) {
                    logger.error(ex);
                }



                //of on last Grid then dont show right arrow button
                if (!(currentIndex + 1 < GridSplit.getArray().size() - 1)) {

                    paneLibraryContainer.remove(btnGameRight);
                    paneLibraryContainer.add(Box.createHorizontalStrut(140),
                            BorderLayout.EAST, 2);
                    btnGameRight.mouseExit();
                }
            }

            coreUI.getCenterPanel().removeAll();
            coreUI.getCenterPanel().add(BorderLayout.CENTER,
                    paneLibraryContainer);

            paneLibraryContainer.repaint();
            paneLibraryContainer.revalidate();

            coreUI.getFrame().requestFocus();

            currentIndex = GridSplit.getArray()
                    .indexOf(paneLibraryContainer.getComponent(1));

        }
        btnGameRight.mouseExit();
    }

    @Override
    public final void closeApp() {
        if (isAddGameUI_Visible()) {
            hideAddGameUI();
        }
        if (isEditGameUI_Visible()) {
            hideEditGameUI();
        }
    }

    public void setSize() {

        double Ratio = ((double) coreUI.getFrame().getWidth()
                        / (double) coreUI.getFrame().getHeight());

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
            SearchBarWidth = 880;
            listFontSize = 19;
            gridSearchFontSize = 35;
            addGameFontSize = 28;
            bottomTopPadding = 10;

        } else {
            gameCoverHeight = (int) ((coreUI.getFrame().getHeight()
                                      + (coreUI.getTopPanelHeight() * 2))
                                     / (Ratio * 2.5));
            gameCoverWidth = (int) ((coreUI.getFrame().getWidth()
                                     + coreUI.getTopPanelHeight()) / (Ratio
                                                                      * 3.5));
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

    /**
     * Listener for the btnGoToProgram button to make gameFileChooser point to
     * the Programs folder based on the OS
     */
    private class GoToProgramsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            // Set File Choosers  location to the Programs/App folder //

            String gotToPath = System.getProperty("user.dir");

            if (coreUI.getOS().contains("Windows")) {

                // Check which Programs folder to use, Use x86 one if possible//

                if (System.getenv("ProgramFiles(x86)") != null) {

                    gotToPath = System.getenv("ProgramFiles(x86)");

                } else if (System.getenv("ProgramFiles") != null) {

                    gotToPath = System.getenv("ProgramFiles");

                }


            } else if (coreUI.getOS().contains("Mac")) {
                gotToPath = "/Applications/";
            } else {
                gotToPath = "";
            }

            // Set appropriate path, will fall back to user.dir //
            gameFileChooser.setCurrentDirectory(new File(gotToPath));
        }
    }

    /**
     * Listener for the btnGoToProgram button to make gameFileChooser point to
     * the Steam games folder.
     */
    private class GoToSteamListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Set File Choosers location to folder containing Steam Games //

            if (coreUI.getOS().contains("Windows")) {
                gameFileChooser.setCurrentDirectory(logic
                        .fetchSteamDirOnWindows());
            } else if (coreUI.getOS().contains("Mac")) {
                if (AFileManager
                        .checkFile("/Applications/Steam/steamapp/common")) {
                    gameFileChooser.setCurrentDirectory(new File(
                            "/Applications/Steam/steamapp/common"));
                }
            } else {
                gameFileChooser.setCurrentDirectory(null);
            }

            coreUI.getFrame().repaint();


        }
    }

    // Getters and Setters
    // ----------------------------------------------------------------.
    public AAnimate getAddGameAnimator() {
        return addGameAnimator;
    }

    public AFadeLabel getLblLibraryStatus() {
        return lblLibraryStatus;
    }

    public boolean IsGameLibraryKeyListenerAdded() {
        return isGameLibraryKeyListenerAdded;
    }

    public final void setIsGameLibraryKeyListenerAdded(
            boolean isGameLibraryKeyListenerAdded) {
        this.isGameLibraryKeyListenerAdded = isGameLibraryKeyListenerAdded;
    }

    public HoverButtonLeft getMoveLibraryLeftHandler() {
        return moveLibraryLeftHandler;
    }

    public HoverButtonRight getMoveLibraryRightHandler() {
        return moveLibraryRightHandler;
    }

    public AButton getAddGameToLibButton() {
        return addGameToLibButton;
    }

    public AAnimate getAddGameToLibButtonAnimator() {
        return addGameToLibButtonAnimator;
    }

    public JPanel getAddSearchContainer() {
        return pnlAddGameSearchContainer;
    }

    public JPanel getBottomCenterPane() {
        return pnlBottomOfCenter;
    }

    public JFileChooser getGameLocator() {
        return gameFileChooser;
    }

    public LibraryHandler getHandler() {
        return handler;
    }

    public AImagePane getPnlBlankCoverGame() {
        return pnlBlankCoverGame;
    }

    public AButton getRemoveSearchButton() {
        return removeSearchButton;
    }

    public JTextField getGameSearchBar() {
        return addGameSearchField;
    }

    public ASimpleDB getCoverDB() {
        return CoverDB;
    }

    public DefaultListModel getListModel() {
        return listModel;
    }

    public JList getGamesList() {
        return gamesList;
    }

    public JScrollPane getListScrollPane() {
        return gameScrollPane;
    }

    public AImage getStatusBadge1() {
        return statusBadge1;
    }

    public AImage getStatusBadge2() {
        return statusBadge2;
    }

    public AImagePane getAddGamePane() {
        return pnlAddGamePane;
    }

    public AImagePane getCoverPane() {
        return pnlCoverPane;
    }

    public void setCoverGame(AImagePane coverGame) {
        this.pnlBlankCoverGame = coverGame;
    }

    public AImagePane getCoverGame() {
        return pnlBlankCoverGame;
    }

    public JPanel getBottomPane() {
        return pnlBottomPane;
    }

    public AImagePane getPnlSearchBG() {
        return pnlSearchBG;
    }

    public JTextField getSearchText() {
        return addGameSearchField;
    }

    public JPanel getButtonPane() {
        return pnlSearchButton;
    }

    public JPanel getGamesContainer() {
        return paneLibraryContainer;
    }

    public GridAnimation getGridAnimate() {
        return GridAnimate;
    }

    public GridManager getGridSplit() {
        return GridSplit;
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
        return searchTextField;
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

    public AButton getZoomM() {
        return btnZoomLess;
    }

    public AButton getZoomP() {
        return btnZoomPlus;
    }

    public AButton getBtnAddGame() {
        return btnShowAddGameUI;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int index) {
        currentIndex = index;
    }

    public String getCurrentPath() {
        return currentPath;
    }

    public void setCurrentPath(String path) {
        currentPath = path;
    }

    public ArrayList<AImagePane> getGameCover() {
        return gameCover;
    }

    public AImage getImgOrganizeType() {
        return imgOrganizeTypeSideBar;
    }

    public AHoverButton getImgGameLeft() {
        return btnGameLeft;
    }

    public AHoverButton getImgGameRight() {
        return btnGameRight;
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

    public boolean isAddGameUI_Visible() {
        return addGameUI_Visible;
    }

    public int getZoom() {
        return zoom;
    }

    public AuroraCoreUI getCoreUI() {
        return coreUI;
    }

    public DashboardUI getDashboardUI() {
        return dashboardUI;
    }

    public LibraryLogic getLogic() {
        return logic;
    }
}
