/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aurora.V1.core.screen_ui;

import aurora.V1.core.AuroraCoreUI;
import aurora.V1.core.AuroraStorage;
import aurora.V1.core.Game;
import aurora.V1.core.screen_handler.LibraryHandler;
import aurora.V1.core.screen_logic.LibraryLogic;
import aurora.V1.core.screen_logic.SettingsLogic;
import aurora.engine.V1.Logic.AAnimate;
import aurora.engine.V1.Logic.AFileDrop;
import aurora.engine.V1.Logic.APostHandler;
import aurora.engine.V1.Logic.ASound;
import aurora.engine.V1.Logic.AThreadWorker;
import aurora.engine.V1.UI.AButton;
import aurora.engine.V1.UI.AImage;
import aurora.engine.V1.UI.AImagePane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JWindow;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class EditCoverUI {

    private final AuroraCoreUI coreUI;

    private final LibraryUI libraryUI;

    private final LibraryHandler libraryHandler;

    private final LibraryLogic libraryLogic;

    private final AuroraStorage auroraStorage;

    private AAnimate editGameCoverAnimator;

    private AImagePane pnlEditGameCoverPane;

    private JPanel pnlBottomPane;

    private AButton btnClose;

    private JPanel pnlCenterPane_editCoverUI;

    private AImagePane pnlDrag_editCoverUI;

    private AImage imgEditGameCoverStatus;

    private JPanel pnlTopPane_editCoverUI;

    private AImage imgTopArrows;

    private AButton btnDone;

    private JPanel pnlRightPane;

    private JPanel pnlLeftPane_editCoverUI;

    private JPanel pnlContent;

    private AAnimate editGameCoverFrameAnimator;

    private JWindow frameEditGameCoverPane;

    private ActionListener closeEditCoverListener;

    private JPanel pnlGlass;

    private AFileDrop fileDrop;

    private LibraryHandler.EditCoverUIDragedListener fileDragedListener;

    // Array of supported images to be droped and used
    ArrayList<String> supportedImages = new ArrayList<>(
            Arrays.asList("png", "jpg", "jpeg", "gif", "bmp"));

    private boolean editGameCoverUILoaded;

    public EditCoverUI(AuroraCoreUI coreUI, LibraryUI libraryUI) {
        this.coreUI = coreUI;
        this.libraryUI = libraryUI;
        this.libraryHandler = libraryUI.getHandler();
        this.libraryLogic = libraryUI.getLogic();
        this.auroraStorage = libraryUI.getDashboardUI().getStorage();
    }

    public void loadEditGameCoverUI() {


        // Create Main Components
        // ----------------------------------------------------------------.

        //
        // Get Glass Pane to Put UI On
        //
        pnlGlass = (JPanel) coreUI.getFrame().getGlassPane();

        // Create Components
        // ----------------------------------------------------------------.
        frameEditGameCoverPane = new JWindow();
        pnlEditGameCoverPane = new AImagePane("editCoverUI_bg.png",
                                              new BorderLayout());
        pnlEditGameCoverPane.setPreferredSize(new Dimension(pnlEditGameCoverPane
                .getRealImageWidth(), pnlEditGameCoverPane.getRealImageHeight()));

        // Top Panel Components
        pnlTopPane_editCoverUI = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlTopPane_editCoverUI.setOpaque(false);
        imgTopArrows = new AImage("editCoverUI_arrows.png");
        imgTopArrows.setPreferredSize(new Dimension(imgTopArrows.getImgWidth(),
                                                    imgTopArrows.getImgHeight()));

        // Bottom Panel Components
        pnlBottomPane = new JPanel(new FlowLayout(FlowLayout.RIGHT,
                                                  10,
                                                  3));
        pnlBottomPane.setOpaque(false);
        btnClose = new AButton("editCoverUI_btnClose_norm.png",
                               "editCoverUI_btnClose_down.png",
                               "editCoverUI_btnClose_over.png");
        closeEditCoverListener = libraryHandler.new CloseEditCoverListener(
                frameEditGameCoverPane);

        // Right Panel Components
        pnlRightPane = new JPanel();
        pnlRightPane.setLayout(new BoxLayout(
                pnlRightPane, BoxLayout.Y_AXIS));
        pnlRightPane.setOpaque(false);
        btnDone = new AButton("editCoverUI_btnDone_norm.png",
                              "editCoverUI_btnDone_down.png",
                              "editCoverUI_btnDone_over.png");


        // Left Panel Components
        pnlLeftPane_editCoverUI = new JPanel();
        pnlLeftPane_editCoverUI.setLayout(new BoxLayout(pnlLeftPane_editCoverUI,
                                                        BoxLayout.Y_AXIS));
        pnlLeftPane_editCoverUI.setOpaque(false);
        imgEditGameCoverStatus = new AImage("addUI_badge_invalid.png");

        // Center Panel
        pnlCenterPane_editCoverUI = new JPanel(new FlowLayout(FlowLayout.CENTER,
                                                              0, 8));
        pnlCenterPane_editCoverUI.setOpaque(false);


        // Drag Pane
        pnlDrag_editCoverUI = new AImagePane("editCoverUI_dragBG.png");
        pnlDrag_editCoverUI.setPreferredSize(new Dimension(pnlDrag_editCoverUI
                .getRealImageWidth() + 5, pnlDrag_editCoverUI
                                                           .getRealImageHeight()));

        fileDragedListener = libraryHandler.new EditCoverUIDragedListener(
                pnlDrag_editCoverUI, imgEditGameCoverStatus);

        fileDrop = new AFileDrop(pnlDrag_editCoverUI,
                                 "editCoverUI_dropBG.png",
                                 "editCoverUI_rejectBG.png", true,
                                 fileDragedListener, supportedImages);

        pnlDrag_editCoverUI.addMouseListener(new MouseHoverListener());

        // Content Panel
        pnlContent = new JPanel(new BorderLayout());
        pnlContent.setOpaque(false);
        frameEditGameCoverPane.addMouseListener(new MouseHoverListener());
    }

    private class MouseHoverListener extends MouseAdapter {

        @Override
        public void mouseEntered(MouseEvent e) {
            frameEditGameCoverPane.setAlwaysOnTop(true);
            frameEditGameCoverPane.requestFocusInWindow();
            frameEditGameCoverPane.setAlwaysOnTop(false);
        }

    }

    public void hideEditCoverFrame() {
        closeEditCoverListener.actionPerformed(null);
    }

    public void buildEditGameCoverUI(Game game) {

        // Check if loading for the first time
        if (!editGameCoverUILoaded) {

            // Set up glass panel
            frameEditGameCoverPane.setAutoRequestFocus(true);
            frameEditGameCoverPane.setBackground(new Color(0, 0, 0, 0));
            frameEditGameCoverPane.setContentPane(new ShapedPane());

            btnDone.addActionListener(
                    libraryHandler.new EditCoverUIDoneListener(
                            imgEditGameCoverStatus, game, fileDragedListener));

            frameEditGameCoverPane.setSize(pnlEditGameCoverPane
                    .getRealImageWidth(), pnlEditGameCoverPane
                                           .getRealImageHeight() + 50 + coreUI
                                           .getTaskbarHeight());

            // Set Location for Edit Game UI panels
            frameEditGameCoverPane.setLocation(
                    (coreUI.getFrame().getWidth() / 2) - (pnlEditGameCoverPane
                    .getRealImageWidth() / 2),
                    coreUI.getScreenHeight());

            // Top
            pnlTopPane_editCoverUI.add(imgTopArrows);

            // Bottom
            pnlBottomPane.add(btnClose);
            btnClose.addActionListener(closeEditCoverListener);

            // Center
            int centerHeight = pnlDrag_editCoverUI.getRealImageHeight()
                               + (pnlEditGameCoverPane
                    .getRealImageHeight() - (imgTopArrows
                    .getImgHeight() + pnlBottomPane
                    .getPreferredSize().height));

            pnlCenterPane_editCoverUI.add(pnlDrag_editCoverUI);
            pnlCenterPane_editCoverUI.setPreferredSize(new Dimension(
                    pnlDrag_editCoverUI.getRealImageWidth(), pnlDrag_editCoverUI
                    .getRealImageHeight()));


            fileDrop.setupFileDrop();

            // Right
            JPanel rightPaneContainer = new JPanel(new FlowLayout(
                    FlowLayout.CENTER));
            rightPaneContainer.setOpaque(false);
            rightPaneContainer.add(btnDone);

            rightPaneContainer.setAlignmentY(JComponent.CENTER_ALIGNMENT);

            pnlRightPane.add(Box.createVerticalGlue());
            pnlRightPane.add(rightPaneContainer);
            pnlRightPane.setPreferredSize(new Dimension(
                    (pnlEditGameCoverPane.getRealImageWidth() / 5),
                    centerHeight));

            // Left
            JPanel leftPaneContainer = new JPanel(new FlowLayout(
                    FlowLayout.CENTER));
            leftPaneContainer.setOpaque(false);
            leftPaneContainer.add(imgEditGameCoverStatus);

            leftPaneContainer.setAlignmentY(JComponent.CENTER_ALIGNMENT);

            pnlLeftPane_editCoverUI.add(Box
                    .createVerticalStrut(centerHeight / 6));
            pnlLeftPane_editCoverUI.add(leftPaneContainer);
            pnlLeftPane_editCoverUI.setPreferredSize(pnlRightPane
                    .getPreferredSize());

            // Content Pane
            pnlContent.add(pnlCenterPane_editCoverUI,
                           BorderLayout.CENTER);
            pnlContent.add(pnlTopPane_editCoverUI,
                           BorderLayout.NORTH);
            pnlContent.add(pnlRightPane,
                           BorderLayout.EAST);
            pnlContent.add(pnlLeftPane_editCoverUI,
                           BorderLayout.WEST);

            pnlEditGameCoverPane
                    .add(pnlContent, BorderLayout.CENTER);
            pnlEditGameCoverPane.add(pnlBottomPane,
                                     BorderLayout.PAGE_END);

            frameEditGameCoverPane.getContentPane().add(pnlEditGameCoverPane,
                                                        BorderLayout.PAGE_START);

            pnlEditGameCoverPane.revalidate();
            frameEditGameCoverPane.revalidate();
            editGameCoverUILoaded = true;
        } else {
            // If not then set Done button listener and reset everything
            btnDone.removeActionListener(btnDone
                    .getActionListeners()[0]);
            btnDone.addActionListener(
                    libraryHandler.new EditCoverUIDoneListener(
                            imgEditGameCoverStatus, game, fileDragedListener));


            pnlDrag_editCoverUI.removeAll();
            pnlDrag_editCoverUI.setImage("editCoverUI_dragBG.png");
            pnlDrag_editCoverUI.revalidate();

            imgEditGameCoverStatus.setImgURl("addUI_badge_invalid.png");

            fileDrop.setupFileDrop();
            fileDragedListener.setIsOccupied(false);

        }


    }

    public class ShapedPane extends JPanel {

        public ShapedPane() {

            setOpaque(false);
            setLayout(new BorderLayout());
            setBackground(new Color(45, 55, 73));

        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g.create();
            RenderingHints hints = new RenderingHints(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHints(hints);
            g2d.setColor(getBackground());
            g2d.fill(new RoundRectangle2D.Double(0, 0,
                                                 frameEditGameCoverPane
                                                 .getWidth(),
                                                 frameEditGameCoverPane
                                                 .getHeight(), 69,
                                                 59));
            g2d.dispose();
        }
    }

    public void showEditGameCoverUI(final Game game) {

        pnlGlass.setVisible(true);

        editGameCoverFrameAnimator = new AAnimate();
        editGameCoverAnimator = new AAnimate(
                frameEditGameCoverPane);

        String soundEffectsSetting = auroraStorage.getStoredSettings()
                .getSettingValue(SettingsLogic.SFX_SETTING);
        if (soundEffectsSetting == null) {
            soundEffectsSetting = SettingsLogic.DEFAULT_SFX_SETTING;
        }

        if (soundEffectsSetting.equals("enabled")) {
            int num = 1 + (int) (Math.random() * ((3 - 1) + 1));
            ASound showSound = new ASound("swoop_" + num + ".wav", false);
            showSound.Play();
        }

        AThreadWorker editGameCoverWorker = new AThreadWorker(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        // Set up UI
                        buildEditGameCoverUI(game);

                        editGameCoverAnimator
                        .setInitialLocation((coreUI.getFrame().getWidth() / 2)
                                            - (pnlEditGameCoverPane
                                .getRealImageWidth() / 2),
                                            coreUI.getScreenHeight());

                    }

                }, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        // Show Edit Cover UI
                        editGameCoverFrameAnimator.fadeOut(coreUI.getFrame());

                        editGameCoverFrameAnimator.addPostAnimationListener(
                                new APostHandler() {
                                    @Override
                                    public void doAction() {

                                        coreUI.getFrame().setVisible(false);

                                        libraryLogic
                                        .setupDesktopEnvironmentForCoverArtEdit();
                                        try {
                                            Thread.sleep(400);
                                        } catch (InterruptedException ex) {
                                            java.util.logging.Logger.getLogger(
                                                    LibraryUI.class.getName())
                                            .log(Level.SEVERE, null, ex);
                                        }

                                        editGameCoverAnimator.moveVertical(
                                                coreUI.getScreenHeight()
                                                - pnlEditGameCoverPane
                                                .getRealImageHeight() - (coreUI
                                                .getTaskbarHeight() - 2),
                                                -18);

                                    }
                                });

                        pnlEditGameCoverPane.revalidate();

                    }
                });

        editGameCoverWorker.startOnce();

        libraryUI.setIsEditGameCoverUI_visible(true);
    }

    //
    // Getter and Setter
    //
    public boolean isEditGameCoverUILoaded() {
        return editGameCoverUILoaded;
    }

    public void setIsEditGameCoverUILoaded(boolean isEditGameCoverUILoaded) {
        this.editGameCoverUILoaded = isEditGameCoverUILoaded;
    }

}
