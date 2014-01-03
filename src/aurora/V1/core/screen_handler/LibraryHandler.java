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
import aurora.engine.V1.UI.APopupMenu;
import aurora.engine.V1.UI.AProgressWheel;
import aurora.engine.V1.UI.ARadioButton;
import aurora.engine.V1.UI.ASlickLabel;
import aurora.engine.V1.UI.ATextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import org.apache.log4j.Logger;
import sun.swing.DefaultLookup;

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

    public class RemoveSearchHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                libraryLogic.getGridSearch().restoreGrid();
            } catch (MalformedURLException ex) {
                logger.error(ex);
            }
            libraryLogic.getGridSearch().resetAppendedName();
            libraryUI.getSearchBar().setText("");
            libraryUI.getSearchBar().setText("Search Here...");
            libraryUI.getSearchBar().setForeground(Color.darkGray);
            libraryUI.getSearchBarBG()
                    .setImage("library_searchBar_inactive.png");
            libraryUI.getSearchButtonBG().removeAll();
            libraryUI.getSearchButtonBG().add(libraryUI.getSearchButton(),
                    BorderLayout.NORTH);
            libraryUI.getCoreUI().getFrame().requestFocus();
            libraryUI.getGamesContainer().revalidate();
        }
    }

    //////Search Library Bar//////////
    ///What to do if Click on Search Box
    public class SearchSelectHandler extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (libraryUI.getSearchBar().getText().equals(
                    "Just Start Typing...")) {
                libraryUI.getSearchBar().setText("");
                libraryUI.getSearchBar().setForeground(Color.darkGray);
                libraryUI.getSearchBarBG().setImage(
                        "library_searchBar_active.png");
                libraryUI.getSearchButtonBG().removeAll();
                libraryUI.getSearchButtonBG().add(libraryUI
                        .getRemoveSearchButton(), BorderLayout.NORTH);
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
            libraryUI.getSearchBar().setForeground(Color.darkGray);
            libraryUI.getSearchBarBG().setImage("library_searchBar_active.png");
            libraryUI.getSearchButtonBG().removeAll();
            libraryUI.getSearchButtonBG().add(libraryUI.getRemoveSearchButton(),
                    BorderLayout.NORTH);
            libraryUI.getRemoveSearchButton()
                    .addActionListener(new RemoveSearchHandler());
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
                    libraryUI.getSearchBar().setForeground(Color.darkGray);
                    libraryUI.getSearchBarBG().setImage(
                            "library_searchBar_active.png");
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
                                        != (Game) libraryLogic.getGridSearch()
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
                        for (int i = 0; i < libraryUI.getGridSplit().getArray()
                                .size(); i++) {
                            for (int j = 0; j < libraryUI.getGridSplit()
                                    .getGrid(i).getArray().size(); j++) {
                                // If the focus was not lost due to a GameCover Obj in the Search Grid

                                if (e.getOppositeComponent() instanceof GamePlaceholder) {
                                    if (e.getOppositeComponent()
                                        != (Game) libraryUI.getGridSplit()
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
                }
            }
        }
    }

    public class SearchBoxHandler extends KeyAdapter {
        // Handles Typing In Search Box, when it is in focus

        @Override
        public void keyReleased(KeyEvent e) {

            // This activates for any letter number or space key
            libraryUI.getSearchBar().setForeground(Color.darkGray);
            libraryUI.getSearchBarBG().setImage("library_searchBar_active.png");
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
                    || e.getKeyCode() == KeyEvent.VK_QUOTE
                    || e.getKeyCode() == KeyEvent.VK_PERIOD) {
                    //Sends the key to the search engine to be appended and check for match
                    libraryLogic.getGridSearch().typedChar(e.getKeyChar());

                } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    // If backspace is pressed tell search engine to search for name - 1 character
                    libraryLogic.getGridSearch().removeChar(e.getKeyChar());

                }
            }
        }
    }

    public class listRender extends DefaultListCellRenderer {

        private static final long serialVersionUID = 1L;

        public listRender() {
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
            if (dropLocation != null
                && !dropLocation.isInsert()
                && dropLocation.getIndex() == index) {

                bg = DefaultLookup.getColor(this, ui, "List.dropCellBackground");
                fg = DefaultLookup.getColor(this, ui, "List.dropCellForeground");

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

    public class ComponentListRender extends DefaultListCellRenderer {

        private static final long serialVersionUID = 1L;

        public ComponentListRender() {
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
            if (dropLocation != null
                && !dropLocation.isInsert()
                && dropLocation.getIndex() == index) {

                bg = DefaultLookup.getColor(this, ui, "List.dropCellBackground");
                fg = DefaultLookup.getColor(this, ui, "List.dropCellForeground");

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

    public class SearchRefocusListener extends KeyAdapter {
        //Handles When User Starts Typing While Components other than the
        //Search Box are in focus.
        //Must get first key typed and put it in the searchbox
        //Then set focus to the searchbox

        private JTextField SearchBar;

        public SearchRefocusListener() {
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
                        || e.getKeyCode() == KeyEvent.VK_QUOTE
                        || e.getKeyCode() == KeyEvent.VK_PERIOD) {

                    //Set first character of Search Box to the key typed
                    SearchBar.setText(String.valueOf(e.getKeyChar()));
                    //Clear appended text if there is anything still in there
                    libraryLogic.getGridSearch().resetAppendedName();
                    //clear and prep for search mode
                    libraryLogic.getGridSearch().clearGameGrid();
                    // Pass to search engine first character
                    libraryLogic.getGridSearch().typedChar(e.getKeyChar());
                    // Get focus of Search Box
                    SearchBar.requestFocus();

                    libraryUI.getSearchBar().setForeground(Color.darkGray);
                    libraryUI.getSearchBarBG().setImage(
                            "library_searchBar_active.png");
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

    public class AddGameSearchBoxHandler implements DocumentListener {

        private final GameSearch gameSearch;
        //Handles Typing In Search Box, when it is in focus

        private final JTextField textField;

        public AddGameSearchBoxHandler(GameSearch searchEngine,
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

    public class AddGameSearchClear implements ActionListener {

        private final JTextField txtField;

        private final GameSearch gameSearch;

        public AddGameSearchClear(JTextField searchField,
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

////Add Game UI////////
//For when you select the Textfield in the add Game UI
    public class AddGameMouseHandler extends MouseAdapter {

        private final JTextField txtField;

        private final GameSearch gameSearch;

        private final AImagePane searchBG;

        public AddGameMouseHandler(JTextField searchField,
                                   AImagePane searchBackground,
                                   GameSearch searchEngine) {
            txtField = searchField;
            gameSearch = searchEngine;
            searchBG = searchBackground;
        }

        @Override
        public void mousePressed(MouseEvent e) {

            if (txtField.getText().equals(
                    "Search For Game...")) {
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

    public class AddGameFocusHandler implements FocusListener {

        private final JTextField txtField;

        private final AImagePane txtBackground;

        private final GameSearch gameSearch;

        public AddGameFocusHandler(JTextField textField,
                                   AImagePane txtBackground,
                                   GameSearch searchEngine) {

            this.txtField = textField;
            this.txtBackground = txtBackground;
            this.gameSearch = searchEngine;

        }

        @Override
        public void focusGained(FocusEvent e) {

            if (txtField.getText().equals(
                    "Search For Game...") || txtField.getText().equals("")) {
                txtField.setText("");
                txtField.setCaretColor(Color.cyan);
                gameSearch.resetCover();
                txtField.setForeground(new Color(23, 139, 255));
                if (!(txtBackground instanceof ATextField)) {
                    txtBackground.setImage(
                            "addUI_text_active.png");
                }

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
                    txtField.setText(
                            "Search For Game...");
                    txtField.setForeground(Color.darkGray);
                    if (!(txtBackground instanceof ATextField)) {
                        txtBackground.setImage(
                                "addUI_text_inactive.png");
                    }
                }

            }
        }
    }

    public class HideGameAddUIHandler implements ActionListener {

        private LibraryUI libraryUI;

        public HideGameAddUIHandler(LibraryUI gameLibraryUI) {
            this.libraryUI = gameLibraryUI;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            libraryUI.hideAddGameUI();
        }
    }

    public class HideEditAddUIHandler implements ActionListener {

        private LibraryUI libraryUI;

        public HideEditAddUIHandler(LibraryUI gameLibraryUI) {
            this.libraryUI = gameLibraryUI;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            libraryUI.hideEditGameUI();
        }
    }

    public class ExecutableChooserHandler implements ActionListener {

        private JFileChooser gameLocator;

        public ExecutableChooserHandler(JFileChooser locator) {
            gameLocator = locator;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (gameLocator.getSelectedFile() != null && AFileManager.checkFile(
                    gameLocator.getSelectedFile().getAbsolutePath())) {
                libraryUI
                        .setCurrentPath(gameLocator.getSelectedFile().getPath());
                libraryUI.getStatusBadge2().setImgURl("addUI_badge_valid.png");

            } else {
                libraryUI.getStatusBadge2().setImgURl("addUI_badge_invalid.png");
            }

            libraryLogic.checkManualAddGameStatus();
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

                libraryUI.getImgGameLocationStatus().setImgURl(
                        "addUI_badge_valid.png");
                libraryUI.getTxtNewLocation_editUI().setText(gameLocator
                        .getSelectedFile().getAbsolutePath());
                libraryUI.setIsGameLocation(true);

            } else {
                libraryUI.getImgGameLocationStatus().setImgURl(
                        "addUI_badge_invalid.png");
                libraryUI.setIsGameLocation(false);
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

    public class EditSettingDoneHandler implements ActionListener {

        private MoveToGrid GridMove;

        @Override
        public void actionPerformed(ActionEvent e) {

            AThreadWorker doneTask = new AThreadWorker(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    if (libraryUI.isGameLocation()) {
                        // Check if valid
                        if (libraryUI.getImgGameLocationStatus().getImgURl()
                                .equals("addUI_badge_valid.png")) {
                            // Remove Game
                            libraryUI.getStorage().getStoredLibrary()
                                    .removeGame(libraryUI
                                            .getCurrentGame_editUI());
                            //Set new path
                            libraryUI.getCurrentGame_editUI().setGamePath(
                                    libraryUI
                                    .getTxtNewLocation_editUI().getText());
                            // Re Save
                            libraryUI.getStorage().getStoredLibrary()
                                    .SaveGame(libraryUI.getCurrentGame_editUI());

                            LibraryUI.lblLibraryStatus.setForeground(
                                    Color.orange);
                            LibraryUI.lblLibraryStatus.setText(
                                    "Changed Game Path");
                        }

                    }

                    if (libraryUI.isGameCover()) {

                        // Check if valid
                        if (libraryUI.getImgGameCoverStatus().getImgURl()
                                .equals(
                                        "addUI_badge_valid.png")) {

                            //Set new name
                            String editGameName;
                            if (libraryLogic.getGameSearch_editUI()
                                    .getCurrentlySearchedGame().getName()
                                == null
                                || !libraryLogic.getGameSearch_editUI()
                                    .isIsSearchEnabled()) {
                                editGameName = libraryLogic
                                        .getGameSearch_editUI()
                                        .getAppendedName();
                            } else {
                                editGameName = libraryLogic
                                        .getGameSearch_editUI()
                                        .getCurrentlySearchedGame().getName();
                            }

                            if (!libraryUI
                                    .getGridSplit().isDupicate(editGameName)) {
                                // Remove Game
                                libraryUI.getStorage().getStoredLibrary()
                                        .removeGame(libraryUI
                                                .getCurrentGame_editUI());
                                try {
                                    //Set new path
                                    libraryUI.getCurrentGame_editUI()
                                            .setCoverUrl(
                                                    libraryLogic
                                                    .getGameSearch_editUI()
                                                    .getCurrentlySearchedGame()
                                                    .getBoxArtUrl());

                                    libraryUI.getCurrentGame_editUI()
                                            .setGameName(
                                                    editGameName);

                                } catch (MalformedURLException ex) {
                                    java.util.logging.Logger.getLogger(
                                            LibraryHandler.class.getName()).
                                            log(Level.SEVERE, null, ex);
                                }

                                //refresh
                                libraryUI.getCurrentGame_editUI().refresh(true);

                                // Re Save
                                libraryUI.getStorage().getStoredLibrary()
                                        .SaveGame(libraryUI
                                                .getCurrentGame_editUI());

                                LibraryUI.lblLibraryStatus.setForeground(
                                        Color.orange);
                                LibraryUI.lblLibraryStatus.setText(
                                        "Changed Game Cover");

                                //* Transition towards to left most grid to see the game added *//
                                GridMove = new MoveToGrid(libraryUI
                                        .getCurrentGame_editUI());

                                libraryUI.getGridSplit().unselectPrevious();
                            } else {
                                ADialog info = new ADialog(
                                        ADialog.aDIALOG_WARNING,
                                        "Cannot Add Duplicate Game",
                                        libraryUI
                                        .getCoreUI()
                                        .getRegularFont()
                                        .deriveFont(Font.BOLD, 28));
                                info.setVisible(true);
                                info.showDialog();
                            }

                        }
                    }

                    libraryUI.hideEditGameUI();

                }
            }, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    libraryUI.getCurrentGame_editUI().getBtnFlip()
                            .getActionListeners()[0].actionPerformed(
                                    null);

                    if (GridMove != null && libraryUI.isGameCover()) {
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
                    libraryUI.getGridSplit().unselectPrevious();
                    libraryUI.getCurrentGame_editUI().showOverlayUI();

                    GridMove = null;
                }
            });

            doneTask.startOnce();

        }
    }

    public class ManualAddHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (libraryUI.getPnlAddGamePane().getComponent(1) != libraryUI
                    .getPnlManualAdd()) {

                if (libraryUI.getAddGameToLibButton().isVisible()) {
                    libraryLogic.animateAddButtonUp();
                }

                if (libraryLogic.isIsAutoLoadedOnce()) {
                    libraryLogic.autoClearAll();
                }

                libraryLogic.getGameSearch_addUI().resetCover();
                libraryUI.getSearchText_addUI().setText("");

                libraryUI.getPnlAddGamePane().remove(1);
                libraryUI.getPnlAddGamePane().add(libraryUI.getPnlManualAdd());
                try {
                    libraryUI.getPnlAddGamePane().setImageURL("addUI_bg.png");
                } catch (MalformedURLException ex) {
                    java.util.logging.Logger.getLogger(LibraryHandler.class
                            .getName()).
                            log(Level.SEVERE, null, ex);
                }
                libraryUI.getPnlAddGamePane().revalidate();
                libraryUI.getPnlAddGamePane().repaint();

                AThreadWorker wait = new AThreadWorker(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException ex) {
                            java.util.logging.Logger.getLogger(
                                    LibraryHandler.class.getName()).
                                    log(Level.SEVERE, null, ex);
                        }
                        libraryUI.getSearchText_addUI().requestFocusInWindow();

                    }
                });
                wait.startOnce();
            }

        }
    }

    /**
     * Default style render for Lists in selecting game covers to aurora
     */
    public class ListPanelRender extends DefaultListCellRenderer {

        private static final long serialVersionUID = 1L;

        public ListPanelRender() {
            setOpaque(true);
        }

        @Override
        public Component getListCellRendererComponent(JList list, Object value,
                                                      int index,
                                                      boolean isSelected,
                                                      boolean cellHasFocus) {

            JLabel label = (JLabel) (!(((JPanel) value).getComponent(0) instanceof AImagePane) ?
                    ((JPanel) value).getComponent(0) :
                    ((JPanel) value)
                    .getComponent(1));

            Color bg = null;
            Color fg = null;

            JList.DropLocation dropLocation = list.getDropLocation();
            if (dropLocation != null
                && !dropLocation.isInsert()
                && dropLocation.getIndex() == index) {

                bg = DefaultLookup.getColor(this, ui, "List.dropCellBackground");
                fg = DefaultLookup.getColor(this, ui, "List.dropCellForeground");

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

                JLabel label = (JLabel) (!(((JPanel) value).getComponent(0) instanceof AImagePane) ?
                        ((JPanel) value).getComponent(0) :
                        ((JPanel) value)
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

    public class AutoAddHandler implements ActionListener {

        private final DefaultListModel model;

        public AutoAddHandler(DefaultListModel listModel) {
            model = listModel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            // Select AutoAdd tab
            if (libraryUI.getPnlAddGamePane().getComponent(1) != libraryUI
                    .getPnlAutoAdd()) {

                if (libraryUI.getAddGameToLibButton().isVisible()) {
                    libraryLogic.animateAddButtonUp();
                }

                if (libraryLogic.isIsAutoLoadedOnce()) {
                    libraryLogic.autoClearAll();
                }
                libraryLogic.autoFindGames();

                libraryUI.getPnlAddGamePane().remove(1);
                libraryUI.getPnlAddGamePane().add(libraryUI.getPnlAutoAdd());
                try {
                    libraryUI.getPnlAddGamePane().setImageURL("addUI_bg2.png");
                } catch (MalformedURLException ex) {
                    java.util.logging.Logger.getLogger(LibraryHandler.class
                            .getName()).
                            log(Level.SEVERE, null, ex);
                }
                libraryUI.getPnlAddGamePane().revalidate();
                libraryUI.getPnlAddGamePane().repaint();
            }

        }
    }

    /**
     * Listener for when the Add Game To Library Button is pressed in the
     * Add Game UI
     */
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
                    currentPath = libraryUI.getCurrentPath();
                    gridManager = libraryUI.getGridSplit();
                    storage = libraryUI.getStorage();
                    GameBack = libraryUI.getGamesContainer();

                    if (storage.getStoredSettings().getSettingValue(
                            "organize") == null) {
                        storage.getStoredSettings().saveSetting(
                                "organize",
                                "favorite");
                    }

                    // Hide Add Game panel after animating button up
                    libraryLogic.getAddGameToLibButtonAnimator()
                            .appendPostAnimationListener(
                                    new APostHandler() {

                                        @Override
                                        public void postAction() {

                                            libraryUI.hideAddGameUI();

                                        }
                                    });
                    libraryLogic.animateAddButtonUp();

                    game.reAddInteractive();

                    // If in Manual mode Save current game to storage
                    if (libraryUI.getBtnManual().isSelected) {

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

                        //* reset cover to blank cover *//
                        gameSearch.resetCover();

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

                    String previousLibraryStatus = LibraryUI.lblLibraryStatus
                            .getCurrentText();

                    // Check if in Manual Mode
                    if (libraryUI.getBtnManual().isSelected) {

                        game.setCoverSize(libraryUI.getGameCoverWidth(),
                                libraryUI
                                .getGameCoverHeight());
                        game.reAddInteractive();

                        if (gridManager.addGame(game)) {

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

                            if (!game.isLoaded()) {
                                try {
                                    game.update();
                                } catch (MalformedURLException ex) {
                                    java.util.logging.Logger.getLogger(
                                            LibraryHandler.class
                                            .getName()).
                                            log(Level.SEVERE, null, ex);
                                }
                            }

                            game.setSettingsListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {

                                    libraryUI.showEditGameUI((Game) e
                                            .getSource());

                                }
                            });

                            GridMove = new MoveToGrid(game);
                            //* Transition towards to left most grid to see the game added *//
                            GridMove.runMover();

                            gridManager.unselectPrevious();

                            LibraryUI.lblLibraryStatus
                                    .setForeground(Color.green);
                            LibraryUI.lblLibraryStatus.setText("Added Game");

                            AMixpanelAnalytics mixpanelAnalytics = new AMixpanelAnalytics(
                                    "f5f777273e62089193a68f99f4885a55");
                            mixpanelAnalytics.addProperty("Game Added", game
                                    .getName());
                            mixpanelAnalytics.sendEventProperty("Added Game");

                            try {
                                Thread.sleep(1200);
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

                                    libraryUI.showEditGameUI((Game) e
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

                            if (libraryUI.getModelCheckList().size() > 0) {
                                // Remove radio button item
                                libraryUI.getModelCheckList().removeElementAt(
                                        libraryLogic.getAutoGameList().indexOf(
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
                        AMixpanelAnalytics mixpanelAnalytics = new AMixpanelAnalytics(
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
            if (!libraryUI.isAddGameUI_Visible()) {

                libraryUI.showAddGameUI();

            } else {

                libraryUI.hideAddGameUI();
            }

        }
    }

    public class ShowOrganizeGameHandler implements ActionListener {

        public ShowOrganizeGameHandler() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            libraryUI.showOrganizeUI();

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

        private final APopupMenu organizeUI;

        public UnSelectedOrganizeListener(ASlickLabel lbl, APopupMenu popup) {

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
            gameGrid = libraryUI.getGridSplit().findGame(selectedGame)[0];

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
                if (libraryUI.getCurrentIndex() < gameGrid) {
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

    public class GameLocationSettingListener implements ActionListener {

        private final JLabel label;

        public GameLocationSettingListener(JLabel lbl) {
            this.label = lbl;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            label.setForeground(Color.white);

            libraryUI.showGameLocationUI();

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
            libraryUI.showGameCoverUI();

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
            libraryUI.showOtherUI();

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

        private AHoverButton imgGameLeft;

        private AHoverButton imgGameRight;

        private AImage imgFavorite;

        private GridAnimation GridAnimate;

        public HoverButtonLeft() {
            gridManager = libraryUI.getGridSplit();
            GameBack = libraryUI.getGamesContainer();
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            imgGameLeft = libraryUI.getImgGameLeft();
            imgGameRight = libraryUI.getImgGameRight();
            imgFavorite = libraryUI.getImgOrganizeType();
            GridAnimate = libraryUI.getGridAnimate();

            gridManager = libraryUI.getGridSplit();

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
                    imgGameLeft.mouseExit();
                }

                if (currentIndex < gridManager.getArray().size()) {

                    if (currentIndex - 1 <= 0) {
                        // Far Left Image
                        GameBack.remove(0);
                        GameBack.add(imgFavorite, BorderLayout.WEST, 0);

                    } else {
                        // Left Button
                        GameBack.remove(0);
                        GameBack.add(imgGameLeft, BorderLayout.WEST, 0);
                    }
                    // Add GameCover Covers

                    GridAnimate.moveLeft(currentIndex);

                    try {
                        libraryLogic.loadGames(currentIndex - 1);
                    } catch (MalformedURLException ex) {
                        logger.error(ex);
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
            imgFavorite = libraryUI.getImgOrganizeType();
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
            imgFavorite = libraryUI.getImgOrganizeType();
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

        private GridAnimation GridAnimate;

        private final AuroraCoreUI coreUI;

        public HoverButtonRight() {
            this.coreUI = libraryUI.getCoreUI();

            GameBack = libraryUI.getGamesContainer();
            imgGameLeft = libraryUI.getImgGameLeft();
            imgGameRight = libraryUI.getImgGameRight();
            imgFavorite = libraryUI.getImgOrganizeType();
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
                        logger.error(ex);
                    }

                    // Off on last Grid then dont show right arrow button
                    if (!(libraryUI.getCurrentIndex() + 1 < gridManager
                            .getArray()
                            .size() - 1)) {

                        GameBack.remove(libraryUI.getImgGameRight());
                        GameBack.add(Box.createHorizontalStrut(140),
                                BorderLayout.EAST, 2);
                        imgGameRight.mouseExit();
                    }
                }

                coreUI.getCenterPanel().removeAll();
                coreUI.getCenterPanel().add(BorderLayout.CENTER, libraryUI
                        .getGamesContainer());

                GameBack.repaint();
                GameBack.revalidate();

            }
            imgGameRight.mouseExit();
        }

        @Override
        public void mouseEntered(final MouseEvent e) {

            if (logger.isDebugEnabled()) {
                logger.debug("HOVER IMAGE ACTIVATED");
            }

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

    /**
     * Handler for the Navigation using Keyboard
     */
    public class GameLibraryKeyListener extends KeyAdapter {

        private GridManager gridManager;

        private JPanel GameBack;

        private final AuroraCoreUI coreUI;

        private AuroraStorage storage;

        public GameLibraryKeyListener() {
            this.coreUI = libraryUI.getCoreUI();
            GameBack = libraryUI.getGamesContainer();
            storage = libraryUI.getStorage();
        }

        @Override
        public void keyPressed(KeyEvent e) {

            int currentIndex;
            gridManager = libraryUI.getGridSplit();

            /*
             * get the index of the grid that is currently displayed
             */
            int visibleGridIndex = gridManager.getVisibleGridIndex();

            if (logger.isDebugEnabled()) {
                logger.debug("Initial visible grid = " + visibleGridIndex);
            }

            currentIndex = gridManager.getArray()
                    .indexOf(GameBack.getComponent(1));
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

            String wasdNavSetting = storage.getStoredSettings().getSettingValue("wasd_navigation");
            if(wasdNavSetting == null){
                wasdNavSetting = SettingsLogic.DEFAULT_WASD_NAV_SETTING;
            }

            if ((e.getKeyCode() == KeyEvent.VK_W && wasdNavSetting.equals("enabled"))|| e.getKeyCode()
                                                   == KeyEvent.VK_UP) {

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
                                gridManager.unselectPrevious();
                                newGame.showOverlayUI();
                            }

                        } else if (row == 1) {
                            // Check for GamePlaceholder CANT MOVE THERE!
                            if (!(comp.get(i + (4 * 1)) instanceof GamePlaceholder)) {
                                game.hideOverlayUI();
                                Game newGame = (Game) comp.get(i + (4 * 1));
                                gridManager.unselectPrevious();
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
            } else if ((e.getKeyCode() == KeyEvent.VK_S && wasdNavSetting.equals("enabled")) || e.getKeyCode()
                                                          == KeyEvent.VK_DOWN) {

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
                                gridManager.unselectPrevious();
                                newGame.showOverlayUI();
                            }

                        } else if (row == grid.getRow()) {

                            // Check for GamePlaceholder CANT MOVE THERE!
                            if (!(comp.get(i - (4 * 1)) instanceof GamePlaceholder)) {
                                game.hideOverlayUI();
                                Game newGame = (Game) comp.get(i - (4 * 1));
                                gridManager.unselectPrevious();
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
            } else if ((e.getKeyCode() == KeyEvent.VK_A && wasdNavSetting.equals("enabled")) || e.getKeyCode()
                                                          == KeyEvent.VK_LEFT) {

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
                            logger.debug("visible grid after moving right = "
                                         + visibleGridIndex);
                        }

                        game.hideOverlayUI();
                        Game newGame = (Game) comp.get(i - 1);
                        gridManager.unselectPrevious();
                        newGame.showOverlayUI();
                        cursorMoved = true;
                    } else if (col == 1 && row == 1) {

                        if (gridManager.getArray().indexOf(GameBack
                                .getComponent(1)) > 0) {
                            libraryUI.moveGridLeft();
                            /*
                             * get the index of the grid that is currently
                             * displayed
                             */
                            visibleGridIndex = gridManager.getVisibleGridIndex();

                            if (logger.isDebugEnabled()) {
                                logger
                                        .debug(
                                                "visible grid after moving right = "
                                                + visibleGridIndex);
                            }

                            currentIndex = gridManager.getArray()
                                    .indexOf(GameBack
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
                                Game newGame = (Game) comp.get(comp.size() - 1);
                                gridManager.unselectPrevious();
                                newGame.showOverlayUI();
                            }
                        } else {
                            if (logger.isDebugEnabled()) {
                                logger.debug(
                                        "Cursor cannot move any further left!");
                            }
                        }

                    }
                } else if (!selectedGameFound && (comp.get(0) instanceof Game)) {
                    game = (Game) comp.get(0);
                    game.showOverlayUI();
                }

                // >>> MOVE RIGHT
            } else if ((e.getKeyCode() == KeyEvent.VK_D && wasdNavSetting.equals("enabled")) || e.getKeyCode()
                                                          == KeyEvent.VK_RIGHT) {
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
                    if ((col < grid.getCol()
                         || (col == grid.getCol() && row < grid.getRow()))
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
                            gridManager.unselectPrevious();
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
                            logger
                                    .debug(
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
                            visibleGridIndex = gridManager.getVisibleGridIndex();

                            if (logger.isDebugEnabled()) {
                                logger
                                        .debug(
                                                "visible grid after moving right = "
                                                + visibleGridIndex);
                            }

                            currentIndex = gridManager.getArray()
                                    .indexOf(GameBack
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
                                                "Cannot move to the grid to the right. No more grids!");
                            }
                        }

                    }
                } else if (!selectedGameFound && (comp.get(0) instanceof Game)) {
                    game = (Game) comp.get(0);
                    game.showOverlayUI();
                }
            } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {

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

            if (logger.isDebugEnabled()) {
                logger.debug("Mouse wheel moved " + numberClicks);
            }

            // -
            // Get The Index of The Current Panel Being Displayed
            // Refer too GridManager array of All panels to find it
            // GameBack is the Panel Containing all the game grids
            // -
            currentIndex = gridManager.getArray().indexOf(libraryUI
                    .getGamesContainer()
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
            if (!libraryUI.isEditGameCoverUI_visible()) {
                libraryUI.showEditGameCoverUI(game);
            }

        }

    }

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
                public void postAction() {
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

        private String newGameName;

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
            final JPanel contentContainer = new JPanel(new FlowLayout(
                    FlowLayout.CENTER, -30, 5));
            contentContainer.setOpaque(false);

            contentContainer.add(Box.createVerticalStrut(220));
            contentContainer.add(progressWheel);
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
                    AImagePane scaledCoverArt = coverArt;

                    newGameName = coverArt.getImageURL();

                    int scaledHeight = dragPane.getRealImageHeight() - 8;
                    int scaledWidth = (scaledHeight * coverArt
                            .getRealImageWidth()) / coverArt
                            .getRealImageHeight();

                    scaledCoverArt.setImageSize(scaledWidth,
                            scaledHeight);
                    scaledCoverArt.setPreferredSize(new Dimension(scaledWidth,
                            scaledHeight));

                    contentContainer.removeAll();
                    contentContainer.revalidate();

                    contentContainer.add(scaledCoverArt);
                    dragPane.add(resetContainer, BorderLayout.EAST);
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

        public String getNewGameName() {
            return newGameName;
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
            if (status.getImgURl().equals("addUI_badge_invalid.png")) {

                libraryLogic.flashInvalidStatus(status);

            } else {

                libraryUI.hideEditCoverFrame();
                libraryLogic.editCover(editingGame, dragListener
                        .getNewGameName());
            }

        }

    }

    public class GameSearchButtonListener implements ActionListener {

        private final AImage icon;

        private final GameSearch gameSearch;

        public GameSearchButtonListener(GameSearch search, AImage icon) {
            this.gameSearch = search;
            this.icon = icon;

            gameSearch.setStatusIcon(icon);

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
}
