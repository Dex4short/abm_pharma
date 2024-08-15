package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

import misc.interfaces.Theme;

public class TextField extends Panel{
	private static final long serialVersionUID = -4102102193118450553L;
	private JTextField txtField;
	private String pre_text,sub_text;
	private Graphics2D g2d;
	private Color subtext_color;

	public TextField(String txt) {
		text_field(txt);
	}
	public TextField(String txt, String pre_text) {
		text_field(pre_text + txt);
		setPreText(pre_text);
	}
	public TextField(String txt, String pre_text, String sub_text) {
		text_field(pre_text + txt);
		setPreText(pre_text);
		setSubText(sub_text);
	}
	private final void text_field(String txt) {
		setLayout(new GridLayout(1, 1));
		setArc(5);
		
		pre_text = "";
		sub_text = "";
		
		txtField = new JTextField(txt);
		txtField.setOpaque(false);
		txtField.setBorder(null);
		txtField.setFont(Theme.h1);
		add(txtField);
		
		setBackground(Theme.doc_color[0]);
		setForeground(Theme.gray_shade[0]);
		setSubTextColor(Theme.gray_shade[0]);
		setFont(Theme.h1);
		
		txtField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(txtField.getCaretPosition()<pre_text.length()) {
					txtField.setText(pre_text);
					Toolkit.getDefaultToolkit().beep();
				}
			}
		});
	}
	@Override
	public void paint(Graphics g) {
		g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		g2d.setColor(getBackground());
		g2d.fillRoundRect(0, 0, getWidth(), getHeight(), getArc(), getArc());
		
		g2d.setColor(getForeground());
		g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, getArc(), getArc());
		
		g2d.setFont(getFont());
		g2d.setColor(getSubTextColor());
		if(txtField.getText() == null) {
			g2d.drawString(sub_text, txtField.getX() + g2d.getFontMetrics().stringWidth(pre_text), (getHeight()/2) + (g2d.getFontMetrics().getAscent()/2));
		}
		
		super.paint(g);
	}
	@Override
	public void setArc(int arc) {
		super.setArc(arc);
		setBorder(BorderFactory.createEmptyBorder( 0, getArc()/2, 0, getArc()/2));
	}
	public void setSubText(String sub_text) {
		this.sub_text = sub_text;
	}
	public void setPreText(String pre_text) {
		this.pre_text = pre_text;
	}
	public void setSubTextColor(Color subtext_color) {
		this.subtext_color = subtext_color;
	}
	public JTextField getTextField() {
		return txtField;
	}
	public String getSubText() {
		return sub_text;
	}
	public String getPreText() {
		return pre_text;
	}
	public Color getSubTextColor() {
		return subtext_color;
	}
}
