package states;

import java.awt.Graphics;

import alphaPackage.ControlCenter;
import alphaPackage.Display;
import graphics.Animation;
import graphics.Assets;
import utils.UIManager;

/*
 * subclass of State
 */
public class LaunchState extends State {

	// inherent fields
	private UIManager uiManager;
	private ControlCenter c;

	//next states
	public MenuState menuState;
	
	private Animation cyclone = new Animation(50, Assets.cyclone, false);
	
	// constructor takes in the ControlCenter class for access
	public LaunchState(ControlCenter c) {
		this.c = c;
		
		Display.customCursor("/UI/cursor1.png");
		// initializes the uiManager for UI accesses
		uiManager = new UIManager();
		c.getMouseManager().setUIManager(uiManager);


	}

	// method that sets the character attributes for the selected character
	

	@Override
	public void tick() {
		uiManager.tick();
		cyclone.tick();
		
		if(cyclone.getCurrentFrame().equals(Assets.cyclone[69])) {
			c.setMenuState(new MenuState(c));
			menuState = c.getMenuState();
			State.setState(menuState);
		}
		
	}

	@Override
	public void render(Graphics g) {
		g.fillRect(0, 0, 1280, 800);
		g.drawImage(cyclone.getCurrentFrame(), (1280-600/2*3)/2, (800-338/2*3)/2, 600/2*3, 338/2*3, null);
	
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
	
	public MenuState getMenuState() {
		return menuState;
	}

	public void setMenuState(MenuState menuState) {
		this.menuState = menuState;
	}
	

}