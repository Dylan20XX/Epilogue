package world;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

import creatures.Player;
import states.WorldCreationState;
import states.WorldSelectionState;

//This class is used to write the world properties (game mode and size) to a file
public class WorldDataWriter {

	private static PrintWriter pr;
	private static Random r = new Random();

	public WorldDataWriter(int gameMode, int worldSize, String charName,int charHealth, double charRunSpeed, int charEndurability, 
			int charDamageScale, int charIntimidation, int charIntelligence, int charResistance) {
		
		String file = String.format("worldData/%s",WorldCreationState.worldName);
		File filepath = new File(file);


		//Don't generate another file if the file already exists
		if(filepath.exists() && !filepath.isDirectory()) {

		}else {

			try {
				PrintWriter pr = new PrintWriter(file);

				pr.print(gameMode + " " + worldSize + " " + 0);
				pr.println();
				pr.print(charName + " " + charHealth + " " + charRunSpeed + " " + charEndurability 
						+ " " + charDamageScale + " " + charIntimidation + " " + charIntelligence 
						+ " " + charResistance + " " + charEndurability + " " + charEndurability + " " + charEndurability); 
				//last 3 variables are hunger, thirst, and energy
				pr.println();
				pr.print(0 + " " + 0); //dayNum and time
				pr.println();
				pr.println();
				
				//Save player position
				int x = worldSize / 2 * 64;
				int y = worldSize / 2 * 64;
				pr.print(x + " " + y);
				pr.println();
				pr.println();
				
				//print xp values
				pr.println(0);
				pr.println(0);
				pr.println(0);
				pr.println(0);
				
				for(int i = 0; i < 15; i++) {
					pr.println(0); //print 0s for inventory, hands, and armor items
				}
				
				pr.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}
		
		
		//No chests to start with
		String file2 = String.format("chests/%s",WorldCreationState.worldName);
		File filepath2 = new File(file2);
		if(filepath2.exists() && !filepath2.isDirectory()) {
			System.out.println("hi");
		}else {

			try {
				PrintWriter pr = new PrintWriter(file2);

				pr.println(0);
				pr.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}
		
		//No timed crafting structures to start with
		String file3 = String.format("timedCraftingStructures/%s",WorldCreationState.worldName);
		File filepath3 = new File(file3);
		if(filepath3.exists() && !filepath3.isDirectory()) {

		}else {

			try {
				PrintWriter pr = new PrintWriter(file3);

				pr.println(0);
				pr.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}

		//No creatures to start with
		String file4 = String.format("creatures/%s",WorldCreationState.worldName);
		File filepath4 = new File(file4);
		if(filepath4.exists() && !filepath4.isDirectory()) {

		}else {

			try {
				PrintWriter pr = new PrintWriter(file4);

				pr.println(0);
				pr.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}
		
		//No platforms to start with
		String file5 = String.format("platform/%s",WorldCreationState.worldName);
		File filepath5 = new File(file5);
		if(filepath5.exists() && !filepath5.isDirectory()) {

		}else {

			try {
				PrintWriter pr = new PrintWriter(file5);
				
				for(int y = 0; y < worldSize; y++) {
					for(int x = 0; x < worldSize; x++) {
						pr.print(0 + " ");
					}
					pr.println();
				}
				pr.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}
		
		//No recipes to start with
		String file6 = String.format("recipe/%s",WorldCreationState.worldName);
		File filepath6 = new File(file6);
		if(filepath6.exists() && !filepath6.isDirectory()) {

		}else {

			try {
				PrintWriter pr = new PrintWriter(file6);

				pr.println(0);
				pr.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}
		
	}

}