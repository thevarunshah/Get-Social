package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	List<String> registeredUsers = new ArrayList<String>();
	Map<String, String> userIPMap = new HashMap<String, String>();
    
    @RequestMapping("/register")
    public ResponseEntity<String> register(@RequestParam(value="username", required=true) String username, HttpServletRequest request){
    	
    	String userIP = request.getRemoteAddr();
    	if(registeredUsers.contains(username)){
    		return new ResponseEntity<String>("Username already exists.", HttpStatus.BAD_REQUEST);
    	}
    	
    	registeredUsers.add(username);
    	userIPMap.put(username, userIP);
    	return new ResponseEntity<String>("Successfully registered!", HttpStatus.OK);
    }
    
    @RequestMapping("/checkUser")
    public ResponseEntity<String> checkUser(@RequestParam(value="username", required=true) String username, HttpServletRequest request){
    	
    	String userIP = request.getRemoteAddr();
    	if(registeredUsers.contains(username)){
        	userIPMap.put(username, userIP);
    		return new ResponseEntity<String>("Username valid.", HttpStatus.OK);
    	}
    	
    	return new ResponseEntity<String>("Could not find username.", HttpStatus.BAD_REQUEST);
    }
    
    @RequestMapping("/getUsernames")
    public ResponseEntity<List<String>> getUsernames(@RequestParam(value="username", required=true) String username, HttpServletRequest request){
    	
    	String userIP = request.getRemoteAddr();
    	if(registeredUsers.contains(username)){
    		userIPMap.put(username, userIP);
    		return new ResponseEntity<List<String>>(registeredUsers, HttpStatus.OK);
    	}
    	
    	return new ResponseEntity("Username not valid.", HttpStatus.BAD_REQUEST);
    }
    
    @RequestMapping("/getUserIP")
    public ResponseEntity<String> getUserIP(@RequestParam(value="user", required=true) String user, 
    		@RequestParam(value="username", required=true) String username, HttpServletRequest request){
    	
    	String userIP = request.getRemoteAddr();
    	if(registeredUsers.contains(user)){
    		
    		userIPMap.put(user, userIP);
    		if(registeredUsers.contains(username)){
    			return new ResponseEntity<String>(userIPMap.get(username), HttpStatus.OK);
    		}
    		return new ResponseEntity<String>("Username not valid.", HttpStatus.BAD_REQUEST);
    	}
    	
    	return new ResponseEntity<String>("User not valid.", HttpStatus.BAD_REQUEST);
    }
}
