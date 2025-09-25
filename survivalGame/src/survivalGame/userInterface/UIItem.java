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
	private ItemStack itemStack;
	
	public UIItem(ItemStack itemStack, int pixelX, int pixelY) {
		this.itemStack = itemStack;
		this.pixelX = pixelX;
		this.pixelY = pixelY;
	}
	public UIItem(ItemStack itemStack) {
		this.itemStack = itemStack;
	}
	public UIItem(int pixelX, int pixelY) {
		itemStack = new ItemStack(null,0);
		this.pixelX = pixelX;
		this.pixelY = pixelY;
	}
	

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void renderUI(Graphics2D g, GameGraphics graphics) {
		renderUI(g,graphics,pixelX,pixelY);
	}
	
	public void renderUI(Graphics2D g, GameGraphics graphics,int pixelX, int pixelY) {
		if (isEmpty()) return;
		g.drawImage(itemStack.getItem().getTexture(),pixelX,pixelY,null);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", 1, 26));
		g.drawString(itemStack.getQuantity() + "", pixelX + 42, pixelY + 50);
	}
	
	public ItemStack getItemStack() {
		return itemStack;
	}
	public void setItemStack(ItemStack itemStack) {
		this.itemStack.setQuantity(itemStack.getQuantity()); 
		this.itemStack.setItem(itemStack.getItem()); 
	}
	public void clearItem() {
		this.itemStack.setQuantity(0); 
	}
	public boolean isEmpty(){
		return itemStack.getQuantity() == 0;
	}
}
