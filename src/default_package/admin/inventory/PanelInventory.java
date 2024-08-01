package default_package.admin.inventory;

import customs.buttons.AddProductButton;
import customs.buttons.DisposeProductButton;
import customs.buttons.EditProductButton;
import customs.buttons.ReserveProductButton;
import gui.Button;
import gui.Panel;
import gui.SearchBar;
import misc.interfaces.Icons;
import misc.interfaces.UICustoms;
import misc.objects.Product;
import misc.objects.Remarks;

public class PanelInventory extends Panel implements Icons, UICustoms{
	private static final long serialVersionUID = -8198736918718976336L;
	private static final int print_btn=4, reserve_btn=3, delete_btn=2, edit_btn=1, add_btn=0;
	private SearchBar search_bar;
	private Button btn[];
	private TableInventory table;

	public PanelInventory() {
		setBackground(doc_color[0]);
		
		search_bar = new SearchBar("search", TableInventory.ProductFields) {
			private static final long serialVersionUID = 5457888564606909189L;
			@Override
			public void onSearch() {
				
			}
		};
		search_bar.setArc(30);
		search_bar.setBounds(0, 0, 300, 30);
		add(search_bar);
		
		btn = new Button[5];
		btn[print_btn] = new PrintButton();
		btn[reserve_btn] = createReserveProductButton();
		btn[delete_btn] = createDesposeProductButton();
		btn[edit_btn] = createEditProductButton();
		btn[add_btn] = createAddProductButton();

		for(int i=btn.length-1; i>=0; i--) {
			btn[i].setSize((i==0 ? 120 : 30),  30);
			add(btn[i]);
		}
		
		table = new TableInventory() {
			private static final long serialVersionUID = 1053567446126133371L;
			private boolean enable;
			@Override
			public void onSelectTable(int[] n) { 
				enable = (n.length > 0);
				btn[delete_btn].setEnabled(enable);
				btn[reserve_btn].setEnabled(enable);

				enable = (n.length == 1);
				btn[edit_btn].setEnabled(enable);
			}
			@Override
			public void onPointTable(int n) {
				search_bar.closeFilter();
			}
		};
		table.displayInventoryProducts();
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
	
	private class PrintButton extends Button{
		private static final long serialVersionUID = -4074774662182951765L;
		public PrintButton() {
			super(PrinterIcon);
			custom_button_appearance1(this);
		}
		@Override
		public void onAction() {
			onPrintProducts();
		}
		public void onPrintProducts() {
			search_bar.closeFilter();
		}
	}
	
	private final ReserveProductButton createReserveProductButton() {
		return new ReserveProductButton() {
			private static final long serialVersionUID = 5872322339093033242L;
			@Override
			public void onReserveProduct(Remarks remarks) {
				table.reserveSelectedInventoryProducts(remarks);
			}
			@Override
			public void onAction() {
				super.onAction();
				search_bar.closeFilter();
			}
		};
	}
	private final EditProductButton createEditProductButton() {
		return new EditProductButton() {
			private static final long serialVersionUID = -886513641879925498L;
			@Override
			public void onEditProduct(Product product) {
				table.editInventoryProduct(product);
			}
			@Override
			public TableInventory getTableInventory() {
				return table;
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
				table.disposeSelectedInventoryProducts();
			}
			@Override
			public void onAction() {
				super.onAction();
				search_bar.closeFilter();
			}
		};
	}
	private final AddProductButton createAddProductButton() {
		return new AddProductButton() {
			private static final long serialVersionUID = -4302509354699595386L;
			@Override
			public void onAddProduct(Product product) {
				table.addInventoryProduct(product);
			}
			@Override
			public void onAction() {
				super.onAction();
				search_bar.closeFilter();
			}
		};
	}
}
