package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import directory.Directory;

@RestController
public class Controller {

	Directory directory;
	
	/**
	 * Constructor to initiate the directory from disk or create a blank one
	 */
	public Controller() {
		this.directory = Directory.get();
	}
    
	/**
	 * Register a client's username and ip to the directory
	 * @param username The username to register
	 * @param request
	 * @return String Username registered or already exists
	 */
    @RequestMapping("/register")
    public ResponseEntity<String> register(@RequestParam(value="username", required=true) String username, HttpServletRequest request){
    	
    	if(this.directory.isRegistered(username)){
    		return new ResponseEntity<String>("Username already exists.", HttpStatus.BAD_REQUEST);
    	}
    	this.directory.register(username, request.getRemoteAddr(), request.getRemotePort());
    	return new ResponseEntity<String>("Successfully registered!", HttpStatus.OK);
    }
    
    /**
     * Login a client and update their ip address
     * @param auth The username of the client to login
     * @param request
     * @return String Authentication successful/failed
     */
    @RequestMapping("/loginUser")
    public ResponseEntity<String> checkUser(@RequestParam(value="auth", required=true) String auth, @RequestParam(value="port", required=true) int port, HttpServletRequest request){
    	
    	if(this.directory.isRegistered(auth)){
    		this.directory.updateUser(auth, request.getRemoteAddr(), port);
    		return new ResponseEntity<String>("Authentication successful.", HttpStatus.OK);
    	}
    	
    	return new ResponseEntity<String>("Authentication failed.", HttpStatus.UNAUTHORIZED);
    }
    
    /**
     * Determines if a username is registered with the directory
     * @param auth The username of the client requesting the information
     * @param username The username to look up
     * @param request
     * @return String Username valid or invalid.
     */
    @RequestMapping("/isValidUser")
    public ResponseEntity<String> isValidUser(@RequestParam(value="auth", required=true) String auth, @RequestParam(value="username", required=true) String username, HttpServletRequest request){
    	
    	if(this.directory.isRegistered(auth)){
    		this.directory.updateUser(auth, request.getRemoteAddr());
	    	if(this.directory.isRegistered(username)) {
	    		return new ResponseEntity<String>("Username valid.", HttpStatus.OK);
	    	}
	    	return new ResponseEntity<String>("Username invalid.", HttpStatus.NOT_FOUND);
    	}
    	
    	return new ResponseEntity<String>("Authentication failed.", HttpStatus.UNAUTHORIZED);
    }
    
    /**
     * Gets a list of all usernames registered with the directory
     * @param auth The username of the client requesting the list
     * @param request
     * @return List<String> list of registered usernames
     */
    @RequestMapping("/getUsernames")
    public ResponseEntity<List<String>> getUsernames(@RequestParam(value="auth", required=true) String auth, HttpServletRequest request){
    	
    	if(this.directory.isRegistered(auth)){
    		this.directory.updateUser(auth, request.getRemoteAddr());
    		return new ResponseEntity<List<String>>(this.directory.registeredUsers, HttpStatus.OK);
    	}
    	
    	return new ResponseEntity("Authentication failed.", HttpStatus.UNAUTHORIZED);
    }
    
    /**
     * Retrieves the IP address for a user
     * @param auth The username of the client requesting the IP 
     * @param username The username to retrieve the IP of
     * @param request
     * @return String the IP address of the given username
     */
    @RequestMapping("/getUserIP")
    public ResponseEntity<String> getUserIP(@RequestParam(value="auth", required=true) String auth, 
    		@RequestParam(value="username", required=true) String username, HttpServletRequest request){
    	
    	if(this.directory.isRegistered(auth)){
    		
    		this.directory.updateUser(auth, request.getRemoteAddr());
    		
    		if(this.directory.isRegistered(username)){
    			return new ResponseEntity<String>(this.directory.getUserIP(username), HttpStatus.OK);
    		}
    		return new ResponseEntity<String>("Username not valid.", HttpStatus.BAD_REQUEST);
    	}
    	
    	return new ResponseEntity<String>("Authentication failed.", HttpStatus.UNAUTHORIZED);
    }
    
    
}
