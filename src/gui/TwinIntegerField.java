package gui;

import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import misc.interfaces.Theme;

public class TwinIntegerField extends Panel{
	private static final long serialVersionUID = -3497471385717877254L;
	private NumericField field1, field2;
	private char divider;
	private double aspect_ratio;
	private boolean aspectRatioEnabled;
	
	public TwinIntegerField(String qty, String size){
		setForeground(Theme.gray_shade[1]);
		setFont(Theme.h1);
		
		field1 = new NumericField(qty);
		field2 = new NumericField(size);

		addFieldActionListener(field1, field2);
		addFieldActionListener(field2, field1);
		
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
	public NumericField getLeftIntegerField() {
		return field1;
	}
	public NumericField getRightIntegerField() {
		return field2;
	}
	public boolean isAspectRatioEnabled() {
		return aspectRatioEnabled;
	}
	public void maintainAspectRatio(double aspectRatio) {
		this.aspect_ratio = aspectRatio;
		aspectRatioEnabled = aspectRatio != 0;
	}
	private final void addFieldActionListener(NumericField field1, NumericField field2) {
		field1.getTextField().addKeyListener(new KeyAdapter() {
			private String str;
			private int num, len, column;
			@Override
			public void keyReleased(KeyEvent e) {
				if(isAspectRatioEnabled()) {
					str = field1.getTextField().getText();
					if(!str.equals("")) {
						num = Integer.parseInt(str);
					}
					else {
						num = 0;
					}
					
					column = field1.getTextField().getCaretPosition();
					
					field1.getTextField().setText(num + "");
					field2.getTextField().setText((int)(num / aspect_ratio) + "");
					
					len = (num + "").length();
					if(column > len) {
						column = len;
					}
					field1.getTextField().setCaretPosition(column);
				}
			}
		});
	}
}
