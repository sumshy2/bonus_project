package bonus_project;
/*
 * Dung Le
 * Sumedh Shah
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;

class DrawCircle extends JPanel{
	private int x;
	private int y;
	private Color c;
	public DrawCircle(int xa, int ya, Color color)
	{
		x = xa;
		y = ya;
		c = color;
	}

	public void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		Ellipse2D.Double circle = new Ellipse2D.Double(1, 1, x, y);
		g2d.setColor(c);
		g2d.fill(circle);
	}
}
