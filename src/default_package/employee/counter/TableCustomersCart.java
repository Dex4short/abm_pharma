package default_package.employee.counter;

import database.mysql.MySQL;
import database.mysql.MySQL_Cart;
import database.mysql.MySQL_Inventory;
import database.mysql.MySQL_Orders;
import database.mysql.MySQL_Packaging;
import database.mysql.MySQL_Pricing;
import default_package.ABM_Pharma;
import default_package.admin.inventory.TableInventory;
import gui.Button;
import misc.enums.UomType;
import misc.interfaces.Accountancy;
import misc.interfaces.UICustoms;
import misc.objects.Counter;
import misc.objects.Decimal;
import misc.objects.Order;
import misc.objects.Packaging;
import misc.objects.Pricing;
import misc.objects.Cart;
import misc.objects.Product;
import misc.objects.Quantity;

public abstract class TableCustomersCart extends TableInventory implements UICustoms, Accountancy{
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
		TableOrderRow order_row = ((TableOrderRow)row);
		row.addCell(new RemoveToCartButton(order_row.getOrder()));
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
		Row rows[] = getRows();
		Order orders[] = new Order[rows.length];
		for(int i=0; i< orders.length; i++) {
			orders[i] = ((TableOrderRow)rows[i]).getOrder();
		}
		return orders;
	}
	public Order checkCartProductExistingOrder(Product product) {
		Product products[] = getCartOrders();
		
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
		Quantity qty = Quantity.addQuantities(
				order.getPackaging().getQty(), 
				product.getPackaging().getQty()
		);
		order.getPackaging().setQty(qty);
		MySQL_Packaging.updatePackaging(order.getPackaging());
		
		Decimal amount = calculateNetAmount(
				order.getPackaging(),
				order.getPricing()
		);
		order.getPricing().setUnitAmount(amount);
		MySQL_Pricing.updatePricing(order.getPricing());
		
		displayCartOrders();
	}
	public void addCartOrder(Order order) {
		MySQL_Orders.insertOrder(order);

		Decimal amount = calculateNetAmount(
				order.getPackaging(),
				order.getPricing()
		);
		order.getPricing().setUnitAmount(amount);
		MySQL_Pricing.updatePricing(order.getPricing());
		
		displayCartOrders();
	}
	public void displayCartOrders() {
		removeAllRows();
		
		int order_no = cart.getOrderNo();
		
		Order orders[] = MySQL_Orders.selectOrders(order_no);
		TableOrderRow row;
		
		for(Order order: orders) {
			row = new TableOrderRow(order);
			addRow(row);
		}
	}
	public Order getSelectedOrder() {
		TableOrderRow row = (TableOrderRow)getRowAt(getSelectedRows()[0]);
		return row.getOrder();
	}
	public void removeOrder(Order order) {
		MySQL_Orders.deleteOrder(order);
	}
	
	public abstract void onRemoveOrder(Order returned_order);
	public abstract Counter getCounter();

	private class RemoveToCartButton extends Button{
		private static final long serialVersionUID = 5389348761948869122L;
		private Order order;

		public RemoveToCartButton(Order order) {
			super("- Remove");
			setArc(20);
			this.order = order;
		}
		@Override
		public void setBounds(int x, int y, int width, int height) {
			super.setBounds(x, y + 5, width, height - 10);
		}
		@Override
		public void onAction() {
			Order order = ((TableOrderRow)getParent()).getOrder();
			ABM_Pharma.getWindow().getStacksPanel().pushPanel(new RemoveOrderSubPanelQty(order), shadow);
		}
		
		private class RemoveOrderSubPanelQty extends SubPanelQty{
			private static final long serialVersionUID = -1991482904744681781L;
			public RemoveOrderSubPanelQty(Product product) {
				super(product);
			}
			@Override
			public void onProductQtyOk(Packaging newPackaging, Packaging[] bypackagings) {
				int quantity, parent_priceId, price_id, inv_id;
				
				UomType
				type1 = order.getPackaging().getUom().getUnitType(),
				type2;
				
				Pricing parent_pricing, pricing;
				Decimal amount;
				
				for(Packaging pack: bypackagings) {
					type2 = pack.getUom().getUnitType();
					
					parent_priceId = MySQL_Inventory.selectProductPriceIdByPackId(pack.getParentPackId());
					parent_pricing = MySQL_Pricing.selectPricing(parent_priceId);
					
					amount = calculateNetAmount(pack, parent_pricing);
					
					if(type1 == type2) {
						quantity = pack.getQty().getQuantity();
						pack.getQty().setSize(quantity);
						
						price_id = MySQL_Inventory.selectProductPriceIdByPackId(pack.getPackId());
						pricing = MySQL_Pricing.selectPricing(price_id);
						pricing.setUnitAmount(amount);
						
						if(quantity != 0) {
							MySQL_Packaging.updatePackaging(pack);
							MySQL_Pricing.updatePricing(pricing);
						}
						else {
							MySQL_Orders.deleteOrderByPackaging(pack);
						}
					}
					else {
						if(pack.getQty().getSize() != 0) {
							inv_id = MySQL.nextUID("inv_id", "inventory");
							pricing = parent_pricing;
							pricing.setUnitAmount(amount);
							
							Order new_order = new Order(
								order.getOrderNo(),
								inv_id,
								order.getItem(),
								pack,
								pricing,
								order.getRemarks()
							);
							addCartOrder(new_order);
						}
					}
				}
				
				newPackaging.setPackId(order.getPackaging().getPackId());
				newPackaging.setParentPackId(order.getPackaging().getParentPackId());
				Order returned_order = new Order(
						order.getOrderNo(),
						order.getInvId(),
						order.getItem(),
						newPackaging,
						order.getPricing(),
						order.getRemarks()
				);
				
				onRemoveOrder(returned_order);

				ABM_Pharma.getWindow().getStacksPanel().popPanel();
				displayCartOrders();
			}
		}
	}
	
	public class TableOrderRow extends Row{
		private static final long serialVersionUID = -231322915165237246L;
		private Order order;
		
		public TableOrderRow(Order order) {
			super(createProductRowComponents(order));
			setOrder(order);
		}
		public Order getOrder() {
			return order;
		}
		public void setOrder(Order order) {
			this.order = order;
		}
	}
}
