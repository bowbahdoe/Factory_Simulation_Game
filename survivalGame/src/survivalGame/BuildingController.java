package survivalGame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import survivalGame.ItemManagement.ItemFactory;
import survivalGame.ItemManagement.ItemID;
import survivalGame.ItemManagement.PlaceableItem;
import survivalGame.ItemManagement.WorldItem;

public class BuildingController implements GameKeyListener, MouseClickListener {
	private boolean isBuilding = true;
	private Direction buildRotation = Direction.NORTH;
	private Player player;
	
	public BuildingController(Player player) {
		this.player = player;
		InputListener.getInstance().registerKeyListener(this);
		InputListener.getInstance().registerClickListenerToWorld(this);
	}
	
	public void toggleBuilding() {
		isBuilding = !isBuilding;
	}
	public void placeBuild(Tile tile, Player player) {
		if (tile.getObject() != null) {
			return;
		}
		if (player.getSelectedHotbarSlot() == null) return;
		PlaceableItem toPlace = (PlaceableItem)player.getSelectedHotbarSlot().getItem();
		if (toPlace == null) return;
		TileObject placedObject = toPlace.place(tile, buildRotation);
	
		if (placedObject instanceof Conveyor) {
			Conveyor conv = ((Conveyor) placedObject);
			ConveyorManager.getInstance().registerConveyor(conv);
			conv.recieveWorldItem(new WorldItem(ItemFactory.createItem(ItemID.WOOD), conv.parentTile.pixelX, conv.parentTile.pixelY));
		}
	}

	@Override
	public void onKeyPressed(int keyCode) {
		if (keyCode == KeyEvent.VK_E) {
    		buildRotation = buildRotation.rotatedClockwise();
    	}
    	else if(keyCode == KeyEvent.VK_Q) {
    		buildRotation = buildRotation.rotatedAntiClockwise();
    	}
    	else if (keyCode == KeyEvent.VK_B) {
    		toggleBuilding();
    	}
	}

	@Override
	public void onKeyReleased(int keyCode) {
		
	}
	
	public boolean isBuilding() {
		return isBuilding;
	}
	
	public Direction getBuildRotation() {
		return buildRotation;
	}

	@Override
	public void onClick(MouseEvent e) {
		Tile tile = TileProvider.pixel_AccessTile(e.getX(), e.getY());
		if ( !isBuilding ) {
			player.selectTile(tile);
			return;
		}
		placeBuild(tile,player);
	}
}
