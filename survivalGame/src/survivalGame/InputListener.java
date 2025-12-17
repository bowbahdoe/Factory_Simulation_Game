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

import graphics.CurrentGameState;
import graphics.GameGraphics;
import graphics.UIClickable;
import survivalGame.ItemManagement.ItemFactory;
import survivalGame.ItemManagement.ItemID;
import survivalGame.ItemManagement.PlaceableItem;
import survivalGame.ItemManagement.WorldItem;

public class InputListener implements KeyListener, MouseListener, MouseWheelListener, MouseMotionListener {

	private static InputListener InputListenerInstance;
	
	public static InputListener getInstance() {
		if (InputListenerInstance == null) {
			InputListenerInstance = new InputListener();
		}
        return InputListenerInstance;
    }
	
	

	private int[] clickedCoords = new int[2];

	public boolean leftButtonHeld = false;
	private int mouseX = 0, mouseY = 0;
	 
	//private boolean mouseDragging = false;
	
	private static List<UIClickable> clickableUI = new ArrayList<>();
	private static List<MouseClickListener> clickable = new ArrayList<>();
	private static List<GameKeyListener> keyListeners = new ArrayList<>();
	
	public void registerClickableUI(UIClickable clickableInterface) {
		clickableUI.add(clickableInterface);
	}
	public void registerClickListener(MouseClickListener c) {
		clickable.add(c);
	}
	public void registerKeyListener(GameKeyListener keyListener) {
		keyListeners.add(keyListener);
	}

	@Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
  
        for (GameKeyListener keyListener : keyListeners) {
        	keyListener.onKeyPressed(keyCode);
        }
   
    }
	
	@Override
	public void keyReleased(KeyEvent e) {
	
		// Reset movement 
        int keyCode = e.getKeyCode();
        for (GameKeyListener keyListener : keyListeners) {
        	keyListener.onKeyReleased(keyCode);
        }
	}

	public int[] listenClick() {
		return clickedCoords;
	}

	boolean first = true;
	
	
	
	@Override
    public void mousePressed(MouseEvent e) {
		handleClick(e);
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftButtonHeld = true;
        }
        
        for (MouseClickListener clickableObject : clickable) {
        	clickableObject.onClick(e);
        }
    }
	
	private void handleClick(MouseEvent e) {

		for (UIClickable UI : clickableUI) {
			//If the UI is an instance of Interactable UI, and the mouse click coordinates are within the bounds of that UI.. 
			if (UI.isActive() && UI.getBounds().contains(new Point(e.getX(),e.getY()))) {
				UI.onClick();
				return;
			}
		}
		for (MouseClickListener click : clickable) {
			click.onClick(e);
		}
	}
	
	
	
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftButtonHeld = false;
        }
    }
    
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub

		if (e.isControlDown()) {
            if (e.getWheelRotation() < 0) {
            	GameGraphics.setCameraZoom(GameGraphics.getCameraZoom() * 1.1f); // zoom in
            } else {
            	GameGraphics.setCameraZoom(GameGraphics.getCameraZoom() / 1.1f); // zoom out
            }

            GameGraphics.setCameraZoom(Math.max(0.1f, Math.min(GameGraphics.getCameraZoom(), 5.0f)));

        }
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
	public void keyTyped(KeyEvent e) {
	}
}
