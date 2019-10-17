package creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import alphaPackage.ControlCenter;
import ammo.SandProjectileUp;
import audio.AudioPlayer;
import graphics.Animation;
import graphics.Assets;
import graphics.CT;
import items.Food;
import items.Item;
import world.WorldGenerator;

public class SandCreep extends Creatures {

	public static int width = Creatures.DEFAULT_CREATURE_WIDTH;
	public static int height = Creatures.DEFAULT_CREATURE_HEIGHT;
	
	public long lastAttackTimer, AttackCooldown = 1500, AttackTimer = AttackCooldown;
	 
	public SandCreep(double x, double y, ControlCenter c) {
		super(x, y, width, height, c);

		health = 200;
		speed = 0;
		damage = 170;
		knockValue = 13;
		name = "sandCreep";
		attackBoundSize = 800;
		type = "creatures";
		weight = 60;
		
		bounds.x = width/6;
		bounds.y = height/4*3 - 15;
		bounds.width = width/6*4;
		bounds.height = height/4;

		left = new Animation(200, Assets.sandCreepling, true);
		right = new Animation(200, Assets.sandCreepling, true);

		combatXPDropped = (int)(15 * (double)Player.getPlayerData().getIntelligence()/10);
		
	}

	@Override
	public void tick() {
		AI();
		left.tick();
		right.tick();
	}

	public void AI() {

		if(c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getEntityManager().getPlayer()
				.getBounds().intersects(attackBound())) {
			
				AttackTimer += System.currentTimeMillis() - lastAttackTimer;
		        lastAttackTimer = System.currentTimeMillis();
		        
		        if (AttackTimer < AttackCooldown)
	                return;
		        
		        AudioPlayer.playAudio("audio/shootSand.wav");
		        
		        c.getGameState().getWorldGenerator().getEntityManager().addEntity(new SandProjectileUp(getX() + bounds.x + bounds.width/2-7,getY()+bounds.y, c));
				
				AttackTimer = 0;
				chasing = true;
			
		} else
			chasing = false;

	}

	@Override
	public void render(Graphics g) {
		g.drawImage(left.getCurrentFrame(), (int) (x - c.getGameCamera().getxOffset()),
				(int) (y - c.getGameCamera().getyOffset()), width, height, null);

	}

	@Override
	public void Die() {
		int randDrop1 = CT.random(0, 2);
		for (int i = 0; i < randDrop1; i++)
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Food.bugMeatItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub

	}

}
