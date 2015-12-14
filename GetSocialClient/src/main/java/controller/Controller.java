package controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.Backend;

@RestController
public class Controller {

	private List<String> updates = new ArrayList<String>();
	
    @RequestMapping("/postUpdate")
    public ResponseEntity<String> postUpdate(@RequestParam(value="message", required=true) String message){
    	
    	updates.add(message);
        return new ResponseEntity<String>("Message added to updates.", HttpStatus.OK);
    }
    
    @RequestMapping("/getUpdates")
    public ResponseEntity<List<String>> getUpdates(@RequestParam(value="auth", required=true) String auth){
    	
    	if(Backend.isValidUser(auth)){
    		return new ResponseEntity<List<String>>(updates, HttpStatus.OK);
    	}
    	
    	return new ResponseEntity("Invalid username - please make sure you are registered with the server", HttpStatus.UNAUTHORIZED);
    }
    
    @RequestMapping("/fetchUpdates")
    public ResponseEntity<String> fetchUpdates(@RequestParam(value="username", required=true) String username){
    	
    	return Backend.getUpdates(username);
    }
    
    @RequestMapping("/getUsernames")
    public ResponseEntity<String> getUsernames(){
    	
    	return Backend.getUsernames();
    }
}
