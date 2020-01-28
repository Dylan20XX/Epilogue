package creatures;

import java.awt.Graphics;

import alphaPackage.ControlCenter;
import graphics.CT;

public class Testing extends Creatures {

	public Testing(double x, double y, int width, int height, ControlCenter c) {
		super(x, y, width, height, c);
		
		health = 500;
		speed = 2.5;
		resistance = CT.random(0, 5);
		intimidation = 10;
		name = "survivor";
		type = "creatures";
		weight = 55;
		
	}

	@Override
	public void tick() {
		
		
		
	}

	@Override
	public void render(Graphics g) {
		
		
		
	}

	@Override
	public void Die() {
		
		
		
	}

	@Override
	public void interact() {
		
		
		
	}

}
