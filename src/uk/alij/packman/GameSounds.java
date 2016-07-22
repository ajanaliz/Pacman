package uk.alij.packman;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class GameSounds {

	private MakeSound sounds;
    private boolean stopped;
    public GameSounds(){
    	sounds = new MakeSound();
        stopped=true;
    }
    
    
    /* Play new game sound */
    public void newGame(){
    		sounds.playSound("sounds/newGame.wav");
    }
    
    /* Play packman death sound */
    public void death(){
    	sounds.playSound("sounds/death.wav");
    }
    
    /* Play pacman eating sound */
    public void nomNom(){
        /* If it's already playing, don't start it playing again!*/
        if (!stopped)
          return;

        stopped=false;
        sounds.playSound("sounds/nomnom.wav");
    }

    /* Stop pacman eating sound */
    public void nomNomStop(){
        stopped=true;
        sounds.stopSound();
    }
}
