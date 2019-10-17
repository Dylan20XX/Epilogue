package staticEntity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import alphaPackage.ControlCenter;
import creatures.Player;
import creatures.Sentry;
import graphics.Assets;
import structureInventory.ChestInventory;

public class Cave extends StaticEntity{

	//java's random API
	protected Random r = new Random();
	
	protected ControlCenter c;

	//variables for reference
	public static int width = StaticEntity.DEFAULT_STATICOBJECT_WIDTH * 2;
	public static int height = StaticEntity.DEFAULT_STATICOBJECT_HEIGHT * 2;
	
	private long lastSpawnTimer, spawnCooldown = 5000, spawnTimer = spawnCooldown;
	
	public Cave(double x, double y, ControlCenter c) {
		super(x, y, width, height, c);
		
		this.c = c;

		//bounding box starting and ending location
		bounds.x = 0;
		bounds.y = 0;
		bounds.width = width;
		bounds.height = height;

		health = 1000;
		resistance = 1000;


	}

	@Override
	public void tick() {
		
		spawnTimer += System.currentTimeMillis() - lastSpawnTimer;
		lastSpawnTimer = System.currentTimeMillis();

		if (spawnTimer < spawnCooldown)
			return;
		
		if (c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
				.getEntityManager().getPlayer().getBounds().intersects(spawnBound())) {
			
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getEntityManager().addEntity(new Sentry(x + r.nextInt(64) + 32,
					y + r.nextInt(64) + 32, c));
			
		}

		spawnTimer = 0;
		
	}
	
	public Rectangle spawnBound() {

		return new Rectangle((int) (x - c.getGameCamera().getxOffset() - 600) + bounds.width / 2,
				(int) (y - c.getGameCamera().getyOffset() - 600) + bounds.height / 2, 1200, 1200);

	}
	
	@Override
	public void render(Graphics g) {

		g.drawImage(Assets.cave, (int)(x - c.getGameCamera().getxOffset()), (int)(y - c.getGameCamera().getyOffset()),
				width, height, null);


	}

	@Override
	public void Die() {
		
	}

	@Override
	public void interact() {

	}
	
	//cave can't be hurt or destroyed
	@Override
	public void hurt(int amount) {

	}
	
}
