package survivalGame;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import graphics.GameGraphics;
import survivalGame.tileObjects.TileObject;
import survivalGame.tileObjects.TileTree;

public class WorldIO {

	public static final int SAVE_VERSION = 1;


	/*
	 * SAVING PROCESS:
	 * 
	 * Save Version,
	 * WorldSize,
	 * ChunkAmount,
	 * 
	 * +Tile Saving:
	 * [TileType ID]
	 * [HasTileObject?]
	 * [TileObjectID]
	 */
    public static void save(WorldInfo world, File file) throws IOException {
    	 try (DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
                         
    		 out.writeInt(SAVE_VERSION);
    		 out.writeInt(world.worldSize);
    		 out.writeInt(world.chunkAmount);
    		 
    		 int chunkAmount = world.chunkAmount;
    		 for (int x = 0; x < chunkAmount; x++) {
    				for (int y = 0; y < chunkAmount; y++ ) {
    					for (Tile tile : world.chunks[x * chunkAmount + y].tiles) {
    						tile.write(out);
    					}
    				}
    		 }
    		 out.close();
    	 }
    }
    
    
    private static WorldInfo load(File file) throws IOException {
    	try (DataInputStream input = new DataInputStream(new BufferedInputStream(new FileInputStream(file)))) {
	                        
	   		int saveVersion = input.readInt();
	   		int worldSize = input.readInt();
	   		int chunkAmount = input.readInt();
	   		
	   		int chunkSize = 6;
	   		TileChunk[] chunks = new TileChunk[chunkAmount * chunkAmount];	
	   		
	   		for (int x = 0; x < chunkAmount; x++) {
	   				for (int y = 0; y < chunkAmount; y++ ) {
	   					chunks[chunkAmount * x + y] = loadChunk(x , y , 
									   						new TileChunk(x, y, chunkSize, GameGraphics.TILESIZE),
									   						chunkSize, input);
	   				}
		   	}
	   		input.close();
	   		return new WorldInfo(chunks, worldSize, chunkSize);
	   	}
		 
	   }
    
    private static TileChunk loadChunk(int x, int y, TileChunk chunk, int chunkSize, DataInputStream input) throws IOException {
		for (int Tx = 0; Tx < chunkSize; Tx++) {
			for (int Ty = 0; Ty < chunkSize; Ty++ ) {
				
				int tileX = Tx  + (x * chunkSize), tileY = Ty  + (y * chunkSize);
	
				Tile tile = new Tile(tileX, tileY , chunk,  GameGraphics.TILESIZE);
				
				tile.tileType = TileType.fromId(input.readByte()); //READS tileType Byte
				boolean hasTileObject = input.readBoolean(); //READS tileObject boolean
				if (hasTileObject) {
					input.readByte(); //READS tileObject byte (does nothing for now)
					TileObject tree = new TileTree(tile);
					tree.addTexture("Tree", GameGraphics.getTextureManager());
					tile.setObject(tree);
				}
				
				chunk.add(tile);
				
			}
		}
		return chunk;
	}
    
    
    public static void saveFile(WorldInfo world, String fileName) throws IOException {
    	File file = new File("saves/" + fileName + ".dat");

    	file.getParentFile().mkdirs(); // creates "saves" folder if missing

    	System.out.println("\\ SUCCESSFULLY SAVED! \\");
    	save(world, file);
    }
    
    /**
     * Loads from given File.
     * @return {@link WorldInfo} containing world data 
     * @throws IOException
     */
    public static WorldInfo loadFromFile(String fileName) throws IOException {
    	File file = new File("saves/" + fileName + ".dat");

    	if (!file.exists()) {
    	    return null;
    	}
    	System.out.println("// LOADING! //");
    	return load(file);
    }
    
    public static boolean isFilePresent(String fileName) {
    	File file = new File("saves/" + fileName + ".dat");

    	if (!file.exists()) return false;
    	
    	return true;
    }
}
