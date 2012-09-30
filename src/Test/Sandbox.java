package Test;

import java.io.IOException;

/**
 *
 * @author Sammy
 */
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Field;
import javax.swing.*;

public class Sandbox {

    public static void main(String[] args) throws IOException {

        Sandbox test = new Sandbox();

        test.gui();


    }
    private JFileChooser fc;
    private JPanel RightBottomCenter;

    public void gui() {

        JFrame frame = new JFrame("Test");

        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

       main();

        frame.setVisible(true);



    }
    
    public void main(){
        System.out.print(Math.floor(-2.1));
    }
}
