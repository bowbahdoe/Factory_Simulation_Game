package survivalGame.tileObjects.FactoryComponents;

import java.util.EnumSet;

import graphics.GameGraphics;
import graphics.TextureManager;
import graphics.ImageManipulation.ImageRotater;
import survivalGame.ConveyorNetworkSystem;
import survivalGame.ConveyorSpriteManager;
import survivalGame.Direction;
import survivalGame.Tile;
import survivalGame.TileObjectID;

public class ConveyorSplitter extends Conveyor{
	
	public static final TileObjectID ID = TileObjectID.CONVEYOR_SPLITTER_L;
	
	private Conveyor conveyorForward;
	private Conveyor conveyorSide;
	
	boolean conveyorSwitch = true;
	

	private final Direction sideDirection;
	/**Constructor to instantiate ConveyorSplitter
	 * @param parentTile the tile the component is placed on
	 * @param rotation the component is facing
	 * @param rightSide boolean meaning if it splits items to the right or the left. */
	public ConveyorSplitter(Tile parentTile, Direction rotation, boolean rightSide) {
		super(parentTile, rotation);
		sideDirection = rightSide ? rotation.rotatedClockwise() : rotation.rotatedAntiClockwise();
		disableSpritemask();
		TextureManager textureManager = GameGraphics.getTextureManager();
		
		String texture = rightSide ? "ConveyorSplitterR" : "ConveyorSplitterL";
		
		switch (rotation) {
		case NORTH:
			super.addTexture(textureManager.getTexture(texture));
			break;
		case EAST:
			super.addTexture(ImageRotater.rotateImage(textureManager.getTexture(texture), 90));
			break;
		case SOUTH:
			super.addTexture(ImageRotater.rotateImage(textureManager.getTexture(texture), 180));
			break;
		case WEST:
			super.addTexture(ImageRotater.rotateImage(textureManager.getTexture(texture), -90));
			break;
		}
		
		EnumSet<Direction> blacklist = EnumSet.of(rotation.rotatedAntiClockwise(), rotation.rotatedClockwise());

		attemptAccessingConveyors();
		targetConveyor = conveyorForward;
		
		GameGraphics.registerWorldObj(this, 2);
	
	}

	@Override
	public void process() {
		 attemptAccessingConveyors();
		//if (currentConveyor == null) return;
		
		//currentConveyor.collectItem().setActive(false);
		
		conveyorSwitch = !conveyorSwitch;
		flipCurrentConveyor();
		
	}

	private void attemptAccessingConveyors() {
		conveyorForward = ConveyorNetworkSystem.getConveyorFromTile(super.getTargetTile(rotation));
		conveyorSide = ConveyorNetworkSystem.getConveyorFromTile(super.getTargetTile(sideDirection));
		ConveyorSpriteManager.changeSprite(conveyorSide, sideDirection);
	}
	@Override
	public int getY() {
		return 0;
	}

	@Override
	public Conveyor getConveyor() {
		return this;
	}

	@Override
	public void removeObject() {	
	}

	public void flipCurrentConveyor() {
		if (conveyorSwitch && conveyorForward != null ) {
			targetConveyor = conveyorForward;
		}
		else if (!conveyorSwitch && conveyorSide != null ) {
			targetConveyor = conveyorSide;
		}
		else {
			targetConveyor = null;
		}
		
	}

	
}
