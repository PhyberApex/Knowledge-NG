package de.knowhow.base;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class Config {
	
	private Properties prop;
	
	public Config(){
		FileReader reader = null;
		try {
			reader = new FileReader("knowledge.cfg");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.prop = new Properties();
		try {
			this.prop.load(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void setProperty(String key, String value){
		prop.setProperty(key, value);
	}
	public String getProperty(String key){
		return this.prop.getProperty(key);
	}
	
	public void saveChanges(){
		FileWriter writer = null;
		try {
			writer = new FileWriter("knowledge.cfg");
			this.prop.store(writer, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
