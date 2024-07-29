package customs.buttons;

import default_package.ABM_Pharma;
import gui.Button;
import gui.DialogPanel;
import misc.interfaces.Icons;
import misc.interfaces.UICustoms;

public abstract class DisposeProductButton  extends Button implements UICustoms, Icons{
	private static final long serialVersionUID = -4074774662182951765L;
	public DisposeProductButton() {
		super(DeleteIcon);
		custom_button_appearance(this);
		setEnabled(false);
	}
	@Override
	public void onAction() {
		ABM_Pharma.getWindow().getStacksPanel().pushPanel(new DialogPanelDeleteProduct(), shadow);
	}
	
	private class DialogPanelDeleteProduct extends DialogPanel{
		private static final long serialVersionUID = -3521299212390503023L;
		public DialogPanelDeleteProduct() {
			super("Delete Product", "Move selected product(s) for disposal?");
		}
		@Override
		public void onOk() {
			onDisposeProduct();
			ABM_Pharma.getWindow().getStacksPanel().popPanel();
			ABM_Pharma.getWindow().getDisplayPanel().floatMessage("Selected product(s) disposed.");
		}
		@Override
		public void onCancel() {
			ABM_Pharma.getWindow().getStacksPanel().popPanel();
		}
	}
	public abstract void onDisposeProduct();
}