package world;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

import states.WorldCreationState;
import states.WorldSelectionState;

public class TerrainWriter {

	private static PrintWriter pr;
	private static Random r = new Random();

	public TerrainWriter(int worldSize) {
		String file = String.format("terrain/%s",WorldCreationState.worldName);

		File filepath = new File(file);


		//Don't generate another terrain file if the file already exists
		if(filepath.exists() && !filepath.isDirectory()) {

		}else {

			//String file2 = "worlds/overworldTopper";

			int w = 0;
			int h = 0;

			int numBiomes = 10;

			//biome order
			//natural, forest, semi-desert, semi-desert, waste, waste, waste, ruin, ruin, infected
			int[] bx = new int[numBiomes];
			int[] by = new int[numBiomes];
			int[] extraRadius = new int[numBiomes];
			//regular 2500x2500 size biomes
			//int[] radius = new int[] {75, 75, 125, 125, 125, 125, 125, 125, 125, 175};
			//int[] ogExtraRadius = new int[] {50, 50, 100, 100, 100, 100, 100, 100, 100, 50};
			int[] radius = new int[] {100, 100, 175, 175, 175, 175, 175, 175, 175, 200};
			int[] ogExtraRadius = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			
			if(worldSize == 1500) {
				radius = new int[] {75, 75, 125, 125, 125, 125, 125, 125, 125, 175};
			}
			
			//for testing with smaller world
			//800x800 test
			//int[] radius = new int[] {25, 25, 75, 75, 75, 75, 75, 75, 75, 125};
			//int[] ogExtraRadius = new int[] {25, 25, 50, 50, 50, 50, 50, 50, 50, 25};
			
			//500x500 test
			//int[] radius = new int[] {15, 15, 40, 40, 40, 40, 40, 40, 40, 75};
			//int[] ogExtraRadius = new int[] {15, 15, 30, 30, 30, 30, 30, 30, 30, 15};
			
			//350x350 test
			//int[] radius = new int[] {15, 15, 25, 25, 25, 25, 25, 25, 25, 50};
			//int[] ogExtraRadius = new int[] {15, 15, 30, 30, 30, 30, 30, 30, 30, 15};
			
			//250x250 test
			//int[] radius = new int[] {10, 10, 20, 20, 20, 20, 20, 20, 20, 40};
			//int[] ogExtraRadius = new int[] {15, 15, 30, 30, 30, 30, 30, 30, 30, 15};
			
			//50x50 test
			//int[] radius = new int[] {3, 3, 3, 3, 3, 3, 3, 3, 3, 3};
			//int[] ogExtraRadius = new int[] {2, 2, 2, 2, 2, 2, 2, 2, 2, 2};

			int[][] biomeGrid;

			try {
				PrintWriter pr = new PrintWriter(file);

				//w = 2500;
				//h = 2500;
				
				w = worldSize;
				h = worldSize;
				
				biomeGrid = new int[w][h];
				
				//fill the 2D grid array with 0s
				for(int i = w; i < w; i++) {
					Arrays.fill(biomeGrid[i], 0);
				}
				
				/*
				//generate extra radius amounts
				for(int i = 0; i < numBiomes; i++) {
					extraRadius[i] = r.nextInt(ogExtraRadius[i]);
				}
				*/
				
				for(int i = 0; i < numBiomes; i++) {

					boolean validCoords = true;

					do {

						validCoords = true;

						//Generate x and y coords for biome centre 
						bx[i] = r.nextInt(w);
						by[i] = r.nextInt(h);

						//check for validity w/ every other generated biome centres
						for(int j = 0; j < i; j++) {

							if(((Math.abs(bx[i]-bx[j]) >= radius[i] + radius[j] + 3) == false && 
									(Math.abs(by[i]-by[j]) >= radius[i] + radius[j] + 3) == false) ||
									bx[i] - radius[i] - 2 < 0 || bx[i] + radius[i] + 2 >= w || //make sure that its not too close to world edge
									by[i] - radius[i] - 2 < 0 || by[i] + radius[i] + 2 >= h ) {

								validCoords = false;
								break;

							}

						}

					} while(validCoords == false);

					//once valid coords are found, fill tiles of minimum radius and the staggered edges
					for(int y = by[i] - radius[i]; y < by[i] + radius[i] && y < h; y++) {
						if(y < 0)
							continue;
						if(y >= worldSize)
							break;
						
						for(int x = bx[i] - radius[i]; x < bx[i] + radius[i] && x < w; x++) {
							if(x < 0)
								continue;
							if(x >= worldSize)
								break;

							if(i == 0) { //natural biome
								biomeGrid[x][y] = 1;
							} else if(i == 1) { //forest biome
								biomeGrid[x][y] = 2;
							} else if(i == 2 || i == 3) { //semi-desert biome
								biomeGrid[x][y] = 3;
							} else if(i == 4 || i == 5 || i == 6) { //waste biome
								biomeGrid[x][y] = 4;
							} else if(i == 7 || i == 8) { //ruins biome
								biomeGrid[x][y] = 5;
							} else if(i == 9) { //infected biome
								biomeGrid[x][y] = 6;
							}

						}
					}

					//filling the staggered edges (2 spaces out from minimum radius)
					for(int y = by[i] - radius[i] - 1; y < by[i] + radius[i] + 1; y++) {
						if(y < 0)
							continue;
						if(y >= worldSize)
							break;
						
						for(int x = bx[i] - radius[i] - 1; x < bx[i] + radius[i] + 1; x++) {
							if(x < 0)
								continue;
							if(x >= worldSize)
								break;
							
							if(x <= bx[i] - radius[i] || x >= bx[i] + radius[i] ||
									y <= by[i] - radius[i] || y >= by[i] + radius[i]) {

								int rand = r.nextInt(2);

								if(rand == 1 && biomeGrid[x][y] == 0) {

									if(i == 0) { //natural biome
										biomeGrid[x][y] = 1;
									} else if(i == 1) { //forest biome
										biomeGrid[x][y] = 2;
									} else if(i == 2 || i == 3) { //semi-desert biome
										biomeGrid[x][y] = 3;
									} else if(i == 4 || i == 5 || i == 6) { //waste biome
										biomeGrid[x][y] = 4;
									} else if(i == 7 || i == 8) { //ruin biome
										biomeGrid[x][y] = 5;
									} else if(i == 9) { //infected biome
										biomeGrid[x][y] = 6;
									}

								}

							}

						}
					}
					
					/*
					//filling the staggered edges (2 spaces out from minimum radius)
					for(int y = by[i] - radius[i] - 2; y < by[i] + radius[i] + 2; y++) {
						for(int x = bx[i] - radius[i] - 2; x < bx[i] + radius[i] + 2; x++) {

							if(x <= bx[i] - radius[i] || x >= bx[i] + radius[i] ||
									y <= by[i] - radius[i] || y >= by[i] + radius[i]) {

								int rand = r.nextInt(2);

								if(rand == 1 && biomeGrid[x][y] == 0) {

									if(i == 0) { //natural biome
										biomeGrid[x][y] = 1;
									} else if(i == 1) { //forest biome
										biomeGrid[x][y] = 2;
									} else if(i == 2 || i == 3) { //semi-desert biome
										biomeGrid[x][y] = 3;
									} else if(i == 4 || i == 5 || i == 6) { //waste biome
										biomeGrid[x][y] = 4;
									} else if(i == 7 || i == 8) { //ruin biome
										biomeGrid[x][y] = 5;
									} else if(i == 9) { //infected biome
										biomeGrid[x][y] = 6;
									}

								}

							}

						}
					}*/

				}

				/*
				for(int i = 0; i < numBiomes; i++) {

					//fill in the extra radius for each biome
					for(int y = by[i] - radius[i] - extraRadius[i]; y < by[i] + radius[i] + extraRadius[i]; y++) {
						for(int x = bx[i] - radius[i] - extraRadius[i]; x < bx[i] + radius[i] + extraRadius[i]; x++) {

							if(x >= 0 && x < w && y >= 0 && y < h && biomeGrid[x][y] == 0) {

								if(i == 0) { //natural biome
									biomeGrid[x][y] = 1;
								} else if(i == 1) { //forest biome
									biomeGrid[x][y] = 2;
								} else if(i == 2 || i == 3) { //semi-desert biome
									biomeGrid[x][y] = 3;
								} else if(i == 4 || i == 5 || i == 6) { //waste biome
									biomeGrid[x][y] = 4;
								} else if(i == 7 || i == 8) { //ruin biome
									biomeGrid[x][y] = 5;
								} else if(i == 9) { //infected biome
									biomeGrid[x][y] = 6;
								}

							}

						}
					}

					//filling the staggered edges (2 spaces out from extra radius)
					for(int y = by[i] - radius[i] - extraRadius[i] - 2; y < by[i] + radius[i] + extraRadius[i] + 2; y++) {
						for(int x = bx[i] - radius[i] - extraRadius[i] - 2; x < bx[i] + radius[i] + extraRadius[i] + 2; x++) {

							if(x <= bx[i] - radius[i] - extraRadius[i] || x >= bx[i] + radius[i] + extraRadius[i] ||
									y <= by[i] - radius[i] - extraRadius[i] || y >= by[i] + radius[i] + extraRadius[i]) {

								int rand = r.nextInt(2);

								if(rand == 1 && x >= 0 && x < w && y >= 0 && y < h && biomeGrid[x][y] == 0) {

									if(i == 0) { //natural biome
										biomeGrid[x][y] = 1;
									} else if(i == 1) { //forest biome
										biomeGrid[x][y] = 2;
									} else if(i == 2 || i == 3) { //semi-desert biome
										biomeGrid[x][y] = 3;
									} else if(i == 4 || i == 5 || i == 6) { //waste biome
										biomeGrid[x][y] = 4;
									} else if(i == 7 || i == 8) { //ruin biome
										biomeGrid[x][y] = 5;
									} else if(i == 9) { //infected biome
										biomeGrid[x][y] = 6;
									}

								}

							}
						}

					}
				}
				 */

				//Write to the file using the 2D array
				
				for(int y = 0; y < h; y++) {
					for(int x = 0; x < w; x++) {
						
						pr.print(biomeGrid[x][y] + " ");
						
					}
					
					pr.println();
					
				}
				
				pr.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}

	}

}