package uk.alij.packman;

public class MoverInfo {

	private int currentDirection,requestedDirection;
	private Position position;
	private final int LEFT = 0,UP = 1, RIGHT = 2,DOWN = 3;
	private final int[] dROW = { 0 , -1 , 0 , 1};/*dROW or the changes in row stands for the changes occuring acording to the direction the 
	entity is moving in,while moving left and right the rows dont change but when we're moving up the rows index number decreases and vice verca
	for when we're moving downward*/
	private final int[] dCOL = { -1, 0 , 1 , 0 };/*dCOL or the changes in column stands for the changes that happen while the entity is moving
	column-wise*/
	private final int[] reverseDirection = {RIGHT , DOWN, LEFT, UP}; 
	
	public MoverInfo(Position position) {
		this.position = new Position(position.getRow(), position.getColumn());
		
	}
	public int getCurrentDirection() {
		return currentDirection;
	}
	public void setCurrentDirection(int currentDirection) {
		this.currentDirection = currentDirection;
	}
	public int getRequestedDirection() {
		return requestedDirection;
	}
	public void setRequestedDirection(int requestedDirection) {
		this.requestedDirection = requestedDirection;
	}
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	public int getLEFT() {
		return LEFT;
	}
	public int getUP() {
		return UP;
	}
	public int getRIGHT() {
		return RIGHT;
	}
	public int getDOWN() {
		return DOWN;
	}
	public int[] getdROW() {
		return dROW;
	}
	public int[] getdCOL() {
		return dCOL;
	}
	public int[] getReversePosition() {
		return reverseDirection;
	}
}
