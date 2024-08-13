package default_package.employee;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import database.mysql.MySQL_Counter;
import default_package.ABM_Pharma;
import default_package.employee.counter.PanelCounter;
import gui.Button;
import gui.Panel;
import misc.enums.CounterState;
import misc.interfaces.Theme;
import misc.interfaces.UICustoms;
import misc.objects.Counter;


public class PanelEmployee extends Panel implements Theme, UICustoms{
	private static final long serialVersionUID = -6374468091255785095L;
	private final Image img;
	private Panel panel,panel_counter;
	private Button counterNumber;

	public PanelEmployee(Counter counter) {
		img = Toolkit.getDefaultToolkit().getImage("res/ABM LOGO 2.png");
		counterNumber = new CounterNumberButton(counter.getCounterNo());
		add(counterNumber);
		
		panel = new Panel() {
			private static final long serialVersionUID = -8417089788298739096L;
			@Override
			public void paint(Graphics g) {
				g.setColor(doc_color[0]);
				g.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
				super.paint(g);
			}
		};
		panel_counter = new PanelCounter(counter);
		panel.add(panel_counter);
		add(panel);

		ABM_Pharma.getWindow().setTitle("ABM System Pharma - Counter #" + counter.getCounterNo());
		ABM_Pharma.getWindow().addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				MySQL_Counter.updateCounterState(counter.getCounterNo(), CounterState.OPEN);
			}
		});
	}
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(10, 10, width-20, height-20);
		panel.setBounds(10, 60, getWidth()-20, getHeight()-60);
		panel_counter.setBounds(10, 10, panel.getWidth()-20, panel.getHeight()-20);
		counterNumber.setLocation(width - counterNumber.getWidth() - 30, 10);
	}
	private Graphics2D g2d;
	@Override
	public void paint(Graphics g) {
		g2d = (Graphics2D)g;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		g2d.setColor(doc_color[0]);
		g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
		
		g2d.drawImage(img, 10, 10, this);
		
		g2d.setColor(gray_shade[0]);
		g2d.fillRoundRect(0, 50, getWidth(), getHeight()-50, 10, 10);
		
		super.paint(g2d);
	}

	private class CounterNumberButton extends Button{
		private static final long serialVersionUID = 755423378535795822L;
		
		CounterNumberButton(int counter_no){
			super(counter_no + "");
			setSize(30,30);
			setArc(30);
			custom_button_appearance3(this);
		}
		@Override
		public void onAction() {
			//TODO
		}
	}
}