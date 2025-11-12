package survivalGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.EnumSet;

import graphics.GameGraphics;
import graphics.WorldRenderable;
import survivalGame.ItemManagement.Item;
import survivalGame.ItemManagement.ItemID;
import survivalGame.ItemManagement.WorldItem;

public class Conveyor extends FactoryComponent implements IContainsConveyor{

	private Tile targetTile = super.getTargetTile(rotation);
	public Conveyor inputConveyor;
	public Conveyor targetConveyor;
	public BufferedImage texture;
	public WorldItem heldItem;
	public BeltSequence beltSequence;
	public int spriteMask;
	protected boolean locked;
	private EnumSet<Direction> inputBlacklist;
	
	public Conveyor(Tile parentTile, Direction rotation) {
		super(parentTile, rotation);
		GameGraphics.getInstance().registerWorldObj(this, 2);
	}

	

	@Override
	public void removeObject() {
		// TODO Auto-generated method stub
		
	}

	public EnumSet<Direction> getInputBlackList(){
		return inputBlacklist;
	}

	public boolean isPointingAt(Tile tile) {
		return targetTile == tile;
	}
	public void addInputConveyor(Conveyor conveyor) {
		if (inputConveyor == null) {
			inputConveyor = conveyor;
		}
	}
	
	public boolean hasInputConveyor() {
		return inputConveyor != null;
	}
	public WorldItem collectItem() {
		WorldItem item = heldItem;
		if (heldItem == null) return null;
		heldItem.setActive(false);
		heldItem = null;
		return item;
	}
	
	public void recieveWorldItem(WorldItem item) {
		item.fixToTile(this.parentTile);
		heldItem = item;
	}
	
	public boolean isLocked() {
		return locked;
	}
	public boolean isEmpty() {
		return heldItem == null;
	}
	
	@Override
	public void render(Graphics2D g, GameGraphics graphics) {
		int x = this.parentTile.pixelX;
		int y = this.parentTile.pixelY;
		g.drawImage(super.texture ,x ,y , graphics);
	}
	
	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Tile getTargetTile() {
		return targetTile;
	}



	@Override
	public Conveyor getConveyor() {
		return this;
	}
}
