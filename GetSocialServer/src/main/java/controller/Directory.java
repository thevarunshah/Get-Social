package controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Directory implements Serializable {

	private static final String SAVE_LOCATION = "data/directory.ser";
	private static final long serialVersionUID = 1L;
	
	List<String> registeredUsers = new ArrayList<String>();
	Map<String, String> userIPMap = new HashMap<String, String>();

	public Directory() {
		this.registeredUsers = new ArrayList<String>();
		this.userIPMap = new HashMap<String, String>();
	}

	public Directory(List<String> registeredUsers, Map<String, String> userIPMap) {
		this.registeredUsers = registeredUsers;
		this.userIPMap = userIPMap;
	}
	
	
	
	/*
	 * Directory access & update methods
	 */

	public boolean isRegistered(String username) {
		return this.registeredUsers.contains(username);
	}

	public void register(String username, String userIP) {
		registeredUsers.add(username);
		userIPMap.put(username, userIP);
		Directory.save(this);
	}

	public void updateUserIP(String username, String userIP) {
		userIPMap.put(username, userIP);
		Directory.save(this);
	}

	public String getUserIP(String username) {
		return userIPMap.get(username);
	}

	
	
	/*
	 * Serialization Methods (Saving/Loading from disk)
	 */

	public static Directory get() {
		Directory d = null;
		try {
			FileInputStream fileIn = new FileInputStream(
					Directory.SAVE_LOCATION);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			d = (Directory) in.readObject();
			in.close();
			fileIn.close();
			return d;
		} catch (ClassNotFoundException | IOException e) {
			return new Directory();
		}
	}

	public static void save(Directory d) {
		try {
			FileOutputStream fileOut = new FileOutputStream(Directory.SAVE_LOCATION);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(d);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

}
