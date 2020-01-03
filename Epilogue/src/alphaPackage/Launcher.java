package alphaPackage;

import java.awt.Toolkit;
import java.io.File;

/*
 * Author: Alan
 * 
 * this class starts the program by waking up the brain class
 */
public class Launcher {
	
	public static void main(String[] args) {
		
		addRequiredFolders();
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		ControlCenter c = new ControlCenter("Epilogue", 1280, 800);
		c.Start();
		
	}
	
	private static void addRequiredFolders() {

		File terrain = new File(String.format("terrain"));
		if(!terrain.exists() || !terrain.isDirectory()) {
			boolean directoryCreated = terrain.mkdir();
			if(directoryCreated) 
				System.out.println("terrain folder created");
			else 
				System.out.println("terrain folder was not created");
			
		}
		
		File timedCraftingStructures = new File(String.format("timedCraftingStructures"));
		if(!timedCraftingStructures.exists() || !timedCraftingStructures.isDirectory()) 
			timedCraftingStructures.mkdir();
		
		File creatures = new File(String.format("creatures"));
		if(!creatures.exists() || !creatures.isDirectory()) 
			creatures.mkdir();
		
		File topper = new File(String.format("topper"));
		if(!topper.exists() || !topper.isDirectory()) 
			topper.mkdir();
		
		File chests = new File(String.format("chests"));
		if(!chests.exists() || !chests.isDirectory()) 
			chests.mkdir();
		
		File recipe = new File(String.format("recipe"));
		if(!recipe.exists() || !recipe.isDirectory()) 
			recipe.mkdir();
		
		File worlds = new File(String.format("worlds"));
		if(!worlds.exists() || !worlds.isDirectory()) 
			worlds.mkdir();
		
		File platform = new File(String.format("platform"));
		if(!platform.exists() || !platform.isDirectory()) 
			platform.mkdir();
		
		File worldData = new File(String.format("worldData"));
		if(!worldData.exists() || !worldData.isDirectory()) 
			worldData.mkdir();
		
	}
	
}
