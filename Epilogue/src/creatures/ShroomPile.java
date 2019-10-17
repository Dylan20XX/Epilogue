package creatures;

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

public class ShroomPile extends Creatures{
	
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
        
		health = 1;
		resistance = 0;
		speed = 0;
		damage = 0;
		knockValue = 0;
		name = "shroomPile";
		attackBoundSize = 0;
		type = "material";
		weight = 10;

		// bounding box starting and ending location
		bounds.width = 32;
		bounds.height = 32;
		bounds.x = Tile.TILEWIDTH / 2 - bounds.width / 2;
		bounds.y = Tile.TILEHEIGHT / 2 - bounds.height / 2;
        
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
		
	}

	@Override
	public void interact() {
		
		if(Player.getPlayerData().getInventory().addItem(Food.shroomItem)) {
			
			for(int i = 0; i < amount - 1; i++)
				Player.getPlayerData().getInventory().addItem(Food.shroomItem);
			
			active = false;
		}
		
	}
}
