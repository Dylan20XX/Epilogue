package staticEntity;

import java.awt.image.BufferedImage;

import alphaPackage.ControlCenter;
import creatures.Player;
import entity.Entity;
import graphics.Assets;
import graphics.CT;
import graphics.DeathAnimation;
import inventory.Effect;

/*
 * abstract parent class for all static entities(non-moving objects)
 */
public abstract class StaticEntity extends Entity {

	protected long lastAttackTimer, AttackCooldown = 1000, AttackTimer = 0;
	protected int damage = 0;

	public static final int DEFAULT_STATICOBJECT_WIDTH = (int) (64 * ControlCenter.scaleValue),
			DEFAULT_STATICOBJECT_HEIGHT = (int) (64 * ControlCenter.scaleValue);

	public int placex, placey;

	protected BufferedImage deathImage;
	protected int buildingXPGiven;
	protected boolean placed = false;

	// constructor takes in the properties of a static entity
	public StaticEntity(double x, double y, int width, int height, ControlCenter c) {
		super(x, y, width, height, c);

		// default death image
		deathImage = Assets.rock;
		type = "static entity";
		buildingXPGiven = 1;
		
	}

	@Override
	public void hurt(int amt) {
		health -= (amt - resistance);
		if (health <= 0) {
			active = false; // remove an entity from entity list by setting the active into false
			Die();
			c.getGameState().getWorldGenerator()
					.addDeathAnimation(new DeathAnimation(c, deathImage, (int) (x + bounds.x + bounds.width / 2),
							(int) (y + bounds.y + bounds.height / 2), width, height, true));
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getEntityManager()
					.removeEntity(this);
			Player.getPlayerData().setBuildingXP(Player.getPlayerData().getBuildingXP() + buildingXPGiven); // given
																											// building
																											// xp when
																											// destroyed
		}
	}

	public void knockbackPlayer(Entity e) {

		AttackTimer += System.currentTimeMillis() - lastAttackTimer;
		lastAttackTimer = System.currentTimeMillis();

		if (AttackTimer < AttackCooldown)
			return;

		AttackTimer = 0;
		if (Player.getPlayerData().isCanMove() && e instanceof StaticEntity) {

			StaticEntity cre = (StaticEntity) e;
			Player.getPlayerData().hurt(cre.damage);
			double Amt = cre.getKnockValue() - (double) Player.getPlayerData().getInventory().getInventoryWeight() / 6;

			if (Amt > 0) {

				double sX = e.getBounds().getX() + e.getBounds().getWidth() / 2;
				double sY = e.getBounds().getY() + e.getBounds().getHeight() / 2;
				double eX = Player.getPlayerData().getBounds().getX()
						+ Player.getPlayerData().getBounds().getWidth() / 2;
				double eY = Player.getPlayerData().getBounds().getY()
						+ Player.getPlayerData().getBounds().getHeight() / 2;
				double knockAngle = -Math.atan2(sX - eX, sY - eY);

				Player.getPlayerData().setKnockX(Math.sin(knockAngle) * Amt);
				Player.getPlayerData().setKnockY(Math.cos(knockAngle) * Amt);

				Player.getPlayerData().setCanMove(false);
			}

			if (cre.getName().equals("sentry")) {
				int rand = CT.random(1, 3);
				if (rand == 1)
					c.getGameState().getWorldGenerator().getEffects().addEffect(new Effect("bleeding", 3000));
			}

			new java.util.Timer().schedule(new java.util.TimerTask() {
				@Override
				public void run() {

					Player.getPlayerData().setCanMove(true);

				}
			}, 500);

		}

	}

	public long getLastAttackTimer() {
		return lastAttackTimer;
	}

	public void setLastAttackTimer(long lastAttackTimer) {
		this.lastAttackTimer = lastAttackTimer;
	}

	public long getAttackCooldown() {
		return AttackCooldown;
	}

	public void setAttackCooldown(long attackCooldown) {
		AttackCooldown = attackCooldown;
	}

	public long getAttackTimer() {
		return AttackTimer;
	}

	public void setAttackTimer(long attackTimer) {
		AttackTimer = attackTimer;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getPlacex() {
		return placex;
	}

	public void setPlacex(int placex) {
		this.placex = placex;
	}

	public int getPlacey() {
		return placey;
	}

	public void setPlacey(int placey) {
		this.placey = placey;
	}

	public BufferedImage getDeathImage() {
		return deathImage;
	}

	public void setDeathImage(BufferedImage deathImage) {
		this.deathImage = deathImage;
	}

	public int getBuildingXPGiven() {
		return buildingXPGiven;
	}

	public void setBuildingXPGiven(int buildingXPGiven) {
		this.buildingXPGiven = buildingXPGiven;
	}

	public boolean isPlaced() {
		return placed;
	}

	public void setPlaced(boolean placed) {
		this.placed = placed;
	}

}
