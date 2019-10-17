package creatures;

import java.awt.Graphics;

import alphaPackage.ControlCenter;
import graphics.Animation;
import graphics.Assets;
import graphics.CT;
import items.Food;
import items.Item;

public class Deer extends Creatures {
	
	public Deer(double x, double y, ControlCenter c) {

		super(x, y, Creatures.DEFAULT_CREATURE_WIDTH * 2, Creatures.DEFAULT_CREATURE_HEIGHT * 2, c);

		health = 500;
		speed = 2.5;
		resistance = 0;
		intimidation = 10;
		attackBoundSize = 500;
		name = "deer";
		type = "creatures";
		weight = 55;

		bounds.x = width/9 + 20;
        bounds.y = height/43 - 15;
        bounds.width = width/95;
        bounds.height = height/4+5;

		left = new Animation(400, CT.flip(Assets.deer), true);
		right = new Animation(400, Assets.deer, true);
		
		combatXPDropped = (int)(10 * (double)Player.getPlayerData().getIntelligence()/10);

	}

	@Override
	public void tick() {

		DeerAI();
		if (move)
			move();

		left.tick();
		right.tick();

	}

	public void DeerAI() {

		if(health != ogHealth)
			escape(attackBound(), false);
		
		else if (c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
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

		g.drawImage(getCurrentAnimation(), (int) (x - c.getGameCamera().getxOffset()),
				(int) (y - c.getGameCamera().getyOffset()), width, height, null);

	}

	public void Die() {
		
		int randMeat = CT.random(1, 3);
		for(int i = 0; i < randMeat; i++)
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Food.rawMorselItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));

	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub

	}
}
