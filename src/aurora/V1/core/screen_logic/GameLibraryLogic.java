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
package aurora.V1.core.screen_logic;

import aurora.V1.core.AuroraCoreUI;
import aurora.V1.core.Game;
import aurora.V1.core.screen_handler.GameLibraryHandler;
import aurora.V1.core.screen_handler.GameLibraryHandler.GameLibraryKeyListener;
import aurora.V1.core.screen_ui.DashboardUI;
import aurora.V1.core.screen_ui.GameLibraryUI;
import aurora.engine.V1.Logic.APostHandler;
import aurora.engine.V1.Logic.AuroraScreenHandler;
import aurora.engine.V1.Logic.AuroraScreenLogic;
import java.net.MalformedURLException;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class GameLibraryLogic implements AuroraScreenLogic {

    private final GameLibraryUI libraryUI;

    private GameLibraryHandler libraryHandler;

    private final AuroraCoreUI coreUI;

    private final DashboardUI dashboardUI;

    private int currentPanel;

    public GameLibraryLogic(GameLibraryUI gamelibraryUi) {
        this.libraryUI = gamelibraryUi;
        this.coreUI = gamelibraryUi.getCoreUI();
        this.dashboardUI = gamelibraryUi.getDashboardUI();
    }

    @Override
    public void setHandler(AuroraScreenHandler handler) {

        this.libraryHandler = (GameLibraryHandler) handler;

    }

    public void addGamesToLibrary() {
        try {

            //* Add Games Marked Fav first *//

            for (int i = 0; i < libraryUI.getStorage().getStoredLibrary()
                    .getGameNames()
                    .size();
                    i++) {

                Game Game = new Game(libraryUI.getGridSplit(), coreUI,
                        dashboardUI, libraryUI.getStorage());
                if (libraryUI.getStorage().getStoredLibrary().getFaveStates()
                        .get(i)) {
                    Game.setGameName(libraryUI.getStorage().getStoredLibrary()
                            .getGameNames()
                            .get(i));
                    Game.setCoverUrl(libraryUI.getStorage().getStoredLibrary()
                            .getBoxArtPath()
                            .get(i));
                    //Handle appostrophese in game path
                    Game.setGamePath(libraryUI.getStorage().getStoredLibrary()
                            .getGamePath()
                            .get(i).replace("'", "''"));
                    Game.setFavorite(libraryUI.getStorage().getStoredLibrary()
                            .getFaveStates()
                            .get(i));
                    Game.setCoverSize(libraryUI.getGameCoverWidth(), libraryUI
                            .getGameCoverHeight());

                    libraryUI.getGridSplit().addGame(Game);
                }
            }

            //* Add Non-Fav games after *//

            for (int i = 0; i < libraryUI.getStorage().getStoredLibrary()
                    .getGameNames()
                    .size();
                    i++) {

                Game Game = new Game(libraryUI.getGridSplit(), coreUI,
                        dashboardUI, libraryUI.getStorage());
                if (!libraryUI.getStorage().getStoredLibrary().getFaveStates()
                        .get(i)) {
                    Game.setGameName(libraryUI.getStorage().getStoredLibrary()
                            .getGameNames()
                            .get(i));
                    Game.setCoverUrl(libraryUI.getStorage().getStoredLibrary()
                            .getBoxArtPath()
                            .get(i));
                    //Handle appostrophese in game path
                    Game.setGamePath(libraryUI.getStorage().getStoredLibrary()
                            .getGamePath()
                            .get(i).replace("'", "''"));
                    Game.setFavorite(libraryUI.getStorage().getStoredLibrary()
                            .getFaveStates()
                            .get(i));
                    Game.setCoverSize(libraryUI.getGameCoverWidth(), libraryUI
                            .getGameCoverHeight());

                    libraryUI.getGridSplit().addGame(Game);
                }
            }

            libraryUI.getGridSplit()
                    .finalizeGrid(libraryHandler.new ShowAddGameUiHandler(
                    libraryUI), libraryUI
                    .getGameCoverWidth(), libraryUI.getGameCoverHeight());

            //Load First Grid by default
            loadGames(
                    0);
        } catch (MalformedURLException ex) {
            System.out.println("MalformedURLExeption \n" + ex);
        }
    }

    /**
     * SmartLoad GameCover Covers to minimize memory usage through burst loading
     *
     */
    public void loadGames(int currentGridIndex) throws MalformedURLException {

        System.out.println("LAUNCHING LOAD METHOD");
        currentPanel = (currentGridIndex);
        if (currentPanel < 0) {
            currentPanel = 0;
        }
        System.out.println("current panel: " + currentPanel);



        //Load First Panels

        libraryUI.setIsGameLibraryKeyListenerAdded(false);
        for (int i = 0; i < libraryUI.getGridSplit().getGrid(currentPanel)
                .getArray().size();
                i++) {
            Game game = new Game(libraryUI.getGridSplit(), coreUI, dashboardUI);
            try {
                game = (Game) libraryUI.getGridSplit().getGrid(currentPanel)
                        .getArray().get(i);
                game.addKeyListener(libraryHandler.new searchRefocusListener(
                        libraryUI));

                for (int j = 0; j < game.getKeyListeners().length; j++) {
                    if (game.getKeyListeners()[j] instanceof GameLibraryKeyListener) {
                        libraryUI.setIsGameLibraryKeyListenerAdded(true);
                        break;
                    }
                }

                if (!libraryUI.IsGameLibraryKeyListenerAdded()) {
                    System.out.println("ADDING GAMELIBRARYLISTENER TO " + game
                            .getName());
                    game
                            .addKeyListener(libraryHandler.new GameLibraryKeyListener(
                            libraryUI,
                            coreUI));
                }


                if (!game.isLoaded()) {
                    game.update();

                    System.out.println("loading: " + game.getGameName());
                }
            } catch (RuntimeException ex) {
                System.out.println(ex);
            }
        }


        libraryUI.setIsGameLibraryKeyListenerAdded(false);
        //Load Second Panel if exists -- SMART LOAD
        if (currentPanel < libraryUI.getGridSplit().getArray().size() - 1) {
            for (int i = 0; i < libraryUI.getGridSplit().getGrid(currentPanel
                                                                 + 1).getArray()
                    .size(); i++) {
                Game game = new Game(libraryUI.getGridSplit(), coreUI,
                        dashboardUI);
                try {
                    game = (Game) libraryUI.getGridSplit().getGrid(currentPanel
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
                        System.out.println("ADDING GAMELIBRARYLISTENER TO"
                                           + game.getName());
                        game
                                .addKeyListener(libraryHandler.new GameLibraryKeyListener(
                                libraryUI, coreUI));
                    }
                    if (!game.isLoaded()) {
                        game.update();
                        System.out.println("Secondary loading: " + game
                                .getName());
                    }
                } catch (RuntimeException ex) {
                    System.out.println(ex);
                }


            }
        }
    }

    public void checkNotifiers() {

        if (libraryUI.getStepOne().getImgURl().equals("AddGame_step1_green.png")
            && libraryUI.getStepTwo()
                .getImgURl().equals("AddGame_step2_green.png")) {
            //Animate the Button bellow Add Game UI
            libraryUI.getAddGameToLibButton().setVisible(true);
            libraryUI.getAddGameToLibButtonAnimator().setInitialLocation((coreUI
                    .getFrame()
                    .getWidth() / 2) - (335 / 2), libraryUI.getAddGamePane()
                    .getImgIcon()
                    .getIconHeight() - 180);
            libraryUI.getAddGameToLibButtonAnimator().moveVertical(libraryUI
                    .getAddGamePane()
                    .getImgIcon()
                    .getIconHeight() - 55, 20);
            libraryUI.getAddGameToLibButtonAnimator().removeAllListeners();
        }

        if ((libraryUI.getStepOne().getImgURl().equals("AddGame_step1_red.png")
             || libraryUI.getStepTwo()
                .getImgURl().equals("AddGame_step2_red.png"))
            && libraryUI.getAddGameToLibButton().isVisible()) {


            libraryUI.getAddGameToLibButtonAnimator().moveVertical(0, 16);
            libraryUI.getAddGameToLibButtonAnimator()
                    .addPostAnimationListener(new APostHandler() {
                @Override
                public void actionPerformed() {
                    libraryUI.getAddGameToLibButton().setVisible(false);
                }
            });
        }

    }
}
