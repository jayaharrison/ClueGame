/**
 * 
 * @author Adam Kinard
 * @author Jay Harrison
 *
 *This class represents one cell in the grid.
 *Includes all member variables of the cells along with various test methods and getters/setters
 *
 */

package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class BoardCell {
	private int row;
	private int column;
	private char initial;
	private DoorDirection direction;
	
	/**
	 * Constructor with only row and column, for testing purposes
	 * @param row
	 * @param column
	 */
	public BoardCell(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Constructor for new board cell including row, column, room initial, and door direction params
	 * @param row
	 * @param column
	 * @param initial
	 * @param direction
	 */
	public BoardCell(int row, int column, char initial, DoorDirection direction) {
		this.row = row;
		this.column = column;
		this.initial = initial;
		this.direction = direction;
	}
	
	/**
	 * Get room initial
	 * @return
	 */
	public char getInitial() {
		return initial;
	}
	
	/**
	 * Get door direction
	 * @return
	 */
	public DoorDirection getDoorDirection() {
		return direction;
	}
	
	/**
	 * Get row val
	 * @return
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Set row val
	 * @param row
	 */
	public void setRow(int row) {
		this.row = row;
	}
	
	/**
	 * Get column val
	 * @return
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Set column val
	 * @param column
	 */
	public void setColumn(int column) {
		this.column = column;
	}
	
	/**
	 * Walkway boolean
	 * @return
	 */
	public boolean isWalkway() {
		return (initial == 'W');
	}
	
	/**
	 * Room boolean
	 * @return
	 */
	public boolean isRoom() {
		return (initial != 'W' && initial != 'X');
	}
	
	/**
	 * Doorway boolean
	 * @return
	 */
	public boolean isDoorway() {
		return (direction != DoorDirection.NONE);
	}
	
	/**
	 * BoardCell draw function for swing
	 * @param g
	 */
	public void drawCell(Graphics g) {
		
		switch(this.initial) {
		
		case 'W':
			//draw walkway
			g.setColor(Color.YELLOW);
			g.drawRect(this.column * 25, this.row * 25, 25, 25);
			g.fillRect(this.column * 25, this.row * 25, 25, 25);
			g.setColor(Color.BLACK);
			g.drawRect(this.column * 25, this.row * 25, 25, 25);
			break;
		default:
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(this.column * 25, this.row * 25, 25, 25);
			break;
			
		}
		
		//Specify Door Direction
		if(this.direction != DoorDirection.NONE) {
			if(this.direction == DoorDirection.UP) {
				g.setColor(Color.BLUE);
				g.drawRect(this.column * 25, this.row * 25, 25, 3);
				g.fillRect(this.column * 25, this.row * 25, 25, 3);
			}
			if(this.direction == DoorDirection.DOWN) {
				g.setColor(Color.BLUE);
				g.drawRect(this.column * 25, this.row * 25 + 22, 25, 3);
				g.fillRect(this.column * 25, this.row * 25 + 22, 25, 3);
			}
			if(this.direction == DoorDirection.LEFT) {
				g.setColor(Color.BLUE);
				g.drawRect(this.column * 25, this.row * 25, 3, 25);
				g.fillRect(this.column * 25, this.row * 25, 3, 25);
			}
			if(this.direction == DoorDirection.RIGHT) {
				g.setColor(Color.BLUE);
				g.drawRect(this.column * 25 + 22, this.row * 25, 3, 25);
				g.fillRect(this.column * 25 + 22, this.row * 25, 3, 25);
			}
		
		}
		
	}

}