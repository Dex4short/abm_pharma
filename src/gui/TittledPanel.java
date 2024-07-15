package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;

import misc.interfaces.Theme;

public abstract class TittledPanel extends Panel{
	private static final long serialVersionUID = -5812723597349547472L;
	private JLabel tittle_label;
	private Panel content_pane;
	private Graphics2D g2d;
	
	public TittledPanel(String tittle) {
		setArc(25);
		setMargine(10);
		setBackground(Theme.doc_color[0]);
		setForeground(Theme.gray_shade[0]);
		
		tittle_label = new JLabel(tittle);
		tittle_label.setFont(Theme.h1);
		tittle_label.setForeground(Theme.main_color[2]);
		add(tittle_label);
		
		content_pane = new Panel() {
			private static final long serialVersionUID = -2006049825051789192L;
			@Override
			public void setBounds(int x, int y, int width, int height) {
				super.setBounds(x, y, width, height);
				onResizeContentPane(width, height);
				repaint();
			}
		};
		add(content_pane);
	}
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);		
		onResizeTitledPanel(width, height);
	}
	@Override
	public void paint(Graphics g) {
		g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setColor(getBackground());
		g2d.fillRoundRect(0, 0, getWidth(), getHeight(), getArc(), getArc());
		
		g2d.setColor(getForeground());
		g2d.drawRoundRect(0, 0, getWidth(), getHeight(), getArc(), getArc());
		
		g2d.drawLine(0, 30, getWidth(), 30);
		
		super.paint(g2d);
	}
	@Override
	public void setVisible(boolean aFlag) {
		super.setVisible(aFlag);
		if(aFlag) {
			onResizeTitledPanel(getWidth(), getHeight());
		}
	}
	public Panel getContentPane() {
		return content_pane;
	}
	public JLabel getTittleLabel() {
		return tittle_label;
	}
	public void onResizeTitledPanel(int w, int h) {
		tittle_label.setBounds(getMargine(), 0, w - (getMargine()*2), 30);
		tittle_label.repaint();
		content_pane.setBounds(getMargine(), tittle_label.getHeight() + getMargine(), tittle_label.getWidth(), h-tittle_label.getHeight()-(getMargine()*2));
		content_pane.repaint();

		repaint();
	};
	public abstract void onResizeContentPane(int w, int h);
}
