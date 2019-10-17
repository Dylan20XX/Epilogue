package staticEntity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import alphaPackage.ControlCenter;
import creatures.Player;
import items.Item;
import tiles.Tile;
import world.WorldGenerator;

public class PineSap extends StaticEntity {

	// java's random API
	private Random r = new Random();
	private long lastMoveTimer = System.currentTimeMillis();

	private ControlCenter c;

	// variables for reference
	public static int width = StaticEntity.DEFAULT_STATICOBJECT_WIDTH * 3;
	public static int height = StaticEntity.DEFAULT_STATICOBJECT_HEIGHT * 3;

	private int numDaysToGrow;
	private int startDay;

	public PineSap(double x, double y, ControlCenter c) {
		super(x, y, width, height, c);

		this.c = c;

		bounds.x = width / 5 * 2;
		bounds.y = height - height / 5;
		bounds.width = width / 6;
		bounds.height = height / 5;

		health = 1;
		resistance = 10;

		numDaysToGrow = r.nextInt(5) + 3;
		startDay = WorldGenerator.dayNum;

	}

	@Override
	public void tick() {

		if(WorldGenerator.dayNum >= startDay + numDaysToGrow) {
			
			active = false;

			int sapTileX = (int) (x - x % Tile.TILEWIDTH) / Tile.TILEWIDTH;
			int sapTileY = (int) (y - y % Tile.TILEWIDTH) / Tile.TILEHEIGHT;

			if(c.getGameState().getWorldGenerator().getTerrain()[sapTileX][sapTileY] == 1) {
				c.getGameState().getWorldGenerator().getEntityManager().addEntity(new PineTree(x, y, c));
			} else if(c.getGameState().getWorldGenerator().getTerrain()[sapTileX][sapTileY] == 6) {
				c.getGameState().getWorldGenerator().getEntityManager().addEntity(new InfectedTree(x, y, c));
			} else {
				c.getGameState().getWorldGenerator().getEntityManager().addEntity(new BurntTree(x, y, c));
			}

		}

	}

	@Override
	public void render(Graphics g) {

		g.setColor(Color.yellow);
		Graphics2D g2d = (Graphics2D) g;
		g2d.draw(getBounds());

	}

	@Override
	public void Die() {
		
		int randDrop = r.nextInt(5) + 5;
		for (int i = 0; i < randDrop; i++) {
			// dropping items at a scaled random location
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager()
			.addItem(Item.woodItem.createNew((int) (((int) x + 50 + r.nextInt(50)) * c.getScaleValue()),
					(int) (((int) y + 170 + r.nextInt(20)) * c.getScaleValue())));
		}
		
	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub

	}

}