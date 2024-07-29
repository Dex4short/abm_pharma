package database.mysql;

import misc.enums.ItemCondition;
import misc.objects.Item;
import misc.objects.Packaging;
import misc.objects.Pricing;
import misc.objects.Product;
import misc.objects.Remarks;

public class MySQL_Inventory{
	public static final String InventoryColumns[] = {"inv_id", "item_id", "pack_id", "price_id", "rem_id", "item_condition"};
	
	public static void insertProduct(Product product, ItemCondition itemCondition) {
		MySQL_Items.insertItem(product.getItem());
		MySQL_Packaging.insertPackaging(product.getPackaging());
		MySQL_Pricing.insertPricing(product.getPricing());
		
		Object values[] = {
				product.getInvId(),
				product.getItem().getItemId(),
				product.getPackaging().getPackId(),
				product.getPricing().getPriceId(),
				-1, //no remarks id yet
				itemCondition.toString()
		};
		MySQL.insert("inventory", InventoryColumns, values);
	}
	public static int[] selectProductIds(int inv_id, ItemCondition itemCondition) {
		Object inv_result[][] = MySQL.select(InventoryColumns, "inventory", "where inv_id=" + inv_id + " and item_condition='" + itemCondition + "' ");
		
		int
		item_id  = (int)inv_result[0][1],
		pack_id  = (int)inv_result[0][2],
		price_id = (int)inv_result[0][3],
		rem_id   = (int)inv_result[0][4];
		
		return new int[] {inv_id, item_id, pack_id, price_id, rem_id};
	}
	public static Product[] selectProducts(String condition) {
		Object inventory_result[][] = MySQL.select(InventoryColumns, "inventory", condition);		
		
		Product products[] = new Product[inventory_result.length];
		int inv_id, item_id, pack_id, price_id, rem_id;
		
		for(int i=0; i<products.length; i++) {
			inv_id   = (int)inventory_result[i][0];
			item_id  = (int)inventory_result[i][1];
			pack_id  = (int)inventory_result[i][2];
			price_id = (int)inventory_result[i][3];
			rem_id   = (int)inventory_result[i][4];
			
			Item
			item = MySQL_Items.selectItem(item_id);
			
			Packaging
			packaging = MySQL_Packaging.selectPackaging(pack_id);
			
			Pricing
			pricing = MySQL_Pricing.selectPricing(price_id);
			
			Remarks
			remarks = MySQL_Remarks.selectRemarks(rem_id);
			
			products[i] = new Product(inv_id, item, packaging, pricing, remarks);
		}
		return products;
	}
	public static Product selectProduct(int inv_id) {
		return selectProducts("where inv_id=" + inv_id)[0];
	}
	public static Product[] selectAllProducts(ItemCondition itemCondition) {
		return selectProducts("where item_condition='" + itemCondition.toString() + "' ");
	}
	public static void updateProduct(Product product) {		
		MySQL_Items.updateItem(product.getItem());
		MySQL_Packaging.updatePackaging(product.getPackaging());
		MySQL_Pricing.updatePricing(product.getPricing());
	}
	public static void updateProductItemCondition(int inv_id, ItemCondition itemCondition) {
		MySQL.update("inventory", new String[]{"item_condition"}, new Object[] {itemCondition.toString()}, "where inv_id=" + inv_id);
	}
	public static void updateProductRemarks(int inv_id, Remarks remarks) {
		int rem_id;
		if(remarks == null) {
			MySQL_Remarks.deleteRemarks(inv_id);
			rem_id = -1;
		}
		else {
			MySQL_Remarks.insertRemarks(remarks);
			rem_id = remarks.getRemId();
		}
		MySQL.update("inventory", new String[]{"rem_id"}, new Object[] {rem_id}, "where inv_id=" + inv_id);
	}
}
