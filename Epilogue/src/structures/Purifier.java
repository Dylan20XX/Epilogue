package structures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import alphaPackage.ControlCenter;
import creatures.Player;
import graphics.Assets;
import graphics.CT;
import graphics.CustomTextWritter;
import items.Item;
import items.WaterContainer;
import staticEntity.StaticEntity;
import structureInventory.PurifierCraft;
import tiles.Tile;

public class Purifier extends StaticEntity {

	public static int width = StaticEntity.DEFAULT_STATICOBJECT_WIDTH + 26;
	public static int height = StaticEntity.DEFAULT_STATICOBJECT_HEIGHT + 26;

	Item lastHeldItem;
	private int purificationTimer = 0, purificationCooldown = 100;
	private long resetTimer = 0, resetCooldown = 5000, lastResetTimer;
	private boolean currentlyPurifying = false;

	public Purifier(double x, double y, ControlCenter c) {
		super(x, y, width, height, c);

		type = "timed crafting structure";

		bounds.width = width - 20;
		bounds.height = height / 2 - 15;

		bounds.x = width / 2 - 35;
		bounds.y = height / 2 + 8;

		health = 300;
		resistance = 10;

	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics g) {

		g.drawImage(Assets.purifier, (int) (x - c.getGameCamera().getxOffset()),
				(int) (y - c.getGameCamera().getyOffset()), width, height, null);

		// drawing down the bounding box
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.BLUE);
		g2d.draw(getBounds());

		if (currentlyPurifying) {
			CustomTextWritter.drawString(g, "Purifying Water", (int) (x - c.getGameCamera().getxOffset() + 32 - 80),
					(int) (y - c.getGameCamera().getyOffset()) - 22, false, Color.WHITE, Assets.font16);
			g.setColor(Color.GREEN);
			g.fillRect((int) (x - c.getGameCamera().getxOffset() + 32 - 50),
					(int) (y - c.getGameCamera().getyOffset()) - 10,
					(int) (((double) (purificationCooldown - purificationTimer) / (double) purificationCooldown) * 100),
					10);

			resetTimer += System.currentTimeMillis() - lastResetTimer;
			lastResetTimer = System.currentTimeMillis();

			if (resetTimer < resetCooldown)
				return;

			purificationTimer = 0;
			currentlyPurifying = false;
			resetTimer = 0;
		}

	}

	@Override
	public void Die() {
		for (int i = 0; i < CT.random(1, 15); i++) {
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getItemManager()
					.addItem(Item.rockItem.createNew((int) x + bounds.x + CT.random(0, bounds.width),
							(int) y + bounds.y + CT.random(0, bounds.height)));
		}
		for (int i = 0; i < CT.random(2, 6); i++) {
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getItemManager()
					.addItem(Item.tinIngotItem.createNew((int) x + bounds.x + CT.random(0, bounds.width),
							(int) y + bounds.y + CT.random(0, bounds.height)));
		}
		c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getItemManager()
				.addItem(Item.purificationSinkItem.createNew((int) x + bounds.x + CT.random(0, bounds.width),
						(int) y + bounds.y + CT.random(0, bounds.height)));

	}

	@Override
	public void interact() {

		// reset the purification timer if the player changes their item
		if (lastHeldItem != Player.getPlayerData().getHands().getHand()) {
			lastHeldItem = Player.getPlayerData().getHands().getHand();
			purificationTimer = 0;
		}

		// Check if the player is holding an unpurified water bottle
		if (Player.getPlayerData().getHands().getHand() instanceof WaterContainer) {
			if (!((WaterContainer) Player.getPlayerData().getHands().getHand()).isPurified()
					&& ((WaterContainer) Player.getPlayerData().getHands().getHand()).getCurrentCapacity() != 0) {

				purificationTimer++;
				currentlyPurifying = true;
				resetTimer = 0;

				if (purificationTimer < purificationCooldown)
					return;

				currentlyPurifying = false;
				((WaterContainer) Player.getPlayerData().getHands().getHand()).setPurified(true);
				purificationTimer = 0;

			}

		}

	}

}
