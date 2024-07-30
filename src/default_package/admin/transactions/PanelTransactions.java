package default_package.admin.transactions;

import java.awt.Graphics;

import javax.swing.JLabel;

import default_package.admin.inventory.TableInventory;
import gui.Button;
import gui.IconedButton;
import gui.Panel;
import gui.SearchBar;
import gui.TextField;
import misc.interfaces.Icons;
import misc.interfaces.UICustoms;

public class PanelTransactions extends Panel implements Icons, UICustoms{
	private static final long serialVersionUID = 1413466941505596054L;
	private Button print_customers_btn, print_purchases_btn, return_product_btn;
	private SearchBar search_bar1, search_bar2;
	private TableCustomerTransactions table1;
	private TableInventory table2;
	private SummationBar summation_bar1, summation_bar2;

	public PanelTransactions() {
		setBackground(doc_color[0]);
		
		table1 = new TableCustomerTransactions() {
			private static final long serialVersionUID = -8294324208972422399L;
			@Override
			public void onSelectTable(int[] n) {
				// TODO Auto-generated method stub
			}
		};
		add(table1);
		
		table2 = new TableInventory() {
			private static final long serialVersionUID = 8529903027125842061L;
			@Override
			public void onSelectTable(int[] n) {
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
		print_purchases_btn = createPrintPurchasesButton();
		return_product_btn  = createReturnProductButton();
		
		add(print_customers_btn);
		add(print_purchases_btn);
		add(return_product_btn);

		summation_bar1 = new SummationBar(0, 0);
		summation_bar2 = new SummationBar(0, 0);
		add(summation_bar1);
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

		summation_bar1.setBounds(0, table1.getY() + table1.getHeight(), width - 15, 30);
		summation_bar2.setBounds(0, table2.getY() + table2.getHeight(), width - 15, 30);
	}
	private final Button createPrintCustomersButton() {
		return new Button(PrinterIcon) {
			private static final long serialVersionUID = 7746895474156705643L;
			{
				custom_button_appearance(this);
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
				custom_button_appearance(this);
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
				custom_button_appearance(this);
				setSize(130, 30);
			}
			@Override
			public void onAction() {
				// TODO Auto-generated method stub
			}
		};
	}
	
	private class SummationBar extends Panel{
		private static final long serialVersionUID = -7505975260318730558L;
		private static final int gap=20;
		private TextField totalProfit_field, totalCostField;
		private JLabel label1, label2;
		
		public SummationBar(int totalCost, int totalProfit) {
			setBackground(main_color[2]);
			setArc(5);

			totalProfit_field = new TextField(totalCost + "");
			totalProfit_field.setArc(20);
			totalProfit_field.setSize(150, 20);
			add(totalProfit_field);
			
			label1 = new JLabel("Total Profit");
			label1.setHorizontalAlignment(JLabel.RIGHT);
			label1.setSize(100, 20);
			label1.setFont(h2);
			label1.setForeground(doc_color[0]);
			add(label1);
			
			totalCostField = new TextField(totalProfit + "");
			totalCostField.setArc(20);
			totalCostField.setSize(150, 20);
			add(totalCostField);
			
			label2 = new JLabel("Total Cost");
			label2.setHorizontalAlignment(JLabel.RIGHT);
			label2.setSize(100, 20);
			label2.setFont(h2);
			label2.setForeground(doc_color[0]);
			add(label2);
		}
		@Override
		public void setBounds(int x, int y, int width, int height) {
			super.setBounds(x, y, width, height);
			totalProfit_field.setLocation(width - gap - totalProfit_field.getWidth(), 5);
			label1.setLocation(totalProfit_field.getX() - gap - label1.getWidth(), 5);
			totalCostField.setLocation(label1.getX() - gap - totalCostField.getWidth(), 5);
			label2.setLocation(totalCostField.getX() - gap - label2.getWidth(), 5);
		}
		@Override
		public void paint(Graphics g) {
			g.setColor(getBackground());
			g.fillRoundRect(0, 0, getWidth(), getHeight(), getArc(), getArc());
			
			super.paint(g);
		}
	}
}
