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

import aurora.engine.V1.Logic.AFileManager;
import aurora.engine.V1.Logic.ASimpleDB;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Currently used to update the AuroraCoverDB with the GameDB.txt
 * Do not use unless updating AuroraDB
 *
 * @author Sammy Guergachi
 */
public final class DBUpdater {

    private ArrayList<String> AuroraGamesList;
    private ASimpleDB db;

    public static void main(String[] args) {
        new DBUpdater();
    }

    public DBUpdater() {
        try {
            updateAuroraCoverDB();
        } catch (SQLException ex) {
            Logger.getLogger(DBUpdater.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateAuroraCoverDB() throws SQLException {
        try {
            db = new ASimpleDB("AuroraDB", "AuroraTable", true);
            db.addColumn("AuroraTable", "Game_Name", ASimpleDB.TYPE_STRING_IGNORECASE);
            db.addColumn("AuroraTable", "File_Name", ASimpleDB.TYPE_STRING_IGNORECASE);
        } catch (SQLException ex) {
            Logger.getLogger(DBUpdater.class.getName()).log(Level.SEVERE, null, ex);
        }

        AFileManager fileMngr;
        try {

            fileMngr = new AFileManager(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().toString() + "aurora/V1/resources/", true);
            AuroraGamesList = fileMngr.readFile("GameDB.txt");


        } catch (URISyntaxException ex) {
            Logger.getLogger(DBUpdater.class.getName()).log(Level.SEVERE, null, ex);
        }

        int count = 0;
        System.out.println(AuroraGamesList.size());
        System.out.println(AuroraGamesList.get(AuroraGamesList.size() - 1));
        while (count < AuroraGamesList.size() - 1) {
            AuroraGamesList.set(count, AuroraGamesList.get(count).replace("'", "''"));
            String file1 = AuroraGamesList.get(count).replace(" - ", " ");
            String file = file1.replace(" ", "-") + ".png"; //Convert to URL path

            db.addRow("AuroraTable", Integer.toString(count) + ",'" + AuroraGamesList.get(count).replace(" - ", " ") + "','" + file + "'");
            count++;
        }



    }
}
