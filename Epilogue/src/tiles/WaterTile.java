package tiles;

import java.awt.Graphics;
import java.awt.Graphics2D;

import graphics.Animation;
import graphics.Assets;

public class WaterTile extends Tile{
	
	private Animation waterTile = new Animation(250, Assets.waterTile, true);
	
	public WaterTile(int id) {
		super(Assets.dirtTile, id);
		
	}
	
	public void render(Graphics g, int x, int y, int tileNum) {

		if(tileNum == 0)
			waterTile.setFrames(Assets.waterTile);
		else if(tileNum == 1)
			waterTile.setFrames(Assets.water2Tile);
		else if(tileNum == 2)
			waterTile.setFrames(Assets.water3Tile);
		else if(tileNum == 3)
			waterTile.setFrames(Assets.water4Tile);
		else if(tileNum == 4)
			waterTile.setFrames(Assets.water5Tile);
		else if(tileNum == 5)
			waterTile.setFrames(Assets.water6Tile);
		else if(tileNum == 6)
			waterTile.setFrames(Assets.water7Tile);
		else if(tileNum == 7)
			waterTile.setFrames(Assets.water8Tile);
		else if(tileNum == 8)
			waterTile.setFrames(Assets.water9Tile);
		else if(tileNum == 9)
			waterTile.setFrames(Assets.currentTile);
		else if(tileNum == 10)
			waterTile.setFrames(Assets.current2Tile);
		else if(tileNum == 11)
			waterTile.setFrames(Assets.water10Tile);
		else if(tileNum == 12)
			waterTile.setFrames(Assets.water11Tile);
		else if(tileNum == 13)
			waterTile.setFrames(Assets.water12Tile);
		else if(tileNum == 14)
			waterTile.setFrames(Assets.water13Tile);
		
		waterTile.tick();
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(waterTile.getCurrentFrame(), x, y, TILEWIDTH, TILEHEIGHT, null);
	}
	
	public boolean isSolid() {
		return true;
	}
	
}
