package Tetris;

import java.io.IOException;
public class Tetris {
     public static int[][] block = {{1,1},{1,1}};
	private static int[][] board = new int[20][10];
	private static int row = 20;
	private static int column = 10;
	
    public static void main(String args[]) throws IOException{
		createBoard();
		putBoard();
		
		int i = 0;
		while (!checkCollision()){
			
			if (System.in.available() > 0){
			    int c = System.in.read();
			    char ch = (char) c;
			    
			    if (ch == 'q'){
			        System.out.println("Exit");
			        break;
			    }
			}
			
			System.out.print("\033[H\033[2J");
            System.out.flush();
			
			downBlock();
			printBoard();
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e){
				e.printStackTrace();
			}
			if (checkCollision())
			    add2Board();
		}
		
		
	

        
    }
	
	public static void createBoard(){
		
	
		for (int i = 0; i < 20; i++){
			for (int j = 0; j < 10; j++){
				board[i][j] = 0;
			}
		}
	}
	
	
	
	public static void printBoard(){
		
		for (int i = 0; i < 20; i++){
			
			System.out.print("| ");
			for (int j = 0; j < 10; j++){
				if (board[i][j] == 0){
					System.out.print("- ");
				} else 
					System.out.print("# ");
			}
			System.out.println("|");
			
		}
		
		System.out.print("  ");
		for (int i = 0; i<10; i++){
			System.out.print("X ");
		}
		
		System.out.println();
	}
	
	
	public static void putBoard(){
		
		for (int i = 0; i<block[0].length; i++){
			for (int j = 3; j < block[0].length+3; j++){
				board[i][j] = block[i][j-3];
				//System.out.print(board[i][j]);
			}
		}
		
		
	}
	
	public static void downBlock(){
		
		for (int i = 18; i>=0; i--){
			for (int j = 0; j < 10; j++){
				if (board[i][j] == 1){
					board[i][j]=0;
					board[i+1][j]=1;
				}
			}
		}
	}
	
	public static boolean checkCollision(){
	    
	    for (int i = row-1; i >= 0; i--){
			for (int j = 0; j < column; j++){
				if (i == row-1 && board[i][j] == 1){
					return true;
				} else if (i < row-1 && board[i+1][j] == 2){
				    return true;
				}
				
				
			}
		}
		
		return false;
	}
	
	public static void add2Board(){
	    for (int i = 0; i < row; i++){
			for (int j = 0; j < column; j++){
				if (board[i][j] == 1){
					board[i][j]=2;
				}
			}
		}
	}
	
	
}
