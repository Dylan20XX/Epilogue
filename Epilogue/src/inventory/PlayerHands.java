package inventory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

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
import items.Torch;
import items.WaterContainer;
import items.Weapon;

public class PlayerHands {

	public Item leftHand;
	public Item rightHand;
	public String currentHand = "left";
	
	private static boolean noHelp = false;

	private ControlCenter c;
	private Inventory inventory;

	public PlayerHands(ControlCenter c, Inventory inventory) {
		this.c = c;
		this.inventory = inventory;

	}

	public void tick() {
		if(leftHand != null && leftHand.getType().equals("torch"))
			leftHand.tick();
		if(rightHand != null && rightHand.getType().equals("torch"))
			rightHand.tick();
		if (c.getKeyManager().keyJustPressed(KeyEvent.VK_V)) {
			if (currentHand.equals("left"))
				currentHand = "right";
			else
				currentHand = "left";
		}
		/*
		 * if(!inventory.isActive()) {
		 * if(c.getKeyManager().keyJustPressed(KeyEvent.VK_Q)) {
		 * if(inventory.getInventoryWeight() != 0) { if(currentHand.equals("right")) {
		 * inventory.setInventoryWeight(inventory.getInventoryWeight() -
		 * rightHand.getWeight()); rightHand.setCount(rightHand.getCount() - 1);
		 * 
		 * //rightHand.createNew((int)(player.getX()), (int)(player.getY()));
		 * 
		 * if (rightHand.getCount() <= 0) { inventory.InventoryItems.remove(rightHand);
		 * rightHand = null; } } if(currentHand.equals("left")) {
		 * inventory.setInventoryWeight(inventory.getInventoryWeight() -
		 * leftHand.getWeight()); leftHand.setCount(leftHand.getCount() - 1);
		 * 
		 * //leftHand.createNew((int)(player.getX()), (int)(player.getY()));
		 * 
		 * if (leftHand.getCount() <= 0) { inventory.InventoryItems.remove(leftHand);
		 * leftHand = null; } } } } }
		 */
		if(c.getGameState().getWorldGenerator().isChestActive()) {
			
			if (rightHand != null) {
				if (rightHand.getCount() <= 0) {
					rightHand.setItemEquipped(false);
					rightHand = null;
				}
			}
			if (leftHand != null) {
				if (leftHand.getCount() <= 0) {
					leftHand.setItemEquipped(false);
					leftHand = null;
				}
			}
			
		}
		
		if (inventory.isActive()) {

			if (rightHand != null) {
				if (rightHand.getCount() <= 0) {
					rightHand = null;
				}
			}
			if (leftHand != null) {
				if (leftHand.getCount() <= 0) {
					leftHand = null;
				}
			}

			if (c.getKeyManager().keyJustPressed(KeyEvent.VK_1)) {

				if(inventory.InventoryItems.get(inventory.selectedItem).getType().equals("weapon") || 
						inventory.InventoryItems.get(inventory.selectedItem).getType().equals("axe") ||
						inventory.InventoryItems.get(inventory.selectedItem).getType().equals("pickaxe") || 
						inventory.InventoryItems.get(inventory.selectedItem).getType().equals("shovel")) {
					if(inventory.InventoryItems.get(inventory.selectedItem).getName().equals("dark saber")) 
						AudioPlayer.playAudio("audio/dark_saber_draw.wav");
					else 
						AudioPlayer.playAudio("audio/swordUp.wav");
				} else if(inventory.InventoryItems.get(inventory.selectedItem).getType().equals("ranged")) {
					AudioPlayer.playAudio("audio/gunUp.wav");
				}
				
				if (inventory.InventoryItems.get(inventory.selectedItem).equals(rightHand)) {
					Item temp = rightHand;
					rightHand = leftHand;
					leftHand = temp;
					leftHand.setItemEquipped(true);
					
				}
				else if(inventory.InventoryItems.get(inventory.selectedItem).equals(leftHand)) {
					leftHand = null;
				}
				else {
					if(leftHand != null)
						leftHand.setItemEquipped(false);
					leftHand = inventory.InventoryItems.get(inventory.selectedItem);
					leftHand.setItemEquipped(true);
				} 
				
			}
			if (c.getKeyManager().keyJustPressed(KeyEvent.VK_2)) {

				if(inventory.InventoryItems.get(inventory.selectedItem).getType().equals("weapon") || 
						inventory.InventoryItems.get(inventory.selectedItem).getType().equals("axe") ||
						inventory.InventoryItems.get(inventory.selectedItem).getType().equals("pickaxe") || 
						inventory.InventoryItems.get(inventory.selectedItem).getType().equals("shovel")) {
					if(inventory.InventoryItems.get(inventory.selectedItem).getName().equals("dark saber")) 
						AudioPlayer.playAudio("audio/dark_saber_draw.wav");
					else 
						AudioPlayer.playAudio("audio/swordUp.wav");
				} else if(inventory.InventoryItems.get(inventory.selectedItem).getType().equals("ranged")) {
					AudioPlayer.playAudio("audio/gunUp.wav");
				}
				
				if (inventory.InventoryItems.get(inventory.selectedItem).equals(leftHand)) {
					Item temp = leftHand;
					leftHand = rightHand;
					rightHand = temp;
					rightHand.setItemEquipped(true);
				}
				else if(inventory.InventoryItems.get(inventory.selectedItem).equals(rightHand)) {
					rightHand = null;
				}
				else {
					if(rightHand != null)
						rightHand.setItemEquipped(false);
					rightHand = inventory.InventoryItems.get(inventory.selectedItem);
					rightHand.setItemEquipped(true);
				}

			}

		} else {

			if (c.getKeyManager().keyJustPressed(KeyEvent.VK_1)) {
				
				currentHand = "left";
				if(getHand() != null && (getHand().getType().equals("weapon") || getHand().getType().equals("axe")
						|| getHand().getType().equals("pickaxe") || getHand().getType().equals("shovel"))) {
					if(inventory.InventoryItems.get(inventory.selectedItem).getName().equals("dark saber")) 
						AudioPlayer.playAudio("audio/dark_saber_draw.wav");
					else 
						AudioPlayer.playAudio("audio/swordUp.wav");
				} else if(getHand() != null && getHand().getType().equals("ranged")) {
					AudioPlayer.playAudio("audio/gunUp.wav");
				}

			}

			else if (c.getKeyManager().keyJustPressed(KeyEvent.VK_2)) {

				currentHand = "right";
				if(getHand() != null && (getHand().getType().equals("weapon") || getHand().getType().equals("axe")
						|| getHand().getType().equals("pickaxe") || getHand().getType().equals("shovel"))) {
					if(inventory.InventoryItems.get(inventory.selectedItem).getName().equals("dark saber")) 
						AudioPlayer.playAudio("audio/dark_saber_draw.wav");
					else 
						AudioPlayer.playAudio("audio/swordUp.wav");
				} else if(getHand() != null && getHand().getType().equals("ranged")) {
					AudioPlayer.playAudio("audio/gunUp.wav");
				}
				
			}

			else if (c.getKeyManager().keyJustPressed(KeyEvent.VK_Q) && getHand() != null) {

				if (!inventory.isActive() && !Player.getPlayerData().getHandCraft().isActive()
						&& !Player.getPlayerData().isChestActive() && !Player.getPlayerData().isMetallicOvenActive()) {
					getHand().setItemEquipped(false);
					inventory.dropItem(getHand(), true);
					if(getHand().getCount() <= 0)
						setHand(null);
				}
				
			}

		}
	}
	
	public void displayText(Graphics g) {
		
		if(noHelp)
            return;
		
		if(getHand() != null) {
			
			if(getHand().getType().equals("water container")) {
	
				CustomTextWritter.drawString(g, "left press to drink", 50, c.getHeight() - 120, false, Color.white, Assets.font16);
				boolean canPurify = false;
				
				for(int i = 0; i < c.getGameState().getWorldGenerator().getEntityManager().entitiesInBound.size(); i++) {
					if(c.getMouseManager().mouseBound().intersects(c.getGameState().getWorldGenerator().getEntityManager().entitiesInBound.get(i).getBounds())) {
						canPurify = true;
						break;
					}
				}
				
				if(((WaterContainer)getHand()).isPurified() || ((WaterContainer)getHand()).getCapacity() <= 0)
					canPurify = false;
				
				if(canPurify) {
					CustomTextWritter.drawString(g, "right press to purify", 50, c.getHeight() - 140, false, Color.white, Assets.font16);
				}else {
					CustomTextWritter.drawString(g, "right press to fill", 50, c.getHeight() - 140, false, Color.white, Assets.font16);
				}
				
			} else if(getHand().getType().equals("food")) {
				CustomTextWritter.drawString(g, "left press to eat", 50, c.getHeight() - 120, false, Color.white, Assets.font16);
			} else if(getHand().getType().equals("ranged")) {
				Ranged ranged = (Ranged)getHand();
				
				if(ranged.loading) {
					CustomTextWritter.drawString(g, "reloading...", 50, c.getHeight() - 120, false, Color.white, Assets.font16);
				} else if(ranged.ammoCurrent <= 0 && Player.getPlayerData().running) {
					CustomTextWritter.drawString(g, "press <R> to reload (stop running)...", 50, c.getHeight() - 120, false, Color.white, Assets.font16);
				}
				else if(ranged.ammoCurrent <= 0) {
					CustomTextWritter.drawString(g, "press <R> to reload...", 50, c.getHeight() - 120, false, Color.white, Assets.font16);
				}
			} else {
				CustomTextWritter.drawString(g, "<E> open inventory", 50, c.getHeight() - 140, false, Color.white, Assets.font16);
				CustomTextWritter.drawString(g, "<C> open hand craft", 50, c.getHeight() - 120, false, Color.white, Assets.font16);
			}
			
		} else {
			
			CustomTextWritter.drawString(g, "<E> open inventory", 50, c.getHeight() - 140, false, Color.white, Assets.font16);
			CustomTextWritter.drawString(g, "<C> open hand craft", 50, c.getHeight() - 120, false, Color.white, Assets.font16);
			
		}
	}

	public void render(Graphics g) {
		
		displayText(g);
		
		g.setColor(Color.orange);

		if (currentHand.equals("left")) {
			g.drawRect(40, c.getHeight() - 110, 70, 70);
		} else {
			g.drawRect(110, c.getHeight() - 110, 70, 70);
		}

		g.drawImage(Assets.mainHand, 50, c.getHeight() - 100, 50, 50, null);
		g.drawImage(Assets.offHand, 120, c.getHeight() - 100, 50, 50, null);

		if (leftHand != null) {
			g.drawImage(leftHand.getTexture(), 35, c.getHeight() - 115, 80, 80, null);
			if (leftHand.isStackable())
				CustomTextWritter.drawString(g, leftHand.getCount() + "", 100, c.getHeight() - 50, true, Color.white,
						Assets.font28);
			if(leftHand.getType().equals("weapon")) {
				Weapon weapon = (Weapon)leftHand;
				g.setColor(Color.DARK_GRAY);
				g.fillRect(50, c.getHeight() - 45, 50, 5);
				if(weapon.getEndurancePercentage() < 33) 
					g.setColor(Color.red);
				else if(weapon.getEndurancePercentage() < 33) 
					g.setColor(Color.orange);
				else 
					g.setColor(Color.green);
				g.fillRect(50, c.getHeight() - 45, weapon.getEndurancePercentage()/2, 5);
			} else if(leftHand.getType() == "helmet" || leftHand.getType() == "chest" || leftHand.getType() == "leg"
					|| leftHand.getType() == "boots") {
				Armor armor = (Armor)leftHand;
				g.setColor(Color.DARK_GRAY);
				g.fillRect(50, c.getHeight() - 45, 50, 5);
				if(armor.getEndurancePercentage() < 33) 
					g.setColor(Color.red);
				else if(armor.getEndurancePercentage() < 33) 
					g.setColor(Color.orange);
				else 
					g.setColor(Color.green);
				g.fillRect(50, c.getHeight() - 45, armor.getEndurancePercentage()/2, 5);
			} else if(leftHand.getType().equals("axe") || leftHand.getType().equals("pickaxe")) {
				Tool tool = (Tool)leftHand;
				g.setColor(Color.DARK_GRAY);
				g.fillRect(50, c.getHeight() - 45, 50, 5);
				if(tool.getEndurancePercentage() < 33) 
					g.setColor(Color.red);
				else if(tool.getEndurancePercentage() < 33) 
					g.setColor(Color.orange);
				else 
					g.setColor(Color.green);
				g.fillRect(50, c.getHeight() - 45, (int) tool.getEndurancePercentage()/2, 5);
			} else if(leftHand.getType().equals("water container")) {
				WaterContainer container = (WaterContainer)leftHand;
				g.setColor(Color.DARK_GRAY);
				g.fillRect(50, c.getHeight() - 45, 50, 5);
				if(container.getCapacityPercentage() < 33) 
					g.setColor(Color.red);
				else if(container.getCapacityPercentage() < 50) 
					g.setColor(Color.orange);
				else 
					g.setColor(Color.green);
				g.fillRect(50, c.getHeight() - 45, (int) container.getCapacityPercentage()/2, 5);
				
			} else if(leftHand.getType().equals("food")) {
				Food food = (Food)leftHand;
				if(!food.getFood().equals("rotten")) {
					g.setColor(Color.DARK_GRAY);
					g.fillRect(50, c.getHeight() - 45, 50, 5);
					if(food.getFreshnessPercentage() < 33) 
						g.setColor(Color.red);
					else if(food.getFreshnessPercentage() < 33) 
						g.setColor(Color.orange);
					else 
						g.setColor(Color.green);
					g.fillRect(50, c.getHeight() - 45, (int) food.getFreshnessPercentage()/2, 5);
				}
				
			} else if(leftHand.getType().equals("ranged")) {
				Ranged ranged = (Ranged)leftHand;
				g.setColor(Color.DARK_GRAY);
				g.fillRect(50, c.getHeight() - 45, 50, 5);
				g.setColor(Color.green);
					
				g.fillRect(50, c.getHeight() - 45, (int) ranged.getAmmoPercentage()/2, 5);
			} else if(leftHand.getType().equals("torch")) {
				Torch torch = (Torch)leftHand;
				g.setColor(Color.DARK_GRAY);
				g.fillRect(50, c.getHeight() - 45, 50, 5);
				if(torch.getFuelPercentage() < 33) 
					g.setColor(Color.red);
				else if(torch.getFuelPercentage() < 33) 
					g.setColor(Color.orange);
				else 
					g.setColor(Color.green);
				g.fillRect(50, c.getHeight() - 45, torch.getFuelPercentage()/2, 5);
			}
		}
		if (rightHand != null) {
			g.drawImage(rightHand.getTexture(), 105, c.getHeight() - 115, 80, 80, null);
			if (rightHand.isStackable())
				CustomTextWritter.drawString(g, rightHand.getCount() + "", 170, c.getHeight() - 50, true, Color.white,
						Assets.font28);
			if(rightHand.getType().equals("weapon")) {
				Weapon weapon = (Weapon)rightHand;
				g.setColor(Color.DARK_GRAY);
				g.fillRect(120, c.getHeight() - 45, 50, 5);
				if(weapon.getEndurancePercentage() < 33) 
					g.setColor(Color.red);
				else if(weapon.getEndurancePercentage() < 33) 
					g.setColor(Color.orange);
				else 
					g.setColor(Color.green);
				g.fillRect(120, c.getHeight() - 45, weapon.getEndurancePercentage()/2, 5);
			} else if(rightHand.getType() == "helmet" || rightHand.getType() == "chest" || rightHand.getType() == "leg"
					|| rightHand.getType() == "boots") {
				Armor armor = (Armor)rightHand;
				g.setColor(Color.DARK_GRAY);
				g.fillRect(120, c.getHeight() - 45, 50, 5);
				if(armor.getEndurancePercentage() < 33) 
					g.setColor(Color.red);
				else if(armor.getEndurancePercentage() < 33) 
					g.setColor(Color.orange);
				else 
					g.setColor(Color.green);
				g.fillRect(120, c.getHeight() - 45, armor.getEndurancePercentage()/2, 5);
			} else if(rightHand.getType().equals("axe") || rightHand.getType().equals("pickaxe")) {
				Tool tool = (Tool)rightHand;
				g.setColor(Color.DARK_GRAY);
				g.fillRect(120, c.getHeight() - 45, 50, 5);
				if(tool.getEndurancePercentage() < 33) 
					g.setColor(Color.red);
				else if(tool.getEndurancePercentage() < 33) 
					g.setColor(Color.orange);
				else 
					g.setColor(Color.green);
				g.fillRect(120, c.getHeight() - 45, (int) tool.getEndurancePercentage()/2, 5);
			} else if(rightHand.getType().equals("water container")) {
				WaterContainer container = (WaterContainer)rightHand;
				g.setColor(Color.DARK_GRAY);
				g.fillRect(120, c.getHeight() - 45, 50, 5);
				if(container.getCapacityPercentage() < 33) 
					g.setColor(Color.red);
				else if(container.getCapacityPercentage() < 50) 
					g.setColor(Color.orange);
				else 
					g.setColor(Color.green);
				g.fillRect(120, c.getHeight() - 45, (int) container.getCapacityPercentage()/2, 5);
				
			} else if(rightHand.getType().equals("food")) {
				Food food = (Food)rightHand;
				if(!food.getFood().equals("rotten")) {
					g.setColor(Color.DARK_GRAY);
					g.fillRect(120, c.getHeight() - 45, 50, 5);
					if(food.getFreshnessPercentage() < 33) 
						g.setColor(Color.red);
					else if(food.getFreshnessPercentage() < 33) 
						g.setColor(Color.orange);
					else 
						g.setColor(Color.green);
					g.fillRect(120, c.getHeight() - 45, (int) food.getFreshnessPercentage()/2, 5);
				}
			} else if(rightHand.getType().equals("ranged")) {
				Ranged ranged = (Ranged)rightHand;
				g.setColor(Color.DARK_GRAY);
				g.fillRect(120, c.getHeight() - 45, 50, 5);
				g.setColor(Color.green);
					
				g.fillRect(120, c.getHeight() - 45, (int) ranged.getAmmoPercentage()/2, 5);
				
			} else if(rightHand.getType().equals("torch")) {
				Torch torch = (Torch)rightHand;
				g.setColor(Color.DARK_GRAY);
				g.fillRect(50, c.getHeight() - 45, 50, 5);
				if(torch.getFuelPercentage() < 33) 
					g.setColor(Color.red);
				else if(torch.getFuelPercentage() < 33) 
					g.setColor(Color.orange);
				else 
					g.setColor(Color.green);
				g.fillRect(50, c.getHeight() - 45, torch.getFuelPercentage()/2, 5);
			}
		}
	}

	public BufferedImage getCurrentItemGraphics() {

		if (currentHand.equals("right")) {
			if (rightHand != null)
				return rightHand.getTexture();
			else
				return null;
		} else {
			if (leftHand != null)
				return leftHand.getTexture();
			else
				return null;
		}
	}

	public BufferedImage getOffHandItemGraphics() {

		if (currentHand.equals("left")) {
			if (rightHand != null)
				return rightHand.getTexture();
			else
				return null;
		} else {
			if (leftHand != null)
				return leftHand.getTexture();
			else
				return null;
		}
	}

	public Item getHand() {

		if (currentHand.equals("left")) {
			return leftHand;
		} else {
			return rightHand;
		}

	}

	public void setHand(Item item) {

		if (currentHand.equals("left")) {
			leftHand = item;
		} else {
			rightHand = item;
		}

	}
	
	public boolean isRangedActive() {
		
		if(getHand() == null)
			return false;
		if(getHand().getType().equals("ranged"))
			return true;
		
		return false;
		
	}
	
	public static void changeHelp() {
        noHelp = !noHelp;
	}

}