package survivalGame;

public enum Direction {
	NORTH(0,1 << 0), // 0001
	EAST(1,1 << 1),  // 0010
	SOUTH(2,1 << 2), // 0100
	WEST(3,1 << 3);  // 1000
	
	private int mask = 0;
	private int index = 0;
	
	Direction(int index, int mask){
		this.index = index;
		this.mask = mask;
	}
	
	public int getRotationMask() {
		return mask;
	}
	
	public Direction rotatedClockwise() {
        return values()[(this.index + 1) % 4];
    }

    public Direction rotatedAntiClockwise() {
        return values()[(this.index + 3) % 4];
    }
  
	
}
