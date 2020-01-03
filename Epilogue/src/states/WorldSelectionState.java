package states;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.util.ArrayList;

import alphaPackage.ControlCenter;
import alphaPackage.Display;
import audio.MusicPlayer;
import graphics.Animation;
import graphics.Assets;
import graphics.CustomTextWritter;
import utils.ClickListener;
import utils.ImageButton;
import utils.UIManager;
import world.TerrainWriter;
import world.WorldInput;
import world.WorldWritter;

public class WorldSelectionState extends State {

	// inherent fields
	private UIManager uiManager;
	private UIManager uiManager2; //used for delete menue
	private ControlCenter c;

	// next state
	public CharacterSelectionState characterState;
	public GameState gameState;
	public WorldCreationState worldCreationState;

	// world selected
	public static int worldSelected;

	// number of worlds
	private static int numWorlds = 0;

	private int scroll = 0;
	private ArrayList<String> worldNames = new ArrayList<String>();
	private String selectedWorldName;
	private boolean worldSelectButtonPressed = false;
	private ArrayList<File> listOfFiles = new ArrayList<File>();
	//File[] listOfFiles;

	// loading variables
	private boolean loading = false;

	// character properties
	private int charHealth = 1000, charDamageScale = 50, charIntelligence = 5, 
			charIntimidation = 20, charEndurability = 500, charResistance = 20;
	private double charRunSpeed = 4;
	private String charName = "Shah";
	private int characterID = 1;
	private int savedHealth = 1000, savedDamageScale = 50, savedIntelligence = 5, 
			savedIntimidation = 20, savedEndurability = 500, savedResistance = 20;
	private int hunger, thirst, energy; 
	private int dayNum; 
	private double time;
	private int playerX, playerY; 
	private int basicSurvivalXP, combatXP, cookingXP, buildingXP; 

	private long lastTickTimer, TickCooldown = 1, TickTimer = 0;
	
	private Animation background;
	private boolean deleteMenu = false;
	
	//Varaibles displayed in box
	int gameMode;
	int worldSize;
	int completed;

	// constructor takes in the ControlCenter class for access
	public WorldSelectionState(ControlCenter c) {

		this.c = c;

		setupFileList();

		MusicPlayer.StopMusic();

		//MusicPlayer.playMusic("audio/introAudio.wav");

		// initializes the uiManager for UI accesses
		setUiManager(new UIManager());
		c.getMouseManager().setUIManager(getUiManager());
		uiManager2 = new UIManager();

		reloadButtons();

		background = new Animation(50, Assets.menuBackground, true);

	}	
	
	private void setupFileList() {
		//reset the array lists
		worldNames.clear();
		listOfFiles.clear();
		
		//setup the world select buttons
		File Files = new File("worlds");
		setNumWorlds(Files.list().length);
		
		for(int i = 0; i < Files.listFiles().length; i++) 
			listOfFiles.add(Files.listFiles()[i]);

		//Add all world names to the arraylist
		for(int i = 0; i < listOfFiles.size(); i++) 
			worldNames.add(listOfFiles.get(i).getName());
		System.out.println(worldNames); //test
	}

	//Adds all buttons
	private void reloadButtons() {
		getUiManager().getObjects().clear();

		addWorldButtons();
		addScrollButtons();
		addOptionButtons();
		
		uiManager2.getObjects().clear();
		addDeleteMenuButtons();
	}

	//Add world buttons
	private void addWorldButtons() {

		for(int i = scroll; i < scroll + 5 && i < worldNames.size(); i++) { //Assets.sRight

			int x = i;

			getUiManager().addObject( 
					new ImageButton(424, 238 + (i - scroll) * 81, (int)(529*ControlCenter.scaleValue), (int)(73*ControlCenter.scaleValue), Assets.blank, new ClickListener() {

						@Override
						public void onClick() {

							worldSelected = x;
							selectedWorldName = worldNames.get(x);
							readDataFile(selectedWorldName);
							worldSelectButtonPressed = true;

						}

					}));

		}

	}

	//Adds scroll buttons
	private void addScrollButtons() {

		//scroll buttons
		getUiManager().addObject( //scroll up
				new ImageButton(180, 230, (int)(90*ControlCenter.scaleValue), (int)(190*ControlCenter.scaleValue), Assets.up, new ClickListener() {

					@Override
					public void onClick() {

						if(scroll > 0)
							scroll--;
						
						reloadButtons();
						
						worldSelectButtonPressed = false;

					}

				}));

		getUiManager().addObject( //scroll down
				new ImageButton(180, 440, (int)(90*ControlCenter.scaleValue), (int)(190*ControlCenter.scaleValue), Assets.down, new ClickListener() {

					@Override
					public void onClick() {

						if(scroll < numWorlds - 1)
							scroll++;
						
						reloadButtons();
						
						worldSelectButtonPressed = false;

					}

				}));

	}

	//Adds play, delete, create world, and back buttons
	private void addOptionButtons() {

		//this button allows you to create new worlds
		getUiManager().addObject(
				new ImageButton(450, 160, 400, 40, Assets.createWorld, new ClickListener() {

					@Override
					public void onClick() {

						worldCreationState = new WorldCreationState(c);

						State.setState(worldCreationState);

						System.out.println(numWorlds); //test
						worldSelectButtonPressed = false;

					}

				}));

		//back button to return to menu state
		getUiManager().addObject(
				new ImageButton(55, 720, (int)(48*ControlCenter.scaleValue), (int)(44*ControlCenter.scaleValue), Assets.sLeft, new ClickListener() {

					@Override
					public void onClick() {

						State.setState(c.getMenuState());
						c.getMouseManager().setUiManager(c.getMenuState().getUiManager());
						worldSelectButtonPressed = false;

					}

				}));

		//Play button
		getUiManager().addObject( 
				new ImageButton(1000, 230, (int)(90*ControlCenter.scaleValue), (int)(190*ControlCenter.scaleValue), Assets.play, new ClickListener() {

					@Override
					public void onClick() {

						if(worldSelectButtonPressed) {

							loading = true;
							//nextState(); takes to character select

							//using the WorldLoader class to generate a new randomized map onto a .txt file
							//worldLoader = new WorldWritter(2500);
							//terrainLoader = new TerrainWriter(2500);

							gameState = new GameState(c);

							c.getMouseManager().setUiManager(null);

							State.setState(gameState);

							gameState.getWorldGenerator().createPlayer();

							loading = false;

						}

					}

				}));

		//Delete world button
		getUiManager().addObject( 
				new ImageButton(1000, 440, (int)(90*ControlCenter.scaleValue), (int)(190*ControlCenter.scaleValue), Assets.delete, new ClickListener() {

					@Override
					public void onClick() {

						if(worldSelectButtonPressed) {

							deleteMenu = true;
							c.getMouseManager().setUIManager(c.getMenuState().getWorldSelectState().uiManager2);

							//nextState(); takes to character select

							//using the WorldLoader class to generate a new randomized map onto a .txt file
							//worldLoader = new WorldWritter(2500);
							//terrainLoader = new TerrainWriter(2500);



						}

					}

				}));

	}

	private void addDeleteMenuButtons() {

		//Delete world button
		uiManager2.addObject(
				new ImageButton(c.getWidth() / 2 - 150 - 110, 350, (int)(220*ControlCenter.scaleValue), (int)(47*ControlCenter.scaleValue), Assets.selectButton, new ClickListener() {

					@Override
					public void onClick() {

						deleteWorld();
						deleteMenu = false;
						c.getMouseManager().setUIManager(c.getMenuState().getWorldSelectState().getUiManager());

					}

				}));

		//Don't delete world button
		uiManager2.addObject(
				new ImageButton(c.getWidth() / 2 + 150 - 110, 350, (int)(220*ControlCenter.scaleValue), (int)(47*ControlCenter.scaleValue), Assets.selectButton, new ClickListener() {

					@Override
					public void onClick() {

						deleteMenu = false;
						c.getMouseManager().setUIManager(c.getMenuState().getWorldSelectState().getUiManager());

					}

				}));

	}

	public void deleteWorld() { //delete the world files

		String worldData = String.format("worldData/%s", selectedWorldName);
		File worldDataFilepath = new File(worldData);
		String terrain = String.format("terrain/%s", selectedWorldName);
		File terrainFilepath = new File(terrain);
		String topper = String.format("topper/%s", selectedWorldName);
		File topperFilepath = new File(topper);
		String world = String.format("worlds/%s", selectedWorldName);
		File worldFilepath = new File(world);
		String chest = String.format("chests/%s", selectedWorldName);
		File chestFilepath = new File(chest);
		String timedCraftingStructures = String.format("timedCraftingStructures/%s", selectedWorldName);
		File timedCraftingStructuresFilepath = new File(timedCraftingStructures);
		String creatures = String.format("creatures/%s", selectedWorldName);
		File creaturesFilepath = new File(creatures);
		String platform = String.format("platform/%s", selectedWorldName);
		File platformFilepath = new File(platform);
		String recipe = String.format("recipe/%s", selectedWorldName);
		File recipeFilepath = new File(recipe);

		if(worldDataFilepath.exists()) {
			worldDataFilepath.delete();
		}
		if(terrainFilepath.exists()) {
			terrainFilepath.delete();
		}
		if(topperFilepath.exists()) {
			topperFilepath.delete();
		}
		if(worldFilepath.exists()) {
			worldFilepath.delete();
		}
		if(chestFilepath.exists()) {
			chestFilepath.delete();
		}
		if(timedCraftingStructuresFilepath.exists()) {
			timedCraftingStructuresFilepath.delete();
		}
		if(creaturesFilepath.exists()) {
			creaturesFilepath.delete();
		}
		if(platformFilepath.exists()) {
			platformFilepath.delete();
		}
		if(recipeFilepath.exists()) {
			recipeFilepath.delete();
		}
		
		worldSelectButtonPressed = false;
		setupFileList();
		reloadButtons();
	}

	public void readDataFile(String selectedWorldName) {

		String file = WorldInput.loadFileAsString(String.format("worldData/%s",selectedWorldName));
		String[] tokens = file.split("\\s+"); // split up every number into the string tokens array

		gameMode = WorldInput.parseInt(tokens[0]);
		System.out.println("game mode = " + gameMode);
		//worldSize = WorldInput.parseInt (tokens[1]);
		//compeleted = WorldInput.parseInt(tokens[2]);
		charName = tokens[3];
		savedHealth = WorldInput.parseInt(tokens[4]);
		charRunSpeed = Double.parseDouble(tokens[5]);
		savedEndurability = WorldInput.parseInt(tokens[6]);
		savedDamageScale = WorldInput.parseInt(tokens[7]);
		savedIntimidation = WorldInput.parseInt(tokens[8]);
		savedIntelligence = WorldInput.parseInt(tokens[9]);
		savedResistance = WorldInput.parseInt(tokens[10]);
		hunger = WorldInput.parseInt(tokens[11]);
		thirst = WorldInput.parseInt(tokens[12]);
		energy = WorldInput.parseInt(tokens[13]);
		setDayNum(WorldInput.parseInt(tokens[14]));
		setTime(Double.parseDouble(tokens[15]));
		playerX =  WorldInput.parseInt(tokens[16]);
		playerY =  WorldInput.parseInt(tokens[17]);
		basicSurvivalXP =  WorldInput.parseInt(tokens[18]);
		combatXP =  WorldInput.parseInt(tokens[19]);
		cookingXP =  WorldInput.parseInt(tokens[20]);
		buildingXP =  WorldInput.parseInt(tokens[21]);
		
		if (charName.equals("Rod")) {
			charName = "Rod";
			charHealth = 1000;
			charRunSpeed = 2.3;
			charEndurability = 500;
			charIntimidation = 20;
			charIntelligence = 6;
			charDamageScale = 40;
			charResistance = 12;
		} else if (charName.equals("Ray")) {
			charName = "Ray";
			charHealth = 750;
			charRunSpeed = 2.7;
			charEndurability = 400;
			charIntimidation = 10;
			charIntelligence = 5;
			charDamageScale = 65;
			charResistance = 5;
		} else if (charName.equals("Batash")) {
			charName = "Batash";
			charHealth = 1550;
			charRunSpeed = 1.3;
			charEndurability = 750;
			charIntimidation = 30;
			charIntelligence = 3;
			charDamageScale = 85;
			charResistance = 18;
		} else if (charName.equals("Bharat_Sinai_Peddi")) {
			charName = "Bharat_Sinai_Peddi";
			charHealth = 850;
			charRunSpeed = 1.8;
			charEndurability = 550;
			charIntimidation = 45;
			charIntelligence = 10;
			charDamageScale = 30;
			charResistance = 20;
		} else if (charName.equals("Paris")) {
			charName = "Paris";
			charHealth = 600;
			charRunSpeed = 2;
			charEndurability = 300;
			charIntimidation = 10;
			charIntelligence = 7;
			charDamageScale = 20;
			charResistance = 10;
		} else if (charName.equals("Mr.F")) {
			charName = "Mr.F";
			charHealth = 9999;
			charRunSpeed = 99.99;
			charEndurability = 9999;
			charIntimidation = 9999;
			charIntelligence = 9999;
			charDamageScale = 9999;
			charResistance = 9999;
		}
		
		/*
		if (charName.equals("Shah")) {
			charName = "Shah";
			charHealth = 1000;
			charRunSpeed = 4;
			charEndurability = 500;
			charIntimidation = 20;
			charIntelligence = 5;
			charDamageScale = 50;
			charResistance = 20;
		} else if (charName.equals("Quinn")) {
			charName = "Quinn";
			charHealth = 1150;
			charRunSpeed = 4;
			charEndurability = 400;
			charIntimidation = 20;
			charIntelligence = 7;
			charDamageScale = 85;
			charResistance = 15;
		} else if (charName.equals("Sun")) {
			charName = "Sun";
			charHealth = 650;
			charRunSpeed = 6;
			charEndurability = 400;
			charIntimidation = 15;
			charIntelligence = 7;
			charDamageScale = 180;
			charResistance = 5;
		} else if (charName.equals("Wynn")) {
			charName = "Wynn";
			charHealth = 900;
			charRunSpeed = 5;
			charEndurability = 950;
			charIntimidation = 20;
			charIntelligence = 5;
			charDamageScale = 110;
			charResistance = 20;
		} else if (charName.equals("Ford")) {
			charName = "Ford";
			charHealth = 850;
			charRunSpeed = 5.5;
			charEndurability = 400;
			charIntimidation = 10;
			charIntelligence = 10;
			charDamageScale = 90;
			charResistance = 10;
		} else if (charName.equals("Bahtia")) {
			charName = "Bahtia";
			charHealth = 1450;
			charRunSpeed = 3.5;
			charEndurability = 300;
			charIntimidation = 35;
			charIntelligence = 3;
			charDamageScale = 200;
			charResistance = 20;
		} else if (charName.equals("Tank")) {
			charName = "Tank";
			charHealth = 1850;
			charRunSpeed = 3;
			charEndurability = 500;
			charIntimidation = 25;
			charIntelligence = 6;
			charDamageScale = 55;
			charResistance = 50;
		} else if (charName.equals("Shreyas")) {
			charName = "Shreyas";
			charHealth = 1100;
			charRunSpeed = 5;
			charEndurability = 400;
			charIntimidation = 45;
			charIntelligence = 5;
			charDamageScale = 90;
			charResistance = 25;
		} else if (charName.equals("Shreyas")) {
			charName = "Shreyas";
			charHealth = 800;
			charRunSpeed = 6;
			charEndurability = 400;
			charIntimidation = 20;
			charIntelligence = 6;
			charDamageScale = 150;
			charResistance = 10;
		} else if (charName.equals("Mr.F")) {
			charName = "Mr.F";
			charHealth = 9999;
			charRunSpeed = 99.99;
			charEndurability = 9999;
			charIntimidation = 9999;
			charIntelligence = 9999;
			charDamageScale = 9999;
			charResistance = 9999;
		}
		*/

	}

	private void readMenuData(String selectedWorldName) {
		String file = WorldInput.loadFileAsString(String.format("worldData/%s",selectedWorldName));
		String[] tokens = file.split("\\s+"); // split up every number into the string tokens array

		gameMode = WorldInput.parseInt(tokens[0]);
		worldSize = WorldInput.parseInt(tokens[1]);
		completed = WorldInput.parseInt(tokens[2]);
	}
	
	@Override
	public void tick() {
		getUiManager().tick();
		background.tick();

	}

	@Override
	public void render(Graphics g) {

		//fills the background with black(default color)
		g.fillRect(0, 0, c.getWidth(), c.getHeight());
		g.drawImage(background.getCurrentFrame(), 0, 0, c.getWidth(), c.getHeight(), null);
		
		//renders the UIObjects
		if(deleteMenu) {
			uiManager2.render(g);
			CustomTextWritter.drawString(g, "Are you sure you want to delete \"" + selectedWorldName + "\"", c.getWidth() / 2, c.getHeight() / 2 - 170, true,
					Color.WHITE, Assets.font36);
			CustomTextWritter.drawString(g, "Yes", c.getWidth() / 2 - 150, 300, true,
					Color.WHITE, Assets.font36);
			CustomTextWritter.drawString(g, "No", c.getWidth() / 2 + 150, 300, true,
					Color.WHITE, Assets.font36);
		}else {
			
			g.drawImage(Assets.worldSelectionInterface, 100, 100, c.getWidth() - 200, c.getHeight() - 200, null);
			
			if(worldSelectButtonPressed) {
				g.setColor(Color.GREEN);
				g.fillRect(424, 238 + 81 * (worldSelected - scroll), 529, 73);
				g.fillRect(316, 238 + 81 * (worldSelected - scroll), 97, 73);
			}

			for(int i = scroll; i < scroll + 5 && i < worldNames.size(); i++) {
				CustomTextWritter.drawString(g, worldNames.get(i), 440, 285 + (i - scroll) * 81, false,
						Color.WHITE, Assets.font36);
				readMenuData(worldNames.get(i));
				if(gameMode == 1) {
					CustomTextWritter.drawString(g, String.format("Hardcore"), 316, 258 + (i - scroll) * 81, false,
							Color.WHITE, Assets.font16);
				} else {
					CustomTextWritter.drawString(g, String.format("Normal"), 316, 258 + (i - scroll) * 81, false,
							Color.WHITE, Assets.font16);
				}
				
				if(worldSize == 1500) {
					CustomTextWritter.drawString(g, String.format("Sml World"), 316, 278 + (i - scroll) * 81, false,
							Color.WHITE, Assets.font16);
				} else if(worldSize == 2500) {
					CustomTextWritter.drawString(g, String.format("Med World"), 316, 278 + (i - scroll) * 81, false,
							Color.WHITE, Assets.font16);
				} else if(worldSize == 3500) {
					CustomTextWritter.drawString(g, String.format("Lrg World"), 316, 278 + (i - scroll) * 81, false,
							Color.WHITE, Assets.font16);
				}

				if(completed == 1) {
					CustomTextWritter.drawString(g, String.format("Complete"), 316, 298 + (i - scroll) * 81, false,
							Color.WHITE, Assets.font16);
				} else { //Don't print anything if game isn't finished
					//CustomTextWritter.drawString(g, String.format(""), 316, 298 + (i - scroll) * 81, false,
					//		Color.WHITE, Assets.font16);
				}

			}
			
			getUiManager().render(g);
		}

		if(loading) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, c.getWidth(), c.getHeight());
			g.drawImage(background.getCurrentFrame(), 0, 0, c.getWidth(), c.getHeight(), null);

			CustomTextWritter.drawString(g, "Generating World", c.getWidth() / 2, c.getHeight() / 2 - 70, true,
					Color.WHITE, Assets.font36);
		}

	}

	// getters and setters
	public ControlCenter getControlCenter() {
		return c;
	}

	public void setC(ControlCenter c) {
		this.c = c;
	}

	public CharacterSelectionState getCharacterState() {
		return characterState;
	}

	public void setCharacterState(CharacterSelectionState characterState) {
		this.characterState = characterState;
	}

	public static int getWorldSelected() {
		return worldSelected;
	}

	public static void setWorldSelected(int worldSelected) {
		WorldSelectionState.worldSelected = worldSelected;
	}

	public static int getNumWorlds() {
		return numWorlds;
	}

	public static void setNumWorlds(int numWorlds) {
		WorldSelectionState.numWorlds = numWorlds;
	}

	public int getScroll() {
		return scroll;
	}

	public void setScroll(int scroll) {
		this.scroll = scroll;
	}

	public ArrayList<String> getWorldNames() {
		return worldNames;
	}

	public void setWorldNames(ArrayList<String> worldNames) {
		this.worldNames = worldNames;
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public String getSelectedWorldName() {
		return selectedWorldName;
	}

	public void setSelectedWorldName(String selectedWorldName) {
		this.selectedWorldName = selectedWorldName;
	}

	public WorldCreationState getWorldCreationState() {
		return worldCreationState;
	}

	public void setWorldCreationState(WorldCreationState worldCreationState) {
		this.worldCreationState = worldCreationState;
	}

	public UIManager getUiManager() {
		return uiManager;
	}

	public void setUiManager(UIManager uiManager) {
		this.uiManager = uiManager;
	}

	//character variables getters and setters
	public int getCharHealth() {
		return charHealth;
	}

	public void setCharHealth(int charHealth) {
		this.charHealth = charHealth;
	}

	public int getCharDamageScale() {
		return charDamageScale;
	}

	public void setCharDamageScale(int charDamageScale) {
		this.charDamageScale = charDamageScale;
	}

	public int getCharEndurability() {
		return charEndurability;
	}

	public void setCharEndurability(int charEnduability) {
		this.charEndurability = charEnduability;
	}

	public int getCharResistance() {
		return charResistance;
	}

	public void setCharResistance(int charResistance) {
		this.charResistance = charResistance;
	}

	public double getCharRunSpeed() {
		return charRunSpeed;
	}

	public void setCharRunSpeed(double charRunSpeed) {
		this.charRunSpeed = charRunSpeed;
	}

	public String getCharName() {
		return charName;
	}

	public void setCharName(String charName) {
		this.charName = charName;
	}

	public int getCharacterID() {
		return characterID;
	}

	public void setCharacterID(int characterID) {
		this.characterID = characterID;
	}

	public int getCharIntelligence() {
		return charIntelligence;
	}

	public void setCharIntelligence(int charIntelligence) {
		this.charIntelligence = charIntelligence;
	}

	public int getCharIntimidation() {
		return charIntimidation;
	}

	public void setCharIntimidation(int charIntimidation) {
		this.charIntimidation = charIntimidation;
	}
	
	public int getSavedHealth() {
		return savedHealth;
	}

	public void setSavedHealth(int savedHealth) {
		this.savedHealth = savedHealth;
	}

	public int getSavedDamageScale() {
		return savedDamageScale;
	}

	public void setSavedDamageScale(int savedDamageScale) {
		this.savedDamageScale = savedDamageScale;
	}

	public int getSavedIntelligence() {
		return savedIntelligence;
	}

	public void setSavedIntelligence(int savedIntelligence) {
		this.savedIntelligence = savedIntelligence;
	}

	public int getSavedIntimidation() {
		return savedIntimidation;
	}

	public void setSavedIntimidation(int savedIntimidation) {
		this.savedIntimidation = savedIntimidation;
	}

	public int getSavedEndurability() {
		return savedEndurability;
	}

	public void setSavedEndurability(int savedEndurability) {
		this.savedEndurability = savedEndurability;
	}

	public int getSavedResistance() {
		return savedResistance;
	}

	public void setSavedResistance(int savedResistance) {
		this.savedResistance = savedResistance;
	}

	public int getHunger() {
		return hunger;
	}

	public void setHunger(int hunger) {
		this.hunger = hunger;
	}

	public int getThirst() {
		return thirst;
	}

	public void setThirst(int thirst) {
		this.thirst = thirst;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public int getDayNum() {
		return dayNum;
	}

	public void setDayNum(int dayNum) {
		this.dayNum = dayNum;
	}

	public int getPlayerX() {
		return playerX;
	}

	public void setPlayerX(int playerX) {
		this.playerX = playerX;
	}

	public int getPlayerY() {
		return playerY;
	}

	public void setPlayerY(int playerY) {
		this.playerY = playerY;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public int getBasicSurvivalXP() {
		return basicSurvivalXP;
	}

	public void setBasicSurvivalXP(int basicSurvivalXP) {
		this.basicSurvivalXP = basicSurvivalXP;
	}

	public int getCombatXP() {
		return combatXP;
	}

	public void setCombatXP(int combatXP) {
		this.combatXP = combatXP;
	}

	public int getCookingXP() {
		return cookingXP;
	}

	public void setCookingXP(int cookingXP) {
		this.cookingXP = cookingXP;
	}

	public int getBuildingXP() {
		return buildingXP;
	}

	public void setBuildingXP(int buildingXP) {
		this.buildingXP = buildingXP;
	}

	public int getGameMode() {
		return gameMode;
	}

	public void setGameMode(int gameMode) {
		this.gameMode = gameMode;
	}

}