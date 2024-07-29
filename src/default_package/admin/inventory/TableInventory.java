package default_package.admin.inventory;

import customs.tables.TableProducts;
import database.mysql.MySQL_Inventory;
import misc.enums.ItemCondition;
import misc.objects.Product;
import misc.objects.Remarks;

public abstract class TableInventory extends TableProducts{
	private static final long serialVersionUID = 5365427716438932455L;
	
	public TableInventory() {
		
	}
	public void addInventoryProduct(Product product) {
		MySQL_Inventory.insertProduct(product, ItemCondition.STORED);
		
		TableProductRow 
		inv_row = createTableProductRow(product);
		
		addRow(inv_row);
	}
	public void editInventoryProduct(Product product) {
		int selected_row = getSelectedRows()[0];
		
		MySQL_Inventory.updateProduct(product);
		
		TableProductRow 
		inv_row = createTableProductRow(product);

		removeRowAt(selected_row);
		addRowAt(inv_row, selected_row);
	}
	public void displayInventoryProducts() {
		displayProducst(ItemCondition.STORED);
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
