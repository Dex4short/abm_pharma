package misc.objects;

public class Cart {
	private int cart_no;
	private int counter_no;
	private int order_no;
	
	public Cart(int cart_no, int counter_no, int order_no) {
		setCartNo(cart_no);
		setCounterNo(counter_no);
		setOrderNo(order_no);

	}
	public int getCartNo() {
		return cart_no;
	}
	public void setCartNo(int cart_no) {
		this.cart_no = cart_no;
	}
	public int getCounterNo() {
		return counter_no;
	}
	public void setCounterNo(int counter_no) {
		this.counter_no = counter_no;
	}
	public int getOrderNo() {
		return order_no;
	}
	public void setOrderNo(int order_no) {
		this.order_no = order_no;
	}
}
