package survivalGame.TileManagement;

public enum TileType {
	GRASS((byte) 1);
	
    private final byte id;
    
	TileType(byte id) {
		this.id = (byte) id;
	}
	
	public static TileType fromId(byte id) {
		for (TileType t : TileType.values()) {
			if (t.id == id) return t; 
		}
		throw new IllegalArgumentException("Invalid TileType ID: " + id);
	}
	public byte getID() {
		return id;
	}
}
