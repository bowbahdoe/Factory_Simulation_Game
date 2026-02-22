package survivalGame.userInterface;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.JButton;

import graphics.GameGraphics;
import graphics.UIRenderable;
import survivalGame.Button;
import survivalGame.GameKeyListener;
import survivalGame.GameState;
import survivalGame.WorldIO;

public class PauseMenu implements GameKeyListener, UIRenderable{

	Button[] buttons = new Button[3];
	final BufferedImage buttonTexture;
	
	private boolean active = false;
	
	public PauseMenu() {
		buttonTexture = GameGraphics.getTextureManager().getTexture("Button");
		int[] center = UIAlignment.getCoordinateFromAnchor(UIAnchor.CENTER);
	
	}
	@Override
	public void onKeyPressed(int keyCode) {
		if (keyCode != KeyEvent.VK_ESCAPE) return;
		active = !active;
	}

	@Override
	public void onKeyReleased(int keyCode) {
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public void renderUI(Graphics2D g, GameGraphics graphics) {
		
	}

}
