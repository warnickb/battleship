package battleshipproject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
//import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.omg.CORBA_2_3.portable.InputStream;

public class gridGUI extends JFrame {
	JButton enemyBoardButton = new JButton();
	JButton playerBoardButton = new JButton();
	JButton carrier = new JButton();
	JButton battleship = new JButton();
	JButton cruiser = new JButton();
	JButton submarine = new JButton();
	JButton destroyer = new JButton();
	
	JPanel enemyBoard = new JPanel();
	JPanel playerBoard = new JPanel();
	JPanel statsSection = new JPanel();
	JPanel ships = new JPanel();
	
	//BattleshipGame game = new BattleshipGame();
	Color water = new Color(150,250,250);
	Color hit = new Color(250,175,175);
	Color sunk = new Color(175, 75, 75);
	
	//ImageIcon carrierPic = new ImageIcon("carrier.PNG");
	//java.io.InputStream input = ClassLoader.getSystemResourceAsStream("carrier.png");
	
	
	public gridGUI() {
		gridSetup();
		//game.reset();
		//new shipGUI();
	}
	
	
	private void gridSetup() {
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
		//Create Enemy Grid
		for (int i = 0; i < 8; i++) { 
			for (int j = 0; j < 8; j++) {
			enemyBoardButton = new JButton(" ");
			enemyBoardButton.setActionCommand("(" + i + ", " + j + ")");
			enemyBoardButton.setBackground(water);
			enemyBoardButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					enemyBoardButton = (JButton) e.getSource();
					System.out.println(enemyBoardButton.getActionCommand()+" test");
					
				}
			});
			enemyBoard.add(enemyBoardButton);
			pane.add(enemyBoard, BorderLayout.NORTH);
			
			}
		}
		
		//Add scoring area between grids
	    JTextField score = new JTextField();
	    JTextField turnNum = new JTextField();
	    JTextField hits = new JTextField();
	    JTextField misses = new JTextField();
	    JTextField enemyScore = new JTextField();
	    JTextField enemyHits = new JTextField();
	    JTextField enemyMisses = new JTextField();
	    JTextField space = new JTextField();
	    score.setText("Score: ");
	    turnNum.setText("Turn Number: ");
	    hits.setText("Hits: ");
	    enemyScore.setText("Enemy Score: ");
	    enemyHits.setText("Enemy Hits: ");
	    enemyMisses.setText("Enemy Misses: ");
	    space.setText("   ");
	    misses.setText("Misses: ");
	    statsSection.add(score);
	    statsSection.add(turnNum);
	    statsSection.add(hits);
	    statsSection.add(misses);
	    statsSection.add(enemyScore);
	    statsSection.add(enemyHits);
	    statsSection.add(enemyMisses);
	    statsSection.add(space);
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
	    for (int a = 0; a < 8; a++) {
			for (int b = 0; b < 8; b++) {
			playerBoardButton = new JButton(" ");
			playerBoardButton.setActionCommand("(" + a + ", " + b + ")");
			playerBoardButton.setBackground(water);
			playerBoardButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					playerBoardButton = (JButton) e.getSource();
					System.out.println(playerBoardButton.getActionCommand()+" test1");
					//game.placeShip(a, b, "Left", 3);
				}
			});
			playerBoard.add(playerBoardButton);
			pane.add(playerBoard, BorderLayout.SOUTH);
			
			}
		}
	    
	    
	    
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    pack();
	    setVisible(true);
	    
	    
	}
	
//	private void buttonClick() {
//		enemyBoardButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				System.out.println("test");
//			}
//			});
//		enemyBoardButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				System.out.println("test");
//			}
//			});
//	}
	
	
}
