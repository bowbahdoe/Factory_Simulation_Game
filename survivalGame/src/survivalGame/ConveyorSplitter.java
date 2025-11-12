package survivalGame;

import java.util.EnumSet;

import graphics.GameGraphics;
import graphics.TextureManager;
import graphics.ImageManipulation.ImageRotater;

public class ConveyorSplitter extends FactoryComponent implements IContainsConveyor{
	
	private Conveyor conveyorForward;
	private Conveyor conveyorSide;
	
	private Conveyor currentConveyor;
	
	boolean conveyorSwitch = true;
	/**Constructor to instantiate ConveyorSplitter
	 * @param parentTile the tile the component is placed on
	 * @param rotation the component is facing
	 * @param rightSide boolean meaning if it splits items to the right or the left. 
	 */
	public ConveyorSplitter(Tile parentTile, Direction rotation, boolean rightSide) {
		super(parentTile, rotation);
		
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
		conveyorForward = new Conveyor(parentTile, rotation, blacklist);
		conveyorForward.setRender(false);
		conveyorForward.attachParentComponent(this);
		
		conveyorSide = new Conveyor(parentTile, rightSide ? rotation.rotatedClockwise() : rotation.rotatedAntiClockwise(), blacklist);
		conveyorSide.setRender(false);
		conveyorSide.attachParentComponent(this);
		
		currentConveyor = conveyorForward;
		GameGraphics.getInstance().registerWorldObj(this, 2);
	
	}

	@Override
	public void onTick() {
		//if (currentConveyor == null) return;
		
		currentConveyor.collectItem().setActive(false);
		
		conveyorSwitch = !conveyorSwitch;
		flipCurrentConveyor();
		currentConveyor.onTick();
		
	}

	@Override
	public int getY() {
		return 0;
	}

	@Override
	public Conveyor getConveyor() {
		return currentConveyor;
	}

	@Override
	public void removeObject() {	
	}

	public void flipCurrentConveyor() {
		if (conveyorSwitch && conveyorForward.canPassToTarget()) {
			currentConveyor = conveyorForward;
		}
		else if (!conveyorSwitch && conveyorSide.canPassToTarget()) {
			currentConveyor = conveyorSide;
		}
		else {
			currentConveyor = null;
		}
		
	}

	
}
