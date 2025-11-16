package survivalGame;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import survivalGame.ItemManagement.WorldItem;

public class ConveyorNetworkSystem {
	
	// possible collisions for beltSequence but we will see
	private static Map<BeltSequence, Conveyor> keyToTail = new HashMap<>();

	static int keyCounter = 1; 
	
	public static BeltSequence generateConveyorKey() {
		return new BeltSequence(keyCounter++);
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
	
	public void initializeConveyor(Conveyor currentConveyor) {
		currentConveyor.spriteMask |= currentConveyor.rotation.getRotationMask() << 4;
		searchForInputConveyors(currentConveyor);
		ConveyorSpriteManager.updateSprite(currentConveyor);
		Tile targetTile = currentConveyor.getTargetTile(currentConveyor.rotation);
		currentConveyor.targetConveyor = getConveyorFromTile(targetTile);
		ConveyorSpriteManager.changeSprite(currentConveyor.targetConveyor, currentConveyor.rotation);
		
		if (currentConveyor.inputConveyor == null && (currentConveyor.targetConveyor == null || currentConveyor.targetConveyor.hasInputConveyor())) {
			becomeNewConveyorTail(currentConveyor);
		}	
		else if ( currentConveyor.inputConveyor == null && !currentConveyor.targetConveyor.hasInputConveyor()) {
			currentConveyor.beltSequence = currentConveyor.targetConveyor.beltSequence;
			currentConveyor.targetConveyor.addInputConveyor(currentConveyor);
		}
		
		else {
			currentConveyor.beltSequence = currentConveyor.inputConveyor.beltSequence;
			asignTail(currentConveyor.beltSequence, currentConveyor);
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

				ConveyorSpriteManager.changeSprite(currentConveyor, inputConveyor.rotation);
			
			}
		}
	}
	
	
	private void becomeNewConveyorTail(Conveyor conveyor) {
		conveyor.beltSequence = generateConveyorKey();
		asignTail(conveyor.beltSequence, conveyor);
	}
	
	/**
	 * @param tile to get conveyor from
	 * @return any instance of conveyor on that tile. This function was made due to the interface: IContainsConveyor
	 */
	public static Conveyor getConveyorFromTile(Tile tile) {
		if (!(tile.getObject() instanceof IContainsConveyor)) return null;
		IContainsConveyor conv = ((IContainsConveyor) tile.getObject());
		return conv.getConveyor();
	}
	
	
	/**
	 * Makes conveyor attempt to pass to the next target, It won't passed if its locked.
	 * @param conveyor that passes
	 * @param item to pass (in case you want to create an item)
	 */
	public void conveyorPassToTarget(Conveyor conveyor, WorldItem item) {
		if (item == null || conveyor.isLocked() || conveyor.targetConveyor == null) {
			return;
		}
		if ( !conveyor.targetConveyor.isEmpty() ) return;
		
		conveyor.targetConveyor.recieveWorldItem(item);
		conveyor.heldItem = null;
	}
}
