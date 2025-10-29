package survivalGame;

public abstract class FactoryComponent extends PlacedObject implements ITickable {

	protected Tile parentTile;
	protected Direction rotation;
	public FactoryComponent(Tile parentTile, Direction rotation) {
		super(parentTile, rotation);
		this.parentTile = parentTile;
		this.rotation = rotation;
	}
}
