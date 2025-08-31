package survivalGame;

import java.awt.Graphics2D;

import graphics.GameGraphics;
import graphics.WorldRenderable;

public class WorldItem implements WorldRenderable {

	private int pixelX;
	private int pixelY;
	private Item item;
	
	protected boolean active = true;
	
	public WorldItem(Item item, int pixelX, int pixelY) {
		this.item = item;
		this.pixelX = pixelX;
		this.pixelY = pixelY;
		
		GameGraphics.getInstance();
		GameGraphics.registerWorldObj(this, 4);
	}
	
	@Override
	public int getY() {
		return pixelY;
	}

	@Override
	public boolean isActive() {
		return false;
	}
	
	public void setActive(boolean state) {
		active = state;
	}
	
	public Item getItem() {
		return item;
	}
	
	@Override
	public void render(Graphics2D g, GameGraphics graphics) {
		if (active) {
			g.drawImage(item.getTexture(),pixelX,pixelY,null);
		}
		
	}
	
	public void fixToTile(Tile tile) {
		this.pixelX = tile.pixelX - item.getTexture().getWidth() / 2 + tile.tileSize / 2;
		this.pixelY = tile.pixelY - item.getTexture().getHeight() / 2  + tile.tileSize / 2;
	}
	
}
