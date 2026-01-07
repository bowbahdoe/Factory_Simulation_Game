package survivalGame;

public enum TileType {
	GRASS(1);
	
    public final byte id;
    
	TileType(int id) {
		this.id = (byte) id;
	}
	
	static TileType fromId(byte id) {
		for (TileType t : TileType.values()) {
			if (t.id == id) return t; 
		}
		throw new IllegalArgumentException("Invalid TileType ID: " + id);
	}
}
