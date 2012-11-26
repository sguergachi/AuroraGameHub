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

import aurora.V1.core.AuroraCoreUI;
import aurora.V1.core.AuroraStorage;
import aurora.V1.core.Game;
import aurora.V1.core.GamePlaceholder;
import aurora.V1.core.GameSearch;
import aurora.V1.core.GridAnimation;
import aurora.V1.core.GridManager;
import aurora.V1.core.GridSearch;
import aurora.V1.core.screen_handler.GameLibraryHandler.MoveToLastGrid;
import aurora.V1.core.screen_logic.GameLibraryLogic;
import aurora.V1.core.screen_ui.GameLibraryUI;
import aurora.engine.V1.Logic.AFileManager;
import aurora.engine.V1.Logic.ASimpleDB;
import aurora.engine.V1.Logic.AuroraScreenHandler;
import aurora.engine.V1.Logic.AuroraScreenLogic;
import aurora.engine.V1.UI.AButton;
import aurora.engine.V1.UI.AGridPanel;
import aurora.engine.V1.UI.AHoverButton;
import aurora.engine.V1.UI.AImage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;

/**
 * .------------------------------------------------------------------------.
 * | GameLibraryHandler
 * .------------------------------------------------------------------------.
 * |
 * | This class contains all Listeners/Handlers attached to UI elements
 * | found in GameLibraryUI. The handlers may access the logic or simply
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
public class GameLibraryHandler implements
        AuroraScreenHandler {

    /**
     * GameLibraryLogic instance.
     */
    private GameLibraryLogic libraryLogic;

    /**
     * GameLibraryUI instance.
     */
    private final GameLibraryUI libraryUI;

    private final GridSearch gridSearch;

    private final GameSearch gameSearch;

    private ASimpleDB coverDB;

    /**
     * .-----------------------------------------------------------------------.
     * | GameLibraryHandler(GameLibraryUI)
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
     * @param aLibraryUI GameLibraryUI
     */
    public GameLibraryHandler(final GameLibraryUI aLibraryUI) {
        this.libraryUI = aLibraryUI;

        //* Start Aurora Dabatase connection *//
        try {
            coverDB = new ASimpleDB("AuroraDB", "AuroraTable", false);
        } catch (SQLException ex) {
            Logger.getLogger(GameLibraryUI.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

        this.gridSearch = new GridSearch(libraryUI.getCoreUI(), libraryUI,
                this);
        this.gameSearch = new GameSearch(libraryUI, coverDB,
                libraryUI.getStorage());
    }

    @Override
    public final void setLogic(final AuroraScreenLogic logic) {

        this.libraryLogic = (GameLibraryLogic) logic;

    }

    public class RemoveSearchHandler implements ActionListener {

        private final JTextField SearchBar;

        private final AButton SearchButton;

        public RemoveSearchHandler() {
            this.SearchBar = libraryUI.getSearchBar();
            this.SearchButton = libraryUI.getSearchButton();

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                gridSearch.restoreGrid();
            } catch (MalformedURLException ex) {
                Logger.getLogger(GameLibraryHandler.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
            gridSearch.resetAppendedName();
            SearchBar.setText("Start Typing To Search...");
            libraryUI.getSearchBar().setForeground(Color.darkGray);
            libraryUI.getSearchBar().setFont(libraryUI.getCoreUI()
                    .getDefaultFont().deriveFont(Font.BOLD, 40));
            libraryUI.getSearchBarBG().setImage("SearchBar_inactive.png");
            libraryUI.getSearchButtonBG().removeAll();
            libraryUI.getSearchButtonBG().add(libraryUI.getSearchButton(),
                    BorderLayout.NORTH);
            libraryUI.getCoreUI().getFrame().requestFocus();
            libraryUI.getGameBack().revalidate();
        }
    }

    //////Search Library Bar//////////
    ///What to do if Click on Search Box
    //TODO add aCarousel Handlers
    public class searchSelectHandler extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (libraryUI.getSearchBar().getText().equals(
                    "Start Typing To Search...")) {
                libraryUI.getSearchBar().setText("");
                libraryUI.getSearchBar().setForeground(Color.darkGray);
                libraryUI.getSearchBar().setFont(libraryUI.getCoreUI()
                        .getDefaultFont().deriveFont(Font.BOLD, 44));
                libraryUI.getSearchBarBG().setImage("SearchBar.png");
                libraryUI.getSearchButtonBG().removeAll();
                libraryUI.getSearchButtonBG().add(libraryUI
                        .getRemoveSearchButton(), BorderLayout.NORTH);
                gridSearch.resetAppendedName();
            }
        }
    }

    public class searchButtonHandler implements ActionListener {
        //Handles the Search Button Besides the Search Box

        @Override
        //Simply Requests focus and resets append string
        public void actionPerformed(ActionEvent e) {

            libraryUI.getSearchBar().requestFocus();
            libraryUI.getSearchBar().setText("");
            libraryUI.getSearchBar().setForeground(Color.darkGray);
            libraryUI.getSearchBar().setFont(libraryUI.getCoreUI()
                    .getDefaultFont()
                    .deriveFont(Font.BOLD, 44));
            libraryUI.getSearchBarBG().setImage("SearchBar.png");
            libraryUI.getSearchButtonBG().removeAll();
            libraryUI.getSearchButtonBG().add(libraryUI.getRemoveSearchButton(),
                    BorderLayout.NORTH);
            libraryUI.getRemoveSearchButton()
                    .addActionListener(new RemoveSearchHandler());
        }
    }

    public class searchLostFocusHandler implements FocusListener {

        @Override
        public void focusGained(FocusEvent e) {
        }

        @Override
        public void focusLost(FocusEvent e) {
            libraryUI.getSearchBarBG().setImage("SearchBar.png");
        }
    }

    public class searchFocusHandler implements FocusListener {

        private JTextField SearchBar;

        private JButton SearchButton;

        public searchFocusHandler() {
            this.SearchBar = libraryUI.getSearchBar();
            this.SearchButton = libraryUI.getSearchButton();

        }

        @Override
        //If Focus was not gained thru the search button, then
        //reset text and append string
        public void focusGained(FocusEvent e) {
            if (libraryUI.getSearchBar().getText().equals(
                    "Start Typing To Search...")) {
                if (e.getOppositeComponent() == SearchButton) {
                    SearchBar.setText("");
                    gridSearch.resetAppendedName();
                    libraryUI.getSearchBar().setForeground(Color.darkGray);
                    libraryUI.getSearchBar().setFont(libraryUI.getCoreUI()
                            .getDefaultFont().deriveFont(Font.BOLD, 44));
                    libraryUI.getSearchBarBG().setImage("SearchBar.png");
                    libraryUI.getSearchButtonBG().removeAll();
                    libraryUI.getSearchButtonBG().add(libraryUI
                            .getRemoveSearchButton(), BorderLayout.NORTH);
                    libraryUI.getRemoveSearchButton()
                            .addActionListener(
                            new RemoveSearchHandler());
                }
            }
        }

        @Override
        public void focusLost(FocusEvent e) {

            if (libraryUI.getSearchBar().getText().equals("")) {

                //Make sure Search button had no effect
                if (e.getOppositeComponent() != SearchButton) {
                    //if focus lost then searches thru all Grid Panels, then inside each grid
                    try {
                        for (int i = 0; i < gridSearch.getGridManager()
                                .getArray()
                                .size(); i++) {
                            for (int j = 0; j < gridSearch.getGridManager()
                                    .getGrid(
                                    i).getArray().size(); j++) {
                                //If the focus was not lost due to a GameCover Obj in the Search Grid

                                if (e.getOppositeComponent() instanceof GamePlaceholder) {
                                    if (e.getOppositeComponent()
                                        != (Game) gridSearch.getGridManager()
                                            .getGrid(i).getArray().get(j)) {
                                        System.out.println(e
                                                .getOppositeComponent());
                                        //Attempt to restore to GameCover Library Grid
                                        try {
                                            gridSearch.restoreGrid();
                                        } catch (MalformedURLException ex) {
                                            Logger
                                                    .getLogger(GameLibraryHandler.class
                                                    .getName())
                                                    .log(Level.SEVERE, null, ex);
                                        }
                                        //reset Search Box and append string
                                        gridSearch.resetAppendedName();

                                    }
                                }
                            }
                        }
                    } catch (NullPointerException ex) {
                        for (int i = 0; i < libraryUI.getGridSplit().getArray()
                                .size(); i++) {
                            for (int j = 0; j < libraryUI.getGridSplit()
                                    .getGrid(i).getArray().size(); j++) {
                                //If the focus was not lost due to a GameCover Obj in the Search Grid

                                if (e.getOppositeComponent() instanceof GamePlaceholder) {
                                    if (e.getOppositeComponent()
                                        != (Game) libraryUI.getGridSplit()
                                            .getGrid(i).getArray().get(j)) {
                                        System.out.println(e
                                                .getOppositeComponent());
                                        //Attempt to restore to GameCover Library Grid
                                        try {
                                            gridSearch.restoreGrid();
                                        } catch (MalformedURLException exx) {
                                            Logger
                                                    .getLogger(GameLibraryHandler.class
                                                    .getName())
                                                    .log(Level.SEVERE, null, exx);
                                        }
                                        //reset Search Box and append string
                                        gridSearch.resetAppendedName();

                                    }
                                }
                            }
                        }

                    }

                    SearchBar.setText("Start Typing To Search...");
                    libraryUI.getSearchBar().setForeground(Color.darkGray);
                    libraryUI.getSearchBar().setFont(libraryUI.getCoreUI()
                            .getDefaultFont().deriveFont(Font.BOLD, 40));
                    libraryUI.getSearchBarBG()
                            .setImage("SearchBar_inactive.png");
                    libraryUI.getSearchButtonBG().removeAll();
                    libraryUI.getSearchButtonBG().add(libraryUI
                            .getSearchButton(), BorderLayout.NORTH);
                }
            }
        }
    }

    public class searchBoxHandler extends KeyAdapter {
        //Handles Typing In Search Box, when it is in focus

        @Override
        public void keyReleased(KeyEvent e) {
            //this activates for any letter number or space key

            libraryUI.getSearchBar().setForeground(Color.darkGray);
            libraryUI.getSearchBar().setFont(libraryUI.getCoreUI()
                    .getDefaultFont().deriveFont(Font.BOLD, 44));
            libraryUI.getSearchBarBG().setImage("SearchBar.png");
            if (!libraryUI.isAddGameUI_Visible()) {
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
                    || e.getKeyCode() == KeyEvent.VK_QUOTE) {
                    gridSearch.typedChar(e.getKeyChar()); //Sends the key to the search engine to be appended and check for match

                } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    // If backspace is pressed tell search engine to search for name - 1 character
                    gridSearch.removeChar(e.getKeyChar());

                }
            }
        }
    }

    public class searchRefocusListener extends KeyAdapter {
        //Handles When User Starts Typing While Components other than the
        //Search Box are in focus.
        //Must get first key typed and put it in the searchbox
        //Then set focus to the searchbox

        private JTextField SearchBar;

        public searchRefocusListener() {
            this.SearchBar = libraryUI.getSearchBar();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            //pressing any Number or Letter can activate this
            if (!libraryUI.isAddGameUI_Visible()) {
                if (//e.getKeyCode() == KeyEvent.VK_A
                        e.getKeyCode() == KeyEvent.VK_B
                        || e.getKeyCode() == KeyEvent.VK_C
                        // || e.getKeyCode() == KeyEvent.VK_D
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
                        // || e.getKeyCode() == KeyEvent.VK_S
                        || e.getKeyCode() == KeyEvent.VK_T
                        || e.getKeyCode() == KeyEvent.VK_U
                        || e.getKeyCode() == KeyEvent.VK_V
                        // || e.getKeyCode() == KeyEvent.VK_W
                        || e.getKeyCode() == KeyEvent.VK_X
                        || e.getKeyCode() == KeyEvent.VK_Y
                        || e.getKeyCode() == KeyEvent.VK_Z
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
                        || e.getKeyCode() == KeyEvent.VK_QUOTE) {

                    SearchBar.setText(String.valueOf(e.getKeyChar())); //Set first character of Search Box to the key typed
                    gridSearch.resetAppendedName();//Clear appended text if there is anything still in there
                    gridSearch.clearGameGrid(); //clear and prep for search mode
                    gridSearch.typedChar(e.getKeyChar()); // Pass to search engine first character
                    SearchBar.requestFocus(); // Get focus of Search Box

                    libraryUI.getSearchBar().setForeground(Color.darkGray);
                    libraryUI.getSearchBar().setFont(libraryUI.getCoreUI()
                            .getDefaultFont().deriveFont(Font.BOLD, 44));
                    libraryUI.getSearchBarBG().setImage("SearchBar.png");
                    libraryUI.getSearchButtonBG().removeAll();
                    libraryUI.getSearchButtonBG().add(libraryUI
                            .getRemoveSearchButton(), BorderLayout.NORTH);
                    libraryUI.getRemoveSearchButton()
                            .addActionListener(
                            new RemoveSearchHandler());
                }
            }
        }
    }

    /////////////////////////////////////////////////////////////
    public class addGameSearchBoxHandler extends KeyAdapter {
        //Handles Typing In Search Box, when it is in focus

        @Override
        public void keyReleased(KeyEvent e) {
            //this activates for any letter number or space key

            libraryUI.getSearchBar().setForeground(Color.darkGray);
            libraryUI.getSearchBar().setFont(libraryUI.getCoreUI()
                    .getDefaultFont().deriveFont(Font.BOLD, 44));
            libraryUI.getSearchBarBG().setImage("SearchBar.png");

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
                || e.getKeyCode() == KeyEvent.VK_QUOTE) {
                gameSearch.typedChar(e.getKeyChar()); //Sends the key to the search engine to be appended and check for match

            } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                // If backspace is pressed tell search engine to search for name - 1 character
                gameSearch.removeChar(e.getKeyChar());

            }
        }
    }

    ////Add Game UI////////
    //For when you select the Textfield in the add Game UI
    public class addGameMouseHandler extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {

            if (libraryUI.getSearchText().getText().equals(
                    "Search For Game To Add...")) {
                libraryUI.getSearchText().requestFocus();
                libraryUI.getSearchText().setText("");
                gameSearch.resetCover();
                libraryUI.getSearchText().setForeground(Color.black);
                libraryUI.getSearchArrow().setImage(
                        "AddGame_SearchArrow_dark.png");
            }
        }
    }

    public class addGameFocusHandler implements FocusListener {

        @Override
        public void focusGained(FocusEvent e) {
            if (libraryUI.getSearchText().getText().equals(
                    "Search For Game To Add...")) {
                libraryUI.getSearchText().setText("");
                gameSearch.resetCover();
                libraryUI.getSearchText().setForeground(Color.black);
                libraryUI.getSearchArrow().setImage(
                        "AddGame_SearchArrow_dark.png");
            }

        }

        @Override
        public void focusLost(FocusEvent e) {

            if (e.getOppositeComponent() instanceof JList || e
                    .getOppositeComponent() instanceof JFileChooser == false) {
                try {
                    gridSearch.restoreGrid();
                } catch (MalformedURLException ex) {
                    Logger.getLogger(GameLibraryHandler.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
                if (libraryUI.getSearchText().getText().length() <= 1) {
                    libraryUI.getSearchText().setText(
                            "Search For Game To Add...");
                    libraryUI.getSearchText().setForeground(Color.DARK_GRAY);
                    libraryUI.getSearchArrow().setImage(
                            "AddGame_SearchArrow_light.png");
                }

            }
        }
    }

    public class HideGameAddUIHandler implements ActionListener {

        private GameLibraryUI libraryUI;

        public HideGameAddUIHandler(GameLibraryUI gameLibraryUI) {
            this.libraryUI = gameLibraryUI;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            libraryUI.hideAddGameUI();
        }
    }

    public class ExecutableChooserHandler implements ActionListener {

        private JFileChooser gameLocator;

        public ExecutableChooserHandler(JFileChooser locator) {
            gameLocator = locator;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (gameLocator.getSelectedFile() != null) {
                libraryUI
                        .setCurrentPath(gameLocator.getSelectedFile().getPath());
                libraryUI.getStepTwo().setImgURl("AddGame_step2_green.png");
                libraryLogic.checkNotifiers();
                System.out.println(libraryUI.getCurrentPath());
            } else {
                libraryUI.getStepTwo().setImgURl("AddGame_step2_red.png");
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
                if (extension.equals("exe")
                    || extension.equals("app")
                    || extension.equals("lnk")) {

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
            return "Executable Game File";
        }
    }

    public class AddToLibraryHandler implements ActionListener {

        private GridManager gridManager;

        private JPanel GameBack;

        private MoveToLastGrid GridMove;

        private AuroraStorage storage;

        private String currentPath;

        @Override
        public void actionPerformed(ActionEvent e) {

            currentPath = libraryUI.getCurrentPath();
            gridManager = libraryUI.getGridSplit();
            GameBack = libraryUI.getGameBack();
            GridMove = libraryUI.getGridMove();
            storage = libraryUI.getStorage();

            gameSearch.getFoundGameCover().setGamePath(currentPath);
            gameSearch.getFoundGameCover()
                    .setCoverSize(libraryUI.getGameCoverWidth(), libraryUI
                    .getGameCoverHeight());
            gameSearch.getFoundGameCover().reAddInteractive();
            if (!gridManager.isDupicate(gameSearch.getFoundGameCover())) {
                storage.getStoredLibrary()
                        .SaveGame(gameSearch.getFoundGameCover());


            }
            gridManager.addGame(gameSearch.getFoundGameCover());
            gridManager.finalizeGrid(new ShowAddGameUiHandler(),
                    libraryUI
                    .getGameCoverWidth(), libraryUI
                    .getGameCoverHeight());
            libraryUI.hideAddGameUI();

            //* reset cover to blank cover *//
            gameSearch.resetCover();

            libraryUI.setCurrentIndex(
                    gridManager.getArray().indexOf(GameBack.getComponent(1)));

            //* Transition towards to left most grid to see the game added *//
            GridMove.runMover();
        }
    }

    public class SelectListHandler implements ListSelectionListener {

        private JList gamesList;

        private DefaultListModel listModel;

        private JTextField gameSearchBar;

        public SelectListHandler() {
            gamesList = libraryUI.getGamesList();
            listModel = libraryUI.getListModel();
            gameSearchBar = libraryUI.getGameSearchBar();
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {

            if (gamesList.getSelectedIndex() != -1) {
                System.out.println();
                String gameSelected = (String) listModel.get(gamesList
                        .getSelectedIndex());
                gameSearchBar.setText(gameSelected);

                gameSearch.searchSpecificGame(gameSelected);
                gameSearch.setAppendedName(gameSelected);

            }
        }
    }

    public class ShowAddGameUiHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            libraryUI.showAddGameUI();

        }
    }

    //Transisions towards the Last Grid in the library
    //To show game added (apple iOS style :P )
    public class MoveToLastGrid implements Runnable {

        private Thread mover;

        public void runMover() {
            mover = null;

            if (mover == null) {
                mover = new Thread(this);
            }
            mover.setName("Mover Thread");
            //Start Loader
            System.out.println("Starting Mover Thread");
            mover.start();
        }

        @Override
        public void run() {
            while (Thread.currentThread() == mover) {
                if (libraryUI.getCurrentIndex() < libraryUI.getGridSplit()
                        .getFullGrids()) {

                    libraryUI.getMoveLibraryRightHandler().mouseClicked(null);

                } else if (libraryUI.getCurrentIndex() >= libraryUI
                        .getGridSplit()
                        .getFullGrids()) {
                    break;
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GameLibraryUI.class.getName()).log(
                            Level.SEVERE, null, ex);
                }
            }

        }
    }

    //Prevents from clicking Through the Aurora Add Game UI and select Games in the
    //Background
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

        private AHoverButton imgGameLeft;

        private AHoverButton imgGameRight;

        private AImage imgFavorite;

        private GridAnimation GridAnimate;

        public HoverButtonLeft() {
            gridManager = libraryUI.getGridSplit();
            GameBack = libraryUI.getGameBack();
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            imgGameLeft = libraryUI.getImgGameLeft();
            imgGameRight = libraryUI.getImgGameRight();
            imgFavorite = libraryUI.getImgFavorite();
            GridAnimate = libraryUI.getGridAnimate();

            gridManager = libraryUI.getGridSplit();



            int currentIndex;

            if (!GridAnimate.getAnimator1().isAnimating() && !GridAnimate
                    .getAnimator2().isAnimating()) {

                ///Get The Index of The Current Panel Being Displayed
                ///Refer too GridManager array of All panels to find it
                currentIndex = gridManager.getArray().indexOf(GameBack
                        .getComponent(1));

                //Stop from going to far left
                if (currentIndex - 1 == -1) {
                    currentIndex = 1;
                    imgGameLeft.mouseExit();
                }


                if (currentIndex < gridManager.getArray().size()) {


                    //Clear Panel
                    //GameBack.removeAll();
                    if (currentIndex - 1 <= 0) {
                        //Far Left Image
                        GameBack.remove(0);
                        GameBack.add(imgFavorite, BorderLayout.WEST, 0);

                    } else {
                        //Left Button
                        GameBack.remove(0);
                        GameBack.add(imgGameLeft, BorderLayout.WEST, 0);
                    }
                    //Add GameCover Covers

                    GridAnimate.moveLeft(currentIndex);


                    try {
                        libraryLogic.loadGames(currentIndex - 1);
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(GameLibraryUI.class.getName())
                                .log(Level.SEVERE, null, ex);
                    }


                    GameBack.add(BorderLayout.EAST, imgGameRight);
                }

                libraryUI.getCoreUI().getCenterPanel().removeAll();
                libraryUI.getCoreUI().getCenterPanel().add(BorderLayout.CENTER,
                        GameBack);

                GameBack.repaint();
                GameBack.revalidate();


            }
            imgGameLeft.mouseExit();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            imgGameLeft = libraryUI.getImgGameLeft();
            imgGameRight = libraryUI.getImgGameRight();
            imgFavorite = libraryUI.getImgFavorite();
            GridAnimate = libraryUI.getGridAnimate();
            GridAnimate = libraryUI.getGridAnimate();
            imgGameLeft = libraryUI.getImgGameLeft();

            if (!GridAnimate.getAnimator1().isAnimating() && !GridAnimate
                    .getAnimator2().isAnimating()) {
                imgGameLeft.mouseHover(e);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            imgGameLeft = libraryUI.getImgGameLeft();
            imgGameRight = libraryUI.getImgGameRight();
            imgFavorite = libraryUI.getImgFavorite();
            GridAnimate = libraryUI.getGridAnimate();
            imgGameLeft.mouseExit();

        }
    }

    public class HoverButtonRight extends MouseAdapter {

        private GridManager gridManager;

        private JPanel GameBack;

        private AHoverButton imgGameLeft;

        private AHoverButton imgGameRight;

        private AImage imgFavorite;

        private AImage imgBlank;

        private GridAnimation GridAnimate;

        private final AuroraCoreUI coreUI;

        public HoverButtonRight() {
            this.coreUI = libraryUI.getCoreUI();

            GameBack = libraryUI.getGameBack();
            imgGameLeft = libraryUI.getImgGameLeft();
            imgGameRight = libraryUI.getImgGameRight();
            imgFavorite = libraryUI.getImgFavorite();
            imgBlank = libraryUI.getImgBlank();
            GridAnimate = libraryUI.getGridAnimate();
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            gridManager = libraryUI.getGridSplit();

            if (!GridAnimate.getAnimator1().isAnimating() && !GridAnimate
                    .getAnimator2().isAnimating()) {

                libraryUI.setCurrentIndex(gridManager.getArray()
                        .indexOf(GameBack
                        .getComponent(1)));

                if (libraryUI.getCurrentIndex() < gridManager.getArray().size()
                                                  - 1) {

                    GameBack.remove(0);
                    GameBack.add(libraryUI.getImgGameLeft(), BorderLayout.WEST,
                            0);

                    GameBack.add(imgGameRight, BorderLayout.EAST, 2);

                    GridAnimate.moveRight(libraryUI.getCurrentIndex());


                    try {
                        libraryLogic.loadGames(libraryUI.getCurrentIndex() + 1);
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(GameLibraryUI.class.getName())
                                .log(Level.SEVERE, null, ex);
                    }



                    //of on last Grid then dont show right arrow button
                    if (!(libraryUI.getCurrentIndex() + 1 < gridManager
                            .getArray()
                            .size() - 1)) {

                        GameBack.remove(libraryUI.getImgGameRight());
                        GameBack.add(imgBlank, BorderLayout.EAST, 2);
                        imgGameRight.mouseExit();
                    }
                }

                coreUI.getCenterPanel().removeAll();
                coreUI.getCenterPanel().add(BorderLayout.CENTER, libraryUI
                        .getGameBack());

                GameBack.repaint();
                GameBack.revalidate();

            }
            imgGameRight.mouseExit();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            System.out.println("HOVER IMAGE ACTIVATED");
            GridAnimate = libraryUI.getGridAnimate();
            imgGameRight = libraryUI.getImgGameRight();

            if (!GridAnimate.getAnimator1().isAnimating() && !GridAnimate
                    .getAnimator2().isAnimating()) {
                imgGameRight.mouseHover(e);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            imgGameRight.mouseExit();
        }
    }

    //Handler for the Navigation using Keyboard
    public class GameLibraryKeyListener extends KeyAdapter {

        private GridManager gridManager;

        private JPanel GameBack;

        private final AuroraCoreUI coreUI;

        public GameLibraryKeyListener() {
            this.coreUI = libraryUI.getCoreUI();
            GameBack = libraryUI.getGameBack();
        }

        @Override
        public void keyPressed(KeyEvent e) {

            int currentIndex;
            gridManager = libraryUI.getGridSplit();

            /* get the index of the grid that is currently displayed */
            int visibleGridIndex = gridManager.getVisibleGridIndex();
            System.out.println("Initial visible grid = " + visibleGridIndex);
            currentIndex = gridManager.getArray()
                    .indexOf(GameBack.getComponent(1));
            libraryUI.setCurrentIndex(currentIndex);
            System.out.println("Current Grid = " + currentIndex);

            /* get the grid that is currently displayed */
            AGridPanel grid = gridManager.getGrid(currentIndex);

            /* get an array of all the components in the grid */
            ArrayList comp = grid.getArray();

            boolean cursorMoved = false;

            Game game = null;

            boolean selectedGameFound = false;

            if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode()
                                                   == KeyEvent.VK_UP) {

                int i = 0;

                while (comp.size() > i && !selectedGameFound
                       && !(comp.get(i) instanceof GamePlaceholder)) {
                    //Check for GamePlaceholder CANT MOVE THERE!
                    game = (Game) comp.get(i);

                    if (game.isSelected()) {
                        selectedGameFound = true;
                        System.out.println(game.getName()
                                           + " is selected in the library");
                        int[] columnAndRow = grid.getColumnAndRow(i + 1);
                        int col = columnAndRow[0];
                        int row = columnAndRow[1];

                        System.out.println("Col = " + columnAndRow[0]);
                        System.out.println("Row = " + columnAndRow[1]);

                        if (row > 1) {
                            System.out.println("Cursor is moving up!");
                            //Check for GamePlaceholder CANT MOVE THERE!
                            if (!(comp.get(i - 4) instanceof GamePlaceholder)) {
                                game.hideInteractiveComponents();
                                Game newGame = (Game) comp.get(i - 4);
                                gridManager.unselectPrevious();
                                newGame.displayInteractiveComponents();
                            }

                        } else if (row == 1) {
                            //Check for GamePlaceholder CANT MOVE THERE!
                            if (!(comp.get(i + (4 * 1)) instanceof GamePlaceholder)) {
                                game.hideInteractiveComponents();
                                Game newGame = (Game) comp.get(i + (4 * 1));
                                gridManager.unselectPrevious();
                                newGame.displayInteractiveComponents();
                            }
                        } else {
                            System.out.println(
                                    "Cursor cannot move any further up!");
                        }
                    } else {
                        i++;
                    }

                }

                if (!selectedGameFound && (comp.get(0) instanceof Game)) {
                    game = (Game) comp.get(0);
                    game.displayInteractiveComponents();
                }

                //>>> MOVE DOWN
            } else if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode()
                                                          == KeyEvent.VK_DOWN) {

                int i = 0;

                while (i < comp.size() && !selectedGameFound
                       && !(comp.get(i) instanceof GamePlaceholder)) {
                    //Check for GamePlaceholder CANT MOVE THERE!
                    game = (Game) comp.get(i);

                    if (game.isSelected()) {
                        selectedGameFound = true;
                        System.out.println(game.getName()
                                           + " is selected in the library");
                        int[] columnAndRow = grid.getColumnAndRow(i + 1);
                        int col = columnAndRow[0];
                        int row = columnAndRow[1];

                        System.out.println("Col = " + columnAndRow[0]);
                        System.out.println("Row = " + columnAndRow[1]);

                        if (row < grid.getRow()) {
                            System.out.println("Cursor is moving down!");

                            //Check for GamePlaceholder CANT MOVE THERE!
                            if (!(comp.get(i + 4) instanceof GamePlaceholder)) {
                                game.hideInteractiveComponents();
                                Game newGame = (Game) comp.get(i + 4);
                                gridManager.unselectPrevious();
                                newGame.displayInteractiveComponents();
                            }

                        } else if (row == grid.getRow()) {

                            //Check for GamePlaceholder CANT MOVE THERE!
                            if (!(comp.get(i - (4 * 1)) instanceof GamePlaceholder)) {
                                game.hideInteractiveComponents();
                                Game newGame = (Game) comp.get(i - (4 * 1));
                                gridManager.unselectPrevious();
                                newGame.displayInteractiveComponents();
                            }
                        } else {
                            System.out.println(
                                    "Cursor cannot move any further down!");
                        }
                    } else {
                        i++;
                    }

                }

                if (!selectedGameFound && (comp.get(0) instanceof Game)) {
                    game = (Game) comp.get(0);
                    game.displayInteractiveComponents();
                }


                //>>> MOVE LEFT
            } else if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode()
                                                          == KeyEvent.VK_LEFT) {

                System.out.println("A key pressed");

                int i = 0;

                while (i < comp.size() && !selectedGameFound
                       && !(comp.get(i) instanceof GamePlaceholder)) {
                    game = (Game) comp.get(i);
                    if (game.isSelected()) {
                        selectedGameFound = true;
                        System.out.println("index = " + i);
                        System.out.println(game.getName()
                                           + " is selected in the library");
                    } else {
                        i++;
                    }

                }

                if (!cursorMoved && selectedGameFound) {
                    int[] columnAndRow = grid.getColumnAndRow(i + 1);
                    int col = columnAndRow[0];
                    int row = columnAndRow[1];

                    System.out.println("Col = " + col);
                    System.out.println("Row = " + row);

                    // check to see if the selected game is not the first game in the grid
                    if (col > 1 || (col == 1 && row > 1)) {
                        System.out.println("Cursor is moving left!");
                        visibleGridIndex = gridManager.getVisibleGridIndex();
                        System.out.println("visible grid after moving right = "
                                           + visibleGridIndex);
                        game.hideInteractiveComponents();
                        Game newGame = (Game) comp.get(i - 1);
                        gridManager.unselectPrevious();
                        newGame.displayInteractiveComponents();
                        cursorMoved = true;
                    } else if (col == 1 && row == 1) {

                        if (gridManager.getArray().indexOf(GameBack
                                .getComponent(1)) > 0) {
                            libraryUI.moveGridLeft();
                            /* get the index of the grid that is currently displayed */
                            visibleGridIndex = gridManager.getVisibleGridIndex();
                            System.out
                                             .println("visible grid after moving right = "
                                                      + visibleGridIndex);
                            currentIndex = gridManager.getArray()
                                    .indexOf(GameBack
                                    .getComponent(1));
                            /* get the grid that is currently displayed */
                            grid = gridManager.getGrid(currentIndex);

                            /* get an array of all the components in the grid */
                            comp = grid.getArray();

                            //Check if GamePlaceholder is to the right.
                            if (!(comp.get(comp.size() - 1) instanceof GamePlaceholder)) {
                                game.hideInteractiveComponents();
                                Game newGame = (Game) comp.get(comp.size() - 1);
                                gridManager.unselectPrevious();
                                newGame.displayInteractiveComponents();
                            }
                        } else {
                            System.out.println(
                                    "Cursor cannot move any further left!");
                        }


                    }
                } else if (!selectedGameFound && (comp.get(0) instanceof Game)) {
                    game = (Game) comp.get(0);
                    game.displayInteractiveComponents();
                }

                // >>> MOVE RIGHT
            } else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode()
                                                          == KeyEvent.VK_RIGHT) {

                System.out.println("D key pressed");

                int i = 0;
                //      boolean selectedGameFound = false;

                while (i < comp.size() && !selectedGameFound
                       && !(comp.get(i) instanceof GamePlaceholder)) {
                    game = (Game) comp.get(i);
                    if (game.isSelected()) {
                        selectedGameFound = true;
                        System.out.println("index = " + i);
                        System.out.println(game.getName()
                                           + " is selected in the library");
                    } else {
                        i++;
                    }

                }

                if (!cursorMoved && selectedGameFound) {
                    int[] columnAndRow = grid.getColumnAndRow(i + 1);
                    int col = columnAndRow[0];
                    int row = columnAndRow[1];
                    System.out.println("Col = " + col);
                    System.out.println("Row = " + row);

                    // check to see if the selected is not the last game in the grid
                    if ((col < grid.getCol()
                         || (col == grid.getCol() && row < grid.getRow()))
                        && comp.size() > i + 1) {
                        System.out.println("Cursor is moving right!");
                        System.out.println(game.getName()
                                           + " is Last Game in This Grid!");

                        Game newGame;

                        // get the next object
                        Object obj = comp.get(i + 1);
                        if (obj instanceof Game) {
                            System.out.println("Object is a game");
                            //game.hideInteractiveComponents();
                            newGame = (Game) obj;
                            gridManager.unselectPrevious();
                            newGame.displayInteractiveComponents();
                            cursorMoved = true;
                        } else {
                            System.out.println("Object is an add game icon");
                        }



                        // else check to see if the selected game is the last game in the grid
                    } else if (col == grid.getCol() && row == grid.getRow()) {
                        System.out
                                .println(
                                "Cursor cannot move any further right! Grid needs to move right");

                        // check to see if the the current grid is the last grid
                        if (gridManager.getVisibleGridIndex() < (gridManager
                                .getNumberOfGrids())
                            && !(comp.get(0) instanceof GamePlaceholder)) {
                            System.out.println("This is not the last grid");

                            libraryUI.moveGridRight();

                            /* get the index of the grid that is currently displayed */
                            visibleGridIndex = gridManager.getVisibleGridIndex();
                            System.out
                                             .println("visible grid after moving right = "
                                                      + visibleGridIndex);

                            currentIndex = gridManager.getArray()
                                    .indexOf(GameBack
                                    .getComponent(1));

                            /* get the grid that is currently displayed */
                            grid = gridManager.getGrid(currentIndex);

                            /* get an array of all the components in the grid */
                            comp = grid.getArray();

                            Game newGame = (Game) comp.get(0);

                            newGame.requestFocus();
                            newGame.removePreviousSelected();
                            newGame.revalidate();
                            newGame.displayInteractiveComponents();

                        } else {
                            System.out
                                    .println(
                                    "Cannot move to the grid to the right. No more grids!");
                        }

                    }
                } else if (!selectedGameFound && (comp.get(0) instanceof Game)) {
                    game = (Game) comp.get(0);
                    game.displayInteractiveComponents();
                }
            } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                System.out.println("D key pressed");

                int i = 0;

                while (i < comp.size() && !selectedGameFound
                       && !(comp.get(i) instanceof GamePlaceholder)) {
                    game = (Game) comp.get(i);
                    if (game.isSelected()) {
                        selectedGameFound = true;
                        System.out.println("index = " + i);
                        System.out.println(game.getName()
                                           + " is selected in the library");
                    } else {
                        i++;
                    }

                }

                if (!cursorMoved && selectedGameFound) {
                    game.getPlayHandler().actionPerformed(null);
                }

            } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                coreUI.showExitDialog();
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }

    public class GridMouseWheelListener implements MouseWheelListener {

        private GridManager gridManager;

        public GridMouseWheelListener() {
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {

            int currentIndex;
            gridManager = libraryUI.getGridSplit();

            int numberClicks = e.getWheelRotation();
            System.out.println("Mouse wheel moved " + numberClicks);

            ///Get The Index of The Current Panel Being Displayed///
            ///Refer too GridManager array of All panels to find it///
            //GameBack is the Panel Containing all the game grids///

            currentIndex = gridManager.getArray().indexOf(libraryUI
                    .getGameBack()
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
}
