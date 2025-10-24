package Minesweeper;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.IOException;
import Main.*;

public class Minesweeper{
    private static boolean isGameOver = false;
    private static int numberOfDiggedCells = 0;
    private static int numberOfBombs = 0;
    private static int score = 0, time = 0;
    
	
	public static void increaseDiggedCells(){
        numberOfDiggedCells++;
    }

    public static int getCel(){
        return numberOfDiggedCells;
    }


	public static void createBoard(int size) {
		
		ArrayList<Integer> bombedCells = new ArrayList<>();
		double numberOfBombsDouble = size*size/10.0;
		numberOfBombs = (int) Math.round(numberOfBombsDouble);
		
		
		
		for (int i = 1; i <= size*size; i++) {
			Cell newCell = new Cell("#");
			bombedCells.add(i);
		
			Cell.map.put(i, newCell);
		}
		
		Collections.shuffle(bombedCells);
		bombedCells = new ArrayList<>(bombedCells.subList(0,numberOfBombs));
		for (Integer i : bombedCells){
			Cell.map.get(i).setBomb();
		}
		
	}
	
	public static void printBoard(){
		int size = (int) Math.sqrt(Cell.map.size());

        System.out.println();
        System.out.println("--------Board--------");
		System.out.print("  ");
		for (int i = 1; i <= size; i++){
			//System.out.println(board.get(i).getBomb());
			System.out.print(i + "  ");
		}
		
		char letter = 'A';
		System.out.println();
		for (int x = 0; x < size; x++){
			System.out.print(String.valueOf((char) (letter + x)) + "|");
		
			for (int j = 1; j <= size; j++){
				if (Cell.map.get(x*size+j).getFlag() && Cell.map.get(x*size+j).getValue().equals("#")){
				    System.out.print("F  ");
				    continue;
				}
				if (Cell.map.get(x*size+j).getValue().equals("X")){
				    System.out.print("\u001B[31m"+Cell.map.get(x*size+j).getValue()+"\u001B[0m");
				
				} else
				    System.out.print(Cell.map.get(x*size+j).getValue());
				    
				    System.out.print("  ");
			}
			
			System.out.println();
		}

        System.out.println("----------------------");
        System.out.println();
		
		
    }

    public static void startGame() {
        Scanner input = new Scanner(System.in);
        String choice = "";
        int size = 0;

        System.out.println("Welcome to Minesweeper...");
        System.out.println();

        
        while (!(choice.equals("easy") || choice.equals("medium") || choice.equals("hard"))){
            System.out.print("Select Difficulty (Easy, Medium, Hard): ");
            choice = input.nextLine();
            choice = choice.toLowerCase();
        }

        switch (choice){
            case "easy" : size = 4;
            score += 10;
            break;

            case "medium" : size = 6;
            score += 20;
            break;

            case "hard" : size = 8;
            score += 30;
            break;

                
        }

        

        createBoard(size);
        printBoard();
        
        while (!isGameOver) {
            String row = "00";
            String columnString = "A";
            size = (int) (Math.sqrt(Cell.map.size()));
            choice = "A";
           
            while (!(choice.equals("0") || choice.equals("1"))){
                System.out.print("Dig Cell (0) or Flag Cell (1): ");
                choice = input.nextLine();
            }
            
        
            while ((!Character.isLetter(row.charAt(0)) || row.length() != 1) || (row.charAt(0) < 65 || row.charAt(0) >= (65+size))){
                System.out.print("Enter row:");
                row = input.nextLine();
    
            }

            while (!columnString.matches("\\d+") || Integer.parseInt(columnString) > size || Integer.parseInt(columnString) <= 0) {
                System.out.print("Enter column: ");
                columnString = input.nextLine();
            }

            int columnInt = Integer.parseInt(columnString);
            
            if (choice.equals("1")){
                int x = (int) ( row.charAt(0));
                x = x - 65;
                Cell.map.get(x*size + columnInt).changeFlag();
                printBoard();
                continue;
            }
            
            Cell.digCell(row.charAt(0), columnInt);
            printBoard();
            

            if (numberOfDiggedCells == Cell.map.size() - numberOfBombs){
                isGameOver = true;
                System.out.println("----------------------");
                System.out.println("You Won! Congrulations...");
                System.out.println("----------------------");
                
                System.out.println("[ You Got " + score + " Points ]");
            } else if (numberOfDiggedCells >= Cell.map.size() - numberOfBombs){
                isGameOver = true;
                failure();
            }
        }
        
        Stat.updateValue("Minesweeper", Controller.getCurrentPlayerId(), score, time);
    

        
    }

    public static void failure(){
        System.out.println("----------------------");
        System.out.println("Buum...!You Failed!");
        System.out.println("----------------------");
        score = 0;
    }

    public static void openBoard(){
        int size = (int) Math.sqrt(Cell.map.size());
        
        for (int i = 1; i <= Cell.map.size(); i++){
            if (Cell.map.get(i).getValue().equals("#")){
                //char ch = (char) ('A' + i / size - 1);
                if (Cell.map.get(i).getBomb()){
                    Cell.map.get(i).setCellValue("X");
                    continue;
                }

                int x = i % size;
                x = x == 0 ? 4 : x;
                char ch = (char) ('A' + (i - x) / size);
                Cell.digCell(ch, x);
                increaseDiggedCells();
            }
        }
    }

	public static void main(String[] args) throws IOException {
	    
	   

		startGame();
	}
	
}
