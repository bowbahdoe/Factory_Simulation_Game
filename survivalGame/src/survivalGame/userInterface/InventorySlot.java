package survivalGame.userInterface;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import graphics.GameGraphics;
import graphics.UIClickable;
import survivalGame.InputListener;
import survivalGame.ItemManagement.ItemStack;

public class InventorySlot implements UIClickable{
	
	BufferedImage UI;
	int pixelX;
	int pixelY;
	
	private UIItem items; 
	private PlayerUI parent; 
	private Rectangle rectangleBounds;
	public InventorySlot(int pixelX, int pixelY, PlayerUI parent) {
		InputListener.getInstance().registerClickable(this);
		UI = GameGraphics.getTextureManager().getTexture("InventorySlot");
		this.pixelX = pixelX;
		this.pixelY = pixelY;
		this.parent = parent;
		rectangleBounds = new Rectangle( pixelX,  pixelY, UI.getWidth(), UI.getHeight());
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
		return rectangleBounds;
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
		items = item;
	}
	
}
