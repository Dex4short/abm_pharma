package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import misc.interfaces.Theme;

public abstract class VerticalScrollBar extends Panel{
	private static final long serialVersionUID = -7816050615066695585L;
	private float bar_y,my,scroll_y,view_length, barLength, speed;
	private Graphics2D g2d;

	public VerticalScrollBar() {
		setOpaque(false);
		setBackground(Theme.gray_shade[0]);
		setForeground(Theme.main_color[2]);
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				onPress(e.getY());
				repaint();
			}
		});
		
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				onDrag(e.getY());
				repaint();
			}
		});
		
		addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				onScroll(e.getWheelRotation());
				repaint();
			}
		});
		
		setSpeed(5);
	}
	@Override
	public void paint(Graphics g) {
		g2d = (Graphics2D)g;
		
		g2d.setColor(getBackground());
		g2d.fillRoundRect(0, 0, getWidth(), getHeight(), getWidth(), getWidth());
		
		g2d.setColor(getForeground());
		g2d.fillRoundRect(0, (int)bar_y, getWidth(), (int)barLength, getWidth(), getWidth());
	}
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		calculateViewLength();
		calculateBarLength();
		repaint();
	}
	public void setScrollY(float percent) {
		calculateScrollY((getHeight()-barLength) * percent);
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public float getScrollY() {
		return scroll_y;
	}
	public float getSpeed() {
		return speed;
	}
	public void onPress(int mouse_y) {
		if(mouse_y>bar_y && mouse_y<(bar_y+barLength)) {
			my = mouse_y - bar_y;
		}
	}
	public void onDrag(int mouse_y) {
		bar_y = mouse_y - my;
		calculateScrollY(mouse_y - my);
	}
	public void onScroll(int mouseWheelRotation) {
		bar_y += mouseWheelRotation * getSpeed();
		calculateScrollY(bar_y);
	}
	public abstract void onScrollBarSlide(float y);
	public abstract int providedViewLength();

	private final void calculateViewLength() {
		if(providedViewLength() <= getHeight()) {
			view_length = getHeight();
		}
		else {
			view_length = providedViewLength();
		}
	}
	private final void calculateBarLength() {
		barLength = (getHeight()/view_length) * getHeight();
	}
	private final void calculateScrollY(float y) {
		if(y < 0) {
			bar_y = 0;
		}
		else if(y > (getHeight() - barLength)) {
			bar_y =  getHeight() - barLength;
		}
		else {
			bar_y = y;
		}
		
		scroll_y = (view_length / getHeight()) * bar_y;
		onScrollBarSlide(scroll_y);		
	}
}
