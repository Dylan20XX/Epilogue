package ammo;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import alphaPackage.ControlCenter;
import creatures.Creatures;
import creatures.Player;
import graphics.Animation;
import graphics.Assets;

public class SandProjectile2 extends Creatures{
	
	public static int width = Creatures.DEFAULT_CREATURE_WIDTH/2;
	public static int height = Creatures.DEFAULT_CREATURE_HEIGHT/2;
	public double finaly;
	private boolean attacked = false;
	
	public SandProjectile2(double x, double y, double finaly, ControlCenter c) {
		super(x, y, width, height, c);
		
		health = 50;
		damage = 480;
		knockValue = 0;
		weight = 1;
		type = "creatures";
		
		bounds.x = 0;
		bounds.y = 0;
		bounds.width = 1;
		bounds.height = 1;
		
		BufferedImage[] temp = new BufferedImage[1];
		temp[0] = Assets.sandShotDown;
		
		left = new Animation(0, temp, true);
		right = new Animation(0, temp, true);
		
		this.finaly = finaly;
		finaly += 10;
		
	}

	@Override
	public void tick() {
		y += 10;
		//System.out.println(x + " " + y + " " + Player.getPlayerData().getX() + " " + Player.getPlayerData().getY());
		if(y >= finaly) {
			bounds.x = -width/2;
			bounds.y = -height/2;
			bounds.width = width*2;
			bounds.height = height*2;
			if(getBounds().intersects(Player.getPlayerData().getBounds())) {
					knockbackPlayer(this);
			}
			hurt(9999);
		}
		
	}

	@Override
	public void render(Graphics g) {
		
		g.drawImage(Assets.sandShotDown, (int) (x - c.getGameCamera().getxOffset()),
				(int) (y - c.getGameCamera().getyOffset()), width, height, null);
		
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
