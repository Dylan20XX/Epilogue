package structures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import alphaPackage.ControlCenter;
import audio.AudioPlayer;
import creatures.Player;
import graphics.Assets;
import graphics.CT;
import graphics.CustomTextWritter;
import items.Food;
import items.Item;
import staticEntity.StaticEntity;
import structureInventory.DisintegratorCraft;
import tiles.Tile;

public class Disintegrator extends StaticEntity {

	public static int width = StaticEntity.DEFAULT_STATICOBJECT_WIDTH + 30;
	public static int height = StaticEntity.DEFAULT_STATICOBJECT_HEIGHT + 35;

	private DisintegratorCraft craft;

	public Disintegrator(double x, double y, ControlCenter c) {
		super(x, y, width, height, c);

		type = "timed crafting structure";

		bounds.width = width - 51;
		bounds.height = height - 68;
		bounds.x = width / 2 - 22;
		bounds.y = width / 2 + 20;

		health = 300;
		resistance = 10;
		placed = true;

		craft = new DisintegratorCraft(c);
	}

	@Override
	public void tick() {

		if (!Player.getPlayerData().isDisintegratorActive())
			craft.tick();

	}

	@Override
	public void render(Graphics g) {

		if (!craft.products.isEmpty()) {

			CustomTextWritter.drawString(g, "Collect Your Items", (int) (x - c.getGameCamera().getxOffset() + 32),
					(int) (y - c.getGameCamera().getyOffset()), active, Color.WHITE, Assets.font16);

		}

		if (!craft.currentlySmelting.isEmpty()) {

			// CustomTextWritter.drawString(g, "Smelt Time " + (craft.getSmeltTime() -
			// craft.getSmeltTimer()) / 1000, (int) (x - c.getGameCamera().getxOffset() +
			// 32),
			// (int) (y - c.getGameCamera().getyOffset()) - 30, active, Color.WHITE,
			// Assets.font16);

			g.setColor(Color.GREEN);
			g.fillRect((int) (x - c.getGameCamera().getxOffset() + 32 - 50),
					(int) (y - c.getGameCamera().getyOffset()) - 30,
					(int) (((double) (craft.getSmeltTime() - craft.getSmeltTimer()) / (double) craft.getSmeltTime())
							* 100),
					10);

			// Change this to an asset for an disintegrator that is active
			g.drawImage(Assets.disintegrator[1], (int) (x - c.getGameCamera().getxOffset()),
					(int) (y - c.getGameCamera().getyOffset()), width, height, null);

		} else {

			// Change this to an asset for an disintegrator that is not active
			g.drawImage(Assets.disintegrator[0], (int) (x - c.getGameCamera().getxOffset()),
					(int) (y - c.getGameCamera().getyOffset()), width, height, null);

		}

	}

	@Override
	public void Die() {
		AudioPlayer.playAudio("audio/structureBreak.wav");
		for(int i = 0; i < CT.random(10, 30); i++)
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getItemManager().addItem(Item.rockItem.createNew((int) x + bounds.x + CT.random(0, bounds.width), (int) y + bounds.y + CT.random(0, bounds.height)));
	}

	@Override
	public void interact() {

		if (craft.products.isEmpty() && craft.currentlySmelting.isEmpty()) {
			craft.setActive(true);
			Player.getPlayerData().setDisintegratorActive(true);
			Player.getPlayerData().setDisintegrator(this);
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().setDisintegratorActive(true);
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().setDisintegrator(this);
			craft.craftSetup();
			craft.findCraftableRecipes();
		} else if (!craft.products.isEmpty()){
			craft.collectItems();
		}

	}

	public DisintegratorCraft getCraft() {
		return craft;
	}

	public void setCraft(DisintegratorCraft craft) {
		this.craft = craft;
	}

}
