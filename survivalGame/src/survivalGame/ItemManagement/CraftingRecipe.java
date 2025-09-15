package survivalGame.ItemManagement;

import java.util.Map;

public class CraftingRecipe {
	
	Map<Item, Integer> recipe;
	private Item outputItem;
	
	public CraftingRecipe(Map<Item, Integer> recipe, Item outputItem) {
		this.recipe = recipe;
		this.outputItem = outputItem;
	}
	
	public Item craftItem(Map<Item, Integer> inventory) {
		
		if (!canCraft(inventory)) return null;
		
		//remove required items
		recipe.forEach((item, quantity) -> inventory.merge(item, quantity, (a,b) -> a - b ));
		inventory.merge(outputItem, 1, Integer::sum);
		return outputItem;
		
	}
	public boolean canCraft(Map<Item, Integer> inventory) {
		
		 for (Map.Entry<Item, Integer> entry : recipe.entrySet()) {
			 int available = inventory.getOrDefault(entry.getKey(), 0);
			 if (available < entry.getValue()) return false;
		 }

		return true;
	}
	
	public Item getOutputItem() {
		return outputItem;
	}
	
	public void printRecipe() {
		 for (Map.Entry<Item, Integer> entry : recipe.entrySet()) {
			 System.out.println(entry.getKey().getItemID() + ": " + entry.getValue());
		 }
	}
}
