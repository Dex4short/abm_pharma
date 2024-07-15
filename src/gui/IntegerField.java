package gui;

import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class IntegerField extends TextField{
	private static final long serialVersionUID = 8046081737338819617L;
	private int code, caret_position, n, include[] = {KeyEvent.VK_BACK_SPACE, KeyEvent.VK_DELETE, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT};
	private boolean accept, released;
	private String str;

	public IntegerField(String txt) {
		super(txt);
		str = txt;
		caret_position = 0;
		released = true;
		
		getTextField().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(released) {
					code = e.getKeyCode();
					
					accept = (code>=KeyEvent.VK_0) && (code<=KeyEvent.VK_9);
					for(n=0; n<include.length; n++) {
						accept = accept || (code==include[n]);
					}

					released = false;
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if(!released) {
					accept = accept && getTextField().getText().length()!=0;

					if(accept) {
						str = getTextField().getText();
						caret_position = getTextField().getCaretPosition();
					}
					else{
						getTextField().setText(str);
						getTextField().setCaretPosition(caret_position);
						Toolkit.getDefaultToolkit().beep();
					}
					
					released = true;
				}
			}
		});
	}
}
