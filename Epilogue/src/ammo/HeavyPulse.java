package ammo;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import alphaPackage.ControlCenter;
import creatures.Creatures;
import creatures.Player;
import entity.Entity;
import graphics.Animation;
import graphics.Assets;

public class HeavyPulse extends Creatures {

	private double xMove;
	private double yMove;
	private double angle = 0;
	
	public HeavyPulse(int x, int y, int width, int height, double angle, ControlCenter c) {
		super(x, y, width, height, c);
		
		health = 9999;
		speed = 35;
		knockValue = 1;
		weight = 1;
		type = "pulse";
		damage = 250;
		lit = true;
		
		bounds.x = 0;
		bounds.y = 5;
		bounds.width = 10;
		bounds.height = 10;
		
		xMove = speed*Math.sin(angle);
        yMove = speed*Math.cos(angle);
        this.angle = angle;
        
        BufferedImage[] Bleft = new BufferedImage[1];
        Bleft[0] = Assets.rapidPulse;
        
        left = new Animation(0, Bleft, true);
        right = new Animation(0, Bleft, true);
		
	}

	@Override
	public void tick() {
		x += xMove;
		y += yMove;
		for(int i = 0; i < c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().size(); i++) {
			
			Entity e = c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().get(i);
			
			if(e.getBounds().intersects(getBounds()) && !e.getType().equals("wall") && !e.getName().equals("artilery") && !e.equals(Player.getPlayerData()) && !e.equals(this)) {
				if(e.getType().equals("creatures")) {
					knockbackTarget((Creatures)e, this, true, angle);
				}
				active = false;
			}
			
		}
	}

	@Override
	public void render(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;
		
		AffineTransform backup = g2d.getTransform();
		AffineTransform at = g2d.getTransform();
		at.rotate(-angle-Math.PI/2, x - c.getGameCamera().getxOffset() + width / 2,
				y - c.getGameCamera().getyOffset() + height / 2);
		g2d.setTransform(at);
		g2d.drawImage(Assets.heavyPulseFire, (int) (x - c.getGameCamera().getxOffset() - width),
				(int) (y - c.getGameCamera().getyOffset() + 5 - height/2), width, height, null);

		g2d.setTransform(backup);
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