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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JPanel;
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

    public SettingsUI(DashboardUI dashboardUI, AuroraCoreUI auroraCoreUI) {

        this.appName = "Settings";
        this.dashboardUI = dashboardUI;
        this.coreUI = auroraCoreUI;
    }

    @Override
    public void loadUI() {

        setSize();

        pnlSettingsBG = new JPanel(new BorderLayout());
        pnlSettingsBG.setBackground(new Color(34, 41, 54, (int) (255 * .8)));

        pnlSettingsBG.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0,
                                                                Color.BLACK));

        pnlSettingsBG.setPreferredSize(new Dimension(coreUI.getFrame()
                .getWidth(), coreUI.getCenterPanelHeight() / 2));

        pnlSettingsContent = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 10, 25));
        pnlSettingsContent.setOpaque(false);
        pnlSettingsContent.setPreferredSize(coreUI.getCenterPanel()
                .getPreferredSize());


    }

    @Override
    public void buildUI() {
        coreUI.getTitleLabel().setText("     Settings   ");

        pnlSettingsBG.add(pnlSettingsContent);

        //* Add Library Container to Center Panel *//
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

    }

    @Override
    public AuroraCoreUI getCoreUI() {
        return coreUI;
    }

    @Override
    public void closeApp() {
    }
}
