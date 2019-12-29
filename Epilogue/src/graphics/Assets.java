package graphics;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import alphaPackage.ControlCenter;

/*
 * this class contains all game assets (most, if not all images)
 */
public class Assets {

	// Fonts
	public static InputStream is;
	public static Font font16;
	public static Font font21;
	public static Font font28;
	public static Font font36;

	// Backgrounds
	public static BufferedImage nightBlack;
	
	// Tiles
	public static BufferedImage dirtTile, dirt2Tile, dirt3Tile, dirtETile, stoneWallTile, corruptedTile, 
			naturalTile, natural2Tile, natural3Tile, semiDesertTile, infectedTile, infected2Tile, infected3Tile;
	public static BufferedImage[] waterTile, water2Tile, water3Tile, water4Tile, water5Tile, water6Tile, water7Tile,
			water8Tile, water9Tile, currentTile, current2Tile , water10Tile, water11Tile, water12Tile, water13Tile, 
			dirtTileEdge, dirt2TileEdge, dirtTileDiagonal;
	
	// Floors
	public static BufferedImage woodenPlatform, stonePlatform, metalPlatform;
	
	// UIs
	public static BufferedImage[] selectButton, sLeft, sRight, inventoryButton, equipmentButton, menuBackground, begin,
			help, options, credit, exit, blank;
	public static BufferedImage bar, inventory, handCraft, mainHand, offHand, craft, selectedCraft, playerVitals,
			chestInterface;
	public static BufferedImage healthIcon, hungerIcon, armorIcon, thirstIcon, energyIcon, globe, epilogue, worldSelectionInterface,
	worldCreationInterface, characterSelectionInterface;
	public static BufferedImage[] up, down, play, delete, createWorld, normal, hardcore, small, medium, large, continueButton;
	public static BufferedImage[] materialButton, weaponButton, armorButton, foodButton, structureButton, disableButton, resume, exitToMenu;
	public static BufferedImage materialButton2, weaponButton2, armorButton2, foodButton2, structureButton2, gameMenu, escaped, died;
		
	// effects
	public static BufferedImage poison, foodPoison, invisibility, swiftness, wound, contamination, 
		bloodlust, warmth, tired, anger, heavyArmor, full, high, bleeding, odor, odorless, burning, feaver, exaust;
	public static BufferedImage[] bossSmoke;
	
	// character
	public static BufferedImage[] rodMove, rodStandAttack, rodWalkAttack, rodRunAttack,
		rayMove, rayStandAttack, rayWalkAttack, rayRunAttack,
		batashMove, batashStandAttack, batashWalkAttack, batashRunAttack,
		sinaiMove, sinaiStandAttack, sinaiWalkAttack, sinaiRunAttack,
		parisMove, parisStandAttack, parisWalkAttack, parisRunAttack;
	public static BufferedImage rodIdle, rayIdle, batashIdle, sinaiIdle, parisIdle;
	public static BufferedImage rodArm, rayArm, batashArm, sinaiArm, parisArm;

	// staticEntity
	public static BufferedImage tree1, rock1, agave1, agave2, cactus1, cactus2, giantStingerCactus1,
			giantStingerCactus2, woodenCrate, smelter, smelterActive, autoCooker, autoCookerActive, smithingTable, woodenTable, workbench
			, shroomPile1, shroomPile2, prettyShroomPlant, brainFunguiPlant,
			grass1, grass2, grass3, grass4, grass5, cannon, woodenFenceHorizontal, woodenFenceVertical, 
			woodenGateHorizontalClosed, woodenGateHorizontalOpen, woodenGateVerticalClosed, woodenGateVerticalOpen, powerAdaptorOn, powerAdaptorOff,
			heavyPulse, rapidPulse, mine, tent, mattress, heavyPulseFire, rapidPulseFire, heavyPulseItem, rapidPulseItem, researchTable, cave, 
			infectedTree, spineBush, sentrySpikeSprout, sentrySpike, purifier, pineTree, bush1, bush2, woodenStick,
			metalCrate, vault, spaceShuttle, spaceShuttleBroken,
			ruinPiece1, ruinPiece2, ruinPiece3, ruinPiece4, ruinPiece5, ruinPiece6, stoneWallHorizontal, stoneWallVertical, 
			stoneGateHorizontalClosed, stoneGateHorizontalOpen, stoneGateVerticalClosed, stoneGateVerticalOpen, metalWallHorizontal, 
			metalWallVertical, metalGateHorizontalClosed, metalGateHorizontalOpen, metalGateVerticalClosed, metalGateVerticalOpen, sapling;
	public static BufferedImage[] hive, trashBag, campfire, powerGenerator, lampPost, smoke, artileryBase, vileEmbryo, disintegrator;

	// creatures
	public static BufferedImage[] beetleleft, beetleright, sentryLeft, sentryRight, chickenLeft, chickenRight,
			beetleRedLeft, beetleRedRight, phasmatodea, sentryBroodMother, ghoul, scorpion, sentinel, ostrich,
			deerDeer, deer, mutatedDeer, mutatedChicken, goat, crazedGoat, boar, sandCreepling, scavenger, probe, scavenging, 
			sentryMajor, sentryReplete, sandCreepIn, sandCreepOut, sandCreepActive, sandCreepGround, sandCreepUnderground, awakenedSentinel, 
			awakenedSentinelGun, sleepingSentinel, packAlpha, packMember, scorpionDiveIn;

	// miscellaneous items
	public static BufferedImage burntLog, beetleMembrane, spike, rock, woodenPlank, feather, silk, rope;
	public static BufferedImage coal, tin, bronze, zinc, iron, gold, titanium, tungsten, tinIngot, bronzeIngot, metalPlate,
			zincIngot, ironIngot, goldIngot, titaniumIngot, tungstenIngot, leather, fur, gear, blueprint, sac, waterContainer,
			pipeHead, stick, researchKit, workbenchToolkit, smithingToolkit, sap, ashe, purificationSink, torch, battery, ironBar,
			gunFrame, glockSide, glockBarrel, desertEagleSide, desertEagleBarrel, trigger, repairKit, motor, reciever, mGunGrip, 
			mGunHandel, mGunBarrel, fNozzle, fFuelTank, fGrip, fTubing, fTorch, turboCharger, sparkPlug, compressorWheel, crankShaft;

	// armor
	public static BufferedImage pan, goldenPan;
	public static BufferedImage panLeft, panRight, goldenPanLeft, goldenPanRight;
	public static BufferedImage bronzeArm, zincArm, ironArm, titaniumArm, tungstenArm;
	public static BufferedImage bronzeHelmet, bronzeChestplate, bronzeLegging, bronzeBoots, bronzeGauntlets,
		zincHelmet, zincChestplate, zincLegging, zincBoots, zincGauntlets,
		ironHelmet, ironChestplate, ironLegging, ironBoots, ironGauntlets,
		titaniumHelmet, titaniumChestplate, titaniumLegging, titaniumBoots, titaniumGauntlets,
		tungstenHelmet, tungstenChestplate, tungstenLegging, tungstenBoots, tungstenGauntlets;
	public static BufferedImage[] bronzeHelmetActive, bronzeChestplateActive, bronzeLeggingActive, bronzeBootsActive, bronzeGauntletsActive,
		zincHelmetActive, zincChestplateActive, zincLeggingActive, zincBootsActive, zincGauntletsActive,
		ironHelmetActive, ironChestplateActive, ironLeggingActive, ironBootsActive, ironGauntletsActive,
		titaniumHelmetActive, titaniumChestplateActive, titaniumLeggingActive, titaniumBootsActive, titaniumGauntletsActive,
		tungstenHelmetActive, tungstenChestplateActive, tungstenLeggingActive, tungstenBootsActive, tungstenGauntletsActive;
	public static BufferedImage bronzeHelmetStanding, bronzeChestplateStanding, bronzeLeggingStanding, bronzeBootsStanding, bronzeGauntletsStanding,
		zincHelmetStanding, zincChestplateStanding, zincLeggingStanding, zincBootsStanding, zincGauntletsStanding,
		ironHelmetStanding, ironChestplateStanding, ironLeggingStanding, ironBootsStanding, ironGauntletsStanding,
		titaniumHelmetStanding, titaniumChestplateStanding, titaniumLeggingStanding, titaniumBootsStanding, titaniumGauntletsStanding,
		tungstenHelmetStanding, tungstenChestplateStanding, tungstenLeggingStanding, tungstenBootsStanding, tungstenGauntletsStanding;
	public static BufferedImage cloakingDevice;

	// weapons
	public static BufferedImage bat, katana, bronzeBlade, zincBlade, ironSword, steelSword, titaniumClaws, tungstenMace,
		giantSawBlade, woodenClub, stoneClub, spikeClub, darkSaber;
	
	// tools
	public static BufferedImage woodenAxe, stoneAxe, bronzeAxe, zincAxe, chainsaw, woodenPick, stonePick, bronzePick,
		zincPick, drill, shovel;

	// ranged
	public static BufferedImage megashakalaka, glock, flamethrower, woodenBow, desertEagle, bronzeBow, zincBow, ironBow, ravager, pulseRifle;
	public static BufferedImage glockActive, desertEagleActive, megashakalakaActive, flameThrowerActive, woodenBowActive, 
		bronzeBowActive, zincBowActive, ironBowActive, ravagerActive, pulseRifleActive;
	public static BufferedImage AMM1D, fuel, XM214, bulletFire, flame, arrow, arrowItem, sandShotUp, sandShotDown;

	//food
	public static BufferedImage rawChicken, cookedChicken, rottenChicken, suspiciousChicken, cookedSuspiciousChicken,
	rawMorsel, cookedMorsel, rottenMorsel, suspiciousMorsel, cookedSuspiciousMorsel,
	rawMeat, cookedMeat, rottenMeat, suspiciousMeat, cookedSuspiciousMeat,
	vegeMeat, bugMeat, cookedBugMeat, rottenBugMeat, shroom, prettyShroom, brainFungui,
	scorpionTail, poisonCombo, glucose, salad, mushroomChicken, monsterDinner, bossiliciousMeal, 
	extrafloralNectar, bugMesh, nectarBit;

	// trash
	public static BufferedImage semiconductor, printedCircuitBoard, rektTinCan, brokenVaultDoor, brokenWatch, key,
			brokenGolfClub, blackWire, redWire, teddyBear, diaper, oldShoe, rot;

	public static void init() {

		// Fonts
		// ----------------------------------------------------------------------------
		
		
		try {
			is = Assets.class.getResourceAsStream("/fonts/Silkscreen.ttf");
			Font baseFont = Font.createFont(Font.TRUETYPE_FONT, is);
		    font16 = baseFont.deriveFont(Font.PLAIN, 16);
		    font21 = baseFont.deriveFont(Font.PLAIN, 21);
		    font28 = baseFont.deriveFont(Font.PLAIN, 28);
		    font36 = baseFont.deriveFont(Font.PLAIN, 36);
		    
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		font16 = new Font("serif", Font.PLAIN, 16);
		font21 = new Font("serif", Font.PLAIN, 21);
		font28 = new Font("serif", Font.PLAIN, 28);
		font36 = new Font("serif", Font.PLAIN, 36);
		*/
		
		//Backgrounds
		//-----------------------------------------------------------------------------
		
		try {
			

			Spritesheet nightB = new Spritesheet(ImageIO.read(Assets.class.getResource("/background/nightBlk.png")));
			Spritesheet dirt1 = new Spritesheet(ImageIO.read(Assets.class.getResource("/tile/dirtTile.png")));
			
			nightBlack = nightB.crop(0, 0, 500, 264);
			dirtTile = dirt1.crop(0, 0, 32, 32);
			

			// Tiles
			// ----------------------------------------------------------------------------
			
			Spritesheet dirt2 = new Spritesheet(ImageIO.read(Assets.class.getResource("/tile/dirtTile2.png")));
			Spritesheet dirt3 = new Spritesheet(ImageIO.read(Assets.class.getResource("/tile/dirtTile3.png")));
			Spritesheet dirte = new Spritesheet(ImageIO.read(Assets.class.getResource("/tile/dirtTileExploded.png")));
			Spritesheet stoneWT = new Spritesheet(ImageIO.read(Assets.class.getResource("/tile/stoneWallTile.png")));

			Spritesheet NaturalTile = new Spritesheet(ImageIO.read(Assets.class.getResource("/tile/natural1.png")));
			Spritesheet Natural2Tile = new Spritesheet(ImageIO.read(Assets.class.getResource("/tile/natural2.png")));
			Spritesheet Natural3Tile = new Spritesheet(ImageIO.read(Assets.class.getResource("/tile/natural3.png")));
			//Spritesheet ForestTile = new Spritesheet(ImageIO.read(Assets.class.getResource("/tile/natural.png")));
			Spritesheet SemiDesertTile = new Spritesheet(ImageIO.read(Assets.class.getResource("/tile/semiDesertTile.png")));
			//Spritesheet WasteTile = new Spritesheet(ImageIO.read(Assets.class.getResource("/tile/CorruptedTile.png")));
			//Spritesheet RuinsTile = new Spritesheet(ImageIO.read(Assets.class.getResource("/tile/CorruptedTile.png")));
			Spritesheet CorruptedTile = new Spritesheet(ImageIO.read(Assets.class.getResource("/tile/CorruptedTile.png")));
			Spritesheet InfectedTile = new Spritesheet(ImageIO.read(Assets.class.getResource("/tile/infectedTile1.png")));
			Spritesheet Infected2Tile = new Spritesheet(ImageIO.read(Assets.class.getResource("/tile/infectedTile2.png")));
			Spritesheet Infected3Tile = new Spritesheet(ImageIO.read(Assets.class.getResource("/tile/infectedTile3.png")));
			
			Spritesheet WaterTile = new Spritesheet(ImageIO.read(Assets.class.getResource("/tile/waterEdges.png")));
			Spritesheet CurrentTile = new Spritesheet(ImageIO.read(Assets.class.getResource("/tile/current1.png")));
			Spritesheet Current2Tile = new Spritesheet(ImageIO.read(Assets.class.getResource("/tile/current2.png")));
			Spritesheet Water2Tile = new Spritesheet(ImageIO.read(Assets.class.getResource("/tile/waterEdges2.png")));

			Spritesheet DirtTileEdge = new Spritesheet(ImageIO.read(Assets.class.getResource("/tile/dirtTileEdge.png")));
			Spritesheet Dirt2TileEdge = new Spritesheet(ImageIO.read(Assets.class.getResource("/tile/dirtTileEdge2.png")));
			Spritesheet DirtTileDiagonal = new Spritesheet(ImageIO.read(Assets.class.getResource("/tile/dirtDiagonal.png")));
			
			
			dirt2Tile = dirt2.crop(0, 0, 32, 32);
			dirt3Tile = dirt3.crop(0, 0, 32, 32);
			dirtETile = dirte.crop(0, 0, 32, 32);
			stoneWallTile = stoneWT.crop(0, 0, 32, 32);

			naturalTile = NaturalTile.crop(0, 0, 32, 32);
			natural2Tile = Natural2Tile.crop(0, 0, 32, 32);
			natural3Tile = Natural3Tile.crop(0, 0, 32, 32);
			semiDesertTile = SemiDesertTile.crop(0, 0, 32, 32);
			corruptedTile = CorruptedTile.crop(0, 0, 32, 32);
			infectedTile = InfectedTile.crop(0, 0, 32, 32);
			infected2Tile = Infected2Tile.crop(0, 0, 32, 32);
			infected3Tile = Infected3Tile.crop(0, 0, 32, 32);
			
			waterTile = new BufferedImage[2]; 
			water2Tile = new BufferedImage[2]; 
			water3Tile = new BufferedImage[2]; 
			water4Tile = new BufferedImage[2]; 
			water5Tile = new BufferedImage[2]; 
			water6Tile = new BufferedImage[2]; 
			water7Tile= new BufferedImage[2]; 
			water8Tile = new BufferedImage[2]; 
			water9Tile = new BufferedImage[2]; 
			currentTile = new BufferedImage[2]; 
			current2Tile = new BufferedImage[2]; 
			water10Tile = new BufferedImage[2]; 
			water11Tile = new BufferedImage[2]; 
			water12Tile = new BufferedImage[2]; 
			water13Tile = new BufferedImage[2]; 
			
			waterTile[0] = WaterTile.crop(0, 0, 32, 32);
			water2Tile[0] = WaterTile.crop(32, 0, 32, 32);
			water3Tile[0] = WaterTile.crop(64, 0, 32, 32);
			water4Tile[0] = WaterTile.crop(0, 32, 32, 32);
			water5Tile[0] = WaterTile.crop(32, 32, 32, 32);
			water6Tile[0] = WaterTile.crop(64, 32, 32, 32);
			water7Tile[0] = WaterTile.crop(0, 64, 32, 32);
			water8Tile[0] = WaterTile.crop(32, 64, 32, 32);
			water9Tile[0] = WaterTile.crop(64, 64, 32, 32);
			currentTile[0] = CurrentTile.crop(0, 0, 32, 32);
			current2Tile[0] = Current2Tile.crop(0, 0, 32, 32);
			water10Tile[0] = Water2Tile.crop(0, 0, 32, 32);
			water11Tile[0] = Water2Tile.crop(32, 0, 32, 32);
			water12Tile[0] = Water2Tile.crop(0, 32, 32, 32);
			water13Tile[0] = Water2Tile.crop(32, 32, 32, 32);
			
			waterTile[1] = WaterTile.crop(0, 96, 32, 32);
			water2Tile[1] = WaterTile.crop(32, 96, 32, 32);
			water3Tile[1] = WaterTile.crop(64, 96, 32, 32);
			water4Tile[1] = WaterTile.crop(0, 128, 32, 32);
			water5Tile[1] = WaterTile.crop(32, 128, 32, 32);
			water6Tile[1] = WaterTile.crop(64, 128, 32, 32);
			water7Tile[1] = WaterTile.crop(0, 160, 32, 32);
			water8Tile[1] = WaterTile.crop(32, 160, 32, 32);
			water9Tile[1] = WaterTile.crop(64, 160, 32, 32);
			currentTile[1] = CurrentTile.crop(0, 32, 32, 32);
			current2Tile[1] = Current2Tile.crop(0, 32, 32, 32);
			water10Tile[1] = Water2Tile.crop(0, 64, 32, 32);
			water11Tile[1] = Water2Tile.crop(32, 64, 32, 32);
			water12Tile[1] = Water2Tile.crop(0, 96, 32, 32);
			water13Tile[1] = Water2Tile.crop(32, 96, 32, 32);
			
			dirtTileEdge = new BufferedImage[9];
			dirt2TileEdge = new BufferedImage[4];
			dirtTileDiagonal = new BufferedImage[4];
			
			dirtTileEdge[0] = DirtTileEdge.crop(0, 0, 32, 32);
			dirtTileEdge[1] = DirtTileEdge.crop(32, 0, 32, 32);
			dirtTileEdge[2] = DirtTileEdge.crop(64, 0, 32, 32);
			dirtTileEdge[3] = DirtTileEdge.crop(0, 32, 32, 32);
			dirtTileEdge[4] = DirtTileEdge.crop(32, 32, 32, 32);
			dirtTileEdge[5] = DirtTileEdge.crop(64, 32, 32, 32);
			dirtTileEdge[6] = DirtTileEdge.crop(0, 64, 32, 32);
			dirtTileEdge[7] = DirtTileEdge.crop(32, 64, 32, 32);
			dirtTileEdge[8] = DirtTileEdge.crop(64, 64, 32, 32);
			
			dirt2TileEdge[0] = Dirt2TileEdge.crop(32, 0, 32, 32);
			dirt2TileEdge[1] = Dirt2TileEdge.crop(0, 32, 32, 32);
			dirt2TileEdge[2] = Dirt2TileEdge.crop(65, 32, 31, 32);
			dirt2TileEdge[3] = Dirt2TileEdge.crop(32, 64, 32, 32);
			
			dirtTileDiagonal[0] = DirtTileDiagonal.crop(0, 0, 32, 32);
			dirtTileDiagonal[1] = DirtTileDiagonal.crop(32, 0, 32, 32);
			dirtTileDiagonal[2] = DirtTileDiagonal.crop(0, 32, 32, 32);
			dirtTileDiagonal[3] = DirtTileDiagonal.crop(32, 32, 32, 32);
			
			// Floors
			// ----------------------------------------------------------------------------
			Spritesheet WoodenPlatform = new Spritesheet(ImageIO.read(Assets.class.getResource("/platform/woodenPlatform.png")));
			Spritesheet StonePlatform = new Spritesheet(ImageIO.read(Assets.class.getResource("/platform/stonePlatform.png")));
			Spritesheet MetalPlatform = new Spritesheet(ImageIO.read(Assets.class.getResource("/platform/metalPlatform.png")));
			
			woodenPlatform = WoodenPlatform.crop(0, 0, 32, 32);
			stonePlatform = StonePlatform.crop(0, 0, 32, 32);
			metalPlatform = MetalPlatform.crop(0, 0, 32, 32);
			
			// UIs
			// ----------------------------------------------------------------------------
			Spritesheet select1 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/complete1.png")));
			Spritesheet select2 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/complete2.png")));
			Spritesheet sRight1 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/right1.png")));
			Spritesheet sRight2 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/right2.png")));
			Spritesheet sLeft1 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/left1.png")));
			Spritesheet sLeft2 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/left2.png")));
			Spritesheet barSheet = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/bar.png")));
			Spritesheet hunger = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/hunger.png")));
			Spritesheet thirst = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/thirst.png")));
			Spritesheet health = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/health.png")));
			Spritesheet energy = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/energy.png")));
			Spritesheet armor = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/armor.png")));
			Spritesheet inventory1 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/inventory.png")));
			Spritesheet inventory2 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/inventory2.png")));
			Spritesheet equipment1 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/equipment.png")));
			Spritesheet equipment2 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/equipment2.png")));
			Spritesheet inventoryScreen = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/inventoryScreen.png")));
			Spritesheet handCraftScreen = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/craftingMenu.png")));
			Spritesheet Globe = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/globe.gif")));
			Spritesheet MenuBackground = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/menuBackground1.png")));
			Spritesheet MenuBackground2 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/menuBackground2.png")));
			Spritesheet MenuBackground3 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/menuBackground3.png")));
			Spritesheet MenuBackground4 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/menuBackground4.png")));
			Spritesheet MenuBackground5 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/menuBackground5.png")));
			Spritesheet MenuBackground6 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/menuBackground6.png")));
			Spritesheet MenuBackground7 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/menuBackground7.png")));
			Spritesheet MenuBackground8 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/menuBackground8.png")));
			Spritesheet MainHand = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/mainHand.png")));
			Spritesheet OffHand = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/offHand.png")));
			Spritesheet Craft = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/craft.png")));
			Spritesheet SelectedCraft = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/selectedCraft.png")));
			Spritesheet PlayerVitals = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/playerVitals.png")));
			Spritesheet Begin1 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/begin1.png")));
			Spritesheet Begin2 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/begin2.png")));
			Spritesheet Exit1 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/exit1.png")));
			Spritesheet Exit2 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/exit2.png")));
			Spritesheet Options1 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/options1.png")));
			Spritesheet Options2 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/options2.png")));
			Spritesheet Credits1 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/credits1.png")));
			Spritesheet Credits2 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/credits2.png")));
			Spritesheet Help1 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/help1.png")));
			Spritesheet Help2 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/help2.png")));
			Spritesheet Epilogue = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/epilogue.png")));
			Spritesheet ChestInterface = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/chestInterface.png")));
			Spritesheet Effects = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/effects.png")));
			Spritesheet Up1 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/up1.png")));
			Spritesheet Up2 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/up2.png")));
			Spritesheet Down1 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/down1.png")));
			Spritesheet Down2 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/down2.png")));
			Spritesheet Play1 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/play1.png")));
			Spritesheet Play2 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/play2.png")));
			Spritesheet Delete1 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/delete1.png")));
			Spritesheet Delete2 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/delete2.png")));
			Spritesheet WorldSelectionInterface = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/worldSelection.png")));
			Spritesheet CreateWorld1 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/createWorld1.png")));
			Spritesheet CreateWorld2 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/createWorld2.png")));
			Spritesheet WorldCreationInterface = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/worldCreation.png")));
			Spritesheet CharacterSelectionInterface = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/characterSelection.png")));
			Spritesheet Blank = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/blank.png")));
			Spritesheet Normal1 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/normal1.png")));
			Spritesheet Normal2 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/normal2.png")));
			Spritesheet Hardcore1 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/hardcore1.png")));
			Spritesheet Hardcore2 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/hardcore2.png")));
			Spritesheet Small1 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/small1.png")));
			Spritesheet Small2 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/small2.png")));
			Spritesheet Medium1 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/medium1.png")));
			Spritesheet Medium2 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/medium2.png")));
			Spritesheet Large1 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/large1.png")));
			Spritesheet Large2 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/large2.png")));
			Spritesheet Continue1 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/continue1.png")));
			Spritesheet Continue2 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/continue2.png")));
			Spritesheet Material1 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/materialButton1.png")));
			Spritesheet Material2 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/materialButton2.png")));
			Spritesheet Weapon1 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/weaponButton1.png")));
			Spritesheet Weapon2 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/weaponButton2.png")));
			Spritesheet Armor1 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/armorButton1.png")));
			Spritesheet Armor2 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/armorButton2.png")));
			Spritesheet Food1 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/foodButton1.png")));
			Spritesheet Food2 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/foodButton2.png")));
			Spritesheet Structure1 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/structureButton1.png")));
			Spritesheet Structure2 = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/structureButton2.png")));
			Spritesheet BossSmoke = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/bossSmoke.png")));
			Spritesheet GameMenu = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/gameMenu.png")));
	        Spritesheet ResumeExit = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/resumeExit.png")));
	        Spritesheet DisableButton = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/disableButton.png")));
	        Spritesheet GameOver = new Spritesheet(ImageIO.read(Assets.class.getResource("/UI/gameOver.png")));
			
			blank = new BufferedImage[2];
			blank[0] = Blank.crop(0, 0, 1, 1);
			blank[1] = Blank.crop(0, 0, 1, 1);
	        
			globe = Globe.crop(0, 0, 250, 250);

			menuBackground = new BufferedImage[14];
			menuBackground[0] = MenuBackground.crop(0, 0, 500, 264);
			menuBackground[1] = MenuBackground2.crop(0, 0, 500, 264);
			menuBackground[2] = MenuBackground3.crop(0, 0, 500, 264);
			menuBackground[3] = MenuBackground4.crop(0, 0, 500, 264);
			menuBackground[4] = MenuBackground5.crop(0, 0, 500, 264);
			menuBackground[5] = MenuBackground6.crop(0, 0, 500, 264);
			menuBackground[6] = MenuBackground7.crop(0, 0, 500, 264);
			menuBackground[7] = MenuBackground8.crop(0, 0, 500, 264);
			menuBackground[8] = MenuBackground7.crop(0, 0, 500, 264);
			menuBackground[9] = MenuBackground6.crop(0, 0, 500, 264);
			menuBackground[10] = MenuBackground5.crop(0, 0, 500, 264);
			menuBackground[11] = MenuBackground4.crop(0, 0, 500, 264);
			menuBackground[12] = MenuBackground3.crop(0, 0, 500, 264);
			menuBackground[13] = MenuBackground2.crop(0, 0, 500, 264);

			selectButton = new BufferedImage[2];
			selectButton[0] = select1.crop(0, 0, 850, 270);
			selectButton[1] = select2.crop(0, 0, 850, 270);

			sLeft = new BufferedImage[2];
			sRight = new BufferedImage[2];
			sLeft[0] = sLeft1.crop(0, 0, 15, 6);
			sLeft[1] = sLeft2.crop(0, 0, 15, 6);
			sRight[0] = sRight1.crop(0, 0, 15, 6);
			sRight[1] = sRight2.crop(0, 0, 15, 6);

			inventoryButton = new BufferedImage[2];
			equipmentButton = new BufferedImage[2];
			inventoryButton[0] = inventory1.crop(0, 0, 1024, 1024);
			inventoryButton[1] = inventory2.crop(0, 0, 1024, 1024);
			equipmentButton[0] = equipment1.crop(0, 0, 1024, 1024);
			equipmentButton[1] = equipment2.crop(0, 0, 1024, 1024);
			
			bar = barSheet.crop(0, 0, 148, 289);

			healthIcon = health.crop(0, 0, 1024, 1024);
			hungerIcon = hunger.crop(0, 0, 1024, 1024);
			thirstIcon = thirst.crop(0, 0, 1024, 1024);
			armorIcon = armor.crop(0, 0, 1024, 1024);
			energyIcon = energy.crop(0, 0, 1024, 1024);

			inventory = inventoryScreen.crop(0, 0, 128, 75);
			handCraft = handCraftScreen.crop(0, 0, 100, 75);

			mainHand = MainHand.crop(0, 0, 32, 32);
			offHand = OffHand.crop(0, 0, 32, 32);

			craft = Craft.crop(0, 0, 32, 32);
			selectedCraft = SelectedCraft.crop(0, 0, 32, 32);

			playerVitals = PlayerVitals.crop(0, 0, 46, 75);

			begin = new BufferedImage[2];
			help = new BufferedImage[2];
			options = new BufferedImage[2];
			credit = new BufferedImage[2];
			exit = new BufferedImage[2];

			begin[0] = Begin1.crop(0, 0, 236, 44);
			begin[1] = Begin2.crop(0, 0, 236, 44);

			credit[0] = Credits1.crop(0, 0, 332, 44);
			credit[1] = Credits2.crop(0, 0, 332, 44);

			exit[0] = Exit1.crop(0, 0, 178, 44);
			exit[1] = Exit2.crop(0, 0, 178, 44);

			options[0] = Options1.crop(0, 0, 332, 43);
			options[1] = Options2.crop(0, 0, 332, 43);

			help[0] = Help1.crop(0, 0, 188, 44);
			help[1] = Help2.crop(0, 0, 188, 44);
			
			resume = new BufferedImage[2];
	        exitToMenu = new BufferedImage[2];
	        disableButton = new BufferedImage[2];

	        gameMenu = GameMenu.crop(0, 0, 40, 53);
	        
	        resume[0] = ResumeExit.crop(0, 0, 43, 10);
	        resume[1] = ResumeExit.crop(0, 20, 43, 10);
	        
	        exitToMenu[0] = ResumeExit.crop(0, 10, 43, 10);
	        exitToMenu[1] = ResumeExit.crop(0, 30, 43, 10);
	        
	        disableButton[0] = DisableButton.crop(0, 0, 5, 5);
	        disableButton[1] = DisableButton.crop(0, 5, 5, 5);
	        
	        escaped = GameOver.crop(0, 0, 43, 10);
	        died = GameOver.crop(0, 10, 43, 10);
	        
			epilogue = Epilogue.crop(0, 0, 313, 88);

			chestInterface = ChestInterface.crop(0, 0, 128, 75);
			
			swiftness = Effects.crop(0, 0, 32, 32);
			wound = Effects.crop(32, 0, 32, 32);
			contamination = Effects.crop(64, 0, 32, 32);
			bloodlust = Effects.crop(96, 0, 32, 32);
			warmth = Effects.crop(0, 32, 32, 32);
			tired = Effects.crop(32, 32, 32, 32);
			anger = Effects.crop(64, 32, 32, 32);
			heavyArmor = Effects.crop(96, 32, 32, 32);
			poison = Effects.crop(0, 64, 32, 32);
			foodPoison = Effects.crop(32, 64, 32, 32);
			invisibility = Effects.crop(64, 64, 32, 32);
			full = Effects.crop(96, 64, 32, 32);
			high = Effects.crop(0, 96, 32, 32);
			bleeding = Effects.crop(32, 96, 32, 32);
			odor = Effects.crop(96, 96, 32, 32);
			odorless = Effects.crop(64, 96, 32, 32);
			burning = Effects.crop(0, 128, 32, 32);
			feaver = Effects.crop(32, 128, 32, 32);
			exaust = Effects.crop(64, 128, 32, 32);
			
			//World Creation
			createWorld = new BufferedImage[2];
			up = new BufferedImage[2];
			down = new BufferedImage[2];
			play = new BufferedImage[2];
			delete = new BufferedImage[2];
			normal = new BufferedImage[2];
			hardcore = new BufferedImage[2];
			small = new BufferedImage[2];
			medium = new BufferedImage[2];
			large = new BufferedImage[2];
			continueButton = new BufferedImage[2];
			
			createWorld[0] = CreateWorld1.crop(0, 0, 855, 96);
			createWorld[1] = CreateWorld2.crop(0, 0, 855, 96);
			up[0] = Up1.crop(0, 0, 9, 24);
			up[1] = Up2.crop(0, 0, 9, 24);
			down[0] = Down1.crop(0, 0, 9, 24);
			down[1] = Down2.crop(0, 0, 9, 24);
			play[0] = Play1.crop(0, 0, 9, 24);
			play[1] = Play2.crop(0, 0, 9, 24);
			delete[0] = Delete1.crop(0, 0, 9, 24);
			delete[1] = Delete2.crop(0, 0, 9, 24);
			worldSelectionInterface = WorldSelectionInterface.crop(0, 0, 100, 74);
			worldCreationInterface = WorldCreationInterface.crop(0, 0, 100, 74);
			characterSelectionInterface = CharacterSelectionInterface.crop(0, 0, 100, 74);
			normal[0] = Normal1.crop(0, 0, 700, 222);
			normal[1] = Normal2.crop(0, 0, 700, 222);
			hardcore[0] = Hardcore1.crop(0, 0, 850, 270);
			hardcore[1] = Hardcore2.crop(0, 0, 850, 270);
			small[0] = Small1.crop(0, 0, 600, 190);
			small[1] = Small2.crop(0, 0, 600, 190);
			medium[0] = Medium1.crop(0, 0, 600, 190);
			medium[1] = Medium2.crop(0, 0, 600, 190);
			large[0] = Large1.crop(0, 0, 600, 190);
			large[1] = Large2.crop(0, 0, 600, 190);
			continueButton[0] = Continue1.crop(0, 0, 850, 270);
			continueButton[1] = Continue2.crop(0, 0, 850, 270);
			
			//Crafting Buttons
			materialButton = new BufferedImage[2];
			weaponButton = new BufferedImage[2];
			armorButton = new BufferedImage[2];
			foodButton = new BufferedImage[2];
			structureButton = new BufferedImage[2];
			
			materialButton[0] = Material1.crop(0, 0, 14, 14);
			materialButton[1] = Material2.crop(0, 0, 14, 14);
			weaponButton[0] = Weapon1.crop(0, 0, 14, 14);
			weaponButton[1] = Weapon2.crop(0, 0, 14, 14);
			armorButton[0] = Armor1.crop(0, 0, 14, 14);
			armorButton[1] = Armor2.crop(0, 0, 14, 14);
			foodButton[0] = Food1.crop(0, 0, 14, 14);
			foodButton[1] = Food2.crop(0, 0, 14, 14);
			structureButton[0] = Structure1.crop(0, 0, 14, 14);
			structureButton[1] = Structure2.crop(0, 0, 14, 14);
			
			materialButton2 = Material2.crop(0, 0, 14, 14);
			weaponButton2 = Weapon2.crop(0, 0, 14, 14);
			armorButton2 = Armor2.crop(0, 0, 14, 14);
			foodButton2 = Food2.crop(0, 0, 14, 14);
			structureButton2 = Structure2.crop(0, 0, 14, 14);
			
			//Boss Smoke
			bossSmoke = new BufferedImage[5];
			
			bossSmoke[0] = BossSmoke.crop(0, 0, 64, 64);
			bossSmoke[1] = BossSmoke.crop(64, 0, 64, 64);
			bossSmoke[2] = BossSmoke.crop(128, 0, 64, 64);
			bossSmoke[3] = BossSmoke.crop(192, 0, 64, 64);
			bossSmoke[4] = BossSmoke.crop(256, 0, 64, 64);
			
			// Characters
			// ----------------------------------------------------------------------------

			Spritesheet RodMove = new Spritesheet(ImageIO.read(Assets.class.getResource("/player/rodMove.png")));
			Spritesheet RodAttack = new Spritesheet(ImageIO.read(Assets.class.getResource("/player/rodAttack.png")));
			Spritesheet RayMove = new Spritesheet(ImageIO.read(Assets.class.getResource("/player/rayMove.png")));
			Spritesheet RayAttack = new Spritesheet(ImageIO.read(Assets.class.getResource("/player/rayAttack.png")));
			Spritesheet BatashMove = new Spritesheet(ImageIO.read(Assets.class.getResource("/player/rajinMove.png")));
			Spritesheet BatashAttack = new Spritesheet(ImageIO.read(Assets.class.getResource("/player/rajinAttack.png")));
			Spritesheet SinaiMove = new Spritesheet(ImageIO.read(Assets.class.getResource("/player/BharatSinaiPeddiMove.png")));
			Spritesheet SinaiAttack = new Spritesheet(ImageIO.read(Assets.class.getResource("/player/BharatSinaiPeddiAttack.png")));
			Spritesheet ParisMove = new Spritesheet(ImageIO.read(Assets.class.getResource("/player/parisMove.png")));
			Spritesheet ParisAttack = new Spritesheet(ImageIO.read(Assets.class.getResource("/player/parisAttack.png")));
			Spritesheet RodArm = new Spritesheet(ImageIO.read(Assets.class.getResource("/player/rodArm.png")));

			rodMove = new BufferedImage[3];
			rodStandAttack = new BufferedImage[3];
			rodRunAttack = new BufferedImage[3];
			rayMove = new BufferedImage[3];
			rayStandAttack = new BufferedImage[3];
			rayRunAttack = new BufferedImage[3];
			batashMove = new BufferedImage[3];
			batashStandAttack = new BufferedImage[3];
			batashRunAttack = new BufferedImage[3];
			sinaiMove = new BufferedImage[3];
			sinaiStandAttack = new BufferedImage[3];
			sinaiRunAttack = new BufferedImage[3];
			parisMove = new BufferedImage[3];
			parisStandAttack = new BufferedImage[3];
			parisRunAttack = new BufferedImage[3];
			
			rodIdle = RodMove.crop(192, 0, 64, 64);
			rodArm = RodArm.crop(0, 0, 64, 64);

			for (int i = 0; i < 3; i++) {
				rodMove[i] = RodMove.crop(i*64, 0, 64, 64);
				rodRunAttack[i] = RodAttack.crop(i*64, 0, 64, 64);
				rodStandAttack[i] = RodAttack.crop(192, 0, 64, 64);
				
				rayMove[i] = RayMove.crop(i*64, 0, 64, 64);
				rayRunAttack[i] = RayAttack.crop(i*64, 0, 64, 64);
				rayStandAttack[i] = RayAttack.crop(192, 0, 64, 64);
				
				batashMove[i] = BatashMove.crop(i*64, 0, 64, 64);
				batashRunAttack[i] = BatashAttack.crop(i*64, 0, 64, 64);
				batashStandAttack[i] = BatashAttack.crop(192, 0, 64, 64);
				
				sinaiMove[i] = SinaiMove.crop(i*64, 0, 64, 64);
				sinaiRunAttack[i] = SinaiAttack.crop(i*64, 0, 64, 64);
				sinaiStandAttack[i] = SinaiAttack.crop(192, 0, 64, 64);
				
				parisMove[i] = ParisMove.crop(i*64, 0, 64, 64);
				parisRunAttack[i] = ParisAttack.crop(i*64, 0, 64, 64);
				parisStandAttack[i] = ParisAttack.crop(192, 0, 64, 64);
			}
			rodIdle = RodMove.crop(192, 0, 64, 64);
			rayIdle = RayMove.crop(192, 0, 64, 64);
			batashIdle = BatashMove.crop(192, 0, 64, 64);
			sinaiIdle = SinaiMove.crop(192, 0, 64, 64);
			parisIdle = ParisMove.crop(192, 0, 64, 64);

			// Static Entities
			// -----------------------------------------------------------------------------
			Spritesheet BurntTree = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/BurntTree.png")));
			Spritesheet Hive = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/SentryHive.png")));
			Spritesheet rockObstacle = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/rockObstacle.png")));
			Spritesheet BulletGunFire = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/bulletGunFire.png")));
			Spritesheet SandShotUp = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/sandShotUp.png")));
			Spritesheet SandShotDown = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/sandShotDown.png")));
			Spritesheet Flame = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/flame.png")));
			Spritesheet Arrow = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/arrow.png")));
			Spritesheet Agave1 = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/agave.png")));
			Spritesheet Agave2 = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/agave2.png")));
			Spritesheet Cactus1 = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/cactus.png")));
			Spritesheet Cactus2 = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/cactus2.png")));
			Spritesheet GiantStingerCactus1 = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/giantStingerCactus.png")));
			Spritesheet GiantStingerCactus2 = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/giantStingerCactus2.png")));
			Spritesheet TrashBag1 = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/trashBag1.png")));
			Spritesheet TrashBag2 = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/trashBag2.png")));
			Spritesheet TrashBag3 = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/trashBag3.png")));
			Spritesheet TrashBag4 = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/trashBag4.png")));
			Spritesheet TrashBag5 = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/trashBag5.png")));
			Spritesheet TrashBag6 = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/trashBag6.png")));
			Spritesheet WoodenCrate = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/woodenCrate.png")));
			Spritesheet Smelter = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/smelter.png")));
			Spritesheet SmelterActive = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/smelterActive.png")));
			Spritesheet AutoCooker = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/autoCooker.png")));
			Spritesheet AutoCookerActive = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/autoCookerActive.png")));
			Spritesheet SmithingTable = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/smithingStation.png")));
			Spritesheet WoodenTable = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/woodenTable.png")));
			Spritesheet Workbench = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/workBench.png")));
			Spritesheet ShroomPile = new Spritesheet(ImageIO.read(Assets.class.getResource("/food/shroomPile.png")));
			Spritesheet PrettyShroom = new Spritesheet(ImageIO.read(Assets.class.getResource("/food/prettyShroomPlant.png")));
			Spritesheet BrainFungui = new Spritesheet(ImageIO.read(Assets.class.getResource("/food/brainFunguiPlant.png")));
			Spritesheet Campfire = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/campfire.png")));
			Spritesheet PowerGenerator = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/powerGenerator.png")));
			Spritesheet LampPost = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/lampPost.png")));
			Spritesheet PowerAdaptorOn = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/powerAdaptorOn.png")));
			Spritesheet PowerAdaptorOff = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/powerAdaptorOff.png")));
			Spritesheet Smoke = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/smoke.png")));
			Spritesheet WoodenFenceHorizontal = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/woodenFence.png")));
			Spritesheet WoodenFenceVertical = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/woodenFenceVertical.png")));
			Spritesheet WoodenGateHorizontalClosed = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/woodenGateClosed.png")));
			Spritesheet WoodenGateHorizontalOpen = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/woodenGateOpen.png")));
			Spritesheet WoodenGateVerticalClosed = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/woodenVerticalGateClose.png")));
			Spritesheet WoodenGateVerticalOpen = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/woodenGateVerticalOpen.png")));
			Spritesheet ArtileryBase = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/turretBase.png")));
			Spritesheet HeavyPulse = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/cannonHead.png")));
			Spritesheet HeavyPulseFire = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/heavyPulse.png")));
			Spritesheet RapidPulse = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/rapidPulseHead.png")));
			Spritesheet RapidPulseFire = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/rapidPulse.png")));
			Spritesheet Tent = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/tent.png")));
			Spritesheet Mattress = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/mattress.png")));
			Spritesheet Landmine = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/landmine.png")));
			Spritesheet Turrets = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/turrets.png")));
			Spritesheet ResearchTable = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/researchTable.png")));
			Spritesheet Cave = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/cave.png")));
			Spritesheet InfectedTree = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/infectedTree.png")));
			Spritesheet SpineBush = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/spineBush.png")));
			Spritesheet SentrySpikeSprout = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/spikeOut.png")));
			Spritesheet SentrySpike = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/livingSpike.png")));
			Spritesheet VileEmbryo = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/vileEmbryo.png")));
			Spritesheet AwakenedSentinel = new Spritesheet(ImageIO.read(Assets.class.getResource("/creature/awakenedSentinel.png")));
			Spritesheet Purifier = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/purifier.png")));
			Spritesheet DisintegratorOff = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/disintegrator.png")));
			Spritesheet DisintegratorOn = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/disintegratorActive.png")));
			Spritesheet PineTree = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/pineTree.png")));
			Spritesheet Bush = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/bush.png")));
			Spritesheet WoodenStick = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/woodenStick.png")));
			Spritesheet MetalCrate = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/metalCrate.png")));
			Spritesheet Vault = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/vault.png")));
			Spritesheet SpaceShuttle = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/spaceShuttle.png")));
			Spritesheet RuinPiece1 = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/ruinPiece1.png")));
			Spritesheet RuinPiece2 = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/ruinPiece2.png")));
			Spritesheet RuinPiece3 = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/ruinPiece3.png")));
			Spritesheet RuinPiece4 = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/ruinPiece4.png")));
			Spritesheet RuinPiece5 = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/ruinPiece5.png")));
			Spritesheet RuinPiece6 = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/ruinPiece6.png")));
			Spritesheet StoneWallHorizontal = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/stoneWallHorizontal.png")));
			Spritesheet StoneWallVertical = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/stoneWallVertical.png")));
			Spritesheet StoneGateHorizontalClosed = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/stoneGateHorizontalClosed.png")));
			Spritesheet StoneGateHorizontalOpen = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/stoneGateHorizontalOpen.png")));
			Spritesheet StoneGateVerticalClosed = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/stoneGateVerticalClosed.png")));
			Spritesheet StoneGateVerticalOpen = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/stoneGateVerticalOpen.png")));
			Spritesheet MetalWallHorizontal = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/metalWall.png")));
			Spritesheet MetalWallVertical = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/metalWallVertical.png")));
			Spritesheet MetalGateHorizontalClosed = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/metalGateClosed.png")));
			Spritesheet MetalGateHorizontalOpen = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/metalGateOpen.png")));
			Spritesheet MetalGateVerticalClosed = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/metalGateVerticalClosed.png")));
			Spritesheet MetalGateVerticalOpen = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/metalGateVerticalOpen.png")));
			Spritesheet Sapling = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/liveSap.png")));
			Spritesheet Grass = new Spritesheet(ImageIO.read(Assets.class.getResource("/staticEntity/grass.png")));
			
			grass1 = Grass.crop(0, 0, 64, 64);
			grass2 = Grass.crop(64, 0, 64, 64);
			grass3 = Grass.crop(128, 0, 64, 64);
			grass4 = Grass.crop(192, 0, 64, 64);
			grass5 = Grass.crop(256, 0, 64, 64);
			
			sapling = Sapling.crop(0, 0, 32, 32);
			metalCrate = MetalCrate.crop(0, 0, 32, 32);
			vault = Vault.crop(0, 0, 320, 320);
			
			purifier = Purifier.crop(0, 0, 95, 95);
			
			disintegrator = new BufferedImage[2];
			disintegrator[0] = DisintegratorOff.crop(0, 0, 95, 95);
			disintegrator[1] = DisintegratorOn.crop(0, 0, 95, 95);
			
			vileEmbryo = new BufferedImage[2];
			vileEmbryo[0] = VileEmbryo.crop(0, 0, 64, 64);
			vileEmbryo[1] = VileEmbryo.crop(64, 0, 64, 64);
			
			heavyPulseItem = Turrets.crop(0, 0, 64, 64);
			rapidPulseItem = Turrets.crop(0, 64, 64, 64);
			
			heavyPulseFire = HeavyPulseFire.crop(0, 0, 9, 32);
			rapidPulseFire = RapidPulseFire.crop(0, 0, 3, 32);
			
			bulletFire = BulletGunFire.crop(0, 0, 9, 32);
			
			tent = Tent.crop(0, 0, 81, 81);
			mattress = Mattress.crop(0, 0, 64, 64);
			mine = Landmine.crop(0, 0, 32, 32);
			
			artileryBase = new BufferedImage[2];
			artileryBase[0] = ArtileryBase.crop(0, 0, 64, 64);
			artileryBase[1] = ArtileryBase.crop(0, 64, 64, 64);
			heavyPulse = HeavyPulse.crop(0, 0, 90, 90);
			rapidPulse = RapidPulse.crop(0, 0, 90, 90);
			
			tree1 = BurntTree.crop(0, 0, 128, 128);
			rock1 = rockObstacle.crop(0, 0, 70, 64);
			agave1 = Agave1.crop(0, 0, 64, 64);
			agave2 = Agave2.crop(0, 0, 64, 64);
			cactus1 = Cactus1.crop(0, 0, 64, 64);
			cactus2 = Cactus2.crop(0, 0, 64, 64);
			giantStingerCactus1 = GiantStingerCactus1.crop(0, 0, 64, 64);
			giantStingerCactus2 = GiantStingerCactus2.crop(0, 0, 64, 64);

			flame = Flame.crop(0, 0, 30, 7);
			arrow = Arrow.crop(0, 0, 32, 6);
			
			sandShotUp = SandShotUp.crop(0, 0, 11, 17);
			sandShotDown = SandShotDown.crop(0, 0, 11, 17);

			hive = new BufferedImage[2];

			hive[0] = Hive.crop(0, 0, 100, 64);
			hive[1] = Hive.crop(0, 64, 100, 64);

			trashBag = new BufferedImage[6];

			trashBag[0] = TrashBag1.crop(0, 0, 64, 64);
			trashBag[1] = TrashBag2.crop(0, 0, 64, 64);
			trashBag[2] = TrashBag3.crop(0, 0, 64, 64);
			trashBag[3] = TrashBag4.crop(0, 0, 64, 64);
			trashBag[4] = TrashBag5.crop(0, 0, 64, 64);
			trashBag[5] = TrashBag6.crop(0, 0, 64, 64);
			
			woodenCrate = WoodenCrate.crop(0, 0, 32, 32);
			smelter = Smelter.crop(0, 0, 95, 95);
			smelterActive = SmelterActive.crop(0, 0, 95, 95);
			autoCooker = AutoCooker.crop(0, 0, 64, 64);
			autoCookerActive = AutoCookerActive.crop(0, 0, 64, 64);
			smithingTable = SmithingTable.crop(0, 0, 50, 50);
			woodenTable = WoodenTable.crop(0, 0, 64, 64);
			workbench = Workbench.crop(0, 0, 64, 64);
			
			shroomPile1 = ShroomPile.crop(0, 0, 64, 64);
			shroomPile2 = ShroomPile.crop(64, 0, 64, 64);
			prettyShroomPlant = PrettyShroom.crop(0, 0, 64, 64);
			brainFunguiPlant = BrainFungui.crop(0, 0, 64, 64);
			
			campfire = new BufferedImage[5];
			campfire[0] = Campfire.crop(0, 0, 64, 64);
			campfire[1] = Campfire.crop(64, 0, 64, 64);
			campfire[2] = Campfire.crop(0, 64, 64, 64);
			campfire[3] = Campfire.crop(64, 64, 64, 64);
			campfire[4] = Campfire.crop(0, 128, 64, 64);
			
			powerGenerator = new BufferedImage[4];
			powerGenerator[0] = PowerGenerator.crop(0, 0, 64, 64);
			powerGenerator[1] = PowerGenerator.crop(64, 0, 64, 64);
			powerGenerator[2] = PowerGenerator.crop(0, 64, 64, 64);
			powerGenerator[3] = PowerGenerator.crop(64, 64, 64, 64);
			
			powerAdaptorOn = PowerAdaptorOn.crop(0, 0, 64, 64);
			powerAdaptorOff = PowerAdaptorOff.crop(0, 0, 64, 64);
			
			lampPost = new BufferedImage[2];
			lampPost[0] = LampPost.crop(0, 0, 64, 64);
			lampPost[1] = LampPost.crop(0, 64, 64, 64);
			
			smoke = new BufferedImage[5];

			smoke[0] = Smoke.crop(0, 0, 64, 64);
			smoke[1] = Smoke.crop(64, 0, 64, 64);
			smoke[2] = Smoke.crop(0, 64, 64, 64);
			smoke[3] = Smoke.crop(64, 64, 64, 64);
			smoke[4] = Smoke.crop(0, 128, 64, 64);
			
			woodenFenceHorizontal = WoodenFenceHorizontal.crop(0, 0, 64, 64);
			woodenFenceVertical = WoodenFenceVertical.crop(0, 0, 64, 96);
			woodenGateHorizontalClosed = WoodenGateHorizontalClosed.crop(0, 0, 64, 64);
			woodenGateHorizontalOpen = WoodenGateHorizontalOpen.crop(0, 0, 64, 64);
			woodenGateVerticalClosed = WoodenGateVerticalClosed.crop(0, 0, 64, 96);
			woodenGateVerticalOpen = WoodenGateVerticalOpen.crop(0, 0, 64, 96);
			
			researchTable = ResearchTable.crop(0, 0, 64, 64);
			
			cave = Cave.crop(0, 0, 64, 64);
			infectedTree = InfectedTree.crop(0, 0, 64, 64);
			spineBush = SpineBush.crop(0, 0, 128, 128);
			sentrySpikeSprout = SentrySpikeSprout.crop(0, 0, 64, 64);
			sentrySpike = SentrySpike.crop(0, 0, 64, 64);
			
			awakenedSentinel = new BufferedImage[3];
			awakenedSentinelGun = new BufferedImage[2];
			sleepingSentinel = new BufferedImage[2];
			
			awakenedSentinel[0] = AwakenedSentinel.crop(0, 0, 128, 128);
			awakenedSentinel[1] = AwakenedSentinel.crop(128, 0, 128, 128);
			awakenedSentinel[2] = AwakenedSentinel.crop(256, 0, 128, 128);
			awakenedSentinelGun[0] = AwakenedSentinel.crop(384, 0, 128, 128);
			awakenedSentinelGun[1] = AwakenedSentinel.crop(512, 0, 128, 128);
			sleepingSentinel[0] = AwakenedSentinel.crop(640, 0, 128, 128);
			sleepingSentinel[1] = AwakenedSentinel.crop(768, 0, 128, 128);
			
			pineTree = PineTree.crop(0, 0, 128, 128);
			bush1 = Bush.crop(0, 0, 64, 64);
			bush2 = Bush.crop(0, 64, 64, 64);
			woodenStick = WoodenStick.crop(0, 0, 32, 32);
			
			spaceShuttle = SpaceShuttle.crop(0, 0, 128, 128);
	        spaceShuttleBroken = SpaceShuttle.crop(0, 128, 128, 128);
			
			ruinPiece1 = RuinPiece1.crop(0, 0, 64, 64);
			ruinPiece2 = RuinPiece2.crop(0, 0, 64, 64);
			ruinPiece3 = RuinPiece3.crop(0, 0, 64, 80);
			ruinPiece4 = RuinPiece4.crop(0, 0, 64, 64);
			ruinPiece5 = RuinPiece5.crop(0, 0, 64, 64);
			ruinPiece6 = RuinPiece6.crop(0, 0, 64, 128);
			
			stoneWallHorizontal = StoneWallHorizontal.crop(0, 0, 64, 64);
			stoneWallVertical = StoneWallVertical.crop(0, 0, 64, 96);
			stoneGateHorizontalClosed = StoneGateHorizontalClosed.crop(0, 0, 64, 64);
			stoneGateHorizontalOpen = StoneGateHorizontalOpen.crop(0, 0, 64, 64);
			stoneGateVerticalClosed = StoneGateVerticalClosed.crop(0, 0, 64, 96);
			stoneGateVerticalOpen = StoneGateVerticalOpen.crop(0, 0, 64, 96);
			
			metalWallHorizontal = MetalWallHorizontal.crop(0, 0, 64, 64);
			metalWallVertical = MetalWallVertical.crop(0, 0, 64, 96);
			metalGateHorizontalClosed = MetalGateHorizontalClosed.crop(0, 0, 64, 64);
			metalGateHorizontalOpen = MetalGateHorizontalOpen.crop(0, 0, 64, 64);
			metalGateVerticalClosed = MetalGateVerticalClosed.crop(0, 0, 64, 96);
			metalGateVerticalOpen = MetalGateVerticalOpen.crop(0, 0, 64, 96);
			
			// Creatures
			// ----------------------------------------------------------------------------
			Spritesheet Beetle = new Spritesheet(ImageIO.read(Assets.class.getResource("/creature/Beetle.png")));
			Spritesheet BeetleRed = new Spritesheet(ImageIO.read(Assets.class.getResource("/creature/BeetleRed.png")));
			Spritesheet Sentry = new Spritesheet(ImageIO.read(Assets.class.getResource("/creature/Sentry.png")));
			Spritesheet ChickenLeft = new Spritesheet(ImageIO.read(Assets.class.getResource("/creature/chickenLeft.png")));
			Spritesheet ChickenRight = new Spritesheet(ImageIO.read(Assets.class.getResource("/creature/chickenRight.png")));
			Spritesheet Phasmatodea = new Spritesheet(ImageIO.read(Assets.class.getResource("/creature/stickBug.png")));
			Spritesheet SentryBroodMother = new Spritesheet(ImageIO.read(Assets.class.getResource("/creature/sentryBroodMother.png")));
			Spritesheet Ghoul = new Spritesheet(ImageIO.read(Assets.class.getResource("/creature/wonderingGhoul.png")));
			Spritesheet Scorpion = new Spritesheet(ImageIO.read(Assets.class.getResource("/creature/scorpion.png")));
			Spritesheet Sentinel = new Spritesheet(ImageIO.read(Assets.class.getResource("/creature/awakenedSentinel.png")));
			Spritesheet Ostrich = new Spritesheet(ImageIO.read(Assets.class.getResource("/creature/ostrich.png")));
			Spritesheet DeerDeer = new Spritesheet(ImageIO.read(Assets.class.getResource("/creature/deerDeer.png")));
			Spritesheet Deer = new Spritesheet(ImageIO.read(Assets.class.getResource("/creature/deer.png")));
			Spritesheet MutatedDeer = new Spritesheet(ImageIO.read(Assets.class.getResource("/creature/mutatedDeer.png")));
			Spritesheet MutatedChicken = new Spritesheet(ImageIO.read(Assets.class.getResource("/creature/mutatedChicken.png")));
			Spritesheet Goat = new Spritesheet(ImageIO.read(Assets.class.getResource("/creature/goat.png")));
			Spritesheet CrazedGoat = new Spritesheet(ImageIO.read(Assets.class.getResource("/creature/crazedGoat.png")));
			Spritesheet Scavenger = new Spritesheet(ImageIO.read(Assets.class.getResource("/creature/scavenger.png")));
			Spritesheet Scavenging = new Spritesheet(ImageIO.read(Assets.class.getResource("/creature/scavenging.png")));
			Spritesheet Boar = new Spritesheet(ImageIO.read(Assets.class.getResource("/creature/boar.png")));
			Spritesheet Probe = new Spritesheet(ImageIO.read(Assets.class.getResource("/creature/probe.png")));
			Spritesheet SentryMajor = new Spritesheet(ImageIO.read(Assets.class.getResource("/creature/sentryMajor.png")));
			Spritesheet SentryReplete = new Spritesheet(ImageIO.read(Assets.class.getResource("/creature/sentryReplete.png")));
			Spritesheet SandCreepling = new Spritesheet(ImageIO.read(Assets.class.getResource("/creature/sandCreepling.png")));
			Spritesheet SandCreep = new Spritesheet(ImageIO.read(Assets.class.getResource("/creature/sandCreepOut.png")));
			Spritesheet SandCreepActive = new Spritesheet(ImageIO.read(Assets.class.getResource("/creature/sandCreepActive.png")));
			Spritesheet SandCreepGround = new Spritesheet(ImageIO.read(Assets.class.getResource("/creature/sandCreepGround.png")));
			Spritesheet PackAlpha = new Spritesheet(ImageIO.read(Assets.class.getResource("/creature/packAlpha.png")));
			Spritesheet PackMember = new Spritesheet(ImageIO.read(Assets.class.getResource("/creature/packMember.png")));
			
			packAlpha = new BufferedImage[4];
			packAlpha[0] = PackAlpha.crop(0, 0, 64, 32);
			packAlpha[1] = PackAlpha.crop(0, 32, 64, 32);
			packAlpha[2] = PackAlpha.crop(0, 64, 64, 32);
			packAlpha[3] = PackAlpha.crop(0, 96, 64, 32);
			
			packMember = new BufferedImage[4];
			packMember[0] = PackMember.crop(0, 0, 64, 32);
			packMember[1] = PackMember.crop(0, 32, 64, 32);
			packMember[2] = PackMember.crop(0, 64, 64, 32);
			packMember[3] = PackMember.crop(0, 96, 64, 32);
			
			sandCreepActive = new BufferedImage[2];
			sandCreepActive[0] = SandCreepActive.crop(0, 0, 128, 128);
			sandCreepActive[1] = SandCreepActive.crop(0, 128, 128, 128);
			
			sandCreepGround = new BufferedImage[1];
			sandCreepGround[0] = SandCreepGround.crop(0, 0, 128, 128);
			
			sandCreepUnderground = new BufferedImage[1];
			sandCreepUnderground[0] = SandCreepGround.crop(0, 0, 1, 1);
			
			sandCreepIn = new BufferedImage[15];
			for(int i = 0; i < 15; i++) {
				sandCreepIn[i] = SandCreep.crop(128*i, 0, 128, 128);
			}
			
			sandCreepOut = new BufferedImage[15];
			for(int i = 14; i >= 0; i--) {
				sandCreepOut[14-i] = SandCreep.crop(128*i, 0, 128, 128);
			}
			
			scavenger = new BufferedImage[3];
			scavenger[0] = Scavenger.crop(0, 0, 80, 75);
			scavenger[1] = Scavenger.crop(80, 0, 80, 75);
			scavenger[2] = Scavenger.crop(0, 75, 80, 75);
			
			scavenging =new BufferedImage[2];
			scavenging[0] = Scavenging.crop(0, 0, 80, 75);
			scavenging[1] = Scavenging.crop(0, 75, 80, 75);
			
			boar = new BufferedImage[2];
			boar[0] = Boar.crop(0, 0, 64, 64);
			boar[1] = Boar.crop(0, 64, 64, 64);
			
			sandCreepling = new BufferedImage[2];
			sandCreepling[0] = SandCreepling.crop(0, 0, 64, 64);
			sandCreepling[1] = SandCreepling.crop(0, 64, 64, 64);
			
			probe = new BufferedImage[2];
			probe[0] = Probe.crop(0, 0, 32, 33);
			probe[1] = Probe.crop(0, 33, 32, 33);
			
			sentryMajor = new BufferedImage[2];
			sentryMajor[0] = SentryMajor.crop(0, 0, 64, 64);
			sentryMajor[1] = SentryMajor.crop(0, 64, 64, 64);
			
			sentryReplete = new BufferedImage[2];
			sentryReplete[0] = SentryReplete.crop(0, 0, 80, 80);
			sentryReplete[1] = SentryReplete.crop(0, 80, 80, 80);
			
			beetleleft = new BufferedImage[2];
			beetleright = new BufferedImage[2];

			beetleleft[0] = Beetle.crop(0, 0, 64, 64);
			beetleleft[1] = Beetle.crop(0, 64, 64, 64);
			beetleright[0] = Beetle.crop(64, 0, 64, 64);
			beetleright[1] = Beetle.crop(64, 64, 64, 64);

			beetleRedLeft = new BufferedImage[2];
			beetleRedRight = new BufferedImage[2];

			beetleRedLeft[0] = BeetleRed.crop(0, 0, 64, 64);
			beetleRedLeft[1] = BeetleRed.crop(0, 64, 64, 64);
			beetleRedRight[0] = BeetleRed.crop(64, 0, 64, 64);
			beetleRedRight[1] = BeetleRed.crop(64, 64, 64, 64);

			sentryLeft = new BufferedImage[2];
			sentryRight = new BufferedImage[2];

			sentryLeft[0] = Sentry.crop(0, 0, 64, 64);
			sentryLeft[1] = Sentry.crop(0, 64, 64, 64);
			sentryRight[0] = Sentry.crop(64, 0, 64, 64);
			sentryRight[1] = Sentry.crop(64, 64, 64, 64);

			chickenLeft = new BufferedImage[4];
			chickenRight = new BufferedImage[4];

			phasmatodea = new BufferedImage[2];
			phasmatodea[0] = Phasmatodea.crop(0, 0, 64, 64);
			phasmatodea[1] = Phasmatodea.crop(0, 64, 64, 64);
			
			/*
			sentinel = new BufferedImage[3];
			sentinel[0] = Sentinel.crop(0, 0, 128, 128);
			sentinel[1] = Sentinel.crop(0, 128, 128, 128);
			sentinel[2] = Sentinel.crop(128, 0, 128, 128);
			 */

			sentryBroodMother = new BufferedImage[2];
			sentryBroodMother[0] = SentryBroodMother.crop(0, 0, 128, 128);
			sentryBroodMother[1] = SentryBroodMother.crop(0, 128, 128, 128);

			ghoul = new BufferedImage[4];

			ghoul[0] = Ghoul.crop(0, 0, 50, 50);
			ghoul[1] = Ghoul.crop(50, 0, 50, 50);
			ghoul[2] = Ghoul.crop(0, 50, 50, 50);
			ghoul[3] = Ghoul.crop(50, 50, 50, 50);

			for (int i = 0; i < 4; i++) {
				chickenLeft[i] = ChickenLeft.crop(32 * i, 0, 32, 32);
				chickenRight[i] = ChickenRight.crop(32 * i, 0, 32, 32);
			}

			scorpion = new BufferedImage[2];
			scorpion[0] = Scorpion.crop(0, 0, 48, 96 / 2);
			scorpion[1] = Scorpion.crop(0, 96 / 2, 48, 96 / 2);
			
			ostrich = new BufferedImage[2];
			ostrich[0] = Ostrich.crop(0, 0, 64, 64);
			ostrich[1] = Ostrich.crop(0, 64, 64, 64);
			
			deer = new BufferedImage[3];
			deer[0] = Deer.crop(0, 0, 64, 64);
			deer[1] = Deer.crop(0, 64, 64, 64);
			deer[2] = Deer.crop(0, 64, 64, 64);
			
			deerDeer = new BufferedImage[3];
			deerDeer[0] = DeerDeer.crop(0, 0, 64, 64);
			deerDeer[1] = DeerDeer.crop(0, 64, 64, 64);
			deerDeer[2] = DeerDeer.crop(0, 64, 64, 64);

			mutatedDeer = new BufferedImage[3];
			mutatedDeer[0] = MutatedDeer.crop(0, 0, 64, 64);
			mutatedDeer[1] = MutatedDeer.crop(0, 64, 64, 64);
			mutatedDeer[2] = MutatedDeer.crop(0, 64, 64, 64);

			mutatedChicken = new BufferedImage[4];
			mutatedChicken[0] = MutatedChicken.crop(0, 0, 32, 32);
			mutatedChicken[1] = MutatedChicken.crop(32, 0, 32, 32);
			mutatedChicken[2] = MutatedChicken.crop(64, 0, 32, 32);
			mutatedChicken[3] = MutatedChicken.crop(96, 0, 32, 32);
			
			goat = new BufferedImage[3];
			goat[0] = Goat.crop(0, 0, 64, 64);
			goat[1] = Goat.crop(64, 0, 64, 64);
			goat[2] = Goat.crop(0, 64, 64, 64);
			
			crazedGoat = new BufferedImage[3];
			crazedGoat[0] = CrazedGoat.crop(0, 0, 64, 64);
			crazedGoat[1] = CrazedGoat.crop(64, 0, 64, 64);
			crazedGoat[2] = CrazedGoat.crop(0, 64, 64, 64);
			
			// Miscellaneous Items
			// ----------------------------------------------------------------------------
			Spritesheet logBurnt = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/BurntLog.png")));
			Spritesheet BeetleMembrane = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/BeetleMembrane.png")));
			Spritesheet Spike = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/BloodySpike.png")));
			Spritesheet Rock = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/rock.png")));
			Spritesheet Coal = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/coalChunk.png")));
			Spritesheet Tin = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/tinChunk.png")));
			Spritesheet Bronze = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/copperChunk.png")));
			Spritesheet Zinc = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/zincChunk.png")));
			Spritesheet Iron = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/ironChunk.png")));
			Spritesheet Gold = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/goldChunk.png")));
			Spritesheet Titanium = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/titaniumChunk.png")));
			Spritesheet Tungsten = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/tungstenChunk.png")));
			Spritesheet TinIngot = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/tinIngot.png")));
			Spritesheet BronzeIngot = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/copperIngot.png")));
			Spritesheet ZincIngot = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/zincIngot.png")));
			Spritesheet IronIngot = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/ironIngot.png")));
			Spritesheet GoldIngot = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/goldIngot.png")));
			Spritesheet TitaniumIngot = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/titaniumIngot.png")));
			Spritesheet TungstenIngot = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/tungstenIngot.png")));
			Spritesheet WoodenPlank = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/woodPlank.png")));
			Spritesheet Semiconductor = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/semiConductor.png")));
			Spritesheet PrintedCircuitBoard = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/printedCircuitBoard.png")));
			Spritesheet Feather = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/feather.png")));
			Spritesheet RektTinCan = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/rektTinCan.png")));
			Spritesheet BrokenVaultDoor = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/brokenVaultDoor.png")));
			Spritesheet BrokenWatch = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/brokenWatch.png")));
			Spritesheet Key = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/key.png")));
			Spritesheet BrokenGolfClub = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/brokenGolfClub.png")));
			Spritesheet BlackWire = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/blackWire.png")));
			Spritesheet RedWire = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/redWire.png")));
			Spritesheet TeddyBear = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/teddyBear.png")));
			Spritesheet Silk = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/silk.png")));
			Spritesheet Rope = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/rope.png")));
			Spritesheet Diaper = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/diaper.png")));
			Spritesheet OldShoe = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/oldShoe.png")));
			Spritesheet Rot = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/rot.png")));
			Spritesheet Leather = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/leather.png")));
			Spritesheet Fur = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/fur.png")));
			Spritesheet Gear = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/gear.png")));
			Spritesheet Blueprint = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/blueprint.png")));
			Spritesheet AMM1DBullet = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/AMM1D.png")));
			Spritesheet XM214Bullet = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/XM214.png")));
			Spritesheet Fuel = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/fuelTank.png")));
			Spritesheet ArrowItem = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/arrowItem.png")));
			Spritesheet SacItem = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/waterSac.png")));
			Spritesheet WaterContainerItem = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/metalBottle.png")));
			Spritesheet PipeHead = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/pipeHead.png")));
			Spritesheet Stick = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/woodenStick.png")));
			Spritesheet ResearchKit = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/researchTableKit.png")));
			Spritesheet WorkbenchToolkit = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/workbenchToolkit.png")));
			Spritesheet SmithingKit = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/smithingTableToolKit.png")));
			Spritesheet WoodenBoard = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/woodenBoard.png")));
			Spritesheet Sap = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/seed.png")));
			Spritesheet Ashe = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/ashe.png")));
			Spritesheet PurificationSink = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/purificationSink.png")));
			Spritesheet Torch = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/torch.png")));
			Spritesheet Battery = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/battery.png")));
			Spritesheet IronBar = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/ironBar.png")));
			Spritesheet GunFrame = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/gunFrame.png")));
			Spritesheet GlockSide = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/glockSide.png")));
			Spritesheet DesertEagleSide = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/DesertEagleSlide.png")));
			Spritesheet GlockBarrel = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/glockBarrel.png")));
			Spritesheet DesertEagleBarrel = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/DesertEagleBarrel.png")));
			Spritesheet Trigger = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/trigger.png")));
			Spritesheet MgunBarrel = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/MGunBarrel.png")));
			Spritesheet Motor = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/MGunMotor.png")));
			Spritesheet Reciever = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/MgunReceiver.png")));
			Spritesheet MgunHandel = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/MgunHandel.png")));
			Spritesheet MgunGrip = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/MgunGrip.png")));
			Spritesheet RepairKit = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/repairKit.png")));
			Spritesheet MetalPlate = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/metalPlate.png")));
			Spritesheet FTorch = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/FThrowerTorch.png")));
			Spritesheet FTubing = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/FThrowerTubing.png")));
			Spritesheet FNozzle = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/FThrowerNozzle.png")));
			Spritesheet FGrip = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/FThrowerGrip.png")));
			Spritesheet FFuelTank = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/FThrowerFuelTank.png")));
			Spritesheet TurboCharger = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/turbo_charger.png")));
			Spritesheet SparkPlug = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/spark_plug.png")));
			Spritesheet CompressorWheel = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/compressor_wheel.png")));
			Spritesheet CrankShaft = new Spritesheet(ImageIO.read(Assets.class.getResource("/materialItem/crank_shaft.png")));
			
			fTorch = FTorch.crop(0, 0, 32, 32);
			fTubing = FTubing.crop(0, 0, 32, 32);
			fNozzle = FNozzle.crop(0, 0, 32, 32);
			fGrip = FGrip.crop(0, 0, 32, 32);
			fFuelTank = FFuelTank.crop(0, 0, 32, 32);
			metalPlate = MetalPlate.crop(0, 0, 32, 32);
			motor = Motor.crop(0, 0, 32, 32);
			reciever = Reciever.crop(0, 0, 32, 32);
			mGunGrip = MgunGrip.crop(0, 0, 32, 32);
			mGunHandel = MgunHandel.crop(0, 0, 32, 32);
			mGunBarrel = MgunBarrel.crop(0, 0, 32, 32);
			ironBar = IronBar.crop(0, 0, 32, 32);
			gunFrame = GunFrame.crop(0, 0, 32, 32);
			glockSide = GlockSide.crop(0, 0, 32, 32);
			desertEagleSide = DesertEagleSide.crop(0, 0, 32, 32);
			glockBarrel = GlockBarrel.crop(0, 0, 32, 32);
			desertEagleBarrel = DesertEagleBarrel.crop(0, 0, 32, 32);
			trigger = Trigger.crop(0, 0, 32, 32);
			repairKit = RepairKit.crop(0, 0, 64, 64);
			turboCharger = TurboCharger.crop(0, 0, 64, 59);
			sparkPlug = SparkPlug.crop(0, 0, 64, 64);
			compressorWheel = CompressorWheel.crop(0, 0, 64, 54);
			crankShaft = CrankShaft.crop(0, 0, 64, 64);;
			
			battery = Battery.crop(0, 0, 32, 32);
			pipeHead = PipeHead.crop(0, 0, 32, 32);
			stick = Stick.crop(0, 0, 32, 32);
			researchKit = ResearchKit.crop(0, 0, 32, 32);
			workbenchToolkit = WorkbenchToolkit.crop(0, 0, 32, 32);
			smithingToolkit = SmithingKit.crop(0, 0, 32, 32);
			purificationSink = PurificationSink.crop(0, 0, 32, 32);
			sap = Sap.crop(0, 0, 32, 32);
			ashe = Ashe.crop(0, 0, 32, 32);
			torch = Torch.crop(0, 0, 32, 32);
			
			sac = SacItem.crop(0, 0, 32, 32);
			waterContainer = WaterContainerItem.crop(0, 0, 32, 32);
			
			AMM1D = AMM1DBullet.crop(0, 0, 32, 32);
			XM214 = XM214Bullet.crop(0, 0, 32, 32);
			fuel = Fuel.crop(0, 0, 32, 32);
			arrowItem = ArrowItem.crop(0, 0, 32, 32);
		 
			gear = Gear.crop(0, 0, 32, 32);
			blueprint = Blueprint.crop(0, 0, 32, 32);
			leather = Leather.crop(0, 0, 32, 32);
			fur = Fur.crop(0, 0, 32, 32);
			
			burntLog = logBurnt.crop(0, 0, 32, 32);
			beetleMembrane = BeetleMembrane.crop(0, 0, 64, 64);
			spike = Spike.crop(0, 0, 64, 64);
			rock = Rock.crop(0, 0, 64, 64);
			coal = Coal.crop(0, 0, 32, 32);
			tin = Tin.crop(0, 0, 32, 32);
			bronze = Bronze.crop(0, 0, 32, 32);
			zinc = Zinc.crop(0, 0, 32, 32);
			iron = Iron.crop(0, 0, 32, 32);
			titanium = Titanium.crop(0, 0, 32, 32);
			tungsten = Tungsten.crop(0, 0, 32, 32);
			gold = Gold.crop(0, 0, 32, 32);
			tinIngot = TinIngot.crop(0, 0, 32, 32);
			bronzeIngot = BronzeIngot.crop(0, 0, 32, 32);
			zincIngot = ZincIngot.crop(0, 0, 32, 32);
			ironIngot = IronIngot.crop(0, 0, 32, 32);
			titaniumIngot = TitaniumIngot.crop(0, 0, 32, 32);
			tungstenIngot = TungstenIngot.crop(0, 0, 32, 32);
			goldIngot = GoldIngot.crop(0, 0, 32, 32);
			woodenPlank = WoodenBoard.crop(0, 0, 32, 32);
			feather = Feather.crop(0, 0, 32, 32);
			silk = Silk.crop(0, 0, 32, 32);
			rope = Rope.crop(0, 0, 32, 32);

			semiconductor = Semiconductor.crop(0, 0, 32, 32);
			printedCircuitBoard = PrintedCircuitBoard.crop(0, 0, 32, 32);
			rektTinCan = RektTinCan.crop(0, 0, 32, 32);
			brokenVaultDoor = BrokenVaultDoor.crop(0, 0, 32, 32);
			brokenWatch = BrokenWatch.crop(0, 0, 32, 32);
			key = Key.crop(0, 0, 32, 32);
			brokenGolfClub = BrokenGolfClub.crop(0, 0, 32, 32);
			blackWire = BlackWire.crop(0, 0, 32, 32);
			redWire = RedWire.crop(0, 0, 32, 32);
			teddyBear = TeddyBear.crop(0, 0, 32, 32);
			oldShoe = OldShoe.crop(0, 0, 32, 32);
			rot = Rot.crop(0, 0, 32, 32);
			diaper = Diaper.crop(0, 0, 32, 32);

			// Armor
			// ----------------------------------------------------------------------------
			Spritesheet Pan = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/pan.png")));
			Spritesheet panIcon = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/panIcon.png")));
			Spritesheet goldenPanIcon = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/goldenPan.png")));
			
			Spritesheet BronzeHelmet = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/bronzeHelmet.png")));
			Spritesheet BronzeHelmetActive = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/bronzeHelmetActive.png")));
			Spritesheet BronzeChestplate = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/bronzeChestplate.png")));
			Spritesheet BronzeChestplateActive = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/bronzeChestplateActive.png")));
			Spritesheet BronzeLegging = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/bronzeLeggings.png")));
			Spritesheet BronzeLeggingActive = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/bronzeLeggingsActive.png")));
			Spritesheet BronzeBoots = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/bronzeBoots.png")));
			Spritesheet BronzeBootsActive = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/BronzeBootsActive.png")));
			Spritesheet BronzeGauntlet = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/bronzeGauntlet.png")));
			Spritesheet BronzeGauntletActive = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/bronzeGauntletActive.png")));
			Spritesheet BronzeArm = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/bronzeArm.png")));
			
			Spritesheet ZincHelmet = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/zincHelmet.png")));
			Spritesheet ZincHelmetActive = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/zincHelmetActive.png")));
			Spritesheet ZincChestplate = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/zincChestplate.png")));
			Spritesheet ZincChestplateActive = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/zincChestplateActive.png")));
			Spritesheet ZincLegging = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/zincLeggings.png")));
			Spritesheet ZincLeggingActive = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/zincLeggingsActive.png")));
			Spritesheet ZincBoots = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/zincBoots.png")));
			Spritesheet ZincBootsActive = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/zincBootsActive.png")));
			Spritesheet ZincGauntlet = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/zincGauntlet.png")));
			Spritesheet ZincGauntletActive = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/zincGauntletActive.png")));
			Spritesheet ZincArm = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/zincArm.png")));
			
			Spritesheet IronHelmet = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/ironHelmet.png")));
			Spritesheet IronHelmetActive = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/ironHelmetActive.png")));
			Spritesheet IronChestplate = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/ironChestplate.png")));
			Spritesheet IronChestplateActive = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/ironChestplateActive.png")));
			Spritesheet IronLegging = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/ironLeggings.png")));
			Spritesheet IronLeggingActive = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/ironLeggingsActive.png")));
			Spritesheet IronBoots = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/ironBoots.png")));
			Spritesheet IronBootsActive = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/ironBootsActive.png")));
			Spritesheet IronGauntlet = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/ironGauntlet.png")));
			Spritesheet IronGauntletActive = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/ironGauntletActive.png")));
			Spritesheet IronArm = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/ironArm.png")));
			
			Spritesheet TitaniumHelmet = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/titaniumHelmet.png")));
			Spritesheet TitaniumHelmetActive = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/titaniumHelmetActive.png")));
			Spritesheet TitaniumChestplate = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/titaniumChestplate.png")));
			Spritesheet TitaniumChestplateActive = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/titaniumChestplateActive.png")));
			Spritesheet TitaniumLegging = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/titaniumLeggings.png")));
			Spritesheet TitaniumLeggingActive = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/titaniumLeggingsActive.png")));
			Spritesheet TitaniumBoots = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/titaniumBoots.png")));
			Spritesheet TitaniumBootsActive = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/titaniumBootsActive.png")));
			Spritesheet TitaniumGauntlet = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/titaniumGauntlet.png")));
			Spritesheet TitaniumGauntletActive = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/titaniumGauntletActive.png")));
			Spritesheet TitaniumArm = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/titaniumArm.png")));
			
			Spritesheet TungstenHelmet = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/tungstenHelmet.png")));
			Spritesheet TungstenHelmetActive = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/tungstenHelmetActive.png")));
			Spritesheet TungstenChestplate = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/tungstenChestplate.png")));
			Spritesheet TungstenChestplateActive = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/tungstenChestplateActive.png")));
			Spritesheet TungstenLegging = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/tungstenLeggings.png")));
			Spritesheet TungstenLeggingActive = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/tungstenLeggingsActive.png")));
			Spritesheet TungstenBoots = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/tungstenBoots.png")));
			Spritesheet TungstenBootsActive = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/tungstenBootsActive.png")));
			Spritesheet TungstenGauntlet = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/tungstenGauntlet.png")));
			Spritesheet TungstenGauntletActive = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/tungstenGauntletActive.png")));
			Spritesheet TungstenArm = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/tungstenArm.png")));
			
			Spritesheet CloakingDevice = new Spritesheet(ImageIO.read(Assets.class.getResource("/armorItem/cloakingDevice.png")));
			
			cloakingDevice = CloakingDevice.crop(0, 0, 32, 32);
			
			panLeft = Pan.crop(39, 0, 39, 32);
			panRight = Pan.crop(0, 0, 39, 32);
			// goldenPanLeft = GoldenPan.crop(32, 0, 32, 32);
			// goldenPanRight = GoldenPan.crop(0, 0, 32, 32);

			pan = panIcon.crop(0, 0, 32, 32);
			goldenPan = goldenPanIcon.crop(0, 0, 32, 32);
			
			bronzeHelmet = BronzeHelmet.crop(0, 0, 32, 32);
			bronzeHelmetActive = new BufferedImage[3];
			bronzeHelmetActive[0] = BronzeHelmetActive.crop(0, 0, 64, 64);
			bronzeHelmetActive[1] = BronzeHelmetActive.crop(64, 0, 64, 64);
			bronzeHelmetActive[2] = BronzeHelmetActive.crop(0, 64, 64, 64);
			bronzeHelmetStanding = BronzeHelmetActive.crop(64, 64, 64, 64);
			
			bronzeChestplate = BronzeChestplate.crop(0, 0, 32, 32);
			bronzeChestplateActive = new BufferedImage[3];
			bronzeChestplateActive[0] = BronzeChestplateActive.crop(0, 0, 64, 64);
			bronzeChestplateActive[1] = BronzeChestplateActive.crop(64, 0, 64, 64);
			bronzeChestplateActive[2] = BronzeChestplateActive.crop(0, 64, 64, 64);
			bronzeChestplateStanding = BronzeChestplateActive.crop(64, 64, 64, 64);
			
			bronzeLegging = BronzeLegging.crop(0, 0, 32, 32);
			bronzeLeggingActive = new BufferedImage[3];
			bronzeLeggingActive[0] = BronzeLeggingActive.crop(0, 0, 64, 64);
			bronzeLeggingActive[1] = BronzeLeggingActive.crop(64, 0, 64, 64);
			bronzeLeggingActive[2] = BronzeLeggingActive.crop(0, 64, 64, 64);
			bronzeLeggingStanding = BronzeLeggingActive.crop(64, 64, 64, 64);

			bronzeBoots = BronzeBoots.crop(0, 0, 32, 32);
			bronzeBootsActive = new BufferedImage[3];
			bronzeBootsActive[0] = BronzeBootsActive.crop(0, 0, 64, 64);
			bronzeBootsActive[1] = BronzeBootsActive.crop(64, 0, 64, 64);
			bronzeBootsActive[2] = BronzeBootsActive.crop(0, 64, 64, 64);
			bronzeBootsStanding = BronzeBootsActive.crop(64, 64, 64, 64);
			
			bronzeGauntlets = BronzeGauntlet.crop(0, 0, 32, 32);
			bronzeGauntletsActive = new BufferedImage[3];
			bronzeGauntletsActive[0] = BronzeGauntletActive.crop(0, 0, 64, 64);
			bronzeGauntletsActive[1] = BronzeGauntletActive.crop(64, 0, 64, 64);
			bronzeGauntletsActive[2] = BronzeGauntletActive.crop(0, 64, 64, 64);
			bronzeGauntletsStanding = BronzeGauntletActive.crop(64, 64, 64, 64);
			
			zincHelmet = ZincHelmet.crop(0, 0, 32, 32);
			zincHelmetActive = new BufferedImage[3];
			zincHelmetActive[0] = ZincHelmetActive.crop(0, 0, 64, 64);
			zincHelmetActive[1] = ZincHelmetActive.crop(64, 0, 64, 64);
			zincHelmetActive[2] = ZincHelmetActive.crop(0, 64, 64, 64);
			zincHelmetStanding = ZincHelmetActive.crop(64, 64, 64, 64);

			zincChestplate = ZincChestplate.crop(0, 0, 32, 32);
			zincChestplateActive = new BufferedImage[3];
			zincChestplateActive[0] = ZincChestplateActive.crop(0, 0, 64, 64);
			zincChestplateActive[1] = ZincChestplateActive.crop(64, 0, 64, 64);
			zincChestplateActive[2] = ZincChestplateActive.crop(0, 64, 64, 64);
			zincChestplateStanding = ZincChestplateActive.crop(64, 64, 64, 64);

			zincLegging = ZincLegging.crop(0, 0, 32, 32);
			zincLeggingActive = new BufferedImage[3];
			zincLeggingActive[0] = ZincLeggingActive.crop(0, 0, 64, 64);
			zincLeggingActive[1] = ZincLeggingActive.crop(64, 0, 64, 64);
			zincLeggingActive[2] = ZincLeggingActive.crop(0, 64, 64, 64);
			zincLeggingStanding = ZincLeggingActive.crop(64, 64, 64, 64);

			zincBoots = ZincBoots.crop(0, 0, 32, 32);
			zincBootsActive = new BufferedImage[3];
			zincBootsActive[0] = ZincBootsActive.crop(0, 0, 64, 64);
			zincBootsActive[1] = ZincBootsActive.crop(64, 0, 64, 64);
			zincBootsActive[2] = ZincBootsActive.crop(0, 64, 64, 64);
			zincBootsStanding = ZincBootsActive.crop(64, 64, 64, 64);

			zincGauntlets = ZincGauntlet.crop(0, 0, 32, 32);
			zincGauntletsActive = new BufferedImage[3];
			zincGauntletsActive[0] = ZincGauntletActive.crop(0, 0, 64, 64);
			zincGauntletsActive[1] = ZincGauntletActive.crop(64, 0, 64, 64);
			zincGauntletsActive[2] = ZincGauntletActive.crop(0, 64, 64, 64);
			zincGauntletsStanding = ZincGauntletActive.crop(64, 64, 64, 64);

			ironHelmet = IronHelmet.crop(0, 0, 32, 32);
			ironHelmetActive = new BufferedImage[3];
			ironHelmetActive[0] = IronHelmetActive.crop(0, 0, 64, 64);
			ironHelmetActive[1] = IronHelmetActive.crop(64, 0, 64, 64);
			ironHelmetActive[2] = IronHelmetActive.crop(0, 64, 64, 64);
			ironHelmetStanding = IronHelmetActive.crop(64, 64, 64, 64);

			ironChestplate = IronChestplate.crop(0, 0, 32, 32);
			ironChestplateActive = new BufferedImage[3];
			ironChestplateActive[0] = IronChestplateActive.crop(0, 0, 64, 64);
			ironChestplateActive[1] = IronChestplateActive.crop(64, 0, 64, 64);
			ironChestplateActive[2] = IronChestplateActive.crop(0, 64, 64, 64);
			ironChestplateStanding = IronChestplateActive.crop(64, 64, 64, 64);

			ironLegging = IronLegging.crop(0, 0, 32, 32);
			ironLeggingActive = new BufferedImage[3];
			ironLeggingActive[0] = IronLeggingActive.crop(0, 0, 64, 64);
			ironLeggingActive[1] = IronLeggingActive.crop(64, 0, 64, 64);
			ironLeggingActive[2] = IronLeggingActive.crop(0, 64, 64, 64);
			ironLeggingStanding = IronLeggingActive.crop(64, 64, 64, 64);

			ironBoots = IronBoots.crop(0, 0, 32, 32);
			ironBootsActive = new BufferedImage[3];
			ironBootsActive[0] = IronBootsActive.crop(0, 0, 64, 64);
			ironBootsActive[1] = IronBootsActive.crop(64, 0, 64, 64);
			ironBootsActive[2] = IronBootsActive.crop(0, 64, 64, 64);
			ironBootsStanding = IronBootsActive.crop(64, 64, 64, 64);

			ironGauntlets = IronGauntlet.crop(0, 0, 32, 32);
			ironGauntletsActive = new BufferedImage[3];
			ironGauntletsActive[0] = IronGauntletActive.crop(0, 0, 64, 64);
			ironGauntletsActive[1] = IronGauntletActive.crop(64, 0, 64, 64);
			ironGauntletsActive[2] = IronGauntletActive.crop(0, 64, 64, 64);
			ironGauntletsStanding = IronGauntletActive.crop(64, 64, 64, 64);
			
			titaniumHelmet = TitaniumHelmet.crop(0, 0, 32, 32);
			titaniumHelmetActive = new BufferedImage[3];
			titaniumHelmetActive[0] = TitaniumHelmetActive.crop(0, 0, 64, 64);
			titaniumHelmetActive[1] = TitaniumHelmetActive.crop(64, 0, 64, 64);
			titaniumHelmetActive[2] = TitaniumHelmetActive.crop(0, 64, 64, 64);
			titaniumHelmetStanding = TitaniumHelmetActive.crop(64, 64, 64, 64);

			titaniumChestplate = TitaniumChestplate.crop(0, 0, 32, 32);
			titaniumChestplateActive = new BufferedImage[3];
			titaniumChestplateActive[0] = TitaniumChestplateActive.crop(0, 0, 64, 64);
			titaniumChestplateActive[1] = TitaniumChestplateActive.crop(64, 0, 64, 64);
			titaniumChestplateActive[2] = TitaniumChestplateActive.crop(0, 64, 64, 64);
			titaniumChestplateStanding = TitaniumChestplateActive.crop(64, 64, 64, 64);

			titaniumLegging = TitaniumLegging.crop(0, 0, 32, 32);
			titaniumLeggingActive = new BufferedImage[3];
			titaniumLeggingActive[0] = TitaniumLeggingActive.crop(0, 0, 64, 64);
			titaniumLeggingActive[1] = TitaniumLeggingActive.crop(64, 0, 64, 64);
			titaniumLeggingActive[2] = TitaniumLeggingActive.crop(0, 64, 64, 64);
			titaniumLeggingStanding = TitaniumLeggingActive.crop(64, 64, 64, 64);

			titaniumBoots = TitaniumBoots.crop(0, 0, 32, 32);
			titaniumBootsActive = new BufferedImage[3];
			titaniumBootsActive[0] = TitaniumBootsActive.crop(0, 0, 64, 64);
			titaniumBootsActive[1] = TitaniumBootsActive.crop(64, 0, 64, 64);
			titaniumBootsActive[2] = TitaniumBootsActive.crop(0, 64, 64, 64);
			titaniumBootsStanding = TitaniumBootsActive.crop(64, 64, 64, 64);

			titaniumGauntlets = TitaniumGauntlet.crop(0, 0, 32, 32);
			titaniumGauntletsActive = new BufferedImage[3];
			titaniumGauntletsActive[0] = TitaniumGauntletActive.crop(0, 0, 64, 64);
			titaniumGauntletsActive[1] = TitaniumGauntletActive.crop(64, 0, 64, 64);
			titaniumGauntletsActive[2] = TitaniumGauntletActive.crop(0, 64, 64, 64);
			titaniumGauntletsStanding = TitaniumGauntletActive.crop(64, 64, 64, 64);
			
			tungstenHelmet = TungstenHelmet.crop(0, 0, 32, 32);
			tungstenHelmetActive = new BufferedImage[3];
			tungstenHelmetActive[0] = TungstenHelmetActive.crop(0, 0, 64, 64);
			tungstenHelmetActive[1] = TungstenHelmetActive.crop(64, 0, 64, 64);
			tungstenHelmetActive[2] = TungstenHelmetActive.crop(0, 64, 64, 64);
			tungstenHelmetStanding = TungstenHelmetActive.crop(64, 64, 64, 64);

			tungstenChestplate = TungstenChestplate.crop(0, 0, 32, 32);
			tungstenChestplateActive = new BufferedImage[3];
			tungstenChestplateActive[0] = TungstenChestplateActive.crop(0, 0, 64, 64);
			tungstenChestplateActive[1] = TungstenChestplateActive.crop(64, 0, 64, 64);
			tungstenChestplateActive[2] = TungstenChestplateActive.crop(0, 64, 64, 64);
			tungstenChestplateStanding = TungstenChestplateActive.crop(64, 64, 64, 64);

			tungstenLegging = TungstenLegging.crop(0, 0, 32, 32);
			tungstenLeggingActive = new BufferedImage[3];
			tungstenLeggingActive[0] = TungstenLeggingActive.crop(0, 0, 64, 64);
			tungstenLeggingActive[1] = TungstenLeggingActive.crop(64, 0, 64, 64);
			tungstenLeggingActive[2] = TungstenLeggingActive.crop(0, 64, 64, 64);
			tungstenLeggingStanding = TungstenLeggingActive.crop(64, 64, 64, 64);

			tungstenBoots = TungstenBoots.crop(0, 0, 32, 32);
			tungstenBootsActive = new BufferedImage[3];
			tungstenBootsActive[0] = TungstenBootsActive.crop(0, 0, 64, 64);
			tungstenBootsActive[1] = TungstenBootsActive.crop(64, 0, 64, 64);
			tungstenBootsActive[2] = TungstenBootsActive.crop(0, 64, 64, 64);
			tungstenBootsStanding = TungstenBootsActive.crop(64, 64, 64, 64);

			tungstenGauntlets = TungstenGauntlet.crop(0, 0, 32, 32);
			tungstenGauntletsActive = new BufferedImage[3];
			tungstenGauntletsActive[0] = TungstenGauntletActive.crop(0, 0, 64, 64);
			tungstenGauntletsActive[1] = TungstenGauntletActive.crop(64, 0, 64, 64);
			tungstenGauntletsActive[2] = TungstenGauntletActive.crop(0, 64, 64, 64);
			tungstenGauntletsStanding = TungstenGauntletActive.crop(64, 64, 64, 64);
			
			bronzeArm = BronzeArm.crop(0, 0, 64, 64);
			zincArm = ZincArm.crop(0, 0, 64, 64);
			ironArm = IronArm.crop(0, 0, 64, 64);
			titaniumArm = TitaniumArm.crop(0, 0, 64, 64);
			tungstenArm = TungstenArm.crop(0, 0, 64, 64);

			// Weapon
			// ----------------------------------------------------------------------------
			Spritesheet Megashakalaka = new Spritesheet(ImageIO.read(Assets.class.getResource("/weaponItem/megashakalaka.png")));
			Spritesheet MegashakalakaActive = new Spritesheet(ImageIO.read(Assets.class.getResource("/weaponItem/megashakalakaActive.png")));
			Spritesheet Katana = new Spritesheet(ImageIO.read(Assets.class.getResource("/weaponItem/katana.png")));
			Spritesheet Flamethrower = new Spritesheet(ImageIO.read(Assets.class.getResource("/weaponItem/flameThrower.png")));
			Spritesheet FlamethrowerActive = new Spritesheet(ImageIO.read(Assets.class.getResource("/weaponItem/flameThrowerActive.png")));
			Spritesheet WoodenBow = new Spritesheet(ImageIO.read(Assets.class.getResource("/weaponItem/woodenBow.png")));
			Spritesheet BronzeBow = new Spritesheet(ImageIO.read(Assets.class.getResource("/weaponItem/bronzeBow.png")));
			Spritesheet ZincBow = new Spritesheet(ImageIO.read(Assets.class.getResource("/weaponItem/zincBow.png")));
			Spritesheet IronBow = new Spritesheet(ImageIO.read(Assets.class.getResource("/weaponItem/ironBow.png")));
			Spritesheet Ravager = new Spritesheet(ImageIO.read(Assets.class.getResource("/weaponItem/ravager.png")));
			Spritesheet WoodenBowActive = new Spritesheet(ImageIO.read(Assets.class.getResource("/weaponItem/woodenBowActive.png")));
			Spritesheet BronzeBowActive = new Spritesheet(ImageIO.read(Assets.class.getResource("/weaponItem/bronzeBowActive.png")));
			Spritesheet ZincBowActive = new Spritesheet(ImageIO.read(Assets.class.getResource("/weaponItem/zincBowActive.png")));
			Spritesheet IronBowActive = new Spritesheet(ImageIO.read(Assets.class.getResource("/weaponItem/ironBowActive.png")));
			Spritesheet RavagerActive = new Spritesheet(ImageIO.read(Assets.class.getResource("/weaponItem/ravagerActive.png")));
			Spritesheet Glock = new Spritesheet(ImageIO.read(Assets.class.getResource("/weaponItem/glock.png")));
			Spritesheet GlockActive = new Spritesheet(ImageIO.read(Assets.class.getResource("/weaponItem/glockActive.png")));
			Spritesheet DesertEagle = new Spritesheet(ImageIO.read(Assets.class.getResource("/weaponItem/desertEagle.png")));
			Spritesheet DesertEagleActive = new Spritesheet(ImageIO.read(Assets.class.getResource("/weaponItem/desertEagleActive.png")));
			Spritesheet BronzeBlade = new Spritesheet(ImageIO.read(Assets.class.getResource("/weaponItem/bronzeBlade.png")));
			Spritesheet ZincBlade = new Spritesheet(ImageIO.read(Assets.class.getResource("/weaponItem/zincBlade.png")));
			Spritesheet IronSword = new Spritesheet(ImageIO.read(Assets.class.getResource("/weaponItem/ironSword.png")));
			Spritesheet TitaniumClaws = new Spritesheet(ImageIO.read(Assets.class.getResource("/weaponItem/titaniumClaw.png")));
			Spritesheet TungstenMace = new Spritesheet(ImageIO.read(Assets.class.getResource("/weaponItem/tungstenMace.png")));
			Spritesheet Bat = new Spritesheet(ImageIO.read(Assets.class.getResource("/weaponItem/bat.png")));
			Spritesheet SteelSword = new Spritesheet(ImageIO.read(Assets.class.getResource("/weaponItem/steelSword.png")));
			Spritesheet GiantSawBlade = new Spritesheet(ImageIO.read(Assets.class.getResource("/weaponItem/giantSawBlade.png")));
			Spritesheet WoodenClub = new Spritesheet(ImageIO.read(Assets.class.getResource("/weaponItem/woodenClub.png")));
			Spritesheet StoneClub = new Spritesheet(ImageIO.read(Assets.class.getResource("/weaponItem/stoneClub.png")));
			Spritesheet SpikeClub = new Spritesheet(ImageIO.read(Assets.class.getResource("/weaponItem/spikeClub.png")));
			Spritesheet Tools = new Spritesheet(ImageIO.read(Assets.class.getResource("/weaponItem/tools.png")));
			Spritesheet Shovel = new Spritesheet(ImageIO.read(Assets.class.getResource("/weaponItem/shovel.png")));
			Spritesheet DarkSaber = new Spritesheet(ImageIO.read(Assets.class.getResource("/weaponItem/dark_saber.png")));
			Spritesheet PulseRifle = new Spritesheet(ImageIO.read(Assets.class.getResource("/weaponItem/pulse_rifle.png")));
			Spritesheet PulseRifleActive = new Spritesheet(ImageIO.read(Assets.class.getResource("/weaponItem/pulse_rifle_active.png")));
			
			katana = Katana.crop(0, 0, 64, 64);
			bronzeBlade = BronzeBlade.crop(0, 0, 64, 64);
			zincBlade = ZincBlade.crop(0, 0, 64, 64);
			ironSword = IronSword.crop(0, 0, 64, 64);
			titaniumClaws = TitaniumClaws.crop(0, 0, 64, 64);
			tungstenMace = TungstenMace.crop(0, 0, 64, 64);
			bat = Bat.crop(0, 0, 64, 64);
			steelSword = SteelSword.crop(0, 0, 64, 64);
			giantSawBlade = GiantSawBlade.crop(0, 0, 64, 64);
			woodenClub = WoodenClub.crop(0, 0, 64, 64);
			stoneClub = StoneClub.crop(0, 0, 64, 64);
			spikeClub = SpikeClub.crop(0, 0, 64, 64);
			darkSaber = DarkSaber.crop(0, 0, 64, 64);
			  
			megashakalaka = Megashakalaka.crop(0, 0, 64, 64);
			megashakalakaActive = MegashakalakaActive.crop(0, 0, 64, 64);
			flamethrower = Flamethrower.crop(0, 0, 48, 48);
			flameThrowerActive = FlamethrowerActive.crop(0, 0, 48, 48);
			woodenBow = WoodenBow.crop(0, 0, 64, 64);
			bronzeBow = BronzeBow.crop(0, 0, 64, 64);
			zincBow = ZincBow.crop(0, 0, 64, 64);
			ironBow = IronBow.crop(0, 0, 64, 64);
			ravager = Ravager.crop(0, 0, 64, 64);
			woodenBowActive = WoodenBowActive.crop(0, 0, 64, 64);
			bronzeBowActive = BronzeBowActive.crop(0, 0, 64, 64);
			zincBowActive = ZincBowActive.crop(0, 0, 64, 64);
			ironBowActive = IronBowActive.crop(0, 0, 64, 64);
			ravagerActive = RavagerActive.crop(0, 0, 64, 64);
			glock = Glock.crop(0, 0, 32, 32);
			glockActive = GlockActive.crop(0, 0, 64, 64);
			desertEagle = DesertEagle.crop(0, 0, 32, 32);
			desertEagleActive = DesertEagleActive.crop(0, 0, 64, 64);
			pulseRifle = PulseRifle.crop(0, 0, 64, 64);
			pulseRifleActive = PulseRifleActive.crop(0, 0, 64, 64);
			
			woodenAxe = Tools.crop(0, 0, 64, 64);
			woodenPick = Tools.crop(64, 0, 64, 64);
			stoneAxe = Tools.crop(128, 0, 64, 64);
			stonePick = Tools.crop(192, 0, 64, 64);
			bronzeAxe = Tools.crop(256, 0, 64, 64);
			bronzePick = Tools.crop(320, 0, 64, 64);
			zincAxe = Tools.crop(384, 0, 64, 64);
			zincPick = Tools.crop(448, 0, 64, 64);
			drill = Tools.crop(512, 0, 64, 64);
			chainsaw = Tools.crop(576, 0, 64, 64);

			shovel = Shovel.crop(0, 0, 32, 32);
			
			// Food
			// ----------------------------------------------------------------------------
			Spritesheet BugMeat = new Spritesheet(ImageIO.read(Assets.class.getResource("/food/bugMeat.png")));
			Spritesheet Chicken = new Spritesheet(ImageIO.read(Assets.class.getResource("/food/chicken.png")));
			Spritesheet Morsel = new Spritesheet(ImageIO.read(Assets.class.getResource("/food/morsel.png")));
			Spritesheet Meat = new Spritesheet(ImageIO.read(Assets.class.getResource("/food/meat.png")));
			Spritesheet VegeMeat = new Spritesheet(ImageIO.read(Assets.class.getResource("/food/vegeMeat.png")));
			Spritesheet Shroom = new Spritesheet(ImageIO.read(Assets.class.getResource("/food/shroom.png")));
			Spritesheet PrettyShroomFood = new Spritesheet(ImageIO.read(Assets.class.getResource("/food/prettyShroom.png")));
			Spritesheet BrainFunguiFood = new Spritesheet(ImageIO.read(Assets.class.getResource("/food/brainFungui.png")));
			Spritesheet ScorpionTail = new Spritesheet(ImageIO.read(Assets.class.getResource("/food/scorpionTail.png")));
			Spritesheet PoisonCombo = new Spritesheet(ImageIO.read(Assets.class.getResource("/food/poisonCombo.png")));
			Spritesheet Glucose = new Spritesheet(ImageIO.read(Assets.class.getResource("/food/glucose.png")));
			Spritesheet MonsterDinner = new Spritesheet(ImageIO.read(Assets.class.getResource("/food/monsterDinner.png")));
			Spritesheet Salad = new Spritesheet(ImageIO.read(Assets.class.getResource("/food/salad.png")));
			Spritesheet BugMesh = new Spritesheet(ImageIO.read(Assets.class.getResource("/food/bugMesh.png")));
			Spritesheet MushroomChicken = new Spritesheet(ImageIO.read(Assets.class.getResource("/food/mushroomChicken.png")));
			Spritesheet BossiliciousMeal = new Spritesheet(ImageIO.read(Assets.class.getResource("/food/bossiliciousMeal.png")));
			Spritesheet ExtrafloralNectar = new Spritesheet(ImageIO.read(Assets.class.getResource("/food/extrafloralNectar.png")));
			Spritesheet NectarBit = new Spritesheet(ImageIO.read(Assets.class.getResource("/food/nectarBit.png")));

			bugMeat = BugMeat.crop(0, 0, 32, 32);
			cookedBugMeat = BugMeat.crop(32, 0, 32, 32);
			rottenBugMeat = BugMeat.crop(64, 0, 32, 32);
			rawChicken = Chicken.crop(0, 0, 32, 32);
			cookedChicken = Chicken.crop(32, 0, 32, 32);
			suspiciousChicken = Chicken.crop(64, 0, 32, 32);
			cookedSuspiciousChicken = Chicken.crop(96, 0, 32, 32);
			rottenChicken = Chicken.crop(128, 0, 32, 32);
			rawMorsel = Morsel.crop(0, 0, 32, 32);
			cookedMorsel = Morsel.crop(32, 0, 32, 32);
			suspiciousMorsel = Morsel.crop(64, 0, 32, 32);
			cookedSuspiciousMorsel = Morsel.crop(96, 0, 32, 32);
			rottenMorsel = Morsel.crop(128, 0, 32, 32);
			rawMeat = Meat.crop(0, 0, 32, 32);
			cookedMeat = Meat.crop(32, 0, 32, 32);
			suspiciousMeat = Meat.crop(64, 0, 32, 32);
			cookedSuspiciousMeat = Meat.crop(96, 0, 32, 32);
			rottenMeat = Meat.crop(128, 0, 32, 32);
			vegeMeat = VegeMeat.crop(0, 0, 32, 32);
			shroom = Shroom.crop(0, 0, 32, 32);
			prettyShroom = PrettyShroomFood.crop(0, 0, 32, 32);
			brainFungui = BrainFunguiFood.crop(0, 0, 32, 32);
			scorpionTail = ScorpionTail.crop(0, 0, 32, 32);
			poisonCombo = PoisonCombo.crop(0, 0, 32, 32);
			bugMesh = BugMesh.crop(0, 0, 32, 32);
			mushroomChicken = MushroomChicken.crop(0, 0, 32, 32);
			salad = Salad.crop(0, 0, 32, 32);
			glucose = Glucose.crop(0, 0, 32, 32);
			monsterDinner = MonsterDinner.crop(0, 0, 32, 32);
			bossiliciousMeal = BossiliciousMeal.crop(0, 0, 64, 64);
			extrafloralNectar = ExtrafloralNectar.crop(0, 0, 64, 64);
			nectarBit = NectarBit.crop(0, 0, 32, 32);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Spritesheet nightB = new Spritesheet(ImageIO.read(Assets.class.getResource("/background/nightBlk.png")));
		
		
	}

}
