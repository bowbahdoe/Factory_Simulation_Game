package survivalGame.ItemManagement;

import java.awt.Graphics2D;

import graphics.GameGraphics;
import graphics.WorldRenderable;
import survivalGame.Tile;
import survivalGame.Updatable;
import survivalGame.Updater;

public class WorldItem implements WorldRenderable, Updatable {

	private int pixelX;
	private int pixelY;
	
	private int targetX;
	private int targetY;
	private float startX;
	private float startY;
	private float elapsedTime;
	private boolean moving;
	private Item item;
	
	protected boolean active = true;
	
	public WorldItem(Item item, int pixelX, int pixelY) {
		this.item = item;
		this.pixelX = pixelX;
		this.pixelY = pixelY;
		
		GameGraphics.getInstance().registerWorldObj(this, 4);
		
		Updater.register(this);
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
		lerpToTile(tile);
	}
	
	public void lerpToTile(Tile tile) {
		startX = pixelX;
	    startY = pixelY;

	    targetX = tile.pixelX - item.getTexture().getWidth() / 2 + tile.tileSize / 2;
	    targetY = tile.pixelY - item.getTexture().getHeight() / 2 + tile.tileSize / 2;

	    elapsedTime = 0;
	    moving = true;
	}
	public float lerp(float a, float b, float t) {
	    return a + t * (b - a);
	}

	@Override
	public void update() {
		
	}

	@Override
	public void fixedUpdate(long delta) {

		if (moving && elapsedTime < 0.1f) {
			elapsedTime += delta / 1000f;
			
			float t = Math.min(elapsedTime / 0.1f, 1);
			pixelX = (int) lerp(startX,targetX,t);
			pixelY = (int) lerp(startY,targetY,t);
			
			if (t >= 1f) moving = false;  
		}
	}
}
