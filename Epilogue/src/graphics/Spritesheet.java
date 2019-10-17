package graphics;

import java.awt.image.BufferedImage;

/*
 * this class allows the cropping of an image and other functions needed for a SpriteSheet(a series of images on one file)
 */
public class Spritesheet {

	private BufferedImage sheet;
	
	//constructor takes in a buffered image
	public Spritesheet(BufferedImage sheet) {
		this.sheet = sheet;
	}
	
	//this method crop the buffered image
	public BufferedImage crop(int x, int y, int width, int height) {
		return sheet.getSubimage(x, y, width, height);
	}
	
}
