package structures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import alphaPackage.ControlCenter;
import creatures.Player;
import graphics.Assets;
import staticEntity.StaticEntity;
import structureInventory.AnalyzerCraft;
import tiles.Tile;

//THIS CLASS IS NO LONGER NEEDED
public class Analyzer extends StaticEntity{

	public static int width = StaticEntity.DEFAULT_STATICOBJECT_WIDTH;
	public static int height = StaticEntity.DEFAULT_STATICOBJECT_HEIGHT;

	private AnalyzerCraft craft;

	public Analyzer(double x, double y, ControlCenter c) {
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
		//bounds.x = 10; //bounds.x is how much the rectangle will be off by in relation to x
		//bounds.y = 15;
		bounds.width = width - 20;
		bounds.height = height - 18;
		bounds.x = Tile.TILEWIDTH / 2 - bounds.width / 2;
		bounds.y = Tile.TILEHEIGHT / 2 - bounds.height / 2;
		
		health = 300;
		resistance  = 10;

		craft = new AnalyzerCraft(c);
	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics g) {
		
		g.drawImage(Assets.woodenCrate, (int)(x - c.getGameCamera().getxOffset()), (int)(y - c.getGameCamera().getyOffset()),
				width, height, null);

		//drawing down the bounding box
		Graphics2D g2d = (Graphics2D)g;
		g.setColor(Color.BLUE);
		g2d.draw(getBounds());


	}

	@Override
	public void Die() {

	}

	@Override
	public void interact() {

		craft.setActive(true);
		Player.getPlayerData().setAnalyzerActive(true);
		Player.getPlayerData().setAnalyzer(this);
		c.getMenuState().getWorldSelectState().getGameState()
		.getWorldGenerator().setAnalyzerActive(true);
		c.getMenuState().getWorldSelectState().getGameState()
		.getWorldGenerator().setAnalyzer(this);	
		craft.craftSetup();

	}

	public AnalyzerCraft getCraft() {
		return craft;
	}

	public void setCraft(AnalyzerCraft craft) {
		this.craft = craft;
	}

}
