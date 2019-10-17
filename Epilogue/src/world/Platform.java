package world;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import alphaPackage.ControlCenter;
import graphics.Assets;

public class Platform {
	
	//and array consisting of all varieties of floors
	public static Platform floors[] = new Platform[256];
	
	//Floor initialization
	public static Platform woodenPlatform = new Platform(Assets.woodenPlatform, 1);
	public static Platform stonePlatform = new Platform(Assets.stonePlatform, 2);
	public static Platform metalPlatform = new Platform(Assets.metalPlatform, 3);
	public static Platform grassPlatform = new Platform(Assets.naturalTile, 4);
	//////
	
	//default dimensions for a floor
	public static final int FLOORWIDTH = (int)(64*ControlCenter.scaleValue), FLOORHEIGHT = (int)(64*ControlCenter.scaleValue);
	
	protected BufferedImage texture;
	protected final int id;
	
	//Floor constructor initializes the texture and id of a tile
	public Platform(BufferedImage texture, int id) {
		this.texture = texture;
		this.id = id;
		
		floors[id] = this; 
	}
	
	public void tick() {

	}
	
	public void render(Graphics g, int x, int y) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(texture, x, y, FLOORWIDTH, FLOORHEIGHT, null);
	}
	
	//checks if player is able to walk through
	public boolean isSolid() {
		return false;
	}
	
	public int getId() {
		return id;
	}
	
}
