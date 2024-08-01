package default_package.employee;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import default_package.employee.counter.PanelCounter;
import gui.Panel;
import misc.interfaces.Theme;


public class PanelEmployee extends Panel implements Theme{
	private static final long serialVersionUID = -6374468091255785095L;
	private final Image img;
	private Graphics2D g2d;
	private Panel panel;

	public PanelEmployee() {
		setLayout(null);
		setOpaque(false);
		
		img = Toolkit.getDefaultToolkit().getImage("res/ABM LOGO 2.png");
		
		panel = new PanelCounter();
		add(panel);
	}
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(10, 10, width-20, height-20);
		panel.setBounds(10, 60, getWidth()-20, getHeight()-60);
	}
	@Override
	public void paint(Graphics g) {
		g2d = (Graphics2D)g;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		g2d.setColor(doc_color[0]);
		g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
		
		g2d.drawImage(img, 10, 10, this);
		
		g2d.setColor(gray_shade[0]);
		g2d.fillRoundRect(0, 50, getWidth(), getHeight()-50, 10, 10);
		
		super.paint(g2d);
	}
}