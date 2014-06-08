package Test;

import aurora.engine.V1.UI.AFloatingPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Sandbox extends JFrame {

    public static void main(String[] args) {

        new Sandbox();
    }

    public Sandbox() {



        this.setLayout(null);

//        final JPanel panelA = new JPanel();
//        panelA.setBackground(Color.red);
//        panelA.setPreferredSize(new Dimension(155, 155));
//        panelA.setSize(new Dimension(155, 155));
//        panelA.setLocation(0, this.getPreferredSize().height);
//        this.add(panelA);

        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        final AFloatingPanel popup = new AFloatingPanel(this);
//        popup.setPreferredSize(new Dimension(100, 100));
        popup.setSize(new Dimension(100, 100));
        JPanel lblItem = new JPanel();
        lblItem.setBackground(Color.red);
        lblItem.setPreferredSize(new Dimension(10,10));
        JButton button = new JButton("Show");

        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                popup.show( 50, 150);

            }
        });


        popup.add(lblItem);
        popup.setBackground(Color.CYAN);

        button.setLocation(150, 150);
        button.setSize(200, 100);
        this.add(button);
        this.setVisible(true);

    }

}
