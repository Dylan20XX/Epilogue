package items;

import java.awt.image.BufferedImage;

import graphics.Assets;
import graphics.CT;
import structureInventory.ChestInventory;

public class Armor extends Item {

	public static boolean stackable = false;
	
	public String type;
	public int intimidation;
	public int resistance;
	public double healthRegen;
	public String enhancement;
	public int currentEndurance;
	public int endurance;
	
	// Helmet
	// image, name, id, type, weight, damage, volume, intimidation, resistance, health regeneration, current endurance, endurance
	public static Armor pan = new Armor(Assets.pan, "pan", 200, "helmet", 1.5, 80, 35, 5, 9, 0, 50, 50);
	public static Armor goldenPan = new Armor(Assets.pan, "golden pan", 201, "helmet", 1.5, 80, 35, 5, 9, 0, 50, 50);
	public static Armor bronzeHelmet = new Armor(Assets.bronzeHelmet, "bronze helmet", 202, "helmet", 0.8, 50, 35, 3, 12, 0, 75, 75);
	public static Armor zincHelmet = new Armor(Assets.zincHelmet, "zinc helmet", 203, "helmet", 1.4, 50, 38, 5, 16, 0, 150, 150);
	public static Armor ironHelmet = new Armor(Assets.ironHelmet, "iron helmet", 204, "helmet", 3.2, 50, 45, 10, 25, 0, 180, 180);
	public static Armor titaniumHelmet = new Armor(Assets.titaniumHelmet, "titanium helmet", 205, "helmet", 1.8, 50, 35, 5, 18, 0, 330, 330);
	public static Armor tungstenHelmet = new Armor(Assets.tungstenHelmet, "tungsten helmet", 206, "helmet", 4.5, 50, 45, 15, 28, 0, 380, 380);

	// Chest Plate 
	public static Armor bronzeChest = new Armor(Assets.bronzeChestplate, "bronze chestplate", 300, "chest", 1.5, 50, 96, 5, 18, 0, 85, 85);
	public static Armor zincChest = new Armor(Assets.zincChestplate, "zinc chestplate", 301, "chest", 2.8, 50, 110, 8, 26, 0, 190, 190);
	public static Armor ironChest = new Armor(Assets.ironChestplate, "iron chestplate", 302, "chest", 4.2, 50, 125, 15, 35, 0, 220, 220);
	public static Armor titaniumChest = new Armor(Assets.titaniumChestplate, "titanium chestplate", 303, "chest", 3.4, 50, 75, 10, 25, 0, 390, 390);
	public static Armor tungstenChest = new Armor(Assets.tungstenChestplate, "tungsten chestplate", 304, "chest", 7.6, 50, 135, 18, 45, 0, 450, 450);

	// Leg 
	public static Armor bronzeLegging = new Armor(Assets.bronzeLegging, "bronze leggings", 400, "leg", 1.2, 50, 65, 3, 16, 0, 80, 80);
	public static Armor zincLegging = new Armor(Assets.zincLegging, "zinc leggings", 401, "leg", 2.3, 50, 70, 5, 21, 0, 180, 180);
	public static Armor ironLegging = new Armor(Assets.ironLegging, "iron leggings", 402, "leg", 3.8, 50, 89, 10, 25, 0, 205, 205);
	public static Armor titaniumLegging = new Armor(Assets.titaniumLegging, "titanium leggings", 403, "leg", 1.1, 50, 65, 5, 20, 0, 370, 370);
	public static Armor tungstenLegging = new Armor(Assets.tungstenLegging, "tungsten leggings", 404, "leg", 5.8, 50, 95, 12, 38, 0, 425, 525);

	// Boots
	public static Armor bronzeBoots = new Armor(Assets.bronzeBoots, "bronze boots", 500, "boots", 0.6, 50, 25, 2, 10, 0, 78, 78);
	public static Armor zincBoots = new Armor(Assets.zincBoots, "zinc boots", 501, "boots", 1.2, 50, 30, 4, 14, 0, 160, 160);
	public static Armor ironBoots = new Armor(Assets.ironBoots, "iron boots", 502, "boots", 2.9, 50, 35, 6, 21, 0, 185, 185);
	public static Armor titaniumBoots = new Armor(Assets.titaniumBoots, "titanium boots", 503, "boots", 0.6, 50, 25, 3, 16, 0, 350, 350);
	public static Armor tungstenBoots = new Armor(Assets.tungstenBoots, "tungsten boots", 504, "boots", 4.2, 50, 45, 8, 27, 0, 400, 400);

	// Gauntlets
	public static Armor bronzeGauntlets = new Armor(Assets.bronzeGauntlets, "bronze gauntlets", 550, "gauntlets", 0.5, 50, 23, 1, 6, 0, 70, 70);
	public static Armor zincGauntlets = new Armor(Assets.zincGauntlets, "zinc gauntlets", 551, "gauntlets", 1.1, 50, 25, 2, 10, 0, 145, 145);
	public static Armor ironGauntlets = new Armor(Assets.ironGauntlets, "iron gauntlets", 552, "gauntlets", 2.7, 50, 30, 3, 15, 0, 175, 175);
	public static Armor titaniumGauntlets = new Armor(Assets.titaniumGauntlets, "titanium gauntlets", 553, "gauntlets", 0.8, 50, 23, 1, 11, 0, 315, 315);
	public static Armor tungstenGauntlets = new Armor(Assets.tungstenGauntlets, "tungsten gauntlets", 554, "gauntlets", 3.8, 50, 35, 3, 18, 0, 365, 365);
	
	public Armor(BufferedImage texture, String name, int id,  String type, double weight, int damage,
			int volume, int intimidation, int resistance, double healthRegen, int currentEndurance, int endurance) {
		
		super(texture, name, id, stackable, type, weight, damage, volume);
		
		this.intimidation = intimidation;
		this.resistance = resistance;
		this.healthRegen = healthRegen;
		this.currentEndurance = currentEndurance;
		this.endurance = endurance;
		this.type = type;
		
		enhancement = "";
		
	}
	
	public Item createNew(int x, int y) {
		Item i = new Armor(texture, name, id, type, weight, damage, volume, intimidation, resistance, healthRegen, currentEndurance, endurance);
		Armor a = (Armor)i;
		int rand = CT.random(1, 20);
		if(rand==1 || rand == 12) {
			int temp = resistance/CT.random(8, 12);
			a.resistance += CT.random(temp, resistance);
			a.enhancement = "protective";
		} else if(rand==2) {
			int temp = resistance/CT.random(8, 12);
			a.resistance -= CT.random(temp, resistance);
			a.enhancement = "cracked";
		} else if(rand==3) {
			a.intimidation += intimidation/CT.random(5, 15);
			a.enhancement = "intimidating";
		} else if(rand==4) {
			int temp1 = resistance/CT.random(8, 12);
			a.resistance += CT.random(temp1, resistance);
			int temp = endurance/CT.random(7, 15);
			a.endurance += temp;
			a.currentEndurance += temp;
			a.weight -= weight/CT.random(7, 15);
			a.enhancement = "reinforced";
		} else if(rand==5) {
			int temp = endurance/CT.random(6, 15);
			a.endurance += temp;
			a.currentEndurance += temp;
			a.enhancement = "durable";
		} else if(rand==6) {
			int temp = resistance/CT.random(5, 9);
			a.resistance += CT.random(temp, resistance);
			a.enhancement = "plated";
		} else if(rand==11) {
			int temp1 = resistance/CT.random(8, 12);
			a.resistance += CT.random(temp1, resistance);
			int temp = endurance/CT.random(6, 15);
			a.endurance -= temp;
			a.currentEndurance -= temp;
			a.enhancement = "awful";
		} else if(rand==7) {
			a.weight += weight/CT.random(5, 15);
			a.enhancement = "heavy";
		} else if(rand==8) {
			a.healthRegen += 1;
			a.enhancement = "cozy";
		} else if(rand==9) {
			a.volume += volume/CT.random(5, 15);
			a.enhancement = "massive";
		} else if(rand==10) {
			a.healthRegen -= 1;
			a.enhancement = "uncomfortable";
		} 
		a.setPosition(x, y);
		return (Item)a;
	}

	public Item createNew(int count) { // testing
		Item i = new Armor(texture, name, id, type, weight, damage, volume, intimidation, resistance, healthRegen, currentEndurance, endurance);
		Armor a = (Armor)i;
		int rand = CT.random(1, 20);
		if(rand==1 || rand == 12) {
			int temp = resistance/CT.random(6, 12);
			a.resistance += CT.random(temp, resistance);
			a.enhancement = "protective";
		} else if(rand==2) {
			int temp = resistance/CT.random(6, 12);
			a.resistance -= CT.random(temp, resistance);
			a.enhancement = "cracked";
		} else if(rand==3) {
			a.intimidation += intimidation/CT.random(5, 15);
			a.enhancement = "intimidating";
		} else if(rand==4) {
			int temp1 = resistance/CT.random(6, 12);
			a.resistance += CT.random(temp1, resistance);
			int temp = endurance/CT.random(7, 15);
			a.endurance += temp;
			a.currentEndurance += temp;
			a.weight -= weight/CT.random(7, 15);
			a.enhancement = "reinforced";
		} else if(rand==5) {
			int temp = endurance/CT.random(6, 15);
			a.endurance += temp;
			a.currentEndurance += temp;
			a.enhancement = "durable";
		} else if(rand==6) {
			int temp = resistance/CT.random(4, 8);
			a.resistance += CT.random(temp, resistance);
			a.enhancement = "plated";
		} else if(rand==11) {
			int temp1 = resistance/CT.random(6, 12);
			a.resistance += CT.random(temp1, resistance);
			int temp = endurance/CT.random(6, 15);
			a.endurance -= temp;
			a.currentEndurance -= temp;
			a.enhancement = "awful";
		} else if(rand==7) {
			a.weight += weight/CT.random(5, 15);
			a.enhancement = "heavy";
		} else if(rand==8) {
			a.healthRegen += 1;
			a.enhancement = "cozy";
		} else if(rand==9) {
			a.volume += volume/CT.random(5, 15);
			a.enhancement = "massive";
		} else if(rand==10) {
			a.healthRegen -= 1;
			a.enhancement = "uncomfortable";
		} 
		a.setPickedUp(true);
		a.setCount(count);
		return (Item)a;
	}
	
	// random generation
	public void createNew(ChestInventory chest) {
		Item i = new Armor(texture, name, id, type, weight, damage, volume, intimidation, resistance, healthRegen, currentEndurance, endurance);
		Armor a = (Armor)i;
		int rand = CT.random(1, 20);
		if(rand==1 || rand == 12) {
			int temp = resistance/CT.random(6, 12);
			a.resistance += CT.random(temp, resistance);
			a.enhancement = "protective";
		} else if(rand==2) {
			int temp = resistance/CT.random(6, 12);
			a.resistance -= CT.random(temp, resistance);
			a.enhancement = "cracked";
		} else if(rand==3) {
			a.intimidation += intimidation/CT.random(5, 15);
			a.enhancement = "intimidating";
		} else if(rand==4) {
			int temp1 = resistance/CT.random(6, 12);
			a.resistance += CT.random(temp1, resistance);
			int temp = endurance/CT.random(7, 15);
			a.endurance += temp;
			a.currentEndurance += temp;
			a.weight -= weight/CT.random(7, 15);
			a.enhancement = "reinforced";
		} else if(rand==5) {
			int temp = endurance/CT.random(6, 15);
			a.endurance += temp;
			a.currentEndurance += temp;
			a.enhancement = "durable";
		} else if(rand==6) {
			int temp = resistance/CT.random(4, 8);
			a.resistance += CT.random(temp, resistance);
			a.enhancement = "plated";
		} else if(rand==11) {
			int temp1 = resistance/CT.random(6, 12);
			a.resistance += CT.random(temp1, resistance);
			int temp = endurance/CT.random(6, 15);
			a.endurance -= temp;
			a.currentEndurance -= temp;
			a.enhancement = "awful";
		} else if(rand==7) {
			a.weight += weight/CT.random(5, 15);
			a.enhancement = "heavy";
		} else if(rand==8) {
			a.healthRegen += 1;
			a.enhancement = "cozy";
		} else if(rand==9) {
			a.volume += volume/CT.random(5, 15);
			a.enhancement = "massive";
		} else if(rand==10) {
			a.healthRegen -= 1;
			a.enhancement = "uncomfortable";
		} 
		
		a.setCurrentEndurance(CT.random(1, a.getEndurance()));
		a.setPickedUp(true);
		chest.addItem((Item)a);
	}
	
	public Item createNew(Armor item, int x, int y) {
		
		Armor i = new Armor(item.texture, item.name, item.id, item.type, item.weight, item.damage, 
				item.volume, item.intimidation, item.resistance, item.healthRegen, item.currentEndurance, item.endurance);
		
		i.currentEndurance = item.currentEndurance;
		
		i.setPosition(x, y);
		return (Item)i;
		
	}
	
	public int getEndurancePercentage() {
		return (int)((double)currentEndurance/endurance*100);
	}

	public int getIntimidation() {
		return intimidation;
	}

	public void setIntimidation(int intimidation) {
		this.intimidation = intimidation;
	}

	public int getResistance() {
		return resistance;
	}

	public void setResistance(int resistance) {
		this.resistance = resistance;
	}

	public double getHealthRegen() {
		return healthRegen;
	}

	public void setHealthRegen(double healthRegen) {
		this.healthRegen = healthRegen;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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