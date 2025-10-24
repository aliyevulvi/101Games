package Main;

import java.util.Scanner;
import java.io.IOException;

public class Controller {
	private static int currentPlayerId = -1;
	
	public static void main(String[] args) throws IOException {
		startProgram();
	}
	
	private static void startProgram() throws IOException {
	    
	    System.out.println("\nWelcome to 101Games, Please Sign in or Sign Up!\n");
	    
	    //UserInterface.LoginInterface();
	    UserInterface.MenuInterface();
	    
		
	}
	
	public static void setCurrentPlayerId(int id){
	    currentPlayerId = id;
	}
	
	public static int getCurrentPlayerId(){
	    return currentPlayerId;
	}
	
	
}
