package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import misc.interfaces.Drawable;
import misc.interfaces.Theme;

public abstract class IconedButton extends Button{
	private static final long serialVersionUID = -4530514403461988259L;
	private Graphics2D g2d;
	private Drawable drawable;
	
	public IconedButton(Drawable drawable, String label) {
		super(label);
		iconed_button(drawable);
	}
	public IconedButton(ImageIcon icon, String label) {
		super(label);
		iconed_button(new Drawable() {
			@Override
			public void draw(Graphics2D g2d, int x, int y, int w, int h) {
				g2d.drawImage(icon.getImage(), x + (w/2) - (icon.getIconWidth()/2), y + (h/2) - (icon.getIconHeight()/2), null);
			}
		});
	}
	private final void iconed_button(Drawable drawable) {
		
		setArc(25);
		setBackground(new Color(0,0,0,0));
		setForeground(Theme.gray_shade[0]);
		setHighlight(Theme.gray_shade[1]);
		setPressedColor(getBackground());
		
		setLayout(null);
		getLabel().setHorizontalAlignment(JLabel.LEFT);
		setDrawable(drawable);
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setColor(getForeground());
		if(drawable != null) {
			if(getLabel().getText().equals("")) {
				drawable.draw(g2d, (getWidth()/2) - ((getHeight() - (int)(getArc()*0.50))/2), (getHeight()/2) - ((getHeight() - (int)(getArc()*0.50))/2), getHeight() - (int)(getArc()*0.50), getHeight() - (int)(getArc()*0.50));
			}
			else {
				drawable.draw(g2d, (int)(getArc()*0.25), (int)(getArc()*0.25), getHeight() - (int)(getArc()*0.50), getHeight() - (int)(getArc()*0.50));
				getLabel().setBounds(getHeight(), 0, getWidth() - getHeight() - (int)(getArc()*0.50), getHeight());
			}
		}
		else {
			if(getLabel().getText().equals("")) {
				g2d.drawRect((getWidth()/2) - ((getHeight() - (int)(getArc()*0.50))/2), (getHeight()/2) - ((getHeight() - (int)(getArc()*0.50))/2), getHeight() - (int)(getArc()*0.50), getHeight() - (int)(getArc()*0.50));
			}
			else {
				g2d.drawRect((int)(getArc()*0.25), (int)(getArc()*0.25), getHeight() - (int)(getArc()*0.50), getHeight() - (int)(getArc()*0.50));
				getLabel().setBounds(getHeight(), 0, getWidth() - getHeight() - (int)(getArc()*0.50), getHeight());
			}
		}
		
	}
	@Override
	public void onPressButton() {
		super.onPressButton();
	}
	@Override
	public void onReleaseButton() {
		super.onReleaseButton();
	}
	@Override
	public void onHighlightButton() {
		super.onHighlightButton();
	}
	@Override
	public void onDehighlightButton() {
		super.onDehighlightButton();
	}
	public Drawable getDrawable() {
		return drawable;
	}
	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;
	}
}
