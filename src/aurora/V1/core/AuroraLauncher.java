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

import aurora.engine.V1.UI.ADialog;
import aurora.engine.V1.UI.AImage;
import aurora.engine.V1.UI.AImagePane;
import aurora.engine.V1.UI.AProgressWheel;
import aurora.engine.V1.UI.ATimeLabel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private JDialog launchPane;

    private AuroraCoreUI ui;

    private AImagePane pnlBackground;

    private JPanel pnlTop;

    private JPanel pnlCenter;

    private JLabel lblTitle;

    private JLabel lblGameName;

    private AImage imageRightSide;

    private AImage imageLeftSide;

    private AImagePane gameIcon;

    private AImagePane titleBG;

    private AProgressWheel progressWheel;

    private AImagePane progressWheelBG;

    private JPanel pnlCenterContent;

    private JPanel pnlBottom;

    private Thread launcherThread;

    private JPanel pnlTopCenter;

    private String timeAfter;

    private int minDiff;

    private Process Process;

    private int hoursDiff;

    private int elapsedTime;

    public AuroraLauncher(Game game, AuroraCoreUI ui) {
        this.ui = ui;
        this.game = game;


    }

    public void createUI() {
        launchPane = new JDialog();
        launchPane.setSize(ui.getFrame().getWidth(), ui.getFrame().getHeight());
        launchPane.setBackground(Color.BLACK);
        launchPane.setResizable(false);
        launchPane.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        launchPane.setUndecorated(true);
        launchPane.setAlwaysOnTop(true);



        //Create components
        pnlBackground = new AImagePane("LaunchBG.png", launchPane.getWidth(),
                launchPane.getHeight(), new BorderLayout());
        pnlTop = new JPanel(new BorderLayout(0, 20));
        pnlTop.setOpaque(false);
        pnlTopCenter = new JPanel();
        pnlTopCenter.setOpaque(false);
        pnlCenter = new JPanel();
        pnlCenter.setOpaque(false);
        pnlCenterContent = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlCenterContent.setOpaque(false);
        pnlBottom = new JPanel();
        pnlBottom.setOpaque(false);

        lblTitle = new JLabel("Now Launching");
        lblGameName = new JLabel(game.getGameName());

        imageRightSide = new AImage("rightBrace.png");
        imageLeftSide = new AImage("leftBrace.png");
        gameIcon = new AImagePane();
        gameIcon.setImage(game.getImgIcon(), 260, 230);
        gameIcon.setPreferredSize(new Dimension(230, 270));
        titleBG = new AImagePane("launchTitle.png");
        progressWheel = new AProgressWheel("ProgressWheel.png");
        progressWheelBG = new AImagePane("ProgressWheelBG.png",
                new BorderLayout(0, 0));
        progressWheelBG.setPreferredSize(new Dimension(progressWheelBG
                .getImgIcon().getIconWidth(), progressWheelBG.getImgIcon()
                .getIconHeight()));

        //Config Component
        progressWheelBG.add(progressWheel);
        lblTitle.setFont(ui.getRegularFont().deriveFont(Font.PLAIN, 95));
        lblTitle.setForeground(Color.green);

        //Gracefull Resizing
        if (lblGameName.getText().length() > 50) {
            lblGameName.setFont(ui.getRegularFont().deriveFont(Font.PLAIN, 50));
        } else if (lblGameName.getText().length() > 40) {
            lblGameName.setFont(ui.getRegularFont().deriveFont(Font.PLAIN, 60));
        } else if (lblGameName.getText().length() > 30) {
            lblGameName.setFont(ui.getRegularFont().deriveFont(Font.PLAIN, 70));
        } else {
            lblGameName.setFont(ui.getRegularFont().deriveFont(Font.PLAIN, 95));
        }
        lblGameName.setForeground(Color.lightGray);
        pnlTopCenter.add(titleBG);

        pnlCenterContent.add(lblGameName);
        pnlCenterContent.add(gameIcon);


        pnlCenter.setPreferredSize(new Dimension(launchPane.getWidth(), 28));
        pnlCenter.add(imageLeftSide);
        pnlCenter.add(pnlCenterContent);
        pnlCenter.add(imageRightSide);

        titleBG.setPreferredSize(new Dimension(765, 300));
        pnlTopCenter.setPreferredSize(new Dimension(launchPane.getWidth(), 300));
        pnlTop.add(BorderLayout.CENTER, pnlTopCenter);

        pnlBottom.add(progressWheelBG);

        pnlBackground.add(BorderLayout.NORTH, pnlTopCenter);
        pnlBackground.add(BorderLayout.CENTER, pnlCenter);
        pnlBackground.add(BorderLayout.SOUTH, pnlBottom);


        launchPane.add(pnlBackground);
        launchPane.setVisible(true);
        launchPane.repaint();

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


            try {
                String osName = System.getProperty("os.name");
                if (ui.getOS().equals("Windows 7") || osName
                        .equals("Windows XP") || ui.getOS().equals(
                        "Windows Vista")) {


                    System.out.println(game.getGamePath());
                    if (game.getGamePath().endsWith("exe")) {
                        //Get the directory
                        ProcessBuilder processBuild = new ProcessBuilder(game
                                .getGamePath());

                        processBuild.directory(new File(game.getGamePath()
                                .substring(0, game.getGamePath().lastIndexOf(
                                "\\") + 1).replace("\\", "\\")));

                        //LAUCH GAME
                        Process = processBuild.start();
                        ui.getFrame().setState(JFrame.ICONIFIED);



                        //Pause A Bit
                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(AuroraLauncher.class.getName())
                                    .log(Level.SEVERE, null, ex);
                        }

                        //Game Cover Tracker Data
                        game.setNumberTimesPlayed(game.getNumberTimesPlayed()
                                                  + 1);
                        game.setLastPlayed(ATimeLabel.current(
                                ATimeLabel.TIME_24HOUR));
                        //UI Changes
                        progressWheel.setClockwise(false);
                        progressWheel.setSpeed(6);
                        titleBG.setImage("playTitle.png");


                        //WAIT FOR GAME TO EXIT
                        try {
                            launchPane.setAlwaysOnTop(false);
                            Process.waitFor();

                        } catch (InterruptedException ex) {
                            Logger.getLogger(AuroraLauncher.class.getName())
                                    .log(Level.SEVERE, null, ex);
                        }
                    } else { // Launch Shortcuts
                        launchPane.setAlwaysOnTop(false);
                        String currentDir = new File(game.getGamePath())
                                .getCanonicalPath();
                        currentDir = currentDir.substring(0, currentDir
                                .lastIndexOf("\\") + 1) + '"' + currentDir
                                .substring(currentDir.lastIndexOf("\\") + 1,
                                currentDir.length()) + '"';

                        ProcessBuilder processBuild = new ProcessBuilder();
                        processBuild.command("cmd", "/c", "", currentDir);

                        System.out.println("Current Dir " + currentDir);
                        System.out.println("Command " + processBuild.command());


                        //LAUNCH GAME
                        Process = processBuild.start();

                        ui.getFrame().setState(JFrame.ICONIFIED);

                        //Pause A Bit
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(AuroraLauncher.class.getName())
                                    .log(Level.SEVERE, null, ex);
                        }
                        //Game Cover Tracker Data
                        game.setNumberTimesPlayed(game.getNumberTimesPlayed()
                                                  + 1);
                        game.setLastPlayed(ATimeLabel.current(
                                ATimeLabel.TIME_24HOUR));
                        //UI Changes
                        progressWheel.setClockwise(false);
                        progressWheel.setSpeed(5);
                        titleBG.setImage("playTitle.png");


                        //WAIT FOR GAME TO EXIT
                        try {
                            //launchPane.setAlwaysOnTop(false);
                            Process.waitFor();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(AuroraLauncher.class.getName())
                                    .log(Level.SEVERE, null, ex);
                        }
                    }


                } else if (osName.equals("Mac OS X")) {
                    // escaping the spaces in the game path
                    game.getGamePath().replace(" ", "\\ ");
                    String workingDir = game.getGamePath();
                    workingDir = workingDir.substring(0, workingDir.lastIndexOf(
                            "/") + 1);
                    System.setProperty("user.dir", workingDir);
                    //LAUCH GAME
                    Desktop.getDesktop().open(new File(game.getGamePath()));

                    //Tracker Data
                    game.setNumberTimesPlayed(game.getNumberTimesPlayed() + 1);
                    game.setLastPlayed(ui.getTimeLabel().getText());
                }
            } catch (IOException ex) {
                ADialog error = new ADialog(ADialog.aDIALOG_ERROR,
                        "Unable to find game.");
                error.setOKButtonListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        launchPane.setVisible(false);
                        launchPane.dispose();
                        ui.getFrame().setVisible(true);
                        ui.getFrame().setState(JFrame.NORMAL);

                    }
                });
                error.setVisible(true);
                Logger.getLogger(AuroraLauncher.class.getName()).log(
                        Level.SEVERE, null, ex);
            }

            //Game Has Exited//
            titleBG.setImage("ComputingData.png");


            launchPane.setAlwaysOnTop(true);

            timeAfter = ATimeLabel.current(ATimeLabel.TIME_24HOUR);
            System.out.println(game.getLastPlayed());
            System.out.println(timeAfter);

            //Elapsed Time Calculation
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

            lblGameName.setFont(ui.getRegularFont().deriveFont(Font.PLAIN, 95));
            if (minDiff < 1 && hoursDiff < 1) {
                lblGameName.setText("You Played: under 1 min  ");
            } else if (hoursDiff < 1) {
                lblGameName.setText("You Played: " + minDiff + " min  ");
            } else {
                lblGameName.setText("You Played: " + hoursDiff + "hr and "
                                    + minDiff + "min  ");
            }

            progressWheel.setClockwise(true);
            progressWheel.setSpeed(8);

            //Wait a bit before returning to Aurora
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(AuroraLauncher.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
            break;
        }

        launchPane.setVisible(false);
        launchPane.dispose();
        ui.getFrame().setVisible(true);
        ui.getFrame().setState(JFrame.NORMAL);
    }
}
