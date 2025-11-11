package survivalGame;

import java.util.Map;

import survivalGame.ItemManagement.CraftingRecipe;
import survivalGame.ItemManagement.ItemFactory;
import survivalGame.ItemManagement.ItemID;

public enum Recipes {
	CONVEYOR(new CraftingRecipe(
			Map.of(ItemFactory.createItem(ItemID.WOOD), 3),
			ItemFactory.createItem(ItemID.CONVEYOR)
			)),
	
	TREEHARVESTER(new CraftingRecipe(
			Map.of(ItemFactory.createItem(ItemID.WOOD), 3),
			ItemFactory.createItem(ItemID.TREEHARVESTER)
			)),
	CONVEYORSPLITTER_R(new CraftingRecipe(
			Map.of(ItemFactory.createItem(ItemID.WOOD), 6),
			ItemFactory.createItem(ItemID.CONVEYORSPLITTER_R)
			)),
	CONVEYORSPLITTER_L(new CraftingRecipe(
			Map.of(ItemFactory.createItem(ItemID.WOOD), 6),
			ItemFactory.createItem(ItemID.CONVEYORSPLITTER_L)
			)),
	PLANKER(new CraftingRecipe(
			Map.of(ItemFactory.createItem(ItemID.WOOD), 3),
			ItemFactory.createItem(ItemID.PLANKER)
			));
	private final CraftingRecipe recipe;
	
	Recipes(CraftingRecipe recipe) {
		this.recipe = recipe;
	}

	public CraftingRecipe getRecipe() {
        return recipe;
    }
}
