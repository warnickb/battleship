package battleshipproject;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
//import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class gridGUI extends JFrame {
	
	
	public gridGUI() {
		gridSetup();
		
	}
	
	private void gridSetup() {
		Container pane = getContentPane();
		pane.setLayout(new GridLayout(17,8));
		for (int i = 0; i < 64; i++) {
			JButton button = new JButton(" ");
			button.setBackground(Color.white);
			pane.add(button);
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
	    
	    for (int i = 0; i < 64; i++) {
			JButton button = new JButton(" ");
			button.setBackground(Color.white);
			pane.add(button);
		}
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    pack();
	    setVisible(true);
	}
	
	
}
