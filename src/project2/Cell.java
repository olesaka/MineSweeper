package project2;

/**
 * Class that represents all the aspects of a single minesweeper cell
 * 
 * @author Andrew Olesak
 * @version February 2, 2016
 *
 */
public class Cell {

	private int mineCount;
	private boolean isFlagged;
	private boolean isExposed;
	private boolean isMine;

	/**
	 * Constructor sets values to the instance variables
	 * 
	 * @param c
	 *            the number of surrounding mines
	 * @param f
	 *            whether the cell is flagged
	 * @param e
	 *            whether the cell is exposed
	 * @param m
	 *            whether the cell is a mine
	 */
	public Cell(int c, boolean f, boolean e, boolean m) {
		this.mineCount = c;
		this.isFlagged = f;
		this.isExposed = e;
		this.isMine = m;
	}

	/**
	 * Sets the mine count
	 * 
	 * @param c
	 *            the mine count
	 */
	public void setMineCount(int c) {
		this.mineCount = c;
	}

	/**
	 * Sets whether a square is flagged
	 * 
	 * @param p
	 *            true if the cell is flagged, otherwise false
	 */
	public void setIsFlagged(boolean p) {
		this.isFlagged = p;
	}

	/**
	 * Sets whether a square is exposed
	 * 
	 * @param p
	 *            true if the cell is exposed, otherwise false
	 */
	public void setIsExposed(boolean p) {
		this.isExposed = p;
	}

	/**
	 * Sets whether a squre contains a mine
	 * 
	 * @param p
	 *            true if the cell is a mine, otherwise false
	 */
	public void setIsMine(boolean p) {
		this.isMine = p;
	}

	/**
	 * @return the number of surrounding mines
	 */
	public int getMineCount() {
		return this.mineCount;
	}

	/**
	 * @return true if the cell is flagged, otherwise false
	 */
	public boolean getIsFlagged() {
		return this.isFlagged;
	}

	/**
	 * @return true if the cell is exposed, otherwise false
	 */
	public boolean getIsExposed() {
		return this.isExposed;
	}

	/**
	 * @return true if the cell is a mine, otherwise false
	 */
	public boolean getIsMine() {
		return this.isMine;
	}
}
