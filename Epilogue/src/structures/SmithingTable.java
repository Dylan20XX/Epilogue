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
import tiles.Tile;

public class SmithingTable extends StaticEntity{

	public static int width = StaticEntity.DEFAULT_STATICOBJECT_WIDTH;
	public static int height = StaticEntity.DEFAULT_STATICOBJECT_HEIGHT;

	public SmithingTable(double x, double y, ControlCenter c) {
		super(x, y, width, height, c);

		/*
		bounds.x = width/5*2;
		bounds.y = height-height/5;
		bounds.width = width/6;
		bounds.height = height/5;

		health = 100;
		resistance  = 10;
		*/
		
		//bounding box starting and ending location
		//bounds.x = 10;
		//bounds.y = 15;
		bounds.width = width - 20;
		bounds.height = height - 18;
		bounds.x = Tile.TILEWIDTH / 2 - bounds.width / 2;
		bounds.y = Tile.TILEHEIGHT / 2 - bounds.height / 2;
		
		deathImage = Assets.smithingTable;

		health = 300;
		resistance  = 10;
		
	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics g) {
		
		g.drawImage(Assets.smithingTable, (int)(x - c.getGameCamera().getxOffset()), (int)(y - c.getGameCamera().getyOffset()),
				width, height, null);

	}

	@Override
	public void Die() {
		for(int i = 0; i < CT.random(6, 12); i++)
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Item.woodenPlankItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
		for(int i = 0; i < CT.random(10, 25); i++)
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Item.rockItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
		c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
		.getItemManager().addItem(Item.smithingTableToolkitItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
	}

	@Override
	public void interact() {

		Player.getPlayerData().setSmithingTableActive(true);
		Player.getPlayerData().setSmithingTable(this);
		c.getMenuState().getWorldSelectState().getGameState()
		.getWorldGenerator().setSmithingTableActive(true);
		c.getMenuState().getWorldSelectState().getGameState()
		.getWorldGenerator().setSmithingTable(this);
		
		Player.getPlayerData().getHandCraft().setupHandCraft();

	}

}
