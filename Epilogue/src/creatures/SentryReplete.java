package creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import alphaPackage.ControlCenter;
import graphics.Animation;
import graphics.Assets;
import graphics.CT;
import items.Item;

public class SentryReplete extends Creatures {
	public static int width = Creatures.DEFAULT_CREATURE_WIDTH / 2;
	public static int height = Creatures.DEFAULT_CREATURE_HEIGHT /2;
	 
	public SentryReplete(double x, double y, ControlCenter c) {
		super(x, y, width, height, c);

		health = 200;
		speed = 0;
		damage = 170;
		knockValue = 13;
		name = "sentryReplete";
		attackBoundSize = 800;
		type = "creatures";
		weight = 60;
		
		bounds.x = width/4-13;
        bounds.y = height/3*2+3;
        bounds.width = width+26;
        bounds.height = height-40;

		left = new Animation(125, Assets.sentryMajor, true);
		right = new Animation(125, CT.flip(Assets.sentryMajor), true);

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
			
			chase(1);
			
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
		//g2d.draw(attackBound());

	}

	@Override
	public void Die() {
		
	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub

	}
}
