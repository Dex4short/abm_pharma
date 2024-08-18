package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JPasswordField;

import misc.interfaces.Theme;

public abstract class PasswordField extends JPasswordField{
	private static final long serialVersionUID = -1457422044862344052L;
	private Graphics2D g2d;
	private final int 
	a=5,//top_bottom
	b=10;//left_right
	private String label;
	private int password_limit;

	public PasswordField(int password_limit) {
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder(a, b, a, b));
		setBackground(Theme.gray_shade[0]);
		setForeground(Theme.gray_shade[1]);
		setFont(Theme.h1);

		setLabel("Password");
		setPasswordLimit(password_limit);
		
		addKeyListener(new KeyAdapter() {
			private char password[];
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					onEnter();
					repaint();
				}
			}
			@Override
			public void keyTyped(KeyEvent e) {
				super.keyTyped(e);
				
				password = getPassword();
				if(password.length > getPasswordLimit()) {
					String pass = "";
					for(int i=0; i<getPasswordLimit(); i++) {
						pass += password[i];
					}
					setText(pass);
				}
			}
		});
	}
	@Override
	public void paint(Graphics g) {
		g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setColor(getBackground());
		g2d.fillRect(0, 0, getWidth(), getHeight());
		g2d.setClip(0, 0, getWidth(), getHeight());
		
		g2d.setColor(getForeground());
		if(getPassword().length == 0){
			g2d.drawString(label, b, a + g2d.getFontMetrics().getAscent() + g2d.getFontMetrics().getLeading());
		}
		
		super.paint(g2d);
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public void setPasswordLimit(int password_limit) {
		this.password_limit = password_limit;
	}
	public String getLabel() {
		return label;
	}
	public int getPasswordLimit() {
		return password_limit;
	}
	public void clearPassword() {
		setText("");
		repaint();
	}
	public abstract void onEnter();
}
