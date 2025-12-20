package survivalGame;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import graphics.GameGraphics;
import graphics.UIClickable;

public class Button implements MouseClickListener{

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
		InputListener.getInstance().registerClickListener(GameState.MENU, this);
	}
	public Button(BufferedImage texture, int xPosition, int yPosition, Runnable function) {
		this.texture = texture;
		this.xPosition = xPosition;
		this.yPosition = yPosition; 
		this.function = function;
		InputListener.getInstance().registerClickListener(GameState.MENU, this);
	}	

	public void renderUI(Graphics2D g, GameGraphics graphics) {
		g.drawImage(texture ,xPosition, yPosition, graphics);
		Font largeFont = new Font("Arial", Font.BOLD, 42);
	    g.setFont(largeFont);
		g.drawString(text, xPosition + texture.getWidth() / 2 - text.length() * 12, yPosition + texture.getHeight() / 2 + 15);
	}
	
	@Override
	public void onClick(MouseEvent e) {
		if (!(e.getX() > xPosition && e.getX() < xPosition + texture.getWidth())) return;
		if (!(e.getY() > yPosition && e.getY() < yPosition + texture.getHeight())) return;
		
		function.run();
	}

	
	
}
