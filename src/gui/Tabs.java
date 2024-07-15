package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import misc.interfaces.Drawable;
import misc.interfaces.Theme;

public abstract class Tabs extends Panel{
	private static final long serialVersionUID = 330406143409783800L;
	private GuiTab tab[];
	private int tw,th,selected;
	private Panel frame,panel;

	public Tabs(String labels[]) {
		setLayout(null);
		setOpaque(false);
		setTabSize(100, 40);
		
		tab = new GuiTab[labels.length];
		
		int t;
		for(t=0; t<tab.length; t++) {
			final int n=t;
			tab[t] = new GuiTab(labels[t]);
			tab[t].addMouseListener(new MouseAdapter() {
				final int N=n;
				@Override
				public void mouseClicked(MouseEvent e) {
					untogleAll();
					setSelected(N);
					tab[selected].togle();
					setPanel(changePanel(getSelected()));
				}
				public void untogleAll() {
					for(int t=0; t<tab.length; t++) {
						if(tab[t].isTogled()) {
							tab[t].togle();
						}
					}
				}
			});
			tab[t].setBounds(tw * t, 0, tw, th);
			add(tab[t]);
		}
		tab[0].setArc(25, 0);
		tab[t-1].setArc(0, 25);
		
		setSelected(0);
		tab[getSelected()].togle();
		
		frame = new Panel() {
			private static final long serialVersionUID = 1L;
			private Graphics2D g2d;
			@Override
			public void paint(Graphics g) {
				g2d = (Graphics2D)g;
				
				g2d.setColor(getBackground());
				Drawable.fillRoundRect(g2d, 0, 0, frame.getWidth(), frame.getHeight(), 0, 10);
				
				super.paint(g2d);
			}
		};
		frame.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		frame.setBackground(Theme.doc_color[0]);
		frame.setLayout(new GridLayout(1, 1));
		frame.setOpaque(false);
		frame.add(panel = new Panel());
		add(frame);
	}
	public void setTabSize(int w, int h) {
		tw = w;
		th = h;
	}
	public void setSelected(int selected) {
		this.selected = selected;
	}
	public int getSelected() {
		return selected;
	}
	public void setPanel(Panel new_panel) {
		frame.remove(panel);
		panel = new_panel;
		frame.add(panel);
		revalidate();
		repaint();
	}
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		frame.setBounds(0, th, width, height-th);
		repaint();
	}
	public abstract Panel changePanel(int togled);
	
	public class GuiTab extends JLabel{
		private static final long serialVersionUID = 5657238903141568451L;
		private Color bg_highlight,txt_highlight;
		private int arc1,arc2;
		private boolean m_entered, togled;
		private Graphics2D g2d;
		
		public GuiTab(String label) {
			setText(label);
			setOpaque(false);
			setHorizontalAlignment(JLabel.CENTER);
			setVerticalAlignment(JLabel.CENTER);
			
			setFont(Theme.h1);
			setBackground(Theme.main_color[3]);
			setForeground(Theme.doc_color[0]);
			setHighlight(Theme.doc_color[0], Theme.doc_color[1]);
			
			setArc(0, 0);
			
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					m_entered = true;
					repaint();
				}
				@Override
				public void mouseExited(MouseEvent e) {
					m_entered = false;
					repaint();
				}
			});
		}
		public void setArc(int arc1, int arc2) {
			this.arc1 = arc1;
			this.arc2 = arc2;
		}
		@Override
		public void paint(Graphics g) {
			g2d = (Graphics2D)g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			if(m_entered && !togled) {
				g2d.setColor(getBackground().darker());
			}
			else {
				g2d.setColor(getBackground());
			}
			Drawable.fillRoundRect(g2d, 0, 0, getWidth(), getHeight(), arc1, arc2);
			
			g2d.setColor(Theme.opacity(getBackground().darker(), 0.10f));
			Drawable.drawRoundRect(g2d, 0, 0, getWidth(), getHeight(), arc1, arc2);
			
			super.paint(g2d);
		}
		public void setHighlight(Color bg_highlight, Color txt_highlight) {
			this.bg_highlight = bg_highlight;
			this.txt_highlight = txt_highlight;
		}
		public void togle() {
			Color holder;
			
			holder = getBackground();
			setBackground(bg_highlight);
			bg_highlight = holder;
			
			holder = getForeground();
			setForeground(txt_highlight);
			txt_highlight = holder;
			
			togled = !togled;
		}
		public boolean isTogled() {
			return togled;
		}
	}
}
