/**
 * 
 */
package org.bawaweb.queens8.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

/**
 * @author Navroz
 *
 */
public class Cell extends JComponent {
	
	private int 		row;
	private int 		column;

	private final int 	height 	= 25;
	private final int 	width 	= 25;
	
	private boolean 	blocked;
	private boolean 	occupied;
	
	private boolean 	alreadyPlaced = false;
	public static final String PLACED = "placed";

	private Color 		color;

	public Cell() {
		super();
	}

	public Cell(int x, int y) {
		super();
		this.row = x;
		this.column = y;
	}

	public Cell(int x, int y, String placed) {
		super();
		this.row = x;
		this.column = y;
		
		if(PLACED.equalsIgnoreCase(placed)) {
			this.setAlreadyPlaced(true);
		}
	}

	public Cell(Color coll) {
		super();
		this.setColor(coll);
	}

	public Cell(int row, int column, Color color) {
		super();
		this.row = row;
		this.column = column;
		this.color = color;		
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(this.color);

		g2.fillRect(row, column, width, height);
		
		if(this.occupied && !this.alreadyPlaced) {
			String aQueen = String.valueOf('\u2655');
			Color textColor = Color.BLACK;
            Color bgColor = Color.WHITE;

            g2.setColor(bgColor);
            g2.fillRect(row, column, width, height);
            
            FontMetrics fm = g.getFontMetrics();
            Font aFont = fm.getFont();
            Font bigFont = aFont.deriveFont((float) 25.0);
            
            g2.setFont(bigFont);

            g.setColor(textColor);
            g.drawString(aQueen, this.row, this.column+20);
            
		} else if(this.alreadyPlaced) {
			String aQueen = String.valueOf('\u2655');
			Color textColor = Color.RED;
            Color bgColor = Color.WHITE;

            g2.setColor(bgColor);
            g2.fillRect(row, column, width, height);
            
            FontMetrics fm = g.getFontMetrics();
            Font aFont = fm.getFont();
            Font bigFont = aFont.deriveFont((float) 25.0);
            
            g2.setFont(bigFont);

            g.setColor(textColor);
            g.drawString(aQueen, this.row, this.column+20);
		}
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
		repaint();
	}

	public boolean isOccupied() {
		return occupied;
	}

	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
		if(this.occupied) {
			this.color = Color.YELLOW;
		}
		repaint();
	}

	public boolean isAlreadyPlaced() {
		return alreadyPlaced;
	}

	public void setAlreadyPlaced(boolean alreadyPlaced) {
		this.alreadyPlaced = alreadyPlaced;
	}

}
