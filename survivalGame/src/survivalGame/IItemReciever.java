package survivalGame;

import survivalGame.ItemManagement.Item;

public interface IItemReciever {
	
	public void recieveItem(Item item);
	public boolean canRecieve();
}
