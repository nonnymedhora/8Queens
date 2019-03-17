package org.bawaweb.queens8.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author Navroz
 *
 */
public class ChessBoard extends JFrame {	

	private static final long serialVersionUID = 6112447898458586870L;

	private static final int 	DIM = 8;
	private Cell[][] 			theCells = new Cell[DIM][DIM];
	

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				generateChessBoardFr();
			}
		});

	}
	private static void generateChessBoardFr() {
		JFrame frame = new ChessBoard();
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

	public ChessBoard() {
		super();
		
		initializeCells();
		getQueenCells();
	}

	private void initializeCells() {
		this.setLayout(new GridLayout(DIM, DIM));

		for (int row = 0; row < DIM; row++) {
			for (int col = 0; col < DIM; col++) {
				if ((col + row) % 2 == 0) {
					theCells[row][col] = new Cell(row, col, Color.BLUE);
				} else {
					theCells[row][col] = new Cell(row, col, Color.GREEN);
				}
				
				this.add(theCells[row][col]);
			}
		}
		
	}

	private void getQueenCells() {
		Random r = new Random();
		int x = r.nextInt(DIM);
		generateForQueens(x);
	}

	private void generateForQueens(final int seed) {
		// starts with placing a queen
		// on row 1, col seed
		// rows.cols - 1 to 8 indexed 0 to 7
		int col = seed == 0 ? seed : seed-1;
		Cell seedCell = theCells[0][col];
		blockCellsFor(seedCell);
		seedCell.setOccupied(true);
		
		proceedAfterSeed( seedCell );

	}

	private void proceedAfterSeed(final Cell startCell) {
		int row = startCell.getRow();
		int col = startCell.getColumn();
		boolean broken = false;
		while( row < DIM && !broken) {
			row+=1;	//	go to next row
			if(row < DIM) {
				if ( getFreeCells(row) != null ) {
					Cell[] freeCells = getFreeCells(row);
					Cell selection = randomlySelectCell(freeCells);
					blockCellsFor(selection);
					selection.setOccupied(true);
				} else {
					// startOver iteration from top
					// unable to place   all cells in next
					// row are blocked occupied
					broken = true;
					break;
				}
			}
			
		}
		if( broken ) {
			startOver();
		}
		
	}	

	private void startOver() {
		this.theCells = null;
		this.dispose();
		generateChessBoardFr();
	}

	private Cell randomlySelectCell(final Cell[] freeCells) {
		Random r = new Random();
		int x = r.nextInt(freeCells.length);
		return freeCells[x];
	}

	private Cell[] getFreeCells(final int row) {
		List<Cell> theFreeList = new ArrayList<Cell>();
		Cell[] theFreeCells = null;
		//--get-free-cells-in-the-row
		for(int col = 0; col < DIM; col++) {
			if( !theCells[row][col].isOccupied() && !theCells[row][col].isBlocked() ) {
				theFreeList.add(theCells[row][col]);
			}
		}
		//--remove-cell-4-cols-which-have-occupied
		for( int i = 0; i < theFreeList.size(); i++) {
			final int col = theFreeList.get(i).getColumn();
			
			for(int r = 0; r < DIM; r++) {
				if( theCells[r][col].isOccupied()) {
					theFreeList.remove(i);
				}
			}
		}
		
		final int freeListSize = theFreeList.size();
		if ( freeListSize > 0 ) {
			theFreeCells = new Cell[freeListSize];
			for( int i = 0; i < freeListSize; i++) {
				theFreeCells[i] = theFreeList.get(i);
			}
		}
		return theFreeCells;
	}

	private void blockCellsFor(final Cell aCell) {
		final int row = aCell.getRow();
		final int col = aCell.getColumn();

		blockRowCells(row);
		blockColCells(col);
		blockDiagonalCells(row, col);
		
		repaint();

	}

	private void blockDiagonalCells(final int row, final int col) {
		int startRow = row;
		int startCol = col;
		
		//	l-r,t-d		(col-increases....r-increases)
		boolean broken = false;
		for(int r = startRow; r < DIM && !broken;  ) {
			for(int c = startCol; c < DIM & !broken;   ) {
				r+=1;
				c+=1;//System.out.println("r is "+r+" and c is "+c);
				if( r == DIM || c == DIM ) {
					broken = true;
					break;
				}
				if( r < DIM && c < DIM) {
					if( !theCells[r][c].isOccupied() )
						theCells[r][c].setBlocked(true);	
				}
			}		
			
		}
		
		// l-r,d-t	(col-increases......r-decreases)
		broken = false;
		for(int r = startRow; r >= 0 && !broken;  ) {
			for( int c = startCol; c < DIM && !broken;  ) {
				r-=1;
				c+=1;
				if( r < 0 || c == DIM ) {
					broken = true;
					break;
				}
				if( r >= 0 && c < DIM) {
					if(!theCells[r][c].isOccupied()) 
						theCells[r][c].setBlocked(true);
				}				
			}
		}
		
		//	r-l,t-d		(col-decreases....row-increases)
		broken = false;
		for(int r = startRow; r < DIM && !broken;  ) {
			for( int c = startCol; c >= 0 && !broken;  ) {
				r+=1;
				c-=1;
				if( r == DIM || c < 0 ) {
					broken = true;
					break;
				}
				if( r < DIM && c >= 0 ) {
					if(!theCells[r][c].isOccupied())
						theCells[r][c].setBlocked(true);
				}
			}
		}
		
		//	r-l,d-t		(col-decreases.....row-decreases)
		broken = false;
		for(int r = startRow; r >= 0 && !broken;  ) {
			for( int c = startCol; c >= 0 && !broken;  ) {
				r-=1;
				c-=1;
				if( r < 0 || c < 0 ) {
					broken = true;
					break;
				}
				if( r >= 0 && c >= 0 ) {
					if(!theCells[r][c].isOccupied())
						theCells[r][c].setBlocked(true);
				}
			}
		}		

	}

	private void blockColCells(final int col) {
		for (int row = 0; row < DIM; row++) {
			if (!theCells[row][col].isOccupied()) {
				theCells[row][col].setBlocked(true);
			}
		}
	}

	private void blockRowCells(final int row) {
		for (int col = 0; col < DIM; col++) {
			if (!theCells[row][col].isOccupied()) {
				theCells[row][col].setBlocked(true);
			}
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(300, 300);
	}

}
