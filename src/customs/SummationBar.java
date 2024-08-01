package customs;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JLabel;

import gui.Panel;
import gui.TextField;
import misc.interfaces.UICustoms;
import misc.objects.Decimal;

public class SummationBar extends Panel implements UICustoms{
	private static final long serialVersionUID = -7505975260318730558L;
	private static final int gap=20;
	private TextField field1, field2;
	private JLabel label1, label2;
	
	public SummationBar(String lbl1, Decimal dec1, String lbl2, Decimal dec2) {
		setBackground(main_color[2]);
		setArc(5);
		
		label1 = new JLabel(lbl1);
		label1.setHorizontalAlignment(JLabel.RIGHT);
		label1.setSize(125, 20);
		label1.setFont(h2);
		label1.setForeground(doc_color[0]);
		add(label1);

		field1 = new TextField(dec1.toString());
		field1.setArc(20);
		field1.setSize(150, 20);
		field1.getTextField().setEditable(false);
		add(field1);
		
		label2 = new JLabel(lbl2);
		label2.setHorizontalAlignment(JLabel.RIGHT);
		label2.setSize(125, 20);
		label2.setFont(h2);
		label2.setForeground(doc_color[0]);
		add(label2);
		
		field2 = new TextField(dec2.toString());
		field2.setArc(20);
		field2.setSize(150, 20);
		field2.getTextField().setEditable(false);
		add(field2);
	}
	private int c,last;
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		
		Component comp[] = getComponents();
		
		last = comp.length - 1;
		comp[last].setLocation(width - gap - comp[last].getWidth(), 5);
		
		for(c=last-1; c>=0; c--) {
			comp[c].setLocation(comp[c+1].getX() - gap - comp[c].getWidth(), 5);
		}
	}
	@Override
	public void paint(Graphics g) {
		g.setColor(getBackground());
		g.fillRoundRect(0, 0, getWidth(), getHeight(), getArc(), getArc());
		
		super.paint(g);
	}
}
