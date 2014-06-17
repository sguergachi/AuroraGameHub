package Test;

import javax.swing.JFrame;

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


        this.setVisible(true);

    }

}
