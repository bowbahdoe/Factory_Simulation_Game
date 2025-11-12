package survivalGame;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import survivalGame.ItemManagement.WorldItem;

public class ConveyorNetworkSystem {
	private final ConveyorSpriteManager spriteManager;
	
	
	public ConveyorNetworkSystem(ConveyorSpriteManager spriteManager) {
		this.spriteManager = spriteManager;
	}
	// possible collisions for beltSequence but we will see
	private static Map<BeltSequence, Conveyor> keyToTail = new HashMap<>();

	static int keyCounter = 1; 
	
	public static int generateConveyorKey(Conveyor conv) {
		return keyCounter++;
	}
	
	public static void asignTail(BeltSequence key, Conveyor conv) {
		keyToTail.put(key, conv);
	}
	
	public List<Conveyor> getTails(){
		return new ArrayList<>(keyToTail.values());
	}
	
	public void asignTargetConveyor(Conveyor currentConveyor) {
		Tile targetTile = currentConveyor.getTargetTile(currentConveyor.rotation);
		Conveyor target = getConveyorFromTile(targetTile);
		currentConveyor.targetConveyor = target;
	}
	
	public void initializeConveyor(Conveyor conveyor) {
		conveyor.spriteMask |= conveyor.rotation.getRotationMask() << 4;
		searchForInputConveyors(conveyor);
		spriteManager.updateSprite(conveyor);
		//conveyor.beltSequence = conveyor.inputConveyor.beltSequence;
		Tile targetTile = conveyor.getTargetTile(conveyor.rotation);
		if (conveyor.inputConveyor == null) {
			//If no target, inherit any other key
			if (targetTile.getObject() instanceof IContainsConveyor) {
				conveyor.targetConveyor = getConveyorFromTile(targetTile);
				//conveyor.targetConveyor.changeSprite(this.rotation);
				spriteManager.changeSprite(conveyor, conveyor.rotation);
			}
			//And therefore that parent must be the leaf, so make this new leaf.
			asignTail(conveyor.beltSequence, conveyor);
		}	
		else if (targetTile.getObject() instanceof IContainsConveyor  ) {
			//if conveyor infront, attach to conveyor and update beltkey
			
			conveyor.targetConveyor = getConveyorFromTile(targetTile);
			spriteManager.changeSprite(conveyor.targetConveyor, conveyor.rotation); //?
			
			
			if (conveyor.targetConveyor.hasInputConveyor()) {
				//The conveyor becomes the new tail
				becomeNewConveyorTail(conveyor);
				return;
			}
			conveyor.beltSequence = conveyor.targetConveyor.beltSequence;
			conveyor.targetConveyor.addInputConveyor(conveyor);
			
			
		}
		
		else {
			//Else just make new one
			becomeNewConveyorTail(conveyor);
		}
		

	}
	
	private void searchForInputConveyors(Conveyor currentConveyor) {
		Tile parentTile = currentConveyor.parentTile;
		EnumSet<Direction> inputBlackList = currentConveyor.getInputBlackList();
		Tile[] surroundings = new Tile[4];
		surroundings[0] = TileProvider.world_AccessTile(parentTile.x,parentTile.y - 1);
		surroundings[1] = TileProvider.world_AccessTile(parentTile.x + 1, parentTile.y);
		surroundings[2] = TileProvider.world_AccessTile(parentTile.x, parentTile.y + 1);
		surroundings[3] = TileProvider.world_AccessTile(parentTile.x - 1, parentTile.y);
		for (Tile tile : surroundings) {
			//check surrounding tiles for conveyors, make them the input if they point towards you.
			//Ignore the one you point towards too. 

			if (currentConveyor.getTargetTile() != tile && tile.getObject() instanceof IContainsConveyor containingConveyor) {
				//If the tile isnt the targetTile (basically not facing it), and the object is a conveyor..
				Conveyor inputConveyor = containingConveyor.getConveyor();

				//And if its pointing at THIS tile, and obeys the inputBlacklist Directions...
				if ( !inputConveyor.isPointingAt(parentTile)) continue;
				if ( inputBlackList != null && inputBlackList.contains(inputConveyor.rotation)) continue;
				
				//Make this conveyor the input, and therefore make this conveyor the input's target. Doubly Linked
				currentConveyor.addInputConveyor(inputConveyor);
				inputConveyor.targetConveyor = currentConveyor;
				System.out.println("Found tile with conveyor at " + tile.x + ", " + tile.y);
				if (inputConveyor.rotation == currentConveyor.rotation) continue;
				spriteManager.changeSprite(currentConveyor, inputConveyor.rotation);
			
			}
		}
	}
	
	
	private void becomeNewConveyorTail(Conveyor conveyor) {
		generateConveyorKey(conveyor);
		asignTail(conveyor.beltSequence, conveyor);
	}
	
	/**
	 * @param tile to get conveyor from
	 * @return any instance of conveyor on that tile. This function was made due to the interface: IContainsConveyor
	 */
	private Conveyor getConveyorFromTile(Tile tile) {
		if (!(tile.getObject() instanceof IContainsConveyor)) return null;
		IContainsConveyor conv = ((IContainsConveyor) tile.getObject());
		return conv.getConveyor();
	}
	
	public void conveyorPassToTarget(Conveyor conveyor, WorldItem item) {
		if (item == null || conveyor.isLocked() || conveyor.targetConveyor == null) {
			return;
		}
		if ( !conveyor.targetConveyor.isEmpty() ) return;
		
		conveyor.targetConveyor.recieveWorldItem(item);
		conveyor.heldItem = null;
	}
}
