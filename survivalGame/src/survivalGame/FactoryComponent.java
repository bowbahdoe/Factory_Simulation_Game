package survivalGame;

import survivalGame.TileManagement.Tile;
import survivalGame.TileManagement.TileProvider;
import survivalGame.tileObjects.TileObject;

public abstract class FactoryComponent extends TileObject  {
	
	private Direction rotation;
	
	public void process() {
		// default: do nothing
	}
	
	protected Tile parentTile;
	
	public FactoryComponent(Tile parentTile, Direction rotation) {
		super(parentTile);
		this.parentTile = parentTile;
		this.setRotation(rotation);
	}
	public FactoryComponent(Tile parentTile) {
		super(parentTile);
		this.parentTile = parentTile;
	}
	
	/**
	 * This function returns the Tile in the direction of rotation.
	 * @param rotation or direction to get the next Tile from.
	 * @return the {@link Tile} that rotation is pointing towards.
	 */
	protected Tile getTargetTile(Direction rotation) {
		Tile targetTile = null;
		switch (rotation){
		case NORTH:
			targetTile = TileProvider.world_AccessTile(parentTile.x,parentTile.y - 1);
			break;
		case EAST:
			targetTile = TileProvider.world_AccessTile(parentTile.x + 1, parentTile.y);
			break;
		case SOUTH:
			targetTile = TileProvider.world_AccessTile(parentTile.x, parentTile.y + 1);
			break;
		case WEST:
			targetTile = TileProvider.world_AccessTile(parentTile.x - 1, parentTile.y);
			break;
		}
		return targetTile;
	}
	
	public Direction getRotation() {
		return rotation;
	}
	
	protected void setRotation(Direction rotation) {
		this.rotation = rotation;
	}
	
	public abstract void removeObject();
	
}
