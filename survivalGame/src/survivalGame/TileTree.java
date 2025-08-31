package survivalGame;

import java.awt.Graphics2D;

import graphics.GameGraphics;

public class TileTree extends TileObject{

	public TileTree(Tile parentTile) {
		super();
		super.parentTile = parentTile;
		super.verticalOffset = (int) -(50 + Math.random() * 25);
		GameGraphics.getInstance();
		GameGraphics.registerWorldObj(this, 2);
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return super.parentTile.y;
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return super.parentTile.isActive();
	}
	
	

}
