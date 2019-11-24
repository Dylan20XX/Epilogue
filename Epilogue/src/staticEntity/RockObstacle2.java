package staticEntity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import alphaPackage.ControlCenter;
import creatures.Player;
import graphics.Assets;
import items.Item;
import tiles.Tile;

public class RockObstacle2 extends StaticEntity {// java's random API
	private Random r = new Random();

	private ControlCenter c;

	// variables for reference
	public static int width = Tile.TILEWIDTH;
	public static int height = Tile.TILEHEIGHT;

	// smaller rock
	public RockObstacle2(double x, double y, ControlCenter c) {
		super(x, y, width, height, c);

		this.c = c;

		// bounding box starting and ending location
		bounds.x = width / 8;
		bounds.y = height - height / 3;
		bounds.width = width / 6 * 5;
		bounds.height = height / 5;

		deathImage = Assets.rock1;

		health = 2000;
		resistance = 30;
		requiredTool = "pickaxe";
		
		buildingXPGiven = (int)(Math.random()*(Player.getPlayerData().getIntelligence()));

	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics g) {

		g.drawImage(Assets.rock1, (int) (x - c.getGameCamera().getxOffset()),
				(int) (y - c.getGameCamera().getyOffset()), width, height, null);

	}

	@Override
	public void Die() {

		int randDrop = r.nextInt(5) + 3;
		for (int i = 0; i < randDrop; i++) {
			// dropping items at a scaled random location
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getItemManager()
					.addItem(Item.rockItem.createNew((int) (((int) x + 50 + r.nextInt(50)) * c.getScaleValue()),
							(int) (((int) y + 40 + r.nextInt(20)) * c.getScaleValue())));
		}

		int bronzeDrop = r.nextInt(8);
		int tinDrop = r.nextInt(4);
		int zincDrop = r.nextInt(18);
		int ironDrop = r.nextInt(25);
		int coalDrop = r.nextInt(6);

		if (bronzeDrop == 0) {
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getItemManager()
					.addItem(Item.bronzeChunkItem.createNew((int) (((int) x + 50 + r.nextInt(50)) * c.getScaleValue()),
							(int) (((int) y + 40 + r.nextInt(20)) * c.getScaleValue())));
		}

		if (tinDrop == 0) {
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getItemManager()
					.addItem(Item.tinChunkItem.createNew((int) (((int) x + 50 + r.nextInt(50)) * c.getScaleValue()),
							(int) (((int) y + 40 + r.nextInt(20)) * c.getScaleValue())));
		}

		if (zincDrop == 0) {
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getItemManager()
					.addItem(Item.zincChunkItem.createNew((int) (((int) x + 50 + r.nextInt(50)) * c.getScaleValue()),
							(int) (((int) y + 40 + r.nextInt(20)) * c.getScaleValue())));
		}

		if (ironDrop == 0) {
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getItemManager()
					.addItem(Item.ironChunkItem.createNew((int) (((int) x + 50 + r.nextInt(50)) * c.getScaleValue()),
							(int) (((int) y + 40 + r.nextInt(20)) * c.getScaleValue())));
		}

		if (coalDrop == 0) {
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getItemManager()
					.addItem(Item.coalChunkItem.createNew((int) (((int) x + 50 + r.nextInt(50)) * c.getScaleValue()),
							(int) (((int) y + 40 + r.nextInt(20)) * c.getScaleValue())));
		}

	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub

	}

}