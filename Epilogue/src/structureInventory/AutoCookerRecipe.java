package structureInventory;

import java.util.ArrayList;
import java.util.HashMap;

import items.Food;
import items.Item;

public class AutoCookerRecipe {
	
	public HashMap<Integer, Integer> recipeRequirements = new HashMap<Integer, Integer>(); // First int is item id and
																							// second is item amount
																							// required
	private int id;
	private Item item;
	
	public static ArrayList<AutoCookerRecipe> recipes = new ArrayList<AutoCookerRecipe>(); //Stores all recipes

	public static AutoCookerRecipe cookedChickenItem = new AutoCookerRecipe(0);
	public static AutoCookerRecipe cookedChicken2Item = new AutoCookerRecipe(1);
	public static AutoCookerRecipe cookedChicken3Item = new AutoCookerRecipe(2);
	public static AutoCookerRecipe cookedChicken4tem = new AutoCookerRecipe(3);
	public static AutoCookerRecipe cookedChicken5Item = new AutoCookerRecipe(1);
	public static AutoCookerRecipe cookedChicken6Item = new AutoCookerRecipe(5);
	public static AutoCookerRecipe cookedMorselItem = new AutoCookerRecipe(6);
	public static AutoCookerRecipe cookedMorsel2Item = new AutoCookerRecipe(7);
	public static AutoCookerRecipe cookedMorsel3Item = new AutoCookerRecipe(8);
	public static AutoCookerRecipe cookedMorsel4tem = new AutoCookerRecipe(9);
	public static AutoCookerRecipe cookedMorsel5Item = new AutoCookerRecipe(10);
	public static AutoCookerRecipe cookedMorsel6Item = new AutoCookerRecipe(11);
	public static AutoCookerRecipe cookedMeatItem = new AutoCookerRecipe(12);
	public static AutoCookerRecipe cookedMeat2Item = new AutoCookerRecipe(13);
	public static AutoCookerRecipe cookedMeat3Item = new AutoCookerRecipe(14);
	public static AutoCookerRecipe cookedMeat4Item = new AutoCookerRecipe(15);
	public static AutoCookerRecipe cookedMeat5Item = new AutoCookerRecipe(16);
	public static AutoCookerRecipe cookedMeat6Item = new AutoCookerRecipe(17);
	public static AutoCookerRecipe cookedBugItem = new AutoCookerRecipe(18);
	public static AutoCookerRecipe cookedBug2Item = new AutoCookerRecipe(19);
	public static AutoCookerRecipe cookedBug3Item = new AutoCookerRecipe(20);
	
	private int amtGet = 1;

	public AutoCookerRecipe(int id) {
		this.id = id;

		setupRecipeRequirements();
		setupItem();

		recipes.add(this);
	}

	private void setupRecipeRequirements() {

		if (id == 0) {
			recipeRequirements.put(Food.rawChickenItem.getId(), 1);
			recipeRequirements.put(Item.coalChunkItem.getId(), 1);
		} else if (id == 1) {
			recipeRequirements.put(Food.rawChickenItem.getId(), 1);
			recipeRequirements.put(Item.woodItem.getId(), 1);
		} else if (id == 2) {
			recipeRequirements.put(Food.rawChickenItem.getId(), 1);
			recipeRequirements.put(Item.woodenPlankItem.getId(), 3);
		} else if (id == 3) {
			recipeRequirements.put(Food.suspicousChickenItem.getId(), 1);
			recipeRequirements.put(Item.coalChunkItem.getId(), 1);
		} else if (id == 4) {
			recipeRequirements.put(Food.suspicousChickenItem.getId(), 1);
			recipeRequirements.put(Item.woodItem.getId(), 1);
		} else if (id == 5) {
			recipeRequirements.put(Food.suspicousChickenItem.getId(), 1);
			recipeRequirements.put(Item.woodenPlankItem.getId(), 3);
		} 
		
		else if (id == 6) {
			recipeRequirements.put(Food.rawMorselItem.getId(), 1);
			recipeRequirements.put(Item.coalChunkItem.getId(), 1);
		} else if (id == 7) {
			recipeRequirements.put(Food.rawMorselItem.getId(), 1);
			recipeRequirements.put(Item.woodItem.getId(), 1);
		} else if (id == 8) {
			recipeRequirements.put(Food.rawMorselItem.getId(), 1);
			recipeRequirements.put(Item.woodenPlankItem.getId(), 3);
		} else if (id == 9) {
			recipeRequirements.put(Food.suspicousMorselItem.getId(), 1);
			recipeRequirements.put(Item.coalChunkItem.getId(), 1);
		} else if (id == 10) {
			recipeRequirements.put(Food.suspicousMorselItem.getId(), 1);
			recipeRequirements.put(Item.woodItem.getId(), 1);
		} else if (id == 11) {
			recipeRequirements.put(Food.suspicousMorselItem.getId(), 1);
			recipeRequirements.put(Item.woodenPlankItem.getId(), 3);
		} 
		
		else if (id == 12) {
			recipeRequirements.put(Food.rawMeatItem.getId(), 1);
			recipeRequirements.put(Item.coalChunkItem.getId(), 1);
		} else if (id == 13) {
			recipeRequirements.put(Food.rawMeatItem.getId(), 1);
			recipeRequirements.put(Item.woodItem.getId(), 1);
		} else if (id == 14) {
			recipeRequirements.put(Food.rawMeatItem.getId(), 1);
			recipeRequirements.put(Item.woodenPlankItem.getId(), 3);
		} else if (id == 15) {
			recipeRequirements.put(Food.suspicousMeatItem.getId(), 1);
			recipeRequirements.put(Item.coalChunkItem.getId(), 1);
		} else if (id == 16) {
			recipeRequirements.put(Food.suspicousMeatItem.getId(), 1);
			recipeRequirements.put(Item.woodItem.getId(), 1);
		} else if (id == 17) {
			recipeRequirements.put(Food.suspicousMeatItem.getId(), 1);
			recipeRequirements.put(Item.woodenPlankItem.getId(), 3);
		} 
		
		else if (id == 18) {
			recipeRequirements.put(Food.bugMeatItem.getId(), 1);
			recipeRequirements.put(Item.woodenPlankItem.getId(), 3);
		} else if (id == 19) {
			recipeRequirements.put(Food.bugMeatItem.getId(), 1);
			recipeRequirements.put(Item.woodenPlankItem.getId(), 3);
		} else if (id == 20) {
			recipeRequirements.put(Food.bugMeatItem.getId(), 1);
			recipeRequirements.put(Item.woodenPlankItem.getId(), 3);
		} 
		
	}

	private void setupItem() {

		if (id == 0) {
			setItem(Food.cookedChickenItem);
		} else if (id == 1) {
			setItem(Food.cookedChickenItem);
		} else if (id == 2) {
			setItem(Food.cookedChickenItem);
		} else if (id == 3) {
			setItem(Food.cookedSuspicousChickenItem);
		} else if (id == 4) {
			setItem(Food.cookedSuspicousChickenItem);
		} else if (id == 5) {
			setItem(Food.cookedSuspicousChickenItem);
		} else if (id == 6) {
			setItem(Food.cookedMorselItem);
		} else if (id == 7) {
			setItem(Food.cookedMorselItem);
		} else if (id == 8) {
			setItem(Food.cookedMorselItem);
		} else if (id == 9) {
			setItem(Food.cookedSuspicousMorselItem);
		} else if (id == 10) {
			setItem(Food.cookedSuspicousMorselItem);
		} else if (id == 11) {
			setItem(Food.cookedSuspicousMorselItem);
		} else if (id == 12) {
			setItem(Food.cookedMeatItem);
		} else if (id == 13) {
			setItem(Food.cookedMeatItem);
		} else if (id == 14) {
			setItem(Food.cookedMeatItem);
		} else if (id == 15) {
			setItem(Food.cookedSuspicousMeatItem);
		} else if (id == 16) {
			setItem(Food.cookedSuspicousMeatItem);
		} else if (id == 17) {
			setItem(Food.cookedSuspicousMeatItem);
		} else if (id == 18) {
			setItem(Food.cookedBugItem);
		} else if (id == 19) {
			setItem(Food.cookedBugItem);
		} else if (id == 20) {
			setItem(Food.cookedBugItem);
		}
		
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