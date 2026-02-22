package survivalGame.inputs;

import java.awt.event.KeyEvent;

public interface GameKeyListener {
	
	void onKeyPressed(int keyCode);
    void onKeyReleased(int keyCode);
	
}
