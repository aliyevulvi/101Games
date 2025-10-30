package Main;

import java.time.LocalTime;
import java.time.LocalDate;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import com.google.gson.Gson;

public class Notification {
	private int playerId = -1;
	private int creationDateYear = -1;
	private int creationDateMonth = -1;
	private int creationDateDay = -1;
	private int creationTimeHour = -1;
	private int creationTimeMinute = -1;
	private String subject = "", content = "", sender = "";
	private boolean isRead = false;
	
	private static ArrayList<Notification> notifications = new ArrayList<>();
	private static final File projectRoot = new File(System.getProperty("user.dir"));
    private static final File dataFile = new File(projectRoot, "Data/notification.json");
	
	
	public Notification(int id, String subject, String content, String sender){
	    this.playerId = id;
	    this.subject = subject;
	    this.content = content;
	    this.sender = sender;
	    this.creationDateYear = LocalDate.now().getYear();
	    this.creationDateMonth = LocalDate.now().getMonthValue();
	    this.creationDateDay = LocalDate.now().getDayOfMonth();
	    this.creationTimeHour = LocalTime.now().getHour();
	    this.creationTimeMinute = LocalTime.now().getMinute();
	}
	
	public static void openNot(int playerId, int selected){
	    int counter = 0;
	    Notification currentNot = null;
	    
	    for (Notification n : notifications){
	        if (n.getPlayerId() == playerId){
	            counter++;
	        }
	        
	        if (counter == selected){
	            currentNot = n;
	            n.setRead();
	        }
	    }
	    
	    saveNotsToJson();
	    
	    
	    System.out.println("[----------------------]");
	    System.out.println("Sender: " + currentNot.getSender() + " | Subject: " + currentNot.getSubject());
	    System.out.println(currentNot.getContent());
	    System.out.println("[----------------------]");
	    System.out.println("\n");
	    UserInterface.quit();
	    
	}
	
	public static void sendNot(Notification n){
	    getNotsFromJson();
	    notifications.add(n);
	    saveNotsToJson();
	}
	
	public  int getPlayerId(){
	    return playerId;
	}
	
	public boolean isRead(){
	    return isRead;
	}
	
	public void setRead(){
	    this.isRead = true;
	}
	
	public int getCreationDateYear(){
	    return creationDateYear;
	}
	
	public int getCreationDateMonth(){
	     return creationDateMonth;   
	}
	
	public int getCreationDateDay(){
	    return creationDateDay;
	}
	
	public int getCreationTimeHour(){
	    return creationTimeHour;
	}
	
	public int getCreationTimeMinute(){
	     return creationTimeMinute;   
	}
	
	public String getSubject(){
	    return subject;
	}
	
	public String getContent(){
	     return content;   
	}
	
	public String getSender(){
	    return sender;
	}
	
	public static ArrayList<Notification> getNots(){
	    return notifications;
	}
	
	public static void saveNotsToJson() {
	    Gson gson = new Gson();
	    File file = new File(dataFile.getAbsolutePath());
	     
	    try {
	        if (!file.exists()){
	        file.createNewFile();
	    }
	        
	        FileWriter writer = new FileWriter(dataFile.getAbsolutePath());
            gson.toJson(notifications, writer);
            writer.close();
	    } catch (Exception e){
	        System.out.println(e.toString());
	    }
	}
	
	public static void getNotsFromJson() {
	    Gson gson = new Gson();
	 
	    
	    File file = new File(dataFile.getAbsolutePath());
	    
	    try {
	        if (!file.exists()){
	        file.createNewFile();
	    }
	        
	        FileReader reader = new FileReader(dataFile.getAbsolutePath());
            Notification[] notArray = gson.fromJson(reader, Notification[].class);
            reader.close();
            notifications.clear();
            
            if (notArray != null){
                for (Notification n : notArray){
                    notifications.add(n);
                }
            }
	    } catch (Exception e) {
	        System.out.println(e.toString());
	    }
	}
	
	public static void main(String[] args) {
		
	}
}