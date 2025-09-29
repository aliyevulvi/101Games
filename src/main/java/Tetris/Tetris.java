package Tetris;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class Tetris {
     //public static int[][] block = {{1,1},{1,1}};
     public static int[][] sqBlock = {{1,1},{1,1}};
     public static int[][] lBlock = {{1,3},{1,3},{1,1}};
     public static int[][] jBLock = {{3,1},{3,1},{1,1}};
     public static int[][] iBlock = {{3,1},{3,1},{3,1}};
     public static int[][] zBlock = {{1,1,3},{3,1,1}};
     public static int[][] tBlock = {{3,1,3},{1,1,1}};
     public static int[][] sBlock = {{3,1,1},{1,1,3}};

	public static ArrayList<int[][]> blockList = new ArrayList<>();
	


	private static int[][] board = new int[20][10];
	private static int row = 20;
	private static int column = 10;
	
    public static void main(String args[]) throws IOException{
		blockList.addAll(Arrays.asList(sqBlock,lBlock,jBLock,iBlock,zBlock, tBlock,sBlock));
		Collections.shuffle(blockList);
		createBoard();
		// putBoard();
		moveBlock('d');
		downBlock();
		downBlock();
		downBlock();
		downBlock();
		downBlock();
		downBlock();
		
		printBoard();
		boolean noCollision = false;
		while (true){
			System.out.println("begin");
			noCollision = false;
			putBoard();
				while (!noCollision){
			// System.out.print("\033[H\033[2J");
            // System.out.flush();
			
			System.out.println("cau");
			downBlock();

			if (System.in.available() > 0){
			    int c = System.in.read();
			    char ch = (char) c;
			    
			    if (ch == 'a' || ch =='d'){
			        moveBlock(ch);
			    } else if (ch == 'q'){
					return;
				}

			}
			// System.out.print("\033[H\033[2J");
            // System.out.flush();

			printBoard();

			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e){
				e.printStackTrace();
			}

			noCollision = checkCollision();
			if (noCollision)
			    add2Board();
					
			
		}
		}
	
		
		
		
	

        
    }
	
	private static void moveBlock(char ch) {

		if (ch == 'd'){
			for (int i = 0; i < board.length; i++) {
				for (int j = board[0].length-1; j >= 0 ; j--) {
					if ((board[i][j] == 1 || board[i][j] == 3) && j < board[0].length-1){
						if ((board[i][j] == 1 && board[i][j+1] != 3 && board[i][j+1] != 1) || (board[i][j] == 3 && board[i][j+1] == 0)){
							board[i][j+1] = board[i][j];
							board[i][j] = 0;
						}

						
						
					}
				}
			}
		} else if (ch == 'a'){

		}


	}

	public static void createBoard(){
		
	
		for (int i = 0; i < row; i++){
			for (int j = 0; j < board[i][j]; j++){
				board[i][j] = 0;
			}
		}
	}
	
	
	
	public static void printBoard(){
		
		for (int i = 0; i < row; i++){
			
			System.out.print("| ");
			for (int j = 0; j < column; j++){
				if (board[i][j] == 1){
					System.out.print(board[i][j] + " ");
				} else if (board[i][j] == 3){
					System.out.print(board[i][j] + " ");
				} else if (board[i][j] == 2){
					System.out.print(board[i][j] + " ");
				} else
					System.out.print("- ");
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

	// 	 public static int[][] sqBlock = {{1,1},{1,1}};
    //  public static int[][] lBlock = {{1,3},{1,3},{1,1}};
    //  public static int[][] jBLock = {{3,1},{3,1},{1,1}};
    //  public static int[][] iBlock = {{3,1},{3,1},{3,1}};
    //  public static int[][] zBlock = {{1,1,3},{3,1,1}};
    //  public static int[][] tBlock = {{3,1,3},{1,1,1}};
    //  public static int[][] sBlock = {{3,1,1},{1,1,3}};

		int[][] block = sBlock;
		for (int i = 0; i<block.length; i++){
			for (int j = 0; j < block[0].length; j++){
				board[i][j] = block[i][j];
			}
		}
		
		
	}
	
	public static void downBlock(){
		
		for (int i = row-2; i>=0; i--){
			for (int j = 0; j < column; j++){
				if (board[i][j] == 1 || board[i][j] == 3){
					board[i+1][j] = board[i][j];
					board[i][j]=0;
				}
			}
		}
	}
	
	public static boolean checkCollision(){
	    
	    for (int i = 0; i < row; i++){
			for (int j = 0; j < column; j++){
				if (i == row-1 && board[i][j] == 1){
					System.out.println("v1"); //sil
					return true;
				} else if (i < row-1 && board[i+1][j] == 2 && (board[i][j] == 1)){
					System.out.println("v2"); //sil
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
				} else if (board[i][j] == 3) {
					board[i][j]=0;
				}
			}
		}
	}
	
	
}
