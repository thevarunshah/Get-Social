package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	Directory directory;
	
	public Controller() {
		this.directory = Directory.get();
	}
    
    @RequestMapping("/register")
    public ResponseEntity<String> register(@RequestParam(value="username", required=true) String username, HttpServletRequest request){
    	
    	String userIP = request.getRemoteAddr();
    	if(this.directory.isRegistered(username)){
    		return new ResponseEntity<String>("Username already exists.", HttpStatus.BAD_REQUEST);
    	}
    	this.directory.register(username, userIP);
    	return new ResponseEntity<String>("Successfully registered!", HttpStatus.OK);
    }
    
    @RequestMapping("/isValidUser")
    public ResponseEntity<String> isValidUser(@RequestParam(value="username", required=true) String username, HttpServletRequest request){
    	
    	if(this.directory.isRegistered(username)) {
    		return new ResponseEntity<String>("Username valid.", HttpStatus.OK);
    	}
    	
    	return new ResponseEntity<String>("Could not find username.", HttpStatus.BAD_REQUEST);
    }
    
    @RequestMapping("/checkUser")
    public ResponseEntity<String> checkUser(@RequestParam(value="username", required=true) String username, HttpServletRequest request){
    	
    	if(this.directory.isRegistered(username)){
    		/** TODO
    		 * Probably shouldn't be updating the user IP here
    		 */
    		String userIP = request.getRemoteAddr();
    		this.directory.updateUserIP(username, userIP);
    		return new ResponseEntity<String>("Username valid.", HttpStatus.OK);
    	}
    	
    	return new ResponseEntity<String>("Could not find username.", HttpStatus.BAD_REQUEST);
    }
    
    @RequestMapping("/getUsernames")
    public ResponseEntity<List<String>> getUsernames(@RequestParam(value="auth", required=true) String auth, HttpServletRequest request){
    	
    	if(this.directory.isRegistered(auth)){
    		String authIP = request.getRemoteAddr();
    		this.directory.updateUserIP(auth, authIP);
    		return new ResponseEntity<List<String>>(this.directory.registeredUsers, HttpStatus.OK);
    	}
    	
    	return new ResponseEntity("Username not valid.", HttpStatus.BAD_REQUEST);
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
    		
    		String authIP = request.getRemoteAddr();
    		this.directory.updateUserIP(auth, authIP);
    		
    		if(this.directory.isRegistered(username)){
    			return new ResponseEntity<String>(this.directory.getUserIP(username), HttpStatus.OK);
    		}
    		return new ResponseEntity<String>("Username not valid.", HttpStatus.BAD_REQUEST);
    	}
    	
    	return new ResponseEntity<String>("User not valid.", HttpStatus.BAD_REQUEST);
    }
    
    
}
