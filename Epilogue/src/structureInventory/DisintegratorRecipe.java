package structureInventory;

import java.util.ArrayList;
import java.util.HashMap;

import items.Item;

public class DisintegratorRecipe {
	
	public HashMap<Integer, Integer> recipeRequirements = new HashMap<Integer, Integer>(); // First int is item id and
																							// second is item amount
																							// required
	private int id;
	private Item item;
	
	public static ArrayList<DisintegratorRecipe> recipes = new ArrayList<DisintegratorRecipe>(); //Stores all recipes

	public static DisintegratorRecipe tinIngot = new DisintegratorRecipe(0);
	public static DisintegratorRecipe bronzeIngot = new DisintegratorRecipe(1);
	public static DisintegratorRecipe bronzeIngot2 = new DisintegratorRecipe(2);
	public static DisintegratorRecipe goldIngot = new DisintegratorRecipe(3);
	public static DisintegratorRecipe zincIngot = new DisintegratorRecipe(4);
	public static DisintegratorRecipe titaniumIngot = new DisintegratorRecipe(5);
	public static DisintegratorRecipe tungstenIngot = new DisintegratorRecipe(6);
	public static DisintegratorRecipe tinIngot2 = new DisintegratorRecipe(7);
	public static DisintegratorRecipe bronzeIngot3 = new DisintegratorRecipe(8);
	public static DisintegratorRecipe bronzeIngot4 = new DisintegratorRecipe(9);
	public static DisintegratorRecipe goldIngot2 = new DisintegratorRecipe(10);
	public static DisintegratorRecipe zincIngot2 = new DisintegratorRecipe(11);
	public static DisintegratorRecipe titaniumIngot2 = new DisintegratorRecipe(12);
	public static DisintegratorRecipe tungstenIngot2 = new DisintegratorRecipe(13);
	public static DisintegratorRecipe tinIngot3 = new DisintegratorRecipe(14);
	public static DisintegratorRecipe bronzeIngot5 = new DisintegratorRecipe(15);
	public static DisintegratorRecipe bronzeIngot6 = new DisintegratorRecipe(16);
	public static DisintegratorRecipe goldIngot3 = new DisintegratorRecipe(17);
	public static DisintegratorRecipe zincIngot3 = new DisintegratorRecipe(18);
	public static DisintegratorRecipe titaniumIngot3 = new DisintegratorRecipe(19);
	public static DisintegratorRecipe tungstenIngot3 = new DisintegratorRecipe(20);
	public static DisintegratorRecipe ironBar1 = new DisintegratorRecipe(21);
	public static DisintegratorRecipe ironBar2 = new DisintegratorRecipe(22);
	public static DisintegratorRecipe ironBar3 = new DisintegratorRecipe(23);
	public static DisintegratorRecipe AMM1D1 = new DisintegratorRecipe(24);
	public static DisintegratorRecipe AMM1D2 = new DisintegratorRecipe(25);
	public static DisintegratorRecipe AMM1D3 = new DisintegratorRecipe(26);
	public static DisintegratorRecipe XM2141 = new DisintegratorRecipe(27);
	public static DisintegratorRecipe XM2142 = new DisintegratorRecipe(28);
	public static DisintegratorRecipe XM2143 = new DisintegratorRecipe(29);
	
	private int amtGet = 1;

	public DisintegratorRecipe(int id) {
		this.id = id;

		setupRecipeRequirements();
		setupItem();

		recipes.add(this);
	}

	private void setupRecipeRequirements() {

		if (id == 0) {
			recipeRequirements.put(Item.rektTinCanItem.getId(), 1);
			recipeRequirements.put(Item.coalChunkItem.getId(), 1);
		} else if (id == 1) {
			recipeRequirements.put(Item.redWireItem.getId(), 1);
			recipeRequirements.put(Item.coalChunkItem.getId(), 1);
		} else if (id == 2) {
			recipeRequirements.put(Item.blackWireItem.getId(), 1);
			recipeRequirements.put(Item.coalChunkItem.getId(), 1);
		} else if (id == 3) {
			recipeRequirements.put(Item.semiconductorItem.getId(), 1);
			recipeRequirements.put(Item.coalChunkItem.getId(), 1);
		} else if (id == 4) {
			recipeRequirements.put(Item.pipeHeadItem.getId(), 1);
			recipeRequirements.put(Item.coalChunkItem.getId(), 1);
		} else if (id == 5) {
			recipeRequirements.put(Item.brokenGolfClubItem.getId(), 1);
			recipeRequirements.put(Item.coalChunkItem.getId(), 1);
		} else if (id == 6) {
			recipeRequirements.put(Item.brokenVaultDoorItem.getId(), 1);
			recipeRequirements.put(Item.coalChunkItem.getId(), 1);
		} 
		else if (id == 7) {
			recipeRequirements.put(Item.rektTinCanItem.getId(), 1);
			recipeRequirements.put(Item.woodItem.getId(), 1);
		} else if (id == 8) {
			recipeRequirements.put(Item.redWireItem.getId(), 1);
			recipeRequirements.put(Item.woodItem.getId(), 1);
		} else if (id == 9) {
			recipeRequirements.put(Item.blackWireItem.getId(), 1);
			recipeRequirements.put(Item.woodItem.getId(), 1);
		} else if (id == 10) {
			recipeRequirements.put(Item.semiconductorItem.getId(), 1);
			recipeRequirements.put(Item.woodItem.getId(), 1);
		} else if (id == 11) {
			recipeRequirements.put(Item.pipeHeadItem.getId(), 1);
			recipeRequirements.put(Item.woodItem.getId(), 1);
		} else if (id == 12) {
			recipeRequirements.put(Item.brokenGolfClubItem.getId(), 1);
			recipeRequirements.put(Item.woodItem.getId(), 1);
		} else if (id == 13) {
			recipeRequirements.put(Item.brokenVaultDoorItem.getId(), 1);
			recipeRequirements.put(Item.woodItem.getId(), 1);
		} 
		else if (id == 14) {
			recipeRequirements.put(Item.rektTinCanItem.getId(), 1);
			recipeRequirements.put(Item.woodenPlankItem.getId(), 3);
		} else if (id == 15) {
			recipeRequirements.put(Item.redWireItem.getId(), 1);
			recipeRequirements.put(Item.woodenPlankItem.getId(), 3);
		} else if (id == 16) {
			recipeRequirements.put(Item.blackWireItem.getId(), 1);
			recipeRequirements.put(Item.woodenPlankItem.getId(), 3);
		} else if (id == 17) {
			recipeRequirements.put(Item.semiconductorItem.getId(), 1);
			recipeRequirements.put(Item.woodenPlankItem.getId(), 3);
		} else if (id == 18) {
			recipeRequirements.put(Item.pipeHeadItem.getId(), 1);
			recipeRequirements.put(Item.woodenPlankItem.getId(), 3);
		} else if (id == 19) {
			recipeRequirements.put(Item.brokenGolfClubItem.getId(), 1);
			recipeRequirements.put(Item.woodenPlankItem.getId(), 3);
		} else if (id == 20) {
			recipeRequirements.put(Item.brokenVaultDoorItem.getId(), 1);
			recipeRequirements.put(Item.woodenPlankItem.getId(), 3);
		} else if (id == 21) {
			recipeRequirements.put(Item.ironBarItem.getId(), 1);
			recipeRequirements.put(Item.coalChunkItem.getId(), 1);
		} else if (id == 22) {
			recipeRequirements.put(Item.ironBarItem.getId(), 1);
			recipeRequirements.put(Item.woodItem.getId(), 1);
		} else if (id == 23) {
			recipeRequirements.put(Item.ironBarItem.getId(), 1);
			recipeRequirements.put(Item.woodenPlankItem.getId(), 3);
		} else if (id == 24) {
			recipeRequirements.put(Item.AMM1DBulletItem.getId(), 4);
			recipeRequirements.put(Item.coalChunkItem.getId(), 1);
		} else if (id == 25) {
			recipeRequirements.put(Item.AMM1DBulletItem.getId(), 4);
			recipeRequirements.put(Item.woodItem.getId(), 1);
		} else if (id == 26) {
			recipeRequirements.put(Item.AMM1DBulletItem.getId(), 4);
			recipeRequirements.put(Item.woodenPlankItem.getId(), 3);
		} else if (id == 27) {
			recipeRequirements.put(Item.XM214BulletItem.getId(), 1);
			recipeRequirements.put(Item.coalChunkItem.getId(), 1);
		} else if (id == 28) {
			recipeRequirements.put(Item.XM214BulletItem.getId(), 1);
			recipeRequirements.put(Item.woodItem.getId(), 1);
		} else if (id == 29) {
			recipeRequirements.put(Item.XM214BulletItem.getId(), 1);
			recipeRequirements.put(Item.woodenPlankItem.getId(), 3);
		} 
		
		//fuel requirement (can be replaced with coal)
		recipeRequirements.put(Item.woodenPlankItem.getId(), 1);
		
	}

	private void setupItem() {
		
		if (id == 0) {
			setItem(Item.tinIngotItem);
			amtGet = 2;
		} else if (id == 1) {
			setItem(Item.bronzeIngotItem);
		} else if (id == 2) {
			setItem(Item.bronzeIngotItem);
		} else if (id == 3) {
			setItem(Item.goldIngotItem);
		} else if (id == 4) {
			setItem(Item.zincIngotItem);
		} else if (id == 5) {
			setItem(Item.titaniumIngotItem);
		} else if (id == 6) {
			setItem(Item.tungstenIngotItem);
		} 
		else if (id == 7) {
			setItem(Item.tinIngotItem);
			amtGet = 2;
		} else if (id == 8) {
			setItem(Item.bronzeIngotItem);
		} else if (id == 9) {
			setItem(Item.bronzeIngotItem);
		} else if (id == 10) {
			setItem(Item.goldIngotItem);
		} else if (id == 11) {
			setItem(Item.zincIngotItem);
		} else if (id == 12) {
			setItem(Item.titaniumIngotItem);
		} else if (id == 13) {
			setItem(Item.tungstenIngotItem);
		} 
		else if (id == 14) {
			setItem(Item.tinIngotItem);
			amtGet = 2;
		} else if (id == 15) {
			setItem(Item.bronzeIngotItem);
		} else if (id == 16) {
			setItem(Item.bronzeIngotItem);
		} else if (id == 17) {
			setItem(Item.goldIngotItem);
		} else if (id == 18) {
			setItem(Item.zincIngotItem);
		} else if (id == 19) {
			setItem(Item.titaniumIngotItem);
		} else if (id == 20) {
			setItem(Item.tungstenIngotItem);
		} 
		else if (id == 21) {
			setItem(Item.ironIngotItem);
			amtGet = 3;
		} else if (id == 22) {
			setItem(Item.ironIngotItem);
			amtGet = 3;
		} else if (id == 23) {
			setItem(Item.ironIngotItem);
			amtGet = 3;
		} else if (id == 24) {
			setItem(Item.zincIngotItem);
		} else if (id == 25) {
			setItem(Item.zincIngotItem);
		} else if (id == 26) {
			setItem(Item.zincIngotItem);
		} else if (id == 27) {
			setItem(Item.zincIngotItem);
		} else if (id == 28) {
			setItem(Item.zincIngotItem);
		} else if (id == 29) {
			setItem(Item.zincIngotItem);
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