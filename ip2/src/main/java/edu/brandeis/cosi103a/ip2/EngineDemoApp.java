package edu.brandeis.cosi103a.ip2;

import java.util.Arrays;
import java.util.List;

/**
 * Demo application showing how to use the Engine to run a game.
 */
public class EngineDemoApp {
    
    public static void main(String[] args) {
        System.out.println("=== Automation: The Game - Engine Demo ===\n");
        
        // Create players
        List<Player> players = Arrays.asList(
            new SimplePlayer("Alice"),
            new SimplePlayer("Bob"),
            new SimplePlayer("Charlie")
        );
        
        System.out.println("Players:");
        for (Player player : players) {
            System.out.println("  - " + player.getName());
        }
        System.out.println();
        
        // Create engine
        Engine engine = new EngineImpl(players);
        
        System.out.println("Starting game...\n");
        
        try {
            // Play the game
            GameResult result = engine.play();
            
            // Display results
            System.out.println("=== GAME OVER ===\n");
            System.out.println("Final Results:");
            System.out.println("---------------");
            
            for (int i = 0; i < result.getPlayerResults().size(); i++) {
                PlayerResult pr = result.getPlayerResults().get(i);
                System.out.printf("%d. %s%n", i + 1, pr.getName());
                System.out.printf("   Score: %d points%n", pr.getScore());
                System.out.printf("   Cards: %d total%n", pr.getEndingDeck().size());
                
                // Count card types in ending deck
                int bitcoins = 0, ethereums = 0, dogecoins = 0;
                int methods = 0, modules = 0, frameworks = 0;
                int bugs = 0, actions = 0;
                
                for (CardDefinition card : pr.getEndingDeck()) {
                    switch (card.getName()) {
                        case "Bitcoin": bitcoins++; break;
                        case "Ethereum": ethereums++; break;
                        case "Dogecoin": dogecoins++; break;
                        case "Method": methods++; break;
                        case "Module": modules++; break;
                        case "Framework": frameworks++; break;
                        case "Bug": bugs++; break;
                        default: actions++; break;
                    }
                }
                
                System.out.print("   Deck: ");
                if (frameworks > 0) System.out.printf("%d Framework, ", frameworks);
                if (modules > 0) System.out.printf("%d Module, ", modules);
                if (methods > 0) System.out.printf("%d Method, ", methods);
                if (dogecoins > 0) System.out.printf("%d Dogecoin, ", dogecoins);
                if (ethereums > 0) System.out.printf("%d Ethereum, ", ethereums);
                if (bitcoins > 0) System.out.printf("%d Bitcoin, ", bitcoins);
                if (actions > 0) System.out.printf("%d Action, ", actions);
                if (bugs > 0) System.out.printf("%d Bug", bugs);
                System.out.println("\n");
            }
            
            // Announce winner
            PlayerResult winner = result.getPlayerResults().get(0);
            if (result.getPlayerResults().size() > 1 &&
                winner.getScore() == result.getPlayerResults().get(1).getScore()) {
                System.out.println("It's a tie!");
            } else {
                System.out.println("Winner: " + winner.getName() + " with " + winner.getScore() + " points!");
            }
            
        } catch (PlayerViolationException e) {
            System.err.println("\nGame aborted due to player violation:");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
