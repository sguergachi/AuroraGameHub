/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aurora.V1.core.screen_ui;

import aurora.V1.core.AuroraCoreUI;
import aurora.V1.core.AuroraStorage;
import aurora.V1.core.main;
import aurora.V1.core.screen_handler.LibraryHandler;
import aurora.V1.core.screen_logic.LibraryLogic;
import aurora.V1.core.screen_logic.SettingsLogic;
import static aurora.V1.core.screen_ui.LibraryUI.listFontSize;
import static aurora.V1.core.screen_ui.LibraryUI.logger;
import aurora.engine.V1.Logic.AAnimate;
import aurora.engine.V1.Logic.APostHandler;
import aurora.engine.V1.Logic.ASound;
import aurora.engine.V1.Logic.AThreadWorker;
import aurora.engine.V1.UI.AButton;
import aurora.engine.V1.UI.AImage;
import aurora.engine.V1.UI.AImagePane;
import aurora.engine.V1.UI.ARadioButton;
import aurora.engine.V1.UI.ARadioButtonManager;
import aurora.engine.V1.UI.AScrollBar;
import aurora.engine.V1.UI.ASlickLabel;
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
import java.util.logging.Level;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class AddGameUI {

    private final AuroraCoreUI coreUI;

    private JPanel pnlGlass;

    private AImagePane pnlAddGamePane;

    private JPanel pnlTopPane_addUI;

    private AButton btnClose_addUI;

    private JPanel pnlAddGameType;

    private JPanel pnlManualAdd;

    private ARadioButton btnManual;

    private AButton btnGoToSteam;

    private AButton btnGoToProgram;

    private JPanel pnlTopOfCenter;

    private AImagePane pnlLeftOfTopCenter;

    private AImagePane pnlRightOfTop;

    private JPanel pnlRightOfTopEast;

    private JPanel pnlRightOfTopEastContainer;

    private JPanel pnlLeftOfBottom;

    private JPanel pnlRightOfBottom;

    private JPanel pnlRightOfBottomContainer;

    private JPanel pnlAddGameContainer;

    private JLabel lblLeftTitle;

    private AImage gameLocationStatusIndicator;

    private JLabel lblRightTitle;

    private AImage coverArtStatusIndicator;

    private AImagePane pnlCoverPane_addUI;

    private AImagePane pnlBlankCoverGame_addUI;

    private JList<Object> gamesList_addUI;

    private DefaultListModel<Object> listModel_addUI;

    private JFileChooser gameFileChooser_addUI;

    private JPanel pnlBottomPane;

    private JPanel pnTopOfBottom;

    private JPanel pnlBottomOfCenter;

    private AImagePane pnlSearchBG;

    private JTextField txtSearchField_addUI;

    private JPanel pnlAddGameSearchContainer;

    private AButton btnClearSearch_addUI;

    private AButton btnAutoSearchDB_addUI;

    private AButton btnAddGameToLib_addUI;

    private ARadioButton btnAuto;

    private JPanel pnlAutoAdd;

    private JPanel pnlAutoContent;

    private JPanel pnlAutoTop;

    private JPanel pnlAutoTopContainer;

    private JPanel pnlAutoContainer;

    private JPanel pnlAutoCenter;

    private AImagePane pnlCoverPane_autoUI;

    private AImagePane pnlBlankCoverGame_autoUI;

    private DefaultListModel<Object> listModel_autoUI;

    private JList<Object> gameList_autoUI;

    private JPanel pnlScrollPane;

    private JScrollPane scrollList_autoUI;

    private AScrollBar autoScrollBar;

    private AImage autoAddStatusIndicator;

    private ASlickLabel lblAutoSelectGame;

    private AImagePane pnlCheckBG;

    private DefaultListModel<Object> modelCheckList;

    private JList<Object> pnlCheckList;

    private AImagePane pnlAutoStatusContainer;

    private JPanel pnlListButtons_autoUI;

    private AButton btnAddAll;

    private AButton btnClearAll;

    private AButton btnAutoRefresh;

    private AImage imgAutoSearchStatus_addUI;

    private final LibraryUI libraryUI;

    private final LibraryLogic libraryLogic;

    private final LibraryHandler libraryHandler;

    private boolean addGameUI_Visible;

    private AAnimate addGameAnimator;

    private final AuroraStorage auroraStorage;

    private boolean manualMode = true;

    private boolean autoMode;

    private boolean autoAddStatusEnabled;

    private boolean gameLocationStatusEnabled;

    public AddGameUI(AuroraCoreUI coreUI, LibraryUI libraryUI) {
        this.coreUI = coreUI;
        this.libraryUI = libraryUI;
        this.libraryHandler = libraryUI.getHandler();
        this.libraryLogic = libraryUI.getLogic();
        this.auroraStorage = libraryUI.getDashboardUI().getStorage();
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
    public void loadAddGameUI() {

        // Create Main Components
        // ----------------------------------------------------------------.

        //
        // Get Glass Pane to Put UI On
        //
        pnlGlass = (JPanel) coreUI.getFrame().getGlassPane();
        pnlAddGamePane = new AImagePane("addUI_bg.png",
                new BorderLayout());
        //
        // Top Panel Components
        //
        pnlTopPane_addUI = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 4));
        pnlTopPane_addUI.setLayout(new BorderLayout(10, 4));
        pnlTopPane_addUI.setOpaque(false);

        btnClose_addUI = new AButton("addUI_btnClose_norm.png",
                "addUI_btnClose_down.png",
                "addUI_btnClose_over.png");

        pnlAddGameType = new JPanel(new FlowLayout(FlowLayout.CENTER, -18, 0));
        pnlAddGameType.setOpaque(false);


        // Manual Mode
        // ----------------------------------------------------------------.

        //
        // Central Panel Components
        //
        pnlManualAdd = new JPanel(new BorderLayout());
        pnlManualAdd.setOpaque(false);

        btnManual = new ARadioButton("addUI_btnManual_norm.png",
                "addUI_btnManual_down.png");
        btnManual.setBorder(null);

        btnGoToSteam = new AButton("addUI_btnGoToSteam_norm.png",
                "addUI_btnGoToSteam_down.png",
                "addUI_btnGoToSteam_over.png");
        btnGoToSteam.setBorder(null);
        btnGoToSteam.setMargin(new Insets(0, 0, 0, 0));
        btnGoToSteam.addActionListener(
                libraryHandler.new GoToSteamListener(
                        coreUI, gameFileChooser_addUI));

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

        btnGoToProgram.addActionListener(
                libraryHandler.new GoToProgramsListener(coreUI,
                        gameFileChooser_addUI));

        pnlTopOfCenter = new JPanel(new BorderLayout());
        pnlTopOfCenter.setOpaque(false);

        pnlLeftOfTopCenter = new AImagePane("addUI_status_container.png",
                new FlowLayout(FlowLayout.LEFT));
        pnlRightOfTop = new AImagePane("addUI_status_container.png",
                new FlowLayout(FlowLayout.LEFT, 0, 5));

        pnlRightOfTopEast = new JPanel(new BorderLayout(-2, 0));
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

        coverArtStatusIndicator = new AImage("addUI_badge_idle.png");
        gameLocationStatusIndicator = new AImage("addUI_badge_idle.png");

        pnlCoverPane_addUI = new AImagePane("addUI_game_bg.png",
                new FlowLayout(FlowLayout.RIGHT, -7,
                        10));
        pnlBlankCoverGame_addUI = new AImagePane("Blank-Case.png", 240, 260);
        gamesList_addUI = new JList<>();
        listModel_addUI = new DefaultListModel<>();

        gameFileChooser_addUI = new JFileChooser(System.getProperty("user.home"));

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

        // Set up File Chooser
        SwingUtilities.updateComponentTreeUI(gameFileChooser_addUI);

        gameFileChooser_addUI.setApproveButtonText("Select");
        gameFileChooser_addUI.setDragEnabled(false);
        gameFileChooser_addUI.setDialogType(JFileChooser.OPEN_DIALOG);
        gameFileChooser_addUI.setMultiSelectionEnabled(false);
        gameFileChooser_addUI.setAcceptAllFileFilterUsed(true);
        gameFileChooser_addUI.setEnabled(true);
        gameFileChooser_addUI.revalidate();

        try {
            UIManager.setLookAndFeel(UIManager
                    .getCrossPlatformLookAndFeelClassName());
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
        //
        // Bottom Panel Components
        //
        pnlBottomPane = new JPanel(new BorderLayout(0, 20));
        pnlBottomPane.setOpaque(false);
        pnTopOfBottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnTopOfBottom.setOpaque(false);
        pnlBottomOfCenter = new JPanel(new BorderLayout());
        pnlBottomOfCenter.setOpaque(false);

        pnlSearchBG = new AImagePane("addUI_text_inactive.png",
                new FlowLayout(FlowLayout.RIGHT, 5, -1));
        pnlSearchBG.setLayout(new BorderLayout(0, -1));

        txtSearchField_addUI = new JTextField("Search For Game...");

        pnlAddGameSearchContainer = new JPanel(new FlowLayout(
                FlowLayout.LEFT, 0, 5));
        pnlAddGameSearchContainer.setOpaque(false);

        btnClearSearch_addUI = new AButton("addUI_btnClearText_norm.png",
                "addUI_btnClearText_down.png",
                "addUI_btnClearText_over.png");

        btnAutoSearchDB_addUI = new AButton("addUI_btn_autoSearch_norm.png",
                "addUI_btn_autoSearch_down.png",
                "addUI_btn_autoSearch_norm.png");

        btnAddGameToLib_addUI = new AButton("addUI_btnAdd_norm.png",
                "addUI_btnAdd_down.png",
                "addUI_btnAdd_over.png");
        btnAddGameToLib_addUI.setVisible(false);

        libraryLogic.getGameSearch_addUI().setUpGameSearch(
                pnlBlankCoverGame_addUI,
                pnlCoverPane_addUI,
                listModel_addUI, coverArtStatusIndicator,
                txtSearchField_addUI);

        // Auto Mode
        // ----------------------------------------------------------------.
        btnAuto = new ARadioButton("addUI_btnAuto_norm.png",
                "addUI_btnAuto_down.png");

        //Panels
        pnlAutoAdd = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        pnlAutoAdd.setOpaque(false);

        pnlAutoContent = new JPanel(new BorderLayout(0, -25));
        pnlAutoContent.setOpaque(false);

        pnlAutoTop = new JPanel(new BorderLayout(0, 15));
        pnlAutoTop.setOpaque(false);

        pnlAutoTopContainer = new JPanel(new BorderLayout(0, -25));
        pnlAutoTopContainer.setOpaque(false);

        pnlAutoContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        pnlAutoContainer.setOpaque(false);

        pnlAutoCenter = new JPanel(new BorderLayout(0, 0));
        pnlAutoCenter.setOpaque(false);

        //Components
        pnlCoverPane_autoUI = new AImagePane("autoUI_coverBG.png",
                new FlowLayout(
                        FlowLayout.RIGHT, 0, 15));

        pnlBlankCoverGame_autoUI = new AImagePane("Blank-Case.png", 280, 300);

        listModel_autoUI = new DefaultListModel<>();

        gameList_autoUI = new JList<>();

        pnlScrollPane = new JPanel(new BorderLayout());
        pnlScrollPane.setOpaque(false);

        scrollList_autoUI = new JScrollPane(pnlScrollPane,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scrollList_autoUI.setOpaque(false);
        scrollList_autoUI.getViewport().setOpaque(false);
        scrollList_autoUI.setBorder(null);

        autoScrollBar = new AScrollBar("app_scrollBar.png", "app_scrollBG.png");

        autoAddStatusIndicator = new AImage("addUI_badge_idle.png");

        lblAutoSelectGame = new ASlickLabel("Select Game");

        pnlCheckBG = new AImagePane("autoUI_BG.png", new BorderLayout(12, 0));

        modelCheckList = new DefaultListModel<>();
        pnlCheckList = new JList<>();

        pnlAutoStatusContainer = new AImagePane("addUI_status_container.png",
                new FlowLayout(FlowLayout.LEFT,
                        0, 5));
        pnlAutoStatusContainer.setImageSize(pnlAutoStatusContainer
                .getRealImageWidth() + 35,
                pnlAutoStatusContainer
                .getRealImageHeight());

        pnlListButtons_autoUI = new JPanel(new FlowLayout(FlowLayout.CENTER, 0,
                0));
        pnlListButtons_autoUI.setOpaque(false);

        btnAddAll = new AButton("autoUI_btnAll_norm.png",
                "autoUI_btnAll_down.png",
                "autoUI_btnAll_over.png");
        btnAddAll.setBorder(null);
        btnAddAll.setMargin(new Insets(0, 0, 0, 0));

        btnClearAll = new AButton("autoUI_btnClear_norm.png",
                "autoUI_btnClear_down.png",
                "autoUI_btnClear_over.png");
        btnClearAll.setBorder(null);
        btnClearAll.setMargin(new Insets(0, 0, 0, 0));

        btnAutoRefresh = new AButton("autoUI_btnRefresh_norm.png",
                "autoUI_btnRefresh_down.png",
                "autoUI_btnRefresh_over.png");

        imgAutoSearchStatus_addUI = new AImage("addUI_img_autoSearchOn.png");

        libraryLogic.getGameSearch_autoUI().setCanEditCover(false);

        btnAutoSearchDB_addUI.addActionListener(
                libraryHandler.new GameSearchButtonListener(libraryLogic
                        .getGameSearch_addUI(), imgAutoSearchStatus_addUI));

        btnAutoRefresh.setBorder(null);
        btnAutoRefresh.setMargin(new Insets(0, 0, 0, 0));

        libraryLogic.getGameSearch_autoUI().setUpGameSearch(
                pnlBlankCoverGame_autoUI,
                pnlCoverPane_autoUI,
                listModel_autoUI, autoAddStatusIndicator, null);

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

        if (!libraryUI.isAddGameUILoaded()) { // Only build once, reset every other time

            // Manual UI
            // ----------------------------------------------------------------.
            pnlAddGamePane.setVisible(false);

            //
            // Set Up Title labels for both Left
            // and Right side of the Central Panel
            //
            lblLeftTitle.setFont(coreUI.getDefaultFont().deriveFont(Font.BOLD,
                    33));
            lblLeftTitle.setForeground(Color.lightGray);
            lblRightTitle.setFont(coreUI.getDefaultFont().deriveFont(Font.BOLD,
                    33));
            lblRightTitle.setForeground(Color.lightGray);

            // Set Up Panels containing the Game Cover Art
            pnlCoverPane_addUI.setPreferredSize(new Dimension(pnlCoverPane_addUI
                    .getImgIcon()
                    .getIconWidth(), pnlCoverPane_addUI.getImgIcon()
                    .getIconHeight()));

            //
            // Set Up 2 Panels containing the Left and
            // Right titles at the top of the Content panel
            //
            pnlLeftOfTopCenter.setPreferredSize(new Dimension(pnlAddGamePane
                    .getImgIcon().getIconWidth() / 2, 75));
            pnlRightOfTop.setPreferredSize(new Dimension(pnlAddGamePane
                    .getImgIcon().getIconWidth() / 2, 50));

            pnlLeftOfBottom
                    .setPreferredSize(new Dimension(pnlAddGamePane
                                    .getImgIcon().getIconWidth() / 2 - 10,
                                    pnlCoverPane_addUI
                                    .getImgIcon()
                                    .getIconHeight()));
            pnlRightOfBottom
                    .setPreferredSize(new Dimension(pnlAddGamePane
                                    .getImgIcon().getIconWidth() / 2 - 10,
                                    pnlCoverPane_addUI
                                    .getImgIcon()
                                    .getIconHeight()));
            pnlRightOfBottom.setBackground(new Color(38, 46, 60));

            pnlRightOfBottomContainer
                    .setPreferredSize(new Dimension(pnlAddGamePane
                                    .getImgIcon().getIconWidth() / 2,
                                    pnlCoverPane_addUI
                                    .getImgIcon()
                                    .getIconHeight()));

            pnlBlankCoverGame_addUI.setPreferredSize(new Dimension(240, 260));

            // Set up Go To Shortcuts //
            int rightOfTopEastWidth;
            // Check that steam exists before showing the
            if (libraryLogic.fetchSteamDirOnWindows() != null) {
                pnlRightOfTopEastContainer.add(btnGoToSteam);
                rightOfTopEastWidth = 4;
            } else {
                rightOfTopEastWidth = 3;
            }

            // Add Go To Program
            pnlRightOfTopEastContainer.add(btnGoToProgram);

            pnlRightOfTopEast.add(Box.createHorizontalStrut(pnlRightOfTop
                    .getPreferredSize().width / rightOfTopEastWidth));
            pnlRightOfTopEast.add(pnlRightOfTopEastContainer, BorderLayout.EAST);

            // Set up Game List //
            gamesList_addUI.setPreferredSize(
                    new Dimension(
                            pnlCoverPane_addUI.getImgIcon().getIconWidth() +
                            90, pnlCoverPane_addUI.getImgIcon()
                            .getIconHeight()));

            gamesList_addUI.setBackground(new Color(38, 46, 60));
            gamesList_addUI.setForeground(Color.lightGray);
            gamesList_addUI.setFont(coreUI.getDefaultFont()
                    .deriveFont(Font.BOLD,
                            listFontSize));
            gamesList_addUI
                    .setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            gamesList_addUI.setSelectionBackground(new Color(54, 95, 143));
            gamesList_addUI.setSelectionForeground(new Color(238, 243, 249));
            gamesList_addUI.setBorder(null);

            gamesList_addUI.setLayoutOrientation(JList.VERTICAL);
            gamesList_addUI.setVisibleRowCount(10);

            // List model for JList Containing Game Names
            gamesList_addUI.setModel(listModel_addUI);
            gamesList_addUI.setCellRenderer(libraryHandler.new GameListRender());

            try {
                String name = "javax.swing.ClientPropertyKey";
                Class<?> keyClazz = Class.forName(name);
                Field field = keyClazz.getDeclaredField(
                        "PopupFactory_FORCE_HEAVYWEIGHT_POPUP");
                field.setAccessible(true);
                Object fieldValue = field.get(null);
                gameFileChooser_addUI.putClientProperty(fieldValue, true);
            } catch (Exception e) {
                logger.error(e);
            }

            gameFileChooser_addUI.setPreferredSize(new Dimension(
                    pnlRightOfBottom.getPreferredSize().width, pnlRightOfBottom
                    .getPreferredSize().height + 5));
            gameFileChooser_addUI.revalidate();

            // Bottom Panel
            // Set Up Textfield where user will search for game to add
            txtSearchField_addUI.setFont(coreUI.getRopaFont().deriveFont(
                    Font.PLAIN,
                    libraryUI.getGameNameFontSize() - 3));
            txtSearchField_addUI.setForeground(Color.gray);
            txtSearchField_addUI.setOpaque(false);
            txtSearchField_addUI.setBorder(null);
            txtSearchField_addUI.setBorder(BorderFactory.createEmptyBorder());
            txtSearchField_addUI.setPreferredSize(new Dimension(375, 50));

            // Set up image sizes for the Search box
            pnTopOfBottom.setPreferredSize(new Dimension(pnlAddGamePane
                    .getImgIcon()
                    .getIconWidth(), 20));
            pnlSearchBG.setPreferredSize(new Dimension(pnlSearchBG.getImgIcon()
                    .getIconWidth(), pnlSearchBG.getImgIcon().getIconHeight()));

            // Set up glass panel
            pnlGlass.setVisible(true);
            pnlGlass.setLayout(null);

            // Set Location for Add Game UI panels
            pnlAddGamePane.setLocation((coreUI.getFrame().getWidth() / 2) -
                                       (pnlAddGamePane.getImgIcon()
                                       .getIconWidth() /
                                        2), -380);
            pnlAddGamePane
                    .setSize(
                            new Dimension(pnlAddGamePane.getImgIcon()
                                    .getIconWidth(), pnlAddGamePane.getImgIcon()
                                    .getIconHeight()));
            pnlAddGamePane.revalidate();

            btnAddGameToLib_addUI.setLocation(
                    (coreUI.getFrame().getWidth() / 2) -
                    btnAddGameToLib_addUI
                    .getWidth() / 2,
                    pnlAddGamePane
                    .getImgIcon()
                    .getIconHeight() - 90);
            btnAddGameToLib_addUI.setSize(new Dimension(340, 140));


            // Add the Close button to the Top most Panel
            pnlTopPane_addUI.add(btnClose_addUI, BorderLayout.EAST);

            //
            // Manual/Auto Panel
            //
            ARadioButtonManager rdbManager = new ARadioButtonManager();
            rdbManager.addButton(btnManual);
            rdbManager.addButton(btnAuto);
            rdbManager.setRadioButton();

            pnlAddGameType.add(btnManual);
            pnlAddGameType.add(btnAuto);

            pnlTopPane_addUI.add(pnlAddGameType, BorderLayout.CENTER);

            pnlTopPane_addUI.add(Box.createHorizontalStrut(82),
                    BorderLayout.WEST);

            //
            // Bottom Panel Components
            //
            btnClearSearch_addUI.addActionListener(
                    libraryHandler.new GameSearchBoxClear(txtSearchField_addUI,
                            libraryLogic
                            .getGameSearch_addUI()));
            btnClearSearch_addUI.setMargin(new Insets(0, 0, 0, 0));

            // Add components to form the Search Box
            pnlSearchBG.add(Box.createHorizontalStrut(15), BorderLayout.WEST);
            pnlSearchBG.add(txtSearchField_addUI, BorderLayout.CENTER);
            pnlSearchBG.add(btnClearSearch_addUI, BorderLayout.EAST);

            btnAutoSearchDB_addUI.add(imgAutoSearchStatus_addUI);

            if (main.LAUNCHES < 5) {
                btnAutoSearchDB_addUI.setToolTipText("Disable AuroraCoverDB");
            }

            pnlAddGameSearchContainer.add(Box.createHorizontalStrut(105));
            pnlAddGameSearchContainer.add(pnlSearchBG);
            pnlAddGameSearchContainer.add(btnAutoSearchDB_addUI);

            // Add UI elements to the Bottom Panel in the Add Game UI
            pnlBottomPane
                    .add(pnlAddGameSearchContainer, BorderLayout.PAGE_START);

            //
            // Central Panel Components
            //
            pnlLeftOfTopCenter.add(coverArtStatusIndicator);
            pnlLeftOfTopCenter.add(lblLeftTitle);
            pnlRightOfTop.add(gameLocationStatusIndicator);
            pnlRightOfTop.add(lblRightTitle);
            pnlRightOfTop.add(pnlRightOfTopEast);

            pnlCoverPane_addUI.add(pnlBlankCoverGame_addUI);
            pnlLeftOfBottom.add(Box.createHorizontalStrut(20));
            pnlLeftOfBottom.add(pnlCoverPane_addUI);
            pnlLeftOfBottom.add(gamesList_addUI);

            pnlRightOfBottom.add(gameFileChooser_addUI);
            pnlRightOfBottomContainer
                    .add(pnlRightOfBottom);

            pnlAddGameContainer.add(pnlLeftOfBottom, BorderLayout.WEST);
            pnlAddGameContainer
                    .add(pnlRightOfBottomContainer, BorderLayout.EAST);

            pnlTopOfCenter.add(pnlLeftOfTopCenter, BorderLayout.WEST);
            pnlTopOfCenter.add(pnlRightOfTop, BorderLayout.EAST);

            pnlManualAdd.add(pnlTopOfCenter, BorderLayout.NORTH);
            pnlManualAdd.add(pnlAddGameContainer, BorderLayout.CENTER);
            pnlManualAdd.add(pnlBottomPane, BorderLayout.SOUTH);

            //
            // Add the TOP the CENTER and the BOTTOM
            // panels to the Add Game UI
            //
            pnlAddGamePane.add(pnlTopPane_addUI, BorderLayout.PAGE_START);
            pnlAddGamePane.add(pnlManualAdd, BorderLayout.CENTER);

            pnlGlass.add(pnlAddGamePane);
            pnlGlass.add(btnAddGameToLib_addUI);

            // Auto UI
            // ----------------------------------------------------------------.

            //
            //Top Panel
            //
            lblAutoSelectGame.setForeground(Color.lightGray);
            lblAutoSelectGame.setFont(coreUI.getDefaultFont().deriveFont(
                    Font.BOLD,
                    33));

            pnlAutoStatusContainer.add(autoAddStatusIndicator);
            pnlAutoStatusContainer.add(lblAutoSelectGame);

            pnlAutoTop.add(pnlAutoStatusContainer, BorderLayout.WEST);

            // Set up Game List
            gameList_autoUI.setBackground(new Color(38, 46, 60));
            gameList_autoUI.setForeground(Color.lightGray);
            gameList_autoUI.setFont(coreUI.getDefaultFont()
                    .deriveFont(Font.BOLD,
                            listFontSize));
            gameList_autoUI
                    .setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            gameList_autoUI.setSelectionBackground(new Color(54, 95, 143));
            gameList_autoUI.setSelectionForeground(new Color(238, 243, 249));
            gameList_autoUI.setBorder(null);
            gameList_autoUI.setLayoutOrientation(JList.VERTICAL);
            gameList_autoUI.setFixedCellHeight(30);
            gameList_autoUI.setFixedCellWidth(scrollList_autoUI
                    .getPreferredSize().width);

            gameList_autoUI.setModel(listModel_autoUI);
            gameList_autoUI
                    .setCellRenderer(libraryHandler.new AutoGameListlRender());
            gameList_autoUI
                    .removeMouseListener(pnlCheckList.getMouseListeners()[0]);

            // Check Box Pane
            pnlCheckBG.setPreferredSize(new Dimension(pnlCheckBG
                    .getRealImageWidth(),
                    pnlCoverPane_autoUI
                    .getRealImageHeight()));

            scrollList_autoUI.getViewport().addChangeListener(
                    new ChangeListener() {
                        @Override
                        public void stateChanged(ChangeEvent e) {

                            pnlCheckBG.setImageSize(pnlCheckBG
                                    .getRealImageWidth(),
                                    scrollList_autoUI
                                    .getViewport()
                                    .getPreferredSize().height);

                            pnlCheckBG.setPreferredSize(new Dimension(pnlCheckBG
                                            .getRealImageWidth(),
                                            scrollList_autoUI
                                            .getViewport()
                                            .getPreferredSize().height));

                        }
                    });

            pnlCheckList.setModel(modelCheckList);
            pnlCheckList.setBorder(BorderFactory.createEmptyBorder(0, 1,
                    0, 0));
            pnlCheckList.setOpaque(false);
            pnlCheckList.setLayoutOrientation(JList.VERTICAL);
            pnlCheckList.setCellRenderer(
                    libraryHandler.new CheckListRender());
            pnlCheckList.setFixedCellHeight(30);
            pnlCheckList.setFixedCellWidth(pnlCheckBG
                    .getRealImageWidth());
            pnlCheckList
                    .removeMouseListener(pnlCheckList.getMouseListeners()[0]);

            pnlCheckBG.add(pnlCheckList, BorderLayout.CENTER);

            // Set up Scroll Pane
            JScrollBar scrollBar = new JScrollBar();
            scrollBar.setUI(autoScrollBar);
            scrollBar.setUnitIncrement(35);

            scrollList_autoUI.setVerticalScrollBar(scrollBar);
            scrollList_autoUI.setPreferredSize(new Dimension(pnlAddGamePane
                    .getPreferredSize().width / 2 - 50,
                    pnlCoverPane_autoUI
                    .getRealImageHeight()));

            pnlScrollPane.add(gameList_autoUI, BorderLayout.CENTER);
            pnlScrollPane.add(pnlCheckBG, BorderLayout.EAST);

            // Above List Panel
            btnAddAll.addActionListener(
                    libraryHandler.new AutoAddAllButtonHandler());
            btnClearAll.addActionListener(
                    libraryHandler.new AutoClearAllButtonHandler());
            btnAutoRefresh.addActionListener(
                    libraryHandler.new AutoRefreshHandler());

            pnlListButtons_autoUI.add(btnAutoRefresh);
            pnlListButtons_autoUI.add(Box.createHorizontalStrut(150));
            pnlListButtons_autoUI.add(btnAddAll);
            pnlListButtons_autoUI.add(btnClearAll);
            pnlListButtons_autoUI.add(Box.createHorizontalStrut(17));

            pnlAutoTopContainer.add(pnlListButtons_autoUI, BorderLayout.EAST);

            // Center Panel
            pnlBlankCoverGame_autoUI.setPreferredSize(new Dimension(
                    pnlBlankCoverGame_autoUI.getImageWidth(),
                    pnlBlankCoverGame_autoUI.getImageHeight()));

            pnlCoverPane_autoUI.setPreferredSize(new Dimension(
                    pnlCoverPane_autoUI.getRealImageWidth(), pnlCoverPane_autoUI
                    .getRealImageHeight()));
            pnlCoverPane_autoUI.add(pnlBlankCoverGame_autoUI);

            pnlAutoContainer.add(pnlCoverPane_autoUI);
            pnlAutoContainer.add(scrollList_autoUI);

            pnlAutoCenter.add(pnlAutoTopContainer, BorderLayout.NORTH);
            pnlAutoCenter.add(pnlAutoContainer, BorderLayout.CENTER);

            // Auto UI Content
            pnlAutoContent.add(pnlAutoTop, BorderLayout.NORTH);
            pnlAutoContent.add(pnlAutoCenter, BorderLayout.CENTER);

            pnlAutoAdd.add(pnlAutoContent);

            // Handlers
            // ----------------------------------------------------------------.
            btnClose_addUI
                    .addActionListener(libraryHandler.new HideAddGameUIHandler(
                                    libraryUI));

            pnlAddGamePane.addMouseListener(
                    libraryHandler.new EmptyMouseHandler());

            txtSearchField_addUI
                    .addFocusListener(
                            libraryHandler.new GameSearchBoxFocusHandler(
                                    txtSearchField_addUI, pnlSearchBG,
                                    libraryLogic
                                    .getGameSearch_addUI()));
            txtSearchField_addUI
                    .addMouseListener(libraryHandler.new GameSearchBoxMouseHandler(
                                    txtSearchField_addUI, pnlSearchBG,
                                    libraryLogic
                                    .getGameSearch_addUI()));
            txtSearchField_addUI.getDocument().addDocumentListener(
                    libraryHandler.new GameSearchBoxChangeHandler(libraryLogic
                            .getGameSearch_addUI(), txtSearchField_addUI));

            // Auto Search Status Button
            gamesList_addUI.addListSelectionListener(
                    libraryHandler.new SelectListHandler(libraryLogic
                            .getGameSearch_addUI()));

            gameFileChooser_addUI.setFileFilter(
                    libraryHandler.new ExecutableFilterHandler());
            gameFileChooser_addUI
                    .addActionListener(
                            libraryHandler.new ExecutableChooserHandler(
                                    gameFileChooser_addUI));
            btnAddGameToLib_addUI
                    .addActionListener(
                            libraryHandler.new AddToLibraryButtonHandler(
                                    libraryLogic
                                    .getGameSearch_addUI()));

            gameList_autoUI.addListSelectionListener(
                    libraryHandler.new AutoSelectListHandler(libraryLogic
                            .getGameSearch_autoUI()));

            btnManual.setSelectedHandler(libraryHandler.new ManualAddHandler());
            btnAuto.setSelectedHandler(libraryHandler.new AutoAddHandler(
                    listModel_autoUI));

            // Finalize
            // ----------------------------------------------------------------.
            btnAddGameToLib_addUI.revalidate();
            pnlAddGamePane.revalidate();

            libraryUI.setIsAddGameUILoaded(true);

        }
    }

    public void showAddGameUI() {

        if (libraryUI.isEditGameUIVisible()) {
            libraryUI.getEditGameUI().hideEditGameUI();
            showAddGameUI();
        } else if (libraryUI.isAddGameUIVisible()) {

            hideAddGameUI();

        } else {

            pnlGlass.setVisible(true);

            addGameAnimator = new AAnimate(pnlAddGamePane);

            String soundEffectsSetting = auroraStorage.getStoredSettings()
                    .getSettingValue("sound_effects");
            if (soundEffectsSetting == null) {
                soundEffectsSetting = SettingsLogic.DEFAULT_SFX_SETTING;
            }

            if (soundEffectsSetting.equals("enabled")) {
                int num = 1 + (int) (Math.random() * ((3 - 1) + 1));
                ASound showSound = new ASound("swoop_" + num + ".wav", false);
                showSound.Play();
            }

            AThreadWorker addGameWorker = new AThreadWorker(
                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            buildAddGameUI();

                            if (main.LAUNCHES > 5 || auroraStorage
                                .getStoredLibrary()
                                .getNumberGames() > 0) {
                                btnManual.setSelected();
                                btnAuto.setUnSelected();
                            } else {
                                btnAuto.setSelected();
                                btnManual.setUnSelected();
                            }

                            txtSearchField_addUI.setText("");

                            coverArtStatusIndicator.setImgURl(
                                    "addUI_badge_idle.png");
                            gameLocationStatusIndicator.setImgURl(
                                    "addUI_badge_idle.png");

                            libraryLogic.getGameSearch_addUI().enableSearch();
                            libraryLogic.getGameSearch_addUI().resetCover();

                            addGameAnimator.setInitialLocation(
                                    (coreUI.getFrame().getWidth() / 2) -
                                    (pnlAddGamePane.getImgIcon()
                                    .getIconWidth() / 2), -390);
                        }
                    }, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            // Animate Down Add Game UI
                            addGameAnimator.moveVertical(0, 33);
                            pnlAddGamePane.revalidate();

                            txtSearchField_addUI.setFocusable(true);

                            addGameAnimator.addPostAnimationListener(
                                    new APostHandler() {
                                        @Override
                                        public void postAction() {
                                            addGameUI_Visible = true;
                                            txtSearchField_addUI.requestFocus();
                                        }
                                    });
                        }
                    });

            addGameWorker.startOnce();

        }
    }

    public void hideAddGameUI() {
        if (addGameUI_Visible) {
            addGameUI_Visible = false;

            btnAddGameToLib_addUI.setVisible(false);


            String soundEffectsSetting = auroraStorage.getStoredSettings()
                    .getSettingValue("sound_effects");
            if (soundEffectsSetting == null) {
                soundEffectsSetting = SettingsLogic.DEFAULT_SFX_SETTING;
            }

            int num = 1 + (int) (Math.random() * ((3 - 1) + 1));

            if (soundEffectsSetting.equals("enabled")) {
                ASound showSound = new ASound("reverse_swoop_" + num + ".wav",
                        false);
                showSound.Play();
            }

            // Animate Up Add Game UI
            addGameAnimator.addPostAnimationListener(new APostHandler() {
                @Override
                public void postAction() {
                    pnlGlass.setVisible(false);
                }
            });
            addGameAnimator.moveVertical(-492, 35);


            resetAddGameUI();


//            txtGridSearchField.requestFocus();
            coreUI.getFrame().requestFocus();


        }
    }

    /**
     * .-----------------------------------------------------------------------.
     * | switchToAutoMode()
     * .-----------------------------------------------------------------------.
     * |
     * | This method builds switches from the Manual Mode to the Auto Mode
     * | of adding games
     * |
     * .........................................................................
     *
     */
    public void switchToAutoMode() {

        if (!isAutoMode()) {

            setAutoMode(true);

            if (btnAddGameToLib_addUI.isVisible()) {
                libraryLogic.animateAddButtonUp();
            }

            if (libraryLogic.isAutoLoadedOnce()) {
                libraryLogic.autoClearAll();
            }

            // Attempt to start finding games on system
            libraryLogic.autoFindGames();

            // Remove whatever is currently there and add auto add pnl
            pnlAddGamePane.remove(1);
            pnlAddGamePane.add(pnlAutoAdd);
            try {
                pnlAddGamePane.setImageURL("addUI_bg2.png");
            } catch (MalformedURLException ex) {
                java.util.logging.Logger.getLogger(LibraryHandler.class
                        .getName()).
                        log(Level.SEVERE, null, ex);
            }
            pnlAddGamePane.revalidate();
            pnlAddGamePane.repaint();

        }

    }

    /**
     * .-----------------------------------------------------------------------.
     * | switchToManualMode()
     * .-----------------------------------------------------------------------.
     * |
     * | This method builds switches from the Auto Mode to the Manual Mode
     * | of adding games
     * |
     * .........................................................................
     *
     */
    public void switchToManualMode() {

        if (!isManualMode()) {

            setManualMode(true);

            // Check if the add game to library button is visible
            if (btnAddGameToLib_addUI.isVisible()) {
                libraryLogic.animateAddButtonUp();
            }

            // Check if already searched for games automatically
            if (libraryLogic.isAutoLoadedOnce()) {
                libraryLogic.autoClearAll();
            }

            libraryLogic.getGameSearch_addUI().resetCover();
            txtSearchField_addUI.setText("");

            // Remove whatever is currently there and add manual add pnl
            pnlAddGamePane.remove(1);
            pnlAddGamePane.add(pnlManualAdd);
            try {
                pnlAddGamePane.setImageURL("addUI_bg.png");
            } catch (MalformedURLException ex) {
                java.util.logging.Logger.getLogger(LibraryHandler.class
                        .getName()).
                        log(Level.SEVERE, null, ex);
            }
            pnlAddGamePane.revalidate();
            pnlAddGamePane.repaint();


            // Auto focus on the game name text field
            AThreadWorker wait = new AThreadWorker(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        java.util.logging.Logger.getLogger(
                                LibraryHandler.class.getName()).
                                log(Level.SEVERE, null, ex);
                    }
                    txtSearchField_addUI.requestFocusInWindow();

                }
            });
            wait.startOnce();
        }

    }

    public void resetAddGameUI() {

        resetManualModeIndicators();
        addGameAnimator.removeAllListeners();
        btnAddGameToLib_addUI.setVisible(false);
        btnAddGameToLib_addUI.repaint();
        addGameAnimator = null;

    }

    //
    // Auto Mode Indicators settings
    //
    public void resetAutoModeIndicator() {
        autoAddStatusIndicator.setImgURl("addUI_badge_idle.png");
        autoAddStatusEnabled = false;
    }

    public void setAutoInicator(Boolean indicatorState) {
        if (indicatorState) {
            autoAddStatusIndicator.setImgURl("addUI_badge_valid.png");
            autoAddStatusEnabled = true;
        } else {
            autoAddStatusIndicator.setImgURl("addUI_badge_invalid.png");
            autoAddStatusEnabled = false;
        }
    }

    //
    // Manual Mode Indicators settings
    //
    public void resetManualModeIndicators() {
        gameLocationStatusIndicator.setImgURl("addUI_badge_idle.png");
        coverArtStatusIndicator.setImgURl("addUI_badge_idle.png");
        gameLocationStatusEnabled = false;
        libraryLogic.checkManualAddGameStatus();
    }

    public void setGameLocationIndicator(Boolean indicatorState) {
        if (indicatorState) {
            gameLocationStatusIndicator.setImgURl("addUI_badge_valid.png");
            gameLocationStatusEnabled = true;
        } else {
            gameLocationStatusIndicator.setImgURl("addUI_badge_invalid.png");
            gameLocationStatusEnabled = false;
        }
        libraryLogic.checkManualAddGameStatus();
    }

    public void setCoverArtIndicator(Boolean indicatorState) {
        if (indicatorState) {
            coverArtStatusIndicator.setImgURl("addUI_badge_valid.png");
        } else {
            coverArtStatusIndicator.setImgURl("addUI_badge_invalid.png");
        }
        libraryLogic.checkManualAddGameStatus();
    }

    //
    // Getters and Setters
    //
    public AButton getAddGameToLibraryButton() {
        return btnAddGameToLib_addUI;
    }

    public JTextField getTxtSearchField_addUI() {
        return txtSearchField_addUI;
    }

    public AImagePane getPnlAddGamePane() {
        return pnlAddGamePane;
    }

    public boolean isManualMode() {
        return manualMode;
    }

    public void setManualMode(boolean isManualMode) {
        this.manualMode = isManualMode;
        this.autoMode = !isManualMode;
    }

    private boolean isAutoMode() {
        return autoMode;
    }

    private void setAutoMode(boolean isAutoMode) {
        this.autoMode = isAutoMode;
        this.manualMode = !isAutoMode;
    }

    public ARadioButton getBtnManual() {
        return btnManual;
    }

    public boolean isAutoAddStatusValid() {
        return autoAddStatusEnabled;
    }

    public boolean isCoverArtStatusIndicatorValid() {
        return coverArtStatusIndicator.getImgURl().equals(
                "addUI_badge_valid.png");
    }

    public boolean isGameLocationStatusValid() {
        return gameLocationStatusEnabled;
    }

    public void setCurrentGameLocation(String path) {
        gameFileChooser_addUI.setCurrentDirectory(new File(path));
    }

    public String getCurrentGameLocation() {
        return gameFileChooser_addUI.getCurrentDirectory().getPath();
    }

    public AButton getBtnAutoRefresh() {
        return btnAutoRefresh;
    }

    public JList<Object> getGamesList_addUI() {
        return gamesList_addUI;
    }

    public JList<Object> getGameList_autoUI() {
        return gameList_autoUI;
    }

    public DefaultListModel<Object> getModelCheckList() {
        return modelCheckList;
    }

    public JList<Object> getPnlCheckList() {
        return pnlCheckList;
    }

    public DefaultListModel<Object> getListModel_autoUI() {
        return listModel_autoUI;
    }
}
