package survivalGame;

import java.util.HashMap;
import java.util.Map;

import survivalGame.ItemManagement.CraftingRecipe;
import survivalGame.ItemManagement.Item;
import survivalGame.ItemManagement.ItemID;

public enum Recipes {
	CONVEYOR(new CraftingRecipe(
			Map.of(new Item(ItemID.WOOD.getId()), 3),
			new Item(ItemID.CONVEYOR.getId())
			));

	private final CraftingRecipe recipe;
	
	Recipes(CraftingRecipe recipe) {
		this.recipe = recipe;
	}

	public CraftingRecipe getRecipe() {
        return recipe;
    }
}
