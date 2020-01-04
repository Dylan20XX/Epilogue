package world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Random;

import alphaPackage.ControlCenter;
import audio.AudioPlayer;
import audio.BackgroundPlayer;
import audio.MusicPlayer;
import creatures.AwakenedSentinel;
import creatures.Boar;
import creatures.BrainFungui;
import creatures.Chicken;
import creatures.CrazedGoat;
import creatures.Creatures;
import creatures.Deer;
import creatures.DeerDeer;
import creatures.DesertScorpion;
import creatures.ExtrafloralNectar;
import creatures.GiantBeetle;
import creatures.Goat;
import creatures.Grass;
import creatures.MutatedChicken;
import creatures.MutatedDeer;
import creatures.Ostrich;
import creatures.PackAlpha;
import creatures.PackMember;
import creatures.Phasmatodea;
import creatures.Player;
import creatures.PrettyShroom;
import creatures.RedGiantBeetle;
import creatures.SandCreep;
import creatures.Scavenger;
import creatures.Sentry;
import creatures.SentryBroodMother;
import creatures.SentryMajor;
import creatures.SentryReplete;
import creatures.ShroomPile;
import creatures.SleepingSentinel;
import creatures.VileSpawn;
import creatures.WonderingGhoul;
import creatures.WoodenStick;
import entity.EntityManager;
import graphics.Animation;
import graphics.Assets;
import graphics.CT;
import graphics.CustomTextWritter;
import graphics.DeathAnimation;
import inputs.MouseCursor;
import inventory.Effect;
import inventory.EffectManager;
import inventory.Equipment;
import inventory.HandCraft;
import inventory.Inventory;
import inventory.MessageBox;
import inventory.PlayerHands;
import inventory.Recipe;
import items.Armor;
import items.Food;
import items.Item;
import items.ItemManager;
import items.Ranged;
import items.Tool;
import items.Torch;
import items.WaterContainer;
import items.Weapon;
import staticEntity.Agave;
import staticEntity.BurntTree;
import staticEntity.Bush;
import staticEntity.Cactus;
import staticEntity.Cave;
import staticEntity.GiantStingerCactus;
import staticEntity.InfectedTree;
import staticEntity.LivingSpike;
import staticEntity.PineSap;
import staticEntity.PineTree;
import staticEntity.RockObstacle;
import staticEntity.RockObstacle2;
import staticEntity.RuinPiece2;
import staticEntity.RuinPiece3;
import staticEntity.RuinPiece4;
import staticEntity.RuinPiece5;
import staticEntity.RuinPiece6;
import staticEntity.SentryHive;
import staticEntity.SpaceShuttle;
import staticEntity.SpineBush;
import staticEntity.StaticEntity;
import staticEntity.TrashBag;
import staticEntity.VileEmbryo;
import structureInventory.AutoCookerV2Craft;
import structureInventory.SmithingTableRecipe;
import structureInventory.WorkbenchRecipe;
import structures.Analyzer;
import structures.AutoCooker;
import structures.CampFire;
import structures.Chest;
import structures.Disintegrator;
import structures.GateDown;
import structures.GateLeft;
import structures.GateRight;
import structures.GateUp;
import structures.HeavyPulseArtillery;
import structures.LampPost;
import structures.MetalChest;
import structures.MetalContainer;
import structures.MetallicOven;
import structures.PowerAdaptor;
import structures.PowerGenerator;
import structures.Purifier;
import structures.RapidPulseArtillery;
import structures.ResearchTable;
import structures.SmithingTable;
import structures.Tent;
import structures.WallDown;
import structures.WallLeft;
import structures.WallRight;
import structures.WallUp;
import structures.WoodenCrate;
import structures.Workbench;
import tiles.Tile;
import tiles.WaterTile;
import utils.UIManager;

/*
 * this class displays the tiles on screen by reading the .txt files written by the WorldWritter class
 */
public class WorldGenerator {

	private int width, height; // width and height of the screen
	private int[][] tiles; // the map, stored in a 2D array
	private int spawnx, spawny; // will be used in the future for player's spawn position
	private EntityManager entityManager;
	private ArrayList<DeathAnimation> deathManager = new ArrayList<DeathAnimation>();
	private ItemManager itemManager;
	private ControlCenter c;
	private UIManager uiManager;
	public boolean inGame = false;
	public WorldSaver worldSaver;

	// day/night effect
	private double radius;

	// Time, day starts at time = 310
	public static double time = 310;
	public static int dayNum = 1;
	private long lastChangeTimer, timeCooldown = 3529, timeTimer = timeCooldown;
	private long lastsleepTimer, sleepCooldown = 25, sleepTimer = sleepCooldown;
	public static double dayBrightness = 155;
	public static float sleepBrightness = 0;
	public static boolean isSleeping, vileSpawned = false;
	public boolean sleepFade = false, alphaAlive = false;
	public static int numVileEmbryo = 0;

	private Random r = new Random();

	// Player
	private Inventory inventory;
	private Equipment equipment;
	private PlayerHands hands;
	private HandCraft handCraft;
	public static MessageBox messageBox;
	private EffectManager effects;	

	private int[][] terrain; // the terrain, stored in a 2D array
	private int[][] topper; // the topper with static entities, stored in a 2D array
	private int[][] checkedEntities; //used for checking the topper array to add in entities
	private int[][] floor; // the floor tiles, stored in a 2D array
	private int[][] current; // the water current tiles, stored in a 2D array - reset each time the game is loaded
	
	public static int[][] lightMap;
	public static int[][] powerMap;

	//Loading Screen Variables
	private boolean loading = true;
	private int amountLoaded = 0;
	private Animation background = new Animation(50, Assets.menuBackground, true);

	// topper loader
	private TopperWriter topperLoader;

	// build cooldown
	private long lastBuildTimer, buildCooldown = 200, buildTimer = buildCooldown;

	// mouse cursor interact cooldown
	private long lastInteractTimer, interactCooldown = 100, interactTimer = interactCooldown;

	// sentry cooldown
	private long lastSentrySpawnTimer, sentrySpawnCooldown = 2000, sentrySpawnTimer = buildCooldown;
	private long lastSentryQueenSpawnTimer, sentryQueenSpawnCooldown = 2000, sentryQueenSpawnTimer =  0;
	
	//structure building variables
	private boolean currentlyBuildingStructure = false;
	private long lastStructureTimer, structureCooldown = 2000, structureTimer = 0;
	private long lastWallRotateTimer, wallRotateCooldown = 150, wallRotateTimer = 0;
	private Animation smoke = new Animation(50, Assets.smoke, true);
	private int smokeX, smokeY;
	private Animation bossSmoke = new Animation(50, Assets.bossSmoke, true);
	public static boolean swallowed = false;

	// main entities
	public static boolean nextSentry = false;
	private boolean broodMotherSpawned = false;
	public boolean broodMotherAlive = false;
	private boolean chestActive = false;
	private Chest chest;
	private boolean metallicOvenActive = false;
	private MetallicOven metallicOven;
	private boolean workbenchActive = false;
	private Workbench workbench;
	private boolean smithingTableActive = false;
	private SmithingTable smithingTable;
	private boolean disintegratorActive = false;
	private Disintegrator disintegrator;
	private boolean analyzerActive = false;
	private Analyzer analyzer;
	private boolean purifierActive = false;
	private Purifier purifier;
	private boolean autoCookerActive = false;
	private AutoCooker autoCooker;
	private boolean autoCookerV2Active = false;
	private AutoCookerV2Craft autoCookerV2;
	private boolean researchTableActive = false;
	private ResearchTable researchTable;
	public static MouseCursor cursor;
	
	private boolean fullMoon = false;
	//a full moon causes wandering ghouls to spawn at a high rate throughout the night
	//after day 10, there is a 1/15 chance of a full moon occuring each night
	//start at t=90 and ends at t=305
	
	//Pack spawning variables
	private boolean canSpawnWolf = true;
	private boolean canSpawnDeer = true;
	private boolean canSpawnGoat = true;
	private long lastWolfTimer, wolfCooldown = 600000, wolfTimer = 0; //new packs can spawn after 10 mins
	private long lastDeerTimer, deerCooldown = 600000, deerTimer = 0;
	private long lastGoatTimer, goatCooldown = 600000, goatTimer = 0;
	private boolean packAlphaActive = false;
	
	// Crafting structure
	private boolean structureCrafted = false;
	private int structureNum = 0;
	private int wallNum = 0; //determines the rotation of the wall
	private Recipe structureRecipe;
	private boolean structureWorkbenchCrafted = false;
	private WorkbenchRecipe structureWorkbenchRecipe;

	public static ArrayList<Creatures> entityRanged = new ArrayList<Creatures>();
	
	public static boolean bossActive = false;

	private boolean gameCompleted = false;
	
	private long lastTickTimer, TickCooldown = 10, TickTimer = 0;
	
	public static int numParasite = 0;
	
	// TESTING VARIABLES
	// --------------------
	private boolean allDay = false;
	
	private boolean debugMode = false;

	// constructor of the WorldGenerator class calls the loadWorld() class
	// takes in the character state for player access
	// takes in a String path for world generation
	public WorldGenerator(String path, ControlCenter c) {
		this.c = c;
		
		time = c.getMenuState().getWorldSelectState().getTime();
		dayNum = c.getMenuState().getWorldSelectState().getDayNum();
		
		if(time > 255) {
			dayBrightness = 255 - (time - 255)*3;
			if(dayBrightness < 0)
				dayBrightness = 0;
		}
		else {
			dayBrightness = (time)*3;
			if(dayBrightness > 255)
				dayBrightness = 255;
		}
		
		worldSaver = new WorldSaver(c);
		
		//c.getGameState().getWorldGenerator().worldSaver.saveWorld();
		
		// generates the world by reading from the file
		loadWorld(path);
	}

	// method that creates the player as an entity
	public void createPlayer() {

		entityManager = new EntityManager(
				new Player(c.getMenuState().getWorldSelectState().getPlayerX(), 
						c.getMenuState().getWorldSelectState().getPlayerY(), 
						c.getMenuState().getWorldSelectState().getCharName(),
						c.getMenuState().getWorldSelectState().getCharHealth(),
						c.getMenuState().getWorldSelectState().getCharRunSpeed(),
						c.getMenuState().getWorldSelectState().getCharDamageScale(),
						c.getMenuState().getWorldSelectState().getCharIntelligence(),
						c.getMenuState().getWorldSelectState().getCharIntimidation(),
						c.getMenuState().getWorldSelectState().getCharResistance(),
						c.getMenuState().getWorldSelectState().getCharEndurability(),
						c.getMenuState().getWorldSelectState().getControlCenter()));

		Player.getPlayerData().setHealth(c.getMenuState().getWorldSelectState().getSavedHealth());
		Player.getPlayerData().setHunger(c.getMenuState().getWorldSelectState().getHunger());
		Player.getPlayerData().setThirst(c.getMenuState().getWorldSelectState().getThirst());
		Player.getPlayerData().setEnergy(c.getMenuState().getWorldSelectState().getEnergy());
		
		Player.getPlayerData().setBasicSurvivalXP(c.getMenuState().getWorldSelectState().getBasicSurvivalXP());
		Player.getPlayerData().setCombatXP(c.getMenuState().getWorldSelectState().getCombatXP());
		Player.getPlayerData().setCookingXP(c.getMenuState().getWorldSelectState().getCookingXP());
		Player.getPlayerData().setBuildingXP(c.getMenuState().getWorldSelectState().getBuildingXP());

		inventory = entityManager.getPlayer().getInventory();
		equipment = entityManager.getPlayer().getEquipment();

		hands = entityManager.getPlayer().getHands();
		itemManager = new ItemManager(c);
		handCraft = entityManager.getPlayer().getHandCraft();
		messageBox = new MessageBox(c);
		effects = new EffectManager(c);

		/*
		entityManager.addEntity(
				new Goat(entityManager.getPlayer().getX()- 600, entityManager.getPlayer().getY() - 150, c));
		
		entityManager.addEntity(
				new Tent(entityManager.getPlayer().getX()- 200, entityManager.getPlayer().getY() - 150, c));

		entityManager.addEntity(
				new ResearchTable(entityManager.getPlayer().getX()- 200, entityManager.getPlayer().getY() + 250, c));

		// test chest
		entityManager.addEntity(
				new WoodenCrate(entityManager.getPlayer().getX() - 100, entityManager.getPlayer().getY() + 200, c));
		entityManager.addEntity(
				new MetalContainer(entityManager.getPlayer().getX() - 100, entityManager.getPlayer().getY() + 300, c));
		entityManager.addEntity(
				new MetalChest(entityManager.getPlayer().getX() - 100, entityManager.getPlayer().getY() + 400, c));

		//entityManager.addEntity(
				//new Goat(entityManager.getPlayer().getX() - 600, entityManager.getPlayer().getY(), c));
		// test metallic oven
		entityManager.addEntity(
				new MetallicOven(entityManager.getPlayer().getX() - 350, entityManager.getPlayer().getY(), c));

		// TEST WORKBENCH, SMITHING TABLE, DISINTEGRATOR, PURIFIER, AUTOCOOKER, AND
		// ANALYZER
		entityManager.addEntity(
				new Workbench(entityManager.getPlayer().getX() + 200, entityManager.getPlayer().getY() - 50, c));

		entityManager.addEntity(
				new SmithingTable(entityManager.getPlayer().getX() + 200, entityManager.getPlayer().getY() - 100, c));

		entityManager.addEntity(
				new Disintegrator(entityManager.getPlayer().getX() + 100, entityManager.getPlayer().getY() - 150, c));

		entityManager.addEntity(
				new Purifier(entityManager.getPlayer().getX() + 100, entityManager.getPlayer().getY() - 200, c));

		entityManager.addEntity(
				new AutoCooker(entityManager.getPlayer().getX() + 200, entityManager.getPlayer().getY() - 250, c));
		
		//sentryHive = new SentryHive(entityManager.getPlayer().getX() + 700, entityManager.getPlayer().getY() + 300, c);
		entityManager.addEntity(
				new SpaceShuttle(entityManager.getPlayer().getX() + 200, entityManager.getPlayer().getY() + 300, c));
		*/
		
		// test mouse cursor - this is required for the mouse cursor to work
		cursor = new MouseCursor(c);
		//entityManager.addEntity(sentryHive);
		
		//entityManager.addEntity(
		//		new AwakenedSentinel(entityManager.getPlayer().getX() + 700, entityManager.getPlayer().getY() + 300, c));

		uiManager = new UIManager();
		c.getMouseManager().setUIManager(uiManager);
		
		loadTopper();
		
		loadPlayerInventory();
		loadChests();
		loadTimedCraftingStructures();
		loadCreatures();
		loadRecipes();
		
	}
	
	public void tick() {
		
		if(loading)
			return;
		
		debugControls();
		
		for (int i = 0; i < entityRanged.size(); i++) {
			entityManager.addEntity(entityRanged.get(i));
			entityRanged.remove(entityRanged.get(i));
			i--;
		}

		if (entityManager != null)
			entityManager.tick();

		if (deathManager != null)
			for(DeathAnimation d : deathManager)
				d.tick();

		if (uiManager != null)
			uiManager.tick();

		if (itemManager != null)
			itemManager.tick();

		if(messageBox != null)
			messageBox.tick();

		if(effects != null)
			effects.tick();

		if(structureCrafted || structureWorkbenchCrafted) {

			structureBuildMode();

		}else if(currentlyBuildingStructure){

			structureBuildTimer();

		}else if(!(handCraft.isActive() || inventory.isActive() || isChestActive() || isWorkbenchActive() || isSmithingTableActive() ||
				isDisintegratorActive() || isAnalyzerActive() || isPurifierActive() || isAutoCookerActive() || isAutoCookerV2Active() ||
				isMetallicOvenActive())){ 
			buildTimer();

			interactTimer();
		}
		
		loadTopperEntities();
		timeControl();
		//System.out.println("Time: " + time);

		// tick the cursor
		if (cursor != null)
			cursor.tick();
		
		//Reset Pack Spawning Variables
		if(canSpawnWolf == false) {
			wolfTimer += System.currentTimeMillis() - lastWolfTimer;
			lastWolfTimer = System.currentTimeMillis();
			
			if (!(wolfTimer < wolfCooldown)) {
				canSpawnWolf = true;
				wolfTimer = 0;
			}	
		}
		
		if(canSpawnDeer == false) {
			deerTimer += System.currentTimeMillis() - lastDeerTimer;
			lastDeerTimer = System.currentTimeMillis();
			
			if (!(deerTimer < deerCooldown)) {
				canSpawnDeer = true;
				deerTimer = 0;
			}
		}
		
		if(canSpawnGoat == false) {
			goatTimer += System.currentTimeMillis() - lastGoatTimer;
			lastGoatTimer = System.currentTimeMillis();
			
			if (!(goatTimer < goatCooldown)) {
				canSpawnGoat = true;
				goatTimer = 0;
			}
		}
		
		//Attempt to spawn packs of creatures
		spawnPack();

		//Attempt to spawn creatures
		spawnCreature();
		
		//Attempt to spawn material entities
		spawnMaterials();

	}

	public void render(Graphics g) {
		// render limits, tiles that users can see
		int xStart = (int) (Math.max(0, c.getGameCamera().getxOffset() / Tile.TILEWIDTH));
		int xEnd = (int) Math.min(width, (c.getGameCamera().getxOffset() + c.getWidth()) / Tile.TILEWIDTH + 1);
		int yStart = (int) (Math.max(0, c.getGameCamera().getyOffset() / Tile.TILEHEIGHT));
		int yEnd = (int) Math.min(height, (c.getGameCamera().getyOffset() + c.getHeight()) / Tile.TILEHEIGHT + 1);

		for (int y = yStart; y < yEnd; y++) {
			for (int x = xStart; x < xEnd; x++) {
				// biome test
				if (tiles[x][y] == 0 || tiles[x][y] == 1) {

					if (terrain[x][y] == 1) { //natural biome
						Tile.naturalTile.render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
								(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()));
					} else if (terrain[x][y] == 2) { //forest biome
						Tile.dirtETile.render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
								(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()));
					} else if (terrain[x][y] == 3) { //semi-desert biome
						Tile.semiDesertTile.render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
								(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()));
					} else if (terrain[x][y] == 4) { //waste biome
						Tile.dirtETile.render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
								(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()));
					} else if (terrain[x][y] == 5) { //ruin biome
						Tile.dirtETile.render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
								(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()));
					} else if (terrain[x][y] == 6) { //infected biome
						Tile.infectedTile.render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
								(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()));
					} else {
						getTile(x, y).render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
								(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()));
					}

				} else if(tiles[x][y] == 2) { //if tiles[x][y] == 2 show the uncommon form of the tile

					if (terrain[x][y] == 1) { //natural biome
						Tile.natural2Tile.render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
								(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()));
					} else if (terrain[x][y] == 2) { //forest biome
						Tile.dirtETile.render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
								(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()));
					} else if (terrain[x][y] == 3) { //semi-desert biome
						Tile.semiDesertTile.render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
								(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()));
					} else if (terrain[x][y] == 4) { //waste biome
						Tile.dirtETile.render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
								(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()));
					} else if (terrain[x][y] == 5) { //ruin biome
						Tile.dirtETile.render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
								(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()));
					} else if (terrain[x][y] == 6) { //infected biome
						Tile.infected2Tile.render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
								(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()));
					} else {
						getTile(x, y).render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
								(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()));
					}

				} else if(tiles[x][y] == 3) { //if tiles[x][y] == 3 show the rare form of the tile


					if (terrain[x][y] == 1) { //natural biome
						Tile.natural3Tile.render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
								(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()));
					} else if (terrain[x][y] == 2) { //forest biome
						Tile.dirtETile.render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
								(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()));
					} else if (terrain[x][y] == 3) { //semi-desert biome
						Tile.semiDesertTile.render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
								(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()));
					} else if (terrain[x][y] == 4) { //waste biome
						Tile.dirtETile.render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
								(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()));
					} else if (terrain[x][y] == 5) { //ruin biome
						Tile.dirtETile.render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
								(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()));
					} else if (terrain[x][y] == 6) { //infected biome
						Tile.infected3Tile.render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
								(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()));
					} else {
						Tile.dirt3Tile.render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
								(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()));
					}

				} else if(tiles[x][y] == 10) { //fill the water tiles

					//render the tile under the water tile incase the water tile is at the edge of the lake
					if (terrain[x][y] == 1) { //natural biome
						Tile.naturalTile.render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
								(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()));
					} else if (terrain[x][y] == 2) { //forest biome
						Tile.dirtETile.render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
								(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()));
					} else if (terrain[x][y] == 3) { //semi-desert biome
						Tile.semiDesertTile.render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
								(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()));
					} else if (terrain[x][y] == 4) { //waste biome
						Tile.dirtETile.render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
								(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()));
					} else if (terrain[x][y] == 5) { //ruin biome
						Tile.dirtETile.render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
								(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()));
					} else if (terrain[x][y] == 6) { //infected biome
						Tile.infectedTile.render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
								(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()));
					} else {
						getTile(x, y).render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
								(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()));
					}


					if(x > 0 && x < width - 1 && y > 0 && y < height - 1) {
						//render the water tile under it
						if(tiles[x][y-1] == 10 && tiles[x][y+1] == 10 && tiles[x-1][y] == 10 && tiles[x+1][y] == 10) { //4 sides surrounded by water

							if(tiles[x-1][y-1] != 10) { //no water on the top left
								((WaterTile) Tile.waterTile).render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
										(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), 14);
							} else if(tiles[x+1][y-1] != 10) { //no water on the top right
								((WaterTile) Tile.waterTile).render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
										(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), 13);
							} else if(tiles[x+1][y+1] != 10) { //no water on the bottom right
								((WaterTile) Tile.waterTile).render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
										(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), 11);
							} else if(tiles[x-1][y+1] != 10) { //no water on the bottom left
								((WaterTile) Tile.waterTile).render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
										(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), 12);
							} else { //no corners touching land

								//have a chance of it being a current tile
								if(current[x][y] == 0) {
									int rand = r.nextInt(8);
									if(rand == 0) { //current 1 tile
										current[x][y] = 1;
									} else if(rand == 1) { //current 2 tile
										current[x][y] = 2;
									} else { //normal water tile
										current[x][y] = 3;
									}
								}

								if(current[x][y] == 1) {
									((WaterTile) Tile.waterTile).render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
											(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), 9);
								} else if(current[x][y] == 2) {
									((WaterTile) Tile.waterTile).render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
											(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), 10);
								} else {
									((WaterTile) Tile.waterTile).render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
											(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), 4);
								}

							}

						} else if(tiles[x][y-1] == 10 && tiles[x][y+1] == 10 && tiles[x-1][y] == 10) { //no water to the right
							((WaterTile) Tile.waterTile).render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
									(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), 5);
						} else if(tiles[x][y-1] == 10 && tiles[x][y+1] == 10 && tiles[x+1][y] == 10) { //no water to the left
							((WaterTile) Tile.waterTile).render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
									(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), 3);
						} else if(tiles[x][y+1] == 10 && tiles[x-1][y] == 10 && tiles[x+1][y] == 10) { //no water on top
							((WaterTile) Tile.waterTile).render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
									(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), 1);
						} else if(tiles[x][y-1] == 10 && tiles[x-1][y] == 10 && tiles[x+1][y] == 10) { //no water below
							((WaterTile) Tile.waterTile).render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
									(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), 7);
						} else if(tiles[x][y+1] == 10 && tiles[x-1][y] == 10) { //no water on the top and right
							((WaterTile) Tile.waterTile).render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
									(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), 2);
						} else if(tiles[x][y-1] == 10 && tiles[x-1][y] == 10) { //no water on the right and bottom
							((WaterTile) Tile.waterTile).render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
									(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), 8);
						} else if(tiles[x][y-1] == 10 && tiles[x+1][y] == 10) { //no water on the bottom and left
							((WaterTile) Tile.waterTile).render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
									(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), 6);
						} else if(tiles[x][y+1] == 10 && tiles[x+1][y] == 10) { //no water to the left and top
							((WaterTile) Tile.waterTile).render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
									(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), 0);

						} else if(tiles[x-1][y] == 10 && tiles[x+1][y] == 10) { //no water to the top and bottom
							((WaterTile) Tile.waterTile).render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
									(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), 1);
						} else if(tiles[x][y-1] == 10 && tiles[x][y+1] == 10) { //no water to the left and right
							((WaterTile) Tile.waterTile).render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
									(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), 5);

						} else if(tiles[x][y-1] == 10) { //only water on the top
							((WaterTile) Tile.waterTile).render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
									(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), 8);
						} else if(tiles[x+1][y] == 10) { //only water to the right
							((WaterTile) Tile.waterTile).render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
									(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), 1);
						} else if(tiles[x][y+1] == 10) { //only water below
							((WaterTile) Tile.waterTile).render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
									(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), 6);
						} else if(tiles[x-1][y] == 10) { //only water to the left
							((WaterTile) Tile.waterTile).render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
									(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), 2);
						}

					} else {
						((WaterTile) Tile.waterTile).render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
								(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), 4);
					}



				} else {
					getTile(x, y).render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
							(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()));
				}

				//Render dirt edges
				if((terrain[x][y] != 0 && tiles[x][y] != 10) && 
						(terrain[x-1][y-1] == 0 || terrain[x][y-1] == 0 || terrain[x+1][y-1] == 0 || 
						terrain[x-1][y] == 0 || terrain[x+1][y] == 0 || 
						terrain[x-1][y+1] == 0 || terrain[x][y+1] == 0 || terrain[x+1][y+1] == 0) ) {

					if(x > 0 && x < width - 1 && y > 0 && y < height - 1) {

						//render the dirt edge on top of the tile
						if(terrain[x][y-1] == 0 && terrain[x][y+1] == 0 && terrain[x-1][y] == 0 && terrain[x+1][y] == 0) { //4 sides surrounded by dirt

							if(terrain[x-1][y-1] != 0) { //no water on the top left
								g.drawImage(Assets.dirtTileDiagonal[3], (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
										(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), Tile.TILEWIDTH, Tile.TILEHEIGHT, null);
							} else if(terrain[x+1][y-1] != 0) { //no water on the top right
								g.drawImage(Assets.dirtTileDiagonal[2], (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
										(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), Tile.TILEWIDTH, Tile.TILEHEIGHT, null);
							} else if(terrain[x+1][y+1] != 0) { //no water on the bottom right
								g.drawImage(Assets.dirtTileDiagonal[0], (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
										(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), Tile.TILEWIDTH, Tile.TILEHEIGHT, null);
							} else if(terrain[x-1][y+1] != 0) { //no water on the bottom left
								g.drawImage(Assets.dirtTileDiagonal[1], (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
										(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), Tile.TILEWIDTH, Tile.TILEHEIGHT, null);
							}
							
						} else if(terrain[x][y-1] == 0 && terrain[x][y+1] == 0 && terrain[x-1][y] == 0) { //no dirt to the right
							g.drawImage(Assets.dirtTileEdge[5], (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
									(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), Tile.TILEWIDTH, Tile.TILEHEIGHT, null);
						} else if(terrain[x][y-1] == 0 && terrain[x][y+1] == 0 && terrain[x+1][y] == 0) { //no dirt to the left
							g.drawImage(Assets.dirtTileEdge[3], (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
									(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), Tile.TILEWIDTH, Tile.TILEHEIGHT, null);
						} else if(terrain[x][y+1] == 0 && terrain[x-1][y] == 0 && terrain[x+1][y] == 0) { //no dirt on top
							g.drawImage(Assets.dirtTileEdge[1], (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
									(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), Tile.TILEWIDTH, Tile.TILEHEIGHT, null);
						} else if(terrain[x][y-1] == 0 && terrain[x-1][y] == 0 && terrain[x+1][y] == 0) { //no dirt below
							g.drawImage(Assets.dirtTileEdge[7], (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
									(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), Tile.TILEWIDTH, Tile.TILEHEIGHT, null);

						} else if(terrain[x][y+1] == 0 && terrain[x-1][y] == 0) { //no dirt on the top and right
							g.drawImage(Assets.dirtTileEdge[2], (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
									(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), Tile.TILEWIDTH, Tile.TILEHEIGHT, null);
						} else if(terrain[x][y-1] == 0 && terrain[x-1][y] == 0) { //no dirt on the right and bottom
							g.drawImage(Assets.dirtTileEdge[8], (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
									(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), Tile.TILEWIDTH, Tile.TILEHEIGHT, null);
						} else if(terrain[x][y-1] == 0 && terrain[x+1][y] == 0) { //no dirt on the bottom and left
							g.drawImage(Assets.dirtTileEdge[6], (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
									(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), Tile.TILEWIDTH, Tile.TILEHEIGHT, null);
						} else if(terrain[x][y+1] == 0 && terrain[x+1][y] == 0) { //no dirt to the left and top
							g.drawImage(Assets.dirtTileEdge[0], (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
									(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), Tile.TILEWIDTH, Tile.TILEHEIGHT, null);

						} else if(terrain[x-1][y] == 0 && terrain[x+1][y] == 0) { //no dirt to the top and bottom
							g.drawImage(Assets.dirtTileEdge[1], (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
									(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), Tile.TILEWIDTH, Tile.TILEHEIGHT, null);
						} else if(terrain[x][y-1] == 0 && terrain[x][y+1] == 0) { //no dirt to the left and right
							g.drawImage(Assets.dirtTileEdge[5], (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
									(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), Tile.TILEWIDTH, Tile.TILEHEIGHT, null);

						} else if(terrain[x][y-1] == 0) { //only dirt on the top
							g.drawImage(Assets.dirt2TileEdge[3], (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
									(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), Tile.TILEWIDTH, Tile.TILEHEIGHT, null);
							//g.drawImage(Assets.dirtTileEdge[7], (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
									//(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), Tile.TILEWIDTH, Tile.TILEHEIGHT, null);
						} else if(terrain[x+1][y] == 0) { //only dirt to the right
							g.drawImage(Assets.dirt2TileEdge[1], (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
									(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), Tile.TILEWIDTH, Tile.TILEHEIGHT, null);
							//g.drawImage(Assets.dirtTileEdge[3], (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
									//(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), Tile.TILEWIDTH, Tile.TILEHEIGHT, null);
						} else if(terrain[x][y+1] == 0) { //only dirt below
							g.drawImage(Assets.dirt2TileEdge[0], (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
									(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), Tile.TILEWIDTH, Tile.TILEHEIGHT, null);
							//g.drawImage(Assets.dirtTileEdge[1], (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
									//(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), Tile.TILEWIDTH, Tile.TILEHEIGHT, null);
						} else if(terrain[x-1][y] == 0) { //only dirt to the left
							g.drawImage(Assets.dirt2TileEdge[2], (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
									(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), Tile.TILEWIDTH, Tile.TILEHEIGHT, null);
							//g.drawImage(Assets.dirtTileEdge[5], (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
									//(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), Tile.TILEWIDTH, Tile.TILEHEIGHT, null);
						}
					}
				}

				//Render floors
				if(floor[x][y] == 1) {
					Platform.woodenPlatform.render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
							(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()));
				} else if(floor[x][y] == 2) {
					Platform.stonePlatform.render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
							(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()));
				} else if(floor[x][y] == 3) {
					Platform.metalPlatform.render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
							(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()));
				} else if(floor[x][y] == 4) {
					Platform.grassPlatform.render(g, (int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()),
							(int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()));
				}
			}
		}

		if (itemManager != null)
			itemManager.render(g);

		if (entityManager != null) {
			entityManager.render(g);

			if (hands.getHand() != null) {
				if (hands.getHand().getType().equals("structure")) {

					
					int buildX = (int) ((c.getMouseManager().mouseBound().x -
							(c.getGameCamera().getxOffset() % Tile.TILEWIDTH)) -
							c.getMouseManager().mouseBound().x % Tile.TILEWIDTH);
					int buildY = (int) ((c.getMouseManager().mouseBound().y - 
							(c.getGameCamera().getyOffset() % Tile.TILEHEIGHT)) - 
							c.getMouseManager().mouseBound().y % Tile.TILEHEIGHT);
					
					Rectangle rect = new Rectangle(buildX, buildY, Tile.TILEWIDTH, Tile.TILEHEIGHT);

					Graphics2D g2d = (Graphics2D) g;
					g.setColor(Color.WHITE);

					for(int i = 0; i < entityManager.entities.size(); i++) {
						if(rect.intersects(entityManager.entities.get(i).getBounds()) && !(structureNum == 51 || structureNum == 52 || structureNum == 53))
							g.setColor(Color.RED);
					}

					//set the square color to red to show that its outside of player build range
					if(Math.abs(buildX - (Player.getPlayerData().getXLoc() + Player.getPlayerData().getBounds().getWidth())) > Tile.TILEWIDTH * 3 || 
							Math.abs(buildY - (Player.getPlayerData().getYLoc() + Player.getPlayerData().getBounds().getHeight())) > Tile.TILEHEIGHT * 3 )
						g.setColor(Color.RED);
					
					g2d.draw(rect);
				}
			}
		}
		
		if (deathManager != null) {
			for(int i = 0; i < deathManager.size(); i++) {
				if(deathManager.get(i).isFaded() <= 0) {
					deathManager.remove(i);
				}
				if(deathManager.isEmpty())
					break;
				
				if(i < deathManager.size())
					deathManager.get(i).render(g);
			}
		}

		if(structureCrafted) {

			int buildX = (int) ((c.getMouseManager().mouseBound().x -
					(c.getGameCamera().getxOffset() % Tile.TILEWIDTH)) -
					c.getMouseManager().mouseBound().x % Tile.TILEWIDTH);
			int buildY = (int) ((c.getMouseManager().mouseBound().y - 
					(c.getGameCamera().getyOffset() % Tile.TILEHEIGHT)) - 
					c.getMouseManager().mouseBound().y % Tile.TILEHEIGHT);

			Rectangle rect = new Rectangle(buildX, buildY, Tile.TILEWIDTH, Tile.TILEHEIGHT);

			Graphics2D g2d = (Graphics2D) g;
			g.setColor(Color.WHITE);

			for(int i = 0; i < entityManager.entities.size(); i++) {
				if(rect.intersects(entityManager.entities.get(i).getBounds()) && !(structureNum == 51 || structureNum == 52 || structureNum == 53))
					g.setColor(Color.RED);
			}

			//set the square color to red to show that its outside of player build range
			if(Math.abs(buildX - (Player.getPlayerData().getXLoc() + Player.getPlayerData().getBounds().getWidth())) > Tile.TILEWIDTH * 3 || 
					Math.abs(buildY - (Player.getPlayerData().getYLoc() + Player.getPlayerData().getBounds().getHeight())) > Tile.TILEHEIGHT * 3 )
				g.setColor(Color.RED);
			int wallType = 0;
			if(structureNum == 9 || structureNum == 10 || structureNum == 11 || structureNum == 12 || structureNum == 13 || structureNum == 14) {
			if(structureWorkbenchCrafted) {
					wallType = getStructureWorkbenchRecipe().getWallType();
				}else if (structureCrafted){
					wallType = getStructureRecipe().getWallType();
				}
			}
//
//			if(structureNum == 1) {
//				g.drawImage(Assets.workbench, buildX, buildY, Workbench.width, Workbench.height, null);
//			} else if(structureNum == 2) {
//				g.drawImage(Assets.smithingTable, buildX, buildY, SmithingTable.width, SmithingTable.height, null);
//			} else if(structureNum == 3) { //replace with purifier image when it's made
//				g.drawImage(Assets.woodenCrate, buildX, buildY, Purifier.width, Purifier.height, null);
//			} else if(structureNum == 4) { //replace with analyzer image when made
//				g.drawImage(Assets.researchTable, buildX, buildY, Analyzer.width, Analyzer.height, null);
//			} else if(structureNum == 5) {
//				g.drawImage(Assets.smelter, buildX, buildY, MetallicOven.width, MetallicOven.height, null);
//			} else if(structureNum == 6) {
//				g.drawImage(Assets.autoCooker, buildX, buildY, AutoCooker.width, AutoCooker.height, null);
//			} else if(structureNum == 7) {
//				g.drawImage(Assets.disintegrator[0], buildX, buildY, Disintegrator.width, Disintegrator.height, null);
//			} else 
			if(structureNum == 9 || structureNum == 11|| structureNum == 13) { //walls

				if(wallType == 1) {
					if(wallNum == 0) { //top
						g.drawImage(Assets.woodenFenceHorizontal, buildX - WallUp.wallXoffset, buildY - WallUp.wallYoffset, Assets.woodenFenceHorizontal.getWidth(), Assets.woodenFenceHorizontal.getHeight(), null);
					} else if(wallNum == 1) { //right
						g.drawImage(CT.flip(Assets.woodenFenceVertical), buildX - WallRight.wallXoffset, buildY - WallRight.wallYoffset, Assets.woodenFenceVertical.getWidth(), Assets.woodenFenceVertical.getHeight(), null);
					} else if(wallNum == 2) { //bottom
						g.drawImage(Assets.woodenFenceHorizontal, buildX - WallDown.wallXoffset, buildY - WallDown.wallYoffset, Assets.woodenFenceHorizontal.getWidth(), Assets.woodenFenceHorizontal.getHeight(), null);
					} else if(wallNum == 3) { //left
						g.drawImage(Assets.woodenFenceVertical, buildX - WallLeft.wallXoffset, buildY - WallLeft.wallYoffset, Assets.woodenFenceVertical.getWidth(), Assets.woodenFenceVertical.getHeight(), null);
					}
				} else if(wallType == 2) {
					if(wallNum == 0) { //top
						g.drawImage(Assets.stoneWallHorizontal, buildX, buildY, WallUp.width, WallUp.height, null);
					} else if(wallNum == 1) { //right
						g.drawImage(Assets.stoneWallVertical, buildX + StaticEntity.DEFAULT_STATICOBJECT_WIDTH - WallLeft.width, buildY, WallLeft.width, WallLeft.height, null);
					} else if(wallNum == 2) { //bottom
						g.drawImage(Assets.stoneWallHorizontal, buildX, buildY + StaticEntity.DEFAULT_STATICOBJECT_HEIGHT - WallDown.height, WallDown.width, WallDown.height, null);
					} else if(wallNum == 3) { //left
						g.drawImage(Assets.stoneWallVertical, buildX, buildY, WallLeft.width, WallLeft.height, null);
					}
				} else if(wallType == 3) {
					if(wallNum == 0) { //top
						g.drawImage(Assets.metalWallHorizontal, buildX, buildY, WallUp.width, WallUp.height, null);
					} else if(wallNum == 1) { //right
						g.drawImage(Assets.metalWallVertical, buildX + StaticEntity.DEFAULT_STATICOBJECT_WIDTH - WallLeft.width, buildY, WallLeft.width, WallLeft.height, null);
					} else if(wallNum == 2) { //bottom
						g.drawImage(Assets.metalWallHorizontal, buildX, buildY + StaticEntity.DEFAULT_STATICOBJECT_HEIGHT - WallDown.height, WallDown.width, WallDown.height, null);
					} else if(wallNum == 3) { //left
						g.drawImage(Assets.metalWallVertical, buildX, buildY, WallLeft.width, WallLeft.height, null);
					}
				}

			} else if(structureNum == 10 || structureNum == 12 || structureNum == 14) { //gates

				if(wallType == 1) {
					if(wallNum == 0) { //top
						g.drawImage(Assets.woodenGateHorizontalClosed, buildX - GateUp.wallXoffset, buildY - GateUp.wallYoffset, Assets.woodenGateHorizontalClosed.getWidth(), Assets.woodenGateHorizontalClosed.getHeight(), null);
					} else if(wallNum == 1) { //right
						g.drawImage(Assets.woodenGateVerticalClosed, buildX - GateRight.wallXoffset, buildY - GateRight.wallYoffset, Assets.woodenGateVerticalClosed.getWidth(), Assets.woodenGateVerticalClosed.getHeight(), null);
					} else if(wallNum == 2) { //bottom
						g.drawImage(Assets.woodenGateHorizontalClosed, buildX - GateDown.wallXoffset, buildY - GateDown.wallYoffset, Assets.woodenGateHorizontalClosed.getWidth(), Assets.woodenGateHorizontalClosed.getHeight(), null);
					} else if(wallNum == 3) { //left
						g.drawImage(Assets.woodenGateVerticalClosed, buildX - GateLeft.wallXoffset, buildY - GateLeft.wallYoffset, Assets.woodenGateVerticalClosed.getWidth(), Assets.woodenGateVerticalClosed.getHeight(), null);
					}
				} else if(wallType == 2) {
					if(wallNum == 0) { //top
						g.drawImage(Assets.stoneGateHorizontalClosed, buildX, buildY, WallUp.width, WallUp.height, null);
					} else if(wallNum == 1) { //right
						g.drawImage(Assets.stoneGateVerticalClosed, buildX + StaticEntity.DEFAULT_STATICOBJECT_WIDTH - WallLeft.width, buildY, WallLeft.width, WallLeft.height, null);
					} else if(wallNum == 2) { //bottom
						g.drawImage(Assets.stoneGateHorizontalClosed, buildX, buildY + StaticEntity.DEFAULT_STATICOBJECT_HEIGHT - WallDown.height, WallDown.width, WallDown.height, null);
					} else if(wallNum == 3) { //left
						g.drawImage(Assets.stoneGateVerticalClosed, buildX, buildY, WallLeft.width, WallLeft.height, null);
					}
				} else if(wallType == 3) {
					if(wallNum == 0) { //top
						g.drawImage(Assets.metalGateHorizontalClosed, buildX, buildY, WallUp.width, WallUp.height, null);
					} else if(wallNum == 1) { //right
						g.drawImage(Assets.metalGateVerticalClosed, buildX + StaticEntity.DEFAULT_STATICOBJECT_WIDTH - WallLeft.width, buildY, WallLeft.width, WallLeft.height, null);
					} else if(wallNum == 2) { //bottom
						g.drawImage(Assets.metalGateHorizontalClosed, buildX, buildY + StaticEntity.DEFAULT_STATICOBJECT_HEIGHT - WallDown.height, WallDown.width, WallDown.height, null);
					} else if(wallNum == 3) { //left
						g.drawImage(Assets.metalGateVerticalClosed, buildX, buildY, WallLeft.width, WallLeft.height, null);
					}
				}

			} //else if(structureNum == 51) {
//				g.drawImage(Assets.woodenPlatform, buildX, buildY, Platform.FLOORWIDTH,  Platform.FLOORHEIGHT, null);
//			} else if(structureNum == 52) {
//				g.drawImage(Assets.stonePlatform, buildX, buildY, Platform.FLOORWIDTH,  Platform.FLOORHEIGHT, null);
//			} else if(structureNum == 53) {
//				g.drawImage(Assets.metalPlatform, buildX, buildY, Platform.FLOORWIDTH,  Platform.FLOORHEIGHT, null);
//			} else if(structureNum == 54) {
//				g.drawImage(Assets.naturalTile, buildX, buildY, Platform.FLOORWIDTH,  Platform.FLOORHEIGHT, null);
//			}
			else if(structureNum == 10 || structureNum == 12 || structureNum == 14) { //gates
				
								if(wallType == 1) {
									if(wallNum == 0) { //top
										g.drawImage(Assets.woodenGateHorizontalClosed, buildX - GateUp.wallXoffset, buildY - GateUp.wallYoffset, Assets.woodenGateHorizontalClosed.getWidth(), Assets.woodenGateHorizontalClosed.getHeight(), null);
									} else if(wallNum == 1) { //right
										g.drawImage(Assets.woodenGateVerticalClosed, buildX - GateRight.wallXoffset, buildY - GateRight.wallYoffset, Assets.woodenGateVerticalClosed.getWidth(), Assets.woodenGateVerticalClosed.getHeight(), null);
									} else if(wallNum == 2) { //bottom
										g.drawImage(Assets.woodenGateHorizontalClosed, buildX - GateDown.wallXoffset, buildY - GateDown.wallYoffset, Assets.woodenGateHorizontalClosed.getWidth(), Assets.woodenGateHorizontalClosed.getHeight(), null);
									} else if(wallNum == 3) { //left
										g.drawImage(Assets.woodenGateVerticalClosed, buildX - GateLeft.wallXoffset, buildY - GateLeft.wallYoffset, Assets.woodenGateVerticalClosed.getWidth(), Assets.woodenGateVerticalClosed.getHeight(), null);
									}
								} else if(wallType == 2) {
									if(wallNum == 0) { //top
										g.drawImage(Assets.stoneGateHorizontalClosed, buildX, buildY, WallUp.width, WallUp.height, null);
									} else if(wallNum == 1) { //right
										g.drawImage(Assets.stoneGateVerticalClosed, buildX + StaticEntity.DEFAULT_STATICOBJECT_WIDTH - WallLeft.width, buildY, WallLeft.width, WallLeft.height, null);
									} else if(wallNum == 2) { //bottom
										g.drawImage(Assets.stoneGateHorizontalClosed, buildX, buildY + StaticEntity.DEFAULT_STATICOBJECT_HEIGHT - WallDown.height, WallDown.width, WallDown.height, null);
									} else if(wallNum == 3) { //left
										g.drawImage(Assets.stoneGateVerticalClosed, buildX, buildY, WallLeft.width, WallLeft.height, null);
									}
								} else if(wallType == 3) {
									if(wallNum == 0) { //top
										g.drawImage(Assets.metalGateHorizontalClosed, buildX, buildY, WallUp.width, WallUp.height, null);
									} else if(wallNum == 1) { //right
										g.drawImage(Assets.metalGateVerticalClosed, buildX + StaticEntity.DEFAULT_STATICOBJECT_WIDTH - WallLeft.width, buildY, WallLeft.width, WallLeft.height, null);
									} else if(wallNum == 2) { //bottom
										g.drawImage(Assets.metalGateHorizontalClosed, buildX, buildY + StaticEntity.DEFAULT_STATICOBJECT_HEIGHT - WallDown.height, WallDown.width, WallDown.height, null);
									} else if(wallNum == 3) { //left
										g.drawImage(Assets.metalGateVerticalClosed, buildX, buildY, WallLeft.width, WallLeft.height, null);
									}
								}
				
							}
			
			g2d.draw(rect);
			
			if(structureNum == 9 || structureNum == 11|| structureNum == 13 || structureNum == 10 || structureNum == 12 || structureNum == 14) { //walls
				CustomTextWritter.drawString(g, "PRESS R TO ROTATE", 200, 100, true, Color.YELLOW, Assets.font28);
			}
			
			//PRESS ESC TO CANCEL
			CustomTextWritter.drawString(g, "PRESS ESC TO CANCEL", 200, 70, true, Color.YELLOW, Assets.font28);
		}

		if(!swallowed)
			for (int y = yStart; y < yEnd; y++) {
				for (int x = xStart; x < xEnd; x++) {
					if (!allDay) 
						if(lightMap != null)
							if(lightMap[x][y] >= 3) {
								g.setColor(new Color((int)(dayBrightness/15), (int)(dayBrightness/20), (int)(dayBrightness/40), (int)(dayBrightness/10 * 4)));
								g.fillRect((int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()), (int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), Tile.TILEWIDTH, Tile.TILEHEIGHT);
							} else if(lightMap[x][y] == 2) {
								g.setColor(new Color((int)(dayBrightness/17), (int)(dayBrightness/22), (int)(dayBrightness/30), (int)(dayBrightness/10 * 6)));
								g.fillRect((int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()), (int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), Tile.TILEWIDTH, Tile.TILEHEIGHT);
							} else if(lightMap[x][y] == 1) {
								g.setColor(new Color((int)(dayBrightness/21), (int)(dayBrightness/24), (int)(dayBrightness/20), (int)(dayBrightness/10 * 8)));
								g.fillRect((int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()), (int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), Tile.TILEWIDTH, Tile.TILEHEIGHT);
							} else {
								g.setColor(new Color((int)(dayBrightness/25), (int)(dayBrightness/25), (int)(dayBrightness/15), (int)(dayBrightness)));
								g.fillRect((int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()), (int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), Tile.TILEWIDTH, Tile.TILEHEIGHT);
							}
				}
			}
		else {
			for (int y = yStart; y < yEnd; y++) {
				for (int x = xStart; x < xEnd; x++) {
					g.setColor(Color.black);
					g.fillRect((int) (x * Tile.TILEWIDTH - c.getGameCamera().getxOffset()), (int) (y * Tile.TILEHEIGHT - c.getGameCamera().getyOffset()), Tile.TILEWIDTH, Tile.TILEHEIGHT);
				}
			}
		}
		
		if(isSleeping) {
			sleep();
			g.setColor(new Color(0, 0, 0, sleepBrightness));
			g.fillRect(0, 0, c.width, c.height);
		}
		
		if(entityManager != null)
			for(int i = 0; i < entityManager.getEntitiesInBound().size(); i++) {
				if(entityManager.getEntitiesInBound().get(i).getType().equals("pulse")) {
					entityManager.getEntitiesInBound().get(i).render(g);
				}
			}

		//Show time left for crafting
		if(currentlyBuildingStructure) {
			g.setColor(Color.GREEN);
			g.fillRect((int) (Player.getPlayerData().getXLoc() - 40),(int) (Player.getPlayerData().getYLoc() - 50), (int) (((double)(structureCooldown - structureTimer) / (double) structureCooldown) * 100), 10);
			//Animation smoke = new Animation(50, Assets.smoke, true);
			smoke.tick();
			g.drawImage(smoke.getCurrentFrame(), (int) (Player.getPlayerData().getX() - c.getGameCamera().getxOffset()) - 17 - 32,
					(int) (Player.getPlayerData().getY() - c.getGameCamera().getyOffset()) - 21 - 32, Tile.TILEWIDTH * 2, Tile.TILEHEIGHT * 2, null);
			g.drawImage(smoke.getCurrentFrame(), smokeX - 32, smokeY - 32, Tile.TILEWIDTH * 2, Tile.TILEHEIGHT * 2, null);
		}

		Graphics2D g2d = (Graphics2D) g;
		
		g.drawImage(Assets.healthIcon, (int) ((c.getWidth() - 150) * ControlCenter.scaleValue),
				(int) (20 * ControlCenter.scaleValue), (int) (30 * ControlCenter.scaleValue),
				(int) (30 * ControlCenter.scaleValue), null);
		g.drawImage(Assets.hungerIcon, (int) ((c.getWidth() - 120) * ControlCenter.scaleValue),
				(int) (20 * ControlCenter.scaleValue), (int) (30 * ControlCenter.scaleValue),
				(int) (30 * ControlCenter.scaleValue), null);
		g.drawImage(Assets.thirstIcon, (int) ((c.getWidth() - 90) * ControlCenter.scaleValue),
				(int) (20 * ControlCenter.scaleValue), (int) (30 * ControlCenter.scaleValue),
				(int) (30 * ControlCenter.scaleValue), null);
		g.drawImage(Assets.energyIcon, (int) ((c.getWidth() - 60) * ControlCenter.scaleValue),
				(int) (20 * ControlCenter.scaleValue), (int) (30 * ControlCenter.scaleValue),
				(int) (30 * ControlCenter.scaleValue), null);
		g.drawImage(Assets.armorIcon, (int) ((c.getWidth() - 140) * ControlCenter.scaleValue),
				(int) (170 * ControlCenter.scaleValue), (int) (40 * ControlCenter.scaleValue),
				(int) (40 * ControlCenter.scaleValue), null);

		if(messageBox != null)
			messageBox.render(g);
		if(effects != null)
			effects.render(g);

		if (entityManager != null) {
			g.setColor(Color.DARK_GRAY);

			CustomTextWritter.drawString(g, "" + Player.getPlayerData().getResistance(), c.getWidth() - 70, 190, true,
					Color.WHITE, Assets.font28);

			if (Math.round(
					(double) Player.getPlayerData().getHealth() / (double) Player.getPlayerData().ogHealth * 100) > 75)
				g.setColor(Color.green);
			else if (Math.round(
					(double) Player.getPlayerData().getHealth() / (double) Player.getPlayerData().ogHealth * 100) > 25)
				g.setColor(Color.YELLOW);
			else
				g.setColor(Color.RED);

			g.fillRect(c.getWidth() - 150 + 10, 60, 10, (int) Math.round(
					(double) Player.getPlayerData().getHealth() / (double) Player.getPlayerData().ogHealth * 100));

			if (Math.round(
					(double) Player.getPlayerData().hunger / (double) Player.getPlayerData().ogEndurance * 100) > 75)
				g.setColor(Color.green);
			else if (Math.round(
					(double) Player.getPlayerData().hunger / (double) Player.getPlayerData().ogEndurance * 100) > 25)
				g.setColor(Color.YELLOW);
			else
				g.setColor(Color.RED);

			g.fillRect(c.getWidth() - 120 + 10, 60, 10, 
					(int) Math.round(
							(double) Player.getPlayerData().hunger / (double) Player.getPlayerData().ogEndurance * 100));

			if (Math.round(
					(double) Player.getPlayerData().thirst / (double) Player.getPlayerData().ogEndurance * 100) > 75)
				g.setColor(Color.green);
			else if (Math.round(
					(double) Player.getPlayerData().thirst / (double) Player.getPlayerData().ogEndurance * 100) > 25)
				g.setColor(Color.YELLOW);
			else
				g.setColor(Color.RED);

			g.fillRect(c.getWidth() - 90 + 10, 60, 10, 
					(int) Math.round(
							(double) Player.getPlayerData().thirst / (double) Player.getPlayerData().ogEndurance * 100));

			if (Math.round(
					(double) Player.getPlayerData().energy / (double) Player.getPlayerData().ogEndurance * 100) > 75)
				g.setColor(Color.green);
			else if (Math.round(
					(double) Player.getPlayerData().energy / (double) Player.getPlayerData().ogEndurance * 100) > 25)
				g.setColor(Color.YELLOW);
			else
				g.setColor(Color.RED);

			g.fillRect(c.getWidth() - 60 + 10, 60, 10,
					(int) Math.round(
							(double) Player.getPlayerData().energy / (double) Player.getPlayerData().ogEndurance * 100));

			CustomTextWritter.drawString(g, "Day: " + dayNum, c.getWidth() - 230, 70, true,
					Color.YELLOW, Assets.font28);
			/*
			 * CustomTextWritter.drawString(g, "" + Math.round((double)
			 * Player.getPlayerData().getHealth() / (double) Player.getPlayerData().ogHealth
			 * 100) + "%", c.getWidth() - 60, 40, true, Color.WHITE, Assets.font28);
			 * CustomTextWritter.drawString(g, "" + Math.round( (double)
			 * Player.getPlayerData().hunger / (double) Player.getPlayerData().ogEndurance *
			 * 100) + "%", c.getWidth() - 60, 95, true, Color.WHITE, Assets.font28);
			 * CustomTextWritter.drawString(g, "" + Math.round( (double)
			 * Player.getPlayerData().thirst / (double) Player.getPlayerData().ogEndurance *
			 * 100) + "%", c.getWidth() - 60, 150, true, Color.WHITE, Assets.font28);
			 * CustomTextWritter.drawString(g, "" + Math.round( (double)
			 * Player.getPlayerData().energy / (double) Player.getPlayerData().ogEndurance *
			 * 100) + "%", c.getWidth() - 60, 205, true, Color.WHITE, Assets.font28);
			 * CustomTextWritter.drawString(g, "" + Player.getPlayerData().getResistance(),
			 * c.getWidth() - 60, 260, true, Color.WHITE, Assets.font28);
			 */
		}

		if (uiManager != null)

			uiManager.render(g);

		if (entityManager != null) {

			inventory.render(g);
			equipment.render(g);
			hands.render(g);
			
			if(handCraft != null)
				handCraft.render(g);

			if (isChestActive()) {
				chest.getInventory().render(g);
			//} else if (isWorkbenchActive()) {// July 15
			//	workbench.getCraft().render(g);
			//} else if (isSmithingTableActive()) {
			//	smithingTable.getCraft().render(g);
			} else if (isDisintegratorActive()) {
				disintegrator.getCraft().render(g);
			} else if (isAnalyzerActive()) {
				analyzer.getCraft().render(g);
			//} else if (isPurifierActive()) {
				//purifier.getCraft().render(g);
			} else if (isAutoCookerActive()) {
				autoCooker.getCraft().render(g);
			} else if (isAutoCookerV2Active()) {
				autoCookerV2.render(g);
			} else if (isMetallicOvenActive()) {
				metallicOven.getInventory().render(g);
			} else if (isResearchTableActive()) {
                researchTable.getCraft().render(g);
            }
			
			
			if (nextSentry) {
				nextSentry = false;
				//spawnSentry();
			}

			// render mouse cursor
			if (cursor != null)
				cursor.render(g);

		}
		
		//Show player experience
		if(entityManager != null) {
			CustomTextWritter.drawString(g, String.format("Survival XP: %d", Player.getPlayerData().getBasicSurvivalXP())
					, c.getWidth() - 100, c.getHeight() / 2 - 125, true, Color.WHITE, Assets.font16);
			CustomTextWritter.drawString(g, String.format("Combat XP: %d", Player.getPlayerData().getCombatXP())
					, c.getWidth() - 100, c.getHeight() / 2 - 100, true, Color.WHITE, Assets.font16);
			CustomTextWritter.drawString(g, String.format("Cooking XP: %d", Player.getPlayerData().getCookingXP())
					, c.getWidth() - 100, c.getHeight() / 2 - 75, true, Color.WHITE, Assets.font16);
			CustomTextWritter.drawString(g, String.format("Building XP: %d", Player.getPlayerData().getBuildingXP())
					, c.getWidth() - 100, c.getHeight() / 2 - 50, true, Color.WHITE, Assets.font16);
		}

		//Show time left for loading the topper
		if(loading) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, c.getWidth(), c.getHeight());
			g.drawImage(background.getCurrentFrame(), 0, 0, c.getWidth(), c.getHeight(), null);

			CustomTextWritter.drawString(g, "Loading World", c.getWidth() / 2, c.getHeight() / 2 - 70, true,
					Color.WHITE, Assets.font28);

			g.setColor(Color.GRAY); //can make black or gray - both look fine
			g.fillRect(c.getWidth() / 2 - 350 - 5, c.getHeight() / 2 - 5, 710, 20);

			g.setColor(Color.GREEN);
			g.fillRect(c.getWidth() / 2 - 350, c.getHeight() / 2, (int) (((double)(amountLoaded) / (double) (width * height)) * 700), 10);
		}
		
		//testing player coords
		if(entityManager != null && debugMode) {
			CustomTextWritter.drawString(g, String.format("X : %d", (int) Player.getPlayerData().getX()), 100, 10, true,
					Color.WHITE, Assets.font28);
			CustomTextWritter.drawString(g, String.format("Y : %d", (int) Player.getPlayerData().getY()), 100, 40, true,
					Color.WHITE, Assets.font28);

			int playerTileX = (int) (Player.getPlayerData().getX() -
					Player.getPlayerData().getX() % Tile.TILEWIDTH) / Tile.TILEWIDTH;
			int playerTileY = (int) (Player.getPlayerData().getY() -
					Player.getPlayerData().getY() % Tile.TILEWIDTH) / Tile.TILEHEIGHT;

			//show the coords of the tile the player is on
			CustomTextWritter.drawString(g, String.format("Tile X : %d", playerTileX), 100, 70, true,
					Color.WHITE, Assets.font28);
			CustomTextWritter.drawString(g, String.format("Tile Y : %d", playerTileY), 100, 100, true,
					Color.WHITE, Assets.font28);
			
			CustomTextWritter.drawString(g, String.format("Time : %d", (int)time), 100, 190, true, Color.WHITE, Assets.font28);
			
			/*
			//test coords of mouse tile
			int mouseTileX = (int) ((c.getMouseManager().mouseBound().getX() + c.getGameCamera().getxOffset()) -
					(c.getMouseManager().mouseBound().getX() + c.getGameCamera().getxOffset()) % Tile.TILEWIDTH) / Tile.TILEWIDTH;
			int mouseTileY = (int) ((c.getMouseManager().mouseBound().getY() + c.getGameCamera().getyOffset()) -
					(c.getMouseManager().mouseBound().getY() + c.getGameCamera().getyOffset()) % Tile.TILEWIDTH) / Tile.TILEHEIGHT;
			CustomTextWritter.drawString(g, String.format("Mouse X : %d", mouseTileX), 100, 130, true,
					Color.WHITE, Assets.font28);
			CustomTextWritter.drawString(g, String.format("Mouse Y : %d", mouseTileY), 100, 160, true,
					Color.WHITE, Assets.font28);
			 */
			
			//show tile outline for mouse
			int buildX = (int) ((c.getMouseManager().mouseBound().x -
					(c.getGameCamera().getxOffset() % Tile.TILEWIDTH)) -
					c.getMouseManager().mouseBound().x % Tile.TILEWIDTH);
			int buildY = (int) ((c.getMouseManager().mouseBound().y - 
					(c.getGameCamera().getyOffset() % Tile.TILEHEIGHT)) - 
					c.getMouseManager().mouseBound().y % Tile.TILEHEIGHT);
			Rectangle rect = new Rectangle(buildX, buildY, Tile.TILEWIDTH, Tile.TILEHEIGHT);
			g.setColor(Color.WHITE);
			g2d.draw(rect);
			
			buildX = (int) ((c.getMouseManager().mouseBound().x -
					(c.getGameCamera().getxOffset() % Tile.TILEWIDTH) -
					c.getMouseManager().mouseBound().x % Tile.TILEWIDTH) + 
					c.getGameCamera().getxOffset());
			buildY = (int) ((c.getMouseManager().mouseBound().y - 
					(c.getGameCamera().getyOffset() % Tile.TILEHEIGHT) - 
					c.getMouseManager().mouseBound().y % Tile.TILEHEIGHT) + 
					c.getGameCamera().getyOffset());
			CustomTextWritter.drawString(g, String.format("Topper : %d", topper[buildX / 64][buildY / 64]), 100, 130, true,
					Color.WHITE, Assets.font28);
			CustomTextWritter.drawString(g, String.format("Floor : %d", floor[buildX / 64][buildY / 64]), 100, 160, true,
					Color.WHITE, Assets.font28);
			CustomTextWritter.drawString(g, String.format("Current Spd : %.2f", Player.getPlayerData().getCurrentSpeed()), 100, 220, true,
					Color.WHITE, Assets.font28);
			CustomTextWritter.drawString(g, String.format("Extra Spd : %.2f", Player.getPlayerData().extraSpeed), 100, 250, true,
					Color.WHITE, Assets.font28);
		}

	}

	private void buildTimer() {
		buildTimer += System.currentTimeMillis() - lastBuildTimer;
		lastBuildTimer = System.currentTimeMillis();

		if (buildTimer < buildCooldown)
			return;

		if (hands.getHand() != null && hands.getHand().getType().equals("structure")) {

			boolean canBuild = true;

			int buildX = (int) ((c.getMouseManager().mouseBound().x -
					(c.getGameCamera().getxOffset() % Tile.TILEWIDTH)) -
					c.getMouseManager().mouseBound().x % Tile.TILEWIDTH);
			int buildY = (int) ((c.getMouseManager().mouseBound().y - 
					(c.getGameCamera().getyOffset() % Tile.TILEHEIGHT)) - 
					c.getMouseManager().mouseBound().y % Tile.TILEHEIGHT);

			Rectangle rect = new Rectangle(buildX, buildY, Tile.TILEWIDTH, Tile.TILEHEIGHT);

			for(int i = 0; i < entityManager.entitiesInBound.size(); i++) {
				if(rect.intersects(entityManager.entitiesInBound.get(i).getBounds()))
					canBuild = false;
			}

			//prevent the player from building outside of building range (about 3 tiles)
			if(Math.abs(buildX - (Player.getPlayerData().getXLoc() + Player.getPlayerData().getBounds().getWidth())) > Tile.TILEWIDTH * 3 || 
					Math.abs(buildY - (Player.getPlayerData().getYLoc() + Player.getPlayerData().getBounds().getHeight())) > Tile.TILEHEIGHT * 3 )
				canBuild = false;


			buildX = (int) ((c.getMouseManager().mouseBound().x -
					(c.getGameCamera().getxOffset() % Tile.TILEWIDTH) -
					c.getMouseManager().mouseBound().x % Tile.TILEWIDTH) + 
					c.getGameCamera().getxOffset());
			buildY = (int) ((c.getMouseManager().mouseBound().y - 
					(c.getGameCamera().getyOffset() % Tile.TILEHEIGHT) - 
					c.getMouseManager().mouseBound().y % Tile.TILEHEIGHT) + 
					c.getGameCamera().getyOffset());

			if (c.getMouseManager().isLeftPressed()) {

				if (topper[buildX / 64][buildY / 64] == 0 && canBuild) {
					
					//add different entities for different items
					if (hands.getHand().getId() == 700) { //pine sap
						entityManager.addEntity(new PineSap(buildX, 
								buildY, c));
						topper[buildX / 64][buildY / 64] = 51;
					}

					inventory.removeItem(hands.getHand());

				}
			}

		}

		buildTimer = 0; // cool down resets
	}

	private void structureBuildMode() {
		
		//PRESS ESC TO CANCEL
		if (c.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)) {
			structureCrafted = false;
		}

		//Player.getPlayerData().setCanMove(false); //prevent player from moving

		boolean canBuild = true;

		int buildX = (int) ((c.getMouseManager().mouseBound().x -
				(c.getGameCamera().getxOffset() % Tile.TILEWIDTH)) -
				c.getMouseManager().mouseBound().x % Tile.TILEWIDTH);
		int buildY = (int) ((c.getMouseManager().mouseBound().y - 
				(c.getGameCamera().getyOffset() % Tile.TILEHEIGHT)) - 
				c.getMouseManager().mouseBound().y % Tile.TILEHEIGHT);

		Rectangle rect = new Rectangle(buildX, buildY, Tile.TILEWIDTH, Tile.TILEHEIGHT);

		for(int i = 0; i < entityManager.entitiesInBound.size(); i++) {
			if(rect.intersects(entityManager.entitiesInBound.get(i).getBounds()))
				canBuild = false;
		}

		//prevent the player from building outside of building range (about 3 tiles)
		if(Math.abs(buildX - (Player.getPlayerData().getXLoc() + Player.getPlayerData().getBounds().getWidth())) > Tile.TILEWIDTH * 3 || 
				Math.abs(buildY - (Player.getPlayerData().getYLoc() + Player.getPlayerData().getBounds().getHeight())) > Tile.TILEHEIGHT * 3 )
			canBuild = false;

		buildX = (int) ((c.getMouseManager().mouseBound().x -
				(c.getGameCamera().getxOffset() % Tile.TILEWIDTH) -
				c.getMouseManager().mouseBound().x % Tile.TILEWIDTH) + 
				c.getGameCamera().getxOffset());
		buildY = (int) ((c.getMouseManager().mouseBound().y - 
				(c.getGameCamera().getyOffset() % Tile.TILEHEIGHT) - 
				c.getMouseManager().mouseBound().y % Tile.TILEHEIGHT) + 
				c.getGameCamera().getyOffset());
		
		if(tiles[buildX / 64][buildY / 64] == 10)
			canBuild = false;

		if (c.getKeyManager().keyJustPressed(KeyEvent.VK_UP) || c.getKeyManager().keyJustPressed(KeyEvent.VK_R)) { //for rotating walls

			wallRotateTimer += System.currentTimeMillis() - lastWallRotateTimer;
			lastWallRotateTimer = System.currentTimeMillis();

			if (!(wallRotateTimer < wallRotateCooldown)) {
				wallRotateTimer = 0;
				if(wallNum >= 3)
					wallNum = 0;
				else
					wallNum++;
			}

		}

		if (c.getMouseManager().isLeftPressed()) {

			if (topper[buildX / 64][buildY / 64] == 0 && canBuild) {

				int wallType = 0;

				if (structureCrafted){
					wallType = getStructureRecipe().getWallType();
				}
				
				AudioPlayer.playAudio("audio/build.wav");
				
				if(structureNum == 1) {
					entityManager.addEntity(new Workbench(buildX, buildY, c));
				}else if(structureNum == 2) {
					entityManager.addEntity(new SmithingTable(buildX, buildY, c));
				}else if(structureNum == 3) {
					entityManager.addEntity(new Purifier(buildX, buildY, c));
				}else if(structureNum == 4) {
					entityManager.addEntity(new ResearchTable(buildX, buildY, c));
				}else if(structureNum == 5) {
					entityManager.addEntity(new MetallicOven(buildX, buildY, c));
				}else if(structureNum == 6) {
					entityManager.addEntity(new AutoCooker(buildX, buildY, c));
				}else if(structureNum == 7) {
					entityManager.addEntity(new Disintegrator(buildX, buildY, c));
				}else if(structureNum == 9 || structureNum == 11|| structureNum == 13) { //wood wall / stone wall / metal wall

					if(wallNum == 0) { //top
						entityManager.addEntity(new WallUp(buildX, buildY, wallType, c));
						
						//1st 3 digits are structure num and last is wall type (ex. 201 = wall up, 1 = wood)
						if(wallType == 1) //wood
							topper[buildX / 64][buildY / 64] = 2011;
						else if(wallType == 2) //stone
							topper[buildX / 64][buildY / 64] = 2012;
						else if(wallType == 3) //metal
							topper[buildX / 64][buildY / 64] = 2013;
						
					}else if(wallNum == 1) { //right
						entityManager.addEntity(new WallRight(buildX, buildY, wallType, c));
						
						if(wallType == 1) //wood
							topper[buildX / 64][buildY / 64] = 2021;
						else if(wallType == 2) //stone
							topper[buildX / 64][buildY / 64] = 2022;
						else if(wallType == 3) //metal
							topper[buildX / 64][buildY / 64] = 2023;
						
					}else if(wallNum == 2) { //bottom
						entityManager.addEntity(new WallDown(buildX, buildY, wallType, c));
						
						if(wallType == 1) //wood
							topper[buildX / 64][buildY / 64] = 2031;
						else if(wallType == 2) //stone
							topper[buildX / 64][buildY / 64] = 2032;
						else if(wallType == 3) //metal
							topper[buildX / 64][buildY / 64] = 2033;
						
					}else if(wallNum == 3) { //left
						entityManager.addEntity(new WallLeft(buildX, buildY, wallType, c));
						
						if(wallType == 1) //wood
							topper[buildX / 64][buildY / 64] = 2041;
						else if(wallType == 2) //stone
							topper[buildX / 64][buildY / 64] = 2042;
						else if(wallType == 3) //metal
							topper[buildX / 64][buildY / 64] = 2043;
						
					}

				}else if(structureNum == 10 || structureNum == 12 || structureNum == 14) { //wood gate / stone gate / metal gate

					if(wallNum == 0) { //top
						entityManager.addEntity(new GateUp(buildX, buildY, wallType, c));
						
						if(wallType == 1) //wood
							topper[buildX / 64][buildY / 64] = 2051;
						else if(wallType == 2) //stone
							topper[buildX / 64][buildY / 64] = 2052;
						else if(wallType == 3) //metal
							topper[buildX / 64][buildY / 64] = 2053;
						
					}else if(wallNum == 1) { //right
						entityManager.addEntity(new GateRight(buildX, buildY, wallType, c));
						
						if(wallType == 1) //wood
							topper[buildX / 64][buildY / 64] = 2061;
						else if(wallType == 2) //stone
							topper[buildX / 64][buildY / 64] = 2062;
						else if(wallType == 3) //metal
							topper[buildX / 64][buildY / 64] = 2063;
						
					}else if(wallNum == 2) { //bottom
						entityManager.addEntity(new GateDown(buildX, buildY, wallType, c));
						
						if(wallType == 1) //wood
							topper[buildX / 64][buildY / 64] = 2071;
						else if(wallType == 2) //stone
							topper[buildX / 64][buildY / 64] = 2072;
						else if(wallType == 3) //metal
							topper[buildX / 64][buildY / 64] = 2073;
						
					}else if(wallNum == 3) { //left
						entityManager.addEntity(new GateLeft(buildX, buildY, wallType, c));
						
						if(wallType == 1) //wood
							topper[buildX / 64][buildY / 64] = 2081;
						else if(wallType == 2) //stone
							topper[buildX / 64][buildY / 64] = 2082;
						else if(wallType == 3) //metal
							topper[buildX / 64][buildY / 64] = 2083;
						
					}

				}else if(structureNum == 15) {
					CampFire campfire = new CampFire(buildX, buildY, c);
					entityManager.addEntity(campfire);

					addLight(campfire, 5, buildX/64, buildY/64, 3);

				} else if(structureNum == 16) {
					PowerGenerator powerGenerator = new PowerGenerator(buildX, buildY, c);
					entityManager.addEntity(powerGenerator);

					addLight(powerGenerator, 3, buildX/64, buildY/64, 3);
					addPower(powerGenerator, 7, buildX/64, buildY/64);

				} else if(structureNum == 17) {
					LampPost lamp = new LampPost(buildX, buildY, c);
					lamp.placex = buildX/64;
					lamp.placey = buildY/64;
					entityManager.addEntity(lamp);

				} else if(structureNum == 18) {
					PowerAdaptor adaptor = new PowerAdaptor(buildX, buildY, c);
					adaptor.placex = buildX/64;
					adaptor.placey = buildY/64;
					entityManager.addEntity(adaptor);

				} else if(structureNum == 19) {
					HeavyPulseArtillery adaptor = new HeavyPulseArtillery(buildX, buildY, c);
					adaptor.placex = buildX/64;
					adaptor.placey = buildY/64;
					entityManager.addEntity(adaptor);

				} else if(structureNum == 20) {
					RapidPulseArtillery adaptor = new RapidPulseArtillery(buildX, buildY, c);
					adaptor.placex = buildX/64;
					adaptor.placey = buildY/64;
					entityManager.addEntity(adaptor);

				}  else if(structureNum == 21) {
					Purifier adaptor = new Purifier(buildX, buildY, c);
					adaptor.placex = buildX/64;
					adaptor.placey = buildY/64;
					entityManager.addEntity(adaptor);

				} else if(structureNum == 22) {
					MetalContainer adaptor = new MetalContainer(buildX, buildY, c);
					adaptor.placex = buildX/64;
					adaptor.placey = buildY/64;
					entityManager.addEntity(adaptor);

				} else if(structureNum == 23) {
					MetalChest adaptor = new MetalChest(buildX, buildY, c);
					adaptor.placex = buildX/64;
					adaptor.placey = buildY/64;
					entityManager.addEntity(adaptor);

				} else if(structureNum == 24) {
					Tent adaptor = new Tent(buildX, buildY, c);
					adaptor.placex = buildX/64;
					adaptor.placey = buildY/64;
					entityManager.addEntity(adaptor);
					
					System.out.println("building a tent");
				} else if(structureNum == 51) {
					floor[buildX / 64][buildY / 64] = 1;
				} else if(structureNum == 52) {
					floor[buildX / 64][buildY / 64] = 2;
				} else if(structureNum == 53) {
					floor[buildX / 64][buildY / 64] = 3;
				} else if(structureNum == 54) {
					floor[buildX / 64][buildY / 64] = 4;
				}

				if(!(structureNum == 0 || structureNum == 51 || structureNum == 52 || structureNum == 53 || 
						structureNum == 9 || structureNum == 11 || structureNum == 13 ||
						structureNum == 10 || structureNum == 12 || structureNum == 14))
					topper[buildX / 64][buildY / 64] = 100 + structureNum; //structure numbers are 100 - 106

				if (structureCrafted) {
					
					for (Entry<Integer, Integer> entry : structureRecipe.recipeRequirements.entrySet()) {
						for (int i = 0; i < entry.getValue(); i++) {
							Player.getPlayerData().getInventory().removeItem(Item.items[entry.getKey()]);
						}
					}

				}

				wallNum = 0;
				structureNum = 0;
				structureCrafted = false;
				structureWorkbenchCrafted = false;
				currentlyBuildingStructure = true;
				Player.getPlayerData().setCanMove(false); //prevent player from moving
				lastStructureTimer = System.currentTimeMillis();

				//Set the smoke location
				smokeX = (int) ((c.getMouseManager().mouseBound().x -
						(c.getGameCamera().getxOffset() % Tile.TILEWIDTH)) -
						c.getMouseManager().mouseBound().x % Tile.TILEWIDTH);
				smokeY = (int) ((c.getMouseManager().mouseBound().y - 
						(c.getGameCamera().getyOffset() % Tile.TILEHEIGHT)) - 
						c.getMouseManager().mouseBound().y % Tile.TILEHEIGHT);

			} else if(structureNum == 51 || structureNum == 52 || structureNum == 53 || structureNum == 54 && 
					!(tiles[buildX / 64][buildY / 64] == 10)) {
				//System.out.println("floor built");
				//System.out.println(structureNum);
				if(!(Math.abs(buildX - (Player.getPlayerData().getXLoc() + Player.getPlayerData().getBounds().getWidth())) > Tile.TILEWIDTH * 3 && 
						Math.abs(buildY - (Player.getPlayerData().getYLoc() + Player.getPlayerData().getBounds().getHeight())) > Tile.TILEHEIGHT * 3 )) {
					
					//System.out.println("building");
					if(structureNum == 51) {
						floor[buildX / 64][buildY / 64] = 1;
					} else if(structureNum == 52) {
						floor[buildX / 64][buildY / 64] = 2;
					} else if(structureNum == 53) {
						floor[buildX / 64][buildY / 64] = 3;
					} else if(structureNum == 54) {
						floor[buildX / 64][buildY / 64] = 4;
					}

					if (structureCrafted) {
						
						for (Entry<Integer, Integer> entry : structureRecipe.recipeRequirements.entrySet()) {
							for (int i = 0; i < entry.getValue(); i++) {
								//System.out.println("removing");
								Player.getPlayerData().getInventory().removeItem(Item.items[entry.getKey()]);
							}

						}

					}

					wallNum = 0;
					structureNum = 0;
					structureCrafted = false;
					currentlyBuildingStructure = true;
					Player.getPlayerData().setCanMove(false); //prevent player from moving
					lastStructureTimer = System.currentTimeMillis();

					//Set the smoke location
					smokeX = (int) ((c.getMouseManager().mouseBound().x -
							(c.getGameCamera().getxOffset() % Tile.TILEWIDTH)) -
							c.getMouseManager().mouseBound().x % Tile.TILEWIDTH);
					smokeY = (int) ((c.getMouseManager().mouseBound().y - 
							(c.getGameCamera().getyOffset() % Tile.TILEHEIGHT)) - 
							c.getMouseManager().mouseBound().y % Tile.TILEHEIGHT);
				}

			}

		}

	}

	public void addLight(StaticEntity s, int radius, int x, int y, int lumen) {

		s.placex = x;
		s.placey = y;
		int temp = radius/2;
		int additional = 0;
		if(radius == 5)
			additional = 2;
		else if(radius == 7)
			additional = 4;
		else if(radius == 9)
			additional = 6;

		for(int i = Math.max(y-temp-radius+ additional, 0); i <= Math.min(y-temp+1, lightMap[0].length); i++) {
			for(int j = Math.max(x - temp, 0); j <= Math.min(x + temp, lightMap.length); j++) {
				lightMap[j][i] += lumen;
			}
			temp++;
		}
		for(int i = Math.max(y-radius/2, 0); i <= Math.min(y+radius/2, lightMap[0].length); i++) {
			for(int j = Math.max(x - temp, 0); j <= Math.min(x + temp, lightMap.length); j++) {
				lightMap[j][i] += lumen;
			}
		}
		temp--;
		for(int i = Math.max(y+radius/2+1, 0); i <= Math.min(y+radius/2 + radius - additional, lightMap[0].length); i++) {
			for(int j = Math.max(x - temp, 0); j <= Math.min(x + temp, lightMap.length); j++) {
				lightMap[j][i] += lumen;
			}
			temp--;
		}
	}

	public void removeLight(int radius, int x, int y, int lumen) {
		int additional = 0;
		if(radius == 5)
			additional = 2;
		else if(radius == 7)
			additional = 4;
		else if(radius == 9)
			additional = 6;
		int temp = radius/2;
		for(int i = Math.max(y-temp-radius+ additional, 0); i <= Math.min(y-temp+1, lightMap[0].length); i++) {
			for(int j = Math.max(x - temp, 0); j <= Math.min(x + temp, lightMap.length); j++) {
				lightMap[j][i] -= lumen;
				if(lightMap[j][i] < 0)
					lightMap[j][i] = 0;
			}
			temp++;
		}
		for(int i = Math.max(y-radius/2, 0); i <= Math.min(y+radius/2, lightMap[0].length); i++) {
			for(int j = Math.max(x - temp, 0); j <= Math.min(x + temp, lightMap.length); j++) {
				lightMap[j][i] -= lumen;
				if(lightMap[j][i] < 0)
					lightMap[j][i] = 0;
			}
		}
		temp--;
		for(int i = Math.max(y+radius/2+1, 0); i <= Math.min(y+radius/2 + radius - additional, lightMap[0].length); i++) {
			for(int j = Math.max(x - temp, 0); j <= Math.min(x + temp, lightMap.length); j++) {
				lightMap[j][i] -= lumen;
				if(lightMap[j][i] < 0)
					lightMap[j][i] = 0;
			}
			temp--;
		}
	}

	public void addPower(StaticEntity s, int radius, int x, int y) {

		s.placex = x;
		s.placey = y;
		int temp = radius/2;
		int additional = 0;
		if(radius == 5)
			additional = 2;
		else if(radius == 7)
			additional = 4;
		else if(radius == 9)
			additional = 6;
		for(int i = Math.max(y-temp-radius+ additional, 0); i <= Math.min(y-temp+1, powerMap[0].length); i++) {
			for(int j = Math.max(x - temp, 0); j <= Math.min(x + temp, powerMap.length); j++) {
				powerMap[j][i]++;
			}
			temp++;
		}
		for(int i = Math.max(y-radius/2, 0); i <= Math.min(y+radius/2, powerMap[0].length); i++) {
			for(int j = Math.max(x - temp, 0); j <= Math.min(x + temp, powerMap.length); j++) {
				powerMap[j][i]++;
			}
		}
		temp--;
		for(int i = Math.max(y+radius/2+1, 0); i <= Math.min(y+radius/2 + radius - additional, powerMap[0].length); i++) {
			for(int j = Math.max(x - temp, 0); j <= Math.min(x + temp, powerMap.length); j++) {
				powerMap[j][i]++;
			}
			temp--;
		}
	}

	public void removePower(int radius, int x, int y) {
		int additional = 0;
		if(radius == 5)
			additional = 2;
		else if(radius == 7)
			additional = 4;
		else if(radius == 9)
			additional = 6;
		int temp = radius/2;
		for(int i = Math.max(y-temp-radius+ additional, 0); i <= Math.min(y-temp+1, powerMap[0].length); i++) {
			for(int j = Math.max(x - temp, 0); j <= Math.min(x + temp, powerMap.length); j++) {
				powerMap[j][i]--;
			}
			temp++;
		}
		for(int i = Math.max(y-radius/2, 0); i <= Math.min(y+radius/2, powerMap[0].length); i++) {
			for(int j = Math.max(x - temp, 0); j <= Math.min(x + temp, powerMap.length); j++) {
				powerMap[j][i]--;
			}
		}
		temp--;
		for(int i = Math.max(y+radius/2+1, 0); i <= Math.min(y+radius/2 + radius - additional, powerMap[0].length); i++) {
			for(int j = Math.max(x - temp, 0); j <= Math.min(x + temp, powerMap.length); j++) {
				powerMap[j][i]--;
			}
			temp--;
		}
	}

	//this method builds the structure after the player has confirmed the location
	private void structureBuildTimer() {

		Player.getPlayerData().setCanMove(false); //prevent player from moving

		structureTimer += System.currentTimeMillis() - lastStructureTimer;
		lastStructureTimer = System.currentTimeMillis();

		if (structureTimer < structureCooldown)
			return;

		Player.getPlayerData().setCanMove(true);
		currentlyBuildingStructure = false;

		currentlyBuildingStructure = false; 
		structureTimer = 0; // cool down resets

	}

	private void interactTimer() {
		interactTimer += System.currentTimeMillis() - lastInteractTimer;
		lastInteractTimer = System.currentTimeMillis();

		if (interactTimer < interactCooldown || inMenu())
			return;

		if (c.getMouseManager().isRightPressed()) {

			int mouseTileX = (int) ((c.getMouseManager().mouseBound().getX() + c.getGameCamera().getxOffset()) -
					(c.getMouseManager().mouseBound().getX() + c.getGameCamera().getxOffset()) % Tile.TILEWIDTH) / Tile.TILEWIDTH;
			int mouseTileY = (int) ((c.getMouseManager().mouseBound().getY() + c.getGameCamera().getyOffset()) -
					(c.getMouseManager().mouseBound().getY() + c.getGameCamera().getyOffset()) % Tile.TILEWIDTH) / Tile.TILEHEIGHT;

			if(Player.getPlayerData().getHands().getHand() != null &&
					Player.getPlayerData().getHands().getHand().getType().equals("water container") &&
					tiles[mouseTileX][mouseTileY] == 10) {

				Item item = Player.getPlayerData().getHands().getHand();

				if(item instanceof WaterContainer) {
					Player.getPlayerData().filling = true;
					((WaterContainer) item).setCurrentCapacity(((WaterContainer) item).getCurrentCapacity()+4);
					if(((WaterContainer) item).getCurrentCapacity() > ((WaterContainer) item).getCapacity()) {
						((WaterContainer) item).setCurrentCapacity(((WaterContainer) item).getCapacity());
					}
					((WaterContainer) item).setPurified(false);
				} else
					Player.getPlayerData().filling = false;

			} else {
				Player.getPlayerData().filling = false;
				int buildX = (int) ((c.getMouseManager().mouseBound().x -
						(c.getGameCamera().getxOffset() % Tile.TILEWIDTH)) -
						c.getMouseManager().mouseBound().x % Tile.TILEWIDTH);
				int buildY = (int) ((c.getMouseManager().mouseBound().y - 
						(c.getGameCamera().getyOffset() % Tile.TILEHEIGHT)) - 
						c.getMouseManager().mouseBound().y % Tile.TILEHEIGHT);

				if(Math.abs(buildX - (Player.getPlayerData().getXLoc() + Player.getPlayerData().getBounds().getWidth())) <= Tile.TILEWIDTH * 3 && 
						Math.abs(buildY - (Player.getPlayerData().getYLoc() + Player.getPlayerData().getBounds().getHeight())) <= Tile.TILEHEIGHT * 3 )
					cursor.interact();
			}

		} else if (c.getMouseManager().isLeftPressed() && c.getKeyManager().shift) { //allow player to drink from nearby water blocks

			int mouseTileX = (int) ((c.getMouseManager().mouseBound().getX() + c.getGameCamera().getxOffset()) -
					(c.getMouseManager().mouseBound().getX() + c.getGameCamera().getxOffset()) % Tile.TILEWIDTH) / Tile.TILEWIDTH;
			int mouseTileY = (int) ((c.getMouseManager().mouseBound().getY() + c.getGameCamera().getyOffset()) -
					(c.getMouseManager().mouseBound().getY() + c.getGameCamera().getyOffset()) % Tile.TILEWIDTH) / Tile.TILEHEIGHT;

			int buildX = (int) ((c.getMouseManager().mouseBound().x -
					(c.getGameCamera().getxOffset() % Tile.TILEWIDTH)) -
					c.getMouseManager().mouseBound().x % Tile.TILEWIDTH);
			int buildY = (int) ((c.getMouseManager().mouseBound().y - 
					(c.getGameCamera().getyOffset() % Tile.TILEHEIGHT)) - 
					c.getMouseManager().mouseBound().y % Tile.TILEHEIGHT);

			if(tiles[mouseTileX][mouseTileY] == 10) {

				if(Math.abs(buildX - (Player.getPlayerData().getXLoc() + Player.getPlayerData().getBounds().getWidth())) <= Tile.TILEWIDTH * 3 && 
						Math.abs(buildY - (Player.getPlayerData().getYLoc() + Player.getPlayerData().getBounds().getHeight())) <= Tile.TILEHEIGHT * 3 ) {
					
					Player.getPlayerData().eatTimer += System.currentTimeMillis() - Player.getPlayerData().lastEatTimer;
					Player.getPlayerData().lastEatTimer = System.currentTimeMillis();
					Player.getPlayerData().drinkAudioTimer += System.currentTimeMillis() - Player.getPlayerData().lastdrinkAudioTimer;
					Player.getPlayerData().lastdrinkAudioTimer = System.currentTimeMillis();

					if (Player.getPlayerData().eatTimer > Player.getPlayerData().drinkCooldown) {
					
						if (Player.getPlayerData().drinkAudioTimer > Player.getPlayerData().drinkAudioCooldown) {
							AudioPlayer.playAudio("audio/drink.wav");
							if(CT.random(1, 7) == 6) {
								EffectManager.addEffect(new Effect("contamination", CT.random(10000, 100000)));
								if(CT.random(1, 2) == 1)
									MessageBox.addMessage("you drank something weird...");
								else 
									MessageBox.addMessage("that tasted ill...");
							}
							if(CT.random(1, 15) == 1) {
								EffectManager.addEffect(new Effect("poison", CT.random(5000, 15000)));
								if(CT.random(1, 2) == 1)
									MessageBox.addMessage("you dont feel so good...");
								else 
									MessageBox.addMessage("you tasted germs...");
							}
							Player.getPlayerData().drinkAudioTimer = 0;
						}
						
						Player.getPlayerData().thirst += 5;
	
						if(Player.getPlayerData().thirst > Player.getPlayerData().ogEndurance)
							Player.getPlayerData().thirst = Player.getPlayerData().ogEndurance;
						
						Player.getPlayerData().eatTimer = 0;
					}
				} 

			} 

		}

		interactTimer = 0; // cool down resets
	}
	/*
	public void spawnSentry() {

		int temp = r.nextInt(3);

		if (temp == 1) {

			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getEntityManager().addEntity(new Sentry(sentryHive.getX() + r.nextInt(100) + 50,
					sentryHive.getY() + r.nextInt(140), c));

		} else if (temp == 2) {

			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getEntityManager().addEntity(new Sentry(sentryHive.getX() + r.nextInt(100) + 150,
					sentryHive.getY() + r.nextInt(140), c));

		} else if (temp == 0) { // 1/3 chance of hive spawning sentry major

			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.getEntityManager().addEntity(new SentryMajor(sentryHive.getX() + r.nextInt(100) + 150,
					sentryHive.getY() + r.nextInt(140), c));

		}

	}
	*/
	//
	private void timeControl() {
		timeTimer += System.currentTimeMillis() - lastChangeTimer;
		lastChangeTimer = System.currentTimeMillis();

		if (timeTimer < timeCooldown)
			return;

		if (time >= 255*2) {
			time = 0;
		}
		//daytime is 310 to 40, pitch black 90 to 260
		if(time == 310) {
			dayNum++;
			Player.getPlayerData().setBasicSurvivalXP(Player.getPlayerData().getBasicSurvivalXP() + 
					Player.getPlayerData().getIntelligence() + CT.random(1, Player.getPlayerData().getIntelligence() + 1));
			MusicPlayer.playMusic("audio/ScorchedEarth.wav");
			MessageBox.addMessage("A new day has begun");
		}
		
		if(time == 40) {
			MessageBox.addMessage("The day is getting dark");
		}
		//Have a chance of starting a blood moon after day 5
		if(time == 90 && dayNum > 5) {
			int rand = r.nextInt(15);
			
			if(rand == 0) {
				fullMoon = true;
				MessageBox.addMessage("The moon is full tonight");
			}
		}
		
		//Full Moon ends at 305
		if(time == 260 && fullMoon)
			fullMoon = false;
		
		time++;

		if (time < 255) 
			dayBrightness+=3;
		else
			dayBrightness-=3;

		if(dayBrightness < 0)
			dayBrightness = 0;
		if(dayBrightness > 255)
			dayBrightness = 255;

		timeTimer = 0;

	}
	
	public void sleep() {
		
		if(isSleeping) {
			
			sleepTimer += System.currentTimeMillis() - lastsleepTimer;
			lastsleepTimer = System.currentTimeMillis();
			
			if (sleepTimer < sleepCooldown)
				return;
			
			if(!sleepFade) {
				sleepBrightness += 0.01f;
				if(sleepBrightness >= 1) {
					sleepBrightness = 1;
					sleepFade = true;
					sleepTimer = 0; 
					Player.getPlayerData().energy = Player.getPlayerData().ogEndurance;
					Player.getPlayerData().hunger -= Player.getPlayerData().hunger/4;
					Player.getPlayerData().thirst -= Player.getPlayerData().thirst/4;
					time = 310;
					Player.getPlayerData().setBasicSurvivalXP(Player.getPlayerData().getBasicSurvivalXP() + 
							Player.getPlayerData().getIntelligence() + CT.random(1, Player.getPlayerData().getIntelligence() + 1));
					BackgroundPlayer.playAudio("audio/ScorchedEarth.wav");
					if(Player.getPlayerData().getThirst() < Player.getPlayerData().ogEndurance/3 || Player.getPlayerData().getHunger() < Player.getPlayerData().ogEndurance/3)
						MessageBox.addMessage("You had a rough sleep");
					else
						MessageBox.addMessage("You had a good sleep");
					Player.getPlayerData().overnightFood();
					Player.getPlayerData().setCanMove(true);
					if(time <= 40 || time >= 310)
						dayNum++;
					if(time > 255) {
						dayBrightness = 255 - (time - 255)*3;
						if(dayBrightness < 0)
							dayBrightness = 0;
					}
					else {
						dayBrightness = (time)*2;
						if(dayBrightness > 255)
							dayBrightness = 255;
					}
					return;
				}
			} else {
				sleepBrightness -= 0.01f;
				if(sleepBrightness <= 0) {
					sleepBrightness = 0;
					sleepFade = false;
					isSleeping = false;
				}
			}
			
			sleepTimer = 0;
		}
		
	}

	// error checking method, if tile doesn't exist, then just display a grass tile
	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) {
			return Tile.dirtTile;
		}

		Tile t = Tile.tiles[tiles[x][y]];

		if (t == null)
			return Tile.dirtTile;

		return t;
	}

	// Reads the .txt file
	private void loadWorld(String path) {
		String file = WorldInput.loadFileAsString(path);
		String[] tokens = file.split("\\s+"); // split up every number into the string tokens array

		// first row of the file is the dimension of the world in tiles
		width = WorldInput.parseInt(tokens[0]);

		height = WorldInput.parseInt(tokens[1]);
		// second row of the file is the player spawn positions, will be implemented
		// later
		spawnx = WorldInput.parseInt(tokens[2]) * Tile.TILEWIDTH;
		spawny = WorldInput.parseInt(tokens[3]) * Tile.TILEHEIGHT;

		tiles = new int[width][height];
		checkedEntities = new int[width][height];
		floor = new int[width][height];
		current = new int[width][height];

		// puts the tiles inside a 2D array
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tiles[x][y] = WorldInput.parseInt(tokens[(x + y * width) + 4]);
			}
		}

		loadTerrain();

	}

	private void loadTerrain() {

		String file = WorldInput.loadFileAsString(String.format("terrain/%s", c.getMenuState().getWorldSelectState().getSelectedWorldName()));
		String[] tokens = file.split("\\s+"); // split up every number into the string tokens array

		terrain = new int[width][height];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				terrain[x][y] = WorldInput.parseInt(tokens[(x + y * width)]);
			}
		}

	}

	private void loadTopper() {

		loading = true;

		String file1 = WorldInput
				.loadFileAsString(String.format("platform/%s", c.getMenuState().getWorldSelectState().getSelectedWorldName()));
		String[] tokens1 = file1.split("\\s+"); // split up every number into the string tokens array

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				floor[x][y] = WorldInput.parseInt(tokens1[(x + y * width)]);
			}
		}

		String file = WorldInput
				.loadFileAsString(String.format("topper/%s", c.getMenuState().getWorldSelectState().getSelectedWorldName()));
		String[] tokens = file.split("\\s+"); // split up every number into the string tokens array

		topper = new int[width][height];
		
		String lightMapFile = WorldInput
				.loadFileAsString(String.format("lightMap/%s", c.getMenuState().getWorldSelectState().getSelectedWorldName()));
		String[] lightTokens = lightMapFile.split("\\s+"); // split up every number into the string tokens array
		
		String powerMapFile = WorldInput
				.loadFileAsString(String.format("powerMap/%s", c.getMenuState().getWorldSelectState().getSelectedWorldName()));
		String[] powerTokens = powerMapFile.split("\\s+"); // split up every number into the string tokens array
		
		lightMap = new int[width][height];
		powerMap = new int[width][height];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				topper[x][y] = WorldInput.parseInt(tokens[(x + y * width)]);
				lightMap[x][y] = WorldInput.parseInt(lightTokens[(x + y * width)]);
				powerMap[x][y] = WorldInput.parseInt(powerTokens[(x + y * width)]);
				amountLoaded++;
			}
		}

		loading = false;
	}
	
	private void loadTopperEntities() {

		int playerTileX = (int) (Player.getPlayerData().getX() -
				Player.getPlayerData().getX() % Tile.TILEWIDTH) / Tile.TILEWIDTH;
		int playerTileY = (int) (Player.getPlayerData().getY() -
				Player.getPlayerData().getY() % Tile.TILEWIDTH) / Tile.TILEHEIGHT;


		for (int y = playerTileY - 50; y < playerTileY + 51 && y < tiles.length; y++) {
			if(y < 0)
				continue;
			for (int x = playerTileX - 50; x < playerTileX + 51 && x < tiles.length; x++) {
				if(x < 0)
					continue;

				if (topper[x][y] == 1 && checkedEntities[x][y] == 0) {
					if(terrain[x][y] == 6) {
						entityManager
						.addEntity(new InfectedTree(x * Tile.TILEWIDTH - BurntTree.width / 2 + BurntTree.width / 5,
								y * Tile.TILEHEIGHT - BurntTree.height / 4 * 3, c));
					} else if(terrain[x][y] == 1) { 
						entityManager
						.addEntity(new PineTree(x * Tile.TILEWIDTH - PineTree.width / 2 + PineTree.width / 5,
								y * Tile.TILEHEIGHT - PineTree.height / 4 * 3, c));
					} else {
						entityManager
						.addEntity(new BurntTree(x * Tile.TILEWIDTH - BurntTree.width / 2 + BurntTree.width / 5,
								y * Tile.TILEHEIGHT - BurntTree.height / 4 * 3, c));
					}
					checkedEntities[x][y] = 1;
					//System.out.println("Added tree");
				} else if (topper[x][y] == 2 && checkedEntities[x][y] == 0) {
					entityManager.addEntity(
							new RockObstacle2(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, c));
					checkedEntities[x][y] = 2;
					//System.out.println("Added small rock");
				} else if (topper[x][y] == 3 && checkedEntities[x][y] == 0) { //large rock
					entityManager.addEntity(
							new RockObstacle(x * Tile.TILEWIDTH - RockObstacle.width / 2 + RockObstacle.width / 5,
									y * Tile.TILEHEIGHT - RockObstacle.height / 4 * 3, c));
					checkedEntities[x][y] = 3;
					//System.out.println("Added large rock");
				} else if (topper[x][y] == 4 && checkedEntities[x][y] == 0) { //cave
					entityManager.addEntity(
							new Cave(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, c));
					checkedEntities[x][y] = 4;
					//System.out.println("Added cave");
				} else if (topper[x][y] == 5 && checkedEntities[x][y] == 0) { //spine bush
					entityManager.addEntity(
							new SpineBush(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, c));
					checkedEntities[x][y] = 5;
					//System.out.println("Added spine bush");
				} else if (topper[x][y] == 6 && checkedEntities[x][y] == 0) { //living spike
					entityManager.addEntity(
							new LivingSpike(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, c));
					checkedEntities[x][y] = 6;
					//System.out.println("Added living spike");
				} else if (topper[x][y] == 7 && checkedEntities[x][y] == 0) { //hive
					entityManager.addEntity(
							new SentryHive(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, c));
					checkedEntities[x][y] = 7;
					//System.out.println("Added hive at: x = " + x + " y = " + y);
				} else if (topper[x][y] == 8 && checkedEntities[x][y] == 0) { //trash bag
					entityManager.addEntity(
							new TrashBag(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, c));
					checkedEntities[x][y] = 8;
					//System.out.println("Added trash bag");
				} else if (topper[x][y] == 9 && checkedEntities[x][y] == 0) { //bush
					entityManager.addEntity(
							new Bush(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, c));
					checkedEntities[x][y] = 9;
					//System.out.println("Added bush");
				} else if (topper[x][y] == 10 && checkedEntities[x][y] == 0) { //agave
					entityManager.addEntity(new Agave(x * Tile.TILEWIDTH - Tile.TILEWIDTH,
							y * Tile.TILEHEIGHT - Tile.TILEHEIGHT * 2, c));
					checkedEntities[x][y] = 10;
					//System.out.println("Added agave");
				} else if (topper[x][y] == 11 && checkedEntities[x][y] == 0) { //cactus
					entityManager.addEntity(new Cactus(x * Tile.TILEWIDTH - Cactus.width / 2 + Cactus.width / 5,
							y * Tile.TILEHEIGHT - Cactus.height / 4 * 3, c));
					checkedEntities[x][y] = 11;
				} else if (topper[x][y] == 12 && checkedEntities[x][y] == 0) { //giant stinger cactus
					entityManager.addEntity(new GiantStingerCactus(x * Tile.TILEWIDTH - GiantStingerCactus.width / 2 + GiantStingerCactus.width / 5,
							y * Tile.TILEHEIGHT - GiantStingerCactus.height / 4 * 3, c));
					checkedEntities[x][y] = 12;
				} else if (topper[x][y] == 13 && checkedEntities[x][y] == 0) { //vile embryo
					entityManager.addEntity(
							new VileEmbryo(x * Tile.TILEWIDTH, (y - 1) * Tile.TILEHEIGHT, c));
					checkedEntities[x][y] = 13;
				} else if (topper[x][y] == 15 && checkedEntities[x][y] == 0) { //ruin piece 2
					entityManager.addEntity(
							new RuinPiece2(x * Tile.TILEWIDTH, (y ) * Tile.TILEHEIGHT, c));
					checkedEntities[x][y] = 15;
				} else if (topper[x][y] == 16 && checkedEntities[x][y] == 0) { //ruin piece 3
					entityManager.addEntity(
							new RuinPiece3(x * Tile.TILEWIDTH, (y) * Tile.TILEHEIGHT, c));
					checkedEntities[x][y] = 16;
				} else if (topper[x][y] == 17 && checkedEntities[x][y] == 0) { //ruin piece 4
					entityManager.addEntity(
							new RuinPiece4(x * Tile.TILEWIDTH, (y) * Tile.TILEHEIGHT, c));
					checkedEntities[x][y] = 17;
				} else if (topper[x][y] == 18 && checkedEntities[x][y] == 0) { //ruin piece 5
					entityManager.addEntity(
							new RuinPiece5(x * Tile.TILEWIDTH, (y) * Tile.TILEHEIGHT, c));
					checkedEntities[x][y] = 18;
				} else if (topper[x][y] == 19 && checkedEntities[x][y] == 0) { //ruin piece 6
					entityManager.addEntity(
							new RuinPiece6(x * Tile.TILEWIDTH, (y) * Tile.TILEHEIGHT, c));
					checkedEntities[x][y] = 14;
				} else if (topper[x][y] == 20 && checkedEntities[x][y] == 0) { //chest
					entityManager.addEntity(
							new WoodenCrate(x * Tile.TILEWIDTH, (y) * Tile.TILEHEIGHT, c));
					checkedEntities[x][y] = 20;
				} else if (topper[x][y] == 21 && checkedEntities[x][y] == 0) { //space shuttle
					entityManager.addEntity(
							new SpaceShuttle(x * Tile.TILEWIDTH, (y) * Tile.TILEHEIGHT, c));
					checkedEntities[x][y] = 21;
				} else if (topper[x][y] == 22 && checkedEntities[x][y] == 0) { //space shuttle
					entityManager.addEntity(
							new SleepingSentinel((x - 1) * Tile.TILEWIDTH, (y - 4) * Tile.TILEHEIGHT, c));
					checkedEntities[x][y] = 0;
					topper[x][y] = 0; //change topper value to 0 because sleeping sentinel will be added to creature file
				}
				
				//Player made structures (topper values are 100 + structure num but walls are an exception)
				else if (topper[x][y] == 101 && checkedEntities[x][y] == 0) { 
					entityManager.addEntity(
							new Workbench(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, c));
					checkedEntities[x][y] = 101;
				} else if (topper[x][y] == 102 && checkedEntities[x][y] == 0) { 
					entityManager.addEntity(
							new SmithingTable(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, c));
					checkedEntities[x][y] = 102;
				} else if (topper[x][y] == 103 && checkedEntities[x][y] == 0) { 
					entityManager.addEntity(
							new Purifier(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, c));
					checkedEntities[x][y] = 103;
				} else if (topper[x][y] == 104 && checkedEntities[x][y] == 0) { 
					entityManager.addEntity(
							new ResearchTable(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, c));
					checkedEntities[x][y] = 104;
				} else if (topper[x][y] == 105 && checkedEntities[x][y] == 0) { 
					entityManager.addEntity(
							new MetallicOven(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, c));
					checkedEntities[x][y] = 104;
				} else if (topper[x][y] == 106 && checkedEntities[x][y] == 0) {
					entityManager.addEntity(
							new AutoCooker(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, c));
					checkedEntities[x][y] = 106;
				} else if (topper[x][y] == 107 && checkedEntities[x][y] == 0) { 
					entityManager.addEntity(
							new Disintegrator(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, c));
					checkedEntities[x][y] = 107;
				} 
				
				//Walls - 1st 3 digits are structure num and last is wall type (ex. 201 = wall up, 1 = wood)
				else if (topper[x][y] == 2011 && checkedEntities[x][y] == 0) { 
					entityManager.addEntity(
							new WallUp(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, 1, c));
					checkedEntities[x][y] = 2011;
				} else if (topper[x][y] == 2012 && checkedEntities[x][y] == 0) { 
					entityManager.addEntity(
							new WallUp(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, 2, c));
					checkedEntities[x][y] = 2012;
				} else if (topper[x][y] == 2013 && checkedEntities[x][y] == 0) { 
					entityManager.addEntity(
							new WallUp(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, 3, c));
					checkedEntities[x][y] = 2013;
				}
				
				else if (topper[x][y] == 2021 && checkedEntities[x][y] == 0) { 
					entityManager.addEntity(
							new WallRight(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, 1, c));
					checkedEntities[x][y] = 2021;
				} else if (topper[x][y] == 2022 && checkedEntities[x][y] == 0) { 
					entityManager.addEntity(
							new WallRight(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, 2, c));
					checkedEntities[x][y] = 2022;
				} else if (topper[x][y] == 2023 && checkedEntities[x][y] == 0) { 
					entityManager.addEntity(
							new WallRight(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, 3, c));
					checkedEntities[x][y] = 2023;
				}
				
				else if (topper[x][y] == 2031 && checkedEntities[x][y] == 0) { 
					entityManager.addEntity(
							new WallDown(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, 1, c));
					checkedEntities[x][y] = 2031;
				} else if (topper[x][y] == 2032 && checkedEntities[x][y] == 0) { 
					entityManager.addEntity(
							new WallDown(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, 2, c));
					checkedEntities[x][y] = 2032;
				} else if (topper[x][y] == 2033 && checkedEntities[x][y] == 0) { 
					entityManager.addEntity(
							new WallDown(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, 3, c));
					checkedEntities[x][y] = 2033;
				}
				
				else if (topper[x][y] == 2041 && checkedEntities[x][y] == 0) { 
					entityManager.addEntity(
							new WallLeft(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, 1, c));
					checkedEntities[x][y] = 2041;
				} else if (topper[x][y] == 2042 && checkedEntities[x][y] == 0) { 
					entityManager.addEntity(
							new WallLeft(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, 2, c));
					checkedEntities[x][y] = 2042;
				} else if (topper[x][y] == 2043 && checkedEntities[x][y] == 0) { 
					entityManager.addEntity(
							new WallLeft(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, 3, c));
					checkedEntities[x][y] = 2043;
				}
				
				//Gates
				else if (topper[x][y] == 2051 && checkedEntities[x][y] == 0) { 
					entityManager.addEntity(
							new GateUp(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, 1, c));
					checkedEntities[x][y] = 2051;
				} else if (topper[x][y] == 2052 && checkedEntities[x][y] == 0) { 
					entityManager.addEntity(
							new GateUp(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, 2, c));
					checkedEntities[x][y] = 2052;
				} else if (topper[x][y] == 2053 && checkedEntities[x][y] == 0) { 
					entityManager.addEntity(
							new GateUp(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, 3, c));
					checkedEntities[x][y] = 2053;
				}
				
				else if (topper[x][y] == 2061 && checkedEntities[x][y] == 0) { 
					entityManager.addEntity(
							new GateRight(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, 1, c));
					checkedEntities[x][y] = 2061;
				} else if (topper[x][y] == 2062 && checkedEntities[x][y] == 0) { 
					entityManager.addEntity(
							new GateRight(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, 2, c));
					checkedEntities[x][y] = 2062;
				} else if (topper[x][y] == 2063 && checkedEntities[x][y] == 0) { 
					entityManager.addEntity(
							new GateRight(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, 3, c));
					checkedEntities[x][y] = 2063;
				}
				
				else if (topper[x][y] == 2071 && checkedEntities[x][y] == 0) { 
					entityManager.addEntity(
							new GateDown(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, 1, c));
					checkedEntities[x][y] = 2071;
				} else if (topper[x][y] == 2072 && checkedEntities[x][y] == 0) { 
					entityManager.addEntity(
							new GateDown(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, 2, c));
					checkedEntities[x][y] = 2072;
				} else if (topper[x][y] == 2073 && checkedEntities[x][y] == 0) { 
					entityManager.addEntity(
							new GateDown(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, 3, c));
					checkedEntities[x][y] = 2073;
				}
				
				else if (topper[x][y] == 2081 && checkedEntities[x][y] == 0) { 
					entityManager.addEntity(
							new GateLeft(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, 1, c));
					checkedEntities[x][y] = 2081;
				} else if (topper[x][y] == 2082 && checkedEntities[x][y] == 0) { 
					entityManager.addEntity(
							new GateLeft(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, 2, c));
					checkedEntities[x][y] = 2082;
				} else if (topper[x][y] == 2083 && checkedEntities[x][y] == 0) { 
					entityManager.addEntity(
							new GateLeft(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, 3, c));
					checkedEntities[x][y] = 2083;
				}
				
				//other player made entities
				else if (topper[x][y] == 115 && checkedEntities[x][y] == 0) { 
					entityManager.addEntity(
							new CampFire(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, c));
					checkedEntities[x][y] = 115;
				} else if (topper[x][y] == 116 && checkedEntities[x][y] == 0) { 
					entityManager.addEntity(
							new PowerGenerator(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, c));
					checkedEntities[x][y] = 116;
				} else if (topper[x][y] == 117 && checkedEntities[x][y] == 0) { 
					entityManager.addEntity(
							new LampPost(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, c));
					checkedEntities[x][y] = 117;
				} else if (topper[x][y] == 118 && checkedEntities[x][y] == 0) { 
					entityManager.addEntity(
							new PowerAdaptor(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, c));
					checkedEntities[x][y] = 118;
				} else if (topper[x][y] == 119 && checkedEntities[x][y] == 0) { 
					entityManager.addEntity(
							new HeavyPulseArtillery(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, c));
					checkedEntities[x][y] = 119;
				} else if (topper[x][y] == 120 && checkedEntities[x][y] == 0) {
					entityManager.addEntity(
							new RapidPulseArtillery(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, c));
					checkedEntities[x][y] = 120;
				} else if (topper[x][y] == 121 && checkedEntities[x][y] == 0) {
					entityManager.addEntity(
							new Purifier(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, c));
					checkedEntities[x][y] = 121;
				} else if (topper[x][y] == 122 && checkedEntities[x][y] == 0) {
					entityManager.addEntity(
							new MetalContainer(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, c));
					checkedEntities[x][y] = 122;
				} else if (topper[x][y] == 123 && checkedEntities[x][y] == 0) {
					entityManager.addEntity(
							new MetalChest(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, c));
					checkedEntities[x][y] = 123;
				} else if (topper[x][y] == 124 && checkedEntities[x][y] == 0) {
					entityManager.addEntity(
							new Tent(x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, c));
					checkedEntities[x][y] = 124;
				}
				
			}
		}
	
	}

	private void spawnCreature() {
		
		//Full Moon Spawns
		if(fullMoon) {
			int rand = r.nextInt(1);  //detremines chances of creature spawning
			if(rand == 0 && entityManager.getNumCreatures() < EntityManager.MAX_CREATURES) { //if rand = 1 attept to spawn a creature

				//creatures spawn in 50 x 50 range around player - currently set to about a 32x32 range
				int x = r.nextInt(20) + 12;
				int y = r.nextInt(20) + 12;

				int positiveX = r.nextInt(2);
				int positiveY = r.nextInt(2);

				if(positiveX == 0) 
					x = -x;

				if(positiveY == 0) 
					y = -y;

				int playerTileX = (int) (Player.getPlayerData().getX() -
						Player.getPlayerData().getX() % Tile.TILEWIDTH) / Tile.TILEWIDTH;
				int playerTileY = (int) (Player.getPlayerData().getY() -
						Player.getPlayerData().getY() % Tile.TILEWIDTH) / Tile.TILEHEIGHT;

				int biomeSpecific = r.nextInt(2); //determines if the biome will determine the creature spawned - generates num from 0-1
				int creatureType = r.nextInt(3); //determines the type of creature spawned - you can increase the num of different possible creatures

				int spawnX = (playerTileX + x) * Tile.TILEWIDTH;
				int spawnY = (playerTileY + y) * Tile.TILEHEIGHT;
				Rectangle spawnRect = new Rectangle((int)(spawnX - c.getGameCamera().getxOffset() - Tile.TILEWIDTH * 2), 
						(int) (spawnY - c.getGameCamera().getyOffset() - Tile.TILEHEIGHT * 2), 
						Tile.TILEWIDTH * 5, Tile.TILEHEIGHT * 5); //set a 5x5 tile area needed for creatures to spawn

				boolean canSpawn = true;
				
				if(spawnX < 0 || spawnY < 0 || spawnX >= width * Tile.TILEWIDTH || spawnY >= height * Tile.TILEHEIGHT)
					return;

				if(playerTileX + x < width && playerTileX + x > 0 && playerTileY + y < height && playerTileY + y > 0) {
					if(tiles[spawnX / Tile.TILEWIDTH][spawnY / Tile.TILEHEIGHT] == 10)
						canSpawn = false;
				} else
					canSpawn = false;
				
				if(floor[spawnX / Tile.TILEWIDTH][spawnY / Tile.TILEHEIGHT] != 0)
					canSpawn = false;

				//check if creature has enough space to spawn
				for(int i = 0; i < entityManager.getEntitiesInBound().size(); i++) {
					if(spawnRect.intersects(entityManager.getEntitiesInBound().get(i).getBounds())) {
						canSpawn = false;
						break;
					}
				}

				if(canSpawn) {
					entityManager.addEntity(new WonderingGhoul(spawnX, spawnY, c));
				}

			}
			
		} else {
			
			//Creature Spawning
			
				
			if(entityManager.getNumCreatures() < EntityManager.MAX_CREATURES) { //if rand = 1 attept to spawn a creature

				//creatures spawn in 50 x 50 range around player - currently set to about a 40x40 range
				//int x = r.nextInt(80) - 40;
				//int y = r.nextInt(80) - 40;

				//creatures spawn in 50 x 50 range around player - currently set to about a 32x32 range
				int x = r.nextInt(20) + 12;
				int y = r.nextInt(20) + 12;

				int positiveX = r.nextInt(2);
				int positiveY = r.nextInt(2);

				if(positiveX == 0) 
					x = -x;

				if(positiveY == 0) 
					y = -y;

				//test spawning in 10x10 area around player
				//int x = r.nextInt(20) - 10;
				//int y = r.nextInt(20) - 10;

				int playerTileX = (int) (Player.getPlayerData().getX() -
						Player.getPlayerData().getX() % Tile.TILEWIDTH) / Tile.TILEWIDTH;
				int playerTileY = (int) (Player.getPlayerData().getY() -
						Player.getPlayerData().getY() % Tile.TILEWIDTH) / Tile.TILEHEIGHT;

				//int biomeSpecific = r.nextInt(2); //determines if the biome will determine the creature spawned - generates num from 0-1
				int creatureType = r.nextInt(100); //determines the type of creature spawned - you can increase the num of different possible creatures

				int spawnX = (playerTileX + x) * Tile.TILEWIDTH;
				int spawnY = (playerTileY + y) * Tile.TILEHEIGHT;
				Rectangle spawnRect = new Rectangle((int)(spawnX - c.getGameCamera().getxOffset() - Tile.TILEWIDTH * 2), 
						(int) (spawnY - c.getGameCamera().getyOffset() - Tile.TILEHEIGHT * 2), 
						Tile.TILEWIDTH * 5, Tile.TILEHEIGHT * 5); //set a 5x5 tile area needed for creatures to spawn

				int biome = 0;
				boolean canSpawn = true;
				
				if(spawnX < 0 || spawnY < 0 || spawnX >= width * Tile.TILEWIDTH || spawnY >= height * Tile.TILEHEIGHT)
					return;

				if(playerTileX + x < width && playerTileX + x > 0 && playerTileY + y < height && playerTileY + y > 0) {
					biome = terrain[playerTileX + x][playerTileY + y];
					if(tiles[spawnX / Tile.TILEWIDTH][spawnY / Tile.TILEHEIGHT] == 10)
						canSpawn = false;
				} else
					canSpawn = false;
				
				if(floor[spawnX / Tile.TILEWIDTH][spawnY / Tile.TILEHEIGHT] != 0)
					canSpawn = false;

				//check if creature has enough space to spawn
				for(int i = 0; i < entityManager.getEntitiesInBound().size(); i++) {
					if(spawnRect.intersects(entityManager.getEntitiesInBound().get(i).getBounds())) {
						canSpawn = false;
						break;
					}
				}
				int rand = r.nextInt(4);  //detremines chances of creature spawning
				if(biome == 5)
					rand = 1;
				if(canSpawn && rand == 1) {

					if(biome == 1) { //natural biome
						if(creatureType < 15) {
							entityManager.addCreature(new Phasmatodea(spawnX, spawnY, c));
						} else if(creatureType < 25) {
							entityManager.addCreature(new Chicken(spawnX, spawnY, c));
						} else if(creatureType < 35) {
							entityManager.addCreature(new Boar(spawnX, spawnY, c));
						} else if(creatureType < 45) {
							entityManager.addCreature(new Deer(spawnX, spawnY, c));
						} else if(creatureType < 55) {
							entityManager.addCreature(new GiantBeetle(spawnX, spawnY, c));
						} else if(creatureType < 58) {
							entityManager.addCreature(new RedGiantBeetle(spawnX, spawnY, c));
						} else if(creatureType < 68) {
							entityManager.addCreature(new WonderingGhoul(spawnX, spawnY, c));
						}
					} else if(biome == 2) { //forest biome
						if(creatureType < 2) {
							entityManager.addCreature(new RedGiantBeetle(spawnX, spawnY, c));
						} else if(creatureType < 10) {
							entityManager.addCreature(new GiantBeetle(spawnX, spawnY, c));
						} else if(creatureType < 13) {
							entityManager.addCreature(new Chicken(spawnX, spawnY, c));
						} else if(creatureType < 25) {
							entityManager.addCreature(new MutatedChicken(spawnX, spawnY, c));
						} else if(creatureType < 40) {
							entityManager.addCreature(new Boar(spawnX, spawnY, c));
						} else if(creatureType < 55) {
							entityManager.addCreature(new MutatedDeer(spawnX, spawnY, c));
						} else if(creatureType < 70) {
							entityManager.addCreature(new Scavenger(spawnX, spawnY, c));
						} else if(creatureType < 80) {
							entityManager.addCreature(new WonderingGhoul(spawnX, spawnY, c));
						} else if(creatureType < 85) {
							entityManager.addCreature(new DeerDeer(spawnX, spawnY, c));
						} else if(creatureType < 90) {
							entityManager.addCreature(new Sentry(spawnX, spawnY, c));
						}
					} else if(biome == 3) { //semi-desert biome	
						if(creatureType == 1) {
							entityManager.addCreature(new VileEmbryo(spawnX, spawnY, c));
						}else if(creatureType < 5) {
							entityManager.addCreature(new DesertScorpion(spawnX, spawnY, c));
						} else if(creatureType < 25) {
							entityManager.addCreature(new SandCreep(spawnX, spawnY, c)); 
						} else if(creatureType < 35) {
							entityManager.addCreature(new Boar(spawnX, spawnY, c)); 
						} else if(creatureType < 45) {
							entityManager.addCreature(new WonderingGhoul(spawnX, spawnY, c));
						} else if(creatureType < 55){
							entityManager.addCreature(new CrazedGoat(spawnX, spawnY, c));
						}
					} else if(biome == 4) { //waste biome
						if(creatureType < 40) {
							entityManager.addCreature(new WonderingGhoul(spawnX, spawnY, c));
						} else if(creatureType < 60) {
							entityManager.addCreature(new Scavenger(spawnX, spawnY, c));
						}
					} else if(biome == 5) { //ruins biome
						if(creatureType < 5) {
							entityManager.addCreature(new Scavenger(spawnX, spawnY, c));
						} else 
							entityManager.addCreature(new WonderingGhoul(spawnX, spawnY, c));
						
					} else if(biome == 6) { //infected biome
						if(creatureType < 85) {
							entityManager.addCreature(new Sentry(spawnX, spawnY, c));
						} else 
							entityManager.addCreature(new SentryMajor(spawnX, spawnY, c));
						 
					} else if(biome == 0) { //plains (default biome)
						if(creatureType < 2) {
							entityManager.addCreature(new RedGiantBeetle(spawnX, spawnY, c));
						} else if(creatureType < 10) {
							entityManager.addCreature(new GiantBeetle(spawnX, spawnY, c));
						} else if(creatureType < 12) {
							entityManager.addCreature(new Chicken(spawnX, spawnY, c));
						} else if(creatureType < 25) {
							entityManager.addCreature(new MutatedChicken(spawnX, spawnY, c));
						} else if(creatureType < 35) {
							entityManager.addCreature(new Boar(spawnX, spawnY, c));
						} else if(creatureType < 55) {
							entityManager.addCreature(new MutatedDeer(spawnX, spawnY, c));
						} else if(creatureType < 58) {
							entityManager.addCreature(new Deer(spawnX, spawnY, c));
						} else if(creatureType < 70) {
							entityManager.addCreature(new Scavenger(spawnX, spawnY, c));
						} else if(creatureType < 80) {
							entityManager.addCreature(new WonderingGhoul(spawnX, spawnY, c));
						} else if(creatureType < 85) {
							entityManager.addCreature(new DeerDeer(spawnX, spawnY, c));
						} else if(creatureType < 90) {
							entityManager.addCreature(new Sentry(spawnX, spawnY, c));
						}
					}
					//System.out.println("spawn"); //test
					
				}

			}
			
		}
		
	}
	
	//This method spawns a pack of animals
	//Wolf, deer, or goat pack
	//Wolf - andy biome
	//Deer - natural biome
	//Goat - semi-desert biome
	private void spawnPack() {
		
		int spawnChance = CT.random(0, 200); //detremines chances of creature spawning
		
		if(spawnChance == 0 && entityManager.getNumCreatures() < EntityManager.MAX_CREATURES && 
				(canSpawnWolf || canSpawnDeer || canSpawnGoat)) { //if spawnChance = 0 attept to spawn a pack

			//creatures spawn in 50 x 50 range around player - currently set to about a 16x16 range
			int x = r.nextInt(5) + 12;
			int y = r.nextInt(5) + 12;

			int positiveX = r.nextInt(2);
			int positiveY = r.nextInt(2);

			if(positiveX == 0) 
				x = -x;

			if(positiveY == 0) 
				y = -y;

			int playerTileX = (int) (Player.getPlayerData().getX() -
					Player.getPlayerData().getX() % Tile.TILEWIDTH) / Tile.TILEWIDTH;
			int playerTileY = (int) (Player.getPlayerData().getY() -
					Player.getPlayerData().getY() % Tile.TILEWIDTH) / Tile.TILEHEIGHT;

			int packType = r.nextInt(2); //determines the type of creature spawned

			int spawnX = (playerTileX + x) * Tile.TILEWIDTH;
			int spawnY = (playerTileY + y) * Tile.TILEHEIGHT;
			Rectangle spawnRect = new Rectangle((int)(spawnX - c.getGameCamera().getxOffset() - Tile.TILEWIDTH * 2), 
					(int) (spawnY - c.getGameCamera().getyOffset() - Tile.TILEHEIGHT * 2), 
					Tile.TILEWIDTH * 6, Tile.TILEHEIGHT * 6); //set a 5x5 tile area needed for creatures to spawn

			int biome = 0;
			boolean canSpawn = true;
			
			if(spawnX < 0 || spawnY < 0 || spawnX >= width * Tile.TILEWIDTH || spawnY >= height * Tile.TILEHEIGHT)
				return;

			if(playerTileX + x < width && playerTileX + x > 0 && playerTileY + y < height && playerTileY + y > 0) {
				biome = terrain[playerTileX + x][playerTileY + y];
				if(tiles[spawnX / Tile.TILEWIDTH][spawnY / Tile.TILEHEIGHT] == 10)
					canSpawn = false;
			} else
				canSpawn = false;
			
			//check for water
			for(int j = playerTileY + y - 3; j < playerTileY + y + 4; j++) {
				for(int i = playerTileX + x - 3; i < playerTileX + x + 4; i++) {
					if(tiles[i][j] == 10 || floor[i][j] != 0)
						canSpawn = false;
				}
			}

			//check if creature has enough space to spawn
			for(int i = 0; i < entityManager.getEntitiesInBound().size(); i++) {
				if(spawnRect.intersects(entityManager.getEntitiesInBound().get(i).getBounds())) {
					canSpawn = false;
					break;
				}
			}
			
			if(canSpawn) {
				if(biome == 1) { //natural biome
					
					if(packType == 0 && canSpawnDeer) { //spawn deer pack
						
						spawnDeerPack(spawnX, spawnY);
						
					} else if(packType == 1 && canSpawnWolf && packAlphaActive == false) { //spawn wolf pack
						
						spawnWolfPack(spawnX, spawnY);
						
					}
					
				} else if(biome == 3) { //semi-desert biome
					
					if(packType == 0 && canSpawnGoat) { //spawn goat pack
						
						spawnGoatPack(spawnX, spawnY);
						
						
					} else if(packType == 1 && canSpawnWolf && packAlphaActive == false) { //spawn wolf pack
						
						spawnWolfPack(spawnX, spawnY);

						
					}
					
				} else if(canSpawnWolf && packAlphaActive == false) { //all other biomes only spawn wolf pack
					
					if(packType == 0 && canSpawnDeer) { //spawn deer pack
						
						spawnDeerPack(spawnX, spawnY);
						
					} else if(packType == 1 && canSpawnDeer) { //spawn mutated deer pack
						
						spawnMutatedDeerPack(spawnX, spawnY);
						
					}

				}
				
			} //end of spawn check

		}
		
	}
	
	private void spawnWolfPack(int spawnX, int spawnY) {
		
		entityManager.addEntity(new PackAlpha(spawnX, spawnY, c));
		packAlphaActive = true;
		
		int numMembers = r.nextInt(2) + 3; //3-4 members
		
		int numMembersActive = 0;
		
		while(numMembersActive < numMembers) {
			
			if(numMembersActive == 0) {
				entityManager.addEntity(new PackMember(spawnX - Tile.TILEWIDTH, spawnY - Tile.TILEHEIGHT, c));
			} else if(numMembersActive == 1) {
				entityManager.addEntity(new PackMember(spawnX + Tile.TILEWIDTH, spawnY - Tile.TILEHEIGHT, c));
			} else if(numMembersActive == 2) {
				entityManager.addEntity(new PackMember(spawnX - Tile.TILEWIDTH, spawnY + Tile.TILEHEIGHT, c));
			} else if(numMembersActive == 3) {
				entityManager.addEntity(new PackMember(spawnX + Tile.TILEWIDTH, spawnY + Tile.TILEHEIGHT, c));
			}
			
			numMembersActive++;
			
		}
		
		MessageBox.addMessage("you hear howling in the distance");
		lastWolfTimer = System.currentTimeMillis();
		canSpawnWolf = false;
	}
	
	private void spawnDeerPack(int spawnX, int spawnY) {
		
		int numMembers = r.nextInt(5) + 2; //2-6 members
		
		int numMembersActive = 0;
		
		while(numMembersActive < numMembers) {
			
			if(numMembersActive == 0) {
				entityManager.addEntity(new Deer(spawnX - Tile.TILEWIDTH, spawnY - Tile.TILEHEIGHT, c));
			} else if(numMembersActive == 1) {
				entityManager.addEntity(new Deer(spawnX + Tile.TILEWIDTH, spawnY - Tile.TILEHEIGHT, c));
			} else if(numMembersActive == 2) {
				entityManager.addEntity(new Deer(spawnX - Tile.TILEWIDTH, spawnY + Tile.TILEHEIGHT, c));
			} else if(numMembersActive == 3) {
				entityManager.addEntity(new Deer(spawnX + Tile.TILEWIDTH, spawnY + Tile.TILEHEIGHT, c));
			} else if(numMembersActive == 4) {
				entityManager.addEntity(new Deer(spawnX - Tile.TILEWIDTH, spawnY, c));
			} else if(numMembersActive == 5) {
				entityManager.addEntity(new Deer(spawnX + Tile.TILEWIDTH, spawnY, c));
			}
			
			numMembersActive++;
			
		}
		
		//Can add a message box when deer pack spawns
		//MessageBox.addMessage("you hear howling in the distance");
		lastDeerTimer = System.currentTimeMillis();
		canSpawnDeer = false;
	}
	
	private void spawnGoatPack(int spawnX, int spawnY) {
		int numMembers = r.nextInt(7) + 4; //4-10 members
		
		int numMembersActive = 0;
		
		while(numMembersActive < numMembers) {
			
			if(numMembersActive == 0) {
				entityManager.addEntity(new Goat(spawnX - Tile.TILEWIDTH, spawnY - Tile.TILEHEIGHT, c));
			} else if(numMembersActive == 1) {
				entityManager.addEntity(new Goat(spawnX + Tile.TILEWIDTH, spawnY - Tile.TILEHEIGHT, c));
			} else if(numMembersActive == 2) {
				entityManager.addEntity(new Goat(spawnX - Tile.TILEWIDTH, spawnY + Tile.TILEHEIGHT, c));
			} else if(numMembersActive == 3) {
				entityManager.addEntity(new Goat(spawnX + Tile.TILEWIDTH, spawnY + Tile.TILEHEIGHT, c));
			} else if(numMembersActive == 4) {
				entityManager.addEntity(new Goat(spawnX - Tile.TILEWIDTH, spawnY, c));
			} else if(numMembersActive == 5) {
				entityManager.addEntity(new Goat(spawnX + Tile.TILEWIDTH, spawnY, c));
			} else if(numMembersActive == 6) {
				entityManager.addEntity(new Goat(spawnX - Tile.TILEWIDTH, spawnY - Tile.TILEHEIGHT * 2, c));
			} else if(numMembersActive == 7) {
				entityManager.addEntity(new Goat(spawnX + Tile.TILEWIDTH, spawnY - Tile.TILEHEIGHT * 2, c));
			} else if(numMembersActive == 8) {
				entityManager.addEntity(new Goat(spawnX - Tile.TILEWIDTH, spawnY + Tile.TILEHEIGHT * 2, c));
			} else if(numMembersActive == 9) {
				entityManager.addEntity(new Goat(spawnX + Tile.TILEWIDTH, spawnY + Tile.TILEHEIGHT * 2, c));
			}
			
			numMembersActive++;
			
			lastGoatTimer = System.currentTimeMillis();
			canSpawnGoat = false;
		}
			
	}
	
	private void spawnMutatedDeerPack(int spawnX, int spawnY) {
		
		int numMembers = r.nextInt(5) + 2; //2-6 members
		
		int numMembersActive = 0;
		
		while(numMembersActive < numMembers) {
			
			if(numMembersActive == 0) {
				entityManager.addEntity(new MutatedDeer(spawnX - Tile.TILEWIDTH, spawnY - Tile.TILEHEIGHT, c));
			} else if(numMembersActive == 1) {
				entityManager.addEntity(new MutatedDeer(spawnX + Tile.TILEWIDTH, spawnY - Tile.TILEHEIGHT, c));
			} else if(numMembersActive == 2) {
				entityManager.addEntity(new MutatedDeer(spawnX - Tile.TILEWIDTH, spawnY + Tile.TILEHEIGHT, c));
			} else if(numMembersActive == 3) {
				entityManager.addEntity(new MutatedDeer(spawnX + Tile.TILEWIDTH, spawnY + Tile.TILEHEIGHT, c));
			} else if(numMembersActive == 4) {
				entityManager.addEntity(new MutatedDeer(spawnX - Tile.TILEWIDTH, spawnY, c));
			} else if(numMembersActive == 5) {
				entityManager.addEntity(new MutatedDeer(spawnX + Tile.TILEWIDTH, spawnY, c));
			}
			
			numMembersActive++;
			
		}
		
		//Can add a message box when deer pack spawns
		//MessageBox.addMessage("you hear howling in the distance");
		lastDeerTimer = System.currentTimeMillis();
		canSpawnDeer = false;
	}
	
	//This method handles the spawning of collectible entities
	private void spawnMaterials() {

		//Creature Spawning
		int rand = r.nextInt(20);  //detremines chances of creature spawning
		if(rand == 1 && entityManager.getNumCreatures() < EntityManager.MAX_CREATURES) { //if rand = 1 attept to spawn a creature

			//creatures spawn in 22 x 22 range around player
			int x = r.nextInt(10) + 12;
			int y = r.nextInt(10) + 12;

			int positiveX = r.nextInt(2);
			int positiveY = r.nextInt(2);

			if(positiveX == 0) 
				x = -x;

			if(positiveY == 0) 
				y = -y;

			//test spawning in 10x10 area around player
			//int x = r.nextInt(20) - 10;
			//int y = r.nextInt(20) - 10;

			int playerTileX = (int) (Player.getPlayerData().getX() -
					Player.getPlayerData().getX() % Tile.TILEWIDTH) / Tile.TILEWIDTH;
			int playerTileY = (int) (Player.getPlayerData().getY() -
					Player.getPlayerData().getY() % Tile.TILEWIDTH) / Tile.TILEHEIGHT;

			//int biomeSpecific = r.nextInt(2); //determines if the biome will determine the creature spawned - generates num from 0-1
			int creatureType = r.nextInt(100); //determines the type of creature spawned - you can increase the num of different possible creatures

			int spawnX = (playerTileX + x) * Tile.TILEWIDTH;
			int spawnY = (playerTileY + y) * Tile.TILEHEIGHT;
			Rectangle spawnRect = new Rectangle((int)(spawnX - c.getGameCamera().getxOffset()), 
					(int) (spawnY - c.getGameCamera().getyOffset()), 
					Tile.TILEWIDTH, Tile.TILEHEIGHT); //set a 1x1 tile area needed for materials to spawn

			int biome = 0;
			boolean canSpawn = true;
			
			if(spawnX < 0 || spawnY < 0 || spawnX >= width * Tile.TILEWIDTH || spawnY >= height * Tile.TILEHEIGHT)
				return;

			if(playerTileX + x < width && playerTileX + x > 0 && playerTileY + y < height && playerTileY + y > 0) {
				biome = terrain[playerTileX + x][playerTileY + y];
				if(tiles[spawnX / Tile.TILEWIDTH][spawnY / Tile.TILEHEIGHT] == 10)
					canSpawn = false;
			} else
				canSpawn = false;

			//check if creature has enough space to spawn
			for(int i = 0; i < entityManager.getEntitiesInBound().size(); i++) {
				if(spawnRect.intersects(entityManager.getEntitiesInBound().get(i).getBounds())) {
					canSpawn = false;
					break;
				}
			}

			if(canSpawn) {

				if(biome == 1) { //natural biome
					if(creatureType < 5) {
						entityManager.addCreature(new ExtrafloralNectar(spawnX, spawnY, c));
					} else if(creatureType < 10) {
						entityManager.addCreature(new BrainFungui(spawnX, spawnY, c));
					} else if(creatureType < 15) {
						entityManager.addCreature(new PrettyShroom(spawnX, spawnY, c));
					} else if(creatureType < 50) {
						entityManager.addCreature(new ShroomPile(spawnX, spawnY, c));
					} else if(creatureType < 60) {
						entityManager.addCreature(new WoodenStick(spawnX, spawnY, c));
					} else  {
						entityManager.addCreature(new Grass(spawnX, spawnY, c));
					}
				} else if(biome == 2) { //forest biome
					if(creatureType < 3) {
						entityManager.addCreature(new BrainFungui(spawnX, spawnY, c));
					} else if(creatureType < 6) {
						entityManager.addCreature(new PrettyShroom(spawnX, spawnY, c));
					} else if(creatureType < 30) {
						entityManager.addCreature(new ShroomPile(spawnX, spawnY, c));
					} else if(creatureType < 60) {
						entityManager.addCreature(new WoodenStick(spawnX, spawnY, c));
					} else {
						entityManager.addCreature(new Grass(spawnX, spawnY, c));
					}
				} else if(biome == 3) { //semi-desert biome	
					if(creatureType < 30) {
						entityManager.addCreature(new WoodenStick(spawnX, spawnY, c));
					}
				} else if(biome == 4) { //waste biome
					if(creatureType < 2) {
						entityManager.addCreature(new BrainFungui(spawnX, spawnY, c));
					} else if(creatureType < 4) {
						entityManager.addCreature(new PrettyShroom(spawnX, spawnY, c));
					} else if(creatureType < 10) {
						entityManager.addCreature(new ShroomPile(spawnX, spawnY, c));
					} else if(creatureType < 20) {
						entityManager.addCreature(new WoodenStick(spawnX, spawnY, c));
					}
				} else if(biome == 5) { //ruins biome
					if(creatureType < 2) {
						entityManager.addCreature(new BrainFungui(spawnX, spawnY, c));
					} else if(creatureType < 4) {
						entityManager.addCreature(new PrettyShroom(spawnX, spawnY, c));
					} else if(creatureType < 10) {
						entityManager.addCreature(new ShroomPile(spawnX, spawnY, c));
					} else if(creatureType < 20) {
						entityManager.addCreature(new WoodenStick(spawnX, spawnY, c));
					}
				} else if(biome == 6) { //infected biome
					
				} else {
					if(creatureType < 2) {
						entityManager.addCreature(new BrainFungui(spawnX, spawnY, c));
					} else if(creatureType < 4) {
						entityManager.addCreature(new PrettyShroom(spawnX, spawnY, c));
					} else if(creatureType < 10) {
						entityManager.addCreature(new ShroomPile(spawnX, spawnY, c));
					} else if(creatureType < 20) {
						entityManager.addCreature(new WoodenStick(spawnX, spawnY, c));
					} else {
						entityManager.addCreature(new Grass(spawnX, spawnY, c));
					}
				}

				//System.out.println("spawn"); //test
				
				
			}

		}
	}
	
	private void debugControls() {
		
		if(c.getKeyManager().keyJustPressed(KeyEvent.VK_F3)) { //enter debug mode
			debugMode = !debugMode;
		}
		if(debugMode) {
			Player.getPlayerData().setHealth(Player.getDefaultHealth());
			Player.getPlayerData().energy = 800;
			allDay = true;
			effects.addEffect(new Effect("swiftness", 10000));
		}
		
		if(c.getKeyManager().keyJustPressed(KeyEvent.VK_T))
			entityManager.addEntity(new SentryHive(entityManager.getPlayer().getX() + 200, entityManager.getPlayer().getY() - 300, c));
		if(c.getKeyManager().keyJustPressed(KeyEvent.VK_U))
			entityManager.addEntity(new SentryBroodMother(entityManager.getPlayer().getX() + 200, entityManager.getPlayer().getY() - 300, c));
		if(c.getKeyManager().keyJustPressed(KeyEvent.VK_O))
			entityManager.addEntity(new Boar(entityManager.getPlayer().getX() + 200, entityManager.getPlayer().getY() - 300, c));
		if (c.getKeyManager().keyJustPressed(KeyEvent.VK_G))
			Player.getPlayerData().energy+= 800;
		if (c.getKeyManager().keyJustPressed(KeyEvent.VK_I))
			allDay = !allDay;
		if (c.getKeyManager().keyJustPressed(KeyEvent.VK_N))
			System.out.println(Player.getPlayerData().getCurrentSpeed());
		if (c.getKeyManager().keyJustPressed(KeyEvent.VK_V)) {
			entityManager.addCreature(
					new WonderingGhoul(entityManager.getPlayer().getX() - 800, entityManager.getPlayer().getY() + 350, c));
			entityManager.addCreature(
					new WonderingGhoul(entityManager.getPlayer().getX() - 900, entityManager.getPlayer().getY() + 350, c));
			entityManager.addCreature(
					new WonderingGhoul(entityManager.getPlayer().getX() - 800, entityManager.getPlayer().getY() + 150, c));
			entityManager.addCreature(
					new WonderingGhoul(entityManager.getPlayer().getX() - 1000, entityManager.getPlayer().getY() + 150, c));
			entityManager.addCreature(
					new WonderingGhoul(entityManager.getPlayer().getX() - 1000, entityManager.getPlayer().getY() + 350, c));
			
		}
		if(c.getKeyManager().keyJustPressed(KeyEvent.VK_B)) {
			time += 10;
		}
		if(c.getKeyManager().keyJustPressed(KeyEvent.VK_H)) {
			effects.addEffect(new Effect("swiftness", 10000));
		}
		if(c.getKeyManager().keyJustPressed(KeyEvent.VK_L)) {
			effects.addEffect(new Effect("heavily armed", 10000));
		}
		if(c.getKeyManager().keyJustPressed(KeyEvent.VK_N)) {
			Player.getPlayerData().thirst-=20;
		}
		if(c.getKeyManager().keyJustPressed(KeyEvent.VK_M)) {
			
			AudioPlayer.playAudio("audio/groundShake.wav");
			
			MessageBox.addMessage("you have angered the Vilespawn...");
			
			new java.util.Timer().schedule(new java.util.TimerTask() {
				@Override
				public void run() {
					c.getGameCamera().shake(20);
	
				}
			}, 1000);
			
			new java.util.Timer().schedule(new java.util.TimerTask() {
				@Override
				public void run() {
					c.getGameCamera().shake(50);
	
				}
			}, 2000);
			
			new java.util.Timer().schedule(new java.util.TimerTask() {
				@Override
				public void run() {
					c.getGameCamera().shake(20);
	
				}
			}, 4000);
			
			new java.util.Timer().schedule(new java.util.TimerTask() {
				@Override
				public void run() {
					entityManager.addEntity(
							new VileSpawn(c));
	
				}
			}, 6000);
			
		}
		
		if(c.getKeyManager().keyJustPressed(KeyEvent.VK_R)) {
			if(Player.getPlayerData().getHands().getHand() != null && Player.getPlayerData().getHands().getHand().getType().equals("ranged")) {
				Ranged r = (Ranged)Player.getPlayerData().getHands().getHand();
					r.reload();
			}
		}
		
		//Biome tp test
		if(c.getKeyManager().keyJustPressed(KeyEvent.VK_4)) { //natural biome
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if(terrain[x][y] == 1) {
						Player.getPlayerData().setX(x * Tile.TILEWIDTH);
						Player.getPlayerData().setY(y * Tile.TILEHEIGHT);
					}	
				}
			}
		}
		if(c.getKeyManager().keyJustPressed(KeyEvent.VK_5)) { //forest biome
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if(terrain[x][y] == 2) {
						Player.getPlayerData().setX(x * Tile.TILEWIDTH);
						Player.getPlayerData().setY(y * Tile.TILEHEIGHT);
					}	
				}
			}
		}
		if(c.getKeyManager().keyJustPressed(KeyEvent.VK_6)) { //semi-desert biome
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if(terrain[x][y] == 3) {
						Player.getPlayerData().setX(x * Tile.TILEWIDTH);
						Player.getPlayerData().setY(y * Tile.TILEHEIGHT);
					}	
				}
			}
		}
		if(c.getKeyManager().keyJustPressed(KeyEvent.VK_7)) { //waste biome
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if(terrain[x][y] == 4) {
						Player.getPlayerData().setX(x * Tile.TILEWIDTH);
						Player.getPlayerData().setY(y * Tile.TILEHEIGHT);
					}	
				}
			}
		}
		if(c.getKeyManager().keyJustPressed(KeyEvent.VK_8)) { //ruins biome
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if(terrain[x][y] == 5) {
						Player.getPlayerData().setX(x * Tile.TILEWIDTH);
						Player.getPlayerData().setY(y * Tile.TILEHEIGHT);
					}	
				}
			}
		}
		if(c.getKeyManager().keyJustPressed(KeyEvent.VK_9)) { //infected biome
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if(terrain[x][y] == 6) {
						Player.getPlayerData().setX(x * Tile.TILEWIDTH);
						Player.getPlayerData().setY(y * Tile.TILEHEIGHT);
					}	
				}
			}
		}
		if(c.getKeyManager().keyJustPressed(KeyEvent.VK_0)) { //water
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if(tiles[x][y] == 10) {
						Player.getPlayerData().setX(x * Tile.TILEWIDTH);
						Player.getPlayerData().setY(y * Tile.TILEHEIGHT);
					}	
				}
			}
		}
		
		if(numVileEmbryo >= 5 && !vileSpawned) {
			vileSpawned = true;
			
			AudioPlayer.playAudio("audio/groundShake.wav");
			
			MessageBox.addMessage("you have angered the Vilespawn...");
			
			new java.util.Timer().schedule(new java.util.TimerTask() {
				@Override
				public void run() {
					c.getGameCamera().shake(20);
	
				}
			}, 1000);
			
			new java.util.Timer().schedule(new java.util.TimerTask() {
				@Override
				public void run() {
					c.getGameCamera().shake(50);
	
				}
			}, 2000);
			
			new java.util.Timer().schedule(new java.util.TimerTask() {
				@Override
				public void run() {
					c.getGameCamera().shake(20);
	
				}
			}, 4000);
			
			new java.util.Timer().schedule(new java.util.TimerTask() {
				@Override
				public void run() {
					entityManager.addEntity(
							new VileSpawn(c));
	
				}
			}, 6000);
			
		}
		
		//testing movement
		if(c.getKeyManager().keyJustPressed(KeyEvent.VK_HOME)) { //up
			Player.getPlayerData().setY(Player.getPlayerData().getY() - Tile.TILEHEIGHT);
		}
		if(c.getKeyManager().keyJustPressed(KeyEvent.VK_END)) { //down
			Player.getPlayerData().setY(Player.getPlayerData().getY() + Tile.TILEHEIGHT);
		}
		if(c.getKeyManager().keyJustPressed(KeyEvent.VK_DELETE)) { //left
			Player.getPlayerData().setX(Player.getPlayerData().getX() - Tile.TILEWIDTH);
		}
		if(c.getKeyManager().keyJustPressed(KeyEvent.VK_PAGE_DOWN)) { //right
			Player.getPlayerData().setX(Player.getPlayerData().getX() + Tile.TILEWIDTH);
		}
		if(c.getKeyManager().keyJustPressed(KeyEvent.VK_INSERT)) { //move 100 left
			Player.getPlayerData().setX(Player.getPlayerData().getX() - 100 * Tile.TILEWIDTH);
		}
		if(c.getKeyManager().keyJustPressed(KeyEvent.VK_PAGE_UP)) { //move 100 right
			Player.getPlayerData().setX(Player.getPlayerData().getX() +  100 * Tile.TILEWIDTH);
		}
		if(c.getKeyManager().keyJustPressed(KeyEvent.VK_F1)) { //move 100 up
			Player.getPlayerData().setY(Player.getPlayerData().getY() - 100 * Tile.TILEHEIGHT);
		}
		if(c.getKeyManager().keyJustPressed(KeyEvent.VK_F2)) { //move 100 down
			Player.getPlayerData().setY(Player.getPlayerData().getY() + 100 * Tile.TILEHEIGHT);
		}
		
		if(c.getKeyManager().keyJustPressed(KeyEvent.VK_F4)) { //activate full moon
			fullMoon = true;			
			MessageBox.addMessage("the moon is full tonight");
		}
		
		if(c.getKeyManager().keyJustPressed(KeyEvent.VK_F5)) { //move forward through time
			time++;
			dayNum++;
			Player.getPlayerData().setBasicSurvivalXP(Player.getPlayerData().getBasicSurvivalXP() + 
					Player.getPlayerData().getIntelligence() + CT.random(1, Player.getPlayerData().getIntelligence() + 1));
		}
		//test - press o while in inventory to show item to string method
		if (c.getKeyManager().keyJustPressed(KeyEvent.VK_O)) {
			for(int i = 0; i < inventory.InventoryItems.size(); i++) {
				System.out.println(inventory.InventoryItems.get(i).toString());
			}
		}
		if(c.getKeyManager().keyJustPressed(KeyEvent.VK_F6)) { //entity test spawning
			//entityManager.addEntity(new RuinPiece1(Player.getPlayerData().getX(),
			//		Player.getPlayerData().getY(), c));
			//entityManager.addEntity(new RuinPiece2(Player.getPlayerData().getX(),
			//		Player.getPlayerData().getY(), c));
			//entityManager.addEntity(new RuinPiece3(Player.getPlayerData().getX(),
			//		Player.getPlayerData().getY(), c));
			//entityManager.addEntity(new RuinPiece4(Player.getPlayerData().getX(),
			//		Player.getPlayerData().getY(), c));
			//entityManager.addEntity(new RuinPiece5(Player.getPlayerData().getX(),
			//		Player.getPlayerData().getY(), c));
			//entityManager.addEntity(new RuinPiece6(Player.getPlayerData().getX(),
			//		Player.getPlayerData().getY(), c));
			//entityManager.addEntity(new SpaceShuttle(Player.getPlayerData().getX(),
			//		Player.getPlayerData().getY(), c));
			entityManager.addEntity(new SleepingSentinel(Player.getPlayerData().getX(),
					Player.getPlayerData().getY(), c));
			//System.out.println(AwakenedSentinel.class.getSimpleName());
		}
		
		if(c.getKeyManager().keyJustPressed(KeyEvent.VK_F7)) {
			Recipe.unlockAllRecipes();
		}
		
		//test saving features
		if (c.getKeyManager().keyJustPressed(KeyEvent.VK_MINUS)) { //save topper
			//worldSaver.saveTopper();
			//System.out.println("topper saved");
			//worldSaver.saveChests();
			//System.out.println("chests saved");
			
			//worldSaver.saveTimedCraftingStructures();
			//System.out.println("timed crafting structures saved");
			
			//worldSaver.saveCreatures();
			//System.out.println("creatures saved");
			
			worldSaver.saveWorld();
			System.out.println("world saved");
		}
		if (c.getKeyManager().keyJustPressed(KeyEvent.VK_EQUALS)) {
			worldSaver.savePlayer();
			System.out.println("player data saved");
		}
		if (c.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) { //save platform
			//worldSaver.savePlatforms();
			//System.out.println("platforms saved");
			//System.out.println(Item.items[500].getName());
			//worldSaver.saveChests();
			//System.out.println("chests saved");
			
			//worldSaver.saveTimedCraftingStructures();
			//System.out.println("timed crafting structures saved");
			
			//entityManager.addEntity(
			//		new AwakenedSentinel(entityManager.getPlayer().getX() - Tile.TILEWIDTH, 
			//				entityManager.getPlayer().getY() - 4 * Tile.TILEHEIGHT, c));
							
			//loadPlayerInventory();
			//System.out.println("inventory loaded");
			
			//loadTimedCraftingStructures();
			//System.out.println("timed crafting structures loaded");
			
			//loadCreatures();
			//System.out.println("creatures loaded");
		}
		if (c.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
			Player.getPlayerData().setX(48000);
			Player.getPlayerData().setY(48000);
			//worldSaver.saveCreatures();
			//System.out.println("creatures saved");
		}
		
	}
	
	//LOAD METHODS
	private void loadPlayerInventory() {
		String file = WorldInput.loadFileAsString(String.format("worldData/%s",c.getMenuState().getWorldSelectState().getSelectedWorldName()));
		String[] tokens = file.split("\\s+"); // split up every number into the string tokens array
		
		/*
		charName = tokens[2];
		savedHealth = WorldInput.parseInt(tokens[3]);
		charRunSpeed = Double.parseDouble(tokens[4]);
		savedEndurability = WorldInput.parseInt(tokens[5]);
		savedDamageScale = WorldInput.parseInt(tokens[6]);
		savedIntimidation = WorldInput.parseInt(tokens[7]);
		savedIntelligence = WorldInput.parseInt(tokens[8]);
		savedResistance = WorldInput.parseInt(tokens[9]);
		hunger = WorldInput.parseInt(tokens[10]);
		thirst = WorldInput.parseInt(tokens[11]);
		energy = WorldInput.parseInt(tokens[12]);
		setDayNum(WorldInput.parseInt(tokens[13]));
		setTime(Double.parseDouble(tokens[14]));
		playerX =  WorldInput.parseInt(tokens[15]);
		playerY =  WorldInput.parseInt(tokens[16]);
		basicSurvivalXP =  WorldInput.parseInt(tokens[17]);
		combatXP =  WorldInput.parseInt(tokens[18]);
		cookingXP =  WorldInput.parseInt(tokens[19]);
		buildingXP =  WorldInput.parseInt(tokens[20]);
		*/
		
		//Inventory starts at tokens[22]
		int index = 22;
		
		//ARMOR
		int numArmor = WorldInput.parseInt(tokens[index]);
		index++;
		for(int i = 0; i < numArmor; i++) {
			int id = WorldInput.parseInt(tokens[index]);
			index++;
			double weight = Double.parseDouble(tokens[index]);
			index++;
			int damage = WorldInput.parseInt(tokens[index]);
			index++;
			int volume = WorldInput.parseInt(tokens[index]);
			index++;
			int intimidation = WorldInput.parseInt(tokens[index]);
			index++;
			int resistance = WorldInput.parseInt(tokens[index]);
			index++;
			double healthRegen = Double.parseDouble(tokens[index]);
			index++;
			int currentEndurance = WorldInput.parseInt(tokens[index]);
			index++;
			int endurance = WorldInput.parseInt(tokens[index]);
			index++;
			
			Armor e = new Armor(Item.items[id].getTexture(), Item.items[id].getName(), id, Item.items[id].getType(), weight, damage, volume, 
					intimidation, resistance, healthRegen, currentEndurance, endurance);
			Player.getPlayerData().getInventory().addItem(e);
		}
		
		//FOOD
		int numFood = WorldInput.parseInt(tokens[index]);
		index++;
		for(int i = 0; i < numFood; i++) {
			int id = WorldInput.parseInt(tokens[index]);
			index++;
			double weight = Double.parseDouble(tokens[index]);
			index++;
			int damage = WorldInput.parseInt(tokens[index]);
			index++;
			int volume = WorldInput.parseInt(tokens[index]);
			index++;
			int hunger = WorldInput.parseInt(tokens[index]);
			index++;
			int thirst = WorldInput.parseInt(tokens[index]);
			index++;
			int freshness = WorldInput.parseInt(tokens[index]);
			index++;
			
			Food e = new Food(Item.items[id].getTexture(), Item.items[id].getName(), id, weight, damage, volume, 
					hunger, thirst, ((Food)Item.items[id]).getFood(), freshness);
			Player.getPlayerData().getInventory().addItem(e);
		}
		
		//RANGED
		int numRanged = WorldInput.parseInt(tokens[index]);
		index++;
		for(int i = 0; i < numRanged; i++) {
			int id = WorldInput.parseInt(tokens[index]);
			index++;
			double weight = Double.parseDouble(tokens[index]);
			index++;
			int damage = WorldInput.parseInt(tokens[index]);
			index++;
			double aSpeed = Double.parseDouble(tokens[index]);
			index++;
			int intimidation = WorldInput.parseInt(tokens[index]);
			index++;
			int ammoMax = WorldInput.parseInt(tokens[index]);
			index++;
			int reloadCooldown = WorldInput.parseInt(tokens[index]);
			index++;
			double accuracy = Double.parseDouble(tokens[index]);
			index++;
			int volume = WorldInput.parseInt(tokens[index]);
			index++;
			int ammoCurrent = WorldInput.parseInt(tokens[index]);
			index++;
			
			Ranged e = new Ranged(Item.items[id].getTexture(), Item.items[id].getName(), id, weight, damage, aSpeed, 
					intimidation, ammoMax, reloadCooldown, ((Ranged)Item.items[id]).getRefilMaterial(), accuracy, volume);
			e.setAmmoCurrent(ammoCurrent);
			Player.getPlayerData().getInventory().addItem(e);
		}
		
		//TOOLS
		int numTool = WorldInput.parseInt(tokens[index]);
		index++;
		for(int i = 0; i < numTool; i++) {
			int id = WorldInput.parseInt(tokens[index]);
			index++;
			double weight = Double.parseDouble(tokens[index]);
			index++;
			int damage = WorldInput.parseInt(tokens[index]);
			index++;
			double aSpeed = Double.parseDouble(tokens[index]);
			index++;
			int intimidation = WorldInput.parseInt(tokens[index]);
			index++;
			double range = Double.parseDouble(tokens[index]);
			index++;
			int volume = WorldInput.parseInt(tokens[index]);
			index++;
			double power = Double.parseDouble(tokens[index]);
			index++;
			int currentEndurance = WorldInput.parseInt(tokens[index]);
			index++;
			int endurance = WorldInput.parseInt(tokens[index]);
			index++;
			
			Tool e = new Tool(Item.items[id].getTexture(), Item.items[id].getName(), Item.items[id].getType(), id, weight, damage, aSpeed, 
					intimidation, range, volume, power, currentEndurance, endurance);
			Player.getPlayerData().getInventory().addItem(e);
		}
		
		//TORCHES
		int numTorch = WorldInput.parseInt(tokens[index]);
		index++;
		for(int i = 0; i < numTorch; i++) {
			Torch e = new Torch();
			Player.getPlayerData().getInventory().addItem(e);
		}
		
		//WATER CONTAINERS
		int numWaterContainer = WorldInput.parseInt(tokens[index]);
		index++;
		for(int i = 0; i < numWaterContainer; i++) {
			int id = WorldInput.parseInt(tokens[index]);
			index++;
			double weight = Double.parseDouble(tokens[index]);
			index++;
			int volume = WorldInput.parseInt(tokens[index]);
			index++;
			int currentCapacity = WorldInput.parseInt(tokens[index]);
			index++;
			int capacity = WorldInput.parseInt(tokens[index]);
			index++;
			
			WaterContainer e = new WaterContainer(Item.items[id].getTexture(), Item.items[id].getName(), id, weight, volume, currentCapacity, 
					capacity);
			Player.getPlayerData().getInventory().addItem(e);
		}
		
		//WEAPONS
		int numWeapon = WorldInput.parseInt(tokens[index]);
		index++;
		for(int i = 0; i < numWeapon; i++) {
			int id = WorldInput.parseInt(tokens[index]);
			index++;
			double weight = Double.parseDouble(tokens[index]);
			index++;
			int damage = WorldInput.parseInt(tokens[index]);
			index++;
			double aSpeed = Double.parseDouble(tokens[index]);
			index++;
			int intimidation = WorldInput.parseInt(tokens[index]);
			index++;
			double range = Double.parseDouble(tokens[index]);
			index++;
			int volume = WorldInput.parseInt(tokens[index]);
			index++;
			int currentEndurance = WorldInput.parseInt(tokens[index]);
			index++;
			int endurance = WorldInput.parseInt(tokens[index]);
			index++;
			
			Weapon e = new Weapon(Item.items[id].getTexture(), Item.items[id].getName(), id, weight, damage, aSpeed, 
					intimidation, range, volume, currentEndurance, endurance);
			Player.getPlayerData().getInventory().addItem(e);
		}
		
		//ITEMS
		int numItem = WorldInput.parseInt(tokens[index]);
		index++;
		for(int i = 0; i < numItem; i++) {
			int id = WorldInput.parseInt(tokens[index]);
			index++;
			double weight = Double.parseDouble(tokens[index]);
			index++;
			int damage = WorldInput.parseInt(tokens[index]);
			index++;
			int volume = WorldInput.parseInt(tokens[index]);
			index++;
			int count = WorldInput.parseInt(tokens[index]);
			index++;
			
			Item e = new Item(Item.items[id].getTexture(), Item.items[id].getName(), id, Item.items[id].isStackable(), 
					Item.items[id].getType(), weight, damage, volume);
			for(int j = 0; j < count; j++)
				Player.getPlayerData().getInventory().addItem(e); 
		}
		
		//HANDS
		//LEFT HAND
		int leftHandType = WorldInput.parseInt(tokens[index]);
		index++;
		if(leftHandType == 1) { //armor
			int id = WorldInput.parseInt(tokens[index]);
			index++;
			double weight = Double.parseDouble(tokens[index]);
			index++;
			int damage = WorldInput.parseInt(tokens[index]);
			index++;
			int volume = WorldInput.parseInt(tokens[index]);
			index++;
			int intimidation = WorldInput.parseInt(tokens[index]);
			index++;
			int resistance = WorldInput.parseInt(tokens[index]);
			index++;
			double healthRegen = Double.parseDouble(tokens[index]);
			index++;
			int currentEndurance = WorldInput.parseInt(tokens[index]);
			index++;
			int endurance = WorldInput.parseInt(tokens[index]);
			index++;
			
			Armor e = new Armor(Item.items[id].getTexture(), Item.items[id].getName(), id, Item.items[id].getType(), weight, damage, volume, 
					intimidation, resistance, healthRegen, currentEndurance, endurance);
			
			for(Item x: Player.getPlayerData().getInventory().InventoryItems) {
				if(x.getId() == e.getId())
					Player.getPlayerData().getHands().leftHand = x;
			}
		} else if(leftHandType == 2) { //food
			int id = WorldInput.parseInt(tokens[index]);
			index++;
			double weight = Double.parseDouble(tokens[index]);
			index++;
			int damage = WorldInput.parseInt(tokens[index]);
			index++;
			int volume = WorldInput.parseInt(tokens[index]);
			index++;
			int hunger = WorldInput.parseInt(tokens[index]);
			index++;
			int thirst = WorldInput.parseInt(tokens[index]);
			index++;
			int freshness = WorldInput.parseInt(tokens[index]);
			index++;
			
			Food e = new Food(Item.items[id].getTexture(), Item.items[id].getName(), id, weight, damage, volume, 
					hunger, thirst, ((Food)Item.items[id]).getFood(), freshness);
			for(Item x: Player.getPlayerData().getInventory().InventoryItems) {
				if(x.getId() == e.getId())
					Player.getPlayerData().getHands().leftHand = x;
			}
		} else if(leftHandType == 3) { //ranged
			int id = WorldInput.parseInt(tokens[index]);
			index++;
			double weight = Double.parseDouble(tokens[index]);
			index++;
			int damage = WorldInput.parseInt(tokens[index]);
			index++;
			double aSpeed = Double.parseDouble(tokens[index]);
			index++;
			int intimidation = WorldInput.parseInt(tokens[index]);
			index++;
			int ammoMax = WorldInput.parseInt(tokens[index]);
			index++;
			int reloadCooldown = WorldInput.parseInt(tokens[index]);
			index++;
			double accuracy = Double.parseDouble(tokens[index]);
			index++;
			int volume = WorldInput.parseInt(tokens[index]);
			index++;
			int ammoCurrent = WorldInput.parseInt(tokens[index]);
			index++;
			
			Ranged e = new Ranged(Item.items[id].getTexture(), Item.items[id].getName(), id, weight, damage, aSpeed, 
					intimidation, ammoMax, reloadCooldown, ((Ranged)Item.items[id]).getRefilMaterial(), accuracy, volume);
			e.setAmmoCurrent(ammoCurrent);
			for(Item x: Player.getPlayerData().getInventory().InventoryItems) {
				if(x.getId() == e.getId())
					Player.getPlayerData().getHands().leftHand = x;
			}
		} else if(leftHandType == 4) { //tool
			int id = WorldInput.parseInt(tokens[index]);
			index++;
			double weight = Double.parseDouble(tokens[index]);
			index++;
			int damage = WorldInput.parseInt(tokens[index]);
			index++;
			double aSpeed = Double.parseDouble(tokens[index]);
			index++;
			int intimidation = WorldInput.parseInt(tokens[index]);
			index++;
			double range = Double.parseDouble(tokens[index]);
			index++;
			int volume = WorldInput.parseInt(tokens[index]);
			index++;
			double power = Double.parseDouble(tokens[index]);
			index++;
			int currentEndurance = WorldInput.parseInt(tokens[index]);
			index++;
			int endurance = WorldInput.parseInt(tokens[index]);
			index++;
			
			Tool e = new Tool(Item.items[id].getTexture(), Item.items[id].getName(), Item.items[id].getType(), id, weight, damage, aSpeed, 
					intimidation, range, volume, power, currentEndurance, endurance);
			for(Item x: Player.getPlayerData().getInventory().InventoryItems) {
				if(x.getId() == e.getId())
					Player.getPlayerData().getHands().leftHand = x;
			}
		} else if(leftHandType == 5) { //torch
			Torch e = new Torch();
			Player.getPlayerData().getHands().leftHand = e;
		} else if(leftHandType == 6) { //water container
			int id = WorldInput.parseInt(tokens[index]);
			index++;
			double weight = Double.parseDouble(tokens[index]);
			index++;
			int volume = WorldInput.parseInt(tokens[index]);
			index++;
			int currentCapacity = WorldInput.parseInt(tokens[index]);
			index++;
			int capacity = WorldInput.parseInt(tokens[index]);
			index++;
			
			WaterContainer e = new WaterContainer(Item.items[id].getTexture(), Item.items[id].getName(), id, weight, volume, currentCapacity, 
					capacity);
			for(Item x: Player.getPlayerData().getInventory().InventoryItems) {
				if(x.getId() == e.getId())
					Player.getPlayerData().getHands().leftHand = x;
			}
		} else if(leftHandType == 7) { //weapon
			int id = WorldInput.parseInt(tokens[index]);
			index++;
			double weight = Double.parseDouble(tokens[index]);
			index++;
			int damage = WorldInput.parseInt(tokens[index]);
			index++;
			double aSpeed = Double.parseDouble(tokens[index]);
			index++;
			int intimidation = WorldInput.parseInt(tokens[index]);
			index++;
			double range = Double.parseDouble(tokens[index]);
			index++;
			int volume = WorldInput.parseInt(tokens[index]);
			index++;
			int currentEndurance = WorldInput.parseInt(tokens[index]);
			index++;
			int endurance = WorldInput.parseInt(tokens[index]);
			index++;
			
			Weapon e = new Weapon(Item.items[id].getTexture(), Item.items[id].getName(), id, weight, damage, aSpeed, 
					intimidation, range, volume, currentEndurance, endurance);
			for(Item x: Player.getPlayerData().getInventory().InventoryItems) {
				if(x.getId() == e.getId())
					Player.getPlayerData().getHands().leftHand = x;
			}
		} else if(leftHandType == 8) { //item
			int id = WorldInput.parseInt(tokens[index]);
			index++;
			double weight = Double.parseDouble(tokens[index]);
			index++;
			int damage = WorldInput.parseInt(tokens[index]);
			index++;
			int volume = WorldInput.parseInt(tokens[index]);
			index++;
			int count = WorldInput.parseInt(tokens[index]);
			index++;
			
			Item e = new Item(Item.items[id].getTexture(), Item.items[id].getName(), id, Item.items[id].isStackable(), 
					Item.items[id].getType(), weight, damage, volume);
			e.setCount(count);
			for(Item x: Player.getPlayerData().getInventory().InventoryItems) {
				if(x.getId() == e.getId())
					Player.getPlayerData().getHands().leftHand = x;
			}
		} //if leftHandType is 0, there is no item in hand
		
		//RIGHT HAND
		int rightHandType = WorldInput.parseInt(tokens[index]);
		index++;
		if(rightHandType == 1) { //armor
			int id = WorldInput.parseInt(tokens[index]);
			index++;
			double weight = Double.parseDouble(tokens[index]);
			index++;
			int damage = WorldInput.parseInt(tokens[index]);
			index++;
			int volume = WorldInput.parseInt(tokens[index]);
			index++;
			int intimidation = WorldInput.parseInt(tokens[index]);
			index++;
			int resistance = WorldInput.parseInt(tokens[index]);
			index++;
			double healthRegen = Double.parseDouble(tokens[index]);
			index++;
			int currentEndurance = WorldInput.parseInt(tokens[index]);
			index++;
			int endurance = WorldInput.parseInt(tokens[index]);
			index++;
			
			Armor e = new Armor(Item.items[id].getTexture(), Item.items[id].getName(), id, Item.items[id].getType(), weight, damage, volume, 
					intimidation, resistance, healthRegen, currentEndurance, endurance);
			for(Item x: Player.getPlayerData().getInventory().InventoryItems) {
				if(x.getId() == e.getId())
					Player.getPlayerData().getHands().rightHand = x;
			}
		} else if(rightHandType == 2) { //food
			int id = WorldInput.parseInt(tokens[index]);
			index++;
			double weight = Double.parseDouble(tokens[index]);
			index++;
			int damage = WorldInput.parseInt(tokens[index]);
			index++;
			int volume = WorldInput.parseInt(tokens[index]);
			index++;
			int hunger = WorldInput.parseInt(tokens[index]);
			index++;
			int thirst = WorldInput.parseInt(tokens[index]);
			index++;
			int freshness = WorldInput.parseInt(tokens[index]);
			index++;
			
			Food e = new Food(Item.items[id].getTexture(), Item.items[id].getName(), id, weight, damage, volume, 
					hunger, thirst, ((Food)Item.items[id]).getFood(), freshness);
			for(Item x: Player.getPlayerData().getInventory().InventoryItems) {
				if(x.getId() == e.getId())
					Player.getPlayerData().getHands().rightHand = x;
			}
		} else if(rightHandType == 3) { //ranged
			int id = WorldInput.parseInt(tokens[index]);
			index++;
			double weight = Double.parseDouble(tokens[index]);
			index++;
			int damage = WorldInput.parseInt(tokens[index]);
			index++;
			double aSpeed = Double.parseDouble(tokens[index]);
			index++;
			int intimidation = WorldInput.parseInt(tokens[index]);
			index++;
			int ammoMax = WorldInput.parseInt(tokens[index]);
			index++;
			int reloadCooldown = WorldInput.parseInt(tokens[index]);
			index++;
			double accuracy = Double.parseDouble(tokens[index]);
			index++;
			int volume = WorldInput.parseInt(tokens[index]);
			index++;
			int ammoCurrent = WorldInput.parseInt(tokens[index]);
			index++;
			
			Ranged e = new Ranged(Item.items[id].getTexture(), Item.items[id].getName(), id, weight, damage, aSpeed, 
					intimidation, ammoMax, reloadCooldown, ((Ranged)Item.items[id]).getRefilMaterial(), accuracy, volume);
			e.setAmmoCurrent(ammoCurrent);
			for(Item x: Player.getPlayerData().getInventory().InventoryItems) {
				if(x.getId() == e.getId())
					Player.getPlayerData().getHands().rightHand = x;
			}
		} else if(rightHandType == 4) { //tool
			int id = WorldInput.parseInt(tokens[index]);
			index++;
			double weight = Double.parseDouble(tokens[index]);
			index++;
			int damage = WorldInput.parseInt(tokens[index]);
			index++;
			double aSpeed = Double.parseDouble(tokens[index]);
			index++;
			int intimidation = WorldInput.parseInt(tokens[index]);
			index++;
			double range = Double.parseDouble(tokens[index]);
			index++;
			int volume = WorldInput.parseInt(tokens[index]);
			index++;
			double power = Double.parseDouble(tokens[index]);
			index++;
			int currentEndurance = WorldInput.parseInt(tokens[index]);
			index++;
			int endurance = WorldInput.parseInt(tokens[index]);
			index++;
			
			Tool e = new Tool(Item.items[id].getTexture(), Item.items[id].getName(), Item.items[id].getType(), id, weight, damage, aSpeed, 
					intimidation, range, volume, power, currentEndurance, endurance);
			for(Item x: Player.getPlayerData().getInventory().InventoryItems) {
				if(x.getId() == e.getId())
					Player.getPlayerData().getHands().rightHand = x;
			}
		} else if(rightHandType == 5) { //torch
			Torch e = new Torch();
			Player.getPlayerData().getHands().rightHand = e;
		} else if(rightHandType == 6) { //water container
			int id = WorldInput.parseInt(tokens[index]);
			index++;
			double weight = Double.parseDouble(tokens[index]);
			index++;
			int volume = WorldInput.parseInt(tokens[index]);
			index++;
			int currentCapacity = WorldInput.parseInt(tokens[index]);
			index++;
			int capacity = WorldInput.parseInt(tokens[index]);
			index++;
			
			WaterContainer e = new WaterContainer(Item.items[id].getTexture(), Item.items[id].getName(), id, weight, volume, currentCapacity, 
					capacity);
			for(Item x: Player.getPlayerData().getInventory().InventoryItems) {
				if(x.getId() == e.getId())
					Player.getPlayerData().getHands().rightHand = x;
			}
		} else if(rightHandType == 7) { //weapon
			
			int id = WorldInput.parseInt(tokens[index]);
			index++;
			double weight = Double.parseDouble(tokens[index]);
			index++;
			int damage = WorldInput.parseInt(tokens[index]);
			index++;
			double aSpeed = Double.parseDouble(tokens[index]);
			index++;
			int intimidation = WorldInput.parseInt(tokens[index]);
			index++;
			double range = Double.parseDouble(tokens[index]);
			index++;
			int volume = WorldInput.parseInt(tokens[index]);
			index++;
			int currentEndurance = WorldInput.parseInt(tokens[index]);
			index++;
			int endurance = WorldInput.parseInt(tokens[index]);
			index++;
			
			Weapon e = new Weapon(Item.items[id].getTexture(), Item.items[id].getName(), id, weight, damage, aSpeed, 
					intimidation, range, volume, currentEndurance, endurance);
			for(Item x: Player.getPlayerData().getInventory().InventoryItems) {
				if(x.getId() == e.getId())
					Player.getPlayerData().getHands().rightHand = x;
			}
			
		} else if(rightHandType == 8) { //item
			int id = WorldInput.parseInt(tokens[index]);
			index++;
			double weight = Double.parseDouble(tokens[index]);
			index++;
			int damage = WorldInput.parseInt(tokens[index]);
			index++;
			int volume = WorldInput.parseInt(tokens[index]);
			index++;
			int count = WorldInput.parseInt(tokens[index]);
			index++;
			
			Item e = new Item(Item.items[id].getTexture(), Item.items[id].getName(), id, Item.items[id].isStackable(), 
					Item.items[id].getType(), weight, damage, volume);
			e.setCount(count);
			for(Item x: Player.getPlayerData().getInventory().InventoryItems) {
				if(x.getId() == e.getId())
					Player.getPlayerData().getHands().rightHand = x;
			}
		} //if rightHandType is 0, there is no item in hand
		
		//EQUIPMENT
		
		//HELMET
		int wearingHelmet = WorldInput.parseInt(tokens[index]);
		index++;
		if(wearingHelmet == 1) {
			int id = WorldInput.parseInt(tokens[index]);
			index++;
			double weight = Double.parseDouble(tokens[index]);
			index++;
			int damage = WorldInput.parseInt(tokens[index]);
			index++;
			int volume = WorldInput.parseInt(tokens[index]);
			index++;
			int intimidation = WorldInput.parseInt(tokens[index]);
			index++;
			int resistance = WorldInput.parseInt(tokens[index]);
			index++;
			double healthRegen = Double.parseDouble(tokens[index]);
			index++;
			int currentEndurance = WorldInput.parseInt(tokens[index]);
			index++;
			int endurance = WorldInput.parseInt(tokens[index]);
			index++;
			
			Armor e = new Armor(Item.items[id].getTexture(), Item.items[id].getName(), id, Item.items[id].getType(), weight, damage, volume, 
					intimidation, resistance, healthRegen, currentEndurance, endurance);
			
			for(Item x: Player.getPlayerData().getInventory().InventoryItems) {
				if(x instanceof Armor)
					if(x.getId() == e.getId() && ((Armor)x).currentEndurance == e.currentEndurance)
						Player.getPlayerData().getEquipment().helmet = (Armor) x;
			}
		}
		
		//CHESTPLATE
		int wearingChest = WorldInput.parseInt(tokens[index]);
		index++;
		if(wearingChest == 1) {
			int id = WorldInput.parseInt(tokens[index]);
			index++;
			double weight = Double.parseDouble(tokens[index]);
			index++;
			int damage = WorldInput.parseInt(tokens[index]);
			index++;
			int volume = WorldInput.parseInt(tokens[index]);
			index++;
			int intimidation = WorldInput.parseInt(tokens[index]);
			index++;
			int resistance = WorldInput.parseInt(tokens[index]);
			index++;
			double healthRegen = Double.parseDouble(tokens[index]);
			index++;
			int currentEndurance = WorldInput.parseInt(tokens[index]);
			index++;
			int endurance = WorldInput.parseInt(tokens[index]);
			index++;
			
			Armor e = new Armor(Item.items[id].getTexture(), Item.items[id].getName(), id, Item.items[id].getType(), weight, damage, volume, 
					intimidation, resistance, healthRegen, currentEndurance, endurance);
			
			for(Item x: Player.getPlayerData().getInventory().InventoryItems) {
				if(x instanceof Armor)
					if(x.getId() == e.getId() && ((Armor)x).currentEndurance == e.currentEndurance)
						Player.getPlayerData().getEquipment().chest = (Armor) x;
			}
		}
		
		//LEG
		int wearingLeg = WorldInput.parseInt(tokens[index]);
		index++;
		if(wearingLeg == 1) {
			int id = WorldInput.parseInt(tokens[index]);
			index++;
			double weight = Double.parseDouble(tokens[index]);
			index++;
			int damage = WorldInput.parseInt(tokens[index]);
			index++;
			int volume = WorldInput.parseInt(tokens[index]);
			index++;
			int intimidation = WorldInput.parseInt(tokens[index]);
			index++;
			int resistance = WorldInput.parseInt(tokens[index]);
			index++;
			double healthRegen = Double.parseDouble(tokens[index]);
			index++;
			int currentEndurance = WorldInput.parseInt(tokens[index]);
			index++;
			int endurance = WorldInput.parseInt(tokens[index]);
			index++;
			
			Armor e = new Armor(Item.items[id].getTexture(), Item.items[id].getName(), id, Item.items[id].getType(), weight, damage, volume, 
					intimidation, resistance, healthRegen, currentEndurance, endurance);
			
			for(Item x: Player.getPlayerData().getInventory().InventoryItems) {
				if(x instanceof Armor)
					if(x.getId() == e.getId() && ((Armor)x).currentEndurance == e.currentEndurance)
						Player.getPlayerData().getEquipment().leg = (Armor) x;
			}
		}
		
		//BOOT
		int wearingBoot = WorldInput.parseInt(tokens[index]);
		index++;
		if(wearingBoot == 1) {
			int id = WorldInput.parseInt(tokens[index]);
			index++;
			double weight = Double.parseDouble(tokens[index]);
			index++;
			int damage = WorldInput.parseInt(tokens[index]);
			index++;
			int volume = WorldInput.parseInt(tokens[index]);
			index++;
			int intimidation = WorldInput.parseInt(tokens[index]);
			index++;
			int resistance = WorldInput.parseInt(tokens[index]);
			index++;
			double healthRegen = Double.parseDouble(tokens[index]);
			index++;
			int currentEndurance = WorldInput.parseInt(tokens[index]);
			index++;
			int endurance = WorldInput.parseInt(tokens[index]);
			index++;
			
			Armor e = new Armor(Item.items[id].getTexture(), Item.items[id].getName(), id, Item.items[id].getType(), weight, damage, volume, 
					intimidation, resistance, healthRegen, currentEndurance, endurance);
			
			for(Item x: Player.getPlayerData().getInventory().InventoryItems) {
				if(x instanceof Armor)
					if(x.getId() == e.getId() && ((Armor)x).currentEndurance == e.currentEndurance)
						Player.getPlayerData().getEquipment().boot = (Armor) x;
			}
		}
		
		//GAUNTLETS
		int wearingGauntlet = WorldInput.parseInt(tokens[index]);
		index++;
		if(wearingGauntlet == 1) {
			int id = WorldInput.parseInt(tokens[index]);
			index++;
			double weight = Double.parseDouble(tokens[index]);
			index++;
			int damage = WorldInput.parseInt(tokens[index]);
			index++;
			int volume = WorldInput.parseInt(tokens[index]);
			index++;
			int intimidation = WorldInput.parseInt(tokens[index]);
			index++;
			int resistance = WorldInput.parseInt(tokens[index]);
			index++;
			double healthRegen = Double.parseDouble(tokens[index]);
			index++;
			int currentEndurance = WorldInput.parseInt(tokens[index]);
			index++;
			int endurance = WorldInput.parseInt(tokens[index]);
			index++;
			
			Armor e = new Armor(Item.items[id].getTexture(), Item.items[id].getName(), id, Item.items[id].getType(), weight, damage, volume, 
					intimidation, resistance, healthRegen, currentEndurance, endurance);
			
			for(Item x: Player.getPlayerData().getInventory().InventoryItems) {
				if(x instanceof Armor)
					if(x.getId() == e.getId() && ((Armor)x).currentEndurance == e.currentEndurance)
						Player.getPlayerData().getEquipment().gauntlet = (Armor) x;
			}
		}
		
	}

	private void loadChests() {
		String file = WorldInput.loadFileAsString(String.format("chests/%s",c.getMenuState().getWorldSelectState().getSelectedWorldName()));
		String[] tokens = file.split("\\s+"); // split up every number into the string tokens array
		
		int index = 0;
		
		//numChests
		//
		//topperX topperY
		//numItems chestType
		//num of a specific item
		//type of item (ex. armor, food)
		//constructor values
		
		int numChests = WorldInput.parseInt(tokens[index]);
		index++;
		for(int i = 0; i < numChests; i++) {
			
			//Chest coordinates
			int topperX = WorldInput.parseInt(tokens[index]);
			index++;
			int topperY = WorldInput.parseInt(tokens[index]);
			index++;
			
			//number of items
			int numItems = WorldInput.parseInt(tokens[index]);
			index++;
			
			//number of items
			int chestType = WorldInput.parseInt(tokens[index]);
			index++;
			
			Chest chest;
			
			if(chestType == 1) {
				chest = new WoodenCrate(topperX * Tile.TILEWIDTH, topperY * Tile.TILEHEIGHT, c);
			} else if(chestType == 2) {
				chest = new MetalContainer(topperX * Tile.TILEWIDTH, topperY * Tile.TILEHEIGHT, c);
			} else if(chestType == 3) {
				chest = new MetalChest(topperX * Tile.TILEWIDTH, topperY * Tile.TILEHEIGHT, c);
			} else { //default to wooden crate
				chest = new WoodenCrate(topperX * Tile.TILEWIDTH, topperY * Tile.TILEHEIGHT, c);
			}

			chest.getInventory().emptyChest();
			
			for(int j = 0; j < numItems; j++) {
				
				//number of that specific item
				int itemCount = WorldInput.parseInt(tokens[index]);
				index++;
				
				//type of item (ex. armor, food)
				int itemType = WorldInput.parseInt(tokens[index]);
				index++;
				
				if(itemType == 1) { //armor
					int id = WorldInput.parseInt(tokens[index]);
					index++;
					double weight = Double.parseDouble(tokens[index]);
					index++;
					int damage = WorldInput.parseInt(tokens[index]);
					index++;
					int volume = WorldInput.parseInt(tokens[index]);
					index++;
					int intimidation = WorldInput.parseInt(tokens[index]);
					index++;
					int resistance = WorldInput.parseInt(tokens[index]);
					index++;
					double healthRegen = Double.parseDouble(tokens[index]);
					index++;
					int currentEndurance = WorldInput.parseInt(tokens[index]);
					index++;
					int endurance = WorldInput.parseInt(tokens[index]);
					index++;
					
					Armor e = new Armor(Item.items[id].getTexture(), Item.items[id].getName(), id, Item.items[id].getType(), weight, damage, volume, 
							intimidation, resistance, healthRegen, currentEndurance, endurance);
					for(int c = 0; c < itemCount; c++)
						chest.getInventory().addItem(e); 
				} else if(itemType == 2) { //food
					int id = WorldInput.parseInt(tokens[index]);
					index++;
					double weight = Double.parseDouble(tokens[index]);
					index++;
					int damage = WorldInput.parseInt(tokens[index]);
					index++;
					int volume = WorldInput.parseInt(tokens[index]);
					index++;
					int hunger = WorldInput.parseInt(tokens[index]);
					index++;
					int thirst = WorldInput.parseInt(tokens[index]);
					index++;
					int freshness = WorldInput.parseInt(tokens[index]);
					index++;
					
					Food e = new Food(Item.items[id].getTexture(), Item.items[id].getName(), id, weight, damage, volume, 
							hunger, thirst, ((Food)Item.items[id]).getFood(), freshness);
					for(int c = 0; c < itemCount; c++)
						chest.getInventory().addItem(e); 
				} else if(itemType == 3) { //ranged
					int id = WorldInput.parseInt(tokens[index]);
					index++;
					double weight = Double.parseDouble(tokens[index]);
					index++;
					int damage = WorldInput.parseInt(tokens[index]);
					index++;
					double aSpeed = Double.parseDouble(tokens[index]);
					index++;
					int intimidation = WorldInput.parseInt(tokens[index]);
					index++;
					int ammoMax = WorldInput.parseInt(tokens[index]);
					index++;
					int reloadCooldown = WorldInput.parseInt(tokens[index]);
					index++;
					double accuracy = Double.parseDouble(tokens[index]);
					index++;
					int volume = WorldInput.parseInt(tokens[index]);
					index++;
					int ammoCurrent = WorldInput.parseInt(tokens[index]);
					index++;
					
					Ranged e = new Ranged(Item.items[id].getTexture(), Item.items[id].getName(), id, weight, damage, aSpeed, 
							intimidation, ammoMax, reloadCooldown, ((Ranged)Item.items[id]).getRefilMaterial(), accuracy, volume);
					e.setAmmoCurrent(ammoCurrent);
					for(int c = 0; c < itemCount; c++)
						chest.getInventory().addItem(e); 
				} else if(itemType == 4) { //tool
					int id = WorldInput.parseInt(tokens[index]);
					index++;
					double weight = Double.parseDouble(tokens[index]);
					index++;
					int damage = WorldInput.parseInt(tokens[index]);
					index++;
					double aSpeed = Double.parseDouble(tokens[index]);
					index++;
					int intimidation = WorldInput.parseInt(tokens[index]);
					index++;
					double range = Double.parseDouble(tokens[index]);
					index++;
					int volume = WorldInput.parseInt(tokens[index]);
					index++;
					double power = Double.parseDouble(tokens[index]);
					index++;
					int currentEndurance = WorldInput.parseInt(tokens[index]);
					index++;
					int endurance = WorldInput.parseInt(tokens[index]);
					index++;
					
					Tool e = new Tool(Item.items[id].getTexture(), Item.items[id].getName(), Item.items[id].getType(), id, weight, damage, aSpeed, 
							intimidation, range, volume, power, currentEndurance, endurance);
					for(int c = 0; c < itemCount; c++)
						chest.getInventory().addItem(e); 
				} else if(itemType == 5) { //torch
					Torch e = new Torch();
					for(int c = 0; c < itemCount; c++)
						chest.getInventory().addItem(e); 
				} else if(itemType == 6) { //water container
					int id = WorldInput.parseInt(tokens[index]);
					index++;
					double weight = Double.parseDouble(tokens[index]);
					index++;
					int volume = WorldInput.parseInt(tokens[index]);
					index++;
					int currentCapacity = WorldInput.parseInt(tokens[index]);
					index++;
					int capacity = WorldInput.parseInt(tokens[index]);
					index++;
					
					WaterContainer e = new WaterContainer(Item.items[id].getTexture(), Item.items[id].getName(), id, weight, volume, currentCapacity, 
							capacity);
					for(int c = 0; c < itemCount; c++)
						chest.getInventory().addItem(e); 
				} else if(itemType == 7) { //weapon
					int id = WorldInput.parseInt(tokens[index]);
					index++;
					double weight = Double.parseDouble(tokens[index]);
					index++;
					int damage = WorldInput.parseInt(tokens[index]);
					index++;
					double aSpeed = Double.parseDouble(tokens[index]);
					index++;
					int intimidation = WorldInput.parseInt(tokens[index]);
					index++;
					double range = Double.parseDouble(tokens[index]);
					index++;
					int volume = WorldInput.parseInt(tokens[index]);
					index++;
					int currentEndurance = WorldInput.parseInt(tokens[index]);
					index++;
					int endurance = WorldInput.parseInt(tokens[index]);
					index++;
					
					Weapon e = new Weapon(Item.items[id].getTexture(), Item.items[id].getName(), id, weight, damage, aSpeed, 
							intimidation, range, volume, currentEndurance, endurance);
					for(int c = 0; c < itemCount; c++)
						chest.getInventory().addItem(e); 
				} else if(itemType == 8) { //item
					int id = WorldInput.parseInt(tokens[index]);
					index++;
					double weight = Double.parseDouble(tokens[index]);
					index++;
					int damage = WorldInput.parseInt(tokens[index]);
					index++;
					int volume = WorldInput.parseInt(tokens[index]);
					index++;
					
					Item e = new Item(Item.items[id].getTexture(), Item.items[id].getName(), id, Item.items[id].isStackable(), 
							Item.items[id].getType(), weight, damage, volume);
					for(int c = 0; c < itemCount; c++)
						chest.getInventory().addItem(e); 
				}
				
			} //end of specific item loop
			
			entityManager.addEntity(chest);
			
		} //end of all items loop
		
	}
	
	private void loadTimedCraftingStructures() {
		
		String file = WorldInput.loadFileAsString(String.format("timedCraftingStructures/%s",c.getMenuState().getWorldSelectState().getSelectedWorldName()));
		String[] tokens = file.split("\\s+"); // split up every number into the string tokens array
		
		int index = 0;
		
		//total number of timed crafting structures
		//
		//topperX topperY
		//structureNum (105 = metallic oven, 106 = autocooker, 107 = disintegrator)
		//state of structure (0 = nothing inside, 1 = currently cooking (item in currentlySmelting map), 2 = finished cooking (item in products map)
		//itemId itemAmount
		
		int numTimedCraftingStructures = WorldInput.parseInt(tokens[index]);
		index++;
		
		for(int i = 0; i < numTimedCraftingStructures; i++) {
			
			//Chest coordinates
			int topperX = WorldInput.parseInt(tokens[index]);
			index++;
			int topperY = WorldInput.parseInt(tokens[index]);
			index++;
			
			//type of structure
			int structureNum = WorldInput.parseInt(tokens[index]);
			index++;
			
			//off, cooking, or finished cooking
			int state = WorldInput.parseInt(tokens[index]);
			index++;
			
			int id;
			int itemAmount;
			//one crafting structure will be added to entity list based on the structure num
			MetallicOven metallicOven = new MetallicOven(topperX * Tile.TILEWIDTH, topperY * Tile.TILEHEIGHT, c);
			AutoCooker autoCooker = new AutoCooker(topperX * Tile.TILEWIDTH, topperY * Tile.TILEHEIGHT, c);
			Disintegrator disintegrator = new Disintegrator(topperX * Tile.TILEWIDTH, topperY * Tile.TILEHEIGHT, c);
			
			if(state == 2) {
				id = WorldInput.parseInt(tokens[index]);
				index++;
				itemAmount = WorldInput.parseInt(tokens[index]);
				index++;
				
				//add item to appropriate structure as a product
				if(structureNum == 105) {
					metallicOven.getInventory().products.put(id, itemAmount);
				} else if(structureNum == 106) {
					autoCooker.getCraft().products.put(id, itemAmount);
				} else if(structureNum == 107) {
					disintegrator.getCraft().products.put(id, itemAmount);
				}
				
			} else if(state == 1) {
				id = WorldInput.parseInt(tokens[index]);
				index++;
				itemAmount = WorldInput.parseInt(tokens[index]);
				index++;
				
				//add item to appropriate structure to be cooked
				if(structureNum == 105) {
					metallicOven.getInventory().currentlySmelting.put(id, itemAmount);
				} else if(structureNum == 106) {
					autoCooker.getCraft().currentlyCooking.put(id, itemAmount);
				} else if(structureNum == 107) {
					disintegrator.getCraft().currentlySmelting.put(id, itemAmount);
				}
			}
			
			//add appropriate structure to entity manager
			if(structureNum == 105) {
				metallicOven.getInventory().setLastSmeltTimer(System.currentTimeMillis());
				entityManager.addEntity(metallicOven);
			} else if(structureNum == 106) {
				autoCooker.getCraft().setLastCookTimer(System.currentTimeMillis());
				entityManager.addEntity(autoCooker);
			} else if(structureNum == 107) {
				disintegrator.getCraft().setLastSmeltTimer(System.currentTimeMillis());
				entityManager.addEntity(disintegrator);
			}
			
		}
		
	}
	
	private void loadCreatures() {

		String file = WorldInput.loadFileAsString(String.format("creatures/%s",c.getMenuState().getWorldSelectState().getSelectedWorldName()));
		String[] tokens = file.split("\\s+"); // split up every number into the string tokens array
		
		int index = 0;
		
		//numCreatures
		//
		//creature name, creatureX, creatureY
		
		int numCreatures = WorldInput.parseInt(tokens[index]);
		index++;
		
		for(int i = 0; i < numCreatures; i++) {
			
			String creatureName = tokens[index];
			index++;
			int creatureX = WorldInput.parseInt(tokens[index]);
			index++;
			int creatureY = WorldInput.parseInt(tokens[index]);
			index++;
			
			//have to do this for every single creature
			if(creatureName.equals("awakenedSentinel")) {
				entityManager.addEntity(new AwakenedSentinel(creatureX, creatureY, c));
			} else if(creatureName.equals("boar")) {
				entityManager.addEntity(new Boar(creatureX, creatureY, c));
			} else if(creatureName.equals("brainFungui")) {
				entityManager.addEntity(new BrainFungui(creatureX, creatureY, c));
			} else if(creatureName.equals("chicken")) {
				entityManager.addEntity(new Chicken(creatureX, creatureY, c));
			} else if(creatureName.equals("crazedGoat")) {
				entityManager.addEntity(new CrazedGoat(creatureX, creatureY, c));
			} else if(creatureName.equals("deer")) {
				entityManager.addEntity(new Deer(creatureX, creatureY, c));
			} else if(creatureName.equals("deerDeer")) {
				entityManager.addEntity(new DeerDeer(creatureX, creatureY, c));
			} else if(creatureName.equals("desertScorpion")) {
				entityManager.addEntity(new DesertScorpion(creatureX, creatureY, c));
			} else if(creatureName.equals("extrafloralNectar")) {
				entityManager.addEntity(new ExtrafloralNectar(creatureX, creatureY, c));
			} else if(creatureName.equals("giantBeetle")) {
				entityManager.addEntity(new GiantBeetle(creatureX, creatureY, c));
			} else if(creatureName.equals("goat")) {
				entityManager.addEntity(new Goat(creatureX, creatureY, c));
			} else if(creatureName.equals("mutatedChicken")) {
				entityManager.addEntity(new MutatedChicken(creatureX, creatureY, c));
			} else if(creatureName.equals("mutatedDeer")) {
				entityManager.addEntity(new MutatedDeer(creatureX, creatureY, c));
			} else if(creatureName.equals("ostrich")) {
				entityManager.addEntity(new Ostrich(creatureX, creatureY, c));
			} else if(creatureName.equals("packAlpha")) {
				entityManager.addEntity(new PackAlpha(creatureX, creatureY, c));
			} else if(creatureName.equals("packMember")) {
				entityManager.addEntity(new PackMember(creatureX, creatureY, c));
			} else if(creatureName.equals("phasmatodea")) {
				entityManager.addEntity(new Phasmatodea(creatureX, creatureY, c));
			} else if(creatureName.equals("prettyShroom")) {
				entityManager.addEntity(new PrettyShroom(creatureX, creatureY, c));
			} else if(creatureName.equals("redGiantBeetle")) {
				entityManager.addEntity(new RedGiantBeetle(creatureX, creatureY, c));
			} else if(creatureName.equals("sandCreep")) {
				entityManager.addEntity(new SandCreep(creatureX, creatureY, c));
			} else if(creatureName.equals("scavenger")) {
				entityManager.addEntity(new Scavenger(creatureX, creatureY, c));
			} else if(creatureName.equals("sentry")) {
				entityManager.addEntity(new Sentry(creatureX, creatureY, c));
			} else if(creatureName.equals("sentryBroodMother")) {
				entityManager.addEntity(new SentryBroodMother(creatureX, creatureY, c));
			} else if(creatureName.equals("sentryMajor")) {
				entityManager.addEntity(new SentryMajor(creatureX, creatureY, c));
			} else if(creatureName.equals("sentryReplete")) {
				entityManager.addEntity(new SentryReplete(creatureX, creatureY, c));
			} else if(creatureName.equals("shroomPile")) {
				entityManager.addEntity(new ShroomPile(creatureX, creatureY, c));
			} else if(creatureName.equals("sleepingSentinel")) {
				entityManager.addEntity(new SleepingSentinel(creatureX, creatureY, c));
			} else if(creatureName.equals("vileSpawn")) {
				entityManager.addEntity(new VileSpawn(c));
			} else if(creatureName.equals("wonderingGhoul")) {
				entityManager.addEntity(new WonderingGhoul(creatureX, creatureY, c));
			} else if(creatureName.equals("woodenStick")) {
				entityManager.addEntity(new WoodenStick(creatureX, creatureY, c));
			} 
			
		}
	}
	
	private void loadRecipes() {
		
		String file = WorldInput.loadFileAsString(String.format("recipe/%s",c.getMenuState().getWorldSelectState().getSelectedWorldName()));
		String[] tokens = file.split("\\s+"); // split up every number into the string tokens array
		
		int index = 0;
		
		//total number of timed crafting structures
		//
		//recipeId recipeType (if recipeType == 0 Recipe; if recipeType == 1 WorkbenchRecipe; if recipeType == 2 SmithingTableRecipe)
		
		int numRecipes = WorldInput.parseInt(tokens[index]);
		index++;
		
		for(int i = 0; i < numRecipes; i++) {
			
			int recipeId = WorldInput.parseInt(tokens[index]);
			index++;
			int recipeType = WorldInput.parseInt(tokens[index]);
			index++;
			
			if(recipeType == 0) {
				Recipe recipe = new Recipe(recipeId, true);
			} else if(recipeType == 1) {
				WorkbenchRecipe recipe = new WorkbenchRecipe(recipeId, true);
			} else if(recipeType == 2) {
				SmithingTableRecipe recipe = new SmithingTableRecipe(recipeId, true);
			} 
			
		}
		
	}
	
    private boolean inMenu() {
    	if(Player.getPlayerData().getInventory().isActive() || Player.getPlayerData().getHandCraft().isActive() || 
    			isChestActive() || isMetallicOvenActive() || isWorkbenchActive() || isSmithingTableActive() || 
    			isDisintegratorActive() || isAnalyzerActive() || isPurifierActive() || isAutoCookerActive() || 
    			isAutoCookerV2Active() || isResearchTableActive())
    		return true;
    	else
    		return false;
    }
	
	// getters and setters
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public ItemManager getItemManager() {
		return itemManager;
	}

	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}

	public UIManager getUiManager() {
		return uiManager;
	}

	public void setUiManager(UIManager uiManager) {
		this.uiManager = uiManager;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int[][] getTopper() {
		return topper;
	}

	public ControlCenter getC() {
		return c;
	}

	public void setC(ControlCenter c) {
		this.c = c;
	}

	public MessageBox getMessageBox() {
		return messageBox;
	}

	public void setMessageBox(MessageBox messageBox) {
		this.messageBox = messageBox;
	}

	public EffectManager getEffects() {
		return effects;
	}

	public void setEffects(EffectManager effects) {
		this.effects = effects;
	}

	public boolean isChestActive() {
		return chestActive;
	}

	public void setChestActive(boolean chestActive) {
		this.chestActive = chestActive;
	}

	public Chest getChest() {
		return chest;
	}

	public void setChest(Chest chest) {
		this.chest = chest;
	}

	public boolean isMetallicOvenActive() {
		return metallicOvenActive;
	}

	public void setMetallicOvenActive(boolean metallicOvenActive) {
		this.metallicOvenActive = metallicOvenActive;
	}

	public MetallicOven getMetallicOven() {
		return metallicOven;
	}

	public void setMetallicOven(MetallicOven metallicOven) {
		this.metallicOven = metallicOven;
	}

	public boolean isWorkbenchActive() {
		return workbenchActive;
	}

	public void setWorkbenchActive(boolean workbenchActive) {
		this.workbenchActive = workbenchActive;
	}

	public Workbench getWorkbench() {
		return workbench;
	}

	public void setWorkbench(Workbench workbench) {
		this.workbench = workbench;
	}

	public boolean isSmithingTableActive() {
		return smithingTableActive;
	}

	public void setSmithingTableActive(boolean smithingTableActive) {
		this.smithingTableActive = smithingTableActive;
	}

	public SmithingTable getSmithingTable() {
		return smithingTable;
	}

	public void setSmithingTable(SmithingTable smithingTable) {
		this.smithingTable = smithingTable;
	}

	public boolean isDisintegratorActive() {
		return disintegratorActive;
	}

	public void setDisintegratorActive(boolean disintegratorActive) {
		this.disintegratorActive = disintegratorActive;
	}

	public Disintegrator getDisintegrator() {
		return disintegrator;
	}

	public void setDisintegrator(Disintegrator disintegrator) {
		this.disintegrator = disintegrator;
	}

	public boolean isAnalyzerActive() {
		return analyzerActive;
	}

	public void setAnalyzerActive(boolean analyzerActive) {
		this.analyzerActive = analyzerActive;
	}

	public Analyzer getAnalyzer() {
		return analyzer;
	}

	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}

	public boolean isPurifierActive() {
		return purifierActive;
	}

	public void setPurifierActive(boolean purifierActive) {
		this.purifierActive = purifierActive;
	}

	public Purifier getPurifier() {
		return purifier;
	}

	public void setPurifier(Purifier purifier) {
		this.purifier = purifier;
	}

	public boolean isAutoCookerActive() {
		return autoCookerActive;
	}

	public void setAutoCookerActive(boolean autoCookerActive) {
		this.autoCookerActive = autoCookerActive;
	}

	public AutoCooker getAutoCooker() {
		return autoCooker;
	}

	public void setAutoCooker(AutoCooker autoCooker) {
		this.autoCooker = autoCooker;
	}

	public boolean isAutoCookerV2Active() {
		return autoCookerV2Active;
	}

	public void setAutoCookerV2Active(boolean autoCookerV2Active) {
		this.autoCookerV2Active = autoCookerV2Active;
	}

	public AutoCookerV2Craft getAutoCookerV2() {
		return autoCookerV2;
	}

	public void setAutoCookerV2(AutoCookerV2Craft autoCookerV2) {
		this.autoCookerV2 = autoCookerV2;
	}
	
	public boolean isResearchTableActive() {
		return researchTableActive;
	}

	public void setResearchTableActive(boolean researchTableActive) {
		this.researchTableActive = researchTableActive;
	}

	public ResearchTable getResearchTable() {
		return researchTable;
	}

	public void setResearchTable(ResearchTable researchTable) {
		this.researchTable = researchTable;
	}

	public boolean isStructureCrafted() {
		return structureCrafted;
	}

	public void setStructureCrafted(boolean structureCrafted) {
		this.structureCrafted = structureCrafted;
	}

	public int getStructureNum() {
		return structureNum;
	}

	public void setStructureNum(int structureNum) {
		this.structureNum = structureNum;
	}

	/**
	 * @return the structureRecipe
	 */
	public Recipe getStructureRecipe() {
		return structureRecipe;
	}

	/**
	 * @param structureRecipe the structureRecipe to set
	 */
	public void setStructureRecipe(Recipe structureRecipe) {
		this.structureRecipe = structureRecipe;
	}

	/**
	 * @return the structureWorkbenchCrafted
	 */
	public boolean isStructureWorkbenchCrafted() {
		return structureWorkbenchCrafted;
	}

	/**
	 * @param structureWorkbenchCrafted the structureWorkbenchCrafted to set
	 */
	public void setStructureWorkbenchCrafted(boolean structureWorkbenchCrafted) {
		this.structureWorkbenchCrafted = structureWorkbenchCrafted;
	}

	/**
	 * @return the structureWorkbenchRecipe
	 */
	public WorkbenchRecipe getStructureWorkbenchRecipe() {
		return structureWorkbenchRecipe;
	}

	/**
	 * @param structureWorkbenchRecipe the structureWorkbenchRecipe to set
	 */
	public void setStructureWorkbenchRecipe(WorkbenchRecipe structureWorkbenchRecipe) {
		this.structureWorkbenchRecipe = structureWorkbenchRecipe;
	}

	public boolean isCurrentlyBuildingStructure() {
		return currentlyBuildingStructure;
	}

	public void setCurrentlyBuildingStructure(boolean currentlyBuildingStructure) {
		this.currentlyBuildingStructure = currentlyBuildingStructure;
	}

	public void addDeathAnimation(DeathAnimation d) {
		deathManager.add(d);
	}
	
	public int[][] getFloor() {
		return floor;
	}

	public void setFloor(int[][] floor) {
		this.floor = floor;
	}
	
	public long getLastSentryQueenSpawnTimer() {
		return lastSentryQueenSpawnTimer;
	}

	public void setLastSentryQueenSpawnTimer(long lastSentryQueenSpawnTimer) {
		this.lastSentryQueenSpawnTimer = lastSentryQueenSpawnTimer;
	}
	
	public boolean isPackAlphaActive() {
		return packAlphaActive;
	}

	public void setPackAlphaActive(boolean packAlphaActive) {
		this.packAlphaActive = packAlphaActive;
	}

	public int[][] getTerrain() {
		return terrain;
	}

	public void setTerrain(int[][] terrain) {
		this.terrain = terrain;
	}
	
	public boolean isGameCompleted() {
		return gameCompleted;
	}

	public void setGameCompleted(boolean gameCompleted) {
		this.gameCompleted = gameCompleted;
	}
	
	public WorldSaver getWorldSaver() {
		return worldSaver;
	}

	public void setWorldSaver(WorldSaver worldSaver) {
		this.worldSaver = worldSaver;
	}
	
}
