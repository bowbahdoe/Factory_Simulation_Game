package survivalGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.HashSet;
import java.util.Set;

import graphics.GameGraphics;

public class Conveyor extends TileObject implements ITickable{

	private Conveyor inputConveyor;
	private Conveyor targetConveyor;
	private WorldItem heldItem;
	private Tile targetTile;
	private boolean isRoot; //Purely for visual purposes
	private int beltKey = -1;
	/** 
	@param rotation where 0,1,2,3 North, East, South, West are the rotation choices.
	*/
	public Conveyor(Tile parentTile, int rotation) {
		super();
		super.parentTile = parentTile;

		GameGraphics.getInstance();
		GameGraphics.registerWorldObj(this, 2);
		
		ConveyorManager.getInstance();
		ConveyorManager.registerConveyor(this);
		switch (rotation){
			case 0:
				targetTile = getTile(parentTile.x,parentTile.y - 1);
				super.addTexture("ConveyorN", GameGraphics.getTextureManager());
				break;
			case 1:
				targetTile = getTile(parentTile.x + 1, parentTile.y);
				super.addTexture("ConveyorE", GameGraphics.getTextureManager());
				break;
			case 2:
				targetTile = getTile(parentTile.x, parentTile.y + 1);
				super.addTexture("ConveyorS", GameGraphics.getTextureManager());
				break;
			case 3:
				targetTile = getTile(parentTile.x - 1, parentTile.y);
				super.addTexture("ConveyorW", GameGraphics.getTextureManager());
				break;
			default:
				System.err.println("ERROR: Conveyor rotation is wrong! ");
		}
		
		if (findParents()) {
			//If no target, inherit any other key
			System.out.println("Inherited from parent");
			beltKey = inputConveyor.getBeltKey();
			if (targetTile.getObject() instanceof Conveyor) {
				targetConveyor = (Conveyor) targetTile.getObject();
			}
			//And therefore that parent must be the leaf, so make this new leaf.
			ConveyorManager.asignLeaf(beltKey, this);
		}	
		else if (targetTile.getObject() instanceof Conveyor && !((Conveyor) targetTile.getObject()).hasInputConveyor() ) {
			//if conveyor infront, attach to conveyor and update beltkey
			System.out.println("Successfully found targetConveyor! ");
			targetConveyor = (Conveyor) targetTile.getObject();
			targetConveyor.addInputConveyor(this);
			
			beltKey = targetConveyor.getBeltKey();
		}
		
		else {
			//Else just make new one
			System.out.println("New beltkey made!");
			beltKey = ConveyorManager.generateConveyorKey(this);
			isRoot = true;
			ConveyorManager.asignLeaf(beltKey, this);
		}
		
	}
	public boolean findParents() {
		Tile[] surroundings = new Tile[4];
		surroundings[0] = getTile(parentTile.x,parentTile.y - 1);
		surroundings[1] = getTile(parentTile.x + 1, parentTile.y);
		surroundings[2] = getTile(parentTile.x, parentTile.y + 1);
		surroundings[3] = getTile(parentTile.x - 1, parentTile.y);
		for (Tile tile : surroundings) {
			//check surrounding tiles for conveyors, make them the input if they point towards you.
			//Ignore the one you point towards too. 
			
			if (targetTile != tile && tile.getObject() instanceof Conveyor) {
				Conveyor conv = (Conveyor) tile.getObject();
				if (conv.isPointingAt(this.parentTile)) {
					inputConveyor = conv;
					inputConveyor.targetConveyor = this;
					System.out.println("Found tile with conveyor at " + tile.x + ", " + tile.y);
				}
				
				
			}
		}
		return inputConveyor != null;
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

	    if (targetConveyor != null && targetConveyor.isEmpty() && targetConveyor != null) {
			if (heldItem != null) {
				passToTarget(heldItem);
			}
			else {
			}

		}
		
	}
	public void passToTarget(WorldItem item) {
		if (item == null) {
			return;
		}
		if ( ! targetConveyor.isEmpty()) return;
		
		targetConveyor.recieveItem(item);
		heldItem = null;
	}
	private Tile getTile(int x, int y) {
		
		int chunkSize = GameGraphics.getInstance().chunkSize;
		int chunkAmount = GameGraphics.getInstance().worldSize / chunkSize;
		TileChunk chunk = (TileChunk) GameGraphics.getInstance().chunks[chunkAmount * (x / chunkSize) + (y / chunkSize) ];
		
		int chunkX = x - (chunk.x * chunkSize);
		int chunkY = y - (chunk.y * chunkSize);
		
		Tile tile = chunk.tiles.get(chunkX * chunkSize + chunkY);
		return tile;
	}
	
	public void recieveItem(WorldItem item) {
		item.fixToTile(this.parentTile);
		heldItem = item;
		System.out.println("!!! Recieved Item !!! ");
		
	}
	public boolean isEmpty() {
		return heldItem == null;
	}
	public void addInputConveyor(Conveyor conveyor) {
		if (inputConveyor == null) {
			inputConveyor = conveyor;
			if (targetConveyor == null) {
				ConveyorManager.registerConveyor(conveyor);
			}
			
		}
	}
	public void removeInputConveyor(Conveyor conveyor) {
		inputConveyor = null;
	}
	
	public int getBeltKey() {
		return beltKey;
	}
	
	public Conveyor getTargetConveyor() {
		return targetConveyor;
	}
	
	public Conveyor getInputConveyor() {
		return inputConveyor;
	}
	public boolean hasInputConveyor() {
		return inputConveyor != null;
	}
	public boolean isPointingAt(Tile tile) {
		return targetTile == tile;
	}
	@Override
	public void render(Graphics2D g, GameGraphics graphics) {
		if (toRender) {
			int pixelX = parentTile.pixelX;
			int pixelY = parentTile.pixelY;
			g.drawImage(texture, pixelX, pixelY + verticalOffset, null); 
			
			int fontSize = 19;
			Font largeFont = new Font("Arial", Font.BOLD, fontSize);
		    g.setFont(largeFont);
		    g.setColor(Color.blue);
			g.drawString("" + beltKey ,  pixelX, pixelY + verticalOffset + fontSize);
			if (isRoot) {
				g.setColor(Color.MAGENTA);
				g.fillRect(pixelX + 70, pixelY, 30, 30);
				
				g.setColor(Color.BLACK);
				g.drawString(beltKey + "", pixelX + 73, pixelY + 24);
			}
		}
		
	}
	public WorldItem collectItem() {
		
		WorldItem item = heldItem;
		heldItem.setActive(false);
		heldItem = null;
		return item;
		
	}
}
