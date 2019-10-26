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

public class CrazedGoat extends Creatures {

	public CrazedGoat(double x, double y, ControlCenter c) {
		super(x, y, Creatures.DEFAULT_CREATURE_WIDTH *2, Creatures.DEFAULT_CREATURE_HEIGHT *2, c);

		health = 800;
		speed = 2.7;
		damage = 120;
		knockValue = 12;
		name = "crazedGoat";
		attackBoundSize = 800;
		type = "creatures";
		weight = 125;
		
		 bounds.x = width/5-12;
	     bounds.y = height/2+15;
	     bounds.width = width/2*2+2;
	     bounds.height = height/4+20;

		left = new Animation(125, Assets.crazedGoat, true);
		right = new Animation(125, CT.flip(Assets.crazedGoat), true);
		
		combatXPDropped = (int)(5 + Math.random()*(Player.getPlayerData().getIntelligence()*2));

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

		if(c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getEntityManager().getPlayer()
				.getBounds().intersects(attackBound())) {
			
			if (damageBound().intersects(Player.getPlayerData().getBounds())) 
				knockbackPlayer(this);
			
			chase(1.5);
			
		}
			
		else 
			
			natural();

	}

	@Override
	public void render(Graphics g) {
		g.drawImage(getCurrentAnimation(), (int) (x - c.getGameCamera().getxOffset()),
				(int) (y - c.getGameCamera().getyOffset()), width + 32, height + 32, null);

		g.setColor(Color.blue);
		Graphics2D g2d = (Graphics2D) g;
		g2d.draw(getBounds());
		g.setColor(Color.GREEN);
		g2d.draw(attackBound());

	}

	@Override
	public void Die() {
		int randDrop1 = CT.random(0, 1);
		int randDrop3 = CT.random(1, 2);
		int randDrop2 = CT.random(1, 6);
		for (int i = 0; i < randDrop1; i++)
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Food.rawMorselItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
		for (int i = 0; i < randDrop3; i++)
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Food.suspicousMorselItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
		for (int i = 0; i < randDrop2; i++)
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Item.furItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));

	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub

	}

}
