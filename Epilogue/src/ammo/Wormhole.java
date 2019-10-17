package ammo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import alphaPackage.ControlCenter;
import creatures.Creatures;
import graphics.Assets;
import graphics.CT;
import staticEntity.StaticEntity;

public class Wormhole extends StaticEntity{
	
	public static int width = Creatures.DEFAULT_CREATURE_WIDTH*10;
	public static int height = Creatures.DEFAULT_CREATURE_HEIGHT*10;
	
	public BufferedImage curImage = Assets.sandCreepGround[0];
	
	public Wormhole(double x, double y, ControlCenter c) {
		
		super(x, y, width, height, c);
		
		health = 9999;
		name = "worm hole";
		
		bounds.x = 128;
		bounds.y = 128;
		bounds.width = 1;
		bounds.height = 1;
		
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		
		g.drawImage(curImage, (int) (x - c.getGameCamera().getxOffset()),
				(int) (y - c.getGameCamera().getyOffset()), width, height, null);
		g.setColor(Color.blue);
		Graphics2D g2d = (Graphics2D) g;
		g2d.draw(getBounds());
	}

	@Override
	public void Die() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub
		
	}
}
