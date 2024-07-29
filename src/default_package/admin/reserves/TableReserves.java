package default_package.admin.reserves;


import customs.DescriptionArea;
import customs.tables.TableProducts;
import misc.enums.ItemCondition;
import misc.objects.Remarks;

public abstract class TableReserves extends TableProducts{
	private static final long serialVersionUID = 1L;

	public TableReserves() {
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
				"Remarks"
		};
		setColumns(modified_columns);
	}
	@Override
	public void addRowAt(Row row, int n) {
		TableProductRow product_row = ((TableProductRow)row);
		Remarks remarks = product_row.getProduct().getRemarks();
		row.addCell(new DescriptionArea(remarks.getDetails()));
		super.addRowAt(row, n);
	}
	public void displayReservedProducts() {
		displayProducst(ItemCondition.RESERVED);
	}
	public void disposeSelectedReservedProducts() {
		setSelectedProductsReservation(null, ItemCondition.DISPOSED);
		displayReservedProducts();
	}
	public void restoreSelectedReservedProducts() {
		setSelectedProductsReservation(null, ItemCondition.STORED);
		displayReservedProducts();
	}
	
	public abstract class RemarksArea extends DescriptionArea{
		private static final long serialVersionUID = 9125557805889421032L;
		public RemarksArea(Remarks remarks) {
			super(remarks.getDetails());
		}
	}
}
