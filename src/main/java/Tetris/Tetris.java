package Tetris;

import java.io.IOException;
public class Tetris {
     public static int[][] block2 = {{1,1},{1,1}};
     public static int[][] block = {{1,0},{1,0},{1,1}};
     public static int[][] block3 = {{0,1},{0,1},{1,1}};

	private static int[][] board = new int[20][10];
	private static int row = 20;
	private static int column = 10;
	
    public static void main(String args[]) throws IOException{
		createBoard();
		putBoard();
		moveBlock('d');
		moveBlock('d');
		// downBlock();
		// downBlock();
		moveBlock('d');
		moveBlock('d');
		moveBlock('d');
		printBoard();
		
		while (!checkCollision()){
			
			

			System.out.print("\033[H\033[2J");
            System.out.flush();
			
			downBlock();

			if (System.in.available() > 0){
			    int c = System.in.read();
			    char ch = (char) c;
			    
			    if (ch == 'a' || ch =='d'){
			        moveBlock(ch);
			    } else if (ch == 'q'){
					break;
				}

			}
			System.out.print("\033[H\033[2J");
            System.out.flush();

			printBoard();

			
			try {
				Thread.sleep(300);
			} catch (InterruptedException e){
				e.printStackTrace();
			}
			if (checkCollision())
			    add2Board();
			
			
			
		}
		
		
	

        
    }
	
	private static void moveBlock(char ch) {

		if (ch == 'd'){
			for (int i = 0; i < board.length; i++) {
				for (int j = board[0].length-1; j >= 0 ; j--) {
					if (board[i][j] == 1 && j < board[0].length-1){
						board[i][j] = 0;
						board[i][j+1] = 1;
					}
				}
			}
		} else if (ch == 'a'){

		}


	}

	public static void createBoard(){
		
	
		for (int i = 0; i < row; i++){
			for (int j = 0; j < column; j++){
				board[i][j] = 0;
			}
		}
	}
	
	
	
	public static void printBoard(){
		
		for (int i = 0; i < row; i++){
			
			System.out.print("| ");
			for (int j = 0; j < column; j++){
				if (board[i][j] == 0){
					System.out.print("- ");
				} else 
					System.out.print("# ");
			}
			System.out.println("|");
			
		}
		
		System.out.print("  ");
		for (int i = 0; i<column; i++){
			System.out.print("X ");
		}
		
		System.out.println();
	}
	
	
	public static void putBoard(){

		for (int i = 0; i<block.length; i++){
			for (int j = 0; j < block[0].length; j++){
				board[i][j] = block[i][j];
			}
		}
		
		
	}
	
	public static void downBlock(){
		
		for (int i = row-2; i>=0; i--){
			for (int j = 0; j < column; j++){
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
