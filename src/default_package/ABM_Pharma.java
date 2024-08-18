package default_package;

import javax.swing.JFrame;


public class ABM_Pharma{
	private static WindowMain w;

	public static void main(String[]args) {
		w = new WindowMain();
		w.setSize(800, 600);
		w.setLocationRelativeTo(null);
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		w.setExtendedState(JFrame.MAXIMIZED_BOTH);
		w.setVisible(true);
	}
	public static WindowMain getWindow() {
		return w;
	}
}
