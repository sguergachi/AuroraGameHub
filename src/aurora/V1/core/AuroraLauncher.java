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
import aurora.engine.V1.UI.AImage;
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
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Period;

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

    private AImage imgTitle;

    private JPanel pnlCenter;

    private JPanel pnlBottom;

    private Thread launcherThread;

    private DateTime timeAfter = null;

    private Process launchGameProcess;

    private ASlickLabel lblPlayedInfo;

    private ASlickLabel lblPlayedTime;

    private boolean manualMode;

    private JPanel pnlTimePlayed;

    private boolean debug = true;

    static final Logger logger = Logger.getLogger(AuroraLauncher.class);

    private DateTime timeStarted;

    private AButton btnWatch;

    private AButton btnFix;

    private AButton btnLearn;

    private JPanel pnlShortcuts;

    private AImagePane pnlTopTitle;

    private JPanel pnlButtonContainer;

    private AButton btnMinimize;

    private AButton btnExit;

    private boolean waiting;

    private boolean exitingLauncher = false;

    private AButton btnDone;

    private ASlickLabel lblManualMode;

    private JPanel pnlTitle;

    public AuroraLauncher(AuroraCoreUI ui) {
        this.coreUI = ui;
        loadUI();
    }

    private void loadUI() {

        // Create Frame Containing Launch UI
        launchPane = new JFrame("Aurora Launcher");
        launchPane.setSize(coreUI.getFrame().getWidth(), coreUI.getFrame()
                           .getHeight());
        launchPane.setBackground(Color.BLACK);
        launchPane.setResizable(false);
        launchPane.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        launchPane.setUndecorated(true);
        launchPane.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        launchPane.addWindowListener(new WindowAdapter() {
            private int count;

            @Override
            public void windowClosing(WindowEvent e) {
                logger.info("AuroraLauncher Exiting");

                manualMode = false;

                if (!exitingLauncher) {
                    gameExited(false);
                }

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

        // Create Panels
        pnlBackground = new AImagePane("app_launch_bg.png", launchPane
                                       .getWidth(),
                                       launchPane.getHeight(),
                                       new BorderLayout());

        pnlTop = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        pnlTop.setOpaque(false);

        pnlTopContainer = new JPanel();
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

        // Create Content Components
        lblGameName = new ASlickLabel();
        lblGameName.setForeground(new Color(103, 103, 103));

        lblPlayedInfo = new ASlickLabel("You Played");
        lblPlayedInfo
                .setFont(coreUI.getRegularFont().deriveFont(Font.PLAIN, 60));
        lblPlayedInfo.setForeground(new Color(45, 59, 75));

        lblPlayedTime = new ASlickLabel();
        lblPlayedTime
                .setFont(coreUI.getRegularFont().deriveFont(Font.PLAIN, 80));
        lblPlayedTime.setForeground(Color.white);

        // Shortcut Buttons
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

        pnlShortcuts = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));
        pnlShortcuts.setOpaque(false);

        imgGameCover = new AImagePane();

        // Create Top Components

        // Title Container for lblManualMode and imgTitle
        pnlTitle = new JPanel(new BorderLayout(0, 5));
        pnlTitle.setOpaque(false);
        pnlTitle.setLayout(new BoxLayout(pnlTitle, BoxLayout.Y_AXIS));

        // Manual Mode Label
        lblManualMode = new ASlickLabel("Manual Mode");
        lblManualMode.setFont(coreUI.getRopaFont().deriveFont(Font.PLAIN, 22));
        lblManualMode.setForeground(Color.ORANGE);
        lblManualMode.setHorizontalAlignment(SwingConstants.CENTER);
        lblManualMode.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        // Title Image Showing Status of Launcing Process
        imgTitle = new AImage("launch_NowLaunching_img.png");
        imgTitle.setHorizontalAlignment(SwingConstants.CENTER);
        imgTitle.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        // Top Buttons for Title
        btnMinimize = new AButton("launch_minimize_norm.png",
                                  "launch_minimize_down.png",
                                  "launch_minimize_over.png");
        btnMinimize.setBorder(null);
        btnMinimize.setMargin(new Insets(0, 0, 0, 0));
        btnMinimize.setEnabled(true);

        btnExit = new AButton("launch_exit_norm.png",
                              "launch_exit_down.png",
                              "launch_exit_over.png");
        btnExit.setBorder(null);
        btnExit.setMargin(new Insets(0, 0, 0, 0));
        btnExit.setEnabled(true);

        btnDone = new AButton("launch_done_norm.png",
                              "launch_done_down.png",
                              "launch_done_over.png");
        btnDone.setBorder(null);
        btnDone.setMargin(new Insets(0, 0, 0, 0));
        btnDone.setEnabled(true);
        btnDone.addActionListener(new GoBackButtonListener());


        // Panel containing imgTitle and topButtons
        pnlTopTitle = new AImagePane("launch_status_bg.png",
                                     new BorderLayout(0, -4));

        // Top Button panels
        pnlButtonContainer
        = new JPanel(new FlowLayout(FlowLayout.CENTER, -1, 0));
        pnlButtonContainer.setOpaque(false);



    }

    private void buildUI() {

        // Fonts
        // --------------------------------------------------------------------.
        lblGameName.setText("  " + game.getGameName());
        // Gracefull Resizing Based on Length of Game Name
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


        // Add Components to Panels
        // --------------------------------------------------------------------.
        // Top Panel
        btnExit.addActionListener(new ExitGameListener());
        btnMinimize.addActionListener(new MinimizeListener());

        pnlButtonContainer.add(btnExit);
        pnlButtonContainer.add(btnMinimize);

        pnlTitle.add(Box.createVerticalStrut(30), BorderLayout.CENTER);
        pnlTitle.add(imgTitle, BorderLayout.CENTER);

        pnlTopTitle.add(pnlTitle, BorderLayout.CENTER);
        pnlTopTitle.add(pnlButtonContainer, BorderLayout.SOUTH);
        pnlTopTitle.setPreferredSize(pnlTopTitle.getRealImageSize());

        pnlTopContainer.add(pnlTopTitle);
        pnlTopContainer.add(Box.createVerticalStrut(coreUI.getScreenHeight()
                                                    / 12));



        // Set Game Cover Image
        imgGameCover.setImage(game.getCoverImagePane());
        imgGameCover.setImageHeight(launchPane.getHeight() / 3 + 80);
        imgGameCover.setImageWidth(imgGameCover.getImageHeight()
                                   - imgGameCover.getImageHeight() / 15);
        imgGameCover.setPreferredSize(new Dimension(imgGameCover.getImageWidth()
                                                    + 20, imgGameCover
                                                    .getImageHeight()));
        imgGameCover.setAlignmentY(JComponent.CENTER_ALIGNMENT);
        imgGameCover.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        // Center Content Panel
        pnlCenter.add(imgGameCover);

        pnlTimePlayed.setBounds(launchPane.getWidth() + 300, pnlCenter
                                .getLocation().y / 2, pnlTimePlayed.getWidth(),
                                pnlTimePlayed.getHeight());

        pnlTimePlayed.add(lblPlayedInfo);
        pnlTimePlayed.add(lblPlayedTime);
        lblPlayedTime.setText("                                              ");

        pnlCenter.add(pnlTimePlayed);

        // Bottom Panel
        pnlShortcuts.add(btnWatch);
        pnlShortcuts.add(btnFix);
        pnlShortcuts.add(btnLearn);

        lblGameName.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlShortcuts.setAlignmentX(Component.CENTER_ALIGNMENT);

        pnlBottom.add(lblGameName);
        pnlBottom.add(Box.createVerticalStrut(20));
        pnlBottom.add(pnlShortcuts);
        pnlBottom.add(Box.createVerticalStrut(20));

        pnlTop.add(pnlTopContainer);

        // Add All To Background Panel
        pnlBackground.add(BorderLayout.NORTH, pnlTop);
        pnlBackground.add(BorderLayout.CENTER, pnlCenter);
        pnlBackground.add(BorderLayout.SOUTH, pnlBottom);

        // Add Background to Frame and set Visible
        launchPane.add(pnlBackground);
        launchPane.setVisible(true);
        launchPane.repaint();

        pnlTimePlayed.setVisible(false);
        launchPane.setAlwaysOnTop(true);
        launchPane.requestFocusInWindow();
        launchPane.setAlwaysOnTop(false);


        coreUI.getInputController().stashCurrentListeners();
        coreUI.getInputController().clearAllListeners();
    }

    public void launchGame(Game game) {

        this.game = game;

        buildUI();

        launcherThread = null;

        if (launcherThread == null) {
            launcherThread = new Thread(this);
        }
        launcherThread.setName("Launch Game Thread");
        // start run()
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

                    // Check if using .exe as executable
                    if (game.getGamePath().endsWith("exe")) {

                        // Get the directory
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
                                                   "\\") + 1)
                                .replace("\\", "\\")));

                        // Launch Game
                        launchGameProcess(processBuild);

                        // If not .exe then its a shortcut (.ink or .url)
                    } else if (game.getGamePath().endsWith("bat")) {

                        // Get the directory
                        String currentDir = new File(game.getGamePath())
                                .getCanonicalPath();
                        currentDir = currentDir.substring(0, currentDir
                                                          .lastIndexOf("\\") + 1)
                                     + '"' + currentDir
                                .substring(currentDir.lastIndexOf("\\") + 1,
                                           currentDir.length()) + '"';
                        if (logger.isDebugEnabled()) {
                            logger.debug("Shortcut Directory: " + currentDir);
                        }

                        // Set Commands to launch shortcut
                        Process processBuild = Runtime.getRuntime().exec(
                                "cmd /c start " + currentDir);
                        launchGameProcess(processBuild);
                    } else {

                        // Get the directory
                        String currentDir = new File(game.getGamePath())
                                .getCanonicalPath();
                        currentDir = currentDir.substring(0, currentDir
                                                          .lastIndexOf("\\") + 1)
                                     + '"' + currentDir
                                .substring(currentDir.lastIndexOf("\\") + 1,
                                           currentDir.length()) + '"';
                        if (logger.isDebugEnabled()) {
                            logger.debug("Shortcut Directory: " + currentDir);
                        }

                        // Set Commands to launch shortcut
                        ProcessBuilder processBuild = new ProcessBuilder();
                        processBuild.command("cmd", "/c", "", '"' + currentDir
                                                              + '"');

                        // Launch Game
                        launchGameProcess(processBuild);

                    }

                    // Launch Game For Mac OS X
                    // --------------------------------------------------------.
                } else if (coreUI.getOS().contains("Mac OS X") || debug) {

                    // escaping the spaces in the game path
                    String workingDir = game.getGamePath().replace(" ", "\\ ");
                    workingDir = workingDir.substring(0, workingDir.lastIndexOf(
                                                      "/") + 1);

                    // Set Commands to launch shortcut
                    ProcessBuilder processBuild = new ProcessBuilder();
                    processBuild.command("open", "-W", game.getGamePath());

                    // Launch Game
                    launchGameProcess(processBuild);

                }
            } catch (IOException ex) {

                // Handle Exeption Unable to Find or Launch Game
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
            // Calculate Time
            calculateTimePlayed();

            // Decide whether the game has trully quit
            if (manualMode) {

                showManualTimerUI();

                break;
            } else {
                // Change Title

                launchPane.setState(JFrame.NORMAL);
                launchPane.setAlwaysOnTop(true);
                showTimeSpentPlaying();

                break;
            }

        }

    }

    private void gameExited(Boolean overideManual) {

        // Game Has Exited
        // ----------------------------------------------------------------.
        // Calculate Time
        if (timeAfter == null) {

            if (timeStarted == null) {
                timeStarted = new DateTime();
            }

            calculateTimePlayed();
        }
        // Decide whether the game has trully quit
        if (manualMode && !overideManual) {

            showManualTimerUI();

        } else {

            // Change Title
            exitingLauncher = true;


            launchPane.setState(JFrame.NORMAL);

            showTimeSpentPlaying();

        }

    }

    public void showManualTimerUI() {

        pnlButtonContainer.remove(btnExit);
        pnlButtonContainer.add(btnDone, 0);

        pnlTitle.add(lblManualMode, BorderLayout.SOUTH);
        pnlTitle.revalidate();
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

        // Pause A Bit To Let Game Start
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            logger.error(ex);

        }
        if (!exitingLauncher) {
            timeStarted = new DateTime();

            // Tracker Data
            game.setLastPlayed(ATimeLabel.current(ATimeLabel.DATE));
            game.setOcurrencesPlayed(game.getOccurencesPlayed() + 1);

            // Change Title to "Playing..."
            imgTitle.setImage("launch_Playing_img.png");

            // Allow for Alt-Tabing  while playing Game
            launchPane.setAlwaysOnTop(false);

            // Wait For Game To Exit
            try {
                waiting = true;
                launchGameProcess.waitFor();

            } catch (Exception ex) {
                logger.error(ex);
                ex.printStackTrace();
            }

            waiting = false;
        }
    }

    private void launchGameProcess(Process process) {

        coreUI.getFrame().setState(JFrame.ICONIFIED);
        coreUI.getFrame().setVisible(false);

        // Allow for Alt-Tabing  while playing Game
        launchPane.setAlwaysOnTop(false);

        // Pause A Bit To Let Game Start
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            logger.error(ex);

        }

        timeStarted = new DateTime();

        // Tracker Data
        game.setLastPlayed(ATimeLabel.current(ATimeLabel.DATE));
        game.setOcurrencesPlayed(game.getOccurencesPlayed() + 1);

        // Change Title to "Playing..."
        imgTitle.setImage("launch_Playing_img.png");



        // Wait For Game To Exit
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

        timeAfter = new DateTime();
        logger.info(game.getLastPlayed());
        logger.info(timeAfter);

        Period period = new Period(timeStarted, timeAfter);

        // Elapsed Time Calculation
        int hoursDiff = period.getHours();
        int minDiff = period.getMinutes();

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

        pnlButtonContainer.setVisible(false);
        launchPane.requestFocusInWindow();

        AThreadWorker showTime = new AThreadWorker(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                pnlTitle.remove(0);
                pnlTitle
                        .add(Box.createVerticalStrut(45), BorderLayout.CENTER, 0);
                imgTitle.setImage("launch_StandBy_img.png");

                pnlTimePlayed.setVisible(true);
                pnlTimePlayed.repaint();
                pnlTimePlayed.setBounds(launchPane.getWidth() + 300, pnlCenter
                                        .getLocation().y / 2, pnlTimePlayed
                                        .getWidth(),
                                        pnlTimePlayed
                                        .getHeight());
                imgGameCover.setLocation(imgGameCover.getLocation());
                pnlCenter.setLayout(null);
                imgGameCover.setLocation(imgGameCover.getLocation());

                imgGameCover.revalidate();
                pnlCenter.revalidate();

                pnlTopTitle.setIgnoreRepaint(true);

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

        // Save Metadata Then Go Back To Aurora
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
        coreUI.getInputController().revertToStashedListeners();
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

            if (!exitingLauncher) {

                if (waiting) {
                    launchGameProcess.destroy();
                }

                launchPane.setState(JFrame.NORMAL);

                manualMode = false;

                gameExited(true);

            }
        }
    }

    private class MinimizeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (!exitingLauncher) {
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
