package survivalGame.ItemManagement;

import java.awt.image.BufferedImage;
import graphics.GameGraphics;

public abstract class Item  {
	
	private ItemID id;
	private BufferedImage texture;
	
	
	public Item(ItemID itemID) {
		id = itemID;
		texture = GameGraphics.getTextureManager().getTexture(itemID.getIdString());
	}
	
	public BufferedImage getTexture() {
		return texture;
	}
	
	public ItemID getItemID()
	{
		return id;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Item)) return false;
		Item item = (Item) o;
		
		return this.getItemID().equals(item.getItemID());

	}
	
    @Override
    public int hashCode() {
        return getItemID().hashCode();
    }

}
