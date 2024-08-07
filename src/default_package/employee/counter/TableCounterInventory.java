package default_package.employee.counter;

import default_package.ABM_Pharma;
import default_package.admin.inventory.TableInventory;
import gui.Button;
import misc.interfaces.UICustoms;
import misc.objects.Product;

public abstract class TableCounterInventory extends TableInventory implements UICustoms{
	private static final long serialVersionUID = -3325745314260689909L;

	public TableCounterInventory() {
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
		row.addCell(new AddToCartButton());
		super.addRowAt(row, n);
	}
	
	private class AddToCartButton extends Button{
		private static final long serialVersionUID = 5389348761948869122L;

		public AddToCartButton() {
			super("+ Add to Cart");
			setArc(20);
		}
		@Override
		public void setBounds(int x, int y, int width, int height) {
			super.setBounds(x, y + 5, width, height - 10);
		}
		@Override
		public void onAction() {
			Product product = ((TableProductRow)getParent()).getProduct();
			ABM_Pharma.getWindow().getStacksPanel().pushPanel(new AddToCartSubPanelQty(product), shadow);
		}
		
		private class AddToCartSubPanelQty extends SubPanelQty{
			private static final long serialVersionUID = 4404997689749490601L;
			
			public AddToCartSubPanelQty(Product product) {
				super(product);
			}
			@Override
			public void onProductQtyOk(Product[] byproducts) {
				for(Product product: byproducts) {
					System.out.println(product.toString() + "\n");
				}
				//TODO
			}
		}
	}
}
