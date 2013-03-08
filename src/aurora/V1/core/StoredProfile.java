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
                        ASimpleDB.TYPE_STRING_IGNORECASE);

                db.addColumn("Profile", "Last_Time", ASimpleDB.TYPE_BOOLEAN);
            } catch (SQLException ex) {
                logger.error(ex);
            }

        }

    }

    @Override
    public void storeFromDatabase() {

        GameNames = getDatabaseArray("Profile", "Game_Name");
        GameTypes = getDatabaseArray("Profile", "Game_Types");
        TotalTimes = getDatabaseArray("Profile", "Total_Time");
        OccurrenceTimes = getDatabaseArray("Profile", "Occurence_Time");
        LastTimes = getDatabaseArray("Profile", "Last_Time");



    }


    @Override
    public void storeToDatabase() {

        try {

            db.addRowFlex("Library",
                    new String[]{"Game_Name",
                                "Game_Types",
                                "Total_Time",
                                "Occurence_Time",
                                "Last_Time"
                                },
                    ("'" + GameName + "'" + ","
                    + "'" + GameType + "'" + ","
                    + "'" + TotalTime + "'" + ","
                    + "'" + OccurrenceTime + "'" + ","
                    + "'" + LastTime + "'"));

        } catch (SQLException ex) {
            logger.error(ex);
        }
        //Clear for next set of Games
        GameName = "";
        GameType = "";
        TotalTime = "";
        OccurrenceTime = 0;
        LastTime = "";
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
