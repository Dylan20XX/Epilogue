package utils;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/*
 * class that manages the UIObjects
 * 
 * contains a list that stores all the UIObjects
 */
public class UIManager {

	//ArrayList that stores all the UIObjects
	private ArrayList<UIObject> objects;
	
	public UIManager() {
		objects = new ArrayList<UIObject>();
	}
	
	public void tick() {
		for(int i = 0; i < objects.size(); i++) 
			objects.get(i).tick();
	}
	
	public void render(Graphics g) {
		for(int i = 0; i < objects.size(); i++) 
			objects.get(i).render(g);
	}
	
	//detection methods to see if mouse is hovering over an object
	public void onMouseMove(MouseEvent e) {
		for(int i = 0; i < objects.size(); i++) 
			objects.get(i).onMouseMove(e);
			
	}
	
	public void onMouseRelease(MouseEvent e) {
		for(int i = 0; i < objects.size(); i++) 
			objects.get(i).onMouseRelease(e);
		
		
	}
	
	//add and remove method for the UIObjects
	public void addObject(UIObject o ) {
		objects.add(o);
	}
	
	public void removeObject(UIObject o ) {
		objects.remove(o);
	}
	
	//getter and setters
	public ArrayList<UIObject> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<UIObject> objects) {
		this.objects = objects;
	}
	
}
