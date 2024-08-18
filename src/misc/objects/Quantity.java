package misc.objects;

public class Quantity {
	private int quantity, size;
	
	public Quantity(int quantity) {
		setQuantity(quantity);
		setSize(quantity);
	}
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
	public static Quantity addQuantities(Quantity qty_a, Quantity qty_b) {
		int qty1,qty2,qty,size;
		
		qty1 = qty_a.getQuantity();
		qty2 = qty_b.getQuantity();
		qty  = qty1 + qty2;
		
		size = qty_a.getSize();
		if(qty > size) {
			size = qty;
		}
		
		return new Quantity(qty, size);
	}
}
