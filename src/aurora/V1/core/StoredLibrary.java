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
import aurora.engine.V1.Logic.AStorage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Contains all of the Games Currently In The Library
 *
 * @author Sammy
 */
public class StoredLibrary extends AStorage {

    private GameLibraryUI Library;
    private ArrayList<String> GameNames;
    private ArrayList<String> GamePaths;
    private ArrayList<String> BoxArtPaths;
    private ArrayList<Boolean> Favestates;
    private String GameName;
    private String GamePath;
    private String BoxArtPath;
    private Boolean Favestate = false;

    public StoredLibrary() {
    }

    @Override
    public void setUpDatabase(Boolean FirstTime, String Path) {
        GameNames   = new ArrayList();
        GamePaths   = new ArrayList();
        BoxArtPaths = new ArrayList();
        Favestates  = new ArrayList();

        try {
            super.db = new ASimpleDB("Games", Path);
        } catch (SQLException ex) {
            Logger.getLogger(StoredLibrary.class.getName()).log(Level.SEVERE, null, ex);
        }

        //If this is the first time we are setting up the database
        //Then create the columns where the data will reside
        if (FirstTime) {

            try {
                db.addTable("Library", false);
                db.addColumn("Library", "Game_Name", ASimpleDB.TYPE_STRING_IGNORECASE);
                db.setConstraint("Library", "Game_Name", ASimpleDB.UNIQUE);
                db.addColumn("Library", "Executable_Path", ASimpleDB.TYPE_STRING_IGNORECASE);
                db.addColumn("Library", "BoxArt_Path", ASimpleDB.TYPE_STRING_IGNORECASE);
                db.addColumn("Library", "FavState", ASimpleDB.TYPE_BOOLEAN);
            } catch (SQLException ex) {
                Logger.getLogger(StoredLibrary.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    /*
     * Do not use
     */
    public void SaveLibrary(GameLibraryUI library) {


        this.Library = library;
        if (Library != null) {

            for (int a = 0; a < Library.getGridSplit().getArray().size(); a++) {
                for (int b = 0; b < Library.getGridSplit().getGrid(a).getArray().size(); b++) {

                    Game game = (Game) Library.getGridSplit().getGrid(a).getArray().get(b);
                    GameName   = game.getGameName();
                    GamePath   = game.getGamePath();
                    BoxArtPath = game.getBoxArtUrl();
                    Favestate  = game.isFavorite();

                }
            }

        }


    }

    /*
     * Update the state of a specific game uses GameName as identifier of game
     * to update
     */
    public void SaveFavState(Game game) {

        if (game != null) {

            Favestate = game.isFavorite();
            GameName  = game.getGameName();
            GameName  = game.getGameName().replace("'", "''");
            System.out.println("Saved Favourite State to " + Favestate);
            Favestates.add(Favestate);

            storeStateToDatabase();

        }
    }

    /*
     * Saves a specific game to database Handles Appostrophe
     */
    public void SaveGame(Game game) {

        if (game != null) {
            System.out.println("Saving To Library");
            GameName   = game.getGameName().replace("'", "''");
            GamePath   = game.getGamePath();
            BoxArtPath = game.getBoxArtUrl().replace("'", "''");


            System.out.println("Saved Game ");
            System.out.println(game.getGameName() + " " + game.getBoxArtUrl() + " " + game.getGamePath());


            GameNames.add(GameName);
            GamePaths.add(GamePath);
            BoxArtPaths.add(BoxArtPath);
            Favestate = false;

            storeToDatabase();

        }


    }

    /*
     * Removes the game from the stored library
     */
    public void removeGame(Game game) {
    	String gameName = game.getGameName().replace("'", "''");
    	GameNames.remove(gameName);
    	GamePaths.remove(game.getGamePath());
    	BoxArtPaths.remove(game.getBoxArtUrl());

        removeFromDatabase(gameName);
    }

    /**
     * Store Everything from the database into this storage
     *
     * @param path
     */
    @Override
    public void storeFromDatabase() {

        Favestates  = getDatabaseArray("Library", "FavState");
        GameNames   = getDatabaseArray("Library", "Game_Name");
        GamePaths   = getDatabaseArray("Library", "Executable_Path");
        BoxArtPaths = getDatabaseArray("Library", "BoxArt_Path");

    }

    /*
     * Changes the state of a game by updating a row in the database
     */
    public void storeStateToDatabase() {

        try {

            db.setColValue("Library", "FavState", "Game_Name", "'" + GameName + "'", Favestate);
            db.CloseConnection();
        } catch (SQLException ex) {
            Logger.getLogger(StoredLibrary.class.getName()).log(Level.SEVERE, null, ex);
        }

        GameName  = "";
        Favestate = false;
    }

    /*
     * Stores game to database by creating a new row with gameCover
     * characteristics
     */
    @Override
    public void storeToDatabase() {

        try {

            db.addRowFlex("Library", new String[]{"Game_Name", "Executable_Path", "BoxArt_Path", "FavState"},
                    ("'" + GameName + "'" + "," + "'" + GamePath + "'" + "," + "'" + BoxArtPath + "'" + "," + "'" + Favestate + "'"));

        } catch (SQLException ex) {
            Logger.getLogger(StoredLibrary.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Clear for next set of Games
        GameName   = "";
        GamePath   = "";
        BoxArtPath = "";


    }

    /*
     * Remove a game from the library database
     */
    private void removeFromDatabase(String name) {
    	try {
    		db.deleteRowFlex("Library", "Game_Name=" + "'" + name + "'");

    	} catch (SQLException ex) {
    		Logger.getLogger(StoredLibrary.class.getName()).log(Level.SEVERE, null, ex);
    	}
    }

    /*
     * Search for the name in the GameNames array
     */
    public boolean search(String name) {
    	if (GameNames.contains(name)) {
    		System.out.println(name + " was found");
    		return true;
    	} else {
    		return false;
    	}
    }

    public ArrayList<String> getBoxArtPath() {
        return BoxArtPaths;
    }

    public ArrayList<String> getGameNames() {
        return GameNames;
    }

    public ArrayList<Boolean> getFaveStates() {
        return Favestates;
    }

    public ArrayList<String> getGamePath() {
        return GamePaths;
    }
}
