package survivalGame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import graphics.GameGraphics;
import graphics.WorldRenderable;

public final class ConveyorManager implements ITickable, WorldRenderable{
	
	public static final int NORTH = Direction.NORTH.getRotationMask();
	public static final int EAST  = Direction.EAST.getRotationMask();
	public static final int SOUTH = Direction.SOUTH.getRotationMask();
	public static final int WEST  = Direction.WEST.getRotationMask();
	
	final int NORTH_out = (NORTH << 4);
	final int EAST_out = (EAST << 4);
	final int SOUTH_out = (SOUTH << 4);
	final int WEST_out = (WEST << 4);
	
	//These maps keys are formatted in: OutputDirection_InputDirections
	final Map<Integer, String> conveyorSpritemap = (Map<Integer, String>) Map.ofEntries(
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
			
	private static ConveyorManager ConveyorManagerInstance;
	static List<Conveyor> conveyors = new ArrayList<>();
	
	private static Map<Integer, Conveyor> keyToLeaf = new HashMap<>();
	static int keyCounter = 1; 
	//This will increment for each new conveyor leef you make.
	
	public static ConveyorManager getInstance() {
		if (ConveyorManagerInstance == null) {
            ConveyorManagerInstance = new ConveyorManager();
	    }
        return ConveyorManagerInstance;
    }
	
	public ConveyorManager() {
		TickManager.getInstance().register(this); 
		
		
		GameGraphics.getInstance().registerWorldObj(this, 4);
	
	}
	 
	
	public static int generateConveyorKey(Conveyor conv) {
		return keyCounter++;
	}
	
	public static void asignLeaf(int key, Conveyor conv) {
		keyToLeaf.put(key, conv);
	}
	@Override
	public void onTick() {
		
		List<Conveyor> leaves = new ArrayList<>(keyToLeaf.values());
		
		for (Conveyor c : leaves.reversed()) {
			traverse(c, c);
		}
	}
	
	private void traverse(Conveyor c, Conveyor root) {
		
		c.onTick(); //Updates the conveyor, pass item along
		//System.out.println("Traversed through " +  c.parentTile.x + ", " + c.parentTile.y);
		
		if (c.getInputConveyor() == null || c.getInputConveyor().getBeltKey() != root.getBeltKey()) return;
		
		traverse(c.getInputConveyor(), root);
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void render(Graphics2D g, GameGraphics graphics) {
		List<Integer> map = new ArrayList<>(keyToLeaf.keySet());
		for (int key : map) {
			Conveyor c = keyToLeaf.get(key);
			
			int x = c.parentTile.pixelX;
			int y = c.parentTile.pixelY;
			g.setColor(Color.ORANGE);
			g.fillRect(x + 70, y, 30, 30);
			
			g.setColor(Color.BLACK);
			g.drawString(key + "", x + 73, y + 24);
		}
		
	}
	

}
