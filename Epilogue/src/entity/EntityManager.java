package entity;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import creatures.AwakenedSentinel;
import creatures.Player;
import creatures.SentryBroodMother;
import creatures.SleepingSentinel;
import creatures.VileSpawn;
import staticEntity.BurntTree;
import staticEntity.InfectedTree;
import staticEntity.PineTree;
import staticEntity.RockObstacle;
import staticEntity.StaticEntity;
import tiles.Tile;

/*
 * this class stores all the entities in the game and manages them
 */
public class EntityManager {

	// an ArrayList consisting of all entities
	public ArrayList<Entity> entities;
	public ArrayList<Entity> entitiesInBound; //stores creatures and static entities in a 50x50 radius around the player
	//creatures outside this radius degenerate
	//dont need this cuz the only creatures that can exist are ones in the entitiesOnScreen ArrayList
	//public ArrayList<Entity> creatures;
	
	public static final int MAX_CREATURES = 50;
	public static final int MAX_MATERIALS = 50;
	public int numCreatures = 0;
	public int numMaterials = 0;

	private Player player;

	// sorts the order which entities are rendered
	private Comparator<Entity> renderSorter = new Comparator<Entity>() {
		
		@Override
		public int compare(Entity a, Entity b) {
			// render the ones that have the higher y coordinates, compare entities
			
			if (a.getBounds().getY() < b.getBounds().getY()) {
				return -1;
			}
			
			return 1;
			
		}

	};

	// constructor of the EntityManager initializes the entities ArrayList
	public EntityManager(Player player) {
		
		this.player = player;
		
		entities = new ArrayList<Entity>();
		//creatures = new ArrayList<Entity>();
		entitiesInBound = new ArrayList<Entity>();
		addEntity(player);
		
	}

	public void tick() {
		
		for (int i = 0; i < entities.size(); i++) {
			if(entities.get(i).getBounds().intersects(Player.getPlayerData().entityBound())) {
				
				if(!entitiesInBound.contains(entities.get(i))) {
					entitiesInBound.add(entities.get(i));
					//System.out.println(entitiesInBound.size());
				}
				
				entities.get(i).tick();
				
			}
			
			if(i >= entities.size())
				break;
			
			if(!entities.get(i).isActive()) {
				
				//update topper===================
				if(entities.get(i) instanceof StaticEntity) {
					StaticEntity e = (StaticEntity) entities.get(i);
					
					int eTileX = (int) (e.getX() -
							e.getX() % Tile.TILEWIDTH) / Tile.TILEWIDTH;
					int eTileY = (int) (e.getY() -
							e.getY() % Tile.TILEHEIGHT) / Tile.TILEHEIGHT;
					
					//SPECIAL CASES MUST BE ADDED FOR ENTITIES THAT ARE TALLER THAN 1 TILE
					
					//System.out.println("x : " + e.getX());
					//System.out.println("y : " + e.getY());
					
					//System.out.println("camera x offset: " + player.getControlCenter().getGameCamera().getxOffset());
					//System.out.println("camera y offset: " + player.getControlCenter().getGameCamera().getyOffset());

					//System.out.println("topper updated x: " + eTileX + " y: " + eTileY);
					player.getControlCenter().getGameState().getWorldGenerator().getTopper()[eTileX][eTileY] = 0;
				}
				//===============================
				
				entities.remove(entities.get(i));
			}
		}
		
		for(int i = 0; i < entitiesInBound.size(); i++) {
			
			if(i >= entitiesInBound.size())
				break;
			
			if(entitiesInBound.get(i).getType().equals("creatures") && entitiesInBound.get(i) != player)
				entitiesInBound.get(i).tick();
			
			if(!entitiesInBound.get(i).isActive()) {
				if(entitiesInBound.get(i).getType().equals("creatures"))
					numCreatures--;
				
				if(entitiesInBound.get(i).getType().equals("material"))
					numMaterials--;
				
				//update topper==================
				if(entitiesInBound.get(i) instanceof StaticEntity) {
					StaticEntity e = (StaticEntity) entitiesInBound.get(i);
					
					int eTileX = (int) (e.getX() -
							e.getX() % Tile.TILEWIDTH) / Tile.TILEWIDTH;
					int eTileY = (int) (e.getY() -
							e.getY() % Tile.TILEHEIGHT) / Tile.TILEHEIGHT;
					
					if(e instanceof BurntTree || e instanceof InfectedTree || e instanceof PineTree) {
						eTileX += 1;
						eTileY += 3;
					} else if(e instanceof RockObstacle) {
						eTileX += 1;
						eTileY += 2;
					}
					
					//System.out.println("x : " + e.getX());
					//System.out.println("y : " + e.getY());
					
					//SPECIAL CASES MUST BE ADDED FOR ENTITIES THAT ARE TALLER THAN 1 TILE
					
					//System.out.println("camera x offset: " + player.getControlCenter().getGameCamera().getxOffset());
					//System.out.println("camera y offset: " + player.getControlCenter().getGameCamera().getyOffset());

					//System.out.println("topper updated x: " + eTileX + " y: " + eTileY);
					player.getControlCenter().getGameState().getWorldGenerator().getTopper()[eTileX][eTileY] = 0;
				}
				//=============================
				
				entitiesInBound.remove(entitiesInBound.get(i));
				i--;
			} else if(!entitiesInBound.get(i).getBounds().intersects(Player.getPlayerData().entityBound()) && !entitiesInBound.get(i).getType().equals("timed crafting structure")) {
				if(entitiesInBound.get(i).getType().equals("creatures"))
					numCreatures--;
				
				if(entitiesInBound.get(i).getType().equals("material"))
					numMaterials--;
				
				entitiesInBound.remove(entitiesInBound.get(i));
				i--;
			}
		}
		 
		Collections.sort(entitiesInBound, renderSorter);  
		
	}
	
	public void render(Graphics g) {


		for(int i = 0; i < entitiesInBound.size(); i++) {
			if(entitiesInBound.get(i).getBounds().intersects(Player.getPlayerData().renderBound()))
				entitiesInBound.get(i).render(g);
		}

	}

	public void addCreature(Entity e) {
		numCreatures++;
		entitiesInBound.add(e);
	}
	
	public void removeCreature(Entity e) {
		entitiesInBound.remove(e);
	}
	
	public void addEntity(Entity e) {
		if(e.getType().equals("creatures") && !(e instanceof Player) && !(e instanceof VileSpawn) && 
				!(e instanceof SentryBroodMother) && !(e instanceof AwakenedSentinel) && !(e instanceof SleepingSentinel)) {
			numCreatures++;
			entitiesInBound.add(e);
		} else if(e.getType().equals("material")) {
			numMaterials++;
			entitiesInBound.add(e);
		} else {
			entities.add(e);
		}
    }
	
	public void removeEntity(Entity e) {
		entities.remove(e);
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}
	
	public ArrayList<Entity> getEntitiesInBound() {
		return entitiesInBound;
	}
	
	//public ArrayList<Entity> getCreatures() {
		//return creatures;
	//}

	public void setEntities(ArrayList<Entity> entities) {
		this.entities = entities;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getNumCreatures() {
		return numCreatures;
	}

	public void setNumCreatures(int numCreatures) {
		this.numCreatures = numCreatures;
	}
	
}
