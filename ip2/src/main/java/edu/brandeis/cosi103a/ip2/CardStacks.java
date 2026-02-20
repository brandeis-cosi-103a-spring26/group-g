package edu.brandeis.cosi103a.ip2;

import java.util.*;

/**
 * Manages the supply of cards available for purchase in the game.
 * Cards are organized in stacks by their card definition.
 */
public class CardStacks {
    private final Map<CardDefinition, Integer> stacks;
    private final int numPlayers;

    public CardStacks(int numPlayers) {
        if (numPlayers < 1 || numPlayers > 4) {
            throw new IllegalArgumentException("Number of players must be between 1 and 4");
        }
        this.numPlayers = numPlayers;
        this.stacks = new HashMap<>();
        initializeStacks();
    }

    private void initializeStacks() {
        // Money cards
        stacks.put(Cards.BITCOIN, 60);
        stacks.put(Cards.ETHEREUM, 40);
        stacks.put(Cards.DOGECOIN, 30);
        
        // Automation cards
        stacks.put(Cards.METHOD, 14);
        stacks.put(Cards.MODULE, 8);
        stacks.put(Cards.FRAMEWORK, 8);
        
        // Bug cards (10 per player)
        stacks.put(Cards.BUG, 10 * numPlayers);
        
        // Action cards (10 each)
        stacks.put(Cards.REFACTOR, 10);
        stacks.put(Cards.CODE_REVIEW, 10);
        stacks.put(Cards.EVERGREEN_TEST, 10);
    }

    /**
     * Gets the count of cards remaining in a stack.
     */
    public int getCount(CardDefinition card) {
        return stacks.getOrDefault(card, 0);
    }

    /**
     * Takes a card from the stack. Returns true if successful, false if stack is empty.
     */
    public boolean takeCard(CardDefinition card) {
        int count = stacks.getOrDefault(card, 0);
        if (count > 0) {
            stacks.put(card, count - 1);
            return true;
        }
        return false;
    }

    /**
     * Adds a card back to the stack (e.g., for testing or special effects).
     */
    public void returnCard(CardDefinition card) {
        stacks.put(card, stacks.getOrDefault(card, 0) + 1);
    }

    /**
     * Gets all card types that have at least one card available.
     */
    public List<CardDefinition> getAvailableCards() {
        List<CardDefinition> available = new ArrayList<>();
        for (Map.Entry<CardDefinition, Integer> entry : stacks.entrySet()) {
            if (entry.getValue() > 0) {
                available.add(entry.getKey());
            }
        }
        return available;
    }

    /**
     * Checks if the game should end (all Framework cards purchased).
     */
    public boolean isGameOver() {
        return getCount(Cards.FRAMEWORK) == 0;
    }

    /**
     * Gets all stacks for display/debugging purposes.
     */
    public Map<CardDefinition, Integer> getAllStacks() {
        return new HashMap<>(stacks);
    }
}
