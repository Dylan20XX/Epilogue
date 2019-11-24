package creatures;

import java.awt.Graphics;

import alphaPackage.ControlCenter;
import graphics.Animation;
import graphics.Assets;
import graphics.CT;
import items.Food;
import items.Item;

public class Phasmatodea extends Creatures {

	public Phasmatodea(double x, double y, ControlCenter c) {
		super(x, y, Creatures.DEFAULT_CREATURE_WIDTH * 3, Creatures.DEFAULT_CREATURE_HEIGHT * 3, c);

		health = 750;
		speed = 3.5;
		resistance = 80;
		knockValue = 20;
		damage = 220;
		attackBoundSize = 300;
		name = "phasmatodea";
		type = "creatures";
		weight = 25;

		bounds.x = 30;
		bounds.y = 120;
		bounds.width = 170;
		bounds.height = 30;
		
		left = new Animation(200, Assets.phasmatodea, true);
		right = new Animation(200, CT.flip(Assets.phasmatodea), true);

		combatXPDropped = (int)(14 * (double)Player.getPlayerData().getIntelligence()/10);
		
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
		} else if(health != ogHealth) {
			chase(0.8);
		}

	}

	@Override
	public void render(Graphics g) {
		g.drawImage(getCurrentAnimation(), (int) (x - c.getGameCamera().getxOffset()),
				(int) (y - c.getGameCamera().getyOffset()), width + 32, height + 32, null);

	}

	@Override
	public void Die() {
		int randDrop1 = CT.random(1, 3);
		for (int i = 0; i < randDrop1; i++)
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Food.bugMeatItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub
		
	}
}
