package Main;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Player {
    private String playerNickname = "undefined";
    private int playerId = -1;
    private String playerEmailAddress = "undefined";
    private String playerPassword = "undefined";
    private ArrayList<Player> friends = new ArrayList<>();

    private static ArrayList<Player> players = new ArrayList<>();
    private static final File projectRoot = new File(System.getProperty("user.dir"));
    private static final File dataFile = new File(projectRoot, "Data/data.json");
	

    public Player(String nickname, String email, String pass) {
        this.playerNickname = nickname;
        this.playerEmailAddress = email;
        this.playerId = getNewPlayerId();
        this.playerPassword = pass;
        
        Stat.createStat(this.playerId);
    }

    public Player(String nickname, int id, String email, String pass) {
        this.playerNickname = nickname;
        this.playerId = id;
        this.playerEmailAddress = email;
        this.playerPassword = pass;
        
        Stat.createStat(this.playerId);
    }
    
    public ArrayList<Player> getFriends(){
        return friends;
    }

    public static ArrayList<Player> getPlayers() {
        return players;
    }

    public String getPlayerNickname() {
        return playerNickname;
    }

    public void setPlayerNickname(String nickname) {
        this.playerNickname = nickname;
    }

    public int getPlayerId() {
        return playerId;
    }

    private int getNewPlayerId() {
        while (true) {
            int randomValue = 1000 + (int) (Math.random() * 9000);
            getPlayersFromJson();

            for (Player p : players) {
                if (p.getPlayerId() == randomValue) {
                    continue;
                }
            }
            return randomValue;
        }
    }

    public String getPlayerEmailAddress() {
        return playerEmailAddress;
    }

    public String getPlayerPassword() {
        return playerPassword;
    }

    private static void createPlayer(Player player) {
        Gson gson = new Gson();
    }

    public static boolean createPlayerData(Player player) {
        Gson gson = new Gson();
        getPlayersFromJson();

        players.add(player);
        
        

        try {
            File file = new File(dataFile.getAbsolutePath());
        
        if (!file.exists()){
            file.createNewFile();
        }
        
            File projectRoot = new File(System.getProperty("user.dir"));
            File dataFile = new File(projectRoot, "Data/data.json");
            FileWriter writer = new FileWriter(dataFile.getAbsolutePath());
            gson.toJson(players, writer);
            writer.close();

            System.out.println("[ Your Account Created ]");
        } catch (IOException e) {
            System.out.println("File not changed");
        }

        return true;
    }
    
    public static void writePlayersToJson() {
        Gson gson = new Gson();
        getPlayersFromJson();

        try {
            File file = new File(dataFile.getAbsolutePath());
        
        if (!file.exists()){
            file.createNewFile();
        }
        
            File projectRoot = new File(System.getProperty("user.dir"));
            File dataFile = new File(projectRoot, "Data/data.json");
            FileWriter writer = new FileWriter(dataFile.getAbsolutePath());
            gson.toJson(players, writer);
            writer.close();
            
        } catch (IOException e) {
            System.out.println("File not changed");
        }

    }

    protected static void getPlayersFromJson() {
        Gson gson = new Gson();
        
        

        try {
            File file = new File(dataFile.getAbsolutePath());
        
        if (!file.exists()){
            file.createNewFile();
        }
        
            File projectRoot = new File(System.getProperty("user.dir"));
            File dataFile = new File(projectRoot, "Data/data.json");
            FileReader reader = new FileReader(dataFile.getAbsolutePath());
            Player[] playerArray = gson.fromJson(reader, Player[].class);
            reader.close();

            players.clear();
            if (playerArray != null) {
                for (Player p : playerArray) {
                    players.add(p);
                }
            }

        } catch (IOException e) {
            System.out.println("File coulndt read");
            System.out.println(e.toString());
        }
    }

    public static void removePlayerData(int id) {
        Gson gson = new Gson();
        getPlayersFromJson();

        for (Player p : players) {
            if (p.getPlayerId() == id) {
                players.remove(p);
                break;
            }
        }

        try {
            File projectRoot = new File(System.getProperty("user.dir"));
            File dataFile = new File(projectRoot, "Data/data.json");
            FileWriter writer = new FileWriter(dataFile.getAbsolutePath());
            gson.toJson(players, writer);
            writer.close();
            System.out.println("[ Player Removed ]");
        } catch (IOException e) {
            System.out.println("File not changed");
        }
    }
    

    public static void main(String[] args) {}
}