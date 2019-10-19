package items;

import java.awt.image.BufferedImage;

import graphics.Assets;
import graphics.CT;
import structureInventory.ChestInventory;

public class Tool extends Item {
	
	protected static boolean stackable = false;
	
	protected int intimidation;
	protected double range;
	protected double aSpeed;
	protected double power;
	protected String enhancement;
	protected int currentEndurance;
	protected int endurance;
	
	//Tools
	//texture, name, id, weight, damage, aSpeed, intimidation, range, volume, power, currentEndurance, endurance

	public static Tool woodenAxeTool = new Tool(Assets.woodenAxe, "wooden axe", "axe", 900, 1.4, 35, 1200, 2, 30, 30, 3, 50, 50);
	public static Tool woodenPickaxeTool = new Tool(Assets.woodenAxe, "wooden pickaxe", "pickaxe", 901, 1.4, 30, 1200, 2, 30, 30, 4, 50, 50);
	public static Tool stoneAxeTool = new Tool(Assets.stoneAxe, "stone axe", "axe", 902, 1.8, 50, 1200, 2, 30, 30, 4, 100, 100);
	public static Tool stonePickaxeTool = new Tool(Assets.stonePick, "stone pickaxe", "pickaxe", 903, 1.8, 35, 1200, 2, 30, 30, 5, 85, 85);
	public static Tool bronzeAxeTool = new Tool(Assets.bronzeAxe, "bronze axe","axe",  904, 2.2, 60, 1200, 2, 30, 30, 5, 150, 150);
	public static Tool bronzePickaxeTool = new Tool(Assets.bronzePick, "bronze pickaxe", "pickaxe", 905, 2.2, 50, 1200, 2, 30, 30, 6, 150, 150);
	public static Tool zincAxeTool = new Tool(Assets.zincAxe, "zinc axe", "axe", 906, 3.5, 78, 1200, 2, 30, 30, 6, 280, 280);
	public static Tool zincPickaxeTool = new Tool(Assets.zincPick, "zinc pickaxe", "pickaxe", 907, 3.5, 65, 1200, 2, 30, 30, 7, 280, 280);
	public static Tool drillTool = new Tool(Assets.drill, "drill", "pickaxe", 908, 5.9, 45, 350, 2, 1, 65, 8, 580, 580);
	public static Tool chainsawTool = new Tool(Assets.chainsaw, "chainsaw", "axe", 909, 4.2, 58, 350, 2, 1, 65, 7, 580, 580);
	public static Tool shovelTool = new Tool(Assets.shovel, "shovel", "shovel", 910, 1.8, 120, 1200, 2, 1, 60, 6, 100, 100);
	
	public Tool(BufferedImage texture, String name, String type, int id, double weight, int damage,
			double aSpeed, int intimidation, double range, int volume, double power, int currentEndurance, int endurance) {
		
		super(texture, name, id, stackable, type, weight, damage, volume);
		
		this.aSpeed = aSpeed;
		this.range = range;
		this.intimidation = intimidation;
		this.type = type;
		this.power = power;
		this.currentEndurance = currentEndurance;
		this.endurance = endurance;
		
		enhancement = "";

	}
	
	public Item createNew(int x, int y) {
		Item e = new Tool(texture, name, type, id, weight, damage, aSpeed, intimidation, range, volume, power, currentEndurance, endurance);
		
		Tool i = (Tool)e;
		int rand = CT.random(1, 20);
		if(rand==1 || rand==9) {
			i.damage += damage/CT.random(5, 15);
			i.enhancement = "sharp";
		} else if(rand==2) {
			i.power -= power/CT.random(7, 20);
			i.damage -= damage/CT.random(7, 20);
			i.enhancement = "awful";
		} else if(rand==3 || rand==10) {
			i.aSpeed += aSpeed/CT.random(10, 20);
			i.enhancement = "rough";
		} else if(rand==4) {
			i.weight += weight/CT.random(10, 30);
			i.aSpeed += aSpeed/CT.random(8, 20);
			i.enhancement = "heavy";
		} else if(rand==5) {
			i.power += power/CT.random(5, 15);
			i.aSpeed -= aSpeed/CT.random(7, 15);
			int temp = endurance/CT.random(7, 15);
			i.endurance += temp;
			i.currentEndurance += temp;
			i.enhancement = "effective";
		} else if(rand==6) {
			int temp = endurance/CT.random(8, 15);
			i.endurance += temp;
			i.currentEndurance += temp;
			i.damage += damage/CT.random(5, 12);
			i.aSpeed -= aSpeed/CT.random(6, 15);
			i.enhancement = "dangerous";
		} else if(rand==7) {
			int temp = endurance/CT.random(5, 15);
			i.endurance += temp;
			i.currentEndurance += temp;
			i.enhancement = "durable";
		} else if(rand==8) {
			i.power += power/CT.random(8, 20);
			i.weight -= weight/CT.random(8, 20);
			i.enhancement = "efficient";
		} else if(rand==11 || rand==10) {
			i.power -= power/CT.random(6, 15);
			i.enhancement = "damaged";
		} else if(rand==12) {
			i.volume += volume/CT.random(10, 15);
			i.aSpeed += aSpeed/CT.random(8, 15);
			i.enhancement = "massive";
		} else {
			i.enhancement = "";
		}
		
		i.setPosition(x, y);
		return (Item)i;
	}

	public Item createNew(int count) { // testing
		Item e = new Tool(texture, name, type, id, weight, damage, aSpeed, intimidation, range, volume, power, currentEndurance, endurance);
		
		Tool i = (Tool)e;
		int rand = CT.random(1, 20);
		if(rand==1 || rand==9) {
			i.damage += damage/CT.random(5, 15);
			i.enhancement = "sharp";
		} else if(rand==2) {
			i.power -= power/CT.random(7, 20);
			i.aSpeed += aSpeed/CT.random(6, 15);
			i.enhancement = "awful";
		} else if(rand==3 || rand==10) {
			i.aSpeed += aSpeed/CT.random(10, 20);
			i.enhancement = "rough";
		} else if(rand==4) {
			i.weight += weight/CT.random(10, 30);
			i.aSpeed += aSpeed/CT.random(8, 20);
			i.enhancement = "heavy";
		} else if(rand==5) {
			i.power += power/CT.random(5, 15);
			i.aSpeed -= aSpeed/CT.random(7, 15);
			int temp = endurance/CT.random(7, 15);
			i.endurance += temp;
			i.currentEndurance += temp;
			i.enhancement = "effective";
		} else if(rand==6) {
			int temp = endurance/CT.random(8, 15);
			i.endurance += temp;
			i.currentEndurance += temp;
			i.damage += damage/CT.random(5, 12);
			i.aSpeed -= aSpeed/CT.random(6, 15);
			i.enhancement = "dangerous";
		} else if(rand==7) {
			int temp = endurance/CT.random(5, 15);
			i.endurance += temp;
			i.currentEndurance += temp;
			i.enhancement = "durable";
		} else if(rand==8) {
			i.power += power/CT.random(8, 20);
			i.enhancement = "efficient";
		} else if(rand==11 || rand==10) {
			i.power -= power/CT.random(6, 15);
			i.enhancement = "damaged";
		} else if(rand==12) {
			i.volume += volume/CT.random(10, 15);
			i.aSpeed += aSpeed/CT.random(8, 15);
			i.enhancement = "massive";
		} else {
			i.enhancement = "";
		}
		
		i.setPickedUp(true);
		i.setCount(count);
		return (Item)i;
	}
	
	// random generation
	public void createNew(ChestInventory chest) { // testing
		Item e = new Tool(texture, name, type, id, weight, damage, aSpeed, intimidation, range, volume, power, currentEndurance, endurance);
		
		Tool i = (Tool)e;
		int rand = CT.random(1, 20);
		if(rand==1 || rand==9) {
			i.damage += damage/CT.random(5, 15);
			i.enhancement = "sharp";
		} else if(rand==2) {
			i.power -= power/CT.random(7, 20);
			i.aSpeed += aSpeed/CT.random(6, 15);
			i.enhancement = "awful";
		} else if(rand==3 || rand==10) {
			i.aSpeed += aSpeed/CT.random(10, 20);
			i.enhancement = "rough";
		} else if(rand==4) {
			i.weight += weight/CT.random(10, 30);
			i.aSpeed += aSpeed/CT.random(8, 20);
			i.enhancement = "heavy";
		} else if(rand==5) {
			i.power += power/CT.random(5, 15);
			i.aSpeed -= aSpeed/CT.random(7, 15);
			int temp = endurance/CT.random(7, 15);
			i.endurance += temp;
			i.currentEndurance += temp;
			i.enhancement = "effective";
		} else if(rand==6) {
			int temp = endurance/CT.random(8, 15);
			i.endurance += temp;
			i.currentEndurance += temp;
			i.damage += damage/CT.random(5, 12);
			i.aSpeed -= aSpeed/CT.random(6, 15);
			i.enhancement = "dangerous";
		} else if(rand==7) {
			int temp = endurance/CT.random(5, 15);
			i.endurance += temp;
			i.currentEndurance += temp;
			i.enhancement = "durable";
		} else if(rand==8) {
			i.power += power/CT.random(8, 20);
			i.enhancement = "efficient";
		} else if(rand==11 || rand==10) {
			i.power -= power/CT.random(6, 15);
			i.enhancement = "damaged";
		} else if(rand==12) {
			i.volume += volume/CT.random(10, 15);
			i.aSpeed += aSpeed/CT.random(8, 15);
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
	
	public Item createNew(Tool item, int x, int y) {
		
		Tool i = new Tool(item.texture, item.name, item.type, item.id, item.weight, item.damage, item.aSpeed, 
				item.intimidation, item.range, item.volume, item.power, item.currentEndurance, item.endurance);
		
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

	public double getPower() {
		return power;
	}

	public void setPower(double power) {
		this.power = power;
	}

}
