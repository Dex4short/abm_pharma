package customs.buttons;

import default_package.ABM_Pharma;
import gui.Button;
import gui.DialogPanel;
import misc.interfaces.Icons;
import misc.interfaces.UICustoms;

public abstract class RestoreProductButton extends Button implements UICustoms, Icons{
	private static final long serialVersionUID = -6007712432442863988L;
	public RestoreProductButton() {
		super(InventoryIcon);
		custom_button_appearance(this);
		setEnabled(false);
	}
	@Override
	public void onAction() {
		ABM_Pharma.getWindow().getStacksPanel().pushPanel(new DialogPanelInventoryProduct(), shadow);
	}
	private class DialogPanelInventoryProduct extends DialogPanel{
		private static final long serialVersionUID = 4369926855217082617L;
		public DialogPanelInventoryProduct() {
			super("Restore Product", "Restore selected product(s) to inventory?");
		}
		@Override
		public void onOk() {
			onRestoreProduct();
			ABM_Pharma.getWindow().getStacksPanel().popPanel();
			ABM_Pharma.getWindow().getDisplayPanel().floatMessage("Selected product(s) restored.");
		}
		@Override
		public void onCancel() {
			ABM_Pharma.getWindow().getStacksPanel().popPanel();
		}
	}
	public abstract void onRestoreProduct();
}

