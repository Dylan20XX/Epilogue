package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

/*
 * class that draws a customized text by taking in advanced properties
 */
public class CustomTextWritter {

	// method takes in a graphics object, text, x and y position, center variable,
	// color, and font
	public static void drawString(Graphics g, String text, int xPos, int yPos, boolean center, Color c, Font font) {

		g.setColor(c);
		g.setFont(font);
		int x = xPos;
		int y = yPos;

		// center the text using a FontMetrics
		if (center) {
			FontMetrics fm = g.getFontMetrics(font);
			x = xPos - fm.stringWidth(text) / 2;
			y = (yPos - fm.getHeight() / 2) + fm.getAscent();
		}
		g.drawString(text, x, y);

	}

}
