package uk.alij.mainengine;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Ali J on 3/7/2015.
 */
public class GameLoop extends Thread {

    private final Game game;
    private final GameCanvas canvas;
    private boolean stopped;
    private boolean paused;

    public GameLoop(Game game, GameCanvas canvas) {
        this.game = game;
        this.canvas = canvas;
        this.stopped = false;
        this.paused = false;
    }

    public void pauseGame() {
        this.paused = true;
    }

    public void resumeGame() {
        this.paused = false;
    }

    public void stopGame() {
        stopped = true;
    }

    @Override
    public void run() {
        game.init();

        while (!game.isOver() && !stopped) {

            if (!paused) {
                game.update();
                canvas.repaint();
            }

            try {
                Thread.sleep(game.getDelay());
            } catch (InterruptedException ex) {
                Logger.getLogger(GameLoop.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}