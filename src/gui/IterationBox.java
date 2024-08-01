package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import misc.interfaces.Theme;

public abstract class IterationBox extends Panel implements Theme{
	private static final long serialVersionUID = 1L;
	private JLabel lbl;
	private Button btn_inc, btn_dec;
	private ArrayList<String> iterations;
	private int selected;
	private Color border_color;
	
	public IterationBox(String iterations[], int selected) {
		setBackground(Theme.doc_color[0]);
		setBorderColor(Theme.gray_shade[0]);
		setArc(5);
		
		lbl = new JLabel();
		lbl.setBorder(BorderFactory.createEmptyBorder(0, 2, 0 ,0));
		lbl.setFont(h1);
		lbl.setOpaque(false);
		add(lbl);
		
		btn_inc = new Button("+") {
			private static final long serialVersionUID = 3899991859843739702L;
			@Override
			public void onAction() {
				increment();
			}
		};
		button_color(btn_inc);
		add(btn_inc);

		btn_dec = new Button("-") {
			private static final long serialVersionUID = 3899991859843739702L;
			@Override
			public void onAction() {
				decrement();
			}
		};
		button_color(btn_dec);
		add(btn_dec);
		
		this.iterations = new ArrayList<String>();
		setIterations(iterations);
		setSelectedIteration(selected);
	}
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		lbl.setBounds(0, 0, width - 20, height);
		btn_inc.setBounds(lbl.getWidth()-1, 1, 20, (height/2)-1);
		btn_dec.setBounds(lbl.getWidth()-1, height/2, 20, (height/2)-1);
	}
	@Override
	public void paint(Graphics g) {
		g.setColor(getBackground());
		g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, getArc(), getArc());
		
		super.paint(g);
		
		g.setColor(getBorderColor());
		g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, getArc(), getArc());
	}
	public void setIterations(String iterations[]) {
		this.iterations.clear();
		for(String str: iterations) {
			this.iterations.add(str);
		}
	}
	public void setSelectedIteration(int n) {
		if(n>=0 && n<iterations.size()) {
			selected = n;
		}
		setSelectedIterationValue(iterations.get(selected));
	}
	public void setSelectedIterationValue(String value) {
		lbl.setText(value);
	}
	public void setBorderColor(Color border_color) {
		this.border_color = border_color;
	}
	public JLabel getLabel() {
		return lbl;
	}
	public Button getIncrementButton() {
		return btn_inc;
	}
	public Button getDecrementButton() {
		return btn_dec;
	}
	public String getSelectedIterationValue() {
		return lbl.getText();
	}
	public int getSelectedIteration() {
		return selected;
	}
	public Color getBorderColor() {
		return border_color;
	}
	public void increment() {
		if(selected < iterations.size()-1) {
			selected++;
		}
		else {
			Toolkit.getDefaultToolkit().beep();
		}
		setSelectedIterationValue(iterations.get(selected));
		onIncrement(getSelectedIterationValue());
	}
	public void decrement() {
		if(selected > 0) {
			selected--;
		}
		else {
			Toolkit.getDefaultToolkit().beep();
		}
		setSelectedIterationValue(iterations.get(selected));
		onDecrement(getSelectedIterationValue());
	}
	public abstract void onIncrement(String selectedIterationValue);
	public abstract void onDecrement(String selectedIterationValue);
	
	private final void button_color(Button btn) {
		btn.setArc(3);
		btn.setBackground(Theme.doc_color[0]);
		btn.setForeground(Theme.doc_color[1]);
		btn.setBorderColor(Theme.gray_shade[0]);
		btn.setPressedColor(Theme.gray_shade[1]);

		btn.getLabel().setForeground(Theme.doc_color[1]);
	}
}
