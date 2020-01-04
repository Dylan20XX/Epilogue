package creatures;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import alphaPackage.ControlCenter;
import audio.AudioPlayer;
import audio.BackgroundPlayer;
import audio.MusicPlayer;
import graphics.Animation;
import graphics.Assets;
import graphics.CT;
import staticEntity.SentrySpike;
import world.WorldGenerator;

public class SentryBroodMother extends Creatures {

        private long lastSpawnTimer, spawnCooldown = 2000, spawnTimer = spawnCooldown;
        private long lastParasiteTimer, ParasiteCooldown = 100, ParasiteTimer = spawnCooldown;
        private Random r = new Random();
        private int selectedAction;
        private boolean canAttack = true;
        private int numSentriesSpawned = 0;

        public SentryBroodMother(double x, double y, ControlCenter c) {
                super(x, y, Creatures.DEFAULT_CREATURE_WIDTH * 7, Creatures.DEFAULT_CREATURE_HEIGHT * 7, c);

                health = 9500;
                speed = 3.3;
                resistance = 50;
                knockValue = 20;
                attackBoundSize = 8000;
                weight = 350;

                bounds.x = 30;
                bounds.y = 300;
                bounds.width = 450;
                bounds.height = 50;

                name = "sentryBroodMother";
                type = "creatures";

                left = new Animation(200, Assets.sentryBroodMother, true);
                right = new Animation(200, CT.flip(Assets.sentryBroodMother), true);

                combatXPDropped = (int)(200 * (double)Player.getPlayerData().getIntelligence()/10);
                
        		BackgroundPlayer.StopAudio();
        		MusicPlayer.playMusic("audio/sentryQueenTheme.wav");
                
        }

        @Override
        public void tick() {
        	
                if(selectedAction == 0) { //chase player
                        AI();
                        if (move)
                                move();
                        left.tick();
                        right.tick();
                } else if(selectedAction == 1) { //chase player and spawn sentry

                        AI();
                        if (move)
                                move();
                        left.tick();
                        right.tick();
                        
                        //If player is in this bound, queen will spawn sentries
                        if (c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
                                        .getEntityManager().getPlayer().getBounds().intersects(attackBound()) &&
                                        canAttack) {

                                int temp = r.nextInt(3);

                                if (temp == 1) {

                                        c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
                                        .getEntityManager().addEntity(new Sentry(x + r.nextInt(100) + 50,
                                                        y + r.nextInt(140), c));

                                } else if (temp == 2) {

                                        c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
                                        .getEntityManager().addEntity(new Sentry(x + r.nextInt(100) + 150,
                                                        y + r.nextInt(140), c));

                                } else if (temp == 0) {

                                        c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
                                        .getEntityManager().addEntity(new SentryMajor(x + r.nextInt(100) + 150,
                                                        y + r.nextInt(140), c));

                                }


                                numSentriesSpawned++;

                                if(numSentriesSpawned >= 2)
                                        canAttack = false;

                        }
                } else if(selectedAction == 2) { //spike attack
                	
                	ParasiteTimer += System.currentTimeMillis() - lastParasiteTimer;
                    lastParasiteTimer = System.currentTimeMillis();

                    if (ParasiteTimer < ParasiteCooldown)
                            return;
                    
                    ParasiteTimer = 0;
                    
                    if(WorldGenerator.numParasite > 25)
                		return;
                    
                	c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
                    .getEntityManager().addEntity(new Parasite(x + r.nextInt(450) + 50,
                                    y + r.nextInt(140) + 150, c));
                	
                	WorldGenerator.numParasite++;
                	
                	canAttack = false;
                	/*
                	double sX = x - c.getGameCamera().getxOffset() + bounds.x + bounds.width / 2;
    				double sY = y - c.getGameCamera().getxOffset() + bounds.y + bounds.height / 2;
    				
                	double eX = Player.getPlayerData().getX() + Player.getPlayerData().getBounds().x + Player.getPlayerData().getBounds().width / 2;
    				double eY = Player.getPlayerData().getY() + Player.getPlayerData().getBounds().y + Player.getPlayerData().getBounds().height / 2;

    				double desinationAngle = Math.atan2(sX - eX, sY - eY);
    				
    				new java.util.Timer().schedule(new java.util.TimerTask() {
    					@Override
    					public void run() {
    						
    						c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
                            .getEntityManager().addEntity(new SentrySpike(
                            		sX + 200 * Math.sin(desinationAngle), sY + 150 * Math.cos(desinationAngle), c));

    					}
    				}, 200);
    				
    				new java.util.Timer().schedule(new java.util.TimerTask() {
    					@Override
    					public void run() {
    						
    						c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
                            .getEntityManager().addEntity(new SentrySpike(
                            		sX + 200 * Math.sin(desinationAngle), sY + 300 * Math.cos(desinationAngle), c));

    					}
    				}, 400);
    				
    				new java.util.Timer().schedule(new java.util.TimerTask() {
    					@Override
    					public void run() {
    						
    						c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
                            .getEntityManager().addEntity(new SentrySpike(
                            		sX + 200 * Math.sin(desinationAngle), sY + 450 * Math.cos(desinationAngle), c));

    					}
    				}, 600);
    				
    				new java.util.Timer().schedule(new java.util.TimerTask() {
    					@Override
    					public void run() {
    						
    						c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
                            .getEntityManager().addEntity(new SentrySpike(
                            		sX + 200 * Math.sin(desinationAngle), sY + 600 * Math.cos(desinationAngle), c));

    					}
    				}, 800);
    				
    				new java.util.Timer().schedule(new java.util.TimerTask() {
    					@Override
    					public void run() {
    						
    						c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
                            .getEntityManager().addEntity(new SentrySpike(
                            		sX + 200 * Math.sin(desinationAngle), sY + 750 * Math.cos(desinationAngle), c));

    					}
    				}, 1000);
    				*/
/*
                        if (c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
                                        .getEntityManager().getPlayer().getBounds().intersects(attackBound()) && canAttack) {

                                c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
                                .getEntityManager().addEntity(new SentrySpike(
                                                Player.getPlayerData().getX() + r.nextInt(16) - 8 + Player.getPlayerData().getVelX() * 24, 
                                                Player.getPlayerData().getY() + r.nextInt(16) - 8 + Player.getPlayerData().getVelY() * 24, c));
                                
                                c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
                                .getEntityManager().addEntity(new SentrySpike(
                                                Player.getPlayerData().getX() - Tile.TILEWIDTH + r.nextInt(16) - 8 + Player.getPlayerData().getVelX() * 24, 
                                                Player.getPlayerData().getY() - Tile.TILEHEIGHT + r.nextInt(16) - 8 + Player.getPlayerData().getVelY() * 24, c));

                                c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
                                .getEntityManager().addEntity(new SentrySpike(
                                                Player.getPlayerData().getX() + r.nextInt(16) - 8 + Player.getPlayerData().getVelX() * 24, 
                                                Player.getPlayerData().getY() - Tile.TILEHEIGHT + r.nextInt(16) - 8 + Player.getPlayerData().getVelY() * 24, c));

                                c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
                                .getEntityManager().addEntity(new SentrySpike(
                                                Player.getPlayerData().getX() + Tile.TILEWIDTH + r.nextInt(16) - 8 + Player.getPlayerData().getVelX() * 24, 
                                                Player.getPlayerData().getY() - Tile.TILEHEIGHT + r.nextInt(16) - 8 + Player.getPlayerData().getVelY() * 24, c));

                                c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
                                .getEntityManager().addEntity(new SentrySpike(
                                                Player.getPlayerData().getX() - Tile.TILEWIDTH + r.nextInt(16) - 8 + Player.getPlayerData().getVelX() * 24, 
                                                Player.getPlayerData().getY() + r.nextInt(16) - 8 + Player.getPlayerData().getVelY() * 24, c));

                                c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
                                .getEntityManager().addEntity(new SentrySpike(
                                                Player.getPlayerData().getX() + Tile.TILEWIDTH + r.nextInt(16) - 8 + Player.getPlayerData().getVelX() * 24, 
                                                Player.getPlayerData().getY() + r.nextInt(16) - 8 + Player.getPlayerData().getVelY() * 24, c));

                                c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
                                .getEntityManager().addEntity(new SentrySpike(
                                                Player.getPlayerData().getX() - Tile.TILEWIDTH + r.nextInt(16) - 8 + Player.getPlayerData().getVelX() * 24, 
                                                Player.getPlayerData().getY() + Tile.TILEHEIGHT + r.nextInt(16) - 8 + Player.getPlayerData().getVelY() * 24, c));

                                c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
                                .getEntityManager().addEntity(new SentrySpike(
                                                Player.getPlayerData().getX() + r.nextInt(16) - 8 + Player.getPlayerData().getVelX() * 24, 
                                                Player.getPlayerData().getY() + Tile.TILEHEIGHT + r.nextInt(16) - 8 + Player.getPlayerData().getVelY() * 24, c));

                                c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator()
                                .getEntityManager().addEntity(new SentrySpike(
                                                Player.getPlayerData().getX() + Tile.TILEWIDTH + r.nextInt(16) - 8 + Player.getPlayerData().getVelX() * 24, 
                                                Player.getPlayerData().getY() + Tile.TILEHEIGHT + r.nextInt(16) - 8 + Player.getPlayerData().getVelY() * 24, c));
                                 
                                
                                canAttack = false;

                        }
                        */

                }

                spawnTimer += System.currentTimeMillis() - lastSpawnTimer;
                lastSpawnTimer = System.currentTimeMillis();

                if (spawnTimer < spawnCooldown)
                        return;

                selectedAction = r.nextInt(3);
                canAttack = true;
                spawnTimer = 0;
                
                if(!(closeBound().intersects(Player.getPlayerData().getBounds())) && attackBound().intersects(Player.getPlayerData().getBounds())) {
                        selectedAction = 0;
                        speed = 3.3;
                } else {
                        speed = 2;
                }
                        

        }

        public void AI() {

                for(int i = 0; i < c.getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().size(); i++) {
                        if(c.getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().get(i).getBounds().intersects(damageBound())) {
                                if(!c.getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().get(i)
                                                .equals(c.getGameState().getWorldGenerator().getEntityManager().getPlayer()) && 
                                                !c.getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().get(i).equals(this) &&
                                                !(c.getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().get(i) instanceof Sentry) &&
                                                !(c.getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().get(i) instanceof Parasite) &&
                                                !(c.getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().get(i) instanceof SentryMajor) &&
                                                !(c.getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().get(i) instanceof SentrySpike)) {
                                        c.getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound().get(i).hurt(10000);
                                }
                        }
                }

                if (damageBound().intersects(Player.getPlayerData().getBounds())) 
                        knockbackPlayer(this);

                chase(1);

        }

        public Rectangle closeBound() { 
                
                return new Rectangle((int) (x + bounds.x - c.getGameCamera().getxOffset() - 1000) + bounds.width / 2,
                                (int) (y  + bounds.y - c.getGameCamera().getyOffset() - 1000) + bounds.height / 2, 2000, 2000);
                
        }

        public Rectangle breakBound() {

                return new Rectangle((int)(x - c.getGameCamera().getxOffset()) - 2 + bounds.x, (int)(y - c.getGameCamera().getyOffset()) - 2 + bounds.y, 
                                bounds.width + 4, bounds.height + 4);

        }

        @Override
        public void render(Graphics g) {

                g.drawImage(getCurrentAnimation(), (int) (x - c.getGameCamera().getxOffset()),
                                (int) (y - c.getGameCamera().getyOffset()), width + 32, height + 32, null);
/*
                g.setColor(Color.blue);
                Graphics2D g2d = (Graphics2D) g;
                g2d.draw(getBounds());
                g.setColor(Color.GREEN);
                g2d.draw(closeBound());
                g2d.draw(attackBound());
*/
        }


        @Override
        public void Die() {

                c.getGameState().getWorldGenerator().broodMotherAlive = false;

                AudioPlayer.playAudio("audio/broodMotherSpawn.wav");
                MusicPlayer.StopMusic();

        }

        @Override
        public void interact() {
                // TODO Auto-generated method stub

        }

}
