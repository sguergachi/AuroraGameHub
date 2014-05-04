/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aurora.V1.core.screen_ui;

import aurora.V1.core.AuroraCoreUI;
import aurora.V1.core.AuroraStorage;
import aurora.V1.core.Game;
import aurora.V1.core.screen_handler.LibraryHandler;
import aurora.V1.core.screen_logic.LibraryLogic;
import static aurora.V1.core.screen_ui.LibraryUI.logger;
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
import java.lang.reflect.Field;
import java.util.logging.Level;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
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

    public EditGameUI(AuroraCoreUI coreUI, LibraryUI libraryUI) {
        this.coreUI = coreUI;
        this.libraryUI = libraryUI;
        this.libraryHandler = libraryUI.getHandler();
        this.libraryLogic = libraryUI.getLogic();
        this.auroraStorage = libraryUI.getDashboardUI().getStorage();
    }



    private void loadEditGameUI() {

        // Create Components
        // ----------------------------------------------------------------.
        //* Get Glass Pane to Put UI On *//
        if (pnlGlass == null) {
            pnlGlass = (JPanel) coreUI.getFrame().getGlassPane();
        }
        pnlEditGamePane = new AImagePane("editUI_bg.png",
                new BorderLayout());

        //* Top Panel Components *//
        pnlTopPane_editUI = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 4));
        pnlTopPane_editUI.setOpaque(false);
        btnClose_editUI = new AButton("addUI_btnClose_norm.png",
                "addUI_btnClose_down.png",
                "addUI_btnClose_over.png");

        //* Center Panel *//
        pnlCenter_editUI = new JPanel(new BorderLayout());
        pnlCenter_editUI.setOpaque(false);

        //* Right Menu Pane *//
        pnlRightPane_editUI = new AImagePane("editUI_right.png");
        pnlRightPane_editUI.setPreferredSize(new Dimension(pnlRightPane_editUI
                .getRealImageWidth() + 5, pnlRightPane_editUI
                .getRealImageHeight()));
        pnlRightPane_editUI.setLayout(new BoxLayout(pnlRightPane_editUI,
                BoxLayout.Y_AXIS));

        // Panel containing current Game cover with game name
        pnlTopRightPane_editUI = new JPanel();
        pnlTopRightPane_editUI.setLayout(new BoxLayout(pnlTopRightPane_editUI,
                BoxLayout.Y_AXIS));
        pnlTopRightPane_editUI.setOpaque(false);

        lblCurrentName_editUI = new ASlickLabel("Game Name");

        imgCurrentGame_editUI = new AImagePane("Blank-Case.png");
        imgCurrentGame_editUI.setImageSize(((imgCurrentGame_editUI
                .getRealImageWidth() / 4) + 3),
                (imgCurrentGame_editUI
                .getRealImageHeight() / 4));
        imgCurrentGame_editUI.setPreferredSize(new Dimension(
                imgCurrentGame_editUI.getImageWidth(), imgCurrentGame_editUI
                .getImageHeight() + 2));

        pnlCurrentName_editUI = new JPanel();
        pnlCurrentName_editUI.setOpaque(false);
        pnlCurrentName_editUI.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        pnlCurrentImage_editUI = new JPanel();
        pnlCurrentImage_editUI.setOpaque(false);
        pnlCurrentImage_editUI
                .setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        pnlCurrentImage_editUI.setPreferredSize(new Dimension(
                pnlRightPane_editUI.getRealImageWidth(), imgCurrentGame_editUI
                .getImageHeight() - 5));

        // Panel containing Buttons to go between diffrent settings
        pnlCenterRight_editUI = new JPanel();
        pnlCenterRight_editUI.setOpaque(false);
        pnlCenterRight_editUI.setLayout(new GridLayout(3, 1, 0, -6));

        btnGameLocation_editUI = new ARadioButton("editUI_btnSetting_norm.png",
                "editUI_btnSetting_down.png");
        btnGameLocation_editUI.setLayout(new BoxLayout(btnGameLocation_editUI,
                BoxLayout.Y_AXIS));

        btnGameCover_editUI = new ARadioButton("editUI_btnSetting_norm.png",
                "editUI_btnSetting_down.png");
        btnGameCover_editUI.setLayout(new BoxLayout(btnGameCover_editUI,
                BoxLayout.Y_AXIS));

        btnOther_editUI = new ARadioButton("editUI_btnSetting_norm.png",
                "editUI_btnSetting_down.png");
        btnOther_editUI.setLayout(new BoxLayout(btnOther_editUI,
                BoxLayout.Y_AXIS));

        lblGameLocation_editUI = new ASlickLabel(" Game Location ");
        lblGameCover_editUI = new ASlickLabel(" Box Art ");
        lblOther_editUI = new ASlickLabel(" Other ");

        // Button when setting is done, to save.
        btnDone_editUI = new AButton("editUI_btnDone_norm.png",
                "editUI_btnDone_down.png",
                "editUI_btnDone_over.png");

        //* Left Content Pane *//
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
        lblCurrentLocation_editUI.setFont(getCoreUI().getRopaFont().deriveFont(
                Font.PLAIN, 21));

        txtCurrentLocation_editUI = new ATextField("editUI_textBar.png");
        txtCurrentLocation_editUI.setTextboxSize(0, 0);
        txtCurrentLocation_editUI.getTextBox().setEditable(false);
        txtCurrentLocation_editUI.getTextBox().setFont(getCoreUI()
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

        //* Set up File Chooser *//
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

        imgGameLocationStatus = new AImage("addUI_badge_idle.png");

        //- Bottom
        pnlGameLocationBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT,
                0, 0));
        pnlGameLocationBottom.setOpaque(false);

        lblNewLocation_editUI = new ASlickLabel("New Location");
        lblNewLocation_editUI.setForeground(Color.lightGray);
        lblNewLocation_editUI.setFont(getCoreUI().getRopaFont().deriveFont(
                Font.PLAIN, 21));

        txtNewLocation_editUI = new ATextField("editUI_textBar.png");
        txtNewLocation_editUI.setTextboxSize(0, 0);
        txtNewLocation_editUI.getTextBox().setEditable(false);
        txtNewLocation_editUI.getTextBox().setFont(getCoreUI().getRegularFont()
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

        imgGameCoverStatus = new AImage("addUI_badge_idle.png");

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

        imgAutoSearchStatus_editUI = new AImage("addUI_img_autoSearchOn.png");

        btnClearSearch_editUI.setMargin(new Insets(0, 0, 0, 3));

        libraryLogic.getGameSearch_editUI().setUpGameSearch(
                pnlBlankCoverGame_editUI,
                pnlCoverPane_editUI,
                listModel_editUI, imgGameCoverStatus, txtGameCoverSearch_editUI
                .getTextBox());

        btnAutoSearchDB_editUI.addActionListener(
                libraryHandler.new GameSearchButtonListener(libraryLogic
                        .getGameSearch_editUI(), imgAutoSearchStatus_editUI));
    }

    public void buildEditGameUI(Game game) {



        if (!editUILoaded) {

            // Set Up Components
            // ----------------------------------------------------------------.
            currentGame_editUI = game;

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
                                    .getIconWidth(), pnlAddGamePane.getImgIcon()
                                    .getIconHeight()));
            pnlEditGamePane.revalidate();

            // Add to Components
            // ----------------------------------------------------------------.
            //* TOP PANEL COMPONENTS *//
            //* Add the Close button to the Top most Panel *//
            pnlTopPane_editUI.add(btnClose_editUI);

            //* Right Menu Pane *//
            lblCurrentName_editUI.setFont(getCoreUI().getRopaFont().deriveFont(
                    Font.PLAIN, 13));
            lblCurrentName_editUI.setForeground(Color.LIGHT_GRAY);
            lblCurrentName_editUI.setText("  " + currentGame_editUI.getName());
            imgCurrentGame_editUI
                    .setImage(currentGame_editUI.getCoverImagePane()
                            .getImgIcon(),
                            imgCurrentGame_editUI.getImageWidth(),
                            imgCurrentGame_editUI
                            .getImageHeight());

            pnlCurrentImage_editUI.add(imgCurrentGame_editUI);
            pnlCurrentName_editUI.add(lblCurrentName_editUI);

            pnlCurrentImage_editUI.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            pnlCurrentName_editUI.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            pnlTopRightPane_editUI.add(Box.createVerticalGlue());
            pnlTopRightPane_editUI.add(pnlCurrentImage_editUI);
            pnlTopRightPane_editUI.add(pnlCurrentName_editUI);

            lblGameLocation_editUI.setFont(getCoreUI().getRopaFont().deriveFont(
                    Font.PLAIN, 25));
            lblGameLocation_editUI.setForeground(Color.lightGray);

            lblGameCover_editUI.setFont(getCoreUI().getRopaFont().deriveFont(
                    Font.PLAIN, 25));
            lblGameCover_editUI.setForeground(Color.lightGray);

            lblOther_editUI.setFont(getCoreUI().getRopaFont().deriveFont(
                    Font.PLAIN, 25));
            lblOther_editUI.setForeground(Color.lightGray);

            lblGameLocation_editUI.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            btnGameLocation_editUI.add(Box.createVerticalStrut(btnOther_editUI
                    .getRealImageHeight() / 4));
            btnGameLocation_editUI.add(lblGameLocation_editUI);

            lblGameCover_editUI.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            btnGameCover_editUI.add(Box.createVerticalStrut(btnOther_editUI
                    .getRealImageHeight() / 4));
            btnGameCover_editUI.add(lblGameCover_editUI);

            lblOther_editUI.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            btnOther_editUI.add(Box.createVerticalStrut(btnOther_editUI
                    .getRealImageHeight() / 4));
            btnOther_editUI.add(lblOther_editUI);

            ARadioButtonManager rdbManager = new ARadioButtonManager();
            rdbManager.addButton(btnGameLocation_editUI);
            rdbManager.addButton(btnGameCover_editUI);
            rdbManager.addButton(btnOther_editUI);
            rdbManager.setRadioButton();

            btnGameLocation_editUI.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
            btnGameCover_editUI.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
            btnOther_editUI.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
            pnlCenterRight_editUI.add(btnGameCover_editUI);
            pnlCenterRight_editUI.add(btnGameLocation_editUI);

//          pnlCenterRight_editUI.add(btnOther_editUI);
            pnlTopRightPane_editUI.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            pnlCenterRight_editUI.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            btnDone_editUI.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            pnlRightPane_editUI.add(pnlTopRightPane_editUI);
            pnlRightPane_editUI.add(pnlCenterRight_editUI);
            pnlRightPane_editUI.add(Box.createVerticalStrut(10));
            pnlRightPane_editUI.add(btnDone_editUI);
            pnlRightPane_editUI.add(Box.createVerticalStrut(25));
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
                                    .getRealImageHeight() / 2 + 15));
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
            pnlGameLocationCenter.add(imgGameLocationStatus);
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
            pnlCenter_editUI.add(pnlRightPane_editUI, BorderLayout.EAST);
            pnlCenter_editUI.add(pnlLeftPane_editUI, BorderLayout.CENTER);

            pnlEditGamePane.add(pnlTopPane_editUI, BorderLayout.PAGE_START);
            pnlEditGamePane.add(pnlCenter_editUI, BorderLayout.CENTER);

            pnlGlass.add(pnlEditGamePane);

            // Game Cover
            // ----------------------------------------------------------------.

            //
            // Set up Game List
            //
            gamesList_editUI.setPreferredSize(
                    new Dimension(pnlCoverPane_editUI.getImgIcon()
                            .getIconWidth()
                                          + 110,
                            pnlCoverPane_editUI.getImgIcon()
                            .getIconHeight()));
            gamesList_editUI.setForeground(Color.lightGray);
            gamesList_editUI.setBackground(new Color(38, 46, 60));
            gamesList_editUI.setSelectionBackground(new Color(54, 95, 143));
            gamesList_editUI.setSelectionForeground(new Color(238, 243, 249));
            gamesList_editUI.setFont(coreUI.getDefaultFont().deriveFont(
                    Font.BOLD,
                    listFontSize));
            gamesList_editUI.setSelectionMode(
                    ListSelectionModel.SINGLE_SELECTION);
            gamesList_editUI.setLayoutOrientation(JList.VERTICAL);
            gamesList_editUI.setVisibleRowCount(10);

            gamesList_editUI.setCellRenderer(libraryHandler.new listRender());
            gamesList_editUI.setModel(listModel_editUI);

            pnlGameCoverContainer
                    .setPreferredSize(new Dimension(pnlEditGamePane
                                    .getImgIcon().getIconWidth() / 2,
                                    pnlCoverPane_editUI
                                    .getImgIcon().getIconHeight()));

            pnlCoverPane_editUI.setPreferredSize(new Dimension(
                    pnlCoverPane_editUI
                    .getImgIcon()
                    .getIconWidth(), pnlCoverPane_editUI.getImgIcon()
                    .getIconHeight()));

            pnlBlankCoverGame_editUI.setPreferredSize(new Dimension(240, 260));

            pnlCoverPane_editUI.add(pnlBlankCoverGame_editUI);

            pnlGameCoverContainer.add(pnlCoverPane_editUI);
            pnlGameCoverContainer.add(gamesList_editUI);

            pnlGameCoverCenter.add(imgGameCoverStatus);
            pnlGameCoverCenter.add(Box.createHorizontalStrut(19));
            pnlGameCoverCenter.add(pnlGameCoverContainer);

            // Bottom
            lblGameCoverSearch.setForeground(Color.LIGHT_GRAY);
            lblGameCoverSearch.setFont(getCoreUI().getRopaFont().deriveFont(
                    Font.PLAIN, 22));

            txtGameCoverSearch_editUI.getTextBox().setForeground(Color.gray);
            txtGameCoverSearch_editUI.getTextBox().setFont(getCoreUI()
                    .getRopaFont()
                    .deriveFont(
                            Font.PLAIN, 28));
            txtGameCoverSearch_editUI.setText("Search For Game...");
            txtGameCoverSearch_editUI.setTextboxSize(0, 0);

            txtGameCoverSearch_editUI.add(Box.createHorizontalStrut(10),
                    BorderLayout.WEST);
            txtGameCoverSearch_editUI.add(btnClearSearch_editUI,
                    BorderLayout.EAST);

            btnAutoSearchDB_editUI.add(imgAutoSearchStatus_editUI);

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
                            this));
            pnlEditGamePane.addMouseListener(
                    libraryHandler.new EmptyMouseHandler());

            btnGameLocation_editUI.setSelectedHandler(
                    libraryHandler.new GameLocationSettingListener(
                            lblGameLocation_editUI));
            btnGameLocation_editUI.setUnSelectedHandler(
                    libraryHandler.new UnselectSettingListener(
                            lblGameLocation_editUI));

            btnGameCover_editUI.setSelectedHandler(
                    libraryHandler.new GameCoverSettingListener(
                            lblGameCover_editUI));
            btnGameCover_editUI.setUnSelectedHandler(
                    libraryHandler.new UnselectSettingListener(
                            lblGameCover_editUI));

            btnOther_editUI.setSelectedHandler(
                    libraryHandler.new OtherSettingListener(lblOther_editUI));
            btnOther_editUI.setUnSelectedHandler(
                    libraryHandler.new UnselectSettingListener(lblOther_editUI));

            txtGameCoverSearch_editUI.getTextBox()
                    .addFocusListener(libraryHandler.new AddGameFocusHandler(
                                    txtGameCoverSearch_editUI.getTextBox(),
                                    txtGameCoverSearch_editUI, libraryLogic
                                    .getGameSearch_editUI()));
            txtGameCoverSearch_editUI.getTextBox()
                    .addMouseListener(libraryHandler.new AddGameMouseHandler(
                                    txtGameCoverSearch_editUI.getTextBox(),
                                    txtGameCoverSearch_editUI, libraryLogic
                                    .getGameSearch_editUI()));

            txtGameCoverSearch_editUI.getTextBox().getDocument()
                    .addDocumentListener(
                            libraryHandler.new AddGameSearchBoxHandler(
                                    libraryLogic
                                    .getGameSearch_editUI(),
                                    txtGameCoverSearch_editUI.getTextBox()));

            btnClearSearch_editUI.addActionListener(
                    libraryHandler.new AddGameSearchClear(
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
        } else if (!lblCurrentName_editUI.getText().trim().equals(
                game.getGameName())) {
            currentGame_editUI = game;
            lblCurrentName_editUI.setText("  " + currentGame_editUI.getName());
            imgCurrentGame_editUI
                    .setImage(currentGame_editUI.getCoverImagePane()
                            .getImgIcon(),
                            imgCurrentGame_editUI.getImageWidth(),
                            imgCurrentGame_editUI
                            .getImageHeight());

        }

        // reset UI
        txtNewLocation_editUI.setText("");
        txtGameCoverSearch_editUI.getTextBox().setText("");
        imgGameLocationStatus.setImgURl("addUI_badge_idle.png");
        imgGameCoverStatus.setImgURl("addUI_badge_idle.png");
        libraryLogic.getGameSearch_editUI().resetCover();

        // First to be selected when edit UI summoned
        if (!btnGameLocation_editUI.isSelected
                    && !btnGameCover_editUI.isSelected
                    && !btnOther_editUI.isSelected) {
            btnGameCover_editUI.setSelected();
        }

    }
}
