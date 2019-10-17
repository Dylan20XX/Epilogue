package creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import alphaPackage.ControlCenter;
import graphics.Animation;
import graphics.Assets;
import graphics.CT;
import items.Item;

public class Scavenger extends Creatures {
	
	int cautionBoundSize=350;
	Animation scavengingLeftAnimationLeft;
	Animation scavengingLeftAnimationRight;
	
	Animation scavengerWalkLeft;
	Animation scavengerWalkRight;
	
	public Scavenger(double x, double y, ControlCenter c) {
		super(x, y, Creatures.DEFAULT_CREATURE_WIDTH, Creatures.DEFAULT_CREATURE_HEIGHT , c);

		health = 800;
		speed = 1.4;
		damage = 120;
		knockValue = 0;
		name = "scavenger";
		attackBoundSize = 500;
		type = "creatures";
		weight = 35;
		
		bounds.x = width/5-6;
		bounds.y = height - height/4;
		bounds.width = width/5*7;
		bounds.height = height/4+10;
		
		scavengingLeftAnimationLeft = new Animation(200, Assets.scavenging, true);
		scavengingLeftAnimationRight = new Animation(200, CT.flip(Assets.scavenging), true);
		
		scavengerWalkLeft = new Animation(200, Assets.scavenger, true);
		scavengerWalkRight = new Animation(200, CT.flip(Assets.scavenger), true);
		
		combatXPDropped = (int)(10 * (double)Player.getPlayerData().getIntelligence()/10);
		
	}
	
	@Override
	public void tick() {
		AI();
		if (move)
			move();
		left.tick();
		right.tick();
	}
	public void AI() {
		
		if(health != ogHealth)
			escape(attackBound(), false);
		
		if(c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getEntityManager().getPlayer()
				.getBounds().intersects(cautionBound(cautionBoundSize)))
			if(c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getEntityManager().getPlayer()
					.getBounds().intersects(attackBound())) {
				left = scavengerWalkLeft;
				right = scavengerWalkRight;
				escape(attackBound(), true);
				
			} else {
				left = scavengingLeftAnimationLeft;
				right = scavengingLeftAnimationRight;
				caution();
			}
			
		else {
			left = scavengerWalkLeft;
			right = scavengerWalkRight;
			natural();
			
		}

	}
	@Override
	public void render(Graphics g) {
		g.drawImage(getCurrentAnimation(), (int) (x - c.getGameCamera().getxOffset()),
				(int) (y - c.getGameCamera().getyOffset()), width + 32, height + 32, null);

	}

	@Override
	public void Die() {
		int randDrop1 = CT.random(2, 6);
		for (int i = 0; i < randDrop1; i++)
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
					.getItemManager().addItem(Item.gearItem.createNew((int) x + CT.random(0, 40) + 20, (int) y + bounds.y + CT.random(0, 20)));

	}
	
	@Override
	public void interact() {
		// TODO Auto-generated method stub

	}

}
