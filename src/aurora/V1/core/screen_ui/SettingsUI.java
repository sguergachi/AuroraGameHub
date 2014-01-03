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

import aurora.V1.core.AuroraApp;
import aurora.V1.core.AuroraCoreUI;
import aurora.V1.core.AuroraStorage;
import aurora.V1.core.screen_handler.SettingsHandler;
import aurora.V1.core.screen_logic.SettingsLogic;
import static aurora.V1.core.screen_ui.LibraryUI.gameNameFontSize;
import static aurora.V1.core.screen_ui.LibraryUI.selectedGameBarHeight;
import static aurora.V1.core.screen_ui.LibraryUI.selectedGameBarWidth;
import aurora.engine.V1.UI.AButton;
import aurora.engine.V1.UI.AFadeLabel;
import aurora.engine.V1.UI.AImage;
import aurora.engine.V1.UI.AImagePane;
import aurora.engine.V1.UI.ARadioButton;
import aurora.engine.V1.UI.AScrollBar;
import aurora.engine.V1.UI.ASlickLabel;
import aurora.engine.V1.UI.ASlickTextPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import org.apache.log4j.Logger;

/**
 * Settings GUI
 * <p/>
 * @author Sammy
 */
public class SettingsUI extends AuroraApp {

    private final DashboardUI dashboardUI;

    private final AuroraCoreUI coreUI;

    private AuroraStorage storage;

    static final Logger logger = Logger.getLogger(SettingsUI.class);

    private JPanel pnlSettingsContent;

    private JPanel pnlSettingsBG;

    private JPanel pnlSettingsTop;

    private JScrollPane pnlSettingsTopScroll;

    private JSeparator settingsTitleSeperator;

    private JPanel pnlSettingsCenter;

    private JScrollPane pnlSettingsCenterScroll;

    private ASlickLabel lblGeneralSettings;

    private int settings_height;

    private int settings_width;

    private JSeparator generalSettingsSeperator;

    private JPanel pnlGeneralSettingsTitlePane;

    private AScrollBar settingsCenterScrollUI;

    private JScrollBar settingsCenterScrollBar;

    private int padding_top;

    private int title_size;

    private JPanel pnlGeneralSettingsLowerTitlePane;

    private JPanel pnlGeneralSettingsGrid;

    private JPanel pnlSoundEffectsSetting;

    private ASlickTextPane lblSoundEffectsSetting;

    private AImage imgSoundEffectIcon;

    private ARadioButton rdbSoundEffects;

    private JPanel pnlSoundEffectSettingLabel;

    private JPanel pnlGeneralSettingsContainer;

    private JPanel pnlWASDNavigationSetting;

    private AImage imgWASDNavigationIcon;

    private ARadioButton rdbWASDNavigation;

    private ASlickTextPane lblWASDNavigationSetting;

    private JPanel pnlWASDNavigationLabel;

    private JPanel pnlBackgroundGameSearchSetting;

    private AImage imgBackgroundGameSearchIcon;

    private JPanel pnlBackgroundGameSearchLabel;

    private ASlickTextPane lblBackgroundGameSearchSetting;

    private AButton btnUpdateAuroraDBSearch;

    private ARadioButton rdbBackgroundGameSearch;

    private ASlickTextPane lblUpdateAuroraDBSearchSetting;

    private JPanel pnlUpdateAuroraDBSearchLabel;

    private JPanel pnlUpdateAuroraDBSearchSetting;

    private AImage imgUpdateAuroraDBSearchIcon;

    private int bottomTopPadding;

    private AImagePane pnlSettingsStatusPane;

    public static AFadeLabel lblSettingsStatus;

    private JPanel pnlBottomCenterContainer;

    public static final String DEAFULT_SETTINGS_STATUS = "Edit a Setting";

    public static final Color DEFAULT_SETTINGS_COLOR = Color.lightGray;

    private boolean isScreenLoaded;

    private SettingsLogic settingsLogic;

    private SettingsHandler settingsHandler;

    private SettingsHandler.RefreshAuroraDBHandler refreshAuroraDBHandler;

    private SettingsHandler.EnableBackgroundGameSearchHandler enableBackgroundGameSearchHandler;

    private SettingsHandler.DisableBackgroundGameSearchHandler disableBackgroundGameSearchHandler;

    private SettingsHandler.EnableWASDNavigationHandler enableWASDNavigationHandler;

    private SettingsHandler.DisableWASDNavigationHandler disableWASDNavigationHandler;

    private SettingsHandler.EnableSoundEffectsHandler enableSoundEffectsHandler;

    private SettingsHandler.DisableSoundEffectsHandler disableSoundEffectsHandler;

    public SettingsUI(AuroraStorage auroraStorage, DashboardUI dashboardUI,
                      AuroraCoreUI auroraCoreUI) {

        this.appName = "Settings";
        this.storage = auroraStorage;
        this.dashboardUI = dashboardUI;
        this.coreUI = auroraCoreUI;

        this.settingsLogic = new SettingsLogic(this);
        this.settingsHandler = new SettingsHandler(this);

        settingsHandler.setLogic(settingsLogic);
        settingsLogic.setHandler(settingsHandler);
    }

    @Override
    public void loadUI() {


        // Settings Main Panels
        // --------------------------------------------------------------------.

        // Background Panel
        pnlSettingsBG = new JPanel(new BorderLayout(0, 0));
        pnlSettingsBG.setBackground(new Color(35, 40, 48));

        pnlSettingsBG.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0,
                Color.BLACK));
        pnlSettingsBG.setPreferredSize(new Dimension(coreUI.getFrame()
                .getWidth(), coreUI.getCenterPanelHeight() / 2));

        setSize();


        // Content Panel
        pnlSettingsContent = new JPanel();
        pnlSettingsContent.setLayout(new BoxLayout(pnlSettingsContent,
                BoxLayout.Y_AXIS));
        pnlSettingsContent.setOpaque(false);
        pnlSettingsContent.setPreferredSize(coreUI.getCenterPanel()
                .getPreferredSize());

        // Settings Top/Title, Seperator and Center panels
        // --------------------------------------------------------------------.

        // Top Panel
        pnlSettingsTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnlSettingsTop.setOpaque(false);
        pnlSettingsTop.setBackground(Color.red);

        pnlSettingsTopScroll = new JScrollPane(pnlSettingsTop,
                JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        pnlSettingsTopScroll.setBorder(null);
        pnlSettingsTopScroll.setOpaque(false);
        pnlSettingsTopScroll.getViewport().setOpaque(false);

        lblGeneralSettings = new ASlickLabel("General Settings");

        pnlGeneralSettingsTitlePane = new JPanel(new BorderLayout(0, 0));
        pnlGeneralSettingsTitlePane.setOpaque(false);
        generalSettingsSeperator = new JSeparator(SwingConstants.VERTICAL);



        // Seperator Panel
        settingsTitleSeperator = new JSeparator(SwingConstants.HORIZONTAL);


        // Center Panel
        pnlSettingsCenter = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnlSettingsCenter.setOpaque(false);

        pnlSettingsCenterScroll = new JScrollPane(pnlSettingsCenter,
                JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pnlSettingsCenterScroll.setOpaque(false);
        pnlSettingsCenterScroll.getViewport().setOpaque(false);
        pnlSettingsCenterScroll.setBorder(null);

        settingsCenterScrollUI = new AScrollBar("settings_scroll_thumb.png",
                "settings_scroll_track.png");
        settingsCenterScrollBar = new JScrollBar();



        // General Settings

        pnlGeneralSettingsContainer = new JPanel(new BorderLayout(0, 0));
        pnlGeneralSettingsContainer.setOpaque(false);

        pnlGeneralSettingsGrid = new JPanel();
        pnlGeneralSettingsGrid.setOpaque(false);


        loadGeneralSettingsUI();


        // Bottom Pane
        // --------------------------------------------------------------------.

        pnlBottomCenterContainer = new JPanel(new FlowLayout(FlowLayout.CENTER,
                0, bottomTopPadding));
        pnlBottomCenterContainer.setOpaque(false);

        // Settings Status


        pnlSettingsStatusPane = new AImagePane("library_selectedGameBar_bg.png",
                selectedGameBarWidth,
                selectedGameBarHeight);
        pnlSettingsStatusPane.setLayout(new BorderLayout(0, 10));

        lblSettingsStatus = new AFadeLabel(DEAFULT_SETTINGS_STATUS);
        lblSettingsStatus.setForeground(DEFAULT_SETTINGS_COLOR);
        lblSettingsStatus.setFont(coreUI
                .getDefaultFont().deriveFont(Font.PLAIN,
                        gameNameFontSize));


    }

    private void loadGeneralSettingsUI() {

        // Sound Effects
        pnlSoundEffectsSetting = new JPanel(new FlowLayout(FlowLayout.LEFT,
                15, 5));
        pnlSoundEffectsSetting.setOpaque(false);


        imgSoundEffectIcon = new AImage("settings_img_sound.png");


        rdbSoundEffects = new ARadioButton("settings_btn_notselected.png",
                "settings_btn_selected.png");


        pnlSoundEffectSettingLabel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 0, 0));
        pnlSoundEffectSettingLabel.setOpaque(false);
        pnlSoundEffectSettingLabel.setPreferredSize(new Dimension(190,
                40));

        lblSoundEffectsSetting = new ASlickTextPane("Sound Effects");
        lblSoundEffectsSetting.setPreferredSize(pnlSoundEffectSettingLabel
                .getPreferredSize());
        lblSoundEffectsSetting.setForeground(new Color(218, 218, 234));
        lblSoundEffectsSetting.setFont(coreUI.getRopaFont().deriveFont(
                Font.PLAIN, 30));


        pnlSoundEffectSettingLabel.add(lblSoundEffectsSetting);



        // WASD Navigation
        pnlWASDNavigationSetting = new JPanel(new FlowLayout(FlowLayout.LEFT,
                15, 5));
        pnlWASDNavigationSetting.setOpaque(false);


        imgWASDNavigationIcon = new AImage("settings_img_keyboard.png");


        rdbWASDNavigation = new ARadioButton("settings_btn_notselected.png",
                "settings_btn_selected.png");


        pnlWASDNavigationLabel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 0, 0));
        pnlWASDNavigationLabel.setOpaque(false);
        pnlWASDNavigationLabel.setPreferredSize(new Dimension(190,
                78));

        lblWASDNavigationSetting = new ASlickTextPane("WASD Navigation");
        lblWASDNavigationSetting.setPreferredSize(pnlWASDNavigationLabel
                .getPreferredSize());
        lblWASDNavigationSetting.setForeground(new Color(218, 218, 234));
        lblWASDNavigationSetting.setFont(coreUI.getRopaFont().deriveFont(
                Font.PLAIN, 30));

        pnlWASDNavigationLabel.add(lblWASDNavigationSetting);



        // Background Game Search
        pnlBackgroundGameSearchSetting = new JPanel(new FlowLayout(
                FlowLayout.LEFT,
                15, 5));
        pnlBackgroundGameSearchSetting.setOpaque(false);


        imgBackgroundGameSearchIcon = new AImage("settings_img_update.png");

        rdbBackgroundGameSearch = new ARadioButton(
                "settings_btn_notselected.png",
                "settings_btn_selected.png");

        pnlBackgroundGameSearchLabel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 0, 0));
        pnlBackgroundGameSearchLabel.setOpaque(false);
        pnlBackgroundGameSearchLabel.setPreferredSize(new Dimension(190,
                78));

        lblBackgroundGameSearchSetting = new ASlickTextPane(
                "Background Game Search");
        lblBackgroundGameSearchSetting.setPreferredSize(
                pnlBackgroundGameSearchLabel
                .getPreferredSize());
        lblBackgroundGameSearchSetting.setForeground(new Color(218, 218, 234));
        lblBackgroundGameSearchSetting.setFont(coreUI.getRopaFont().deriveFont(
                Font.PLAIN, 30));

        pnlBackgroundGameSearchLabel.add(lblBackgroundGameSearchSetting);


        // Download AuroraDB
        pnlUpdateAuroraDBSearchSetting = new JPanel(new FlowLayout(
                FlowLayout.LEFT,
                15, 5));
        pnlUpdateAuroraDBSearchSetting.setOpaque(false);

        imgUpdateAuroraDBSearchIcon = new AImage("settings_img_update.png");

        btnUpdateAuroraDBSearch = new AButton("settings_btn_select_norm.png",
                "settings_btn_select_down.png", "settings_btn_select_over.png");
        btnUpdateAuroraDBSearch.setMargin(new Insets(0, 0,
                0, 0));

        pnlUpdateAuroraDBSearchLabel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 0, 0));
        pnlUpdateAuroraDBSearchLabel.setOpaque(false);
        pnlUpdateAuroraDBSearchLabel.setPreferredSize(new Dimension(190,
                78));

        lblUpdateAuroraDBSearchSetting = new ASlickTextPane(
                "Update Aurora Cover Art DB");
        lblUpdateAuroraDBSearchSetting.setPreferredSize(
                pnlUpdateAuroraDBSearchLabel
                .getPreferredSize());
        lblUpdateAuroraDBSearchSetting.setForeground(new Color(218, 218, 234));
        lblUpdateAuroraDBSearchSetting.setFont(coreUI.getRopaFont().deriveFont(
                Font.PLAIN, 30));

        pnlUpdateAuroraDBSearchLabel.add(lblUpdateAuroraDBSearchSetting);



    }

    private void buildGeneralSettings() {

        // Sound Effects
        pnlSoundEffectsSetting.add(imgSoundEffectIcon);
        pnlSoundEffectsSetting.add(pnlSoundEffectSettingLabel);
        pnlSoundEffectsSetting.add(rdbSoundEffects);
        pnlSoundEffectsSetting.revalidate();
        pnlSoundEffectsSetting.repaint();

        // WASD Nav
        pnlWASDNavigationSetting.add(imgWASDNavigationIcon);
        pnlWASDNavigationSetting.add(pnlWASDNavigationLabel);
        pnlWASDNavigationSetting.add(rdbWASDNavigation);
        pnlWASDNavigationSetting.revalidate();

        // Background Game Search
        pnlBackgroundGameSearchSetting.add(imgBackgroundGameSearchIcon);
        pnlBackgroundGameSearchSetting.add(pnlBackgroundGameSearchLabel);
        pnlBackgroundGameSearchSetting.add(rdbBackgroundGameSearch);
        pnlBackgroundGameSearchSetting.revalidate();

        // Download AuroraDB
        pnlUpdateAuroraDBSearchSetting.add(imgUpdateAuroraDBSearchIcon);
        pnlUpdateAuroraDBSearchSetting.add(pnlUpdateAuroraDBSearchLabel);
        pnlUpdateAuroraDBSearchSetting.add(btnUpdateAuroraDBSearch);
        pnlUpdateAuroraDBSearchSetting.revalidate();

        pnlGeneralSettingsGrid.add(pnlSoundEffectsSetting);
        pnlGeneralSettingsGrid.add(pnlWASDNavigationSetting);
        pnlGeneralSettingsGrid.add(pnlBackgroundGameSearchSetting);
        pnlGeneralSettingsGrid.add(pnlUpdateAuroraDBSearchSetting);

        pnlGeneralSettingsGrid.setLayout(new GridLayout(2, 2, padding_top,
                padding_top));
        pnlGeneralSettingsGrid.revalidate();

        pnlGeneralSettingsContainer
                .add(Box.createVerticalStrut(padding_top),
                        BorderLayout.NORTH);
        pnlGeneralSettingsContainer.add(pnlGeneralSettingsGrid,
                BorderLayout.SOUTH);

    }

    @Override
    public void buildUI() {

        if (!isScreenLoaded) {

            // Content Setup
            // --------------------------------------------------------------------.

            // Top Panel

            lblGeneralSettings.setForeground(new Color(34, 140, 0));
            lblGeneralSettings.setFont(coreUI.getRopaFont().deriveFont(
                    Font.PLAIN,
                    title_size));

            generalSettingsSeperator.setPreferredSize(
                    new Dimension(2, 40));
            generalSettingsSeperator.setMaximumSize(generalSettingsSeperator
                    .getPreferredSize());
            generalSettingsSeperator.setForeground(new Color(13, 17, 21));
            generalSettingsSeperator.setBackground(new Color(13, 17, 21));

            pnlGeneralSettingsTitlePane.add(Box.createGlue(),
                    BorderLayout.CENTER);
            pnlGeneralSettingsLowerTitlePane = new JPanel(new FlowLayout(
                    FlowLayout.LEFT, 0, 0));
            pnlGeneralSettingsLowerTitlePane.setOpaque(false);
            pnlGeneralSettingsLowerTitlePane.setBackground(Color.GREEN);

            pnlGeneralSettingsLowerTitlePane.add(generalSettingsSeperator);


            pnlGeneralSettingsTitlePane.add(pnlGeneralSettingsLowerTitlePane,
                    BorderLayout.SOUTH);
            pnlGeneralSettingsTitlePane.setPreferredSize(new Dimension(
                    generalSettingsSeperator.getPreferredSize().width,
                    title_size + 10));

            pnlSettingsTop.add(Box.createHorizontalStrut(35));
            pnlSettingsTop.add(pnlGeneralSettingsTitlePane);
            pnlSettingsTop.add(Box.createHorizontalStrut(10));
            pnlSettingsTop.add(lblGeneralSettings);



            // Seperator
            settingsTitleSeperator.setPreferredSize(
                    new Dimension(settings_width * 2,
                            2));
            settingsTitleSeperator.setMaximumSize(settingsTitleSeperator
                    .getPreferredSize());
            settingsTitleSeperator.setForeground(new Color(13, 17, 21));
            settingsTitleSeperator.setBackground(new Color(13, 17, 21));



            // Center Panel
            pnlSettingsCenter.setPreferredSize(new Dimension(settings_width,
                    settings_height * 2 + (title_size + 10)));
            pnlSettingsCenter.add(Box.createHorizontalStrut(35));

            pnlSettingsCenterScroll.setPreferredSize(pnlSettingsCenter
                    .getPreferredSize());

            settingsCenterScrollBar.setUI(settingsCenterScrollUI);
            settingsCenterScrollBar.setPreferredSize(new Dimension(
                    settings_width,
                    12));
            settingsCenterScrollBar.setOpaque(false);
            pnlSettingsCenterScroll.setHorizontalScrollBar(
                    settingsCenterScrollBar);



            // General Settings

            buildGeneralSettings();

            pnlSettingsCenter.add(pnlGeneralSettingsContainer);


            // Add to content panel
            // --------------------------------------------------------------------.

            pnlSettingsContent.add(Box.createVerticalStrut(padding_top));
            pnlSettingsContent.add(pnlSettingsTopScroll);
            pnlSettingsContent.add(settingsTitleSeperator);
            pnlSettingsContent.add(pnlSettingsCenterScroll);


            pnlSettingsBG.add(pnlSettingsContent);


            // Bottom Panel
            pnlSettingsStatusPane
                    .setPreferredSize(new Dimension(
                                    selectedGameBarWidth, selectedGameBarHeight));

            pnlSettingsStatusPane.add(lblSettingsStatus);

            lblSettingsStatus.setSize(new Dimension(lblSettingsStatus
                    .getPreferredSize().width, lblSettingsStatus
                    .getPreferredSize().height));
            lblSettingsStatus.validate();

            pnlSettingsStatusPane.validate();

            pnlBottomCenterContainer.add(pnlSettingsStatusPane);



            isScreenLoaded = true;
            addToCanvas();

        } else {

            addToCanvas();

        }
    }

    @Override
    public void addToCanvas() {

        coreUI.getTitleLabel().setText("    Settings   ");

        // Handle the Handlers ;)
        // ----------------------------------------------------------------.
        addToVolatileListenerBank(coreUI.getBackgroundImagePane());
        addToVolatileListenerBank(coreUI.getBottomPane());
        addToVolatileListenerBank(coreUI.getCenterPanel());
        addToVolatileListenerBank(coreUI.getSouthFromTopPanel());
        addToVolatileListenerBank(coreUI.getFrameControlImagePane());
        addToVolatileListenerBank(coreUI.getTopPane());

        // Set defaults or pre-set values to settings
        checkSettingsValues();

        attactchHandlers();

        // Add Settings BG to Center Panel
        // --------------------------------------------------------------------.
        coreUI.getCenterPanel().add(BorderLayout.NORTH, Box
                .createVerticalStrut(
                        20));
        coreUI.getCenterPanel().add(BorderLayout.CENTER, pnlSettingsBG);
        coreUI.getCenterPanel().add(BorderLayout.SOUTH, Box
                .createVerticalStrut(
                        20));
        coreUI.getCenterPanel().repaint();


        // Add Settings Status and Info Feed to Bottom Pane
        // --------------------------------------------------------------------.

        // Status
        coreUI.getCenterFromBottomPanel().setLayout(new BorderLayout());

        coreUI.getCenterFromBottomPanel().add(BorderLayout.NORTH,
                pnlBottomCenterContainer);


        // Info Feed
        getDashboardUI().getInfoFeed().setImageSize(getCoreUI()
                .getScreenWidth() - 20, getDashboardUI().getInfoFeed()
                .getImageHeight() - 5);
        getDashboardUI().getInfoFeed()
                .setPreferredSize(new Dimension(getDashboardUI().getInfoFeed()
                                .getPreferredSize().width,
                                getDashboardUI().getInfoFeed()
                                .getImageHeight()));

        coreUI.getBottomContentPane().setLayout(new BorderLayout());
        coreUI.getBottomContentPane().setVisible(true);

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

        coreUI.getCenterFromBottomPanel().add(BorderLayout.CENTER, coreUI
                .getBottomContentPane());


    }

    private void checkSettingsValues() {

        // Sound effects
        if (storage.getStoredSettings().getSettingValue("sound_effects") != null) {

            if (storage.getStoredSettings().getSettingValue("sound_effects")
                    .equals("enabled")) {

                rdbSoundEffects.setSelected();


            } else if (storage.getStoredSettings().getSettingValue(
                    "sound_effects").equals("disabled")) {

                rdbSoundEffects.setUnSelected();

            }

        } else {

            storage.getStoredSettings().saveSetting("sound_effects",
                    SettingsLogic.DEFAULT_SFX_SETTING);
            checkSettingsValues();

        }


        // WASD Navigation
        if (storage.getStoredSettings()
                .getSettingValue("wasd_navigation") != null) {

            if (storage.getStoredSettings().getSettingValue(
                    "wasd_navigation")
                    .equals("enabled")) {

                rdbWASDNavigation.setSelected();


            } else if (storage.getStoredSettings().getSettingValue(
                    "wasd_navigation").equals("disabled")) {

                rdbWASDNavigation.setUnSelected();

            }

        } else {

            storage.getStoredSettings().saveSetting("wasd_navigation",
                    SettingsLogic.DEFAULT_WASD_NAV_SETTING);
            checkSettingsValues();

        }


        // Background search
        if (storage.getStoredSettings()
                .getSettingValue("background_game_search") != null) {

            if (storage.getStoredSettings().getSettingValue(
                    "background_game_search")
                    .equals("enabled")) {

                rdbBackgroundGameSearch.setSelected();


            } else if (storage.getStoredSettings().getSettingValue(
                    "background_game_search").equals("disabled")) {

                rdbBackgroundGameSearch.setUnSelected();

            }

        } else {

            storage.getStoredSettings().saveSetting("background_game_search",
                    SettingsLogic.DEFAULT_BACKGROUND_SEARCH_SETTING);
            checkSettingsValues();

        }


    }

    private void attactchHandlers() {

        // Create Handlers
        refreshAuroraDBHandler = settingsHandler.new RefreshAuroraDBHandler();

        enableBackgroundGameSearchHandler = settingsHandler.new EnableBackgroundGameSearchHandler();
        disableBackgroundGameSearchHandler = settingsHandler.new DisableBackgroundGameSearchHandler();

        enableWASDNavigationHandler = settingsHandler.new EnableWASDNavigationHandler();
        disableWASDNavigationHandler = settingsHandler.new DisableWASDNavigationHandler();

        enableSoundEffectsHandler = settingsHandler.new EnableSoundEffectsHandler();
        disableSoundEffectsHandler = settingsHandler.new DisableSoundEffectsHandler();


        // Attach Handlers
        btnUpdateAuroraDBSearch.addActionListener(refreshAuroraDBHandler);

        rdbBackgroundGameSearch.setSelectedHandler(
                enableBackgroundGameSearchHandler);
        rdbBackgroundGameSearch.setUnSelectedHandler(
                disableBackgroundGameSearchHandler);

        rdbWASDNavigation.setSelectedHandler(enableWASDNavigationHandler);
        rdbWASDNavigation.setUnSelectedHandler(disableWASDNavigationHandler);

        rdbSoundEffects.setSelectedHandler(enableSoundEffectsHandler);
        rdbSoundEffects.setUnSelectedHandler(disableSoundEffectsHandler);


    }

    public void setSize() {


        settings_height = pnlSettingsBG.getPreferredSize().height - (2 * 20);
        settings_width = coreUI.getFrame().getWidth();

        if (coreUI.isLargeScreen()) {

            padding_top = 60;
            title_size = 60;
            bottomTopPadding = 10;

        } else {
            padding_top = 40;
            title_size = 60;
            bottomTopPadding = 5;
        }

    }

    @Override
    public void closeApp() {
    }

    @Override
    public AuroraCoreUI getCoreUI() {
        return coreUI;
    }

    public DashboardUI getDashboardUI() {
        return dashboardUI;
    }

    public ARadioButton getRdbSoundEffects() {
        return rdbSoundEffects;
    }

    public ARadioButton getRdbWASDNavigation() {
        return rdbWASDNavigation;
    }

    public AButton getBtnUpdateAuroraDBSearch() {
        return btnUpdateAuroraDBSearch;
    }

    public ARadioButton getRdbBackgroundGameSearch() {
        return rdbBackgroundGameSearch;
    }

}
