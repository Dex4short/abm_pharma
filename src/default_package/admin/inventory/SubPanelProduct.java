package default_package.admin.inventory;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JComponent;
import javax.swing.JLabel;

import customs.DateLabel;
import default_package.ABM_Pharma;
import gui.ActionPanel;
import gui.Button;
import gui.DatePicker;
import gui.HorizontalPanel;
import gui.NumericField;
import gui.TwinIntegerField;
import gui.TextField;
import misc.interfaces.Field;
import misc.interfaces.InventoryConstants;
import misc.interfaces.Theme;
import misc.objects.Date;
import misc.objects.Decimal;
import misc.objects.Percentage;
import misc.objects.Product;
import misc.objects.Quantity;
import misc.objects.Uom;

public abstract class SubPanelProduct extends ActionPanel implements InventoryConstants{
	private static final long serialVersionUID = 4129364051265662075L;
	private final int titled_panel_h = 160;
	private HorizontalPanel header_panel,fillup_panel;
	private JLabel lbl[];
	private Field field[];
	private int i;
	private Product product;
	
	public SubPanelProduct(String panel_title) {
		super(panel_title);
		
		header_panel = new HorizontalPanel();
		fillup_panel = new HorizontalPanel();
		fillup_panel.setBackground(Theme.opacity(Theme.gray_shade[0], 0.25f));
		fillup_panel.setForeground(new Color(0,0,0,0));
		
		String str[] = InventoryFields;
		
		lbl = new JLabel[str.length];
		field = new Field[str.length];
		
		field[item_no] = new DefaultField("0000");
		field[description] = new DefaultField("sample");
		field[lot_no] = new DefaultField("0000");
		field[date_added] = new DateField(Date.getCurrentDate().toString(), false);
		field[exp_date] = new DateField("yyyy-mm-dd", true);
		field[brand] = new DefaultField("xxxx");
		field[qty] = new QtyField("0","0");
		field[uom] = new UomField("set");
		field[cost] = new DecimalField("0.00");
		field[unit_price] = new DecimalField("0.00");
		field[discount] = new PercentageField("0","%");
		field[unit_amount] = new DecimalField("0.00");
		
		for(i=0; i<str.length; i++) {
			lbl[i] = new JLabel(str[i]);
			lbl[i].setOpaque(false);
			lbl[i].setFont(Theme.h1);
			lbl[i].setForeground(Theme.gray_shade[1]);
			header_panel.add(lbl[i]);
			
			fillup_panel.add((JComponent)field[i]);
		}
		
		getContentPane().add(header_panel);
		getContentPane().add(fillup_panel);
		
	}
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(30, (height/2) - (titled_panel_h/2), width-60, titled_panel_h);
	}
	@Override
	public void onResizeContentPane(int w, int h) {
		header_panel.setBounds(0, 0, w, 30);
		fillup_panel.setBounds(0, 40, w, 30);
	}
	@Override
	public void onOk() {
		try {
			setProduct(new Product(
					(String)getValue(item_no),
					(String)getValue(description),
					(String)getValue(lot_no),
					(Date)getValue(date_added),
					(Date)getValue(exp_date),
					(String)getValue(brand),
					(Quantity)getValue(qty),
					(Uom)getValue(uom),
					(Decimal)getValue(cost),
					(Decimal)getValue(unit_price),
					(Percentage)getValue(discount),
					(Decimal)getValue(unit_amount)
			));
			onProductOk(getProduct());
		}
		catch (Exception e) {
			Toolkit.getDefaultToolkit().beep();
			ABM_Pharma.getWindow().getDisplayPanel().floatMessage(e.getMessage());
			e.printStackTrace();
		}
	}
	@Override
	public void onCancel() {
		onProductCancel();
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
	public void displayProduct(int item_no) {
		//TODO
	}
	public abstract void onProductOk(Product product);
	public abstract void onProductCancel();
	
	public class DefaultField extends TextField implements Field{
		private static final long serialVersionUID = 2966679684696242206L;
		public DefaultField(String txt) {
			super(txt);
			setBackground(new Color(0,0,0,0));
			setForeground(new Color(0,0,0,0));
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
	
	public class DateField extends DateLabel implements Field{
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
		public void onCheckDate(Status status) {
			//TODO
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
			create_datePicker((String)value);
		}
		public void create_datePicker(String date) {
			date_picker = new DatePicker(date) {
				private static final long serialVersionUID = -2206607859866137188L;
				private final int panel_w=300, panel_h=300;
				@Override
				public void onPickCalendarDate(int yyyy, String month, int mm, String day, int dd) {
					ABM_Pharma.getWindow().getStacksPanel().popPanel();
					getLabel().setText(getDate().toString());
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
				private final int panel_w=400, panel_h=300;
				
				@Override
				public void onUomOk(Uom selectedUom) {
					getLabel().setText(selectedUom.getUnitName());
					ABM_Pharma.getWindow().getStacksPanel().popPanel();
				}
				@Override
				public void onUomCancel() {
					ABM_Pharma.getWindow().getStacksPanel().popPanel();
				}
				@Override
				public void setBounds(int x, int y, int width, int height) {
					super.setBounds(x + (width/2) - (panel_w/2), y + (height/2) - (panel_h/2), panel_w, panel_h);
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
			panel_uom.setSelectedUom((Uom)value);
		}
	}
	
	public class DecimalField extends NumericField implements Field{
		private static final long serialVersionUID = -1983438059373165334L;
		
		public DecimalField(String txt) {
			super(txt);
			setBackground(new Color(0,0,0,0));
			setForeground(new Color(0,0,0,0));
			include(KeyEvent.VK_PERIOD);
			include(KeyEvent.VK_COMMA);
		}
		@Override
		public Object getValue() throws Exception{
			String regex = "^\\d{1,3}(,\\d{3})*(\\.\\d{2})?$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(getTextField().getText());
			
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
}
