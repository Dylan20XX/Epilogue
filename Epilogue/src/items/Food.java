package items;

import java.awt.image.BufferedImage;

import graphics.Assets;
import graphics.CT;
import structureInventory.ChestInventory;

public class Food extends Item {
        
        protected static boolean stackable = false;
        protected static String type = "food";
        public long lastrotTimer, rotCooldown = 14000, rotTimer = rotCooldown, lastrot = 0;
        public int hunger, thirst;//14000
        public int currentFreshness, freshness;
        public String food;
        
        // food
        public static Food rawChickenItem = new Food(Assets.rawChicken, "raw chicken", 800, 1.5, 10, 35, 50, 5, "raw", 140);
        public static Food cookedChickenItem = new Food(Assets.cookedChicken, "cooked chicken", 801, 1.5, 10, 35, 120, -35, "cooked", 200);
        public static Food rottenChickenItem = new Food(Assets.rottenChicken, "rotted chicken", 802, 1.5, 10, 35, 50, 5, "rotten", 0);
        public static Food suspicousChickenItem = new Food(Assets.suspiciousChicken, "suspicous chicken", 803, 1.5, 10, 35, 50, 5, "suspicious", 100);
        public static Food bugMeatItem = new Food(Assets.bugMeat, "bug meat", 804, 0.4, 5, 10, 20, 5, "raw meat", 140);
        public static Food vegeMeatItem = new Food(Assets.vegeMeat, "vege meat", 805, 0.3, 5, 8, 10, 30, "vege", 320);
        public static Food shroomItem = new Food(Assets.shroom, "shrooms", 806, 0.3, 5, 8, 5, 3, "vege", 580);
        public static Food prettyShroomItem = new Food(Assets.prettyShroom, "pretty shrooms", 807, 0.3, 5, 8, 8, 2, "poison", 580);
        public static Food brainFungusItem = new Food(Assets.brainFungui, "brain fungui", 808, 0.3, 5, 8, 15, 20, "vege", 580);
        public static Food rawMorselItem = new Food(Assets.rawMorsel, "raw morsel", 809, 1.5, 10, 35, 25, 3, "raw", 140);
        public static Food cookedMorselItem = new Food(Assets.cookedMorsel, "cooked morsel", 810, 1.5, 10, 35, 55, -15, "cooked", 200);
        public static Food rottenMorselItem = new Food(Assets.rottenMorsel, "rotted morsel", 811, 1.5, 10, 35, 25, 3, "rotten", 0);
        public static Food suspicousMorselItem = new Food(Assets.suspiciousMorsel, "suspicous morsel", 812, 1.5, 10, 35, 25, 3, "suspicious", 100);
        public static Food rawMeatItem = new Food(Assets.rawMeat, "raw meat chunk", 813, 1.5, 10, 35, 65, 8, "raw", 140);
        public static Food cookedMeatItem = new Food(Assets.cookedMeat, "cooked meat chunk", 814, 1.5, 10, 35, 145, -50, "cooked", 200);
        public static Food rottenMeatItem = new Food(Assets.rottenMeat, "rotted meat chunk", 815, 1.5, 10, 35, 65, 8, "rotten", 0);
        public static Food suspicousMeatItem = new Food(Assets.suspiciousMeat, "suspicous meat chunk", 816, 1.5, 10, 35, 65, 8, "suspicious", 100);
        public static Food extrafloralNectar = new Food(Assets.extrafloralNectar, "extrafloral nectar", 817, 2.5, 5, 8, 30, 135, "vege", 550);
        public static Food nectarBit = new Food(Assets.nectarBit, "nectar bit", 818, 0.5, 5, 6, 5, 25, "vege", 150);
        public static Food cookedSuspicousChickenItem = new Food(Assets.cookedSuspiciousChicken, "cooked chicken", 819, 1.5, 10, 35, 80, -35, "cooked", 150);
        public static Food cookedSuspicousMorselItem = new Food(Assets.cookedSuspiciousMorsel, "cooked chicken", 820, 1.5, 10, 35, 40, -20, "cooked", 150);
        public static Food cookedSuspicousMeatItem = new Food(Assets.cookedSuspiciousMeat, "cooked chicken", 821, 1.5, 10, 35, 95, -50, "cooked", 150);
        public static Food cookedBugItem = new Food(Assets.cookedBugMeat, "cooked bug", 822, 1.5, 10, 35, 50, -35, "cooked", 150);
        public static Food bossiliciousMealItem = new Food(Assets.bossiliciousMeal, "bossilicious meal", 823, 8, 10, 120, 400, 100, "cooked", 350);
        public static Food saladItem = new Food(Assets.salad, "salad", 824, 0.8, 10, 15, 55, 80, "vege", 350);
        public static Food rottenBugItem = new Food(Assets.rottenBugMeat, "rotten bug", 829, 0.4, 5, 10, 20, 5, "rotten", 0);
        public static Food rotItem = new Food(Assets.rot, "rot", 830, 1, 0, 10, 0, 0, "rotten", 0);
        public static Food scorpionTailItem = new Food(Assets.scorpionTail, "scorpion tail", 831, 0.8, 50, 15, 20, 20, "poison", 1000);
        public static Food poisonComboItem = new Food(Assets.poisonCombo, "poison combo", 832, 1, 40, 20, 35, 35, "poison", 1000);
        public static Food BugMesh = new Food(Assets.bugMesh, "bug mesh", 833, 2, 10, 50, 180, 60, "cooked", 180);
        public static Food glucose = new Food(Assets.glucose, "glucose", 834, 0.5, 10, 5, 10, 10, "cooked", 1500);
        public static Food mushroomChicken = new Food(Assets.mushroomChicken, "mushroom chicken", 834, 3, 10, 55, 230, 20, "cooked", 200);
        public static Food monsterDinner = new Food(Assets.monsterDinner, "monsterDinner", 834, 4, 10, 65, 250, 30, "cooked", 200);
        
        public Food(BufferedImage texture, String name, int id, double weight, int damage,
                        int volume, int hunger, int thirst, String food, int freshness) {
                
                super(texture, name, id, stackable, type, weight, damage, volume);
                
                this.name = name;
                this.hunger = hunger;
                this.thirst = thirst;
                this.freshness = freshness;
                this.food = food;
                currentFreshness = freshness;
                
        }
        
        public Item createNew(int x, int y) {
                Item i = new Food(texture, name, id, weight, damage, volume, hunger, thirst, food, freshness);
                i.setPosition(x, y);
                return i;
        }

        public Item createNew(int count) { // testing
                Item i = new Food(texture, name, id, weight, damage, volume, hunger, thirst, food, freshness);
                i.setPickedUp(true);
                i.setCount(count);
                return i;
        }
        
        public Item createNewInventoryItem(Food item, int x, int y) {
    		item.setPosition(x, y);
    		return item;
    	}
        
        public Item createNew(Food item, int x, int y) {
    		
    		Food i = new Food(item.texture, item.name, item.id, item.weight, item.damage, item.volume, item.hunger, 
    				item.thirst, item.food, item.freshness);
    		
    		i.currentFreshness = item.currentFreshness;
    		
    		i.setPosition(x, y);
    		return (Item)i;
    		
    	}
        
     // random generation
    	public void createNew(ChestInventory chest) {
    		Item i = new Food(texture, name, id, weight, damage, volume, hunger, thirst, food, freshness);
    		Food a = (Food)i;
    		a.setPickedUp(true);
    		chest.addItem((Item)a);
    	}
        
        public int getFreshnessPercentage() {
                return (int)((double)currentFreshness/freshness*100);
        }

        public int getHunger() {
                return hunger;
        }

        public void setHunger(int hunger) {
                this.hunger = hunger;
        }

        public int getThirst() {
                return thirst;
        }

        public void setThirst(int thirst) {
                this.thirst = thirst;
        }

        public String getFood() {
                return food;
        }

        public void setFood(String food) {
                this.food = food;
        }

        public int getCurrentFreshness() {
                return currentFreshness;
        }

        public void setCurrentFreshness(int currentFreshess) {
                this.currentFreshness = currentFreshess;
        }

        public int getFreshness() {
                return freshness;
        }

        public void setFreshness(int freshness) {
                this.freshness = freshness;
        }

}
