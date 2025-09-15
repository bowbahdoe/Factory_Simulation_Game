package survivalGame.ItemManagement;

import java.awt.image.BufferedImage;
import graphics.GameGraphics;

public class Item  {
	
	private String id;
	private BufferedImage texture;
	
	public Item(String id) {
		this.id = id;
		texture = GameGraphics.getTextureManager().getTexture(id);
	}
	
	public BufferedImage getTexture() {
		return texture;
	}
	
	public String getItemID()
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
