package customs;

import java.awt.FontMetrics;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;

import misc.interfaces.Theme;

public class DescriptionArea extends JTextArea implements Theme{
	private static final long serialVersionUID = -8865892778780763823L;
	private FontMetrics metrics;
	private int last_width, str_w, str_h, lines, txt_h, gap;
	
	public DescriptionArea(String text) {
		super(text);
		setLineWrap(true);
		setWrapStyleWord(true);
		setEditable(false);
		setOpaque(false);
		
		setFont(h1);
		setForeground(doc_color[1]);
	}
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds( x, y, width, height);
		
		if(width != last_width) {
			metrics = getFontMetrics(getFont());
			str_w = metrics.stringWidth(getText());
			str_h = metrics.getAscent() + metrics.getDescent();
			
			lines = (int)Math.ceil((double)str_w/(double)width);
			txt_h = str_h * lines;
			
			if(txt_h>=height) {
				gap = 0;
			}
			else {
				gap = (height/2) - (txt_h/2);
			}
			setBorder(BorderFactory.createEmptyBorder(gap, 0, gap, 0));
		}
		last_width = width;
	}
}
