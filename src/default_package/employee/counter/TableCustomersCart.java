package default_package.employee.counter;

import default_package.admin.inventory.TableInventory;
import gui.Button;
import misc.interfaces.UICustoms;
import misc.objects.Product;

public abstract class TableCustomersCart extends TableInventory implements UICustoms{
	private static final long serialVersionUID = -3325745314260689909L;
	
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
