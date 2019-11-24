package items;

import creatures.Player;
import graphics.Assets;
import structures.TorchLight;
import tiles.Tile;

public class Torch extends Item{

	private int curFuel;
	private static final int fuel = 18000;
	private static TorchLight tl;
	private static int prevPTX = 0, prevPTY = 0, prevLumen = 3, PTX = 0, PTY = 0, Lumen = 3;
	private boolean ini = false;
	
	public Torch() {
		super(Assets.torch, "torch", 11, false, "torch", 0.1, 25, 20);
		curFuel = fuel;
	}
	
	@Override
	public void tick() {
		if(isEquipped) {
			if(!ini){
				onEquip();
				ini = true;
			}
			curFuel -= 1;
			PTX = (int) (c.getGameState().getWorldGenerator().getEntityManager().getPlayer().getX() / Tile.TILEWIDTH);
			PTY = (int) (c.getGameState().getWorldGenerator().getEntityManager().getPlayer().getY() / Tile.TILEHEIGHT);
			Lumen = getLumen();
			if(PTX != prevPTX || PTY != prevPTY || Lumen != prevLumen) {
				c.getGameState().getWorldGenerator().addLight(tl, 5, PTX, PTY, Lumen);
				c.getGameState().getWorldGenerator().removeLight(5, prevPTX, prevPTY, prevLumen);
				prevPTX = PTX;
				prevPTY = PTY;
				prevLumen = Lumen;
			}
		} else {
			ini = false;
			c.getGameState().getWorldGenerator().removeLight(5, PTX, PTY, Lumen);
		}
		
		if(curFuel <= 0) {
			tl.Die();
			Player.getPlayerData().getInventory().addItem(Item.ashe);
			timeDropped = ItemManager.DROPTIME;
			Player.getPlayerData().getInventory().removeItem(this);
		}
	}
	
	public void onEquip() {
		prevPTX = (int) (c.getGameState().getWorldGenerator().getEntityManager().getPlayer().getX() / Tile.TILEWIDTH);
		prevPTY = (int) (c.getGameState().getWorldGenerator().getEntityManager().getPlayer().getY() / Tile.TILEHEIGHT);
		prevLumen = getLumen();
		PTX = prevPTX;
		PTY = prevPTY;
		Lumen = prevLumen;
		tl = new TorchLight(c);
		c.getGameState().getWorldGenerator().addLight(tl, 5, PTX, PTY, Lumen);
	}
	
	public int getFuelPercentage() {
		return (int)((double) curFuel/fuel * 100);
	}

	public int getLumen() {
		int bright = getFuelPercentage();
		if(bright < 33)
			return 1;
		if(bright < 66)
			return 2;
		return 3;
	}
}