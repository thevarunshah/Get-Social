package backend;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import controller.Application;

public class Backend {
	
	public static String server = "http://localhost:8080";
    
    public static boolean isValidUser(String username){
    	
    	HttpResponse response = get(server + "/isValidUser?auth=" + Application.username + "&username=" + username);
    	
    	if(response.getStatusLine().getStatusCode() != 200){
    		return false;
    	}
    	return true;
    }
    
    public static ResponseEntity<String> getUsernames(){
    	
    	HttpResponse response = get(server + "/getUsernames?auth=" + Application.username);
    	if(response.getStatusLine().getStatusCode() != 200){
    		return new ResponseEntity<String>("Something went wrong, please try again.", HttpStatus.BAD_REQUEST);
    	}
    	
		String usersList = extractResponse(response);
    	return new ResponseEntity<String>(usersList, HttpStatus.OK);
    }
    
    public static ResponseEntity<String> getUpdates(String username){
    	
    	HttpResponse ipResponse = get(server + "/getUserIP?auth=" + Application.username + "&username=" + username);
    	if(ipResponse.getStatusLine().getStatusCode() != 200){
    		return new ResponseEntity<String>("Couldn't find a valid user with the given username.", HttpStatus.BAD_REQUEST);
    	}
    	String userIP = extractResponse(ipResponse);
    	System.out.println(userIP);
    	HttpResponse response = get("http://" + userIP + "/getUpdates?auth=" + Application.username);
    	if(response.getStatusLine().getStatusCode() != 200){
    		return new ResponseEntity<String>("Something went wrong, please try again.", HttpStatus.BAD_REQUEST);
    	}
    	String updates = extractResponse(response);
    	
    	return new ResponseEntity<String>(updates, HttpStatus.OK);
    }
    
    public static HttpResponse get(String url){
		
		try{
			 
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet get = new HttpGet(url);
			
			HttpResponse response = client.execute(get); 
			return response;
			
		} catch(Exception e){
			
			System.out.println("exception: " + e);
		}
		
		return null;
	}
    
    private static String extractResponse(HttpResponse response){
		
		String responseString = "";
		
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = "";
			while ((line = br.readLine()) != null){
				responseString += line;
			}
		} catch(Exception e){
			System.out.println("exception: " + e);			
		}
		
		return responseString;
	}
}
