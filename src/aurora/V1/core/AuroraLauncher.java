/*
 * Copyright 2012 Sardonix Creative.
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
import aurora.engine.V1.UI.ADialog;
import aurora.engine.V1.UI.AImage;
import aurora.engine.V1.UI.AImagePane;
import aurora.engine.V1.UI.AProgressWheel;
import aurora.engine.V1.UI.ASlickLabel;
import aurora.engine.V1.UI.ATimeLabel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class AuroraLauncher implements Runnable {

    private Game game;

    private JFrame launchPane;

    private AuroraCoreUI coreUI;

    private AImagePane pnlBackground;

    private JPanel pnlTop;

    private JLabel lblGameName;

    private AImagePane imgGameCover;

    private AImagePane imgTitle;

    private JPanel pnlCenter;

    private JPanel pnlBottom;

    private Thread launcherThread;

    private String timeAfter;

    private int minDiff;

    private Process launchGameProcess;

    private int hoursDiff;

    private int elapsedTime;

    private ASlickLabel lblPlayedInfo;

    private ASlickLabel lblPlayedTime;

    private boolean manualMode;

    private JPanel pnlTimePlayed;

    private boolean debug = true;

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
        launchPane.setAlwaysOnTop(true);


        //* Create Panels *//
        pnlBackground = new AImagePane("app_launch_bg.png", launchPane
                .getWidth(),
                launchPane.getHeight(), new BorderLayout());

        pnlTop = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 50));
        pnlTop.setOpaque(false);


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

        //* Title Image Showing Status of Launcing Process *//
        imgTitle = new AImagePane("app_launch_nowLaunching.png");

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
        pnlTop.add(imgTitle);
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
        launcherThread.setName("Launch Thread");
        //Start Loader
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

                        //* Set Commands to launch shortcut *//
                        ProcessBuilder processBuild = new ProcessBuilder();
                        processBuild.command("cmd", "/c", "", currentDir);


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

//                    System.out.println("Mac Work Dir " + workingDir);
//                    System.out.println("Game path " + game.getGamePath());

                    //* Set Commands to launch shortcut *//
                    ProcessBuilder processBuild = new ProcessBuilder();
                    processBuild.command("open", "-W", game.getGamePath());


                    //* Launch Game *//
                    launchGameProcess(processBuild);

//                    System.setProperty("user.dir", workingDir);
//
//                    //* Launch Game *//
//                    Desktop.getDesktop().open(new File(game.getGamePath()));

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

            timeAfter = ATimeLabel.current(ATimeLabel.TIME_24HOUR);
            System.out.println(game.getLastPlayed());
            System.out.println(timeAfter);

            //* Elapsed Time Calculation *//
            hoursDiff = Math.abs(Integer.parseInt(timeAfter.substring(0, 2))
                                 - Integer.parseInt(game.getLastPlayed()
                    .substring(0, 2))) * 60;
            minDiff = Math.abs(Integer.parseInt(timeAfter.substring(3, 5))
                               - Integer.parseInt(game.getLastPlayed()
                    .substring(3, 5)));
            //ELAPSED TIME IN MIN IS ((HOURS*60) - MIN FROM TIME1) + MIN FROM TIME2
            elapsedTime = Math.abs((hoursDiff - Integer.parseInt(timeAfter
                    .substring(3, 5))) + Integer.parseInt(game.getLastPlayed()
                    .substring(3, 5)));

            hoursDiff = elapsedTime / 60;
            minDiff = elapsedTime - (hoursDiff * 60);

            System.out.println("Elapsed " + elapsedTime);
            System.out.println("Hours " + hoursDiff);
            System.out.println("Min " + minDiff);

            if (minDiff < 1 && hoursDiff < 1) {
                lblPlayedTime.setText("Under 1 min  ");
                manualMode = true;
            } else if (hoursDiff < 1) {
                lblPlayedTime.setText(minDiff + " min  ");
            } else {
                lblPlayedTime.setText(hoursDiff + "hr and "
                                      + minDiff + "min  ");
            }

            if (manualMode) {

                showManualTimerUI();

                try {
                    Thread.sleep(4000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(AuroraLauncher.class.getName()).log(
                            Level.SEVERE, null, ex);
                }


                break;
            } else {
                //* Change Title *//
                imgTitle.setImage("app_launch_standBy.png");

                //* Start Music Again *//
                coreUI.getBackgroundSound().Resume();

                launchPane.setAlwaysOnTop(true);

                showTimeSpentPlaying();

                //* Wait a bit before returning to Aurora *//
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(AuroraLauncher.class.getName()).log(
                            Level.SEVERE, null, ex);
                }


                break;
            }


        }

        launchPane.setVisible(false);
        launchPane.dispose();
        coreUI.getFrame().setVisible(true);
        coreUI.getFrame().setState(JFrame.NORMAL);
        manualMode = false;

        pnlCenter.setLayout(new FlowLayout(FlowLayout.CENTER));
    }

    public void showManualTimerUI() {

        imgTitle.setImage("app_launch_standBy.png");
        //* Start Music Again *//
        coreUI.getBackgroundSound().Resume();

        showTimeSpentPlaying();
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

    private void showTimeSpentPlaying() {



        pnlTimePlayed.setVisible(true);
        pnlTimePlayed.repaint();
        pnlTimePlayed.setBounds(launchPane.getWidth() + 300, pnlCenter
                .getLocation().y / 2, pnlTimePlayed.getWidth(), pnlTimePlayed
                .getHeight());

        pnlCenter.setLayout(null);


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


    }
}
