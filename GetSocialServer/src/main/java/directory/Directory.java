package directory;

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
	private static final long serialVersionUID = 2L;
	
	public List<String> registeredUsers = new ArrayList<String>();
	private Map<String, User> userMap = new HashMap<String, User>();

	public Directory() {
		this.registeredUsers = new ArrayList<String>();
		this.userMap = new HashMap<String, User>();
	}

	public Directory(List<String> registeredUsers, Map<String, User> userMap) {
		this.registeredUsers = registeredUsers;
		this.userMap = userMap;
	}
	
	
	
	/*
	 * Directory access & update methods
	 */

	public boolean isRegistered(String username) {
		return this.registeredUsers.contains(username);
	}

	public void register(String username, String userIP, int port) {
		registeredUsers.add(username);
		userMap.put(username, new User(username, userIP, port));
		Directory.save(this);
	}

	public void updateUser(String username, String userIP, int port) {
		User u = userMap.get(username);
		u.ip = userIP;
		u.port = port;
		Directory.save(this);
	}
	
	public void updateUser(String username, String userIP) {
		User u = userMap.get(username);
		u.ip = userIP;
		Directory.save(this);
	}

	public String getUserIP(String username) {
		User u = userMap.get(username);
		return u.ip + ":" + u.port;
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
