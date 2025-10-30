package Main;

import java.util.Scanner;

public class Login {
	public static void main(String[] args) {
		
	}
	
	public static boolean signIn(){
	    String nickname = "undefined", password = "undefined"; 
	    try {
	        Scanner input = new Scanner(System.in);
	        System.out.println("\n------------------------");
	        System.out.print("[ Nickname ] : ");
	        nickname = input.nextLine();
	        System.out.print("[ Password ] : ");
	        password = input.nextLine();
	        System.out.println("------------------------");
	        //input.close();
	    } catch (Exception e) {
	        System.out.println("[ Please Enter Valid Value ]");
	    }
	    
	    Player.getPlayersFromJson();
	    
	    for (Player p : Player.getPlayers()){
	        if (p.getPlayerNickname().equals(nickname) && p.getPlayerPassword().equals(password)){
	            System.out.println("[ Signed in Successfully ]");
	            Controller.setCurrentPlayerId(p.getPlayerId());
	            return true;
	        }
	            
	    }
	    
	    System.out.println("[ Nickname or Password is Incorrect ]");
	    return false;
	    
	}
	
	public static boolean signUp(){
	    String nickname = "undefined", email = "undefined", password = "undefined";
	    Player.getPlayersFromJson();
	    
	    while (true){
	    try {
	        System.out.println("\n------------------------");
	        Scanner input = new Scanner(System.in);
	        System.out.print("[ Define Nickname ] : ");
	        nickname = input.nextLine();
	        System.out.print("[ Define password ] : ");
	        password = input.nextLine();
	        System.out.print("[ Set Email ] : ");
	        email = input.nextLine();
	        System.out.println("------------------------");
	        //input.close();
	    } catch (Exception e){
	        System.out.println("[ Please Enter Valid Value ]");
	    }
	    
	    for (Player p : Player.getPlayers()){
	        if (p.getPlayerEmailAddress().equals(email) || p.getPlayerNickname().equals(nickname)){
	            System.out.println("[ This Email or Nickname Currently Using ]");
	            return false;
	        }
	        
	    }
	    
	    break;
	    
    	}
    	
    	Player newPlayer = new Player(nickname, email, password);
    	Player.createPlayerData(newPlayer);
    	
    	Controller.setCurrentPlayerId(newPlayer.getPlayerId());
    	
    	return true;
	    
	}
}