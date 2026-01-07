package survivalGame;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import graphics.CurrentGameState;
import graphics.GameGraphics;
import graphics.TextureManager;
import graphics.ImageManipulation.ImageFlipper;
import survivalGame.ItemManagement.ItemID;

public class InitialiseGame {
	
	
	static TextureManager textureManager = new TextureManager();
	

	public static void main(String[] args) {
		//playMusic();
		
		Thread updaterThread = new Thread(Updater.getInstance());
		
		loadTextures();
		CurrentGameState.gameState = GameState.MENU;
		
		
		GameGraphics gameGraphics = new GameGraphics();
		gameGraphics.addOnTextureManager(textureManager);
		gameGraphics.addMouseMotionListener(InputListener.getInstance());
		
		Updater.register(gameGraphics);
		gameGraphics.initialiseMenu(textureManager);

		new MainScreen(gameGraphics);
		
        updaterThread.start();  // Start the game loop in a separate thread
        
		
	}
	
	
	
	public static void playMusic() {
		String location = "src/music.wav";
		File musicPath = new File(location);
		try {
			AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
			Clip clip;
			try {
				clip = AudioSystem.getClip();
				clip.open(audioInput);
				clip.start();
				clip.loop(clip.LOOP_CONTINUOUSLY);
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void loadTextures() {

		textureManager.loadTexture("src/images/Stickman.png", "Player");
		textureManager.loadTexture("src/images/grasy.png", "Grass");
		textureManager.loadTexture("src/images/Tree.png", "Tree");
		textureManager.loadTexture("src/images/Rock.png", "Rock");
		
		String[] directions = {"N", "E", "S", "W"};
		for (String direction : directions) {
		    textureManager.loadTexture("src/images/Conveyors/Conveyor" + direction + ".png", "Conveyor" + direction);
		}

		String[] turns = {"NE", "SE", "SW", "NW", "EN", "ES", "WS", "WN"};
		for (String turn : turns) {
		    textureManager.loadTexture("src/images/Conveyors/ConveyorTurn" + turn + ".png", "Conveyor" + turn);
		    textureManager.loadTexture("src/images/Conveyors/Junctions/ConveyorT_" + turn + ".png", "ConveyorT_" + turn);
		}
		turns = new String[]{"VE", "VW", "HN", "HS"};
		for (String turn : turns) {
			 textureManager.loadTexture("src/images/Conveyors/Junctions/ConveyorT_" + turn + ".png", "ConveyorT_" + turn);
		}
		
		textureManager.loadTexture("src/images/PlayerUI.png", "PlayerUI");

		textureManager.loadTexture("src/images/InventorySquare.png", "InventorySlot");
		textureManager.loadTexture("src/images/itemSelection.png", "SelectedSlot");
		textureManager.loadTexture("src/images/CraftingSquareActive.png", "ButtonActive");
		textureManager.loadTexture("src/images/CraftingSquareInactive.png", "ButtonInactive");
		textureManager.loadTexture("src/images/Hotbar.png", "Hotbar");
		
		textureManager.loadTexture("src/images/factoryComponents/TreeHarvester.png", "TreeHarvester");
		textureManager.loadTexture("src/images/factoryComponents/Planker.png", "Planker");
		textureManager.loadTexture("src/images/factoryComponents/ConveyorSplitter.png", "ConveyorSplitterR");
		textureManager.addTexture(ImageFlipper.flipImageHorizontal(textureManager.getTexture("ConveyorSplitterR")), "ConveyorSplitterL");
		textureManager.loadTexture("src/images/factoryComponents/RockDriller.png", "RockDriller");
		
		textureManager.loadTexture("src/images/Items/Item_Conveyor.png", "ConveyorItem");
		textureManager.loadTexture("src/images/Items/Item_TreeHarvester.png", "TreeHarvesterItem");
		textureManager.loadTexture("src/images/Items/Item_Wood.png", "WoodItem");
		textureManager.loadTexture("src/images/Items/Item_Log.png", "LogItem");
		textureManager.loadTexture("src/images/Items/Item_Rock.png", "RockItem");
		textureManager.loadTexture("src/images/Items/Item_Planker.png", "PlankerItem");
		textureManager.loadTexture("src/images/Items/Item_ConveyorSplitter.png", "ConveyorSplitterItemR");
		textureManager.addTexture(ImageFlipper.flipImageHorizontal(textureManager.getTexture("ConveyorSplitterItemR")), "ConveyorSplitterItemL");
		textureManager.loadTexture("src/images/Items/Item_RockDriller.png", "RockDrillerItem");
		
		textureManager.loadTexture("src/images/BlueprintDirection.png", "Blueprint");
		
		textureManager.loadTexture("src/images/Button.png", "Button");
		textureManager.loadTexture("src/images/Background.png", "Background");
	}
}
