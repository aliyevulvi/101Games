package Tetris;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class Tetris {
     public static int[][] ablock = {{1,1},{1,1}};
     public static int[][] sqBlock = {{1,1},{1,1}};
     public static int[][] lBlock = {{1,3},{1,3},{1,1}};
     public static int[][] jBLock = {{3,1},{3,1},{1,1}};
     public static int[][] iBlock = {{1},{1},{1}};
     public static int[][] zBlock = {{1,1,3},{3,1,1}};
     public static int[][] tBlock = {{3,1,3},{1,1,1}};
     public static int[][] sBlock = {{3,1,1},{1,1,3}};
     private static int score = 0;

	public static ArrayList<int[][]> blockList = new ArrayList<>();
	
	private static int row = 25;
	private static int column = 10;
    private static int[][] board = new int[row][column];

	
    public static void main(String args[]) throws IOException{
		blockList.addAll(Arrays.asList(sqBlock,lBlock,jBLock,iBlock,zBlock, tBlock,sBlock));
		
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
		
		outer:
		while (true){
	
			
							
			noCollision = false;
			putBoard();
				while (!noCollision){
			 System.out.print("\033[H\033[2J");
            System.out.flush();
			downBlock();

			

        if (System.in.available() > 0){
    int c = System.in.read();
    char ch = (char) c;

    while (System.in.available() > 0) {
        System.in.read();
    }

    if (ch == 'a' || ch =='d'){
        moveBlock(ch);
    } else if (ch == 'z'){
        return;
    } else if (ch == 'e' || ch == 'q' ){
        rotateBlock(ch);
    }
}
		
			System.out.print("\033[H\033[2J");
            System.out.flush();
     
			printBoard();

			
			try {
				Thread.sleep(400);
			} catch (InterruptedException e){
				e.printStackTrace();
			}

			noCollision = checkCollision();
			if (noCollision){
			    add2Board();
			    checkRow();
			    
			}
			
			if (isGameOver()){
			    failureMessage();
			    break outer;
			}
			
		}
		}
	
		
		
		
	

        
    }
    
    private static void failureMessage(){
        System.out.println();
        System.out.println("-----------");
        System.out.println("Game Over!");
        System.out.println("Final Score: " + score);
        System.out.println("-----------");
    }
    
    private static boolean isGameOver(){
        for (int i = 0; i < column; i++){
            if (board[7][i] == 2)
                return true;
        }
        
        return false;
    }
    
    private static void rotateBlock(char ch){
        int centerPtX = -1, centerPtY = -1;
        ArrayList<Integer> values = new ArrayList<>();
        int newColumn = 1, newRow = 1;
        
        if ( ch == 'e'){
        
        // CENTER PTS / NEW ROW & COLUMN /
        for (int j = 0; j < column; j++){
            for (int i = row - 1; i >= 0; i--){
                if (board[i][j] == 1 || board[i][j] == 3){
                    if (centerPtX == -1){
                        centerPtX = i;
                        centerPtY = j;
                    }
                    
                    if ( j != centerPtY && i == centerPtX)
                        newRow++;
                    
                    values.add(board[i][j]);
                }
            }
        }
        
        
        newColumn = values.size() / newRow;
        int[][] rotatedBlock = new int[newRow][newColumn];
        
        for (int i = 0; i < newRow; i++){
            for (int j = 0; j< newColumn; j++){
                rotatedBlock[i][j] = values.get(i*newColumn + j);
            }
        }
        
        
        
        if (centerPtX - newRow + 1 >= 0 && centerPtY + newColumn - 1 < column){
            for (int i = 0; i < row; i++){
                for (int j =0; j < column; j++){
                    if (board[i][j] == 1 || board[i][j] == 3)
                        board[i][j] = 0;
                }
            }
            
            
            for (int i = centerPtX - newRow + 1; i <= centerPtX; i++){
                for (int j =centerPtY; j < centerPtY + newColumn; j++){
                  board[i][j] = rotatedBlock[i - (centerPtX - newRow + 1)][j - centerPtY];
                }
            }
            
            
        }
        
    }
        
        if (ch == 'q') {
            
            for (int j = 0; j < column; j++){
            for (int i = row-1; i >= 0; i--){
                if (board[i][j] == 1 || board[i][j] == 3){
                    if (centerPtX == -1){
                        centerPtX = i;
                        centerPtY = j;
                    }
                    
                    if ( j != centerPtY && i == centerPtX)
                        newRow++;
                    
                    values.add(board[i][j]);
                }
            }
        }
                Collections.reverse(values);
                
                newColumn = values.size() / newRow;
                int[][] rotatedBlock = new int[newRow][newColumn];
                
                for (int i = 0; i < newRow; i++){
                    for (int j = 0; j < newColumn; j++){
                        rotatedBlock[i][j] = values.get(i*newColumn + j);
                    }
                }
                centerPtY++;
                if (centerPtX - newRow + 1 >= 0 && centerPtY - newColumn + 1 >= 0){
            for (int i = 0; i < row; i++){
                for (int j =0; j < column; j++){
                    if (board[i][j] == 1 || board[i][j] == 3)
                        board[i][j] = 0;
                }
            }
            
            
            for (int i = centerPtX ; i > centerPtX - newRow; i--){
                for (int j =centerPtY; j > centerPtY - newColumn; j--){
                  board[i][j] = rotatedBlock[i - centerPtX + newRow - 1][j - (centerPtY - newColumn + 1)];
                }
                
            }
            
            
        }

            }
        
    }
    
    private static void clearRow(int i){
        // CLEARS SPECIFIC ROW
        for (int x = 0; x < column; x++){
            board[i][x] = 0;
        }
        
        // UPDATES BOARD
        if (i != 0){
        for (int a = i - 1; a >= 0; a--){
            for (int b = 0; b < column; b++){
                board[a+1][b] = board[a][b];
                board[a][b] = 0;
            }
        }
        
        }
        
        
    }
    
    private static void checkRow(){
        int multiplier = 0;
        int newScore = 10;
        
        for (int i = row-1; i >= 0; i--){
             boolean rowIsFull = false;
             for (int j = 0; j < column; j++){
                    if (board[i][j] != 2){
                        break;
                    }
                    
                    if (j == column  - 1){
                        clearRow(i);
                        multiplier++;
                        score += multiplier*newScore;
                        i = row;
                    }
              }   
        }
        
      
    }
	
	private static void moveBlock(char ch) {
	    
	    boolean available = true;

		if (ch == 'd'){
			outer:
			for (int j = board[0].length-2; j >= 0 ; j--) {
				for (int i = 0; i < board.length; i++) {
					if (board[i][j] == 1 && board[i][j+1] == 0) {
					    for (int x = 0; x < row; x++){
					        if (board[x][j] == 1 && (board[x][j+1] == 2 || board[x][j+1] == 1))
					            break outer;
					    }
					    
					    board[i][j+1] = board[i][j];
					    board[i][j] = 0;        
					    
					} else if (board[i][j] == 3 && (board[i][j+1] == 0 || board[i][j+1] == 2)){
					    for (int x = 0; x < row; x++){
					        if (board[x][j] == 1 && (board[x][j+1] == 2 || board[x][j+1] == 1))
					            break outer;
					    }
					    if (board[i][j+1] == 0)
					        board[i][j+1] = board[i][j];
					    board[i][j] = 0;
					}

				}
			}
		} else if (ch == 'a'){
		          
		        
		    outer:
			for (int j = 1; j < board[0].length ; j++) {
				for (int i = 0; i < board.length; i++) {
					if (board[i][j] == 1 && board[i][j-1] == 0) {
					    for (int x = 0; x < row; x++){
					        if (board[x][j] == 1 && (board[x][j-1] == 2 || board[x][j-1] == 1))
					            break outer;
					    }
					    
					    board[i][j-1] = board[i][j];
					    board[i][j] = 0;        
					    
					} else if (board[i][j] == 3 && (board[i][j-1] == 0 || board[i][j-1] == 2)){
					    for (int x = 0; x < row; x++){
					        if (board[x][j] == 1 && (board[x][j-1] == 2 || board[x][j-1] == 1))
					            break outer;
					    }
					    if (board[i][j-1] == 0)
					        board[i][j-1] = board[i][j];
					    board[i][j] = 0;
					}

				}
			}
            //for (int i = 0; i < board.length; i++){
//                for (int j = 1; j < board[0].length; j++){
//                    
//                    
//                    
//                    //if ((board[i][j] == 1 || board[i][j] == 3) && board[i][j-1] == 0) {
//                        board[i][j-1] = board[i][j];
//                        board[i][j] = 0;
//                    }
//                }
//            }
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
		
        System.out.println();
        		
		for (int i = 0; i < row; i++){
			
			System.out.print("| ");
			for (int j = 0; j < column; j++){
				if (board[i][j] == 1){
					System.out.print("#" + " ");
				} else if (board[i][j] == 3){
					System.out.print(" " + " ");
				} else if (board[i][j] == 2){
					System.out.print("#" + " ");
				} else
					System.out.print("- ");
			}
			System.out.println("|");
			
		}
		
		System.out.print("  ");
		for (int i = 0; i<column; i++){
			System.out.print("X ");
		}
		
		System.out.println("\n");
		System.out.println("Score: " + score);
	}
	
	
	public static void putBoard(){

	// 	 public static int[][] sqBlock = {{1,1},{1,1}};
    //  public static int[][] lBlock = {{1,3},{1,3},{1,1}};
    //  public static int[][] jBLock = {{3,1},{3,1},{1,1}};
    //  public static int[][] iBlock = {{3,1},{3,1},{3,1}};
    //  public static int[][] zBlock = {{1,1,3},{3,1,1}};
    //  public static int[][] tBlock = {{3,1,3},{1,1,1}};
    //  public static int[][] sBlock = {{3,1,1},{1,1,3}};
        	Collections.shuffle(blockList);
		int[][] block =  blockList.get(0);
		for (int i = 0; i<block.length; i++){
			for (int j = 0; j < block[0].length; j++){
				board[i+1][j+3] = block[i][j];
			}
		}
		
		
	}
	
	public static void downBlock(){
		
		outer:
		for (int i = row-2; i>=0; i--){
			for (int j = 0; j < column; j++){
				
				if (board[i][j] == 1 && board[i+1][j] == 0){
				    for (int x = 0; x < column; x++){
				        if (board[i][x] == 1 && (board[i+1][x] == 1 || board[i+1][x] == 2 || board[i+1][x] == 3)){
				            break outer;
				        } else if (board[i][x] == 3 && (board[i+1][x] == 3  || board[i+1][x] == 1)){
				            break outer;
				        }
				    }
				    
				    board[i+1][j] = board[i][j];
				    board[i][j] = 0;
				} else if (board[i][j] == 3) {
				    if (board[i+1][j] == 0){
				        for (int x = 0; x < column; x++){
				        if (board[i][x] == 1 && (board[i+1][x] == 1 || board[i+1][x] == 2 || board[i+1][x] == 3)){
				            break outer;
				        } else if (board[i][x] == 3 && (board[i+1][x] == 3  || board[i+1][x] == 1)){
				            break outer;
				        }
				    }
				    
				    board[i+1][j] = board[i][j];
				    board[i][j] = 0;
				    } else if (board[i+1][j] == 2) {
				        for (int x = 0; x < column; x++){
				        if (board[i][x] == 1 && (board[i+1][x] == 1 || board[i+1][x] == 2 || board[i+1][x] == 3)){
				            break outer;
				        } else if (board[i][x] == 3 && (board[i+1][x] == 3  || board[i+1][x] == 1)){
				            break outer;
				        }
				    }
				    
				    board[i][j] = 0;
				    }
				    
				}
				
				//if (board[i][j] == 1 || board[i][j] == 3){
//					if (board[i][j] == 3 && board[i+1][j] == 2){
//					    board[i][j] = 0;
//					} else {
//					board[i+1][j] = board[i][j];
//					board[i][j]=0;
//					
//					}
//				}
				
				
			}
		}
	}
	
	public static boolean checkCollision(){
	    
	    for (int i = 0; i < row; i++){
			for (int j = 0; j < column; j++){
				if (i == row-1 && board[i][j] == 1){
					return true;
				} else if (i < row-1 && board[i+1][j] == 2 && (board[i][j] == 1)){
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
