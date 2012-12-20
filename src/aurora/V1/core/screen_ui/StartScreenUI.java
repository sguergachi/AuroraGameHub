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
package aurora.V1.core.screen_ui;

import aurora.V1.core.AuroraCoreUI;
import aurora.V1.core.AuroraMini;
import aurora.V1.core.AuroraStorage;
import aurora.V1.core.screen_handler.StartScreenHandler;
import aurora.V1.core.screen_handler.StartScreenHandler;
import aurora.V1.core.screen_handler.StartScreenHandler.FrameKeyListener;
import aurora.V1.core.screen_logic.StartScreenLogic;
import aurora.engine.V1.Logic.AFileManager;
import aurora.engine.V1.Logic.ANuance;
import aurora.engine.V1.Logic.APostHandler;
import aurora.engine.V1.Logic.ASurface;
import aurora.engine.V1.Logic.AuroraScreenUI;
import aurora.engine.V1.UI.AButton;
import aurora.engine.V1.UI.AImage;
import aurora.engine.V1.UI.AProgressWheel;
import aurora.engine.V1.UI.APrompter;
import aurora.engine.V1.UI.AScrollingImage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * .------------------------------------------------------------------------.
 * | StartScreenUI :: Aurora App Class
 * .------------------------------------------------------------------------.
 * |
 * | This class contains the UI for the Start Screen associated with an
 * | appropriate *Handler* and *Logic* class which handle the actions caused
 * | by the UI components found here
 * |
 * | This class must follow the rules stated in the AuroraScreenUI
 * | Interface found in the Aurora Engine. The *Handler* and *Logic* classes
 * | The Handler class is called: StartScreenHandler
 * | The Logic class is called: StartScreenLogic
 * |
 * .........................................................................
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 * @author Carlos Machado <camachado@gmail.com>
 *
 */
public final class StartScreenUI implements Runnable, AuroraScreenUI {

    private JFrame frame;

    private APrompter promptDisplay;

    private AProgressWheel progressWheel;

    private ArrayList<String> promptList;

    private AScrollingImage imgHexPane;

    private AuroraCoreUI coreUI;

    private ANuance auroraVI;

    private int displayYpos;

    public static boolean Online = false;

    private String path = "AuroraData";

    private JPanel pnlUserButton;

    private JPanel loadingPane;

    private AFileManager fileIO;

    private AuroraStorage auroraStorage;

    private ASurface resource;

    private Thread backgroundLoadThread;

    private boolean isTransisioning = false;

    public static boolean START_WITH_MINI = false;

    private boolean isLoaded = false;

    private int displayFontSize;

    private final StartScreenHandler handler;

    private FrameKeyListener startKeyHandler;

    private AImage cursorImage;

    private Object toolkit;

    private final StartScreenLogic logic;

    private DashboardUI loadedDashboardUI;

    private boolean dashboardLoaded;

    private boolean loadedData;

    public StartScreenUI(Boolean startMini) {

        StartScreenUI.START_WITH_MINI = startMini;
        frame = new JFrame("Aurora Game Manager ~ V1");
        coreUI = new AuroraCoreUI(this.frame);
        handler = new StartScreenHandler(this);
        logic = new StartScreenLogic(this);
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
                Logger.getLogger(StartScreenUI.class.getName()).
                        log(Level.SEVERE, null, exx);
            }
        }

        try {
            coreUI.setUI();
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(StartScreenUI.class.getName()).
                    log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StartScreenUI.class.getName()).
                    log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(StartScreenUI.class.getName()).
                    log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(StartScreenUI.class.getName()).
                    log(Level.SEVERE, null, ex);
        } catch (FontFormatException ex) {
            Logger.getLogger(StartScreenUI.class.getName()).
                    log(Level.SEVERE, null, ex);
        }

        try {
            coreUI.setSFX();
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(StartScreenUI.class.getName()).
                    log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StartScreenUI.class.getName()).
                    log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(StartScreenUI.class.getName()).
                    log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(StartScreenUI.class.getName()).
                    log(Level.SEVERE, null, ex);
        }

        progressWheel = new AProgressWheel("app_progressWheel.png");
        imgHexPane = new AScrollingImage("start_scrolling_hex.png", 0, 0);
        imgHexPane.setIgnoreRepaint(true);
        imgHexPane.setDoubleBuffered(true);

        fileIO = new AFileManager("");
        loadingPane = new JPanel();
        loadingPane.setLayout(new BorderLayout());
        loadingPane.setOpaque(false);

        this.startKeyHandler = handler.new FrameKeyListener();

        buildUI();

    }

    @Override
    public void buildUI() {

        // Setup UI
        // --------------------------------------------------------------------.

        setSizes();

        logic.startBackgroundMusic();

        //* Option to start with AuroraMini *//
        if (START_WITH_MINI) {
            coreUI.getMinimizeHandler().setArg(AuroraMini.LOADING_MODE);
            coreUI.minimizeAurora(AuroraMini.LOADING_MODE);
        }


        //*
        // The setCenterToFrame Panel Contains Hex Animation
        // and maintains a space between top and bottom panel
        //*

        coreUI.getCenterPanel().add(BorderLayout.CENTER, imgHexPane);


        //* Set Up Prompter and Scrolling Animation *//
        imgHexPane.StartLoop();


        //* Add Escape Key Listener to frame *//
        frame.addKeyListener(startKeyHandler);

        //* set up prompter *//
        promptDisplay = new APrompter(new Color(0, 127, 153), coreUI
                .getBoldFont()
                .deriveFont(Font.PLAIN, displayFontSize));
        promptList = generatePrompts();

        promptDisplay.add(promptList);
        promptDisplay.setUp(displayYpos, promptDisplay.getWidth());

        promptDisplay.setPreferredSize(
                new Dimension(displayYpos, promptDisplay.getWidth()));

        promptDisplay.revalidate();
        promptDisplay.setIgnoreRepaint(true);
        promptDisplay.addPost(new APostHandler() {
            @Override
            public void actionPerformed() {
                completedStartUp();
            }
        });

        //* panel containing progress wheel and prompter *//
        loadingPane.add(BorderLayout.CENTER, promptDisplay);
        loadingPane.add(BorderLayout.SOUTH, progressWheel);
        loadingPane.revalidate();

        coreUI.getBottomContentPane().add(loadingPane);

        coreUI.getBottomContentPane().revalidate();
        coreUI.getBottomContentPane().repaint();

        //* prep Aurora for dashboard *//
        try {
            backgroundLoad();
        } catch (IOException ex) {
            Logger.getLogger(StartScreenUI.class.getName()).
                    log(Level.SEVERE, null, ex);
        }

        //* Add Background containing CoreUI to Frame *//
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
        coreUI.getBottomContentPane().revalidate();

        startTransision();

        //Notify Ready.
        isLoaded = true;
        if (START_WITH_MINI) {
            coreUI.getMiniMode().setMode(AuroraMini.MINIMIZE_MODE);
        }



    }

    public ArrayList<String> generatePrompts() {
        auroraVI = new ANuance();
        ArrayList<String> toDisplayList = new ArrayList<String>();
        if (checkUser()) {


            toDisplayList.add(auroraVI.VI(ANuance.inx_Greeting) + " " + fileIO.
                    getUserName());
            toDisplayList.add(auroraVI.VI(ANuance.inx_WelcomeBack));
            return toDisplayList;
        } else {

            toDisplayList.add(auroraVI.VI(ANuance.inx_Greeting) + " " + fileIO.
                    getUserName());

            toDisplayList
                    .add(
                    auroraVI.VI(ANuance.inx_Welcome) + " to Aurora Game Manager");

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

        //* Start background loader *//
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

                //* Check if Main Folder "AuroraData" Exists *//
                if (!checkMainDir()) {

                    FirstTimeLoad = true;
                    fileIO.createFolder("AuroraData");
                    fileIO.setPath(fileIO.getPath() + path);
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

                    //* Check if both Sub folders Exist *//
                } else if (!checkSubDir()) {
                    FirstTimeLoad = true;
                    promptDisplay.add("Unable To Find Subfolders");
                    promptDisplay.add("Attempting to Create New Ones...");
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

                } else if (!checkDBFiles()) {
                    FirstTimeLoad = true;
                    promptDisplay.add("Unable To Find a Data Files");
                    promptDisplay.add("Attempting to Create New Ones...");
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
                } else {
                    fileIO.setPath(fileIO.getPath() + path);
                }
                if (!FirstTimeLoad && START_WITH_MINI) {
                    coreUI.minimizeAurora(AuroraMini.LOADING_MODE);
                }

                //* Check if Online *//
                if (!checkOnline("auroragm.sourceforge.net")) {
                    Online = false;
                    promptDisplay.add(
                            "I Can't Connect To AuroraDB, Let Me Try Again...",
                            Color.RED);

                    //*
                    // Check if its Users Internet is down
                    // or Aurora Servers are down
                    //*
                    if (checkOnline("google.com") && !checkOnline(
                            "auroragm.sourceforge.net")) {
                        promptDisplay
                                .add(
                                "Well, It Seems Our Servers Are Down, Try Again In A Bit.",
                                Color.RED);

                        //The User is having internet problems
                    } else if (!checkOnline("google.com")) {
                        promptDisplay.add("Can't Connect To Google...");
                        promptDisplay
                                .add(
                                "Either The Universe Exploded OR You Don't Have Internet...",
                                Color.RED);
                        promptDisplay.add("Running In Offline Mode...");
                    } else {
                        promptDisplay
                                .add(
                                "I Seem To Have Finnally Esstablished Connection...");
                    }
                } else {
                    Online = true;

                }


                if (!FirstTimeLoad) {
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


                //* load DashboardUI *//
                loadedDashboardUI = new DashboardUI(coreUI, this);
                System.out.println("Loading Dashboard...");
                loadedDashboardUI.loadUI();

                loadedData = true;


            }

            dashboardLoaded = loadedDashboardUI.isDashboardUiLoaded();


            if (dashboardLoaded) {

                promptDisplay.add("Loading Complete", new Color(0, 191, 255));
                System.out.println("Loading COMPLETED!!");

                try {
                    Thread.sleep(16);
                } catch (InterruptedException ex) {
                    Logger.getLogger(StartScreenUI.class.getName()).
                            log(Level.SEVERE, null, ex);
                }

                promptDisplay.done();
                break;
            } else {
                promptDisplay.add("Loading...", new Color(0, 191, 255));
                System.out.println(">> Still Loading Dashboard...");
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
            displayFontSize = coreUI.getBottomPanelSize() / 10;
        } else {
            displayYpos = 10;
            displayFontSize = coreUI.getBottomPanelSize() / 10;

        }

    }

    private boolean checkOnline(String URL) {
        final URL url;
        try {
            url = new URL("http://" + URL);
            try {

                final URLConnection conn = url.openConnection();
                conn.connect();
            } catch (IOException ex) {
                System.out.println("Computer is NOT online");
                return false;



            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(StartScreenUI.class
                    .getName()).log(Level.SEVERE,
                    null, ex);

        }

        System.out.println("Computer is Online");
        return true;
    }

    private boolean checkUser() {
        if (checkMainDir() && checkSubDir() && checkDBFiles()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkMainDir() {

        if (fileIO.checkFile(fileIO.getPath() + path)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkSubDir() {
        if (fileIO.checkFile(fileIO.getPath() + path + "/User Data") && fileIO.
                checkFile(fileIO.getPath() + path + "/Game Data")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkDBFiles() {
        if (fileIO.checkFile(fileIO.getPath() + path + "/User Data/User.h2.db")
            && fileIO.
                checkFile(fileIO.getPath() + path + "/Game Data/Games.h2.db")) {
            return true;
        } else {
            return false;
        }
    }

    public FrameKeyListener getStartKeyHandler() {
        return startKeyHandler;
    }

    public AFileManager getFileIO() {
        return fileIO;
    }

    public static boolean isOnline() {
        return Online;
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
}
