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

import aurora.engine.V1.Logic.aSimpleDB;
import aurora.engine.V1.Logic.aStore;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sammy
 */
public class StoredProfile extends aStore implements Serializable {

    private String USER_NAME;
    private boolean RESOLUTION;
    private boolean SOUND_FX;
    private boolean BG_SOUND;

    public StoredProfile() {
    }

    @Override
    public void storeFromDatabase() {
    }

    @Override
    public void setUpDatabase(Boolean FirstTime, String Path) {
          try {
            super.db = new aSimpleDB("User", "Profile", FirstTime, Path);
        } catch (SQLException ex) {
            Logger.getLogger(StoredProfile.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    @Override
    public void storeToDatabase() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
