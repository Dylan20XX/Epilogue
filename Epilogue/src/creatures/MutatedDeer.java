package creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import alphaPackage.ControlCenter;
import graphics.Animation;
import graphics.Assets;
import graphics.CT;
import items.Food;
import items.Item;

public class MutatedDeer extends Creatures{
	
	public MutatedDeer(double x, double y, ControlCenter c) {
		
		super(x, y, Creatures.DEFAULT_CREATURE_WIDTH, Creatures.DEFAULT_CREATURE_HEIGHT, c);

		health = 100;
		speed = 1.4;
		resistance = 0;
		damage = 100;
		intimidation = 10;
		knockValue = 8;
		attackBoundSize = 500;
		name = "mutatedDeer";
		type = "creatures";
		weight = 10;
		
		bounds.x = width/9 + 20;
        bounds.y = height/43 - 15;
        bounds.width = width/95;
        bounds.height = height/4+5;

		left = new Animation(200, Assets.chickenLeft, true);
		right = new Animation(200, Assets.chickenRight, true);
		
		combatXPDropped = (int)(6 + Math.random()*(Player.getPlayerData().getIntelligence()*2));
		
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
		
		if(health < ogHealth/3)
			escape(attackBound(), true);
		
		else if(c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getEntityManager().getPlayer()
				.getBounds().intersects(attackBound())) { 
			if (damageBound().intersects(Player.getPlayerData().getBounds())) 
				knockbackPlayer(this);
			
			chase(1);
		} 
 
		else {
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
		int randMeat = CT.random(1, 3);
		for(int i = 0; i < randMeat; i++)
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Food.suspicousMorselItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub
		
	}
	
}
