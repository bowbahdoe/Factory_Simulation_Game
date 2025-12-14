package graphics;

import java.util.ArrayList;
import java.util.List;

import survivalGame.Button;

public class Menu {
	List<UIClickable> buttons = new ArrayList<>();
	Button button = new Button(null, null, 0, 0, null);
	
	public Menu(TextureManager Tmanager) {
		buttons.add(new Button("AA", Tmanager.getTexture("InventorySlot"), 300, 300, 
				() -> System.out.println("AA")));
	}
}
