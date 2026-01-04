package survivalGame.userInterface;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TextArea;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextField;

import graphics.CurrentGameState;
import graphics.GameGraphics;
import graphics.TextureManager;
import survivalGame.Button;
import survivalGame.GameState;

public class WorldSelectionMenu {
	List<Button> buttons = new ArrayList<>();
	
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
		
		buttons.add(new Button("Slot 1",
				rectLeft, 
				GameState.WORLDSELECTION,
				() -> CurrentGameState.gameState = GameState.WORLDSELECTION)
				);
		
		buttons.add(new Button("Slot 2",
				rectMiddle, 
				GameState.WORLDSELECTION,
				() -> CurrentGameState.gameState = GameState.WORLDSELECTION)
				);
		buttons.add(new Button("Slot 3",
				rectRight, 
				GameState.WORLDSELECTION,
				() -> CurrentGameState.gameState = GameState.MENU)
				);
		JTextField textField = new JTextField();
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
