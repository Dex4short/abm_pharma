package default_package.admin.reserves;

import customs.buttons.DisposeProductButton;
import customs.buttons.RestoreProductButton;
import gui.Button;
import gui.Panel;
import gui.SearchBar;
import misc.interfaces.Icons;
import misc.interfaces.UICustoms;

public class PanelReserves extends Panel implements Icons, UICustoms{
	private static final long serialVersionUID = 3096802445723190456L;
	private static final int inventory_btn=1, delete_btn=0;
	private SearchBar search_bar;
	private Button btn[];
	private TableReserves table;
	
	public PanelReserves() {
		setBackground(doc_color[0]);
		
		search_bar = new SearchBar("search", TableReserves.ProductFields) {
			private static final long serialVersionUID = 5457888564606909189L;
			@Override
			public void onSearch() {
				
			}
		};
		search_bar.setArc(30);
		search_bar.setBounds(0, 0, 300, 30);
		add(search_bar);
		
		btn = new Button[2];
		btn[inventory_btn] = createRestoreProductButton();
		btn[delete_btn] = createDesposeProductButton();
		
		for(int i=0; i<btn.length; i++) {
			btn[i].setSize(30, 30);
			add(btn[i]);
		}
		
		table = new TableReserves() {
			private static final long serialVersionUID = 3858814701541743076L;
			private boolean enable;
			@Override
			public void onSelectTable(int[] n) {
				enable = (n.length > 0);
				btn[inventory_btn].setEnabled(enable);
				btn[delete_btn].setEnabled(enable);
			}
		};
		table.displayReservedProducts();
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
				table.restoreSelectedReservedProducts();
			}
			@Override
			public void onAction() {
				super.onAction();
				search_bar.closeFilter();
			}
		};
	}
	private final DisposeProductButton createDesposeProductButton(){
		return new DisposeProductButton() {
			private static final long serialVersionUID = -4149000508932728634L;
			@Override
			public void onDisposeProduct() {
				table.disposeSelectedReservedProducts();
			}
			@Override
			public void onAction() {
				super.onAction();
				search_bar.closeFilter();
			}
		};
	}
}
