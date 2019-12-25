package creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import alphaPackage.ControlCenter;
import audio.AudioPlayer;
import graphics.Animation;
import graphics.Assets;
import graphics.CT;
import items.Food;
import staticEntity.SentrySpike;

public class DesertScorpion extends Creatures {
	
	private boolean buried = true;
	private boolean awakening = false; //set to true as scorpion is rising up
	private long lastAwakenTimer, awakenCooldown = 1500, awakenTimer = 0;
	private long lastattackTimer, attackCooldown = 2000, attackTimer = attackCooldown;
	
	private Animation smoke = new Animation(50, Assets.bossSmoke, true);
	
	public DesertScorpion(double x, double y, ControlCenter c) {
		super(x, y, Creatures.DEFAULT_CREATURE_WIDTH * 5, Creatures.DEFAULT_CREATURE_HEIGHT * 5, c);

		health = 2550;
		speed = 2.8;
		damage = 250;
		resistance = 80;
		knockValue = 30;
		attackBoundSize = 400;
		attackCooldown = 1500;
		name = "desertScorpion";
		type = "creatures";
		weight = 155;

		bounds.width = width - 10;
		bounds.height = height/5;
		bounds.x = 5;
		bounds.y = height - bounds.height*2;
		
		left = new Animation(200, Assets.scorpion, true);
		right = new Animation(200, CT.flip(Assets.scorpion), true);
		
		combatXPDropped = (int)(10 + Math.random()*(Player.getPlayerData().getIntelligence()*2));

	}

	@Override
	public void tick() {
		AI();
		if (move && !buried)
			move();
		left.tick();
		right.tick();
		
		if(buried) {
			attackBoundSize = 400;
		} else {
			attackBoundSize = 1200;
		}
		
		if(awakening) {
			canMove = false;
			awaken();
		}
		
	}
	
	private void awaken() {
		awakenTimer += System.currentTimeMillis() - lastAwakenTimer;
		lastAwakenTimer = System.currentTimeMillis();

		if (awakenTimer < awakenCooldown)
			return;

		buried = false;
		awakening = false;
		canMove = true;
		bounds.y = height/2-20;
		bounds.height = height/2;
	}
	
	public void AI() {
		
		if(c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getEntityManager().getPlayer()
				.getBounds().intersects(attackBound()) && !buried) { 
			
			attack();
			
		} else if(c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getEntityManager().getPlayer()
				.getBounds().intersects(attackBound()) && buried && !awakening) { 
			lastAwakenTimer = System.currentTimeMillis();
			awakening = true;
			c.getGameCamera().shake(50);
			AudioPlayer.playAudio("audio/scorpion.wav");
		} else {
			natural();
		}
 
	}
	
	public void attack() {

		if (damageBound().intersects(Player.getPlayerData().getBounds())) 
			knockbackPlayer(this);
		
		chase(1.5);

		attackTimer += System.currentTimeMillis() - lastattackTimer;
		lastattackTimer = System.currentTimeMillis();

		if (attackTimer < attackCooldown)
			return;
		
		for(int i = 0; i < c.getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().size(); i++) {
			if(c.getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().get(i).getBounds().intersects(damageBound())) {
				if(!c.getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().get(i)
						.equals(c.getGameState().getWorldGenerator().getEntityManager().getPlayer()) && 
						!c.getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().get(i).equals(this) &&
						!(c.getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().get(i) instanceof VileSpawn) &&
						!(c.getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().get(i) instanceof SentryBroodMother) &&
						!(c.getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().get(i) instanceof AwakenedSentinel)) {
					c.getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().get(i).hurt(10000);
				}
			}
		}
		
		attackTimer = 0;
		
	}

	@Override
	public void render(Graphics g) {
		
		if(!buried) {
			g.drawImage(getCurrentAnimation(), (int) (x - c.getGameCamera().getxOffset()),
					(int) (y - c.getGameCamera().getyOffset()), width, height, null);
		} else if(awakening) {
			g.drawImage(smoke.getCurrentFrame(), (int) (x - c.getGameCamera().getxOffset()),
					(int) (y - c.getGameCamera().getyOffset()), width, height, null);
			smoke.tick();
			g.drawImage(Assets.sandCreepGround[0], (int) (x - c.getGameCamera().getxOffset() - width/2),
					(int) (y - c.getGameCamera().getyOffset()) - height, width*2, height*2, null);
		} else {
			g.drawImage(Assets.sandCreepGround[0], (int) (x - c.getGameCamera().getxOffset() - width/2),
					(int) (y - c.getGameCamera().getyOffset()) - height, width*2, height*2, null);
		}
		
		/*Add image for when scorpion is underground
		Graphics2D g2d = (Graphics2D)g;
		g.setColor(Color.BLUE);
		g2d.draw(getBounds());
		g.setColor(Color.GREEN);
		g2d.draw(attackBound());
		*/

	}

	@Override
	public void Die() {
		AudioPlayer.playAudio("audio/scorpion.wav");
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
