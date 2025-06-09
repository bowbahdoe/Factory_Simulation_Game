package survivalGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tile implements RenderComponent{
	final int x;
	final int y;
	final int tileSize;
	
	final int pixelX;
	final int pixelY;
	
	private boolean active = false;
	private boolean selected = false;
	private TileObject tileObject; 
	public final TileChunk chunkParent;
	// Declare image outside the try block
	private BufferedImage texture; 
	public Tile(int x, int y, TileChunk parent, int tileSize) {
		this.x = x;
		this.y = y;
		this.tileSize = tileSize;
		pixelX = x * tileSize;
		pixelY = y * tileSize;
		
		GameGraphics.getInstance();
		GameGraphics.register(this, 1);
		chunkParent = parent;
	}
	
	public void setObject(TileObject object) {
		tileObject = object;
	}
	public TileObject getObject() {
		return tileObject;
	}
	public void addTexture(String Texture, TextureManager textureM) {
		texture = textureM.getTexture(Texture);
	}
	@Override
	public void render(Graphics2D g, GameGraphics graphics) {
		if (active) {
			int width = graphics.getWidth();
			int height = graphics.getHeight();

			//g.drawImage(texture, pixelX, pixelY, null); 
			g.setColor(new Color(70,110,76));
			g.fillRect(pixelX, pixelY, tileSize, tileSize);
			if (selected) {
				g.setColor(new Color(0,0,111));
				g.drawRect(pixelX + tileSize / 4, pixelY + tileSize / 4, tileSize - tileSize / 2, tileSize - tileSize / 2);
				g.setColor(new Color(0,0,50,35));
				g.fillRect(pixelX, pixelY, tileSize, tileSize);
			}
			//g.drawRect(pixelX, pixelY, tileSize, tileSize);
			
			if (tileObject != null) {
				tileObject.render(g, graphics);
			}
		}
		
		
		
		
	}

	@Override
	public int getY() {
		return y;
	}
	
	public void setActive(boolean state) {
		active = state;
	}
	@Override
	public boolean isActive() {
		return false;
	}
	public void setSelect(boolean selected) {
		this.selected = selected;
	}
	public boolean isSelected() {
		return selected;
	}
}
