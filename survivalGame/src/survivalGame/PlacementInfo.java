package survivalGame;

public class PlacementInfo {
	//Wrapper class for PlaceableFactory
	//Did this since the Tile placed is yet to be decided
	//Only the class which is going to be instantiated is decided. 
	final Tile tile;
	final Direction direction;
	
	public PlacementInfo(Tile tile, Direction direction) {
		this.tile = tile;
		this.direction = direction;
	}
}
