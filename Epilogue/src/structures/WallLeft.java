package structures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import alphaPackage.ControlCenter;
import graphics.Assets;
import staticEntity.StaticEntity;

public class WallLeft extends StaticEntity{
	
	public static int width = StaticEntity.DEFAULT_STATICOBJECT_WIDTH / 4;
	public static int height = StaticEntity.DEFAULT_STATICOBJECT_HEIGHT;
	
	public static int wallXoffset = 24;
	public static int wallYoffset = 32;
	private int wallType = 0; //1 - wood, 2 - stone, 3 - metal
	
	public WallLeft(double x, double y, int wallType, ControlCenter c) {
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

	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics g) {
		
		if(wallType == 1) {
			g.drawImage(Assets.woodenFenceVertical, (int)(x - c.getGameCamera().getxOffset() - wallXoffset), (int)(y - c.getGameCamera().getyOffset() - wallYoffset),
					Assets.woodenFenceVertical.getWidth(), Assets.woodenFenceVertical.getHeight(), null);
		}else if(wallType == 2) {
			g.drawImage(Assets.stoneWallVertical, (int)(x - c.getGameCamera().getxOffset() - wallXoffset), (int)(y - c.getGameCamera().getyOffset() - wallYoffset),
					Assets.stoneWallVertical.getWidth(), Assets.stoneWallVertical.getHeight(), null);
		}else if(wallType == 3) {
			g.drawImage(Assets.metalWallVertical, (int)(x - c.getGameCamera().getxOffset() - wallXoffset), (int)(y - c.getGameCamera().getyOffset() - wallYoffset),
					Assets.metalWallVertical.getWidth(), Assets.metalWallVertical.getHeight(), null);
		}

	}

	@Override
	public void Die() {

	}

	@Override
	public void interact() {

		

	}
	
}
