package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;

import misc.interfaces.Theme;
import misc.objects.Date;

public abstract class DatePicker extends ActionPanel{
	private static final long serialVersionUID = -322731435061702702L;
	private int last_panel;
	private Button btn_year, btn_month, btn_day;
	private Panel content_panel[];
	private Date date;

	public DatePicker() {
		super("Date Picker");
		
		date = new Date();
		
		int
		yyyy = date.getYear(),
		mm = date.getMonth(),
		dd = date.getDay();
		
		construct_date_picker(yyyy + "", mm + "", dd + "");
	}
	public DatePicker(String date) {
		super("Date Picker");
		
		int yyyy, mm, dd;
		
		try {
			yyyy = Date.extractYear(date);
		}catch (Exception e) {
			yyyy = Date.getCurrentYear();
		}
		
		try {
			mm = Date.extractMonth(date);
		} catch (Exception e) {
			mm = Date.getCurrentMonth();
		}
		
		try {
			dd = Date.extractDay(date);
		} catch (Exception e) {
			dd = Date.getCurrentDay();
		}
		
		this.date = new Date(yyyy, mm, dd);
		
		construct_date_picker(yyyy+"", mm+"", dd+"");
	}
	private final void construct_date_picker(String year, String month, String day) {	
		btn_year = new Button(year) {
			private static final long serialVersionUID = 5262142670923800645L;
			@Override
			public void onAction() {
				openPanel(0);
			}
		};
		getContentPane().add(btn_year);
		
		btn_month = new Button(month) {
			private static final long serialVersionUID = 5262142670923800645L;
			@Override
			public void onAction() {
				openPanel(1);
			}
		};
		getContentPane().add(btn_month);
		
		btn_day= new Button(day) {
			private static final long serialVersionUID = 5262142670923800645L;
			@Override
			public void onAction() {
				openPanel(2);
			}
		};
		getContentPane().add(btn_day);
		
		content_panel = new Panel[3];
		content_panel[0] = new YearPanel() {
			private static final long serialVersionUID = -903881822796808752L;
			@Override
			public void onPickYear(String year, int yyyy) {
				onPickCalendarYear(year, yyyy);
			}
		};
		content_panel[1] = new MonthPanel() {
			private static final long serialVersionUID = 1L;
			@Override
			public void onPickMonth(String month, int mm) {
				onPickCalendarMonth(month, mm);
			}
		};
		content_panel[2] = new DayPanel() {
			private static final long serialVersionUID = 1L;
			@Override
			public void onPickDay(String day, int dd) {
				onPickCalendarDay(day, dd);
			}
		};
		
		last_panel = -1;
		openPanel(0);
		
	}
	@Override
	public void onResizeContentPane(int w, int h) {
		final int btn_h=30, btn_w=60;
		
		btn_year.setBounds((w/2) - (btn_w/2) - getMargine() - btn_w, 0, btn_w, btn_h);
		btn_month.setBounds((w/2) - (btn_w/2), 0, btn_w, btn_h);
		btn_day.setBounds((w/2) + (btn_w/2) + getMargine(), 0, btn_w, btn_h);
		content_panel[last_panel].setBounds(0, btn_h + getMargine(), w, h - btn_h - getMargine());
	}
	@Override
	public void onOk() {
		try {
			Integer.parseInt(btn_year.getLabel().getText());
			Integer.parseInt(btn_month.getLabel().getText());
			Integer.parseInt(btn_day.getLabel().getText());
			
			onPickCalendarDate(date.getYear(), date.getMonthName(), date.getMonth(), date.getDayName(), date.getDay());
		} catch (Exception e) {
			onInvalidPickDate();
		}
	}
	@Override
	public void onCancel() {
		onCloseDatePicker();
	}
	public Date getCalendar(){
		return date;
	}
	public Date getDate() {
		return date;
	}
	public void openPanel(int next_panel) {		
		if(last_panel != -1) {
			getContentPane().remove(content_panel[last_panel]);
		}
		getContentPane().add(content_panel[next_panel]);
		last_panel = next_panel;
		
		revalidate();
		repaint();

		content_panel[next_panel].revalidate();
		content_panel[next_panel].repaint();
	}
	public void onPickCalendarYear(String year, int yyyy) {
		date.setYear(yyyy);
		openPanel(1);
	}
	public void onPickCalendarMonth(String month, int mm) {
		date.setMonth(mm);
		openPanel(2);
	}
	public void onPickCalendarDay(String day, int dd) {
		date.setDay(dd);
	}
	public void onInvalidPickDate() {
		Toolkit.getDefaultToolkit().beep();
	}
	public abstract void onPickCalendarDate(int yyyy, String month, int mm, String day, int dd);
	public abstract void onCloseDatePicker();
	
	public abstract class YearPanel extends ListPanel{
		private static final long serialVersionUID = 6704820730372838991L;
		private boolean displayed=false;
		private int year_row;
		private Link link;

		public YearPanel() {
			setForeground(new Color(0,0,0,0));
			setItemHeight(30);
			getVScrollBar().setSpeed(1);
			
			int year=2000;
			while(year<2100) {
				addRowYears(year, year+3);
				year+=4;
			}
		}
		private final void addRowYears(int starting_year, int end_year) {
			addItem(new HorizontalPanel() {
				private static final long serialVersionUID = 4074194059880003482L;
				{
					setBackground(new Color(0,0,0,0));
					setForeground(getBackground());

					for(int year=starting_year; year<=end_year; year++) {
						add(new Link(year + "") {
							private static final long serialVersionUID = 7981196744490259644L;
							{
								setForeground(Theme.gray_shade[1]);
								setHighlightColor(Theme.doc_color[1]);
								setPressedColor(Theme.doc_color[1]);
								
								int yr;
								try {
									yr = Integer.parseInt(btn_year.getLabel().getText());
								} catch (Exception e) {
									yr = Date.getCurrentYear();
								}
								
								if(getLabel().getText().equals(yr + "")) {
									year_row = getItemCount() + 1;
									
									link = this;
									link.setBackground(Theme.opacity(Theme.gray_shade[0], 0.5f));
									link.setForeground(Theme.doc_color[1]);
								}
							}
							@Override
							public void directory() {
								link.setBackground(new Color(0,0,0,0));
								link.setForeground(Theme.gray_shade[1]);
								link = this;
								
								setBackground(Theme.opacity(Theme.gray_shade[0], 0.5f));
								setForeground(Theme.doc_color[1]);
								
								String year = getLabel().getText();
								btn_year.getLabel().setText(year);
								
								onPickYear(year, Integer.parseInt(year));
							}
						});
					}
				}
			});
		}
		@Override
		public void setBounds(int x, int y, int width, int height) {
			super.setBounds(x, y, width, height);
			if(!displayed) {
				getVScrollBar().setScrollY((float)year_row/((float)getItemCount()-1));
				displayed=true;
			}
		}
		@Override
		public void onSelectItem(int n) {}//unused
		@Override
		public void onHighLightBackground(Graphics2D g2d, int x, int y, int w, int h) {}
		@Override
		public void onHighLightForeground(Graphics2D g2d, int x, int y, int w, int h) {}
		public abstract void onPickYear(String year, int yyyy);
	}
	
	public abstract class MonthPanel extends Panel{
		private static final long serialVersionUID = 7904375214809724919L;
		private static final int rows=4,cols=3;
		private Link link;
		
		public MonthPanel() {
			setLayout(new GridLayout(rows, cols));
			setBorder(BorderFactory.createEmptyBorder( 10, 10, 10, 10));
			
			
			for(int r=0; r<rows; r++) {
				for(int c=0; c<cols; c++) {
					final int mm = (cols * r) + (c+1);
					
					add(new Link(Date.getMonthName(mm)) {
						private static final long serialVersionUID = -171652431288484447L;
						{
							setForeground(Theme.gray_shade[1]);
							setHighlightColor(Theme.doc_color[1]);
							setPressedColor(Theme.doc_color[1]);

							int m;
							try {
								m = Integer.parseInt(btn_month.getLabel().getText());
							} catch (Exception e) {
								m = Date.getCurrentMonth();
							}
							
							if(getLabel().getText().equals(Date.getMonthName(m))) {								
								link = this;
								link.setBackground(Theme.opacity(Theme.gray_shade[0], 0.5f));
								link.setForeground(Theme.doc_color[1]);
							}
						}
						@Override
						public void directory() {
							link.setBackground(new Color(0,0,0,0));
							link.setForeground(Theme.gray_shade[1]);
							link = this;
							
							setBackground(Theme.opacity(Theme.gray_shade[0], 0.5f));
							setForeground(Theme.doc_color[1]);
							
							String month = this.getLabel().getText();
							btn_month.getLabel().setText(mm + "");
							
							((DayPanel)content_panel[2]).updateDays(date.getYear(), mm);
							
							onPickMonth(month, mm);
						}
					});
				}
			}
		}
		public abstract void onPickMonth(String month, int mm);
	}
	
	public abstract class DayPanel extends Panel{
		private static final long serialVersionUID = 7904375214809724919L;
		private final int row,col;
		private Link link;
		
		public DayPanel() {
			row=5;
			col=7;
			setLayout(new GridLayout(row, col));
			setBorder(BorderFactory.createEmptyBorder( 10, 10, 10, 10));
			
			for(int i=0; i<row; i++) {
				for(int ii=0; ii<col; ii++) {
					final int dd = (col * i) + (ii+1);
					
					if(dd>31) {
						break;
					}
					
					add(new Link(dd + "") {
						private static final long serialVersionUID = -171652431288484447L;
						{
							setForeground(Theme.gray_shade[1]);
							setHighlightColor(Theme.doc_color[1]);
							setPressedColor(Theme.doc_color[1]);
							
							int d;
							try {
								d = Integer.parseInt(btn_day.getLabel().getText());
							} catch (Exception  e) {
								d = Date.getCurrentDay();
							}
							
							if(getLabel().getText().equals(d + "")) {
								link = this;
								link.setBackground(Theme.opacity(Theme.gray_shade[0], 0.5f));
								link.setForeground(Theme.doc_color[1]);
							}
						}
						@Override
						public void directory() {
							link.setBackground(new Color(0,0,0,0));
							link.setForeground(Theme.gray_shade[1]);
							link = this;
							
							setBackground(Theme.opacity(Theme.gray_shade[0], 0.5f));
							setForeground(Theme.doc_color[1]);
							
							String day = Date.getDayName(date.getYear(), date.getMonth(), dd);
							btn_day.getLabel().setText(dd + "");
							
							onPickDay(day, dd);
						}
					});
				}
			}
		}
		public void updateDays(int yyyy, int mm) {
			int days = Date.getMonthDays(yyyy, mm);
			
			boolean visible=true;
			for(int d=27; d<31; d++) {
				if(d==days) {
					visible = false;
				}
				getComponent(d).setVisible(visible);
			}
		}
		public abstract void onPickDay(String day, int dd);
	}
}
