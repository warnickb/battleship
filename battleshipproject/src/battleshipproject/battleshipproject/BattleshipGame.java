//package battleshipproject;

import javax.swing.*;
import java.util.*;

/**************************************************************************************
 * BattleshipGame Logic Class
 *
 * @author Justin Perticone
 * @version November 20, 2018
 **************************************************************************************/

public class BattleshipGame implements BattleshipInterface {

	/* instance variables */

	/* 1 = player, 2 = AI */
	private int currentPlayer;

	/* keeps track of player's shots */
	private int[][] grid;

	/* keeps track of AI's shots */
	private int[][] AIgrid;

	/* keeps track of player's ships */
	private int[][] shipLocs;

	/* keeps track of AI's ships */
	private int[][] AIshipLocs;

	/* grid is 8 x 8 */
	private int totalRows;
	private int totalCols;
	private int gridSize;

	/* count hits and misses */
	private int playerHits;
	private int AIhits;
	private int playerMisses;
	private int AImisses;

	/* used to differentiate the two 3 long ships */
	private boolean placeCruiser;

	/* variables used for AI "smartshooting" */
	private boolean smartShooting;
	private int smartHits;
	private int initialHitRow;
	private int initialHitCol;
	private int initialShipHit;
	private String rememberOtherHit;
	private String rememberOtherHit2;
	private String rememberOtherHit3;

	/* determines AI level */
	private String difficulty;

	// ADD THESE TO RESET
	private String[] smartShots = {"XX", "XX", "XX", "XX"};
	private int[] allShipsStatus = new int[]{2, 3, 3, 4, 5, 2, 3, 3, 4, 5};
	private String[] allShipNames = new String[]{"Destroyer", "Submarine", "Cruiser", "Battleship", "Carrier"};
	

	/***************************************************************************
	 * Default constructor for Battleship Game
	 ***************************************************************************/
	public BattleshipGame() {

		// default board is 8 x 8
		totalRows = 8;
		totalCols = 8;
		gridSize = 64;
		difficulty = "brutal";

		// move these to reset FIXME
		currentPlayer = 1;
		playerHits = 0;
		AIhits = 0;
		playerMisses = 0;
		AImisses = 0;
		smartShooting = false;
		smartHits = 0;
		initialHitRow = -2;
		initialHitCol = -2;
		initialShipHit = -2;
		placeCruiser = false;
		rememberOtherHit = "";
		rememberOtherHit2 = "";
		rememberOtherHit3 = "";

		reset();		

	}

	// FIXME INCOMPLETE
	public BattleshipGame(String difficulty) {

		totalRows = 8;
		totalCols = 8;
		gridSize = 64;
		this.difficulty = difficulty;

		reset();

	}

	//WILL NEED MORE CONSTRUCTORS FOR ADDITIONAL GAME MODES
	//CUSTOM GRID SIZE??
	//MOVEABLE SHIPS??
	//POWER-UPS??
	
	// FIXME USE ECLIPSE FOR THESE * LINES 	

	/***************************************************************************
	 * Determines if location of fire is valid
	 * If valid, a shot will be fired to location
	 * Adjusts currentPlayer
	 *
	 * @param row_pos
	 * @param col_pos
	 * @return true when location is valid, otherwise false because
	 * 	1) the location is out of bounds
	 * 	2) the location has already received fire
	 */
	public boolean shotFired(int row_pos, int col_pos) {  // THIS GETS CALLED WHEN A BUTTON ON THE GUI IS CLICKED
		
		boolean playerShipSunk = false;
		boolean AIshipSunk = false;

		// FIXME GUI SHOULD ENSURE VALID INPUT
		// current player = player
		if(currentPlayer == 1) {
			// checking to see if location has already been fired to
			if(grid[row_pos][col_pos] != 0) {
				// THIS MAY CHANGE WITH GUI INTEGRATION

				// FIXME TESTING VERSION ONLY
				System.out.println("loc " + row_pos + " " + col_pos);

				System.out.println("You have already fired to this location!");
				return false; // FIXME GUI WILL LOOP UNTIL TRUE IS RETURNED INDICATING A SUCCESSFUL SHOT
			}
			else if(row_pos > -1 && col_pos > -1 && row_pos < totalRows && col_pos < totalCols) {
				// missed shot FIXME NOTIFY OF A MISS - JOPTION
				if(AIshipLocs[row_pos][col_pos] == 0) {
					// increment player misses
					playerMisses++;
					
					// 1 on grid indicates a miss
					grid[row_pos][col_pos] = 1;

					// FIXME TESTING VERSION ONLY
					System.out.println("Player Miss: " + playerMisses);
					System.out.println("loc " + row_pos + " " + col_pos);
					System.out.println("CP : " + currentPlayer);

					// update currentPlayer
					if(currentPlayer == 2)
						currentPlayer = 1;
					else 
						currentPlayer++;
					return true;
				} 
				// hit shot
				else if(AIshipLocs[row_pos][col_pos] >= 2) {
					// increment player hits
					playerHits++;

					// FIXME TESTING VERSION ONLY
					System.out.println("Player Hit: " + playerHits);
					System.out.println("loc " + row_pos + " " + col_pos);
					System.out.println("CP : " + currentPlayer);
				
					// 2 on grid indicates a hit	
					grid[row_pos][col_pos] = 2;
			
					// checking for s sunk ship
					String shipSunk = sunkStatus(AIshipLocs[row_pos][col_pos], currentPlayer);

					// ship has been sunk
					if(shipSunk.length() > 1) 
						System.out.println("The enemies " + shipSunk + " has been sunk!!");

					// update currentPlayer
					if(currentPlayer == 2)
						currentPlayer = 1;
					else
						currentPlayer++;
					return true;
				}
				else {
					System.out.println("Something bad happened.");
				}
			}
		}
		// current player = AI
		else if(currentPlayer == 2) {
			// checking to see if location has already been fired to
			// FIXME MAY NEED SOMETHIING TO BRIDGE GAPS OVER SHIPS FOR SMARTSHOOTING
			if(AIgrid[row_pos][col_pos] != 0) {
				return false;
			}
			if(row_pos > -1 && col_pos > -1 && row_pos < totalRows && col_pos < totalCols) {
				// missed shot
				if(shipLocs[row_pos][col_pos] == 0) {
					// increment AI misses
					AImisses++;

					// 1 on AIgrid indicates miss
					AIgrid[row_pos][col_pos] = 1;

					// FIXME TESTING VERSION ONLY
					System.out.println("AI Miss : " + AImisses);
					System.out.println("CP : " + currentPlayer);

					// update currentPlayer
					if(currentPlayer == 2)
						currentPlayer = 1;
					else 
						currentPlayer++;
					return true;
				} 
				// hit shot
				else if(shipLocs[row_pos][col_pos] >= 2) {
					// increment AI hits
					AIhits++;

					// FIXME TESTING VERSION ONLY
					System.out.println("AI Hit: " + AIhits);
					System.out.println("CP : " + currentPlayer);

					// 2 on AIgrid indicates a hit
					AIgrid[row_pos][col_pos] = 2;
					
					// smartshooting is enabled after a hit
					smartShooting = true;

					// smartshooting level 1 saves the four coordinates around the initial hit
					// AI will fire at one of these at random until it hits the ship a second time
					// smartshooting level 2 learns direction of ship and saves the two 
					//  possible coordinates of the next hit
					// this continues until ship is sunk, then smartshooting is disabled unless
					//  a second ship was hit during smartshooting
					if(smartShooting) {

						// smartHits used to enhance shooting after second hit
						smartHits++;

						// help used to increment "smartShots" coordinates
						int help = 0;

						// logic after first hit
						if(smartHits == 1) {
							// saving initial information of initial hit
							initialHitRow = row_pos;
							initialHitCol = col_pos;
							initialShipHit = shipLocs[row_pos][col_pos];
							
							// prevent out of bounds coordinates
							if(col_pos > 0)
								// coordinate left of initial hit
								smartShots[0] = String.valueOf(initialHitRow) + String.valueOf(initialHitCol-1);
							if(col_pos < 7)
								// coordinate right of initial hit
								smartShots[1] = String.valueOf(initialHitRow) + String.valueOf(initialHitCol+1);
							if(row_pos > 0)
								// coordinate above the initial hit
								smartShots[2] = String.valueOf(initialHitRow-1) + String.valueOf(initialHitCol);
							if(row_pos < 7)
								// coordinate below the initial hit
								smartShots[3] = String.valueOf(initialHitRow+1) + String.valueOf(initialHitCol);
						}

						// logic after second hit
						else if(smartHits >= 2) {
							// hit within same row, on the same ship as initial - ship is horizontally placed 
							if(initialHitRow == row_pos && shipLocs[row_pos][col_pos] == initialShipHit) {
								// hit was left of the initial hit
								if(initialHitCol > col_pos) {
									help = Integer.parseInt(smartShots[0].substring(1, 2)) - 1;
									// cannot have out of bounds coordinate	
									if(help >= 0)	
										// coordinate left of this hit
										smartShots[0] = String.valueOf(initialHitRow) + String.valueOf(help);
									else
										smartShots[0] = "XX"; // reached left boarder
								}
								// hit was right of the initial hit
								else if(initialHitCol < col_pos) {
									help = Integer.parseInt(smartShots[1].substring(1, 2)) + 1;		
									//smartShots[0] = String.valueOf(initialHitRow) + String.valueOf(initialHitCol);
									if(help <= 7)
										// coordinate right of this hit
										smartShots[1] = String.valueOf(initialHitRow) + String.valueOf(help);
									else 
										smartShots[1] = "XX"; // reached right boarder
								}
								else 
									System.out.println("Should not reach here.");
								// AI has learned direction of the ship - only needs left and right coords now
								smartShots[2] = "XX";
								smartShots[3] = "XX";
							}
							// hit within same column, on the same ship as initial - ship is vertically placed
							else if(initialHitCol == col_pos && shipLocs[row_pos][col_pos] == initialShipHit) {
								// hit was above the initial hit
								if(initialHitRow > row_pos) {
									help = Integer.parseInt(smartShots[2].substring(0, 1)) - 1;	
									if(help >= 0)
										// coordinate above this hit	
										smartShots[2] = String.valueOf(help) + String.valueOf(initialHitCol);
									else
										smartShots[2] = "XX"; // reached top boarder
								}
								// hit was below the initial hit
								else if(initialHitRow < row_pos) {
									help = Integer.parseInt(smartShots[3].substring(0, 1)) + 1;		
									if(help <= 7)
										// coordinate below this hit
										smartShots[3] = String.valueOf(help) + String.valueOf(initialHitCol);
									else 
										smartShots[3] = "XX"; // reached bottom boarder
								}
								else 
									System.out.println("Should not reach here.");
								// AI has learned direction of the ship - only needs above and below coords now
								smartShots[0] = "XX";
								smartShots[1] = "XX";
							}
							else {
								// another ship was hit during smartshooting, remember coords
								// AI could hit up to three additional ships while trying to sink initial ship
								// hit a second ship during smartshooting
								if(rememberOtherHit.length() == 0)
									rememberOtherHit = String.valueOf(row_pos) + String.valueOf(col_pos);
								// hit a third ship during smartshooting
								else if(rememberOtherHit2.length() == 0)
									rememberOtherHit2 = String.valueOf(row_pos) + String.valueOf(col_pos);
								// hit a fourth ship during smartshooting
								else
									rememberOtherHit3 = String.valueOf(row_pos) + String.valueOf(col_pos);


							}
						}

					}

					// checking for a sunk ship
					String shipSunk = sunkStatus(shipLocs[row_pos][col_pos], currentPlayer);

					// ship has been sunk
					if(shipSunk.length() > 1) {
						System.out.println("Your " + shipSunk + " has been sunk!!");
						// remember coordinates of second ship hit during smartshooting 
						if(rememberOtherHit.length() == 2)
							rememberShip();
						// disable smartshooting until next hit
						else {
							smartShooting = false;
							smartHits = 0;
						}
					}
				
					// update currentPlayer
					if(currentPlayer == 2)
						currentPlayer = 1;
					else
						currentPlayer++;
					return true;
				}
				else {
					System.out.println("Something bad happened.");
				}
			}

		} else {
			System.out.println("Something bad happened.");
		}
		
		System.out.println("should not reach here");
		return false;
	}


	/**
	 *
	 *
	 *
	 *
	 */
	public void rememberShip() {
		
		// getting coordinates of other ship hit during smartshooting
		int rowCoord = Integer.parseInt(rememberOtherHit.substring(0, 1));
		int colCoord = Integer.parseInt(rememberOtherHit.substring(1, 2));
		
		// updating initial hit variables
		initialHitRow = rowCoord;
		initialHitCol = colCoord;
		initialShipHit = shipLocs[rowCoord][colCoord];
		
		// FIXME TESTING VERSION ONLY
		System.out.println("RR " + rowCoord + " CR " + colCoord);

		// prevent out of bounds coordinates
		if(colCoord > 0)
			// coordinate left of initial hit
			smartShots[0] = String.valueOf(rowCoord) + String.valueOf(colCoord-1);
		else
			smartShots[0] = "XX";
		if(colCoord < 7)
			// coordinate right of initial hit
			smartShots[1] = String.valueOf(rowCoord) + String.valueOf(colCoord+1);
		else
			smartShots[1] = "XX";
		if(rowCoord > 0)
			// coordinate above the initial hit
			smartShots[2] = String.valueOf(rowCoord-1) + String.valueOf(colCoord);
		else 
			smartShots[2] = "XX";
		if(rowCoord < 7)
			// coordinate below the initial hit
			smartShots[3] = String.valueOf(rowCoord+1) + String.valueOf(colCoord);
		else
			smartShots[3] = "XX";

		// two other ships were marked
		if(rememberOtherHit3.length() == 2) {
			rememberOtherHit = rememberOtherHit2;
			rememberOtherHit2 = rememberOtherHit3;
			rememberOtherHit3 = "";
		}

		// one other ship was marked
		else if(rememberOtherHit2.length() == 2) {
			rememberOtherHit = rememberOtherHit2;
			rememberOtherHit2 = "";
		}

		// no other ship was marked
		else
			rememberOtherHit = "";
			
	}

	/**
	 *
	 *
	 *
	 */
	public String sunkStatus(int shipHit, int player) {
	
		// determining proper index based on the ship hit and which player fired	
		int index = -1;

		// FIXME TESTING VERSION ONLY
		System.out.println("shiphit " + shipHit);
		
		// adjustment for the Destroyer and Submarine
		if(shipHit == 2 || shipHit == 3)
			index = shipHit-2;
		// adjustment for the Battleship and Carrier
		else if(shipHit == 4 || shipHit == 5)
			index = shipHit-1;
		// adjustment for the Cruiser
		else if(shipHit == 6) // 6 is second 3 long boat (Cruisser)
			index = shipHit-4;
		// adjustment fot the AI
		if(player == 2)
			index += 5;

		// FIXME TESTING VERSION ONLY 
		System.out.println("index: " + index + " player " + player);
		
		// adjusting hit ships remaining "life"
		int val = allShipsStatus[index];
		allShipsStatus[index] = val-1;

		String sunkenShip = "";	

		// looking for a sunken ship
		for(int i = 0; i < allShipsStatus.length; i++) {

			// FIXME TESTING VERSION ONLY
			System.out.println("Ship Status " + i + " " + allShipsStatus[i]);
			
			// found a sunken ship
			if(allShipsStatus[i] == 0) {
				if(player == 1)
					sunkenShip = allShipNames[index];
				else if(player == 2)
					sunkenShip = allShipNames[index-5];
				else
					System.out.println("oh no");
				// adjusting ships life to -1 to indicate sunk
				allShipsStatus[i] = -1;
			}
		}

		// FIXME TESTING VERSION ONLY
		System.out.println("sunkenShip = " + sunkenShip);

		// returning the sunken ship
		return sunkenShip;
	}

	/**
	 * Returns whether it is the player's turn or the AI's turn
	 *
	 * @return 0 = player, 1 = AI	// FIXME MAY NOT NEED
	 */
	public int currentPlayer() {
		return currentPlayer;
	}


	/** 
	 * Returns the ID of the winning player //FIXME GOING TO BE MORE THAN THIS, INCLUDE TOTAL HITS/MISSES
	 *
	 * @return 0 = player, 1 = AI, -1 = winner currently undetermined
	 */
	public void getWinner() { 
		// player won the game
		if(playerHits == 17) {
			System.out.println("Player has won!!");
		} 
		// AI won the game
		else if (AIhits == 17) {
			System.out.println("The computer has won!!");
		}
		else 
			System.out.println("what the heck happened");

		// FIXME GIVE OPTION TO START A NEW GAME OR EXIT - COULD ENHANCE TO HAVE A "n" response
		// FIXME NEED TO REDESIGN CONSTRUCTOR AND RESET BEFORE THIS WILL WORK
		String playAgain = JOptionPane.showInputDialog(null, "Would you like to play again? (y/n)");
		if(playAgain.equals("y")) 
			reset();
		else
			System.out.println("Thank you for playing!!");

	}


	/**
	 * Reset the game
	 */
	public void reset() {

		// setting up the firing grid and setting all coordinates to 0
		grid = new int[totalRows][totalCols];
		for(int row = 0; row < totalRows; row++)
			for (int col = 0; col < totalCols; col++)
				grid[row][col] = 0;

		// setting up the AI firing grid and setting all coordinates to 0
		AIgrid = new int[totalRows][totalCols];
		for(int row = 0; row < totalRows; row++)
			for(int col = 0; col < totalCols; col++)
				AIgrid[row][col] = 0;

		// setting up the ship locations grid and setting all coordinates to 0
		shipLocs = new int[totalRows][totalCols];
		for(int row = 0; row < totalRows; row++)
			for(int col = 0; col < totalCols; col++)
				shipLocs[row][col] = 0;

		// setting up the AI ship locations grid and setting all coordinates to 0
		AIshipLocs = new int[totalRows][totalCols];
		for(int row = 0; row < totalRows; row++)
			for(int col = 0; col < totalCols; col++)
				AIshipLocs[row][col] = 0;
		
		// player will now place their ships
		System.out.println("Time to place your ships!");
		//placePlayerShips();
		presentationDemo();

		// AI ships will now be placed at random
		System.out.println("The AI's ships will now be placed at random...");
		placeAIShips();

		// begin the game
		//playGame();
		AIonly();

	}

	// FIXME THIS ENTIRE METHOD NEEDS TO BE MOVED TO GUI AND MODIFIED
	public void playGame() {
		Scanner scanner = new Scanner(System.in);

		// game is played until all ships are sunk on one side
		while(playerHits != 17 && AIhits != 17) {
			String gridLoc = "";
			boolean validInput = false;

			System.out.println("Input coordinates to fire to: ");

			// player taking shot after valid input
			while(validInput == false) {
				gridLoc = scanner.next();
				if(gridLoc.length() == 2 && validLetter(gridLoc.substring(0, 1)) == true && validNumber(Integer.parseInt(gridLoc.substring(1, 2))))
					validInput = true;
				else
					System.out.println("Invalid input");
			}
			// received valid input - converting input to ints
			int y = convertLetterInput(gridLoc.substring(0, 1));
			int x = Integer.parseInt(gridLoc.substring(1, 2));
			
			// FIXME TESTING VERSION ONLY
			System.out.println("Player coordinates (" + y + "," + x + ")");

			// firing shot
			shotFired(y, x);
			
			// AI taking shot	
			Random rand = new Random();

			int a = 0;
			int b = 0;
			// ensuring hit on first turn on challenge difficulty
			if(difficulty.equals("challenge") && AIhits + AImisses == 0) {
				out:
				// searching for player ship
				for(int i = 0; i < totalRows; i++)
					for(int j = 0; j < totalCols; j++)
						if(shipLocs[i][j] >= 2 && AIgrid[i][j] == 0) {
							a = i;
							b = j;
							break out;
						}

				// FIXME TESTING VERSION ONLY
				System.out.println("challenge " + a + "," + b);

				// firing shot
				shotFired(a, b);
			}
			// ensuring hit on first turn and after every 5 misses on brutal difficulty
			else if(difficulty.equals("brutal") && (AIhits + AImisses == 0 || (AImisses % 5 == 0 && AImisses > 0))) {
				out:
				// searching for player ship
				for(int i = 0; i < totalRows; i++)
					for(int j = 0; j < totalCols; j++)
						if(shipLocs[i][j] >= 2 && AIgrid[i][j] == 0) {
							a = i;
							b = j;
							break out;
						}

				// FIXME TESTING VERSION ONLY
				System.out.println("brutal " + a + "," + b);

				// firing shot
				shotFired(a, b);
				
			}
			// firing with smartshooting
			else if(smartShooting) {
				int r = rand.nextInt(4);
				while(smartShots[r].equals("XX"))
					r = rand.nextInt(4);
				shotFired(Integer.parseInt(smartShots[r].substring(0,1)),
						Integer.parseInt(smartShots[r].substring(1,2)));

				// FIXME TESTING VERSION ONLY 
				System.out.println("AI coordinates: " + smartShots[r]);
			}
			// default firing at random
			else {
				// generating random coordinates
				int r1 = rand.nextInt(8);
				int r2 = rand.nextInt(8);

				// FIXME TESTING VERSION ONLY
				System.out.println("AI coordinates: (" + r1 + "," + r2 + ")");

				// firing shot
				shotFired(r1, r2);
			}

			// displaying all grids
			displayPlayerShips();
			displayAIShips();
			displayPlayerGrid();
			displayAIGrid();

		}
		// game is over
		getWinner();
	}


	/**
	 *
	 *
	 *
	 *
	 */
	public void AIonly() {

		while(AIhits != 10) {
			// AI taking shot	
			Random rand = new Random();
			currentPlayer = 2;

			int a = 0;
			int b = 0;
			// ensuring hit on first turn on challenge difficulty
			if(difficulty.equals("challenge") && AIhits + AImisses == 0) {
				out:
				// searching for player ship
				for(int i = 0; i < totalRows; i++)
					for(int j = 0; j < totalCols; j++)
						if(shipLocs[i][j] >= 2 && AIgrid[i][j] == 0) {
							a = i;
							b = j;
							break out;
						}

				// FIXME TESTING VERSION ONLY
				System.out.println("challenge " + a + "," + b);

				// firing shot
				shotFired(a, b);
			}
			// ensuring hit on first turn and after every 5 misses on brutal difficulty
			else if(difficulty.equals("brutal") && (AIhits + AImisses == 0 || (AImisses % 5 == 0 && AImisses > 0))) {
				out:
				// searching for player ship
				for(int i = 0; i < totalRows; i++)
					for(int j = 0; j < totalCols; j++)
						if(shipLocs[i][j] >= 2 && AIgrid[i][j] == 0) {
							a = i;
							b = j;
							break out;
						}

				// FIXME TESTING VERSION ONLY
				System.out.println("brutal " + a + "," + b);

				// firing shot
				shotFired(a, b);
				
			}
			// firing with smartshooting
			else if(smartShooting) {
				int r = rand.nextInt(4);
				while(smartShots[r].equals("XX"))
					r = rand.nextInt(4);
				shotFired(Integer.parseInt(smartShots[r].substring(0,1)),
						Integer.parseInt(smartShots[r].substring(1,2)));

				// FIXME TESTING VERSION ONLY 
				System.out.println("AI coordinates: " + smartShots[r]);
			}
			// default firing at random
			else {
				// generating random coordinates
				int r1 = rand.nextInt(8);
				int r2 = rand.nextInt(8);

				// FIXME TESTING VERSION ONLY
				System.out.println("AI coordinates: (" + r1 + "," + r2 + ")");

				// firing shot
				shotFired(r1, r2);
			}

			// displaying grids
			displayPlayerShips();
			//displayAIShips();
			//displayPlayerGrid();
			displayAIGrid();
		}

	}
	
	/**
	 *
	 *
	 *
	 */
	public void placePlayerShips() {

		int playerShipsPlaced = 0;
		int playerCurrentShip = 1;

		// variable to differentiate between Submarine and Cruiser
		boolean playerFirstThreeLong = true;

		// valid letters and numbers
		String lettersUpper = "ABCDEFGH";
		String numbers = "01234567";

		// FIXME JOPTION FOR GUI
		System.out.println("The grid is 8x8. The letters range from A-H and numbers from 0-7.");
		
		// until all ships are placed
		while(playerShipsPlaced < 5) {
			
			String gridLoc = "";

			// counts the directions the ship can be placed in
			int canPlace = 0;
			
			// coordinates of each direction ship can be placed in
			int placeLeft[] = new int[2];
			placeLeft[0] = -1;
			placeLeft[1] = -1;
			int placeRight[] = new int[2];
			placeRight[0] = -1;
			placeRight[1] = -1;
			int placeUpwards[] = new int [2];
			placeUpwards[0] = -1;
			placeUpwards[1] = -1;
			int placeDownwards[] = new int [2];
			placeDownwards[0] = -1;
			placeDownwards[1] = -1;

			// lets program know if ship was placed
			boolean shipPlaced = false;
			// ensures valid user input
			boolean validInput = false;

			// while loop used to check for valid input
			Scanner scanner = new Scanner(System.in);
			while(validInput == false) {
				//System.out.println("Enter the starting point of your ship (Ex: E5): ");
				gridLoc = scanner.next();
				if(gridLoc.length() == 2 && validLetter(gridLoc.substring(0, 1)) == true && validNumber(Integer.parseInt(gridLoc.substring(1, 2))))
					validInput = true;
				else {
					System.out.println("Invalid input. Please try again.\n");
				}
			}

			// FIXME may need to print this out to test it - FIXME toUpperCase() need to be implemented above
			String gridCol = gridLoc.substring(1, 2);
			gridLoc = gridLoc.substring(0, 1).toUpperCase() + gridCol;
		
			// converting char digits to ints
			int y = convertLetterInput(gridLoc.substring(0, 1));
			int x = Integer.parseInt(gridLoc.substring(1, 2));

			// ship not already placed at input coordinates
			if(shipLocs[y][x] == 0) {

				// FIXME I BELIEVE THIS COULD BE OPTIMIZED INTO TWO FOR LOOPS
				// checking in the left direction (number)
				for(int i = x; i >= 0; i--) {
					// check boundary
					if(x - playerCurrentShip < 0) {
						// out of bounds
						break;
					}
					else if(shipLocs[y][i] >= 2) {
						// ship in the way
						break;
					}
					else if(i == 0) {
						placeLeft[0] = y;
						placeLeft[1] = i;
						canPlace++;
					}
				}
				// checking in the right direction (number)	
				for(int i = x; i <= 7; i++) {
					// check boundary
					if(x + playerCurrentShip > 7) {
						// out of bounds
						break;
					}
					else if(shipLocs[y][i] >= 2) {
						// ship in the way
						break;
					}
					else if(i == 7) {
						placeRight[0] = y;
						placeRight[1] = i;
						canPlace++;
					}
				}
				// checking in the upwards direction (letter)	
				for(int i = y; i >= 0; i--) {
					// check boundary
					if(y - playerCurrentShip < 0) {
						// out of bounds
						break;
					}
					else if(shipLocs[i][x] >= 2) {
						// ship in the way
						break;
					}
					else if(i == 0) {
						placeUpwards[0] = i;
						placeUpwards[1] = x;
						canPlace++;
					}
				}
				// checking in the downwards direction (letter)	
				for(int i = y; i <= 7; i++) {
					// check boundary
					if(y + playerCurrentShip > 7) {
						// out of bounds
						break;
					}
					else if(shipLocs[i][x] >= 2) {
						// ship in the way
						break;
					}
					else if(i == 7) {
						placeDownwards[0] = i;
						placeDownwards[1] = x;
						canPlace++;
					}

				}

			} else {
				// can't place in any direction
				canPlace = 0;
			}
			
			// FIXME GIVE AN OPTION TO CANCEL PLACEMENT??

			// cannot place ship
			if(canPlace == 0) {
				System.out.println("You can not place a ship in any direction from this point.");
			}
			// can place in all four directions
			else if(canPlace == 4) {
				Object[] possibleValues = {"Left", "Right", "Upwards", "Downwards"};
			   	Object selectedValue = JOptionPane.showInputDialog(null, "Choose Direction", "Place Ship", 
					JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues[0]);	

				placeShip(y, x, selectedValue.toString(), playerCurrentShip);
				shipPlaced = true;
			}
			// can place in three directions
			else if(canPlace == 3) {
			
				// the three directions ship can be placed in
				String one = "";
				String two = "";
				String three = "";

				int found = 1;

				// determining which three directions ship can be placed in
				if(placeLeft[0] != -1) {
					one = "Left";
					found++;
				}
				if(placeRight[0] != -1) {
					if(found == 1)
						one = "Right";
					else if(found == 2)
						two = "Right";
					found++;
				}
				if(placeUpwards[0] != -1) {
					if(found == 2)
						two = "Upwards";
					else if (found == 3)
						three = "Upwards";
					found++;
				}
				if(found == 3)
					three = "Downwards";

				Object[] possibleValues = {one, two, three};
				Object selectedValue = JOptionPane.showInputDialog(null, "Choose Direction", "Place Ship",
						JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues[0]);

				placeShip(y, x, selectedValue.toString(), playerCurrentShip);
				shipPlaced = true;

			}
			// can place in two directions
			else if(canPlace == 2) {

				// the two directions ship can be placed in
				String one = "";
				String two = "";

				int found = 1;

				// determining the two directions ship can be placed in
				if(placeLeft[0] != -1) {
					one = "Left";
					found++;
				}
				if(placeRight[0] != -1) {
					if(found == 1)
						one = "Right";
					else if(found == 2) 
						two = "Right";
					found++;
				}
				if(placeUpwards[0] != -1) {
					if(found == 1)
						one = "Upwards";
					else if(found == 2)
						two = "Upwards";
					found++;
				}
				if(placeDownwards[0] != -1) {
					if (found == 2)
						two = "Downwards";
				}

				Object[] possibleValues = {one, two};
				Object selectedValue = JOptionPane.showInputDialog(null, "Choose Direction", "Place Ship",
						JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues[0]);

				placeShip(y, x, selectedValue.toString(), playerCurrentShip);
				shipPlaced = true;

			}
			else if(canPlace == 1) {

				// the one direction ship can be placed in
				String one = "";

				// determining which direction ship can be placed in
				if(placeLeft[0] != -1) 
					one = "Left";
				else if(placeRight[0] != -1)
					one = "Right";
				else if(placeUpwards[0] != -1)
					one = "Upwards";
				else if(placeDownwards[0] != -1)
					one = "Downwards";


				Object[] possibleValues = {one};
				Object selectedValue = JOptionPane.showInputDialog(null, "Choose Direction", "Place Ship",
						JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues[0]);

				placeShip(y, x, selectedValue.toString(), playerCurrentShip);
				shipPlaced = true;

			}
			
			// checking to see if first three long ship was placed
			// incrementing ships placed and current ship length
			if(shipPlaced == true) {
				if(playerShipsPlaced == 1 && playerFirstThreeLong == true) {
					playerFirstThreeLong = false;
					playerShipsPlaced++;
				}
				else {
					playerShipsPlaced++; 
					playerCurrentShip++;
				}
			}

		}

		// displaying ship placement
		displayPlayerShips();
	}

	/**
	 *
	 *
	 *
	 *
	 */
	public void presentationDemo() {

		// placing player Carrier
		for(int i = 1; i < 6; i++)
			shipLocs[1][i] = 5;

		// placing player Battleship
		for(int i = 3; i < 7; i++)
			shipLocs[5][i] = 4;

		// placing player Cruiser
		for(int i = 5; i < 8; i++)
			shipLocs[7][i] = 6;

		// placing player Submarine
		for(int i = 2; i < 5; i++)
			shipLocs[i][1] = 3;

		// placing player Destroyer
		for(int i = 2; i < 4; i++)
			shipLocs[i][3] = 2;
		
		// displaying ship placement
		displayPlayerShips();
	}

	/**
	 *
	 *
	 *
	 */
	public boolean validLetter(String letter) { 
		String validLetters = "ABCDEFGH";
		for(int i = 0; i < validLetters.length(); i++)
			if(letter.equals(validLetters.substring(i, i+1)))
				return true;
		return false;
	}

	/**
	 *
	 *
	 *
	 */
	public boolean validNumber(int number) {
		for(int i = 0; i < 8; i++)
			if(number == i)
				return true;
		return false;
	}

	/**
	 *
	 *
	 *
	 */
	public int convertLetterInput(String letter) {
		switch (letter) {
			case "A": 
				return 0;
			case "B": 
				return 1;
			case "C":
				return 2;
			case "D":
				return 3;
			case "E":
				return 4;
			case "F":
				return 5;
			case "G":
				return 6;
			case "H":
				return 7;
		}
		
		System.out.println("should not reach here");
		return -1;
	}

	/**
	 *
	 *
	 *
	 */
	public void placeShip(int a, int b, String dir, int shipLength) {
		// cruiser has a value of 6
		if(placeCruiser)
			shipLocs[a][b] = shipLength+4;
		else 
			// other ships have a value equal to their length
			// shipLength + 1 is actual length of ship
			shipLocs[a][b] = shipLength+1;

		// placing ship in the left direction
		if(dir.equals("Left")) {		
			for(int i = 1; i <= shipLength; i++) { 
				if(placeCruiser) 
					shipLocs[a][b-i] = shipLength+4;
				else 
					shipLocs[a][b-i] = shipLength+1;
			}
		}
		// placing ship in the right direction
		else if(dir.equals("Right")) {
			for(int i = 1; i <= shipLength; i++) {
				if(placeCruiser) 
					shipLocs[a][b+i] = shipLength+4;
				else
					shipLocs[a][b+i] = shipLength+1;
			}
		}
		// placing ship in the upwards direction
		else if(dir.equals("Upwards")) {
			for(int i = 1; i <= shipLength; i++) {
				if(placeCruiser) 
					shipLocs[a-i][b] = shipLength+4;
				else
					shipLocs[a-i][b] = shipLength+1;
			}
		}
		// placing ship in the downwards direction
		else if(dir.equals("Downwards")) {
			for(int i = 1; i <= shipLength; i++) {
				if(placeCruiser) 
					shipLocs[a+i][b] = shipLength+4;
				else 
					shipLocs[a+i][b] = shipLength+1;
			}
		}
		
		// after Cruiser has been placed, set boolean to false
		if(placeCruiser)
			placeCruiser = false;
		// after Submarine has been placed, place Cruiser next
		else if(shipLength+1 == 3) 
			placeCruiser = true;
	}

	/**
	 *
	 *
	 *
	 */
	public void placeAIShips() {

		int AIshipsPlaced = 0;

		// current ship length 
		int AIcurrentShip = 2;

		// boolean to differentiate between Submarine and Cruiser
		boolean AIfirstThreeLong = true;
		
		Random rand = new Random();

		// until all ships have been placed
		while(AIshipsPlaced < 5) {
			// tells program if ship was successfully placed
			boolean shipPlaced = true;

			// generating random coordinates
			int r1 = rand.nextInt(8);
			int r2 = rand.nextInt(8);

			// d is for direction
			int d = rand.nextInt(4);

			// tells program if ship can be placed in directions
			boolean canPlace = true;

			// counts spaces of open water
			int open = 0;
	
			// if trying to place starting point in open water	
			if(AIshipLocs[r1][r2] == 0) {
				open++;

				// trying to place in upwards direction
				if(d == 0 && r1-AIcurrentShip >= 0) {
					for(int i = 0; i < AIcurrentShip-1; i++) {
						// checking for open water and boundaries
						if((r1-i-1) >= 0 && AIshipLocs[r1-i-1][r2] == 0) {
							open++;
						} else {
							// ship is in the way or out of bounds
							open = 0;
							shipPlaced = false;
							break;
						}
					}
					// placing ship upwards with appropriate value
					if(open == AIcurrentShip) {
						for(int i = 0; i < AIcurrentShip; i++) {
							if(placeCruiser)
								AIshipLocs[r1-i][r2] = 6;
							else
								AIshipLocs[r1-i][r2] = AIcurrentShip; 
						}
					}
				}
				// trying to place in the downwards direction
				else if(d == 1 && r1+AIcurrentShip <= 7) {
					for(int i = 0; i < AIcurrentShip-1; i++) {
						// checking for open water and boundaries
						if((r1+i+1) <= 7 && AIshipLocs[r1+i+1][r2] == 0) {
							open++;
						} else {
							// ship is in the way or out of bounds
							open = 0;
							shipPlaced = false;
							break;
						}
					}
					// placing ship downwards with appropriate value
					if(open == AIcurrentShip) {
						for(int i = 0; i < AIcurrentShip; i++) {
							if(placeCruiser)
								AIshipLocs[r1+i][r2] = 6;
							else
								AIshipLocs[r1+i][r2] = AIcurrentShip;
						}
					}
				}
				// trying to place in the left direction
				else if(d == 2 && r2-AIcurrentShip >= 0) {
					for(int i = 0; i < AIcurrentShip-1; i++) {
						// checking for open water and boundaries
						if((r2-i-1) >= 0 && AIshipLocs[r1][r2-i-1] == 0) {
							open++;
						} else {
							// ship is in the way or out of bounds
							open = 0;
							shipPlaced = false;	
							break;
						}
					}
					// placing ship in the left direction
					if(open == AIcurrentShip) {
						for(int i = 0; i < AIcurrentShip; i++) {
							if(placeCruiser)
								AIshipLocs[r1][r2-i] = 6;
							else
								AIshipLocs[r1][r2-i] = AIcurrentShip;
						}
					}
				}
				// trying to place in the right direction	
				else if(d == 3 && r2+AIcurrentShip <= 7) {
					for(int i = 0; i < AIcurrentShip-1; i++) {
						// checking for open water and boundaries
						if((r2+i+1) <= 7 && AIshipLocs[r1][r2+i+1] == 0) {
							open++;
						} else {
							// ship is in the way or out of bounds
							open = 0;
							shipPlaced = false;
							break;
						}
					}
					// placing ship in the right direction
					if(open == AIcurrentShip) {
						for(int i = 0; i < AIcurrentShip; i++) {
							if(placeCruiser)
								AIshipLocs[r1][r2+i] = 6;
							else
								AIshipLocs[r1][r2+i] = AIcurrentShip;
						}
					}
				}
				else {
					// could not place ship
					shipPlaced = false;
				}	
				
			} else {
				// could not place ship
				shipPlaced = false;
			}	

			// incrementing ships placed and current ship
			if(shipPlaced == true) {
				// do not increment current ship when the Submarine is placed
				if(AIshipsPlaced == 1 && AIfirstThreeLong == true) {
					AIfirstThreeLong = false;
					placeCruiser = true;
					AIshipsPlaced++;
				}
				else {
					AIshipsPlaced++;
					AIcurrentShip++;
					placeCruiser = false;
				}
			}
		}
		
		System.out.println("The AI's ships have been placed!");

		// displaying AI ship placement
		displayAIShips();
	}

	/**
	 *
	 *
	 *
	 */
	public void displayPlayerShips() {
		System.out.println("Displaying the Player's Ship Placement:");
		for(int row = 0; row < totalRows; row++) {
			for (int col = 0; col < totalCols; col++) {
				System.out.print(shipLocs[row][col]);
				if(col == 7)
					// next row
					System.out.println("");
			}
		}
	}

	/**
	 *
	 *
	 *
	 */
	public void displayAIShips() {
		System.out.println("Displaying AI's Ship Placement:");
		for(int row = 0; row < totalRows; row++) {
			for (int col = 0; col < totalCols; col++) {
				System.out.print(AIshipLocs[row][col]);
				if(col == 7)
					// next row
					System.out.println("");
			}
		}
	}

	/**
	 *
	 *
	 *
	 */
	public void displayPlayerGrid() {
		System.out.println("Displaying the Player's Grid:");
		for(int row = 0; row < totalRows; row++) {
			for (int col = 0; col < totalCols; col++) {
				System.out.print(grid[row][col]);
				if(col == 7)
					// next row
					System.out.println("");
			}
		}
	}

	/**
	 *
	 *
	 *
	 */
	public void displayAIGrid() {
		System.out.println("Displaying the AI's Grid:");
		for(int row = 0; row < totalRows; row++) {
			for (int col = 0; col < totalCols; col++) {
				System.out.print(AIgrid[row][col]);
				if(col == 7)
					// next row
					System.out.println("");
			}
		}
	}



	/**
	 * Getter method for rows on the grid
	 *
	 * @return number of rows on the grid
	 */
	public int getRows() {
		return totalRows;
	}


	/**
	 * Getter method for columns on the grid
	 *
	 * @return number of columns on the grid
	 */
	public int getCols() {
		return totalCols;
	}



}
