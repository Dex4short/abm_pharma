package misc.objects;

public class Quantity {
	private int quantity, size;
	
	public Quantity(int quantity, int overall) {
		setQuantity(quantity);
		setSize(overall);
	}
	public int getQuantity() {
		return quantity;
	}
	public int getSize() {
		return size;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public void setSize(int overall) {
		this.size = overall;
	}
	
}
