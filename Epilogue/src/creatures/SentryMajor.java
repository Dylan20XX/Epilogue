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

public class SentryMajor extends Creatures {

	public static int width = Creatures.DEFAULT_CREATURE_WIDTH /3 * 5;
	public static int height = Creatures.DEFAULT_CREATURE_HEIGHT /3 * 5;
	 
	public SentryMajor(double x, double y, ControlCenter c) {
		super(x, y, width, height, c);

		health = 800;
		speed = 1.5;
		damage = 170;
		knockValue = 15;
		resistance = 30;
		name = "sentryMajor";
		attackBoundSize = 1500;
		type = "creatures";
		weight = 60;
		
		bounds.x = width/6-12;
        bounds.y = height/7*5;
        bounds.width = width/7*8;
        bounds.height = height/6*3 - 20;

		left = new Animation(125, Assets.sentryMajor, true);
		right = new Animation(125, CT.flip(Assets.sentryMajor), true);
		
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
			
			chase(1);
			
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
		int randDrop1 = CT.random(2, 5);
		int randDrop2 = CT.random(0, 2);
		for (int i = 0; i < randDrop1; i++)
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Item.spikeItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));

		for (int i = 0; i < randDrop2; i++)
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Food.bugMeatItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub

	}

}
