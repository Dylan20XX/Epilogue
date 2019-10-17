package inventory;

import java.awt.Color;
import java.awt.Graphics;

import alphaPackage.ControlCenter;
import graphics.Assets;
import graphics.CustomTextWritter;

public class MessageBox {
	
	private ControlCenter c;
	
	private static String slot1, slot2, slot3, slot4, slot5;
	
	private static long m1lastTimer, m1Cooldown = 5000, m1Timer = 0;
	private static long m2lastTimer, m2Cooldown = 5000, m2Timer = 0;
	private static long m3lastTimer, m3Cooldown = 5000, m3Timer = 0;
	private static long m4lastTimer, m4Cooldown = 5000, m4Timer = 0;
	private static long m5lastTimer, m5Cooldown = 5000, m5Timer = 0;
	
	public MessageBox(ControlCenter c) {
		
		this.c = c;
		
	}
	
	public void tick() {
		
		m1Timer += System.currentTimeMillis() - m1lastTimer;
		m1lastTimer = System.currentTimeMillis();

		if (m1Timer > m1Cooldown) {
			slot1 = null;
			m1Timer = 0;
		}
		
		m2Timer += System.currentTimeMillis() - m2lastTimer;
		m2lastTimer = System.currentTimeMillis();

		if (m2Timer > m2Cooldown) {
			slot2 = null;
			m2Timer = 0;
		}
		
		m3Timer += System.currentTimeMillis() - m3lastTimer;
		m3lastTimer = System.currentTimeMillis();

		if (m3Timer > m3Cooldown) {
			slot3 = null;
			m3Timer = 0;
		}
		
		m4Timer += System.currentTimeMillis() - m4lastTimer;
		m4lastTimer = System.currentTimeMillis();

		if (m4Timer > m4Cooldown) {
			slot4 = null;
			m4Timer = 0;
		}
		
		m5Timer += System.currentTimeMillis() - m5lastTimer;
		m5lastTimer = System.currentTimeMillis();

		if (m5Timer > m5Cooldown) {
			slot5 = null;
			m5Timer = 0;
		}
		
	}
	
	public static void addMessage(String message) {
		
		slot5 = slot4;
		slot4 = slot3;
		slot3 = slot2;
		slot2 = slot1;
		slot1 = message;
		
		m5Timer = m4Timer;
		m4Timer = m3Timer;
		m3Timer = m2Timer;
		m2Timer = m1Timer;
		m1Timer = 0;
		
	}
	
	public void render(Graphics g) {
		
		if(slot1!= null)
			CustomTextWritter.drawString(g, slot1, c.getWidth() - 200, c.getHeight() - 75, true, Color.YELLOW, Assets.font16);
		if(slot2!= null)
			CustomTextWritter.drawString(g, slot2, c.getWidth() - 200, c.getHeight() - 100, true, Color.YELLOW, Assets.font16);
		if(slot3!= null)
			CustomTextWritter.drawString(g, slot3, c.getWidth() - 200, c.getHeight() - 125, true, Color.YELLOW, Assets.font16);
		if(slot4!= null)
			CustomTextWritter.drawString(g, slot4, c.getWidth() - 200, c.getHeight() - 150, true, Color.YELLOW, Assets.font16);
		if(slot5!= null)
			CustomTextWritter.drawString(g, slot5, c.getWidth() - 200, c.getHeight() - 175, true, Color.YELLOW, Assets.font16);
		
		
	}
	
}
