package survivalGame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import survivalGame.ItemManagement.ItemFactory;
import survivalGame.ItemManagement.ItemID;
import survivalGame.ItemManagement.PlaceableItem;
import survivalGame.ItemManagement.WorldItem;
import survivalGame.TileManagement.Tile;
import survivalGame.TileManagement.TileProvider;
import survivalGame.tileObjects.TileObject;
import survivalGame.tileObjects.FactoryComponents.Conveyor;

public class BuildingController implements GameKeyListener, MouseClickListener {
	private BuildMode buildMode = BuildMode.BUILD;
	
	private Direction buildRotation = Direction.NORTH;
	private Player player;
	
	public BuildingController(Player player) {
		this.player = player;
		InputListener.getInstance().registerKeyListener(this);
		InputListener.getInstance().registerClickListenerToWorld(this);
	}
	
	
	public void placeBuild(Tile tile, Player player) {
		//If tile is occupied, don't place build.
		if (tile.getTileObject() != null) {
			return;
		}
		//If player hasn't selected a hotbarSlot, return.
		if (player.getSelectedHotbarSlot() == null) return;
		if (!(player.getSelectedHotbarSlot().getItem() instanceof PlaceableItem)) return;
		
 		//place the item if it is placeable.
		PlaceableItem toPlace = (PlaceableItem)player.getSelectedHotbarSlot().getItem();
		if (toPlace == null) return;
		TileObject placedObject = toPlace.place(tile, buildRotation);
	
		if (placedObject instanceof Conveyor) {
			Conveyor conv = ((Conveyor) placedObject);
			ConveyorManager.getInstance().registerConveyor(conv);
			conv.recieveWorldItem(new WorldItem(ItemFactory.createItem(ItemID.WOOD), conv.parentTile.pixelX, conv.parentTile.pixelY));
		}
	}

	public void deleteBuild(Tile tile) {
		if (tile.getTileObject() instanceof FactoryComponent component) {
			component.removeObject();
			tile.setTileObject(null);
		}
	}
	@Override
	public void onKeyPressed(int keyCode) {
		//E -> rotate clockwise
		//Q -> rotate antiClockwise
		//B -> toggle building
		if (keyCode == KeyEvent.VK_E) {
    		buildRotation = buildRotation.rotatedClockwise();
    	}
    	else if(keyCode == KeyEvent.VK_Q) {
    		buildRotation = buildRotation.rotatedAntiClockwise();
    	}
    	else if (keyCode == KeyEvent.VK_B) {
    		if (buildMode == BuildMode.BUILD) buildMode = BuildMode.SELECT;
    		else {
    		buildMode = BuildMode.BUILD;
    		}
    	}
    	else if (keyCode == KeyEvent.VK_V) {
    		if (buildMode == BuildMode.DELETE) buildMode = BuildMode.SELECT;
    		else {
    		buildMode = BuildMode.DELETE;
    		}
    	}
	}

	@Override
	public void onKeyReleased(int keyCode) {
	}
	
	public BuildMode getBuildMode() {
		return buildMode;
	}
	
	public Direction getBuildRotation() {
		return buildRotation;
	}

	@Override
	public void onClick(MouseEvent e) {
		//Select Tile and place build if buidling enabled.
		Tile tile = TileProvider.pixel_AccessTile(e.getX(), e.getY());
		if (buildMode == BuildMode.BUILD) {
			
			placeBuild(tile,player);
			return;
		}
		if (buildMode != BuildMode.DELETE) return;
		
		deleteBuild(tile);
	}
}
