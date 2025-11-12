package survivalGame;

import java.util.Map;

import graphics.GameGraphics;

public class ConveyorSpriteManager {
	public final int NORTH = Direction.NORTH.getRotationMask();
	public final int EAST  = Direction.EAST.getRotationMask();
	public final int SOUTH = Direction.SOUTH.getRotationMask();
	public final int WEST  = Direction.WEST.getRotationMask();
	
	public final int NORTH_out = (NORTH << 4);
	public final int EAST_out = (EAST << 4);
	public final int SOUTH_out = (SOUTH << 4);
	public final int WEST_out = (WEST << 4);
	
	//These maps keys are formatted in: OutputDirection_InputDirections
	private final Map<Integer, String> conveyorSpritemap = (Map<Integer, String>) Map.ofEntries(
			Map.entry(EAST_out, "ConveyorE"),
			Map.entry(WEST_out, "ConveyorW"),
			Map.entry(NORTH_out, "ConveyorN"),
			Map.entry(SOUTH_out, "ConveyorS"),
			
			//Curved
			Map.entry(EAST_out  | NORTH, "ConveyorNE"),
		    Map.entry(WEST_out  | NORTH, "ConveyorNW"),
		    Map.entry(NORTH_out | EAST,  "ConveyorWN"),
		    Map.entry(SOUTH_out | EAST,  "ConveyorWS"),
		    Map.entry(NORTH_out | WEST,  "ConveyorEN"),
		    Map.entry(SOUTH_out | WEST,  "ConveyorES"),
		    Map.entry(EAST_out  | SOUTH, "ConveyorSE"),
		    Map.entry(WEST_out  | SOUTH, "ConveyorSW"),

		    // T-junctions
		    Map.entry(EAST_out  | (EAST  | NORTH),  "ConveyorT_NE"),
		    Map.entry(WEST_out  | (NORTH | WEST),  "ConveyorT_NW"),
		    Map.entry(NORTH_out | (NORTH | EAST),  "ConveyorT_EN"),
		    Map.entry(SOUTH_out | (EAST  | SOUTH),  "ConveyorT_ES"),
		    Map.entry(NORTH_out | (NORTH | WEST),  "ConveyorT_WN"),
		    Map.entry(SOUTH_out | (WEST  | SOUTH),  "ConveyorT_WS"),
		    Map.entry(EAST_out  | (SOUTH | EAST),  "ConveyorT_SE"),
		    Map.entry(WEST_out  | (SOUTH | WEST),  "ConveyorT_SW"),
		    
		    Map.entry(WEST_out  | (NORTH | SOUTH), "ConveyorT_VE"),
		    Map.entry(EAST_out  | (NORTH | SOUTH), "ConveyorT_VW"),
		    Map.entry(NORTH_out | (WEST  | EAST),   "ConveyorT_HN"),
		    Map.entry(SOUTH_out | (WEST  | EAST),   "ConveyorT_HS")
		);
	
	/**
	 * changes sprite according to the added input direction
	 * @param inputRotation is the input direction added.
	 */
	public void changeSprite(Conveyor conveyor, Direction inputRotation) {
		conveyor.spriteMask |=  inputRotation.getRotationMask();
		
		if ( !conveyorSpritemap.containsKey(conveyor.spriteMask) ) return;
		conveyor.addTexture(conveyorSpritemap.get(conveyor.spriteMask), GameGraphics.getTextureManager());
	}
	
	public void updateSprite(Conveyor conveyor) {
		if ( !conveyorSpritemap.containsKey(conveyor.spriteMask) ) return;
		System.out.println("ADDED TEXTURE----------");
		conveyor.addTexture(conveyorSpritemap.get(conveyor.spriteMask), GameGraphics.getTextureManager());
	}
	
}
