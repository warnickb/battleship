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
	// 1 = player, 2 = AI
	private int currentPlayer;
	// MAY NEED TWO BOARDS 
	// 1) FOR FIRED TO AND NOT FIRED TO
	// 2) FOR SHIP LOCATION AND EMPTY
	// -1 means not fired to, 1 means been fired to
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
	//private boolean firstGame; any need???
	

	/**
	 * Default constructor for Battleship game
	 */
	public BattleshipGame() {

		// default board is 8 x 8
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
	 * 	1) the location is out of bounds
	 * 	2) the location has already received fire
	 */
	public boolean shotFired(int row_pos, int col_pos) {  // THIS GETS CALLED WHEN A BUTTON ON THE GUI IS CLICKED
		
		// GUI SHOULD ENSURE VALID INPUT
		if(currentPlayer == 1) {
			// checking to see if location has already been fired to
			if(grid[row_pos][col_pos] != -1) {
				// THIS MAY CHANGE WITH GUI INTEGRATION
				System.out.println("You have already fired to this location!");
				return false;
			}
			if(row_pos > -1 && col_pos > -1 && row_pos < totalRows && col_pos < totalCols) {
				// missed shot
				if(AIshipLocs[row_pos][col_pos] == 1) {
					playerMisses++;
					// update currentPlayer
					if(currentPlayer == 2)
						currentPlayer = 1;
					else 
						currentPlayer++;
					return false;
				} 
				// hit shot
				else if(AIshipLocs[row_pos][col_pos] == 2) {
					playerHits++;
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
		else if(currentPlayer == 2) {
			// checking to see if location has already been fired to
			if(AIgrid[row_pos][col_pos] != -1) {
				// IN SMART AI, WE WILL WANT TO ADD AN OPTION TO SAVE NEXT SHOT AFTER A HIT
				// TO SOME PLACE NEAR LAST HIT
				// THIS MAY CHANGE WITH GUI INTEGRATION
				// System.out.println("You have already fired to this location!");
				return false;
			}
			if(row_pos > -1 && col_pos > -1 && row_pos < totalRows && col_pos < totalCols) {
				// missed shot
				if(shipLocs[row_pos][col_pos] == 1) {
					AImisses++;
					// update currentPlayer
					if(currentPlayer == 2)
						currentPlayer = 1;
					else 
						currentPlayer++;
					return false;
				} 
				// hit shot
				else if(shipLocs[row_pos][col_pos] == 2) {
					AIhits++;
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
	public void getWinner() {  // FIXME probably dont want this to be int but be void - originally an int
		if(playerHits == 17) {
			System.out.println("Player has won!!");
			
		} 
		else if (AIhits == 17) {
			System.out.println("The computer has won!!");

		}

		// FIXME GIVE OPTION TO START A NEW GAME OR EXIT
	}


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
		
		System.out.println("Time to place your ships!");
		placePlayerShips();
		System.out.println("The AI's ships will now be placed at random...");
		placeAIShips();

	}

	
	/**
	 *
	 *
	 *
	 */
	public void placePlayerShips() {

		int playerShipsPlaced = 0;
		int playerCurrentShip = 1;
		boolean playerFirstThreeLong = true;
		String lettersUpper = "ABCDEFGH";
		String numbers = "01234567";

		System.out.println("The grid is 8x8. The letters range from A-H and numbers from 0-7.");
		
		while(playerShipsPlaced < 5) {
			
			String gridLoc = "";
			int canPlace = 0;
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
			boolean shipPlaced = false;
			boolean validInput = false;

			// while loop used to check for valid input
			Scanner scanner = new Scanner(System.in);
			while(validInput == false) {
				System.out.println("Enter the starting point of your ship (Ex: E5): ");
				gridLoc = scanner.next();
				if(gridLoc.length() == 2 && validLetter(gridLoc.substring(0, 1)) == true && validNumber(Integer.parseInt(gridLoc.substring(1, 2))))
					validInput = true;
				else {
					System.out.println("Invalid input. Please try again.\n");
				}
			}

			// FIXME may need to print this out to test it
			String gridCol = gridLoc.substring(1, 2);
			gridLoc = gridLoc.substring(0, 1).toUpperCase() + gridCol;
		
			// coverting char digits to ints FIXME NEED TO USE SUBSTRING
			int y = convertLetterInput(gridLoc.substring(0, 1));
			int x = Integer.parseInt(gridLoc.substring(1, 2));
			if(shipLocs[y][x] == 1) {

				// FIXME I BELIEVE THIS COULD BE OPTIMIZED INTO ONE FOR LOOP
				// checking in the left direction (number)
				for(int i = x; i >= 0; i--) {
					// first check boundary
					if(x - playerCurrentShip < 0) {
						// out of bounds
						break;
					}
					else if(shipLocs[y][i] == 2) {
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
					// first check boundary
					if(x + playerCurrentShip > 7) {
						// out of bounds
						break;
					}
					else if(shipLocs[y][i] == 2) {
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
					// first check boundary
					if(y - playerCurrentShip < 0) {
						// out of bounds
						break;
					}
					else if(shipLocs[i][x] == 2) {
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
					// first check boundary
					if(y + playerCurrentShip > 7) {
						// out of bounds
						break;
					}
					else if(shipLocs[i][x] == 2) {
						// ship in the way
						break;
					}
					else if(i == 7) {
						placeDownwards[0] = i;
						placeDownwards[1] = x;
						canPlace++;
					}

					// FIXME changed a whole lot of playerShipLocs to shipLocs
				}

			} else {
				canPlace = 0;
			}
			
			// FIXME GIVE AN OPTION TO CANCEL PLACEMENT??
			if(canPlace == 0) {
				System.out.println("You can not place a ship in any direction from this point.");
			}
			else if(canPlace == 4) {

				Object[] possibleValues = {"Left", "Right", "Upwards", "Downwards"};
			   	Object selectedValue = JOptionPane.showInputDialog(null, "Choose Direction", "Place Ship", 
					JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues[0]);	

				placeShip(y, x, selectedValue.toString(), playerCurrentShip);
				shipPlaced = true;

			}
			else if(canPlace == 3) {
				// FIXME MAY NOT BE ABLE TO KEEP REUSING VARIABLES
				String one = "";
				String two = "";
				String three = "";
				int found = 1;
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
			else if(canPlace == 2) {

				String one = "";
				String two = "";
				int found = 1;
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

				String one = "";
				if(placeLeft[0] != -1) 
					one = "Left";
				else if(placeRight[0] != -1)
					one = "Right";
				else if(placeUpwards[0] != -1)
					one = "Upwards";
				else if(placeDownwards[0] != -1)
					one = "Downwards";
				// FIXME COULD BE OPTIMIZED
				Object[] possibleValues = {one};
				Object selectedValue = JOptionPane.showInputDialog(null, "Choose Direction", "Place Ship",
						JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues[0]);

				placeShip(y, x, selectedValue.toString(), playerCurrentShip);
				shipPlaced = true;

			}
			
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

		displayPlayerShips();
	}

	/**
	 *
	 *
	 *
	 */
	public boolean validLetter(String letter) { // FIXME NEED TO FIND WHERE THIS IS CALLED
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
		shipLocs[a][b] = 2;
		if(dir.equals("Left")) {
			for(int i = 1; i <= shipLength; i++)
				shipLocs[a][b-i] = 2;
		}
		if(dir.equals("Right")) {
			for(int i = 1; i <= shipLength; i++)
				shipLocs[a][b+i] = 2;
		}
		if(dir.equals("Upwards")) {
			for(int i = 1; i <= shipLength; i++)
				shipLocs[a-i][b] = 2;
		}
		if(dir.equals("Downwards")) {
			for(int i = 1; i <= shipLength; i++)
				shipLocs[a+i][b] = 2;
		}
	}

	/**
	 *
	 *
	 *
	 */
	public void placeAIShips() {

		int AIshipsPlaced = 0;
		// FIXME AICURSHIP MAY NEED ADJUSTMENT LIKE IT DID IN PLAYER PLACING
		int AIcurrentShip = 2;
		boolean AIfirstThreeLong = true;

		// USE JOPTIONPANES FOR SHIP PLACEMENT??? NAH SYSTEMS
		// MOVE SHIPLOCS INITIALIZATION TO RESET
		// CREATE SOME LOOP OR ARRAY TO CONVERT ABCDEFGH TO NUMBERS 0-7
		
		Random rand = new Random();

		while(AIshipsPlaced < 5) {
			boolean shipPlaced = true;
			int r1 = rand.nextInt(8);
			int r2 = rand.nextInt(8);
			// d is for direction
			int d = rand.nextInt(4);
		
			if(AIshipLocs[r1][r2] == 1) {
				// FIXME IF OUT OF BOUNDS NEED TO RESET TO 1 *****
				//
				// FIXME THE RETHINKING BEGINS HERE, NEED TO DO 2 SETTING ELSEWHERE 
				//AIshipLocs[r1][r2] = 2;
				// LOOKS IN THE UPWARD DIRECTION, IN RETHINKING, MOVE THE R1-I-1 TO FIRST IF CONDITIONAL
				// FIXME CURSHIP MIGHT NEED +1
				// ********************************************************************************************
				// FIXME COMPLETE REVAMPING SHOULD INCLUDE NO PLACING UNTIL ALL PLACES HAVE BEEN CHECKED
				// ********************************************************************************************
				if(d == 0 && r1-AIcurrentShip >= 0) {
					AIshipLocs[r1][r2] = 2;
					for(int i = 0; i < AIcurrentShip-1; i++) {
						// MAY ALSO NEED CONDITIONAL FOR IN BOUNDS - FIRST PART OF IF SHOULD BE DOING THIS 0S AND 7S MAY BE THE ISSUE!!!!!!
						if((r1-i-1) >= 0 && AIshipLocs[r1-i-1][r2] == 1) {
							AIshipLocs[r1-i-1][r2] = 2;
						} else {
							// FIXME THESE RESETS ARE GOING OB TO SET VALUES - WILL NEED TO USE VARIABLE i, used to use aicurship
							for(int j = i; j >= 0; j--) {
								AIshipLocs[r1-j][r2] = 1;
								shipPlaced = false;
							}
							break;
						}
					}
				}
				else if(d == 1 && r1+AIcurrentShip <= 7) {
					AIshipLocs[r1][r2] = 2;
					for(int i = 0; i < AIcurrentShip-1; i++) {
						// MAY ALSO NEED CONDITIONAL FOR IN BOUNDS
						if((r1+i+1) <= 7 && AIshipLocs[r1+i+1][r2] == 1) {
							AIshipLocs[r1+i+1][r2] = 2;
						} else {
							// FIXME this style of resetting must be reworked
							for(int j = i; j >= 0; j--) {
								AIshipLocs[r1+j][r2] = 1;
								shipPlaced = false;
							}
							break;
						}
					}

				}
				else if(d == 2 && r2-AIcurrentShip >= 0) {
					AIshipLocs[r1][r2] = 2;
					for(int i = 0; i < AIcurrentShip-1; i++) {
						// MAY ALSO NEED CONDITIONAL FOR IN BOUNDS - FIXME REMOVE THE REDUNDANT CONDITIONALS (first part)
						if((r2-i-1) >= 0 && AIshipLocs[r1][r2-i-1] == 1) {
							AIshipLocs[r1][r2-i-1] = 2;
						} else {
							for(int j = i; j >= 0; j--) {
								// MAY NEED TO BE CHECKING THESE FOR OB
								AIshipLocs[r1][r2-j] = 1;
								shipPlaced = false;
							}
							break;
						}
					}
				}
				else if(d == 3 && r2+AIcurrentShip <= 7) {
					AIshipLocs[r1][r2] = 2;
					for(int i = 0; i < AIcurrentShip-1; i++) {
						// MAY ALSO NEED CONDITIONAL FOR IN BOUNDS
						if((r2+i+1) <= 7 && AIshipLocs[r1][r2+i+1] == 1) {
							AIshipLocs[r1][r2+i+1] = 2;
						} else {
							for(int j = i; j >= 0; j--) {
								AIshipLocs[r1][r2+j] = 1;
								shipPlaced = false;
							}
							break;
						}
					}
				}
				else {
					shipPlaced = false;
					//System.out.println("We have an error.");
				}	



				//FIXME place ship start, then find direction it may go in
				//may need a boolean if these ifs to say a ship was placed 
				
			} else {
				shipPlaced = false;
			}	

			if(shipPlaced == true) {
				if(AIshipsPlaced == 2 && AIfirstThreeLong == true) {
					AIfirstThreeLong = false;
					AIshipsPlaced++;
				}
				else {
					AIshipsPlaced++;
					AIcurrentShip++;
				}
			}
		}
		
		System.out.println("The AI's ships have been place!");
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
