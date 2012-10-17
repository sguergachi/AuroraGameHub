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
import aurora.engine.V1.Logic.aSimpleDB;
import aurora.engine.V1.UI.aImagePane;
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
    private GameLibraryUI libUI;
    private aSimpleDB db;
    private ArrayList foundGameList;
    private char typed;
    private String AppendedName = ""; //This is the concatination of all characters
    private String foundGame;
    private static Game foundGameCover;
    private aImagePane notFound;
    private Thread typeThread;
    private int sleep;
    private Object[] foundArray;
    private AuroraStorage storage;

    
    //////////////////////////
    ////////Constructor//////
    ////////////////////////
    public GameSearch(GameLibraryUI gameLibraryUI, aSimpleDB database, AuroraStorage storage) {
        this.ui = gameLibraryUI.getCoreUI();
        this.db = database;
        this.storage = storage;
        libUI = gameLibraryUI;
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
            //searchGame();
        }
    }

    public void removeChar(char typedChar) {

        typed = typedChar;

        //Remove ONE Character From End of Appended Name
        if (AppendedName.length() - 1 > 0) {
            AppendedName = (String) AppendedName.subSequence(0, AppendedName.length() - 1);
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

        libUI.getCoverPane().removeAll();
        libUI.getCoverPane().revalidate();
        libUI.getCoverPane().add(libUI.getCoverGame());
        libUI.getCoverGame().revalidate();
        libUI.getCoverPane().revalidate();

        libUI.getSearchText().setText("");
        AppendedName = "";
        foundGame = "";

        foundArray = null;
        libUI.getAddGamePane().revalidate();
        libUI.getListModel().removeAllElements();
        libUI.getStepOne().setImgURl("AddGame_step1_red.png");
        libUI.checkNotifiers();
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
            foundGame = (String) db.getRowFlex("AuroraTable", new String[]{"FILE_NAME"}, "GAME_NAME='" + gameName.replace("'", "''") + "'", "FILE_NAME")[0];
        } catch (Exception ex) {
            Logger.getLogger(GameSearch.class.getName()).log(Level.SEVERE, null, ex);
            foundGame = null;
        }

        //If not found show Placeholder and turn notification red
        if (foundGame == null) {
            libUI.getCoverPane().removeAll();
            notFound = new aImagePane("NoGameFound.png", 220, 250);
            libUI.getCoverPane().add(notFound);
            foundGameCover = null;
            libUI.getStepOne().setImgURl("AddGame_step1_red.png");
            libUI.checkNotifiers();
            libUI.getListModel().removeAllElements();
            libUI.getCoverPane().repaint();
            libUI.getCoverPane().revalidate();

            //Show the game Cover if a single database item is found
        } else if (foundGame != null) {

            libUI.getCoverPane().removeAll();
            //Create the new GameCover object
            foundGameCover = new Game(libUI.getGridSplit(), ui, libUI.getDashUI(), storage);
            try {
                foundGameCover.setCoverUrl(foundGame);
            } catch (MalformedURLException ex) {
                Logger.getLogger(GameSearch.class.getName()).log(Level.SEVERE, null, ex);
            }
            foundGameCover.setCoverSize(220, 250);
            foundGameCover.setGameName(gameName);
            
            libUI.getCoverPane().add(foundGameCover);
            try {
                foundGameCover.update();
                foundGameCover.removeInteraction();
            } catch (MalformedURLException ex) {
                Logger.getLogger(GameSearch.class.getName()).log(Level.SEVERE, null, ex);
            }

            //Change notification
            libUI.getStepOne().setImgURl("AddGame_step1_green.png");
            libUI.checkNotifiers();
            libUI.getCoverPane().repaint();
            libUI.getCoverPane().revalidate();

        }
    }

    public static Game getFoundGameCover() {
        return foundGameCover;
    }

    private void searchGame() {

        //What Happends When The Length is zero
        if (AppendedName.length() <= 0 || libUI.getGameSearchBar().getText().length() == 0) {
            System.out.println("RESETING PANE");
            resetCover();
            libUI.getCoverPane().repaint();
            libUI.getCoverPane().revalidate();
        } else {
            libUI.getListModel().removeAllElements();
            //Query the database
            try {
                foundArray = db.searchAprox("AuroraTable", "FILE_NAME", "GAME_NAME", AppendedName.toString());
            } catch (SQLException ex) {
                Logger.getLogger(GameSearch.class.getName()).log(Level.SEVERE, null, ex);

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
                        libUI.getListModel().addElement(gameItem.replace("-", " ").replace(".png", ""));
                    }
                }
                libUI.getGamesList().revalidate();
               //Library.getGamesList().setSelectedIndex(1);
            } catch (Exception ex) {
                foundGame = null;
            }


            //If Can't Get the game then show a Placeholder Image
            //and turn the notifier red
            if (foundGame == null) {

                libUI.getCoverPane().removeAll();
                notFound = new aImagePane("NoGameFound.png", 220, 250);
                libUI.getCoverPane().add(notFound);
                foundGameCover = null;
                libUI.getStepOne().setImgURl("AddGame_step1_red.png");
                libUI.checkNotifiers();
                libUI.getListModel().removeAllElements();
                libUI.getCoverPane().repaint();
                libUI.getCoverPane().revalidate();

            } else if (foundGame != null) {

                libUI.getCoverPane().removeAll();


                //Set up GameCover object with First Database item found
                foundGameCover = new Game(libUI.getGridSplit(), ui, libUI.getDashUI(), storage);
                try {
                    foundGameCover.setCoverUrl(foundGame); //use seperate string
                } catch (MalformedURLException ex) {
                    Logger.getLogger(GameSearch.class.getName()).log(Level.SEVERE, null, ex);
                }
                foundGameCover.setCoverSize(220, 250);
                foundGameCover.setGameName(foundGame.replace("-", " ").replace(".png", ""));
                
                libUI.getCoverPane().add(foundGameCover);
                //Show GameCover
                try {
                    foundGameCover.update();
                    foundGameCover.removeInteraction();
                } catch (MalformedURLException ex) {
                    Logger.getLogger(GameSearch.class.getName()).log(Level.SEVERE, null, ex);
                }

                //Trun notifier Green
                libUI.getStepOne().setImgURl("AddGame_step1_green.png");
                libUI.checkNotifiers();
                libUI.getCoverPane().repaint();
                libUI.getCoverPane().revalidate();
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
                Logger.getLogger(GameSearch.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;
        }
        searchGame();
        typeThread = null;
    }
}
