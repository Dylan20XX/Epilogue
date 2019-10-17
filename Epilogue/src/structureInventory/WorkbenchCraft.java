
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

//THIS CLASS IS NO LONGER NEEDED
public class WorkbenchCraft {

	private boolean active;
	private ControlCenter c;

	private UIManager uiManager;

	private ArrayList<WorkbenchRecipe> recipeList = new ArrayList<WorkbenchRecipe>(); //Stores recipes that can be crafted

	private boolean addButtons;
	private boolean addCraftButton;

	private Item craftableItem; // Item to be crafted
	private String craftableItemName; // Name of item to be crafted
	public HashMap<Item, Integer> recipeRequirements = new HashMap<Item, Integer>(); //Stores item and amount needed for crafting

	private WorkbenchRecipe selectedRecipe;

	// crafting
	private String craftingRequirementsString;
	private boolean craftingButtonPressed = false;
	private int craftCount = 0;

	private int pageNum = 0;

	private boolean currentlyCrafting = false;
	private long lastCraftTimer, craftCooldown = 1000, craftTimer = 0;

	public WorkbenchCraft(ControlCenter c) {

		this.c = c;

		uiManager = new UIManager();

	}

	//This method fills the recipeList ArrayList with craftable recipes
	public void findCraftableRecipes() {

		/*for (WorkbenchRecipe i : WorkbenchRecipe.recipes){
			if(isRecipeCraftable(i))
				recipeList.add(i);
		}*/

	}

	//This method checks if a recipe can be crafted
	private boolean isRecipeCraftable(WorkbenchRecipe recipe) {

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

							if(!currentlyCrafting) {

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
							Player.getPlayerData().setWorkbenchActive(false);
							c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
							.setWorkbenchActive(false);

							// return the mouse manager to world generator
							c.getMouseManager().setUIManager(c.getMenuState().getWorldSelectState().getGameState()
									.getWorldGenerator().getUiManager());

							c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().setStructureWorkbenchCrafted(true);
							c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().setStructureNum(selectedRecipe.getId() - 700);
							c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().setStructureWorkbenchRecipe(selectedRecipe);

						}else {

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

		currentlyCrafting = false; 
		craftTimer = 0; // cool down resets
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

							if(!currentlyCrafting) {

								if((pageNum + 1) * 16 < recipeList.size())
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

		if (c.getKeyManager().keyJustPressed(KeyEvent.VK_E) && !currentlyCrafting) {
			active = false;
			Player.getPlayerData().setWorkbenchActive(false);
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.setWorkbenchActive(false);

			// return the mouse manager to world generator
			c.getMouseManager().setUIManager(c.getMenuState().getWorldSelectState().getGameState()
					.getWorldGenerator().getUiManager());

		}

		if(currentlyCrafting)
			craft();

	}

	public void render(Graphics g) {

		if (active) {
			g.drawImage(Assets.handCraft, c.getWidth() / 2 - Assets.handCraft.getWidth() * 4,
					c.getHeight() / 2 - Assets.handCraft.getHeight() * 4, Assets.handCraft.getWidth() * 8,
					Assets.handCraft.getHeight() * 8, null);
			
			CustomTextWritter.drawString(g, "Workbench", (int) (c.getWidth() / 2 * c.getScaleValue()),
					(int) (75 * c.getScaleValue()), active, Color.WHITE, Assets.font36);

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
						CustomTextWritter.drawString(g, "NOT ENOUGH MATERIALs", (int) (870 * c.getScaleValue()),
								(int) (700 * c.getScaleValue()), active, Color.WHITE, Assets.font16);
					}

					i++;
				}

			}
			
			//Show time left for crafting
			if(currentlyCrafting) {
				g.setColor(Color.GREEN);
				g.fillRect((int) (870 * c.getScaleValue()),(int) (750 * c.getScaleValue()), (int) (((double)(craftCooldown - craftTimer) / (double) craftCooldown) * 200), 10);
			}

			if (uiManager != null)
				uiManager.render(g);

		}
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}