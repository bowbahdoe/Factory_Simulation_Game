package survivalGame;

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class Item implements RenderComponent {
	
	protected int pixelX;
	protected int pixelY;
	protected boolean active;
	public Item(int pixelX, int pixelY) {
		this.pixelX = pixelX;
		this.pixelY = pixelY;
	}
	
	@Override
	public int getY() {
		return pixelY;
	}
	
	@Override
	public boolean isActive() {
		return active;
	}
	
	@Override
	public void render(Graphics2D g, GameGraphics graphics) {
		if (active) {
			g.setColor(Color.RED);
			g.fillRect(pixelX, pixelY, 20, 20);
		}
	}
}
