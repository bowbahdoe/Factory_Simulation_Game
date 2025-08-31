package survivalGame;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.List;
import java.util.TreeMap;

import graphics.GameGraphics;
import graphics.UIClickable;
import graphics.UIRenderable;

public class InputListener implements KeyListener, MouseListener, MouseWheelListener, MouseMotionListener {

	private int horiz;
	private int vert;
	
	private boolean isBuilding;
	private int buildRotation = 0;
	
	private int[] clickedCoords = new int[2];
	private Tile clickedTile;
	
	PlayerUI playerUI;
	Player player;
	public boolean leftButtonHeld = false;
	public int mouseX = 0, mouseY = 0;
	 
	public InputListener(PlayerUI playerUI,Player player) {
		this.playerUI = playerUI;
		this.player = player;
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
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
            case KeyEvent.VK_B:
            	isBuilding = !isBuilding;
            	break;
            case KeyEvent.VK_I:
            	playerUI.toggle();
            case KeyEvent.VK_F:
            	player.collectItems();
            	
        }
        
        if (isBuilding) {

        	if (keyCode == KeyEvent.VK_E) {
        		buildRotation++;

        	}
        	else if(keyCode == KeyEvent.VK_Q) {
        		buildRotation--;
        		
        	}
        	buildRotation = (buildRotation + 4) % 4;
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
		GameGraphics graphics = GameGraphics.getInstance();
		
		int pixelX = (int) ( (clickedCoords[0] - graphics.getSize().width / 2) / graphics.getCameraZoom() + graphics.getOriginOffset()[0]);
		int pixelY = (int) ((clickedCoords[1] - graphics.getSize().height / 2) / graphics.getCameraZoom() + graphics.getOriginOffset()[1]);
		int x = (pixelX / graphics.tileSize);
		int y = (pixelY / graphics.tileSize);
		
		int chunkSize = graphics.chunkSize;
		int chunkAmount = graphics.worldSize / chunkSize;
		int positionInArray = chunkAmount * (x / chunkSize) + (y / chunkSize);		
		TileChunk chunk = (TileChunk) graphics.chunks[positionInArray];
		
		int chunkX = x - (chunk.x * chunkSize);
		int chunkY = y - (chunk.y * chunkSize);
		
		System.out.println("-------------------------------------------------");
		System.out.println("Coords clicked: " + x + ", " + y);
		System.out.println("Chunk coords: " + chunk.x + ", " + chunk.y + "    ||||||||      Coords in chunk: " + chunkX + ", " + chunkY + " |||   pos in list: " + (chunkX * chunkSize + chunkY));
		if (chunkX * chunkSize + chunkY < 0 ) return null; //Out of bounds
		Tile tile = chunk.tiles.get(chunkX * chunkSize + chunkY);
		System.out.println("Tile Coords: " + tile.x + ", " + tile.y + " is Selected? " + tile.isSelected());
		
		//-------------------------
		if (isBuilding) {
			manageBuilding(tile);
		}
		
		
		return tile;
	}
	private void manageBuilding(Tile tile) {
		if (tile.getObject() != null) {
			return;
		}
		Conveyor conv = new Conveyor(tile,buildRotation);
		tile.setObject(conv);
		if (first || !first) {
			conv.recieveItem(new WorldItem(new Item("WoodItem"), conv.parentTile.pixelX, conv.parentTile.pixelY));
			first = false;
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	
	
	
	@Override
    public void mousePressed(MouseEvent e) {
		handleClick(e);
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftButtonHeld = true;
        }
    }
	
	private void handleClick(MouseEvent e) {
		List<UIRenderable> UILayers = GameGraphics.getInstance().getUILayers();
		
		for (UIRenderable UI : UILayers) {
			//If the UI is an instance of Interactable UI, and the mouse click coordinates are within the bounds of that UI.. 
			if (UI instanceof UIClickable && UI.isActive() && ((UIClickable) UI).getBounds().contains(new Point(e.getX(),e.getY()))  ) {
				((UIClickable) UI).onClick();
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseX = e.getX();
        mouseY = e.getY();
	}

}
