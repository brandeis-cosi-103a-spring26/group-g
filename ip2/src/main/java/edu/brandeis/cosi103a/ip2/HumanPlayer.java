package edu.brandeis.cosi103a.ip2;

import com.google.common.collect.ImmutableList;
import java.util.Scanner;

/**
 * A human player implementation of the Player interface, using BasePlayer for state management.
 */
public class HumanPlayer extends BasePlayer implements Player {
    private static final Scanner scanner = new Scanner(System.in);

    // Zero-argument constructor required by the Engine
    public HumanPlayer() {
        super("HumanPlayer" + System.currentTimeMillis());
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public Decision makeDecision(GameState state, ImmutableList<Decision> options) {
        try {
            System.out.println("Available decisions:");
            for (int i = 0; i < options.size(); i++) {
                System.out.println(i + ": " + options.get(i));
            }
            System.out.print("Enter the number of your choice: ");
            int choice = scanner.nextInt();
            if (choice >= 0 && choice < options.size()) {
                return options.get(choice);
            } else {
                System.out.println("Invalid choice. Defaulting to first option.");
                return options.get(0);
            }
        } catch (Exception e) {
            System.out.println("Error during decision: " + e.getMessage());
            return options.get(0); // Fallback to first option
        }
    }

    @Override
    public String toString() {
        return getName();
    }
}
