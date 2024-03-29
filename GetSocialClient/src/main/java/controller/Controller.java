package controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.Backend;

@RestController
public class Controller {

	private Profile profile;
	
	/**
	 * Constructor to initiate the profile from disk for this username or create a blank one
	 */
	public Controller() {
		this.profile = Profile.get(Application.username);
	}
	
    @RequestMapping("/postUpdate")
    public ResponseEntity<String> postUpdate(@RequestParam(value="message", required=true) String message){
    	
    	profile.post(message);
        return new ResponseEntity<String>("Message added to updates.", HttpStatus.OK);
    }
    
    @RequestMapping("/getUpdates")
    public ResponseEntity<List<String>> getUpdates(@RequestParam(value="auth", required=true) String auth){
    	
    	if(Backend.isValidUser(auth)){
    		if(Backend.isReputedUser(auth)){
	    		
    			Backend.updateReputation();
	    		return new ResponseEntity<List<String>>(profile.getUpdates(), HttpStatus.OK);
    		}
    		return new ResponseEntity("You do not have a high enough reputation to receive my updates.", HttpStatus.UNAUTHORIZED);
    	}
    	
    	return new ResponseEntity("Invalid username - please make sure you are registered with the server.", HttpStatus.UNAUTHORIZED);
    }
    
    @RequestMapping("/fetchUpdates")
    public ResponseEntity<String> fetchUpdates(@RequestParam(value="username", required=true) String username){
    	
    	return Backend.getUpdates(username);
    }
    
    @RequestMapping("/getUsernames")
    public ResponseEntity<String> getUsernames(){
    	
    	return Backend.getUsernames();
    }
    
    @RequestMapping("/reportUser")
    public ResponseEntity<String> reportUser(@RequestParam(value="username", required=true) String username){
    	
    	if(Backend.reportUser(username)){
    		return new ResponseEntity<String>("User reported successfully!", HttpStatus.OK);
    	}
    	
    	return new ResponseEntity<String>("Could not report the user.", HttpStatus.BAD_REQUEST);
    }
}
