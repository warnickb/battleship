package battleshipproject;

import javax.swing.*;

import java.util.*;
import java.awt.*;

/**************************************************************************************
 * BattleshipGame Logic Class For Use With GUI
 *
 * @author Justin Perticone
 * @version November 29, 2018
 **************************************************************************************/

public class BattleshipLogicForGUI implements BattleshipInterface {

	/* instance variables */

	/* 1 = player, 2 = AI */
	int currentPlayer;

	/* keeps track of player's shots */
	int[][] grid;

	/* keeps track of AI's shots */
	int[][] AIgrid;

	/* keeps track of player's ships */
	int[][] shipLocs;

	/* keeps track of AI's ships */
	int[][] AIshipLocs;

	/* grid is 8 x 8 */
	int totalRows;
	int totalCols;
	int gridSize;

	/* count hits and misses */
	int playerHits;
	int AIhits;
	int playerMisses;
	int AImisses;
	int hitStreak;
	int AIhitStreak;
	int bestStreak;
	int AIbestStreak;

	/* used to differentiate the two 3 long ships */
	boolean placeCruiser;

	/* variables used for AI "smartshooting" */
	boolean smartShooting;
	boolean shipHit;
	int smartHits;
	int initialHitRow;
	int initialHitCol;
	int initialShipHit;
	int brutalHits;
	String rememberOtherHit;
	String rememberOtherHit2;
	String rememberOtherHit3;

	/* determines AI level */
	String difficulty;

	//FIXME FIXME FIXME WILL NEED TO MOVE THESE TO WORK WITH RESET - or have a reset special arrays method
	/* used to help AI sink a ship after it lands a hit */
	String[] smartShots = {"XX", "XX", "XX", "XX"};

	/* keeps track of the life of each ship of both the player and AI */
	int[] allShipsStatus = new int[]{2, 3, 3, 4, 5, 2, 3, 3, 4, 5};

	/* array with all the names of the ships */ 
	String[] allShipNames = new String[]{"Destroyer", "Submarine", "Cruiser", "Battleship", "Carrier"};

	/* array of ships hit aside from the first ship while AI is trying to sink a ship */ 
	int[] shipsRemembered = new int[]{0, 0, 0, 0};
	
	ImageIcon shipsIcon;
	ImageIcon shipsPlacedIcon;
	ImageIcon desIcon;
	ImageIcon subIcon;
	ImageIcon cruIcon;
	ImageIcon batIcon;
	ImageIcon carIcon;
	ImageIcon coordIcon;
	ImageIcon invalidIcon;

	

	/***************************************************************************
	 * Default constructor for Battleship Game
	 ***************************************************************************/
	public BattleshipLogicForGUI() {

		// default board is 8 x 8
		totalRows = 8;
		totalCols = 8;
		gridSize = 64;
		
		shipsIcon = new ImageIcon(BattleshipLogicForGUI.class.getResource("linedships.jpg"));
		shipsPlacedIcon = new ImageIcon(BattleshipLogicForGUI.class.getResource("ships.jpg"));
		desIcon = new ImageIcon(BattleshipLogicForGUI.class.getResource("destroyer.jpg"));
		subIcon = new ImageIcon(BattleshipLogicForGUI.class.getResource("submarine.jpg"));
		cruIcon = new ImageIcon(BattleshipLogicForGUI.class.getResource("cruiser.jpg"));
		batIcon = new ImageIcon(BattleshipLogicForGUI.class.getResource("battleship.jpg"));
		carIcon = new ImageIcon(BattleshipLogicForGUI.class.getResource("carrier.jpg"));
		coordIcon = new ImageIcon(BattleshipLogicForGUI.class.getResource("coordinates.png"));
		invalidIcon = new ImageIcon(BattleshipLogicForGUI.class.getResource("invalid.png"));
		
		reset();		

	}


	/***************************************************************************
	 * FIXME I THINK IT MIGHT BE BEST TO JUST HAVE DIFFICULTY DECIDED IN RESET
	 ***************************************************************************/
	public BattleshipLogicForGUI(String difficulty) {

		totalRows = 8;
		totalCols = 8;
		gridSize = 64;
		this.difficulty = difficulty;

		reset();

	}

	
	/**
	 *
	 *
	 *
	 *
	 */
	public boolean checkFire(int row_pos, int col_pos) {

		if(currentPlayer == 1)
			if(grid[row_pos][col_pos] != 0) {
				JOptionPane.showMessageDialog(null, "You have already fired to this location!");
				return false;
			} else {
				return true;
			}
		if(currentPlayer == 2)
			if(AIgrid[row_pos][col_pos] != 0)
				return false;
			else
				return true;
		System.out.println("Should not reach here.");
		return false;

	}


	/****************************************************************************
	 * Determines if location of fire is valid
	 *
	 * @param row_pos - row value
	 * @param col_pos - column value
	 * @return true when location is valid, otherwise false because
	 * 	1) the location is out of bounds
	 * 	2) the location has already received fire
	 ****************************************************************************/
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

				JOptionPane.showMessageDialog(null, "You have already fired to this location!");

				return false; // FIXME GUI WILL LOOP UNTIL TRUE IS RETURNED INDICATING A SUCCESSFUL SHOT
			}
			else if(row_pos > -1 && col_pos > -1 && row_pos < totalRows && col_pos < totalCols) {
				// missed shot FIXME NOTIFY OF A MISS - JOPTION
				if(AIshipLocs[row_pos][col_pos] == 0) {
					// increment player misses
					playerMisses++;
					hitStreak = 0;
					
					// 1 on grid indicates a miss
					grid[row_pos][col_pos] = 1;

					// FIXME TESTING VERSION ONLY
					System.out.println("Player Miss: " + playerMisses);
					System.out.println("loc " + row_pos + " " + col_pos);
					System.out.println("CP : " + currentPlayer);

					// update currentPlayer
					currentPlayer = 2;
					//if(currentPlayer == 2)
						//currentPlayer = 1;
					//else 
						//currentPlayer++;
					return false;
				} 
				// hit shot
				else if(AIshipLocs[row_pos][col_pos] >= 2) {
					// increment player hits
					playerHits++;
					hitStreak++;
					
					if(hitStreak > bestStreak)
						bestStreak = hitStreak;

					// FIXME TESTING VERSION ONLY
					System.out.println("Player Hit: " + playerHits);
					System.out.println("loc " + row_pos + " " + col_pos);
					System.out.println("CP : " + currentPlayer);
				
					// 2 on grid indicates a hit	
					grid[row_pos][col_pos] = 2;
			
					// checking for s sunk ship
					String shipSunk = sunkStatus(AIshipLocs[row_pos][col_pos], currentPlayer);

					// ship has been sunk
					if(shipSunk.length() > 1) {
						if(shipSunk.equals("Destroyer"))
							JOptionPane.showMessageDialog(null, "The enemies " + shipSunk + " has been sunk!!", "Ship Sunk!",
									JOptionPane.INFORMATION_MESSAGE, desIcon);
						else if(shipSunk.equals("Submarine"))
							JOptionPane.showMessageDialog(null, "The enemies " + shipSunk + " has been sunk!!", "Ship Sunk!",
									JOptionPane.INFORMATION_MESSAGE, subIcon);
						else if(shipSunk.equals("Cruiser"))
							JOptionPane.showMessageDialog(null, "The enemies " + shipSunk + " has been sunk!!", "Ship Sunk!",
									JOptionPane.INFORMATION_MESSAGE, cruIcon);
						else if(shipSunk.equals("Battleship"))
							JOptionPane.showMessageDialog(null, "The enemies " + shipSunk + " has been sunk!!", "Ship Sunk!",
									JOptionPane.INFORMATION_MESSAGE, batIcon);
						else if(shipSunk.equals("Carrier"))
							JOptionPane.showMessageDialog(null, "The enemies " + shipSunk + " has been sunk!!", "Ship Sunk!",
									JOptionPane.INFORMATION_MESSAGE, carIcon);
					}

					currentPlayer = 2;
					// update currentPlayer
					//if(currentPlayer == 2)
						//currentPlayer = 1;
					//else
						//currentPlayer++;
					return true;
				}
				else {
					JOptionPane.showMessageDialog(null, "Error 1");
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
					AIhitStreak = 0;

					// 1 on AIgrid indicates miss
					AIgrid[row_pos][col_pos] = 1;

					// FIXME TESTING VERSION ONLY
					System.out.println("AI Miss : " + AImisses);
					System.out.println("loc " + row_pos + " " + col_pos);
					System.out.println("CP : " + currentPlayer);

					// update currentPlayer
					//if(currentPlayer == 2)
					currentPlayer = 1;
					//else 
						//currentPlayer++;
					return false;
				} 
				// hit shot
				else if(shipLocs[row_pos][col_pos] >= 2) {
					// increment AI hits
					AIhits++;
					AIhitStreak++;
					
					if(AIhitStreak > AIbestStreak)
						AIbestStreak = AIhitStreak;

					// FIXME TESTING VERSION ONLY
					System.out.println("AI Hit: " + AIhits);
					System.out.println("loc " + row_pos + " " + col_pos);
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
									JOptionPane.showMessageDialog(null, "Error 2");
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
									JOptionPane.showMessageDialog(null, "Error 3");
								// AI has learned direction of the ship - only needs above and below coords now
								smartShots[0] = "XX";
								smartShots[1] = "XX";
							}
							else {
								// another ship was hit during smartshooting, remember coords
								// AI could hit up to three additional ships while trying to sink initial ship
								// hit a second ship during smartshooting

								// checking to see if ship has been hit yet
								for(int i = 0; i < 4; i++)
									if(shipsRemembered[i] == shipLocs[row_pos][col_pos])
										// ship has already been hit
										shipHit = true;
								
								// ship not yet remembered, remember it
								if(!shipHit) {
									for(int i = 0; i < 4; i++)
										if(shipsRemembered[i] == 0)
											shipsRemembered[i] = shipLocs[row_pos][col_pos];
								}

								// ship not yet remembered, remember it
								if(!shipHit) {
									if(rememberOtherHit.length() == 0)
										rememberOtherHit = String.valueOf(row_pos) + String.valueOf(col_pos);
									// hit a third ship during smartshooting
									else if(rememberOtherHit2.length() == 0)
										rememberOtherHit2 = String.valueOf(row_pos) + String.valueOf(col_pos);
									// hit a fourth ship during smartshooting
									else
										rememberOtherHit3 = String.valueOf(row_pos) + String.valueOf(col_pos);
								}
								
								// resetting shipHit to false
								shipHit = false;
							}
						}

					}

					// checking for a sunk ship
					String shipSunk = sunkStatus(shipLocs[row_pos][col_pos], currentPlayer);

					// ship has been sunk
					if(shipSunk.length() > 1) {
						if(shipSunk.equals("Destroyer"))
							JOptionPane.showMessageDialog(null, "Your " + shipSunk + " has been sunk!!", "Ship Sunk!", 
									JOptionPane.INFORMATION_MESSAGE, desIcon);
						else if(shipSunk.equals("Submarine"))
							JOptionPane.showMessageDialog(null, "Your " + shipSunk + " has been sunk!!", "Ship Sunk!", 
									JOptionPane.INFORMATION_MESSAGE, subIcon);
						else if(shipSunk.equals("Cruiser"))
							JOptionPane.showMessageDialog(null, "Your " + shipSunk + " has been sunk!!", "Ship Sunk!", 
									JOptionPane.INFORMATION_MESSAGE, cruIcon);
						else if(shipSunk.equals("Battleship"))
							JOptionPane.showMessageDialog(null, "Your " + shipSunk + " has been sunk!!", "Ship Sunk!", 
									JOptionPane.INFORMATION_MESSAGE, batIcon);
						else if(shipSunk.equals("Carrier"))
							JOptionPane.showMessageDialog(null, "Your " + shipSunk + " has been sunk!!", "Ship Sunk!", 
									JOptionPane.INFORMATION_MESSAGE, carIcon);
				
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
					//if(currentPlayer == 2)
					currentPlayer = 1;
					//else
						//currentPlayer++;
					return true;
				}
				else {
					JOptionPane.showMessageDialog(null, "Error 4");
				}
			}

		} else {
			JOptionPane.showMessageDialog(null, "Error 5");
		}
		
		JOptionPane.showMessageDialog(null, "Error 6");
		return false;
	}


	/****************************************************************************
	 * Used to enhance the AI
	 * Remembers any ships hit while working to sink the ship
	 * it first hit
	 * Remembers up to three ships on top of the ship initially hit
	 ****************************************************************************/
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

	
	/****************************************************************************
	 * Checks on the status of all ships to see if any have been sunk
	 * Method is called after every hit by both the player and the AI
	 *
	 * @param shipHit - the value used to uniquely identify each ship
	 * @param player - 1 = player, 2 = AI
	 * @return the name of the ship sunk, empty string if no ship sunk
	 ****************************************************************************/
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
					JOptionPane.showMessageDialog(null, "Error 7");
				// adjusting ships life to -1 to indicate sunk
				allShipsStatus[i] = -1;

				// forgetting ship
				if(player == 2) {
					for(int j = 0; j < 4; j++)
						if(shipsRemembered[j] == shipHit)
							shipsRemembered[j] = 0;
				}
			}
		}

		// FIXME TESTING VERSION ONLY
		System.out.println("sunkenShip = " + sunkenShip);

		// returning the sunken ship
		return sunkenShip;
	}

	
	/****************************************************************************
	 * Returns whether it is the player's turn or the AI's turn
	 *
	 * @return 1 = player, 2 = AI
	 ****************************************************************************/
	public int currentPlayer() {
		return currentPlayer;
	}


	/****************************************************************************
	 * Method executed once all ships have been sunk on one side
	 * Asks user if they would like to play again - FIXME
	 ****************************************************************************/
	public void getWinner() { 
		// player won the game
		if(playerHits == 17) {
			JOptionPane.showMessageDialog(null, "You have won the game!!");
		} 
		// AI won the game
		else if (AIhits == 17) {
			JOptionPane.showMessageDialog(null, "The enemy has won the game!!");
		}
		else 
			JOptionPane.showMessageDialog(null, "Error 8");

		// asking user if they would like to play again
		String playAgain = JOptionPane.showInputDialog(null, "Would you like to play again? (y/n)");
		if(playAgain.equals("y")) 
			// user wants to play another game
			reset();
		else if(playAgain.equals("n")) {
			JOptionPane.showMessageDialog(null, "Thank you for playing!!");
			// FIXME ADD CLOSING PROGRAM TO GUI
		}
		else 
			JOptionPane.showMessageDialog(null, "Invalid input. Enter \"y\" or \"n\" next time!");

	}


	/****************************************************************************
	 * Resets the game
	 ****************************************************************************/
	public void reset() {

		Object[] possibleDiffs = {"Normal", "Challenge", "Brutal"};
	   	Object selectedDiff = JOptionPane.showInputDialog(null, "Choose Difficulty", "Difficulty Selection", 
			JOptionPane.INFORMATION_MESSAGE, null, possibleDiffs, possibleDiffs[0]);	
		difficulty = selectedDiff.toString();

		currentPlayer = 1;
		playerHits = 0;
		AIhits = 0;
		playerMisses = 0;
		AImisses = 0;
		hitStreak = 0;
		AIhitStreak = 0;
		bestStreak = 0;
		AIbestStreak = 0;
		smartShooting = false;
		shipHit = false;
		smartHits = 0;
		initialHitRow = -2;
		initialHitCol = -2;
		initialShipHit = -2;
		brutalHits = 0;
		placeCruiser = false;
		rememberOtherHit = "";
		rememberOtherHit2 = "";
		rememberOtherHit3 = "";
	
		// FIXME CREATE A HELPER METHOD TO RESET THESE
		/* used to help AI sink a ship after it lands a hit */
		//smartShots = {"XX", "XX", "XX", "XX"};
	
		/* keeps track of the life of each ship of both the player and AI */
		//allShipsStatus = new int[]{2, 3, 3, 4, 5, 2, 3, 3, 4, 5};

		/* array with all the names of the ships */ 
		//allShipNames = new String[]{"Destroyer", "Submarine", "Cruiser", "Battleship", "Carrier"};

		/* array of ships hit aside from the first ship while AI is trying to sink a ship */ 
		//shipsRemembered = new int[]{0, 0, 0, 0};
	

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
		JOptionPane.showMessageDialog(null, "Time to place your ships!", "Game Setup", 
				JOptionPane.INFORMATION_MESSAGE, shipsIcon);
		placePlayerShips();
		//presentationDemo();

		// AI ships will now be placed at random
		JOptionPane.showMessageDialog(null, "The AI's ships will now be placed at random...", "Game Setup",
				JOptionPane.INFORMATION_MESSAGE, shipsIcon);
		placeAIShips();

		// begin the game
		//playGame();
		//AIonly();
		
		// FIXME BEGIN TIMER HERE 
		

	}


	
	public void playGame() {}



	/**
	 *
	 *
	 *
	 *
	 */
	public void AIonly() {}

	
	
	/****************************************************************************
	 * Allows the player to place their ships 
	 ****************************************************************************/
	public void placePlayerShips() {

		int playerShipsPlaced = 0;
		int playerCurrentShip = 1;

		// variable to differentiate between Submarine and Cruiser
		boolean playerFirstThreeLong = true;

		// valid letters and numbers
		String lettersUpper = "ABCDEFGH";
		String numbers = "01234567";
		
		// FIXME FIXME FIXME FIXME FIXME FIXME 

		JOptionPane.showMessageDialog(null, "The grid is 8x8. The letters for the columns range from A-H " + 
				"and numbers for the rows range from 0-7.", "Game Setup", 
				JOptionPane.INFORMATION_MESSAGE, coordIcon);
		
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
			while(validInput == false) {
				gridLoc = JOptionPane.showInputDialog(null, "Enter the coordinates for your ship: ");
				if(gridLoc.length() == 2 && validLetter(gridLoc.substring(0, 1)) == true && validNumber(Integer.parseInt(gridLoc.substring(1, 2))))
					validInput = true;
				else
					JOptionPane.showMessageDialog(null, "Invalid input. Please try again.", "Game Setup",
							JOptionPane.INFORMATION_MESSAGE, invalidIcon);
			}

			// FIXME toUpperCase() need to be implemented above
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
			
			// cannot place ship
			if(canPlace == 0) {
				JOptionPane.showMessageDialog(null, "You cannot place a ship in any direction from this point " +
						"or have already placed a ship here.", "Game Setup", JOptionPane.INFORMATION_MESSAGE, invalidIcon);
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

	
	/****************************************************************************
	 * Used specifically to demo the project in class
	 ****************************************************************************/
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

	
	/****************************************************************************
	 * Checks to see if the player's letter input for row is valid
	 *
	 * @param letter - the letter the player input for row
	 * @return true if valid, false otherwise
	 ****************************************************************************/
	public boolean validLetter(String letter) { 
		String validLetters = "ABCDEFGH";
		for(int i = 0; i < validLetters.length(); i++)
			if(letter.equals(validLetters.substring(i, i+1)))
				return true;
		return false;
	}

	
	/****************************************************************************
	 * Checks to see if the player's number input for column is valid
	 *
	 * @param number - the number the player input for column
	 * @return true if valid, false otherwise
	 ****************************************************************************/
	public boolean validNumber(int number) {
		for(int i = 0; i < 8; i++)
			if(number == i)
				return true;
		return false;
	}

	
	/****************************************************************************
	 * Converts valid player letter input into the correct number value
	 * Number value needed to access the 2D array properly
	 *
	 * @param letter - the number the player input for row
	 * @return the corresponding number value for letter entered
	 ****************************************************************************/
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
		
		JOptionPane.showMessageDialog(null, "Error 9");
		return -1;
	}

	
	/****************************************************************************
	 * Places a player's ship once it has checked for valid location
	 * Called from placePlayerShips()
	 *
	 * @param a - the row coordinate of the ship's starting point
	 * @param b - the column coordinate of the ship's starting point
	 * @param dir - the direction the rest of the ship will go 
	 * @param shipLength - the length of the ship being placed
	 ****************************************************************************/
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

	
	/****************************************************************************
	 * Places the AI's ships at random
	 ****************************************************************************/
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
		
		JOptionPane.showMessageDialog(null, "The AI's ships have been placed!", "Game Setup",
				JOptionPane.INFORMATION_MESSAGE, shipsPlacedIcon);

		// displaying AI ship placement
		displayAIShips();
	}
	
	
	/*
	 * 
	 * 
	 */
	
	
	
	
	/*
	 * 
	 * 
	 * 
	 */

	
	/****************************************************************************
	 * Displays the 2D integer array that shows the player's ship locations
	 * 2 = Destroyer, 3 = Submarine, 4 = Battleship, 5 = Carrier, 6 = Cruiser
	 * 0 = open water
	 ****************************************************************************/
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

	
	/****************************************************************************
	 * Displays the 2D integer array that shows the AI's ship locations
	 * 2 = Destroyer, 3 = Submarine, 4 = Battleship, 5 = Carrier, 6 = Cruiser
	 * 0 = open water
	 ****************************************************************************/
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

	
	/****************************************************************************
	 * Displays the 2D integer array that shows the player's shots taken
	 * 0 = open water, 1 = miss, 2 = hit
	 ****************************************************************************/
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

	
	/****************************************************************************
	 * Displays the 2D integer array that shows the AI's shots taken
	 * 0 = open water, 1 = miss, 2 = hit
	 ****************************************************************************/
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


	/****************************************************************************
	 * Getter method for rows on the grid
	 *
	 * @return number of rows on the grid
	 ****************************************************************************/
	public int getRows() {
		return totalRows;
	}


	/****************************************************************************
	 * Getter method for columns on the grid
	 *
	 * @return number of columns on the grid
	 ****************************************************************************/
	public int getCols() {
		return totalCols;
	}


}
