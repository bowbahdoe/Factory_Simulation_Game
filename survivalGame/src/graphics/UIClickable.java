package graphics;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public interface UIClickable extends UIRenderable {
	
	//Rectangle has a function called contains() which takes in class Point as argument
	//Point is instantiated with (x,y)
	public Rectangle getBounds();
	public void onClick();
}