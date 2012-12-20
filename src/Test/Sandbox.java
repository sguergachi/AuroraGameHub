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

  public static void main(String[] args) throws MalformedURLException, UnsupportedAudioFileException, IOException, LineUnavailableException {
          JFrame frame = new JFrame("Sound debugging");
	  frame.setLayout(new FlowLayout());
	  final ASound as = new ASound(ASound.sfxTheme, true);
	  JButton btn = new JButton("Click me");
	  btn.addActionListener(new ActionListener(){
		@Override public void actionPerformed(ActionEvent arg0) {
			try {
				as.Play();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	  });
	  JButton btn2 = new JButton("stop dat music");
	  btn2.addActionListener(new ActionListener(){
		@Override public void actionPerformed(ActionEvent arg0) {
			as.Stop();

		}

	  });
	  frame.setSize(200, 100);
	  frame.getContentPane().add(btn);
	  frame.getContentPane().add(btn2);
	  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  frame.setVisible(true);
  }

}