package Test;

import aurora.engine.V1.Logic.ABrowser;
import javax.swing.*;

public class Sandbox {
    private static ABrowser browser;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        browser = new ABrowser(100,100);
        browser.goTo("google.com");
        browser.getPanelBrowser();
        JPanel panel = new JPanel();


        frame.add(browser);


        frame.setVisible(true);
    }
}