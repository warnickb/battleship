import javax.swing.*;
import java.util.*;

/**
 * BattleshipGame Logic Class
 *
 * @author Justin Perticone
 * @version October 12, 2018
 */

public class BattleshipGame implements BattleshipInterface {

	/* instance variables */
	// PROLLY DONT NEED TOTAL PLAYERS 
	//private int totalPlayers;
	private int currentPlayer;
	// MAY NEED TWO BOARDS 
	// 1) FOR FIRED TO AND NOT FIRED TO
	// 2) FOR SHIP LOCATION AND EMPTY
	private int[][] grid;
	private int[][] AIgrid;
	private int[][] shipLocs;
	private int[][] AIshipLocs;
	private int totalRows;
	private int totalCols;
	private int boardSize;
	private int playerHits;
	private int AIhits;
	private int playerMisses;
	private int AImisses;
	//private boolean firstGame; ???
	

	/**
	 * Default constructor for Battleship game
	 */
	public BattleshipGame() {

		//board is 8 x 8
		totalRows = 8;
		totalCols = 8;
		boardSize = 64;
		currentPlayer = 1;
		playerHits = 0;
		AIhits = 0;
		playerMisses = 0;
		AImisses = 0;

		reset();		

	}

	//WILL NEED MORE CONSTRUCTORS FOR ADDITIONAL GAME MODES
	//CUSTOM GRID SIZE??
	//MOVEABLE SHIPS??
	//POWER-UPS??

	/**
	 * Determines if location of fire is valid
	 * If valid, a shot will be fired to location
	 * Adjusts currentPlayer
	 *
	 * @param row_pos
	 * @param col_pos
	 * @return true when location is valid, otherwise false because
	 * 	1) the lcoation is out of bounds
	 * 	2) the location has already received fire
	 */
	public boolean select(int row_pos, int col_pos) {
		
		// first if checks for in bounds
		if(row_pos > -1 && col_pos > -1 && row_pos < totalRows && col_pos < totalCols) {
			if(board[row_pos][col_pos] == -1) {
				if(currentPlayer <= totalPlayers) {
					// NEED THE LOGIC HERE TO DETERMINE IF THERES A BOAT
					// SAY 1 = NO BOAT, SAY 2 = BOAT
					board[row_pos][col_pos] = currentPlayer + 1;
					if(currentPlayer + 1 == totalPlayers


				}


	}

	// FIXME will probably use two selects, one for player, one for AI
	// board also must be called grid


	/**
	 * Returns whether it is the player's turn or the AI's turn
	 *
	 * @return 0 = player, 1 = AI
	 */
	public int currentPlayer() {

		return currentPlayer;

	}


	/** 
	 * Returns the ID of the winning player
	 *
	 * @return 0 = player, 1 = AI, -1 = winner currently undetermined
	 */
	public int getWinner();


	/**
	 * Reset the game
	 */
	public void reset() {

		// WILL CHANGE ONCE WE HAVE A MAIN MENU
	
		// setting up the grid and assigning all values to -1
		grid = new int[totalRows][totalCols];
		for(int row = 0; row < totalRows; row++)
			for (int col = 0; col < totalCols; col++)
				grid[row][col] = -1;

	
		AIgrid = new int[totalRows][totalCols];
		for(int row = 0; row < totalRows; row++)
			for(int col = 0; col < totalCols; col++)
				AIgrid[row][col] = -1;


		
		shipLocs = new int[totalRows][totalCols];
		for(int row = 0; row < totalRows; row++)
			for(int col = 0; col < totalCols; col++)
				shipLocs[row][col] = 1;

		AIshipLocs = new int[totalRows][totalCols];
		for(int row = 0; row < totalRows; row++)
			for(int col = 0; col < totalCols; col++)
				AIshipLocs[row][col] = 1;

		// FIXME
		placeShips();


	}

	
	/**
	 *
	 *
	 *
	 */
	public void placePlayerShips() {

		int playerShipsPlaced = 0;
		int playerCurrentShip = 2;
		int canPlace = 0;
		boolean playerFirstThreeLong = true;
		// MAY NEED NULL TERMINATOR
		char lettersUpper[8] = {A, B, C, D, E, F, G, H};
		//char lettersLower[8] = {a, b, c, d, e, f, g, h};
		char numbers[8] = {0, 1, 2, 3, 4, 5, 6, 7};

		printf("The grid is 8x8. The letters range from A-H and numbers from 0-7.");
		
		while(playerShipsPlaced < 5) {
			
			char gridLoc[2];
			int placeLeft[2] = {-1, -1};
			int placeRight[2] = {-1, -1};
			int placeUpwards[2] = {-1, -1};
			int placeDownwards = {-1, -1};
			// BE MORE DESCRIPTIVE
			// FIXME NEED LOOP TO PREVENT INVALID INPUT
			printf("Enter the starting point of your ship (Ex: E5): ");
			scanf("%s", gridLoc);
			// USE LOGIC TO ASK FOR WHICH END POINT OF THE 4 DIRECTIONS
			putchar(toupper(gridLoc[0]));
			// NEED TO CHECK FOR BORDERS AND FOR ALREADY PLACED SHIPS
			// convert letter to a number
			for(int i = 0; i < 8; i++) {
				if(strcmp(lettersUpper[i], gridLoc[0] == 0))
					gridLoc[0] = i;
			}
			// coverting char digits to ints
			int y = gridLoc[0] - '0';
			int x = gridLoc[1] - '0';
			if(playerShipLocs[y][x] == 1) {

				// checking in the left direction (number)
				for(int i = x; i >= 0; i--) {
					// first check boundary
					if(x - playerCurrentShip < 0) {
						// out of bounds
						break;
					}
					else if(playerShipLocs[y][i] == 2) {
						// ship in the war
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
					// first check boundary
					if(x + playerCurrentShip > 7) {
						// out of bounds
						break;
					}
					else if(playerShipLocs[y][i] == 2) {
						// ship in the war
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
					// first check boundary
					if(y - playerCurrentShip < 0) {
						// out of bounds
						break;
					}
					else if(playerShipLocs[i][x] == 2) {
						// ship in the war
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
					// first check boundary
					if(y + playerCurrentShip > 7) {
						// out of bounds
						break;
					}
					else if(playerShipLocs[i][x] == 2) {
						// ship in the war
						break;
					}
					else if(i == 0) {
						placeDownwards[0] = i;
						placeDownwards[1] = x;
						canPlace++;
					}
				}

			} else {
				canPlace = 0;
			}
			// MAYBE JUST SAY "LEFT?" "DOWN?" ETC
			if(canPlace == 4) {


				printf("Select ship end point: %s, %s, %s, %s", 

			}
			if(canPlace == 3) {


				printf("Select ship end point: %s, %s, %s", 

			}
			if(canPlace == 2) {


				printf("Select ship end point: %s, %s", 

			}
			if(canPlace == 1) {


				printf("Select ship end point: %s",

			}

			printf("You can not place a ship in any direction from this point.");	


			// THEN PLACE SHIP
			

			// THEN UP CURRENT SHIP

		}

	}

	/**
	 *
	 *
	 *
	 */
	public void placeShips() {

		int playerShipsPlaced = 0;
		int AIshipsPlaced = 0;
		int playerCurrentShip = 2;
		int AIcurrentShip = 2;
		boolean playerFirstThreeLong = true;
		boolean AIfirstThreeLong = true;

		// USE JOPTIONPANES FOR SHIP PLACEMENT??? NAH SYSTEMS
		// MOVE SHIPLOCS INITIALIZATION TO RESET
		// CREATE SOME LOOP OR ARRAY TO CONVERT ABCDEFGH TO NUMBERS 0-7

		// USE SWITCH IN HERE TO CONVERT LETTERS TO NUMBERS
		while(playerShipsPlaced < 5) {
			System.out.println("Start point of 
		}

		Random rand = new Random();

		while(AIshipsPlaced < 5) {
			boolean shipPlaced = true;
			int r1 = rand.nextInt(8);
			int r2 = rand.nextInt(8);
			// d is for direction
			int d = rand.nextInt(4);
			if(AIshipLocs[r1][r2] == 1) {
				// FIXME IF OUT OF BOUNDS NEED TO RESET TO 1
				AIshipLocs[r1][r2] = 2;
				if(d == 0) {
					for(int i = 0; i < AIcurrentShip-1; i++) {
						// MAY ALSO NEED CONDITIONAL FOR IN BOUNDS
						if((r1-i-1) >= 0 && AIshipLocs[r1-i-1][r2] == 1) {
							AIshipLocs[r1-i-1][r2] = 2;
						} else {
							for(int j = AIcurrentShip; j >= 0; j--) {
								AIshipLocs[r1-j][r2] = 1;
								shipPlaced = false;
							}
						}
					}
				}
				else if(d == 1) {
					for(int i = 0; i < AIcurrentShip-1; i++) {
						// MAY ALSO NEED CONDITIONAL FOR IN BOUNDS
						if((r1+i+1) >= 0 && AIshipLocs[r1+i+1][r2] == 1) {
							AIshipLocs[r1+i+1][r2] = 2;
						} else {
							for(int j = AIcurrentShip; j >= 0; j--) {
								AIshipLocs[r1+j][r2] = 1;
								shipPlaced = false;
							}
						}
					}

				}
				else if(d == 2) {
					for(int i = 0; i < AIcurrentShip-1; i++) {
						// MAY ALSO NEED CONDITIONAL FOR IN BOUNDS
						if((r2-i-1) >= 0 && AIshipLocs[r1][r2-i-1] == 1) {
							AIshipLocs[r1][r2-i-1] = 2;
						} else {
							for(int j = AIcurrentShip; j >= 0; j--) {
								// MAY NEED TO BE CHECKING THESE FOR OB
								AIshipLocs[r1][r2-j] = 1;
								shipPlaced = false;
							}
						}
					}
				}
				else if(d == 3) {
					for(int i = 0; i < AIcurrentShip-1; i++) {
						// MAY ALSO NEED CONDITIONAL FOR IN BOUNDS
						if((r1-i-1) >= 0 && AIshipLocs[r1-i-1][r2] == 1) {
							AIshipLocs[r1-i-1][r2] = 2;
						} else {
							for(int j = AIcurrentShip; j >= 0; j--) {
								AIshipLocs[r1-j][r2] = 1;
								shipPlaced = false;
							}
						}
					}
				}
				else {
					System.out.println("We have an error.");
				}	



				//FIXME place ship start, then find direction it may go in
				//may need a boolean if these ifs to say a ship was placed 
				
			} else {
				shipPlaced = false;
			}	

			if(shipPlaced == true) {
				if(AIshipsPlaced == 3 && AIfirstThreeLong == true) {
					AIshipsPlaced += 0;
					AIfirstThreeLong = false;
				}
				else
					AIshipsPlaced++;
			}
		}
	}


	// FIXME METHODS TO DISPLAY GRIDS IN TESTING


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
