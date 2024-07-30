package customs.buttons;

import database.mysql.MySQL;
import default_package.ABM_Pharma;
import default_package.admin.reserves.SubPanelRemarks;
import gui.Button;
import gui.DialogPanel;
import misc.interfaces.Icons;
import misc.interfaces.UICustoms;
import misc.objects.Date;
import misc.objects.Remarks;
import misc.objects.Time;

public abstract class ReserveProductButton extends Button implements UICustoms, Icons{
	private static final long serialVersionUID = -4074774662182951765L;
	public ReserveProductButton() {
		super(ReserveIcon);
		custom_button_appearance(this);
		setEnabled(false);
	}
	@Override
	public void onAction() {
		onResrveProduct();
	}
	public void onResrveProduct() {
		ABM_Pharma.getWindow().getStacksPanel().pushPanel(new DialogPanelReserveProduct(), shadow);
	}
	
	private class DialogPanelReserveProduct extends DialogPanel{
		private static final long serialVersionUID = -7808996567397554095L;
		public DialogPanelReserveProduct() {
			super("Reserve Product", "Move selected product(s) for reservation?");
		}
		@Override
		public void onOk() {
			ABM_Pharma.getWindow().getStacksPanel().pushPanel(new SubPanelRemarks() {
				private static final long serialVersionUID = -8748684249639546914L;
				@Override
				public void onOk() {
					int    rem_id  = MySQL.nextUID("rem_id", "remarks");
					Date   date    = new Date();
					Time   time    = new Time();
					String details = getTextArea().getText();
					
					Remarks 
					remarks = new Remarks(rem_id, date, time, details);
					onReserveProduct(remarks);
					
					ABM_Pharma.getWindow().getStacksPanel().popPanel();
					ABM_Pharma.getWindow().getStacksPanel().popPanel();
					ABM_Pharma.getWindow().getDisplayPanel().floatMessage("Selected product(s) reserved.");
				}
				@Override
				public void onCancel() {
					ABM_Pharma.getWindow().getStacksPanel().popPanel();
					ABM_Pharma.getWindow().getStacksPanel().popPanel();
				}
			});
		}
		@Override
		public void onCancel() {
			ABM_Pharma.getWindow().getStacksPanel().popPanel();
		}
	}
	public abstract void onReserveProduct(Remarks remarks) ;
}
