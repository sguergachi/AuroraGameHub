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
package aurora.V1.core.screen_handler;

import aurora.V1.core.screen_ui.WelcomeUI;
import aurora.engine.V1.Logic.AuroraScreenHandler;
import aurora.engine.V1.Logic.AuroraScreenLogic;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.apache.log4j.Logger;

/**
 * .------------------------------------------------------------------------.
 * | WelcomeHandler
 * .------------------------------------------------------------------------.
 * |
 * | This class contains all Listeners/Handlers attached to UI elements
 * | found in WelcomeUI. The handlers may access the logic or simply
 * | make simple processing within each Handler/Listeners.
 * |
 * | Each Handler is attached to UI components to listen for different actions
 * | The actions can be processed or handled internally or within th Logic
 * | of the Screen.
 * |
 * |
 * .........................................................................
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 * @author Carlos Machado <camachado@gmail.com>
 *
 */
public class WelcomeHandler implements AuroraScreenHandler {

    private final WelcomeUI ui;
    private AuroraScreenLogic startLogic;
    static final Logger logger = Logger.getLogger(WelcomeHandler.class);

    public WelcomeHandler(WelcomeUI ui) {
        this.ui = ui;
    }

    @Override
    public void setLogic(AuroraScreenLogic logic) {
        this.startLogic = logic;
    }


    public class FrameKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {

            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (ui.isLoaded() && !ui.isTransisioning()) {

                    ui.startTransision();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }
}
