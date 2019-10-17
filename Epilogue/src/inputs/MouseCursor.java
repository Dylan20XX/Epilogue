package inputs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import alphaPackage.ControlCenter;
import entity.Entity;

public class MouseCursor{

	private ControlCenter c;

	private double x, y;
	private Rectangle bounds;
	public static int width = 2;
	public static int height = 2;
	public static int mouseOffset = width / 2;

	public MouseCursor(ControlCenter c) {
		this.c = c;

		//bounding box starting and ending location
		bounds = new Rectangle(0, 0, width, height);

	}


	public void tick() {

		//x = c.getGameCamera().getxOffset() + c.getMouseManager().getMouseX() - mouseOffset;
		//y = c.getGameCamera().getyOffset() + c.getMouseManager().getMouseY() - mouseOffset;
		
		//bounds.x = (int) x;
		//bounds.y = (int) y;
		
	}

	public void render(Graphics g) {

		//drawing down the bounding box
		Graphics2D g2d = (Graphics2D)g;
		g.setColor(Color.BLUE);
		g2d.draw(bounds.getBounds());

	}
	
	public void interact() {

		for(Entity e : c.getMenuState().getWorldSelectState().getGameState().getWorldGenerator().getEntityManager().getEntitiesInBound()) {
			//if(e.getCollisionBounds(0, 0).intersects(c.getMouseManager().mouseBound())) {
			if(e.getBounds().intersects(c.getMouseManager().mouseBound())) {
				e.interact();
				break;
			}
		}
		
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)(x - c.getGameCamera().getxOffset() + bounds.x), 
				(int)(y - c.getGameCamera().getyOffset() + bounds.y), bounds.width, bounds.height);
	}

}
