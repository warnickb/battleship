package battleshipproject;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**************************************************************************************
 * JUnit test class for Battleship back end logic
 * Must be ran with the version of the logic class that auto places player ships
 * Player ships are placed in specific pattern
 *
 * @author Justin Perticone
 * @version December 7, 2018
 **************************************************************************************/
public class LogicTesting {
		
	/* instance of our game */
	private BattleshipLogicForGUI g;
	
	
	@Before 
	public void setup() {
		g = new BattleshipLogicForGUI();
	}
	

	@Test 
	public void resetShouldSetupGame() {
		
		assertEquals(8, g.getRows());
		assertEquals(8, g.getCols());
		assertEquals(64, g.gridSize);
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
	

	@Test
	public void checkFirePreventsRepeatShotsForAI() {
		
		g.shotFired(0, 0);
		assertTrue(g.checkFire(2, 3));
		g.shotFired(2, 3);
		g.shotFired(0, 1);
		assertFalse(g.checkFire(2, 3));
		assertTrue(g.checkFire(4, 5));
		g.shotFired(4, 5);
			
	}
	
	
	@Test
	public void shotFiredUpdatesCurrentPlayer() {
		
		assertEquals(1, g.currentPlayer());
		g.shotFired(0, 0);
		assertEquals(2, g.currentPlayer());
		g.shotFired(0, 0);
		assertEquals(1, g.currentPlayer());
		g.shotFired(0, 1);
		assertEquals(2, g.currentPlayer());
		
	}
	
	
	@Test
	public void shotFiredUpdatesHits() {
		
		assertEquals(0, g.AIhits);
		g.shotFired(0, 0);
		g.shotFired(2, 3);
		assertEquals(1, g.AIhits);
		g.shotFired(0, 1);
		g.shotFired(3, 3);
		assertEquals(2, g.AIhits);
		
	}
	
	
	@Test
	public void shotFiredUpdatesMisses() {
		
		assertEquals(0, g.AImisses);
		g.shotFired(0, 0);
		g.shotFired(0, 0);
		assertEquals(1, g.AImisses);
		g.shotFired(0, 1);
		g.shotFired(0, 1);
		assertEquals(2, g.AImisses);
		
	}
	
	
	@Test
	public void shotFiredEnablesSmartShooting() {
		
		assertFalse("smartshooting should be disabled at start", g.smartShooting);
		g.shotFired(0, 0);
		g.shotFired(0, 0);
		assertFalse("miss should not enable smartshooting", g.smartShooting);
		g.shotFired(0, 1);
		g.shotFired(3, 3);
		assertTrue("hit should enable smartshooting", g.smartShooting);
		
	}
	
	
	@Test
	public void shotFiredDisablesSmartShooting() {
		
		g.shotFired(0, 0);
		g.shotFired(0, 0);
		assertFalse("miss should not enable smartshooting", g.smartShooting);
		g.shotFired(0, 1);
		g.shotFired(3, 3);
		assertTrue("hit should enable smartshooting", g.smartShooting);
		g.shotFired(0, 2);
		g.shotFired(2, 3);
		assertFalse("sink should disable smartshooting", g.smartShooting);
		
	}
	
	
	@Test
	public void shotFiredUpdatesSmartHits() {
		
		assertEquals(0, g.smartHits);
		g.shotFired(0, 0);
		g.shotFired(1, 1);
		assertEquals(1, g.smartHits);
		g.shotFired(0, 1);
		g.shotFired(0, 0);
		assertEquals(1, g.smartHits);
		g.shotFired(0, 2);
		g.shotFired(1, 2);
		assertEquals(2, g.smartHits);
		g.shotFired(0, 2);
		g.shotFired(1, 3);
		assertEquals(3, g.smartHits);
		
	}
	
	
	@Test
	public void aiSinkingShipResetsSmartHits() {
		
		assertEquals(0, g.smartHits);
		g.shotFired(0, 0);
		g.shotFired(2, 1);
		assertEquals(1, g.smartHits);
		g.shotFired(0, 1);
		g.shotFired(3, 1);
		assertEquals(2, g.smartHits);
		g.shotFired(0, 2);
		g.shotFired(4, 1);
		assertEquals(0, g.smartHits);
		
	}
	
	
	@Test
	public void shotFiredUpdatesSmartShots() {
		
		g.shotFired(0, 0);
		g.shotFired(2, 1);
		// left, right, above, below
		assertEquals("20", g.smartShots[0]);
		assertEquals("22", g.smartShots[1]);
		assertEquals("11", g.smartShots[2]);
		assertEquals("31", g.smartShots[3]);
		
	}
	
	
	@Test
	public void markAIShipsHitDuringSmartShooting() {
		
		g.shotFired(0, 1);
		g.shotFired(2, 1);
		g.shotFired(0, 0);
		g.shotFired(1, 1);
		assertEquals(5, g.shipsRemembered[0]);
		assertEquals("11", g.rememberOtherHit);
		
	}
	
	
	@Test
	public void rememberShipFromListOfOne() {
		
		// first turn
		g.shotFired(0, 1);
		g.shotFired(1, 1);
		// second turn
		g.shotFired(0, 0);
		// second ship hit
		g.shotFired(2, 1);
		assertEquals(3, g.shipsRemembered[0]);
		assertEquals("21", g.rememberOtherHit);
		// third turn
		g.shotFired(0, 2);
		g.shotFired(1, 2);
		// fourth turn
		g.shotFired(0, 3);
		g.shotFired(1, 3);
		// fifth turn
		g.shotFired(0, 4);
		g.shotFired(1, 4);
		// sixth turn, player carrier sunk
		g.shotFired(0, 5);
		g.shotFired(1, 5);
		assertEquals(0, g.shipsRemembered[0]);
		assertEquals("", g.rememberOtherHit);
		assertEquals("20", g.smartShots[0]);
		assertEquals("22", g.smartShots[1]);
		assertEquals("11", g.smartShots[2]);
		assertEquals("31", g.smartShots[3]);
		
	}
	
	
	@Test
	public void rememberShipFromListOfThree() {
		
		// impossible shooting pattern but still effectively tests
		// AI algorithm
		
		// first turn
		g.shotFired(0, 1);
		g.shotFired(1, 1);
		// second turn
		g.shotFired(0, 0);
		// second ship hit, first remembered
		g.shotFired(2, 1);
		// third turn
		g.shotFired(1, 1);
		// third ship hit, second remembered
		g.shotFired(2, 3);
		// fourth turn
		g.shotFired(1, 2);
		// fourth ship hit, third remembered
		g.shotFired(7, 5);
		assertEquals(3, g.shipsRemembered[0]);
		assertEquals("21", g.rememberOtherHit);
		//assertEquals(2, g.shipsRemembered[1]);
		assertEquals("23", g.rememberOtherHit2);
		//assertEquals(6, g.shipsRemembered[2]);
		assertEquals("75", g.rememberOtherHit3);
		// sinking carrier
		g.shotFired(0, 2);
		g.shotFired(1, 2);
		g.shotFired(0, 3);
		g.shotFired(1, 3);
		g.shotFired(0, 4);
		g.shotFired(1, 4);
		g.shotFired(0, 5);
		g.shotFired(1, 5);
		// checking statuses
		//assertEquals(2, g.shipsRemembered[0]);
		assertEquals("23", g.rememberOtherHit);
		//assertEquals(6, g.shipsRemembered[1]);
		assertEquals("75", g.rememberOtherHit2);
		//assertEquals(0, g.shipsRemembered[2]);
		assertEquals("", g.rememberOtherHit3);
		assertEquals("20", g.smartShots[0]);
		assertEquals("22", g.smartShots[1]);
		assertEquals("11", g.smartShots[2]);
		assertEquals("31", g.smartShots[3]);
		// sinking submarine
		g.shotFired(2, 0);
		g.shotFired(3, 1);
		g.shotFired(2, 1);
		g.shotFired(4, 1);
		// checking statuses
		//assertEquals(6, g.shipsRemembered[0]);
		assertEquals("75", g.rememberOtherHit);
		//assertEquals(0, g.shipsRemembered[1]);
		assertEquals("", g.rememberOtherHit2);
		assertEquals("22", g.smartShots[0]);
		assertEquals("24", g.smartShots[1]);
		assertEquals("13", g.smartShots[2]);
		assertEquals("33", g.smartShots[3]);
		// sinking destroyer
		g.shotFired(3, 0);
		g.shotFired(3, 3);
		// checking statuses
		//assertEquals(0, g.shipsRemembered[0]);
		assertEquals("", g.rememberOtherHit);
		//assertEquals(0, g.shipsRemembered[1]);
		assertEquals("", g.rememberOtherHit2);
		assertEquals("74", g.smartShots[0]);
		assertEquals("76", g.smartShots[1]);
		assertEquals("65", g.smartShots[2]);
		assertEquals("XX", g.smartShots[3]);
		
	}


	@Test
	public void testValidLetters() {
		
		String valids = "ABCDEFGH";
		for(int i = 0; i < valids.length(); i++)
			assertTrue("ABCDEFGH are valid letters", 
					g.validLetter(valids.substring(i, i+1)));
		
	}
	
	
	@Test 
	public void testInvalidLetters() {
		
		String valids = "IJKLMNOPQRSTUVWXZY0123456789";
		for(int i = 0; i < valids.length(); i++)
			assertFalse("letters I-Z and digits 0-9 are invalid letters", 
					g.validLetter(valids.substring(i, i+1)));
		
	}
	
	
	@Test
	public void testValidNumbers() {
		
		for(int i = 0; i < 8; i++) 
			assertTrue("0-7 are valid numbers", g.validNumber(i));
		
	}
	
	
	@Test
	public void testInvalidNumbers() { 
		
		for(int i = -10; i < 0; i++)
			assertFalse("-10 (and below) through -1 are invalid numbers", g.validNumber(i));
		
		for(int i = 8; i < 101; i++)
			assertFalse("8-100 and above are invalid numbers", g.validNumber(i));
		
	}
	

	@Test
	public void convertAto0() {
		
		assertEquals(0, g.convertLetterInput("A"));
		
	}
	
	
	@Test
	public void convertBto1() {
		
		assertEquals(1, g.convertLetterInput("B"));
		
	}
	
	
	@Test
	public void convertCto2() {
		
		assertEquals(2, g.convertLetterInput("C"));
		
	}
	
	
	@Test
	public void convertDto3() {
		
		assertEquals(3, g.convertLetterInput("D"));
		
	}
	
	
	@Test
	public void convertEto4() {
		
		assertEquals(4, g.convertLetterInput("E"));
		
	}
	
	
	@Test
	public void convertFto5() {
		
		assertEquals(5, g.convertLetterInput("F"));
		
	}
	
	
	@Test
	public void convertGto6() {
		
		assertEquals(6, g.convertLetterInput("G"));
		
	}
	
	
	@Test
	public void convertHto7() {
		
		assertEquals(7, g.convertLetterInput("H"));
		
	}
	
	
	@Test
	public void AIShipsPlaceRandomly() {
		
		BattleshipLogicForGUI g2 = new BattleshipLogicForGUI();
		
		boolean different = false;
		
		for(int r = 0; r < 8; r++)
			for(int c = 0; c < 8; c++)
				if(g.AIshipLocs[r][c] == 2)
					if(g2.AIshipLocs[r][c] != 2)
						different = true;
		for(int r = 0; r < 8; r++)
			for(int c = 0; c < 8; c++)
				if(g.AIshipLocs[r][c] == 3)
					if(g2.AIshipLocs[r][c] != 3)
						different = true;
		for(int r = 0; r < 8; r++)
			for(int c = 0; c < 8; c++)
				if(g.AIshipLocs[r][c] == 6)
					if(g2.AIshipLocs[r][c] != 6)
						different = true;
		for(int r = 0; r < 8; r++)
			for(int c = 0; c < 8; c++)
				if(g.AIshipLocs[r][c] == 4)
					if(g2.AIshipLocs[r][c] != 4)
						different = true;
		for(int r = 0; r < 8; r++)
			for(int c = 0; c < 8; c++)
				if(g.AIshipLocs[r][c] == 5)
					if(g2.AIshipLocs[r][c] != 5)
						different = true;
						
		assertTrue("ships are not placed randomly", different);
		
	}
	
	
	@Test
	public void shipsDisplayWithNoErrors() {
		
		g.displayAIGrid();
		g.displayAIShips();
		g.displayPlayerGrid();
		g.displayPlayerShips();
		
	}
	

}
