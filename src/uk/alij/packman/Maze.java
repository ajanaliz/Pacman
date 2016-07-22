package uk.alij.packman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Maze {
	
	private ArrayList<String> lines;
//	private int rowy,columnx;
	private int rows,columns;
	private int width , height;
	private Position packManPosition,ghostPosition;
	private ArrayList<Position> pills, powerPills;

	public Maze(int m) {
		lines = new ArrayList<String>();
		pills = new ArrayList<Position>();
		powerPills = new ArrayList<Position>();
		loadLines(m);
	}
	
	private void loadLines(int m) {//in this function we read the maps resemblance from a file,initialize the packmans and the ghosts starting point in the maze
		File myDir = new File("mazes/" + m + ".txt");
		BufferedReader reader;

		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(myDir)));
			try {
				int r = 0;
				String read = null;
				while (true) {
					read = reader.readLine();
					if(read == null){
						break;
					}
					if (read.isEmpty())
						continue;
					lines.add(read);
					if(read.contains("4")){
						setGhostPosition(new Position(r , read.indexOf('4')));
					}
					if(read.contains("5")){//set the initial position for packman
						packManPosition = new Position(r, read.indexOf('5'));
					}
					for(int i = 0; i <read.length();i++){
						if(read.charAt(i) == '2'){//we have a pill
							pills.add(new Position(r,i));
						}else if(read.charAt(i) == '3'){
							powerPills.add(new Position(r,i));
						}
					}
					r++;
				}
				
				rows = lines.size();
				columns = lines.get(0).length();
				
				width = columns * 2;
				height = rows * 2;
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
	
	public char charAt(int row, int column){
		return lines.get(row).charAt(column);
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public char[][] getCells() {
		char[][] cells = new char[rows][columns];
		for(int r = 0; r<rows;r++){
			System.arraycopy(lines.get(r).toCharArray(), 0, cells[r], 0, columns);
		}
		return cells;
	}

	public Position getPackManPosition() {
		return packManPosition;
	}

	public void setPackManPosition(Position packManPosition) {
		this.packManPosition = packManPosition;
	}

	public Position getGhostPosition() {
		return ghostPosition;
	}

	public void setGhostPosition(Position ghostPosition) {
		this.ghostPosition = ghostPosition;
	}

	public ArrayList<Position> getPills() {
		return pills;
	}

	public void setPills(ArrayList<Position> pills) {
		this.pills = pills;
	}

	public ArrayList<Position> getPowerPills() {
		return powerPills;
	}

	public void setPowerPills(ArrayList<Position> powerPills) {
		this.powerPills = powerPills;
	}

}
