/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aurora.V1.core.screen_ui;

import aurora.V1.core.AuroraCoreUI;
import aurora.V1.core.AuroraStorage;
import aurora.V1.core.Game;
import aurora.V1.core.GameSearch;
import aurora.V1.core.screen_handler.LibraryHandler;
import aurora.V1.core.screen_logic.LibraryLogic;
import aurora.V1.core.screen_logic.SettingsLogic;
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
import aurora.engine.V1.UI.ASlickLabel;
import aurora.engine.V1.UI.ATextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class EditGameUI {

    private final AuroraCoreUI coreUI;

    private final LibraryUI libraryUI;

    private final LibraryHandler libraryHandler;

    private final LibraryLogic libraryLogic;

    private final AuroraStorage auroraStorage;

    private JPanel pnlCenter_editUI;

    private AImagePane pnlRightPane;

    private JPanel pnlTopRightPane;

    private ASlickLabel lblCurrentName;

    private JPanel pnlCurrentName;

    private JPanel pnlCurrentImage;

    private JPanel pnlCenterRight;

    private AButton btnDone_editUI;

    private JPanel pnlLeftPane_editUI;

    private ASlickLabel lblGameLocation;

    private ASlickLabel lblGameCover;

    private ASlickLabel lblOther;

    private ARadioButton btnGameLocation;

    private ARadioButton btnGameCover;

    private ARadioButton btnOther;

    private AButton btnClose_editUI;

    private AImagePane pnlEditGamePane;

    private JPanel pnlTopPane_editUI;

    private JPanel pnlGameLocation_editUI;

    private JPanel pnlGameLocationTop;

    private ASlickLabel lblCurrentLocation_editUI;

    private ATextField txtCurrentLocation_editUI;

    private JPanel pnlGameLocationCenter;

    private JPanel pnlGameFileChooser_editUI;

    private JFileChooser gameFileChooser_editUI;

    private AImagePane imgCurrentGame_editUI;

    private AImage gameLocationStatusIndicator;

    private JPanel pnlGameLocationBottom;

    private ASlickLabel lblNewLocation_editUI;

    private ATextField txtNewLocation_editUI;

    private JPanel pnlGameCover_editUI;

    private JPanel pnlGameCoverCenter;

    private JPanel pnlGameCoverContainer;

    private AImagePane pnlCoverPane_editUI;

    private AImagePane pnlBlankCoverGame_editUI;

    private JList gamesList_editUI;

    private DefaultListModel listModel_editUI;

    private AImage gameCoverStatusIndicator;

    private JPanel pnlGameCoverBottom;

    private JPanel pnlGameCoverSearchContainer;

    private ASlickLabel lblGameCoverSearch;

    private ATextField txtGameCoverSearch_editUI;

    private AButton btnClearSearch_editUI;

    private AButton btnAutoSearchDB_editUI;

    private AImage autoSearchStatusIndictor;

    private boolean editUILoaded;

    private Game currentGameBeingEdited;

    private JLayeredPane pnlGlass;

    private final AddGameUI addGameUI;

    private AAnimate editGameAnimator;

    private boolean gameLocationChanged;

    private boolean gameCoverStatusEnabled;

    private boolean gameLocationStatusEnabled;

    public EditGameUI(AuroraCoreUI coreUI, LibraryUI libraryUI) {
        this.coreUI = coreUI;
        this.libraryUI = libraryUI;
        this.libraryHandler = libraryUI.getHandler();
        this.libraryLogic = libraryUI.getLogic();
        this.auroraStorage = libraryUI.getDashboardUI().getStorage();
        this.addGameUI = libraryUI.getAddGameUI();
    }

    /**
     * .-----------------------------------------------------------------------.
     * | loadEditGameUI()
     * .-----------------------------------------------------------------------.
     * |
     * | Initializes UI components
     * |
     * .........................................................................
     *
     */
    public void loadEditGameUI() {

        // Create Components
        // ----------------------------------------------------------------.


        // Get Glass Pane to Put UI On



        if (pnlGlass == null) {
            pnlGlass = coreUI.getFrame().getLayeredPane();
        }
        pnlEditGamePane = new AImagePane("editUI_bg.png",
                                         new BorderLayout());

        // Top Panel Components
        pnlTopPane_editUI = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 4));
        pnlTopPane_editUI.setOpaque(false);
        btnClose_editUI = new AButton("addUI_btnClose_norm.png",
                                      "addUI_btnClose_down.png",
                                      "addUI_btnClose_over.png");

        // Center Panel
        pnlCenter_editUI = new JPanel(new BorderLayout());
        pnlCenter_editUI.setOpaque(false);

        // Right Menu Pane
        pnlRightPane = new AImagePane("editUI_right.png");
        pnlRightPane.setPreferredSize(new Dimension(pnlRightPane
                .getRealImageWidth() + 5, pnlRightPane
                                                    .getRealImageHeight()));
        pnlRightPane.setLayout(new BoxLayout(pnlRightPane,
                                             BoxLayout.Y_AXIS));

        // Panel containing current Game cover with game name
        pnlTopRightPane = new JPanel();
        pnlTopRightPane.setLayout(new BoxLayout(pnlTopRightPane,
                                                BoxLayout.Y_AXIS));
        pnlTopRightPane.setOpaque(false);

        lblCurrentName = new ASlickLabel("Game Name");

        imgCurrentGame_editUI = new AImagePane("Blank-Case.png");
        imgCurrentGame_editUI.setImageSize(((imgCurrentGame_editUI
                .getRealImageWidth() / 4) + 3),
                                           (imgCurrentGame_editUI
                                           .getRealImageHeight() / 4));
        imgCurrentGame_editUI.setPreferredSize(new Dimension(
                imgCurrentGame_editUI.getImageWidth(), imgCurrentGame_editUI
                .getImageHeight() + 2));

        pnlCurrentName = new JPanel();
        pnlCurrentName.setOpaque(false);
        pnlCurrentName.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        pnlCurrentImage = new JPanel();
        pnlCurrentImage.setOpaque(false);
        pnlCurrentImage
                .setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        pnlCurrentImage.setPreferredSize(new Dimension(
                pnlRightPane.getRealImageWidth(), imgCurrentGame_editUI
                .getImageHeight() - 5));

        // Panel containing Buttons to go between diffrent settings
        pnlCenterRight = new JPanel();
        pnlCenterRight.setOpaque(false);
        pnlCenterRight.setLayout(new GridLayout(3, 1, 0, -6));

        btnGameLocation = new ARadioButton("editUI_btnSetting_norm.png",
                                           "editUI_btnSetting_down.png");
        btnGameLocation.setLayout(new BoxLayout(btnGameLocation,
                                                BoxLayout.Y_AXIS));

        btnGameCover = new ARadioButton("editUI_btnSetting_norm.png",
                                        "editUI_btnSetting_down.png");
        btnGameCover.setLayout(new BoxLayout(btnGameCover,
                                             BoxLayout.Y_AXIS));

        btnOther = new ARadioButton("editUI_btnSetting_norm.png",
                                    "editUI_btnSetting_down.png");
        btnOther.setLayout(new BoxLayout(btnOther,
                                         BoxLayout.Y_AXIS));

        lblGameLocation = new ASlickLabel(" Game Location ");
        lblGameCover = new ASlickLabel(" Box Art ");
        lblOther = new ASlickLabel(" Other ");

        // Button when setting is done, to save.
        btnDone_editUI = new AButton("editUI_btnDone_norm.png",
                                     "editUI_btnDone_down.png",
                                     "editUI_btnDone_over.png");

        // Left Content Pane
        pnlLeftPane_editUI = new JPanel();
        pnlLeftPane_editUI.setOpaque(false);

        // Game Location
        // ----------------------------------------------------------------.
        pnlGameLocation_editUI = new JPanel();
        pnlGameLocation_editUI.setOpaque(false);
        pnlGameLocation_editUI.setLayout(new BoxLayout(pnlGameLocation_editUI,
                                                       BoxLayout.Y_AXIS));

        //- Top
        pnlGameLocationTop = new JPanel(new FlowLayout(FlowLayout.RIGHT,
                                                       0, 0));
        pnlGameLocationTop.setOpaque(false);

        lblCurrentLocation_editUI = new ASlickLabel("Current Location");
        lblCurrentLocation_editUI.setForeground(Color.lightGray);
        lblCurrentLocation_editUI.setFont(coreUI.getRopaFont().deriveFont(
                Font.PLAIN, 21));

        txtCurrentLocation_editUI = new ATextField("editUI_textBar.png");
        txtCurrentLocation_editUI.setTextboxSize(0, 0);
        txtCurrentLocation_editUI.getTextBox().setEditable(false);
        txtCurrentLocation_editUI.getTextBox().setFont(coreUI
                .getRegularFont().deriveFont(
                        Font.PLAIN, 18));
        txtCurrentLocation_editUI.getTextBox().setForeground(new Color(
                0x46B8B8));

        //- Center
        pnlGameLocationCenter = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0,
                                                          10));
        pnlGameLocationCenter.setOpaque(false);

        pnlGameFileChooser_editUI = new JPanel();
        pnlGameFileChooser_editUI.setOpaque(true);

        gameFileChooser_editUI = new JFileChooser();

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
        SwingUtilities.updateComponentTreeUI(gameFileChooser_editUI);

        gameFileChooser_editUI.setApproveButtonText("Select");
        gameFileChooser_editUI.setDragEnabled(false);
        gameFileChooser_editUI.setDialogType(JFileChooser.OPEN_DIALOG);
        gameFileChooser_editUI.setMultiSelectionEnabled(false);
        gameFileChooser_editUI.setAcceptAllFileFilterUsed(true);
        gameFileChooser_editUI.setEnabled(true);
        gameFileChooser_editUI.revalidate();

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

        gameLocationStatusIndicator = new AImage("addUI_badge_idle.png");

        //- Bottom
        pnlGameLocationBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT,
                                                          0, 0));
        pnlGameLocationBottom.setOpaque(false);

        lblNewLocation_editUI = new ASlickLabel("New Location");
        lblNewLocation_editUI.setForeground(Color.lightGray);
        lblNewLocation_editUI.setFont(coreUI.getRopaFont().deriveFont(
                Font.PLAIN, 21));

        txtNewLocation_editUI = new ATextField("editUI_textBar.png");
        txtNewLocation_editUI.setTextboxSize(0, 0);
        txtNewLocation_editUI.getTextBox().setEditable(false);
        txtNewLocation_editUI.getTextBox().setFont(coreUI.getRegularFont()
                .deriveFont(
                        Font.PLAIN, 18));
        txtNewLocation_editUI.getTextBox().setForeground(new Color(70, 184, 70));

        // Game Box Art
        // ----------------------------------------------------------------.
        pnlGameCover_editUI = new JPanel();
        pnlGameCover_editUI.setOpaque(false);
        pnlGameCover_editUI.setLayout(new BoxLayout(pnlGameCover_editUI,
                                                    BoxLayout.Y_AXIS));

        //- Center
        pnlGameCoverCenter = new JPanel(new FlowLayout(FlowLayout.CENTER,
                                                       25, 20));
        pnlGameCoverCenter.setOpaque(false);

        pnlGameCoverContainer = new JPanel(new FlowLayout(FlowLayout.LEFT,
                                                          0, 0));
        pnlGameCoverContainer.setOpaque(false);

        pnlGameCoverCenter.setOpaque(false);

        pnlCoverPane_editUI = new AImagePane("addUI_game_bg.png",
                                             new FlowLayout(FlowLayout.RIGHT,
                                                            -10, 10));

        pnlBlankCoverGame_editUI = new AImagePane("Blank-Case.png", 240, 260);
        gamesList_editUI = new JList();
        listModel_editUI = new DefaultListModel();

        gameCoverStatusIndicator = new AImage("addUI_badge_idle.png");

        //- Bottom
        pnlGameCoverBottom = new JPanel(new FlowLayout(FlowLayout.CENTER,
                                                       14, 0));
        pnlGameCoverBottom.setOpaque(false);

        pnlGameCoverSearchContainer = new JPanel(new FlowLayout(FlowLayout.LEFT,
                                                                0, 0));
        pnlGameCoverSearchContainer.setOpaque(false);

        lblGameCoverSearch = new ASlickLabel("Game Name");
        txtGameCoverSearch_editUI = new ATextField("editUI_text_inactive.png",
                                                   "editUI_text_active.png");

        btnClearSearch_editUI = new AButton("addUI_btnClearText_norm.png",
                                            "addUI_btnClearText_down.png",
                                            "addUI_btnClearText_over.png");

        btnAutoSearchDB_editUI = new AButton("addUI_btn_autoSearch_norm.png",
                                             "addUI_btn_autoSearch_down.png",
                                             "addUI_btn_autoSearch_norm.png");

        autoSearchStatusIndictor = new AImage("addUI_img_autoSearchOn.png");

        btnClearSearch_editUI.setMargin(new Insets(0, 0, 0, 3));

        libraryLogic.getGameSearch_editUI().setUpGameSearch(
                pnlBlankCoverGame_editUI,
                pnlCoverPane_editUI,
                listModel_editUI, gameCoverStatusIndicator,
                txtGameCoverSearch_editUI
                .getTextBox());

        btnAutoSearchDB_editUI.addActionListener(
                libraryHandler.new GameSearchButtonListener(libraryLogic
                        .getGameSearch_editUI(), autoSearchStatusIndictor));
    }

    /**
     * .-----------------------------------------------------------------------.
     * | buildEditGameUI(Game game)
     * .-----------------------------------------------------------------------.
     * |
     * | builds and lays out EditGameUI with Game object in mind
     * |
     * .........................................................................
     *
     */
    public void buildEditGameUI(Game game) {



        if (!editUILoaded) {

            // Set Up Components
            // ----------------------------------------------------------------.
            currentGameBeingEdited = game;

            // Set up glass panel
            pnlGlass.setVisible(true);
            pnlGlass.setLayout(null);

            // Set Location for Edit Game UI panels
            pnlEditGamePane.setLocation((coreUI.getFrame().getWidth() / 2)
                                                - (addGameUI.getPnlAddGamePane()
                    .getImgIcon()
                    .getIconWidth() / 2), -380);
            pnlEditGamePane
                    .setSize(
                            new Dimension(addGameUI.getPnlAddGamePane()
                                    .getImgIcon()
                                    .getIconWidth(), addGameUI
                                          .getPnlAddGamePane().getImgIcon()
                                          .getIconHeight()));
            pnlEditGamePane.revalidate();

            // Add to Components
            // ----------------------------------------------------------------.
            // TOP PANEL COMPONENTS
            // Add the Close button to the Top most Panel
            pnlTopPane_editUI.add(btnClose_editUI);

            // Right Menu Pane
            lblCurrentName.setFont(coreUI.getRopaFont().deriveFont(
                    Font.PLAIN, 13));
            lblCurrentName.setForeground(Color.LIGHT_GRAY);
            lblCurrentName.setText("  " + currentGameBeingEdited
                    .getName());
            imgCurrentGame_editUI
                    .setImage(currentGameBeingEdited.getCoverImagePane()
                            .getImgIcon(),
                              imgCurrentGame_editUI.getImageWidth(),
                              imgCurrentGame_editUI
                              .getImageHeight());

            pnlCurrentImage.add(imgCurrentGame_editUI);
            pnlCurrentName.add(lblCurrentName);

            pnlCurrentImage.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            pnlCurrentName.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            pnlTopRightPane.add(Box.createVerticalGlue());
            pnlTopRightPane.add(pnlCurrentImage);
            pnlTopRightPane.add(pnlCurrentName);

            lblGameLocation.setFont(coreUI.getRopaFont().deriveFont(
                    Font.PLAIN, 25));
            lblGameLocation.setForeground(Color.lightGray);

            lblGameCover.setFont(coreUI.getRopaFont().deriveFont(
                    Font.PLAIN, 25));
            lblGameCover.setForeground(Color.lightGray);

            lblOther.setFont(coreUI.getRopaFont().deriveFont(
                    Font.PLAIN, 25));
            lblOther.setForeground(Color.lightGray);

            lblGameLocation.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            btnGameLocation.add(Box.createVerticalStrut(btnOther
                    .getRealImageHeight() / 4));
            btnGameLocation.add(lblGameLocation);

            lblGameCover.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            btnGameCover.add(Box.createVerticalStrut(btnOther
                    .getRealImageHeight() / 4));
            btnGameCover.add(lblGameCover);

            lblOther.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            btnOther.add(Box.createVerticalStrut(btnOther
                    .getRealImageHeight() / 4));
            btnOther.add(lblOther);

            ARadioButtonManager rdbManager = new ARadioButtonManager();
            rdbManager.addButton(btnGameLocation);
            rdbManager.addButton(btnGameCover);
            rdbManager.addButton(btnOther);
            rdbManager.setRadioButton();

            btnGameLocation.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
            btnGameCover.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
            btnOther.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
            pnlCenterRight.add(btnGameCover);
            pnlCenterRight.add(btnGameLocation);

//          pnlCenterRight_editUI.add(btnOther_editUI);
            pnlTopRightPane.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            pnlCenterRight.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            btnDone_editUI.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            pnlRightPane.add(pnlTopRightPane);
            pnlRightPane.add(pnlCenterRight);
            pnlRightPane.add(Box.createVerticalStrut(10));
            pnlRightPane.add(btnDone_editUI);
            pnlRightPane.add(Box.createVerticalStrut(25));
            //
            // Left Content Panel
            //
            try {
                try {
                    String name = "javax.swing.ClientPropertyKey";
                    Class<?> keyClazz = Class.forName(name);
                    Field field = keyClazz.getDeclaredField(
                            "PopupFactory_FORCE_HEAVYWEIGHT_POPUP");
                    field.setAccessible(true);
                    Object fieldValue = field.get(null);
                    gameFileChooser_editUI
                            .putClientProperty(fieldValue, true);
                } catch (Exception e) {
                    logger.error(e);
                }
            } catch (Exception e) {
                logger.error(e);
                e.printStackTrace();
            }

            // Game Location
            // ----------------------------------------------------------------.

            //Sizes
            pnlGameFileChooser_editUI
                    .setPreferredSize(new Dimension(txtCurrentLocation_editUI
                                    .getImageWidth(),
                                                    pnlEditGamePane
                                                    .getRealImageHeight() / 2
                                                            + 15));
            pnlGameFileChooser_editUI.setBackground(new Color(38, 46, 60));

            gameFileChooser_editUI.setPreferredSize(new Dimension(
                    pnlGameFileChooser_editUI
                    .getPreferredSize().width,
                    pnlGameFileChooser_editUI
                    .getPreferredSize().height));

            pnlGameFileChooser_editUI.add(gameFileChooser_editUI);

            // Top
            pnlGameLocationTop.add(lblCurrentLocation_editUI);
            pnlGameLocationTop.add(txtCurrentLocation_editUI);

            // Center
            pnlGameLocationCenter.add(gameLocationStatusIndicator);
            pnlGameLocationCenter.add(Box.createHorizontalStrut(40));
            pnlGameLocationCenter.add(pnlGameFileChooser_editUI);

            // Bottom
            pnlGameLocationBottom.add(lblNewLocation_editUI);
            pnlGameLocationBottom.add(txtNewLocation_editUI);

            pnlGameLocationTop.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            pnlGameLocationCenter.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            pnlGameLocationBottom.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            pnlGameLocation_editUI.add(Box.createVerticalStrut(25));
            pnlGameLocation_editUI.add(pnlGameLocationTop);
            pnlGameLocation_editUI.add(pnlGameLocationCenter);
            pnlGameLocation_editUI.add(pnlGameLocationBottom);

            //
            // Add major panels to component
            //
            pnlCenter_editUI.add(pnlRightPane, BorderLayout.EAST);
            pnlCenter_editUI.add(pnlLeftPane_editUI, BorderLayout.CENTER);

            pnlEditGamePane.add(pnlTopPane_editUI, BorderLayout.PAGE_START);
            pnlEditGamePane.add(pnlCenter_editUI, BorderLayout.CENTER);

            pnlGlass.add(pnlEditGamePane, JLayeredPane.MODAL_LAYER);

            // Game Cover
            // ----------------------------------------------------------------.

            //
            // Set up Game List
            //
            gamesList_editUI.setPreferredSize(
                    new Dimension(pnlCoverPane_editUI.getImgIcon()
                            .getIconWidth() + 110,
                                  pnlCoverPane_editUI.getImgIcon()
                                  .getIconHeight()));
            gamesList_editUI.setForeground(Color.lightGray);
            gamesList_editUI.setBackground(new Color(38, 46, 60));
            gamesList_editUI.setSelectionBackground(new Color(54, 95, 143));
            gamesList_editUI.setSelectionForeground(new Color(238, 243, 249));
            gamesList_editUI.setFont(coreUI.getDefaultFont().deriveFont(
                    Font.BOLD,
                    LibraryUI.getListFontSize()));
            gamesList_editUI.setSelectionMode(
                    ListSelectionModel.SINGLE_SELECTION);
            gamesList_editUI.setLayoutOrientation(JList.VERTICAL);
            gamesList_editUI.setVisibleRowCount(10);

            gamesList_editUI
                    .setCellRenderer(libraryHandler.new GameListRender());
            gamesList_editUI.setModel(listModel_editUI);

            pnlGameCoverContainer
                    .setPreferredSize(new Dimension(pnlEditGamePane
                                    .getImgIcon().getIconWidth() / 2,
                                                    pnlCoverPane_editUI
                                                    .getImgIcon()
                                                    .getIconHeight()));

            pnlCoverPane_editUI.setPreferredSize(new Dimension(
                    pnlCoverPane_editUI
                    .getImgIcon()
                    .getIconWidth(), pnlCoverPane_editUI.getImgIcon()
                    .getIconHeight()));

            pnlBlankCoverGame_editUI.setPreferredSize(new Dimension(240, 260));

            pnlCoverPane_editUI.add(pnlBlankCoverGame_editUI);

            pnlGameCoverContainer.add(pnlCoverPane_editUI);
            pnlGameCoverContainer.add(gamesList_editUI);

            pnlGameCoverCenter.add(gameCoverStatusIndicator);
            pnlGameCoverCenter.add(Box.createHorizontalStrut(19));
            pnlGameCoverCenter.add(pnlGameCoverContainer);

            // Bottom
            lblGameCoverSearch.setForeground(Color.LIGHT_GRAY);
            lblGameCoverSearch.setFont(coreUI.getRopaFont().deriveFont(
                    Font.PLAIN, 22));

            txtGameCoverSearch_editUI.getTextBox().setForeground(Color.darkGray);
            txtGameCoverSearch_editUI.getTextBox().setFont(coreUI
                    .getRopaFont()
                    .deriveFont(
                            Font.PLAIN, 28));
            txtGameCoverSearch_editUI.setText(GameSearch.DEFAULT_SEARCH_TEXT);
            txtGameCoverSearch_editUI.setTextboxSize(0, 0);

            txtGameCoverSearch_editUI.add(Box.createHorizontalStrut(10),
                                          BorderLayout.WEST);
            txtGameCoverSearch_editUI.add(btnClearSearch_editUI,
                                          BorderLayout.EAST);

            btnAutoSearchDB_editUI.add(autoSearchStatusIndictor);

            pnlGameCoverSearchContainer.add(txtGameCoverSearch_editUI);
            pnlGameCoverSearchContainer.add(btnAutoSearchDB_editUI);

            pnlGameCoverBottom.add(lblGameCoverSearch);
            pnlGameCoverBottom.add(pnlGameCoverSearchContainer);

            // Add to Game Cover Edit main Panel
            pnlGameCover_editUI.add(Box.createVerticalStrut(25));
            pnlGameCover_editUI.add(pnlGameCoverCenter);
            pnlGameCover_editUI.add(pnlGameCoverBottom);

            editUILoaded = true;

            // Handlers
            // ----------------------------------------------------------------.
            btnClose_editUI.addActionListener(
                    libraryHandler.new HideEditAddUIHandler(
                            libraryUI));
            pnlEditGamePane.addMouseListener(
                    libraryHandler.new EmptyMouseHandler());

            btnGameLocation.setSelectedHandler(
                    libraryHandler.new GameLocationSettingListener(
                            lblGameLocation));
            btnGameLocation.setUnSelectedHandler(
                    libraryHandler.new UnselectSettingListener(
                            lblGameLocation));

            btnGameCover.setSelectedHandler(
                    libraryHandler.new GameCoverSettingListener(
                            lblGameCover));
            btnGameCover.setUnSelectedHandler(
                    libraryHandler.new UnselectSettingListener(
                            lblGameCover));

            btnOther.setSelectedHandler(
                    libraryHandler.new OtherSettingListener(lblOther));
            btnOther.setUnSelectedHandler(
                    libraryHandler.new UnselectSettingListener(lblOther));

            txtGameCoverSearch_editUI.getTextBox()
                    .addFocusListener(
                            libraryHandler.new GameSearchBoxFocusHandler(
                                    txtGameCoverSearch_editUI.getTextBox(),
                                    txtGameCoverSearch_editUI, libraryLogic
                                    .getGameSearch_editUI()));
            txtGameCoverSearch_editUI.getTextBox()
                    .addMouseListener(
                            libraryHandler.new GameSearchBoxMouseHandler(
                                    txtGameCoverSearch_editUI.getTextBox(),
                                    txtGameCoverSearch_editUI, libraryLogic
                                    .getGameSearch_editUI()));

            txtGameCoverSearch_editUI.getTextBox().getDocument()
                    .addDocumentListener(
                            libraryHandler.new GameSearchBoxChangeHandler(
                                    libraryLogic
                                    .getGameSearch_editUI(),
                                    txtGameCoverSearch_editUI.getTextBox()));

            btnClearSearch_editUI.addActionListener(
                    libraryHandler.new GameSearchBoxClear(
                            txtGameCoverSearch_editUI
                            .getTextBox(), libraryLogic
                            .getGameSearch_editUI()));

            gamesList_editUI.addListSelectionListener(
                    libraryHandler.new SelectListHandler(libraryLogic
                            .getGameSearch_editUI()));

            gameFileChooser_editUI.setFileFilter(
                    libraryHandler.new ExecutableFilterHandler());
            gameFileChooser_editUI
                    .addActionListener(
                            libraryHandler.new GameEditFileChooserHandler(
                                    gameFileChooser_editUI));

            btnDone_editUI.addActionListener(
                    libraryHandler.new EditSettingDoneHandler());
        } else if (!lblCurrentName.getText().trim().equals(
                game.getGameName())) {
            currentGameBeingEdited = game;
            lblCurrentName.setText("  " + currentGameBeingEdited
                    .getName());
            imgCurrentGame_editUI
                    .setImage(currentGameBeingEdited.getCoverImagePane()
                            .getImgIcon(),
                              imgCurrentGame_editUI.getImageWidth(),
                              imgCurrentGame_editUI
                              .getImageHeight());

        }

        // reset UI
        txtNewLocation_editUI.setText("");
        gameLocationStatusIndicator.setImgURl("addUI_badge_idle.png");
        gameCoverStatusIndicator.setImgURl("addUI_badge_idle.png");
        libraryLogic.getGameSearch_editUI().resetCover();

        // First to be selected when edit UI summoned
        if (!btnGameLocation.isSelected
                    && !btnGameCover.isSelected
                    && !btnOther.isSelected) {
            btnGameCover.setSelected();
        }

    }

    //
    // Auto Mode Indicators settings
    //
    public void resetGameCoverIndicator() {
        gameCoverStatusIndicator.setImgURl("addUI_badge_idle.png");
    }

    public void setGameCoverInicator(Boolean indicatorState) {
        if (indicatorState) {
            gameCoverStatusIndicator.setImgURl("addUI_badge_valid.png");
            gameCoverStatusEnabled = true;
        } else {
            gameCoverStatusIndicator.setImgURl("addUI_badge_invalid.png");
            gameCoverStatusEnabled = false;
        }
    }

    public void resetGameLocationIndicator() {
        gameLocationStatusIndicator.setImgURl("addUI_badge_idle.png");
    }

    public void setGameLocationInicator(Boolean indicatorState) {
        if (indicatorState) {
            gameLocationStatusIndicator.setImgURl("addUI_badge_valid.png");
            gameLocationStatusEnabled = true;
        } else {
            gameLocationStatusIndicator.setImgURl("addUI_badge_invalid.png");
            gameLocationStatusEnabled = false;
        }
    }

    /**
     * .-----------------------------------------------------------------------.
     * | showEditGameUI(Game game)
     * .-----------------------------------------------------------------------.
     * |
     * | Shows the edit game UI by sliding the UI down
     * |
     * .........................................................................
     *
     */
    public void showEditGameUI(final Game game) {

        // Check if other UI panels are already visible and hide them
        // Also hide and reshow based on whether a diffrent game is being edited
        if (libraryUI.isAddGameUIVisible()) {
            addGameUI.hideAddGameUI();
            showEditGameUI(game);
        } else if (libraryUI.isEditGameUIVisible() && currentGameBeingEdited
                                                              != game) {
            hideEditGameUI();

            // Wait till after the hide animation to reshow the edit UI
            editGameAnimator.appendPostAnimationListener(new APostHandler() {

                @Override
                public void doAction() {
                    try {
                        Thread.sleep(100);
                        showEditGameUI(game);
                        currentGameBeingEdited = game;
                    } catch (InterruptedException ex) {
                        Logger.getLogger(EditGameUI.class.getName()).log(
                                Level.SEVERE,
                                null,
                                ex);
                    }
                }
            });

        } else if (libraryUI.isEditGameUIVisible()) {
            hideEditGameUI();
        } else {

            pnlGlass.setVisible(true);
            libraryUI.setEditGameUI_Visible(true);

            editGameAnimator = new AAnimate(pnlEditGamePane);

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

            AThreadWorker editGameWorker = new AThreadWorker(
                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            buildEditGameUI(game);

                        }
                    }, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {


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

    /**
     * .-----------------------------------------------------------------------.
     * | showEditGameUI(Game game)
     * .-----------------------------------------------------------------------.
     * |
     * | Shows the edit game UI by sliding the UI down
     * |
     * .........................................................................
     *
     */
    public void hideEditGameUI() {

        if (libraryUI.isEditGameUIVisible()) {

            libraryUI.setEditGameUI_Visible(false);

            String soundEffectsSetting = auroraStorage.getStoredSettings()
                    .getSettingValue("sound_effects");
            if (soundEffectsSetting == null) {
                soundEffectsSetting = SettingsLogic.DEFAULT_SFX_SETTING;
            }

            if (soundEffectsSetting.equals("enabled")) {
                int num = 1 + (int) (Math.random() * ((3 - 1) + 1));
                ASound showSound = new ASound("reverse_swoop_" + num + ".wav",
                                              false);
                showSound.Play();
            }

            // Animate Up Add Game UI

            editGameAnimator.appendPostAnimationListener(new APostHandler() {
                @Override
                public void doAction() {
                    txtGameCoverSearch_editUI.setText("");
                    pnlGlass.setVisible(false);
                }
            });
            editGameAnimator.moveVertical(-492, 35);


            editGameAnimator.removeAllListeners();

            btnGameLocation.setUnSelected();
            btnGameCover.setUnSelected();
            btnOther.setUnSelected();



        }

    }

    /**
     * Show the Game Location change UI
     */
    public void showGameLocationUI() {

        if (libraryUI.isEditGameUIVisible()) {

            pnlLeftPane_editUI.removeAll();

            final File location = new File(currentGameBeingEdited.getGamePath());

            SwingUtilities.invokeLater(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread
                                .sleep(700);
                            } catch (InterruptedException ex) {
                                java.util.logging.Logger
                                .getLogger(
                                        LibraryUI.class
                                        .getName())
                                .log(Level.SEVERE,
                                     null, ex);
                            }
                            gameFileChooser_editUI
                            .setCurrentDirectory(
                                    location);
                        }
                    });

            txtCurrentLocation_editUI.setText(location.getAbsolutePath());

            pnlLeftPane_editUI.add(pnlGameLocation_editUI);
            pnlLeftPane_editUI.revalidate();
            pnlLeftPane_editUI.repaint();

            pnlGameLocation_editUI.revalidate();
            pnlGameLocation_editUI.repaint();

        }

    }

    /**
     * Show the Game Box Art change UI
     */
    public void showGameCoverUI() {
        if (libraryUI.isEditGameUIVisible()) {

            pnlLeftPane_editUI.removeAll();
            pnlLeftPane_editUI.revalidate();
            pnlLeftPane_editUI.repaint();

            pnlLeftPane_editUI.add(pnlGameCover_editUI);
            pnlLeftPane_editUI.revalidate();
            pnlLeftPane_editUI.repaint();


            libraryLogic.getGameSearch_editUI().enableSearch();
            libraryLogic.getGameSearch_editUI().resetCover();
            libraryLogic.getGameSearch_editUI().resetText();
            this.resetGameCoverIndicator();
//            txtGameCoverSearch_editUI.getTextBox().requestFocusInWindow();
//            btnDone_editUI.requestFocusInWindow();
        }
    }

    /**
     * Show the Other settings UI
     */
    public void showOtherUI() {
        if (libraryUI.isEditGameUIVisible()) {

            pnlLeftPane_editUI.removeAll();
            pnlLeftPane_editUI.revalidate();
            pnlLeftPane_editUI.repaint();
        }
    }

    //
    // Setters and Getters
    //
    public boolean isGameCoverChanged() {
        if (txtGameCoverSearch_editUI.getText().length() > 0
                    && !txtGameCoverSearch_editUI.getText().equals(
                        GameSearch.DEFAULT_SEARCH_TEXT)) {
            return true;
        } else {
            return false;
        }
    }

    public void setEditGameLocationChanged(boolean isGameLocationChanged) {
        this.gameLocationChanged = isGameLocationChanged;
    }

    public Game getCurrentGame_editUI() {
        return currentGameBeingEdited;
    }

    public JFileChooser getGameFileChooser_editUI() {
        return gameFileChooser_editUI;
    }

    public ATextField getTxtCurrentLocation_editUI() {
        return txtCurrentLocation_editUI;
    }

    public ATextField getTxtNewLocation_editUI() {
        return txtNewLocation_editUI;
    }

    public boolean isGameCoverStatusEnabled() {
        return gameCoverStatusEnabled;
    }

    public AImage getAutoSearchStatusIndictor() {
        return autoSearchStatusIndictor;
    }

    public AImage getGameCoverStatusIndicator() {
        return gameCoverStatusIndicator;
    }

    public boolean isGameLocationChanged() {
        return gameLocationChanged;
    }

    public AImage getGameLocationStatusIndicator() {
        return gameLocationStatusIndicator;
    }

    public boolean isGameLocationStatusEnabled() {
        return gameLocationStatusEnabled;
    }

}
