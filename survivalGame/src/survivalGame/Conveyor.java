package survivalGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import graphics.GameGraphics;
import survivalGame.ItemManagement.WorldItem;

public class Conveyor extends TileObject implements ITickable{

	private Conveyor inputConveyor;
	private Conveyor targetConveyor;
	private WorldItem heldItem;
	private Tile targetTile;
	private boolean isRoot; //Purely for visual purposes
	private int beltKey = -1;

	private Direction rotation;
	
	int mask = 0;
	
	public Conveyor(Tile parentTile, Direction rotation) {
		super();
		super.parentTile = parentTile;
		
		this.rotation = rotation;
		GameGraphics.getInstance().registerWorldObj(this, 2);
		
		ConveyorManager.getInstance();
		switch (rotation){
			case NORTH:
				targetTile = TileProvider.world_AccessTile(parentTile.x,parentTile.y - 1);
				super.addTexture("ConveyorN", GameGraphics.getTextureManager());
				break;
			case EAST:
				targetTile = TileProvider.world_AccessTile(parentTile.x + 1, parentTile.y);
				super.addTexture("ConveyorE", GameGraphics.getTextureManager());
				break;
			case SOUTH:
				targetTile = TileProvider.world_AccessTile(parentTile.x, parentTile.y + 1);
				super.addTexture("ConveyorS", GameGraphics.getTextureManager());
				break;
			case WEST:
				targetTile = TileProvider.world_AccessTile(parentTile.x - 1, parentTile.y);
				super.addTexture("ConveyorW", GameGraphics.getTextureManager());
				break;
		}
		
		if (findParents()) {
			//If no target, inherit any other key
			beltKey = inputConveyor.getBeltKey();
			if (targetTile.getObject() instanceof Conveyor) {
				targetConveyor = (Conveyor) targetTile.getObject();
				targetConveyor.changeSprite(this);
			}
			//And therefore that parent must be the leaf, so make this new leaf.
			ConveyorManager.asignLeaf(beltKey, this);
		}	
		else if (targetTile.getObject() instanceof Conveyor  ) {
			//if conveyor infront, attach to conveyor and update beltkey
			targetConveyor = (Conveyor) targetTile.getObject();
			targetConveyor.changeSprite(this);
			
			if (((Conveyor) targetTile.getObject()).hasInputConveyor()) return;
			targetConveyor.addInputConveyor(this);
			beltKey = targetConveyor.getBeltKey();
			becomeConveyorLeaf();
		}
		
		else {
			//Else just make new one
			becomeConveyorLeaf();
		}
		
	}
	private boolean findParents() {
		Tile[] surroundings = new Tile[4];
		surroundings[0] = TileProvider.world_AccessTile(parentTile.x,parentTile.y - 1);
		surroundings[1] = TileProvider.world_AccessTile(parentTile.x + 1, parentTile.y);
		surroundings[2] = TileProvider.world_AccessTile(parentTile.x, parentTile.y + 1);
		surroundings[3] = TileProvider.world_AccessTile(parentTile.x - 1, parentTile.y);
		for (Tile tile : surroundings) {
			//check surrounding tiles for conveyors, make them the input if they point towards you.
			//Ignore the one you point towards too. 
			
			if (targetTile != tile && tile.getObject() instanceof Conveyor) {
				Conveyor conv = (Conveyor) tile.getObject();
				if (conv.isPointingAt(this.parentTile)) {
					//inputConveyor = conv;
					addInputConveyor(conv);
					inputConveyor.targetConveyor = this;
					System.out.println("Found tile with conveyor at " + tile.x + ", " + tile.y);
					changeSprite(inputConveyor);
				}
				
				
			}
		}
		return inputConveyor != null;
	}
	
	public void changeSprite(Conveyor inputConveyor) {
		
		Direction inputRotation = inputConveyor.getRotation();
		mask = inputRotation.getRotationMask() | mask;
		int key;

		key = (rotation.getRotationMask() << 4) | mask;
		System.out.println(key);
		if ( !ConveyorManager.getInstance().conveyorSpritemap.containsKey(key) ) return;
		super.addTexture(ConveyorManager.getInstance().conveyorSpritemap.get(key), GameGraphics.getTextureManager());
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
	
	public void recieveItem(WorldItem item) {
		item.fixToTile(this.parentTile);
		heldItem = item;
		
	}
	public boolean isEmpty() {
		return heldItem == null;
	}
	public void addInputConveyor(Conveyor conveyor) {
		mask |= conveyor.getRotation().getRotationMask();
		System.out.println(mask);
		if (inputConveyor == null) {
			inputConveyor = conveyor;
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
	public Direction getRotation() {
		return rotation;
	}
	@Override
	public void render(Graphics2D g, GameGraphics graphics) {
		if (toRender) {
			int pixelX = parentTile.pixelX;
			int pixelY = parentTile.pixelY;
			g.drawImage(texture, pixelX, pixelY + verticalOffset, null); 
			
			int fontSize = 12;
			Font largeFont = new Font("Arial", Font.BOLD, fontSize);
		    g.setFont(largeFont);
		    g.setColor(Color.blue);
			g.drawString("" + beltKey ,  pixelX, pixelY  + fontSize);
			
			g.setColor(Color.GREEN);
			g.drawString(Integer.toBinaryString(mask),  pixelX, pixelY + 45 + fontSize);
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
	
	private void becomeConveyorLeaf() {
		beltKey = ConveyorManager.generateConveyorKey(this);
		isRoot = true;
		ConveyorManager.asignLeaf(beltKey, this);
	}
}
