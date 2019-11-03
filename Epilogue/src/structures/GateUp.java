package structures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import alphaPackage.ControlCenter;
import audio.AudioPlayer;
import creatures.Player;
import graphics.Assets;
import staticEntity.StaticEntity;

public class GateUp extends StaticEntity{
	
	public static int width = StaticEntity.DEFAULT_STATICOBJECT_WIDTH;
	public static int height = StaticEntity.DEFAULT_STATICOBJECT_HEIGHT / 4;
	
	public static int wallXoffset = 0;
	public static int wallYoffset = 48;
	private int wallType = 0; //1 - wood, 2 - stone, 3 - metal
	private boolean open = false; //determines if gate is open
	private Rectangle secondaryBounds = new Rectangle(0, 0, width, height);

	public GateUp(double x, double y, int wallType, ControlCenter c) {
		super(x, y, width, height, c);
		
		type = "gate";
		
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
		this.wallType = wallType;
		
		//if(wallType == 1)
		deathImage = Assets.woodenGateHorizontalClosed;
		
		bounds.width = width;
		bounds.height = height;
		
		bounds.x = 0;
		bounds.y = 0;
		
		if(wallType == 1) {
			health = 300;
			resistance  = 10;
		}else if(wallType == 2) {
			health = 400;
			resistance  = 10;
		}else if(wallType == 3) {
			health = 500;
			resistance  = 10;
		}
		placed = true;
		
	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics g) {
		
		//Note - add different assets for an open and closed gate
		if(open) {
			if(wallType == 1) {
				g.drawImage(Assets.woodenGateHorizontalOpen, (int)(x - c.getGameCamera().getxOffset() - wallXoffset), (int)(y - c.getGameCamera().getyOffset() - wallYoffset),
						Assets.woodenGateHorizontalOpen.getWidth(), Assets.woodenGateHorizontalOpen.getHeight(), null);
			}else if(wallType == 2) {
				g.drawImage(Assets.stoneGateHorizontalOpen, (int)(x - c.getGameCamera().getxOffset() - wallXoffset), (int)(y - c.getGameCamera().getyOffset() - wallYoffset),
						Assets.stoneGateHorizontalOpen.getWidth(), Assets.stoneGateHorizontalOpen.getHeight(), null);
			}else if(wallType == 3) {
				g.drawImage(Assets.metalGateHorizontalOpen, (int)(x - c.getGameCamera().getxOffset() - wallXoffset), (int)(y - c.getGameCamera().getyOffset() - wallYoffset),
						Assets.metalGateHorizontalOpen.getWidth(), Assets.metalGateHorizontalOpen.getHeight(), null);
			}
		}else {
			if(wallType == 1) {
				g.drawImage(Assets.woodenGateHorizontalClosed, (int)(x - c.getGameCamera().getxOffset() - wallXoffset), (int)(y - c.getGameCamera().getyOffset() - wallYoffset),
						Assets.woodenGateHorizontalClosed.getWidth(), Assets.woodenGateHorizontalClosed.getHeight(), null);
			}else if(wallType == 2) {
				g.drawImage(Assets.stoneGateHorizontalClosed, (int)(x - c.getGameCamera().getxOffset() - wallXoffset), (int)(y - c.getGameCamera().getyOffset() - wallYoffset),
						Assets.stoneGateHorizontalClosed.getWidth(), Assets.stoneGateHorizontalClosed.getHeight(), null);
			}else if(wallType == 3) {
				g.drawImage(Assets.metalGateHorizontalClosed, (int)(x - c.getGameCamera().getxOffset() - wallXoffset), (int)(y - c.getGameCamera().getyOffset() - wallYoffset),
						Assets.metalGateHorizontalClosed.getWidth(), Assets.metalGateHorizontalClosed.getHeight(), null);
			}
		}
		
		/*
		//drawing down the bounding box
		Graphics2D g2d = (Graphics2D)g;
		g.setColor(Color.BLUE);
		g2d.draw(getBounds());
		
		if(open) {
			g.setColor(Color.GREEN);
			g2d.draw(getBounds());
		}
*/
	}

	@Override
	public void Die() {
		AudioPlayer.playAudio("audio/structureBreak.wav");
	}

	@Override
	public void interact() {

		if(open && !(getBounds().intersects(Player.getPlayerData().getBounds()))) { //close the gate
			
			secondaryBounds.width = width;
			secondaryBounds.height = height;
			open = false;
			
		}else { //open the gate
			
			secondaryBounds.width = 0;
			secondaryBounds.height = 0;
			open = true;
			
		}
		
	}
	
	public Rectangle getCollisionBounds(double velX, double velY) {
        return new Rectangle((int)(x + secondaryBounds.x + velX), (int)(y + secondaryBounds.y + velY), secondaryBounds.width, secondaryBounds.height);
    }
	
}
