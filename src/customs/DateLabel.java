package customs;

import java.awt.Color;
import java.awt.Graphics2D;
import java.time.LocalDate;

import javax.swing.JLabel;

import gui.IconedButton;
import misc.interfaces.Drawable;
import misc.interfaces.Theme;
import misc.objects.Date;

public abstract class DateLabel extends IconedButton{
	private static final long serialVersionUID = -6144913342611237979L;
	private Status status;
	
	public enum Status{		
		Good(Color.green.darker()),
		Warning(Color.yellow.darker()),
		Bad(Color.red.darker()), 
		Expired(Color.darkGray.darker()),
		NEUTRAL(Theme.main_color[3]),
		UNSET(Theme.main_color[3]);
		
		private final Color color;
		Status(Color color){
			this.color = color;
		}
		public Color getColor() {
			return color;
		}
	};
	
	public DateLabel(String date) {
		super(new Drawable() {
			@Override
			public void draw(Graphics2D g2d, int x, int y, int w, int h) {
				g2d.fillOval(x, y, w, h);
			}
		}, date);
				
		setArc(14);
		getLabel().setHorizontalAlignment(JLabel.CENTER);
		
		checkDate();
	}
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y + (height/2) - 8, width-10, 16);
	}
	public void setStatus(Status status) {
		this.status = status;
		
		setForeground(status.getColor());
		setBackground(Theme.opacity(getForeground(), 0.20f));
		setHighlight(getForeground());
		
		getLabel().setForeground(getForeground());
	}
	public Status getStatus() {
		return status;
	}
	public void checkDate() {		
		if(getLabel().getText().equals("yyyy-mm-dd")) {
			setStatus(Status.UNSET);
		}
		else {
			Date date = new Date();
			Date expiry = new Date(getLabel().getText());

			if(bestBefore(date, expiry, 9)) {
				setStatus(Status.Good);
			}
			else if(bestBefore(date, expiry, 4)) {
				setStatus(Status.Warning);
			}
			else if(bestBefore(date, expiry, 0)){
				setStatus(Status.Bad);
			}
			else {
				setStatus(Status.Expired);
			}
		}
		onCheckDate(status);
	}
	public void setDate(Date date) {
		getLabel().setText(date.toString());
		checkDate();
	}
	public Date getDate() {
		return new Date(getLabel().getText());
	}
	
	public static boolean bestBefore(Date date, Date expiry, int month_margin) {		
		LocalDate date_add = LocalDate.of(date.getYear(), date.getMonth(), date.getDay());
		LocalDate date_exp = LocalDate.of(expiry.getYear(), expiry.getMonth(), expiry.getDay());
		return date_add.compareTo(date_exp.minusMonths(month_margin)) <= 0;
	}
	
	public abstract void onCheckDate(Status status);
}
