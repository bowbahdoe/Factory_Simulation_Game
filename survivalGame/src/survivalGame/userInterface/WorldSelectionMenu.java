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
import survivalGame.Button.ButtonBuilder;

public class WorldSelectionMenu {
	Button[] slotButtons = new Button[3];
	
	Button[] deleteButtons = new Button[3];
	
	Button goBackButton;
	//Button[]  = new Button[3];
	
	BufferedImage buttonTexture;
	BufferedImage background;
	BufferedImage worldSlot;
	
	private final int[] titlePosition;
	public WorldSelectionMenu(TextureManager Tmanager) {
		background = Tmanager.getTexture("Background");
		buttonTexture = Tmanager.getTexture("Button");
		worldSlot = Tmanager.getTexture("WorldSlot");
		
		//READ FROM SAVE FILES FIRST BEFORE DISPLAYING BUTTONS. 
		
		
		slotButtons[0] = new ButtonBuilder(() -> onSlotButtonClick(0), GameState.WORLDSELECTION)
				.setTexture(worldSlot)
				.setDimensionToTexture()
				.centerDimensionToPoint(UIAnchor.CENTER)
				.build();
		
		slotButtons[1] = new ButtonBuilder(() -> onSlotButtonClick(0), GameState.WORLDSELECTION)
				.setTexture(worldSlot)
				.setDimensionToTexture()
				.centerDimensionToPoint(UIAnchor.CENTER_LEFT)
				.build();
		
		slotButtons[2] = new ButtonBuilder(() -> onSlotButtonClick(0), GameState.WORLDSELECTION)
				.setTexture(worldSlot)
				.setDimensionToTexture()
				.centerDimensionToPoint(UIAnchor.CENTER_RIGHT)
				.build();
		
		goBackButton = new ButtonBuilder(() -> CurrentGameState.gameState = GameState.MENU, GameState.WORLDSELECTION)
				.setTexture(buttonTexture)
				.setDimensionToTexture()
				.centerDimensionToPoint(UIAnchor.BOTTOM)
				.offsetY(-50)
				.setText("Return")
				.build();
	
		initialiseDeleteButtons();
		
		titlePosition = UIAlignment.getCoordinateFromAnchor(UIAnchor.CENTER_TOP);
	}
	
	private void initialiseDeleteButtons() {
		int buttonX = GameGraphics.SCREEN_WIDTH / 2 - buttonTexture.getWidth() / 2;
		Rectangle rectMiddle = new Rectangle(buttonX, 820, 300,50);
		Rectangle rectLeft= new Rectangle((int) (buttonX - 0.6 * buttonX), 820, 300,50);
		Rectangle rectRight = new Rectangle((int) (buttonX + 0.6 * buttonX), 820, 300,50);
		
		Color buttonColour = new Color(200,20,20);
		
		deleteButtons[0] = new ButtonBuilder(() -> onDeleteButtonClick(0, slotButtons[0]), GameState.WORLDSELECTION)
				.setWidth(320)
				.setHeight(60)
				.setColour(buttonColour)
				.setText("DELETE SAVE")
				.setFontSize(35)
				.centerDimensionToPoint(UIAnchor.CENTER_BOTTOM)
				.build();
		
		deleteButtons[1] = new ButtonBuilder(() -> onDeleteButtonClick(1, slotButtons[1]), GameState.WORLDSELECTION)
				.setWidth(320)
				.setHeight(60)
				.setColour(buttonColour)
				.setText("DELETE SAVE")
				.setFontSize(35)
				.centerDimensionToPoint(UIAnchor.CENTER_BOTTOM_LEFT)
				.build();
		
		deleteButtons[2] = new ButtonBuilder(() -> onDeleteButtonClick(2, slotButtons[2]), GameState.WORLDSELECTION)
				.setWidth(320)
				.setHeight(60)
				.setColour(buttonColour)
				.setText("DELETE SAVE")
				.setFontSize(35)
				.centerDimensionToPoint(UIAnchor.CENTER_BOTTOM_RIGHT)
				.build();
		
	
		
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
			button.renderUI(g, graphics);
		}
		
		for (Button button : deleteButtons) {
			button.renderUI(g, graphics);
		}
		
		goBackButton.renderUI(g, graphics);
		

		Font largeFont = new Font("Arial", Font.BOLD, 100);
	    g.setFont(largeFont);
	    g.drawString("World Selection", titlePosition[0] - g.getFontMetrics().stringWidth("World Selection") / 2, titlePosition[1]);

	}

	
}
