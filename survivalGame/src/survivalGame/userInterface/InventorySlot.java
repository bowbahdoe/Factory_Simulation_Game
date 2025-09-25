package survivalGame.userInterface;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import graphics.GameGraphics;
import graphics.UIClickable;
import survivalGame.InputListener;
import survivalGame.ItemManagement.ItemStack;

public class InventorySlot implements UIClickable{
	
	private BufferedImage UI;
	private BufferedImage selectedUI;
	final int pixelX;
	final int pixelY;
	
	private UIItem items;
	private PlayerUI parent; 
	private Rectangle rectangleBounds;
	
	private boolean selected = false;
	public InventorySlot(int pixelX, int pixelY, PlayerUI parent) {
		InputListener.getInstance().registerClickable(this);
		UI = GameGraphics.getTextureManager().getTexture("InventorySlot");
		selectedUI = GameGraphics.getTextureManager().getTexture("SelectedSlot");
		this.pixelX = pixelX;
		this.pixelY = pixelY;
		this.parent = parent;
		rectangleBounds = new Rectangle( pixelX,  pixelY, UI.getWidth(), UI.getHeight());
		items = new UIItem(pixelX, pixelY); 
	}

	@Override
	public boolean isActive() {
		return parent.isActive();
	}

	@Override
	public void renderUI(Graphics2D g, GameGraphics graphics) {
		g.drawImage(UI, pixelX,  pixelY, graphics);
		if (selected) {
			g.drawImage(selectedUI,pixelX,pixelY,graphics);
			
		}
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
		parent.selectSlot(this);
	}
	
	public ItemStack getItemStack() {
		return items.getItemStack();
	}
	public boolean isEmpty() {
		return items.isEmpty();
	}
	
	public UIItem getItem() {
		return items;
	}
	public void setItemStack(ItemStack itemStack) {
		this.items.setItemStack(itemStack);
	}
	public void clearItem() {
		items.clearItem();
	}
	public void toggleSelect() {
		selected = !selected;
	}
	public void setSelected(boolean state) {
	    selected = state;
	}
	public boolean isSelected() {
		return selected;
	}
}
