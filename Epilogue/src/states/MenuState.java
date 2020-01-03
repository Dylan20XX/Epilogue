package states;

import java.awt.Graphics;

import javax.swing.JOptionPane;

import alphaPackage.ControlCenter;
import audio.AudioPlayer;
import audio.BackgroundPlayer;
import audio.MusicPlayer;
import graphics.Animation;
import graphics.Assets;
import utils.ClickListener;
import utils.ImageButton;
import utils.UIManager;

/*
 * character selection state
 * 
 * subclass of State
 */
public class MenuState extends State {

	// inherent fields
	private UIManager uiManager;
	private ControlCenter c;

	//next states
	public WorldSelectionState worldSelectState;
	public OptionState optionState;
	public ControlState controlState;
	public CreditState creditState;
	
	private Animation background;
	
	// constructor takes in the ControlCenter class for access
	public MenuState(ControlCenter c) {
		this.c = c;
		// using the music player to play the background music
		MusicPlayer.playMusic("audio/menu.wav");

		// initializes the uiManager for UI accesses
		setUiManager(new UIManager());
		c.getMouseManager().setUIManager(getUiManager());

		// adding the buttons
		// world select button
		getUiManager().addObject(
				new ImageButton(50, 550, 236/2, 44/2, Assets.begin, new ClickListener() {

					@Override
					public void onClick() {

						AudioPlayer.playAudio("audio/next.wav");
						
						worldSelectState = new WorldSelectionState(c);
						
						State.setState(worldSelectState);
						
					}

				}));

		/*
		// options button
		getUiManager().addObject(
				new ImageButton(50, 550, 332/2, 44/2, Assets.options, new ClickListener() {

					@Override
					public void onClick() {

						AudioPlayer.playAudio("audio/next.wav");
						
						optionState = new OptionState(c);
						
						State.setState(optionState);
						
					}

				}));
		*/
		// controls button
		getUiManager().addObject(
				new ImageButton(50, 600, 188/2, 44/2, Assets.help, new ClickListener() {

					@Override
					public void onClick() {
						
						AudioPlayer.playAudio("audio/next.wav");
						
						openWebPage("https://www.project-epilogue.com/controls/");

					}

				}));
		
		// credits button
		getUiManager().addObject(
				new ImageButton(50, 650, 332/2, 44/2, Assets.credit, new ClickListener() {

					@Override
					public void onClick() {
						
						AudioPlayer.playAudio("audio/next.wav");
						
						openWebPage("https://www.project-epilogue.com/credits/");
						
					}

				}));
		
		// exit button
		getUiManager().addObject(
				new ImageButton(50, 700, 178/2, 44/2, Assets.exit, new ClickListener() {

					@Override
					public void onClick() {

						AudioPlayer.playAudio("audio/next.wav");
						
						System.exit(1);
						
					}

				}));
		
		background = new Animation(50, Assets.menuBackground, true);

	}

	// method that sets the character attributes for the selected character
	
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
		g.drawImage(Assets.epilogue, c.getWidth()/2 - 314, 90 - 88, 314*2, 88*2, null);
		
		//renders the UIObjects
		getUiManager().render(g);

	}
	
	// method opens the URL from the parameter
	public void openWebPage(String url) {
	
		try {
			// using the java.awt library to open the URL on a browser
			java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
	
		} catch (java.io.IOException e) {
	
			// display message dialogue for invalid input
			JOptionPane.showMessageDialog(null,
					"Invalid URL\n\n"
							+ "click 'ok' to continue...",
					"INVALID INPUT", JOptionPane.WARNING_MESSAGE);
			
		}
	
	}

	// getters and setters
	public ControlCenter getControlCenter() {
		return c;
	}

	public void setC(ControlCenter c) {
		this.c = c;
	}
	
	public WorldSelectionState getWorldSelectState() {
		return worldSelectState;
	}

	public void setWorldSelectState(WorldSelectionState worldSelectState) {
		this.worldSelectState = worldSelectState;
	}

	public OptionState getOptionState() {
		return optionState;
	}

	public void setOptionState(OptionState optionState) {
		this.optionState = optionState;
	}

	public ControlState getControlState() {
		return controlState;
	}

	public void setControlState(ControlState controlState) {
		this.controlState = controlState;
	}

	public CreditState getCreditState() {
		return creditState;
	}

	public void setCreditState(CreditState creditState) {
		this.creditState = creditState;
	}

	public UIManager getUiManager() {
		return uiManager;
	}

	public void setUiManager(UIManager uiManager) {
		this.uiManager = uiManager;
	}
	

}