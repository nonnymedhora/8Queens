package org.bawaweb.queens8.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author Navroz Adapted from 'Exhaustive Recursion & Backtracking'
 * Random Depth-First Search, row-by-row with backtracking
 */
public class SolveNQueens extends JFrame {

	private static final int 	DIM = 19; // NUM_QUEENS, change this for resizing

	private boolean[][] 		theGrid;
	private Cell[][] 			theCells;

	public SolveNQueens() {
		theGrid = 	new boolean[DIM][DIM];
		theCells = 	new Cell[DIM][DIM];

		clearBoard(theGrid);
		solve(theGrid, 0);

		theCells = transform(theGrid);

		addCells(theCells);
		// printBoard();
	}

	private void addCells(final Cell[][] cells) {
		this.setLayout(new GridLayout(DIM, DIM));
		for (int r = 0; r < DIM; r++) {
			for (int c = 0; c < DIM; c++) {
				this.add(cells[r][c]);
			}
		}
	}

	private Cell[][] transform(final boolean[][] grid) {
		for (int r = 0; r < DIM; r++) {
			for (int c = 0; c < DIM; c++) {
				if ((c + r) % 2 == 0) {
					theCells[r][c] = new Cell(r, c, Color.BLUE);
				} else {
					theCells[r][c] = new Cell(r, c, Color.GREEN);
				}

				if (grid[r][c]) {
					theCells[r][c].setOccupied(true);
				}
			}
		}
		return theCells;
	}

	private boolean solve(final boolean[][] aGrid, final int row) {
		if (row >= DIM)
			return true;

		for (int col = 0; col < DIM; col++) {
			int aCol = getRandomFreeColCell(aGrid, col);
			if (isSafe(aGrid, row, aCol)) {
				placeQueen(aGrid, row, aCol);

				if (solve(aGrid, row + 1)) {
					return true;
				}

				removeQueen(aGrid, row, aCol);
			}
		}
		return false;
	}

	private int getRandomFreeColCell(final boolean[][] grid, final int col) {
		List<Boolean> freeColCells = new ArrayList<Boolean>();
		for (int row = 0; row < DIM; row++) {
			if (!grid[row][col]) {
				freeColCells.add(grid[row][col]);
			}
		}
		int rand = new Random().nextInt(freeColCells.size());

		return rand;
	}

	private void placeQueen(final boolean[][] grid, final int row, final int col) {
		grid[row][col] = true;

	}

	private void removeQueen(final boolean[][] grid, final int row, final int col) {
		grid[row][col] = false;
	}

	private boolean isSafe(final boolean[][] aGrid, final int row, final int col) {
		return (diagonalClear(aGrid, row, col) && colClear(aGrid, row, col));
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				generateChessBoardFr();
			}
		});

	}

	private void clearBoard(final boolean[][] grid) {
		for (int row = 0; row < DIM; row++) {
			for (int col = 0; col < DIM; col++) {
				grid[row][col] = false;
			}
		}
	}

	private boolean colClear(final boolean[][] aGrid, final int queenRow, final int queenCol) {
		for (int row = 0; row < DIM; row++) {
			if (aGrid[row][queenCol] == true) {
				return false;
			}
		}
		return true;
	}

	private boolean diagonalClear(final boolean[][] aGrid, final int queenRow, final int queenCol) {
		return (lowerDiagonalClear(aGrid, queenRow, queenCol) && upperDiagonalClear(aGrid, queenRow, queenCol));
	}

	private boolean lowerDiagonalClear(final boolean[][] aGrid, final int queenRow, final int queenCol) {
		int row, col = 0;
		for (row = queenRow, col = queenCol; row >= 0 && col >= 0; row--, col--) {
			if (aGrid[row][col] == true) {
				return false;
			}
		}
		for (row = queenRow, col = queenCol; row >= 0 && col < DIM; row--, col++) {
			if (aGrid[row][col] == true) {
				return false;
			}
		}
		return true;
	}

	private boolean upperDiagonalClear(final boolean[][] aGrid, final int queenRow, final int queenCol) {
		int row, col = 0;
		for (row = queenRow, col = queenCol; row < DIM && col >= 0; row++, col--) {
			if (aGrid[row][col] == true) {
				return false;
			}
		}
		for (row = queenRow, col = queenCol; row < DIM && col < DIM; row++, col++) {
			if (aGrid[row][col] == true) {
				return false;
			}
		}
		return true;
	}

	private void printRow(final int row) {
		System.out.print("|");
		for (int col = 0; col < DIM; col++) {
			if (theGrid[row][col]) {
				System.out.print(" " + '\u2655' + " |");
			} else {
				System.out.print("   |");
			}
		}
		System.out.print("\n___________________________________\n");

	}

	private void printBoard() {
		System.out.println("___________________________________");
		for (int row = 0; row < DIM; row++) {
			printRow(row);
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension((int) (37.5 * DIM), (int) (37.5 * DIM));
	}

	private static void generateChessBoardFr() {
		JFrame frame = new SolveNQueens();
		frame.setTitle("BaWaZ ChessBoard");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		}

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

}
