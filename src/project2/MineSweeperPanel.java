package project2;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Class Creates a panel for the user to interact with
 * 
 * @author Andrew Olesak
 * @version February 2, 2016
 */

public class MineSweeperPanel extends JPanel {

	private JButton[][] board;
	private Cell iCell;
	private JButton quiteButton;
	private MineSweeperGame game;
	private int rows;
	private int columns;
	private int numOfMines;
	private boolean expandBoard;
	private int wins;
	private int loses;
	private JLabel winLabel;
	private JLabel loseLabel;
	private JLabel minesLeftLabel;
	private int minesLeft;

	/**
	 * Constructor initializes instance variables and creates the board and
	 * JPanels
	 */
	public MineSweeperPanel(int r, int c, int mines) {
		this.rows = r;
		this.columns = c;
		this.numOfMines = mines;
		this.expandBoard = true;
		this.wins = 0;
		this.loses = 0;
		this.minesLeft = mines;

		// sets the size of the board
		this.board = new JButton[this.rows][this.columns];

		// creates a new minesweeper game object
		this.game = new MineSweeperGame(this.rows, this.columns, numOfMines);

		// create a JPanel for the quit button
		JPanel menu = new JPanel();
		this.quiteButton = new JButton("quit");
		this.quiteButton.addActionListener(new ButtonListener());
		menu.add(this.quiteButton);

		// create a JPanel for the wins and loses labels
		JPanel status = new JPanel();
		status.setLayout(new GridLayout(1, 2));
		this.minesLeftLabel = new JLabel("Mines Left: " + this.minesLeft);
		status.add(this.minesLeftLabel);
		
		this.winLabel = new JLabel("Wins: " + this.wins);
		status.add(this.winLabel);
		this.loseLabel = new JLabel("Loses: " + this.loses);
		status.add(this.loseLabel);

		// create a JPanel for the board
		JPanel gamePanel = new JPanel();
		gamePanel.setLayout(new GridBagLayout());
		GridBagConstraints place = new GridBagConstraints();
		place.gridheight = this.rows;
		place.gridwidth = this.columns;
		place = new GridBagConstraints();

		// instantiate the buttons
		Icon emptyIcon = new ImageIcon("square.png");
		for (int row = 0; row < this.rows; row++) {
			for (int col = 0; col < this.columns; col++) {
				this.board[row][col] = new JButton("", emptyIcon);
				this.board[row][col].addActionListener(new ButtonListener());
				this.board[row][col].setBorder(BorderFactory.createEmptyBorder());
				this.board[row][col].addMouseListener(new RightMouse());
				place.gridx = row;
				place.gridy = col;
				gamePanel.add(this.board[row][col], place);
			}
		}

		// organize the panels using a GridBag layout
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(status);
		add(gamePanel);
		add(menu);

		// assigns the number of surrounding mines to each cell
		this.setAllMineCounts();
	}

	/**
	 * helper method that sets the appropriate icon to the JButtons
	 */
	private void displayBoard() {
		for (int row = 0; row < this.rows; row++) {
			for (int col = 0; col < this.columns; col++) {
				iCell = game.getCell(row, col);
				if (iCell.getIsExposed() && iCell.getIsMine()) {
					this.board[row][col].setText("");
				} else if (iCell.getIsExposed()) {
					this.board[row][col].setEnabled(false);
					this.board[row][col].setText("" + iCell.getMineCount());
					this.board[row][col].setHorizontalTextPosition(JButton.CENTER);
				} else {
					this.board[row][col].setEnabled(true);
					this.board[row][col].setText("");
				}
			}
		}
	}

	/**
	 * Calculates the number of surrounding mines for each cell
	 * 
	 * @param r
	 *            the row of the cell
	 * @param c
	 *            the column of the cell
	 * @return the number of surrounding mines
	 */
	private int getNumOfSurroundingMines(int r, int c) {
		int mineCounter = 0;
		for (int row = r - 1; row < r + 2; row++) {
			for (int col = c - 1; col < c + 2; col++) {
				if (row < 0 || col < 0 || row >= this.rows || col >= this.columns) {
					continue;
				} else if (this.game.getCell(row, col).getIsMine()) {
					mineCounter++;
				}
			}
		}
		return mineCounter;
	}

	/**
	 * Sets all the mine counts for each cell
	 */
	private void setAllMineCounts() {
		for (int row = 0; row < this.rows; row++) {
			for (int col = 0; col < this.columns; col++) {
				iCell = game.getCell(row, col);
				iCell.setMineCount(this.getNumOfSurroundingMines(row, col));
			}
		}
	}

	/**
	 * shows the location of all the mines
	 */
	private void showAllMines() {
		Icon mine = new ImageIcon("mine.png");
		for (int row = 0; row < this.rows; row++) {
			for (int col = 0; col < this.columns; col++) {
				iCell = game.getCell(row, col);
				if (iCell.getIsMine()) {
					board[row][col].setIcon(mine);
				}
			}
		}
	}

	/**
	 * Expands the cells around exposed cells that have no surrounding mines
	 */
	private void expandBoard() {
		this.expandBoard = true;
		for (int row = 0; row < this.rows; row++) {
			for (int col = 0; col < this.columns; col++) {
				if (!this.board[row][col].isEnabled() && this.game.getCell(row, col).getMineCount() == 0) {
					for (int r = row - 1; r < row + 2; r++) {
						for (int c = col - 1; c < col + 2; c++) {
							if (r < 0 || c < 0 || r >= this.rows || c >= this.columns) {
								continue;
							} else if (this.board[r][c].isEnabled()) {
								iCell = this.game.getCell(r, c);
								iCell.setIsExposed(true);
								this.displayBoard();
								this.expandBoard = false;
							}
						}
					}
				}
			}
		}
	}

	/**
	 * resets the buttons of the board
	 */
	private void resetBoard() {
		Icon empty = new ImageIcon("square.png");
		for (int row = 0; row < this.rows; row++) {
			for (int col = 0; col < this.columns; col++) {
				this.board[row][col].setIcon(empty);
			}
		}
	}

	private void gameStatus() {
		if (game.getGameStatus() == GameStatus.Lost) {
			this.showAllMines();
			JOptionPane.showMessageDialog(null, "You Lost!");
			this.game = game.reset();
			this.setAllMineCounts();
			this.resetBoard();
			this.displayBoard();
			this.loses++;
			this.minesLeft = numOfMines;
			this.minesLeftLabel.setText("Mines Left: " + this.minesLeft);
			this.loseLabel.setText("Loses: " + this.loses);
		} else if (game.getGameStatus() == GameStatus.Won) {
			JOptionPane.showMessageDialog(null, "You Won!");
			this.game = game.reset();
			this.setAllMineCounts();
			this.resetBoard();
			this.displayBoard();
			this.wins++;
			this.minesLeft = numOfMines;
			this.minesLeftLabel.setText("Mines Left: " + this.minesLeft);
			this.winLabel.setText("Wins: " + this.wins);
		}
	}

	/**
	 * Class checks for and reacts to right clicks
	 * 
	 * @author Andrew Olesak
	 * @version February 11, 2016
	 */
	private class RightMouse implements MouseListener {

		/**
		 * Reacts to a right click to put flags markers on the grid
		 * 
		 * @param e
		 *            the button that was clicked on the mouse
		 */
		public void mouseClicked(MouseEvent e) {
			Icon flag = new ImageIcon("Flag.png");
			Icon empty = new ImageIcon("square.png");
			if (e.getButton() == MouseEvent.BUTTON3) {
				for (int row = 0; row < rows; row++) {
					for (int col = 0; col < columns; col++) {
						if (e.getSource() == board[row][col]) {
							iCell = game.getCell(row, col);
							if (iCell.getIsExposed()) {
								return;
							} else if (iCell.getIsFlagged()) {
								iCell.setIsFlagged(false);
								board[row][col].setIcon(empty);
								minesLeft++;
								minesLeftLabel.setText("Mines Left: " + minesLeft);
							} else {
								board[row][col].setIcon(flag);
								iCell.setIsFlagged(true);
								minesLeft--;
								minesLeftLabel.setText("Mines Left: " + minesLeft);
							}
						}
					}
				}
			}
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}
	}

	/**
	 * Class reacts to buttons being pressed and changes the board accordingly
	 * 
	 * @author Andrew Olesak
	 * @version Februrary 8, 2016
	 */
	private class ButtonListener implements ActionListener {

		/**
		 * Reacts to the buttons on the board being pressed
		 */
		public void actionPerformed(ActionEvent e) {

			// react to the close button pressed
			if (e.getSource() == quiteButton) {
				int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?");
				if (reply == JOptionPane.YES_OPTION) {
					System.exit(0);
				} else {
					return;
				}
			}

			// react to each cell being pressed
			for (int row = 0; row < rows; row++) {
				for (int col = 0; col < columns; col++) {
					if (e.getSource() == board[row][col]) {
						board[row][col].setEnabled(false);
						game.select(row, col);
					}
				}
			}
			displayBoard();

			// expands the board so all edges contain at least one mine
			expandBoard = false;
			while (!expandBoard) {
				expandBoard();
			}

			// determine if there is a winner or loser
			gameStatus();
		}
	}
}