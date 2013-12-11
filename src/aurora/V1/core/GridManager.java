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

import aurora.engine.V1.UI.AGridPanel;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JComponent;
import org.apache.log4j.Logger;

/**
 * GridManager An array of aGridPanels Provides methods to manipulate and get
 * Grids
 *
 * @author Sammy
 */
public class GridManager {

    private AuroraCoreUI ui;

    private int row;

    private int col;

    private final ArrayList<AGridPanel> Grids = new ArrayList<>();

    private int fullGrids;

    private GamePlaceholder blankAddGame;

    private ActionListener listener;

    private GamePlaceholder placeholder;

    private int width;

    private int height;

    private boolean needFinalizing = false;

    private boolean isTransitioningGame;

    private int visibleGrid;

    static final Logger logger = Logger.getLogger(GridManager.class);

    private int numGames;

    /**
     * Manages GridPanels for GameLibrary
     *
     * @param row
     * @param col
     * @param ui
     */
    public GridManager(int row, int col, AuroraCoreUI ui) {
        this.row = row;
        this.col = col;
        this.ui = ui;
        this.visibleGrid = 0;
    }

    GridManager() {
    }

    /**
     * initiate a default grid
     *
     * @param index Specific position to add Grid to Manager
     */
    public void initiateGrid(int index) {

        createGrid(row, col, index);

    }

    /**
     * Add a game to Grid
     *
     * @param game
     * @return
     */
    public Boolean addGame(Game game) {

        fullGrids = 0;
        for (int i = 0; i < Grids.size(); i++) {
            if (!isDupicate(game) || isTransitioningGame) {

                if (!Grids.get(i).isGridFull()) {

                    numGames++;

                    Grids.get(i).addToGrid(game);

                    isTransitioningGame = false; // Is Not Being Added to next Grid

                    if (logger.isDebugEnabled()) {
                        logger.debug("Added Game To GridPanel: " + game
                                .getName());

                        logger.debug("to Grid " + i);
                    }

                } else if (containsPlaceHolders(Grids.get(i))) {

                    // -
                    // Found ending placeholder panel, convert to "+" panel
                    // and end loop
                    // -
                    replacePlaceHolder(Grids.get(i), game, listener);
                    break;

                } else {
                    if (logger.isDebugEnabled()) {
                        logger.debug("UNABLE To add: " + game.getName());
                        logger.debug("Grid " + i + " is Full!");
                    }

                    fullGrids++;
                    //when Full make new Grid
                    if (fullGrids == Grids.size()) {
                        createGrid(row, col, Grids.size());
                        Grids.get(Grids.size() - 1).addToGrid(game);
                        isTransitioningGame = true; // Is Being Added to next Grid
                        if (logger.isDebugEnabled()) {
                            logger.debug("Added Game: " + game.getName());
                            logger.debug("to Grid " + (Grids.size() - 1));
                        }

                    }
                }

            } else {
                return false;
            }

        }

        return true;

    }

    /**
     * check if Game Cover Art is already in the library
     *
     * @param game GameCover to check for duplicates
     */
    public boolean isDupicate(Game game) {
        for (int i = 0; i < Grids.size(); i++) {
            for (int a = 0; a < Grids.get(i).getArray().size(); a++) {
                if (Grids.get(i).getArray().get(a) instanceof GamePlaceholder
                    == false) {
                    Game cover = (Game) Grids.get(i).getArray().get(a);
                    if (cover.getBoxArtUrl().equals(game.getBoxArtUrl())
                        && !game.getBoxArtUrl()
                            .equals("library_noGameFound.png")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Finds Same Game and returns that game in the library
     *
     * @param game
     *             <p/>
     * @return
     */
    public Game echoGame(Game game) {

        for (int i = 0; i < Grids.size(); i++) {
            for (int a = 0; a < Grids.get(i).getArray().size(); a++) {
                if (Grids.get(i).getArray().get(a) instanceof GamePlaceholder
                    == false) {

                    Game cover = (Game) Grids.get(i).getArray().get(a);
                    if (cover.getBoxArtUrl().equals(game.getBoxArtUrl())) {
                        return cover;
                    }
                }
            }
        }
        return null;

    }

    public void finalizeGrid(ActionListener listener, int width, int height) {
        this.listener = listener;

        this.width = width;
        this.height = height;

        this.blankAddGame = new GamePlaceholder();
        blankAddGame.setUp(width, height,
                "library_placeholder_bg.png");
        blankAddGame.addButton("library_placeholder_add_norm.png",
                "library_placeholder_add_down.png",
                "library_placeholder_add_over.png", listener);

        if (!Grids.get(Grids.size() - 1).isGridFull()) {

            Grids.get(Grids.size() - 1).addToGrid(blankAddGame);

        } else if (fullGrids == Grids.size() - 1) {
            fullGrids++;
            //when Full make new Grid
            createGrid(row, col, Grids.size());
            Grids.get(Grids.size() - 1).addToGrid(blankAddGame);
            if (logger.isDebugEnabled()) {
                logger.debug("Created new Grid: " + (Grids.size() - 1));
            }
        }

        addPlaceHolders(width, height);
    }

    /**
     * Adds Placeholder Items instead of Games using predefined image heights
     * and widths
     *
     * @param width
     * @param height
     */
    public void addPlaceHolders(int width, int height) {
        while (!Grids.get(Grids.size() - 1).isGridFull()) {
            this.placeholder = new GamePlaceholder();
            placeholder.setUp(width, height,
                    "library_placeholder_bg.png");

            Grids.get(Grids.size() - 1).addToGrid(placeholder);
        }
    }

    /**
     * check if any other cover was setSelected and sets it to setUnselected
     */
    public void unselectPrevious() {
        for (int i = 0; i < Grids.size(); i++) {
            for (int j = 0; j < Grids.get(i).getArray().size(); j++) {
                if (!(Grids.get(i).getArray().get(j) instanceof GamePlaceholder)) {
                    Game game = (Game) Grids.get(i).getArray().get(j);
                    if (game.isSelected()) {
                        game.setUnselected();
                        game.getGameBar().setVisible(false);
                        game.revalidate();
                    }
                }
            }

        }
    }

    /**
     * attempts to remove everything in grid.
     * <p/>
     */
    public void clearAllGrids() {

        for (int i = 0; i <= Grids.size(); i++) {

            try {
                if (logger.isDebugEnabled()) {
                    logger.debug("Clearing Grid... " + i);
                }

                Grids.get(i).getArray().clear();
                Grids.get(i).update();
                Grids.get(i).revalidate();

            } catch (RuntimeException ex) {
                logger.error(ex);
            }

        }

        System.gc();
    }

    /*
     * Replace placeholder with Game then add placeholder at the end
     * using finilize
     */
    private void replacePlaceHolder(AGridPanel gridPanel, Game game,
                                    ActionListener addGameHandler) {

        for (int a = (gridPanel.getArray().size() - 1); a >= 0; a--) {
            if (!(gridPanel.getArray().get(a) instanceof Game)) {
                gridPanel.removeComp((JComponent) gridPanel.getArray().get(a));
                gridPanel.update();
            }
        }

        gridPanel.addToGrid(game);
        gridPanel.update();
        gridPanel.update();
    }

    /**
     * find a game in any Grid.
     *
     * int[0] = Grid
     * int[1] = Index Position inside Grid
     *
     * @param GameCover object
     */
    public int[] findGame(Game game) {
        int GridPosition = -1;
        int Grid = -1;
        for (int i = 0; i < Grids.size(); i++) {
            if (Grids.get(i).find(game) != -1) {
                GridPosition = Grids.get(i).find(game);
                Grid = i;
            }
        }

        int[] find = new int[2];
        find[0] = Grid;
        find[1] = GridPosition;

        return find;
    }

    public boolean gameExists(Game game) {

        for (int i = 0; i < Grids.size(); i++) {
            if (logger.isDebugEnabled()) {
                logger.debug("GameName " + game.getName());
                logger.debug(Grids.get(i).find(game));
            }

            if (Grids.get(i).find(game) != -1) {
                return true;
            }
        }

        return false;
    }

    private boolean containsPlaceHolders(AGridPanel gridPanel) {

        for (int i = 0; i < Grids.size(); i++) {
            for (Object game : gridPanel.getArray()) {
                if (game instanceof GamePlaceholder) {
                    return true;
                }
            }
        }
        return false;

    }

    private boolean containsAddPlaceHolders(AGridPanel gridPanel) {

        for (int i = 0; i < Grids.size(); i++) {
            for (Object obj : gridPanel.getArray()) {
                if (obj instanceof GamePlaceholder) {
                    GamePlaceholder place = (GamePlaceholder) obj;
                    if (place.isContainsButton()) {
                        return true;
                    }
                }
            }
        }
        return false;

    }

    public int getVisibleGridIndex() {
        return visibleGrid;
    }

    public void decrementVisibleGridIndex() {
        visibleGrid--;
    }

    public void incrementVisibleGridIndex() {
        visibleGrid++;
    }

    /**
     * find a game in any Grid
     *
     * int[0] = Grid int[1] = GridPosition
     *
     * @param GameCover object
     */
    public int[] findGameName(String Name) {
        int Grid = -1;
        int GridPosition = -1;

        //Go Through all Grids
        for (int i = 0; i < Grids.size(); i++) {

            //Go Through All panels in Each Grid
            for (int a = 0; a < Grids.get(i).getArray().size(); a++) {
                try {
                    Game game = (Game) Grids.get(i).getArray().get(a);
                    if (game.getName().equalsIgnoreCase(Name)) {

                        Grid = i;
                        GridPosition = a;
                    }
                } catch (RuntimeException ex) {
                }
            }
        }

        int[] find = new int[2];
        find[0] = Grid;
        find[1] = GridPosition;

        return find;
    }

    /**
     * .-----------------------------------------------------------------------.
     * | getGame(int)
     * .-----------------------------------------------------------------------.
     * | Returns the Game object using index across all grids
     * .........................................................................
     *
     * <p/>
     */
    public Game getGame(int Index) {

        // Calculate what grid the game should be added to //
        int gridIndex = (Index / (row * col));
        // Calculate what index in specific grid the game should be added
        int gameIndex = Index % (row * col);

        return (Game) (Grids.get(gridIndex).getArray().get(gameIndex));
    }

    /**
     * .-----------------------------------------------------------------------.
     * | getGameFromName(String)
     * .-----------------------------------------------------------------------.
     * | Returns the Game object if its in the Library by searching through
     * | all of the grids.
     * .........................................................................
     *
     * <p/>
     */
    public Game getGameFromName(String GameName) {
        Game gameFound = null;

        try {
            gameFound = (Game) this.getGrid(this.findGameName(GameName)[0])
                    .getArray().get(this.findGameName(GameName)[1]);
        } catch (Exception ex) {
            gameFound = null;
        }

        return gameFound;

    }

    /**
     * removes a game in any Grid
     *
     * @param GameCover object
     */
    public void removeGame(Game game) {

        // get the grid location of where the game is contained
        int[] gridLocation = this.findGame(game);

        if (logger.isDebugEnabled()) {
            logger.debug("Game as found in grid location: " + gridLocation[0]
                         + "," + gridLocation[1]);
        }

        // grab the index of where the grid is located in the manager
        int index = gridLocation[0];

        // get the grid where the game is located
        AGridPanel grid = this.getGrid(index);

        // alternative to remove the game
        grid.removeComp(game);

        System.out.println("Number of grids that exist: " + Grids.size());

        if ((Grids.size() - 1) > index) {

            if (logger.isDebugEnabled()) {
                logger.debug("grid.size is > index");
            }

            for (int i = index; i < Grids.size() - 1; i++) {
                AGridPanel currentGrid = this.getGrid(i);
                AGridPanel nextGrid = this.getGrid(i + 1);
                Object o = nextGrid.getFirstComponent();

                if (o.getClass().equals(game.getClass())) {
                    nextGrid.removeComp((Game) o);
                    nextGrid.update();
                    currentGrid.addToGrid((Game) o);
                    currentGrid.update();
                } else {
                    Grids.remove(nextGrid);
                    if (!containsAddPlaceHolders(grid)) {
                        needFinalizing = true;
                    }
                }

            }

            // finalize the grid if there is no placeholder and it is the last grid in
            // in the library
        } else if (((Grids.size() - 1) == index) && (!containsAddPlaceHolders(
                grid))) {

            needFinalizing = true;

        }

        if (needFinalizing) {

            finalizeGrid(listener, width, height);
            needFinalizing = false;
        } else {
            addPlaceHolders(game.getWidth(), game.getHeight());
        }

        grid.update();
        numGames--;

    }

    /**
     * removes a game in any Grid
     *
     * @param GameCover object
     */
    public void moveFavorite(Game game) {

        // get the grid location of where the game is contained
        int[] gridLocation = this.findGame(game);

        if (logger.isDebugEnabled()) {
            logger.debug("Game was found in grid location: " + gridLocation[0]
                         + "," + gridLocation[1]);
        }

        // grab the index of where the grid is located in the manager
        int index = gridLocation[0];

        if (logger.isDebugEnabled()) {
            logger.debug("Game was found in index: " + index);
        }

        // get the grid where the game is located
        AGridPanel grid = this.getGrid(index);

        if (index == 0) {
            grid.removeComp(game);
            grid.addToGrid(game, 0);
            grid.update();
        } else if (index > 0) {
            // alternative to remove the game
            grid.removeComp(game);

            AGridPanel firstGrid = this.getGrid(0);

            for (int i = index - 1; i >= 0; i--) {
                AGridPanel currentGrid = this.getGrid(i);
                AGridPanel previousGrid = this.getGrid(i + 1);
                Game lastGame = (Game) currentGrid.getComponent(7);
                currentGrid.removeComp(lastGame);
                currentGrid.update();
                previousGrid.addToGrid(lastGame, 0);
                previousGrid.update();
            }

            firstGrid.addToGrid(game, 0);
            firstGrid.update();

        }

        grid.update();
        ui.getFrame().repaint();
    }

    /**
     * Moves a game that has been unfavorited. The unfavorited game will be moved
     * to the location before the first unfavorited game that is found in the library
     *
     * @param GameCover object
     */
    public void moveUnfavorite(Game game) {

        // get the grid location of where the game is contained
        int[] gridLocation = this.findGame(game);

        if (logger.isDebugEnabled()) {
            logger.debug("Game was found in grid location: " + gridLocation[0]
                         + "," + gridLocation[1]);
        }

        // grab the index of where the grid is located in the manager
        int index = gridLocation[0];

        if (logger.isDebugEnabled()) {
            logger.debug("Game was found in index: " + index);
        }

        // get the grid where the game is located
        AGridPanel grid = this.getGrid(index);

        int firstUnfavoriteGridIndex = 0;
        int firstUnfavoriteGameIndex = 0;
        int i = index;
        int j = gridLocation[1] + 1;
        boolean firstUnfavouriteFound = false;
        AGridPanel currentGrid = null;
        int lastGameIndex = 0;

        while ((i < Grids.size()) && !firstUnfavouriteFound) {
            currentGrid = this.getGrid(i);
            lastGameIndex = currentGrid.getLastIndexOf(Game.class);
            Game lastGame = (Game) currentGrid.getComponent(lastGameIndex);

            if (!lastGame.isFavorite()) {
                // -
                // if the last game is not a favourite then we know to look in this grid
                // for the first game that is not favourited
                // -

                while ((j <= lastGameIndex) && !firstUnfavouriteFound) {
                    Game g = (Game) currentGrid.getComponent(j);
                    if (!g.isFavorite()) {
                        firstUnfavouriteFound = true;
                        firstUnfavoriteGridIndex = i;
                        firstUnfavoriteGameIndex = j;
                    }
                    j++;
                }
            }

            i++;
            j = 0;
        }

        if (index == firstUnfavoriteGridIndex) {
            grid.removeComp(game);
            grid.update();

            // if all is faved, move to last possible cell
            if (firstUnfavoriteGameIndex == 0) {
                firstUnfavoriteGameIndex = lastGameIndex + 1;
            }

            grid.addToGrid(game, firstUnfavoriteGameIndex - 1);
            grid.update();

            // -
            // if the first unfavourite game is found in a grid different
            // than the grid where the game was selected as unfavourite
            // -
        } else if (index < firstUnfavoriteGridIndex) {

            AGridPanel prevGrid = grid;
            AGridPanel currGrid = null;

            // -
            // check to see if the first unfavourite game is in the next grid and if so, is it
            // the first game in the grid.  If it is, then we simply move the unfavourite
            // game to the end of the current grid it is in
            // -
            if (firstUnfavoriteGameIndex == 0) {
                if ((index + 1) == firstUnfavoriteGridIndex) {
                    prevGrid.removeComp(game);
                    prevGrid.update();
                    prevGrid.addToGrid(game, 7);
                    prevGrid.update();
                } else if ((index + 1) < firstUnfavoriteGridIndex) {
                    for (int k = index + 1; k < firstUnfavoriteGridIndex; k++) {

                        // remove the unfavourited game
                        prevGrid.removeComp(game);
                        prevGrid.update();

                        // get the first game of the next
                        currGrid = this.getGrid(k);
                        // get the first game of the next grid and move
                        // it to the previous grid
                        Game tempGame = (Game) currGrid.getComponent(0);
                        currGrid.removeComp(tempGame);
                        currGrid.update();
                        prevGrid.addToGrid(tempGame, 7);
                        prevGrid.update();

                        currGrid.addToGrid(game, 7);
                        currGrid.update();
                        prevGrid = currGrid;
                    }
                }
            } else if (firstUnfavoriteGameIndex > 0) {

                for (int k = index + 1; k <= firstUnfavoriteGridIndex; k++) {

                    // remove the unfavourited game
                    prevGrid.removeComp(game);
                    prevGrid.update();

                    // get the first game of the next
                    currGrid = this.getGrid(k);

                    // get the first game of the next grid and move
                    // it to the previous grid
                    Game tempGame = (Game) currGrid.getComponent(0);
                    currGrid.removeComp(tempGame);
                    currGrid.update();
                    prevGrid.addToGrid(tempGame, 7);
                    prevGrid.update();

                    if (k == firstUnfavoriteGridIndex) {
                        currGrid.addToGrid(game, firstUnfavoriteGameIndex - 1);
                        currGrid.update();
                    }

                    prevGrid = currGrid;
                }
            }
        }

        grid.update();
        ui.getFrame().repaint();
    }

    /**
     * Moves game to proper alphabetic positioning in library.
     *
     * @param game
     */
    public void moveAlphabetic(Game game) {

        ArrayList<String> alphaArray = new ArrayList<String>();

        //Go Through all Grids
        for (int i = 0; i < Grids.size(); i++) {

            //Go Through All panels in Each Grid
            for (int a = 0; a < Grids.get(i).getArray().size(); a++) {

                alphaArray.add(((Game) Grids.get(i).getArray().get(a))
                        .getName());

            }

        }

        Collections.sort(alphaArray);

        int alphaIndex = alphaArray.indexOf(game.getName());

        // Calculate what grid the game should be added to //
        int gridIndex = (alphaIndex / (row * col));
        // Calculate what index in specific grid the game should be added
        int gameIndex = alphaIndex % (row * col);

        // get the grid location of where the game is contained
        int[] gridLocation = this.findGame(game);

        // grab the index of where the grid is located in the manager
        int index = gridLocation[0];

        if (logger.isDebugEnabled()) {
            logger.debug("Game was found in index: " + index);
        }

        // get the grid where the game is located
        AGridPanel grid = this.getGrid(index);

        if (index == 0) {
            grid.removeComp(game);
            grid.addToGrid(game, gameIndex);
            grid.update();
        } else if (index > 0) {
            // alternative to remove the game
            grid.removeComp(game);

            AGridPanel firstGrid = this.getGrid(gridIndex);

            for (int i = index - 1; i >= 0; i--) {
                AGridPanel currentGrid;
                AGridPanel previousGrid;

                currentGrid = this.getGrid(i);
                previousGrid = this.getGrid(i + 1);

                try {
                    Game lastGame = (Game) currentGrid.getComponent(7);

                    currentGrid.removeComp(lastGame);
                    currentGrid.update();
                    previousGrid.addToGrid(lastGame, 0);
                    previousGrid.update();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            firstGrid.addToGrid(game, gameIndex);
            firstGrid.update();

        }

        grid.update();
        ui.getFrame().repaint();
        alphaArray = null;
    }

    /**
     * Create and add a new GridPanel to the Grids ArrayList
     *
     * @param row   Row Number
     * @param col   Column Number
     * @param index specific position to add new Grid
     *
     */
    public void createGrid(int row, int col, int index) {
        AGridPanel GridPanel = new AGridPanel(row, col, true);

        Grids.add(index, GridPanel);
    }

    /**
     * Returns AGridPanel which may contain JComponents in a grid
     *
     * @param panelIndex
     * @return AGridPanel
     *
     */
    public AGridPanel getGrid(int panelIndex) {

        return Grids.get(panelIndex);

    }

    /**
     * check if any other cover was setSelected and sets it to setUnselected
     */
    public AGridPanel getSelectedGrid() {
        for (int i = 0; i < Grids.size(); i++) {
            for (int j = 0; j < Grids.get(i).getArray().size(); j++) {
                try {
                    Game game = (Game) Grids.get(i).getArray().get(j);
                    if (game.isSelected()) {
                        return Grids.get(i);
                    }
                } catch (RuntimeException ex) {
                }
            }

        }
        return null;
    }

    /**
     * Returns an Array filled with all AGridPanel's
     *
     * @return Grid ArrayList
     *
     */
    public ArrayList getArray() {
        return Grids;
    }

    public int getFullGrids() {
        return fullGrids;
    }

    public int getNumberOfGrids() {
        return Grids.size();
    }
}
