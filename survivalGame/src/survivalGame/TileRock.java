package survivalGame;

import graphics.GameGraphics;

public class TileRock extends TileObject {
	
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
	
	
}
