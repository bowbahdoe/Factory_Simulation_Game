package survivalGame.userInterface;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import graphics.GameGraphics;
import graphics.UIRenderable;
import survivalGame.ItemManagement.ItemStack;

public class UIItem implements UIRenderable{

	private int pixelX;
	private int pixelY;
	private boolean ui_active = true;
	private ItemStack itemStack;
	
	public UIItem(ItemStack itemStack, int pixelX, int pixelY) {
		this.itemStack = itemStack;
		this.pixelX = pixelX;
		this.pixelY = pixelY;
	}
	public UIItem(ItemStack itemStack) {
		this.itemStack = itemStack;
	}

	

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void renderUI(Graphics2D g, GameGraphics graphics) {
		// TODO Auto-generated method stub
		g.drawImage(itemStack.getItem().getTexture(),pixelX,pixelY,null);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", 1, 26));
		g.drawString(itemStack.getQuantity() + "", pixelX + 42, pixelY + 50);
	}
	
	public ItemStack getItemStack() {
		return itemStack;
	}
	public void setItemStack(ItemStack itemStack) {
		this.itemStack = itemStack;
	}
}
