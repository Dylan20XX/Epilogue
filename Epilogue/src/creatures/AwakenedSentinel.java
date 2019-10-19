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


public class AwakenedSentinel extends Creatures {

	private int shootBoundSize = 2000;
	private boolean awakened = true;
	private boolean awakening = false; //set to true as sentinel is waking up
	private long lastAwakenTimer, awakenCooldown = 8000, awakenTimer = 0;
	private Animation smoke = new Animation(50, CT.flip(Assets.bossSmoke), true);

	private long lastAttackTimer, attackCooldown = 100, attackTimer = 0; //rate of fire

	//how long it takes to perform a action
	private long lastActionTimer, actionCooldown = 7000, actionTimer = actionCooldown; //immediately perform an action

	private long lastChargeTimer, chargeCooldown = 2000, chargeTimer = 0; //time required to charge an attack

	private long lastDestroyTimer, DestroyCooldown = 2000, DestroyTimer = 0; //time required to check objects intersecting it

	private long lastTrackTimer, trackCooldown = 25, trackTimer = 0; 
	private int selectedAction;
	private boolean firing = false, smokeOn = false, soundPlayed = false, sound2Played = false;
	private int movePool;
	private Random r = new Random();
	private double shootAngle;
	private double originX, originY;
	private double[] previousOriginX = new double[40], previousOriginY = new double[40];
	private int index = 0;
	private int secondaryIndex = 20;

	public AwakenedSentinel(double x, double y, ControlCenter c) {
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

		name = "awakenedSentinel";
		type = "creatures";

		left = new Animation(475, Assets.awakenedSentinel, true);
		right = new Animation(475, CT.flip(Assets.awakenedSentinel), true);
		
		combatXPDropped = (int)(200 * (double)Player.getPlayerData().getIntelligence()/10);

	}

	@Override
	public void tick() {

		if(awakened) {

			trackTimer += System.currentTimeMillis() - lastTrackTimer;
			lastTrackTimer = System.currentTimeMillis();

			if (trackTimer > trackCooldown) {
				previousOriginX[index] = originX;
				previousOriginY[index] = originY;
				originX = x + bounds.width/2 - c.getGameCamera().getxOffset() + 84;
				originY = y + bounds.height/2 - c.getGameCamera().getyOffset() + bounds.height/3*2 - 45;

				index++;
				if(index > 39)
					index = 0;

				secondaryIndex++;
				if(secondaryIndex > 39)
					secondaryIndex = 0;

				trackTimer = 0;
			}

			if (movePool == 0) { // more likely to fire at player if player is closer

				if(selectedAction == 0 || selectedAction == 1 || selectedAction == 2) { //fire at player

					if (!(chargeTimer < chargeCooldown)) {
						shoot();
						sound2Played = false;
					} else {

						chargeTimer += System.currentTimeMillis() - lastChargeTimer;
						lastChargeTimer = System.currentTimeMillis();

						if (chargeTimer < chargeCooldown) {
							//play sound effect for change
							if(!sound2Played) {
								AudioPlayer.playAudio("audio/powerUp.wav");
								if(CT.random(1, 4) == 1) {
									AudioPlayer.playAudio("audio/exterminate.wav");
								}
								sound2Played = true;
							}
						}

					}

				} else if(selectedAction == 3) { //chase player

					speed = 0.7;

					AI();
					if (move)
						move();
					left.tick();
					right.tick();

				}

			} else if(movePool == 1) { //more likely to chase player if player is in attack bound and outside of shoot bound
				
				if(selectedAction == 0 || selectedAction == 1 || selectedAction == 2) { //chase player in range

					speed = 3;

					AI();
					if (move)
						move();
					left.tick();
					right.tick();

				} else if(selectedAction == 3) { //fire at player

					//shoot();

					if (!(chargeTimer < chargeCooldown)) {
						shoot();
						sound2Played = false;
					} else {

						chargeTimer += System.currentTimeMillis() - lastChargeTimer;
						lastChargeTimer = System.currentTimeMillis();

						if(!sound2Played) {
							AudioPlayer.playAudio("audio/powerUp.wav");
							if(CT.random(1, 4) == 1) {
								AudioPlayer.playAudio("audio/exterminate.wav");
							}
							sound2Played = true;
						}

					}				

				}

			}

			actionTimer += System.currentTimeMillis() - lastActionTimer;
			lastActionTimer = System.currentTimeMillis();

			if (actionTimer < actionCooldown)
				return;


			if(shootBound().intersects(Player.getPlayerData().getBounds())) { //More likely to shoot if player is closer
				movePool = 0;
			}else if(attackBound().intersects(Player.getPlayerData().getBounds())){ //More likely to chase if player is far away
				movePool = 1;
			}

			selectedAction = r.nextInt(4);
			firing = false;
			lastChargeTimer = System.currentTimeMillis();
			chargeTimer = 0;

			actionTimer = 0;

		}

		if(awakening) {
			awaken();
		}

	}

	private void shoot() {

		firing = true;

		double playerX = Player.getPlayerData().getXLoc();
		double playerY = Player.getPlayerData().getYLoc();

		double originX = x + bounds.width/2 - c.getGameCamera().getxOffset() + 84; //+ bounds.width/3*2
		double originY = y + bounds.height/2 - c.getGameCamera().getyOffset() + bounds.height/3*2 - 45; //+ bounds.height/3*2

		//shootAngle = Math.atan2(playerX - originX, playerY - originY); //change to origin to track player (secondary index follows behind player movement)
		shootAngle = Math.atan2(playerX - previousOriginX[index], playerY - previousOriginY[index]);
		int bulletX = (int) (originX + c.getGameCamera().getxOffset()); //+ bounds.width/3*2 or + bounds.width/3
		int bulletY = (int) (originY + c.getGameCamera().getyOffset()); //+ bounds.height/3*2 or + bounds.height/3

		if(playerX > originX) {
			direction = 1;
		} else
			direction = 0;

		attackTimer += System.currentTimeMillis() - lastAttackTimer;
		lastAttackTimer = System.currentTimeMillis();

		if (attackTimer < attackCooldown)
			return;

		//AudioPlayer.playAudio("audio/minigun.wav");
		AudioPlayer.playAudio("audio/rapidPulse.wav");
		c.getGameState().getWorldGenerator().getEntityManager().addEntity(new RapidPulseSentinel(bulletX, 
				bulletY, 12, 4, (shootAngle - Math.PI/28) + Math.random()*(Math.PI/28*2), c));

		attackTimer = 0; // cool down resets

	}

	public void AI() {
		DestroyTimer += System.currentTimeMillis() - lastDestroyTimer;
		lastDestroyTimer = System.currentTimeMillis();
		
		if(DestroyTimer > DestroyCooldown) {
			for(int i = 0; i < c.getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().size(); i++) {
				if(c.getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().get(i).getBounds().intersects(breakBound())) {
					if(!c.getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().get(i)
							.equals(c.getGameState().getWorldGenerator().getEntityManager().getPlayer()) && 
							!c.getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().get(i).equals(this)) {
						c.getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().get(i).hurt(10000);
					}
				}
			}
			DestroyTimer = 0;
		}

		if (damageBound().intersects(Player.getPlayerData().getBounds())) 
			knockbackPlayer(this);

		chase(1);

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

		if(awakened) {
			g.drawImage(getCurrentAnimation(), (int) (x - c.getGameCamera().getxOffset()),
					(int) (y - c.getGameCamera().getyOffset()), width + 32, height + 32, null);

			if(firing) {
				if (direction == 1) {
					at.translate(0, -45);
					at.rotate(-shootAngle - Math.toRadians(95), 
							(int) (x - c.getGameCamera().getxOffset() + (width - bounds.width) / 2 + bounds.width / 2) + 5, 
							(int) (y - c.getGameCamera().getyOffset() + (height - bounds.height) / 2 + bounds.height / 2));
					g2d.setTransform(at);
					g2d.setTransform(at);
					g2d.drawImage(Assets.awakenedSentinelGun[1], (int) (x - c.getGameCamera().getxOffset()), (int) (y - c.getGameCamera().getyOffset()), width + 32, height + 32, null);

				} else {
					at.translate(0, -45);
					at.rotate(-shootAngle + Math.toRadians(95), 
							(int) (x - c.getGameCamera().getxOffset() + (width - bounds.width) / 2 + bounds.width / 2) + 5, 
							(int) (y - c.getGameCamera().getyOffset() + (height - bounds.height) / 2 + bounds.height / 2));
					g2d.setTransform(at);
					g2d.drawImage(CT.flip(Assets.awakenedSentinelGun[1]), (int) (x - c.getGameCamera().getxOffset()), (int) (y - c.getGameCamera().getyOffset()), width + 32, height + 32, null);

				}
			} else {
				if (direction == 1) {
					g.drawImage(CT.flip(Assets.awakenedSentinelGun[0]), (int) (x - c.getGameCamera().getxOffset()),
							(int) (y - c.getGameCamera().getyOffset()), width + 32, height + 32, null);
				} else {
					g.drawImage(Assets.awakenedSentinelGun[0], (int) (x - c.getGameCamera().getxOffset()),
							(int) (y - c.getGameCamera().getyOffset()), width + 32, height + 32, null);
				}
			}

			g2d.setTransform(backup);
		} else if(awakening) { //sentinel powering on
			
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
		/*
		double originX = x + bounds.x + bounds.width/2 - c.getGameCamera().getxOffset();
		double originY = y + bounds.y + bounds.height/2 - c.getGameCamera().getyOffset();
		g.drawRect((int) originX, (int) originY, 5, 5);

		int bulletX = (int) (originX + c.getGameCamera().getxOffset() + bounds.width/3); //+ bounds.width/3*2 
		int bulletY = (int) (originY + c.getGameCamera().getyOffset() + bounds.height/3); //+ bounds.height/3*2
		g.drawRect((int) (bulletX - c.getGameCamera().getxOffset()), (int) (bulletY - c.getGameCamera().getyOffset()), 5, 5);
		 */
	}

	@Override
	public void Die() {
		AudioPlayer.playAudio("audio/explode.wav");
		MusicPlayer.StopMusic();
		for(int i = 0; i < CT.random(15, 30); i++) {
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getItemManager()
			.addItem(Item.gearItem.createNew((int) x + bounds.x + CT.random(0, bounds.width),
					(int) y + bounds.y + CT.random(0, bounds.height)));
		}
		for(int i = 0; i < CT.random(3, 8); i++) {
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getItemManager()
			.addItem(Item.metalPlateItem.createNew((int) x + bounds.x + CT.random(0, bounds.width),
					(int) y + bounds.y + CT.random(0, bounds.height)));
		}
	}

	@Override
	public void interact() {

		//Currently a semiconductor is used to awaken the sentinel but the item can be changed here
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
