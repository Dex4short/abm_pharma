package customs.buttons;

import default_package.ABM_Pharma;
import default_package.admin.inventory.SubPanelProduct;
import gui.Button;
import misc.interfaces.Icons;
import misc.interfaces.UICustoms;
import misc.objects.Product;

public abstract class EditProductButton  extends Button implements UICustoms,Icons{
	private static final long serialVersionUID = -4074774662182951765L;
	
	public EditProductButton() {
		super(PencilIcon);
		custom_button_appearance1(this);
		setEnabled(false);
	}
	@Override
	public void onAction() {
		Product products[] = getSelectedProduct();
		ABM_Pharma.getWindow().getStacksPanel().pushPanel(new SubPanelEditProduct(products), shadow);
	}
	
	private class SubPanelEditProduct extends SubPanelProduct{
		private static final long serialVersionUID = -2025602627570912043L;
		public SubPanelEditProduct(Product product[]) {
			super("Edit Product");
			
			new Thread() {
				public void run() {
					for(int i=0; i<product.length; i++) {
						getFillupPanel(i).displayProduct(product[i]);
					}
					showFillupPanel(product.length);
				};
			}.start();
		}
		@Override
		public void onProductOk(Product product[]) {
			onEditProduct(product);
			ABM_Pharma.getWindow().getStacksPanel().popPanel();
			ABM_Pharma.getWindow().getDisplayPanel().floatMessage(product[0].getItem().getDescription() + " " + product[0].getItem().getBrand() + " changed.");
		}
		@Override
		public void onProductCancel() {
			ABM_Pharma.getWindow().getStacksPanel().popPanel();
		}
	}
	public abstract void onEditProduct(Product product[]);
	public abstract Product[] getSelectedProduct();
}
