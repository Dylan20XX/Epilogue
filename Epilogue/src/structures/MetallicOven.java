package structures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import alphaPackage.ControlCenter;
import creatures.Player;
import graphics.Assets;
import graphics.CT;
import graphics.CustomTextWritter;
import items.Item;
import staticEntity.StaticEntity;
import structureInventory.MetallicOvenInventory;
import tiles.Tile;

public class MetallicOven extends StaticEntity {

	public static int width = StaticEntity.DEFAULT_STATICOBJECT_WIDTH + 26;
	public static int height = StaticEntity.DEFAULT_STATICOBJECT_HEIGHT + 26;

	private MetallicOvenInventory inventory;

	public MetallicOven(double x, double y, ControlCenter c) {
		super(x, y, width, height, c);

		type = "timed crafting structure";

		/*
		 * bounds.x = width/5*2; bounds.y = height-height/5; bounds.width = width/6;
		 * bounds.height = height/5;
		 * 
		 * health = 100; resistance = 10;
		 */

		// bounding box starting and ending location
		// bounds.x = 10;
		// bounds.y = 15;

		bounds.width = width - 54;
		bounds.height = height / 3 + 5;

		bounds.x = Tile.TILEWIDTH / 2 - bounds.width / 2 + 12;
		bounds.y = Tile.TILEHEIGHT / 2 - bounds.height / 2 + 38;

		deathImage = Assets.smelter;

		health = 300;
		resistance = 10;

		inventory = new MetallicOvenInventory(c);
	}

	@Override
	public void tick() {

		if (!Player.getPlayerData().isMetallicOvenActive())
			inventory.tick();

	}

	@Override
	public void render(Graphics g) {

		if (!inventory.products.isEmpty()) {

			CustomTextWritter.drawString(g, "Collect Your Items", (int) (x - c.getGameCamera().getxOffset() + 32),
					(int) (y - c.getGameCamera().getyOffset()), active, Color.WHITE, Assets.font16);

		}

		if (!inventory.currentlySmelting.isEmpty()) {

			g.setColor(Color.GREEN);
			g.fillRect((int) (x - c.getGameCamera().getxOffset() + 32 - 50),
					(int) (y - c.getGameCamera().getyOffset()) - 30,
					(int) (((double) (inventory.getSmeltTime() - inventory.getSmeltTimer())
							/ (double) inventory.getSmeltTime()) * 100),
					10);

			// Change this to an asset for an oven that is active
			g.drawImage(Assets.smelterActive, (int) (x - c.getGameCamera().getxOffset()),
					(int) (y - c.getGameCamera().getyOffset()), width, height, null);

		} else {

			// Change this to an asset for an oven that is not active
			g.drawImage(Assets.smelter, (int) (x - c.getGameCamera().getxOffset()),
					(int) (y - c.getGameCamera().getyOffset()), width, height, null);

		}

	}

	@Override
	public void Die() {
		for (int i = 0; i < CT.random(10, 25); i++)
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getItemManager()
					.addItem(Item.rockItem.createNew((int) x + bounds.x + CT.random(0, bounds.width),
							(int) y + bounds.y + CT.random(0, bounds.height)));
	}

	@Override
	public void interact() {

		if(inventory.products.isEmpty() && inventory.currentlySmelting.isEmpty()) {
			inventory.setActive(true);
			Player.getPlayerData().setMetallicOvenActive(true);
			Player.getPlayerData().setMetallicOven(this);
			c.getMenuState().getWorldSelectState().getGameState()
			.getWorldGenerator().setMetallicOvenActive(true);
			c.getMenuState().getWorldSelectState().getGameState()
			.getWorldGenerator().setMetallicOven(this);
			inventory.craftSetup();
			inventory.findCraftableRecipes();
		} else if (!inventory.products.isEmpty()){
			inventory.collectItems();
		}

	}

	public MetallicOvenInventory getInventory() {
		return inventory;
	}

	public void setInventory(MetallicOvenInventory inventory) {
		this.inventory = inventory;
	}

}
