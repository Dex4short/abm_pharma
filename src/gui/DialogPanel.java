package gui;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;

public abstract class DialogPanel extends ActionPanel{
	private static final long serialVersionUID = -3242567548405939538L;
	private final int panel_w, panel_h;
	private JTextArea txt_area;

	public DialogPanel(String tittle, String dialog) {
		super(tittle);
		
		panel_w = 200;
		panel_h = 150;
		
		txt_area = new JTextArea();
		txt_area.setBackground(getBackground());
		txt_area.setForeground(doc_color[1]);
		txt_area.setBorder(BorderFactory.createEmptyBorder());
		txt_area.setLineWrap(true);
		txt_area.setWrapStyleWord(true);
		txt_area.setText(dialog);
		
		getContentPane().add(txt_area);
		
	}
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x + (width/2) - (panel_w/2), y + (height/2) - (panel_h/2), panel_w, panel_h);
	}
	@Override
	public void onResizeContentPane(int w, int h) {
		txt_area.setBounds(getMargine(), getMargine(), w - (getMargine()*2), h - (getMargine()*2));
	}
	public JTextArea getTextArea() {
		return txt_area;
	}
}
