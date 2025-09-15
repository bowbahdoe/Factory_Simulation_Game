package survivalGame.userInterface;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import graphics.GameGraphics;
import graphics.UIRenderable;
import survivalGame.InventoryListener;
import survivalGame.Recipes;
import survivalGame.ItemManagement.Item;

public class CraftingUI implements UIRenderable, InventoryListener {
	
	private Map<Item, Integer> inventory;
	private PlayerUI playerUI;
	private List<CraftButton> craftButtons = new ArrayList<>();
	public CraftingUI(PlayerUI playerUI,Rectangle bounds) {
		
		this.inventory = playerUI.inventory;
		this.playerUI = playerUI;
	
		//1300,470 are bounds corner
		
		craftButtons.add(new CraftButton(bounds.x,bounds.y, this, Recipes.CONVEYOR.getRecipe()));
		
				

	}
	@Override
	public boolean isActive() {
		return playerUI.isActive();
	}

	@Override
	public void renderUI(Graphics2D g, GameGraphics graphics) {
		for (CraftButton button : craftButtons) {
			button.renderUI(g, graphics);
		}
	}


	public Map<Item, Integer> getInventory() {
		return inventory;
	}
	public void printInventory() {
		 for (Map.Entry<Item, Integer> entry : inventory.entrySet()) {
			 System.out.println(entry.getKey().getItemID() + ": " + entry.getValue());
		 }
	}
	
	@Override
	public void onInventoryChanged() {
		System.out.println("Inventory changed");
		craftButtons.forEach(CraftButton::onInventoryChanged);
	}
	
	public PlayerUI getPlayerUI() {
		return playerUI;
	}
	

}
