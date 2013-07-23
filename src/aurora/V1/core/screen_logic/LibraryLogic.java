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
import aurora.V1.core.Game;
import aurora.V1.core.GameFinder;
import aurora.V1.core.GameSearch;
import aurora.V1.core.GridSearch;
import aurora.V1.core.screen_handler.LibraryHandler;
import aurora.V1.core.screen_handler.LibraryHandler.GameLibraryKeyListener;
import aurora.V1.core.screen_ui.DashboardUI;
import aurora.V1.core.screen_ui.LibraryUI;
import aurora.engine.V1.Logic.AAnimate;
import aurora.engine.V1.Logic.AFileManager;
import aurora.engine.V1.Logic.ANuance;
import aurora.engine.V1.Logic.APostHandler;
import aurora.engine.V1.Logic.ASimpleDB;
import aurora.engine.V1.Logic.AThreadWorker;
import aurora.engine.V1.Logic.AuroraScreenHandler;
import aurora.engine.V1.Logic.AuroraScreenLogic;
import aurora.engine.V1.UI.AImage;
import aurora.engine.V1.UI.AImagePane;
import aurora.engine.V1.UI.ARadioButton;
import aurora.engine.V1.UI.ASlickLabel;
import aurora.engine.V1.UI.ATimeLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.prefs.Preferences;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.apache.log4j.Logger;

/**
 * .------------------------------------------------------------------------.
 * | LibraryLogic
 * .------------------------------------------------------------------------.
 * |
 * |
 * | This Class is the Logic component of the Library App. Its instanced
 * | In LibraryUI.
 * |
 * | This class is supposed to handle all of the Long Processing of UI or
 * | Actions generated by the Handler. Reusable processing and long logic
 * | methods should go here. It implements the AuroraScreenLogic interface.
 * |
 * .........................................................................
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 * @author Carlos Machado <camachado@gmail.com>
 * <p/>
 */
public class LibraryLogic implements AuroraScreenLogic {

    /**
     * Library UI instance.
     */
    private final LibraryUI libraryUI;

    /**
     * Library Handler instance.
     */
    private LibraryHandler libraryHandler;

    /**
     * Core UI instance.
     */
    private final AuroraCoreUI coreUI;

    /**
     * Dashboard UI instance.
     */
    private final DashboardUI dashboardUI;

    /**
     * Boolean on whether the library even has a single favorite game in DB.
     */
    private boolean libHasFavourites;

    static final Logger logger = Logger.getLogger(LibraryLogic.class);

    private AAnimate addGameToLibButtonAnimator;

    private boolean isLoaded = false;

    private ASimpleDB coverDB;

    private GameSearch gameSearch_addUI;

    private GridSearch gridSearch;

    private GameSearch gameSearch_editUI;

    private GameSearch gameSearch_autoUI;

    private ArrayList<String> nameOfGames;

    private ArrayList<File> executableGamePath;

    private boolean isAutoLoadedOnce;

    private boolean refreshAuto;

    private AThreadWorker findGames;

    /**
     * .-----------------------------------------------------------------------.
     * | LibraryLogic(LibraryUI)
     * .-----------------------------------------------------------------------.
     * |
     * | This is the Constructor of the Game Library Logic class.
     * |
     * | The LibraryUI is required to make adjustments to the UI from the
     * | logic.
     * |
     * | The games are loaded, added and overall managed through here.
     * |
     * | NOTE: for Logic to work you must use the set(HandlerDashboardHandler)
     * | method for the logic to be able to attach some handlers to UI
     * | elements
     * |
     * .........................................................................
     *
     * @param gamelibraryUi LibraryUI
     *
     */
    public LibraryLogic(final LibraryUI gamelibraryUi) {
        this.libraryUI = gamelibraryUi;
        this.coreUI = gamelibraryUi.getCoreUI();
        this.dashboardUI = gamelibraryUi.getDashboardUI();

    }

    @Override
    public final void setHandler(final AuroraScreenHandler handler) {

        this.libraryHandler = (LibraryHandler) handler;

    }

    /**
     * .-----------------------------------------------------------------------.
     * | setUpCoverDB()
     * .-----------------------------------------------------------------------.
     * |
     * | This method will start connection with CoverDB and will initialize
     * | gameSearch and gridSeach methods.
     * |
     * .........................................................................
     *
     * <p/>
     */
    public void setUpCoverDB() {
        //* Start Aurora Dabatase connection *//
        try {
            coverDB = new ASimpleDB("AuroraDB", "AuroraTable", false, System
                    .getProperty("user.home") + "//AuroraData//");
        } catch (SQLException ex) {
            logger.error(ex);
        }

        gridSearch = new GridSearch(libraryUI.getCoreUI(), libraryUI,
                libraryHandler);
        gameSearch_addUI = new GameSearch(libraryUI, coverDB,
                libraryUI.getStorage());
        gameSearch_editUI = new GameSearch(libraryUI, coverDB,
                libraryUI.getStorage());
        gameSearch_autoUI = new GameSearch(libraryUI, coverDB,
                libraryUI.getStorage());

    }

    public GameSearch getGameSearch_autoUI() {
        return gameSearch_autoUI;
    }

    public GameSearch getGameSearch_addUI() {
        return gameSearch_addUI;
    }

    public GridSearch getGridSearch() {
        return gridSearch;
    }

    public GameSearch getGameSearch_editUI() {
        return gameSearch_editUI;
    }

    /**
     * .-----------------------------------------------------------------------.
     * | addGamesToLibrary()
     * .-----------------------------------------------------------------------.
     * |
     * | This method will add all games found in the Aurora Storage to the
     * | Library UI.
     * |
     * | It will add the games from favorite to non-favorite games.
     * | It will generate new Grids along the way when it fills previous ones.
     * |
     * .........................................................................
     *
     * <p/>
     */
    public final void addGamesToLibrary() {
        try {


            //* check that favorites are not null *//
            if (libraryUI.getStorage().getStoredLibrary().getFaveStates()
                != null) {
                libHasFavourites = true;
            }



            int librarySize = libraryUI.getStorage().getStoredLibrary()
                    .getGameNames()
                    .size() - 1;

            String organize = libraryUI.getStorage().getStoredSettings()
                    .getSettingValue(
                    "organize");


            ArrayList<Game> gamesList = new ArrayList<Game>();

            // Create Array of Games //
            for (int i = 0; i <= librarySize;
                    i++) {

                Game game = null;

                if (!isLoaded) {

                    game = new Game(libraryUI.getGridSplit(), coreUI,
                            dashboardUI, libraryUI.getStorage());
                    game.setGameName(libraryUI.getStorage()
                            .getStoredLibrary()
                            .getGameNames()
                            .get(i));
                    game.setCoverUrl(libraryUI.getStorage()
                            .getStoredLibrary()
                            .getBoxArtPath()
                            .get(i));

                    if (libHasFavourites) {
                        game.setFavorite(libraryUI.getStorage()
                                .getStoredLibrary()
                                .getFaveStates()
                                .get(i));
                    }

                    //* Handle appostrophese in game path *//
                    game.setGamePath(libraryUI.getStorage()
                            .getStoredLibrary()
                            .getGamePath()
                            .get(i).replace("'", "''"));

                    game.setCoverSize(libraryUI.getGameCoverWidth(),
                            libraryUI
                            .getGameCoverHeight());
                } else {
                    game = libraryUI.getGridSplit().getGame(i);
                }

                gamesList.add(game);

            }


            //clear grids to start
            libraryUI.getGridSplit().clearAllGrids();


            // Add Metadata to games from database if it exists //
            if (!isLoaded) {
                if (libraryUI.getStorage().getStoredProfile()
                        .getGameNames() != null) {
                    for (int i = 0; i < libraryUI.getStorage()
                            .getStoredProfile()
                            .getGameNames()
                            .size();
                            i++) {

                        String gameName = libraryUI.getStorage()
                                .getStoredProfile()
                                .getGameNames().get(i);

                        int a = 0;
                        Game game = null;
                        while (a < gamesList.size()) {
                            if (gamesList.get(a).getName().equals(gameName)) {
                                game = gamesList.get(a);
                                break;
                            }
                            a++;
                        }



                        if (game != null) {

                            game.setGameType(libraryUI.getStorage()
                                    .getStoredProfile()
                                    .getGameTypes().get(i));
                            game.setTotalTimePlayed(libraryUI.getStorage()
                                    .getStoredProfile()
                                    .getTotalTimes().get(i));
                            game.setOcurrencesPlayed(libraryUI.getStorage()
                                    .getStoredProfile()
                                    .getOccurrenceTimes().get(i));
                            game.setLastPlayed(libraryUI.getStorage()
                                    .getStoredProfile()
                                    .getLastTimes().get(i));

                            logger.info("ProfileDB Game Name:" + game
                                    .getGameName());
                            logger.info("ProfileDB Game Type:" + game
                                    .getGameType());
                            logger.info("ProfileDB Last Played:" + game
                                    .getLastPlayed());
                            logger.info("ProfileDB Occurences:" + game
                                    .getOccurencesPlayed());
                            logger.info("ProfileDB Total Time:" + game
                                    .getTotalTimePlayed());



                        }
                    }


                }
            }
            if (organize == null) {
                organize = "favorite";
                libraryUI.getStorage().getStoredSettings().saveSetting(organize,
                        "favorite");
            }

            // Check if Organization Type is "Favorite" //
            if (organize.equalsIgnoreCase("Favorite")) {
                if (!libraryUI.getImgOrganizeType().getImgURl().equals(
                        "library_favourites.png")) {
                    libraryUI.getImgOrganizeType().setImgURl(
                            "library_favourites.png");
                }

                //* Reverse Add Games Marked Fav first *//
                for (int i = librarySize; i >= 0;
                        i--) {


                    if (libHasFavourites && gamesList.get(i).isFavorite()) {

                        libraryUI.getGridSplit().addGame(gamesList.get(i));

                    }


                }


                //* Add Non-Fav games after *//
                for (int i = 0; i <= librarySize;
                        i++) {


                    if (!libHasFavourites || !gamesList.get(i).isFavorite()) {

                        libraryUI.getGridSplit().addGame(gamesList.get(i));
                    }

                }

                // Organize according to Alphabetic Game Name //
            } else if (organize.equalsIgnoreCase("Alphabetic")) {

                libraryUI.getImgOrganizeType().setImgURl(
                        "library_alphabetic.png");

                String[] alphaArray = new String[gamesList.size()];

                for (int i = librarySize; i >= 0;
                        i--) {

                    alphaArray[i] = gamesList.get(i).getName();
                }

                Arrays.sort(alphaArray);

                for (int i = 0; i <= librarySize;
                        i++) {

                    int h = 0;
                    while (!gamesList.get(h).getName().equals(alphaArray[i])) {
                        h++;
                    }
                    libraryUI.getGridSplit().addGame(gamesList.get(h));

                }

                gamesList = null;
                alphaArray = null;


                // Organize according to Time Played //
            } else if (organize.equalsIgnoreCase("Most Played")) {

                libraryUI.getImgOrganizeType().setImgURl(
                        "library_mostplayed.png");

                ArrayList<Game> timeList = new ArrayList<Game>();
                for (int i = 0; i <= librarySize;
                        i++) {
                    if (gamesList.get(i)
                            .getTotalTimePlayed() != null) {
                        timeList.add(gamesList.get(i));
                    } else {
                        gamesList.get(i).setTotalTimePlayed("00:00");
                        timeList.add(gamesList.get(i));
                    }
                }



                Collections.sort(timeList, new Comparator<Game>() {
                    private int time;

                    @Override
                    public int compare(Game g1, Game g2) {
                        SimpleDateFormat format = new SimpleDateFormat("HH:mm");

                        try {

                            time = format.parse(g2.getTotalTimePlayed())
                                    .compareTo(format.parse(g1
                                    .getTotalTimePlayed()));

                        } catch (ParseException ex) {
                            java.util.logging.Logger.getLogger(
                                    LibraryLogic.class.getName()).
                                    log(Level.SEVERE, null, ex);
                        }


                        return time;

                    }
                });


                for (int i = 0; i <= librarySize;
                        i++) {
                    libraryUI.getGridSplit().addGame(timeList.get(i));
                }



            }

            libraryUI.getGridSplit()
                    .finalizeGrid(libraryHandler.new ShowAddGameUiHandler(),
                    libraryUI
                    .getGameCoverWidth(), libraryUI.getGameCoverHeight());


            //Load First Grid by default
            loadGames(0);


            AThreadWorker garbage = new AThreadWorker(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.gc();
                }
            });



            garbage.startOnce();

        } catch (MalformedURLException ex) {
            logger.error(ex);
        }
    }

    /**
     * .-----------------------------------------------------------------------.
     * | loadGames(int currentGridIndex)
     * .-----------------------------------------------------------------------.
     * |
     * | This method is where the Library loads the first and sometimes the
     * | second grid of games.
     * |
     * .........................................................................
     *
     * @throws MalformedURLException Exception
     */
    public final void loadGames(final int currentGridIndex) throws
            MalformedURLException {

        if (logger.isDebugEnabled()) {
            logger.debug("Loading Games");
        }

        int currentGrid = currentGridIndex;
        if (currentGrid < 0) {
            currentGrid = 0;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("current panel: " + currentGrid);
        }



        //Load First Panels

        libraryUI.setIsGameLibraryKeyListenerAdded(false);
        for (int i = 0; i < libraryUI.getGridSplit().getGrid(currentGrid)
                .getArray().size();
                i++) {
            Game game = new Game(libraryUI.getGridSplit(), coreUI, dashboardUI);
            try {
                game = (Game) libraryUI.getGridSplit().getGrid(currentGrid)
                        .getArray().get(i);
                game.addKeyListener(libraryHandler.new SearchRefocusListener());
                if (game.getLibraryLogic() == null) {
                    game.setLibraryLogic(this);
                }


                for (int j = 0; j < game.getKeyListeners().length; j++) {
                    if (game.getKeyListeners()[j] instanceof GameLibraryKeyListener) {
                        libraryUI.setIsGameLibraryKeyListenerAdded(true);
                        break;
                    }
                }

                if (!libraryUI.IsGameLibraryKeyListenerAdded()) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("ADDING GAMELIBRARYLISTENER TO " + game
                                .getName());
                    }

                    game.addKeyListener(
                            libraryHandler.new GameLibraryKeyListener());
                }


                if (!game.isLoaded()) {
                    game.update();

                    if (logger.isDebugEnabled()) {
                        logger.debug("loading: " + game.getGameName());
                    }

                }

                game.setSettingsListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        libraryUI.showEditGameUI((Game) e.getSource());

                    }
                });
            } catch (RuntimeException ex) {
                logger.error(ex);
            }
        }


        libraryUI.setIsGameLibraryKeyListenerAdded(false);
        //Load Second Panel if exists -- SMART LOAD
        if (currentGrid < libraryUI.getGridSplit().getArray().size() - 1) {
            for (int i = 0; i < libraryUI.getGridSplit().getGrid(currentGrid
                                                                 + 1).getArray()
                    .size(); i++) {
                Game game = new Game(libraryUI.getGridSplit(), coreUI,
                        dashboardUI);
                try {
                    game = (Game) libraryUI.getGridSplit().getGrid(currentGrid
                                                                   + 1)
                            .getArray()
                            .get(i);

                    for (int j = 0; j < game.getKeyListeners().length; j++) {
                        if (game.getKeyListeners()[j] instanceof GameLibraryKeyListener) {
                            libraryUI.setIsGameLibraryKeyListenerAdded(true);
                            break;
                        }
                    }

                    if (!libraryUI.IsGameLibraryKeyListenerAdded()) {
                        if (logger.isDebugEnabled()) {
                            logger.debug("ADDING GAMELIBRARYLISTENER TO" + game
                                    .getName());
                        }

                        game.addKeyListener(
                                libraryHandler.new GameLibraryKeyListener());
                    }

                    if (!game.isLoaded()) {
                        game.update();
                        if (logger.isDebugEnabled()) {
                            logger.debug("Secondary loading: " + game.getName());
                        }

                    }
                } catch (RuntimeException ex) {
                    logger.error(ex);
                }


            }
        }


        isLoaded = true;

    }

    /**
     * .-----------------------------------------------------------------------.
     * | checkAddGameStatus()
     * .-----------------------------------------------------------------------.
     * |
     * | This method checks if both Add Game UI badges are Green meaning
     * | user is able to add game to the library.
     * |
     * .........................................................................
     *
     * @throws MalformedURLException Exception
     */
    public void checkAddGameStatus() {

        if (libraryUI.getStatusBadge1().getImgURl().equals(
                "addUI_badge_valid.png")
            && libraryUI.getStatusBadge2()
                .getImgURl().equals("addUI_badge_valid.png") && !libraryUI
                .getAddGameToLibButton().isVisible()) {

            //Animate the Button below Add Game UI//
            animateAddButtonDown();


        } else if ((libraryUI.getStatusBadge1().getImgURl().equals(
                "addUI_badge_invalid.png")
                    || libraryUI.getStatusBadge2()
                .getImgURl().equals("addUI_badge_invalid.png"))
                   && libraryUI.getAddGameToLibButton().isVisible()) {

            //Animate up and hide it//
            animateAddButtonUp();
        }

    }

    /**
     * Animates the Add Game To Library Button to a visible state
     */
    private void animateAddButtonDown() {
        addGameToLibButtonAnimator = new AAnimate(libraryUI
                .getAddGameToLibButton());

        libraryUI.getAddGameToLibButton().setVisible(true);
        addGameToLibButtonAnimator.setInitialLocation((coreUI
                .getFrame()
                .getWidth() / 2) - libraryUI.getAddGameToLibButton()
                .getWidth() / 2, libraryUI.getAddGamePane()
                .getImgIcon()
                .getIconHeight() - 180);
        addGameToLibButtonAnimator.moveVertical(libraryUI
                .getAddGamePane()
                .getImgIcon()
                .getIconHeight() - 55, 20);
        addGameToLibButtonAnimator.removeAllListeners();
    }

    /**
     * Animates the Add Game To Library Button to hide behind the AddGamePane
     */
    private void animateAddButtonUp() {
        addGameToLibButtonAnimator = new AAnimate(libraryUI
                .getAddGameToLibButton());

        addGameToLibButtonAnimator.setInitialLocation(libraryUI
                .getAddGameToLibButton().getX(), libraryUI
                .getAddGameToLibButton().getY());
        addGameToLibButtonAnimator.moveVertical(-5, 20);

        addGameToLibButtonAnimator
                .addPostAnimationListener(new APostHandler() {
            @Override
            public void postAction() {
                libraryUI.getAddGameToLibButton().setVisible(false);
            }
        });
    }
    private File steamFile = null;

    /**
     * .-----------------------------------------------------------------------.
     * | fetchSteamDirOnWindows()
     * .-----------------------------------------------------------------------.
     * |
     * | This method looks through the Windows registry to find the Steam game
     * | Directory
     * |
     * .........................................................................
     *
     */
    public File fetchSteamDirOnWindows() {
        final int HKEY_CURRENT_USER = 0x80000001;
        final int KEY_QUERY_VALUE = 1;
        final int KEY_SET_VALUE = 2;
        final int KEY_READ = 0x20019;

        final Preferences userRoot = Preferences.userRoot();
        final Preferences systemRoot = Preferences.systemRoot();
        final Class clz = userRoot.getClass();


        try {
            final Method openKey = clz.getDeclaredMethod("openKey",
                    byte[].class, int.class, int.class);
            openKey.setAccessible(true);

            final Method closeKey = clz
                    .getDeclaredMethod("closeKey",
                    int.class);
            closeKey.setAccessible(true);

            final Method winRegQueryValue = clz.getDeclaredMethod(
                    "WindowsRegQueryValueEx", int.class,
                    byte[].class);
            winRegQueryValue.setAccessible(true);
            final Method winRegEnumValue = clz.getDeclaredMethod(
                    "WindowsRegEnumValue1", int.class, int.class,
                    int.class);
            winRegEnumValue.setAccessible(true);
            final Method winRegQueryInfo = clz.getDeclaredMethod(
                    "WindowsRegQueryInfoKey1", int.class);
            winRegQueryInfo.setAccessible(true);


            byte[] valb = null;
            String vals = null;
            String key = null;
            Integer handle = -1;

            // Query for steam path
            key = "Software\\Classes\\steam\\Shell\\Open\\Command";
            handle = (Integer) openKey.invoke(systemRoot,
                    toCstr(key),
                    KEY_READ, KEY_READ);
            valb = (byte[]) winRegQueryValue.invoke(systemRoot,
                    handle,
                    toCstr(""));
            vals = (valb != null ? new String(valb).trim() : null);
            closeKey.invoke(Preferences.systemRoot(), handle);

            int steamExeIndex = vals.indexOf("steam.exe");
            if (steamExeIndex > 0) {
                String steamPath = vals.substring(1, steamExeIndex);
                steamPath = steamPath + "\\steamapps\\common";
                steamFile = new File(steamPath);

            }
        } catch (Exception ex) {
            logger.error(ex);
        }


        return steamFile;

    }

    private byte[] toCstr(String str) {
        byte[] result = new byte[str.length() + 1];
        for (int i = 0; i < str.length(); i++) {
            result[i] = (byte) str.charAt(i);
        }
        result[str.length()] = 0;
        return result;
    }
    /**
     * .-----------------------------------------------------------------------.
     * | autoFindGames()
     * .-----------------------------------------------------------------------.
     * |
     * | searches Windows computers for games on local drive using GameFinder
     * |
     * .........................................................................
     *
     */
    private boolean selected = false;

    public void autoFindGames(final DefaultListModel model) {


        findGames = new AThreadWorker(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isAutoLoadedOnce || refreshAuto) {

                    LibraryUI.lblLibraryStatus.setForeground(Color.CYAN);
                    LibraryUI.lblLibraryStatus.setText(coreUI.getVi().VI(
                            ANuance.inx_Searching) + " For Games");
                    libraryUI.getPrgLibraryStatus().resume();

                    nameOfGames = null;
                    executableGamePath = null;

                    libraryUI
                            .getModelCheckList().removeAllElements();
                    model.removeAllElements();

                    nameOfGames = GameFinder.getNameOfGamesOnDrive();
                    executableGamePath = GameFinder.getExecutablePathsOnDrive(
                            nameOfGames);



                    for (int i = 0; i < nameOfGames.size(); i++) {

                        if (!libraryUI.getStorage().getStoredLibrary()
                                .getGameNames().contains(nameOfGames.get(i))) {
                            //Create Check Box UI
                            final AImagePane radioPanel = new AImagePane(
                                    "autoUI_checkBG_norm.png", new FlowLayout(
                                    FlowLayout.CENTER));
                            radioPanel.setPreferredSize(new Dimension(radioPanel
                                    .getRealImageWidth(), radioPanel
                                    .getRealImageHeight()));
                            radioPanel.setBorder(null);

                            final ARadioButton radioButton = new ARadioButton(
                                    "autoUI_check_inactive.png",
                                    "autoUI_check_active.png");
                            radioButton.setBorder(null);

                            ASlickLabel label = new ASlickLabel(nameOfGames.get(
                                    i));
                            label.setFont(coreUI.getDefaultFont()
                                    .deriveFont(Font.BOLD,
                                    LibraryUI.listFontSize));


                            radioPanel.add(radioButton);
                            radioPanel.setForeground(Color.black);


                            JPanel pnlGameElement = new JPanel(new FlowLayout(
                                    FlowLayout.LEFT, 5, 0));

                            JLabel gameName = new JLabel(nameOfGames
                                    .get(i));

                            AImagePane imgStatusIcon = new AImagePane(
                                    "autoUI_listIcon.png");
                            imgStatusIcon.setPreferredSize(new Dimension(
                                    imgStatusIcon.getRealImageWidth(),
                                    imgStatusIcon.getRealImageHeight()));


                            if (!gameSearch_autoUI.checkGameExist(gameName
                                    .getText())) {
                                pnlGameElement.add(imgStatusIcon);
                            }

                            pnlGameElement.add(gameName);



                            //Add Check box and Game name
                            libraryUI.getModelCheckList().addElement(radioPanel);

                            model.addElement(pnlGameElement);

                        }
                    }


                    libraryUI.getPnlCheckList().addMouseListener(
                            new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {


                            // Verify that the click occured on the selected cell
                            final int index = libraryUI.getPnlCheckList()
                                    .locationToIndex(e.getPoint());


                            if (((ARadioButton) ((AImagePane) libraryUI
                                    .getModelCheckList().get(index))
                                    .getComponent(0)).isSelected) {
                                selected = true;

                            } else {

                                selected = false;
                            }



                            if (((ARadioButton) ((AImagePane) libraryUI
                                    .getModelCheckList().get(index))
                                    .getComponent(0)).isSelected) {

                                ((ARadioButton) ((AImagePane) libraryUI
                                        .getModelCheckList().get(index))
                                        .getComponent(0))
                                        .setUnSelected();


                            } else {
                                ((ARadioButton) ((AImagePane) libraryUI
                                        .getModelCheckList().get(index))
                                        .getComponent(0)).setSelected();
                            }

                            libraryUI.getPnlCheckList().revalidate();
                            libraryUI.getPnlCheckList().repaint();


                        }
                    });

                    libraryUI.getPnlCheckList().addMouseMotionListener(
                            new MouseMotionListener() {
                        @Override
                        public void mouseDragged(MouseEvent e) {

                            // Verify that the click occured on the selected cell
                            final int index = libraryUI.getPnlCheckList()
                                    .locationToIndex(e.getPoint());


                            if (selected) {

                                ((ARadioButton) ((AImagePane) libraryUI
                                        .getModelCheckList().get(index))
                                        .getComponent(0))
                                        .setUnSelected();
                            } else {

                                ((ARadioButton) ((AImagePane) libraryUI
                                        .getModelCheckList().get(index))
                                        .getComponent(0)).setSelected();
                            }

                            libraryUI.getPnlCheckList().revalidate();
                            libraryUI.getPnlCheckList().repaint();

                        }

                        @Override
                        public void mouseMoved(MouseEvent e) {
                        }
                    });

                    libraryUI.getGamesList().addMouseListener(
                            new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {


                            System.out.println("CLICKED!");

                            // Verify that the click occured on the selected cell
                            final int index = libraryUI.getGamesList()
                                    .locationToIndex(e.getPoint());

                            if (e.getClickCount() == 2) {
                                if (((ARadioButton) ((AImagePane) libraryUI
                                        .getModelCheckList().get(index))
                                        .getComponent(0)).isSelected) {

                                    ((ARadioButton) ((AImagePane) libraryUI
                                            .getModelCheckList().get(index))
                                            .getComponent(0))
                                            .setUnSelected();


                                } else {
                                    ((ARadioButton) ((AImagePane) libraryUI
                                            .getModelCheckList().get(index))
                                            .getComponent(0)).setSelected();
                                }
                            }


                        }
                    });


                    LibraryUI.lblLibraryStatus.setForeground(Color.GREEN);
                    LibraryUI.lblLibraryStatus.setText("Finished");

                    libraryUI.getPrgLibraryStatus().stop();

                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException ex) {
                        java.util.logging.Logger.getLogger(LibraryLogic.class
                                .getName()).
                                log(Level.SEVERE, null, ex);
                    }

                    LibraryUI.lblLibraryStatus.setForeground(Color.LIGHT_GRAY);
                    LibraryUI.lblLibraryStatus.setText("Select a Game");

                    isAutoLoadedOnce = true;
                    refreshAuto = false;
                }
            }
        });


        findGames.startOnce();
    }

    public void autoRefresh() {
        refreshAuto = true;
        findGames.startOnce();
    }

    public boolean isIsAutoLoadedOnce() {
        return isAutoLoadedOnce;
    }

    public void autoSelectAll() {

        AThreadWorker select = new AThreadWorker(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (nameOfGames != null) {
                    for (int i = 0; i < nameOfGames.size(); i++) {
                        ((ARadioButton) ((AImagePane) libraryUI
                                .getModelCheckList().get(i))
                                .getComponent(0)).setSelected();

                        libraryUI.getPnlCheckList().revalidate();
                        libraryUI.getPnlCheckList().repaint();

                        try {
                            Thread.sleep(20);


                        } catch (InterruptedException ex) {
                            java.util.logging.Logger.getLogger(
                                    LibraryLogic.class
                                    .getName()).
                                    log(Level.SEVERE, null, ex);
                        }
                    }
                }

                libraryUI.getPnlCheckList().revalidate();
                libraryUI.getPnlCheckList().repaint();

            }
        });

        select.startOnce();



    }

    public void autoClearAll() {

        AThreadWorker clear = new AThreadWorker(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (nameOfGames != null) {
                    for (int i = 0; i < nameOfGames.size(); i++) {
                        ((ARadioButton) ((AImagePane) libraryUI
                                .getModelCheckList().get(i))
                                .getComponent(0)).setUnSelected();

                        libraryUI.getPnlCheckList().revalidate();
                        libraryUI.getPnlCheckList().repaint();

                        try {
                            Thread.sleep(20);


                        } catch (InterruptedException ex) {
                            java.util.logging.Logger.getLogger(
                                    LibraryLogic.class
                                    .getName()).
                                    log(Level.SEVERE, null, ex);
                        }
                    }
                }

                libraryUI.getPnlCheckList().revalidate();
                libraryUI.getPnlCheckList().repaint();

            }
        });

        clear.startOnce();
    }
}
