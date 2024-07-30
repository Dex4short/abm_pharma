package default_package;

import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import default_package.admin.PanelAdmin;
import default_package.employee.PanelEmployee;
import default_package.login.PanelLogin;
import gui.DisplayPanel;
import gui.Panel;
import gui.StacksPanel;
import misc.enums.SecurityRole;
import misc.interfaces.Theme;

public class WindowMain extends JFrame{
	private static final long serialVersionUID = 3951944640222567255L;
	private DisplayPanel display_panel;
	private StacksPanel stacks_panel;
	
	public WindowMain() {
		setTitle("ABM System Pharma");
		setBackground(Theme.main_color[2]);
		
		display_panel = new DisplayPanel();
		setContentPane(display_panel);
		
		stacks_panel = new StacksPanel() {
			private static final long serialVersionUID = 1L;
			@Override
			public void onPushPanel(Panel panel) {
				
			}
			@Override
			public void onPopPanel(Panel panel) {
				
			}
		};
		stacks_panel.setBackground(Theme.main_color[2]);
		stacks_panel.pushPanel(new PanelLogin() {
			private static final long serialVersionUID = 8576528911235392926L;
			@Override
			public void onLogin(SecurityRole role) {
				if(role == SecurityRole.ADMIN) {
					initializePanelAdmin();
				}
				else if(role == SecurityRole.EMPLOYEE) {
					initializePanelEmployee();
				}
			}
		});
		display_panel.add(stacks_panel);
		
		addWindowStateListener(new WindowStateListener() {
			int t;
			@Override
			public void windowStateChanged(WindowEvent e) {
				t=0;
				new Timer().scheduleAtFixedRate(new TimerTask() {
					@Override
					public void run() {
						repaint();
						
						t++;
						if(t==2) {
							cancel();
						}
					}
				}, 0, 50);
			}
		});
	}
	private final void initializePanelAdmin() {
		stacks_panel.popPanel();
		stacks_panel.pushPanel(new PanelAdmin());
	}
	private final void initializePanelEmployee() {
		stacks_panel.popPanel();
		stacks_panel.pushPanel(new PanelEmployee());
	}
	public StacksPanel getStacksPanel() {
		return stacks_panel;
	}
	public DisplayPanel getDisplayPanel() {
		return display_panel;
	}
}
