package survivalGame;

import java.awt.Graphics2D;
import java.util.EnumSet;

import graphics.GameGraphics;
import graphics.TextureManager;
import graphics.ImageManipulation.ImageRotater;
import survivalGame.ItemManagement.ItemID;
import survivalGame.ItemManagement.WorldItem;

public class Planker extends FactoryComponent implements IContainsConveyor{
	
	private ActionTimer actionTimer;
	private Conveyor conveyor;
	public Planker(Tile parentTile, Direction rotation) {
		super(parentTile, rotation);
		
		TextureManager textureManager = GameGraphics.getTextureManager();
		
		switch (rotation) {
		case NORTH:
			super.addTexture(textureManager.getTexture("Planker"));
			break;
		case EAST:
			super.addTexture(ImageRotater.rotateImage(textureManager.getTexture("Planker"), 90));
			break;
		case SOUTH:
			super.addTexture(ImageRotater.rotateImage(textureManager.getTexture("Planker"), 180));
			break;
		case WEST:
			super.addTexture(ImageRotater.rotateImage(textureManager.getTexture("Planker"), -90));
			break;
		}
		
		EnumSet<Direction> blacklist = EnumSet.of(rotation.rotatedAntiClockwise(), rotation.rotatedClockwise());
		conveyor = new Conveyor(parentTile, rotation, blacklist);
		conveyor.setRender(false);

		actionTimer = new ActionTimer(3);
		conveyor.attachParentComponent(this);
		GameGraphics.getInstance().registerWorldObj(this, 2);
	}

	@Override
	public void onTick() {
		if (actionTimer.actionTick()) {
			conveyor.lock(false);
		}
		else {
			conveyor.lock(true);
		}

		WorldItem planks = conveyor.collectItem();
		if (planks == null) return;
		planks.getItem().changeItemInto(ItemID.WOOD); //now becomes planks
		planks.setActive(true);
		conveyor.recieveWorldItem(planks);
	}

	@Override
	public int getY() {
		return 0;
	}

	@Override
	public void removeObject() {	
	}

	

	@Override
	public void render(Graphics2D g, GameGraphics graphics) {
		int x = this.parentTile.pixelX;
		int y = this.parentTile.pixelY;
		g.drawImage(super.texture ,x ,y , graphics);
	}
	
	@Override
	public Conveyor getConveyor() {
		return conveyor;
	}

}
