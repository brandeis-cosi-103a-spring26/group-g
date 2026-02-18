package edu.brandeis.cosi103a.ip2;

import com.google.common.collect.ImmutableList;

/**
 * Represents the final result for a single player at the end of the game.
 */
public class PlayerResult {
    private final String name;
    private final int score;
    private final ImmutableList<CardDefinition> endingDeck;

    public PlayerResult(String name, int score, ImmutableList<CardDefinition> endingDeck) {
        this.name = name;
        this.score = score;
        this.endingDeck = endingDeck;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public ImmutableList<CardDefinition> getEndingDeck() {
        return endingDeck;
    }

    @Override
    public String toString() {
        return name + ": " + score + " points (" + endingDeck.size() + " cards)";
    }
}
