package creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import alphaPackage.ControlCenter;
import graphics.Animation;
import graphics.Assets;
import graphics.CT;
import items.Food;

public class PackAlpha extends Creatures {

	public PackAlpha(double x, double y, ControlCenter c) {
		super(x, y, Creatures.DEFAULT_CREATURE_WIDTH/3*4, Creatures.DEFAULT_CREATURE_HEIGHT/6*4, c);

		health = 800;
		damage = 200;
		speed = 1.6;
		knockValue = 15;
		attackBoundSize = 5000;
		resistance = (int)(Math.random()*30);
		name = "packAlpha";
		type = "creatures";
		weight = 75;
		
		bounds.x = width/4;
        bounds.y = height/2 + height/4;
        bounds.width = width;
        bounds.height = height/4*3;
		
        int animationSpeed = CT.random(90, 140);
		
		right = new Animation(animationSpeed, Assets.packAlpha, true);
		left = new Animation(animationSpeed, CT.flip(Assets.packAlpha), true);
		
		combatXPDropped = (int)(20 * (double)Player.getPlayerData().getIntelligence()/10);

	}

	@Override
	public void tick() {
		AI();
		move();
		left.tick();
		right.tick();
	}


	public void AI() {
		
		if(c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getEntityManager().getPlayer()
				.getBounds().intersects(attackBound())) {
			
			if (damageBound().intersects(Player.getPlayerData().getBounds())) 
				knockbackPlayer(this);
			
			chase(2);
			
		}
			
		else 
			
			natural();

	}

	@Override
	public void render(Graphics g) {
		
		g.drawImage(getCurrentAnimation(), (int) (x - c.getGameCamera().getxOffset()),
				(int) (y - c.getGameCamera().getyOffset()), width + 32, height + 32, null);
		

	}

	@Override
	public void Die() {
		int randMeat = CT.random(1, 2);
		for(int i = 0; i < randMeat; i++)
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Food.suspicousMorselItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
		int randFur = CT.random(1, 5);
		for(int i = 0; i < randFur; i++)
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Food.furItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
		
		c.getGameState().getWorldGenerator().setPackAlphaActive(false);
	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub
		
	}
	
}
