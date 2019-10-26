package creatures;

import java.awt.Graphics;

import alphaPackage.ControlCenter;
import graphics.Animation;
import graphics.Assets;
import graphics.CT;
import items.Food;
import items.Item;

public class GiantBeetle extends Creatures{

	public GiantBeetle(double x, double y, ControlCenter c) {
		super(x, y, Creatures.DEFAULT_CREATURE_WIDTH / 2 * 5, Creatures.DEFAULT_CREATURE_HEIGHT / 2 * 5, c);

		health = 1350;
		ogHealth = health;
		damage = 250;
		speed = 1;
		resistance = 95;
		knockValue = 18;
		attackBoundSize = 800;
		name = "giantBeetle";
		type = "creatures";
		weight = 155;
		
		bounds.x = (int)(5 * ControlCenter.scaleValue);
		bounds.y = (int)(90 * ControlCenter.scaleValue);
		bounds.width = Creatures.DEFAULT_CREATURE_HEIGHT / 2 * 5 - 10;
		bounds.height = (int)(25 * ControlCenter.scaleValue);

		left = new Animation(100, Assets.beetleleft, true);
		right = new Animation(100, Assets.beetleright, true);
		
		combatXPDropped = (int)(12 + Math.random()*(Player.getPlayerData().getIntelligence()*2));

	}

	@Override
	public void tick() {
		BeetleAI();
		if (move)
			move();
		left.tick();
		right.tick();
	}

	public void BeetleAI() {
		
		if(health == ogHealth) 
			
			natural();
			
		else {
			attackBoundSize = 1500;
			if(c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getEntityManager().getPlayer()
					.getBounds().intersects(attackBound())) {
				
				if (damageBound().intersects(Player.getPlayerData().getBounds())) 
					knockbackPlayer(this);
				
				chase(2);
				
			}
				
			else 
				
				natural();
			
		}
			
	}

	@Override
	public void render(Graphics g) {
		
		g.drawImage(getCurrentAnimation(), (int)(x - c.getGameCamera().getxOffset()),
				(int)(y - c.getGameCamera().getyOffset()), 
				width, height, null);

	}

	@Override
	public void Die() {
		int randDrop1 = CT.random(1, 3);
		int randDrop2 = CT.random(0, 2);
		for (int i = 0; i < randDrop1; i++)
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Food.bugMeatItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
		if (randDrop2 == 1)
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Item.beetleMembraneItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub
		
	}

}
