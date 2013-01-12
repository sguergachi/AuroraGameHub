/*
 * Made By Sardonix Creative.
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
package aurora.V1.core;

import aurora.V1.core.screen_handler.GameLibraryHandler;
import aurora.engine.V1.Logic.AAnimate;
import aurora.engine.V1.Logic.APostHandler;
import aurora.engine.V1.UI.AImage;
import aurora.engine.V1.UI.AImagePane;
import aurora.engine.V1.UI.AScrollBar;
import aurora.engine.V1.UI.ASlickLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class AboutOverlay {

    private final AuroraCoreUI coreUI;

    private JPanel pnlGlass;

    private AImagePane pnlAboutPane;

    private JPanel pnlTop;

    private JPanel pnlBottom;

    private AImagePane pnlCenter;

    private JScrollPane scrollPane;

    private JScrollBar scrollBar;

    private ASlickLabel lblVersion;

    private final String buildNumber;

    private ASlickLabel lblBuild;

    private JPanel pnlVersion;

    private AAnimate aboutBoxAnimator;

    private boolean isAboutVisible;

    private AImagePane imgLogo;

    private JPanel pnlCenterContainer;

    public AboutOverlay(AuroraCoreUI CoreUI) {

        coreUI = CoreUI;
        this.buildNumber = coreUI.getBuildNumber();

        loadAboutUI();
    }

    public final void loadAboutUI() {


        // Create Components
        // ----------------------------------------------------------------.

        // Get Glass Pane to Put UI On //
        pnlGlass = (JPanel) coreUI.getFrame().getGlassPane();


        pnlAboutPane = new AImagePane("app_about_bg.png",
                new BorderLayout());
        pnlAboutPane.addFocusListener(new AboutBoxFocusListener());

        // Inner Panels //
        pnlTop = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlTop.setOpaque(false);

        pnlCenterContainer = new JPanel();
        pnlCenterContainer.setOpaque(false);

        pnlCenter = new AImagePane("app_about_center.png", pnlAboutPane
                .getRealImageWidth(), 0);

        pnlBottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlBottom.setOpaque(false);

        imgLogo = new AImagePane("app_about_logo.png");
        imgLogo.addMouseListener(new LogoMouseListener());

        // Scroll Bar //
        scrollBar = new JScrollBar();
        scrollBar.setUnitIncrement(14);
        scrollBar.setUI(new AScrollBar("app_scrollBar.png", "app_scrollBG.png"));

        scrollPane = new JScrollPane(pnlCenterContainer,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBar(scrollBar);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);



        // Components //

        pnlVersion = new JPanel();
        pnlVersion.setOpaque(false);
        pnlVersion.setLayout(new BoxLayout(pnlVersion, BoxLayout.Y_AXIS));


        lblVersion = new ASlickLabel(main.VERSION);
        lblBuild = new ASlickLabel("build: " + buildNumber);


    }

    public void buildAboutUI() {



        // Top Panel //
        lblVersion.setFont(coreUI.getRopaFont().deriveFont(Font.PLAIN, 46));
        lblBuild.setFont(coreUI.getRopaFont().deriveFont(Font.PLAIN, 17));

        lblVersion.setForeground(Color.white);
        lblBuild.setForeground(Color.white);

        pnlVersion.add(lblVersion, BorderLayout.CENTER);
        pnlVersion.add(lblBuild, BorderLayout.SOUTH);
        pnlTop.add(pnlVersion);

        // Center Panel //
        scrollPane.setBorder(null);
        scrollPane.setWheelScrollingEnabled(true);


        pnlCenter.setLayout(new BoxLayout(pnlCenter, BoxLayout.Y_AXIS));
        addContent();

        pnlCenterContainer.setLayout(new BoxLayout(pnlCenterContainer,
                BoxLayout.Y_AXIS));

        pnlCenter.add(scrollPane);

        // Bottom Pane//
        pnlBottom.add(imgLogo);
        imgLogo.setPreferredSize(new Dimension(imgLogo.getRealImageWidth(),
                imgLogo.getRealImageHeight()));
        pnlBottom.setPreferredSize(new Dimension(imgLogo.getRealImageWidth(),
                pnlAboutPane.getRealImageHeight()
                - pnlTop.getPreferredSize().height - pnlCenter
                .getRealImageHeight()));

        // Add to Main Panel //
        pnlAboutPane.add(pnlTop, BorderLayout.NORTH);
        pnlAboutPane.add(pnlCenter, BorderLayout.CENTER);
        pnlAboutPane.add(pnlBottom, BorderLayout.SOUTH);

        pnlAboutPane.setLocation((coreUI.getFrame().getWidth() / 2)
                                 - (pnlAboutPane.getImgIcon()
                .getIconWidth()
                                    / 2), -380);
        pnlAboutPane
                .setSize(
                new Dimension(pnlAboutPane.getImgIcon()
                .getIconWidth(), pnlAboutPane.getImgIcon().getIconHeight()));
        pnlAboutPane.revalidate();
        pnlAboutPane.requestFocusInWindow();

    }

    public void showAboutBox() {
        pnlGlass.add(pnlAboutPane);
        pnlGlass.setLayout(null);
        pnlGlass.setOpaque(false);
        pnlGlass.setVisible(true);

        aboutBoxAnimator = new AAnimate(pnlAboutPane);

        aboutBoxAnimator.setInitialLocation((coreUI.getFrame().getWidth() / 2)
                                            - (pnlAboutPane.getImgIcon()
                .getIconWidth() / 2), -390);
        aboutBoxAnimator.moveVertical(0, 33);
        pnlAboutPane.revalidate();

        isAboutVisible = true;
        pnlAboutPane.requestFocusInWindow();

    }

    public void hideAboutBox() {

        if (isAboutVisible) {
            aboutBoxAnimator.moveVertical(-485, 33);
            aboutBoxAnimator.addPostAnimationListener(new APostHandler() {
                @Override
                public void postAction() {
                    pnlGlass.remove(pnlAboutPane);
                    pnlGlass.setVisible(false);
                }
            });



            isAboutVisible = false;
        }
    }

    private void addContent() {

        // Made By //
        JPanel pnlMadeBy = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlMadeBy.setLayout(new BoxLayout(pnlMadeBy, BoxLayout.Y_AXIS));
        pnlMadeBy.setOpaque(false);

        ASlickLabel lblMadeBy = new ASlickLabel("Hand Crafted By");
        lblMadeBy.setFont(coreUI.getRopaFont().deriveFont(Font.PLAIN, 32));
        lblMadeBy.setForeground(Color.WHITE);
        AImage logo = new AImage("sardonix_logo.png");

        pnlMadeBy.add(lblMadeBy);
        pnlMadeBy.add(logo);

        pnlCenterContainer.add(pnlMadeBy);


        // Seperator //
        AImage seperator1 = new AImage("app_seperator.png");
        pnlCenterContainer.add(seperator1);


        // Code Credit //

        JPanel pnlCodeCredit = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlCodeCredit.setLayout(new BoxLayout(pnlCodeCredit, BoxLayout.Y_AXIS));
        pnlCodeCredit.setOpaque(false);

        ASlickLabel lblCodeCreditTitle = new ASlickLabel(
                "Portions of this Software Contains Code From");
        lblCodeCreditTitle.setFont(coreUI.getRopaFont().deriveFont(Font.PLAIN,
                24));
        lblCodeCreditTitle.setForeground(Color.WHITE);

        ASlickLabel lblh2Database = new ASlickLabel(
                "H2 Database - Database Engine");
        lblh2Database.setFont(coreUI.getRopaFont().deriveFont(Font.PLAIN,
                20));
        lblh2Database.setForeground(Color.WHITE);

        ASlickLabel lblRSSParser = new ASlickLabel(
                "Lars Vogel - RSS Parser");
        lblRSSParser.setFont(coreUI.getRopaFont().deriveFont(Font.PLAIN,
                20));
        lblRSSParser.setForeground(Color.WHITE);

        ASlickLabel lblAnalyticsParser = new ASlickLabel(
                "Mixpanel Java - Analytics Library");
        lblAnalyticsParser.setFont(coreUI.getRopaFont().deriveFont(Font.PLAIN,
                20));
        lblAnalyticsParser.setForeground(Color.WHITE);

        ASlickLabel lblJSONParser = new ASlickLabel(
                "JSON in Java - Used for Good, not Evil.");
        lblJSONParser.setFont(coreUI.getRopaFont().deriveFont(Font.PLAIN,
                20));
        lblJSONParser.setForeground(Color.WHITE);

        pnlCodeCredit.add(lblCodeCreditTitle);
        pnlCodeCredit.add(Box.createVerticalStrut(20));
        pnlCodeCredit.add(lblh2Database);
        pnlCodeCredit.add(lblRSSParser);
        pnlCodeCredit.add(lblJSONParser);

        pnlCenterContainer.add(pnlCodeCredit);


        // Seperator //
        AImage seperator2 = new AImage("app_seperator.png");
        pnlCenterContainer.add(seperator2);


        // Special Thanks //

        JPanel pnlSpecialThanks = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlSpecialThanks.setLayout(
                new BoxLayout(pnlSpecialThanks, BoxLayout.Y_AXIS));
        pnlSpecialThanks.setOpaque(false);

        ASlickLabel lblSpecialThanks = new ASlickLabel(
                "Special Thanks To");
        lblSpecialThanks.setFont(coreUI.getRopaFont().deriveFont(Font.PLAIN,
                24));
        lblSpecialThanks.setForeground(Color.WHITE);

        ASlickLabel lblJeno = new ASlickLabel(
                "Jeno-Cyber - Gorgeous Box Art");
        lblJeno.setFont(coreUI.getRopaFont().deriveFont(Font.PLAIN,
                20));
        lblJeno.setForeground(Color.WHITE);

        ASlickLabel lblStackParser = new ASlickLabel(
                "Stack Overflow - Making Life Easier");
        lblStackParser.setFont(coreUI.getRopaFont().deriveFont(Font.PLAIN,
                20));
        lblStackParser.setForeground(Color.WHITE);


        pnlSpecialThanks.add(lblSpecialThanks);
        pnlSpecialThanks.add(Box.createVerticalStrut(20));
        pnlSpecialThanks.add(lblJeno);
        pnlSpecialThanks.add(lblStackParser);

        pnlCenterContainer.add(pnlSpecialThanks);

        // Seperator //
        AImage seperator3 = new AImage("app_seperator.png");
        pnlCenterContainer.add(seperator3);


        // Special Thanks //

        JPanel pnlLicense = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlLicense.setLayout(new BoxLayout(pnlLicense, BoxLayout.Y_AXIS));
        pnlLicense.setOpaque(false);

        ASlickLabel lblLicense = new ASlickLabel(
                "License");
        lblLicense.setFont(coreUI.getRopaFont().deriveFont(Font.PLAIN,
                24));
        lblLicense.setForeground(Color.WHITE);

        ASlickLabel lblLicenseText = new ASlickLabel(
        " This work is licensed under the \n"
        + " Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License. \n"
        + " To view a copy of this license, visit \n"
        + "\n"
        + "'http://creativecommons.org/licenses/by-nc-nd/3.0/' \n "
        +"\n"
        +" or send a letter to Creative Commons, 444 Castro Street, ScoreUIte 900, \n"
        +" Mountain View, California, 94041, USA.\n"
        +" Unless reqcoreUIred by applicable law or agreed to in writing, software\n"
        +" distributed under the License is distributed on an \"AS IS\" BASIS,\n"
        +" WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n"
        +" See the License for the specific language governing permissions and\n"
        +" limitations under the License.");

        lblLicenseText.setFont(coreUI.getRopaFont().deriveFont(
                Font.PLAIN,
                20));
        lblLicenseText.setForeground(Color.WHITE);



        pnlLicense.add(lblLicense);
        pnlLicense.add(Box.createVerticalStrut(20));
        pnlLicense.add(lblLicenseText);

        pnlCenterContainer.add(pnlLicense);








    }

    private class AboutBoxFocusListener implements FocusListener {

        public AboutBoxFocusListener() {
        }

        @Override
        public void focusGained(FocusEvent e) {
            System.out.println("About Box Focus");
        }

        @Override
        public void focusLost(FocusEvent e) {

            System.out.println("Hide About Box");
            hideAboutBox();
        }
    }

    private class LogoMouseListener implements MouseListener {

        public LogoMouseListener() {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            hideAboutBox();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            imgLogo.setImage("app_about_logo_hover.png");
        }

        @Override
        public void mouseExited(MouseEvent e) {
            imgLogo.setImage("app_about_logo.png");
        }
    }
}
