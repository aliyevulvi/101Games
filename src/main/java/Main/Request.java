package Main;

import java.util.ArrayList;
import java.time.LocalTime;
import java.time.LocalDate;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import com.google.gson.Gson;
import java.util.Scanner;

public class Request {
	private Player fromWho = null, toWhom = null;
	private String reqStatus = "pending", reqType = "undefined";
	private int creationDay = -1, creationMonth = -1, creationYear = -1;
	private int creationMinute = -1, creationHour;
	
	private static ArrayList<Request> reqs = new ArrayList<>();
	private static final File projectRoot = new File(System.getProperty("user.dir"));
    private static final File dataFile = new File(projectRoot, "Data/request.json");
	
	public Request(Player fromWho, Player toWhom){
	    this.fromWho = fromWho;
	    this.toWhom = toWhom;
	    this.reqType = "Friendship";
	    
	    
	    LocalTime creationTime = LocalTime.now();
	    LocalDate creationDate = LocalDate.now();
	    
	    this.creationDay = creationDate.getDayOfMonth();
	    this.creationMonth = creationDate.getMonthValue();
	    this.creationYear = creationDate.getYear();
	    this.creationMinute = creationTime.getMinute();
	    this.creationHour = creationTime.getHour();
	    
	    
	    
	}
	
	public int getDay(){
	    return creationDay;
	}
	
	public int getMonth () {
	    return creationMonth;
	}
	
	public int getMinute(){
	    return creationMinute;
	}
	
	public int getHour(){
	    return creationHour;
	}
	
	public Player getFromWho(){
	    return fromWho;
	}
	
	public Player getToWhom(){
	    return toWhom;
	}
	
	public String getReqStatus(){
	    return reqStatus;
	}
	
	public String getReqType(){
	    return reqType;
	}
	
	public void setReqStatus(){
	    String status = "undefined";
	    System.out.println("\n");
	    try {
	        Scanner input = new Scanner(System.in);
	        System.out.print("\033[1A");        
            System.out.print("\033[2K");
	        System.out.print("[ Enter \"ACCEPT\" Or \"REJECT\" ] : ");
	        status = input.nextLine();
	        
	        if (status.equals("ACCEPT")){
	            this.reqStatus = "ACCEPTED";
	            Request.createFriendship(fromWho.getPlayerId(), toWhom.getPlayerId());
	        } else if (status.equals("REJECT")){
	            this.reqStatus = "REJECTED";
	        } else {
	            setReqStatus();
	        }
	        
	    } catch (Exception e) {
	        setReqStatus();
	    }
	}
	
	public static void createFriendship(int fromId, int toId){
	    Player.getPlayersFromJson();
	    Player senderPlayer = null, receiverPlayer = null;
	    
	    
	    for (Player p : Player.getPlayers()){
	        if (p.getPlayerId() == fromId){
	            for (Player p2 : Player.getPlayers()){
	                if (p2.getPlayerId() == toId){
	                    p.getFriends().add(p2);
	                }
	            }
	        } else if (p.getPlayerId() == toId){
	            for (Player p2 : Player.getPlayers()){
	                if (p2.getPlayerId() == fromId){
	                    p.getFriends().add(p2);
	                }
	            }
	        }
	    }
	    
	    Player.writePlayersToJson();
	}
	
	public static void sendRequest(Request req){
	    Notification.sendNot(new Notification(req.getToWhom().getPlayerId(), req.getReqType(), (req.getFromWho().getPlayerNickname() + " Wants Be Friend with You"), req.getFromWho().getPlayerNickname()));
	    getReqsFromJson();
	    reqs.add(req);
	    writeReqsToJson();
	}
	
	public static void getReqsFromJson(){
	    Gson gson = new Gson();
	    try {
	        File file = new File(dataFile.getAbsolutePath());
	        
	        if (!file.exists()){
	            file.createNewFile();
	        }
	
	        FileReader reader = new FileReader(dataFile.getAbsolutePath());
            Request[] reqArray = gson.fromJson(reader, Request[].class);
            reader.close();
            reqs.clear();
            
            if (reqArray != null){
                for (Request r : reqArray){
                    reqs.add(r);
                }
            }        
	        
	    } catch (Exception e) {}
	}
	
	public static void writeReqsToJson(){
	    Gson gson = new Gson();
	    File file = new File(dataFile.getAbsolutePath());
	     
	    try {
	        if (!file.exists()){
	        file.createNewFile();
	    }
	        
	        FileWriter writer = new FileWriter(dataFile.getAbsolutePath());
            gson.toJson(reqs, writer);
            writer.close();
	    } catch (Exception e){
	       
	    }
	}
	
	public static ArrayList<Request> getReqs(){
	    return reqs;
	}
	
	public static void main(String[] args) {
		
	}
}