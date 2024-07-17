package database.mysql;

import misc.objects.Product;

public class MySQL_Inventory{
	public static String InventoryColumns[] = {"inv_id", "item_id", "pack_id", "price_id"};
	
	public static int insertInventory(Product product) {
		int 
		inv_id = MySQL.nextUID("inventory"),
		item_id = MySQL_Items.insertItem(
				product.getItemNo(),
				product.getDescription(),
				product.getLotNo(),
				product.getDateAdded(),
				product.getExpDate(),
				product.getBrand()
		),
		uom_id = MySQL_Uom.insertUom(
				product.getUom()
		),
		pack_id = MySQL_Packaging.insertPackaging(
				product.getQty(),
				uom_id
		),
		price_id = MySQL_Pricing.insertPricing(
				product.getCost(),
				product.getUnitPrice(),
				product.getDiscount(),
				product.getUnitAmount()
		);
		
		Object values[] = {
				inv_id,
				item_id,
				pack_id,
				price_id
		};
		MySQL.insert("inventory", InventoryColumns, values);
		
		return inv_id;
	}
	/*
	public static Product[] selectAllProducts() {
		Object result[][] = MySQL.select(new String[] {"*"}, "inventory", "");
		Product products[] = new Product[result.length];
		
		for(int i=0; i<products.length; i++) {
			products[i]MySQL_Inventory = new Product(
					(int)result[i][0],
					(String)result[i][1], 
					(String)result[i][2], 
					new Date((String)result[i][3]), 
					new Date((String)result[i][4]),
					(String)result[i][5], 
					new Quantity(i, i), null, null, null, null, null);
		}
		return
	}
	*/
}
