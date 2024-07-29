package misc.interfaces;

import gui.Button;

public interface UICustoms extends Theme{

	public default void custom_button_appearance(Button btn){
		btn.setBackground(main_color[2]);
		btn.setForeground(doc_color[0]);
		btn.getLabel().setForeground(doc_color[0]);
		btn.setHighlight(main_color[0]);
		btn.setPressedColor(main_color[2].darker());
		btn.setArc(30);
	}
}
