package staticEntity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import alphaPackage.ControlCenter;
import creatures.Player;
import graphics.Assets;
import graphics.CT;
import items.Food;
import tiles.Tile;

public class GiantStingerCactus extends StaticEntity{
	
	//java's random API
	private Random r = new Random();
	
	private ControlCenter c;
	
	//variables for reference
	public static int width = StaticEntity.DEFAULT_STATICOBJECT_WIDTH*3;
	public static int height = StaticEntity.DEFAULT_STATICOBJECT_HEIGHT*3;
	
	private BufferedImage cactus;
	
	private Rectangle secondaryBounds = new Rectangle(0, 0, width, height);
	
    public GiantStingerCactus(double x, double y, ControlCenter c) {
        super(x, y, width, height, c);
    
        this.c = c;
        
        //bounding box starting and ending location
        bounds.x = width/5*2 + 3;
        bounds.y = height-height/5;
        bounds.width = width/6;
        bounds.height = height/5;
        
        health = 1000;
        resistance  = 10;
        deathImage = Assets.giantStingerCactus1;
        
        int randCactus = (int)(Math.random() * 4);
        
        if(randCactus == 0) 
        	cactus = Assets.giantStingerCactus1;
        else if(randCactus == 1) 
        	cactus = Assets.giantStingerCactus2;
        if(randCactus == 2) 
        	cactus = CT.flip(Assets.giantStingerCactus1);
        if(randCactus == 3) 
        	cactus = CT.flip(Assets.giantStingerCactus2);
        
		damage = 150;
		knockValue = 12;
        
    }
    
    @Override
    public void tick() {
        
		//Damage the player when the player is inside of the cactus
		if(Player.getPlayerData().getCollisionBounds(0, 0).
				intersects(new Rectangle((int)(x + bounds.x - 60), (int)(y + bounds.y - 60), 
						120 + bounds.width , 120 + bounds.height))) {
			knockbackPlayer(this);
		}
    	
    }

    @Override
    public void render(Graphics g) {
    	
        g.drawImage(cactus, (int)(x - c.getGameCamera().getxOffset()), (int)(y - c.getGameCamera().getyOffset()),
        		width, height, null);
		
    }

	@Override
	public void Die() {

		for (int i = 0; i < CT.random(1, 2); i++) {
			// dropping items at a scaled random location
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Food.vegeMeatItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
		}
		
	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub
		
	}
	
}
