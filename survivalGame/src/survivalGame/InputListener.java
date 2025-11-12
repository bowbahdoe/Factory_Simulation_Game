package survivalGame;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

import graphics.GameGraphics;
import graphics.UIClickable;
import survivalGame.ItemManagement.ItemFactory;
import survivalGame.ItemManagement.ItemID;
import survivalGame.ItemManagement.PlaceableItem;
import survivalGame.ItemManagement.WorldItem;

public class InputListener implements KeyListener, MouseListener, MouseWheelListener, MouseMotionListener {

	//Singleton reference
	private static InputListener InputListenerInstance;
	
	public static InputListener getInstance() {
        return InputListenerInstance;
    }
	
	private int horiz;
	private int vert;
	
	private boolean isBuilding = true;
	private Direction buildRotation = Direction.NORTH;
	
	private int[] clickedCoords = new int[2];
	private Tile clickedTile;

	private Player player;
	public boolean leftButtonHeld = false;
	private int mouseX = 0, mouseY = 0;
	 
	private boolean mouseDragging = false;
	public InputListener(Player player) {
		GameGraphics.getInstance().addMouseMotionListener(this);
		InputListenerInstance = this;
		this.player = player;
	}
	
	List<UIClickable> clickableInterfaces = new ArrayList<>();
	List<GameKeyListener> keyListeners = new ArrayList<>();
	
	public void registerClickable(UIClickable clickableInterface) {
		clickableInterfaces.add(clickableInterface);
	}
	
	public void registerKeyListener(GameKeyListener keyListener) {
		keyListeners.add(keyListener);
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	@Override
    public void keyPressed(KeyEvent e) {
        // Get the key code of the pressed key
        int keyCode = e.getKeyCode();

        // Check for WASD keys
        switch (keyCode) {
            case KeyEvent.VK_W:
                vert = 1;
                break;
            case KeyEvent.VK_A:
                horiz = 1;
                break;
            case KeyEvent.VK_S:
                vert = -1;
                break;
            case KeyEvent.VK_D:
                horiz = -1;
                break;
            case KeyEvent.VK_F:
            	player.collectItems();

        }
        for (GameKeyListener keyListener : keyListeners) {
        	keyListener.keyPressed(keyCode);
        }
        
        if (isBuilding) {

        	if (keyCode == KeyEvent.VK_E) {
        		buildRotation = buildRotation.rotatedClockwise();
        	}
        	else if(keyCode == KeyEvent.VK_Q) {
        		buildRotation = buildRotation.rotatedAntiClockwise();
        		
        	}
        }
    }
	public int[] listenMovement() {
		return new int[] {horiz,vert};
	}
	@Override
	public void keyReleased(KeyEvent e) {
	
		// Reset movement 
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_S:
                vert = 0;  
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_D:
                horiz = 0; 
                break;
        }
	}

	public int[] listenClick() {
		return clickedCoords;
	}
	public Tile listenClickedTile() {
		return clickedTile;
	}
	
	boolean first = true;
	private Tile getClickedTile() {
		Tile tile = TileProvider.pixel_AccessTile(clickedCoords[0], clickedCoords[1]);
		return tile;
	}
	private void manageBuilding(Tile tile) {
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
    public void mousePressed(MouseEvent e) {
		handleClick(e);
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftButtonHeld = true;
        }
    }
	
	private void handleClick(MouseEvent e) {

		for (UIClickable UI : clickableInterfaces) {
			//If the UI is an instance of Interactable UI, and the mouse click coordinates are within the bounds of that UI.. 
			if (UI.isActive() && UI.getBounds().contains(new Point(e.getX(),e.getY()))) {
				UI.onClick();
				return;
			}
		}
		handleWorldClick(e);
	}
	
	private void handleWorldClick(MouseEvent e) {
		System.out.println("Clicked at " + e.getX() + ", " + e.getY());
		clickedCoords[0] = e.getX();
		clickedCoords[1] = e.getY();
		Tile pastClickedTile = clickedTile;
		clickedTile = getClickedTile();
		if (isBuilding) {
			manageBuilding(clickedTile);
		}
		
		if (clickedTile == pastClickedTile) {
			clickedTile = null;
		}
	}
	
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftButtonHeld = false;
        }
    }


	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		GameGraphics graphics = GameGraphics.getInstance();
		if (e.isControlDown()) {
            if (e.getWheelRotation() < 0) {
            	graphics.setCameraZoom(graphics.getCameraZoom() * 1.1f); // zoom in
            } else {
            	graphics.setCameraZoom(graphics.getCameraZoom() / 1.1f); // zoom out
            }

            graphics.setCameraZoom(Math.max(0.1f, Math.min(graphics.getCameraZoom(), 5.0f)));

        }
	}
	public Direction getBuildRotation() {
		return buildRotation;
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
        mouseY = e.getY();
	}

	public int getMouseX() {
		return mouseX;
	}
	public int getMouseY() {
		return mouseY;
	}
}
