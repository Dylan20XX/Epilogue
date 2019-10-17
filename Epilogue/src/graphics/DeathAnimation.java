package graphics;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import alphaPackage.ControlCenter;

public class DeathAnimation {
        
        private ControlCenter c;
        
        private BufferedImage creature;
        private BufferedImage[][] pieces;
        
        private int horSlices, verSlices;
        private int rows = 0;
        private int faded = 50;
        private int scaleX, scaleY;
        
        private float alpha;
        
        private int[][] x, y;
        private int[][] velX, velY;
        private int[][] decX, decY;
        
        private boolean[][] stopped;
        
        private static int pSize = 8;
        private static int VXMAX = 15;
        private static int VXMIN = 2;
        private static int VYMAX = 20;
        private static int VYMIN = 5;
        private static int DXMAX = 5;
        private static int DXMIN = 1;
        private static int DYMAX = 5;
        private static int DYMIN = 1;
        
        public DeathAnimation(ControlCenter c, BufferedImage creature, int xPos, int yPos, int width, int height, boolean isStatic) {
                this.c = c;
                this.creature = creature;
                
                if(isStatic)
                        pSize = 12;
                
                scaleX = width / creature.getWidth();
                scaleY = height / creature.getHeight();
                
                verSlices = creature.getHeight()/pSize;
                horSlices = creature.getWidth()/pSize;
                
                pieces = new BufferedImage[verSlices][horSlices];
                x = new int[verSlices][horSlices];
                y = new int[verSlices][horSlices];
                velX = new int[verSlices][horSlices];
                velY = new int[verSlices][horSlices];
                decX = new int[verSlices][horSlices];
                decY = new int[verSlices][horSlices];
                stopped = new boolean[verSlices][horSlices];
                
                alpha = 1f;
                
                chop(xPos, yPos);
                AssignRand();
        }
        
        public void tick() {
                if(isComplete() == true) {
                        return;
                }
                
                for(int j = 0; j < rows; j++) {
                        for(int i = 0; i < horSlices; i++) {
                                if(i < horSlices/2)
                                        x[j][i] -= velX[j][i];
                                else
                                        x[j][i] += velX[j][i];
                                
                                if(velX[j][i] != 0) {   
                                        velX[j][i] -= decX[j][i];
                                        if(velX[j][i] < 0)
                                                velX[j][i] = 0;
                                }
                                
                                y[j][i] -= velY[j][i];
                                
                                if(velY[j][i] != 0) {   
                                        velY[j][i] -= decY[j][i];
                                        if(velY[j][i] < 0)
                                                velY[j][i] = 0;
                                }
                                
                                if(velX[j][i] == 0 && velY[j][i] == 0) {
                                        stopped[j][i] = true;
                                }
                        }
                }
                
                if(rows < verSlices)
                        rows++;
        }
        
        public void render(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                AlphaComposite comp = (AlphaComposite)g2d.getComposite();
                
                if(isComplete() && alpha >= 0 && alpha <= 1) {
                        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                                alpha -= 0.02f;
                        for(int j = 0; j < verSlices; j++) {
                                for(int k = 0; k < horSlices; k++) {
                                        g2d.drawImage(pieces[j][k], (int)(x[j][k]-c.getGameCamera().getxOffset()), (int)(y[j][k]-c.getGameCamera().getyOffset()), pSize*scaleX, pSize*scaleY, null);
                                        y[j][k]++;
                                }
                        }
                        faded--;
                        g2d.setComposite(comp);
                        return;
                }
                
                for(int j = 0; j < rows; j++) {
                        for(int i = 0; i < horSlices; i++) {
                                g2d.drawImage(pieces[j][i], (int)(x[j][i]-c.getGameCamera().getxOffset()), (int)(y[j][i]-c.getGameCamera().getyOffset()), pSize*scaleX, pSize*scaleY, null);
                        }
                }
        }
        
        public boolean isComplete() {
                for(int i = 0; i < verSlices; i++) {
                        for(int j = 0; j < horSlices; j++) {
                                if(stopped[i][j] == false) {
                                        return false;
                                }
                        }
                }
                return true;
        }

        public void chop(int xPos, int yPos) {
                Spritesheet img = new Spritesheet(creature);
                
                int prevX = 0;
                int prevY = 0;
                
                for(int i = 0; i < verSlices; i++) {
                        for(int j = 0; j < horSlices; j++) {
                                pieces[i][j] = img.crop(prevX, prevY, pSize, pSize);
                                x[i][j] = xPos + prevX*scaleX;
                                y[i][j] = yPos + prevY*scaleY;
                                prevX += pSize;
                        }
                        prevY += pSize;
                        prevX = 0;
                }
        }
        
        public void AssignRand() {
                for(int i = 0; i < verSlices; i++) {
                        for(int j = 0; j < horSlices; j++) {
                                velX[i][j] = CT.random(VXMIN, VXMAX);   
                                velY[i][j] = CT.random(VYMIN, VYMAX);
                                decX[i][j] = CT.random(DXMIN, velX[i][j] < DXMAX ? velX[i][j] : DXMAX);
                                decY[i][j] = CT.random(DYMIN, velY[i][j] < DYMAX ? velY[i][j] : DYMAX);
                        }
                }
        }
        
        public int isFaded() {
                return faded;
        }
}
