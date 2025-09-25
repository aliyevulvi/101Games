import java.util.HashMap;

public class  Cell{
	
	public static HashMap<Integer, Cell> map = new HashMap<>(); 
	private boolean thereIsBomb = false;
	private String value;
	
	
	public Cell(String value) {
		this.value = value;
	}
	
	public void setCellValue(String value){
		this.value = value;
	}
	
	public void setBomb() {
		this.thereIsBomb = true;
	}
	
	public boolean getBomb(){
		return thereIsBomb;
	}
	
	public String getValue(){
		return value;
	}
	
	public static void digCell(char ch, int j){
		int numberOfBombsAround = 0;
		int i = (int) ch;
		i = i - 65;
		int size = (int) Math.sqrt(Cell.map.size());

        if (!Cell.map.get(i*size+j).getValue().equals("#"))
            return;

        
	
		if (Cell.map.get(i*size+j).getBomb()){
            Cell.map.get(i*size+j).setCellValue("X");
            Minesweeper.openBoard();
			return;} 
		else {
			if ((j+1) <= size && Cell.map.get(i*size+j+1).thereIsBomb) //right
				numberOfBombsAround++;
			if ((i+1) < size && (j+1) <= size && Cell.map.get((i+1)*size+j+1).thereIsBomb) //right down
				numberOfBombsAround++;
			if ((i+1) < size && Cell.map.get((i+1)*size+j).thereIsBomb) //bottom
				numberOfBombsAround++;
			if ((i+1) < size && (j-1) > 0 && Cell.map.get((i+1)*size+j-1).thereIsBomb) //left down
				numberOfBombsAround++;
			if ((j-1) > 0 && Cell.map.get(i*size+j-1).thereIsBomb) //left
				numberOfBombsAround++;
			if ((i-1) >= 0 && (j-1) > 0 && Cell.map.get((i-1)*size+j-1).thereIsBomb) //left up
				numberOfBombsAround++;
			if ((i-1) >= 0 && Cell.map.get((i-1)*size+j).thereIsBomb) //top
				numberOfBombsAround++;
			if ((i-1) >= 0 && (j+1) <= size && Cell.map.get((i-1)*size+j+1).thereIsBomb) //right up
				numberOfBombsAround++;
				
			if (numberOfBombsAround == 0){
				Cell.map.get(i*size+j).setCellValue(" ");
               Minesweeper.increaseDiggedCells();
				
				if ((j+1) <= size && Cell.map.get(i*size+j+1).getValue().equals("#"))
					digCell(ch,j+1);
				if ((i+1) < size && (j+1) <= size && Cell.map.get((i+1)*size+j+1).getValue().equals("#"))
					digCell((char) (ch + 1), j+1);
				if ((i+1) < size && Cell.map.get((i+1)*size+j).getValue().equals("#"))
					digCell((char) (ch + 1), j);
				if ((i+1) < size && (j-1) > 0 && Cell.map.get((i+1)*size+j-1).getValue().equals("#"))
					digCell((char) (ch + 1), j-1);
				if ((j-1) > 0 && Cell.map.get(i*size+j-1).getValue().equals("#"))
					digCell(ch,j-1);
				if ((i-1) >= 0 && (j-1) > 0 && Cell.map.get((i-1)*size+j-1).getValue().equals("#"))
					digCell((char) (ch - 1), j-1);
				if ((i-1) >= 0 && Cell.map.get((i-1)*size+j).getValue().equals("#"))
					digCell((char) (ch - 1), j);
				if ((i-1) >= 0 && (j+1) <= size && Cell.map.get((i-1)*size+j+1).getValue().equals("#"))
					digCell((char) (ch - 1), j+1);
			} else {
				Cell.map.get(i*size+j).setCellValue("" + numberOfBombsAround);
                Minesweeper.increaseDiggedCells();
                
        	}
				
			
		}

        
			
	}
	
	
	
	
	
	
}
