package default_package.admin.inventory;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JLabel;

import gui.ActionPanel;
import gui.HorizontalPanel;
import gui.IterationBox;
import gui.ListPanel;
import gui.NumericField;
import misc.interfaces.Theme;
import misc.interfaces.UomPresets;
import misc.objects.Uom;

public abstract class SubPanelUOM extends ActionPanel implements UomPresets{
	private static final long serialVersionUID = 8885051793237377183L;
	private final int panel_w, panel_h, field_h;
	private HorizontalPanel header_panel, field_panel;
	private MainUomPanel main_uom;
	private SubUomPanel sub_uom;
	private Uom selected_uom;

	public SubPanelUOM() {
		super("Unit of Measure");
		
		panel_w = 400;
		panel_h = 300;
		field_h = 30;
				
		header_panel = new HorizontalPanel();
		header_panel.add(createLabel("Main UOM", Theme.gray_shade[1]));
		header_panel.add(createLabel("Sub UOM", Theme.gray_shade[1]));
		getContentPane().add(header_panel);
		
		field_panel = new HorizontalPanel();
		field_panel.setBackground(Theme.gray_shade[0]);
		field_panel.add(createLabel("box", Theme.doc_color[1]));
		field_panel.add(createLabel(": none", Theme.doc_color[1]));
		getContentPane().add(field_panel);
		
		main_uom = new MainUomPanel();
		getContentPane().add(main_uom);

		sub_uom = new SubUomPanel();
		getContentPane().add(sub_uom);

		setSelectedUom(Uom_Preset[0]);
	}
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x + (width/2) - (panel_w/2), y + (height/2) - (panel_h/2), panel_w, panel_h);
	}
	@Override
	public void onResizeContentPane(int w, int h) {
		header_panel.setBounds(0, 0, w, field_h);
		field_panel.setBounds(0, field_h + getMargine(), w, field_h);
		main_uom.setBounds(0, (field_h + getMargine()) * 2, (w/2) - (getMargine()/2), h - ((field_h + getMargine()) *2) - getMargine());
		sub_uom.setBounds((w/2) + (getMargine()/2), (field_h + getMargine()) * 2, (w/2) - (getMargine()/2), h - ((field_h + getMargine()) *2) - getMargine());
	}
	@Override
	public void onOk() {
		onUomOk(getSelectedUom());
	}
	@Override
	public void onCancel() {
		onUomCancel();
	}
	public Uom getSelectedUom() {
		prepareIds(selected_uom);
		return selected_uom;
	}
	public void setSelectedUom(Uom uom) {
		JLabel uom_lbl1 = (JLabel)field_panel.getComponent(0);
		JLabel uom_lbl2 = (JLabel)field_panel.getComponent(1);
		
		String
		main_uom = uom.getUnitType().toString(),
		sub_uoms = "";
		
		Uom uom_node = uom;
		
		sub_uom.removeAllItems();
		while(uom_node.getSubUom() != null) {
			uom_node = uom_node.getSubUom();
			
			sub_uoms += ": " + uom_node.getUnitType() + " ";
			sub_uom.addSubUomItem(uom_node);
			
		}
		
		uom_lbl1.setText(main_uom);
		if(sub_uoms.equals("")) {
			sub_uom.addItem("none");
			uom_lbl2.setText(": none");
		}
		else {
			uom_lbl2.setText(sub_uoms);
		}
		
		selected_uom = uom;
	}
	
	public abstract void onUomOk(Uom selectedUom);
	public abstract void onUomCancel();

	private final JLabel createLabel(String str, Color color) {
		JLabel lbl = new JLabel(str);
		lbl.setFont(Theme.h1);
		lbl.setForeground(color);
		return lbl;
	}
	
	public class MainUomPanel extends ListPanel{
		private static final long serialVersionUID = 8252498005827473422L;
		
		public MainUomPanel(){
			Uom uom;
			
			String
			main_uom = "",
			sub_uoms = "";
			
			int i;
			for(i=0; i<Uom_Preset.length; i++) {
				uom = Uom_Preset[i];
				
				main_uom = uom.getUnitType().toString();
				while(uom.getSubUom() != null) {
					uom = uom.getSubUom();
					sub_uoms += (": " + uom.getUnitType() + " ");
				}
				addItem(main_uom + " " + (sub_uoms.equals("") ? "" : sub_uoms));
				
				main_uom = "";
				sub_uoms = "";
			}
		}
		@Override
		public void onSelectItem(int n) {
			Uom uom = Uom_Preset[n];	
			setSelectedUom(uom);
		}
	}
	
	public class SubUomPanel extends ListPanel{
		private static final long serialVersionUID = 8252498005827473422L;
		{
			addItem("none");
			setSelectionEnabled(false);
		}
		@Override
		public void onSelectItem(int n) {
			
		}
		public void addSubUomItem(Uom subUom) {
			addItem(new SubUomItem(subUom, subUom.getUnitSize()));
		}
		
		private class SubUomItem extends HorizontalPanel{
			private static final long serialVersionUID = -5248387306150387785L;
			private JComponent unitSize_field;
			
			public SubUomItem(Uom subUom, int size_value) {
				setMargine(2);
				setForeground(new Color(0,0,0,0));
				setBackground(getForeground());
				
				JLabel unitName_label = new JLabel(subUom.getUnitType().toString());
				unitName_label.setOpaque(false);
				unitName_label.setFont(Theme.h1);
				unitName_label.setForeground(Theme.doc_color[1]);
				add(unitName_label);
				
				if(size_value >= 0) {
					unitSize_field = new IterationBox(Uom_Sizes, (size_value/2) - 2) {
						private static final long serialVersionUID = 1L;
						@Override
						public void onIncrement(String selectedIterationValue) {
							subUom.setUnitSize(Integer.parseInt(selectedIterationValue));
						}
						@Override
						public void onDecrement(String selectedIterationValue) {
							subUom.setUnitSize(Integer.parseInt(selectedIterationValue));
						}
					};
				}
				else {
					NumericField num_field = new NumericField("10");
					num_field.addKeyListener(new KeyAdapter() {
						@Override
						public void keyReleased(KeyEvent e) {
							subUom.setUnitSize(num_field.getNumber());
						}
					});
					num_field.setArc(5);
					num_field.setCharacterLimit(4);
					subUom.setUnitSize(10);
					unitSize_field = num_field;
				}
				
				add(unitSize_field);
			}
		}
	}
}
