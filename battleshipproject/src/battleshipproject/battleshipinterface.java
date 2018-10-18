



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
	public boolean select(int row_pos, int col_pos);



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
	public int getWinner();


	/**
	 * Reset the game
	 */
	public void reset();


	/**
	 *
	 *
	 */
	public void placeShips();


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
