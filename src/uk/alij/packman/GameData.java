package uk.alij.packman;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import uk.alij.mainengine.GameApplication;
import uk.alij.mainengine.GameLoop;

public class GameData {

	private int mazeNumber;
	private CopyOnWriteArrayList<Position> pills,powerPills;
	private MoverInfo packMan;
	private GhostInfo[] ghostInfos;
	private int score;
	private Maze[] mazes;
	private boolean dead;
	private GameSounds sounds;
	private Thread sound;
	
	public GameData(){
		sounds = new GameSounds();
		sound = new Thread("Sound"){
			public void run(){
				sounds.newGame();
			}
		};
		sound.start();
		dead = false;
		ghostInfos = new GhostInfo[4];
		mazes = new Maze[4];
		//first thing we're going to do is load all the mazes
		loadMazes();
	}

	private void loadMazes() {
		for(int m = 0;m<4;m++){
			mazes[m] = new Maze(m);
		}
//		mazeNumber = 3;
		setMaze(mazeNumber);
		
	}

	private void setMaze(int m) {
		packMan = new MoverInfo(mazes[m].getPackManPosition());
		for (int g = 0;g < 4;g++){
			ghostInfos[g] = new GhostInfo(mazes[m].getGhostPosition());
		}
		pills = new CopyOnWriteArrayList((List<Position>)(mazes[m].getPills().clone()));
		powerPills = new CopyOnWriteArrayList((List<Position>)(mazes[m].getPowerPills().clone()));
	}

	public void update() {
		/*for removing the pills,we are going to check if the packman's position is in the pills then we'll remove that pill*/
		
		if(pills.contains(packMan.getPosition())){
			pills.remove(packMan.getPosition());
			sound = new Thread("Sound"){
				public void run(){
					sounds.nomNom();
					sounds.nomNomStop();
				}
			};
			sound.start();
			//also whenever the packman eats the pills,we're going to increment the score by 5
			score += 5;
		}else if(powerPills.contains(packMan.getPosition())){
			//if the packman ate a powerpill,make eating sound
			sound = new Thread("Sound"){
				public void run(){
					sounds.nomNom();
					sounds.nomNomStop();
				}
			};
			sound.start();
			powerPills.remove(packMan.getPosition());
			//whenever the packman eats a powerpill we're going to increment the score by 50
			score += 50;
			for(GhostInfo ghosts: ghostInfos){//everytime the packman eats a powerpill we need to increment the ediblecountdown of the ghosts meaning in this time period they can be eaten by the packman
				ghosts.setEdibleCountDown(500);
			}
		}/*NOTE: for the contain and remove methods to work properly we need to make each object of the position unique-->to do this,we're
		going to generate the hashCode() and equals() methods for the Position class*/
		for(GhostInfo ghost: ghostInfos){
			if(ghost.getEdibleCountDown() > 0){//if the ghost can be eaten,then we will check for the position,to see if its collided with the packman or not
				if(touching(ghost.getPosition(),packMan.getPosition())){
					//make eating sound
					sound = new Thread("Sound"){
						public void run(){
							sounds.nomNom();
							sounds.nomNomStop();
						}
					};
					sound.start();
					//eat the ghost and reset
					score += 100;
					//reset the ghost position
					ghost.setCurrentDirection(ghost.getRequestedDirection() - (new MoverInfo(new Position(0, 0)).getLEFT()));
					ghost.getPosition().setRow(mazes[mazeNumber].getGhostPosition().getRow());
					ghost.getPosition().setColumn(mazes[mazeNumber].getGhostPosition().getColumn());
					ghost.setEdibleCountDown(1);
//					ghost.setPosition(mazes[mazeNumber].getGhostPosition());
				}
				ghost.setEdibleCountDown(ghost.getEdibleCountDown() - 1);
			}else{
				if(touching(ghost.getPosition(),packMan.getPosition())){//if the edible countdown is zero and the packman touches the ghost then the packman dies
					dead = true;
					sound = new Thread("Sound"){
						public void run(){
							sounds.nomNomStop();
							sounds.death();
						}
					};
					sound.start();
					
				}
			}
		}
		if(pills.isEmpty() && powerPills.isEmpty()){//we're checking if the level is clear
			mazeNumber++;
			if(mazeNumber < 4){
				setMaze(mazeNumber);
			}else{
				//the game is over
				dead = true;
			}
		}
	}

	private boolean touching(Position ghostsPosition, Position packMansPosition) {
		//if the packman and the ghost are 4 pixels apart then they should be touching so if i calculate the difference they have in rows and columns and sum those up,if the answer is less than 3 then they're touching-->either vertically or horizantally-->meaning if the difference in either angle is less than 4 pixels,they're touching
		return ((Math.abs(ghostsPosition.getRow() - packMansPosition.getRow()) + Math.abs(ghostsPosition.getColumn() - packMansPosition.getColumn())) < 3);
	}

	public void movePackMan(int requestedDirection) {
		if(move(requestedDirection,packMan)){
			packMan.setCurrentDirection(requestedDirection);
		}else{
			move(packMan.getCurrentDirection(),packMan);
		}
	}
	
	public void moveGhosts(int[] decide) {
//		for(GhostInfo ghost:ghostInfos){
		for(int i = 0;i < 4; i++){
			GhostInfo ghost = ghostInfos[i];
			ghost.setRequestedDirection(decide[i]);
			if(move(ghost.getRequestedDirection(),ghost)){
				ghost.setCurrentDirection(ghost.getRequestedDirection());
			}else{
				move(ghost.getCurrentDirection(),ghost);
			}
		}
	}
	
	public List<Integer> getPossibleDirections(Position position) {
		List<Integer> list = new ArrayList<Integer>();
		for(int d = 0;d<4;d++){//for each of the directions
			Position npos = getNextPositionInDir(position, d);
			if(mazes[mazeNumber].charAt(npos.getRow(), npos.getColumn()) != '0'){//we're going to check if the position at that direction is not zero
				list.add(d);
			}
		}
		return list;
	}
	
	
	
	private Position getNextPositionInDir(Position position, int d) {
		int newRow = wrap(position.getRow(), new MoverInfo(new Position(0, 0)).getdROW()[d], mazes[mazeNumber].getRows());
		int newCol = wrap(position.getColumn(), new MoverInfo(new Position(0, 0)).getdCOL()[d] , mazes[mazeNumber].getColumns());
		return new Position(newRow,newCol);
	}

	private int wrap(int value,int incrementingValue,int max){
		return (value + max + incrementingValue) % max;
	}
	private boolean move(int direction, MoverInfo entity){
		//the current position of the packman is (row , column)
		int rowy = entity.getPosition().getRow();
		int columnx = entity.getPosition().getColumn();
		int rows = mazes[mazeNumber].getRows();
		int columns = mazes[mazeNumber].getColumns();
		int newRow = wrap(rowy,entity.getdROW()[direction],rows);
		int newColumn = wrap(columnx , entity.getdCOL()[direction],columns);
		if(mazes[mazeNumber].charAt(newRow, newColumn) != '0'){
			entity.setPosition(new Position(newRow, newColumn));
			return true;
		}
		return false;
	}
	
	public int getWidth() {
		return mazes[mazeNumber].getWidth();
	}

	public int getHeight() {
		return mazes[mazeNumber].getHeight();
	}
	
	public int getMazeNumber() {
		return mazeNumber;
	}

	public void setMazeNumber(int mazeNumber) {
		this.mazeNumber = mazeNumber;
	}
	
	public CopyOnWriteArrayList<Position> getPills() {
		return pills;
	}

	public void setPills(CopyOnWriteArrayList<Position> pills) {
		this.pills = pills;
	}

	public CopyOnWriteArrayList<Position> getPowerPills() {
		return powerPills;
	}

	public void setPowerPills(CopyOnWriteArrayList<Position> powerPills) {
		this.powerPills = powerPills;
	}

	public MoverInfo getPackMan() {
		return packMan;
	}

	public void setPackMan(MoverInfo packMan) {
		this.packMan = packMan;
	}

	public GhostInfo[] getGhostInfo() {
		return ghostInfos;
	}

	public void setGhostInfo(GhostInfo[] ghostInfo) {
		this.ghostInfos= ghostInfo;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}




//	private boolean move(int direction, MoverInfo entity){
//		//the current position of the packman is (row , column)
//		int rowy = entity.getPosition().getRow();
//		int columnx = entity.getPosition().getColumn();
//		int rows = mazes[mazeNumber].getRows();
//		int columns = mazes[mazeNumber].getColumns();
//		switch (direction) {
//		case LEFT: // 37
//			if(columnx > 0 && mazes[mazeNumber].charAt(rowy , columnx - 1) != '0'){
//				entity.setPosition(new Position(rowy, columnx - 1));
//				return true;
//			}
//			if(columnx == 0 && mazes[mazeNumber].charAt(rowy, columns - 1) != '0'){
//				entity.setPosition(new Position(rowy, columns - 1));
//				return true;
//			}
//			break;
//		case RIGHT: // 39
//			if(columnx < columns - 1 && mazes[mazeNumber].charAt(rowy , columnx + 1) != '0'){
//				entity.setPosition(new Position(rowy, columnx + 1));
//				return true;
//			}
//			break;
//		case UP: // 38
//			if(rowy > 0 && mazes[mazeNumber].charAt(rowy - 1, columnx) != '0'){
//				entity.setPosition(new Position(rowy - 1, columnx));
//				return true;
//			}
//			break;
//		case DOWN: // 40
//			if(rowy < rows - 1 && mazes[mazeNumber].charAt(rowy + 1 , columnx) != '0'){
//				entity.setPosition(new Position(rowy + 1, columnx));
//				return true;
//			}
//			break;
//		}
//		return false;
//	}
}
