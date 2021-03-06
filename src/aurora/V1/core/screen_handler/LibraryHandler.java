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

import aurora.V1.core.AuroraCoreUI;
import aurora.V1.core.AuroraStorage;
import aurora.V1.core.Game;
import aurora.V1.core.GamePlaceholder;
import aurora.V1.core.GameSearch;
import aurora.V1.core.GridAnimation;
import aurora.V1.core.GridManager;
import aurora.V1.core.StoredSettings;
import aurora.V1.core.main;
import aurora.V1.core.screen_handler.LibraryHandler.MoveToGrid;
import aurora.V1.core.screen_logic.LibraryLogic;
import aurora.V1.core.screen_logic.SettingsLogic;
import aurora.V1.core.screen_ui.LibraryUI;
import aurora.engine.V1.Logic.AAnimate;
import aurora.engine.V1.Logic.AFileDrop;
import aurora.engine.V1.Logic.AFileManager;
import aurora.engine.V1.Logic.AMixpanelAnalytics;
import aurora.engine.V1.Logic.APostHandler;
import aurora.engine.V1.Logic.AThreadWorker;
import aurora.engine.V1.Logic.AuroraScreenHandler;
import aurora.engine.V1.Logic.AuroraScreenLogic;
import aurora.engine.V1.UI.AButton;
import aurora.engine.V1.UI.ADialog;
import aurora.engine.V1.UI.AGridPanel;
import aurora.engine.V1.UI.AHoverButton;
import aurora.engine.V1.UI.AImage;
import aurora.engine.V1.UI.AImagePane;
import aurora.engine.V1.UI.AProgressWheel;
import aurora.engine.V1.UI.ARadioButton;
import aurora.engine.V1.UI.ASlickLabel;
import aurora.engine.V1.UI.ATextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import org.apache.log4j.Logger;

/**
 * .------------------------------------------------------------------------.
 * | LibraryHandler
 * .------------------------------------------------------------------------.
 * |
 * | This class contains all Listeners/Handlers attached to UI elements
 * | found in LibraryUI. The handlers may access the logic or simply
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
public class LibraryHandler implements
        AuroraScreenHandler {

    /**
     * LibraryLogic instance.
     */
    private LibraryLogic libraryLogic;

    /**
     * LibraryUI instance.
     */
    private final LibraryUI libraryUI;

    static final Logger logger = Logger.getLogger(LibraryHandler.class);

    /**
     * .-----------------------------------------------------------------------.
     * | LibraryHandler(LibraryUI)
     * .-----------------------------------------------------------------------.
     * |
     * | This is the Constructor of the GameLibrary Handler class.
     * |
     * | The Constructor of the Handler class needs to UI class to be able to
     * | first get the logic from it, and second to be able to manipulate the UI
     * | within the actual Handlers.
     * |
     * .........................................................................
     * <p/>
     * @param aLibraryUI LibraryUI
     */
    public LibraryHandler(final LibraryUI aLibraryUI) {
        this.libraryUI = aLibraryUI;

    }

    @Override
    public final void setLogic(final AuroraScreenLogic logic) {

        this.libraryLogic = (LibraryLogic) logic;

    }

    //
    // General Library
    //
    /**
     * Transisions towards the Grid where the game is located
     * To show game added (apple iOS style :P )
     */
    public class MoveToGrid implements Runnable {

        private int gameGrid;

        private final Game selectedGame;

        public MoveToGrid(Game game) {
            selectedGame = game;

        }
        private Thread mover;

        public void runMover() {
            gameGrid = libraryUI.getGridManager().findGame(selectedGame)[0];

            mover = null;
            mover = new Thread(this);
            mover.setName("Mover Thread");
            //Start Loader

            if (logger.isDebugEnabled()) {
                logger.debug("Starting Mover Thread");
            }

            mover.start();
        }

        @Override
        public void run() {
            while (Thread.currentThread() == mover) {
                if (libraryUI.getCurrentGridIndex() < gameGrid) {
                    logger.debug("Moving Right");
                    libraryUI.moveGridRight();

                    libraryUI.setCurrentIndex(-1);

                } else {
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    logger.error(ex);
                }
            }

        }
    }

    /**
     * Prevents from clicking Through the Aurora Add Game UI and select Games in
     * the
     * Background
     */
    public class EmptyMouseHandler extends MouseAdapter {

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

    public class HoverButtonLeft extends MouseAdapter {

        private GridManager gridManager;

        private JPanel GameBack;

        private AHoverButton btnMoveLeft;

        private AHoverButton btnMoveRight;

        private AImage imgFavorite;

        private GridAnimation GridAnimate;

        public HoverButtonLeft() {
            gridManager = libraryUI.getGridManager();
            GameBack = libraryUI.getGameGridContainer();
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            btnMoveLeft = libraryUI.getBtnMoveLeft();
            btnMoveRight = libraryUI.getBtnMoveRight();
            imgFavorite = libraryUI.getImgOrganizeType();
            GridAnimate = libraryUI.getGridAnimate();

            gridManager = libraryUI.getGridManager();

            int currentIndex;

            if (!GridAnimate.getAnimator1().isAnimating() && !GridAnimate
                    .getAnimator2().isAnimating()) {
                // -
                // Get The Index of The Current Panel Being Displayed
                // Refer too GridManager array of All panels to find it
                // -
                currentIndex = gridManager.getArray().indexOf(GameBack
                        .getComponent(1));

                // Stop from going to far left
                if (currentIndex - 1 == -1) {
                    currentIndex = 1;
                    btnMoveLeft.mouseExit();
                }

                if (currentIndex < gridManager.getArray().size()) {

                    if (currentIndex - 1 <= 0) {
                        // Far Left Image
                        GameBack.remove(0);
                        GameBack.add(imgFavorite, BorderLayout.WEST, 0);

                    } else {
                        // Left Button
                        GameBack.remove(0);
                        GameBack.add(libraryUI.getPnlMoveLeftContainer(),
                                BorderLayout.WEST, 0);
                    }
                    // Add GameCover Covers

                    GridAnimate.moveLeft(currentIndex);

                    try {
                        libraryLogic.loadGames(currentIndex - 1);
                    } catch (MalformedURLException ex) {
                        logger.error(ex);
                    }

                    GameBack.add(BorderLayout.EAST, libraryUI
                            .getPnlMoveRightContainer());
                }

                libraryUI.getCoreUI().getCenterPanel().removeAll();
                libraryUI.getCoreUI().getCenterPanel().add(BorderLayout.CENTER,
                        GameBack);

                GameBack.repaint();
                GameBack.revalidate();

            }
            btnMoveLeft.mouseExit();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            btnMoveLeft = libraryUI.getBtnMoveLeft();
            btnMoveRight = libraryUI.getBtnMoveRight();
            imgFavorite = libraryUI.getImgOrganizeType();
            GridAnimate = libraryUI.getGridAnimate();
            GridAnimate = libraryUI.getGridAnimate();
            btnMoveLeft = libraryUI.getBtnMoveLeft();

            if (!GridAnimate.getAnimator1().isAnimating() && !GridAnimate
                    .getAnimator2().isAnimating()) {
                btnMoveLeft.mouseHover(e);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            btnMoveLeft = libraryUI.getBtnMoveLeft();
            btnMoveRight = libraryUI.getBtnMoveRight();
            imgFavorite = libraryUI.getImgOrganizeType();
            GridAnimate = libraryUI.getGridAnimate();
            btnMoveLeft.mouseExit();

        }
    }

    public class HoverButtonRight extends MouseAdapter {

        private GridManager gridManager;

        private JPanel GameBack;

        private GridAnimation GridAnimate;

        private final AuroraCoreUI coreUI;

        private AHoverButton btnMoveRight;

        public HoverButtonRight() {
            this.coreUI = libraryUI.getCoreUI();

            GameBack = libraryUI.getGameGridContainer();
            btnMoveRight = libraryUI.getBtnMoveRight();
            GridAnimate = libraryUI.getGridAnimate();
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            gridManager = libraryUI.getGridManager();

            if (!GridAnimate.getAnimator1().isAnimating() && !GridAnimate
                    .getAnimator2().isAnimating()) {

                libraryUI.setCurrentIndex(gridManager.getArray()
                        .indexOf(GameBack
                                .getComponent(1)));

                if (libraryUI.getCurrentGridIndex() < gridManager.getArray()
                        .size() - 1) {

                    GameBack.remove(0);
                    GameBack.add(libraryUI.getPnlMoveLeftContainer(),
                            BorderLayout.WEST,
                            0);

                    GameBack.add(libraryUI.getPnlMoveRightContainer(),
                            BorderLayout.EAST, 2);

                    GridAnimate.moveRight(libraryUI.getCurrentGridIndex());

                    try {
                        libraryLogic.loadGames(libraryUI.getCurrentGridIndex()
                                               + 1);
                    } catch (MalformedURLException ex) {
                        logger.error(ex);
                    }

                    // Off on last Grid then dont show right arrow button
                    if (!(libraryUI.getCurrentGridIndex() + 1 < gridManager
                          .getArray()
                          .size() - 1)) {

                        GameBack.remove(libraryUI.getPnlMoveRightContainer());
                        GameBack.add(Box.createHorizontalStrut(140),
                                BorderLayout.EAST, 2);
                        btnMoveRight.mouseExit();
                    }
                }

                coreUI.getCenterPanel().removeAll();
                coreUI.getCenterPanel().add(BorderLayout.CENTER, libraryUI
                        .getGameGridContainer());

                GameBack.repaint();
                GameBack.revalidate();

            }
            btnMoveRight.mouseExit();
        }

        @Override
        public void mouseEntered(final MouseEvent e) {

            if (logger.isDebugEnabled()) {
                logger.debug("HOVER IMAGE ACTIVATED");
            }

            GridAnimate = libraryUI.getGridAnimate();
            btnMoveRight = libraryUI.getBtnMoveRight();

            if (!GridAnimate.getAnimator1().isAnimating() && !GridAnimate
                    .getAnimator2().isAnimating()) {
                btnMoveRight.mouseHover(e);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            btnMoveRight.mouseExit();
        }
    }

    /**
     * Handler for the Navigation using Keyboard
     */
    public class GameLibraryKeyListener extends AbstractAction {

        private GridManager gridManager;

        private final JPanel pnlGameGridContainer;

        private final AuroraCoreUI coreUI;

        private AuroraStorage storage;

        private int keyCode;

        public GameLibraryKeyListener(int KeyCode) {
            this.keyCode = KeyCode;

            this.coreUI = libraryUI.getCoreUI();
            pnlGameGridContainer = libraryUI.getGameGridContainer();
            storage = libraryUI.getStorage();
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            int currentIndex;
            gridManager = libraryUI.getGridManager();

            /*
             * get the index of the grid that is currently displayed
             */
            int visibleGridIndex = gridManager.getVisibleGridIndex();

            if (logger.isDebugEnabled()) {
                logger.debug("Initial visible grid = " + visibleGridIndex);
            }

            currentIndex = gridManager.getArray()
                    .indexOf(pnlGameGridContainer.getComponent(1));
            libraryUI.setCurrentIndex(currentIndex);

            if (logger.isDebugEnabled()) {
                logger.debug("Current Grid = " + currentIndex);
            }

            /*
             * get the grid that is currently displayed
             */
            AGridPanel grid = gridManager.getGrid(currentIndex);

            /*
             * get an array of all the components in the grid
             */
            ArrayList comp = grid.getArray();

            boolean cursorMoved = false;

            Game game = null;

            boolean selectedGameFound = false;

            String wasdNavSetting = storage.getStoredSettings().getSettingValue(
                    "wasd_navigation");
            if (wasdNavSetting == null) {
                wasdNavSetting = SettingsLogic.DEFAULT_WASD_NAV_SETTING;
            }

            if (!libraryUI.isAnyOverlayVisible()) {

                if ((keyCode == KeyEvent.VK_W && wasdNavSetting.equals(
                     "enabled") && !libraryUI.getSearchBar().isFocusOwner())
                    || keyCode == KeyEvent.VK_UP) {

                    int i = 0;

                    while (comp.size() > i && !selectedGameFound
                           && !(comp.get(i) instanceof GamePlaceholder)) {
                        //Check for GamePlaceholder CANT MOVE THERE!
                        game = (Game) comp.get(i);

                        if (game.isSelected()) {
                            selectedGameFound = true;

                            if (logger.isDebugEnabled()) {
                                logger.debug(game.getName()
                                             + " is selected in the library");
                            }

                            int[] columnAndRow = grid.getColumnAndRow(i + 1);
                            int col = columnAndRow[0];
                            int row = columnAndRow[1];

                            if (logger.isDebugEnabled()) {
                                logger.debug("Col = " + columnAndRow[0]);
                                logger.debug("Row = " + columnAndRow[1]);
                            }

                            if (row > 1) {
                                if (logger.isDebugEnabled()) {
                                    logger.debug("Cursor is moving up!");
                                }

                                // Check for GamePlaceholder CANT MOVE THERE!
                                if (!(comp.get(i - 4) instanceof GamePlaceholder)) {
                                    game.hideOverlayUI();
                                    Game newGame = (Game) comp.get(i - 4);
                                    gridManager.unselectPrevious(null);
                                    newGame.showOverlayUI();
                                }

                            } else if (row == 1) {
                                // Check for GamePlaceholder CANT MOVE THERE!
                                if (!(comp.get(i + (4 * 1)) instanceof GamePlaceholder)) {
                                    game.hideOverlayUI();
                                    Game newGame = (Game) comp.get(i + (4 * 1));
                                    gridManager.unselectPrevious(null);
                                    newGame.showOverlayUI();
                                }
                            } else {
                                if (logger.isDebugEnabled()) {
                                    logger.debug(
                                            "Cursor cannot move any further up!");
                                }
                            }
                        } else {
                            i++;
                        }

                    }

                    if (!selectedGameFound && (comp.get(0) instanceof Game)) {

                        game = (Game) comp.get(0);
                        game.showOverlayUI();
                    }

                    //>>> MOVE DOWN
                } else if ((keyCode == KeyEvent.VK_S && wasdNavSetting
                            .equals("enabled") && !libraryUI.getSearchBar()
                            .isFocusOwner())
                           || keyCode == KeyEvent.VK_DOWN) {

                    int i = 0;

                    while (i < comp.size() && !selectedGameFound
                           && !(comp.get(i) instanceof GamePlaceholder)) {
                        //Check for GamePlaceholder CANT MOVE THERE!
                        game = (Game) comp.get(i);

                        if (game.isSelected()) {
                            selectedGameFound = true;

                            if (logger.isDebugEnabled()) {
                                logger.debug(game.getName()
                                             + " is selected in the library");
                            }

                            int[] columnAndRow = grid.getColumnAndRow(i + 1);
                            int col = columnAndRow[0];
                            int row = columnAndRow[1];

                            if (logger.isDebugEnabled()) {
                                logger.debug("Col = " + columnAndRow[0]);
                                logger.debug("Row = " + columnAndRow[1]);
                            }

                            if (row < grid.getRow()) {
                                if (logger.isDebugEnabled()) {
                                    logger.debug("Cursor is moving down!");
                                }

                                // Check for GamePlaceholder CANT MOVE THERE!
                                if (!(comp.get(i + 4) instanceof GamePlaceholder)) {
                                    game.hideOverlayUI();
                                    Game newGame = (Game) comp.get(i + 4);
                                    gridManager.unselectPrevious(null);
                                    newGame.showOverlayUI();
                                }

                            } else if (row == grid.getRow()) {

                                // Check for GamePlaceholder CANT MOVE THERE!
                                if (!(comp.get(i - (4 * 1)) instanceof GamePlaceholder)) {
                                    game.hideOverlayUI();
                                    Game newGame = (Game) comp.get(i - (4 * 1));
                                    gridManager.unselectPrevious(null);
                                    newGame.showOverlayUI();
                                }
                            } else {

                                if (logger.isDebugEnabled()) {
                                    logger.debug(
                                            "Cursor cannot move any further down!");
                                }
                            }
                        } else {
                            i++;
                        }

                    }

                    if (!selectedGameFound && (comp.get(0) instanceof Game)) {
                        game = (Game) comp.get(0);
                        game.showOverlayUI();
                    }

                    //>>> MOVE LEFT
                } else if ((keyCode == KeyEvent.VK_A && wasdNavSetting
                            .equals("enabled") && !libraryUI.getSearchBar()
                            .isFocusOwner())
                           || keyCode == KeyEvent.VK_LEFT) {

                    if (logger.isDebugEnabled()) {
                        logger.debug("A key pressed");
                    }

                    int i = 0;

                    while (i < comp.size() && !selectedGameFound
                           && !(comp.get(i) instanceof GamePlaceholder)) {
                        game = (Game) comp.get(i);
                        if (game.isSelected()) {
                            selectedGameFound = true;

                            if (logger.isDebugEnabled()) {
                                logger.debug("index = " + i);
                                logger.debug(game.getName()
                                             + " is selected in the library");
                            }
                        } else {
                            i++;
                        }

                    }

                    if (!cursorMoved && selectedGameFound) {
                        int[] columnAndRow = grid.getColumnAndRow(i + 1);
                        int col = columnAndRow[0];
                        int row = columnAndRow[1];

                        if (logger.isDebugEnabled()) {
                            logger.debug("Col = " + col);
                            logger.debug("Row = " + row);
                        }

                        // Check to see if the setSelected game is not the first game in the grid
                        if (col > 1 || (col == 1 && row > 1)) {
                            System.out.println("Cursor is moving left!");
                            visibleGridIndex = gridManager.getVisibleGridIndex();

                            if (logger.isDebugEnabled()) {
                                logger.debug("Cursor is moving left");
                                logger.debug(
                                        "visible grid after moving right = "
                                        + visibleGridIndex);
                            }

                            game.hideOverlayUI();
                            Game newGame = (Game) comp.get(i - 1);
                            gridManager.unselectPrevious(null);
                            newGame.showOverlayUI();
                            cursorMoved = true;
                        } else if (col == 1 && row == 1) {

                            if (gridManager.getArray().indexOf(
                                    pnlGameGridContainer
                                    .getComponent(1)) > 0) {
                                libraryUI.moveGridLeft();
                                /*
                                 * get the index of the grid that is currently
                                 * displayed
                                 */
                                visibleGridIndex = gridManager
                                        .getVisibleGridIndex();

                                if (logger.isDebugEnabled()) {
                                    logger
                                            .debug(
                                                    "visible grid after moving right = "
                                                    + visibleGridIndex);
                                }

                                currentIndex = gridManager.getArray()
                                        .indexOf(pnlGameGridContainer
                                                .getComponent(1));
                                /*
                                 * get the grid that is currently displayed
                                 */
                                grid = gridManager.getGrid(currentIndex);

                                /*
                                 * get an array of all the components in the grid
                                 */
                                comp = grid.getArray();

                                // Check if GamePlaceholder is to the right.
                                if (!(comp.get(comp.size() - 1) instanceof GamePlaceholder)) {
                                    game.hideOverlayUI();
                                    Game newGame = (Game) comp.get(comp.size()
                                                                   - 1);
                                    gridManager.unselectPrevious(null);
                                    newGame.showOverlayUI();
                                }
                            } else {
                                if (logger.isDebugEnabled()) {
                                    logger.debug(
                                            "Cursor cannot move any further left!");
                                }
                            }

                        }
                    } else if (!selectedGameFound
                               && (comp.get(0) instanceof Game)) {
                        game = (Game) comp.get(0);
                        game.showOverlayUI();
                    }

                    // >>> MOVE RIGHT
                } else if ((keyCode == KeyEvent.VK_D && wasdNavSetting
                            .equals("enabled") && !libraryUI.getSearchBar()
                            .isFocusOwner())
                           || keyCode == KeyEvent.VK_RIGHT) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("D key pressed");
                    }

                    int i = 0;

                    while (i < comp.size() && !selectedGameFound
                           && !(comp.get(i) instanceof GamePlaceholder)) {
                        game = (Game) comp.get(i);
                        if (game.isSelected()) {
                            selectedGameFound = true;

                            if (logger.isDebugEnabled()) {
                                logger.debug("index = " + i);
                                logger.debug(game.getName()
                                             + " is selected in the library");
                            }
                        } else {
                            i++;
                        }

                    }

                    if (!cursorMoved && selectedGameFound) {
                        int[] columnAndRow = grid.getColumnAndRow(i + 1);
                        int col = columnAndRow[0];
                        int row = columnAndRow[1];

                        if (logger.isDebugEnabled()) {
                            logger.debug("Col = " + col);
                            logger.debug("Row = " + row);
                        }

                        // Check to see if the setSelected is not the last game in the grid
                        if ((col < grid.getCol() || (col == grid.getCol() && row
                                                                             < grid
                                                     .getRow()))
                            && comp.size() > i + 1) {

                            if (logger.isDebugEnabled()) {
                                logger.debug("Cursor is moving right!");
                                logger.debug(game.getName()
                                             + " is Last Game in This Grid!");
                            }

                            Game newGame;

                            // Get the next object
                            Object obj = comp.get(i + 1);
                            if (obj instanceof Game) {

                                if (logger.isDebugEnabled()) {
                                    logger.debug("Object is a game");
                                }

                                newGame = (Game) obj;
                                gridManager.unselectPrevious(null);
                                newGame.showOverlayUI();
                                cursorMoved = true;
                            } else {
                                if (logger.isDebugEnabled()) {
                                    logger.debug("Object is an add game icon");
                                }
                            }

                            // else check to see if the setSelected game is the last game in the grid
                        } else if (col == grid.getCol() && row == grid.getRow()) {

                            if (logger.isDebugEnabled()) {
                                logger.debug(
                                        "Cursor cannot move any further right! Grid needs to move right");
                            }

                            // check to see if the the current grid is the last grid
                            if (gridManager.getVisibleGridIndex() < (gridManager
                                                                     .getNumberOfGrids())
                                && !(comp.get(0) instanceof GamePlaceholder)) {

                                if (logger.isDebugEnabled()) {
                                    logger.debug("This is not the last grid");
                                }

                                libraryUI.moveGridRight();

                                /*
                                 * get the index of the grid that is currently
                                 * displayed
                                 */
                                visibleGridIndex = gridManager
                                        .getVisibleGridIndex();

                                if (logger.isDebugEnabled()) {
                                    logger
                                            .debug(
                                                    "visible grid after moving right = "
                                                    + visibleGridIndex);
                                }

                                currentIndex = gridManager.getArray()
                                        .indexOf(pnlGameGridContainer
                                                .getComponent(1));

                                /*
                                 * get the grid that is currently displayed
                                 */
                                grid = gridManager.getGrid(currentIndex);

                                /*
                                 * get an array of all the components in the grid
                                 */
                                comp = grid.getArray();

                                Game newGame = (Game) comp.get(0);

                                newGame.requestFocus();
                                newGame.unSelectPrevious();
                                newGame.revalidate();
                                newGame.showOverlayUI();

                            } else {
                                if (logger.isDebugEnabled()) {
                                    logger
                                            .debug(
                                                    "Cannot move to the grid to the right."
                                                    + " No more grids!");
                                }
                            }

                        }
                    } else if (!selectedGameFound
                               && (comp.get(0) instanceof Game)) {
                        game = (Game) comp.get(0);
                        game.showOverlayUI();
                    }
                } else if (keyCode == KeyEvent.VK_ESCAPE) {
                    coreUI.getInputController().clearListener_A_Button();
                    coreUI.showExitDialog();
                }
            }

        }

    }

    public class GridMouseWheelListener implements MouseWheelListener {

        private GridManager gridManager;

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {

            int currentIndex;
            gridManager = libraryUI.getGridManager();

            int numberClicks = e.getWheelRotation();

            if (logger.isDebugEnabled()) {
                logger.debug("Mouse wheel moved " + numberClicks);
            }

            // -
            // Get The Index of The Current Panel Being Displayed
            // Refer too GridManager array of All panels to find it
            // GameBack is the Panel Containing all the game grids
            // -
            currentIndex = gridManager.getArray().indexOf(libraryUI
                    .getGameGridContainer()
                    .getComponent(1));
            libraryUI.setCurrentIndex(currentIndex);

            if (numberClicks < 0) {
                if (currentIndex > 0) {
                    libraryUI.moveGridLeft();

                }
            } else if (numberClicks > 0) {
                if (currentIndex < (gridManager.getNumberOfGrids() - 1)) {
                    libraryUI.moveGridRight();

                }
            }

        }
    }

    public class GridMoveActionListener implements ActionListener {

        private GridManager gridManager;

        private final int key;

        public GridMoveActionListener(int key) {
            this.key = key;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int currentIndex;
            gridManager = libraryUI.getGridManager();

            if (logger.isDebugEnabled()) {
                logger.debug("Grid Move " + key);
            }

            // -
            // Get The Index of The Current Panel Being Displayed
            // Refer too GridManager array of All panels to find it
            // GameBack is the Panel Containing all the game grids
            // -
            currentIndex = gridManager.getArray().indexOf(libraryUI
                    .getGameGridContainer()
                    .getComponent(1));
            libraryUI.setCurrentIndex(currentIndex);

            if (key == KeyEvent.VK_KP_RIGHT) {
                if (currentIndex > 0) {
                    libraryUI.moveGridLeft();

                }
            } else if (key == KeyEvent.VK_KP_LEFT) {
                if (currentIndex < (gridManager.getNumberOfGrids() - 1)) {
                    libraryUI.moveGridRight();

                }
            }

        }
    }

    //
    // Library Grid Search
    //
    public class ResetSearchHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                libraryLogic.getGridSearch().restoreGrid();
            } catch (MalformedURLException ex) {
                logger.error(ex);
            }
            libraryLogic.getGridSearch().resetAppendedName();
            libraryUI.getSearchBar().setText("");
            libraryUI.getSearchBar().setText("Just Start Typing...");
            libraryUI.getSearchBar().setForeground(Color.darkGray);
            libraryUI.getSearchBarBG()
                    .setImage("library_searchBar_inactive.png");
            libraryUI.getSearchButtonBG().removeAll();
            libraryUI.getSearchButtonBG().add(libraryUI.getSearchButton(),
                    BorderLayout.NORTH);
            libraryUI.getSearchButtonBG().revalidate();
            libraryUI.getGameGridContainer().revalidate();
            libraryUI.getCoreUI().getBackgroundImagePane()
                    .requestFocusInWindow();
        }
    }

    public class SearchSelectHandler extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            if (libraryUI.getSearchBar().getText().equals(
                    "Just Start Typing...")) {
                libraryUI.getSearchBar().setText("");
                libraryUI.getSearchBar().setForeground(Color.gray);
                libraryUI.getSearchBarBG().setImage(
                        "library_searchBar_active.png");
                libraryUI.getSearchButtonBG().removeAll();
                libraryUI.getSearchButtonBG().add(libraryUI
                        .getRemoveSearchButton(), BorderLayout.NORTH);
                libraryUI.getSearchButtonBG().revalidate();
                libraryLogic.getGridSearch().resetAppendedName();
            }
        }
    }

    public class SearchButtonHandler implements ActionListener {
        // Handles the Search Button Besides the Search Box

        @Override
        // Simply Requests focus and resets append string
        public void actionPerformed(ActionEvent e) {

            libraryUI.getSearchBar().requestFocus();
            libraryUI.getSearchBar().setText("");
            libraryUI.getSearchBar().setForeground(Color.gray);
            libraryUI.getSearchBarBG().setImage("library_searchBar_active.png");
            libraryUI.getSearchButtonBG().removeAll();
            libraryUI.getSearchButtonBG().add(libraryUI.getRemoveSearchButton(),
                    BorderLayout.NORTH);
            libraryUI.getSearchButtonBG().revalidate();
        }
    }

    public class SearchLostFocusHandler implements FocusListener {

        @Override
        public void focusGained(FocusEvent e) {
        }

        @Override
        public void focusLost(FocusEvent e) {
            libraryUI.getSearchBarBG().setImage("library_searchBar_active.png");
        }
    }

    public class SearchFocusHandler implements FocusListener {

        private JTextField SearchBar;

        private JButton SearchButton;

        public SearchFocusHandler() {
            this.SearchBar = libraryUI.getSearchBar();
            this.SearchButton = libraryUI.getSearchButton();

        }

        @Override
        // If Focus was not gained thru the search button, then
        // Reset text and append string
        public void focusGained(FocusEvent e) {
            if (libraryUI.getSearchBar().getText().equals(
                    "Just Type To Search...")) {
                if (e.getOppositeComponent() == SearchButton) {
                    SearchBar.setText("");
                    libraryLogic.getGridSearch().resetAppendedName();
                    libraryUI.getSearchBar().setForeground(Color.gray);
                    libraryUI.getSearchBarBG().setImage(
                            "library_searchBar_active.png");
                    libraryUI.getSearchButtonBG().removeAll();
                    libraryUI.getSearchButtonBG().add(libraryUI
                            .getRemoveSearchButton(), BorderLayout.NORTH);
                    libraryUI.getRemoveSearchButton()
                            .addActionListener(
                                    new ResetSearchHandler());
                }
            }
        }

        @Override
        public void focusLost(FocusEvent e) {

            if (libraryUI.getSearchBar().getText().equals("")) {

                // Make sure Search button had no effect
                if (e.getOppositeComponent() != SearchButton) {
                    // if focus lost then searches thru all Grid Panels, then inside each grid
                    try {
                        for (int i = 0; i < libraryLogic.getGridSearch()
                             .getGridManager()
                             .getArray()
                             .size(); i++) {
                            for (int j = 0; j < libraryLogic.getGridSearch()
                                 .getGridManager()
                                 .getGrid(
                                         i).getArray().size(); j++) {
                                // If the focus was not lost due to a GameCover Obj in the Search Grid

                                if (e.getOppositeComponent() instanceof GamePlaceholder) {
                                    if (e.getOppositeComponent()
                                        != (Game) libraryLogic
                                            .getGridSearch()
                                            .getGridManager()
                                            .getGrid(i).getArray().get(j)) {
                                        if (logger.isDebugEnabled()) {
                                            logger.debug(e
                                                    .getOppositeComponent());
                                        }

                                        // Attempt to restore to GameCover Library Grid
                                        try {
                                            libraryLogic.getGridSearch()
                                                    .restoreGrid();
                                        } catch (MalformedURLException ex) {
                                            logger.error(ex);
                                        }
                                        // Reset Search Box and append string
                                        libraryLogic.getGridSearch()
                                                .resetAppendedName();

                                    }
                                }
                            }
                        }
                    } catch (NullPointerException ex) {
                        for (int i = 0; i < libraryUI.getGridManager()
                             .getArray()
                             .size(); i++) {
                            for (int j = 0; j < libraryUI.getGridManager()
                                 .getGrid(i).getArray().size(); j++) {
                                // If the focus was not lost due to a GameCover Obj in the Search Grid

                                if (e.getOppositeComponent() instanceof GamePlaceholder) {
                                    if (e.getOppositeComponent()
                                        != (Game) libraryUI
                                            .getGridManager()
                                            .getGrid(i).getArray().get(j)) {
                                        if (logger.isDebugEnabled()) {
                                            logger.debug(e
                                                    .getOppositeComponent());
                                        }

                                        // Attempt to restore to GameCover Library Grid
                                        try {
                                            libraryLogic.getGridSearch()
                                                    .restoreGrid();
                                        } catch (MalformedURLException exx) {
                                            logger.error(exx);
                                        }
                                        // Reset Search Box and append string
                                        libraryLogic.getGridSearch()
                                                .resetAppendedName();

                                    }
                                }
                            }
                        }

                    }

                    SearchBar.setText("Just Start Typing...");
                    libraryUI.getSearchBar().setForeground(Color.darkGray);
                    libraryUI.getSearchBarBG()
                            .setImage("library_searchBar_inactive.png");
                    libraryUI.getSearchButtonBG().removeAll();
                    libraryUI.getSearchButtonBG().add(libraryUI
                            .getSearchButton(), BorderLayout.NORTH);
                    libraryUI.getCoreUI().getBackgroundImagePane()
                            .requestFocusInWindow();
                }
            }
        }
    }

    public class SearchBoxHandler extends KeyAdapter {
        // Handles Typing In Search Box, when it is in focus

        int count;

        @Override
        public void keyPressed(KeyEvent e) {

            count = 0;

            // This activates for any letter number or space key
            libraryUI.getSearchBar().setForeground(Color.gray);
            libraryUI.getSearchBarBG().setImage("library_searchBar_active.png");
            if (!libraryUI.isAddGameUIVisible()) {
                if (e.getKeyCode() == KeyEvent.VK_A
                    || e.getKeyCode() == KeyEvent.VK_B
                    || e.getKeyCode() == KeyEvent.VK_C
                    || e.getKeyCode() == KeyEvent.VK_D
                    || e.getKeyCode() == KeyEvent.VK_E
                    || e.getKeyCode() == KeyEvent.VK_F
                    || e.getKeyCode() == KeyEvent.VK_G
                    || e.getKeyCode() == KeyEvent.VK_H
                    || e.getKeyCode() == KeyEvent.VK_I
                    || e.getKeyCode() == KeyEvent.VK_J
                    || e.getKeyCode() == KeyEvent.VK_K
                    || e.getKeyCode() == KeyEvent.VK_L
                    || e.getKeyCode() == KeyEvent.VK_M
                    || e.getKeyCode() == KeyEvent.VK_N
                    || e.getKeyCode() == KeyEvent.VK_O
                    || e.getKeyCode() == KeyEvent.VK_P
                    || e.getKeyCode() == KeyEvent.VK_Q
                    || e.getKeyCode() == KeyEvent.VK_R
                    || e.getKeyCode() == KeyEvent.VK_S
                    || e.getKeyCode() == KeyEvent.VK_T
                    || e.getKeyCode() == KeyEvent.VK_U
                    || e.getKeyCode() == KeyEvent.VK_V
                    || e.getKeyCode() == KeyEvent.VK_W
                    || e.getKeyCode() == KeyEvent.VK_X
                    || e.getKeyCode() == KeyEvent.VK_Y
                    || e.getKeyCode() == KeyEvent.VK_Z
                    || e.getKeyCode() == KeyEvent.VK_SPACE
                    || e.getKeyCode() == KeyEvent.VK_1
                    || e.getKeyCode() == KeyEvent.VK_2
                    || e.getKeyCode() == KeyEvent.VK_3
                    || e.getKeyCode() == KeyEvent.VK_4
                    || e.getKeyCode() == KeyEvent.VK_5
                    || e.getKeyCode() == KeyEvent.VK_6
                    || e.getKeyCode() == KeyEvent.VK_7
                    || e.getKeyCode() == KeyEvent.VK_8
                    || e.getKeyCode() == KeyEvent.VK_9
                    || e.getKeyCode() == KeyEvent.VK_0
                    || e.getKeyCode() == KeyEvent.VK_QUOTE
                    || e.getKeyCode() == KeyEvent.VK_PERIOD) {

                    count++;

                    //Sends the key to the search engine to be appended and check for match
                    libraryLogic.getGridSearch().typedChar(e.getKeyChar());
                } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    // If backspace is pressed tell search engine to search for name - 1 character
                    libraryLogic.getGridSearch().removeChar(e.getKeyChar());

                }
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {

            super.keyTyped(e);
        }
    }

    public class SearchRefocusListener extends AbstractAction {
        //Handles When User Starts Typing While Components other than the
        //Search Box are in focus.
        //Must get first key typed and put it in the searchbox
        //Then set focus to the searchbox

        private JTextField SearchBar;

        private final int keyCode;

        public SearchRefocusListener(int KeyCode) {
            this.SearchBar = libraryUI.getSearchBar();
            this.keyCode = KeyCode;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //pressing any Number or Letter can activate this
            if (!libraryUI.isAddGameUIVisible()
                && !libraryUI.isEditGameUIVisible()
                && !libraryUI.isEditGameCoverUIVisible()
                && !SearchBar.isFocusOwner()) {
                if (keyCode == KeyEvent.VK_B
                    || keyCode == KeyEvent.VK_C
                    || keyCode == KeyEvent.VK_E
                    || keyCode == KeyEvent.VK_F
                    || keyCode == KeyEvent.VK_G
                    || keyCode == KeyEvent.VK_H
                    || keyCode == KeyEvent.VK_I
                    || keyCode == KeyEvent.VK_J
                    || keyCode == KeyEvent.VK_K
                    || keyCode == KeyEvent.VK_L
                    || keyCode == KeyEvent.VK_M
                    || keyCode == KeyEvent.VK_N
                    || keyCode == KeyEvent.VK_O
                    || keyCode == KeyEvent.VK_P
                    || keyCode == KeyEvent.VK_Q
                    || keyCode == KeyEvent.VK_R
                    || keyCode == KeyEvent.VK_T
                    || keyCode == KeyEvent.VK_U
                    || keyCode == KeyEvent.VK_V
                    || keyCode == KeyEvent.VK_X
                    || keyCode == KeyEvent.VK_Y
                    || keyCode == KeyEvent.VK_Z
                    || keyCode == KeyEvent.VK_1
                    || keyCode == KeyEvent.VK_2
                    || keyCode == KeyEvent.VK_3
                    || keyCode == KeyEvent.VK_4
                    || keyCode == KeyEvent.VK_5
                    || keyCode == KeyEvent.VK_6
                    || keyCode == KeyEvent.VK_7
                    || keyCode == KeyEvent.VK_8
                    || keyCode == KeyEvent.VK_9
                    || keyCode == KeyEvent.VK_0
                    || keyCode == KeyEvent.VK_QUOTE
                    || keyCode == KeyEvent.VK_PERIOD
                    || (libraryUI.getStorage().getStoredSettings()
                        .getSettingValue(SettingsLogic.WASD_NAV_SETTING).equals(
                        "disabled")
                        && (keyCode == KeyEvent.VK_W
                            || keyCode == KeyEvent.VK_A
                            || keyCode == KeyEvent.VK_S
                            || keyCode == KeyEvent.VK_D))) {

                    SearchBar.setEnabled(false);

                    //Clear appended text if there is anything still in there
                    libraryLogic.getGridSearch().resetAppendedName();


                    libraryUI.getSearchBar().setForeground(Color.gray);
                    libraryUI.getSearchBarBG().setImage(
                            "library_searchBar_active.png");
                    libraryUI.getSearchButtonBG().removeAll();
                    libraryUI.getSearchButtonBG().add(libraryUI
                            .getRemoveSearchButton(), BorderLayout.NORTH);
                    libraryUI.getRemoveSearchButton()
                            .addActionListener(
                                    new ResetSearchHandler());


                    SearchBar.setText(String.valueOf(KeyEvent
                            .getKeyText(keyCode).toLowerCase().toCharArray()[0]));
                    //Set first character of Search Box to the key typed
                    libraryLogic.getGridSearch().typedChar(KeyEvent.getKeyText(
                            keyCode).toLowerCase().toCharArray()[0]);


                    SearchBar.requestFocusInWindow();

                    AThreadWorker pauseEnter = new AThreadWorker(
                            new ActionListener() {

                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    try {
                                        Thread.sleep(60);
                                    } catch (InterruptedException ex) {
                                        java.util.logging.Logger.getLogger(
                                                LibraryHandler.class.getName())
                                        .log(Level.SEVERE, null, ex);
                                    }


                                    SearchBar.setEnabled(true);

                                }
                            });

                    pauseEnter.startOnce();

                }
            }
        }
    }

    //
    // Add & Edit Game UI
    //
    public class GameSearchBoxFocusHandler implements FocusListener {

        private final JTextField txtField;

        private final AImagePane txtBackground;

        private final GameSearch gameSearch;

        public GameSearchBoxFocusHandler(JTextField textField,
                                         AImagePane txtBackground,
                                         GameSearch searchEngine) {

            this.txtField = textField;
            this.txtBackground = txtBackground;
            this.gameSearch = searchEngine;

        }

        @Override
        public void focusGained(FocusEvent e) {
            if (!(txtBackground instanceof ATextField)) {
                txtBackground.setImage(
                        "addUI_text_active.png");
                txtBackground.repaint();
            }
            txtField.setCaretColor(Color.cyan);
            if (txtField.getText().equals(
                    GameSearch.DEFAULT_SEARCH_TEXT) || txtField.getText()
                    .equals(
                            GameSearch.DEFAULT_SEARCH_TEXT2)
                || txtField.getText().equals("")) {

                txtField.setText("");

                gameSearch.resetCover();
                txtField.setForeground(new Color(23, 139, 255));


            }

        }

        @Override
        public void focusLost(FocusEvent e) {

            if (e.getOppositeComponent() instanceof JList || e
                    .getOppositeComponent() instanceof JFileChooser == false) {
                try {
                    libraryLogic.getGridSearch().restoreGrid();
                } catch (MalformedURLException ex) {
                    logger.error(ex);
                }
                if (txtField.getText().length() < 1) {

                    txtField.setForeground(Color.darkGray);
                    txtField.setText(
                            GameSearch.DEFAULT_SEARCH_TEXT);

                    if (!(txtBackground instanceof ATextField)) {
                        txtBackground.setImage(
                                "addUI_text_inactive.png");
                    }
                }

            }
        }
    }

    public class ExecutableFilterHandler extends FileFilter {

        private AuroraCoreUI coreUI;

        public ExecutableFilterHandler() {
            this.coreUI = libraryUI.getCoreUI();
        }

        @Override
        public boolean accept(File file) {
            if (file.isDirectory()) {

                return true;
            }

            String extension = AFileManager.getExtension(file);
            if (extension != null) {
                if (extension.equals("exe") || extension.equals("app")
                    || extension.equals("lnk")
                    || extension.equals("url")
                    || extension.equals("bat")) {

                    return true;
                } else {
                    return false;
                }
            } else if (coreUI.getOS().indexOf("nix") >= 0 || coreUI.getOS()
                    .indexOf("nux") >= 0) {

                return true;
            }

            return false;
        }

        @Override
        public String getDescription() {
            return "Executable Files & Shortcuts";
        }
    }

    public class ExecutableChooserHandler implements ActionListener {

        private JFileChooser gameLocator;

        public ExecutableChooserHandler(JFileChooser locator) {
            gameLocator = locator;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            // Check if selected location is valid
            if (gameLocator.getSelectedFile() != null && AFileManager.checkFile(
                    gameLocator.getSelectedFile().getAbsolutePath())) {
                libraryUI
                        .getAddGameUI().setCurrentGameLocation(gameLocator
                                .getSelectedFile().getPath());
                libraryUI.getAddGameUI().setGameLocationIndicator(true);

            } else {
                libraryUI.getAddGameUI().setGameLocationIndicator(false);
            }

            libraryLogic.checkManualAddGameStatus();
        }
    }

    public class GameSearchBoxChangeHandler implements DocumentListener {

        private final GameSearch gameSearch;
        //Handles Typing In Search Box, when it is in focus

        private final JTextField textField;

        public GameSearchBoxChangeHandler(GameSearch searchEngine,
                                          JTextField TextField) {
            this.gameSearch = searchEngine;
            this.textField = TextField;
        }

        @Override
        public void insertUpdate(DocumentEvent e) {

            libraryUI.getSearchBar().setForeground(Color.darkGray);
            libraryUI.getSearchBarBG().setImage("library_searchBar_active.png");

            gameSearch
                    .setAppendedName(textField.getText());
        }

        @Override
        public void removeUpdate(DocumentEvent e) {

            libraryUI.getSearchBar().setForeground(Color.darkGray);
            libraryUI.getSearchBarBG().setImage("library_searchBar_active.png");

            gameSearch
                    .setAppendedName(textField.getText());
        }

        @Override
        public void changedUpdate(DocumentEvent e) {

            libraryUI.getSearchBar().setForeground(Color.darkGray);
            libraryUI.getSearchBarBG().setImage("library_searchBar_active.png");

            gameSearch
                    .setAppendedName(textField.getText());

        }
    }

    public class GameSearchBoxClear implements ActionListener {

        private final JTextField txtField;

        private final GameSearch gameSearch;

        public GameSearchBoxClear(JTextField searchField,
                                  GameSearch searchEngine) {
            txtField = searchField;
            gameSearch = searchEngine;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            txtField.setText("");
            gameSearch.resetCover();
            txtField.requestFocusInWindow();
        }
    }

    public class GameListRender extends DefaultListCellRenderer {

        private static final long serialVersionUID = 1L;

        public GameListRender() {
            setOpaque(true);
        }

        @Override
        public Component getListCellRendererComponent(JList list, Object value,
                                                      int index,
                                                      boolean isSelected,
                                                      boolean cellHasFocus) {

            setComponentOrientation(list.getComponentOrientation());

            Color bg = null;
            Color fg = null;

            JList.DropLocation dropLocation = list.getDropLocation();
            if (dropLocation != null && !dropLocation.isInsert()
                && dropLocation.getIndex() == index) {


                fg = UIManager.getColor("List.dropCellForeground");
                bg = UIManager.getColor("List.dropCellBackground");

                isSelected = true;
            }

            if (isSelected) {
                setBackground(bg == null ? list.getSelectionBackground() : bg);
                setForeground(fg == null ? list.getSelectionForeground() : fg);
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            if (value instanceof Icon) {
                setIcon((Icon) value);
                setText("");
            } else {
                setIcon(null);
                setText((value == null) ? "" : value.toString());
            }

            setEnabled(list.isEnabled());
            setFont(list.getFont());

            Border border = BorderFactory.createEmptyBorder(3, 10, 3,
                    2);
            if (isSelected) {
            } else {
                setBorder(border);
            }

            return this;

        }
    }

    public class GameSearchBoxMouseHandler extends MouseAdapter {

        private final JTextField txtField;

        private final GameSearch gameSearch;

        private final AImagePane searchBG;

        public GameSearchBoxMouseHandler(JTextField searchField,
                                         AImagePane searchBackground,
                                         GameSearch searchEngine) {
            txtField = searchField;
            gameSearch = searchEngine;
            searchBG = searchBackground;
        }

        @Override
        public void mousePressed(MouseEvent e) {

            if (txtField.getText().equals(
                    GameSearch.DEFAULT_SEARCH_TEXT) || txtField.getText()
                    .equals(
                            GameSearch.DEFAULT_SEARCH_TEXT2)) {
                txtField.requestFocus();
                txtField.setText("");
                gameSearch.resetCover();
                txtField.setForeground(new Color(23, 139,
                        255));
                searchBG.setImage(
                        "addUI_text_active.png");
            }
        }
    }

    public class GameSearchButtonListener implements ActionListener {

        private final AImage icon;

        private final GameSearch gameSearch;

        public GameSearchButtonListener(GameSearch search, AImage icon,
                                        AButton btn) {
            this.gameSearch = search;
            this.icon = icon;

            gameSearch.setStatusIcon(icon, btn);

        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (icon.getImgURl().equals("addUI_img_autoSearchOff.png")) {

                gameSearch.enableSearch();

                ToolTipManager.sharedInstance()
                        .setLightWeightPopupEnabled(false);
                ToolTipManager.sharedInstance().registerComponent(((AButton) e
                                                                   .getSource()));
                if (main.LAUNCHES < 5) {
                    ((AButton) e.getSource()).setToolTipText(
                            "Enable AuroraCoverDB");
                }

            } else {

                gameSearch.disableSearch();

                if (main.LAUNCHES < 5) {
                    ((AButton) e.getSource()).setToolTipText(
                            "Disable AuroraCoverDB");
                }

            }

        }

    }

    //
    // Manual Add Game UI
    //
    public class AddToLibraryButtonHandler implements ActionListener {

        private GridManager gridManager;

        private JPanel GameBack;

        private MoveToGrid GridMove;

        private AuroraStorage storage;

        private String currentPath;

        private final GameSearch gameSearch;

        public AddToLibraryButtonHandler(GameSearch searchEngine) {
            this.gameSearch = searchEngine;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            final Game game = gameSearch.getCurrentlySearchedGame();

            AThreadWorker add = new AThreadWorker(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                     // Save game being added to library


                    gridManager = libraryUI.getGridManager();
                    storage = libraryUI.getStorage();
                    GameBack = libraryUI.getGameGridContainer();

                    if (storage.getStoredSettings().getSettingValue(
                            "organize") == null) {
                        storage.getStoredSettings().saveSetting(
                                "organize",
                                "favorite");
                    }

                    // Hide Add Game panel after animating button up
                    libraryLogic.animateAddButtonUp();
                    libraryLogic.getAddGameToLibButtonAnimator()
                            .appendPostAnimationListener(
                                    new APostHandler() {
                                        @Override
                                        public void doAction() {

                                            libraryUI.getAddGameUI()
                                            .hideAddGameUI();

                                        }
                                    });


                    // If in Manual mode Save current game to storage
                    if (libraryUI.getAddGameUI().isManualMode()) {

                        currentPath = libraryUI.getAddGameUI()
                                .getCurrentGameLocation();
                        game.setGamePath(currentPath);
                        game.setLibraryLogic(libraryLogic);

                        if (game.getGameName() == null) {
                            game.setGameName(gameSearch.getAppendedName());
                        }

                        if (!gridManager.isDupicate(game)) {
                            storage.getStoredLibrary()
                                    .SaveGame(game);

                        } else {
                            ADialog info = new ADialog(ADialog.aDIALOG_WARNING,
                                    "Cannot Add Duplicate Game",
                                    libraryUI
                                    .getCoreUI()
                                    .getRegularFont()
                                    .deriveFont(Font.BOLD, 28));

                            info.showDialog();
                            info.setVisible(true);

                            gridManager.echoGame(game).showOverlayUI();
                        }

                    } else { // Save all selected games to storage

                        for (int i = 0; i < libraryLogic.getAutoAddCurrentList()
                             .size(); i++) {

                            libraryLogic.getAutoAddCurrentList().get(i)
                                    .setLibraryLogic(libraryLogic);

                            if (!gridManager
                                    .isDupicate(libraryLogic
                                            .getAutoAddCurrentList()
                                            .get(i))) {
                                storage.getStoredLibrary()
                                        .SaveGame(libraryLogic
                                                .getAutoAddCurrentList()
                                                .get(i));
                            }

                        }
                    }

                }
            }, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Add Game To Library Grid
                    String previousLibraryStatus = LibraryUI.lblLibraryStatus
                            .getCurrentText();

                    // Check if in Manual Mode
                    if (libraryUI.getAddGameUI().isManualMode()) {

                        game.setCoverSize(libraryUI.getGameCoverWidth(),
                                libraryUI
                                .getGameCoverHeight());
                        game.disableEditCoverOverlay();
                        if (gridManager.addGame(game)) {

                            // ReOrder library grid if in alphabetic sorting
                            if (storage.getStoredSettings().getSettingValue(
                                    "organize")
                                    .equalsIgnoreCase("alphabetic")) {

                                libraryLogic.addGamesToLibrary();

                            } else {

                                gridManager.finalizeGrid(
                                        new ShowAddGameUIHandler(),
                                        libraryUI
                                        .getGameCoverWidth(), libraryUI
                                        .getGameCoverHeight());

                            }

                            libraryUI.setCurrentIndex(
                                    gridManager.getArray().indexOf(GameBack
                                            .getComponent(1)));

//                            if (!game.isLoaded()) {
                            try {
                                game.update();
                            } catch (MalformedURLException ex) {
                                java.util.logging.Logger.getLogger(
                                        LibraryHandler.class
                                        .getName()).
                                        log(Level.SEVERE, null, ex);
                            }
//                            }

                            game.setSettingsListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    libraryUI.getEditGameUI().showEditGameUI(
                                            (Game) e
                                            .getSource());
                                }
                            });

                            // Transition towards to left most grid to see the game added
                            libraryLogic.getAddGameToLibButtonAnimator()
                                    .appendPostAnimationListener(
                                            new APostHandler() {

                                                @Override
                                                public void doAction() {
                                                    gridManager
                                                    .unselectPrevious(null);
                                                    GridMove = new MoveToGrid(
                                                            game);

                                                    GridMove.runMover();


                                                }
                                            });


                            LibraryUI.lblLibraryStatus
                                    .setForeground(Color.green);
                            LibraryUI.lblLibraryStatus.setText("Added Game");

                            AMixpanelAnalytics mixpanelAnalytics
                                               = new AMixpanelAnalytics(
                                            "f5f777273e62089193a68f99f4885a55");
                            mixpanelAnalytics.addProperty("Game Added", game
                                    .getName());
                            mixpanelAnalytics.sendEventProperty("Added Game");

                            try {
                                Thread.sleep(1100);
                            } catch (InterruptedException ex) {
                                java.util.logging.Logger.getLogger(
                                        LibraryHandler.class.getName()).
                                        log(Level.SEVERE, null, ex);
                            }

                            game.showOverlayUI();

                        }
                    } else { // In Auto Add Mode

                        for (int i = 0; i < libraryLogic.getAutoAddCurrentList()
                             .size(); i++) {

                            // Load game from Selected Game list
                            Game autoGame = libraryLogic.getAutoAddCurrentList()
                                    .get(i);

                            autoGame.setCoverSize(libraryUI.getGameCoverWidth(),
                                    libraryUI
                                    .getGameCoverHeight());

                            if (!autoGame.isLoaded()) {
                                try {
                                    autoGame.update();
                                } catch (MalformedURLException ex) {
                                    java.util.logging.Logger.getLogger(
                                            LibraryHandler.class
                                            .getName()).
                                            log(Level.SEVERE, null, ex);
                                }
                            }

                            autoGame.setSettingsListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {

                                    libraryUI.getEditGameUI().showEditGameUI(
                                            (Game) e
                                            .getSource());

                                }
                            });

                            // -
                            // Add to grid and check which organize method is
                            // used to reorganize library with new games
                            // -
                            if (gridManager.addGame(autoGame)) {

                                libraryUI.setCurrentIndex(
                                        gridManager.getArray().indexOf(GameBack
                                                .getComponent(1)));

                            }

                            if (libraryUI.getAddGameUI().getModelCheckList()
                                    .size() > 0) {
                                // Remove radio button item
                                libraryUI.getAddGameUI().getModelCheckList()
                                        .removeElementAt(
                                                libraryLogic.getAutoGameList()
                                                .indexOf(
                                                        libraryLogic
                                                        .getAutoAddCurrentList()
                                                        .get(i)));
                                // Remove game item
                                libraryLogic.getAutoGameModel().removeElementAt(
                                        libraryLogic.getAutoGameList().indexOf(
                                                libraryLogic
                                                .getAutoAddCurrentList()
                                                .get(i)));
                            }

                            libraryLogic.getAutoGameList().remove(libraryLogic
                                    .getAutoAddCurrentList()
                                    .get(i));

                        }

                        if (storage.getStoredSettings().getSettingValue(
                                "organize")
                                .equalsIgnoreCase("alphabetic")) {

                            libraryLogic.addGamesToLibrary();

                        } else {

                            gridManager.finalizeGrid(
                                    new ShowAddGameUIHandler(),
                                    libraryUI
                                    .getGameCoverWidth(), libraryUI
                                    .getGameCoverHeight());

                        }

                        // Change game display status to green
                        LibraryUI.lblLibraryStatus
                                .setForeground(Color.green);

                        // Display how many games were added with proper grammar
                        if (libraryLogic.getAutoAddCurrentList()
                                .size() > 1) {
                            LibraryUI.lblLibraryStatus.setText("Added "
                                                               + libraryLogic
                                    .getAutoAddCurrentList()
                                    .size() + " Games");
                        } else {
                            LibraryUI.lblLibraryStatus.setText("Added "
                                                               + libraryLogic
                                    .getAutoAddCurrentList()
                                    .size() + " Game");
                        }
                        try {
                            Thread.sleep(1200);
                        } catch (InterruptedException ex) {
                            java.util.logging.Logger.getLogger(
                                    LibraryHandler.class.getName())
                                    .log(Level.SEVERE, null, ex);
                        }

                        LibraryUI.lblLibraryStatus
                                .setText(previousLibraryStatus);
                        LibraryUI.lblLibraryStatus.setForeground(
                                LibraryUI.DEFAULT_LIBRARY_COLOR);

                        // Tell Mixpanel that latest games added were auto added
                        AMixpanelAnalytics mixpanelAnalytics
                                           = new AMixpanelAnalytics(
                                        "f5f777273e62089193a68f99f4885a55");
                        mixpanelAnalytics.addProperty("Auto Added", libraryLogic
                                .getAutoAddCurrentList()
                                .size());
                        mixpanelAnalytics.sendEventProperty("Added Game");
                    }

                }
            });

            add.startOnce();

        }
    }

    public class HideAddGameUIHandler implements ActionListener {

        private LibraryUI libraryUI;

        public HideAddGameUIHandler(LibraryUI gameLibraryUI) {
            this.libraryUI = gameLibraryUI;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            libraryUI.getAddGameUI().hideAddGameUI();
        }
    }

    /**
     * Listener for the btnGoToProgram button to make gameFileChooser point to
     * the Programs folder based on the OS
     */
    public class GoToProgramsListener implements ActionListener {

        private final AuroraCoreUI coreUI;

        private final JFileChooser gameFileChooser_addUI;

        public GoToProgramsListener(AuroraCoreUI coreUI,
                                    JFileChooser fileChooser) {
            this.coreUI = coreUI;
            this.gameFileChooser_addUI = fileChooser;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            // Set File Choosers  location to the Programs/App folder //
            String goToPath = System.getProperty("user.dir");

            if (coreUI.getOS().contains("Windows")) {

                // Check which Programs folder to use, Use x86 one if possible//
                if (System.getenv("ProgramFiles(x86)") != null) {

                    goToPath = System.getenv("ProgramFiles(x86)");

                } else if (System.getenv("ProgramFiles") != null) {

                    goToPath = System.getenv("ProgramFiles");

                }

            } else if (coreUI.getOS().contains("Mac")) {
                goToPath = "/Applications/";
            } else {
                goToPath = "";
            }

            // Set appropriate path, will fall back to user.dir //
            gameFileChooser_addUI.setCurrentDirectory(new File(goToPath));
        }
    }

    /**
     * Listener for the btnGoToProgram button to make gameFileChooser point to
     * the Steam games folder.
     */
    public class GoToSteamListener implements ActionListener {

        private final AuroraCoreUI coreUI;

        private final JFileChooser gameFileChooser_addUI;

        public GoToSteamListener(AuroraCoreUI coreUI, JFileChooser fileChooser) {
            this.coreUI = coreUI;
            this.gameFileChooser_addUI = fileChooser;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Set File Choosers location to folder containing Steam Games //

            if (coreUI.getOS().contains("Windows")) {
                gameFileChooser_addUI.setCurrentDirectory(libraryLogic
                        .fetchSteamDirOnWindows());
            } else if (coreUI.getOS().contains("Mac")) {
                if (AFileManager
                        .checkFile("/Applications/Steam/steamapp/common")) {
                    gameFileChooser_addUI.setCurrentDirectory(new File(
                            "/Applications/Steam/steamapp/common"));
                }
            } else {
                gameFileChooser_addUI.setCurrentDirectory(null);
            }

            coreUI.getFrame().repaint();

        }
    }

    /**
     * .-----------------------------------------------------------------------.
     * | ManualAddHandler
     * .-----------------------------------------------------------------------.
     * |
     * | ActionListener for switching to manual mode
     * |
     * .........................................................................
     *
     */
    public class ManualAddHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            libraryUI.getAddGameUI().switchToManualMode();

        }
    }

    //
    // Auto Add Game UI
    //
    public class CheckListRender extends DefaultListCellRenderer {

        private static final long serialVersionUID = 1L;

        public CheckListRender() {
            setOpaque(false);
        }

        @Override
        public Component getListCellRendererComponent(JList list, Object value,
                                                      int index,
                                                      boolean isSelected,
                                                      boolean cellHasFocus) {

            setComponentOrientation(list.getComponentOrientation());

            Color bg = null;
            Color fg = null;

            JList.DropLocation dropLocation = list.getDropLocation();
            if (dropLocation != null && !dropLocation.isInsert()
                && dropLocation.getIndex() == index) {

                fg = UIManager.getColor("List.dropCellForeground");
                bg = UIManager.getColor("List.dropCellBackground");

                isSelected = true;
            }

            this.setBorder(null);

            if (value instanceof JPanel) {
                return (Component) value;
            } else {
                return this;
            }

        }
    }

    public class AutoGameListlRender extends DefaultListCellRenderer {

        private static final long serialVersionUID = 1L;

        public AutoGameListlRender() {
            setOpaque(true);
        }

        @Override
        public Component getListCellRendererComponent(JList list, Object value,
                                                      int index,
                                                      boolean isSelected,
                                                      boolean cellHasFocus) {

            JLabel label
                   = (JLabel) (!(((JPanel) value).getComponent(0) instanceof AImagePane)
                               ? ((JPanel) value).getComponent(0)
                               : ((JPanel) value)
                               .getComponent(1));

            Color bg = null;
            Color fg = null;

            JList.DropLocation dropLocation = list.getDropLocation();
            if (dropLocation != null && !dropLocation.isInsert()
                && dropLocation.getIndex() == index) {


                fg = UIManager.getColor("List.dropCellForeground");
                bg = UIManager.getColor("List.dropCellBackground");

                isSelected = true;
            }

            if (isSelected) {
                ((JPanel) value).setBackground(bg == null ? list
                        .getSelectionBackground() : bg);

                label.setForeground(fg == null ? list
                        .getSelectionForeground() : fg);
            } else {
                ((JPanel) value).setBackground(list.getBackground());
                label.setForeground(list.getForeground());
            }

            ((JPanel) value).setEnabled(list.isEnabled());

            label.setFont(list.getFont());

            Border border = BorderFactory.createEmptyBorder(1, 5, 0,
                    2);

            ((JPanel) value).setBorder(border);

            return (JPanel) value;

        }
    }

    public class AutoSelectListHandler implements ListSelectionListener {

        private JList gamesList;

        private DefaultListModel listModel;

        private int selectionIndex;

        private String prevSelection;

        private final GameSearch gameSearch;

        public AutoSelectListHandler(GameSearch searchEngine) {
            gameSearch = searchEngine;

        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            gamesList = (JList) e.getSource();
            listModel = (DefaultListModel) ((JList) e.getSource()).getModel();

            if (gamesList.getSelectedIndex() != -1) {

                Object value = listModel.get(gamesList
                        .getSelectedIndex());

                JLabel label
                       = (JLabel) (!(((JPanel) value).getComponent(0) instanceof AImagePane)
                                   ? ((JPanel) value).getComponent(0)
                                   : ((JPanel) value)
                                   .getComponent(1));

                String gameSelected = label.getText();

                Iterator<Game> iter = libraryLogic.getAutoGameList().iterator();
                int i = 0;
                while (iter.hasNext()) {
                    Game game = iter.next();
                    if (game.getGameName().equals(gameSelected)) {
                        break;
                    }
                    i++;
                }

                // Prevent double selection
                if (prevSelection != null && prevSelection.equals(gameSelected)) {
                    selectionIndex++;
                } else {
                    selectionIndex = 0;
                }

                if (selectionIndex == 0) {
                    gameSearch.getSpecificGame(
                            libraryLogic.getAutoGameList().get(i).getBoxArtUrl());
                }

                prevSelection = gameSelected;
            }
        }
    }

    public class AutoClearAllButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            libraryLogic.autoClearAll();
        }
    }

    public class AutoAddAllButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            libraryLogic.autoSelectAll();
        }
    }

    public class AutoRefreshHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            libraryLogic.refreshAutoAdd();
        }
    }

    /**
     * .-----------------------------------------------------------------------.
     * | AutoAddHandler
     * .-----------------------------------------------------------------------.
     * |
     * | ActionListener for switching to auto mode
     * |
     * .........................................................................
     *
     */
    public class AutoAddHandler implements ActionListener {

        private final DefaultListModel model;

        public AutoAddHandler(DefaultListModel listModel) {
            model = listModel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            libraryUI.getAddGameUI().switchToAutoMode();
        }
    }

    /**
     * Listener for when the Add Game To Library Button is pressed in the
     * Add Game UI
     */
    public class SelectListHandler implements ListSelectionListener {

        private JList gamesList;

        private DefaultListModel listModel;

        private final GameSearch gameSearch;

        private Object prevSelection;

        private int selectionIndex;

        public SelectListHandler(GameSearch searchEngine) {
            gameSearch = searchEngine;
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            gamesList = (JList) e.getSource();
            listModel = (DefaultListModel) ((JList) e.getSource()).getModel();
            if (gamesList.getSelectedIndex() != -1) {
                String gameSelected = (String) listModel.get(gamesList
                        .getSelectedIndex());

                // Prevent double selection
                if (prevSelection != null && prevSelection.equals(gameSelected)) {
                    selectionIndex++;
                } else {
                    selectionIndex = 0;
                }

                if (selectionIndex == 0) {
                    gameSearch.searchSpecificGame(gameSelected);
                    libraryUI.getLogic().checkManualAddGameStatus();
                }

                prevSelection = gameSelected;

            }
        }
    }

    public class ShowAddGameUIHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!libraryUI.isAddGameUIVisible()) {

                libraryUI.getAddGameUI().showAddGameUI();

            } else {

                libraryUI.getAddGameUI().hideAddGameUI();
            }

        }
    }

    //
    // Organize UI
    //
    public class ShowOrganizeGameHandler implements ActionListener {

        public ShowOrganizeGameHandler() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (libraryUI.getOrganizeUI().isVisible()) {
                libraryUI.getOrganizeUI().hideOrganizeUI();
            } else {
                libraryUI.getOrganizeUI().showOrganizeUI();
            }
        }
    }

    public class SelectedOrganizeListener implements ActionListener {

        private final ASlickLabel label;

        private final String settingValue;

        private final StoredSettings storage;

        public SelectedOrganizeListener(ASlickLabel lbl, StoredSettings settings,
                                        String SettingValue) {

            label = lbl;
            settingValue = SettingValue;
            storage = settings;

        }

        @Override
        public void actionPerformed(ActionEvent e) {

            String currentVal = storage.getSettingValue("organize");
            storage.saveSetting("organize", settingValue);

            if (!currentVal.trim().equals(settingValue.trim())) {
                libraryLogic.addGamesToLibrary();
            }

            label.setForeground(Color.white);

        }
    }

    public class UnSelectedOrganizeListener implements ActionListener {

        private final ASlickLabel label;

        private final JComponent organizeUI;

        public UnSelectedOrganizeListener(ASlickLabel lbl, JComponent popup) {

            label = lbl;
            organizeUI = popup;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            label.setForeground(new Color(173, 173, 173));

            Timer timer = new Timer(500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    organizeUI.setVisible(false);
                    ((Timer) e.getSource()).stop();
                }
            });

            timer.start();

        }
    }

    public class OrganizeMouseListener implements MouseListener {

        private final ASlickLabel label;

        public OrganizeMouseListener(ASlickLabel label) {
            this.label = label;
        }

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
            if (!((ARadioButton) e.getSource()).isSelected) {
                label.setForeground(Color.white);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (!((ARadioButton) e.getSource()).isSelected) {
                label.setForeground(new Color(173, 173, 173));
            }

        }
    }

    //
    // Edit Game UI
    //
    public class HideEditAddUIHandler implements ActionListener {

        private LibraryUI libraryUI;

        public HideEditAddUIHandler(LibraryUI gameLibraryUI) {
            this.libraryUI = gameLibraryUI;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            libraryUI.getEditGameUI().hideEditGameUI();
        }
    }

    public class GameEditFileChooserHandler implements ActionListener {

        private JFileChooser gameLocator;

        public GameEditFileChooserHandler(JFileChooser locator) {
            gameLocator = locator;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (gameLocator.getSelectedFile() != null && AFileManager.checkFile(
                    gameLocator.getSelectedFile().getAbsolutePath())) {

                libraryUI.getEditGameUI().setGameLocationInicator(true);
                libraryUI.getEditGameUI().getTxtNewLocation_editUI().setText(
                        gameLocator
                        .getSelectedFile().getAbsolutePath());
                libraryUI.getEditGameUI().setEditGameLocationChanged(true);

            } else {
                libraryUI.getEditGameUI().setGameLocationInicator(false);
                libraryUI.getEditGameUI().setEditGameLocationChanged(false);
            }
        }
    }

    public class EditSettingDoneHandler implements ActionListener {

        private MoveToGrid GridMove;

        @Override
        public void actionPerformed(ActionEvent e) {

            AThreadWorker doneTask = new AThreadWorker(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    // Check if valid
                    if (libraryUI.getEditGameUI().isGameLocationStatusEnabled()) {

                        if (libraryUI.getEditGameUI()
                                .isGameLocationChanged()) {

                            // Remove Game
                            libraryUI.getStorage().getStoredLibrary()
                                    .removeGame(libraryUI
                                            .getEditGameUI()
                                            .getCurrentGame_editUI());
                            //Set new path
                            libraryUI.getEditGameUI().getCurrentGame_editUI()
                                    .setGamePath(
                                            libraryUI
                                            .getEditGameUI()
                                            .getTxtNewLocation_editUI()
                                            .getText());
                            // Re Save
                            libraryUI.getStorage().getStoredLibrary()
                                    .SaveGame(libraryUI.getEditGameUI()
                                            .getCurrentGame_editUI());

                            LibraryUI.lblLibraryStatus.setForeground(
                                    Color.orange);
                            LibraryUI.lblLibraryStatus.setText(
                                    "Changed Game Path");

                        }
                    }
                    if (libraryUI.getEditGameUI().isGameCoverChanged()) {


                        // The library status text when completed
                        String completedText = "Changed Game Name";
                        //Set new name
                        String editGameName;
                        if (libraryLogic.getGameSearch_editUI()
                                .getCurrentlySearchedGame().getName()
                            == null
                            || !libraryLogic.getGameSearch_editUI()
                                .isSearchEnabled()) {
                            editGameName = libraryLogic
                                    .getGameSearch_editUI()
                                    .getAppendedName();
                        } else {
                            editGameName = libraryLogic
                                    .getGameSearch_editUI()
                                    .getCurrentlySearchedGame().getName();
                        }

                        if (!libraryUI
                                .getGridManager().isDupicate(editGameName)) {
                            // Remove Game
                            libraryUI.getStorage().getStoredLibrary()
                                    .removeGame(libraryUI
                                            .getEditGameUI()
                                            .getCurrentGame_editUI());
                            //Set new path if new cover art added
                            if (!libraryLogic
                                    .getGameSearch_editUI()
                                    .getCurrentlySearchedGame()
                                    .getBoxArtUrl().equals(
                                            "library_noGameFound.png")) {
                                libraryUI.getEditGameUI()
                                        .getCurrentGame_editUI()
                                        .setCoverUrl(
                                                libraryLogic
                                                .getGameSearch_editUI()
                                                .getCurrentlySearchedGame()
                                                .getBoxArtUrl());
                                completedText = "Changed Game Cover";
                            }

                            libraryUI.getEditGameUI()
                                    .getCurrentGame_editUI()
                                    .setGameName(
                                            editGameName);



                            //refresh
                            libraryUI.getEditGameUI()
                                    .getCurrentGame_editUI().refresh();

                            // Re Save
                            libraryUI.getStorage().getStoredLibrary()
                                    .SaveGame(libraryUI
                                            .getEditGameUI()
                                            .getCurrentGame_editUI());

                            LibraryUI.lblLibraryStatus.setForeground(
                                    Color.orange);
                            LibraryUI.lblLibraryStatus.setText(
                                    completedText);

                            // Transition towards to left most grid to see the game added
                            GridMove = new MoveToGrid(libraryUI
                                    .getEditGameUI().getCurrentGame_editUI());

                            libraryUI.getGridManager().unselectPrevious(null);
                        } else {
                            ADialog info = new ADialog(
                                    ADialog.aDIALOG_WARNING,
                                    "Cannot Add Duplicate Game",
                                    libraryUI
                                    .getCoreUI()
                                    .getRegularFont()
                                    .deriveFont(Font.BOLD, 28));
                            info.showDialog();
                            info.setVisible(true);


                        }
                    }


                    libraryUI.getEditGameUI().hideEditGameUI();

                }
            }, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    libraryUI.getEditGameUI().getCurrentGame_editUI()
                            .getBtnFlip()
                            .getActionListeners()[0].actionPerformed(
                                    null);

                    if (GridMove != null && libraryUI.getEditGameUI()
                            .isGameCoverChanged()) {
                        libraryUI.setCurrentIndex(0);
                        GridMove.runMover();
                    }

                    try {
                        Thread.sleep(1200);
                    } catch (InterruptedException ex) {
                        java.util.logging.Logger.getLogger(LibraryHandler.class
                                .getName()).
                                log(Level.SEVERE, null, ex);
                    }

                    LibraryUI.lblLibraryStatus.setForeground(
                            LibraryUI.DEFAULT_LIBRARY_COLOR);
                    libraryUI.getGridManager().unselectPrevious(null);
                    libraryUI.getEditGameUI().getCurrentGame_editUI()
                            .showOverlayUI();

                    GridMove = null;
                }
            });

            doneTask.startOnce();

        }
    }

    public class GameLocationSettingListener implements ActionListener {

        private final JLabel label;

        public GameLocationSettingListener(JLabel lbl) {
            this.label = lbl;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            label.setForeground(Color.white);

            libraryUI.getEditGameUI().showGameLocationUI();

        }
    }

    public class GameCoverSettingListener implements ActionListener {

        private final JLabel label;

        public GameCoverSettingListener(JLabel lbl) {
            this.label = lbl;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            label.setForeground(Color.white);
            libraryUI.getEditGameUI().showGameCoverUI();

        }
    }

    public class OtherSettingListener implements ActionListener {

        private final JLabel label;

        public OtherSettingListener(JLabel lbl) {
            this.label = lbl;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            label.setForeground(Color.white);
            libraryUI.getEditGameUI().showOtherUI();

        }
    }

    public class UnselectSettingListener implements ActionListener {

        private final JLabel label;

        public UnselectSettingListener(JLabel lbl) {
            this.label = lbl;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            label.setForeground(Color.lightGray);

        }
    }

    //
    // Edit Cover UI
    //
    /**
     * Handles the animation when attempting to close the edit cover UI pane
     */
    public class CloseEditCoverListener implements ActionListener {

        private Window editCoverFrame;

        public CloseEditCoverListener(Window editCoverFrame) {
            this.editCoverFrame = editCoverFrame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {


            libraryUI.getCoreUI().getFrame().setVisible(true);
            final AAnimate frameFadeAnimator = new AAnimate();

            AAnimate editCoverAnimator = new AAnimate(editCoverFrame);

            editCoverAnimator.setInitialLocation(editCoverFrame.getX(),
                    editCoverFrame.getY());

            editCoverAnimator.moveVertical(libraryUI.getCoreUI()
                    .getScreenHeight(), 33);

            libraryUI.setIsEditGameCoverUI_visible(false);

            editCoverAnimator.addPostAnimationListener(new APostHandler() {

                @Override
                public void doAction() {
                    frameFadeAnimator
                            .fadeIn(libraryUI.getCoreUI()
                                    .getFrame());
                }
            });

        }
    }

    /**
     * Listener for when files are dropped on EditCoverUI
     */
    public class EditCoverUIDragedListener implements AFileDrop.Listener {

        private final AImagePane dragPane;

        private AProgressWheel progressWheel;

        private boolean isOccupied;

        private final AImage statusIcon;

        private String coverFileName;

        private int GridBagConstraints;

        public EditCoverUIDragedListener(AImagePane DragPane, AImage statusIcon) {
            this.dragPane = DragPane;
            this.statusIcon = statusIcon;
            isOccupied = false;
        }

        @Override
        public void filesDropped(final File[] files) {
            isOccupied = false;
            progressWheel = new AProgressWheel("Aurora_Loader.png");
            dragPane
                    .setLayout(new BorderLayout(0, 0));

            // Content Pane
            final JPanel contentContainer = new JPanel(new GridBagLayout());


            contentContainer.setOpaque(false);

            contentContainer.add(progressWheel, GridBagConstraints);
            contentContainer.setAlignmentY(JComponent.CENTER_ALIGNMENT);

            // Reset Button
            AButton btnReset = new AButton("app_btn_close_norm.png",
                    "app_btn_close_down.png",
                    "app_btn_close_over.png");

            btnReset.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    dragPane.removeAll();
                    dragPane.setImage("editCoverUI_dragBG.png");
                    dragPane.revalidate();
                    isOccupied = false;
                    statusIcon.setImgURl("addUI_badge_invalid.png");

                }
            });
            final JPanel resetContainer = new JPanel(new BorderLayout());
            resetContainer.setOpaque(false);
            resetContainer.add(btnReset, BorderLayout.NORTH);
            resetContainer.setPreferredSize(new Dimension(55, dragPane
                    .getRealImageHeight()));

            dragPane.add(Box.createVerticalGlue());
            dragPane.add(contentContainer, BorderLayout.CENTER);
            dragPane.setImage("editCoverUI_processBG.png");

            AThreadWorker load = new AThreadWorker(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    AImagePane coverArt = libraryLogic.processNewCoverArtImage(
                            files[0]);

                    coverFileName = coverArt.getImageURL();

                    int scaledHeight = dragPane.getRealImageHeight() - 8;
                    int scaledWidth = (scaledHeight * coverArt
                                       .getRealImageWidth()) / coverArt
                            .getRealImageHeight();

                    coverArt.setImageSize(scaledWidth,
                            scaledHeight);
                    coverArt.setPreferredSize(new Dimension(scaledWidth,
                            scaledHeight));

                    contentContainer.removeAll();
                    contentContainer.add(coverArt);
                    contentContainer.revalidate();

                    dragPane.add(resetContainer, BorderLayout.EAST);
                    dragPane.add(Box.createHorizontalStrut(resetContainer
                            .getPreferredSize().width), BorderLayout.WEST);
                    dragPane.revalidate();

                    statusIcon.setImgURl("addUI_badge_valid.png");
                    contentContainer.revalidate();

                }
            });

            load.startOnce();
            isOccupied = true;

        }

        public boolean isOccupied() {
            return isOccupied;
        }

        public void setIsOccupied(boolean isOccupied) {
            this.isOccupied = isOccupied;
        }

        public String getCoverArtFileName() {
            return coverFileName;
        }

    }

    /**
     * Listener for the Done Button on the EditCoverUI
     */
    public class EditCoverUIDoneListener implements ActionListener {

        private final AImage status;

        private final Game editingGame;

        private final EditCoverUIDragedListener dragListener;

        public EditCoverUIDoneListener(AImage statusImage, Game game,
                                       EditCoverUIDragedListener listener) {
            this.status = statusImage;
            this.editingGame = game;
            this.dragListener = listener;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (dragListener
                    .getCoverArtFileName() == null) {

                libraryLogic.flashInvalidStatus(status);

            } else {

                libraryUI.getEditCoverUI().hideEditCoverFrame();
                libraryLogic.editCover(editingGame, dragListener
                        .getCoverArtFileName());
            }

        }

    }

    //
    // Game Search
    //
    /**
     * Handler added to game covers in GameSearch to allow ability to edit game
     * cover art
     */
    public class GameCoverEditListner implements ActionListener {

        private final Game game;

        public GameCoverEditListner(Game currentGame) {
            game = currentGame;

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!libraryUI.isEditGameCoverUIVisible()) {
                libraryUI.getEditCoverUI().showEditGameCoverUI(game);
            }

        }

    }

}
