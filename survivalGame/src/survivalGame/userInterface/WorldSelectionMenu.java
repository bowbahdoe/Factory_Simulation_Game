package survivalGame.userInterface;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;

import graphics.CurrentGameState;
import graphics.GameGraphics;
import graphics.TextureManager;
import survivalGame.Button;
import survivalGame.GameState;
import survivalGame.WorldGenerator;
import survivalGame.WorldInfo;

public class WorldSelectionMenu {
	Button[] buttons = new Button[3];
	WorldInfo[] saves = new WorldInfo[3];
	
	BufferedImage buttonTexture;
	BufferedImage background;
	

	public WorldSelectionMenu(TextureManager Tmanager) {
		background = Tmanager.getTexture("Background");
		buttonTexture = Tmanager.getTexture("Button");
		
		int buttonX = GameGraphics.SCREEN_WIDTH / 2 - buttonTexture.getWidth() / 2;
		int buttonSize = 200;
		Rectangle rectMiddle = new Rectangle(buttonX, 300, 300,300);
		Rectangle rectLeft= new Rectangle((int) (buttonX - 0.6 * buttonX), 300, 300,300);
		Rectangle rectRight = new Rectangle((int) (buttonX + 0.6 * buttonX), 300, 300,300);
		
		//READ FROM SAVE FILES FIRST BEFORE DISPLAYING BUTTONS. 
		
		buttons[0] = (new Button("[EMPTY]",
				rectLeft, 
				GameState.WORLDSELECTION,
				() -> onButtonClick(0) )
				);
		
		buttons[1] = (new Button("[EMPTY]" ,
				rectMiddle, 
				GameState.WORLDSELECTION,
				() -> onButtonClick(1) )
				);
		
		buttons[2] = (new Button("[EMPTY]",
				rectRight, 
				GameState.WORLDSELECTION,
				() -> onButtonClick(2) )
				);
		
		JTextField textField = new JTextField();
	}
	public void onButtonClick(int index) {
		if (saves[index] == null) {
			GameGraphics.initialiseWorld(WorldGenerator.generateWorld());
			
		}
		
		
		CurrentGameState.gameState = GameState.GAME;
	}
	
	public void renderWorldSelection(Graphics2D g, GameGraphics graphics) {
		g.drawImage(background,0,0, GameGraphics.SCREEN_WIDTH, GameGraphics.SCREEN_HEIGHT, graphics);
		
		for (Button button : buttons) {
			if (!button.isActive()) continue;
			g.setColor(new Color(0,0,0, 120));
			g.fillRect(button.xPosition, button.yPosition, 300, 500);
			g.setColor(new Color(200,200,200));
			button.renderUI(g, graphics);
		}
		g.setColor(new Color(0,0,0));
		Font largeFont = new Font("Arial", Font.BOLD, 100);
	    g.setFont(largeFont);
	    g.drawString("World Selection", 400, 160);
	    
	}

	
}
