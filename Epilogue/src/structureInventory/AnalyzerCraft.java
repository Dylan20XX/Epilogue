package structureInventory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import alphaPackage.ControlCenter;
import creatures.Player;
import graphics.Assets;
import graphics.CustomTextWritter;
import inventory.Inventory;
import inventory.Recipe;
import items.Item;
import utils.ClickListener;
import utils.ImageButton;
import utils.UIManager;

//THIS CLASS IS NO LONGER NEEDED
public class AnalyzerCraft {

	private ControlCenter c;
	private UIManager uiManager;
	private Player player;
	private boolean active = false;
	private int inventorySpacing = 30;
	private Inventory playerInventory;
	
	private Item selectedItem;
	
	private boolean analyzed = false;
	private Item analyzedItem;
	private String requiredStructure;
	private String craftingRequirementsString;
	public HashMap<Integer, Integer> recipeRequirements = new HashMap<Integer, Integer>();

	public AnalyzerCraft(ControlCenter c) {
		this.c = c;
		player = Player.getPlayerData();
		playerInventory = Player.getPlayerData().getInventory();
		
		uiManager = new UIManager();
	}

	public void tick() {

		if (c.getKeyManager().keyJustPressed(KeyEvent.VK_E)) {
			active = false;
			Player.getPlayerData().setAnalyzerActive(false); 
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
			.setAnalyzerActive(false);
			
			// return the mouse manager to world generator
			c.getMouseManager().setUIManager(c.getMenuState().getWorldSelectState().getGameState()
					.getWorldGenerator().getUiManager());
			
			uiManager.getObjects().clear();// Clear the objects from the uiManager

			recipeRequirements.clear();
		}

		if (!active) {
			return;
		}

		if (c.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
			playerInventory.selectedItem--;
		}

		if (c.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
			playerInventory.selectedItem++;
		}

		if (playerInventory.selectedItem < 0) {
			playerInventory.selectedItem = playerInventory.InventoryItems.size() - 1;
		}

		else if (playerInventory.selectedItem >= playerInventory.InventoryItems.size()) {
			playerInventory.selectedItem = 0;
		}

		if (playerInventory.getInventoryWeight() < 0) {
			playerInventory.setInventoryWeight(0);
		}

	}

	public void render(Graphics g) {
		
		if (!active) {
			return;
		}
		
		c.getMouseManager().setUIManager(uiManager);
		
		g.drawImage(Assets.handCraft, c.getWidth() / 2 - Assets.handCraft.getWidth() * 4,
				c.getHeight() / 2 - Assets.handCraft.getHeight() * 4, Assets.handCraft.getWidth() * 8,
				Assets.handCraft.getHeight() * 8, null);
		
		CustomTextWritter.drawString(g, "Analyzer", (int) (c.getWidth() / 2 * c.getScaleValue()),
				(int) (75 * c.getScaleValue()), active, Color.WHITE, Assets.font36);

		// Render the Player inventory (playerInventory)
		int playerInLen = playerInventory.InventoryItems.size();
		if (playerInLen == 0)
			return;

		for (int i = -5; i < 6; i++) {
			if (playerInventory.selectedItem + i < 0 || playerInventory.selectedItem + i >= playerInLen)
				continue;
			if (i == 0) {
				CustomTextWritter.drawString(g,
						"> " + playerInventory.InventoryItems.get(playerInventory.selectedItem + i).getName()
						+ " <",
						(int) (312 * c.getScaleValue()), (int) ((315 + i * inventorySpacing) * c.getScaleValue()), true,
						Color.YELLOW, Assets.font28);

			} else {
				CustomTextWritter.drawString(g,
						playerInventory.InventoryItems.get(playerInventory.selectedItem + i).getName(),
						(int) (312 * c.getScaleValue()), (int) ((315 + i * inventorySpacing) * c.getScaleValue()), true,
						Color.WHITE, Assets.font28);
			}
		}
		
		if(analyzed) {
			
			//Render Item Info
			g.drawImage(analyzedItem.getTexture(), (int) (800 * c.getScaleValue()),
					(int) (170 * c.getScaleValue()), (int) (150 * c.getScaleValue()),
					(int) (150 * c.getScaleValue()), null);
			
			//Name of analyzed item
			CustomTextWritter.drawString(g, analyzedItem.getName(), (int) (870 * c.getScaleValue()),
					(int) (400 * c.getScaleValue()), active, Color.WHITE, Assets.font16);
			
			// Weight of craftable item
			CustomTextWritter.drawString(g, "Weight: " + analyzedItem.getWeight() + " lb",
					(int) (870 * c.getScaleValue()), (int) (440 * c.getScaleValue()), active, Color.WHITE,
					Assets.font16);

			
			if(recipeRequirements.isEmpty()) {
				
				// Requirements
				CustomTextWritter.drawString(g, "No Recipe", (int) (870 * c.getScaleValue()),
						(int) (480 * c.getScaleValue()), active, Color.WHITE, Assets.font16);
				
			}else {
				
				// Requirements
				CustomTextWritter.drawString(g, "Requirements:", (int) (870 * c.getScaleValue()),
						(int) (480 * c.getScaleValue()), active, Color.WHITE, Assets.font16);
				
				int i = 0;
				for (Entry<Integer, Integer> entry : recipeRequirements.entrySet()) {

					// Player.getPlayerData().getInventory().getItemCount(craftingIngredientList[a])
					// replaces craftingIngredientList[a].getCount()
					craftingRequirementsString = String.format("%d / %d %s", entry.getValue(),
							Player.getPlayerData().getInventory().getItemCount(Item.items[entry.getKey()]),
							Item.items[entry.getKey()].getName());
					CustomTextWritter.drawString(g, craftingRequirementsString, (int) (870 * c.getScaleValue()),
							(int) ((505 + i * 40) * c.getScaleValue()), active, Color.WHITE, Assets.font16);


					i++;
				}
				
				CustomTextWritter.drawString(g, requiredStructure, (int) (870 * c.getScaleValue()),
						(int) ((505 + i * 40) * c.getScaleValue()), active, Color.WHITE, Assets.font16);
			}
			
		}else if(!playerInventory.InventoryItems.isEmpty() && selectedItem != null){
			
			//Render Item Info
			g.drawImage(selectedItem.getTexture(), (int) (800 * c.getScaleValue()),
					(int) (170 * c.getScaleValue()), (int) (150 * c.getScaleValue()),
					(int) (150 * c.getScaleValue()), null);
			
			//Name of analyzed item
			CustomTextWritter.drawString(g, selectedItem.getName(), (int) (870 * c.getScaleValue()),
					(int) (400 * c.getScaleValue()), active, Color.WHITE, Assets.font16);
			
			// Weight of craftable item
			CustomTextWritter.drawString(g, "Weight: " + selectedItem.getWeight() + " lb",
					(int) (870 * c.getScaleValue()), (int) (440 * c.getScaleValue()), active, Color.WHITE,
					Assets.font16);
			
			//Show cost to analyze
			CustomTextWritter.drawString(g, "Analysis Cost:", (int) (870 * c.getScaleValue()),
					(int) (480 * c.getScaleValue()), active, Color.WHITE, Assets.font16);
			CustomTextWritter.drawString(g, String.format("1 / %d %s",selectedItem.getCount(), selectedItem.getName()), (int) (870 * c.getScaleValue()),
					(int) (505 * c.getScaleValue()), active, Color.WHITE, Assets.font16);
			
		}
		
		if (uiManager != null)
			uiManager.render(g);
	}
	
	public void craftSetup() {
		addAnalysisButtons();
		
		selectedItem = null;
		
		analyzed = false;
		analyzedItem = null;
		requiredStructure = "";
		craftingRequirementsString = "";
		recipeRequirements.clear();
	}
	
	private void addAnalysisButtons() {
		
		//This button selects an item to be analyzed
		uiManager.addObject(new ImageButton(c.getWidth() / 2 - Assets.handCraft.getWidth() * 4 + 550,
				c.getHeight() / 2 - Assets.handCraft.getHeight() * 4 + 490, (int) (75 * ControlCenter.scaleValue),
				(int) (75 * ControlCenter.scaleValue), Assets.equipmentButton, new ClickListener() {

			@Override
			public void onClick() {
				
				if(!playerInventory.InventoryItems.isEmpty()) {
					analyzed = false;
					selectedItem = playerInventory.InventoryItems.get(playerInventory.selectedItem);
				}
				
			}

		}));
		
		//This button performs analysis
		uiManager.addObject(new ImageButton(c.getWidth() / 2 - Assets.handCraft.getWidth() * 4 + 650,
				c.getHeight() / 2 - Assets.handCraft.getHeight() * 4 + 490, (int) (75 * ControlCenter.scaleValue),
				(int) (75 * ControlCenter.scaleValue), Assets.equipmentButton, new ClickListener() {

			@Override
			public void onClick() {
				
				//analyze(playerInventory.InventoryItems.get(playerInventory.selectedItem));
				if(analyzed == false && selectedItem != null)
					analyze(selectedItem);
				
			}

		}));

	}
	
	private void analyze(Item item) {
		
		analyzedItem = item;
		analyzed = true;
		recipeRequirements.clear();
		requiredStructure = "";
		
		boolean recipeFound = false;
		
		for(Recipe i: Recipe.recipes) {
			
			if(i.getItem().getId() == item.getId()) {
				recipeRequirements.putAll(i.recipeRequirements);
				requiredStructure = "Hand Craft Recipe";
				recipeFound = true;
				break;
			}
			
		}
		
		if(recipeFound) {
			playerInventory.removeItem(playerInventory.InventoryItems.get(playerInventory.selectedItem));
			return;
		}

		/*
		for(WorkbenchRecipe i: WorkbenchRecipe.recipes) {
			
			if(i.getItem().getId() == item.getId()) {
				recipeRequirements.putAll(i.recipeRequirements);
				requiredStructure = "Workbench Recipe";
				recipeFound = true;
				break;
			}
			
		}
		*/
		if(recipeFound) {
			playerInventory.removeItem(playerInventory.InventoryItems.get(playerInventory.selectedItem));
			return;
		}
		/*
		for(SmithingTableRecipe i: SmithingTableRecipe.recipes) {
			
			if(i.getItem().getId() == item.getId()) {
				recipeRequirements.putAll(i.recipeRequirements);
				requiredStructure = "Smithing Table Recipe";
				recipeFound = true;
				break;
			}
			
		}
		*/
		if(recipeFound) {
			playerInventory.removeItem(playerInventory.InventoryItems.get(playerInventory.selectedItem));
			return;
		}
		
		for(PurifierRecipe i: PurifierRecipe.recipes) {
			
			if(i.getItem().getId() == item.getId()) {
				recipeRequirements.putAll(i.recipeRequirements);
				requiredStructure = "Purifier Recipe";
				recipeFound = true;
				break;
			}
			
		}
		
		if(recipeFound) {
			playerInventory.removeItem(playerInventory.InventoryItems.get(playerInventory.selectedItem));
			return;
		}
		
		for(MetallicOvenRecipe i: MetallicOvenRecipe.recipes) {
			
			if(i.getItem().getId() == item.getId()) {
				recipeRequirements.putAll(i.recipeRequirements);
				requiredStructure = "Metallic Oven Recipe";
				recipeFound = true;
				break;
			}
			
		}
		
		if(recipeFound) {
			playerInventory.removeItem(playerInventory.InventoryItems.get(playerInventory.selectedItem));
			return;
		}
		
		for(AutoCookerRecipe i: AutoCookerRecipe.recipes) {
			
			if(i.getItem().getId() == item.getId()) {
				recipeRequirements.putAll(i.recipeRequirements);
				requiredStructure = "Auto Cooker Recipe";
				recipeFound = true;
				break;
			}
			
		}
		
		if(recipeFound) {
			playerInventory.removeItem(playerInventory.InventoryItems.get(playerInventory.selectedItem));
			return;
		}
		
		for(DisintegratorRecipe i: DisintegratorRecipe.recipes) {
			
			if(i.getItem().getId() == item.getId()) {
				recipeRequirements.putAll(i.recipeRequirements);
				requiredStructure = "Disintegrator Recipe";
				recipeFound = true;
				break;
			}
			
		}
		
		if(recipeFound) {
			playerInventory.removeItem(playerInventory.InventoryItems.get(playerInventory.selectedItem));
			return;
		}
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
	
	public boolean isAnalyzed() {
		return analyzed;
	}
	
	public void setAnalyzed(boolean analyzed) {
		this.analyzed = analyzed;
	}

}
