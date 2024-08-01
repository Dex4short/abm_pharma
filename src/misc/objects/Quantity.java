package misc.objects;

public class Quantity {
	private int quantity, size;
	
	public Quantity(int quantity, int size) {
		setQuantity(quantity);
		setSize(size);
	}
	@Override
	public String toString() {
		String str = getQuantity() + "/" + getSize();
		return str;
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
