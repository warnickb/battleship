package battleshipproject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class gridGUI extends JFrame {
	JButton[][] board;
	BattleshipGame newGame;
	JFrame newFrame;
	JPanel newPanel;
	GridLayout newLayout;
	
	public gridGUI() {
		gridSetup();
	}
	
	private void gridSetup() {
		newGame = new BattleshipGame();
		
		newFrame = new JFrame();
		newPanel = new JPanel();
		newLayout = new GridLayout(8, 8);
		newPanel.setLayout(newLayout);
		
		
		board = new JButton[newGame.getRows()][newGame.getCols()];
		Dimension dim = new Dimension(80,80);
		for (int row = 0; row < newGame.getRows(); row++) {
			for (int col = 0; col < newGame.getCols(); col++) {
				board[row][col] = new JButton(" ");
				board[row][col].setPreferredSize(dim);
				//board[row][col].addActionListener(newHandler);
				board[row][col].setBackground(Color.WHITE);
				newPanel.add(board[row][col]);
			}
		}
		newFrame.getContentPane();
		newFrame.setVisible(true);
		newFrame.setLocationRelativeTo(null);
		
	}
	
	
}
