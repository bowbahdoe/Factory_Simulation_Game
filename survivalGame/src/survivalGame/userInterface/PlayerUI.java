package survivalGame.userInterface;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import graphics.GameGraphics;
import graphics.UIRenderable;
import survivalGame.GameKeyListener;
import survivalGame.InputListener;
import survivalGame.InventoryListener;
import survivalGame.ItemManagement.Item;
import survivalGame.ItemManagement.ItemStack;

public class PlayerUI implements UIRenderable, GameKeyListener, InventoryListener{

	private boolean active = false;
	
	BufferedImage UI;
	InventorySlot[] slots = new InventorySlot[40];
	
	Map<Item, Integer> inventory = new HashMap<>();
	List<UIRenderable> allUI = new ArrayList<>();
	
	private InventorySlot lastSelectedSlot;
	
	List<InventoryListener> listeners = new ArrayList<>();
	public PlayerUI() {
		GameGraphics.getInstance().registerUI(this);
		InputListener.getInstance().registerKeyListener(this);
		UI = GameGraphics.getTextureManager().getTexture("PlayerUI");
		//allUI.add();
		int x = GameGraphics.getInstance().screenWidth / 2 - UI.getWidth() / 2;
		int y = GameGraphics.getInstance().screenHeight / 2 - UI.getHeight() / 2;
		System.out.println(GameGraphics.getInstance().screenWidth + ", " + GameGraphics.getInstance().screenHeight);
		for (int i = 0; i < 40; i ++) {

			int pixelX = x + (i % 5) * 70 + 40;
			int pixelY = y + (i / 5) * 70 + 80;
			slots[i] = new InventorySlot(pixelX, pixelY,this);
			allUI.add(slots[i]);
		}
		CraftingUI craftingUI = new CraftingUI(this,new Rectangle(x + 430, y + 80,UI.getWidth()/2,UI.getHeight()));
		new HotbarUI(this);
		allUI.add(craftingUI);
		listeners.add(craftingUI);
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
	    	slot.clearItem();
	    }

	    int invCounter = 0;
	    
	    for (Map.Entry<Item, Integer> entry : inventory.entrySet()) {
	        Item item = entry.getKey();
	        int quantity = entry.getValue();

	        if (quantity <= 0) break;

	        //merge into existing slots with same item
	        for (InventorySlot slot : slots) {
	            if (slot.isEmpty() ||  !slot.getItemStack().getItem().getItemID().equals(item.getItemID()) ) continue;
	           
	            ItemStack stack = slot.getItemStack();
                int spaceLeft = ItemStack.MAX_STACK - stack.getQuantity();

                int toAdd = Math.min(quantity, spaceLeft);
                stack.setQuantity(stack.getQuantity() + toAdd);
                quantity -= toAdd;
                if (quantity == 0) break;
	        }
	        	
	        // if quantity left, make new stacks until there is none left 
	        while (quantity > 0 && invCounter < slots.length) {
	        	InventorySlot slot = slots[invCounter];
	            if (!slot.isEmpty()) continue;
	            
	            int toPlace = Math.min(quantity, ItemStack.MAX_STACK); 
                ItemStack newStack = new ItemStack(item, toPlace);
                slot.setItemStack(newStack);
                quantity -= toPlace;
                
	            invCounter++;
	        }
	    }
	}

	public void mergeInventory(Map<Item, Integer> inv) {
		inv.forEach((key,value) -> inventory.merge(key,value, Integer::sum ));
		onInventoryChanged(); //including themselves
	}
	@Override
	public void keyPressed(int keyCode) {
		
		if (keyCode == KeyEvent.VK_I) {
			toggle();
		}
		
	}
	
	@Override
	public void onInventoryChanged() {
		organiseToSlots();
		listeners.forEach(InventoryListener::onInventoryChanged);
	}
	
	public void selectSlot(InventorySlot slot) {
		
		if (slot == lastSelectedSlot) {slot.toggleSelect(); return;}
		
		if (lastSelectedSlot != null) lastSelectedSlot.setSelected(false);;
		lastSelectedSlot = slot;
		slot.toggleSelect();
	}
	
	public InventorySlot getSelectedSlot() {
		if (! lastSelectedSlot.isSelected()) return null;
		
		return lastSelectedSlot;
	}
}
