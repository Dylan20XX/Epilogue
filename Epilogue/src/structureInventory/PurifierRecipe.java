package structureInventory;

import java.util.ArrayList;
import java.util.HashMap;

import items.Item;
import items.Weapon;

public class PurifierRecipe {
	
	public HashMap<Integer, Integer> recipeRequirements = new HashMap<Integer, Integer>(); // First int is item id and
																							// second is item amount
																							// required
	private int id;
	private Item item;

	public static ArrayList<PurifierRecipe> recipes = new ArrayList<PurifierRecipe>();//Stores all recipes
	public static PurifierRecipe woodenPlank = new PurifierRecipe(0);
	public static PurifierRecipe rope = new PurifierRecipe(1);
	public static PurifierRecipe woodenClub = new PurifierRecipe(2);
	public static PurifierRecipe stoneClub = new PurifierRecipe(3);
	public static PurifierRecipe bow = new PurifierRecipe(4);
	public static PurifierRecipe arrow = new PurifierRecipe(5);
	
	//TEST RECIPES
	public static PurifierRecipe woodenPlank1 = new PurifierRecipe(0);
	public static PurifierRecipe woodenPlank2 = new PurifierRecipe(0);
	public static PurifierRecipe woodenPlank3 = new PurifierRecipe(0);
	public static PurifierRecipe woodenPlank4 = new PurifierRecipe(0);
	public static PurifierRecipe woodenPlank5 = new PurifierRecipe(0);
	public static PurifierRecipe woodenPlank6 = new PurifierRecipe(0);
	public static PurifierRecipe woodenPlank7 = new PurifierRecipe(0);
	public static PurifierRecipe woodenPlank8 = new PurifierRecipe(0);
	public static PurifierRecipe woodenPlank9 = new PurifierRecipe(0);
	public static PurifierRecipe woodenPlank10 = new PurifierRecipe(0);
	public static PurifierRecipe woodenPlank11 = new PurifierRecipe(0);
	public static PurifierRecipe woodenPlank12 = new PurifierRecipe(0);
	public static PurifierRecipe woodenPlank13 = new PurifierRecipe(0);
	public static PurifierRecipe woodenPlank14 = new PurifierRecipe(0);
	public static PurifierRecipe woodenPlank15 = new PurifierRecipe(0);
	public static PurifierRecipe woodenPlank16 = new PurifierRecipe(0);
	
	private int amtGet = 1;

	public PurifierRecipe(int id) {
		this.id = id;

		setupPurifierRecipeRequirements();
		setupItem();

		recipes.add(this);
	}

	private void setupPurifierRecipeRequirements() {

		if (id == 0) {
			recipeRequirements.put(Item.woodItem.getId(), 1);
			amtGet = 3;
		} else if (id == 1) {
			recipeRequirements.put(Item.silkItem.getId(), 8);
		} else if (id == 2) {
			recipeRequirements.put(Item.woodenPlankItem.getId(), 1);
			recipeRequirements.put(Item.ropeItem.getId(), 1);
		} else if (id == 3) {
			recipeRequirements.put(Item.rockItem.getId(), 8);
			recipeRequirements.put(Item.ropeItem.getId(), 1);
		} else if (id == 4) {
			recipeRequirements.put(Item.silkItem.getId(), 12);
			recipeRequirements.put(Item.woodenPlankItem.getId(), 5);
		} else if (id == 5) {
			recipeRequirements.put(Item.rockItem.getId(), 1);
			recipeRequirements.put(Item.woodenPlankItem.getId(), 1);
			recipeRequirements.put(Item.featherItem.getId(), 1);
		} 

	}

	private void setupItem() {

		if (id == 0) {
			setItem(Item.woodenPlankItem);
		} else if (id == 1) {
			setItem(Item.spikeItem);
		} else if (id == 2) {
			setItem(Weapon.woodenClub);
		} else if (id == 3) {
			setItem(Weapon.stoneClub);
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