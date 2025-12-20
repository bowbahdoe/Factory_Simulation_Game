package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import survivalGame.Button;
import survivalGame.GameState;

public class Menu {
	List<Button> buttons = new ArrayList<>();
	Button button = new Button("hi", GameGraphics.getTextureManager().getTexture("Button"), 0, 0, null);
	
	BufferedImage buttonTexture;
	BufferedImage background;
	public Menu(TextureManager Tmanager) {
		background = Tmanager.getTexture("Background");
		buttonTexture = Tmanager.getTexture("Button");
		
		int buttonX = GameGraphics.SCREEN_WIDTH / 2 - buttonTexture.getWidth() / 2;
		buttons.add(new Button("Play",
				buttonTexture, 
				buttonX, 450, 
				() -> CurrentGameState.gameState = GameState.GAME)
				);
		
		buttons.add(new Button("Settings",
				buttonTexture, 
				buttonX, 550, 
				() -> CurrentGameState.gameState = GameState.SETTINGS)
				);
		
		buttons.add(new Button("Quit",
				buttonTexture, 
				buttonX, 700, 
				() -> System.out.println("AA"))
				);
	}
	
	
	public void renderMenu(Graphics2D g, GameGraphics graphics) {
		g.drawImage(background,0,0,graphics);
		
		for (Button button : buttons) {
			button.renderUI(g, graphics);
		}
		Font largeFont = new Font("Arial", Font.BOLD, 150);
	    g.setFont(largeFont);
	    g.drawString("Autism", 200, 160);
	}
}
