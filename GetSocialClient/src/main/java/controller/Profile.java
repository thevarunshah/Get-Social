package controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Profile implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static final String SAVE_LOCATION = "data/";
	private static final String SAVE_EXT = ".ser";
	
	private List<String> updates;
	private String username;
	
	public Profile(String username) {
		updates = new ArrayList<String>();
		this.username = username;
	}
	
	public Profile(String username, List<String> updates) {
		this.username = username;
		this.updates = updates;
	}
	
	public List<String> getUpdates() {
		return this.updates;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void post(String message) {
		this.updates.add(message);
		Profile.save(this);
	}
	
	
	
	/*
	 * Serialization Methods (Saving/Loading from disk)
	 */

	public static Profile get(String username) {
		System.out.println("Getting profile for user " + username);
		Profile p = null;
		try {
			FileInputStream fileIn = new FileInputStream(
					Profile.SAVE_LOCATION + username + Profile.SAVE_EXT);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			p = (Profile) in.readObject();
			in.close();
			fileIn.close();
			return p;
		} catch (ClassNotFoundException | IOException e) {
			return new Profile(username);
		}
	}

	public static void save(Profile p) {
		System.out.println("Saving user " + p.getUsername());
		try {
			FileOutputStream fileOut = new FileOutputStream(Profile.SAVE_LOCATION + p.getUsername() + Profile.SAVE_EXT);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(p);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

}
