package survivalGame.ItemManagement;

public enum ItemID {
	//IMPORTANT: id must be the name of the item texture
	CONVEYOR("ConveyorItem", ItemType.PLACEABLE),
	TREEHARVESTER("TreeHarvesterItem", ItemType.PLACEABLE),
	PLANKER("PlankerItem", ItemType.PLACEABLE),
    WOOD("WoodItem", ItemType.RESOURCE),
	LOG("LogItem", ItemType.RESOURCE);
	
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
