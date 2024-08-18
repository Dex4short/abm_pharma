package default_package.admin.reserves;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import gui.ActionPanel;

public abstract class SubPanelRemarks extends ActionPanel{
	private static final long serialVersionUID = -6166727417412619338L;
	private final int panel_w, panel_h;
	private JLabel textLength_display;
	private JTextArea txt_area;
	private int text_count;
	
	public SubPanelRemarks() {
		super("Remarks");
		
		panel_w = 300;
		panel_h = 250;
		
		textLength_display = new JLabel("0/64");
		textLength_display.setBounds(0, 0, 50, 25);
		getContentPane().add(textLength_display);
		
		txt_area = new JTextArea() {
			private static final long serialVersionUID = -5588543841548630841L;
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				g.setColor(gray_shade[0]);
				g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
			}
		};
		txt_area.addKeyListener(new KeyAdapter() {
			private String str;
			@Override
			public void keyReleased(KeyEvent e) {
				str = txt_area.getText();
				text_count = str.length();
				
				if(text_count > 64) {
					Toolkit.getDefaultToolkit().beep();
					txt_area.setText(str.substring(0, 64));
					textLength_display.setText("64/64");
				}
				else {
					textLength_display.setText(text_count + "/64");
				}
			}
		});
		txt_area.setFont(h1);
		txt_area.setBackground(doc_color[0]);
		txt_area.setForeground(doc_color[1]);
		txt_area.setLineWrap(true);
		txt_area.setWrapStyleWord(true);
		txt_area.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		getContentPane().add(txt_area);
		
	}
	@Override
	public void onResizeContentPane(int w, int h) {
		txt_area.setBounds(0, 30, w, h - 40);
	}
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x + (width/2) - (panel_w/2), y + (height/2) - (panel_h/2), panel_w, panel_h);
	}
	public JTextArea getTextArea() {
		return txt_area;
	}
	public int getTextCount() {
		return text_count;
	}
}
