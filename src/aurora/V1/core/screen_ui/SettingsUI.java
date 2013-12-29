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
import aurora.engine.V1.UI.AImagePane;
import aurora.engine.V1.UI.ASlickLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
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
        pnlSettingsBG = new JPanel(new BorderLayout());
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

        pnlSettingsTopScroll = new JScrollPane(pnlSettingsTop,
                JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        pnlSettingsTopScroll.setBorder(null);
        pnlSettingsTopScroll.setOpaque(false);
        pnlSettingsTopScroll.getViewport().setOpaque(false);

        lblGeneralSettings = new ASlickLabel("General Settings");



        // Seperator Panel
        settingsTitleSeperator = new JSeparator(SwingConstants.HORIZONTAL);
        settingsTitleSeperator.setForeground(Color.BLACK);
        settingsTitleSeperator.setBackground(Color.BLACK);


        // Center Panel
        pnlSettingsCenter = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnlSettingsCenter.setOpaque(false);

        pnlSettingsCenterScroll = new JScrollPane(pnlSettingsCenter,
                JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pnlSettingsCenterScroll.setOpaque(false);
        pnlSettingsCenterScroll.getViewport().setOpaque(false);
        pnlSettingsCenterScroll.setBorder(null);





    }

    @Override
    public void buildUI() {

        setSize();

        coreUI.getTitleLabel().setText("     Settings   ");



        // Content Setup
        // --------------------------------------------------------------------.

        // Top Panel
        lblGeneralSettings.setForeground(new Color(34, 140, 0));
        lblGeneralSettings.setFont(coreUI.getRopaFont().deriveFont(Font.PLAIN,
                60));

        pnlSettingsTop.add(Box.createHorizontalStrut(35));
        pnlSettingsTop.add(lblGeneralSettings);

        pnlSettingsTop.setPreferredSize(new Dimension(settings_width,
                settings_height / 60));
        pnlSettingsTopScroll.setPreferredSize(pnlSettingsTop.getPreferredSize());

        // Seperator
        settingsTitleSeperator.setPreferredSize(new Dimension(settings_width*2,
                1));
        settingsTitleSeperator.setMaximumSize(settingsTitleSeperator.getPreferredSize());


        // Center Panel
        pnlSettingsCenter.setPreferredSize(new Dimension(settings_width,
                settings_height * 2));
        pnlSettingsCenter.add(Box.createHorizontalStrut(35));

        pnlSettingsCenterScroll.setPreferredSize(pnlSettingsCenter
                .getPreferredSize());


        // Add to content panel
        // --------------------------------------------------------------------.

        pnlSettingsContent.add(Box.createVerticalStrut(60));
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




        // Sorry Dialog incase Settings is not ready
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
