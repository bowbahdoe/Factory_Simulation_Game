package survivalGame.ItemManagement;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import graphics.GameGraphics;
import graphics.UIRenderable;
import survivalGame.InputListener;
import survivalGame.userInterface.PlayerUI;
import survivalGame.userInterface.UIItem;

public class HotbarSlot implements UIRenderable{
	//private BufferedImage UI;
	private BufferedImage selectedUI;
	final int pixelX;
	final int pixelY;
	
	private UIItem items; 
	private PlayerUI parent; 
	
	private boolean selected = false;
	public HotbarSlot(int pixelX, int pixelY, PlayerUI parent) {
		//UI = GameGraphics.getTextureManager().getTexture("InventorySlot");
		selectedUI = GameGraphics.getTextureManager().getTexture("SelectedSlot");
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
		//g.drawImage(UI, pixelX,  pixelY, graphics);
		if (selected) {
			g.drawImage(selectedUI,pixelX,pixelY,graphics);
			
		}

		if (items != null) {
			items.renderUI(g, graphics,pixelX,pixelY);
		}
		
		
		
	}

	public ItemStack getItemStack() {
		return items.getItemStack();
	}
	
	public boolean isEmpty() {
		return items == null;
	}
	
	public UIItem getItem() {
		return items;
	}
	public void setItem(UIItem items) {
		this.items = items;
	}
	public void toggleSelect() {
		selected = !selected;
	}
	
	public boolean isSelected() {
		return selected;
	}
}
