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

public class Sentry extends Creatures {

	public Sentry(double x, double y, ControlCenter c) {
		super(x, y, Creatures.DEFAULT_CREATURE_WIDTH / 2, Creatures.DEFAULT_CREATURE_HEIGHT / 2, c);

		health = 200;
		speed = 3.5;
		damage = 80;
		knockValue = 8;
		name = "sentry";
		attackBoundSize = 1500;
		type = "creatures";
		weight = 10;
		
		bounds.x = 10;
		bounds.y = 35;
		bounds.width = 50;
		bounds.height = 10;

		left = new Animation(125, Assets.sentryLeft, true);
		right = new Animation(125, Assets.sentryRight, true);
		
		combatXPDropped = (int)(Math.random()*(Player.getPlayerData().getIntelligence()*2))/4;

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
		int randDrop1 = CT.random(0, 3);
		for (int i = 0; i < randDrop1; i++)
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Food.spikeItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub

	}

}
