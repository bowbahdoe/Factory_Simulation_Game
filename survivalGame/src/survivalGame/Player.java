package survivalGame;

import java.awt.Color;
import java.awt.Graphics2D;

import graphics.GameGraphics;
import graphics.WorldRenderable;

public class Player implements Updatable, WorldRenderable{
	
	public InputListener input = new InputListener();
	private double x = 0;
	private double y = 0;
	private int velocity = 360;
	
	private int[] movement;
	
	private Tile selectedTile;
	
	public Player() {
		Updater.getInstance();
		Updater.register(this);
		GameGraphics.register(this, 2);
	
	}
	@Override
	public void update() {
		//System.out.println("On tile: " + x / 100 + " , " + y /100);
		movement = input.listenMovement();
		if (selectedTile != null) {
			selectedTile.setSelect(false);
		}
		selectedTile = input.listenClickedTile();
		if (selectedTile != null) {
			selectedTile.setSelect(true);
		}
		
		
		
		//int xCoord = selectionCoords[0] / GameGraphics.getInstance().tileSize;
		//GameGraphics.registerDynamic(this, (int)y / 100);
	}
	@Override
	public void fixedUpdate(long delta) {
		//Delta is in milliseconds, so divide it by 1000 to convert it to seconds lol
		if (movement[0] != 0 && movement[1] != 0) {
			double move = Math.sqrt(movement[0] * movement[0] + movement[1] * movement[1]);
			//(movement[0] / move) to restore direction, since move just gives magnitude. 
		    x += (movement[0] / move) * velocity * delta / 1000f;
		    y += (movement[1] / move) * velocity * delta / 1000f;
			
		}
		else {
			x += movement[0] * velocity * delta / 1000f;
			y += movement[1] * velocity * delta / 1000f;
		}
		
		
	}
	public double getYCoord() {
		return y;
	}
	public double getXCoord() {
		return x;
	}
	@Override
	public int getY() {
		return (int) y;
	}
	@Override
	public boolean isActive() {
		return true;
	}
	
	@Override
	public void render(Graphics2D g, GameGraphics graphics) {
		int width = graphics.getWidth();
		int height = graphics.getHeight();

		
		int pixelX = (int)x;
		int pixelY = (int)y;
		g.setColor(new Color(250,0,90));
		g.fillRect(-pixelX + width / 2 - 25,-pixelY + height / 2 - 25, 50, 50); 
	}
	public Tile getSelectedTile() {
		return selectedTile;
	}
	public void selectTile(Tile selectedTile) {
		this.selectedTile = selectedTile;
	}

}
