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
 * @version November 29, 2018
 **********************************************************************/
public class gridGUI extends JFrame {
	
	JButton[][] enemyBoardButton;
	JButton[][] playerBoardButton;
	BattleshipLogicForGUI game;
	
	JButton score = new JButton();
	JButton turnNum = new JButton();
	JButton hits = new JButton();
	JButton misses = new JButton();
	JButton enemyScore = new JButton();
	JButton enemyHits = new JButton();
	JButton enemyMisses = new JButton();
	JButton hitStreak = new JButton("Hit Streak: 0");
	JButton bestHitStreak = new JButton("Top Hit Streak: 0");
	JButton timer = new JButton("Time: 00:00.000");
	JButton enemyHitStreak = new JButton("Enemy Hit Streak: 0");
	JButton enemyBestHitStreak = new JButton("Top Hit Streak: 0");
	
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
	
	Color water;
	Color hit;
	Color sunk;
	Color ship;
	Color checkmark;
	Color yellow;
	Color orange;
	
	GridLayout myLayout;
	MyButtonHandler myHandler;
	JPanel pane;
	JFrame frame;
	JMenuBar menuBar;
	JMenu file;
	JMenu stats;
	JMenu information;
	JMenuItem saveItem;
	JMenuItem loadItem;
	JMenuItem newItem;
	JMenuItem quitItem;
	JMenuItem currStatsItem;
	JMenuItem leaderboardsItem;
	JMenuItem diffInfoItem;
	
	ImageIcon winnerIcon;
	ImageIcon loserIcon;
	ImageIcon statsIcon;
	
	long startTime;
	long elapsedTime;
	long elapsedSeconds;
	long secondsDisplay;
	long elapsedMinutes;
	long minutesDisplay;
	
	Font font;

		
	public gridGUI() {
		game = new BattleshipLogicForGUI();
		water = new Color(200,235,250);
		hit = new Color(250,175,175);
		sunk = new Color(220,120,120);
		ship = new Color(200,200,200);
		checkmark = new Color(225,250,180);
		yellow = new Color(250,250,180);
		orange = new Color(250,235,180);
		winnerIcon = new ImageIcon(gridGUI.class.getResource("fireworks.jpg"));
		loserIcon = new ImageIcon(gridGUI.class.getResource("sinking.jpg"));
		statsIcon = new ImageIcon(gridGUI.class.getResource("stats.jpg"));
		startTime = System.currentTimeMillis();
		myHandler = new MyButtonHandler();

		gridSetup();
	}
	
	
	public void gridSetup() {
		
		//create GUI and set Layout
		font = new Font("Lucida Calligraphy", Font.PLAIN, 18);
		frame = new JFrame();
		pane = new JPanel();
		myLayout = new GridLayout(19, 10);
		pane.setLayout(myLayout);
		menuBar = new JMenuBar();
		file = new JMenu("File");
		saveItem = new JMenuItem("Save Game");
		loadItem = new JMenuItem("Load Game");
		newItem = new JMenuItem("New Game");
		newItem.addActionListener(myHandler);
		quitItem = new JMenuItem("Exit");
		quitItem.addActionListener(myHandler);
		file.add(saveItem);
		file.add(loadItem);
		file.add(newItem);
		file.add(quitItem);
		stats = new JMenu("Stats");
		currStatsItem = new JMenuItem("Game Statistics");
		currStatsItem.addActionListener(myHandler);
		// FIXME make leaderboards into a panel or something other than message dialog
		leaderboardsItem = new JMenuItem("Leaderboards");
		//leaderboardsItem.addActionListener(myHandler);
		stats.add(currStatsItem);
		information = new JMenu("Information");
		diffInfoItem = new JMenuItem("Difficulty Information");
		diffInfoItem.addActionListener(myHandler);
		information.add(diffInfoItem);
		menuBar.add(file);
		menuBar.add(stats);
		menuBar.add(information);
		frame.setJMenuBar(menuBar);
		
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
		
		
		playerSunk.setBackground(yellow);
		enemySunk.setBackground(yellow);
		score.setBackground(yellow);
		bestHitStreak.setBackground(yellow);
		enemyScore.setBackground(yellow);
		enemyBestHitStreak.setBackground(yellow);
		

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

		
		score.setText("Accuracy: ");
	    turnNum.setText("Turn Number: 0");
	    hits.setText("Hits: 0");
	    enemyScore.setText("Accuracy: ");
	    enemyHits.setText("Enemy Hits: 0");
	    enemyMisses.setText("Enemy Misses: 0");
	    misses.setText("Misses: 0");
		
		pane.add(playerSunk);
	    pane.add(sunkDes);
	    pane.add(sunkSub);
	    pane.add(sunkCru);
	    pane.add(sunkBat);
	    pane.add(sunkCar);
	    pane.add(score);
	    pane.add(bestHitStreak);
	    
	    score.setFont(font);
	    bestHitStreak.setFont(font);
		

	    pane.add(hits);
	    pane.add(misses);
	    pane.add(enemyHits);
	    pane.add(enemyMisses);
	    pane.add(hitStreak);
	    pane.add(enemyHitStreak);
	    pane.add(turnNum);
	    pane.add(timer); 
	    
	    hits.setFont(font);
	    misses.setFont(font);
	    enemyHits.setFont(font);
	    enemyMisses.setFont(font);
	    hitStreak.setFont(font);
	    enemyHitStreak.setFont(font);
	    turnNum.setFont(font);
	    timer.setFont(font);
	    
	   
	    
	    score.setEnabled(false);
	    hits.setEnabled(false);
	    misses.setEnabled(false);
	    enemyScore.setEnabled(false);
	    enemyHits.setEnabled(false);
	    enemyMisses.setEnabled(false);
	    turnNum.setEnabled(false);
	    
	    pane.add(enemySunk);
	    pane.add(enemySunkDes);
	    pane.add(enemySunkSub);
	    pane.add(enemySunkCru);
	    pane.add(enemySunkBat);
	    pane.add(enemySunkCar);
	    pane.add(enemyScore);
	    pane.add(enemyBestHitStreak);
	    
	    enemyScore.setFont(font);
	    enemyBestHitStreak
	    .setFont(font);
	    

	    
	    //Create Player Grid
	   
		
	    playerBoardButton = new JButton[game.getRows()][game.getCols()];
	    for(int row = 0; row < game.getRows(); row++) {
		    for(int col = 0; col < game.getCols(); col++) {
			    playerBoardButton[row][col] = new JButton(" ");
			    playerBoardButton[row][col].addActionListener(myHandler);
			    playerBoardButton[row][col].setBackground(water);
			    playerBoardButton[row][col].setPreferredSize(dim);
			    pane.add(playerBoardButton[row][col]);
			    playerBoardButton[row][col].setEnabled(false);
		    }
	    }
	    
	    
	    for(int row = 0; row < game.getRows(); row++) {
	    	for(int col = 0; col < game.getCols(); col++) {
	    		if(game.shipLocs[row][col] >= 2) {
	    			playerBoardButton[row][col].setBackground(ship);
	    			playerBoardButton[row][col].setFont(font);
	    			playerBoardButton[row][col].setText(String.valueOf(game.shipLocs[row][col]));
	    		}
	    	}
	    }
	    
	    frame.getContentPane().add(pane);
	    frame.setTitle("Classic Battleship: " + game.difficulty + " Difficulty");
	    frame.setLocationRelativeTo(null);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    frame.pack();
	    frame.setBounds(0,0, screenSize.width, screenSize.height);
	    frame.setSize(screenSize.width, screenSize.height);
	    frame.setVisible(true);
	    //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    //pack();
	    //setVisible(true);
	}
	

	private class MyButtonHandler implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			Object which = e.getSource();
			Random rand = new Random();
			
			String gameStatistics = hits.getText() + "\n";
			//gameStatistics += misses.getText() + "\n";
			gameStatistics += enemyHits.getText() + "\n";
			//gameStatistics += enemyMisses.getText() + "\n";
			gameStatistics += bestHitStreak.getText() + "\n";
			gameStatistics += enemyHitStreak.getText() + "\n";
			gameStatistics += score.getText() + "\n";
			gameStatistics += enemyScore.getText() + "\n";
			gameStatistics += turnNum.getText() + "\n";
			gameStatistics += timer.getText();
			
			elapsedTime = System.currentTimeMillis() - startTime;
			
			if(which == currStatsItem) {
				JOptionPane.showMessageDialog(null, gameStatistics, "Current Game Stats",
						JOptionPane.INFORMATION_MESSAGE, statsIcon);
			}
			else if(which == quitItem ) {
				System.exit(1);
			}
			else if(which == newItem) {
				int confirmation = JOptionPane.showConfirmDialog(null, "End this game to " +
						"begin a new game?", null, JOptionPane.YES_NO_OPTION);
				if(confirmation == JOptionPane.YES_OPTION) { 
					frame.dispose();
					new GUI();
				}
			}
			else if(which == diffInfoItem) {
				game.difficultyInfo();
			}
			// FIXME WILL NEW new GUI() in loadItem
			else if(which == saveItem) {
				
			}
			else if(which == loadItem) {
				
			}
			
			else {
			
				for(int r = 0; r < game.getRows(); r++) {
					for(int c = 0; c < game.getCols(); c++) {
						if(enemyBoardButton[r][c] == which) {
							//if(game.checkFire(r, c)) {
								if(game.shotFired(r, c)) {
									enemyBoardButton[r][c].setBackground(hit);
									hits.setText("Hits: " + game.playerHits);
								}
								else {
									enemyBoardButton[r][c].setBackground(Color.WHITE);
									misses.setText("Misses: " + game.playerMisses);	
								}
								hitStreak.setText("Hit Streak: " + game.hitStreak);
								bestHitStreak.setText("Top Hit Streak: " + game.bestStreak);
								enemyBoardButton[r][c].setEnabled(false);
							} else {
								System.out.println("already fired here");
							}
						//}
					}
				}

				DecimalFormat df = new DecimalFormat("0.0");
				float accuracy = ((float)game.playerHits/((float)game.playerHits+(float)game.playerMisses))* 100;
				System.out.println("acc " + accuracy);
				score.setText("Accuracy: " + df.format(accuracy) + "%");
			
				int r = 0; 
				int c = 0;
				int x = 0;
				boolean validShip = false;

				if((game.difficulty.equals("Challenge") && game.AIhits + game.AImisses == 0) ||
						game.difficulty.equals("Brutal")) {

					while(!validShip) {

						int index = -1;

						x = rand.nextInt(5) + 2;
						System.out.println("x " + x);

						if(x == 2 || x == 3)
							index = x-2;
						else if(x == 4 || x == 5)
							index = x-1;
						else if(x == 6)
							index = x-4;
					
						index += 5;

						if(game.allShipsStatus[index] > 0) 
							validShip = true;
					
						System.out.println("index " + index);

					}
				}
		
				if(game.difficulty.equals("Challenge") && game.AIhits + game.AImisses == 0) {
					System.out.println("x " + x);

					out:

						for(int i = 0; i < game.totalRows; i++)
							for(int j = 0; j < game.totalCols; j++)
								if(game.shipLocs[i][j] == x && game.AIgrid[i][j] == 0) {
									r = i;
									c = j;
									break out;
								}
					if(game.checkFire(r, c)) {
						if(game.shotFired(r, c)) {
							playerBoardButton[r][c].setBackground(hit);
							enemyHits.setText("Enemy Hits: " + game.AIhits);
						}
						else {
							playerBoardButton[r][c].setBackground(Color.WHITE);
							enemyMisses.setText("Enemy Misses: " + game.AImisses);
						}
						enemyHitStreak.setText("Enemy Hit Streak: " + game.AIhitStreak);
						enemyBestHitStreak.setText("Top Hit Streak: " + game.AIbestStreak);
					}

				}

				else if(game.difficulty.equals("Brutal") && game.AImisses % 5 == 0 && 
						game.AImisses / 5 == game.brutalHits && game.smartShooting == false) {
					System.out.println("x " + x);

					boolean canFire = false;
					while(!canFire) {
						out:

							for(int i = 0; i < game.totalRows; i++) 
								for(int j = 0; j < game.totalCols; j++)
									if(game.shipLocs[i][j] == x && game.AIgrid[i][j] == 0) {
										r = i;
										c = j;
										break out;
									}
						System.out.println("r " + r + " c " + c);
						if(game.checkFire(r, c))
							canFire = true;
					}
					if(canFire) {
						if(game.shotFired(r, c)) {
							playerBoardButton[r][c].setBackground(hit);
							enemyHits.setText("Enemy Hits: " + game.AIhits);
							game.brutalHits++;
						}
						else {
							playerBoardButton[r][c].setBackground(Color.WHITE);
							enemyMisses.setText("Enemy Misses: " + game.AImisses);
						}
						enemyHitStreak.setText("Enemy Hit Streak: " + game.AIhitStreak);
						enemyBestHitStreak.setText("Top Hit Streak: " + game.AIbestStreak);
					}
					else 
						System.out.println("major error");

				}
			
				else if(game.smartShooting == true) {
					boolean canFire = false;
					int ran = rand.nextInt(4);
					System.out.println("ran1 " + ran);
					System.out.println(game.smartShots[ran]);
				
					while(!canFire) {
						while(game.smartShots[ran].equals("XX")) {
							ran = rand.nextInt(4);
							System.out.println("ran " + ran);
							System.out.println(game.smartShots[ran]);
						}
						if(game.checkFire(Integer.parseInt(game.smartShots[ran].substring(0,1)), 
								Integer.parseInt(game.smartShots[ran].substring(1,2))))
							canFire = true;
						else
							game.smartShots[ran] = "XX";
					}
	
					if(canFire) {
						int row = Integer.parseInt(game.smartShots[ran].substring(0, 1));
						int col = Integer.parseInt(game.smartShots[ran].substring(1, 2));

						if(game.shotFired(row, col)) { 
							playerBoardButton[row][col].setBackground(hit);
							enemyHits.setText("Enemy Hits: " + game.AIhits);
						}
						else {
							playerBoardButton[row][col].setBackground(Color.WHITE);
							enemyMisses.setText("Enemy Misses: " + game.AImisses);
						}
						enemyHitStreak.setText("Enemy Hit Streak: " + game.AIhitStreak);
						enemyBestHitStreak.setText("Top Hit Streak: " + game.AIbestStreak);
					}
					else
						System.out.println("major error 2");
				}

				else {
					boolean canFire = false;
					int r1 = -1;
					int r2 = -1;
					while(!canFire) {
						r1 = rand.nextInt(8);
						r2 = rand.nextInt(8);
						if(game.checkFire(r1, r2))
							canFire = true;
					}
					if(canFire) {
						if(game.shotFired(r1, r2)) {
							playerBoardButton[r1][r2].setBackground(hit);
							enemyHits.setText("Enemy Hits: " + game.AIhits);
						}
						else {
							playerBoardButton[r1][r2].setBackground(Color.WHITE);
							enemyMisses.setText("Enemy Misses: " + game.AImisses);
						}
						enemyHitStreak.setText("Enemy Hit Streak: " + game.AIhitStreak);
						enemyBestHitStreak.setText("Top Hit Streak: " + game.AIbestStreak);
					}
					else 
						System.out.println("major error 3");

				}
			
				float enemyAccuracy = ((float)game.AIhits/((float)game.AIhits+(float)game.AImisses))* 100;
				System.out.println("acc " + accuracy);
				enemyScore.setText("Accuracy: " + df.format(enemyAccuracy) + "%");
			
				turnNum.setText("Turn Number: " + (game.playerHits+game.playerMisses+1));

				// helpful for debugging
				game.displayPlayerShips();
				game.displayAIShips();
				game.displayPlayerGrid();
				game.displayAIGrid();
			
				int sunkShip = 0;
				for(int z = 0; z < game.allShipsStatus.length; z++) {
					if(game.allShipsStatus[z] == -1) {
						if(z <= 4) {
							sunkShip = shipVal(z);
							for(int row = 0; row < game.getRows(); row++) {
								for(int col = 0; col < game.getCols(); col++) {
									if(game.AIshipLocs[row][col] == sunkShip) {
										enemyBoardButton[row][col].setBackground(sunk);
										enemyBoardButton[row][col].setText(String.valueOf(game.AIshipLocs[row][col]));
										enemyBoardButton[row][col].setFont(font);
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
						else if(z > 4) {
							sunkShip = shipVal(z);
							for(int row = 0; row < game.getRows(); row++) {
								for(int col = 0; col < game.getCols(); col++) {
									if(game.shipLocs[row][col] == sunkShip) {
										playerBoardButton[row][col].setBackground(sunk);
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
						game.allShipsStatus[z] = -2;
					}
				}
			
			

				// FIXME WOULD HAVE TO UPDATE LEADERBOARDS IN HERE
				if(game.playerHits == 17) {
					JOptionPane.showMessageDialog(null, "", "Game Over! You have won the game!!",
							JOptionPane.INFORMATION_MESSAGE, winnerIcon);
					JOptionPane.showMessageDialog(null, gameStatistics, "Game Stats",
							JOptionPane.INFORMATION_MESSAGE, statsIcon);
					sunkDes.setBackground(orange);
					sunkSub.setBackground(orange);
					sunkCru.setBackground(orange);
					sunkBat.setBackground(orange);
					sunkCar.setBackground(orange);
				}
				else if(game.AIhits == 17) {
					JOptionPane.showMessageDialog(null, "", "Game Over! The enemy has won the game!!",
							JOptionPane.INFORMATION_MESSAGE, loserIcon);
					JOptionPane.showMessageDialog(null, gameStatistics, "Game Stats",
							JOptionPane.INFORMATION_MESSAGE, statsIcon);
					enemySunkDes.setBackground(orange);
					enemySunkSub.setBackground(orange);
					enemySunkCru.setBackground(orange);
					enemySunkBat.setBackground(orange);
					enemySunkCar.setBackground(orange);
				}
			
			
			
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
		
		public int shipVal(int index) {
			
			// if there's an error it will change the color of all of the water
			
			if(index == 0 || index == 5)
				return 2;
			if(index == 1 || index == 6)
				return 3;
			if(index == 2 || index == 7)
				return 6;
			if(index == 3 || index == 8)
				return 4;
			if(index == 4 || index == 9)
				return 5;
			
			return 0;
		}
	}


}

