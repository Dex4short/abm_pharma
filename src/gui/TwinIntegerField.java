package gui;

import java.awt.Graphics;

import misc.interfaces.Theme;

public class TwinIntegerField extends Panel{
	private static final long serialVersionUID = -3497471385717877254L;
	private IntegerField field1, field2;
	private char divider;
	
	public TwinIntegerField(String qty, String size){
		setForeground(Theme.gray_shade[1]);
		setFont(Theme.h1);
		
		field1 = new IntegerField(qty);
		field2 = new IntegerField(size);
		
		add(field1);
		add(field2);

		setDivider(':');
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(getForeground());
		g.drawString(divider+"", (getWidth()/2) - (g.getFontMetrics().stringWidth(divider+"")/2), (getHeight()/2) + (g.getFontMetrics().getAscent()/2) - 3);
		g.drawLine(0, getHeight()-1, getWidth(), getHeight()-1);
	}
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		field1.setBounds(0, 0, (getWidth()/2) - 5, getHeight() - 3);
		field2.setBounds((getWidth()/2) + 5, 0, (getWidth()/2) - 5, getHeight() - 3);
	}
	public void setDivider(char divider) {
		this.divider = divider;
	}
	public char getDivider() {
		return divider;
	}
	public IntegerField getLeftIntegerField() {
		return field1;
	}
	public IntegerField getRightIntegerField() {
		return field2;
	}
}
