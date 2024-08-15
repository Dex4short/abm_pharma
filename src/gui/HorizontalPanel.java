package gui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import misc.interfaces.Theme;

public class HorizontalPanel extends Panel{
	private static final long serialVersionUID = -6377510957898754752L;
	private Graphics2D g2d;

	public HorizontalPanel() {
		setArc(5);
		setMargine(5);
		setIndent(0);
		
		setBackground(Theme.doc_color[0]);
		setForeground(Theme.gray_shade[0]);
	}
	@Override
	public void paint(Graphics g) {
		g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setColor(getBackground());
		g2d.fillRoundRect(0, 0, getWidth(), getHeight(), getArc(), getArc());
		
		g2d.setColor(getForeground());
		g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, getArc(), getArc());
		
		super.paint(g2d);
	}
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		horizontalLayout(getComponents(), getMargine()+getIndent(), getMargine(), width-(getMargine()*2)-getIndent(), height-(getMargine()*2));
	}
	@Override
	public Component add(Component comp) {
		return super.add(comp);
	}
	public void addComponents(Component components[]) {
		for(Component comp: components) {
			add(comp);
		}
	}
	public static void horizontalLayout(Component components[], int x, int y, int w, int h) {
		int
		n = 0,
		comp_w = (w/components.length);
		for(Component comp: components) {
			comp.setBounds(x + (comp_w * n), y, comp_w, h);
			n++;
		}
	}
}
