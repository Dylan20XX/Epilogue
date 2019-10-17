package structures;

import java.awt.Graphics;

import alphaPackage.ControlCenter;
import staticEntity.StaticEntity;

public class TorchLight extends StaticEntity{

	private ControlCenter c;
	
	public TorchLight(ControlCenter c) {
		super(0, 0, 1, 1, c);
		this.c = c;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Die() {
		c.getGameState().getWorldGenerator().removeLight(5, placex, placey, 1);
		
	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub
		
	}

}
