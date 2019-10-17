package states;

import java.awt.Color;
import java.awt.Graphics;

import alphaPackage.ControlCenter;
import audio.MusicPlayer;
import graphics.Assets;
import graphics.CustomTextWritter;
import utils.ClickListener;
import utils.ImageButton;
import utils.UIManager;

/*
 * character selection state
 * 
 * subclass of State
 */
public class CreditState extends State {

	// inherent fields
	private UIManager uiManager;
	private ControlCenter controlCenter;

	//next states
	public MenuState menuState;
	
	// constructor takes in the ControlCenter class for access
	public CreditState(ControlCenter c) {
		this.controlCenter = c;
		
		
		// using the music player to play the background music
		MusicPlayer.playMusic("audio/introAudio.wav");

		// initializes the uiManager for UI accesses
		uiManager = new UIManager();
		controlCenter.getMouseManager().setUIManager(uiManager);

		// adding the buttons
		uiManager.addObject(
				new ImageButton((int)(200*ControlCenter.scaleValue), (int)((c.getHeight() - 130)*ControlCenter.scaleValue), (int)(48*ControlCenter.scaleValue), (int)(44*ControlCenter.scaleValue), Assets.sLeft, new ClickListener() {

					@Override
					public void onClick() {

						menuState = new MenuState(c);
						
						State.setState(menuState);
						
					}

				}));

	}

	// method that sets the character attributes for the selected character
	

	@Override
	public void tick() {
		uiManager.tick();

	}

	@Override
	public void render(Graphics g) {
		
		//fills the background with black(default color)
		g.fillRect(0, 0, controlCenter.getWidth(), controlCenter.getHeight());
		
	
		//renders the UIObjects
		uiManager.render(g);

	}

	// getters and setters
	public ControlCenter getControlCenter() {
		return controlCenter;
	}

	public void setC(ControlCenter c) {
		this.controlCenter = c;
	}
	
	public MenuState getMenuState() {
		return menuState;
	}

	public void setMenuState(MenuState menuState) {
		this.menuState = menuState;
	}
	

}