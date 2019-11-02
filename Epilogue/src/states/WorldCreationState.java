package states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import alphaPackage.ControlCenter;
import audio.MusicPlayer;
import graphics.Animation;
import graphics.Assets;
import graphics.CustomTextWritter;
import staticEntity.BurntTree;
import staticEntity.RockObstacle;
import tiles.Tile;
import utils.ClickListener;
import utils.ImageButton;
import utils.UIManager;
import world.TerrainWriter;
import world.TopperWriter;
import world.WorldDataWriter;
import world.WorldInput;
import world.WorldWritter;

public class WorldCreationState extends State {

	// inherent fields
	private UIManager uiManager;
	private ControlCenter c;

	// next state
	public CharacterSelectionState characterState;

	public final int MAX_CHARACTERS = 22; //The greatest number of characters a world name can have

	//number of worlds
	private static int numWorlds = 0;
	private ArrayList<String> worldNames = new ArrayList<String>();

	//world properties
	public static String worldName = "";
	private int gameMode; //0 = normal / 1 = hardcore
	private int worldSize; //length of the world | 2500 = small / 5000 = medium / 100000 = large


	private boolean gameModeSelected = false;
	private boolean worldSizeSelected = false;
	private boolean typing = true;

	private Animation background;

	// constructor takes in the ControlCenter class for access
	public WorldCreationState(ControlCenter c) {

		this.c = c;

		//setup the world select buttons
		File Files = new File("worlds");
		setNumWorlds(Files.list().length);

		File[] listOfFiles = Files.listFiles();

		//Add all world names to the arraylist
		for(int i = 0; i < listOfFiles.length; i++) 
			worldNames.add(listOfFiles[i].getName());

		MusicPlayer.StopMusic();

		//MusicPlayer.playMusic("audio/introAudio.wav");

		// initializes the uiManager for UI accesses
		uiManager = new UIManager();
		c.getMouseManager().setUIManager(uiManager);

		reloadButtons();

		background = new Animation(50, Assets.menuBackground, true);

	}	

	//Adds all buttons
	private void reloadButtons() {
		uiManager.getObjects().clear();

		addOptionButtons();
	}



	//Adds world name, NORMAL / HARDCORE , create world, and back buttons
	private void addOptionButtons() {

		//Allows player to enter world name
		/*
		uiManager.addObject( 
				new ImageButton(300, 185, (int)(48*ControlCenter.scaleValue), (int)(44*ControlCenter.scaleValue), Assets.sRight, new ClickListener() {

					@Override
					public void onClick() {

						typing = true;

					}

				}));
		*/

		//normal mode button
		uiManager.addObject(
				new ImageButton(c.getWidth() / 2 - 138 - 110, 303, (int)(205*ControlCenter.scaleValue), (int)(65*ControlCenter.scaleValue), Assets.normal, new ClickListener() {

					@Override
					public void onClick() {

						gameMode = 0;
						gameModeSelected = true;
						//typing = false;
					}

				}));

		//hardcore mode button
		uiManager.addObject(
				new ImageButton(c.getWidth() / 2 + 110 - 110, 303, (int)(205*ControlCenter.scaleValue), (int)(65*ControlCenter.scaleValue), Assets.hardcore, new ClickListener() {

					@Override
					public void onClick() {

						gameMode = 1;
						gameModeSelected = true;
						//typing = false;
					}

				}));

		//small world button
		uiManager.addObject(
				new ImageButton(c.getWidth() / 2 - 257 - 110, 441, (int)(205*ControlCenter.scaleValue), (int)(65*ControlCenter.scaleValue), Assets.small, new ClickListener() {

					@Override
					public void onClick() {

						worldSize = 1500;
						worldSizeSelected = true;
						//typing = false;
					}

				}));

		//medium world button
		uiManager.addObject(
				new ImageButton(c.getWidth() / 2 - 9 - 110, 441, (int)(205*ControlCenter.scaleValue), (int)(65*ControlCenter.scaleValue), Assets.medium, new ClickListener() {

					@Override
					public void onClick() {

						worldSize = 2500;
						worldSizeSelected = true;
						//typing = false;
					}

				}));

		//large world button
		uiManager.addObject(
				new ImageButton(c.getWidth() / 2 + 240 - 110, 441, (int)(205*ControlCenter.scaleValue), (int)(65*ControlCenter.scaleValue), Assets.large, new ClickListener() {

					@Override
					public void onClick() {

						worldSize = 3500;
						worldSizeSelected = true;
						//typing = false;
					}

				}));

		//back button to return to world selection state
		uiManager.addObject(
				new ImageButton(55, 720, (int)(48*ControlCenter.scaleValue), (int)(44*ControlCenter.scaleValue), Assets.sLeft, new ClickListener() {

					@Override
					public void onClick() {

						State.setState(c.getMenuState().getWorldSelectState());
						c.getMouseManager().setUIManager(c.getMenuState().getWorldSelectState().getUiManager());
						
						worldName = "";
						gameModeSelected = false;
						worldSizeSelected = false;
						//typing = false;
					}

				}));

		//Create world button
		uiManager.addObject(
				new ImageButton(c.getWidth() / 2 - 9 - 110, 578, (int)(205*ControlCenter.scaleValue), (int)(65*ControlCenter.scaleValue), Assets.continueButton, new ClickListener() {

					@Override
					public void onClick() {

						if(gameModeSelected && worldSizeSelected && checkWorldName()) {
							nextState();

							gameModeSelected = false;
							worldSizeSelected = false;
							//typing = false;
						}

					}

				}));

	}

	public void nextState() {

		characterState = new CharacterSelectionState(c, gameMode, worldSize);
		State.setState(characterState);

	}

	//This method allows the player to input the world name
	private void type() {

		if(worldName.length() < MAX_CHARACTERS)
			if (c.getKeyManager().keyJustPressed(KeyEvent.VK_A)) {
				worldName = worldName + "a";
			} else if (c.getKeyManager().keyJustPressed(KeyEvent.VK_B)) {
				worldName = worldName + "b";
			} else if (c.getKeyManager().keyJustPressed(KeyEvent.VK_C)) {
				worldName = worldName + "c";
			} else if (c.getKeyManager().keyJustPressed(KeyEvent.VK_D)) {
				worldName = worldName + "d";
			} else if (c.getKeyManager().keyJustPressed(KeyEvent.VK_E)) {
				worldName = worldName + "e";
			} else if (c.getKeyManager().keyJustPressed(KeyEvent.VK_F)) {
				worldName = worldName + "f";
			} else if (c.getKeyManager().keyJustPressed(KeyEvent.VK_G)) {
				worldName = worldName + "g";
			} else if (c.getKeyManager().keyJustPressed(KeyEvent.VK_H)) {
				worldName = worldName + "h";
			} else if (c.getKeyManager().keyJustPressed(KeyEvent.VK_I)) {
				worldName = worldName + "i";
			} else if (c.getKeyManager().keyJustPressed(KeyEvent.VK_J)) {
				worldName = worldName + "j";
			} else if (c.getKeyManager().keyJustPressed(KeyEvent.VK_K)) {
				worldName = worldName + "k";
			} else if (c.getKeyManager().keyJustPressed(KeyEvent.VK_L)) {
				worldName = worldName + "l";
			} else if (c.getKeyManager().keyJustPressed(KeyEvent.VK_M)) {
				worldName = worldName + "m";
			} else if (c.getKeyManager().keyJustPressed(KeyEvent.VK_N)) {
				worldName = worldName + "n";
			} else if (c.getKeyManager().keyJustPressed(KeyEvent.VK_O)) {
				worldName = worldName + "o";
			} else if (c.getKeyManager().keyJustPressed(KeyEvent.VK_P)) {
				worldName = worldName + "p";
			} else if (c.getKeyManager().keyJustPressed(KeyEvent.VK_Q)) {
				worldName = worldName + "q";
			} else if (c.getKeyManager().keyJustPressed(KeyEvent.VK_R)) {
				worldName = worldName + "r";
			} else if (c.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
				worldName = worldName + "s";
			} else if (c.getKeyManager().keyJustPressed(KeyEvent.VK_T)) {
				worldName = worldName + "t";
			} else if (c.getKeyManager().keyJustPressed(KeyEvent.VK_U)) {
				worldName = worldName + "u";
			} else if (c.getKeyManager().keyJustPressed(KeyEvent.VK_V)) {
				worldName = worldName + "v";
			} else if (c.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
				worldName = worldName + "w";
			} else if (c.getKeyManager().keyJustPressed(KeyEvent.VK_X)) {
				worldName = worldName + "x";
			} else if (c.getKeyManager().keyJustPressed(KeyEvent.VK_Y)) {
				worldName = worldName + "y";
			} else if (c.getKeyManager().keyJustPressed(KeyEvent.VK_Z)) {
				worldName = worldName + "z";
			} else if (c.getKeyManager().keyJustPressed(KeyEvent.VK_SPACE) && worldName.length() > 0) {
				worldName = worldName + " ";
			} else if (c.getKeyManager().keyJustPressed(KeyEvent.VK_1)) {
				worldName = worldName + "1";
			} else if (c.getKeyManager().keyJustPressed(KeyEvent.VK_2)) {
				worldName = worldName + "2";
			} else if (c.getKeyManager().keyJustPressed(KeyEvent.VK_3)) {
				worldName = worldName + "3";
			} else if (c.getKeyManager().keyJustPressed(KeyEvent.VK_4)) {
				worldName = worldName + "4";
			} else if (c.getKeyManager().keyJustPressed(KeyEvent.VK_5)) {
				worldName = worldName + "5";
			} else if (c.getKeyManager().keyJustPressed(KeyEvent.VK_6)) {
				worldName = worldName + "6";
			} else if (c.getKeyManager().keyJustPressed(KeyEvent.VK_7)) {
				worldName = worldName + "7";
			} else if (c.getKeyManager().keyJustPressed(KeyEvent.VK_8)) {
				worldName = worldName + "8";
			} else if (c.getKeyManager().keyJustPressed(KeyEvent.VK_9)) {
				worldName = worldName + "9";
			} else if (c.getKeyManager().keyJustPressed(KeyEvent.VK_0)) {
				worldName = worldName + "0";
			}

		if (c.getKeyManager().keyJustPressed(KeyEvent.VK_BACK_SPACE)) {
			worldName = removeLastChar(worldName);
		}

	}

	private String removeLastChar(String str) {
		if (str == null || str.length() == 0) {
			return str;
		}

		return str.substring(0, str.length() - 1);
	}

	private boolean checkWorldName() { //check that the world name isn't already taken

		for(int i = 0; i < worldNames.size(); i++) 
			if(worldName.charAt(worldName.length() - 1) == ' ') //if the last character is a space, remove it
				worldName = removeLastChar(worldName);

		if(worldName.length() == 0)
			return false;

		for(int i = 0; i < worldNames.size(); i++) 
			if(worldName.equals(worldNames.get(i)))
				return false;

		return true;

	}

	@Override
	public void tick() {
		uiManager.tick();
		background.tick();

		if(typing)
			type(); //allow player to type world name

	}

	@Override
	public void render(Graphics g) {

		//fills the background with black(default color)
		g.fillRect(0, 0, c.getWidth(), c.getHeight());
		g.drawImage(background.getCurrentFrame(), 0, 0, c.getWidth(), c.getHeight(), null);
		
		g.drawImage(Assets.worldCreationInterface, 100, 100, c.getWidth() - 200, c.getHeight() - 200, null);

		CustomTextWritter.drawString(g, "Enter World Name", c.getWidth() / 2, 150, true,
				Color.WHITE, Assets.font36);

		g.setColor(Color.BLACK);
		g.fillRect(294, 181, 648, 48);

		if(typing && worldName.length() < MAX_CHARACTERS)
			CustomTextWritter.drawString(g, worldName + "_", 330, 215, false,
					Color.WHITE, Assets.font36);
		else
			CustomTextWritter.drawString(g, worldName, 330, 215, false,
					Color.WHITE, Assets.font36);

		CustomTextWritter.drawString(g, "Select Game Mode", c.getWidth() / 2, 265, true,
				Color.WHITE, Assets.font36);

		CustomTextWritter.drawString(g, "Select World Size", c.getWidth() / 2, 405, true,
				Color.WHITE, Assets.font36);
		
		if(gameModeSelected) {
			g.setColor(Color.GREEN);
			if(gameMode == 0) {
				g.fillRect(c.getWidth() / 2 - 138 - 110, 303, (int)(205*ControlCenter.scaleValue), (int)(65*ControlCenter.scaleValue));
			}else if(gameMode == 1) {
				g.fillRect(c.getWidth() / 2 + 110 - 110, 303, (int)(205*ControlCenter.scaleValue), (int)(65*ControlCenter.scaleValue));
			}
		}
		
		if(worldSizeSelected) {
			g.setColor(Color.GREEN);
			if(worldSize == 1500) {
				g.fillRect(c.getWidth() / 2 - 257 - 110, 441, (int)(205*ControlCenter.scaleValue), (int)(65*ControlCenter.scaleValue));
			}else if(worldSize == 2500) {
				g.fillRect(c.getWidth() / 2 - 9 - 110, 441, (int)(205*ControlCenter.scaleValue), (int)(65*ControlCenter.scaleValue));
			}else if(worldSize == 3500) {
				g.fillRect(c.getWidth() / 2 + 240 - 110, 441, (int)(205*ControlCenter.scaleValue), (int)(65*ControlCenter.scaleValue));
			}
		}

		//renders the UIObjects
		uiManager.render(g);

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

	public static int getNumWorlds() {
		return numWorlds;
	}

	public static void setNumWorlds(int numWorlds) {
		WorldCreationState.numWorlds = numWorlds;
	}

	public int getGameMode() {
		return gameMode;
	}

	public void setGameMode(int gameMode) {
		this.gameMode = gameMode;
	}

	public int getWorldSize() {
		return worldSize;
	}

	public void setWorldSize(int worldSize) {
		this.worldSize = worldSize;
	}

	public UIManager getUiManager() {
		return uiManager;
	}

}