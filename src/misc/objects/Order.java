package misc.objects;

public class Order extends Product{
	private int order_no;
	
	public Order(int order_no, int inv_id, Item item, Packaging packaging, Pricing pricing, Remarks remarks) {
		super(inv_id, item, packaging, pricing, remarks);
		setOrderNo(order_no);
	}
	public int getOrderNo() {
		return order_no;
	}
	public void setOrderNo(int order_no) {
		this.order_no = order_no;
	}
}