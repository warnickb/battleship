package battleshipproject;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
//import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class gridGUI extends JFrame {
	JButton enemyBoardButton = new JButton();
	JButton playerBoardButton = new JButton();
	
	public gridGUI() {
		gridSetup();
		//buttonClick();
	}
	
	private void gridSetup() {
		Container pane = getContentPane();
		pane.setLayout(new GridLayout(17,8));
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
			enemyBoardButton = new JButton(" ");
			enemyBoardButton.setActionCommand("(" + i + ", " + j + ")");
			enemyBoardButton.setBackground(Color.white);
			enemyBoardButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					enemyBoardButton = (JButton) e.getSource();
					System.out.println(enemyBoardButton.getActionCommand()+" test");
					
				}
			});
			pane.add(enemyBoardButton);
			
			}
		}
	
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
	    pane.add(score);
	    pane.add(turnNum);
	    pane.add(hits);
	    pane.add(misses);
	    pane.add(enemyScore);
	    pane.add(enemyHits);
	    pane.add(enemyMisses);
	    pane.add(space);
	    score.setEditable(false);
	    turnNum.setEditable(false);
	    hits.setEditable(false);
	    misses.setEditable(false);
	    enemyScore.setEditable(false);
	    enemyHits.setEditable(false);
	    enemyMisses.setEditable(false);
	    space.setEditable(false);
	    
	    for (int a = 0; a < 8; a++) {
			for (int b = 0; b < 8; b++) {
			playerBoardButton = new JButton(" ");
			playerBoardButton.setActionCommand("(" + a + ", " + b + ")");
			playerBoardButton.setBackground(Color.white);
			playerBoardButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					playerBoardButton = (JButton) e.getSource();
					System.out.println(playerBoardButton.getActionCommand()+" test1");
					
				}
			});
			pane.add(playerBoardButton);
			
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
