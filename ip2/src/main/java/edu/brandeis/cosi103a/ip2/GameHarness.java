package edu.brandeis.cosi103a.ip2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class GameHarness {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Prompt for number of players
        System.out.print("Enter the number of players (2-4): ");
        int numPlayers = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // List to hold player instances
        List<Player> players = new ArrayList<>();

        // Create players based on user input
        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Enter type for player " + (i + 1) + " (human/AI): ");
            String playerType = scanner.nextLine().trim().toLowerCase();
            if (playerType.equals("human")) {
                players.add(new Player("Player " + (i + 1)));
            } else if (playerType.equals("ai")) {
                players.add(new Player("Player " + (i + 1)));
            } else {
                System.out.println("Invalid player type. Defaulting to human.");
                players.add(new Player("Player " + (i + 1)));
            }
        }

        // Instantiate the Engine
        Game game = new Game();

        // Run the game
        System.out.println("Starting the game...");
        game.play();

        // Print game events and final results
        System.out.println("Game Over! Final Results:");
        game.play();

        scanner.close();
    }
}