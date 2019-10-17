package structures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import alphaPackage.ControlCenter;
import ammo.HeavyPulse;
import audio.AudioPlayer;
import creatures.Creatures;
import entity.Entity;
import graphics.Assets;
import graphics.CT;
import staticEntity.StaticEntity;
import tiles.Tile;

public class HeavyPulseArtillery extends StaticEntity {

	// java's random API
	private Random r = new Random();

	private ControlCenter c;

	// variables for reference
	public static int width = Tile.TILEWIDTH / 2 * 3;
	public static int height = Tile.TILEHEIGHT / 2 * 3;

	private BufferedImage base = Assets.artileryBase[1];
	private BufferedImage head = CT.rotateClockwise90(CT.rotateClockwise90(CT.rotateClockwise90(Assets.heavyPulse)));

	private Queue<Creatures> targets = new LinkedList<Creatures>();

	private long lastAttackTimer, attackCooldown = 4000, attackTimer = 0; // attack speed every 0.5s

	private double shootAngle = Math.PI/2;
	
	private boolean on;

	public HeavyPulseArtillery(double x, double y, ControlCenter c) {
		super(x, y, width, height, c);

		this.c = c;
		name = "artilery";
		health = 800;
		deathImage = Assets.artileryBase[0];
		
		// bounding box starting and ending location
		bounds.x = width / 6;
		bounds.y = height - height / 3 - 10;
		bounds.width = width / 6 * 4;
		bounds.height = height / 3;

		health = 200;
		resistance = 10;

	}

	@Override
	public void tick() {
		
		if (c.getGameState().getWorldGenerator().powerMap[placex][placey] > 0) {
			base = Assets.artileryBase[1];
			on = true;
		}
		else {
			base = Assets.artileryBase[0];
			on = false;
		}

	}

	@Override
	public void render(Graphics g) {

		if(on)
			if (targets.isEmpty())
				for (int i = 0; i < c.getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound()
						.size(); i++) {
					Entity e = c.getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().get(i);
					Creatures c;
					if (e.getType().equals("creatures")) {
						c = (Creatures) e;
					} else
						continue;
	
					if (c.getBounds().intersects(range()) && c.isChasing()) {
						targets.add(c);
						break;
					}
	
				}
			else {
				
				Entity target = targets.peek();
				
				if (!target.isActive())
					targets.poll();
				
				else if (target.getBounds().intersects(range())) {
	
					attackTimer += System.currentTimeMillis() - lastAttackTimer;
					lastAttackTimer = System.currentTimeMillis();
	
					double sX = target.getBounds().x + target.getBounds().width / 2 + c.getGameCamera().getxOffset();
					double sY = target.getBounds().y + target.getBounds().height / 2 + c.getGameCamera().getyOffset();
	
					double eX = x + bounds.x + bounds.width/2;
					double eY = y + bounds.y; 
					shootAngle = Math.atan2(sX - eX, sY - eY);
	
					if (attackTimer > attackCooldown) {
	
						AudioPlayer.playAudio("audio/heavyPulse.wav");
						c.getGameState().getWorldGenerator().getEntityManager().addEntity(new HeavyPulse((int)x + bounds.x + bounds.width/2 -8, (int)(y + bounds.y - 21), 34, 12, shootAngle, c));
	
						attackTimer = 0;
	
					}
	
				} else {
					targets.poll();
				}
	
			}

		// drawing down the bounding box
		Graphics2D g2d = (Graphics2D) g;

		g.drawImage(base, (int) (x - c.getGameCamera().getxOffset()), (int) (y - c.getGameCamera().getyOffset()), width,
				height, null);

		AffineTransform backup = g2d.getTransform();
		AffineTransform at = g2d.getTransform();
		// at.translate(x - c.getGameCamera().getxOffset(), y -
		// c.getGameCamera().getyOffset());
		at.rotate(-shootAngle, x - c.getGameCamera().getxOffset() + width / 2,
				y - c.getGameCamera().getyOffset() + height / 2 - 10);
		g2d.setTransform(at);
		g2d.drawImage(head, (int) (x - c.getGameCamera().getxOffset()),
				(int) (y - c.getGameCamera().getyOffset() - 10), width, height, null);
		g2d.setTransform(backup);

	}

	private Rectangle range() {
		return new Rectangle((int) (x + bounds.x - c.getGameCamera().getxOffset()) - 450 + bounds.width / 2 - 3,
				(int) (y + bounds.y - c.getGameCamera().getyOffset() - 450) + bounds.height / 2, 900, 900);

	}

	@Override
	public void Die() {
		
	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub

	}

}
