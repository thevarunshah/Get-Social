package controller;

import org.apache.http.HttpResponse;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import backend.Backend;

@SpringBootApplication
public class Application {

	public static String username;
	public static int port;
	
    public static void main(String[] args) {
    	
    	username = args[0];
    	port = Integer.parseInt(args[1]);
    	
    	System.out.println("your username is: " + username);
    	
        ApplicationContext context = SpringApplication.run(Application.class, args);
        
        if(!loginUser()){
    		System.out.println("Could not find username on server, please verify your username and reboot your interface.");
        	SpringApplication.exit(context, new ExitCodeGenerator[0]);
        }
    }
    
    /**
     * Log the user in on startup to make sure they've been registered with the directory and update their ip
     * @return boolean True is successful, false otherwise
     */
    private static boolean loginUser(){
    	
    	HttpResponse response = Backend.get(Backend.server + "/loginUser?auth=" + username + "&port=" + port);
    	if(response.getStatusLine().getStatusCode() != 200){
    		return false;
    	}
    	return true;
    }
    
    /**
     * Get the username of this client
     * @return String the client's username
     */
    public String getUsername(){
    	return username;
    } 
}
