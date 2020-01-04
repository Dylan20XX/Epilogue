package creatures;

import java.awt.Graphics;

import alphaPackage.ControlCenter;
import graphics.Animation;
import graphics.Assets;
import graphics.CT;
import world.WorldGenerator;

public class Parasite extends Creatures {

	public Parasite(double x, double y, ControlCenter c) {
		super(x, y, Creatures.DEFAULT_CREATURE_WIDTH / 4, Creatures.DEFAULT_CREATURE_HEIGHT / 4, c);

		health = 10;
		speed = 4.2;
		damage = 80;
		knockValue = 8;
		name = "sentry";
		attackBoundSize = 1500;
		type = "creatures";
		weight = 5;
		
		bounds.x = 5;
		bounds.y = 35/2;
		bounds.width = 50/2;
		bounds.height = 10/2;

		left = new Animation(125, Assets.parasite, true);
		right = new Animation(125, CT.flip(Assets.parasite), true);
		
		combatXPDropped = 0;

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

		if (damageBound().intersects(Player.getPlayerData().getBounds())) 
			knockbackPlayer(this);
		
		chase(1);

	}

	@Override
	public void render(Graphics g) {
		g.drawImage(getCurrentAnimation(), (int) (x - c.getGameCamera().getxOffset()),
				(int) (y - c.getGameCamera().getyOffset()), width + 16, height + 16, null);

	}

	@Override
	public void Die() {
		WorldGenerator.numParasite--;
	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub

	}

}
