package utils;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import audio.AudioPlayer;
import graphics.Assets;

/*
 * this class is a customized button so we don't have to use the ugly JButton (facts)
 * 
 * subclass of UIObject
 */
public class ImageButton extends UIObject {

	// properties of the image button
	private BufferedImage[] images;
	private BufferedImage image;
	private ClickListener clicker;
	
	private boolean btnAudio = true;
	
	int type = 0;
	// type 0 - normal hovering
	// type 1 - crafting recipe
	
	// constructor of normal image button takes in all requirements required to create the button
	public ImageButton(double x, double y, int width, int height, BufferedImage[] images, ClickListener clicker) {
		super(x, y, width, height);
		this.images = images;
		this.clicker = clicker;

	}

	// constructor takes in all requirements required to create the button
	public ImageButton(double x, double y, int width, int height, BufferedImage image, ClickListener clicker) {
		super(x, y, width, height);
		this.image = image;
		this.clicker = clicker;
		
		this.type = 1;

	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics g) {
		
		if(type == 0) 
			// hovering integer is from the UIObject class
			if (hovering) {

				if(btnAudio)
					AudioPlayer.playAudio("audio/next.wav");
				
				btnAudio = false;
				
				g.drawImage(images[1], (int) x, (int) y, width, height, null);
			}
			else {

				btnAudio = true;
				
				g.drawImage(images[0], (int) x, (int) y, width, height, null);
			}
		
		else if(type == 1) {
			
			if (hovering) {
				g.drawImage(Assets.selectedCraft, (int)x, (int)y, 100, 100, null);
				g.drawImage(image, (int) x + 13, (int) y + 13, width, height, null);
			}
			else {
				g.drawImage(Assets.craft, (int)x, (int)y , 100, 100, null);
				g.drawImage(image, (int) x + 13, (int) y + 13, width, height, null);
			}
			
		}

	}

	// Override method from the interface
	@Override
	public void onClick() {
		clicker.onClick();

	}

}
