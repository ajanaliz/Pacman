package uk.alij.mainengine;

import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import uk.alij.mainengine.config.Config;
import uk.alij.mainengine.config.KeyGetter;
import uk.alij.packman.PackMan;

/**
 * Created by Ali J on 3/7/2015.
 */
public class GameApplication {
    public static void start(final Game game) {
        SwingUtilities.invokeLater(new Runnable() {
        	@Override
            public void run() {
            	JFrame frame = new JFrame(game.getTitle());
            	try {
        			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        		} catch (ClassNotFoundException e) {
        			e.printStackTrace();
        		} catch (InstantiationException e) {
        			e.printStackTrace();
        		} catch (IllegalAccessException e) {
        			e.printStackTrace();
        		} catch (UnsupportedLookAndFeelException e) {
        			e.printStackTrace();
        		}
            	
            	frame.setLayout(null);
            	frame.setType(Type.NORMAL);
            	frame.setResizable(false);
                frame.setSize(game.getWidth(), game.getHeight() + 25);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                KeyGetter keyGetter = new KeyGetter();
                keyGetter.loadKeys();
                Config config = new Config();
                try {
					config.loadConfig();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
                JMenuBar bar = new JMenuBar();
                bar.setBounds(0,0,game.getWidth(),25);
                JMenu file = new JMenu("File");
                file.setBounds(0,0,45,24);
                JMenuItem exit = new JMenuItem("Exit");
                exit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Closing...");
                        System.exit(0);
                    }
                });
                JMenuItem newGame = new JMenuItem("New Game");
                newGame.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //Code for new Game
                        System.out.println("Starting New Game...");
                        newGame();
                    }
                });
                JMenuItem options = new JMenuItem("Options");
                options.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new Config().openConfig(frame);
                    System.out.println("Options");
                    }
                });
                final JMenuItem highscore = new JMenuItem("Highscore");
                highscore.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {//we need to make a menu pop up with the highscore on it
                        int Highscore = 0;//replace this with gethighscoremethod later
                        final JFrame alert = new JFrame("High Score");
                        alert.setSize(200,150);
                        alert.setLayout(null);
                        alert.setLocationRelativeTo(null);

                        JLabel Score = new JLabel("The High Score is: " + Highscore );
                        Score.setBounds(0,0,200,50);
                        JButton okaybutton = new JButton("Okay");
                        okaybutton.setBounds(50,80,100,30);
                        okaybutton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                alert.dispose();
                            }
                        });
                        alert.add(okaybutton);
                        alert.setResizable(false);
                        alert.setVisible(true);
                        alert.add(Score);
                    }
                });
                frame.add(bar);
                file.add(highscore);
                file.add(options);
                file.add(exit);
                file.add(newGame);
                bar.add(file);
                GameCanvas canvas = new GameCanvas();
                canvas.setBounds(0, 25, game.getWidth(), game.getHeight());
                canvas.setGame(game);
                frame.getContentPane().add(canvas);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                canvas.requestFocus();
                new GameLoop(game, canvas).start();
            }
        });
    }
    private static void newGame(){
    	start(new PackMan());
    }
}
