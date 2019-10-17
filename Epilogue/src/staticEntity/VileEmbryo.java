package staticEntity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import alphaPackage.ControlCenter;
import graphics.Animation;
import graphics.Assets;
import graphics.CT;
import items.Food;
import tiles.Tile;
import world.WorldGenerator;

public class VileEmbryo extends StaticEntity {

	// java's random API
	private Random r = new Random();

	private ControlCenter c;

	// variables for reference
	public static int width = Tile.TILEWIDTH*2;
	public static int height = Tile.TILEHEIGHT * 2;
	
	private Animation anim = new Animation(900, Assets.vileEmbryo, true);

	public VileEmbryo(double x, double y, ControlCenter c) {
		super(x, y, width, height, c);

		this.c = c;

		// bounding box starting and ending location
		bounds.x = width / 8;
		bounds.y = height - height / 3;
		bounds.width = width / 6 * 5;
		bounds.height = height / 5;

		name = "vile embryo";
		
		resistance = 0;
		
		deathImage = Assets.vileEmbryo[0];

		health = 400;

	}

	@Override
	public void tick() {

		anim.tick();
		
	}

	@Override
	public void render(Graphics g) {

		g.drawImage(anim.getCurrentFrame(), (int) (x - c.getGameCamera().getxOffset()), (int) (y - c.getGameCamera().getyOffset()), width,
				height, null);

	}

	@Override
	public void Die() {
		WorldGenerator.numVileEmbryo += 1;
		int randDrop1 = CT.random(1, 3);
		for (int i = 0; i < randDrop1; i++)
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Food.bugMeatItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
		for (int i = 0; i < CT.random(2, 6); i++)
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Food.glucose.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
	
	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub

	}

}