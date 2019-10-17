package creatures;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import alphaPackage.ControlCenter;
import entity.Entity;
import graphics.Animation;
import graphics.Assets;
import graphics.CT;
import graphics.DeathAnimation;
import inventory.Effect;
import inventory.EffectManager;
import tiles.Tile;

/*
 * abstract parent class for all creatures
 * all creatures must contain the following abstract methods and properties
 * 
 * subclass of Entity
 */
public abstract class Creatures extends Entity {

        // default properties of a creature, can choose or not choose to use the
        // properties
        public static final double DEFAULT_SPEED = 4;
        public static final int DEFAULT_CREATURE_WIDTH = (int) (64 * ControlCenter.scaleValue),
                        DEFAULT_CREATURE_HEIGHT = (int) (64 * ControlCenter.scaleValue), DEFAULT_CREATURE_DAMAGE = 100;
        protected long lastMoveTimer, moveCooldown = 100, moveTimer = moveCooldown;
        protected long lastAttackTimer, AttackCooldown = 800, AttackTimer = moveCooldown;
        protected double velX, velY;

        protected double speed;
        protected int damage, intelligence, intimidation, endurability, resistance, weight;
        protected String name;
        protected ControlCenter c;
        protected int direction = 0;

        protected boolean isMoving = false;
        protected boolean isAttacking = false;
        protected boolean move = true;
        protected int attackBoundSize = 0;
        protected boolean canMove = true;
        protected double knockX = 0, knockY = 0;
        protected boolean chasing;
        protected boolean lit;
        protected int ogHealth;

        protected static int combatXPDropped;
        
        //prevent creatures from getting stuck
        protected long lastStuckXTimer, stuckXCooldown = 800, stuckXTimer = moveCooldown; //added Sept 1
        protected long lastStuckYTimer, stuckYCooldown = 800, stuckYTimer = moveCooldown; //added Sept 1
        protected double lastX, lastY;
        protected boolean stuckX, stuckY;
        private int selectedUnstuckX, selectedUnstuckY;
        private Random r = new Random();
        
        protected Animation left, right;

        public Creatures(double x, double y, int width, int height, ControlCenter c) {
        	
            super(x, y, width, height, c);

            this.c = c;

            // initializes default velocity
            velX = 0;
            velY = 0;

            // initializes default properties if it is unchanged in the subclass
            damage = DEFAULT_CREATURE_DAMAGE;
            speed = DEFAULT_SPEED;
            name = "unnamed";
            type = "creatures";
            intimidation = Integer.MAX_VALUE;
            resistance = 0;
            weight = 0;
            ogHealth = health;

            // default combat experience given
            combatXPDropped = 1;

        }

        public void move() {

                if (velX == 0 && velY == 0)
                        isMoving = false;
                else
                        isMoving = true;

                // telling collision box where we are moving
                if (!checkEntityCollision(velX, 0)) 
                        moveX();
                if (!checkEntityCollision(0, velY)) 
                        moveY();

        }

        public void moveX() {
                if (velX > 0) { // moving right
                        int tx = (int) (x + velX + bounds.x + bounds.width) / Tile.TILEWIDTH; // getting tile x
                        if (!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT)
                                        && !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
                                x += velX;
                        } else {
                                x = tx * Tile.TILEWIDTH - bounds.x - bounds.width - 1;
                        }
                } else if (velX < 0) {
                        int tx = (int) (x + velX + bounds.x) / Tile.TILEWIDTH; // getting tile x
                        if (!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT)
                                        && !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
                                x += velX;
                        } else {
                                x = tx * Tile.TILEWIDTH + Tile.TILEWIDTH - bounds.x;
                        }
                }

        }

        public void moveY() {
                if (velY < 0) { // up
                        int ty = (int) (y + velY + bounds.y) / Tile.TILEHEIGHT;
                        if (!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty)
                                        && !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)) {
                                y += velY;
                        } else {
                                y = ty * Tile.TILEHEIGHT + Tile.TILEHEIGHT - bounds.y;
                        }
                } else if (velY > 0) {
                        int ty = (int) (y + velY + bounds.y + bounds.height) / Tile.TILEHEIGHT; // getting tile x
                        if (!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty)
                                        && !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)) {
                                y += velY;
                        } else {
                                y = ty * Tile.TILEHEIGHT - bounds.y - bounds.height - 1;
                        }
                }
        }

        protected boolean collisionWithTile(int x, int y) {
                return c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getTile(x, y).isSolid();
        }

        protected BufferedImage getCurrentAnimation(Animation left, Animation right, BufferedImage leftIdle,
                        BufferedImage rightIdle) {

                if (velX < 0) {
                        direction = 0;
                        return left.getCurrentFrame();
                } else if (velX > 0) {
                        direction = 1;
                        return right.getCurrentFrame();
                } else {
                        if (direction == 0)
                                return leftIdle;
                        else
                                return rightIdle;
                }
        }

        @Override
        public void hurt(int amt) {

                if(resistance < amt) {
                        health -= (amt - resistance);
                        if(this.equals(Player.getPlayerData())) {
                                if(CT.random(1, 25) == 1) {
                                        EffectManager.addEffect(new Effect("anger", CT.random(10000, 30000)));
                                }
                                if(CT.random(1, 15) == 1) {
                                        EffectManager.addEffect(new Effect("bleeding", CT.random(3000, 12000)));
                                }
                                if(CT.random(1, 20) == 1) {
                                        EffectManager.addEffect(new Effect("wounded", CT.random(10000, 45000)));
                                }
                        }
                }

                if (health <= 0) {
                        active = false; // remove an entity from entity list by setting the active into false
                        Die();
                        c.getGameState().getWorldGenerator()
                                        .addDeathAnimation(new DeathAnimation(c, getCurrentAnimation(), (int) (x+bounds.x+bounds.width/2), (int) (y+bounds.y+bounds.height/2), width, height, false));
                        c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getEntityManager()
                                        .removeCreature(this);
                        Player.getPlayerData().setCombatXP(Player.getPlayerData().getCombatXP() + combatXPDropped); // drop combat
                                                                                                                                                                                                                // xp when
                                                                                                                                                                                                                // defeated
                }
        }

        public void knockbackPlayer(Entity e) {

                AttackTimer += System.currentTimeMillis() - lastAttackTimer;
                lastAttackTimer = System.currentTimeMillis();

                if (AttackTimer < AttackCooldown)
                        return;

                AttackTimer = 0;
                if (Player.getPlayerData().canMove && e.getType().equals("creatures")) {

                        Creatures cre = (Creatures)e;
                        Player.getPlayerData().hurt(cre.damage);
                        double Amt = cre.knockValue - (double) Player.getPlayerData().getInventory().getInventoryWeight() / 6;

                        if(!(Player.getPlayerData().getEquipment().helmet == null && Player.getPlayerData().getEquipment().chest == null
                                        && Player.getPlayerData().getEquipment().leg == null && Player.getPlayerData().getEquipment().boot == null
                                        && Player.getPlayerData().getEquipment().gauntlet == null))
                                damageArmor();
                        
                        if (Amt > 0) {
        
                                double sX = e.getBounds().getX() + e.getBounds().getWidth() / 2;
                                double sY = e.getBounds().getY() + e.getBounds().getHeight() / 2;
                                double eX = Player.getPlayerData().getBounds().getX() + Player.getPlayerData().getBounds().getWidth() / 2;
                                double eY = Player.getPlayerData().getBounds().getY() + Player.getPlayerData().getBounds().getHeight() / 2;
                                double knockAngle = -Math.atan2(sX - eX, sY - eY);

                                Player.getPlayerData().setKnockX(Math.sin(knockAngle) * Amt);
                                Player.getPlayerData().setKnockY(Math.cos(knockAngle) * Amt);

                                Player.getPlayerData().canMove = false;
                        }

                        move = false;
                        if (left != null)
                                left.setSpeed(1000);
                        if (right != null)
                                right.setSpeed(1000);

                        new java.util.Timer().schedule(new java.util.TimerTask() {
                                @Override
                                public void run() {
                                        
                                        Player.getPlayerData().canMove = true;

                                        move = true;
                                        if (left != null)
                                                left.setSpeed(125);
                                        if (right != null)
                                                right.setSpeed(125);

                                }
                        }, 500);

                }

        }
        
        public void damageArmor() {
                
                int rand = CT.random(1, 5);
                
                if(rand == 1) {
                        if(Player.getPlayerData().getEquipment().helmet != null) {
                                Player.getPlayerData().getEquipment().helmet.currentEndurance-=1;
                                if(Player.getPlayerData().getEquipment().helmet.currentEndurance <= 0) {
                                        Player.getPlayerData().getInventory().removeItem(Player.getPlayerData().getEquipment().helmet);
                                }
                        } else {
                                damageArmor();
                        }
                } else if(rand == 2) {
                        if(Player.getPlayerData().getEquipment().chest != null) {
                                Player.getPlayerData().getEquipment().chest.currentEndurance-=1;
                                if(Player.getPlayerData().getEquipment().chest.currentEndurance <= 0) {
                                        Player.getPlayerData().getInventory().removeItem(Player.getPlayerData().getEquipment().chest);
                                }
                        } else {
                                damageArmor();
                        }
                } else if(rand == 3) {
                        if(Player.getPlayerData().getEquipment().leg != null) {
                                Player.getPlayerData().getEquipment().leg.currentEndurance-=1;
                                if(Player.getPlayerData().getEquipment().leg.currentEndurance <= 0) {
                                        Player.getPlayerData().getInventory().removeItem(Player.getPlayerData().getEquipment().leg);
                                }
                        } else {
                                damageArmor();
                        }
                } else if(rand == 4) {
                        if(Player.getPlayerData().getEquipment().boot != null) {
                                Player.getPlayerData().getEquipment().boot.currentEndurance-=1;
                                if(Player.getPlayerData().getEquipment().boot.currentEndurance <= 0) {
                                        Player.getPlayerData().getInventory().removeItem(Player.getPlayerData().getEquipment().boot);
                                }
                        } else {
                                damageArmor();
                        }
                } else if(rand == 5) {
                        if(Player.getPlayerData().getEquipment().gauntlet != null) {
                                Player.getPlayerData().getEquipment().gauntlet.currentEndurance-=1;
                                if(Player.getPlayerData().getEquipment().gauntlet.currentEndurance <= 0) {
                                        Player.getPlayerData().getInventory().removeItem(Player.getPlayerData().getEquipment().gauntlet);
                                }
                        } else {
                                damageArmor();
                        }
                }
                
        }

        public void knockbackTarget(Creatures target, Creatures from, boolean conditionAngle, double incomingAngle) {

                target.hurt(from.getDamage());
                
                if (target.canMove) { 
                        
                        double Amt = 0;
                        if((double) target.getWeight() < from.getDamage())
                                Amt = (from.getDamage() - (double) target.getWeight())/10;

                        if (Amt > 0) {

                                double sX = target.getBounds().getX() + target.getBounds().getWidth() / 2;
                                double sY = target.getBounds().getY() + target.getBounds().getHeight() / 2;
                                double eX = from.getBounds().getX() + from.getBounds().getWidth() / 2;
                                double eY = from.getBounds().getY() + from.getBounds().getHeight() / 2;
                                double knockAngle = Math.atan2(sX - eX, sY - eY);

                                if (conditionAngle)
                                        knockAngle = incomingAngle;

                                target.setKnockX(Math.sin(knockAngle) * Amt);
                                target.setKnockY(Math.cos(knockAngle) * Amt);

                                target.canMove = false;
                        }
                }

        }

        public void chase(double factor) {

                chasing = true;

                if (canMove) {
                        if (c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getEntityManager().getPlayer()
                                        .getBounds().intersects(attackBound())) {

                                double sX = x + bounds.x + bounds.width / 2;
                                double sY = y + bounds.y + bounds.height / 2;

                                double eX = Player.getPlayerData().getX() + Player.getPlayerData().bounds.x
                                                + Player.getPlayerData().bounds.width / 2;
                                double eY = Player.getPlayerData().getY() + Player.getPlayerData().bounds.y
                                                + Player.getPlayerData().bounds.height / 2;

                                double angle = Math.atan2(sX - eX, sY - eY);
                                
                                if(!stuckY)
                                        velX = -(speed*factor) * Math.sin(angle);
                                if(!stuckX)
                                        velY = -(speed*factor) * Math.cos(angle);

                                if (velX > 0)
                                        direction = 1;
                                else if (velX < 0)
                                        direction = 2;
                                
                                if(x == lastX && Math.abs(velX)> Math.abs(velY) && Math.abs(velY) < speed / 4 ) { //creature is trying to move left/right but can't
                                        //System.out.println("stuck x");
                                        
                                        stuckXTimer += System.currentTimeMillis() - lastStuckXTimer;
                                        lastStuckXTimer = System.currentTimeMillis();

                                        if (stuckXTimer < stuckXCooldown)
                                                return;
                                        
                                        if(stuckX == false)
                                                selectedUnstuckX = r.nextInt(2);
                                        
                                        stuckX = true;
                                        if(selectedUnstuckX == 0)
                                                velY = -speed;
                                        else if(selectedUnstuckX == 1)
                                                velY = speed;
                                } 
                                if(x != lastX) {
                                        stuckXTimer = 0;
                                        stuckX = false;
                                }
                                
                                if(y == lastY && Math.abs(velY) > Math.abs(velX) && Math.abs(velX) < speed / 4) { //creature is trying to move up/down but cant
                                        //System.out.println("stuck y");
                                        
                                        stuckYTimer += System.currentTimeMillis() - lastStuckYTimer;
                                        lastStuckYTimer = System.currentTimeMillis();

                                        if (stuckYTimer < stuckYCooldown)
                                                return;
                                        
                                        if(stuckY == false)
                                                selectedUnstuckY = r.nextInt(2);
                                        
                                        stuckY = true;
                                        
                                        if(selectedUnstuckY == 0)
                                                velX = -speed;
                                        else if(selectedUnstuckY == 1)
                                                velX = speed;

                                }
                                if(y != lastY) {
                                        stuckYTimer = 0;
                                        stuckY = false;
                                }
                                
                                lastX = x;
                                lastY = y;


                        }
                } else {
                        velX = knockX;
                        velY = knockY;

                        new java.util.Timer().schedule(new java.util.TimerTask() {
                                @Override
                                public void run() {

                                        canMove = true;
                                        velX = 0;
                                        velY = 0;
                                }
                        }, 70);
                }

        }

        public void escape(Rectangle bound, boolean stop) {

                chasing = false;
                
                if (canMove) {
                        if(stop) {
                                if (c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getEntityManager().getPlayer()
                                                .getBounds().intersects(bound)) {
        
                                        double sX = x + bounds.x + bounds.width / 2;
                                        double sY = y + bounds.y + bounds.height / 2;
        
                                        double eX = Player.getPlayerData().getX() + bounds.x + bounds.width / 2;
                                        double eY = Player.getPlayerData().getY() + bounds.y + bounds.height / 2;
        
                                        double angle = Math.atan2(sX - eX, sY - eY);
        
                                        velX = speed * 2 * Math.sin(angle);
                                        velY = speed * 2 * Math.cos(angle);
        
                                        if (velX > 0)
                                                direction = 1;
                                        else if (velX < 0)
                                                direction = 2;
        
                                }
                        } else {
                                double sX = x + bounds.x + bounds.width / 2;
                                double sY = y + bounds.y + bounds.height / 2;

                                double eX = Player.getPlayerData().getX() + bounds.x + bounds.width / 2;
                                double eY = Player.getPlayerData().getY() + bounds.y + bounds.height / 2;

                                double angle = Math.atan2(sX - eX, sY - eY);

                                velX = speed * 2 * Math.sin(angle);
                                velY = speed * 2 * Math.cos(angle);

                                if (velX > 0)
                                        direction = 1;
                                else if (velX < 0)
                                        direction = 2;
                        }
                        
                } else {
                        
                        velX = knockX;
                        velY = knockY;

                        new java.util.Timer().schedule(new java.util.TimerTask() {
                                @Override
                                public void run() {

                                        canMove = true;
                                        velX = 0;
                                        velY = 0;
                                }
                        }, 70);
                }

        }

        public void natural() {

                chasing = false;

                if (canMove) {
                        moveCooldown = (int) (Math.random() * 100) * 1000;
                        moveTimer += System.currentTimeMillis() - lastMoveTimer;
                        lastMoveTimer = System.currentTimeMillis();

                        if (moveTimer >= moveCooldown) {

                                int randDirection = (int) (Math.random() * 5);

                                if (randDirection == 1) {
                                        direction = 1;
                                        velX = 1;
                                        velY = 0;
                                } else if (randDirection == 2) {
                                        direction = 2;
                                        velX = -1;
                                        velY = 0;
                                } else if (randDirection == 3) {
                                        direction = 1;
                                        velX = 0;
                                        velY = 1;
                                } else if (randDirection == 4) {
                                        direction = 2;
                                        velX = 0;
                                        velY = -1;
                                } else {
                                        direction = 0;
                                        velX = 0;
                                        velY = 0;
                                }

                        }

                        moveTimer = 0;

                } else {

                        velX = knockX;
                        velY = knockY;

                        new java.util.Timer().schedule(new java.util.TimerTask() {
                                @Override
                                public void run() {

                                        canMove = true;
                                        velX = 0;
                                        velY = 0;
                                }
                        }, 70);

                }

        }

        public void caution() {

                chasing = false;

                if (canMove) {

                        if (Player.getPlayerData().getX() < x)
                                direction = 2;
                        else
                                direction = 1;

                        velX = 0;
                        velY = 0;

                } else {

                        velX = knockX;
                        velY = knockY;

                        new java.util.Timer().schedule(new java.util.TimerTask() {
                                @Override
                                public void run() {

                                        canMove = true;
                                        velX = 0;
                                        velY = 0;
                                }
                        }, 70);

                }

        }

        public Rectangle attackBound() {
                return new Rectangle(
                                (int) (x + bounds.x - c.getGameCamera().getxOffset()) - attackBoundSize / 2 + bounds.width / 2,
                                (int) (y + bounds.y - c.getGameCamera().getyOffset() - attackBoundSize / 2) + bounds.height / 2,
                                attackBoundSize, attackBoundSize);
        }

        public Rectangle cautionBound(int size) {
                return new Rectangle((int) (x + bounds.x - c.getGameCamera().getxOffset()) - size + bounds.width / 2,
                                (int) (y + bounds.y - c.getGameCamera().getyOffset() - size) + bounds.height / 2, size * 2, size * 2);
        }
        
        public BufferedImage getCurrentAnimation() {
                if (left != null && right != null) {
                        if (velX != 0 || velY != 0)

                                if (direction == 1)
                                        return right.getCurrentFrame();
                                else
                                        return left.getCurrentFrame();

                        else

                        if (direction == 1)
                                return CT.flip(left.getFrames()[0]);
                        else
                                return left.getFrames()[0];
                } else {
                        return Assets.sandShotDown;
                }

        }

        // getters and setters
        public int getHealth() {
                return health;
        }

        public void setHealth(int health) {
                this.health = health;
        }

        public double getSpeed() {
                return speed;
        }

        public void setSpeed(double speed) {
                this.speed = speed;
        }

        public double getVelX() {
                return velX;
        }

        public void setVelX(double velX) {
                this.velX = velX;
        }

        public double getVelY() {
                return velY;
        }

        public void setVelY(double velY) {
                this.velY = velY;
        }

        public int getDamage() {
                return damage;
        }

        public void setDamage(int damage) {
                this.damage = damage;
        }

        public int getIntelligence() {
                return intelligence;
        }

        public void setIntelligence(int intelligence) {
                this.intelligence = intelligence;
        }

        public int getIntimidation() {
                return intimidation;
        }

        public void setIntimidation(int intimidation) {
                this.intimidation = intimidation;
        }

        public int getEndurability() {
                return endurability;
        }

        public void setEndurability(int endurability) {
                this.endurability = endurability;
        }

        public int getResistance() {
                return resistance;
        }

        public void setResistance(int resistance) {
                this.resistance = resistance;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public int getDirection() {
                return direction;
        }

        public void setDirection(int direction) {
                this.direction = direction;
        }

        public boolean isMoving() {
                return isMoving;
        }

        public void setMoving(boolean isMoving) {
                this.isMoving = isMoving;
        }

        public boolean isAttacking() {
                return isAttacking;
        }

        public void setAttacking(boolean isAttacking) {
                this.isAttacking = isAttacking;
        }

        public boolean isCanMove() {
                return canMove;
        }

        public void setCanMove(boolean canMove) {
                this.canMove = canMove;
        }

        public double getKnockX() {
                return knockX;
        }

        public void setKnockX(double knockX) {
                this.knockX = knockX;
        }

        public double getKnockY() {
                return knockY;
        }

        public void setKnockY(double knockY) {
                this.knockY = knockY;
        }

        public int getWeight() {
                return weight;
        }

        public void setWeight(int weight) {
                this.weight = weight;
        }

        public boolean isChasing() {
                return chasing;
        }

        public void setChasing(boolean chasing) {
                this.chasing = chasing;
        }

        public boolean isLit() {
                return lit;
        }

        public void setLit(boolean lit) {
                this.lit = lit;
        }

        public int getCombatXPDropped() {
                return combatXPDropped;
        }

        public void setCombatXPDropped(int combatXPDropped) {
                this.combatXPDropped = combatXPDropped;
        }

}
