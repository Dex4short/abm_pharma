package gui;

import javax.swing.JPanel;

public class Panel extends JPanel{
	private static final long serialVersionUID = -7827218546812480617L;
	private int arc,margine,indent;

	public Panel() {
		setOpaque(false);
		setLayout(null);
		
	}
	public void setArc(int arc) {
		this.arc = arc;
	}
	public int getArc() {
		return arc;
	}
	public void setMargine(int margine) {
		this.margine = margine;
	}
	public int getMargine() {
		return margine;
	}
	public int getIndent() {
		return indent;
	}
	public void setIndent(int indent) {
		this.indent = indent;
	}
}
