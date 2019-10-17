package structureInventory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import alphaPackage.ControlCenter;
import creatures.Player;
import graphics.Assets;
import graphics.CustomTextWritter;
import items.Item;
import utils.ClickListener;
import utils.ImageButton;
import utils.UIManager;

public class PurifierCraft {

	private ControlCenter c;
	private boolean active = false;

	private UIManager uiManager;

	private ArrayList<PurifierRecipe> recipeList = new ArrayList<PurifierRecipe>(); //Stores recipes that can be crafted

	private boolean addButtons;
	private boolean addCraftButton;

	private Item craftableItem; // Item to be crafted
	private String craftableItemName; // Name of item to be crafted
	public HashMap<Item, Integer> recipeRequirements = new HashMap<Item, Integer>(); //Stores item and amount needed for crafting

	private PurifierRecipe selectedRecipe;

	private String craftingRequirementsString;
	private boolean craftingButtonPressed = false;
	private int craftCount = 0;
	
	private int pageNum = 0;

	public HashMap<Integer, Integer> currentlyCooking = new HashMap<Integer, Integer>(); //holds items to be smelted
	public HashMap<Integer, Integer> products = new HashMap<Integer, Integer>(); //holds items smelted

	//smelt timer variables
	private long lastCookTimer, cookTime = 10000, cookTimer = 0;

	public PurifierCraft(ControlCenter c) {
		this.c = c;

		uiManager = new UIManager();

	}
	
	//This method fills the recipeList ArrayList with craftable recipes
	public void findCraftableRecipes() {

		for (PurifierRecipe i : PurifierRecipe.recipes){
			if(isRecipeCraftable(i))
				recipeList.add(i);
		}

	}
	
	//This method checks if a recipe can be crafted
	private boolean isRecipeCraftable(PurifierRecipe recipe) {

		boolean canCraft = true;

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
	
	// This method adds image buttons for the recipes
	private void addRecipeButtons() {

		int numCraftable = 0; // used for positioning image buttons
		int row = 0; // for positioning multiple rows of image buttons

		for(int i = pageNum * 16; i < (pageNum + 1) * 16 && i < recipeList.size(); i++) {

			int x = i;

			uiManager.addObject(new ImageButton(
					c.getWidth() / 2 - Assets.handCraft.getWidth() * 4 + 50
					+ (numCraftable % 4) * (100 * ControlCenter.scaleValue),
					c.getHeight() / 2 - Assets.handCraft.getHeight() * 4 + 40
					+ row * (75* ControlCenter.scaleValue),
					(int) (75 * ControlCenter.scaleValue), (int) (75 * ControlCenter.scaleValue),
					recipeList.get(i).getItem().getTexture(), new ClickListener() {

						@Override
						public void onClick() {

							selectedRecipe = recipeList.get(x);

							craftingButtonPressed = true;
							craftCount = recipeList.get(x).getAmtGet();
							craftableItem = recipeList.get(x).getItem();
							craftableItemName = recipeList.get(x).getItem().getName();

							recipeRequirements.clear();

							for (Map.Entry<Integer, Integer> entry : recipeList.get(x).recipeRequirements.entrySet()) {
								recipeRequirements.put(Item.items[entry.getKey()], entry.getValue());
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

				if (craftableItem != null) {

					if (isRecipeCraftable(selectedRecipe)) {
						
						//Begin smelting the item to be made
						startCooking(craftableItem, craftCount);

						// Remove items used in crafting for recipes with multiple ingredients
						for (Entry<Item, Integer> entry : recipeRequirements.entrySet()) {
							for (int i = 0; i < entry.getValue(); i++) 
								Player.getPlayerData().getInventory().removeItem(entry.getKey());
						}

					}

				}
			}

		}));

	}
	
	private void addPageButtons() {

		if(pageNum > 0)
			uiManager.addObject(new ImageButton(
					c.getWidth() / 2 - Assets.handCraft.getWidth() * 4 + 50
					+ 0 * (100 * ControlCenter.scaleValue),
					c.getHeight() / 2 - Assets.handCraft.getHeight() * 4 + 40
					+ 5 * (75* ControlCenter.scaleValue),
					(int) (75 * ControlCenter.scaleValue), (int) (75 * ControlCenter.scaleValue),
					Assets.sLeft, new ClickListener() {

						@Override
						public void onClick() {

							if(pageNum > 0)
								pageNum--;

							craftableItem = null;
							addButtons = true;
							addCraftButton = true;
							craftingButtonPressed = false;
							uiManager.getObjects().clear();
							recipeRequirements.clear();

						}

					}));

		if((pageNum + 1) * 16 < recipeList.size())
			uiManager.addObject(new ImageButton(
					c.getWidth() / 2 - Assets.handCraft.getWidth() * 4 + 50
					+ 3 * (100 * ControlCenter.scaleValue),
					c.getHeight() / 2 - Assets.handCraft.getHeight() * 4 + 40
					+ 5 * (75* ControlCenter.scaleValue),
					(int) (75 * ControlCenter.scaleValue), (int) (75 * ControlCenter.scaleValue),
					Assets.sRight, new ClickListener() {

						@Override
						public void onClick() {

							if((pageNum + 1) * 16 < recipeList.size())
								pageNum++;

							craftableItem = null;
							addButtons = true;
							addCraftButton = true;
							craftingButtonPressed = false;
							uiManager.getObjects().clear();
							recipeRequirements.clear();

						}

					}));

	}
	
	//This method sets up the crafting recipes when the player interacts with the static entity
	public void craftSetup() { 
		//reset variables
		craftableItem = null;
		addButtons = true;
		addCraftButton = true;
		craftingButtonPressed = false;

		uiManager.getObjects().clear();// Clear the objects from the uiManager

		recipeRequirements.clear();
		recipeList.clear();//Clear the recipe list

		pageNum = 0;
	}
	
	public void tick() {

		cook();

		if(Player.getPlayerData().isPurifierActive()){

			if (c.getKeyManager().keyJustPressed(KeyEvent.VK_E)) {
				active = false;
				Player.getPlayerData().setPurifierActive(false);
				c.getMenuState().getWorldSelectState().getGameState()
				.getWorldGenerator().setPurifierActive(false);

				// return the mouse manager to world generator
				c.getMouseManager().setUIManager(c.getMenuState().getWorldSelectState().getGameState()
						.getWorldGenerator().getUiManager());

				uiManager.getObjects().clear();// Clear the objects from the uiManager
			}

		}
	}

	public void render(Graphics g) {
		
		if (!active) {
			return;
		}


		if (active) {
			g.drawImage(Assets.handCraft, c.getWidth() / 2 - Assets.handCraft.getWidth() * 4,
					c.getHeight() / 2 - Assets.handCraft.getHeight() * 4, Assets.handCraft.getWidth() * 8,
					Assets.handCraft.getHeight() * 8, null);
			
			CustomTextWritter.drawString(g, "Purifier", (int) (c.getWidth() / 2 * c.getScaleValue()),
					(int) (75 * c.getScaleValue()), active, Color.WHITE, Assets.font36);

			c.getMouseManager().setUIManager(uiManager);

			// Render the stuff for the item to be crafted here
			if (addButtons) {
				addRecipeButtons();
				addPageButtons();
			}
			addButtons = false;

			if (addCraftButton)
				addCraftingButton();
			addCraftButton = false;

			// Render the info about the craft
			if (craftingButtonPressed) {
				g.drawImage(craftableItem.getTexture(), (int) (800 * c.getScaleValue()),
						(int) (170 * c.getScaleValue()), (int) (150 * c.getScaleValue()),
						(int) (150 * c.getScaleValue()), null);

				// Name of craftable item
				CustomTextWritter.drawString(g, craftableItemName, (int) (870 * c.getScaleValue()),
						(int) (400 * c.getScaleValue()), active, Color.WHITE, Assets.font16);

				// Weight of craftable item
				CustomTextWritter.drawString(g, "Weight: " + craftableItem.getWeight() + " lb",
						(int) (870 * c.getScaleValue()), (int) (440 * c.getScaleValue()), active, Color.WHITE,
						Assets.font16);

				// Requirements
				CustomTextWritter.drawString(g, "Requirements:", (int) (870 * c.getScaleValue()),
						(int) (480 * c.getScaleValue()), active, Color.WHITE, Assets.font16);

				int i = 0;
				for (Entry<Item, Integer> entry : recipeRequirements.entrySet()) {

					// Player.getPlayerData().getInventory().getItemCount(craftingIngredientList[a])
					// replaces craftingIngredientList[a].getCount()
					craftingRequirementsString = String.format("%d / %d %s", entry.getValue(),
							Player.getPlayerData().getInventory().getItemCount(entry.getKey()),
							entry.getKey().getName());
					CustomTextWritter.drawString(g, craftingRequirementsString, (int) (870 * c.getScaleValue()),
							(int) ((505 + i * 40) * c.getScaleValue()), active, Color.WHITE, Assets.font16);

					if (entry.getValue() > Player.getPlayerData().getInventory()
							.getItemCount(entry.getKey())) {
						CustomTextWritter.drawString(g, "NOT ENOUGH MATERIALS", (int) (870 * c.getScaleValue()),
								(int) (700 * c.getScaleValue()), active, Color.WHITE, Assets.font16);
					}

					i++;
				}

			}
			
			//CustomTextWritter.drawString(g, "Smelt Timer " + smeltTimer, (int) (670 * c.getScaleValue()),
					//(int) (750 * c.getScaleValue()), active, Color.WHITE, Assets.font16);
			
			//CustomTextWritter.drawString(g, "Last Smelt Time " + lastSmeltTimer, (int) (470 * c.getScaleValue()),
					//(int) (750 * c.getScaleValue()), active, Color.WHITE, Assets.font16);
			
			//Show time left for cooking
			if(!currentlyCooking.isEmpty()) {
				//CustomTextWritter.drawString(g, "Smelt Time " + (getSmeltTime() - getSmeltTimer()) / 1000, (int) (c.getWidth() / 2 * c.getScaleValue()),
						//(int) (700 * c.getScaleValue()), active, Color.WHITE, Assets.font16);
				
				g.setColor(Color.GREEN);
				g.fillRect((int) ((c.getWidth() / 2 - 100)  * c.getScaleValue()),(int) (750 * c.getScaleValue()), 
						(int) (((double)(cookTime - cookTimer) / (double) cookTime) * 200), 10);
				
				
				CustomTextWritter.drawString(g, "Cooking", (int) (150 * c.getScaleValue()),
						(int) (100 * c.getScaleValue()), active, Color.WHITE, Assets.font28);
				
				//show items being smelted
				int i = 0;
				for (Entry<Integer, Integer> entry : currentlyCooking.entrySet()) {

					g.drawImage(Item.items[entry.getKey()].getTexture(), (int) (100 * c.getScaleValue()),
							(int) ((170 + i * 100) * c.getScaleValue()), (int) (100 * c.getScaleValue()),
							(int) (100 * c.getScaleValue()), null);
					
					CustomTextWritter.drawString(g, String.format("%d", entry.getValue()), (int) (210 * c.getScaleValue()),
							(int) ((270 + i * 100) * c.getScaleValue()), active, Color.WHITE, Assets.font28);

					i++;
				}
			}
			
			if(!products.isEmpty()) {
				
				CustomTextWritter.drawString(g, "Products", (int) (1150 * c.getScaleValue()),
						(int) (100 * c.getScaleValue()), active, Color.WHITE, Assets.font28);
				
				//show items that are prepared and can be collected
				int i = 0;
				for (Entry<Integer, Integer> entry : products.entrySet()) {

					g.drawImage(Item.items[entry.getKey()].getTexture(), (int) (1100 * c.getScaleValue()),
							(int) ((170 + i * 100) * c.getScaleValue()), (int) (100 * c.getScaleValue()),
							(int) (100 * c.getScaleValue()), null);
					
					CustomTextWritter.drawString(g, String.format("%d", entry.getValue()), (int) (1210 * c.getScaleValue()),
							(int) ((270 + i * 100) * c.getScaleValue()), active, Color.WHITE, Assets.font28);

					i++;
				}
			}

			if (uiManager != null)
				uiManager.render(g);

		}

	}

	//adds items to be smelted
	public void startCooking(Item craftableItem, int craftCount) {
		
		if(currentlyCooking.isEmpty())
			lastCookTimer = System.currentTimeMillis();
		
		if(currentlyCooking.containsKey(craftableItem.getId())) {

			currentlyCooking.put(craftableItem.getId(), currentlyCooking.get(craftableItem.getId()) + craftCount);

		}else {

			currentlyCooking.put(craftableItem.getId(), craftCount);

		}

	}

	public void cook() {
		
		if(!currentlyCooking.isEmpty()) {

			setCookTimer(getCookTimer() + System.currentTimeMillis() - lastCookTimer);
			lastCookTimer = System.currentTimeMillis();

			if (getCookTimer() < getCookTime())
				return;

			for (Entry<Integer, Integer> entry : currentlyCooking.entrySet()) {

				if(products.containsKey(entry.getKey()))
					products.put(entry.getKey(), products.get(entry.getKey()) + 1);
				else
					products.put(entry.getKey(), 1);

				removeItem(entry.getKey());
				break;

			}

			setCookTimer(0); // cool down resets
		}

	}

	public void removeItem(int itemId) {

		if(currentlyCooking.containsKey(itemId)) {
			int itemAmount = currentlyCooking.get(itemId);
			currentlyCooking.put(itemId, itemAmount - 1);
			if(currentlyCooking.get(itemId) <= 0) {
				currentlyCooking.remove(itemId);
			}
		}

	}

	public void collectItems() {

		for (Entry<Integer, Integer> entry : products.entrySet()) {

			for(int i = 0; i < entry.getValue(); i++)
				Player.getPlayerData().getInventory().addItem(Item.items[entry.getKey()]);

		}
		
		products.clear();

	}

	public ControlCenter getC() {
		return c;
	}

	public void setC(ControlCenter c) {
		this.c = c;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * @return the smeltTime
	 */
	public long getCookTime() {
		return cookTime;
	}

	/**
	 * @param smeltTime the smeltTime to set
	 */
	public void setCookTime(long cookTime) {
		this.cookTime = cookTime;
	}

	/**
	 * @return the smeltTimer
	 */
	public long getCookTimer() {
		return cookTimer;
	}

	/**
	 * @param smeltTimer the smeltTimer to set
	 */
	public void setCookTimer(long cookTimer) {
		this.cookTimer = cookTimer;
	}

}
