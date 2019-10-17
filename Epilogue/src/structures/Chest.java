package structures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import alphaPackage.ControlCenter;
import creatures.Player;
import graphics.Assets;
import graphics.CT;
import items.Food;
import items.Item;
import staticEntity.StaticEntity;
import structureInventory.ChestInventory;

public class Chest extends StaticEntity{

	//java's random API
	protected Random r = new Random();
	
	protected ControlCenter c;

	//variables for reference
	public static int width = StaticEntity.DEFAULT_STATICOBJECT_WIDTH;
	public static int height = StaticEntity.DEFAULT_STATICOBJECT_HEIGHT;

	protected ChestInventory inventory;
	
	private BufferedImage appearence;

	public Chest(double x, double y, int h, int r, double v, BufferedImage app, ControlCenter c) {
		super(x, y, width, height, c);
		
		this.c = c;

		//bounding box starting and ending location
		bounds.x = 10;
		bounds.y = height/2;
		bounds.width = width - 20;
		bounds.height = (height - 18)/2;

		health = h;
		resistance = r;
		name = "chest";

		appearence = app;
		inventory = new ChestInventory(c, v);

	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics g) {

		g.drawImage(appearence, (int)(x - c.getGameCamera().getxOffset()), (int)(y - c.getGameCamera().getyOffset()),
				width, height, null);

	}

	@Override
	public void Die() {
		
		for(int i = 0; i < inventory.InventoryItemsList.size(); i++) {
			
			if(inventory.InventoryItemsList.get(i).isStackable()) {
				for(int j = 0; j < inventory.InventoryItems.get(inventory.InventoryItemsList.get(i)); j++) {
					c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getItemManager().addItem(inventory.InventoryItemsList.get(i).createNew((int)(((int)x + r.nextInt(50))*c.getScaleValue()), (int)(((int)y + r.nextInt(20))*c.getScaleValue())));
				}
			}else {
				c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getItemManager().addItem(inventory.InventoryItemsList.get(i).createNew((int)(((int)x + r.nextInt(50))*c.getScaleValue()), (int)(((int)y + r.nextInt(20))*c.getScaleValue())));
			}
			
		}
		
		inventory.emptyChest(); //empty the chest inventory
		
	}

	@Override
	public void interact() {
		
		inventory.updateItemList();
		inventory.setActive(true);
		Player.getPlayerData().setChestActive(true);
		Player.getPlayerData().setChest(this);
		c.getMenuState().getWorldSelectState().getGameState()
		.getWorldGenerator().setChestActive(true);
		c.getMenuState().getWorldSelectState().getGameState()
		.getWorldGenerator().setChest(this);

	}

	public ChestInventory getInventory() {
		return inventory;
	}

	public void setInventory(ChestInventory inventory) {
		this.inventory = inventory;
	}

	
}
