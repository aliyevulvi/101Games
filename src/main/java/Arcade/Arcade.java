package Arcade;

import java.io.IOException;
import java.time.*;

public class Arcade {
	private static int row = 20;
	private static int column = 11;
	private static int[][] board = new int[row][column];
	private static int score = 0;
	private static int x = 15, y = 5;
	private static boolean isGameOver = false;
	private static int fire = 4, second = 1000, level = 1;
	
	public static void main(String[] args) throws IOException {
		startGame();
	}
	
	public static void startGame() throws IOException {
	    createBoard();
		createEnemies(level);
	    
	    while (!isGameOver){
			

	        System.out.print("\033[H\033[2J");
            System.out.flush();

            if (fire == 0) {
                fire = second/250;
                fire();
            } else 
                fire--;
                
	        bulletAction();
	        printBoard();
	        
	        try {
				Thread.sleep(100);
			} catch (InterruptedException e){
				e.printStackTrace();
			}
			
			if (System.in.available() > 0){
                int c = System.in.read();
                char ch = (char) c;
                
                if (ch == 'q'){
                    break;
                } else if (ch == 'a' || ch == 'd'){
                    move(ch);
                }
			}
			
			while (System.in.available() > 0) {
                System.in.read();
            }
			
			if (level > 3){
				System.out.println("You Win!");
				break;
			} else if (noEnemy()){
				level++;
				createEnemies(level);
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
	            if (board[x][j] == 1) {
	                board[x][j] = 0;
	                board[x][j-1] = 1;
	                y = j - 1;
	                break;
	            }
	            
	        }
	    } else if (ch == 'd') {
	        for (int j = 0; j < column-1; j++){
	            if (board[x][j] == 1) {
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
	    
	    board[x-1][y] = bullet;
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
	        System.out.print(" V");
	    }
	    System.out.println();
	    
	    for (int i = 0; i< row; i++){
	        System.out.print("    | ");
	        for (int j = 0; j < column; j++){
	            if (board[i][j] == 0){
	                System.out.print("- ");
	            } else if (board[i][j] == 1) {
	                System.out.print("A ");
	            } else if (board[i][j] == 2){
	                System.out.print(": ");
	            } else if (board[i][j] == 3){
	                System.out.print("X ");
	            }
	            
	        }
	        System.out.println("|");
	    }

		System.out.println("\n[Score: " + score + "]");
	    
	    
	}
	
}