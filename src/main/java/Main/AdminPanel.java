package Main;

import java.io.File;
import java.util.Scanner;

public class AdminPanel {
	private static final File projectRoot = new File(System.getProperty("user.dir"));
    private static final File dataFile = new File(projectRoot, "Data/stat.json");
	private static final File dataFile2 = new File(projectRoot, "Data/data.json");
	private static final File dataFile3 = new File(projectRoot, "Data/notification.json");
	private static final File dataFile4 = new File(projectRoot, "Data/request.json");
	
	
	public static void main(String[] args) {
		
	}
	
	public static void AdminPanelMenu(){
	    
	    int selection = -1;
	    
	    while (true) {
	        System.out.println("[----Admin Panel----]");
	        System.out.println("[ Remove All Data (1) ]");
	        System.out.println("[ Show All Data (2) ]");
	        System.out.println("[ Create Me (3) ]");
	        System.out.println("[ Delete Player ]");
	        System.out.println("[-------------------]");
	        System.out.print("[ Select an Option ] : ");
	    
	        try{
	            Scanner input = new Scanner(System.in);
	            selection = input.nextInt();
	        
	            if (selection < 10)
	                break;
	        } catch (Exception e){
	        
	        }
	    }
	    
	    switch (selection){
	        case 1: removeAllData();
	        break;
	        case 2: showAllData();
	        break;
	        case 3: createMe();
	        break;
	        case 4: removePlayerData();
	        break;
	    }
	}
	
	public static void removePlayerData(){
	    int id = -1;
	    
	    try { 
	        Scanner input = new Scanner(System.in);
	        System.out.print("[ Enter Player ID ] : ");
	        id = input.nextInt();
	        
	    } catch (Exception e){
	        
	    }
	    
	    Player.removePlayerData(id);
	    System.out.println("[ Player Deleted ]");

        AdminPanelMenu();
	}
	
	public static void removeAllData(){
	    File file = new File(dataFile.getAbsolutePath());
	    File file2 = new File(dataFile2.getAbsolutePath());
	    File file3 = new File(dataFile3.getAbsolutePath());
	    File file4 = new File(dataFile4.getAbsolutePath());
	    
	    if (file.exists()){
	        file.delete();
	    }
	    
	    if (file2.exists()){
	        file2.delete();
	    }
	    
	    if (file3.exists())
	        file3.delete();
	        
	    if (file4.exists())
	        file4.delete();
	}
    	
	public static void showAllData(){
	    Player.getPlayersFromJson();
	    
	    int selection = -1;
	    
	    while (true) {
	        System.out.println("ID  |  Nickname  |  Email");
	        for (Player p : Player.getPlayers()){
	            System.out.println(p.getPlayerId() + " | " + p.getPlayerNickname() + " | " + p.getPlayerEmailAddress());
	        }
	    
	        System.out.println("\n[ Select an Option ] : ");
	    
	        try {
	            Scanner input = new Scanner(System.in);
	            selection = input.nextInt();
	            
	            if (selection == 0)
	                break;
	        
	        } catch (Exception e){
	        
	        }
	    }
	}
	
	public static void createMe(){
	    Player me = new Player("aliyew" , 1 , "aliyevulvi0012@gmail.com", "123");
	    Player.createPlayerData(me);
	}
}