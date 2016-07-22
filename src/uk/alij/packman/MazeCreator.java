package uk.alij.packman;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class MazeCreator {
	
	public static void main(String[] args) {
		for(int m = 0;m<4;m++){
			//load in the maze strings
			ArrayList<String> lines = new ArrayList<String>();
			File myDir = new File("mazes/" + m + ".txt");
			BufferedReader reader;
			int rows = 0;
			int columns = 0;
			int width = 0;//each character on the txt file represents 2 pixels
			int height = 0;
			try {
				reader = new BufferedReader(new InputStreamReader(
						new FileInputStream(myDir)));
				try {
					String read = null;
					while (true) {
						read = reader.readLine();
						if(read == null){
							break;
						}
						if (read.isEmpty())
							continue;
						lines.add(read);
					}
					rows = lines.size();
					columns = lines.get(0).length();
					width = columns * 2;//each character on the txt file represents 2 pixels
					height = rows * 2;
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			//draw maze on the image
			//to draw it we're going to get the graphics from the image and try to draw it using the methods in Graphics
			BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			//for drawing the maze we go cell by cell
			for(int r = 0; r< rows; r++){
				for(int c = 0;c< columns;c++){
					if(lines.get(r).charAt(c) != '0'){
						g.fillRect(c*2-14, r*2-14, 28, 28);
					}
				}
			}
			g.dispose();
			//save the image
			try {
				ImageIO.write(image, "png", new File("images/" + m +".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
