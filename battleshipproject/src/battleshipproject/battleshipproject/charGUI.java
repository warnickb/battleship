package battleshipproject;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class charGUI extends JFrame{
	JPanel contentPane;
	private JButton btnNewButton;
	private JButton btnNewButton2;
	private JButton btnNewButton3;
	
	public charGUI() {
		menu();
		buttonClick();
	}
	private void menu() {
		setTitle("Battleship");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 3, 0, 0));
		
		btnNewButton = new JButton("Character 1");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPane.add(btnNewButton);
		
		btnNewButton2 = new JButton("Character 2");
		btnNewButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPane.add(btnNewButton2);
		
		btnNewButton3 = new JButton("Character 3");
		btnNewButton3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPane.add(btnNewButton3);
		setVisible(true);
		
	}
	private void buttonClick() {
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//JFrame charGUI = new JFrame();
				new gridGUI();
				setVisible(false);
			}
			});
		btnNewButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//JFrame charGUI = new JFrame();
				new gridGUI();
				setVisible(false);
			}
			});
		btnNewButton3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//JFrame charGUI = new JFrame();
				new gridGUI();
				setVisible(false);
			}
			});
			
		}
}
