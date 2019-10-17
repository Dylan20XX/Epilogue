package states;

import java.awt.Graphics;

/*
 * this class is the parent class for all states in the game, including menu state, game state, settings state, and etc.
 */
public abstract class State {
	
	//default state is null
	private static State currentState = null;
	
	public static void setState(State state) {
		currentState = state;
	}
	
	public static State getState() {
		return currentState;
	}
	
	//abstract methods
	public abstract void tick();
	
	public abstract void render(Graphics g);

}
