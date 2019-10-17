package states;

import java.awt.Graphics;

import alphaPackage.ControlCenter;
import audio.BackgroundPlayer;
import audio.MusicPlayer;
import graphics.Assets;
import utils.ClickListener;
import utils.ImageButton;
import utils.UIManager;

public class EscapeState extends State{

        private ControlCenter c;
        private UIManager uiManager;
        private int timer = 500;
    
        public EscapeState (ControlCenter c){
                this.c = c;
                c.getGameState().getWorldGenerator().worldSaver.saveWorld();
                MusicPlayer.StopMusic();
                BackgroundPlayer.StopAudio();
        }
        
        @Override
        public void tick() {
                if(timer <= 0) {
                        c.getMouseManager().setUIManager(c.getMenuState().getUiManager());
                        MusicPlayer.playMusic("audio/menu.wav");
                        State.setState(c.getMenuState());
                }
                timer--;
                System.out.println(timer);
        }

        @Override
        public void render(Graphics g) {
                g.drawImage(Assets.nightBlack, 0, 0, c.width, c.height, null);
                g.drawImage(Assets.escaped, 350, 300, 600, 100, null);
        }

}
