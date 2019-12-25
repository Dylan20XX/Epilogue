package creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import alphaPackage.ControlCenter;
import audio.AudioPlayer;
import creatures.Player;
import graphics.Assets;
import graphics.CT;
import items.Food;
import items.Item;
import tiles.Tile;

public class PrettyShroom extends Creatures {// java's random API
	private Random r = new Random();

	private ControlCenter c;

	//variables for reference
	public static int width = Tile.TILEWIDTH;
	public static int height = Tile.TILEHEIGHT;

	public PrettyShroom(double x, double y, ControlCenter c) {
		super(x, y, width, height, c);

		this.c = c;
		
		health = 1;
		resistance = 0;
		speed = 0;
		damage = 0;
		knockValue = 0;
		name = "prettyShroom";
		attackBoundSize = 0;
		type = "material";
		weight = 10;

		// bounding box starting and ending location
		bounds.width = 32;
		bounds.height = 32;
		bounds.x = Tile.TILEWIDTH / 2 - bounds.width / 2;
		bounds.y = Tile.TILEHEIGHT / 2 - bounds.height / 2;

	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics g) {

		if(Player.getPlayerData().steppingBound().intersects(getBounds())) {
    		
    		AudioPlayer.playAudio("audio/plant.wav");
    		Die();
    		active = false;
    		
    	}

		g.drawImage(Assets.prettyShroomPlant, (int) (x - c.getGameCamera().getxOffset()),
				(int) (y - c.getGameCamera().getyOffset()), width, height, null);

	}

	@Override
	public void Die() {
		
		c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
		.getItemManager().addItem(Food.prettyShroomItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
	
	}

	@Override
	public void interact() {
		
		if(Player.getPlayerData().getInventory().addItem(Food.prettyShroomItem)) {
			AudioPlayer.playAudio("audio/pickup.wav");
			active = false;
		}

	}
}