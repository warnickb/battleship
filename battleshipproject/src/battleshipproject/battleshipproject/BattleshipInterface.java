package battleshipproject;

/********************************************************************************
 * Battleship Game Logic Interface
 * 
 * @author Justin Perticone
 * @version December 5, 2018
 ********************************************************************************/
public interface BattleshipInterface {

	/****************************************************************************
	 * Determines if coordinates are valid to fire to
	 * Only used by the AI
	 *
	 * @param row_pos - row value
	 * @param col_pos - column value
	 * @return true if location is valid, false otherwise
	 ****************************************************************************/
	public boolean checkFire(int row_pos, int col_pos);


	/****************************************************************************
	 * Determines if the shot fired is a hit or a miss
	 * AI updates smartShots and shipsRemembered arrays within this method
	 *
	 * @param row_pos - row value
	 * @param col_pos - column value
	 * @return true if the shot is a hit, false if it is a miss
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
	 * Resets the game
	 ****************************************************************************/
	public void reset();
	
	
	/****************************************************************************
	 * Gives the player information regarding the selected difficulty
	 ****************************************************************************/
	public void difficultyInfo();


	/****************************************************************************
	 * Allows the player to place their ships 
	 ****************************************************************************/
	public void placePlayerShips();


	/****************************************************************************
	 * Used to auto place the player's ships 
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
