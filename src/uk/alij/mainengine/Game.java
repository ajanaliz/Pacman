package uk.alij.mainengine;

import java.awt.*;
import java.awt.event.*;

/**
 * Created by Ali J on 3/7/2015.
 */
public abstract class Game implements KeyListener,MouseListener,MouseMotionListener, MouseWheelListener {

    protected boolean over;
    protected int width=400, height=300;
    protected int delay = 30;
    protected String title = "My Game";

    public void init() {}
    abstract public void update();
    abstract public void draw(Graphics2D g);


    public boolean isOver() { return over; }
    public String getTitle() { return title; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getDelay() { return delay; }
    public void resize(int width, int height) {}

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseWheelMoved(MouseWheelEvent e){
    }

}
