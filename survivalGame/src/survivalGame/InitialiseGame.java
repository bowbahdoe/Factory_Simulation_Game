package survivalGame;


import java.awt.Graphics2D;

import graphics.GameGraphics;
import graphics.TextureManager;

public class InitialiseGame {
	
	static TileChunk[] chunks;
	static TextureManager textureManager = new TextureManager();
	private static int tiles = 0;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread updaterThread = new Thread(Updater.getInstance());
		
		loadTextures();
		
		int worldSize = 36 * 9;
		//world size is length or width of world, so if world size 2, 4 tiles total
		//chunk size reccommended: 6
		int chunkSize = 6;
		int chunkAmount = worldSize / chunkSize;
		

		chunks = new TileChunk[chunkAmount * chunkAmount];	
		GameGraphics gameGraphics = GameGraphics.getInstance();
		gameGraphics.init(new WorldInfo(chunks, worldSize, chunkSize));
		
		for (int x = 0; x < chunkAmount; x++) {
			for (int y = 0; y < chunkAmount; y++ ) {
				createChunk(x,y,chunkSize, chunkAmount);
			}
		}

		System.out.println(chunks.length + " chunks and " + tiles + " tiles") ;

		gameGraphics.addOnTextureManager(textureManager);
		Player player = new Player();
		gameGraphics.attachPlayer(player);
		
		new MainScreen(gameGraphics);
		
        updaterThread.start();  // Start the game loop in a separate thread
		
	}
	/**
	 * @param x coordinate of chunk
	 * @param y coordinate of chunk
	 * @param chunkSize is the width and height of the chunk square
	 * @param chunkAmount is the amount of chunks there will be in the world
	 */
	private static void createChunk(int x, int y, int chunkSize, int chunkAmount) {
		TileChunk chunk = new TileChunk(x,y, chunkSize, GameGraphics.TILESIZE);
		for (int Tx = 0; Tx < chunkSize; Tx++) {
			for (int Ty = 0; Ty < chunkSize; Ty++ ) {
				Tile tile = new Tile(Tx  + (x * chunkSize),Ty  + (y * chunkSize),chunk,  GameGraphics.TILESIZE);
				tiles++;
				tile.addTexture("Grass",textureManager);
				
				int rNum = (int) (Math.random() * 100) + 1; 
				if (rNum <= 1) {
					TileObject tree = new TileTree(tile);
					tree.addTexture("Tree",textureManager);
					tile.setObject(tree);
				}
				chunk.add(tile);
			}
		}
		chunks[x * chunkAmount + y] = chunk;
	}
	
	private static void loadTextures() {

		textureManager.loadTexture("src/images/Stickman.png", "Player");
		textureManager.loadTexture("src/images/grasy.png", "Grass");
		textureManager.loadTexture("src/images/Tree.png", "Tree");
		
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
		
		textureManager.loadTexture("src/images/TreeHarvester.png", "TreeHarvester");
		textureManager.loadTexture("src/images/Planker.png", "Planker");
		
		textureManager.loadTexture("src/images/Item_Conveyor.png", "ConveyorItem");
		textureManager.loadTexture("src/images/Item_TreeHarvester.png", "TreeHarvesterItem");
		textureManager.loadTexture("src/images/Item_Wood.png", "WoodItem");
		textureManager.loadTexture("src/images/Item_Log.png", "LogItem");
		textureManager.loadTexture("src/images/Item_Planker.png", "PlankerItem");
		
		textureManager.loadTexture("src/images/BlueprintDirection.png", "Blueprint");
	}
}
