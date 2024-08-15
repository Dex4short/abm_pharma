package gui;


import misc.interfaces.Icons;
import misc.interfaces.Theme;

public abstract class CheckBox extends IconedButton{
	private static final long serialVersionUID = 2035244685280448808L;
	
	public CheckBox() {
		super(Icons.CheckIcon, "");
		checkBox();
	}
	public CheckBox(String label) {
		super(Icons.CheckIcon, label);
		checkBox();
	}
	private final void checkBox() {
		setArc(1);
		setBackground(Theme.doc_color[0]);
		setBorderColor(Theme.main_color[2]);
		setHighlight(Theme.main_color[0]);
		setPressedColor(getBorderColor());
	}
	@Override
	public void onPressButton() {
		setPressed(!isPressed());
		onCheck(isPressed());
	}
	@Override
	public void onReleaseButton() {
		//removes instructions
	}
	@Override
	public void onAction() {
		//unused
	}
	public abstract void onCheck(boolean toggled);
}
