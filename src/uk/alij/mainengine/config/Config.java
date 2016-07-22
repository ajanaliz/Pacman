package uk.alij.mainengine.config;

import java.awt.Choice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Config {//this is for costomizing the controls your program runs on
	private String left,right,up,down,pause;
	private ArrayList<Choice> choices;
	private final JFrame options = new JFrame("Options");
	private KeyGetter keyGetter;
	
	
	
	public Config(){
		left = "Left";
		right = "Right";
		up = "Up";
		down = "Down";
		pause = "Pause";
		keyGetter = new KeyGetter();
	}
	
	public void openConfig(JFrame frame){
		options.setSize(400,300);
        options.setResizable(false);
        options.setLocationRelativeTo(frame);
        options.setLayout(null);
        choices = new ArrayList<Choice>();
        Choice left = addChoice("Left", options, 30, 30);
		left.select(getLeft());
		Choice right = addChoice("Right", options, 150, 30);
		right.select(getRight());
		Choice up = addChoice("Up", options, 30, 80);
		up.select(getUp());
		Choice down = addChoice("Down", options, 150, 80);
		down.select(getDown());
		Choice pause = addChoice("Pause", options, 30, 130);
		pause.select(getPause());
		JButton done = new JButton("Done");
		done.setBounds(150,220,100,30);
		done.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				options.dispose();
				saveChanges();
				
			}
		});
		options.add(done);
		options.setVisible(true);
	}
	private Choice addChoice(String name,JFrame options,int x,int y){
		JLabel label = new JLabel(name);
		label.setBounds(x,y - 20, 100,20);
		Choice key = new Choice();
		for(String keyNames : getKeyNames()){
			key.add(keyNames);
		}
		key.setBounds(x,y,100,20);
		options.add(key);
		options.add(label);
		choices.add(key);
		return key;
	}
	private ArrayList<String> getKeyNames(){
		ArrayList<String> result = new ArrayList<String>();
		for(String keyNames: keyGetter.getKeyNames() ){
			result.add(keyNames);
			if (keyNames.equalsIgnoreCase("F24")) {//we dont need the rest
				break;
			}
		}
		return result;
	}
	
	private void saveChanges(){
		Choice left = choices.get(0);
		Choice right = choices.get(1);
		Choice up = choices.get(2);
		Choice down = choices.get(3);
		Choice pause = choices.get(4);
		setLeft(left.getSelectedItem());
		setRight(right.getSelectedItem());
		setUp(up.getSelectedItem());
		setDown(down.getSelectedItem());
		setPause(pause.getSelectedItem());
		saveConfig();
	}
	
	private void saveConfig(){
		File directory = new File(getDefaultDirectory(), "/Gamename");
		if(!directory.exists()){
			directory.mkdirs();
		}
		File Config = new File(directory , "/Config");
		try {
			PrintWriter pw = new PrintWriter(Config);
			pw.println("right:" + right);
			pw.println("left:" + left);
			pw.println("up:" + up);
			pw.println("down:" + down);
			pw.println("pause:" + pause);
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private String getDefaultDirectory(){
		String OS = System.getProperty("os.name").toUpperCase();
		if( OS.contains("WIN")){
			return System.getenv("APPDATA");
		}
		if(OS.contains("MAC")){
			return System.getProperty("user.home") + "Library/Application Support/";
		}
		return System.getProperty("user.home");
	}
	
	public void loadConfig() throws IOException{
		File directory = new File(getDefaultDirectory() , "/Gamename");
		if (!directory.exists()) {
			directory.mkdirs();
		}
		File config = new File(directory, "/Config");
		if (!config.exists()) {
			config.createNewFile();
			saveConfig();
			return;
		}
		Scanner s = new Scanner(config);
		HashMap<String, String> values = new HashMap<String,String>();
		while(s.hasNextLine()){
			String[] entry = s.nextLine().split(":");
			String key = entry[0];
			String value = entry[1];
			values.put(key, value);
		}
		if (values.size() != 5) {
			System.err.println("Config is unsusable,saving defaults");
			saveConfig();
			return;
		}
		if (!values.containsKey("left") || !values.containsKey("right") || !values.containsKey("down") || !values.containsKey("up") || !values.containsKey("pause")) {
			System.err.println("Invalid names in Config,Saving Defaults");
			saveConfig();
			return;
		}
		String left = values.get("left");
		String right = values.get("right");
		String up = values.get("up");
		String down = values.get("down");
		String pause = values.get("pause");
		
		if(!(getKeyNames().contains(left) && getKeyNames().contains(right) 
				&& getKeyNames().contains(up) && getKeyNames().contains(down)
				&& getKeyNames().contains(pause))){
			System.err.println("Invalid names in Config,Saving Defaults");
			return;
		}
		
		setLeft(left);
		setRight(right);
		setDown(down);
		setUp(up);
		setPause(pause);
	}


	public String getLeft() {
		return left;
	}


	public void setLeft(String left) {
		this.left = left;
	}


	public String getRight() {
		return right;
	}


	public void setRight(String right) {
		this.right = right;
	}


	public String getUp() {
		return up;
	}


	public void setUp(String up) {
		this.up = up;
	}


	public String getDown() {
		return down;
	}


	public void setDown(String down) {
		this.down = down;
	}


	public String getPause() {
		return pause;
	}


	public void setPause(String pause) {
		this.pause = pause;
	}

}
