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

import org.apache.log4j.Logger;

import aurora.engine.V1.Logic.AStorage;

/**
 * Contains all of the Settings Attributes set by the user
 *
 * @version 0.1
 * @author Sammy
 */
public class StoredSettings extends AStorage {

    public int highres;
    public boolean surface;
    public boolean soundfx;
    public boolean bgsoundfx;
    static final Logger logger = Logger.getLogger(StoredSettings.class);

    @Override
    public void storeFromDatabase() {
    }

    @Override
    public void setUpDatabase(Boolean FirstTime, String Path) {

    }

    @Override
    public void storeToDatabase() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
