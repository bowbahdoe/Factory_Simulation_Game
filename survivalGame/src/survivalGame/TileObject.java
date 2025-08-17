
package survivalGame;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import graphics.GameGraphics;
import graphics.TextureManager;
import graphics.WorldRenderable;

public abstract class TileObject implements WorldRenderable{
	
	protected BufferedImage texture; 
	protected Tile parentTile;
	
	protected int verticalOffset = 0;
	public void addTexture(String Texture, TextureManager textureM) {
		texture = textureM.getTexture(Texture);
		
	}
	
	@Override
	public void render(Graphics2D g, GameGraphics graphics) {
		int pixelX = parentTile.pixelX;
		int pixelY = parentTile.pixelY;
		g.drawImage(texture, pixelX, pixelY + verticalOffset, null); 
	}
}
