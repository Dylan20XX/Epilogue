package staticEntity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import alphaPackage.ControlCenter;
import graphics.Assets;
import graphics.CT;
import items.Food;
import items.ItemManager;
import items.Ranged;
import items.Weapon;
import tiles.Tile;

public class TrashBag extends StaticEntity {

	// java's random API
	private Random r = new Random();

	private ControlCenter c;

	// variables for reference
	public static int width = Tile.TILEWIDTH * 2;
	public static int height = Tile.TILEWIDTH * 2;

	private BufferedImage bag;

	int numBag = CT.random(1, 6);
	
	public TrashBag(double x, double y, ControlCenter c) {
		super(x, y, width, height, c);

		this.c = c;

		// bounding box starting and ending location
		if(numBag == 1) {
            bag = Assets.trashBag[0];
            health = CT.random(0, 150);
            bounds.x = width / 5+3;
            bounds.y = height - height / 3+18;
            
            bounds.width = width / 2-14;
            bounds.height = height / 3-28;

        } else if(numBag == 2) {
            bag = Assets.trashBag[1];
            health = CT.random(150, 300);
            bounds.x = width / 5+3;
            bounds.y = height - height / 3+18;
            
            bounds.width = width / 2+12;
            bounds.height = height / 3-23;
        } else if(numBag == 3) {
            bag = Assets.trashBag[2];
            health = CT.random(300, 450);
            
            bounds.x = width / 5-20;
            bounds.y = height - height / 3+20;
            
            bounds.width = width / 2+33;
            bounds.height = height / 3-23;

        } else if(numBag == 4) {
            bag = Assets.trashBag[3];
            health = CT.random(450, 600);
            
            bounds.x = width / 5-20;
            bounds.y = height - height / 3+18;
            
            bounds.width = width / 2+60;
            bounds.height = height / 3-23;
        } else if(numBag == 5) {
            bag = Assets.trashBag[4];
            health = CT.random(600, 750);
            bounds.x = width / 5-20;
            bounds.y = height - height / 3+10;
            
            bounds.width = width / 2+60;
            bounds.height = height / 3-15;
        } else if(numBag == 6) {
            bag = Assets.trashBag[5];
            health = CT.random(750, 900);
            bounds.x = width / 5-20;
            bounds.y = height - height / 3+10;
            
            bounds.width = width / 2+60;
            bounds.height = height / 3-15;
        }

		name = "trash bag";
		
		deathImage = Assets.trashBag[3];

		health = CT.random(1, 750);
		resistance = 30;

	}

	@Override
	public void tick() {


	}

	@Override
	public void render(Graphics g) {

		g.drawImage(bag, (int) (x - c.getGameCamera().getxOffset()), (int) (y - c.getGameCamera().getyOffset()), width,
				height, null);

	}

	@Override
	public void Die() {
		if(CT.random(1, 200) == 1) {
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Ranged.megashakalaka.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
		
		}
		if(CT.random(1, 75) == 1) {
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Ranged.desertEagle.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
		
		}
		if(CT.random(1, 50) == 1) {
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Ranged.glock.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
		
		}
		if(CT.random(1, 50) == 1) {
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Weapon.katana.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
		
		}
		if(CT.random(1, 25) == 1) {
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Weapon.batWeapon.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
		
		}
		if(CT.random(1, 100) == 1) {
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Weapon.SteelSword.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
		
		}
		if(CT.random(1, 150) == 1) {
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Weapon.giantSawBlade.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
		
		}
		if(CT.random(1, 5) == 1) {
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Food.rotItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
		
		}
		
		for(int i = 0; i < numBag; i++) {
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(ItemManager.trashList.get(CT.random(0, ItemManager.trashList.size()-1)).
					createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
		
		}
	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub

	}

}
