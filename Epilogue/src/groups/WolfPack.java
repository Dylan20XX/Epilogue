package groups;

import java.awt.Rectangle;

import alphaPackage.ControlCenter;
import creatures.PackAlpha;
import creatures.PackMember;
import graphics.CT;
import tiles.Tile;

public class WolfPack {
	
	private ControlCenter c;
	private int x, y;
	
	public WolfPack(int x, int y, ControlCenter c) {
		
		this.c = c;
		
		this.x = x;
		this.y = y;
		
		c.getGameState().getWorldGenerator().getEntityManager().addCreature(new PackAlpha(x, y, c));
		int numMember = CT.random(2, 6);
		for(int i = 0; i < numMember; i++) {
			c.getGameState().getWorldGenerator().getEntityManager().addCreature(new PackMember(x, y, c));
		}
		
	}

}
