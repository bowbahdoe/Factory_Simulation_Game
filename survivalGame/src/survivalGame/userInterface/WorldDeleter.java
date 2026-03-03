package survivalGame.userInterface;

import graphics.CurrentGameState;
import graphics.GameGraphics;
import survivalGame.GameState;

public class WorldDeleter {
	public static void quitWorld() {
		GameGraphics.clearWorldObjects();
		GameGraphics.clearUI();
		CurrentGameState.gameState = GameState.MENU;
	}
}
