package survivalGame;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import graphics.GameGraphics;

public class TileChunk {

	List<Tile> tiles = new ArrayList<>();

	final int x;
	final int y;
	
	final int pixelX;
	final int pixelY;
	final int chunkPixelSize;
	
	private boolean activatedTiles = false;
	public TileChunk(int x, int y, int size, int tileSize) {
		this.x = x;
		this.y = y;

		pixelX = x * size * tileSize;
		pixelY = y * size * tileSize;
		chunkPixelSize = size * tileSize;
	}
	
	public void add(Tile tile) {
		tiles.add(tile);
	}

	public void renderChunk(Graphics2D g, GameGraphics graphics) {
		//tile chunk works like gamegraphics, but groups tiles together avoiding repeated checks
		int width = (int) (graphics.getWidth() / graphics.getCameraZoom()); 
		int height = (int) (graphics.getHeight() / graphics.getCameraZoom() ); 
		//Renders tiles if within range
		if (pixelX > graphics.getOriginOffset()[0] - chunkPixelSize - width / 2 && pixelX < graphics.getOriginOffset()[0] + width / 2 
				&& pixelY > graphics.getOriginOffset()[1] - chunkPixelSize - height / 2 && pixelY < graphics.getOriginOffset()[1] + height / 2 ) {
			
			for (Tile tile : tiles) {
				tile.setActive(true);
				activatedTiles = true;
			}
			
		}
		else if (activatedTiles){
			for (Tile tile : tiles) {
				tile.setActive(false);
				activatedTiles = false;
			}
		}
	}

	
}
