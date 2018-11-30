package battleshipproject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
//import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.*;
import java.util.*;

//CHECKLIST
//add sunk updates

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
	JButton carrier = new JButton();
	JButton battleship = new JButton();
	JButton cruiser = new JButton();
	JButton submarine = new JButton();
	JButton destroyer = new JButton();
	
	JPanel enemyBoard = new JPanel();
	JPanel playerBoard = new JPanel();
	JPanel statsSection = new JPanel();
	JPanel ships = new JPanel();
	
	JTextField score = new JTextField();
    JTextField turnNum = new JTextField();
    JTextField hits = new JTextField();
    JTextField misses = new JTextField();
    JTextField enemyScore = new JTextField();
    JTextField enemyHits = new JTextField();
    JTextField enemyMisses = new JTextField();
    JTextField space = new JTextField();
	
	Color water;
	Color hit;
	Color sunk;
	MyButtonHandler myHandler;

	
	
	public gridGUI() {
		game = new BattleshipLogicForGUI();
		water = new Color(200,235,250);
		hit = new Color(250,175,175);
		sunk = new Color(175,75,75);
		myHandler = new MyButtonHandler();
		gridSetup();
		//game.reset();
		//new shipGUI();
	}
	
	
	public void gridSetup() {
		//create GUI and set Layout
		
		
		Container pane = getContentPane();
		pane.setLayout(new BorderLayout());
		enemyBoard.setLayout(new GridLayout(8,8));
		playerBoard.setLayout(new GridLayout(8,8));
		statsSection.setLayout(new GridLayout(1,8));
		ships.setLayout(new GridLayout(1,5));
		
		carrier = new JButton("carrier.png");
	    carrier.addActionListener(null);
	    ships.add(carrier);
	    
	    
	    battleship = new JButton("Battleship");
	    battleship.addActionListener(null);
	    ships.add(battleship);
	    
	    cruiser = new JButton("Cruiser");
	    cruiser.addActionListener(null);
	    ships.add(cruiser);
	    
	    submarine = new JButton("Submarine");
	    submarine.addActionListener(null);
	    ships.add(submarine);
	    
	    destroyer = new JButton("Destroyer");
	    destroyer.addActionListener(null);
	    ships.add(destroyer);
	    
	    pane.add(ships, BorderLayout.LINE_END);
	    
		enemyBoardButton = new JButton[game.getRows()][game.getCols()];
		for(int row = 0; row < game.getRows(); row++) {
		    	for(int col = 0; col < game.getCols(); col++) {
				enemyBoardButton[row][col] = new JButton(" ");
			    	enemyBoardButton[row][col].addActionListener(myHandler);
			    	enemyBoardButton[row][col].setBackground(water);
			    	enemyBoard.add(enemyBoardButton[row][col]);
			    	pane.add(enemyBoard, BorderLayout.NORTH);
		    	}
	    	}

		
	    //Add scoring area between grids
	    
	    // FIXME need to use instance variables to get the values in text fields
		// FIXME decide how to calculate score (use multipliers, hits, misses etc)
		// - or use accuracy
	    score.setText("Score: 0");
	    turnNum.setText("Turn Number: 0");
	    hits.setText("Hits: 0");
	    enemyScore.setText("Enemy Score: 0");
	    enemyHits.setText("Enemy Hits: 0");
	    enemyMisses.setText("Enemy Misses: 0");
	    space.setText("   ");
	    misses.setText("Misses: 0");
	    statsSection.add(score);
	    statsSection.add(turnNum);
	    statsSection.add(hits);
	    statsSection.add(misses);
	    statsSection.add(enemyScore);
	    statsSection.add(enemyHits);
	    statsSection.add(enemyMisses);
	    statsSection.add(space);
	    // FIXME these may cause issues updating text fields - shouldn't
	    score.setEditable(false);
	    turnNum.setEditable(false);
	    hits.setEditable(false);
	    misses.setEditable(false);
	    enemyScore.setEditable(false);
	    enemyHits.setEditable(false);
	    enemyMisses.setEditable(false);
	    space.setEditable(false);
	    
	    pane.add(statsSection, BorderLayout.WEST);
	    
	    
	    //Create Player Grid
	   
		
	    playerBoardButton = new JButton[game.getRows()][game.getCols()];
	    for(int row = 0; row < game.getRows(); row++) {
		    for(int col = 0; col < game.getCols(); col++) {
			    playerBoardButton[row][col] = new JButton(" ");
			    playerBoardButton[row][col].addActionListener(myHandler);
			    playerBoardButton[row][col].setBackground(water);
			    playerBoard.add(playerBoardButton[row][col]);
			    pane.add(playerBoard, BorderLayout.SOUTH);
			    playerBoardButton[row][col].setEnabled(false);
		    }
	    }

	    
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    pack();
	    setVisible(true);
	}


	private class MyButtonHandler implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			Object which = e.getSource();
			//int player = game.currentPlayer();
			Random rand = new Random();
			
			for(int r = 0; r < game.getRows(); r++) {
				for(int c = 0; c < game.getCols(); c++) {
					if(enemyBoardButton[r][c] == which) {
						if(game.checkFire(r, c)) {
							if(game.shotFired(r, c)) {
								enemyBoardButton[r][c].setBackground(hit);
								hits.setText("Hits: " + game.playerHits);
							}
							else {
								enemyBoardButton[r][c].setBackground(Color.WHITE);
								misses.setText("Misses: " + game.playerMisses);
							}
							enemyBoardButton[r][c].setEnabled(false);
						} else {
							System.out.println("already fired here");
						}
					}
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

			if((game.difficulty.equals("challenge") && game.AIhits + game.AImisses == 0) ||
					game.difficulty.equals("brutal")) {

				while(!validShip) {

					int index = -1;

					x = rand.nextInt(5) + 2;

					if(c == 2 || c == 3)
						index = c-2;
					else if(c == 4 || c == 5)
						index = c-1;
					else if(c == 6)
						index = c-4;
					
					index += 5;

					if(game.allShipsStatus[index] > 0) 
						validShip = true;

				}
			}
		
			if(game.difficulty.equals("challenge") && game.AIhits + game.AImisses == 0) {
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
				}

			}

			else if(game.difficulty.equals("brutal") && game.AImisses % 5 == 0 && 
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
					if(game.checkFire(r, c))
						canFire = true;
				}
				if(canFire) {
					if(game.shotFired(r, c)) {
						playerBoardButton[r][c].setBackground(hit);
						enemyHits.setText("Enemy Hits: " + game.AIhits);
					}
					else {
						playerBoardButton[r][c].setBackground(Color.WHITE);
						enemyMisses.setText("Enemy Misses: " + game.AImisses);
					}
					game.brutalHits++;
				}
				else 
					System.out.println("major error");
				//System.out.println("brutal hits " + game.brutalHits);

			}
			
			else if(game.smartShooting == true) {
				System.out.println("is this being executed???");
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
					System.out.println("ughv2 " + row + col);
					//System.out.println("ugh " + Integer.parseInt(game.smartShots[ran].substring(0,1)) + Integer.parseInt(game.smartShots[ran].substring(1,2)));
					if(game.shotFired(row, col)) { 
						playerBoardButton[row][col].setBackground(hit);
						enemyHits.setText("Enemy Hits: " + game.AIhits);
					}
					else {
						playerBoardButton[row][col].setBackground(Color.WHITE);
						enemyMisses.setText("Enemy Misses: " + game.AImisses);
					}
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
				}
				else 
					System.out.println("major error 3");

			}

			System.out.println("brutal hits " + game.brutalHits);
			
			turnNum.setText("Turn Number: " + (game.playerHits+game.playerMisses+1));

			// helpful for debugging
			game.displayPlayerShips();
			game.displayAIShips();
			game.displayPlayerGrid();
			game.displayAIGrid();

			if(game.playerHits == 17)
				JOptionPane.showMessageDialog(null, "You have won the game!!");
			else if(game.AIhits == 17)
				JOptionPane.showMessageDialog(null, "The enemy has won the game!!");

		}
	}


}

