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

//
//
        JLabel im = new JLabel(new ImageIcon("C:\\Users\\Sammy\\Documents\\Aurora\\Onix 2 original\\app_Background.png"));
        frame.add(im);



//        System.out.println(GameFinder.getNameOfGamesOnDrive().get(2));
//        System.out.println(GameFinder.getNameOfGamesOnDrive());
//        System.out.println(GameFinder.getExecutablePathsOnDrive(GameFinder
//                .getNameOfGamesOnDrive()));



        frame.setVisible(true);
    }
}