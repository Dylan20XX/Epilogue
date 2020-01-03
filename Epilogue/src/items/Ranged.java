package items;

import java.awt.image.BufferedImage;

import audio.AudioPlayer;
import creatures.Player;
import graphics.Assets;
import graphics.CT;
import inventory.MessageBox;
import structureInventory.ChestInventory;

public class Ranged extends Item{

	public static boolean stackable = false;
	public static String type = "ranged";
	
	public int reloadCooldown;
	public int intimidation;
	public double aSpeed;
	public int ammoMax;
	public int ammoCurrent;
	public String refillMaterial;
	public boolean loading = false;
	public String enhancement;
	public double accuracy = 0;
	
	// Ranged Weapons
	public static Ranged glock = new Ranged(Assets.glock, "glock", 150, 3, 95, 1000, 0, 8, 2500, "AMM1D ammo", Math.PI/32, 28);
	public static Ranged megashakalaka = new Ranged(Assets.megashakalaka, "megashakalaka", 151, 15, 30, 80, 15, 250, 8000, "XM214 ammo", Math.PI/24, 135);
	public static Ranged flamethrower = new Ranged(Assets.flamethrower, "flamethrower", 152, 6.8, 15, 55, 5, 200, 6000, "fuel tank", Math.PI/10, 75);
	public static Ranged woodenBow = new Ranged(Assets.woodenBow, "wooden bow", 153, 1.6, 150, 3500, 3, 1, 3500, "arrow", Math.PI/35, 40);
	public static Ranged bronzeBow = new Ranged(Assets.bronzeBow, "bronze bow", 154, 2.3, 150, 2000, 3, 1, 2000, "arrow", Math.PI/40, 40);
	public static Ranged zincBow = new Ranged(Assets.zincBow, "zinc bow", 155, 3.2, 150, 1500, 3, 1, 1500, "arrow", Math.PI/45, 40);
	public static Ranged ironBow = new Ranged(Assets.ironBow, "iron bow", 156, 6.1, 150, 1250, 3, 1, 1250, "arrow", Math.PI/80, 50);
	public static Ranged ravager = new Ranged(Assets.ravager, "ravager", 157, 4.6, 150, 1000, 3, 1, 1100, "arrow", 0, 60);
	//public static Item rustyAssultRifle = new Ranged(Assets.woodenBow, "rusty assult rifle", 154, 6.5, 55, 85, 5, 35, 85);
	public static Ranged desertEagle = new Ranged(Assets.desertEagle, "desert eagle", 158, 2.5, 120, 800, 3, 12, 1500, "AMM1D ammo", Math.PI/42, 23);
	public static Ranged AWP = new Ranged(Assets.woodenBow, "AWP", 159, 15, 350, 3500, 10, 4, 8000, "AMM1D ammo", 0, 140);
	public static Ranged pulseRifle = new Ranged(Assets.pulseRifle, "pulse rifle", 160, 10, 100, 205, 10, 30, 1500, "none", Math.PI/80, 115);

	public Ranged(BufferedImage texture, String name, int id, double weight, int damage,
			double aSpeed, int intimidation, int ammoMax, int loadSpeed, String refillMaterial, double accuracy, int volume) {
		
		super(texture, name, id, stackable, type, weight, damage, volume);
		
		this.aSpeed = aSpeed;
		this.intimidation = intimidation;
		this.name = name;
		this.ammoMax = ammoMax;
		ammoCurrent = ammoMax;
		reloadCooldown = loadSpeed;
		this.refillMaterial = refillMaterial;
		this.accuracy = accuracy;
		
		enhancement = "";

	}
	
	public void reload() {
		
		if(name.equals("pulse rifle")) {
			AudioPlayer.playAudio("audio/lazer_charge_up.wav");
			loading = true;
			new java.util.Timer().schedule(new java.util.TimerTask() {
				@Override
				public void run() {
					
					ammoCurrent = ammoMax;
					
					loading = false;
	
				}
			}, reloadCooldown);
			
		}
		else if(!loading) {
			
			loading = true;
			
			boolean found = false;
			
			for(int i = 0; i < Player.getPlayerData().getInventory().InventoryItems.size(); i++) {
				
				Item item = Player.getPlayerData().getInventory().InventoryItems.get(i);
				
				if(item.getName().equals(refillMaterial)) {
					
					if(name.equals("ravager")) {
						if(item.getCount() >= 3) {
							Player.getPlayerData().getInventory().removeItem(item);
							Player.getPlayerData().getInventory().removeItem(item);
							Player.getPlayerData().getInventory().removeItem(item);
							found = true;
						} else {
							break;
						}
					} 
					
					Player.getPlayerData().getInventory().removeItem(item);
					found = true;
					
					break;
					
				}
				
			}
			
			if(!found) {
				if(!found) {
					if (name.equals("wooden bow") || name.equals("bronze bow") || name.equals("zinc bow") || name.equals("iron bow") || name.equals("ravager")) {
						MessageBox.addMessage("out of arrows...");
					} else {
						MessageBox.addMessage("out of ammo...");
					}
				} else {
					
					if (name.equals("wooden bow") || name.equals("bronze bow") || name.equals("zinc bow") || name.equals("iron bow") || name.equals("ravager")) {
						AudioPlayer.playAudio("audio/bowReload.wav");
						
					} else {
						AudioPlayer.playAudio("audio/reload.wav");
					}
					
					ammoCurrent = ammoMax;
				}
				
				loading = false;
				
				return;
			} else {
				new java.util.Timer().schedule(new java.util.TimerTask() {
					@Override
					public void run() {
						
						if (name.equals("wooden bow") || name.equals("bronze bow") || name.equals("zinc bow") || name.equals("iron bow") || name.equals("ravager")) {
							AudioPlayer.playAudio("audio/bowReload.wav");
							
						} else {
							AudioPlayer.playAudio("audio/reload.wav");
						}
						
						ammoCurrent = ammoMax;
						
						loading = false;
		
					}
				}, reloadCooldown);
			}
			
		}
		
	}
	
	public Item createNew(int x, int y) {
		Item e = new Ranged(texture, name, id, weight, damage, aSpeed, intimidation, ammoMax, reloadCooldown, refillMaterial, accuracy, volume);
		
		Ranged i = (Ranged) e;
		int rand = CT.random(1, 16);
		if (rand == 1) {
			i.aSpeed += aSpeed / CT.random(6, 15);
			i.accuracy -= Math.PI/(Math.random()*25 + 25);
			i.enhancement = "shameful";
		} else if (rand == 2) {
			i.aSpeed += aSpeed / CT.random(8, 20);
			i.reloadCooldown += reloadCooldown / CT.random(5, 10);
			i.enhancement = "jamming";
		} else if (rand == 3) {
			i.weight += weight / CT.random(10, 30);
			i.aSpeed += aSpeed / CT.random(8, 20);
			i.enhancement = "heavy";
		} else if (rand == 4) {
			i.aSpeed -= aSpeed / CT.random(7, 15);
			i.reloadCooldown -= reloadCooldown / CT.random(5, 10);
			i.enhancement = "revengeful";
		} else if (rand == 5) {
			i.aSpeed -= aSpeed / CT.random(8, 18);
			i.accuracy += Math.PI/(Math.random()*25 + 25);
			i.enhancement = "piercing";
		} else if (rand == 6) {
			i.reloadCooldown -= reloadCooldown / CT.random(4, 10);
			i.enhancement = "quickdraw";
		} else if (rand == 7) {
			i.aSpeed -= aSpeed / CT.random(8, 15);
			i.enhancement = "fast";
		} else if (rand == 8) {
			i.volume += volume / CT.random(10, 15);
			i.aSpeed += aSpeed / CT.random(8, 15);
			i.enhancement = "massive";
		} else {
			i.enhancement = "";
		}
		
		i.setAmmoCurrent(0);
		
		i.setPosition(x, y);
		return (Item)i;
	}

	public Item createNew(int count) { // testing
		Item e = new Ranged(texture, name, id, weight, damage, aSpeed, intimidation, ammoMax, reloadCooldown, refillMaterial, accuracy, volume);
		
		Ranged i = (Ranged) e;
		int rand = CT.random(1, 18);
		if (rand == 1) {
			i.aSpeed += aSpeed / CT.random(6, 15);
			i.accuracy -= Math.PI/(Math.random()*25 + 25);
			i.enhancement = "shameful";
		} else if (rand == 2) {
			i.aSpeed += aSpeed / CT.random(8, 20);
			i.reloadCooldown += reloadCooldown / CT.random(5, 10);
			i.enhancement = "jamming";
		} else if (rand == 3) {
			i.weight += weight / CT.random(10, 30);
			i.aSpeed += aSpeed / CT.random(8, 20);
			i.enhancement = "heavy";
		} else if (rand == 4) {
			i.aSpeed -= aSpeed / CT.random(7, 15);
			i.reloadCooldown -= reloadCooldown / CT.random(5, 10);
			i.enhancement = "revengeful";
		} else if (rand == 5) {
			i.accuracy += Math.PI/(Math.random()*25 + 25);
			i.enhancement = "piercing";
		} else if (rand == 6) {
			i.reloadCooldown -= reloadCooldown / CT.random(4, 10);
			i.enhancement = "quickdraw";
		} else if (rand == 7) {
			i.aSpeed -= aSpeed / CT.random(8, 15);
			i.enhancement = "fast";
		} else if (rand == 8) {
			i.volume += volume / CT.random(10, 15);
			i.aSpeed += aSpeed / CT.random(8, 15);
			i.enhancement = "massive";
		} else {
			i.enhancement = "";
		}
		
		i.setPickedUp(true);
		i.setCount(count);
		return i;
	}
	
	public void createNew(ChestInventory chest) {
		
		Item e = new Ranged(texture, name, id, weight, damage, aSpeed, intimidation, ammoMax, reloadCooldown, refillMaterial, accuracy, volume);
		
		Ranged i = (Ranged) e;
		int rand = CT.random(1, 18);
		if (rand == 1) {
			i.aSpeed += aSpeed / CT.random(6, 15);
			i.accuracy -= Math.PI/(Math.random()*25 + 25);
			i.enhancement = "shameful";
		} else if (rand == 2) {
			i.aSpeed += aSpeed / CT.random(8, 20);
			i.reloadCooldown += reloadCooldown / CT.random(5, 10);
			i.enhancement = "jamming";
		} else if (rand == 3) {
			i.weight += weight / CT.random(10, 30);
			i.aSpeed += aSpeed / CT.random(8, 20);
			i.enhancement = "heavy";
		} else if (rand == 4) {
			i.aSpeed -= aSpeed / CT.random(7, 15);
			i.reloadCooldown -= reloadCooldown / CT.random(5, 10);
			i.enhancement = "revengeful";
		} else if (rand == 5) {
			i.accuracy += Math.PI/(Math.random()*25 + 25);
			i.reloadCooldown -= reloadCooldown / CT.random(6, 12);
			i.enhancement = "sniping";
		} else if (rand == 6 || rand == 9) {
			i.reloadCooldown -= reloadCooldown / CT.random(4, 10);
			i.enhancement = "quickdraw";
		} else if (rand == 7) {
			i.aSpeed -= aSpeed / CT.random(8, 15);
			i.enhancement = "piercing";
		} else if (rand == 8) {
			i.volume += volume / CT.random(10, 15);
			i.aSpeed += aSpeed / CT.random(8, 15);
			i.enhancement = "massive";
		} else {
			i.enhancement = "";
		}
		
		i.ammoCurrent = CT.random(0, ammoMax);
		
		i.setPickedUp(true);
		if(chest.getInventoryVolume() + i.getVolume() < chest.getInventoryCapacity())
			chest.addItem((Item)i);
		
	}
	
	public Item createNew(Ranged item, int x, int y) {
		
		Ranged i = new Ranged(item.texture, item.name, item.id, item.weight, item.damage, item.aSpeed, 
				item.intimidation, item.ammoMax, item.reloadCooldown, item.refillMaterial, item.accuracy, item.volume);
		
		i.ammoCurrent = item.ammoCurrent;
		
		i.setPosition(x, y);
		return (Item)i;
		
	}
	
	public int getAmmoPercentage() {
		return (int)((double)ammoCurrent/ammoMax*100);
	}

	public int getIntimidation() {
		return intimidation;
	}

	public void setIntimidation(int intimidation) {
		this.intimidation = intimidation;
	}


	public double getaSpeed() {
		return aSpeed;
	}

	public void setaSpeed(double aSpeed) {
		this.aSpeed = aSpeed;
	}

	public int getAmmoMax() {
		return ammoMax;
	}

	public void setAmmoMax(int ammoMax) {
		this.ammoMax = ammoMax;
	}

	public int getAmmoCurrent() {
		return ammoCurrent;
	}

	public void setAmmoCurrent(int ammoCurrent) {
		this.ammoCurrent = ammoCurrent;
	}

	public int getReloadCooldown() {
		return reloadCooldown;
	}

	public void setReloadCooldown(int reloadCooldown) {
		this.reloadCooldown = reloadCooldown;
	}

	public String getRefilMaterial() {
		return refillMaterial;
	}

	public void setRefilMaterial(String refillMaterial) {
		this.refillMaterial = refillMaterial;
	}

	public boolean isLoading() {
		return loading;
	}

	public void setLoading(boolean loading) {
		this.loading = loading;
	}

	public String getEnhancement() {
		return enhancement;
	}

	public void setEnhancement(String enhancement) {
		this.enhancement = enhancement;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}
	
}
