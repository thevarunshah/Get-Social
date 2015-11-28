package controller;

import org.apache.http.HttpResponse;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import backend.Backend;

@SpringBootApplication
public class Application {

	public static String username = "thevarunshah";
	
    public static void main(String[] args) {
    	
        ApplicationContext context = SpringApplication.run(Application.class, args);
        if(!checkUser()){
    		System.out.println("Could not find username on server, please verify your username and reboot your interface.");
        	SpringApplication.exit(context, new ExitCodeGenerator[0]);
        }
    }
    
    private static boolean checkUser(){
    	
    	HttpResponse response = Backend.get(Backend.server + "/checkUser?username=" + username);
    	
    	if(response.getStatusLine().getStatusCode() != 200){
    		return false;
    	}
    	return true;
    }
    
    public String getUsername(){
    	return username;
    }
}
