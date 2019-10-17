package world;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import states.WorldCreationState;

/*
 * this class writes to the .txt world files
 *  writes the tile data
 */
public class WorldWritter {

	private static PrintWriter pr;
	private static Random r = new Random();
	
	int[][] tiles;
	
	private static ArrayList<Point> lake1 = new ArrayList<Point>();
	private static ArrayList<Point> lake2 = new ArrayList<Point>();
	private static ArrayList<Point> lake3 = new ArrayList<Point>();
	private static ArrayList<Point> riverHorizontal1 = new ArrayList<Point>();
	private static ArrayList<Point> riverHorizontal2 = new ArrayList<Point>();
	private static ArrayList<Point> riverVertical1 = new ArrayList<Point>();
	private static ArrayList<Point> riverVertical2 = new ArrayList<Point>();

	public WorldWritter(int worldSize) {
		//String file = "worlds/overworld";
		String file = String.format("worlds/%s",WorldCreationState.worldName);
		//String file2 = "worlds/overworldTopper";

		int w = 0;
		int h = 0;
		int sx = 0; // player starting position x
		int sy = 0; // player starting position y

		try {
			PrintWriter pr = new PrintWriter(file);
			w = worldSize;
			h = worldSize;
			sx = worldSize / 2; // player starting position x
			sy = worldSize / 2; // player starting position y
			pr.println(w + " " + h); // first row of the file is the dimension of the world in tiles
			pr.println(sx + " " + sy); // second row of the file is the player spawn positions, will be implemented
										// later
			tiles  = new int[w][h];
			
			//for(int i = 0; i < worldSize; i ++)
				//Arrays.fill(tiles[i], 0);
			
			for (int i = 0; i < h; i++) {
				for (int j = 0; j < w; j++) {

					int randBlock = r.nextInt(4);
					if(randBlock == 3)
						randBlock = r.nextInt(4);
					tiles[i][j] = randBlock;
					
				}
				//pr.println();
			}
			
			setupTemplates();
			addWater(worldSize);
			
			for (int y = 0; y < h; y++) {
				for (int x = 0; x < w; x++) {
					if(tiles[x][y] == 11)
						tiles[x][y] = 10;
				}
			}
			
			//Print tiles array to a file at the end
			for (int y = 0; y < worldSize; y++) {
				for (int x = 0; x < worldSize; x++) {
					pr.print(tiles[x][y] + " ");
					
				}
				pr.println();
			}
			pr.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void addWater(int worldSize) {
		
		//num lakes spawned is based on world size
		int numLakes = (worldSize * worldSize) / (worldSize - worldSize / 5); 
		if(worldSize == 1500) {
			numLakes = 1200;
		}
		
		//choose spot for lake
		//1 lakes must be at least 50 blocks away from spawn and 50 blocks away from other lakes (mark centres of lakes)
		//2 each time a lake is spawned, have a chance of creating a river nearby 
		//(gen num from 1-4 for direction and a num from 1-5 for chance of spawning river)
		//4 at the end of the river spawn another lake if possible (not too close to spawn) (max 3 connected lakes)
		//5 repeat step 2 checking that the next lake can be spawned in a valid location
		
		for(int i = 0; i < numLakes; i++) {
			
			int centreX;
			int centreY;
			
			boolean validCoords = true;
			
			//Find valid coords for a lake to spawn
			do {
				
				validCoords = true;
				centreX = r.nextInt(worldSize);
				centreY = r.nextInt(worldSize);
				
				for(int y = centreY - 30; y < centreY + 30 && y < worldSize; y++) {
					if(y < 0)
						continue;
					for(int x = centreX - 30; x < centreX + 30 && x < worldSize; x++) {
						if(x < 0)
							continue;
						
						if(tiles[x][y] == 100 || tiles[x][y] == 11 
								|| centreX + 30 >= worldSize || centreX  - 30 < 0 || centreY + 30 >= worldSize || centreY - 30 < 0) {
							//11 is the value used to mark the centre of lakes 
							//(all 11s will be overwritten with 10 at the end)
							
							validCoords = false;
							break;
						}
						
					}
					if (validCoords == false)
						break;
					
				}
				
			} while(validCoords == false);
			
			
			//spawn a max of 3 lakes connected by rivers
			for(int c = 0; c < 3; c++) {
				
				spawnLake(centreX, centreY);
				
				//check if the last spawned lake is close to spawn
				//if it is, stop spawning connectected rivers/lakes
				for(int y = centreY - 30; y < centreY + 30 && y < worldSize; y++) {
					if(y < 0)
						continue;
					for(int x = centreX - 30; x < centreX + 30 && x < worldSize; x++) {
						if(x < 0)
							continue;
						
						if(tiles[x][y] == 100 || centreX + 30 >= worldSize || centreX  - 30 < 0 || centreY + 30 >= worldSize || centreY - 30 < 0) 
							validCoords = false;
						
					}
					if (validCoords == false)
						break;
				}
				
				if (validCoords == false || c == 2) {
					validCoords = true;
					break;
				}
				
				
				//have a chance of spawning a river
				int riverSpawnChance = r.nextInt(2);
				int riverDirection = r.nextInt(4);
				
				if(riverSpawnChance == 0) {
					spawnRiver(centreX, centreY, riverDirection);
				} else {
					break;
				}
				
				if(riverDirection == 0) { //left
					centreX -= 29;
				} else if(riverDirection == 1) { //right
					centreX += 28;
				} else if(riverDirection == 2) { //up
					centreY -= 29;
				} else if(riverDirection == 3) { //down
					centreY += 28;
				}
				
				//check if the last spawned lake is close to spawn
				//if it is, stop spawning connectected rivers/lakes
				for(int y = centreY - 30; y < centreY + 30 && y < worldSize; y++) {
					if(y < 0)
						continue;
					for(int x = centreX - 30; x < centreX + 30 && x < worldSize; x++) {
						if(x < 0)
							continue;
						
						if(tiles[x][y] == 100 || centreX + 30 >= worldSize || centreX  - 30 < 0 || centreY + 30 >= worldSize || centreY - 30 < 0) 
							validCoords = false;
						
					}
					if (validCoords == false)
						break;
				}
				
				if (validCoords == false) {
					validCoords = true;
					break;
				}
				
			}	
			

		}
		
		//Fill world border with water
		for(int y = 0; y < worldSize; y++) {
			for(int x = 0; x < worldSize; x++) {
				if(x < 10 || y < 10 || x > worldSize - 12 || y > worldSize - 12)
					tiles[x][y] = 10;
			}
		}
		
	}

	private void spawnLake(int centreX, int centreY) {
		
		//Spawn the lake
		int lakeType = r.nextInt(3);
		
		if(lakeType == 0) {
			
			for(int j = 0; j < lake1.size(); j++) {
				tiles[(int) (centreX + lake1.get(j).getX())][(int) (centreY + lake1.get(j).getY())] = 10;
			}
			
		} else if(lakeType == 1) {
			
			for(int j = 0; j < lake2.size(); j++) {
				tiles[(int) (centreX + lake2.get(j).getX())][(int) (centreY + lake2.get(j).getY())] = 10;
			}
			
		} else if(lakeType == 2) {
			
			for(int j = 0; j < lake2.size(); j++) {
				tiles[(int) (centreX + lake2.get(j).getX())][(int) (centreY + lake2.get(j).getY())] = 10;
			}
			
		}
		
		tiles[centreX][centreY] = 11;
	}
	
	private void spawnRiver(int centreX, int centreY, int riverDirection) { //takes in the centre coordinates of the last spawned lake
		
		int riverType = r.nextInt(2);
		
		int riverStartX = 0;
		int riverStartY = 0;
		
		if(riverDirection == 0) { //left
			riverStartX = centreX - 24;
			riverStartY = centreY;
		} else if(riverDirection == 1) { //right
			riverStartX = centreX + 5;
			riverStartY = centreY;
		} else if(riverDirection == 2) { //up
			riverStartX = centreX;
			riverStartY = centreY - 24;
		} else if(riverDirection == 3) { //down
			riverStartX = centreX;
			riverStartY = centreY + 5;
		}
		
		//spawn the river based on its type and direction
		if(riverType == 0) {
			
			if(riverDirection == 0 || riverDirection == 1) {
				
				for(int j = 0; j < riverHorizontal1.size(); j++) {
					tiles[(int) (riverStartX + riverHorizontal1.get(j).getX())][(int) (riverStartY + riverHorizontal1.get(j).getY())] = 10;
				}
				
			} else if(riverDirection == 2 || riverDirection == 3) {
				
				for(int j = 0; j < riverVertical1.size(); j++) {
					tiles[(int) (riverStartX + riverVertical1.get(j).getX())][(int) (riverStartY + riverVertical1.get(j).getY())] = 10;
				}
				
			}
			
		} else if(riverType == 1) {
			
			if(riverDirection == 0 || riverDirection == 1) {
				
				for(int j = 0; j < riverHorizontal2.size(); j++) {
					tiles[(int) (riverStartX + riverHorizontal2.get(j).getX())][(int) (riverStartY + riverHorizontal2.get(j).getY())] = 10;
				}
				
			} else if(riverDirection == 2 || riverDirection == 3) {
				
				for(int j = 0; j < riverVertical2.size(); j++) {
					tiles[(int) (riverStartX + riverVertical2.get(j).getX())][(int) (riverStartY + riverVertical2.get(j).getY())] = 10;
				}
				
			}
			
		}
		
	}
	
	//This method sets up the templates for the lakes and rivers
	private void setupTemplates() { 
		
		//Lake 1
		lake1.add(new Point(0,-6));
		lake1.add(new Point(1,-6));
		
		lake1.add(new Point(-8,-1));
		lake1.add(new Point(-8,0));
		lake1.add(new Point(-7,-1));
		lake1.add(new Point(-7,0));
		//lake1.add(new Point(-7,2)); //since this block is surrounded by 3 land blocks, it must be removed
		
		lake1.add(new Point(8,-3));
		lake1.add(new Point(8,-2));
		lake1.add(new Point(8,-1));
		
		for(int x = -2; x < 4; x++) {
			lake1.add(new Point(x,-5));
		}
		
		for(int x = -1; x < 5; x++) {
			lake1.add(new Point(x,6));
		}
		
		for(int y = -2; y < 3; y++) {
			lake1.add(new Point(-6,y));
		}
		for(int y = -3; y < 4; y++) {
			lake1.add(new Point(-5,y));
		}
		for(int y = -4; y < 4; y++) {
			lake1.add(new Point(6,y));
		}
		for(int y = -3; y < 2; y++) {
			lake1.add(new Point(7,y));
		}
		
		for(int y = -4; y < 6; y++) {
			for(int x = -4; x < 6; x++) {
				lake1.add(new Point(x,y));
			}
		}
		
		//Lake 2
		lake2.add(new Point(-5,-1));
		lake2.add(new Point(-5,0));
		
		lake2.add(new Point(6,-3));
		lake2.add(new Point(6,-2));
		lake2.add(new Point(6,-1));
		lake2.add(new Point(6,1));
		lake2.add(new Point(6,2));
		lake2.add(new Point(6,3));
		
		lake2.add(new Point(7,2));
		lake2.add(new Point(7,3));
		
		//lake2.add(new Point(8,2)); //since this block is surrounded by 3 land blocks, it must be removed
		
		for(int y = -2; y < 2; y++) {
			lake2.add(new Point(-4,y));
		}
		
		for(int y = -3; y < 4; y++) {
			lake2.add(new Point(-3,y));
		}
		
		for(int x = -1; x < 4; x++) {
			lake2.add(new Point(x,5));
		}
		
		for(int y = -4; y < 5; y++) {
			for(int x = -2; x < 6; x++) {
				lake2.add(new Point(x,y));
			}
		}
		
		//Lake 3
		//lake3.add(new Point(-7,-2)); //since this block is surrounded by 3 land blocks, it must be removed
		
		lake3.add(new Point(-6,-3));
		lake3.add(new Point(-6,-2));
		
		lake3.add(new Point(-5,-3));
		lake3.add(new Point(-5,-2));
		lake3.add(new Point(-5,-1));
		
		lake3.add(new Point(-4,4));
		
		lake3.add(new Point(-1,-5));
		lake3.add(new Point(0,-5));
		lake3.add(new Point(1,-5));
		
		lake3.add(new Point(5,-2));
		lake3.add(new Point(5,0));
		lake3.add(new Point(5,1));
		lake3.add(new Point(5,2));
		
		//lake3.add(new Point(6,2)); //since this block is surrounded by 3 land blocks, it must be removed
		
		for(int y = -3; y < 2; y++) {
			lake3.add(new Point(-4,y));
		}
		
		for(int x = -3; x < 2; x++) {
			lake3.add(new Point(x,-4));
		}
		
		for(int y = -2; y < 5; y++) {
			lake3.add(new Point(4,y));
		}
		
		for(int y = -3; y < 5; y++) {
			for(int x = -3; x < 4; x++) {
				lake3.add(new Point(x,y));
			}
		}
		
		//Horizontal River 1
		for(int x = 1; x < 5; x++) {
			riverHorizontal1.add(new Point(x,-2));
		}
		
		for(int x = 12; x < 16; x++) {
			riverHorizontal1.add(new Point(x,-2));
		}
		
		for(int x = 4; x < 12; x++) {
			riverHorizontal1.add(new Point(x,2));
		}
		
		for(int x = 15; x < 19; x++) {
			riverHorizontal1.add(new Point(x,2));
		}
		
		for(int x = 6; x < 10; x++) {
			riverHorizontal1.add(new Point(x,3));
		}
		
		for(int y = -1; y < 2; y++) {
			for(int x = 0; x < 26; x++) {
				riverHorizontal1.add(new Point(x,y));
			}
		}
		
		//Horizontal River 2
		for(int x = 3; x < 7; x++) {
			riverHorizontal2.add(new Point(x,-3));
		}
		
		for(int x = 12; x < 17; x++) {
			riverHorizontal2.add(new Point(x,-3));
		}
		
		for(int x = 1; x < 9; x++) {
			riverHorizontal2.add(new Point(x,-2));
		}
		
		for(int x = 11; x < 18; x++) {
			riverHorizontal2.add(new Point(x,-2));
		}
		
		for(int x = 1; x < 4; x++) {
			riverHorizontal2.add(new Point(x,2));
		}
		
		for(int x = 7; x < 13; x++) {
			riverHorizontal2.add(new Point(x,2));
		}
		
		for(int x = 16; x < 18; x++) {
			riverHorizontal2.add(new Point(x,2));
		}
		
		for(int y = -1; y < 2; y++) {
			for(int x = 0; x < 26; x++) {
				riverHorizontal2.add(new Point(x,y));
			}
		}
		
		//Vertical River 1
		riverVertical1.add(new Point(2,0));
		riverVertical1.add(new Point(2,1));
		riverVertical1.add(new Point(2,2));
		riverVertical1.add(new Point(2,14));
		riverVertical1.add(new Point(2,15));
		riverVertical1.add(new Point(2,16));
		riverVertical1.add(new Point(2,17));
		
		for(int y = 7; y < 13; y++) {
			riverVertical1.add(new Point(-3,y));
		}
		
		for(int y = 2; y < 15; y++) {
			riverVertical1.add(new Point(-2,y));
		}
		
		for(int y = 0; y < 26; y++) {
			for(int x = -1; x < 2; x++) {
				riverVertical1.add(new Point(x,y));
			}
		}
		
		//Vertical River 2
		for(int y = 0; y < 4; y++) {
			riverVertical2.add(new Point(-2,y));
		}
		
		for(int y = 8; y < 13; y++) {
			riverVertical2.add(new Point(-2,y));
		}
		
		for(int y = 3; y < 10; y++) {
			riverVertical2.add(new Point(2,y));
		}
		
		for(int y = 12; y < 17; y++) {
			riverVertical2.add(new Point(2,y));
		}
		
		for(int y = 5; y < 8; y++) {
			riverVertical2.add(new Point(3,y));
		}
		
		for(int y = 0; y < 26; y++) {
			for(int x = -1; x < 2; x++) {
				riverVertical2.add(new Point(x,y));
			}
		}
		
		
	} //end of method
	
}
