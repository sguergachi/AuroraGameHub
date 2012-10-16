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

import aurora.V1.core.screen_handler.GameLibraryHandler;
import aurora.V1.core.screen_ui.GameLibraryUI;
import aurora.engine.V1.UI.aImage;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * GridSearch Searches through Game Library
 *
 * @author Sammy
 * @version 0.2
 */
public class GridSearch {

    private char typed; // This is the character user types
    private String AppendedName = ""; //This is the concatination of all characters
    private AuroraCoreUI ui; // The is the UI object
    private GameLibraryUI libUI; // This is the Library View Object
    private aImage sideSearchImage; // The Image To The Side of The Grid that says: Search
    private GridManager SearchManager = null; // The Grid Manager For the Manager
    private ArrayList<Game> foundGameList; //List of all games found
    private boolean ClearedGrid; // ClearedGrid boolean
    private Game foundGame; //Current found game
    private Game GameOriginal; //Original GameCover for copying purpouses
    private FavListener FaveListener;
    private final GameLibraryHandler handler;
    private GridAnimation SearchGridAnimator;

    //////////////////////////
    ////////Constructor//////
    ////////////////////////
    public GridSearch(AuroraCoreUI ui, GameLibraryUI Obj_Library, GridManager manager) {
        this.ui = ui;
        this.libUI = Obj_Library;
        this.handler = libUI.getHandler();
        foundGameList = new ArrayList();
        this.ClearedGrid = false;
    }

    public void typedChar(char typedChar) {
        typed = typedChar; // Set variable to typeChar
        System.out.println("TYPED Character: " + String.valueOf(typed));

        //Append to Appended Name
        AppendedName = AppendedName.concat(String.valueOf(typed));

        System.out.println("Appended name: " + AppendedName);

        //clear library grid if not already clear
        if (!ClearedGrid) {
            clearGameGrid();
            System.out.println("Cleared Grid");
        }

        //Initiate Search Sequence
        searchGame();

    }

    public void removeChar(char typedChar) {
        typed = typedChar;



        //What Happends When The Length is zero
        if (AppendedName.length() == 0 || libUI.getSearchBar().getText().length() == 0) {
            try {
                restoreGrid(); //Restores to The original Library
                resetAppendedName(); // Resets AppendName variable
                libUI.getGameBack().revalidate(); // Refreshes the Grid.
            } catch (MalformedURLException ex) {
                Logger.getLogger(GridSearch.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //Remove ONE Character From End of Appended Name
        if (AppendedName.length() - 1 >= 0) {
            AppendedName = (String) AppendedName.subSequence(0, AppendedName.length() - 1);

        }

        System.out.println("Appended name: " + AppendedName);


        searchGame();

    }

    public void resetAppendedName() {
        AppendedName = "";
        foundGameList.removeAll(foundGameList);
    }

    /**
     * Search is split in two Exact Search and Lenient Search Exact search is
     * when the game typed is the same as a game in library Lenient search is
     * when the first few letters are typed and a search for games with those
     * letters occurs
     */
    private void searchGame() {
        //TODO Progress Wheel

        this.clearSearchGrid();

        System.out.println("Check EXACT Search: " + checkGameExistsInLibrary(AppendedName));

        //EXACT SEARCH
        if (checkGameExistsInLibrary(AppendedName)) { // Check if game is exact match to library game
            System.out.println("Performing Exact Search: " + AppendedName);

            //Remove what ever is in the search grid

            //Add the exact game found
            addFoundGame(AppendedName);
            //Display the game found
            try {
                displayGames();

            } catch (MalformedURLException ex) {
                Logger.getLogger(GridSearch.class.getName()).log(Level.SEVERE, null, ex);
            }




            //LENIENT SEARCH
        } else if (AppendedName.length() > 0) {

            System.out.println("Performing Lenient Search: " + AppendedName);

            //Search Each Grid
            for (int g = 0; g < libUI.getGridSplit().getArray().size(); g++) {
                //Search GameCover in specific Grid

                for (int j = 0; j < libUI.getGridSplit().getGrid(g).getArray().size(); j++) {

                    //check if placeholder object
                    if (libUI.getGridSplit().getGrid(g).getArray().get(j).getClass() != GamePlaceholder.class) {

                        //Convert each object in specific grid to GameCover Object
                        Game game = (Game) libUI.getGridSplit().getGrid(g).getArray().get(j);

                        int checkLength = ((AppendedName.length()));


                        if (checkLength < game.getName().length()) {
                            //String containing Substring of Games
                            String gameSub = game.getName().toLowerCase().substring(0, checkLength).toString();
                            //String Containing Substring of Text Typed
                            String appendedSub = AppendedName.substring(0, checkLength).toLowerCase();

                            System.out.println("!Substring of Appended is: " + appendedSub);
                            System.out.println("!Substring of Game is: " + gameSub);
                            System.out.println("!!Match Found?: " + gameSub.equals(appendedSub));

                            if (gameSub.equals(appendedSub)) {

                                //Check for duplicates
                                if (!foundGameList.contains(game)) {
                                    addFoundGame(game.getName());
                                    try {
                                        displayGames();

                                    } catch (MalformedURLException ex) {
                                        Logger.getLogger(GridSearch.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }

                        }

                    }
                }

            }

            //If Nothing Found clear grid
            if (foundGameList.isEmpty()) {
                this.clearSearchGrid();
                libUI.getGameBack().repaint();
            }

            //Clear grid
        } else if (AppendedName.length() != 0) {
            this.clearSearchGrid();
            libUI.getGameBack().repaint();

        }

        //add the place holders at the end
        SearchManager.addPlaceHolders(libUI.getSIZE_GameCoverWidth(), libUI.getSIZE_GameCoverHeight());
    }

    private boolean checkGameExistsInSearch(String name) {

        for (int i = 0; i < SearchManager.getArray().size(); i++) {
            for (int j = 0; j < SearchManager.getGrid(i).getArray().size(); j++) {
                if (!SearchManager.getGrid(i).getArray().get(j).getClass().getName().equals(GamePlaceholder.class.getName())) {
                    Game game = (Game) SearchManager.getGrid(i).getArray().get(j);
                    if (game.getName().equals(name)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //Check if GameCover with exact string exists in library
    private boolean checkGameExistsInLibrary(String name) {

        if (libUI.getGridSplit().findGameName(name)[0] != -1
                && libUI.getGridSplit().findGameName(name)[0] != -1) {
            return true;
        }
        return false;
    }

    private void addFoundGame(String name) {

        //MAKE A COPY OF THE PREVIEOUS GAME

        GameOriginal = (Game) (libUI.getGridSplit().getGrid(
                libUI.getGridSplit().findGameName(name)[0]).getArray().get(
                libUI.getGridSplit().findGameName(name)[1]));

        //Set Up New Cover
        foundGame = new Game(SearchManager, ui, GameOriginal.getBoxArtUrl());
        //manually copying it over
        foundGame.setFavorite(GameOriginal.isFavorite());
        foundGame.setGameName(GameOriginal.getName());
        foundGame.setCoverSize(GameOriginal.getWidth(), GameOriginal.getHeight());
        foundGame.setDashObj(GameOriginal.getDashObj());
        foundGame.setStorage(GameOriginal.getStorage());

        foundGameList.add(foundGame);
        System.out.println(foundGameList);

    }

    //Live Display of GameCover Found
    private void displayGames() throws MalformedURLException {


        //Go through Array and add to Grid
        for (int i = 0; i < foundGameList.size(); i++) {
            //Check for dupicates
            if (!checkGameExistsInSearch(foundGameList.get(i).getName())) {

                SearchManager.addGame(foundGameList.get(i)); // add to the grid.
                foundGameList.get(i).addFocusListener(handler.new searchLostFocusHandler(libUI));
                foundGameList.get(i).update();

                //Handle Remote Favorting to affect original game too
                foundGame = foundGameList.get(i);
                GameOriginal = (Game) (libUI.getGridSplit().getGrid(
                        libUI.getGridSplit().findGameName(foundGame.getGameName())[0]).getArray().get(
                        libUI.getGridSplit().findGameName(foundGame.getGameName())[1]));

                FaveListener = new FavListener(foundGame, GameOriginal);

            
                foundGameList.get(i).getFavoriteButton().addActionListener(FaveListener);

                libUI.getGameBack().revalidate();
                libUI.getGameBack().repaint();

                SearchManager.getGrid(0).revalidate();
                SearchManager.getGrid(0).repaint();
            }


        }
    }

    //Clears Search grid and foundGameList
    public void clearSearchGrid() {
        System.out.println("Clearing Search Grid");
        SearchManager.clearAllGrids();
        foundGameList.removeAll(foundGameList);
        System.out.println(foundGameList);

    }

    /**
     * Remove Library Grid, add new one for search
     */
    public void clearGameGrid() {

        //Tells Every body here that this method has already been executed
        ClearedGrid = true;


        //Remove Favorite Side Image
        libUI.getGameBack().remove(0);
        libUI.getGameBack().remove(libUI.getImgFavorite());
        //Add search Side image
        this.sideSearchImage = new aImage("Aurora_Search.png");
        libUI.getGameBack().add(sideSearchImage, BorderLayout.WEST);
        for (int i = 0; i < libUI.getGridSplit().getArray().size(); i++) {
            libUI.getGameBack().remove(libUI.getGridSplit().getGrid(i));

        }

        libUI.getGameBack().revalidate();
        libUI.getGameBack().repaint();

        setUpGrid();


    }

    public GridManager getGridManager() {
        return SearchManager;
    }

    /*
     * Restore Library Grid
     */
    public void restoreGrid() throws MalformedURLException {

        ClearedGrid = false;


        libUI.getGameBack().removeAll();

        libUI.getGameBack().add(libUI.getImgFavorite(), BorderLayout.WEST);
        libUI.getGameBack().add(libUI.getGridSplit().getGrid(0), BorderLayout.CENTER);
        libUI.getGameBack().add(libUI.getImgGameRight(), BorderLayout.EAST);

        libUI.getGameBack().revalidate();
        libUI.getGameBack().repaint();


    }

    //New Grid for Search
    private void setUpGrid() {
        this.SearchManager = new GridManager(2, 4, ui);
        SearchManager.initiateGrid(0);
        libUI.getGameBack().add(SearchManager.getGrid(0), BorderLayout.CENTER);

        this.SearchGridAnimator = new GridAnimation(SearchManager, libUI.getGameBack());


    }

    class FavListener implements ActionListener {

        private Game game;
        private Game original;

        public FavListener (Game game, Game original) {
            this.game = game;
            this.original = original;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (game.isFavorite()) {
                original.unfavorite();
            } else {
                original.setFavorite();
            }
        }
    }
}
