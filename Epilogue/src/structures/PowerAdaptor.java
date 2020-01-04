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

public class PowerAdaptor extends StaticEntity {

	// java's random API
	private Random r = new Random();

	private ControlCenter c;

	// variables for reference
	public static int width = Tile.TILEWIDTH;
	public static int height = Tile.TILEHEIGHT;
	
	private BufferedImage adaptor = Assets.powerAdaptorOff;
	private boolean preAddPower = false;
	
	public PowerAdaptor(double x, double y, ControlCenter c) {
		super(x, y, width, height, c);

		this.c = c;

		name = "power adaptor";
		// bounding box starting and ending location
		bounds.x = (width - width/3*2)/2;
		bounds.y = height/3;
		bounds.width = width/3*2;
		bounds.height = height / 3 * 2;
		deathImage = Assets.powerAdaptorOff;
		health = 500;
		resistance = 10;
		placed = true;

	}

	@Override
	public void tick() {
		
		if (c.getGameState().getWorldGenerator().powerMap[placex][placey] > 0) {
			adaptor = Assets.powerAdaptorOn;
			if(!preAddPower) {
				c.getGameState().getWorldGenerator().addPower(this, 7, placex, placey);
				preAddPower = true;
			}
		}
		else {
			adaptor = Assets.powerAdaptorOff;
			if(preAddPower) {
				c.getGameState().getWorldGenerator().removePower(7, placex, placey);
				preAddPower = false;
			}
		}
		
	}

	@Override
	public void render(Graphics g) {
		// drawing down the bounding box
		Graphics2D g2d = (Graphics2D) g;

		g.drawImage(adaptor, (int) (x - c.getGameCamera().getxOffset()),
				(int) (y - c.getGameCamera().getyOffset()), width, height, null);

	}

	@Override
	public void Die() {
		AudioPlayer.playAudio("audio/structureBreak.wav");
		if(preAddPower) 
			c.getGameState().getWorldGenerator().removePower(7, placex, placey);

	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub

	}
}
