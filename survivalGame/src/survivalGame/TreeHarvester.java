package survivalGame;

import java.awt.Graphics2D;

import graphics.GameGraphics;
import graphics.TextureManager;
import survivalGame.ItemManagement.Item;
import survivalGame.ItemManagement.ItemFactory;
import survivalGame.ItemManagement.ItemID;

public class TreeHarvester extends FactoryComponent  {

	private TileTree targetTree;
	private IItemReciever targetOutput;
	
	private Tile targetTile;
	private Tile behindTile;
	private final int actionTime = 3;
	private int currentTime = actionTime;
	
	public TreeHarvester(Tile parentTile, Direction rotation) {
		super(parentTile, rotation);
		
		GameGraphics.getInstance().registerWorldObj(this, 2);
		TickManager.getInstance().register(this);
		TextureManager textureManager = GameGraphics.getTextureManager();
		switch (rotation) {
		case NORTH:
			super.addTexture(ImageRotater.rotateImage(textureManager.getTexture("TreeHarvester"), 90));
			break;
		case EAST:
			super.addTexture(ImageRotater.rotateImage(textureManager.getTexture("TreeHarvester"), 180));
			break;
		case SOUTH:
			super.addTexture(ImageRotater.rotateImage(textureManager.getTexture("TreeHarvester"), -90));
			break;
		case WEST:
			super.addTexture("TreeHarvester", textureManager);
			break;
		}
		
		Direction opposite = rotation.rotatedClockwise().rotatedClockwise();
		targetTile = getTargetTile(rotation);
		behindTile = getTargetTile(opposite);
		
		if (targetTile.getObject() instanceof TileTree tree) {
			targetTree = tree;
		}
		checkForOutput();
	}
	
	/**
	 * checks for any object that can recieve the item it dispenses.
	 */
	public void checkForOutput() {
		if (behindTile.getObject() instanceof IItemReciever output) {
			targetOutput = output;
		}
		if (targetOutput instanceof Conveyor conv) {
			Direction opposite = rotation.rotatedClockwise().rotatedClockwise();
			conv.changeSprite(opposite);
		}
	}
	@Override
	public void onTick() {
		if (targetTile.getObject() instanceof TileTree tree) {
			targetTree = tree;
		}
		checkForOutput();
		
		currentTime --;
		if (currentTime > 0) return;
		currentTime = actionTime;
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
		return 0;
	}
	
	@Override
	public void render(Graphics2D g, GameGraphics graphics) {
		int x = this.parentTile.pixelX;
		int y = this.parentTile.pixelY;
		g.drawImage(super.texture ,x ,y , graphics);
	}
	
	public void removeObject() {
		
	}
}
