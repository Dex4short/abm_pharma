package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;

public abstract class StacksPanel extends Panel{
	private static final long serialVersionUID = 8182176255182230327L;
	private Color layer_color;
	private int c;
	
	public StacksPanel() {
		setLayerColor(new Color(0,0,0,0));
	}
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		for(c=0; c<getComponentCount(); c++) {
			getComponent(c).setBounds(x, y, width, height);
		}
	}
	public final void pushPanel(Panel panel, Color color) {
		setLayerColor(color);
		pushPanel(panel);
	}
	public final void pushPanel(Panel panel) {
		onPushPanel(panel);
		add(new Panel() {
			private static final long serialVersionUID = 756774899253711353L;
			{
				add(panel);
				addMouseListener(new MouseAdapter() {
					//disable clicking through
				});
			}
			@Override
			public void paint(Graphics g) {
				g.setColor(getLayerColor());
				g.fillRect(0, 0, getWidth(), getHeight());
				super.paint(g);
			}
			@Override
			public void setBounds(int x, int y, int width, int height) {
				super.setBounds(x, y, width, height);
				panel.setBounds(x, y, width, height);
			}
		}, 0);
		
		revalidate();
		repaint();
	}
	public final void popPanel() {
		onPopPanel((Panel)getComponent(0));
		remove(0);
		
		revalidate();
		repaint();
	}
	public Color getLayerColor() {
		return layer_color;
	}
	public void setLayerColor(Color layer_color) {
		this.layer_color = layer_color;
	}
	public abstract void onPushPanel(Panel panel);
	public abstract void onPopPanel(Panel panel);
}
