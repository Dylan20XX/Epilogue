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

public class DesertScorpion extends Creatures {
	
	private boolean buried = true;
	private boolean awakening = false; //set to true as scorpion is rising up
	private long lastAwakenTimer, awakenCooldown = 1500, awakenTimer = 0;
	private Animation smoke = new Animation(50, Assets.bossSmoke, true);
	
	public DesertScorpion(double x, double y, ControlCenter c) {
		super(x, y, Creatures.DEFAULT_CREATURE_WIDTH / 2 * 5, Creatures.DEFAULT_CREATURE_HEIGHT * 2, c);

		health = 950;
		speed = 2.2;
		damage = 250;
		resistance = 40;
		knockValue = 10;
		attackBoundSize = 400;
		name = "desertScorpion";
		type = "creatures";
		weight = 85;

		bounds.x = 15;
		bounds.y = 130;
		bounds.width = 150;
		bounds.height = 45;
		
		left = new Animation(200, Assets.scorpion, true);
		right = new Animation(200, CT.flip(Assets.scorpion), true);
		
		combatXPDropped = (int)(10 * (double)Player.getPlayerData().getIntelligence()/10);

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
			attackBoundSize = 700;
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
			if (damageBound().intersects(Player.getPlayerData().getBounds())) 
				knockbackPlayer(this);
			
			chase(1);
		} else if(c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getEntityManager().getPlayer()
				.getBounds().intersects(attackBound()) && buried && !awakening) { 
			lastAwakenTimer = System.currentTimeMillis();
			awakening = true;
		} else {
			natural();
		}
 
	}

	@Override
	public void render(Graphics g) {
		
		if(!buried) {
			g.drawImage(getCurrentAnimation(), (int) (x - c.getGameCamera().getxOffset()),
					(int) (y - c.getGameCamera().getyOffset()), width + 32, height + 32, null);
		} else if(awakening) {
			g.drawImage(smoke.getCurrentFrame(), (int) (x - c.getGameCamera().getxOffset()),
					(int) (y - c.getGameCamera().getyOffset()), width + 32, height + 32, null);
			smoke.tick();
			g.drawImage(Assets.sandCreepGround[0], (int) (x - c.getGameCamera().getxOffset() - 32),
					(int) (y - c.getGameCamera().getyOffset() - 32), width + 64, height + 64, null);
		} else {
			g.drawImage(Assets.sandCreepGround[0], (int) (x - c.getGameCamera().getxOffset() - 32),
					(int) (y - c.getGameCamera().getyOffset() - 32), width + 64, height + 64, null);
		}
		
		//Add image for when scropion is underground
		
		
		Graphics2D g2d = (Graphics2D)g;
		g.setColor(Color.BLUE);
		g2d.draw(getBounds());
		g.setColor(Color.GREEN);
		g2d.draw(attackBound());

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
