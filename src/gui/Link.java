package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import misc.interfaces.Theme;

public abstract class Link extends Panel{
	private static final long serialVersionUID = -6195815654674270671L;
	private JLabel lbl;
	private Color highlight_color, pressed_color;
	
	public Link(String label) {
		setLayout(new GridLayout(1,1));
		setForeground(Theme.main_color[0]);
		setBackground(new Color(0,0,0,0));
		setHighlightColor(Theme.main_color[1]);
		setPressedColor(Theme.main_color[2]);
		setFont(Theme.h1);
		
		lbl = new JLabel(label);
		lbl.setOpaque(false);
		lbl.setForeground(getForeground());
		lbl.setFont(getFont());
		lbl.setHorizontalAlignment(JLabel.CENTER);
		add(lbl);
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				directory();
			}
			@Override
			public void mousePressed(MouseEvent e) {
				lbl.setForeground(getPressedColor());
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				lbl.setForeground(getHighlightColor());
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lbl.setForeground(getHighlightColor());
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lbl.setForeground(getForeground());
			}
		});
		
	}
	@Override
	public void paint(Graphics g) {
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		super.paint(g);
	}
	@Override
	public void setForeground(Color fg) {
		super.setForeground(fg);
		if(lbl != null) {
			lbl.setForeground(fg);
		}
	}
	@Override
	public void setBackground(Color bg) {
		super.setBackground(bg);
		if(lbl != null) {
			lbl.setBackground(bg);
		}
	}
	@Override
	public void setFont(Font font) {
		super.setFont(font);
		if(lbl != null) {
			lbl.setFont(font);
		}
	}
	public void setHighlightColor(Color highlight_color) {
		this.highlight_color = highlight_color;
	}
	public void setPressedColor(Color pressed_color) {
		this.pressed_color = pressed_color;
	}
	public Color getHighlightColor() {
		return highlight_color;
	}
	public Color getPressedColor() {
		return pressed_color;
	}
	public JLabel getLabel() {
		return lbl;
	}
	public abstract void directory();
}
