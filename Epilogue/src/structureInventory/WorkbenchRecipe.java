package structureInventory;

import inventory.Recipe;
import items.Item;

public class WorkbenchRecipe extends Recipe{
	
	private int id;
	private Item item;
	private boolean structure = false;
	private int wallType = 0; //1 - wood, 2 - stone, 3 - metal
	
	private int amtGet = 1;

	public WorkbenchRecipe(int id) {
		super(id);
		
		this.id = id;

	}
	
	public WorkbenchRecipe(int id, boolean learned) {
		super(id, learned);
		
		this.id = id;

	}

	// Getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getAmtGet() {
		return amtGet;
	}

	public void setAmtGet(int amtGet) {
		this.amtGet = amtGet;
	}
	
	public boolean isStructure() {
		return structure;
	}
	
	public void setStructure(boolean structure) {
		this.structure = structure;
	}

	public int getWallType() {
		return wallType;
	}

	public void setWallType(int wallType) {
		this.wallType = wallType;
	}

}