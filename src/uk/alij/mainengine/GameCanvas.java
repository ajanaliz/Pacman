package uk.alij.mainengine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * Created by Ali J on 3/7/2015.
 */
public class GameCanvas extends JComponent implements ComponentListener {

    private Game game;

    public GameCanvas() {
    }

    public GameCanvas(Game game) {
        this.game = game;
        addKeyListener(game);
        addMouseListener(game);
        addMouseMotionListener(game);
        addMouseWheelListener(game);
        requestFocus();
        addComponentListener(this);
    }

    public void setGame(Game game) {
        this.game = game;
        addKeyListener(game);
        addMouseListener(game);
        addMouseMotionListener(game);
        requestFocus();
        addComponentListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        game.draw((Graphics2D)g);
    }

    @Override
    public void componentResized(ComponentEvent ce) {
        game.resize(ce.getComponent().getWidth(), ce.getComponent().getHeight());
    }

    @Override
    public void componentMoved(ComponentEvent ce) {

    }

    @Override
    public void componentShown(ComponentEvent ce) {
//        game.resize(ce.getComponent().getWidth(), ce.getComponent().getHeight());
    }

    @Override
    public void componentHidden(ComponentEvent ce) {

    }

}