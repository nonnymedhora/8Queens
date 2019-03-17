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
 * With X queens placed on board, places remaining N-X queens
 */
public class SolveRemainingNQueens extends JFrame {

	private static final long serialVersionUID = 6265915711271944049L;

	private static final int 	DIM = 8; // NUM_QUEENS, change this for resizing

	private boolean[][] 		theGrid;
	private Cell[][] 			theCells;
	private Cell[]				placed;

	public SolveRemainingNQueens(Cell[] placedCells) {
		theGrid = 		new boolean[DIM][DIM];
		theCells = 		new Cell[DIM][DIM];

		this.placed = 	placedCells;

		clearBoard();
		solve(0);

		theCells = transform();

		addCells(theCells);

		printBoard();
	}

	private void addCells(final Cell[][] cells) {
		this.setLayout(new GridLayout(DIM, DIM));
		for (int r = 0; r < DIM; r++) {
			for (int c = 0; c < DIM; c++) {
				this.add(cells[r][c]);
			}
		}
	}

	private Cell[][] transform() {
		for (int r = 0; r < DIM; r++) {
			for (int c = 0; c < DIM; c++) {
				if ((c + r) % 2 == 0) {
					theCells[r][c].setColor(Color.BLUE);
				} else {
					theCells[r][c].setColor(Color.GREEN);
				}

				if (theGrid[r][c] && !theCells[r][c].isAlreadyPlaced()) {
					theCells[r][c].setOccupied(true);
				}
			}
		}
		return theCells;
	}

	private boolean solve(final int row) {
		if ( row >= DIM )
			return true;

		if ( getPlacedCellRows().contains(row) ) {
			return (solve(row + 1));
		}

		for (int attemptCount = 0; attemptCount < 10; attemptCount++) {

			for (int col = 0; col < DIM; col++) {
				int aCol = getRandomFreeColCell(row);
				if (isSafe(row, aCol)) {
					placeQueen(row, aCol);

					if (solve(row + 1)) {
						return true;
					}

					removeQueen(row, aCol);
				}
			}
		}
		return false;
	}

	private List<Integer> getPlacedCellRows() {
		List<Integer> placedCellRows = new ArrayList<Integer>();
		for (int i = 0; i < this.placed.length; i++) {
			placedCellRows.add(this.placed[i].getRow());
		}
		return placedCellRows;
	}

	private int getRandomFreeColCell(final int row) {
		List<Boolean> freeColCells = new ArrayList<Boolean>();
		for (int col = 0; col < DIM; col++) {
			if (!this.theGrid[row][col]) {
				freeColCells.add(this.theGrid[row][col]);
			}
		}
		int rand = new Random().nextInt(freeColCells.size());

		return rand;
	}

	private void placeQueen(final int row, final int col) {
		this.theGrid[row][col] = true;
		this.theCells[row][col].setOccupied(true);

	}

	private void removeQueen(final int row, final int col) {
		this.theGrid[row][col] = false;
		this.theCells[row][col].setOccupied(false);
	}

	private boolean isSafe(final int row, final int col) {
		return (diagonalClear(row, col) && colClear(row, col));
	}

	public static void main(String[] args) {
		Cell[] placedCells = new Cell[] { new Cell(0, 4, Cell.PLACED), new Cell(5, 5, Cell.PLACED) };
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				generateChessBoardFr(placedCells);
			}
		});

	}

	private void clearBoard() {
		for (int row = 0; row < DIM; row++) {
			for (int col = 0; col < DIM; col++) {
				theGrid[row][col] = false;
				theCells[row][col] = new Cell(row, col);
			}
		}

		for (int i = 0; i < this.placed.length; i++) {
			int row = this.placed[i].getRow();
			int col = this.placed[i].getColumn();

			theCells[row][col].setAlreadyPlaced(true);
			theGrid[row][col] = true;

		}
	}

	private boolean colClear(final int queenRow, final int queenCol) {
		for (int row = 0; row < DIM; row++) {
			if (this.theGrid[row][queenCol] == true) {
				return false;
			}
		}
		return true;
	}

	private boolean diagonalClear(final int queenRow, final int queenCol) {
		return (lowerDiagonalClear(queenRow, queenCol) && upperDiagonalClear(queenRow, queenCol));
	}

	private boolean lowerDiagonalClear(final int queenRow, final int queenCol) {
		int row, col = 0;

		for (row = queenRow, col = queenCol; row >= 0 && col >= 0; row--, col--) {
			if (this.theGrid[row][col] == true) {
				return false;
			}
		}
		for (row = queenRow, col = queenCol; row >= 0 && col < DIM; row--, col++) {
			if (this.theGrid[row][col] == true) {
				return false;
			}
		}
		return true;
	}

	private boolean upperDiagonalClear(final int queenRow, final int queenCol) {
		int row, col = 0;

		for (row = queenRow, col = queenCol; row < DIM && col >= 0; row++, col--) {
			if (this.theGrid[row][col] == true) {
				return false;
			}
		}
		for (row = queenRow, col = queenCol; row < DIM && col < DIM; row++, col++) {
			if (this.theGrid[row][col] == true) {
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

	private static void generateChessBoardFr(Cell[] placedCells) {
		JFrame frame = new SolveRemainingNQueens(placedCells);
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
