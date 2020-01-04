package world;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map.Entry;

import alphaPackage.ControlCenter;
import creatures.AwakenedSentinel;
import creatures.Creatures;
import creatures.Player;
import creatures.SentryBroodMother;
import creatures.SleepingSentinel;
import creatures.VileSpawn;
import entity.Entity;
import entity.EntityManager;
import inventory.Recipe;
import items.Armor;
import items.Food;
import items.Item;
import items.Ranged;
import items.Tool;
import items.Torch;
import items.WaterContainer;
import items.Weapon;
import structureInventory.SmithingTableRecipe;
import structureInventory.WorkbenchRecipe;
import structures.AutoCooker;
import structures.Chest;
import structures.Disintegrator;
import structures.MetalChest;
import structures.MetalContainer;
import structures.MetallicOven;
import structures.WoodenCrate;
import tiles.Tile;

public class WorldSaver {
	
	private ControlCenter c;
	
	public WorldSaver(ControlCenter c) {
		this.c = c;
		
	}
	
	//This method saves the world by calling all other save methods
	public void saveWorld() {
		
		saveTopper();
		saveCreatures();
		savePlayer();
		savePlatforms();
		saveChests();
		saveTimedCraftingStructures();
		saveRecipes();
		saveLightMap();
		savePowerMap();
		
	}
	
	//This method saves the world after a player has died
	public void saveWorldAfterDeath() {
		
		saveTopper();
		saveCreatures();
		savePlayerAfterDeath();
		savePlatforms();
		saveChests();
		saveTimedCraftingStructures();
		saveRecipes();
		saveLightMap();
		savePowerMap();
		
	}
	
	//This method saves the topper
	public void saveTopper() {
		
		String file = String.format("topper/%s", c.getMenuState().getWorldSelectState().getSelectedWorldName());
		File filepath = new File(file);
		
		if(filepath.exists() && !filepath.isDirectory()) {
			
			try {
				
				int width =	c.getGameState().getWorldGenerator().getWidth();
				int height = c.getGameState().getWorldGenerator().getHeight();
				
				int[][] topper = c.getGameState().getWorldGenerator().getTopper();
				
				PrintWriter pr = new PrintWriter(file);
				
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						pr.print(topper[x][y] + " ");
					}
					pr.println();
				}
				
				pr.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	//This method saves positions of all creatures except for player
	public void saveCreatures() { 
		
		//numCreatures
		//
		//creature name, creatureX, creatureY
		
		EntityManager entityManager = c.getGameState().getWorldGenerator().getEntityManager();
		
		ArrayList<Entity> creatures = new ArrayList<Entity>(); //change type to creature arraylist
		
		//Add creatures in entities in bound list to array list
		for(int i = 0; i < entityManager.getEntitiesInBound().size(); i++) {
			if(entityManager.getEntitiesInBound().get(i) instanceof Creatures 
					&& !(entityManager.getEntitiesInBound().get(i) instanceof Player)) {
				
				creatures.add(entityManager.getEntitiesInBound().get(i));
				
			}
		}
		
		//add bosses in entities to array list
		for(int i = 0; i < entityManager.getEntities().size(); i++) {
			
			Entity e = entityManager.getEntities().get(i);
			
			if(e instanceof VileSpawn || e instanceof SentryBroodMother || e instanceof AwakenedSentinel || e instanceof SleepingSentinel) {
				
				creatures.add(entityManager.getEntities().get(i));
				
			}
		}
		
		//Start writing creature data to a file
		String file = String.format("creatures/%s", c.getMenuState().getWorldSelectState().getSelectedWorldName());
		//File filepath = new File(file);
		
		try {
			
			int numCreatures =	creatures.size();
			PrintWriter pr = new PrintWriter(file);
			
			pr.print(numCreatures); //print number of creatures on first line of file
			pr.println();
			
			for (int i = 0; i < numCreatures; i++) {
				
				//print creature name, x, and y
				pr.print(String.format("%s %d %d", creatures.get(i).getName(), (int) creatures.get(i).getX(), (int) creatures.get(i).getY()));
				pr.println();
				
			}
			
			pr.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	//This method saves the player's position, inventory, hands, armor, vitals, and the dayNum
	public void savePlayer() {
		
		//gameMode (normal/harcore) worldSize gameCompleted(0 = not finished, 1 = player beat game)
		//player stats and vitals
		//dayNum and time
		//
		//playerX playerY
		//
		//basic survival xp
		//combat xp
		//cooking xp
		//building xp
		//
		//armor item list size
		//constructor values
		//
		//food item list size
		//constructor values
		
		String worldDataFile = WorldInput.loadFileAsString(String.format("worldData/%s", c.getMenuState().getWorldSelectState().getSelectedWorldName()));
		String[] tokens = worldDataFile.split("\\s+"); // split up every number into the string tokens array

		int gameMode =  WorldInput.parseInt(tokens[0]);
		int worldSize = WorldInput.parseInt(tokens[1]);
		int gameCompleted = 0;
		
		if(c.getGameState().getWorldGenerator().isGameCompleted()) {
			gameCompleted = 1;
		}
		
		
		String file = String.format("worldData/%s", c.getMenuState().getWorldSelectState().getSelectedWorldName());
		File filepath = new File(file);
		
		if(filepath.exists() && !filepath.isDirectory()) {
			
			try {
				
				PrintWriter pr = new PrintWriter(file);
				
				//save player stats
				pr.print(gameMode + " " + worldSize + " " + gameCompleted);
				pr.println();
				pr.print(Player.getPlayerData().getName() + " " + Player.getPlayerData().getHealth() + " " + 
						Player.getPlayerData().getRunSpeed() + " " + Player.getPlayerData().getEndurability() + " " + 
						Player.getPlayerData().getDamage() + " " + Player.getPlayerData().getIntimidation() + " " + 
						Player.getPlayerData().getIntelligence() + " " + Player.getPlayerData().getResistance() + " " + 
						Player.getPlayerData().getHunger() + " " + Player.getPlayerData().getThirst() + " " + 
						Player.getPlayerData().getEnergy());
				pr.println();
				pr.print(WorldGenerator.dayNum + " " + WorldGenerator.time);
				pr.println();
				pr.println();
				
				//Save player position
				int x = (int) Player.getPlayerData().getX();
				int y = (int) Player.getPlayerData().getY();
				pr.print(x + " " + y);
				pr.println();
				pr.println();
				
				//Save player xp values
				pr.println(Player.getPlayerData().getBasicSurvivalXP());
				pr.println(Player.getPlayerData().getCombatXP());
				pr.println(Player.getPlayerData().getCookingXP());
				pr.println(Player.getPlayerData().getBuildingXP());
				pr.println();
				
				//Save inventory items
				ArrayList<Item> itemList = new ArrayList<Item>(); //list of items that don't fall into a sub category
				ArrayList<Armor> armorList = new ArrayList<Armor>();
				ArrayList<Food> foodList = new ArrayList<Food>();
				ArrayList<Ranged> rangedList = new ArrayList<Ranged>();
				ArrayList<Tool> toolList = new ArrayList<Tool>();
				ArrayList<Torch> torchList = new ArrayList<Torch>();
				ArrayList<WaterContainer> waterContainerList = new ArrayList<WaterContainer>();
				ArrayList<Weapon> weaponList = new ArrayList<Weapon>();
				
				ArrayList<Item> inventoryItems = Player.getPlayerData().getInventory().InventoryItems;
				
				for(int i = 0; i < Player.getPlayerData().getInventory().InventoryItems.size(); i++) {
					
					if(inventoryItems.get(i) instanceof Armor) {
						armorList.add((Armor) inventoryItems.get(i));
					} else if(inventoryItems.get(i) instanceof Food) {
						foodList.add((Food) inventoryItems.get(i));
					} else if(inventoryItems.get(i) instanceof Ranged) {
						rangedList.add((Ranged) inventoryItems.get(i));
					} else if(inventoryItems.get(i) instanceof Tool) {
						toolList.add((Tool) inventoryItems.get(i));
					} else if(inventoryItems.get(i) instanceof Torch) {
						torchList.add((Torch) inventoryItems.get(i));
					} else if(inventoryItems.get(i) instanceof WaterContainer) {
						waterContainerList.add((WaterContainer) inventoryItems.get(i));
					} else if(inventoryItems.get(i) instanceof Weapon) {
						weaponList.add((Weapon) inventoryItems.get(i));
					} else {
						itemList.add(inventoryItems.get(i));
					}
					
				}
				
				
				//Write the inventory items
				//NOTE: item textures are the only constructor variables that are not saved,
				//however textures can be accessed using Item.items[id].getTexture()
				//string data such as names can be stored because it may be multiple words
				pr.print(armorList.size());
				pr.println();
				for(Armor e : armorList) {
					pr.print(e.getId() + " " + e.getWeight() + " " + e.getDamage() + " " + e.getVolume() + " " + e.intimidation + " " + 
							e.resistance + " " + e.healthRegen + " " + e.currentEndurance + " " + e.endurance); 
					pr.println();
				}
				pr.println();
				
				pr.print(foodList.size());
				pr.println();
				for(Food e : foodList) {
					pr.print(e.getId() + " " + e.getWeight() + " " + e.getDamage() + " " + 
							e.getVolume() + " " + e.hunger + " " + e.thirst + " " + 
							+ e.freshness); 
					pr.println();
				}
				pr.println();
				
				pr.print(rangedList.size());
				pr.println();
				for(Ranged e : rangedList) {
					pr.print(e.getId() + " " + e.getWeight() + " " + 
							e.getDamage() + " " + e.aSpeed + " " + e.intimidation + " " + e.ammoMax + " " + 
							e.reloadCooldown + " " + e.accuracy + " " + e.getVolume() + " " + 
							e.ammoCurrent); //ammo current isnt used in constructor
					pr.println();
				}
				pr.println();
				
				pr.print(toolList.size());
				pr.println();
				for(Tool e : toolList) {
					pr.print(e.getId() + " " + 
							e.getWeight() + " " + e.getDamage() + " " + e.getaSpeed() + " " + e.getIntimidation() + " " + 
							e.getRange() + " " + e.getVolume() + " " + e.getPower() + " " + e.getCurrentEndurance() +  " " + 
							e.getEndurance()); 
					pr.println();
				}
				pr.println();
				
				pr.print(torchList.size()); //nothing in torch constructor
				pr.println();
				pr.println();

				pr.print(waterContainerList.size());
				pr.println();
				for(WaterContainer e : waterContainerList) {
					pr.print(e.getId() + " " + e.getWeight() + " " + 
							e.getVolume() + " " + e.getCurrentCapacity() +  " " + e.getCapacity()); 
					pr.println();
				}
				pr.println();
				
				pr.print(weaponList.size());
				pr.println();
				for(Weapon e : weaponList) {
					pr.print(e.getId() + " " + e.getWeight() + " " + 
							e.getDamage() + " " + e.getaSpeed() + " " + e.getIntimidation() + " " + 
							e.getRange() + " " + e.getVolume() + " " + e.getCurrentEndurance() +  " " + 
							e.getEndurance()); 
					pr.println();
				}
				pr.println();
				
				pr.print(itemList.size());
				pr.println();
				for(Item e : itemList) {
					pr.print(e.getId() + " " + e.getWeight() + " " + e.getDamage() + " " + e.getVolume() + " " + e.getCount()); 
					pr.println();
				}
				pr.println();
				
				//Save hand items
				Item rightHand = Player.getPlayerData().getHands().rightHand;
				Item leftHand = Player.getPlayerData().getHands().leftHand;
				
				if(leftHand instanceof Armor) {
					pr.print(1);
					pr.println();
					Armor e = (Armor) leftHand;
					pr.print(e.getId() + " " + e.getWeight() + " " + e.getDamage() + " " + e.getVolume() + " " + e.intimidation + " " + 
							e.resistance + " " + e.healthRegen + " " + e.currentEndurance + " " + e.endurance); 
					pr.println();
				} else if(leftHand instanceof Food) {
					pr.print(2);
					pr.println();
					Food e = (Food) leftHand;
					pr.print(e.getId() + " " + e.getWeight() + " " + e.getDamage() + " " + 
							e.getVolume() + " " + e.hunger + " " + e.thirst + " " + 
							+ e.freshness); 
					pr.println();
				} else if(leftHand instanceof Ranged) {
					pr.print(3);
					pr.println();
					Ranged e = (Ranged) leftHand;
					pr.print(e.getId() + " " + e.getWeight() + " " + 
							e.getDamage() + " " + e.aSpeed + " " + e.intimidation + " " + e.ammoMax + " " + 
							e.reloadCooldown + " " + e.accuracy + " " + e.getVolume() + " " + 
							e.ammoCurrent); //ammo current isnt used in constructor
					pr.println();
				} else if(leftHand instanceof Tool) {
					pr.print(4);
					pr.println();
					Tool e = (Tool) leftHand;
					pr.print(e.getId() + " " + 
							e.getWeight() + " " + e.getDamage() + " " + e.getaSpeed() + " " + e.getIntimidation() + " " + 
							e.getRange() + " " + e.getVolume() + " " + e.getPower() + " " + e.getCurrentEndurance() +  " " + 
							e.getEndurance()); 
					pr.println();
				} else if(leftHand instanceof Torch) {
					pr.print(5);
					pr.println();
				} else if(leftHand instanceof WaterContainer) {
					pr.print(6);
					pr.println();
					WaterContainer e = (WaterContainer) leftHand;
					pr.print(e.getId() + " " + e.getWeight() + " " + 
							e.getVolume() + " " + e.getCurrentCapacity() +  " " + e.getCapacity()); 
					pr.println();
				} else if(leftHand instanceof Weapon) {
					pr.print(7);
					pr.println();
					Weapon e = (Weapon) leftHand;
					pr.print(e.getId() + " " + e.getWeight() + " " + 
							e.getDamage() + " " + e.getaSpeed() + " " + e.getIntimidation() + " " + 
							e.getRange() + " " + e.getVolume() + " " + e.getCurrentEndurance() +  " " + 
							e.getEndurance()); 
					pr.println();
				} else if(leftHand != null){
					pr.print(8);
					pr.println();
					Item e = leftHand;
					pr.print(e.getId() + " " + e.getWeight() + " " + e.getDamage() + " " + e.getVolume() + " " + e.getCount()); 
					pr.println();
				} else { //no item
					pr.print(0);
					pr.println();
				}
				pr.println();
				
				//save right hand
				if(rightHand instanceof Armor) {
					pr.print(1);
					pr.println();
					Armor e = (Armor) rightHand;
					pr.print(e.getId() + " " + e.getWeight() + " " + e.getDamage() + " " + e.getVolume() + " " + e.intimidation + " " + 
							e.resistance + " " + e.healthRegen + " " + e.currentEndurance + " " + e.endurance); 
					pr.println();
				} else if(rightHand instanceof Food) {
					pr.print(2);
					pr.println();
					Food e = (Food) rightHand;
					pr.print(e.getId() + " " + e.getWeight() + " " + e.getDamage() + " " + 
							e.getVolume() + " " + e.hunger + " " + e.thirst + " " + 
							+ e.freshness); 
					pr.println();
				} else if(rightHand instanceof Ranged) {
					pr.print(3);
					pr.println();
					Ranged e = (Ranged) rightHand;
					pr.print(e.getId() + " " + e.getWeight() + " " + 
							e.getDamage() + " " + e.aSpeed + " " + e.intimidation + " " + e.ammoMax + " " + 
							e.reloadCooldown + " " + e.accuracy + " " + e.getVolume() + " " + 
							e.ammoCurrent); //ammo current isnt used in constructor
					pr.println();
				} else if(rightHand instanceof Tool) {
					pr.print(4);
					pr.println();
					Tool e = (Tool) rightHand;
					pr.print(e.getId() + " " + 
							e.getWeight() + " " + e.getDamage() + " " + e.getaSpeed() + " " + e.getIntimidation() + " " + 
							e.getRange() + " " + e.getVolume() + " " + e.getPower() + " " + e.getCurrentEndurance() +  " " + 
							e.getEndurance()); 
					pr.println();
				} else if(rightHand instanceof Torch) {
					pr.print(5);
					pr.println();
				} else if(rightHand instanceof WaterContainer) {
					pr.print(6);
					pr.println();
					WaterContainer e = (WaterContainer) rightHand;
					pr.print(e.getId() + " " + e.getWeight() + " " + 
							e.getVolume() + " " + e.getCurrentCapacity() +  " " + e.getCapacity()); 
					pr.println();
				} else if(rightHand instanceof Weapon) {
					pr.print(7);
					pr.println();
					Weapon e = (Weapon) rightHand;
					pr.print(e.getId() + " " + e.getWeight() + " " + 
							e.getDamage() + " " + e.getaSpeed() + " " + e.getIntimidation() + " " + 
							e.getRange() + " " + e.getVolume() + " " + e.getCurrentEndurance() +  " " + 
							e.getEndurance()); 
					pr.println();
				} else if(rightHand != null){
					pr.print(8);
					pr.println();
					Item e = rightHand;
					pr.print(e.getId() + " " + e.getWeight() + " " + e.getDamage() + " " + e.getVolume() + " " + e.getCount()); 
					pr.println();
				} else { //no item
					pr.print(0);
					pr.println();
				}
				pr.println();
				
				//Save armor
				Armor helmet = Player.getPlayerData().getEquipment().helmet;
				Armor chest = Player.getPlayerData().getEquipment().chest;
				Armor leg = Player.getPlayerData().getEquipment().leg;
				Armor boot = Player.getPlayerData().getEquipment().boot;
				Armor gauntlet = Player.getPlayerData().getEquipment().gauntlet;
				
				if(helmet != null) {
					pr.print(1);
					pr.println();
					Armor e = (Armor) helmet;
					pr.print(e.getId() + " " + e.getWeight() + " " + e.getDamage() + " " + e.getVolume() + " " + e.intimidation + " " + 
							e.resistance + " " + e.healthRegen + " " + e.currentEndurance + " " + e.endurance); 
					pr.println();
				} else {
					pr.print(0);
					pr.println();
				}
				
				if(chest != null) {
					pr.print(1);
					pr.println();
					Armor e = (Armor) chest;
					pr.print(e.getId() + " " + e.getWeight() + " " + e.getDamage() + " " + e.getVolume() + " " + e.intimidation + " " + 
							e.resistance + " " + e.healthRegen + " " + e.currentEndurance + " " + e.endurance); 
					pr.println();
				} else {
					pr.print(0);
					pr.println();
				}
				
				if(leg != null) {
					pr.print(1);
					pr.println();
					Armor e = (Armor) leg;
					pr.print(e.getId() + " " + e.getWeight() + " " + e.getDamage() + " " + e.getVolume() + " " + e.intimidation + " " + 
							e.resistance + " " + e.healthRegen + " " + e.currentEndurance + " " + e.endurance); 
					pr.println();
				} else {
					pr.print(0);
					pr.println();
				}
				
				if(boot != null) {
					pr.print(1);
					pr.println();
					Armor e = (Armor) boot;
					pr.print(e.getId() + " " + e.getWeight() + " " + e.getDamage() + " " + e.getVolume() + " " + e.intimidation + " " + 
							e.resistance + " " + e.healthRegen + " " + e.currentEndurance + " " + e.endurance); 
					pr.println();
				} else {
					pr.print(0);
					pr.println();
				}
				
				if(gauntlet != null) {
					pr.print(1);
					pr.println();
					Armor e = (Armor) gauntlet;
					pr.print(e.getId() + " " + e.getWeight() + " " + e.getDamage() + " " + e.getVolume() + " " + e.intimidation + " " + 
							e.resistance + " " + e.healthRegen + " " + e.currentEndurance + " " + e.endurance); 
					pr.println();
				} else {
					pr.print(0);
					pr.println();
				}
				
				pr.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	//This method saves the floor array to a file
	public void savePlatforms() {
		
		String file = String.format("platform/%s", c.getMenuState().getWorldSelectState().getSelectedWorldName());
		//File filepath = new File(file);
		
		//you can uncomment this once a platform writer is implemented
		//if(filepath.exists() && !filepath.isDirectory()) { 
			
			try {
				
				int width =	c.getGameState().getWorldGenerator().getWidth();
				int height = c.getGameState().getWorldGenerator().getHeight();
				
				int[][] platforms = c.getGameState().getWorldGenerator().getFloor();
				
				PrintWriter pr = new PrintWriter(file);
				
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						pr.print(platforms[x][y] + " ");
					}
					pr.println();
				}
				
				pr.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		//}
		
	}
	
	//This method saves the chests
	public void saveChests() {
		
		//numChests
		//
		//topperX topperY
		//numItems chestType
		//num of a specific item
		//type of item (ex. armor, food)
		//constructor values
		
		EntityManager entityManager = c.getGameState().getWorldGenerator().getEntityManager();
		
		ArrayList<Chest> chests = new ArrayList<Chest>(); //change type to creature arraylist
		
		//add chests in entities to array list
		for(int i = 0; i < entityManager.getEntities().size(); i++) {
			
			Entity e = entityManager.getEntities().get(i);
			
			if(e instanceof Chest) {
				chests.add((Chest) entityManager.getEntities().get(i));
			}
		}
		
		//Start writing chest data to a file
		String file = String.format("chests/%s", c.getMenuState().getWorldSelectState().getSelectedWorldName());
		//File filepath = new File(file);
		
		try {
			
			int numChests =	chests.size();
			PrintWriter pr = new PrintWriter(file);
			
			pr.print(numChests); //print number of chests on first line of file
			pr.println();
			pr.println(); //skip a line after number of chests
			
			for (int i = 0; i < numChests; i++) {
				
				//print topper x, and topper y
				pr.print(String.format("%d %d", (int) chests.get(i).getX() / Tile.TILEWIDTH, (int) chests.get(i).getY() / Tile.TILEHEIGHT));
				pr.println();
				
				int chestType = 1; //default wooden crate
				
				if(chests.get(i) instanceof WoodenCrate) {
					chestType = 1;
				} else if(chests.get(i) instanceof MetalContainer) {
					chestType = 2;
				} else if(chests.get(i) instanceof MetalChest) {
					chestType = 3;
				} 
				
				//print number of items
				pr.print(String.format("%d %d", chests.get(i).getInventory().InventoryItems.size(), chestType));
				pr.println();
				
				//print the number of each specific item, the type of item, and its constuctor values
				for (Entry<Item, Integer> entry : chests.get(i).getInventory().InventoryItems.entrySet()) {
					//print the number of each specific item
					pr.print(entry.getValue());
					pr.println();
					
					//print the type of item and its constuctor values
					if(entry.getKey() instanceof Armor) {
						pr.print(1);
						pr.println();
						Armor e = (Armor) entry.getKey();
						pr.print(e.getId() + " " + e.getWeight() + " " + e.getDamage() + " " + e.getVolume() + " " + e.intimidation + " " + 
								e.resistance + " " + e.healthRegen + " " + e.currentEndurance + " " + e.endurance); 
					} else if(entry.getKey() instanceof Food) {
						pr.print(2);
						pr.println();
						Food e = (Food) entry.getKey();
						pr.print(e.getId() + " " + e.getWeight() + " " + e.getDamage() + " " + 
								e.getVolume() + " " + e.hunger + " " + e.thirst + " " + 
								+ e.freshness); 
					} else if(entry.getKey() instanceof Ranged) {
						pr.print(3);
						pr.println();
						Ranged e = (Ranged) entry.getKey();
						pr.print(e.getId() + " " + e.getWeight() + " " + 
								e.getDamage() + " " + e.aSpeed + " " + e.intimidation + " " + e.ammoMax + " " + 
								e.reloadCooldown + " " + e.accuracy + " " + e.getVolume() + " " + 
								e.ammoCurrent); //ammo current isnt used in constructor
					} else if(entry.getKey() instanceof Tool) {
						pr.print(4);
						pr.println();
						Tool e = (Tool) entry.getKey();
						pr.print(e.getId() + " " + 
								e.getWeight() + " " + e.getDamage() + " " + e.getaSpeed() + " " + e.getIntimidation() + " " + 
								e.getRange() + " " + e.getVolume() + " " + e.getPower() + " " + e.getCurrentEndurance() +  " " + 
								e.getEndurance()); 
					} else if(entry.getKey() instanceof Torch) {
						pr.print(5);
						pr.println();
					} else if(entry.getKey() instanceof WaterContainer) {
						pr.print(6);
						pr.println();
						WaterContainer e = (WaterContainer) entry.getKey();
						pr.print(e.getId() + " " + e.getWeight() + " " + 
								e.getVolume() + " " + e.getCurrentCapacity() +  " " + e.getCapacity()); 
					} else if(entry.getKey() instanceof Weapon) {
						pr.print(7);
						pr.println();
						Weapon e = (Weapon) entry.getKey();
						pr.print(e.getId() + " " + e.getWeight() + " " + 
								e.getDamage() + " " + e.getaSpeed() + " " + e.getIntimidation() + " " + 
								e.getRange() + " " + e.getVolume() + " " + e.getCurrentEndurance() +  " " + 
								e.getEndurance()); 
					} else if(entry.getKey() != null){
						pr.print(8);
						pr.println();
						Item e = entry.getKey();
						pr.print(e.getId() + " " + e.getWeight() + " " + e.getDamage() + " " + e.getVolume()); //COUNT IS NOT TRACKED FOR CHEST ITEMS
					}
					pr.println();
					
				}
				
				pr.println(); //skip a line after each chest
				
			}
			
			pr.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	
		
	}
	
	//This method saves the timed crafting structures (metallic oven, auto cooker, and disintegrator)
	public void saveTimedCraftingStructures() {
		
		//total number of timed crafting structures
		//
		//topperX topperY
		//structureNum (105 = metallic oven, 106 = autocooker, 107 = disintegrator)
		//state of structure (0 = nothing inside, 1 = currently cooking (item in currentlySmelting map), 2 = finished cooking (item in products map)
		//itemId itemAmount
		
		EntityManager entityManager = c.getGameState().getWorldGenerator().getEntityManager();
		
		ArrayList<MetallicOven> metallicOvens = new ArrayList<MetallicOven>();
		ArrayList<AutoCooker> autoCookers = new ArrayList<AutoCooker>();
		ArrayList<Disintegrator> disintegrators = new ArrayList<Disintegrator>();
		
		//add chests in entities to array list
		for(int i = 0; i < entityManager.getEntities().size(); i++) {
			
			Entity e = entityManager.getEntities().get(i);
			
			if(e instanceof MetallicOven) {
				metallicOvens.add((MetallicOven) entityManager.getEntities().get(i));
			} else if(e instanceof AutoCooker) {
				autoCookers.add((AutoCooker) entityManager.getEntities().get(i));
			} else if(e instanceof Disintegrator) {
				disintegrators.add((Disintegrator) entityManager.getEntities().get(i));
			} 
		}
		
		//Start writing chest data to a file
		String file = String.format("timedCraftingStructures/%s", c.getMenuState().getWorldSelectState().getSelectedWorldName());
		//File filepath = new File(file);
		
		try {
			
			//total number of timed crafting structures
			int numTimedCraftingStructures = metallicOvens.size() + autoCookers.size() + disintegrators.size(); 
			PrintWriter pr = new PrintWriter(file);
			
			pr.print(numTimedCraftingStructures); //print number of timed crafting structures on first line of file
			pr.println();
			pr.println(); //skip a line after number of timed crafting structures
			
			//print all the metallic ovens
			for (int i = 0; i < metallicOvens.size(); i++) {
				
				//print topper x, and topper y
				pr.print(String.format("%d %d", (int) metallicOvens.get(i).getX() / Tile.TILEWIDTH, (int) metallicOvens.get(i).getY() / Tile.TILEHEIGHT));
				pr.println();
				
				//print the structure number
				pr.print(105);
				pr.println();
				
				//print the state of the structure
				if(metallicOvens.get(i).getInventory().currentlySmelting.isEmpty() && metallicOvens.get(i).getInventory().products.isEmpty()) {
					
					pr.print(0);
					pr.println();
					
				} else if(!metallicOvens.get(i).getInventory().currentlySmelting.isEmpty()) {
					
					pr.print(1);
					pr.println();
					
					for (Entry<Integer, Integer> entry : metallicOvens.get(i).getInventory().currentlySmelting.entrySet()) {
						
						pr.print(entry.getKey() + " " + entry.getValue());
						pr.println();
						break;
						
					}
					
				} else if(!metallicOvens.get(i).getInventory().products.isEmpty()) {
					
					pr.print(2);
					pr.println();
					
					for (Entry<Integer, Integer> entry : metallicOvens.get(i).getInventory().products.entrySet()) {
						
						pr.print(entry.getKey() + " " + entry.getValue());
						pr.println();
						break;
						
					}
					
				}
				
				pr.println(); //skip a line after each metallic oven
				
			}
			
			//print all the auto cookers
			for (int i = 0; i < autoCookers.size(); i++) {
				
				//print topper x, and topper y
				pr.print(String.format("%d %d", (int) autoCookers.get(i).getX() / Tile.TILEWIDTH, (int) autoCookers.get(i).getY() / Tile.TILEHEIGHT));
				pr.println();
				
				//print the structure number
				pr.print(106);
				pr.println();
				
				//print the state of the structure
				if(autoCookers.get(i).getCraft().currentlyCooking.isEmpty() && autoCookers.get(i).getCraft().products.isEmpty()) {
					
					pr.print(0);
					pr.println();
					
				} else if(!autoCookers.get(i).getCraft().currentlyCooking.isEmpty()) {
					
					pr.print(1);
					pr.println();
					
					for (Entry<Integer, Integer> entry : autoCookers.get(i).getCraft().currentlyCooking.entrySet()) {
						
						pr.print(entry.getKey() + " " + entry.getValue());
						pr.println();
						break;
						
					}
					
				} else if(!autoCookers.get(i).getCraft().products.isEmpty()) {
					
					pr.print(2);
					pr.println();
					
					for (Entry<Integer, Integer> entry : autoCookers.get(i).getCraft().products.entrySet()) {
						
						pr.print(entry.getKey() + " " + entry.getValue());
						pr.println();
						break;
						
					}
					
				}
				
				pr.println(); //skip a line after each auto cooker
				
			}
			
			//print all the disintegrators
			for (int i = 0; i < disintegrators.size(); i++) {
				
				//print topper x, and topper y
				pr.print(String.format("%d %d", (int) disintegrators.get(i).getX() / Tile.TILEWIDTH, (int) disintegrators.get(i).getY() / Tile.TILEHEIGHT));
				pr.println();
				
				//print the structure number
				pr.print(107);
				pr.println();
				
				//print the state of the structure
				if(disintegrators.get(i).getCraft().currentlySmelting.isEmpty() && disintegrators.get(i).getCraft().products.isEmpty()) {
					
					pr.print(0);
					pr.println();
					
				} else if(!disintegrators.get(i).getCraft().currentlySmelting.isEmpty()) {
					
					pr.print(1);
					pr.println();
					
					for (Entry<Integer, Integer> entry : disintegrators.get(i).getCraft().currentlySmelting.entrySet()) {
						
						pr.print(entry.getKey() + " " + entry.getValue());
						pr.println();
						break;
						
					}
					
				} else if(!disintegrators.get(i).getCraft().products.isEmpty()) {
					
					pr.print(2);
					pr.println();
					
					for (Entry<Integer, Integer> entry : disintegrators.get(i).getCraft().products.entrySet()) {
						
						pr.print(entry.getKey() + " " + entry.getValue());
						pr.println();
						break;
						
					}
					
				}
				
				pr.println(); //skip a line after each disintegrator
				
			}
			
			pr.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	public void saveRecipes() {
		
		//total number of timed crafting structures
		//
		//recipeId recipeType (if recipeType == 0 Recipe; if recipeType == 1 WorkbenchRecipe; if recipeType == 2 SmithingTableRecipe)
		
		ArrayList<Recipe> recipes = Recipe.unlockedRecipes;
		
		//Start writing chest data to a file
		String file = String.format("recipe/%s", c.getMenuState().getWorldSelectState().getSelectedWorldName());
		//File filepath = new File(file);
		
		try {
			
			//total number of timed crafting structures
			int numRecipes = Recipe.unlockedRecipes.size(); 
			PrintWriter pr = new PrintWriter(file);
			
			pr.print(numRecipes); //print number of recipes on first line of file
			pr.println();
			pr.println(); //skip a line after number of timed crafting structures
			
			for(int i = 0; i < numRecipes; i++) {
				
				pr.print(recipes.get(i).getId() + " ");
				
				if(Recipe.unlockedRecipes.get(i) instanceof WorkbenchRecipe) {
					pr.print(1);
				} else if(Recipe.unlockedRecipes.get(i) instanceof SmithingTableRecipe) {
					pr.print(2);
				} else {
					pr.print(0);
				}
				
				pr.println();
				
			}
			
			pr.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	//This method saves the player's position, inventory, hands, armor, vitals, and the dayNum after a death
	public void savePlayerAfterDeath() {
		
		//gameMode (normal/harcore) worldSize gameCompleted(0 = not finished, 1 = player beat game)
		//player stats and vitals
		//dayNum and time
		//
		//playerX playerY
		//
		//basic survival xp
		//combat xp
		//cooking xp
		//building xp
		//
		//armor item list size
		//constructor values
		//
		//food item list size
		//constructor values
		
		String worldDataFile = WorldInput.loadFileAsString(String.format("worldData/%s", c.getMenuState().getWorldSelectState().getSelectedWorldName()));
		String[] tokens = worldDataFile.split("\\s+"); // split up every number into the string tokens array

		int gameMode =  WorldInput.parseInt(tokens[0]);
		int worldSize = WorldInput.parseInt(tokens[1]);
		int gameCompleted = 0;
		
		if(c.getGameState().getWorldGenerator().isGameCompleted()) {
			gameCompleted = 1;
		}
		
		
		String file = String.format("worldData/%s", c.getMenuState().getWorldSelectState().getSelectedWorldName());
		File filepath = new File(file);
		
		if(filepath.exists() && !filepath.isDirectory()) {
			
			try {
				
				PrintWriter pr = new PrintWriter(file);
				
				//save player stats
				pr.print(gameMode + " " + worldSize + " " + gameCompleted);
				pr.println();
				pr.print(Player.getPlayerData().getName() + " " + c.getMenuState().getWorldSelectState().getSavedHealth() + " " + 
						Player.getPlayerData().getRunSpeed() + " " + c.getMenuState().getWorldSelectState().getSavedEndurability() + " " + 
						Player.getPlayerData().getDamage() + " " + Player.getPlayerData().getIntimidation() + " " + 
						Player.getPlayerData().getIntelligence() + " " + Player.getPlayerData().getResistance() + " " + 
						c.getMenuState().getWorldSelectState().getHunger() + " " + c.getMenuState().getWorldSelectState().getThirst() + " " + 
						c.getMenuState().getWorldSelectState().getEnergy());
				pr.println();
				pr.print(WorldGenerator.dayNum + " " + WorldGenerator.time);
				pr.println();
				pr.println();
				
				//Respawn at the location of your last save
//				int x = (int) Player.getPlayerData().getX();
//				int y = (int) Player.getPlayerData().getY();
				int x = c.getMenuState().getWorldSelectState().getPlayerX();
				int y = c.getMenuState().getWorldSelectState().getPlayerY();
				pr.println(x + " " + y);
				pr.println();
				
				//Save player xp values
				pr.println(Player.getPlayerData().getBasicSurvivalXP());
				pr.println(Player.getPlayerData().getCombatXP());
				pr.println(Player.getPlayerData().getCookingXP());
				pr.println(Player.getPlayerData().getBuildingXP());
				pr.println();
				
				//Save inventory items
//				ArrayList<Item> itemList = new ArrayList<Item>(); //list of items that don't fall into a sub category
//				ArrayList<Armor> armorList = new ArrayList<Armor>();
//				ArrayList<Food> foodList = new ArrayList<Food>();
//				ArrayList<Ranged> rangedList = new ArrayList<Ranged>();
//				ArrayList<Tool> toolList = new ArrayList<Tool>();
//				ArrayList<Torch> torchList = new ArrayList<Torch>();
//				ArrayList<WaterContainer> waterContainerList = new ArrayList<WaterContainer>();
//				ArrayList<Weapon> weaponList = new ArrayList<Weapon>();
//				
//				ArrayList<Item> inventoryItems = Player.getPlayerData().getInventory().InventoryItems;
//				
//				for(int i = 0; i < Player.getPlayerData().getInventory().InventoryItems.size(); i++) {
//					
//					if(inventoryItems.get(i) instanceof Armor) {
//						armorList.add((Armor) inventoryItems.get(i));
//					} else if(inventoryItems.get(i) instanceof Food) {
//						foodList.add((Food) inventoryItems.get(i));
//					} else if(inventoryItems.get(i) instanceof Ranged) {
//						rangedList.add((Ranged) inventoryItems.get(i));
//					} else if(inventoryItems.get(i) instanceof Tool) {
//						toolList.add((Tool) inventoryItems.get(i));
//					} else if(inventoryItems.get(i) instanceof Torch) {
//						torchList.add((Torch) inventoryItems.get(i));
//					} else if(inventoryItems.get(i) instanceof WaterContainer) {
//						waterContainerList.add((WaterContainer) inventoryItems.get(i));
//					} else if(inventoryItems.get(i) instanceof Weapon) {
//						weaponList.add((Weapon) inventoryItems.get(i));
//					} else {
//						itemList.add(inventoryItems.get(i));
//					}
//					
//				}
				
				
				//Write the inventory items
				//NOTE: item textures are the only constructor variables that are not saved,
				//however textures can be accessed using Item.items[id].getTexture()
				//string data such as names can be stored because it may be multiple words
				
//				pr.print(armorList.size());
//				pr.println();
//				for(Armor e : armorList) {
//					pr.print(e.getId() + " " + e.getWeight() + " " + e.getDamage() + " " + e.getVolume() + " " + e.intimidation + " " + 
//							e.resistance + " " + e.healthRegen + " " + e.currentEndurance + " " + e.endurance); 
//					pr.println();
//				}
				pr.println(0);
				
//				pr.print(foodList.size());
//				pr.println();
//				for(Food e : foodList) {
//					pr.print(e.getId() + " " + e.getWeight() + " " + e.getDamage() + " " + 
//							e.getVolume() + " " + e.hunger + " " + e.thirst + " " + 
//							+ e.freshness); 
//					pr.println();
//				}
				pr.println(0);
				
//				pr.print(rangedList.size());
//				pr.println();
//				for(Ranged e : rangedList) {
//					pr.print(e.getId() + " " + e.getWeight() + " " + 
//							e.getDamage() + " " + e.aSpeed + " " + e.intimidation + " " + e.ammoMax + " " + 
//							e.reloadCooldown + " " + e.accuracy + " " + e.getVolume() + " " + 
//							e.ammoCurrent); //ammo current isnt used in constructor
//					pr.println();
//				}
				pr.println(0);
				
//				pr.print(toolList.size());
//				pr.println();
//				for(Tool e : toolList) {
//					pr.print(e.getId() + " " + 
//							e.getWeight() + " " + e.getDamage() + " " + e.getaSpeed() + " " + e.getIntimidation() + " " + 
//							e.getRange() + " " + e.getVolume() + " " + e.getPower() + " " + e.getCurrentEndurance() +  " " + 
//							e.getEndurance()); 
//					pr.println();
//				}
				pr.println(0);
				
//				pr.print(torchList.size()); //nothing in torch constructor
//				pr.println();
				pr.println(0);

//				pr.print(waterContainerList.size());
//				pr.println();
//				for(WaterContainer e : waterContainerList) {
//					pr.print(e.getId() + " " + e.getWeight() + " " + 
//							e.getVolume() + " " + e.getCurrentCapacity() +  " " + e.getCapacity()); 
//					pr.println();
//				}
				pr.println(0);
				
//				pr.print(weaponList.size());
//				pr.println();
//				for(Weapon e : weaponList) {
//					pr.print(e.getId() + " " + e.getWeight() + " " + 
//							e.getDamage() + " " + e.getaSpeed() + " " + e.getIntimidation() + " " + 
//							e.getRange() + " " + e.getVolume() + " " + e.getCurrentEndurance() +  " " + 
//							e.getEndurance()); 
//					pr.println();
//				}
				pr.println(0);
				
//				pr.print(itemList.size());
//				pr.println();
//				for(Item e : itemList) {
//					pr.print(e.getId() + " " + e.getWeight() + " " + e.getDamage() + " " + e.getVolume() + " " + e.getCount()); 
//					pr.println();
//				}
				pr.println(0);
				
				//Save hand items
//				Item rightHand = Player.getPlayerData().getHands().rightHand;
//				Item leftHand = Player.getPlayerData().getHands().leftHand;
//				
//				if(leftHand instanceof Armor) {
//					pr.print(1);
//					pr.println();
//					Armor e = (Armor) leftHand;
//					pr.print(e.getId() + " " + e.getWeight() + " " + e.getDamage() + " " + e.getVolume() + " " + e.intimidation + " " + 
//							e.resistance + " " + e.healthRegen + " " + e.currentEndurance + " " + e.endurance); 
//					pr.println();
//				} else if(leftHand instanceof Food) {
//					pr.print(2);
//					pr.println();
//					Food e = (Food) leftHand;
//					pr.print(e.getId() + " " + e.getWeight() + " " + e.getDamage() + " " + 
//							e.getVolume() + " " + e.hunger + " " + e.thirst + " " + 
//							+ e.freshness); 
//					pr.println();
//				} else if(leftHand instanceof Ranged) {
//					pr.print(3);
//					pr.println();
//					Ranged e = (Ranged) leftHand;
//					pr.print(e.getId() + " " + e.getWeight() + " " + 
//							e.getDamage() + " " + e.aSpeed + " " + e.intimidation + " " + e.ammoMax + " " + 
//							e.reloadCooldown + " " + e.accuracy + " " + e.getVolume() + " " + 
//							e.ammoCurrent); //ammo current isnt used in constructor
//					pr.println();
//				} else if(leftHand instanceof Tool) {
//					pr.print(4);
//					pr.println();
//					Tool e = (Tool) leftHand;
//					pr.print(e.getId() + " " + 
//							e.getWeight() + " " + e.getDamage() + " " + e.getaSpeed() + " " + e.getIntimidation() + " " + 
//							e.getRange() + " " + e.getVolume() + " " + e.getPower() + " " + e.getCurrentEndurance() +  " " + 
//							e.getEndurance()); 
//					pr.println();
//				} else if(leftHand instanceof Torch) {
//					pr.print(5);
//					pr.println();
//				} else if(leftHand instanceof WaterContainer) {
//					pr.print(6);
//					pr.println();
//					WaterContainer e = (WaterContainer) leftHand;
//					pr.print(e.getId() + " " + e.getWeight() + " " + 
//							e.getVolume() + " " + e.getCurrentCapacity() +  " " + e.getCapacity()); 
//					pr.println();
//				} else if(leftHand instanceof Weapon) {
//					pr.print(7);
//					pr.println();
//					Weapon e = (Weapon) leftHand;
//					pr.print(e.getId() + " " + e.getWeight() + " " + 
//							e.getDamage() + " " + e.getaSpeed() + " " + e.getIntimidation() + " " + 
//							e.getRange() + " " + e.getVolume() + " " + e.getCurrentEndurance() +  " " + 
//							e.getEndurance()); 
//					pr.println();
//				} else if(leftHand != null){
//					pr.print(8);
//					pr.println();
//					Item e = leftHand;
//					pr.print(e.getId() + " " + e.getWeight() + " " + e.getDamage() + " " + e.getVolume() + " " + e.getCount()); 
//					pr.println();
//				} else { //no item
//					pr.print(0);
//					pr.println();
//				}
				
				//no item
				pr.print(0);
				pr.println();
				pr.println();
				
				//save right hand
//				if(rightHand instanceof Armor) {
//					pr.print(1);
//					pr.println();
//					Armor e = (Armor) rightHand;
//					pr.print(e.getId() + " " + e.getWeight() + " " + e.getDamage() + " " + e.getVolume() + " " + e.intimidation + " " + 
//							e.resistance + " " + e.healthRegen + " " + e.currentEndurance + " " + e.endurance); 
//					pr.println();
//				} else if(rightHand instanceof Food) {
//					pr.print(2);
//					pr.println();
//					Food e = (Food) rightHand;
//					pr.print(e.getId() + " " + e.getWeight() + " " + e.getDamage() + " " + 
//							e.getVolume() + " " + e.hunger + " " + e.thirst + " " + 
//							+ e.freshness); 
//					pr.println();
//				} else if(rightHand instanceof Ranged) {
//					pr.print(3);
//					pr.println();
//					Ranged e = (Ranged) rightHand;
//					pr.print(e.getId() + " " + e.getWeight() + " " + 
//							e.getDamage() + " " + e.aSpeed + " " + e.intimidation + " " + e.ammoMax + " " + 
//							e.reloadCooldown + " " + e.accuracy + " " + e.getVolume() + " " + 
//							e.ammoCurrent); //ammo current isnt used in constructor
//					pr.println();
//				} else if(rightHand instanceof Tool) {
//					pr.print(4);
//					pr.println();
//					Tool e = (Tool) rightHand;
//					pr.print(e.getId() + " " + 
//							e.getWeight() + " " + e.getDamage() + " " + e.getaSpeed() + " " + e.getIntimidation() + " " + 
//							e.getRange() + " " + e.getVolume() + " " + e.getPower() + " " + e.getCurrentEndurance() +  " " + 
//							e.getEndurance()); 
//					pr.println();
//				} else if(rightHand instanceof Torch) {
//					pr.print(5);
//					pr.println();
//				} else if(rightHand instanceof WaterContainer) {
//					pr.print(6);
//					pr.println();
//					WaterContainer e = (WaterContainer) rightHand;
//					pr.print(e.getId() + " " + e.getWeight() + " " + 
//							e.getVolume() + " " + e.getCurrentCapacity() +  " " + e.getCapacity()); 
//					pr.println();
//				} else if(rightHand instanceof Weapon) {
//					pr.print(7);
//					pr.println();
//					Weapon e = (Weapon) rightHand;
//					pr.print(e.getId() + " " + e.getWeight() + " " + 
//							e.getDamage() + " " + e.getaSpeed() + " " + e.getIntimidation() + " " + 
//							e.getRange() + " " + e.getVolume() + " " + e.getCurrentEndurance() +  " " + 
//							e.getEndurance()); 
//					pr.println();
//				} else if(rightHand != null){
//					pr.print(8);
//					pr.println();
//					Item e = rightHand;
//					pr.print(e.getId() + " " + e.getWeight() + " " + e.getDamage() + " " + e.getVolume() + " " + e.getCount()); 
//					pr.println();
//				} else { //no item
//					pr.print(0);
//					pr.println();
//				}
				
				//no item
				pr.print(0);
				pr.println();
				pr.println();
				
				//Save armor
//				Armor helmet = Player.getPlayerData().getEquipment().helmet;
//				Armor chest = Player.getPlayerData().getEquipment().chest;
//				Armor leg = Player.getPlayerData().getEquipment().leg;
//				Armor boot = Player.getPlayerData().getEquipment().boot;
//				Armor gauntlet = Player.getPlayerData().getEquipment().gauntlet;
//				
//				if(helmet != null) {
//					pr.print(1);
//					pr.println();
//					Armor e = (Armor) helmet;
//					pr.print(e.getId() + " " + e.getWeight() + " " + e.getDamage() + " " + e.getVolume() + " " + e.intimidation + " " + 
//							e.resistance + " " + e.healthRegen + " " + e.currentEndurance + " " + e.endurance); 
//					pr.println();
//				} else {
//					pr.print(0);
//					pr.println();
//				}
//				
//				if(chest != null) {
//					pr.print(1);
//					pr.println();
//					Armor e = (Armor) chest;
//					pr.print(e.getId() + " " + e.getWeight() + " " + e.getDamage() + " " + e.getVolume() + " " + e.intimidation + " " + 
//							e.resistance + " " + e.healthRegen + " " + e.currentEndurance + " " + e.endurance); 
//					pr.println();
//				} else {
//					pr.print(0);
//					pr.println();
//				}
//				
//				if(leg != null) {
//					pr.print(1);
//					pr.println();
//					Armor e = (Armor) leg;
//					pr.print(e.getId() + " " + e.getWeight() + " " + e.getDamage() + " " + e.getVolume() + " " + e.intimidation + " " + 
//							e.resistance + " " + e.healthRegen + " " + e.currentEndurance + " " + e.endurance); 
//					pr.println();
//				} else {
//					pr.print(0);
//					pr.println();
//				}
//				
//				if(boot != null) {
//					pr.print(1);
//					pr.println();
//					Armor e = (Armor) boot;
//					pr.print(e.getId() + " " + e.getWeight() + " " + e.getDamage() + " " + e.getVolume() + " " + e.intimidation + " " + 
//							e.resistance + " " + e.healthRegen + " " + e.currentEndurance + " " + e.endurance); 
//					pr.println();
//				} else {
//					pr.print(0);
//					pr.println();
//				}
//				
//				if(gauntlet != null) {
//					pr.print(1);
//					pr.println();
//					Armor e = (Armor) gauntlet;
//					pr.print(e.getId() + " " + e.getWeight() + " " + e.getDamage() + " " + e.getVolume() + " " + e.intimidation + " " + 
//							e.resistance + " " + e.healthRegen + " " + e.currentEndurance + " " + e.endurance); 
//					pr.println();
//				} else {
//					pr.print(0);
//					pr.println();
//				}
				
				//all armor is lost upon death
				for(int i = 0; i < 5; i++)
					pr.println(0);
				
				pr.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	//This method saves the light map
	public void saveLightMap() {
		
		String file = String.format("lightMap/%s", c.getMenuState().getWorldSelectState().getSelectedWorldName());
		File filepath = new File(file);
		
		if(filepath.exists() && !filepath.isDirectory()) {
			
			try {
				
				int width =	c.getGameState().getWorldGenerator().getWidth();
				int height = c.getGameState().getWorldGenerator().getHeight();
				
				PrintWriter pr = new PrintWriter(file);
				
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						pr.print(WorldGenerator.lightMap[x][y] + " ");
					}
					pr.println();
				}
				
				pr.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	//This method saves the power map
	public void savePowerMap() {
		
		String file = String.format("powerMap/%s", c.getMenuState().getWorldSelectState().getSelectedWorldName());
		File filepath = new File(file);
		
		if(filepath.exists() && !filepath.isDirectory()) {
			
			try {
				
				int width =	c.getGameState().getWorldGenerator().getWidth();
				int height = c.getGameState().getWorldGenerator().getHeight();
				
				PrintWriter pr = new PrintWriter(file);
				
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						pr.print(WorldGenerator.powerMap[x][y] + " ");
					}
					pr.println();
				}
				
				pr.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
}
