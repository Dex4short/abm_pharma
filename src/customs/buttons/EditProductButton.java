package customs.buttons;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

import default_package.ABM_Pharma;
import default_package.admin.inventory.SubPanelProduct;
import default_package.admin.inventory.SubPanelProduct.FillupPanel.DateField;
import default_package.admin.inventory.SubPanelProduct.FillupPanel.QtyField;
import default_package.admin.inventory.SubPanelProduct.FillupPanel.UomField;
import gui.Button;
import gui.TextField;
import misc.interfaces.Icons;
import misc.interfaces.UICustoms;
import misc.objects.Product;
import misc.objects.Quantity;

public abstract class EditProductButton  extends Button implements UICustoms,Icons{
	private static final long serialVersionUID = -4074774662182951765L;
	
	public EditProductButton() {
		super(PencilIcon);
		custom_button_appearance1(this);
		setEnabled(false);
	}
	@Override
	public void onAction() {
		Product products[] = getSelectedProduct();
		ABM_Pharma.getWindow().getStacksPanel().pushPanel(new SubPanelEditProduct(products), shadow);
	}
	
	private class SubPanelEditProduct extends SubPanelProduct{
		private static final long serialVersionUID = -2025602627570912043L;
		private Product old_products[];
		
		public SubPanelEditProduct(Product products[]) {
			super("Edit Product");
			old_products = products;
			
			for(int i=0; i<3;i++) {
				((QtyField)getFillupPanel(i).getField(qty)).maintainAspectRatio(1);
			}
			
			new Thread() {
				public void run() {					
					Quantity 
					product_qty = products[0].getPackaging().getQty();
					
					int 
					qty = product_qty.getQuantity(),
					size = product_qty.getSize();
					
					if(qty != size) {
						restrict_entries(products.length);
					}

					for(int i=0; i<products.length; i++) {
						getFillupPanel(i).displayProduct(products[i]);
					}
					
					showFillupPanel(products.length);
				}
			}.start();
		}
		@Override
		public void onProductOk(Product new_products[]) {
			if(old_products.length == new_products.length) {
				boolean unchanged;
				String str1,str2;
				
				str1 = old_products[0].toString();
				str2 = new_products[0].toString();
				unchanged = str1.equals(str2);
				
				for(int i=1; i<new_products.length; i++){					
					str1 = old_products[i].toString();
					str2 = new_products[i].toString();
					unchanged = unchanged && (str1.equals(str2));
				};
				
				if(unchanged) {
					ABM_Pharma.getWindow().getDisplayPanel().floatMessage("There are no changes.");
					return;
				}
			}

			onEditProduct(new_products, old_products);
			ABM_Pharma.getWindow().getStacksPanel().popPanel();
			ABM_Pharma.getWindow().getDisplayPanel().floatMessage(new_products[0].getItem().getDescription() + " [" + new_products[0].getItem().getBrand() + "] changed.");
		}
		@Override
		public void onProductCancel() {
			ABM_Pharma.getWindow().getStacksPanel().popPanel();
		}
		
		private final void restrict_entries(int fillup_entries) {
			TextField text_field;
			DateField date_field;
			QtyField qty_field;
			UomField uom_field;
			for(int i=0; i<fillup_entries; i++) {
				text_field = ((TextField)getFillupPanel(i).getField(item_no));
				text_field.getTextField().setEditable(false);
				text_field.getTextField().setForeground(gray_shade[1]);
				add_prompt(text_field.getTextField(), "Item No has been finalized.");
				
				text_field = ((TextField)getFillupPanel(i).getField(description));
				text_field.getTextField().setEditable(false);
				text_field.getTextField().setForeground(gray_shade[1]);
				add_prompt(text_field.getTextField(), "Description has been finalized.");
				
				text_field = ((TextField)getFillupPanel(i).getField(lot_no));
				text_field.getTextField().setEditable(false);
				text_field.getTextField().setForeground(gray_shade[1]);
				add_prompt(text_field.getTextField(), "Lot No has been finalized.");
				
				text_field = ((TextField)getFillupPanel(i).getField(brand));
				text_field.getTextField().setEditable(false);
				text_field.getTextField().setForeground(gray_shade[1]);
				add_prompt(text_field.getTextField(), "Brand Name has been finalized.");

				text_field = ((TextField)getFillupPanel(i).getField(cost));
				text_field.getTextField().setEditable(false);
				text_field.getTextField().setForeground(gray_shade[1]);
				add_prompt(text_field.getTextField(), "Product Cost has been finalized.");
				
				date_field = ((DateField)getFillupPanel(i).getField(date_added));
				date_field.setEnabled(false);
				add_prompt(date_field, "Can no longer change the Date Added.");
				
				date_field = ((DateField)getFillupPanel(i).getField(exp_date));
				date_field.setEnabled(false);
				add_prompt(date_field, "Can no longer change the Date Expiry.");
				
				qty_field = ((QtyField)getFillupPanel(i).getField(qty));
				qty_field.setEditable(false);
				add_prompt(qty_field.getLeftIntegerField().getTextField(), "Can no longer change the Product Quantity.");
				add_prompt(qty_field.getRightIntegerField().getTextField(), "Can no longer change the Product Size.");
				
				uom_field = ((UomField)getFillupPanel(i).getField(uom));
				uom_field.setEnabled(false);
				add_prompt(uom_field, "Can no longer change Units.");
			}
		}
		private final void add_prompt(JComponent component, String message) {
			component.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					ABM_Pharma.getWindow().getDisplayPanel().floatMessage(message);
					Toolkit.getDefaultToolkit().beep();
				};
			});
		}
	}
	public abstract void onEditProduct(Product new_products[], Product old_products[]);
	public abstract Product[] getSelectedProduct();
	

}
