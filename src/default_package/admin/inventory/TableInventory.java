package default_package.admin.inventory;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JLabel;

import customs.DescriptionArea;
import customs.DateLabel;
import gui.Panel;
import gui.Table;
import misc.interfaces.InventoryConstants;
import misc.objects.Product;

public abstract class TableInventory extends Table implements InventoryConstants{
	private static final long serialVersionUID = 1L;
	
	public TableInventory() {
		super(InventoryFields);
	}
	public void addRow(Product product) {
		addRow(new JComponent[] {
				new JLabel(product.getItemNo() + ""),
				new DescriptionArea(product.getDescription()) {
					private static final long serialVersionUID = 3496999908344369471L;
					{
						addMouseListener(new MouseAdapter() {
							public void mouseClicked(MouseEvent e) {
								getParent().getMouseListeners()[0].mouseClicked(e);
							}
							public void mouseEntered(MouseEvent e) {
								getParent().getMouseListeners()[0].mouseEntered(e);
							}
						});
					}
					@Override
					public void setBounds(int x, int y, int width, int height) {
						super.setBounds(x, y, width-10, height);
					}
				},
				new JLabel(product.getLotNo()),
				new JLabel(product.getDateAdded().toString()),
				new Panel() {
					private static final long serialVersionUID = 3826616726128215212L;
					private DateLabel exp_lbl;
					{
						setLayout(new GridLayout(1,1));
						exp_lbl = new DateLabel(product.getExpDate().toString()) {
							private static final long serialVersionUID = 28304963119071874L;
							@Override
							public void onAction() {
								// TODO
							}
							@Override
							public void onCheckDate(Status status) {
								// TODO
							}
						};
						exp_lbl.setEnabled(false);
						add(exp_lbl);
					}
				},
				new JLabel(product.getBrand()),
				new JLabel(product.getQty() + ""),
				new JLabel(product.getUom().getUnitName()),
				new JLabel(product.getCost().toString()),
				new JLabel(product.getUnitPrice().toString()),
				new JLabel(product.getDiscount().getPercentValue()),
				new JLabel(product.getUnitAmount().toString())
		});
	}
	public Product getSelectedProduct() {
		return null;
	}
}
