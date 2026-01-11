package survivalGame;

public enum TileObjectID {
	TREE(1),
	ROCK(2),
	CONVEYOR(3),
	TREE_HARVESTER(4),
	ROCK_DRILLER(5),
	CONVEYOR_SPLITTER(6),
	PLANKER(7);
	
	public final byte id;
	    
	TileObjectID(int id) {
			this.id = (byte) id;
	}
	
	static TileObjectID fromId(byte id) {
		for (TileObjectID t : TileObjectID.values()) {
			if (t.id == id) return t; 
		}
		throw new IllegalArgumentException("Invalid TileType ID: " + id);
	}
}
