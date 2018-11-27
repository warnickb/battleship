package battleshipproject;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

public class shipGUI extends JFrame {
	
	public shipGUI() {
		shipGUI();
		
	}
	public void shipGUI() {
		//create GUI and set Layout
				
				getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
				setBounds(100, 100, 641, 412);
				
				JButton btn1 = new JButton("Carrier");
				btn1.setAlignmentX(Component.CENTER_ALIGNMENT);
				getContentPane().add(btn1);
				
				JButton btn2 = new JButton("Battleship");
				btn2.setAlignmentX(Component.CENTER_ALIGNMENT);
				getContentPane().add(btn2);
				
				JButton btn3 = new JButton("Cruiser");
				btn3.setAlignmentX(Component.CENTER_ALIGNMENT);
				getContentPane().add(btn3);
				
				JButton btn4 = new JButton("Submarine");
				btn4.setAlignmentX(Component.CENTER_ALIGNMENT);
				getContentPane().add(btn4);
				
				JButton btn5 = new JButton("Destroyer");
				btn5.setAlignmentX(Component.CENTER_ALIGNMENT);
				getContentPane().add(btn5);
				
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			    pack();
			    setVisible(true);
	}

	

}