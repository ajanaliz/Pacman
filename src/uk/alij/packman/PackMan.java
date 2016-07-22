package uk.alij.packman;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import uk.alij.mainengine.Game;
import uk.alij.mainengine.GameApplication;
import uk.alij.mainengine.SpriteSheet;

import java.awt.Color;
public class PackMan extends Game {
	
	private int frame;
	private int requestedDirection;
	private GameData data;
	private SpriteSheet drawer;
	private GhostsCoach ghostsCoach;
	
	
	public PackMan(){
		data = new GameData();
		drawer = new SpriteSheet("images/packman_sheet.png", "images/packman_sheet.info");
		ghostsCoach = new GhostsCoach();
		title = "PackMan - Ali J.";
		width = data.getWidth();
		height = data.getHeight() + 50;//the + 50 will be for placing the score
		delay = 20;
	}
	
	@Override
	public void update() {
		if(!data.isDead()){
			frame++;
			//move the packman according to the requested direction
			data.movePackMan(requestedDirection);
			//move all the ghosts which will be  returning from ghostcoach which will decide which way the ghosts will go according to the data given to it
			if(frame%2 == 0){//we're giving advantage to the packman-->we're skipping 1 frame for the ghosts move,for every 2 moves the packman does the ghosts move once
				data.moveGhosts(ghostsCoach.decide(data));
			}
			//update the entire game info itself for example:is it time to remove the pills or weather the score should be updated or not
			data.update();
		}
	}

	@Override
	public void draw(Graphics2D g) {
		//clear the Canvas
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		//draw the maze
		drawer.draw(g,"mazes", data.getMazeNumber() < 4 ? data.getMazeNumber() : 3, 0, 0, false);
		//draw the pills
		for(Position pill:data.getPills()){
			drawer.draw(g, "pill" , 0, pill.getColumn() * 2, pill.getRow() * 2, true);
		}
		//draw the powerpills
		for(Position powerPill:data.getPowerPills()){
			drawer.draw(g, "powerpills", 0, powerPill.getColumn() * 2, powerPill.getRow() * 2, true);
		}
		//draw packman
		MoverInfo packman = data.getPackMan();
		drawer.draw(g, "packmans", packman.getCurrentDirection(), frame % 3,packman.getPosition().getColumn() * 2, packman.getPosition().getRow() * 2, true);
		//draw ghosts
		for(int i = 0;i<data.getGhostInfo().length;i++){
			GhostInfo gInfo = data.getGhostInfo()[i];
			if(gInfo.getEdibleCountDown() == 0){
				drawer.draw(g, "ghosts", i,gInfo.getCurrentDirection() + frame % 2, gInfo.getPosition().getColumn() * 2, gInfo.getPosition().getRow() * 2,true);
			}else{
				drawer.draw(g, "edibleghosts", frame % 2, gInfo.getPosition().getColumn() * 2, gInfo.getPosition().getRow() * 2,true);
			}
		}
		//draw score
		drawer.draw(g, "score", 0, 10, 510, false);
		String score = "" + data.getScore();
		for(int i = 0; i < score.length();i++){
			char c = score.charAt(score.length() - 1 - i);
			drawer.draw(g, "digits", c - '0', width - ( i * 20) - 20, 510 , false);
		}
		//draw game
		if(data.isDead()){
			g.setColor(new Color(100,100,100,200));//we're going to draw a transparent rectangle for the entire screen
			g.fillRect(0, 0, width, height);
			drawer.draw(g, "over", 0, width/2, height/2 - 50, true);//we're going to draw the gameover 
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(37 <= key && key <= 40){
			requestedDirection = key - 37;//we're going to keep it from the range of 0 to 3
		}//we only change direction if the user enters one of the arrow keys
	}
	public static void main(String[] args) {
		GameApplication.start(new PackMan());
	}
	// ====================================================== G A M E     C O N C E P T ==========================================================

	/*
	 * the concept of the game-->each time the packman wants to move(2 pieces at a time--->because STEP is 2)-->the whole canvas of the game is made of 2
	 * pixel based squares,this means at anytime of the game the packman would be at one of the squares on the grid,so what we really have to do in
	 * order to define were on the maze the packman is allowed to go on is-->we will have to define a maze,this maze can be anything it can be a table,a
	 * String,in this case,we'll make it a string of 1s and 0s-->1s stand for the grid the packman is allowed to walk on and 0s standing for the areas
	 * of the grid the packman is not allowed to move on I.E the walls of the maze*/

	// ===================================================== G A M E     A L G O R I T H M =========================================================

	/*
	 * if i make a txt file with for example 50 rows and 150 columns,that way by sorting the cells of this 50x150 matrix i can resemble a 100x300
	 * Screen(its multiplied by 2 because the packman moves 2 pixels at a time,meaning,each cell of our matrix can account for 2 pixels)*/

	/*
	 * other numbers we could add to our String containing the maps numeric resemblance are for example 2s,3s,4s and 5s each meaning a different
	 * attribute--->for example 4 is the initial position of the ghosts(their  spawning point) in our maze and 5 is the initial position of our packman
	 * the 2s will be for representing the pill's locations(the sort of food the packman gets) and the 3s will be representing the positions of the
	 * powerpills the packman eats and becomes strong and eats ghosts with*/
	
	// ============================================================ M O V E     L O G I C  ==============================================================
	
	/*our packman has a flaw,when its going on the map and we hit the up button and theres a wall on the row on top of it,the packman turns upwards towards
	 * the wall and stops moving-->what it should be like is this:when we're moving and we hit the up button the packman changes direction if and only if
	 * the route is open,otherwise the key pressed will be ignored entirely--->for handling this matter we need to keep track of two directions-->the first 
	 * being the requesting direction and the second being the current direction we're handling,at initialization of the game, the current direction and 
	 * the requested direction are one in the same,during the game if the user enters a key we need to see if we can go to the requested direction or not,
	 * if not we are going to fall back and use the current direction until the path in the requested direction becomes available-->then we switch the current
	 * direction to the requested direcetion*/

	// ============================================================ R E M O V I N G    P I L L S  ==============================================================
	
	/* we said that the keycode for the pills and powerpills in the maze would be 2s and 3s,as in whenever our drawer encounters a 2 in our maze it would draw a 
	 * pill on that poisition and whenever it sees a 3 it will draw a powerpill on that position-->whenever our packman moves on the 2 or 3 block we are going to 
	 * set the charAt that row and column to 1 so that the drawer will think that its just an ordinary path,hence it wont from now on draw any pills on that spot*/
	
/*
	private BufferedImage packman;
	private int frame;// number of the frames each procedure has
	private int requestedDirection,currentDirection;// which direction the packman is going in
	private int columnx, rowy;// the coordinates of our packman ----> x = column
								// and y = row
	private final int STEP;
	private ArrayList<String> lines;
	private int rows, columns;
	private BufferedImage[] mazeImages;
	private int mazeNumber;
	private Maze[] mazes = new Maze[4];
	private char[][] cells;

	public PackMan() {
		mazeNumber = 0;
		lines = new ArrayList<String>();
		mazeImages = new BufferedImage[4];
		title = "PackMan - Ali J.";
		requestedDirection = KeyEvent.VK_RIGHT;
		columnx = 300;
		rowy = 200;
		STEP = 2;
		try {
			packman = ImageIO.read(new File("images/packman.png"));
			for(int m = 0;m<4;m++){
				mazeImages[m] = ImageIO.read(new File("images/" + m + m + ".png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//load mazes information
		for(int m = 0;m<4;m++){
			mazes[m] = new Maze(m);
		}
		//update the information from mazes[mazeNumber]
		rows = mazes[mazeNumber].getRows();
		columns = mazes[mazeNumber].getColumns();
		//initial positions for packman
		rowy = mazes[mazeNumber].getRowy();
		columnx = mazes[mazeNumber].getColumnx();
		//size of the game screen
		width = mazes[mazeNumber].getWidth();
		height = mazes[mazeNumber].getHeight();
		//get copy of the cells
		cells = mazes[mazeNumber].getCells();//a copy of characters in current maze
	}

	@Override
	public void update() {
		frame++;
		if (frame > 5) {
			frame = 0;
		}
		if(move(requestedDirection) == SUCCESS){
			currentDirection = requestedDirection;
		}else{
			move(currentDirection);
		}
		//after we move we are going to update the pills
		if(cells[rowy][columnx] == '2'){
			//eat the pill
			cells[rowy][columnx] = '1';
		}else if(cells[rowy][columnx] == '3'){
			cells[rowy][columnx] = '1';
			delay = 15;
		}
	}

	private final int SUCCESS = 1,FAIL = 0;
	private int move(int Direction){
		//the current position of the packman is (row , column)
		switch (Direction) {
		case KeyEvent.VK_LEFT: // 37
			if(columnx > 0 && mazes[mazeNumber].charAt(rowy , columnx - 1) != '0'){
				columnx -= 1;
				return SUCCESS;
			}
			if(columnx == 0 && cells[rowy][columns -1] != '0'){
				columnx = columns - 1;
				return SUCCESS;
			}
			break;
		case KeyEvent.VK_RIGHT: // 39
			if(columnx < columns - 1 && mazes[mazeNumber].charAt(rowy , columnx + 1) != '0'){
				columnx += 1;
				return SUCCESS;
			}
			if(columnx == width && cells[rowy][1] != '0'){
				columnx = 1;
				return SUCCESS;
			}
			
			break;
		case KeyEvent.VK_UP: // 38
			if(rowy > 0 && mazes[mazeNumber].charAt(rowy - 1, columnx) != '0'){
				rowy -= 1;
				return SUCCESS;
			}
			break;
		case KeyEvent.VK_DOWN: // 40
			if(rowy < rows - 1 && mazes[mazeNumber].charAt(rowy + 1 , columnx) != '0'){
				rowy += 1;
				return SUCCESS;
			}
			break;
		}
		return FAIL;
	}
	


	@Override
	public void draw(Graphics2D g) {
		g.drawImage(mazeImages[mazeNumber], 0, 0, null);
		g.setColor(Color.WHITE);
		for( int r = 0; r < mazes[mazeNumber].getRows();r++){
			for(int c = 0;c<mazes[mazeNumber].getColumns();c++){
				if(cells[r][c] == '2'){
					//draw pill
					g.fillOval(c*STEP - 3, r*STEP - 3, 6, 6);
				}else if(cells[r][c] == '3'){
					//draw power pill
					g.fillOval(c*STEP - 6, r*STEP - 6, 12, 12);
				}
			}
		}
		g.drawImage(packman.getSubimage((frame / 2) * 30,(currentDirection - 37) * 30, 28, 28), columnx*STEP - 14, rowy*STEP - 14, null);//why (direction - 370??-->because the keycodes start from 37 and go up to 40,why (columnx*STEP - 14) or (rowx*STEP - 14)??-->because for drawing the packman we need to account for the fact that each step is for 2 pixels then we subtract that from half of the size of the packman
	}
*/
	
}
