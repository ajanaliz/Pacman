package uk.alij.packman;

public class GhostInfo extends MoverInfo{
	
	private int edibleCountDown;

	public GhostInfo(Position ghostPosition) {
		super(ghostPosition);
		edibleCountDown = 0;
	}
	
	public int getEdibleCountDown() {
		return edibleCountDown;
	}

	public void setEdibleCountDown(int edibleCountDown) {
		this.edibleCountDown = edibleCountDown;
	}

}
