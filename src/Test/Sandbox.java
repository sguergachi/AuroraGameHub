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
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

public class Sandbox extends JFrame {

    public static void main(String[] args) throws IOException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
        } catch (InstantiationException ex) {
        } catch (IllegalAccessException ex) {
        } catch (UnsupportedLookAndFeelException ex) {
        }
        new Sandbox().gui();

    }
    private Cursor cursor;

    private Toolkit kit;

    private Image cursorImage;

    public void gui() {



        setSize(800, 800);
        setLayout(new BorderLayout());
        add(new MouseCursorPane());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setCursor(cursor);
    }

    public class MouseCursorPane extends JPanel {

        private BufferedImage cursorImage;

        private Toolkit kit;

        public MouseCursorPane() {
            setBackground(Color.darkGray);
            try {
                kit = Toolkit.getDefaultToolkit();
                AImage image = new AImage("cursor.png");
                cursorImage = image.resizeBufferedImg(
                        cursorImage, image.getImgIcon().getIconWidth(), image
                        .getImgIcon().getIconHeight());
                cursorImage = ImageIO.read(getClass().getResource("/aurora/V1/resources/cursor.png"));
                for (int i = 0; i < cursorImage.getHeight(); i++) {
                    int[] rgb = cursorImage.getRGB(0, i, cursorImage.getWidth(),
                            1, null, 0, cursorImage.getWidth() * 4);
                    for (int j = 0; j < rgb.length; j++) {
                        int alpha = (rgb[j] >> 24) & 255;
                        if (alpha < 128) {
                            alpha = 0;
                        } else {
                            alpha = 255;
                        }
                        rgb[j] &= 0x00ffffff;
                        rgb[j] = (alpha << 24) | rgb[j];
                    }
                    cursorImage.setRGB(0, i, cursorImage.getWidth(), 1, rgb, 0,
                            cursorImage.getWidth() * 4);
                }
                Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(
                        cursorImage, new Point(0, 0), "CustomCursor");

                setCursor(cursor);

            } catch (Exception exp) {
                exp.printStackTrace();
            }
        }
    }
}
