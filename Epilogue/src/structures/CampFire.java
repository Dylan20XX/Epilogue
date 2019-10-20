package structures;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.Random;

import alphaPackage.ControlCenter;
import creatures.Player;
import graphics.Animation;
import graphics.Assets;
import graphics.CT;
import items.Item;
import staticEntity.StaticEntity;
import tiles.Tile;

public class CampFire extends StaticEntity {

	// java's random API
	private Random r = new Random();

	private ControlCenter c;

	private Animation fire;
	
	private static final int fuel = 36000;
	private int curFuel;
	private int state = 1, prevState = 1;
	
	// variables for reference
	public static int width = Tile.TILEWIDTH;
	public static int height = Tile.TILEHEIGHT;
	
	public CampFire(double x, double y, ControlCenter c) {
		super(x, y, width, height, c);

		this.c = c;
		name = "campfire";

		// bounding box starting and ending location
		bounds.x = width/6;
        bounds.y = height - height / 5;
        bounds.width = width / 2+17;
        bounds.height = height / 5-3;

		deathImage = Assets.campfire[0];
		health = 200;
		resistance = 10;
		curFuel = fuel;
		
		fire = new Animation(200, Assets.campfire, true);
		
		damage = 65;
		knockValue = 10;
	}

	@Override
	public void tick() {
		fire.tick();
		
		if(curFuel < 1)
			health = 0;
		else
			curFuel -= 1;
		
		int fPrct = (int)((double) curFuel / fuel * 100);
		if(fPrct < 33)
			state = 3;
		else if(fPrct < 66)
			state = 2;
		else 
			state = 1;
		if(state != prevState)
			updateLumen();
		prevState = state;
		
		if(Player.getPlayerData().getCollisionBounds(0, 0).
				intersects(new Rectangle((int)(x + bounds.x - 30), (int)(y + bounds.y - 30), 
						60 + bounds.width , 60 + bounds.height))) {
			knockbackPlayer(this);
		}
	}

	@Override
	public void render(Graphics g) {
		// drawing down the bounding box
		Graphics2D g2d = (Graphics2D) g;

		g.drawImage(fire.getCurrentFrame(), (int) (x - c.getGameCamera().getxOffset()),
				(int) (y - c.getGameCamera().getyOffset()), width, height, null);

		g.setColor(Color.BLUE);
		g2d.draw(getBounds());

	}

	@Override
	public void Die() {

		c.getGameState().getWorldGenerator().removeLight(5, placex, placey, 1);
		
		c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getItemManager()
		.addItem(Item.ashe.createNew((int) x + bounds.x + CT.random(0, bounds.width),
				(int) y + bounds.y + CT.random(0, bounds.height)));
	}

	@Override
	public void interact() {
		if(Player.getPlayerData().getHands() == null || Player.getPlayerData().getHands().getHand() == null || (int)((double) curFuel / fuel * 100) > 99)
			return;
		Item fuelItem = Player.getPlayerData().getHands().getHand();
        String itemName = fuelItem.getName();
		if(itemName.equals("log")) {
			curFuel += 1800;
			Player.getPlayerData().getInventory().removeItem(fuelItem);
        }
		if(itemName.equals("plank")) {
			curFuel += 3600;
			Player.getPlayerData().getInventory().removeItem(fuelItem);
        }
		if(itemName.equals("stick")) {
			curFuel += 3600;
			Player.getPlayerData().getInventory().removeItem(fuelItem);
        }
		if(itemName.equals("coal")) {
			curFuel += 9000;
			Player.getPlayerData().getInventory().removeItem(fuelItem);
        }
		if(curFuel > fuel)
			curFuel = fuel;
	}
	
	public void updateLumen() {
		c.getGameState().getWorldGenerator().addLight(this, 5, this.placex, this.placey, state);
		c.getGameState().getWorldGenerator().removeLight(5, this.placex, this.placey, prevState);
	}
}
