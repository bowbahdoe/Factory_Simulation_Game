package survivalGame.ItemManagement;

public enum ItemID {
	//IMPORTANT: id must be the name of the item texture
	CONVEYOR("ConveyorItem", ItemType.PLACEABLE),
	CONVEYORSPLITTER_R("ConveyorSplitterItemR", ItemType.PLACEABLE),
	CONVEYORSPLITTER_L("ConveyorSplitterItemL", ItemType.PLACEABLE),
	TREEHARVESTER("TreeHarvesterItem", ItemType.PLACEABLE),
	ROCKDRILLER("RockDrillerItem", ItemType.PLACEABLE),
	PLANKER("PlankerItem", ItemType.PLACEABLE),
    WOOD("WoodItem", ItemType.RESOURCE),
	LOG("LogItem", ItemType.RESOURCE),
	ROCK("RockItem", ItemType.RESOURCE);
	
    private final String id;
    private final ItemType itemType;
    
    ItemID(String id, ItemType itemType) {
        this.id = id;
        this.itemType = itemType;
    }

    public String getIdString() {
        return id;
    }
    
    public ItemType getItemType() {
    	return itemType;
    }
}
