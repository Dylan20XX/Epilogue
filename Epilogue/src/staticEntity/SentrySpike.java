package staticEntity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import alphaPackage.ControlCenter;
import audio.AudioPlayer;
import creatures.Player;
import graphics.Assets;
import tiles.Tile;

public class SentrySpike extends StaticEntity{

	public static int width = StaticEntity.DEFAULT_STATICOBJECT_WIDTH;
	public static int height = StaticEntity.DEFAULT_STATICOBJECT_HEIGHT;
	
	private boolean open = true; //determines if the hitbox is active (true = no hitbox & false = hitbox active)
	private Rectangle secondaryBounds = new Rectangle(0, 0, width, height);
	private long lastOpenTimer, openCooldown = 1000, openTimer = 0; //timer for spike hitbox to become active
	private long closeCooldown = 2000, closeTimer = 0; //time before spike disappears
	private boolean damagePlayer = false;

	public SentrySpike(double x, double y, ControlCenter c) {
		super(x, y, width, height, c);
		
		/*
		bounds.x = width/5*2;
		bounds.y = height-height/5;
		bounds.width = width/6;
		bounds.height = height/5;

		health = 100;
		resistance  = 10;
		*/
		
		//bounding box starting and ending location
		
		//bounds.x = 10;
		//bounds.y = 15;
		
		//if(wallType == 1)
		deathImage = Assets.workbench;
		
		bounds.width = width - 20;
		bounds.height = height - 18;
		
		bounds.x = Player.getPlayerData().getBounds().width / 2 - bounds.width / 2;
		bounds.y = Player.getPlayerData().getBounds().height / 2 - bounds.height / 2;

		health = 300;
		resistance  = 10;
		
		secondaryBounds.x = Player.getPlayerData().getBounds().width / 2 - bounds.width / 2;
		secondaryBounds.y = Player.getPlayerData().getBounds().height / 2 - bounds.height / 2;
		secondaryBounds.width = 0;
		secondaryBounds.height = 0;
		
		lastOpenTimer = System.currentTimeMillis();
		
	}

	@Override
	public void tick() {
		
		//Damage the player when the spike hitboxes are active and the player is inside of them
		if(!open && Player.getPlayerData().getCollisionBounds(0, 0).
				intersects(new Rectangle((int)(x + secondaryBounds.x), (int)(y + secondaryBounds.y), 
						secondaryBounds.width, secondaryBounds.height)) && !damagePlayer) {
			//Player.getPlayerData().hurt(500); //set the amount to hurt the player if spike intersects player hitbox
			Player.getPlayerData().setHealth(Player.getPlayerData().getHealth() - 200);
			damagePlayer = true; //set this to true so that the player is only hurt once
		}
		
		openTimer += System.currentTimeMillis() - lastOpenTimer;

		if (openTimer < openCooldown) {
			lastOpenTimer = System.currentTimeMillis();
			return;
		}	
		
		secondaryBounds.width = bounds.width;
		secondaryBounds.height = bounds.height;
		if(open)
			AudioPlayer.playAudio("audio/breakOut.wav"); //Play the spike sound
		open = false;

		
		//If you want the spikes to remain, comment everything below
		closeTimer += System.currentTimeMillis() - lastOpenTimer;
		lastOpenTimer = System.currentTimeMillis();

		if (closeTimer < closeCooldown)
			return;
		
		hurt(health * 2 + resistance);
		
	}

	@Override
	public void render(Graphics g) {
		
		//Add different images for when spike is fully summoned
		if(open) { //not hitbox yet
			g.drawImage(Assets.sentrySpikeSprout, (int)(x + bounds.x * 2 - c.getGameCamera().getxOffset()), (int)(y + bounds.y * 2 - c.getGameCamera().getyOffset()),
					width, height, null);
		} else { //hitbox is active image
			g.drawImage(Assets.sentrySpike, (int)(x + bounds.x * 2 - c.getGameCamera().getxOffset()), (int)(y + bounds.y * 2 - c.getGameCamera().getyOffset()),
					width, height, null);
		}

	}

	@Override
	public void Die() {
		
	}

	@Override
	public void interact() {
		
	}
	
	public Rectangle getCollisionBounds(double velX, double velY) {
		//No collision bound if the player is within the spike
		if(Player.getPlayerData().getCollisionBounds(0, 0).intersects(new Rectangle((int)(x + secondaryBounds.x + velX), (int)(y + secondaryBounds.y + velY), secondaryBounds.width, secondaryBounds.height))) {
			return new Rectangle(0,0,0,0);
		} else {
			return new Rectangle((int)(x + secondaryBounds.x + velX), (int)(y + secondaryBounds.y + velY), secondaryBounds.width, secondaryBounds.height);
		}
		
    }
}
