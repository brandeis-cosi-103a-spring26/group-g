package edu.brandeis.cosi103a.ip2;

import com.google.common.collect.ImmutableList;

/**
 * The interface to a player in a game of Automation: The Game.
 * A Player participates in a game and is responsible for making decisions throughout the game.
 * The Player cannot directly manipulate game state—it is prompted by the Engine to choose from a list of presented decisions.
 * The Engine is responsible for applying the chosen decision and updating the game state.
 */
public interface Player {
    /**
     * Gets the name of the player.
     * @return the name of the player
     */
    String getName();

    /**
     * Makes a choice during the game. This method should handle exceptions to the greatest extent possible -
     * allowing an exception to escape will cause the player to forfeit the game.
     * @param state the current game state
     * @param options the available decisions to choose from
     * @return the chosen decision
     */
    Decision makeDecision(GameState state, ImmutableList<Decision> options);
}

