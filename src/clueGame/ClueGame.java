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
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.peer.WindowPeer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import clueGame.Board;
import clueGame.BoardCell;
import experiment.FileMenu;
import experiment.GUI_Example;
import sun.awt.WindowIDProvider;

public class ClueGame extends JFrame {
	private ControlGUI gui;
	private MyCardsGUI side;
	private FileMenu fileMenu;
	private Board board;
	private ArrayList<Player> players;
	
	private static String welcomeTitle = "Welcome to Clue";
	private static String welcomeMessage = "You are Miss Scarlet, press Next Player to begin play";
	private static String errorTitle = "Error";
	private static String turnNotOverMessage = "Please finish your turn before pressing Next Player.";
	private static String incorrectLocationMessage = "Please select a valid space.";
	private static String poorlyTimedAccusation = "You can only make an accusation at the beginning of your turn.";
	private static String unableToMakeAccusation = "You can only make an accusation when inside of a room.";
	private static String winTitle = "Winner!";
	private static String exitTitle = "Game Over";
	private static String exitMsg = "Thank you for playing, please exit the game at this time. Restart if you wish to play again.";
	private static String wrongAnswerTitle = "Incorrect Accusation : Player Lost";
	private static String wrongAnswerMsg = " submitted an incorrect accusation. The player has been removed from the game.";
	
	private Player user = new Player();
	private Set<Card> userHand = new HashSet<Card>();
	
	private int arrayOffset = 0;
	private Player currentPlayer;
	private GuessDialog guessDialog;
	private String cpuSuggestion;
	private String response;
	private String cpuWins;
	private String humanWins;
	
	private boolean humanIsDone = false;
	private boolean humanGuessSubmitted = false;
	private boolean gameWon = false;
	
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
				errorPane.showMessageDialog(new JFrame(), turnNotOverMessage, errorTitle, JOptionPane.INFORMATION_MESSAGE);
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

			//Handle computer accusation before movement
			if (arrayOffset > 0 && currentPlayer.isInRoom()) {
				if (((ComputerPlayer) currentPlayer).getAccuseFlag()) {
					if (board.checkAccusaton(((ComputerPlayer) currentPlayer).getSuggestion()) ) {
						//Comp player wins
						response = ((ComputerPlayer) currentPlayer).getSuggestion().toString();
						cpuWins = currentPlayer.getPlayerName() + " wins!" + "Answer: " + response;

						JOptionPane winnerPane = new JOptionPane();
						winnerPane.showMessageDialog(new JFrame(), cpuWins, winTitle, JOptionPane.INFORMATION_MESSAGE);

						// Exit
						JOptionPane exitPane = new JOptionPane();
						exitPane.showMessageDialog(new JFrame(), exitMsg, exitTitle, JOptionPane.INFORMATION_MESSAGE);
						System.exit(0);
					}
					else if (!board.isCorrectGuess()){
						//player loses
						//Player is kicked message
						currentPlayer.setAlive(false);
						board.showCardsOnDeath(currentPlayer);
						players.remove(currentPlayer);

						//Message
						JOptionPane kickedPane = new JOptionPane();
						kickedPane.showMessageDialog(new JFrame(), currentPlayer.getPlayerName() + wrongAnswerMsg, wrongAnswerTitle, JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
			// Call draws targets for human
			board.nextPlayer(currentPlayer, dieRoll, players);
			board.repaint();

			// Check suggestion- comp
			if (arrayOffset > 0 && currentPlayer.isInRoom()) {
				//handle computer suggestion, created inside of Board.nextPlayer
				gui.updateGuessGUI(((ComputerPlayer) currentPlayer).getSuggestion(), board.getDisproven());
			}

			// Increase offset
			arrayOffset++;					
		}
	}

	// Accusation listener
	// Handles functions for making an accusation
	public class accusationListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (humanIsDone || currentPlayer instanceof ComputerPlayer) {
				JOptionPane errorPane = new JOptionPane();
				errorPane.showMessageDialog(new JFrame(), poorlyTimedAccusation, errorTitle, JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			else if (!currentPlayer.isInRoom()) {
				JOptionPane errorPane = new JOptionPane();
				errorPane.showMessageDialog(new JFrame(), unableToMakeAccusation, errorTitle, JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			else {
				JDialog accuseDialog = new GuessDialog(currentPlayer);
				accuseDialog.setModalityType(ModalityType.APPLICATION_MODAL);
				accuseDialog.setVisible(true);
				
				if (((HumanPlayer) currentPlayer).getSuggestionFlag()) {
					Card proof = board.handleSuggestion(currentPlayer, ((HumanPlayer) currentPlayer).getHumanSuggestion(), players);
					gui.updateGuessGUI(((HumanPlayer) currentPlayer).getHumanSuggestion(), proof);
					
					if (gameWon) {
						//game over, human wins
						humanWins = currentPlayer.getPlayerName() + " wins! Congratulations!";
						JOptionPane winnerPane = new JOptionPane();
						winnerPane.showMessageDialog(new JFrame(), humanWins, winTitle, JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						//game over, human loses, comps continue
						currentPlayer.setAlive(false);
						players.remove(currentPlayer);
						humanIsDone = true;
						board.repaint();

						//Message
						JOptionPane kickedPane = new JOptionPane();
						kickedPane.showMessageDialog(new JFrame(), "You" + wrongAnswerMsg, wrongAnswerTitle, JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		}
	}
	
	// Human move listener, handles suggestions
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
						
						// Draw suggestion box
						if (currentPlayer.isInRoom()) {
							guessDialog = new GuessDialog(currentPlayer);
							guessDialog.setModalityType(ModalityType.APPLICATION_MODAL);
							guessDialog.setVisible(true);
							
							// If guess submitted, handle suggestion, update GUI
							if (((HumanPlayer) currentPlayer).getSuggestionFlag()) {
								Card proof = board.handleSuggestion(currentPlayer, ((HumanPlayer) currentPlayer).getHumanSuggestion(), players);
								gui.updateGuessGUI(((HumanPlayer) currentPlayer).getHumanSuggestion(), proof);
							}
						}
						((HumanPlayer) currentPlayer).setSuggestionFlag(false);
						humanIsDone = true;
						board.repaint();
					}
				}
			}
			
			if (!validMove) {
				//Choose a valid target
				JOptionPane errorPane = new JOptionPane();
				errorPane.showMessageDialog(new JFrame(), incorrectLocationMessage, errorTitle, JOptionPane.INFORMATION_MESSAGE);
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
