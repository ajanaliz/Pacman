package uk.alij.packman;

import java.util.List;
import java.util.Random;

public class GhostsCoach {

	Random random;
	
	public GhostsCoach(){
		random = new Random();
	}
	
	public int[] decide(GameData data) {//this will return an integer array containing 4 numbers specifying the direction for each of the ghosts-->on default it will return 4 number 0s meaning the left direction--->37-37=0
		int[] directions = new int[4];
		for(int i = 0; i < 4; i++){//this is going to traverse the ghosts and get all their possible directions and adds them all to a list
		//,then its going to remove the reversed form of their current directions from the list,and then choose one of the available 
		//directions from the list-->this way the ghosts wont jump back and forth making the reversed direction of their current directions 
		//making them look dumb and stupid
			List<Integer> list = data.getPossibleDirections(data.getGhostInfo()[i].getPosition());
//			System.out.println(data.getGhostInfo()[i].getCurrentDirection());//for debugging
			list.remove(new Integer(new MoverInfo(data.getGhostInfo()[i].getPosition()).getReversePosition()[data.getGhostInfo()[i].getCurrentDirection()]));
			directions[i] = list.get(random.nextInt(list.size()));
		}
		return directions;
//		return new int[]{
//				random.nextInt(4),
//				random.nextInt(4),
//				random.nextInt(4),
//				random.nextInt(4)
//		};
	}

}
