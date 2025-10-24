package Main;

import java.util.Scanner;
import java.io.IOException;

public class UserInterface {
    public static void main(String[] args) {}

    public static void LoginInterface() {
        boolean bool = false;
        while (!bool) {
            try {
                Scanner input = new Scanner(System.in);
                System.out.print("\n[ Sign In (0) ][ Sign Up (1) ] : ");
                int x = input.nextInt();

                if (!((x == 0) || (x == 1))) {
                    System.out.println("[ Please Enter Only `0` or `1` ]\n");
                } else {
                    switch (x) {
                        case 0:
                            bool = Login.signIn();
                            break;
                        case 1:
                            bool = Login.signUp();
                            break;
                    }
                }

            } catch (Exception e) {
                System.out.println("[ Please Enter Only `0` or `1` ]\n");
                System.out.println(e.toString());
            }
        }
    }

    public static void MenuInterface() throws IOException {
        int selection = -1;

        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("[ -----Menu----- ]");
        System.out.println("[ Minesweeper (1) ]");
        System.out.println("[ Tetris (2) ]");
        System.out.println("[ Arcade (3) ]");
        System.out.println("[ Rank (4) ]");
        System.out.println("[ Notifications (5) ]");
        System.out.println("[ Friends (6) ]");
        System.out.println("[ Settings (7) ]");
        System.out.println("[ Exit (0) ]");
        System.out.println("[---------------]");

        while (true) {
            try {
                Scanner input = new Scanner(System.in);
                System.out.println("\n[ Please Select Option ] : ");
                selection = input.nextInt();

                if ((selection - 10) >= 0 || selection != 111) {
                    System.out.println("[ Please Enter 0-9 ]");
                } else {
                    break;
                }

            } catch (Exception e) {
                
            }
        }

        switch (selection){
            case 1: Minesweeper.Minesweeper.startGame();
            break;
            case 2: Tetris.Tetris.main(new String[0]);
            break;
            case 3: Arcade.Arcade.startGame();
            break;
            case 4: System.out.println(" Rank ");
            break;
            case 5: System.out.println(" Notifications ");
            break;
            case 6: System.out.println(" Friends ");
            break;
            case 7: System.out.println(" Settings ");
            break;
            case 0: System.out.println(" Exit "); System.exit(0);
            break;
            case 111: System.out.println(" Admin Panel ");
            break;
        }
        
        MenuInterface();
    }
}