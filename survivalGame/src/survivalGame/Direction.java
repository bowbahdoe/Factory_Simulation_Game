package survivalGame;

public enum Direction {
	NORTH(0),
	EAST(1),
	SOUTH(2),
	WEST(3);
	
	private int rotation = 0;
	
	Direction(int rotation){
		this.rotation = rotation;
	}
	
	public int getRotation() {
		return rotation;
	}
	
	public Direction rotatedClockwise() {
        return values()[(this.rotation + 1) % 4];
    }

    public Direction rotatedAntiClockwise() {
        return values()[(this.rotation + 3) % 4];
    }
  
	
}
