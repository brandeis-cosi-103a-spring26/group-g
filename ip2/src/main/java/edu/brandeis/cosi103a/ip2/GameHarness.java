package edu.brandeis.cosi103a.ip2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameHarness {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of players (2-4): ");
        int numPlayers = scanner.nextInt();
        scanner.nextLine();

        List<Player> players = new ArrayList<>();

        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Enter type for Player " + (i + 1) + " (human/ai): ");
            String type = scanner.nextLine().trim().toLowerCase();

            if (type.equals("human")) {
                players.add(new HumanPlayer());
            } 
            else if (type.equals("ai")) {
                // Replace with new AIPlayer() once implemented
                players.add(new HumanPlayer()); 
                System.out.println("AIPlayer not implemented yet. Using HumanPlayer instead.");
            } 
            else {
                System.out.println("Invalid input. Defaulting to HumanPlayer.");
                players.add(new HumanPlayer());
            }
        }

        System.out.println("Starting the game...");

        Game game = new Game();  // Temporary until Engine integration
        game.play();

        scanner.close();
    }
}
