package structures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import alphaPackage.ControlCenter;
import audio.AudioPlayer;
import graphics.Assets;
import staticEntity.StaticEntity;

public class WallUp extends StaticEntity{
	
	public static int width = StaticEntity.DEFAULT_STATICOBJECT_WIDTH;
	public static int height = StaticEntity.DEFAULT_STATICOBJECT_HEIGHT / 4;
	
	public static int wallXoffset = 0;
	public static int wallYoffset = 48;
	private int wallType = 0; //1 - wood, 2 - stone, 3 - metal

	public WallUp(double x, double y, int wallType, ControlCenter c) {
		super(x, y, width, height, c);
		
		type = "wall";
		
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
		deathImage = Assets.woodenFenceHorizontal;
		
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
		
		
		if(wallType == 1) {
			g.drawImage(Assets.woodenFenceHorizontal, (int)(x - c.getGameCamera().getxOffset() - wallXoffset), (int)(y - c.getGameCamera().getyOffset() - wallYoffset),
					Assets.woodenFenceHorizontal.getWidth(), Assets.woodenFenceHorizontal.getHeight(), null);
		}else if(wallType == 2) {
			g.drawImage(Assets.stoneWallHorizontal, (int)(x - c.getGameCamera().getxOffset() - wallXoffset), (int)(y - c.getGameCamera().getyOffset() - wallYoffset),
					Assets.stoneWallHorizontal.getWidth(), Assets.stoneWallHorizontal.getHeight(), null);
		}else if(wallType == 3) {
			g.drawImage(Assets.metalWallHorizontal, (int)(x - c.getGameCamera().getxOffset() - wallXoffset), (int)(y - c.getGameCamera().getyOffset() - wallYoffset),
					Assets.metalWallHorizontal.getWidth(), Assets.metalWallHorizontal.getHeight(), null);
		}

	}

	@Override
	public void Die() {
		AudioPlayer.playAudio("audio/structureBreak.wav");
	}

	@Override
	public void interact() {

		

	}
	
}
