package uk.alij.mainengine;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class SpriteSheet {

	BufferedImage image;
	HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();

	public SpriteSheet(String imageName, String infoName) {
		try {
			image = ImageIO.read(new File(imageName));
			Scanner s = new Scanner(new File(infoName));
			while (s.hasNextLine()) {
				String line = s.nextLine().trim();
				if (!line.startsWith("#") && !line.trim().isEmpty()) {
					Sprite sprite = new Sprite(line.split("#")[0]);
//					System.out.println(sprite.name);
					sprites.put(sprite.name, sprite);
				}
			}
			s.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
//		System.out.println(sprites.keySet());
	}
	
	public Sprite getSprite(String name) {
		return sprites.get(name);
	}
	
	public void draw(Graphics g, String name, int row, int column, int x, int y, boolean centered) {
		Region r = getSprite(name).getRegion(row, column);
		if (centered) {
			g.drawImage(image.getSubimage(r.x, r.y, r.w, r.h), x-r.w/2, y-r.h/2, null);
		} else {
			g.drawImage(image.getSubimage(r.x, r.y, r.w, r.h), x, y, null);
		}
	}
	
	public void draw(Graphics g, String name, int index, int x, int y, boolean centered) {
		Region r = getSprite(name).getRegion(index);
		if (centered) {
			g.drawImage(image.getSubimage(r.x, r.y, r.w, r.h), x-r.w/2, y-r.h/2, null);
		} else {
			g.drawImage(image.getSubimage(r.x, r.y, r.w, r.h), x, y, null);
		}
	}
}

