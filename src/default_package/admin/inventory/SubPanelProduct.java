package default_package.admin.inventory;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import customs.DateLabel;
import default_package.ABM_Pharma;
import default_package.admin.inventory.SubPanelProduct.FillupPanel.DateField;
import default_package.admin.inventory.SubPanelProduct.FillupPanel.DefaultField;
import default_package.admin.inventory.SubPanelProduct.FillupPanel.QtyField;
import default_package.admin.inventory.SubPanelProduct.FillupPanel.FunctionButton;
import gui.ActionPanel;
import gui.Button;
import gui.DatePicker;
import gui.HorizontalPanel;
import gui.NumericField;
import gui.StrictTextField;
import gui.TwinIntegerField;
import gui.TextField;
import misc.interfaces.Accountancy;
import misc.interfaces.Field;
import misc.interfaces.Icons;
import misc.interfaces.TableConstants;
import misc.interfaces.TableConstants.Products;
import misc.interfaces.Theme;
import misc.interfaces.UnitConverter;
import misc.objects.Date;
import misc.objects.Decimal;
import misc.objects.Item;
import misc.objects.Packaging;
import misc.objects.Percentage;
import misc.objects.Pricing;
import misc.objects.Product;
import misc.objects.Quantity;
import misc.objects.Uom;

public abstract class SubPanelProduct extends ActionPanel implements TableConstants.Products{
	private static final long serialVersionUID = 4129364051265662075L;
	private int titled_panel_h = 120, showed_fillup_panels;
	private JLabel lbl[];
	private HorizontalPanel header_panel;
	private FillupPanel fillup_panel[];
	private FunctionButton function_btn[];
	
	public SubPanelProduct(String panel_title) {
		super(panel_title);
		
		header_panel = new HorizontalPanel();
		getContentPane().add(header_panel);
		
		lbl = new JLabel[ProductFields.length];
		int i;
		for(i=0; i<ProductFields.length; i++) {
			lbl[i] = new JLabel(ProductFields[i]);
			lbl[i].setOpaque(false);
			lbl[i].setFont(Theme.h1);
			lbl[i].setForeground(Theme.gray_shade[1]);
			header_panel.add(lbl[i]);
		}
		
		fillup_panel = new FillupPanel[3];
		function_btn = new FunctionButton[2];
		for(i=0; i<3; i++) {
			fillup_panel[i] = new FillupPanel();
			fillup_panel[i].setBackground(Theme.opacity(Theme.gray_shade[0], 0.25f));
			fillup_panel[i].setForeground(new Color(0,0,0,0));
			modify_fillup_panel(i);
			
			if(i != 0) {
				function_btn[i-1] = fillup_panel[i].new FunctionButton();
				getContentPane().add(function_btn[i-1]);
			}
			
			getContentPane().add(fillup_panel[i]);
		}
		
		showFillupPanel(1);
	}
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(30, (height/2) - (titled_panel_h/2), width-60, titled_panel_h);
	}
	private int i;
	@Override
	public void onResizeContentPane(int w, int h) {
		header_panel.setBounds(0, 0, w, 30);
		for(i=0; i<fillup_panel.length; i++) {
			fillup_panel[i].setBounds(0, 40 + (i * 40), w, 30);
			if(i != 0) {
				function_btn[i-1].setBounds(fillup_panel[i].getX() + fillup_panel[i].getWidth() - 25, fillup_panel[i].getY() + (fillup_panel[i].getHeight()/2) - 10, 20, 20);
			}
		}
	}
	@Override
	public void onOk() {
		Product products[] = new Product[showed_fillup_panels];
		
		int 
		parent_item_id = -1,
		parent_pack_id = -1;
		
		boolean product_prepared;
		
		try {
			for(int i=0; i<showed_fillup_panels; i++) {
				product_prepared = fillup_panel[i].prepareProduct(parent_item_id, parent_pack_id);
				
				if(product_prepared) {
					products[i] = fillup_panel[i].getProduct();
					
					parent_item_id = products[0].getItem().getItemId();
					parent_pack_id = products[0].getPackaging().getPackId();
				}
				else {
					return;
				}
			}
		} catch (Exception e) {
			ABM_Pharma.getWindow().getDisplayPanel().floatMessage("Unexpected Error...(SubPanelProduct -> onOk)");
			System.out.println(e.getMessage());
			return;
		}

		onProductOk(products);
	}
	@Override
	public void onCancel() {
		onProductCancel();
	}
	public FillupPanel getFillupPanel(int index) {
		return fillup_panel[index];
	}	
	public void showFillupPanel(int n) {
		for(int i=0; i<fillup_panel.length; i++) {
			if(i<n) {
				fillup_panel[i].setVisible(true);
				if(i != 0) {
					function_btn[i-1].setVisible(true);
				}
			}
			else {
				fillup_panel[i].setVisible(false);
				if(i != 0) {
					function_btn[i-1].setVisible(false);
				}
			}
		}
		titled_panel_h = 120 + (40 * n);
		showed_fillup_panels = n;
		
		revalidate();
	}
	public abstract void onProductOk(Product new_products[]);
	public abstract void onProductCancel();
	
	public class FillupPanel extends HorizontalPanel {
		private static final long serialVersionUID = 2087536255768169721L;
		private Field field[];
		private Product product;
		
		public FillupPanel() {
			field = new Field[ProductFields.length];

			field[item_no] = new DefaultField("0000");
			field[description] = new DefaultField("sample");
			field[lot_no] = new DefaultField("0000");
			field[date_added] = new DateField(Date.getCurrentDate().toString(), false) {
				private static final long serialVersionUID = -4048055821873558219L;
				@Override
				public void onSetDate(Date date) {
					syncronize_date_fields(date_added, date);
				}
			};
			field[exp_date] = new DateField("yyyy-mm-dd", true) {
				private static final long serialVersionUID = 5467829186052137522L;
				@Override
				public void onSetDate(Date date) {
					syncronize_date_fields(exp_date, date);
				}
			};
			field[brand] = new DefaultField("xxxx");
			field[qty] = new QtyField("0","0");
			field[uom] = new UomField("set");
			field[cost] = new DecimalField("0.00");
			field[unit_price] = new UnitPriceField("0.00");
			field[discount] = new DiscountField("0","%");
			field[unit_amount] = new DecimalField("0.00");
			
			int i;
			for(i=0; i<field.length; i++) {				
				add((JComponent)field[i]);
			}

			((DefaultField)field[item_no]).setCharacterLimit(8);
			((DefaultField)field[description]).setCharacterLimit(64);
			((DefaultField)field[lot_no]).setCharacterLimit(8);
			((DefaultField)field[brand]).setCharacterLimit(32);

			((DecimalField)field[unit_amount]).getTextField().setEditable(false);
		}
		public boolean prepareProduct(int parent_item_id, int parent_pack_id) {
			boolean prepared;
			
			int
			inv_id = -1,
			item_id = parent_item_id,
			pack_id = -1,
			price_id = -1;
			
			if(product != null) {
				inv_id = product.getInvId();
				item_id = product.getItem().getItemId();
				pack_id = product.getPackaging().getPackId();
				price_id = product.getPricing().getPriceId();
			}
			
			try {
				setProduct(new Product(
						inv_id, 
						new Item(
							item_id,
							(String)getValue(item_no),
							(String)getValue(description),
							(String)getValue(lot_no),
							(Date)getValue(date_added),
							(Date)getValue(exp_date),
							(String)getValue(brand)
						),
						new Packaging(
							pack_id, 
							(Quantity)getValue(qty),
							(Uom)getValue(uom),
							parent_pack_id
						),
						new Pricing(
							price_id,
							(Decimal)getValue(cost),
							(Decimal)getValue(unit_price),
							(Percentage)getValue(discount),
							(Decimal)getValue(unit_amount)
						),
						null //no remarks yet
				));
				prepared=true;
			}
			catch (Exception e) {
				Toolkit.getDefaultToolkit().beep();
				ABM_Pharma.getWindow().getDisplayPanel().floatMessage(e.getMessage());
				prepared=false;
			}
			
			return prepared;
		}
		public void setProduct(Product product) {
			this.product = product;
		}
		public Product getProduct() {
			return product;
		}
		public Object getValue(int index) throws Exception{
			return field[index].getValue();
		}
		public Field getField(int field_index) {
			return field[field_index];
		}
		public void displayProduct(Product product) {
			setProduct(product);
			
			try {
				getField(item_no).setValue(product.getItem().getItemNo());
				getField(description).setValue(product.getItem().getDescription());
				getField(lot_no).setValue(product.getItem().getLotNo());
				getField(date_added).setValue(product.getItem().getDateAdded());
				getField(exp_date).setValue(product.getItem().getExpDate());
				getField(brand).setValue(product.getItem().getBrand());
				getField(qty).setValue(product.getPackaging().getQty());
				getField(uom).setValue(product.getPackaging().getUom());
				getField(cost).setValue(product.getPricing().getCost());
				getField(unit_price).setValue(product.getPricing().getUnitPrice());
				getField(discount).setValue(product.getPricing().getDiscount());
				getField(unit_amount).setValue(product.getPricing().getUnitAmount());
			} catch (Exception e) {
				ABM_Pharma.getWindow().getDisplayPanel().floatMessage(e.getMessage());
			}
		}
		
		public class DefaultField extends StrictTextField implements Field{
			private static final long serialVersionUID = 3567456867200960971L;
			public DefaultField(String txt) {
				super(txt);
				setBackground(new Color(0,0,0,0));
				setForeground(new Color(0,0,0,0));
				includeNumbers(true);
			}
			@Override
			public Object getValue() {
				return getTextField().getText();
			}
			@Override
			public void setValue(Object value) {
				getTextField().setText((String)value);
			}
		}
		
		public class NumberField extends TextField implements Field{
			private static final long serialVersionUID = 2966679684696242206L;
			public NumberField(String txt) {
				super(txt);
				setBackground(new Color(0,0,0,0));
				setForeground(new Color(0,0,0,0));
			}
			@Override
			public Object getValue() {
				return Integer.parseInt(getTextField().getText());
			}
			@Override
			public void setValue(Object value) {
				getTextField().setText((String)value);
			}
		}
		
		public abstract class DateField extends DateLabel implements Field{
			private static final long serialVersionUID = 8236428170590423585L;
			private DatePicker date_picker;
			private boolean date_checking;
			
			public DateField(String date, boolean date_checking){
				super(date);
				create_datePicker(date);
				this.date_checking = date_checking;
				checkDate();
			}
			@Override
			public void onAction() {
				ABM_Pharma.getWindow().getStacksPanel().pushPanel(date_picker, Theme.shadow);
			}
			@Override
			public void checkDate() {
				if(date_checking) {
					super.checkDate();
				}
				else {
					setStatus(Status.NEUTRAL);
				}
			}
			@Override
			public Object getValue() throws Exception{
				if(getStatus() == Status.UNSET) {
					throw new Exception("Date is not set.");
				}
				else {
					return date_picker.getDate();
				}
			}
			@Override
			public void setValue(Object value) {
				Date date = ((Date)value);
				setDate(date);
				create_datePicker(date.toString());
			}
			public abstract void onSetDate(Date date);
			
			private void create_datePicker(String date) {
				date_picker = new DatePicker(date) {
					private static final long serialVersionUID = -2206607859866137188L;
					private final int panel_w=300, panel_h=300;
					@Override
					public void onPickCalendarDate(int yyyy, String month, int mm, String day, int dd) {
						ABM_Pharma.getWindow().getStacksPanel().popPanel();
						onSetDate(getDate());
						checkDate();
					}
					@Override
					public void onCloseDatePicker() {
						ABM_Pharma.getWindow().getStacksPanel().popPanel();
					}
					@Override
					public void setBounds(int x, int y, int width, int height) {
						super.setBounds(x + (width/2) - (panel_w/2), y + (height/2) - (panel_h/2), panel_w, panel_h);
					}
				};
			}
		}
		
		public class QtyField extends TwinIntegerField implements Field{
			private static final long serialVersionUID = -3497471385717877254L;
			
			public QtyField(String qty, String size){
				super(qty, size);
				setDivider('/');
				getLeftIntegerField().setCharacterLimit(4);
				getRightIntegerField().setCharacterLimit(4);
			}
			@Override
			public void setBounds(int x, int y, int width, int height) {
				super.setBounds(x, y, width - 10, height);
			}
			@Override
			public Object getValue() throws Exception {
				if(getLeftIntegerField().getTextField().getText().equals("")) {
					throw new Exception("Quantity field is empty");
				}
				else if(getRightIntegerField().getTextField().getText().equals("")) {
					throw new Exception("Quantity size is empty");
				}
				else if(getLeftIntegerField().getTextField().getText().equals("0") && isVisible()) {
					throw new Exception("Quantity field is zero");
				}
				else if(getRightIntegerField().getTextField().getText().equals("0") && isVisible()) {
					throw new Exception("Quantity size is zero");
				}
				else {
					return new Quantity(
						Integer.parseInt(getLeftIntegerField().getTextField().getText()),
						Integer.parseInt(getRightIntegerField().getTextField().getText())
					);
				}
			}
			@Override
			public void setValue(Object value) throws Exception {
				Quantity qty = (Quantity)value;
				getLeftIntegerField().getTextField().setText(qty.getQuantity() + "");
				getRightIntegerField().getTextField().setText(qty.getSize() + "");
			}
		}
		
		public class UomField extends Button implements Field{
			private static final long serialVersionUID = -7278476314453891020L;
			private SubPanelUOM panel_uom;
			
			public UomField(String label){
				super(label);
				panel_uom = new SubPanelUOM() {
					private static final long serialVersionUID = 6273765536069776416L;
					
					@Override
					public void onUomOk(Uom selectedUom) {
						updateFillupPanels(selectedUom);
						ABM_Pharma.getWindow().getStacksPanel().popPanel();
					}
					@Override
					public void onUomCancel() {
						ABM_Pharma.getWindow().getStacksPanel().popPanel();
					}
					private void updateFillupPanels(Uom selectedUom) {
						int depth = 0;
						
						Uom uom = selectedUom;
						FillupPanel fillup_panel; 

						try {
							while(uom != null) {
								fillup_panel = getFillupPanel(depth);
								fillup_panel.getField(SubPanelProduct.uom).setValue(uom);
								
								depth++;
								uom = uom.getSubUom();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						showFillupPanel(depth);
					}
				};
			}
			@Override
			public void onAction() {
				ABM_Pharma.getWindow().getStacksPanel().pushPanel(panel_uom, Theme.shadow);
			}
			@Override
			public void setBounds(int x, int y, int w, int h) {
				super.setBounds( x, y, w-10, h);
			}
			@Override
			public Object getValue() throws Exception{
				if(getLabel().getText().equals("set")) {
					throw new Exception("UOM is not set.");
				}
				else {
					return panel_uom.getSelectedUom();
				}
			}
			@Override
			public void setValue(Object value) {
				Uom uom = (Uom)value;
				panel_uom.setSelectedUom(uom);
				getLabel().setText(uom.getUnitType().toString());
			}
		}
		
		public class DecimalField extends NumericField implements Field{
			private static final long serialVersionUID = -1983438059373165334L;
			private static final String regex = "^\\d{1,3}(,\\d{3})*(\\.\\d{2})?$";
			private Pattern pattern;
			private Matcher matcher;
			
			public DecimalField(String txt) {
				super(txt);
				setBackground(new Color(0,0,0,0));
				setForeground(new Color(0,0,0,0));
				include(KeyEvent.VK_PERIOD);
				include(KeyEvent.VK_COMMA);

				pattern = Pattern.compile(regex);
			}
			@Override
			public Object getValue() throws Exception{
				matcher = pattern.matcher(getTextField().getText());
				
				if(matcher.matches()) {
					return new Decimal(new BigDecimal(getTextField().getText().replace(",", "")));
				}
				else {
					throw new Exception("Invalid decimal expression, (1,000,000.00) ");
				}
			}
			@Override
			public void setValue(Object value) {
				getTextField().setText(((Decimal)value).toString());
			}
		}
		
		public class PercentageField extends NumericField implements Field{
			private static final long serialVersionUID = 1998228066987443752L;

			public PercentageField(String txt, String pre_text) {
				super(txt, pre_text);
				setBackground(new Color(0,0,0,0));
				setForeground(new Color(0,0,0,0));
			}
			@Override
			public Object getValue() throws Exception{
				String regex = "%100|%([1-9]?[0-9])";
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(getTextField().getText());
				
				if(matcher.matches()) {
					return new Percentage(getTextField().getText());
				}
				else {
					throw new Exception("Invalid percentage expression, (%0 ~ %100) ");
				}
			}
			@Override
			public void setValue(Object value) {
				getTextField().setText(((Percentage)value).toString());
			}
		}

		private class UnitPriceField extends DecimalField implements Accountancy{
			private static final long serialVersionUID = -43436803068709785L;
			private Decimal unitPrice, unitAmount;
			private Percentage unitDiscount;
			
			public UnitPriceField(String txt) {
				super(txt);
				getTextField().addKeyListener(new KeyAdapter() {
					@Override
					public void keyReleased(KeyEvent e) {
						try {
							unitPrice    = (Decimal)getValue();
							unitDiscount = (Percentage)getField(discount).getValue();
							unitAmount   = calculateUnitAmount(unitPrice, unitDiscount);
							
							getField(unit_amount).setValue(unitAmount);
						} catch (Exception ex) {
							
						}
					}
				});
			}
		}

		private class DiscountField extends PercentageField implements Accountancy{
			private static final long serialVersionUID = -4439526515605889163L;
			private Decimal unitPrice, unitAmount;
			private Percentage unitDiscount;
			public DiscountField(String txt, String pre_text) {
				super(txt, pre_text);
				getTextField().addKeyListener(new KeyAdapter() {
					@Override
					public void keyReleased(KeyEvent e) {
						try {
							unitPrice    = (Decimal)getField(unit_price).getValue();
							unitDiscount = (Percentage)getValue();
							unitAmount   = calculateUnitAmount(unitPrice, unitDiscount);
							
							getField(unit_amount).setValue(unitAmount);
						} catch (Exception ex) {
							
						}
					}
				});
			}
		}
		
		public class FunctionButton extends Button implements Icons, Accountancy, UnitConverter{
			private static final long serialVersionUID = 931027830203147047L;
			
			public FunctionButton() {
				super(FunctionIcon);
			}
			@Override
			public void onAction() {
				try {
					Uom
					main_uom = (Uom)getFillupPanel(0).getField(Products.uom).getValue();
					Decimal 
					main_cost = (Decimal)getFillupPanel(0).getField(Products.cost).getValue(),
					main_unit_price = (Decimal)getFillupPanel(0).getField(Products.unit_price).getValue();

					Uom
					sub_uom = (Uom)getField(Products.uom).getValue();
					Percentage
					sub_discount = (Percentage)getField(Products.discount).getValue();

					BigDecimal
					a = main_cost.toBigDecimal(),
					b = main_unit_price.toBigDecimal(),
					c = getUnitScale(main_uom, sub_uom.getUnitType());
					
					Decimal
					sub_cost = new Decimal(a.multiply(c)),
					sub_unit_price = new Decimal(b.multiply(c)),
					sub_unit_amount = calculateUnitAmount(sub_unit_price, sub_discount);
					
					getField(Products.cost).setValue(sub_cost);
					getField(Products.unit_price).setValue(sub_unit_price);
					getField(Products.discount).setValue(sub_discount);
					getField(Products.unit_amount).setValue(sub_unit_amount);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void modify_fillup_panel(int i) {
		syncronize_text_fields(i);

		if(i != 0) {
			((QtyField)fillup_panel[i].getField(qty)).setVisible(false);
			((DateField)fillup_panel[i].getField(date_added)).setVisible(false);
			((DateField)fillup_panel[i].getField(exp_date)).setVisible(false);
		}
	}
	private void syncronize_text_fields(int i) {
		final JTextField txt_field[] = new JTextField[4];
		final int field_column[] = {item_no, description, lot_no, brand};

		txt_field[0] = ((DefaultField)fillup_panel[i].getField(item_no)).getTextField();
		txt_field[1] = ((DefaultField)fillup_panel[i].getField(description)).getTextField();
		txt_field[2] = ((DefaultField)fillup_panel[i].getField(lot_no)).getTextField();
		txt_field[3] = ((DefaultField)fillup_panel[i].getField(brand)).getTextField();
		
		for(int n=0; n<txt_field.length; n++) {
			final int N=n, M=i;
			txt_field[n].addKeyListener(new KeyAdapter() {
				private int m;
				@Override
				public void keyReleased(KeyEvent e) {
					for(m=0; m<fillup_panel.length; m++) {
						if(m != M) {
							((DefaultField)fillup_panel[m].getField(field_column[N])).getTextField().setText(txt_field[N].getText());
						}
					}
				}
			});

			if(i!=0) {
				txt_field[n].setVisible(false);
			}
		}		
	}
	private void syncronize_date_fields(int field_column, Date date) {
		((DateField)fillup_panel[0].getField(field_column)).setDate(date);
		((DateField)fillup_panel[1].getField(field_column)).setDate(date);
		((DateField)fillup_panel[2].getField(field_column)).setDate(date);
	}
}
