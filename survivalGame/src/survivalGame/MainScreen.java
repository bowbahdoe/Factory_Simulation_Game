package survivalGame;

import java.awt.event.MouseListener;

import javax.swing.JFrame;

import graphics.GameGraphics;


public class MainScreen {
	
	public MainScreen(GameGraphics gameGraphics, MouseListener mouseListener) {
	    JFrame frame = new JFrame("Custom Screen");

	    frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
	    frame.setUndecorated(true); 
	    
	    frame.add(gameGraphics);
	    frame.setVisible(true);
	    
	    frame.addMouseListener(mouseListener);
	    gameGraphics.setFocusable(true);
	    gameGraphics.requestFocusInWindow();
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}