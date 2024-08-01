package default_package.employee.counter;

import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.JLabel;

import default_package.ABM_Pharma;
import gui.ActionPanel;
import gui.HorizontalPanel;
import gui.ListPanel;
import gui.NumericField;
import misc.interfaces.UnitConverter;
import misc.objects.Product;
import misc.objects.Uom;

public abstract class SubPanelQty extends ActionPanel implements UnitConverter{
	private static final long serialVersionUID = 7956993707331785565L;
	private static final int panel_w=300, panel_h=300;
	private HorizontalPanel h_panel1, h_panel2;
	private JLabel label1, label2, uom_label;
	private NumericField qty_field;
	private ListPanel uom_list;
	private Product product;

	public SubPanelQty(Product product) {
		super("Product Quantity");
		setProduct(product);
		
		h_panel1 = new HorizontalPanel();
		getContentPane().add(h_panel1);
		
		label1 = new JLabel("Uom");
		label1.setFont(h1);
		label1.setForeground(doc_color[1]);
		h_panel1.add(label1);
		
		label2 = new JLabel("Qty");
		label2.setFont(h1);
		label2.setForeground(doc_color[1]);
		h_panel1.add(label2);
		
		h_panel2 = new HorizontalPanel();
		h_panel2.setBackground(gray_shade[0]);
		getContentPane().add(h_panel2);
		
		uom_label = new JLabel("Uom");
		uom_label.setFont(h1);
		uom_label.setForeground(doc_color[1]);
		h_panel2.add(uom_label);
		
		qty_field = new NumericField(product.getPackaging().getQty().getQuantity() + "");
		h_panel2.add(qty_field);
		
		uom_list = new ListPanel() {
			private static final long serialVersionUID = -729884907894417694L;
			@Override
			public void onSelectItem(int n) {
				ItemUom item_uom = (ItemUom)getItem(n);
				
				String 
				unit_name = item_uom.getUom().getUnitType().toString();
				
				uom_label.setText(unit_name);
			}
		};
		createUomListItems();	
		getContentPane().add(uom_list);

		uom_list.onSelectItem(0);
	}
	@Override
	public void onOk() {
		Product 
		product = getProduct(),
		byproducts[];
		
		Uom
		uom1 = product.getPackaging().getUom(),
		uom2 = ((ItemUom)uom_list.getSelectedItem()).getUom();
		
		int
		qty1 = product.getPackaging().getQty().getQuantity(),
		qty2 = qty_field.getNumber();
		
		byproducts = subtractUnits(product, uom1, qty1, uom2, qty2);
		if(byproducts == null) {
			ABM_Pharma.getWindow().getDisplayPanel().floatMessage("Insuficient unit quantity.");
			Toolkit.getDefaultToolkit().beep();
			return;
		}
		
		onProductQtyOk(byproducts);
	}
	@Override
	public void onCancel() {
		ABM_Pharma.getWindow().getStacksPanel().popPanel();
	}
	@Override
	public void onResizeContentPane(int w, int h) {
		h_panel1.setBounds(0, 0, w, 30);
		h_panel2.setBounds(0, 40, w, 30);
		uom_list.setBounds(0, 80, w , h - 90);
	}
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x + (width/2) - (panel_w/2), y + (height/2) - (panel_h/2), panel_w, panel_h);
	}
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}

	public class ItemUom extends HorizontalPanel{
		private static final long serialVersionUID = 285736772407821987L;
		private JLabel lbl1,lbl2;
		private Uom uom;
		
		public ItemUom(Uom uom, String description) {
			setBackground(new Color(0,0,0,0));
			setForeground(new Color(0,0,0,0));
			
			String uom_name = uom.getUnitType().toString();

			lbl1 = new JLabel(uom_name);
			lbl1.setFont(h1);
			lbl1.setForeground(doc_color[1]);
			add(lbl1);
			
			lbl2 = new JLabel(description);
			lbl2.setFont(h1);
			lbl2.setForeground(gray_shade[1]);
			add(lbl2);
			
			setUom(uom);
		}
		public Uom getUom() {
			return uom;
		}
		public void setUom(Uom uom) {
			this.uom = uom;
		}
	}
	
	public abstract void onProductQtyOk(Product byproducts[]);
	
	private void createUomListItems() {
		Uom uom = product.getPackaging().getUom();
		
		String 
		description1 = uom.getUnitSize() + " " + uom.getUnitType(),
		description2 = "";
		
		while(uom != null) {
			uom_list.addItem(new ItemUom(uom, description1 + description2));
			
			description1 = "1 " + uom.getUnitType();
			
			uom = uom.getSubUom();
			
			if(uom != null) {
				description2 = " : " + uom.getUnitSize() + " " + uom.getUnitType() + "(s)";
			}
			else {
				description2 = "";
			}
		}	
	}
}
