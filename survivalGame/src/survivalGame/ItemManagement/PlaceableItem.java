package survivalGame.ItemManagement;

import survivalGame.Direction;
import survivalGame.PlaceablesFactory;
import survivalGame.PlacementInfo;
import survivalGame.Tile;
import survivalGame.tileObjects.TileObject;

public class PlaceableItem extends Item {

	public PlaceableItem(ItemID id) {
		super(id);
	}

	public TileObject place(Tile tile, Direction placementRotation) {
		TileObject placedObject = PlaceablesFactory.createPlaceable(this.getItemID(), new PlacementInfo(tile, placementRotation));
		tile.setObject(placedObject);
		return placedObject;
	}
}
