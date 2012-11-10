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
import aurora.engine.V1.Logic.AuroraScreenUI;
import aurora.engine.V1.UI.AButton;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import javax.swing.JComponent;

/**
 * .---------------------------------------------------------------------------.
 * | AuroraApp
 * .---------------------------------------------------------------------------.
 * |
 * |
 * | AuroraApp is an abstract class extended by classes that represent an
 * | APP in Aurora.
 * |
 * | An APP are simply contained applications that Run on the AuroraGameHub
 * | platform. They are run through the Carousel which may represent many
 * | APPs.
 * |
 * | APPs through AuroraApp implement the AuroraScreenUI interface which allows
 * | for a uniform creation and management of UI.
 * |
 * .............................................................................
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 * <p/>
 */
public abstract class AuroraApp implements AuroraScreenUI {

    /**
     * ArrayList containing JComponents to be cleaned of Listeners.
     */
    private ArrayList<JComponent> componentsContainingListeners;

    /**
     * ArrayList of KeyListeners found in componentsContainingListeners.
     */
    private KeyListener[] keyListeners;

    /**
     * ArrayList of ActionListener found in ComponentsContainingListeners.
     */
    private ActionListener[] actionListeners;

    /**
     * ArrayList of MouseWheelListener found in ComponentsContainingListeners.
     */
    private MouseWheelListener[] mouseWheelListeners;

    /**
     * ArrayList of MouseListenersss found in ComponentsContainingListeners.
     */
    private MouseListener[] mouseListeners;

    /**
     * ArrayList of KeyListener found in the JFrame.
     */
    private KeyListener[] frameKeyListeners;

    /**
     * ArrayList of MouseListener found in the JFrame.
     */
    private MouseListener[] frameMouseListeners;

    /**
     * ArrayList of MouseWheelListener found in the JFrame.
     */
    private MouseWheelListener[] frameMouseWheelListeners;

    /**
     * .-----------------------------------------------------------------------.
     * | AuroraApp()
     * .-----------------------------------------------------------------------.
     * |
     * | This is the Constructor of the AuroraApp .
     * |
     * | It simply initializes the componentsContainingListeners ArrayList
     * | to be used in the addToVolatileListenerBank(JComponent) method
     * |
     * .........................................................................
     *
     */
    public AuroraApp() {

        componentsContainingListeners = new ArrayList<JComponent>();
    }

    /**
     * .-----------------------------------------------------------------------.
     * | addToVolatileListenerBank(JComponent)
     * .-----------------------------------------------------------------------.
     * |
     * | This method collects, in an ArrayList, components that should have
     * | all listeners attached to it removed when going back to Dashboard
     * |
     * | The Volatile Listeners will be removed when going back and should be
     * | re-added when going back into the App.
     * |
     * |
     * .........................................................................
     *
     * @param aComponent JComponent
     *
     */
    public final void addToVolatileListenerBank(final JComponent aComponent) {
        componentsContainingListeners.add(aComponent);
    }

    /**
     * .-----------------------------------------------------------------------.
     * | backToDashboard()
     * .-----------------------------------------------------------------------.
     * |
     * | This method is executed when the App needs to go Back to the Dashboard.
     * | Normally this happens when the User presses the back button on the
     * | FrameControlsPanel.
     * |
     * | The method removes all listeners from components that affect the
     * | DashboardUI using the removeAllListeners() method
     * |
     * | The method clears all changes made to CoreUI before DashboardUI using
     * | the clearUiToDashboard() method.
     * |
     * | The method re-uses the frozen state of DashboardUI instead of
     * | re-creating the Dashboard using addToCanvas()
     * |
     * .........................................................................
     */
    public final void backToDashboard() {



        //* Remove All Listeners from componentsContainingListeners *//
        removeAllListeners();


        //* Clear everything in the Center Panel of CoreUI *//
        clearUI();


        //* Re-add all DashboardUI components back to CoreUI *//
        getDashboardUI().addToCanvas();


    }

    /**
     * .-----------------------------------------------------------------------.
     * | getDashboardUI()
     * .-----------------------------------------------------------------------.
     * |
     * | This method returns the Instanced DashboardUI from the specific APP.
     * |
     * | It is used in the AuroraApp abstract class to take care of going back
     * | and too the Dashboard easily.
     * |
     * .........................................................................
     *
     * <p/>
     * @return DashboardUI
     */
    public abstract DashboardUI getDashboardUI();

    /**
     * .-----------------------------------------------------------------------.
     * | clearUI()
     * .-----------------------------------------------------------------------.
     * |
     * | This method clears the CoreUI to allow any App to use by removing all
     * | content the bottom bar and the center panel as well as ability for the
     * | DashboardUI to be created when going back from any App
     * |
     * | The method must be called by UI Screen classes in the constructor to be
     * | useful before the App UI is added to the CoreUI or When going back to
     * | the Dashboard
     * |
     * .........................................................................
     */
    public final void clearUI() {


        //* Remove all Center and Bottom Bar Content *//
        getCoreUI().getKeyToPressPanel().removeAll();
        getCoreUI().getCenterPanel().removeAll();
        getCoreUI().getUserSpacePanel().removeAll();
        getCoreUI().getCenterFromBottomPanel().removeAll();
        getCoreUI().getUserSpacePanel().revalidate();


        //* Remove All from top of bottom pane and re-add CoreUI components*//
        getCoreUI().getCenterFromBottomPanel().add(BorderLayout.NORTH,
                getCoreUI()
                .getHeaderOfCenterFromBottomPanel());
        getCoreUI().getHeaderOfCenterFromBottomPanel()
                .setPreferredSize(new Dimension(getCoreUI().getFrame()
                .getWidth(),
                getCoreUI().getKeyToPressPanel().getHeight()));
        getCoreUI().getHeaderOfCenterFromBottomPanel().revalidate();

        getCoreUI().getCenterPanel().revalidate();
        getCoreUI().getCenterFromBottomPanel().revalidate();

        //* Clear Bottom of Top Pane and re-add the Frame Conrols *//
        getCoreUI().getSouthFromTopPanel().removeAll();
        getCoreUI().getSouthFromTopPanel()
                .add(getCoreUI().getFrameControlContainerPanel(),
                BorderLayout.EAST);

        System.out.println("Mouse Listeners::: " + getCoreUI()
                .getFrameControlImagePane().getComponent(0)
                .getMouseListeners().length);

        if (getCoreUI().getFrameControlImagePane().getComponent(0)
                .getMouseListeners().length <= 2) {
            getCoreUI().getFrameControlImagePane().getComponent(0)
                    .addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(final MouseEvent e) {
                    removeAllListeners();
                    backToDashboard();
                }
            });
        }


        //* Remove all from the title pane of the bottom bar and re-add title*//
        getCoreUI().getTitlePanel().removeAll();
        getCoreUI().getTitlePanel().add(BorderLayout.CENTER, getCoreUI()
                .getTitleLabel());

        //* Set Size to CoreUI Components *//
        getCoreUI().getCenterPanel().setPreferredSize(new Dimension(getCoreUI()
                .getCenterPanel().getWidth(), getCoreUI().getFrame().getHeight()
                                              - getCoreUI().getBottomImagePane()
                .getHeight() - getCoreUI().getTopImagePane().getHeight()));

        getCoreUI().getFrame().requestFocus();
        getCoreUI().getFrame().repaint();


        System.gc();




    }

    /**
     * .-----------------------------------------------------------------------.
     * | removeAllListeners()
     * .-----------------------------------------------------------------------.
     * |
     * | This method goes through every JComponent in the ArrayList
     * | componentsContainingListeners as well as the Main JFrame and removes
     * | all possible KeyListeners, MouseWheelListeners as well
     * | as MouseListeners
     * |
     * | This method is used to prevent conflicting handlers being added
     * | In an App and in the DashboardUI.
     * |
     * .........................................................................
     */
    private void removeAllListeners() {

        // Check through all Components in the Volatile Bank!
        for (int i = 0; i < componentsContainingListeners.size(); i++) {

            //* Remove KeyListers from Components ArrayList *//
            keyListeners = componentsContainingListeners.get(i)
                    .getKeyListeners();

            for (int j = 0; j < keyListeners.length; j++) {
                componentsContainingListeners.get(i)
                        .removeKeyListener(keyListeners[j]);
            }


            //* Remove Mouse Listeners from Components ArrayList *//
            mouseListeners = componentsContainingListeners.get(i)
                    .getMouseListeners();

            for (int j = 0; j < mouseListeners.length; j++) {
                componentsContainingListeners.get(i)
                        .removeMouseListener(mouseListeners[j]);

            }


            //* Remove Mouse Wheel Listeners from Components ArrayList *//
            mouseWheelListeners = componentsContainingListeners.get(i)
                    .getMouseWheelListeners();

            for (int j = 0; j < mouseWheelListeners.length; j++) {
                componentsContainingListeners.get(i)
                        .removeMouseWheelListener(mouseWheelListeners[j]);

            }



            //* Maybe its a buttom, try to remove its ActionListeners. *//
            if (componentsContainingListeners.get(i) instanceof AButton) {
                actionListeners = ((AButton) componentsContainingListeners
                        .get(i)).getActionListeners();
                for (int j = 0; j < actionListeners.length; j++) {
                    ((AButton) componentsContainingListeners.get(i))
                            .removeActionListener(actionListeners[j]);
                }
            }

        }

        //* Clear Listeners from Frame *//

        frameKeyListeners = getCoreUI().getFrame().getKeyListeners();

        for (int i = 0; i < frameKeyListeners.length; i++) {
            getCoreUI().getFrame().removeKeyListener(frameKeyListeners[i]);
        }

        frameMouseListeners = getCoreUI().getFrame().getMouseListeners();

        for (int i = 0; i < frameMouseListeners.length; i++) {
            getCoreUI().getFrame().removeMouseListener(frameMouseListeners[i]);
        }

        frameMouseWheelListeners = getCoreUI().getFrame()
                .getMouseWheelListeners();

        for (int i = 0; i < frameMouseWheelListeners.length; i++) {
            getCoreUI().getFrame()
                    .removeMouseWheelListener(frameMouseWheelListeners[i]);
        }


        componentsContainingListeners.removeAll(componentsContainingListeners);

    }

    /**
     * The CoreUI from the specific AuroraScreen.
     * <p/>
     * @return AuroraCoreUI
     */
    public abstract AuroraCoreUI getCoreUI();
}
