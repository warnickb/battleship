package battleshipproject;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class gridGUI extends JFrame {
	private JPanel gridArray;
	private JFrame frame;
	JButton[][] grid;
	
	public gridGUI() {
		
		
		createGrid();
	}
	
	private void createGrid() {
		setTitle("Battleship");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		gridArray = new JPanel();
		gridArray.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(gridArray);
		gridArray.setLayout(new GridLayout(8,8));
		
		for(int i = 0; i < 64; i++) {
			JButton button = new JButton(Integer.toString(i+1));
			gridArray.add(button);
		}
		setVisible(true);
		
	}
	
	
}
