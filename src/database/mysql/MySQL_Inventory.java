package database.mysql;

import misc.interfaces.UID;
import misc.objects.Item;
import misc.objects.Packaging;
import misc.objects.Pricing;
import misc.objects.Product;

public class MySQL_Inventory{
	public static String InventoryColumns[] = {"inv_id", "item_id", "pack_id", "price_id"};
	
	public static int insertInventory(Product product) {
		int 
		inv_id = MySQL.nextUID("inventory"),
		item_id = MySQL_Items.insertItem(
				product.getItem()
		),
		pack_id = MySQL_Packaging.insertPackaging(
				product.getPackaging()
		),
		price_id = MySQL_Pricing.insertPricing(
				product.getPricing()
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
	public static Product[] selectAllProducts() {
		Object inventory_result[][] = MySQL.select(InventoryColumns, "inventory", "");		
		
		RetrievedProduct products[] = new RetrievedProduct[inventory_result.length];
		int item_id,pack_id,price_id;
		
		for(int i=0; i<products.length; i++) {
			final int inv_id = (int)inventory_result[i][0];
			
			item_id = (int)inventory_result[i][1];
			pack_id = (int)inventory_result[i][2];
			price_id = (int)inventory_result[i][3];
			
			Item
			item = MySQL_Items.selectItem(item_id);
			
			Packaging
			packaging = MySQL_Packaging.selectPackaging(pack_id);
			
			Pricing
			pricing = MySQL_Pricing.selectPricing(price_id);
			
			products[i] = new RetrievedProduct(item, packaging, pricing) {
				@Override
				public int getId() {
					// TODO Auto-generated method stub
					return inv_id;
				}
			};
			
		}
		
		return products;
	}
	
	public static abstract class RetrievedProduct extends Product implements UID{
		public RetrievedProduct(Item item, Packaging packaging, Pricing pricing) {
			super(item, packaging, pricing);
			// TODO Auto-generated constructor stub
		}
	}
}
