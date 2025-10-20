package Tetris;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.time.LocalTime;
import java.time.Duration;


public class Tetris {
     public static int[][] ablock = {{1,1},{1,1}};
     public static int[][] sqBlock = {{1,1},{1,1}};
     public static int[][] lBlock = {{1,3},{1,3},{1,1}};
     public static int[][] jBLock = {{3,1},{3,1},{1,1}};
     public static int[][] iBlock = {{1},{1},{1}};
     public static int[][] zBlock = {{1,1,3},{3,1,1}};
     public static int[][] tBlock = {{3,1,3},{1,1,1}};
     public static int[][] sBlock = {{3,1,1},{1,1,3}};
     private static int score = 0, speed = 300;

	public static ArrayList<int[][]> blockList = new ArrayList<>();
	private static ArrayList<int[][]> nextBlocks = new ArrayList<>();
	
	private static int maxHeight = 0, totalLength = 0;
	private static int[][] nextBlocksTable = new int[0][0];
	private static long minutes = 0, seconds = 0;
	
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
		
		//printBoard();
		boolean noCollision = false;
	    LocalTime start = LocalTime.now();	
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
    } else if (ch == 'f'){
        pause();
    }
    }
    
		
			System.out.print("\033[H\033[2J");
            System.out.flush();
     
			printBoard();

			
			try {
				Thread.sleep(speed);
			} catch (InterruptedException e){
				e.printStackTrace();
			}

			noCollision = checkCollision();
			if (noCollision){
			    add2Board();
			    checkRow();
			    speed--;
			    
			}
			
			if (isGameOver()){
			    failureMessage();
			    break outer;
			}
			
			LocalTime end = LocalTime.now();
		    Duration dur = Duration.between(start,end);
		    long totalSeconds = dur.getSeconds();
		    minutes = totalSeconds / 60;
		    seconds = totalSeconds % 60;
			
		}
		
		
		
		}
	
		
		
		
	

        
    }
    
    private static int height(){
        for (int i = 0; i < row; i++){
            for (int j = 0; j < column; j++){
                if (board[i][j] == 2)
                    return 25-i;
            }
        }
        
        return 0;
    }
    
    private static void pause() throws IOException{
        while (true){
            
            printBoard();
            System.out.println();
            System.out.println("--------------------");
	        System.out.println("Press \"F\" For Resume");
	        System.out.println("--------------------");
	        
            if (System.in.available() > 0){
                int x = System.in.read();
                char c = (char) x;
            
                if (c == 'f')
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
					System.out.print("  ");
				
				
			
			}
			System.out.print("|");
			
			if ( i == row - 11){
				    System.out.print("--");
				    for (int a = 0; a < nextBlocksTable[0].length; a++){
				            System.out.print("--");
				    }
				    System.out.print("|");
			}
			
			
			if ( i == row - 10){
			    String timeStr = "  Timer: " + minutes + ":" + seconds;
			    System.out.print(timeStr);
			    int k = 2*nextBlocksTable[0].length - timeStr.length() + 2;
			    for (int p = 0; p < k; p++)
			        System.out.print(" ");
			    System.out.print("|");
			}
			
			if ( i == row - 9){
				    System.out.print("--");
				    for (int a = 0; a < nextBlocksTable[0].length; a++){
				            System.out.print("--");
				    }
				    System.out.print("|");
			}
			
			if ( i == row - 8){
			    String heightStr = "  Height: " + height();
			    System.out.print(heightStr);
			    int k = 2*nextBlocksTable[0].length - heightStr.length() + 2;
			    for (int p = 0; p < k; p++)
			        System.out.print(" ");
			    System.out.print("|");
			}
			
			if ( i == row - 7){
				    System.out.print("--");
				    for (int a = 0; a < nextBlocksTable[0].length; a++){
				            System.out.print("--");
				    }
				    System.out.print("|");
			}
			
			if ( i == row - 6){
			    String scoreStr = "  Score: " + score;
			    System.out.print(scoreStr);
			    
			    
			    int k = 2*nextBlocksTable[0].length - scoreStr.length() + 2;
			    for (int p = 0; p < k; p++)
			        System.out.print(" ");
			    System.out.print("|");
			}
			
			if ( i == row - 5){
				    System.out.print("--");
				    for (int a = 0; a < nextBlocksTable[0].length; a++){
				            System.out.print("--");
				    }
				    System.out.print("|");
			}
		
			if ( i == row - 4){
				    System.out.print("  ");
				    for (int a = 0; a < nextBlocksTable[0].length; a++){
				        if (nextBlocksTable[0][a] == 1)
				            System.out.print("# ");
				        else
				            System.out.print("  ");
				    }
				    System.out.print("|");
				}
				
				if ( i == row - 3){
				    System.out.print("  ");
				    for (int a = 0; a < nextBlocksTable[1].length; a++){
				        if (nextBlocksTable[1][a] == 1)
				            System.out.print("# ");
				        else
				            System.out.print("  ");
				    }
				    System.out.print("|");
				}
				
				if ( i == row - 2){
				    System.out.print("  ");
				    for (int a = 0; a < nextBlocksTable[2].length; a++){
				        if (nextBlocksTable[2][a] == 1)
				            System.out.print("# ");
				        else
				            System.out.print("  ");
				    }
				    System.out.print("|");
				}	
				
				if ( i == row - 1){
				    System.out.print("--");
				    for (int a = 0; a < nextBlocksTable[0].length; a++){
				            System.out.print("--");
				    }
				    System.out.print("|");
				}
													
			System.out.println();
			
		}
		
		System.out.print("  ");
		for (int i = 0; i<column; i++){
			System.out.print("X ");
		}
		
		
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
        
        if (nextBlocks.size() == 0){
            nextBlocks.add(blockList.get(0));
            nextBlocks.add(blockList.get(1));
            nextBlocks.add(blockList.get(2));
        }
            
		int[][] block =  nextBlocks.get(0);
		nextBlocks.remove(0); 
		nextBlocks.trimToSize();
		nextBlocks.add(blockList.get(0));
		
		for (int i = 0; i<block.length; i++){
			for (int j = 0; j < block[0].length; j++){
				board[i+1][j+3] = block[i][j];
			}
		}
		
        totalLength = 0;
        maxHeight = 0;
		for (int[][] arr : nextBlocks){
		    if (arr.length > maxHeight)
		        maxHeight = arr.length;
		        
		    totalLength += arr[0].length;
		    System.out.println(arr[0].length);
		}
		
		System.out.println("olcu " + nextBlocks.size());
		nextBlocksTable = new int[3][totalLength + 2];
		
		for (int i = 0; i < 3; i++){
	        for (int j = 0; j < totalLength+2; j++){
	            nextBlocksTable[i][j] = 0;
	        }
	    }
	
		int counter = 0;
		int a = 2, b = 0, temp = 0;;
		for (int[][] arr : nextBlocks){
		    for (int i = arr.length - 1; i >= 0; i--){
		        for (int j = 0; j < arr[0].length; j++){
		            nextBlocksTable[a][b] = arr[i][j];
		            System.out.println("a " + a + " b " + b);
		            b++;
		        }
		        a--;
		        b = temp;
		    }
		    a = 2;
		    temp += arr[0].length + 1;
		    b = temp;
		    System.out.println("bir arr getdi");
		 
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
