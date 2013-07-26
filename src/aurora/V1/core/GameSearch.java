/*
 * Made By Sardonix Creative.
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

import aurora.V1.core.screen_ui.LibraryUI;
import aurora.engine.V1.Logic.ASimpleDB;
import aurora.engine.V1.UI.AImage;
import aurora.engine.V1.UI.AImagePane;
import java.awt.Dimension;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

/**
 * For the AddGameUI to search through the AuroraDB for games.
 *
 * @author Sammy
 */
public class GameSearch implements Runnable {

    private AuroraCoreUI ui;

    private LibraryUI libraryUI;

    private ASimpleDB db;

    private char typed;

    private String AppendedName = ""; //This is the concatenation of all characters

    private String foundGame;

    private static Game foundGameCover;

    private AImagePane notFound;

    private Thread typeThread;

    private int sleep;

    private Object[] foundArray;

    private AuroraStorage storage;

    static final Logger logger = Logger.getLogger(GameSearch.class);

    private AImagePane imgBlankCover;

    private AImagePane pnlGameCoverPane;

    private DefaultListModel listModel;

    private AImage imgStatus;

    private JTextField txtSearch;

    /////////////////////
    /////Constructor/////
    /////////////////////
    public GameSearch(LibraryUI gameLibraryUI, ASimpleDB database,
                      AuroraStorage storage) {

        this.ui = gameLibraryUI.getCoreUI();
        this.db = database;
        this.storage = storage;
        libraryUI = gameLibraryUI;

    }

    public void setUpGameSearch(AImagePane imgBlank, AImagePane coverPane,
                                DefaultListModel model, AImage status,
                                JTextField textField) {


        this.imgBlankCover = imgBlank;
        this.pnlGameCoverPane = coverPane;
        this.listModel = model;
        this.imgStatus = status;
        this.txtSearch = textField;

    }

    public void typedChar(char typedChar) {
        typed = typedChar; // Set variable to typeChar
        if (logger.isDebugEnabled()) {
            logger.debug("TYPED Character: " + String.valueOf(typed));
        }

        //Append character to var
        AppendedName = AppendedName.concat(String.valueOf(typed));

        if (logger.isDebugEnabled()) {
            logger.debug("Appended GAME name: " + AppendedName);
        }

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
                logger.error(ex);
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

        if (logger.isDebugEnabled()) {
            logger.debug("Appended name: " + AppendedName);
        }

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

        pnlGameCoverPane.removeAll();
        pnlGameCoverPane.revalidate();
        pnlGameCoverPane.add(imgBlankCover);
        pnlGameCoverPane.revalidate();
        pnlGameCoverPane.repaint();
        imgBlankCover.repaint();

        AppendedName = "";
        foundGame = "";

        foundArray = null;
        listModel.removeAllElements();
        imgStatus.setImgURl("addUI_badge_invalid.png");
        libraryUI.getLogic().checkManualAddGameStatus();

    }

    public void setAppendedName(String AppendedName) {
        this.AppendedName = AppendedName;
    }

    public Boolean checkGameExist(String gameName){

         try {
            foundGame = (String) db.getRowFlex("AuroraTable", new String[]{
                "FILE_NAME"}, "GAME_NAME='" + gameName
                    .replace("'", "''") + "'", "FILE_NAME")[0];
        } catch (Exception ex) {
            logger.error(ex);
            foundGame = null;
        }

         if(foundGame == null){
             return false;
         }else{
             return true;
         }
    }

    /**
     * Search from outside Class using specific String
     *
     * @param gameName the name of the Game you want to search for
     *
     */
    public AImagePane searchSpecificGame(String gameName) {
        try {
            foundGame = (String) db.getRowFlex("AuroraTable", new String[]{
                "FILE_NAME"}, "GAME_NAME='" + gameName
                    .replace("'", "''") + "'", "FILE_NAME")[0];
        } catch (Exception ex) {
            logger.error(ex);
            foundGame = null;
        }

         foundGameCover = null;

        //If not found show Placeholder and turn notification red
        if (foundGame == null) {
            pnlGameCoverPane.removeAll();
            notFound = new AImagePane("library_noGameFound.png", imgBlankCover
                    .getWidth(), imgBlankCover.getHeight());
            notFound.setPreferredSize(new Dimension( imgBlankCover
                    .getWidth(), imgBlankCover.getHeight()));
            pnlGameCoverPane.add(notFound);


            imgStatus.setImgURl("addUI_badge_invalid.png");
            libraryUI.getLogic().checkManualAddGameStatus();
            pnlGameCoverPane.repaint();
            pnlGameCoverPane.revalidate();

            return notFound;

            //Show the game Cover if a single database item is found
        } else{

            pnlGameCoverPane.removeAll();
            //Create the new GameCover object
            foundGameCover = new Game(libraryUI.getGridSplit(), ui, libraryUI
                    .getDashboardUI(), storage);
            try {
                foundGameCover.setCoverUrl(foundGame);
            } catch (MalformedURLException ex) {
                logger.error(ex);
            }
            foundGameCover.setCoverSize(imgBlankCover
                    .getWidth(), imgBlankCover.getHeight());
            foundGameCover.setGameName(gameName);

            pnlGameCoverPane.add(foundGameCover);
            try {
                foundGameCover.update();
                foundGameCover.removeOverlayUI();
            } catch (MalformedURLException ex) {
                logger.error(ex);
            }

            //Change notification
            imgStatus.setImgURl("addUI_badge_valid.png");
            libraryUI.getLogic().checkManualAddGameStatus();
            pnlGameCoverPane.repaint();
            pnlGameCoverPane.revalidate();

            return foundGameCover;
        }



    }

    public Game getFoundGameCover() {
        return foundGameCover;
    }

    private void searchGame() {

        //What Happends When The Length is zero
        if (AppendedName.length() <= 0 || txtSearch.getText()
                .length() == 0) {

            if (logger.isDebugEnabled()) {
                logger.debug("RESETTING PANE");
            }

            resetCover();
            pnlGameCoverPane.repaint();
            pnlGameCoverPane.revalidate();
        } else {
            listModel.removeAllElements();
            //Query the database

            try {
                if (logger.isDebugEnabled()) {
                    logger.debug("Searching for" + AppendedName.toString());
                }

                foundArray = db.searchAprox("AuroraTable", "FILE_NAME",
                        "GAME_NAME", AppendedName.toString());
            } catch (SQLException ex) {
                logger.error(ex);
            }
            try {
                //Get the first game name as a seperate string to show
                //in cover Art
                foundGame = (String) foundArray[0];
                if (logger.isDebugEnabled()) {
                    logger.debug(foundGame);
                }

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {

                        //Add rest of found items to the List to allow for selection of other games
                        for (int i = 0; i <= 10 && i < foundArray.length; i++) {
                            if (foundArray[i] != null) {
                                String gameItem = (String) foundArray[i];
                                if (!listModel.contains(gameItem
                                        .replace("-", " ").replace(".png", ""))) {
                                    listModel.addElement(gameItem
                                            .replace("-", " ").replace(".png",
                                            ""));

                                }
                            }
                        }

                    }
                });
            } catch (Exception ex) {
                foundGame = null;
            }



            //If Can't Get the game then show a Placeholder Image
            //and turn the notifier red
            if (foundGame == null) {

                pnlGameCoverPane.removeAll();
                notFound = new AImagePane("library_noGameFound.png",
                        imgBlankCover.getWidth(), imgBlankCover.getHeight());
                notFound.setPreferredSize(
                        new Dimension(notFound.getImageWidth(), notFound
                        .getImageHeight()));
                pnlGameCoverPane.add(notFound);
                foundGameCover = null;

                imgStatus.setImgURl("addUI_badge_invalid.png");
                libraryUI.getLogic().checkManualAddGameStatus();

                listModel.removeAllElements();

                pnlGameCoverPane.repaint();
                pnlGameCoverPane.revalidate();

            } else if (foundGame != null) {

                pnlGameCoverPane.removeAll();

                //Set up GameCover object with First Database item found
                foundGameCover = new Game(libraryUI.getGridSplit(), ui,
                        libraryUI.getDashboardUI(), storage);
                try {
                    foundGameCover.setCoverUrl(foundGame); //use seperate string
                } catch (MalformedURLException ex) {
                    logger.error(ex);
                }
                foundGameCover.setCoverSize(imgBlankCover
                        .getWidth(), imgBlankCover
                        .getHeight());
                foundGameCover.setGameName(foundGame.replace("-", " ").replace(
                        ".png", ""));

                pnlGameCoverPane.add(foundGameCover);
                //Show GameCover
                try {
                    foundGameCover.update();
                    foundGameCover.removeOverlayUI();
                } catch (MalformedURLException ex) {
                    logger.error(ex);
                }

                //Trun notifier Green
                imgStatus.setImgURl("addUI_badge_valid.png");
                libraryUI.getLogic().checkManualAddGameStatus();
                pnlGameCoverPane.repaint();
                pnlGameCoverPane.revalidate();
            }
        }
    }

    @Override
    public void run() {

        while (Thread.currentThread() == typeThread) {
            try {
                if (logger.isDebugEnabled()) {
                    logger.debug("WAITING FOR SEARCH");
                }

                Thread.sleep(sleep);
            } catch (InterruptedException ex) {
                logger.error(ex);
            }
            break;
        }
        searchGame();
        typeThread = null;
    }
}
