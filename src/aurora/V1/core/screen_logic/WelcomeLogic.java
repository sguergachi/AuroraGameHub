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
package aurora.V1.core.screen_logic;

import aurora.V1.core.AuroraCoreUI;
import aurora.V1.core.main;
import aurora.V1.core.screen_handler.WelcomeHandler;
import aurora.V1.core.screen_ui.DashboardUI;
import aurora.V1.core.screen_ui.WelcomeUI;
import aurora.engine.V1.Logic.AFileManager;
import aurora.engine.V1.Logic.AMixpanelAnalytics;
import aurora.engine.V1.Logic.AThreadWorker;
import aurora.engine.V1.Logic.AuroraScreenHandler;
import aurora.engine.V1.Logic.AuroraScreenLogic;
import aurora.engine.V1.UI.AImage;
import aurora.engine.V1.UI.APrompter;
import aurora.engine.V1.UI.AScrollingImage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class WelcomeLogic implements AuroraScreenLogic {

    private final WelcomeUI startScreenUI;

    private WelcomeHandler startHandler;

    private final AuroraCoreUI coreUI;

    private AScrollingImage imgHexPane;

    private AImage imgTopLogo;

    private int topHeight;

    private AImage imgTopLogoSmall;

    private int topSmallImageHeight;

    private int topSmallImageWidth;

    private AThreadWorker animateTransision;

    private int centerHeight;

    private DashboardUI dashboardUI;

    static final Logger logger = Logger.getLogger(WelcomeLogic.class);

    private int frameControlHeight;

    private AFileManager fileIO;

    public WelcomeLogic(WelcomeUI aStartScreenUI) {

        this.startScreenUI = aStartScreenUI;
        this.coreUI = startScreenUI.getCoreUI();

    }

    @Override
    public void setHandler(AuroraScreenHandler handler) {
        startHandler = (WelcomeHandler) handler;
    }

    private void loadTransitionUI() {

        setSize();

        imgHexPane = startScreenUI.getImgHexPane();
        imgTopLogo = coreUI.getLogoImage();
        imgTopLogoSmall = new AImage("dash_header_logo.png");


        imgTopLogoSmall.setImageSize(topSmallImageWidth, topSmallImageHeight);

    }

    public void transisionToDashboard() {

        loadTransitionUI();

        animateTransision = new AThreadWorker(new ActionListener() {
            // Times cycling through threadWorker loop
            private int c = 0;

            // Scale of Hex Image growning
            private int scale = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                c++;
                if (c == 1) {
                    // change header logo to smaller logo
                    imgTopLogo.setIcon(imgTopLogoSmall.getImgIcon());
                    imgTopLogo.repaint();

                    // stop scrolling Animation
                    imgHexPane.stop();

                    // Remove Panel Containing Frame Controls*//
                    coreUI.getTopPane().remove(coreUI.getSouthFromTopPanel());
                    coreUI.getSouthFromTopPanel().setVisible(false);
                    coreUI.getTopPane().revalidate();

                    imgHexPane.setCenterToFrame(coreUI.getFrame());
                    imgHexPane.repaint();

                } else {
                    // Change Size Values
                    scale++;
                    topHeight--;
                    centerHeight += 5;

                    // Change Component Sizes
                    imgHexPane.grow(scale);
                    coreUI.getTopPane().setImageHeight(topHeight);
                    imgHexPane.repaint();
                    imgHexPane.revalidate();

                    coreUI.getTopPane().setPreferredSize(new Dimension(coreUI
                            .getTopPane()
                            .getWidth(),
                                                                       topHeight
                                                                       - 50));
                    coreUI.getCenterPanel()
                            .setPreferredSize(new Dimension(coreUI
                                            .getCenterPanel()
                                            .getWidth(),
                                                            centerHeight));




                    if (topHeight >= imgTopLogoSmall.getImgIcon()
                            .getIconHeight() + 300) {
                        centerHeight = centerHeight - 2;
                        topHeight = imgTopLogoSmall.getImgIcon().getIconHeight()
                                            + 50;
                        coreUI.getTopPane().setImageHeight(topHeight);

                    }

                    // Check if Reached Proper Size to stop
                    if (scale == 37) {

                        showDashdoard();
                        animateTransision.stop();
                    }

                }


            }
        }, 18);

        animateTransision.start();

    }

    private void showDashdoard() {

        AThreadWorker loadDashboard = new AThreadWorker(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Re-add Frame Controls

                coreUI.getTopPane().add(BorderLayout.PAGE_END, coreUI
                                        .getSouthFromTopPanel());
                coreUI.getTopPane().revalidate();
                coreUI.getSouthFromTopPanel().revalidate();

                // Set bigger background image for Frame Control panel
                coreUI.getFrameControlImagePane().setImage(
                        "dash_frameControl_bg.png");
                coreUI.getFrameControlImagePane().repaint();
                coreUI.getFrameControlImagePane().setImageHeight(
                        frameControlHeight);
                coreUI.getFrameControlImagePane().revalidate();


                // Remove all components in Center Panel
                coreUI.getCenterPanel().removeAll();

                // Get or Generate new DashboardUI
                dashboardUI = startScreenUI.getLoadedDashboardUI();

                if (dashboardUI == null) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Creating New Dashboard");
                    }
                    dashboardUI = new DashboardUI(coreUI, startScreenUI);
                    dashboardUI.loadUI();
                } else {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Using LOADED dashboard");
                    }
                }

                // Build DashboardUI
                dashboardUI.buildUI();

                coreUI.getSouthFromTopPanel().setVisible(true);


            }
        });

        loadDashboard.startOnce();

    }

    public static boolean checkOnline(String URL) {
        final URL url;
        try {
            url = new URL(URL);
            try {

                final URLConnection conn = url.openConnection();
                conn.connect();
            } catch (IOException ex) {
                logger.warn("Computer is not online");
                return false;
            }
        } catch (MalformedURLException ex) {
            logger.error(ex);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Computer is online");
        }

        return true;
    }

    public void sendAnalytics() {
        AMixpanelAnalytics analytics = new AMixpanelAnalytics(
                "f5f777273e62089193a68f99f4885a55");
        analytics.addProperty("Version", main.VERSION + " b" + coreUI
                              .getBuildNumber());
        analytics.addProperty("Resolution", coreUI.getScreenHeight() + "x"
                                                    + coreUI.getScreenWidth());
        analytics
                .addProperty("Java Version", System.getProperty("java.version"));
        analytics.addProperty("OS", System.getProperty("os.name"));
        analytics.sendEventProperty("Launched Aurora");


    }

    public void incrementAuroraLaunch() {

        String launches = startScreenUI.getAuroraStorage().getStoredSettings()
                .getSettingValue(
                        "launch");

        if (launches == null || launches.equals("null")) {
            startScreenUI.getAuroraStorage().getStoredSettings().saveSetting(
                    "launch", "1");
            main.LAUNCHES = 1;
        } else {
            int value = Integer.parseInt(launches) + 1;
            launches = Integer.toString(value);
            startScreenUI.getAuroraStorage().getStoredSettings().saveSetting(
                    "launch", launches);
            main.LAUNCHES = Integer.parseInt(launches);
        }

        logger.info("Number of Launches: " + main.LAUNCHES);

    }

    public void moveAuroraDB(APrompter promptDisplay) {
        logger.info("Moving AuroraDB to AuroraData folder...");

        String installPath = WelcomeUI.class.getProtectionDomain()
                .getCodeSource().getLocation().getPath().replaceFirst(
                        "AuroraGameHub.jar", "").replaceAll("%20", " ");
        String auroraDbPath = installPath + "/lib/AuroraDB.h2.db";

        logger.info(" >>> auroraDB Path " + auroraDbPath);
        logger.info(" >>> installPath Path " + installPath);

        if (!fileIO.checkFile(fileIO
                .getPath() + "AuroraDB.h2.db")
                    || fileIO.checkFile(installPath + "/updateDB")) {

            if (fileIO.checkFile(installPath + "/updateDB")) {
                try {
                    fileIO.deleteFile(new File(installPath + "/updateDB"));
                } catch (IOException ex) {
                    java.util.logging.Logger
                            .getLogger(WelcomeUI.class.getName()).
                            log(Level.SEVERE, null, ex);
                }
            }

            if (fileIO.checkFile(auroraDbPath)) {
                try {
                    fileIO.copyFile(new File(auroraDbPath), new File(fileIO
                                    .getPath() + "AuroraDB.h2.db"));
                } catch (IOException ex) {
                    java.util.logging.Logger
                            .getLogger(WelcomeUI.class.getName()).
                            log(Level.SEVERE, null, ex);
                }
            } else {
                logger.info("Did Not Move AuroraDB to AuroraData");
                promptDisplay
                        .add("Downloading AuroraCoverDB...", new Color(0, 191,
                                                                       255));

                downloadAuroraDB();

            }
        }
    }

    public Boolean downloadAuroraDB() {
        logger.info("Downloading AuroraDB...");
        try {
            fileIO.downloadFile(new URL(
                    "http://s3.amazonaws.com/AuroraStorage/AuroraDB.h2.db"),
                                new File(fileIO.getPath() + "/AuroraDB.h2.db"));

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(WelcomeUI.class.getName()).
                    log(Level.SEVERE, null, ex);
            logger.info("Un-Sucessful Download!");
            return false;
        }
        logger.info("Successful Download");
        return true;

    }

    public boolean checkUser() {
        if (checkSubDir()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkMainDir() {

        if (fileIO.checkFile(fileIO.getPath() + main.DATA_PATH)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkSubDir() {
        if (fileIO.checkFile(fileIO.getPath() + main.DATA_PATH + "/User Data") && fileIO.
                checkFile(fileIO.getPath() + main.DATA_PATH + "/Game Data")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkDBFiles() {
        if (fileIO.checkFile(fileIO.getPath() + "/User Data/User.h2.db")
                    && fileIO.
                checkFile(fileIO.getPath() + "/Game Data/Games.h2.db")) {
            return true;
        } else {
            return false;
        }
    }

    private void setSize() {

        if (coreUI.isLargeScreen()) {
            frameControlHeight = 0;
            topHeight = coreUI.getTopPane().getHeight();
            centerHeight = coreUI.getCenterPanel().getHeight() + 60;
            topSmallImageHeight = coreUI.getCenterPanel().getHeight() / 16 + 20;
            topSmallImageWidth = coreUI.getFrame().getWidth() / 2 + 20;
        } else {
            frameControlHeight = coreUI.getFrameControlImagePane().getImgIcon()
                    .getIconHeight();
            topHeight = coreUI.getTopPane().getHeight();
            centerHeight = coreUI.getCenterPanel().getHeight() + 60;
            topSmallImageHeight = coreUI.getCenterPanel().getHeight() / 16 + 20;
            topSmallImageWidth = coreUI.getFrame().getWidth() / 2 + 20;
        }


    }

    public void setFileIO(AFileManager fileIO) {
        this.fileIO = fileIO;
    }
}
