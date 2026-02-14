package survivalGame.tileObjects.FactoryComponents;

import java.awt.Graphics2D;

import graphics.GameGraphics;
import graphics.TextureManager;
import graphics.ImageManipulation.ImageRotater;
import survivalGame.ActionTimer;
import survivalGame.ConveyorSpriteManager;
import survivalGame.Direction;
import survivalGame.FactoryComponent;
import survivalGame.IItemReciever;
import survivalGame.ITickable;
import survivalGame.TickManager;
import survivalGame.ItemManagement.Item;
import survivalGame.ItemManagement.ItemFactory;
import survivalGame.ItemManagement.ItemID;
import survivalGame.TileManagement.Tile;
import survivalGame.TileManagement.TileObjectID;
import survivalGame.tileObjects.TileTree;

public class TreeHarvester extends FactoryComponent implements ITickable {

	public static final TileObjectID ID = TileObjectID.TREE_HARVESTER;
	
	private TileTree targetTree;
	private IItemReciever targetOutput;
	
	private Tile targetTile;
	private Tile behindTile;
	private final ActionTimer actionTime = new ActionTimer(3);
	
	public TreeHarvester(Tile parentTile, Direction rotation) {
		super(parentTile, rotation);
		
		GameGraphics.registerWorldObj(this, 2);
		TickManager.getInstance().register(this);
		TextureManager textureManager = GameGraphics.getTextureManager();
		switch (rotation) {
		case NORTH:
			super.setTexture(ImageRotater.rotateImage(textureManager.getTexture("TreeHarvester"), 90));
			break;
		case EAST:
			super.setTexture(ImageRotater.rotateImage(textureManager.getTexture("TreeHarvester"), 180));
			break;
		case SOUTH:
			super.setTexture(ImageRotater.rotateImage(textureManager.getTexture("TreeHarvester"), -90));
			break;
		case WEST:
			super.setTexture("TreeHarvester", textureManager);
			break;
		}
		
		Direction opposite = rotation.rotatedClockwise().rotatedClockwise();
		targetTile = getTargetTile(rotation);
		behindTile = getTargetTile(opposite);
		
		if (targetTile.getTileObject() instanceof TileTree tree) {
			targetTree = tree;
		}
		checkForOutput();
	}
	
	/**
	 * checks for any object that can recieve the item it dispenses.
	 */
	public void checkForOutput() {
		if (behindTile.getTileObject() instanceof IItemReciever output) {
			targetOutput = output;
		}
		if (targetOutput instanceof Conveyor conv) {
			Direction opposite = getRotation().rotatedClockwise().rotatedClockwise();
			ConveyorSpriteManager.changeSprite(conv, opposite);
		}
	}
	@Override
	public void onTick() {
		checkForOutput();
		
		if (! actionTime.actionTick()) return;
		if (targetTree == null || targetOutput == null) return;
		action();
		
	}

	private void action() {
		if (! targetOutput.canRecieve()) return;
		Item item = ItemFactory.createItem(ItemID.LOG);
		System.out.println("----------LOG OUTPUT");
		targetOutput.recieveItem(item);
	}
	
	@Override
	public int getY() {
		return parentTile.y;
	}
	
	@Override
	public void render(Graphics2D g, GameGraphics graphics) {
		int x = this.parentTile.pixelX;
		int y = this.parentTile.pixelY;
		g.drawImage(super.texture ,x ,y , graphics);
	}

	@Override
	public void removeObject() {

	}

	@Override
	public TileObjectID getTileObjectID() {
		return ID;
	}
	

}
