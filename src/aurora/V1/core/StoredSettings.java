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

<<<<<<< HEAD
=======
import static aurora.V1.core.StoredProfile.logger;
import aurora.engine.V1.Logic.ASimpleDB;
>>>>>>> origin/dev
import org.apache.log4j.Logger;

import aurora.engine.V1.Logic.AStorage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Contains all of the Settings Attributes set by the user
 *
 * @version 0.1
 * @author Sammy Guergachi
 */
public class StoredSettings extends AStorage {

    public int highres;

    public boolean surface;

    public boolean soundfx;

    public boolean bgsoundfx;
    static final Logger logger = Logger.getLogger(StoredSettings.class);

    static final Logger logger = Logger.getLogger(StoredSettings.class);

    private ArrayList<String> settingNames;

    private ArrayList<String> settingValues;

    private int index;

    private String settingName;

    private String settingValue;

    public StoredSettings() {


        settingNames = new ArrayList<String>();
        settingValues = new ArrayList<String>();



    }

    public ArrayList<String> getSettingNames() {
        return settingNames;
    }

    public ArrayList<String> getSettingValues() {
        return settingValues;
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

        if (db.checkTable("Settings")) {
            try {
                db.addTable("Settings", false);


                db.addColumn("Settings", "Setting_Name",
                        ASimpleDB.TYPE_STRING_IGNORECASE);
                db.setConstraint("Profile", "Game_Name", ASimpleDB.UNIQUE);

                db.addColumn("Settings", "Setting_Value",
                        ASimpleDB.TYPE_STRING_IGNORECASE);


            } catch (SQLException ex) {
                logger.error(ex);
            }

        }


    }

    public String getSettingValue(String SettingName) {
        String value = null;

        if (settingNames.indexOf(SettingName) != -1) {
            value = settingValues.get(settingNames.indexOf(SettingName));
        }

        return value;

    }

    public void saveSetting(String SettingName, String SettingValue) {

        settingName = SettingName;
        settingValue = SettingValue;

        index = settingNames.indexOf(settingName);


        if (index == -1) {
            settingNames.add(SettingName);
            settingValues.add(SettingValue);

            index = settingNames.size();

            storeToDatabase();
        } else {

            storeStateToDatabase();
            settingValues.set(index, SettingValue);
        }
    }

    @Override
    public void storeFromDatabase() {
        try {
            settingNames = getDatabaseArray("Settings", "Setting_Name");
            settingValues = getDatabaseArray("Settings", "Setting_Value");
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(StoredSettings.class.getName()).
                    log(Level.SEVERE, null, ex);
        }

        if (settingNames == null) {

            settingNames = new ArrayList<String>();
            settingValues = new ArrayList<String>();

        }


    }

    @Override
    public void storeToDatabase() {

        try {

            db.addRowFlex("Settings",
                    new String[]{"Setting_Name",
                "Setting_Value"
            },
                    ("'" + settingName + "'" + ","
                     + "'" + settingValue + "'"));

        } catch (SQLException ex) {
            logger.error(ex);
        }

    }

    public void storeStateToDatabase() {


        //-
        // Update metadata of game using selective updating
        // To use as little database resources as needed
        //-
        try {
            if (!settingValues.get(settingNames.indexOf(settingName)).equals(
                    settingValue)) {

                db.setColValue("Settings",
                        "Setting_Value",
                        "Setting_Name",
                        "'"
                        + settingName
                        + "'",
                        settingValue);

            }


            db.CloseConnection();
        } catch (SQLException ex) {
            logger.error(ex);
        }


    }
}
