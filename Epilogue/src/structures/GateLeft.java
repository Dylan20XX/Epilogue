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

public class GateLeft extends StaticEntity{
	
	public static int width = StaticEntity.DEFAULT_STATICOBJECT_WIDTH / 4;
	public static int height = StaticEntity.DEFAULT_STATICOBJECT_HEIGHT;
	
	public static int wallXoffset = 24;
	public static int wallYoffset = 32;
	private int wallType = 0; //1 - wood, 2 - stone, 3 - metal
	private boolean open = false; //determines if gate is open
	private Rectangle secondaryBounds = new Rectangle(0, 0, width, height);
	
	public GateLeft(double x, double y, int wallType, ControlCenter c) {
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
		deathImage = Assets.woodenFenceVertical;
		
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
				g.drawImage(Assets.woodenGateVerticalOpen, (int)(x - c.getGameCamera().getxOffset() - wallXoffset), (int)(y - c.getGameCamera().getyOffset() - wallYoffset),
						Assets.woodenGateVerticalOpen.getWidth(), Assets.woodenGateVerticalOpen.getHeight(), null);
			}else if(wallType == 2) {
				g.drawImage(Assets.stoneGateVerticalOpen, (int)(x - c.getGameCamera().getxOffset() - wallXoffset), (int)(y - c.getGameCamera().getyOffset() - wallYoffset),
						Assets.stoneGateVerticalOpen.getWidth(), Assets.stoneGateVerticalOpen.getHeight(), null);
			}else if(wallType == 3) {
				g.drawImage(Assets.metalGateVerticalOpen, (int)(x - c.getGameCamera().getxOffset() - wallXoffset), (int)(y - c.getGameCamera().getyOffset() - wallYoffset),
						Assets.metalGateVerticalOpen.getWidth(), Assets.metalGateVerticalOpen.getHeight(), null);
			}
		}else {
			if(wallType == 1) {
				g.drawImage(Assets.woodenGateVerticalClosed, (int)(x - c.getGameCamera().getxOffset() - wallXoffset), (int)(y - c.getGameCamera().getyOffset() - wallYoffset),
						Assets.woodenGateVerticalClosed.getWidth(), Assets.woodenGateVerticalClosed.getHeight(), null);
			}else if(wallType == 2) {
				g.drawImage(Assets.stoneGateVerticalClosed, (int)(x - c.getGameCamera().getxOffset() - wallXoffset), (int)(y - c.getGameCamera().getyOffset() - wallYoffset),
						Assets.stoneGateVerticalClosed.getWidth(), Assets.stoneGateVerticalClosed.getHeight(), null);
			}else if(wallType == 3) {
				g.drawImage(Assets.metalGateVerticalClosed, (int)(x - c.getGameCamera().getxOffset() - wallXoffset), (int)(y - c.getGameCamera().getyOffset() - wallYoffset),
						Assets.metalGateVerticalClosed.getWidth(), Assets.metalGateVerticalClosed.getHeight(), null);
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
