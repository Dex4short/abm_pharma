package default_package.login;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;

import database.mysql.MySQL_Counter;
import default_package.ABM_Pharma;
import gui.Button;
import gui.Link;
import gui.Panel;
import misc.enums.CounterState;
import misc.interfaces.UICustoms;
import misc.objects.Counter;
import misc.objects.Time;

public abstract class PanelCounterSelection extends Panel implements UICustoms{
	private static final long serialVersionUID = 3913243872663372998L;
	private static final int btn_size=30, gap=10;
	private Graphics2D g2d;
	private Button btn[];
	private Link back_btn;
	private int grid_w,grid_h,grid=4;

	public PanelCounterSelection() {
		Counter counter[] = MySQL_Counter.selectAllCounters();
		
		btn = new Button[counter.length-1];
		grid = (int)Math.ceil(Math.sqrt(btn.length));
		grid_w = ((btn_size * grid) + (gap * (grid-1)));
		grid_h = ((btn_size * grid) + (gap * (grid-1)));
		
		for(int i=0; i<btn.length; i++) {
			btn[i] = new CounterButton(counter[i+1]);
			add(btn[i]);
			revalidate();
		}
		
		back_btn = new Link("back") {
			private static final long serialVersionUID = 7942352957397437620L;
			@Override
			public void directory() {
				onReturn();
			}
		};
		back_btn.setForeground(doc_color[0]);
		back_btn.getLabel().setHorizontalAlignment(JLabel.LEFT);
		add(back_btn);
	}
	private int c, r, n, btn_x, btn_y, x_translate, y_translate;
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);

		x_translate = x + (width/2) + (grid_w/2);
		y_translate = y + (height/2) - (grid_h/2);
		
		for(r=0; r<grid; r++) {
			for(c=0; c<grid; c++) {
				n = (r*(grid)) + c;
				btn_x = x_translate + (c * (btn_size + gap));
				btn_y = y_translate + (r * (btn_size + gap));
				
				if(n < btn.length && btn[n]!=null) {
					btn[n].setBounds(btn_x , btn_y, btn_size, btn_size);
				}
			}
		}
		
		back_btn.setBounds((getWidth()/2) - (int)(grid_w*1.5), (getHeight()/2), 100, btn_size);
	}
	@Override
	public void paint(Graphics g) {
		g2d = (Graphics2D)g;
		
		g2d.setColor(doc_color[0]);
		g2d.setFont(h2);
		g2d.drawString("Select Counter Number:", (getWidth()/2) - (int)(grid_w*1.5), (getHeight()/2));
		
		g2d.drawLine((getWidth()/2), y_translate - gap, (getWidth()/2), y_translate + grid_h + gap);
		
		g2d.setColor(shadow);
		for(r=0; r<grid; r++) {
			for(c=0; c<grid; c++) {
				n = (r*(grid)) + c;
				btn_x = x_translate + (c * (btn_size + gap)) - 1;
				btn_y = y_translate + (r * (btn_size + gap)) - 1;
				
				if(n < btn.length) {
					g2d.fillOval(
							btn_x,
							btn_y,
							btn_size + 2,
							btn_size + 2
					);
				}
			}
		}
		
		super.paint(g);
	}

	public abstract void onCounterSelect(Counter counter);
	public abstract void onReturn();
	
	public class CounterButton extends Button{
		private static final long serialVersionUID = 5334961866112375232L;
		private Counter counter;
		private Timer timer;
		
		public CounterButton(Counter counter) {
			super(counter.getCounterNo() + "");
			custom_button_appearance1(this);
			
			this.counter = counter;
			
			checkCounterState();
			beginCounterUpdater();
		}
		@Override
		public void onAction() {
			int counter_no = counter.getCounterNo();
			CounterState counter_state = MySQL_Counter.selectCounterState(counter_no);
			
			if(counter_state == CounterState.OPEN) {
				timer.cancel();
				
				counter.setActiveTime(new Time());
				counter.setCounterState(CounterState.CLOSE);
				
				MySQL_Counter.updateCounter(counter);
				onCounterSelect(counter);
			}
			else {
				ABM_Pharma.getWindow().getDisplayPanel().floatMessage("Counter is no longer open.");
			}
		}
		public void checkCounterState() {
			if(counter.getCounterState() == CounterState.OPEN) {
				setEnabled(true);
			}
			else {
				setEnabled(false);
			}
		}
		
		private final void beginCounterUpdater() {
			timer = new Timer();
			timer.scheduleAtFixedRate(new TimerTask() {
				private Time active_time, current_time;
				private LocalTime a,b;
				private int minutes;
				
				@Override
				public void run() {
					if(!isEnabled()) {
						active_time = MySQL_Counter.selectCounterActiveTime(counter.getCounterNo());
						current_time = new Time();
						
						a = active_time.toSQLTime().toLocalTime();
						b = current_time.toSQLTime().toLocalTime();
						
						minutes = (int)Math.abs(Duration.between(a,b).toMinutes());
						System.out.println(minutes);
						
						if(minutes >= 2) {
							MySQL_Counter.updateCounterState(counter.getCounterNo(), CounterState.OPEN);
							counter.setCounterState(CounterState.OPEN);
							checkCounterState();
						}
					}
					else {
						if(MySQL_Counter.selectCounterState(counter.getCounterNo()) == CounterState.CLOSE) {
							counter.setCounterState(CounterState.CLOSE);
							checkCounterState();
						}
					}
				}
			}, 0, 120000);
		}
	}
}
