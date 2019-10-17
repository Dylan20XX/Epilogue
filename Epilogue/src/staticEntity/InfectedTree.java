package staticEntity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Random;

import alphaPackage.ControlCenter;
import graphics.Assets;
import graphics.CT;
import items.Armor;
import items.Food;
import items.Item;
import items.Weapon;

public class InfectedTree extends StaticEntity{
	
	private ControlCenter c;
	
	//variables for reference
	public static int width = StaticEntity.DEFAULT_STATICOBJECT_WIDTH*3;
	public static int height = StaticEntity.DEFAULT_STATICOBJECT_HEIGHT*3;
	
    public InfectedTree(double x, double y, ControlCenter c) {
        super(x, y, width, height, c);
    
        this.c = c;
        
        //bounding box starting and ending location
        bounds.x = width/5*2;
        bounds.y = height-height/5;
        bounds.width = width/6;
        bounds.height = height/5;
        
        health = 1200;
        resistance  = 20;
        deathImage = Assets.infectedTree;
        requiredTool = "axe";
        
    }
    
    @Override
    public void tick() {
        
    }

    @Override
    public void render(Graphics g) {
    	
        g.drawImage(Assets.infectedTree, (int)(x - c.getGameCamera().getxOffset()), (int)(y - c.getGameCamera().getyOffset()),
        		width, height, null);
        
    }

	@Override
	public void Die() {

		for(int i = 0; i < CT.random(1, 2); i++) 
        	c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Item.woodItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
		for(int i = 0; i < CT.random(1, 5); i++)
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Item.silkItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));

	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub
		
	}
}
