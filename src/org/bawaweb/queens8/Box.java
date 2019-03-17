package org.bawaweb.queens8;

import org.bawaweb.queens8.ui.Cell;

/**
 * @author Navroz
 * A logical representation of Cell
 */
public class Box {

	public enum STATE  { 
		FREE, OCCUPIED, BLOCKED;
	};
	
	private int row;
	private int col;
	
	private boolean free;
	private boolean occupied;
	private boolean blocked;
	
	private String state;
	
	public static final String FREE = "free";
	public static final String BLOCKED = "blocked";
	public static final String OCCUPIED = "occupied";
	
	
	
	public Box(int r, int c) {
		super();
		this.row = r;
		this.col = c;
	}
	
	public Box(int r, int c, String status) {
		super();
		this.row = r;
		this.col = c;
		
		if(FREE.equalsIgnoreCase(status)) {
			this.setFree(true);
			this.setState("free");
		} else if(OCCUPIED.equalsIgnoreCase(status)) {
			this.setOccupied(true);
			this.setState("occupied");
		} else if(BLOCKED.equalsIgnoreCase(status)) {
			this.setBlocked(true);
			this.setState("blocked");
		} else { 
			System.out.print("ERR");
			this.setState("ERR");
		}			
	}

	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}

	public boolean isFree() {
		return free;
	}

	public void setFree(boolean f) {
		if (f==true) {
			this.setState(FREE);
			this.free = true;
			this.blocked = false;
			this.occupied = false;
		}
	}

	public boolean isOccupied() {
		return occupied;
	}

	public void setOccupied(boolean ocpd) {		
		if (ocpd==true) {
			this.setState(OCCUPIED);
			this.occupied = true;
			this.free = false;
			this.blocked = false;
		}
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blkd) {
		if(this.isOccupied()&&blkd==true) {
			setOccupied(blkd);
		} else {
			if (blkd==true) {
				this.setState(BLOCKED);
				this.blocked = true;
				this.free = false;
				this.occupied = false;
			}
		}			
		
	}

	@Override
	public String toString() {
		String box = null;
		if(this.isFree()) {
			box = " F |";
		} else if(this.isOccupied()) {
			box = " "+'\u2655'+" |";
		} else if(this.isBlocked()) {
			box = " B |";
		}		
		
		return box;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Cell transform2Cell() {
		Cell aCell = new Cell(row,col);
		if(this.isBlocked()) {
			aCell.setBlocked(true);
		} else if(this.isOccupied()) {
			aCell.setOccupied(true);
		}
		return aCell;
	}

}
