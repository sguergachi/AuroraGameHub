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

import aurora.V1.core.screen_ui.Dashboard_UI;
import aurora.engine.V1.UI.aButton;
import aurora.engine.V1.UI.aProgressWheel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FontFormatException;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * A Super class to Sub menu app
 *
 * @author Sammy
 * @version 0.3
 */
public abstract class AuroraApp implements Runnable {

    public JFrame frame;
    public AuroraCoreUI ui;
    public Dashboard_UI dash_Obj;
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

//    public void setStorage(AuroraStorage storage) {
//        this.storage = storage;
//    }
    public abstract void createGUI();

    /**
     * Add to the list of items that will have their Listeners Removed when
     * going back to dashboard
     */
    public void addToVolatileListenerBank(JComponent component) {
        ComponentsContainingListeners.add(component);
    }

    public void backToDashboard() {
        removeAllListeners();
        clearUI_Backwards();


        try {
            dash_Obj.buildGUI();
            System.gc();
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(AuroraApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AuroraApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(AuroraApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(AuroraApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FontFormatException ex) {
            Logger.getLogger(AuroraApp.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    public void setUpSize() {
        int Ratio = ui.getFrame().getWidth() / ui.getFrame().getHeight();
        if (ui.isLargeScreen()) {
            SIZE_TopPadding = ui.getPnlCenter().getHeight() / 5 + Ratio / 5;

        } else {
            SIZE_TopPadding = ui.getPnlCenter().getHeight() / 5 + Ratio / 10;
        }

    }

    public void clearUI_Backwards() {

        ///...Clear UI
        ui.getPnlKeyToPress().removeAll();

        ui.getPnlCenter().removeAll();
        ui.getPnlUserSpace().removeAll();

        ui.getPnlUserSpace().revalidate();
        ui.getPnlKeyToPress().revalidate();



        ui.getPnlCenterFromBottom().removeAll();
        ui.getPnlCenterFromBottom().validate();
        ui.getPnlCenterFromBottom().add(BorderLayout.NORTH, ui.getPnlHeaderOfCenterFromBottom());
        ui.getPnlCenterFromBottom().revalidate();
        ui.getPnlHeaderOfCenterFromBottom().setPreferredSize(new Dimension(ui.getFrame().getWidth(), ui.getPnlKeyToPress().getHeight()));

        ui.getPnlSouthFromTop().setPreferredSize(new Dimension(ui.getPnlSouthFromTop().getWidth(), ui.getPnlSouthFromTop().getHeight()));
        ui.getPnlCenter().setPreferredSize(new Dimension(ui.getPnlCenter().getWidth(), ui.getFrame().getHeight() - ui.getPnlBottom().getHeight() - ui.getPnlTop().getHeight()));
        ui.getPnlCenter().setPreferredSize(new Dimension(ui.getPnlCenter().getWidth(), ui.getFrame().getHeight() - (ui.getFrame().getHeight() / 6 * 2)));
        ui.getPnlBottom().setPreferredSize(new Dimension(ui.getPnlBottom().getWidth(), ui.getFrame().getHeight() / 6 + 25));
        ui.getPnlBottom().setImageHeight(ui.getFrame().getHeight() / 6 + 25);


        ui.getPnlSouthFromTop().removeAll();
        ui.getPnlSouthFromTop().add(BorderLayout.EAST, ui.getPnlFrameControlContainer());
        ui.getPnlSouthFromTop().repaint();

        ui.getPnlInfo().removeAll();
        ui.getPnlInfo().add(BorderLayout.CENTER, ui.getLblInfo());
        ui.getFrame().repaint();
        ui.getFrame().getGlassPane().setVisible(false);
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

        ui.getLblInfo().setText("   Loading...   ");

        ui.getPnlKeyToPress().removeAll();
        ui.getPnlCenter().removeAll();
        ui.getPnlUserSpace().removeAll();

        ui.getPnlUserSpace().revalidate();


        ui.getPnlCenter().setPreferredSize(new Dimension(ui.getPnlCenter().getWidth(), ui.getFrame().getHeight() - (ui.getFrame().getHeight() / 6 * 2)));
        ui.getPnlBottom().setPreferredSize(new Dimension(ui.getPnlBottom().getWidth(), ui.getFrame().getHeight() / 6 + 15));
        ui.getPnlBottom().setImageHeight(ui.getFrame().getHeight() / 6 + 30);

        //Remove Content in Center
        ui.getPnlCenterFromBottom().removeAll();


        //re-add Time
        ui.getPnlCenterFromBottom().add(BorderLayout.NORTH, ui.getPnlHeaderOfCenterFromBottom());
        ui.getPnlHeaderOfCenterFromBottom().setPreferredSize(new Dimension(ui.getFrame().getWidth(), ui.getPnlKeyToPress().getHeight()));
        ui.getPnlHeaderOfCenterFromBottom().revalidate();

        ui.getPnlCenter().revalidate();
        ui.getPnlCenterFromBottom().revalidate();

        //Change Back button
        ui.getPnlSouthFromTop().removeAll();
        ui.getPnlSouthFromTop().add(ui.getPnlFrameControlContainer(), BorderLayout.EAST);
        ui.getPnlFrameControl().getComponent(0).addMouseListener(new MouseListener() {
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

        ui.getFrame().getContentPane().addKeyListener(new KeyListener() {
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

        ui.getFrame().requestFocus();



        //Finalize
        ui.getPnlInfo().removeAll();
        ui.getPnlInfo().add(BorderLayout.CENTER, ui.getLblInfo());

        System.gc();
        ui.getFrame().repaint();


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

        FrameKeyListeners = ui.getFrame().getKeyListeners();

        for (int i = 0; i < FrameKeyListeners.length; i++) {
            ui.getFrame().removeKeyListener(FrameKeyListeners[i]);
        }

        FrameMouseListeners = ui.getFrame().getMouseListeners();

        for (int i = 0; i < FrameMouseListeners.length; i++) {
            ui.getFrame().removeMouseListener(FrameMouseListeners[i]);
        }

        FrameMouseWheelListeners = ui.getFrame().getMouseWheelListeners();

        for (int i = 0; i < FrameMouseWheelListeners.length; i++) {
            ui.getFrame().removeMouseWheelListener(FrameMouseWheelListeners[i]);
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