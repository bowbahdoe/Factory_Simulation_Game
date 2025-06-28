package survivalGame;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Conveyor extends TileObject implements ITickable{

	private List<Conveyor> inputConveyors = new ArrayList<>();
	private Conveyor targetConveyor;
	private Item heldItem;
	private Tile targetTile;
	
	private int tickCount = 1;
	private int tickTime = 1;
	/** 
	@param rotation where 1,2,3,4 North, East, South, West are the rotation choices.
	*/
	public Conveyor(Tile parentTile, int rotation) {
		super();
		super.parentTile = parentTile;
		TickManager.getInstance();
		TickManager.addToTick((ITickable)this);
		
		GameGraphics.getInstance();
		GameGraphics.register(this, 1);
		
		switch (rotation){
			case 1:
				targetTile = getTile(parentTile.x,parentTile.y - 1);
				super.addTexture("ConveyorN", GameGraphics.getTextureManager());
				break;
			case 2:
				targetTile = getTile(parentTile.x + 1, parentTile.y);
				super.addTexture("ConveyorE", GameGraphics.getTextureManager());
				break;
			case 3:
				targetTile = getTile(parentTile.x, parentTile.y + 1);
				super.addTexture("ConveyorS", GameGraphics.getTextureManager());
				break;
			case 4:
				targetTile = getTile(parentTile.x - 1, parentTile.y );
				super.addTexture("ConveyorW", GameGraphics.getTextureManager());
				break;
		}
		if (targetTile.getObject() instanceof Conveyor) {
			System.out.println("Successfully found targetConveyor! ");
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
			if (targetConveyor == null) {
				if (targetTile.getObject() instanceof Conveyor) {
					targetConveyor = (Conveyor) targetTile.getObject();
				}
			}
			else if (targetConveyor.isEmpty() && targetConveyor != null) {
				if (heldItem != null) {
					System.out.println("Passed");
					passToTarget(heldItem);
					heldItem = null;
				}
				else {
					System.out.println("no held item");
				}

			}
		}
		
	}
	public void passToTarget(Item item) {
		if (item == null) {
			System.out.println("nothing to pass");
			return;
		}
		targetConveyor.recieveItem(item);
	}
	public Tile getTile(int x, int y) {
		
		int chunkSize = GameGraphics.getInstance().chunkSize;
		int chunkAmount = GameGraphics.getInstance().worldSize / chunkSize;
		TileChunk chunk = (TileChunk) GameGraphics.getInstance().chunks[chunkAmount * (x / chunkSize) + (y / chunkSize) ];
		
		int chunkX = x - (chunk.x * chunkSize);
		int chunkY = y - (chunk.y * chunkSize);
		
		Tile tile = chunk.tiles.get(chunkX * chunkSize + chunkY);
		return tile;
	}
	
	public void recieveItem(Item item) {
		item.fixToTile(this.parentTile);
		heldItem = item;
		System.out.println("!!! Recieved Item !!! ");
		
	}
	public boolean isEmpty() {
		return heldItem == null;
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
