package database.mysql;

import misc.enums.ItemCondition;
import misc.objects.Order;
import misc.objects.Product;

public class MySQL_Orders {
	private static final String OrdersFields[] = {"order_no", "inv_id"};
	
	public static void insertOrder(Order order) {
		MySQL_Inventory.insertProduct(order, ItemCondition.ORDERED);
		
		Object values[] = {
			order.getOrderNo(),
			order.getInvId()
		};
		MySQL.insert("orders", OrdersFields, values);
	}
	public static Order[] selectOrders(int order_no) {
		Object order_results[][] = MySQL.select(OrdersFields, "orders", "where order_no=" + order_no);
		
		Order orders[] = new Order[order_results.length];
		Product product;
		int inv_id;
		
		for(int i=0; i< orders.length; i++) {
			inv_id = (int)order_results[i][1];
			product = MySQL_Inventory.selectProduct(inv_id);
			
			orders[i] = new Order(
					order_no, 
					product.getInvId(),
					product.getItem(),
					product.getPackaging(), 
					product.getPricing(),
					product.getRemarks()
			);
			orders[i].setOrderNo(order_no);
		}
		
		return orders;
	}
	public void updateOrderQuantity(Order order, int qty) {
		int inv_id = order.getInvId();
		MySQL.update("", OrdersFields, OrdersFields, null);
		
	}
}
