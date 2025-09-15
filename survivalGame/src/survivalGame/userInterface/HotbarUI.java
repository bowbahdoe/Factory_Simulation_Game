package survivalGame.userInterface;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import graphics.GameGraphics;
import graphics.UIRenderable;
import survivalGame.GameKeyListener;
import survivalGame.InputListener;

public class HotbarUI implements UIRenderable, GameKeyListener{

	InventorySlot[] slots = new InventorySlot[8];
	BufferedImage ui;
	final private int pixelX;
	final private int pixelY;
	public HotbarUI(PlayerUI playerUI) {
		
		GameGraphics.getInstance().registerUI(this);
		InputListener.getInstance().registerKeyListener(this);
		
		ui = GameGraphics.getTextureManager().getTexture("Hotbar");

		pixelX = GameGraphics.getInstance().screenWidth / 2 - ui.getWidth() / 2;
		pixelY = GameGraphics.getInstance().screenHeight - ui.getHeight() - 10;
		
		slots[0] = new InventorySlot(pixelX + 50, pixelY - 50, playerUI);
	}
	
	@Override
	public void keyPressed(int keyCode) {
		if (keyCode < KeyEvent.VK_1 || keyCode > KeyEvent.VK_8) return;
		
		System.out.println(keyCode);
		manageSlotSelection(slots[keyCode - KeyEvent.VK_1]);
	}
	
	public void manageSlotSelection(InventorySlot slot) {
		System.out.println("-------------- Slot selected ");
	}
	
	@Override
	public boolean isActive() {
		return true;
	}

	@Override
	public void renderUI(Graphics2D g, GameGraphics graphics) {
		g.drawImage(ui,pixelX,pixelY,graphics);
	}





}
