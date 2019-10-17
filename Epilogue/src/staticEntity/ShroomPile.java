package staticEntity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import alphaPackage.ControlCenter;
import graphics.Assets;
import graphics.CT;
import items.Food;
import items.Item;
import tiles.Tile;

public class ShroomPile extends StaticEntity{
	
	//java's random API
	private Random r = new Random();
	
	private ControlCenter c;
	
	//variables for reference
	public static int width = Tile.TILEWIDTH;
	public static int height = Tile.TILEHEIGHT;
	
	int amount = 0;
	
    public ShroomPile(double x, double y, ControlCenter c) {
        super(x, y, width, height, c);
    
        this.c = c;
        
        //bounding box starting and ending location
        bounds.x = width/3;
        bounds.y = height/3;
        bounds.width = width/3;
        bounds.height = height/3;
        
        health = 1;
        resistance  = 0;
        
        deathImage = Assets.shroomPile1;
        
        int amt = CT.random(1, 3);
        
        if(amt == 2)
        	amount = 2;
        else
        	amount = 1;
        
    }
    
    @Override
    public void tick() {
        
    }

    @Override
    public void render(Graphics g) {
    	
    	if(amount == 1)
	        g.drawImage(Assets.shroomPile1, (int)(x - c.getGameCamera().getxOffset()), (int)(y - c.getGameCamera().getyOffset()),
	        		width, height, null);
    	else  
    		g.drawImage(Assets.shroomPile2, (int)(x - c.getGameCamera().getxOffset()), (int)(y - c.getGameCamera().getyOffset()),
        		width, height, null);
    	
    }

	@Override
	public void Die() {

		if(amount == 1) {
			for(int i = 0; i < 3; i++)
				c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
				.getItemManager().addItem(Food.shroomItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));

		} else {
			for(int i = 0; i < 5; i++)
				c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
				.getItemManager().addItem(Food.shroomItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
		}
		
        
	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub
		
	}
}
