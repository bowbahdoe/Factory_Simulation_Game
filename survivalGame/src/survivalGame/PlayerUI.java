package survivalGame;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import graphics.GameGraphics;
import graphics.UIRenderable;

public class PlayerUI implements UIRenderable{

	private boolean active = false;
	
	BufferedImage UI;
	InventorySlot[] slots = new InventorySlot[40];
	
	Map<Item, Integer> inventory = new HashMap<>();
	List<UIRenderable> allUI = new ArrayList<>();
	
	public PlayerUI() {
		GameGraphics.registerUI(this);
		UI = GameGraphics.getTextureManager().getTexture("PlayerUI");
		//allUI.add();
		int x = GameGraphics.getInstance().getSize().width / 2 - UI.getWidth() / 2;
		int y = GameGraphics.getInstance().getSize().height / 2 - UI.getHeight() / 2;
		for (int i = 0; i < 40; i ++) {
			int pixelX = -x + 500 + (i % 5) * 70;
			int pixelY = -y + 140 + (i / 5) * 70;
			slots[i] = new InventorySlot(pixelX, pixelY,this);
			allUI.add(slots[i]);
		}
	}
	public void toggle() {
		active = !active;
	}
	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public void renderUI(Graphics2D g, GameGraphics graphics) {
		int x = graphics.getSize().width / 2 - UI.getWidth() / 2;
		int y = graphics.getSize().height / 2 - UI.getHeight() / 2;
		g.drawImage(UI, x,y, graphics);
		
		for (UIRenderable ui : allUI) {
			ui.renderUI(g, graphics);
		}
	}
	
	
	
	public void organiseToSlots() {
	    for (InventorySlot slot : slots) {
	    	slot.setItem(null);
	    }

	    int invCounter = 0;

	    for (Map.Entry<Item, Integer> entry : inventory.entrySet()) {
	        Item item = entry.getKey();
	        int quantity = entry.getValue();

	        if (quantity <= 0) continue;

	        //merge into existing slots
	        for (InventorySlot slot : slots) {
	            if (!slot.isEmpty() && slot.getItemStack().getItem().getItemID().equals(item.getItemID())) {
	                ItemStack stack = slot.getItemStack();
	                int spaceLeft = ItemStack.MAX_STACK - stack.getQuantity();

	                if (spaceLeft > 0) {
	                    int toAdd = Math.min(quantity, spaceLeft);
	                    stack.setQuantity(stack.getQuantity() + toAdd);
	                    quantity -= toAdd;
	                    if (quantity == 0) break;
	                }
	            }
	        }

	        // if quantity left, make new stacks until there is none left 
	        while (quantity > 0 && invCounter < slots.length) {
	            InventorySlot slot = slots[invCounter];
	            if (slot.isEmpty()) {
	                int toPlace = Math.min(quantity, ItemStack.MAX_STACK); 
	                ItemStack newStack = new ItemStack(item, toPlace);
	                slot.setItem(new UIItem(newStack, slot.pixelX, slot.pixelY));
	                quantity -= toPlace;
	            }
	            invCounter++;
	        }
	    }
	}
	public void mergeInventory(Map<Item, Integer> inv) {
		inv.forEach((key,value) -> inventory.merge(key,value, Integer::sum ));

		organiseToSlots();
	}

}
