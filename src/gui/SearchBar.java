package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import misc.interfaces.Drawable;
import misc.interfaces.Theme;

public abstract class SearchBar extends JPanel{
	private static final long serialVersionUID = -4102102193118450553L;
	private JTextField txt_field;
	private IconedButton filter_button, search_button;
	private ListPanel filter_listPanel;
	private Graphics2D g2d;
	private int arc;
	private boolean isFilterOpen;
	private String selected_filter;

	public SearchBar(String lbl, String filter_list[]) {
		setLayout(null);
		setOpaque(false);
		
		filter_button = new IconedButton(getFilterIcon(), "Filter") {
			private static final long serialVersionUID = -6556089546066139060L;
			@Override
			public void onAction() {
				onClickFilterButton();
			}
		};
		filter_button.getLabel().setForeground(filter_button.getForeground());
		add(filter_button);

		filter_listPanel = new ListPanel() {
			private static final long serialVersionUID = -8388444071110406471L;
			@Override
			public void onSelectItem(int n) {
				onClickSelectedFilter(((JLabel)getItem(n)).getText());
			}
		};
		
		txt_field = new JTextField();
		txt_field.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				onClickTextField(e);
			}
		});
		txt_field.setOpaque(false);
		txt_field.setBorder(null);
		add(txt_field);

		search_button = new IconedButton(getSearchIcon(), "") {
			private static final long serialVersionUID = 4020092466661583657L;
			@Override
			public void onAction() {
				onClickSearchButton();
			}
		};
		search_button.setBackground(Theme.gray_shade[0]);
		search_button.setForeground(Theme.main_color[2]);
		search_button.setHighlight(Theme.doc_color[0]);
		search_button.setPressedColor(search_button.getBackground().darker());
		add(search_button);
		
		setFilterList(filter_list);
		setSelectedFilter(filter_list[0]);
		setArc(25);
		
		addComponentListener(new ComponentAdapter() {
			int width,height;
			@Override
			public void componentResized(ComponentEvent e) {
				width = getWidth();
				height = getHeight();			
				
				filter_button.setBounds(0, 0, 115, height);
				txt_field.setBounds(filter_button.getWidth() + 10, 0, width - filter_button.getWidth() - 10 - (arc * 2), height);
				search_button.setBounds(txt_field.getX() + txt_field.getWidth() + 2, 2, width - (txt_field.getX() + txt_field.getWidth()) - 4, height - 4);
				filter_listPanel.setBounds(0, getY() + height, width, 100);
			}
		});
	}
	public ListPanel getFilterListPanel() {
		return filter_listPanel;
	}
	public IconedButton getSearchButton() {
		return search_button;
	}
	public IconedButton getFilterButton() {
		return filter_button;
	}
	public void addFilterList(String label) {
		JLabel lbl = new JLabel(label);
		lbl.setOpaque(false);
		lbl.setForeground(Theme.doc_color[1]);
		lbl.setFont(Theme.h1);
		lbl.setVerticalTextPosition(JLabel.CENTER);
		filter_listPanel.addItem(lbl);
	}
	public void setFilterList(String label[]) {
		for(String lbl: label) {
			addFilterList(lbl);
		}
	}
	public void setSelectedFilter(String selected_filter) {
		this.selected_filter = selected_filter;
		getFilterButton().getLabel().setText(selected_filter);
	}
	public String getSelectedFilter() {
		return selected_filter;
	}
	public void setArc(int arc) {
		this.arc = arc;
		filter_button.setArc(arc);
		search_button.setArc(arc - 4);
	}
	public int getArc() {
		return arc;
	}
	public void openFilter(){
		if(!isFilterOpen) {
			getParent().add(filter_listPanel, 0).setBounds(getX(), getY() + getHeight(), getWidth(), 100);
			getParent().repaint();
			isFilterOpen = true;
		}
	}
	public void closeFilter() {
		if(isFilterOpen) {
			getParent().remove(filter_listPanel);
			getParent().repaint();
			isFilterOpen = false;
		}
	}
	public void onClickFilterButton() {
		if(isFilterOpen) {
			closeFilter();
		}
		else{
			openFilter();
		}
	}
	public void onClickTextField(MouseEvent e) {
		closeFilter();
	}
	public void onClickSelectedFilter(String selected) {
		setSelectedFilter(selected);
		closeFilter();
	}
	public void onClickSearchButton() {
		onSearch();
		closeFilter();
	}
	public abstract void onSearch();
	@Override
	public void paint(Graphics g) {
		g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setColor(Theme.doc_color[0]);
		g2d.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
		
		g2d.setColor(Theme.gray_shade[0]);
		g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, arc, arc);
		
		super.paint(g2d);
	}
	public static Drawable getFilterIcon() {
		return new Drawable() {
			@Override
			public void draw(Graphics2D g2d, int x, int y, int w, int h) {
				int p[][]= {
						{(int)(x + (w * 0.10)), (int)(x + w*0.90), (int)(x + (w*0.60)), (int)(x + w*0.60), (int)(x + (w*0.40)), (int)(x + (w*0.40))},
						{y, y, y + (h/2), y + h, (int)(y + (h*0.90)), y + (h/2)}
				};
				g2d.drawPolygon(p[0], p[1], 6);
			}
		};
	}
	public static Drawable getSearchIcon() {
		return new Drawable() {
			@Override
			public void draw(Graphics2D g2d, int x, int y, int w, int h) {
				g2d.drawOval(x, y, (int)(w*0.80), (int)(h*0.80));
				g2d.drawLine(x + (int)(w*0.40) + (int)(Math.cos(Math.toRadians(45)) * (int)(w*0.40)) + 1, y + (int)(h*0.40) + (int)(Math.sin(Math.toRadians(45)) * (int)(h*0.40)) + 1, x + w, y + h);
			}
		};
	}
}

