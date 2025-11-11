package survivalGame.userInterface;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import graphics.GameGraphics;
import graphics.UIRenderable;
import survivalGame.GameKeyListener;
import survivalGame.InputListener;
import survivalGame.Player;
import survivalGame.ItemManagement.Item;
import survivalGame.ItemManagement.ItemStack;
import survivalGame.ItemManagement.PlaceableItem;

public final class HotbarUI implements UIRenderable, GameKeyListener{

	HotbarSlot[] slots = new HotbarSlot[8];
	BufferedImage ui;
	final private int pixelX;
	final private int pixelY;
	
	private PlayerUI playerUI;
	private Player player;
	public HotbarUI(PlayerUI playerUI, Player player) {
		this.playerUI = playerUI;
		this.player = player;
		GameGraphics.getInstance().registerUI(this);
		InputListener.getInstance().registerKeyListener(this);
		
		ui = GameGraphics.getTextureManager().getTexture("Hotbar");

		pixelX = GameGraphics.getInstance().screenWidth / 2 - ui.getWidth() / 2;
		pixelY = GameGraphics.getInstance().screenHeight - ui.getHeight() - 10;
		
		for (int i = 0; i < 8; i++) {
			slots[i] = new HotbarSlot((pixelX + 30 + i * 100), pixelY + 15, playerUI);
		}
	}
	
	@Override
	public void keyPressed(int keyCode) {
		if (keyCode < KeyEvent.VK_1 || keyCode > KeyEvent.VK_8) return;
		
		System.out.println(keyCode + ", " + (keyCode - KeyEvent.VK_1));
		
		int index = keyCode - KeyEvent.VK_1;
		if (playerUI.isActive()) {
			setItemToSlot(slots[index]);
			return;
		}
		if (player.getSelectedHotbarSlot() == null) {
			player.setSelectedHotbarSlot(slots[index]);
			slots[index].setSelected(true);
			return;
		}
		slots[index].setSelected(true);
		player.getSelectedHotbarSlot().setSelected(false);
		player.setSelectedHotbarSlot(slots[index]);
		
	}
	//Sets item to slot
	private void setItemToSlot(HotbarSlot slot) {
		if (playerUI.getSelectedSlot() == null) return;
		
		if (playerUI.isActive()) {
			ItemStack selectedSlotItems = playerUI.getSelectedSlot().getItemStack();
			
			slot.setItem( selectedSlotItems.getItem() );
			return;
		}
		
	}
	
	public PlaceableItem getSelectedItem() {
		if (player.getSelectedHotbarSlot() == null) return null;
		Item item = player.getSelectedHotbarSlot().getItem();
		if (!(item instanceof PlaceableItem)) return null;
		return (PlaceableItem) item;
	}
	
	@Override
	public boolean isActive() {
		return true;
	}

	@Override
	public void renderUI(Graphics2D g, GameGraphics graphics) {
		g.drawImage(ui,pixelX,pixelY,graphics);
		for (HotbarSlot slot : slots) {
			slot.renderUI(g, graphics);
		}

	}





}
