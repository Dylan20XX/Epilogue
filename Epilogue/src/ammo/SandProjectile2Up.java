package ammo;

import java.awt.Graphics;

import alphaPackage.ControlCenter;
import creatures.Creatures;
import creatures.Player;
import graphics.Assets;
import graphics.CT;

public class SandProjectile2Up extends Creatures{
	
	public static int width = Creatures.DEFAULT_CREATURE_WIDTH/2;
	public static int height = Creatures.DEFAULT_CREATURE_HEIGHT/2;
	public double finaly;
	private boolean created = false;
	
	double xLoc, yLoc;
	
	public SandProjectile2Up(double x, double y, double xLoc, double yLoc, ControlCenter c) {
		
		super(x, y, width, height, c);
		
		health = 100;
		damage = 0;
		type = "creatures";
		this.xLoc = xLoc;
		this.yLoc = yLoc;
		
		bounds.x = 0;
		bounds.y = 0;
		bounds.width = 1;
		bounds.height = 1;
		
		finaly += 20;
		
	}

	@Override
	public void tick() {
		y -= 10;
		
		if(getY() < Player.getPlayerData().getY() - c.getHeight()/2 - 100) {
			if(!created) {
				c.getGameState().getWorldGenerator().getEntityManager().addEntity(new SandProjectile2(xLoc - 400 + CT.random(0, 800), Player.getPlayerData().getY() - c.getHeight()/2 - 500, yLoc - 400 + CT.random(0, 800), c));
				created = true;
			}
			active = false;
		}
	}

	@Override
	public void render(Graphics g) {
		
		g.drawImage(Assets.sandShotUp, (int) (x - c.getGameCamera().getxOffset()),
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
