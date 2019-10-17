package ammo;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import alphaPackage.ControlCenter;
import creatures.Creatures;
import creatures.Player;
import graphics.Animation;
import graphics.Assets;
import graphics.CT;

public class SandProjectile extends Creatures{
	
	public static int width = Creatures.DEFAULT_CREATURE_WIDTH/3;
	public static int height = Creatures.DEFAULT_CREATURE_HEIGHT/3;
	public double finaly;
	private boolean attacked = false;
	
	public SandProjectile(double x, double y, double finaly, ControlCenter c) {
		super(x, y, width, height, c);
		
		health = 50;
		damage = 220;
		knockValue = 0;
		weight = 1;
		type = "creatures";
		
		bounds.x = 0;
		bounds.y = 0;
		bounds.width = width;
		bounds.height = height;
		
		this.finaly = finaly;
		finaly += 20;
		x -= 100;
		x += CT.random(0, 200);
		
		BufferedImage[] temp = new BufferedImage[1];
		temp[0] = Assets.sandShotDown;
		
		left = new Animation(0, temp, true);
		right = new Animation(0, temp, true);
		
	}

	@Override
	public void tick() {
		y += 8;
		//System.out.println(x + " " + y + " " + Player.getPlayerData().getX() + " " + Player.getPlayerData().getY());
		if(y >= finaly) {
			 if(getBounds().intersects(Player.getPlayerData().getBounds())) {
					knockbackPlayer(this);
			}
			hurt(9999);
			return;
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
