package graphics;

import java.awt.Graphics2D;

public interface UIRenderable {
	boolean isActive();
	void renderUI(Graphics2D g, GameGraphics graphics);
}
