package ammo;

import java.awt.Graphics;

import alphaPackage.ControlCenter;
import creatures.Creatures;
import creatures.Player;
import graphics.Assets;
import graphics.CT;

public class SandProjectileUp extends Creatures{
	
	public static int width = Creatures.DEFAULT_CREATURE_WIDTH/3;
	public static int height = Creatures.DEFAULT_CREATURE_HEIGHT/3;
	public double finaly;
	private boolean created = false;
	
	public SandProjectileUp(double x, double y, ControlCenter c) {
		
		super(x, y, width, height, c);
		
		health = 100;
		damage = 0;
		type = "creatures";
		
		bounds.x = 0;
		bounds.y = 0;
		bounds.width = 1;
		bounds.height = 1;
		
		finaly += 20;
		
	}

	@Override
	public void tick() {
		y -= 8;
		
		if(getY() < Player.getPlayerData().getY() - c.getHeight()/2 - 100) {
			int shootx = (int)c.getGameState().getWorldGenerator().getEntityManager().getPlayer().getX();
			int shooty = (int)c.getGameState().getWorldGenerator().getEntityManager().getPlayer().getY();
			int xt = -50;
			xt += CT.random(0, 100);
			if(!created) {
				c.getGameState().getWorldGenerator().getEntityManager().addEntity(new SandProjectile(shootx+xt, Player.getPlayerData().getY() - c.getHeight()/2 - 500, shooty + 10, c));
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
