package creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import alphaPackage.ControlCenter;
import graphics.Animation;
import graphics.Assets;
import graphics.CT;

public class Ostrich extends Creatures {

	public Ostrich(double x, double y, ControlCenter c) {
		super(x, y, Creatures.DEFAULT_CREATURE_WIDTH * 2, Creatures.DEFAULT_CREATURE_HEIGHT * 2, c);

		health = 650;
		speed = 2;
		resistance = 10;
		knockValue = 8;
		attackBoundSize = 600;
		damage = 200;
		name = "ostrich";
		type = "creatures";
		weight = 75;

		bounds.x = width/5-5;
        bounds.y = width/5*4;
        bounds.width = width;
        bounds.height = width/2-10;
		
		left = new Animation(200, CT.flip(Assets.ostrich), true);
		right = new Animation(200, Assets.ostrich, true);

	}

	@Override
	public void tick() {
		AI();
		if (move)
			move();
		left.tick();
		right.tick();
	}

	public void AI() {
		
		if(c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getEntityManager().getPlayer()
				.getBounds().intersects(attackBound())) {
			
			if (damageBound().intersects(Player.getPlayerData().getBounds())) 
				knockbackPlayer(this);
			
			chase(1.5);
			
		}
			
		else 
			
			natural();

	}

	@Override
	public void render(Graphics g) {
		g.drawImage(getCurrentAnimation(), (int) (x - c.getGameCamera().getxOffset()),
				(int) (y - c.getGameCamera().getyOffset()), width + 32, height + 32, null);

		g.setColor(Color.blue);
		Graphics2D g2d = (Graphics2D) g;
		g2d.draw(getBounds());
		g.setColor(Color.GREEN);
		g2d.draw(attackBound());
	}

	@Override
	public void Die() {
		
	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub
		
	}
}

