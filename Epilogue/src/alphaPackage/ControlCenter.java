package alphaPackage;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import graphics.Assets;
import graphics.GameCamera;
import inputs.KeyManager;
import inputs.MouseManager;
import states.GameState;
import states.MenuState;
import states.State;

/*
 * this class serves as the brain of the program
 * 
 * starts the thread
 * initializes the window
 * initializes all other parent classes
 */
public class ControlCenter implements Runnable {

	// Frame
	private Display display;
	public int width, height;
	public String title;
	private double scaledWidth;
	private double scaledHeight;
	public static double scaleValue;
	private Toolkit tk = Toolkit.getDefaultToolkit();

	// Threads
	private Thread thread;
	private boolean running = false;

	// Graphics
	private BufferStrategy bs;
	private Graphics g;
	private GameCamera gameCamera;

	// Inputs
	private MouseManager mouseManager;
	private KeyManager keyManager;

	// States
	public MenuState menuState;

	// constructor of the ControlCenter class initializes the properties of the
	public ControlCenter(String title, int width, int height) {

		// creating scaling variables
		scaledHeight = tk.getScreenSize().getHeight() / 800;
		scaledWidth = tk.getScreenSize().getWidth() / 1280;
		//scaleValue = Math.min(scaledHeight, scaledWidth);
		scaleValue = 1;
		
		this.width = width;
		this.height = height;
		this.title = title;

		mouseManager = new MouseManager();
		keyManager = new KeyManager();

		init();
	}

	private void init() {

		// creates the window by calling the display class
		display = new Display(title, (int) (width * scaleValue), (int) (height * scaleValue));

		display.getFrame().addKeyListener(keyManager);
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);

		gameCamera = new GameCamera(0, 0, this);

		// initializes the game assets, such as buffered images
		Assets.init();

		// set the state of the game, in the future, a menu state will be implemented
		menuState = new MenuState(this);
		State.setState(menuState);

	}

	// will be automatically called by the thread, runs constantly
	// method that allows a function of happen constantly with the thread
	private void tick() {

		if (State.getState() != null) {
			State.getState().tick();
		}
		keyManager.tick();
		gameCamera.tick();
	}

	// will be automatically called by the thread, runs constantly
	// this method draws images onto the canvas or frame
	private void render() {
		bs = display.getCanvas().getBufferStrategy(); // set to the canvas buffer strategy
		if (bs == null) {
			display.getCanvas().createBufferStrategy(3); // creating 3 buffers (3 imaginary screens to pre-load images)
			return;
		}
		g = bs.getDrawGraphics();

		g.clearRect(0, 0, (int) (width * scaleValue), (int) (height * scaleValue)); // Clear Screen before drawing
		// ----------------DRAW HERE------------------

		if (State.getState() != null) {

			// renders what is in the current state
			State.getState().render(g);

		}

		// -------------------------------------------
		bs.show();
		g.dispose();

	}

	// automatically called by implementing runnable
	public void run() {

		int fps = 60; // we want 60
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime(); // clock
		long timer = 0;
		int ticks = 0;

		while (running) {
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;

			if (delta >= 1) {
				// methods to run with the thread
				tick();
				render();
				//
				ticks++;
				delta--;
			}

			if (timer >= 1000000000) {
				System.out.println("Ticks and Frames: " + ticks);
				ticks = 0;
				timer = 0;
			}
		}

		// calls stop() if running is false, stops the program
		Stop();
	}

	// called when game starts
	public synchronized void Start() {
		if (running) // if already running, return (safety)
			return;

		running = true;
		thread = new Thread(this);
		thread.start(); // calls the run
	}

	// called when game stops
	public synchronized void Stop() {
		if (!running) // if already stopped, return (safety)
			return;

		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// getters for height and width, should not be changed in the game, that is why
	// there are no setters
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public MouseManager getMouseManager() {
		return mouseManager;
	}

	public void setMouseManager(MouseManager mouseManager) {
		this.mouseManager = mouseManager;
	}

	public KeyManager getKeyManager() {
		return keyManager;
	}

	public void setKeyManager(KeyManager keyManager) {
		this.keyManager = keyManager;
	}

	public GameCamera getGameCamera() {
		return gameCamera;
	}

	public void setGameCamera(GameCamera gameCamera) {
		this.gameCamera = gameCamera;
	}

	public MenuState getMenuState() {
		return menuState;
	}

	public void setMenuState(MenuState menuState) {
		this.menuState = menuState;
	}

	public double getScaleValue() {
		return scaleValue;
	}

	public void setScaleValue(double scaleValue) {
		this.scaleValue = scaleValue;
	}
	
	public GameState getGameState() {
		return this.getMenuState().getWorldSelectState().getGameState();
	}

}
