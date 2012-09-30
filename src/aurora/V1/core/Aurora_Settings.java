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

import aurora.engine.V1.UI.aRadioButtonManager;
import aurora.engine.V1.UI.aButton;
import aurora.engine.V1.UI.aImage;
import aurora.engine.V1.UI.aImagePane;
import aurora.engine.V1.UI.aRadioButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.*;

/**
 * Settings GUI
 * @author Sammy
 */
public class Aurora_Settings extends AuroraApp {

    private aImagePane imgSettingsBG;
    private int SIZE_BigFont;
    private int SIZE_BackgroundHeight;
    private int SIZE_BackgroundWidth;
    private int SIZE_RowWidth;
    private int SIZE_RowHeight;
    private Font BigFont;
    private aImage imgKeyIco;
    private JLabel lblKeyAction;


    public Aurora_Settings(Aurora_Dashboard dash_obj, AuroraCoreUI ui) {

        this.dash_Obj = dash_obj;
        this.ui = ui;
        this.clearUI_Forwards();
    }

    @Override
    public void createGUI() {
        setSize();
        BigFont = ui.getFontRegular().deriveFont(Font.PLAIN, SIZE_BigFont);
        imgSettingsBG = new aImagePane("Aurora_SettingsBG.png", SIZE_BackgroundWidth, SIZE_BackgroundHeight, new BorderLayout());
        imgSettingsBG.setLayout(new BoxLayout(imgSettingsBG, BoxLayout.Y_AXIS));

        ui.getLblInfo().setText("  Settings  ");
        ui.getPnlCenter().add(imgSettingsBG);
        
        //Key Actions Panel

        imgKeyIco = new aImage("KeyboardKeys/enter.png");
        lblKeyAction = new JLabel(" Apply ");
        lblKeyAction.setFont(ui.getDefaultFont().deriveFont(Font.PLAIN,25));
        lblKeyAction.setForeground(Color.YELLOW);

        ui.getPnlKeyToPress().add(imgKeyIco);
        ui.getPnlKeyToPress().add(lblKeyAction);


        //////////SEPERATORS
        
        aImage Seperator1 = new aImage("Bar.png");
        Seperator1.setW(SIZE_RowWidth);
        
        aImage Seperator2 = new aImage("Bar.png");
        Seperator2.setW(SIZE_RowWidth);
        
        aImage Seperator3 = new aImage("Bar.png");
        Seperator3.setW(SIZE_RowWidth);
        
        aImage Seperator4 = new aImage("Bar.png");
        Seperator4.setW(SIZE_RowWidth);
        
        JPanel SeperatorPnl1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        SeperatorPnl1.add(Seperator1);
        SeperatorPnl1.setOpaque(false);
        SeperatorPnl1.setPreferredSize(new Dimension(SIZE_RowWidth, Seperator1.getImgHeight()));
        
        JPanel SeperatorPnl2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        SeperatorPnl2.add(Seperator2);
        SeperatorPnl2.setOpaque(false);
        SeperatorPnl2.setPreferredSize(new Dimension(SIZE_RowWidth, Seperator2.getImgHeight()));
        
        JPanel SeperatorPnl3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        SeperatorPnl3.add(Seperator3);
        SeperatorPnl3.setOpaque(false);
        SeperatorPnl3.setPreferredSize(new Dimension(SIZE_RowWidth, Seperator3.getImgHeight()));
        
        JPanel SeperatorPnl4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        SeperatorPnl4.add(Seperator4);
        SeperatorPnl4.setOpaque(false);
        SeperatorPnl4.setPreferredSize(new Dimension(SIZE_RowWidth, Seperator4.getImgHeight()+25));


        ///Row 1
        JPanel row1 = new JPanel();
        row1.setOpaque(false);
        row1.setLayout(new BoxLayout(row1, BoxLayout.X_AXIS));
        row1.setPreferredSize(new Dimension(SIZE_RowWidth, SIZE_RowHeight));

 

        JPanel row1Controls = new JPanel();
        row1Controls.setOpaque(false);
        row1Controls.setLayout(new FlowLayout(FlowLayout.LEADING, 50, 10));
       
        
        
        aRadioButton rdHighResolution = new aRadioButton("Aurora_OnBtn_Up.png", "Aurora_OnBtn_Over.png",ui.getFrame());
        aRadioButton rdLowResolution = new aRadioButton("Aurora_OffBtn_Up.png", "Aurora_OffBtn_Over.png",ui.getFrame());
        
        //Radio Button Manager
        
        aRadioButtonManager mngResolution = new aRadioButtonManager();
        
        mngResolution.addButton(rdHighResolution);
        mngResolution.addButton(rdLowResolution);
        mngResolution.setRadioButton();
        
        aImage imgScreen = new aImage("Aurora_Screen_Logo.png");
        
        
        
        JLabel lblScreenResolution = new JLabel("     Screen Resolution                    ");
        lblScreenResolution.setFont(BigFont);
        lblScreenResolution.setForeground(Color.LIGHT_GRAY);
 


        //Add Row 1

        row1Controls.add(rdHighResolution);
        row1Controls.add(imgScreen);
        row1Controls.add(rdLowResolution);

        row1.add(lblScreenResolution);
        row1.add(row1Controls);




        //Row 2
        JPanel row2 = new JPanel();
        row2.setOpaque(false);
        row2.setLayout(new BoxLayout(row2, BoxLayout.X_AXIS));
        row2.setPreferredSize(new Dimension(SIZE_RowWidth, SIZE_RowHeight));
        
        JPanel row2Controls = new JPanel();
        row2Controls.setOpaque(false);
        row2Controls.setLayout(new FlowLayout(FlowLayout.LEADING, 50, 10));

        
        
        aRadioButton rdSFXOn = new aRadioButton("Aurora_OnBtn_Up.png", "Aurora_OnBtn_Over.png",ui.getFrame());
        aRadioButton rdSFXOff = new aRadioButton("Aurora_OffBtn_Up.png", "Aurora_OffBtn_Over.png",ui.getFrame());
        
        
        
        //Radio Button Manager
        
        aRadioButtonManager mngSoundFX = new aRadioButtonManager();
        
        mngSoundFX.addButton(rdSFXOn);
        mngSoundFX.addButton(rdSFXOff);
        mngSoundFX.setRadioButton();
        
        
        
        aImage imgSoundFX = new aImage("Aurora_Sound_Logo.png");
        
        
        JLabel lblSoundEffects = new JLabel("     Sound Effects                           ");
        lblSoundEffects.setFont(BigFont);
        lblSoundEffects.setForeground(Color.LIGHT_GRAY);
        
        

        //Add to row2
        
        row2Controls.add(rdSFXOn);
        row2Controls.add(imgSoundFX);
        row2Controls.add(rdSFXOff);
        
        row2.add( lblSoundEffects);
        row2.add( row2Controls);


        
        
        //Row 3
        JPanel row3 = new JPanel();
        row3.setOpaque(false);
        row3.setLayout(new BoxLayout(row3, BoxLayout.X_AXIS));
        row3.setPreferredSize(new Dimension(SIZE_RowWidth, SIZE_RowHeight));
        
        JPanel row3Controls = new JPanel();
        row3Controls.setOpaque(false);
        row3Controls.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 10));
        
        
        aRadioButton rdBSoundOn = new aRadioButton("Aurora_OnBtn_Up.png", "Aurora_OnBtn_Over.png",ui.getFrame());
        aRadioButton rdBSoundOff = new aRadioButton("Aurora_OffBtn_Up.png", "Aurora_OffBtn_Over.png",ui.getFrame());
        
        
        
        //Radio Button Manager
        
        aRadioButtonManager mngBSound = new aRadioButtonManager();
        
        mngBSound.addButton(rdBSoundOn);
        mngBSound.addButton(rdBSoundOff);
        mngBSound.setRadioButton();
        
        
        
        aImage imgSoundBG = new aImage("Aurora_Sound_Logo.png");
        
        

        JLabel lblSoundBackground = new JLabel("     Background Music                    ");
        lblSoundBackground.setFont(BigFont);
        lblSoundBackground.setForeground(Color.LIGHT_GRAY);

        //Add to Row 3
                
        
        row3Controls.add(rdBSoundOn);
        row3Controls.add(imgSoundBG);
        row3Controls.add(rdBSoundOff);
        
        row3.add( lblSoundBackground);
        row3.add( row3Controls);


        

        //Row 4
        JPanel row4 = new JPanel();
        row4.setOpaque(false);
        row4.setLayout(new BoxLayout(row4, BoxLayout.X_AXIS));
        row4.setPreferredSize(new Dimension(SIZE_RowWidth, SIZE_RowHeight));
        
        
        JPanel row4Controls = new JPanel();
        row4Controls.setOpaque(false);
        row4Controls.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 10));

        
        
//        aRadioButton rdSurfaceOn = new aRadioButton("Aurora_OnBtn_Up.png", "Aurora_OnBtn_Over.png",ui.getFrame());
//        aRadioButton rdSurfaceOff = new aRadioButton("Aurora_OffBtn_Up.png", "Aurora_OffBtn_Over.png",ui.getFrame());
//        
//        
         
        //Radio Button Manager
        
//        aRadioButtonManager mngSurface = new aRadioButtonManager();
//        
//        mngSurface.addButton(rdSurfaceOn);
//        mngSurface.addButton(rdSurfaceOff);
//        mngSurface.setRadioButton();
        
        
        aImage btnSurfaceOff = new aImage("Aurora_OffBtn_Disabled.png");
        aImage btnSurfaceOn = new aImage("Aurora_OnBtn_Disabled.png");
        
         aImage imgCommingSoon = new aImage("Aurora_CommingSoon_Logo.png");
        

        aImage AuroraSurface = new aImage("AuroraSurface.png");

        
        //Add row 4
        
        row4Controls.add(btnSurfaceOn);
        row4Controls.add(imgCommingSoon);
        row4Controls.add(btnSurfaceOff);
        
        row4.add( AuroraSurface);
        row4.add(row4Controls);






        ///ADD TO SETTINGS PANEL
        imgSettingsBG.add(row1);
        imgSettingsBG.add(SeperatorPnl1);
        imgSettingsBG.add(row2);
        imgSettingsBG.add(SeperatorPnl2);
        imgSettingsBG.add(row3);
        imgSettingsBG.add(SeperatorPnl3);
        imgSettingsBG.add(row4);
        imgSettingsBG.add(SeperatorPnl4);


        aButton btnApply = new aButton("Aurora_Apply_normal.png","Aurora_Apply_down.png","Aurora_Apply_over.png");
        
        ui.getPnlUserSpace().setLayout(new BorderLayout());
        ui.getPnlUserSpace().setVisible(true);
        ui.getPnlCenterFromBottom().add(BorderLayout.CENTER, ui.getPnlUserSpace());
        
        
        ui.getPnlUserSpace().removeAll();
        
        ui.getPnlUserSpace().add( btnApply);
        ui.getPnlUserSpace().revalidate();
        
        ui.getFrame().repaint();
        // ui.getFrame().validate();
        
        
    }

    public void setSize() {
        if (ui.isLargeScreen()) {
            SIZE_BackgroundHeight = ui.getPnlCenter().getHeight();
            SIZE_BackgroundWidth = ui.getPnlCenter().getWidth();
            SIZE_BigFont = 77;
            SIZE_RowWidth = (ui.getPnlCenter().getWidth()) - 140;
            SIZE_RowHeight = 40;
        } else {
            SIZE_BackgroundHeight = ui.getPnlCenter().getHeight() + 40;
            SIZE_BackgroundWidth = ui.getPnlCenter().getWidth();
            SIZE_BigFont = 70;
            SIZE_RowWidth = (ui.getPnlCenter().getWidth()) - 170;
            SIZE_RowHeight = 190;
        }

    }
}
