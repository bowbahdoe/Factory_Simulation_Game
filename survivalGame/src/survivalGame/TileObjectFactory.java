package survivalGame;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import survivalGame.tileObjects.TileObject;
import survivalGame.tileObjects.TileRock;
import survivalGame.tileObjects.TileTree;
import survivalGame.tileObjects.FactoryComponents.Conveyor;
import survivalGame.tileObjects.FactoryComponents.ConveyorSplitter;
import survivalGame.tileObjects.FactoryComponents.Planker;
import survivalGame.tileObjects.FactoryComponents.RockDriller;
import survivalGame.tileObjects.FactoryComponents.TreeHarvester;

public class TileObjectFactory {
	
	static private Map<TileObjectID, Function<PlacementInfo, TileObject>> IDtoInstance;
	
	static {
		IDtoInstance = new HashMap<>();
		IDtoInstance.put(TileObjectID.CONVEYOR, info -> new Conveyor(info.tile,info.direction));
		IDtoInstance.put(TileObjectID.TREE_HARVESTER, info -> new TreeHarvester(info.tile,info.direction));
		IDtoInstance.put(TileObjectID.PLANKER, info -> new Planker(info.tile,info.direction));
		IDtoInstance.put(TileObjectID.CONVEYOR_SPLITTER_L, info -> new ConveyorSplitter(info.tile,info.direction,true));
		IDtoInstance.put(TileObjectID.CONVEYOR_SPLITTER_R, info -> new ConveyorSplitter(info.tile,info.direction,false));
		IDtoInstance.put(TileObjectID.ROCK_DRILLER, info -> new RockDriller(info.tile,info.direction));
		IDtoInstance.put(TileObjectID.TREE, info -> new TileTree(info.tile));
		IDtoInstance.put(TileObjectID.ROCK, info -> new TileRock(info.tile));
	}
	
	/**
	 * Instantiates the placeable object according to the itemID.
	 * @param tileObjectID specifies which TileObject to create.
	 * @param info contains tile position data and rotation.
	 * @return The instantiated {@link TileObject}
	 */
	public static TileObject createTileObject(TileObjectID tileObjectID, PlacementInfo info) {
		if (!IDtoInstance.containsKey(tileObjectID)) {
			 throw new IllegalArgumentException("ItemID: " + tileObjectID.toString() + " is invalid! ");
		}
		
		Function<PlacementInfo, TileObject> function = IDtoInstance.get(tileObjectID);

		return function.apply(info);
		
	}
}
