package staticEntity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import alphaPackage.ControlCenter;
import graphics.Assets;
import graphics.CT;
import items.Food;
import items.Item;

public class Agave extends StaticEntity {

	// java's random API
	private Random r = new Random();

	private ControlCenter c;

	// variables for reference
	public static int width = StaticEntity.DEFAULT_STATICOBJECT_WIDTH * 3;
	public static int height = StaticEntity.DEFAULT_STATICOBJECT_HEIGHT * 3;
	
	private BufferedImage agave;
	
	public Agave(double x, double y, ControlCenter c) {
		super(x, y, width, height, c);

		this.c = c;
		
		// bounding box starting and ending location
		bounds.x = width / 4+10;
        bounds.y = height - height / 5;
        bounds.width = width / 3+14;
        bounds.height = height / 5-10;

		health = 800;
		resistance = 35;
		
		int randAgave = (int)(Math.random() * 4);
		
		if(randAgave == 0) 
			agave = Assets.agave1;
		else if(randAgave == 1)
			agave = CT.flip(Assets.agave1);
		if(randAgave == 2) 
			agave = Assets.agave2;
		else if(randAgave == 3)
			agave = CT.flip(Assets.agave2);
		
		deathImage = agave;
		requiredTool = "axe";

	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics g) {

		g.drawImage(agave, (int) (x - c.getGameCamera().getxOffset()),
				(int) (y - c.getGameCamera().getyOffset()), width, height, null);

	}

	@Override
	public void Die() {

		int randDrop = CT.random(1, 3);
		for (int i = 0; i < randDrop; i++) {
			// dropping items at a scaled random location
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Food.vegeMeatItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
		}

	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub
		
	}

}
