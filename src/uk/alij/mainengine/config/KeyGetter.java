package uk.alij.mainengine.config;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;

public class KeyGetter {
	private ArrayList<String> keyNames;
	private HashMap<String, Integer> keys;
	private Field[] fields;
	public KeyGetter(){
		keys = new HashMap<String,Integer>();
		keyNames = new ArrayList<String>();
		fields = KeyEvent.class.getFields();
	}
	
	public void loadKeys(){
		for(Field fields:fields){
			if(Modifier.isStatic(fields.getModifiers()))
				if(fields.getName().startsWith("VK"))
					try{
						int num = fields.getInt(null);
						String name = KeyEvent.getKeyText(num);
						keys.put(name, num);
						keyNames.add(name);
						System.out.println(name);//for debuging
					}catch(IllegalAccessException e){
						e.printStackTrace();
					}
		}
	}

	public ArrayList<String> getKeyNames() {
		return keyNames;
	}

	public void setKeyNames(ArrayList<String> keyNames) {
		this.keyNames = keyNames;
	}

	public HashMap<String, Integer> getKeys() {
		return keys;
	}

	public void setKeys(HashMap<String, Integer> keys) {
		this.keys = keys;
	}

	public Field[] getFields() {
		return fields;
	}

	public void setFields(Field[] fields) {
		this.fields = fields;
	}

}
