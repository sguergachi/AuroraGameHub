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

import static aurora.V1.core.StoredLibrary.logger;
import aurora.engine.V1.Logic.ASimpleDB;
import aurora.engine.V1.Logic.AStorage;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
<<<<<<< HEAD
=======
import java.util.logging.Level;
>>>>>>> origin/dev

import org.apache.log4j.Logger;

/**
 *
 * @author Sammy
 */
public class StoredProfile extends AStorage implements Serializable {

    private String USER_NAME;

    private boolean RESOLUTION;

    private boolean SOUND_FX;

    private boolean BG_SOUND;

    static final Logger logger = Logger.getLogger(StoredProfile.class);

    private ArrayList<String> GameNames;

    private ArrayList<String> GameTypes;

    private ArrayList<String> LastTimes;

    private ArrayList<String> TotalTimes;

    private ArrayList<Integer> OccurrenceTimes;

    private String GameName;

    private String GameType;

    private String LastTime;

    private String TotalTime;

    private int OccurrenceTime;

    public StoredProfile() {
        GameNames = new ArrayList<String>();
        GameTypes = new ArrayList<String>();
        TotalTimes = new ArrayList<String>();
        OccurrenceTimes = new ArrayList<Integer>();
        LastTimes = new ArrayList<String>();
<<<<<<< HEAD
    }

    @Override
    public void setUpDatabase(Boolean FirstTime, String Path) {


        try {
            super.db = new ASimpleDB("User", Path);
        } catch (SQLException ex) {
            logger.error(ex);
        }


        //-
        // If this is the first time we are setting up the database
        // Then create the columns where the data will reside
        //-

        if (FirstTime) {

            try {
                db.addTable("Profile", false);

                db.addColumn("Profile", "Game_Name",
                        ASimpleDB.TYPE_STRING_IGNORECASE);
                db.setConstraint("Profile", "Game_Name", ASimpleDB.UNIQUE);

                db.addColumn("Profile", "Game_Type",
                        ASimpleDB.TYPE_STRING_IGNORECASE);

                db.addColumn("Profile", "Total_Time",
                        ASimpleDB.TYPE_STRING_IGNORECASE);

                db.addColumn("Profile", "Occurence_Time",
                        ASimpleDB.TYPE_INTEGER);

                db.addColumn("Profile", "Last_Time",
                        ASimpleDB.TYPE_STRING_IGNORECASE);
            } catch (SQLException ex) {
                logger.error(ex);
            }

        }

    }

    /*
     * Saves a specific game to database Handles Appostrophe
     */
    public void SaveGameMetadata(Game game) {

        if (game != null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Saving Metadata...");

            }

            GameName = game.getGameName().replace("'", "''");
            GameType = game.getGameType();
            TotalTime = game.getTotalTimePlayed();
            OccurrenceTime = game.getOccurencesPlayed();
            LastTime = game.getLastPlayed();

            if (!GameNames.contains(GameName)) {
                storeToDatabase();
            } else {
                storeStateToDatabase();
            }


            GameNames.add(GameName);
            GameTypes.add(GameType);
            TotalTimes.add(TotalTime);
            OccurrenceTimes.add(OccurrenceTime);
            LastTimes.add(LastTime);


            //Clear for next set of Games
            GameName = "";
            GameType = "";
            TotalTime = "";
            OccurrenceTime = 0;
            LastTime = "";

            if (logger.isDebugEnabled()) {
                logger.debug("Saved Game Metadata");
                logger.debug(game.getGameName() + " " + game.getBoxArtUrl()
                             + " " + game.getGamePath());
            }

        }

    }

    /*
     * Removes the game from the stored library
     */
    public void removeGameMetadata(Game game) {
        String gameName = game.getGameName().replace("'", "''");

        GameNames.remove(gameName);
        GameTypes.remove(game.getGameType());
        TotalTimes.remove(game.getTotalTimePlayed());
        if (!OccurrenceTimes.contains(game.getOccurencesPlayed())) {
            OccurrenceTimes.remove(game.getOccurencesPlayed());
        }
        LastTimes.remove(game.getLastPlayed());

        removeFromDatabase(gameName);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void storeFromDatabase() {

        GameNames = getDatabaseArray("Profile", "Game_Name");
        GameTypes = getDatabaseArray("Profile", "Game_Type");
        TotalTimes = getDatabaseArray("Profile", "Total_Time");
        OccurrenceTimes = getDatabaseArray("Profile", "Occurence_Time");
        LastTimes = getDatabaseArray("Profile", "Last_Time");

=======
    }

    @Override
    public void setUpDatabase(Boolean FirstTime, String Path) {


        try {
            super.db = new ASimpleDB("User", Path);
        } catch (SQLException ex) {
            logger.error(ex);
        }


        //-
        // If this is the first time we are setting up the database
        // Then create the columns where the data will reside
        //-

        if (FirstTime) {

            try {
                db.addTable("Profile", false);

                db.addColumn("Profile", "Game_Name",
                        ASimpleDB.TYPE_STRING_IGNORECASE);
                db.setConstraint("Profile", "Game_Name", ASimpleDB.UNIQUE);

                db.addColumn("Profile", "Game_Type",
                        ASimpleDB.TYPE_STRING_IGNORECASE);

                db.addColumn("Profile", "Total_Time",
                        ASimpleDB.TYPE_STRING_IGNORECASE);

                db.addColumn("Profile", "Occurence_Time",
                        ASimpleDB.TYPE_INTEGER);

                db.addColumn("Profile", "Last_Time",
                        ASimpleDB.TYPE_STRING_IGNORECASE);
            } catch (SQLException ex) {
                logger.error(ex);
            }

        }

    }

    /*
     * Saves a specific game to database Handles Appostrophe
     */
    public void saveGameMetadata(Game game) {

        if (game != null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Saving Metadata...");

            }

            GameName = game.getGameName().replace("'", "''");
            GameType = game.getGameType();
            TotalTime = game.getTotalTimePlayed();
            OccurrenceTime = game.getOccurencesPlayed();
            LastTime = game.getLastPlayed();

            if (!GameNames.contains(GameName)) {
                storeToDatabase();
            } else {
                storeStateToDatabase();
            }


            GameNames.add(GameName);
            GameTypes.add(GameType);
            TotalTimes.add(TotalTime);
            OccurrenceTimes.add(OccurrenceTime);
            LastTimes.add(LastTime);


            //Clear for next set of Games
            GameName = "";
            GameType = "";
            TotalTime = "";
            OccurrenceTime = 0;
            LastTime = "";

            if (logger.isDebugEnabled()) {
                logger.debug("Saved Game Metadata");
                logger.debug(game.getGameName() + " " + game.getBoxArtUrl()
                             + " " + game.getGamePath());
            }

        }

    }

    /*
     * Removes the game from the stored library
     */
    public void removeGameMetadata(Game game) {
        String gameName = game.getGameName().replace("'", "''");

        GameNames.remove(gameName);
        GameTypes.remove(game.getGameType());
        TotalTimes.remove(game.getTotalTimePlayed());
        if (!OccurrenceTimes.contains(game.getOccurencesPlayed())) {
            OccurrenceTimes.remove(game.getOccurencesPlayed());
        }
        LastTimes.remove(game.getLastPlayed());

        removeFromDatabase(gameName);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void storeFromDatabase() {
        try {
            GameNames = getDatabaseArray("Profile", "Game_Name");
            GameTypes = getDatabaseArray("Profile", "Game_Type");
            TotalTimes = getDatabaseArray("Profile", "Total_Time");
            OccurrenceTimes = getDatabaseArray("Profile", "Occurence_Time");
            LastTimes = getDatabaseArray("Profile", "Last_Time");
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(StoredProfile.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
>>>>>>> origin/dev
        if (GameNames == null) {

            GameNames = new ArrayList<String>();
            GameTypes = new ArrayList<String>();
            TotalTimes = new ArrayList<String>();
            OccurrenceTimes = new ArrayList<Integer>();
            LastTimes = new ArrayList<String>();
        }

<<<<<<< HEAD
=======

>>>>>>> origin/dev
    }

    public void storeStateToDatabase() {


        //-
        // Update metadata of game using selective updating
        // To use as little database resources as needed
        //-
        try {
<<<<<<< HEAD
            if (!GameTypes.get(GameNames.indexOf(GameName)).equals(GameType)) {
=======
            if (GameTypes.get(GameNames.indexOf(GameName)) != null && !GameTypes
                    .get(GameNames.indexOf(GameName)).equals(GameType)) {
>>>>>>> origin/dev
                db.setColValue("Profile",
                        "Game_Type",
                        "Game_Name",
                        "'"
                        + GameName
                        + "'",
                        GameType);

            }
            if (!TotalTimes.get(GameNames.indexOf(GameName)).equals(TotalTime)) {
                db.setColValue("Profile",
                        "Total_Time",
                        "Game_Name",
                        "'"
                        + GameName
                        + "'",
                        TotalTime);
            }
            if (OccurrenceTimes.get(GameNames.indexOf(GameName))
                != OccurrenceTime) {
                db.setColValue("Profile",
                        "Occurence_Time",
                        "Game_Name",
                        "'"
                        + GameName
                        + "'", OccurrenceTime);
            }
            if (!LastTimes.get(GameNames.indexOf(GameName)).equals(LastTime)) {
                db.setColValue("Profile",
                        "Last_Time",
                        "Game_Name",
                        "'"
                        + GameName
                        + "'",
                        LastTime);
            }

            db.CloseConnection();
        } catch (SQLException ex) {
            logger.error(ex);
        }


    }

    @Override
    public void storeToDatabase() {

        try {

            db.addRowFlex("Profile",
                    new String[]{"Game_Name",
<<<<<<< HEAD
                        "Game_Type",
                        "Total_Time",
                        "Occurence_Time",
                        "Last_Time"
                    },
=======
                "Game_Type",
                "Total_Time",
                "Occurence_Time",
                "Last_Time"
            },
>>>>>>> origin/dev
                    ("'" + GameName + "'" + ","
                     + "'" + GameType + "'" + ","
                     + "'" + TotalTime + "'" + ","
                     + "'" + OccurrenceTime + "'" + ","
                     + "'" + LastTime + "'"));

        } catch (SQLException ex) {
            logger.error(ex);
        }

    }

    /*
     * Remove a game from the library database
     */
    private void removeFromDatabase(String name) {
        try {
            db.deleteRowFlex("Profile", "Game_Name=" + "'" + name + "'");

        } catch (SQLException ex) {
            logger.error(ex);
        }
    }

    public ArrayList<String> getGameNames() {
        return GameNames;
    }

    public ArrayList<String> getGameTypes() {
        return GameTypes;
    }

    public ArrayList<String> getLastTimes() {
        return LastTimes;
    }

    public ArrayList<String> getTotalTimes() {
        return TotalTimes;
    }

    public ArrayList<Integer> getOccurrenceTimes() {
        return OccurrenceTimes;
    }

    public boolean isBG_SOUND() {
        return BG_SOUND;
    }

    public void setBG_SOUND(boolean BG_SOUND) {
        this.BG_SOUND = BG_SOUND;
    }

    public boolean isRESOLUTION() {
        return RESOLUTION;
    }

    public void setRESOLUTION(boolean RESOLUTION) {
        this.RESOLUTION = RESOLUTION;
    }

    public boolean isSOUND_FX() {
        return SOUND_FX;
    }

    public void setSOUND_FX(boolean SOUND_FX) {
        this.SOUND_FX = SOUND_FX;
    }

    public String getUSER_NAME() {
        return USER_NAME;
    }

    public void setUSER_NAME(String USER_NAME) {
        this.USER_NAME = USER_NAME;
    }
}
