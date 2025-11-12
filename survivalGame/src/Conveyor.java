
package survivalGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.EnumSet;

import graphics.GameGraphics;
import survivalGame.ItemManagement.Item;
import survivalGame.ItemManagement.ItemID;
import survivalGame.ItemManagement.WorldItem;

public class Conveyor extends FactoryComponent implements IItemReciever, IContainsConveyor{

	private Conveyor inputConveyor; //A conveyor that shares the same key, and is the input
	private Conveyor targetConveyor;
	private FactoryComponent parentComponent;
	private WorldItem heldItem; 
	private Tile targetTile;
	private boolean isTail; //Purely for visual purposes
	private int beltKey = -1;
	private boolean toRender = true;
	private boolean locked = false;
	int mask = 0;
	
	public Conveyor(Tile parentTile, Direction rotation) {
		super(parentTile,rotation);
		initializeConveyor(parentTile,rotation,null);
	}
	public Conveyor(Tile parentTile, Direction rotation, EnumSet<Direction> inputBlacklist) {
		super(parentTile,rotation);
		initializeConveyor(parentTile,rotation,inputBlacklist);
	}
	
	private void initializeConveyor(Tile parentTile, Direction rotation, EnumSet<Direction> inputBlacklist) {
		mask |= rotation.getRotationMask() << 4;
		super.addTexture(ConveyorManager.getInstance().conveyorSpritemap.get(mask), GameGraphics.getTextureManager());
		GameGraphics.getInstance().registerWorldObj(this, 2);
		targetTile = getTargetTile(rotation);
		if (findParents(inputBlacklist)) {
			//If no target, inherit any other key
			beltKey = inputConveyor.getBeltKey();
			if (targetTile.getObject() instanceof IContainsConveyor) {
				targetConveyor = getConveyorFromTile(targetTile);
				targetConveyor.changeSprite(this.rotation);
			}
			//And therefore that parent must be the leaf, so make this new leaf.
			ConveyorManager.asignTail(beltKey, this);
		}	
		else if (targetTile.getObject() instanceof IContainsConveyor  ) {
			//if conveyor infront, attach to conveyor and update beltkey
			
			targetConveyor = getConveyorFromTile(targetTile);
			targetConveyor.changeSprite(this.rotation);
			
			if (targetConveyor.hasInputConveyor()) {
				becomeNewConveyorTail();
				return;
			}
			beltKey = targetConveyor.getBeltKey();
			targetConveyor.addInputConveyor(this);
			
			
		}
		
		else {
			//Else just make new one
			becomeNewConveyorTail();
		}
		
	}
	/**
	 * Checks surroundings for any possible input conveyors, while obeying the inputblacklist.
	 * @param inputBlacklist is an enumset which limits the input directions of input conveyors allowing to pass on items to this conveyor
	 * @returns boolean whether or not it found an input conveyor or not. 
	 */
	private boolean findParents(EnumSet<Direction> inputBlacklist) {
		Tile[] surroundings = new Tile[4];
		surroundings[0] = TileProvider.world_AccessTile(parentTile.x,parentTile.y - 1);
		surroundings[1] = TileProvider.world_AccessTile(parentTile.x + 1, parentTile.y);
		surroundings[2] = TileProvider.world_AccessTile(parentTile.x, parentTile.y + 1);
		surroundings[3] = TileProvider.world_AccessTile(parentTile.x - 1, parentTile.y);
		for (Tile tile : surroundings) {
			//check surrounding tiles for conveyors, make them the input if they point towards you.
			//Ignore the one you point towards too. 
			
			if (targetTile != tile && tile.getObject() instanceof IContainsConveyor icc) {
				Conveyor conv = icc.getConveyor();

				if ( !conv.isPointingAt(this.parentTile)) continue;
				if ( inputBlacklist != null && inputBlacklist.contains(conv.getRotation())) continue;
				
				addInputConveyor(conv);
				inputConveyor.addTargetConveyor(this);
				System.out.println("Found tile with conveyor at " + tile.x + ", " + tile.y);
				changeSprite(inputConveyor.rotation);
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
		return super.toRender;
	}

	/**
	 * OnTick() makes the conveyor attempt passing the item to the next.
	 * It also calls the function on any parentcomponent it has. 
	 */
	@Override
	public void onTick() {

	    if (targetConveyor != null && targetConveyor.isEmpty() && heldItem != null) {
	    	if (parentComponent != null) parentComponent.onTick();
			passToTarget(heldItem);
		}
	}
	

	/**
	 * passes the provided item to the target conveyor.
	 * @param item to pass
	 */
	public void passToTarget(WorldItem item) {
		if (item == null || isLocked()) {
			return;
		}
		if ( !targetConveyor.isEmpty() ) return;
		
		targetConveyor.recieveWorldItem(item);
		heldItem = null;
	}
	
	public boolean canPassToTarget() {
		if (heldItem == null || isLocked()) {
			return false;
		}
		if ( !targetConveyor.isEmpty() ) return false;
		return true;
	}
	
	public void recieveWorldItem(WorldItem item) {
		item.fixToTile(this.parentTile);
		heldItem = item;
	}
	public boolean isEmpty() {
		return heldItem == null;
	}
	public void addInputConveyor(Conveyor conveyor) {
		//mask |= conveyor.getRotation().getRotationMask();
		if (inputConveyor == null) {
			inputConveyor = conveyor;
		}
	}
	public void removeInputConveyor(Conveyor conveyor) {
		inputConveyor = null;
	}
	public void addTargetConveyor(Conveyor conveyor) {
		targetConveyor = conveyor;
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
		if (toRender)  {
			int pixelX = parentTile.pixelX;
			int pixelY = parentTile.pixelY;
			g.drawImage(super.texture, pixelX, pixelY + verticalOffset, null); 
			
			/*
			int fontSize = 12;
			Font largeFont = new Font("Arial", Font.BOLD, fontSize);
		    g.setFont(largeFont);
		    g.setColor(Color.blue);
			g.drawString("" + beltKey ,  pixelX, pixelY  + fontSize);
			
			if (inputConveyor == null) {
				g.setColor(Color.MAGENTA);
				g.fillRect(pixelX + 70, pixelY, 30, 30);
				
				g.setColor(Color.BLACK);
				g.drawString(beltKey + "", pixelX + 73, pixelY + 24);
			}
			*/
		}		
	}
	
	public WorldItem collectItem() {
		
		WorldItem item = heldItem;
		if (heldItem == null) return null;
		heldItem.setActive(false);
		heldItem = null;
		return item;
	}
	public boolean checkItem(ItemID item) {
		if (heldItem == null) return false;
		return heldItem.getItem().getItemID() == item;
		
	}
	
	/**
	 * Conveyor becomes a new conveyor tail in the belt sequence, meaning it will be the first one to update for that beltKey.
	 * Tail in this context means the end of a linked list like structure, in this case being conveyor belts. 
	 */
	private void becomeNewConveyorTail() {
		beltKey = ConveyorManager.generateConveyorKey(this);
		isTail = true;
		ConveyorManager.asignTail(beltKey, this);
	}
	@Override
	public void recieveItem(Item item) {
		recieveWorldItem(new WorldItem(item,parentTile.pixelX,parentTile.pixelY));
		
	}
	@Override
	public boolean canRecieve() {
		return isEmpty() && !locked;
	}
	public void setRender(boolean state) {
		toRender = state;
	}
	public void removeObject() {
		//TODO 
	}

	public Conveyor getConveyor() {
		return this;
	}

	public void lock(boolean state) {
		locked = state;
	}
	
	/**
	 * For a conveyor to be locked, it means the conveyor can't pass to the target
	 * @return
	 */
	public boolean isLocked() {
		return locked;
	}
	

	/**
	 * Attach a component that will add an additional functionality as items go past the conveyor
	 * @return
	 */
	public void attachParentComponent(FactoryComponent component) {
		parentComponent = component;
	}
}
