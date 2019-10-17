package staticEntity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import alphaPackage.ControlCenter;
import creatures.Player;
import graphics.Assets;
import items.Food;
import items.Item;
import tiles.Tile;

public class PrettyShroom extends StaticEntity {// java's random API
	private Random r = new Random();

	private ControlCenter c;

	//variables for reference
	public static int width = Tile.TILEWIDTH;
	public static int height = Tile.TILEHEIGHT;

	public PrettyShroom(double x, double y, ControlCenter c) {
		super(x, y, width, height, c);

		this.c = c;

		// bounding box starting and ending location
		bounds.x = width/5*2;
		bounds.y = height/5*2;
		bounds.width = width/5;
		bounds.height = height/5;

		deathImage = Assets.prettyShroom;
				
		health = 1;
		resistance = 0;

	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics g) {

		g.drawImage(Assets.prettyShroomPlant, (int) (x - c.getGameCamera().getxOffset()),
				(int) (y - c.getGameCamera().getyOffset()), width, height, null);

	}

	@Override
	public void Die() {

		c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
		.getItemManager()
		.addItem(Food.prettyShroomItem.createNew((int)(((int)x + bounds.x)*c.getScaleValue()), (int)(((int)y + bounds.y)*c.getScaleValue())));

	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub

	}
}