package default_package.admin;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import default_package.admin.disposal.PanelDisposal;
import default_package.admin.inventory.PanelInventory;
import default_package.admin.reserves.PanelReserves;
import default_package.admin.transactions.PanelTransactions;
import gui.Panel;
import gui.Tabs;
import misc.interfaces.Theme;

public class PanelAdmin extends Panel{
	private static final long serialVersionUID = -6374468091255785095L;
	private final Image img;
	private Graphics2D g2d;
	private Tabs tabs;

	public PanelAdmin() {
		setLayout(null);
		setOpaque(false);
		
		img = Toolkit.getDefaultToolkit().getImage("res/ABM LOGO 2.png");
		
		String labels[] = {"Inventory", "Transactions", "Reserves", "Disposal", "Product Returns", "Statistics", "Customers"};
		tabs = new Tabs(labels) {
			private static final long serialVersionUID = 1L;
			@Override
			public Panel changePanel(int selected) {
				switch (selected) {
				case 0:
					return new PanelInventory();
				case 1:
					return new PanelTransactions();
				case 2:
					return new PanelReserves();
				case 3:
					return new PanelDisposal();
				default:
					return new Panel();
				}
			}
		};
		tabs.setPanel(new PanelInventory());
		add(tabs);
		
	}
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(10, 10, width - 20, height - 20);
		tabs.setBounds(10, 75, getWidth() - 20, getHeight() - 75);
	}
	@Override
	public void paint(Graphics g) {
		g2d = (Graphics2D)g;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		g2d.setColor(Theme.doc_color[0]);
		g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
		
		g2d.drawImage(img, 10, 10, this);
		
		g2d.setColor(Theme.gray_shade[0]);
		g2d.fillRect(0, 50, getWidth(), getHeight()-60);
		
		super.paint(g2d);
	}
}