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
	
	public static final int NORTH = 1 << 0; // 0001
	public static final int EAST  = 1 << 1; // 0010
	public static final int SOUTH = 1 << 2; // 0100
	public static final int WEST  = 1 << 3; // 1000
	
	//These maps keys are formatted in: OutputDirection_InputDirections
	
	final Map<String, String> conveyorSpritemap = Map.ofEntries(
		    Map.entry("EAST_" + NORTH, "ConveyorNE"),
		    Map.entry("WEST_" + NORTH, "ConveyorNW"),
		    Map.entry("NORTH_" + EAST, "ConveyorWN"),
		    Map.entry("SOUTH_" + EAST, "ConveyorWS"),
		    Map.entry("NORTH_" + WEST, "ConveyorEN"),
		    Map.entry("SOUTH_" + WEST, "ConveyorES"),
		    Map.entry("EAST_" + SOUTH, "ConveyorSE"),
		    Map.entry("WEST_" + SOUTH, "ConveyorSW"),
		    
		    Map.entry("EAST_" + (EAST | NORTH), "ConveyorT_NE"),
		    Map.entry("WEST_" + (NORTH | WEST), "ConveyorT_NW"),
		    Map.entry("NORTH_" + (NORTH | EAST), "ConveyorT_EN"),
		    Map.entry("SOUTH_" + (EAST | SOUTH), "ConveyorT_ES"),
		    Map.entry("NORTH_" + (NORTH | WEST), "ConveyorT_WN"),
		    Map.entry("SOUTH_" + (WEST | SOUTH), "ConveyorT_WS"),
		    Map.entry("EAST_" + (SOUTH | EAST), "ConveyorT_SE"),
		    Map.entry("WEST_" + (SOUTH | WEST), "ConveyorT_SW"),
		    
		    Map.entry("WEST_" + (NORTH | SOUTH), "ConveyorT_VE"),
		    Map.entry("EAST_" + (NORTH | SOUTH), "ConveyorT_VW"),
		    Map.entry("NORTH_" + (WEST | EAST), "ConveyorT_HN"),
		    Map.entry("SOUTH_" + (WEST | EAST), "ConveyorT_HS")
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
		TickManager.getInstance();
		TickManager.register(this); 
		
		
		GameGraphics.getInstance().registerWorldObj(this, 4);
		
		for (String e : conveyorSpritemap.keySet()) {
			System.out.println(" aa " + e);
		}
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
	
	public int directionMask(Direction direction, int mask) {
		switch (direction){
		case NORTH:
			mask |= NORTH;
			break;
		case EAST:
			mask |= EAST;
			break;
		case SOUTH:
			mask |= SOUTH;
			break;
		case WEST:
			mask |= WEST;
			break;
		default:
			break;
		}
		
		return mask;
	}
}
