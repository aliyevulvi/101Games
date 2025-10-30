package Main;

import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;

public class UserInterface {
    public static void main(String[] args) {}

    public static void LoginInterface() {
        boolean bool = false;
        while (!bool) {
            try {
                Scanner input = new Scanner(System.in);
                System.out.print("\n[ Sign In (0) ][ Sign Up (1) ] : ");
                int x = input.nextInt();

                if (!((x == 0) || (x == 1))) {
                    System.out.println("[ Please Enter Only `0` or `1` ]\n");
                } else {
                    switch (x) {
                        case 0:
                            bool = Login.signIn();
                            break;
                        case 1:
                            bool = Login.signUp();
                            break;
                    }
                }

            } catch (Exception e) {
                System.out.println("[ Please Enter Only `0` or `1` ]\n");
                System.out.println(e.toString());
            }
        }
    }

    public static void MenuInterface() throws IOException {
        int selection = -1;

        //System.out.print("\033[H\033[2J");
//        System.out.flush();

        System.out.println("[ -----Menu----- ]");
        System.out.println("[ Minesweeper (1) ]");
        System.out.println("[ Tetris (2) ]");
        System.out.println("[ Arcade (3) ]");
        System.out.println("[ Rank (4) ]");
        System.out.println("[ Notifications (5) ]");
        System.out.println("[ Friends (6) ]");
        System.out.println("[ Settings (7) ]");
        System.out.println("[ Exit (0) ]");
        System.out.println("[---------------]");

        while1:
        while (true) {
            try {
                Scanner input = new Scanner(System.in);
                System.out.print("\n[ Please Select Option ] : ");
                selection = input.nextInt();

                if (selection == 111) {
                    break while1;
                } else if ( (selection / 10) != 0 ) {
                    System.out.println("[ Please Enter 0-9 ]");
                 } else  {
                    break while1;
                }

            } catch (Exception e) {
                
            }
        }

        switch (selection){
            case 1: Minesweeper.Minesweeper.startGame();
            break;
            case 2: Tetris.Tetris.main(new String[0]);
            break;
            case 3: Arcade.Arcade.startGame();
            break;
            case 4: showRanking();
            break;
            case 5: showNotifications();
            break;
            case 6: showFriends();
            break;
            case 7: System.out.println(" Settings ");
            break;
            case 0: System.out.println(" Exit "); System.exit(0);
            break;
            case 111: AdminPanel.AdminPanelMenu();
            break;
        }
        
        MenuInterface();
    }
    
    private static void showFriends(){
        
        Player currentPlayer = null;
        for (Player p : Player.getPlayers()){
            if (p.getPlayerId() == Controller.getCurrentPlayerId()){
                currentPlayer = p;
            }
        }
        
        System.out.println("[------------]");
        if (currentPlayer.getFriends().size() == 0){
            System.out.println("[ No Friends ]");
        } else {
            for (Player p : currentPlayer.getFriends()){
                System.out.println("[ "+ p.getPlayerNickname() +" ] ");
            }
        }
        System.out.println("[------------]");
        System.out.println();
        
        System.out.printf("[ %-20s ]", "Show Requests (1)");
        System.out.printf("[ %-20s ]", "Send Request (2)");
        System.out.printf("[ %-20s ]", "Remove Friend (3)");
        System.out.printf("[ %-20s ]", "Send Meesage (4)");
        System.out.printf("[ %-20s ]", "Exit (0)");
        System.out.print("\n[ Select Option ] : ");
        
        int selection = -1;
        try {
            Scanner input = new Scanner(System.in);
            selection = input.nextInt();
            
            if (selection < 0 && selection > 4)
                showFriends();
        } catch (Exception e){
            showFriends();
        }
        
        switch (selection){
            case 1: showReqs();
            break;
            case 2: System.out.println("Send Request");
            break;
            case 3: System.out.println("Remove Friend");
            break;
            case 4: System.out.println("Send Message");
            break;
            case 0: 
            break;
        }
        
    }
    
    public static void showReqs(){
        Request.getReqsFromJson();
        
        System.out.println("[-----------------------------------]");
        System.out.printf("[ %-10s %-15s %-10s %-5s ]%n", "Sender", "Type", "Status", "Data");
        for (Request r : Request.getReqs()){
            if (r.getToWhom().getPlayerId() == Controller.getCurrentPlayerId()){
                System.out.printf("[ %-10s %-15s %-10s %-2d/%-2d ]%n", r.getFromWho().getPlayerNickname(), r.getReqType(), r.getReqStatus(), r.getDay(), r.getMonth());
            }
        }
        System.out.println("[-----------------------------------]");
    }
    
    private static void showRanking() throws IOException{
        Player.getPlayersFromJson();
        Stat.getStatsFromJson();
        ArrayList<Stat> compList = new ArrayList<>();
        
        System.out.print("\033[H\033[2J");
        System.out.flush();
        
        
        System.out.println("[--------Minesweeper---------]");
        System.out.printf("[ %-10s %-6s %-8s ]%n", "Nickname", "Score", "Playtime");
        
        Stat maxValue; 
        Player currentPlayer = null;
        for (int i = 0; i < Stat.getStats().size();){
            maxValue = Stat.getStats().get(i);
            for (int j = 0; j < Stat.getStats().size()-1; j++){ 
                if (maxValue.getStatMap().get("Minesweeper")[0] < Stat.getStats().get(j+1).getStatMap().get("Minesweeper")[0]){
                    maxValue = Stat.getStats().get(j+1);
                } else if (maxValue.getStatMap().get("Minesweeper")[0] == Stat.getStats().get(j+1).getStatMap().get("Minesweeper")[0] && maxValue.getStatMap().get("Minesweeper")[1] > Stat.getStats().get(j+1).getStatMap().get("Minesweeper")[1]) {
                    maxValue = Stat.getStats().get(j+1);
                }
            }
            
            for (Player p : Player.getPlayers()){
                if (p.getPlayerId() == maxValue.getPlayerId()){
                    currentPlayer = p;
                }
            }
            System.out.printf("[ %-10s %-6d %-8d ]%n", currentPlayer.getPlayerNickname(), maxValue.getStatMap().get("Minesweeper")[0], maxValue.getStatMap().get("Minesweeper")[1]);
            
            Stat.getStats().remove(maxValue);
        }
        System.out.println("[----------------------------]");
        System.out.println();
        
        Stat.getStatsFromJson();
        System.out.println("[-----------Tetris-----------]");
        System.out.printf("[ %-10s %-6s %-8s ]%n", "Nickname", "Score", "Playtime");
        
        for (int i = 0; i < Stat.getStats().size();){
            maxValue = Stat.getStats().get(i);
            for (int j = 0; j < Stat.getStats().size()-1; j++){ 
                if (maxValue.getStatMap().get("Tetris")[0] < Stat.getStats().get(j+1).getStatMap().get("Tetris")[0]){
                    maxValue = Stat.getStats().get(j+1);
                } else if (maxValue.getStatMap().get("Tetris")[0] == Stat.getStats().get(j+1).getStatMap().get("Tetris")[0] && maxValue.getStatMap().get("Tetris")[1] > Stat.getStats().get(j+1).getStatMap().get("Tetris")[1]) {
                    maxValue = Stat.getStats().get(j+1);
                }
            }
            for (Player p : Player.getPlayers()){
                if (p.getPlayerId() == maxValue.getPlayerId()){
                    currentPlayer = p;
                }
            }
            System.out.printf("[ %-10s %-6d %-8d ]%n", currentPlayer.getPlayerNickname(), maxValue.getStatMap().get("Tetris")[0], maxValue.getStatMap().get("Tetris")[1]);
            
            Stat.getStats().remove(maxValue);
        }
        System.out.println("[----------------------------]");
        System.out.println();
        
        Stat.getStatsFromJson();
        System.out.println("[-----------Arcade-----------]");
        System.out.printf("[ %-10s %-6s %-8s ]%n", "Nickname", "Score", "Playtime");
        
        for (int i = 0; i < Stat.getStats().size();){
            maxValue = Stat.getStats().get(i);
            for (int j = 0; j < Stat.getStats().size()-1; j++){ 
                if (maxValue.getStatMap().get("Arcade")[0] < Stat.getStats().get(j+1).getStatMap().get("Arcade")[0]){
                    maxValue = Stat.getStats().get(j+1);
                } else if (maxValue.getStatMap().get("Arcade")[0] == Stat.getStats().get(j+1).getStatMap().get("Arcade")[0] && maxValue.getStatMap().get("Arcade")[1] > Stat.getStats().get(j+1).getStatMap().get("Arcade")[1]) {
                    maxValue = Stat.getStats().get(j+1);
                }
            }
            for (Player p : Player.getPlayers()){
                if (p.getPlayerId() == maxValue.getPlayerId()){
                    currentPlayer = p;
                }
            }
            System.out.printf("[ %-10s %-6d %-8d ]%n", currentPlayer.getPlayerNickname(), maxValue.getStatMap().get("Arcade")[0], maxValue.getStatMap().get("Arcade")[1]);
            
            Stat.getStats().remove(maxValue);
        }
        System.out.println("[----------------------------]");
        System.out.println("\n");
        
        quit();
        
    }
    
    public static void showNotifications(){
        Notification.getNotsFromJson();
        
        String readData = "undefined";
        int counter = 0, selection = 0;
        System.out.println("[----------------------Notifications---------------------]");
        System.out.printf("[ %-4s%-12s %-15s %-5s %-10s %-4s ]%n", "No", "Sender", "Subject", "Time", "Date", "Read");
        System.out.println("[--------------------------------------------------------]");
        for (Notification n : Notification.getNots()){
            if (n.getPlayerId() == Controller.getCurrentPlayerId()){
                counter++;
                if (n.isRead())
                    readData = "-";
                else
                    readData = "*";
                System.out.printf("[ %-4d%-12s %-15s %-2d:%-2d %-2d/%-2d/%-4d %-4s ]%n", counter, n.getSender(), n.getSubject(), n.getCreationTimeHour(), n.getCreationTimeMinute(), n.getCreationDateDay(), n.getCreationDateMonth(), n.getCreationDateYear(), readData);
            }
        }
        
        System.out.println("\n");
        
        while (true){
            try {
                System.out.print("\033[1A");        
                System.out.print("\033[2K");
                System.out.print("[ Enter Notification No ( \"0\" For Previos Page) ] :");
                Scanner input = new Scanner(System.in);
                selection = input.nextInt();
                
                if (selection > 0 && selection <= counter){
                    // OPEN NOT
                    Notification.openNot(Controller.getCurrentPlayerId(), selection);
                    break;
                } else if (selection == 0)
                    break;
                else 
                    continue;
            } catch (Exception e) {
                
                } 
        }
        
    }
    
    public static int quit() {
        int selection = -1;
        
        System.out.print("\033[1A");        
        System.out.print("\033[2K");
        System.out.print("\r[ Enter \"0\" to Previous Page ] : ");
        try {
            Scanner input = new Scanner(System.in);
            selection = input.nextInt();
            
            if ( selection == 0 )
            return selection;
        else {
            selection = quit();
        }
        
        } catch (Exception e){
            quit();
        }
        
        
        
        return selection;
        
    }
}