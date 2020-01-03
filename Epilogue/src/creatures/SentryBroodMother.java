package creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

import alphaPackage.ControlCenter;
import audio.AudioPlayer;
import audio.MusicPlayer;
import entity.Entity;
import graphics.Animation;
import graphics.Assets;
import graphics.CT;
import items.Ranged;
import items.Weapon;
import staticEntity.SentrySpike;
import tiles.Tile;
import world.WorldGenerator;

public class SentryBroodMother extends Creatures {

	private long lastSpawnTimer, spawnCooldown = 2000, spawnTimer = spawnCooldown;
	private long lastattackTimer, attackCooldown = 1000, attackTimer = attackCooldown;
	private Random r = new Random();
	private int selectedAction;
	private boolean canAttack = true;
	private int numSentriesSpawned = 0;

	public SentryBroodMother(double x, double y, ControlCenter c) {
		super(x, y, Creatures.DEFAULT_CREATURE_WIDTH * 7, Creatures.DEFAULT_CREATURE_HEIGHT * 7, c);

		health = 5000;
		speed = 3.3;
		resistance = 40;
		knockValue = 30;
		attackBoundSize = 8000;
		weight = 350;

		bounds.x = 30;
		bounds.y = 300;
		bounds.width = 450;
		bounds.height = 50;

		name = "sentryBroodMother";
		type = "creatures";

		left = new Animation(200, Assets.sentryBroodMother, true);
		right = new Animation(200, CT.flip(Assets.sentryBroodMother), true);

		combatXPDropped = (int)(500 + Math.random()*(Player.getPlayerData().getIntelligence()*2));

	}

	@Override
	public void tick() {
		WorldGenerator.bossActive = true;
		if(selectedAction == 0) { //chase player
			AI();
			if (move)
				move();
			left.tick();
			right.tick();
		} else if(selectedAction == 1) { //chase player and spawn sentry

			AI();
			if (move)
				move();
			left.tick();
			right.tick();
			
			//If player is in this bound, queen will spawn sentries
			if (c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
					.getEntityManager().getPlayer().getBounds().intersects(attackBound()) &&
					canAttack) {

				int temp = r.nextInt(3);

				if (temp == 1) {

					c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
					.getEntityManager().addEntity(new Sentry(x + r.nextInt(100) + 50,
							y + r.nextInt(140), c));

				} else if (temp == 2) {

					c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
					.getEntityManager().addEntity(new Sentry(x + r.nextInt(100) + 150,
							y + r.nextInt(140), c));

				} else if (temp == 0) {

					c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
					.getEntityManager().addEntity(new SentryMajor(x + r.nextInt(100) + 150,
							y + r.nextInt(140), c));

				}


				numSentriesSpawned++;

				if(numSentriesSpawned >= 2)
					canAttack = false;

			}
		} else if(selectedAction == 2) { //spike attack

			if (c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
					.getEntityManager().getPlayer().getBounds().intersects(attackBound()) && canAttack) {

				c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
				.getEntityManager().addEntity(new SentrySpike(
						Player.getPlayerData().getX() + r.nextInt(16) - 8 + Player.getPlayerData().getVelX() * 24, 
						Player.getPlayerData().getY() + r.nextInt(16) - 8 + Player.getPlayerData().getVelY() * 24, c));
				
				c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
				.getEntityManager().addEntity(new SentrySpike(
						Player.getPlayerData().getX() - Tile.TILEWIDTH + r.nextInt(16) - 8 + Player.getPlayerData().getVelX() * 24, 
						Player.getPlayerData().getY() - Tile.TILEHEIGHT + r.nextInt(16) - 8 + Player.getPlayerData().getVelY() * 24, c));

				c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
				.getEntityManager().addEntity(new SentrySpike(
						Player.getPlayerData().getX() + r.nextInt(16) - 8 + Player.getPlayerData().getVelX() * 24, 
						Player.getPlayerData().getY() - Tile.TILEHEIGHT + r.nextInt(16) - 8 + Player.getPlayerData().getVelY() * 24, c));

				c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
				.getEntityManager().addEntity(new SentrySpike(
						Player.getPlayerData().getX() + Tile.TILEWIDTH + r.nextInt(16) - 8 + Player.getPlayerData().getVelX() * 24, 
						Player.getPlayerData().getY() - Tile.TILEHEIGHT + r.nextInt(16) - 8 + Player.getPlayerData().getVelY() * 24, c));

				c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
				.getEntityManager().addEntity(new SentrySpike(
						Player.getPlayerData().getX() - Tile.TILEWIDTH + r.nextInt(16) - 8 + Player.getPlayerData().getVelX() * 24, 
						Player.getPlayerData().getY() + r.nextInt(16) - 8 + Player.getPlayerData().getVelY() * 24, c));

				c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
				.getEntityManager().addEntity(new SentrySpike(
						Player.getPlayerData().getX() + Tile.TILEWIDTH + r.nextInt(16) - 8 + Player.getPlayerData().getVelX() * 24, 
						Player.getPlayerData().getY() + r.nextInt(16) - 8 + Player.getPlayerData().getVelY() * 24, c));

				c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
				.getEntityManager().addEntity(new SentrySpike(
						Player.getPlayerData().getX() - Tile.TILEWIDTH + r.nextInt(16) - 8 + Player.getPlayerData().getVelX() * 24, 
						Player.getPlayerData().getY() + Tile.TILEHEIGHT + r.nextInt(16) - 8 + Player.getPlayerData().getVelY() * 24, c));

				c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
				.getEntityManager().addEntity(new SentrySpike(
						Player.getPlayerData().getX() + r.nextInt(16) - 8 + Player.getPlayerData().getVelX() * 24, 
						Player.getPlayerData().getY() + Tile.TILEHEIGHT + r.nextInt(16) - 8 + Player.getPlayerData().getVelY() * 24, c));

				c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
				.getEntityManager().addEntity(new SentrySpike(
						Player.getPlayerData().getX() + Tile.TILEWIDTH + r.nextInt(16) - 8 + Player.getPlayerData().getVelX() * 24, 
						Player.getPlayerData().getY() + Tile.TILEHEIGHT + r.nextInt(16) - 8 + Player.getPlayerData().getVelY() * 24, c));
				 
				
				canAttack = false;

			}

		}

		spawnTimer += System.currentTimeMillis() - lastSpawnTimer;
		lastSpawnTimer = System.currentTimeMillis();

		if (spawnTimer < spawnCooldown)
			return;

		selectedAction = r.nextInt(3);
		canAttack = true;
		spawnTimer = 0;
		
		if(!(closeBound().intersects(Player.getPlayerData().getBounds())) && attackBound().intersects(Player.getPlayerData().getBounds())) {
			selectedAction = 0;
			speed = 3.3;
		} else {
			speed = 2;
		}
			

	}

	public void AI() {

		if (damageBound().intersects(Player.getPlayerData().getBounds())) 
			knockbackPlayer(this);

		chase(1.2);

		
		attackTimer += System.currentTimeMillis() - lastattackTimer;
		lastattackTimer = System.currentTimeMillis();

		if (attackTimer < attackCooldown)
			return;
		
		for(int i = 0; i < c.getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().size(); i++) {
			if(c.getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().get(i).getBounds().intersects(damageBound())) {
				if(!c.getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().get(i)
						.equals(c.getGameState().getWorldGenerator().getEntityManager().getPlayer()) && 
						!c.getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().get(i).equals(this) &&
						!(c.getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().get(i) instanceof Sentry) &&
						!(c.getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().get(i) instanceof SentryMajor) &&
						!(c.getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().get(i) instanceof SentrySpike)
						&& !(c.getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().get(i) instanceof AwakenedSentinel)
						&& !(c.getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().get(i) instanceof SentryBroodMother)
						&& !(c.getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().get(i) instanceof VileSpawn)) {
					c.getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().get(i).hurt(10000);
				}
			}
		}
		
		attackTimer = 0;
		
	}

	public Rectangle closeBound() { 
		
		return new Rectangle((int) (x + bounds.x - c.getGameCamera().getxOffset() - 1000) + bounds.width / 2,
				(int) (y  + bounds.y - c.getGameCamera().getyOffset() - 1000) + bounds.height / 2, 2000, 2000);
		
	}

	public Rectangle breakBound() {

		return new Rectangle((int)(x - c.getGameCamera().getxOffset()) - 2 + bounds.x, (int)(y - c.getGameCamera().getyOffset()) - 2 + bounds.y, 
				bounds.width + 4, bounds.height + 4);

	}

	@Override
	public void render(Graphics g) {

		g.drawImage(getCurrentAnimation(), (int) (x - c.getGameCamera().getxOffset()),
				(int) (y - c.getGameCamera().getyOffset()), width + 32, height + 32, null);

		g.setColor(Color.blue);
		Graphics2D g2d = (Graphics2D) g;
		g2d.draw(getBounds());
		g.setColor(Color.GREEN);
		g2d.draw(closeBound());
		g2d.draw(attackBound());

	}


	@Override
	public void Die() {

		WorldGenerator.bossActive = false;
		c.getGameState().getWorldGenerator().broodMotherAlive = false;
		
		int rand = CT.random(1, 3);
		if(rand == 1)
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getItemManager()
					.addItem(Ranged.ravager.createNew((int) x + bounds.x + CT.random(0, bounds.width),
							(int) y + bounds.y + CT.random(0, bounds.height)));

		AudioPlayer.playAudio("audio/broodMotherSpawn.wav");
		MusicPlayer.StopMusic();

	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub

	}

}
