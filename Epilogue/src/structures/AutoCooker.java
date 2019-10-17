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
import structureInventory.AutoCookerCraft;
import tiles.Tile;

public class AutoCooker extends StaticEntity {

	public static int width = StaticEntity.DEFAULT_STATICOBJECT_WIDTH + 26;
	public static int height = StaticEntity.DEFAULT_STATICOBJECT_HEIGHT + 26;
	private AutoCookerCraft craft;

	public AutoCooker(double x, double y, ControlCenter c) {
		super(x, y, width, height, c);

		type = "timed crafting structure";

		bounds.width = width - 42;
		bounds.height = height / 3 + 7;

		bounds.x = Tile.TILEWIDTH / 2 - bounds.width / 2 + 12;
		bounds.y = Tile.TILEHEIGHT / 2 - bounds.height / 2 + 30;

		health = 450;
		resistance = 40;
		deathImage = Assets.autoCooker;

		craft = new AutoCookerCraft(c);
	}

	@Override
	public void tick() {

		if (!Player.getPlayerData().isAutoCookerActive())
			craft.tick();

	}

	@Override
	public void render(Graphics g) {

		if (!craft.products.isEmpty()) {

			CustomTextWritter.drawString(g, "Collect Your Items", (int) (x - c.getGameCamera().getxOffset() + 32),
					(int) (y - c.getGameCamera().getyOffset()), active, Color.WHITE, Assets.font16);

		}

		if (!craft.currentlyCooking.isEmpty()) {

			// CustomTextWritter.drawString(g, "Cook Time " + (craft.getCookTime() -
			// craft.getCookTimer()) / 1000, (int) (x - c.getGameCamera().getxOffset() +
			// 32),
			// (int) (y - c.getGameCamera().getyOffset()) - 30, active, Color.WHITE,
			// Assets.font16);

			g.setColor(Color.GREEN);
			g.fillRect((int) (x - c.getGameCamera().getxOffset() + 32 - 50),
					(int) (y - c.getGameCamera().getyOffset()) - 30,
					(int) (((double) (craft.getCookTime() - craft.getCookTimer()) / (double) craft.getCookTime())
							* 100),
					10);

			g.drawImage(Assets.autoCookerActive, (int) (x - c.getGameCamera().getxOffset()),
					(int) (y - c.getGameCamera().getyOffset()), width, height, null);

		} else {

			g.drawImage(Assets.autoCooker, (int) (x - c.getGameCamera().getxOffset()),
					(int) (y - c.getGameCamera().getyOffset()), width, height, null);

		}

	}

	@Override
	public void Die() {
		for (int i = 0; i < CT.random(5, 20); i++)
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getItemManager()
					.addItem(Item.rockItem.createNew((int) x + bounds.x + CT.random(0, bounds.width),
							(int) y + bounds.y + CT.random(0, bounds.height)));
	}

	@Override
	public void interact() {

		if(craft.products.isEmpty() && craft.currentlyCooking.isEmpty()) {
			craft.setActive(true);
			Player.getPlayerData().setAutoCookerActive(true);
			Player.getPlayerData().setAutoCooker(this);
			c.getMenuState().getWorldSelectState().getGameState()
			.getWorldGenerator().setAutoCookerActive(true);
			c.getMenuState().getWorldSelectState().getGameState()
			.getWorldGenerator().setAutoCooker(this);
			craft.craftSetup();
			craft.findCraftableRecipes();
		} else if (!craft.products.isEmpty()){
			craft.collectItems();
		}

	}

	public AutoCookerCraft getCraft() {
		return craft;
	}

	public void setInventory(AutoCookerCraft craft) {
		this.craft = craft;
	}

}
