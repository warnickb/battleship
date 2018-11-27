//package battleshipproject;

/********************************************************************************
 * Battleship Game Logic Interface
 * 
 * @author Justin Perticone
 * @version November 26, 2018
 ********************************************************************************/

public interface BattleshipInterface {

	/****************************************************************************
	 * Determines if location of fire is valid
	 *
	 * @param row_pos - row value
	 * @param col_pos - column value
	 * @return true when location is valid, otherwise false because
	 * 	1) the location is out of bounds
	 * 	2) the location has already received fire
	 ****************************************************************************/
	public boolean shotFired(int row_pos, int col_pos);
	

	/****************************************************************************
	 * Used to enhance the AI
	 * Remembers any ships hit while working to sink the ship
	 * it first hit
	 * Remembers up to three ships on top of the ship initially hit
	 ****************************************************************************/
	public void rememberShip();


	/****************************************************************************
	 * Checks on the status of all ships to see if any have been sunk
	 * Method is called after every hit by both the player and the AI
	 *
	 * @param shipHit - the value used to uniquely identify each ship
	 * @param player - 1 = player, 2 = AI
	 * @return the name of the ship sunk, empty string if no ship sunk
	 ****************************************************************************/
	public String sunkStatus(int shipHit, int player);


	/****************************************************************************
	 * Returns whether it is the player's turn or the AI's turn
	 *
	 * @return 1 = player, 2 = AI
	 ****************************************************************************/
	public int currentPlayer();


	/****************************************************************************
	 * Method executed once all ships have been sunk on one side
	 * Asks user if they would like to play again - FIXME
	 ****************************************************************************/
	public void getWinner();


	/****************************************************************************
	 * Resets the game
	 ****************************************************************************/
	public void reset();

	
	/****************************************************************************
	 * Plays out an entire game of Battleship until there is a winner
	 ****************************************************************************/
	public void playGame();

	
	/****************************************************************************
	 * Used in the testing version only 
	 * Simulates a game where only the AI can fire
	 * Able to analyze the "thought process" of the AI
	 ****************************************************************************/
	public void AIonly();


	/****************************************************************************
	 * Allows the player to place their ships 
	 ****************************************************************************/
	public void placePlayerShips();


	/****************************************************************************
	 * Used specifically to demo the project in class
	 ****************************************************************************/
	public void presentationDemo();

	
	/****************************************************************************
	 * Checks to see if the player's letter input for row is valid
	 *
	 * @param letter - the letter the player input for row
	 * @return true if valid, false otherwise
	 ****************************************************************************/
	public boolean validLetter(String letter);

	
	/****************************************************************************
	 * Checks to see if the player's number input for column is valid
	 *
	 * @param number - the number the player input for column
	 * @return true if valid, false otherwise
	 ****************************************************************************/
	public boolean validNumber(int number);

	
	/****************************************************************************
	 * Converts valid player letter input into the correct number value
	 * Number value needed to access the 2D array properly
	 *
	 * @param letter - the number the player input for row
	 * @return the corresponding number value for letter entered
	 ****************************************************************************/
	public int convertLetterInput(String letter);

	
	/****************************************************************************
	 * Places a player's ship once it has checked for valid location
	 * Called from placePlayerShips()
	 *
	 * @param a - the row coordinate of the ship's starting point
	 * @param b - the column coordinate of the ship's starting point
	 * @param dir - the direction the rest of the ship will go 
	 * @param shipLength - the length of the ship being placed
	 ****************************************************************************/
	public void placeShip(int a, int b, String dir, int shipLength);

	
	/****************************************************************************
	 * Places the AI's ships at random
	 ****************************************************************************/
	public void placeAIShips();


	/****************************************************************************
	 * Displays the 2D integer array that shows the player's ship locations
	 * 2 = Destroyer, 3 = Submarine, 4 = Battleship, 5 = Carrier, 6 = Cruiser
	 * 0 = open water
	 ****************************************************************************/
	public void displayPlayerShips();
	
	
	/****************************************************************************
	 * Displays the 2D integer array that shows the AI's ship locations
	 * 2 = Destroyer, 3 = Submarine, 4 = Battleship, 5 = Carrier, 6 = Cruiser
	 * 0 = open water
	 ****************************************************************************/
	public void displayAIShips();
	
	
	/****************************************************************************
	 * Displays the 2D integer array that shows the player's shots taken
	 * 0 = open water, 1 = miss, 2 = hit
	 ****************************************************************************/
	public void displayPlayerGrid();
	
	
	/****************************************************************************
	 * Displays the 2D integer array that shows the AI's shots taken
	 * 0 = open water, 1 = miss, 2 = hit
	 ****************************************************************************/
	public void displayAIGrid();


	/****************************************************************************
	 * Getter method for rows on the grid
	 *
	 * @return number of rows on the grid
	 ****************************************************************************/
	public int getRows();

	
	/****************************************************************************
	 * Getter method for columns on the grid
	 *
	 * @return number of columns on the grid
	 ****************************************************************************/
	public int getCols();

}
