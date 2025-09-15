package survivalGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import graphics.GameGraphics;
import graphics.TextureManager;
import graphics.WorldRenderable;

public class Tile implements WorldRenderable{
	final int x;
	final int y;
	public final int tileSize;
	
	final public int pixelX;
	final public int pixelY;
	
	private boolean toRender = false;
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
		

		GameGraphics.getInstance().registerWorldObj(this, 1);
		chunkParent = parent;
	}


	@Override
	public void render(Graphics2D g, GameGraphics graphics) {
		if (toRender) {

			//g.drawImage(texture, pixelX, pixelY, null); 
			g.setColor(new Color(65,105,72));
			g.fillRect(pixelX, pixelY, tileSize, tileSize);
			if (selected) {
				g.setColor(new Color(0,0,111));
				g.drawRect(pixelX + tileSize / 4, pixelY + tileSize / 4, tileSize - tileSize / 2, tileSize - tileSize / 2);
				g.setColor(new Color(0,0,50,35));
				g.fillRect(pixelX, pixelY, tileSize, tileSize);
			}

			
		}	
	}

	@Override
	public int getY() {
		return y;
	}
	
	public void setActive(boolean state) {
		toRender = state;
		if (tileObject != null) {
			tileObject.setActive(state);
		}
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
	
	public void setObject(TileObject object) {
		tileObject = object;
	}
	public TileObject getObject() {
		return tileObject;
	}
	public void addTexture(String Texture, TextureManager textureM) {
		texture = textureM.getTexture(Texture);
	}
}
