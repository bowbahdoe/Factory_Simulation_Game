package survivalGame;

public enum Direction {
	NORTH((byte)0 ,1 << 0), // 0001
	EAST((byte) 1  ,1 << 1),  // 0010
	SOUTH((byte)2 ,1 << 2), // 0100
	WEST((byte) 3  ,1 << 3);  // 1000
	
	private int mask = 0;
	private byte ID = 0;
	
	Direction(byte ID, int mask){
		this.ID = ID;
		this.mask = mask;
	}
	
	public int getRotationMask() {
		return mask;
	}
	
	public int getRotationID() {
		return ID;
	}
	
	public Direction rotatedClockwise() {
        return values()[(this.ID + 1) % 4];
    }

    public Direction rotatedAntiClockwise() {
        return values()[(this.ID + 3) % 4];
    }
  
	
}
