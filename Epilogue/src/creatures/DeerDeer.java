package creatures;

import java.awt.Graphics;

import alphaPackage.ControlCenter;
import graphics.Animation;
import graphics.Assets;
import graphics.CT;
import items.Food;
import items.Item;

public class DeerDeer extends Creatures{
	
	public DeerDeer(double x, double y, ControlCenter c) {
		
		super(x, y, Creatures.DEFAULT_CREATURE_WIDTH * 2, Creatures.DEFAULT_CREATURE_HEIGHT * 2, c);

		health = 500;
		speed = 2.5;
		resistance = 0;
		intimidation = 10;
		attackBoundSize = 600;
		name = "deerDeer";
		type = "creatures";
		weight = 55;
		
		bounds.x = width/9 + 20;
        bounds.y = height/43 - 15;
        bounds.width = width/95;
        bounds.height = height/4+5;

		left = new Animation(400, CT.flip(Assets.deerDeer), true);
		right = new Animation(400, Assets.deerDeer, true);
		
		combatXPDropped = (int)(10 * (double)Player.getPlayerData().getIntelligence()/10);
	
	}

	@Override
	public void tick() {
		
		DeerDeerAI();
		if (move)
			move();
		
		left.tick();
		right.tick();
		
	}
	
	public void DeerDeerAI() {
		
		if(health != ogHealth)
			escape(attackBound(), false);
		
		if (c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
				.getEntityManager().getPlayer().getBounds().intersects(attackBound())) {
			
			left.setSpeed(200);
			right.setSpeed(200);

			escape(attackBound(), true);
		}
		else {
			left.setSpeed(400);
			right.setSpeed(400);
			natural();
		}
			
	}

	@Override
	public void render(Graphics g) {
		
		g.drawImage(getCurrentAnimation(), (int)(x - c.getGameCamera().getxOffset()),
				(int)(y - c.getGameCamera().getyOffset()), 
				width, height, null);
		
	}
	
	public void Die() {
		
		int randMeat = CT.random(0, 2);
		int randMeat2 = 1;
		for(int i = 0; i < randMeat; i++)
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Food.rawMorselItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
		for(int i = 0; i < randMeat2; i++)
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Food.suspicousMorselItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
		
	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub
		
	}
	
}
