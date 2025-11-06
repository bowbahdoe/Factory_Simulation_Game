package survivalGame;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import survivalGame.ItemManagement.ItemID;

public class PlaceablesFactory {
	static Map<ItemID, Function<PlacementInfo, TileObject>> placementMap;
	
	static {
		placementMap = new HashMap<>();
		placementMap.put(ItemID.CONVEYOR, info -> new Conveyor(info.tile,info.direction));
		placementMap.put(ItemID.TREEHARVESTER, info -> new TreeHarvester(info.tile,info.direction));
		placementMap.put(ItemID.PLANKER, info -> new Planker(info.tile,info.direction));
	}
	
	public static TileObject createPlaceable(ItemID itemID, PlacementInfo info) {
		if (!placementMap.containsKey(itemID)) return null;
		
		Function<PlacementInfo, TileObject> function = placementMap.get(itemID);

		return function.apply(info);
		
	}
}
