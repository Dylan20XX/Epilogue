package structures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import alphaPackage.ControlCenter;
import creatures.Player;
import graphics.Assets;
import graphics.CT;
import items.Item;
import staticEntity.StaticEntity;

public class MetalChest extends Chest{

	//variables for reference
	public static int width = StaticEntity.DEFAULT_STATICOBJECT_WIDTH;
	public static int height = StaticEntity.DEFAULT_STATICOBJECT_HEIGHT;

	public MetalChest(double x, double y, ControlCenter c) {
		super(x, y, 900, 30, 2000, Assets.woodenCrate, c);
		
		health = 3000;
		resistance = 60;
		placed = true;
		
	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics g) {

		g.drawImage(Assets.vault, (int)(x - c.getGameCamera().getxOffset()), (int)(y - c.getGameCamera().getyOffset()),
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
		
		for(int i = 0; i < CT.random(1, 3); i++)
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Item.tungstenIngotItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));

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

}
