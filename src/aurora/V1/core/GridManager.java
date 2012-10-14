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

import aurora.engine.V1.UI.aDialog;
import aurora.engine.V1.UI.aGridPanel;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JComponent;

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
    private ArrayList<aGridPanel> Grids = new ArrayList<aGridPanel>();
    private int fullGrids;
    private GamePlaceholder blankAddGame;
    private ActionListener listener;
    private GamePlaceholder placeholder;
    private int width;
    private int height;
    private boolean needFinalizing = false;
    private boolean isTransitioningGame;
    private int visibleGrid;

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
     * @param GameCover object
     */
    public void addGame(Game game) {
        fullGrids = 0;
        for (int i = 0; i < Grids.size(); i++) {
            if (!isDupicate(game) || isTransitioningGame) {

                if (!Grids.get(i).isGridFull()) {

                    Grids.get(i).addToGrid(game);
                    isTransitioningGame = false; // Is Not Being Added to next Grid
                    System.out.println("Added Game To GridPanel: " + game.getName());
                    System.out.println("to Grid " + i);

                } else if (containsPlaceHolders(Grids.get(i))) {

                    replacePlaceHolder(Grids.get(i), game, listener);

                } else {
                    System.out.println("FAILED To add: " + game.getName());
                    System.out.println("Grid " + i + " is Full!");
                    fullGrids++;
                    //when Full make new Grid
                    if (fullGrids == Grids.size()) {
                        createGrid(row, col, Grids.size());
                        Grids.get(Grids.size() - 1).addToGrid(game);
                        isTransitioningGame = true; // Is Being Added to next Grid
                        System.out.println("Added Game: " + game.getName());
                        System.out.println("to Grid " + (Grids.size() - 1));

                    }
                }
            } else {
                aDialog info = new aDialog(aDialog.aDIALOG_WARNING, "Cannot Add Duplicate Box Art", ui.getDefaultFont());
                info.showDialog();
                info.setVisible(true);
                echoGame(game).selected();
            }

        }


    }

    /**
     * check if Game Cover Art is already in the library
     *
     * @param game GameCover to check for duplicates
     */
    public boolean isDupicate(Game game) {
        for (int i = 0; i < Grids.size(); i++) {
            for (int a = 0; a < Grids.get(i).getArray().size(); a++) {
                if (Grids.get(i).getArray().get(a) instanceof GamePlaceholder == false) {
                    Game cover = (Game) Grids.get(i).getArray().get(a);
                    if (cover.getBoxArtUrl().equals(game.getBoxArtUrl())) {
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
     * @return
     */
    public Game echoGame(Game game) {

        for (int i = 0; i < Grids.size(); i++) {
            for (int a = 0; a < Grids.get(i).getArray().size(); a++) {
                if (Grids.get(i).getArray().get(a) instanceof GamePlaceholder == false) {
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

        if (!Grids.get(Grids.size() - 1).isGridFull()) {



            this.blankAddGame = new GamePlaceholder();
            blankAddGame.setUp(width + 10, height + 10, "EmptyGrid.png");
            blankAddGame.addButton("AddToEmpty_up.png", "AddToEmpty_down.png", "AddToEmpty_over.png", listener);
            Grids.get(Grids.size() - 1).addToGrid(blankAddGame);

        }

        addPlaceHolders(width, height);
        //Grids.get(fullGrids).revalidate();
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
            placeholder.setUp(width + 10, height + 10, "EmptyGrid.png");

            Grids.get(Grids.size() - 1).addToGrid(placeholder);
//            System.out.println("Is Last Grid Full? True or False: " + Grids.get(fullGrids).checkFull());
        }
        //Grids.get(fullGrids).revalidate();
    }

    /**
     * check if any other cover was selected and sets it to unselected
     */
    public void unselectPrevious() {
        for (int i = 0; i < Grids.size(); i++) {
            for (int j = 0; j < Grids.get(i).getArray().size(); j++) {
                try {
                    Game game = (Game) Grids.get(i).getArray().get(j);
                    if (game.isSelected()) {
                        game.unselected();
                        game.getGameBar().setVisible(false);
                        game.revalidate();
                    }
                } catch (RuntimeException ex) {
                }
            }

        }
    }

    //attempts to remove everything in grid.
    public void clearAllGrids() {
        for (int i = 0; i < Grids.size(); i++) {

            try {
                System.out.println("Clearing Grid... " + i);

                Grids.get(i).getArray().clear();
                Grids.get(i).update();
                Grids.get(i).revalidate();


            } catch (RuntimeException ex) {
            }

        }
    }

    private void replacePlaceHolder(aGridPanel gridPanel, Game game, ActionListener addGameHandler) {

        //Replace placeholder with Game then add placeholder at the end
        //using finilize

        for (int a = (gridPanel.getArray().size() - 1); a >= 0; a--) {
            if (!(gridPanel.getArray().get(a) instanceof Game)) {
                gridPanel.removeComp((JComponent) gridPanel.getArray().get(a));
                gridPanel.update();
            }
        }

        gridPanel.addToGrid(game);
        gridPanel.update();
        this.finalizeGrid(addGameHandler, game.getWidth(), game.getHeight());
        gridPanel.update();
    }

    /**
     * find a game in any Grid int[0] = Grid int[1] = GridPosition
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
            System.out.println("GameName " + game.getName());
            System.out.println(Grids.get(i).find(game));
            if (Grids.get(i).find(game) != -1) {
                return true;
            }
        }

        return false;
    }

    private boolean containsPlaceHolders(aGridPanel gridPanel) {

        for (int i = 0; i < Grids.size(); i++) {
            for (Object game : gridPanel.getArray()) {
                if (game instanceof GamePlaceholder) {
                    return true;
                }
            }
        }
        return false;

    }

    private boolean containsAddPlaceHolders(aGridPanel gridPanel) {

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
                    //System.out.println("GameCover Name Searching... " + game.getName());
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
     * removes a game in any Grid
     *
     * @param GameCover object
     */
    public void removeGame(Game game) {

        //TODO support appostrophe removal

        // get the grid location of where the game is contained
        int[] gridLocation = this.findGame(game);
        System.out.println("Game as found in grid location: " + gridLocation[0] + "," + gridLocation[1]);

        // grab the index of where the grid is located in the manager
        int index = gridLocation[0];

        // get the grid where the game is located
        aGridPanel grid = this.getGrid(index);


        // alternative to remove the game
        grid.removeComp(game);

        System.out.println("Number of grids that exist: " + Grids.size());

        if ((Grids.size() - 1) > index) {
            for (int i = index; i < Grids.size() - 1; i++) {
                aGridPanel currentGrid = this.getGrid(i);
                aGridPanel nextGrid = this.getGrid(i + 1);
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
        } else if (!containsAddPlaceHolders(grid) && Grids.size() == 1) {

            needFinalizing = true;

        }


        if (needFinalizing) {

            finalizeGrid(listener, width, height);
            needFinalizing = false;
        } else {
            addPlaceHolders(game.getWidth(), game.getHeight());
        }

        grid.update();


    }

    /**
     * Create and add a new GridPanel to the Grids ArrayList
     *
     * @param row Row Number
     * @param col Column Number
     * @param index specific position to add new Grid
     *
     */
    public void createGrid(int row, int col, int index) {
        aGridPanel GridPanel = new aGridPanel(row, col, true);

        Grids.add(index, GridPanel);
    }

    /**
     * Returns aGridPanel which may contain JComponents in a grid
     *
     * @param PanelIndex
     * @return aGridPanel
     *
     */
    public aGridPanel getGrid(int panelIndex) {

        return Grids.get(panelIndex);

    }

    /**
     * check if any other cover was selected and sets it to unselected
     */
    public aGridPanel getSelectedGrid() {
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
     * Returns an Array filled with all aGridPanel's
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
