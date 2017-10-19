package project2;

import java.util.*;

/**
 * Class creates a simple version of MineSweeper
 * 
 * @author Andrew Olesak
 * @version February 2, 2016
 */

public class MineSweeperGame {

	private Cell[][] board;
	private GameStatus status;
	private int totalMineCount;
	private int rows;
	private int columns;

	/**
	 * Constructor sets values to the instance variables and sets the mines for
	 * the board
	 */
	public MineSweeperGame(int r, int c, int mines) {
		this.rows = r;
		this.columns = c;
		this.totalMineCount = mines;
		this.board = new Cell[this.rows][this.columns];
		for (int row = 0; row < this.rows; row++) {
			for (int col = 0; col < this.columns; col++) {
				this.board[row][col] = new Cell(0, false, false, false);
			}
		}

		Random random = new Random();
		int mineCount = 0;
		while (mineCount < this.totalMineCount) {
			int col = random.nextInt(this.columns);
			int row = random.nextInt(this.rows);
			if (!this.board[row][col].getIsMine()) {
				this.board[row][col].setIsMine(true);
				mineCount++;
			}
		}
	}

	/**
	 * Method marks the cell that has been selected
	 * 
	 * @param row
	 *            the row in which the cell lies
	 * @param col
	 *            the column in which the cell lies
	 */
	public void select(int row, int col) {
		if (this.board[row][col].getIsFlagged()) {
			return;
		}
		this.board[row][col].setIsExposed(true);
	}

	/**
	 * creates a new game object for MineSweeper
	 */
	public MineSweeperGame reset() {
		MineSweeperGame game = new MineSweeperGame(this.rows, this.columns, this.totalMineCount);
		return game;
	}

	/**
	 * Determines whether the player has won or lost the game or the game is not
	 * over yet
	 */
	public GameStatus getGameStatus() {
		int numOfExposedCells = 0;
		for (int row = 0; row < this.rows; row++) {
			for (int col = 0; col < this.columns; col++) {
				if (this.board[row][col].getIsExposed() && this.board[row][col].getIsMine()) {
					this.status = GameStatus.Lost;
					return status;
				} else if (this.board[row][col].getIsExposed()) {
					numOfExposedCells++;
				}
			}
		}
		if (numOfExposedCells == (this.rows * this.columns) - this.totalMineCount) {
			this.status = GameStatus.Won;
			return this.status;
		}
		this.status = GameStatus.NotOverYet;
		return this.status;
	}

	/**
	 * 
	 * @param row
	 *            the row
	 * @param col
	 *            the column
	 * @return the given cell
	 */
	public Cell getCell(int row, int col) {
		return this.board[row][col];
	}
}
