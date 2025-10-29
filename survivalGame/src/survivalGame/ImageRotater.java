package survivalGame;

import java.awt.Graphics2D;

import java.awt.image.BufferedImage;

public class ImageRotater {

	/**
	 * rotates Clockwise 
	 * @param bufferedImage input
	 * @param angle to rotate clockwise
	 * @return A rotated buffered image
	 */
	public static BufferedImage rotateImage(BufferedImage image, int angle) {
	    int width = image.getWidth();
	    int height = image.getHeight();

	    //new blank image
	    BufferedImage rotated = new BufferedImage(width, height, image.getType());
	    Graphics2D g2d = rotated.createGraphics();

	    g2d.rotate(Math.toRadians(angle), width / 2.0, height / 2.0);
	    g2d.drawImage(image, 0, 0, null);
	    g2d.dispose();

	    return rotated;
	}
	
}
