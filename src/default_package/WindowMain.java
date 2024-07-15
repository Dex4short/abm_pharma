package default_package;
import javax.swing.JFrame;

import default_package.admin.PanelAdmin;
import default_package.employee.PanelEmployee;
import default_package.login.PanelLogin;
import gui.DisplayPanel;
import gui.Panel;
import gui.StacksPanel;
import misc.enums.Role;
import misc.interfaces.Theme;

public class WindowMain extends JFrame{
	private static final long serialVersionUID = 3951944640222567255L;
	private DisplayPanel display_panel;
	private StacksPanel stacks_panel;
	
	public WindowMain() {
		setTitle("ABM System Pharma");
		
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
		stacks_panel.setOpaque(true);
		stacks_panel.setBackground(Theme.main_color[2]);
		stacks_panel.pushPanel(new PanelLogin() {
			private static final long serialVersionUID = 8576528911235392926L;
			@Override
			public void onLogin(Role role) {
				if(role == Role.ADMIN) {
					initializePanelAdmin();
				}
				else if(role == Role.EMPLOYEE) {
					initializePanelEmployee();
				}
			}
		});
		display_panel.add(stacks_panel);
		
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
