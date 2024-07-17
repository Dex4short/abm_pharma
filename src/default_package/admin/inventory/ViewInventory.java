package default_package.admin.inventory;

import database.mysql.MySQL_Inventory;
import default_package.ABM_Pharma;
import gui.Button;
import gui.IconedButton;
import gui.Panel;
import gui.SearchBar;
import misc.interfaces.Icons;
import misc.interfaces.InventoryConstants;
import misc.interfaces.Theme;
import misc.objects.Product;

public class ViewInventory extends Panel implements InventoryConstants, Icons{
	private static final long serialVersionUID = -8198736918718976336L;
	private static final int item_print_btn=4, item_send_to_drafts_btn=3, item_delete_btn=2, item_edit_btn=1, item_add_btn=0;
	private SearchBar search_bar;
	private Button btn[];
	private InventoryTable inventory_table;

	public ViewInventory() {
		setLayout(null);
		setBackground(Theme.doc_color[0]);
		
		search_bar = new InventorySearchBar();
		add(search_bar);
		
		btn = new Button[5];
		btn[item_print_btn] = new InventoryPrintButton();
		btn[item_send_to_drafts_btn] = new InventorySendToDraftsButton();
		btn[item_edit_btn] = new InventoryEditButton();
		btn[item_delete_btn] = new InventoryDeleteButton();
		btn[item_add_btn] = new InventoryAddProductButton();

		int i;
		for(i=btn.length-1; i>=0; i--) {
			btn[i].setSize((i==0 ? 120 : 30),  30);
			add(btn[i]);
		}
		
		inventory_table = new InventoryTable();
		add(inventory_table);
		
	}
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		onPanelAdminResized(width, height);
	}
	public Button getButton(int index) {
		return btn[index];
	}
	public void onPanelAdminResized(int width, int height) {
		int i;
		for(i=btn.length-1; i>=0; i--) {
			btn[i].setLocation(getWidth() - (35 * i) - btn[0].getWidth(), 0);
		}
		inventory_table.setBounds(-10, 40, width+20, height-40);
		repaint();
	}
	
	public class InventorySearchBar extends SearchBar{
		private static final long serialVersionUID = 5457888564606909189L;
		public InventorySearchBar() {
			super("search", InventoryFields);
			setArc(30);
			setBounds(0, 0, 300, 30);
		}
		@Override
		public void onSearch() {
			search_bar.closeFilter();
		}
	}
	
	public interface DefaultButton extends Theme{
		public default void default_appearance(Button btn){
			btn.setBackground(Theme.main_color[2]);
			btn.setForeground(Theme.doc_color[0]);
			btn.getLabel().setForeground(Theme.doc_color[0]);
			btn.setHighlight(Theme.main_color[0]);
			btn.setPressedColor(Theme.main_color[2].darker());
			btn.setArc(30);
		}
	}
	
	public class InventoryPrintButton extends Button implements DefaultButton{
		private static final long serialVersionUID = -4074774662182951765L;
		public InventoryPrintButton() {
			super(PrinterIcon);
			default_appearance(this);
			setEnabled(false);
		}
		@Override
		public void onAction() {
			search_bar.closeFilter();
		}
	}
	
	public class InventorySendToDraftsButton extends Button implements DefaultButton{
		private static final long serialVersionUID = -4074774662182951765L;
		public InventorySendToDraftsButton() {
			super(SendIcon);
			default_appearance(this);
			setEnabled(false);
		}
		@Override
		public void onAction() {
			search_bar.closeFilter();
		}
	}
	
	public class InventoryEditButton extends Button implements DefaultButton{
		private static final long serialVersionUID = -4074774662182951765L;
		public InventoryEditButton() {
			super(PencilIcon);
			default_appearance(this);
			setEnabled(false);
		}
		@Override
		public void onAction() {
			onEditItem();
		}
		public void onEditItem() {
			search_bar.closeFilter();				
			ABM_Pharma.getWindow().getStacksPanel().pushPanel(new SubPanelEditProduct());
		}
		
		private class SubPanelEditProduct extends SubPanelProduct{
			private static final long serialVersionUID = -2025602627570912043L;
			public SubPanelEditProduct() {
				super("Edit Product");
				//displayProduct();
			}
			@Override
			public void onProductOk(Product product) {
				
			}
			@Override
			public void onProductCancel() {
				
			}
		}
	}
	
	public class InventoryDeleteButton extends Button implements DefaultButton{
		private static final long serialVersionUID = -4074774662182951765L;
		public InventoryDeleteButton() {
			super(DeleteIcon);
			default_appearance(this);
			setEnabled(false);
		}
		@Override
		public void onAction() {
			onDeleteItem();
		}
		public void onDeleteItem() {
			search_bar.closeFilter();
		}
	}
	
	public class InventoryAddProductButton extends IconedButton implements DefaultButton{
		private static final long serialVersionUID = -4074774662182951765L;
		public InventoryAddProductButton() {
			super(AddIcon, "Add Product");
			default_appearance(this);
		}
		@Override
		public void onAction() {
			onAddItem();
		}
		public void onAddItem() {
			search_bar.closeFilter();
			ABM_Pharma.getWindow().getStacksPanel().pushPanel(new SubPanelAddProduct(), Theme.shadow);
		}
		
		private class SubPanelAddProduct extends SubPanelProduct{
			private static final long serialVersionUID = 5028819089367136642L;
			public SubPanelAddProduct() {
				super("Add New Product");
				((QtyField)getField(InventoryConstants.qty)).maintainAspectRatio(1);
			}
			@Override
			public void onProductOk(Product product) {
				MySQL_Inventory.insertInventory(product);
				inventory_table.addRow(product);
				ABM_Pharma.getWindow().getStacksPanel().popPanel();
				ABM_Pharma.getWindow().getDisplayPanel().floatMessage("+ " + product.getDescription() + " " + product.getBrand() + ".");
			}
			@Override
			public void onProductCancel() {
				ABM_Pharma.getWindow().getStacksPanel().popPanel();
			}
			
		}
	}
	
	public class InventoryTable extends TableInventory{
		private static final long serialVersionUID = 1053567446126133371L;
		@Override
		public void onSelectTable(int[] n) {
			boolean enable = n.length > 0;
			getButton(item_delete_btn).setEnabled(enable);
			getButton(item_edit_btn).setEnabled(enable);
			getButton(item_send_to_drafts_btn).setEnabled(enable);
		}
		@Override
		public void onPointTable(int n) {
			search_bar.closeFilter();
		}
	}
	
}
