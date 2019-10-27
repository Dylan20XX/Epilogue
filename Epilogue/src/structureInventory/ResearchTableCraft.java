package structureInventory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import alphaPackage.ControlCenter;
import creatures.Player;
import graphics.Assets;
import graphics.CustomTextWritter;
import inventory.Recipe;
import items.Armor;
import items.Food;
import items.Item;
import items.Ranged;
import items.Tool;
import items.Torch;
import items.WaterContainer;
import items.Weapon;
import structureInventory.SmithingTableRecipe;
import structureInventory.WorkbenchRecipe;
import tiles.Tile;
import utils.ClickListener;
import utils.ImageButton;
import utils.UIManager;

public class ResearchTableCraft {

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

	public ResearchTableCraft(ControlCenter c) {

		this.c = c;

		uiManager = new UIManager();

	}

	//This method fills the recipeList ArrayList with craftable recipes
	private void findCraftableRecipes() {

		for (Recipe i : Recipe.lockedRecipes) {
			if(!isRecipeLearned(i))
				recipeList.add(i);
		}
			

		//Add the recipes to different category arraylists
		for (Recipe i : recipeList) {
			if(i.getItem() instanceof Weapon || i.getItem() instanceof Ranged || i.getItem() instanceof Tool) {
				
				//make sure recipes aren't added twice
				boolean alreadyAdded = false;
				for(Recipe x: weaponRecipeList) {
					if(i.getId() == x.getId())
						alreadyAdded = true;
				}
				
				if(!alreadyAdded)
					weaponRecipeList.add(i);
				
			} else if(i.getItem() instanceof Armor) {
				
				boolean alreadyAdded = false;
				for(Recipe x: armorRecipeList) {
					if(i.getId() == x.getId())
						alreadyAdded = true;
				}
				
				if(!alreadyAdded)
					armorRecipeList.add(i);
				
			} else if(i.getItem() instanceof Food) {
				
				boolean alreadyAdded = false;
				for(Recipe x: foodRecipeList) {
					if(i.getId() == x.getId())
						alreadyAdded = true;
				}
				
				if(!alreadyAdded)
					foodRecipeList.add(i);
				
			} else if(i.isStructure()) { //i.getItem().getType().equals("structure") || i.getItem().getType().equals("platform")
				
				boolean alreadyAdded = false;
				for(Recipe x: structureRecipeList) {
					if(i.getId() == x.getId())
						alreadyAdded = true;
				}
				
				if(!alreadyAdded)
					structureRecipeList.add(i);
				
			} else { //else if(i.getItem().getType().equals("miscellaneous")) & holds water containers
				
				boolean alreadyAdded = false;
				for(Recipe x: materialRecipeList) {
					if(i.getId() == x.getId())
						alreadyAdded = true;
				}
				
				if(!alreadyAdded)
					materialRecipeList.add(i);
				
			}
			
		}
		
	}
	
	private boolean isRecipeLearned(Recipe i) {
		for(Recipe e : Recipe.unlockedRecipes) {
			if(i.equals(e))
				return true;
		}
		return false;
	}
	
	private boolean isRecipeLearnable(Recipe i) {
		
		if(Player.getPlayerData().getBasicSurvivalXP() >= i.getBasicSurvivalXP() &&
				Player.getPlayerData().getCombatXP() >= i.getCombatXP() &&
				Player.getPlayerData().getCookingXP() >= i.getCookingXP() &&
				Player.getPlayerData().getBuildingXP() >= i.getBuildingXP()) {
			return true;
		} else {
			return false;
		}
	}

	// This method adds image buttons for the recipes
	private void addRecipeButtons() {

		int numCraftable = 0; // used for positioning image buttons
		int row = 0; // for positioning multiple rows of image buttons
		
		for(int i = pageNum * 16; i < (pageNum + 1) * 16 && i < selectedArrayList().size(); i++) {

			int x = i;
			
			Item item = selectedArrayList().get(i).getItem();
			
			while(item == null) {
				selectedArrayList().remove(selectedArrayList().get(i));
				item = selectedArrayList().get(i).getItem();
			}
			
			if(item instanceof Armor) {
				Armor r = (Armor) item;
				
				uiManager.addObject(new ImageButton(
						c.getWidth() / 2 - Assets.handCraft.getWidth() * 4 + 50
						+ (numCraftable % 4) * (100 * ControlCenter.scaleValue),
						c.getHeight() / 2 - Assets.handCraft.getHeight() * 4 + 40
						+ row * (100* ControlCenter.scaleValue),
						(int) (75 * ControlCenter.scaleValue), (int) (75 * ControlCenter.scaleValue),
						r.getTexture(), new ClickListener() {

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
			} else if(item instanceof Weapon) {
				Weapon r = (Weapon) item;
				
				uiManager.addObject(new ImageButton(
						c.getWidth() / 2 - Assets.handCraft.getWidth() * 4 + 50
						+ (numCraftable % 4) * (100 * ControlCenter.scaleValue),
						c.getHeight() / 2 - Assets.handCraft.getHeight() * 4 + 40
						+ row * (100* ControlCenter.scaleValue),
						(int) (75 * ControlCenter.scaleValue), (int) (75 * ControlCenter.scaleValue),
						r.getTexture(), new ClickListener() {

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
			} else if(item instanceof Ranged) {
				Ranged r = (Ranged) item;
				
				uiManager.addObject(new ImageButton(
						c.getWidth() / 2 - Assets.handCraft.getWidth() * 4 + 50
						+ (numCraftable % 4) * (100 * ControlCenter.scaleValue),
						c.getHeight() / 2 - Assets.handCraft.getHeight() * 4 + 40
						+ row * (100* ControlCenter.scaleValue),
						(int) (75 * ControlCenter.scaleValue), (int) (75 * ControlCenter.scaleValue),
						r.getTexture(), new ClickListener() {

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
			} else if(item instanceof Tool) {
				Tool r = (Tool) item;
				
				uiManager.addObject(new ImageButton(
						c.getWidth() / 2 - Assets.handCraft.getWidth() * 4 + 50
						+ (numCraftable % 4) * (100 * ControlCenter.scaleValue),
						c.getHeight() / 2 - Assets.handCraft.getHeight() * 4 + 40
						+ row * (100* ControlCenter.scaleValue),
						(int) (75 * ControlCenter.scaleValue), (int) (75 * ControlCenter.scaleValue),
						r.getTexture(), new ClickListener() {

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
			} else if(item instanceof Food) {
				Food r = (Food) item;
				
				uiManager.addObject(new ImageButton(
						c.getWidth() / 2 - Assets.handCraft.getWidth() * 4 + 50
						+ (numCraftable % 4) * (100 * ControlCenter.scaleValue),
						c.getHeight() / 2 - Assets.handCraft.getHeight() * 4 + 40
						+ row * (100* ControlCenter.scaleValue),
						(int) (75 * ControlCenter.scaleValue), (int) (75 * ControlCenter.scaleValue),
						r.getTexture(), new ClickListener() {

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
			} else if(item instanceof WaterContainer) {
				WaterContainer r = (WaterContainer) item;
				
				uiManager.addObject(new ImageButton(
						c.getWidth() / 2 - Assets.handCraft.getWidth() * 4 + 50
						+ (numCraftable % 4) * (100 * ControlCenter.scaleValue),
						c.getHeight() / 2 - Assets.handCraft.getHeight() * 4 + 40
						+ row * (100* ControlCenter.scaleValue),
						(int) (75 * ControlCenter.scaleValue), (int) (75 * ControlCenter.scaleValue),
						r.getTexture(), new ClickListener() {

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
				
			}  else if(item instanceof WaterContainer) {
				Torch r = (Torch) item;
				
				uiManager.addObject(new ImageButton(
						c.getWidth() / 2 - Assets.handCraft.getWidth() * 4 + 50
						+ (numCraftable % 4) * (100 * ControlCenter.scaleValue),
						c.getHeight() / 2 - Assets.handCraft.getHeight() * 4 + 40
						+ row * (100* ControlCenter.scaleValue),
						(int) (75 * ControlCenter.scaleValue), (int) (75 * ControlCenter.scaleValue),
						r.getTexture(), new ClickListener() {

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
				
			}else {
				
				uiManager.addObject(new ImageButton(
						c.getWidth() / 2 - Assets.handCraft.getWidth() * 4 + 50
						+ (numCraftable % 4) * (100 * ControlCenter.scaleValue),
						c.getHeight() / 2 - Assets.handCraft.getHeight() * 4 + 40
						+ row * (100* ControlCenter.scaleValue),
						(int) (75 * ControlCenter.scaleValue), (int) (75 * ControlCenter.scaleValue),
						item.getTexture(), new ClickListener() {

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
				
			}
			
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

					if (isRecipeLearnable(selectedRecipe) && !isRecipeLearned(selectedRecipe)) {

							currentlyCrafting = true;
							lastCraftTimer = System.currentTimeMillis();
						

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

		//Add learned item to unlocked recipe list
		Recipe.lockedRecipes.remove(selectedRecipe);
		Recipe.unlockedRecipes.add(selectedRecipe);

		//Subtract XP used
		Player.getPlayerData().setBasicSurvivalXP(Player.getPlayerData().getBasicSurvivalXP() - selectedRecipe.getBasicSurvivalXP());
		Player.getPlayerData().setCombatXP(Player.getPlayerData().getCombatXP() - selectedRecipe.getCombatXP());
		Player.getPlayerData().setCookingXP(Player.getPlayerData().getCookingXP() - selectedRecipe.getCookingXP());
		Player.getPlayerData().setBuildingXP(Player.getPlayerData().getBuildingXP() - selectedRecipe.getBuildingXP());
		
		//reset so button can be removed
		craftableItem = null;
		craftingButtonPressed = false;

		uiManager.getObjects().clear();// Clear the objects from the uiManager

		recipeRequirements.clear();
		recipeList.clear();//Clear the recipe list

		materialRecipeList.clear();
		weaponRecipeList.clear();
		armorRecipeList.clear();
		foodRecipeList.clear();
		structureRecipeList.clear();
		
		addButtons = false;
		addCraftButton = false;
		findCraftableRecipes();
		active = true;
		addButtons = true;
		addCraftButton = true;
		
		if(pageNum > 0 && (pageNum - 1) * 16 + 16 + 1 > selectedArrayList().size())
			pageNum -= 1;
		//end of reset code
		
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
					(int) (75 * ControlCenter.scaleValue), (int) (45 * ControlCenter.scaleValue),
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
					(int) (75 * ControlCenter.scaleValue), (int) (45 * ControlCenter.scaleValue),
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
				(int) (Assets.handCraft.getHeight() * 8 / 5 * ControlCenter.scaleValue), 
				(int) (Assets.handCraft.getHeight() * 8 / 5 * ControlCenter.scaleValue),
				Assets.materialButton, new ClickListener() {

					@Override
					public void onClick() {
						
						if(!currentlyCrafting) {
							
							selectedCategory = "material";
							
							pageNum = 0;
							craftableItem = null;
							addButtons = true;
							addCraftButton = true;
							craftingButtonPressed = false;
							uiManager.getObjects().clear();
							recipeRequirements.clear();
							
						}

					}

				}));

		//Weapon
		uiManager.addObject(new ImageButton(
				c.getWidth() / 2 - Assets.handCraft.getWidth() * 4 - Assets.handCraft.getHeight() * 8 / 5,
				c.getHeight() / 2 - Assets.handCraft.getHeight() * 4 + Assets.handCraft.getHeight() * 8 / 5,
				(int) (Assets.handCraft.getHeight() * 8 / 5 * ControlCenter.scaleValue), 
				(int) (Assets.handCraft.getHeight() * 8 / 5 * ControlCenter.scaleValue),
				Assets.weaponButton, new ClickListener() {

					@Override
					public void onClick() {
						
						if(!currentlyCrafting) {
							
							selectedCategory = "weapon";
							
							pageNum = 0;
							craftableItem = null;
							addButtons = true;
							addCraftButton = true;
							craftingButtonPressed = false;
							uiManager.getObjects().clear();
							recipeRequirements.clear();
							
						}

					}

				}));

		//Armor
		uiManager.addObject(new ImageButton(
				c.getWidth() / 2 - Assets.handCraft.getWidth() * 4 - Assets.handCraft.getHeight() * 8 / 5,
				c.getHeight() / 2 - Assets.handCraft.getHeight() * 4 + Assets.handCraft.getHeight() * 8 / 5 * 2,
				(int) (Assets.handCraft.getHeight() * 8 / 5 * ControlCenter.scaleValue), 
				(int) (Assets.handCraft.getHeight() * 8 / 5 * ControlCenter.scaleValue),
				Assets.armorButton, new ClickListener() {

					@Override
					public void onClick() {
						
						if(!currentlyCrafting) {
							
							selectedCategory = "armor";
							
							pageNum = 0;
							craftableItem = null;
							addButtons = true;
							addCraftButton = true;
							craftingButtonPressed = false;
							uiManager.getObjects().clear();
							recipeRequirements.clear();
							
						}
						
					}

				}));

		//Food
		uiManager.addObject(new ImageButton(
				c.getWidth() / 2 - Assets.handCraft.getWidth() * 4 - Assets.handCraft.getHeight() * 8 / 5,
				c.getHeight() / 2 - Assets.handCraft.getHeight() * 4 + Assets.handCraft.getHeight() * 8 / 5 * 3,
				(int) (Assets.handCraft.getHeight() * 8 / 5 * ControlCenter.scaleValue), 
				(int) (Assets.handCraft.getHeight() * 8 / 5 * ControlCenter.scaleValue),
				Assets.foodButton, new ClickListener() {

					@Override
					public void onClick() {
						
						if(!currentlyCrafting) {
							
							selectedCategory = "food";

							pageNum = 0;
							craftableItem = null;
							addButtons = true;
							addCraftButton = true;
							craftingButtonPressed = false;
							uiManager.getObjects().clear();
							recipeRequirements.clear();
						
						}
						
					}

				}));

		//Structure
		uiManager.addObject(new ImageButton(
				c.getWidth() / 2 - Assets.handCraft.getWidth() * 4 - Assets.handCraft.getHeight() * 8 / 5,
				c.getHeight() / 2 - Assets.handCraft.getHeight() * 4 + Assets.handCraft.getHeight() * 8 / 5 * 4,
				(int) (Assets.handCraft.getHeight() * 8 / 5 * ControlCenter.scaleValue), 
				(int) (Assets.handCraft.getHeight() * 8 / 5 * ControlCenter.scaleValue),
				Assets.structureButton, new ClickListener() {

					@Override
					public void onClick() {
						
						if(!currentlyCrafting) {
							
							selectedCategory = "structure";
							
							pageNum = 0;
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

	public void tick() {

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

				// Name of craftable item
				CustomTextWritter.drawString(g, craftableItemName, (int) (870 * c.getScaleValue()),
						(int) (390 * c.getScaleValue()), active, Color.WHITE, Assets.font21);

				// Type of craftable item
				CustomTextWritter.drawString(g, "type: " + craftableItem.getType(), (int) (870 * c.getScaleValue()),
						(int) (415 * c.getScaleValue()), active, Color.WHITE, Assets.font16);
				// Weight of craftable item
				CustomTextWritter.drawString(g, "weight: " + Math.round(craftableItem.getWeight()) + " lb",
						(int) (870 * c.getScaleValue()), (int) (430 * c.getScaleValue()), active,
						Color.WHITE, Assets.font16);
				// Volume of craftable item
				CustomTextWritter.drawString(g, "volume: " + Math.round(craftableItem.getVolume()) + " cm^3",
						(int) (870 * c.getScaleValue()), (int) (445 * c.getScaleValue()), active,
						Color.WHITE, Assets.font16);
				
				if (craftableItem.getType() == "weapon") {
					
					Weapon weapon = (Weapon)craftableItem;
					
					CustomTextWritter.drawString(g, "base damage: " + weapon.getDamage(),
							(int) (870 * c.getScaleValue()), (int) (460 * c.getScaleValue()),
							active, Color.WHITE, Assets.font16);

					String aSpeed = "";
					
					if (weapon.getaSpeed() < 500)
						aSpeed = "super fast";
					else if (weapon.getaSpeed() < 1000)
						aSpeed = "fast";
					else if (weapon.getaSpeed() < 1500)
						aSpeed = "average";
					else if (weapon.getaSpeed() < 2000)
						aSpeed = "slow";
					else
						aSpeed = "super slow";
					
					CustomTextWritter.drawString(g, "speed: " + aSpeed,
							(int) (870 * c.getScaleValue()), (int) (475 * c.getScaleValue()),
							active, Color.WHITE, Assets.font16);
					
					CustomTextWritter.drawString(g, weapon.getEnhancement(),
							(int) (870 * c.getScaleValue()), (int) (490 * c.getScaleValue()),
							active, Color.WHITE, Assets.font16);

					
				} else if (craftableItem.getType() == "ranged") {
					
					Ranged ranged = (Ranged)craftableItem;
							
					String aSpeed = "";
					
					if (ranged.getaSpeed() < 500)
						aSpeed = "super fast";
					else if (ranged.getaSpeed() < 1000)
						aSpeed = "fast";
					else if (ranged.getaSpeed() < 1500)
						aSpeed = "average";
					else if (ranged.getaSpeed() < 2000)
						aSpeed = "slow";
					else
						aSpeed = "super slow";
					
					CustomTextWritter.drawString(g, "speed: " + aSpeed,
							(int) (870 * c.getScaleValue()), (int) (460 * c.getScaleValue()),
							active, Color.WHITE, Assets.font16);
					
					CustomTextWritter.drawString(g, "load speed: " + Math.round(ranged.reloadCooldown/1000) + "s",
							(int) (870 * c.getScaleValue()), (int) (475 * c.getScaleValue()),
							active, Color.WHITE, Assets.font16);
					
					CustomTextWritter.drawString(g, ranged.getEnhancement(),
							(int) (870 * c.getScaleValue()), (int) (490 * c.getScaleValue()),
							active, Color.WHITE, Assets.font16);
					
				} else if (craftableItem.getType() == "helmet" || craftableItem.getType() == "chest" || craftableItem.getType() == "leg"
						|| craftableItem.getType() == "boots") {
					
					Armor armor = (Armor) craftableItem;
					
					CustomTextWritter.drawString(g, "resistance: " + armor.getResistance(),
							(int) (870 * c.getScaleValue()), (int) (460 * c.getScaleValue()), active,
							Color.WHITE, Assets.font16);
					
					if(armor.getHealthRegen() != 0)
						CustomTextWritter.drawString(g, "health regen: " + armor.getHealthRegen(),
								(int) (870 * c.getScaleValue()), (int) (475 * c.getScaleValue()), active,
								Color.WHITE, Assets.font16);
					
					CustomTextWritter.drawString(g, armor.getEnhancement(),
							(int) (870 * c.getScaleValue()), (int) (490 * c.getScaleValue()),
							active, Color.WHITE, Assets.font16);
					
				} else if (craftableItem.getType() == "food") {
					
					Food food = (Food)(craftableItem);
					
					CustomTextWritter.drawString(g, "hunger restore: " + food.getHunger(),
							(int) (870 * c.getScaleValue()), (int) (460 * c.getScaleValue()),
							active, Color.WHITE, Assets.font16);
					CustomTextWritter.drawString(g, "thirst restore: " + food.getThirst(),
							(int) (870 * c.getScaleValue()), (int) (475 * c.getScaleValue()),
							active, Color.WHITE, Assets.font16);
					
				} else if (craftableItem.getType() == "axe" || craftableItem.getType() == "pickaxe") {
					
					Tool tool = (Tool)craftableItem;
					
					CustomTextWritter.drawString(g, "base damage: " + tool.getDamage(),
							(int) (870 * c.getScaleValue()), (int) (460 * c.getScaleValue()),
							active, Color.WHITE, Assets.font16);
					
					if(tool.getType().equals("axe"))
						CustomTextWritter.drawString(g, "axe power: x" + Math.round(tool.getPower()),
								(int) (870 * c.getScaleValue()), (int) (4750 * c.getScaleValue()),
								active, Color.WHITE, Assets.font16);
					else
						CustomTextWritter.drawString(g, "pickaxe power: x" + Math.round(tool.getPower()),
								(int) (870 * c.getScaleValue()), (int) (475 * c.getScaleValue()),
								active, Color.WHITE, Assets.font16);

					String aSpeed = "";
					
					if (tool.getaSpeed() < 500)
						aSpeed = "super fast";
					else if (tool.getaSpeed() < 1000)
						aSpeed = "fast";
					else if (tool.getaSpeed() < 1500)
						aSpeed = "average";
					else if (tool.getaSpeed() < 2000)
						aSpeed = "slow";
					else
						aSpeed = "super slow";
					
					CustomTextWritter.drawString(g, "speed: " + aSpeed,
							(int) (870 * c.getScaleValue()), (int) (490 * c.getScaleValue()),
							active, Color.WHITE, Assets.font16);
					
					CustomTextWritter.drawString(g, tool.getEnhancement(),
							(int) (870 * c.getScaleValue()), (int) (505 * c.getScaleValue()),
							active, Color.WHITE, Assets.font16);
					
				} else if (craftableItem.getType() == "food") {
					Food food = (Food)craftableItem;
					CustomTextWritter.drawString(g, "hunger restore: " + food.getHunger(),
							(int) (870 * c.getScaleValue()), (int) (460 * c.getScaleValue()), active,
							Color.WHITE, Assets.font16);
					CustomTextWritter.drawString(g, "thirst restore: " + food.getThirst(),
							(int) (870 * c.getScaleValue()), (int) (475 * c.getScaleValue()), active,
							Color.WHITE, Assets.font16);
				}
				
				// Requirements
				CustomTextWritter.drawString(g, "Requirements:", (int) (870 * c.getScaleValue()),
						(int) (520 * c.getScaleValue()), active, Color.WHITE, Assets.font16);
				
				if(selectedRecipe.getBasicSurvivalXP() != 0)
					CustomTextWritter.drawString(g, "Basic Survival XP: " + selectedRecipe.getBasicSurvivalXP(), (int) (870 * c.getScaleValue()),
							(int) (535 * c.getScaleValue()), active, Color.WHITE, Assets.font16);
				if(selectedRecipe.getCombatXP() != 0)
					CustomTextWritter.drawString(g, "Combat XP: " + selectedRecipe.getCombatXP(), (int) (870 * c.getScaleValue()),
							(int) (535 * c.getScaleValue()), active, Color.WHITE, Assets.font16);
				if(selectedRecipe.getCookingXP() != 0)
					CustomTextWritter.drawString(g, "Cooking XP: " + selectedRecipe.getCookingXP(), (int) (870 * c.getScaleValue()),
							(int) (535 * c.getScaleValue()), active, Color.WHITE, Assets.font16);
				if(selectedRecipe.getBuildingXP() != 0)
					CustomTextWritter.drawString(g, "Building XP: " + selectedRecipe.getBuildingXP(), (int) (870 * c.getScaleValue()),
							(int) (535 * c.getScaleValue()), active, Color.WHITE, Assets.font16);
				
				if(!isRecipeLearnable(selectedRecipe))
					CustomTextWritter.drawString(g, "Not Enough XP", (int) (870 * c.getScaleValue()),
							(int) (565 * c.getScaleValue()), active, Color.WHITE, Assets.font21);

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
						(int) (Assets.handCraft.getHeight() * 8 / 5 * ControlCenter.scaleValue),
						(int) (Assets.handCraft.getHeight() * 8 / 5 * ControlCenter.scaleValue), null);
			else if(selectedCategory.equals("weapon"))
				g.drawImage(Assets.weaponButton2, (int) (c.getWidth() / 2 - Assets.handCraft.getWidth() * 4 - Assets.handCraft.getHeight() * 8 / 5),
						(int) (c.getHeight() / 2 - Assets.handCraft.getHeight() * 4 + Assets.handCraft.getHeight() * 8 / 5), 
						(int) (Assets.handCraft.getHeight() * 8 / 5 * ControlCenter.scaleValue),
						(int) (Assets.handCraft.getHeight() * 8 / 5 * ControlCenter.scaleValue), null);
			else if(selectedCategory.equals("armor"))
				g.drawImage(Assets.armorButton2, (int) (c.getWidth() / 2 - Assets.handCraft.getWidth() * 4 - Assets.handCraft.getHeight() * 8 / 5),
						(int) (c.getHeight() / 2 - Assets.handCraft.getHeight() * 4 + Assets.handCraft.getHeight() * 8 / 5 * 2), 
						(int) (Assets.handCraft.getHeight() * 8 / 5 * ControlCenter.scaleValue),
						(int) (Assets.handCraft.getHeight() * 8 / 5 * ControlCenter.scaleValue), null);
			else if(selectedCategory.equals("food"))
				g.drawImage(Assets.foodButton2, (int) (c.getWidth() / 2 - Assets.handCraft.getWidth() * 4 - Assets.handCraft.getHeight() * 8 / 5),
						(int) (c.getHeight() / 2 - Assets.handCraft.getHeight() * 4 + Assets.handCraft.getHeight() * 8 / 5 * 3), 
						(int) (Assets.handCraft.getHeight() * 8 / 5 * ControlCenter.scaleValue),
						(int) (Assets.handCraft.getHeight() * 8 / 5 * ControlCenter.scaleValue), null);
			else if(selectedCategory.equals("structure"))
				g.drawImage(Assets.structureButton2, (int) (c.getWidth() / 2 - Assets.handCraft.getWidth() * 4 - Assets.handCraft.getHeight() * 8 / 5),
						(int) (c.getHeight() / 2 - Assets.handCraft.getHeight() * 4 + Assets.handCraft.getHeight() * 8 / 5 * 4), 
						(int) (Assets.handCraft.getHeight() * 8 / 5 * ControlCenter.scaleValue),
						(int) (Assets.handCraft.getHeight() * 8 / 5 * ControlCenter.scaleValue), null);
			
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
		
		Player.getPlayerData().setResearchTableActive(false);
		c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
		.setResearchTableActive(false);		
	}

	public void craftSetup() {
		
		addButtons = false;
		addCraftButton = false;
		findCraftableRecipes();
		active = true;
		addButtons = true;
		addCraftButton = true;

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
