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


import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.sound.sampled.*;
import javax.sound.*;
import javax.swing.*;

import aurora.engine.V1.Logic.*;
import aurora.engine.V1.UI.*;

public class Sandbox {

    public static void main(String[] args)  {
        ANuance nuance = null;
        try {
            nuance = new ANuance(
            "https://s3.amazonaws.com/AuroraStorage/AIDictionary.txt",
            "C:\\Users\\Sammy\\Documents\\AuroraData\\AIDictionary.txt");
        } catch (IOException ex) {
            Logger.getLogger(Sandbox.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(nuance.VI(ANuance.inx_Error));
    }
}