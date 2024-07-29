package gui;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.function.Consumer;

import javax.swing.JComponent;
import javax.swing.JLabel;

import misc.interfaces.Theme;

public abstract class ListPanel extends Panel{
	private static final long serialVersionUID = 1L;
	private int item_h, item_w, r, hovered, clip_x, clip_y, clip_w, clip_h;
	private VerticalScrollBar v_scroll_bar;
	private ArrayList<JComponent> list;
	private Graphics2D g2d;
	private Color highlight_background,highlight_foreground;

	public ListPanel() {
		setLayout(null);
		setOpaque(false);
		
		setBackground(Theme.doc_color[0]);
		setForeground(Theme.opacity(Theme.gray_shade[0], 0.50f));
		setHighlightBackground(Theme.gray_shade[0]);
		setHighlightForeground(Theme.gray_shade[0]);
		
		setArc(10);
		setItemHeight(20);
		setMargine(10);
		
		list = new ArrayList<JComponent>();
		
		v_scroll_bar = new VerticalScrollBar() {
			private static final long serialVersionUID = -8856133957642873324L;
			@Override
			public void onScrollBarSlide(float y) {
				//TODO
			}
			@Override
			public int providedViewLength() {
				return (getItemHeight() * list.size());
			}
		};
		add(v_scroll_bar);
		
		addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				v_scroll_bar.onScroll(e.getWheelRotation());
				repaint();
			}
		});

		hovered = -1;
	}
	@Override
	public void paint(Graphics g) {
		g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setColor(getBackground());
		g2d.fillRoundRect(0, 0, getWidth(), getHeight(), getArc(), getArc());
		
		g2d.setColor(getForeground());
		g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, getArc(), getArc());
		
		g2d.setClip(clip_x, clip_y, clip_w, clip_h);
		onHighLightBackground(g2d, clip_x, clip_y + (int)((getItemHeight() * hovered) - v_scroll_bar.getScrollY()), getItemWidth(), getItemHeight());
		super.paint(g2d);
		onHighLightForeground(g2d, clip_x, clip_y + (int)((getItemHeight() * hovered) - v_scroll_bar.getScrollY()), getItemWidth(), getItemHeight());
		g2d.setClip(0, 0, getWidth(), getHeight());
		
		calculateItemBounds();
	}
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		calculateClippings();
		calculateItemWidth();
		calculateItemBounds();
		v_scroll_bar.setBounds(getWidth() - 5 - getMargine(), getMargine(), 5, clip_h);
	}
	public void setItemHeight(int item_height) {
		this.item_h = item_height;
	}
	public ArrayList<JComponent> getItemList(){
		return list;
	}
	public JComponent getItem(int n) {
		return list.get(n);
	}
	public int getItemHeight() {
		return item_h;
	}
	public int getItemWidth() {
		return item_w;
	}
	public void setHighlightBackground(Color highlight_background) {
		this.highlight_background = highlight_background;
	}
	public Color getHighlightBackground() {
		return highlight_background;
	}
	public void setHighlightForeground(Color highlight_foreground) {
		this.highlight_foreground = highlight_foreground;
	}
	public Color getHighlightForeground() {
		return highlight_foreground;
	}
	public int getItemCount() {
		return list.size();
	}
	public VerticalScrollBar getVScrollBar() {
		return v_scroll_bar;
	}
	public void addItem(String str) {
		addItem(new DefaultItem(str));
	}
	public JComponent addItem(JComponent component) {
		list.add(component);
		prepareComponent(component);
		
		return component;
	}
	public JComponent addItemAt(JComponent component, int n) {
		list.add(n, component);
		prepareComponent(component);
		
		return component;
	}
	public void removeItem(JComponent component) {
		remove(component);
		list.remove(component);
		revalidate();
		repaint();
	}
	public void removeItemAt(int n) {
		remove(list.get(n));
		list.remove(n);
		revalidate();
		repaint();
	}
	public void removeAllItems() {
		while(list.size() > 0) {
			removeItemAt(0);
		}
	}
	public void onHighLightBackground(Graphics2D g2d, int x, int y, int w, int h) {
		if(hovered != -1) {
			g2d.setColor(getHighlightBackground());
			g2d.fillRect( x, y, w, h);
		}
	}
	public void onHighLightForeground(Graphics2D g2d, int x, int y, int w, int h) {
		if(hovered != -1) {
			g2d.setColor(getHighlightForeground());
			g2d.drawRect( x, y, w, h);
		}
	}
	public void onPointItem(int n) {}
	
	public abstract void onSelectItem(int n);
	
	public class DefaultItem extends JLabel{
		private static final long serialVersionUID = -5588515571384403736L;
		public DefaultItem(String str) {
			super(str);
			setOpaque(false);
			setFont(Theme.h1);
			setForeground(Theme.doc_color[1]);
		}
	}

	private final void calculateClippings() {
		clip_x = getMargine();
		clip_y = getMargine();
		clip_w = getWidth() - (getMargine()*2);
		clip_h = getHeight() - (getMargine()*2);
	}
	private final void calculateItemWidth() {
		item_w = clip_w - v_scroll_bar.getWidth() - getMargine();
	}
	private final void calculateItemBounds() {
		r=0;
		list.forEach(new Consumer<JComponent>() {
			@Override
			public void accept(JComponent comp) {
				comp.setBounds(clip_x, clip_y  + (int)((getItemHeight() * r) - v_scroll_bar.getScrollY()), getItemWidth(), getItemHeight());
				r++;
			}
		});
	}
	private final void prepareComponent(JComponent component) {
		component.addMouseListener(new MouseAdapter() {
			final int n=list.indexOf(component);
			@Override
			public void mouseClicked(MouseEvent e) {
				onSelectItem(n);
				repaint();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				hovered = n;
				onPointItem(n);
				repaint();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				hovered = -1;
				repaint();
			}
		});
		
		add(list.get(list.indexOf(component)));
		
		revalidate();
		repaint();
	}
	
}
