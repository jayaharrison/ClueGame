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
	private ArrayList<Player> players;
	
	private static String welcomeTitle = "Welcome to Clue";
	private static String welcomeMessage = "You are Miss Scarlet, press Next Player to begin play";
	private static String turnNotOverTitle = "Error: Your turn is not complete.";
	private static String turnNotOverMessage = "Please finish your turn before pressing Next Player.";
	private static String incorrectLocationMessage = "Please select a valid space.";
	private static String cannotMakeAccusation = "You can only make an accusation during your turn.";
	
	private Player user = new Player();
	private Set<Card> userHand = new HashSet<Card>();
	
	private static int arrayOffset = 0;
	private Player currentPlayer;
	
	private boolean humanIsDone = false;
	
	//private ArrayList<Player> players = board.getPlayerMap().values(); //Need to populate

	public ClueGame() {
		
		// Get board and set up
		board = Board.getInstance();
		board.setConfigFiles("ClueRooms.csv", "ClueRooms.txt", "CluePlayers.txt", "ClueWeapons.txt");
		board.initialize();	
		
		// Get players from map to array list
		Map<String, Player> playerMap = board.getPlayerMap();
		players = new ArrayList<Player>();

		Set<String> playerKeys = playerMap.keySet();
		for (String key : playerKeys) {
			Player player = playerMap.get(key);
			System.out.println(player.getPlayerName());
			players.add(player);
		}
		
		
		// Set up user hand, Miss Scarlett at 0. Human is always Miss Scarlett.
		user = players.get(0);
		userHand = user.getHand();
		
		// Initialize GUIs/listeners
		gui = new ControlGUI();
		gui.player.addActionListener(new nextPlayerListener());
		gui.accusation.addActionListener(new accusationListener());
		
		addMouseListener(new MoveHumanPlayer());
		
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

	// Next Player listener
	public class nextPlayerListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			// run all code for game
			
			// If human is not done display error
			if (!humanIsDone && currentPlayer instanceof HumanPlayer) {
				JOptionPane errorPane = new JOptionPane();
				errorPane.showMessageDialog(new JFrame(), turnNotOverMessage, turnNotOverTitle, JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			// Reset boolean
			humanIsDone = false;
			
			// Wrap around array if at max
			if (arrayOffset >= players.size()) arrayOffset = 0;
			currentPlayer = players.get(arrayOffset);

			// Roll dice
			int dieRoll = (int)Math.floor(Math.random() * Math.floor(6)) + 1;
			
			// Update GUI with current info
			gui.updateGUI(currentPlayer, dieRoll);
			
			// Call nextplayer
			board.nextPlayer(currentPlayer, dieRoll);
			board.repaint();
			
			// Increase offset
			arrayOffset++;
		
		}
	}

	// Accusation listener
	// Handles functions for make an accusation
	public class accusationListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// run all code for game
			System.exit(0);
		}
	}
	
	// Human move listener
	public class MoveHumanPlayer implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (currentPlayer instanceof ComputerPlayer) return;

			boolean validMove = false;

			// IS VALID
			for (BoardCell c : board.getTargets()) {
				if (e.getX() >= c.getColumn() * 25 && e.getX() <= (c.getColumn() * 25) + 25) {
					if (e.getY() >= c.getRow() * 25 + 50 && e.getY() <= (c.getRow() * 25) + 75) {
						user.makeMove(board.getCellAt(c.getRow(), c.getColumn()));
						validMove = true;
						humanIsDone = true;
						board.repaint();
					}
				}
			}
			
			if (!validMove) {
				//Choose a valid target
				JOptionPane errorPane = new JOptionPane();
				errorPane.showMessageDialog(new JFrame(), incorrectLocationMessage, turnNotOverTitle, JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}


		@Override
		public void mouseEntered(MouseEvent arg0) {}
		@Override
		public void mouseExited(MouseEvent arg0) {}
		@Override
		public void mousePressed(MouseEvent arg0) {}
		@Override
		public void mouseReleased(MouseEvent arg0) {}
	}
	
	/**
	 * Returns Human Player
	 * @returns user
	 */
	public Player getUser() {
		return this.user;
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
		pane.showMessageDialog(game, welcomeMessage, welcomeTitle, JOptionPane.INFORMATION_MESSAGE);	
	}
	
}
