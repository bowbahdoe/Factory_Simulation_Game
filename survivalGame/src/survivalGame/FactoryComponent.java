package survivalGame;

public abstract class FactoryComponent extends PlacedObject  {

	public void process() {
		// default: do nothing
	}
	protected Tile parentTile;
	protected Direction rotation;
	public FactoryComponent(Tile parentTile, Direction rotation) {
		super(parentTile, rotation);
		this.parentTile = parentTile;
		this.rotation = rotation;
	}
}
