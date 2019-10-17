package items;

import java.awt.image.BufferedImage;

import graphics.CT;
import structureInventory.ChestInventory;

public class WaterContainer extends Item {
	
	protected static boolean stackable = false;
	protected static String type = "water container";
	protected static int damage = 0;
	protected boolean purified = false;
	
	protected int currentCapacity;
	protected int capacity;

	public WaterContainer(BufferedImage texture, String name, int id, double weight, int volume, int currentCapacity, int capacity) {
		
		super(texture, name, id, stackable, type, weight, damage, volume);
		
		this.currentCapacity = currentCapacity;
		this.capacity = capacity;

	}
	
	public Item createNew(int x, int y) {
		Item i = new WaterContainer(texture, name, id, weight, volume, currentCapacity, capacity);
		i.setPosition(x, y);
		return i;
	}

	public Item createNew(int count) { // testing
		Item i = new WaterContainer(texture, name, id, weight, volume, currentCapacity, capacity);
		i.setPickedUp(true);
		i.setCount(count);
		return i;
	}
	
	// random generation
	public void createNew(ChestInventory chest) { // testing
		Item e = new WaterContainer(texture, name, id, weight, volume, currentCapacity, capacity);
		
		WaterContainer i = (WaterContainer)e;
		
		i.setCurrentCapacity(CT.random(1, i.getCapacity()));
		i.setPickedUp(true);
		if(chest.getInventoryVolume() + i.getVolume() < chest.getInventoryCapacity())
			chest.addItem((Item)i);
	}
	
	public double getCapacityPercentage() {
		return ((double) currentCapacity/ (double) capacity)*100;
	}

	public Item createNew(WaterContainer item, int x, int y) {
		
		WaterContainer i = new WaterContainer(texture, name, id, weight, volume, currentCapacity, capacity);
		
		i.currentCapacity = item.currentCapacity;
		
		i.setPosition(x, y);
		return (Item)i;
		
	}

	public int getCurrentCapacity() {
		return currentCapacity;
	}

	public void setCurrentCapacity(int currentCapacity) {
		this.currentCapacity = currentCapacity;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public boolean isPurified() {
		return purified;
	}

	public void setPurified(boolean purified) {
		this.purified = purified;
	}

	@Override
	public String toString() {
		return "WaterContainer [purified=" + purified + ", currentCapacity=" + currentCapacity + ", capacity="
				+ capacity + ", pickedUp=" + pickedUp + ", c=" + c + ", inventory=" + inventory + ", texture=" + texture
				+ ", weight=" + weight + ", name=" + name + ", type=" + type + ", id=" + id + ", x=" + x + ", y=" + y
				+ ", count=" + count + ", bounds=" + bounds + ", stackable=" + stackable + ", damage=" + damage
				+ ", volume=" + volume + ", timeDropped=" + timeDropped + ", isEquipped=" + isEquipped + "]";
	}

}
