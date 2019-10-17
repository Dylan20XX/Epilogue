package structures;

import java.awt.Graphics;

import alphaPackage.ControlCenter;
import creatures.Player;
import graphics.Assets;
import graphics.CT;
import items.Item;
import staticEntity.StaticEntity;
import tiles.Tile;

public class Workbench extends StaticEntity {

	public static int width = StaticEntity.DEFAULT_STATICOBJECT_WIDTH + 10;
	public static int height = StaticEntity.DEFAULT_STATICOBJECT_HEIGHT + 10;

	public Workbench(double x, double y, ControlCenter c) {
		super(x, y, width, height, c);

		bounds.width = width - 34;
		bounds.height = height - 22;

		bounds.x = Tile.TILEWIDTH / 2 - bounds.width / 2 + 5;
		bounds.y = Tile.TILEHEIGHT / 2 - bounds.height / 2 + 10;

		// if(wallType == 1)
		deathImage = Assets.workbench;

		health = 350;
		resistance = 40;

	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics g) {

		g.drawImage(Assets.workbench, (int) (x - c.getGameCamera().getxOffset()),
				(int) (y - c.getGameCamera().getyOffset()), width, height, null);

	}

	@Override
	public void Die() {

		for (int i = 0; i < CT.random(5, 15); i++)
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getItemManager()
					.addItem(Item.woodenPlankItem.createNew((int) x + bounds.x + CT.random(0, bounds.width),
							(int) y + bounds.y + CT.random(0, bounds.height)));
		c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getItemManager()
		.addItem(Item.workbenchToolkitItem.createNew((int) x + bounds.x + CT.random(0, bounds.width),
				(int) y + bounds.y + CT.random(0, bounds.height)));
		
	}

	@Override
	public void interact() {

		// craft.setActive(true);
		Player.getPlayerData().setWorkbenchActive(true);
		Player.getPlayerData().setWorkbench(this);
		c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().setWorkbenchActive(true);
		c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().setWorkbench(this);
		// craft.craftSetup();
		// craft.findCraftableRecipes();

		Player.getPlayerData().getHandCraft().setupHandCraft();

	}
}
