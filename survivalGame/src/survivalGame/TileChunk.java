package survivalGame;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import graphics.GameGraphics;

public class TileChunk {

	List<Tile> tiles = new ArrayList<>();
	

	private int size;
	final int x;
	final int y;
	
	final int pixelX;
	final int pixelY;
	final int chunkPSize;
	
	private boolean activatedTiles = false;
	public TileChunk(int x, int y, int size, int tileSize) {
		this.x = x;
		this.y = y;
		this.size = size;

		pixelX = x * size * tileSize;
		pixelY = y * size * tileSize;
		chunkPSize = size * tileSize;
	}
	
	public void add(Tile tile) {
		tiles.add(tile);
	}
	
	

	public void activateChunk(Graphics2D g, GameGraphics graphics) {
		//tile chunk works like gamegraphics, but groups tiles together avoiding repeated checks
		int width = graphics.getWidth();
		int height = graphics.getHeight();
		if (pixelX > graphics.getOriginOffset()[0] - chunkPSize && pixelX < graphics.getOriginOffset()[0] + width
				&& pixelY > graphics.getOriginOffset()[1] - chunkPSize && pixelY < graphics.getOriginOffset()[1] + height) {
			
			toggleTiles();
			
		}
		else if (activatedTiles){
			toggleTiles();
		}
	}
	private void toggleTiles() {
		for (Tile tile : tiles) {
			tile.setActive(!activatedTiles);
		}
	}
}
