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
import aurora.engine.V1.UI.AuroraUI;
import aurora.engine.V1.UI.AButton;
import aurora.engine.V1.UI.AProgressWheel;
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
 * A Super class to Sub menu app.
 *
 * @author Sammy
 * @version 0.3
 */
public abstract class AuroraApp implements Runnable, AuroraUI {

    private JFrame frame;

    private AuroraCoreUI coreUI;

    private DashboardUI dashboardUI;

    private int SIZE_TopPadding;

    private Thread loadApp;

    private AProgressWheel progress;

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
        int Ratio = getCoreUI().getFrame().getWidth() / getCoreUI().getFrame().getHeight();
        if (getCoreUI().isLargeScreen()) {
            SIZE_TopPadding = getCoreUI().getCenterPanel().getHeight() / 5 + Ratio
                                                                        / 5;

        } else {
            SIZE_TopPadding = getCoreUI().getCenterPanel().getHeight() / 5 + Ratio
                                                                        / 10;
        }

    }

    public void clearUI_Backwards() {

        ///...Clear UI
        getCoreUI().getKeyToPressPanel().removeAll();

        getCoreUI().getCenterPanel().removeAll();
        getCoreUI().getUserSpacePanel().removeAll();

        getCoreUI().getUserSpacePanel().revalidate();
        getCoreUI().getKeyToPressPanel().revalidate();



        getCoreUI().getCenterFromBottomPanel().removeAll();
        getCoreUI().getCenterFromBottomPanel().validate();
        getCoreUI().getCenterFromBottomPanel().add(BorderLayout.NORTH, getCoreUI()
                .getHeaderOfCenterFromBottomPanel());
        getCoreUI().getCenterFromBottomPanel().revalidate();
        getCoreUI().getHeaderOfCenterFromBottomPanel()
                .setPreferredSize(new Dimension(getCoreUI().getFrame().getWidth(),
                getCoreUI().getKeyToPressPanel().getHeight()));

        getCoreUI().getSouthFromTopPanel().setPreferredSize(new Dimension(getCoreUI()
                .getSouthFromTopPanel().getWidth(), getCoreUI()
                .getSouthFromTopPanel().getHeight()));
        getCoreUI().getCenterPanel().setPreferredSize(new Dimension(getCoreUI()
                .getCenterPanel().getWidth(), getCoreUI().getFrame().getHeight()
                                              - getCoreUI().getBottomImagePane()
                .getHeight() - getCoreUI().getTopImagePane().getHeight()));
        getCoreUI().getCenterPanel().setPreferredSize(new Dimension(getCoreUI()
                .getCenterPanel().getWidth(), getCoreUI().getFrame().getHeight()
                                              - (getCoreUI().getFrame().getHeight()
                                                 / 6 * 2)));
        getCoreUI().getBottomImagePane().setPreferredSize(new Dimension(getCoreUI()
                .getBottomImagePane().getWidth(), getCoreUI().getFrame().getHeight()
                                                  / 6 + 25));
        getCoreUI().getBottomImagePane().setImageHeight(getCoreUI().getFrame().getHeight()
                                                   / 6 + 25);


        getCoreUI().getSouthFromTopPanel().removeAll();
        getCoreUI().getSouthFromTopPanel().add(BorderLayout.EAST, getCoreUI()
                .getFrameControlContainerPanel());
        getCoreUI().getSouthFromTopPanel().repaint();

        getCoreUI().getInfoPanel().removeAll();
        getCoreUI().getInfoPanel().add(BorderLayout.CENTER, getCoreUI().getInfoLabel());
        getCoreUI().getFrame().repaint();
        getCoreUI().getFrame().getGlassPane().setVisible(false);
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

        getCoreUI().getInfoLabel().setText("   Loading...   ");

        getCoreUI().getKeyToPressPanel().removeAll();
        getCoreUI().getCenterPanel().removeAll();
        getCoreUI().getUserSpacePanel().removeAll();

        getCoreUI().getUserSpacePanel().revalidate();


        getCoreUI().getCenterPanel().setPreferredSize(new Dimension(getCoreUI()
                .getCenterPanel().getWidth(), getCoreUI().getFrame().getHeight()
                                              - (getCoreUI().getFrame().getHeight()
                                                 / 6 * 2)));
        getCoreUI().getBottomImagePane().setPreferredSize(new Dimension(getCoreUI()
                .getBottomImagePane().getWidth(), getCoreUI().getFrame().getHeight()
                                                  / 6 + 15));
        getCoreUI().getBottomImagePane().setImageHeight(getCoreUI().getFrame().getHeight()
                                                   / 6 + 30);

        //Remove Content in Center
        getCoreUI().getCenterFromBottomPanel().removeAll();


        //re-add Time
        getCoreUI().getCenterFromBottomPanel().add(BorderLayout.NORTH, getCoreUI()
                .getHeaderOfCenterFromBottomPanel());
        getCoreUI().getHeaderOfCenterFromBottomPanel()
                .setPreferredSize(new Dimension(getCoreUI().getFrame().getWidth(),
                getCoreUI().getKeyToPressPanel().getHeight()));
        getCoreUI().getHeaderOfCenterFromBottomPanel().revalidate();

        getCoreUI().getCenterPanel().revalidate();
        getCoreUI().getCenterFromBottomPanel().revalidate();

        //Change Back button
        getCoreUI().getSouthFromTopPanel().removeAll();
        getCoreUI().getSouthFromTopPanel()
                .add(getCoreUI().getFrameControlContainerPanel(), BorderLayout.EAST);
        getCoreUI().getFrameControlImagePane().getComponent(0)
                .addMouseListener(new MouseListener() {
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

        getCoreUI().getFrame().getContentPane().addKeyListener(new KeyListener() {
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

        getCoreUI().getFrame().requestFocus();



        //Finalize
        getCoreUI().getInfoPanel().removeAll();
        getCoreUI().getInfoPanel().add(BorderLayout.CENTER, getCoreUI().getInfoLabel());

        System.gc();
        getCoreUI().getFrame().repaint();


    }

    private void removeAllListeners() {

        //For Components in the Volatile Bank!
        for (int i = 0; i < ComponentsContainingListeners.size(); i++) {

            //Remove KeyListers
            KeyListeners = ComponentsContainingListeners.get(i)
                    .getKeyListeners();

            for (int j = 0; j < KeyListeners.length; j++) {
                ComponentsContainingListeners.get(i)
                        .removeKeyListener(KeyListeners[j]);

            }


            //Remove Mouse Listeners
            MouseListeners = ComponentsContainingListeners.get(i)
                    .getMouseListeners();

            for (int j = 0; j < MouseListeners.length; j++) {
                ComponentsContainingListeners.get(i)
                        .removeMouseListener(MouseListeners[j]);

            }


            //Remove Mouse Wheel Listeners
            MouseWheelListeners = ComponentsContainingListeners.get(i)
                    .getMouseWheelListeners();

            for (int j = 0; j < MouseWheelListeners.length; j++) {
                ComponentsContainingListeners.get(i)
                        .removeMouseWheelListener(MouseWheelListeners[j]);

            }



            //Maybe its a buttom remove its action Listeners.
            try {
                ActionListeners = ((AButton) ComponentsContainingListeners
                        .get(i)).getActionListeners();
                for (int j = 0; j < ActionListeners.length; j++) {
                    ((AButton) ComponentsContainingListeners.get(i))
                            .removeActionListener(ActionListeners[j]);
                }
            } catch (Exception e) {
                //maybe its not a Button
            }
        }

        //Only for the Frame!

        FrameKeyListeners = getCoreUI().getFrame().getKeyListeners();

        for (int i = 0; i < FrameKeyListeners.length; i++) {
            getCoreUI().getFrame().removeKeyListener(FrameKeyListeners[i]);
        }

        FrameMouseListeners = getCoreUI().getFrame().getMouseListeners();

        for (int i = 0; i < FrameMouseListeners.length; i++) {
            getCoreUI().getFrame().removeMouseListener(FrameMouseListeners[i]);
        }

        FrameMouseWheelListeners = getCoreUI().getFrame().getMouseWheelListeners();

        for (int i = 0; i < FrameMouseWheelListeners.length; i++) {
            getCoreUI().getFrame()
                    .removeMouseWheelListener(FrameMouseWheelListeners[i]);
        }


        ComponentsContainingListeners.removeAll(ComponentsContainingListeners);

    }

    @Override
    public void run() {

        while (Thread.currentThread() == loadApp) {

            break;
        }


    }

    /**
     * @return the coreUI
     */
    public AuroraCoreUI getCoreUI() {
        return coreUI;
    }

    /**
     * @param coreUI the coreUI to set
     */
    public void setCoreUI(AuroraCoreUI coreUI) {
        this.coreUI = coreUI;
    }
}
