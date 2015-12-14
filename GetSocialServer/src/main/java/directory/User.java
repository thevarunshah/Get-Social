package directory;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public String username;
	public String ip;
	public int port;
	
	public User(String username, String ip, int port) {
		this.username = username;
		this.ip = ip;
		this.port = port;
	}

}
