package default_package.admin.inventory;

import customs.tables.TableProducts;
import database.mysql.MySQL;
import database.mysql.MySQL_Inventory;
import misc.enums.ItemCondition;
import misc.objects.Product;
import misc.objects.Remarks;

public abstract class TableInventory extends TableProducts{
	private static final long serialVersionUID = 5365427716438932455L;
	
	public TableInventory() {
		
	}
	public void addInventoryProduct(Product product[]) {
		ItemCondition item_condition;
		for(int i=0; i<product.length; i++) {
			product[i].setInvId(MySQL.nextUID("inv_id", "inventory"));
			if(i==0) {
				item_condition = ItemCondition.STORED;
			}
			else {
				item_condition = ItemCondition.ARCHIVED;
				product[i].getItem().setItemId(product[0].getItem().getItemId());
				product[i].getPackaging().setParentPackId(product[0].getPackaging().getPackId());
			}
			MySQL_Inventory.insertProduct(product[i], item_condition);
		}
		displayInventoryProducts();
	}
	public void editInventoryProduct(Product product[]) {
		for(int i=0; i<product.length; i++) {
			MySQL_Inventory.updateProduct(product[i]);
		}
		displayInventoryProducts();
	}
	public void displayInventoryProducts() {
		displayProducts(ItemCondition.STORED);
	}
	public void disposeSelectedInventoryProducts() {
		setSelectedProductsItemConditionTo(ItemCondition.DISPOSED);
		displayInventoryProducts();
	}
	public void reserveSelectedInventoryProducts(Remarks remarks) {
		setSelectedProductsReservation(remarks, ItemCondition.RESERVED);
		displayInventoryProducts();
	}
}
