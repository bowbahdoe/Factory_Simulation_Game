package survivalGame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import graphics.GameGraphics;
import graphics.UIRenderable;
import graphics.WorldRenderable;

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
}
