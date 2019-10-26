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

public class Boar extends Creatures {

	private static int width = Creatures.DEFAULT_CREATURE_WIDTH / 2 * 3;
	private static int height = Creatures.DEFAULT_CREATURE_HEIGHT / 2 * 3;
	
	private int cautionBoundSize = 300;
	
	public Boar(double x, double y, ControlCenter c) {
		super(x, y, width, height, c);

		health = 1200;
		speed = 2;
		damage = 210;
		knockValue = 13;
		name = "boar";
		attackBoundSize = 300;
		type = "creatures";
		weight = 60;
		
		bounds.x = width/5;
		bounds.y = height/2 + 15;
		bounds.width = width - width/5 + 5;
		bounds.height = height/4;

		left = new Animation(300, Assets.boar, true);
		right = new Animation(300, CT.flip(Assets.boar), true);
		
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
		
		if(health < ogHealth/3)
			escape(attackBound(), false);

		else if(c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getEntityManager().getPlayer()
				.getBounds().intersects(cautionBound(cautionBoundSize)))
			if(c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getEntityManager().getPlayer()
					.getBounds().intersects(attackBound())) {
				
				attackBoundSize = cautionBoundSize * 2;
				
				if (damageBound().intersects(Player.getPlayerData().getBounds())) 
					knockbackPlayer(this);
				
				chase(2);
				
			} else {
				caution();
			}
			
		else {
			attackBoundSize = 300;
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
		int randDrop1 = CT.random(1, 2);
		int randDrop2 = CT.random(1, 4);
		for (int i = 0; i < randDrop1; i++) {
			if(CT.random(1, 2) == 1)
				c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
						.getItemManager().addItem(Food.rawMeatItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
			else
				c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
				.getItemManager().addItem(Food.suspicousMeatItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
		}
		for (int i = 0; i < randDrop2; i++)
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
					.getItemManager().addItem(Item.leatherItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
		for (int i = 0; i < CT.random(0, 2); i++)
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
					.getItemManager().addItem(Item.furItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));

	}
	
	@Override
	public void interact() {
		// TODO Auto-generated method stub

	}

}