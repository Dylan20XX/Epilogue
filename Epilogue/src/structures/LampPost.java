package structures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import alphaPackage.ControlCenter;
import graphics.Assets;
import staticEntity.StaticEntity;
import tiles.Tile;

public class LampPost extends StaticEntity {

	// java's random API
	private Random r = new Random();

	private ControlCenter c;

	// variables for reference
	public static int width = Tile.TILEWIDTH * 3;
	public static int height = Tile.TILEHEIGHT * 3;

	private BufferedImage lamp = Assets.lampPost[0];

	private boolean preAddLight = false;

	public LampPost(double x, double y, ControlCenter c) {
		super(x, y, width, height, c);

		this.c = c;
		name = "lamp post";

		// bounding box starting and ending location

		bounds.x = width / 5 * 2;
		bounds.y = height - height / 5;
		bounds.width = width / 6 + 4;
		bounds.height = height / 5;
		deathImage = Assets.lampPost[0];

		health = 400;
		resistance = 35;

	}

	@Override
	public void tick() {

		if (c.getGameState().getWorldGenerator().powerMap[placex][placey] > 0) {
			lamp = Assets.lampPost[0];
			if (!preAddLight) {
				c.getGameState().getWorldGenerator().addLight(this, 5, placex, placey, 3);
				preAddLight = true;
			}
		} else {
			lamp = Assets.lampPost[1];
			if (preAddLight) {
				c.getGameState().getWorldGenerator().removeLight(5, placex, placey, 3);
				preAddLight = false;
			}
		}

	}

	@Override
	public void render(Graphics g) {
		// drawing down the bounding box
		Graphics2D g2d = (Graphics2D) g;

		g.drawImage(lamp, (int) (x - c.getGameCamera().getxOffset()), (int) (y - c.getGameCamera().getyOffset()), width,
				height, null);

		g.setColor(Color.BLUE);
		g2d.draw(getBounds());

	}

	@Override
	public void Die() {

		if (preAddLight) {
			c.getGameState().getWorldGenerator().removeLight(5, placex, placey, 3);
		}

	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub

	}

}
