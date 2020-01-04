package states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import alphaPackage.ControlCenter;
import audio.AudioPlayer;
import audio.BackgroundPlayer;
import audio.MusicPlayer;
import creatures.Player;
import graphics.Assets;
import graphics.CustomTextWritter;
import inventory.PlayerHands;
import utils.ClickListener;
import utils.ImageButton;
import utils.UIManager;
import utils.UIObject;
import world.WorldGenerator;

/*
 * this class ticks and renders what will be included in the game state
 */
public class GameState extends State{

        private WorldGenerator world;
        private ControlCenter c;
        private UIManager uiManager;
        private boolean isDead = false;
        private boolean Options = false;
        private boolean noAudio = false;
        private boolean noBGM = false;
        private boolean noHelp = false;
        
        //constructor initializes the world generator
        public GameState(ControlCenter c) {
                this.c = c;
                world = new WorldGenerator(String.format("worlds/%s", c.getMenuState().getWorldSelectState().getSelectedWorldName()), c); //change this to name of world selected 
                MusicPlayer.StopMusic();
        }

        @Override
        public void tick() {
                if(c.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE) && !world.isStructureCrafted() && !world.isStructureWorkbenchCrafted()) {
                        if(Options) 
                                Options = false;
                        else {
                                Options = true;
                                OptionMenu();
                        }
                }
                if(Options || isDead)
                    uiManager.tick();
                else
                        world.tick();
        }

        @Override
        public void render(Graphics g) {
                g.drawImage(Assets.nightBlack, 0, 0, c.width, c.height, null);
                world.render(g);
                if(Options) {
                        g.drawImage(Assets.gameMenu, 475, 125, 450, 525, null);
                        uiManager.render(g);
                }
                if(isDead) {
                        g.setColor(new Color(211, 211, 211, 100));
                        g.fillRect(0, 0, c.width, c.height);
                        g.drawImage(Assets.gameMenu, 475, 125, 450, 525, null);
                    CustomTextWritter.drawString(g, "You Died", 550, 250, false, Color.white, Assets.font36);
                    if(c.getMenuState().getWorldSelectState().getGameMode() == 1) {
                    	CustomTextWritter.drawString(g, "World Deleted", 550, 350, false, Color.white, Assets.font36);
                    }
                    uiManager.render(g);
                }
                g.dispose();
        }
        
        public void DeathScreen(){
                isDead = true;
                uiManager = new UIManager();
                c.getMouseManager().setUIManager(uiManager);
                c.getMenuState().getWorldSelectState().readDataFile(c.getMenuState().getWorldSelectState().getSelectedWorldName());
                if(c.getMenuState().getWorldSelectState().getGameMode() == 0) { //if game mode is normal, allow the player to continue
	                uiManager.addObject(
	                                new ImageButton(550, 400, 300, 50, Assets.resume, new ClickListener() {
	
	                                        @Override
	                                        public void onClick() {
	
	                                                AudioPlayer.playAudio("audio/next.wav");
	                                                
	                                                world = new WorldGenerator(String.format("worlds/%s", c.getMenuState().getWorldSelectState().getSelectedWorldName()), c);
	                                                c.getGameState().getWorldGenerator().createPlayer();
	                                                isDead = false;
	                                                
	                                        }
	
	                                }));
	                System.out.println("Game mode = " + c.getMenuState().getWorldSelectState().getGameMode());
                } else if(c.getMenuState().getWorldSelectState().getGameMode() == 1) { //if the game mode is hardcore, DELETE THE WORLD
                	c.getMenuState().getWorldSelectState().deleteWorld();
                }
                uiManager.addObject(
                        new ImageButton(550, 500, 300, 50, Assets.exitToMenu, new ClickListener() {

                                @Override
                                public void onClick() {
                                	BackgroundPlayer.StopAudio();
                                    AudioPlayer.playAudio("audio/next.wav");
                                    exitToMenu();
                                }

                        }));
        }
        
        public void OptionMenu() {
                uiManager = new UIManager();
                c.getMouseManager().setUIManager(uiManager);
                uiManager.addObject(
                                new ImageButton(550, 250, 300, 50, Assets.resume, new ClickListener() {

                                        @Override
                                        public void onClick() {
                                        	
                                            AudioPlayer.playAudio("audio/next.wav");
                                            Options = false;
                                        }

                                }));
                uiManager.addObject(
                                new ImageButton(550, 500, 300, 50, Assets.exitToMenu, new ClickListener() {

                                        @Override
                                        public void onClick() {
                                        	BackgroundPlayer.StopAudio();
                                        	c.getGameState().getWorldGenerator().worldSaver.saveWorld();
                                            AudioPlayer.playAudio("audio/next.wav");
                                            exitToMenu();
                                        }

                                }));
                uiManager.addObject(
                                new UIObject(550, 325, 25, 25) {

                                        @Override
                                        public void onClick() {

                                                AudioPlayer.playAudio("audio/next.wav");
                                                noAudio = !noAudio;
                                                AudioPlayer.changeMute();
                                        }

                                        @Override
                                        public void tick() {
                                        }

                                        @Override
                                        public void render(Graphics g) {
                                                if(noAudio)
                                                        g.drawImage(Assets.disableButton[1], (int) x, (int) y, width, height, null);
                                                else
                                                        g.drawImage(Assets.disableButton[0], (int) x, (int) y, width, height, null);
                                                
                                                CustomTextWritter.drawString(g, "Disable SFX", 600, 350, false, Color.white, Assets.font36);
                                        }

                                });
                uiManager.addObject(
                                new UIObject(550, 375, 25, 25) {

                                        @Override
                                        public void onClick() {

                                                AudioPlayer.playAudio("audio/next.wav");
                                                noBGM = !noBGM;
                                                BackgroundPlayer.changeMute();
                                                MusicPlayer.changeMute();
                                        }

                                        @Override
                                        public void tick() {
                                        }

                                        @Override
                                        public void render(Graphics g) {
                                                if(noBGM)
                                                        g.drawImage(Assets.disableButton[1], (int) x, (int) y, width, height, null);
                                                else
                                                        g.drawImage(Assets.disableButton[0], (int) x, (int) y, width, height, null);
                                                
                                                CustomTextWritter.drawString(g, "Disable BGM", 600, 400, false, Color.white, Assets.font36);
                                        }

                                });
                uiManager.addObject(
                                new UIObject(550, 425, 25, 25) {

                                        @Override
                                        public void onClick() {

                                                AudioPlayer.playAudio("audio/next.wav");
                                                noHelp = !noHelp;
                                                PlayerHands.changeHelp();
                                        }

                                        @Override
                                        public void tick() {
                                        }

                                        @Override
                                        public void render(Graphics g) {
                                                if(noHelp)
                                                        g.drawImage(Assets.disableButton[1], (int) x, (int) y, width, height, null);
                                                else
                                                        g.drawImage(Assets.disableButton[0], (int) x, (int) y, width, height, null);
                                                
                                                CustomTextWritter.drawString(g, "Disable Help", 600, 450, false, Color.white, Assets.font36);
                                        }

                                });
        }
        
        public void exitToMenu() {
             c.getMouseManager().setUIManager(c.getMenuState().getUiManager());
             MusicPlayer.playMusic("audio/menu.wav");
             State.setState(c.getMenuState());
        }
        
        //getters and setters
        public WorldGenerator getWorldGenerator() {
                return world;
        }

        public void setWorldGenerator(WorldGenerator world) {
                this.world = world;
        }
        
}
