package graphics;

import java.awt.image.BufferedImage;

public class Animation {
	
	private int speed, index;
	private BufferedImage[] frames;
	private long lastTime, timer;
	private boolean repeat;
	
	public Animation(int speed, BufferedImage[] frames, boolean repeat) {
		this.speed = speed;
		this.frames = frames;
		this.repeat = repeat;
		index = 0;
		timer = 0;
		lastTime = System.currentTimeMillis();
	}
	
	public void tick() {
		timer += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		
		if(timer > speed) {
			index++;
			timer = 0;
			if(index >= frames.length && repeat)	//restart repetitive animations
				index = 0;
		}
	}
	
	public BufferedImage getCurrentFrame() {
		return frames[index];
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public BufferedImage[] getFrames() {
		return frames;
	}

	public void setFrames(BufferedImage[] frames) {
		this.frames = frames;
	}
	
}
