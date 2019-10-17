package creatures;

import java.awt.Graphics;

import alphaPackage.ControlCenter;
import graphics.Animation;
import graphics.Assets;
import graphics.CT;
import items.Food;
import items.Item;

public class MutatedChicken extends Creatures {

	public MutatedChicken(double x, double y, ControlCenter c) {

		super(x, y, Creatures.DEFAULT_CREATURE_WIDTH, Creatures.DEFAULT_CREATURE_HEIGHT, c);

		health = 100;
		speed = 1.5;
		resistance = 0;
		intimidation = 10;
		attackBoundSize = 800;
		name = "mutatedChicken";
		type = "creatures";
		weight = 10;

		bounds.x = Creatures.DEFAULT_CREATURE_WIDTH / 6;
		bounds.y = Creatures.DEFAULT_CREATURE_HEIGHT / 3 * 2;
		bounds.width = Creatures.DEFAULT_CREATURE_WIDTH / 6 * 4;
		bounds.height = Creatures.DEFAULT_CREATURE_HEIGHT / 6;

		left = new Animation(200, Assets.mutatedChicken, true);
		right = new Animation(200, CT.flip(Assets.mutatedChicken), true);
		
		combatXPDropped = (int)(5 * (double)Player.getPlayerData().getIntelligence()/10);

	}

	@Override
	public void tick() {

		ChickenAI();
		move();

		left.tick();
		right.tick();

	}

	public void ChickenAI() {

		if (c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
				.getEntityManager().getPlayer().getBounds().intersects(attackBound()))

			escape(attackBound(), false);

		else

			natural();

	}

	@Override
	public void render(Graphics g) {

		g.drawImage(getCurrentAnimation(), (int) (x - c.getGameCamera().getxOffset()),
				(int) (y - c.getGameCamera().getyOffset()), width, height, null);

	}

	public void Die() {
		c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
		.getItemManager().addItem(Food.suspicousChickenItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));

		int randFeather = CT.random(2, 6);
		for (int i = 0; i < randFeather; i++) {
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Item.featherItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));

		}

	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub

	}

}
