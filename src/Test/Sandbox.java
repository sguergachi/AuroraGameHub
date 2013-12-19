package Test;

import aurora.engine.V1.Logic.AAnimate;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Sandbox extends JPanel {
    private static JFrame frame;

    public static void main(String[] args) {
         frame = new JFrame();
        frame.setSize(500, 500);
        frame.add(new Sandbox());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


    }
    private final AAnimate animate;

    public Sandbox() {
        this.setLayout(null);

        final JPanel panelA = new JPanel();
        panelA.setBackground(Color.red);
        panelA.setPreferredSize(new Dimension(155, 155));
        panelA.setSize(new Dimension(155, 155));
        panelA.setLocation(0, this.getPreferredSize().height);
        this.add(panelA);

        animate = new AAnimate(panelA);


        animate.setInitialLocation(100, frame.getHeight() );
        animate.moveVertical(frame.getHeight()- panelA.getHeight(), 5);

    }

}
