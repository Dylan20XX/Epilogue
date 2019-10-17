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
import graphics.CT;

public class RapidPulse extends Creatures {

	private double xMove;
	private double yMove;
	private double angle = 0;
	
	public RapidPulse(int x, int y, int width, int height, double angle, ControlCenter c) {
		super(x, y, width, height, c);
		
		health = 9999;
		speed = 35;
		knockValue = 2;
		weight = 3;
		type = "pulse";
		damage = 75;
		lit = true;
		
		bounds.x = 0;
		bounds.y = 5;
		bounds.width = 5;
		bounds.height = 5;
		double randAngle = Math.PI/CT.random(18, 36);
		this.angle = angle - Math.PI/36 + randAngle;
		xMove = speed*Math.sin(this.angle);
        yMove = speed*Math.cos(this.angle);
        
        
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
		g2d.drawImage(Assets.rapidPulseFire, (int) (x - c.getGameCamera().getxOffset() - width),
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