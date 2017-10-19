package project2;

import javax.swing.*;

/**
 * Class runs a main method to create the GUI in a JFrame
 * 
 * @author Andrew Olesak
 * @version February 2, 2016
 */
public class MineSweeper extends JFrame {

	public static void main(String[] args) {
		JFrame frame = new JFrame("MineSweeper");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// take input for the dimensions of the game
		// check to see if the input is valid, otherwise set to default values
		int rows = 0;
		int columns = 0;
		int totalMines = 0;
		String squares = JOptionPane.showInputDialog(null, "Enter the size of the board between 3 and 30:");
		String mines = JOptionPane.showInputDialog(null, "Enter the number of mines:");
		try {
			rows = Integer.parseInt(squares);
			columns = rows;
			totalMines = Integer.parseInt(mines);
			if (rows < 3 || rows > 30) {
				throw new Exception();
			} else if (totalMines < 1 || totalMines >= rows * columns) {
				throw new Exception();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "You did not enter valid input");
			rows = 10;
			columns = 10;
			totalMines = 10;
		}

		MineSweeperPanel panel = new MineSweeperPanel(rows, columns, totalMines);
		frame.getContentPane().add(panel);

		frame.pack();
		frame.setVisible(true);
	}

}
