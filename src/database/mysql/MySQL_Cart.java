package database.mysql;

import misc.objects.Cart;

public class MySQL_Cart {
	private static final String CartFields[] = {"cart_no", "counter_no", "order_no"};
	
	public static void insertCart(Cart cart) {
		int order_no = MySQL.nextUID("order_no", "cart");
		
		cart.setOrderNo(order_no);
		
		Object values[] = {
			cart.getCartNo(),
			cart.getCounterNo(),
			cart.getOrderNo()
		};
		MySQL.insert("cart", CartFields, values);
	}
	public static Cart selectCart(int cart_no, int counter_no) {
		Object cart_result[][] = MySQL.select(CartFields, "cart", "where cart_no=" + cart_no + " and counter_no=" + counter_no);
		
		if(cart_result.length != 0) {
			Cart cart = new Cart(
					(int)cart_result[0][0],
					(int)cart_result[0][1],
					(int)cart_result[0][2]
			);
			return cart;
		}
		else {
			return null;
		}
	}
}
