package default_package.admin.disposal;

import customs.buttons.ReserveProductButton;
import customs.buttons.RestoreProductButton;
import gui.Button;
import gui.Panel;
import gui.SearchBar;
import misc.interfaces.Icons;
import misc.interfaces.UICustoms;
import misc.objects.Remarks;

public class PanelDisposal extends Panel implements Icons, UICustoms{
	private static final long serialVersionUID = 3096802445723190456L;
	private static final int restore_btn=2, reserve_btn=1, delete_btn=0;
	private SearchBar search_bar;
	private Button btn[];
	private TableDisposals table;
	
	public PanelDisposal() {
		setBackground(doc_color[0]);
		
		search_bar = new SearchBar("search", TableDisposals.ProductFields) {
			private static final long serialVersionUID = 5457888564606909189L;
			@Override
			public void onSearch() {
				//TODO
			}
		};
		search_bar.setArc(30);
		search_bar.setBounds(0, 0, 300, 30);
		add(search_bar);
		
		btn = new Button[3];
		btn[restore_btn] = createRestoreProductButton();
		btn[reserve_btn] = createReserveProductButton();
		btn[delete_btn] = new DeleteButton();
		
		for(int i=0; i<btn.length; i++) {
			btn[i].setSize(30, 30);
			add(btn[i]);
		}
		
		table = new TableDisposals() {
			private static final long serialVersionUID = 3858814701541743076L;
			private boolean enable;
			@Override
			public void onSelectTable(int[] n) {
				enable = (n.length > 0);
				btn[restore_btn].setEnabled(enable);
				btn[reserve_btn].setEnabled(enable);
				btn[delete_btn].setEnabled(enable);
			}
		};
		add(table);
	}
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		for(int i=btn.length-1; i>=0; i--) {
			btn[i].setLocation(getWidth() - (35 * i) - btn[0].getWidth(), 0);
		}
		table.setBounds(-10, 40, width+20, height-40);
	}
	private final RestoreProductButton createRestoreProductButton() {
		return new RestoreProductButton() {
			private static final long serialVersionUID = -7036599494243267255L;
			@Override
			public void onRestoreProduct() {
				table.restoreSelectedDisposedProducts();
			}
			@Override
			public void onAction() {
				super.onAction();
				search_bar.closeFilter();
			}
		};
	}
	private final ReserveProductButton createReserveProductButton() {
		return new ReserveProductButton() {
			private static final long serialVersionUID = 5872322339093033242L;
			@Override
			public void onReserveProduct(Remarks remarks) {
				table.reserveSelectedDisposedProducts(remarks);
			}
			@Override
			public void onAction() {
				super.onAction();
				search_bar.closeFilter();
			}
		};
	}
	
	private class DeleteButton extends Button{
		private static final long serialVersionUID = -5869347417422217739L;
		public DeleteButton() {
			super(DeleteIcon);
			custom_button_appearance1(this);
			setEnabled(false);
		}
		@Override
		public void onAction() {
			// TODO Auto-generated method stub
		}
	}
}
