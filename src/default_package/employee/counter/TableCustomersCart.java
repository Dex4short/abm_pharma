package default_package.employee.counter;

import database.mysql.MySQL;
import database.mysql.MySQL_Cart;
import database.mysql.MySQL_Orders;
import default_package.admin.inventory.TableInventory;
import gui.Button;
import misc.enums.ItemCondition;
import misc.interfaces.UICustoms;
import misc.objects.Counter;
import misc.objects.Order;
import misc.objects.Cart;
import misc.objects.Product;

public abstract class TableCustomersCart extends TableInventory implements UICustoms{
	private static final long serialVersionUID = -3325745314260689909L;
	private Cart cart;
	
	public TableCustomersCart() {		
		String modified_columns[] = {
				ProductFields[item_no],
				ProductFields[description],
				ProductFields[lot_no],
				ProductFields[date_added],
				ProductFields[exp_date],
				ProductFields[brand],
				ProductFields[qty],
				ProductFields[uom],
				ProductFields[cost],
				ProductFields[unit_price],
				ProductFields[discount],
				ProductFields[unit_amount],
				"..."
		};
		setColumns(modified_columns);
	}
	@Override
	public void addRowAt(Row row, int n) {
		TableProductRow product_row = ((TableProductRow)row);
		row.addCell(new RemoveToCartButton(product_row.getProduct()));
		super.addRowAt(row, n);
	}
	public void openCart() {
		int 
		cart_no = getCounter().getCurrentCartNo(),
		counter_no = getCounter().getCounterNo();
		
		cart = MySQL_Cart.selectCart(cart_no, counter_no);
		if(cart == null) {
			createCart();
			MySQL_Cart.insertCart(cart);
		}

		displayCartOrders();
	}
	public void createCart() {
		cart = new Cart(getCounter().getCurrentCartNo(), getCounter().getCounterNo(), -1);
	}
	public void addProductToCart(Product product) {
		int inv_id = MySQL.nextUID("inv_id", "inventory");
		product.setInvId(inv_id);
		
		Order order = new Order(
				cart.getOrderNo(),
				product.getInvId(),
				product.getItem(),
				product.getPackaging(), 
				product.getPricing(),
				product.getRemarks()
		);
		MySQL_Orders.insertOrder(order);
		displayCartOrders();
	}
	public void displayCartOrders() {
		int order_no = cart.getOrderNo();
		
		Order orders[] = MySQL_Orders.selectOrders(order_no);
		TableProductRow row;
		
		for(Order order: orders) {
			row = createTableProductRow(order);
			addRow(row);
		}
	}
	
	public abstract Counter getCounter();

	private class RemoveToCartButton extends Button{
		private static final long serialVersionUID = 5389348761948869122L;
		private Product product;

		public RemoveToCartButton(Product product) {
			super("- Remove");
			setArc(20);
			this.product = product;
		}
		@Override
		public void setBounds(int x, int y, int width, int height) {
			super.setBounds(x, y + 5, width, height - 10);
		}
		@Override
		public void onAction() {
			// TODO Auto-generated method stub
		}
	}
}
