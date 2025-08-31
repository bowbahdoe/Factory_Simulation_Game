package survivalGame;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import graphics.GameGraphics;
import graphics.UIClickable;
import graphics.UIRenderable;

public class InventorySlot implements UIClickable{
	
	BufferedImage UI;
	int pixelX;
	int pixelY;
	
	private UIItem items; 
	private PlayerUI parent; 
	
	public InventorySlot(int pixelX, int pixelY, PlayerUI parent) {
		GameGraphics.registerUI(this);
		UI = GameGraphics.getTextureManager().getTexture("InventorySlot");
		this.pixelX = pixelX;
		this.pixelY = pixelY;
		this.parent = parent;
	}

	@Override
	public boolean isActive() {
		return parent.isActive();
	}

	@Override
	public void renderUI(Graphics2D g, GameGraphics graphics) {
		
		g.drawImage(UI, pixelX,  pixelY, graphics);
		if (items != null) {
			items.renderUI(g, graphics);
		}
		
	}

	@Override
	public Rectangle getBounds() {

		return new Rectangle( pixelX,  pixelY, UI.getWidth(), UI.getHeight());
	}

	@Override
	public void onClick() {
		
		pixelY+=20;
	}
	
	public ItemStack getItemStack() {
		return items.getItemStack();
	}
	
	public boolean isEmpty() {
		return items == null;
	}
	public void setItem(UIItem item) {
		System.out.println("ItemStack is set!!");
		items = item;

	}
	
}
