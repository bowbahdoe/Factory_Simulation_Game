package graphics;

import java.awt.Graphics2D;

public interface WorldRenderable {
	int getY();
	boolean isActive();
	void render(Graphics2D g, GameGraphics graphics);
}
