package default_package.employee.counter;

import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;

import customs.SummationBar;
import database.mysql.MySQL_Counter;
import gui.Button;
import gui.Panel;
import gui.SearchBar;
import misc.interfaces.Drawable;
import misc.interfaces.TableConstants.Products;
import misc.objects.Counter;
import misc.objects.Decimal;
import misc.objects.Order;
import misc.objects.Product;
import misc.interfaces.Theme;
import misc.interfaces.UICustoms;

public class PanelCounter extends Panel implements Theme, UICustoms{
	private static final long serialVersionUID = 1L;
	private Counter counter;
	private SearchBar search_bar;
	private TableCounterInventory table1;
	private TableCustomersCart table2;
	private SummationBarCounter summation_bar;
	private Timer timer;
	
	public PanelCounter(Counter counter){
		setCounter(counter);
		begin_counter_timer();
		
		setArc(10);
		setBackground(doc_color[0]);
		
		search_bar = new SearchBar("search", Products.ProductFields) {
			private static final long serialVersionUID = 5457888564606909189L;
			@Override
			public void onSearch() {
				
			}
		};
		search_bar.setArc(30);
		search_bar.setBounds(0, 0, 400, 30);
		add(search_bar);
		
		table1 = new TableCounterInventory() {
			private static final long serialVersionUID = -8294324208972422399L;
			@Override
			public void onSelectTable(int[] n) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onPointTable(int n) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onAddToCart(Product product) {
				table2.addProductToCart(product);
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
			@Override
			public void onPointTable(int n) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onRemoveOrder(Order returned_order) {
				table1.returnOrderToInventory(returned_order);
			}
			@Override
			public Counter getCounter() {
				return counter;
			}
		};
		table2.openCart();
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
		table1.setBounds(-10, 40, width + 20, (height/2) - 40);
		table2.setBounds(-10, (height/2), width + 20, (height/2) - 30);
		summation_bar.setBounds(-10, table2.getY() + table2.getHeight(), width + 10, 30);
	}
	public Counter getCounter() {
		return counter;
	}
	public void setCounter(Counter counter) {
		this.counter = counter;
	}
	
	private class SummationBarCounter extends SummationBar{
		private static final long serialVersionUID = -7505975260318730558L;
		private Button check_out_btn;

		public SummationBarCounter(Decimal dec1, Decimal dec2) {
			super("Discount", dec1, "Total Amount", dec2);
			
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
	
	private final void begin_counter_timer() {
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				MySQL_Counter.updateCounterActiveTime(counter);
			}
		}, 0, 60000);
	}
}
