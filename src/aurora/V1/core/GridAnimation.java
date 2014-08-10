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

import aurora.engine.V1.Logic.AAnimate;
import aurora.engine.V1.Logic.APostHandler;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import org.apache.log4j.Logger;

/**
 * Moves Grid Panels through animation for transition to next Grid of Games
 * <p/>
 * @author Sammy
 */
public class GridAnimation {

    private final GridManager GridSplit;

    private JPanel contentPanel;

    private int currentPanel;

    private AAnimate animator1;

    private AAnimate animator2;

    static final Logger logger = Logger.getLogger(GridAnimation.class);

    public GridAnimation(GridManager GridSplit, JPanel ContentPanel) {
        this.GridSplit = GridSplit;
        this.contentPanel = ContentPanel;

        animator1 = new AAnimate();
        animator2 = new AAnimate();
    }

    public void moveRight(int currentIndex) {
        this.currentPanel = currentIndex;

        //Move Panel To Right
        animator1 = new AAnimate(GridSplit.getGrid(currentPanel));
        animator2 = new AAnimate(GridSplit.getGrid(currentPanel + 1));


        animator1.moveHorizontal(1900, 65);
        animator1.addPostAnimationListener(new APostHandler() {

            @Override
            public void doAction() {
                GridSplit.getGrid(currentPanel + 1).setVisible(true);
            }
        });

        //Add to GridManager
        GridSplit.getGrid(currentPanel + 1).setVisible(false);
        contentPanel.add(GridSplit.getGrid(currentPanel + 1),
                BorderLayout.CENTER, 1);

        GridSplit.incrementVisibleGridIndex();

        //Move Second Panel To Center

        animator2.setInitialLocation((-1800), 0);
//        animator2.moveHorizontal(185, 85);
        contentPanel.revalidate();
        contentPanel.repaint();

    }

    public void moveLeft(int currentIndex) {
        this.currentPanel = currentIndex;

        //Move Panel to Left
        animator1 = new AAnimate(GridSplit.getGrid(currentPanel));
        animator2 = new AAnimate(GridSplit.getGrid(currentPanel - 1));


        animator1.moveHorizontal((-1900), 65);
        animator1.addPostAnimationListener(new APostHandler() {

            @Override
            public void doAction() {
                GridSplit.getGrid(currentPanel - 1).setVisible(true);
            }
        });

        //Add to GridManager
        GridSplit.getGrid(currentPanel - 1).setVisible(false);
        contentPanel.add(GridSplit.getGrid(currentPanel - 1),
                BorderLayout.CENTER, 1);

        GridSplit.decrementVisibleGridIndex();

        //Move Second Grid Towards Center

        animator2.setInitialLocation((1800), 0);
//        animator2.moveHorizontal(185, -86);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public AAnimate getAnimator1() {
        return animator1;
    }

    public AAnimate getAnimator2() {
        return animator2;
    }
}
