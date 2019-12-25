package creatures;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import alphaPackage.ControlCenter;
import graphics.Animation;
import graphics.Assets;
import graphics.CT;
import items.Food;
import items.Item;

public class Chicken extends Creatures{
	
	public Chicken(double x, double y, ControlCenter c) {
		
		super(x, y, Creatures.DEFAULT_CREATURE_WIDTH, Creatures.DEFAULT_CREATURE_HEIGHT, c);

		health = 100;
		speed = 1.8;
		resistance = 0;
		intimidation = 10;
		attackBoundSize = 500;
		name = "chicken";
		type = "creatures";
		weight = 10;
		
		bounds.x = Creatures.DEFAULT_CREATURE_WIDTH/6;
		bounds.y = Creatures.DEFAULT_CREATURE_HEIGHT/3*2;
		bounds.width = Creatures.DEFAULT_CREATURE_WIDTH/6*4;
		bounds.height = Creatures.DEFAULT_CREATURE_HEIGHT/6;

		left = new Animation(200, Assets.chickenLeft, true);
		right = new Animation(200, Assets.chickenRight, true);
		
		combatXPDropped = (int)(2 + Math.random()*(Player.getPlayerData().getIntelligence()*2))/2;
		
	}

	@Override
	public void tick() {
		
		ChickenAI();
		if (move)
			move();
		
		left.tick();
		right.tick();
		
	}
	
	public void ChickenAI() {
		
		if(c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getEntityManager().getPlayer()
				.getBounds().intersects(attackBound())) {
			left.setSpeed(100);
			right.setSpeed(100);
			escape(attackBound(), false);
		}
		else {
			natural();
			left.setSpeed(200);
			right.setSpeed(200);
		}
			
	}

	@Override
	public void render(Graphics g) {
		
		g.drawImage(getCurrentAnimation(), (int)(x - c.getGameCamera().getxOffset()),
				(int)(y - c.getGameCamera().getyOffset()), 
				width, height, null);
		
	}
	
	public void Die() {
		
		c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getItemManager()
		.addItem(Food.rawChickenItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
		
		int randFeather = CT.random(2, 6);
		for(int i = 0; i < randFeather; i++) {
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getItemManager()
			.addItem(Item.featherItem.createNew((int) x + bounds.x + bounds.width/2, (int) y + bounds.y + bounds.height/2));
			
		}
		
	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub
		
	}
	
}
