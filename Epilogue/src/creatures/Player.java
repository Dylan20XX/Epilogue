package creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

import alphaPackage.ControlCenter;
import ammo.AMM1D;
import ammo.Arrow;
import ammo.XM214;
import ammo.Flame;
import ammo.PulseRifle;
import audio.AudioPlayer;
import entity.Entity;
import graphics.Animation;
import graphics.Assets;
import graphics.CT;
import inventory.Effect;
import inventory.EffectManager;
import inventory.Equipment;
import inventory.HandCraft;
import inventory.Inventory;
import inventory.MessageBox;
import inventory.PlayerHands;
import inventory.Recipe;
import items.Food;
import items.Item;
import items.Ranged;
import items.Tool;
import items.WaterContainer;
import items.Weapon;
import structureInventory.AutoCookerV2Craft;
import structures.Analyzer;
import structures.AutoCooker;
import structures.Chest;
import structures.Disintegrator;
import structures.MetallicOven;
import structures.Purifier;
import structures.ResearchTable;
import structures.SmithingTable;
import structures.Workbench;
import tiles.Tile;

/*
 * Object player is the user
 * 
 * subclass of the creature class because player is a creature
 * checks and interprets player inputs
 */
public class Player extends Creatures {

	private Shape ab;

	// Player properties
	private static ControlCenter c;
	private double currentSpeed;
	private Inventory inventory;
	private Equipment equipment;
	private PlayerHands hands;
	private HandCraft handCraft;

	private double runSpeed;
	public boolean running = false;

	private long lastAttackTimer, attackCooldown = 800, attackTimer = 0; // attack speed every 0.5s
	public long lastEatTimer, eatCooldown = 1000, drinkCooldown = 150, drinkAudioTimer = 0, lastdrinkAudioTimer,
			drinkAudioCooldown = 2500, eatTimer = 0; // attack speed every 0.5s
	private long lastHungerTimer, hungerCooldown = 10000, hungerTimer = hungerCooldown;
	private long lastThirstTimer, thirstCooldown = 7000, thirstTimer = thirstCooldown;
	private long lastEnergyTimer, energyCooldown = 1000, energyTimer = energyCooldown;
	private long lastEnergyRegenTimer, energyRegenCooldown = 1000, energyRegenTimer = energyCooldown;
	private long lastHealthRegenTimer, healthRegenCooldown = 1000, healthRegenTimer = energyCooldown;
	private long lastHealthDegenTimer, healthDegenCooldown = 1000, healthDegenTimer = energyCooldown;

	private long lastknockTimer, knockCooldown = 100, knockTimer = knockCooldown;

	private int energyRegenAmt = 4;
	private int energyDegenAmt = 0;
	public int healthRegenAmt = 2;

	private Animation playerMoveLeft, playerMoveRight, playerRunLeft, playerRunRight, playerStandAttackLeft,
			playerStandAttackRight, playerWalkAttackLeft, playerWalkAttackRight, playerRunAttackLeft,
			playerRunAttackRight, playerIdleAniLeft, playerIdleAniRight;
	private Animation currentLeftAnimation, currentRightAnimation;
	private BufferedImage[] playerIdleLeft, playerIdleRight;
	private BufferedImage Arm = Assets.rodArm;

	public int ogEndurance;
	public int ogHealth;
	public int ogDamage;
	public int hunger;
	public int thirst;
	public int energy;

	private int entityBoundSize = Tile.TILEWIDTH * 50;
	private int renderBoundSize = 1500;
	private int animationSpeed = 0;

	public boolean knocked;
	public boolean stealth;
	public boolean tiredStated, exaustStated;
	public boolean drinking, canDrink, filling;

	public boolean destinationReached = true;
	private double sX, sY, eX, eY, desinationAngle;
	private double destX, destY;
	public double extraSpeed = 0, bonusAttackSpeed = 0;

	private double shootAngle = 0;
	private double swptAng = Math.PI;

	// Chest variables
	private boolean chestActive = false;
	private Chest chest;

	// Metallic Oven variables
	private boolean metallicOvenActive = false;
	private MetallicOven metallicOven;

	// Crafting Structure variables
	private boolean workbenchActive = false;
	private Workbench workbench;
	private boolean smithingTableActive = false;
	private SmithingTable smithingTable;
	private boolean disintegratorActive = false;
	private Disintegrator disintegrator;
	private boolean analyzerActive = false;
	private Analyzer analyzer;
	private boolean purifierActive = false;
	private Purifier purifier;
	private boolean autoCookerActive = false;
	private AutoCooker autoCooker;
	private boolean autoCookerV2Active = false;
	private AutoCookerV2Craft autoCookerV2;
	private boolean researchTableActive = false;
	private ResearchTable researchTable;

	// Floor breaking variables
	private int numSwings; // number of times the player swings at a floor
	private int numSwingsRequired; // number of swings required to break a floor (3 - wood, 2 - stone, 1 - iron)
	private int lastPlayerTileX; // tile coords of the last floor attacked, if changed, reset number of swings
	private int lastPlayerTileY;

	//Experience Variables
	private int basicSurvivalXP = 0; //Basic survival experience - received after a day ends
	private int combatXP = 0; //Combat experience for weapons and equipments and armors, gained from destroying enemies
	private int cookingXP = 0; //Cooking experience - gained from cooking food
	private int buildingXP = 0; //Building experience - gained from destroying static entities
	
	private boolean goatAggro = false; //if this is true, goats will chase player
	private long lastGoatAggroTimer, goatAggroCooldown = 15000, goatAggroTimer;
	
	private Random r = new Random();
	
	// constructor of player taking in all required player attributes
	// also takes in the ControlCenter for other class accesses
	public Player(double x, double y, String name, int health, double speed, int baseDamage, int intelligence,
			int intimidation, int resistance, int endurability, ControlCenter c) {
		super(x, y, Creatures.DEFAULT_CREATURE_WIDTH * 3 / 2, Creatures.DEFAULT_CREATURE_HEIGHT * 3 / 2, c);

		// sets the player attributes
		this.health = health;
		this.name = name;
		this.speed = speed;
		this.damage = baseDamage;
		this.intelligence = intelligence;
		this.intimidation = intimidation;
		this.resistance = resistance;
		this.endurability = endurability;
		Player.c = c;
		runSpeed = speed + 2;

		ogEndurance = endurability;
		ogDamage = baseDamage;
		ogHealth = health;
		hunger = endurability;
		thirst = endurability;
		energy = endurability;
		
		knockValue = baseDamage / 20;

		equipment = new Equipment(c, this);
		inventory = new Inventory(c, this, equipment);
		hands = new PlayerHands(c, inventory);
		handCraft = new HandCraft(c);

		bounds.x = 0;
		bounds.y = 0;
		bounds.width = (int) (23 * ControlCenter.scaleValue);
		bounds.height = (int) (30 * ControlCenter.scaleValue);

		if(name.equals("Rod")) {
			playerMoveLeft = new Animation(300, Assets.rodMove, true);
			playerMoveRight = new Animation(300, CT.flip(Assets.rodMove), true);
			playerRunLeft = new Animation(100, Assets.rodMove, true);
			playerRunRight = new Animation(100, CT.flip(Assets.rodMove), true);
			playerStandAttackLeft = new Animation(300, Assets.rodStandAttack, true);
			playerStandAttackRight = new Animation(300, CT.flip(Assets.rodStandAttack), true);
			playerWalkAttackLeft = new Animation(300, Assets.rodRunAttack, true);
			playerWalkAttackRight = new Animation(300, CT.flip(Assets.rodRunAttack), true);
			playerRunAttackLeft = new Animation(100, Assets.rodRunAttack, true);
			playerRunAttackRight = new Animation(100, CT.flip(Assets.rodRunAttack), true);
			playerIdleLeft = new BufferedImage[1];
			playerIdleRight = new BufferedImage[1];
			playerIdleLeft[0] = Assets.rodIdle;
			playerIdleRight[0] = CT.flip(Assets.rodIdle);
			playerIdleAniLeft = new Animation(0, playerIdleLeft, true);
			playerIdleAniRight = new Animation(0, CT.flip(playerIdleLeft), true);
		} else if(name.equals("Ray")) {
			playerMoveLeft = new Animation(300, Assets.rayMove, true);
			playerMoveRight = new Animation(300, CT.flip(Assets.rayMove), true);
			playerRunLeft = new Animation(100, Assets.rayMove, true);
			playerRunRight = new Animation(100, CT.flip(Assets.rayMove), true);
			playerStandAttackLeft = new Animation(300, Assets.rayStandAttack, true);
			playerStandAttackRight = new Animation(300, CT.flip(Assets.rayStandAttack), true);
			playerWalkAttackLeft = new Animation(300, Assets.rayRunAttack, true);
			playerWalkAttackRight = new Animation(300, CT.flip(Assets.rayRunAttack), true);
			playerRunAttackLeft = new Animation(100, Assets.rayRunAttack, true);
			playerRunAttackRight = new Animation(100, CT.flip(Assets.rayRunAttack), true);
			playerIdleLeft = new BufferedImage[1];
			playerIdleRight = new BufferedImage[1];
			playerIdleLeft[0] = Assets.rayIdle;
			playerIdleRight[0] = CT.flip(Assets.rayIdle);
			playerIdleAniLeft = new Animation(0, playerIdleLeft, true);
			playerIdleAniRight = new Animation(0, CT.flip(playerIdleLeft), true);
		} else if(name.equals("Batash")) {
			playerMoveLeft = new Animation(300, Assets.batashMove, true);
			playerMoveRight = new Animation(300, CT.flip(Assets.batashMove), true);
			playerRunLeft = new Animation(100, Assets.batashMove, true);
			playerRunRight = new Animation(100, CT.flip(Assets.batashMove), true);
			playerStandAttackLeft = new Animation(300, Assets.batashStandAttack, true);
			playerStandAttackRight = new Animation(300, CT.flip(Assets.batashStandAttack), true);
			playerWalkAttackLeft = new Animation(300, Assets.batashRunAttack, true);
			playerWalkAttackRight = new Animation(300, CT.flip(Assets.batashRunAttack), true);
			playerRunAttackLeft = new Animation(100, Assets.batashRunAttack, true);
			playerRunAttackRight = new Animation(100, CT.flip(Assets.batashRunAttack), true);
			playerIdleLeft = new BufferedImage[1];
			playerIdleRight = new BufferedImage[1];
			playerIdleLeft[0] = Assets.batashIdle;
			playerIdleRight[0] = CT.flip(Assets.batashIdle);
			playerIdleAniLeft = new Animation(0, playerIdleLeft, true);
			playerIdleAniRight = new Animation(0, CT.flip(playerIdleLeft), true);
		} else if(name.equals("Bharat_Sinai_Peddi")) {
			playerMoveLeft = new Animation(300, Assets.sinaiMove, true);
			playerMoveRight = new Animation(300, CT.flip(Assets.sinaiMove), true);
			playerRunLeft = new Animation(100, Assets.sinaiMove, true);
			playerRunRight = new Animation(100, CT.flip(Assets.sinaiMove), true);
			playerStandAttackLeft = new Animation(300, Assets.sinaiStandAttack, true);
			playerStandAttackRight = new Animation(300, CT.flip(Assets.sinaiStandAttack), true);
			playerWalkAttackLeft = new Animation(300, Assets.sinaiRunAttack, true);
			playerWalkAttackRight = new Animation(300, CT.flip(Assets.sinaiRunAttack), true);
			playerRunAttackLeft = new Animation(100, Assets.sinaiRunAttack, true);
			playerRunAttackRight = new Animation(100, CT.flip(Assets.sinaiRunAttack), true);
			playerIdleLeft = new BufferedImage[1];
			playerIdleRight = new BufferedImage[1];
			playerIdleLeft[0] = Assets.sinaiIdle;
			playerIdleRight[0] = CT.flip(Assets.sinaiIdle);
			playerIdleAniLeft = new Animation(0, playerIdleLeft, true);
			playerIdleAniRight = new Animation(0, CT.flip(playerIdleLeft), true);
		} else if(name.equals("Paris")) {
			playerMoveLeft = new Animation(300, Assets.parisMove, true);
			playerMoveRight = new Animation(300, CT.flip(Assets.parisMove), true);
			playerRunLeft = new Animation(100, Assets.parisMove, true);
			playerRunRight = new Animation(100, CT.flip(Assets.parisMove), true);
			playerStandAttackLeft = new Animation(300, Assets.parisStandAttack, true);
			playerStandAttackRight = new Animation(300, CT.flip(Assets.parisStandAttack), true);
			playerWalkAttackLeft = new Animation(300, Assets.parisRunAttack, true);
			playerWalkAttackRight = new Animation(300, CT.flip(Assets.parisRunAttack), true);
			playerRunAttackLeft = new Animation(100, Assets.parisRunAttack, true);
			playerRunAttackRight = new Animation(100, CT.flip(Assets.parisRunAttack), true);
			playerIdleLeft = new BufferedImage[1];
			playerIdleRight = new BufferedImage[1];
			playerIdleLeft[0] = Assets.parisIdle;
			playerIdleRight[0] = CT.flip(Assets.parisIdle);
			playerIdleAniLeft = new Animation(0, playerIdleLeft, true);
			playerIdleAniRight = new Animation(0, CT.flip(playerIdleLeft), true);
		}

		thirst -= 30;
		hunger -= 30;
		health -= 20;

	}

	public static Player getPlayerData() {

		return c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getEntityManager().getPlayer();

	}

	// extended methods
	@Override
	public void tick() {

		if (autoCookerV2 != null && !autoCookerV2Active)
			autoCookerV2.tick();

		if (hands.getHand() == null) {
			attackCooldown = 800;
		} else {
			if (hands.getHand().getType().equals("weapon")) {
				Weapon weapon = (Weapon) (hands.getHand());
				attackCooldown = (long) weapon.getaSpeed();
			} else if (hands.getHand().getType().equals("axe") || hands.getHand().getType().equals("pickaxe")) {
				Tool tool = (Tool) (hands.getHand());
				attackCooldown = (long) tool.getaSpeed();
			} else if (hands.getHand().getType().equals("ranged")) {
				Ranged weapon = (Ranged) (hands.getHand());
				attackCooldown = (long) weapon.getaSpeed();
			} else
				attackCooldown = 800;
		}

		if (currentLeftAnimation != null)
			currentLeftAnimation.tick();
		if (currentRightAnimation != null)
			currentRightAnimation.tick();

		if (running)
			animationSpeed = 300;
		else if (isMoving)
			animationSpeed = 100;
		else
			animationSpeed = 0;

		if (hands.getHand() == null)
			damage = ogDamage;
		else
			damage = hands.getHand().getDamage();

		//if(!c.getGameState().getWorldGenerator().isStructureCrafted() && !c.getGameState().getWorldGenerator().isCurrentlyBuildingStructure()) {
			getInput();
		//}
		
		updateVitals();
		updateAnimations();
		checkFood();
		equipment.tick();
		hands.tick();

		if (hunger > (double) ogEndurance / 2 && thirst > (double) ogEndurance / 2) {
			energyRegenAmt = 12;
			healthRegenAmt = 3;
		} else if(thirst == 0) {
			energyRegenAmt = -3;
			healthRegenAmt = -2;
		} else if(hunger == 0) {
			energyRegenAmt = -2;
			healthRegenAmt = -3;
		} else if (hunger > (double) ogEndurance / 2) {
			energyRegenAmt = 3;
			healthRegenAmt = 2;
		} else if (thirst > (double) ogEndurance / 2) {
			energyRegenAmt = 9;
			healthRegenAmt = 1;
		} else {
			energyRegenAmt = -5;
			healthRegenAmt = -5;
		}

		move(); // called from the Creature class

		c.getGameCamera().centerOnEntity(this);

		if(!c.getGameState().getWorldGenerator().isStructureCrafted() && !c.getGameState().getWorldGenerator().isCurrentlyBuildingStructure()) {
			checkAttack(hands.getHand());
		}
		// tick the chest if it's active
		if (isChestActive()) {
			chest.getInventory().tick();
		//} else if (isWorkbenchActive()) { // July 15 *add analyser and cooking structure
		//	workbench.getCraft().tick();
		//} else if (isSmithingTableActive()) {
		//	smithingTable.getCraft().tick();
		} else if (isAnalyzerActive()) {
			analyzer.getCraft().tick();
		//} else if (isPurifierActive()) {
			//purifier.getCraft().tick();
		} else if (isAutoCookerActive()) {
			autoCooker.getCraft().tick();
		} else if (isDisintegratorActive()) {
			disintegrator.getCraft().tick();
		} else if (isAutoCookerV2Active()) {
			autoCookerV2.tick();
		} else if (isMetallicOvenActive()) {
			metallicOven.getInventory().tick();
		} else if(isResearchTableActive()) {
            researchTable.getCraft().tick();
        } else if (inventory.isActive()) {
			inventory.tick();
		} else if (handCraft.isActive()) {
			handCraft.tick();
		} else {
			inventory.tick();
			handCraft.tick();
		}
		
		//Goat aggro timer
		if(goatAggro) {
			goatAggroTimer += System.currentTimeMillis() - lastGoatAggroTimer;
			lastGoatAggroTimer = System.currentTimeMillis();
			
			if (!(goatAggroTimer < goatAggroCooldown)) {
				goatAggroTimer = 0;
				goatAggro = false;
			}
		}

	}

	private void updateAnimations() {

		if (isMoving) {

			if (running) {
				if(hands != null && hands.getHand()!=null && hands.getHand().getType().equals("ranged")) {
					currentLeftAnimation = playerRunAttackLeft;
					currentRightAnimation = playerRunAttackRight;
				} else if (isAttacking) {
					currentLeftAnimation = playerRunAttackLeft;
					currentRightAnimation = playerRunAttackRight;
				} else {
					currentLeftAnimation = playerRunLeft;
					currentRightAnimation = playerRunRight;
				}
			} else {
				if(hands != null && hands.getHand()!=null && hands.getHand().getType().equals("ranged")) {
					currentLeftAnimation = playerWalkAttackLeft;
					currentRightAnimation = playerWalkAttackRight;
				} else if (isAttacking) {
					currentLeftAnimation = playerWalkAttackLeft;
					currentRightAnimation = playerWalkAttackRight;
				} else {
					currentLeftAnimation = playerMoveLeft;
					currentRightAnimation = playerMoveRight;
				}
			}
			

		} else {

			if(hands != null && hands.getHand()!=null && hands.getHand().getType().equals("ranged")) {
				currentLeftAnimation = playerStandAttackLeft;
				currentRightAnimation = playerStandAttackRight;
			} else if (isAttacking) {
				currentLeftAnimation = playerStandAttackLeft;
				currentRightAnimation = playerStandAttackRight;
			} else {
				currentLeftAnimation = playerIdleAniLeft;
				currentRightAnimation = playerIdleAniRight;
			}
			
		}

	}

	private void updateVitals() {

		hungerTimer += System.currentTimeMillis() - lastHungerTimer;
		lastHungerTimer = System.currentTimeMillis();

		if (hungerTimer < hungerCooldown)
			return;

		hunger -= 3;

		hungerTimer = 0; // cool down resets

		if (hunger < 0)
			hunger = 0;

		thirstTimer += System.currentTimeMillis() - lastThirstTimer;
		lastThirstTimer = System.currentTimeMillis();

		if (thirstTimer < thirstCooldown)
			return;

		thirst -= 5;

		thirstTimer = 0; // cool down resets

		if (thirst < 0)
			thirst = 0;

	}

	private void checkAttack(Item item) {

		if (item != null) {

			if (item.getType().equals("ranged")) {
				
				double mouseX = c.getMouseManager().mouseBound().getX();
				double mouseY = c.getMouseManager().mouseBound().getY();

				double originX = x + bounds.width/2 - c.getGameCamera().getxOffset();
				double originY = y + 20 - c.getGameCamera().getyOffset();

				shootAngle = Math.atan2(mouseX - originX, mouseY - originY);
				
				if(mouseX > originX) {
					direction = 1;
				} else
					direction = 0;

				attackTimer += System.currentTimeMillis() - lastAttackTimer;
				lastAttackTimer = System.currentTimeMillis();

				if (attackTimer < attackCooldown)
					return;

				if (inMenu()) // July																			// 15
					return;
				
				if (c.getMouseManager().isLeftClicked() || c.getMouseManager().isLeftPressed()) {
					
					Ranged r = (Ranged)item;
					
					if(r.ammoCurrent > 0 && !r.loading)  {
						if (item.getName().equals("megashakalaka")) {
							AudioPlayer.playAudio("audio/minigun.wav");
							c.getGameState().getWorldGenerator().getEntityManager().addEntity(new XM214((int)x + 10, (int)y, 12, 4, (shootAngle - r.accuracy) + Math.random()*(r.accuracy*2), c));
						} else if (item.getName().equals("pulse rifle")) {
							AudioPlayer.playAudio("audio/laser_gun.wav");
							c.getGameState().getWorldGenerator().getEntityManager().addEntity(new PulseRifle((int)x + 10, (int)y, 12, 4, (shootAngle - r.accuracy) + Math.random()*(r.accuracy*2), c));
						} else if (item.getName().equals("glock")) {
							//c.getGameCamera().shake(1);
							AudioPlayer.playAudio("audio/glock.wav");
							c.getGameState().getWorldGenerator().getEntityManager().addEntity(new AMM1D((int)x + 10, (int)y - 30, 12, 4, (shootAngle - r.accuracy) + Math.random()*(r.accuracy*2), c));
						} else if (item.getName().equals("desert eagle")) {
							//c.getGameCamera().shake(1);
							AudioPlayer.playAudio("audio/desertEagle.wav");
							c.getGameState().getWorldGenerator().getEntityManager().addEntity(new AMM1D((int)x + 10, (int)y - 30, 12, 4, (shootAngle - r.accuracy) + Math.random()*(r.accuracy*2), c));
						} else if (item.getName().equals("wooden bow") || item.getName().equals("bronze bow") || item.getName().equals("zinc bow") || item.getName().equals("iron bow")) {
							AudioPlayer.playAudio("audio/bow.wav");
							c.getGameState().getWorldGenerator().getEntityManager().addEntity(new Arrow((int)x + 10, (int)y - 25, 40, 6, (shootAngle - r.accuracy) + Math.random()*(r.accuracy*2), c));
						} else if (item.getName().equals("ravager")) {
							AudioPlayer.playAudio("audio/bow.wav");
							c.getGameState().getWorldGenerator().getEntityManager().addEntity(new Arrow((int)x + 10, (int)y - 25, 40, 6, (shootAngle - r.accuracy) + Math.random()*(r.accuracy*2), c));
							c.getGameState().getWorldGenerator().getEntityManager().addEntity(new Arrow((int)x + 10, (int)y - 5, 40, 6, (shootAngle - r.accuracy) + Math.random()*(r.accuracy*2), c));
							c.getGameState().getWorldGenerator().getEntityManager().addEntity(new Arrow((int)x + 10, (int)y - 45, 40, 6, (shootAngle - r.accuracy) + Math.random()*(r.accuracy*2), c));
						} else if (item.getName().equals("flamethrower")) {
							AudioPlayer.playAudio("audio/flamethrower.wav");
							c.getGameState().getWorldGenerator().getEntityManager().addEntity(new Flame((int)x - 10, (int)y-10, 50, 45, (shootAngle - r.accuracy) + Math.random()*(r.accuracy*2) + Math.PI/8, c));
						} 
						
						r.ammoCurrent -= 1;
					} else {
						
						if (name.equals("wooden bow") || name.equals("bronze bow") || name.equals("zinc bow") || name.equals("iron bow") || name.equals("ravager")) {
							
						} else
							AudioPlayer.playAudio("audio/trigger.wav");
						
					}
					
					isAttacking = true;
				} else {
					isAttacking = false;
				}

				attackTimer = 0; // cool down resets

			} else if (item.getType().equals("food")) {

				Food food = (Food) item;

				eatTimer += System.currentTimeMillis() - lastEatTimer;
				lastEatTimer = System.currentTimeMillis();

				if (eatTimer < eatCooldown)
					return;
				
				if (inMenu()) 
					return;
				
				if (c.getKeyManager().aup || c.getKeyManager().adown || c.getKeyManager().aleft
						|| c.getKeyManager().aright || c.getMouseManager().isLeftPressed()) {

					hunger += food.getHunger();
					AudioPlayer.playAudio("audio/eat.wav");

					if (hunger > ogEndurance)
						hunger = ogEndurance;

					thirst += food.getThirst();

					if (thirst > ogEndurance)
						thirst = ogEndurance;
					
					if(food.getName().equals("extrafloralNectar")) {
						EffectManager.addEffect(new Effect("swiftness", 100000));
					} else if(food.getName().equals("nectar bit")) {
						EffectManager.addEffect(new Effect("swiftness", 25000));
					} else if(food.getName().equals("pretty shrooms")) {
						EffectManager.addEffect(new Effect("poison", 60000));
					} else if(food.getName().equals("scorpion tail")) {
						EffectManager.addEffect(new Effect("poison", 70000));
					} else if(food.getName().equals("bossilicious meal")) {
						EffectManager.addEffect(new Effect("strength", 300000));
						EffectManager.addEffect(new Effect("bloodlust", 300000));
						EffectManager.addEffect(new Effect("heavily armed", 300000));
						
					} else if(food.getName().equals("poison combo")) {
						for(int i = 0; i < EffectManager.effects.size(); i++) {
							EffectManager.effects.get(i).effectTimer = EffectManager.effects.get(i).effectCooldown;
						}
					} else if(food.getName().equals("glucose")) {
						EffectManager.addEffect(new Effect("swiftness", 30000));
						EffectManager.addEffect(new Effect("bloodlust", 30000));
					} else if(food.getName().equals("brain fungui")) {
						EffectManager.addEffect(new Effect("anger", 450000));
					} 
					
					if(food.getFood().equals("rotten")) {
						if(CT.random(1, 8) != 1) {
							EffectManager.addEffect(new Effect("food poison", CT.random(10000, 200000)));
						}
						if(CT.random(1, 2) == 1) {
							EffectManager.addEffect(new Effect("poison", CT.random(25000, 55000)));
						}
						if(CT.random(1, 2) == 1)
							MessageBox.addMessage("you don't feel so good...");
						else 
							MessageBox.addMessage("you feel very ill...");
					}
					if(food.getFood().equals("raw")) {
						if(CT.random(1, 3) != 1) {
							EffectManager.addEffect(new Effect("food poison", CT.random(10000, 100000)));
						}
						if(CT.random(1, 6) == 1) {
							EffectManager.addEffect(new Effect("poison", CT.random(15000, 35000)));
						}
						if(CT.random(1, 2) == 1)
							MessageBox.addMessage("that tasted weird...");
						else
							MessageBox.addMessage("you regret eating that...");
						
					}
					if(food.getFood().equals("cooked")) {
						if(CT.random(1, 2) == 1)
							MessageBox.addMessage("that tasted good...");
						else 
							MessageBox.addMessage("delicious...");
						
					}
					if(food.getFood().equals("suspicious")) {
						if(CT.random(1, 2) != 1) {
							EffectManager.addEffect(new Effect("food poison", CT.random(20000, 125000)));
						}
						if(CT.random(1, 2) == 1) {
							EffectManager.addEffect(new Effect("poison", CT.random(20000, 45000)));
						}
						if(CT.random(1, 2) == 1)
							MessageBox.addMessage("disgusting...");
						else if(CT.random(1, 2) == 2)
							MessageBox.addMessage("you regret eating that...");
						
					}
					
					inventory.removeItem(item);

					if (item.getCount() == 0)
						hands.setHand(null);

				}

				eatTimer = 0;

			} else if (item.getType().equals("water container")) {

				eatTimer += System.currentTimeMillis() - lastEatTimer;
				lastEatTimer = System.currentTimeMillis();
				drinkAudioTimer += System.currentTimeMillis() - lastdrinkAudioTimer;
				lastdrinkAudioTimer = System.currentTimeMillis();

				if (eatTimer < drinkCooldown)
					return;
				
				if (inMenu()) 
					return;
				
				if (c.getKeyManager().aup || c.getKeyManager().adown || c.getKeyManager().aleft
						|| c.getKeyManager().aright || c.getMouseManager().isLeftPressed()) {

					if (item instanceof WaterContainer) {
						
						drinking = true;

						if (((WaterContainer) item).getCurrentCapacity() > 0) {
							((WaterContainer) item)
									.setCurrentCapacity(((WaterContainer) item).getCurrentCapacity() - 1);

							thirst += 10;
							
							if (drinkAudioTimer > drinkAudioCooldown) {
								if(!((WaterContainer) item).isPurified()) {
									if(CT.random(1, 8) == 1) {
										EffectManager.addEffect(new Effect("contamination", CT.random(10000, 100000)));
										if(CT.random(1, 2) == 1)
											MessageBox.addMessage("you drank something weird...");
										else 
											MessageBox.addMessage("that tasted ill...");
									}
									if(CT.random(1, 7) == 1) {
										EffectManager.addEffect(new Effect("poison", CT.random(5000, 15000)));
										if(CT.random(1, 2) == 1)
											MessageBox.addMessage("you dont feel so good...");
										else 
											MessageBox.addMessage("you tasted germs...");
									}
								}
								AudioPlayer.playAudio("audio/drink.wav");
								drinkAudioTimer = 0;
							}

							if (thirst > ogEndurance)
								thirst = ogEndurance;
						} else {
							((WaterContainer) item).setPurified(false);
						}

					}

				} else 
					
					drinking = false;

				eatTimer = 0;

			} else if (item.getName().equals("shovel")) { //shovel is used to break tiles
    			
				attackTimer += System.currentTimeMillis() - lastAttackTimer;
				lastAttackTimer = System.currentTimeMillis();

				if (attackTimer < attackCooldown - bonusAttackSpeed)
					return;
				
				if (inMenu()) // July 15
					return;
				
				//Uncomment this to select shovel tiles with mouse
//				int playerTileX = (int) ((c.getMouseManager().mouseBound().x -
//						(c.getGameCamera().getxOffset() % Tile.TILEWIDTH) -
//						c.getMouseManager().mouseBound().x % Tile.TILEWIDTH) + 
//						c.getGameCamera().getxOffset());
//				int playerTileY = (int) ((c.getMouseManager().mouseBound().y - 
//						(c.getGameCamera().getyOffset() % Tile.TILEHEIGHT) - 
//						c.getMouseManager().mouseBound().y % Tile.TILEHEIGHT) + 
//						c.getGameCamera().getyOffset());
				
				//This allows shovel tiles to be selected based on the block player is standing on
				int playerTileX = (int) ((getBounds().x + 32 -
						(c.getGameCamera().getxOffset() % Tile.TILEWIDTH) -
						(getBounds().x + 32) % Tile.TILEWIDTH) + 
						c.getGameCamera().getxOffset());
				int playerTileY = (int) ((getBounds().y + 40 - 
						(c.getGameCamera().getyOffset() % Tile.TILEHEIGHT) - 
						(getBounds().y + 40) % Tile.TILEHEIGHT) + 
						c.getGameCamera().getyOffset());
				
				if (c.getKeyManager().aup) {
					AudioPlayer.playAudio("audio/swing.wav");
					isAttacking = true;
				} else if (c.getKeyManager().adown) {
					AudioPlayer.playAudio("audio/swing.wav");
					isAttacking = true;
				} else if (c.getKeyManager().aleft) {
					AudioPlayer.playAudio("audio/swing.wav");
					direction = 0;
					isAttacking = true;
				} else if (c.getKeyManager().aright) {
					AudioPlayer.playAudio("audio/swing.wav");
					direction = 1;
					isAttacking = true;
				} else if (c.getMouseManager().isLeftPressed()) {
					AudioPlayer.playAudio("audio/swing.wav");
					isAttacking = true;
				} else {
					isAttacking = false;
					swptAng = Math.PI;
				}
				
				attackTimer = 0; // cool down resets
				
				if(isAttacking) {
					
					if(lastPlayerTileX != playerTileX || lastPlayerTileY != playerTileY) {
						numSwings = 0;
						lastPlayerTileX = playerTileX;
						lastPlayerTileY = playerTileY;
					}
					
					numSwingsRequired = 3;

					numSwings++;

					if(c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getFloor()[playerTileX / Tile.TILEWIDTH][playerTileY / Tile.TILEHEIGHT] != 0 &&
							numSwings >= numSwingsRequired) {
						
						//where the item will drop
						int dropX = (int) ((int) getBounds().x + c.getGameCamera().getxOffset());
						int dropY = (int) ((int) getBounds().y + c.getGameCamera().getyOffset());

						//set what each type of platform will drop
						if(c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getFloor()[playerTileX / Tile.TILEWIDTH][playerTileY / Tile.TILEHEIGHT] == 1) { //wood floor
							c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getItemManager().addItem(Item.woodItem.createNew((int)(((int)dropX)*c.getScaleValue()), (int)(((int)dropY)*c.getScaleValue())));
						} else if(c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getFloor()[playerTileX / Tile.TILEWIDTH][playerTileY / Tile.TILEHEIGHT] == 2) { //stone floor
							c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getItemManager().addItem(Item.woodItem.createNew((int)(((int)dropX)*c.getScaleValue()), (int)(((int)dropY)*c.getScaleValue())));
						} else if(c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getFloor()[playerTileX / Tile.TILEWIDTH][playerTileY / Tile.TILEHEIGHT] == 3) { //metal floor
							c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getItemManager().addItem(Item.woodItem.createNew((int)(((int)dropX)*c.getScaleValue()), (int)(((int)dropY)*c.getScaleValue())));
						} 
						
						//destroy the platform
						c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getFloor()[playerTileX / Tile.TILEWIDTH][playerTileY / Tile.TILEHEIGHT] = 0;
						
						//if attacking lower the durability of the item  
						Tool tool = (Tool) item;
						tool.setCurrentEndurance(tool.getCurrentEndurance()-1);
						if(tool.getCurrentEndurance() <= 0) {
							inventory.removeItem(tool);
							hands.setHand(null);;
						}

					}
				}
				
			 } else if (item.getType().equals("tool")) { //used to break entities
    			
				attackTimer += System.currentTimeMillis() - lastAttackTimer;
				lastAttackTimer = System.currentTimeMillis();

				if (attackTimer < attackCooldown - bonusAttackSpeed)
					return;

				if (inMenu()) // July 15
					return;

				Rectangle cb = getCollisionBounds(0, 0); // collision bound
				Rectangle ar = new Rectangle();

				int arSize = 0;

				if (item.getType().equals("tool")) {
					Tool tool = (Tool) item;
					arSize = 40 + (int) tool.getRange(); // attack range, 25 pixels
					
				} else {
					arSize = 40;
				}
				
				ar.width = arSize;
				ar.height = arSize;

				if (c.getKeyManager().aup) {
					AudioPlayer.playAudio("audio/punch.wav");
					ar.x = cb.x + cb.width / 2 - arSize / 2; // get center of collision bound when player attacks up
					ar.y = cb.y - arSize;
					isAttacking = true;
				} else if (c.getKeyManager().adown) {
					AudioPlayer.playAudio("audio/punch.wav");
					ar.x = cb.x + cb.width / 2 - arSize / 2;
					ar.y = cb.y + cb.height;
					isAttacking = true;
				} else if (c.getKeyManager().aleft) {
					AudioPlayer.playAudio("audio/punch.wav");
					ar.x = cb.x - arSize;
					ar.y = cb.y + cb.height / 2 - arSize / 2;
					direction = 0;
					isAttacking = true;
				} else if (c.getKeyManager().aright) {
					AudioPlayer.playAudio("audio/punch.wav");
					ar.x = cb.x + cb.width;
					ar.y = cb.y + cb.height / 2 - arSize / 2;
					direction = 1;
					isAttacking = true;
				} else {
					isAttacking = false;
					swptAng = Math.PI;
				}

				if (isAttacking) {
					energy -= 5;
					if (energy <= 0) {
						energy = 0;
					}
				}
				
				attackTimer = 0; // cool down resets

				for (Entity e : c.getMenuState().getWorldSelectState().getGameState()
						.getWorldGenerator().getEntityManager().getEntitiesInBound()) {
					if (e.equals(this)) // cannot hurt the player itself while attacking
						continue;
					if (e.getCollisionBounds(0,0).intersects(ar)) {

						if(e.getType().equals("static entity")) {
							if(e.getRequiredTool().equals("")) {
								e.hurt(item.getDamage());
								if (item.getType().equals("tool")) {
									Tool tool = (Tool) item;
									tool.setCurrentEndurance(tool.getCurrentEndurance()-1);
									if(tool.getCurrentEndurance() <= 0) {
										inventory.removeItem(tool);
										hands.setHand(null);
										AudioPlayer.playAudio("audio/equipmentBreak.wav");
									}
								}
							}else if(e.getRequiredTool().equals("axe") && item.getName().equals("wooden axe") || item.getName().equals("stone axe") || item.getName().equals("chainsaw")) {
								e.hurt(item.getDamage()); //use tool damage
								Tool tool = (Tool) item;
								tool.setCurrentEndurance(tool.getCurrentEndurance()-1);
								if(tool.getCurrentEndurance() <= 0) {
									inventory.removeItem(tool);
									hands.setHand(null);
									AudioPlayer.playAudio("audio/equipmentBreak.wav");
								}
							}else if(e.getRequiredTool().equals("pickaxe") && item.getName().equals("wooden pickaxe") || item.getName().equals("stone pickaxe") || item.getName().equals("drill")) {
								e.hurt(item.getDamage()); //use tool damage
								Tool tool = (Tool) item;
								tool.setCurrentEndurance(tool.getCurrentEndurance()-1);
								if(tool.getCurrentEndurance() <= 0) {
									inventory.removeItem(tool);
									hands.setHand(null);
									AudioPlayer.playAudio("audio/equipmentBreak.wav");
								}
							}
							
						}
						
						return;
					}
				}
    			
    		} else if(item.getName().equals("blueprint")){
    			
				if (c.getKeyManager().aup || c.getKeyManager().adown || c.getKeyManager().aleft
						|| c.getKeyManager().aright || c.getMouseManager().isLeftPressed()) {
					
					if(!Recipe.lockedRecipes.isEmpty()) {
						int rand = r.nextInt(Recipe.lockedRecipes.size());
						
						Recipe recipe = Recipe.lockedRecipes.get(rand);
						
						if(recipe != null && recipe.getItem() != null) {
							//Add learned item to unlocked recipe list
							Recipe.lockedRecipes.remove(recipe);
							Recipe.unlockedRecipes.add(recipe);
							
							inventory.removeItem(hands.getHand());
							
							MessageBox.addMessage(String.format("Recipe Unlocked: %s", recipe.getItem().getName()));
						}

					}
					
				}
    			
    		} else {
				attackTimer += System.currentTimeMillis() - lastAttackTimer;
				lastAttackTimer = System.currentTimeMillis();

				if (attackTimer < attackCooldown - bonusAttackSpeed)
					return;

				if (inMenu()) 
					return;
				/*
				 * double mouseX = c.getMouseManager().getMouseX(); double mouseY =
				 * c.getMouseManager().getMouseY();
				 * 
				 * double originX = c.getWidth() / 2 + 10; double originY = c.getHeight() / 2 +
				 * 10;
				 * 
				 * shootAngle = Math.atan2(mouseX - originX, mouseY - originY);
				 */
				Rectangle cb = getCollisionBounds(0, 0); // collision bound
				Rectangle ar = new Rectangle();
				// ab = new Rectangle();

				// Point center = new Point(cb.x + cb.width/2, cb.y + cb.height/2);
				// AffineTransform at = new AffineTransform();
				int arSize = 0;

				if (item.getType().equals("weapon")) {
					Weapon weapon = (Weapon) item;
					arSize = 40 + (int) weapon.getRange(); // attack range, 25 pixels
				} else {
					arSize = 40;
				}

				ar.width = arSize;
				ar.height = arSize;

				boolean condition = false;
				int arSizeChange = arSize / 5;

				if ((c.getKeyManager().aup && c.getKeyManager().aleft)
						|| ((c.getMouseManager().isLeftClicked() || c.getMouseManager().isLeftPressed())
								&& c.getMouseManager().mouseBound().y + c.getGameCamera().getyOffset() < y - 50
								&& c.getMouseManager().mouseBound().x + c.getGameCamera().getxOffset() < x + bounds.x
										- 20)) {
					if(item.getName().equals("dark saber")) 
						AudioPlayer.playAudio("audio/dark_saber.wav");
					else 
						AudioPlayer.playAudio("audio/swing.wav");
					arSize -= arSizeChange;
					ar.x = cb.x - arSize; // get center of collision bound when player attacks up
					ar.y = cb.y - arSize;
					direction = 0;
					condition = true;
					isAttacking = true;
				} else if ((c.getKeyManager().aup && c.getKeyManager().aright)
						|| ((c.getMouseManager().isLeftClicked() || c.getMouseManager().isLeftPressed())
								&& c.getMouseManager().mouseBound().y + c.getGameCamera().getyOffset() < y - 50
								&& c.getMouseManager().mouseBound().x + c.getGameCamera().getxOffset() > x + bounds.x
										+ bounds.width + 20)) {
					if(item.getName().equals("dark saber")) 
						AudioPlayer.playAudio("audio/dark_saber.wav");
					else 
						AudioPlayer.playAudio("audio/swing.wav");
					arSize -= arSizeChange;
					ar.x = cb.x + cb.width; // get center of collision bound when player attacks up
					ar.y = cb.y - arSize;
					arSize -= arSizeChange;
					direction = 1;
					condition = true;
					isAttacking = true;
				} else if ((c.getKeyManager().adown && c.getKeyManager().aleft)
						|| ((c.getMouseManager().isLeftClicked() || c.getMouseManager().isLeftPressed())
								&& c.getMouseManager().mouseBound().y + c.getGameCamera().getyOffset() > y + bounds.y
										+ bounds.width
								&& c.getMouseManager().mouseBound().x + c.getGameCamera().getxOffset() < x + bounds.x
										- 20)) {
					if(item.getName().equals("dark saber")) 
						AudioPlayer.playAudio("audio/dark_saber.wav");
					else 
						AudioPlayer.playAudio("audio/swing.wav");
					arSize -= arSizeChange;
					ar.x = cb.x - arSize; // get center of collision bound when player attacks up
					ar.y = cb.y + cb.height;
					direction = 0;
					condition = true;
					isAttacking = true;
				} else if ((c.getKeyManager().adown && c.getKeyManager().aright)
						|| ((c.getMouseManager().isLeftClicked() || c.getMouseManager().isLeftPressed())
								&& c.getMouseManager().mouseBound().y + c.getGameCamera().getyOffset() > y + bounds.y
										+ bounds.width
								&& c.getMouseManager().mouseBound().x + c.getGameCamera().getxOffset() > x + bounds.x
										+ bounds.width + 20)) {
					if(item.getName().equals("dark saber")) 
						AudioPlayer.playAudio("audio/dark_saber.wav");
					else 
						AudioPlayer.playAudio("audio/swing.wav");
					arSize -= arSizeChange;
					ar.x = cb.x + cb.width; // get center of collision bound when player attacks up
					ar.y = cb.y + cb.height;
					direction = 1;
					condition = true;
					isAttacking = true;
				} else if (c.getKeyManager().aup
						|| ((c.getMouseManager().isLeftClicked() || c.getMouseManager().isLeftPressed())
								&& c.getMouseManager().mouseBound().y + c.getGameCamera().getyOffset() < y
								&& c.getMouseManager().mouseBound().x + c.getGameCamera().getxOffset() > x + bounds.x
										- 20
								&& c.getMouseManager().mouseBound().x + c.getGameCamera().getxOffset() < x + bounds.x
										+ bounds.width + 20)) {
					if(item.getName().equals("dark saber")) 
						AudioPlayer.playAudio("audio/dark_saber.wav");
					else 
						AudioPlayer.playAudio("audio/swing.wav");
					ar.x = cb.x + cb.width / 2 - arSize / 2; // get center of collision bound when player attacks up
					ar.y = cb.y - arSize;
					isAttacking = true;
				} else if (c.getKeyManager().adown
						|| ((c.getMouseManager().isLeftClicked() || c.getMouseManager().isLeftPressed())
								&& c.getMouseManager().mouseBound().y + c.getGameCamera().getyOffset() > y + bounds.y
										+ bounds.height
								&& c.getMouseManager().mouseBound().x + c.getGameCamera().getxOffset() > x + bounds.x
										- 20
								&& c.getMouseManager().mouseBound().x + c.getGameCamera().getxOffset() < x + bounds.x
										+ bounds.width + 20)) {
					if(item.getName().equals("dark saber")) 
						AudioPlayer.playAudio("audio/dark_saber.wav");
					else 
						AudioPlayer.playAudio("audio/swing.wav");
					ar.x = cb.x + cb.width / 2 - arSize / 2;
					ar.y = cb.y + cb.height;
					isAttacking = true;
				} else if (c.getKeyManager().aleft
						|| ((c.getMouseManager().isLeftClicked() || c.getMouseManager().isLeftPressed())
								&& c.getMouseManager().mouseBound().x + c.getGameCamera().getxOffset() < x + bounds.x
										+ bounds.width / 2
								&& c.getMouseManager().mouseBound().y + c.getGameCamera().getyOffset() > y - 50
										+ bounds.y
								&& c.getMouseManager().mouseBound().y + c.getGameCamera().getyOffset() < y + bounds.y
										+ bounds.height)) {
					if(item.getName().equals("dark saber")) 
						AudioPlayer.playAudio("audio/dark_saber.wav");
					else 
						AudioPlayer.playAudio("audio/swing.wav");
					ar.x = cb.x - arSize;
					ar.y = cb.y + cb.height / 2 - arSize / 2;
					direction = 0;
					isAttacking = true;
				} else if (c.getKeyManager().aright
						|| ((c.getMouseManager().isLeftClicked() || c.getMouseManager().isLeftPressed())
								&& c.getMouseManager().mouseBound().x + c.getGameCamera().getxOffset() > x + bounds.x
										+ bounds.width / 2
								&& c.getMouseManager().mouseBound().y + c.getGameCamera().getyOffset() > y - 50
										+ bounds.y
								&& c.getMouseManager().mouseBound().y + c.getGameCamera().getyOffset() < y + bounds.y
										+ bounds.height)) {
					if(item.getName().equals("dark saber")) 
						AudioPlayer.playAudio("audio/dark_saber.wav");
					else 
						AudioPlayer.playAudio("audio/swing.wav");
					ar.x = cb.x + cb.width;
					ar.y = cb.y + cb.height / 2 - arSize / 2;
					direction = 1;
					isAttacking = true;
				}

				else {
					isAttacking = false;
					swptAng = Math.PI;
				}

				if (isAttacking) {
					energy -= 5;
					if (energy <= 0) {
						energy = 0;
					}
				}

				if (condition)
					arSize += arSizeChange;

				attackTimer = 0; // cool down resets

				for(int i = 0; i < c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
						.getEntityManager().getEntitiesInBound().size(); i++) {
					
					Entity e = c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
							.getEntityManager().getEntitiesInBound().get(i);
					
					if (e.equals(this)) // cannot hurt the player itself while attacking
						continue;
					if (ar.intersects(e.getCollisionBounds(0, 0))) {
						
						if (item.getType().equals("weapon")) {
							Weapon weapon = (Weapon) item;
							weapon.setCurrentEndurance(weapon.getCurrentEndurance() - 1);
							if (weapon.getCurrentEndurance() <= 0) {
								inventory.removeItem(weapon);
								hands.setHand(null);
								AudioPlayer.playAudio("audio/equipmentBreak.wav");
							}
							
							if (e.getType().equals("creatures")) {
								AudioPlayer.playAudio("audio/hit.wav");
								e.hurt(damage);
								double eX = this.getBounds().getX() + getBounds().getWidth() / 2;
								double eY = this.getBounds().getY() + getBounds().getHeight() / 2;
								double sX = e.getBounds().getX() + e.getBounds().getWidth() / 2;
								double sY = e.getBounds().getY() + e.getBounds().getHeight() / 2;
								shootAngle = Math.atan2(sX - eX, sY - eY);
								knockbackTarget((Creatures) e, this, true, shootAngle);
							} else {
								e.hurt(damage);
								AudioPlayer.playAudio("audio/hitSolid.wav");
							}
							
						} else if(item.getType().equals("axe") || item.getType().equals("pickaxe")) {
							Tool tool = (Tool) item;
							tool.setCurrentEndurance(tool.getCurrentEndurance() - 1);
							if (tool.getCurrentEndurance() <= 0) {
								inventory.removeItem(tool);
								hands.setHand(null);
								AudioPlayer.playAudio("audio/equipmentBreak.wav");
							}
							
							if (e.getType().equals("creatures")) {
								AudioPlayer.playAudio("audio/hit.wav");
								e.hurt(damage);
								double eX = this.getBounds().getX() + getBounds().getWidth() / 2;
								double eY = this.getBounds().getY() + getBounds().getHeight() / 2;
								double sX = e.getBounds().getX() + e.getBounds().getWidth() / 2;
								double sY = e.getBounds().getY() + e.getBounds().getHeight() / 2;
								shootAngle = Math.atan2(sX - eX, sY - eY);
								knockbackTarget((Creatures) e, this, true, shootAngle);
							} else if(e.getRequiredTool().equals("axe") && e.getRequiredTool().equals("axe")) {
								e.hurt((int)(tool.getPower()*tool.getDamage())); 
								AudioPlayer.playAudio("audio/hitSolid.wav");
							} else if(e.getRequiredTool().equals("pickaxe") && e.getRequiredTool().equals("pickaxe")) {
								e.hurt((int)(tool.getPower()*tool.getDamage())); 
								AudioPlayer.playAudio("audio/hitSolid.wav");
							} else {
								e.hurt((int)(tool.getPower()*tool.getDamage())); 
								AudioPlayer.playAudio("audio/hitSolid.wav");
							}
						}
					}
				}
			}

		}

		else {

			attackTimer += System.currentTimeMillis() - lastAttackTimer;
			lastAttackTimer = System.currentTimeMillis();

			if (attackTimer < attackCooldown - bonusAttackSpeed)
				return;

			if (inMenu()) 
				return;

			/*
			 * double mouseX = c.getMouseManager().getMouseX(); double mouseY =
			 * c.getMouseManager().getMouseY();
			 * 
			 * double originX = c.getWidth() / 2 + 10; double originY = c.getHeight() / 2 +
			 * 10;
			 * 
			 * shootAngle = Math.atan2(mouseX - originX, mouseY - originY);
			 */

			Rectangle cb = getCollisionBounds(0, 0); // collision bound
			int arSize = 40; // attack range, 25 pixels
			Rectangle ar = new Rectangle();
			// Shape ab = new Rectangle();
			ar.width = arSize;
			ar.height = arSize;
			// Point center = new Point(cb.x + cb.width/2, cb.y + cb.height/2);
			// AffineTransform at = new AffineTransform();

			// ar.x = cb.x + cb.width;
			// ar.y = center.y - arSize/2;

			boolean condition = false;
			int arSizeChange = arSize / 5;

			/*
			 * if(c.getMouseManager().isLeftClicked() ||
			 * c.getMouseManager().isLeftPressed()) { System.out.println(shootAngle);
			 * at.rotate(shootAngle, center.x, center.y); ab =
			 * at.createTransformedShape(ar); if(shootAngle < Math.PI/4 || shootAngle >
			 * Math.PI/4*3) direction = 0; else direction = 1; condition = true; isAttacking
			 * = true; }
			 */

			if ((c.getKeyManager().aup && c.getKeyManager().aleft)
					|| ((c.getMouseManager().isLeftClicked() || c.getMouseManager().isLeftPressed())
							&& c.getMouseManager().mouseBound().y + c.getGameCamera().getyOffset() < y - 50
							&& c.getMouseManager().mouseBound().x + c.getGameCamera().getxOffset() < x + bounds.x
									- 20)) {
				AudioPlayer.playAudio("audio/swing.wav");
				arSize -= arSizeChange;
				ar.x = cb.x - arSize; // get center of collision bound when player attacks up
				ar.y = cb.y - arSize;
				direction = 0;
				condition = true;
				isAttacking = true;
			} else if ((c.getKeyManager().aup && c.getKeyManager().aright)
					|| ((c.getMouseManager().isLeftClicked() || c.getMouseManager().isLeftPressed())
							&& c.getMouseManager().mouseBound().y + c.getGameCamera().getyOffset() < y - 50
							&& c.getMouseManager().mouseBound().x + c.getGameCamera().getxOffset() > x + bounds.x
									+ bounds.width + 20)) {
				AudioPlayer.playAudio("audio/swing.wav");
				arSize -= arSizeChange;
				ar.x = cb.x + cb.width; // get center of collision bound when player attacks up
				ar.y = cb.y - arSize;
				arSize -= arSizeChange;
				direction = 1;
				condition = true;
				isAttacking = true;
			} else if ((c.getKeyManager().adown && c.getKeyManager().aleft)
					|| ((c.getMouseManager().isLeftClicked() || c.getMouseManager().isLeftPressed())
							&& c.getMouseManager().mouseBound().y + c.getGameCamera().getyOffset() > y + bounds.y
									+ bounds.width
							&& c.getMouseManager().mouseBound().x + c.getGameCamera().getxOffset() < x + bounds.x
									- 20)) {
				AudioPlayer.playAudio("audio/swing.wav");
				arSize -= arSizeChange;
				ar.x = cb.x - arSize; // get center of collision bound when player attacks up
				ar.y = cb.y + cb.height;
				direction = 0;
				condition = true;
				isAttacking = true;
			} else if ((c.getKeyManager().adown && c.getKeyManager().aright)
					|| ((c.getMouseManager().isLeftClicked() || c.getMouseManager().isLeftPressed())
							&& c.getMouseManager().mouseBound().y + c.getGameCamera().getyOffset() > y + bounds.y
									+ bounds.width
							&& c.getMouseManager().mouseBound().x + c.getGameCamera().getxOffset() > x + bounds.x
									+ bounds.width + 20)) {
				AudioPlayer.playAudio("audio/swing.wav");
				arSize -= arSizeChange;
				ar.x = cb.x + cb.width; // get center of collision bound when player attacks up
				ar.y = cb.y + cb.height;
				direction = 1;
				condition = true;
				isAttacking = true;
			} else if (c.getKeyManager().aup
					|| ((c.getMouseManager().isLeftClicked() || c.getMouseManager().isLeftPressed())
							&& c.getMouseManager().mouseBound().y + c.getGameCamera().getyOffset() < y
							&& c.getMouseManager().mouseBound().x + c.getGameCamera().getxOffset() > x + bounds.x - 20
							&& c.getMouseManager().mouseBound().x + c.getGameCamera().getxOffset() < x + bounds.x
									+ bounds.width + 20)) {
				AudioPlayer.playAudio("audio/swing.wav");
				ar.x = cb.x + cb.width / 2 - arSize / 2; // get center of collision bound when player attacks up
				ar.y = cb.y - arSize;
				isAttacking = true;
			} else if (c.getKeyManager().adown
					|| ((c.getMouseManager().isLeftClicked() || c.getMouseManager().isLeftPressed())
							&& c.getMouseManager().mouseBound().y + c.getGameCamera().getyOffset() > y + bounds.y
									+ bounds.height
							&& c.getMouseManager().mouseBound().x + c.getGameCamera().getxOffset() > x + bounds.x - 20
							&& c.getMouseManager().mouseBound().x + c.getGameCamera().getxOffset() < x + bounds.x
									+ bounds.width + 20)) {
				AudioPlayer.playAudio("audio/swing.wav");
				ar.x = cb.x + cb.width / 2 - arSize / 2;
				ar.y = cb.y + cb.height;
				isAttacking = true;
			} else if (c.getKeyManager().aleft
					|| ((c.getMouseManager().isLeftClicked() || c.getMouseManager().isLeftPressed())
							&& c.getMouseManager().mouseBound().x + c.getGameCamera().getxOffset() < x + bounds.x
							&& c.getMouseManager().mouseBound().y + c.getGameCamera().getyOffset() > y - 50 + bounds.y
							&& c.getMouseManager().mouseBound().y + c.getGameCamera().getyOffset() < y + bounds.y
									+ bounds.height)) {
				AudioPlayer.playAudio("audio/swing.wav");
				ar.x = cb.x - arSize;
				ar.y = cb.y + cb.height / 2 - arSize / 2;
				direction = 0;
				isAttacking = true;
			} else if (c.getKeyManager().aright
					|| ((c.getMouseManager().isLeftClicked() || c.getMouseManager().isLeftPressed())
							&& c.getMouseManager().mouseBound().x + c.getGameCamera().getxOffset() > x + bounds.x
									+ bounds.width
							&& c.getMouseManager().mouseBound().y + c.getGameCamera().getyOffset() > y - 50 + bounds.y
							&& c.getMouseManager().mouseBound().y + c.getGameCamera().getyOffset() < y + bounds.y
									+ bounds.height)) {
				AudioPlayer.playAudio("audio/swing.wav");
				ar.x = cb.x + cb.width;
				ar.y = cb.y + cb.height / 2 - arSize / 2;
				direction = 1;
				isAttacking = true;
			} else {
				isAttacking = false;
				swptAng = Math.PI;
			}

			if (isAttacking) {
				if (stealth) {
					c.getGameState().getWorldGenerator().getMessageBox().addMessage("exiting stealth mode...");
					stealth = false;
				}
				energy -= 5;
				if (energy <= 0) {
					energy = 0;
				}
			}

			if (condition)
				arSize += arSizeChange;

			attackTimer = 0; // cool down resets

			for (int i = 0; i < c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
					.getEntityManager().getEntitiesInBound().size(); i++) {

				if (c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getEntityManager()
						.getEntitiesInBound().get(i).equals(this)) // cannot hurt the player itself while
																	// attacking
					continue;
				if (c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getEntityManager()
						.getEntitiesInBound().get(i).getCollisionBounds(0, 0).intersects(ar)) {
					
					if (c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getEntityManager()
							.getEntitiesInBound().get(i).getRequiredTool().equals(""))
						c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getEntityManager()
								.getEntitiesInBound().get(i).hurt(damage);
					if (c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getEntityManager()
							.getEntitiesInBound().get(i).getType().equals("creatures")) {
						AudioPlayer.playAudio("audio/hit.wav");
						
						double eX = this.getBounds().getX() + getBounds().getWidth() / 2;
						double eY = this.getBounds().getY() + getBounds().getHeight() / 2;
						double sX = c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
								.getEntityManager().getEntitiesInBound().get(i).getBounds().getX()
								+ c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
										.getEntityManager().getEntitiesInBound().get(i).getBounds().getWidth() / 2;
						double sY = c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
								.getEntityManager().getEntitiesInBound().get(i).getBounds().getY()
								+ c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
										.getEntityManager().getEntitiesInBound().get(i).getBounds().getHeight() / 2;
						shootAngle = Math.atan2(sX - eX, sY - eY);
						knockbackTarget((Creatures) c.getMenuState().getWorldSelectState().getGameState()
								.getWorldGenerator().getEntityManager().getEntitiesInBound().get(i), this, true,
								shootAngle);
					} else {
						c.getMenuState().getWorldSelectState().getGameState()
						.getWorldGenerator().getEntityManager().getEntitiesInBound().get(i).hurt(damage);
						AudioPlayer.playAudio("audio/hitSolid.wav");
					}
					return;
				}

			}

		}

	}

	// method that detects user inputs
	private void getInput() {
		
		velX = 0;
		velY = 0;

		if (canMove) {

			if (!destinationReached && !filling) {
				eX = x + bounds.x + bounds.width / 2;
				eY = y + bounds.y + bounds.height / 2;

				if (eX > sX - 1 && eX < sX + 1 && eY > sY - 1 && eY < sY + 1)
					destinationReached = true;

				desinationAngle = Math.atan2(sX - eX, sY - eY);

				velX = (currentSpeed + extraSpeed) * Math.sin(desinationAngle);
				velY = (currentSpeed + extraSpeed) * Math.cos(desinationAngle);

				if (velX > 0)
					direction = 1;
				else if (velX < 0)
					direction = 0;
			}

			if (c.getMouseManager().isRightClicked()) {
				destinationReached = false;
				sX = c.getMouseManager().mouseBound().x + c.getGameCamera().getxOffset();
				sY = c.getMouseManager().mouseBound().y + c.getGameCamera().getyOffset();
			}

			if (c.getMouseManager().isRightPressed()) {

				destinationReached = true;

				sX = c.getMouseManager().mouseBound().x + c.getGameCamera().getxOffset();
				sY = c.getMouseManager().mouseBound().y + c.getGameCamera().getyOffset();

				eX = x + bounds.x + bounds.width / 2;
				eY = y + bounds.y + bounds.height / 2;

				desinationAngle = Math.atan2(sX - eX, sY - eY);

				velX = (currentSpeed + extraSpeed) * Math.sin(desinationAngle);
				velY = (currentSpeed + extraSpeed) * Math.cos(desinationAngle);

				if (velX > 0)
					direction = 1;
				else if (velX < 0)
					direction = 0;

			}

			if (inMenu()) 
				return;

			// key detection
			if (c.getKeyManager().up) {

				destinationReached = true;
				velY = -(currentSpeed + extraSpeed);

			}
			if (c.getKeyManager().down) {

				destinationReached = true;
				velY = (currentSpeed + extraSpeed);

			}
			if (c.getKeyManager().left) {

				destinationReached = true;
				velX = -(currentSpeed + extraSpeed);
				if (!isAttacking && !hands.isRangedActive())
					direction = 0;

			}
			if (c.getKeyManager().right) {

				destinationReached = true;
				velX = (currentSpeed + extraSpeed);
				if (!isAttacking && !hands.isRangedActive())
					direction = 1;

			}

			// if run button is pressed, then change velocity to the player run speed
			if (c.getKeyManager().run && energy > ogEndurance / 3) {

				if (energy > ogEndurance / 3) {
					if (inventory.getInventoryWeight() > damage / 10) { // damage is also a measure of strength
						if (energy > ogEndurance / 2)
							currentSpeed = (runSpeed - 0.5 - (inventory.getInventoryWeight() - damage / 10) / 35
									+ extraSpeed) * ControlCenter.scaleValue;
						else if (energy > ogEndurance / 3)
							currentSpeed = (runSpeed - 1 - (inventory.getInventoryWeight() - damage / 10) / 35
									+ extraSpeed) * ControlCenter.scaleValue;
						else if (energy > ogEndurance / 5)
							currentSpeed = (runSpeed - 1.5 - (inventory.getInventoryWeight() - damage / 10) / 35
									+ extraSpeed) * ControlCenter.scaleValue;
						else if (energy > ogEndurance / 10)
							currentSpeed = (runSpeed - 2 - (inventory.getInventoryWeight() - damage / 10) / 35
									+ extraSpeed) * ControlCenter.scaleValue;

						if (currentSpeed < 0)
							currentSpeed = 0;
					} else {

						if (energy > ogEndurance / 2 + extraSpeed)
							currentSpeed = (runSpeed - 0.5) * ControlCenter.scaleValue;
						else if (energy > ogEndurance / 3 + extraSpeed)
							currentSpeed = (runSpeed - 1) * ControlCenter.scaleValue;
						else if (energy > ogEndurance / 5 + extraSpeed)
							currentSpeed = (runSpeed - 1.5) * ControlCenter.scaleValue;
						else if (energy > ogEndurance / 10 + extraSpeed)
							currentSpeed = (runSpeed - 2) * ControlCenter.scaleValue;

						if (currentSpeed < 0)
							currentSpeed = 0;

					}
					running = true;
				}

				if (energy < ogEndurance / 3)
					running = false;

			} else {

				if (inventory.getInventoryWeight() > damage / 10) { // damage is also a measure of strength {
					if (energy > ogEndurance / 2)
						currentSpeed = (speed - (inventory.getInventoryWeight() - damage / 10) / 35 + extraSpeed)
								* ControlCenter.scaleValue;
					else if (energy > ogEndurance / 3)
						currentSpeed = (speed/4*3 - (inventory.getInventoryWeight() - damage / 10) / 35 + extraSpeed)
								* ControlCenter.scaleValue;
					else if (energy > ogEndurance / 5)
						currentSpeed = (speed/2 - (inventory.getInventoryWeight() - damage / 10) / 35 + extraSpeed)
								* ControlCenter.scaleValue;
					else if (energy > ogEndurance / 10)
						currentSpeed = (speed/4 - (inventory.getInventoryWeight() - damage / 10) / 35 + extraSpeed)
								* ControlCenter.scaleValue;

					if (currentSpeed < 0)
						currentSpeed = 0;

				} else {
					if (energy > ogEndurance / 2)
						currentSpeed = (speed + extraSpeed) * ControlCenter.scaleValue;
					else if (energy > ogEndurance / 3)
						currentSpeed = (speed/4*3 + extraSpeed) * ControlCenter.scaleValue;
					else if (energy > ogEndurance / 5)
						currentSpeed = (speed/2 + extraSpeed) * ControlCenter.scaleValue;
					else if (energy > ogEndurance / 10)
						currentSpeed = (speed/4 + extraSpeed) * ControlCenter.scaleValue;

					if (currentSpeed < 0)
						currentSpeed = 0;
				}
				running = false;

			}

		} else if (!c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().isStructureCrafted()
				&& !c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
						.isStructureWorkbenchCrafted()
				&& !c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
						.isCurrentlyBuildingStructure()) { // July 26

			velX += knockX;
			velY += knockY;

			new java.util.Timer().schedule(new java.util.TimerTask() {
				@Override
				public void run() {

					canMove = true;

				}
			}, 80);

		}

		if (isMoving) {

			energyTimer += System.currentTimeMillis() - lastEnergyTimer;
			lastEnergyTimer = System.currentTimeMillis();

			if (energyTimer > energyCooldown) {

				if (running)
					energy -= 15;
				else
					energy -= 1;

				if (energy <= 0)
					energy = 0;

				energyTimer = 0;

			}

		} else {
			energyRegenTimer += System.currentTimeMillis() - lastEnergyRegenTimer;
			lastEnergyRegenTimer = System.currentTimeMillis();

			if (energyRegenTimer > energyRegenCooldown) {

				energy += energyRegenAmt;
				if (energy > ogEndurance)
					energy = ogEndurance;

				energyRegenTimer = 0;

			}
		}

		if (health < ogHealth) {
			healthRegenTimer += System.currentTimeMillis() - lastHealthRegenTimer;
			lastHealthRegenTimer = System.currentTimeMillis();

			if (healthRegenTimer > healthRegenCooldown) {

				health += healthRegenAmt;
				if (health > ogHealth)
					health = ogHealth;

				healthRegenTimer = 0;

			}
		}

		if (hunger == 0 && thirst == 0) {
			healthDegenTimer += System.currentTimeMillis() - lastHealthDegenTimer;
			lastHealthDegenTimer = System.currentTimeMillis();

			if (healthDegenTimer > healthDegenCooldown) {

				health -= 5;
				if (health > ogHealth)
					health = ogHealth;

				healthDegenTimer = 0;

			}
		} else if (hunger == 0) {
			healthDegenTimer += System.currentTimeMillis() - lastHealthDegenTimer;
			lastHealthDegenTimer = System.currentTimeMillis();

			if (healthDegenTimer > healthDegenCooldown) {

				health -= 3;
				if (health > ogHealth)
					health = ogHealth;

				healthDegenTimer = 0;

			}
		} else if (thirst == 0) {
			healthDegenTimer += System.currentTimeMillis() - lastHealthDegenTimer;
			lastHealthDegenTimer = System.currentTimeMillis();

			if (healthDegenTimer > healthDegenCooldown) {

				health -= 2;
				if (health > ogHealth)
					health = ogHealth;

				healthDegenTimer = 0;

			}
		}

		if (energy < ogEndurance / 5 && thirst <= ogEndurance / 5 && hunger <= ogEndurance / 5) {
			c.getGameState().getWorldGenerator().getEffects().addEffect(new Effect("exausted", 1));
			if (!exaustStated) {
				c.getGameState().getWorldGenerator().getMessageBox().addMessage("you're starting to faint...");
				exaustStated = true;
			}
		} else
			exaustStated = false;

		if (energy < ogEndurance / 3) {
			c.getGameState().getWorldGenerator().getEffects().addEffect(new Effect("tired", 1));
			if (!tiredStated) {
				c.getGameState().getWorldGenerator().getMessageBox().addMessage("you're feeling tired...");
				tiredStated = true;
			}
		} else
			tiredStated = false;

	}
	
	// checks food status
	public void checkFood() {
        for(int i = 0; i < inventory.InventoryItems.size(); i++) {
                if(inventory.InventoryItems.get(i).getType().equals("food")) {
                        
                        Food food = (Food)inventory.InventoryItems.get(i);
                        food.rotTimer += System.currentTimeMillis() - food.lastrotTimer;
                        food.lastrotTimer = System.currentTimeMillis();

                        if (food.rotTimer < food.rotCooldown)
                                continue;
                        
                        if(!food.getFood().equals("rotten") && food.currentFreshness > 0) {
                                food.currentFreshness--;
                                if(food.currentFreshness <= 1) {
                                        if(food.getName().equals("raw chicken") || food.getName().equals("cooked chicken") || food.getName().equals("suspicious chicken")) {
                                        	if(hands.leftHand != null) 
                                        		if(hands.leftHand.equals(food))
                                        			hands.leftHand = Food.rottenChickenItem;
                                        	
                                        	if(hands.rightHand != null)
                                        		if(hands.rightHand.equals(food))
                                        			hands.rightHand = Food.rottenChickenItem;
                                        	
                                        	inventory.removeItem(food);
                                        	inventory.addItem(Food.rottenChickenItem);
                                        }
                                        else if(food.getName().equals("raw morsel") || food.getName().equals("cooked morsel") || food.getName().equals("suspicious morsel")) {
                                        	if(hands.leftHand != null) 
                                        		if(hands.leftHand.equals(food))
                                        			hands.leftHand = Food.rottenMorselItem;
                                        	
                                        	if(hands.rightHand != null)
                                        		if(hands.rightHand.equals(food))
                                        			hands.rightHand = Food.rottenMorselItem;
                                        	
                                        	inventory.removeItem(food);
                                        	inventory.addItem(Food.rottenMorselItem);
                                        }
                                        else if(food.getName().equals("raw meat") || food.getName().equals("cooked meat") || food.getName().equals("suspicious meat")) {
                                        	if(hands.leftHand != null) 
                                        		if(hands.leftHand.equals(food))
                                        			hands.leftHand = Food.rottenMeatItem;
                                        	
                                        	if(hands.rightHand != null)
                                        		if(hands.rightHand.equals(food))
                                        			hands.rightHand = Food.rottenMeatItem;
                                        	
                                        	inventory.removeItem(food);
                                        	inventory.addItem(Food.rottenMeatItem);
                                        }
                                        else if(food.getName().equals("bug meat") || food.getName().equals("cooked bug")) {
                                        	if(hands.leftHand != null) 
                                        		if(hands.leftHand.equals(food))
                                        			hands.leftHand = Food.rottenBugItem;
                                        	
                                        	if(hands.rightHand != null)
                                        		if(hands.rightHand.equals(food))
                                        			hands.rightHand = Food.rottenBugItem;
                                        	
                                        	inventory.removeItem(food);
                                        	inventory.addItem(Food.rottenBugItem);
                                        }
                                        else {
                                        	if(hands.leftHand != null) 
                                        		if(hands.leftHand.equals(food))
                                        			hands.leftHand = Food.rotItem;
                                        	
                                        	if(hands.rightHand != null)
                                        		if(hands.rightHand.equals(food))
                                        			hands.rightHand = Food.rotItem;
                                        	
                                        	inventory.removeItem(food);
                                        	inventory.addItem(Food.rotItem);
                                        }
                                        return;
                                }
                        }
                        
                        food.rotTimer = 0;
                }
        }
	}
	
	public void overnightFood() {
		for(int i = 0; i < inventory.InventoryItems.size(); i++) {
            if(inventory.InventoryItems.get(i).getType().equals("food")) {
                    
                    Food food = (Food)inventory.InventoryItems.get(i);
                    
                    if(!food.getFood().equals("rotten") && food.currentFreshness > 0) {
                            food.currentFreshness -= 58;
                            if(food.currentFreshness <= 1) {
                                    if(food.getName().equals("raw chicken") || food.getName().equals("cooked chicken") || food.getName().equals("suspicious chicken")) {
                                    	if(hands.leftHand.equals(food))
                                    		hands.leftHand = Food.rottenChickenItem;
                                    	else if(hands.rightHand.equals(food))
                                    		hands.rightHand = Food.rottenChickenItem;
                                    	
                                    	inventory.removeItem(food);
                                    	inventory.addItem(Food.rottenChickenItem);
                                    }
                                    else if(food.getName().equals("raw morsel") || food.getName().equals("cooked morsel") || food.getName().equals("suspicious morsel")) {
                                    	if(hands.leftHand.equals(food))
                                    		hands.leftHand = Food.rottenMorselItem;
                                    	else if(hands.rightHand.equals(food))
                                    		hands.rightHand = Food.rottenMorselItem;
                                    	
                                    	inventory.removeItem(food);
                                    	inventory.addItem(Food.rottenMorselItem);
                                    }
                                    else if(food.getName().equals("raw meat") || food.getName().equals("cooked meat") || food.getName().equals("suspicious meat")) {
                                    	if(hands.leftHand.equals(food))
                                    		hands.leftHand = Food.rottenMeatItem;
                                    	else if(hands.rightHand.equals(food))
                                    		hands.rightHand = Food.rottenMeatItem;
                                    	
                                    	inventory.removeItem(food);
                                    	inventory.addItem(Food.rottenMeatItem);
                                    }
                                    else if(food.getName().equals("bug meat") || food.getName().equals("cooked bug")) {
                                    	if(hands.leftHand.equals(food))
                                    		hands.leftHand = Food.rottenBugItem;
                                    	else if(hands.rightHand.equals(food))
                                    		hands.rightHand = Food.rottenBugItem;
                                    	
                                    	inventory.removeItem(food);
                                    	inventory.addItem(Food.rottenBugItem);
                                    }
                                    else {
                                    	if(hands.leftHand.equals(food))
                                    		hands.leftHand = Food.rotItem;
                                    	else if(hands.rightHand.equals(food))
                                    		hands.rightHand = Food.rotItem;
                                    	
                                    	inventory.removeItem(food);
                                    	inventory.addItem(Food.rotItem);
                                    }
                            }
                    }
                    
            }
		} for(int i = 0; i < c.getGameState().getWorldGenerator().getEntityManager().getEntities().size(); i++) {
			Entity e = c.getGameState().getWorldGenerator().getEntityManager().getEntities().get(i);
			if(e.getName().equals("chest")) {
				Chest c = (Chest)e;
				c.getInventory().overnightFood();
			}
		}
	}

	@Override
	public void render(Graphics g) {

		if (!stealth) {

			Graphics2D g2d = (Graphics2D) g;

			AffineTransform backup = g2d.getTransform();
			AffineTransform at = g2d.getTransform();
			AffineTransform at2 = g2d.getTransform();
			
			if(hands.getHand() != null && hands.getHand().getType().equals("ranged")) {
				
				if (direction == 1) {
					at.translate(c.getWidth() / 2 - 38, c.getHeight() / 2 - 85);
					at.rotate(-shootAngle, 59, 64);
					g2d.setTransform(at);
					g2d.drawImage(Arm, Arm.getWidth() / 2, Arm.getHeight() / 2, 64, 64, null);
					
					if(hands.getHand().getName().equals("glock")) {
						g2d.drawImage(Assets.glockActive, Arm.getWidth() / 2, Arm.getHeight() / 2, 64, 64, null);
					} else if(hands.getHand().getName().equals("desert eagle")) {
						g2d.drawImage(Assets.desertEagleActive, Arm.getWidth() / 2, Arm.getHeight() / 2, 64, 64, null);
					} else if(hands.getHand().getName().equals("wooden bow")) {
						g2d.drawImage(Assets.woodenBowActive, Arm.getWidth() / 2, Arm.getHeight() / 2, 64, 64, null);
					} else if(hands.getHand().getName().equals("bronze bow")) {
						g2d.drawImage(Assets.bronzeBowActive, Arm.getWidth() / 2, Arm.getHeight() / 2, 64, 64, null);
					} else if(hands.getHand().getName().equals("zinc bow")) {
						g2d.drawImage(Assets.zincBowActive, Arm.getWidth() / 2, Arm.getHeight() / 2, 64, 64, null);
					} else if(hands.getHand().getName().equals("iron bow")) {
						g2d.drawImage(Assets.ironBowActive, Arm.getWidth() / 2, Arm.getHeight() / 2, 64, 64, null);
					} else if(hands.getHand().getName().equals("ravager")) {
						g2d.drawImage(Assets.ravagerActive, Arm.getWidth() / 2, Arm.getHeight() / 2, 64, 64, null);
					} else if(hands.getHand().getName().equals("megashakalaka")) {
						at2.translate(c.getWidth() / 2 - 33, c.getHeight() / 2 - 55);
						at2.rotate(-shootAngle, 45, 55);
						g2d.setTransform(at2);
						g2d.drawImage(Assets.megashakalakaActive, Arm.getWidth() / 2 - 36, Arm.getHeight() / 2, 100, 100, null);
					} else if(hands.getHand().getName().equals("pulse rifle")) {
						at2.translate(c.getWidth() / 2 - 33, c.getHeight() / 2 - 55);
						at2.rotate(-shootAngle, 45, 55);
						g2d.setTransform(at2);
						g2d.drawImage(Assets.pulseRifleActive, Arm.getWidth() / 2 - 36, Arm.getHeight() / 2, 100, 100, null);
					} else if(hands.getHand().getName().equals("flamethrower")) {
						at2.translate(c.getWidth() / 2 - 33, c.getHeight() / 2 - 55);
						at2.rotate(-shootAngle, 45, 55);
						g2d.setTransform(at2);
						g2d.drawImage(Assets.flameThrowerActive, Arm.getWidth() / 2 - 36, Arm.getHeight() / 2, 100, 100, null);
					}
				} else {
					
					at.translate(c.getWidth() / 2 - 65, c.getHeight() / 2 - 84);
					at.rotate(-shootAngle, 69, 67);
					g2d.setTransform(at);
					g2d.drawImage(CT.flip(Arm), Arm.getWidth() / 2, Arm.getHeight() / 2, 64, 64, null);
					
					if(hands.getHand().getName().equals("glock")) {
						g2d.drawImage(CT.flip(Assets.glockActive), Arm.getWidth() / 2, Arm.getHeight() / 2, 64, 64, null);
					} else if(hands.getHand().getName().equals("desert eagle")) {
						g2d.drawImage(CT.flip(Assets.desertEagleActive), Arm.getWidth() / 2, Arm.getHeight() / 2, 64, 64, null);
					} else if(hands.getHand().getName().equals("wooden bow")) {
						g2d.drawImage(CT.flip(Assets.woodenBowActive), Arm.getWidth() / 2, Arm.getHeight() / 2, 64, 64, null);
					} else if(hands.getHand().getName().equals("bronze bow")) {
						g2d.drawImage(CT.flip(Assets.bronzeBowActive), Arm.getWidth() / 2, Arm.getHeight() / 2, 64, 64, null);
					} else if(hands.getHand().getName().equals("zinc bow")) {
						g2d.drawImage(CT.flip(Assets.zincBowActive), Arm.getWidth() / 2, Arm.getHeight() / 2, 64, 64, null);
					} else if(hands.getHand().getName().equals("iron bow")) {
						g2d.drawImage(CT.flip(Assets.ironBowActive), Arm.getWidth() / 2, Arm.getHeight() / 2, 64, 64, null);
					} else if(hands.getHand().getName().equals("ravager")) {
						g2d.drawImage(CT.flip(Assets.ravagerActive), Arm.getWidth() / 2, Arm.getHeight() / 2, 64, 64, null);
					} else if(hands.getHand().getName().equals("megashakalaka")) {
						at2.translate(c.getWidth() / 2 - 33, c.getHeight() / 2 - 55);
						at2.rotate(-shootAngle, 45, 57);
						g2d.setTransform(at2);
						g2d.drawImage(CT.flip(Assets.megashakalakaActive), Arm.getWidth() / 2 - 36, Arm.getHeight() / 2, 100, 100, null);
					} else if(hands.getHand().getName().equals("pulse rifle")) {
						at2.translate(c.getWidth() / 2 - 33, c.getHeight() / 2 - 55);
						at2.rotate(-shootAngle, 45, 57);
						g2d.setTransform(at2);
						g2d.drawImage(CT.flip(Assets.pulseRifleActive), Arm.getWidth() / 2 - 36, Arm.getHeight() / 2, 100, 100, null);
					} else if(hands.getHand().getName().equals("flamethrower")) {
						at2.translate(c.getWidth() / 2 - 33, c.getHeight() / 2 - 55);
						at2.rotate(-shootAngle, 45, 57);
						g2d.setTransform(at2);
						g2d.drawImage(CT.flip(Assets.flameThrowerActive), Arm.getWidth() / 2 - 36, Arm.getHeight() / 2, 100, 100, null);
					}
				}
				
			} else if (isAttacking && (hands.getHand() == null || hands.getHand().getType().equals("weapon") || 
					hands.getHand().getType().equals("axe") || hands.getHand().getType().equals("pickaxe") ||
					hands.getHand().getType().equals("shovel"))) {
				swptAng += ((2 * Math.PI) / (attackCooldown / 1000.0 * 60));
				// swptAng += 2*Math.PI/120.0;
				swptAng = swptAng > 2 * Math.PI ? swptAng - 2 * Math.PI : swptAng;
				// System.out.println(swptAng);

				double rotAng = swptAng > Math.PI && swptAng < 5.65 ? swptAng : 5.65;

				if (direction == 1) {
					at.translate(c.getWidth() / 2 - 38, c.getHeight() / 2 - 85);
					at.rotate(rotAng, 59, 64);
					g2d.setTransform(at);
					g2d.drawImage(Arm, Arm.getWidth() / 2, Arm.getHeight() / 2, 64, 64, null);
					
					if (hands.getHand() != null) {
						at2.translate(c.getWidth() / 2 - 101, c.getHeight() / 2 - 42);
						at2.rotate(rotAng - Math.PI / 8, 123, 20);
						g2d.setTransform(at2);
						g2d.drawImage(CT.flip(CT.rotateClockwise90(CT.rotateClockwise90(hands.getHand().getTexture()))),
								hands.getHand().getTexture().getWidth() / 2 + 7,
								hands.getHand().getTexture().getHeight() / 2 - 17, 100, 100, null);
					}

				} else {
					at.translate(c.getWidth() / 2 - 65, c.getHeight() / 2 - 84);
					at.rotate(-rotAng, 69, 64);
					g2d.setTransform(at);
					g2d.drawImage(CT.flip(Arm), Arm.getWidth() / 2, Arm.getHeight() / 2, 64, 64, null);
				
					if (hands.getHand() != null) {
						at2.translate(c.getWidth() / 2 - 35, c.getHeight() / 2 - 40);
						at2.rotate(-rotAng + Math.PI / 8, 40, 19);
						g2d.setTransform(at2);
						g2d.drawImage(CT.rotateClockwise90(CT.rotateClockwise90(hands.getHand().getTexture())),
								hands.getHand().getTexture().getWidth() / 2 - 7,
								hands.getHand().getTexture().getHeight() / 2 - 14, 100, 100, null);
					}
				}
			} else if (hands.getHand() != null) {
				
				//Facing right
				if (direction == 1) {
					Point center = new Point(c.getWidth() / 2 - 75, c.getHeight() / 2 - 65);
					at.translate(center.x, center.y);
					at.rotate(-Math.PI/9, 82, 82);
					g2d.setTransform(at);
					if(hands.getHand().getType().equals("weapon") || hands.getHand().getType().equals("ranged") || 
							hands.getHand().getType().equals("axe") || hands.getHand().getType().equals("pickaxe") ||
							hands.getHand().getType().equals("shovel"))
						g2d.drawImage(CT.rotateClockwise90(CT.flip(hands.getHand().getTexture())),
								hands.getHand().getTexture().getWidth() / 2 + 55, hands.getHand().getTexture().getHeight() / 2 + 10,
								100, 100, null);
				
				//Facing left
				} else {
					Point center = new Point(c.getWidth() / 2 - 75, c.getHeight() / 2 - 65);
					at.translate(center.x, center.y);
					at.rotate(Math.PI/9, 82, 82);
					g2d.setTransform(at);
					if(hands.getHand().getType().equals("weapon") || hands.getHand().getType().equals("ranged") || 
							hands.getHand().getType().equals("axe") || hands.getHand().getType().equals("pickaxe"))
						g2d.drawImage(
								(CT.rotateClockwise90(
										CT.rotateClockwise90(CT.rotateClockwise90(hands.getHand().getTexture())))),
								hands.getHand().getTexture().getWidth() / 2 - 45, hands.getHand().getTexture().getHeight() / 2 + 10,
								100, 100, null);
					else if(hands.getHand().getType().equals("shovel"))
						g2d.drawImage(
								(CT.rotateClockwise90(
										CT.rotateClockwise90(CT.rotateClockwise90(hands.getHand().getTexture())))),
								hands.getHand().getTexture().getWidth() / 2 - 10, hands.getHand().getTexture().getHeight() / 2 + 10,
								100, 100, null);
					
				}

			}
			
			g2d.setTransform(backup);

			if (direction == 0) {
				if (currentLeftAnimation != null) {
					g.drawImage(currentLeftAnimation.getCurrentFrame(), (int) (x - c.getGameCamera().getxOffset()) - 35,
							(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);

					if (equipment.currentChestLeftAnimation != null
							&& !c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
									.getEntityManager().getPlayer().getInventory().isActive())

						if (isMoving)
							g.drawImage(equipment.currentChestLeftAnimation.getCurrentFrame(),
									(int) (x - c.getGameCamera().getxOffset()) - 35,
									(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
						else {
							if (equipment.currentChestLeftAnimation.equals(equipment.bronzeCL))
								g.drawImage(Assets.bronzeChestplateStanding,
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
							else if (equipment.currentChestLeftAnimation.equals(equipment.zincCL))
								g.drawImage(Assets.zincChestplateStanding,
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
							else if (equipment.currentChestLeftAnimation.equals(equipment.ironCL))
								g.drawImage(Assets.ironChestplateStanding,
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
							else if (equipment.currentChestLeftAnimation.equals(equipment.titaniumCL))
								g.drawImage(Assets.titaniumChestplateStanding,
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
							else if (equipment.currentChestLeftAnimation.equals(equipment.tungstenCL))
								g.drawImage(Assets.tungstenChestplateStanding,
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
						}

					if (equipment.currentHelmetLeftAnimation != null
							&& !c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
									.getEntityManager().getPlayer().getInventory().isActive())

						if (isMoving)
							g.drawImage(equipment.currentHelmetLeftAnimation.getCurrentFrame(),
									(int) (x - c.getGameCamera().getxOffset()) - 35,
									(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
						else {
							if (equipment.currentHelmetLeftAnimation.equals(equipment.bronzeHL))
								g.drawImage(Assets.bronzeHelmetStanding,
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
							else if (equipment.currentHelmetLeftAnimation.equals(equipment.zincHL))
								g.drawImage(Assets.zincHelmetStanding, (int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
							else if (equipment.currentHelmetLeftAnimation.equals(equipment.ironHL))
								g.drawImage(Assets.ironHelmetStanding, (int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
							else if (equipment.currentHelmetLeftAnimation.equals(equipment.titaniumHL))
								g.drawImage(Assets.titaniumHelmetStanding,
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
							else if (equipment.currentHelmetLeftAnimation.equals(equipment.tungstenHL))
								g.drawImage(Assets.tungstenHelmetStanding,
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
						}
					if (equipment.currentLegLeftAnimation != null
							&& !c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
									.getEntityManager().getPlayer().getInventory().isActive())

						if (isMoving)
							g.drawImage(equipment.currentLegLeftAnimation.getCurrentFrame(),
									(int) (x - c.getGameCamera().getxOffset()) - 35,
									(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);

						else {

							if (equipment.currentLegLeftAnimation.equals(equipment.bronzeLL))
								g.drawImage(Assets.bronzeLeggingStanding,
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
							else if (equipment.currentLegLeftAnimation.equals(equipment.zincLL))
								g.drawImage(Assets.zincLeggingStanding, (int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
							else if (equipment.currentLegLeftAnimation.equals(equipment.ironLL))
								g.drawImage(Assets.ironLeggingStanding, (int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
							else if (equipment.currentLegLeftAnimation.equals(equipment.titaniumLL))
								g.drawImage(Assets.titaniumLeggingStanding,
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
							else if (equipment.currentLegLeftAnimation.equals(equipment.tungstenLL))
								g.drawImage(Assets.tungstenLeggingStanding,
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
						}
					if (equipment.currentBootsLeftAnimation != null
							&& !c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
									.getEntityManager().getPlayer().getInventory().isActive())
						if (isMoving)
							g.drawImage(equipment.currentBootsLeftAnimation.getCurrentFrame(),
									(int) (x - c.getGameCamera().getxOffset()) - 35,
									(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
						else {
							if (equipment.currentBootsLeftAnimation.equals(equipment.bronzeBL))
								g.drawImage(Assets.bronzeBootsStanding, (int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
							else if (equipment.currentBootsLeftAnimation.equals(equipment.zincBL))
								g.drawImage(Assets.zincBootsStanding, (int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
							else if (equipment.currentBootsLeftAnimation.equals(equipment.ironBL))
								g.drawImage(Assets.ironBootsStanding, (int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
							else if (equipment.currentBootsLeftAnimation.equals(equipment.titaniumBL))
								g.drawImage(Assets.titaniumBootsStanding,
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
							else if (equipment.currentBootsLeftAnimation.equals(equipment.tungstenBL))
								g.drawImage(Assets.tungstenBootsStanding,
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
						}

					if (equipment.currentGauntletsLeftAnimation != null
							&& !c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
									.getEntityManager().getPlayer().getInventory().isActive())
						if (isMoving)
							g.drawImage(equipment.currentGauntletsLeftAnimation.getCurrentFrame(),
									(int) (x - c.getGameCamera().getxOffset()) - 35,
									(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
						else {
							if (equipment.currentGauntletsLeftAnimation.equals(equipment.bronzeGL))
								g.drawImage(Assets.bronzeGauntletsStanding,
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
							else if (equipment.currentGauntletsLeftAnimation.equals(equipment.zincGL))
								g.drawImage(Assets.zincGauntletsStanding,
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
							else if (equipment.currentGauntletsLeftAnimation.equals(equipment.ironGL))
								g.drawImage(Assets.ironGauntletsStanding,
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
							else if (equipment.currentGauntletsLeftAnimation.equals(equipment.titaniumGL))
								g.drawImage(Assets.titaniumGauntletsStanding,
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
							else if (equipment.currentGauntletsLeftAnimation.equals(equipment.tungstenGL))
								g.drawImage(Assets.tungstenGauntletsStanding,
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
						}
				}
			} else {
				if (currentRightAnimation != null) {
					g.drawImage(currentRightAnimation.getCurrentFrame(),
							(int) (x - c.getGameCamera().getxOffset()) - 35,
							(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);

					if (equipment.currentChestRightAnimation != null
							&& !c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
									.getEntityManager().getPlayer().getInventory().isActive())

						if (isMoving)
							g.drawImage(equipment.currentChestRightAnimation.getCurrentFrame(),
									(int) (x - c.getGameCamera().getxOffset()) - 35,
									(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
						else {
							if (equipment.currentChestRightAnimation.equals(equipment.bronzeCR))
								g.drawImage(CT.flip(Assets.bronzeChestplateStanding),
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
							else if (equipment.currentChestRightAnimation.equals(equipment.zincCR))
								g.drawImage(CT.flip(Assets.zincChestplateStanding),
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
							else if (equipment.currentChestRightAnimation.equals(equipment.ironCR))
								g.drawImage(CT.flip(Assets.ironChestplateStanding),
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
							else if (equipment.currentChestRightAnimation.equals(equipment.titaniumCR))
								g.drawImage(CT.flip(Assets.titaniumChestplateStanding),
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
							else if (equipment.currentChestRightAnimation.equals(equipment.tungstenCR))
								g.drawImage(CT.flip(Assets.tungstenChestplateStanding),
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
						}

					if (equipment.currentHelmetRightAnimation != null
							&& !c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
									.getEntityManager().getPlayer().getInventory().isActive())

						if (isMoving)
							g.drawImage(equipment.currentHelmetRightAnimation.getCurrentFrame(),
									(int) (x - c.getGameCamera().getxOffset()) - 35,
									(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
						else {
							if (equipment.currentHelmetRightAnimation.equals(equipment.bronzeHR))
								g.drawImage(CT.flip(Assets.bronzeHelmetStanding),
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
							else if (equipment.currentHelmetRightAnimation.equals(equipment.zincHR))
								g.drawImage(CT.flip(Assets.zincHelmetStanding),
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
							else if (equipment.currentHelmetRightAnimation.equals(equipment.ironHR))
								g.drawImage(CT.flip(Assets.ironHelmetStanding),
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
							else if (equipment.currentHelmetRightAnimation.equals(equipment.titaniumHR))
								g.drawImage(CT.flip(Assets.titaniumHelmetStanding),
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
							else if (equipment.currentHelmetRightAnimation.equals(equipment.tungstenHR))
								g.drawImage(CT.flip(Assets.tungstenHelmetStanding),
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
						}

					if (equipment.currentLegRightAnimation != null
							&& !c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
									.getEntityManager().getPlayer().getInventory().isActive())

						if (isMoving)
							g.drawImage(equipment.currentLegRightAnimation.getCurrentFrame(),
									(int) (x - c.getGameCamera().getxOffset()) - 35,
									(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);

						else

						{
							if (equipment.currentLegRightAnimation.equals(equipment.bronzeLR))
								g.drawImage(CT.flip(Assets.bronzeLeggingStanding),
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
							else if (equipment.currentLegRightAnimation.equals(equipment.zincLR))
								g.drawImage(CT.flip(Assets.zincLeggingStanding),
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
							else if (equipment.currentLegRightAnimation.equals(equipment.ironLR))
								g.drawImage(CT.flip(Assets.ironLeggingStanding),
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
							else if (equipment.currentLegRightAnimation.equals(equipment.titaniumLR))
								g.drawImage(CT.flip(Assets.titaniumLeggingStanding),
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
							else if (equipment.currentLegRightAnimation.equals(equipment.tungstenLR))
								g.drawImage(CT.flip(Assets.tungstenLeggingStanding),
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
						}

					if (equipment.currentBootsRightAnimation != null
							&& !c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
									.getEntityManager().getPlayer().getInventory().isActive())
						if (isMoving)
							g.drawImage(equipment.currentBootsRightAnimation.getCurrentFrame(),
									(int) (x - c.getGameCamera().getxOffset()) - 35,
									(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
						else {
							if (equipment.currentBootsRightAnimation.equals(equipment.bronzeBR))
								g.drawImage(CT.flip(Assets.bronzeBootsStanding),
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
							else if (equipment.currentBootsRightAnimation.equals(equipment.zincBR))
								g.drawImage(CT.flip(Assets.zincBootsStanding),
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
							else if (equipment.currentBootsRightAnimation.equals(equipment.ironBR))
								g.drawImage(CT.flip(Assets.ironBootsStanding),
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
							else if (equipment.currentBootsRightAnimation.equals(equipment.titaniumBR))
								g.drawImage(CT.flip(Assets.titaniumBootsStanding),
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
							else if (equipment.currentBootsRightAnimation.equals(equipment.tungstenBR))
								g.drawImage(CT.flip(Assets.tungstenBootsStanding),
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
						}

					if (equipment.currentGauntletsRightAnimation != null
							&& !c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
									.getEntityManager().getPlayer().getInventory().isActive())
						if (isMoving)
							g.drawImage(equipment.currentGauntletsRightAnimation.getCurrentFrame(),
									(int) (x - c.getGameCamera().getxOffset()) - 35,
									(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
						else {
							if (equipment.currentGauntletsRightAnimation.equals(equipment.bronzeGR))
								g.drawImage(CT.flip(Assets.bronzeGauntletsStanding),
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
							else if (equipment.currentGauntletsRightAnimation.equals(equipment.zincGR))
								g.drawImage(CT.flip(Assets.zincGauntletsStanding),
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
							else if (equipment.currentGauntletsRightAnimation.equals(equipment.ironGR))
								g.drawImage(CT.flip(Assets.ironGauntletsStanding),
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
							else if (equipment.currentGauntletsRightAnimation.equals(equipment.titaniumGR))
								g.drawImage(CT.flip(Assets.titaniumGauntletsStanding),
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
							else if (equipment.currentGauntletsRightAnimation.equals(equipment.tungstenGR))
								g.drawImage(CT.flip(Assets.tungstenGauntletsStanding),
										(int) (x - c.getGameCamera().getxOffset()) - 35,
										(int) (y - c.getGameCamera().getyOffset()) - 60, width, height, null);
						}
				}
			}

			//Show selected tile when holding a shovel
			if(hands.getHand() != null && hands.getHand().getType().equals("shovel") && !inMenu()) {
				//Shows mouse tile targeted if using mouse for shovel
//				int buildX = (int) ((c.getMouseManager().mouseBound().x -
//						(c.getGameCamera().getxOffset() % Tile.TILEWIDTH)) -
//						c.getMouseManager().mouseBound().x % Tile.TILEWIDTH);
//				int buildY = (int) ((c.getMouseManager().mouseBound().y - 
//						(c.getGameCamera().getyOffset() % Tile.TILEHEIGHT)) - 
//						c.getMouseManager().mouseBound().y % Tile.TILEHEIGHT);
				//Shows tile targeted if using tile that player is standing on
				int buildX = (int) ((getBounds().x + 32 -
						(c.getGameCamera().getxOffset() % Tile.TILEWIDTH)) -
						(getBounds().x + 32) % Tile.TILEWIDTH);
				int buildY = (int) ((getBounds().y + 40 - 
						(c.getGameCamera().getyOffset() % Tile.TILEHEIGHT)) - 
						(getBounds().y + 40) % Tile.TILEHEIGHT);
				Rectangle rect = new Rectangle(buildX, buildY, Tile.TILEWIDTH, Tile.TILEHEIGHT);
				g.setColor(Color.WHITE);
				g2d.draw(rect);
			}
			
		}
		//g.setColor(Color.red);
		//g.drawRect((int) this.getBounds().getX(), (int) this.getBounds().getY(), (int) this.getBounds().getWidth(),
		//		(int) this.getBounds().getHeight());

	}

	public Rectangle entityBound() {

		return new Rectangle((int) (x - c.getGameCamera().getxOffset() - entityBoundSize / 2) + bounds.width / 2,
				(int) (y - c.getGameCamera().getyOffset() - entityBoundSize / 2) + bounds.height / 2, entityBoundSize,
				entityBoundSize);

	}

	public Rectangle renderBound() {

		return new Rectangle((int) (x - c.getGameCamera().getxOffset() - renderBoundSize / 2) + bounds.width / 2,
				(int) (y - c.getGameCamera().getyOffset() - renderBoundSize / 2) + bounds.height / 2, renderBoundSize,
				renderBoundSize);

	}

	public Rectangle bulletBound() {

		return new Rectangle((int) (x - c.getGameCamera().getxOffset() - 1400 / 2) + bounds.width / 2,
				(int) (y - c.getGameCamera().getyOffset() - 1400 / 2) + bounds.height / 2, 1400, 1400);

	}

	public Rectangle flameBound() {

		return new Rectangle((int) (x - c.getGameCamera().getxOffset() - 300 / 2) + bounds.width / 2,
				(int) (y - c.getGameCamera().getyOffset() - 300 / 2) + bounds.height / 2, 300, 300);

	}

	public void postRender(Graphics g) {
		inventory.render(g);
	}

	public int getXLoc() {
		return (int) x - (int) c.getGameCamera().getxOffset() - bounds.width / 2;
	}

	public int getYLoc() {
		return (int) y - (int) c.getGameCamera().getyOffset() - bounds.height / 2;
	}

	@Override
	public void Die() {
		String worldData = String.format("worldData/%s", c.getMenuState().getWorldSelectState().getSelectedWorldName());
		File worldDataFilepath = new File(worldData);
		//Player Die() method is also called from effect manager when bleeding causing error if world is deleted
		if(worldDataFilepath.exists()) { 
			c.getGameState().getWorldGenerator().worldSaver.saveWorld();
	        c.getGameState().DeathScreen();
		}
	}

	@Override
	public String toString() {
		return "health: " + health + "\nresistance: " + resistance + "\nspeed: " + speed + "\ndamage: " + damage
				+ "\nintelligence: " + intelligence + "\nintimidation: " + intimidation;
	}
	
    private boolean inMenu() {
    	if(inventory.isActive() || handCraft.isActive() || isChestActive() || isMetallicOvenActive()
    			|| isWorkbenchActive() || isSmithingTableActive() || isDisintegratorActive()
    			|| isAnalyzerActive() || isPurifierActive() || isAutoCookerActive() || isAutoCookerV2Active() 
    			|| isResearchTableActive())
    		return true;
    	else
    		return false;
    }
	
	// getters and setters
	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	public ControlCenter getControlCenter() {
		return c;
	}

	public void setControlCenter(ControlCenter c) {
		Player.c = c;
	}

	public double getCurrentSpeed() {
		return currentSpeed;
	}

	public void setCurrentSpeed(double currentSpeed) {
		this.currentSpeed = currentSpeed;
	}

	public PlayerHands getHands() {
		return hands;
	}

	public void setHands(PlayerHands hands) {
		this.hands = hands;
	}

	public double getRunSpeed() {
		return runSpeed;
	}

	public void setRunSpeed(double runSpeed) {
		this.runSpeed = runSpeed;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public HandCraft getHandCraft() {
		return handCraft;
	}

	public void setHandCraft(HandCraft handCraft) {
		this.handCraft = handCraft;
	}

	public boolean isChestActive() {
		return chestActive;
	}

	public void setChestActive(boolean chestActive) {
		this.chestActive = chestActive;
	}

	public Chest getChest() {
		return chest;
	}

	public void setChest(Chest chest2) {
		this.chest = chest2;
	}

	public boolean isMetallicOvenActive() {
		return metallicOvenActive;
	}

	public void setMetallicOvenActive(boolean metallicOvenActive) {
		this.metallicOvenActive = metallicOvenActive;
	}

	public MetallicOven getMetallicOven() {
		return metallicOven;
	}

	public void setMetallicOven(MetallicOven metallicOven) {
		this.metallicOven = metallicOven;
	}

	public boolean isWorkbenchActive() {
		return workbenchActive;
	}

	public void setWorkbenchActive(boolean workbenchActive) {
		this.workbenchActive = workbenchActive;
	}

	public Workbench getWorkbench() {
		return workbench;
	}

	public void setWorkbench(Workbench workbench) {
		this.workbench = workbench;
	}

	public boolean isSmithingTableActive() {
		return smithingTableActive;
	}

	public void setSmithingTableActive(boolean smithingTableActive) {
		this.smithingTableActive = smithingTableActive;
	}

	public SmithingTable getSmithingTable() {
		return smithingTable;
	}

	public void setSmithingTable(SmithingTable smithingTable) {
		this.smithingTable = smithingTable;
	}

	public boolean isDisintegratorActive() {
		return disintegratorActive;
	}

	public void setDisintegratorActive(boolean disintegratorActive) {
		this.disintegratorActive = disintegratorActive;
	}

	public Disintegrator getDisintegrator() {
		return disintegrator;
	}

	public void setDisintegrator(Disintegrator disintegrator) {
		this.disintegrator = disintegrator;
	}

	/**
	 * @return the analyzerActive
	 */
	public boolean isAnalyzerActive() {
		return analyzerActive;
	}

	/**
	 * @param analyzerActive the analyzerActive to set
	 */
	public void setAnalyzerActive(boolean analyzerActive) {
		this.analyzerActive = analyzerActive;
	}

	/**
	 * @return the analyzer
	 */
	public Analyzer getAnalyzer() {
		return analyzer;
	}

	/**
	 * @param analyzer the analyzer to set
	 */
	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}

	public boolean isPurifierActive() {
		return purifierActive;
	}

	public void setPurifierActive(boolean purifierActive) {
		this.purifierActive = purifierActive;
	}

	public Purifier getPurifier() {
		return purifier;
	}

	public void setPurifier(Purifier purifier) {
		this.purifier = purifier;
	}

	public boolean isAutoCookerActive() {
		return autoCookerActive;
	}

	public void setAutoCookerActive(boolean autoCookerActive) {
		this.autoCookerActive = autoCookerActive;
	}

	public AutoCooker getAutoCooker() {
		return autoCooker;
	}

	public void setAutoCooker(AutoCooker autoCooker) {
		this.autoCooker = autoCooker;
	}

	public boolean isAutoCookerV2Active() {
		return autoCookerV2Active;
	}

	public void setAutoCookerV2Active(boolean autoCookerV2Active) {
		this.autoCookerV2Active = autoCookerV2Active;
	}

	public AutoCookerV2Craft getAutoCookerV2() {
		return autoCookerV2;
	}

	public void setAutoCookerV2(AutoCookerV2Craft autoCookerV2) {
		this.autoCookerV2 = autoCookerV2;
	}
	
	public boolean isResearchTableActive() {
		return researchTableActive;
	}

	public void setResearchTableActive(boolean researchTableActive) {
		this.researchTableActive = researchTableActive;
	}
    
	public ResearchTable getResearchTable() {
		return researchTable;
	}

	public void setResearchTable(ResearchTable researchTable) {
		this.researchTable = researchTable;
	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub

	}

	public int getAnimationSpeed() {
		return animationSpeed;
	}

	public void setAnimationSpeed(int animationSpeed) {
		this.animationSpeed = animationSpeed;
	}

	public Animation getCurrentLeftAnimation() {
		return currentLeftAnimation;
	}

	public void setCurrentLeftAnimation(Animation currentLeftAnimation) {
		this.currentLeftAnimation = currentLeftAnimation;
	}

	public Animation getCurrentRightAnimation() {
		return currentRightAnimation;
	}

	public void setCurrentRightAnimation(Animation currentRightAnimation) {
		this.currentRightAnimation = currentRightAnimation;
	}

	public long getKnockCooldown() {
		return knockCooldown;
	}

	public void setKnockCooldown(long knockCooldown) {
		this.knockCooldown = knockCooldown;
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
	
	public boolean isGoatAggro() {
		return goatAggro;
	}

	public void setGoatAggro(boolean goatAggro) {
		this.goatAggro = goatAggro;
	}

	public long getGoatAggroTimer() {
		return goatAggroTimer;
	}

	public void setGoatAggroTimer(long goatAggroTimer) {
		this.goatAggroTimer = goatAggroTimer;
	}

	public long getLastGoatAggroTimer() {
		return lastGoatAggroTimer;
	}

	public void setLastGoatAggroTimer(long lastGoatAggroTimer) {
		this.lastGoatAggroTimer = lastGoatAggroTimer;
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

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

}
