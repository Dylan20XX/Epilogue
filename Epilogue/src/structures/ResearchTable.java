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
import structureInventory.ResearchTableCraft;
import tiles.Tile;

public class ResearchTable extends StaticEntity {

	public static int width = StaticEntity.DEFAULT_STATICOBJECT_WIDTH + 26;
	public static int height = StaticEntity.DEFAULT_STATICOBJECT_HEIGHT + 26;

	ResearchTableCraft craft;

	public ResearchTable(double x, double y, ControlCenter c) {
		super(x, y, width, height, c);

		// if(wallType == 1)
		deathImage = Assets.workbench;

		bounds.width = width - 20;
		bounds.height = height - 45;

		bounds.x = Tile.TILEWIDTH / 2 - bounds.width / 2 + 14;
		bounds.y = Tile.TILEHEIGHT / 2 - bounds.height / 2 + 30;

		health = 300;
		resistance = 40;

		craft = new ResearchTableCraft(c);

	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics g) {

		g.drawImage(Assets.researchTable, (int) (x - c.getGameCamera().getxOffset()),
				(int) (y - c.getGameCamera().getyOffset()), width, height, null);
		
	}

	@Override
	public void Die() {
		
		for (int i = 0; i < CT.random(5, 20); i++)
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getItemManager()
					.addItem(Item.woodenPlankItem.createNew((int) x + bounds.x + CT.random(0, bounds.width),
							(int) y + bounds.y + CT.random(0, bounds.height)));
		c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getItemManager()
		.addItem(Item.researchKitItem.createNew((int) x + bounds.x + CT.random(0, bounds.width),
				(int) y + bounds.y + CT.random(0, bounds.height)));
		
	}

	@Override
	public void interact() {

		craft.craftSetup();
		Player.getPlayerData().setResearchTableActive(true);
		Player.getPlayerData().setResearchTable(this);
		c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().setResearchTableActive(true);
		c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().setResearchTable(this);

	}

	public ResearchTableCraft getCraft() {
		return craft;
	}

	public void setCraft(ResearchTableCraft craft) {
		this.craft = craft;
	}
}
