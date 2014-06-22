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

/**
 * Contains Objects "Storing" Data for a particular application
 * used to manage and store data generated by AuroraApp
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class AuroraStorage {
    private StoredLibrary storedLibrary;
    private StoredSettings storedSettings;
    private StoredProfile storedProfile;
    static final Logger logger = Logger.getLogger(AuroraStorage.class);


    public AuroraStorage(){
        this.storedLibrary = new StoredLibrary();
        this.storedProfile = new StoredProfile();
        this.storedSettings = new StoredSettings();
    }

    public StoredLibrary getStoredLibrary() {
        return storedLibrary;
    }

    public StoredProfile getStoredProfile() {
        return storedProfile;
    }

    public StoredSettings getStoredSettings() {
        return storedSettings;
    }

}
