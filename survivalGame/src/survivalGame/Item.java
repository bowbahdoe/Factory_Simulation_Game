package survivalGame;

import java.awt.Graphics2D;

public abstract class Item implements RenderComponent {
	
	protected int pixelX;
	protected int pixelY;
	
	@Override
	public int getY() {
		return 0;
	}
	
	@Override
	public boolean isActive() {
		return false;
	}
	
	@Override
	public void render(Graphics2D g, GameGraphics graphics) {
		
	}
}
