package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import misc.interfaces.Theme;

public abstract class Button extends Panel implements Theme{
	private static final long serialVersionUID = -7792473850222708125L;
	private JLabel label;
	private Color highlight,border_color,pressed_color;
	private boolean pressed, m_entered;
	private MouseListener m_listener;
	private Graphics2D g2d;

	public Button(String button_label) {
		button(new JLabel(button_label));
	}
	public Button(ImageIcon img) {
		button(new JLabel(img));
	}
	private final void button(JLabel lbl) {
		setLayout(new GridLayout(1, 1));
		setOpaque(false);
		
		label = lbl;
		label.setOpaque(false);
		label.setFont(h1);
		label.setForeground(doc_color[0]);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
		add(label);
		
		setBackground(main_color[2]);
		setHighlight(main_color[0]);
		setPressedColor(getBackground().darker());
		setBorderColor(new Color(0,0,0,0));
		setArc(10);
		
		m_listener = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				onClickButton();
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				onReleaseButton();
			}
			@Override
			public void mousePressed(MouseEvent e) {
				onPressButton();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				onDehighlightButton();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				onHighlightButton();
			}
		};
		
		setEnabled(true);
	}
	@Override
	public void paint(Graphics g) {
		g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		if(pressed) {
			g2d.setColor(getPressedColor());
		}
		else {
			g2d.setColor(getBackground());
		}
		g2d.fillRoundRect( 0, 0, getWidth(), getHeight(), getArc(), getArc());

		super.paint(g2d);
		
		if(m_entered) {
			g2d.setColor(getHighlight());
		}
		else {
			g2d.setColor(getBorderColor());
		}
		g2d.drawRoundRect( 0, 0, getWidth()-1, getHeight()-1, getArc(), getArc());

	}
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		
		for(MouseListener m_l :getMouseListeners()) {
			if(m_l == m_listener) {
				if(!enabled) {
					removeMouseListener(m_listener);
					setBackground(Theme.opacity(getBackground(), 0.25f));
				}
				return;
			}
		}
		
		addMouseListener(m_listener);
		setBackground(Theme.opacity(getBackground(), 1f));
		
		repaint();
	}
	public void setPressed(boolean press) {
		pressed = press;
		repaint();
	}
	public void setMouseEntered(boolean entered) {
		m_entered = entered;
		repaint();
	}
	public void setHighlight(Color highlight) {
		this.highlight = highlight;
	}
	public void setBorderColor(Color borderColor) {
		border_color = borderColor;
	}
	public void setPressedColor(Color pressedColor) {
		pressed_color = pressedColor;
	}
	public Color getHighlight() {
		return highlight;
	}
	public Color getBorderColor() {
		return border_color;
	}
	public Color getPressedColor() {
		return pressed_color;
	}
	public JLabel getLabel() {
		return label;
	}
	public boolean isPressed() {
		return pressed;
	}
	public boolean isMouseEntered() {
		return m_entered;
	}
	public void onClickButton() {
		onAction();
	}
	public void onPressButton() {
		setPressed(true);
	}
	public void onReleaseButton() {
		setPressed(false);
	}
	public void onHighlightButton() {
		setMouseEntered(true);
	}
	public void onDehighlightButton() {
		setMouseEntered(false);
	}
	public abstract void onAction();
}
