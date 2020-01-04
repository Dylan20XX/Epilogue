package structures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import alphaPackage.ControlCenter;
import audio.AudioPlayer;
import graphics.Assets;
import staticEntity.StaticEntity;
import tiles.Tile;

public class LampPost extends StaticEntity {

	// java's random API
	private Random r = new Random();

	private ControlCenter c;

	// variables for reference
	public static int width = Tile.TILEWIDTH * 3;
	public static int height = Tile.TILEHEIGHT * 3;

	private BufferedImage lamp = Assets.lampPost[0];

	private boolean preAddLight = false;

	public LampPost(double x, double y, ControlCenter c) {
		super(x, y, width, height, c);

		this.c = c;
		name = "lamp post";

		// bounding box starting and ending location

		bounds.x = width / 8 - 25;
		bounds.y = height - height;
		bounds.width = width / 3;
		bounds.height = height / 3;
		deathImage = Assets.lampPost[0];

		health = 400;
		resistance = 35;
		placed = true;

	}

	@Override
	public void tick() {
		
		System.out.println("lamp lightmap: " + c.getGameState().getWorldGenerator().powerMap[(int) (x/64)][(int) (y/64)]);
		
		if (c.getGameState().getWorldGenerator().powerMap[(int) (x/64)][(int) (y/64)] > 0) {
			lamp = Assets.lampPost[0];
			if (!preAddLight) {
				c.getGameState().getWorldGenerator().addLight(this, 5, (int) (x/64), (int) (y/64), 3);
				preAddLight = true;
			}
		} else {
			lamp = Assets.lampPost[1];
			if (preAddLight) {
				c.getGameState().getWorldGenerator().removeLight(5, (int) (x/64), (int) (y/64), 3);
				preAddLight = false;
			}
		}

	}

	@Override
	public void render(Graphics g) {
		// drawing down the bounding box
		Graphics2D g2d = (Graphics2D) g;

		g.drawImage(lamp, (int) (x - Tile.TILEWIDTH - c.getGameCamera().getxOffset()), (int) (y - 2*Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), width,
				height, null);

	}

	@Override
	public void Die() {
		AudioPlayer.playAudio("audio/structureBreak.wav");
		if (preAddLight) {
			c.getGameState().getWorldGenerator().removeLight(5, (int) (x/64), (int) (y/64), 3);
		}

	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub

	}

}
