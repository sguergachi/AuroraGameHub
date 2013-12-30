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
import aurora.engine.V1.UI.AImage;
import aurora.engine.V1.UI.AImagePane;
import aurora.engine.V1.UI.ARadioButton;
import aurora.engine.V1.UI.AScrollBar;
import aurora.engine.V1.UI.ASlickLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
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

    static final Logger logger = Logger.getLogger(SettingsUI.class);

    private AImagePane sorry;

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

//    private ASlickTextPane lblSoundEffectsSetting;
    private ASlickLabel lblSoundEffectsSetting;

    private AImage imgSoundEffectIcon;

    private ARadioButton rdbSoundEffects;

    private JPanel pnlSoundEffectSettingLabel;

    private JPanel pnlGeneralSettingsContainer;

    public SettingsUI(DashboardUI dashboardUI, AuroraCoreUI auroraCoreUI) {

        this.appName = "Settings";
        this.dashboardUI = dashboardUI;
        this.coreUI = auroraCoreUI;
    }

    @Override
    public void loadUI() {


        // Settings Main Panels
        // --------------------------------------------------------------------.

        // Background Panel
        pnlSettingsBG = new JPanel(new BorderLayout(0, 0));
        pnlSettingsBG.setBackground(new Color(34, 41, 54, (int) (255 * .8)));

        pnlSettingsBG.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0,
                Color.BLACK));
        pnlSettingsBG.setPreferredSize(new Dimension(coreUI.getFrame()
                .getWidth(), coreUI.getCenterPanelHeight() / 2));


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
        pnlGeneralSettingsGrid.setLayout(new GridLayout(2, 2, padding_top / 3,
                padding_top / 5));



        loadGeneralSettingsUI();



    }

    private void loadGeneralSettingsUI() {


        // Sound Effects
        pnlSoundEffectsSetting = new JPanel(new FlowLayout(FlowLayout.LEFT,
                8, 5));
        pnlSoundEffectsSetting.setOpaque(false);


        imgSoundEffectIcon = new AImage("settings_img_sound.png");


        rdbSoundEffects = new ARadioButton("settings_btn_notselected.png",
                "settings_btn_selected.png");


        pnlSoundEffectSettingLabel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 0, 0));
        pnlSoundEffectSettingLabel.setOpaque(false);
        pnlSoundEffectSettingLabel.setPreferredSize(new Dimension(250,
                56));

//        lblSoundEffectsSetting = new ASlickTextPane("Sound Effects");
        lblSoundEffectsSetting = new ASlickLabel("Sound Effects");
        lblSoundEffectsSetting.setPreferredSize(pnlSoundEffectSettingLabel
                .getPreferredSize());
        lblSoundEffectsSetting.setForeground(new Color(218, 218, 234));
        lblSoundEffectsSetting.setFont(coreUI.getRopaFont().deriveFont(
                Font.PLAIN, 30));


//        StyledDocument doc = lblSoundEffectsSetting.getStyledDocument();
//        SimpleAttributeSet center = new SimpleAttributeSet();
//        StyleConstants.setAlignment(center, StyleConstants.ALIGN_LEFT);
//        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        pnlSoundEffectSettingLabel.add(lblSoundEffectsSetting);


        // WASD Navigation


    }

    private void buildGeneralSettings() {

        // Sound Effects
        pnlSoundEffectsSetting.add(imgSoundEffectIcon);
        pnlSoundEffectsSetting.add(pnlSoundEffectSettingLabel);
        pnlSoundEffectsSetting.add(rdbSoundEffects);
        pnlSoundEffectsSetting.revalidate();
        pnlSoundEffectsSetting.repaint();


        pnlGeneralSettingsGrid.add(pnlSoundEffectsSetting);
        pnlGeneralSettingsGrid.revalidate();
        pnlGeneralSettingsGrid.repaint();

    }

    @Override
    public void buildUI() {

        setSize();

        coreUI.getTitleLabel().setText("     Settings   ");



        // Content Setup
        // --------------------------------------------------------------------.

        // Top Panel
//        pnlSettingsTop.setPreferredSize(new Dimension(settings_width,
//                settings_height / 5));
//        pnlSettingsTopScroll.setPreferredSize(pnlSettingsTop.getPreferredSize());

        lblGeneralSettings.setForeground(new Color(34, 140, 0));
        lblGeneralSettings.setFont(coreUI.getRopaFont().deriveFont(Font.PLAIN,
                title_size));

        generalSettingsSeperator.setPreferredSize(
                new Dimension(2, 40));
        generalSettingsSeperator.setMaximumSize(generalSettingsSeperator
                .getPreferredSize());
        generalSettingsSeperator.setForeground(new Color(13, 17, 21));
        generalSettingsSeperator.setBackground(new Color(13, 17, 21));

        pnlGeneralSettingsTitlePane.add(Box.createGlue(), BorderLayout.CENTER);
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
        pnlSettingsTop.add(Box.createHorizontalStrut(5));
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
        settingsCenterScrollBar.setPreferredSize(new Dimension(settings_width,
                12));
        settingsCenterScrollBar.setOpaque(false);
        pnlSettingsCenterScroll.setHorizontalScrollBar(settingsCenterScrollBar);



        // General Settings

        buildGeneralSettings();

        pnlGeneralSettingsContainer
                .add(Box.createVerticalStrut(padding_top),
                        BorderLayout.NORTH);
        pnlGeneralSettingsContainer.add(pnlGeneralSettingsGrid,
                BorderLayout.SOUTH);
        pnlSettingsCenter.add(pnlGeneralSettingsContainer);


        // Add to content panel
        // --------------------------------------------------------------------.

        pnlSettingsContent.add(Box.createVerticalStrut(padding_top));
        pnlSettingsContent.add(pnlSettingsTopScroll);
        pnlSettingsContent.add(settingsTitleSeperator);
        pnlSettingsContent.add(pnlSettingsCenterScroll);


        pnlSettingsBG.add(pnlSettingsContent);


        // Add Settings BG to Center Panel
        // --------------------------------------------------------------------.
        coreUI.getCenterPanel().add(BorderLayout.NORTH, Box.createVerticalStrut(
                20));
        coreUI.getCenterPanel().add(BorderLayout.CENTER, pnlSettingsBG);
        coreUI.getCenterPanel().add(BorderLayout.SOUTH, Box.createVerticalStrut(
                20));
        coreUI.getCenterPanel().repaint();




        // Sorry image in case Settings is not ready
//        sorry = new AImagePane("inDev.png");
//        sorry.setPreferredSize(new Dimension(sorry.getRealImageWidth(), sorry
//                .getRealImageHeight()));
//        pnlSettingsContent.add(sorry);
    }

    @Override
    public void addToCanvas() {
    }

    public DashboardUI getDashboardUI() {
        return dashboardUI;
    }

    public void setSize() {


        settings_height = pnlSettingsBG.getPreferredSize().height - (2 * 20);
        settings_width = coreUI.getFrame().getWidth();

        if (coreUI.isLargeScreen()) {

            padding_top = 60;
            title_size = 60;


        } else {
            padding_top = 40;
            title_size = 60;
        }

    }

    @Override
    public AuroraCoreUI getCoreUI() {
        return coreUI;
    }

    @Override
    public void closeApp() {
    }

}
