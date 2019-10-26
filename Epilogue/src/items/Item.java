package items;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import alphaPackage.ControlCenter;
import audio.AudioPlayer;
import graphics.Assets;
import graphics.CT;
import inventory.Inventory;
import structureInventory.ChestInventory;

public class Item {

	public static final int ITEMWIDTH = (int) (32 * ControlCenter.scaleValue),
			ITEMHEIGHT = (int) (32 * ControlCenter.scaleValue);
	protected boolean pickedUp = false;

	protected ControlCenter c;
	protected Inventory inventory;
	protected BufferedImage texture;
	protected double weight;
	protected String name, type;
	protected int id;
	protected int x, y, count;
	protected Rectangle bounds;
	protected boolean stackable;
	protected int damage;
	protected int volume;
	protected int timeDropped;
	protected boolean isEquipped;
	
	public static Item[] items = new Item[1000];

	// Items
	// image, name, id, stackable, type, weight, damage, volume
	public static Item woodItem = new Item(Assets.burntLog, "log", 0, true, "miscellaneous", 0.8, 50, 15);
	public static Item beetleMembraneItem = new Item(Assets.beetleMembrane, "beetle membrane", 1, true, "miscellaneous", 0.1, 10, 5);
	public static Item spikeItem = new Item(Assets.spike, "spike", 2, true, "miscellaneous", 0.05, 65, 15);
	public static Item tinChunkItem = new Item(Assets.tin, "tin chunk", 3, true, "miscellaneous", 0.1, 35,  20);
	public static Item bronzeChunkItem = new Item(Assets.bronze, "bronze chunk", 4, true, "miscellaneous", 0.1, 35, 15);
	public static Item zincChunkItem = new Item(Assets.zinc, "zinc chunk", 5, true, "miscellaneous", 0.2, 35,  15);
	public static Item goldChunkItem = new Item(Assets.gold, "gold chunk", 6, true, "miscellaneous", 0.2, 35, 15);
	public static Item ironChunkItem = new Item(Assets.iron, "iron chunk", 7, true, "miscellaneous", 0.5, 35, 15);
	public static Item titaniumChunkItem = new Item(Assets.titanium, "titanium chunk", 8, true, "miscellaneous", 0.3,35, 15);
	public static Item tungstenChunkItem = new Item(Assets.tungsten, "tungsten chunk", 9, true, "miscellaneous", 0.8, 35, 15);
	public static Item coalChunkItem = new Item(Assets.coal, "coal", 10, true, "miscellaneous", 0.3, 35, 15);
	public static Item torch = new Torch();
	public static Item tinIngotItem = new Item(Assets.tinIngot, "tin Ingot", 12, true, "miscellaneous", 0.08, 35, 20);
	public static Item bronzeIngotItem = new Item(Assets.bronzeIngot, "bronze Ingot", 13, true, "miscellaneous", 0.1, 35, 20);
	public static Item zincIngotItem = new Item(Assets.zincIngot, "zinc Ingot", 14, true, "miscellaneous", 0.2, 35, 20);
	public static Item goldIngotItem = new Item(Assets.goldIngot, "gold Ingot", 15, true, "miscellaneous", 0.2, 35, 20);
	public static Item ironIngotItem = new Item(Assets.ironIngot, "iron Ingot", 16, true, "miscellaneous", 0.5, 35, 20);
	public static Item titaniumIngotItem = new Item(Assets.titaniumIngot, "titanium Ingot", 17, true, "miscellaneous", 0.2, 35, 20);
	public static Item tungstenIngotItem = new Item(Assets.tungstenIngot, "tungsten Ingot", 18, true, "miscellaneous", 0.8, 35, 20);
	public static Item featherItem = new Item(Assets.feather, "feather", 19, true, "miscellaneous", 0.04, 15, 5);
	public static Item rockItem = new Item(Assets.rock, "stone", 20, true, "miscellaneous", 0.1, 45, 15);
	public static Item silkItem = new Item(Assets.silk, "silk", 21, true, "miscellaneous", 0.01, 5, 2);
	public static Item ropeItem = new Item(Assets.rope, "rope", 22, true, "miscellaneous", 0.08, 35, 10);
	public static Item tinCanItem = new Item(Assets.rock, "tin can", 23, true, "miscellaneous", 0.1, 20, 30);
	public static Item arrowItem = new Item(Assets.arrowItem, "arrow", 24, true, "miscellaneous", 0.1, 30, 10);
	public static Item AMM1DBulletItem = new Item(Assets.AMM1D, "AMM1D ammo", 25, true, "miscellaneous", 0.4, 5, 5);
	public static Item workbenchToolkitItem = new Item(Assets.workbenchToolkit, "workbench toolkit", 26, true, "miscellaneous", 0.1, 20, 25);
	public static Item smithingTableToolkitItem = new Item(Assets.smithingToolkit, "smithing set", 27, true, "miscellaneous", 0.1, 20, 25);
	public static Item purificationSinkItem = new Item(Assets.purificationSink, "purification sink", 28, true, "miscellaneous", 0.1, 5,  15);
	public static Item ashe = new Item(Assets.ashe, "ashe", 29, true, "miscellaneous", 0.1, 0, 5);
		//water containers
		//texture, name, id, weight, volume, currentCapacity, capacity
		public static Item waterSacItem = new WaterContainer(Assets.sac, "water sac", 30, 0.1, 1, 0, 100);
		public static Item waterBottleItem = new WaterContainer(Assets.waterContainer, "watter container", 31, 0.1, 1, 0, 150);
		
	public static Item bearBearItem = new Item(Assets.teddyBear, "bear bear", 32, true, "miscellaneous", 0.12, 5, 10);
	public static Item rektTinCanItem = new Item(Assets.rektTinCan, "bear bear", 33, true, "miscellaneous", 0.08, 10, 8);
	public static Item blackWireItem = new Item(Assets.blackWire, "black wire", 34, true, "miscellaneous", 0.05, 5, 1);
	public static Item redWireItem = new Item(Assets.redWire, "red wire", 35, true, "miscellaneous", 0.05, 5, 1);
	public static Item brokenVaultDoorItem = new Item(Assets.brokenVaultDoor, "broken vault door", 36, true, "miscellaneous", 2.5, 60, 80);
	public static Item stickItem = new Item(Assets.stick, "stick", 37, true, "miscellaneous", 0.01, 20, 1);
	public static Item brokenGolfClubItem = new Item(Assets.brokenGolfClub, "broken golf club", 38, true, "miscellaneous", 1.2, 65, 35);
	public static Item semiconductorItem = new Item(Assets.semiconductor, "semiconductor", 39, true, "miscellaneous", 0.5, 5, 8);
	public static Item diaperItem = new Item(Assets.diaper, "old diaper", 40, true, "miscellaneous", 0.02, 5, 3);
	public static Item oldShoeItem = new Item(Assets.oldShoe, "old shoe", 41, true, "miscellaneous", 0.02, 30, 25);
	public static Item gearItem = new Item(Assets.gear, "gear", 42, true, "miscellaneous", 0.05, 0, 1);
	public static Item pipeHeadItem = new Item(Assets.AMM1D, "pipe head", 43, true, "miscellaneous", 2, 55, 30);
	public static Item leatherItem = new Item(Assets.leather, "leather", 44, true, "miscellaneous", 0.2, 5, 5);
	public static Item furItem = new Item(Assets.fur, "fur", 45, true, "miscellaneous", 0.1, 0, 1);
	public static Item blueprintItem = new Item(Assets.blueprint, "blueprint", 46, true, "miscellaneous", 0.1, 0, 5);
	public static Item XM214BulletItem = new Item(Assets.XM214, "XM214 ammo", 47, true, "miscellaneous", 3, 5, 5);
	public static Item FuelTankItem = new Item(Assets.fuel, "fuel tank", 48, true, "miscellaneous", 5, 5, 5);
	public static Item researchKitItem = new Item(Assets.researchKit, "research kit", 49, true, "miscellaneous", 0.1, 20, 10);
	public static Item ironBarItem = new Item(Assets.ironBar, "metal bar", 50, true, "miscellaneous", 1.5, 55, 5);
	public static Item batteryItem = new Item(Assets.battery, "battery", 51, true, "miscellaneous", 0.5, 5, 5);;
	public static Item woodenStickItem = new Item(Assets.woodenStick, "wooden stick", 52, true, "miscellaneous", 5, 5, 5);
	public static Item woodenPlankItem = new Item(Assets.woodenPlank, "wooden board", 53, true, "miscellaneous", 0.1, 35, 25);
	public static Item metalPlateItem = new Item(Assets.metalPlate, "metal plate", 54, true, "miscellaneous", 2, 35, 25);
	
	public static Item glockBarrelItem = new Item(Assets.glockBarrel, "glock barrow", 75, true, "miscellaneous", 0.3, 35, 10);
	public static Item desertEagleBarrelItem = new Item(Assets.desertEagleBarrel, "desert eagle barrow", 76, true, "miscellaneous", 0.2, 35, 10);
	public static Item glockSideItem = new Item(Assets.glockSide, "glock side", 77, true, "miscellaneous", 0.4, 35, 12);
	public static Item desertEagleSideItem = new Item(Assets.desertEagleSide, "desert eagle side", 78, true, "miscellaneous", 0.3, 35, 12);
	public static Item gunFrameItem = new Item(Assets.gunFrame, "gun frame", 79, true, "miscellaneous", 0.5, 35, 18);
	public static Item triggerItem = new Item(Assets.trigger, "trigger", 80, true, "miscellaneous", 0.1, 35, 1);
	public static Item mGunBarrel = new Item(Assets.mGunBarrel, "minigun barrel", 81, true, "miscellaneous", 3, 0, 40);
	public static Item mGunGrip = new Item(Assets.mGunGrip, "minigun grip", 82, false, "miscellaneous", 2, 0, 20);
	public static Item mGunReciever = new Item(Assets.reciever, "reciever", 83, false, "miscellaneous", 2, 0, 15);
	public static Item mGunHandle = new Item(Assets.mGunHandel, "minigun handel", 84, false, "miscellaneous", 2, 0, 20);
	public static Item mGunMotor = new Item(Assets.motor, "motor", 85, false, "miscellaneous", 8, 0, 30);
	public static Item fThrowerTorch = new Item(Assets.fTorch, "flame torch", 86, false, "miscellaneous", 1, 0, 10);
	public static Item fThrowerTubing = new Item(Assets.fTubing, "tubing", 87, true, "miscellaneous", 1, 0, 15);
	public static Item fThrowerNozzle = new Item(Assets.fNozzle, "flame thrower nozzle", 88, false, "miscellaneous", 2, 0, 20);
	public static Item fThrowerFuelTank = new Item(Assets.fFuelTank, "fuel tank", 89, false, "miscellaneous", 5, 0, 30);
	public static Item fThrowerGrip = new Item(Assets.fGrip, "flame thrower grip", 90, false, "miscellaneous", 3, 0, 30);
	public static Item repairKit = new Item(Assets.repairKit, "repair kit", 999, false, "miscellaneous", 28, 0, 250);
	
	// Suspicious Statue Sacrifices 
	//public static Item orbOfMagnire = new Item(Assets.pan, "orb or magnire", 600, false, "sacrifice", 0.6, 0, 1000, 0, 0, 0, 25, 0, 0);
	// structure 
	public static Item sapItem = new Item(Assets.sap, "sap", 700, false, "structure", 0.01, 0, 1);
	public static Item workbenchItem = new Item(Assets.workbench, "workbench", 701, false, "structure", 0, 0, 0);
	public static Item smithingTableItem = new Item(Assets.smithingTable, "smithing table", 702, false, "structure", 0, 0, 0);
	public static Item purifierItem = new Item(Assets.purifier, "purifier", 703, false, "structure", 0, 0, 0);
	public static Item researchTableItem = new Item(Assets.researchTable, "research table", 704, false, "structure", 0, 0, 0);
	public static Item autoCookerItem = new Item(Assets.autoCooker, "auto cooker", 705, false, "structure", 0, 0, 0);
	public static Item smelterItem = new Item(Assets.smelter, "smelter", 706, false, "structure", 0, 0, 0);
	public static Item disintegratorItem = new Item(Assets.disintegrator[0], "disintegrator", 707, false, "structure", 0, 0, 0);
	public static Item autoCookerV2Item = new Item(Assets.AMM1D, "auto cooker v2", 708, false, "structure", 9, 55, 45);
	public static Item woodenWallItem = new Item(Assets.woodenFenceHorizontal, "wooden wall", 709, false, "structure", 0, 0, 0); 
	public static Item woodenGateItem = new Item(Assets.woodenGateHorizontalClosed, "wooden gate", 710, false, "structure", 0, 0, 0);
	public static Item stoneWallItem = new Item(Assets.stoneWallHorizontal, "stone wall", 709, false, "structure", 0, 0, 0); 
	public static Item stoneGateItem = new Item(Assets.stoneGateHorizontalClosed, "stone gate", 710, false, "structure", 0, 0, 0);
	public static Item metalWallItem = new Item(Assets.metalWallHorizontal, "metal wall", 709, false, "structure", 0, 0, 0); 
	public static Item metalGateItem = new Item(Assets.metalGateHorizontalClosed, "metal gate", 710, false, "structure", 0, 0, 0);
	public static Item campfireItem = new Item(Assets.campfire[0], "campfire", 711, false, "structure", 0, 0, 0);
	public static Item powerGeneratorItem = new Item(Assets.powerGenerator[0], "power generator", 712, false, "structure", 0, 0, 0);
	public static Item lampPostItem = new Item(Assets.lampPost[0], "lamp post", 713, false, "structure", 0, 0, 0);
	public static Item powerAdaptorItem = new Item(Assets.powerAdaptorOn, "power adaptor", 714, false, "structure", 0, 0, 0);
	public static Item heavyPulseArtilleryItem = new Item(Assets.heavyPulseItem, "heavy pulse artillery", 715, false, "structure", 0, 0, 0);
	public static Item rapidPulseArtilleryItem = new Item(Assets.rapidPulseItem, "rapid pulse artillery", 716, false, "structure", 0, 0, 0);
	public static Item tentItem = new Item(Assets.tent, "tent", 717, false, "structure", 0, 0, 0);
	public static Item woodenCrateItem = new Item(Assets.woodenCrate, "wooden crate", 718, false, "structure", 0, 0, 0);
	public static Item metalContainerItem = new Item(Assets.metalCrate, "vault", 719, false, "structure", 0, 0, 0);
	public static Item vaultItem = new Item(Assets.vault, "vault", 720, false, "structure", 0, 0, 0);
	
	// platforms
	public static Item woodenPlatformItem = new Item(Assets.woodenPlatform, "wooden platform", 751, false, "platform", 0.01, 0, 1);
	public static Item stonePlatformItem = new Item(Assets.stonePlatform, "stone platform", 752, false, "platform", 0.01, 0, 1);
	public static Item metalPlatformItem = new Item(Assets.metalPlatform, "metal platform", 753, false, "platform", 0.01, 0, 1);
	public static Item grassPlatformItem = new Item(Assets.naturalTile, "grass tile", 754, false, "platform", 0.01, 0, 1);
	
	/*
		 * workbench - physical assemblation
		 * smelter - smelt pure metal elements (ingots)
		 * smithing table - making metal joints/parts
		 * disintegratorItem - break down unwanted items
		 */
	
	/*
	 * Total Resistance Status: bronze: 55 Zinc: 79 Iron: 110 Titanium (fast/light):
	 * 90 Tungsten (slow/heavy): 138
	 * 
	 * Total Weight Status(kg): bronze: 4.1 Zinc: 7.7 Iron: 14.1 Titanium
	 * (fast/light): 7.9 Tungsten (slow/heavy): 22.1
	 * 
	 * Full Set Passive bronze: health regen + 10% Zinc: Iron: melee damage + 8%
	 * Titanium (fast/light): speed + 15% Tungsten (slow/heavy): resistance + 10%
	 * 
	 * A weight of over 10kg will affect the player's speed. Both titanium and
	 * tungsten are top tier armor miscellaneous. Depends on what type of style the
	 * player likes.
	 */

	public Item(BufferedImage texture, String name, int id, boolean stackable, String type, double weight, int damage, int volume) {

		this.texture = texture;
		this.name = name;
		this.id = id;
		this.stackable = stackable;
		this.weight = weight;
		this.type = type;
		this.damage = damage;
		this.volume = volume;
		count = 1;
		timeDropped = 0;
		isEquipped = false;
		
		bounds = new Rectangle(x, y, ITEMWIDTH, ITEMHEIGHT);

		items[id] = this;

	}

	public void tick() {

		if(!pickedUp)
			timeDropped++;
		
		if (c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
				.getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(bounds)
				&& c.getKeyManager().keyJustPressed(KeyEvent.VK_P)) {
			if(c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getEntityManager().getPlayer().getInventory().addItem(this))
				pickedUp = true;
			AudioPlayer.playAudio("audio/pickup.wav");
			
		}

	}

	public void render(Graphics g) {
		if (c == null) {
			return;
		}

		render(g, (int) (x - c.getGameCamera().getxOffset()), (int) (y - c.getGameCamera().getyOffset()));

	}

	public void render(Graphics g, int x, int y) { // for inventory
		g.drawImage(texture, x, y, ITEMWIDTH, ITEMHEIGHT, null);
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
		bounds.x = x;
		bounds.y = y;
	}

	public Item createNew(int x, int y) {
		Item i = new Item(texture, name, id, stackable, type, weight, damage, volume);
		i.setPosition(x, y);
		return i;
	}
	
	public Item createNewInventoryItem(Item item, int x, int y) {
		item.setPosition(x, y);
		return item;
	}

	public Item createNew(int count) { // testing
		Item i = new Item(texture, name, id, stackable, type, weight, damage, volume);
		i.setPickedUp(true);
		i.setCount(count);
		return i;
	}
	
	public void createNew(ChestInventory chest, int count) { // random generation
		Item i = new Item(texture, name, id, stackable, type, weight, damage, volume);
		i.setPickedUp(true);
		for(int a = 0; a < count; a++) {
			if(chest.getInventoryVolume() + i.getVolume() < chest.getInventoryCapacity())
				chest.addItem(i);
		}
	}
	
	public ControlCenter getC() {
		return c;
	}

	public void setC(ControlCenter c) {
		this.c = c;
	}

	public BufferedImage getTexture() {
		return texture;
	}

	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public boolean isPickedUp() {
		return pickedUp;
	}

	public void setPickedUp(boolean pickedUp) {
		this.pickedUp = pickedUp;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public boolean isStackable() {
		return stackable;
	}

	public void setStackable(boolean stackable) {
		this.stackable = stackable;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}
	
	public int getTimeDropped() {
		return timeDropped;
	}
	
	public void setTimeDropped(int timeDropped) {
		this.timeDropped = timeDropped;
	}
	
	public boolean isItemEquipped() {
		return isEquipped;
	}
	
	public void setItemEquipped(boolean isEquipped) {
		this.isEquipped = isEquipped;
	}
	
	@Override
	public String toString() {
		return "Item [pickedUp=" + pickedUp + ", c=" + c + ", inventory=" + inventory + ", texture=" + texture
				+ ", weight=" + weight + ", name=" + name + ", type=" + type + ", id=" + id + ", x=" + x + ", y=" + y
				+ ", count=" + count + ", bounds=" + bounds + ", stackable=" + stackable + ", damage=" + damage
				+ ", volume=" + volume + ", timeDropped=" + timeDropped + ", isEquipped=" + isEquipped + "]";
	}
	
}