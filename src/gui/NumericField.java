package gui;

import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.function.Consumer;


public class NumericField extends TextField{
	private static final long serialVersionUID = 8046081737338819617L;
	private int code, column; 
	private ArrayList<Integer> include;
	private boolean accept, released;
	private String str;

	public NumericField(String txt) {
		super(txt);
		numeric_field(txt);
	}
	public NumericField(String txt, String pre_text) {
		super(txt, pre_text);
		numeric_field(pre_text + txt);
	}
	public NumericField(String txt, String pre_text, String sub_text) {
		super(txt, pre_text, sub_text);
		numeric_field(pre_text + txt);
	}
	public void include(int keycode) {
		include.add(Integer.valueOf(keycode));
	}
	public void except(int keycode) {
		include.remove(Integer.valueOf(keycode));
	}
	private void numeric_field(String txt) {
		str = txt;
		column = 0;
		released = true;
		
		include = new ArrayList<Integer>();
		include.add(KeyEvent.VK_BACK_SPACE);
		include.add(KeyEvent.VK_DELETE);
		include.add(KeyEvent.VK_LEFT);
		include.add(KeyEvent.VK_RIGHT);
		
		getTextField().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(released) {
					code = e.getKeyCode();
					
					accept = (code>=KeyEvent.VK_0) && (code<=KeyEvent.VK_9);
					include.forEach(new Consumer<Integer>() {
						@Override
						public void accept(Integer keyCode) {
							accept = accept || (code==keyCode.intValue());
						}
					});

					released = false;
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if(!released) {
					if(accept) {
						str = getTextField().getText();
						column = getTextField().getCaretPosition();
						
						getTextField().setText(str);
						
						if(column > str.length()) {
							column = str.length();
						}
						getTextField().setCaretPosition(column);
					}
					else{
						getTextField().setText(str);
						Toolkit.getDefaultToolkit().beep();
						getTextField().setCaretPosition(column);
					}
					
					released = true;
				}
			}
		});
	
	}
}
