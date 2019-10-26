package staticEntity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import alphaPackage.ControlCenter;
import creatures.Player;
import graphics.Assets;
import graphics.CT;
import items.Item;
import tiles.Tile;

public class RockObstacle extends StaticEntity {// java's random API
	private Random r = new Random();

	private ControlCenter c;

//variables for reference
	public static int width = Tile.TILEWIDTH * 2;
	public static int height = Tile.TILEHEIGHT * 2;

	public RockObstacle(double x, double y, ControlCenter c) {
		super(x, y, width, height, c);

		this.c = c;

		// bounding box starting and ending location
		bounds.x = width / 8;
		bounds.y = height - height / 3;
		bounds.width = width / 6 * 5;
		bounds.height = height / 5;

		deathImage = Assets.rock1;

		health = 1500;
		resistance = 20;
		requiredTool = "pickaxe";
		
		buildingXPGiven = (int)(Math.random()*(Player.getPlayerData().getIntelligence()*2));

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

		for(int i = 0; i < CT.random(3, 5); i++)
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Item.rockItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));

		int bronzeDrop = r.nextInt(19);
		int tinDrop = r.nextInt(5);
		int zincDrop = r.nextInt(18);
		int ironDrop = r.nextInt(25);
		int titaniumDrop = r.nextInt(50);
		int tungstenDrop = r.nextInt(50);
		int coalDrop = r.nextInt(6);

		if (bronzeDrop == 0) {
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Item.bronzeChunkItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));

		}

		if (tinDrop == 0) {
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Item.tinChunkItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));

		}

		if (zincDrop == 0) {
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Item.zincChunkItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));

		}

		if (ironDrop == 0) {
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Item.ironChunkItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));

		}

		if (coalDrop == 0) {
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Item.coalChunkItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));

		}
		
		if (tungstenDrop == 0) {
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Item.tungstenChunkItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));

		}
		
		if (titaniumDrop == 0) {
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Item.titaniumChunkItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));

		}
		
	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub

	}

}