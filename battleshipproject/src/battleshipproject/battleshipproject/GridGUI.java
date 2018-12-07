package battleshipproject;

import java.awt.Color; 
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.*;
import java.util.*;


/**********************************************************************
 * Primary GUI for Battleship Project
 *
 * @author Justin Perticone
 * @author Brendan Warnick
 * @version December 5, 2018
 **********************************************************************/
public class GridGUI extends JFrame {
	
	/** Battleship Game object */
	BattleshipLogicForGUI game;
	
	/** buttons for the game grids */
	JButton[][] enemyBoardButton;
	JButton[][] playerBoardButton;
	
	/*** JButtons used by GUI ***/
	/** scores display firing accuracy */
	JButton score;
	JButton turnNum;
	JButton hits;
	JButton misses;
	JButton enemyScore;
	JButton enemyHits;
	JButton enemyMisses;
	/** hit streak is the number of consecutive shots hit */
	JButton hitStreak;
	JButton bestHitStreak;
	/** timer times how long the game is */
	JButton timer;
	JButton enemyHitStreak;
	JButton enemyBestHitStreak;
	/** sunk buttons change color to indicate ship is sunk */
	JButton playerSunk;
	JButton sunkDes;
	JButton sunkSub;
	JButton sunkCru;
	JButton sunkBat;
	JButton sunkCar;
	JButton enemySunk;
	JButton enemySunkDes;
	JButton enemySunkSub;
	JButton enemySunkCru;
	JButton enemySunkBat;
	JButton enemySunkCar;
	
	/** water = default color of grid buttons */
	Color water;
	/** hit = color of a hit ship */
	Color hit;
	/** sunk = color of a sunk ship */
	Color sunk;
	/** ship = color of player ships on bottom grid */ 
	Color ship;
	/** checkmark = color of sunk ship on checklist */
	Color checkmark;
	/** yellow = color of some informational buttons */
	Color yellow;
	/** orange = color of winner's ships on checklist post game */
	Color orange;
	
	/** using grid layout */
	GridLayout myLayout;
	
	/** action listener for GUI */
	MyButtonHandler myHandler;
	
	/** GUI container */
	JPanel pane;
	JFrame frame;
	
	/** MenuBar of GUI */
	JMenuBar menuBar;
	
	/** JMenus of GUI */
	JMenu file;
	JMenu stats;
	JMenu information;

	/*** JMenuItems of GUI ***/
	/** saves current game - not functional */
	JMenuItem saveItem;
	/** loads a saved game - not functional */
	JMenuItem loadItem;
	/** takes user to main menu */
	JMenuItem newItem;
	/** exits game */
	JMenuItem quitItem;
	/** displays current game statistics */
	JMenuItem currStatsItem;
	/** displays the leaderboards menu - not functional */
	JMenuItem leaderboardsItem;
	/** displays the difficulty information */
	JMenuItem diffInfoItem;
	
	/*** ImageIcons of GUI ***/
	/** image displayed when the player wins */
	ImageIcon winnerIcon;
	/** image displayed when the player loses */
	ImageIcon loserIcon;
	/** image that displays with statistics */
	ImageIcon statsIcon;
	
	/** variables to calculate and display time */
	long startTime;
	long elapsedTime;
	long elapsedSeconds;
	long secondsDisplay;
	long elapsedMinutes;
	long minutesDisplay;
	
	/** variables to calculate firing accuracies */
	float accuracy;
	float enemyAccuracy;
	
	/** font used on GUI buttons */
	Font font;

	/** boolean used to remember a brutal hit is owed */
	boolean rememberBrutal;
		
	
	/****************************************************************************
	 * Default Constructor
	 ****************************************************************************/
	public GridGUI() {
		
		game = new BattleshipLogicForGUI();
		
		// setting colors to desired values
		water = new Color(200,235,250);
		hit = new Color(250,175,175);
		sunk = new Color(220,120,120);
		ship = new Color(200,200,200);
		checkmark = new Color(225,250,180);
		yellow = new Color(250,250,180);
		orange = new Color(250,235,180);
		
		// linking the proper image files with icons
		winnerIcon = new ImageIcon(GridGUI.class.getResource("fireworks.jpg"));
		loserIcon = new ImageIcon(GridGUI.class.getResource("sinking.jpg"));
		statsIcon = new ImageIcon(GridGUI.class.getResource("stats.jpg"));
		
		myHandler = new MyButtonHandler();
		
		// font chosen 
		font = new Font("Lucida Calligraphy", Font.BOLD, 16);
		
		// instantiating JButtons with text
		score = new JButton("Accuracy: ");
		turnNum = new JButton("Turn Number: 0");
		hits = new JButton("Hits: 0");
		misses = new JButton("Misses: 0");
		enemyScore = new JButton("Accuracy: ");
		enemyHits = new JButton("Enemy Hits: 0");
		enemyMisses = new JButton("Enemy Misses: 0");
		hitStreak = new JButton("Hit Streak: 0");
		bestHitStreak = new JButton("Top Hit Streak: 0");
		timer = new JButton("Time: 00:00.000");
		enemyHitStreak = new JButton("Enemy Hit Streak: 0");
		enemyBestHitStreak = new JButton("Top Hit Streak: 0");
		playerSunk = new JButton("Player Checklist:");
		sunkDes = new JButton("Destroyer");
		sunkSub = new JButton("Submarine");
		sunkCru = new JButton("Cruiser");
		sunkBat = new JButton("Battleship");
		sunkCar = new JButton("Carrier");
		enemySunk = new JButton("Enemy Checklist:");
		enemySunkDes = new JButton("Destroyer");
		enemySunkSub = new JButton("Submarine");
		enemySunkCru = new JButton("Cruiser");
		enemySunkBat = new JButton("Battleship");
		enemySunkCar = new JButton("Carrier");
		
		// game timer
		startTime = System.currentTimeMillis();
		
		// AI does not need to remember a brutal hit from the start
		rememberBrutal = false;

		gridSetup();
	}
	
	
	/****************************************************************************
	 * Sets up more features of the GUI 
	 * Adds all elements to the container and makes it visible
	 ****************************************************************************/
	public void gridSetup() {

		// setting up the layout
		frame = new JFrame();
		pane = new JPanel();
		myLayout = new GridLayout((game.getRows()*2+3),(game.getCols()));
		pane.setLayout(myLayout);
		
		// initializing the JMenuBar, JMenus, and JMenuItems
		menuBar = new JMenuBar();
		file = new JMenu("File");
		stats = new JMenu("Stats");
		information = new JMenu("Information");
		saveItem = new JMenuItem("Save Game");
		loadItem = new JMenuItem("Load Game");
		newItem = new JMenuItem("New Game");
		quitItem = new JMenuItem("Exit");
		currStatsItem = new JMenuItem("Game Statistics");
		leaderboardsItem = new JMenuItem("Leaderboards");
		diffInfoItem = new JMenuItem("Difficulty Information");
		
		// setting up the JMenuBar with all the components
		newItem.addActionListener(myHandler);
		quitItem.addActionListener(myHandler);
		currStatsItem.addActionListener(myHandler);
		diffInfoItem.addActionListener(myHandler);
		file.add(saveItem);
		file.add(loadItem);
		file.add(newItem);
		file.add(quitItem);
		stats.add(currStatsItem);
		stats.add(leaderboardsItem);
		information.add(diffInfoItem);
		menuBar.add(file);
		menuBar.add(stats);
		menuBar.add(information);
		// setting the JMenuBar to our JFrame
		frame.setJMenuBar(menuBar);		
		
		// setting certain buttons to yellow color
		playerSunk.setBackground(yellow);
		enemySunk.setBackground(yellow);
		score.setBackground(yellow);
		bestHitStreak.setBackground(yellow);
		enemyScore.setBackground(yellow);
		enemyBestHitStreak.setBackground(yellow);		
	    
		// creating the grid that the player will fire on
	    Dimension dim = new Dimension(80, 80);
		enemyBoardButton = new JButton[game.getRows()][game.getCols()];
		for(int row = 0; row < game.getRows(); row++) {
		    for(int col = 0; col < game.getCols(); col++) {    	
		    	enemyBoardButton[row][col] = new JButton(" ");
			   	enemyBoardButton[row][col].addActionListener(myHandler);
			   	enemyBoardButton[row][col].setBackground(water);
			   	enemyBoardButton[row][col].setPreferredSize(dim);
			   	pane.add(enemyBoardButton[row][col]);
		   	}
	    }
		
		// adding top row of informational buttons
		pane.add(playerSunk);
	    pane.add(sunkDes);
	    pane.add(sunkSub);
	    pane.add(sunkCru);
	    pane.add(sunkBat);
	    pane.add(sunkCar);
	    pane.add(score);
	    pane.add(bestHitStreak);
	   
	    // adding middle row of informational buttons
	    pane.add(hits);
	    pane.add(misses);
	    pane.add(enemyHits);
	    pane.add(enemyMisses);
	    pane.add(hitStreak);
	    pane.add(enemyHitStreak);
	    pane.add(turnNum);
	    pane.add(timer); 
	    
	    // adding bottom row of informational buttons
	    pane.add(enemySunk);
	    pane.add(enemySunkDes);
	    pane.add(enemySunkSub);
	    pane.add(enemySunkCru);
	    pane.add(enemySunkBat);
	    pane.add(enemySunkCar);
	    pane.add(enemyScore);
	    pane.add(enemyBestHitStreak);
	     
	    // setting the font of all buttons to our custom font
	    hits.setFont(font);
	    misses.setFont(font);
	    enemyHits.setFont(font);
	    enemyMisses.setFont(font);
	    hitStreak.setFont(font);
	    enemyHitStreak.setFont(font);
	    turnNum.setFont(font);
	    timer.setFont(font);
	    score.setFont(font);
	    bestHitStreak.setFont(font);
	    enemyScore.setFont(font);
	    enemyBestHitStreak.setFont(font);
	    playerSunk.setFont(font);
		sunkDes.setFont(font);
		sunkSub.setFont(font);
		sunkCru.setFont(font);
		sunkBat.setFont(font);
		sunkCar.setFont(font);
		enemySunk.setFont(font);
		enemySunkDes.setFont(font);
		enemySunkSub.setFont(font);
		enemySunkCru.setFont(font);
		enemySunkBat.setFont(font);
		enemySunkCar.setFont(font);
		
		// creating the grid for the player's ships
		// also the grid the enemy will fire on
	    playerBoardButton = new JButton[game.getRows()][game.getCols()];
	    for(int row = 0; row < game.getRows(); row++) {
		    for(int col = 0; col < game.getCols(); col++) {
			    playerBoardButton[row][col] = new JButton(" ");
			    playerBoardButton[row][col].addActionListener(myHandler);
			    playerBoardButton[row][col].setBackground(water);
			    playerBoardButton[row][col].setPreferredSize(dim);
			    pane.add(playerBoardButton[row][col]);
			    // user cannot click these buttons
			    playerBoardButton[row][col].setEnabled(false);
		    }
	    }
	    
	    // altering buttons to display player's ship placement on bottom grid
	    for(int row = 0; row < game.getRows(); row++) {
	    	for(int col = 0; col < game.getCols(); col++) {
	    		if(game.shipLocs[row][col] >= 2) {
	    			// changing button color to a gray color 
	    			playerBoardButton[row][col].setBackground(ship);
	    			// displaying ship designated number for identification
	    			playerBoardButton[row][col].setFont(font);
	    			playerBoardButton[row][col].setText(String.valueOf(game.shipLocs[row][col]));
	    		}
	    	}
	    }
	    
	    // disabling all informational JButtons
	    score.setEnabled(false);
	    hits.setEnabled(false);
	    misses.setEnabled(false);
	    enemyScore.setEnabled(false);
	    enemyHits.setEnabled(false);
	    enemyMisses.setEnabled(false);
	    turnNum.setEnabled(false);
	    playerSunk.setEnabled(false);
	    sunkDes.setEnabled(false);
	    sunkSub.setEnabled(false);
	    sunkCru.setEnabled(false);
	    sunkBat.setEnabled(false);
	    sunkCar.setEnabled(false);
	    enemySunk.setEnabled(false);
	    enemySunkDes.setEnabled(false);
	    enemySunkSub.setEnabled(false);
	    enemySunkCru.setEnabled(false);
	    enemySunkBat.setEnabled(false);
	    enemySunkCar.setEnabled(false);
	    hitStreak.setEnabled(false);
	    bestHitStreak.setEnabled(false);
	    timer.setEnabled(false);
	    enemyHitStreak.setEnabled(false);
	    enemyBestHitStreak.setEnabled(false);
	    
	    // adding components to the JFrame, titling the JFrame
	    frame.getContentPane().add(pane);
	    frame.setTitle("Classic Battleship: " + game.difficulty + " Difficulty");
	    frame.setLocationRelativeTo(null);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    // setting size and position of JFrame, setting visible
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    frame.pack();
	    frame.setBounds(0,0, screenSize.width, screenSize.height);
	    frame.setSize(screenSize.width, screenSize.height-45);
	    frame.setVisible(true);
	    
	}
	
	
	/****************************************************************************
	 * ActionListener used by the GUI
	 * 
	 * @author Justin Perticone
	 * @author Brendan Warnick
	 * @version December 5, 2018
	 ****************************************************************************/
	private class MyButtonHandler implements ActionListener {

		/************************************************************************
		 * Method handles all actions performed within the GUI
		 ************************************************************************/
		public void actionPerformed(ActionEvent e) {

			// source of action
			Object which = e.getSource();
			
			// used to generate random numbers for the AI
			Random rand = new Random();
			
			// String used to display game statistics
			// slight moderation depending on set difficulty 
			String gameStatistics = "";
			if(game.difficulty.equals("Brutal"))
				gameStatistics = "Brutal Hits: " + game.brutalHits + "\n";
			else {
				gameStatistics = hits.getText() + "\n";
				gameStatistics += enemyHits.getText() + "\n";
			}
			gameStatistics += bestHitStreak.getText() + "\n";
			gameStatistics += enemyHitStreak.getText() + "\n";
			gameStatistics += score.getText() + "\n";
			gameStatistics += "Enemy: " + enemyScore.getText() + "\n";
			gameStatistics += turnNum.getText() + "\n";
			gameStatistics += timer.getText();
			
			// current time length of game 
			elapsedTime = System.currentTimeMillis() - startTime;
			
			// display the current game stats to the player
			if(which == currStatsItem) {
				JOptionPane.showMessageDialog(null, gameStatistics, "Current Game Stats",
						JOptionPane.INFORMATION_MESSAGE, statsIcon);
			}
			// exit the program
			else if(which == quitItem ) {
				System.exit(1);
			}
			// end current game and go to main menu
			else if(which == newItem) {
				int confirmation = JOptionPane.showConfirmDialog(null, "End this game to " +
						"begin a new game?", null, JOptionPane.YES_NO_OPTION);
				if(confirmation == JOptionPane.YES_OPTION) { 
					frame.dispose();
					new GUI();
				}
			}
			// display the selected difficulty information to the player
			else if(which == diffInfoItem) {
				game.difficultyInfo();
			}
			
			// a shot has been fired by the player
			else {
				
				// searching for where shot was fired
				for(int r = 0; r < game.getRows(); r++) {
					for(int c = 0; c < game.getCols(); c++) {
						if(enemyBoardButton[r][c] == which) {
							// found the coordinates of shot taken
							if(game.shotFired(r, c)) {
								// shot fired was a hit, update button color and hits
								enemyBoardButton[r][c].setBackground(hit);
								hits.setText("Hits: " + game.playerHits);
							}
							else {
								// shot fired was a miss, update button color and misses
								enemyBoardButton[r][c].setBackground(Color.WHITE);
								misses.setText("Misses: " + game.playerMisses);	
							}
							// update hit streak
							hitStreak.setText("Hit Streak: " + game.hitStreak);
							bestHitStreak.setText("Top Hit Streak: " + game.bestStreak);
							// disable button to avoid player refiring to same coordinate
							enemyBoardButton[r][c].setEnabled(false);
							
							// update the game time 
							elapsedSeconds = elapsedTime / 1000;
							secondsDisplay = elapsedSeconds % 60;
							elapsedMinutes = elapsedSeconds / 60;
							minutesDisplay = elapsedMinutes % 60;
							if(minutesDisplay < 10)
								timer.setText("Time: 0" + minutesDisplay + ":" + secondsDisplay + "." + elapsedTime % 1000);
							else
								timer.setText("Time: " + minutesDisplay + ":" + secondsDisplay + "." + elapsedTime % 1000);
						} 
					}
				}

				// format to display accuracy to the tenths position
				DecimalFormat df = new DecimalFormat("0.0");
				// calculating and updating accuracy
				accuracy = ((float)game.playerHits/((float)game.playerHits+(float)game.playerMisses))* 100;
				score.setText("Accuracy: " + df.format(accuracy) + "%");
				
				
			/*************************************************Transitioning to AI Shooting*************************************************/
				
				
				int r = 0; // row that AI will fire to
				int c = 0; // column that AI will fire to
				int x = 0; // ship to randomly fire to on challenge and brutal difficulties
				boolean validShip = false;

				// 1) guarantee AI will hit a random ship during the first turn on Challenge and Brutal difficulty
				// 2) guarantee AI will hit a random ship every 5 turns on Brutal difficulty
				if((game.difficulty.equals("Challenge") && game.AIhits + game.AImisses == 0) ||
						game.difficulty.equals("Brutal")) {

					// finding a valid ship to hit
					while(!validShip) {

						// initial index offset
						int index = -1;

						// designated ship values range from 2-6
						x = rand.nextInt(5) + 2;

						// index adjustments for array in Logic class 
						if(x == 2 || x == 3)
							index = x-2;
						else if(x == 4 || x == 5)
							index = x-1;
						else if(x == 6)
							index = x-4;
					
						// index adjustment because AI is firing 
						index += 5;

						// if ships health is above 0, it is a valid ship
						if(game.allShipsStatus[index] > 0) 
							validShip = true;
						
					}
				}
		
				// AI guaranteed hit on first turn of game on Challenge difficulty
				if(game.difficulty.equals("Challenge") && game.AIhits + game.AImisses == 0) {
					
					out:
						// finding the valid ship on the grid, marking coordinates
						for(int i = 0; i < game.totalRows; i++)
							for(int j = 0; j < game.totalCols; j++)
								if(game.shipLocs[i][j] == x && game.AIgrid[i][j] == 0) {
									r = i;
									c = j;
									break out;
								}
					// ensuring AI will not fire to a coordinate already fired to
					if(game.checkFire(r, c)) {
						// taking the shot
						if(game.shotFired(r, c)) {
							// shot fired was a hit, update button color
							playerBoardButton[r][c].setBackground(hit);
							// update enemy hits
							enemyHits.setText("Enemy Hits: " + game.AIhits);
						}
						else {
							// shot fired was a miss, update button color
							playerBoardButton[r][c].setBackground(Color.WHITE);
							// update enemy misses
							enemyMisses.setText("Enemy Misses: " + game.AImisses);
						}
						// update enemy hit streak
						enemyHitStreak.setText("Enemy Hit Streak: " + game.AIhitStreak);
						enemyBestHitStreak.setText("Top Hit Streak: " + game.AIbestStreak);
					}

				}
				
				// brutal hit owed on Brutal difficulty
				else if((game.difficulty.equals("Brutal") && game.AImisses % 5 == 0 && 
						game.AImisses / 5 == game.brutalHits && game.smartShooting == false) || 
						rememberBrutal && !game.smartShooting) {

					// will no longer owe a brutal hit after this shot
					if(rememberBrutal)
						rememberBrutal = false;

					// loops until finding a valid shot to take
					boolean canFire = false;
					while(!canFire) {
						out:
							// finding the valid ship on the grid, marking coordinates
							for(int i = 0; i < game.totalRows; i++) 
								for(int j = 0; j < game.totalCols; j++)
									if(game.shipLocs[i][j] == x && game.AIgrid[i][j] == 0) {
										r = i;
										c = j;
										break out;
									}
						// checking for valid shot
						if(game.checkFire(r, c))
							canFire = true;
					}
					if(canFire) {
						// taking shot
						if(game.shotFired(r, c)) {
							// shot fired was a hit, update button color
							playerBoardButton[r][c].setBackground(hit);
							// update enemy hits and brutal hits
							enemyHits.setText("Enemy Hits: " + game.AIhits);
							game.brutalHits++;
						}
						else {
							// shot fired was a miss, update button color 
							playerBoardButton[r][c].setBackground(Color.WHITE);
							// update enemy misses
							enemyMisses.setText("Enemy Misses: " + game.AImisses);
						}
						// update enemy miss streak
						enemyHitStreak.setText("Enemy Hit Streak: " + game.AIhitStreak);
						enemyBestHitStreak.setText("Top Hit Streak: " + game.AIbestStreak);
					}
					else 
						System.out.println("major error");

				}
				
				// AI is working to sink a ship it has already hit
				else if(game.smartShooting == true) {
					
					boolean canFire = false;
					
					// randomly choosing which direction to shoot
					int ran = rand.nextInt(4);
					
					// case where the AI miss is a multiple of 5 but it is already working to 
					// sink a different ship, must remember that it owes a brutal hit
					if(!rememberBrutal && game.AImisses % 5 == 0 && game.AImisses / 5 == game.brutalHits &&
							game.difficulty.equals("Brutal"))
						rememberBrutal = true;
				
					// looping for a valid coordinate to fire to 
					while(!canFire) {
						while(game.smartShots[ran].equals("XX")) {
							ran = rand.nextInt(4);
						}
						// checking for valid coordinates
						if(game.checkFire(Integer.parseInt(game.smartShots[ran].substring(0,1)), 
								Integer.parseInt(game.smartShots[ran].substring(1,2))))
							canFire = true;
						else
							game.smartShots[ran] = "XX";
					}
	
					// valid coordinates were found
					if(canFire) {
						int row = Integer.parseInt(game.smartShots[ran].substring(0, 1));
						int col = Integer.parseInt(game.smartShots[ran].substring(1, 2));

						// taking shot
						if(game.shotFired(row, col)) { 
							// shot fired was a hit, update button color
							playerBoardButton[row][col].setBackground(hit);
							// update enemy hits
							enemyHits.setText("Enemy Hits: " + game.AIhits);
						}
						else {
							// shot fired was a miss, update button color
							playerBoardButton[row][col].setBackground(Color.WHITE);
							// update enemy misses
							enemyMisses.setText("Enemy Misses: " + game.AImisses);
						}
						// update enemy hit streak
						enemyHitStreak.setText("Enemy Hit Streak: " + game.AIhitStreak);
						enemyBestHitStreak.setText("Top Hit Streak: " + game.AIbestStreak);
					}
					else
						System.out.println("major error 2");
				}

				// nothing special, AI is firing at random
				else {
					
					boolean canFire = false;
					int r1 = -1;
					int r2 = -1;
					
					// looping for valid coordinates
					while(!canFire) {
						r1 = rand.nextInt(8);
						r2 = rand.nextInt(8);
						if(game.checkFire(r1, r2))
							canFire = true;
					}
					// valid coordinates found
					if(canFire) {
						// taking shot
						if(game.shotFired(r1, r2)) {
							// shot fired was a hit, update button color
							playerBoardButton[r1][r2].setBackground(hit);
							// update enemy hits
							enemyHits.setText("Enemy Hits: " + game.AIhits);
						}
						else {
							// shot fired was a miss, update button color
							playerBoardButton[r1][r2].setBackground(Color.WHITE);
							// update enemy misses
							enemyMisses.setText("Enemy Misses: " + game.AImisses);
						}
						// update enemy hit streak
						enemyHitStreak.setText("Enemy Hit Streak: " + game.AIhitStreak);
						enemyBestHitStreak.setText("Top Hit Streak: " + game.AIbestStreak);
					}
					else 
						System.out.println("major error 3");

				}
			
				// calculating and update enemy accuracy
				enemyAccuracy = ((float)game.AIhits/((float)game.AIhits+(float)game.AImisses))* 100;
				enemyScore.setText("Accuracy: " + df.format(enemyAccuracy) + "%");
			
				// update the turn number
				turnNum.setText("Turn Number: " + (game.playerHits+game.playerMisses+1));
			
				// checking for sunk ships
				int sunkShip = 0;
				for(int z = 0; z < game.allShipsStatus.length; z++) {
					// -1 in array from Logic class indicates a sunken ship
					if(game.allShipsStatus[z] == -1) {
						// indices 0-4 are AI ships
						if(z <= 4) {
							sunkShip = shipVal(z);
							// found sunken ship, now searching grids for location
							for(int row = 0; row < game.getRows(); row++) {
								for(int col = 0; col < game.getCols(); col++) {
									if(game.AIshipLocs[row][col] == sunkShip) {
										// update color of all buttons of sunken ship and display value
										enemyBoardButton[row][col].setBackground(sunk);
										enemyBoardButton[row][col].setText(String.valueOf(game.AIshipLocs[row][col]));
										enemyBoardButton[row][col].setFont(font);
										// update player checklist by changing corresponding button color
										if(sunkShip == 2)
											sunkDes.setBackground(checkmark);
										else if(sunkShip == 3)
											sunkSub.setBackground(checkmark);
										else if(sunkShip == 6)
											sunkCru.setBackground(checkmark);
										else if(sunkShip == 4)
											sunkBat.setBackground(checkmark);
										else if(sunkShip == 5)
											sunkCar.setBackground(checkmark);
									}
								}
							}
						}
						// indices 5-9 are player ships
						else if(z > 4) {
							sunkShip = shipVal(z);
							// found sunken ship, now searching grids for location
							for(int row = 0; row < game.getRows(); row++) {
								for(int col = 0; col < game.getCols(); col++) {
									if(game.shipLocs[row][col] == sunkShip) {
										// update color of all buttons of sunken ship
										playerBoardButton[row][col].setBackground(sunk);
										// update enemy checklist by changing corresponding button color
										if(sunkShip == 2)
											enemySunkDes.setBackground(checkmark);
										else if(sunkShip == 3)
											enemySunkSub.setBackground(checkmark);
										else if(sunkShip == 6)
											enemySunkCru.setBackground(checkmark);
										else if(sunkShip == 4)
											enemySunkBat.setBackground(checkmark);
										else if(sunkShip == 5)
											enemySunkCar.setBackground(checkmark);
									}
								}
							}
						}
						// make sunken ships status -2 within the Logic class array to signify it was acknowledged
						game.allShipsStatus[z] = -2;
					}
				}
			
				// update statistics after turn to display properly when game is over
				// slight moderation depending on set difficulty 
				if(game.difficulty.equals("Brutal"))
					gameStatistics = "Brutal Hits: " + game.brutalHits + "\n";
				else {
					gameStatistics = hits.getText() + "\n";
					gameStatistics += enemyHits.getText() + "\n";
				}
				gameStatistics += bestHitStreak.getText() + "\n";
				gameStatistics += enemyHitStreak.getText() + "\n";
				gameStatistics += score.getText() + "\n";
				gameStatistics += "Enemy: " + enemyScore.getText() + "\n";
				gameStatistics += turnNum.getText() + "\n";
				gameStatistics += timer.getText();			

				// player has won the game
				if(game.playerHits == 17) {
					// post game display after winning
					JOptionPane.showMessageDialog(null, "", "Game Over! You have won the game!!",
							JOptionPane.INFORMATION_MESSAGE, winnerIcon);
					JOptionPane.showMessageDialog(null, gameStatistics, "Game Stats",
							JOptionPane.INFORMATION_MESSAGE, statsIcon);
					// set all player checklist buttons to orange color 
					sunkDes.setBackground(orange);
					sunkSub.setBackground(orange);
					sunkCru.setBackground(orange);
					sunkBat.setBackground(orange);
					sunkCar.setBackground(orange);
				}
				// AI has won the game
				else if(game.AIhits == 17) {
					// post game display after losing
					JOptionPane.showMessageDialog(null, "", "Game Over! The enemy has won the game!!",
							JOptionPane.INFORMATION_MESSAGE, loserIcon);
					JOptionPane.showMessageDialog(null, gameStatistics, "Game Stats",
							JOptionPane.INFORMATION_MESSAGE, statsIcon);
					// set all enemy checklist buttons to orange color
					enemySunkDes.setBackground(orange);
					enemySunkSub.setBackground(orange);
					enemySunkCru.setBackground(orange);
					enemySunkBat.setBackground(orange);
					enemySunkCar.setBackground(orange);
				}
				// disable remaining buttons post game
				if(game.playerHits == 17 || game.AIhits == 17) {
					for(int row = 0; row < game.getRows(); row++) {
				    	for(int col = 0; col < game.getCols(); col++) {
				    		enemyBoardButton[row][col].setEnabled(false);	
				    	}
				    }
				}	
			
			}
		
		}
		
		
		/************************************************************************
		 * Helper method to find sunken ships
		 * 
		 * @param index - index from Logic class array storing ship health
		 * @return ships designated value found at that index given 
		 ************************************************************************/
		public int shipVal(int index) {
			
			// destroyer health indices 
			if(index == 0 || index == 5)
				return 2;
			// submarine health indices
			if(index == 1 || index == 6)
				return 3;
			// cruiser health indices
			if(index == 2 || index == 7)
				return 6;
			// battleship health indices
			if(index == 3 || index == 8)
				return 4;
			// carrier health indices
			if(index == 4 || index == 9)
				return 5;
			
			// would indicate something has gone wrong
			return 0;
		}
	}


}

