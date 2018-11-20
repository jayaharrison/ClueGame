/**
 * @author Jay Harrison
 * @author Adam Kinard
 * 
 * Main class for game operation. Runs the entire program
 */
package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import clueGame.Board;
import clueGame.BoardCell;
import experiment.FileMenu;
import experiment.GUI_Example;

public class ClueGame extends JFrame {
	private ControlGUI gui;
	private MyCardsGUI side;
	private FileMenu fileMenu;
	private Board board;
	private Map<String, Player> players;
	
	private static String title = "Welcome to Clue";
	private static String message = "You are Miss Scarlet, press Next Player to begin play";
	
	private Player user = new Player();
	private Set<Card> userHand = new HashSet<Card>();
	
	//private ArrayList<Player> players = board.getPlayerMap().values(); //Need to populate

	public ClueGame() {
		
		// Get board and set up
		board = Board.getInstance();
		board.setConfigFiles("ClueRooms.csv", "ClueRooms.txt", "CluePlayers.txt", "ClueWeapons.txt");
		board.initialize();		
		
		Map<String, Player> players = board.getPlayerMap();
		
		for (Player p : players.values()) {
			if ( p instanceof HumanPlayer ) {
				user = p;
			}
		}
		
		userHand = user.getHand();
		
		// Initialize GUIs
		gui = new ControlGUI();
		gui.player.addActionListener(new nextPlayerListener());
		gui.accusation.addActionListener(new accusationListener());
		
		
		
		side = new MyCardsGUI(userHand);
		fileMenu = new FileMenu();
	
		
		// JFrame setup
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700, 765);
		
		// Add panels/etc to frame
		add(board, BorderLayout.CENTER);
		add(gui, BorderLayout.SOUTH);
		add(side, BorderLayout.EAST);
		setJMenuBar(fileMenu);
		
	}
	
	public void updateGame(Player player) {
		gui.updateGUI(player);
		board.nextPlayer(player, gui.getDieRoll());
		repaint();
	}
	
	/**
	 * Runs the game
	 * @param args
	 */
	public static void main(String[] args) {
		//Create a JFrame with all the normal functionality
		ClueGame game = new ClueGame();
		game.setVisible(true);
		
		JOptionPane pane = new JOptionPane();
		pane.showMessageDialog(game, message, title, JOptionPane.INFORMATION_MESSAGE);	
		
		
	}

	// Next Player listener
	public class nextPlayerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// run all code for game
			// goes to next player in list
		}
	}

	// Next Player listener
	public class accusationListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// run all code for game
			System.exit(0);
		}
	}
	
	public Player getUser() {
		return this.user;
	}
}
