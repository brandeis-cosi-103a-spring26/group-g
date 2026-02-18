package edu.brandeis.cosi103a.ip2;

/**
 * Game execution engine that orchestrates play between players.
 * 
 * Responsibility:
 * The Engine executes a complete game given a list of players and available cards,
 * managing game state transitions and enforcing game rules. It communicates with
 * players through the Player interface, providing valid decision options and
 * validating chosen decisions.
 */
public interface Engine {
    /**
     * Executes the game and returns the results for each player.
     * 
     * @return The game results containing player information (name, score, and ending deck)
     *         for each player, sorted from most points to least.
     * @throws PlayerViolationException if a player violates the rules of the game
     *         or throws an exception when making a decision
     */
    GameResult play() throws PlayerViolationException;
}
