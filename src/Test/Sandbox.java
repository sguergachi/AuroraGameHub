package Test;

import aurora.engine.V1.Logic.ASurface;
import aurora.engine.V1.UI.AImage;
import java.io.IOException;

/**
 *
 * @author Sammy
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Sandbox {

    public static void main(String[] args) throws IOException {

        Sandbox test = new Sandbox();

        test.gui2();


    }
    private JFileChooser fc;

    private JPanel RightBottomCenter;

    private Cursor eraserCursor;

    public void gui2() {

        int size = 32;

        /*
         * we need two buffered images as the cursor only supports on/off for alpha
         *
         * so we need to draw to an image without alpha support
         * then draw that to one with alpha support
         * then make "white" transparent
         */
        BufferedImage image = new BufferedImage(size, size,
                BufferedImage.TYPE_INT_RGB);
        BufferedImage image2 = new BufferedImage(size, size,
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = image.createGraphics();
        Graphics2D g2 = image2.createGraphics();

        g.setColor(Color.white);
        g.fillRect(0, 0, size, size);


        // turn on anti-aliasing.
        g.setStroke(new BasicStroke(4.0f)); // 4-pixel lines
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(new Color(0.5f, 0f, 0f));
        g.drawOval(3, 3, size - 7, size - 7);

        g2.drawImage(image, 0, 0, null, null);


        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {

                int rgb = image.getRGB(x, y);

                int blue = rgb & 0xff;
                int green = (rgb & 0xff00) >> 8;
                int red = (rgb & 0xff0000) >> 16;
                //int alpha = (rgb & 0xff000000) >> 24;

                if (red == 255 && green == 255 && blue == 255) {
                    // make white transparent
                    image2.setRGB(x, y, 0);
                }

            }
        }

        eraserCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                image2, new Point(size / 2, size / 2), "eraserCursor");

        JFrame frame = new JFrame("Test");
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);

        frame.setCursor(eraserCursor);
    }

    public void gui() {

        JFrame frame = new JFrame("Test");
        frame.getContentPane().setBackground(Color.black);

        AImage cursorImage = new AImage("cursor.png");
        AImage cursorSelect = new AImage("cursorSelect.png");
        AImage cursorHover = new AImage("cursorHover.png");
        AImage cursorHoverYellow = new AImage("cursorHoverYellow.png");

        cursorImage.setVisible(false);
        cursorSelect.setVisible(false);
        cursorHover.setVisible(false);
        cursorHoverYellow.setVisible(false);


        JPanel pnl = new JPanel();
        pnl.setBackground(Color.darkGray);
        JPanel pnl2 = new JPanel();
        pnl2.setBackground(Color.darkGray);
        JPanel pnl3 = new JPanel();
        pnl3.setBackground(Color.darkGray);
        pnl3.setPreferredSize(new Dimension(50, 100));
        JPanel pnl5 = new JPanel();
        pnl5.setBackground(Color.gray);
        JPanel pnl4 = new JPanel();
        pnl4.setBackground(Color.black);

        frame.setLayout(new BorderLayout());

        frame.add(pnl, BorderLayout.EAST);
        frame.add(pnl2, BorderLayout.WEST);
        frame.add(pnl3, BorderLayout.NORTH);
        frame.add(pnl5, BorderLayout.SOUTH);
        frame.add(pnl4, BorderLayout.CENTER);

        frame.setCursor(frame.getToolkit().createCustomCursor(
                new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(
                0, 0),
                "null"));

        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);

        JPanel glass = (JPanel) frame.getGlassPane();
        glass.setVisible(true);
        glass.setLayout(null);
        glass.setOpaque(false);

        glass.add(cursorImage);
        glass.add(cursorSelect);
        glass.add(cursorHover);
        glass.add(cursorHoverYellow);

        CursorListener cursor = new CursorListener(
                cursorImage, cursorSelect);
        glass.addMouseMotionListener(cursor);
        glass.addMouseListener(new CursorClick(cursor));

        glass.revalidate();

        frame.setGlassPane(glass);

    }

    private static class CursorClick extends MouseAdapter {

        private final AImage cursorImage;

        private final AImage cursorSelect;

//        private final AImage cursorHover;
        private final CursorListener click;

        public CursorClick(CursorListener click) {
            this.cursorImage = click.getCursorImage();
            this.cursorSelect = click.getCursorSelect();
            this.click = click;
        }

        public AImage getCursorImage() {
            return cursorImage;
        }

        public AImage getCursorSelect() {
            return cursorSelect;
        }

        @Override
        public void mousePressed(MouseEvent e) {

            cursorImage.setVisible(false);

            cursorSelect.setLocation(e.getPoint());

            cursorSelect.setBounds(e.getX(), e.getY(), cursorImage.getImgIcon()
                    .getIconWidth(), cursorImage.getImgIcon().getIconHeight());

            cursorSelect.setVisible(true);
        }

        @Override
        public void mouseReleased(MouseEvent e) {

            cursorSelect.setVisible(false);

            cursorImage.setLocation(e.getPoint());

            cursorImage.setBounds(e.getX(), e.getY(), cursorImage.getImgIcon()
                    .getIconWidth(), cursorImage.getImgIcon().getIconHeight());



            cursorImage.setVisible(true);

        }

        @Override
        public void mouseExited(MouseEvent e) {
            cursorSelect.setVisible(false);
            cursorImage.setVisible(false);
        }
    }

    class CursorListener implements MouseMotionListener {

        private final AImage cursorImage;

        private final AImage cursorSelect;

//        private final AImage cursorHover;
        private CursorListener(AImage cursorImage, AImage cursorSelect) {
            this.cursorImage = cursorImage;
            this.cursorSelect = cursorSelect;
//            this.cursorHover = cursorHover;


        }

        public AImage getCursorImage() {
            return cursorImage;
        }

        public AImage getCursorSelect() {
            return cursorSelect;
        }

        @Override
        public void mouseDragged(MouseEvent e) {

            cursorImage.setVisible(false);

            cursorSelect.setLocation(e.getPoint());

            cursorSelect.setBounds(e.getX(), e.getY(), cursorImage.getImgIcon()
                    .getIconWidth(), cursorImage.getImgIcon().getIconHeight());



            cursorSelect.setVisible(true);
        }

        @Override
        public void mouseMoved(MouseEvent e) {

            cursorSelect.setVisible(false);
//            if (panel.getBounds().contains(e.getPoint())) {


            cursorImage.setLocation(e.getPoint());

            cursorImage.setBounds(e.getX(), e.getY(), cursorImage
                    .getImgIcon()
                    .getIconWidth(), cursorImage.getImgIcon()
                    .getIconHeight());


            cursorImage.setVisible(true);


        }
    }
}
