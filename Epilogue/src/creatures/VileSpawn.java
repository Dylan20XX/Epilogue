package creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import alphaPackage.ControlCenter;
import ammo.SandProjectile2Up;
import ammo.Wormhole;
import audio.AudioPlayer;
import audio.BackgroundPlayer;
import audio.MusicPlayer;
import graphics.Animation;
import graphics.Assets;
import graphics.CT;
import inventory.MessageBox;
import items.Food;
import items.Item;
import items.Ranged;
import items.Weapon;
import world.WorldGenerator;

public class VileSpawn extends Creatures {

	private static int width = Creatures.DEFAULT_CREATURE_WIDTH * 10;
	private static int height = Creatures.DEFAULT_CREATURE_HEIGHT * 10;

	private Animation vileSpawnOut = new Animation(150, Assets.sandCreepOut, true);
	private Animation vileSpawnIn = new Animation(150, Assets.sandCreepIn, true);
	private Animation vileSpawnActive = new Animation(150, Assets.sandCreepActive, true);
	private Animation vileSpawnUnderground = new Animation(150, Assets.sandCreepUnderground, true);
	private Animation currentAnimation = vileSpawnUnderground;

	public long lastAAttackTimer, aattackCooldown = 80, AAttackTimer = aattackCooldown;
	public long lastswallowTimer, swallowCooldown = 50, swallowTimer = swallowCooldown;
	public boolean once = false, swallowed = false;

	public boolean shootEnabled = false;

	public VileSpawn(ControlCenter c) {
		super(Player.getPlayerData().getX() - width / 3 - (width / 3 / 2),
				Player.getPlayerData().getY() - (height / 2 + height / 5) - (height / 4) / 2, width, height, c);

		health = 15000;
		speed = 2.9;
		damage = 280;
		attackBoundSize = 800;
		knockValue = 13;
		name = "vileSpawn";
		type = "creatures";
		resistance = 85;
		weight = 60;

		bounds.x = width / 3 + 50;
		bounds.y = height / 2 + height / 5 + 30;
		bounds.width = 0;
		bounds.height = 0;

		chasing = true;

		left = new Animation(100, Assets.sandCreepOut, true);
		right = new Animation(100, CT.flip(Assets.sandCreepActive), true);

		MessageBox.addMessage("the Vilespawn has been avoken...");
		creepOut();

		BackgroundPlayer.StopAudio();
		MusicPlayer.playMusic("audio/vileSpawnTheme.wav");
		
		combatXPDropped = (int)(500 + Math.random()*(Player.getPlayerData().getIntelligence()*2));

	}

	@Override
	public void tick() {
		AI();
		currentAnimation.tick();
	}

	public void AI() {

		WorldGenerator.bossActive = true;
		if (!swallowed) {

			for (int i = 0; i < c.getGameState().getWorldGenerator().getEntityManager().entitiesInBound.size(); i++) {

				if (getBounds().intersects(
						c.getGameState().getWorldGenerator().getEntityManager().entitiesInBound.get(i).getBounds())
						&& c.getGameState().getWorldGenerator().getEntityManager().entitiesInBound.get(i) != this
						&& !c.getGameState().getWorldGenerator().getEntityManager().entitiesInBound.get(i).getName()
								.equals("worm hole")
								&& !(c.getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().get(i) instanceof AwakenedSentinel)
								&& !(c.getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().get(i) instanceof SentryBroodMother)
								&& !(c.getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().get(i) instanceof VileSpawn)) {
					if (!c.getGameState().getWorldGenerator().getEntityManager().entitiesInBound.get(i)
							.equals(Player.getPlayerData()) && currentAnimation.getIndex() > 4) {
						c.getGameState().getWorldGenerator().getEntityManager().entitiesInBound.get(i).setActive(false);
					}
				}

			}

			if (shootEnabled) {
				AAttackTimer += System.currentTimeMillis() - lastAAttackTimer;
				lastAAttackTimer = System.currentTimeMillis();

				if (AAttackTimer > aattackCooldown) {

					int randAttack = CT.random(1, 2);

					if (randAttack == 1) {

						AudioPlayer.playAudio("audio/shootSand.wav");
						c.getGameState().getWorldGenerator().getEntityManager()
								.addEntity(new SandProjectile2Up(
										getX() + bounds.x + bounds.width / 2 - 50 + CT.random(0, 100),
										getY() + bounds.y + bounds.height / 2 - 400,
										getX() + bounds.x + bounds.width / 2, getY() + bounds.y, c));

					}

					AAttackTimer = 0;

				}
			}

			if (currentAnimation.equals(vileSpawnOut)) {
				if (currentAnimation.getIndex() == 6 && Player.getPlayerData().getBounds().intersects(getBounds())) {
					
					MessageBox.addMessage("you have been swallowed...");
					MessageBox.addMessage("defeat the vilespawn to escape...");
					swallowed = true;
					WorldGenerator.swallowed = true;
					bounds.width += 400;
					bounds.height += 400;
					bounds.x -= 200;
					bounds.y -= 200;
					
				}
				if (currentAnimation.getIndex() == 14) {
					currentAnimation = vileSpawnActive;
					vileSpawnOut.setIndex(0);
				}
			}
			if (currentAnimation.equals(vileSpawnIn)) {
				if (currentAnimation.getIndex() == 14) {
					bounds.width = 0;
					bounds.height = 0;
					currentAnimation = vileSpawnUnderground;
					vileSpawnIn.setIndex(0);
					return;
				}
			}

			if (damageBound().intersects(Player.getPlayerData().getBounds()) && !swallowed
					&& !getBounds().intersects(Player.getPlayerData().getBounds()))
				knockbackPlayer(this);
		}

		else {
			
			swallowTimer += System.currentTimeMillis() - lastswallowTimer;
			lastswallowTimer = System.currentTimeMillis();

			if (swallowTimer > swallowCooldown) {
				Player.getPlayerData().setHealth(Player.getPlayerData().getHealth()-4);
				if(Player.getPlayerData().getHealth() <= 0) {
					Player.getPlayerData().Die();
				}
				swallowTimer = 0;
			}
			
		}
	}

	public void creepIn() {
		if (active && !swallowed) {
			currentAnimation = vileSpawnIn;

			new java.util.Timer().schedule(new java.util.TimerTask() {
				@Override
				public void run() {

					creepOut();

				}
			}, 3000);
		}
	}

	public void creepOut() {
		if (active && !swallowed) {
			if (!once) {
				once = true;
				// AudioPlayer.playAudio("audio/breakOut.wav");

				vileSpawnOut.setIndex(0);

				x = Player.getPlayerData().getX() - width / 3 - (width / 3 / 2);
				y = Player.getPlayerData().getY() - (height / 2 + height / 5) - (height / 4) / 2;

				AudioPlayer.playAudio("audio/vileSpawnBreakOut.wav");

				Wormhole wormhole = new Wormhole((int) x + 25, (int) y + 15, c);
				c.getGameState().getWorldGenerator().getEntityManager().addEntity(wormhole);
				c.getGameCamera().shake(20);

				new java.util.Timer().schedule(new java.util.TimerTask() {
					@Override
					public void run() {
						c.getGameCamera().shake(100);
						bounds.width = width / 3 - 80;
						bounds.height = height / 4 - 20;
						wormhole.setActive(false);
						AudioPlayer.playAudio("audio/vileSpawnBreakOut.wav");
						AudioPlayer.playAudio("audio/vileSpawnOut.wav");
						currentAnimation = vileSpawnOut;
					}
				}, 1300);

				int randTime = CT.random(2000, 4500);
				new java.util.Timer().schedule(new java.util.TimerTask() {
					@Override
					public void run() {
						once = false;
						int randMove = CT.random(1, 5);

						if (randMove == 1 || randMove == 4) {
							creepIn();
						} else if (randMove == 2) {
							remainStill();
						} else if (randMove == 3 || randMove == 5) {
							shoot();
						}

					}
				}, randTime);

			}
		}

	}

	public void remainStill() {
		if (active && !swallowed) {
			new java.util.Timer().schedule(new java.util.TimerTask() {
				@Override
				public void run() {

					int randMove = CT.random(1, 2);

					if (randMove == 1) {
						creepIn();
					} else {
						shoot();
					}

				}
			}, CT.random(2000, 4000));

		}
	}

	public void shoot() {

		if (active && !swallowed) {
			shootEnabled = true;

			new java.util.Timer().schedule(new java.util.TimerTask() {
				@Override
				public void run() {

					int randMove = CT.random(1, 2);
					shootEnabled = false;

					if (randMove == 1) {
						creepIn();
					} else {
						shoot();
					}

				}
			}, CT.random(1000, 4000));
		}

	}

	@Override
	public void render(Graphics g) {
		g.drawImage(currentAnimation.getCurrentFrame(), (int) (x - c.getGameCamera().getxOffset()),
				(int) (y - c.getGameCamera().getyOffset()), width + 32, height + 32, null);

	}

	@Override
	public void Die() {
		
		WorldGenerator.bossActive = false;
		AudioPlayer.playAudio("audio/vileSpawnOut.wav");
		MessageBox.addMessage("the Vilespawn has been defeated...");
		
		if(swallowed) {
			bounds.width -= 400;
			bounds.height -= 400;
			bounds.x += 200;
			bounds.y += 200;
			WorldGenerator.swallowed = false;
			swallowed = false;
			MessageBox.addMessage("you have lost your direction...");
		}
		active = false;
		MusicPlayer.StopMusic();
		int rand = CT.random(1, 3);
		if(rand == 1)
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getItemManager()
					.addItem(Weapon.darkSaber.createNew((int) x + bounds.x + CT.random(0, bounds.width),
							(int) y + bounds.y + CT.random(0, bounds.height)));
		c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getItemManager()
		.addItem(Item.compressorWheel.createNew((int) x + bounds.x + CT.random(0, bounds.width),
				(int) y + bounds.y + CT.random(0, bounds.height)));
		int randDrop = CT.random(5, 10);
		for (int i = 0; i < randDrop; i++)
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getItemManager()
					.addItem(Food.bugMeatItem.createNew((int) x + bounds.x + CT.random(0, bounds.width),
							(int) y + bounds.y + CT.random(0, bounds.height)));

	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub

	}
}
