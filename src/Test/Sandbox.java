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
import javax.swing.UIManager.LookAndFeelInfo;

public class Sandbox extends JFrame {

    public static void main(String[] args) throws IOException {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {

        System.out.println("Nimbus not available");
        }
        new Sandbox().gui();

    }
    private Cursor cursor;

    private Toolkit kit;

    private Image cursorImage;

    public void gui() {

        kit = Toolkit.getDefaultToolkit();
        cursorImage = kit.createImage(getClass().getResource(
                "/aurora/V1/resources/cursor.png"));

        cursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImage, new Point(0, 0), "CustomCursor");

        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setCursor(cursor);
    }
}
