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
	public final int xPosition;
	public final int yPosition;
	public final int width;
	public final int height;
	private final Runnable function;
	private boolean isActive = true;
	private int fontSize = 42;
	
	public Button(String text, BufferedImage texture, int xPosition, int yPosition, GameState state,  Runnable function) {
		this.text = text;
		this.texture = texture;
		this.xPosition = xPosition;
		this.yPosition = yPosition; 
		this.function = function;
		width = texture.getWidth();
		height = texture.getHeight();
		InputListener.getInstance().registerClickListener(state, this);
	}
	public Button(String text, Rectangle rect, GameState state,  Runnable function) {
		this.text = text;
		this.xPosition = rect.x;
		this.yPosition = rect.y; 
		this.function = function;
		width = rect.width;
		height = rect.height;
		InputListener.getInstance().registerClickListener(state, this);
	}

	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}
	
	public void renderUI(Graphics2D g, GameGraphics graphics) {
		if (!isActive) return;
		if (texture != null) {
			g.drawImage(texture ,xPosition, yPosition, graphics);
		}
		Font largeFont = new Font("Arial", Font.BOLD, fontSize);
	    g.setFont(largeFont);
	    
	    int textX =  xPosition + width / 2 - g.getFontMetrics().stringWidth(text) / 2;
	    int textY = yPosition + height / 2;
		g.drawString(text,textX, textY + 15);
	}
	
	@Override
	public void onClick(MouseEvent e) {
		if (!isActive) return;
		if (!(e.getX() > xPosition && e.getX() < xPosition + width)) return;
		if (!(e.getY() > yPosition && e.getY() < yPosition + height)) return;
		
		function.run();
	}
	
	public void setText(String text) {
		this.text = text;
	}
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	public void setActive(boolean state) {
		isActive = state;
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	
}
