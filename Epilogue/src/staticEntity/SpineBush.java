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

public class SpineBush extends StaticEntity{

	//java's random API
	protected Random r = new Random();
	
	protected ControlCenter c;

	//variables for reference
	public static int width = StaticEntity.DEFAULT_STATICOBJECT_WIDTH*2;
	public static int height = StaticEntity.DEFAULT_STATICOBJECT_HEIGHT*2;

	private Rectangle secondaryBounds = new Rectangle(0, 0, width, height);

	public SpineBush(double x, double y, ControlCenter c) {
		super(x, y, width, height, c);
		
		this.c = c;

		bounds.width = width / 2;
		bounds.height = height / 2;
		
		//bounding box starting and ending location
		bounds.x = width / 2 - bounds.width / 2;
		bounds.y = height / 2 - bounds.height / 2;

		health = 200;
		resistance = 50;
		damage = 50;
		knockValue = 10;

	}

	@Override
	public void tick() {
		
		//Damage the player when the player is inside of the bush
		if(Player.getPlayerData().getCollisionBounds(0, 0).
				intersects(new Rectangle((int)(x + bounds.x - 30), (int)(y + bounds.y - 30), 
						bounds.width + 60, bounds.height + 60))) {
			knockbackPlayer(this);
		}
		
	}

	@Override
	public void render(Graphics g) {

		g.drawImage(Assets.spineBush, (int)(x - c.getGameCamera().getxOffset()), (int)(y - c.getGameCamera().getyOffset()),
				width, height, null);

	}

	@Override
	public void Die() {
		for (int i = 0; i < CT.random(1, 3); i++) {
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Item.spikeItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
		}
	}

	@Override
	public void interact() {
		
	}
	
}
