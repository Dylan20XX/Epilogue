package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
 * class for key control
 * 
 * class that consists of all the keys
 */
public class KeyManager implements KeyListener {

	// key properties
	private boolean[] keys, justPressed, cantPress, isPressed;

	// keys
	public boolean up, down, left, right;
	public boolean aup, adown, aleft, aright;
	public boolean run;
	public boolean shift;

	// constructor creates an array of keys with 256 keys, enough for all keys on
	// the keyboard
	// creates an array for keys being pressed
	public KeyManager() {
		keys = new boolean[256];

		justPressed = new boolean[keys.length];
		cantPress = new boolean[keys.length];

		isPressed = new boolean[keys.length];

	}

	public void tick() {

		for (int i = 0; i < keys.length; i++) {
			if (cantPress[i] && !keys[i]) {
				cantPress[i] = false;
			} else if (justPressed[i]) {
				cantPress[i] = true;
				justPressed[i] = false;
			}
			if (!cantPress[i] && keys[i]) {
				justPressed[i] = true;
			}
			isPressed[i] = true;
		}

		// setting a variable to a key
		// if the key is pressed, it turns true, else it is false
		up = keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_D];
		aup = keys[KeyEvent.VK_UP];
		adown = keys[KeyEvent.VK_DOWN];
		aleft = keys[KeyEvent.VK_LEFT];
		aright = keys[KeyEvent.VK_RIGHT];
		run = keys[KeyEvent.VK_F];
		shift = keys[KeyEvent.VK_SHIFT];

	}

	public boolean keyJustPressed(int keyCode) {
		if (keyCode < 0 || keyCode >= keys.length) {
			return false;
		}
		return justPressed[keyCode];
	}

	public boolean keyIsPressed(int keyCode) {
		if (keyCode < 0 || keyCode >= keys.length) {
			return false;
		}

		return isPressed[keyCode];
	}

	public void keyTyped(KeyEvent e) {

	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() < 0 || e.getKeyCode() >= keys.length) {
			return;
		}
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() < 0 || e.getKeyCode() >= keys.length) {
			return;
		}
		keys[e.getKeyCode()] = false;

	}

}
