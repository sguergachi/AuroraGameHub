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
import aurora.V1.core.StartLoader;
import aurora.V1.core.screen_handler.StartScreenLogic;
import aurora.V1.core.screen_handler.StartScreenLogic.FrameKeyListener;
import aurora.V1.core.screen_handler.StartScreenLogic.StartListener;
import aurora.engine.V1.Logic.AFileManager;
import aurora.engine.V1.Logic.APostHandler;
import aurora.engine.V1.Logic.ASurface;
import aurora.engine.V1.Logic.ANuance;
import aurora.engine.V1.Logic.AuroraScreenUI;
import aurora.engine.V1.UI.AButton;
import aurora.engine.V1.UI.AProgressWheel;
import aurora.engine.V1.UI.APrompter;
import aurora.engine.V1.UI.AScrollingImage;
import java.awt.BorderLayout;
import java.awt.Color;
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
 * Start Up GUI and logic.
 *
 * @author Sammy
 * @version 0.4
 */
public final class StartScreenUI implements Runnable, AuroraScreenUI {

    private JFrame frame;

    private APrompter Display;

    private AProgressWheel progressWheel;

    private ArrayList<String> ToDisplay;

    private AScrollingImage HexAnimation;

    private AuroraCoreUI ui;

    private ANuance AuroraVI;

    private int SIZE_Display;

    public static boolean Online = false;

    private StartLoader trans = null;

    private String path = "AuroraData";

    private JPanel pnlUserButton;

    private AButton btnGo;

    private JPanel loadingPane;

    private AFileManager FileIO;

    private AuroraStorage auroraStorage;

    private ASurface resource;

    private Thread login;

    private boolean isTransisioning = false;

    public static boolean START_WITH_MINI = false;

    private boolean isLoaded = false;

    private int SIZE_DisplayFont;

    private final StartScreenLogic handler;

    private FrameKeyListener startKeyHandler;

    public StartScreenUI(Boolean startMini) {


        /////////////////
        //Load Components///////////////////////////////////////
        /////////////////
        StartScreenUI.START_WITH_MINI = startMini;

        frame = new JFrame("Aurora Game Manager ~ V1");
        ui = new AuroraCoreUI(this.frame);
        handler = new StartScreenLogic(this);


        try {
            frame.setIconImage(new ImageIcon(new URL(ui.getResource().
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


        progressWheel = new AProgressWheel("Aurora_wheel.png");
        HexAnimation = new AScrollingImage("Aurora_Hex.png", 0, 0);

        try {
            buildGUI();
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(StartScreenUI.class.getName()).log(Level.SEVERE,
                    null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StartScreenUI.class.getName()).log(Level.SEVERE,
                    null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(StartScreenUI.class.getName()).log(Level.SEVERE,
                    null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(StartScreenUI.class.getName()).log(Level.SEVERE,
                    null, ex);
        } catch (FontFormatException ex) {
            Logger.getLogger(StartScreenUI.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

    }

    public void buildGUI() throws UnsupportedAudioFileException, IOException,
                                  LineUnavailableException, InterruptedException,
                                  FontFormatException {



        /////////////////
        //Setup UI///////////////////////////////////////
        ////////////////

        ui.setUI();
        ui.setSFX();
        setSizes();


        //Option to start with AuroraMini
        if (START_WITH_MINI) {
            ui.getMinimizeHandler().setArg(AuroraMini.LOADING_MODE);
            ui.minimizeAurora(AuroraMini.LOADING_MODE);
        }

        /////////////////////
        //Play Background Sound//////////////////////////
        /////////////////////


        //TODO add Background Sound


        ///////////////////////
        // The Center Panel Contains Hex Animation
        // and maintains a space between top and bottom panel
        ///////////////////////

        pnlUserButton = new JPanel();
        pnlUserButton.setOpaque(false);


        btnGo = new AButton("Aurora_User_normal.png", "Aurora_User_down.png",
                "Aurora_User_over.png");
        btnGo.addActionListener(handler.new StartListener());
        btnGo.setVisible(false);

        // add button
        pnlUserButton.add(BorderLayout.CENTER, btnGo);

        ui.getCenterPanel().add(BorderLayout.CENTER, HexAnimation);


        //Add AuroraScreenUI to Frame
        frame.getContentPane().add(ui.getBackgroundImagePane());

        frame.setVisible(true);



        //Set Up Prompter and Animation

        HexAnimation.setIgnoreRepaint(true);
        HexAnimation.setDoubleBuffered(true);
        HexAnimation.StartLoop();

        //Add Escape Key Listener to frame
        this.startKeyHandler = handler.new FrameKeyListener();
        frame.addKeyListener(startKeyHandler);
        frame.requestFocus();

        startLogin();
    }

    /////Login Sequence
    public void startLogin() throws IOException {
        //Start Button
        FileIO = new AFileManager("");

        ToDisplay = getDisplay();

        loadingPane = new JPanel();
        loadingPane.setLayout(new BorderLayout());
        loadingPane.setOpaque(false);
        loadingPane.add(BorderLayout.SOUTH, progressWheel);


        Display = new APrompter(new Color(0, 127, 153), ui.getBoldFont()
                .deriveFont(Font.PLAIN, SIZE_DisplayFont));
        Display.add(ToDisplay);
        Display.setUp(SIZE_Display, Display.getWidth());

        Display
                .setPreferredSize(
                new Dimension(SIZE_Display, Display.getWidth()));

        Display.revalidate();
        Display.setIgnoreRepaint(true);
        Display.addPost(new APostHandler() {
            @Override
            public void actionPerformed() {
                doneLogin();
            }
        });

        loadingPane.add(BorderLayout.CENTER, Display);
        loadingPane.revalidate();

        ui.getUserSpacePanel().add(loadingPane);

        ui.getUserSpacePanel().revalidate();
        ui.getUserSpacePanel().repaint();

        login = null;
        login = new Thread(this);

        login.setName("Login Thread");

        //Start Loader
        login.start();

    }

    public void doneLogin() {
        //add button panel to UI
        ui.getUserSpacePanel().removeAll();


//        btnGo.setVisible(true);

        StartListener start = handler.new StartListener();
        start.actionPerformed(null);
        ui.getUserSpacePanel().add(pnlUserButton);
        ui.getUserSpacePanel().revalidate();
        //Notify Ready.
        isLoaded = true;
        if (START_WITH_MINI) {
            ui.getMiniMode().setMode(AuroraMini.MINIMIZE_MODE);
        }
    }

    public ArrayList<String> getDisplay() {
        AuroraVI = new ANuance();
        if (checkUser()) {
            ArrayList<String> LoginDisplay = new ArrayList<String>();

            LoginDisplay.add(AuroraVI.VI(ANuance.inx_Greeting) + " " + FileIO.
                    getUserName());
            LoginDisplay.add(AuroraVI.VI(ANuance.inx_WelcomeBack));
            return LoginDisplay;
        } else {
            ArrayList<String> LoginDisplay = new ArrayList<String>();

            LoginDisplay.add(AuroraVI.VI(ANuance.inx_Greeting) + " " + FileIO.
                    getUserName());

            LoginDisplay
                    .add(
                    AuroraVI.VI(ANuance.inx_Welcome) + " to Aurora Game Manager");

            LoginDisplay.add(
                    AuroraVI.VI(ANuance.inx_Preparing) + " for First Time Use");

            LoginDisplay.add(AuroraVI.VI(ANuance.inx_Please) + " Wait...");
            return LoginDisplay;
        }
    }

    @Override
    public void run() {

        while (Thread.currentThread() == login) {
            loadingPane.revalidate();
            loadingPane.repaint();
            auroraStorage = new AuroraStorage();
            Boolean FirstTimeLoad = false;

            //Check if Main Folder "AuroraData" Exists
            if (!checkMainDir()) {

                FirstTimeLoad = true;
                FileIO.createFolder("AuroraData");
                FileIO.setPath(FileIO.getPath() + path);
                FileIO.createFolder("User Data");
                FileIO.createFolder("Game Data");
                //Load Databases
                auroraStorage.getStoredLibrary().setUpDatabase(FirstTimeLoad,
                        FileIO.getPath() + "/Game Data/");
                auroraStorage.getStoredProfile().setUpDatabase(FirstTimeLoad,
                        FileIO.getPath() + "/User Data/");
                auroraStorage.getStoredSettings().setUpDatabase(FirstTimeLoad,
                        FileIO.getPath() + "/User Data/");

                Display.add("Created New Profile");

                //Check if both Sub folders Exist
            } else if (!checkSubDir()) {
                FirstTimeLoad = true;
                Display.add("Unable To Find Subfolders");
                Display.add("Attempting to Create New Ones...");
                FileIO.createFolder("User Data");
                FileIO.createFolder("Game Data");

                //Load Databases
                auroraStorage.getStoredLibrary().setUpDatabase(FirstTimeLoad,
                        FileIO.getPath() + "/Game Data/");
                auroraStorage.getStoredProfile().setUpDatabase(FirstTimeLoad,
                        FileIO.getPath() + "/User Data/");
                auroraStorage.getStoredSettings().setUpDatabase(FirstTimeLoad,
                        FileIO.getPath() + "/User Data/");

            } else if (!checkDBFiles()) {
                FirstTimeLoad = true;
                Display.add("Unable To Find a Data Files");
                Display.add("Attempting to Create New Ones...");
                //Load Databases
                auroraStorage.getStoredLibrary().setUpDatabase(FirstTimeLoad,
                        FileIO.getPath() + "/Game Data/");
                auroraStorage.getStoredProfile().setUpDatabase(FirstTimeLoad,
                        FileIO.getPath() + "/User Data/");
                auroraStorage.getStoredSettings().setUpDatabase(FirstTimeLoad,
                        FileIO.getPath() + "/User Data/");
            } else {
                FileIO.setPath(FileIO.getPath() + path);
            }
            if (!FirstTimeLoad && START_WITH_MINI) {
                ui.minimizeAurora(AuroraMini.LOADING_MODE);
            }

            //Check if Online
            if (!checkOnline("auroragm.sourceforge.net")) {
                Online = false;
                Display.add("I Can't Connect To AuroraDB, Let Me Try Again...",
                        Color.RED);

                //Check if its the Users fault or the aurora severs are down
                if (checkOnline("google.com") && !checkOnline(
                        "auroragm.sourceforge.net")) {
                    Display
                            .add(
                            "Well, It Seems Our Servers Are Down, Try Again In A Bit.",
                            Color.RED);

                    //The User is having internet problems
                } else if (!checkOnline("google.com")) {
                    Display.add("Can't Connect To Google...");
                    Display
                            .add(
                            "Either The Universe Exploded OR You Don't Have Internet...",
                            Color.RED);
                    Display.add("Running In Offline Mode...");
                } else {
                    Display
                            .add(
                            "I Seem To Have Finnally Esstablished Connection...");
                }
            } else {
                Online = true;

            }


            if (!FirstTimeLoad) {
                //Load Databases
                auroraStorage.getStoredLibrary().setUpDatabase(FirstTimeLoad,
                        FileIO.getPath() + "/Game Data/");
                auroraStorage.getStoredLibrary().storeFromDatabase();
                auroraStorage.getStoredProfile().setUpDatabase(FirstTimeLoad,
                        FileIO.getPath() + "/User Data/");
                auroraStorage.getStoredProfile().storeFromDatabase();
                auroraStorage.getStoredSettings().setUpDatabase(FirstTimeLoad,
                        FileIO.getPath() + "/User Data/");
                auroraStorage.getStoredSettings().storeFromDatabase();
            }

            Display.add("Loading Complete", new Color(0, 191, 255));


            break;
        }

    }

    public void setSizes() {

        if (ui.isLargeScreen()) {
            SIZE_Display = 20;
            SIZE_DisplayFont = ui.getBottomPanelSize() / 10;
        } else {
            SIZE_Display = 10;
            SIZE_DisplayFont = ui.getBottomPanelSize() / 10;

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
            Logger.getLogger(StartScreenUI.class.getName()).log(Level.SEVERE,
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

        if (FileIO.checkFile(FileIO.getPath() + path)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkSubDir() {
        if (FileIO.checkFile(FileIO.getPath() + path + "/User Data") && FileIO.
                checkFile(FileIO.getPath() + path + "/Game Data")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkDBFiles() {
        if (FileIO.checkFile(FileIO.getPath() + path + "/User Data/User.h2.db")
            && FileIO.
                checkFile(FileIO.getPath() + path + "/Game Data/Games.h2.db")) {
            return true;
        } else {
            return false;
        }
    }

    public void startTransision() {
        isTransisioning = true;
        trans = new StartLoader(ui, this);
        trans.transitionToMain();
        ui.getUserSpacePanel().setVisible(false);
    }

    public FrameKeyListener getStartKeyHandler() {
        return startKeyHandler;
    }

    public AFileManager getFileIO() {
        return FileIO;
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

    public AScrollingImage getHexAnimation() {
        return HexAnimation;
    }

    public void setHexAnimation(AScrollingImage HexAnimation) {
        this.HexAnimation = HexAnimation;
    }

    public AuroraStorage getAuroraStorage() {
        return auroraStorage;
    }

    @Override
    public void loadUI() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void buildUI() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addToCanvas() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
