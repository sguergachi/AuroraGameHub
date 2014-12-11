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

import aurora.V1.core.AuroraCoreUI;
import aurora.V1.core.AuroraMini;
import aurora.V1.core.AuroraStorage;
import aurora.V1.core.main;
import aurora.V1.core.screen_handler.WelcomeHandler;
import aurora.V1.core.screen_handler.WelcomeHandler.FrameKeyListener;
import aurora.V1.core.screen_logic.WelcomeLogic;
import aurora.engine.V1.Logic.AFileManager;
import aurora.engine.V1.Logic.ANuance;
import aurora.engine.V1.Logic.APostHandler;
import aurora.engine.V1.Logic.AuroraScreenUI;
import aurora.engine.V1.UI.AProgressWheel;
import aurora.engine.V1.UI.APrompter;
import aurora.engine.V1.UI.AScrollingImage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.apache.log4j.Logger;

/**
 * .------------------------------------------------------------------------. |
 * WelcomeUI :: Aurora App Class
 * .------------------------------------------------------------------------. |
 * | This class contains the UI for the Start Screen associated with an |
 * appropriate *Handler* and *Logic* class which handle the actions caused | by
 * the UI components found here | | This class must follow the rules stated in
 * the AuroraScreenUI | Interface found in the Aurora Engine. The *Handler* and
 * *Logic* classes | The Handler class is called: WelcomeHandler | The Logic
 * class is called: WelcomeLogic |
 * .........................................................................
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 * @author Carlos Machado <camachado@gmail.com>
 *
 */
public final class WelcomeUI implements Runnable, AuroraScreenUI {

    private JFrame frame;

    private APrompter promptDisplay;

    private AProgressWheel progressWheel;

    private ArrayList<String> promptList;

    private AScrollingImage imgHexPane;

    private AuroraCoreUI coreUI;

    private ANuance auroraVI;

    private int displayYpos;

    public static boolean isOnline = false;

    private JPanel loadingPane;

    private AFileManager fileIO;

    private AuroraStorage auroraStorage;

    private Thread backgroundLoadThread;

    private boolean isTransisioning = false;

    public static boolean START_WITH_MINI = false;

    private boolean isLoaded = false;

    private int displayFontSize;

    private final WelcomeHandler handler;

    private FrameKeyListener startKeyHandler;

    private final WelcomeLogic logic;

    private DashboardUI loadedDashboardUI;

    private boolean dashboardLoaded;

    private boolean loadedData;

    static final Logger logger = Logger.getLogger(WelcomeUI.class);

    public WelcomeUI(Boolean startMini) {

        WelcomeUI.START_WITH_MINI = startMini;
        frame = new JFrame("Aurora Game Manager ~ V1");
        coreUI = new AuroraCoreUI(this.frame);
        handler = new WelcomeHandler(this);
        logic = new WelcomeLogic(this);
        handler.setLogic(logic);
        logic.setHandler(handler);

    }

    @Override
    public void loadUI() {

        // Load Components
        // --------------------------------------------------------------------.
        try {
            frame.setIconImage(new ImageIcon(new URL(coreUI.getResource().
                    getSurfacePath() + "/aurora/V1/resources/icon.png")).
                    getImage());
        } catch (MalformedURLException ex) {
            try {

                frame.setIconImage(new ImageIcon(getClass().getResource(
                        "/aurora/V1/resources/icon.png")).getImage());

            } catch (Exception exx) {
                logger.error(exx);
            }
        }

        try {
            coreUI.setUI();
        } catch (UnsupportedAudioFileException ex) {
            logger.error(ex);
        } catch (IOException ex) {
            logger.error(ex);
        } catch (LineUnavailableException ex) {
            logger.error(ex);
        } catch (InterruptedException ex) {
            logger.error(ex);
        } catch (FontFormatException ex) {
            logger.error(ex);
        }

        progressWheel = new AProgressWheel("app_progressWheel.png", 20);
        imgHexPane = new AScrollingImage("start_scrolling_hex.png", 0, 0);
        imgHexPane.setIgnoreRepaint(true);
        imgHexPane.setDoubleBuffered(true);

        fileIO = new AFileManager(System.getProperty("user.home"));
        logic.setFileIO(fileIO);
        loadingPane = new JPanel();
        loadingPane.setLayout(new BorderLayout(0, 0));
        loadingPane.setOpaque(false);

        this.startKeyHandler = handler.new FrameKeyListener();

        buildUI();

    }

    @Override
    public void buildUI() {

        // Setup UI
        // --------------------------------------------------------------------.
        setSizes();

        // Option to start with AuroraMini
        if (START_WITH_MINI) {
            coreUI.getMinimizeHandler().setArg(AuroraMini.LOADING_MODE);
            coreUI.minimizeAurora(AuroraMini.LOADING_MODE);
        }

        //
        // The setCenterToFrame Panel Contains Hex Animation
        // and maintains a space between top and bottom panel
        //
        coreUI.getCenterPanel().add(BorderLayout.CENTER, imgHexPane);

        // Set Up Prompter and Scrolling Animation
        imgHexPane.StartLoop(coreUI.getCenterPanel().getPreferredSize().height);

        // Add Escape Key Listener to frame
        frame.addKeyListener(startKeyHandler);

        // set up prompter
        promptDisplay = new APrompter(new Color(0, 127, 153), coreUI
                                      .getBoldFont()
                                      .deriveFont(Font.PLAIN, displayFontSize));
        promptList = generatePrompts();

        promptDisplay.add(promptList);
        promptDisplay.setUp(displayYpos, promptDisplay.getWidth());

        promptDisplay.revalidate();
        promptDisplay.setIgnoreRepaint(true);
        promptDisplay.addPost(new APostHandler() {
            @Override
            public void doAction() {
                completedStartUp();
            }
        });

        // panel containing progress wheel and prompter
        JPanel progressContainer = new JPanel(new FlowLayout(FlowLayout.CENTER,
                                                             0,
                                                             0));
        progressContainer.setOpaque(false);
        progressContainer.add(progressWheel);
        loadingPane.add(BorderLayout.CENTER, promptDisplay);
        loadingPane.add(BorderLayout.SOUTH, progressContainer);
        loadingPane.revalidate();

        coreUI.getBottomContentPane().add(loadingPane);

        coreUI.getBottomContentPane().revalidate();

        // prep Aurora for dashboard
        try {
            backgroundLoad();
        } catch (IOException ex) {
            logger.error(ex);
        }

        // Add Background containing CoreUI to Frame
        frame.getContentPane().add(coreUI.getBackgroundImagePane());
        frame.setVisible(true);

    }

    @Override
    public void addToCanvas() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void completedStartUp() {
        //add button panel to UI
        coreUI.getBottomContentPane().removeAll();


        startTransision();

        coreUI.getBottomContentPane().revalidate();

        //Notify Ready.
        isLoaded = true;
        if (START_WITH_MINI) {
            coreUI.getMiniMode().setMode(AuroraMini.MINIMIZE_MODE);
        }

    }

    public ArrayList<String> generatePrompts() {

        if (logger.isDebugEnabled()) {
            logger.debug(fileIO.getPath() + main.DATA_PATH + "/User Data");
        }

        auroraVI = new ANuance(System.getProperty("user.home")
                               + "/AuroraData/User Data/AIDictionary.txt");

        ArrayList<String> toDisplayList = new ArrayList<String>();
        if (logic.checkUser()) {

            toDisplayList.add(auroraVI.VI(ANuance.inx_Greeting) + " " + fileIO.
                    getUserName());
            toDisplayList.add(auroraVI.VI(ANuance.inx_WelcomeBack));
            return toDisplayList;
        } else {

            toDisplayList.add(auroraVI.VI(ANuance.inx_Greeting) + " " + fileIO.
                    getUserName());

            toDisplayList
                    .add(
                            auroraVI.VI(ANuance.inx_Welcome)
                            + " to Aurora Game Hub");

            toDisplayList.add(
                    auroraVI.VI(ANuance.inx_Preparing) + " for First Time Use");

            toDisplayList.add(auroraVI.VI(ANuance.inx_Please) + " Wait...");
            return toDisplayList;
        }
    }

    public void startTransision() {
        isTransisioning = true;
        logic.transisionToDashboard();
        coreUI.getBottomContentPane().setVisible(false);
    }

    public void backgroundLoad() throws IOException {

        backgroundLoadThread = null;
        backgroundLoadThread = new Thread(this);

        backgroundLoadThread.setName("Startup Thread");

        // Start background loader
        backgroundLoadThread.start();

    }

    @Override
    public void run() {

        while (Thread.currentThread() == backgroundLoadThread) {

            if (!loadedData) {
                loadingPane.revalidate();
                loadingPane.repaint();
                auroraStorage = new AuroraStorage();
                Boolean FirstTimeLoad = false;

                logic.checkAuroraCoverDB(promptDisplay);


                // Check if Main Folder "AuroraData" Exists //
                if (!logic.checkMainDir()) {

                    FirstTimeLoad = true;
                    fileIO.createFolder("AuroraData");
                    fileIO.setPath(fileIO.getPath() + main.DATA_PATH);
                    fileIO.createFolder("User Data");
                    fileIO.createFolder("Game Data");

                    //Load Databases
                    auroraStorage.getStoredLibrary()
                            .setUpDatabase(FirstTimeLoad,
                                           fileIO.getPath() + "/Game Data/");
                    auroraStorage.getStoredProfile()
                            .setUpDatabase(FirstTimeLoad,
                                           fileIO.getPath() + "/User Data/");
                    auroraStorage.getStoredSettings().setUpDatabase(
                            FirstTimeLoad,
                            fileIO.getPath() + "/User Data/");

                    promptDisplay.add("Created New Profile");

                    // Check if both Sub folders Exist
                } else if (!logic.checkSubDir()) {
                    FirstTimeLoad = true;
                    promptDisplay.add("Attempting to Create Subfolders...");
                    fileIO.setPath(fileIO.getPath() + main.DATA_PATH);
                    fileIO.createFolder("User Data");
                    fileIO.createFolder("Game Data");

                    //Load Databases
                    auroraStorage.getStoredLibrary()
                            .setUpDatabase(FirstTimeLoad,
                                           fileIO.getPath() + "/Game Data/");
                    auroraStorage.getStoredProfile()
                            .setUpDatabase(FirstTimeLoad,
                                           fileIO.getPath() + "/User Data/");
                    auroraStorage.getStoredSettings().setUpDatabase(
                            FirstTimeLoad,
                            fileIO.getPath() + "/User Data/");

                } else if (!logic.checkDBFiles()) {
                    FirstTimeLoad = false;
                    fileIO.setPath(fileIO.getPath() + main.DATA_PATH);

                    if (!logic.checkDBFiles()) {
                        FirstTimeLoad = true;
                        promptDisplay.add("Creating Missing Database Files...");

                        //Load Databases
                        auroraStorage.getStoredLibrary()
                                .setUpDatabase(!fileIO.checkFile(fileIO.getPath() + "/Game Data/Game.h2.db"),
                                               fileIO.getPath() + "/Game Data/");
                        auroraStorage.getStoredProfile()
                                .setUpDatabase(!fileIO.checkFile(fileIO.getPath() + "/User Data/User.h2.db"),
                                               fileIO.getPath() + "/User Data/");
                        auroraStorage.getStoredSettings().setUpDatabase(
                                !fileIO.checkFile(fileIO.getPath() + "/User Data/User.h2.db"),
                                fileIO.getPath() + "/User Data/");

                    }
                } else {
                    fileIO.setPath(fileIO.getPath() + main.DATA_PATH);
                }

                if (!FirstTimeLoad && START_WITH_MINI) {
                    coreUI.minimizeAurora(AuroraMini.LOADING_MODE);
                }

//                // Check if isOnline
//                if (!logic.checkOnline("http://aws.amazon.com")) {
//                    isOnline = false;
//                    promptDisplay.add(
//                            "Can't Connect To AuroraDB, Let Me Try Again...",
//                            Color.RED);
//
//                    //
//                    // Check if its Users Internet is down
//                    // or Aurora Servers are down
//                    //
//                    if (logic.checkOnline("http://google.com") && !logic
//                            .checkOnline(
//                                    "https://s3.amazonaws.com")) {
//                        promptDisplay
//                                .add(
//                                        "Well, It Seems Our Servers Are Down, Try Again In A Bit.",
//                                        Color.RED);
//
//                        //The User is having internet problems
//                    } else if (!logic.checkOnline("http://google.com")) {
//                        promptDisplay.add("Can't Connect To Google...");
//                        promptDisplay
//                                .add(
//                                        "Either The Universe Exploded OR You Don't Have Internet...",
//                                        Color.RED);
//                        promptDisplay.add("Running In Offline Mode...");
//                    } else {
//                        promptDisplay
//                                .add(
//                                        "I Seem To Have Finally Established Connection...");
//                        isOnline = true;
//                    }
//                } else {
//                    isOnline = true;
//
//                }

                logic.sendAnalytics();

                if (!FirstTimeLoad) {

                    logger.info("loading Databases");

                    //Load Databases
                    auroraStorage.getStoredLibrary()
                            .setUpDatabase(FirstTimeLoad,
                                           fileIO.getPath() + "/Game Data/");
                    auroraStorage.getStoredLibrary().storeFromDatabase();

                    auroraStorage.getStoredProfile()
                            .setUpDatabase(FirstTimeLoad,
                                           fileIO.getPath() + "/User Data/");
                    auroraStorage.getStoredProfile().storeFromDatabase();

                    auroraStorage.getStoredSettings().setUpDatabase(
                            FirstTimeLoad,
                            fileIO.getPath() + "/User Data/");
                    auroraStorage.getStoredSettings().storeFromDatabase();

                }

                // Increment number of times launched//
                logic.incrementAuroraLaunch();

                try {
                    // Update AIDictionary periodically

                    if (main.LAUNCHES == 0 || main.LAUNCHES % 10 == 0) {
                        logger.info("downloading AIDictionary...");
                        coreUI.setVi(new ANuance(
                                "https://s3.amazonaws.com/AuroraStorage/AIDictionary.txt",
                                System.getProperty("user.home") + "/"
                                + "AuroraData/User Data/AIDictionary.txt"));
                    }

                    if (main.LAUNCHES < 5) {
                        coreUI.getBtnExit().setToolTipText("Exit");
                        coreUI.getBtnMinimize().setToolTipText("Minimize");
                    }
                } catch (IOException ex) {
                    java.util.logging.Logger
                            .getLogger(WelcomeUI.class.getName()).log(
                                    Level.SEVERE, null, ex);
                }

                // load DashboardUI
                loadedDashboardUI = new DashboardUI(coreUI, this);

                if (logger.isDebugEnabled()) {
                    logger.debug("Loading Dashboard...");
                }

                promptDisplay
                        .add("Loading Dashboard...", new Color(0, 191, 255));
                loadedDashboardUI.loadUI();

                loadedData = true;

            }

            dashboardLoaded = loadedDashboardUI.isDashboardUiLoaded();

            if (dashboardLoaded) {

                promptDisplay.add("Loading Complete", new Color(0, 191, 255));

                if (logger.isDebugEnabled()) {
                    logger.debug("Loading COMPLETED!!");
                }

                try {
                    Thread.sleep(16);
                } catch (InterruptedException ex) {
                    logger.error(ex);
                }

                promptDisplay.done();
                break;
            } else {
                promptDisplay.add("Loading...", new Color(0, 191, 255));

                if (logger.isDebugEnabled()) {
                    logger.debug(">> Still Loading Dashboard...");
                }
            }
        }

    }

    public DashboardUI getLoadedDashboardUI() {
        if (dashboardLoaded) {
            return loadedDashboardUI;
        } else {
            return null;
        }
    }

    public void setSizes() {

        if (coreUI.isLargeScreen()) {
            displayYpos = 20;
            displayFontSize = coreUI.getBottomPanelSize() / 11;
        } else {
            displayYpos = 10;
            displayFontSize = coreUI.getBottomPanelSize() / 12;

        }

    }

    public FrameKeyListener getStartKeyHandler() {
        return startKeyHandler;
    }

    public AFileManager getFileIO() {
        return fileIO;
    }

    public static boolean isOnline() {
        return isOnline;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public boolean isTransisioning() {
        return isTransisioning;
    }

    public AScrollingImage getImgHexPane() {
        return imgHexPane;
    }

    public AuroraStorage getAuroraStorage() {
        return auroraStorage;
    }

    public AuroraCoreUI getCoreUI() {
        return coreUI;
    }

    public WelcomeHandler getHandler() {
        return handler;
    }

    public WelcomeLogic getLogic() {
        return logic;
    }
}
