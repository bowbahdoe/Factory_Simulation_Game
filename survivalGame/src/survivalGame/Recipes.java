package survivalGame;

import java.util.Map;

import survivalGame.ItemManagement.CraftingRecipe;
import survivalGame.ItemManagement.ItemFactory;
import survivalGame.ItemManagement.ItemID;

public enum Recipes {
	CONVEYOR(new CraftingRecipe(
			Map.of(ItemFactory.createItem(ItemID.WOOD), 3),
			ItemFactory.createItem(ItemID.CONVEYOR)
			));

	private final CraftingRecipe recipe;
	
	Recipes(CraftingRecipe recipe) {
		this.recipe = recipe;
	}

	public CraftingRecipe getRecipe() {
        return recipe;
    }
}
