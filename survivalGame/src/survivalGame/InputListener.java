package survivalGame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InputListener implements KeyListener, MouseListener {

	private int horiz;
	private int vert;
	
	private int[] clickedCoords = new int[2];
	private Tile clickedTile;
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
    public void keyPressed(KeyEvent e) {
        // Get the key code of the pressed key
        int keyCode = e.getKeyCode();

        // Check for WASD keys
        switch (keyCode) {
            case KeyEvent.VK_W:
                //System.out.println("W key pressed");
                vert = 1;
                break;
            case KeyEvent.VK_A:
                //System.out.println("A key pressed");
                horiz = 1;
                break;
            case KeyEvent.VK_S:
                //System.out.println("S key pressed");
                vert = -1;
                break;
            case KeyEvent.VK_D:
                //System.out.println("D key pressed");
                horiz = -1;
                break;
        }
    }
	public int[] listenMovement() {
		return new int[] {horiz,vert};
	}
	@Override
	public void keyReleased(KeyEvent e) {
	
		// Reset movement 
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_S:
                vert = 0;  
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_D:
                horiz = 0; 
                break;
        }
	}

	public int[] listenClick() {
		return clickedCoords;
	}
	public Tile listenClickedTile() {
		return clickedTile;
	}
	
	private Tile getClickedTile() {
		int x = (clickedCoords[0] + GameGraphics.getInstance().getOriginOffset()[0]) / GameGraphics.getInstance().tileSize;
		int y = (clickedCoords[1] + GameGraphics.getInstance().getOriginOffset()[1]) / GameGraphics.getInstance().tileSize;
		int chunkSize = GameGraphics.getInstance().chunkSize;
		int chunkAmount = GameGraphics.getInstance().worldSize / chunkSize;
		TileChunk chunk = (TileChunk) GameGraphics.getInstance().chunks[chunkAmount * (x / chunkSize) + (y / chunkSize) ];
		
		int chunkX = x - (chunk.x * chunkSize);
		int chunkY = y - (chunk.y * chunkSize);
		
		System.out.println("-------------------------------------------------");
		System.out.println("Coords clicked: " + x + ", " + y);
		System.out.println("Chunk coords: " + chunk.x + ", " + chunk.y + "    ||||||||      Coords in chunk: " + chunkX + ", " + chunkY + " |||   pos in list: " + (chunkX * chunkSize + chunkY));
		Tile tile = chunk.tiles.get(chunkX * chunkSize + chunkY);
		System.out.println("Tile Coords: " + tile.x + ", " + tile.y + " is Selected? " + tile.isSelected());
		return tile;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Clicked at " + e.getX() + ", " + e.getY());
		clickedCoords[0] = e.getX();
		clickedCoords[1] = e.getY();
		clickedTile = getClickedTile();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
