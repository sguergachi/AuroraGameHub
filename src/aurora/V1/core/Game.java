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

import aurora.V1.core.screen_logic.LibraryLogic;
import aurora.V1.core.screen_logic.SettingsLogic;
import aurora.V1.core.screen_logic.WelcomeLogic;
import aurora.V1.core.screen_ui.DashboardUI;
import aurora.V1.core.screen_ui.LibraryUI;
import aurora.engine.V1.Logic.AFileManager;
import aurora.engine.V1.Logic.ASound;
import aurora.engine.V1.Logic.AThreadWorker;
import aurora.engine.V1.UI.AButton;
import aurora.engine.V1.UI.ADialog;
import aurora.engine.V1.UI.AImagePane;
import aurora.engine.V1.UI.AProgressWheel;
import aurora.engine.V1.UI.AScrollBar;
import aurora.engine.V1.UI.ASlickLabel;
import aurora.engine.V1.UI.ATextField;
import aurora.engine.V1.UI.ATimeLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import org.apache.log4j.Logger;

/**
 * .------------------------------------------------------------------------.
 * | Game
 * .------------------------------------------------------------------------.
 * |
 * |
 * | This Class is the Game Object that contains the UI and functionality
 * | For the Game Cover found in the Library along with the MetaData of
 * | The actual Game.
 * |
 * .........................................................................
 * <p/>
 * @author Sammy Guergachi <sguergachi at gmail.com>
 * @author Carlos Machado <camachado@gmail.com>
 */
public class Game extends AImagePane implements Runnable, Cloneable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String coverURL;

    private String gamePath;

    private String timePlayed = null;

    private String lastPlayed;

    private String gameType;

    private int numberTimesPlayed;

    private int width;

    private int height;

    private int SIZE_TOPPANE_COMP;

    private int SIZE_BottomPaneHeight;

    private Thread gameCoverThread;

    private boolean isFavorite;

    private boolean isLoaded = false;

    private boolean isSelected;

    private boolean isRemoved = false;

    private AProgressWheel progressWheel;

    private AImagePane coverImagePane;

    private AImagePane imgSelectedGlow;

    private AImagePane imgStarIcon;

    private AImagePane imgOverlayBar;

    private AImagePane imgConfirmPromptImagePane;

    private JPanel pnlInteractivePane;

    private JPanel topPanel;

    private JPanel pnlFlipPane;

    private JPanel pnlFavoritePane;

    private JPanel bottomPanel;

    private JPanel pnlOverlayContainer;

    private JPanel confirmPanel;

    private JPanel denyPanel;

    private AButton btnRemove;

    private AButton btnFavorite;

    private AButton btnFlip;

    private AButton btnPlay;

    private AButton confirmButton;

    private AButton denyButton;

    private GridManager manager;

    private AuroraCoreUI coreUI;

    private DashboardUI dashboardUI;

    private AuroraStorage storage;

    private final String rootCoverDBPath = "https://s3.amazonaws.com/CoverArtDB/";

    private PlayButtonListener playButtonListener;

    private boolean isGameRemoveMode;

    private int removeButtonWidth;

    private int removeButtonSeperation;

    private boolean isFliped;

    static final Logger logger = Logger.getLogger(Game.class);

    private AButton btnAward;

    private JPanel pnlAwardPane;

    private AButton btnSetting;

    private ASlickLabel lblHoursPlayed;

    private ASlickLabel lblLastPlayed;

    private ASlickLabel lblTimesPlayed;

    private ASlickLabel lblGameType;

    private ATextField txtHoursPlayed;

    private ATextField txtLastPlayed;

    private ATextField txtTimesPlayed;

    private ATextField txtGameType;

    private AImagePane pnlShortcutImage;

    private JScrollPane pnlFlipScrollPane;

    private JScrollBar flipScrollBar;

    private JPanel pnlFlipContentPane;

    private JPanel pnlRightPane;

    private JPanel pnlFlipContainer;

    private JPanel pnlTopImageContainer;

    private boolean isFlipUIReady;

    private ASlickLabel lblShortcut;

    private JPanel pnlShortcutLbl;

    private AButton btnWatch;

    private AButton btnFix;

    private AButton btnLearn;

    private JPanel pnlShortcutBtn;

    private int flipShortcutWidth;

    private int flipShortcutHeight;

    private int labelFontSize;

    private int flipPadding;

    private LibraryLogic libraryLogic;

    private ImageIcon localImage;

    private AButton btnAddCustomOverlay;

    private ActionListener customGameCoverListener;

    private String localGameRootPath;

    public Game() {
    }

    public Game(final GridManager gridManager, final AuroraCoreUI auroraCoreUI,
                final DashboardUI dashboardUi) {

        this.dashboardUI = dashboardUi;
        this.coreUI = auroraCoreUI;
        this.manager = gridManager;
        this.setOpaque(false);
        this.setDoubleBuffered(true);

        //DEFAULT CASE
        this.setImage("Blank-Case.png", height, width);
        this.setPreferredSize(new Dimension(width, height));

    }

    public Game(final GridManager manager, final AuroraCoreUI ui,
                final DashboardUI dashboard, final AuroraStorage storage) {

        this.dashboardUI = dashboard;
        this.coreUI = ui;
        this.storage = storage;
        this.manager = manager;
        this.setOpaque(false);
        this.setDoubleBuffered(true);

        //DEFAULT CASE
        this.setImage("Blank-Case.png", height, width);
        this.setPreferredSize(new Dimension(width, height));

    }

    public Game(final GridManager manager, final AuroraCoreUI ui,
                final String CoverURL) {

        this.coreUI = ui;
        this.manager = manager;
        this.setOpaque(false);
        this.setDoubleBuffered(true);
        this.coverURL = CoverURL;

        //DEFAULT CASE
        this.setImage("Blank-Case.png", height, width);
        this.setPreferredSize(new Dimension(width, height));

    }

    public Game(final String CoverURL, final DashboardUI dashboard) {

        this.setOpaque(false);
        this.coreUI = dashboard.getCoreUI();
        this.dashboardUI = dashboard;
        this.coverURL = CoverURL;
        this.storage = dashboardUI.getStorage();

        //DEFAULT CASE
        this.setImage("Blank-Case.png", height, width);
        this.setPreferredSize(new Dimension(width, height));

    }

    public Game(final DashboardUI dashboard) {

        this.setOpaque(false);
        this.dashboardUI = dashboard;
        this.coreUI = dashboard.getCoreUI();
        this.storage = dashboardUI.getStorage();

        //DEFAULT CASE
        this.setImage("Blank-Case.png", height, width);
        this.setPreferredSize(new Dimension(width, height));

    }

    /**
     * .-----------------------------------------------------------------------.
     * | update()
     * .-----------------------------------------------------------------------.
     * |
     * | This method loads the Game Cover based on the coverURL. It saves the
     * | games cover locally by default and if it doesnt exist it downloads the
     * | image from servers.
     * |
     * | this also adds the Interactive Panel which is the overlayed UI on top
     * | of the Game to allow for manipulation.
     * |
     * .........................................................................
     * <p/>
     * @throws MalformedURLException Exception
     *
     */
    public final void update() throws MalformedURLException {

        // Set Up Interactive Overlay Panel
        // ----------------------------------------------------------------.
        pnlInteractivePane = new JPanel(new BorderLayout());
        pnlInteractivePane.setOpaque(false);
        pnlInteractivePane.setName("pnlInteractivePane");
        pnlInteractivePane.addMouseListener(new Game.InteractiveListener());
        this.addMouseListener(new Game.InteractiveListener());
        this.add(pnlInteractivePane);
        this.revalidate();

        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(width, height));

        // Create Overlay UI Components //
        coverImagePane = new AImagePane();
        coverImagePane.setName(name);

        if (coverURL.equals("library_noGameFound.png")) {
            coverImagePane.setImage("library_noGameFound.png");
            System.out.println("No Game Found");
        }

        imgSelectedGlow = new AImagePane("game_selectedGlow.png", width + 10,
                height + 10);
        imgStarIcon = new AImagePane("game_favouriteIcon.png", 100, 32);
        imgStarIcon.setPreferredSize(new Dimension(100, 32));
        btnRemove = new AButton("game_btn_remove_norm.png",
                "game_btn_remove_down.png",
                "game_btn_remove_over.png");
        btnRemove.addActionListener(new RemoveButtonListener());

        topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setPreferredSize(new Dimension(width, 55));

        bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.setPreferredSize(new Dimension(width - 10,
                SIZE_BottomPaneHeight));

        // Set Up Bottom Bar Content
        // ----------------------------------------------------------------.
        // The Image Panel //
        imgOverlayBar = new AImagePane("game_overlay.png", width - 30, 55);
        imgOverlayBar.setOpaque(false);
        imgOverlayBar.setPreferredSize(new Dimension(width - 30, 55));
        imgOverlayBar.setLayout(new BorderLayout(0, 0));
        imgOverlayBar.setBackground(Color.blue);

        // The Panel that Contains the Actuall Components //
        pnlOverlayContainer = new JPanel();
        pnlOverlayContainer.setOpaque(false);
        pnlOverlayContainer.setBackground(Color.red);
        pnlOverlayContainer.setLayout(new BoxLayout(pnlOverlayContainer,
                BoxLayout.X_AXIS));

        // Favourite Buttom //
        btnFavorite = new AButton("game_btn_star_norm.png",
                "game_btn_star_down.png",
                "game_btn_star_over.png");
        btnFavorite.addActionListener(new Game.FavoriteButtonListener());
        if (main.LAUNCHES < 5) {
            btnFavorite.setToolTipText("Favorite");
        }

        pnlFavoritePane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlFavoritePane.setPreferredSize(new Dimension(30, 40));
        pnlFavoritePane.add(btnFavorite);
        pnlFavoritePane.setOpaque(false);

        // Flip Button //
        btnFlip = new AButton("game_btn_reverseRight_norm.png",
                "game_btn_reverseRight_down.png",
                "game_btn_reverseRight_over.png");
        btnFlip.addActionListener(new Game.FlipButtonListener());
        if (main.LAUNCHES < 5) {
            btnFlip.setToolTipText("Flip");
        }

        pnlFlipPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlFlipPane.setPreferredSize(new Dimension(80, 40));
        pnlFlipPane.add(btnFlip);
        pnlFlipPane.setOpaque(false);

        // Play Game Button //
        btnPlay = new AButton("game_btn_play_norm.png",
                "game_btn_play_down.png",
                "game_btn_play_over.png");
        playButtonListener = new Game.PlayButtonListener();
        btnPlay.addActionListener(playButtonListener);
        if (main.LAUNCHES < 5) {
            btnPlay.setToolTipText("Play");
        }
        btnPlay.setPreferredSize(new Dimension(40, 40));

        // Custom Cover overlay button
        btnAddCustomOverlay = new AButton(
                "editUI_editGameOverlay_norm.png",
                "editUI_editGameOverlay_down.png",
                "editUI_editGameOverlay_norm.png");

        // Reverse Buttons
        // ----------------------------------------------------------------.
        // Awards Button //
        btnAward = new AButton("game_btn_award_norm.png",
                "game_btn_award_down.png",
                "game_btn_award_over.png");
        if (main.LAUNCHES < 5) {
            btnAward.setToolTipText("Unavailable");
        }

        pnlAwardPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlAwardPane.setPreferredSize(new Dimension(30, 40));
        pnlAwardPane.add(btnAward);
        pnlAwardPane.setOpaque(false);

        // Settings Button
        btnSetting = new AButton("game_btn_setting_norm.png",
                "game_btn_setting_down.png",
                "game_btn_setting_over.png");
        btnSetting.setPreferredSize(new Dimension(40, 40));
        if (main.LAUNCHES < 5) {
            btnSetting.setToolTipText("Settings");
        }

        // Add Buttons to the Containers //
        pnlOverlayContainer.add(pnlFavoritePane);
        pnlOverlayContainer.add(btnPlay);
        pnlOverlayContainer.add(pnlFlipPane);

        // Add Container to the Image, which is not visible by default //
        imgOverlayBar.setVisible(false);
        imgOverlayBar.add(pnlOverlayContainer, BorderLayout.CENTER);

        // Add Image to the Bottom Bar //
        bottomPanel.add(imgOverlayBar, BorderLayout.NORTH);

        // Set Up Top Bar Content
        // ----------------------------------------------------------------.
        btnRemove.setVisible(false);
        imgStarIcon.setVisible(false);

        topPanel.add(btnRemove, BorderLayout.EAST);
        topPanel.add(imgStarIcon, BorderLayout.WEST);
        topPanel.validate();

        imgOverlayBar.validate();

        pnlInteractivePane.add(topPanel, BorderLayout.PAGE_START);
        pnlInteractivePane.add(bottomPanel, BorderLayout.SOUTH);

        bottomPanel.validate();
        topPanel.validate();
        pnlInteractivePane.validate();

        //Loading Thread
        gameCoverThread = null;

        if (gameCoverThread == null) {
            gameCoverThread = new Thread(this);
        }
        gameCoverThread.setName("Game Cover Thread");

        //Start Loader
        gameCoverThread.start();

        pnlFavoritePane.revalidate();

        if (logger.isDebugEnabled()) {
            logger.debug("pane width " + width);
        }

    }

    @Override
    public final void run() {

        if (Thread.currentThread() == gameCoverThread) {

            if (!java.util.Arrays.asList(this.getComponents())
                    .contains(progressWheel)) {
                progressWheel = new AProgressWheel("Aurora_Loader.png");
                progressWheel.setPreferredSize(this.getPreferredSize());
                this.add(progressWheel, BorderLayout.NORTH);

            }

            AFileManager fileIO = dashboardUI.getStartUI().getFileIO();
            localGameRootPath = fileIO.getPath() + "Game Data//";

            // Try to Get Image Locally //
            Boolean loadedImage = true;
            try {
                localImage = fileIO.findImg("Game Data",
                        coverURL);
            } catch (Exception ex) {
                loadedImage = false;
            }
            if (localImage != null && loadedImage) {

                coverImagePane.setImage(localImage,
                        width, height);
                coverImagePane.setDoubleBuffered(true);

            } else if (coverImagePane.getImgIcon() == null) {

                // Load Image From S3 //
                try {

                    // Prevent too many games loading at once
                    int rand = (int) (Math.random() * 100) + (200 - 100);
                    try {
                        Thread.sleep(rand);
                    } catch (InterruptedException ex) {
                        java.util.logging.Logger.getLogger(Game.class.getName())
                                .log(Level.SEVERE, null, ex);
                    }

                    if (WelcomeLogic.checkOnline(rootCoverDBPath + coverURL)) {
                        if (logger.isDebugEnabled()) {
                            logger.debug(coverURL);
                        }

                        coverImagePane.setURL(rootCoverDBPath + coverURL);

                        //Set Background accordingly
                        if (coverImagePane.getImgIcon().getIconHeight() == -1) {

                            coverImagePane.setImage("library_noGameFound.png");

                        } else {
                            //Save image locally
                            fileIO.writeImage(
                                    coverImagePane, coverURL, "Game Data");

                        }
                    } else if (coverImagePane.checkImageExists(coverURL)) {

                        coverImagePane.setImageURL(coverURL);

                    } else {

                        coverImagePane.setImage("library_noGameFound.png");

                    }

                    //Set Background accordingly
                    coverImagePane.setImageSize(width, height);
                    coverImagePane.setPreferredSize(new Dimension(width,
                            height));

                } catch (MalformedURLException ex) {
                    logger.error(ex);
                }
            }

            coverImagePane.setImageSize(width, height);
            coverImagePane.setPreferredSize(new Dimension(width,
                    height));

            this.setImage(coverImagePane);
            this.add(pnlInteractivePane);
            this.revalidate();
            this.repaint();
        }
        //End of Loading
        pnlInteractivePane.setPreferredSize(new Dimension(width, height));
        pnlInteractivePane.setSize(new Dimension(width, height));

        isLoaded = true;

        //Finalize
        afterLoad();

    }

    /**
     * .-----------------------------------------------------------------------.
     * | afterLoad();
     * .-----------------------------------------------------------------------.
     * |
     * | A method that is called after the Thread has completed its load.
     * | The method checks for what state it needs to be in terms of
     * | Being Selected, and Being Favorite.
     * |
     * .........................................................................
     * <p/>
     *
     */
    private void afterLoad() {
        AThreadWorker afterLoad = new AThreadWorker(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (isLoaded) {

                    if (isSelected) {
                        setSelected();
                    }

                    if (isFavorite) {
                        setFavorite();
                    }

                }

                try {
                    if (localImage == null) {
                        Thread.sleep(100);
                    }
                    if (progressWheel != null) {
                        progressWheel.stop();
                        remove(progressWheel);
                    }

                } catch (InterruptedException ex) {
                    java.util.logging.Logger.getLogger(Game.class.getName()).
                            log(Level.SEVERE, null, ex);
                }

                revalidate();
                repaint();

            }

        });

        afterLoad.startOnce();

    }

    /**
     * .-----------------------------------------------------------------------.
     * | reAddInteractive();
     * .-----------------------------------------------------------------------.
     * |
     * | This method is called when we want to re-add the
     * | Overlay UI on the Game Covers
     * |
     * .........................................................................
     * <p/>
     *
     */
    public final void reAddInteractive() {

        isRemoved = false;
        setSize();
        pnlInteractivePane.setVisible(true);

        // Sizes //
        imgSelectedGlow.setImageSize(width + 10,
                height + 10);
        imgOverlayBar.setImageSize(width - 30, 55);
        imgOverlayBar.setPreferredSize(new Dimension(width - 30, 55));
        topPanel.setPreferredSize(new Dimension(width, 55));

        bottomPanel.setPreferredSize(new Dimension(width - 10,
                SIZE_BottomPaneHeight));
        pnlAwardPane.setPreferredSize(new Dimension(30, 40));
        pnlFlipPane.setPreferredSize(new Dimension(80, 40));
        btnSetting.setPreferredSize(new Dimension(40, 40));

        // Remove all and re-add //
        pnlOverlayContainer.removeAll();
        pnlOverlayContainer.add(pnlFavoritePane);
        pnlOverlayContainer.add(btnPlay);
        pnlOverlayContainer.add(pnlFlipPane);
        pnlOverlayContainer.validate();

        imgOverlayBar.removeAll();
        imgOverlayBar.setVisible(false);
        imgOverlayBar.add(pnlOverlayContainer);
        imgOverlayBar.setOpaque(false);
        imgOverlayBar.validate();

        topPanel.removeAll();
        topPanel.add(btnRemove, BorderLayout.EAST);
        topPanel.add(imgStarIcon, BorderLayout.WEST);
        topPanel.validate();

        bottomPanel.removeAll();
        bottomPanel.add(imgOverlayBar, BorderLayout.NORTH);
        bottomPanel.validate();

        pnlInteractivePane.removeAll();
        pnlInteractivePane.add(topPanel, BorderLayout.PAGE_START);
        pnlInteractivePane.add(bottomPanel, BorderLayout.SOUTH);
        pnlInteractivePane.revalidate();

        // load selected and star
        afterLoad();

        this.repaint();
    }

    /**
     * .-----------------------------------------------------------------------.
     * | enableEditCoverOverlay();
     * .-----------------------------------------------------------------------.
     * |
     * | Enables the ability to add a custom cover art image to game by clicking
     * | on the game.
     * |
     * .........................................................................
     * <p/>
     *
     */
    public final void enableEditCoverOverlay() {

        this.removeAll();
        this.revalidate();
        if (isRemoved) {
            addOverlayUI();
        }

        if (pnlInteractivePane.getMouseListeners().length > 0) {
            pnlInteractivePane.removeMouseListener(
                    pnlInteractivePane.getMouseListeners()[0]);
        }

        if (this.getMouseListeners().length > 0) {
            this.removeMouseListener(this.getMouseListeners()[0]);
        }

        pnlInteractivePane.removeAll();
        pnlInteractivePane.setVisible(true);
        pnlInteractivePane
                .setPreferredSize(new Dimension(width, height));
        pnlInteractivePane.setLayout(new BorderLayout());
        pnlInteractivePane.revalidate();
        btnAddCustomOverlay.setButtonSize(width, height);
        btnAddCustomOverlay.setVisible(false);
        btnAddCustomOverlay.setMargin(new Insets(0, 0, 0, 0));

        pnlInteractivePane.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                btnAddCustomOverlay.setVisible(true);
            }

        });

        btnAddCustomOverlay.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseExited(MouseEvent e) {
                btnAddCustomOverlay.setVisible(false);
            }
        });

        pnlInteractivePane.add(btnAddCustomOverlay);
    }

    /**
     * .-----------------------------------------------------------------------.
     * | disableEditCoverOverlay();
     * .-----------------------------------------------------------------------.
     * |
     * | disables the ability to add a custom cover art image to game by
     * clicking
     * | on the game.
     * |
     * .........................................................................
     * <p/>
     *
     */
    public final void disableEditCoverOverlay() {

        if (isRemoved) {
            addOverlayUI();
        }

        pnlInteractivePane.addMouseListener(new Game.InteractiveListener());
        this.addMouseListener(new Game.InteractiveListener());
    }

    /**
     * .-----------------------------------------------------------------------.
     * | addTime(int minDiff, int hoursDiff)
     * .-----------------------------------------------------------------------.
     * |
     * | This method is the only way to increase the timePlayed value,
     * | you have to give the number of minutes and the number of hours.
     * | The method will accept 0 as a value and calculates everything in method
     * |
     * .........................................................................
     * <p/>
     * @param minDiff   Integer
     * @param hoursDiff Integer
     */
    public final void addTime(final int minDiff, final int hoursDiff) {

        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date d = null;
        if (timePlayed == null || timePlayed.equals("null")) {
            timePlayed = "00:00";
        }
        try {
            d = df.parse(timePlayed);
        } catch (ParseException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.MINUTE, minDiff);
        cal.add(Calendar.HOUR, hoursDiff);

        timePlayed = df.format(cal.getTime());

    }

    /**
     * .-----------------------------------------------------------------------.
     * | unSelectPrevious()
     * .-----------------------------------------------------------------------.
     * |
     * | Uses the GridManager to un-select any previously setSelected game
     * |
     * .........................................................................
     * <p/>
     */
    public final void unSelectPrevious() {
        if (manager != null) {
            manager.unselectPrevious();
        }

    }

    /**
     * .-----------------------------------------------------------------------.
     * | setCoverSize(int width, int height)
     * .-----------------------------------------------------------------------.
     * |
     * | Method that sets the Size of the Game Cover
     * |
     * .........................................................................
     * <p/>
     * @param coverWidth  Integer
     * @param coverHeight Integer
     */
    public final void setCoverSize(final int coverWidth, final int coverHeight) {
        this.width = coverWidth;
        this.height = coverHeight;
        this.setImageSize(width, height);
        setSize();
    }

    /**
     * .-----------------------------------------------------------------------.
     * | setSelected()
     * .-----------------------------------------------------------------------.
     * |
     * | Method that selects the Game by showing the glow
     * |
     * .........................................................................
     * <p/>
     */
    public final void setSelected() {
        isSelected = true;
        if (isLoaded) {
            this.add(imgSelectedGlow);
            this.repaint();
            this.validate();
        }

    }

    /**
     * .-----------------------------------------------------------------------.
     * | setUnselected()
     * .-----------------------------------------------------------------------.
     * |
     * | Method to de-select a game cover to show the overlay UI and glow
     * |
     * .........................................................................
     * <p/>
     */
    public final void setUnselected() {

        if (isSelected) {

            if (isGameRemoveMode) {

                new CancelRemoveGameHandler().actionPerformed(null);
                imgOverlayBar.setVisible(false);
            }

            isSelected = false;
            btnRemove.setVisible(false);
            pnlInteractivePane.revalidate();
            this.remove(imgSelectedGlow);
            this.repaint();
            this.revalidate();

        }

    }

    /**
     * .-----------------------------------------------------------------------.
     * | hideOverlayUI()
     * .-----------------------------------------------------------------------.
     * |
     * | hides the Overlay UI that shows up when you click on a game
     * | but does not hid the glow or star
     * |
     * .........................................................................
     * <p/>
     */
    public final void hideOverlayUI() {
        if (isLoaded) {
            btnRemove.setVisible(false);
            pnlInteractivePane.revalidate();
        }
        imgOverlayBar.setVisible(false);
        setUnselected();
    }

    /**
     * .-----------------------------------------------------------------------.
     * | showOverlayUI()
     * .-----------------------------------------------------------------------.
     * |
     * | shows the overlay/Interactive pane with the remove button and selects
     * | game.
     * |
     * .........................................................................
     * <p/>
     */
    public final void showOverlayUI() {

        pnlInteractivePane.setSize(width + 47, height + 28);

        if (logger.isDebugEnabled()) {
            logger.debug("INTERACTIVE SIZE " + pnlInteractivePane.getWidth()
                         + " " + pnlInteractivePane.getHeight());
        }

        showRemoveBtn();
        imgOverlayBar.setVisible(true);
        setSelected();

        LibraryUI.lblLibraryStatus.setFont(LibraryUI.lblLibraryStatus
                .getFont()
                .deriveFont(Font.PLAIN, LibraryUI.gameNameFontSize));
        LibraryUI.lblLibraryStatus.setForeground(Color.lightGray);
        LibraryUI.lblLibraryStatus.setText(getName());

    }

    /**
     * .-----------------------------------------------------------------------.
     * | removeOverlayUI()
     * .-----------------------------------------------------------------------.
     * |
     * | Completely removes the Overlay UI from game cover. This is used if
     * | the game cover is only for displaying the game icon
     * |
     * .........................................................................
     * <p/>
     */
    public final void removeOverlayUI() {
        this.remove(pnlInteractivePane);
        this.isRemoved = true;
    }

    public final void addOverlayUI() {
        this.add(pnlInteractivePane);
        this.isRemoved = false;
    }

    public final void setFavorite() {

        isFavorite = true;
        if (isLoaded) {
            imgStarIcon.setVisible(true);
            pnlInteractivePane.revalidate();
        }

    }

    public final void setUnFavorite() {
        if (isFavorite) {
            isFavorite = false;
            imgStarIcon.setVisible(false);
            pnlInteractivePane.revalidate();
        }
    }

    /**
     * .-----------------------------------------------------------------------.
     * | animateFavouriteMove()
     * .-----------------------------------------------------------------------.
     * |
     * | Replaces the Game Cover and shows the Favorited Image cover for a
     * | second then re-shows the game cover.
     * |
     * | This is used as a visual cue to indicate that a game has been favorited
     * | and moved because of that.
     * |
     * .........................................................................
     * <p/>
     */
    private void animateFavouriteMove() {

        hideOverlayUI();
        revalidate();
        thisGame().setEnabled(false);
        Game temp = thisGame();

        AImagePane favouritedImg = new AImagePane("library_favourited_bg.png",
                width, height);

        thisGame().clearImage();
        thisGame().setImage(favouritedImg);

        try {
            LibraryUI.lblLibraryStatus.setForeground(Color.yellow);
            LibraryUI.lblLibraryStatus.setText("Favorited a Game");

            Thread.sleep(900);
            thisGame().repaint();
            thisGame().revalidate();

            LibraryUI.lblLibraryStatus.setForeground(Color.lightGray);
            LibraryUI.lblLibraryStatus.setText(getName());

        } catch (InterruptedException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        thisGame().setEnabled(true);
        thisGame().setVisible(false);

        thisGame().clearImage();
        thisGame().setImage(temp.getCoverImagePane().getImgIcon(),
                height, width);
        showOverlayUI();
        select();
    }

    /**
     * .-----------------------------------------------------------------------.
     * | animateUnFavouriteMove()
     * .-----------------------------------------------------------------------.
     * |
     * | Replaces the Game Cover and shows the Un-Favorited Image cover for a
     * | second then re-shows the game cover.
     * |
     * | This is used as a visual cue to indicate that a game has been
     * | un-favorited
     * | and moved because of that.
     * |
     * .........................................................................
     * <p/>
     */
    private void animateUnFavouriteMove() {

        hideOverlayUI();
        revalidate();
        thisGame().setEnabled(false);
        Game temp = thisGame();

        AImagePane favouritedImg = new AImagePane("library_unfavourited_bg.png",
                width, height);

        thisGame().clearImage();
        thisGame().setImage(favouritedImg);

        try {

            LibraryUI.lblLibraryStatus.setForeground(Color.yellow);
            LibraryUI.lblLibraryStatus.setText("Un-Favorited a Game");

            Thread.sleep(900);
            thisGame().repaint();
            thisGame().revalidate();

            LibraryUI.lblLibraryStatus.setForeground(Color.lightGray);
            LibraryUI.lblLibraryStatus.setText(getName());

        } catch (InterruptedException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        thisGame().setEnabled(true);
        thisGame().setVisible(false);

        thisGame().clearImage();
        thisGame().setImage(temp.getCoverImagePane().getImgIcon(),
                height, width);
        showOverlayUI();
        select();
    }

    public final void showRemoveBtn() {

        if (isLoaded) {
            btnRemove.setVisible(true);
            pnlInteractivePane.revalidate();
        }
    }

    /**
     * Pass the Library Logic Object to this game
     * <p/>
     * @param logic
     */
    public final void setLibraryLogic(LibraryLogic logic) {

        this.libraryLogic = logic;

    }

    public LibraryLogic getLibraryLogic() {
        return libraryLogic;
    }

    public void setSettingsListener(final ActionListener action) {
        if (btnSetting.getActionListeners().length == 0) {
            btnSetting.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    action.actionPerformed(new ActionEvent(thisGame(), 0, ""));
                }
            });
        }
    }

    /**
     * Saves the Metadata associated with this Game to the Stored Profile
     *
     */
    public final void saveMetadata() {
        storage.getStoredProfile().saveGameMetadata(this);

        if (storage.getStoredSettings().getSettingValue("organize")
                .equalsIgnoreCase("most played") && libraryLogic != null) {

            libraryLogic.addGamesToLibrary();
        }

    }

    private class EnterGameTypeListener implements ActionListener {

        public EnterGameTypeListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            txtGameType.getTextBox().setFocusable(false);
            txtGameType.getTextBox().setFocusable(true);

        }
    }

    private class GameTypeListener implements FocusListener {

        public GameTypeListener() {
        }

        @Override
        public void focusGained(FocusEvent e) {
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (txtGameType.getTextBox().getText().length() > 0) {
                setGameType(txtGameType.getText());
                saveMetadata();
            }
        }
    }

    public class FlipButtonListener implements ActionListener {

        private Game tempGame;

        public FlipButtonListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (logger.isDebugEnabled()) {
                logger.debug("Flip button pressed");
            }

            if (isLoaded) {

                AuroraStorage storage = dashboardUI.getStorage();
                String soundEffectsSetting = storage.getStoredSettings()
                        .getSettingValue("sound_effects");
                if (soundEffectsSetting == null) {
                    soundEffectsSetting = SettingsLogic.DEFAULT_SFX_SETTING;
                }

                if (!isFliped) { // Flip Game

                    // Sound FX

                    if (soundEffectsSetting.equals("enabled")) {
                        ASound flipSFX = new ASound("tick_3.wav", false);
                        flipSFX.Play();
                    }

                    // Replace Game Cover art with Fliped image //
                    tempGame = thisGame();

                    thisGame().clearImage();
                    thisGame().setImage("Reverse-Case.png", height, width);

                    btnFlip.setButtonStates("game_btn_reverseLeft_norm.png",
                            "game_btn_reverseLeft_down.png",
                            "game_btn_reverseLeft_over.png");

                    pnlOverlayContainer.removeAll();
                    pnlOverlayContainer.validate();

                    pnlOverlayContainer.add(pnlAwardPane);
                    pnlOverlayContainer.add(btnSetting);
                    pnlOverlayContainer.add(pnlFlipPane);
                    pnlOverlayContainer.revalidate();

                    if (isFlipUIReady) {
                        flipGame();
                    } else {
                        setUpFlipedUI();
                    }

                    thisGame().revalidate();
                    isFliped = true;

                } else { // Un-flip game

                    if (soundEffectsSetting.equals("enabled")) {
                        ASound flipSFX = new ASound("tick_4.wav", false);
                        flipSFX.Play();
                    }

                    // replace with
                    thisGame().clearImage();
                    thisGame().setImage(tempGame.getCoverImagePane()
                            .getImgIcon(),
                            height, width);
                    btnFlip.setButtonStates("game_btn_reverseRight_norm.png",
                            "game_btn_reverseRight_down.png",
                            "game_btn_reverseRight_over.png");

                    // reset to normal overlay UI //
                    reAddInteractive();
                    showOverlayUI();

                    thisGame().revalidate();
                    isFliped = false;
                }
            }
        }
    }
    private int textBoxWidth;

    private int textBoxHeight;

    /**
     * .-----------------------------------------------------------------------.
     * | setUpFlipedUI()
     * .-----------------------------------------------------------------------.
     * |
     * | Sets up the UI that is shown when the Flip button on the game is
     * | selected.
     * |
     * .........................................................................
     * <p/>
     */
    private void setUpFlipedUI() {

        // Create main Panels
        // ----------------------------------------------------------------.
        // Shortcut Pane //
        pnlShortcutImage = new AImagePane("game_flip_shortcutsBG.png",
                flipShortcutWidth,
                flipShortcutHeight, new BorderLayout());
        pnlShortcutImage.setPreferredSize(
                new Dimension(flipShortcutWidth, flipShortcutHeight));
        pnlShortcutImage.setBorder(BorderFactory.createEmptyBorder(0, 5,
                2, 5));

        lblShortcut = new ASlickLabel(" Shortcut");
        lblShortcut.setFont(this.coreUI.getRopaFont().deriveFont(Font.PLAIN,
                11));
        lblShortcut.setForeground(new Color(102, 102, 102));

        pnlShortcutLbl = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        pnlShortcutLbl.setOpaque(false);
        pnlShortcutLbl.setPreferredSize(new Dimension(width - width / 4, 10));

        pnlShortcutBtn = new JPanel(new FlowLayout(FlowLayout.LEFT, -17, 0));
        pnlShortcutBtn.setPreferredSize(new Dimension(width - width / 4, 60));
        pnlShortcutBtn.setOpaque(false);

        int btnWidth = flipShortcutWidth / 4 + 2;
        int btnHeight = btnWidth - 2;

        btnWatch = new AButton("game_btn_watch_norm.png",
                "game_btn_watch_down.png",
                "game_btn_watch_over.png", btnWidth, btnHeight);
        btnWatch.addActionListener(new WatchListener());
        if (main.LAUNCHES < 5) {
            btnWatch.setToolTipText("Gameplay Videos");
        }

        btnFix = new AButton("game_btn_help_norm.png",
                "game_btn_help_down.png",
                "game_btn_help_over.png", btnWidth, btnHeight);
        btnFix.addActionListener(new FixListener());
        if (main.LAUNCHES < 5) {
            btnFix.setToolTipText("PC Gaming Wiki");
        }

        btnLearn = new AButton("game_btn_learn_norm.png",
                "game_btn_learn_down.png",
                "game_btn_learn_over.png", btnWidth, btnHeight);
        btnLearn.addActionListener(new LearnListener());
        if (main.LAUNCHES < 5) {
            btnLearn.setToolTipText("Wikia");
        }

        // Content Pane //
        pnlFlipContentPane = new JPanel(new BorderLayout(0, 0));
        pnlFlipContentPane.setOpaque(false);
        pnlFlipContentPane.setBorder(BorderFactory.createEmptyBorder(5, 40,
                5, 0));
        pnlFlipContentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        pnlFlipContentPane.addMouseListener(new InteractiveListener());

        // Right Pane //
        pnlRightPane = new JPanel();
        pnlRightPane.setLayout(new BoxLayout(pnlRightPane, BoxLayout.Y_AXIS));
        pnlRightPane.setLayout(new GridLayout(4, 0, 0, 13));
        pnlRightPane.setOpaque(false);

        // Scroll Bar and Scroll Panes
        // ----------------------------------------------------------------.
        flipScrollBar = new JScrollBar();
        flipScrollBar.setUnitIncrement(20);
        flipScrollBar.setUI(new AScrollBar("app_scrollBar.png",
                "game_scrollBarBG.png"));
        flipScrollBar.setPreferredSize(new Dimension(6, flipScrollBar
                .getPreferredSize().height));

        pnlFlipScrollPane = new JScrollPane(pnlFlipContentPane,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        pnlFlipScrollPane.setOpaque(false);
        pnlFlipScrollPane.getViewport().setOpaque(false);
        pnlFlipScrollPane.setVerticalScrollBar(flipScrollBar);
        pnlFlipScrollPane.setBorder(null);
        pnlFlipScrollPane.validate();

        // Labels and Textboxes
        // ----------------------------------------------------------------.
        // Lables //
        lblHoursPlayed = new ASlickLabel("Hours Played");
        lblHoursPlayed.setFont(this.coreUI.getRopaFont().deriveFont(Font.PLAIN,
                labelFontSize
                - 2));
        lblHoursPlayed.setForeground(new Color(202, 202, 217));

        lblLastPlayed = new ASlickLabel("Last Played");
        lblLastPlayed.setFont(this.coreUI.getRopaFont().deriveFont(Font.PLAIN,
                labelFontSize
                - 2));
        lblLastPlayed.setForeground(new Color(202, 202, 217));

        lblTimesPlayed = new ASlickLabel("Times Played");
        lblTimesPlayed.setFont(this.coreUI.getRopaFont().deriveFont(Font.PLAIN,
                labelFontSize
                - 2));
        lblTimesPlayed.setForeground(new Color(202, 202, 217));

        lblGameType = new ASlickLabel("Game Type");
        lblGameType.setFont(this.coreUI.getRopaFont().deriveFont(Font.PLAIN,
                labelFontSize
                - 2));
        lblGameType.setForeground(new Color(202, 202, 217));

        // Text boxes //
        if (coreUI.getLargeScreen()) {
            textBoxWidth = width / 2 + 22 + flipPadding;
        } else {
            textBoxWidth = width / 2 + 22;
        }
        textBoxHeight = height / 12;

        txtHoursPlayed = new ATextField("game_textLabel_inactive.png",
                "game_textLabel_active.png");
        txtHoursPlayed.setTextboxSize(textBoxWidth, textBoxHeight);
        txtHoursPlayed.getTextBox().setFont(this.coreUI.getRopaFont()
                .deriveFont(Font.PLAIN,
                        labelFontSize));
        txtHoursPlayed.getTextBox().setDisabledTextColor(new Color(0, 255, 0));

        txtTimesPlayed = new ATextField("game_textLabel_inactive.png",
                "game_textLabel_active.png");
        txtTimesPlayed.setTextboxSize(textBoxWidth, textBoxHeight);
        txtTimesPlayed.getTextBox().setFont(this.coreUI.getRopaFont()
                .deriveFont(Font.PLAIN,
                        labelFontSize));
        txtTimesPlayed.getTextBox().setDisabledTextColor(new Color(0, 255, 0));

        txtLastPlayed = new ATextField("game_textLabel_inactive.png",
                "game_textLabel_active.png");
        txtLastPlayed.setTextboxSize(textBoxWidth, textBoxHeight);
        txtLastPlayed.getTextBox().setFont(this.coreUI.getRopaFont()
                .deriveFont(Font.PLAIN,
                        labelFontSize));
        txtLastPlayed.getTextBox().setDisabledTextColor(new Color(0, 255, 0));

        txtGameType = new ATextField("game_textLabel_inactive.png",
                "game_textLabel_active.png");
        txtGameType.setTextboxSize(textBoxWidth, textBoxHeight);
        txtGameType.getTextBox().setFont(this.coreUI.getRopaFont()
                .deriveFont(Font.PLAIN,
                        labelFontSize));
        txtGameType.getTextBox().setCaretColor(Color.CYAN);
        txtGameType.getTextBox().setForeground(new Color(0, 255, 0));

        txtGameType.getTextBox().addFocusListener(new GameTypeListener());
        txtGameType.getTextBox().addActionListener(new EnterGameTypeListener());

        // Add To Panels
        // ----------------------------------------------------------------.
        // Add to Right Panel //
        lblHoursPlayed.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        txtHoursPlayed.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        JPanel pnlHoursPlayedLbl = new JPanel();
        pnlHoursPlayedLbl.setOpaque(false);
        pnlHoursPlayedLbl.setLayout(new BoxLayout(pnlHoursPlayedLbl,
                BoxLayout.Y_AXIS));
        pnlHoursPlayedLbl.add(lblHoursPlayed);
        pnlHoursPlayedLbl.add(txtHoursPlayed);
        pnlRightPane.add(pnlHoursPlayedLbl);

        lblTimesPlayed.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        txtTimesPlayed.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        JPanel pnlTimesPlayedLbl = new JPanel();
        pnlTimesPlayedLbl.setLayout(new BoxLayout(pnlTimesPlayedLbl,
                BoxLayout.Y_AXIS));
        pnlTimesPlayedLbl.setOpaque(false);
        pnlTimesPlayedLbl.add(lblTimesPlayed);
        pnlTimesPlayedLbl.add(txtTimesPlayed);
        pnlRightPane.add(pnlTimesPlayedLbl);

        lblLastPlayed.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        txtLastPlayed.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        JPanel pnlLastPlayedLbl = new JPanel();
        pnlLastPlayedLbl.setLayout(new BoxLayout(pnlLastPlayedLbl,
                BoxLayout.Y_AXIS));
        pnlLastPlayedLbl.setOpaque(false);
        pnlLastPlayedLbl.add(lblLastPlayed);
        pnlLastPlayedLbl.add(txtLastPlayed);
        pnlRightPane.add(pnlLastPlayedLbl);

        lblGameType.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        txtGameType.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        JPanel pnlGameTypeLbl = new JPanel();
        pnlGameTypeLbl.setLayout(new BoxLayout(pnlGameTypeLbl,
                BoxLayout.Y_AXIS));
        pnlGameTypeLbl.setOpaque(false);
        pnlGameTypeLbl.add(lblGameType);
        pnlGameTypeLbl.add(txtGameType);
        pnlRightPane.add(pnlGameTypeLbl);

        // Add to Content Pane //
        pnlFlipContentPane.add(Box.createHorizontalStrut(20));
        pnlFlipContentPane.add(pnlRightPane);

        // Add scroll pane to container //
        pnlFlipContainer = new JPanel();
        pnlFlipContainer.setLayout(new BorderLayout());
        pnlFlipContainer.setOpaque(false);
        pnlFlipContainer.setPreferredSize(pnlFlipContentPane.getPreferredSize());
        pnlFlipContainer.add(pnlFlipScrollPane, BorderLayout.CENTER);
        pnlFlipContainer.add(Box.createHorizontalStrut(width / 3 - flipPadding),
                BorderLayout.EAST);

        // Add Shortcut buttons to panel //
        pnlShortcutBtn.add(btnWatch);
        pnlShortcutBtn.add(btnFix);
        pnlShortcutBtn.add(btnLearn);
        pnlShortcutImage.add(pnlShortcutBtn, BorderLayout.WEST);

        // Set up Shortcut panel //
        pnlShortcutLbl.add(lblShortcut);
        pnlShortcutImage.add(pnlShortcutLbl, BorderLayout.SOUTH);

        pnlTopImageContainer = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 0, 0));
        pnlTopImageContainer.setBorder(BorderFactory.createEmptyBorder(20, 0,
                0, (width
                    / 5)));
        pnlTopImageContainer.setPreferredSize(new Dimension(flipShortcutWidth,
                flipShortcutHeight
                + 25));
        pnlTopImageContainer.setOpaque(false);

        pnlTopImageContainer.add(pnlShortcutImage, BorderLayout.CENTER);

        isFlipUIReady = true;
        flipGame();

    }

    /**
     * .-----------------------------------------------------------------------.
     * | WatchListener
     * .-----------------------------------------------------------------------.
     * |
     * | Listener for the Watch shortcut button to link to the Youtube search
     * | results for the game
     * |
     * .........................................................................
     * <p/>
     */
    private class WatchListener implements ActionListener {

        public WatchListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            String url = "http://www.youtube.com/results?search_query=";
            String gameName = getName().replace(" ", "+").replace("'", "");;
            url += gameName;

            try {
                try {
                    Desktop.getDesktop().browse(new URI(url));
                } catch (URISyntaxException ex) {
                    java.util.logging.Logger.getLogger(Game.class.getName()).
                            log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(Game.class.getName()).
                        log(Level.SEVERE, null, ex);
            }

        }
    }

    /**
     * .-----------------------------------------------------------------------.
     * | FixListener
     * .-----------------------------------------------------------------------.
     * |
     * | Listener for the Watch shortcut button to link to the PCgamingWiki
     * | search results for the game
     * |
     * .........................................................................
     * <p/>
     */
    private class FixListener implements ActionListener {

        public FixListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            String url = "http://pcgamingwiki.com/wiki/";
            String gameName = getName().replace(" ", "_").replace("'", "");
            url += gameName;

            try {
                try {
                    Desktop.getDesktop().browse(new URI(url));
                } catch (URISyntaxException ex) {
                    java.util.logging.Logger.getLogger(Game.class.getName()).
                            log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(Game.class.getName()).
                        log(Level.SEVERE, null, ex);
            }

        }
    }

    /**
     * .-----------------------------------------------------------------------.
     * | LearnListener
     * .-----------------------------------------------------------------------.
     * |
     * | Listener for the Watch shortcut button to link to the Wikia
     * | search results for the game
     * |
     * .........................................................................
     * <p/>
     */
    private class LearnListener implements ActionListener {

        public LearnListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            String url = "http://www.google.com/search?q=";
            String gameName = getName().trim().replace(" ", "+");
            url = url + gameName + "+wikia&btnI";

            try {
                try {
                    Desktop.getDesktop().browse(new URI(url));
                } catch (URISyntaxException ex) {
                    java.util.logging.Logger.getLogger(Game.class.getName()).
                            log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(Game.class.getName()).
                        log(Level.SEVERE, null, ex);
            }

        }
    }

    /**
     * .-----------------------------------------------------------------------.
     * | showFlipUIContent()
     * .-----------------------------------------------------------------------.
     * |
     * | Shows the flip UI which has been loaded using the setUpFlipedUI()
     * |
     * .........................................................................
     * <p/>
     */
    public void flipGame() {

        topPanel.removeAll();
        topPanel.revalidate();
        topPanel.setPreferredSize(pnlTopImageContainer.getPreferredSize());
        topPanel.add(pnlTopImageContainer, BorderLayout.CENTER);
        topPanel.revalidate();

        pnlInteractivePane.add(pnlFlipContainer, BorderLayout.CENTER, 1);
        pnlInteractivePane.revalidate();

        // Hours Played
        // ----------------------------------------------------------------.
        if (this.timePlayed != null && !this.timePlayed.equals("null")) {

            // Parse time //
            String hoursPlayed = timePlayed
                    .substring(0, timePlayed.indexOf(":"));
            String minutesPlayed = timePlayed.substring(timePlayed.indexOf(':')
                                                        + 1,
                    timePlayed.length());

            if (!hoursPlayed.equals("0")) {
                hoursPlayed = hoursPlayed.replaceFirst("0", "");
            }
            if (!minutesPlayed.equals("0")) {
                minutesPlayed = minutesPlayed.replaceFirst("0", "");
            }

            // convert to ints, check for plurals
            int hours = Integer.parseInt(hoursPlayed);
            int mins = Integer.parseInt(minutesPlayed);

            String hourTxt = "hr";
            String minTxt = "min";

            if (hours > 1) {
                hourTxt = "hrs";
            }
            if (mins > 1) {
                minTxt = "mins";
            }

            // parse to textbox
            if ((minutesPlayed.equals("0")) && (hoursPlayed.equals("0"))) {

                txtHoursPlayed.setText("Under a min");

            } else if (minutesPlayed.equals("0")) {

                txtHoursPlayed.setText(hoursPlayed + hourTxt);

            } else if (hoursPlayed.equals("0")) {

                txtHoursPlayed.setText(minutesPlayed + minTxt);

            } else {

                txtHoursPlayed.setText(hoursPlayed + hourTxt + " "
                                       + minutesPlayed
                                       + minTxt);
            }
        } else {
            txtHoursPlayed.setText("None");
        }
        txtHoursPlayed.getTextBox().setEnabled(false);
        txtHoursPlayed.revalidate();

        // Occurences Played
        // ----------------------------------------------------------------.
        txtTimesPlayed.getTextBox().setEnabled(false);
        String occurence = Integer.toString(this.getOccurencesPlayed());
        if (occurence.equals("0")) {
            txtTimesPlayed.setText("None");
        } else {
            if (Integer.parseInt(occurence) > 1) {
                txtTimesPlayed.setText(occurence + " Times");
            } else {
                txtTimesPlayed.setText(occurence + " Time");
            }
        }
        txtTimesPlayed.revalidate();

        // Last Time Played
        // ----------------------------------------------------------------.
        txtLastPlayed.getTextBox().setEnabled(false);

        // Calculate days past //
        SimpleDateFormat format = new SimpleDateFormat(ATimeLabel.DATE);
        Date past = null;

        String daysPast;
        if (this.lastPlayed != null && !this.lastPlayed.equals("null")) {
            try {
                past = format.parse(lastPlayed);
            } catch (ParseException ex) {
                java.util.logging.Logger.getLogger(Game.class.getName()).
                        log(Level.SEVERE, null, ex);
            }
            Date now = new Date();

            daysPast = Long.toString(TimeUnit.MILLISECONDS.toDays(now
                    .getTime() - past.getTime()));
            if (daysPast.equals("0")) {

                txtLastPlayed.setText("Today");

            } else {

                if (Integer.parseInt(daysPast) > 30) {
                    txtLastPlayed.setText("Over a month ago");
                } else if (Integer.parseInt(daysPast) > 1) {
                    txtLastPlayed.setText(daysPast + " days ago");
                } else {
                    txtLastPlayed.setText("Yesterday");
                }

            }
        } else {

            txtLastPlayed.setText("Not Played");
        }
        txtLastPlayed.revalidate();

        // Game Type
        // ----------------------------------------------------------------.
        if (this.getGameType() != null && !this.getGameType().equals("null")) {
            txtGameType.setText(this.getGameType());
        }

    }
    boolean isFavoriting;

    boolean isUnfavoriting;

    boolean prevState;

    class FavoriteButtonListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            if (logger.isDebugEnabled()) {
                logger.debug("Favourite button pressed");
            }

            AThreadWorker favWorker = new AThreadWorker(
                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            if (storage.getStoredSettings().getSettingValue(
                                    "organize")
                            .equalsIgnoreCase("Favorite")) {

                                if (isFavorite) {
                                    // Unfavoriting

                                    if (isFavoriting == false) {
                                        prevState = isFavorite;
                                    }

                                    isUnfavoriting = true;

                                    setUnFavorite();

                                    // Give time to change decision
                                    try {
                                        Thread.sleep(850);
                                    } catch (InterruptedException ex) {
                                        java.util.logging.Logger.getLogger(
                                                Game.class
                                                .getName()).
                                        log(Level.SEVERE, null, ex);
                                    }

                                    // Check if still favourited
                                    if (!isFavorite && !isFavoriting
                                        && prevState
                                           != isFavorite) {
                                        storage.getStoredLibrary().SaveFavState(
                                                thisGame());
                                        animateUnFavouriteMove();
                                        manager.moveUnfavorite(Game.this);
                                        thisGame().setVisible(true);

                                    }
                                    isUnfavoriting = false;

                                } else {
                                    //Favoriting

                                    if (isUnfavoriting == false) {
                                        prevState = isFavorite;
                                    }

                                    isFavoriting = true;
                                    setFavorite();

                                    // Give time to change decision
                                    try {
                                        Thread.sleep(850);
                                    } catch (InterruptedException ex) {
                                        java.util.logging.Logger.getLogger(
                                                Game.class
                                                .getName()).
                                        log(Level.SEVERE, null, ex);
                                    }

                                    // Check if still favourited
                                    if (isFavorite && !isUnfavoriting
                                        && prevState
                                           != isFavorite) {
                                        storage.getStoredLibrary().SaveFavState(
                                                thisGame());
                                        setUnFavorite();
                                        animateFavouriteMove();
                                        setFavorite();
                                        manager.moveFavorite(Game.this);
                                        thisGame().setVisible(true);

                                    }
                                    isFavoriting = false;

                                }
                            } else {
                                if (isFavorite) {

                                    // Unfavoriting
                                    setUnFavorite();

                                } else {

                                    //Favoriting
                                    setFavorite();

                                }

                            }

                        }
                    });

            favWorker.startOnce();

        }
    }

    class PlayButtonListener implements ActionListener {

        private AuroraLauncher launcher;

        @Override
        public void actionPerformed(final ActionEvent e) {

            if (logger.isDebugEnabled()) {
                logger.debug("Play button pressed");
            }

            if (AFileManager.checkFile(getGamePath())) {

                LibraryUI.lblLibraryStatus.setForeground(Color.green);
                LibraryUI.lblLibraryStatus.setText("Launching Game");

                launcher = new AuroraLauncher(coreUI);
                launcher.launchGame(thisGame());

            } else {

                final ADialog info = new ADialog(ADialog.aDIALOG_WARNING,
                        "Can't Find Game. Would You Like To REMOVE It From Library?      ",
                        coreUI.getRegularFont()
                        .deriveFont(Font.BOLD, 23));
                info.setOKButtonListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        new RemoveGameHandler().actionPerformed(e);
                        info.setVisible(false);
                    }
                });
                info.showDialog();
                info.setVisible(true);

            }

            LibraryUI.lblLibraryStatus.setForeground(Color.lightGray);
            LibraryUI.lblLibraryStatus.setText(getName());

        }
    }

    class RemoveButtonListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            topPanel.remove(btnRemove);
            imgConfirmPromptImagePane = new AImagePane(
                    "game_img_removeWarning.png");
            imgConfirmPromptImagePane
                    .setPreferredSize(new Dimension(imgConfirmPromptImagePane
                                    .getImgIcon().getImage().getWidth(null)
                                                    + SIZE_TOPPANE_COMP,
                                    imgConfirmPromptImagePane
                                    .getImgIcon()
                                    .getImage().getHeight(
                                            null)));
            topPanel.add(imgConfirmPromptImagePane, BorderLayout.EAST);
            topPanel.revalidate();

            pnlOverlayContainer.removeAll();
            confirmButton = new AButton("game_btn_removeYes_norm.png",
                    "game_btn_removeYes_down.png",
                    "game_btn_removeYes_over.png",
                    removeButtonWidth, 55);
            confirmButton.addActionListener(new RemoveGameHandler());
            denyButton = new AButton("game_btn_removeNo_norm.png",
                    "game_btn_removeNo_down.png",
                    "game_btn_removeNo_over.png",
                    removeButtonWidth, 55);
            denyButton.addActionListener(new CancelRemoveGameHandler());

            denyPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,
                    removeButtonSeperation, -5));
            denyPanel.setPreferredSize(new Dimension(145, 55));
            denyPanel.setOpaque(false);
            denyPanel.add(denyButton);

            confirmPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,
                    removeButtonSeperation, -5));
            confirmPanel.setPreferredSize(new Dimension(185, 55));
            confirmPanel.setOpaque(false);
            confirmPanel.add(confirmButton);


            denyPanel.setAlignmentX(JComponent.LEFT_ALIGNMENT);
            confirmPanel.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
            pnlOverlayContainer.add(denyPanel);
            pnlOverlayContainer.add(confirmPanel);
            pnlOverlayContainer.add(Box.createHorizontalStrut(5));
            imgOverlayBar.revalidate();
            setUnselected();
            isGameRemoveMode = true;
            imgOverlayBar.setVisible(true);

        }
    }

    /**
     * .-----------------------------------------------------------------------.
     * | CancelRemoveGameHandler
     * .-----------------------------------------------------------------------.
     * |
     * | Handler when No button setSelected remove the Confirm Removal overlay
     * | and re-add original Game Overlay.
     * |
     * |
     * .........................................................................
     *
     * @author
     * <p/>
     */
    class CancelRemoveGameHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            pnlInteractivePane.removeAll();
            pnlInteractivePane.setVisible(false);

            reAddInteractive();
            showRemoveBtn();
            imgOverlayBar.setVisible(true);
            isGameRemoveMode = false;
            setSelected();
        }
    }

    class RemoveGameHandler implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            if (logger.isDebugEnabled()) {
                logger.debug("Remove button pressed for " + Game.this.getName());
            }

            AThreadWorker removeGame = new AThreadWorker(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    StoredLibrary libraryStorage = storage.getStoredLibrary();
                    libraryStorage.search(name);
                    libraryStorage.removeGame(Game.this);
                    if (storage.getStoredProfile().getGameNames().size() > 0) {
                        storage.getStoredProfile().removeGameMetadata(Game.this);
                    }

                    LibraryUI.lblLibraryStatus.setForeground(new Color(194, 40,
                            35));
                    LibraryUI.lblLibraryStatus.setText("Removed Game");

                    LibraryLogic.refreshAuto = true;

                    try {
                        Thread.sleep(1200);
                    } catch (InterruptedException ex) {
                        java.util.logging.Logger.getLogger(Game.class.getName())
                                .log(Level.SEVERE, null, ex);
                    }

                }
            }, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    LibraryUI.lblLibraryStatus.setForeground(Color.lightGray);
                    LibraryUI.lblLibraryStatus.setText("Select a Game");
                }
            });

            removeGame.startOnce();

            manager.removeGame(Game.this);

        }
    }

    private void select() {

        manager.unselectPrevious();
        showOverlayUI();

    }

    private void unselect() {
        hideOverlayUI();

        if (logger.isDebugEnabled()) {
            logger.debug("GAME UNSELECTED");

        }
    }

    //Game Cover Selected Handler
    class InteractiveListener extends MouseAdapter {

        @Override
        public void mousePressed(final MouseEvent e) {
            if (!isRemoved) {
                requestFocus();
                if (isSelected()) {
                    unselect();
                } else {
                    unSelectPrevious();
                    showOverlayUI();

                    if (logger.isDebugEnabled()) {
                        logger.debug("SELECTED");
                    }
                }
            }

        }

        @Override
        public void mouseEntered(final MouseEvent e) {
            // Mouse being dragged over game
            if (e.getModifiers() == MouseEvent.BUTTON1_MASK) {

                if (!isRemoved) {
                    requestFocus();
                    if (isSelected()) {
                        hideOverlayUI();
                    } else {
                        unSelectPrevious();
                        showOverlayUI();
                    }
                }
            }
        }

    }

    private void setSize() {

        if (coreUI.isLargeScreen()) {
            removeButtonWidth = this.width / 2 - 35;
            removeButtonSeperation = -removeButtonWidth / 6 + 2;

            SIZE_BottomPaneHeight = (50 * 2) - 10;
            SIZE_TOPPANE_COMP = 5;

            flipShortcutWidth = (width / 2) + (width / 10);
            flipShortcutHeight = height / 6 + 5;
            labelFontSize = 18;
            flipPadding = 5;
        } else {
            flipShortcutWidth = (width / 2) + (width / 10);
            flipShortcutHeight = height / 6 + flipShortcutWidth / 12;
            removeButtonWidth = this.width / 2 - 32;
            removeButtonSeperation = -removeButtonWidth / 6 + 1;

            SIZE_TOPPANE_COMP = 0;
            SIZE_BottomPaneHeight = (51 * 2) - (height / 18) + (width / height
                                                                + 1);

            labelFontSize = 17;
            flipPadding = 3;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("OVERLAY HEIGHT " + SIZE_BottomPaneHeight);
        }

    }

    public void refresh(Boolean useAuroraDB) {

        try {
            coverImagePane.setURL(new File(localGameRootPath + coverURL)
                    .getPath());
        } catch (Exception ex) {
            coverImagePane.setURL(rootCoverDBPath + coverURL);
        }

        coverImagePane.setImageSize(width, height);
        this.setImage(coverImagePane);
        coverImagePane.repaint();
        this.revalidate();
        this.repaint();
    }

    // Getters & Setters
    // -----------------------------------------------------------------------.
    public final AuroraStorage getStorage() {
        return storage;
    }

    public final boolean isLoaded() {
        return isLoaded;
    }

    public final ActionListener getPlayHandler() {
        return playButtonListener;
    }

    public final void setStorage(final AuroraStorage storage) {
        this.storage = storage;
    }

    public final AButton getFavoriteButton() {
        return btnFavorite;
    }

    public final void setFavoriteButton(final AButton favoriteButton) {
        this.btnFavorite = favoriteButton;
    }

    public final String getBoxArtUrl() {
        if (coverURL == null || coverURL.equals("")) {
            return coverImagePane.getImageURL();
        } else {
            return coverURL;
        }
    }

    public final JPanel getInteractivePane() {
        return pnlInteractivePane;
    }

    public final AImagePane getGameBar() {
        return imgOverlayBar;
    }

    public final boolean isSelected() {
        return isSelected;
    }

    public final boolean isFavorite() {
        return isFavorite;

    }

    public final String getGameType() {
        return gameType;

    }

    public final String getLastPlayed() {
        return lastPlayed;
    }

    @Override
    public final int getWidth() {
        return width;
    }

    @Override
    public final int getHeight() {
        return height;
    }

    @Override
    public final String getName() {
        return name;
    }

    public final int getOccurencesPlayed() {
        return numberTimesPlayed;
    }

    public final String getTotalTimePlayed() {
        return timePlayed;
    }

    public final DashboardUI getDashboardUI() {
        return this.dashboardUI;
    }

    public final void setCoverUrl(final String coverUrl) throws
            MalformedURLException {
        this.coverURL = coverUrl;

    }

    public final void setCoverImage(final String coverUrl) throws
            MalformedURLException {
        this.coverImagePane.setImageURL(coverUrl);

    }

    public final void setFavorite(final boolean favorite) {
        this.isFavorite = favorite;

        if (favorite) {
            setFavorite();
        }
    }

    public AButton getBtnFlip() {
        return btnFlip;
    }

    public final void setGameType(final String gameType) {
        this.gameType = gameType;
    }

    public final void setLastPlayed(final String lastPlayed) {
        this.lastPlayed = lastPlayed;
    }

    public final void setDashboardUI(final DashboardUI dashboardUi) {
        this.dashboardUI = dashboardUi;
    }

    public final void setGameName(final String name) {
        this.name = name;
    }

    public final String getGameName() {
        return name;
    }

    public final void setOcurrencesPlayed(final int numberTimesPlayed) {
        this.numberTimesPlayed = numberTimesPlayed;
    }

    public final void setTotalTimePlayed(final String timePlayed) {
        this.timePlayed = timePlayed;
    }

    public final Game copy() {
        try {
            return (Game) this.clone();
        } catch (CloneNotSupportedException ex) {
            logger.error(ex);
            return null;
        }
    }

    public AImagePane getCoverImagePane() {
        return coverImagePane;
    }

    public final Game thisGame() {
        return this;
    }

    public final void setGamePath(final String path) {

        if (logger.isDebugEnabled()) {
            logger.debug("Game path = " + path);
        }

        this.gamePath = path;
    }

    public final String getGamePath() {
        return this.gamePath;
    }

    public AButton getBtnAddCustomOverlay() {
        return btnAddCustomOverlay;
    }

    public String getCoverURL() {
        return coverURL;
    }

}
