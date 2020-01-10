package creatures;

import java.awt.Graphics;

import alphaPackage.ControlCenter;
import audio.AudioPlayer;
import graphics.Assets;
import graphics.CT;
import items.Item;
import tiles.Tile;

public class Grass extends Creatures {

	private int size;
	
	public Grass(double x, double y, ControlCenter c) {
		super(x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT, c);
		
		this.c = c;
        
		health = 1;
		resistance = 0;
		speed = 0;
		damage = 0;
		knockValue = 0;
		name = "grass";
		attackBoundSize = 0;
		type = "material";
		weight = 10;

		// bounding box starting and ending location
		bounds.width = 32;
		bounds.height = 32;
		bounds.x = Tile.TILEWIDTH / 2 - bounds.width / 2;
		bounds.y = Tile.TILEHEIGHT / 2 - bounds.height / 2;
		
		int sizeID = CT.random(1, 100);
		
		if(sizeID < 5)
			size = 5;
		else if(sizeID < 15)
			size = 4;
		else if(sizeID < 35)
			size = 3;
		else if(sizeID < 60)
			size = 2;
		else
			size = 1;
		
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {

    	if(Player.getPlayerData().steppingBound().intersects(getBounds())) {
    		
    		AudioPlayer.playAudio("audio/plant.wav");
    		Die();
    		active = false;
    		
    	}
    	
    	if(size == 1)
	        g.drawImage(Assets.grass1, (int)(x - c.getGameCamera().getxOffset()), (int)(y - c.getGameCamera().getyOffset()),
	        		width, height, null);
    	else if(size == 2) 
    		g.drawImage(Assets.grass2, (int)(x - c.getGameCamera().getxOffset()), (int)(y - c.getGameCamera().getyOffset()),
        		width, height, null);
    	else if(size == 3) 
    		g.drawImage(Assets.grass3, (int)(x - c.getGameCamera().getxOffset()), (int)(y - c.getGameCamera().getyOffset()),
        		width, height, null);
    	else if(size == 4) 
    		g.drawImage(Assets.grass4, (int)(x - c.getGameCamera().getxOffset()), (int)(y - c.getGameCamera().getyOffset()),
        		width, height, null);
    	else if(size == 5) 
    		g.drawImage(Assets.grass5, (int)(x - c.getGameCamera().getxOffset()), (int)(y - c.getGameCamera().getyOffset()),
        		width, height, null);
    	
		
	}

	@Override
	public void Die() {
		if(size == 1) {
			int numString = CT.random(1, 12);
			if(numString == 1)
				c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
				.getItemManager().addItem(Item.silkItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
		
		} else if(size == 2) { 
			int numString = CT.random(1, 8);
			if(numString == 1)
				c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
				.getItemManager().addItem(Item.silkItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
		
		} else if(size == 3) {
    		int numString = CT.random(1, 5);
    		if(numString == 1)
				c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
				.getItemManager().addItem(Item.silkItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
    	
    	} else if(size == 4) {
    		int numString = CT.random(1, 3);
    		if(numString == 1)
				c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
				.getItemManager().addItem(Item.silkItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
			int numStick = CT.random(0, 5);
			if(numStick == 1)
    			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
    			.getItemManager().addItem(Item.stickItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
    		
    	} else if(size == 5) {
		
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Item.silkItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
		
    		int numStick = CT.random(0, 3);
    		if(numStick == 1)
    			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
    			.getItemManager().addItem(Item.stickItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
    		
    	
    	}
		
		
		
	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub
		
	}

}
