package staticEntity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

import alphaPackage.ControlCenter;
import audio.AudioPlayer;
import creatures.Parasite;
import creatures.Player;
import creatures.Sentry;
import creatures.SentryBroodMother;
import creatures.SentryMajor;
import graphics.Animation;
import graphics.Assets;
import graphics.CT;
import inventory.MessageBox;
import items.Food;
import items.Item;
import tiles.Tile;
import world.WorldGenerator;

public class SentryHive extends StaticEntity {

	private Animation hive;
	private long lastSpawnTimer, spawnCooldown = 2000, spawnTimer = spawnCooldown;
	private int x, y;
	
	private boolean spawned = false;

	public SentryHive(double x, double y, ControlCenter c) {
		super(x, y, Tile.TILEWIDTH * 7, Tile.TILEHEIGHT * 4, c);
		this.x = (int) x;
		this.y = (int) y;
		bounds.x = 50;
		bounds.y = 140 - 20;
		bounds.width = 64 * 7 - 32 - 60;
		bounds.height = 45 + 48 + 30 + 20;

		deathImage = Assets.hive[0];
		
		health = 6000;
		resistance = 30;
		hive = new Animation(400, Assets.hive, true);
		
		buildingXPGiven = (int)(Math.random()*(Player.getPlayerData().getIntelligence()*2)*2);
		
	}

	@Override
	public void tick() {
		
		hive.tick();

		spawnTimer += System.currentTimeMillis() - lastSpawnTimer;
		lastSpawnTimer = System.currentTimeMillis();

		if (spawnTimer < spawnCooldown)
			return;
		
		if (spawnTimer >= spawnCooldown) {
			
			if (c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
					.getEntityManager().getPlayer().getBounds().intersects(spawnBound())) {

				WorldGenerator.nextSentry = true;
				
			}
			
		}

		spawnTimer = 0;

	}
	
	public void spawnSentry() {

		int temp = CT.random(0, 2);

		if (temp == 1) {

			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getEntityManager().addEntity(new Sentry(x + CT.random(0, 100) + 50,
					y + CT.random(0, 140), c));

		} else if (temp == 2) {

			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getEntityManager().addEntity(new Sentry(x + CT.random(0, 100) + 50,
					y + CT.random(0, 140), c));

		} else if (temp == 0) { // 1/3 chance of hive spawning sentry major

			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getEntityManager().addEntity(new SentryMajor(x + CT.random(0, 100) + 50,
					y + CT.random(0, 140), c));

		}

	}

	public Rectangle spawnBound() {

		return new Rectangle((int) (x - c.getGameCamera().getxOffset() - 800) + bounds.width / 2,
				(int) (y - c.getGameCamera().getyOffset() - 800) + bounds.height / 2, 1600, 1600);

	}

	@Override
	public void render(Graphics g) {
		g.drawImage(hive.getCurrentFrame(), (int) (x - c.getGameCamera().getxOffset()),
				(int) (y - c.getGameCamera().getyOffset()), width, height, null);

	}

	@Override
	public void Die() {
		
		MessageBox.addMessage("you have awakened the Sentry Queen...");

		AudioPlayer.playAudio("audio/broodMotherSpawn.wav");
		
    	c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
        .getEntityManager().addEntity(new SentryBroodMother(x, y, c));
    	
		c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getItemManager()
		.addItem(Item.turboCharger.createNew((int) x + bounds.x + CT.random(0, bounds.width),
				(int) y + bounds.y + CT.random(0, bounds.height)));
		
		for(int i = 0; i < CT.random(6, 15); i++)
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Item.silkItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));

		c.getGameState().getWorldGenerator().setLastSentryQueenSpawnTimer(System.currentTimeMillis());
		
	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub
		
	}

}