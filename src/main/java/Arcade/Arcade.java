package Arcade;

import java.io.IOException;

public class Arcade {
	private static int row = 20;
	private static int column = 11;
	private static int[][] board = new int[row][column];
	private static int score = 0;
	private static int x = 15, y = 5;
	private static boolean isGameOver = false;
	private static int fire = 3;
	
	public static void main(String[] args) throws IOException {
		startGame();
	}
	
	public static void startGame() throws IOException {
	    createBoard();
	    
	    while (!isGameOver){
	        System.out.print("\033[H\033[2J");
            System.out.flush();
            if (fire == 0) {
                fire = 3;
                fire();
            } else 
                fire--;
                
	        bulletAction();
	        printBoard();
	        
	        try {
				Thread.sleep(500);
			} catch (InterruptedException e){
				e.printStackTrace();
			}
			
			if (System.in.available() > 0){
                int c = System.in.read();
                char ch = (char) c;
                
                if (ch == 'q')
                    break;
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
	        System.out.print(" X");
	    }
	    System.out.println();
	    
	    for (int i = 0; i< row; i++){
	        System.out.print("    |");
	        for (int j = 0; j < column; j++){
	            if (board[i][j] == 0){
	                System.out.print("- ");
	            } else if (board[i][j] == 1) {
	                System.out.print("A ");
	            } else if (board[i][j] == 2){
	                System.out.print(": ");
	            }
	            
	        }
	        System.out.println("|");
	    }
	    
	    
	}
	
}