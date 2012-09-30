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

import aurora.engine.V1.Logic.aFileManager;
import aurora.engine.V1.Logic.aPostHandler;
import aurora.engine.V1.Logic.aResourceManager;
import aurora.engine.V1.Logic.aXAVI;
import aurora.engine.V1.UI.aButton;
import aurora.engine.V1.UI.aProgressWheel;
import aurora.engine.V1.UI.aPrompter;
import aurora.engine.V1.UI.aScrollingImage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
 * Start Up GUI and logic
 *
 * @author Sammy
 * @version 0.4
 */
public final class Aurora_StartUp implements Runnable {

    private JFrame frame;
    private aPrompter Display;
    private aProgressWheel progressWheel;
    private ArrayList<String> ToDisplay;
    private aScrollingImage HexAnimation;
    private AuroraCoreUI ui;
    private aXAVI AuroraVI;
    private int SIZE_Display;
    public static boolean Online = false;
    private StartLoader trans = null;
    private FrameKeyListener startKeyHandler;
    private String path = "AuroraData";
    private JPanel pnlUserButton;
    private aButton btnGo;
    private JPanel loadingPane;
    private aFileManager FileIO;
    private AuroraStorage auroraStorage;
    private aResourceManager resource;
    ///
    private Thread login;
    private boolean isTransisioning = false;
    public static boolean START_WITH_MINI = false;
    private boolean isLoaded = false;
    private int SIZE_DisplayFont;

    public Aurora_StartUp(Boolean startMini) {


        /////////////////
        //Load Components///////////////////////////////////////
        /////////////////
        Aurora_StartUp.START_WITH_MINI = startMini;

        //fast load
        frame = new JFrame("Aurora Game Manager ~ V1");
        ui = new AuroraCoreUI(this.frame);




        try {
            frame.setIconImage(new ImageIcon(new URL(ui.getResource().getSurfacePath() + "/aurora/V1/resources/icon.png")).getImage());
        } catch (MalformedURLException ex) {
            try {

                frame.setIconImage(new ImageIcon(getClass().getResource("/aurora/V1/resources/icon.png")).getImage());

            } catch (Exception exx) {
                Logger.getLogger(Aurora_StartUp.class.getName()).log(Level.SEVERE, null, exx);
            }
        }


        progressWheel = new aProgressWheel("Aurora_wheel.png");
        HexAnimation = new aScrollingImage("Aurora_Hex.png", 0, 0);

        try {
            buildGUI();
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Aurora_StartUp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Aurora_StartUp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Aurora_StartUp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Aurora_StartUp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FontFormatException ex) {
            Logger.getLogger(Aurora_StartUp.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void buildGUI() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException, FontFormatException {



        /////////////////
        //Setup UI///////////////////////////////////////
        ////////////////

        ui.setUI();
        ui.setSFX();
        setSizes();




        //Option to start with AuroraMini
        if (START_WITH_MINI) {
            ui.getMinimizeHandler().setArg(AuroraMini.LOADING_MODE);
            ui.auroraMinimize(AuroraMini.LOADING_MODE);
        }

        /////////////////////
        //Play Background Sound//////////////////////////
        /////////////////////


        //ui.sfxTheme.Play();


        ///////////////////////
        // The Center Panel Contains Hex Animation
        // and maintains a space between top and bottom panel
        ///////////////////////

        pnlUserButton = new JPanel();
        pnlUserButton.setOpaque(false);


        btnGo = new aButton("Aurora_User_normal.png", "Aurora_User_down.png", "Aurora_User_over.png");
        btnGo.addActionListener(new StartListener());
        btnGo.setVisible(false);

        // add button
        pnlUserButton.add(BorderLayout.CENTER, btnGo);

        ui.getPnlCenter().add(BorderLayout.CENTER, HexAnimation);


        //Add AuroraUI to Frame
        frame.getContentPane().add(ui.getPnlBackground());

        frame.setVisible(true);



        //Set Up Prompter and Animation

        HexAnimation.setIgnoreRepaint(true);
        HexAnimation.setDoubleBuffered(true);
        HexAnimation.StartLoop();

        //Add Escape Key Listener to frame
        this.startKeyHandler = new FrameKeyListener();
        frame.addKeyListener(startKeyHandler);
        frame.requestFocus();

        startLogin();
    }

    /////Login Sequence
    public void startLogin() throws IOException {
        //Start Button
        FileIO = new aFileManager("");

        ToDisplay = getDisplay();

        loadingPane = new JPanel();
        loadingPane.setLayout(new BorderLayout());
        loadingPane.setOpaque(false);
        loadingPane.add(BorderLayout.SOUTH, progressWheel);


        Display = new aPrompter(new Color(0, 127, 153), ui.getFontBold().deriveFont(Font.PLAIN,SIZE_DisplayFont));
        Display.add(ToDisplay);
        Display.setUp(SIZE_Display, Display.getWidth());

        Display.setPreferredSize(new Dimension(SIZE_Display, Display.getWidth()));

        Display.revalidate();
        Display.setIgnoreRepaint(true);
        Display.addPost(new aPostHandler() {
            @Override
            public void actionPerformed() {
                doneLogin();
            }
        });

        loadingPane.add(BorderLayout.CENTER, Display);
        loadingPane.revalidate();

        ui.getPnlUserSpace().add(loadingPane);

        ui.getPnlUserSpace().revalidate();
        ui.getPnlUserSpace().repaint();

        login = null;
        login = new Thread(this);
        
        login.setName("Login Thread");

        //Start Loader
        login.start();

    }

    public void doneLogin() {
        //add button panel to UI
        ui.getPnlUserSpace().removeAll();
//        btnGo.setVisible(true);
//        btnGo.setVisible(false);
        StartListener start = new StartListener();
        start.actionPerformed(null);
        ui.getPnlUserSpace().add(pnlUserButton);
        ui.getPnlUserSpace().revalidate();
        //Notify Ready.
        isLoaded = true;
        if (START_WITH_MINI) {
            ui.getMiniMode().setMode(AuroraMini.MINIMIZE_MODE);
        }
    }

    public ArrayList<String> getDisplay() {
        AuroraVI = new aXAVI();
        if (checkUser()) {
            ArrayList<String> LoginDisplay = new ArrayList<String>();

            LoginDisplay.add(AuroraVI.VI(aXAVI.inx_Greeting) + " " + FileIO.getUserName());
            LoginDisplay.add(AuroraVI.VI(aXAVI.inx_WelcomeBack));
            return LoginDisplay;
        } else {
            ArrayList<String> LoginDisplay = new ArrayList<String>();

            LoginDisplay.add(AuroraVI.VI(aXAVI.inx_Greeting) + " " + FileIO.getUserName());

            LoginDisplay.add(AuroraVI.VI(aXAVI.inx_Welcome) + " to Aurora Game Manager");

            LoginDisplay.add(AuroraVI.VI(aXAVI.inx_Preparing) + " for First Time Use");

            LoginDisplay.add(AuroraVI.VI(aXAVI.inx_Please) + " Wait...");
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
                auroraStorage.getStoredLibrary().setUpDatabase(FirstTimeLoad, FileIO.getPath() + "/Game Data/");
                auroraStorage.getStoredProfile().setUpDatabase(FirstTimeLoad, FileIO.getPath() + "/User Data/");
                auroraStorage.getStoredSettings().setUpDatabase(FirstTimeLoad, FileIO.getPath() + "/User Data/");

                Display.add("Created New Profile");

                //Check if both Sub folders Exist
            } else if (!checkSubDir()) {
                FirstTimeLoad = true;
                Display.add("Unable To Find Subfolders");
                Display.add("Attempting to Create New Ones...");
                FileIO.createFolder("User Data");
                FileIO.createFolder("Game Data");

                //Load Databases
                auroraStorage.getStoredLibrary().setUpDatabase(FirstTimeLoad, FileIO.getPath() + "/Game Data/");
                auroraStorage.getStoredProfile().setUpDatabase(FirstTimeLoad, FileIO.getPath() + "/User Data/");
                auroraStorage.getStoredSettings().setUpDatabase(FirstTimeLoad, FileIO.getPath() + "/User Data/");

            } else if (!checkDBFiles()) {
                FirstTimeLoad = true;
                Display.add("Unable To Find a Data Files");
                Display.add("Attempting to Create New Ones...");
                //Load Databases
                auroraStorage.getStoredLibrary().setUpDatabase(FirstTimeLoad, FileIO.getPath() + "/Game Data/");
                auroraStorage.getStoredProfile().setUpDatabase(FirstTimeLoad, FileIO.getPath() + "/User Data/");
                auroraStorage.getStoredSettings().setUpDatabase(FirstTimeLoad, FileIO.getPath() + "/User Data/");
            } else {
                FileIO.setPath(FileIO.getPath() + path);
            }
            if (!FirstTimeLoad && START_WITH_MINI) {
                ui.auroraMinimize(AuroraMini.LOADING_MODE);
            }

            //Check if Online
            if (!checkOnline("auroragm.sourceforge.net")) {
                Online = false;
                Display.add("I Can't Connect To AuroraDB, Let Me Try Again...", Color.RED);

                //Check if its the Users fault or the aurora severs are down
                if (checkOnline("google.com") && !checkOnline("auroragm.sourceforge.net")) {
                    Display.add("Well, It Seems Our Servers Are Down, Try Again In A Bit.", Color.RED);

                    //The User is having internet problems
                } else if (!checkOnline("google.com")) {
                    Display.add("Can't Connect To Google...");
                    Display.add("Either The Universe Exploded OR You Don't Have Internet...", Color.RED);
                    Display.add("Running In Offline Mode...");
                } else {
                    Display.add("I Seem To Have Finnally Esstablished Connection...");
                }
            } else {
                Online = true;

            }


            if (!FirstTimeLoad) {
                //Load Databases
                auroraStorage.getStoredLibrary().setUpDatabase(FirstTimeLoad, FileIO.getPath() + "/Game Data/");
                auroraStorage.getStoredLibrary().storeFromDatabase();
                auroraStorage.getStoredProfile().setUpDatabase(FirstTimeLoad, FileIO.getPath() + "/User Data/");
                auroraStorage.getStoredProfile().storeFromDatabase();
                auroraStorage.getStoredSettings().setUpDatabase(FirstTimeLoad, FileIO.getPath() + "/User Data/");
                auroraStorage.getStoredSettings().storeFromDatabase();
            }

            Display.add("Loading Complete", new Color(0, 191, 255));


            break;
        }

    }

    public void setSizes() {

        if (ui.isLargeScreen()) {
            SIZE_Display = 20;
            SIZE_DisplayFont = ui.getSIZE_pnlBottom() / 10;
        } else {
            SIZE_Display = 10;
            SIZE_DisplayFont = ui.getSIZE_pnlBottom() / 10;

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
            Logger.getLogger(Aurora_StartUp.class.getName()).log(Level.SEVERE, null, ex);

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
        if (FileIO.checkFile(FileIO.getPath() + path + "/User Data") && FileIO.checkFile(FileIO.getPath() + path + "/Game Data")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkDBFiles() {
        if (FileIO.checkFile(FileIO.getPath() + path + "/User Data/User.h2.db") && FileIO.checkFile(FileIO.getPath() + path + "/Game Data/Games.h2.db")) {
            return true;
        } else {
            return false;
        }
    }

    public void startTransision() {
        isTransisioning = true;
        trans = new StartLoader(ui, this);
        trans.transitionToMain();
        ui.getPnlUserSpace().setVisible(false);
    }

    /**
     * Transitions to the Main_Window
     */
    class StartListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            startTransision();
        }
    }

    class FrameKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {

            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (isLoaded && !isTransisioning) {

                    startTransision();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }

    public aFileManager getFileIO() {
        return FileIO;
    }

    public static boolean isOnline() {
        return Online;
    }

    public FrameKeyListener getStartKeyHandler() {
        return startKeyHandler;
    }

    public aScrollingImage getHexAnimation() {
        return HexAnimation;
    }

    public void setHexAnimation(aScrollingImage HexAnimation) {
        this.HexAnimation = HexAnimation;
    }

    public AuroraStorage getAuroraStorage() {
        return auroraStorage;
    }
}
