package graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

// class that contains global customization features
public class CT {
	
	// flips a buffered image
	public static BufferedImage flip(BufferedImage sprite) {
		BufferedImage img = new BufferedImage(sprite.getWidth(), sprite.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for (int xx = sprite.getWidth() - 1; xx > 0; xx--) {
			for (int yy = 0; yy < sprite.getHeight(); yy++) {
				img.setRGB(sprite.getWidth() - xx, yy, sprite.getRGB(xx, yy));
			}
		}

		return img;
	}
	
	// flips a buffered image array
	public static BufferedImage[] flip(BufferedImage[] sprite) {
		
		BufferedImage[] anim = new BufferedImage[sprite.length];
		
		for(int i = 0; i < sprite.length; i++) {
			anim[i] = new BufferedImage(sprite[i].getWidth(), sprite[i].getHeight(), BufferedImage.TYPE_INT_ARGB);
			for (int xx = sprite[i].getWidth() - 1; xx > 0; xx--) {
				for (int yy = 0; yy < sprite[i].getHeight(); yy++) {
					anim[i].setRGB(sprite[i].getWidth() - xx, yy, sprite[i].getRGB(xx, yy));
				}
			}
		}
		
		return anim;
	}
	
	public static BufferedImage rotateClockwise90(BufferedImage src) {
	    int width = src.getWidth();
	    int height = src.getHeight();

	    BufferedImage dest = new BufferedImage(height, width, src.getType());

	    Graphics2D graphics2D = dest.createGraphics();
	    graphics2D.translate((height - width) / 2, (height - width) / 2);
	    graphics2D.rotate(Math.PI / 2, height / 2, width / 2);
	    graphics2D.drawRenderedImage(src, null);

	    return dest;
	}
	
	// include start, include end
	public static int random(int start, int end) {
		
		return (int)(Math.random()*(end - start + 1)) + start;
				
	}
	
	// bounds drawing
	/*
	g.setColor(Color.blue);
	Graphics2D g2d = (Graphics2D) g;
	g2d.draw(getBounds());
	g.setColor(Color.GREEN);
	g2d.draw(attackBound());
	*/
	
}

