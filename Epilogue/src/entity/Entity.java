package entity;

import java.awt.Graphics;
import java.awt.Rectangle;

import alphaPackage.ControlCenter;
import audio.AudioPlayer;
import creatures.Parasite;
import creatures.Sentry;
import creatures.SentryBroodMother;
import creatures.SentryMajor;
import staticEntity.Cave;
import staticEntity.SentryHive;
import staticEntity.SentrySpike;
import staticEntity.StaticEntity;

/*
 * parent class for all entities
 */
public abstract class Entity {
	//these properties belongs to all entities, either static or non-static
	public static final int DEFAULT_HEALTH = 1000; //default health
	public static final int DEFAULT_RESISTANCE = 0; //default health
	protected double x, y;	//protected allow extended class to have access to them
	protected int width, height;
	protected Rectangle bounds;
	protected int health;
	protected int ogHealth;
	protected int resistance; //the total damage taken will be damage - resistance
	protected boolean active = true;
	protected EntityManager em;
	protected ControlCenter c;
	protected String name;
	protected String type;
	protected int knockValue;
	
	protected int id;
	protected String requiredTool;
	
	//constructor of the entity class
	public Entity(double x, double y, int width, int height, ControlCenter c) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.c = c;
		
		//if not customized, then health will equal to default health
		health = DEFAULT_HEALTH;
		resistance = DEFAULT_RESISTANCE;
		knockValue = 0;
		name = "";
		type = "";
		requiredTool = "";
		
		bounds = new Rectangle(0, 0, width, height); //creating a basic bounding box for the object
		//entity detection will be implemented later on
	}
	
	//method that can be called when an entity is damaged
	public void hurt(int amt) {
		health -= (amt - resistance);
		
		if(health <= 0) {
			active = false; //remove an entity from entity list by setting the active into false
			Die();
			if(type.equals("static entity"))
				c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getEntityManager().removeEntity(this);
			else if(type.equals("creature"))
				c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getEntityManager().removeCreature(this);
				
		}
	}
	
	public boolean checkEntityCollision(double velX, double velY) { //collision test

		if(this instanceof Sentry || this instanceof SentryMajor || this instanceof Parasite) {
			
			for(int i = 0; i < c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().size(); i++) {
				
				Entity e = c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().get(i);
				if(e.equals(this) || e instanceof Sentry || e instanceof Parasite || e instanceof SentryHive || e instanceof SentryBroodMother 
						|| e instanceof SentryMajor || e instanceof SentrySpike || e instanceof Cave) {
	                continue;
	            }
	            if(e.getCollisionBounds(0, 0).intersects(getCollisionBounds(velX, velY))) {
	            	return true;
	            }
				
			}
			
		} else {
			
			for(int i = 0; i < c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().size(); i++) {
				
				Entity e = c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().get(i);
				if(e.equals(this)) {
	                continue;
	            }
	            if(e.getCollisionBounds(0, 0).intersects(getCollisionBounds(velX, velY))) {
	            	return true;
	            }
				
			}
			
		}

		
        return false;
    }
	
	public Rectangle getCollisionBounds(double velX, double velY) {
        return new Rectangle((int)(x + bounds.x + velX), (int)(y + bounds.y + velY), bounds.width, bounds.height);
    }
	
	public Rectangle damageBound() {
		return new Rectangle((int) (x - c.getGameCamera().getxOffset()) - 5 + bounds.x,
				(int) (y - c.getGameCamera().getyOffset()) - 5 + bounds.y, bounds.width + 10, bounds.height + 10);
	}
	
	//abstract methods
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	//what the entity will drop
	public abstract void Die();
	
	//what the entity does when clicked
	public abstract void interact();
	
	//getters and setters
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Rectangle getBounds() {
		return new Rectangle((int)(x - c.getGameCamera().getxOffset() + bounds.x), 
				(int)(y - c.getGameCamera().getyOffset() + bounds.y), bounds.width, bounds.height);
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public static int getDefaultHealth() {
		return DEFAULT_HEALTH;
	}

	public int getResistance() {
		return resistance;
	}

	public void setResistance(int resistance) {
		this.resistance = resistance;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getKnockValue() {
		return knockValue;
	}

	public void setKnockValue(int knockValue) {
		this.knockValue = knockValue;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public int getYBounds() {
		return (int) getBounds().getY();
	}
	
	public String getRequiredTool() {
		return requiredTool;
	}

	public void setRequiredTool(String requiredTool) {
		this.requiredTool = requiredTool;
	}
}
