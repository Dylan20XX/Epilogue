package structureInventory;

import inventory.Recipe;
import items.Item;

public class SmithingTableRecipe extends Recipe{
	
	private int id;
	private Item item;
	
	private int amtGet = 1;

	public SmithingTableRecipe(int id) {
		super(id);
		
		this.id = id;

	}
	
	public SmithingTableRecipe(int id, boolean learned) {
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

}