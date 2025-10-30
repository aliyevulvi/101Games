package Arcade;

import java.io.IOException;
import java.time.LocalTime;
import java.time.Duration;
import Main.*;

public class Arcade {
	private static int row = 20;
	private static int column = 11;
	private static int[][] board = new int[row][column];
	private static int score = 0;
	private static int x = 15, y = 5;
	private static boolean isGameOver = false;
	private static int fire = 4, second = 1000, level = 1, failure = 0, enemyAction = 0;
	private static long totalSeconds = 0,  minutes = 0, seconds = 0;
	
	public static void main(String[] args) throws IOException {
		startGame();
	}
	
	public static void startGame() throws IOException {
	    //FORMATTING
	    score = 0;
	    x = 15; y = 5;
	    isGameOver = false;
	    fire = 4; second = 1000; level = 1; failure = 0; enemyAction = 0;
	    totalSeconds = 0; minutes = 0; seconds = 0;
	    
	    createBoard();
		createEnemies(level);
		LocalTime start = LocalTime.now();
	    
	    while (!isGameOver){
			
            System.out.print("\033[H\033[2J");
            System.out.flush();
        
	        
            
            bulletAction();
	        

            if (fire == 0) {
                fire = second/250;
                fire();
            } else 
                fire--;
                
	        if (enemyAction == 0){
	            enemyAction = second/200;
	            enemyAction();
	        } else {
	            enemyAction--;
	        }
	        
	       printBoard();
	        
	        try {
				Thread.sleep(100);
			} catch (InterruptedException e){
				e.printStackTrace();
			}
			
			if (System.in.available() > 0){
                int c = System.in.read();
                char ch = (char) c;
                
                if (ch == 'Q'){
                    break;
                } else if (ch == 'a' || ch == 'd'){
                    move(ch);
                } else if (ch == 'f'){
                    pause();
                }
			}
			
			while (System.in.available() > 0) {
                System.in.read();
            }
		
		    if (noEnemy() && level < 3){
				level++;
			}
			
			if (noEnemy()){
			    createEnemies(level);
			
			}
			
			if (failure != 0){
			    failure(failure);
			    isGameOver = true;
			}
			
			LocalTime end = LocalTime.now();
			Duration dur = Duration.between(start, end);
			totalSeconds = dur.getSeconds();
			minutes = totalSeconds / 60;
			seconds = totalSeconds % 60;
                
			
	    }
	        LocalTime end2 = LocalTime.now();
	        Duration dur2 = Duration.between(start, end2);
	        int time = (int) dur2.getSeconds();
	        Stat.updateValue("Arcade", Controller.getCurrentPlayerId(), score, time);
 
	    
	    
	}
	
	private static void pause() throws IOException {
	    
	    while (true){
	        
	        System.out.println("--------------------");
	        System.out.println("Press \"F\" For Resume");
	        System.out.println("--------------------");
	        
	        if (System.in.available() > 0){
                    int d = System.in.read();
                    char pause = (char) d;
                    
                    if (pause == 'f')
                        break;
	        }
	        
	        try {
				Thread.sleep(100);
			} catch (InterruptedException e){
				e.printStackTrace();
			}
			
			System.out.print("\033[H\033[2J");
            System.out.flush();
	    }
	}
	
	private static void playerStats(){
	    
	    System.out.println("--------Stats-------");
	    System.out.println("Eliminated Enemies: " + (score/10) + " | Score: " + score);
	    System.out.println("Survival Time: " + minutes + ":" + seconds);
	    System.out.println("--------------------");
	    
	}
	
	private static void failure(int f){
	    System.out.print("\033[H\033[2J");
        System.out.flush();
        
	    String message = f == 1 ? "Enemies Killed You!" : "Enemies Passed You";
	    System.out.println("--------------------");
	    System.out.println("Game Over!");
	    System.out.println(message);
	    System.out.println("--------------------");
	    System.out.println();
	    
	    playerStats();
	    
	}
	
	private static void enemyAction(){
	    for (int i = row - 2; i >=0; i--){
	        for (int j = 0; j < column; j++){
	            if (board[i][j] == 3){
	                if (board[i+1][j] == 0){
	                    board[i+1][j] = 3;
	                    board[i][j] = 0;
	                    if (i == x)
	                        failure = 2;
	                } else if (board[i+1][j] == 2) {
	                    board[i+1][j] = 0;
	                    board[i][j] = 0;
	                    score +=10;
	                } else if (board[i+1][j] == 1){
	                    board[i+1][j] = 0;
	                    board[i][j] = 0;
	                    failure = 1;
	                } else if (board[i+1][j] == 3){
	                    
	                }
	            }
	        }
	    }
	    
	    
	}

	private static void createEnemies(int level){
		int numberOfEnemies = level*2;
		int x = 0, y = 0;

		for (int i = 0; i < numberOfEnemies; i++) {
			x = (int) (Math.random() * 5 + 1);
			y = (int) (Math.random() * board[0].length);

			if (board[x][y] == 3){
				i--;
			} else {
				board[x][y] = 3;
			}
		}
	}

	private static boolean noEnemy(){
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] == 3)
					return false;
			}
		}

		return true;
	}
	
	private static void move(char ch){
	    
	    if (ch == 'a'){
	        for (int j = 1; j < column; j++){
	            if (board[x][j] == 1 && board[x][j-1] == 0) {
	                board[x][j] = 0;
	                board[x][j-1] = 1;
	                y = j - 1;
	                break;
	            }
	            
	        }
	    } else if (ch == 'd') {
	        for (int j = 0; j < column-1; j++){
	            if (board[x][j] == 1 && board[x][j+1] == 0) {
	                board[x][j] = 0;
	                board[x][j+1] = 1;
	                y = j + 1;
	                break;
	            }
	            
	        }
	    }
	    
	    
	}
	private static void createBoard(){
	    
	    for (int i = 0; i < row; i++){
	        for (int j = 0; j < column; j++){
	            board[i][j] = 0;
	        }
	    }
	    
	    board[x][y] = 1;
	}
	
	private static void fire(){
	    int bullet = 2;
	    
	    if (board[x-1][y] == 0){
	        board[x-1][y] = bullet;
	    }
	}
	
	private static void bulletAction() {
	    for (int i  = 0; i < x; i++){
	        for (int j = 0; j < column; j++){

	            if (board[i][j] == 2 && i == 0){
	                board[i][j] = 0;
	            } else if (board[i][j] == 2 && board[i-1][j] == 3) {
	                board[i][j] = 0;
	                board[i-1][j] = 0;
	                score +=  10;
	            } else if (board[i][j] == 2 && board[i-1][j] == 0){
	                board[i][j] = 0;
	                board[i-1][j] = 2;
	            }
	                
	                
	        }
	    }
	    
	    
	}
	
	private static void printBoard(){
	    
	    System.out.println("\n");
	    System.out.print("     ");
	    for (int i = 0; i < column; i++){
	        System.out.print(" X");
	    }
	    System.out.println();
	    
	    for (int i = 0; i< row; i++){
	        System.out.print("    | ");
	        for (int j = 0; j < column; j++){
	            if (board[i][j] == 0){
	                System.out.print("  ");
	            } else if (board[i][j] == 1) {
	                System.out.print("A ");
	            } else if (board[i][j] == 2){
	                System.out.print(": ");
	            } else if (board[i][j] == 3){
	                System.out.print("V ");
	            }
	            
	        }
	        System.out.println("|");
	    }

		System.out.println("\n[Score: " + score + "]");
		System.out.println("[Duration: " + minutes + ":" + seconds + "]");
	    
	    
	}
	
}