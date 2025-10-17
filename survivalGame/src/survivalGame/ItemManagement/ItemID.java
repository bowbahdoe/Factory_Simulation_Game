package survivalGame.ItemManagement;

public enum ItemID {
    WOOD("WoodItem", ItemType.RESOURCE),
    CONVEYOR("ConveyorItem", ItemType.PLACEABLE);

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
