package Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import com.google.gson.Gson;

public class Stat {
	private int playerId = -1;
	private HashMap<String, Integer[]> statMap = new HashMap<>();
	
	private static final File projectRoot = new File(System.getProperty("user.dir"));
    private static final File dataFile = new File(projectRoot, "Data/stat.json");
	
	private static ArrayList<Stat> stats = new ArrayList<>();
	
	public int getPlayerId(){
	    return playerId;
	}
	
	public void setPlayerId(int id){
	    playerId = id;
	}
	
	public static ArrayList<Stat> getStats(){
	    return stats;
	}
	
	public HashMap<String, Integer[]> getStatMap(){
	    return statMap;
	}
	
	public static void createStat(int id){
	    Stat stat = new Stat();
	    stat.setPlayerId(id);
	    
	    stat.getStatMap().put("Minesweeper", new Integer[]{0,0});
	    stat.getStatMap().put("Tetris", new Integer[]{0,0});
	    stat.getStatMap().put("Arcade", new Integer[]{0,0});
	    
	    getStatsFromJson();
	    stats.add(stat);
	    setStatsToJson();
	    
	    
	}
	
	public static void setStatsToJson(){
	    Gson gson = new Gson();
	    
	    File file = new File(dataFile.getAbsolutePath());
	    
	    
	    
	    try {
	        if (!file.exists()){
	        file.createNewFile();
	    }
	        
	        FileWriter writer = new FileWriter(dataFile.getAbsolutePath());
            gson.toJson(stats, writer);
            writer.close();
	    } catch (Exception e){
	        System.out.println(e.toString());
	    }
	}
	
	public static void getStatsFromJson(){
	    Gson gson = new Gson();
	    
	    File file = new File(dataFile.getAbsolutePath());
	    
	    
	    
	    try {
	        if (!file.exists()){
	        file.createNewFile();
	    }
	        
	        FileReader reader = new FileReader(dataFile.getAbsolutePath());
            Stat[] statArray = gson.fromJson(reader, Stat[].class);
            reader.close();
            stats.clear();
            
            if (statArray != null){
                for (Stat s : statArray){
                    stats.add(s);
                }
            }
	    } catch (Exception e) {
	        System.out.println(e.toString());
	    }
	}
	
	public static void updateValue(String game, int playerId, int score, int time){
	    getStatsFromJson();
	    
	    for (Stat s : stats){
	        if (s.getPlayerId() == playerId){
	            s.getStatMap().get(game)[0] += score;
	            s.getStatMap().get(game)[1] += time;
	        }
	    }
	    
	    
	    setStatsToJson();
	    System.out.println("Stats registered");
	    
	    try {
	        Thread.sleep(2000);
	    } catch (Exception e){
	        
	    }
	    
	    
	}
	
	public static void main(String[] args) {
		
	}
}