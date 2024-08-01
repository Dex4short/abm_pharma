package default_package.admin.disposal;

import customs.tables.TableProducts;
import misc.enums.ItemCondition;
import misc.objects.Remarks;

public abstract class TableDisposals extends TableProducts{
	private static final long serialVersionUID = 8490964193344844806L;

	public TableDisposals() {
		displayDisposedProducts();
	}
	public void displayDisposedProducts(){
		displayProducst(ItemCondition.DISPOSED);
	}
	public void restoreSelectedDisposedProducts() {
		setSelectedProductsItemConditionTo(ItemCondition.STORED);
		displayDisposedProducts();
	}
	public void reserveSelectedDisposedProducts(Remarks remarks) {
		setSelectedProductsReservation(remarks, ItemCondition.RESERVED);
		displayDisposedProducts();
	}
}
