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
package aurora.V1.core.screen_logic;

import aurora.V1.core.AuroraCoreUI;
import aurora.V1.core.AuroraStorage;
import aurora.V1.core.Game;
import aurora.V1.core.screen_handler.DashboardHandler;
import aurora.V1.core.screen_ui.DashboardUI;
import aurora.V1.core.screen_ui.GameLibraryUI;
import aurora.V1.core.screen_ui.GamerProfileUI;
import aurora.V1.core.screen_ui.SettingsUI;
import aurora.engine.V1.Logic.ANuance;
import aurora.engine.V1.UI.ACarouselPane;
import aurora.engine.V1.UI.AImagePane;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * .------------------------------------------------------------------------.
 * | DashboardLogic
 * .------------------------------------------------------------------------.
 * |
 * |
 * | This Class is the Logic component of the Dashboard Screen. Its instanced
 * | In DashboardUI.
 * |
 * | This class is supposed to handle all of the Long Processing of UI or
 * | Actions generated by the Handler. Reusable processing and long logic
 * | methods should go here.
 * |
 * .........................................................................
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 * <p/>
 */
public class DashboardLogic {

    /**
     * The UI Component of the Dashboard Screen.
     */
    private final DashboardUI dashboardUI;

    /**
     * The Handler Component of the Dashboard Screen.
     */
    private DashboardHandler dashboardHandler;

    /**
     * The AuroraStorage instance from DashboardUI.
     */
    private final AuroraStorage storage;

    /**
     * The AuroraCoreUI instance from DashboardUI.
     */
    private final AuroraCoreUI coreUI;

    /**
     * .-----------------------------------------------------------------------.
     * | DashboardLogic(DashboardUI)
     * .-----------------------------------------------------------------------.
     * |
     * | This is the Constructor of the Dashboard Logic class.
     * |
     * | The DashboardUI is required to make adjustments to the UI from the
     * | logic.
     * | The storage will be extracted from DashboardUI and initialized
     * | here.
     * | CoreUI will also be internally initialized here and extracted
     * | from DashboardUI.
     * |
     * | NOTE: for Logic to work you must use the set(HandlerDashboardHandler)
     * | method for the logic to be able to attach some handlers to UI
     * | elements
     * |
     * .........................................................................
     *
     * @param dashboardUi DashboardUI
     *
     */
    public DashboardLogic(final DashboardUI dashboardUi) {

        this.dashboardUI = dashboardUi;
        this.coreUI = dashboardUI.getCoreUI();

        this.storage = dashboardUI.getStorage();

    }

    /**
     * .-----------------------------------------------------------------------.
     * | setHandler(DashboardHandler)
     * .-----------------------------------------------------------------------.
     * |
     * | Pass the Handler Instanced in the DashboardUI
     * | For the Logic to work the Handler *MUST* be passed using this method
     * |
     * |
     * .........................................................................
     * <p/>
     * @param aDashboardHandler DashboardHandler
     */
    public final void setHandler(final DashboardHandler aDashboardHandler) {
        this.dashboardHandler = aDashboardHandler;
    }

    /**
     * .-----------------------------------------------------------------------.
     * | getLibraryIcon()
     * .-----------------------------------------------------------------------.
     * |
     * | This method tries to generate a random game if the storage contains
     * | any games.
     * |
     * | If no games are found in storage it will return a simple blank case
     * | icon
     * |
     * .........................................................................
     *
     * @return an ArrayList with info
     */
    public final AImagePane getLibraryIcon() {

        AImagePane icon;

        //* Double check there are no games in Storage *//
        System.out.println(storage);
        if (storage.getStoredLibrary().getBoxArtPath() == null || storage.
                getStoredLibrary().getBoxArtPath().isEmpty()) {

            //* Set icon to Blank Case *//
            icon = new AImagePane("Blank-Case.png",
                    dashboardUI.getGameCoverWidth(), dashboardUI.
                    getGameCoverHeight());

        } else {
            Random rand = new Random();

            //* Generate random num based on number of games in storage *//
            int randomNum = rand.nextInt(dashboardUI.getStorage().
                    getStoredLibrary().
                    getGameNames().size());

            //* Get the random game *//
            Game randomGame = new Game(dashboardUI.getStorage().
                    getStoredLibrary().
                    getBoxArtPath().
                    get(randomNum), dashboardUI);
            randomGame.setCoverSize(dashboardUI.getGameCoverWidth(),
                    dashboardUI.getGameCoverHeight());
            try {
                randomGame.update();
            } catch (MalformedURLException ex) {
                Logger.getLogger(DashboardUI.class.getName()).log(Level.SEVERE,
                        null, ex);
            }

            //* Disable overlay UI of Game *//
            randomGame.removeInteraction();
            //* Instead, when clicking on game, launch appropriate App *//
            randomGame.getInteractivePane().
                    addMouseListener(
                    dashboardHandler.new CarouselLibraryMouseListener());

            //* Now give icon the cleaned up Random game *//
            icon = randomGame;
        }

        return icon;

    }

    /**
     * .-----------------------------------------------------------------------.
     * | createFeed(ArrayList<String>) --> ArrayList <String>
     * .-----------------------------------------------------------------------.
     * |
     * | This method takes an array and fills it up with field for the info
     * | feed to output.
     * |
     * | An ArrayList which should contain nothing is required and in the output
     * | An ArrayList filled with latest info is given. This ArrayList should go
     * | into the InfoFeed component.
     * |
     * | If No ArrayList is provided (null) then this method will be super smart
     * | and not crash and totally be nice by creating one for you then
     * | offering it to you filled with sweet info totally for free
     * |
     * .........................................................................
     *
     * @param array ArrayList
     * <p/>
     * @return an ArrayList with info
     */
    public final ArrayList<String> createFeed(final ArrayList<String> array) {

        ArrayList<String> Array = null;

        if (array == null) {
            Array = new ArrayList<String>();
        } else {
            Array = array;
        }

        Array.add(coreUI.getVi().VI(ANuance.inx_Welcome) + ", ");
        Array.
                add(
                "How are you doing Today " + coreUI.getVi().VI(ANuance.inx_User)
                + " We hope you enjoy this Alpha release of the Aurora Game Manager");
        Array.add("Make Sure You Check out the Improved Game Library!");
        Array.add("It can totally do stuff now!");
        Array.add("It only took a year or so...");
        Array.add("Checkout our website at auroragm.sourceforge.net ");
        Array.add("Please feel free to contact me personally via e-mail");
        Array.add("> sguergachi@gmail.com < ");
        Array.add("Let me know if you find any bugs ");
        Array.add("I will personally attend to it that it is exterminated");
        Array.add("You can also contact me regarding feedback ");
        Array.add(
                "I will feed on your feedback, if you know what i'm saying ");
        Array.add(
                "Just so you know, I didn't put this bar here to annoy you ");
        Array
                .add(
                "I plan on having it show you a live feed of breaking gaming news ");
        Array.add("As well as tracking information from your profile ");
        Array.add("When ever the heck that gets done... ");
        Array.add("Its gonna be awesome and super useful, I promise ");
        Array
                .add(
                "Just to demonstrate how useful it's going to be, why don't I teach you the alphabet? ");
        Array.add("A B C D E F G H I J K L M N O P Q R S T U V W X Y Z ");
        Array.add("There you go, I just thought you the ABCs! ");
        Array
                .add(
                "Anyways, you don't have to hang around here, just click on the library to start! ");
        Array
                .add(
                "Just press Enter, or, Move your mouse and click on the big thing that says 'Library' ");
        Array.add("I can do this all day ");
        Array.add("And all night ");
        Array.add("Ohh, Breaking News! ");
        Array.add("There is a new Call of Duty game coming out Next Year!!");
        Array.add("Totally did not see that comming...");
        Array.add("I wonder what's going to be in it");
        Array
                .add(
                "I'm going to guess it has something to do with shooting guys with guns");
        Array
                .add(
                "Well, i'm tired, keep checking the Sourceforge page for new updates");
        Array.add("Have fun!");

        return Array;
    }

    /**
     * .-----------------------------------------------------------------------.
     * | launchAuroraApp(ACarouselPane aCarouselPane)
     * .-----------------------------------------------------------------------.
     * |
     * | This method takes in a CarouselPane and tries to determine which APP
     * | Is associated with the specific Carousel Pane and then Launch that APP
     * |
     * | The method does an if check on each known Carousel Pane found in
     * | Dashboard UI such as: LibraryPane, ProfilePane, SettingsPane etc.
     * | then it launches the appropriate UI of the APP associated with that
     * | Carousel Pane.
     * |
     * .........................................................................
     *
     * @param aCarouselPane ACarouselPane
     * <p/>
     */
    public final void launchAuroraApp(final ACarouselPane aCarouselPane) {

        ACarouselPane pane = aCarouselPane;

        if (pane == dashboardUI.getLibraryPane()) {
            //action on click right Panel
            if (dashboardUI != null) {
                GameLibraryUI libraryUI = new GameLibraryUI(dashboardUI
                        .getStartUI().getAuroraStorage(), dashboardUI,
                        dashboardUI.getCoreUI());
                libraryUI.loadUI();
            }
        } else if (pane == dashboardUI.getProfilePane()) {
            GamerProfileUI profileUI = new GamerProfileUI(dashboardUI,
                    dashboardUI.getCoreUI());
            profileUI.loadUI();
        } else if (pane == dashboardUI.getSettingsPane()) {
            SettingsUI settingsUI = new SettingsUI(dashboardUI,
                    dashboardUI.getCoreUI());
            settingsUI.loadUI();
        } else if (pane == dashboardUI.getAuroraNetPane()) {
            // do nothing for now
        }


    }

    /**
     * .-----------------------------------------------------------------------.
     * | navigateCarousel(ACarouselPane aCarouselPane)
     * .-----------------------------------------------------------------------.
     * |
     * | This method takes a CarouselPane and determines based on known points
     * | if it is the Center Pane, the Left Pane or the Right Pane. It then asks
     * | The Carousel in the DashboardUI to move Right, Left or launch the APP
     * | associated with that Pane depending on the location of that Pane.
     * |
     * | IF Pane on the Right Side  >>  Move Carousel to the Left
     * | IF Pane on the Left Side   >>  Move Carousel to the Right
     * | IF Pane is in the Center   >>  Launch App by passing pane to
     * |                                launchAuroraApp(ACarouselPane)
     * .........................................................................
     *
     * @param aCarouselPane ACarouselPane
     * <p/>
     */
    public final void navigateCarousel(final ACarouselPane aCarouselPane) {

        ACarouselPane pane = aCarouselPane;

        /* if Pane is to the Right side, move carousel Left */
        if (pane.getPointX() == dashboardUI.getCarousel().getRightX()) {
            dashboardUI.getCarousel().MoveLeft();

            /* if Pane is to the Left side, move carousel Right */
        } else if (pane.getPointX() == dashboardUI.getCarousel().getLeftX()) {
            dashboardUI.getCarousel().MoveRight();

            /* if Pane is in the Center then launch the App associated with it*/
        } else if (pane.getPointX() == dashboardUI.getCarousel().getCentX()) {
            this.launchAuroraApp(pane);
        }

    }
}
