package gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

import misc.interfaces.Drawable;
import misc.interfaces.Theme;

public class DisplayPanel extends Panel implements Theme{
	private static final long serialVersionUID = 192006444394364516L;
	private ArrayList<Drawable> array_drawable;
	private Consumer<Drawable> consumer_drawable;
	private Graphics2D g2d;

	public DisplayPanel() {
		array_drawable = new ArrayList<Drawable>();
		
		FloatingMessage floating_message = new FloatingMessage();
		array_drawable.add(floating_message);
		
		consumer_drawable = new Consumer<Drawable>() {
			@Override
			public void accept(Drawable drawable) {
				drawable.draw(g2d, 0, 0, getWidth(), getHeight());
			}
		};
		
		setFont(h1);
	}
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		for(Component comp: getComponents()) {
			comp.setBounds(x, y, width, height);
		}
	}
	@Override
	public void paint(Graphics g) {
		g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		super.paint(g2d);

		array_drawable.forEach(consumer_drawable);
	}
	public void addDrawable(Drawable drawable) {
		array_drawable.add(drawable);
	}
	public void removeDrawable(Drawable drawable) {
		array_drawable.remove(drawable);
	}
	public void getDrawable(Drawable drawable) {
		array_drawable.get(array_drawable.indexOf(drawable));
	}
	public void floatMessage(String message) {
		((FloatingMessage)array_drawable.get(0)).floatMessage(message);
	}
	
	public class FloatingMessage implements Drawable{
		private String message;
		private int message_x, message_y, message_w, message_h, floater_x, floater_y, floater_w, floater_h, floater_distance,floater_length, s, sec;
		private float fl, alpha;
		private Color background, foreground, border_color;
		private boolean float_message;
		private Timer timer;

		public FloatingMessage() {
			setBackground(shadow);
			setForeground(doc_color[0]);
			setBorderColor(doc_color[1]);
			
			setMessage("Hello World");
			setFloaterDistance(20);
			setFloaterLength(20);
			setTimer(5);
		}
		@Override
		public void draw(Graphics2D g2d, int display_x, int display_y, int display_w, int display_h) {
			if(float_message) {				
				message_x = display_x + (display_w/2) - (message_w/2);
				message_y = display_y + display_h - getFloaterDistance() - (floater_h/2) + (message_h/2) + (int)fl;
				message_w = g2d.getFontMetrics().stringWidth(getMessage());
				message_h = g2d.getFontMetrics().getAscent();
				
				floater_x = display_x + (display_w/2) - (floater_w/2);
				floater_y = display_y + display_h - getFloaterDistance() - floater_h + (int)fl;
				floater_w = message_w + (message_h * 3);
				floater_h = message_h * 3;
				
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
				
				g2d.setColor(getBackground());
				g2d.fillRoundRect( floater_x, floater_y, floater_w, floater_h, floater_h, floater_h);

				g2d.setColor(getBorderColor());
				g2d.fillRoundRect( floater_x, floater_y, floater_w, floater_h, floater_h, floater_h);
				
				g2d.setColor(getForeground());
				g2d.drawString(getMessage(), message_x, message_y);
			}
		}
		public void floatMessage(String message, int seconds) {
			setTimer(seconds);
			floatMessage(message);
		}
		public void floatMessage(String message) {
			setMessage(message);
			float_message = true;

			repaint();
			
			fl = floater_length;
			s = (sec + 1) * 100;
			
			if(timer != null) {
				timer.cancel();
			}
			timer = new Timer();
			timer.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					repaint();
					
					if(fl>0) {
						fl-=0.5;
						alpha = (1f/floater_length) * (floater_length-fl);
					}
					
					if(s==0) {
						float_message = false;
						repaint();
						cancel();
					}
					else if(s<=100) {
						alpha -= 0.01f;
					}
					s--;
				}
			} , 0, 10);
			
		}
		public void setTimer(int sec) {
			this.sec = sec;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public void setBackground(Color background) {
			this.background = background;
		}
		public void setForeground(Color foreground) {
			this.foreground = foreground;
		}
		public void setBorderColor(Color border_color) {
			this.border_color = border_color;
		}
		public void setFloaterDistance(int floater_distance) {
			this.floater_distance = floater_distance;
		}
		public void setFloaterLength(int floater_length) {
			this.floater_length = floater_length;
		}
		public String getMessage() {
			return message + " " + (int)(s * 0.01f);
		}
		public Color getBackground() {
			return background;
		}
		public Color getForeground() {
			return foreground;
		}
		public Color getBorderColor() {
			return border_color;
		}
		public int getFloaterDistance(){
			return floater_distance;
		}
		public int getFloaterLength() {
			return floater_length;
		}
	}
}
