package survivalGame.tileObjects;

import graphics.GameGraphics;
import survivalGame.Tile;
import survivalGame.TileObjectID;

public class TileRock extends TileObject {
	
	public static final TileObjectID ID = TileObjectID.CONVEYOR;
	
	public TileRock(Tile parentTile) {
		super(parentTile);
		GameGraphics.registerWorldObj(this, 2);
	}

	@Override
	public int getY() {
		return super.parentTile.y;
	}

	@Override
	public boolean isActive() {
		return super.parentTile.isActive();
	}
	
	@Override
	public TileObjectID getTileObjectID() {
		return ID;
	}
	
}
