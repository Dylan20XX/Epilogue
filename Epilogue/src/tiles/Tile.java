package tiles;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import alphaPackage.ControlCenter;
import graphics.Assets;

/*
 * this class is the parent class for all tiles in the game
 */
public class Tile {

	//and array consisting of all varieties of tiles
	public static Tile tiles[] = new Tile[256];
	
	//Tile initialization
	public static Tile dirtTile = new DirtTile(0);
	public static Tile dirt2Tile = new Dirt2Tile(1);
	public static Tile dirt3Tile = new Dirt3Tile(2);
	public static Tile infectedTile = new InfectedTile(3);
	public static Tile infected2Tile = new Infected2Tile(4);
	public static Tile infected3Tile = new Infected3Tile(5);
	public static Tile naturalTile = new NaturalTile(6);
	public static Tile natural2Tile = new Natural2Tile(7);
	public static Tile natural3Tile = new Natural3Tile(8);
	public static Tile corruptedTile = new CorruptedTile(9);
	public static Tile waterTile = new WaterTile(10);
	public static Tile semiDesertTile = new SemiDesertTile(11);
	//public static Tile stoneWallTile = new StoneWallTile(7);
	
	public static Tile dirtETile = new DirtETile(100);
	//////
	
	//default dimensions for  a tile
	public static final int TILEWIDTH = (int)(64*ControlCenter.scaleValue), TILEHEIGHT = (int)(64*ControlCenter.scaleValue);
	
	protected BufferedImage texture;
	protected final int id;
	
	//Tile constructor initializes the texture and id of a tile
	public Tile(BufferedImage texture, int id) {
		this.texture = texture;
		this.id = id;
		
		tiles[id] = this; 
	}
	
	public void tick() {

	}
	
	public void render(Graphics g, int x, int y) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(texture, x, y, TILEWIDTH, TILEHEIGHT, null);
	}
	
	//checks if player is able to walk through
	public boolean isSolid() {
		return false;
	}
	
	public int getId() {
		return id;
	}
	
}
