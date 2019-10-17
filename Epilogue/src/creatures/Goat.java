package creatures;

import java.awt.Graphics;
import java.awt.Rectangle;

import alphaPackage.ControlCenter;
import graphics.Animation;
import graphics.Assets;
import graphics.CT;
import items.Food;
import items.Item;

public class Goat extends Creatures {

	public Goat(double x, double y, ControlCenter c) {
		super(x, y, Creatures.DEFAULT_CREATURE_WIDTH, Creatures.DEFAULT_CREATURE_HEIGHT, c);

		health = 900;
		damage = 140;
		speed = 1;
		knockValue = 12;
		attackBoundSize = 150;
		resistance = 10;
		name = "goat";
		type = "creatures";
		weight = 90;
		ogHealth = health;
		
		bounds.x = width/5-4;
        bounds.y = height - height/4;
        bounds.width = width/3*4-5;
        bounds.height = height/4*2;

		left = new Animation(125, Assets.goat, true);
		right = new Animation(125, CT.flip(Assets.goat), true);
		
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
		
		if(health != ogHealth && health > ogHealth/3 || Player.getPlayerData().isGoatAggro()) {
			attackBoundSize = 1600;
			if(c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getEntityManager().getPlayer()
					.getBounds().intersects(attackBound())) {
				
				if (damageBound().intersects(Player.getPlayerData().getBounds())) 
					knockbackPlayer(this);
				
				chase(2);
				
			}
		} else if(health <= ogHealth/3) {
			escape(attackBound(), false);
		} else 
			if(c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getEntityManager().getPlayer()
					.getBounds().intersects(cautionBound(attackBoundSize*3 - 50))) {
				if(c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getEntityManager().getPlayer()
						.getBounds().intersects(attackBound())) {
					
					if (damageBound().intersects(Player.getPlayerData().getBounds())) 
						knockbackPlayer(this);
					
					chase(2);
					
				} else {
					caution();
				}
			}
			else
				natural();


	}
	
	public Rectangle escapeBound() {
		return new Rectangle(
				(int) (x + bounds.x - c.getGameCamera().getxOffset()) - attackBoundSize*2 + bounds.width/2,
				(int) (y + bounds.y - c.getGameCamera().getyOffset() - attackBoundSize*2) + bounds.height/2,
				attackBoundSize*4, attackBoundSize*4);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(getCurrentAnimation(), (int) (x - c.getGameCamera().getxOffset()),
				(int) (y - c.getGameCamera().getyOffset()), width + 32, height + 32, null);

	}

	@Override
	public void Die() {
		System.out.println("reached");
		int randDrop1 = CT.random(1, 2);
		int randDrop2 = CT.random(1, 4);
		for (int i = 0; i < randDrop1; i++)
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Food.rawMorselItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
		for (int i = 0; i < randDrop2; i++)
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Item.furItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub

	}

}
