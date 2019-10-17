package alphaPackage;

import java.awt.Toolkit;

/*
 * Author: Alan
 * 
 * this class starts the program by waking up the brain class
 */
public class Launcher {
	
	public static void main(String[] args) {
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		ControlCenter c = new ControlCenter("Epilogue", 1280, 800);
		c.Start();
		
	}

}
