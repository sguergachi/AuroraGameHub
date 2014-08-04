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

import aurora.engine.V1.UI.AButton;
import aurora.engine.V1.UI.AImagePane;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import org.apache.log4j.Logger;

/**
 *
 * @author Sammy
 */
public class GamePlaceholder extends AImagePane {
    //A Place Holder Component similar to the Game Component.

    private AButton button = null;

    private JPanel buttonPane;

    private int allWidth;

    private int allHeight;

    static final Logger logger = Logger.getLogger(GamePlaceholder.class);

    private int buttonPadding;

    public GamePlaceholder() {
    }

    public void setUp(int Width, int Height, String BGimg) {
        this.allWidth = Width;
        this.allHeight = Height;

        this.setImage(BGimg, allHeight, allWidth);
        this.setPreferredSize(new Dimension(allWidth, allHeight));
        this.revalidate();
        this.repaint();
        this.setOpaque(false);

        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

    }

    public void addButton(String up, String down, String over,
                          ActionListener handler) {


        buttonPadding = -(allWidth / 20) * ((allHeight) / 120) + (allWidth / 7);


        button = new AButton(up, down, over, allWidth, allHeight);
        button.addActionListener(handler);
        buttonPane = new JPanel(new FlowLayout(FlowLayout.LEFT, buttonPadding,
                                               0)); //Contains the Add Game Button
        buttonPane.setOpaque(false);



        button.addMouseListener(new ButtonMouseListener());
        this.addMouseListener(new ButtonMouseListener());
        buttonPane.addMouseListener(new ButtonMouseListener());
//        buttonPane.add(button);
//        buttonPane.add(Box.createHorizontalStrut(allWidth));

        this.add(button);

        this.revalidate();
        buttonPane.revalidate();
        this.repaint();

    }

    public Boolean isContainsButton() {
        return button != null;
    }

    public class ButtonMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
}
