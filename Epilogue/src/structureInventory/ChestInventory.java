package structureInventory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import alphaPackage.ControlCenter;
import creatures.Player;
import graphics.Assets;
import graphics.CT;
import graphics.CustomTextWritter;
import inventory.Inventory;
import items.Armor;
import items.Food;
import items.Item;
import items.Ranged;
import items.Tool;
import items.Weapon;

public class ChestInventory {

	private ControlCenter c;
	private Player player;
	private boolean active = false;
	public HashMap<Item, Integer> InventoryItems;
	public ArrayList<Item> InventoryItemsList;
	public int selectedItem = 0;
	private int inventorySpacing = 30;
	private double inventoryWeight = 0;
	private Inventory playerInventory;
	private int activeInventory = 1; // Determines if player or chest inventory is controlled
	private double inventoryVolume = 0;
	private double inventoryCapacity;
	private boolean volumeExceed = false;
	private boolean isWild = true;
	
	public ChestInventory(ControlCenter c, double chestCapacity) {
		this.c = c;
		inventoryCapacity = chestCapacity;
		InventoryItems = new HashMap<Item, Integer>();
		InventoryItemsList = new ArrayList<Item>();
		player = Player.getPlayerData();
		playerInventory = Player.getPlayerData().getInventory();
		
		if(isWild) {
			addTreasure();
		}

	}
	
	public void addTreasure() {
		
		int numItems = CT.random(2, 6);
		
		for(int i = 0; i < numItems; i++) {
			
			int special = CT.random(1, 25);
			
			if(special == 1 || special == 2) { // is weapon
				
				int id = CT.random(0, 100);
				 
				if(id <= 1) {
					int rand = CT.random(1, 5);
					if(rand == 1)
						Weapon.titaniumClaws.createNew(this);
					if(rand == 1)
						Weapon.tungstenMace.createNew(this);
					else 
						Weapon.giantSawBlade.createNew(this);
					
				} else if(id <= 4) 
					Weapon.SteelSword.createNew(this);
				else if(id <= 8) 
					Weapon.ironSword.createNew(this);
				else if(id <= 18)
					Weapon.katana.createNew(this);
				else if(id < 30)
					Weapon.zincBlade.createNew(this);
				else if(id < 45)
					Weapon.bronzeBlade.createNew(this);
				else if(id < 55)
					Weapon.spikeyClub.createNew(this);
				else if(id < 70)
					Weapon.stoneClub.createNew(this);
				else if(id < 85)
					Weapon.woodenClub.createNew(this);
				else 
					Weapon.batWeapon.createNew(this);
				
			} else if(special == 3) { // is armor
				
				int id = CT.random(0, 100);
				
				if(id < 3) {
					int rand = CT.random(1, 5);
					if(rand == 1)
						Armor.titaniumHelmet.createNew(this);
					else if(rand == 2)
						Armor.titaniumChest.createNew(this);
					else if(rand == 3)
						Armor.titaniumLegging.createNew(this);
					else if(rand == 4)
						Armor.titaniumBoots.createNew(this);
					else
						Armor.titaniumGauntlets.createNew(this);
					
				} else if(id < 6) {
					int rand = CT.random(1, 5);
					if(rand == 1)
						Armor.tungstenHelmet.createNew(this);
					else if(rand == 2)
						Armor.tungstenChest.createNew(this);
					else if(rand == 3)
						Armor.tungstenLegging.createNew(this);
					else if(rand == 4)
						Armor.tungstenBoots.createNew(this);
					else
						Armor.tungstenGauntlets.createNew(this);
				} else if(id < 20) {
					int rand = CT.random(1, 5);
					if(rand == 1)
						Armor.ironHelmet.createNew(this);
					else if(rand == 2)
						Armor.ironChest.createNew(this);
					else if(rand == 3)
						Armor.ironLegging.createNew(this);
					else if(rand == 4)
						Armor.ironBoots.createNew(this);
					else
						Armor.ironGauntlets.createNew(this);
				} else if(id < 50) {
					int rand = CT.random(1, 5);
					if(rand == 1)
						Armor.zincHelmet.createNew(this);
					else if(rand == 2)
						Armor.zincChest.createNew(this);
					else if(rand == 3)
						Armor.zincLegging.createNew(this);
					else if(rand == 4)
						Armor.zincBoots.createNew(this);
					else
						Armor.zincGauntlets.createNew(this);
				} else {
					int rand = CT.random(1, 5);
					if(rand == 1)
						Armor.bronzeHelmet.createNew(this);
					else if(rand == 2)
						Armor.bronzeChest.createNew(this);
					else if(rand == 3)
						Armor.bronzeLegging.createNew(this);
					else if(rand == 4)
						Armor.bronzeBoots.createNew(this);
					else
						Armor.bronzeGauntlets.createNew(this);
				}
				
			} else if(special == 4 || special == 5) { // is tool
				
				int id = CT.random(0, 100);
				
				if(id < 1) 
					Tool.drillTool.createNew(this);
				else if(id < 2) 
					Tool.chainsawTool.createNew(this);
				else if(id <= 10) 
					Tool.zincAxeTool.createNew(this);
				else if(id <= 18) 
					Tool.zincPickaxeTool.createNew(this);
				else if(id <= 25) 
					Tool.bronzeAxeTool.createNew(this);
				else if(id <= 34) 
					Tool.bronzePickaxeTool.createNew(this);
				else if (id <= 47) 
					Tool.woodenAxeTool.createNew(this);
				else if(id <= 60) 
					Tool.woodenPickaxeTool.createNew(this);
				else if(id <= 85) 
					Tool.stoneAxeTool.createNew(this);
				else if(id <= 100) 
					Tool.stonePickaxeTool.createNew(this);
				
				
			} else if(special <= 7) {
				
				Item.blueprintItem.createNew(this, 1);
				
			} else { // is item
			
				int max = 0;
				for(int j = 0; j < Item.items.length; j++) {
					if(Item.items[j] == null) {
						max = j-1;
						break;
					}
				}
				
				int id = CT.random(0, max);
				int amt = CT.random(0, 5);
				
				if(id == 17 || id == 18) {
					int secondChance = CT.random(1, 2);
					if(secondChance == 1 || secondChance == 2) {
						id = 0;
					}
				} else if(id == 25)
					amt = CT.random(5, 50);
				else if(id == 24) 
					amt = CT.random(3, 10);
				
				for(int j = 0; j < Item.items.length; j++) {
					if(j == id) {
						Item.items[j].createNew(this, amt);
					}
				}
				
			}
			
			if(CT.random(0, 200) == 1) {
				Ranged.megashakalaka.createNew(this);
			}
			if(CT.random(0, 50) == 1) {
				Ranged.glock.createNew(this);
			}
			if(CT.random(0, 75) == 1) {
				Ranged.desertEagle.createNew(this);
			}
			if(CT.random(0, 30) == 1) {
				Ranged.woodenBow.createNew(this);
			}
			if(CT.random(0, 45) == 1) {
				Ranged.bronzeBow.createNew(this);
			}
			if(CT.random(0, 75) == 1) {
				Ranged.zincBow.createNew(this);
			}
			if(CT.random(0, 10) == 1) {
				Food.rotItem.createNew(this);
			}
			
		}
		
	}

	public void tick() {

		if (c.getKeyManager().keyJustPressed(KeyEvent.VK_E)) {
			active = false;
			Player.getPlayerData().setChestActive(false);
			c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
					.setChestActive(false);
		}
		
		checkFood();

		if (!active) {
			return;
		}

		if (c.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
			if (activeInventory == 0) {
				playerInventory.selectedItem--;
			} else if (activeInventory == 1) {
				selectedItem--;
			}
		}
		if (c.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
			if (activeInventory == 0) {
				playerInventory.selectedItem++;
			} else if (activeInventory == 1) {
				selectedItem++;
			}
		}

		if (c.getKeyManager().keyJustPressed(KeyEvent.VK_A)) {
			activeInventory = 0;
		}

		if (c.getKeyManager().keyJustPressed(KeyEvent.VK_D)) {
			activeInventory = 1;
		}

		// Move item from chest to player inventory when Enter key is pressed
		// Shift + Enter only removes one item
		if (c.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)) {

			if (activeInventory == 0 && !playerInventory.InventoryItems.isEmpty()) {

				Item item = playerInventory.InventoryItems.get(playerInventory.selectedItem);
				int inventoryAmount = item.getCount();

				if (c.getKeyManager().shift || !item.isStackable()) {
					if(addItem(item)) {
						playerInventory.setInventoryWeight(playerInventory.getInventoryWeight() - item.getWeight());
						playerInventory.setInventoryVolume(playerInventory.getInventoryVolume() - item.getVolume());
						item.setCount(item.getCount() - 1);
	
						if (item.getCount() <= 0)
							playerInventory.InventoryItems.remove(playerInventory.selectedItem);

					}

				} else {

					for (int i = 0; i < inventoryAmount; i++) {
						if(addItem(item)) {
							playerInventory.setInventoryWeight(playerInventory.getInventoryWeight() - item.getWeight());
							playerInventory.setInventoryVolume(playerInventory.getInventoryVolume() - item.getVolume());
							item.setCount(item.getCount() - 1);
	
							if (item.getCount() <= 0)
								playerInventory.InventoryItems.remove(playerInventory.selectedItem);
						}
						
					}

				}

			} else if (activeInventory == 1 && !InventoryItemsList.isEmpty()) {

				Item item = InventoryItemsList.get(selectedItem);
				int chestAmount = InventoryItems.get(item);

				if (c.getKeyManager().shift || !item.isStackable()) {
					if(playerInventory.addItem(item))
						removeItem(item);

				} else {

					for (int i = 0; i < chestAmount; i++) {
						if(playerInventory.addItem(item))
							removeItem(item);
					}

				}

			}

		}

		if (selectedItem < 0) {
			selectedItem = InventoryItemsList.size() - 1;
		}

		else if (selectedItem >= InventoryItemsList.size()) {
			selectedItem = 0;
		}

		if (playerInventory.selectedItem < 0) {
			playerInventory.selectedItem = playerInventory.InventoryItems.size() - 1;
		}

		else if (playerInventory.selectedItem >= playerInventory.InventoryItems.size()) {
			playerInventory.selectedItem = 0;
		}

		if (inventoryWeight < 0) {
			inventoryWeight = 0;
		}

		if (playerInventory.getInventoryWeight() < 0) {
			playerInventory.setInventoryWeight(0);
		}

		if (inventoryWeight != 0) {
			if (c.getKeyManager().keyJustPressed(KeyEvent.VK_Q)) {

				if (activeInventory == 0  && !playerInventory.InventoryItems.isEmpty()) {
					Item item = playerInventory.InventoryItems.get(playerInventory.selectedItem);
					playerInventory.setInventoryWeight(playerInventory.getInventoryWeight() - item.getWeight());
					item.setCount(item.getCount() - 1);

					c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
							.getItemManager().addItem(item.createNew((int) player.getX() + player.getWidth() / 4,
									(int) player.getY() + player.getHeight() / 2));

					if (item.getCount() <= 0) {
						playerInventory.InventoryItems.remove(selectedItem);
						if (playerInventory.selectedItem > 0)
							playerInventory.selectedItem--;
					}
				} else if (activeInventory == 1 && !InventoryItemsList.isEmpty()) {
					Item item = null;

					int counter = 0;
					for (Entry<Item, Integer> entry : InventoryItems.entrySet()) {
						if (counter == selectedItem)
							item = entry.getKey();
						counter++;
					}

					inventoryWeight -= item.getWeight();
					inventoryVolume -= item.getVolume();
					InventoryItems.put(item, InventoryItems.get(item) - 1);

					c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
							.getItemManager().addItem(item.createNew((int) player.getX() + player.getWidth() / 4,
									(int) player.getY() + player.getHeight() / 2));

					if (InventoryItems.get(item) <= 0) {
						InventoryItems.remove(item);
						InventoryItemsList.remove(selectedItem);
						if (selectedItem > 0)
							selectedItem--;
					}
				}

			}
		}

	}

	public void render(Graphics g) {
		if (!active) {
			return;
		}

		// Render the chest interface
		g.drawImage(Assets.chestInterface, c.getWidth() / 2 - Assets.chestInterface.getWidth() * 4,
				c.getHeight() / 2 - Assets.chestInterface.getHeight() * 4, Assets.chestInterface.getWidth() * 8,
				(int) Assets.chestInterface.getHeight() * 8, null);
		
		CustomTextWritter.drawString(g, "Chest", (int) (c.getWidth() / 2 * c.getScaleValue()),
				(int) (75 * c.getScaleValue()), active, Color.WHITE, Assets.font36);

		CustomTextWritter.drawString(g, "Player Inventory", (int) (c.getWidth() / 4 * c.getScaleValue()),
				(int) (85 * c.getScaleValue()), active, Color.WHITE, Assets.font28);
		
		CustomTextWritter.drawString(g, "Chest Inventory", (int) (c.getWidth() / 4 * 3 * c.getScaleValue()),
				(int) (85 * c.getScaleValue()), active, Color.WHITE, Assets.font28);
		
		CustomTextWritter.drawString(g, "W",
				(int) (304 * c.getScaleValue()), (int) (160 * c.getScaleValue()), active, Color.WHITE,
				Assets.font28);
		
		CustomTextWritter.drawString(g, "S",
				(int) (305 * c.getScaleValue()), (int) (470 * c.getScaleValue()), active, Color.WHITE,
				Assets.font28);
		CustomTextWritter.drawString(g, "W",
				(int) ((1280-305) * c.getScaleValue()), (int) (160 * c.getScaleValue()), active, Color.WHITE,
				Assets.font28);
		
		CustomTextWritter.drawString(g, "S",
				(int) ((1280-304) * c.getScaleValue()), (int) (470 * c.getScaleValue()), active, Color.WHITE,
				Assets.font28);
		
		CustomTextWritter.drawString(g, "A",
				(int) (484 * c.getScaleValue()), (int) (405 * c.getScaleValue()), active, Color.WHITE,
				Assets.font36);
		
		CustomTextWritter.drawString(g, "D",
				(int) ((1280-484) * c.getScaleValue()), (int) (405 * c.getScaleValue()), active, Color.WHITE,
				Assets.font36);
		
		if(volumeExceed) {
			CustomTextWritter.drawString(g, "Inventory Volume Reached", (int) (c.getWidth() / 2 * c.getScaleValue()), (int) (c.getHeight() / 2 * c.getScaleValue()), active,
					Color.WHITE, Assets.font21);
			volumeExceed = false;
		}
		
		//render Chest Inventory
		CustomTextWritter.drawString(g, "Total Weight: " + Math.round(inventoryWeight * 10) / 10 + " kg",
				(int) ((1280-305) * c.getScaleValue()), (int) (530 * c.getScaleValue()), active, Color.WHITE,
				Assets.font16);
		CustomTextWritter.drawString(g, "Total Volume: " + Math.round(inventoryVolume) + " cm^3",
				(int) ((1280-305) * c.getScaleValue()), (int) (555 * c.getScaleValue()), active, Color.WHITE,
				Assets.font16);
		CustomTextWritter.drawString(g, "Max Volume: " + Math.round(inventoryCapacity) + " cm^3",
				(int) ((1280-305) * c.getScaleValue()), (int) (580 * c.getScaleValue()), active, Color.WHITE,
				Assets.font16);
		
		int inlen = InventoryItemsList.size();
		if (inlen != 0) {
			for (int i = -4; i < 5; i++) {
				if (selectedItem + i < 0 || selectedItem + i >= inlen)
					continue;

				if (i == 0) {
					if (activeInventory == 0) {
						CustomTextWritter.drawString(g, InventoryItemsList.get(selectedItem + i).getName(),
								(int) (1280 - 305 * c.getScaleValue()),
								(int) ((315 + i * inventorySpacing) * c.getScaleValue()), true, Color.WHITE,
								Assets.font28);
					} else if (activeInventory == 1) {
						CustomTextWritter.drawString(g,
								"> " + InventoryItemsList.get(selectedItem + i).getName() + " <",
								(int) (1280 - 305 * c.getScaleValue()),
								(int) ((315 + i * inventorySpacing) * c.getScaleValue()), true, Color.YELLOW,
								Assets.font28);
					}
				} else {
					CustomTextWritter.drawString(g, InventoryItemsList.get(selectedItem + i).getName(),
							(int) (1280 - 305 * c.getScaleValue()),
							(int) ((315 + i * inventorySpacing) * c.getScaleValue()), true, Color.WHITE, Assets.font28);
				}
			}
			if (activeInventory == 1 && inventoryWeight != 0) {

				Item item = InventoryItemsList.get(selectedItem);
				
				g.drawImage(item.getTexture(), (int) ((590) * c.getScaleValue()),
						(int) (197 * c.getScaleValue()), (int) (104 * c.getScaleValue()),
						(int) (104 * c.getScaleValue()), null);
				if (item.isStackable() && InventoryItems.get(item) != null)
					CustomTextWritter.drawString(g, Integer.toString(InventoryItems.get(item)),
							c.getWidth() / 2, (int) (400 * c.getScaleValue()), true, Color.WHITE, Assets.font28);
				else
					CustomTextWritter.drawString(g, "~", c.getWidth() / 2, (int) (400 * c.getScaleValue()), true,
							Color.WHITE, Assets.font28);

				CustomTextWritter.drawString(g, "type: " + item.getType(),
						(int) ((c.getWidth() / 2) * c.getScaleValue()), (int) (470 * c.getScaleValue()), active,
						Color.WHITE, Assets.font21);

				CustomTextWritter.drawString(g, "weight: " + Math.round(item.getWeight()) + " lb",
						(int) ((c.getWidth() / 2) * c.getScaleValue()), (int) (500 * c.getScaleValue()), active,
						Color.WHITE, Assets.font21);
				CustomTextWritter.drawString(g, "volume: " + Math.round(item.getVolume()) + " cm^3",
						(int) ((c.getWidth() / 2) * c.getScaleValue()), (int) (530 * c.getScaleValue()), active,
						Color.WHITE, Assets.font21);
				
				if(item.getType() != "pickaxe" && item.getType() != "axe") {
					CustomTextWritter.drawString(g, "Take All: <Enter>",
							(int)(c.getWidth() / 2 * c.getScaleValue()), (int)(630 * c.getScaleValue()), active, 
							Color.WHITE, Assets.font16);
					
					CustomTextWritter.drawString(g, "Take 1: <Shift> + <Enter>", 
							(int)(c.getWidth() / 2 * c.getScaleValue()), (int)(645 * c.getScaleValue()), active, 
							Color.WHITE, Assets.font16);
				}
				

				if (item.getType() == "weapon") {
					g.drawImage(item.getTexture(), (int) ((590) * c.getScaleValue()),
							(int) (197 * c.getScaleValue()), (int) (104 * c.getScaleValue()),
							(int) (104 * c.getScaleValue()), null);
					
					Weapon weapon = (Weapon)item;
					
					g.setColor(Color.DARK_GRAY);
					g.fillRect(590, 320, 100, 8);
					if(weapon.getEndurancePercentage() < 33) 
						g.setColor(Color.red);
					else if(weapon.getEndurancePercentage() < 66) 
						g.setColor(Color.orange);
					else 
						g.setColor(Color.green);
					g.fillRect(590, 320, weapon.getEndurancePercentage(), 8);
					
					Color wc;
					if(weapon.getEnhancement().equals("keen") || weapon.getEnhancement().equals("murderous") || weapon.getEnhancement().equals("enhanced"))
						wc = new Color(130, 199, 102);
					else if(weapon.getEnhancement().equals("damaged") || weapon.getEnhancement().equals("shameful")) 
						wc = new Color(218, 61, 32);
					else 
						wc = Color.white;
					
					CustomTextWritter.drawString(g, "base damage: " + weapon.getDamage(),
							(int) ((c.getWidth() / 2) * c.getScaleValue()), (int) (560 * c.getScaleValue()),
							active, wc, Assets.font21);

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

					if(weapon.getEnhancement().equals("agile") || weapon.getEnhancement().equals("murderous"))
						wc = new Color(130, 199, 102);
					else if(weapon.getEnhancement().equals("rough") || weapon.getEnhancement().equals("shameful") || weapon.getEnhancement().equals("heavy") || weapon.getEnhancement().equals("massive")) 
						wc = new Color(218, 61, 32);
					else 
						wc = Color.white;
					
					CustomTextWritter.drawString(g, "speed: " + aSpeed,
							(int) ((c.getWidth() / 2) * c.getScaleValue()), (int) (590 * c.getScaleValue()),
							active, wc, Assets.font21);
					
					if(weapon.getEnhancement().equals("enhanced") || weapon.getEnhancement().equals("murderous"))
						wc = new Color(185, 32, 218);
					else if(weapon.getEnhancement().equals("rough") || weapon.getEnhancement().equals("shameful") || weapon.getEnhancement().equals("damaged") || weapon.getEnhancement().equals("heavy") || weapon.getEnhancement().equals("massive")) 
						wc = new Color(218, 61, 32);
					else if(weapon.getEnhancement().equals("keen") || weapon.getEnhancement().equals("agile") || weapon.getEnhancement().equals("scary") || weapon.getEnhancement().equals("durable")) 
						wc = new Color(130, 199, 102);
					else 
						wc = Color.white;
					
					CustomTextWritter.drawString(g, weapon.getEnhancement(),
							(int) ((c.getWidth() / 2) * c.getScaleValue()), (int) (185 * c.getScaleValue()),
							active, wc, Assets.font21);
					
					if(weapon.getEnhancement().equals("enhanced"))
						wc = new Color(130, 199, 102);
					else if(weapon.getEnhancement().equals("heavy")) 
						wc = new Color(218, 61, 32);
					else 
						wc = Color.white;
					CustomTextWritter.drawString(g, "weight: " + Math.round(weapon.getWeight()) + " lb",
							(int) (c.getWidth() / 2 * c.getScaleValue()), (int) (500 * c.getScaleValue()), active, wc,
							Assets.font21);
					
					if(weapon.getEnhancement().equals("massive"))
						wc = new Color(218, 61, 32);
					else
						wc = Color.white;
					
					CustomTextWritter.drawString(g, "volume: " + Math.round(weapon.getVolume()) + " cm^3",
							(int) (c.getWidth() / 2 * c.getScaleValue()), (int) (530 * c.getScaleValue()), active, wc,
							Assets.font21);
					
				} else if (item.getType() == "ranged") {
					Ranged ranged = (Ranged)item;
					
					g.setColor(Color.DARK_GRAY);
					g.fillRect(590, 320, 100, 8);
					g.setColor(Color.green);
					g.fillRect(590, 320, ranged.getAmmoPercentage(), 8);
					
					Color rc;
							
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

					if(ranged.getEnhancement().equals("revengeful") || ranged.getEnhancement().equals("piercing"))
						rc = new Color(130, 199, 102);
					else if(ranged.getEnhancement().equals("jamming") || ranged.getEnhancement().equals("shameful") || ranged.getEnhancement().equals("heavy") || ranged.getEnhancement().equals("massive")) 
						rc = new Color(218, 61, 32);
					else 
						rc = Color.white;
					
					CustomTextWritter.drawString(g, "speed: " + aSpeed,
							(int) ((c.getWidth() / 2) * c.getScaleValue()), (int) (560 * c.getScaleValue()),
							active, rc, Assets.font21);
					
					if(ranged.getEnhancement().equals("revengeful") || ranged.getEnhancement().equals("quickdraw") || ranged.getEnhancement().equals("sniping"))
						rc = new Color(130, 199, 102);
					else if(ranged.getEnhancement().equals("jamming")) 
						rc = new Color(218, 61, 32);
					else 
						rc = Color.white;
					
					CustomTextWritter.drawString(g, "load speed: " + Math.round(ranged.reloadCooldown/1000) + "s",
							(int) ((c.getWidth() / 2) * c.getScaleValue()), (int) (590 * c.getScaleValue()),
							active, rc, Assets.font21);
					
					if(ranged.getEnhancement().equals("heavy")) 
						rc = new Color(218, 61, 32);
					else 
						rc = Color.white;
					CustomTextWritter.drawString(g, "weight: " + Math.round(ranged.getWeight()) + " lb",
							(int) (c.getWidth() / 2 * c.getScaleValue()), (int) (500 * c.getScaleValue()), active, rc,
							Assets.font21);
					
					if(ranged.getEnhancement().equals("massive"))
						rc = new Color(218, 61, 32);
					else
						rc = Color.white;
					
					CustomTextWritter.drawString(g, "volume: " + Math.round(ranged.getVolume())+ " cm^3",
							(int) (c.getWidth() / 2 * c.getScaleValue()), (int) (530 * c.getScaleValue()), active, rc,
							Assets.font21);
					
					if(ranged.getEnhancement().equals("sniping") || ranged.getEnhancement().equals("revengeful"))
						rc = new Color(185, 32, 218);
					else if(ranged.getEnhancement().equals("shameful") ||  ranged.getEnhancement().equals("heavy") || ranged.getEnhancement().equals("massive") || ranged.getEnhancement().equals("jamming")) 
						rc = new Color(218, 61, 32);
					else if(ranged.getEnhancement().equals("piercing") || ranged.getEnhancement().equals("quickdraw")) 
						rc = new Color(130, 199, 102);
					else 
						rc = Color.white;
					
					CustomTextWritter.drawString(g, ranged.getEnhancement(),
							(int) ((c.getWidth() / 2) * c.getScaleValue()), (int) (185 * c.getScaleValue()),
							active, rc, Assets.font21);
					
				} else if (item.getType() == "helmet" || item.getType() == "chest" || item.getType() == "leg"
						|| item.getType() == "boots") {
					
					Armor armor = (Armor) item;
					
					g.setColor(Color.DARK_GRAY);
					g.fillRect(590, 320, 100, 8);
					if(armor.getEndurancePercentage() < 33) 
						g.setColor(Color.red);
					else if(armor.getEndurancePercentage() < 66) 
						g.setColor(Color.orange);
					else 
						g.setColor(Color.green);
					g.fillRect(590, 320, armor.getEndurancePercentage(), 8);
					
					Color ac = Color.white;
					
					if(armor.getEnhancement().equals("protective") || armor.getEnhancement().equals("reinforced") || armor.getEnhancement().equals("heavily plated")) 
						ac = new Color(130, 199, 102);
					else if (armor.getEnhancement().equals("cracked") || armor.getEnhancement().equals("awful"))
						ac = new Color(218, 61, 32);
					else 
						ac = Color.white;
					
					CustomTextWritter.drawString(g, "resistance: " + armor.getResistance(),
							(int) (c.getWidth() / 2 * c.getScaleValue()), (int) (560 * c.getScaleValue()), active,
							ac, Assets.font21);
					
					if(armor.getEnhancement().equals("cozy")) 
						ac = new Color(130, 199, 102);
					else if (armor.getEnhancement().equals("uncomfortable"))
						ac = new Color(218, 61, 32);
					else 
						ac = Color.white;
					
					if(armor.getHealthRegen() != 0)
						CustomTextWritter.drawString(g, "health regen: " + armor.getHealthRegen(),
								(int) (c.getWidth() / 2 * c.getScaleValue()), (int) (590 * c.getScaleValue()), active,
								ac, Assets.font21);
					
					if(armor.getEnhancement().equals("reinforced")) 
						ac = new Color(130, 199, 102);
					else if(armor.getEnhancement().equals("heavy")) 
						ac = new Color(218, 61, 32);
					else 
						ac = Color.white;
					CustomTextWritter.drawString(g, "weight: " + Math.round(armor.getWeight()) + " lb",
							(int) (c.getWidth() / 2 * c.getScaleValue()), (int) (500 * c.getScaleValue()), active, ac,
							Assets.font21);
					
					if(armor.getEnhancement().equals("massive")) 
						ac = new Color(218, 61, 32);
					else 
						ac = Color.white;
					CustomTextWritter.drawString(g, "volume: " + Math.round(armor.getVolume()) + " cm^3",
							(int) (c.getWidth() / 2 * c.getScaleValue()), (int) (530 * c.getScaleValue()), active, ac,
							Assets.font21);
					
					if(armor.getEnhancement().equals("reinforced") || armor.getEnhancement().equals("plated"))
						ac = new Color(185, 32, 218);
					else if(armor.getEnhancement().equals("cracked") || armor.getEnhancement().equals("awful") || armor.getEnhancement().equals("massive") || armor.getEnhancement().equals("heavy") || armor.getEnhancement().equals("uncomfortable")) 
						ac = new Color(218, 61, 32);
					else if(armor.getEnhancement().equals("intimidating") || armor.getEnhancement().equals("protective") || armor.getEnhancement().equals("durable") || armor.getEnhancement().equals("cozy")) 
						ac = new Color(130, 199, 102);
					else 
						ac = Color.white;
					
					CustomTextWritter.drawString(g, armor.getEnhancement(),
							(int) ((c.getWidth() / 2) * c.getScaleValue()), (int) (185 * c.getScaleValue()),
							active, ac, Assets.font21);
					
				} else if (item.getType() == "food") {
					Food food = (Food)(item);
					
					if(!food.getFood().equals("rotten")) {
						g.setColor(Color.DARK_GRAY);
						g.fillRect(590, 320, 100, 8);
						if(food.getFreshnessPercentage() < 33) 
							g.setColor(Color.red);
						else if(food.getFreshnessPercentage() < 66) 
							g.setColor(Color.orange);
						else 
							g.setColor(Color.green);
						g.fillRect(590, 320, food.getFreshnessPercentage(), 8);
					}
					
					CustomTextWritter.drawString(g, "hunger restore: " + food.getHunger(),
							(int) ((c.getWidth() / 2) * c.getScaleValue()), (int) (560 * c.getScaleValue()),
							active, Color.WHITE, Assets.font21);
					CustomTextWritter.drawString(g, "thirst restore: " + food.getThirst(),
							(int) ((c.getWidth() / 2) * c.getScaleValue()), (int) (590 * c.getScaleValue()),
							active, Color.WHITE, Assets.font21);
					
				} else if (item.getType() == "axe" || item.getType() == "pickaxe") {
					g.drawImage(item.getTexture(), (int) ((590) * c.getScaleValue()),
							(int) (197 * c.getScaleValue()), (int) (104 * c.getScaleValue()),
							(int) (104 * c.getScaleValue()), null);
					
					Tool tool = (Tool)item;
					
					g.setColor(Color.DARK_GRAY);
					g.fillRect(590, 320, 100, 8);
					if(tool.getEndurancePercentage() < 33) 
						g.setColor(Color.red);
					else if(tool.getEndurancePercentage() < 66) 
						g.setColor(Color.orange);
					else 
						g.setColor(Color.green);
					g.fillRect(590, 320, tool.getEndurancePercentage(), 8);
					
					Color wc;
					if(tool.getEnhancement().equals("sharp") || tool.getEnhancement().equals("dangerous"))
						wc = new Color(130, 199, 102);
					else if(tool.getEnhancement().equals("awful")) 
						wc = new Color(218, 61, 32);
					else 
						wc = Color.white;
					
					CustomTextWritter.drawString(g, "base damage: " + tool.getDamage(),
							(int) ((c.getWidth() / 2) * c.getScaleValue()), (int) (560 * c.getScaleValue()),
							active, wc, Assets.font21);
					
					if(tool.getEnhancement().equals("effective") || tool.getEnhancement().equals("efficient"))
						wc = new Color(130, 199, 102);
					else if(tool.getEnhancement().equals("awful") || tool.getEnhancement().equals("damaged")) 
						wc = new Color(218, 61, 32);
					else 
						wc = Color.white;
					
					if(tool.getType().equals("axe"))
						CustomTextWritter.drawString(g, "axe power: x" + Math.round(tool.getPower()),
								(int) ((c.getWidth() / 2) * c.getScaleValue()), (int) (590 * c.getScaleValue()),
								active, wc, Assets.font21);
					else
						CustomTextWritter.drawString(g, "pickaxe power: x" + Math.round(tool.getPower()),
								(int) ((c.getWidth() / 2) * c.getScaleValue()), (int) (590 * c.getScaleValue()),
								active, wc, Assets.font21);

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

					if(tool.getEnhancement().equals("dangerous") || tool.getEnhancement().equals("effective"))
						wc = new Color(130, 199, 102);
					else if(tool.getEnhancement().equals("rough") || tool.getEnhancement().equals("massive") || tool.getEnhancement().equals("heavy")) 
						wc = new Color(218, 61, 32);
					else 
						wc = Color.white;
					
					CustomTextWritter.drawString(g, "speed: " + aSpeed,
							(int) ((c.getWidth() / 2) * c.getScaleValue()), (int) (620 * c.getScaleValue()),
							active, wc, Assets.font21);
					
					if(tool.getEnhancement().equals("effective") || tool.getEnhancement().equals("dangerous"))
						wc = new Color(185, 32, 218);
					else if(tool.getEnhancement().equals("rough") || tool.getEnhancement().equals("awful") || tool.getEnhancement().equals("massive") || tool.getEnhancement().equals("heavy") || tool.getEnhancement().equals("damaged")) 
						wc = new Color(218, 61, 32);
					else if(tool.getEnhancement().equals("sharp") || tool.getEnhancement().equals("efficient") || tool.getEnhancement().equals("durable")) 
						wc = new Color(130, 199, 102);
					else 
						wc = Color.white;
					
					CustomTextWritter.drawString(g, tool.getEnhancement(),
							(int) ((c.getWidth() / 2) * c.getScaleValue()), (int) (185 * c.getScaleValue()),
							active, wc, Assets.font21);
					
					if(tool.getEnhancement().equals("efficient"))
						wc = new Color(130, 199, 102);
					else if(tool.getEnhancement().equals("heavy")) 
						wc = new Color(218, 61, 32);
					else 
						wc = Color.white;
					CustomTextWritter.drawString(g, "weight: " + Math.round(tool.getWeight()) + " lb",
							(int) (c.getWidth() / 2 * c.getScaleValue()), (int) (500 * c.getScaleValue()), active, wc,
							Assets.font21);
					
					if(tool.getEnhancement().equals("massive"))
						wc = new Color(218, 61, 32);
					else
						wc = Color.white;
					
					CustomTextWritter.drawString(g, "volume: " + Math.round(tool.getVolume()) + " cm^3",
							(int) (c.getWidth() / 2 * c.getScaleValue()), (int) (530 * c.getScaleValue()), active, wc,
							Assets.font21);
					
					CustomTextWritter.drawString(g, "Take All: <Enter>",
							(int)(c.getWidth() / 2 * c.getScaleValue()), (int)(640 * c.getScaleValue()), active, 
							Color.WHITE, Assets.font16);
					
					CustomTextWritter.drawString(g, "Take 1: <Shift> + <Enter>", 
							(int)(c.getWidth() / 2 * c.getScaleValue()), (int)(655 * c.getScaleValue()), active, 
							Color.WHITE, Assets.font16);
				}
			} 
			
		}

		// Render the Player inventory (playerInventory)
		CustomTextWritter.drawString(g, "Total Weight: " + Math.round(playerInventory.getInventoryWeight() * 10) / 10 + " kg",
				(int) (305 * c.getScaleValue()), (int) (530 * c.getScaleValue()), active, Color.WHITE,
				Assets.font16);
		CustomTextWritter.drawString(g, "Total Volume: " + Math.round(playerInventory.getInventoryVolume()) + " cm^3",
				(int) (305 * c.getScaleValue()), (int) (555 * c.getScaleValue()), active, Color.WHITE,
				Assets.font16);
		CustomTextWritter.drawString(g, "Max Volume: " + Math.round(playerInventory.getInventoryCapacity()) + " cm^3",
				(int) (305 * c.getScaleValue()), (int) (580 * c.getScaleValue()), active, Color.WHITE,
				Assets.font16);
		
		int playerInLen = playerInventory.InventoryItems.size();
		if (playerInLen == 0)
			return;

		for (int i = -4; i < 5; i++) {
			if (playerInventory.selectedItem + i < 0 || playerInventory.selectedItem + i >= playerInLen)
				continue;
			if (i == 0) {
				if (activeInventory == 0) {
					CustomTextWritter.drawString(g,
							"> " + playerInventory.InventoryItems.get(playerInventory.selectedItem + i).getName()
									+ " <",
							(int) (305 * c.getScaleValue()), (int) ((315 + i * inventorySpacing) * c.getScaleValue()),
							true, Color.YELLOW, Assets.font28);
				} else if (activeInventory == 1) {
					CustomTextWritter.drawString(g,
							playerInventory.InventoryItems.get(playerInventory.selectedItem + i).getName(),
							(int) (305 * c.getScaleValue()), (int) ((315 + i * inventorySpacing) * c.getScaleValue()),
							true, Color.WHITE, Assets.font28);
				}
			} else {
				CustomTextWritter.drawString(g,
						playerInventory.InventoryItems.get(playerInventory.selectedItem + i).getName(),
						(int) (305 * c.getScaleValue()), (int) ((315 + i * inventorySpacing) * c.getScaleValue()), true,
						Color.WHITE, Assets.font28);
			}
		}
		if (activeInventory == 0 && playerInventory.getInventoryWeight() != 0) {
			Item item = playerInventory.InventoryItems.get(playerInventory.selectedItem);

			g.drawImage(item.getTexture(), (int) (590 * c.getScaleValue()), (int) (197 * c.getScaleValue()),
					(int) (104 * c.getScaleValue()), (int) (104 * c.getScaleValue()), null);
			if (item.isStackable())
				CustomTextWritter.drawString(g, Integer.toString(item.getCount()), c.getWidth() / 2,
						(int) (400 * c.getScaleValue()), true, Color.WHITE, Assets.font28);
			else
				CustomTextWritter.drawString(g, "~", c.getWidth() / 2, (int) (400 * c.getScaleValue()), true,
						Color.WHITE, Assets.font28);

			CustomTextWritter.drawString(g, "type: " + item.getType(), (int) (c.getWidth() / 2 * c.getScaleValue()),
					(int) (470 * c.getScaleValue()), active, Color.WHITE, Assets.font21);

			CustomTextWritter.drawString(g, "weight: " + Math.round(item.getWeight()) + " lb",
					(int) ((c.getWidth() / 2) * c.getScaleValue()), (int) (500 * c.getScaleValue()), active,
					Color.WHITE, Assets.font21);
			CustomTextWritter.drawString(g, "volume: " + Math.round(item.getVolume()) + " cm^3",
					(int) ((c.getWidth() / 2) * c.getScaleValue()), (int) (530 * c.getScaleValue()), active,
					Color.WHITE, Assets.font21);

			if(item.getType() != "pickaxe" && item.getType() != "axe") {
				CustomTextWritter.drawString(g, "Take All: <Enter>",
						(int)(c.getWidth() / 2 * c.getScaleValue()), (int)(630 * c.getScaleValue()), active, 
						Color.WHITE, Assets.font16);
				
				CustomTextWritter.drawString(g, "Take 1: <Shift> + <Enter>", 
						(int)(c.getWidth() / 2 * c.getScaleValue()), (int)(645 * c.getScaleValue()), active, 
						Color.WHITE, Assets.font16);
			}
			

			if (item.getType() == "weapon") {
				g.drawImage(item.getTexture(), (int) ((590) * c.getScaleValue()),
						(int) (197 * c.getScaleValue()), (int) (104 * c.getScaleValue()),
						(int) (104 * c.getScaleValue()), null);
				
				Weapon weapon = (Weapon)item;
				
				g.setColor(Color.DARK_GRAY);
				g.fillRect(590, 320, 100, 8);
				if(weapon.getEndurancePercentage() < 33) 
					g.setColor(Color.red);
				else if(weapon.getEndurancePercentage() < 66) 
					g.setColor(Color.orange);
				else 
					g.setColor(Color.green);
				g.fillRect(590, 320, weapon.getEndurancePercentage(), 8);
				
				Color wc;
				if(weapon.getEnhancement().equals("keen") || weapon.getEnhancement().equals("murderous") || weapon.getEnhancement().equals("enhanced"))
					wc = new Color(130, 199, 102);
				else if(weapon.getEnhancement().equals("damaged") || weapon.getEnhancement().equals("shameful")) 
					wc = new Color(218, 61, 32);
				else 
					wc = Color.white;
				
				CustomTextWritter.drawString(g, "base damage: " + weapon.getDamage(),
						(int) ((c.getWidth() / 2) * c.getScaleValue()), (int) (560 * c.getScaleValue()),
						active, wc, Assets.font21);

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

				if(weapon.getEnhancement().equals("agile") || weapon.getEnhancement().equals("murderous"))
					wc = new Color(130, 199, 102);
				else if(weapon.getEnhancement().equals("rough") || weapon.getEnhancement().equals("shameful") || weapon.getEnhancement().equals("heavy") || weapon.getEnhancement().equals("massive")) 
					wc = new Color(218, 61, 32);
				else 
					wc = Color.white;
				
				CustomTextWritter.drawString(g, "speed: " + aSpeed,
						(int) ((c.getWidth() / 2) * c.getScaleValue()), (int) (590 * c.getScaleValue()),
						active, wc, Assets.font21);
				
				if(weapon.getEnhancement().equals("enhanced") || weapon.getEnhancement().equals("murderous"))
					wc = new Color(185, 32, 218);
				else if(weapon.getEnhancement().equals("rough") || weapon.getEnhancement().equals("shameful") || weapon.getEnhancement().equals("damaged") || weapon.getEnhancement().equals("heavy") || weapon.getEnhancement().equals("massive")) 
					wc = new Color(218, 61, 32);
				else if(weapon.getEnhancement().equals("keen") || weapon.getEnhancement().equals("agile") || weapon.getEnhancement().equals("scary") || weapon.getEnhancement().equals("durable")) 
					wc = new Color(130, 199, 102);
				else 
					wc = Color.white;
				
				CustomTextWritter.drawString(g, weapon.getEnhancement(),
						(int) ((c.getWidth() / 2) * c.getScaleValue()), (int) (185 * c.getScaleValue()),
						active, wc, Assets.font21);
				
				if(weapon.getEnhancement().equals("enhanced"))
					wc = new Color(130, 199, 102);
				else if(weapon.getEnhancement().equals("heavy")) 
					wc = new Color(218, 61, 32);
				else 
					wc = Color.white;
				CustomTextWritter.drawString(g, "weight: " + Math.round(weapon.getWeight()) + " lb",
						(int) (c.getWidth() / 2 * c.getScaleValue()), (int) (500 * c.getScaleValue()), active, wc,
						Assets.font21);
				
				if(weapon.getEnhancement().equals("massive"))
					wc = new Color(218, 61, 32);
				else
					wc = Color.white;
				
				CustomTextWritter.drawString(g, "volume: " + Math.round(weapon.getVolume()) + " cm^3",
						(int) (c.getWidth() / 2 * c.getScaleValue()), (int) (530 * c.getScaleValue()), active, wc,
						Assets.font21);
				
			} else if (item.getType() == "ranged") {
Ranged ranged = (Ranged)item;
				
				g.setColor(Color.DARK_GRAY);
				g.fillRect(590, 320, 100, 8);
				g.setColor(Color.green);
				g.fillRect(590, 320, ranged.getAmmoPercentage(), 8);
				
				Color rc;
						
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

				if(ranged.getEnhancement().equals("revengeful") || ranged.getEnhancement().equals("piercing"))
					rc = new Color(130, 199, 102);
				else if(ranged.getEnhancement().equals("jamming") || ranged.getEnhancement().equals("shameful") || ranged.getEnhancement().equals("heavy") || ranged.getEnhancement().equals("massive")) 
					rc = new Color(218, 61, 32);
				else 
					rc = Color.white;
				
				CustomTextWritter.drawString(g, "speed: " + aSpeed,
						(int) ((c.getWidth() / 2) * c.getScaleValue()), (int) (560 * c.getScaleValue()),
						active, rc, Assets.font21);
				
				if(ranged.getEnhancement().equals("revengeful") || ranged.getEnhancement().equals("quickdraw") || ranged.getEnhancement().equals("sniping"))
					rc = new Color(130, 199, 102);
				else if(ranged.getEnhancement().equals("jamming")) 
					rc = new Color(218, 61, 32);
				else 
					rc = Color.white;
				
				CustomTextWritter.drawString(g, "load speed: " + Math.round(ranged.reloadCooldown/1000) + "s",
						(int) ((c.getWidth() / 2) * c.getScaleValue()), (int) (590 * c.getScaleValue()),
						active, rc, Assets.font21);
				
				if(ranged.getEnhancement().equals("heavy")) 
					rc = new Color(218, 61, 32);
				else 
					rc = Color.white;
				CustomTextWritter.drawString(g, "weight: " + Math.round(ranged.getWeight()) + " lb",
						(int) (c.getWidth() / 2 * c.getScaleValue()), (int) (500 * c.getScaleValue()), active, rc,
						Assets.font21);
				
				if(ranged.getEnhancement().equals("massive"))
					rc = new Color(218, 61, 32);
				else
					rc = Color.white;
				
				CustomTextWritter.drawString(g, "volume: " + Math.round(ranged.getVolume())+ " cm^3",
						(int) (c.getWidth() / 2 * c.getScaleValue()), (int) (530 * c.getScaleValue()), active, rc,
						Assets.font21);
				
				if(ranged.getEnhancement().equals("sniping") || ranged.getEnhancement().equals("revengeful"))
					rc = new Color(185, 32, 218);
				else if(ranged.getEnhancement().equals("shameful") ||  ranged.getEnhancement().equals("heavy") || ranged.getEnhancement().equals("massive") || ranged.getEnhancement().equals("jamming")) 
					rc = new Color(218, 61, 32);
				else if(ranged.getEnhancement().equals("piercing") || ranged.getEnhancement().equals("quickdraw")) 
					rc = new Color(130, 199, 102);
				else 
					rc = Color.white;
				
				CustomTextWritter.drawString(g, ranged.getEnhancement(),
						(int) ((c.getWidth() / 2) * c.getScaleValue()), (int) (185 * c.getScaleValue()),
						active, rc, Assets.font21);
				
			} else if (item.getType() == "helmet" || item.getType() == "chest" || item.getType() == "leg"
					|| item.getType() == "boots") {
				
				Armor armor = (Armor) item;
				
				g.setColor(Color.DARK_GRAY);
				g.fillRect(590, 320, 100, 8);
				if(armor.getEndurancePercentage() < 33) 
					g.setColor(Color.red);
				else if(armor.getEndurancePercentage() < 66) 
					g.setColor(Color.orange);
				else 
					g.setColor(Color.green);
				g.fillRect(590, 320, armor.getEndurancePercentage(), 8);
				
				Color ac = Color.white;
				
				if(armor.getEnhancement().equals("protective") || armor.getEnhancement().equals("reinforced") || armor.getEnhancement().equals("heavily plated")) 
					ac = new Color(130, 199, 102);
				else if (armor.getEnhancement().equals("cracked") || armor.getEnhancement().equals("awful"))
					ac = new Color(218, 61, 32);
				else 
					ac = Color.white;
				
				CustomTextWritter.drawString(g, "resistance: " + armor.getResistance(),
						(int) (c.getWidth() / 2 * c.getScaleValue()), (int) (560 * c.getScaleValue()), active,
						ac, Assets.font21);
				
				if(armor.getEnhancement().equals("cozy")) 
					ac = new Color(130, 199, 102);
				else if (armor.getEnhancement().equals("uncomfortable"))
					ac = new Color(218, 61, 32);
				else 
					ac = Color.white;
				
				if(armor.getHealthRegen() != 0)
					CustomTextWritter.drawString(g, "health regen: " + armor.getHealthRegen(),
							(int) (c.getWidth() / 2 * c.getScaleValue()), (int) (590 * c.getScaleValue()), active,
							ac, Assets.font21);
				
				if(armor.getEnhancement().equals("reinforced")) 
					ac = new Color(130, 199, 102);
				else if(armor.getEnhancement().equals("heavy")) 
					ac = new Color(218, 61, 32);
				else 
					ac = Color.white;
				CustomTextWritter.drawString(g, "weight: " + Math.round(armor.getWeight()) + " lb",
						(int) (c.getWidth() / 2 * c.getScaleValue()), (int) (500 * c.getScaleValue()), active, ac,
						Assets.font21);
				
				if(armor.getEnhancement().equals("massive")) 
					ac = new Color(218, 61, 32);
				else 
					ac = Color.white;
				CustomTextWritter.drawString(g, "volume: " + Math.round(armor.getVolume()) + " cm^3",
						(int) (c.getWidth() / 2 * c.getScaleValue()), (int) (530 * c.getScaleValue()), active, ac,
						Assets.font21);
				
				if(armor.getEnhancement().equals("reinforced") || armor.getEnhancement().equals("plated"))
					ac = new Color(185, 32, 218);
				else if(armor.getEnhancement().equals("cracked") || armor.getEnhancement().equals("awful") || armor.getEnhancement().equals("massive") || armor.getEnhancement().equals("heavy") || armor.getEnhancement().equals("uncomfortable")) 
					ac = new Color(218, 61, 32);
				else if(armor.getEnhancement().equals("intimidating") || armor.getEnhancement().equals("protective") || armor.getEnhancement().equals("durable") || armor.getEnhancement().equals("cozy")) 
					ac = new Color(130, 199, 102);
				else 
					ac = Color.white;
				
				CustomTextWritter.drawString(g, armor.getEnhancement(),
						(int) ((c.getWidth() / 2) * c.getScaleValue()), (int) (185 * c.getScaleValue()),
						active, ac, Assets.font21);
				
			} else if (item.getType() == "food") {
				
				Food food = (Food)(item);
				
				if(!food.getFood().equals("rotten")) {
					g.setColor(Color.DARK_GRAY);
					g.fillRect(590, 320, 100, 8);
					if(food.getFreshnessPercentage() < 33) 
						g.setColor(Color.red);
					else if(food.getFreshnessPercentage() < 66) 
						g.setColor(Color.orange);
					else 
						g.setColor(Color.green);
					g.fillRect(590, 320, food.getFreshnessPercentage(), 8);
				}
				
				CustomTextWritter.drawString(g, "hunger restore: " + food.getHunger(),
						(int) ((c.getWidth() / 2) * c.getScaleValue()), (int) (560 * c.getScaleValue()),
						active, Color.WHITE, Assets.font21);
				CustomTextWritter.drawString(g, "thirst restore: " + food.getThirst(),
						(int) ((c.getWidth() / 2) * c.getScaleValue()), (int) (590 * c.getScaleValue()),
						active, Color.WHITE, Assets.font21);
				
			} else if (item.getType() == "axe" || item.getType() == "pickaxe") {
				g.drawImage(item.getTexture(), (int) ((590) * c.getScaleValue()),
						(int) (197 * c.getScaleValue()), (int) (104 * c.getScaleValue()),
						(int) (104 * c.getScaleValue()), null);
				
				Tool tool = (Tool)item;
				
				g.setColor(Color.DARK_GRAY);
				g.fillRect(590, 320, 100, 8);
				if(tool.getEndurancePercentage() < 33) 
					g.setColor(Color.red);
				else if(tool.getEndurancePercentage() < 66) 
					g.setColor(Color.orange);
				else 
					g.setColor(Color.green);
				g.fillRect(590, 320, tool.getEndurancePercentage(), 8);
				
				Color wc;
				if(tool.getEnhancement().equals("sharp") || tool.getEnhancement().equals("dangerous"))
					wc = new Color(130, 199, 102);
				else if(tool.getEnhancement().equals("awful")) 
					wc = new Color(218, 61, 32);
				else 
					wc = Color.white;
				
				CustomTextWritter.drawString(g, "base damage: " + tool.getDamage(),
						(int) ((c.getWidth() / 2) * c.getScaleValue()), (int) (560 * c.getScaleValue()),
						active, wc, Assets.font21);
				
				if(tool.getEnhancement().equals("effective") || tool.getEnhancement().equals("efficient"))
					wc = new Color(130, 199, 102);
				else if(tool.getEnhancement().equals("awful") || tool.getEnhancement().equals("damaged")) 
					wc = new Color(218, 61, 32);
				else 
					wc = Color.white;
				
				if(tool.getType().equals("axe"))
					CustomTextWritter.drawString(g, "axe power: x" + Math.round(tool.getPower()),
							(int) ((c.getWidth() / 2) * c.getScaleValue()), (int) (590 * c.getScaleValue()),
							active, wc, Assets.font21);
				else
					CustomTextWritter.drawString(g, "pickaxe power: x" + Math.round(tool.getPower()),
							(int) ((c.getWidth() / 2) * c.getScaleValue()), (int) (590 * c.getScaleValue()),
							active, wc, Assets.font21);

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

				if(tool.getEnhancement().equals("dangerous") || tool.getEnhancement().equals("effective"))
					wc = new Color(130, 199, 102);
				else if(tool.getEnhancement().equals("rough") || tool.getEnhancement().equals("massive") || tool.getEnhancement().equals("heavy")) 
					wc = new Color(218, 61, 32);
				else 
					wc = Color.white;
				
				CustomTextWritter.drawString(g, "speed: " + aSpeed,
						(int) ((c.getWidth() / 2) * c.getScaleValue()), (int) (620 * c.getScaleValue()),
						active, wc, Assets.font21);
				
				if(tool.getEnhancement().equals("effective") || tool.getEnhancement().equals("dangerous"))
					wc = new Color(185, 32, 218);
				else if(tool.getEnhancement().equals("rough") || tool.getEnhancement().equals("awful") || tool.getEnhancement().equals("massive") || tool.getEnhancement().equals("heavy") || tool.getEnhancement().equals("damaged")) 
					wc = new Color(218, 61, 32);
				else if(tool.getEnhancement().equals("sharp") || tool.getEnhancement().equals("efficient") || tool.getEnhancement().equals("durable")) 
					wc = new Color(130, 199, 102);
				else 
					wc = Color.white;
				
				CustomTextWritter.drawString(g, tool.getEnhancement(),
						(int) ((c.getWidth() / 2) * c.getScaleValue()), (int) (185 * c.getScaleValue()),
						active, wc, Assets.font21);
				
				if(tool.getEnhancement().equals("efficient"))
					wc = new Color(130, 199, 102);
				else if(tool.getEnhancement().equals("heavy")) 
					wc = new Color(218, 61, 32);
				else 
					wc = Color.white;
				CustomTextWritter.drawString(g, "weight: " + Math.round(tool.getWeight()) + " lb",
						(int) (c.getWidth() / 2 * c.getScaleValue()), (int) (500 * c.getScaleValue()), active, wc,
						Assets.font21);
				
				if(tool.getEnhancement().equals("massive"))
					wc = new Color(218, 61, 32);
				else
					wc = Color.white;
				
				CustomTextWritter.drawString(g, "volume: " + Math.round(tool.getVolume()) + " cm^3",
						(int) (c.getWidth() / 2 * c.getScaleValue()), (int) (530 * c.getScaleValue()), active, wc,
						Assets.font21);
				
				CustomTextWritter.drawString(g, "Take All: <Enter>",
						(int)(c.getWidth() / 2 * c.getScaleValue()), (int)(640 * c.getScaleValue()), active, 
						Color.WHITE, Assets.font16);
				
				CustomTextWritter.drawString(g, "Take 1: <Shift> + <Enter>", 
						(int)(c.getWidth() / 2 * c.getScaleValue()), (int)(655 * c.getScaleValue()), active, 
						Color.WHITE, Assets.font16);
			}
		} 

	}
	
	public void checkFood() {
		for(int i = 0; i < InventoryItemsList.size(); i++) {
            if(InventoryItemsList.get(i).getType().equals("food")) {
                    
                    Food food = (Food)InventoryItemsList.get(i);
                    food.rotTimer += System.currentTimeMillis() - food.lastrotTimer;
                    food.lastrotTimer = System.currentTimeMillis();

                    if (food.rotTimer < food.rotCooldown)
                            continue;
                    
                    if(!food.getFood().equals("rotten") && food.currentFreshness > 0) {
                            food.currentFreshness--;
                            if(food.currentFreshness <= 1) {
                                    if(food.getName().equals("raw chicken") || food.getName().equals("cooked chicken") || food.getName().equals("suspicious chicken")) {
                                    	removeItem(food);
                                    	addItem(Food.rottenChickenItem);
                                    }
                                    else if(food.getName().equals("raw morsel") || food.getName().equals("cooked morsel") || food.getName().equals("suspicious morsel")) {
                                    	removeItem(food);
                                    	addItem(Food.rottenMorselItem);
                                    }
                                    else if(food.getName().equals("raw meat") || food.getName().equals("cooked meat") || food.getName().equals("suspicious meat")) {
                                    	removeItem(food);
                                    	addItem(Food.rottenMeatItem);
                                    }
                                    else if(food.getName().equals("bug meat") || food.getName().equals("cooked bug")) {
                                    	removeItem(food);
                                    	addItem(Food.rottenBugItem);
                                    }
                                    else {
                                    	removeItem(food);
                                    	addItem(Food.rotItem);
                                    }
                                    return;
                            }
                    }
                    
                    food.rotTimer = 0;
            }
    }
	}
	
	public void overnightFood() {
		for(int i = 0; i < InventoryItemsList.size(); i++) {
            if(InventoryItemsList.get(i).getType().equals("food")) {
                    
                    Food food = (Food)InventoryItemsList.get(i);
                    
                    if(!food.getFood().equals("rotten") && food.currentFreshness > 0) {
                            food.currentFreshness-=58;
                            if(food.currentFreshness <= 1) {
                                    if(food.getName().equals("raw chicken") || food.getName().equals("cooked chicken") || food.getName().equals("suspicious chicken")) {
                                    	removeItem(food);
                                    	addItem(Food.rottenChickenItem);
                                    }
                                    else if(food.getName().equals("raw morsel") || food.getName().equals("cooked morsel") || food.getName().equals("suspicious morsel")) {
                                    	removeItem(food);
                                    	addItem(Food.rottenMorselItem);
                                    }
                                    else if(food.getName().equals("raw meat") || food.getName().equals("cooked meat") || food.getName().equals("suspicious meat")) {
                                    	removeItem(food);
                                    	addItem(Food.rottenMeatItem);
                                    }
                                    else if(food.getName().equals("bug meat") || food.getName().equals("cooked bug")) {
                                    	removeItem(food);
                                    	addItem(Food.rottenBugItem);
                                    }
                                    else {
                                    	removeItem(food);
                                    	addItem(Food.rotItem);
                                    }
                                    return;
                            }
                    }
            }
    }
	}

	// Add items to chest
	public boolean addItem(Item item) {
		if(item.getVolume()+inventoryVolume > inventoryCapacity) {
			volumeExceed = true;
			return false;
		}else {
			if (InventoryItems.containsKey(item)) {
				int itemAmount = InventoryItems.get(item);
				InventoryItems.put(item, itemAmount + 1);
			} else {
				InventoryItems.put(item, 1);
			}
			inventoryWeight += item.getWeight();
			inventoryVolume += item.getVolume();
			updateItemList();
			return true;
		}
	}

	// Remove items from chest
	public void removeItem(Item item) {

		if (InventoryItems.containsKey(item)) {
			int itemAmount = InventoryItems.get(item);
			InventoryItems.put(item, itemAmount - 1);
			if (InventoryItems.get(item) <= 0) {
				InventoryItems.remove(item);
			}
			inventoryWeight -= item.getWeight();
			inventoryVolume -= item.getVolume();
		}

		updateItemList();

	}

	public int getItemCount(Item item) {

		if (InventoryItems.containsKey(item)) {
			return InventoryItems.get(item);
		} else {
			return 0;
		}

	}

	// This method updates the item array list
	public void updateItemList() {
		InventoryItemsList.clear();

		for (Entry<Item, Integer> entry : InventoryItems.entrySet()) {
			if (entry.getKey().isStackable())
				InventoryItemsList.add(entry.getKey());
			else
				for (int i = 0; i < entry.getValue(); i++)
					InventoryItemsList.add(entry.getKey());
		}
	}

	// This method clears the chest
	public void emptyChest() {
		InventoryItems.clear();
		InventoryItemsList.clear();
		inventoryWeight = 0;
		inventoryVolume = 0;
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

	public double getInventoryWeight() {
		return inventoryWeight;
	}

	public void setInventoryWeight(double inventoryWeight) {
		this.inventoryWeight = inventoryWeight;
	}

	public double getInventoryVolume() {
		return inventoryVolume;
	}
	
	public void setInventoryVolume(double inventoryVolume) {
		this.inventoryVolume = inventoryVolume;
	}

	public double getInventoryCapacity() {
		return inventoryCapacity;
	}

	public void setInventoryCapacity(double inventoryCapacity) {
		this.inventoryCapacity = inventoryCapacity;
	}
}