package survivalGame.userInterface;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JTextField;

import graphics.CurrentGameState;
import graphics.GameGraphics;
import graphics.TextureManager;
import survivalGame.Button;
import survivalGame.GameState;
import survivalGame.Player;
import survivalGame.WorldGenerator;
import survivalGame.WorldIO;
import survivalGame.WorldInfo;

public class WorldSelectionMenu {
	Button[] slotButtons = new Button[3];
	
	Button[] deleteButtons = new Button[3];
	
	Button goBackButton;
	//Button[]  = new Button[3];
	
	BufferedImage buttonTexture;
	BufferedImage background;
	

	public WorldSelectionMenu(TextureManager Tmanager) {
		background = Tmanager.getTexture("Background");
		buttonTexture = Tmanager.getTexture("Button");
		
		int buttonX = GameGraphics.SCREEN_WIDTH / 2 - buttonTexture.getWidth() / 2;
		Rectangle rectMiddle = new Rectangle(buttonX, 300, 300,300);
		Rectangle rectLeft= new Rectangle((int) (buttonX - 0.6 * buttonX), 300, 300,300);
		Rectangle rectRight = new Rectangle((int) (buttonX + 0.6 * buttonX), 300, 300,300);
		
		//READ FROM SAVE FILES FIRST BEFORE DISPLAYING BUTTONS. 
		
		slotButtons[0] = (new Button(WorldIO.isFilePresent("world0") ? "+++" : "[EMPTY]",
				rectLeft, 
				GameState.WORLDSELECTION,
				() -> onSlotButtonClick(0) )
				);
		
		slotButtons[1] = (new Button(WorldIO.isFilePresent("world1") ? "+++" : "[EMPTY]" ,
				rectMiddle, 
				GameState.WORLDSELECTION,
				() -> onSlotButtonClick(1) )
				);
		
		slotButtons[2] = (new Button(WorldIO.isFilePresent("world2") ? "+++" : "[EMPTY]",
				rectRight, 
				GameState.WORLDSELECTION,
				() -> onSlotButtonClick(2) )
				);
		
		goBackButton = new Button("Return",
				buttonTexture, 
				buttonX, 1000,
				GameState.WORLDSELECTION,
				() -> CurrentGameState.gameState = GameState.MENU);
		
		initialiseDeleteButtons();
	}
	
	private void initialiseDeleteButtons() {
		int buttonX = GameGraphics.SCREEN_WIDTH / 2 - buttonTexture.getWidth() / 2;
		Rectangle rectMiddle = new Rectangle(buttonX, 820, 300,50);
		Rectangle rectLeft= new Rectangle((int) (buttonX - 0.6 * buttonX), 820, 300,50);
		Rectangle rectRight = new Rectangle((int) (buttonX + 0.6 * buttonX), 820, 300,50);
		
		deleteButtons[0] = (new Button("DELETE SAVE",
				rectLeft, 
				GameState.WORLDSELECTION,
				() -> onDeleteButtonClick(0, slotButtons[0]) )
				);
		
		deleteButtons[1] = (new Button("DELETE SAVE" ,
				rectMiddle, 
				GameState.WORLDSELECTION,
				() -> onDeleteButtonClick(1, slotButtons[1]) )
				);
		
		deleteButtons[2] = (new Button("DELETE SAVE",
				rectRight, 
				GameState.WORLDSELECTION,
				() -> onDeleteButtonClick(2, slotButtons[2]) )
				);
		
		for (Button button : deleteButtons) {
			button.setFontSize(24);
		}
		
	}
	
	public void onSlotButtonClick(int index) {
		String fileName = "world" + index;
		try {
			if (WorldIO.isFilePresent(fileName)) {
				Player player = new Player();
				GameGraphics.attachPlayer(player);
				GameGraphics.initialiseWorld( WorldIO.loadFromFile(fileName) );
			}
			else {
				WorldInfo world = WorldGenerator.generateWorld();
				GameGraphics.initialiseWorld(world);
				WorldIO.saveFile(world, fileName);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		CurrentGameState.gameState = GameState.GAME;
	}
	
	public void onDeleteButtonClick(int index, Button button) {
		String fileName = "world" + index;
		WorldIO.deleteFile(fileName);
		button.setText("[EMPTY]");
	}
	
	public void renderWorldSelection(Graphics2D g, GameGraphics graphics) {
		g.drawImage(background,0,0, GameGraphics.SCREEN_WIDTH, GameGraphics.SCREEN_HEIGHT, graphics);
	    
		for (Button button : slotButtons) {
			if (!button.isActive()) continue;
			g.setColor(new Color(0,0,0, 120));
			g.fillRect(button.xPosition, button.yPosition, 300, 500);
			g.setColor(new Color(200,200,200));
			button.renderUI(g, graphics);
		}
		
		for (Button button : deleteButtons) {

			g.setColor(new Color(150,0,0));
			g.fillRect(button.xPosition, button.yPosition, 300, 50);
			g.setColor(new Color(200,200,200));
			button.renderUI(g, graphics);
		}
		g.setColor(new Color(0,0,0));
		goBackButton.renderUI(g, graphics);
		
		Font largeFont = new Font("Arial", Font.BOLD, 100);
	    g.setFont(largeFont);
	    g.drawString("World Selection", 400, 160);
	    
	}

	
}
