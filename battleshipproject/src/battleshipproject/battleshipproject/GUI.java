package battleshipproject;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GUI extends JFrame {
	
	private JPanel contentPane;
	private JFrame frame;
	
	JButton btnNewGame;
	JButton btnSettings;
	JButton btnExit;
	
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
		contentPane = new JPanel();
		frame = new JFrame();
		
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 3, 0, 0));
		
		btnNewGame = new JButton("New Game");
		contentPane.add(btnNewGame);
		
		btnSettings = new JButton("Leaderboards");
		contentPane.add(btnSettings);
		
		btnExit = new JButton("Exit");
		contentPane.add(btnExit);
		
		frame.getContentPane().add(contentPane);
	    frame.setTitle("Battleship");
	    frame.setLocationRelativeTo(null);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    frame.pack();
	    frame.setBounds(0,0, screenSize.width, screenSize.height);
	    frame.setSize(screenSize.width, screenSize.height);
	    frame.setVisible(true);
		
		
	}
	
	private void buttonClick() {
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new gridGUI();
				setVisible(false);
			}
			});
			
		}
		
		
	}
	
	

