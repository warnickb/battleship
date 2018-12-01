package battleshipproject;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.GridLayout;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GUI extends JFrame {
	
	JButton btnNewGame = new JButton();
	private JPanel contentPane;
	
	public GUI() {
		mmGUI();
		buttonClick();
	}
	
	
	/**
	 * Create the frame.
	 */
	private void mmGUI() {
		setTitle("Battleship");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 3, 0, 0));
		setVisible(true);
		
		btnNewGame = new JButton("New Game");
		contentPane.add(btnNewGame);
		
		JButton btnSettings = new JButton("Leaderboard");
		contentPane.add(btnSettings);
		
		JButton btnExit = new JButton("Exit");
		contentPane.add(btnExit);
		
		
	}
	
	private void buttonClick() {
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//JFrame charGUI = new JFrame();
				new difficultyGUI();
				setVisible(false);
			}
			});
			
		}
		
		
	}
	
	

