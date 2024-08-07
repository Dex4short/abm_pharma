package customs.buttons;

import default_package.ABM_Pharma;
import default_package.admin.inventory.SubPanelProduct;
import default_package.admin.inventory.SubPanelProduct.FillupPanel.QtyField;
import gui.IconedButton;
import misc.interfaces.Icons;
import misc.interfaces.UICustoms;
import misc.objects.Product;

public abstract class AddProductButton  extends IconedButton implements UICustoms,Icons{
	private static final long serialVersionUID = -4074774662182951765L;
	public AddProductButton() {
		super(AddIcon, "Add Product");
		custom_button_appearance1(this);
	}
	@Override
	public void onAction() {
		ABM_Pharma.getWindow().getStacksPanel().pushPanel(new SubPanelAddProduct(), shadow);
	}
	
	private class SubPanelAddProduct extends SubPanelProduct{
		private static final long serialVersionUID = 5028819089367136642L;
		public SubPanelAddProduct() {
			super("Add New Product");
			
			for(int i=0; i<3;i++) {
				((QtyField)getFillupPanel(i).getField(qty)).maintainAspectRatio(1);
			}
		}
		@Override
		public void onProductOk(Product product[]) {
			onAddProduct(product);
			ABM_Pharma.getWindow().getStacksPanel().popPanel();
			ABM_Pharma.getWindow().getDisplayPanel().floatMessage("new " + product[0].getItem().getDescription() + " " + product[0].getItem().getBrand() + " added.");
		}
		@Override
		public void onProductCancel() {
			ABM_Pharma.getWindow().getStacksPanel().popPanel();
		}
	}
	
	public abstract void onAddProduct(Product product[]);
}
