package Test;

import aurora.V1.core.GameFinder;
import aurora.engine.V1.Logic.ABrowser;
<<<<<<< HEAD
import java.util.ArrayList;
import javax.swing.*;

public class Sandbox {

    private static ABrowser browser;
=======
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

//public class Sandbox {
public class Sandbox extends JPanel {
>>>>>>> origin/dev

    private Font font;

    private JPanel panel;

    private JLabel label = new JLabel("Font Size Adapter Label", JLabel.CENTER);

    private JTextField tf = new JTextField(
            "Type in text for new label here and hit [Enter]");

<<<<<<< HEAD
//        browser = new ABrowser(100,100);
//        browser.goTo("google.com");
//        browser.getPanelBrowser();
//        JPanel panel = new JPanel();
//
//
//        frame.add(browser);

=======
    public Sandbox() {
        super(new BorderLayout());
        panel = labelPanel();
        add(panel, "North");
        add(tf, "South");
        tf.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                label.setText(tf.getText());
                label.setFont(font);
                int labelW = (int) Math
                        .ceil(label.getPreferredSize().getWidth());
                int maxWidth = (int) Math.floor(panel.getSize().getWidth());
>>>>>>> origin/dev

                if (labelW <= maxWidth) {
                    return;
                }

<<<<<<< HEAD
//        System.out.println(GameFinder.getNameOfGamesOnDrive().get(2));
        System.out.println(GameFinder.getExecutablePathsOnDrive(GameFinder.getNameOfGamesOnDrive()));
=======
                for (int k = 1; labelW > maxWidth; k++) {
                    Font labelFont = font.deriveFont(font.getSize() - k * 1.0f);
                    label.setFont(labelFont);
                    labelW = (int) Math
                            .ceil(label.getPreferredSize().getWidth());
                }
>>>>>>> origin/dev

            }
        });
    }

    private JPanel labelPanel() {
        JPanel lp = new JPanel(new BorderLayout());
        lp.setPreferredSize(new Dimension(270, 30));
        lp.add(label, "North");
        font = label.getFont();
        return lp;
    }

<<<<<<< HEAD
//        frame.setVisible(true);
=======
    public static void main(String[] args) {
        JFrame lsFrame = new JFrame("Lfit");
        lsFrame.add(new Sandbox());
        lsFrame.pack();
        lsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        lsFrame.setVisible(true);
>>>>>>> origin/dev
    }
}
//    public static void main(String[] args) {
//        JFrame frame = new JFrame();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(500, 500);
//
//        JLabel im = new JLabel(new ImageIcon("C:\\Users\\Sammy\\Documents\\Aurora\\Onix 2 original\\app_Background.png"));
//        frame.add(im);
//
//
//        frame.setVisible(true);
//    }
