
package inventory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import alphaPackage.ControlCenter;
import audio.AudioPlayer;
import creatures.Player;
import graphics.Assets;
import graphics.CustomTextWritter;
import items.Armor;
import items.Food;
import items.Item;
import items.Ranged;
import items.Tool;
import items.WaterContainer;
import items.Weapon;
import structureInventory.SmithingTableRecipe;
import structureInventory.WorkbenchRecipe;
import tiles.Tile;
import utils.ClickListener;
import utils.ImageButton;
import utils.UIManager;

public class HandCraft {

	private boolean active;
	private ControlCenter c;

	private UIManager uiManager;

	private ArrayList<Recipe> recipeList = new ArrayList<Recipe>(); //Stores recipes that can be crafted

	//Different Category Recipe Lists
	private ArrayList<Recipe> materialRecipeList = new ArrayList<Recipe>();
	private ArrayList<Recipe> weaponRecipeList = new ArrayList<Recipe>();
	private ArrayList<Recipe> armorRecipeList = new ArrayList<Recipe>();
	private ArrayList<Recipe> foodRecipeList = new ArrayList<Recipe>();
	private ArrayList<Recipe> structureRecipeList = new ArrayList<Recipe>();
	private String selectedCategory = "material";

	private boolean addButtons = true;
	private boolean addCraftButton = true;

	private Item craftableItem; // Item to be crafted
	private String craftableItemName; // Name of item to be crafted
	public HashMap<Item, Integer> recipeRequirements = new HashMap<Item, Integer>(); //Stores item and amount needed for crafting

	private Recipe selectedRecipe;

	// crafting
	private String craftingRequirementsString;
	private boolean craftingButtonPressed = false;
	private int craftCount = 0;

	private int pageNum = 0;

	private boolean currentlyCrafting = false;
	private long lastCraftTimer, craftCooldown = 1000, craftTimer = 0;

	public HandCraft(ControlCenter c) {

		this.c = c;

		uiManager = new UIManager();
		
	}

	//This method fills the recipeList ArrayList with craftable recipes
	private void findCraftableRecipes() {

		for (Recipe i : Recipe.unlockedRecipes){

			recipeList.add(i);

		}

		//Add the recipes to different category arraylists
		for (Recipe i : recipeList){
			if(i.getItem() instanceof Weapon || i.getItem() instanceof Ranged || i.getItem() instanceof Tool)
				weaponRecipeList.add(i);
			else if(i.getItem() instanceof Armor)
				armorRecipeList.add(i);
			else if(i.getItem() instanceof Food)
				foodRecipeList.add(i);
			else if(i.isStructure()) //i.getItem().getType().equals("structure") || i.getItem().getType().equals("platform")
				structureRecipeList.add(i);
			else //else if(i.getItem().getType().equals("miscellaneous")) & holds water containers
				materialRecipeList.add(i);
			
		}
		
	}

	//This method checks if a recipe can be crafted
	private boolean isRecipeCraftable(Recipe recipe) {

		boolean canCraft = true;
		
		if(!correctStructure(recipe))
			return false;
			
		for (Map.Entry<Integer, Integer> entry : recipe.recipeRequirements.entrySet()) {

			boolean itemFound = false;

			for(Item i: Player.getPlayerData().getInventory().InventoryItems) {

				if(i.getId() == entry.getKey() && i.getCount() >= entry.getValue()) {
					itemFound = true;
					break;
				}

			}

			if(itemFound == false) {
				canCraft = false;
				break;
			}

		}

		return canCraft;
	}
	
	private boolean correctStructure(Recipe recipe) {
		
		if(Player.getPlayerData().isWorkbenchActive()) { //return false if workbench is open and smithing table recipe is selected
			if(recipe instanceof SmithingTableRecipe)
				return false;
		} else if(Player.getPlayerData().isSmithingTableActive()) { //return false if smithing table is open and non-smithing table recipe is selected
			if(!(recipe instanceof SmithingTableRecipe)) 
				return false;
		} else { //return false is handcraft is open and non-handcraft recipe is selected
			if((recipe instanceof WorkbenchRecipe) || (recipe instanceof SmithingTableRecipe))
				return false;
		}
		
		return true;
	}

	// This method adds image buttons for the recipes
	private void addRecipeButtons() {

		int numCraftable = 0; // used for positioning image buttons
		int row = 0; // for positioning multiple rows of image buttons

		for(int i = pageNum * 16; i < (pageNum + 1) * 16 && i < selectedArrayList().size(); i++) {

			int x = i;

			uiManager.addObject(new ImageButton(
					c.getWidth() / 2 - Assets.handCraft.getWidth() * 4 + 50
					+ (numCraftable % 4) * (100 * ControlCenter.scaleValue),
					c.getHeight() / 2 - Assets.handCraft.getHeight() * 4 + 40
					+ row * (100* ControlCenter.scaleValue),
					(int) (75 * ControlCenter.scaleValue), (int) (75 * ControlCenter.scaleValue),
					selectedArrayList().get(i).getItem().getTexture(), new ClickListener() {

						@Override
						public void onClick() {

							if(!currentlyCrafting) {

								selectedRecipe = selectedArrayList().get(x);

								craftingButtonPressed = true;
								craftCount = selectedArrayList().get(x).getAmtGet();
								craftableItem = selectedArrayList().get(x).getItem();
								craftableItemName = selectedArrayList().get(x).getItem().getName();

								recipeRequirements.clear();

								for (Map.Entry<Integer, Integer> entry : selectedArrayList().get(x).recipeRequirements.entrySet()) {
									recipeRequirements.put(Item.items[entry.getKey()], entry.getValue());
								}

							}

						}

					}));

			numCraftable++;

			row = numCraftable / 4;

		}

	}

	private void addCraftingButton() {

		uiManager.addObject(new ImageButton(c.getWidth() / 2 - Assets.handCraft.getWidth() * 4 + 600,
				c.getHeight() / 2 - Assets.handCraft.getHeight() * 4 + 490, (int) (75 * ControlCenter.scaleValue),
				(int) (75 * ControlCenter.scaleValue), Assets.equipmentButton, new ClickListener() {

			@Override
			public void onClick() {

				if (craftableItem != null && !currentlyCrafting) {

					if (isRecipeCraftable(selectedRecipe)) {

						//If the crafted item is a structure it must be placed down
						if(selectedRecipe.isStructure()) {

							active = false;
							c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().setStructureCrafted(true);
							c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().setStructureNum(selectedRecipe.getId() - 700);
							c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().setStructureRecipe(selectedRecipe);
							resetHandCraft();
							
						} else {

							AudioPlayer.playAudio("audio/craft.wav");
							
							new java.util.Timer().schedule(new java.util.TimerTask() {
								@Override
								public void run() {
									AudioPlayer.playAudio("audio/craft.wav");
								}
							}, 900);
							
							currentlyCrafting = true;
							lastCraftTimer = System.currentTimeMillis();

						}

					}

				}
			}

		}));

	}

	private void craft() {

		craftTimer += System.currentTimeMillis() - lastCraftTimer;
		lastCraftTimer = System.currentTimeMillis();

		if (craftTimer < craftCooldown)
			return;

		//Add crafted item to inventory
		for(int i = 0; i < craftCount; i++)
			Player.getPlayerData().getInventory().addItem(craftableItem);

		// Remove items used in crafting for recipes with multiple ingredients
		for (Entry<Item, Integer> entry : recipeRequirements.entrySet()) {
			for (int i = 0; i < entry.getValue(); i++) 
				Player.getPlayerData().getInventory().removeItem(entry.getKey());
		}

		//Give cooking xp to player if food was made
		if (craftableItem instanceof Food)
			Player.getPlayerData().setCookingXP(Player.getPlayerData().getCookingXP() + ((Food)craftableItem).cookingXPGiven);


		currentlyCrafting = false; 
		craftTimer = 0; // cool down resets
	}

	//This method adds the page buttons and the category buttons
	private void addPageButtons() {

		if(pageNum > 0)
			uiManager.addObject(new ImageButton(
					c.getWidth() / 2 - Assets.handCraft.getWidth() * 4 + 50
					+ 0 * (100 * ControlCenter.scaleValue),
					c.getHeight() / 2 - Assets.handCraft.getHeight() * 4 + 40
					+ 6 * (75* ControlCenter.scaleValue),
					(int) (75 * ControlCenter.scaleValue), (int) (75 * ControlCenter.scaleValue),
					Assets.sLeft, new ClickListener() {

						@Override
						public void onClick() {

							if(!currentlyCrafting) {

								if(pageNum > 0)
									pageNum--;

								craftableItem = null;
								addButtons = true;
								addCraftButton = true;
								craftingButtonPressed = false;
								uiManager.getObjects().clear();
								recipeRequirements.clear();

							}

						}

					}));

		if((pageNum + 1) * 16 < selectedArrayList().size())
			uiManager.addObject(new ImageButton(
					c.getWidth() / 2 - Assets.handCraft.getWidth() * 4 + 70
					+ 3 * (100 * ControlCenter.scaleValue),
					c.getHeight() / 2 - Assets.handCraft.getHeight() * 4 + 40
					+ 6 * (75* ControlCenter.scaleValue),
					(int) (75 * ControlCenter.scaleValue), (int) (75 * ControlCenter.scaleValue),
					Assets.sRight, new ClickListener() {

						@Override
						public void onClick() {

							if(!currentlyCrafting) {

								if((pageNum + 1) * 16 < selectedArrayList().size())
									pageNum++;

								craftableItem = null;
								addButtons = true;
								addCraftButton = true;
								craftingButtonPressed = false;
								uiManager.getObjects().clear();
								recipeRequirements.clear();

							}

						}

					}));

		//Category Buttons
		//Material
		uiManager.addObject(new ImageButton(
				c.getWidth() / 2 - Assets.handCraft.getWidth() * 4 - Assets.handCraft.getHeight() * 8 / 5,
				c.getHeight() / 2 - Assets.handCraft.getHeight() * 4,
				(int) (Assets.materialButton2.getHeight() * 8), 
				(int) (Assets.materialButton2.getHeight() * 8),
				Assets.materialButton, new ClickListener() {

					@Override
					public void onClick() {
						selectedCategory = "material";
						
						pageNum = 0;
						craftableItem = null;
						addButtons = true;
						addCraftButton = true;
						craftingButtonPressed = false;
						uiManager.getObjects().clear();
						recipeRequirements.clear();
					}

				}));

		//Weapon
		uiManager.addObject(new ImageButton(
				c.getWidth() / 2 - Assets.handCraft.getWidth() * 4 - Assets.handCraft.getHeight() * 8 / 5,
				c.getHeight() / 2 - Assets.handCraft.getHeight() * 4 + Assets.handCraft.getHeight() * 8 / 5,
				(int) (Assets.materialButton2.getHeight() * 8), 
				(int) (Assets.materialButton2.getHeight() * 8),
				Assets.weaponButton, new ClickListener() {

					@Override
					public void onClick() {
						selectedCategory = "weapon";
						
						pageNum = 0;
						craftableItem = null;
						addButtons = true;
						addCraftButton = true;
						craftingButtonPressed = false;
						uiManager.getObjects().clear();
						recipeRequirements.clear();
					}

				}));

		//Armor
		uiManager.addObject(new ImageButton(
				c.getWidth() / 2 - Assets.handCraft.getWidth() * 4 - Assets.handCraft.getHeight() * 8 / 5,
				c.getHeight() / 2 - Assets.handCraft.getHeight() * 4 + Assets.handCraft.getHeight() * 8 / 5 * 2,
				(int) (Assets.materialButton2.getHeight() * 8), 
				(int) (Assets.materialButton2.getHeight() * 8),
				Assets.armorButton, new ClickListener() {

					@Override
					public void onClick() {
						selectedCategory = "armor";
						
						pageNum = 0;
						craftableItem = null;
						addButtons = true;
						addCraftButton = true;
						craftingButtonPressed = false;
						uiManager.getObjects().clear();
						recipeRequirements.clear();
					}

				}));

		//Food
		uiManager.addObject(new ImageButton(
				c.getWidth() / 2 - Assets.handCraft.getWidth() * 4 - Assets.handCraft.getHeight() * 8 / 5,
				c.getHeight() / 2 - Assets.handCraft.getHeight() * 4 + Assets.handCraft.getHeight() * 8 / 5 * 3,
				(int) (Assets.materialButton2.getHeight() * 8), 
				(int) (Assets.materialButton2.getHeight() * 8),
				Assets.foodButton, new ClickListener() {

					@Override
					public void onClick() {
						selectedCategory = "food";
						
						pageNum = 0;
						craftableItem = null;
						addButtons = true;
						addCraftButton = true;
						craftingButtonPressed = false;
						uiManager.getObjects().clear();
						recipeRequirements.clear();
					}

				}));

		//Structure
		uiManager.addObject(new ImageButton(
				c.getWidth() / 2 - Assets.handCraft.getWidth() * 4 - Assets.handCraft.getHeight() * 8 / 5,
				c.getHeight() / 2 - Assets.handCraft.getHeight() * 4 + Assets.handCraft.getHeight() * 8 / 5 * 4,
				(int) (Assets.materialButton2.getHeight() * 8), 
				(int) (Assets.materialButton2.getHeight() * 8),
				Assets.structureButton, new ClickListener() {

					@Override
					public void onClick() {
						selectedCategory = "structure";
						
						pageNum = 0;
						craftableItem = null;
						addButtons = true;
						addCraftButton = true;
						craftingButtonPressed = false;
						uiManager.getObjects().clear();
						recipeRequirements.clear();
					}

				}));

	}

	public void tick() {

		if (c.getKeyManager().keyJustPressed(KeyEvent.VK_C) && !currentlyCrafting && !c.getGameState().getWorldGenerator().isStructureCrafted() &&
				!c.getGameState().getWorldGenerator().isStructureWorkbenchCrafted() && !c.getGameState().getWorldGenerator().isCurrentlyBuildingStructure()) {

			if(!active) { //setup the gui when handcraft is set to active
				findCraftableRecipes();
			} else if(active) { //reset variables when exiting hand craft

				resetHandCraft();
			}

			active = !active;

		}

		//Press E to exit handcraft
		if(c.getKeyManager().keyJustPressed(KeyEvent.VK_E) && active && !currentlyCrafting) {

			resetHandCraft();
			active = false;

		}

		if(currentlyCrafting)
			craft();

	}

	public void render(Graphics g) {

		if (active) {
			g.drawImage(Assets.handCraft, c.getWidth() / 2 - Assets.handCraft.getWidth() * 4,
					c.getHeight() / 2 - Assets.handCraft.getHeight() * 4, Assets.handCraft.getWidth() * 8,
					Assets.handCraft.getHeight() * 8, null);

			c.getMouseManager().setUIManager(uiManager);

			// Render the stuff for the item to be crafted here 
			if (addButtons)
				addRecipeButtons();
			addButtons = false;

			if (addCraftButton) {
				addCraftingButton();
				addPageButtons();
			}
			addCraftButton = false; 

			// Render the info about the craft
			if (craftingButtonPressed) {
				g.drawImage(craftableItem.getTexture(), (int) (800 * c.getScaleValue()),
						(int) (170 * c.getScaleValue()), (int) (150 * c.getScaleValue()),
						(int) (150 * c.getScaleValue()), null);
				
				// Show if correct crafting structure is used
				if(!correctStructure(selectedRecipe)) {
					
					String structureRequired = "";
					
					if(selectedRecipe instanceof WorkbenchRecipe) {
						structureRequired = "Workbench Required";
					} else if(selectedRecipe instanceof SmithingTableRecipe) {
						structureRequired = "Smithing Table Required";
					} else {
						structureRequired = "Craft by hand";
					}
					
					CustomTextWritter.drawString(g, structureRequired, (int) (875 * c.getScaleValue()),
							(int) (380 * c.getScaleValue()), active, Color.WHITE, Assets.font16);
				}

				// Name of craftable item
				CustomTextWritter.drawString(g, craftableItemName, (int) (875 * c.getScaleValue()),
						(int) (400 * c.getScaleValue()), active, Color.WHITE, Assets.font16);

				// Weight of craftable item
				CustomTextWritter.drawString(g, "Weight: " + craftableItem.getWeight() + " lb",
						(int) (875 * c.getScaleValue()), (int) (443 * c.getScaleValue()), active, Color.WHITE,
						Assets.font16);

				// Requirements
				CustomTextWritter.drawString(g, "Requirements:", (int) (875 * c.getScaleValue()),
						(int) (480 * c.getScaleValue()), active, Color.WHITE, Assets.font16);

				int i = 0;
				for (Entry<Item, Integer> entry : recipeRequirements.entrySet()) {

					// Player.getPlayerData().getInventory().getItemCount(craftingIngredientList[a])
					// replaces craftingIngredientList[a].getCount()
					craftingRequirementsString = String.format("%d / %d %s", 
							Player.getPlayerData().getInventory().getItemCount(entry.getKey()), entry.getValue(),
							entry.getKey().getName());
					CustomTextWritter.drawString(g, craftingRequirementsString, (int) (875 * c.getScaleValue()),
							(int) ((505 + i * 20) * c.getScaleValue()), active, Color.WHITE, Assets.font16);

					if (entry.getValue() > Player.getPlayerData().getInventory()
							.getItemCount(entry.getKey())) {
						CustomTextWritter.drawString(g, "NOT ENOUGH MATERIALS", (int) (875 * c.getScaleValue()),
								(int) (700 * c.getScaleValue()), active, Color.WHITE, Assets.font16);
					}

					i++;
				}

			}

			//Show time left for crafting
			if(currentlyCrafting) {
				g.setColor(Color.GREEN);
				g.fillRect((int) ((c.getWidth() / 2 - 100) * c.getScaleValue()),(int) (750 * c.getScaleValue()), (int) (((double)(craftCooldown - craftTimer) / (double) craftCooldown) * 200), 10);
			}
			
			if (uiManager != null)
				uiManager.render(g);
			
			//Show selected category highlighted yellow
			if(selectedCategory.equals("material"))
				g.drawImage(Assets.materialButton2, (int) (c.getWidth() / 2 - Assets.handCraft.getWidth() * 4 - Assets.handCraft.getHeight() * 8 / 5),
						(int) (c.getHeight() / 2 - Assets.handCraft.getHeight() * 4), 
						(int) (Assets.materialButton2.getHeight() * 8),
						(int) (Assets.materialButton2.getHeight() * 8), null);
			else if(selectedCategory.equals("weapon"))
				g.drawImage(Assets.weaponButton2, (int) (c.getWidth() / 2 - Assets.handCraft.getWidth() * 4 - Assets.handCraft.getHeight() * 8 / 5),
						(int) (c.getHeight() / 2 - Assets.handCraft.getHeight() * 4 + Assets.handCraft.getHeight() * 8 / 5), 
						(int) (Assets.materialButton2.getHeight() * 8),
						(int) (Assets.materialButton2.getHeight() * 8), null);
			else if(selectedCategory.equals("armor"))
				g.drawImage(Assets.armorButton2, (int) (c.getWidth() / 2 - Assets.handCraft.getWidth() * 4 - Assets.handCraft.getHeight() * 8 / 5),
						(int) (c.getHeight() / 2 - Assets.handCraft.getHeight() * 4 + Assets.handCraft.getHeight() * 8 / 5 * 2), 
						(int) (Assets.materialButton2.getHeight() * 8),
						(int) (Assets.materialButton2.getHeight() * 8), null);
			else if(selectedCategory.equals("food"))
				g.drawImage(Assets.foodButton2, (int) (c.getWidth() / 2 - Assets.handCraft.getWidth() * 4 - Assets.handCraft.getHeight() * 8 / 5),
						(int) (c.getHeight() / 2 - Assets.handCraft.getHeight() * 4 + Assets.handCraft.getHeight() * 8 / 5 * 3), 
						(int) (Assets.materialButton2.getHeight() * 8),
						(int) (Assets.materialButton2.getHeight() * 8), null);
			else if(selectedCategory.equals("structure"))
				g.drawImage(Assets.structureButton2, (int) (c.getWidth() / 2 - Assets.handCraft.getWidth() * 4 - Assets.handCraft.getHeight() * 8 / 5),
						(int) (c.getHeight() / 2 - Assets.handCraft.getHeight() * 4 + Assets.handCraft.getHeight() * 8 / 5 * 4), 
						(int) (Assets.materialButton2.getHeight() * 8),
						(int) (Assets.materialButton2.getHeight() * 8), null);
			
		}
	}

	private void resetHandCraft() {
		craftableItem = null;
		addButtons = true;
		addCraftButton = true;
		craftingButtonPressed = false;

		// return the mouse manager to world generator
		c.getMouseManager().setUIManager(c.getMenuState().getWorldSelectState().getGameState()
				.getWorldGenerator().getUiManager());

		uiManager.getObjects().clear();// Clear the objects from the uiManager

		recipeRequirements.clear();
		recipeList.clear();//Clear the recipe list

		materialRecipeList.clear();
		weaponRecipeList.clear();
		armorRecipeList.clear();
		foodRecipeList.clear();
		structureRecipeList.clear();

		pageNum = 0;
		
		Player.getPlayerData().setWorkbenchActive(false);
		c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
		.setWorkbenchActive(false);		
		Player.getPlayerData().setSmithingTableActive(false);
		c.getMenuState().getWorldSelectState().getGameState()
		.getWorldGenerator().setSmithingTableActive(false);
	}

	public void setupHandCraft() {

		findCraftableRecipes();
		active = true;

	}

	private ArrayList<Recipe> selectedArrayList() {

		if(selectedCategory.equals("material"))
			return materialRecipeList;
		else if(selectedCategory.equals("weapon"))
			return weaponRecipeList;
		else if(selectedCategory.equals("armor"))
			return armorRecipeList;
		else if(selectedCategory.equals("food"))
			return foodRecipeList;
		else if(selectedCategory.equals("structure"))
			return structureRecipeList;

		return recipeList;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}