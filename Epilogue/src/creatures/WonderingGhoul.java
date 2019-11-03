package creatures;

import java.awt.Graphics;

import alphaPackage.ControlCenter;
import audio.AudioPlayer;
import entity.Entity;
import graphics.Animation;
import graphics.Assets;
import graphics.CT;
import items.Food;
import items.Item;
import items.ItemManager;
import items.Weapon;
import staticEntity.StaticEntity;

public class WonderingGhoul extends Creatures {

	public WonderingGhoul(double x, double y, ControlCenter c) {
		super(x, y, Creatures.DEFAULT_CREATURE_WIDTH/6*5, Creatures.DEFAULT_CREATURE_HEIGHT/6*5, c);

		health = (int)Math.random()*600 + 400;
		damage = (int)(Math.random()*100) + 80;
		speed = Math.random()*0.8 + 0.5;
		knockValue = 4;
		attackBoundSize = 1800;
		resistance = (int)(Math.random()*20);
		attackCooldown = 1200;
		name = "wonderingGhoul";
		type = "creatures";
		weight = 55;
		
		bounds.x = width/2;
		bounds.y = height-5;
		bounds.width = (int) (26 * ControlCenter.scaleValue);
		bounds.height = (int) (30 * ControlCenter.scaleValue);
		
		int animationSpeed = (int)Math.random()*200 + 200;
		
		right = new Animation(animationSpeed, Assets.ghoul, true);
		left = new Animation(animationSpeed, CT.flip(Assets.ghoul), true);

		combatXPDropped = (int)(Math.random()*(Player.getPlayerData().getIntelligence()+1))/2;
		
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
		int randDrop1 = CT.random(1, 10);
		if(randDrop1 == 1) {
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(ItemManager.trashList.get(CT.random(0, ItemManager.trashList.size()-1)).
					createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
		}
	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub
		
	}
}
