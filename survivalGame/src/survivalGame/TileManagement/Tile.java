package survivalGame.TileManagement;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.DataOutputStream;
import java.io.IOException;

import graphics.GameGraphics;
import graphics.WorldRenderable;
import survivalGame.FactoryComponent;
import survivalGame.tileObjects.TileObject;

public class Tile implements WorldRenderable{
	
	private TileType tileType = TileType.GRASS; //NOT FINAL FOR NOW
	
	final public int x;
	final public int y;
	public final int tileSize;
	
	final public int pixelX;
	final public int pixelY;
	
	private boolean toRender = false;
	private boolean selected = false;
	private TileObject tileObject; 
	public final TileChunk chunkParent;
	// Declare image outside the try block
	public Tile(int x, int y, TileChunk parent, int tileSize) {
		
		this.x = x;
		this.y = y;
		this.tileSize = tileSize;
		pixelX = x * tileSize;
		pixelY = y * tileSize;

		GameGraphics.registerWorldObj(this, 1);
		
		chunkParent = parent;
	}

	public void write(DataOutputStream out) throws IOException {
		/*
		 * TileType
		 * Is there an object? 
		 * If so, Write TileObject ID.
		 * Is it a factoryComponent?
		 * If so write rotationID required for factory component.
		 * 
		 * Byte, Boolean ? Byte Boolean ? Byte
		 */
	    out.writeByte(getTileType().getID());
	    
	    out.writeBoolean(tileObject != null);
	    if (tileObject == null) return;
	    
	    out.writeByte(tileObject.getTileObjectID().id);
	    
	   // out.writeBoolean(tileObject instanceof FactoryComponent);
	   // if (!(tileObject instanceof FactoryComponent)) return;
	   // out.writeByte( ((FactoryComponent) tileObject).getRotation().getRotationID() );
	    
	}
	
	@Override
	public void render(Graphics2D g, GameGraphics graphics) {
		if (toRender) {
			
			g.setColor(new Color(66,100,74));
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
	public TileObject getTileObject() {
		return tileObject;
	}
	public boolean isEmpty() {
		return tileObject == null;
	}

	public TileType getTileType() {
		return tileType;
	}
	
	public void setTileType(TileType tileType) {
		this.tileType = tileType;
	}
}
