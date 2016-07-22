package uk.alij.mainengine;

import static java.lang.Integer.parseInt;

public class Region {
	
	public int x, y, w, h;
	
	public Region() {}

	public Region(int x, int y, int w, int h) {
		super();
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public Region(String[] array) {
		x = parseInt(array[0]);
		y = parseInt(array[1]);
		w = parseInt(array[2]);
		h = parseInt(array[3]);
	}
	
	@Override
	public String toString() {
		return String.format("(%d,%d,%d,%d)", x, y, w, h);
	}
}

