/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aurora.V1.core.screen_ui;

import aurora.V1.core.AuroraCoreUI;
import aurora.V1.core.AuroraStorage;
import aurora.V1.core.screen_handler.LibraryHandler;
import aurora.V1.core.screen_logic.SettingsLogic;
import aurora.engine.V1.Logic.ASound;
import aurora.engine.V1.UI.AImage;
import aurora.engine.V1.UI.APopupMenu;
import aurora.engine.V1.UI.ARadioButton;
import aurora.engine.V1.UI.ARadioButtonManager;
import aurora.engine.V1.UI.ASlickLabel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JPanel;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class OrganizeUI {

    private final AuroraCoreUI coreUI;

    private final LibraryUI libraryUI;

    private final LibraryHandler libraryHandler;

    private final AuroraStorage auroraStorage;

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

    public OrganizeUI(AuroraCoreUI coreUI, LibraryUI libraryUI) {
        this.coreUI = coreUI;
        this.libraryUI = libraryUI;
        this.libraryHandler = libraryUI.getHandler();
        this.auroraStorage = libraryUI.getDashboardUI().getStorage();
    }

    public void loadOrganizeUI() {

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
        lblFavorite.setFont(coreUI.getRopaFont().deriveFont(
                Font.PLAIN, 19));
        lblFavorite.setForeground(new Color(173, 173, 173));

        lblAlphabetic = new ASlickLabel("Alphabetic");
        lblAlphabetic.setFont(coreUI.getRopaFont()
                .deriveFont(
                        Font.PLAIN, 19));
        lblAlphabetic.setForeground(new Color(173, 173, 173));

        lblMostPlayed = new ASlickLabel("Most Played");
        lblMostPlayed.setFont(coreUI.getRopaFont()
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
                                                    btnBottom
                                                    .getRealImageHeight()));
        favoritePane.setOpaque(false);

        alphabeticPane = new JPanel(new FlowLayout(FlowLayout.LEFT,
                                                   10, 2));
        alphabeticPane.setPreferredSize(new Dimension(btnBottom
                .getRealImageWidth(),
                                                      btnBottom
                                                      .getRealImageHeight()));
        alphabeticPane.setOpaque(false);

        mostplayedPane = new JPanel(new FlowLayout(FlowLayout.LEFT,
                                                   10, 2));
        mostplayedPane.setPreferredSize(new Dimension(btnBottom
                .getRealImageWidth(),
                                                      btnBottom
                                                      .getRealImageHeight()));
        mostplayedPane.setOpaque(false);

        // Handlers //
        btnTop.addMouseListener(libraryHandler.new OrganizeMouseListener(
                lblFavorite));
        btnTop.setSelectedHandler(libraryHandler.new SelectedOrganizeListener(
                lblFavorite,
                auroraStorage.getStoredSettings(),
                "Favorite"));
        btnTop.setUnSelectedHandler(
                libraryHandler.new UnSelectedOrganizeListener(
                        lblFavorite, organizeMenu));

        btnMiddle.addMouseListener(
                libraryHandler.new OrganizeMouseListener(lblAlphabetic));
        btnMiddle.setSelectedHandler(
                libraryHandler.new SelectedOrganizeListener(lblAlphabetic,
                                                            auroraStorage
                                                            .getStoredSettings(),
                                                            "Alphabetic"));
        btnMiddle.setUnSelectedHandler(
                libraryHandler.new UnSelectedOrganizeListener(
                        lblAlphabetic, organizeMenu));

        btnBottom.addMouseListener(libraryHandler.new OrganizeMouseListener(
                lblMostPlayed));
        btnBottom.setSelectedHandler(
                libraryHandler.new SelectedOrganizeListener(lblMostPlayed,
                                                            auroraStorage
                                                            .getStoredSettings(),
                                                            "Most Played"));
        btnBottom.setUnSelectedHandler(
                libraryHandler.new UnSelectedOrganizeListener(
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

        String soundEffectsSetting = auroraStorage.getStoredSettings()
                .getSettingValue("sound_effects");
        if (soundEffectsSetting == null) {
            soundEffectsSetting = SettingsLogic.DEFAULT_SFX_SETTING;
        }

        if (soundEffectsSetting.equals("enabled")) {
            ASound organizeSFX = new ASound("tick_2.wav", false);
            organizeSFX.Play();
        }

        // States //
        if (!btnTop.isSelected && !btnMiddle.isSelected && !btnBottom.isSelected) {
            String value = auroraStorage.getStoredSettings().getSettingValue(
                    "organize");
            if (value != null) {
                if (value.equalsIgnoreCase("Favorite")) {
                    btnTop.setSelected();

                } else if (value.equalsIgnoreCase("Alphabetic")) {
                    btnMiddle.setSelected();

                } else if (value.equalsIgnoreCase("Most Played")) {
                    btnBottom.setSelected();
                }
            } else {

                auroraStorage.getStoredSettings().saveSetting("organize",
                                                              "favorite");
                value = "Favorite";
                btnTop.setSelected();
            }
        }

        organizeMenu
                .show(coreUI.getFrame(), libraryUI.getBtnOrganizeGames()
                      .getLocationOnScreen().x + ((libraryUI
                      .getBtnOrganizeGames()
                      .getBounds().width) / 3 - (libraryUI.getBtnOrganizeGames()
                      .getBounds().width) / 5) - 3,
                      libraryUI.getBtnOrganizeGames().getLocationOnScreen().y
                      - libraryUI.getBtnOrganizeGames()
                      .getBounds().height - btnMiddle
                      .getRealImageHeight() - 5);

    }
}
