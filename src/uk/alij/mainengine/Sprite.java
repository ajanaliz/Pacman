package uk.alij.mainengine;

import java.util.ArrayList;
import static java.lang.Integer.parseInt;

public class Sprite {

	public String name;
	public String type;
	public int size;
	public int rows;
	public int columns;
	public ArrayList<Region> regions;

	public Sprite() {
	}

	public Sprite(String line) {
		String[] a = line.split("\\s+");
		int i = 0;
		name = a[i++];
		type = a[i++].toUpperCase();
		regions = new ArrayList<Region>();
		switch (type.charAt(0)) {
		case '0':
			for (; i < a.length; i++) {
				regions.add(new Region(a[i].split(",")));
			}
			size = regions.size();
			break;
		case '1': // 1D
			char dir = a[i++].trim().toUpperCase().charAt(0);
			size = parseInt(a[i++]);
			int gap = parseInt(a[i++]);
			Region r = new Region(a[i++].trim().split(","));
			regions.add(r);
			if (dir == 'V') {
				for (int j = 1; j < size; j++) {
					regions.add(new Region(r.x, r.y + j * (r.h + gap), r.w, r.h));
				}
			} else { // 'H'
				for (int j = 1; j < size; j++) {
					regions.add(new Region(r.x + j * (r.w + gap), r.y, r.w, r.h));
				}
			}
			break;
		case '2': // 2D
			String[] da = a[i++].split("x");
			rows = parseInt(da[0]);
			columns = parseInt(da[1]);
			int rowgap = parseInt(a[i++]);
			int columngap = parseInt(a[i++]);
			r = new Region(a[i++].split(","));
			for (int rr=0; rr<rows; rr++) {
				for (int cc=0; cc<columns; cc++) {
					regions.add(
							new Region(
									r.x+cc*(r.w+columngap),
									r.y+rr*(r.h+rowgap),
									r.w, r.h
								)
							);
				}
			}
			break;
		}
	}
	
	public Region getRegion(int row, int column) {
		if (type.startsWith("2")) {
			return regions.get(row*columns+column);
		}
		return getRegion(row);
	}
	
	public Region getRegion(int index) {
		return regions.get(index);
	}
}

