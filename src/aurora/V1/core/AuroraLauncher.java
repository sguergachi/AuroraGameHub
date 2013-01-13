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

import aurora.V1.core.screen_ui.StartScreenUI;
import aurora.engine.V1.Logic.AAnimate;
import aurora.engine.V1.Logic.AThreadWorker;
import aurora.engine.V1.UI.AButton;
import aurora.engine.V1.UI.ADialog;
import aurora.engine.V1.UI.AImage;
import aurora.engine.V1.UI.AImagePane;
import aurora.engine.V1.UI.AProgressWheel;
import aurora.engine.V1.UI.ASlickLabel;
import aurora.engine.V1.UI.ATimeLabel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

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

    private String timeAfter;

    private Process launchGameProcess;

    private ASlickLabel lblPlayedInfo;

    private ASlickLabel lblPlayedTime;

    private boolean manualMode;

    private JPanel pnlTimePlayed;

    private boolean debug = true;

    private AImagePane imgManualMode;

    private AButton btnReturnToAurora;

    public AuroraLauncher(AuroraCoreUI ui) {
        this.coreUI = ui;
        loadUI();
    }

    private void loadUI() {

        //* Create Frame Containing Launch UI *//
        launchPane = new JFrame();
        launchPane.setSize(coreUI.getFrame().getWidth(), coreUI.getFrame()
                .getHeight());
        launchPane.setBackground(Color.BLACK);
        launchPane.setResizable(false);
        launchPane.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        launchPane.setUndecorated(true);


        launchPane.addWindowFocusListener(new LaunchFrameFocusListener());


        try {
            launchPane.setIconImage(new ImageIcon(new URL(coreUI.getResource().
                    getSurfacePath() + "/aurora/V1/resources/icon.png")).
                    getImage());
        } catch (MalformedURLException ex) {
            try {

                launchPane.setIconImage(new ImageIcon(getClass().getResource(
                        "/aurora/V1/resources/icon.png")).getImage());

            } catch (Exception exx) {
                Logger.getLogger(StartScreenUI.class.getName()).
                        log(Level.SEVERE, null, exx);
            }
        }


        //* Create Panels *//
        pnlBackground = new AImagePane("app_launch_bg.png", launchPane
                .getWidth(),
                launchPane.getHeight(), new BorderLayout());

        pnlTop = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 50));
        pnlTop.setOpaque(false);

        pnlTopContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 50));
        pnlTopContainer.setOpaque(false);
        pnlTopContainer.setLayout(new BoxLayout(pnlTopContainer,
                BoxLayout.Y_AXIS));


        pnlCenter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlCenter.setOpaque(false);

        pnlBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 50));
        pnlBottom.setOpaque(false);

        pnlTimePlayed = new JPanel();
        pnlTimePlayed.setOpaque(false);
        pnlTimePlayed.setLayout(new BoxLayout(pnlTimePlayed, BoxLayout.Y_AXIS));


        //* Create Content Components *//

        lblGameName = new ASlickLabel();

        lblPlayedInfo = new ASlickLabel("You Played");

        lblPlayedTime = new ASlickLabel();

        //* Game Cover Icon representing Game *//
        imgGameCover = new AImagePane();

        //* Manual Mode Label Image *//
        imgManualMode = new AImagePane("app_launch_manualMode.png");
        imgManualMode.setPreferredSize(new Dimension(imgManualMode.getImgIcon()
                .getIconWidth(), imgManualMode.getImgIcon().getIconHeight()));

        //* Manual return to Aurora button *//
        btnReturnToAurora = new AButton("app_launch_goBack_norm.png",
                "app_launch_goBack_down.png", "app_launch_goBack_norm.png");
        btnReturnToAurora.addActionListener(new GoBackButtonListener());

        //* Title Image Showing Status of Launcing Process *//
        imgTitle = new AImagePane("app_launch_nowLaunching.png");

        launchPane.requestFocusInWindow();

    }

    private void buildUI() {

        // Fonts
        // --------------------------------------------------------------------.

        lblGameName.setText("  " + game.getGameName());
        //* Gracefull Resizing Based on Length of Game Name *//
        if (lblGameName.getText().length() > 50) {
            lblGameName.setFont(coreUI.getRegularFont().deriveFont(Font.PLAIN,
                    55));
        } else if (lblGameName.getText().length() > 40) {
            lblGameName.setFont(coreUI.getRegularFont().deriveFont(Font.PLAIN,
                    65));
        } else if (lblGameName.getText().length() > 30) {
            lblGameName.setFont(coreUI.getRegularFont().deriveFont(Font.PLAIN,
                    75));
        } else {
            lblGameName.setFont(coreUI.getRegularFont().deriveFont(Font.PLAIN,
                    100));
        }
        lblGameName.setForeground(new Color(103, 103, 103));

        lblPlayedTime
                .setFont(coreUI.getRegularFont().deriveFont(Font.PLAIN, 80));
        lblPlayedTime.setForeground(Color.white);

        lblPlayedInfo
                .setFont(coreUI.getRegularFont().deriveFont(Font.PLAIN, 60));
        lblPlayedInfo.setForeground(new Color(42, 51, 69));



        // Add Components to Panels
        // --------------------------------------------------------------------.

        //* Top Panel *//
        pnlTopContainer.add(imgTitle);
        imgTitle.setPreferredSize(new Dimension(imgTitle.getImgIcon()
                .getIconWidth(), imgTitle.getImgIcon().getIconHeight()));



        //* Set Game Cover Image *//
        imgGameCover.setImage(game);
        imgGameCover.setImageHeight(launchPane.getHeight() / 3 + 80);
        imgGameCover.setImageWidth((int) (imgGameCover.getImageHeight()
                                          - imgGameCover.getImageHeight() / 15));
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
        pnlBottom.add(lblGameName);



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
        launcherThread.start();
    }

    @Override
    public void run() {
        while (Thread.currentThread() == launcherThread) {


            //* Stop Music *//
            coreUI.getBackgroundSound().Pause();


            try {


                // Launch Game For Windows
                // ------------------------------------------------------------.
                if (coreUI.getOS().contains("Windows")) {

                    //* Check if using .exe as executable *//
                    if (game.getGamePath().endsWith("exe")) {


                        //* Get the directory *//
                        ProcessBuilder processBuild = new ProcessBuilder(game
                                .getGamePath());

                        System.out.println("EXE Directory: " + game
                                .getGamePath()
                                .substring(0, game.getGamePath().lastIndexOf(
                                "\\") + 1).replace("\\", "\\"));

                        processBuild.directory(new File(game.getGamePath()
                                .substring(0, game.getGamePath().lastIndexOf(
                                "\\") + 1).replace("\\", "\\")));


                        //* Launch Game *//

                        launchGameProcess(processBuild);



                        //* If not .exe then its a shortcut *//
                    } else {

                        //* Get the directory *//
                        String currentDir = new File(game.getGamePath())
                                .getCanonicalPath();
                        currentDir = currentDir.substring(0, currentDir
                                .lastIndexOf("\\") + 1) + '"' + currentDir
                                .substring(currentDir.lastIndexOf("\\") + 1,
                                currentDir.length()) + '"';
                        System.out.println("Shortcut Directory: " + currentDir);


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


                    //* Tracker Data *//
                    game.setNumberTimesPlayed(game.getNumberTimesPlayed() + 1);
                    game.setLastPlayed(coreUI.getTimeLabel().getText());

                }
            } catch (IOException ex) {

                //* Handle Exeption Unable to Find or Launch Game *//

                ADialog error = new ADialog(ADialog.aDIALOG_ERROR,
                        "Unable To Find & Launch Game.", coreUI.getBoldFont()
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
                Logger.getLogger(AuroraLauncher.class.getName()).log(
                        Level.SEVERE, null, ex);
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

                //* Start Music Again *//
                coreUI.getBackgroundSound().Resume();

                launchPane.setAlwaysOnTop(true);

                showTimeSpentPlaying();

                break;
            }


        }

        if (!manualMode) {
            goBackToAurora();
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
        } catch (IOException ex) {
            Logger.getLogger(AuroraLauncher.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        coreUI.getFrame().setState(JFrame.ICONIFIED);
        coreUI.getFrame().setVisible(false);

        //* Game Cover Tracker Data *//
        game.setNumberTimesPlayed(game.getNumberTimesPlayed()
                                  + 1);
        game.setLastPlayed(ATimeLabel.current(
                ATimeLabel.TIME_24HOUR));



        //* Pause A Bit To Let Game Start *//
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ex) {
            Logger.getLogger(AuroraLauncher.class.getName())
                    .log(Level.SEVERE, null, ex);
        }


        //* Change Title *//
        imgTitle.setImage("app_launch_playing.png");

        //* Allow for Alt-Tabing  while playing Game *//
        launchPane.setAlwaysOnTop(false);

        //* Wait For Game To Exit *//
        try {
            launchGameProcess.waitFor();
        } catch (InterruptedException ex) {
            Logger.getLogger(AuroraLauncher.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

    }

    private void calculateTimePlayed() {
        timeAfter = ATimeLabel.current(ATimeLabel.TIME_24HOUR);
        System.out.println(game.getLastPlayed());
        System.out.println(timeAfter);

        //* Elapsed Time Calculation *//
        int hoursDiff = Math.abs(Integer.parseInt(timeAfter.substring(0, 2))
                                 - Integer.parseInt(game.getLastPlayed()
                .substring(0, 2))) * 60;
        int minDiff = Math.abs(Integer.parseInt(timeAfter.substring(3, 5))
                               - Integer.parseInt(game.getLastPlayed()
                .substring(3, 5)));
        //ELAPSED TIME IN MIN IS ((HOURS*60) - MIN FROM TIME1) + MIN FROM TIME2
        int elapsedTime = Math.abs((hoursDiff - Integer.parseInt(timeAfter
                .substring(3, 5))) + Integer.parseInt(game.getLastPlayed()
                .substring(3, 5)));

        hoursDiff = elapsedTime / 60;
        minDiff = elapsedTime - (hoursDiff * 60);

        System.out.println("Elapsed " + elapsedTime);
        System.out.println("Hours " + hoursDiff);
        System.out.println("Min " + minDiff);

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

        pnlTimePlayed.setVisible(true);
        pnlTimePlayed.repaint();
        pnlTimePlayed.setBounds(launchPane.getWidth() + 300, pnlCenter
                .getLocation().y / 2, pnlTimePlayed.getWidth(), pnlTimePlayed
                .getHeight());

        pnlCenter.setLayout(null);
        imgGameCover.setLocation(imgGameCover.getLocation());
        pnlCenter.revalidate();


        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(AuroraLauncher.class.getName())
                    .log(Level.SEVERE, null, ex);
        }


        AAnimate animateCover = new AAnimate(imgGameCover);
        AAnimate animateTime = new AAnimate(pnlTimePlayed);


        animateCover
                .setInitialLocation(imgGameCover.getLocation().x, imgGameCover
                .getLocation().y);

        animateTime.setInitialLocation(launchPane.getWidth() + 300, pnlCenter
                .getLocation().y / 2);

        animateCover.moveHorizontal(launchPane.getWidth() / 6, -2);
        animateTime.moveHorizontal(launchPane.getWidth() / 2 - 50, -10);

        launchPane.repaint();
    }

    private void goBackToAurora() {

        //* Wait a bit before returning to Aurora *//
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(AuroraLauncher.class.getName()).log(
                    Level.SEVERE, null, ex);
        }

        launchPane.setVisible(false);
        launchPane.dispose();
        coreUI.getFrame().setVisible(true);
        coreUI.getFrame().setState(JFrame.NORMAL);
        manualMode = false;

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
        System.out.println("Mouse Entered <<");
        launchPane.requestFocusInWindow();
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private class GoBackButtonListener implements ActionListener {

        public GoBackButtonListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {


            AThreadWorker worker = new AThreadWorker(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    pnlTop.removeAll();
                    pnlTop.validate();
                    pnlTop.add(pnlTopContainer);
                    pnlTop.revalidate();
                    imgTitle.setImage("app_launch_standBy.png");
                    //* Start Music Again *//
                    coreUI.getBackgroundSound().Resume();

                    calculateTimePlayed();
                    showTimeSpentPlaying();

                }
            }, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //* After above animation completed *//
                    goBackToAurora();
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
            System.out.println("LAUNCH FRAME FOCUS GAINED");
            if (manualMode) {

                pnlTop.removeAll();
                pnlTop.validate();
                pnlTop.add(btnReturnToAurora);
                pnlTop.revalidate();

            }
        }

        @Override
        public void windowLostFocus(WindowEvent e) {
            System.out.println("LAUNCH FRAME FOCUS LOST");
            if (manualMode) {
                pnlTop.removeAll();
                pnlTop.validate();
                pnlTop.add(pnlTopContainer);
                pnlTop.revalidate();
            }

        }
    }
}
