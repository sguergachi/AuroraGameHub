package Test;

import aurora.V1.core.GameFinder;
import aurora.engine.V1.Logic.ABrowser;
import java.util.ArrayList;
import javax.swing.*;

public class Sandbox {

    private static ABrowser browser;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

//        browser = new ABrowser(100,100);
//        browser.goTo("google.com");
//        browser.getPanelBrowser();
//        JPanel panel = new JPanel();
//
//
//        frame.add(browser);



//        System.out.println(GameFinder.getNameOfGamesOnDrive().get(2));
        System.out.println(GameFinder.getExecutablePathsOnDrive(GameFinder.getNameOfGamesOnDrive()));


//        frame.setVisible(true);
    }
}