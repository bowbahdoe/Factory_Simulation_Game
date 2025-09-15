package survivalGame.ItemManagement;

public class ItemStack {
	private Item item;
	private int quantity;
	public final static int MAX_STACK = 50;
	
	public ItemStack(Item item, int quantity) {
		this.item = item;
		this.quantity = quantity;
	}
	
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public void setQuantity(int num) {
		quantity = num;
	}
	public int getQuantity() {
		return quantity;
	}
}
