package survivalGame;

import graphics.GameGraphics;

public class TileProvider {

	/**
	 * Accesses the Tile according to the pixel coordinates of the click.
	 * @param clickedX is x coordinate of the click.
	 * @param clickedY is y coordinate of the click
	 * @return {@link Tile}
	 */
	public static Tile pixel_AccessTile(int clickedX, int clickedY) {
		int pixelX = (int) ( (clickedX - GameGraphics.SCREEN_WIDTH / 2) / GameGraphics.getCameraZoom() + GameGraphics.getOriginOffset()[0]);
		int pixelY = (int) ((clickedY - GameGraphics.SCREEN_HEIGHT / 2) / GameGraphics.getCameraZoom() + GameGraphics.getOriginOffset()[1]);
		
		int x = (pixelX / GameGraphics.TILESIZE);
		int y = (pixelY / GameGraphics.TILESIZE);
		
		int chunkSize = GameGraphics.chunkSize;
		int chunkAmount = GameGraphics.worldSize / chunkSize;
		int positionInArray = chunkAmount * (x / chunkSize) + (y / chunkSize);		
		TileChunk chunk = (TileChunk) GameGraphics.chunks[positionInArray];
		
		int chunkX = x - (chunk.x * chunkSize);
		int chunkY = y - (chunk.y * chunkSize);
	
		if (chunkX * chunkSize + chunkY < 0 ) return null; //Out of bounds
		Tile tile = chunk.tiles.get(chunkX * chunkSize + chunkY);

		return tile;
	}
	
	public static Tile world_AccessTile(int x, int y) {
		int chunkSize = GameGraphics.chunkSize;
		int chunkAmount = GameGraphics.worldSize / chunkSize;
		int positionInArray = chunkAmount * (x / chunkSize) + (y / chunkSize);		
		TileChunk chunk = (TileChunk) GameGraphics.chunks[positionInArray];
		
		int chunkX = x - (chunk.x * chunkSize);
		int chunkY = y - (chunk.y * chunkSize);
	
		if (chunkX * chunkSize + chunkY < 0 ) return null; //Out of bounds
		Tile tile = chunk.tiles.get(chunkX * chunkSize + chunkY);

		return tile;
	}
	
}
