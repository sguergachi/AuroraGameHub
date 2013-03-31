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

import aurora.V1.core.screen_handler.LibraryHandler;
import aurora.engine.V1.Logic.AAnimate;
import aurora.engine.V1.Logic.APostHandler;
import aurora.engine.V1.UI.AImage;
import aurora.engine.V1.UI.AImagePane;
import aurora.engine.V1.UI.AScrollBar;
import aurora.engine.V1.UI.ASlickLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import org.apache.log4j.Logger;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class AboutBox {

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

    static final Logger logger = Logger.getLogger(AboutBox.class);

    public AboutBox(AuroraCoreUI CoreUI) {

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

        pnlCenterContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlCenterContainer.setOpaque(false);

        pnlCenter = new AImagePane("app_about_center.png", pnlAboutPane
                .getRealImageWidth(), 0);

        pnlBottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlBottom.setOpaque(false);

        imgLogo = new AImagePane("app_about_logo.png");
        imgLogo.addMouseListener(new LogoMouseListener());

        // Scroll Bar //
        scrollBar = new JScrollBar();
        scrollBar.setUnitIncrement(30);
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
        lblBuild = new ASlickLabel("build: " + buildNumber.trim());

    }

    public void buildAboutUI() {

        // Top Panel //
        lblVersion.setFont(coreUI.getRopaFont().deriveFont(Font.PLAIN, 46));
        lblBuild.setFont(coreUI.getRopaFont().deriveFont(Font.PLAIN, 17));

        lblVersion.setForeground(Color.white);
        lblBuild.setForeground(Color.white);

        lblVersion.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblBuild.setAlignmentX(Component.CENTER_ALIGNMENT);

        pnlVersion.add(lblVersion, BorderLayout.CENTER);
        pnlVersion.add(lblBuild, BorderLayout.SOUTH);
        pnlTop.add(pnlVersion);

        // Center Panel //
        scrollPane.setBorder(null);
        scrollPane.setWheelScrollingEnabled(true);

        pnlCenter.setLayout(new BorderLayout());
        addContent();

        pnlCenterContainer.setPreferredSize(new Dimension(pnlCenter
                .getRealImageWidth(), (pnlAboutPane.getRealImageHeight() / 2)
                                      * 5));

        pnlCenter.add(scrollPane);

        // Bottom Pane //
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
        pnlMadeBy.setOpaque(false);
        pnlMadeBy.setBackground(Color.MAGENTA);

        ASlickLabel lblMadeBy = new ASlickLabel("Hand Crafted By");
        lblMadeBy.setLink("auroragamehub.com/about");
        lblMadeBy.setFont(coreUI.getRopaFont().deriveFont(Font.PLAIN, 30));
        lblMadeBy.setForeground(Color.WHITE);

        AImage logo = new AImage("sardonix_logo.png");
        logo.setLink("auroragamehub.com/about");

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
        lblCodeCreditTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        ASlickLabel lblh2Database = new ASlickLabel(
                "H2 Database - Database Engine");
        lblh2Database.setLink("http://www.h2database.com");
        lblh2Database.setFont(coreUI.getRopaFont().deriveFont(Font.PLAIN,
                20));
        lblh2Database.setForeground(Color.WHITE);
        lblh2Database.setAlignmentX(Component.CENTER_ALIGNMENT);

        ASlickLabel lblRSSParser = new ASlickLabel(
                "Lars Vogel - RSS Parser");
        lblRSSParser.setLink("http://www.vogella.com/");
        lblRSSParser.setFont(coreUI.getRopaFont().deriveFont(Font.PLAIN,
                20));
        lblRSSParser.setForeground(Color.WHITE);
        lblRSSParser.setAlignmentX(Component.CENTER_ALIGNMENT);

        ASlickLabel lblAnalyticsParser = new ASlickLabel(
                "Mixpanel Java - Analytics Library");
        lblAnalyticsParser.setLink("https://github.com/mixpanel/mixpanel-java");
        lblAnalyticsParser.setFont(coreUI.getRopaFont().deriveFont(Font.PLAIN,
                20));
        lblAnalyticsParser.setForeground(Color.WHITE);
        lblAnalyticsParser.setAlignmentX(Component.CENTER_ALIGNMENT);

        ASlickLabel lblJSONParser = new ASlickLabel(
                "JSON in Java - Used for Good, not Evil.");
        lblJSONParser.setLink("http://json.org/java/");
        lblJSONParser.setFont(coreUI.getRopaFont().deriveFont(Font.PLAIN,
                20));
        lblJSONParser.setForeground(Color.WHITE);
        lblJSONParser.setAlignmentX(Component.CENTER_ALIGNMENT);

        ASlickLabel lblLogger = new ASlickLabel(
                "Apache log4j - Logging Library for Java.");
        lblLogger.setLink("http://logging.apache.org/log4j/1.2/");
        lblLogger.setFont(coreUI.getRopaFont().deriveFont(Font.PLAIN,
                20));
        lblLogger.setForeground(Color.WHITE);
        lblLogger.setAlignmentX(Component.CENTER_ALIGNMENT);

        pnlCodeCredit.add(lblCodeCreditTitle);
        pnlCodeCredit.add(Box.createVerticalStrut(30));
        pnlCodeCredit.add(lblRSSParser);
        pnlCodeCredit.add(lblh2Database);
        pnlCodeCredit.add(lblJSONParser);
        pnlCodeCredit.add(lblLogger);


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
        lblSpecialThanks.setAlignmentX(Component.CENTER_ALIGNMENT);

        ASlickLabel lblJeno = new ASlickLabel(
                "Jeno-Cyber - Gorgeous Box Art");
        lblJeno.setLink("http://jeno-cyber.deviantart.com/");
        lblJeno.setFont(coreUI.getRopaFont().deriveFont(Font.PLAIN,
                20));
        lblJeno.setForeground(Color.WHITE);
        lblJeno.setAlignmentX(Component.CENTER_ALIGNMENT);

        ASlickLabel lblStackOverflow = new ASlickLabel(
                "Stack Overflow - Making Life Easier");
        lblStackOverflow.setLink("http://stackoverflow.com");
        lblStackOverflow.setFont(coreUI.getRopaFont().deriveFont(Font.PLAIN,
                20));
        lblStackOverflow.setForeground(Color.WHITE);
        lblStackOverflow.setAlignmentX(Component.CENTER_ALIGNMENT);

        ASlickLabel lblUsers = new ASlickLabel(
                "Our Users and Fans -  We Love You! <3");
        lblUsers.setLink("https://soundcloud.com/giovanniangel");
        lblUsers.setFont(coreUI.getRopaFont().deriveFont(Font.PLAIN,
                20));
        lblUsers.setForeground(Color.WHITE);
        lblUsers.setAlignmentX(Component.CENTER_ALIGNMENT);


        pnlSpecialThanks.add(lblSpecialThanks);
        pnlSpecialThanks.add(Box.createVerticalStrut(30));
        pnlSpecialThanks.add(lblJeno);
        pnlSpecialThanks.add(lblStackOverflow);
        pnlSpecialThanks.add(lblUsers);

        pnlCenterContainer.add(pnlSpecialThanks);

        // Seperator //
        AImage seperator3 = new AImage("app_seperator.png");
        pnlCenterContainer.add(seperator3);


        // Special Thanks //

        JPanel pnlLicense = new JPanel();
        pnlLicense.setLayout(new BoxLayout(pnlLicense, BoxLayout.Y_AXIS));
        pnlLicense.setOpaque(false);
        pnlLicense.setMaximumSize(pnlCenterContainer.getPreferredSize());

        ASlickLabel lblLicense = new ASlickLabel(
                "License");
        lblLicense.setFont(coreUI.getRopaFont().deriveFont(Font.PLAIN,
                24));
        lblLicense.setForeground(Color.WHITE);
        lblLicense.setAlignmentX(Component.CENTER_ALIGNMENT);

        ASlickLabel lblLicenseText = new ASlickLabel(
                "<html> This work is licensed under the <br>"
                + " Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.<br>"
                + " To view a copy of this license, visit <br>"
                + "'http://creativecommons.org/licenses/by-nc-nd/3.0/' <br> "
                + " or send a letter to Creative Commons, 444 Castro Street, ScoreUIte 900, <br>"
                + " Mountain View, California, 94041, USA.<br>"
                + " Unless required by applicable law or agreed to in writing, software<br>"
                + " distributed under the License is distributed on an \"AS IS\" BASIS,<br>"
                + " WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.<br>"
                + " See the License for the specific language governing permissions and<br>"
                + " limitations under the License. </html>");
        lblLicenseText.setLink(
                "http://creativecommons.org/licenses/by-nc-nd/3.0/");
        lblLicenseText.setFont(coreUI.getRopaFont().deriveFont(
                Font.PLAIN,
                16));
        lblLicenseText.setForeground(Color.WHITE);
        lblLicenseText.setAlignmentX(Component.CENTER_ALIGNMENT);


        pnlLicense.add(lblLicense);
        pnlLicense.add(Box.createVerticalStrut(30));
        pnlLicense.add(lblLicenseText);

        pnlCenterContainer.add(pnlLicense);

    }

    private class AboutBoxFocusListener implements FocusListener {

        public AboutBoxFocusListener() {
        }

        @Override
        public void focusGained(FocusEvent e) {

            if (logger.isDebugEnabled()) {
                logger.debug("About Box Focus");
            }

        }

        @Override
        public void focusLost(FocusEvent e) {

            if (logger.isDebugEnabled()) {
                logger.debug("Hide About Box");
            }

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
