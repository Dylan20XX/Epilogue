package world;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

import states.WorldCreationState;
import states.WorldSelectionState;

public class TopperWriter {

	private static PrintWriter pr;
	private static Random r = new Random();
	
	//1 = tree, 2 = rock, 3 = rock (large), 4 = cave (infected biome only), 5 = spine bush (infected biome only), 6 == living spike, 
	//7 = hive (infected biome only), 8 = trash bags, 9 = bush (natural biome), 10 = agave (semi-desert), 11 = cactus (semi-desert), 
	//12 = giant stinger cactus (semi-desert), 13 = vile embryo, 14 = ruin piece 1, 15 = ruin  piece 2, 16 = ruin piece 3, 17 = ruin piece 4,
	//18 = ruin piece 5, 19 = ruin piece 6, 20 = chest, 21 = space shuttle, 22 = sleeping sentinel
	//50 = placeholder value for tiles covered by objects
	
	public TopperWriter(int[][] tiles, int[][] terrain, int worldSize) {

		String file = String.format("topper/%s", WorldCreationState.worldName);
		
		int w = 0;
		int h = 0;
		
		int[][] topper;
		
		try {

			PrintWriter pr = new PrintWriter(file);

			w = worldSize;
			h = worldSize;
			
			topper = new int[w][h];
			
			//select a location for the hive
			boolean hiveSpawned = false;
			while(!hiveSpawned) {
				for (int y = 0; y < w; y++) {
					for (int x = 0; x < h; x++) {
						
						int rand = r.nextInt(50);
						if(rand == 1) { //try to spawn a hive
							boolean validLocation = true;
							
							for (int v = y-25; v < y+25; v++) {
								if(v < 0 || v >= h || !validLocation) {
									validLocation = false;
									break;
								}
								
								for (int c = x-25; c < x+25; c++) {
									
									if(c < 0 || c >= w) {
										validLocation = false;
										break;
									}

									//if(the area around the attempted spawn area is not in infected biome, dont select the location
									if(terrain[c][v] != 6) 
										validLocation = false;
									
								}
							}
							
							//make sure that theres no water in a 10x10 area around the hive
							for (int v = y-10; v < y+10; v++) {
								if(v < 0 || v >= h || !validLocation) {
									validLocation = false;
									break;
								}
								
								for (int c = x-10; c < x+10; c++) {
									
									if(c < 0 || c >= w) {
										validLocation = false;
										break;
									}

									//if(the area around the attempted spawn area is not in infected biome, dont select the location
									if(tiles[c][v] == 10) 
										validLocation = false;
									
								}
							}
							
							
							if(validLocation) {
								for (int j = y; j < y+5; j++) {
									for(int i = x; i < x+12; i++) {
										topper[i][j] = 50;
									}
								}
								topper[x][y] = 7;
								hiveSpawned = true;
								System.out.println("Hive at: x = " + x + " y = " + y);
							}
							
						}
						
						if(hiveSpawned)
							break;
						
					}
					
					if(hiveSpawned)
						break;
				}
			}
			
			//Select locations for the vile embryos
			int embryosSpawned = 0;
			while(embryosSpawned < 5) {
				for (int y = 0; y < w; y++) {
					for (int x = 0; x < h; x++) {
						
						int rand = r.nextInt(100);
						if(rand == 1) { //try to spawn an embryo
							boolean validLocation = true;
							
							for (int v = y-25; v < y+25; v++) {
								if(v < 0 || v >= h || !validLocation) { //make sure that its not too close to world edge
									validLocation = false;
									break;
								}
								
								for (int c = x-25; c < x+25; c++) { //make sure that its not too close to world edge
									
									if(c < 0 || c >= w) {
										validLocation = false;
										break;
									}

									//if(the area around the attempted spawn area is not in semi-desert biome, dont select the location
									if(terrain[c][v] != 3) 
										validLocation = false;
									
								}
							}
							
							//make sure that theres no water in a 10x10 area around the embryo
							for (int v = y-10; v < y+10; v++) {
								if(v < 0 || v >= h || !validLocation) {
									validLocation = false;
									break;
								}
								
								for (int c = x-10; c < x+10; c++) {
									
									if(c < 0 || c >= w) {
										validLocation = false;
										break;
									}

									if(tiles[c][v] == 10) 
										validLocation = false;
									
								}
							}
							
							if(validLocation) { //make sure that there's a 2x2 tile square to spawn the embryo
								if(!(tiles[x][y-1] != 10 && tiles[x+1][y-1] != 10 
										&& tiles[x+1][y] != 10 &&
										topper[x][y-1] == 0 && topper[x+1][y-1] == 0 && 
										topper[x+1][y] == 0)) {
									validLocation = false;
								}	
							}
							
							if(validLocation) {
								topper[x][y] = 13;
								topper[x][y-1] = 50;
								topper[x+1][y-1] = 50;
								topper[x+1][y] = 50;
								embryosSpawned++;
								System.out.println("Embryo at: x = " + x + " y = " + y);
							}
							
						}
						
						if(embryosSpawned >= 5)
							break;
						
					}
					
					if(embryosSpawned >= 5)
						break;
				}
			}
			
			//Select locations for the vile embryos
			int shuttlesSpawned = 0;
			while(shuttlesSpawned < 2) {
				for (int y = 0; y < w; y++) {
					for (int x = 0; x < h; x++) {
						
						int rand = r.nextInt(1000);
						if(rand == 1) { //try to spawn an embryo
							boolean validLocation = true;
							
							for (int v = y-250; v < y+250; v++) {
								if(v < 0 || v >= h || !validLocation) { //make sure that its not too close to world edge
									validLocation = false;
									break;
								}
								
								for (int c = x-250; c < x+250; c++) { //make sure that its not too close to world edge
									
									if(c < 0 || c >= w) {
										validLocation = false;
										break;
									}

									//can test for certain biomes if desired
									//if(terrain[c][v] != 3) 
									//	validLocation = false;
									
								}
							}
							
							//make sure that theres no water in a 10x10 area around the shuttle
							for (int v = y-10; v < y+10; v++) {
								if(v < 0 || v >= h || !validLocation) {
									validLocation = false;
									break;
								}
								
								for (int c = x-10; c < x+10; c++) {
									
									if(c < 0 || c >= w) {
										validLocation = false;
										break;
									}

									if(tiles[c][v] == 10) 
										validLocation = false;
									
								}
							}
							
							if(validLocation) { //make sure that there's a 5x2 tile square to spawn the shuttle
								if(!(tiles[x+1][y] != 10 && tiles[x+2][y] != 10 && tiles[x+3][y] != 10 && 
										tiles[x+4][y] != 10 && tiles[x][y+1] != 10 && tiles[x+1][y+1] != 10 &&
										tiles[x+2][y+1] != 10 && tiles[x+3][y+1] != 10 && tiles[x+4][y+1] != 10 &&
										topper[x+1][y] == 0 && topper[x+2][y] == 0 && topper[x+3][y] == 0 && 
										topper[x+4][y] == 0 && topper[x][y+1] == 0 && topper[x+1][y+1] == 0 &&
										topper[x+2][y+1] == 0 && topper[x+3][y+1] == 0 && topper[x+4][y+1] == 0)) {
									validLocation = false;
								}	
							}
							
							if(validLocation) {
								topper[x][y] = 21;
								topper[x+1][y] = 50;
								topper[x+2][y] = 50;
								topper[x+3][y] = 50;
								topper[x+4][y] = 50;
								topper[x][y+1] = 50;
								topper[x+1][y+1] = 50;
								topper[x+2][y+1] = 50;
								topper[x+3][y+1] = 50;
								topper[x+4][y+1] = 50;
								
								shuttlesSpawned++;
								System.out.println("Space Shuttle at: x = " + x + " y = " + y);
							}
							
						}
						
						if(shuttlesSpawned >= 2)
							break;
						
					}
					
					if(shuttlesSpawned >= 2)
						break;
				}
			}
			
			//Select locations for the vile embryos
			boolean awakenedSentinelSpawned = false;
			while(!awakenedSentinelSpawned) {
				for (int y = 0; y < w; y++) {
					for (int x = 0; x < h; x++) {
						
						int rand = r.nextInt(100);
						if(rand == 1) { //try to spawn awakened sentinel
							boolean validLocation = true;
							
							for (int v = y-25; v < y+25; v++) {
								if(v < 0 || v >= h || !validLocation) { //make sure that its not too close to world edge
									validLocation = false;
									break;
								}
								
								for (int c = x-25; c < x+25; c++) { //make sure that its not too close to world edge
									
									if(c < 0 || c >= w) {
										validLocation = false;
										break;
									}
									
									//spawn only in the ruins
									if(terrain[c][v] != 5)  
										validLocation = false;
									
								}
							}
							
							//make sure that theres no water in a 10x10 area around the awakened sentinel
							for (int v = y-10; v < y+10; v++) {
								if(v < 0 || v >= h || !validLocation) {
									validLocation = false;
									break;
								}
								
								for (int c = x-10; c < x+10; c++) {
									
									if(c < 0 || c >= w) {
										validLocation = false;
										break;
									}

									if(tiles[c][v] == 10) 
										validLocation = false;
									
								}
							}
							
							if(validLocation) { //make sure that there's a 4x2 tile square to spawn the embryo
								if(!(tiles[x+1][y] != 10 && tiles[x+2][y] != 10 && tiles[x+3][y] != 10 && 
										tiles[x][y+1] != 10 && tiles[x+1][y+1] != 10 &&
										tiles[x+2][y+1] != 10 && tiles[x+3][y+1] != 10 && 
										topper[x+1][y] == 0 && topper[x+2][y] == 0 && topper[x+3][y] == 0 && 
										topper[x][y+1] == 0 && topper[x+1][y+1] == 0 &&
										topper[x+2][y+1] == 0 && topper[x+3][y+1] == 0)) {
									validLocation = false;
								}	
							}
							
							if(validLocation) {
								topper[x][y] = 22;
								topper[x+1][y] = 50;
								topper[x+2][y] = 50;
								topper[x+3][y] = 50;
								topper[x][y+1] = 50;
								topper[x+1][y+1] = 50;
								topper[x+2][y+1] = 50;
								topper[x+3][y+1] = 50;
								
								awakenedSentinelSpawned = true;
								System.out.println("Awakened Sentinel at: x = " + x + " y = " + y);
							}
							
						}
						
						if(awakenedSentinelSpawned)
							break;
						
					}
					
					if(awakenedSentinelSpawned)
						break;
				}
			}
			
			//Select locations for the rest of the topper entities
			for (int y = 0; y < w; y++) {
				for (int x = 0; x < h; x++) {
					
					int spawnChance = r.nextInt(10); //1/12 chance of spawning an entity on every dirt block
					
					//infected biome entity spawns
					if ((tiles[x][y] == 0 || tiles[x][y] == 1 || tiles[x][y] == 2 || tiles[x][y] == 3) &&
							topper[x][y] == 0 && spawnChance == 0 && terrain[x][y] == 6) { 

						int rand = r.nextInt(6);
						
						if (rand == 1) { //spawn tree
							topper[x][y] = 1;
						} else if (rand == 2) { //spawn a rock
							
							rand = r.nextInt(2);
							if(rand == 0) { //small rock
								topper[x][y] = 2;
							} else { //large rock
								if(tiles[x+1][y] != 10 && tiles[x-1][y] != 10 && tiles[x][y-1] != 10 
										&& tiles[x+1][y-1] != 10 && tiles[x-1][y-1] != 10 &&
										topper[x+1][y] == 0 && topper[x-1][y] == 0 && topper[x][y-1] == 0 
										&& topper[x+1][y-1] == 0 && topper[x-1][y-1] == 0) {
									topper[x][y] = 3;
									topper[x+1][y] = 50;
									topper[x-1][y] = 50;
									topper[x][y-1] = 50;
									topper[x+1][y-1] = 50;
									topper[x-1][y-1] = 50;
								}

							}

						} else if (rand == 3 && terrain[x][y] == 6) { //cave
							rand = r.nextInt(4);
							if(rand == 0) {
								if(tiles[x+1][y] != 10 && tiles[x][y+1] != 10 
										&& tiles[x+1][y+1] != 10 &&
										topper[x+1][y] == 0 && topper[x][y+1] == 0 && 
										topper[x+1][y+1] == 0) {
									topper[x][y] = 4;
									topper[x+1][y] = 50;
									topper[x][y+1] = 50;
									topper[x+1][y+1] = 50;
								}
							}
						} else if (rand == 4 && terrain[x][y] == 6) { //spine bush
							if(tiles[x+1][y] != 10 && tiles[x][y+1] != 10 
									&& tiles[x+1][y+1] != 10 &&
									topper[x+1][y] == 0 && topper[x][y+1] == 0 && 
									topper[x+1][y+1] == 0) {
								topper[x][y] = 5;
								topper[x+1][y] = 50;
								topper[x][y+1] = 50;
								topper[x+1][y+1] = 50;
							}
						}  else if (rand == 5 && terrain[x][y] == 6) { //living spike
							topper[x][y] = 6;
						} else { //no topper entity
							topper[x][y] = 0;
						}
						
					//Spawn chances for natural biome
					} else if ((tiles[x][y] == 0 || tiles[x][y] == 1 || tiles[x][y] == 2 || tiles[x][y] == 3) &&
							topper[x][y] == 0 && spawnChance == 0 && terrain[x][y] == 1) { 
						
						int rand = r.nextInt(3);
						
						if (rand == 0) { //spawn tree - these have a taller hitbox
							if(tiles[x][y-1] != 10 && tiles[x][y-2] != 10 &&
									topper[x][y-1] == 0 && topper[x][y-2] == 0) {
								topper[x][y] = 1;
								topper[x][y-1] = 50;
								topper[x][y-2] = 50;
							}
						} else if (rand == 1) { //spawn a rock
							
							rand = r.nextInt(2);
							if(rand == 0) { //small rock
								topper[x][y] = 2;
							} else { //large rock
								if(tiles[x+1][y] != 10 && tiles[x-1][y] != 10 && tiles[x][y-1] != 10 
										&& tiles[x+1][y-1] != 10 && tiles[x-1][y-1] != 10 &&
										topper[x+1][y] == 0 && topper[x-1][y] == 0 && topper[x][y-1] == 0 
										&& topper[x+1][y-1] == 0 && topper[x-1][y-1] == 0) {
									topper[x][y] = 3;
									topper[x+1][y] = 50;
									topper[x-1][y] = 50;
									topper[x][y-1] = 50;
									topper[x+1][y-1] = 50;
									topper[x-1][y-1] = 50;
								}
							}

						} else if (rand == 2) { //bush
							if(tiles[x+1][y] != 10 && tiles[x][y+1] != 10 
									&& tiles[x+1][y+1] != 10 &&
									topper[x+1][y] == 0 && topper[x][y+1] == 0 && 
									topper[x+1][y+1] == 0) {
								topper[x][y] = 9;
								topper[x+1][y] = 50;
								topper[x][y+1] = 50;
								topper[x+1][y+1] = 50;
							}
						} else { //no topper entity
							topper[x][y] = 0;
						}
					
					//Spawn chances for forest biome
					} else if ((tiles[x][y] == 0 || tiles[x][y] == 1 || tiles[x][y] == 2 || tiles[x][y] == 3) &&
							topper[x][y] == 0 && spawnChance == 0 && terrain[x][y] == 2) { 

						int rand = r.nextInt(5);

						if (rand == 0 || rand == 1 || rand == 2 || rand == 3) { //spawn tree
							topper[x][y] = 1;
						} else if (rand == 4) { //spawn a rock

							rand = r.nextInt(2);
							if(rand == 0) { //small rock
								topper[x][y] = 2;
							} else { //large rock
								if(tiles[x+1][y] != 10 && tiles[x-1][y] != 10 && tiles[x][y-1] != 10 
										&& tiles[x+1][y-1] != 10 && tiles[x-1][y-1] != 10 &&
										topper[x+1][y] == 0 && topper[x-1][y] == 0 && topper[x][y-1] == 0 
										&& topper[x+1][y-1] == 0 && topper[x-1][y-1] == 0) {
									topper[x][y] = 3;
									topper[x+1][y] = 50;
									topper[x-1][y] = 50;
									topper[x][y-1] = 50;
									topper[x+1][y-1] = 50;
									topper[x-1][y-1] = 50;
								}
							}

						}
						
					//Spawn chances for semi desert biome
					} else if ((tiles[x][y] == 0 || tiles[x][y] == 1 || tiles[x][y] == 2 || tiles[x][y] == 3) &&
							topper[x][y] == 0 && spawnChance == 0 && terrain[x][y] == 3) { 
						
						int rand = r.nextInt(9);
						
						if (rand == 1) { //spawn a rock
							
							rand = r.nextInt(2);
							if(rand == 0) { //small rock
								topper[x][y] = 2;
							} else { //large rock
								if(tiles[x+1][y] != 10 && tiles[x-1][y] != 10 && tiles[x][y-1] != 10 
										&& tiles[x+1][y-1] != 10 && tiles[x-1][y-1] != 10 &&
										topper[x+1][y] == 0 && topper[x-1][y] == 0 && topper[x][y-1] == 0 
										&& topper[x+1][y-1] == 0 && topper[x-1][y-1] == 0) {
									topper[x][y] = 3;
									topper[x+1][y] = 50;
									topper[x-1][y] = 50;
									topper[x][y-1] = 50;
									topper[x+1][y-1] = 50;
									topper[x-1][y-1] = 50;
								}
							}

						} else if(rand == 2 || rand == 3) { //spawn agave
							
							if(tiles[x-1][y] != 10 && tiles[x+1][y] != 10 &&
									topper[x-1][y] == 0 && topper[x+1][y] == 0) {
								topper[x][y] = 10;
								topper[x-1][y] = 50;
								topper[x+1][y] = 50;
							}
							
						} else if(rand == 4 || rand == 6) {
							
							topper[x][y] = 11;
							
						} else if(rand == 5) {
							
							topper[x][y] = 12;
							
						}
					
						//Spawn chances for waste biome
					} else if ((tiles[x][y] == 0 || tiles[x][y] == 1 || tiles[x][y] == 2 || tiles[x][y] == 3) &&
							topper[x][y] == 0 && spawnChance == 0 && terrain[x][y] == 4) { 

						int rand = r.nextInt(8);

						if (rand == 1) { //spawn tree
							topper[x][y] = 1;
						} else if (rand == 2) { //spawn a rock

							rand = r.nextInt(2);
							if(rand == 0) { //small rock
								topper[x][y] = 2;
							} else { //large rock
								if(tiles[x+1][y] != 10 && tiles[x-1][y] != 10 && tiles[x][y-1] != 10 
										&& tiles[x+1][y-1] != 10 && tiles[x-1][y-1] != 10 &&
										topper[x+1][y] == 0 && topper[x-1][y] == 0 && topper[x][y-1] == 0 
										&& topper[x+1][y-1] == 0 && topper[x-1][y-1] == 0) {
									topper[x][y] = 3;
									topper[x+1][y] = 50;
									topper[x-1][y] = 50;
									topper[x][y-1] = 50;
									topper[x+1][y-1] = 50;
									topper[x-1][y-1] = 50;
								}
							}

						} else if ((rand == 3 || rand == 0 || rand == 4) && terrain[x][y] == 4) { //trash bag
							if(tiles[x+1][y] != 10 && tiles[x][y+1] != 10 
									&& tiles[x+1][y+1] != 10 &&
									topper[x+1][y] == 0 && topper[x][y+1] == 0 && 
									topper[x+1][y+1] == 0) {
								topper[x][y] = 8;
								topper[x+1][y] = 50;
								topper[x][y+1] = 50;
								topper[x+1][y+1] = 50;
							}
						} else { //no topper entity
							topper[x][y] = 0;
						}
						
					//Spawn chances for ruins biome
					} else if ((tiles[x][y] == 0 || tiles[x][y] == 1 || tiles[x][y] == 2 || tiles[x][y] == 3) &&
							topper[x][y] == 0 && spawnChance == 0 && terrain[x][y] == 5) { 
						
						int rand = r.nextInt(14);
						
						if (rand == 0) { //spawn tree
							topper[x][y] = 1;
						} else if (rand == 1 || rand == 2) { //spawn ruin piece 1
							if(tiles[x+1][y] != 10 && tiles[x+2][y] != 10 &&
									topper[x+1][y] == 0 && topper[x+2][y] == 0) {
								topper[x][y] = 14;
								topper[x+1][y] = 50;
								topper[x+2][y] = 50;
							}
						} else if (rand == 3 || rand == 4) { //spawn ruin piece 2
							if(tiles[x+1][y] != 10 && topper[x+1][y] == 0) {
								topper[x][y] = 15;
								topper[x+1][y] = 50;
							}
						} else if (rand == 5 || rand == 6) { //spawn ruin piece 3
							if(tiles[x+1][y] != 10 && topper[x+1][y] == 0) {
								topper[x][y] = 16;
								topper[x+1][y] = 50;
							}
						} else if (rand == 7 || rand == 8) { //spawn ruin piece 4
							if(tiles[x+1][y] != 10 && topper[x+1][y] == 0) {
								topper[x][y] = 17;
								topper[x+1][y] = 50;
							}
						} else if (rand == 9 || rand == 10) { //spawn ruin piece 5
							if(tiles[x+1][y] != 10 && topper[x+1][y] == 0) {
								topper[x][y] = 18;
								topper[x+1][y] = 50;
							}
						} else if (rand == 11 || rand == 12) { //spawn ruin piece 6
							if(tiles[x+1][y] != 10 && topper[x+1][y] == 0) {
								topper[x][y] = 19;
								topper[x+1][y] = 50;
							}
						} else if (rand == 13 || rand == 14) { //spawn chest
							rand = r.nextInt(10);
							if(rand == 0)
								topper[x][y] = 20;
						}
						
					//Spawn chances for all other biomes
					} else if ((tiles[x][y] == 0 || tiles[x][y] == 1 || tiles[x][y] == 2 || tiles[x][y] == 3) &&
							topper[x][y] == 0 && spawnChance == 0) {
						
						int rand = r.nextInt(5);
						
						if (rand == 0 || (rand == 15 && terrain[x][y] == 2)) { //spawn tree (higher spawn chance in a forest biome)
							topper[x][y] = 1;
						} else if (rand == 1) { //spawn a rock
							
							rand = r.nextInt(2);
							if(rand == 0) { //small rock
								topper[x][y] = 2;
							} else { //large rock
								if(tiles[x+1][y] != 10 && tiles[x-1][y] != 10 && tiles[x][y-1] != 10 
										&& tiles[x+1][y-1] != 10 && tiles[x-1][y-1] != 10 &&
										topper[x+1][y] == 0 && topper[x-1][y] == 0 && topper[x][y-1] == 0 
										&& topper[x+1][y-1] == 0 && topper[x-1][y-1] == 0) {
									topper[x][y] = 3;
									topper[x+1][y] = 50;
									topper[x-1][y] = 50;
									topper[x][y-1] = 50;
									topper[x+1][y-1] = 50;
									topper[x-1][y-1] = 50;
								}
							}

						}  else if(rand == 4) {
							rand = r.nextInt(50);
							if(rand == 0)
								topper[x][y] = 20;
						}
						else { //no topper entity
						
							topper[x][y] = 0;
						}
					}
				}
			}
			
			//print the topper array to file
			for (int y = 0; y < w; y++) {
				for (int x = 0; x < h; x++) {
					pr.print(topper[x][y] + " ");
				}
				pr.println();
			}
			pr.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
