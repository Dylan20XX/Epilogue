package staticEntity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import alphaPackage.ControlCenter;
import audio.AudioPlayer;
import creatures.Player;
import graphics.Assets;
import tiles.Tile;

public class RuinPiece6 extends StaticEntity{

	public static int width = StaticEntity.DEFAULT_STATICOBJECT_WIDTH * 2;
	public static int height = StaticEntity.DEFAULT_STATICOBJECT_HEIGHT * 3;

	public RuinPiece6(double x, double y, ControlCenter c) {
		super(x, y, width, height, c);

		deathImage = Assets.workbench;

		bounds.width = width / 2 + Tile.TILEWIDTH / 2;
		bounds.height = Tile.TILEHEIGHT / 4 * 3;

		bounds.x = width / 2 - bounds.width / 2;
		bounds.y = Tile.TILEHEIGHT / 2 - bounds.height / 2;

		health = 1000;
		resistance  = 10;
		
	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics g) {

		g.drawImage(Assets.ruinPiece6, (int)(x - c.getGameCamera().getxOffset()), 
				(int)(y - c.getGameCamera().getyOffset() - Tile.TILEHEIGHT * 1.9),
				width, height, null);


	}

	@Override
	public void Die() {

	}

	@Override
	public void interact() {

	}

}
