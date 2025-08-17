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
		
		GameGraphics.getInstance();
		GameGraphics.register(this, 4);
	}
	 

	public static void registerConveyor(Conveyor conv) {
		//conveyors.add(conv);
	}
	
	public static int generateConveyorKey(Conveyor conv) {
		return keyCounter++;
	}
	
	public static void asignLeaf(int key, Conveyor conv) {
		System.out.println("Put!!!!");
		keyToLeaf.put(key, conv);
	}
	@Override
	public void onTick() {
		
		List<Conveyor> leaves = new ArrayList<>(keyToLeaf.values());
		
		for (Conveyor c : leaves) {
			traverse(c, c);
		}
	}
	
	private void traverse(Conveyor c, Conveyor root) {
		
		c.onTick();
		System.out.println("Traversed through " +  c.parentTile.x + ", " + c.parentTile.y);
		
		//Massive possibility for stack overflow, gotta cover all the edge cases haha
		//if (c.getInputConveyor() == null) System.out.println("--- Null input conveyor");
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
