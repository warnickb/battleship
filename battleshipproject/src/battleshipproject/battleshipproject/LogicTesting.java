package battleshipproject;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * 
 * 
 * @author Justin Perticone
 * @version December 12, 2018
 */
public class LogicTesting {
		
	private BattleshipLogicForGUI g;
	
	
	@Before // may need an if else of sort s for placing player ships
	public void setup() {
		g = new BattleshipLogicForGUI();
	}
	
	// test reset
	@Test // requires user input, may cause an issue
	public void resetShouldSetupGame() {
		
		assertEquals(8, g.getRows());
		assertEquals(8, g.getCols());
		assertEquals(1, g.currentPlayer());
		assertEquals(0, g.playerHits + g.playerMisses);
		assertEquals(0, g.AIhits + g.AImisses);
		assertFalse("smartshooting should be disabled from start", g.smartShooting);
		assertEquals(0, g.smartHits + g.brutalHits);
		
		int playerShots = 0;
		for(int r = 0; r < g.getRows(); r++)
			for(int c = 0; c < g.getRows(); c++)
				if(g.grid[r][c] >= 2)
					playerShots++;
		assertEquals(0, playerShots);
		int playerShips = 0;
		for(int r = 0; r < g.getRows(); r++)
			for(int c = 0; c < g.getCols(); c++)
				if(g.shipLocs[r][c] >= 2)
					playerShips++;
		assertEquals(17, playerShips);
		
		int aiShots = 0;
		for(int r = 0; r < g.getRows(); r++)
			for(int c = 0; c < g.getRows(); c++)
				if(g.AIgrid[r][c] >= 2)
					playerShots++;
		assertEquals(0, aiShots);
		int aiShips = 0;
		for(int r = 0; r < g.getRows(); r++)
			for(int c = 0; c < g.getCols(); c++)
				if(g.AIshipLocs[r][c] >= 2)
					aiShips++;
		assertEquals(17, aiShips);
	}
	
	@Test
	public void correctInitialAIArrays() {
		
		boolean checker = true;
		for(int i = 0; i < g.smartShots.length; i++) {
			if(!g.smartShots[i].equals("XX")) {
				checker = false;
			}
		}
		assertEquals(true, checker);
		
		checker = true;
		if(g.allShipsStatus[0] != 2 || g.allShipsStatus[5] != 2)
			checker = false;
		if(g.allShipsStatus[1] != 3 || g.allShipsStatus[6] != 3)
			checker = false;
		if(g.allShipsStatus[2] != 3 || g.allShipsStatus[7] != 3)
			checker = false;
		if(g.allShipsStatus[3] != 4 || g.allShipsStatus[8] != 4)
			checker = false;
		if(g.allShipsStatus[4] != 5 || g.allShipsStatus[9] != 5)
			checker = false;
		assertEquals(true, checker);
		
		checker = true;
		if(!g.allShipNames[0].equals("Destroyer"))
			checker = false;
		if(!g.allShipNames[1].equals("Submarine"))
			checker = false;
		if(!g.allShipNames[2].equals("Cruiser"))
			checker = false;
		if(!g.allShipNames[3].equals("Battleship"))
			checker = false;
		if(!g.allShipNames[4].equals("Carrier"))
			checker = false;
		assertEquals(true, checker);
		
		checker = true;
		for(int i = 0; i < g.shipsRemembered.length; i++) {
			if(g.shipsRemembered[i] != 0) {
				checker = false;
			}
		}
		assertEquals(true, checker);	
		
	}
	
	// test checkfire - not responsible for out of bounds shots
	@Test
	public void checkFirePreventsRepeatShots() {
		
		// check fire
		// check fire to same as prev 
		// shot fired
		// check fire to prev shot fired
		// check fire to new area
		
		// duplicate each for ai
		
		
	}
	
	
	// test shotFired - update current player, update hits, update misses,
	// enable smartshooting
	@Test
	public void shotFiredUpdatesCurrentPlayer() {
		
		
		
	}
	
	@Test
	public void shotFiredUpdatesHits() {
		
		
		
	}
	
	@Test
	public void shotFiredUpdatesMisses() {
		
		
		
	}
	
	@Test
	public void shotFiredEnablesSmartShooting() {
		
		
		
	}
	
	@Test
	public void shotFiredDisablesSmartShooting() {
		
		
		
	}
	
	@Test
	public void shotFiredUpdatesSmartHits() {
		
		
		
	}
	
	@Test
	public void shotFiredUpdatesSmartShots() {
		
		
		
	}
	
	
	// test remembership
	@Test
	public void markAIShipsHitDuringSmartShooting() {
		
		
		
	}
	
	
	@Test
	public void rememberShipFromListOfOne() {
		
		
		
	}
	
	
	@Test
	public void rememberShipFromListOfTwo() {
		
		
		
	}
	
	
	@Test
	public void rememberShipFromListOfThree() {
		
		
		
	}
	
	// test sunkstatus - will have to check for negative one in checking
	// check health status at each hit for all 
	@Test 
	public void playerDestroyerSunk() {
		
		
		
	}
	
	
	@Test 
	public void AIDestroyerSunk() {
		
		
		
	}
	
	
	@Test 
	public void playerSubmarineSunk() {
		
		
		
	}
	
	
	@Test 
	public void AISubmarineSunk() {
		
		
		
	}
	
	
	
	@Test 
	public void playerCruiserSunk() {
		
		
		
	}
	
	
	@Test 
	public void AICruiserSunk() {
		
		
		
	}
	
	
	
	@Test 
	public void playerBattleshipSunk() {
		
		
		
	}
	
	
	@Test 
	public void AIBattleshipSunk() {
		
		
		
	}
	
	
	
	@Test 
	public void playerCarrierSunk() {
		
		
		
	}
	
	
	@Test 
	public void AICarrierSunk() {
		
		
		
	}
	
	
	// test currentPlayer
	@Test
	public void currentPlayerUpdatesCorrectly() {
		
		// starts at 1
		// after first shot, updates to 2 
		// after second shot, updates to 1
		// after next shot, updates to 2
		// after next shot, updates to 1d
		
	}
	
	
	// test getwinner - no way to test currently, could reset values,
	// add a game one boolean to update ai arrays
	
	
	
	// test placeplayerships - 
	@Test
	public void playerDestroyerPlaced() { 
		
		
		
	}
	
	@Test
	public void playerSubmarinePlaced() { 
		
		
		
	}
	
	@Test
	public void playerCruiserPlaced() { 
		
		
		
	}
	
	@Test
	public void playerBattleshipPlaced() { 
		
		
		
	}
	
	@Test
	public void playerCarrierPlaced() { 
		
		
		
	}
	
	
	
	// test presentationdemo
	@Test
	public void allShipsPlacedCorrectlyInDemo() { 
		
		
		
	}
	
	
	// test validletter
	@Test
	public void testValidLetters() {
		
		
	}
	
	
	@Test 
	public void testInvalidLetters() {
		
		
		
	}
	
	
	
	// test validnumber
	@Test
	public void testValidNumbers() {
		
		
		
	}
	
	@Test
	public void testInvalidNumbers() { // up to 100
		
		
	}
	
	
	
	
	// test convertletterinput
	@Test
	public void convertAto0() {
		
		
	}
	
	@Test
	public void convertBto1() {
		
		
	}
	
	@Test
	public void convertCto2() {
		
		
	}
	
	@Test
	public void convertDto3() {
		
		
	}
	
	@Test
	public void convertEto4() {
		
		
	}
	
	@Test
	public void convertFto5() {
		
		
	}
	
	@Test
	public void convertGto6() {
		
		
	}
	
	@Test
	public void convertHto7() {
		
		
	}
	
	
	
	// test placeship - this gets tested within placeplayerships
	
	
	
	// test placeaiships
	@Test
	public void AIDestroyerPlaced() { 
		
		
	}
	
	@Test
	public void AISubmarinePlaced() { 
		
		
	}
	
	@Test
	public void AICruiserPlaced() { 
		
		
	}
	
	@Test
	public void AIBattleshipPlaced() { 
		
		
	}
	
	@Test
	public void AICarrierPlaced() { 
		
		
	}
	
	
	// test displayplayerships ?? - no need to test the displays
	
	
	
	// test displayaiships 
	
	
	
	// test displayplayergrid
	
	
	
	// test displayaigrid
	

}
