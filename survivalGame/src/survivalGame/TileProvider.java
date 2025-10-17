package survivalGame;

import graphics.GameGraphics;

public class TileProvider {

	public static Tile pixel_AccessTile(int clickedX, int clickedY) {
		GameGraphics graphics = GameGraphics.getInstance();

		int pixelX = (int) ( (clickedX - graphics.getSize().width / 2) / graphics.getCameraZoom() + graphics.getOriginOffset()[0]);
		int pixelY = (int) ((clickedY - graphics.getSize().height / 2) / graphics.getCameraZoom() + graphics.getOriginOffset()[1]);
		
		int x = (pixelX / GameGraphics.TILESIZE);
		int y = (pixelY / GameGraphics.TILESIZE);
		
		int chunkSize = graphics.chunkSize;
		int chunkAmount = graphics.worldSize / chunkSize;
		int positionInArray = chunkAmount * (x / chunkSize) + (y / chunkSize);		
		TileChunk chunk = (TileChunk) graphics.chunks[positionInArray];
		
		int chunkX = x - (chunk.x * chunkSize);
		int chunkY = y - (chunk.y * chunkSize);
	
		if (chunkX * chunkSize + chunkY < 0 ) return null; //Out of bounds
		Tile tile = chunk.tiles.get(chunkX * chunkSize + chunkY);

		return tile;
	}
	
	public static Tile world_AccessTile(int x, int y) {
		GameGraphics graphics = GameGraphics.getInstance();
		
		int chunkSize = graphics.chunkSize;
		int chunkAmount = graphics.worldSize / chunkSize;
		int positionInArray = chunkAmount * (x / chunkSize) + (y / chunkSize);		
		TileChunk chunk = (TileChunk) graphics.chunks[positionInArray];
		
		int chunkX = x - (chunk.x * chunkSize);
		int chunkY = y - (chunk.y * chunkSize);
	
		if (chunkX * chunkSize + chunkY < 0 ) return null; //Out of bounds
		Tile tile = chunk.tiles.get(chunkX * chunkSize + chunkY);

		return tile;
	}
	
}
