package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import clueGame.Board;
import clueGame.BoardCell;

import experiment.GUI_Example;

public class ClueGame extends JFrame {
	private ControlGUI gui;
	private Board board;
	
	public ClueGame() {
		
//		gui = new ControlGUI();
//		setSize(1000, 1000);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		gui = new ControlGUI();
		
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueRooms.txt", "CluePlayers.txt", "ClueWeapons.txt");
		board.initialize();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 1000);
		add(board, BorderLayout.CENTER);
		add(gui, BorderLayout.SOUTH);
		
	}

	
	
	
	public static void main(String[] args) {
		//Create a JFrame with all the normal functionality
		ClueGame game = new ClueGame();
		//game.pack();
		game.setVisible(true);
		
	}
	
}
