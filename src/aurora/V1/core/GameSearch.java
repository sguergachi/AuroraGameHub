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

import aurora.V1.core.screen_ui.GameLibraryUI;
import aurora.engine.V1.Logic.ASimpleDB;
import aurora.engine.V1.UI.AImagePane;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * For the AddGameUI to search through the AuroraDB for games.
 *
 * @author Sammy
 */
public class GameSearch implements Runnable {

    private AuroraCoreUI ui;

    private GameLibraryUI libraryUI;

    private ASimpleDB db;

    private ArrayList foundGameList;

    private char typed;

    private String AppendedName = ""; //This is the concatination of all characters

    private String foundGame;

    private static Game foundGameCover;

    private AImagePane notFound;

    private Thread typeThread;

    private int sleep;

    private Object[] foundArray;

    private AuroraStorage storage;

    /////////////////////
    /////Constructor/////
    /////////////////////
    public GameSearch(GameLibraryUI gameLibraryUI, ASimpleDB database,
                      AuroraStorage storage) {

        this.ui = gameLibraryUI.getCoreUI();
        this.db = database;
        this.storage = storage;
        libraryUI = gameLibraryUI;
        foundGameList = new ArrayList();

    }

    public void typedChar(char typedChar) {
        typed = typedChar; // Set variable to typeChar
        System.out.println("TYPED Character: " + String.valueOf(typed));

        //Append character to var
        AppendedName = AppendedName.concat(String.valueOf(typed));

        System.out.println("Appended GAME name: " + AppendedName);

        //clear library grid if not already clear
        if (AppendedName.length() > 1) {
            //Delay search to allow for Lag Free typing :)
            sleep = 260;

            //Initiate Search Sequence
            typeThread = null;
            if (typeThread == null) {
                typeThread = new Thread(this);
            }

            //Start Search Thread with delay
            try {
                typeThread.start();
            } catch (IllegalThreadStateException ex) {
                System.err.println(ex);
            }

        }
    }

    public void removeChar(char typedChar) {

        typed = typedChar;

        //Remove ONE Character From End of Appended Name
        if (AppendedName.length() - 1 > 0) {
            AppendedName = (String) AppendedName.subSequence(0, AppendedName
                    .length() - 1);
        } else {
            resetCover();
            searchGame();
        }

        System.out.println("Appended name: " + AppendedName);

        //Start search only when more than 1 character is typed
        if (AppendedName.length() > 1) {
            //Delay to allow for typing
            sleep = 260;
            if (typeThread == null) {
                typeThread = new Thread(this);
            }

            //Start Search thread with Delay
            try {
                typeThread.start();
            } catch (IllegalThreadStateException ex) {
            }

        }

    }

    //Reset text, Cover Image, List and turn notification to red
    public void resetCover() {

        libraryUI.getCoverPane().removeAll();
        libraryUI.getCoverPane().revalidate();
        libraryUI.getCoverPane().add(libraryUI.getCoverGame());
        libraryUI.getCoverGame().revalidate();
        libraryUI.getCoverPane().revalidate();

        libraryUI.getSearchText().setText("");
        AppendedName = "";
        foundGame = "";

        foundArray = null;
        libraryUI.getAddGamePane().revalidate();
        libraryUI.getListModel().removeAllElements();
        libraryUI.getStatusBadge1().setImgURl("addUI_badge_invalid.png");
        libraryUI.getLogic().checkNotifiers();

    }

    public void setAppendedName(String AppendedName) {
        this.AppendedName = AppendedName;
    }

    /**
     * Search from outside Class using specific String
     *
     * @param gameName the name of the Game you want to search for
     *
     */
    public void searchSpecificGame(String gameName) {
        try {
            foundGame = (String) db.getRowFlex("AuroraTable", new String[]{
                        "FILE_NAME"}, "GAME_NAME='" + gameName
                    .replace("'", "''") + "'", "FILE_NAME")[0];
        } catch (Exception ex) {
            Logger.getLogger(GameSearch.class.getName()).log(Level.SEVERE, null,
                    ex);
            foundGame = null;
        }

        //If not found show Placeholder and turn notification red
        if (foundGame == null) {
            libraryUI.getCoverPane().removeAll();
            notFound = new AImagePane("NoGameFound.png", libraryUI
                    .getPnlBlankCoverGame().getWidth(), libraryUI
                    .getPnlBlankCoverGame().getHeight());
            libraryUI.getCoverPane().add(notFound);

            foundGameCover = null;
            libraryUI.getStatusBadge1().setImgURl("addUI_badge_invalid.png");
            libraryUI.getLogic().checkNotifiers();
            libraryUI.getListModel().removeAllElements();
            libraryUI.getCoverPane().repaint();
            libraryUI.getCoverPane().revalidate();

            //Show the game Cover if a single database item is found
        } else if (foundGame != null) {

            libraryUI.getCoverPane().removeAll();
            //Create the new GameCover object
            foundGameCover = new Game(libraryUI.getGridSplit(), ui, libraryUI
                    .getDashboardUI(), storage);
            try {
                foundGameCover.setCoverUrl(foundGame);
            } catch (MalformedURLException ex) {
                Logger.getLogger(GameSearch.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
            foundGameCover.setCoverSize(libraryUI.getPnlBlankCoverGame()
                    .getWidth(), libraryUI.getPnlBlankCoverGame().getHeight());
            foundGameCover.setGameName(gameName);

            libraryUI.getCoverPane().add(foundGameCover);
            try {
                foundGameCover.update();
                foundGameCover.removeInteraction();
            } catch (MalformedURLException ex) {
                Logger.getLogger(GameSearch.class.getName()).log(Level.SEVERE,
                        null, ex);
            }

            //Change notification
            libraryUI.getStatusBadge1().setImgURl("addUI_badge_valid.png");
            libraryUI.getLogic().checkNotifiers();
            libraryUI.getCoverPane().repaint();
            libraryUI.getCoverPane().revalidate();

        }
    }

    public Game getFoundGameCover() {
        return foundGameCover;
    }

    private void searchGame() {

        //What Happends When The Length is zero
        if (AppendedName.length() <= 0 || libraryUI.getGameSearchBar().getText()
                .length() == 0) {
            System.out.println("RESETING PANE");
            resetCover();
            libraryUI.getCoverPane().repaint();
            libraryUI.getCoverPane().revalidate();
        } else {
            libraryUI.getListModel().removeAllElements();
            //Query the database

            try {
                System.out.println(db.searchAprox("AuroraTable", "FILE_NAME",
                        "GAME_NAME", AppendedName.toString()));
                foundArray = db.searchAprox("AuroraTable", "FILE_NAME",
                        "GAME_NAME", AppendedName.toString());
            } catch (SQLException ex) {
                Logger.getLogger(GameSearch.class.getName()).log(Level.SEVERE,
                        null, ex);

            }
            try {
                //Get the first game name as a seperate string to show
                //in cover Art
                foundGame = (String) foundArray[0];
                System.out.println(foundGame);

                //Add rest of found items to the List to allow for selection of other games
                for (int i = 0; i <= 10 && i < foundArray.length; i++) {
                    if (foundArray[i] != null) {
                        String gameItem = (String) foundArray[i];
                        libraryUI.getListModel().addElement(gameItem
                                .replace("-", " ").replace(".png", ""));
                    }
                }
                libraryUI.getGamesList().revalidate();
            } catch (Exception ex) {
                foundGame = null;
            }


            //If Can't Get the game then show a Placeholder Image
            //and turn the notifier red
            if (foundGame == null) {

                libraryUI.getCoverPane().removeAll();
                notFound = new AImagePane("NoGameFound.png", libraryUI
                        .getPnlBlankCoverGame().getWidth(), libraryUI
                        .getPnlBlankCoverGame().getHeight());
                libraryUI.getCoverPane().add(notFound);
                foundGameCover = null;
                libraryUI.getStatusBadge1().setImgURl("addUI_badge_invalid.png");
                libraryUI.getLogic().checkNotifiers();
                libraryUI.getListModel().removeAllElements();
                libraryUI.getCoverPane().repaint();
                libraryUI.getCoverPane().revalidate();

            } else if (foundGame != null) {

                libraryUI.getCoverPane().removeAll();


                //Set up GameCover object with First Database item found
                foundGameCover = new Game(libraryUI.getGridSplit(), ui,
                        libraryUI.getDashboardUI(), storage);
                try {
                    foundGameCover.setCoverUrl(foundGame); //use seperate string
                } catch (MalformedURLException ex) {
                    Logger.getLogger(GameSearch.class.getName()).log(
                            Level.SEVERE, null, ex);
                }
                foundGameCover.setCoverSize(libraryUI.getPnlBlankCoverGame()
                        .getWidth(), libraryUI.getPnlBlankCoverGame()
                        .getHeight());
                foundGameCover.setGameName(foundGame.replace("-", " ").replace(
                        ".png", ""));

                libraryUI.getCoverPane().add(foundGameCover);
                //Show GameCover
                try {
                    foundGameCover.update();
                    foundGameCover.removeInteraction();
                } catch (MalformedURLException ex) {
                    Logger.getLogger(GameSearch.class.getName()).log(
                            Level.SEVERE, null, ex);
                }

                //Trun notifier Green
                libraryUI.getStatusBadge1().setImgURl("addUI_badge_valid.png");
                libraryUI.getLogic().checkNotifiers();
                libraryUI.getCoverPane().repaint();
                libraryUI.getCoverPane().revalidate();
            }
        }
    }

    @Override
    public void run() {

        while (Thread.currentThread() == typeThread) {
            try {
                System.out.println("WATING FOR SEARCH");
                Thread.sleep(sleep);
            } catch (InterruptedException ex) {
                Logger.getLogger(GameSearch.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
            break;
        }
        searchGame();
        typeThread = null;
    }
}
