package survivalGame;

import java.awt.Graphics2D;

public interface RenderComponent {
	int getY();
	boolean isActive();
	void render(Graphics2D g, GameGraphics graphics);
}
