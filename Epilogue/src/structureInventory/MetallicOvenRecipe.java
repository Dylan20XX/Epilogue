package structureInventory;

import java.util.ArrayList;
import java.util.HashMap;

import items.Item;

public class MetallicOvenRecipe {
	
	public HashMap<Integer, Integer> recipeRequirements = new HashMap<Integer, Integer>(); // First int is item id and
																							// second is item amount
																							// required
	private int id;
	private Item item;
	
	public static ArrayList<MetallicOvenRecipe> recipes = new ArrayList<MetallicOvenRecipe>(); //Stores all recipes

	public static MetallicOvenRecipe tinIngotItem = new MetallicOvenRecipe(0);
	public static MetallicOvenRecipe bronzeIngotItem = new MetallicOvenRecipe(1);
	public static MetallicOvenRecipe zincIngotItem = new MetallicOvenRecipe(2);
	public static MetallicOvenRecipe goldIngotItem = new MetallicOvenRecipe(3);
	public static MetallicOvenRecipe ironIngotItem = new MetallicOvenRecipe(4);
	public static MetallicOvenRecipe titaniumIngotItem = new MetallicOvenRecipe(5);
	public static MetallicOvenRecipe tungstenIngotItem = new MetallicOvenRecipe(6);
	public static MetallicOvenRecipe tinIngot2Item = new MetallicOvenRecipe(7);
	public static MetallicOvenRecipe bronzeIngot2Item = new MetallicOvenRecipe(8);
	public static MetallicOvenRecipe zincIngot2Item = new MetallicOvenRecipe(9);
	public static MetallicOvenRecipe goldIngot2Item = new MetallicOvenRecipe(10);
	public static MetallicOvenRecipe ironIngot2Item = new MetallicOvenRecipe(11);
	public static MetallicOvenRecipe titaniumIngot2Item = new MetallicOvenRecipe(12);
	public static MetallicOvenRecipe tungstenIngot2Item = new MetallicOvenRecipe(13);
	public static MetallicOvenRecipe tinIngot3Item = new MetallicOvenRecipe(14);
	public static MetallicOvenRecipe bronzeIngot3Item = new MetallicOvenRecipe(15);
	public static MetallicOvenRecipe zincIngot3Item = new MetallicOvenRecipe(16);
	public static MetallicOvenRecipe goldIngot3Item = new MetallicOvenRecipe(17);
	public static MetallicOvenRecipe ironIngot3Item = new MetallicOvenRecipe(18);
	public static MetallicOvenRecipe titaniumIngot3Item = new MetallicOvenRecipe(19);
	public static MetallicOvenRecipe tungstenIngot3Item = new MetallicOvenRecipe(20);
	
	private int amtGet = 1;

	public MetallicOvenRecipe(int id) {
		this.id = id;

		setupRecipeRequirements();
		setupItem();

		recipes.add(this);
	}

	private void setupRecipeRequirements() {

		if (id == 0) {
			recipeRequirements.put(Item.tinChunkItem.getId(), 3);
			recipeRequirements.put(Item.woodenPlankItem.getId(), 4);
		} else if (id == 1) {
			recipeRequirements.put(Item.bronzeChunkItem.getId(), 3);
			recipeRequirements.put(Item.woodenPlankItem.getId(), 4);
		} else if (id == 2) {
			recipeRequirements.put(Item.zincChunkItem.getId(), 4);
			recipeRequirements.put(Item.woodenPlankItem.getId(), 6);
		} else if (id == 3) {
			recipeRequirements.put(Item.goldChunkItem.getId(), 4);
			recipeRequirements.put(Item.woodenPlankItem.getId(), 6);
		} else if (id == 4) {
			recipeRequirements.put(Item.ironChunkItem.getId(), 5);
			recipeRequirements.put(Item.woodenPlankItem.getId(), 9);
		} else if (id == 5) {
			recipeRequirements.put(Item.titaniumChunkItem.getId(), 5);
			recipeRequirements.put(Item.woodenPlankItem.getId(), 9);
		} else if (id == 6) {
			recipeRequirements.put(Item.tungstenChunkItem.getId(), 5);
			recipeRequirements.put(Item.woodenPlankItem.getId(), 9);
		} else if (id == 7) {
			recipeRequirements.put(Item.tinChunkItem.getId(), 3);
			recipeRequirements.put(Item.woodItem.getId(), 1);
		} else if (id == 8) {
			recipeRequirements.put(Item.bronzeChunkItem.getId(), 3);
			recipeRequirements.put(Item.woodItem.getId(), 1);
		} else if (id == 9) {
			recipeRequirements.put(Item.zincChunkItem.getId(), 4);
			recipeRequirements.put(Item.woodItem.getId(), 2);
		} else if (id == 10) {
			recipeRequirements.put(Item.goldChunkItem.getId(), 4);
			recipeRequirements.put(Item.woodItem.getId(), 2);
		} else if (id == 11) {
			recipeRequirements.put(Item.ironChunkItem.getId(), 2);
			recipeRequirements.put(Item.woodItem.getId(), 3);
		} else if (id == 12) {
			recipeRequirements.put(Item.titaniumChunkItem.getId(), 5);
			recipeRequirements.put(Item.woodItem.getId(), 3);
		} else if (id == 13) {
			recipeRequirements.put(Item.tungstenChunkItem.getId(), 5);
			recipeRequirements.put(Item.woodItem.getId(), 3);
		} else if (id == 14) {
			recipeRequirements.put(Item.tinChunkItem.getId(), 3);
			recipeRequirements.put(Item.coalChunkItem.getId(), 1);
		} else if (id == 15) {
			recipeRequirements.put(Item.bronzeChunkItem.getId(), 3);
			recipeRequirements.put(Item.coalChunkItem.getId(), 1);
		} else if (id == 16) {
			recipeRequirements.put(Item.zincChunkItem.getId(), 4);
			recipeRequirements.put(Item.coalChunkItem.getId(), 1);
		} else if (id == 17) {
			recipeRequirements.put(Item.goldChunkItem.getId(), 4);
			recipeRequirements.put(Item.coalChunkItem.getId(), 1);
		} else if (id == 18) {
			recipeRequirements.put(Item.ironChunkItem.getId(), 5);
			recipeRequirements.put(Item.coalChunkItem.getId(), 1);
		} else if (id == 19) {
			recipeRequirements.put(Item.titaniumChunkItem.getId(), 5);
			recipeRequirements.put(Item.coalChunkItem.getId(), 1);
		} else if (id == 20) {
			recipeRequirements.put(Item.tungstenChunkItem.getId(), 5);
			recipeRequirements.put(Item.coalChunkItem.getId(), 1);
		} 
		
	}

	private void setupItem() {

		if (id == 0) {
			setItem(Item.tinIngotItem);
		} else if (id == 1) {
			setItem(Item.bronzeIngotItem);
		} else if (id == 2) {
			setItem(Item.zincIngotItem);
		} else if (id == 3) {
			setItem(Item.goldIngotItem);
		} else if (id == 4) {
			setItem(Item.ironIngotItem);
		} else if (id == 5) {
			setItem(Item.titaniumIngotItem);
		} else if (id == 6) {
			setItem(Item.tungstenIngotItem);
		} else if (id == 7) {
			setItem(Item.tinIngotItem);
		} else if (id == 8) {
			setItem(Item.bronzeIngotItem);
		} else if (id == 9) {
			setItem(Item.zincIngotItem);
		} else if (id == 10) {
			setItem(Item.goldIngotItem);
		} else if (id == 11) {
			setItem(Item.ironIngotItem);
		} else if (id == 12) {
			setItem(Item.titaniumIngotItem);
		} else if (id == 13) {
			setItem(Item.tungstenIngotItem);
		} else if (id == 14) {
			setItem(Item.tinIngotItem);
		} else if (id == 15) {
			setItem(Item.bronzeIngotItem);
		} else if (id == 16) {
			setItem(Item.zincIngotItem);
		} else if (id == 17) {
			setItem(Item.goldIngotItem);
		} else if (id == 18) {
			setItem(Item.ironIngotItem);
		} else if (id == 19) {
			setItem(Item.titaniumIngotItem);
		} else if (id == 20) {
			setItem(Item.tungstenIngotItem);
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