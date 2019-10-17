package items;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import alphaPackage.ControlCenter;
import creatures.Player;
import graphics.Assets;

public class ItemManager {
	private ControlCenter c;
	private ArrayList<Item> items;

	public static ArrayList<Item> trashList = new ArrayList<Item>();
	
	public static final int DROPTIME = 30000;
	
	public ItemManager(ControlCenter c) {
		this.c = c;
		items = new ArrayList<Item>();
		fillTrash();
	}

	public void tick() {
		
		for(int ii = 0; ii < items.size(); ii++) {
			Item i = items.get(ii);
			if(i.getType().equals("food")) {
				Food food = (Food)i;
                food.rotTimer += System.currentTimeMillis() - food.lastrotTimer;
                food.lastrotTimer = System.currentTimeMillis();

                if (food.rotTimer > food.rotCooldown) {
                
	                if(!food.getFood().equals("rotten") && food.currentFreshness > 0) {
	                        food.currentFreshness--;
	                        if(food.currentFreshness <= 1) {
	                                if(food.getName().equals("raw chicken") || food.getName().equals("cooked chicken") || food.getName().equals("suspicious chicken")) {
	                                	addItem(Food.rottenChickenItem.createNew(food.x, food.y));
	                                	items.remove(food);
	                                }
	                                else if(food.getName().equals("raw morsel") || food.getName().equals("cooked morsel") || food.getName().equals("suspicious morsel")) {
	                                	addItem(Food.rottenMorselItem.createNew(food.x,  food.y));
	                                	items.remove(food);
	                                }
	                                else if(food.getName().equals("raw meat") || food.getName().equals("cooked meat") || food.getName().equals("suspicious meat")) {
	                                	addItem(Food.rottenMeatItem.createNew(food.x,  food.y));
	                                	items.remove(food);
	                                }
	                                else if(food.getName().equals("bug meat") || food.getName().equals("cooked bug")) {
	                                	addItem(Food.rottenBugItem.createNew(food.x,  food.y));
	                                	items.remove(food);
	                                }
	                                else {
	                                	addItem(Food.rotItem.createNew(food.x,  food.y));
	                                	items.remove(food);
	                                }
	                        }
	                }
	                
	                food.rotTimer = 0;
                }
                
			}
			i.tick();
			if(i.getTimeDropped() >= DROPTIME)
				items.remove(i);
			if (i.isPickedUp()) { // if item is picked up
				i.setTimeDropped(0);
				items.remove(i);
			}
		}
		
		//Iterator<Item> it = items.iterator();
		//while (it.hasNext()) {
		//	Item i = it.next();
			
		//}
	}

	public void fillTrash() {

		trashList.add(Item.woodenPlankItem);
		trashList.add(Item.woodenStickItem);
		trashList.add(Item.tinChunkItem);
		trashList.add(Item.bronzeChunkItem);
		trashList.add(Item.zincChunkItem);
		trashList.add(Item.silkItem);
		trashList.add(Item.bearBearItem);
		trashList.add(Item.rektTinCanItem);
		trashList.add(Item.blackWireItem);
		trashList.add(Item.redWireItem);
		trashList.add(Item.brokenVaultDoorItem);
		trashList.add(Item.semiconductorItem);
		trashList.add(Item.diaperItem);
		trashList.add(Item.oldShoeItem);
		trashList.add(Item.gearItem);
		trashList.add(Item.pipeHeadItem);
		trashList.add(Item.leatherItem);
		trashList.add(Item.furItem);
		trashList.add(Item.XM214BulletItem);
		trashList.add(Item.FuelTankItem);
		trashList.add(Item.woodenStickItem);
		trashList.add(Item.batteryItem);
		trashList.add(Item.woodenPlankItem);
		
	}

	public void render(Graphics g) {
		for (Item i : items)
			i.render(g);
	}

	public void addItem(Item i) {
		i.setC(c); // whenever adding an item, set the item manage to not null
		items.add(i);
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}

}
