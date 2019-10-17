package structures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import alphaPackage.ControlCenter;
import graphics.Animation;
import graphics.Assets;
import staticEntity.StaticEntity;
import tiles.Tile;

public class PowerGenerator extends StaticEntity {

	// java's random API
	private Random r = new Random();

	private ControlCenter c;

	private Animation powerGen;

	// variables for reference
	public static int width = Tile.TILEWIDTH;
	public static int height = Tile.TILEHEIGHT;
	
	public PowerGenerator(double x, double y, ControlCenter c) {
		super(x, y, width, height, c);

		this.c = c;

		name = "power generator";
		// bounding box starting and ending location
		bounds.x = 0;
		bounds.y = height/4;
		bounds.width = width;
		bounds.height = height / 4 * 3;

		health = 1000;
		resistance = 10;
		
		deathImage = Assets.powerGenerator[0];

		powerGen = new Animation(200, Assets.powerGenerator, true);

	}

	@Override
	public void tick() {
		powerGen.tick();
	}

	@Override
	public void render(Graphics g) {
		// drawing down the bounding box
		Graphics2D g2d = (Graphics2D) g;

		g.drawImage(powerGen.getCurrentFrame(), (int) (x - c.getGameCamera().getxOffset()),
				(int) (y - c.getGameCamera().getyOffset()), width, height, null);

		g.setColor(Color.BLUE);
		g2d.draw(getBounds());

	}

	@Override
	public void Die() {

		c.getGameState().getWorldGenerator().removeLight(3, placex, placey, 3);
		c.getGameState().getWorldGenerator().removePower(7, placex, placey);

	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub

	}

}
