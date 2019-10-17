package graphics;

import alphaPackage.ControlCenter;
import entity.Entity;
import tiles.Tile;

public class GameCamera {
	private double xOffset, yOffset;
	private ControlCenter c;
	
	private boolean isShaking = false;
	private int duration = 0;
	private short shakeDir = 0;
	
	public GameCamera(double xOffset, double yOffset, ControlCenter c) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		
		this.c = c;
	}
	
	public void checkBlankSpace() {
		
		if(xOffset < 0) {
			xOffset = 0;
		} else if(xOffset > (c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getWidth()* Tile.TILEWIDTH - c.getWidth()*ControlCenter.scaleValue)) {
			xOffset = c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getWidth() * Tile.TILEWIDTH - c.getWidth()*ControlCenter.scaleValue;
		}
		
		if(yOffset < 0) {
			yOffset = 0;
		} else if(yOffset > c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getHeight() * Tile.TILEHEIGHT - c.getHeight()*ControlCenter.scaleValue) {
			yOffset = c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getHeight() * Tile.TILEHEIGHT - c.getHeight()*ControlCenter.scaleValue;
		}
		
	}
	
	public void centerOnEntity(Entity e) {
		xOffset = (e.getX() - (c.getWidth()*ControlCenter.scaleValue) / 2);
		yOffset = (e.getY() - (c.getHeight()*ControlCenter.scaleValue) / 2);
		checkBlankSpace();
	}
	
	public void move(double xAmt, double yAmt) {
		xOffset += xAmt;
		yOffset += yAmt;
		checkBlankSpace();
	}

	public void tick() {
		if(isShaking) {
			if(duration == 0) {
				centerOnEntity(c.getGameState().getWorldGenerator().getEntityManager().getPlayer());
				isShaking = false;
			}
			if(duration%2 != 0) {
			
			}else if(shakeDir == 0){
				xOffset += CT.random(5, 15);
				yOffset += CT.random(5, 20);
				shakeDir = 1;
			}else {
				xOffset -= CT.random(5, 15);
				yOffset -= CT.random(5, 20);
				shakeDir = 0;
			}
			duration--;
		}
	}
	
	public void shake(int duration) {
		isShaking = true;
		this.duration = duration;
	}
	
	public double getxOffset() {
		return xOffset;
	}

	public void setxOffset(double xOffset) {
		this.xOffset = xOffset;
	}

	public double getyOffset() {
		return yOffset;
	}

	public void setyOffset(double yOffset) {
		this.yOffset = yOffset;
	}
}
