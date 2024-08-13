package default_package.admin.transactions;

import customs.SummationBar;
import default_package.admin.inventory.TableInventory;
import gui.Button;
import gui.IconedButton;
import gui.Panel;
import gui.SearchBar;
import misc.interfaces.Icons;
import misc.interfaces.UICustoms;
import misc.objects.Decimal;

public class PanelTransactions extends Panel implements Icons, UICustoms{
	private static final long serialVersionUID = 1413466941505596054L;
	private SearchBar search_bar1, search_bar2;
	private Button print_customers_btn, print_purchases_btn, return_product_btn;
	private TableCustomersTransaction table1;
	private TableCustomersPurchase table2;
	private SummationBar summation_bar1, summation_bar2;

	public PanelTransactions() {
		setBackground(doc_color[0]);
		
		table1 = new TableCustomersTransaction() {
			private static final long serialVersionUID = -8294324208972422399L;
			@Override
			public void onSelectTable(int[] n) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onPointTable(int n) {
				// TODO Auto-generated method stub
			}
		};
		add(table1);
		
		table2 = new TableCustomersPurchase() {
			private static final long serialVersionUID = 8529903027125842061L;
			@Override
			public void onSelectTable(int[] n) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onPointTable(int n) {
				// TODO Auto-generated method stub
			}
		};
		add(table2);
		
		search_bar1 = new SearchBar("search", table1.getColumnLabels()) {
			private static final long serialVersionUID = 5457888564606909189L;
			@Override
			public void onSearch() {
				
			}
		};
		search_bar1.setArc(30);
		search_bar1.setBounds(0, 0, 400, 30);
		add(search_bar1);
		
		search_bar2 = new SearchBar("search", table2.getColumnLabels()) {
			private static final long serialVersionUID = 5457888564606909189L;
			@Override
			public void onSearch() {
				
			}
		};
		search_bar2.setArc(30);
		search_bar2.setSize(400, 30);
		add(search_bar2);
		
		print_customers_btn = createPrintCustomersButton();
		add(print_customers_btn);
		print_purchases_btn = createPrintPurchasesButton();
		add(print_purchases_btn);
		return_product_btn  = createReturnProductButton();
		add(return_product_btn);
		
		summation_bar1 = new SummationBarTransactions(new Decimal(), new Decimal());
		add(summation_bar1);
		summation_bar2 = new SummationBarPurchases(new Decimal(), new Decimal());
		add(summation_bar2);
	}
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		print_customers_btn.setLocation(width-30, 0);
		table1.setBounds(-10, 40, width+20, (height/2) - 70);
		
		return_product_btn.setLocation(width - return_product_btn.getWidth(), (getHeight()/2) + 5);
		print_purchases_btn.setLocation(width - return_product_btn.getWidth() - print_purchases_btn.getWidth() - 5, (getHeight()/2) + 5);
		table2.setBounds(-10, (height/2) + 40, width+20, (height/2) - 70);
		
		search_bar2.setLocation(0, (getHeight()/2) + 5);

		summation_bar1.setBounds(0, table1.getY() + table1.getHeight(), width, 30);
		summation_bar2.setBounds(0, table2.getY() + table2.getHeight(), width, 30);
	}
	private final Button createPrintCustomersButton() {
		return new Button(PrinterIcon) {
			private static final long serialVersionUID = 7746895474156705643L;
			{
				custom_button_appearance1(this);
				setSize(30, 30);
			}
			@Override
			public void onAction() {
				// TODO Auto-generated method stub
			}
		};
	}
	private final Button createPrintPurchasesButton() {
		return new Button(PrinterIcon) {
			private static final long serialVersionUID = 7746895474156705643L;
			{
				custom_button_appearance1(this);
				setSize(30, 30);
			}
			@Override
			public void onAction() {
				// TODO Auto-generated method stub
			}
		};
	}
	private final IconedButton createReturnProductButton() {
		return new IconedButton(ReturnIcon, "Return Product") {
			private static final long serialVersionUID = 7746895474156705643L;
			{
				custom_button_appearance1(this);
				setSize(130, 30);
			}
			@Override
			public void onAction() {
				// TODO Auto-generated method stub
			}
		};
	}
	
	private class SummationBarTransactions extends SummationBar{
		private static final long serialVersionUID = -7505975260318730558L;
		
		public SummationBarTransactions(Decimal dec1, Decimal dec2) {
			super("Total Cost Amount", dec1, "Total Profit", dec2);
			// TODO Auto-generated constructor stub
		}
	}
	
	private class SummationBarPurchases extends SummationBar{
		private static final long serialVersionUID = -7505975260318730558L;
		
		public SummationBarPurchases(Decimal dec1, Decimal dec2) {
			super("Cost Amount", dec1, "Profit", dec2);
			// TODO Auto-generated constructor stub
		}
	}
}
