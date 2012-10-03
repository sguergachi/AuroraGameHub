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

package aurora.V1.core.screen_ui;


import aurora.V1.core.AuroraApp;
import aurora.V1.core.AuroraApp;
import aurora.V1.core.AuroraApp;
import aurora.V1.core.AuroraCoreUI;
import aurora.V1.core.AuroraCoreUI;
import aurora.V1.core.AuroraCoreUI;
import aurora.V1.core.screen_ui.Dashboard_UI;
import aurora.engine.V1.Logic.aXAVI;
import aurora.engine.V1.UI.aImagePane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Sammy
 * @version 0.2
 */
public class GamerProfile_UI extends AuroraApp {

    private aImagePane imgGamerProfileBG;
    private JPanel ProfileHeader;
    private JPanel ContentPanel;
    private JLabel GamerName;
    private Font BigFont;
    private JLabel lblGamerName;
    private JPanel pnlGamerName;
    private JLabel TotalGaming;
    private JPanel pnlTotalGaming;
    private JLabel lblTotalGaming;
    private JLabel GamerType;
    private JLabel lblGamerType;
    private JPanel pnlGamerType;
    private JPanel FavePanel;
    private JPanel GameDataChunk;
    private JPanel BadgeDataChunk;
    private aImagePane faveGame;
    private JLabel faveLbl;
    private JPanel BadgeViewerPnl;
    private JPanel ProfileDataPanel;
    private aImagePane StarGame;
    private aImagePane Seprator;
    private JLabel TotalGamesLbl1;
    private JLabel TotalGamesLbl2;
    private JPanel TotalGamesPnl1;
    private JPanel GameCoverPanel;
    private JPanel BadgeDataContent;
    private JPanel GameDataContent;
    private aImagePane Seprator2;
    private JPanel TotalBadgePnl1;
    private JLabel TotalBadgeLbl1;
    private JLabel TotalBadgesLbl2;
    private JPanel TotalGamesPnl2;
    private JLabel TotalGamesLbl3;
    private JLabel TotalGamesLbl4;
    private aImagePane Spacer1;
    private aImagePane Spacer2;
    private JPanel TotalGamesPnl3;
    private JLabel TotalGamesLbl5;
    private JLabel TotalGamesLbl6;
    private JPanel TotalBadgePnl2;
    private JLabel TotalBadgeLbl4;
    private JLabel TotalBadgeLbl3;
    private JPanel TotalBadgePnl3;
    private JLabel TotalBadgeLbl5;
    private JLabel TotalBadgeLbl6;
    private aImagePane BadgesViewBG_EMPTY;
    private JPanel TotalBadgePnl4;
    private JLabel TotalBadgeLbl7;
    private JLabel TotalBadgeLbl8;
    private JPanel TotalBadgePnl5;
    private JLabel TotalBadgeLbl9;
    private JLabel TotalBadgeLbl10;
    private aImagePane Spacer3;
    private aImagePane Spacer4;
    private JPanel TotalGamesPnl4;
    private JLabel TotalGamesLbl7;
    private JLabel TotalGamesLbl8;
    private JPanel TotalGamesPnl5;
    private JLabel TotalGamesLbl9;
    private JLabel TotalGamesLbl10;
    private JPanel GameInfoPanel;
    private aImagePane RecentGameCover;
    private aImagePane MostTimeGameCover;
    private JPanel RecentGameInfoPanel;
    private JPanel MostTimeGameInfoPanel;
    private JPanel RecentGameContentPanel;
    private JPanel MostTimeGameContentPanel;
    private JPanel BadgeInfoPanel;
    private JPanel RecentBadgeInfoPanel;
    private JPanel CurrentBadgeInfoPanel;
    private aImagePane RecentBadge;
    private aImagePane CurrentBadge;
    private JPanel RecentBadgeContentPanel;
    private JPanel CurrentBadgeContentPanel;
    private int SIZE_BackgroundHeight;
    private int SIZE_BackgroundWidth;
    private int SIZE_BadgeViewHeight;
    private int SIZE_BadgeViewWidth;
    private int SIZE_BigFont;
    private int SIZE_StarIconWidth;
    private int SIZE_SeperatorHeight;
    private int SIZE_FaveFont;
    private int SIZE_SubHeaderFont;
    private int SIZE_HeaderFont;
  

    public GamerProfile_UI(Dashboard_UI dash_obj, AuroraCoreUI ui) {

        this.dash_Obj = dash_obj;
        this.ui = ui;
        this.clearUI_Forwards();
    }

    @Override
    public void createGUI() {
        //Set Up
        setSize();


        ///...Create Components

        BigFont = ui.getFontBold().deriveFont(Font.PLAIN, SIZE_BigFont);

        imgGamerProfileBG = new aImagePane("Aurora_ProfileBG.png", SIZE_BackgroundWidth, SIZE_BackgroundHeight, new BorderLayout());


        ////Gamer Name//////////////////////////////////
        pnlGamerName = new JPanel();
        pnlGamerName.setOpaque(false);

        lblGamerName = new JLabel("Gamer Name: ");
        lblGamerName.setForeground(Color.LIGHT_GRAY);
        lblGamerName.setFont(BigFont);

        GamerName = new JLabel(ui.getVi().VI(aXAVI.inx_User));
        GamerName.setForeground(Color.green);
        GamerName.setFont(BigFont);

        pnlGamerName.add(lblGamerName);
        pnlGamerName.add(GamerName);
        //---------------------------------------

        ///Total Game Time//////////////////////////////
        pnlTotalGaming = new JPanel();
        pnlTotalGaming.setOpaque(false);

        lblTotalGaming = new JLabel("Total Gaming Time: ");
        lblTotalGaming.setForeground(Color.LIGHT_GRAY);
        lblTotalGaming.setFont(BigFont);

        TotalGaming = new JLabel("Not Enough Data");
        TotalGaming.setForeground(Color.green);
        TotalGaming.setFont(BigFont);

        pnlTotalGaming.add(lblTotalGaming);
        pnlTotalGaming.add(TotalGaming);
        //---------------------------------------


        ///Gamer Type////////////////////////////////////
        pnlGamerType = new JPanel();
        pnlGamerType.setOpaque(false);

        lblGamerType = new JLabel("Type Of Gamer: ");
        lblGamerType.setForeground(Color.LIGHT_GRAY);
        lblGamerType.setFont(BigFont);

        GamerType = new JLabel("Not Enough Data");
        GamerType.setForeground(Color.green);
        GamerType.setFont(BigFont);

        pnlGamerType.add(lblGamerType);
        pnlGamerType.add(GamerType);
        //---------------------------------------



        ProfileHeader = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
        ProfileHeader.setOpaque(false);



        ///ADD TO HEADER
        ProfileHeader.add(pnlGamerName);
        ProfileHeader.add(pnlGamerType);
        ProfileHeader.add(pnlTotalGaming);




        ////....Content Panel


        //Seperator Icon////////////
        Seprator = new aImagePane("Seprator.png", 7, SIZE_SeperatorHeight);
        Seprator.setPreferredSize(new Dimension(7, SIZE_SeperatorHeight));

        Seprator2 = new aImagePane("Seprator.png", 7, SIZE_SeperatorHeight);
        Seprator2.setPreferredSize(new Dimension(7, SIZE_SeperatorHeight));


        //Spacer Icon////////////////
        Spacer1 = new aImagePane("Spacer.png", 17, 52);
        Spacer1.setPreferredSize(new Dimension(30, 52));

        Spacer2 = new aImagePane("Spacer.png", 17, 52);
        Spacer2.setPreferredSize(new Dimension(30, 52));

        Spacer3 = new aImagePane("Spacer.png", 17, 52);
        Spacer3.setPreferredSize(new Dimension(30, 52));

        Spacer4 = new aImagePane("Spacer.png", 17, 52);
        Spacer4.setPreferredSize(new Dimension(30, 52));
        ////////////////////



        //Favorite Game Panel//////////
        FavePanel = new JPanel();
        FavePanel.setLayout(new BoxLayout(FavePanel, BoxLayout.Y_AXIS));
        FavePanel.setOpaque(false);

        ////Game Cover Panel
        GameCoverPanel = new JPanel();
        GameCoverPanel.setOpaque(false);
        GameCoverPanel.setPreferredSize(new Dimension(190, 370));
        GameCoverPanel.setBackground(Color.red);

        ///Game Cover
        faveGame = new aImagePane("Blank-Case.png", 190, 220);
        faveGame.setPreferredSize(new Dimension(180, 220));

        //Star Cover
        StarGame = new aImagePane("FavoriteIcon.png");
        StarGame.setPreferredSize(new Dimension(SIZE_StarIconWidth, 50));

        faveGame.add(StarGame);


        ///Label

        faveLbl = new JLabel("              Current Favorite Game             ");
        faveLbl.setFont(ui.getFontBold().deriveFont(Font.PLAIN, SIZE_FaveFont));
        faveLbl.setForeground(Color.LIGHT_GRAY);

        GameCoverPanel.add(faveGame);
        GameCoverPanel.add(faveLbl);
        //GameCoverPanel.setOpaque(true);

        //ADD TO FAVE PANEL
        FavePanel.add(GameCoverPanel);

        /////////////////////////////End Fav Panel


        //....Game Data Panel/////////////////
        GameDataChunk = new JPanel();
        GameDataChunk.setOpaque(false);

        //Placeholder Covers
        //TODO code track
        RecentGameCover = new aImagePane("Blank-Case.png", 90, 110);
        MostTimeGameCover = new aImagePane("Blank-Case.png", 90, 110);
        RecentGameCover.setPreferredSize(new Dimension(85, 120));
        MostTimeGameCover.setPreferredSize(new Dimension(85, 120));
        //Add to panel





        //Game Data Content Panel//
        GameDataContent = new JPanel();
        GameDataContent.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        GameDataContent.setOpaque(false);
        GameDataContent.setPreferredSize(new Dimension(480, 370));


        ////InerContent Panel

        GameInfoPanel = new JPanel();
        GameInfoPanel.setOpaque(false);
        GameInfoPanel.setLayout(new BoxLayout(GameInfoPanel, BoxLayout.Y_AXIS));

        ///Text Panel 1///////////////
        TotalGamesPnl1 = new JPanel();
        TotalGamesPnl1.setLayout(new BoxLayout(TotalGamesPnl1, BoxLayout.X_AXIS));
        TotalGamesPnl1.setOpaque(false);

        //Text1
        TotalGamesLbl1 = new JLabel("Total Games: ");
        TotalGamesLbl1.setFont(ui.getFontBold().deriveFont(Font.PLAIN, SIZE_HeaderFont));
        TotalGamesLbl1.setForeground(Color.LIGHT_GRAY);
        //Text2
        TotalGamesLbl2 = new JLabel("N/A");
        TotalGamesLbl2.setFont(ui.getFontBold().deriveFont(Font.PLAIN, SIZE_HeaderFont));
        TotalGamesLbl2.setForeground(Color.green);
        //Add To Text Panel 1//
        TotalGamesPnl1.add(TotalGamesLbl1);
        TotalGamesPnl1.add(TotalGamesLbl2);




        ///Text Panel 2///////////////
        TotalGamesPnl2 = new JPanel();
        TotalGamesPnl2.setLayout(new BoxLayout(TotalGamesPnl2, BoxLayout.X_AXIS));
        TotalGamesPnl2.setOpaque(false);

        //Text3
        TotalGamesLbl3 = new JLabel("Most Recently Played Game: ");
        TotalGamesLbl3.setFont(ui.getFontBold().deriveFont(Font.PLAIN, SIZE_SubHeaderFont));
        TotalGamesLbl3.setForeground(Color.LIGHT_GRAY);
        //Text4
        TotalGamesLbl4 = new JLabel("N/A");
        TotalGamesLbl4.setFont(ui.getFontBold().deriveFont(Font.PLAIN, SIZE_SubHeaderFont));
        TotalGamesLbl4.setForeground(Color.green);
        //Add To Text Panel 2//

        TotalGamesPnl2.add(TotalGamesLbl3);
        TotalGamesPnl2.add(TotalGamesLbl4);


        ///Text Panel 3///////////////
        TotalGamesPnl3 = new JPanel();
        TotalGamesPnl3.setLayout(new BoxLayout(TotalGamesPnl3, BoxLayout.X_AXIS));
        TotalGamesPnl3.setOpaque(false);

        //Text5
        TotalGamesLbl5 = new JLabel("Last Time Played: ");
        TotalGamesLbl5.setFont(ui.getFontBold().deriveFont(Font.PLAIN, SIZE_SubHeaderFont));
        TotalGamesLbl5.setForeground(Color.LIGHT_GRAY);
        //Text6
        TotalGamesLbl6 = new JLabel("N/A");
        TotalGamesLbl6.setFont(ui.getFontBold().deriveFont(Font.PLAIN, SIZE_SubHeaderFont));
        TotalGamesLbl6.setForeground(Color.green);
        //Add To Text Panel 2//

        TotalGamesPnl3.add(Spacer1);
        TotalGamesPnl3.add(TotalGamesLbl5);
        TotalGamesPnl3.add(TotalGamesLbl6);


        ///Text Panel 4///////////////
        TotalGamesPnl4 = new JPanel();
        TotalGamesPnl4.setLayout(new BoxLayout(TotalGamesPnl4, BoxLayout.X_AXIS));
        TotalGamesPnl4.setOpaque(false);

        //Text5
        TotalGamesLbl7 = new JLabel("Most Time Spent On Game: ");
        TotalGamesLbl7.setFont(ui.getFontBold().deriveFont(Font.PLAIN, SIZE_SubHeaderFont));
        TotalGamesLbl7.setForeground(Color.LIGHT_GRAY);
        //Text6
        TotalGamesLbl8 = new JLabel("N/A");
        TotalGamesLbl8.setFont(ui.getFontBold().deriveFont(Font.PLAIN, SIZE_SubHeaderFont));
        TotalGamesLbl8.setForeground(Color.green);
        //Add To Text Panel 2//


        TotalGamesPnl4.add(TotalGamesLbl7);
        TotalGamesPnl4.add(TotalGamesLbl8);

        ///Text Panel 5///////////////
        TotalGamesPnl5 = new JPanel();
        TotalGamesPnl5.setLayout(new BoxLayout(TotalGamesPnl5, BoxLayout.X_AXIS));
        TotalGamesPnl5.setOpaque(false);

        //Text5
        TotalGamesLbl9 = new JLabel("Time Played: ");
        TotalGamesLbl9.setFont(ui.getFontBold().deriveFont(Font.PLAIN, SIZE_SubHeaderFont));
        TotalGamesLbl9.setForeground(Color.LIGHT_GRAY);
        //Text6
        TotalGamesLbl10 = new JLabel("N/A     ");
        TotalGamesLbl10.setFont(ui.getFontBold().deriveFont(Font.PLAIN, SIZE_SubHeaderFont));
        TotalGamesLbl10.setForeground(Color.green);
        //Add To Text Panel 2//

        TotalGamesPnl5.add(Spacer2);
        TotalGamesPnl5.add(TotalGamesLbl9);
        TotalGamesPnl5.add(TotalGamesLbl10);
        //////////////




        ////InerText Panels

        RecentGameInfoPanel = new JPanel();
        RecentGameInfoPanel.setOpaque(false);
        RecentGameInfoPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        RecentGameInfoPanel.setPreferredSize(new Dimension(370, 100));

        MostTimeGameInfoPanel = new JPanel();
        MostTimeGameInfoPanel.setOpaque(false);
        MostTimeGameInfoPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        MostTimeGameInfoPanel.setPreferredSize(new Dimension(370, 100));




        ///ADD TO CONTENT PANELS

        RecentGameInfoPanel.add(TotalGamesPnl2);
        RecentGameInfoPanel.add(TotalGamesPnl3);
        MostTimeGameInfoPanel.add(TotalGamesPnl4);
        MostTimeGameInfoPanel.add(TotalGamesPnl5);

        ////Add Game Cover To Info Panel
        RecentGameContentPanel = new JPanel();
        RecentGameContentPanel.setOpaque(false);
        RecentGameContentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        RecentGameContentPanel.setPreferredSize(new Dimension(480, 140));
        RecentGameContentPanel.add(RecentGameInfoPanel);
        RecentGameContentPanel.add(RecentGameCover);

        MostTimeGameContentPanel = new JPanel();
        MostTimeGameContentPanel.setOpaque(false);
        MostTimeGameContentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        MostTimeGameContentPanel.setPreferredSize(new Dimension(480, 140));
        MostTimeGameContentPanel.add(MostTimeGameInfoPanel);
        MostTimeGameContentPanel.add(MostTimeGameCover);


        //Add to Info Panel
        GameInfoPanel.add(RecentGameContentPanel);
        GameInfoPanel.add(MostTimeGameContentPanel);

        //Add Title To Content
        GameDataContent.add(TotalGamesPnl1);
        GameDataContent.add(GameInfoPanel);

        //Add Seperator To Whole Chunk
        GameDataChunk.add(Seprator);
        GameDataChunk.add(GameDataContent);


        //////////////End Game Data Panel



        //....BadgeData Panel/////////
        BadgeDataChunk = new JPanel();
        BadgeDataChunk.setOpaque(false);


        //Placeholder Covers

        RecentBadge = new aImagePane("Blank-Badge.png", 80, 80);
        CurrentBadge = new aImagePane("Blank-Badge.png", 80, 80);
        RecentBadge.setPreferredSize(new Dimension(80, 100));
        CurrentBadge.setPreferredSize(new Dimension(80, 100));
        //Add to panel


        //Game Data Content Panel//
        BadgeDataContent = new JPanel();
        BadgeDataContent.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        BadgeDataContent.setOpaque(false);
        BadgeDataContent.setPreferredSize(new Dimension(490, 370));


        //info Panel

        BadgeInfoPanel = new JPanel();
        BadgeInfoPanel.setOpaque(false);
        BadgeInfoPanel.setLayout(new BoxLayout(BadgeInfoPanel, BoxLayout.Y_AXIS));

        //Containing Text
        BadgeDataContent = new JPanel();
        BadgeDataContent.setOpaque(false);
        BadgeDataContent.setPreferredSize(new Dimension(450, 370));
        BadgeDataContent.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));


        ///Text Panel line 1///////////////
        TotalBadgePnl1 = new JPanel();
        TotalBadgePnl1.setLayout(new BoxLayout(TotalBadgePnl1, BoxLayout.X_AXIS));
        TotalBadgePnl1.setOpaque(false);

        //Text1
        TotalBadgeLbl1 = new JLabel("Total Badges: ");
        TotalBadgeLbl1.setFont(ui.getFontBold().deriveFont(Font.PLAIN, SIZE_HeaderFont));
        TotalBadgeLbl1.setForeground(Color.LIGHT_GRAY);
        //Text1
        TotalBadgesLbl2 = new JLabel("N/A");
        TotalBadgesLbl2.setFont(ui.getFontBold().deriveFont(Font.PLAIN, SIZE_HeaderFont));
        TotalBadgesLbl2.setForeground(Color.green);
        //Add To Text Panel 1//
        TotalBadgePnl1.add(TotalBadgeLbl1);
        TotalBadgePnl1.add(TotalBadgesLbl2);



        ///Text Panel 2///////////////
        TotalBadgePnl2 = new JPanel();
        TotalBadgePnl2.setLayout(new BoxLayout(TotalBadgePnl2, BoxLayout.X_AXIS));
        TotalBadgePnl2.setOpaque(false);

        //Text3
        TotalBadgeLbl3 = new JLabel("Most Recently Aquired Badge: ");
        TotalBadgeLbl3.setFont(ui.getFontBold().deriveFont(Font.PLAIN, SIZE_SubHeaderFont));
        TotalBadgeLbl3.setForeground(Color.LIGHT_GRAY);
        //Text4
        TotalBadgeLbl4 = new JLabel("N/A");
        TotalBadgeLbl4.setFont(ui.getFontBold().deriveFont(Font.PLAIN, SIZE_SubHeaderFont));
        TotalBadgeLbl4.setForeground(Color.green);
        //Add To Text Panel 2//

        TotalBadgePnl2.add(TotalBadgeLbl3);
        TotalBadgePnl2.add(TotalBadgeLbl4);


        ///Text Panel 3///////////////
        TotalBadgePnl3 = new JPanel();
        TotalBadgePnl3.setLayout(new BoxLayout(TotalBadgePnl3, BoxLayout.X_AXIS));
        TotalBadgePnl3.setOpaque(false);

        //Text5
        TotalBadgeLbl5 = new JLabel("Played For: ");
        TotalBadgeLbl5.setFont(ui.getFontBold().deriveFont(Font.PLAIN, SIZE_SubHeaderFont));
        TotalBadgeLbl5.setForeground(Color.LIGHT_GRAY);
        //Text6
        TotalBadgeLbl6 = new JLabel("N/A");
        TotalBadgeLbl6.setFont(ui.getFontBold().deriveFont(Font.PLAIN, SIZE_SubHeaderFont));
        TotalBadgeLbl6.setForeground(Color.green);
        //Add To Text Panel 2//

        TotalBadgePnl3.add(Spacer3);
        TotalBadgePnl3.add(TotalBadgeLbl5);
        TotalBadgePnl3.add(TotalBadgeLbl6);

        ///Text Panel 4///////////////
        TotalBadgePnl4 = new JPanel();
        TotalBadgePnl4.setLayout(new BoxLayout(TotalBadgePnl4, BoxLayout.X_AXIS));
        TotalBadgePnl4.setOpaque(false);

        //Text5
        TotalBadgeLbl7 = new JLabel("Current Badge Level: ");
        TotalBadgeLbl7.setFont(ui.getFontBold().deriveFont(Font.PLAIN, SIZE_SubHeaderFont));
        TotalBadgeLbl7.setForeground(Color.LIGHT_GRAY);
        //Text6
        TotalBadgeLbl8 = new JLabel("N/A        ");
        TotalBadgeLbl8.setFont(ui.getFontBold().deriveFont(Font.PLAIN, SIZE_SubHeaderFont));
        TotalBadgeLbl8.setForeground(Color.green);
        //Add To Text Panel 2//

        TotalBadgePnl4.add(TotalBadgeLbl7);
        TotalBadgePnl4.add(TotalBadgeLbl8);

        ///Text Panel 5///////////////
        TotalBadgePnl5 = new JPanel();
        TotalBadgePnl5.setLayout(new BoxLayout(TotalBadgePnl5, BoxLayout.X_AXIS));
        TotalBadgePnl5.setOpaque(false);

        //Text5
        TotalBadgeLbl9 = new JLabel("Played For: ");
        TotalBadgeLbl9.setFont(ui.getFontBold().deriveFont(Font.PLAIN, SIZE_SubHeaderFont));
        TotalBadgeLbl9.setForeground(Color.LIGHT_GRAY);
        //Text6
        TotalBadgeLbl10 = new JLabel("N/A");
        TotalBadgeLbl10.setFont(ui.getFontBold().deriveFont(Font.PLAIN, SIZE_SubHeaderFont));
        TotalBadgeLbl10.setForeground(Color.green);
        //Add To Text Panel 2//
        TotalBadgePnl5.add(Spacer4);
        TotalBadgePnl5.add(TotalBadgeLbl9);
        TotalBadgePnl5.add(TotalBadgeLbl10);
////////////////////



        ////InerText Panels

        RecentBadgeInfoPanel = new JPanel();
        RecentBadgeInfoPanel.setOpaque(false);
        RecentBadgeInfoPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        RecentBadgeInfoPanel.setPreferredSize(new Dimension(350, 100));

        CurrentBadgeInfoPanel = new JPanel();
        CurrentBadgeInfoPanel.setOpaque(false);
        CurrentBadgeInfoPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        CurrentBadgeInfoPanel.setPreferredSize(new Dimension(350, 100));


        //Add To Text Panels

        RecentBadgeInfoPanel.add(TotalBadgePnl2);
        RecentBadgeInfoPanel.add(TotalBadgePnl3);
        CurrentBadgeInfoPanel.add(TotalBadgePnl4);
        CurrentBadgeInfoPanel.add(TotalBadgePnl5);

        ////Add Game Cover To Info Panel
        RecentBadgeContentPanel = new JPanel();
        RecentBadgeContentPanel.setOpaque(false);
        RecentBadgeContentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        RecentBadgeContentPanel.setPreferredSize(new Dimension(480, 140));
        RecentBadgeContentPanel.add(RecentBadgeInfoPanel);
        RecentBadgeContentPanel.add(RecentBadge);


        CurrentBadgeContentPanel = new JPanel();
        CurrentBadgeContentPanel.setOpaque(false);
        CurrentBadgeContentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        CurrentBadgeContentPanel.setPreferredSize(new Dimension(480, 140));
        CurrentBadgeContentPanel.add(CurrentBadgeInfoPanel);
        CurrentBadgeContentPanel.add(CurrentBadge);




        //Add Text to content

        BadgeInfoPanel.add(RecentBadgeContentPanel);
        BadgeInfoPanel.add(CurrentBadgeContentPanel);

        BadgeDataContent.add(TotalBadgePnl1);
        BadgeDataContent.add(BadgeInfoPanel);

        BadgeDataChunk.add(Seprator2);
        BadgeDataChunk.add(BadgeDataContent);


        //Add to Badge Data Panel


        ///////////End Badge Data Panel

        //Badge Component
        BadgeViewerPnl = new JPanel();
        BadgeViewerPnl.setOpaque(false);

        BadgesViewBG_EMPTY = new aImagePane("BadgesViewer_none.png", SIZE_BadgeViewWidth, SIZE_BadgeViewHeight);
        BadgesViewBG_EMPTY.setPreferredSize(new Dimension(SIZE_BadgeViewWidth, SIZE_BadgeViewHeight));



        BadgeViewerPnl.add(BadgesViewBG_EMPTY);

        //...Profile containing Data /////
        ProfileDataPanel = new JPanel();
        ProfileDataPanel.setLayout(new GridLayout(0, 3, 0, 0));
        ProfileDataPanel.setOpaque(false);

        //Add to Profile Data Pnl
        ProfileDataPanel.add(FavePanel);
        ProfileDataPanel.add(GameDataChunk);
        ProfileDataPanel.add(BadgeDataChunk);


        //...Profile Conent/////
        ContentPanel = new JPanel();
        ContentPanel.setOpaque(false);

        ///ADD TO CONTENT PANEL
        ContentPanel.add(ProfileDataPanel);
        ContentPanel.add(BadgeViewerPnl);


        ////////////////////////////////////
        //...Main Aurora Center Panel///////



        imgGamerProfileBG.add(BorderLayout.PAGE_START, ProfileHeader);
        imgGamerProfileBG.add(BorderLayout.CENTER, ContentPanel);

        ///...Set Up UI

        ui.getPnlCenter().add(imgGamerProfileBG);
        ui.getPnlCenter().repaint();


        //Finalize
        ui.getLblInfo().setText(" Gamer Profile ");

    }

    public void setSize() {
        if (ui.isLargeScreen()) {
            SIZE_BackgroundHeight = ui.getPnlCenter().getHeight();
            SIZE_BackgroundWidth = ui.getPnlCenter().getWidth();
            SIZE_BadgeViewHeight = 200;
            SIZE_BadgeViewWidth = 1000;
            SIZE_BigFont = 43;
            SIZE_StarIconWidth = 170;
            SIZE_SeperatorHeight = 370;
            SIZE_FaveFont = 33;
            SIZE_SubHeaderFont = 31;
            SIZE_HeaderFont = 70;
        } else {
            SIZE_BackgroundHeight = ui.getPnlCenter().getHeight() + 40;
            SIZE_BackgroundWidth = ui.getPnlCenter().getWidth();
            SIZE_BadgeViewHeight = 150;
            SIZE_BadgeViewWidth = 950;
            SIZE_BigFont = 40;
            SIZE_StarIconWidth = 170;
            SIZE_SeperatorHeight = 350;
            SIZE_FaveFont = 30;
            SIZE_SubHeaderFont = 29;
            SIZE_HeaderFont = 65;
        }
    }
}
