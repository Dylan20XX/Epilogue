package creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import alphaPackage.ControlCenter;
import audio.AudioPlayer;
import graphics.Animation;
import graphics.Assets;
import graphics.CT;
import items.Food;
import items.Item;
import tiles.Tile;

public class WoodenStick extends Creatures {
	
	protected Random r = new Random();
	
	public WoodenStick(double x, double y, ControlCenter c) {
		super(x, y, Creatures.DEFAULT_CREATURE_WIDTH / 2, Creatures.DEFAULT_CREATURE_HEIGHT / 2, c);

		health = 200;
		speed = 0;
		damage = 0;
		knockValue = 0;
		name = "woodenStick";
		attackBoundSize = 0;
		type = "material";
		weight = 10;
		
		bounds.width = 32;
		bounds.height = 32;
		bounds.x = Tile.TILEWIDTH / 2 - bounds.width / 2;
		bounds.y = Tile.TILEHEIGHT / 2 - bounds.height / 2;


	}

	@Override
	public void tick() {

	}

	public void AI() {


	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.woodenStick, (int) (x - c.getGameCamera().getxOffset()),
				(int) (y - c.getGameCamera().getyOffset()), width + 32, height + 32, null);

	}

	@Override
	public void Die() {
		
	}

	@Override
	public void interact() {
		
		int rand = r.nextInt(3) + 1; //get 2-5 items
		
		if(Player.getPlayerData().getInventory().addItem(Item.stickItem)) {
			
			for(int i = 0; i < rand - 1; i++)
				Player.getPlayerData().getInventory().addItem(Item.stickItem);
			
			AudioPlayer.playAudio("audio/pickup.wav");
			active = false;
		}

	}

}
