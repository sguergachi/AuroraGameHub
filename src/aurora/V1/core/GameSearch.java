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
import java.net.MalformedURLException;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
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

    private String AppendedName = ""; //This is the concatenation of all characters

    private String foundGame;

    private static Game foundGameCover;

    private Thread typeThread;

    private int sleep;

    private Object[] foundArray;

    private final AuroraStorage storage;

    static final Logger logger = Logger.getLogger(GameSearch.class);

    private AImagePane imgBlankCover;

    private AImagePane pnlGameCoverPane;

    private DefaultListModel listModel;

    private AImage imgStatus;

    private JTextField txtSearch;

    private Game notFoundCover;

    private boolean canEditCover = true;

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
        if (logger.isDebugEnabled()) {
            logger.debug("Appended name: " + AppendedName);
        }

        //Remove ONE Character From End of Appended Name
        if (AppendedName.length() <= 0) {

            resetCover();
            searchGame();

        } //Start search only when more than 1 character is typed
        else if (AppendedName.length() > 0) {

            //Delay to allow for typing
            if (AppendedName.length() == 1) {
                sleep = 300;
            } else {
                sleep = 260;
            }
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

    public Boolean checkGameExist(String gameName) {

        try {
            foundGame = (String) db.getRowFlex("AuroraTable", new String[]{
                "FILE_NAME"}, "GAME_NAME='" + gameName
                    .replace("'", "''") + "'", "FILE_NAME")[0];
        } catch (Exception ex) {
            logger.error(ex);
            foundGame = null;
        }

        if (foundGame == null) {
            return false;
        } else {
            return true;
        }
    }

    public AImagePane getSpecificGame(String gameImageName) {

        //If not found show Placeholder and turn notification red
        if (gameImageName == null) {

            pnlGameCoverPane.removeAll();
            //Create the new GameCover object
            notFoundCover = new Game(libraryUI.getGridSplit(), ui, libraryUI
                    .getDashboardUI(), storage);
            try {
                notFoundCover.setCoverUrl("library_noGameFound.png");
            } catch (MalformedURLException ex) {
                logger.error(ex);
            }
            notFoundCover.setCoverSize(imgBlankCover
                    .getImageWidth(), imgBlankCover.getImageHeight());

            pnlGameCoverPane.add(notFoundCover);
            try {
                notFoundCover.update();
                notFoundCover.removeOverlayUI();

                if (canEditCover) {
                    notFoundCover.enableEditCoverOverlay();
                    notFoundCover.getBtnAddCustomOverlay().addActionListener(
                            libraryUI.getHandler().new GameCoverEditListner(
                                    notFoundCover));
                }
            } catch (MalformedURLException ex) {
                logger.error(ex);
            }

            //Change notification
            imgStatus.setImgURl("addUI_badge_invalid.png");
            pnlGameCoverPane.repaint();
            pnlGameCoverPane.revalidate();
            notFoundCover.revalidate();

            return notFoundCover;

            //Show the game Cover if a single database item is found
        } else {

            pnlGameCoverPane.removeAll();
            //Create the new GameCover object
            foundGameCover = new Game(libraryUI.getGridSplit(), ui, libraryUI
                    .getDashboardUI(), storage);
            try {
                foundGameCover.setCoverUrl(gameImageName);
            } catch (MalformedURLException ex) {
                logger.error(ex);
            }
            foundGameCover.setCoverSize(imgBlankCover
                    .getImageWidth(), imgBlankCover.getImageHeight());

            pnlGameCoverPane.add(foundGameCover);
            try {
                foundGameCover.update();
                foundGameCover.removeOverlayUI();

                // Enable editing of cover art
                if (canEditCover) {
                    foundGameCover.enableEditCoverOverlay();
                    foundGameCover.getBtnAddCustomOverlay().addActionListener(
                            libraryUI.getHandler().new GameCoverEditListner(
                                    foundGameCover));
                }
            } catch (MalformedURLException ex) {
                logger.error(ex);
            }

            //Change notification
            imgStatus.setImgURl("addUI_badge_valid.png");
            pnlGameCoverPane.repaint();
            pnlGameCoverPane.revalidate();

            return foundGameCover;
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
            //Create the new GameCover object
            notFoundCover = new Game(libraryUI.getGridSplit(), ui, libraryUI
                    .getDashboardUI(), storage);
            try {
                notFoundCover.setCoverUrl("library_noGameFound.png");
            } catch (MalformedURLException ex) {
                logger.error(ex);
            }
            notFoundCover.setCoverSize(imgBlankCover
                    .getImageWidth(), imgBlankCover.getImageHeight());

            notFoundCover.setGameName(gameName);

            pnlGameCoverPane.add(notFoundCover);
            try {
                notFoundCover.update();
                notFoundCover.removeOverlayUI();

                // Enable editing of cover art
                if (canEditCover) {
                    notFoundCover.enableEditCoverOverlay();
                    notFoundCover.getBtnAddCustomOverlay().addActionListener(
                            libraryUI.getHandler().new GameCoverEditListner(
                                    notFoundCover));
                }
            } catch (MalformedURLException ex) {
                logger.error(ex);
            }

            //Change notification
            imgStatus.setImgURl("addUI_badge_invalid.png");
            pnlGameCoverPane.repaint();
            pnlGameCoverPane.revalidate();
            notFoundCover.revalidate();

            return notFoundCover;

            //Show the game Cover if a single database item is found
        } else {

            pnlGameCoverPane.removeAll();
            // Create the new GameCover object
            foundGameCover = new Game(libraryUI.getGridSplit(), ui, libraryUI
                    .getDashboardUI(), storage);
            try {
                foundGameCover.setCoverUrl(foundGame);
            } catch (MalformedURLException ex) {
                logger.error(ex);
            }
            foundGameCover.setCoverSize(imgBlankCover
                    .getImageWidth(), imgBlankCover.getImageHeight());

            foundGameCover.setGameName(gameName);

            pnlGameCoverPane.add(foundGameCover);
            try {
                foundGameCover.update();
                foundGameCover.removeOverlayUI();

                // Enable editing of cover art
                if (canEditCover) {
                    foundGameCover.enableEditCoverOverlay();
                    foundGameCover.getBtnAddCustomOverlay().addActionListener(
                            libraryUI.getHandler().new GameCoverEditListner(
                                    foundGameCover));
                }
            } catch (MalformedURLException ex) {
                logger.error(ex);
            }

            //Change notification
            imgStatus.setImgURl("addUI_badge_valid.png");
            pnlGameCoverPane.repaint();
            pnlGameCoverPane.revalidate();
            foundGameCover.revalidate();

            return foundGameCover;
        }

    }

    /**
     * Searches for games similar to the selected one.
     * Returns an array
     * [0] contains possible game name
     * [1] contains possible game names game path
     * <p>
     * @param gameName
     *                 <p>
     * @return
     */
    public String[] searchSimilarGame(String gameName) {

        String possibleGameName = null;
        String possibleGameImageName = null;
        //TODO use flex query to make multiple searches to find a possible match
        try {

            String tableName = "AuroraTable";
            String columnCSV = "FILE_NAME";

            int attempt = 0;
            String savedGameName = null;
            while (attempt >= 0) {

                String whereQuery = "GAME_NAME='" + gameName.replace("'", "''")
                                    + "'";
                ResultSet rs = null;
                rs = db.flexQuery("SELECT " + columnCSV + " FROM "
                                  + tableName + " WHERE " + whereQuery);

                // Check if found a match
                if (rs.getRow() > 0) {
                    Array a = rs.getArray(columnCSV);
                    Object[] array = (Object[]) a.getArray();
                    possibleGameName = gameName;
                    possibleGameImageName = (String) array[0];
                    break;
                } else { // If no match found, change game name a little

                    switch (attempt) {
                        case 0: // First attempt: remove garbage characters
                            if (gameName.matches("^.*[©®™°²³º¼½¾].*$")) {
                                gameName = gameName.replaceAll("[©®™°²³º¼½¾]",
                                                               "");
                                break;
                            }
                        case 1: // Second attempt: add spaces between Letters
                            savedGameName = gameName;
                            gameName = addSpaces(gameName);
                            break;
                        case 2:
                            gameName = savedGameName;
                            possibleGameName = gameName;
                            possibleGameImageName = reductiveSearch(gameName);
                            attempt = -2;
                            break;
                        default:
                            attempt = -2;
                            break;

                    }
                }

                attempt++;
            }

        } catch (SQLException ex) {
            logger.error(ex);
            foundGame = null;
        }

        String[] returnArray = new String[2];

        returnArray[0] = possibleGameName;
        returnArray[1] = possibleGameImageName;

        if (possibleGameName == null) {
            returnArray = null;
        }
        db.CloseConnection();
        return returnArray;
    }

    private String addSpaces(String text) {
        String tempString = text;
        String modifiedText = text;

        while (tempString.length() > 1) {
            int spaceIndex = 0; // location of spaces

            Character c = tempString.charAt(tempString.length() - 1);

            // Check if Upper case is detected
            if (Character.isUpperCase(c)
                && tempString.length() - 2 > 0) {

                //Afterward check if previous char is lowercase
                Character c2 = tempString.charAt(tempString.length() - 2);

                if (Character.isLowerCase(c2)) {
                    // Need to add a space in between them.
                    spaceIndex = tempString.length() - 1;

                    // Add space
                    modifiedText = modifiedText.substring(0, spaceIndex)
                                   + " " + modifiedText.substring(spaceIndex,
                                                                  modifiedText
                            .length());

                }

            }

            // Remove one character each time
            tempString = tempString.substring(0, tempString.length() - 1);
        }

        return modifiedText.trim();

    }

    //TODO use db aprox search while removing a letter each time untill found.
    private String reductiveSearch(String gameName) {

        String tableName = "AuroraTable";
        String column = "FILE_NAME";
        String whereQuery = "GAME_NAME";
        String tempName = gameName;
        String gameImagePath = null;
        for (int i = 0; i < gameName.length(); i++) {

            ResultSet rs = null;
            try {
                rs = db.flexQuery("SELECT "
                                  + column
                                  + " FROM "
                                  + tableName
                                  + " WHERE "
                                  + whereQuery
                                  + " LIKE '%"
                                  + tempName
                        .replace("'", "''") + "%'");

                // Check if found a match
                if (rs.getRow() > 0) {
                    Array a = rs.getArray(column);
                    Object[] array = (Object[]) a.getArray();
                    gameImagePath = (String) array[0];
                    break;
                } else {

                    tempName = tempName.substring(0, tempName.length() - 1);
                }
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(GameSearch.class.getName())
                        .log(Level.SEVERE, null, ex);
            }

        }

        return gameImagePath;

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
                //Create the new GameCover object
                notFoundCover = new Game(libraryUI.getGridSplit(), ui, libraryUI
                        .getDashboardUI(), storage);
                try {
                    notFoundCover.setCoverUrl("library_noGameFound.png");
                } catch (MalformedURLException ex) {
                    logger.error(ex);
                }
                notFoundCover.setCoverSize(imgBlankCover
                        .getImageWidth(), imgBlankCover.getImageHeight());

                pnlGameCoverPane.add(notFoundCover);

                try {
                    notFoundCover.update();
                    notFoundCover.removeOverlayUI();

                    //Allow for custom editing cover art
                    if (canEditCover) {
                        notFoundCover.enableEditCoverOverlay();
                        notFoundCover.getBtnAddCustomOverlay()
                                .addActionListener(
                                        libraryUI.getHandler().new GameCoverEditListner(
                                                notFoundCover));
                    }
                } catch (MalformedURLException ex) {
                    logger.error(ex);
                }

                //Change notification
                imgStatus.setImgURl("addUI_badge_invalid.png");
                pnlGameCoverPane.repaint();
                pnlGameCoverPane.revalidate();
                notFoundCover.revalidate();

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
                        .getImageWidth(), imgBlankCover
                        .getImageHeight());
                foundGameCover.setGameName(foundGame.replace("-", " ").replace(
                        ".png", ""));

                pnlGameCoverPane.add(foundGameCover);
                //Show GameCover
                try {
                    foundGameCover.update();
                    foundGameCover.removeOverlayUI();

                    //Enable the ability to edit cover art
                    if (canEditCover) {
                        foundGameCover.enableEditCoverOverlay();
                        foundGameCover.getBtnAddCustomOverlay()
                                .addActionListener(
                                        libraryUI.getHandler().new GameCoverEditListner(
                                                foundGameCover));
                    }
                } catch (MalformedURLException ex) {
                    logger.error(ex);
                }

                //Turn notifier Green
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

    public String getAppendedName() {
        return AppendedName;
    }

    public Game getFoundGameCover() {
        return foundGameCover;
    }

    public Game getCurrentlySearchedGame() {
        return (Game) pnlGameCoverPane.getComponent(0);
    }

    public void setCanEditCover(boolean canEditCover) {
        this.canEditCover = canEditCover;
    }

}
