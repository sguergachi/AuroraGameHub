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

package aurora.V1.core.screen_handler;

import aurora.V1.core.screen_ui.StartScreenUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class StartScreenLogic {
    
    private final StartScreenUI ui;

    public StartScreenLogic(StartScreenUI ui) {
        this.ui = ui;
    }

    
    /**
     * Transitions to the Main_Window
     */
   public class StartListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ui.startTransision();
        }
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
