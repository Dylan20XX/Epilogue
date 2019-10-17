package staticEntity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import alphaPackage.ControlCenter;
import creatures.Player;
import graphics.Assets;
import states.EscapeState;
import states.State;
import staticEntity.StaticEntity;
import tiles.Tile;

public class SpaceShuttle extends StaticEntity{

        public static int width = StaticEntity.DEFAULT_STATICOBJECT_WIDTH * 5;
        public static int height = StaticEntity.DEFAULT_STATICOBJECT_HEIGHT * 2;
        
        private boolean isRepaired = false;
        private BufferedImage shuttleAppearence;
        private int timer = 400;

        public SpaceShuttle(double x, double y, ControlCenter c) {
                super(x, y, width, height, c);
                
                bounds.width = width - Tile.TILEWIDTH / 2;
                bounds.height = height - Tile.TILEHEIGHT / 2;
                bounds.x = width / 2 - bounds.width / 2;
                bounds.y = height / 2 - bounds.height / 2;
                
                health = 100000;
                resistance  = 10;

                shuttleAppearence = Assets.spaceShuttleBroken;
        }

        @Override
        public void tick() {    
                if(!isRepaired)
                        return;
                
                if(timer == 0)
                        State.setState(new EscapeState(c));
                
                if(timer > 400) {
                        y -= (timer%8 == 0) ? 1 : 0;
                } else if(timer > 200) {
                        y -= (timer%4 == 0) ? 1 : 0;
                } else if(timer > 100) {
                        y -= (timer%2 == 0) ? 1 : 0;
                } else if(timer > 25) {
                        int  temp = timer%4;
                        if(temp == 1)
                                y += 1;
                        if(temp == 2)
                                y += 2;
                        if(temp == 3)
                                y -= 1;
                        if(temp == 0)
                                y -= 2;                         
                } else {
                        y -= 4;
                        x -= 64;
                } 
                
                timer--;
        }

        @Override
        public void render(Graphics g) {
                g.drawImage(shuttleAppearence, (int)(x - c.getGameCamera().getxOffset()), (int)(y - c.getGameCamera().getyOffset() - height / 1.25),
                                width, (int) (height * 2.5), null);

                //drawing down the bounding box
                Graphics2D g2d = (Graphics2D)g;
                g.setColor(Color.BLUE);
                g2d.draw(getBounds());
        }

        @Override
        public void Die() {
                
        }

        @Override
        public void interact() {
                if(Player.getPlayerData().getHands()!= null && 
                		Player.getPlayerData().getHands().getHand() != null &&
                		Player.getPlayerData().getHands().getHand().getName().equals("repair kit")) {
                        isRepaired = true;
                        shuttleAppearence = Assets.spaceShuttle;
                        Player.getPlayerData().stealth = true;
                        c.getGameCamera().shake(300);
                }
        }

}
