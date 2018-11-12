package battleshipproject;





public interface BattleshipInterface {

	/**
	 * Determines if location of fire is valid
	 *
	 * @param row_pos
	 * @param col_pos
	 * @return true when location is valid, otherwise false because
	 * 	1) the location is out of bounds
	 * 	2) the location has already received fire
	 */
	public boolean shotFired(int row_pos, int col_pos);



	/**
	 * Returns whether it is the player's turn or the AI's turn
	 *
	 * @return 0 = player, 1 = AI
	 */
	public int currentPlayer();



	/**
	 * Returns the ID of the winning player
	 *
	 * @return 0 = player, 1 = AI, -1 = winner currently undetermined
	 */
	public void getWinner();


	/**
	 * Reset the game
	 */
	public void reset();


	/**
	 *	FIXME TWO METHODS TO PLACE SHIPS NOW
	 *
	 */
	public void placePlayerShips();

	/**
	 *
	 *
	 *
	 */
	public boolean validLetter(String letter);

	/**
	 *
	 *
	 */
	public boolean validNumber(int number);

	/**
	 *
	 *
	 *
	 */
	public int convertLetterInput(String letter);

	/**
	 *
	 *
	 *
	 */
	public void placeShip(int a, int b, String dir, int shipLength);

	/**
	 *
	 *
	 */
	public void placeAIShips();


	// FIXME MUST ADD TO THE GRID DISPLAYERS IN HERE
	/**
	 *
	 *
	 */
	public void displayPlayerShips();
	
	/**
	 *
	 *
	 */
	public void displayAIShips();
	
	/**
	 *
	 *
	 */
	public void displayPlayerGrid();
	
	/**
	 *
	 *
	 */
	public void displayAIGrid();


	/**
	 * Getter method for rows on the grid
	 *
	 * @return number of rows on the grid
	 */
	public int getRows();

	/**
	 * Getter method for columns on the grid
	 *
	 * @return number of columns on the grid
	 */
	public int getCols();

	/**
	 * 
	 *
	 */





}
