package battleshipproject;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/********************************************************************************
 * Main menu for Battleship program
 * 
 * @author Justin Perticone
 * @author Brendan Warnick
 * @version December 5, 2018
 ********************************************************************************/
public class GUI extends JFrame {
	
	/** container for main menu */
	private JPanel contentPane;
	private JFrame frame;
	
	/** main menu buttons */
	JButton btnNewGame;
	JButton btnLeaderboards;
	JButton btnExit;
	
	
	/****************************************************************************
	 * Default Constructor
	 ****************************************************************************/
	public GUI() {
		setupGUI();
		buttonClick();
	}
	
	
	/****************************************************************************
	 * Creating main menu
	 ****************************************************************************/
	private void setupGUI() {
		
		Font font = new Font("Lucida Calligraphy", Font.BOLD, 30);
		
		contentPane = new JPanel();
		frame = new JFrame();
		
		// setting layout of panel
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 3, 0, 0));
		
		// button to start new game
		btnNewGame = new JButton("New Game");
		btnNewGame.setFont(font);
		contentPane.add(btnNewGame);
		
		// button to view leaderboards - not functional
		btnLeaderboards = new JButton("Leaderboards");
		btnLeaderboards.setFont(font);
		contentPane.add(btnLeaderboards);
		
		// button to exit program
		btnExit = new JButton("Exit");
		btnExit.setFont(font);
		contentPane.add(btnExit);
		
		// adding components to JFrame
		frame.getContentPane().add(contentPane);
	    frame.setTitle("Battleship: Main Menu");
	    frame.setLocationRelativeTo(null);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    // sizing JFrame
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    frame.pack();
	    frame.setBounds(0,0, screenSize.width, screenSize.height);
	    frame.setSize(screenSize.width, screenSize.height);
	    frame.setVisible(true);
		
	}
	
	
	/****************************************************************************
	 * ActionListener used by the Main Menu
	 ****************************************************************************/
	private void buttonClick() {
		// new game button clicked, begin new game
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new gridGUI();
				frame.dispose();
			}
		});
		// exit button clicked, exit program
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});	
		
		}
			
	}
	
	

