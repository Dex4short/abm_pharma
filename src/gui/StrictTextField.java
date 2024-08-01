package gui;

import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.function.Consumer;

public class StrictTextField extends TextField{
	private static final long serialVersionUID = -7105058236421731237L;
	private ArrayList<Integer> include;
	private int code, column, characterLimit;
	private boolean accept, released, isNumbersIncluded, isSmallLettersIncluded, isCapitalLettersIncluded, isSpecialCharactersIncluded;
	private String str;
	
	public StrictTextField(String txt) {
		super(txt);
		strict_field(txt);
	}
	public StrictTextField(String txt, String pre_text) {
		super(txt, pre_text);
		strict_field(pre_text + txt);
	}
	public StrictTextField(String txt, String pre_text, String sub_text) {
		super(txt, pre_text, sub_text);
		strict_field(pre_text + txt);
	}
	public void setCharacterLimit(int characterLimit) {
		this.characterLimit = characterLimit;
	}
	public int getCharacterLimit() {
		return characterLimit;
	}
	public boolean isNumbersIncluded() {
		return isNumbersIncluded;
	}
	public boolean isSmallLettersIncluded() {
		return isSmallLettersIncluded;
	}
	public boolean isCapitalLettersIncluded() {
		return isCapitalLettersIncluded;
	}
	public boolean isLettersIncluded() {
		return isSmallLettersIncluded && isCapitalLettersIncluded;
	}
	public boolean isSpecialCharactersIncluded() {
		return isSpecialCharactersIncluded;
	}
	public void include(int keycode) {
		include.add(Integer.valueOf(keycode));
	}
	public void includeNumbers(boolean isNumbersIncluded) {
		this.isNumbersIncluded = isNumbersIncluded;
	}
	public void includeSmallLetters(boolean isSmallLettersIncluded) {
		this.isSmallLettersIncluded = isSmallLettersIncluded;
	}
	public void includeCapitalLetters(boolean isCapitalLettersIncluded) {
		this.isCapitalLettersIncluded = isCapitalLettersIncluded;
	}
	public void includeLetters(boolean isLettersIncluded) {
		includeSmallLetters(isLettersIncluded);
		includeCapitalLetters(isLettersIncluded);
	}
	public void includePecialCharacters(boolean isSpecialCharactersIncluded) {
		this.isSpecialCharactersIncluded = isSpecialCharactersIncluded;
	}
	public void except(int keycode) {
		include.remove(Integer.valueOf(keycode));
	}
	
	private void strict_field(String txt) {
		str = txt;
		column = 0;
		released = true;
		
		include = new ArrayList<Integer>();
		include.add(KeyEvent.VK_BACK_SPACE);
		include.add(KeyEvent.VK_DELETE);
		include.add(KeyEvent.VK_LEFT);
		include.add(KeyEvent.VK_RIGHT);
		
		includeLetters(true);
		setCharacterLimit(16);
		
		getTextField().addKeyListener(new KeyAdapter() {
			private boolean isLimitReached;
			@Override
			public void keyPressed(KeyEvent e) {				
				if(released) {
					code = e.getKeyCode();
					
					if(isNumbersIncluded()) {
						accept = accept || ((code>=KeyEvent.VK_0) && (code<=KeyEvent.VK_9));
					}
					
					if(isSmallLettersIncluded()) {
						accept = accept || ((code>=97) && (code<=122));
					}
					
					if(isCapitalLettersIncluded()) {
						accept = accept || ((code>=65) && (code<=90));
					}
					
					if(isSpecialCharactersIncluded()) {
						accept = accept || (
							((code>=33) && (code<=47)) || 
							((code>=58) && (code<=64)) ||
							((code>=91) && (code<=96)) ||
							((code>=123) && (code<=126))
						);
					}
					
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
				isLimitReached = getTextField().getText().length() > getCharacterLimit();
				
				if(isLimitReached) {
					released = false;
					accept = false;
				}
				
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
					accept = false;
				}
			}
		});
	
	}
}
