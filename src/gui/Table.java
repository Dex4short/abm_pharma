package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.function.Consumer;

import javax.swing.JComponent;
import javax.swing.JLabel;

import misc.interfaces.Theme;

public abstract class Table extends Panel{
	private static final long serialVersionUID = -2108154469027694188L;
	private Column column[];
	private CheckBox main_checkBox;
	private int columns, column_h, column_w, indent, check_count, checked_rows[];
	private ListPanel list_panel;
	private Graphics2D g2d;

	public Table(String column_labels[]) {
		setLayout(null);
		setBackground(Theme.doc_color[0]);
		setForeground(Theme.gray_shade[0]);
		
		main_checkBox = new CheckBox() {
			private static final long serialVersionUID = 9081935907172114487L;
			@Override
			public void onCheck(boolean toggled) {
				onClickMainCheckBox(toggled);
			}
		};
		
		list_panel = new ListPanel() {
			private static final long serialVersionUID = -141173420101906617L;
			@Override
			public void onSelectItem(int n) {
				onSelectTable(n);
			}
			@Override
			public void onPointItem(int n) {
				onPointTable(n);
			}
		};
		list_panel.setForeground(Theme.doc_color[0]);
		list_panel.setHighlightBackground(Theme.gray_shade[0]);
		list_panel.setHighlightForeground(Theme.gray_shade[0]);

		setArc(5);
		setIndent(30);
		setMargine(10);
		
		setColumns(column_labels);
		setColumnHeight(30);
		setRowHeight(30);
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				onTableResized(getWidth(), getHeight());
			}
		});
	}
	@Override
	public void paint(Graphics g) {
		g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setColor(getBackground());
		g2d.fillRoundRect(getMargine(), 0, getWidth()-(getMargine()*3)-list_panel.getVScrollBar().getWidth(), getColumnHeight(), getArc(), getArc());

		super.paint(g);
		
		g2d.setColor(getForeground());
		g2d.drawRoundRect(getMargine(), 0, getWidth()-(getMargine()*3)-list_panel.getVScrollBar().getWidth(), getColumnHeight(), getArc(), getArc());
	}
	public int getColumnCount() {
		return columns;
	}
	public int getColumnHeight() {
		return column_h;
	}
	public int getColumnWidth() {
		return column_w;
	}
	public int getRowHeight() {
		return list_panel.getItemHeight();
	}
	public int getRowCount() {
		return list_panel.getItemCount();
	}
	public int getIndent() {
		return indent;
	}
	public int getMargine() {
		return list_panel.getMargine();
	}
	public int getCheckCount() {
		return check_count;
	}
	public int[] getSelectedRows() {
		return checked_rows;
	}
	public JComponent getValueAt(int row, int column) {
		Row row_panel = (Row)list_panel.getItem(row);
		return row_panel.getComponentAt(column);
	}
	public void setColumnWidth(int columnWidth) {
		column_w = columnWidth;
	}
	public void setColumns(String column_labels[]) {
		removeAll();
		add(main_checkBox);
		
		columns = column_labels.length;
		column = new Column[columns];
		for(int l=0; l<column.length; l++) {
			column[l] = new Column(column_labels[l]);
			add(column[l]);
		}
		
		add(list_panel);
	}
	public void setColumnHeight(int columnHeight) {
		column_h = columnHeight;
		main_checkBox.setSize(10, 10);
		main_checkBox.setLocation(getMargine() + (getIndent()/2) - (main_checkBox.getWidth()/2), (column_h/2) - (main_checkBox.getHeight()/2));
	}
	public void setRowHeight(int rowHeight) {
		list_panel.setItemHeight(rowHeight);
	}
	public void setIndent(int indent) {
		this.indent = indent;
	}
	public void setMargine(int margine) {
		list_panel.setMargine(margine);
	}
	public void setAllCheckBoxesPressed(boolean press) {
		list_panel.getItemList().forEach(new Consumer<JComponent>() {
			public void accept(JComponent c) {
				((Row)c).getCheckBox().setPressed(press);
			}
		});
	}
	public void addRow(JComponent components[]) {
		list_panel.addItem(new Row(components)).setBackground(Theme.opacity((getRowCount()%2)==0 ? Theme.doc_color[0]: Theme.gray_shade[0], 0.25f));
		repaint();
	}
	public void removeRow(int n) {
		list_panel.removeItem(n);
	}
	public void checkSelectedRows() {
		checked_rows = new int[getCheckCount()];
		list_panel.getItemList().forEach(new Consumer<JComponent>() {
			private CheckBox box;
			private int i=0,n=0;
			@Override
			public void accept(JComponent comp) {
				box = ((Row)comp).getCheckBox();
				if(box.isPressed()) {
					checked_rows[i] = n;
					i++;
				}
				n++;
			}
		});
	}
	public void onTableResized(int w, int h) {
		main_checkBox.setLocation(getMargine() + (getIndent()/2) - (main_checkBox.getWidth()/2), (column_h/2) - (main_checkBox.getHeight()/2));
		setColumnWidth((w-(getMargine()*3)-getIndent()-list_panel.getVScrollBar().getWidth()) / getColumnCount());
		for(int l=0; l<getColumnCount(); l++) {
			column[l].setBounds(getMargine() + getIndent() + (getColumnWidth() * l), 0, getColumnWidth(), getColumnHeight());
		}
		list_panel.setBounds(0, getColumnHeight(), w, h-getColumnHeight());
		list_panel.repaint();
		repaint();
	}
	public void onClickMainCheckBox(boolean press) {
		setAllCheckBoxesPressed(press);
		checkSelectedRows();
		onSelectTable(getSelectedRows());
	}
	public void onPointTable(int n) {
		//do nothing
	}
	public void onSelectTable(int n) {
		setAllCheckBoxesPressed(false);
		CheckBox box = ((Row)list_panel.getItem(n)).getCheckBox();
		box.setPressed(!box.isPressed());
		checkSelectedRows();
		onSelectTable(getSelectedRows());
	}
	public abstract void onSelectTable(int n[]);
	
	public class Column extends JLabel{
		private static final long serialVersionUID = 1243781723141617946L;
		
		public Column(String label) {
			super(label);
			setOpaque(false);
			setForeground(Theme.gray_shade[1]);
			setFont(Theme.h1);
		}
	}
	
	public class Row extends Panel{
		private static final long serialVersionUID = 1243781703141617946L;
		private CheckBox check_box;
		private JComponent comp[];
		private int c;
		private Graphics2D g2d;
		
		public Row(JComponent components[]) {
			setLayout(null);
			setOpaque(false);
			setIndent(30);
			
			check_box = new CheckBox() {
				private static final long serialVersionUID = 1L;
				@Override
				public void onCheck(boolean toggled) {
					checkSelectedRows();
					onSelectTable(getSelectedRows());
				}
				@Override
				public void setPressed(boolean press) {
					if(press) {
						if(!isPressed()) {
							check_count++;
						}
					}
					else {
						if(isPressed()) {
							check_count--;
						}
					}
					super.setPressed(press);
				}
			};
			add(check_box);
			
			comp = components;
			for(int c=0; c<comp.length; c++) {
				comp[c].setOpaque(false);
				comp[c].setForeground(Theme.doc_color[1]);
				comp[c].setFont(Theme.h1);
				add(comp[c]);
			}
			
		}
		@Override
		public void paint(Graphics g) {
			g2d = (Graphics2D)g;
			
			g2d.setColor(getBackground());
			g2d.fillRoundRect(0, 0, getWidth(), getHeight(), getArc(), getArc());

			super.paint(g2d);
			
			check_box.setBounds((getIndent()/2) - 5, (getHeight()/2) - 5, 10, 10);
			for(c=0; c<comp.length; c++) {
				comp[c].setBounds(getIndent() + (getColumnWidth() * c), 0, getColumnWidth(), getHeight());
			}
		}
		public CheckBox getCheckBox() {
			return check_box;
		}
		public JComponent getComponentAt(int n) {
			return comp[n];
		}
	}
}
