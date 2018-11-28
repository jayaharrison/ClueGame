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
	private static String cpuWinsTitle = "Winner!";
	
	private Player user = new Player();
	private Set<Card> userHand = new HashSet<Card>();
	
	private int arrayOffset = 0;
	private Player currentPlayer;
	private GuessDialog guessDialog;
	private String cpuSuggestion;
	private String response;
	private String cpuWins;
	
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
			// goes to next player in list

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
			
			// NEED TO HANDLE IN BOARD< NEXTPLAYER FUNCTION
//			BoardCell temp = board.getCellAt(players.get(currentPlayer).getRow(), players.get(currentPlayer).getColumn());
//			if (temp.getInitial() != 'W') {
//				cpuPlayer.createSuggestion();
//				cpuSuggestion = cpuPlayer.getSuggestion().toString();
//				
//				if(board.handleSuggestion(cpuPlayer, cpuPlayer.getSuggestion(), players) == null) {
//					//Player wins
//					cpuPlayer.correctGuess = true;
//					response = board.handleSuggestion(cpuPlayer, cpuPlayer.getSuggestion(), players).getName();
//					cpuWins = cpuPlayer.getPlayerName() + " wins!" + "Answer: " + response;
//					JOptionPane winnerPane = new JOptionPane();
//					winnerPane.showMessageDialog(new JFrame(), cpuWins, cpuWinsTitle, JOptionPane.INFORMATION_MESSAGE);
//					gui.responseField.setText("No new clue");
//				}
//				else {
//					response = board.handleSuggestion(cpuPlayer, cpuPlayer.getSuggestion(), players).getName();
//					gui.responseField.setText(response);
//				}
//				gui.guessField.setText(cpuSuggestion);
//				for (Player p : players) {
//					if (p.getPlayerName().equals(cpuPlayer.getSuggestion().getPerson().getName())) {
//						p.setLocation(cpuPlayer.getRow(), cpuPlayer.getColumn());
//					}
//				}
//			}
			
			// Call nextplayer
			board.nextPlayer(currentPlayer, dieRoll);
			//TODO: update guess GUI, then repaint
			board.repaint();

			// Increase offset
			arrayOffset++;			
//			
//			humanIsDone = true;
//			
		
		}
	}

	// Accusation listener
	// Handles functions for make an accusation
	public class accusationListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// TODO: Fix
//			if (currentPlayer != 0) {
//				JOptionPane errorPane = new JOptionPane();
//				errorPane.showMessageDialog(new JFrame(), cannotMakeAccusation, turnNotOverTitle, JOptionPane.INFORMATION_MESSAGE);
//				return;
//			}
//			guessDialog = new GuessDialog();
//			guessDialog.setVisible(true);
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
					
						// TODO: Fix
//						if(board.getCellAt(c.getRow(), c.getColumn()).getInitial() != 'W') {
//							BoardCell temp = board.getCellAt(players.get(0).getRow(), players.get(0).getColumn());
//							String roomName = board.getLegend().get(temp.getInitial());
//							guessDialog = new GuessDialog(roomName);
//							guessDialog.setVisible(true);
//							System.out.println(guessDialog.savedPersonGuess);
//						}
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
		
	public class SubmitAccusationClick implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			//TODO: Fix
//			guessDialog.savedRoomGuess = (String)guessDialog.roomsDropdown.getSelectedItem();
//			guessDialog.savedPersonGuess = (String) guessDialog.playersDropdown.getSelectedItem();
//			guessDialog.savedWeaponGuess = (String) guessDialog.weaponsDropdown.getSelectedItem();
//			Solution s = new Solution(board.getSpecificCard(guessDialog.savedRoomGuess), board.getSpecificCard(guessDialog.savedPersonGuess), board.getSpecificCard(guessDialog.savedWeaponGuess));
//			board.handleSuggestion(players.get(currentPlayer), s, players);
		}
		
	}
	
	public class SubmitSuggestionClick implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			//TODO: Fix
//			BoardCell b = board.getCellAt(players.get(currentPlayer).getRow(), players.get(currentPlayer).getColumn());
//			
//			guessDialog.savedRoomGuess = board.getLegend().get(b);
//			guessDialog.savedPersonGuess = (String) guessDialog.playersDropdown.getSelectedItem();
//			guessDialog.savedWeaponGuess = (String) guessDialog.weaponsDropdown.getSelectedItem();
//			
//			Solution s = new Solution(board.getSpecificCard(guessDialog.savedRoomGuess), board.getSpecificCard(guessDialog.savedPersonGuess), board.getSpecificCard(guessDialog.savedWeaponGuess));
//			board.handleSuggestion(players.get(currentPlayer), s, players);
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
