package inventory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import alphaPackage.ControlCenter;
import creatures.Player;
import graphics.Assets;
import graphics.CT;
import graphics.CustomTextWritter;
import items.Armor;
import items.Food;
import items.Item;
import items.Ranged;
import items.Tool;
import items.Torch;
import items.WaterContainer;
import items.Weapon;
import structureInventory.AutoCookerV2Craft;

public class Inventory {

	private long lastInventoryFullTimer, InventoryFullCooldown = 2000, InventoryFullTimer = InventoryFullCooldown;
	
	private ControlCenter c;
	private Player player;
	private boolean active = false;
	public ArrayList<Item> InventoryItems;
	public int selectedItem = 0;
	private int inventorySpacing = 30;
	private double inventoryWeight = 0;
	public double maxInventoryVolume = 750;
	private double inventoryVolume = 0;
	private boolean volumeExceed = false;
	private Equipment e;
	private AutoCookerV2Craft autoCookerV2; //NOTE: The player can only use one autocookerV2

	public Inventory(ControlCenter c, Player player, Equipment e) {
		
		this.c = c;
		this.player = player;
		this.e = e;
		InventoryItems = new ArrayList<Item>();
		autoCookerV2 = new AutoCookerV2Craft(c);
		addItem(Item.torch);
		addItem(Tool.woodenAxeTool);
		addItem(Weapon.darkSaber);
		addItem(Ranged.pulseRifle);
		addItem(Torch.torch);
		//for(int i = 0; i< 20; i++)
		//	addItem(Item.woodenPlankItem);
		//addItem(Item.researchKitItem);
		
	}

	public void tick() {
	
		if (c.getKeyManager().keyJustPressed(KeyEvent.VK_E)) {
			active = !active;
			
			if(active)
				player.getHandCraft().setActive(false);
		}

		if (!active) {
			return;
		}

		if (c.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
			selectedItem--;
		}
		if (c.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
			selectedItem++;
		}

		if (c.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)) {
			Item item = InventoryItems.get(selectedItem);

			if (item.getType().equals("helmet")) {

				if (e.helmet != null) {
					player.setResistance(player.getResistance() - player.getEquipment().helmet.getResistance());
					player.setIntimidation(player.getIntimidation() - player.getEquipment().helmet.getIntimidation());
					e.helmet = null;
				} else {
					e.helmet = (Armor)item;
					player.setResistance(player.getResistance() + player.getEquipment().helmet.getResistance());
					player.setIntimidation(player.getIntimidation() + player.getEquipment().helmet.getIntimidation());
				}
			}

			else if (item.getType().equals("chest")) {
				if (e.chest != null) {
					player.setResistance(player.getResistance() - player.getEquipment().chest.getResistance());
					player.setIntimidation(player.getIntimidation() - player.getEquipment().chest.getIntimidation());
					e.chest = null;
				}
				else {
					e.chest = (Armor)item;
					player.setResistance(player.getResistance() + player.getEquipment().chest.getResistance());
					player.setIntimidation(player.getIntimidation() + player.getEquipment().chest.getIntimidation());
				}
			}

			else if (item.getType().equals("leg")) {
				if (e.leg != null) {
					player.setResistance(player.getResistance() - player.getEquipment().leg.getResistance());
					player.setIntimidation(player.getIntimidation() - player.getEquipment().leg.getIntimidation());
					e.leg = null;
				} else {
					e.leg = (Armor)item;
					player.setResistance(player.getResistance() + player.getEquipment().leg.getResistance());
					player.setIntimidation(player.getIntimidation() + player.getEquipment().leg.getIntimidation());
				}
			}

			else if (item.getType().equals("boots")) {
				if (e.boot != null) {
					player.setResistance(player.getResistance() - player.getEquipment().boot.getResistance());
					player.setIntimidation(player.getIntimidation() - player.getEquipment().boot.getIntimidation());
					e.boot = null;
				} else {
					e.boot = (Armor)item;
					player.setResistance(player.getResistance() + player.getEquipment().boot.getResistance());
					player.setIntimidation(player.getIntimidation() + player.getEquipment().boot.getIntimidation());
				}
			}
			
			else if (item.getType().equals("gauntlets")) {
				if (e.gauntlet != null) {
					player.setResistance(player.getResistance() - player.getEquipment().gauntlet.getResistance());
					player.setIntimidation(player.getIntimidation() - player.getEquipment().gauntlet.getIntimidation());
					e.boot = null;
				} else {
					e.gauntlet = (Armor)item;
					player.setResistance(player.getResistance() + player.getEquipment().gauntlet.getResistance());
					player.setIntimidation(player.getIntimidation() + player.getEquipment().gauntlet.getIntimidation());
				}
			}

			if(item.getId() == Item.autoCookerV2Item.getId()) {
				if(autoCookerV2.products.isEmpty()) {
					autoCookerV2.setActive(true);
					Player.getPlayerData().setAutoCookerV2Active(true);
					Player.getPlayerData().setAutoCookerV2(autoCookerV2);
					c.getMenuState().getWorldSelectState().getGameState()
					.getWorldGenerator().setAutoCookerV2Active(true);
					c.getMenuState().getWorldSelectState().getGameState()
					.getWorldGenerator().setAutoCookerV2(autoCookerV2);
					autoCookerV2.craftSetup();
					autoCookerV2.findCraftableRecipes();
				}else {
					autoCookerV2.collectItems();
				}
			}
		}

		if (selectedItem < 0) {
			selectedItem = InventoryItems.size() - 1;
		}

		else if (selectedItem >= InventoryItems.size()) {
			selectedItem = 0;
		}

		if (inventoryWeight < 0) {
			inventoryWeight = 0;
		}
		if (inventoryVolume < 0) {
			inventoryVolume = 0;
		}

		if (inventoryWeight != 0) {
			if (c.getKeyManager().keyJustPressed(KeyEvent.VK_Q) && !InventoryItems.isEmpty()) {
				dropItem(InventoryItems.get(selectedItem), true);
				
			}
		}

	}
	
	public void dropItem(Item item, boolean condition) {
		
		inventoryWeight -= item.getWeight();
		inventoryVolume -= item.getVolume();
		item.setCount(item.getCount() - 1);
		
		if(item.getType().equals("ranged")) {
			if(condition) {
				Ranged r = (Ranged)item;
				c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
				.getItemManager().addItem(r.createNew(r, (int) player.getX() + CT.random(0, player.getBounds().width),
						(int) player.getY() + CT.random(0, player.getBounds().height)));
			}
		} else if(item.getType().equals("weapon")) {
			if(condition) {
				Weapon r = (Weapon)item;
				c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
				.getItemManager().addItem(r.createNew(r, (int) player.getX() + CT.random(0, player.getBounds().width),
						(int) player.getY() + CT.random(0, player.getBounds().height)));
			}
		} else if(item.getType().equals("axe") || item.getType().equals("pickaxe")) {
			if(condition) {
				Tool r = (Tool)item;
				c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
				.getItemManager().addItem(r.createNew(r, (int) player.getX() + CT.random(0, player.getBounds().width),
						(int) player.getY() + CT.random(0, player.getBounds().height)));
			}
		} else if(item.getType().equals("helmet") || item.getType().equals("chest")  || item.getType().equals("leg")
				 || item.getType().equals("boots") || item.getType().equals("gauntlets")) {
			if(condition) {
				Armor r = (Armor)item;
				c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
				.getItemManager().addItem(r.createNew(r, (int) player.getX() + CT.random(0, player.getBounds().width),
						(int) player.getY() + CT.random(0, player.getBounds().height)));
			}
		} else if(item.getType().equals("water container")) {
			if(condition) {
				WaterContainer r = (WaterContainer)item;
				c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
				.getItemManager().addItem(r.createNew(r, (int) player.getX() + CT.random(0, player.getBounds().width),
						(int) player.getY() + CT.random(0, player.getBounds().height)));
			}
		} else if(item.getType().equals("food")) {
			if(condition) {
				Food r = (Food)item;
				c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
				.getItemManager().addItem(r.createNew(r, (int) player.getX() + CT.random(0, player.getBounds().width),
						(int) player.getY() + CT.random(0, player.getBounds().height)));
			}
		} else if(item.getType().equals("torch")) {
			if(condition) {
				Torch t = (Torch)item;
				item.setItemEquipped(false);
				item.tick();
				c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
				.getItemManager().addItem(t.createNew(t, (int) player.getX() + CT.random(0, player.getBounds().width),
						(int) player.getY() + CT.random(0, player.getBounds().height)));
			}
		} else {
			if(condition) {
				c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
				.getItemManager().addItem(item.createNewInventoryItem(item, (int) player.getX() + CT.random(0, player.getBounds().width),
						(int) player.getY() + CT.random(0, player.getBounds().height)));
			}
		}
		
		if (item.getCount() <= 0) {
			InventoryItems.remove(selectedItem);
			if (selectedItem > 0)
				selectedItem--;
		}
		
	}
	
	public void render(Graphics g) {
		if (!active) {
			return;
		}
		g.drawImage(Assets.inventory, c.getWidth() / 2 - Assets.inventory.getWidth() * 4,
				c.getHeight() / 2 - Assets.inventory.getHeight() * 4, Assets.inventory.getWidth() * 8,
				(int) Assets.inventory.getHeight() * 8, null);
		
		CustomTextWritter.drawString(g, "W",
				(int) (312 * c.getScaleValue()), (int) (160 * c.getScaleValue()), active, Color.WHITE,
				Assets.font28);
		
		CustomTextWritter.drawString(g, "S",
				(int) (312 * c.getScaleValue()), (int) (470 * c.getScaleValue()), active, Color.WHITE,
				Assets.font28);
		
		if(volumeExceed) {
			CustomTextWritter.drawString(g, "Inventory Volume Reached",
					(int) (c.getWidth() / 2 * c.getScaleValue()), (int) (c.getHeight() / 2 * c.getScaleValue()), active, 
					Color.WHITE, Assets.font28);
			volumeExceed = false;
		}
		
		if(e.helmet != null) {
			g.drawImage(e.helmet.getTexture(), (int) (865 * c.getScaleValue()), (int) (185 * c.getScaleValue()),
					(int) (64 * c.getScaleValue()), (int) (64 * c.getScaleValue()), null);
			g.setColor(Color.DARK_GRAY);
			g.fillRect(850, 260, 100, 8);
			if(e.helmet.getEndurancePercentage() < 33) 
				g.setColor(Color.red);
			else if(e.helmet.getEndurancePercentage() < 66) 
				g.setColor(Color.orange);
			else 
				g.setColor(Color.green);
			g.fillRect(850, 260, e.helmet.getEndurancePercentage(), 8);
		}
		if(e.chest != null) {
			g.drawImage(e.chest.getTexture(), (int) (1000 * c.getScaleValue()), (int) (185 * c.getScaleValue()),
					(int) (64 * c.getScaleValue()), (int) (64 * c.getScaleValue()), null);
			g.setColor(Color.DARK_GRAY);
			g.fillRect(985, 260, 100, 8);
			if(e.chest.getEndurancePercentage() < 33) 
				g.setColor(Color.red);
			else if(e.chest.getEndurancePercentage() < 66) 
				g.setColor(Color.orange);
			else 
				g.setColor(Color.green);
			g.fillRect(985, 260, e.chest.getEndurancePercentage(), 8);
		}
		if(e.leg != null) {
			g.drawImage(e.leg.getTexture(), (int) (865 * c.getScaleValue()), (int) (320 * c.getScaleValue()),
					(int) (64 * c.getScaleValue()), (int) (64 * c.getScaleValue()), null);
			g.setColor(Color.DARK_GRAY);
			g.fillRect(850, 395, 100, 8);
			if(e.leg.getEndurancePercentage() < 33) 
				g.setColor(Color.red);
			else if(e.leg.getEndurancePercentage() < 66) 
				g.setColor(Color.orange);
			else 
				g.setColor(Color.green);
			g.fillRect(850, 395, e.leg.getEndurancePercentage(), 8);
		}
		if(e.boot != null) {
			g.drawImage(e.boot.getTexture(), (int) (1000 * c.getScaleValue()), (int) (320 * c.getScaleValue()),
					(int) (64 * c.getScaleValue()), (int) (64 * c.getScaleValue()), null);
			g.setColor(Color.DARK_GRAY);
			g.fillRect(985, 395, 100, 8);
			if(e.boot.getEndurancePercentage() < 33) 
				g.setColor(Color.red);
			else if(e.boot.getEndurancePercentage() < 66) 
				g.setColor(Color.orange);
			else 
				g.setColor(Color.green);
			g.fillRect(985, 395, e.boot.getEndurancePercentage(), 8);
		}
		if(e.gauntlet != null) {
			g.drawImage(e.gauntlet.getTexture(), (int) (935 * c.getScaleValue()), (int) (455 * c.getScaleValue()),
					(int) (64 * c.getScaleValue()), (int) (64 * c.getScaleValue()), null);
			g.setColor(Color.DARK_GRAY);
			g.fillRect(922, 530, 100, 8);
			if(e.gauntlet.getEndurancePercentage() < 33) 
				g.setColor(Color.red);
			else if(e.gauntlet.getEndurancePercentage() < 66) 
				g.setColor(Color.orange);
			else 
				g.setColor(Color.green);
			g.fillRect(922, 530, e.gauntlet.getEndurancePercentage(), 8);
		}
		
		int inlen = InventoryItems.size();
		if (inlen == 0)
			return;

		for (int i = -4; i < 5; i++) {
			if (selectedItem + i < 0 || selectedItem + i >= inlen)
				continue;
			if (i == 0) {
				CustomTextWritter.drawString(g, "> " + InventoryItems.get(selectedItem + i).getName() + " <",
						(int) (312 * c.getScaleValue()), (int) ((320 + i * inventorySpacing) * c.getScaleValue()), true,
						Color.YELLOW, Assets.font28);
			} else {
				CustomTextWritter.drawString(g, InventoryItems.get(selectedItem + i).getName(),
						(int) (312 * c.getScaleValue()), (int) ((320 + i * inventorySpacing) * c.getScaleValue()), true,
						Color.WHITE, Assets.font28);
			}
		}
		if (inventoryWeight != 0) {
			Item item = InventoryItems.get(selectedItem);
			
			if(item.getType() != "axe" && item.getType() != "pickaxe") {
				CustomTextWritter.drawString(g, "Equip in Hand: <1> or <2>",
						(int)(c.getWidth() / 2 * c.getScaleValue()), (int)(630 * c.getScaleValue()), active, 
						Color.WHITE, Assets.font16);
				
				CustomTextWritter.drawString(g, "Equipment: <Enter>", 
						(int)(c.getWidth() / 2 * c.getScaleValue()), (int)(645 * c.getScaleValue()), active, 
						Color.WHITE, Assets.font16);
			}


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
				
				CustomTextWritter.drawString(g, "volume: " + Math.round(weapon.getVolume())+ " cm^3",
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
				
				CustomTextWritter.drawString(g, "Equip in Hand: <1> or <2>",
						(int)(c.getWidth() / 2 * c.getScaleValue()), (int)(640 * c.getScaleValue()), active, 
						Color.WHITE, Assets.font16);
				
				CustomTextWritter.drawString(g, "Equipment: <Enter>", 
						(int)(c.getWidth() / 2 * c.getScaleValue()), (int)(655 * c.getScaleValue()), active, 
						Color.WHITE, Assets.font16);
				
			} else if (item.getType() == "food") {
				Food food = (Food)item;
				CustomTextWritter.drawString(g, "hunger restore: " + food.getHunger(),
						(int) (c.getWidth() / 2 * c.getScaleValue()), (int) (560 * c.getScaleValue()), active,
						Color.WHITE, Assets.font21);
				CustomTextWritter.drawString(g, "thirst restore: " + food.getThirst(),
						(int) (c.getWidth() / 2 * c.getScaleValue()), (int) (590 * c.getScaleValue()), active,
						Color.WHITE, Assets.font21);
			} else if (item.getId() == Item.autoCookerV2Item.getId()) { //Auto Cooker V2 info
				if(!autoCookerV2.products.isEmpty()) {
					CustomTextWritter.drawString(g, "Collect Your Items", 
							(int) (c.getWidth() / 2 * c.getScaleValue()), (int) (560 * c.getScaleValue()), active,
							Color.WHITE, Assets.font21);
				}
				if(!autoCookerV2.currentlyCooking.isEmpty()) {
					CustomTextWritter.drawString(g, "Cook Time " + (autoCookerV2.getCookTime() - autoCookerV2.getCookTimer()) / 1000, 
							(int) (c.getWidth() / 2 * c.getScaleValue()), (int) (590 * c.getScaleValue()), active,
							Color.WHITE, Assets.font21);
				}
			}

			CustomTextWritter.drawString(g, "Total Weight: " + Math.round(inventoryWeight * 10) / 10 + " kg",
					(int) (312 * c.getScaleValue()), (int) (530 * c.getScaleValue()), active, Color.WHITE,
					Assets.font16);
			CustomTextWritter.drawString(g, "Total Volume: " + Math.round(inventoryVolume) + " cm^3",
					(int) (312 * c.getScaleValue()), (int) (555 * c.getScaleValue()), active, Color.WHITE,
					Assets.font16);
			CustomTextWritter.drawString(g, "Max Volume: " + Math.round(maxInventoryVolume) + " cm^3",
					(int) (312 * c.getScaleValue()), (int) (580 * c.getScaleValue()), active, Color.WHITE,
					Assets.font16);
		}
	}
	
	// Inventory
	public boolean addItem(Item item) {
		if(inventoryVolume + item.getVolume() > maxInventoryVolume) {
			volumeExceed = true;
			InventoryFullTimer += System.currentTimeMillis() - lastInventoryFullTimer;
			lastInventoryFullTimer = System.currentTimeMillis();
			if (InventoryFullTimer > InventoryFullCooldown) {
				MessageBox.addMessage("Inventory Too Full...");
				InventoryFullTimer= 0;
			}
			return false;
		} else {
			item.setC(c);
			if (item.isStackable()) {
				for (Item i : InventoryItems) {
					if (i.getId() == item.getId()) {
						i.setCount(i.getCount() + 1);
						inventoryWeight += i.getWeight();
						inventoryVolume += i.getVolume();
						return true;
					}
				}
				
				//add the item to inventory items if it does not yet exist in the inventory
				InventoryItems.add(item);
				if(item.getCount() == 0)
					item.setCount(item.getCount() + 1);
				inventoryWeight += item.getWeight();
				inventoryVolume += item.getVolume();
				return true;
			}
			
			InventoryItems.add(item); // adding new items that are not in the list and non-stackable items
			if(item.getCount() == 0)
				item.setCount(item.getCount() + 1);
			inventoryWeight += item.getWeight();
			inventoryVolume += item.getVolume();
			return true;
		}
	}
/*
	public void removeItem(Item item) {
		if(item.isStackable()) {
			for (Item i : InventoryItems) {
				if (i.getId() == item.getId()) {
					i.setCount(i.getCount() - 1);
					inventoryWeight -= i.getWeight();
					inventoryVolume -= i.getVolume();
					if (i.getCount() <= 0)
						i.setCount(0);
						for(int j = 0; j < InventoryItems.size(); j++) {
							if(InventoryItems.get(j) == i)
								InventoryItems.remove(j);
						}
					return;
				}
			}
		}
		InventoryItems.remove(item);
		inventoryWeight -= item.getWeight();
		inventoryVolume -= item.getVolume();
	}
	*/
	public void removeItem(Item item) {
		
		for(int i = 0; i < InventoryItems.size(); i++) {
			if(InventoryItems.get(i).equals(item)) {
				if(!InventoryItems.get(i).isStackable()) {
					
					if(Player.getPlayerData().getHands().leftHand != null && Player.getPlayerData().getHands().leftHand.equals(item)) 
						Player.getPlayerData().getHands().leftHand = null;
					
					if(Player.getPlayerData().getHands().rightHand != null && Player.getPlayerData().getHands().rightHand.equals(item)) 
						Player.getPlayerData().getHands().rightHand = null;
					
					if(Player.getPlayerData().getEquipment().helmet != null && Player.getPlayerData().getEquipment().helmet.equals(item)) {
						Player.getPlayerData().setResistance(Player.getPlayerData().getResistance()-Player.getPlayerData().getEquipment().helmet.getResistance());
						Player.getPlayerData().setIntimidation(Player.getPlayerData().getIntimidation()-Player.getPlayerData().getEquipment().helmet.getIntimidation());
						Player.getPlayerData().getEquipment().helmet = null;
					}
					if(Player.getPlayerData().getEquipment().chest != null && Player.getPlayerData().getEquipment().chest.equals(item)) {
						Player.getPlayerData().setResistance(Player.getPlayerData().getResistance()-Player.getPlayerData().getEquipment().chest.getResistance());
						Player.getPlayerData().setIntimidation(Player.getPlayerData().getIntimidation()-Player.getPlayerData().getEquipment().chest.getIntimidation());
						Player.getPlayerData().getEquipment().chest = null;
					}
					if(Player.getPlayerData().getEquipment().leg != null && Player.getPlayerData().getEquipment().leg.equals(item)) {
						Player.getPlayerData().setResistance(Player.getPlayerData().getResistance()-Player.getPlayerData().getEquipment().leg.getResistance());
						Player.getPlayerData().setIntimidation(Player.getPlayerData().getIntimidation()-Player.getPlayerData().getEquipment().leg.getIntimidation());
						Player.getPlayerData().getEquipment().leg = null;
					}
					if(Player.getPlayerData().getEquipment().boot != null && Player.getPlayerData().getEquipment().boot.equals(item)) {
						Player.getPlayerData().setResistance(Player.getPlayerData().getResistance()-Player.getPlayerData().getEquipment().boot.getResistance());
						Player.getPlayerData().setIntimidation(Player.getPlayerData().getIntimidation()-Player.getPlayerData().getEquipment().boot.getIntimidation());
						Player.getPlayerData().getEquipment().boot = null;
						
					}
					if(Player.getPlayerData().getEquipment().gauntlet != null && Player.getPlayerData().getEquipment().gauntlet.equals(item)) { 
						Player.getPlayerData().setResistance(Player.getPlayerData().getResistance()-Player.getPlayerData().getEquipment().gauntlet.getResistance());
						Player.getPlayerData().setIntimidation(Player.getPlayerData().getIntimidation()-Player.getPlayerData().getEquipment().gauntlet.getIntimidation());
						Player.getPlayerData().getEquipment().gauntlet = null;
					
					}
					InventoryItems.remove(i);
					inventoryWeight -= item.getWeight();
					inventoryVolume -= item.getVolume();
					
					return;
					
				} else {
					if(item.getCount() - 1 <= 0) {
						
						if(Player.getPlayerData().getHands().leftHand != null && Player.getPlayerData().getHands().leftHand.equals(item)) 
							Player.getPlayerData().getHands().leftHand = null;
						
						if(Player.getPlayerData().getHands().rightHand != null && Player.getPlayerData().getHands().rightHand.equals(item)) 
							Player.getPlayerData().getHands().rightHand = null;
						
						InventoryItems.remove(i);
						inventoryWeight -= item.getWeight();
						inventoryVolume -= item.getVolume();
						
					} else {
						
						item.setCount(item.getCount() - 1);
						inventoryWeight -= (item.getWeight());
						inventoryVolume -= (item.getVolume());
						
					}
				}
			}  else if(InventoryItems.get(i).getId() == item.getId() && item.isStackable()) {
				
				InventoryItems.get(i).setCount(InventoryItems.get(i).getCount() - 1);
				inventoryWeight -= InventoryItems.get(i).getWeight();
				inventoryVolume -= InventoryItems.get(i).getVolume();
				if (InventoryItems.get(i).getCount() <= 0) {
					InventoryItems.get(i).setCount(0);
					for(int j = 0; j < InventoryItems.size(); j++) {
						if(InventoryItems.get(j) == InventoryItems.get(i))
							InventoryItems.remove(j);
					}
				}
				return;
				
			} /*else if(InventoryItems.get(i).getId() == item.getId() && !item.isStackable()) {
			
			if(Player.getPlayerData().getHands().leftHand != null && Player.getPlayerData().getHands().leftHand.equals(InventoryItems.get(i))) 
				Player.getPlayerData().getHands().leftHand = null;
			
			if(Player.getPlayerData().getHands().rightHand != null && Player.getPlayerData().getHands().rightHand.equals(InventoryItems.get(i))) 
				Player.getPlayerData().getHands().rightHand = null;
			
			if(Player.getPlayerData().getEquipment().helmet != null && Player.getPlayerData().getEquipment().helmet.equals(InventoryItems.get(i))) {
				Player.getPlayerData().setResistance(Player.getPlayerData().getResistance()-Player.getPlayerData().getEquipment().helmet.getResistance());
				Player.getPlayerData().setIntimidation(Player.getPlayerData().getIntimidation()-Player.getPlayerData().getEquipment().helmet.getIntimidation());
				Player.getPlayerData().getEquipment().helmet = null;
			}
			if(Player.getPlayerData().getEquipment().chest != null && Player.getPlayerData().getEquipment().chest.equals(InventoryItems.get(i))) {
				Player.getPlayerData().setResistance(Player.getPlayerData().getResistance()-Player.getPlayerData().getEquipment().chest.getResistance());
				Player.getPlayerData().setIntimidation(Player.getPlayerData().getIntimidation()-Player.getPlayerData().getEquipment().chest.getIntimidation());
				Player.getPlayerData().getEquipment().chest = null;
			}
			if(Player.getPlayerData().getEquipment().leg != null && Player.getPlayerData().getEquipment().leg.equals(InventoryItems.get(i))) {
				Player.getPlayerData().setResistance(Player.getPlayerData().getResistance()-Player.getPlayerData().getEquipment().leg.getResistance());
				Player.getPlayerData().setIntimidation(Player.getPlayerData().getIntimidation()-Player.getPlayerData().getEquipment().leg.getIntimidation());
				Player.getPlayerData().getEquipment().leg = null;
			}
			if(Player.getPlayerData().getEquipment().boot != null && Player.getPlayerData().getEquipment().boot.equals(InventoryItems.get(i))) {
				Player.getPlayerData().setResistance(Player.getPlayerData().getResistance()-Player.getPlayerData().getEquipment().boot.getResistance());
				Player.getPlayerData().setIntimidation(Player.getPlayerData().getIntimidation()-Player.getPlayerData().getEquipment().boot.getIntimidation());
				Player.getPlayerData().getEquipment().boot = null;
				
			}
			if(Player.getPlayerData().getEquipment().gauntlet != null && Player.getPlayerData().getEquipment().gauntlet.equals(InventoryItems.get(i))) { 
				Player.getPlayerData().setResistance(Player.getPlayerData().getResistance()-Player.getPlayerData().getEquipment().gauntlet.getResistance());
				Player.getPlayerData().setIntimidation(Player.getPlayerData().getIntimidation()-Player.getPlayerData().getEquipment().gauntlet.getIntimidation());
				Player.getPlayerData().getEquipment().gauntlet = null;
			
			}
			
			InventoryItems.remove(i);
			inventoryWeight -= InventoryItems.get(i).getId();
			inventoryVolume -= InventoryItems.get(i).getId();
			return;
			
		}*/
		}
		
	}

	public int getItemCount(Item item) {
	
		for (Item i : InventoryItems) {
			if (i.getId() == item.getId()) {
				return i.getCount();
			}
		}
		return 0;
	
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
		return maxInventoryVolume;
	}
	public void setInventoryCapacity(double inventoryCapacity) {
		this.maxInventoryVolume = inventoryCapacity;
	}
}