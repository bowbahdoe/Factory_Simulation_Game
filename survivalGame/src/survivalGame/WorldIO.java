package survivalGame;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class WorldIO {

	public static final int SAVE_VERSION = 1;
	

    public static void save(WorldInfo world, File file) throws IOException {
    	 try (DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
                         
    		 out.writeInt(SAVE_VERSION);
    		 out.writeInt(world.worldSize);
    		 out.writeInt(world.chunkAmount);
    		 
    		 int chunkAmount = world.chunkAmount;
    		 for (int x = 0; x < chunkAmount; x++) {
    				for (int y = 0; y < chunkAmount; y++ ) {
    					
    					for (Tile tile : world.chunks[chunkAmount * x + y].tiles) {
    						
    					}
    				}
    			}
    	 }
    }
}
