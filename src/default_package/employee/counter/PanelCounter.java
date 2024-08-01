package default_package.employee.counter;

import java.awt.Graphics;

import customs.SummationBar;
import gui.Button;
import gui.Panel;
import gui.SearchBar;
import misc.interfaces.Drawable;
import misc.interfaces.TableConstants.Products;
import misc.objects.Decimal;
import misc.interfaces.Theme;
import misc.interfaces.UICustoms;

public class PanelCounter extends Panel implements Theme, UICustoms{
	private static final long serialVersionUID = 1L;
	private SearchBar search_bar;
	private TableCounterInventory table1;
	private TableCustomersCart table2;
	private SummationBarCounter summation_bar;
	
	public PanelCounter(){
		setArc(10);
		setBackground(doc_color[0]);
		
		search_bar = new SearchBar("search", Products.ProductFields) {
			private static final long serialVersionUID = 5457888564606909189L;
			@Override
			public void onSearch() {
				
			}
		};
		search_bar.setArc(30);
		search_bar.setBounds(10, 10, 400, 30);
		add(search_bar);
		
		table1 = new TableCounterInventory() {
			private static final long serialVersionUID = -8294324208972422399L;
			@Override
			public void onSelectTable(int[] n) {
				// TODO Auto-generated method stub
			}
		};
		table1.displayInventoryProducts();
		add(table1);		
		
		table2 = new TableCustomersCart() {
			private static final long serialVersionUID = 8529903027125842061L;
			@Override
			public void onSelectTable(int[] n) {
				// TODO Auto-generated method stub
			}
		};
		add(table2);
		
		summation_bar = new SummationBarCounter(new Decimal(), new Decimal());
		add(summation_bar);
	}
	@Override
	public void paint(Graphics g) {
		g.setColor(getBackground());
		Drawable.fillRoundRect(g, 0, 0, getWidth(), getHeight(), getArc(), getArc());
		
		super.paint(g);
	}
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		table1.setBounds(0, 50, width, (height/2) - 50);
		table2.setBounds(0, (height/2), width, (height/2) - 50);
		summation_bar.setBounds(10, table2.getY() + table2.getHeight(), width - 20, 30);
	}
	private class SummationBarCounter extends SummationBar{
		private static final long serialVersionUID = -7505975260318730558L;
		private Button check_out_btn;

		public SummationBarCounter(Decimal dec1, Decimal dec2) {
			super("Dsicount", dec1, "Total Amount", dec2);
			
			check_out_btn = new Button("Check Out") {
				private static final long serialVersionUID = -1660422433371212342L;
				@Override
				public void onAction() {
					// TODO Auto-generated method stub
				}
			};
			custom_button_appearance2(check_out_btn);
			check_out_btn.setSize(100, 20);
			check_out_btn.setFont(h2);
			check_out_btn.setArc(20);
			add(check_out_btn);
		}
	}
}
