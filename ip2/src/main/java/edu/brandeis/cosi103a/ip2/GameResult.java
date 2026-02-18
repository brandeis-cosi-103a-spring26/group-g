package edu.brandeis.cosi103a.ip2;

import com.google.common.collect.ImmutableList;

/**
 * Represents the final results of the game for all players.
 * Players are sorted by score (highest to lowest).
 */
public class GameResult {
    private final ImmutableList<PlayerResult> playerResults;

    public GameResult(ImmutableList<PlayerResult> playerResults) {
        this.playerResults = playerResults;
    }

    public ImmutableList<PlayerResult> getPlayerResults() {
        return playerResults;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Game Results:\n");
        for (int i = 0; i < playerResults.size(); i++) {
            sb.append((i + 1)).append(". ").append(playerResults.get(i)).append("\n");
        }
        return sb.toString();
    }
}
