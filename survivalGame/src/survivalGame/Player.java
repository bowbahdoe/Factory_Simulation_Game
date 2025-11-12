package survivalGame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;


import graphics.GameGraphics;
import graphics.WorldRenderable;
import graphics.ImageManipulation.ImageRotater;
import survivalGame.ItemManagement.Item;
import survivalGame.ItemManagement.ItemID;
import survivalGame.ItemManagement.PlaceableItem;
import survivalGame.ItemManagement.WorldItem;
import survivalGame.userInterface.HotbarSlot;
import survivalGame.userInterface.InventorySlot;
import survivalGame.userInterface.PlayerUI;

public class Player implements Updatable, WorldRenderable{
	
	private InputListener input = new InputListener(this);
	private PlayerUI playerUI = new PlayerUI(this);
	BufferedImage[] blueprints = new BufferedImage[4];
	
	private double pixelX = 50;
	private double pixelY = 0;
	private int velocity = 360 * 6;
	
	private int[] movement;
	
	private Tile selectedTile;
	
	private BufferedImage character;
	
	private InventorySlot selectedInventorySlot;
	private HotbarSlot selectedHotbarSlot;
	public Player() {
		Updater.getInstance();
		Updater.register(this);
		GameGraphics.getInstance().registerWorldObj(this, 3);
		character = GameGraphics.getTextureManager().getTexture("Player");
	
		playerUI.addToInventory(ItemID.CONVEYOR,20);
		playerUI.addToInventory(ItemID.TREEHARVESTER,20);
		playerUI.addToInventory(ItemID.WOOD,20);
		
		blueprints[0] = GameGraphics.getTextureManager().getTexture("Blueprint");
		blueprints[1] = ImageRotater.rotateImage(blueprints[0], 90);
		blueprints[2] = ImageRotater.rotateImage(blueprints[0], 180);
		blueprints[3] = ImageRotater.rotateImage(blueprints[0], -90);
	}
	@Override
	public void update() {
		movement = input.listenMovement();
		if (selectedTile != null) {
			selectedTile.setSelect(false);
		}
		selectedTile = input.listenClickedTile();
		if (selectedTile != null) {
			selectedTile.setSelect(true);
		}
		
	}
	@Override
	public void fixedUpdate(long delta) {
		//Delta is in milliseconds, so divide it by 1000 to convert it to seconds lol
		if (movement[0] != 0 && movement[1] != 0) {
			double move = Math.sqrt(movement[0] * movement[0] + movement[1] * movement[1]);
			//(movement[0] / move) to restore direction, since move just gives magnitude. 
		    pixelX += (movement[0] / move) * velocity * delta / 1000f;
		    pixelY += (movement[1] / move) * velocity * delta / 1000f;
			
		}
		else {
			pixelX += movement[0] * velocity * delta / 1000f;
			pixelY += movement[1] * velocity * delta / 1000f;
		}
		
		
	}
	public double getYCoord() {
		return pixelY;
	}
	public double getXCoord() {
		return pixelX;
	}
	@Override
	public int getY() {
		return (int) pixelY;
	}
	@Override
	public boolean isActive() {
		return true;
	}
	
	@Override
	public void render(Graphics2D g, GameGraphics graphics) {
		int playerSize = 25;
		g.setColor(new Color(250,0,90,122));
		g.fillOval(-(int)pixelX - playerSize,-(int)pixelY - playerSize, 50, 50); 
		
		g.drawImage(character, -(int)pixelX - playerSize, -(int)pixelY - playerSize, graphics);
		
		if (selectedHotbarSlot == null) return;

		if (!(selectedHotbarSlot.getItem() instanceof PlaceableItem)) return;
		Tile tile = TileProvider.pixel_AccessTile(input.getMouseX(), input.getMouseY());
		if (!tile.isEmpty()) return;
		BufferedImage texture = null;
		switch (input.getBuildRotation()) {
		case NORTH:
			texture = blueprints[0];
			break;
		case EAST:
			texture = blueprints[1];
			break;
		case SOUTH:
			texture = blueprints[2];
			break;
		case WEST:
			texture = blueprints[3];
			break;
		}
		if (texture == null) return;
		g.drawImage(texture, (int) (tile.pixelX), (int) (tile.pixelY), graphics);
		
		g.drawImage(getSelectedHotbarSlot().getItem().getTexture(), tile.pixelX + 75, tile.pixelY + 75,25,25, graphics);
	}
	public Tile getSelectedTile() {
		return selectedTile;
	}
	public void selectTile(Tile selectedTile) {
		this.selectedTile = selectedTile;
	}
	
	public void collectItems() {
		Tile[] tiles = new Tile[9];
		tiles[0] = getTile(0,0);
		tiles[1] = getTile(0,100);
		tiles[2] = getTile(100,0);
		tiles[3] = getTile(100,100);
		tiles[4] = getTile(-100,0);
		tiles[5] = getTile(0,-100);
		tiles[6] = getTile(-100,-100);
		tiles[7] = getTile(100,-100);
		tiles[8] = getTile(-100,100);
		Map<Item, Integer> tempInventory = new HashMap<>();
		for (Tile tile : tiles) {
			if ( !(tile.getObject() instanceof IContainsConveyor) ) continue;
			
			Conveyor conv = ((IContainsConveyor) tile.getObject()).getConveyor();
			if (!conv.isEmpty()) {
				WorldItem worldItem = conv.collectItem();
				tempInventory.merge(worldItem.getItem(), 1, Integer::sum); 
				// same as tempInventory.put(item,tempInventory.get(item) + 1)	
			}
		}
		playerUI.mergeInventory(tempInventory);
	}
	private Tile getTile(int xOffset, int yOffset) {
		GameGraphics graphics = GameGraphics.getInstance();
		
		
		int x = (int) ((graphics.getOriginOffset()[0] + xOffset )/ GameGraphics.TILESIZE);
		int y = (int) ((graphics.getOriginOffset()[1] + yOffset ) / GameGraphics.TILESIZE);
		
		int chunkSize = graphics.chunkSize;
		int chunkAmount = graphics.worldSize / chunkSize;
		int positionInArray = chunkAmount * (x / chunkSize) + (y / chunkSize);		
		if (positionInArray < 0) return null;
		TileChunk chunk = (TileChunk) graphics.chunks[positionInArray];
		
		int chunkX = x - (chunk.x * chunkSize);
		int chunkY = y - (chunk.y * chunkSize);
		
		if (chunkX * chunkSize + chunkY < 0 ) return null; //Out of bounds
		Tile tile = chunk.tiles.get(chunkX * chunkSize + chunkY);

		
		return tile;
	}
	public InventorySlot getSelectedInventorySlot() {
		return selectedInventorySlot;
	}
	public void setSelectedInventorySlot(InventorySlot selectedInventorySlot) {
		this.selectedInventorySlot = selectedInventorySlot;
	}
	public HotbarSlot getSelectedHotbarSlot() {
		return selectedHotbarSlot;
	}
	public void setSelectedHotbarSlot(HotbarSlot selectedHotbarSlot) {
		this.selectedHotbarSlot = selectedHotbarSlot;
	}

}
