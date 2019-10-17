package states;

import java.awt.Color;
import java.awt.Graphics;

import alphaPackage.ControlCenter;
import audio.AudioPlayer;
import audio.MusicPlayer;
import graphics.Animation;
import graphics.Assets;
import graphics.CT;
import graphics.CustomTextWritter;
import utils.ClickListener;
import utils.ImageButton;
import utils.UIManager;
import world.TerrainWriter;
import world.TopperWriter;
import world.WorldDataWriter;
import world.WorldInput;
import world.WorldWritter;

/*
 * character selection state
 * 
 * subclass of State
 */
public class CharacterSelectionState extends State {

	//Worlds
	private WorldDataWriter dataLoader;
	private WorldWritter worldLoader;
	private TerrainWriter terrainLoader;
	private TopperWriter topperLoader;
	//public GameState gameState;

	// inherent fields
	private UIManager uiManager;
	private ControlCenter c;

	// world properties
	private int gameMode; //0 = normal / 1 = hardcore
	private int worldSize; //length of the world | 2500 = small / 5000 = medium / 100000 = large
	private int[][] tiles;
	private int[][] terrain;

	// character properties
	private int charHealth = 1000, charDamageScale = 50, charIntelligence = 5, 
			charIntimidation = 20, charEndurability = 500, charResistance = 20;
	private double charRunSpeed = 2.3;
	private String charName = "Rod";
	private int characterID = 1;

	//loading variables
	private boolean loading = false;
	private Animation background = new Animation(50, Assets.menuBackground, true);

	// constructor takes in the ControlCenter class for access
	public CharacterSelectionState(ControlCenter c, int gameMode, int worldSize) {
		this.c = c;
		this.gameMode = gameMode;
		this.worldSize = worldSize;

		// initializes the uiManager for UI accesses
		uiManager = new UIManager();
		c.getMouseManager().setUIManager(uiManager);

		// adding the buttons
		uiManager.addObject(
				new ImageButton((int)(176*ControlCenter.scaleValue), (int)((c.getHeight() - 205)*ControlCenter.scaleValue), (int)(162*ControlCenter.scaleValue), (int)(48*ControlCenter.scaleValue), Assets.sLeft, new ClickListener() {

					@Override
					public void onClick() {
						AudioPlayer.playAudio("audio/next.wav");
						characterID--;
						if (characterID < 1)
							characterID = 5;

						characterData();
					}

				}));

		uiManager.addObject(
				new ImageButton((int)(381*ControlCenter.scaleValue), (int)(((c.getHeight() - 205)*ControlCenter.scaleValue)), (int)(151*ControlCenter.scaleValue), (int)(48*ControlCenter.scaleValue), Assets.sRight, new ClickListener() {

					@Override
					public void onClick() {
						AudioPlayer.playAudio("audio/next.wav");
						characterID++;
						if (characterID > 5)
							characterID = 1;

						characterData();

					}

				}));

		uiManager.addObject(new ImageButton((int)((c.getWidth() - 543)*ControlCenter.scaleValue), (int)((c.getHeight() - 214)*ControlCenter.scaleValue), (int)(205*ControlCenter.scaleValue), (int)(65*ControlCenter.scaleValue), Assets.selectButton,
				new ClickListener() {

			@Override
			public void onClick() {

				AudioPlayer.playAudio("audio/select.wav");

				loading = true;

				dataLoader = new WorldDataWriter(gameMode, worldSize, charName, charHealth, charRunSpeed, charEndurability, charDamageScale, charIntimidation, charIntelligence, charResistance);
				worldLoader = new WorldWritter(worldSize);
				terrainLoader = new TerrainWriter(worldSize);
				loadWorld(String.format("worlds/%s", WorldCreationState.worldName));
				loadTerrain(String.format("terrain/%s", WorldCreationState.worldName));
				topperLoader = new TopperWriter(tiles, terrain, worldSize);
				
				c.getMenuState().getWorldSelectState().setSelectedWorldName(WorldCreationState.worldName); //make sure that this world is loaded
				c.getMenuState().getWorldSelectState().gameState = new GameState(c);

				c.getMouseManager().setUiManager(null);

				State.setState(c.getMenuState().getWorldSelectState().gameState);
				
				c.getMenuState().getWorldSelectState().readDataFile(WorldCreationState.worldName);
				c.getMenuState().getWorldSelectState().gameState.getWorldGenerator().createPlayer();

				loading = false;
			}

		}));
		
		//back button
		uiManager.addObject(
				new ImageButton((int)(55*ControlCenter.scaleValue), (int)(720*ControlCenter.scaleValue), (int)(48*ControlCenter.scaleValue), (int)(44*ControlCenter.scaleValue), Assets.sLeft, new ClickListener() {

					@Override
					public void onClick() {
						AudioPlayer.playAudio("audio/next.wav");
						
						State.setState(c.getMenuState().getWorldSelectState().getWorldCreationState());
						c.getMouseManager().setUIManager(c.getMenuState().getWorldSelectState().getWorldCreationState().getUiManager());
					}

				}));

	}

	// Reads the .txt file
	private void loadWorld(String path) {
		String file = WorldInput.loadFileAsString(path);
		String[] tokens = file.split("\\s+"); // split up every number into the string tokens array

		tiles = new int[worldSize][worldSize];

		// puts the tiles inside a 2D array
		for (int y = 0; y < worldSize; y++) { //height
			for (int x = 0; x < worldSize; x++) { //width
				tiles[x][y] = WorldInput.parseInt(tokens[(x + y * worldSize) + 4]);
			}
		}

	}

	private void loadTerrain(String path) {
		String file = WorldInput.loadFileAsString(path);
		String[] tokens = file.split("\\s+"); // split up every number into the string tokens array

		terrain = new int[worldSize][worldSize];

		for (int y = 0; y < worldSize; y++) { //height
			for (int x = 0; x < worldSize; x++) { //width
				terrain[x][y] = WorldInput.parseInt(tokens[(x + y * worldSize)]);
			}
		}

	}

	// method that sets the character attributes for the selected character
	public void characterData() {
		// change it if you like
		if (characterID == 1) {
			charName = "Rod";
			charHealth = 1000;
			charRunSpeed = 2.3;
			charEndurability = 500;
			charIntimidation = 20;
			charIntelligence = 6;
			charDamageScale = 40;
			charResistance = 12;
		} else if (characterID == 2) {
			charName = "Ray";
			charHealth = 750;
			charRunSpeed = 2.7;
			charEndurability = 400;
			charIntimidation = 10;
			charIntelligence = 5;
			charDamageScale = 65;
			charResistance = 5;
		} else if (characterID == 3) {
			charName = "Batash";
			charHealth = 1550;
			charRunSpeed = 1.3;
			charEndurability = 750;
			charIntimidation = 30;
			charIntelligence = 3;
			charDamageScale = 85;
			charResistance = 18;
		} else if (characterID == 4) {
			charName = "Bharat_Sinai_Peddi";
			charHealth = 850;
			charRunSpeed = 1.8;
			charEndurability = 550;
			charIntimidation = 45;
			charIntelligence = 10;
			charDamageScale = 30;
			charResistance = 20;
		} else if (characterID == 5) {
			charName = "Paris";
			charHealth = 600;
			charRunSpeed = 2;
			charEndurability = 300;
			charIntimidation = 10;
			charIntelligence = 7;
			charDamageScale = 20;
			charResistance = 10;
		}

	}

	@Override
	public void tick() {
		uiManager.tick();

	}

	@Override
	public void render(Graphics g) {

		//fills the background with black(default color)
		g.fillRect(0, 0, (int)(ControlCenter.scaleValue * c.getWidth()), (int)(c.getHeight()*ControlCenter.scaleValue));
		g.drawImage(background.getCurrentFrame(), 0, 0, c.getWidth(), c.getHeight(), null);
		g.drawImage(Assets.characterSelectionInterface, 100, 100, c.getWidth() - 200, c.getHeight() - 200, null);
		
		if(characterID == 1)
			g.drawImage(CT.flip(Assets.rodIdle), 185, 200, 340, 340, null);
		else if(characterID == 2)
			g.drawImage(CT.flip(Assets.rayIdle), 185, 200, 340, 340, null);
		else if(characterID == 3)
			g.drawImage(CT.flip(Assets.batashIdle), 185, 200, 340, 340, null);
		else if(characterID == 4)
			g.drawImage(CT.flip(Assets.sinaiIdle), 185, 200, 340, 340, null);
		else if(characterID == 5)
			g.drawImage(CT.flip(Assets.parisIdle), 185, 200, 340, 340, null);

		//writes the character info using the customized text writer
		CustomTextWritter.drawString(g, charName, (int)((c.getWidth() - 925)*ControlCenter.scaleValue), (int)(180*ControlCenter.scaleValue), true, Color.YELLOW, Assets.font36);
		CustomTextWritter.drawString(g, "Vital: ", (int)((c.getWidth() - 525)*ControlCenter.scaleValue), (int)(200*ControlCenter.scaleValue), true, Color.RED, Assets.font28);
		CustomTextWritter.drawString(g, "Strength: ", (int)((c.getWidth() - 555)*ControlCenter.scaleValue), (int)(250*ControlCenter.scaleValue), true, Color.RED, Assets.font28);
		CustomTextWritter.drawString(g, "Agility: ", (int)((c.getWidth() - 540)*ControlCenter.scaleValue), (int)(300*ControlCenter.scaleValue), true, Color.RED, Assets.font28);
		CustomTextWritter.drawString(g, "Intelligence: ", (int)((c.getWidth() - 580)*ControlCenter.scaleValue), (int)(350*ControlCenter.scaleValue), true, Color.RED, Assets.font28);
		CustomTextWritter.drawString(g, "Intimidation: ", (int)((c.getWidth() - 580)*ControlCenter.scaleValue), (int)(400*ControlCenter.scaleValue), true, Color.RED, Assets.font28);
		CustomTextWritter.drawString(g, "Resistance: ", (int)((c.getWidth() - 570)*ControlCenter.scaleValue), (int)(450*ControlCenter.scaleValue), true, Color.RED, Assets.font28);
		CustomTextWritter.drawString(g, "Endurability: ", (int)((c.getWidth() - 580)*ControlCenter.scaleValue), (int)(500*ControlCenter.scaleValue), true, Color.RED, Assets.font28);

		for(int i = 0; i < charHealth; i+=100) {
			g.drawImage(Assets.bar, (int)((c.getWidth() - 475 + (i/100)*15)*ControlCenter.scaleValue), (int)(180*ControlCenter.scaleValue), (int)(15*ControlCenter.scaleValue), (int)(40*ControlCenter.scaleValue), null);
		}

		for(int i = 0; i < charDamageScale; i+=2) {
			g.drawImage(Assets.bar, (int)((c.getWidth() - 475 + (i/6)*15)*ControlCenter.scaleValue), (int)(230*ControlCenter.scaleValue), (int)(15*ControlCenter.scaleValue), (int)(40*ControlCenter.scaleValue), null);
		}

		for(int i = 0; i < (charRunSpeed-1)*10; i+=1) {
			g.drawImage(Assets.bar, (int)((c.getWidth() - 475 + (i/1)*15)*ControlCenter.scaleValue), (int)(280*ControlCenter.scaleValue), (int)(15*ControlCenter.scaleValue), (int)(40*ControlCenter.scaleValue), null);
		}

		for(int i = 0; i < charIntelligence*2; i+=1) {
			g.drawImage(Assets.bar, (int)((c.getWidth() - 475 + (i/1)*15)*ControlCenter.scaleValue), (int)(330*ControlCenter.scaleValue), (int)(15*ControlCenter.scaleValue), (int)(40*ControlCenter.scaleValue), null);
		}

		for(int i = 0; i < charIntimidation*2; i+=5) {
			g.drawImage(Assets.bar, (int)((c.getWidth() - 475 + (i/5)*15)*ControlCenter.scaleValue), (int)(380*ControlCenter.scaleValue), (int)(15*ControlCenter.scaleValue), (int)(40*ControlCenter.scaleValue), null);
		}

		for(int i = 0; i < charResistance; i+=3) {
			g.drawImage(Assets.bar, (int)((c.getWidth() - 475 + (i/3)*15)*ControlCenter.scaleValue), (int)(430*ControlCenter.scaleValue), (int)(15*ControlCenter.scaleValue), (int)(40*ControlCenter.scaleValue), null);
		}

		for(int i = 0; i < charEndurability; i+=50) {
			g.drawImage(Assets.bar, (int)((c.getWidth() - 475 + (i/50)*15)*ControlCenter.scaleValue), (int)(480*ControlCenter.scaleValue), (int)(15*ControlCenter.scaleValue), (int)(40*ControlCenter.scaleValue), null);
		}

		//renders the UIObjects
		uiManager.render(g);

		if(loading) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, c.getWidth(), c.getHeight());
			g.drawImage(background.getCurrentFrame(), 0, 0, c.getWidth(), c.getHeight(), null);

			CustomTextWritter.drawString(g, "Generating World", c.getWidth() / 2, c.getHeight() / 2 - 70, true,
					Color.WHITE, Assets.font36);
		}

	}

	// getters and setters
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

	public ControlCenter getControlCenter() {
		return c;
	}

	public void setC(ControlCenter c) {
		this.c = c;
	}

	public UIManager getUiManager() {
		return uiManager;
	}

	public void setUiManager(UIManager uiManager) {
		this.uiManager = uiManager;
	}

}
