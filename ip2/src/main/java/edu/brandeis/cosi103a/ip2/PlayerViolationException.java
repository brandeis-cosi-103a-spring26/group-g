package edu.brandeis.cosi103a.ip2;

/**
 * Exception thrown when a player violates the rules of the game.
 */
public class PlayerViolationException extends Exception {
    public PlayerViolationException(String message) {
        super(message);
    }

    public PlayerViolationException(String message, Throwable cause) {
        super(message, cause);
    }
}
