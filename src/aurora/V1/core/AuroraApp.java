/*
 * Copyright 2012 Sardonix Creative.
 *
 * This work is licensed under the
 * Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * To view a copy of this license, visit
 *
 *      http://creativecommons.org/licenses/by-nc-nd/3.0/
 *
 * or send a letter to Creative Commons, 444 Castro Street, ScoreUIte 900,
 * Mountain View, California, 94041, USA.
 * Unless reqcoreUIred by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package aurora.V1.core;

import aurora.V1.core.screen_ui.DashboardUI;
import aurora.engine.V1.UI.AuroraScreenUI;
import aurora.engine.V1.UI.aButton;
import aurora.engine.V1.UI.aProgressWheel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * A Super class to Sub menu app
 *
 * @author Sammy
 * @version 0.3
 */
public abstract class AuroraApp implements Runnable, AuroraScreenUI {

    private JFrame frame;
    private AuroraCoreUI coreUI;
    private DashboardUI dashboardUI;
    private int SIZE_TopPadding;
    private Thread loadApp;
    private aProgressWheel progress;
    private ArrayList<JComponent> ComponentsContainingListeners;
    private KeyListener[] KeyListeners;
    private ActionListener[] ActionListeners;
    private MouseListener[] MouseListeners;
    private MouseWheelListener[] MouseWheelListeners;
    private KeyListener[] FrameKeyListeners;
    private MouseListener[] FrameMouseListeners;
    private MouseWheelListener[] FrameMouseWheelListeners;

    public AuroraApp() {
        ComponentsContainingListeners = new ArrayList<JComponent>();
        
    }

    /**
     * Add to the list of items that will have their Listeners Removed when
     * going back to dashboard.
     *
     * @param component Any component
     */
    public final void addToVolatileListenerBank(final JComponent component) {
        ComponentsContainingListeners.add(component);
    }

    public final void backToDashboard() {
        removeAllListeners();
        clearUI_Backwards();


        dashboardUI.buildUI();
        System.gc();



    }

    public void setUpSize() {
        int Ratio = coreUI.getFrame().getWidth() / coreUI.getFrame().getHeight();
        if (coreUI.isLargeScreen()) {
            SIZE_TopPadding = coreUI.getCenterPanel().getHeight() / 5 + Ratio / 5;

        } else {
            SIZE_TopPadding = coreUI.getCenterPanel().getHeight() / 5 + Ratio / 10;
        }

    }

    public void clearUI_Backwards() {

        ///...Clear UI
        coreUI.getKeyToPressPanel().removeAll();

        coreUI.getCenterPanel().removeAll();
        coreUI.getUserSpacePanel().removeAll();

        coreUI.getUserSpacePanel().revalidate();
        coreUI.getKeyToPressPanel().revalidate();



        coreUI.getCenterFromBottomPanel().removeAll();
        coreUI.getCenterFromBottomPanel().validate();
        coreUI.getCenterFromBottomPanel().add(BorderLayout.NORTH, coreUI.getHeaderOfCenterFromBottomPanel());
        coreUI.getCenterFromBottomPanel().revalidate();
        coreUI.getHeaderOfCenterFromBottomPanel().setPreferredSize(new Dimension(coreUI.getFrame().getWidth(), coreUI.getKeyToPressPanel().getHeight()));

        coreUI.getSouthFromTopPanel().setPreferredSize(new Dimension(coreUI.getSouthFromTopPanel().getWidth(), coreUI.getSouthFromTopPanel().getHeight()));
        coreUI.getCenterPanel().setPreferredSize(new Dimension(coreUI.getCenterPanel().getWidth(), coreUI.getFrame().getHeight() - coreUI.getBottomImagePane().getHeight() - coreUI.getTopImagePane().getHeight()));
        coreUI.getCenterPanel().setPreferredSize(new Dimension(coreUI.getCenterPanel().getWidth(), coreUI.getFrame().getHeight() - (coreUI.getFrame().getHeight() / 6 * 2)));
        coreUI.getBottomImagePane().setPreferredSize(new Dimension(coreUI.getBottomImagePane().getWidth(), coreUI.getFrame().getHeight() / 6 + 25));
        coreUI.getBottomImagePane().setImageHeight(coreUI.getFrame().getHeight() / 6 + 25);


        coreUI.getSouthFromTopPanel().removeAll();
        coreUI.getSouthFromTopPanel().add(BorderLayout.EAST, coreUI.getFrameControlContainerPanel());
        coreUI.getSouthFromTopPanel().repaint();

        coreUI.getInfoPanel().removeAll();
        coreUI.getInfoPanel().add(BorderLayout.CENTER, coreUI.getInfoLabel());
        coreUI.getFrame().repaint();
        coreUI.getFrame().getGlassPane().setVisible(false);
    }

    public void clearUI_Forwards() {

        setUpSize();


        ///...Set Up UI


        ///THIS STUFF WAS TO ASYNC LOAD APP IN BACKGROUND, DOESN'T WORK BUT
        //WE SHOULD THINK ABOUT MAKING THIS LOAD FASTER
//        loadApp = null;
//
//        if (loadApp == null) {
//            loadApp = new Thread(this);
//        }
//        loadApp.setName("Load App Thread");
//        //Start Loader
//        loadApp.start();

        coreUI.getInfoLabel().setText("   Loading...   ");

        coreUI.getKeyToPressPanel().removeAll();
        coreUI.getCenterPanel().removeAll();
        coreUI.getUserSpacePanel().removeAll();

        coreUI.getUserSpacePanel().revalidate();


        coreUI.getCenterPanel().setPreferredSize(new Dimension(coreUI.getCenterPanel().getWidth(), coreUI.getFrame().getHeight() - (coreUI.getFrame().getHeight() / 6 * 2)));
        coreUI.getBottomImagePane().setPreferredSize(new Dimension(coreUI.getBottomImagePane().getWidth(), coreUI.getFrame().getHeight() / 6 + 15));
        coreUI.getBottomImagePane().setImageHeight(coreUI.getFrame().getHeight() / 6 + 30);

        //Remove Content in Center
        coreUI.getCenterFromBottomPanel().removeAll();


        //re-add Time
        coreUI.getCenterFromBottomPanel().add(BorderLayout.NORTH, coreUI.getHeaderOfCenterFromBottomPanel());
        coreUI.getHeaderOfCenterFromBottomPanel().setPreferredSize(new Dimension(coreUI.getFrame().getWidth(), coreUI.getKeyToPressPanel().getHeight()));
        coreUI.getHeaderOfCenterFromBottomPanel().revalidate();

        coreUI.getCenterPanel().revalidate();
        coreUI.getCenterFromBottomPanel().revalidate();

        //Change Back button
        coreUI.getSouthFromTopPanel().removeAll();
        coreUI.getSouthFromTopPanel().add(coreUI.getFrameControlContainerPanel(), BorderLayout.EAST);
        coreUI.getFrameControlImagePane().getComponent(0).addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                removeAllListeners();
                backToDashboard();

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
        });

        coreUI.getFrame().getContentPane().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    removeAllListeners();
                    backToDashboard();

                }

            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        coreUI.getFrame().requestFocus();



        //Finalize
        coreUI.getInfoPanel().removeAll();
        coreUI.getInfoPanel().add(BorderLayout.CENTER, coreUI.getInfoLabel());

        System.gc();
        coreUI.getFrame().repaint();


    }

    private void removeAllListeners() {

        //For Components in the Volatile Bank!
        for (int i = 0; i < ComponentsContainingListeners.size(); i++) {

            //Remove KeyListers 
            KeyListeners = ComponentsContainingListeners.get(i).getKeyListeners();

            for (int j = 0; j < KeyListeners.length; j++) {
                ComponentsContainingListeners.get(i).removeKeyListener(KeyListeners[j]);

            }


            //Remove Mouse Listeners
            MouseListeners = ComponentsContainingListeners.get(i).getMouseListeners();

            for (int j = 0; j < MouseListeners.length; j++) {
                ComponentsContainingListeners.get(i).removeMouseListener(MouseListeners[j]);

            }


            //Remove Mouse Wheel Listeners
            MouseWheelListeners = ComponentsContainingListeners.get(i).getMouseWheelListeners();

            for (int j = 0; j < MouseWheelListeners.length; j++) {
                ComponentsContainingListeners.get(i).removeMouseWheelListener(MouseWheelListeners[j]);

            }



            //Maybe its a buttom remove its action Listeners.
            try {
                ActionListeners = ((aButton) ComponentsContainingListeners.get(i)).getActionListeners();
                for (int j = 0; j < ActionListeners.length; j++) {
                    ((aButton) ComponentsContainingListeners.get(i)).removeActionListener(ActionListeners[j]);
                }
            } catch (Exception e) {
                //maybe its not a Button
            }
        }

        //Only for the Frame!

        FrameKeyListeners = coreUI.getFrame().getKeyListeners();

        for (int i = 0; i < FrameKeyListeners.length; i++) {
            coreUI.getFrame().removeKeyListener(FrameKeyListeners[i]);
        }

        FrameMouseListeners = coreUI.getFrame().getMouseListeners();

        for (int i = 0; i < FrameMouseListeners.length; i++) {
            coreUI.getFrame().removeMouseListener(FrameMouseListeners[i]);
        }

        FrameMouseWheelListeners = coreUI.getFrame().getMouseWheelListeners();

        for (int i = 0; i < FrameMouseWheelListeners.length; i++) {
            coreUI.getFrame().removeMouseWheelListener(FrameMouseWheelListeners[i]);
        }


        ComponentsContainingListeners.removeAll(ComponentsContainingListeners);

    }

    @Override
    public void run() {

        while (Thread.currentThread() == loadApp) {

            break;
        }


    }
}