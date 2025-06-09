package survivalGame;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Conveyor extends TileObject implements ITickable {

	private List<Conveyor> inputConveyors = new ArrayList<>();
	private Conveyor targetConveyor;
	private Queue<Item> queue = new LinkedList<>(); //probably unneccesary queue
	private Tile targetTile;
	
	private int tickCount = 10;
	private int tickTime = 10;
	/** 
	@param rotation where 1,2,3,4 North, East, South, West are the rotation choices.
	*/
	public Conveyor(Tile parentTile, int rotation) {
		super();
		super.parentTile = parentTile;
		
		GameGraphics.getInstance();
		GameGraphics.register(this, 2);
		switch (rotation){
			case 1:
				targetTile = getTile(parentTile.x,parentTile.y + 1);
				break;
			case 2:
				targetTile = getTile(parentTile.x + 1, parentTile.y);
				break;
			case 3:
				targetTile = getTile(parentTile.x, parentTile.y - 1);
				break;
			case 4:
				targetTile = getTile(parentTile.x - 1, parentTile.y );
				break;
		}
		if (targetTile.getObject() instanceof Conveyor) {
			targetConveyor = (Conveyor) targetTile.getObject();
			targetConveyor.addInputConveyor(this);
		}
	}

	@Override
	public int getY() {
		return super.parentTile.y;
	}

	@Override
	public boolean isActive() {
		return super.parentTile.isActive();
	}

	@Override
	public void onTick() {
		tickCount--;
		if (tickCount == 0) {
			tickCount = tickTime;
		}
		
	}
	public Tile getTile(int x, int y) {
		
		int chunkSize = GameGraphics.getInstance().chunkSize;
		int chunkAmount = GameGraphics.getInstance().worldSize / chunkSize;
		TileChunk chunk = (TileChunk) GameGraphics.getInstance().chunks[chunkAmount * (x / chunkSize) + (y / chunkSize) ];
		
		int chunkX = x - (chunk.x * chunkSize);
		int chunkY = y - (chunk.y * chunkSize);
		
		System.out.println("//////////////////////////////////");
		Tile tile = chunk.tiles.get(chunkX * chunkSize + chunkY);
		return tile;
	}
	public void addInputConveyor(Conveyor conveyor) {
		inputConveyors.add(conveyor);
	}
	public void removeInputConveyor(Conveyor conveyor) {
		for (int i = inputConveyors.size() - 1; i >= 0; i--) {
			if (inputConveyors.get(i) == conveyor) {
				inputConveyors.remove(conveyor);
			}
		}
	}
	
}
