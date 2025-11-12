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
	
	private ConveyorSpriteManager spriteManager = new ConveyorSpriteManager(); 
	private ConveyorNetworkSystem networkSystem = new ConveyorNetworkSystem(spriteManager);
	
	private static ConveyorManager ConveyorManagerInstance;

	
	/**
	 * Maps beltKey to conveyor Leaf. 
	 * A conveyor leaf is the end of a conveyor belt sequence, treated like a linked list.
	 */
	
	
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
	 
	
	@Override
	public void onTick() {
		
		List<Conveyor> tails = networkSystem.getTails();
		
		//Use DFS for each conveyor belt. Reversed because usually people make conveyor belts chronologically, so in numerical order
		for (Conveyor c : tails.reversed()) {
			traverse(c, c);
		}
	}
	
	private void traverse(Conveyor conveyor, Conveyor root) {
		
		networkSystem.conveyorPassToTarget(conveyor, conveyor.heldItem); //Updates the conveyor, pass item along
		
		//System.out.println("Traversed through " +  c.parentTile.x + ", " + c.parentTile.y);
		
		if (conveyor.inputConveyor == null || conveyor.inputConveyor.beltSequence != root.beltSequence || conveyor.inputConveyor == root) return;
		
		traverse(conveyor.inputConveyor, root);
	}

	public void registerConveyor(Conveyor conveyor) {
		networkSystem.initializeConveyor(conveyor);
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
		/*
		List<Integer> map = new ArrayList<>(keyToTail.keySet());
		for (int key : map) {
			//Draws orange box on each Tail (Where DFS starts from)
			Conveyor c = keyToTail.get(key);
			
			int x = c.parentTile.pixelX;
			int y = c.parentTile.pixelY;
			g.setColor(Color.ORANGE);
			g.fillRect(x + 70, y, 30, 30);
			
			g.setColor(Color.BLACK);
			g.drawString(key + "", x + 73, y + 24);
		}
		*/
	}

	public ConveyorSpriteManager getSpriteManager() {
		return spriteManager;
	}

	public ConveyorNetworkSystem getNetworkSystem() {
		return networkSystem;
	}
	

}
