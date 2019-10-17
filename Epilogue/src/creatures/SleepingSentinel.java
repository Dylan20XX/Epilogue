package creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.Random;

import alphaPackage.ControlCenter;
import ammo.RapidPulse;
import ammo.RapidPulseSentinel;
import audio.AudioPlayer;
import audio.BackgroundPlayer;
import audio.MusicPlayer;
import graphics.Animation;
import graphics.Assets;
import graphics.CT;
import items.Food;
import items.Item;


public class SleepingSentinel extends Creatures {

	private int shootBoundSize = 2000;
	private boolean awakened = false;
	private boolean awakening = false; //set to true as sentinel is waking up
	private long lastAwakenTimer, awakenCooldown = 8000, awakenTimer = 0;
	private Animation smoke = new Animation(50, CT.flip(Assets.bossSmoke), true);

	private boolean smokeOn = false, soundPlayed = false, sound2Played = false;

	public SleepingSentinel(double x, double y, ControlCenter c) {
		super(x, y, Creatures.DEFAULT_CREATURE_WIDTH * 6, Creatures.DEFAULT_CREATURE_HEIGHT * 6, c);

		health = 8500;
		speed = 0.7;
		knockValue = 10;
		attackBoundSize = 8000;
		weight = 500;

		bounds.x = width/4+12;
		bounds.y = height/2 + height/4;
		bounds.width = width/2+10;
		bounds.height = height/4;

		resistance = 105;

		name = "sleepingSentinel";
		type = "creatures";

		left = new Animation(475, Assets.awakenedSentinel, true);
		right = new Animation(475, CT.flip(Assets.awakenedSentinel), true);

		combatXPDropped = (int)(200 * (double)Player.getPlayerData().getIntelligence()/10);
		
	}

	@Override
	public void tick() {

		if(awakening) {
			awaken();
		} else if(awakened) {
			setActive(false);
			c.getGameState().getWorldGenerator().getEntityManager().addEntity(new AwakenedSentinel(x, y, c));
		}

	}

	private void awaken() {
		awakenTimer += System.currentTimeMillis() - lastAwakenTimer;
		lastAwakenTimer = System.currentTimeMillis();

		if (awakenTimer < awakenCooldown)
			return;

		AudioPlayer.playAudio("audio/exterminate.wav");
		awakened = true;
		awakening = false;
		bounds.y = height/2-20;
		bounds.height = height/2;
	}

	public Rectangle shootBound() {
		return new Rectangle(
				(int) (x + bounds.x - c.getGameCamera().getxOffset()) - shootBoundSize / 2 + bounds.width / 2,
				(int) (y + bounds.y - c.getGameCamera().getyOffset() - shootBoundSize / 2) + bounds.height / 2,
				shootBoundSize, shootBoundSize);
	}

	public Rectangle breakBound() {

		return new Rectangle((int)(x - c.getGameCamera().getxOffset()) - 10 + bounds.x, (int)(y - c.getGameCamera().getyOffset()) - 10 + bounds.y, 
				bounds.width + 20, bounds.height + 20);

	}

	@Override
	public void render(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		AffineTransform backup = g2d.getTransform();
		AffineTransform at = g2d.getTransform();
		AffineTransform at2 = g2d.getTransform();

		if(awakening) { //sentinel powering on
			
			new java.util.Timer().schedule(new java.util.TimerTask() {
				@Override
				public void run() {
					soundPlayed = true;
					smokeOn = true;
					
				}
			}, 2000);
			
			g.drawImage(Assets.sleepingSentinel[1], (int) (x - c.getGameCamera().getxOffset()),
					(int) (y - c.getGameCamera().getyOffset()), width + 32, height + 32, null);
			
			if(smokeOn) {
				g.drawImage(smoke.getCurrentFrame(), (int) (x - c.getGameCamera().getxOffset()),
						(int) (y - c.getGameCamera().getyOffset()), width + 32, height + 32, null);
				g.drawImage(smoke.getCurrentFrame(), (int) (x - c.getGameCamera().getxOffset() - 64),
						(int) (y - c.getGameCamera().getyOffset()), width + 32, height + 32, null);
				g.drawImage(smoke.getCurrentFrame(), (int) (x - c.getGameCamera().getxOffset() + 64),
						(int) (y - c.getGameCamera().getyOffset()), width + 32, height + 32, null);
				g.drawImage(smoke.getCurrentFrame(), (int) (x - c.getGameCamera().getxOffset() - 32),
						(int) (y - c.getGameCamera().getyOffset() + 32), width + 32, height + 32, null);
				g.drawImage(smoke.getCurrentFrame(), (int) (x - c.getGameCamera().getxOffset() + 32),
						(int) (y - c.getGameCamera().getyOffset() + 32), width + 32, height + 32, null);
				smoke.tick();

			}
			
			if(soundPlayed) {
				c.getGameCamera().shake(50);
				soundPlayed = false;
			}
			
			
		} else { //sleeping sentinel
			g.drawImage(Assets.sleepingSentinel[0], (int) (x - c.getGameCamera().getxOffset()),
					(int) (y - c.getGameCamera().getyOffset()), width + 32, height + 32, null);
		}
/*
		g.setColor(Color.blue);
		g2d.draw(getBounds());
		g.setColor(Color.GREEN);
		g2d.draw(breakBound());
		g2d.draw(shootBound());
		g.setColor(Color.RED);
		g2d.draw(attackBound());
*/
	}

	@Override
	public void Die() {
		
	}

	@Override
	public void interact() {

		//Battery is used to awaken the sentinel
		if(Player.getPlayerData().getHands().getHand() == Item.batteryItem && (awakened == false && awakening == false)) {
			Player.getPlayerData().getInventory().removeItem(Player.getPlayerData().getHands().getHand());
			MusicPlayer.StopMusic();
			BackgroundPlayer.StopAudio();
			MusicPlayer.playMusic("audio/awakenedSentinelTheme.wav");
			AudioPlayer.playAudio("audio/powerUp.wav");
			lastAwakenTimer = System.currentTimeMillis();
			awakening = true;
		}

	}
}
