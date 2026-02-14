package survivalGame.TileManagement;

public enum TileObjectID {
	TREE(1),
	ROCK(2),
	CONVEYOR(3),
	TREE_HARVESTER(4),
	ROCK_DRILLER(5),
	CONVEYOR_SPLITTER_R(6),
	CONVEYOR_SPLITTER_L(7),
	PLANKER(8);
	
	public final byte id;
	    
	TileObjectID(int id) {
			this.id = (byte) id;
	}
	
	public static TileObjectID fromId(byte id) {
		for (TileObjectID t : TileObjectID.values()) {
			if (t.id == id) return t; 
		}
		throw new IllegalArgumentException("Invalid TileType ID: " + id);
	}
}
