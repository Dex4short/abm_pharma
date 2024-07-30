package misc.interfaces;

import java.awt.Color;
import java.awt.Font;

public interface Theme {
	public static Color
	main_color[] = {
			new Color(5, 145, 255),
			new Color(8, 196, 234),
			new Color(1,  34, 131),
			new Color(2, 60, 255)
	},
	doc_color[] = {
			Color.white,
			Color.black
	},
	gray_shade[] = {
			new Color(217,217,217),//lighter
			new Color(135,135,135),
			new Color(94, 94, 94),
			new Color(74, 74, 74),
			new Color(64, 64, 64)//darker
	},
	shadow = new Color( 0, 0, 0, 100);
	public static Font
	h1 = new Font("Arial", Font.BOLD, 11),
	h2 = new Font("Arial Rounded MT", Font.BOLD, 12);
	
	public static Color opacity(Color color, float opacity) {
		return new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(255 * opacity));
	}
}
