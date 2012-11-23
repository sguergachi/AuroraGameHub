/*
 * Copyright 2012 Sardonix Creative.
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
import aurora.V1.core.screen_handler.GameLibraryHandler;
import aurora.V1.core.screen_handler.GameLibraryHandler.GameLibraryKeyListener;
import aurora.V1.core.screen_handler.GameLibraryHandler.HoverButtonLeft;
import aurora.V1.core.screen_handler.GameLibraryHandler.HoverButtonRight;
import aurora.V1.core.screen_handler.GameLibraryHandler.MoveToLastGrid;
import aurora.V1.core.screen_handler.GameLibraryHandler.ShowAddGameUiHandler;
import aurora.V1.core.screen_handler.GameLibraryHandler.searchBoxHandler;
import aurora.V1.core.screen_handler.GameLibraryHandler.searchFocusHandler;
import aurora.V1.core.screen_logic.GameLibraryLogic;
import aurora.engine.V1.Logic.*;
import aurora.engine.V1.UI.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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

/**
 * .---------------------------------------------------------------------------.
 * | GameLibraryUI :: Aurora App Class
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
 * @author Sammy <sguergachi at gmail.com> carlos <camachado@gmail.com>
 * <p/>
 */
public class GameLibraryUI extends AuroraApp {

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
    private AButton btnCloseAddUI;

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
     * Panel Containing btnShowAddGameUI.
     */
    private JPanel pnlShowAddGameContainer;

    /**
     * Panel Containing imgSelectedGamePane.
     */
    private JPanel pnlSelectedGameContainer;

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
    private JPanel pnlCenter;

    /**
     * AddGameUI Panel Containing Top part of Bottom Panel.
     */
    private JPanel pnTopOfBottom;

    /**
     * AddGameUI Panel Containing Top part of Center Panel.
     */
    private JPanel pnlTopOfCenter;

    /**
     * AddGameUI Panel Containing Left part of Center Panel.
     */
    private JPanel pnlLeftOfTopCenter;

    /**
     * AddGameUI Panel Containing Right part of Top Part of Center Panel.
     */
    private JPanel pnlRightOfTopOfCenter;

    /**
     * AddGameUI Panel Containing Top part of Panel.
     */
    private JPanel pnlTopPane;

    /**
     * AddGameUI Panel Containing Left part of Bottom Part of Center Panel.
     */
    private JPanel pnlLeftOfBottomOfCenter;

    /**
     * AddGameUI Panel Containing Right part of Bottom Part of Center Panel.
     */
    private JPanel pnlRightOfBottomOfCenter;

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
     * Panel Containing background image of Currently Selected game label.
     */
    private AImagePane imgSelectedGamePane;

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
     * AddGameUI Arrow image pointing to search box.
     */
    private AImagePane pnlSearchArrow;

    /**
     * AddGameUI Background image for Search box.
     */
    private AImagePane pnlSearchBox;

    /**
     * AddGameUI Bottom image panel.
     */
    private AImagePane pnlBottomPane;

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
     * Image for keyboard icon.
     */
    private AImage imgKeyIco;

    /**
     * Image for Favorite logo on side of library.
     */
    private AImage imgFavorite;

    /**
     * Image of Blank Case used as placeholder.
     */
    private AImage imgBlank;

    /**
     * Image Step One badge.
     */
    private AImage stepOne;

    /**
     * Image Step Two badge.
     */
    private AImage stepTwo;

    /**
     * AddGameUI Title Label.
     */
    private JLabel lblAddTitle;

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
    public static JLabel lblGameName;

    private ArrayList<AImagePane> gameCover;

    private int zoom;

    private GridManager GridSplit;

    private int currentIndex;

    private ArrayList<Boolean> loadedPanels;

    private GridAnimation GridAnimate;

    private JTextField gridSearchBar;

    private GameLibraryHandler handler;

    private GridSearch Search;

    private ASimpleDB CoverDB;

    private AAnimate addGameAnimator;

    private JTextField gameSearchBar;

    private GameSearch GameSearch;

    private searchBoxHandler searchBoxHandler;

    private searchFocusHandler searchFocusHandler;

    private boolean addGameUI_Visible = false;

    private JList gamesList;

    private JScrollPane gameScrollPane;

    private JFileChooser gameLocator;

    private DefaultListModel listModel;

    private String currentPath;

    private AAnimate addGameToLibButtonAnimator;

    private AuroraStorage storage;

    private HoverButtonLeft moveLibraryLeftHandler;

    private HoverButtonRight moveLibraryRightHandler;

    private MoveToLastGrid GridMove;

    private boolean isAddGameUILoaded = false;

    private boolean isGameLibraryKeyListenerAdded = false;

    private int SIZE_SearchBarWidth;

    public static int gameCoverWidth;

    public static int zoomButtonHeight;

    public static int selectedGameBarHeight;

    public static int selectedGameBarWidth;

    public static int addGameWidth;

    public static int addGameHeight;

    public static int gameNameFontSize;

    public static int gameCoverHeight;

    private boolean isScreenLoaded = false;

    private int btnBackWidth;

    private int btnBackHeight;

    private final DashboardUI dashboardUI;

    private final AuroraCoreUI coreUI;

    private final GameLibraryLogic logic;

    /**
     * .-----------------------------------------------------------------------.
     * | GameLibraryUI(AuroraStorage, DashboardUI, AuroraCoreUI)
     * .-----------------------------------------------------------------------.
     * |
     * | This is the Constructor of the GameLibraryUI UI class.
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
    public GameLibraryUI(final AuroraStorage auroraStorage,
                         final DashboardUI dashboardUi,
                         final AuroraCoreUI auroraCoreUI) {

        this.coreUI = auroraCoreUI;
        this.storage = auroraStorage;
        this.dashboardUI = dashboardUi;

        this.logic = new GameLibraryLogic(this);
        this.handler = new GameLibraryHandler(this);

        handler.setLogic(logic);
        logic.setHandler(handler);
    }

    @Override
    public final void loadUI() {

        // Load All UI Components
        // --------------------------------------------------------------------.

        //* Create Components for Library *//

        paneLibraryContainer = new JPanel(true);

        imgFavorite = new AImage("Aurora_Favorite.png");
        imgBlank = new AImage("Aurora_Blank.png");



        btnGameRight = new AHoverButton(3,
                "Aurora_RightLib_normal.png", "Aurora_RightLib_over.png");
        btnGameLeft = new AHoverButton(3,
                "Aurora_LeftLib_normal.png", "Aurora_LeftLib_over.png");


        //* Zoom Buttons *//
        btnZoomPlus = new AButton("Aurora_ZoomP_normal.png",
                "Aurora_ZoomP_down.png",
                "Aurora_ZoomP_over.png", 0, zoomButtonHeight);
        btnZoomLess = new AButton("Aurora_ZoomM_normal.png",
                "Aurora_ZoomM_down.png",
                "Aurora_ZoomM_over.png", 0, zoomButtonHeight);


        //* Key Board Naviagtion Icon *//
        imgKeyIco = new AImage("KeyboardKeys/arrows.png", coreUI
                .getKeyIconWidth(), coreUI.getKeyIconHeight());
        lblKeyAction = new JLabel(" Move ");


        //* Selected Game Name Bar *//
        pnlSelectedGameContainer = new JPanel();

        imgSelectedGamePane = new AImagePane("Aurora_SelectedGameBar.png",
                selectedGameBarWidth, selectedGameBarHeight,
                new FlowLayout(FlowLayout.CENTER, 0, 5));

        lblGameName = new JLabel("Select A Game For Info");


        //* Add Game Button *//
        pnlShowAddGameContainer = new JPanel(new BorderLayout());

        btnShowAddGameUI = new AButton("Aurora_Add_normal.png",
                "Aurora_Add_down.png",
                "Aurora_Add_over.png", addGameWidth, addGameHeight);


        //* Search Bar *//
        pnlSearchBarBG = new AImagePane("SearchBar_inactive.png",
                new BorderLayout());
        removeSearchButton = new AButton("SearchButtonBack_up.png",
                "SearchButtonBack_down.png", "SearchButtonBack_over.png");
        gridSearchBar = new JTextField("Start Typing To Search...");
        btnSearch = new AButton("SearchButton_up.png",
                "SearchButton_down.png", "SearchButton_over.png");
        pnlSearchButtonBG = new AImagePane("SearchButtonBG.png",
                new BorderLayout());
        pnlSearchText = new JPanel(new BorderLayout());
        pnlSearchButton = new JPanel(new BorderLayout());
        pnlSearchContainer = new JPanel(new BorderLayout());
        pnlSearchBar = new JPanel(new BorderLayout());

        //* Create Grid Manager *//
        GridSplit = new GridManager(2, 4, coreUI);

        //* Grid Animator *//
        this.GridAnimate = new GridAnimation(GridSplit, paneLibraryContainer);


        //* Start Aurora Dabatase connection *//
        try {
            CoverDB = new ASimpleDB("AuroraDB", "AuroraTable", false);
        } catch (SQLException ex) {
            Logger.getLogger(GameLibraryUI.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

        // Handlers
        // --------------------------------------------------------------------.

        ////Search Backend////
        Search = new GridSearch(coreUI, this, GridSplit);



    }

    @Override
    public final void buildUI() {
        if (!isScreenLoaded) {
            setSize();

            //* Add Zoom Buttons *//
//        coreUI.getTitlePanel().removeAll();
//        coreUI.getTitlePanel().add(ZoomM);
//        coreUI.getTitlePanel().add(coreUI.getTitleLabel());
//        coreUI.getTitlePanel().add(ZoomP);

            paneLibraryContainer.setOpaque(false);
            paneLibraryContainer.setBackground(Color.red);
            paneLibraryContainer.setLayout(new BorderLayout(0, 0));

            lblKeyAction.setFont(coreUI.getDefaultFont().deriveFont(Font.PLAIN,
                    coreUI.getKeysFontSize()));
            lblKeyAction.setForeground(Color.YELLOW);



            //* Selected Game Name Bar *//
            pnlSelectedGameContainer.setOpaque(false);

            lblGameName.setOpaque(false);
            lblGameName.setFont(coreUI.getDefaultFont().deriveFont(Font.PLAIN,
                    gameNameFontSize));
            lblGameName.setForeground(Color.LIGHT_GRAY);

            imgSelectedGamePane.setPreferredSize(new Dimension(
                    selectedGameBarWidth, selectedGameBarHeight));
            imgSelectedGamePane.add(lblGameName);
            pnlSelectedGameContainer.add(imgSelectedGamePane);

            //* Add Game Button *//
            pnlShowAddGameContainer.setOpaque(false);
            btnShowAddGameUI.addActionListener(handler.new ShowAddGameUiHandler(
                    this));
            pnlShowAddGameContainer.add(btnShowAddGameUI, BorderLayout.CENTER);


            // Search Bar
            // ----------------------------------------------------------------.

            pnlSearchBarBG.setPreferredSize(new Dimension(SIZE_SearchBarWidth,
                    50));
            removeSearchButton.setPreferredSize(new Dimension(70, 51));

            gridSearchBar.setOpaque(false);
            gridSearchBar.setBorder(null);
            gridSearchBar.setColumns(19);
            gridSearchBar.setForeground(Color.darkGray);
            gridSearchBar.setFont(coreUI.getDefaultFont().deriveFont(Font.BOLD,
                    40));
            gridSearchBar.setPreferredSize(new Dimension(880, 50));

            btnSearch.setPreferredSize(new Dimension(70, 51));
            btnSearch
                    .addActionListener(handler.new searchButtonHandler(this));

            pnlSearchButtonBG.setPreferredSize(new Dimension(70, 51));
            pnlSearchButtonBG.add(btnSearch, BorderLayout.NORTH);

            pnlSearchText.setOpaque(false);
            pnlSearchText.add(gridSearchBar, BorderLayout.NORTH);

            pnlSearchContainer.setOpaque(false);
            pnlSearchContainer.add(pnlSearchButton, BorderLayout.WEST);
            pnlSearchContainer.add(pnlSearchText, BorderLayout.CENTER);

            pnlSearchButton.setOpaque(false);
            pnlSearchButton.add(pnlSearchButtonBG, BorderLayout.NORTH);

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
            paneLibraryContainer.add(BorderLayout.WEST, imgFavorite);
            paneLibraryContainer.add(BorderLayout.CENTER, GridSplit.getGrid(0));
            paneLibraryContainer.add(BorderLayout.EAST, btnGameRight);



            // Add Games to Library
            // ----------------------------------------------------------------.

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

        attactchHandlers();

        addToVolatileListenerBank(gridSearchBar);
        addToVolatileListenerBank(coreUI.getBackgroundImagePane());
        addToVolatileListenerBank(coreUI.getBottomImagePane());
        addToVolatileListenerBank(coreUI.getCenterPanel());
        addToVolatileListenerBank(coreUI.getSouthFromTopPanel());
        addToVolatileListenerBank(coreUI.getFrameControlImagePane());
        addToVolatileListenerBank(coreUI.getTopImagePane());
        addToVolatileListenerBank(this.btnShowAddGameUI);
        addToVolatileListenerBank(this.paneLibraryContainer);
        addToVolatileListenerBank(this.imgSelectedGamePane);
        addToVolatileListenerBank(this.btnGameLeft);
        addToVolatileListenerBank(this.btnGameRight);


        //* Add Search Bar to Top Bar *//
        coreUI.getSouthFromTopPanel().add(BorderLayout.CENTER, pnlSearchBar);
        coreUI.getSouthFromTopPanel().setPreferredSize(
                new Dimension(coreUI.getSouthFromTopPanel().getWidth(), coreUI
                .getFrameControlImagePane().getHeight()));
        coreUI.getSouthFromTopPanel().revalidate();

        //* Add AddGameButton to Bottom Bar *//
        coreUI.getUserSpacePanel().setLayout(new BorderLayout());
        coreUI.getUserSpacePanel().setVisible(true);
        coreUI.getUserSpacePanel().add(pnlShowAddGameContainer);

        //* Set up Bottom Bar *//
        coreUI.getCenterFromBottomPanel().setLayout(new BorderLayout());
        coreUI.getCenterFromBottomPanel().add(BorderLayout.CENTER,
                pnlSelectedGameContainer);
        coreUI.getCenterFromBottomPanel().add(BorderLayout.SOUTH, coreUI
                .getUserSpacePanel());

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
        coreUI.getTitleLabel().setText("   Game Library   ");
        btnGameRight.requestFocusInWindow();
        coreUI.getFrame().requestFocus();
    }

    public void attactchHandlers() {

        searchBoxHandler = handler.new searchBoxHandler(this);
        searchFocusHandler = handler.new searchFocusHandler(this);
        gridSearchBar.addKeyListener(searchBoxHandler);
        gridSearchBar.addFocusListener(searchFocusHandler);
        gridSearchBar.addMouseListener(handler.new searchSelectHandler(this));

        moveLibraryLeftHandler = handler.new HoverButtonLeft(this, coreUI);
        moveLibraryRightHandler = handler.new HoverButtonRight(this, coreUI);

        GridMove = handler.new MoveToLastGrid(this);

        btnGameRight.setMouseListener(moveLibraryRightHandler);
        btnGameLeft.setMouseListener(moveLibraryLeftHandler);

        coreUI.getFrame()
                .addKeyListener(handler.new searchRefocusListener(this));
        coreUI.getFrame()
                .addKeyListener(handler.new GameLibraryKeyListener(this, coreUI));
        coreUI.getFrame()
                .addMouseWheelListener(handler.new GridMouseWheelListener(this));

        coreUI.getBackgroundImagePane()
                .addKeyListener(handler.new searchRefocusListener(this));
        coreUI.getBackgroundImagePane()
                .addKeyListener(handler.new GameLibraryKeyListener(this, coreUI));
        coreUI.getBackgroundImagePane()
                .addMouseWheelListener(handler.new GridMouseWheelListener(this));

        coreUI.getBottomImagePane()
                .addKeyListener(handler.new searchRefocusListener(this));
        coreUI.getBottomImagePane()
                .addKeyListener(handler.new GameLibraryKeyListener(this, coreUI));
        coreUI.getBottomImagePane()
                .addMouseWheelListener(handler.new GridMouseWheelListener(this));

        coreUI.getCenterPanel()
                .addKeyListener(handler.new searchRefocusListener(this));
        coreUI.getCenterPanel()
                .addKeyListener(handler.new GameLibraryKeyListener(this, coreUI));
        coreUI.getCenterPanel()
                .addMouseWheelListener(handler.new GridMouseWheelListener(this));

        coreUI.getSouthFromTopPanel()
                .addKeyListener(handler.new searchRefocusListener(this));
        coreUI.getSouthFromTopPanel()
                .addKeyListener(handler.new GameLibraryKeyListener(this, coreUI));
        coreUI.getSouthFromTopPanel()
                .addMouseWheelListener(handler.new GridMouseWheelListener(this));

        coreUI.getFrameControlImagePane()
                .addKeyListener(handler.new searchRefocusListener(this));
        coreUI.getFrameControlImagePane()
                .addKeyListener(handler.new GameLibraryKeyListener(this, coreUI));
        coreUI.getFrameControlImagePane()
                .addMouseWheelListener(handler.new GridMouseWheelListener(this));

        coreUI.getTopImagePane()
                .addKeyListener(handler.new searchRefocusListener(this));
        coreUI.getTopImagePane()
                .addKeyListener(handler.new GameLibraryKeyListener(this, coreUI));
        coreUI.getTopImagePane()
                .addMouseWheelListener(handler.new GridMouseWheelListener(this));

        this.btnShowAddGameUI.addKeyListener(handler.new searchRefocusListener(
                this));
        this.btnShowAddGameUI.addKeyListener(handler.new GameLibraryKeyListener(
                this,
                coreUI));

        this.paneLibraryContainer.addKeyListener(
                handler.new searchRefocusListener(this));
        this.paneLibraryContainer
                .addKeyListener(handler.new GameLibraryKeyListener(
                this,
                coreUI));

        this.imgSelectedGamePane
                .addKeyListener(handler.new searchRefocusListener(this));
        this.imgSelectedGamePane
                .addKeyListener(handler.new GameLibraryKeyListener(this, coreUI));

        this.btnGameLeft.addKeyListener(handler.new searchRefocusListener(this));
        this.btnGameLeft.addKeyListener(handler.new GameLibraryKeyListener(this,
                coreUI));

        this.btnGameRight
                .addKeyListener(handler.new searchRefocusListener(this));
        this.btnGameRight
                .addKeyListener(handler.new GameLibraryKeyListener(this, coreUI));

    }

    public final void buildAddGameUI() {

        if (!isAddGameUILoaded) {

            
            // Create Components
            // ----------------------------------------------------------------.
            
            //* Get Glass Pane to Put UI On *//
            JPanel glass = (JPanel) coreUI.getFrame().getGlassPane();
            pnlAddGamePane = new AImagePane("Aurora_AddGame_BG.png",
                    new BorderLayout());

            //* TOP PANEL COMPONENTS *//
            pnlTopPane = new JPanel(new BorderLayout());
            pnlTopPane.setOpaque(false);
            btnCloseAddUI = new AButton("Aurora_Close_normal.png",
                    "Aurora_Close_down.png", "Aurora_Close_over.png");


            //* CENTRAL PANEL COMPONENTS *//
            pnlCenter = new JPanel(new BorderLayout());
            pnlCenter.setOpaque(false);
            pnlTopOfCenter = new JPanel(new BorderLayout());
            pnlTopOfCenter.setOpaque(false);
            pnlLeftOfTopCenter = new JPanel(new FlowLayout(FlowLayout.LEFT));
            pnlLeftOfTopCenter.setOpaque(false);
            pnlRightOfTopOfCenter = new JPanel(new FlowLayout(FlowLayout.LEFT));
            pnlRightOfTopOfCenter.setOpaque(false);
            pnlLeftOfBottomOfCenter = new JPanel(new FlowLayout(FlowLayout.LEFT,
                    0, 0));
            pnlLeftOfBottomOfCenter.setOpaque(false);
            pnlRightOfBottomOfCenter = new JPanel();
            pnlRightOfBottomOfCenter.setOpaque(true);
            pnlRightOfBottomContainer = new JPanel(new BorderLayout());
            pnlRightOfBottomContainer.setOpaque(false);
            pnlAddGameContainer = new JPanel(new BorderLayout(0, 0));
            pnlAddGameContainer.setOpaque(false);

            lblLeftTitle = new JLabel("Name");
            lblRightTitle = new JLabel("Location");
            stepOne = new AImage("AddGame_step1_normal.png");
            stepTwo = new AImage("AddGame_step2_normal.png");
            pnlCoverPane = new AImagePane("AddGame_CoverBG.png",
                    new FlowLayout(FlowLayout.LEFT,0, 10));
            pnlBlankCoverGame = new AImagePane("Blank-Case.png", 220, 250);
            gamesList = new JList();
            gameLocator = new JFileChooser(System.getProperty("user.home"));



            //* BOTTOM PANEL COMPONENTS *//
            pnlBottomPane = new AImagePane("AddGame_BottomBar.png",
                    new BorderLayout());
            pnTopOfBottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
            pnTopOfBottom.setOpaque(false);
            pnlBottomOfCenter = new JPanel(new BorderLayout());
            pnlBottomOfCenter.setOpaque(false);
            pnlSearchBG = new AImagePane("AddGame_SearchBG.png",
                    new BorderLayout());
            pnlSearchArrow = new AImagePane("AddGame_SearchArrow_light.png",
                    new BorderLayout());
            pnlSearchBox = new AImagePane("AddGame_SearchBox.png",
                    new BorderLayout());
            gameSearchBar = new JTextField("Search For Game To Add...");
            lblAddTitle = new JLabel("GAME NAME");
            pnlAddGameSearchContainer = new JPanel(new FlowLayout(
                    FlowLayout.CENTER));
            pnlAddGameSearchContainer.setOpaque(false);

            GameSearch = new GameSearch(this, CoverDB, storage);
            addGameToLibButton = new AButton("AddGame_AddToLib_normal.png",
                    "AddGame_AddToLib_down.png", "AddGame_AddToLib_over.png");
            addGameToLibButton.setVisible(false);
            addGameToLibButtonAnimator = new AAnimate(addGameToLibButton);



            // Set Up Components
            // ----------------------------------------------------------------.

            //* CENTRAL PANEL COMPONENTS *//
            
            //*
            // Set Up Title labels for both Left 
            // and Right side of the Central Panel
            //*
            lblLeftTitle.setFont(coreUI.getDefaultFont().deriveFont(Font.BOLD,
                    32));
            lblRightTitle.setFont(coreUI.getDefaultFont().deriveFont(Font.BOLD,
                    32));
                    
            //*
            // Set Up 2 Panels containing the Left and 
            // Right titles at the top of the Content panel
            //*
            pnlLeftOfTopCenter.setPreferredSize(new Dimension(pnlAddGamePane
                    .getImgIcon().getIconWidth() / 2, 75));
            pnlRightOfTopOfCenter.setPreferredSize(new Dimension(pnlAddGamePane
                    .getImgIcon().getIconWidth() / 2, 75));

            pnlLeftOfBottomOfCenter.setPreferredSize(new Dimension(490,
                    pnlCoverPane
                    .getImgIcon().getIconHeight()));
            pnlRightOfBottomOfCenter.setPreferredSize(new Dimension(490,
                    pnlCoverPane
                    .getImgIcon().getIconHeight()));
            pnlRightOfBottomOfCenter.setBackground(Color.DARK_GRAY);
            pnlRightOfBottomContainer.setPreferredSize(new Dimension(500,
                    pnlCoverPane
                    .getImgIcon().getIconHeight()));
                    
            //* Set Up Panels containing the Game Cover Art *//
            pnlCoverPane.setPreferredSize(new Dimension(pnlCoverPane
                    .getImgIcon()
                    .getIconWidth(), pnlCoverPane.getImgIcon().getIconHeight()));
            pnlBlankCoverGame.setPreferredSize(new Dimension(220, 260));
            
            //* Set up Game List *//
            gamesList.setPreferredSize(
                    new Dimension(pnlCoverPane.getImgIcon().getIconWidth() + 80,
                    pnlCoverPane.getImgIcon().getIconHeight()));
            gamesList.setBackground(Color.DARK_GRAY);
            gamesList.setForeground(Color.lightGray);
            gamesList.setFont(coreUI.getDefaultFont().deriveFont(Font.BOLD, 19));
            gamesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            gamesList.setLayoutOrientation(JList.VERTICAL);
            gamesList.setVisibleRowCount(10);
            
            //* List model for JList Containing Game Names *//
            listModel = new DefaultListModel();
            gamesList.setModel(listModel);



            //* Set up File Chooser UI (Windows only) *//
            UIManager.put("Viewport.background", Color.GRAY);
            UIManager.put("Viewport.foreground", Color.WHITE);
            UIManager.put("ScrollPane.background", Color.GRAY);
            UIManager.put("ComboBox.background", Color.LIGHT_GRAY);
            UIManager.put("ScrollBar.background", Color.GRAY);
            UIManager.put("Panel.background", Color.GRAY);
            UIManager.put("Panel.forground", Color.WHITE);
            UIManager.put("TextField.foreground", Color.WHITE);
            UIManager.put("TextField.background", Color.DARK_GRAY);


            //* Set up File Chooser *//
            SwingUtilities.updateComponentTreeUI(gameLocator);

            gameLocator.setApproveButtonText("Select");
            gameLocator.setDragEnabled(false);
            gameLocator.setDialogType(JFileChooser.OPEN_DIALOG);
            gameLocator.setMultiSelectionEnabled(false);
            gameLocator.setAcceptAllFileFilterUsed(true);
            gameLocator.setEnabled(true);
            gameLocator.revalidate();

            gameLocator.setPreferredSize(new Dimension(pnlCoverPane.getImgIcon()
                    .getIconWidth() * 2 + 100, pnlCoverPane.getImgIcon()
                    .getIconHeight() - 5));

            try {
                Field field = PopupFactory.class.getDeclaredField(
                        "forceHeavyWeightPopupKey");
                field.setAccessible(true);
                gameLocator.putClientProperty(field.get(null), true);
            } catch (Exception e) {
                e.printStackTrace();
            }



            //* BOTTOM PANEL COMPONENTS *//
            pnlBottomPane
                    .setPreferredSize(new Dimension(pnlBottomPane
                    .getImgIcon()
                    .getIconWidth(), pnlBottomPane.getImgIcon().getIconHeight()));
                    
            //* Set Up Textfield where user will search for game to add *//
            gameSearchBar.setFont(coreUI.getDefaultFont().deriveFont(Font.BOLD,
                    20));
            gameSearchBar.setForeground(Color.DARK_GRAY);
            gameSearchBar.setOpaque(false);
            gameSearchBar.setBorder(null);
            gameSearchBar.setPreferredSize(new Dimension(500, 50));
            
            //* Set up image sizes for the Search box *//
            pnTopOfBottom.setPreferredSize(new Dimension(pnlAddGamePane
                    .getImgIcon()
                    .getIconWidth(), 20));
            pnlSearchBox.setPreferredSize(new Dimension(pnlSearchBox.getImgIcon()
                    .getIconWidth(), pnlSearchBox.getImgIcon().getIconHeight()));
            pnlSearchBG.setPreferredSize(new Dimension(pnlSearchBG.getImgIcon()
                    .getIconWidth(), pnlSearchBG.getImgIcon().getIconHeight()));
            pnlSearchArrow.setPreferredSize(new Dimension(pnlSearchArrow.getImgIcon()
                    .getIconWidth(), pnlSearchArrow.getImgIcon().getIconHeight()));
            //* Set Up Title Label for Add Game UI *//
            lblAddTitle.setFont(coreUI.getDefaultFont()
                    .deriveFont(Font.BOLD, 15));
            lblAddTitle.setOpaque(false);
            lblAddTitle.setForeground(Color.black);

            
            //* Set up glass panel *//
            glass.setVisible(true);
            glass.setLayout(null);
            glass.setOpaque(false);
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
                                           - (335 / 2), pnlAddGamePane
                    .getImgIcon()
                    .getIconHeight() - 90);
            addGameToLibButton.setSize(new Dimension(340, 140));


            // Add to Components
            // ----------------------------------------------------------------.

            //* TOP PANEL COMPONENTS *//
            
            //* Add the Close button to the Top most Panel *//
            pnlTopPane.add(btnCloseAddUI, BorderLayout.EAST);
            pnlTopPane.add(btnCloseAddUI, BorderLayout.EAST);


            //* BOTTOM PANEL COMPONENTS *//
            
            //* Add components to form the Search Box *//
            pnlSearchBox.add(gameSearchBar, BorderLayout.CENTER);
            pnlSearchBG.add(pnlSearchArrow, BorderLayout.WEST);
            pnlSearchBG.add(pnlSearchBox, BorderLayout.EAST);
            pnlAddGameSearchContainer.add(pnlSearchBG);
            
            //* Add UI elements to the Bottom Panel in the Add Game UI *//
            pnTopOfBottom.add(lblAddTitle);
            pnlBottomPane.add(pnlAddGameSearchContainer, BorderLayout.CENTER);
            pnlBottomPane.add(pnTopOfBottom, BorderLayout.PAGE_START);


            //* CENTRAL PANEL COMPONENTS *//
            
            //* Add the Titles and Badges at the Top of the CENTRAL panel *//
            pnlLeftOfTopCenter.add(stepOne);
            pnlLeftOfTopCenter.add(lblLeftTitle);
            pnlRightOfTopOfCenter.add(stepTwo);
            pnlRightOfTopOfCenter.add(lblRightTitle);

            pnlCoverPane.add(pnlBlankCoverGame, BorderLayout.SOUTH);
            pnlLeftOfBottomOfCenter.add(pnlCoverPane);
            pnlLeftOfBottomOfCenter.add(gamesList);

            //* Add the main Content to the Central Panel *//
            pnlAddGameContainer.add(pnlLeftOfBottomOfCenter, BorderLayout.WEST);
            pnlRightOfBottomOfCenter.add(gameLocator);
            pnlRightOfBottomContainer
                    .add(pnlRightOfBottomOfCenter, BorderLayout.WEST);
            pnlAddGameContainer
                    .add(pnlRightOfBottomContainer, BorderLayout.EAST);
            pnlTopOfCenter.add(pnlLeftOfTopCenter, BorderLayout.WEST);
            pnlTopOfCenter.add(pnlRightOfTopOfCenter, BorderLayout.EAST);
            pnlCenter.add(pnlTopOfCenter, BorderLayout.NORTH);
            pnlCenter.add(pnlAddGameContainer, BorderLayout.CENTER);


            //*
            // Add the TOP the CENTER and the BOTTOM
            // panels to the Add Game UI
            //*
            pnlAddGamePane.add(pnlTopPane, BorderLayout.PAGE_START);
            pnlAddGamePane.add(pnlCenter, BorderLayout.CENTER);
            pnlAddGamePane.add(pnlBottomPane, BorderLayout.PAGE_END);

            glass.add(pnlAddGamePane);
            glass.add(addGameToLibButton);



            // Handlers
            // ----------------------------------------------------------------.

            btnCloseAddUI.addActionListener(handler.new HideGameAddUIHandler(
                    this));

            pnlAddGamePane.addMouseListener(handler.new EmptyMouseHandler());
            gameSearchBar
                    .addFocusListener(handler.new addGameFocusHandler(this));
            gameSearchBar
                    .addMouseListener(handler.new addGameMouseHandler(this));
            gameSearchBar.addKeyListener(handler.new addGameSearchBoxHandler(
                    this));
            gamesList.addListSelectionListener(handler.new SelectListHandler(
                    this));
            gameLocator.setFileFilter(
                    handler.new ExecutableFilterHandler(coreUI));
            gameLocator.addActionListener(handler.new ExecutableChooserHandler(
                    this, gameLocator));
            addGameToLibButton
                    .addActionListener(handler.new AddToLibraryHandler(this));


            // Finalize
            // ----------------------------------------------------------------.

            pnlAddGamePane.setVisible(false);
            addGameAnimator = new AAnimate(pnlAddGamePane);
            addGameToLibButton.revalidate();
            pnlAddGamePane.revalidate();

            glass.revalidate();

            isAddGameUILoaded = true;
        }
    }

    public void showAddGameUI() {
        addGameUI_Visible = true;

        buildAddGameUI();


        //* Animate Down Add Game UI *//
        addGameAnimator.setInitialLocation((coreUI.getFrame().getWidth() / 2)
                                           - (pnlAddGamePane.getImgIcon()
                .getIconWidth() / 2), -390);
        addGameAnimator.moveVertical(0, 32);
        pnlAddGamePane.revalidate();

        gameSearchBar.setFocusable(true);

        addGameAnimator.addPostAnimationListener(new APostHandler() {
            @Override
            public void actionPerformed() {
                gameSearchBar.requestFocus();
            }
        });

    }

    public void hideAddGameUI() {
        addGameUI_Visible = false;
        addGameToLibButton.setVisible(false);
        
        //* Animate Up Add Game UI *//
        addGameAnimator.moveVertical(-470, 32);

        stepTwo.setImgURl("AddGame_step2_red.png");
        addGameAnimator.removeAllListeners();
        addGameToLibButton.setVisible(false);
        addGameToLibButton.repaint();
        
        //TODO Fix Search Bar Focus
        gridSearchBar.requestFocus();
        coreUI.getFrame().requestFocus();
        gridSearchBar.requestFocus();
        coreUI.getFrame().requestFocus();

    }

    public void moveGridLeft() {
        System.out.println("Left key pressed");
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
                    paneLibraryContainer.add(imgFavorite, BorderLayout.WEST, 0);

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
                    Logger.getLogger(GameLibraryUI.class.getName()).log(
                            Level.SEVERE, null, ex);
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
        System.out.println("Right key pressed");

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
                    Logger.getLogger(GameLibraryUI.class.getName()).log(
                            Level.SEVERE, null, ex);
                }



                //of on last Grid then dont show right arrow button
                if (!(currentIndex + 1 < GridSplit.getArray().size() - 1)) {

                    paneLibraryContainer.remove(btnGameRight);
                    paneLibraryContainer.add(imgBlank, BorderLayout.EAST, 2);
                    btnGameRight.mouseExit();
                }
            }

            coreUI.getCenterPanel().removeAll();
            coreUI.getCenterPanel().add(BorderLayout.CENTER,
                    paneLibraryContainer);

            paneLibraryContainer.repaint();
            paneLibraryContainer.revalidate();

            coreUI.getFrame().requestFocus();

        }
        btnGameRight.mouseExit();
    }

    public void setSize() {

        int Ratio = (coreUI.getFrame().getWidth() - coreUI.getFrame()
                .getHeight()) / 2;
        System.out.println("Ratio " + Ratio);
        System.out.println("Height " + coreUI.getFrame().getHeight());
        System.out.println("Width " + coreUI.getFrame().getWidth());
        if (coreUI.isLargeScreen()) {
            gameCoverHeight = coreUI.getFrame().getHeight() / 3 - (Ratio
                                                                   / 10)
                              + 5;
            gameCoverWidth = coreUI.getFrame().getWidth() / 5
                             - (Ratio / 10) - 5;
            zoomButtonHeight = 30;
            selectedGameBarHeight = 65;
            selectedGameBarWidth = 380;
            addGameWidth = 351;
            addGameHeight = 51;
            gameNameFontSize = 35;
            SIZE_SearchBarWidth = 880;
            btnBackWidth = 0;
            btnBackHeight = 0;

        } else {
            btnBackWidth = 30;
            btnBackHeight = 35;
            gameCoverHeight = coreUI.getFrame().getHeight() / 3 - (Ratio
                                                                   / 10);
            gameCoverWidth = coreUI.getFrame().getWidth() / 5
                             - (Ratio / 10);
            zoomButtonHeight = 25;
            addGameWidth = 300;
            addGameHeight = 40;
            selectedGameBarHeight = 60;
            selectedGameBarWidth = 360;
            gameNameFontSize = 32;
            SIZE_SearchBarWidth = coreUI.getFrame().getWidth() / 2 + coreUI
                    .getControlWidth() / 2;
        }


    }

    // Getters and Setters
    // ----------------------------------------------------------------.
            
    public AAnimate getAddGameAnimator() {
        return addGameAnimator;
    }

    public JLabel getLblGameName() {
        return lblGameName;
    }

    public static int getGameCoverHeight() {
        return gameCoverHeight;
    }

    public static void setGameCoverHeight(int gameCoverHeight) {
        GameLibraryUI.gameCoverHeight = gameCoverHeight;
    }

    public boolean IsGameLibraryKeyListenerAdded() {
        return isGameLibraryKeyListenerAdded;
    }

    public void setIsGameLibraryKeyListenerAdded(
            boolean isGameLibraryKeyListenerAdded) {
        this.isGameLibraryKeyListenerAdded = isGameLibraryKeyListenerAdded;
    }

    /**
     * Size Value.
     * <p/>
     * @return int
     */
    public final int getGameCoverWidth() {
        return gameCoverWidth;
    }

    /**
     * Set Size Value.
     * <p/>
     * @param gameCoverWidth
     */
    public final void setGameCoverWidth(int gameCoverWidth) {
        GameLibraryUI.gameCoverWidth = gameCoverWidth;
    }

    public static int getGameNameFontSize() {
        return gameNameFontSize;
    }

    public static void setGameNameFontSize(int gameNameFontSize) {
        GameLibraryUI.gameNameFontSize = gameNameFontSize;
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
        return gameLocator;
    }

    public GameLibraryHandler getHandler() {
        return handler;
    }

    public AButton getRemoveSearchButton() {
        return removeSearchButton;
    }

    public JTextField getGameSearchBar() {
        return gameSearchBar;
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

    public MoveToLastGrid getGridMove() {
        return GridMove;
    }

    public JScrollPane getListScrollPane() {
        return gameScrollPane;
    }

    public AImage getStepOne() {
        return stepOne;
    }

    public AImage getStepTwo() {
        return stepTwo;
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

    public AImagePane getBottomPane() {
        return pnlBottomPane;
    }

    public JLabel getLblAddUITitle() {
        return lblAddTitle;
    }

    public AImagePane getSearchArrow() {
        return pnlSearchArrow;
    }

    public AImagePane getSearchBG() {
        return pnlSearchBG;
    }

    public AImagePane getSearchBox() {
        return pnlSearchBox;
    }

    public JTextField getSearchText() {
        return gameSearchBar;
    }

    public GameSearch getGameSearch() {
        return GameSearch;
    }

    public JPanel getButtonPane() {
        return pnlSearchButton;
    }

    public JPanel getGameBack() {
        return paneLibraryContainer;
    }

    public GridAnimation getGridAnimate() {
        return GridAnimate;
    }

    public GridManager getGridSplit() {
        return GridSplit;
    }

    public int getAddGameHeight() {
        return addGameHeight;
    }

    public int getAddGameWidth() {
        return addGameWidth;
    }

    public int getSIZE_GameCoverHeight() {
        return gameCoverHeight;
    }

    public int getSIZE_GameCoverWidth() {
        return gameCoverWidth;
    }

    public int getSIZE_GameNameFont() {
        return gameNameFontSize;
    }

    public int getSelectedGameBarHeight() {
        return selectedGameBarHeight;
    }

    public int getSelectedGameBarWidth() {
        return selectedGameBarWidth;
    }

    public int getZoomButtonHeight() {
        return zoomButtonHeight;
    }

    public GridSearch getSearch() {
        return Search;
    }

    public JTextField getSearchBar() {
        return gridSearchBar;
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
        return pnlSelectedGameContainer;
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

    public AImage getImgFavorite() {
        return imgFavorite;
    }

    public AImage getImgBlank() {
        return imgBlank;
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
        return imgSelectedGamePane;
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

    public GameLibraryLogic getLogic() {
        return logic;
    }
}
