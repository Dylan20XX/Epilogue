package items;

import java.awt.image.BufferedImage;

import graphics.Assets;
import graphics.CT;
import structureInventory.ChestInventory;

public class Weapon extends Item {

	public static boolean stackable = false;
	public static String type = "weapon";

	public int intimidation;
	public double range;
	public double aSpeed;
	public String enhancement;
	public int currentEndurance;
	public int endurance;
	
	// Melee Weapons
	// image, name, id, weight, damage, attack speed, intimidation, range, volume, current endurance, endurance
	public static Weapon batWeapon = new Weapon(Assets.bat, "bat", 100, 1.8, 65, 1200, 2, 30, 30, 550, 550);
	public static Weapon SteelSword = new Weapon(Assets.steelSword, "steel sword", 101, 2.2, 170, 1200, 2, 60, 50, 1500, 1500);
	public static Weapon katana = new Weapon(Assets.katana, "katana", 102, 0.6, 115, 500, 2, 65, 50, 750, 750);
	public static Weapon bronzeBlade = new Weapon(Assets.bronzeBlade, "bronze blade", 103, 1.2, 80, 1000, 2, 45, 30, 350, 350);
	public static Weapon zincBlade = new Weapon(Assets.zincBlade, "zinc blade", 104, 1.4, 103, 1000, 3, 55, 35, 6500, 6500);
	public static Weapon ironSword = new Weapon(Assets.ironSword, "iron sword", 105, 2.6, 155, 1000, 10, 75, 55, 1100, 1100);
	public static Weapon titaniumClaws = new Weapon(Assets.titaniumClaws, "titanium claws", 106, 0.8, 80, 300, 5, 30, 20, 1650, 1650);
	public static Weapon tungstenMace = new Weapon(Assets.tungstenMace, "tungsten mace", 107, 5.5, 225, 1750, 30, 100, 100, 1800, 1800);
	public static Weapon giantSawBlade = new Weapon(Assets.giantSawBlade, "giant saw blade", 108, 7.2, 210, 2250, 25, 100, 95, 800, 800);
	
	public static Weapon woodenClub = new Weapon(Assets.woodenClub, "wooden club", 109, 1.4, 50, 1000, 2, 30, 30, 80, 80);
	public static Weapon stoneClub = new Weapon(Assets.stoneClub, "stone club", 110, 2, 75, 1200, 2, 30, 50, 210, 210);
	public static Weapon spikeyClub = new Weapon(Assets.spikeClub, "spikey club", 111, 2, 105, 1000, 2, 30, 50, 175, 175);
	
	public static Weapon darkSaber = new Weapon(Assets.darkSaber, "dark saber", 112, 0.8, 160, 450, 50, 85, 25, 1000, 1000);
	
	public Weapon(BufferedImage texture, String name, int id, double weight, int damage, double aSpeed,
			int intimidation, double range, int volume, int currentEndurance, int endurance) {

		super(texture, name, id, stackable, type, weight, damage, volume);

		this.aSpeed = aSpeed;
		this.range = range;
		this.intimidation = intimidation;
		this.currentEndurance = currentEndurance;
		this.endurance = endurance;

		enhancement = "";

	}

	public Item createNew(int x, int y) {
		Item e = new Weapon(texture, name, id, weight, damage, aSpeed, intimidation, range, volume, currentEndurance,
				endurance);
		Weapon i = (Weapon) e;
		int rand = CT.random(1, 20);
		if (rand == 1 || rand == 9) {
			i.damage += damage / CT.random(5, 15);
			i.enhancement = "keen";
		} else if (rand == 2) {
			i.damage -= damage / CT.random(7, 20);
			i.aSpeed += aSpeed / CT.random(6, 15);
			i.enhancement = "shameful";
		} else if (rand == 3 || rand == 10) {
			i.aSpeed += aSpeed / CT.random(10, 20);
			i.enhancement = "rough";
		} else if (rand == 4) {
			i.weight += weight / CT.random(10, 30);
			i.aSpeed += aSpeed / CT.random(8, 20);
			i.enhancement = "heavy";
		} else if (rand == 5) {
			i.damage += damage / CT.random(4, 9);
			i.aSpeed -= aSpeed / CT.random(6, 15);
			i.enhancement = "murderous";
		} else if (rand == 6) {
			int temp = endurance / CT.random(8, 15);
			i.endurance += temp;
			i.currentEndurance += temp;
			i.damage += damage / CT.random(8, 15);
			i.weight -= weight / CT.random(10, 25);
			i.enhancement = "enhanced";
		} else if (rand == 7) {
			int temp = endurance / CT.random(5, 15);
			i.endurance += temp;
			i.currentEndurance += temp;
			i.enhancement = "durable";
		} else if (rand == 8) {
			i.intimidation += intimidation / CT.random(10, 20);
			i.enhancement = "scary";
		} else if (rand == 11 || rand == 10) {
			i.damage -= damage / CT.random(8, 20);
			i.enhancement = "damaged";
		} else if (rand == 12) {
			i.aSpeed -= aSpeed / CT.random(8, 15);
			i.enhancement = "agile";
		} else if (rand == 13) {
			i.volume += volume / CT.random(10, 15);
			i.aSpeed += aSpeed / CT.random(8, 15);
			i.enhancement = "massive";
		} else {
			i.enhancement = "";
		}
		i.setPosition(x, y);
		return (Item) i;
	}

	public Item createNew(int count) { // testing
		Item e = new Weapon(texture, name, id, weight, damage, aSpeed, intimidation, range, volume, currentEndurance,
				endurance);

		Weapon i = (Weapon) e;
		int rand = CT.random(1, 20);
		if (rand == 1 || rand == 9) {
			i.damage += damage / CT.random(5, 15);
			i.enhancement = "keen";
		} else if (rand == 2) {
			i.damage -= damage / CT.random(7, 20);
			i.aSpeed += aSpeed / CT.random(6, 15);
			i.enhancement = "shameful";
		} else if (rand == 3 || rand == 10) {
			i.aSpeed += aSpeed / CT.random(10, 20);
			i.enhancement = "rough";
		} else if (rand == 4) {
			i.weight += weight / CT.random(10, 30);
			i.aSpeed += aSpeed / CT.random(8, 20);
			i.enhancement = "heavy";
		} else if (rand == 5) {
			i.damage += damage / CT.random(4, 9);
			i.aSpeed -= aSpeed / CT.random(6, 15);
			i.enhancement = "murderous";
		} else if (rand == 6) {
			int temp = endurance / CT.random(8, 15);
			i.endurance += temp;
			i.currentEndurance += temp;
			i.damage += damage / CT.random(8, 15);
			i.weight -= weight / CT.random(10, 25);
			i.enhancement = "enhanced";
		} else if (rand == 7) {
			int temp = endurance / CT.random(5, 15);
			i.endurance += temp;
			i.currentEndurance += temp;
			i.enhancement = "durable";
		} else if (rand == 8) {
			i.intimidation += intimidation / CT.random(10, 20);
			i.enhancement = "scary";
		} else if (rand == 11 || rand == 10) {
			i.damage -= damage / CT.random(8, 20);
			i.enhancement = "damaged";
		} else if (rand == 12) {
			i.aSpeed -= aSpeed / CT.random(8, 15);
			i.enhancement = "agile";
		} else if (rand == 13) {
			i.volume += volume / CT.random(10, 15);
			i.aSpeed += aSpeed / CT.random(8, 15);
			i.enhancement = "massive";
		} else {
			i.enhancement = "";
		}

		i.setPickedUp(true);
		i.setCount(count);
		return (Item) i;
	}

	// random generation
	public void createNew(ChestInventory chest) {

		Item e = new Weapon(texture, name, id, weight, damage, aSpeed, intimidation, range, volume, currentEndurance,
				endurance);

		Weapon i = (Weapon) e;
		
		int rand = CT.random(1, 20);
		
		if (rand == 1 || rand == 9) {
			i.damage += i.damage / CT.random(5, 15);
			i.enhancement = "keen";
		} else if (rand == 2) {
			i.damage -= i.damage / CT.random(7, 20);
			i.aSpeed += i.aSpeed / CT.random(6, 15);
			i.enhancement = "shameful";
		} else if (rand == 3 || rand == 10) {
			i.aSpeed += i.aSpeed / CT.random(10, 20);
			i.enhancement = "rough";
		} else if (rand == 4) {
			i.weight += i.weight / CT.random(10, 30);
			i.aSpeed += i.aSpeed / CT.random(8, 20);
			i.enhancement = "heavy";
		} else if (rand == 5) {
			i.damage += i.damage / CT.random(4, 9);
			i.aSpeed -= i.aSpeed / CT.random(6, 15);
			i.enhancement = "murderous";
		} else if (rand == 6) {
			int temp = i.endurance / CT.random(8, 15);
			i.endurance += temp;
			i.currentEndurance += temp;
			i.damage += i.damage / CT.random(8, 15);
			i.weight -= i.weight / CT.random(10, 25);
			i.enhancement = "enhanced";
		} else if (rand == 7) {
			int temp = i.endurance / CT.random(5, 15);
			i.endurance += temp;
			i.currentEndurance += temp;
			i.enhancement = "durable";
		} else if (rand == 8) {
			i.intimidation += i.intimidation / CT.random(10, 20);
			i.enhancement = "scary";
		} else if (rand == 11 || rand == 10) {
			i.damage -= i.damage / CT.random(8, 20);
			i.enhancement = "damaged";
		} else if (rand == 12) {
			i.aSpeed -= i.aSpeed / CT.random(8, 15);
			i.enhancement = "agile";
		} else if (rand == 13) {
			i.volume += i.volume / CT.random(10, 15);
			i.aSpeed += i.aSpeed / CT.random(8, 15);
			i.enhancement = "massive";
		} else {
			i.enhancement = "";
		}
		
		i.setCurrentEndurance(CT.random(1, i.getEndurance()));
		
		i.setPickedUp(true);
		if(chest.getInventoryVolume() + i.getVolume() < chest.getInventoryCapacity())
			chest.addItem((Item)i);

	}

	public int getEndurancePercentage() {
		return (int) ((double) currentEndurance / endurance * 100);
	}

	public Item createNew(Weapon item, int x, int y) {
		
		Weapon i = new Weapon(item.texture, item.name, item.id, item.weight, item.damage, item.aSpeed, 
				item.intimidation, item.range, item.volume, item.currentEndurance, item.endurance);
		
		i.currentEndurance = item.currentEndurance;
		
		i.setPosition(x, y);
		return (Item)i;
		
	}

	public int getIntimidation() {
		return intimidation;
	}

	public void setIntimidation(int intimidation) {
		this.intimidation = intimidation;
	}

	public double getRange() {
		return range;
	}

	public void setRange(double range) {
		this.range = range;
	}

	public double getaSpeed() {
		return aSpeed;
	}

	public void setaSpeed(double aSpeed) {
		this.aSpeed = aSpeed;
	}

	public String getEnhancement() {
		return enhancement;
	}

	public void setEnhancement(String enhancement) {
		this.enhancement = enhancement;
	}

	public int getCurrentEndurance() {
		return currentEndurance;
	}

	public void setCurrentEndurance(int currentEndurance) {
		this.currentEndurance = currentEndurance;
	}

	public int getEndurance() {
		return endurance;
	}

	public void setEndurance(int endurance) {
		this.endurance = endurance;
	}

}
