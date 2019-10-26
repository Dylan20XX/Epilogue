package staticEntity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import alphaPackage.ControlCenter;
import creatures.Player;
import graphics.Assets;
import graphics.CT;
import items.Food;
import items.Item;
import structureInventory.ChestInventory;

public class Bush extends StaticEntity{

	//java's random API
	protected Random r = new Random();
	
	protected ControlCenter c;

	//variables for reference
	public static int width = StaticEntity.DEFAULT_STATICOBJECT_WIDTH*2;
	public static int height = StaticEntity.DEFAULT_STATICOBJECT_HEIGHT*2;

	private Rectangle secondaryBounds = new Rectangle(0, 0, width, height);
	private long lastHarvestTimer, harvestCooldown = 300000, harvestTimer = 0; //can be havested for sticks every 5 mins
	private boolean harvested = false;

	public Bush(double x, double y, ControlCenter c) {
		super(x, y, width, height, c);
		
		this.c = c;

		bounds.width = width / 4 * 3;
		bounds.height = height / 4 * 3;
		
		//bounding box starting and ending location
		bounds.x = width / 2 - bounds.width / 2;
		bounds.y = height / 2 - height/2;

		health = 200;
		resistance = 50;

		buildingXPGiven = (int)(Math.random()*(Player.getPlayerData().getIntelligence()*2));

	}

	@Override
	public void tick() {
		
		if(harvested) {
			harvestTimer += System.currentTimeMillis() - lastHarvestTimer;
			lastHarvestTimer = System.currentTimeMillis();

			if (harvestTimer < harvestCooldown)
				return;
			
			harvested = false;
			harvestTimer = 0;
		}
		
	}

	@Override
	public void render(Graphics g) {
		
		if(harvested) 
			g.drawImage(Assets.bush1, (int)(x - c.getGameCamera().getxOffset()), (int)(y - c.getGameCamera().getyOffset()),
					width, height, null);
		else
			g.drawImage(Assets.bush2, (int)(x - c.getGameCamera().getxOffset()), (int)(y - c.getGameCamera().getyOffset()),
					width, height, null);

	}

	@Override
	public void Die() {
		
		for (int i = 0; i < CT.random(2, 4); i++) {
			// dropping items at a scaled random location
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Item.stickItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
		}
		
	}

	@Override
	public void interact() {
		
		if(!harvested) {
			
			int rand = r.nextInt(4) + 2; //get 2-5 items
			
			if(Player.getPlayerData().getInventory().addItem(Item.woodenStickItem)) {
				
				for(int i = 0; i < rand - 1; i++)
					Player.getPlayerData().getInventory().addItem(Item.woodenStickItem);
				
				lastHarvestTimer = System.currentTimeMillis();
				harvested = true;
			}	
		}
		
	}
	
}
