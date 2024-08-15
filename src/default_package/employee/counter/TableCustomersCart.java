package default_package.employee.counter;

import database.mysql.MySQL;
import database.mysql.MySQL_Cart;
import database.mysql.MySQL_Orders;
import database.mysql.MySQL_Packaging;
import default_package.admin.inventory.TableInventory;
import gui.Button;
import misc.interfaces.UICustoms;
import misc.objects.Counter;
import misc.objects.Order;
import misc.objects.Cart;
import misc.objects.Product;
import misc.objects.Quantity;

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
	public Order[] getCartOrders() {
		Product products[] = getProducts();
		Order orders[] = new Order[products.length];
		for(int i=0; i< products.length; i++) {
			orders[i] = (Order)products[i];
		}
		return orders;
	}
	public Order checkCartProductExistingOrder(Product product) {
		Product products[] = getProducts();
		
		int	
		parentPack_id1 = product.getPackaging().getParentPackId(), 
		parentPack_id2;
		
		for(int i=0; i<products.length; i++) {
			parentPack_id2 = products[i].getPackaging().getParentPackId();
			if(parentPack_id1 == parentPack_id2) {
				return (Order)products[i];
			}
		}
		
		return null;
	}
	public void addProductToCart(Product product) {
		Order existing_order = checkCartProductExistingOrder(product);
		
		if(existing_order == null) {
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
			addCartOrder(order);
		}
		else {
			incrementCartOrder(existing_order, product);
		}
	}
	public void incrementCartOrder(Order order, Product product) {
		int
		qty1 = order.getPackaging().getQty().getQuantity(),
		qty2 = product.getPackaging().getQty().getQuantity(),
		
		size1 = order.getPackaging().getQty().getSize(),
		size2 = product.getPackaging().getQty().getSize();
		
		Quantity qty = new Quantity(
				qty1 + qty2, 
				size1 + size2
		);
		order.getPackaging().setQty(qty);
		MySQL_Packaging.updatePackaging(order.getPackaging());
		
		displayCartOrders();
	}
	public void addCartOrder(Order order) {
		MySQL_Orders.insertOrder(order);
		displayCartOrders();
	}
	public void displayCartOrders() {
		removeAllRows();
		
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
