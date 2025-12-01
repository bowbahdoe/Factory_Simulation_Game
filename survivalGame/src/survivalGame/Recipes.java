package survivalGame;

import java.util.Map;

import survivalGame.ItemManagement.CraftingRecipe;
import survivalGame.ItemManagement.ItemFactory;
import survivalGame.ItemManagement.ItemID;

public enum Recipes {
	CONVEYOR(new CraftingRecipe(
			Map.of(ItemID.WOOD, 3),
			ItemID.CONVEYOR
			)),
	TREEHARVESTER(new CraftingRecipe(
			Map.of(ItemID.WOOD, 3),
			ItemID.TREEHARVESTER
			)),
	CONVEYORSPLITTER_R(new CraftingRecipe(
			Map.of(ItemID.WOOD, 6),
			ItemID.CONVEYORSPLITTER_R
			)),
	CONVEYORSPLITTER_L(new CraftingRecipe(
			Map.of(ItemID.WOOD, 6),
			ItemID.CONVEYORSPLITTER_L
			)),
	PLANKER(new CraftingRecipe(
			Map.of(ItemID.WOOD, 3),
			ItemID.PLANKER
			)),
	ROCKDRILLER(new CraftingRecipe(
			Map.of(ItemID.WOOD, 3, 
				   ItemID.CONVEYOR, 1),
			ItemID.ROCKDRILLER
			));

	private final CraftingRecipe recipe;
	
	Recipes(CraftingRecipe recipe) {
		this.recipe = recipe;
	}

	public CraftingRecipe getRecipe() {
        return recipe;
    }
}
