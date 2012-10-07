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

import aurora.V1.core.screen_ui.StartScreen_UI;
import aurora.V1.core.screen_ui.GameLibrary_UI;
import aurora.V1.core.screen_ui.Dashboard_UI;
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
    private Dashboard_UI dashObj;
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

    public Game(GridManager manager, AuroraCoreUI ui, Dashboard_UI obj) {


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

    public Game(GridManager manager, AuroraCoreUI ui, Dashboard_UI obj, AuroraStorage storage) {
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

    public Game(String CoverURL, Dashboard_UI obj) {


        this.setOpaque(false);
        this.ui = obj.getCoreUI();
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

    public Game(Dashboard_UI obj) {


        this.setOpaque(false);
        this.dashObj = obj;
        this.ui = obj.getCoreUI();

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

                    if (StartScreen_UI.Online) {
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
        GameLibrary_UI.lblGameName.setText(getName());


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

    public Dashboard_UI getDashObj() {
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

    public void setDashObj(Dashboard_UI DashObj) {
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
