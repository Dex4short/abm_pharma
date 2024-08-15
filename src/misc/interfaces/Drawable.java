package misc.interfaces;

import java.awt.Graphics;
import java.awt.Graphics2D;

public interface Drawable {
	public void draw(Graphics2D g2d, int x, int y, int w, int h);
	public static void drawRoundRect(Graphics g2d,int x, int y, int w, int h, int arc1, int arc2) {
		g2d.drawArc(x, y, arc1, arc1, 90, 90);
		g2d.drawArc(x+(w-arc2-1), y, arc2, arc2, 90, -90);
		g2d.drawLine(x+(int)(arc1*0.50), y, x + (w - (int)(arc2*0.50)), y);
		g2d.drawLine(x, y + (int)(arc1*0.50), x, y+h);
		g2d.drawLine(x+(w-1), y + (int)(arc2*0.50), x+(w-1), y+h);
	}
	public static void fillRoundRect(Graphics g2d,int x, int y, int w, int h, int arc1, int arc2){
		g2d.fillArc(x, y, arc1, arc1, 90, 90);
		g2d.fillArc(x+(w-arc2), y, arc2, arc2, 90, -90);
		g2d.fillRect(x+(int)(arc1*0.50), y, w - ((int)(arc1*0.50) + (int)(arc2*0.50)), h);
		g2d.fillRect(x, y+(int)(arc1*0.50), (int)(arc1*0.50), h - (int)(arc1*0.50));
		g2d.fillRect(x + (w - (int)(arc2*0.50)), y + (int)(arc2*0.50), (int)(arc2*0.50), h-(int)(arc2*0.50));
	}
}
