package customs.tables;

import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;

import customs.DateLabel;
import customs.DescriptionArea;
import database.mysql.MySQL_Inventory;
import gui.Panel;
import gui.Table;
import misc.enums.ItemCondition;
import misc.interfaces.TableConstants;
import misc.objects.Product;
import misc.objects.Remarks;

public abstract class TableProducts extends Table implements TableConstants.Products{
	private static final long serialVersionUID = -937488215455225000L;

	public TableProducts() {
		super(ProductFields);
	}
	public void displayProducts(ItemCondition item_condition) {
		removeAllRows();
		
		new Thread() {
			public void run() {
				Product products[] = MySQL_Inventory.selectAllProducts(item_condition);
				TableProductRow inv_row;
				
				for(Product product: products) {
					inv_row = createTableProductRow(product);
					addRow(inv_row);
				}

				clearSelections();
			}
		}.start();
	}
	public Product[] getSelectedProducts() {
		int
		selected_rows[] = getSelectedRows(),
		inv_id;
		
		Product
		products[] = new Product[selected_rows.length];
		
		TableProductRow row;
		for(int i=0; i<selected_rows.length; i++) {
			row = (TableProductRow)getRowAt(selected_rows[i]);
			inv_id = row.getProduct().getInvId();
			products[i] = MySQL_Inventory.selectProduct(inv_id);
		}
		
		return products;
	}
	public Product getSelectedProduct() {
		return getSelectedProducts()[0];
	}
	public Product[] getSelectedProductChildrenPrime() {
		Product parent_product = getSelectedProduct();
		return MySQL_Inventory.selectProductChildrenPrime(parent_product);
	}
	public Product[] getProducts() {
		Row rows[] = getRows();
		Product products[] = new Product[rows.length];
		for(int i=0; i<products.length; i++) {
			products[i] = ((TableProductRow)rows[i]).getProduct();
		}
		return products;
	}
	public void setSelectedProductsItemConditionTo(ItemCondition itemCondition) {
		Product products[] = getSelectedProducts();
		for(Product product: products) {
			MySQL_Inventory.updateProductItemCondition(product.getInvId(), itemCondition);
		}
	}
	public void setSelectedProductsReservation(Remarks remarks, ItemCondition itemCondition) {
		Product products[] = getSelectedProducts();
		int inv_id;
		for(Product product: products) {
			inv_id = product.getInvId();
			MySQL_Inventory.updateProductItemCondition(inv_id, itemCondition);
			MySQL_Inventory.updateProductRemarks(inv_id, remarks);
		}
	}
	public JComponent[] createProductRowComponents(Product product) {
		JComponent
		item_no		= new JLabel(product.getItem().getItemNo() + ""),
		description	= new DescriptionArea(product.getItem().getDescription()) {
			private static final long serialVersionUID = 3496999908344369471L;
			@Override
			public void setBounds(int x, int y, int width, int height) {
				super.setBounds(x, y, width-10, height);
			}
		},
		lot_no		= new JLabel(product.getItem().getLotNo()),
		date_added 	= new JLabel(product.getItem().getDateAdded().toString()),
		exp_date	= new Panel() {
			private static final long serialVersionUID = 3826616726128215212L;
			private DateLabel exp_lbl;
			{
				setLayout(new GridLayout(1,1));
				exp_lbl = new DateLabel(product.getItem().getExpDate().toString()) {
					private static final long serialVersionUID = 28304963119071874L;
					@Override
					public void onAction() {
						// TODO
					}
				};
				exp_lbl.setEnabled(false);
				add(exp_lbl);
			}
		},
		brand		= new JLabel(product.getItem().getBrand()),
		qty			= new JLabel(product.getPackaging().getQty().getQuantity() + " / " + product.getPackaging().getQty().getSize()),
		uom			= new JLabel(product.getPackaging().getUom().getUnitType().toString()),
		cost		= new JLabel(product.getPricing().getCost().toString()),
		unit_price	= new JLabel(product.getPricing().getUnitPrice().toString()),
		percent		= new JLabel(product.getPricing().getDiscount().toString()),
		unit_amount	= new JLabel(product.getPricing().getUnitAmount().toString());
		
		return new JComponent[] {item_no, description, lot_no, date_added, exp_date, brand, qty, uom, cost, unit_price, percent, unit_amount};
	}
	public TableProductRow createTableProductRow(Product product) {
		return new TableProductRow(product);
	}

	public class TableProductRow extends Row{
		private static final long serialVersionUID = -231322915165237246L;
		private Product product;
		
		public TableProductRow(Product product) {
			super(createProductRowComponents(product));
			setProduct(product);
		}
		public Product getProduct() {
			return product;
		}
		public void setProduct(Product product) {
			this.product = product;
		}
	}
}
