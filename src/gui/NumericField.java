package gui;


public class NumericField extends StrictTextField{
	private static final long serialVersionUID = 8046081737338819617L;


	public NumericField(String txt) {
		super(txt);
		numeric_field();
	}
	public NumericField(String txt, String pre_text) {
		super(txt, pre_text);
		numeric_field();
	}
	public NumericField(String txt, String pre_text, String sub_text) {
		super(txt, pre_text, sub_text);
		numeric_field();
	}
	
	private void numeric_field() {
		includeNumbers(true);
		includeLetters(false);
	}
	public int getNumber() {
		String str = getTextField().getText();
		int 
		start = getPreText().length(),
		end = str.length();
		
		str = str.substring(start, end);
		
		return Integer.parseInt(str);
	}
	public void setNumber(int n) {
		getTextField().setText(getPreText() + n);
	}
}
