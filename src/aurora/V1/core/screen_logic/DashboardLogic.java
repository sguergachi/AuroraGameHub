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
package aurora.V1.core.screen_logic;

import aurora.V1.core.AuroraStorage;
import aurora.V1.core.Game;
import aurora.V1.core.screen_ui.DashboardUI;
import aurora.engine.V1.UI.aImagePane;
import java.util.Random;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class DashboardLogic {

    private final DashboardUI dashboardUI;
    private Game randomGame;
    private aImagePane icon;

    /**
     *
     * @param dashboardUi DashboardUI instance
     */
    public DashboardLogic(final DashboardUI dashboardUi) {

        this.dashboardUI = dashboardUi;

    }

    /**
     * .-----------------------------------------------------------------------.
     * | getLibraryIcon()
     * .-----------------------------------------------------------------------.
     * |
     * | This method tries to generate a random game if the storage contains
     * | any games.
     * |
     * | If no games are found it will return a simple blank case for the icon
     * |
     * .........................................................................
     *
     * @return an ArrayList with info
     */
    public aImagePane getLibraryIcon() {

        if (dashboardUI.getStorage().getStoredLibrary().getBoxArtPath() == null || dashboardUI.getStorage().
                getStoredLibrary().getBoxArtPath().isEmpty()) {

            aImagePane blank = new aImagePane("Blank-Case.png",
                    dashboardUI.getGameCoverWidth(), dashboardUI.getGameCoverHeight());
            icon = blank; //Name change for carousel

        } else {
            Random rand = new Random();

            int randomNum = rand.nextInt(dashboardUI.getStorage().getStoredLibrary().
                    getGameNames().size());

            System.out.println("Random Num " + randomNum);
            System.out.println("Storage size " + dashboardUI.getStorage().getStoredLibrary().
                    getBoxArtPath());

            randomGame = new Game(dashboardUI.getStorage().getStoredLibrary().getBoxArtPath().
                    get(randomNum), dashboardUI);
            randomGame.setCoverSize(dashboardUI.getGameCoverWidth(), dashboardUI.getGameCoverHeight());

            icon = randomGame; //Name change for carousel
        }

        return icon;

    }
}
