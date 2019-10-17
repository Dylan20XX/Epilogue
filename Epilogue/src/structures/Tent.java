package structures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import alphaPackage.ControlCenter;
import graphics.Assets;
import inventory.MessageBox;
import staticEntity.StaticEntity;
import world.WorldGenerator;

public class Tent extends StaticEntity {

	// java's random API
	private Random r = new Random();
	
	private long lastsleepTimer, sleepCooldown = 2000, sleepTimer = sleepCooldown;

	private ControlCenter c;

	// variables for reference
	public static int width = StaticEntity.DEFAULT_STATICOBJECT_WIDTH * 3;
	public static int height = StaticEntity.DEFAULT_STATICOBJECT_HEIGHT * 3;
	
	public Tent(double x, double y, ControlCenter c) {
		super(x, y, width, height, c);

		this.c = c;

		// bounding box starting and ending location
		bounds.x = width / 6;
		bounds.y = height/2;
		bounds.width = width / 6*4;
		bounds.height = height / 2;
		
		deathImage = Assets.tent;

		health = 100;
		resistance = 10;
		
	}

	@Override
	public void tick() {
		
		sleepTimer += System.currentTimeMillis() - lastsleepTimer;
		lastsleepTimer = System.currentTimeMillis();
		
		if (sleepTimer < sleepCooldown)
			return;
		
		if(c.getMouseManager().mouseBound().intersects(getBounds())) {
			if(c.getMouseManager().isRightClicked() || c.getMouseManager().isRightPressed()) {
				if(WorldGenerator.time < 280 && WorldGenerator.time > 50) {
					MessageBox.addMessage("sleeping...");
					c.getGameState().getWorldGenerator().worldSaver.saveWorld();
					c.getGameState().getWorldGenerator().sleep();
					WorldGenerator.isSleeping = true;
				} else {
					MessageBox.addMessage("not tired enough to sleep...");
				}
			}
		}
		
		sleepTimer = 0;
		
	}

	@Override
	public void render(Graphics g) {

		g.drawImage(Assets.tent, (int) (x - c.getGameCamera().getxOffset()),
				(int) (y - c.getGameCamera().getyOffset()), width, height, null);

		// drawing down the bounding box
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.BLUE);
		g2d.draw(getBounds());
	}

	@Override
	public void Die() {

		

	}
	@Override
	public void interact() {
		// TODO Auto-generated method stub
		
	}

}
