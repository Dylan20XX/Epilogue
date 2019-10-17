package utils;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

/*
 * parent class of all UIObjects
 */
public abstract class UIObject {
	
	//properties of all UIObjects
	protected double x, y;
	protected int width, height;
	protected Rectangle bounds;
	protected boolean hovering = false;

	public UIObject(double x, double y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		//set a rectangle bound for the mouse to click
		bounds = new Rectangle((int)x, (int)y, width, height);
	}
	
	//class detects the hovering effect
	public void onMouseMove(MouseEvent e) {
		if(bounds.contains(e.getX(), e.getY())) {
			hovering = true;
		} else {
			hovering = false;
		}
	}
	
	//class that enables the onClick method function when creating an UIObject
	public void onMouseRelease(MouseEvent e) {
		if(hovering)
			onClick();
	}

	//abstract methods
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	public abstract void onClick();
	
	//getters and setters
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isHovering() {
		return hovering;
	}

	public void setHovering(boolean hovering) {
		this.hovering = hovering;
	}
	
}
