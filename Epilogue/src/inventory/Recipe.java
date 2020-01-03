package inventory;

import java.util.ArrayList;
import java.util.HashMap;

import items.Armor;
import items.Food;
import items.Item;
import items.Ranged;
import items.Tool;
import items.Torch;
import items.Weapon;
import structureInventory.SmithingTableRecipe;
import structureInventory.WorkbenchRecipe;

public class Recipe {
	
	public HashMap<Integer, Integer> recipeRequirements = new HashMap<Integer, Integer>(); // First int is item id and
																							// second is item amount
																							// required
	private int id;
	private Item item;
	private boolean structure = false;
	private int wallType = 0; //1 - wood, 2 - stone, 3 - metal
	
	public static ArrayList<Recipe> lockedRecipes = new ArrayList<Recipe>();
	public static ArrayList<Recipe> unlockedRecipes = new ArrayList<Recipe>();
	
	private int basicSurvivalXP = 0;
	private int combatXP = 0;
	private int cookingXP = 0;
	private int buildingXP = 0;
	
	//NOTE: Recipes should have the same id as the item that they are used to make
	
	public static ArrayList<Recipe> recipes = new ArrayList<Recipe>();//Stores all recipes
	
	//starting recipes 
	public static Recipe woodStrip = new Recipe(0);
	public static Recipe rope = new Recipe(1);
	public static Recipe woodenClub = new Recipe(2);
	public static Recipe stoneClub = new Recipe(3);
	public static Recipe salad = new Recipe(8);
	public static Recipe sac = new Recipe(10);
	public static Recipe torch = new Recipe(11);
	public static Recipe workbenchToolkit = new Recipe(12);
	public static Recipe smithingTableToolkit = new Recipe(13);
	public static Recipe researchTableKit = new Recipe(14);
	
	public static Recipe redWire = new Recipe(15);
	public static Recipe blackWire = new Recipe(16);
	public static Recipe silk = new Recipe(17);
	public static Recipe waterContainer = new Recipe(18);
	public static Recipe arrow = new Recipe(19);
	public static Recipe AMM1D = new Recipe(20);
	public static Recipe XM214 = new Recipe(21);
	public static Recipe purificationSink = new Recipe(22);
	public static Recipe fuel = new Recipe(23);
	public static Recipe battery = new Recipe(24);
	public static Recipe ironBar = new Recipe(25);
	public static Recipe metalPlate = new Recipe(26);
	public static Recipe trigger = new Recipe(27);
	public static Recipe gunFrame= new Recipe(28);
	public static Recipe glockSide = new Recipe(29);
	public static Recipe desertEagleSide = new Recipe(30);
	public static Recipe glockBarrel = new Recipe(31);
	public static Recipe desertEagleBarrel = new Recipe(32);
	public static Recipe motor = new Recipe(33);
	public static Recipe reciever = new Recipe(34);
	public static Recipe mGunBarrel = new Recipe(35);
	public static Recipe mGunHandel = new Recipe(36);
	public static Recipe mGunGrip = new Recipe(37);
	public static Recipe fTorch = new Recipe(38);
	public static Recipe fNozzle = new Recipe(40);
	public static Recipe fGrip = new Recipe(41);
	public static Recipe fFuelTank = new Recipe(42);
	public static Recipe fur = new Recipe(43);
	public static Recipe rope3 = new Recipe(44);
	public static Recipe shovel = new Recipe(45);
	public static Recipe sparkPlug = new Recipe(46);
	public static Recipe repairKit = new Recipe(47);
	
	public static Recipe spikeClub = new Recipe(100);
	public static Recipe bronzeBlade = new Recipe(101);
	public static Recipe zincBlade = new Recipe(102);
	public static Recipe ironSword = new Recipe(103);
	public static Recipe titaniumClaws = new Recipe(104);
	public static Recipe tungstenMace = new Recipe(105);
	public static Recipe woodenAxe = new Recipe(106);
	public static Recipe woodenPickaxe = new Recipe(107);
	public static Recipe stoneAxe = new Recipe(108);
	public static Recipe stonePickaxe = new Recipe(109);
	public static Recipe bronzeAxe = new Recipe(110);
	public static Recipe bronzePickaxe = new Recipe(111);
	public static Recipe zincAxe = new Recipe(112);
	public static Recipe zincPickaxe = new Recipe(113);
	public static Recipe chainsaw = new Recipe(114);
	public static Recipe drill = new Recipe(115);
	
	public static Recipe glock = new Recipe(116);
	public static Recipe woodenBow = new Recipe(117);
	public static Recipe bronzeBow = new Recipe(118);
	public static Recipe zincBow = new Recipe(119);
	public static Recipe ironBow = new Recipe(120);
	public static Recipe flameThrower = new Recipe(121);
	public static Recipe megashakalaka = new Recipe(122);
	public static Recipe desertEagle = new Recipe(123);
	
	public static Recipe bronzeHelmet = new SmithingTableRecipe(200);
	public static Recipe bronzeChestplate = new SmithingTableRecipe(201);
	public static Recipe bronzeLeggings = new SmithingTableRecipe(202);
	public static Recipe bronzeBoots = new SmithingTableRecipe(203);
	public static Recipe bronzeGauntlets = new SmithingTableRecipe(204);
	public static Recipe zincHelmet = new SmithingTableRecipe(205);
	public static Recipe zincChestplate = new SmithingTableRecipe(206);
	public static Recipe zincLeggings = new SmithingTableRecipe(207);
	public static Recipe zincBoots = new SmithingTableRecipe(208);
	public static Recipe zincGauntlets = new SmithingTableRecipe(209);
	public static Recipe ironHelmet = new SmithingTableRecipe(210);
	public static Recipe ironChestplate = new SmithingTableRecipe(211);
	public static Recipe ironLeggings = new SmithingTableRecipe(212);
	public static Recipe ironBoots = new SmithingTableRecipe(213);
	public static Recipe ironGauntlets = new SmithingTableRecipe(214);
	public static Recipe titaniumHelmet = new SmithingTableRecipe(215);
	public static Recipe titaniumChestplate = new SmithingTableRecipe(216);
	public static Recipe titaniumLeggings = new SmithingTableRecipe(217);
	public static Recipe titaniumBoots = new SmithingTableRecipe(218);
	public static Recipe titaniumGauntlets = new SmithingTableRecipe(219);
	public static Recipe tungstenHelmet = new SmithingTableRecipe(220);
	public static Recipe tungstenChestplate = new SmithingTableRecipe(221);
	public static Recipe tungstenLeggings = new SmithingTableRecipe(222);
	public static Recipe tungstenBoots = new SmithingTableRecipe(223);
	public static Recipe tungstenGauntlets = new SmithingTableRecipe(224);
	
	public static Recipe mushroomChicken = new WorkbenchRecipe(300);
	public static Recipe monsterDinner = new WorkbenchRecipe(301);
	public static Recipe bugMesh = new WorkbenchRecipe(302);
	public static Recipe poisonCombo = new WorkbenchRecipe(303);
	public static Recipe bossiliciousMeal = new WorkbenchRecipe(304);
	public static Recipe nectarBit = new WorkbenchRecipe(305);
	
	public static Recipe workBench = new Recipe(701);
	public static Recipe smithingTable = new Recipe(702);
	public static Recipe woodenCrate = new Recipe(703);
	public static Recipe researchTable = new Recipe(704);
	public static Recipe smelter = new Recipe(705);
	public static Recipe autoCooker = new Recipe(706);
	public static Recipe disintegrator = new Recipe(707);
	//708 removed (analyzer)
	public static Recipe woodenWall = new Recipe(709);
	public static Recipe woodenGate = new Recipe(710);
	public static Recipe stoneWall = new Recipe(711);
	public static Recipe stoneGate = new Recipe(712);
	public static Recipe metalWall = new Recipe(713);
	public static Recipe metalGate = new Recipe(714);
	public static Recipe campfire = new Recipe(715);
	public static Recipe powerGenerator = new Recipe(716);
	public static Recipe lampPost = new Recipe(717);
	public static Recipe powerAdaptor = new Recipe(718);
	public static Recipe heavyPulseArtillery = new Recipe(719);
	public static Recipe rapidPulseArtillery = new Recipe(720);
	public static Recipe purifier = new Recipe(721);
	public static Recipe metalContainer = new Recipe(722);
	public static Recipe vault = new Recipe(723);
	public static Recipe tent = new Recipe(724);
	
	public static Recipe woodenFloor = new Recipe(751);
	public static Recipe stoneFloor = new Recipe(752);
	public static Recipe metalFloor = new Recipe(753);
	public static Recipe grassTile = new Recipe(754);
	
	private int amtGet = 1;

	public Recipe(int id) {
		this.id = id;

		setupRecipeRequirements();
		setupItem();
		
		setupXPRequirements();
		
		if((id >= 0 && id <= 14) || id == 43 || (id >= 106 && id <= 109) || id == 701 || id == 702 || id == 704 || id == 715) { //starting recipes
			
			boolean alreadyAdded = false;
			for (Recipe i : Recipe.recipes){
				if(i.getId() == id)
					alreadyAdded = true;
			}
			if(!alreadyAdded) {
				recipes.add(this);
				unlockedRecipes.add(this); //add to locked recipe list
			}
			
		} else {
			//Add the recipe to the list if its not already added
			boolean alreadyAdded = false;
			for (Recipe i : Recipe.recipes){
				if(i.getId() == id)
					alreadyAdded = true;
			}
			if(!alreadyAdded) {
				recipes.add(this);
				lockedRecipes.add(this); //add to locked recipe list
			}
			
		}
		
	}
	
	public Recipe(int id, boolean learned) {
		this.id = id;
		
		setupRecipeRequirements();
		setupItem();
		
		setupXPRequirements();
		
		if(learned) {
			
			boolean alreadyAdded = false;
			for (Recipe i : Recipe.unlockedRecipes){
				if(i.getId() == id)
					alreadyAdded = true;
			}
			if(!alreadyAdded) {
				recipes.add(this);
				unlockedRecipes.add(this); //add to unlocked recipe list
			}
			for (int  i = 0; i < Recipe.lockedRecipes.size(); i++){
				if(lockedRecipes.get(i).getId() == id)
					lockedRecipes.remove(i);
			}
			
		} 
		
	}
	
	private void setupRecipeRequirements() {

		if (id == 0) {
			recipeRequirements.put(Item.woodItem.getId(), 1);
			amtGet = 3;
		} else if (id == 1) {
			recipeRequirements.put(Item.silkItem.getId(), 8);
		} else if (id == 2) {
			recipeRequirements.put(Item.stickItem.getId(), 5);
			recipeRequirements.put(Item.ropeItem.getId(), 1);
		} else if (id == 3) {
			recipeRequirements.put(Weapon.woodenClub.getId(), 1);
			recipeRequirements.put(Item.rockItem.getId(), 8);
			recipeRequirements.put(Item.ropeItem.getId(), 1);
		} else if (id == 8) {
			recipeRequirements.put(Food.vegeMeatItem.getId(), 4);
			recipeRequirements.put(Food.shroomItem.getId(), 4);
			recipeRequirements.put(Item.smithingTableItem.getId(), 1);
		} else if (id == 19) {
			recipeRequirements.put(Item.stickItem.getId(), 1);
			recipeRequirements.put(Item.featherItem.getId(), 3);
		} else if (id == 10) {
			recipeRequirements.put(Item.leatherItem.getId(), 5);
			recipeRequirements.put(Item.ropeItem.getId(), 1);
		} else if (id == 11) {
			recipeRequirements.put(Item.coalChunkItem.getId(), 1);
			recipeRequirements.put(Item.stickItem.getId(), 1);
		} else if (id == 12) {
			recipeRequirements.put(Item.tinIngotItem.getId(), 3);
			recipeRequirements.put(Item.stickItem.getId(), 5);
		} else if (id == 13) {
			recipeRequirements.put(Item.tinIngotItem.getId(), 5);
			recipeRequirements.put(Item.stickItem.getId(), 8);
		} else if (id == 14) {
			recipeRequirements.put(Item.tinIngotItem.getId(), 4);
			recipeRequirements.put(Item.stickItem.getId(), 12);
			recipeRequirements.put(Item.leatherItem.getId(), 2);
		} 
		
		else if (id == 15) {
			recipeRequirements.put(Item.tinIngotItem.getId(), 1);
			recipeRequirements.put(Item.bronzeIngotItem.getId(), 1);
		} else if (id == 16) {
			recipeRequirements.put(Item.tinIngotItem.getId(), 1);
			recipeRequirements.put(Item.bronzeIngotItem.getId(), 2);
		} else if (id == 17) {
			recipeRequirements.put(Item.furItem.getId(), 1);
		} else if (id == 18) {
			recipeRequirements.put(Item.ironIngotItem.getId(), 4);
			recipeRequirements.put(Item.tinIngotItem.getId(), 5);
		} else if (id == 20) {
			recipeRequirements.put(Item.zincIngotItem.getId(), 1);
		} else if (id == 21) {
			recipeRequirements.put(Item.zincIngotItem.getId(), 1);
		} else if (id == 22) {
			recipeRequirements.put(Item.tinIngotItem.getId(), 5);
			recipeRequirements.put(Item.ironIngotItem.getId(), 1);
		} else if (id == 23) {
			recipeRequirements.put(Item.coalChunkItem.getId(), 1);
			recipeRequirements.put(Item.tinIngotItem.getId(), 3);
		} else if (id == 24) {
			recipeRequirements.put(Item.titaniumIngotItem.getId(), 1);
			recipeRequirements.put(Item.coalChunkItem.getId(), 1);
		} else if (id == 25) {
			recipeRequirements.put(Item.ironIngotItem.getId(), 3);
		} else if (id == 26) {
			recipeRequirements.put(Item.ironIngotItem.getId(), 2);
			recipeRequirements.put(Item.zincIngotItem.getId(), 2);
		} else if (id == 27) {
			recipeRequirements.put(Item.ironIngotItem.getId(), 1);
		} else if (id == 28) {
			recipeRequirements.put(Item.tinIngotItem.getId(), 4);
		} else if (id == 29) {
			recipeRequirements.put(Item.tinIngotItem.getId(), 3);
		} else if (id == 30) {
			recipeRequirements.put(Item.tinIngotItem.getId(), 2);
			recipeRequirements.put(Item.titaniumIngotItem.getId(), 1);
		} else if (id == 31) {
			recipeRequirements.put(Item.ironIngotItem.getId(), 2);
			recipeRequirements.put(Item.tinIngotItem.getId(), 1);
		} else if (id == 32) {
			recipeRequirements.put(Item.titaniumIngotItem.getId(), 2);
			recipeRequirements.put(Item.tinIngotItem.getId(), 1);
		} else if (id == 33) {
			recipeRequirements.put(Item.redWireItem.getId(), 4);
			recipeRequirements.put(Item.blackWireItem.getId(), 4);
			recipeRequirements.put(Item.metalPlateItem.getId(), 2);
			recipeRequirements.put(Item.titaniumIngotItem.getId(), 2);
			recipeRequirements.put(Item.batteryItem.getId(), 3);
			recipeRequirements.put(Item.goldIngotItem.getId(), 1);
		} else if (id == 34) {
			recipeRequirements.put(Item.redWireItem.getId(), 1);
			recipeRequirements.put(Item.blackWireItem.getId(), 1);
			recipeRequirements.put(Item.tungstenIngotItem.getId(), 2);
		} else if (id == 35) {
			recipeRequirements.put(Item.ironBarItem.getId(), 1);
		} else if (id == 36) {
			recipeRequirements.put(Item.tinIngotItem.getId(), 3);
			recipeRequirements.put(Item.tinIngotItem.getId(), 1);
		} else if (id == 37) {
			recipeRequirements.put(Item.ironIngotItem.getId(), 2);
			recipeRequirements.put(Item.triggerItem.getId(), 1);
		} else if (id == 38) {
			recipeRequirements.put(Item.ironBarItem.getId(), 1);
			recipeRequirements.put(Item.tinIngotItem.getId(), 3);
		} else if (id == 40) {
			recipeRequirements.put(Item.tinIngotItem.getId(), 4);
			recipeRequirements.put(Item.bronzeIngotItem.getId(), 2);
		} else if (id == 41) {
			recipeRequirements.put(Item.zincIngotItem.getId(), 2);
			recipeRequirements.put(Item.triggerItem.getId(), 1);
		} else if (id == 42) {
			recipeRequirements.put(Item.metalPlateItem.getId(), 3);
		} else if (id == 43) {
			recipeRequirements.put(Item.bearBearItem.getId(), 1);
		} else if (id == 44) {
			recipeRequirements.put(Item.furItem.getId(), 3);
		} else if (id == 45) {
			recipeRequirements.put(Item.metalPlateItem.getId(), 1);
			recipeRequirements.put(Item.stickItem.getId(), 5);
			recipeRequirements.put(Item.ropeItem.getId(), 1);
		} else if (id == 46) {
			recipeRequirements.put(Item.tinIngotItem.getId(), 3);
			recipeRequirements.put(Item.titaniumIngotItem.getId(), 4);
			recipeRequirements.put(Item.tungstenIngotItem.getId(), 2);
		} else if (id == 47) {
			recipeRequirements.put(Item.turboCharger.getId(), 1);
			recipeRequirements.put(Item.crankShaft.getId(), 1);
			recipeRequirements.put(Item.compressorWheel.getId(), 1);
			recipeRequirements.put(Item.sparkPlug.getId(), 1);
		}
		
		else if (id == 100) {
			recipeRequirements.put(Weapon.woodenClub.getId(), 1);
			recipeRequirements.put(Item.spikeItem.getId(), 8);
			recipeRequirements.put(Item.ropeItem.getId(), 1);
		} else if (id == 101) {
			recipeRequirements.put(Item.bronzeIngotItem.getId(), 4);
			recipeRequirements.put(Item.stickItem.getId(), 3);
			recipeRequirements.put(Item.ropeItem.getId(), 1);
		} else if (id == 102) {
			recipeRequirements.put(Item.zincIngotItem.getId(), 5);
			recipeRequirements.put(Item.stickItem.getId(), 3);
			recipeRequirements.put(Item.ropeItem.getId(), 1);
		} else if (id == 103) {
			recipeRequirements.put(Item.ironIngotItem.getId(), 6);
			recipeRequirements.put(Item.stickItem.getId(), 3);
		} else if (id == 104) {
			recipeRequirements.put(Item.titaniumIngotItem.getId(), 4);
			recipeRequirements.put(Item.stickItem.getId(), 3);
		} else if (id == 105) {
			recipeRequirements.put(Item.tungstenIngotItem.getId(), 8);
			recipeRequirements.put(Item.stickItem.getId(), 3);
		} else if (id == 106) {
			recipeRequirements.put(Item.woodenPlankItem.getId(), 2);
			recipeRequirements.put(Item.stickItem.getId(), 3);
			recipeRequirements.put(Item.ropeItem.getId(), 1);
		} else if (id == 107) {
			recipeRequirements.put(Item.woodenPlankItem.getId(), 3);
			recipeRequirements.put(Item.stickItem.getId(), 3);
			recipeRequirements.put(Item.ropeItem.getId(), 1);
		} else if (id == 108) {
			recipeRequirements.put(Item.rockItem.getId(), 6);
			recipeRequirements.put(Item.stickItem.getId(), 3);
			recipeRequirements.put(Item.ropeItem.getId(), 1);
		} else if (id == 109) {
			recipeRequirements.put(Item.rockItem.getId(), 8);
			recipeRequirements.put(Item.stickItem.getId(), 3);
			recipeRequirements.put(Item.ropeItem.getId(), 1);
		} else if (id == 110) {
			recipeRequirements.put(Item.bronzeIngotItem.getId(), 2);
			recipeRequirements.put(Item.stickItem.getId(), 3);
			recipeRequirements.put(Item.ropeItem.getId(), 1);
		} else if (id == 111) {
			recipeRequirements.put(Item.bronzeIngotItem.getId(), 3);
			recipeRequirements.put(Item.stickItem.getId(), 3);
			recipeRequirements.put(Item.ropeItem.getId(), 1);
		} else if (id == 112) {
			recipeRequirements.put(Item.zincIngotItem.getId(), 3);
			recipeRequirements.put(Item.stickItem.getId(), 4);
			recipeRequirements.put(Item.ropeItem.getId(), 1);
		} else if (id == 113) {
			recipeRequirements.put(Item.zincIngotItem.getId(), 4);
			recipeRequirements.put(Item.stickItem.getId(), 4);
			recipeRequirements.put(Item.ropeItem.getId(), 1);
		} else if (id == 114) {
			recipeRequirements.put(Item.titaniumIngotItem.getId(), 4);
			recipeRequirements.put(Item.ironIngotItem.getId(), 6);
		} else if (id == 115) {
			recipeRequirements.put(Item.tungstenIngotItem.getId(), 4);
			recipeRequirements.put(Item.ironIngotItem.getId(), 6);
		} 
		
		else if (id == 116) {
			recipeRequirements.put(Item.triggerItem.getId(), 1);
			recipeRequirements.put(Item.gunFrameItem.getId(), 1);
			recipeRequirements.put(Item.glockBarrelItem.getId(), 1);
			recipeRequirements.put(Item.glockSideItem.getId(), 1);
		} else if (id == 117) {
			recipeRequirements.put(Item.stickItem.getId(), 12);
			recipeRequirements.put(Item.ropeItem.getId(), 3);
		} else if (id == 118) {
			recipeRequirements.put(Item.bronzeIngotItem.getId(), 4);
			recipeRequirements.put(Item.ropeItem.getId(), 3);
		} else if (id == 119) {
			recipeRequirements.put(Item.zincIngotItem.getId(), 6);
			recipeRequirements.put(Item.ropeItem.getId(), 4);
		} else if (id == 120) {
			recipeRequirements.put(Item.ironBarItem.getId(), 3);
			recipeRequirements.put(Item.ropeItem.getId(), 4);
		} else if (id == 121) {
			recipeRequirements.put(Item.mGunReciever.getId(), 1);
			recipeRequirements.put(Item.fThrowerTorch.getId(), 1);
			recipeRequirements.put(Item.fThrowerGrip.getId(), 1);
			recipeRequirements.put(Item.fThrowerFuelTank.getId(), 1);
			recipeRequirements.put(Item.fThrowerNozzle.getId(), 1);
		} else if (id == 122) {
			recipeRequirements.put(Item.mGunMotor.getId(), 1);
			recipeRequirements.put(Item.mGunReciever.getId(), 1);
			recipeRequirements.put(Item.mGunBarrel.getId(), 5);
			recipeRequirements.put(Item.mGunHandle.getId(), 1);
			recipeRequirements.put(Item.mGunGrip.getId(), 1);
		} else if(id == 123) {
			recipeRequirements.put(Item.triggerItem.getId(), 1);
			recipeRequirements.put(Item.gunFrameItem.getId(), 1);
			recipeRequirements.put(Item.desertEagleBarrelItem.getId(), 1);
			recipeRequirements.put(Item.desertEagleSideItem.getId(), 1);
		}
		
		else if (id == 200) {
			recipeRequirements.put(Item.bronzeIngotItem.getId(), 5);
			recipeRequirements.put(Item.furItem.getId(), 3);
		} else if (id == 201) {
			recipeRequirements.put(Item.bronzeIngotItem.getId(), 7);
			recipeRequirements.put(Item.leatherItem.getId(), 4);
		} else if (id == 202) {
			recipeRequirements.put(Item.bronzeIngotItem.getId(), 6);
			recipeRequirements.put(Item.leatherItem.getId(), 2);
		} else if (id == 203) {
			recipeRequirements.put(Item.bronzeIngotItem.getId(), 4);
			recipeRequirements.put(Item.furItem.getId(), 5);
		} else if (id == 204) {
			recipeRequirements.put(Item.bronzeIngotItem.getId(), 2);
		} 
		else if (id == 205) {
			recipeRequirements.put(Item.zincIngotItem.getId(), 5);
			recipeRequirements.put(Item.furItem.getId(), 3);
		} else if (id == 206) {
			recipeRequirements.put(Item.zincIngotItem.getId(), 7);
			recipeRequirements.put(Item.leatherItem.getId(), 4);
		} else if (id == 207) {
			recipeRequirements.put(Item.zincIngotItem.getId(), 6);
			recipeRequirements.put(Item.leatherItem.getId(), 2);
		} else if (id == 208) {
			recipeRequirements.put(Item.zincIngotItem.getId(), 4);
			recipeRequirements.put(Item.furItem.getId(), 5);
		} else if (id == 209) {
			recipeRequirements.put(Item.zincIngotItem.getId(), 2);
		} 
		else if (id == 210) {
			recipeRequirements.put(Item.ironIngotItem.getId(), 5);
			recipeRequirements.put(Item.furItem.getId(), 3);
		} else if (id == 211) {
			recipeRequirements.put(Item.ironIngotItem.getId(), 7);
			recipeRequirements.put(Item.leatherItem.getId(), 4);
		} else if (id == 212) {
			recipeRequirements.put(Item.ironIngotItem.getId(), 6);
			recipeRequirements.put(Item.leatherItem.getId(), 2);
		} else if (id == 213) {
			recipeRequirements.put(Item.ironIngotItem.getId(), 4);
			recipeRequirements.put(Item.furItem.getId(), 5);
		} else if (id == 214) {
			recipeRequirements.put(Item.ironIngotItem.getId(), 2);
		} 
		else if (id == 215) {
			recipeRequirements.put(Item.titaniumIngotItem.getId(), 5);
			recipeRequirements.put(Item.furItem.getId(), 3);
		} else if (id == 216) {
			recipeRequirements.put(Item.titaniumIngotItem.getId(), 7);
			recipeRequirements.put(Item.leatherItem.getId(), 4);
		} else if (id == 217) {
			recipeRequirements.put(Item.titaniumIngotItem.getId(), 6);
			recipeRequirements.put(Item.leatherItem.getId(), 2);
		} else if (id == 218) {
			recipeRequirements.put(Item.titaniumIngotItem.getId(), 4);
			recipeRequirements.put(Item.furItem.getId(), 5);
		} else if (id == 219) {
			recipeRequirements.put(Item.titaniumIngotItem.getId(), 2);
		} 
		else if (id == 220) {
			recipeRequirements.put(Item.tungstenIngotItem.getId(), 5);
			recipeRequirements.put(Item.furItem.getId(), 3);
		} else if (id == 221) {
			recipeRequirements.put(Item.tungstenIngotItem.getId(), 7);
			recipeRequirements.put(Item.leatherItem.getId(), 4);
		} else if (id == 222) {
			recipeRequirements.put(Item.tungstenIngotItem.getId(), 6);
			recipeRequirements.put(Item.leatherItem.getId(), 2);
		} else if (id == 223) {
			recipeRequirements.put(Item.tungstenIngotItem.getId(), 4);
			recipeRequirements.put(Item.furItem.getId(), 5);
		} else if (id == 224) {
			recipeRequirements.put(Item.tungstenIngotItem.getId(), 2);
		} 
		
		else if (id == 300) {
			recipeRequirements.put(Food.cookedChickenItem.getId(), 1);
			recipeRequirements.put(Food.mushroomChicken.getId(), 3);
		} else if (id == 301) {
			recipeRequirements.put(Food.cookedMeatItem.getId(), 1);
			recipeRequirements.put(Food.vegeMeatItem.getId(), 2);
		} else if (id == 302) {
			recipeRequirements.put(Food.bugMeatItem.getId(), 3);
			recipeRequirements.put(Food.vegeMeatItem.getId(), 3);
		} else if (id == 303) {
			recipeRequirements.put(Food.scorpionTailItem.getId(), 1);
			recipeRequirements.put(Food.prettyShroomItem.getId(), 2);
		} else if (id == 304) {
			recipeRequirements.put(Food.cookedChickenItem.getId(), 1);
			recipeRequirements.put(Food.cookedMorselItem.getId(), 2);
			recipeRequirements.put(Food.cookedMeatItem.getId(), 1);
			recipeRequirements.put(Food.vegeMeatItem.getId(), 5);
			recipeRequirements.put(Food.mushroomChicken.getId(), 5);
		} else if (id == 305) {
			recipeRequirements.put(Food.extrafloralNectar.getId(), 1);
		} 
		
		else if (id == 701) {
			recipeRequirements.put(Item.woodenPlankItem.getId(), 12);
			recipeRequirements.put(Item.workbenchToolkitItem.getId(), 1);
		} else if (id == 702) {
			recipeRequirements.put(Item.woodenPlankItem.getId(), 10);
			recipeRequirements.put(Item.rockItem.getId(), 25);
			recipeRequirements.put(Item.smithingTableItem.getId(), 1);
		} else if (id == 703) {
			recipeRequirements.put(Item.woodenPlankItem.getId(), 12);
			recipeRequirements.put(Item.tinIngotItem.getId(), 3);
		} else if (id == 704) {
			recipeRequirements.put(Item.woodenPlankItem.getId(), 20);
			recipeRequirements.put(Item.researchKitItem.getId(), 1);
		} else if (id == 705) {
			recipeRequirements.put(Item.rockItem.getId(), 25);
		} else if (id == 706) {
			recipeRequirements.put(Item.rockItem.getId(), 25);
		} else if (id == 707) {
			recipeRequirements.put(Item.rockItem.getId(), 30);
		} else if (id == 709) {
			recipeRequirements.put(Item.woodenPlankItem.getId(), 8);
		} else if (id == 710) {
			recipeRequirements.put(Item.woodenPlankItem.getId(), 8);
		} else if (id == 711) {
			recipeRequirements.put(Item.rockItem.getId(), 15);
		} else if (id == 712) {
			recipeRequirements.put(Item.rockItem.getId(), 15);
		} else if (id == 713) {
			recipeRequirements.put(Item.metalPlateItem.getId(), 1);
			recipeRequirements.put(Item.ironBarItem.getId(), 2);
		} else if (id == 714) {
			recipeRequirements.put(Item.metalPlateItem.getId(), 1);
			recipeRequirements.put(Item.ironBarItem.getId(), 2);
		} else if (id == 715) {
			recipeRequirements.put(Item.stickItem.getId(), 12);
		} else if (id == 716) {
			recipeRequirements.put(Item.mGunMotor.getId(), 1);
			recipeRequirements.put(Item.metalPlateItem.getId(), 4);
			recipeRequirements.put(Item.tinIngotItem.getId(), 5);
			recipeRequirements.put(Item.redWireItem.getId(), 5);
			recipeRequirements.put(Item.blackWireItem.getId(), 5);
		} else if (id == 717) {
			recipeRequirements.put(Item.ironBarItem.getId(), 2);
			recipeRequirements.put(Item.redWireItem.getId(), 2);
			recipeRequirements.put(Item.blackWireItem.getId(), 2);
		} else if (id == 718) {
			recipeRequirements.put(Item.titaniumIngotItem.getId(), 1);
			recipeRequirements.put(Item.metalPlateItem.getId(), 2);
			recipeRequirements.put(Item.tinIngotItem.getId(), 5);
			recipeRequirements.put(Item.redWireItem.getId(), 3);
			recipeRequirements.put(Item.blackWireItem.getId(), 3);
		} else if (id == 719) {
			recipeRequirements.put(Item.tungstenIngotItem.getId(), 3);
			recipeRequirements.put(Item.metalPlateItem.getId(), 2);
			recipeRequirements.put(Item.redWireItem.getId(), 3);
			recipeRequirements.put(Item.blackWireItem.getId(), 3);
		} else if (id == 720) {
			recipeRequirements.put(Item.titaniumIngotItem.getId(), 3);
			recipeRequirements.put(Item.metalPlateItem.getId(), 2);
			recipeRequirements.put(Item.redWireItem.getId(), 3);
			recipeRequirements.put(Item.blackWireItem.getId(), 3);
		} else if (id == 721) {
			recipeRequirements.put(Item.tinIngotItem.getId(), 6);
			recipeRequirements.put(Item.rockItem.getId(), 15);
			recipeRequirements.put(Item.purificationSinkItem.getId(), 1);
		} else if (id == 722) {
			recipeRequirements.put(Item.metalPlateItem.getId(), 2);
		} else if (id == 723) {
			recipeRequirements.put(Item.tungstenIngotItem.getId(), 4);
		} else if (id == 724) {
			recipeRequirements.put(Item.leatherItem.getId(), 14);
			recipeRequirements.put(Item.ironBarItem.getId(), 2);
			recipeRequirements.put(Item.tinIngotItem.getId(), 5);
		} 
		
		else if (id == 751) {
			recipeRequirements.put(Item.woodenPlankItem.getId(), 8);
		} else if (id == 752) {
			recipeRequirements.put(Item.rockItem.getId(), 15);
		} else if (id == 753) {
			recipeRequirements.put(Item.metalPlateItem.getId(), 1);
		} else if (id == 754) {
			recipeRequirements.put(Item.sapItem.getId(), 8);
		} 
		
		
	}

	private void setupItem() {

		if (id == 0) {
			setItem(Item.woodenPlankItem);
			amtGet = 3;
		} else if (id == 1) {
			setItem(Item.ropeItem);
			amtGet = 3;
		} else if (id == 2) {
			setItem(Weapon.woodenClub);
		} else if (id == 3) {
			setItem(Weapon.stoneClub);
		} else if (id == 8) {
			setItem(Food.saladItem);
		} else if (id == 19) {
			setItem(Item.arrowItem);
		} else if (id == 10) {
			setItem(Item.waterSacItem);
		} else if (id == 11) {
			setItem(Torch.torch);
		} else if (id == 12) {
			setItem(Item.workbenchToolkitItem);
		} else if (id == 13) {
			setItem(Item.smithingTableToolkitItem);
		} else if (id == 14) {
			setItem(Item.researchKitItem);
		} 
		
		else if (id == 15) {
			setItem(Item.redWireItem);
			amtGet = 2;
		} else if (id == 16) {
			setItem(Item.blackWireItem);
			amtGet = 2;
		} else if (id == 17) {
			setItem(Item.silkItem);
			amtGet = 5;
		} else if (id == 18) {
			setItem(Item.waterBottleItem);
		} else if (id == 20) {
			setItem(Item.AMM1DBulletItem);
			amtGet = 4;
		} else if (id == 21) {
			setItem(Item.XM214BulletItem);
		} else if (id == 22) {
			setItem(Item.purificationSinkItem);
		} else if (id == 23) {
			setItem(Item.FuelTankItem);
		} else if (id == 24) {
			setItem(Item.batteryItem);
		} else if (id == 25) {
			setItem(Item.ironBarItem);
		} else if (id == 26) {
			setItem(Item.metalPlateItem);
		} else if (id == 27) {
			setItem(Item.triggerItem);
		} else if (id == 28) {
			setItem(Item.gunFrameItem);
		} else if (id == 29) {
			setItem(Item.glockSideItem);
		} else if (id == 30) {
			setItem(Item.desertEagleSideItem);
		} else if (id == 31) {
			setItem(Item.glockBarrelItem);
		} else if (id == 32) {
			setItem(Item.desertEagleBarrelItem);
		} else if (id == 33) {
			setItem(Item.mGunMotor);
		} else if (id == 34) {
			setItem(Item.mGunReciever);
		} else if (id == 35) {
			setItem(Item.mGunBarrel);
		} else if (id == 36) {
			setItem(Item.mGunHandle);
		} else if (id == 37) {
			setItem(Item.mGunGrip);
		} else if (id == 38) {
			setItem(Item.fThrowerTorch);
		} else if (id == 40) {
			setItem(Item.fThrowerNozzle);
		} else if (id == 41) {
			setItem(Item.fThrowerGrip);
		} else if (id == 42) {
			setItem(Item.FuelTankItem);
		} else if (id == 43) {
			setItem(Item.furItem);
			amtGet = 8;
		} else if (id == 44) {
			setItem(Item.ropeItem);
		} else if (id == 45) {
			setItem(Tool.shovelTool);
		} else if (id == 46) {
			setItem(Item.sparkPlug);
		} else if (id == 47) {
			setItem(Item.repairKit);
		} 
		
		else if (id == 100) {
			setItem(Weapon.spikeyClub);
		} else if (id == 101) {
			setItem(Weapon.bronzeBlade);
		} else if (id == 102) {
			setItem(Weapon.zincBlade);
		} else if (id == 103) {
			setItem(Weapon.ironSword);
		} else if (id == 104) {
			setItem(Weapon.titaniumClaws);
		} else if (id == 105) {
			setItem(Weapon.tungstenMace);
		} else if (id == 106) {
			setItem(Tool.woodenAxeTool);
		} else if (id == 107) {
			setItem(Tool.woodenPickaxeTool);
		} else if (id == 108) {
			setItem(Tool.stoneAxeTool);
		} else if (id == 109) {
			setItem(Tool.stonePickaxeTool);
		} else if (id == 110) {
			setItem(Tool.bronzeAxeTool);
		} else if (id == 111) {
			setItem(Tool.bronzePickaxeTool);
		} else if (id == 112) {
			setItem(Tool.zincAxeTool);
		} else if (id == 113) {
			setItem(Tool.zincPickaxeTool);
		} else if (id == 114) {
			setItem(Tool.chainsawTool);
		} else if (id == 115) {
			setItem(Tool.drillTool);
		} 
		
		else if (id == 116) {
			setItem(Ranged.glock);
		} else if (id == 117) {
			setItem(Ranged.woodenBow);
		} else if (id == 118) {
			setItem(Ranged.bronzeBow);
		} else if (id == 119) {
			setItem(Ranged.zincBow);
		} else if (id == 120) {
			setItem(Ranged.ironBow);
		} else if (id == 121) {
			setItem(Ranged.flamethrower);
		} else if (id == 122) {
			setItem(Ranged.megashakalaka);
		} else if (id == 123) {
			setItem(Ranged.desertEagle);
		} 
		
		else if (id == 200) {
			setItem(Armor.bronzeHelmet);
		} else if (id == 201) {
			setItem(Armor.bronzeChest);
		} else if (id == 202) {
			setItem(Armor.bronzeLegging);
		} else if (id == 203) {
			setItem(Armor.bronzeBoots);
		} else if (id == 204) {
			setItem(Armor.bronzeGauntlets);
		} 
		else if (id == 205) {
			setItem(Armor.zincHelmet);
		} else if (id == 206) {
			setItem(Armor.zincChest);
		} else if (id == 207) {
			setItem(Armor.zincLegging);
		} else if (id == 208) {
			setItem(Armor.zincBoots);
		} else if (id == 209) {
			setItem(Armor.zincGauntlets);
		} 
		else if (id == 210) {
			setItem(Armor.ironHelmet);
		} else if (id == 211) {
			setItem(Armor.ironChest);
		} else if (id == 212) {
			setItem(Armor.ironLegging);
		} else if (id == 213) {
			setItem(Armor.ironBoots);
		} else if (id == 214) {
			setItem(Armor.ironGauntlets);
		} 
		else if (id == 215) {
			setItem(Armor.titaniumHelmet);
		} else if (id == 216) {
			setItem(Armor.titaniumChest);
		} else if (id == 217) {
			setItem(Armor.titaniumLegging);
		} else if (id == 218) {
			setItem(Armor.titaniumBoots);
		} else if (id == 219) {
			setItem(Armor.titaniumGauntlets);
		} 
		else if (id == 220) {
			setItem(Armor.tungstenHelmet);
		} else if (id == 221) {
			setItem(Armor.tungstenChest);
		} else if (id == 222) {
			setItem(Armor.tungstenLegging);
		} else if (id == 223) {
			setItem(Armor.tungstenBoots);
		} else if (id == 224) {
			setItem(Armor.tungstenGauntlets);
		} 
		
		else if (id == 300) {
			setItem(Food.mushroomChicken);
		} else if (id == 301) {
			setItem(Food.monsterDinner);
		} else if (id == 302) {
			setItem(Food.BugMesh);
		} else if (id == 303) {
			setItem(Food.poisonComboItem);
		} else if (id == 304) {
			setItem(Food.bossiliciousMealItem);
		} else if (id == 305) {
			setItem(Food.nectarBit);
			amtGet = 5;
		} 
		
		else if (id == 701) {
			setItem(Item.workbenchItem);
			amtGet = 0;
			setStructure(true);
		} else if (id == 702) {
			setItem(Item.smithingTableItem);
			amtGet = 0;
			setStructure(true);
		} else if (id == 703) {
			setItem(Item.woodenCrateItem);
			amtGet = 0;
			setStructure(true);
		} else if (id == 704) {
			setItem(Item.researchTableItem);
			amtGet = 0;
			setStructure(true);
		} else if (id == 705) {
			setItem(Item.smelterItem);
			amtGet = 0;
			setStructure(true);
		} else if (id == 706) {
			setItem(Item.autoCookerItem);
			amtGet = 0;
			setStructure(true);
		} else if (id == 707) {
			setItem(Item.disintegratorItem);
			amtGet = 0;
			setStructure(true);
		} else if (id == 709) {
			setItem(Item.woodenWallItem);
			amtGet = 0;
			wallType = 1;
			setStructure(true);
		} else if (id == 710) {
			setItem(Item.woodenGateItem);
			amtGet = 0;
			wallType = 1;
			setStructure(true);
		} else if (id == 711) {
			setItem(Item.stoneWallItem);
			amtGet = 0;
			wallType = 2;
			setStructure(true);
		} else if (id == 712) {
			setItem(Item.stoneGateItem);
			amtGet = 0;
			wallType = 2;
			setStructure(true);
		} else if (id == 713) {
			setItem(Item.metalWallItem);
			amtGet = 0;
			wallType = 3;
			setStructure(true);
		} else if (id == 714) {
			setItem(Item.metalGateItem);
			amtGet = 0;
			wallType = 3;
			setStructure(true);
		} else if (id == 715) {
			setItem(Item.campfireItem);
			amtGet = 0;
			setStructure(true);
		} else if (id == 716) {
			setItem(Item.powerGeneratorItem);
			amtGet = 0;
			setStructure(true);
		} else if (id == 717) {
			setItem(Item.lampPostItem);
			amtGet = 0;
			setStructure(true);
		} else if (id == 718) {
			setItem(Item.powerAdaptorItem);
			amtGet = 0;
			setStructure(true);
		} else if (id == 719) {
			setItem(Item.heavyPulseArtilleryItem);
			amtGet = 0;
			setStructure(true);
		} else if (id == 720) {
			setItem(Item.rapidPulseArtilleryItem);
			amtGet = 0;
			setStructure(true);
		} else if (id == 721) {
			setItem(Item.purifierItem);
			amtGet = 0;
			setStructure(true);
		} else if (id == 722) {
			setItem(Item.metalContainerItem);
			amtGet = 0;
			setStructure(true);
		} else if (id == 723) {
			setItem(Item.vaultItem);
			amtGet = 0;
			setStructure(true);
		} else if (id == 724) {
			setItem(Item.tentItem);
			amtGet = 0;
			setStructure(true);
		} 
		
		else if (id == 751) {
			setItem(Item.woodenPlatformItem);
			amtGet = 0;
			setStructure(true);
		} else if (id == 752) {
			setItem(Item.stonePlatformItem);
			amtGet = 0;
			setStructure(true);
		} else if (id == 753) {
			setItem(Item.metalPlatformItem);
			amtGet = 0;
			setStructure(true);
		}  else if (id == 754) {
			setItem(Item.grassPlatformItem);
			amtGet = 0;
			setStructure(true);
		} 
		
		else {
            System.out.println("no item set for recipe with id: " + id);
        }
		
	}
	
	//sets up the requirements to learn the recipe
	private void setupXPRequirements() {
		
		//alternatively xp needed can be set up based on the recipe id
		if(this.getItem() instanceof Weapon) {
			
			Weapon i = (Weapon)this.getItem();
			
			if(i.getName().equals("tungsten mace"))
				combatXP = 500;
			else if(i.getName().equals("titanium claws"))
				combatXP = 1;
			else if(i.getName().equals("iron sword"))
				combatXP = 250;
			else if(i.getName().equals("zinc sword"))
				combatXP = 150;
			else
				combatXP = 100;
			
		} else if(this.getItem() instanceof Ranged) {
			
			Ranged i = (Ranged)this.getItem();
			
			if(i.getName().equals("megashakalaka"))
				combatXP = 750;
			else if(i.getName().equals("flamethrower"))
				combatXP = 600;
			else if(i.getName().equals("iron bow"))
				combatXP = 250;
			else
				combatXP = 200;
			
		} else if(this.getItem() instanceof Armor) {
			
			Armor i = (Armor)this.getItem();
			
			if(i.getName().equals("zinc helmet") || i.getName().equals("zinc leggings") ||  i.getName().equals("zinc chestplate") ||
					i.getName().equals("zinc boots") || i.getName().equals("zinc gauntlets")) 
				combatXP = 100;
			else if(i.getName().equals("iron helmet") || i.getName().equals("iron leggings") ||  i.getName().equals("iron chestplate") ||
					i.getName().equals("iron boots") || i.getName().equals("iron gauntlets"))
				combatXP = 150;
			else if(i.getName().equals("titanium helmet") || i.getName().equals("titanium leggings") ||  i.getName().equals("titanium chestplate") ||
					i.getName().equals("titanium boots") || i.getName().equals("titanium gauntlets"))
				combatXP = 250;
			else if(i.getName().equals("tungsten helmet") || i.getName().equals("tungsten leggings") ||  i.getName().equals("tungsten chestplate") ||
					i.getName().equals("tungsten boots") || i.getName().equals("tungsten gauntlets"))
				combatXP = 250;
			else
				combatXP = 50;
			
		} else if(this.getItem() instanceof Food) {
			
			Food i = (Food)this.getItem();
			
			if(i.getName().equals("bossilicious meal") ) 
				cookingXP = 1000;
			else 
				cookingXP = 200;
			
		} else if(this.isStructure()) {//i.getItem().getType().equals("structure") || i.getItem().getType().equals("platform")
			
			buildingXP = 0; //250
			
		} else if(this.getItem() instanceof Tool) {
			
			Tool i = (Tool)this.getItem();
			
			if(i.getName().equals("chainsaw")) 
				basicSurvivalXP = 500;
			if(i.getName().equals("drill")) 
				basicSurvivalXP = 500;
			else 
				basicSurvivalXP = 100;
			
		} else { //else if(i.getItem().getType().equals("miscellaneous")) || this.getItem() instanceof Tool || this.getItem() instanceof WaterContainer
		
			basicSurvivalXP = 50;
			
		}
		
	}

	// Getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getAmtGet() {
		return amtGet;
	}

	public void setAmtGet(int amtGet) {
		this.amtGet = amtGet;
	}
	
	public boolean isStructure() {
		return structure;
	}
	
	public void setStructure(boolean structure) {
		this.structure = structure;
	}

	public int getWallType() {
		return wallType;
	}

	public void setWallType(int wallType) {
		this.wallType = wallType;
	}

	public int getBasicSurvivalXP() {
		return basicSurvivalXP;
	}

	public void setBasicSurvivalXP(int basicSurvivalXP) {
		this.basicSurvivalXP = basicSurvivalXP;
	}

	public int getCombatXP() {
		return combatXP;
	}

	public void setCombatXP(int combatXP) {
		this.combatXP = combatXP;
	}

	public int getCookingXP() {
		return cookingXP;
	}

	public void setCookingXP(int cookingXP) {
		this.cookingXP = cookingXP;
	}

	public int getBuildingXP() {
		return buildingXP;
	}

	public void setBuildingXP(int buildingXP) {
		this.buildingXP = buildingXP;
	}

}