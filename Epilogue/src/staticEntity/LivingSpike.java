package staticEntity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import alphaPackage.ControlCenter;
import audio.AudioPlayer;
import creatures.Player;
import graphics.Assets;
import graphics.CT;
import items.Item;
import tiles.Tile;

public class LivingSpike extends StaticEntity {

	public LivingSpike(double x, double y, ControlCenter c) {
		super(x, y, StaticEntity.DEFAULT_STATICOBJECT_WIDTH + CT.random(0, 50), CT.random(StaticEntity.DEFAULT_STATICOBJECT_WIDTH + 10, StaticEntity.DEFAULT_STATICOBJECT_HEIGHT*5), c);

		deathImage = Assets.spike;

		bounds.width = width / 4;
		bounds.height = 20;

		bounds.x = width / 2 - 12;
		bounds.y = height -20;

		health = CT.random(300, 500);
		resistance = 40;
		
		buildingXPGiven = (int)(Math.random()*(Player.getPlayerData().getIntelligence()*2))/2;

	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics g) {

		g.drawImage(Assets.sentrySpike, (int) (x - c.getGameCamera().getxOffset()),
				(int) (y - c.getGameCamera().getyOffset()), width, height, null);

	}

	@Override
	public void Die() {
		for(int i = 0; i < CT.random(1, 3); i++) {
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getItemManager()
			.addItem(Item.spikeItem.createNew((int) x + bounds.x + CT.random(0, bounds.width),
					(int) y + bounds.y + CT.random(0, bounds.height)));
		}
	}

	@Override
	public void interact() {

	}

}
