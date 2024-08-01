package default_package.login;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import database.mysql.MySQL_Security;
import gui.Button;
import gui.Link;
import gui.Panel;
import gui.PasswordField;
import misc.enums.SecurityRole;
import misc.interfaces.Theme;

public abstract class PanelLogin extends Panel{
	private static final long serialVersionUID = -3149306316190451372L;
	private Graphics2D g2d;
	private final Image logo;
	private final int logoSize;
	private PasswordField pass;
	private Button login_btn;
	private Link cancel_link;
	
	public PanelLogin(){
		setLayout(null);
		setOpaque(false);

		logo = Toolkit.getDefaultToolkit().getImage("res/ABM LOGO.png");
		logoSize = 200;
		
		pass = new PasswordField(16) {
			private static final long serialVersionUID = -3023589574972491238L;
			@Override
			public void onEnter() {
				new Thread() {
					public void run() {
						inputPassword(getPassword());
					}
				}.start();
			}
		};
		add(pass);

		login_btn = new Button("Log In") {
			private static final long serialVersionUID = 1376546956761977108L;
			@Override
			public void onAction() {
				pass.onEnter();
			}
		};
		add(login_btn);

		cancel_link = new Link("Cancel or Exit") {
			private static final long serialVersionUID = 7895944149267731139L;
			@Override
			public void directory() {
				System.exit(0);
			}
		};
		add(cancel_link);

		setSize(300, 400);
	}
	@Override
	public void paint(Graphics g) {
		g2d = (Graphics2D)g;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		g2d.setColor(Theme.doc_color[0]);
		g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
		
		g2d.drawImage(logo, (getWidth()/2) - (logoSize/2), (getHeight()/3) - (logoSize/2), this);
		
		super.paint(g2d);
	}
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds((width/2) - (getWidth()/2), (height/2) - (getHeight()/2), 300, 400);
		pass.setBounds((getWidth()/2)-80, (int)(getHeight()*0.65) - 13, 160, 25);
		login_btn.setBounds((getWidth()/2)-50, (int)(getHeight()*0.75) - 13, 100, 25);
		cancel_link.setBounds((getWidth()/2) - 50, (int)(getHeight()*0.82) - 13, 100, 25);
	}
	private void inputPassword(char password[]) {
		SecurityRole role = MySQL_Security.getAccess(password);
		pass.clearPassword();
		switch (role) {
		case ADMIN:
			pass.setLabel("Please wait...");
			break;
		case EMPLOYEE:
			pass.setLabel("Please wait...");
			break;
		case NONE:
			pass.setLabel("Wrong Password");
			break;
		}
		
		onLogin(role);
	}
	public abstract void onLogin(SecurityRole role);
}
