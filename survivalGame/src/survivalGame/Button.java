package survivalGame;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import graphics.GameGraphics;
import graphics.UIClickable;

public class Button implements UIClickable{

	private BufferedImage texture;
	private String text;
	private final int xPosition;
	private final int yPosition;
	private final Runnable function;
	public Button(String text, BufferedImage texture, int xPosition, int yPosition, Runnable function) {
		this.text = text;
		this.texture = texture;
		this.xPosition = xPosition;
		this.yPosition = yPosition; 
		this.function = function;
	}
	public Button(BufferedImage texture, int xPosition, int yPosition, Runnable function) {
		this.texture = texture;
		this.xPosition = xPosition;
		this.yPosition = yPosition; 
		this.function = function;
	}
	
	public void onClick() {
		function.run();
	}

	@Override
	public boolean isActive() {
		return true;
	}

	@Override
	public void renderUI(Graphics2D g, GameGraphics graphics) {
		g.drawImage(texture ,xPosition, yPosition, graphics);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(xPosition, yPosition, texture.getWidth(), texture.getHeight());
	}
	
}
