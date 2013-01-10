<<<<<<< HEAD
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

import aurora.engine.V1.UI.aButton;
import aurora.engine.V1.UI.aDialog;
import aurora.engine.V1.UI.aImagePane;
import aurora.engine.V1.UI.aProgressWheel;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Sammy
 */
public class Game extends aImagePane implements Serializable, Runnable, Cloneable {

    private boolean mouseover;
    private String Name;
    private String coverURL;
    private String gamePATH;
    private String TimePlayed;
    private String LastPlayed;
    private int NumberTimesPlayed;
    private String GameType;
    private boolean Favorite;
    private Thread GameCoverThread;
    private boolean loaded = false;
    private aProgressWheel progress;
    private int width;
    private int height;
    private aImagePane coverImg;
    private aImagePane Blank;
    private boolean selected;
    private aImagePane Glow;
    private aImagePane FavoriteIcon;
    private JPanel InteractivePane;
    private JPanel TopPane;
    private aImagePane GameBar;
    private aButton RemoveButton;
    private aButton FavoriteButton;
    private aButton InfoButton;
    private aButton PlayButton;
    private GridManager manager;
    private JPanel BottomPane;
    private aImagePane RemovePane;
    private AuroraCoreUI ui;
    private JPanel PlayButtonPane;
    private JPanel InfoButtonPane;
    private JPanel FavoriteButtonPane;
    private Aurora_Dashboard dashObj;
    private JPanel GameBarPane;
    private aDialog dbErrr;
    private boolean isRemoved = false;
    private aImagePane imgConfirmPrompt;
    private JLabel lblYes;
    private JLabel lblNo;
    private aButton btnConfirm;
    private aButton btnDeny;
    private JPanel ConfirmPane;
    private JPanel DenyPane;
    private AuroraStorage storage;
    private int SIZE_TOPPANE_COMP;
    private int SIZE_BottomPaneHeight;
    private final String rootCoverDBPath = "https://s3.amazonaws.com/CoverArtDB/";
    private PlayButtonListener playButtonListener;

    public Game() {
    }

    public Game(GridManager manager, AuroraCoreUI ui, Aurora_Dashboard obj) {


        this.dashObj = obj;
        this.ui = ui;
        this.manager = manager;
        this.setOpaque(false);
        this.setDoubleBuffered(true);
        //this.setSize();

        //DEFAULT CASE
        this.setImage("Blank-Case.png", height, width);
        this.setPreferredSize(new Dimension(width, height));


//        InteractivePane = new JPanel(new BorderLayout());
//        InteractivePane.setOpaque(false);
//        InteractivePane.setPreferredSize(new Dimension(width, height));
//        this.add(InteractivePane);
//        this.revalidate();


    }

    public Game(GridManager manager, AuroraCoreUI ui, Aurora_Dashboard obj, AuroraStorage storage) {
        this.dashObj = obj;
        this.ui = ui;
        this.storage = storage;
        this.manager = manager;
        this.setOpaque(false);
        this.setDoubleBuffered(true);
        // this.setSize();

        //DEFAULT CASE
        this.setImage("Blank-Case.png", height, width);
        this.setPreferredSize(new Dimension(width, height));



    }

    public Game(GridManager manager, AuroraCoreUI ui, String CoverURL) {

        this.ui = ui;
        this.manager = manager;
        this.setOpaque(false);
        this.setDoubleBuffered(true);
        this.coverURL = CoverURL;
        // this.setSize();

        //DEFAULT CASE
        this.setImage("Blank-Case.png", height, width);
        this.setPreferredSize(new Dimension(width, height));

//        InteractivePane = new JPanel(new BorderLayout());
//        InteractivePane.setOpaque(false);
//        InteractivePane.setPreferredSize(new Dimension(width, height));
//        this.add(InteractivePane);
//        this.revalidate();

    }

    public Game(String CoverURL, Aurora_Dashboard obj) {


        this.setOpaque(false);
        this.ui = obj.getUI();
        this.dashObj = obj;
        this.coverURL = CoverURL;

        //DEFAULT CASE
        this.setImage("Blank-Case.png", height, width);
        this.setPreferredSize(new Dimension(width, height));

//        InteractivePane = new JPanel(new BorderLayout());
//        InteractivePane.setOpaque(false);
//        InteractivePane.setPreferredSize(new Dimension(width, height));
//        InteractivePane.setVisible(false);
//        this.revalidate();

    }

    public Game(Aurora_Dashboard obj) {


        this.setOpaque(false);
        this.dashObj = obj;
        this.ui = obj.getUI();

        //DEFAULT CASE
        this.setImage("Blank-Case.png", height, width);
        this.setPreferredSize(new Dimension(width, height));

//        InteractivePane = new JPanel(new BorderLayout());
//        InteractivePane.setOpaque(false);
//        InteractivePane.setPreferredSize(new Dimension(width, height));
//        InteractivePane.setVisible(false);
//        this.revalidate();

    }

    public void update() throws MalformedURLException {


        InteractivePane = new JPanel(new BorderLayout());
        InteractivePane.setOpaque(false);
        InteractivePane.addMouseListener(new Game.InteractiveListener());
        this.addMouseListener(new Game.InteractiveListener());
        this.add(InteractivePane);
        this.revalidate();

        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(width, height));


        //Create Overlay UI Components//

        coverImg = new aImagePane();
        Blank = new aImagePane();
        Glow = new aImagePane("Glow-Case.png", width + 10, height + 10);
        FavoriteIcon = new aImagePane("FavoriteIcon.png", 100, 32);
        FavoriteIcon.setPreferredSize(new Dimension(100, 32));
        RemoveButton = new aButton("RemoveGame_up.png", "RemoveGame_down.png", "RemoveGame_over.png");
        RemoveButton.addActionListener(new RemoveButtonListener());

        GameBar = new aImagePane("GameIconBar.png", width - 30, 55);
        GameBar.setOpaque(false);
        GameBar.setPreferredSize(new Dimension(width - 30, 55));
        GameBar.setLayout(new BorderLayout());
        GameBar.setBackground(Color.blue);

        GameBarPane = new JPanel();
        GameBarPane.setOpaque(false);
        GameBarPane.setBackground(Color.red);
        GameBarPane.setLayout(new BoxLayout(GameBarPane, BoxLayout.X_AXIS));



        //Game Bar Elements//

        FavoriteButton = new aButton("StarGame_up.png", "StarGame_down.png", "StarGame_over.png");
        FavoriteButton.addActionListener(new Game.FavoriteButtonListener());

        FavoriteButtonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        FavoriteButtonPane.setPreferredSize(new Dimension(30, 40));
        FavoriteButtonPane.add(FavoriteButton);
        FavoriteButtonPane.setOpaque(false);


        InfoButton = new aButton("GameInfo_up.png", "GameInfo_down.png", "GameInfo_over.png");

        InfoButtonPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        InfoButtonPane.setPreferredSize(new Dimension(80, 40));
        InfoButtonPane.add(InfoButton);
        InfoButtonPane.setOpaque(false);


        PlayButton = new aButton("PlayGame_up.png", "PlayGame_down.png", "PlayGame_over.png");
        PlayButton.setPreferredSize(new Dimension(40, 40));
        PlayButton.setOpaque(false);
        playButtonListener = new Game.PlayButtonListener();
        PlayButton.addActionListener(playButtonListener);


        GameBarPane.add(FavoriteButtonPane);
        GameBarPane.add(PlayButton);
        GameBarPane.add(InfoButtonPane);

        GameBar.setVisible(false);
        GameBar.add(GameBarPane);


        //Set up inteteractive pane

        TopPane = new JPanel(new BorderLayout());
        TopPane.setOpaque(false);
        TopPane.setPreferredSize(new Dimension(width, 55));

        BottomPane = new JPanel(new BorderLayout());
        BottomPane.setOpaque(false);
        BottomPane.setPreferredSize(new Dimension(width - 10, SIZE_BottomPaneHeight));

        RemoveButton.setVisible(false);
        FavoriteIcon.setVisible(false);

        TopPane.add(RemoveButton, BorderLayout.EAST);
        TopPane.add(FavoriteIcon, BorderLayout.WEST);
        TopPane.validate();

        BottomPane.add(GameBar, BorderLayout.NORTH);
        GameBar.validate();

        InteractivePane.add(TopPane, BorderLayout.PAGE_START);
        InteractivePane.add(BottomPane, BorderLayout.SOUTH);



        BottomPane.validate();
        TopPane.validate();
        InteractivePane.validate();

        //Loading Thread
        GameCoverThread = null;

        if (GameCoverThread == null) {
            GameCoverThread = new Thread(this);
        }
        GameCoverThread.setName("Game Cover Thread");
        //Start Loader
        GameCoverThread.start();

        FavoriteButtonPane.revalidate();

        System.out.println("pane width " + width);

    }

    @Override
    public void run() {

        if (Thread.currentThread() == GameCoverThread) {


            progress = new aProgressWheel("Aurora_Loader.png");
            progress.setPreferredSize(this.getPreferredSize());
            this.add(progress, BorderLayout.NORTH);
            //Mouse handlers


            if (dashObj.getStartUp_Obj().getFileIO().findImg("Game Data", coverURL) != null) {
                //Get Image

                coverImg.setImage(dashObj.getStartUp_Obj().getFileIO().findImg("Game Data", coverURL), width, height);
                coverImg.setImageSize(width, height);
                coverImg.setPreferredSize(new Dimension(width, height));
                coverImg.setDoubleBuffered(true);
                this.remove(progress);
                this.setImage(coverImg);
                this.add(InteractivePane);
                this.revalidate();
                this.repaint();
            } else {

                //Load Image

                try {

                    if (Aurora_StartUp.Online) {
                        dbErrr = null;
                        System.out.println(coverURL);
                        coverImg.setImageURL(rootCoverDBPath + coverURL);

                        //Set Background acordingly
                        coverImg.setImageSize(width, height);
                        coverImg.setPreferredSize(new Dimension(width, height));
                        if (coverImg.getImgIcon().getIconHeight() == -1) {
                            if (dbErrr == null) {
                                dbErrr = new aDialog(aDialog.aDIALOG_ERROR, "AuroraDB Error! Can't Access BoxArt", ui.getFontBold());
                                dbErrr.showDialog();

                            }
                            dbErrr.setVisible(true);
                        } else {
                            dashObj.getStartUp_Obj().getFileIO().writeImage(coverImg, coverURL, "Game Data");

                            this.remove(progress);

                            //Add Image To GameCover Cover

                            this.setImage(coverImg);
                            this.add(InteractivePane);
                            this.revalidate();
                            this.repaint();
                        }
                    } else {
                        this.remove(progress);
                        this.add(InteractivePane);
                        this.revalidate();
                    }



                } catch (MalformedURLException ex) {

                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }


            }


        }
        //End of Loading
        InteractivePane.setPreferredSize(new Dimension(width, height));
        InteractivePane.setSize(new Dimension(width, height));

        loaded = true;

        //Finalize
        afterLoad();

    }

    //To be called when we want to re add the Overlay on the Game Covers
    public void reAddInteractive() {

        isRemoved = false;
        setSize();
        InteractivePane.setVisible(true);

        Glow = new aImagePane("Glow-Case.png", width + 10, height + 10);
        FavoriteIcon = new aImagePane("FavoriteIcon.png", 100, 32);
        FavoriteIcon.setPreferredSize(new Dimension(100, 32));
        RemoveButton = new aButton("RemoveGame_up.png", "RemoveGame_down.png", "RemoveGame_over.png");
        RemoveButton.addActionListener(new RemoveButtonListener());

        GameBar = new aImagePane("GameIconBar.png", width - 30, 55);
        GameBar.setOpaque(false);
        GameBar.setPreferredSize(new Dimension(width - 30, 55));
        GameBar.setLayout(new BorderLayout());
        GameBar.setBackground(Color.blue);

        GameBarPane = new JPanel();
        GameBarPane.setOpaque(false);
        GameBarPane.setLayout(new BoxLayout(GameBarPane, BoxLayout.X_AXIS));



        //Game Bar Elements//

        FavoriteButton = new aButton("StarGame_up.png", "StarGame_down.png", "StarGame_over.png");
        FavoriteButton.addActionListener(new Game.FavoriteButtonListener());


        FavoriteButtonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        FavoriteButtonPane.setPreferredSize(new Dimension(30, 40));
        FavoriteButtonPane.add(FavoriteButton);
        FavoriteButtonPane.setOpaque(false);


        InfoButton = new aButton("GameInfo_up.png", "GameInfo_down.png", "GameInfo_over.png");

        InfoButtonPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        InfoButtonPane.setPreferredSize(new Dimension(80, 40));
        InfoButtonPane.add(InfoButton);
        InfoButtonPane.setOpaque(false);


        PlayButton = new aButton("PlayGame_up.png", "PlayGame_down.png", "PlayGame_over.png");
        PlayButton.setPreferredSize(new Dimension(40, 40));
        PlayButton.setOpaque(false);
        playButtonListener = new Game.PlayButtonListener();
        PlayButton.addActionListener(playButtonListener);


        GameBarPane.add(FavoriteButtonPane);
        GameBarPane.add(PlayButton);
        GameBarPane.add(InfoButtonPane);
        GameBarPane.validate();

        GameBar.setVisible(false);
        GameBar.add(GameBarPane);
        GameBar.setOpaque(false);
        GameBar.validate();



        //Set up inteteractive pane

        TopPane = new JPanel(new BorderLayout());
        TopPane.setOpaque(false);
        TopPane.setPreferredSize(new Dimension(width, 55));

        BottomPane = new JPanel(new BorderLayout());
        BottomPane.setOpaque(false);
        BottomPane.setPreferredSize(new Dimension(width - 10, SIZE_BottomPaneHeight));


        RemoveButton.setVisible(false);
        FavoriteIcon.setVisible(false);

        TopPane.add(RemoveButton, BorderLayout.EAST);
        TopPane.add(FavoriteIcon, BorderLayout.WEST);
        TopPane.validate();

        BottomPane.add(GameBar, BorderLayout.NORTH);


        InteractivePane.add(TopPane, BorderLayout.PAGE_START);
        InteractivePane.add(BottomPane, BorderLayout.SOUTH);


        TopPane.validate();
        InteractivePane.revalidate();

        if (isFavorite()) {
            favorite();
        }

        this.repaint();
    }

    private void setSize() {
        if (ui.isLargeScreen()) {

            SIZE_BottomPaneHeight = (50 * 2) - 10;
            SIZE_TOPPANE_COMP = 5;
        } else {
            SIZE_TOPPANE_COMP = 0;
            SIZE_BottomPaneHeight = (50 * 2) - 10;
        }
        System.out.println("OVERLAY HEIGHT " + SIZE_BottomPaneHeight);
    }

    public void removePreviousSelected() {
        if (manager != null) {
            manager.unselectPrevious();
        }

    }

    private void afterLoad() {
        if (loaded) {

            if (selected) {
                selected();
            }

            if (Favorite) {
                favorite();
            }
        }

    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setCoverSize(int width, int height) {
        this.width = width;
        this.height = height;
        this.setImageSize(width, height);
        setSize();
    }

    public void selected() {
        selected = true;
        if (loaded) {
            this.add(Glow);
            this.repaint();
            this.validate();
        }

    }

    public void unselected() {
        if (selected) {
            selected = false;
            RemoveButton.setVisible(false);
            InteractivePane.revalidate();
            this.remove(Glow);
            this.repaint();
            this.revalidate();

        }
    }

    public void hideInteractiveComponents() {
        hideInteraction();
        GameBar.setVisible(false);
        unselected();
    }

    public void displayInteractiveComponents() {

        InteractivePane.setSize(width + 47, height + 28);
        System.out.println("INTERACTIVE SIZE " + InteractivePane.getWidth() + " " + InteractivePane.getHeight());
        
        showRemove();
        GameBar.setVisible(true);
        selected();
        Aurora_GameLibrary.lblGameName.setText(getName());


    }

    public void favorite() {
        Favorite = true;
        if (loaded) {
            FavoriteIcon.setVisible(true);
            InteractivePane.revalidate();
        }

    }

    public void unfavorite() {
        if (Favorite) {
            Favorite = false;
            FavoriteIcon.setVisible(false);
            InteractivePane.revalidate();
        }
    }

    public void showRemove() {

        if (loaded) {
            RemoveButton.setVisible(true);
            InteractivePane.revalidate();
        }
    }

    public void hideInteraction() {
        if (loaded) {
            RemoveButton.setVisible(false);
            InteractivePane.revalidate();
        }
    }

    public void removeInteraction() {
        this.remove(InteractivePane);
        this.isRemoved = true;
    }

    ////Getters and Setters
    public AuroraStorage getStorage() {
        return storage;
    }
    
    public ActionListener getPlayHandler(){
        return playButtonListener;
    }

    public void setStorage(AuroraStorage storage) {
        this.storage = storage;
    }

    public aButton getFavoriteButton() {
        return FavoriteButton;
    }

    public void setFavoriteButton(aButton FavoriteButton) {
        this.FavoriteButton = FavoriteButton;
    }

    public String getBoxArtURL() {
        return coverURL;
    }

    public JPanel getInteractivePane() {
        return InteractivePane;
    }

    public aImagePane getGameBar() {
        return GameBar;
    }

    public boolean isSelected() {
        return selected;
    }

    public boolean isFavorite() {
        return Favorite;

    }

    public String getGameType() {
        return GameType;

    }

    public String getLastPlayed() {
        return LastPlayed;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public String getName() {
        return Name;
    }

    public int getNumberTimesPlayed() {
        return NumberTimesPlayed;
    }

    public String getTimePlayed() {
        return TimePlayed;
    }

    public Aurora_Dashboard getDashObj() {
        return this.dashObj;
    }

    public void setCoverURL(String CoverURL) throws MalformedURLException {
        this.coverURL = CoverURL;
    }

    public void setFavorite(boolean Favorite) {
        this.Favorite = Favorite;

        if (Favorite) {
            favorite();
        }
    }

    public void setGameType(String GameType) {
        this.GameType = GameType;

    }

    public void setLastPlayed(String LastPlayed) {
        this.LastPlayed = LastPlayed;
    }

    public void setDashObj(Aurora_Dashboard DashObj) {
        this.dashObj = DashObj;
    }

    public void setGameName(String Name) {
        this.Name = Name;

    }

    public String getGameName() {
        return Name;
    }

    public void setNumberTimesPlayed(int NumberTimesPlayed) {
        this.NumberTimesPlayed = NumberTimesPlayed;

    }

    public void setTimePlayed(String TimePlayed) {
        this.TimePlayed = TimePlayed;
    }

    public Game copy() {
        try {
            return (Game) this.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Game thisGame() {
        return this;
    }

    public void setGamePath(String path) {
        System.out.println("Game path = " + path);
        this.gamePATH = path;
    }

    public String getGamePath() {
        return this.gamePATH;
    }

    class FavoriteButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Favourite button pressed.");
            if (Favorite) {
                unfavorite();
                storage.getStoredLibrary().SaveFavState(thisGame());
            } else {
                favorite();
                storage.getStoredLibrary().SaveFavState(thisGame());
            }
        }
    }

    //PlayButtonListener added by Carlos
    class PlayButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            System.out.println("Play button pressed.");
            new AuroraLauncher(copy(), ui).createUI();

        }
    }

    class RemoveButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            TopPane.remove(RemoveButton);
            imgConfirmPrompt = new aImagePane("GameDelete_ConfirmOverlay.png");
            imgConfirmPrompt.setPreferredSize(new Dimension(imgConfirmPrompt.getImgIcon().getImage().getWidth(null) + SIZE_TOPPANE_COMP, imgConfirmPrompt.getImgIcon().getImage().getHeight(null)));
            TopPane.add(imgConfirmPrompt, BorderLayout.EAST);
            TopPane.revalidate();

            GameBarPane.removeAll();
            lblYes = new JLabel("Yes");
            lblNo = new JLabel("No");
            btnConfirm = new aButton("GameDelete_Accept.png", "GameDelete_Accept_down.png", "GameDelete_Accept_over.png");
            btnConfirm.addActionListener(new RemoveGameHandler());
            btnDeny = new aButton("GameDelete_Deny.png", "GameDelete_Deny_down.png", "GameDelete_Deny_over.png");
            btnDeny.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    InteractivePane.removeAll();
                    InteractivePane.setVisible(false);
                    remove(Glow);
                    selected = true;
                    reAddInteractive();
                    showRemove();
                    selected();
                    GameBar.setVisible(true);


                }
            });
            lblYes.setFont(ui.getDefaultFont().deriveFont(Font.PLAIN, 20));
            lblYes.setForeground(Color.yellow);

            lblNo.setFont(ui.getDefaultFont().deriveFont(Font.PLAIN, 20));
            lblNo.setForeground(Color.yellow);

            DenyPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            DenyPane.setPreferredSize(new Dimension(30, 40));
            DenyPane.add(lblNo);
            DenyPane.setOpaque(false);

            ConfirmPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
            ConfirmPane.setPreferredSize(new Dimension(90, 40));
            ConfirmPane.add(lblYes);
            ConfirmPane.setOpaque(false);

            GameBarPane.add(DenyPane);
            GameBarPane.add(btnDeny);
            GameBarPane.add(btnConfirm);
            GameBarPane.add(ConfirmPane);
            GameBar.revalidate();
            unselected();
            GameBar.setVisible(true);

        }
    }

    class RemoveGameHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Remove button pressed for " + Game.this.getName());
            AuroraStorage storage = dashObj.getStorage();
            StoredLibrary library = storage.getStoredLibrary();
            library.search(Name);
            library.removeGame(Game.this);
            manager.removeGame(Game.this);
        }
    }

    //Game Cover Selected Handler
    class InteractiveListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
//            
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (!isRemoved) {
                requestFocus();
                if (isSelected()) {
                    hideInteraction();
                    GameBar.setVisible(false);
                    unselected();
                    System.out.println("GAME UNSELECTED");
                } else {

                    removePreviousSelected();
                    displayInteractiveComponents();
                }
            }


        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {

            if (e.getModifiers() == MouseEvent.BUTTON1_MASK) {

                if (!isRemoved) {
                    requestFocus();
                    if (isSelected()) {
                        hideInteraction();
                        GameBar.setVisible(false);
                        unselected();
                    } else {
                        removePreviousSelected();
                        displayInteractiveComponents();
                    }
                }
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
}
=======
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

import aurora.V1.core.screen_ui.DashboardUI;
import aurora.V1.core.screen_ui.GameLibraryUI;
import aurora.V1.core.screen_ui.StartScreenUI;
import aurora.engine.V1.UI.AButton;
import aurora.engine.V1.UI.ADialog;
import aurora.engine.V1.UI.AImagePane;
import aurora.engine.V1.UI.AProgressWheel;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 * @author Carlos Machado <camachado@gmail.com>
 */
public class Game extends AImagePane implements Runnable, Cloneable {

    private String name;

    private String coverUrl;

    private String gamePath;

    private String timePlayed;

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

    private AImagePane blankImagePane;

    private AImagePane glowImagePane;

    private AImagePane favoriteIconImagePane;

    private AImagePane gameBarImagePane;

    private AImagePane removeImagePane;

    private AImagePane imgConfirmPromptImagePane;

    private JPanel interactivePanel;

    private JPanel topPanel;

    private JPanel playButtonPanel;

    private JPanel infoButtonPanel;

    private JPanel favoriteButtonPanel;

    private JPanel bottomPanel;

    private JPanel gameBarPanel;

    private JPanel confirmPanel;

    private JPanel denyPanel;

    private AButton removeButton;

    private AButton favoriteButton;

    private AButton infoButton;

    private AButton playButton;

    private AButton confirmButton;

    private AButton denyButton;

    private ADialog dbErrorDialog;

    private GridManager manager;

    private AuroraCoreUI coreUI;

    private DashboardUI dashboardUi;

    private AuroraStorage storage;

    private final String rootCoverDBPath = "https://s3.amazonaws.com/CoverArtDB/";

    private PlayButtonListener playButtonListener;

    private boolean isGameRemoveMode;

    public Game() {
    }

    public Game(final GridManager gridManager, final AuroraCoreUI auroraCoreUI,
                final DashboardUI dashboardUi) {


        this.dashboardUi = dashboardUi;
        this.coreUI = auroraCoreUI;
        this.manager = gridManager;
        this.setOpaque(false);
        this.setDoubleBuffered(true);

        //DEFAULT CASE
        this.setImage("Blank-Case.png", height, width);
        this.setPreferredSize(new Dimension(width, height));



    }

    public Game(final GridManager manager, final AuroraCoreUI ui,
                final DashboardUI obj, final AuroraStorage storage) {
        this.dashboardUi = obj;
        this.coreUI = ui;
        this.storage = storage;
        this.manager = manager;
        this.setOpaque(false);
        this.setDoubleBuffered(true);
        // this.setSize();

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
        this.coverUrl = CoverURL;
        // this.setSize();

        //DEFAULT CASE
        this.setImage("Blank-Case.png", height, width);
        this.setPreferredSize(new Dimension(width, height));


    }

    public Game(final String CoverURL, final DashboardUI obj) {


        this.setOpaque(false);
        this.coreUI = obj.getCoreUI();
        this.dashboardUi = obj;
        this.coverUrl = CoverURL;

        //DEFAULT CASE
        this.setImage("Blank-Case.png", height, width);
        this.setPreferredSize(new Dimension(width, height));


    }

    public Game(final DashboardUI obj) {


        this.setOpaque(false);
        this.dashboardUi = obj;
        this.coreUI = obj.getCoreUI();

        //DEFAULT CASE
        this.setImage("Blank-Case.png", height, width);
        this.setPreferredSize(new Dimension(width, height));


    }

    public final void update() throws MalformedURLException {


        interactivePanel = new JPanel(new BorderLayout());
        interactivePanel.setOpaque(false);
        interactivePanel.addMouseListener(new Game.InteractiveListener());
        this.addMouseListener(new Game.InteractiveListener());
        this.add(interactivePanel);
        this.revalidate();

        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(width, height));


        //Create Overlay UI Components//
        coverImagePane = new AImagePane();
        blankImagePane = new AImagePane();
        glowImagePane = new AImagePane("game_selectedGlow.png", width + 10,
                height + 10);
        favoriteIconImagePane = new AImagePane("game_favouriteIcon.png", 100, 32);
        favoriteIconImagePane.setPreferredSize(new Dimension(100, 32));
        removeButton = new AButton("game_btn_remove_norm.png",
                "game_btn_remove_down.png",
                "game_btn_remove_over.png");
        removeButton.addActionListener(new RemoveButtonListener());

        gameBarImagePane = new AImagePane("game_overlay.png", width - 30, 55);
        gameBarImagePane.setOpaque(false);
        gameBarImagePane.setPreferredSize(new Dimension(width - 30, 55));
        gameBarImagePane.setLayout(new BorderLayout());
        gameBarImagePane.setBackground(Color.blue);

        gameBarPanel = new JPanel();
        gameBarPanel.setOpaque(false);
        gameBarPanel.setBackground(Color.red);
        gameBarPanel.setLayout(new BoxLayout(gameBarPanel, BoxLayout.X_AXIS));

        //Game Bar Elements//
        favoriteButton = new AButton("game_btn_star_norm.png",
                "game_btn_star_down.png",
                "game_btn_star_over.png");
        favoriteButton.addActionListener(new Game.FavoriteButtonListener());
        favoriteButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        favoriteButtonPanel.setPreferredSize(new Dimension(30, 40));
        favoriteButtonPanel.add(favoriteButton);
        favoriteButtonPanel.setOpaque(false);

        infoButton = new AButton("game_btn_reverse_norm.png",
                "game_btn_reverse_down.png",
                "game_btn_reverse_over.png");
        infoButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoButtonPanel.setPreferredSize(new Dimension(80, 40));
        infoButtonPanel.add(infoButton);
        infoButtonPanel.setOpaque(false);

        playButton = new AButton("game_btn_play_norm.png",
                "game_btn_play_down.png",
                "game_btn_play_over.png");
        playButton.setPreferredSize(new Dimension(40, 40));
        playButton.setOpaque(false);
        playButtonListener = new Game.PlayButtonListener();
        playButton.addActionListener(playButtonListener);

        gameBarPanel.add(favoriteButtonPanel);
        gameBarPanel.add(playButton);
        gameBarPanel.add(infoButtonPanel);

        gameBarImagePane.setVisible(false);
        gameBarImagePane.add(gameBarPanel);

        //Set up interactive pane
        topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setPreferredSize(new Dimension(width, 55));

        bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.setPreferredSize(new Dimension(width - 10,
                SIZE_BottomPaneHeight));

        removeButton.setVisible(false);
        favoriteIconImagePane.setVisible(false);

        topPanel.add(removeButton, BorderLayout.EAST);
        topPanel.add(favoriteIconImagePane, BorderLayout.WEST);
        topPanel.validate();

        bottomPanel.add(gameBarImagePane, BorderLayout.NORTH);
        gameBarImagePane.validate();

        interactivePanel.add(topPanel, BorderLayout.PAGE_START);
        interactivePanel.add(bottomPanel, BorderLayout.SOUTH);

        bottomPanel.validate();
        topPanel.validate();
        interactivePanel.validate();

        //Loading Thread
        gameCoverThread = null;

        if (gameCoverThread == null) {
            gameCoverThread = new Thread(this);
        }
        gameCoverThread.setName("Game Cover Thread");
        //Start Loader
        gameCoverThread.start();

        favoriteButtonPanel.revalidate();

        System.out.println("pane width " + width);

    }

    @Override
    public final void run() {

        if (Thread.currentThread() == gameCoverThread) {
            progressWheel = new AProgressWheel("Aurora_Loader.png");
            progressWheel.setPreferredSize(this.getPreferredSize());
            this.add(progressWheel, BorderLayout.NORTH);
            //Mouse handlers
            if (dashboardUi.getStartUI().getFileIO().findImg("Game Data",
                    coverUrl) != null) {
                //Get Image
                coverImagePane.setImage(dashboardUi.getStartUI().getFileIO()
                        .findImg("Game Data", coverUrl), width, height);
                coverImagePane.setImageSize(width, height);
                coverImagePane.setPreferredSize(new Dimension(width, height));
                coverImagePane.setDoubleBuffered(true);
                this.remove(progressWheel);
                this.setImage(coverImagePane);
                this.add(interactivePanel);
                this.revalidate();
                this.repaint();
            } else {
                //Load Image
                try {

                    if (StartScreenUI.Online) {
                        dbErrorDialog = null;
                        System.out.println(coverUrl);
                        coverImagePane.setURL(rootCoverDBPath + coverUrl);

                        //Set Background accordingly
                        coverImagePane.setImageSize(width, height);
                        coverImagePane.setPreferredSize(new Dimension(width,
                                height));
                        if (coverImagePane.getImgIcon().getIconHeight() == -1) {
                            if (dbErrorDialog == null) {
                                dbErrorDialog = new ADialog(
                                        ADialog.aDIALOG_ERROR,
                                        "AuroraDB Error! Can't Access BoxArt",
                                        coreUI.getRegularFont().deriveFont(
                                        Font.BOLD, 28));
                                dbErrorDialog.showDialog();

                            }
                            dbErrorDialog.setVisible(true);
                        } else {
                            dashboardUi.getStartUI().getFileIO().writeImage(
                                    coverImagePane, coverUrl, "Game Data");

                            this.remove(progressWheel);

                            //Add Image To GameCover Cover
                            this.setImage(coverImagePane);
                            this.add(interactivePanel);
                            this.revalidate();
                            this.repaint();
                        }
                    } else {
                        this.remove(progressWheel);
                        this.add(interactivePanel);
                        this.revalidate();
                    }

                } catch (Exception ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE,
                            null, ex);
                }
            }
        }
        //End of Loading
        interactivePanel.setPreferredSize(new Dimension(width, height));
        interactivePanel.setSize(new Dimension(width, height));

        isLoaded = true;

        //Finalize
        afterLoad();

    }

    //To be called when we want to re add the Overlay on the Game Covers
    public final void reAddInteractive() {

        isRemoved = false;
        setSize();
        interactivePanel.setVisible(true);

        glowImagePane = new AImagePane("game_selectedGlow.png", width + 10,
                height + 10);
        favoriteIconImagePane = new AImagePane("game_favouriteIcon.png", 100, 32);
        favoriteIconImagePane.setPreferredSize(new Dimension(100, 32));
        removeButton = new AButton("game_btn_remove_norm.png",
                "game_btn_remove_down.png",
                "game_btn_remove_over.png");
        removeButton.addActionListener(new RemoveButtonListener());

        gameBarImagePane = new AImagePane("game_overlay.png", width - 30, 55);
        gameBarImagePane.setOpaque(false);
        gameBarImagePane.setPreferredSize(new Dimension(width - 30, 55));
        gameBarImagePane.setLayout(new BorderLayout());
        gameBarImagePane.setBackground(Color.blue);

        gameBarPanel = new JPanel();
        gameBarPanel.setOpaque(false);
        gameBarPanel.setLayout(new BoxLayout(gameBarPanel, BoxLayout.X_AXIS));

        //Game Bar Elements//
        favoriteButton = new AButton("game_btn_star_norm.png",
                "game_btn_star_down.png",
                "game_btn_star_over.png");
        favoriteButton.addActionListener(new Game.FavoriteButtonListener());
        favoriteButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        favoriteButtonPanel.setPreferredSize(new Dimension(30, 40));
        favoriteButtonPanel.add(favoriteButton);
        favoriteButtonPanel.setOpaque(false);

        infoButton = new AButton("game_btn_reverse_norm.png",
                "game_btn_reverse_down.png",
                "game_btn_reverse_over.png");
        infoButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoButtonPanel.setPreferredSize(new Dimension(80, 40));
        infoButtonPanel.add(infoButton);
        infoButtonPanel.setOpaque(false);

        playButton = new AButton("game_btn_play_norm.png",
                "game_btn_play_down.png",
                "game_btn_play_over.png");
        playButton.setPreferredSize(new Dimension(40, 40));
        playButton.setOpaque(false);
        playButtonListener = new Game.PlayButtonListener();
        playButton.addActionListener(playButtonListener);

        gameBarPanel.add(favoriteButtonPanel);
        gameBarPanel.add(playButton);
        gameBarPanel.add(infoButtonPanel);
        gameBarPanel.validate();

        gameBarImagePane.setVisible(false);
        gameBarImagePane.add(gameBarPanel);
        gameBarImagePane.setOpaque(false);
        gameBarImagePane.validate();

        //Set up interactive pane

        topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setPreferredSize(new Dimension(width, 55));

        bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.setPreferredSize(new Dimension(width - 10,
                SIZE_BottomPaneHeight));

        removeButton.setVisible(false);
        favoriteIconImagePane.setVisible(false);

        topPanel.add(removeButton, BorderLayout.EAST);
        topPanel.add(favoriteIconImagePane, BorderLayout.WEST);
        topPanel.validate();

        bottomPanel.add(gameBarImagePane, BorderLayout.NORTH);

        interactivePanel.add(topPanel, BorderLayout.PAGE_START);
        interactivePanel.add(bottomPanel, BorderLayout.SOUTH);

        topPanel.validate();
        interactivePanel.revalidate();

        if (isFavorite()) {
            setFavorite();
        }

        this.repaint();
    }

    private void setSize() {
        if (coreUI.isLargeScreen()) {

            SIZE_BottomPaneHeight = (50 * 2) - 10;
            SIZE_TOPPANE_COMP = 5;
        } else {
            SIZE_TOPPANE_COMP = 0;
            SIZE_BottomPaneHeight = (50 * 2) - 10;
        }
        System.out.println("OVERLAY HEIGHT " + SIZE_BottomPaneHeight);
    }

    public final void removePreviousSelected() {
        if (manager != null) {
            manager.unselectPrevious();
        }

    }

    private void afterLoad() {
        if (isLoaded) {

            if (isSelected) {
                selected();
            }

            if (isFavorite) {
                setFavorite();
            }
        }

    }

    public final boolean isLoaded() {
        return isLoaded;
    }

    public final void setCoverSize(final int width, final int height) {
        this.width = width;
        this.height = height;
        this.setImageSize(width, height);
        setSize();
    }

    public final void selected() {
        isSelected = true;
        if (isLoaded) {
            this.add(glowImagePane);
            this.repaint();
            this.validate();
        }

    }

    public final void unselected() {
        if (isSelected) {

            if (isGameRemoveMode) {

                new CancelRemoveGameHandler().actionPerformed(null);
                gameBarImagePane.setVisible(false);
            }

            isSelected = false;
            removeButton.setVisible(false);
            interactivePanel.revalidate();
            this.remove(glowImagePane);
            this.repaint();
            this.revalidate();



        }
    }

    // Future method to be used to set isSelected variable
    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public final void hideInteractiveComponents() {
        hideInteraction();
        gameBarImagePane.setVisible(false);
        unselected();
    }

    public final void displayInteractiveComponents() {

        interactivePanel.setSize(width + 47, height + 28);
        System.out.println("INTERACTIVE SIZE " + interactivePanel.getWidth()
                           + " " + interactivePanel.getHeight());

        showRemove();
        gameBarImagePane.setVisible(true);
        selected();
        GameLibraryUI.lblGameName.setText(getName());


    }

    public final void setFavorite() {
        isFavorite = true;
        if (isLoaded) {
            favoriteIconImagePane.setVisible(true);
            interactivePanel.revalidate();
        }

    }

    public final void unfavorite() {
        if (isFavorite) {
            isFavorite = false;
            favoriteIconImagePane.setVisible(false);
            interactivePanel.revalidate();
        }
    }

    public final void showRemove() {

        if (isLoaded) {
            removeButton.setVisible(true);
            interactivePanel.revalidate();
        }
    }

    public final void hideInteraction() {
        if (isLoaded) {
            removeButton.setVisible(false);
            interactivePanel.revalidate();
        }
    }

    public final void removeInteraction() {
        this.remove(interactivePanel);
        this.isRemoved = true;
    }

    ////Getters and Setters
    public final AuroraStorage getStorage() {
        return storage;
    }

    public final ActionListener getPlayHandler() {
        return playButtonListener;
    }

    public final void setStorage(final AuroraStorage storage) {
        this.storage = storage;
    }

    public final AButton getFavoriteButton() {
        return favoriteButton;
    }

    public final void setFavoriteButton(final AButton favoriteButton) {
        this.favoriteButton = favoriteButton;
    }

    public final String getBoxArtUrl() {
        return coverUrl;
    }

    public final JPanel getInteractivePane() {
        return interactivePanel;
    }

    public final AImagePane getGameBar() {
        return gameBarImagePane;
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

    public final int getNumberTimesPlayed() {
        return numberTimesPlayed;
    }

    public final String getTimePlayed() {
        return timePlayed;
    }

    public final DashboardUI getDashObj() {
        return this.dashboardUi;
    }

    public final void setCoverUrl(final String coverUrl) throws
            MalformedURLException {
        this.coverUrl = coverUrl;
    }

    public final void setFavorite(final boolean favorite) {
        this.isFavorite = favorite;

        if (favorite) {
            setFavorite();
        }
    }

    public final void setGameType(final String gameType) {
        this.gameType = gameType;
    }

    public final void setLastPlayed(final String lastPlayed) {
        this.lastPlayed = lastPlayed;
    }

    public final void setDashObj(final DashboardUI dashboardUi) {
        this.dashboardUi = dashboardUi;
    }

    public final void setGameName(final String name) {
        this.name = name;
    }

    public final String getGameName() {
        return name;
    }

    public final void setNumberTimesPlayed(final int numberTimesPlayed) {
        this.numberTimesPlayed = numberTimesPlayed;
    }

    public final void setTimePlayed(final String timePlayed) {
        this.timePlayed = timePlayed;
    }

    public final Game copy() {
        try {
            return (Game) this.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public final Game thisGame() {
        return this;
    }

    public final void setGamePath(final String path) {
        System.out.println("Game path = " + path);
        this.gamePath = path;
    }

    public final String getGamePath() {
        return this.gamePath;
    }

    class FavoriteButtonListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            System.out.println("Favourite button pressed.");
            if (isFavorite) {
                unfavorite();
                storage.getStoredLibrary().SaveFavState(thisGame());
            } else {
                setFavorite();
                storage.getStoredLibrary().SaveFavState(thisGame());
            }
        }
    }

    //PlayButtonListener added by Carlos
    class PlayButtonListener implements ActionListener {

        private AuroraLauncher launcher;

        @Override
        public void actionPerformed(final ActionEvent e) {

            System.out.println("Play button pressed.");
            launcher = new AuroraLauncher(coreUI);

            launcher.launchGame(copy());
        }
    }

    class RemoveButtonListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            topPanel.remove(removeButton);
            imgConfirmPromptImagePane = new AImagePane(
                    "game_img_removeWarning.png");
            imgConfirmPromptImagePane
                    .setPreferredSize(new Dimension(imgConfirmPromptImagePane
                    .getImgIcon().getImage().getWidth(null) + SIZE_TOPPANE_COMP,
                    imgConfirmPromptImagePane.getImgIcon().getImage().getHeight(
                    null)));
            topPanel.add(imgConfirmPromptImagePane, BorderLayout.EAST);
            topPanel.revalidate();

            gameBarPanel.removeAll();
            confirmButton = new AButton("game_btn_removeYes_norm.png",
                    "game_btn_removeYes_down.png", "game_btn_removeYes_over.png",
                    120, 55);
            confirmButton.addActionListener(new RemoveGameHandler());
            denyButton = new AButton("game_btn_removeNo_norm.png",
                    "game_btn_removeNo_down.png", "game_btn_removeNo_over.png",
                    120, 55);
            denyButton.addActionListener(new CancelRemoveGameHandler());

            denyPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, -20, -5));
            denyPanel.setPreferredSize(new Dimension(135, 55));
            denyPanel.setOpaque(false);
            denyPanel.add(denyButton);

            confirmPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, -20, -5));
            confirmPanel.setPreferredSize(new Dimension(175, 55));
            confirmPanel.setOpaque(false);

            confirmPanel.add(confirmButton);


            gameBarPanel.add(denyPanel);
            gameBarPanel.add(confirmPanel);
            gameBarImagePane.revalidate();
            unselected();
            isGameRemoveMode = true;
            gameBarImagePane.setVisible(true);


        }
    }

    /**
     * .-----------------------------------------------------------------------.
     * |CancelRemoveGameHandler
     * .-----------------------------------------------------------------------.
     * |
     * | Handler when No button selected remove the Confirm Removal overlay
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
            interactivePanel.removeAll();
            interactivePanel.setVisible(false);
            remove(glowImagePane);
            reAddInteractive();
            showRemove();
            gameBarImagePane.setVisible(true);
            isGameRemoveMode = false;
        }
    }

    class RemoveGameHandler implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            System.out.println("Remove button pressed for " + Game.this
                    .getName());
            AuroraStorage storage = dashboardUi.getStorage();
            StoredLibrary library = storage.getStoredLibrary();
            library.search(name);
            library.removeGame(Game.this);
            manager.removeGame(Game.this);
        }
    }

    //Game Cover Selected Handler
    class InteractiveListener implements MouseListener {

        @Override
        public void mouseClicked(final MouseEvent e) {
        }

        @Override
        public void mousePressed(final MouseEvent e) {
            if (!isRemoved) {
                requestFocus();
                if (isSelected()) {
                    hideInteraction();
                    gameBarImagePane.setVisible(false);
                    unselected();
                    System.out.println("GAME UNSELECTED");
                } else {

                    removePreviousSelected();
                    displayInteractiveComponents();
                }
            }

        }

        @Override
        public void mouseReleased(final MouseEvent e) {
        }

        @Override
        public void mouseEntered(final MouseEvent e) {

            if (e.getModifiers() == MouseEvent.BUTTON1_MASK) {

                if (!isRemoved) {
                    requestFocus();
                    if (isSelected()) {
                        hideInteraction();
                        gameBarImagePane.setVisible(false);
                        unselected();
                    } else {
                        removePreviousSelected();
                        displayInteractiveComponents();
                    }
                }
            }
        }

        @Override
        public void mouseExited(final MouseEvent e) {
        }
    }
}
>>>>>>> origin/dev
