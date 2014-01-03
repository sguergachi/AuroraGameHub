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

import aurora.engine.V1.Logic.AAnimate;
import aurora.engine.V1.Logic.AMixpanelAnalytics;
import aurora.engine.V1.Logic.AThreadWorker;
import aurora.engine.V1.UI.AButton;
import aurora.engine.V1.UI.ADialog;
import aurora.engine.V1.UI.AImagePane;
import aurora.engine.V1.UI.ASlickLabel;
import aurora.engine.V1.UI.ATimeLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.apache.log4j.Logger;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class AuroraLauncher implements Runnable, MouseListener {

    private Game game;

    private JFrame launchPane;

    private AuroraCoreUI coreUI;

    private AImagePane pnlBackground;

    private JPanel pnlTopContainer;

    private JPanel pnlTop;

    private JLabel lblGameName;

    private AImagePane imgGameCover;

    private AImagePane imgTitle;

    private JPanel pnlCenter;

    private JPanel pnlBottom;

    private Thread launcherThread;

    private String timeAfter = null;

    private Process launchGameProcess;

    private ASlickLabel lblPlayedInfo;

    private ASlickLabel lblPlayedTime;

    private boolean manualMode;

    private JPanel pnlTimePlayed;

    private boolean debug = true;

    private AImagePane imgManualMode;

    private AButton btnReturnToAurora;

    static final Logger logger = Logger.getLogger(AuroraLauncher.class);

    private String timeStarted;

    private AButton btnWatch;

    private AButton btnFix;

    private AButton btnLearn;

    private JPanel pnlShortcuts;

    private JPanel pnlTitle;

    private JPanel pnlTopButtons;

    private AButton btnMinimize;

    private AButton btnExit;

    private boolean waiting;

    private boolean exitingGame = false;

    public AuroraLauncher(AuroraCoreUI ui) {
        this.coreUI = ui;
        loadUI();
    }

    private void loadUI() {

        //* Create Frame Containing Launch UI *//
        launchPane = new JFrame("Aurora Launcher");
        launchPane.setSize(coreUI.getFrame().getWidth(), coreUI.getFrame()
                .getHeight());
        launchPane.setBackground(Color.BLACK);
        launchPane.setResizable(false);
        launchPane.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        launchPane.setUndecorated(true);
        launchPane.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        launchPane.addWindowFocusListener(new LaunchFrameFocusListener());
        launchPane.addWindowListener(new WindowAdapter() {
            private int count;

            @Override
            public void windowClosing(WindowEvent e) {
                logger.info("AuroraLauncher Exiting");

                manualMode = false;

                gameExited();

            }

            @Override
            public void windowClosed(WindowEvent e) {

                count++;
                if (count == 1) {
                    logger.info("AuroraLauncher Exited");

                    manualMode = false;

                    launchPane.dispose();
                    launchPane.setVisible(false);

                    launchPane.setAlwaysOnTop(false);
                }
            }
        });

        try {
            launchPane.setIconImage(new ImageIcon(new URL(coreUI.getResource().
                    getSurfacePath() + "/aurora/V1/resources/icon.png")).
                    getImage());
        } catch (MalformedURLException ex) {
            try {

                launchPane.setIconImage(new ImageIcon(getClass().getResource(
                        "/aurora/V1/resources/icon.png")).getImage());

            } catch (Exception exx) {
                logger.error(exx);

            }
        }

        //* Create Panels *//
        pnlBackground = new AImagePane("app_launch_bg.png", launchPane
                .getWidth(),
                launchPane.getHeight(),
                new BorderLayout());

        pnlTop = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 50));
        pnlTop.setOpaque(false);

        pnlTopContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 50));
        pnlTopContainer.setOpaque(false);
        pnlTopContainer.setLayout(new BoxLayout(pnlTopContainer,
                BoxLayout.Y_AXIS));

        pnlCenter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlCenter.setOpaque(false);

        pnlBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 50));
        pnlBottom.setLayout(new BoxLayout(pnlBottom, BoxLayout.Y_AXIS));
        pnlBottom.setOpaque(false);

        pnlTimePlayed = new JPanel();
        pnlTimePlayed.setOpaque(false);
        pnlTimePlayed.setLayout(new BoxLayout(pnlTimePlayed, BoxLayout.Y_AXIS));

        //* Create Content Components *//
        lblGameName = new ASlickLabel();

        lblPlayedInfo = new ASlickLabel("You Played");

        lblPlayedTime = new ASlickLabel();

        // Shortcut Buttons //
        btnWatch = new AButton("game_btn_watch_norm.png",
                "game_btn_watch_down.png",
                "game_btn_watch_over.png");
        btnWatch.addActionListener(new WatchListener());
        btnWatch.setMargin(new Insets(0, 0, 0, 0));
        btnWatch.setBorder(null);
        btnWatch.setBorderPainted(false);

        btnFix = new AButton("game_btn_help_norm.png",
                "game_btn_help_down.png",
                "game_btn_help_over.png");
        btnFix.addActionListener(new FixListener());
        btnFix.setMargin(new Insets(0, 0, 0, 0));
        btnFix.setBorder(null);
        btnFix.setBorderPainted(false);

        btnLearn = new AButton("game_btn_learn_norm.png",
                "game_btn_learn_down.png",
                "game_btn_learn_over.png");
        btnLearn.addActionListener(new LearnListener());
        btnLearn.setMargin(new Insets(0, 0, 0, 0));
        btnLearn.setBorder(null);
        btnLearn.setBorderPainted(false);

        pnlShortcuts = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        pnlShortcuts.setOpaque(false);

        //* Game Cover Icon representing Game *//
        imgGameCover = new AImagePane();

        //* Manual Mode Label Image *//
        imgManualMode = new AImagePane("app_launch_manualMode.png");
        imgManualMode.setPreferredSize(new Dimension(imgManualMode.getImgIcon()
                .getIconWidth(), imgManualMode.getImgIcon().getIconHeight()));

        //* Manual return to Aurora button *//
        btnReturnToAurora = new AButton("app_launch_goBack_norm.png",
                "app_launch_goBack_down.png",
                "app_launch_goBack_norm.png");
        btnReturnToAurora.addActionListener(new GoBackButtonListener());

        //* Title Image Showing Status of Launcing Process *//
        imgTitle = new AImagePane("app_launch_nowLaunching.png");

        //Top Buttons for Title //
        btnMinimize = new AButton("app_launch_minimize_norm.png",
                "app_launch_minimize_down.png",
                "app_launch_minimize_over.png");
        btnMinimize.setBorder(null);
        btnMinimize.setMargin(new Insets(0, 0, 0, 0));

        btnExit = new AButton("app_launch_exit_norm.png",
                "app_launch_exit_down.png",
                "app_launch_exit_over.png");
        btnExit.setBorder(null);
        btnExit.setMargin(new Insets(0, 0, 0, 0));

        btnExit.setEnabled(true);
        btnMinimize.setEnabled(true);

        // Panel containing imgTitle and topButtons
        pnlTitle = new JPanel(new BorderLayout(0, -4));
        pnlTitle.setOpaque(false);

        // Top Button panels
        pnlTopButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, -1, 0));
        pnlTopButtons.setOpaque(false);

        launchPane.requestFocusInWindow();

    }

    private void buildUI() {

        // Fonts
        // --------------------------------------------------------------------.
        lblGameName.setText("  " + game.getGameName());
        //* Gracefull Resizing Based on Length of Game Name *//
        if (lblGameName.getText().length() > 50) {
            lblGameName.setFont(coreUI.getRegularFont().deriveFont(Font.PLAIN,
                    50));
        } else if (lblGameName.getText().length() > 40) {
            lblGameName.setFont(coreUI.getRegularFont().deriveFont(Font.PLAIN,
                    60));
        } else if (lblGameName.getText().length() > 30) {
            lblGameName.setFont(coreUI.getRegularFont().deriveFont(Font.PLAIN,
                    70));
        } else {
            lblGameName.setFont(coreUI.getRegularFont().deriveFont(Font.PLAIN,
                    90));
        }
        lblGameName.setForeground(new Color(103, 103, 103));

        lblPlayedTime
                .setFont(coreUI.getRegularFont().deriveFont(Font.PLAIN, 80));
        lblPlayedTime.setForeground(Color.white);

        lblPlayedInfo
                .setFont(coreUI.getRegularFont().deriveFont(Font.PLAIN, 60));
        lblPlayedInfo.setForeground(new Color(45, 59, 75));

        // Add Components to Panels
        // --------------------------------------------------------------------.
        //* Top Panel *//
        btnExit.addActionListener(new ExitGameListener());
        btnMinimize.addActionListener(new MinimizeListener());

        pnlTopButtons.add(btnExit);
        pnlTopButtons.add(btnMinimize);

        pnlTitle.add(pnlTopButtons, BorderLayout.NORTH);
        pnlTitle.add(imgTitle, BorderLayout.CENTER);

        pnlTopContainer.add(pnlTitle);

        imgTitle.setPreferredSize(new Dimension(imgTitle.getImgIcon()
                .getIconWidth(), imgTitle.getImgIcon().getIconHeight()));

        pnlTitle.setPreferredSize(new Dimension(imgTitle.getRealImageWidth(),
                imgTitle.getRealImageHeight()
                + btnExit.getPreferredSize().height));

        //* Set Game Cover Image *//
        imgGameCover.setImage(game);
        imgGameCover.setImageHeight(launchPane.getHeight() / 3 + 80);
        imgGameCover.setImageWidth(imgGameCover.getImageHeight()
                                   - imgGameCover.getImageHeight() / 15);
        imgGameCover.setPreferredSize(new Dimension(imgGameCover.getImageWidth()
                                                    + 20, imgGameCover
                .getImageHeight()));

        //* Center Content Panel *//
        pnlCenter.add(imgGameCover);

        pnlTimePlayed.setBounds(launchPane.getWidth() + 300, pnlCenter
                .getLocation().y / 2, pnlTimePlayed.getWidth(), pnlTimePlayed
                .getHeight());

        pnlTimePlayed.add(lblPlayedInfo);
        pnlTimePlayed.add(lblPlayedTime);
        lblPlayedTime.setText("                                              ");

        pnlCenter.add(pnlTimePlayed);

        //* Bottom Panel *//
        pnlShortcuts.add(btnWatch);
        pnlShortcuts.add(btnFix);
        pnlShortcuts.add(btnLearn);

        lblGameName.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlShortcuts.setAlignmentX(Component.CENTER_ALIGNMENT);

        pnlBottom.add(pnlShortcuts);
        pnlBottom.add(lblGameName);

        pnlBottom.add(Box.createVerticalStrut(20));

        pnlTop.add(pnlTopContainer);

        //* Add All To Background Panel *//
        pnlBackground.add(BorderLayout.NORTH, pnlTop);
        pnlBackground.add(BorderLayout.CENTER, pnlCenter);
        pnlBackground.add(BorderLayout.SOUTH, pnlBottom);

        //* Add Background to Frame and set Visible *//
        launchPane.add(pnlBackground);
        launchPane.setVisible(true);
        launchPane.repaint();

        pnlTimePlayed.setVisible(false);

    }

    public void launchGame(Game game) {

        this.game = game;

        buildUI();

        launcherThread = null;

        if (launcherThread == null) {
            launcherThread = new Thread(this);
        }
        launcherThread.setName("Launch Game Thread");
        //* start run() *//
        AMixpanelAnalytics mixpanelAnalytics = new AMixpanelAnalytics(
                "f5f777273e62089193a68f99f4885a55");
        mixpanelAnalytics.addProperty("Game being Launched", game.getName());
        mixpanelAnalytics.addProperty("Times a game launched", true);
        mixpanelAnalytics.sendEventProperty("Launched Game");

        launcherThread.start();
    }

    @Override
    public void run() {
        while (Thread.currentThread() == launcherThread) {

            try {

                // Launch Game For Windows
                // ------------------------------------------------------------.
                if (coreUI.getOS().contains("Windows")) {

                    //* Check if using .exe as executable *//
                    if (game.getGamePath().endsWith("exe")) {

                        //* Get the directory *//
                        ProcessBuilder processBuild = new ProcessBuilder(game
                                .getGamePath());

                        if (logger.isDebugEnabled()) {
                            logger.debug("EXE Directory: " + game
                                    .getGamePath()
                                    .substring(0, game.getGamePath()
                                            .lastIndexOf(
                                                    "\\") + 1).replace("\\",
                                            "\\"));
                        }

                        processBuild.directory(new File(game.getGamePath()
                                .substring(0, game.getGamePath().lastIndexOf(
                                                "\\") + 1).replace("\\", "\\")));

                        //* Launch Game *//
                        launchGameProcess(processBuild);

                        //* If not .exe then its a shortcut (.ink or .url) *//
                    } else if (game.getGamePath().endsWith("bat")) {

                        //* Get the directory *//
                        String currentDir = new File(game.getGamePath())
                                .getCanonicalPath();
                        currentDir = currentDir.substring(0, currentDir
                                .lastIndexOf("\\") + 1) + '"' + currentDir
                                .substring(currentDir.lastIndexOf("\\") + 1,
                                        currentDir.length()) + '"';
                        if (logger.isDebugEnabled()) {
                            logger.debug("Shortcut Directory: " + currentDir);
                        }

                        //* Set Commands to launch shortcut *//
                        Process processBuild = Runtime.getRuntime().exec(
                                "cmd /c start " + currentDir);
                        launchGameProcess(processBuild);
                    } else {

                        //* Get the directory *//
                        String currentDir = new File(game.getGamePath())
                                .getCanonicalPath();
                        currentDir = currentDir.substring(0, currentDir
                                .lastIndexOf("\\") + 1) + '"' + currentDir
                                .substring(currentDir.lastIndexOf("\\") + 1,
                                        currentDir.length()) + '"';
                        if (logger.isDebugEnabled()) {
                            logger.debug("Shortcut Directory: " + currentDir);
                        }

                        //* Set Commands to launch shortcut *//
                        ProcessBuilder processBuild = new ProcessBuilder();
                        processBuild.command("cmd", "/c", "", '"' + currentDir
                                                              + '"');

                        //* Launch Game *//
                        launchGameProcess(processBuild);

                    }

                    // Launch Game For Mac OS X
                    // --------------------------------------------------------.
                } else if (coreUI.getOS().contains("Mac OS X") || debug) {

                    //* escaping the spaces in the game path *//
                    String workingDir = game.getGamePath().replace(" ", "\\ ");
                    workingDir = workingDir.substring(0, workingDir.lastIndexOf(
                            "/") + 1);

                    //* Set Commands to launch shortcut *//
                    ProcessBuilder processBuild = new ProcessBuilder();
                    processBuild.command("open", "-W", game.getGamePath());

                    //* Launch Game *//
                    launchGameProcess(processBuild);

                }
            } catch (IOException ex) {

                //* Handle Exeption Unable to Find or Launch Game *//
                ADialog error = new ADialog(ADialog.aDIALOG_ERROR,
                        "Unable To Find & Launch Game.",
                        coreUI.getBoldFont()
                        .deriveFont(28));

                error.setOKButtonListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        launchPane.setVisible(false);
                        launchPane.dispose();
                        coreUI.getFrame().setVisible(true);
                        coreUI.getFrame().setState(JFrame.NORMAL);

                    }
                });
                error.setVisible(true);
                logger.error(ex);

            }

            // Game Has Exited
            // ----------------------------------------------------------------.
            //* Calculate Time *//
            calculateTimePlayed();

            //* Decide whether the game has trully quit *//
            if (manualMode) {

                showManualTimerUI();

                break;
            } else {
                //* Change Title *//
                imgTitle.setImage("app_launch_standBy.png");

                launchPane.setState(JFrame.NORMAL);
                launchPane.setAlwaysOnTop(true);
                showTimeSpentPlaying();

                break;
            }

        }

    }

    private void gameExited() {

        // Game Has Exited
        // ----------------------------------------------------------------.
        //* Calculate Time *//
        if (timeAfter == null) {

            if (timeStarted == null) {
                timeStarted = ATimeLabel.current(
                        ATimeLabel.TIME_24HOUR);
            }

            calculateTimePlayed();
        }
        //* Decide whether the game has trully quit *//
        if (manualMode) {

            showManualTimerUI();

        } else {

            //* Change Title *//
            imgTitle.setImage("app_launch_standBy.png");
            exitingGame = true;

            launchPane.setState(JFrame.NORMAL);
            launchPane.setAlwaysOnTop(true);

            showTimeSpentPlaying();

        }

    }

    public void showManualTimerUI() {

        if (launchPane.isFocused()) {
            pnlTop.removeAll();
            pnlTop.validate();
            pnlTop.add(btnReturnToAurora);
            pnlTop.revalidate();
        } else {
            pnlTopContainer.add(imgManualMode);
            pnlTopContainer.revalidate();
            launchPane.requestFocusInWindow();
        }
    }

    private void launchGameProcess(ProcessBuilder processBuild) {
        try {
            launchGameProcess = processBuild.start();
        } catch (Exception ex) {

            ADialog error = new ADialog(
                    ADialog.aDIALOG_ERROR,
                    "Unable to launch Game "
                    + "\n Make Sure To Launch Aurora In Administrator Mode",
                    coreUI.getRegularFont()
                    .deriveFont(Font.BOLD, 25));

            error.showDialog();

            logger.error(ex);
            ex.printStackTrace();

            error.setVisible(true);
        }
        coreUI.getFrame().setState(JFrame.ICONIFIED);
        coreUI.getFrame().setVisible(false);

        //* Pause A Bit To Let Game Start *//
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            logger.error(ex);

        }

        timeStarted = ATimeLabel.current(
                ATimeLabel.TIME_24HOUR);

        //* Tracker Data *//
        game.setLastPlayed(ATimeLabel.current(ATimeLabel.DATE));
        game.setOcurrencesPlayed(game.getOccurencesPlayed() + 1);

        //* Change Title to "Playing..." *//
        imgTitle.setImage("app_launch_playing.png");

        //* Allow for Alt-Tabing  while playing Game *//
        launchPane.setAlwaysOnTop(false);

        //* Wait For Game To Exit *//
        try {
            waiting = true;
            launchGameProcess.waitFor();

        } catch (Exception ex) {
            logger.error(ex);
            ex.printStackTrace();
        }

        waiting = false;

    }

    private void launchGameProcess(Process process) {

        coreUI.getFrame().setState(JFrame.ICONIFIED);
        coreUI.getFrame().setVisible(false);

        //* Pause A Bit To Let Game Start *//
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            logger.error(ex);

        }

        timeStarted = ATimeLabel.current(
                ATimeLabel.TIME_24HOUR);

        //* Tracker Data *//
        game.setLastPlayed(ATimeLabel.current(ATimeLabel.DATE));
        game.setOcurrencesPlayed(game.getOccurencesPlayed() + 1);

        //* Change Title to "Playing..." *//
        imgTitle.setImage("app_launch_playing.png");

        //* Allow for Alt-Tabing  while playing Game *//
        launchPane.setAlwaysOnTop(false);

        //* Wait For Game To Exit *//
        try {
            waiting = true;
            process.waitFor();

        } catch (Exception ex) {
            logger.error(ex);
            ex.printStackTrace();
        }

        waiting = false;

    }

    private void calculateTimePlayed() {

        timeAfter = ATimeLabel.current(ATimeLabel.TIME_24HOUR);
        logger.info(game.getLastPlayed());
        logger.info(timeAfter);

        //* Elapsed Time Calculation *//
        int hoursDiff = Math.abs(Integer.parseInt(timeAfter.substring(0, 2))
                                 - Integer.parseInt(timeStarted
                .substring(0, 2))) * 60;
        int minDiff = Math.abs(Integer.parseInt(timeAfter.substring(3, 5))
                               - Integer.parseInt(timeStarted
                .substring(3, 5)));
        //ELAPSED TIME IN MIN IS ((HOURS*60) - MIN FROM TIME1) + MIN FROM TIME2
        int elapsedTime = Math.abs((hoursDiff - Integer.parseInt(timeAfter
                .substring(3, 5))) + Integer.parseInt(timeStarted
                        .substring(3, 5)));

        hoursDiff = elapsedTime / 60;
        minDiff = elapsedTime - (hoursDiff * 60);

        logger.info("Elapsed " + elapsedTime);
        logger.info("Hours " + hoursDiff);
        logger.info("Min " + minDiff);

        if (minDiff <= 1 && hoursDiff < 1) {
            lblPlayedTime.setText("Under 1 min  ");
            if (!manualMode) {
                manualMode = true;
            }
        } else if (hoursDiff < 1 && minDiff > 1) {
            lblPlayedTime.setText(minDiff + " min  ");
        } else {
            lblPlayedTime.setText(hoursDiff + "hr and "
                                  + minDiff + "min  ");
        }

        launchPane.setVisible(true);
        launchPane.setAlwaysOnTop(true);
        launchPane.requestFocusInWindow();
        launchPane.setAlwaysOnTop(false);

        // Add to total time played this game //
        game.addTime(minDiff, hoursDiff);

        minDiff = 0;
        hoursDiff = 0;

    }

    /**
     * .-----------------------------------------------------------------------.
     * | showTimeSpentPlaying()
     * .-----------------------------------------------------------------------.
     * |
     * |
     * | Shows how much time was spent playing in the form of an animation
     * | First the Game icons slides to the right slightly, and then the
     * | panel containing the label with how much time was spent playing slides
     * | in from the right.
     * |
     * .........................................................................
     *
     */
    private void showTimeSpentPlaying() {

        AThreadWorker showTime = new AThreadWorker(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                pnlTop.removeAll();
                pnlTop.validate();
                pnlTop.add(pnlTopContainer);
                pnlTop.revalidate();
                imgTitle.setImage("app_launch_standBy.png");

                pnlTimePlayed.setVisible(true);
                pnlTimePlayed.repaint();
                pnlTimePlayed.setBounds(launchPane.getWidth() + 300, pnlCenter
                        .getLocation().y / 2, pnlTimePlayed.getWidth(),
                        pnlTimePlayed
                        .getHeight());

                pnlCenter.setLayout(null);
                imgGameCover.setLocation(imgGameCover.getLocation());
                pnlCenter.revalidate();

                pnlTitle.setIgnoreRepaint(true);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    logger.error(ex);

                }

                AAnimate animateCover = new AAnimate(imgGameCover);
                AAnimate animateTime = new AAnimate(pnlTimePlayed);

                animateCover
                        .setInitialLocation(imgGameCover.getLocation().x,
                                imgGameCover
                                .getLocation().y);

                animateTime.setInitialLocation(launchPane.getWidth() + 300,
                        pnlCenter
                        .getLocation().y / 2);

                animateCover.moveHorizontal(launchPane.getWidth() / 6, -2);
                animateTime.moveHorizontal(launchPane.getWidth() / 2 - 50, -10);

                launchPane.repaint();

            }
        }, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                goBackToAurora();

            }
        });

        showTime.startOnce();
    }

    private void goBackToAurora() {

        //* Save Metadata Then Go Back To Aurora *//
        logger.info("Saved Metadata");
        game.saveMetadata();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            logger.error(ex);
        }

        launchPane.setVisible(false);
        launchPane.dispose();

        coreUI.getFrame().setVisible(true);
        coreUI.getFrame().setState(JFrame.NORMAL);
        manualMode = false;

        game.showOverlayUI();
        pnlCenter.setLayout(new FlowLayout(FlowLayout.CENTER));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        launchPane.requestFocusInWindow();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        launchPane.requestFocusInWindow();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        launchPane.requestFocusInWindow();
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private class ExitGameListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (!exitingGame) {

                if (waiting) {
                    launchGameProcess.destroy();
                }

                launchPane.setAlwaysOnTop(true);
                launchPane.setState(JFrame.NORMAL);

                manualMode = false;

                gameExited();

            }
        }
    }

    private class MinimizeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (!exitingGame) {
                launchPane.setState(JFrame.ICONIFIED);
            }

        }
    }

    private class GoBackButtonListener implements ActionListener {

        public GoBackButtonListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            AThreadWorker worker = new AThreadWorker(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    calculateTimePlayed();
                    showTimeSpentPlaying();

                }
            });

            worker.startOnce();

        }
    }

    private class LaunchFrameFocusListener implements WindowFocusListener {

        public LaunchFrameFocusListener() {
        }

        @Override
        public void windowGainedFocus(WindowEvent e) {
            if (logger.isDebugEnabled()) {
                logger.debug("LAUNCH FRAME FOCUS GAINED");
            }

            if (manualMode) {

                pnlTop.removeAll();
                pnlTop.validate();
                pnlTop.add(btnReturnToAurora);
                pnlTop.revalidate();

            }
        }

        @Override
        public void windowLostFocus(WindowEvent e) {
            if (logger.isDebugEnabled()) {
                logger.debug("LAUNCH FRAME FOCUS LOST");
            }

            if (manualMode) {
                pnlTop.removeAll();
                pnlTop.validate();
                pnlTop.add(pnlTopContainer);
                pnlTop.revalidate();
            }

        }
    }

    /**
     * .-----------------------------------------------------------------------.
     * | WatchListener
     * .-----------------------------------------------------------------------.
     * |
     * | Listener for the Watch shortcut button to link to the Youtube search
     * | results for the game
     * |
     * .........................................................................
     * <p/>
     */
    private class WatchListener implements ActionListener {

        public WatchListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            String url = "http://www.youtube.com/results?search_query=";
            String gameName = game.getName().replace(" ", "+").replace("'", "");;
            url += gameName;

            try {
                try {
                    Desktop.getDesktop().browse(new URI(url));
                } catch (URISyntaxException ex) {
                    java.util.logging.Logger.getLogger(Game.class.getName()).
                            log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(Game.class.getName()).
                        log(Level.SEVERE, null, ex);
            }

        }
    }

    /**
     * .-----------------------------------------------------------------------.
     * | FixListener
     * .-----------------------------------------------------------------------.
     * |
     * | Listener for the Watch shortcut button to link to the PCgamingWiki
     * | search results for the game
     * |
     * .........................................................................
     * <p/>
     */
    private class FixListener implements ActionListener {

        public FixListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            String url = "http://pcgamingwiki.com/wiki/";
            String gameName = game.getName().replace(" ", "_").replace("'", "");
            url += gameName;

            try {
                try {
                    Desktop.getDesktop().browse(new URI(url));
                } catch (URISyntaxException ex) {
                    java.util.logging.Logger.getLogger(Game.class.getName()).
                            log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(Game.class.getName()).
                        log(Level.SEVERE, null, ex);
            }

        }
    }

    /**
     * .-----------------------------------------------------------------------.
     * | LearnListener
     * .-----------------------------------------------------------------------.
     * |
     * | Listener for the Watch shortcut button to link to the Wikia
     * | search results for the game
     * |
     * .........................................................................
     * <p/>
     */
    private class LearnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String url = "http://www.google.com/search?q=";
            String gameName = game.getName().trim().replace(" ", "+");
            url = url + gameName + "+wiki&btnI";

            try {
                try {
                    Desktop.getDesktop().browse(new URI(url));
                } catch (URISyntaxException ex) {
                    java.util.logging.Logger.getLogger(Game.class.getName()).
                            log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(Game.class.getName()).
                        log(Level.SEVERE, null, ex);
            }

        }
    }
}
